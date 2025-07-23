package android.gesture;

/* loaded from: classes.dex */
class Instance {
    private static final float[] ORIENTATIONS = {0.0f, 0.7853982f, 1.5707964f, 2.3561945f, 3.1415927f, 0.0f, -0.7853982f, -1.5707964f, -2.3561945f, -3.1415927f};
    private static final int PATCH_SAMPLE_SIZE = 16;
    private static final int SEQUENCE_SAMPLE_SIZE = 16;
    final long id;
    final String label;
    final float[] vector;

    private Instance(long j, float[] fArr, String str) {
        this.id = j;
        this.vector = fArr;
        this.label = str;
    }

    private void normalize() {
        float[] fArr = this.vector;
        int length = fArr.length;
        float f = 0.0f;
        for (float f2 : fArr) {
            f += f2 * f2;
        }
        float sqrt = (float) Math.sqrt(f);
        for (int i = 0; i < length; i++) {
            fArr[i] = fArr[i] / sqrt;
        }
    }

    static Instance createInstance(int i, int i2, Gesture gesture, String str) {
        if (i == 2) {
            Instance instance = new Instance(gesture.getID(), temporalSampler(i2, gesture), str);
            instance.normalize();
            return instance;
        }
        return new Instance(gesture.getID(), spatialSampler(gesture), str);
    }

    private static float[] spatialSampler(Gesture gesture) {
        return GestureUtils.spatialSampling(gesture, 16, false);
    }

    private static float[] temporalSampler(int i, Gesture gesture) {
        float[] temporalSampling = GestureUtils.temporalSampling(gesture.getStrokes().get(0), 16);
        float[] computeCentroid = GestureUtils.computeCentroid(temporalSampling);
        float atan2 = (float) Math.atan2(temporalSampling[1] - computeCentroid[1], temporalSampling[0] - computeCentroid[0]);
        float f = -atan2;
        if (i != 1) {
            int length = ORIENTATIONS.length;
            for (int i2 = 0; i2 < length; i2++) {
                float f2 = ORIENTATIONS[i2] - atan2;
                if (Math.abs(f2) < Math.abs(f)) {
                    f = f2;
                }
            }
        }
        GestureUtils.translate(temporalSampling, -computeCentroid[0], -computeCentroid[1]);
        GestureUtils.rotate(temporalSampling, f);
        return temporalSampling;
    }
}
