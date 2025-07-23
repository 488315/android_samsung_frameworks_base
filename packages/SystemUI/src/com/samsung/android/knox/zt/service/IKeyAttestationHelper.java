package com.samsung.android.knox.zt.service;

import java.security.cert.Certificate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKeyAttestationHelper {
    boolean attestKey(String str, byte[] bArr, boolean z);

    Certificate[] getCertificateChain(String str);

    boolean setCertificateChain(String str, Certificate[] certificateArr);

    byte[] sign(String str, byte[] bArr);
}
