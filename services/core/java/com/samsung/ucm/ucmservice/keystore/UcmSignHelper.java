package com.samsung.ucm.ucmservice.keystore;

/* loaded from: classes2.dex */
public abstract class UcmSignHelper {
    public String algorithm;

    public boolean isEncryptFunction() {
        return false;
    }

    public byte[] processInput(byte[] bArr) {
        return bArr;
    }

    public UcmSignHelper(String str) {
        this.algorithm = str;
    }

    public String getProcessAlgorithm() {
        return this.algorithm;
    }

    public String getMdAlgorithm(String str) {
        if (str.toLowerCase().endsWith("withrsa")) {
            str = str.substring(0, str.length() - 7);
        } else if (str.toLowerCase().endsWith("withecdsa")) {
            str = str.substring(0, str.length() - 9);
        }
        return convertMDAlgorithm(str);
    }

    public String convertMDAlgorithm(String str) {
        return str.toUpperCase().replace("SHA", "SHA-");
    }
}
