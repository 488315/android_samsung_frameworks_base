package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.Digest;

/* loaded from: classes5.dex */
class Utils {
    Utils() {
    }

    static CryptoServiceProperties getDefaultProperties(Digest digest, CryptoServicePurpose cryptoServicePurpose) {
        return new DefaultProperties(digest.getDigestSize() * 4, digest.getAlgorithmName(), cryptoServicePurpose);
    }

    static CryptoServiceProperties getDefaultProperties(Digest digest, int i, CryptoServicePurpose cryptoServicePurpose) {
        return new DefaultPropertiesWithPRF(digest.getDigestSize() * 4, i, digest.getAlgorithmName(), cryptoServicePurpose);
    }

    private static class DefaultPropertiesWithPRF implements CryptoServiceProperties {
        private final String algorithmName;
        private final int bitsOfSecurity;
        private final int prfBitsOfSecurity;
        private final CryptoServicePurpose purpose;

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public Object getParams() {
            return null;
        }

        public DefaultPropertiesWithPRF(int i, int i2, String str, CryptoServicePurpose cryptoServicePurpose) {
            this.bitsOfSecurity = i;
            this.prfBitsOfSecurity = i2;
            this.algorithmName = str;
            this.purpose = cryptoServicePurpose;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public int bitsOfSecurity() {
            if (this.purpose == CryptoServicePurpose.PRF) {
                return this.prfBitsOfSecurity;
            }
            return this.bitsOfSecurity;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public String getServiceName() {
            return this.algorithmName;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public CryptoServicePurpose getPurpose() {
            return this.purpose;
        }
    }

    private static class DefaultProperties implements CryptoServiceProperties {
        private final String algorithmName;
        private final int bitsOfSecurity;
        private final CryptoServicePurpose purpose;

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public Object getParams() {
            return null;
        }

        public DefaultProperties(int i, String str, CryptoServicePurpose cryptoServicePurpose) {
            this.bitsOfSecurity = i;
            this.algorithmName = str;
            this.purpose = cryptoServicePurpose;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public int bitsOfSecurity() {
            return this.bitsOfSecurity;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public String getServiceName() {
            return this.algorithmName;
        }

        @Override // com.android.internal.org.bouncycastle.crypto.CryptoServiceProperties
        public CryptoServicePurpose getPurpose() {
            return this.purpose;
        }
    }
}
