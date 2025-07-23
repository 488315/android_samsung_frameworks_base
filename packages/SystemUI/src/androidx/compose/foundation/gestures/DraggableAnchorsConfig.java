package androidx.compose.foundation.gestures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
