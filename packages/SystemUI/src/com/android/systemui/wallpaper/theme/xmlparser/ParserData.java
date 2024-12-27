package com.android.systemui.wallpaper.theme.xmlparser;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayInfo;
import android.widget.FrameLayout;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameImageView;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ParserData {
    public AnimationBuilder mAnimationBuilder;
    public final Resources mApkResources;
    public final ComplexAnimationBuilder mComplexAnimationBuilder;
    public final Context mContext;
    public final float mDeviceDensity;
    public final float mDeviceHeight;
    public final float mDeviceWidth;
    public FrameImageView mFrameImageView;
    public int mImageViewHeight;
    public int mImageViewWidth;
    public boolean mIsScaled;
    public boolean mIsStartTag;
    public boolean mIsWallpaper;
    public final int mMetricsHeight;
    public final int mMetricsWidth;
    public final String mPkgName;
    public final FrameLayout mRootView;
    public float mScaledDx;
    public float mScaledDy;
    public float mScaledRatio;
    public XmlPullParser mXpp;
    public float mPackageWidth = 640.0f;
    public float mPackageHeight = 640.0f;

    public ParserData(Resources resources, Context context, String str, FrameLayout frameLayout, int i, int i2, boolean z) {
        Display display;
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
        this.mRootView = frameLayout;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        if (displayManager != null && (display = displayManager.getDisplay(0)) != null) {
            display.getRealMetrics(displayMetrics);
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            int i3 = displayInfo.rotation;
            int i4 = displayMetrics.widthPixels;
            this.mMetricsWidth = i4;
            int i5 = displayMetrics.heightPixels;
            this.mMetricsHeight = i5;
            if (i4 > i5 && (i3 == 1 || i3 == 3)) {
                this.mMetricsWidth = i5;
                this.mMetricsHeight = i4;
            }
            int i6 = this.mMetricsWidth;
            int i7 = this.mMetricsHeight;
            float f = context.getResources().getDisplayMetrics().density;
            DensityUtil.sMetricsWidth = (int) (i6 / f);
            DensityUtil.sMetricsHeight = (int) (i7 / f);
        }
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mDeviceDensity = f2;
        if (z) {
            this.mDeviceWidth = i / f2;
            this.mDeviceHeight = i2 / f2;
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
