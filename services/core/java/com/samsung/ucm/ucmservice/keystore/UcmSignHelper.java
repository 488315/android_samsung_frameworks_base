package com.samsung.ucm.ucmservice.keystore;

import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UcmSignHelper {
    public final String algorithm;

    public UcmSignHelper(String str) {
        this.algorithm = str;
    }

    public static String getMdAlgorithm(String str) {
        if (str.toLowerCase().endsWith("withrsa")) {
            str = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(7, 0, str);
        } else if (str.toLowerCase().endsWith("withecdsa")) {
            str = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(9, 0, str);
        }
        return str.toUpperCase().replace("SHA", "SHA-");
    }

    public String getProcessAlgorithm() {
        return this.algorithm;
    }

    public boolean isEncryptFunction() {
        return this instanceof UcmSignHelperPkcs1;
    }

    public byte[] processInput(byte[] bArr) {
        return bArr;
    }
}
