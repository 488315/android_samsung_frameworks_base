package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Fingerprint;
import com.android.internal.org.bouncycastle.util.Strings;
import java.math.BigInteger;

/* loaded from: classes5.dex */
class DHUtil {
    DHUtil() {
    }

    static String privateKeyToString(String str, BigInteger bigInteger, DHParameters dHParameters) {
        StringBuffer stringBuffer = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        BigInteger modPow = dHParameters.getG().modPow(bigInteger, dHParameters.getP());
        stringBuffer.append(str);
        stringBuffer.append(" Private Key [").append(generateKeyFingerprint(modPow, dHParameters)).append(NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("              Y: ").append(modPow.toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    static String publicKeyToString(String str, BigInteger bigInteger, DHParameters dHParameters) {
        StringBuffer stringBuffer = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        stringBuffer.append(str);
        stringBuffer.append(" Public Key [").append(generateKeyFingerprint(bigInteger, dHParameters)).append(NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("             Y: ").append(bigInteger.toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    private static String generateKeyFingerprint(BigInteger bigInteger, DHParameters dHParameters) {
        return new Fingerprint(Arrays.concatenate(bigInteger.toByteArray(), dHParameters.getP().toByteArray(), dHParameters.getG().toByteArray())).toString();
    }
}
