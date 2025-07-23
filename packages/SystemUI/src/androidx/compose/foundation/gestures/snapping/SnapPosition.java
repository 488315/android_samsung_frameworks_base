package androidx.compose.foundation.gestures.snapping;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SnapPosition {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Start implements SnapPosition {
        public static final Start INSTANCE = new Start();

        private Start() {
        }

        @Override // androidx.compose.foundation.gestures.snapping.SnapPosition
        public final int position(int i, int i2, int i3, int i4) {
            return 0;
        }

        public final String toString() {
            return "Start";
        }
    }

    int position(int i, int i2, int i3, int i4);
}
