package com.android.server.accessibility.magnification;

import android.content.Context;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.accessibility.A11yRune;

import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.os.BackgroundThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MagnificationScaleProvider {
    protected static final float DEFAULT_MAGNIFICATION_SCALE = 2.0f;
    public static final float MAX_SCALE = MagnificationConstants.SCALE_MAX_VALUE;
    public final Context mContext;
    public final SparseArray mUsersScales = new SparseArray();
    public int mCurrentUserId = 0;
    public final Object mLock = new Object();

    public MagnificationScaleProvider(Context context) {
        this.mContext = context;
    }

    public final float getScale(int i) {
        float floatValue;
        if (i == 0 || (i == 1 && A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP)) {
            return Settings.Secure.getFloatForUser(
                    this.mContext.getContentResolver(),
                    "accessibility_display_magnification_scale",
                    DEFAULT_MAGNIFICATION_SCALE,
                    this.mCurrentUserId);
        }
        synchronized (this.mLock) {
            floatValue =
                    ((Float)
                                    getScalesWithCurrentUser()
                                            .get(i, Float.valueOf(DEFAULT_MAGNIFICATION_SCALE)))
                            .floatValue();
        }
        return floatValue;
    }

    public final SparseArray getScalesWithCurrentUser() {
        SparseArray sparseArray = (SparseArray) this.mUsersScales.get(this.mCurrentUserId);
        if (sparseArray != null) {
            return sparseArray;
        }
        SparseArray sparseArray2 = new SparseArray();
        this.mUsersScales.put(this.mCurrentUserId, sparseArray2);
        return sparseArray2;
    }

    public final void putScale(final float f, int i) {
        if (i == 0 || (i == 1 && A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP)) {
            BackgroundThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                             // com.android.server.accessibility.magnification.MagnificationScaleProvider$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MagnificationScaleProvider magnificationScaleProvider =
                                            MagnificationScaleProvider.this;
                                    Settings.Secure.putFloatForUser(
                                            magnificationScaleProvider.mContext
                                                    .getContentResolver(),
                                            "accessibility_display_magnification_scale",
                                            f,
                                            magnificationScaleProvider.mCurrentUserId);
                                }
                            });
            return;
        }
        synchronized (this.mLock) {
            getScalesWithCurrentUser().put(i, Float.valueOf(f));
        }
    }

    public final String toString() {
        String str;
        synchronized (this.mLock) {
            str =
                    "MagnificationScaleProvider{mCurrentUserId="
                            + this.mCurrentUserId
                            + "Scale on the default display="
                            + getScale(0)
                            + "Scales on non-default displays="
                            + getScalesWithCurrentUser()
                            + '}';
        }
        return str;
    }
}
