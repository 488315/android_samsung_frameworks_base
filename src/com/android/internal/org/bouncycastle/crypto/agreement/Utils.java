package com.android.internal.org.bouncycastle.crypto.agreement;

import com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.constraints.ConstraintUtils;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECKeyParameters;

/* loaded from: classes5.dex */
class Utils {
    Utils() {
    }

    static CryptoServiceProperties getDefaultProperties(String str, ECKeyParameters eCKeyParameters) {
        return new DefaultServiceProperties(str, ConstraintUtils.bitsOfSecurityFor(eCKeyParameters.getParameters().getCurve()), eCKeyParameters, CryptoServicePurpose.AGREEMENT);
    }

    static CryptoServiceProperties getDefaultProperties(String str, DHKeyParameters dHKeyParameters) {
        return new DefaultServiceProperties(str, ConstraintUtils.bitsOfSecurityFor(dHKeyParameters.getParameters().getP()), dHKeyParameters, CryptoServicePurpose.AGREEMENT);
    }
}
