package com.android.systemui.volume.dialog.sliders.shared.model;

import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SliderInputEvent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Button implements SliderInputEvent {
        public static final Button INSTANCE = new Button();

        private Button() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Button);
        }

        public final int hashCode() {
            return -842599122;
        }

        public final String toString() {
            return "Button";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Touch extends SliderInputEvent {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class End implements Touch {
            public final float x;
            public final float y;

            public End(float f, float f2) {
                this.x = f;
                this.y = f2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof End)) {
                    return false;
                }
                End end = (End) obj;
                return Float.compare(this.x, end.x) == 0 && Float.compare(this.y, end.y) == 0;
            }

            @Override // com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent.Touch
            public final float getY() {
                return this.y;
            }

            public final int hashCode() {
                return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("End(x=");
                sb.append(this.x);
                sb.append(", y=");
                return DpCornerSize$$ExternalSyntheticOutline0.m(this.y, ")", sb);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Move implements Touch {
            public final float x;
            public final float y;

            public Move(float f, float f2) {
                this.x = f;
                this.y = f2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Move)) {
                    return false;
                }
                Move move = (Move) obj;
                return Float.compare(this.x, move.x) == 0 && Float.compare(this.y, move.y) == 0;
            }

            @Override // com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent.Touch
            public final float getY() {
                return this.y;
            }

            public final int hashCode() {
                return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Move(x=");
                sb.append(this.x);
                sb.append(", y=");
                return DpCornerSize$$ExternalSyntheticOutline0.m(this.y, ")", sb);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Start implements Touch {
            public final float x;
            public final float y;

            public Start(float f, float f2) {
                this.x = f;
                this.y = f2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Start)) {
                    return false;
                }
                Start start = (Start) obj;
                return Float.compare(this.x, start.x) == 0 && Float.compare(this.y, start.y) == 0;
            }

            @Override // com.android.systemui.volume.dialog.sliders.shared.model.SliderInputEvent.Touch
            public final float getY() {
                return this.y;
            }

            public final int hashCode() {
                return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Start(x=");
                sb.append(this.x);
                sb.append(", y=");
                return DpCornerSize$$ExternalSyntheticOutline0.m(this.y, ")", sb);
            }
        }

        float getY();
    }
}
