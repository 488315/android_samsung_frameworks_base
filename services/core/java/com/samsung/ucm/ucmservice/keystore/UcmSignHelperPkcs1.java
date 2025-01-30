package com.samsung.ucm.ucmservice.keystore;

import com.att.iqi.lib.metrics.hw.HwConstants;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes2.dex */
public class UcmSignHelperPkcs1 extends UcmSignHelper {
    public static final HashMap algorithmMap;
    public static final HashSet algorithmSet;

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public String getProcessAlgorithm() {
        return "RSA/ECB/PKCS1Padding";
    }

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public boolean isEncryptFunction() {
        return true;
    }

    static {
        HashMap hashMap = new HashMap();
        algorithmMap = hashMap;
        hashMap.put("md5withrsa", new byte[]{48, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED, 48, 12, 6, 8, 42, -122, 72, -122, -9, 13, 2, 5, 5, 0, 4, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED});
        hashMap.put("sha1withrsa", new byte[]{48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20});
        hashMap.put("sha224withrsa", new byte[]{48, 45, 48, 13, 6, 9, 96, -122, 72, 1, 101, 3, 4, 2, 4, 5, 0, 4, 28});
        hashMap.put("sha256withrsa", new byte[]{48, 49, 48, 13, 6, 9, 96, -122, 72, 1, 101, 3, 4, 2, 1, 5, 0, 4, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED});
        hashMap.put("sha384withrsa", new byte[]{48, 65, 48, 13, 6, 9, 96, -122, 72, 1, 101, 3, 4, 2, 2, 5, 0, 4, 48});
        hashMap.put("sha512withrsa", new byte[]{48, 81, 48, 13, 6, 9, 96, -122, 72, 1, 101, 3, 4, 2, 3, 5, 0, 4, 64});
        HashSet hashSet = new HashSet();
        algorithmSet = hashSet;
        hashSet.addAll(hashMap.keySet());
    }

    public static boolean isSupportedAlgorithm(String str) {
        return algorithmSet.contains(str.toLowerCase());
    }

    public UcmSignHelperPkcs1(String str) {
        super(str);
    }

    @Override // com.samsung.ucm.ucmservice.keystore.UcmSignHelper
    public byte[] processInput(byte[] bArr) {
        byte[] bArr2 = (byte[]) algorithmMap.get(this.algorithm.toLowerCase());
        byte[] digest = MessageDigest.getInstance(getMdAlgorithm(this.algorithm)).digest(bArr);
        if (digest == null || bArr2 == null) {
            return null;
        }
        byte[] bArr3 = new byte[bArr2.length + digest.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(digest, 0, bArr3, bArr2.length + 0, digest.length);
        return bArr3;
    }
}
