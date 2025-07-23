package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.cms.SignerInfo;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.io.IOException;

/* loaded from: classes5.dex */
public class CMSPatchKit {
    public static SignerInformation createNonDERSignerInfo(SignerInformation signerInformation) {
        return new DLSignerInformation(signerInformation);
    }

    public static SignerInformation createWithSignatureAlgorithm(SignerInformation signerInformation, AlgorithmIdentifier algorithmIdentifier) {
        return new ModEncAlgSignerInformation(signerInformation, algorithmIdentifier);
    }

    private static class DLSignerInformation extends SignerInformation {
        protected DLSignerInformation(SignerInformation signerInformation) {
            super(signerInformation);
        }

        @Override // com.android.internal.org.bouncycastle.cms.SignerInformation
        public byte[] getEncodedSignedAttributes() throws IOException {
            return this.signedAttributeSet.getEncoded(ASN1Encoding.DL);
        }
    }

    private static class ModEncAlgSignerInformation extends SignerInformation {
        protected ModEncAlgSignerInformation(SignerInformation signerInformation, AlgorithmIdentifier algorithmIdentifier) {
            super(signerInformation, editEncAlg(signerInformation.info, algorithmIdentifier));
        }

        private static SignerInfo editEncAlg(SignerInfo signerInfo, AlgorithmIdentifier algorithmIdentifier) {
            return new SignerInfo(signerInfo.getSID(), signerInfo.getDigestAlgorithm(), signerInfo.getAuthenticatedAttributes(), algorithmIdentifier, signerInfo.getEncryptedDigest(), signerInfo.getUnauthenticatedAttributes());
        }
    }
}
