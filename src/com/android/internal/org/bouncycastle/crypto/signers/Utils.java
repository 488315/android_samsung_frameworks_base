package com.android.internal.org.bouncycastle.crypto.signers;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.constraints.ConstraintUtils;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.DSAKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECKeyParameters;

/* loaded from: classes5.dex */
class Utils {
    Utils() {
    }

    static CryptoServiceProperties getDefaultProperties(String str, DSAKeyParameters dSAKeyParameters, boolean z) {
        return new DefaultServiceProperties(str, ConstraintUtils.bitsOfSecurityFor(dSAKeyParameters.getParameters().getP()), dSAKeyParameters, getPurpose(z));
    }

    static CryptoServiceProperties getDefaultProperties(String str, ECKeyParameters eCKeyParameters, boolean z) {
        return new DefaultServiceProperties(str, ConstraintUtils.bitsOfSecurityFor(eCKeyParameters.getParameters().getCurve()), eCKeyParameters, getPurpose(z));
    }

    static CryptoServiceProperties getDefaultProperties(String str, int i, CipherParameters cipherParameters, boolean z) {
        return new DefaultServiceProperties(str, i, cipherParameters, getPurpose(z));
    }

    static CryptoServicePurpose getPurpose(boolean z) {
        return z ? CryptoServicePurpose.SIGNING : CryptoServicePurpose.VERIFYING;
    }
}
