package com.android.server.sensorprivacy;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.SparseArray;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;

public final class PersistedState {
    public final AtomicFile mAtomicFile;
    public final ArrayMap mStates;

    public final class PVersion0 {
        public SparseArray mIndividualEnabled;
    }

    public final class PVersion1 {
        public final SparseArray mIndividualEnabled = new SparseArray();

        public PVersion1(int i) {
            if (i != 1) {
                throw new RuntimeException("Only version 1 supported");
            }
        }
    }

    public final class PVersion2 {
        public final ArrayMap mStates = new ArrayMap();

        public PVersion2(int i) {
            if (i != 2) {
                throw new RuntimeException("Only version 2 supported");
            }
        }
    }

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
