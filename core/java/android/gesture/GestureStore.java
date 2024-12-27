package android.gesture;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class GestureStore {
    private static final short FILE_FORMAT_VERSION = 1;
    public static final int ORIENTATION_INVARIANT = 1;
    public static final int ORIENTATION_SENSITIVE = 2;
    static final int ORIENTATION_SENSITIVE_4 = 4;
    static final int ORIENTATION_SENSITIVE_8 = 8;
    private static final boolean PROFILE_LOADING_SAVING = false;
    public static final int SEQUENCE_INVARIANT = 1;
    public static final int SEQUENCE_SENSITIVE = 2;
    private int mSequenceType = 2;
    private int mOrientationStyle = 2;
    private final HashMap<String, ArrayList<Gesture>> mNamedGestures = new HashMap<>();
    private boolean mChanged = false;
    private Learner mClassifier = new InstanceLearner();

    public void setOrientationStyle(int style) {
        this.mOrientationStyle = style;
    }

    public int getOrientationStyle() {
        return this.mOrientationStyle;
    }

    public void setSequenceType(int type) {
        this.mSequenceType = type;
    }

    public int getSequenceType() {
        return this.mSequenceType;
    }

    public Set<String> getGestureEntries() {
        return this.mNamedGestures.keySet();
    }

    public ArrayList<Prediction> recognize(Gesture gesture) {
        Instance instance =
                Instance.createInstance(this.mSequenceType, this.mOrientationStyle, gesture, null);
        return this.mClassifier.classify(
                this.mSequenceType, this.mOrientationStyle, instance.vector);
    }

    public void addGesture(String entryName, Gesture gesture) {
        if (entryName == null || entryName.length() == 0) {
            return;
        }
        ArrayList<Gesture> gestures = this.mNamedGestures.get(entryName);
        if (gestures == null) {
            gestures = new ArrayList<>();
            this.mNamedGestures.put(entryName, gestures);
        }
        gestures.add(gesture);
        this.mClassifier.addInstance(
                Instance.createInstance(
                        this.mSequenceType, this.mOrientationStyle, gesture, entryName));
        this.mChanged = true;
    }

    public void removeGesture(String entryName, Gesture gesture) {
        ArrayList<Gesture> gestures = this.mNamedGestures.get(entryName);
        if (gestures == null) {
            return;
        }
        gestures.remove(gesture);
        if (gestures.isEmpty()) {
            this.mNamedGestures.remove(entryName);
        }
        this.mClassifier.removeInstance(gesture.getID());
        this.mChanged = true;
    }

    public void removeEntry(String entryName) {
        this.mNamedGestures.remove(entryName);
        this.mClassifier.removeInstances(entryName);
        this.mChanged = true;
    }

    public ArrayList<Gesture> getGestures(String entryName) {
        ArrayList<Gesture> gestures = this.mNamedGestures.get(entryName);
        if (gestures != null) {
            return new ArrayList<>(gestures);
        }
        return null;
    }

    public boolean hasChanged() {
        return this.mChanged;
    }

    public void save(OutputStream stream) throws IOException {
        save(stream, false);
    }

    public void save(OutputStream stream, boolean closeStream) throws IOException {
        DataOutputStream out = null;
        try {
            HashMap<String, ArrayList<Gesture>> maps = this.mNamedGestures;
            out =
                    new DataOutputStream(
                            stream instanceof BufferedOutputStream
                                    ? stream
                                    : new BufferedOutputStream(stream, 32768));
            out.writeShort(1);
            out.writeInt(maps.size());
            for (Map.Entry<String, ArrayList<Gesture>> entry : maps.entrySet()) {
                String key = entry.getKey();
                ArrayList<Gesture> examples = entry.getValue();
                int count = examples.size();
                out.writeUTF(key);
                out.writeInt(count);
                for (int i = 0; i < count; i++) {
                    examples.get(i).serialize(out);
                }
            }
            out.flush();
            this.mChanged = false;
        } finally {
            if (closeStream) {
                GestureUtils.closeStream(out);
            }
        }
    }

    public void load(InputStream stream) throws IOException {
        load(stream, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0022 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void load(java.io.InputStream r5, boolean r6) throws java.io.IOException {
        /*
            r4 = this;
            r0 = 0
            java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch: java.lang.Throwable -> L26
            boolean r2 = r5 instanceof java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L26
            if (r2 == 0) goto L9
            r2 = r5
            goto L11
        L9:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L26
            r3 = 32768(0x8000, float:4.5918E-41)
            r2.<init>(r5, r3)     // Catch: java.lang.Throwable -> L26
        L11:
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L26
            r0 = r1
            short r1 = r0.readShort()     // Catch: java.lang.Throwable -> L26
            switch(r1) {
                case 1: goto L1d;
                default: goto L1c;
            }     // Catch: java.lang.Throwable -> L26
        L1c:
            goto L20
        L1d:
            r4.readFormatV1(r0)     // Catch: java.lang.Throwable -> L26
        L20:
            if (r6 == 0) goto L25
            android.gesture.GestureUtils.closeStream(r0)
        L25:
            return
        L26:
            r1 = move-exception
            if (r6 == 0) goto L2c
            android.gesture.GestureUtils.closeStream(r0)
        L2c:
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.gesture.GestureStore.load(java.io.InputStream,"
                        + " boolean):void");
    }

    private void readFormatV1(DataInputStream in) throws IOException {
        Learner classifier = this.mClassifier;
        HashMap<String, ArrayList<Gesture>> namedGestures = this.mNamedGestures;
        namedGestures.clear();
        int entriesCount = in.readInt();
        for (int i = 0; i < entriesCount; i++) {
            String name = in.readUTF();
            int gestureCount = in.readInt();
            ArrayList<Gesture> gestures = new ArrayList<>(gestureCount);
            for (int j = 0; j < gestureCount; j++) {
                Gesture gesture = Gesture.deserialize(in);
                gestures.add(gesture);
                classifier.addInstance(
                        Instance.createInstance(
                                this.mSequenceType, this.mOrientationStyle, gesture, name));
            }
            namedGestures.put(name, gestures);
        }
    }

    Learner getLearner() {
        return this.mClassifier;
    }
}
