package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.fingerprint.Fingerprint;
import android.util.Slog;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.biometrics.sensors.BiometricUserState;

import java.util.ArrayList;
import java.util.Iterator;

public final class FingerprintUserState extends BiometricUserState {
    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final void doWriteState(TypedXmlSerializer typedXmlSerializer) {
        ArrayList copy;
        synchronized (this) {
            copy = getCopy(this.mBiometrics);
        }
        typedXmlSerializer.startTag((String) null, "fingerprints");
        int size = copy.size();
        for (int i = 0; i < size; i++) {
            Fingerprint fingerprint = (Fingerprint) copy.get(i);
            typedXmlSerializer.startTag((String) null, "fingerprint");
            typedXmlSerializer.attributeInt(
                    (String) null, "fingerId", fingerprint.getBiometricId());
            typedXmlSerializer.attribute((String) null, "name", fingerprint.getName().toString());
            typedXmlSerializer.attributeInt((String) null, "groupId", fingerprint.getGroupId());
            typedXmlSerializer.attributeLong((String) null, "deviceId", fingerprint.getDeviceId());
            typedXmlSerializer.attributeInt(
                    (String) null, "duplicatedCount", fingerprint.semGetDuplicatedImageCount());
            typedXmlSerializer.endTag((String) null, "fingerprint");
        }
        typedXmlSerializer.endTag((String) null, "fingerprints");
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final String getBiometricsTag() {
        return "fingerprints";
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final ArrayList getCopy(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Fingerprint fingerprint = (Fingerprint) it.next();
            arrayList2.add(
                    new Fingerprint(
                            fingerprint.getName(),
                            fingerprint.getGroupId(),
                            fingerprint.getBiometricId(),
                            fingerprint.getDeviceId(),
                            fingerprint.semGetDuplicatedImageCount()));
        }
        return arrayList2;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final String getLegacyFileName() {
        return "settings_fingerprint.xml";
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final int getNameTemplateResource() {
        return 17042921;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final void parseBiometricsLocked(TypedXmlPullParser typedXmlPullParser) {
        int i;
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("fingerprint")) {
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "groupId");
                int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "fingerId");
                long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "deviceId");
                try {
                    i = typedXmlPullParser.getAttributeInt((String) null, "duplicatedCount");
                } catch (Exception e) {
                    Slog.e("FingerprintState", "parseBiometricsLocked : failed", e);
                    i = -1;
                }
                this.mBiometrics.add(
                        new Fingerprint(
                                attributeValue, attributeInt, attributeInt2, attributeLong, i));
            }
        }
    }
}
