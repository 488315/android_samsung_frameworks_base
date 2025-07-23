package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CMSSignedDataGenerator extends CMSSignedGenerator {
    private boolean isDefiniteLength;
    private List signerInfs;

    public CMSSignedDataGenerator() {
        this.signerInfs = new ArrayList();
        this.isDefiniteLength = false;
    }

    public CMSSignedDataGenerator(DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        super(digestAlgorithmIdentifierFinder);
        this.signerInfs = new ArrayList();
        this.isDefiniteLength = false;
    }

    public void setDefiniteLengthEncoding(boolean z) {
        this.isDefiniteLength = z;
    }

    public CMSSignedData generate(CMSTypedData cMSTypedData) throws CMSException {
        return generate(cMSTypedData, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.internal.org.bouncycastle.cms.CMSSignedData generate(com.android.internal.org.bouncycastle.cms.CMSTypedData r12, boolean r13) throws com.android.internal.org.bouncycastle.cms.CMSException {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.cms.CMSSignedDataGenerator.generate(com.android.internal.org.bouncycastle.cms.CMSTypedData, boolean):com.android.internal.org.bouncycastle.cms.CMSSignedData");
    }

    public SignerInformationStore generateCounterSigners(SignerInformation signerInformation) throws CMSException {
        return generate(new CMSProcessableByteArray(null, signerInformation.getSignature()), false).getSignerInfos();
    }
}
