package com.android.systemui.wallpaper.theme.xmlparser;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameImageView;
import com.android.systemui.wallpaper.view.KeyguardAnimatedWallpaper;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ParserData {
    public AnimationBuilder mAnimationBuilder;
    public final Resources mApkResources;
    public ComplexAnimationBuilder mComplexAnimationBuilder;
    public final Context mContext;
    public float mDeviceDensity;
    public float mDeviceHeight;
    public float mDeviceWidth;
    public FrameImageView mFrameImageView;
    public int mImageViewHeight;
    public int mImageViewWidth;
    public final boolean mIsPreview;
    public boolean mIsScaled;
    public boolean mIsStartTag;
    public boolean mIsWallpaper;
    public int mMetricsHeight;
    public int mMetricsWidth;
    public final String mPkgName;
    public final KeyguardAnimatedWallpaper mRootView;
    public float mScaledDx;
    public float mScaledDy;
    public float mScaledRatio;
    public final WallpaperResultCallback mWallpaperResultCallback;
    public XmlPullParser mXpp;
    public float mPackageWidth = 640.0f;
    public float mPackageHeight = 640.0f;

    public ParserData(Resources resources, Context context, String str, KeyguardAnimatedWallpaper keyguardAnimatedWallpaper, int i, int i2, boolean z, int i3, WallpaperResultCallback wallpaperResultCallback) {
        Display display;
        float f;
        int i4;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDeviceWidth = 640.0f;
        this.mDeviceHeight = 640.0f;
        this.mDeviceDensity = 4.0f;
        this.mScaledRatio = 1.0f;
        this.mScaledDx = 0.0f;
        this.mScaledDy = 0.0f;
        this.mImageViewWidth = -2;
        this.mImageViewHeight = -2;
        this.mContext = context;
        this.mApkResources = resources;
        this.mPkgName = str;
        this.mRootView = keyguardAnimatedWallpaper;
        this.mIsPreview = z;
        this.mWallpaperResultCallback = wallpaperResultCallback;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        if (displayManager != null && (display = displayManager.getDisplay(0)) != null) {
            display.getRealMetrics(displayMetrics);
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            int i5 = displayInfo.rotation;
            int i6 = displayMetrics.widthPixels;
            this.mMetricsWidth = i6;
            int i7 = displayMetrics.heightPixels;
            this.mMetricsHeight = i7;
            if (i6 > i7 && (i5 == 1 || i5 == 3)) {
                this.mMetricsWidth = i7;
                this.mMetricsHeight = i6;
            }
            int i8 = this.mMetricsWidth;
            int i9 = this.mMetricsHeight;
            float f2 = context.getResources().getDisplayMetrics().density;
            if (i3 == 1) {
                if (context.getResources().getConfiguration().orientation == 2) {
                    f = context.getResources().getDisplayMetrics().heightPixels;
                    i4 = context.getResources().getDisplayMetrics().widthPixels;
                } else {
                    f = context.getResources().getDisplayMetrics().widthPixels;
                    i4 = context.getResources().getDisplayMetrics().heightPixels;
                }
                DensityUtil.sMetricsWidth = (int) (f / f2);
                DensityUtil.sMetricsHeight = (int) (i4 / f2);
            } else {
                DensityUtil.sMetricsWidth = (int) (i8 / f2);
                DensityUtil.sMetricsHeight = (int) (i9 / f2);
            }
        }
        float f3 = context.getResources().getDisplayMetrics().density;
        this.mDeviceDensity = f3;
        if (z) {
            this.mDeviceWidth = i / f3;
            this.mDeviceHeight = i2 / f3;
        } else {
            this.mDeviceWidth = DensityUtil.sMetricsWidth;
            this.mDeviceHeight = DensityUtil.sMetricsHeight;
        }
        this.mFrameImageView = new FrameImageView(context);
        this.mAnimationBuilder = new AnimationBuilder();
        this.mComplexAnimationBuilder = new ComplexAnimationBuilder();
    }

    public final float getDevicePixelX(float f) {
        return Math.round((f * this.mScaledRatio * this.mDeviceDensity) + 0.5f);
    }

    public final float getDevicePixelY(float f) {
        return Math.round((f * this.mScaledRatio * this.mDeviceDensity) + 0.5f);
    }
}
