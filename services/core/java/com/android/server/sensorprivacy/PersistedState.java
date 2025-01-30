package com.android.server.sensorprivacy;

import android.os.Environment;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.BiConsumer;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class PersistedState {
    public static final String LOG_TAG = "PersistedState";
    public final AtomicFile mAtomicFile;
    public ArrayMap mStates = new ArrayMap();

    public static PersistedState fromFile(String str) {
        return new PersistedState(str);
    }

    public PersistedState(String str) {
        this.mAtomicFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), str));
        readState();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.server.sensorprivacy.PersistedState$PVersion1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readState() {
        FileInputStream openRead;
        Object obj;
        PVersion2 pVersion2;
        boolean z;
        boolean z2;
        PVersion2 pVersion22;
        AtomicFile atomicFile;
        IOException e;
        AtomicFile atomicFile2 = this.mAtomicFile;
        if (!atomicFile2.exists()) {
            AtomicFile atomicFile3 = new AtomicFile(new File(Environment.getDataSystemDirectory(), "sensor_privacy.xml"));
            if (atomicFile3.exists()) {
                try {
                    FileInputStream openRead2 = atomicFile3.openRead();
                    try {
                        XmlUtils.beginDocument(Xml.resolvePullParser(openRead2), "sensor-privacy");
                        if (openRead2 != null) {
                            try {
                                openRead2.close();
                            } catch (IOException e2) {
                                e = e2;
                                atomicFile = atomicFile3;
                                Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e);
                                atomicFile3.delete();
                                atomicFile2 = atomicFile;
                                int i = 2;
                                boolean z3 = false;
                                boolean z4 = false;
                                boolean z5 = false;
                                if (atomicFile2.exists()) {
                                }
                                obj = null;
                                if (obj == null) {
                                }
                                z = obj instanceof PVersion0;
                                ?? r2 = obj;
                                if (z) {
                                }
                                z2 = r2 instanceof PVersion1;
                                pVersion22 = r2;
                                if (z2) {
                                }
                                if (!(pVersion22 instanceof PVersion2)) {
                                }
                            } catch (XmlPullParserException unused) {
                            }
                        }
                        atomicFile2 = atomicFile3;
                    } catch (Throwable th) {
                        if (openRead2 != null) {
                            try {
                                openRead2.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    atomicFile = atomicFile2;
                    e = e3;
                    Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e);
                    atomicFile3.delete();
                    atomicFile2 = atomicFile;
                    int i2 = 2;
                    boolean z32 = false;
                    boolean z42 = false;
                    boolean z52 = false;
                    if (atomicFile2.exists()) {
                    }
                    obj = null;
                    if (obj == null) {
                    }
                    z = obj instanceof PVersion0;
                    ?? r22 = obj;
                    if (z) {
                    }
                    z2 = r22 instanceof PVersion1;
                    pVersion22 = r22;
                    if (z2) {
                    }
                    if (!(pVersion22 instanceof PVersion2)) {
                    }
                } catch (XmlPullParserException unused2) {
                }
            }
        }
        int i22 = 2;
        boolean z322 = false;
        boolean z422 = false;
        boolean z522 = false;
        if (atomicFile2.exists()) {
            try {
                openRead = atomicFile2.openRead();
            } catch (IOException | RuntimeException | XmlPullParserException e4) {
                Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e4);
                atomicFile2.delete();
            }
            try {
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                XmlUtils.beginDocument(resolvePullParser, "sensor-privacy");
                int i3 = 0;
                int attributeInt = resolvePullParser.getAttributeInt((String) null, "persistence-version", 0);
                if (attributeInt == 0) {
                    PVersion0 pVersion0 = new PVersion0(i3);
                    readPVersion0(resolvePullParser, pVersion0);
                    obj = pVersion0;
                } else {
                    if (attributeInt == 1) {
                        ?? pVersion1 = new PVersion1(resolvePullParser.getAttributeInt((String) null, "version", 1));
                        readPVersion1(resolvePullParser, pVersion1);
                        pVersion2 = pVersion1;
                    } else if (attributeInt == 2) {
                        PVersion2 pVersion23 = new PVersion2(resolvePullParser.getAttributeInt((String) null, "version", 2));
                        readPVersion2(resolvePullParser, pVersion23);
                        pVersion2 = pVersion23;
                    } else {
                        Log.e(LOG_TAG, "Unknown persistence version: " + attributeInt + ". Deleting.", new RuntimeException());
                        atomicFile2.delete();
                        obj = null;
                    }
                    obj = pVersion2;
                }
                if (openRead != null) {
                    openRead.close();
                }
                if (obj == null) {
                    obj = new PVersion2(i22);
                }
                z = obj instanceof PVersion0;
                ?? r222 = obj;
                if (z) {
                    r222 = PVersion1.fromPVersion0((PVersion0) obj);
                }
                z2 = r222 instanceof PVersion1;
                pVersion22 = r222;
                if (z2) {
                    pVersion22 = PVersion2.fromPVersion1((PVersion1) r222);
                }
                if (!(pVersion22 instanceof PVersion2)) {
                    this.mStates = pVersion22.mStates;
                    return;
                } else {
                    Log.e(LOG_TAG, "State not successfully upgraded.");
                    this.mStates = new ArrayMap();
                    return;
                }
            } catch (Throwable th3) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }
        obj = null;
        if (obj == null) {
        }
        z = obj instanceof PVersion0;
        ?? r2222 = obj;
        if (z) {
        }
        z2 = r2222 instanceof PVersion1;
        pVersion22 = r2222;
        if (z2) {
        }
        if (!(pVersion22 instanceof PVersion2)) {
        }
    }

    public static void readPVersion0(TypedXmlPullParser typedXmlPullParser, PVersion0 pVersion0) {
        XmlUtils.nextElement(typedXmlPullParser);
        while (typedXmlPullParser.getEventType() != 1) {
            if ("individual-sensor-privacy".equals(typedXmlPullParser.getName())) {
                pVersion0.addState(XmlUtils.readIntAttribute(typedXmlPullParser, "sensor"), XmlUtils.readBooleanAttribute(typedXmlPullParser, "enabled"));
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            } else {
                XmlUtils.nextElement(typedXmlPullParser);
            }
        }
    }

    public static void readPVersion1(TypedXmlPullParser typedXmlPullParser, PVersion1 pVersion1) {
        while (typedXmlPullParser.getEventType() != 1) {
            XmlUtils.nextElement(typedXmlPullParser);
            if ("user".equals(typedXmlPullParser.getName())) {
                int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "id");
                int depth = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    if ("individual-sensor-privacy".equals(typedXmlPullParser.getName())) {
                        pVersion1.addState(attributeInt, typedXmlPullParser.getAttributeInt((String) null, "sensor"), typedXmlPullParser.getAttributeBoolean((String) null, "enabled"));
                    }
                }
            }
        }
    }

    public static void readPVersion2(TypedXmlPullParser typedXmlPullParser, PVersion2 pVersion2) {
        while (typedXmlPullParser.getEventType() != 1) {
            XmlUtils.nextElement(typedXmlPullParser);
            if ("sensor-state".equals(typedXmlPullParser.getName())) {
                pVersion2.addState(typedXmlPullParser.getAttributeInt((String) null, "toggle-type"), typedXmlPullParser.getAttributeInt((String) null, "user-id"), typedXmlPullParser.getAttributeInt((String) null, "sensor"), typedXmlPullParser.getAttributeInt((String) null, "state-type"), typedXmlPullParser.getAttributeLong((String) null, "last-change"));
            } else {
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public SensorState getState(int i, int i2, int i3) {
        return (SensorState) this.mStates.get(new TypeUserSensor(i, i2, i3));
    }

    public SensorState setState(int i, int i2, int i3, SensorState sensorState) {
        return (SensorState) this.mStates.put(new TypeUserSensor(i, i2, i3), sensorState);
    }

    public class TypeUserSensor {
        public int mSensor;
        public int mType;
        public int mUserId;

        public TypeUserSensor(int i, int i2, int i3) {
            this.mType = i;
            this.mUserId = i2;
            this.mSensor = i3;
        }

        public TypeUserSensor(TypeUserSensor typeUserSensor) {
            this(typeUserSensor.mType, typeUserSensor.mUserId, typeUserSensor.mSensor);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TypeUserSensor)) {
                return false;
            }
            TypeUserSensor typeUserSensor = (TypeUserSensor) obj;
            return this.mType == typeUserSensor.mType && this.mUserId == typeUserSensor.mUserId && this.mSensor == typeUserSensor.mSensor;
        }

        public int hashCode() {
            return (((this.mType * 31) + this.mUserId) * 31) + this.mSensor;
        }
    }

    public void schedulePersist() {
        int size = this.mStates.size();
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < size; i++) {
            arrayMap.put(new TypeUserSensor((TypeUserSensor) this.mStates.keyAt(i)), new SensorState((SensorState) this.mStates.valueAt(i)));
        }
        IoThread.getHandler().sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: com.android.server.sensorprivacy.PersistedState$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((PersistedState) obj).persist((ArrayMap) obj2);
            }
        }, this, arrayMap));
    }

    public final void persist(ArrayMap arrayMap) {
        FileOutputStream startWrite;
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mAtomicFile.startWrite();
        } catch (IOException e) {
            e = e;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "sensor-privacy");
            resolveSerializer.attributeInt((String) null, "persistence-version", 2);
            resolveSerializer.attributeInt((String) null, "version", 2);
            for (int i = 0; i < arrayMap.size(); i++) {
                TypeUserSensor typeUserSensor = (TypeUserSensor) arrayMap.keyAt(i);
                SensorState sensorState = (SensorState) arrayMap.valueAt(i);
                if (typeUserSensor.mType == 1) {
                    resolveSerializer.startTag((String) null, "sensor-state");
                    resolveSerializer.attributeInt((String) null, "toggle-type", typeUserSensor.mType);
                    resolveSerializer.attributeInt((String) null, "user-id", typeUserSensor.mUserId);
                    resolveSerializer.attributeInt((String) null, "sensor", typeUserSensor.mSensor);
                    resolveSerializer.attributeInt((String) null, "state-type", sensorState.getState());
                    resolveSerializer.attributeLong((String) null, "last-change", sensorState.getLastChange());
                    resolveSerializer.endTag((String) null, "sensor-state");
                }
            }
            resolveSerializer.endTag((String) null, "sensor-privacy");
            resolveSerializer.endDocument();
            this.mAtomicFile.finishWrite(startWrite);
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            Log.e(LOG_TAG, "Caught an exception persisting the sensor privacy state: ", e);
            this.mAtomicFile.failWrite(fileOutputStream);
        }
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream) {
        SparseArray sparseArray = new SparseArray();
        int size = this.mStates.size();
        for (int i = 0; i < size; i++) {
            int i2 = ((TypeUserSensor) this.mStates.keyAt(i)).mType;
            int i3 = ((TypeUserSensor) this.mStates.keyAt(i)).mUserId;
            int i4 = ((TypeUserSensor) this.mStates.keyAt(i)).mSensor;
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(i3);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.put(i3, sparseArray2);
            }
            sparseArray2.put(i4, new Pair(Integer.valueOf(i2), (SensorState) this.mStates.valueAt(i)));
        }
        dualDumpOutputStream.write("storage_implementation", 1138166333444L, SensorPrivacyStateControllerImpl.class.getName());
        int size2 = sparseArray.size();
        for (int i5 = 0; i5 < size2; i5++) {
            int keyAt = sparseArray.keyAt(i5);
            long start = dualDumpOutputStream.start("users", 2246267895811L);
            long j = 1120986464257L;
            dualDumpOutputStream.write("user_id", 1120986464257L, keyAt);
            SparseArray sparseArray3 = (SparseArray) sparseArray.valueAt(i5);
            int i6 = 0;
            for (int size3 = sparseArray3.size(); i6 < size3; size3 = size3) {
                int keyAt2 = sparseArray3.keyAt(i6);
                int intValue = ((Integer) ((Pair) sparseArray3.valueAt(i6)).first).intValue();
                SensorState sensorState = (SensorState) ((Pair) sparseArray3.valueAt(i6)).second;
                long start2 = dualDumpOutputStream.start("sensors", 2246267895812L);
                dualDumpOutputStream.write("sensor", j, keyAt2);
                long start3 = dualDumpOutputStream.start("toggles", 2246267895810L);
                dualDumpOutputStream.write("toggle_type", 1159641169924L, intValue);
                dualDumpOutputStream.write("state_type", 1159641169925L, sensorState.getState());
                dualDumpOutputStream.write("last_change", 1112396529667L, sensorState.getLastChange());
                dualDumpOutputStream.end(start3);
                dualDumpOutputStream.end(start2);
                i6++;
                j = 1120986464257L;
                size2 = size2;
            }
            dualDumpOutputStream.end(start);
        }
    }

    public void forEachKnownState(QuadConsumer quadConsumer) {
        int size = this.mStates.size();
        for (int i = 0; i < size; i++) {
            TypeUserSensor typeUserSensor = (TypeUserSensor) this.mStates.keyAt(i);
            quadConsumer.accept(Integer.valueOf(typeUserSensor.mType), Integer.valueOf(typeUserSensor.mUserId), Integer.valueOf(typeUserSensor.mSensor), (SensorState) this.mStates.valueAt(i));
        }
    }

    public class PVersion0 {
        public SparseArray mIndividualEnabled;

        public final void upgrade() {
        }

        public PVersion0(int i) {
            this.mIndividualEnabled = new SparseArray();
            if (i != 0) {
                throw new RuntimeException("Only version 0 supported");
            }
        }

        public final void addState(int i, boolean z) {
            this.mIndividualEnabled.put(i, new SensorState(z));
        }
    }

    public class PVersion1 {
        public SparseArray mIndividualEnabled;

        public final void upgrade() {
        }

        public PVersion1(int i) {
            this.mIndividualEnabled = new SparseArray();
            if (i != 1) {
                throw new RuntimeException("Only version 1 supported");
            }
        }

        public static PVersion1 fromPVersion0(PVersion0 pVersion0) {
            pVersion0.upgrade();
            PVersion1 pVersion1 = new PVersion1(1);
            int[] iArr = {0};
            try {
                iArr = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserIds();
            } catch (Exception e) {
                Log.e(PersistedState.LOG_TAG, "Unable to get users.", e);
            }
            for (int i : iArr) {
                for (int i2 = 0; i2 < pVersion0.mIndividualEnabled.size(); i2++) {
                    pVersion1.addState(i, pVersion0.mIndividualEnabled.keyAt(i2), ((SensorState) pVersion0.mIndividualEnabled.valueAt(i2)).isEnabled());
                }
            }
            return pVersion1;
        }

        public final void addState(int i, int i2, boolean z) {
            SparseArray sparseArray = (SparseArray) this.mIndividualEnabled.get(i, new SparseArray());
            this.mIndividualEnabled.put(i, sparseArray);
            sparseArray.put(i2, new SensorState(z));
        }
    }

    public class PVersion2 {
        public ArrayMap mStates;

        public PVersion2(int i) {
            this.mStates = new ArrayMap();
            if (i != 2) {
                throw new RuntimeException("Only version 2 supported");
            }
        }

        public static PVersion2 fromPVersion1(PVersion1 pVersion1) {
            pVersion1.upgrade();
            PVersion2 pVersion2 = new PVersion2(2);
            SparseArray sparseArray = pVersion1.mIndividualEnabled;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                SparseArray sparseArray2 = (SparseArray) sparseArray.valueAt(i);
                int size2 = sparseArray2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    int keyAt2 = sparseArray2.keyAt(i2);
                    SensorState sensorState = (SensorState) sparseArray2.valueAt(i2);
                    pVersion2.addState(1, keyAt, keyAt2, sensorState.getState(), sensorState.getLastChange());
                }
            }
            return pVersion2;
        }

        public final void addState(int i, int i2, int i3, int i4, long j) {
            this.mStates.put(new TypeUserSensor(i, i2, i3), new SensorState(i4, j));
        }
    }
}
