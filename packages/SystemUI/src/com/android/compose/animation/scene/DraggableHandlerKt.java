package com.android.compose.animation.scene;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.input.pointer.PointerType;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.SwipeSource;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DraggableHandlerKt {

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

    /* renamed from: access$resolveSwipe-DrK6AWw, reason: not valid java name */
    public static final Swipe.Resolved m919access$resolveSwipeDrK6AWw(Orientation orientation, boolean z, SwipeSource.Resolved resolved, int i, PointerType pointerType) {
        SwipeDirection.Resolved resolved2;
        int i2 = WhenMappings.$EnumSwitchMapping$0[orientation.ordinal()];
        if (i2 == 1) {
            resolved2 = z ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
        } else {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            resolved2 = z ? SwipeDirection.Resolved.Up : SwipeDirection.Resolved.Down;
        }
        return new Swipe.Resolved(resolved2, i, resolved, pointerType, null);
    }
}
