package android.net.vcn.persistablebundleutils;

import android.net.ipsec.ike.ChildSaProposal;
import android.net.vcn.persistablebundleutils.SaProposalUtilsBase;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ChildSaProposalUtils extends SaProposalUtilsBase {
    public static PersistableBundle toPersistableBundle(ChildSaProposal childSaProposal) {
        return SaProposalUtilsBase.toPersistableBundle(childSaProposal);
    }

    public static ChildSaProposal fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        ChildSaProposal.Builder builder = new ChildSaProposal.Builder();
        PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle("ENCRYPT_ALGO_KEY");
        Objects.requireNonNull(persistableBundle2, "Encryption algo bundle was null");
        for (SaProposalUtilsBase.EncryptionAlgoKeyLenPair encryptionAlgoKeyLenPair : PersistableBundleUtils.toList(persistableBundle2, new ChildSaProposalUtils$$ExternalSyntheticLambda0())) {
            builder.addEncryptionAlgorithm(encryptionAlgoKeyLenPair.encryptionAlgo, encryptionAlgoKeyLenPair.keyLen);
        }
        int[] intArray = persistableBundle.getIntArray("INTEGRITY_ALGO_KEY");
        Objects.requireNonNull(intArray, "Integrity algo array was null");
        for (int i : intArray) {
            builder.addIntegrityAlgorithm(i);
        }
        int[] intArray2 = persistableBundle.getIntArray("DH_GROUP_KEY");
        Objects.requireNonNull(intArray2, "DH Group array was null");
        for (int i2 : intArray2) {
            builder.addDhGroup(i2);
        }
        return builder.build();
    }
}
