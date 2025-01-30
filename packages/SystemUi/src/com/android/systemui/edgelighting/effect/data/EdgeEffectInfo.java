package com.android.systemui.edgelighting.effect.data;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeEffectInfo {
    public Drawable mAppIcon;
    public int[] mEffectColors;
    public int mEffectType;
    public boolean mHasActionButton;
    public boolean mIsBlackBG;
    public boolean mIsMultiResolutionSupoorted;
    public boolean mIsSupportAppIcon;
    public long mLightingDuration;
    public String mNotificationKey;
    public String mPackageName;
    public PendingIntent mPendingIntent;
    public Bundle mPlusEffectBundle;
    public boolean mShouldShowAppIcon;
    public float mStrokeAlpha;
    public float mStrokeWidth;
    public String[] mText;
    public int mWidthDepth;
    public boolean mInfiniteLighting = false;
    public boolean mEdgeLightingAction = true;
    public boolean mIsGrayScaled = true;
}
