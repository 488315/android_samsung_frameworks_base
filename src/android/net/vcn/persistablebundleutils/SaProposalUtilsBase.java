package android.net.vcn.persistablebundleutils;

import android.net.ipsec.ike.SaProposal;
import android.net.vcn.persistablebundleutils.SaProposalUtilsBase;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
abstract class SaProposalUtilsBase {
    static final String DH_GROUP_KEY = "DH_GROUP_KEY";
    static final String ENCRYPT_ALGO_KEY = "ENCRYPT_ALGO_KEY";
    static final String INTEGRITY_ALGO_KEY = "INTEGRITY_ALGO_KEY";

    SaProposalUtilsBase() {
    }

    static class EncryptionAlgoKeyLenPair {
        private static final String ALGO_KEY = "ALGO_KEY";
        private static final String KEY_LEN_KEY = "KEY_LEN_KEY";
        public final int encryptionAlgo;
        public final int keyLen;

        EncryptionAlgoKeyLenPair(int i, int i2) {
            this.encryptionAlgo = i;
            this.keyLen = i2;
        }

        EncryptionAlgoKeyLenPair(PersistableBundle persistableBundle) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            this.encryptionAlgo = persistableBundle.getInt(ALGO_KEY);
            this.keyLen = persistableBundle.getInt(KEY_LEN_KEY);
        }

        public PersistableBundle toPersistableBundle() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt(ALGO_KEY, this.encryptionAlgo);
            persistableBundle.putInt(KEY_LEN_KEY, this.keyLen);
            return persistableBundle;
        }
    }

    static PersistableBundle toPersistableBundle(SaProposal saProposal) {
        PersistableBundle persistableBundle = new PersistableBundle();
        ArrayList arrayList = new ArrayList();
        for (Pair<Integer, Integer> pair : saProposal.getEncryptionAlgorithms()) {
            arrayList.add(new EncryptionAlgoKeyLenPair(pair.first.intValue(), pair.second.intValue()));
        }
        persistableBundle.putPersistableBundle(ENCRYPT_ALGO_KEY, PersistableBundleUtils.fromList(arrayList, new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.SaProposalUtilsBase$$ExternalSyntheticLambda0
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return ((SaProposalUtilsBase.EncryptionAlgoKeyLenPair) obj).toPersistableBundle();
            }
        }));
        persistableBundle.putIntArray(INTEGRITY_ALGO_KEY, saProposal.getIntegrityAlgorithms().stream().mapToInt(new ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.SaProposalUtilsBase$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray());
        persistableBundle.putIntArray(DH_GROUP_KEY, saProposal.getDhGroups().stream().mapToInt(new ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.SaProposalUtilsBase$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray());
        return persistableBundle;
    }
}
