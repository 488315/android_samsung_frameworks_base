package com.android.internal.org.bouncycastle.jcajce.provider.digest;

import com.android.internal.org.bouncycastle.crypto.digests.Haraka256Digest;
import com.android.internal.org.bouncycastle.crypto.digests.Haraka512Digest;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;

/* loaded from: classes5.dex */
public class Haraka {
    private Haraka() {
    }

    public static class Digest256 extends BCMessageDigest implements Cloneable {
        public Digest256() {
            super(new Haraka256Digest());
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() throws CloneNotSupportedException {
            Digest256 digest256 = (Digest256) super.clone();
            digest256.digest = new Haraka256Digest((Haraka256Digest) this.digest);
            return digest256;
        }
    }

    public static class Digest512 extends BCMessageDigest implements Cloneable {
        public Digest512() {
            super(new Haraka512Digest());
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public Object clone() throws CloneNotSupportedException {
            Digest512 digest512 = (Digest512) super.clone();
            digest512.digest = new Haraka512Digest((Haraka512Digest) this.digest);
            return digest512;
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String PREFIX = Haraka.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            sb.append(str);
            sb.append("$Digest256");
            configurableProvider.addAlgorithm("MessageDigest.HARAKA-256", sb.toString());
            configurableProvider.addAlgorithm("MessageDigest.HARAKA-512", str + "$Digest512");
        }
    }
}
