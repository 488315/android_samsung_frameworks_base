package com.android.server.biometrics.sensors.face;

import android.R;
import android.hardware.face.Face;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.biometrics.sensors.BiometricUserState;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FaceUserState extends BiometricUserState {
    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final void doWriteState(TypedXmlSerializer typedXmlSerializer) {
        ArrayList copy;
        synchronized (this) {
            copy = getCopy(this.mBiometrics);
        }
        typedXmlSerializer.startTag((String) null, "faces");
        int size = copy.size();
        for (int i = 0; i < size; i++) {
            Face face = (Face) copy.get(i);
            typedXmlSerializer.startTag((String) null, "face");
            typedXmlSerializer.attributeInt((String) null, "faceId", face.getBiometricId());
            typedXmlSerializer.attribute((String) null, "name", face.getName().toString());
            typedXmlSerializer.attributeLong((String) null, "deviceId", face.getDeviceId());
            typedXmlSerializer.endTag((String) null, "face");
        }
        typedXmlSerializer.endTag((String) null, "faces");
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final String getBiometricsTag() {
        return "faces";
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final ArrayList getCopy(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Face face = (Face) it.next();
            arrayList2.add(new Face(face.getName(), face.getBiometricId(), face.getDeviceId()));
        }
        return arrayList2;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final String getLegacyFileName() {
        return "settings_face.xml";
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final int getNameTemplateResource() {
        return R.string.lock_pattern_view_aspect;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    public final void parseBiometricsLocked(TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("face")) {
                this.mBiometrics.add(
                        new Face(
                                typedXmlPullParser.getAttributeValue((String) null, "name"),
                                typedXmlPullParser.getAttributeInt((String) null, "faceId"),
                                typedXmlPullParser.getAttributeLong((String) null, "deviceId")));
            }
        }
    }
}
