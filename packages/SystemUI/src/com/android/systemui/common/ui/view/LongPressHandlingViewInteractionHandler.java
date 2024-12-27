package com.android.systemui.common.ui.view;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LongPressHandlingViewInteractionHandler {
    public final Function0 isAttachedToWindow;
    public boolean isLongPressHandlingEnabled;
    public Function0 longPressDuration;
    public final Function2 onLongPressDetected;
    public final Function0 onSingleTapDetected;
    public final Function2 postDelayed;
    public DisposableHandle scheduledLongPressHandle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class MotionEventModel {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Cancel extends MotionEventModel {
            public static final Cancel INSTANCE = new Cancel();

            private Cancel() {
                super(null);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Down extends MotionEventModel {
            public final int x;
            public final int y;

            public Down(int i, int i2) {
                super(null);
                this.x = i;
                this.y = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Down)) {
                    return false;
                }
                Down down = (Down) obj;
                return this.x == down.x && this.y == down.y;
            }

            public final int hashCode() {
                return Integer.hashCode(this.y) + (Integer.hashCode(this.x) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Down(x=");
                sb.append(this.x);
                sb.append(", y=");
                return Anchor$$ExternalSyntheticOutline0.m(this.y, ")", sb);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Move extends MotionEventModel {
            public final float distanceMoved;

            public Move(float f) {
                super(null);
                this.distanceMoved = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Move) && Float.compare(this.distanceMoved, ((Move) obj).distanceMoved) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.distanceMoved);
            }

            public final String toString() {
                return "Move(distanceMoved=" + this.distanceMoved + ")";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Other extends MotionEventModel {
            public static final Other INSTANCE = new Other();

            private Other() {
                super(null);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Up extends MotionEventModel {
            public final float distanceMoved;
            public final long gestureDuration;

            public Up(float f, long j) {
                super(null);
                this.distanceMoved = f;
                this.gestureDuration = j;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Up)) {
                    return false;
                }
                Up up = (Up) obj;
                return Float.compare(this.distanceMoved, up.distanceMoved) == 0 && this.gestureDuration == up.gestureDuration;
            }

            public final int hashCode() {
                return Long.hashCode(this.gestureDuration) + (Float.hashCode(this.distanceMoved) * 31);
            }

            public final String toString() {
                return "Up(distanceMoved=" + this.distanceMoved + ", gestureDuration=" + this.gestureDuration + ")";
            }
        }

        private MotionEventModel() {
        }

        public /* synthetic */ MotionEventModel(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LongPressHandlingViewInteractionHandler(Function2 function2, Function0 function0, Function2 function22, Function0 function02, Function0 function03) {
        this.postDelayed = function2;
        this.isAttachedToWindow = function0;
        this.onLongPressDetected = function22;
        this.onSingleTapDetected = function02;
        this.longPressDuration = function03;
    }
}
