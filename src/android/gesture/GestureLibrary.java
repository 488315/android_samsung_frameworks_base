package android.gesture;

import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class GestureLibrary {
    protected final GestureStore mStore = new GestureStore();

    public boolean isReadOnly() {
        return false;
    }

    public abstract boolean load();

    public abstract boolean save();

    protected GestureLibrary() {
    }

    public Learner getLearner() {
        return this.mStore.getLearner();
    }

    public void setOrientationStyle(int i) {
        this.mStore.setOrientationStyle(i);
    }

    public int getOrientationStyle() {
        return this.mStore.getOrientationStyle();
    }

    public void setSequenceType(int i) {
        this.mStore.setSequenceType(i);
    }

    public int getSequenceType() {
        return this.mStore.getSequenceType();
    }

    public Set<String> getGestureEntries() {
        return this.mStore.getGestureEntries();
    }

    public ArrayList<Prediction> recognize(Gesture gesture) {
        return this.mStore.recognize(gesture);
    }

    public void addGesture(String str, Gesture gesture) {
        this.mStore.addGesture(str, gesture);
    }

    public void removeGesture(String str, Gesture gesture) {
        this.mStore.removeGesture(str, gesture);
    }

    public void removeEntry(String str) {
        this.mStore.removeEntry(str);
    }

    public ArrayList<Gesture> getGestures(String str) {
        return this.mStore.getGestures(str);
    }
}
