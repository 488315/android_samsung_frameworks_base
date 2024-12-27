package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AppCompatUtils {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.AppCompatUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements BooleanSupplier {
        public boolean mRead;
        public boolean mValue;
        public final /* synthetic */ BooleanSupplier val$supplier;

        public AnonymousClass1(BooleanSupplier booleanSupplier) {
            this.val$supplier = booleanSupplier;
        }

        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            if (!this.mRead) {
                this.mRead = true;
                this.mValue = this.val$supplier.getAsBoolean();
            }
            return this.mValue;
        }
    }

    public static void adjustBoundsForTaskbar(WindowState windowState, Rect rect) {
        InsetsSource insetsSource;
        InsetsState insetsState = windowState.getInsetsState(false);
        int sourceSize = insetsState.sourceSize() - 1;
        while (true) {
            if (sourceSize < 0) {
                insetsSource = null;
                break;
            }
            insetsSource = insetsState.sourceAt(sourceSize);
            if (insetsSource.getType() == WindowInsets.Type.navigationBars() && insetsSource.hasFlags(2) && insetsSource.isVisible()) {
                break;
            } else {
                sourceSize--;
            }
        }
        if (insetsSource != null) {
            rect.bottom = Math.min(rect.bottom, insetsSource.getFrame().top);
        }
    }

    public static float computeAspectRatio(Rect rect) {
        return (rect.width() == 0 || rect.height() == 0) ? FullScreenMagnificationGestureHandler.MAX_SCALE : Math.max(r0, r2) / Math.min(r0, r2);
    }

    public static Rect getAppBounds(final ActivityRecord activityRecord) {
        final Rect appBounds = activityRecord.getConfiguration().windowConfiguration.getAppBounds();
        return appBounds == null ? activityRecord.getBounds() : (Rect) activityRecord.mAppCompatController.mTransparentPolicy.mTransparentPolicyState.findOpaqueNotFinishingActivityBelow().map(new AppCompatUtils$$ExternalSyntheticLambda0()).orElseGet(new Supplier() { // from class: com.android.server.wm.AppCompatUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                ActivityRecord activityRecord2 = ActivityRecord.this;
                return activityRecord2.hasSizeCompatBounds() ? activityRecord2.getScreenResolvedBounds() : appBounds;
            }
        });
    }

    public static void offsetBounds(Configuration configuration, int i, int i2) {
        configuration.windowConfiguration.getBounds().offset(i, i2);
        configuration.windowConfiguration.getAppBounds().offset(i, i2);
    }
}
