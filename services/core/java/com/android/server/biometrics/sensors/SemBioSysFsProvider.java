package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public interface SemBioSysFsProvider {
    byte[] readSysFs(String str);

    void writeSysFs(String str, byte[] bArr);
}
