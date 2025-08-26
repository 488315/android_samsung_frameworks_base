package androidx.compose.foundation.gestures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class DraggableAnchorsConfig<T> {
    public final List keys = new ArrayList();
    public float[] positions;

    public DraggableAnchorsConfig() {
        float[] fArr = new float[5];
        for (int i = 0; i < 5; i++) {
            fArr[i] = Float.NaN;
        }
        this.positions = fArr;
    }

    public final void at(Object obj, float f) {
        ((ArrayList) this.keys).add(obj);
        if (this.positions.length < ((ArrayList) this.keys).size()) {
            this.positions = Arrays.copyOf(this.positions, ((ArrayList) this.keys).size() + 2);
        }
        this.positions[((ArrayList) this.keys).size() - 1] = f;
    }
}
