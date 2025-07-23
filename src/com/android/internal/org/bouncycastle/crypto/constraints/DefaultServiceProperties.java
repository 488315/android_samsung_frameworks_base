package com.android.internal.org.bouncycastle.crypto.constraints;

import com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;

/* loaded from: classes5.dex */
public class DefaultServiceProperties implements CryptoServiceProperties {
    private final String algorithm;
    private final int bitsOfSecurity;
    private final Object params;
    private final CryptoServicePurpose purpose;

    public DefaultServiceProperties(String str, int i) {
        this(str, i, null, CryptoServicePurpose.ANY);
    }

    public DefaultServiceProperties(String str, int i, Object obj) {
        this(str, i, obj, CryptoServicePurpose.ANY);
    }

    public DefaultServiceProperties(String str, int i, Object obj, CryptoServicePurpose cryptoServicePurpose) {
        this.algorithm = str;
        this.bitsOfSecurity = i;
        this.params = obj;
        if (obj instanceof CryptoServicePurpose) {
            throw new IllegalArgumentException("params should not be CryptoServicePurpose");
        }
        this.purpose = cryptoServicePurpose;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
    public int bitsOfSecurity() {
        return this.bitsOfSecurity;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
    public String getServiceName() {
        return this.algorithm;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
    public CryptoServicePurpose getPurpose() {
        return this.purpose;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
    public Object getParams() {
        return this.params;
    }
}
