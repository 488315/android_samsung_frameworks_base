package com.android.server.sensorprivacy;

import android.os.Handler;
import android.util.AtomicFile;

public final class AllSensorStateController {
    public static AllSensorStateController sInstance;
    public final AtomicFile mAtomicFile;
    public boolean mEnabled;
    public SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1 mListener;
    public Handler mListenerHandler;

    /* JADX WARN: Code restructure failed: missing block: B:28:0x003b, code lost:

       r7.mEnabled = com.android.internal.util.XmlUtils.readBooleanAttribute(r2, "enabled", false) | r7.mEnabled;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AllSensorStateController() {
        /*
            r7 = this;
            r7.<init>()
            android.util.AtomicFile r0 = new android.util.AtomicFile
            java.io.File r1 = new java.io.File
            java.io.File r2 = android.os.Environment.getDataSystemDirectory()
            java.lang.String r3 = "sensor_privacy.xml"
            r1.<init>(r2, r3)
            r0.<init>(r1)
            r7.mAtomicFile = r0
            boolean r1 = r0.exists()
            if (r1 != 0) goto L1d
            return
        L1d:
            r1 = 0
            java.io.FileInputStream r0 = r0.openRead()     // Catch: java.lang.Throwable -> L7e
            com.android.modules.utils.TypedXmlPullParser r2 = android.util.Xml.resolvePullParser(r0)     // Catch: java.lang.Throwable -> L45
        L26:
            int r3 = r2.getEventType()     // Catch: java.lang.Throwable -> L45
            r4 = 1
            if (r3 == r4) goto L78
            java.lang.String r3 = r2.getName()     // Catch: java.lang.Throwable -> L45
            java.lang.String r4 = "all-sensor-privacy"
            boolean r4 = r4.equals(r3)     // Catch: java.lang.Throwable -> L45
            java.lang.String r5 = "enabled"
            if (r4 == 0) goto L47
            boolean r3 = r7.mEnabled     // Catch: java.lang.Throwable -> L45
            boolean r2 = com.android.internal.util.XmlUtils.readBooleanAttribute(r2, r5, r1)     // Catch: java.lang.Throwable -> L45
            r2 = r2 | r3
            r7.mEnabled = r2     // Catch: java.lang.Throwable -> L45
            goto L78
        L45:
            r2 = move-exception
            goto L80
        L47:
            java.lang.String r4 = "sensor-privacy"
            boolean r4 = r4.equals(r3)     // Catch: java.lang.Throwable -> L45
            if (r4 == 0) goto L59
            boolean r4 = r7.mEnabled     // Catch: java.lang.Throwable -> L45
            boolean r6 = com.android.internal.util.XmlUtils.readBooleanAttribute(r2, r5, r1)     // Catch: java.lang.Throwable -> L45
            r4 = r4 | r6
            r7.mEnabled = r4     // Catch: java.lang.Throwable -> L45
        L59:
            java.lang.String r4 = "user"
            boolean r3 = r4.equals(r3)     // Catch: java.lang.Throwable -> L45
            if (r3 == 0) goto L74
            java.lang.String r3 = "id"
            r4 = -1
            int r3 = com.android.internal.util.XmlUtils.readIntAttribute(r2, r3, r4)     // Catch: java.lang.Throwable -> L45
            if (r3 != 0) goto L74
            boolean r3 = r7.mEnabled     // Catch: java.lang.Throwable -> L45
            boolean r4 = com.android.internal.util.XmlUtils.readBooleanAttribute(r2, r5)     // Catch: java.lang.Throwable -> L45
            r3 = r3 | r4
            r7.mEnabled = r3     // Catch: java.lang.Throwable -> L45
        L74:
            com.android.internal.util.XmlUtils.nextElement(r2)     // Catch: java.lang.Throwable -> L45
            goto L26
        L78:
            if (r0 == 0) goto L94
            r0.close()     // Catch: java.lang.Throwable -> L7e java.lang.Throwable -> L7e
            goto L94
        L7e:
            r0 = move-exception
            goto L8b
        L80:
            if (r0 == 0) goto L8a
            r0.close()     // Catch: java.lang.Throwable -> L86
            goto L8a
        L86:
            r0 = move-exception
            r2.addSuppressed(r0)     // Catch: java.lang.Throwable -> L7e java.lang.Throwable -> L7e
        L8a:
            throw r2     // Catch: java.lang.Throwable -> L7e java.lang.Throwable -> L7e
        L8b:
            java.lang.String r2 = "AllSensorStateController"
            java.lang.String r3 = "Caught an exception reading the state from storage: "
            android.util.Log.e(r2, r3, r0)
            r7.mEnabled = r1
        L94:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sensorprivacy.AllSensorStateController.<init>():void");
    }
}
