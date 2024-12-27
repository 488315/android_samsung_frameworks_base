package com.android.server.accessibility.magnification;

import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.DisplayInfo;
import android.view.MagnificationSpec;

import com.android.internal.util.function.QuintConsumer;

import java.util.Locale;

public final /* synthetic */
class FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda4
        implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        float f;
        FullScreenMagnificationController.DisplayMagnification displayMagnification =
                (FullScreenMagnificationController.DisplayMagnification) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        int intValue3 = ((Integer) obj4).intValue();
        int intValue4 = ((Integer) obj5).intValue();
        synchronized (FullScreenMagnificationController.this.mLock) {
            try {
                Rect rect = displayMagnification.mTempRect;
                rect.set(displayMagnification.mMagnificationBounds);
                if (rect.intersects(intValue, intValue2, intValue3, intValue4)) {
                    Rect rect2 = displayMagnification.mTempRect1;
                    MagnificationSpec magnificationSpec =
                            displayMagnification.mSpecAnimationBridge.mSentMagnificationSpec;
                    float f2 = magnificationSpec.scale;
                    float f3 = magnificationSpec.offsetX;
                    float f4 = magnificationSpec.offsetY;
                    rect2.set(displayMagnification.mMagnificationBounds);
                    rect2.offset((int) (-f3), (int) (-f4));
                    rect2.scale(1.0f / f2);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    DisplayInfo displayInfo =
                            FullScreenMagnificationController.this.mDisplayManagerInternal
                                    .getDisplayInfo(displayMagnification.mDisplayId);
                    if (displayInfo != null) {
                        displayInfo.getLogicalMetrics(
                                displayMetrics,
                                CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO,
                                (Configuration) null);
                    } else {
                        displayMetrics.setToDefaults();
                    }
                    float width = rect2.width() / 4.0f;
                    float applyDimension = TypedValue.applyDimension(1, 10.0f, displayMetrics);
                    int i = intValue3 - intValue;
                    int width2 = rect2.width();
                    float f5 = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    if (i > width2) {
                        f =
                                TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0
                                        ? intValue - rect2.left
                                        : intValue3 - rect2.right;
                    } else {
                        if (intValue < rect2.left) {
                            f = (intValue - r3) - width;
                        } else {
                            f = intValue3 > rect2.right ? (intValue3 - r6) + width : 0.0f;
                        }
                    }
                    if (intValue4 - intValue2 > rect2.height()) {
                        f5 = intValue2 - rect2.top;
                    } else {
                        if (intValue2 < rect2.top) {
                            f5 = (intValue2 - r9) - applyDimension;
                        } else {
                            if (intValue4 > rect2.bottom) {
                                f5 = (intValue4 - r8) + applyDimension;
                            }
                        }
                    }
                    float f6 = displayMagnification.mCurrentMagnificationSpec.scale;
                    displayMagnification.offsetMagnifiedRegion(-1, f * f6, f5 * f6);
                }
            } finally {
            }
        }
    }
}
