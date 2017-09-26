/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uuzuche.lib_zxing.decoding;

import android.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Finishes an activity after a period of inactivity.
 * 在一段时间内不活动之后结束一个Activity.
 */
public final class InactivityTimer {

    private static final int INACTIVITY_DELAY_SECONDS = 5 * 60;// 5分钟后自动关闭目标activity

    /**
     * 创建只有一条线程的线程池，他可以在指定延迟后执行线程任务,其中一个构造方法可以传入ThreadFractory
     * JDK 1.5之后有了ScheduledExecutorService，不建议你再使用java.util.Timer，
     * 因为它无论功能性能都不如ScheduledExecutorService。
     */
    private final ScheduledExecutorService inactivityTimer =
            Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
    private final Activity activity;
    private ScheduledFuture<?> inactivityFuture = null;// 表示ScheduledExecutorService中提交了任务的返回结果

    public InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }

    public void onActivity() {
        cancel();
        inactivityFuture = inactivityTimer.schedule(new FinishListener(activity),
                INACTIVITY_DELAY_SECONDS,
                TimeUnit.SECONDS);// 5分钟后执行关闭操作
    }

    private void cancel() {
        if (inactivityFuture != null) {
            inactivityFuture.cancel(true);
            inactivityFuture = null;
        }
    }

    public void shutdown() {
        cancel();
        inactivityTimer.shutdown();
    }

    /**
     * 通过自定义ThreadFactory创建一个守护线程
     */
    private static final class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            // 设置为守护线程,用于守护当前的UI线程
            thread.setDaemon(true);
            return thread;
        }
    }

}
