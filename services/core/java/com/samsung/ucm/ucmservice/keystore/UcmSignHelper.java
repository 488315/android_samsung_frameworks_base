package com.samsung.ucm.ucmservice.keystore;

import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;

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
