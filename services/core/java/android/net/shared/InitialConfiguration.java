package android.net.shared;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.InetAddresses;
import android.net.InitialConfigurationParcelable;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.RouteInfo;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class InitialConfiguration {
    public static final InetAddress INET6_ANY = InetAddresses.parseNumericAddress("::");
    private static final int RFC6177_MIN_PREFIX_LENGTH = 48;
    private static final int RFC7421_PREFIX_LENGTH = 64;
    public final Set ipAddresses = new HashSet();
    public final Set directlyConnectedRoutes = new HashSet();
    public final Set dnsServers = new HashSet();

    public static boolean all(Iterable iterable, Predicate predicate) {
        return !any(iterable, not(predicate));
    }

    public static boolean any(Iterable iterable, Predicate predicate) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static InitialConfiguration copy(InitialConfiguration initialConfiguration) {
        if (initialConfiguration == null) {
            return null;
        }
        InitialConfiguration initialConfiguration2 = new InitialConfiguration();
        initialConfiguration2.ipAddresses.addAll(initialConfiguration.ipAddresses);
        initialConfiguration2.directlyConnectedRoutes.addAll(
                initialConfiguration.directlyConnectedRoutes);
        initialConfiguration2.dnsServers.addAll(initialConfiguration.dnsServers);
        return initialConfiguration2;
    }

    public static InitialConfiguration fromStableParcelable(
            InitialConfigurationParcelable initialConfigurationParcelable) {
        if (initialConfigurationParcelable == null) {
            return null;
        }
        InitialConfiguration initialConfiguration = new InitialConfiguration();
        initialConfiguration.ipAddresses.addAll(
                Arrays.asList(initialConfigurationParcelable.ipAddresses));
        initialConfiguration.directlyConnectedRoutes.addAll(
                Arrays.asList(initialConfigurationParcelable.directlyConnectedRoutes));
        initialConfiguration.dnsServers.addAll(
                ParcelableUtil.fromParcelableArray(
                        initialConfigurationParcelable.dnsServers,
                        new InitialConfiguration$$ExternalSyntheticLambda9(0)));
        return initialConfiguration;
    }

    private static boolean isCompliantIPv6PrefixLength(int i) {
        return 48 <= i && i <= 64;
    }

    private static boolean isDirectlyConnectedRoute(RouteInfo routeInfo, IpPrefix ipPrefix) {
        return !routeInfo.hasGateway()
                && routeInfo.getType() == 1
                && ipPrefix.equals(routeInfo.getDestination());
    }

    private static boolean isIPv4(IpPrefix ipPrefix) {
        return ipPrefix.getAddress() instanceof Inet4Address;
    }

    public static boolean isIPv4(LinkAddress linkAddress) {
        return linkAddress.getAddress() instanceof Inet4Address;
    }

    public static boolean isIPv6DefaultRoute(IpPrefix ipPrefix) {
        return ipPrefix.getAddress().equals(INET6_ANY);
    }

    public static boolean isIPv6GUA(LinkAddress linkAddress) {
        return linkAddress.isIpv6() && linkAddress.isGlobalPreferred();
    }

    public static boolean isPrefixLengthCompliant(IpPrefix ipPrefix) {
        return isIPv4(ipPrefix) || isCompliantIPv6PrefixLength(ipPrefix.getPrefixLength());
    }

    public static boolean isPrefixLengthCompliant(LinkAddress linkAddress) {
        return isIPv4(linkAddress) || isCompliantIPv6PrefixLength(linkAddress.getPrefixLength());
    }

    public static /* synthetic */ boolean lambda$isProvisionedBy$3(
            IpPrefix ipPrefix, RouteInfo routeInfo) {
        return isDirectlyConnectedRoute(routeInfo, ipPrefix);
    }

    public static /* synthetic */ boolean lambda$isValid$0(
            LinkAddress linkAddress, IpPrefix ipPrefix) {
        return ipPrefix.contains(linkAddress.getAddress());
    }

    public static /* synthetic */ boolean lambda$isValid$1(
            InetAddress inetAddress, IpPrefix ipPrefix) {
        return ipPrefix.contains(inetAddress);
    }

    public static /* synthetic */ boolean lambda$not$4(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static Predicate not(Predicate predicate) {
        return new InitialConfiguration$$ExternalSyntheticLambda2(2, predicate);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InitialConfiguration)) {
            return false;
        }
        InitialConfiguration initialConfiguration = (InitialConfiguration) obj;
        return this.ipAddresses.equals(initialConfiguration.ipAddresses)
                && this.directlyConnectedRoutes.equals(initialConfiguration.directlyConnectedRoutes)
                && this.dnsServers.equals(initialConfiguration.dnsServers);
    }

    public boolean isProvisionedBy(List list, List list2) {
        if (this.ipAddresses.isEmpty()) {
            return false;
        }
        Iterator it = this.ipAddresses.iterator();
        while (it.hasNext()) {
            if (!any(
                    list,
                    new InitialConfiguration$$ExternalSyntheticLambda1(
                            (LinkAddress) it.next(), 1))) {
                return false;
            }
        }
        if (list2 == null) {
            return true;
        }
        Iterator it2 = this.directlyConnectedRoutes.iterator();
        while (it2.hasNext()) {
            if (!any(
                    list2,
                    new InitialConfiguration$$ExternalSyntheticLambda2(1, (IpPrefix) it2.next()))) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        if (this.ipAddresses.isEmpty()) {
            return false;
        }
        Iterator it = this.ipAddresses.iterator();
        while (it.hasNext()) {
            if (!any(
                    this.directlyConnectedRoutes,
                    new InitialConfiguration$$ExternalSyntheticLambda1(
                            (LinkAddress) it.next(), 0))) {
                return false;
            }
        }
        Iterator it2 = this.dnsServers.iterator();
        while (it2.hasNext()) {
            if (!any(
                    this.directlyConnectedRoutes,
                    new InitialConfiguration$$ExternalSyntheticLambda2(
                            0, (InetAddress) it2.next()))) {
                return false;
            }
        }
        final int i = 0;
        if (any(
                this.ipAddresses,
                not(
                        new Predicate() { // from class:
                                          // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean isPrefixLengthCompliant;
                                boolean isIPv6DefaultRoute;
                                boolean isIPv6GUA;
                                boolean isPrefixLengthCompliant2;
                                boolean isIPv4;
                                switch (i) {
                                    case 0:
                                        isPrefixLengthCompliant =
                                                InitialConfiguration.isPrefixLengthCompliant(
                                                        (LinkAddress) obj);
                                        return isPrefixLengthCompliant;
                                    case 1:
                                        isIPv6DefaultRoute =
                                                InitialConfiguration.isIPv6DefaultRoute(
                                                        (IpPrefix) obj);
                                        return isIPv6DefaultRoute;
                                    case 2:
                                        isIPv6GUA =
                                                InitialConfiguration.isIPv6GUA((LinkAddress) obj);
                                        return isIPv6GUA;
                                    case 3:
                                        isPrefixLengthCompliant2 =
                                                InitialConfiguration.isPrefixLengthCompliant(
                                                        (IpPrefix) obj);
                                        return isPrefixLengthCompliant2;
                                    default:
                                        isIPv4 = InitialConfiguration.isIPv4((LinkAddress) obj);
                                        return isIPv4;
                                }
                            }
                        }))) {
            return false;
        }
        final int i2 = 1;
        if (any(
                this.directlyConnectedRoutes,
                new Predicate() { // from class:
                                  // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean isPrefixLengthCompliant;
                        boolean isIPv6DefaultRoute;
                        boolean isIPv6GUA;
                        boolean isPrefixLengthCompliant2;
                        boolean isIPv4;
                        switch (i2) {
                            case 0:
                                isPrefixLengthCompliant =
                                        InitialConfiguration.isPrefixLengthCompliant(
                                                (LinkAddress) obj);
                                return isPrefixLengthCompliant;
                            case 1:
                                isIPv6DefaultRoute =
                                        InitialConfiguration.isIPv6DefaultRoute((IpPrefix) obj);
                                return isIPv6DefaultRoute;
                            case 2:
                                isIPv6GUA = InitialConfiguration.isIPv6GUA((LinkAddress) obj);
                                return isIPv6GUA;
                            case 3:
                                isPrefixLengthCompliant2 =
                                        InitialConfiguration.isPrefixLengthCompliant(
                                                (IpPrefix) obj);
                                return isPrefixLengthCompliant2;
                            default:
                                isIPv4 = InitialConfiguration.isIPv4((LinkAddress) obj);
                                return isIPv4;
                        }
                    }
                })) {
            final int i3 = 2;
            if (all(
                    this.ipAddresses,
                    not(
                            new Predicate() { // from class:
                                              // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    boolean isPrefixLengthCompliant;
                                    boolean isIPv6DefaultRoute;
                                    boolean isIPv6GUA;
                                    boolean isPrefixLengthCompliant2;
                                    boolean isIPv4;
                                    switch (i3) {
                                        case 0:
                                            isPrefixLengthCompliant =
                                                    InitialConfiguration.isPrefixLengthCompliant(
                                                            (LinkAddress) obj);
                                            return isPrefixLengthCompliant;
                                        case 1:
                                            isIPv6DefaultRoute =
                                                    InitialConfiguration.isIPv6DefaultRoute(
                                                            (IpPrefix) obj);
                                            return isIPv6DefaultRoute;
                                        case 2:
                                            isIPv6GUA =
                                                    InitialConfiguration.isIPv6GUA(
                                                            (LinkAddress) obj);
                                            return isIPv6GUA;
                                        case 3:
                                            isPrefixLengthCompliant2 =
                                                    InitialConfiguration.isPrefixLengthCompliant(
                                                            (IpPrefix) obj);
                                            return isPrefixLengthCompliant2;
                                        default:
                                            isIPv4 = InitialConfiguration.isIPv4((LinkAddress) obj);
                                            return isIPv4;
                                    }
                                }
                            }))) {
                return false;
            }
        }
        final int i4 = 3;
        if (any(
                this.directlyConnectedRoutes,
                not(
                        new Predicate() { // from class:
                                          // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean isPrefixLengthCompliant;
                                boolean isIPv6DefaultRoute;
                                boolean isIPv6GUA;
                                boolean isPrefixLengthCompliant2;
                                boolean isIPv4;
                                switch (i4) {
                                    case 0:
                                        isPrefixLengthCompliant =
                                                InitialConfiguration.isPrefixLengthCompliant(
                                                        (LinkAddress) obj);
                                        return isPrefixLengthCompliant;
                                    case 1:
                                        isIPv6DefaultRoute =
                                                InitialConfiguration.isIPv6DefaultRoute(
                                                        (IpPrefix) obj);
                                        return isIPv6DefaultRoute;
                                    case 2:
                                        isIPv6GUA =
                                                InitialConfiguration.isIPv6GUA((LinkAddress) obj);
                                        return isIPv6GUA;
                                    case 3:
                                        isPrefixLengthCompliant2 =
                                                InitialConfiguration.isPrefixLengthCompliant(
                                                        (IpPrefix) obj);
                                        return isPrefixLengthCompliant2;
                                    default:
                                        isIPv4 = InitialConfiguration.isIPv4((LinkAddress) obj);
                                        return isIPv4;
                                }
                            }
                        }))) {
            return false;
        }
        final int i5 = 4;
        return this.ipAddresses.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        boolean isPrefixLengthCompliant;
                                        boolean isIPv6DefaultRoute;
                                        boolean isIPv6GUA;
                                        boolean isPrefixLengthCompliant2;
                                        boolean isIPv4;
                                        switch (i5) {
                                            case 0:
                                                isPrefixLengthCompliant =
                                                        InitialConfiguration
                                                                .isPrefixLengthCompliant(
                                                                        (LinkAddress) obj);
                                                return isPrefixLengthCompliant;
                                            case 1:
                                                isIPv6DefaultRoute =
                                                        InitialConfiguration.isIPv6DefaultRoute(
                                                                (IpPrefix) obj);
                                                return isIPv6DefaultRoute;
                                            case 2:
                                                isIPv6GUA =
                                                        InitialConfiguration.isIPv6GUA(
                                                                (LinkAddress) obj);
                                                return isIPv6GUA;
                                            case 3:
                                                isPrefixLengthCompliant2 =
                                                        InitialConfiguration
                                                                .isPrefixLengthCompliant(
                                                                        (IpPrefix) obj);
                                                return isPrefixLengthCompliant2;
                                            default:
                                                isIPv4 =
                                                        InitialConfiguration.isIPv4(
                                                                (LinkAddress) obj);
                                                return isIPv4;
                                        }
                                    }
                                })
                        .count()
                <= 1;
    }

    public InitialConfigurationParcelable toStableParcelable() {
        InitialConfigurationParcelable initialConfigurationParcelable =
                new InitialConfigurationParcelable();
        initialConfigurationParcelable.ipAddresses =
                (LinkAddress[]) this.ipAddresses.toArray(new LinkAddress[0]);
        initialConfigurationParcelable.directlyConnectedRoutes =
                (IpPrefix[]) this.directlyConnectedRoutes.toArray(new IpPrefix[0]);
        initialConfigurationParcelable.dnsServers =
                (String[])
                        ParcelableUtil.toParcelableArray(
                                this.dnsServers,
                                new InitialConfiguration$$ExternalSyntheticLambda9(1),
                                String.class);
        return initialConfigurationParcelable;
    }

    public String toString() {
        String join = TextUtils.join(", ", this.ipAddresses);
        String join2 = TextUtils.join(", ", this.directlyConnectedRoutes);
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "InitialConfiguration(IPs: {", join, "}, prefixes: {", join2, "}, DNS: {"),
                TextUtils.join(", ", this.dnsServers),
                "})");
    }
}
