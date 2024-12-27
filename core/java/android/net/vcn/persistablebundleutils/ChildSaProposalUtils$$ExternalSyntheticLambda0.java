package android.net.vcn.persistablebundleutils;

import android.os.PersistableBundle;

import com.android.server.vcn.repackaged.util.PersistableBundleUtils;

public final /* synthetic */ class ChildSaProposalUtils$$ExternalSyntheticLambda0
        implements PersistableBundleUtils.Deserializer {
    @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
    public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
        return new SaProposalUtilsBase.EncryptionAlgoKeyLenPair(persistableBundle);
    }
}
