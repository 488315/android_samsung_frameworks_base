package com.samsung.android.knox.zt.service;

import java.security.cert.Certificate;

/* loaded from: classes4.dex */
public interface IKeyAttestationHelper {
    boolean attestKey(String str, byte[] bArr, boolean z);

    Certificate[] getCertificateChain(String str);

    boolean setCertificateChain(String str, Certificate[] certificateArr);

    byte[] sign(String str, byte[] bArr);
}
