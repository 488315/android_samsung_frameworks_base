package android.net.vcn.persistablebundleutils;

import android.net.InetAddresses;
import android.net.ipsec.ike.IkeTrafficSelector;
import android.os.PersistableBundle;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class IkeTrafficSelectorUtils {
    private static final String END_ADDRESS_KEY = "END_ADDRESS_KEY";
    private static final String END_PORT_KEY = "END_PORT_KEY";
    private static final String START_ADDRESS_KEY = "START_ADDRESS_KEY";
    private static final String START_PORT_KEY = "START_PORT_KEY";

    public static IkeTrafficSelector fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        int i = persistableBundle.getInt(START_PORT_KEY);
        int i2 = persistableBundle.getInt(END_PORT_KEY);
        String string = persistableBundle.getString(START_ADDRESS_KEY);
        String string2 = persistableBundle.getString(END_ADDRESS_KEY);
        Objects.requireNonNull(string, "startAddress was null");
        Objects.requireNonNull(string, "endAddress was null");
        return new IkeTrafficSelector(i, i2, InetAddresses.parseNumericAddress(string), InetAddresses.parseNumericAddress(string2));
    }

    public static PersistableBundle toPersistableBundle(IkeTrafficSelector ikeTrafficSelector) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(START_PORT_KEY, ikeTrafficSelector.startPort);
        persistableBundle.putInt(END_PORT_KEY, ikeTrafficSelector.endPort);
        persistableBundle.putString(START_ADDRESS_KEY, ikeTrafficSelector.startingAddress.getHostAddress());
        persistableBundle.putString(END_ADDRESS_KEY, ikeTrafficSelector.endingAddress.getHostAddress());
        return persistableBundle;
    }
}
