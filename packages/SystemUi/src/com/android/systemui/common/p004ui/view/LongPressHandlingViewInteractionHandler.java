package com.android.systemui.common.p004ui.view;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LongPressHandlingViewInteractionHandler {
    public final Function0 isAttachedToWindow;
    public final Function2 onLongPressDetected;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class MotionEventModel {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Cancel extends MotionEventModel {
            public static final /* synthetic */ int $r8$clinit = 0;

            static {
                new Cancel();
            }

            private Cancel() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Down extends MotionEventModel {

            /* renamed from: x */
            public final int f241x;

            /* renamed from: y */
            public final int f242y;

            public Down(int i, int i2) {
                super(null);
                this.f241x = i;
                this.f242y = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Down)) {
                    return false;
                }
                Down down = (Down) obj;
                return this.f241x == down.f241x && this.f242y == down.f242y;
            }

            public final int hashCode() {
                return Integer.hashCode(this.f242y) + (Integer.hashCode(this.f241x) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Down(x=");
                sb.append(this.f241x);
                sb.append(", y=");
                return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.f242y, ")");
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Other extends MotionEventModel {
            public static final /* synthetic */ int $r8$clinit = 0;

            static {
                new Other();
            }

            private Other() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.common.ui.view.LongPressHandlingViewInteractionHandler$MotionEventModel$Up */
        public final class C1163Up extends MotionEventModel {
            public final float distanceMoved;
            public final long gestureDuration;

            public C1163Up(float f, long j) {
                super(null);
                this.distanceMoved = f;
                this.gestureDuration = j;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof C1163Up)) {
                    return false;
                }
                C1163Up c1163Up = (C1163Up) obj;
                return Float.compare(this.distanceMoved, c1163Up.distanceMoved) == 0 && this.gestureDuration == c1163Up.gestureDuration;
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

    public LongPressHandlingViewInteractionHandler(Function2 function2, Function0 function0, Function2 function22, Function0 function02) {
        this.isAttachedToWindow = function0;
        this.onLongPressDetected = function22;
    }
}
