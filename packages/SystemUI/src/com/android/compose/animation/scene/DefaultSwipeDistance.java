package com.android.compose.animation.scene;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.unit.IntSize;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultSwipeDistance implements UserActionDistance {
    public static final DefaultSwipeDistance INSTANCE = new DefaultSwipeDistance();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private DefaultSwipeDistance() {
    }

    @Override // com.android.compose.animation.scene.UserActionDistance
    public final float absoluteDistance(UserActionDistanceScopeImpl userActionDistanceScopeImpl, ContentKey contentKey, ContentKey contentKey2, Orientation orientation) {
        long j;
        IntSize m923targetSizeGG5KONw = userActionDistanceScopeImpl.$$delegate_0.m923targetSizeGG5KONw(contentKey);
        if (m923targetSizeGG5KONw == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int i = WhenMappings.$EnumSwitchMapping$0[orientation.ordinal()];
        long j2 = m923targetSizeGG5KONw.packedValue;
        if (i == 1) {
            j = j2 >> 32;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            j = 4294967295L & j2;
        }
        return (int) j;
    }
}
