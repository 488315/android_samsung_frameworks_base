package com.android.systemui.edgelighting.effect.data;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public final class EdgeEffectInfo {
    public Drawable mAppIcon;
    public int[] mEffectColors;
    public int mEffectType;
    public boolean mHasActionButton;
    public boolean mIsBlackBG;
    public boolean mIsMultiResolutionSupoorted;
    public boolean mIsSmallIcon;
    public boolean mIsUsingAppIcon;
    public long mLightingDuration;
    public String mNotificationKey;
    public String mPackageName;
    public PendingIntent mPendingIntent;
    public Bundle mPlusEffectBundle;
    public float mStrokeAlpha;
    public float mStrokeWidth;
    public String[] mText;
    public int mWidthDepth;
    public boolean mInfiniteLighting = false;
    public boolean mEdgeLightingAction = true;
    public boolean mIsGrayScaled = true;
}
