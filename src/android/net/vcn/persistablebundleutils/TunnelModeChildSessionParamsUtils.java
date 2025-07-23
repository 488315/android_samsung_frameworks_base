package android.net.vcn.persistablebundleutils;

import android.net.InetAddresses;
import android.net.ipsec.ike.ChildSaProposal;
import android.net.ipsec.ike.IkeTrafficSelector;
import android.net.ipsec.ike.TunnelModeChildSessionParams;
import android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils;
import android.net.vcn.util.PersistableBundleUtils;
import android.os.PersistableBundle;
import android.system.OsConstants;
import android.util.Log;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TunnelModeChildSessionParamsUtils {
    private static final String CONFIG_REQUESTS_KEY = "CONFIG_REQUESTS_KEY";
    private static final String HARD_LIFETIME_SEC_KEY = "HARD_LIFETIME_SEC_KEY";
    private static final String INBOUND_TS_KEY = "INBOUND_TS_KEY";
    private static final String OUTBOUND_TS_KEY = "OUTBOUND_TS_KEY";
    private static final String SA_PROPOSALS_KEY = "SA_PROPOSALS_KEY";
    private static final String SOFT_LIFETIME_SEC_KEY = "SOFT_LIFETIME_SEC_KEY";
    private static final String TAG = "TunnelModeChildSessionParamsUtils";

    /* JADX INFO: Access modifiers changed from: private */
    static class ConfigRequest {
        private static final String IP6_PREFIX_LEN = "ip6PrefixLen";
        private static final int PREFIX_LEN_UNUSED = -1;
        private static final int TYPE_IPV4_ADDRESS = 1;
        private static final int TYPE_IPV4_DHCP = 5;
        private static final int TYPE_IPV4_DNS = 3;
        private static final int TYPE_IPV4_NETMASK = 6;
        private static final int TYPE_IPV6_ADDRESS = 2;
        private static final int TYPE_IPV6_DNS = 4;
        private static final String TYPE_KEY = "type";
        private static final String VALUE_KEY = "address";
        public final InetAddress address;
        public final int ip6PrefixLen;
        public final int type;

        ConfigRequest(TunnelModeChildSessionParams.TunnelModeChildConfigRequest tunnelModeChildConfigRequest) {
            int prefixLength;
            if (tunnelModeChildConfigRequest instanceof TunnelModeChildSessionParams.ConfigRequestIpv4Address) {
                this.type = 1;
                this.address = ((TunnelModeChildSessionParams.ConfigRequestIpv4Address) tunnelModeChildConfigRequest).getAddress();
            } else if (tunnelModeChildConfigRequest instanceof TunnelModeChildSessionParams.ConfigRequestIpv6Address) {
                this.type = 2;
                TunnelModeChildSessionParams.ConfigRequestIpv6Address configRequestIpv6Address = (TunnelModeChildSessionParams.ConfigRequestIpv6Address) tunnelModeChildConfigRequest;
                Inet6Address address = configRequestIpv6Address.getAddress();
                this.address = address;
                if (address != null) {
                    prefixLength = configRequestIpv6Address.getPrefixLength();
                    this.ip6PrefixLen = prefixLength;
                }
            } else if (tunnelModeChildConfigRequest instanceof TunnelModeChildSessionParams.ConfigRequestIpv4DnsServer) {
                this.type = 3;
                this.address = null;
            } else if (tunnelModeChildConfigRequest instanceof TunnelModeChildSessionParams.ConfigRequestIpv6DnsServer) {
                this.type = 4;
                this.address = null;
            } else if (tunnelModeChildConfigRequest instanceof TunnelModeChildSessionParams.ConfigRequestIpv4DhcpServer) {
                this.type = 5;
                this.address = null;
            } else if (tunnelModeChildConfigRequest instanceof TunnelModeChildSessionParams.ConfigRequestIpv4Netmask) {
                this.type = 6;
                this.address = null;
            } else {
                throw new IllegalStateException("Unknown TunnelModeChildConfigRequest");
            }
            prefixLength = -1;
            this.ip6PrefixLen = prefixLength;
        }

        ConfigRequest(PersistableBundle persistableBundle) {
            Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            this.type = persistableBundle.getInt("type");
            this.ip6PrefixLen = persistableBundle.getInt(IP6_PREFIX_LEN);
            String string = persistableBundle.getString("address");
            if (string == null) {
                this.address = null;
            } else {
                this.address = InetAddresses.parseNumericAddress(string);
            }
        }

        public PersistableBundle toPersistableBundle() {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putInt("type", this.type);
            persistableBundle.putInt(IP6_PREFIX_LEN, this.ip6PrefixLen);
            InetAddress inetAddress = this.address;
            if (inetAddress != null) {
                persistableBundle.putString("address", inetAddress.getHostAddress());
            }
            return persistableBundle;
        }
    }

    public static PersistableBundle toPersistableBundle(TunnelModeChildSessionParams tunnelModeChildSessionParams) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putPersistableBundle(SA_PROPOSALS_KEY, PersistableBundleUtils.fromList(tunnelModeChildSessionParams.getSaProposals(), new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda1
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return ChildSaProposalUtils.toPersistableBundle((ChildSaProposal) obj);
            }
        }));
        persistableBundle.putPersistableBundle(INBOUND_TS_KEY, PersistableBundleUtils.fromList(tunnelModeChildSessionParams.getInboundTrafficSelectors(), new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda2
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return IkeTrafficSelectorUtils.toPersistableBundle((IkeTrafficSelector) obj);
            }
        }));
        persistableBundle.putPersistableBundle(OUTBOUND_TS_KEY, PersistableBundleUtils.fromList(tunnelModeChildSessionParams.getOutboundTrafficSelectors(), new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda2
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return IkeTrafficSelectorUtils.toPersistableBundle((IkeTrafficSelector) obj);
            }
        }));
        persistableBundle.putInt(HARD_LIFETIME_SEC_KEY, tunnelModeChildSessionParams.getHardLifetimeSeconds());
        persistableBundle.putInt(SOFT_LIFETIME_SEC_KEY, tunnelModeChildSessionParams.getSoftLifetimeSeconds());
        ArrayList arrayList = new ArrayList();
        Iterator<TunnelModeChildSessionParams.TunnelModeChildConfigRequest> it = tunnelModeChildSessionParams.getConfigurationRequests().iterator();
        while (it.hasNext()) {
            arrayList.add(new ConfigRequest(it.next()));
        }
        persistableBundle.putPersistableBundle(CONFIG_REQUESTS_KEY, PersistableBundleUtils.fromList(arrayList, new PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda3
            @Override // android.net.vcn.util.PersistableBundleUtils.Serializer
            public final PersistableBundle toPersistableBundle(Object obj) {
                return ((TunnelModeChildSessionParamsUtils.ConfigRequest) obj).toPersistableBundle();
            }
        }));
        return persistableBundle;
    }

    private static List<IkeTrafficSelector> getTsFromPersistableBundle(PersistableBundle persistableBundle, String str) {
        PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(str);
        Objects.requireNonNull(persistableBundle2, "Value for key " + str + " was null");
        return PersistableBundleUtils.toList(persistableBundle2, new PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda0
            @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
            public final Object fromPersistableBundle(PersistableBundle persistableBundle3) {
                return IkeTrafficSelectorUtils.fromPersistableBundle(persistableBundle3);
            }
        });
    }

    public static TunnelModeChildSessionParams fromPersistableBundle(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        TunnelModeChildSessionParams.Builder builder = new TunnelModeChildSessionParams.Builder();
        PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(SA_PROPOSALS_KEY);
        Objects.requireNonNull(persistableBundle2, "SA proposal was null");
        Iterator it = PersistableBundleUtils.toList(persistableBundle2, new PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda4
            @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
            public final Object fromPersistableBundle(PersistableBundle persistableBundle3) {
                return ChildSaProposalUtils.fromPersistableBundle(persistableBundle3);
            }
        }).iterator();
        while (it.hasNext()) {
            builder.addSaProposal((ChildSaProposal) it.next());
        }
        Iterator<IkeTrafficSelector> it2 = getTsFromPersistableBundle(persistableBundle, INBOUND_TS_KEY).iterator();
        while (it2.hasNext()) {
            builder.addInboundTrafficSelectors(it2.next());
        }
        Iterator<IkeTrafficSelector> it3 = getTsFromPersistableBundle(persistableBundle, OUTBOUND_TS_KEY).iterator();
        while (it3.hasNext()) {
            builder.addOutboundTrafficSelectors(it3.next());
        }
        builder.setLifetimeSeconds(persistableBundle.getInt(HARD_LIFETIME_SEC_KEY), persistableBundle.getInt(SOFT_LIFETIME_SEC_KEY));
        PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(CONFIG_REQUESTS_KEY);
        Objects.requireNonNull(persistableBundle3, "Config request list was null");
        boolean z = false;
        boolean z2 = false;
        for (ConfigRequest configRequest : PersistableBundleUtils.toList(persistableBundle3, new PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils$$ExternalSyntheticLambda5
            @Override // android.net.vcn.util.PersistableBundleUtils.Deserializer
            public final Object fromPersistableBundle(PersistableBundle persistableBundle4) {
                return new TunnelModeChildSessionParamsUtils.ConfigRequest(persistableBundle4);
            }
        })) {
            switch (configRequest.type) {
                case 1:
                    if (configRequest.address == null) {
                        builder.addInternalAddressRequest(OsConstants.AF_INET);
                    } else {
                        builder.addInternalAddressRequest((Inet4Address) configRequest.address);
                    }
                    z = true;
                    break;
                case 2:
                    if (configRequest.address == null) {
                        builder.addInternalAddressRequest(OsConstants.AF_INET6);
                        break;
                    } else {
                        builder.addInternalAddressRequest((Inet6Address) configRequest.address, configRequest.ip6PrefixLen);
                        break;
                    }
                case 3:
                    if (configRequest.address != null) {
                        Log.w(TAG, "Requesting a specific IPv4 DNS server is unsupported");
                    }
                    builder.addInternalDnsServerRequest(OsConstants.AF_INET);
                    break;
                case 4:
                    if (configRequest.address != null) {
                        Log.w(TAG, "Requesting a specific IPv6 DNS server is unsupported");
                    }
                    builder.addInternalDnsServerRequest(OsConstants.AF_INET6);
                    break;
                case 5:
                    if (configRequest.address != null) {
                        Log.w(TAG, "Requesting a specific IPv4 DHCP server is unsupported");
                    }
                    builder.addInternalDhcpServerRequest(OsConstants.AF_INET);
                    break;
                case 6:
                    z2 = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized config request type: " + configRequest.type);
            }
        }
        if (z != z2) {
            Log.w(TAG, String.format("Expect IPv4 address request and IPv4 netmask request either both exist or both absent, but found hasIpv4AddressReq exists? %b, hasIpv4AddressReq exists? %b, ", Boolean.valueOf(z), Boolean.valueOf(z2)));
        }
        return builder.build();
    }
}
