package android.net.vcn.persistablebundleutils;

import android.net.ipsec.ike.IkeSaProposal;
import android.net.vcn.persistablebundleutils.SaProposalUtilsBase;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import java.util.Objects;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final class IkeSaProposalUtils extends SaProposalUtilsBase {
    private static final String PRF_KEY = "PRF_KEY";

    public static PersistableBundle toPersistableBundle(IkeSaProposal ikeSaProposal) {
        PersistableBundle persistableBundle = SaProposalUtilsBase.toPersistableBundle(ikeSaProposal);
        persistableBundle.putIntArray(PRF_KEY, ikeSaProposal.getPseudorandomFunctions().stream().mapToInt(new ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.IkeSaProposalUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) obj).intValue();
            }
        }).toArray());
        return persistableBundle;
    }

    public static IkeSaProposal fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        IkeSaProposal.Builder builder = new IkeSaProposal.Builder();
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
        int[] intArray3 = persistableBundle.getIntArray(PRF_KEY);
        Objects.requireNonNull(intArray3, "PRF array was null");
        for (int i3 : intArray3) {
            builder.addPseudorandomFunction(i3);
        }
        return builder.build();
    }
}
