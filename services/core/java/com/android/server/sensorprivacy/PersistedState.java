package com.android.server.sensorprivacy;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.SparseArray;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PersistedState {
    public final AtomicFile mAtomicFile;
    public final ArrayMap mStates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PVersion0 {
        public SparseArray mIndividualEnabled;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PVersion1 {
        public final SparseArray mIndividualEnabled = new SparseArray();

        public PVersion1(int i) {
            if (i != 1) {
                throw new RuntimeException("Only version 1 supported");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PVersion2 {
        public final ArrayMap mStates = new ArrayMap();

        public PVersion2(int i) {
            if (i != 2) {
                throw new RuntimeException("Only version 2 supported");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TypeUserSensor {
        public final int mSensor;
        public final int mType;
        public final int mUserId;

        public TypeUserSensor(int i, int i2, int i3) {
            this.mType = i;
            this.mUserId = i2;
            this.mSensor = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TypeUserSensor)) {
                return false;
            }
            TypeUserSensor typeUserSensor = (TypeUserSensor) obj;
            return this.mType == typeUserSensor.mType
                    && this.mUserId == typeUserSensor.mUserId
                    && this.mSensor == typeUserSensor.mSensor;
        }

        public final int hashCode() {
            return (((this.mType * 31) + this.mUserId) * 31) + this.mSensor;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x007c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PersistedState() {
        /*
            Method dump skipped, instructions count: 481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sensorprivacy.PersistedState.<init>():void");
    }

    public static void readPVersion0(TypedXmlPullParser typedXmlPullParser, PVersion0 pVersion0) {
        XmlUtils.nextElement(typedXmlPullParser);
        while (typedXmlPullParser.getEventType() != 1) {
            if ("individual-sensor-privacy".equals(typedXmlPullParser.getName())) {
                pVersion0.mIndividualEnabled.put(
                        XmlUtils.readIntAttribute(typedXmlPullParser, "sensor"),
                        new SensorState(
                                XmlUtils.readBooleanAttribute(typedXmlPullParser, "enabled")));
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
                        int attributeInt2 =
                                typedXmlPullParser.getAttributeInt((String) null, "sensor");
                        boolean attributeBoolean =
                                typedXmlPullParser.getAttributeBoolean((String) null, "enabled");
                        SparseArray sparseArray =
                                (SparseArray)
                                        pVersion1.mIndividualEnabled.get(
                                                attributeInt, new SparseArray());
                        pVersion1.mIndividualEnabled.put(attributeInt, sparseArray);
                        sparseArray.put(attributeInt2, new SensorState(attributeBoolean));
                    }
                }
            }
        }
    }

    public static void readPVersion2(TypedXmlPullParser typedXmlPullParser, PVersion2 pVersion2) {
        while (typedXmlPullParser.getEventType() != 1) {
            XmlUtils.nextElement(typedXmlPullParser);
            if ("sensor-state".equals(typedXmlPullParser.getName())) {
                int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "toggle-type");
                int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "user-id");
                int attributeInt3 = typedXmlPullParser.getAttributeInt((String) null, "sensor");
                int attributeInt4 = typedXmlPullParser.getAttributeInt((String) null, "state-type");
                long attributeLong =
                        typedXmlPullParser.getAttributeLong((String) null, "last-change");
                ArrayMap arrayMap = pVersion2.mStates;
                TypeUserSensor typeUserSensor =
                        new TypeUserSensor(attributeInt, attributeInt2, attributeInt3);
                SensorState sensorState = new SensorState();
                sensorState.mStateType = attributeInt4;
                String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                sensorState.mLastChange = Math.min(SystemClock.elapsedRealtime(), attributeLong);
                arrayMap.put(typeUserSensor, sensorState);
            } else {
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }
}
