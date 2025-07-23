package android.net.vcn.persistablebundleutils;

import android.net.vcn.persistablebundleutils.SaProposalUtilsBase;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ChildSaProposalUtils$$ExternalSyntheticLambda0 implements PersistableBundleUtils.Deserializer {
    @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
    public final Object fromPersistableBundle(PersistableBundle persistableBundle) {
        return new SaProposalUtilsBase.EncryptionAlgoKeyLenPair(persistableBundle);
    }
}
