package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.p023dh;

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

    static String privateKeyToString(String algorithm, BigInteger x, DHParameters dhParams) {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.lineSeparator();
        BigInteger y = dhParams.getG().modPow(x, dhParams.getP());
        buf.append(algorithm);
        buf.append(" Private Key [").append(generateKeyFingerprint(y, dhParams)).append(NavigationBarInflaterView.SIZE_MOD_END).append(nl);
        buf.append("              Y: ").append(y.toString(16)).append(nl);
        return buf.toString();
    }

    static String publicKeyToString(String algorithm, BigInteger y, DHParameters dhParams) {
        StringBuffer buf = new StringBuffer();
        String nl = Strings.lineSeparator();
        buf.append(algorithm);
        buf.append(" Public Key [").append(generateKeyFingerprint(y, dhParams)).append(NavigationBarInflaterView.SIZE_MOD_END).append(nl);
        buf.append("             Y: ").append(y.toString(16)).append(nl);
        return buf.toString();
    }

    private static String generateKeyFingerprint(BigInteger y, DHParameters dhParams) {
        return new Fingerprint(Arrays.concatenate(y.toByteArray(), dhParams.getP().toByteArray(), dhParams.getG().toByteArray())).toString();
    }
}
