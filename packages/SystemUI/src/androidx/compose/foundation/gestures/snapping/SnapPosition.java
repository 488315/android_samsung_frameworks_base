package androidx.compose.foundation.gestures.snapping;

/* loaded from: classes.dex */
public interface SnapPosition {

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
