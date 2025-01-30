package android.net.shared;

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

/* loaded from: classes.dex */
public class InitialConfiguration {
  public static final InetAddress INET6_ANY = InetAddresses.parseNumericAddress("::");
  private static final int RFC6177_MIN_PREFIX_LENGTH = 48;
  private static final int RFC7421_PREFIX_LENGTH = 64;
  public final Set ipAddresses = new HashSet();
  public final Set directlyConnectedRoutes = new HashSet();
  public final Set dnsServers = new HashSet();

  private static boolean isCompliantIPv6PrefixLength(int i) {
    return 48 <= i && i <= 64;
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

  public String toString() {
    return String.format(
        "InitialConfiguration(IPs: {%s}, prefixes: {%s}, DNS: {%s})",
        TextUtils.join(", ", this.ipAddresses),
        TextUtils.join(", ", this.directlyConnectedRoutes),
        TextUtils.join(", ", this.dnsServers));
  }

  public boolean isValid() {
    if (this.ipAddresses.isEmpty()) {
      return false;
    }
    for (final LinkAddress linkAddress : this.ipAddresses) {
      if (!any(
          this.directlyConnectedRoutes,
          new Predicate() { // from class:
                            // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$isValid$0;
              lambda$isValid$0 = InitialConfiguration.lambda$isValid$0(linkAddress, (IpPrefix) obj);
              return lambda$isValid$0;
            }
          })) {
        return false;
      }
    }
    for (final InetAddress inetAddress : this.dnsServers) {
      if (!any(
          this.directlyConnectedRoutes,
          new Predicate() { // from class:
                            // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$isValid$1;
              lambda$isValid$1 = InitialConfiguration.lambda$isValid$1(inetAddress, (IpPrefix) obj);
              return lambda$isValid$1;
            }
          })) {
        return false;
      }
    }
    if (any(
        this.ipAddresses,
        not(
            new Predicate() { // from class:
                              // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda5
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean isPrefixLengthCompliant;
                isPrefixLengthCompliant =
                    InitialConfiguration.isPrefixLengthCompliant((LinkAddress) obj);
                return isPrefixLengthCompliant;
              }
            }))) {
      return false;
    }
    return ((any(
                    this.directlyConnectedRoutes,
                    new Predicate() { // from class:
                                      // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda6
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean isIPv6DefaultRoute;
                        isIPv6DefaultRoute =
                            InitialConfiguration.isIPv6DefaultRoute((IpPrefix) obj);
                        return isIPv6DefaultRoute;
                      }
                    })
                && all(
                    this.ipAddresses,
                    not(
                        new Predicate() { // from class:
                                          // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda7
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean isIPv6GUA;
                            isIPv6GUA = InitialConfiguration.isIPv6GUA((LinkAddress) obj);
                            return isIPv6GUA;
                          }
                        })))
            || any(
                this.directlyConnectedRoutes,
                not(
                    new Predicate() { // from class:
                                      // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda8
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean isPrefixLengthCompliant;
                        isPrefixLengthCompliant =
                            InitialConfiguration.isPrefixLengthCompliant((IpPrefix) obj);
                        return isPrefixLengthCompliant;
                      }
                    }))
            || this.ipAddresses.stream()
                    .filter(
                        new Predicate() { // from class:
                                          // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda9
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean isIPv4;
                            isIPv4 = InitialConfiguration.isIPv4((LinkAddress) obj);
                            return isIPv4;
                          }
                        })
                    .count()
                > 1)
        ? false
        : true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static /* synthetic */ boolean lambda$isValid$0(
      LinkAddress linkAddress, IpPrefix ipPrefix) {
    return ipPrefix.contains(linkAddress.getAddress());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static /* synthetic */ boolean lambda$isValid$1(
      InetAddress inetAddress, IpPrefix ipPrefix) {
    return ipPrefix.contains(inetAddress);
  }

  public boolean isProvisionedBy(List list, List list2) {
    if (this.ipAddresses.isEmpty()) {
      return false;
    }
    for (final LinkAddress linkAddress : this.ipAddresses) {
      if (!any(
          list,
          new Predicate() { // from class:
                            // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean isSameAddressAs;
              isSameAddressAs = linkAddress.isSameAddressAs((LinkAddress) obj);
              return isSameAddressAs;
            }
          })) {
        return false;
      }
    }
    if (list2 == null) {
      return true;
    }
    for (final IpPrefix ipPrefix : this.directlyConnectedRoutes) {
      if (!any(
          list2,
          new Predicate() { // from class:
                            // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$isProvisionedBy$3;
              lambda$isProvisionedBy$3 =
                  InitialConfiguration.lambda$isProvisionedBy$3(ipPrefix, (RouteInfo) obj);
              return lambda$isProvisionedBy$3;
            }
          })) {
        return false;
      }
    }
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static /* synthetic */ boolean lambda$isProvisionedBy$3(
      IpPrefix ipPrefix, RouteInfo routeInfo) {
    return isDirectlyConnectedRoute(routeInfo, ipPrefix);
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
                new InitialConfiguration$$ExternalSyntheticLambda1(),
                String.class);
    return initialConfigurationParcelable;
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
            new InitialConfiguration$$ExternalSyntheticLambda0()));
    return initialConfiguration;
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

  private static boolean isDirectlyConnectedRoute(RouteInfo routeInfo, IpPrefix ipPrefix) {
    return !routeInfo.hasGateway()
        && routeInfo.getType() == 1
        && ipPrefix.equals(routeInfo.getDestination());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isPrefixLengthCompliant(LinkAddress linkAddress) {
    return isIPv4(linkAddress) || isCompliantIPv6PrefixLength(linkAddress.getPrefixLength());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isPrefixLengthCompliant(IpPrefix ipPrefix) {
    return isIPv4(ipPrefix) || isCompliantIPv6PrefixLength(ipPrefix.getPrefixLength());
  }

  private static boolean isIPv4(IpPrefix ipPrefix) {
    return ipPrefix.getAddress() instanceof Inet4Address;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isIPv4(LinkAddress linkAddress) {
    return linkAddress.getAddress() instanceof Inet4Address;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isIPv6DefaultRoute(IpPrefix ipPrefix) {
    return ipPrefix.getAddress().equals(INET6_ANY);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isIPv6GUA(LinkAddress linkAddress) {
    return linkAddress.isIpv6() && linkAddress.isGlobalPreferred();
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

  public static boolean all(Iterable iterable, Predicate predicate) {
    return !any(iterable, not(predicate));
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static /* synthetic */ boolean lambda$not$4(Predicate predicate, Object obj) {
    return !predicate.test(obj);
  }

  public static Predicate not(final Predicate predicate) {
    return new Predicate() { // from class:
                             // android.net.shared.InitialConfiguration$$ExternalSyntheticLambda2
      @Override // java.util.function.Predicate
      public final boolean test(Object obj) {
        boolean lambda$not$4;
        lambda$not$4 = InitialConfiguration.lambda$not$4(predicate, obj);
        return lambda$not$4;
      }
    };
  }
}
