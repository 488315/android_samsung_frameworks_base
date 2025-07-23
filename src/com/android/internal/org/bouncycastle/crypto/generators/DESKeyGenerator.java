package com.android.internal.org.bouncycastle.crypto.generators;

import com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.DESParameters;

/* loaded from: classes5.dex */
public class DESKeyGenerator extends CipherKeyGenerator {
    @Override // com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        super.init(keyGenerationParameters);
        if (this.strength == 0 || this.strength == 7) {
            this.strength = 8;
        } else if (this.strength != 8) {
            throw new IllegalArgumentException("DES key must be 64 bits long.");
        }
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties("DESKeyGen", 56, null, CryptoServicePurpose.KEYGEN));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator
    public byte[] generateKey() {
        byte[] bArr = new byte[8];
        do {
            this.random.nextBytes(bArr);
            DESParameters.setOddParity(bArr);
        } while (DESParameters.isWeakKey(bArr, 0));
        return bArr;
    }
}
