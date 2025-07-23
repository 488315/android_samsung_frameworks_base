package android.net.vcn.persistablebundleutils;

import android.net.ipsec.ike.IkeTunnelConnectionParams;
import android.os.PersistableBundle;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TunnelConnectionParamsUtils {
    private static final int EXPECTED_BUNDLE_KEY_CNT = 1;
    private static final String PARAMS_TYPE_IKE = "IKE";

    public static PersistableBundle toPersistableBundle(IkeTunnelConnectionParams ikeTunnelConnectionParams) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putPersistableBundle(PARAMS_TYPE_IKE, IkeTunnelConnectionParamsUtils.serializeIkeParams(ikeTunnelConnectionParams));
        return persistableBundle;
    }

    public static IkeTunnelConnectionParams fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        if (persistableBundle.keySet().size() != 1) {
            throw new IllegalArgumentException(String.format("Expect PersistableBundle to have %d element but found: %s", 1, persistableBundle.keySet()));
        }
        if (persistableBundle.get(PARAMS_TYPE_IKE) != null) {
            return IkeTunnelConnectionParamsUtils.deserializeIkeParams(persistableBundle.getPersistableBundle(PARAMS_TYPE_IKE));
        }
        throw new IllegalArgumentException("Invalid Tunnel Connection Params type " + persistableBundle.keySet().iterator().next());
    }

    private static final class IkeTunnelConnectionParamsUtils {
        private static final String CHILD_PARAMS_KEY = "CHILD_PARAMS_KEY";
        private static final String IKE_PARAMS_KEY = "IKE_PARAMS_KEY";

        private IkeTunnelConnectionParamsUtils() {
        }

        public static PersistableBundle serializeIkeParams(IkeTunnelConnectionParams ikeTunnelConnectionParams) {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putPersistableBundle(IKE_PARAMS_KEY, IkeSessionParamsUtils.toPersistableBundle(ikeTunnelConnectionParams.getIkeSessionParams()));
            persistableBundle.putPersistableBundle(CHILD_PARAMS_KEY, TunnelModeChildSessionParamsUtils.toPersistableBundle(ikeTunnelConnectionParams.getTunnelModeChildSessionParams()));
            return persistableBundle;
        }

        public static IkeTunnelConnectionParams deserializeIkeParams(PersistableBundle persistableBundle) {
            PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(IKE_PARAMS_KEY);
            PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(CHILD_PARAMS_KEY);
            Objects.requireNonNull(persistableBundle2, "IkeSessionParams was null");
            Objects.requireNonNull(persistableBundle2, "TunnelModeChildSessionParams was null");
            return new IkeTunnelConnectionParams(IkeSessionParamsUtils.fromPersistableBundle(persistableBundle2), TunnelModeChildSessionParamsUtils.fromPersistableBundle(persistableBundle3));
        }
    }
}
