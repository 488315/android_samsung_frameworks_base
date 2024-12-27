package com.android.server.connectivity;

import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.IpPrefix;
import android.net.IpSecTransform;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkAgent;
import android.net.NetworkCapabilities;
import android.net.RouteInfo;
import android.net.ipsec.ike.ChildSessionCallback;
import android.net.ipsec.ike.ChildSessionConfiguration;
import android.net.ipsec.ike.IkeFqdnIdentification;
import android.net.ipsec.ike.IkeIdentification;
import android.net.ipsec.ike.IkeIpv4AddrIdentification;
import android.net.ipsec.ike.IkeIpv6AddrIdentification;
import android.net.ipsec.ike.IkeKeyIdIdentification;
import android.net.ipsec.ike.IkeRfc822AddrIdentification;
import android.net.ipsec.ike.IkeSessionCallback;
import android.net.ipsec.ike.IkeSessionConfiguration;
import android.net.ipsec.ike.IkeSessionConnectionInfo;
import android.net.ipsec.ike.IkeTrafficSelector;
import android.net.ipsec.ike.exceptions.IkeException;
import android.net.ipsec.ike.exceptions.IkeIOException;
import android.net.ipsec.ike.exceptions.IkeProtocolException;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.Log;

import com.android.internal.net.VpnConfig;
import com.android.internal.util.HexDump;
import com.android.net.module.util.IpRange;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class VpnIkev2Utils {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ChildSessionCallbackImpl implements ChildSessionCallback {
        public final Vpn.IkeV2VpnRunnerCallback mCallback;
        public final int mToken;

        public ChildSessionCallbackImpl(Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback, int i) {
            this.mCallback = ikeV2VpnRunnerCallback;
            this.mToken = i;
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onClosed() {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("ChildClosed for token "), this.mToken, "IkeV2VpnRunner");
            ((Vpn.IkeV2VpnRunner) this.mCallback).onSessionLost(this.mToken, null);
        }

        public final void onClosedExceptionally(IkeException ikeException) {
            Log.d(
                    "IkeV2VpnRunner",
                    "ChildClosedExceptionally for token " + this.mToken,
                    ikeException);
            ((Vpn.IkeV2VpnRunner) this.mCallback).onSessionLost(this.mToken, ikeException);
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onIpSecTransformCreated(IpSecTransform ipSecTransform, int i) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "ChildTransformCreated; Direction: ", "; token "),
                    this.mToken,
                    "IkeV2VpnRunner");
            Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback = this.mCallback;
            int i2 = this.mToken;
            Vpn.IkeV2VpnRunner ikeV2VpnRunner = (Vpn.IkeV2VpnRunner) ikeV2VpnRunnerCallback;
            if (!ikeV2VpnRunner.isActiveToken(i2)) {
                Vpn.this.mEventChanges.log(
                        "[IKEEvent-"
                                + ikeV2VpnRunner.mSessionKey
                                + "] onChildTransformCreated obsolete token="
                                + i2);
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i2, "ChildTransformCreated for obsolete token ", "IkeV2VpnRunner");
                return;
            }
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder("[IKEEvent-");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i2,
                    ikeV2VpnRunner.mSessionKey,
                    "] onChildTransformCreated token=",
                    ", direction=",
                    sb);
            sb.append(i);
            sb.append(", transform=");
            sb.append(ipSecTransform);
            localLog.log(sb.toString());
            try {
                ikeV2VpnRunner.mTunnelIface.setUnderlyingNetwork(
                        ikeV2VpnRunner.mIkeConnectionInfo.getNetwork());
                ikeV2VpnRunner.mIpSecManager.applyTunnelModeTransform(
                        ikeV2VpnRunner.mTunnelIface, i, ipSecTransform);
            } catch (IOException | IllegalArgumentException e) {
                Log.d("IkeV2VpnRunner", "Transform application failed for token " + i2, e);
                ikeV2VpnRunner.onSessionLost(i2, e);
            }
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onIpSecTransformDeleted(IpSecTransform ipSecTransform, int i) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "ChildTransformDeleted; Direction: ", "; for token "),
                    this.mToken,
                    "IkeV2VpnRunner");
        }

        public final void onIpSecTransformsMigrated(
                IpSecTransform ipSecTransform, IpSecTransform ipSecTransform2) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("ChildTransformsMigrated; token "),
                    this.mToken,
                    "IkeV2VpnRunner");
            Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback = this.mCallback;
            int i = this.mToken;
            Vpn.IkeV2VpnRunner ikeV2VpnRunner = (Vpn.IkeV2VpnRunner) ikeV2VpnRunnerCallback;
            if (!ikeV2VpnRunner.isActiveToken(i)) {
                Vpn.this.mEventChanges.log(
                        "[IKEEvent-"
                                + ikeV2VpnRunner.mSessionKey
                                + "] onChildMigrated obsolete token="
                                + i);
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i, "onChildMigrated for obsolete token ", "IkeV2VpnRunner");
                return;
            }
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder("[IKEEvent-");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i, ikeV2VpnRunner.mSessionKey, "] onChildMigrated token=", ", in=", sb);
            sb.append(ipSecTransform);
            sb.append(", out=");
            sb.append(ipSecTransform2);
            localLog.log(sb.toString());
            Network network = ikeV2VpnRunner.mIkeConnectionInfo.getNetwork();
            try {
                synchronized (Vpn.this) {
                    try {
                        Vpn vpn = Vpn.this;
                        if (vpn.mVpnRunner != ikeV2VpnRunner) {
                            return;
                        }
                        LinkProperties makeLinkProperties = vpn.makeLinkProperties();
                        VpnConfig vpnConfig = Vpn.this.mConfig;
                        vpnConfig.underlyingNetworks = new Network[] {network};
                        vpnConfig.mtu = ikeV2VpnRunner.calculateVpnMtu();
                        LinkProperties makeLinkProperties2 = Vpn.this.makeLinkProperties();
                        if (makeLinkProperties2.getLinkAddresses().isEmpty()) {
                            ikeV2VpnRunner.onSessionLost(
                                    i,
                                    new IkeIOException(
                                            new IOException("No valid addresses for MTU < 1280")));
                            return;
                        }
                        HashSet hashSet = new HashSet(makeLinkProperties.getLinkAddresses());
                        hashSet.removeAll(makeLinkProperties2.getLinkAddresses());
                        if (!hashSet.isEmpty()) {
                            Vpn vpn2 = Vpn.this;
                            vpn2.startNewNetworkAgent(
                                    vpn2.mNetworkAgent,
                                    "MTU too low for IPv6; restarting network agent");
                            Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                                LinkAddress linkAddress = (LinkAddress) it.next();
                                ikeV2VpnRunner.mTunnelIface.removeAddress(
                                        linkAddress.getAddress(), linkAddress.getPrefixLength());
                            }
                        } else if (!makeLinkProperties2.equals(makeLinkProperties)) {
                            Vpn.doSendLinkProperties(Vpn.this.mNetworkAgent, makeLinkProperties2);
                        }
                        ikeV2VpnRunner.mTunnelIface.setUnderlyingNetwork(network);
                        ikeV2VpnRunner.mIpSecManager.applyTunnelModeTransform(
                                ikeV2VpnRunner.mTunnelIface, 0, ipSecTransform);
                        ikeV2VpnRunner.mIpSecManager.applyTunnelModeTransform(
                                ikeV2VpnRunner.mTunnelIface, 1, ipSecTransform2);
                    } finally {
                    }
                }
            } catch (IOException | IllegalArgumentException e) {
                Log.d("IkeV2VpnRunner", "Transform application failed for token " + i, e);
                ikeV2VpnRunner.onSessionLost(i, e);
            }
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onOpened(ChildSessionConfiguration childSessionConfiguration) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("ChildOpened for token "), this.mToken, "IkeV2VpnRunner");
            Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback = this.mCallback;
            int i = this.mToken;
            Vpn.IkeV2VpnRunner ikeV2VpnRunner = (Vpn.IkeV2VpnRunner) ikeV2VpnRunnerCallback;
            if (!ikeV2VpnRunner.isActiveToken(i)) {
                Vpn.this.mEventChanges.log(
                        "[IKEEvent-"
                                + ikeV2VpnRunner.mSessionKey
                                + "] onChildOpened obsolete token="
                                + i);
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i, "onChildOpened called for obsolete token ", "IkeV2VpnRunner");
                return;
            }
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder("[IKEEvent-");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i, ikeV2VpnRunner.mSessionKey, "] onChildOpened token=", ", addr=", sb);
            sb.append(TextUtils.join(", ", childSessionConfiguration.getInternalAddresses()));
            sb.append(" dns=");
            sb.append(TextUtils.join(", ", childSessionConfiguration.getInternalDnsServers()));
            localLog.log(sb.toString());
            try {
                String interfaceName = ikeV2VpnRunner.mTunnelIface.getInterfaceName();
                List<LinkAddress> internalAddresses =
                        childSessionConfiguration.getInternalAddresses();
                ArrayList arrayList = new ArrayList();
                int calculateVpnMtu = ikeV2VpnRunner.calculateVpnMtu();
                boolean z = false;
                boolean z2 = false;
                for (LinkAddress linkAddress : internalAddresses) {
                    z |= linkAddress.isIpv6();
                    z2 |= linkAddress.isIpv4();
                }
                if (z && !z2 && calculateVpnMtu < 1280) {
                    ikeV2VpnRunner.onSessionLost(
                            i,
                            new IkeIOException(
                                    new IOException("No valid addresses for MTU < 1280")));
                    return;
                }
                Collection routesFromTrafficSelectors =
                        VpnIkev2Utils.getRoutesFromTrafficSelectors(
                                childSessionConfiguration.getOutboundTrafficSelectors());
                for (LinkAddress linkAddress2 : internalAddresses) {
                    ikeV2VpnRunner.mTunnelIface.addAddress(
                            linkAddress2.getAddress(), linkAddress2.getPrefixLength());
                }
                Iterator it = childSessionConfiguration.getInternalDnsServers().iterator();
                while (it.hasNext()) {
                    arrayList.add(((InetAddress) it.next()).getHostAddress());
                }
                Network network = ikeV2VpnRunner.mIkeConnectionInfo.getNetwork();
                synchronized (Vpn.this) {
                    try {
                        Vpn vpn = Vpn.this;
                        if (vpn.mVpnRunner != ikeV2VpnRunner) {
                            return;
                        }
                        vpn.mInterface = interfaceName;
                        VpnConfig vpnConfig = vpn.mConfig;
                        vpnConfig.mtu = calculateVpnMtu;
                        vpnConfig.interfaze = interfaceName;
                        vpnConfig.addresses.clear();
                        Vpn.this.mConfig.addresses.addAll(internalAddresses);
                        Vpn.this.mConfig.routes.clear();
                        Vpn.this.mConfig.routes.addAll(routesFromTrafficSelectors);
                        VpnConfig vpnConfig2 = Vpn.this.mConfig;
                        if (vpnConfig2.dnsServers == null) {
                            vpnConfig2.dnsServers = new ArrayList();
                        }
                        Vpn.this.mConfig.dnsServers.clear();
                        Vpn.this.mConfig.dnsServers.addAll(arrayList);
                        Vpn vpn2 = Vpn.this;
                        vpn2.mConfig.underlyingNetworks = new Network[] {network};
                        NetworkAgent networkAgent = vpn2.mNetworkAgent;
                        if (networkAgent == null) {
                            if (vpn2.isSettingsVpnLocked()) {
                                Vpn.m375$$Nest$mprepareStatusIntent(Vpn.this);
                            }
                            Vpn.this.agentConnect(
                                    new Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4(
                                            ikeV2VpnRunner));
                        } else {
                            Vpn.doSendLinkProperties(networkAgent, vpn2.makeLinkProperties());
                            ikeV2VpnRunner.mRetryCount = 0;
                        }
                    } finally {
                    }
                }
            } catch (Exception e) {
                Log.d("IkeV2VpnRunner", "Error in ChildOpened for token " + i, e);
                ikeV2VpnRunner.onSessionLost(i, e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IkeSessionCallbackImpl implements IkeSessionCallback {
        public final Vpn.IkeV2VpnRunnerCallback mCallback;
        public final int mToken;

        public IkeSessionCallbackImpl(Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback, int i) {
            this.mCallback = ikeV2VpnRunnerCallback;
            this.mToken = i;
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public final void onClosed() {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("IkeClosed for token "), this.mToken, "IkeV2VpnRunner");
            ((Vpn.IkeV2VpnRunner) this.mCallback).onSessionLost(this.mToken, null);
        }

        public final void onClosedExceptionally(IkeException ikeException) {
            Log.d(
                    "IkeV2VpnRunner",
                    "IkeClosedExceptionally for token " + this.mToken,
                    ikeException);
            ((Vpn.IkeV2VpnRunner) this.mCallback).onSessionLost(this.mToken, ikeException);
        }

        public final void onError(IkeProtocolException ikeProtocolException) {
            Log.d("IkeV2VpnRunner", "IkeError for token " + this.mToken, ikeProtocolException);
        }

        public final void onIkeSessionConnectionInfoChanged(
                IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onIkeSessionConnectionInfoChanged for token "),
                    this.mToken,
                    "IkeV2VpnRunner");
            ((Vpn.IkeV2VpnRunner) this.mCallback)
                    .onIkeConnectionInfoChanged(this.mToken, ikeSessionConnectionInfo);
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public final void onOpened(IkeSessionConfiguration ikeSessionConfiguration) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("IkeOpened for token "), this.mToken, "IkeV2VpnRunner");
            Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback = this.mCallback;
            int i = this.mToken;
            Vpn.IkeV2VpnRunner ikeV2VpnRunner = (Vpn.IkeV2VpnRunner) ikeV2VpnRunnerCallback;
            if (!ikeV2VpnRunner.isActiveToken(i)) {
                Vpn.this.mEventChanges.log(
                        "[IKEEvent-"
                                + ikeV2VpnRunner.mSessionKey
                                + "] onIkeOpened obsolete token="
                                + i);
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i, "onIkeOpened called for obsolete token ", "IkeV2VpnRunner");
                return;
            }
            ikeV2VpnRunner.mMobikeEnabled = ikeSessionConfiguration.isIkeExtensionEnabled(2);
            IkeSessionConnectionInfo ikeSessionConnectionInfo =
                    ikeSessionConfiguration.getIkeSessionConnectionInfo();
            LocalLog localLog = Vpn.this.mEventChanges;
            StringBuilder sb = new StringBuilder("[IKEEvent-");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    i, ikeV2VpnRunner.mSessionKey, "] onIkeOpened token=", ", localAddr=", sb);
            sb.append(ikeSessionConnectionInfo.getLocalAddress());
            sb.append(", network=");
            sb.append(ikeSessionConnectionInfo.getNetwork());
            sb.append(", mobikeEnabled= ");
            sb.append(ikeV2VpnRunner.mMobikeEnabled);
            localLog.log(sb.toString());
            ikeV2VpnRunner.onIkeConnectionInfoChanged(i, ikeSessionConnectionInfo);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Ikev2VpnNetworkCallback extends ConnectivityManager.NetworkCallback {
        public final Vpn.IkeV2VpnRunnerCallback mCallback;
        public final Executor mExecutor;
        public final String mTag = "IkeV2VpnRunner";

        public Ikev2VpnNetworkCallback(
                Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback, Executor executor) {
            this.mCallback = ikeV2VpnRunnerCallback;
            this.mExecutor = executor;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            Log.d(this.mTag, "onAvailable called for network: " + network);
            this.mExecutor.execute(
                    new VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda1(
                            this, network, 0));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onCapabilitiesChanged(
                Network network, NetworkCapabilities networkCapabilities) {
            Log.d(this.mTag, "NC changed for net " + network + " : " + networkCapabilities);
            this.mExecutor.execute(
                    new VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda0(
                            this, networkCapabilities));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            Log.d(this.mTag, "LP changed for net " + network + " : " + linkProperties);
            this.mExecutor.execute(
                    new VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda0(
                            this, linkProperties));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            Log.d(this.mTag, "onLost called for network: " + network);
            this.mExecutor.execute(
                    new VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda1(
                            this, network, 1));
        }
    }

    public static Collection getRoutesFromTrafficSelectors(List list) {
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            IkeTrafficSelector ikeTrafficSelector = (IkeTrafficSelector) it.next();
            Iterator it2 =
                    new IpRange(
                                    ikeTrafficSelector.startingAddress,
                                    ikeTrafficSelector.endingAddress)
                            .asIpPrefixes()
                            .iterator();
            while (it2.hasNext()) {
                hashSet.add(new RouteInfo((IpPrefix) it2.next(), null, null, 1));
            }
        }
        return hashSet;
    }

    public static IkeIdentification parseIkeIdentification(String str) {
        if (str.contains("@")) {
            return str.startsWith("@#")
                    ? new IkeKeyIdIdentification(HexDump.hexStringToByteArray(str.substring(2)))
                    : str.startsWith("@@")
                            ? new IkeRfc822AddrIdentification(str.substring(2))
                            : str.startsWith("@")
                                    ? new IkeFqdnIdentification(str.substring(1))
                                    : new IkeRfc822AddrIdentification(str);
        }
        if (!InetAddresses.isNumericAddress(str)) {
            return str.contains(":")
                    ? new IkeKeyIdIdentification(str.getBytes())
                    : new IkeFqdnIdentification(str);
        }
        InetAddress parseNumericAddress = InetAddresses.parseNumericAddress(str);
        if (parseNumericAddress instanceof Inet4Address) {
            return new IkeIpv4AddrIdentification((Inet4Address) parseNumericAddress);
        }
        if (parseNumericAddress instanceof Inet6Address) {
            return new IkeIpv6AddrIdentification((Inet6Address) parseNumericAddress);
        }
        throw new IllegalArgumentException("IP version not supported");
    }
}
