package android.net.vcn.persistablebundleutils;

import android.net.InetAddresses;
import android.net.ipsec.ike.IkeDerAsn1DnIdentification;
import android.net.ipsec.ike.IkeFqdnIdentification;
import android.net.ipsec.ike.IkeIdentification;
import android.net.ipsec.ike.IkeIpv4AddrIdentification;
import android.net.ipsec.ike.IkeIpv6AddrIdentification;
import android.net.ipsec.ike.IkeKeyIdIdentification;
import android.net.ipsec.ike.IkeRfc822AddrIdentification;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Objects;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes3.dex */
public final class IkeIdentificationUtils {
    private static final String DER_ASN1_DN_KEY = "DER_ASN1_DN_KEY";
    private static final String FQDN_KEY = "FQDN_KEY";
    private static final int ID_TYPE_DER_ASN1_DN = 1;
    private static final int ID_TYPE_FQDN = 2;
    private static final int ID_TYPE_IPV4_ADDR = 3;
    private static final int ID_TYPE_IPV6_ADDR = 4;
    private static final String ID_TYPE_KEY = "ID_TYPE_KEY";
    private static final int ID_TYPE_KEY_ID = 5;
    private static final int ID_TYPE_RFC822_ADDR = 6;
    private static final String IP4_ADDRESS_KEY = "IP4_ADDRESS_KEY";
    private static final String IP6_ADDRESS_KEY = "IP6_ADDRESS_KEY";
    private static final String KEY_ID_KEY = "KEY_ID_KEY";
    private static final String RFC822_ADDRESS_KEY = "RFC822_ADDRESS_KEY";

    public static PersistableBundle toPersistableBundle(IkeIdentification ikeIdentification) {
        if (ikeIdentification instanceof IkeDerAsn1DnIdentification) {
            PersistableBundle createPersistableBundle = createPersistableBundle(1);
            createPersistableBundle.putPersistableBundle(DER_ASN1_DN_KEY, PersistableBundleUtils.fromByteArray(((IkeDerAsn1DnIdentification) ikeIdentification).derAsn1Dn.getEncoded()));
            return createPersistableBundle;
        }
        if (ikeIdentification instanceof IkeFqdnIdentification) {
            PersistableBundle createPersistableBundle2 = createPersistableBundle(2);
            createPersistableBundle2.putString(FQDN_KEY, ((IkeFqdnIdentification) ikeIdentification).fqdn);
            return createPersistableBundle2;
        }
        if (ikeIdentification instanceof IkeIpv4AddrIdentification) {
            PersistableBundle createPersistableBundle3 = createPersistableBundle(3);
            createPersistableBundle3.putString(IP4_ADDRESS_KEY, ((IkeIpv4AddrIdentification) ikeIdentification).ipv4Address.getHostAddress());
            return createPersistableBundle3;
        }
        if (ikeIdentification instanceof IkeIpv6AddrIdentification) {
            PersistableBundle createPersistableBundle4 = createPersistableBundle(4);
            createPersistableBundle4.putString(IP6_ADDRESS_KEY, ((IkeIpv6AddrIdentification) ikeIdentification).ipv6Address.getHostAddress());
            return createPersistableBundle4;
        }
        if (ikeIdentification instanceof IkeKeyIdIdentification) {
            PersistableBundle createPersistableBundle5 = createPersistableBundle(5);
            createPersistableBundle5.putPersistableBundle(KEY_ID_KEY, PersistableBundleUtils.fromByteArray(((IkeKeyIdIdentification) ikeIdentification).keyId));
            return createPersistableBundle5;
        }
        if (ikeIdentification instanceof IkeRfc822AddrIdentification) {
            PersistableBundle createPersistableBundle6 = createPersistableBundle(6);
            createPersistableBundle6.putString(RFC822_ADDRESS_KEY, ((IkeRfc822AddrIdentification) ikeIdentification).rfc822Name);
            return createPersistableBundle6;
        }
        throw new IllegalStateException("Unrecognized IkeIdentification subclass");
    }

    private static PersistableBundle createPersistableBundle(int i) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt(ID_TYPE_KEY, i);
        return persistableBundle;
    }

    public static IkeIdentification fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        int i = persistableBundle.getInt(ID_TYPE_KEY);
        switch (i) {
            case 1:
                PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(DER_ASN1_DN_KEY);
                Objects.requireNonNull(persistableBundle2, "ASN1 DN was null");
                return new IkeDerAsn1DnIdentification(new X500Principal(PersistableBundleUtils.toByteArray(persistableBundle2)));
            case 2:
                return new IkeFqdnIdentification(persistableBundle.getString(FQDN_KEY));
            case 3:
                String string = persistableBundle.getString(IP4_ADDRESS_KEY);
                Objects.requireNonNull(string, "IPv4 address was null");
                return new IkeIpv4AddrIdentification((Inet4Address) InetAddresses.parseNumericAddress(string));
            case 4:
                String string2 = persistableBundle.getString(IP6_ADDRESS_KEY);
                Objects.requireNonNull(string2, "IPv6 address was null");
                return new IkeIpv6AddrIdentification((Inet6Address) InetAddresses.parseNumericAddress(string2));
            case 5:
                PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(KEY_ID_KEY);
                Objects.requireNonNull(persistableBundle, "Key ID was null");
                return new IkeKeyIdIdentification(PersistableBundleUtils.toByteArray(persistableBundle3));
            case 6:
                return new IkeRfc822AddrIdentification(persistableBundle.getString(RFC822_ADDRESS_KEY));
            default:
                throw new IllegalStateException("Unrecognized IKE ID type: " + i);
        }
    }
}
