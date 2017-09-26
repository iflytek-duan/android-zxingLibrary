package com.uuzuche.lib_zxing.activity;

import android.content.Context;
import android.util.DisplayMetrics;

import com.uuzuche.lib_zxing.DisplayUtil;

/**
 * Created by aaron on 16/9/7.
 */

public class ZXingLibrary {
    private boolean playBeep = true;// 标识是否播放闹铃，默认为播放
    private boolean vibrate = true;// 标识是否开启震动，默认开启

    private ZXingLibrary() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static class ZXingLibraryHolders {
        static final ZXingLibrary INSTANCE = new ZXingLibrary();
    }

    public static ZXingLibrary getInstance() {
        return ZXingLibraryHolders.INSTANCE;
    }

    public void initDisplayOpinion(Context context) {
        if (context == null) {
            return;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(context, dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(context, dm.heightPixels);
    }

    public boolean isPlayBeep() {
        return playBeep;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setPlayBeep(boolean playBeep) {
        this.playBeep = playBeep;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }
}
