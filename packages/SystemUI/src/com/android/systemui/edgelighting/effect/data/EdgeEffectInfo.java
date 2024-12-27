package com.android.systemui.edgelighting.effect.data;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
