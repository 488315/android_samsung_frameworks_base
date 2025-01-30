package com.samsung.android.knox.zt.service;

import java.security.cert.Certificate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKeyAttestationHelper {
    boolean attestKey(String str, byte[] bArr, boolean z);

    Certificate[] getCertificateChain(String str);

    boolean setCertificateChain(String str, Certificate[] certificateArr);

    byte[] sign(String str, byte[] bArr);
}
