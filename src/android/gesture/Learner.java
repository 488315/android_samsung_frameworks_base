package android.gesture;

import java.util.ArrayList;

/* loaded from: classes.dex */
abstract class Learner {
    private final ArrayList<Instance> mInstances = new ArrayList<>();

    abstract ArrayList<Prediction> classify(int i, int i2, float[] fArr);

    Learner() {
    }

    void addInstance(Instance instance) {
        this.mInstances.add(instance);
    }

    ArrayList<Instance> getInstances() {
        return this.mInstances;
    }

    void removeInstance(long j) {
        ArrayList<Instance> arrayList = this.mInstances;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Instance instance = arrayList.get(i);
            if (j == instance.id) {
                arrayList.remove(instance);
                return;
            }
        }
    }

    void removeInstances(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList<Instance> arrayList2 = this.mInstances;
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            Instance instance = arrayList2.get(i);
            if ((instance.label == null && str == null) || (instance.label != null && instance.label.equals(str))) {
                arrayList.add(instance);
            }
        }
        arrayList2.removeAll(arrayList);
    }
}
