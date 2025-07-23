package com.android.systemui.edgelighting.effect.data;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EdgeEffectInfo {
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
    public final boolean mInfiniteLighting = false;
    public boolean mIsGrayScaled = true;
}
