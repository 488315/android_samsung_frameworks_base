package com.android.server.vcn;

import android.content.Context;
import android.net.ConnectivityDiagnosticsManager;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.IpPrefix;
import android.net.IpSecManager;
import android.net.IpSecTransform;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkAgent;
import android.net.NetworkAgentConfig;
import android.net.NetworkCapabilities;
import android.net.NetworkProvider;
import android.net.NetworkRequest;
import android.net.NetworkScore;
import android.net.RouteInfo;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.net.ipsec.ike.ChildSaProposal;
import android.net.ipsec.ike.ChildSessionCallback;
import android.net.ipsec.ike.ChildSessionConfiguration;
import android.net.ipsec.ike.ChildSessionParams;
import android.net.ipsec.ike.IkeSession;
import android.net.ipsec.ike.IkeSessionCallback;
import android.net.ipsec.ike.IkeSessionConfiguration;
import android.net.ipsec.ike.IkeSessionConnectionInfo;
import android.net.ipsec.ike.IkeSessionParams;
import android.net.ipsec.ike.IkeTrafficSelector;
import android.net.ipsec.ike.IkeTunnelConnectionParams;
import android.net.ipsec.ike.TunnelModeChildSessionParams;
import android.net.ipsec.ike.exceptions.IkeException;
import android.net.ipsec.ike.exceptions.IkeInternalException;
import android.net.ipsec.ike.exceptions.IkeProtocolException;
import android.net.vcn.VcnGatewayConnectionConfig;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.WifiInfo;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.util.HexDump;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;
import com.android.internal.util.WakeupMessage;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.vcn.TelephonySubscriptionTracker;
import com.android.server.vcn.Vcn;
import com.android.server.vcn.VcnGatewayConnection;
import com.android.server.vcn.routeselection.UnderlyingNetworkController;
import com.android.server.vcn.routeselection.UnderlyingNetworkRecord;
import com.android.server.vcn.util.MtuUtils;
import com.android.server.vcn.util.OneWayBoolean;
import com.android.server.vcn.util.PersistableBundleUtils;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class VcnGatewayConnection extends StateMachine {
    static final String NETWORK_INFO_EXTRA_INFO = "VCN";
    static final String NETWORK_INFO_NETWORK_TYPE_STRING = "MOBILE";
    static final int NETWORK_LOSS_DISCONNECT_TIMEOUT_SECONDS = 30;
    static final int SAFEMODE_TIMEOUT_SECONDS = 30;
    static final int TEARDOWN_TIMEOUT_SECONDS = 5;
    public VcnChildSessionConfiguration mChildConfig;
    final ConnectedState mConnectedState;
    final ConnectingState mConnectingState;
    public final VcnGatewayConnectionConfig mConnectionConfig;
    public final VcnConnectivityDiagnosticsCallback mConnectivityDiagnosticsCallback;
    public final ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;
    public final ConnectivityManager mConnectivityManager;
    public int mCurrentToken;
    public final Dependencies mDeps;
    public WakeupMessage mDisconnectRequestAlarm;
    final DisconnectedState mDisconnectedState;
    final DisconnectingState mDisconnectingState;
    public int mFailedAttempts;
    public final Vcn.VcnGatewayStatusCallback mGatewayStatusCallback;
    public IkeSessionConnectionInfo mIkeConnectionInfo;
    public VcnIkeSession mIkeSession;
    public final IpSecManager mIpSecManager;
    public boolean mIsInSafeMode;
    public final boolean mIsMobileDataEnabled;
    public final OneWayBoolean mIsQuitting;
    public TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot;
    public VcnNetworkAgent mNetworkAgent;
    public WakeupMessage mRetryTimeoutAlarm;
    final RetryTimeoutState mRetryTimeoutState;
    public WakeupMessage mSafeModeTimeoutAlarm;
    public final ParcelUuid mSubscriptionGroup;
    public WakeupMessage mTeardownTimeoutAlarm;
    public IpSecManager.IpSecTunnelInterface mTunnelIface;
    public UnderlyingNetworkRecord mUnderlying;
    public final UnderlyingNetworkController mUnderlyingNetworkController;
    public final VcnUnderlyingNetworkControllerCallback mUnderlyingNetworkControllerCallback;
    public final VcnContext mVcnContext;
    public final VcnWakeLock mWakeLock;
    static final InetAddress DUMMY_ADDR = InetAddresses.parseNumericAddress("192.0.2.0");
    static final String TEARDOWN_TIMEOUT_ALARM = "VcnGatewayConnection_TEARDOWN_TIMEOUT_ALARM";
    static final String DISCONNECT_REQUEST_ALARM = "VcnGatewayConnection_DISCONNECT_REQUEST_ALARM";
    static final String RETRY_TIMEOUT_ALARM = "VcnGatewayConnection_RETRY_TIMEOUT_ALARM";
    static final String SAFEMODE_TIMEOUT_ALARM = "VcnGatewayConnection_SAFEMODE_TIMEOUT_ALARM";
    public static final int[] MERGED_CAPABILITIES = {11, 18};

    public abstract class ActiveBaseState extends BaseState {
        public ActiveBaseState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final boolean isValidToken(int i) {
            return i == Integer.MIN_VALUE || i == VcnGatewayConnection.this.mCurrentToken;
        }
    }

    public abstract class BaseState extends State {
        public BaseState() {
        }

        public final void enter() {
            try {
                enterState();
            } catch (Exception e) {
                VcnGatewayConnection.m1029$$Nest$mlogWtf(VcnGatewayConnection.this, e);
                VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock("Uncaught exception: " + e.toString(), true);
            }
        }

        public abstract void enterState();

        public final void exit() {
            try {
                exitState();
            } catch (Exception e) {
                VcnGatewayConnection.m1029$$Nest$mlogWtf(VcnGatewayConnection.this, e);
                VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock("Uncaught exception: " + e.toString(), true);
            }
        }

        public void exitState() {
        }

        public final void handleDisconnectRequested(EventDisconnectRequestedInfo eventDisconnectRequestedInfo) {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            StringBuilder sb = new StringBuilder("Tearing down. Cause: ");
            sb.append(eventDisconnectRequestedInfo.reason);
            sb.append("; quitting = ");
            boolean z = eventDisconnectRequestedInfo.shouldQuit;
            sb.append(z);
            String sb2 = sb.toString();
            int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
            vcnGatewayConnection.logInfo(sb2);
            if (z) {
                VcnGatewayConnection.this.mIsQuitting.mValue = true;
            }
            teardownNetwork();
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            if (vcnGatewayConnection2.mIkeSession == null) {
                vcnGatewayConnection2.transitionTo(vcnGatewayConnection2.mDisconnectedState);
            } else {
                vcnGatewayConnection2.transitionTo(vcnGatewayConnection2.mDisconnectingState);
            }
        }

        public final void handleSafeModeTimeoutExceeded() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            vcnGatewayConnection.mSafeModeTimeoutAlarm = null;
            vcnGatewayConnection.logInfo("Entering safe mode after timeout exceeded");
            teardownNetwork();
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            vcnGatewayConnection2.mIsInSafeMode = true;
            vcnGatewayConnection2.mGatewayStatusCallback.onSafeModeStatusChanged();
        }

        public boolean isValidToken(int i) {
            return true;
        }

        public final void logUnhandledMessage(Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                    int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
                    vcnGatewayConnection.getClass();
                    break;
                default:
                    VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Unknown event code ", " in state ");
                    m.append(getClass().getSimpleName());
                    String sb = m.toString();
                    int[] iArr2 = VcnGatewayConnection.MERGED_CAPABILITIES;
                    vcnGatewayConnection2.logWtf(sb);
                    break;
            }
        }

        public final boolean processMessage(Message message) {
            int i = message.arg1;
            if (!isValidToken(i)) {
                VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Message called with obsolete token: ", "; what: ");
                m.append(message.what);
                String sb = m.toString();
                int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
                vcnGatewayConnection.logDbg(sb);
                return true;
            }
            try {
                processStateMsg(message);
            } catch (Exception e) {
                VcnGatewayConnection.m1029$$Nest$mlogWtf(VcnGatewayConnection.this, e);
                VcnGatewayConnection.this.sendDisconnectRequestedAndAcquireWakelock("Uncaught exception: " + e.toString(), true);
            }
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            int[] iArr2 = VcnGatewayConnection.MERGED_CAPABILITIES;
            Handler handler = vcnGatewayConnection2.getHandler();
            if (handler == null || !handler.hasMessagesOrCallbacks()) {
                vcnGatewayConnection2.mVcnContext.ensureRunningOnLooperThread();
                VcnWakeLock vcnWakeLock = vcnGatewayConnection2.mWakeLock;
                synchronized (vcnWakeLock) {
                    vcnWakeLock.mImpl.release();
                }
                Objects.toString(vcnGatewayConnection2.mWakeLock);
            }
            return true;
        }

        public abstract void processStateMsg(Message message);

        public final void teardownNetwork() {
            VcnNetworkAgent vcnNetworkAgent = VcnGatewayConnection.this.mNetworkAgent;
            if (vcnNetworkAgent != null) {
                vcnNetworkAgent.mImpl.unregister();
                VcnGatewayConnection.this.mNetworkAgent = null;
            }
        }
    }

    public final class ConnectedState extends ActiveBaseState {
        public final /* synthetic */ VcnGatewayConnection this$0$1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ConnectedState() {
            super();
            this.this$0$1 = VcnGatewayConnection.this;
        }

        public final void applyTransform(int i, IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, Network network, IpSecTransform ipSecTransform, int i2) {
            if (i2 != 0 && i2 != 1) {
                this.this$0$1.logWtf("Applying transform for unexpected direction: " + i2);
            }
            try {
                ipSecTunnelInterface.setUnderlyingNetwork(network);
                this.this$0$1.mIpSecManager.applyTunnelModeTransform(ipSecTunnelInterface, i2, ipSecTransform);
                if (i2 == 0 && this.this$0$1.mVcnContext.isFlagNetworkMetricMonitorEnabled()) {
                    this.this$0$1.mVcnContext.getClass();
                    if (VcnContext.isFlagIpSecTransformStateEnabled()) {
                        VcnGatewayConnection vcnGatewayConnection = this.this$0$1;
                        vcnGatewayConnection.mUnderlyingNetworkController.updateInboundTransform(vcnGatewayConnection.mUnderlying, ipSecTransform);
                    }
                }
                Set allExposedCapabilities = this.this$0$1.mConnectionConfig.getAllExposedCapabilities();
                if (i2 == 0 && allExposedCapabilities.contains(2)) {
                    this.this$0$1.mIpSecManager.applyTunnelModeTransform(ipSecTunnelInterface, 2, ipSecTransform);
                }
            } catch (IOException | IllegalArgumentException e) {
                VcnGatewayConnection.m1028$$Nest$mlogInfo(this.this$0$1, "Transform application failed for network " + i, e);
                VcnGatewayConnection.m1031$$Nest$msessionLost(this.this$0$1, i, e);
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void enterState() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            if (vcnGatewayConnection.mTunnelIface == null) {
                try {
                    IpSecManager ipSecManager = vcnGatewayConnection.mIpSecManager;
                    InetAddress inetAddress = VcnGatewayConnection.DUMMY_ADDR;
                    vcnGatewayConnection.mTunnelIface = ipSecManager.createIpSecTunnelInterface(inetAddress, inetAddress, vcnGatewayConnection.mUnderlying.network);
                } catch (IpSecManager.ResourceUnavailableException | IOException unused) {
                    VcnGatewayConnection.this.teardownAsynchronously();
                }
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void exitState() {
            VcnGatewayConnection.this.setSafeModeAlarm();
        }

        /* JADX WARN: Type inference failed for: r5v11, types: [com.android.server.vcn.VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r6v11, types: [com.android.server.vcn.VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0] */
        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void processStateMsg(Message message) {
            VcnChildSessionConfiguration vcnChildSessionConfiguration;
            VcnNetworkAgent vcnNetworkAgent;
            final int i = 1;
            final int i2 = 0;
            switch (message.what) {
                case 1:
                    VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                    UnderlyingNetworkRecord underlyingNetworkRecord = vcnGatewayConnection.mUnderlying;
                    UnderlyingNetworkRecord underlyingNetworkRecord2 = ((EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                    vcnGatewayConnection.mUnderlying = underlyingNetworkRecord2;
                    if (underlyingNetworkRecord2 != null) {
                        if (underlyingNetworkRecord != null && underlyingNetworkRecord.network.equals(underlyingNetworkRecord2.network)) {
                            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                            VcnNetworkAgent vcnNetworkAgent2 = vcnGatewayConnection2.mNetworkAgent;
                            if (vcnNetworkAgent2 != null && (vcnChildSessionConfiguration = vcnGatewayConnection2.mChildConfig) != null) {
                                updateNetworkAgent(vcnGatewayConnection2.mTunnelIface, vcnNetworkAgent2, vcnChildSessionConfiguration, vcnGatewayConnection2.mIkeConnectionInfo);
                                break;
                            }
                        } else {
                            VcnGatewayConnection.this.logInfo("Migrating to new network: " + VcnGatewayConnection.this.mUnderlying.network);
                            VcnGatewayConnection vcnGatewayConnection3 = VcnGatewayConnection.this;
                            vcnGatewayConnection3.mIkeSession.mImpl.setNetwork(vcnGatewayConnection3.mUnderlying.network);
                            break;
                        }
                    } else {
                        vcnGatewayConnection.logInfo("Underlying network lost");
                        break;
                    }
                    break;
                case 2:
                case 8:
                case 9:
                default:
                    logUnhandledMessage(message);
                    break;
                case 3:
                    VcnGatewayConnection vcnGatewayConnection4 = VcnGatewayConnection.this;
                    vcnGatewayConnection4.transitionTo(vcnGatewayConnection4.mDisconnectingState);
                    break;
                case 4:
                    VcnGatewayConnection.this.deferMessage(message);
                    VcnGatewayConnection vcnGatewayConnection5 = VcnGatewayConnection.this;
                    vcnGatewayConnection5.transitionTo(vcnGatewayConnection5.mDisconnectingState);
                    break;
                case 5:
                    EventTransformCreatedInfo eventTransformCreatedInfo = (EventTransformCreatedInfo) message.obj;
                    VcnGatewayConnection vcnGatewayConnection6 = VcnGatewayConnection.this;
                    applyTransform(vcnGatewayConnection6.mCurrentToken, vcnGatewayConnection6.mTunnelIface, vcnGatewayConnection6.mUnderlying.network, eventTransformCreatedInfo.transform, eventTransformCreatedInfo.direction);
                    break;
                case 6:
                    VcnGatewayConnection vcnGatewayConnection7 = VcnGatewayConnection.this;
                    VcnChildSessionConfiguration vcnChildSessionConfiguration2 = vcnGatewayConnection7.mChildConfig;
                    VcnChildSessionConfiguration vcnChildSessionConfiguration3 = ((EventSetupCompletedInfo) message.obj).childSessionConfig;
                    vcnGatewayConnection7.mChildConfig = vcnChildSessionConfiguration3;
                    int i3 = vcnGatewayConnection7.mCurrentToken;
                    IpSecManager.IpSecTunnelInterface ipSecTunnelInterface = vcnGatewayConnection7.mTunnelIface;
                    IkeSessionConnectionInfo ikeSessionConnectionInfo = vcnGatewayConnection7.mIkeConnectionInfo;
                    try {
                        ArraySet arraySet = new ArraySet(vcnChildSessionConfiguration3.mChildConfig.getInternalAddresses());
                        ArraySet arraySet2 = new ArraySet();
                        if (vcnChildSessionConfiguration2 != null) {
                            arraySet2.addAll(vcnChildSessionConfiguration2.mChildConfig.getInternalAddresses());
                        }
                        ArraySet arraySet3 = new ArraySet();
                        arraySet3.addAll((Collection) arraySet);
                        arraySet3.removeAll((Collection<?>) arraySet2);
                        ArraySet arraySet4 = new ArraySet();
                        arraySet4.addAll((Collection) arraySet2);
                        arraySet4.removeAll((Collection<?>) arraySet);
                        Iterator it = arraySet3.iterator();
                        while (it.hasNext()) {
                            LinkAddress linkAddress = (LinkAddress) it.next();
                            ipSecTunnelInterface.addAddress(linkAddress.getAddress(), linkAddress.getPrefixLength());
                        }
                        Iterator it2 = arraySet4.iterator();
                        while (it2.hasNext()) {
                            LinkAddress linkAddress2 = (LinkAddress) it2.next();
                            ipSecTunnelInterface.removeAddress(linkAddress2.getAddress(), linkAddress2.getPrefixLength());
                        }
                    } catch (IOException e) {
                        VcnGatewayConnection.m1028$$Nest$mlogInfo(this.this$0$1, "Adding address to tunnel failed for token " + i3, e);
                        VcnGatewayConnection.m1031$$Nest$msessionLost(this.this$0$1, i3, e);
                    }
                    VcnGatewayConnection vcnGatewayConnection8 = VcnGatewayConnection.this;
                    VcnNetworkAgent vcnNetworkAgent3 = vcnGatewayConnection8.mNetworkAgent;
                    if (vcnNetworkAgent3 == null) {
                        VcnGatewayConnection vcnGatewayConnection9 = this.this$0$1;
                        NetworkCapabilities buildNetworkCapabilities = VcnGatewayConnection.buildNetworkCapabilities(vcnGatewayConnection9.mConnectionConfig, vcnGatewayConnection9.mUnderlying, vcnGatewayConnection9.mIsMobileDataEnabled);
                        VcnGatewayConnection vcnGatewayConnection10 = this.this$0$1;
                        LinkProperties buildConnectedLinkProperties = vcnGatewayConnection10.buildConnectedLinkProperties(vcnGatewayConnection10.mConnectionConfig, ipSecTunnelInterface, vcnChildSessionConfiguration3, vcnGatewayConnection10.mUnderlying, ikeSessionConnectionInfo);
                        NetworkAgentConfig build = new NetworkAgentConfig.Builder().setLegacyType(0).setLegacyTypeName(VcnGatewayConnection.NETWORK_INFO_NETWORK_TYPE_STRING).setLegacySubType(0).setLegacySubTypeName(TelephonyManager.getNetworkTypeName(0)).setLegacyExtraInfo(VcnGatewayConnection.NETWORK_INFO_EXTRA_INFO).build();
                        VcnGatewayConnection vcnGatewayConnection11 = this.this$0$1;
                        Dependencies dependencies = vcnGatewayConnection11.mDeps;
                        VcnContext vcnContext = vcnGatewayConnection11.mVcnContext;
                        List list = Vcn.CAPS_REQUIRING_MOBILE_DATA;
                        NetworkScore build2 = new NetworkScore.Builder().setLegacyInt(52).setTransportPrimary(true).build();
                        VcnNetworkProvider vcnNetworkProvider = this.this$0$1.mVcnContext.mVcnNetworkProvider;
                        ?? r5 = new Consumer(this) { // from class: com.android.server.vcn.VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0
                            public final /* synthetic */ VcnGatewayConnection.ConnectedState f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i4 = i2;
                                VcnGatewayConnection.ConnectedState connectedState = this.f$0;
                                switch (i4) {
                                    case 0:
                                        VcnGatewayConnection.VcnNetworkAgent vcnNetworkAgent4 = (VcnGatewayConnection.VcnNetworkAgent) obj;
                                        VcnGatewayConnection vcnGatewayConnection12 = connectedState.this$0$1;
                                        if (vcnGatewayConnection12.mNetworkAgent == vcnNetworkAgent4) {
                                            vcnGatewayConnection12.logInfo("NetworkAgent was unwanted");
                                            connectedState.this$0$1.teardownAsynchronously();
                                            break;
                                        } else {
                                            vcnGatewayConnection12.logDbg("unwanted() called on stale NetworkAgent");
                                            break;
                                        }
                                    default:
                                        Integer num = (Integer) obj;
                                        if (!connectedState.this$0$1.mIsQuitting.mValue) {
                                            int intValue = num.intValue();
                                            if (intValue == 1) {
                                                connectedState.this$0$1.mVcnContext.ensureRunningOnLooperThread();
                                                VcnGatewayConnection vcnGatewayConnection13 = connectedState.this$0$1;
                                                vcnGatewayConnection13.mFailedAttempts = 0;
                                                vcnGatewayConnection13.cancelSafeModeAlarm();
                                                VcnGatewayConnection vcnGatewayConnection14 = connectedState.this$0$1;
                                                vcnGatewayConnection14.mIsInSafeMode = false;
                                                vcnGatewayConnection14.mGatewayStatusCallback.onSafeModeStatusChanged();
                                                break;
                                            } else if (intValue == 2) {
                                                VcnGatewayConnection vcnGatewayConnection15 = connectedState.this$0$1;
                                                UnderlyingNetworkRecord underlyingNetworkRecord3 = vcnGatewayConnection15.mUnderlying;
                                                if (underlyingNetworkRecord3 != null) {
                                                    vcnGatewayConnection15.mConnectivityManager.reportNetworkConnectivity(underlyingNetworkRecord3.network, false);
                                                }
                                                connectedState.this$0$1.setSafeModeAlarm();
                                                break;
                                            } else {
                                                connectedState.this$0$1.logWtf("Unknown validation status " + num + "; ignoring");
                                                break;
                                            }
                                        }
                                        break;
                                }
                            }
                        };
                        ?? r6 = new Consumer(this) { // from class: com.android.server.vcn.VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0
                            public final /* synthetic */ VcnGatewayConnection.ConnectedState f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i4 = i;
                                VcnGatewayConnection.ConnectedState connectedState = this.f$0;
                                switch (i4) {
                                    case 0:
                                        VcnGatewayConnection.VcnNetworkAgent vcnNetworkAgent4 = (VcnGatewayConnection.VcnNetworkAgent) obj;
                                        VcnGatewayConnection vcnGatewayConnection12 = connectedState.this$0$1;
                                        if (vcnGatewayConnection12.mNetworkAgent == vcnNetworkAgent4) {
                                            vcnGatewayConnection12.logInfo("NetworkAgent was unwanted");
                                            connectedState.this$0$1.teardownAsynchronously();
                                            break;
                                        } else {
                                            vcnGatewayConnection12.logDbg("unwanted() called on stale NetworkAgent");
                                            break;
                                        }
                                    default:
                                        Integer num = (Integer) obj;
                                        if (!connectedState.this$0$1.mIsQuitting.mValue) {
                                            int intValue = num.intValue();
                                            if (intValue == 1) {
                                                connectedState.this$0$1.mVcnContext.ensureRunningOnLooperThread();
                                                VcnGatewayConnection vcnGatewayConnection13 = connectedState.this$0$1;
                                                vcnGatewayConnection13.mFailedAttempts = 0;
                                                vcnGatewayConnection13.cancelSafeModeAlarm();
                                                VcnGatewayConnection vcnGatewayConnection14 = connectedState.this$0$1;
                                                vcnGatewayConnection14.mIsInSafeMode = false;
                                                vcnGatewayConnection14.mGatewayStatusCallback.onSafeModeStatusChanged();
                                                break;
                                            } else if (intValue == 2) {
                                                VcnGatewayConnection vcnGatewayConnection15 = connectedState.this$0$1;
                                                UnderlyingNetworkRecord underlyingNetworkRecord3 = vcnGatewayConnection15.mUnderlying;
                                                if (underlyingNetworkRecord3 != null) {
                                                    vcnGatewayConnection15.mConnectivityManager.reportNetworkConnectivity(underlyingNetworkRecord3.network, false);
                                                }
                                                connectedState.this$0$1.setSafeModeAlarm();
                                                break;
                                            } else {
                                                connectedState.this$0$1.logWtf("Unknown validation status " + num + "; ignoring");
                                                break;
                                            }
                                        }
                                        break;
                                }
                            }
                        };
                        dependencies.getClass();
                        VcnNetworkAgent vcnNetworkAgent4 = new VcnNetworkAgent(vcnContext, buildNetworkCapabilities, buildConnectedLinkProperties, build2, build, vcnNetworkProvider, r5, r6);
                        VcnNetworkAgent.AnonymousClass1 anonymousClass1 = vcnNetworkAgent4.mImpl;
                        anonymousClass1.register();
                        anonymousClass1.markConnected();
                        vcnGatewayConnection8.mNetworkAgent = vcnNetworkAgent4;
                    } else {
                        updateNetworkAgent(ipSecTunnelInterface, vcnNetworkAgent3, vcnChildSessionConfiguration3, ikeSessionConnectionInfo);
                        this.this$0$1.mVcnContext.ensureRunningOnLooperThread();
                        VcnGatewayConnection vcnGatewayConnection12 = this.this$0$1;
                        vcnGatewayConnection12.mFailedAttempts = 0;
                        vcnGatewayConnection12.cancelSafeModeAlarm();
                        VcnGatewayConnection vcnGatewayConnection13 = this.this$0$1;
                        vcnGatewayConnection13.mIsInSafeMode = false;
                        vcnGatewayConnection13.mGatewayStatusCallback.onSafeModeStatusChanged();
                    }
                    VcnGatewayConnection vcnGatewayConnection14 = VcnGatewayConnection.this;
                    Dependencies dependencies2 = vcnGatewayConnection14.mDeps;
                    TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot = vcnGatewayConnection14.mLastSnapshot;
                    ParcelUuid parcelUuid = vcnGatewayConnection14.mSubscriptionGroup;
                    dependencies2.getClass();
                    PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp = telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(parcelUuid);
                    int max = Math.max(1, carrierConfigForSubGrp != null ? carrierConfigForSubGrp.mBundle.getInt("vcn_tunnel_aggregation_sa_count_max", 1) : 1);
                    VcnGatewayConnection.this.logInfo("Parallel tunnel count: " + max);
                    while (i2 < max - 1) {
                        VcnGatewayConnection vcnGatewayConnection15 = VcnGatewayConnection.this;
                        VcnIkeSession vcnIkeSession = vcnGatewayConnection15.mIkeSession;
                        TunnelModeChildSessionParams tunnelModeChildSessionParams = vcnGatewayConnection15.mConnectionConfig.getTunnelConnectionParams().getTunnelModeChildSessionParams();
                        TunnelModeChildSessionParams.Builder builder = new TunnelModeChildSessionParams.Builder();
                        Iterator<ChildSaProposal> it3 = tunnelModeChildSessionParams.getChildSaProposals().iterator();
                        while (it3.hasNext()) {
                            builder.addChildSaProposal(it3.next());
                        }
                        Iterator<IkeTrafficSelector> it4 = tunnelModeChildSessionParams.getInboundTrafficSelectors().iterator();
                        while (it4.hasNext()) {
                            builder.addInboundTrafficSelectors(it4.next());
                        }
                        Iterator<IkeTrafficSelector> it5 = tunnelModeChildSessionParams.getOutboundTrafficSelectors().iterator();
                        while (it5.hasNext()) {
                            builder.addOutboundTrafficSelectors(it5.next());
                        }
                        builder.setLifetimeSeconds(tunnelModeChildSessionParams.getHardLifetimeSeconds(), tunnelModeChildSessionParams.getSoftLifetimeSeconds());
                        TunnelModeChildSessionParams build3 = builder.build();
                        VcnGatewayConnection vcnGatewayConnection16 = VcnGatewayConnection.this;
                        vcnIkeSession.mImpl.openChildSession(build3, vcnGatewayConnection16.new VcnChildSessionCallback(vcnGatewayConnection16.mCurrentToken, true));
                        i2++;
                    }
                    break;
                case 7:
                    handleDisconnectRequested((EventDisconnectRequestedInfo) message.obj);
                    break;
                case 10:
                    handleSafeModeTimeoutExceeded();
                    break;
                case 11:
                    EventMigrationCompletedInfo eventMigrationCompletedInfo = (EventMigrationCompletedInfo) message.obj;
                    VcnGatewayConnection.this.logInfo("Migration completed: " + VcnGatewayConnection.this.mUnderlying.network);
                    VcnGatewayConnection vcnGatewayConnection17 = VcnGatewayConnection.this;
                    applyTransform(vcnGatewayConnection17.mCurrentToken, vcnGatewayConnection17.mTunnelIface, vcnGatewayConnection17.mUnderlying.network, eventMigrationCompletedInfo.inTransform, 0);
                    VcnGatewayConnection vcnGatewayConnection18 = VcnGatewayConnection.this;
                    applyTransform(vcnGatewayConnection18.mCurrentToken, vcnGatewayConnection18.mTunnelIface, vcnGatewayConnection18.mUnderlying.network, eventMigrationCompletedInfo.outTransform, 1);
                    VcnGatewayConnection vcnGatewayConnection19 = VcnGatewayConnection.this;
                    updateNetworkAgent(vcnGatewayConnection19.mTunnelIface, vcnGatewayConnection19.mNetworkAgent, vcnGatewayConnection19.mChildConfig, vcnGatewayConnection19.mIkeConnectionInfo);
                    VcnGatewayConnection vcnGatewayConnection20 = VcnGatewayConnection.this;
                    vcnGatewayConnection20.mConnectivityManager.reportNetworkConnectivity(vcnGatewayConnection20.mNetworkAgent.mImpl.getNetwork(), false);
                    break;
                case 12:
                    VcnGatewayConnection.this.mIkeConnectionInfo = ((EventIkeConnectionInfoChangedInfo) message.obj).ikeConnectionInfo;
                    break;
                case 13:
                    Network network = ((EventDataStallSuspectedInfo) message.obj).network;
                    VcnGatewayConnection vcnGatewayConnection21 = VcnGatewayConnection.this;
                    if (vcnGatewayConnection21.mUnderlying != null && (vcnNetworkAgent = vcnGatewayConnection21.mNetworkAgent) != null && vcnNetworkAgent.mImpl.getNetwork().equals(network)) {
                        VcnGatewayConnection.this.logInfo("Perform Mobility update to recover from suspected data stall");
                        VcnGatewayConnection vcnGatewayConnection22 = VcnGatewayConnection.this;
                        vcnGatewayConnection22.mIkeSession.mImpl.setNetwork(vcnGatewayConnection22.mUnderlying.network);
                        break;
                    }
                    break;
            }
        }

        public final void updateNetworkAgent(IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, VcnNetworkAgent vcnNetworkAgent, VcnChildSessionConfiguration vcnChildSessionConfiguration, IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            VcnGatewayConnection vcnGatewayConnection = this.this$0$1;
            NetworkCapabilities buildNetworkCapabilities = VcnGatewayConnection.buildNetworkCapabilities(vcnGatewayConnection.mConnectionConfig, vcnGatewayConnection.mUnderlying, vcnGatewayConnection.mIsMobileDataEnabled);
            VcnGatewayConnection vcnGatewayConnection2 = this.this$0$1;
            LinkProperties buildConnectedLinkProperties = vcnGatewayConnection2.buildConnectedLinkProperties(vcnGatewayConnection2.mConnectionConfig, ipSecTunnelInterface, vcnChildSessionConfiguration, vcnGatewayConnection2.mUnderlying, ikeSessionConnectionInfo);
            vcnNetworkAgent.mImpl.sendNetworkCapabilities(buildNetworkCapabilities);
            VcnNetworkAgent.AnonymousClass1 anonymousClass1 = vcnNetworkAgent.mImpl;
            anonymousClass1.sendLinkProperties(buildConnectedLinkProperties);
            UnderlyingNetworkRecord underlyingNetworkRecord = this.this$0$1.mUnderlying;
            anonymousClass1.setUnderlyingNetworks(underlyingNetworkRecord == null ? null : Collections.singletonList(underlyingNetworkRecord.network));
        }
    }

    public final class ConnectingState extends ActiveBaseState {
        public ConnectingState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void enterState() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            if (vcnGatewayConnection.mIkeSession != null) {
                vcnGatewayConnection.logWtf("ConnectingState entered with active session");
                VcnGatewayConnection.this.mIkeSession.mImpl.kill();
                VcnGatewayConnection.this.mIkeSession = null;
            }
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            vcnGatewayConnection2.mIkeSession = vcnGatewayConnection2.buildIkeSession(vcnGatewayConnection2.mUnderlying.network);
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void processStateMsg(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 10) {
                    handleSafeModeTimeoutExceeded();
                    return;
                }
                if (i != 12) {
                    if (i != 3) {
                        if (i == 4) {
                            VcnGatewayConnection.this.deferMessage(message);
                            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                            vcnGatewayConnection.transitionTo(vcnGatewayConnection.mDisconnectingState);
                            return;
                        } else if (i != 5 && i != 6) {
                            if (i != 7) {
                                logUnhandledMessage(message);
                                return;
                            } else {
                                handleDisconnectRequested((EventDisconnectRequestedInfo) message.obj);
                                return;
                            }
                        }
                    }
                }
                VcnGatewayConnection.this.deferMessage(message);
                VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                vcnGatewayConnection2.transitionTo(vcnGatewayConnection2.mConnectedState);
                return;
            }
            VcnGatewayConnection vcnGatewayConnection3 = VcnGatewayConnection.this;
            UnderlyingNetworkRecord underlyingNetworkRecord = vcnGatewayConnection3.mUnderlying;
            vcnGatewayConnection3.mUnderlying = ((EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
            if (underlyingNetworkRecord == null) {
                vcnGatewayConnection3.logWtf("Old underlying network was null in connected state. Bug?");
            }
            VcnGatewayConnection vcnGatewayConnection4 = VcnGatewayConnection.this;
            UnderlyingNetworkRecord underlyingNetworkRecord2 = vcnGatewayConnection4.mUnderlying;
            if (underlyingNetworkRecord2 == null) {
                vcnGatewayConnection4.transitionTo(vcnGatewayConnection4.mDisconnectingState);
                return;
            } else if (underlyingNetworkRecord != null && underlyingNetworkRecord2.network.equals(underlyingNetworkRecord.network)) {
                return;
            } else {
                VcnGatewayConnection.this.mDisconnectingState.mSkipRetryTimeout = true;
            }
            VcnGatewayConnection vcnGatewayConnection5 = VcnGatewayConnection.this;
            vcnGatewayConnection5.transitionTo(vcnGatewayConnection5.mDisconnectingState);
        }
    }

    public class Dependencies {
    }

    public final class DisconnectedState extends BaseState {
        public DisconnectedState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void enterState() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            if (vcnGatewayConnection.mIsQuitting.mValue) {
                vcnGatewayConnection.quitNow();
            }
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            if (vcnGatewayConnection2.mIkeSession != null || vcnGatewayConnection2.mNetworkAgent != null) {
                vcnGatewayConnection2.logWtf("Active IKE Session or NetworkAgent in DisconnectedState");
            }
            VcnGatewayConnection.this.cancelSafeModeAlarm();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void exitState() {
            VcnGatewayConnection.this.setSafeModeAlarm();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void processStateMsg(Message message) {
            int i = message.what;
            if (i == 1) {
                VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                UnderlyingNetworkRecord underlyingNetworkRecord = ((EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                vcnGatewayConnection.mUnderlying = underlyingNetworkRecord;
                if (underlyingNetworkRecord != null) {
                    vcnGatewayConnection.transitionTo(vcnGatewayConnection.mConnectingState);
                    return;
                }
                return;
            }
            if (i != 7) {
                logUnhandledMessage(message);
            } else if (((EventDisconnectRequestedInfo) message.obj).shouldQuit) {
                VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                vcnGatewayConnection2.mIsQuitting.mValue = true;
                vcnGatewayConnection2.quitNow();
            }
        }
    }

    public final class DisconnectingState extends ActiveBaseState {
        public boolean mSkipRetryTimeout;

        public DisconnectingState() {
            super();
            this.mSkipRetryTimeout = false;
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void enterState() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            VcnIkeSession vcnIkeSession = vcnGatewayConnection.mIkeSession;
            if (vcnIkeSession == null) {
                vcnGatewayConnection.logWtf("IKE session was already closed when entering Disconnecting state.");
                VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                vcnGatewayConnection2.sendMessageAndAcquireWakeLock(4, vcnGatewayConnection2.mCurrentToken);
            } else {
                if (vcnGatewayConnection.mUnderlying == null) {
                    vcnIkeSession.mImpl.kill();
                    return;
                }
                vcnIkeSession.mImpl.close();
                VcnGatewayConnection vcnGatewayConnection3 = VcnGatewayConnection.this;
                if (vcnGatewayConnection3.mTeardownTimeoutAlarm != null) {
                    vcnGatewayConnection3.logWtf("mTeardownTimeoutAlarm should be null before being set; mCurrentToken: " + vcnGatewayConnection3.mCurrentToken);
                }
                vcnGatewayConnection3.mTeardownTimeoutAlarm = vcnGatewayConnection3.createScheduledAlarm(VcnGatewayConnection.TEARDOWN_TIMEOUT_ALARM, vcnGatewayConnection3.obtainMessage(8, vcnGatewayConnection3.mCurrentToken), TimeUnit.SECONDS.toMillis(5L));
            }
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void exitState() {
            this.mSkipRetryTimeout = false;
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            WakeupMessage wakeupMessage = vcnGatewayConnection.mTeardownTimeoutAlarm;
            if (wakeupMessage != null) {
                wakeupMessage.cancel();
                vcnGatewayConnection.mTeardownTimeoutAlarm = null;
            }
            vcnGatewayConnection.removeEqualMessages(8, null);
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void processStateMsg(Message message) {
            int i = message.what;
            if (i == 1) {
                VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                UnderlyingNetworkRecord underlyingNetworkRecord = ((EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                vcnGatewayConnection.mUnderlying = underlyingNetworkRecord;
                if (underlyingNetworkRecord != null) {
                    return;
                }
            } else {
                if (i == 4) {
                    VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                    vcnGatewayConnection2.mIkeSession = null;
                    if (!vcnGatewayConnection2.mIsQuitting.mValue && vcnGatewayConnection2.mUnderlying != null) {
                        vcnGatewayConnection2.transitionTo(this.mSkipRetryTimeout ? vcnGatewayConnection2.mConnectingState : vcnGatewayConnection2.mRetryTimeoutState);
                        return;
                    }
                    teardownNetwork();
                    VcnGatewayConnection vcnGatewayConnection3 = VcnGatewayConnection.this;
                    vcnGatewayConnection3.transitionTo(vcnGatewayConnection3.mDisconnectedState);
                    return;
                }
                if (i == 10) {
                    handleSafeModeTimeoutExceeded();
                    return;
                }
                if (i == 7) {
                    EventDisconnectRequestedInfo eventDisconnectRequestedInfo = (EventDisconnectRequestedInfo) message.obj;
                    if (eventDisconnectRequestedInfo.shouldQuit) {
                        VcnGatewayConnection.this.mIsQuitting.mValue = true;
                    }
                    teardownNetwork();
                    if (eventDisconnectRequestedInfo.reason.equals("Underlying Network lost")) {
                        VcnGatewayConnection.this.mIkeSession.mImpl.kill();
                        return;
                    }
                    return;
                }
                if (i != 8) {
                    logUnhandledMessage(message);
                    return;
                }
            }
            VcnGatewayConnection.this.mIkeSession.mImpl.kill();
        }
    }

    public final class EventDataStallSuspectedInfo implements EventInfo {
        public final Network network;

        public EventDataStallSuspectedInfo(Network network) {
            this.network = network;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof EventDataStallSuspectedInfo) {
                return Objects.equals(this.network, ((EventDataStallSuspectedInfo) obj).network);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.network);
        }
    }

    public final class EventDisconnectRequestedInfo implements EventInfo {
        public final String reason;
        public final boolean shouldQuit;

        public EventDisconnectRequestedInfo(String str, boolean z) {
            Objects.requireNonNull(str);
            this.reason = str;
            this.shouldQuit = z;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof EventDisconnectRequestedInfo)) {
                return false;
            }
            EventDisconnectRequestedInfo eventDisconnectRequestedInfo = (EventDisconnectRequestedInfo) obj;
            return this.reason.equals(eventDisconnectRequestedInfo.reason) && this.shouldQuit == eventDisconnectRequestedInfo.shouldQuit;
        }

        public final int hashCode() {
            return Objects.hash(this.reason, Boolean.valueOf(this.shouldQuit));
        }
    }

    public final class EventIkeConnectionInfoChangedInfo implements EventInfo {
        public final IkeSessionConnectionInfo ikeConnectionInfo;

        public EventIkeConnectionInfoChangedInfo(IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            this.ikeConnectionInfo = ikeSessionConnectionInfo;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof EventIkeConnectionInfoChangedInfo) {
                return Objects.equals(this.ikeConnectionInfo, ((EventIkeConnectionInfoChangedInfo) obj).ikeConnectionInfo);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.ikeConnectionInfo);
        }
    }

    public interface EventInfo {
    }

    public final class EventMigrationCompletedInfo implements EventInfo {
        public final IpSecTransform inTransform;
        public final IpSecTransform outTransform;

        public EventMigrationCompletedInfo(IpSecTransform ipSecTransform, IpSecTransform ipSecTransform2) {
            Objects.requireNonNull(ipSecTransform);
            this.inTransform = ipSecTransform;
            Objects.requireNonNull(ipSecTransform2);
            this.outTransform = ipSecTransform2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof EventMigrationCompletedInfo)) {
                return false;
            }
            EventMigrationCompletedInfo eventMigrationCompletedInfo = (EventMigrationCompletedInfo) obj;
            return Objects.equals(this.inTransform, eventMigrationCompletedInfo.inTransform) && Objects.equals(this.outTransform, eventMigrationCompletedInfo.outTransform);
        }

        public final int hashCode() {
            return Objects.hash(this.inTransform, this.outTransform);
        }
    }

    public final class EventSessionLostInfo implements EventInfo {
        public final Exception exception;

        public EventSessionLostInfo(Exception exc) {
            this.exception = exc;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof EventSessionLostInfo) {
                return Objects.equals(this.exception, ((EventSessionLostInfo) obj).exception);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.exception);
        }
    }

    public final class EventSetupCompletedInfo implements EventInfo {
        public final VcnChildSessionConfiguration childSessionConfig;

        public EventSetupCompletedInfo(VcnChildSessionConfiguration vcnChildSessionConfiguration) {
            Objects.requireNonNull(vcnChildSessionConfiguration);
            this.childSessionConfig = vcnChildSessionConfiguration;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof EventSetupCompletedInfo) {
                return Objects.equals(this.childSessionConfig, ((EventSetupCompletedInfo) obj).childSessionConfig);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.childSessionConfig);
        }
    }

    public final class EventTransformCreatedInfo implements EventInfo {
        public final int direction;
        public final IpSecTransform transform;

        public EventTransformCreatedInfo(IpSecTransform ipSecTransform, int i) {
            this.direction = i;
            Objects.requireNonNull(ipSecTransform);
            this.transform = ipSecTransform;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof EventTransformCreatedInfo)) {
                return false;
            }
            EventTransformCreatedInfo eventTransformCreatedInfo = (EventTransformCreatedInfo) obj;
            return this.direction == eventTransformCreatedInfo.direction && Objects.equals(this.transform, eventTransformCreatedInfo.transform);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.direction), this.transform);
        }
    }

    public final class EventUnderlyingNetworkChangedInfo implements EventInfo {
        public final UnderlyingNetworkRecord newUnderlying;

        public EventUnderlyingNetworkChangedInfo(UnderlyingNetworkRecord underlyingNetworkRecord) {
            this.newUnderlying = underlyingNetworkRecord;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof EventUnderlyingNetworkChangedInfo) {
                return Objects.equals(this.newUnderlying, ((EventUnderlyingNetworkChangedInfo) obj).newUnderlying);
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.newUnderlying);
        }
    }

    public final class IkeSessionCallbackImpl implements IkeSessionCallback {
        public final int mToken;

        public IkeSessionCallbackImpl(int i) {
            this.mToken = i;
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public final void onClosed() {
            VcnGatewayConnection.this.logDbg("IkeClosed for token " + this.mToken);
            VcnGatewayConnection.m1030$$Nest$msessionClosed(VcnGatewayConnection.this, this.mToken, null);
        }

        public final void onClosedExceptionally(IkeException ikeException) {
            VcnGatewayConnection.m1028$$Nest$mlogInfo(VcnGatewayConnection.this, "IkeClosedExceptionally for token " + this.mToken, ikeException);
            VcnGatewayConnection.m1030$$Nest$msessionClosed(VcnGatewayConnection.this, this.mToken, ikeException);
        }

        public final void onError(IkeProtocolException ikeProtocolException) {
            VcnGatewayConnection.m1028$$Nest$mlogInfo(VcnGatewayConnection.this, "IkeError for token " + this.mToken, ikeProtocolException);
        }

        public final void onIkeSessionConnectionInfoChanged(IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            VcnGatewayConnection.this.logDbg("onIkeSessionConnectionInfoChanged for token " + this.mToken);
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            int i = this.mToken;
            vcnGatewayConnection.getClass();
            vcnGatewayConnection.sendMessageAndAcquireWakeLock(12, i, new EventIkeConnectionInfoChangedInfo(ikeSessionConnectionInfo));
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public final void onOpened(IkeSessionConfiguration ikeSessionConfiguration) {
            VcnGatewayConnection.this.logDbg("IkeOpened for token " + this.mToken);
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            int i = this.mToken;
            IkeSessionConnectionInfo ikeSessionConnectionInfo = ikeSessionConfiguration.getIkeSessionConnectionInfo();
            vcnGatewayConnection.getClass();
            vcnGatewayConnection.sendMessageAndAcquireWakeLock(12, i, new EventIkeConnectionInfoChangedInfo(ikeSessionConnectionInfo));
        }
    }

    public final class RetryTimeoutState extends ActiveBaseState {
        public RetryTimeoutState() {
            super();
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void enterState() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            int i = vcnGatewayConnection.mFailedAttempts;
            vcnGatewayConnection.mFailedAttempts = i + 1;
            if (vcnGatewayConnection.mUnderlying == null) {
                vcnGatewayConnection.logWtf("Underlying network was null in retry state");
                teardownNetwork();
                VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                vcnGatewayConnection2.transitionTo(vcnGatewayConnection2.mDisconnectedState);
                return;
            }
            long[] retryIntervalsMillis = vcnGatewayConnection.mConnectionConfig.getRetryIntervalsMillis();
            long j = i >= retryIntervalsMillis.length ? retryIntervalsMillis[retryIntervalsMillis.length - 1] : retryIntervalsMillis[i];
            if (vcnGatewayConnection.mRetryTimeoutAlarm != null) {
                vcnGatewayConnection.logWtf("mRetryTimeoutAlarm should be null before being set; mCurrentToken: " + vcnGatewayConnection.mCurrentToken);
            }
            vcnGatewayConnection.mRetryTimeoutAlarm = vcnGatewayConnection.createScheduledAlarm(VcnGatewayConnection.RETRY_TIMEOUT_ALARM, vcnGatewayConnection.obtainMessage(2, vcnGatewayConnection.mCurrentToken), j);
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void exitState() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            WakeupMessage wakeupMessage = vcnGatewayConnection.mRetryTimeoutAlarm;
            if (wakeupMessage != null) {
                wakeupMessage.cancel();
                vcnGatewayConnection.mRetryTimeoutAlarm = null;
            }
            vcnGatewayConnection.removeEqualMessages(2, null);
        }

        @Override // com.android.server.vcn.VcnGatewayConnection.BaseState
        public final void processStateMsg(Message message) {
            int i = message.what;
            if (i == 1) {
                VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
                UnderlyingNetworkRecord underlyingNetworkRecord = vcnGatewayConnection.mUnderlying;
                UnderlyingNetworkRecord underlyingNetworkRecord2 = ((EventUnderlyingNetworkChangedInfo) message.obj).newUnderlying;
                vcnGatewayConnection.mUnderlying = underlyingNetworkRecord2;
                if (underlyingNetworkRecord2 == null) {
                    teardownNetwork();
                    VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                    vcnGatewayConnection2.transitionTo(vcnGatewayConnection2.mDisconnectedState);
                    return;
                } else if (underlyingNetworkRecord != null && underlyingNetworkRecord2.network.equals(underlyingNetworkRecord.network)) {
                    return;
                }
            } else if (i != 2) {
                if (i == 7) {
                    handleDisconnectRequested((EventDisconnectRequestedInfo) message.obj);
                    return;
                } else if (i != 10) {
                    logUnhandledMessage(message);
                    return;
                } else {
                    handleSafeModeTimeoutExceeded();
                    return;
                }
            }
            VcnGatewayConnection vcnGatewayConnection3 = VcnGatewayConnection.this;
            vcnGatewayConnection3.transitionTo(vcnGatewayConnection3.mConnectingState);
        }
    }

    public class VcnChildSessionCallback implements ChildSessionCallback {
        public boolean mIsChildOpened = false;
        public final boolean mIsOpportunistic;
        public final int mToken;

        public VcnChildSessionCallback(int i, boolean z) {
            this.mToken = i;
            this.mIsOpportunistic = z;
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onClosed() {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            String str = "ChildClosed for token " + this.mToken;
            int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
            vcnGatewayConnection.logDbg(str);
            if (this.mIsOpportunistic && !this.mIsChildOpened) {
                VcnGatewayConnection.this.logDbg("ChildClosed for unopened opportunistic child; ignoring");
                return;
            }
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            int i = this.mToken;
            vcnGatewayConnection2.getClass();
            vcnGatewayConnection2.sendMessageAndAcquireWakeLock(3, i, new EventSessionLostInfo(null));
        }

        public final void onClosedExceptionally(IkeException ikeException) {
            VcnGatewayConnection.m1028$$Nest$mlogInfo(VcnGatewayConnection.this, "ChildClosedExceptionally for token " + this.mToken, ikeException);
            if (!this.mIsOpportunistic || this.mIsChildOpened) {
                VcnGatewayConnection.m1031$$Nest$msessionLost(VcnGatewayConnection.this, this.mToken, ikeException);
            } else {
                VcnGatewayConnection.this.logInfo("ChildClosedExceptionally for unopened opportunistic child; ignoring");
            }
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onIpSecTransformCreated(IpSecTransform ipSecTransform, int i) {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "ChildTransformCreated; Direction: ", "; token ");
            m.append(this.mToken);
            String sb = m.toString();
            int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
            vcnGatewayConnection.logDbg(sb);
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            int i2 = this.mToken;
            vcnGatewayConnection2.getClass();
            vcnGatewayConnection2.sendMessageAndAcquireWakeLock(5, i2, new EventTransformCreatedInfo(ipSecTransform, i));
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onIpSecTransformDeleted(IpSecTransform ipSecTransform, int i) {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "ChildTransformDeleted; Direction: ", "; for token ");
            m.append(this.mToken);
            String sb = m.toString();
            int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
            vcnGatewayConnection.logDbg(sb);
        }

        public final void onIpSecTransformsMigrated(IpSecTransform ipSecTransform, IpSecTransform ipSecTransform2) {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            String str = "ChildTransformsMigrated; token " + this.mToken;
            int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
            vcnGatewayConnection.logDbg(str);
            VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
            int i = this.mToken;
            vcnGatewayConnection2.getClass();
            vcnGatewayConnection2.sendMessageAndAcquireWakeLock(11, i, new EventMigrationCompletedInfo(ipSecTransform, ipSecTransform2));
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public final void onOpened(ChildSessionConfiguration childSessionConfiguration) {
            onOpened(new VcnChildSessionConfiguration(childSessionConfiguration));
        }

        public void onOpened(VcnChildSessionConfiguration vcnChildSessionConfiguration) {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            String str = "ChildOpened for token " + this.mToken;
            int[] iArr = VcnGatewayConnection.MERGED_CAPABILITIES;
            vcnGatewayConnection.logDbg(str);
            if (this.mIsOpportunistic) {
                VcnGatewayConnection.this.logDbg("ChildOpened for opportunistic child; suppressing event message");
                this.mIsChildOpened = true;
            } else {
                VcnGatewayConnection vcnGatewayConnection2 = VcnGatewayConnection.this;
                int i = this.mToken;
                vcnGatewayConnection2.getClass();
                vcnGatewayConnection2.sendMessageAndAcquireWakeLock(6, i, new EventSetupCompletedInfo(vcnChildSessionConfiguration));
            }
        }
    }

    public class VcnChildSessionConfiguration {
        public final ChildSessionConfiguration mChildConfig;

        public VcnChildSessionConfiguration(ChildSessionConfiguration childSessionConfiguration) {
            this.mChildConfig = childSessionConfiguration;
        }
    }

    public final class VcnConnectivityDiagnosticsCallback extends ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback {
        public VcnConnectivityDiagnosticsCallback() {
        }

        @Override // android.net.ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback
        public final void onDataStallSuspected(ConnectivityDiagnosticsManager.DataStallReport dataStallReport) {
            VcnGatewayConnection.this.mVcnContext.ensureRunningOnLooperThread();
            Network network = dataStallReport.getNetwork();
            VcnGatewayConnection.this.logInfo("Data stall suspected on " + network);
            VcnGatewayConnection.this.sendMessageAndAcquireWakeLock(13, Integer.MIN_VALUE, new EventDataStallSuspectedInfo(network));
        }
    }

    public class VcnIkeSession {
        public final IkeSession mImpl;

        public VcnIkeSession(VcnContext vcnContext, IkeSessionParams ikeSessionParams, ChildSessionParams childSessionParams, IkeSessionCallbackImpl ikeSessionCallbackImpl, VcnChildSessionCallback vcnChildSessionCallback) {
            this.mImpl = new IkeSession(vcnContext.mContext, ikeSessionParams, childSessionParams, new HandlerExecutor(new Handler(vcnContext.mLooper)), ikeSessionCallbackImpl, vcnChildSessionCallback);
        }
    }

    public class VcnNetworkAgent {
        public final AnonymousClass1 mImpl;

        /* JADX WARN: Type inference failed for: r11v0, types: [com.android.server.vcn.VcnGatewayConnection$VcnNetworkAgent$1] */
        public VcnNetworkAgent(VcnContext vcnContext, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, NetworkScore networkScore, NetworkAgentConfig networkAgentConfig, NetworkProvider networkProvider, final VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0 vcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0, final VcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0 vcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda02) {
            this.mImpl = new NetworkAgent(vcnContext.mContext, vcnContext.mLooper, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider) { // from class: com.android.server.vcn.VcnGatewayConnection.VcnNetworkAgent.1
                public final void onNetworkUnwanted() {
                    vcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda0.accept(VcnNetworkAgent.this);
                }

                public final void onValidationStatus(int i, Uri uri) {
                    vcnGatewayConnection$ConnectedStateBase$$ExternalSyntheticLambda02.accept(Integer.valueOf(i));
                }
            };
        }
    }

    public final class VcnUnderlyingNetworkControllerCallback implements UnderlyingNetworkController.UnderlyingNetworkControllerCallback {
        public VcnUnderlyingNetworkControllerCallback() {
        }

        public final void onSelectedUnderlyingNetworkChanged(UnderlyingNetworkRecord underlyingNetworkRecord) {
            VcnGatewayConnection vcnGatewayConnection = VcnGatewayConnection.this;
            vcnGatewayConnection.mVcnContext.ensureRunningOnLooperThread();
            UnderlyingNetworkRecord underlyingNetworkRecord2 = vcnGatewayConnection.mUnderlying;
            if (!Objects.equals(underlyingNetworkRecord2 == null ? null : underlyingNetworkRecord2.network, underlyingNetworkRecord == null ? null : underlyingNetworkRecord.network)) {
                StringBuilder sb = new StringBuilder("Selected underlying network changed: ");
                sb.append(underlyingNetworkRecord == null ? null : underlyingNetworkRecord.network);
                vcnGatewayConnection.logInfo(sb.toString());
            }
            if (underlyingNetworkRecord == null) {
                Dependencies dependencies = vcnGatewayConnection.mDeps;
                VcnContext vcnContext = vcnGatewayConnection.mVcnContext;
                dependencies.getClass();
                if (Settings.Global.getInt(vcnContext.mContext.getContentResolver(), "airplane_mode_on", 0) != 0) {
                    vcnGatewayConnection.sendMessageAndAcquireWakeLock(1, Integer.MIN_VALUE, new EventUnderlyingNetworkChangedInfo(null));
                    vcnGatewayConnection.sendDisconnectRequestedAndAcquireWakelock("Underlying Network lost", false);
                    return;
                } else if (vcnGatewayConnection.mDisconnectRequestAlarm == null) {
                    vcnGatewayConnection.mDisconnectRequestAlarm = vcnGatewayConnection.createScheduledAlarm(VcnGatewayConnection.DISCONNECT_REQUEST_ALARM, vcnGatewayConnection.obtainMessage(7, Integer.MIN_VALUE, 0, new EventDisconnectRequestedInfo("Underlying Network lost", false)), TimeUnit.SECONDS.toMillis(30L));
                }
            } else {
                WakeupMessage wakeupMessage = vcnGatewayConnection.mDisconnectRequestAlarm;
                if (wakeupMessage != null) {
                    wakeupMessage.cancel();
                    vcnGatewayConnection.mDisconnectRequestAlarm = null;
                }
                vcnGatewayConnection.removeEqualMessages(7, new EventDisconnectRequestedInfo("Underlying Network lost", false));
            }
            vcnGatewayConnection.sendMessageAndAcquireWakeLock(1, Integer.MIN_VALUE, new EventUnderlyingNetworkChangedInfo(underlyingNetworkRecord));
        }
    }

    public class VcnWakeLock {
        public final PowerManager.WakeLock mImpl;

        public VcnWakeLock(Context context) {
            PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "VcnGatewayConnection");
            this.mImpl = newWakeLock;
            newWakeLock.setReferenceCounted(false);
        }
    }

    public static void $r8$lambda$dQrFsdoY5M3GVBsFSNJpWCtl8SQ(VcnGatewayConnection vcnGatewayConnection, Message message) {
        vcnGatewayConnection.acquireWakeLock();
        super.sendMessage(message);
    }

    /* renamed from: -$$Nest$mlogInfo, reason: not valid java name */
    public static void m1028$$Nest$mlogInfo(VcnGatewayConnection vcnGatewayConnection, String str, Throwable th) {
        Slog.i("VcnGatewayConnection", vcnGatewayConnection.getLogPrefix() + str, th);
        VcnManagementService.LOCAL_LOG.log("[INFO] " + vcnGatewayConnection.getTagLogPrefix() + str + th);
    }

    /* renamed from: -$$Nest$mlogWtf, reason: not valid java name */
    public static void m1029$$Nest$mlogWtf(VcnGatewayConnection vcnGatewayConnection, Throwable th) {
        Slog.wtf("VcnGatewayConnection", vcnGatewayConnection.getLogPrefix() + "Uncaught exception", th);
        VcnManagementService.LOCAL_LOG.log("[WTF ] Uncaught exception" + th);
    }

    /* renamed from: -$$Nest$msessionClosed, reason: not valid java name */
    public static void m1030$$Nest$msessionClosed(VcnGatewayConnection vcnGatewayConnection, int i, Exception exc) {
        String name;
        String str;
        int i2;
        if (exc != null) {
            vcnGatewayConnection.getClass();
            if ((exc instanceof IkeProtocolException) && ((IkeProtocolException) exc).getErrorType() == 24) {
                name = exc.getClass().getName();
                str = exc.getMessage();
                i2 = 1;
            } else if ((exc instanceof IkeInternalException) && (exc.getCause() instanceof IOException)) {
                name = IOException.class.getName();
                str = exc.getCause().getMessage();
                i2 = 2;
            } else {
                name = RuntimeException.class.getName();
                str = "Received " + exc.getClass().getSimpleName() + " with message: " + exc.getMessage();
                i2 = 0;
            }
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i2, "Encountered error; code=", ", exceptionClass=", name, ", exceptionMessage=");
            m.append(str);
            vcnGatewayConnection.logDbg(m.toString());
            vcnGatewayConnection.mGatewayStatusCallback.onGatewayConnectionError(i2, vcnGatewayConnection.mConnectionConfig.getGatewayConnectionName(), name, str);
        }
        vcnGatewayConnection.getClass();
        vcnGatewayConnection.sendMessageAndAcquireWakeLock(3, i, new EventSessionLostInfo(exc));
        vcnGatewayConnection.sendMessageAndAcquireWakeLock(4, i);
    }

    /* renamed from: -$$Nest$msessionLost, reason: not valid java name */
    public static void m1031$$Nest$msessionLost(VcnGatewayConnection vcnGatewayConnection, int i, Exception exc) {
        if (exc != null) {
            vcnGatewayConnection.mGatewayStatusCallback.onGatewayConnectionError(0, vcnGatewayConnection.mConnectionConfig.getGatewayConnectionName(), RuntimeException.class.getName(), "Received " + exc.getClass().getSimpleName() + " with message: " + exc.getMessage());
        }
        vcnGatewayConnection.getClass();
        vcnGatewayConnection.sendMessageAndAcquireWakeLock(3, i, new EventSessionLostInfo(exc));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VcnGatewayConnection(VcnContext vcnContext, ParcelUuid parcelUuid, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, VcnGatewayConnectionConfig vcnGatewayConnectionConfig, Vcn.VcnGatewayStatusCallback vcnGatewayStatusCallback, boolean z, Dependencies dependencies) {
        super("VcnGatewayConnection", vcnContext.mLooper);
        Objects.requireNonNull(vcnContext, "Missing vcnContext");
        DisconnectedState disconnectedState = new DisconnectedState();
        this.mDisconnectedState = disconnectedState;
        DisconnectingState disconnectingState = new DisconnectingState();
        this.mDisconnectingState = disconnectingState;
        ConnectingState connectingState = new ConnectingState();
        this.mConnectingState = connectingState;
        ConnectedState connectedState = new ConnectedState();
        this.mConnectedState = connectedState;
        RetryTimeoutState retryTimeoutState = new RetryTimeoutState();
        this.mRetryTimeoutState = retryTimeoutState;
        this.mTunnelIface = null;
        OneWayBoolean oneWayBoolean = new OneWayBoolean();
        oneWayBoolean.mValue = false;
        this.mIsQuitting = oneWayBoolean;
        this.mIsInSafeMode = false;
        this.mCurrentToken = -1;
        this.mFailedAttempts = 0;
        this.mVcnContext = vcnContext;
        Objects.requireNonNull(parcelUuid, "Missing subscriptionGroup");
        this.mSubscriptionGroup = parcelUuid;
        Objects.requireNonNull(vcnGatewayConnectionConfig, "Missing connectionConfig");
        this.mConnectionConfig = vcnGatewayConnectionConfig;
        Objects.requireNonNull(vcnGatewayStatusCallback, "Missing gatewayStatusCallback");
        this.mGatewayStatusCallback = vcnGatewayStatusCallback;
        this.mIsMobileDataEnabled = z;
        Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        Objects.requireNonNull(telephonySubscriptionSnapshot, "Missing snapshot");
        this.mLastSnapshot = telephonySubscriptionSnapshot;
        VcnUnderlyingNetworkControllerCallback vcnUnderlyingNetworkControllerCallback = new VcnUnderlyingNetworkControllerCallback();
        this.mUnderlyingNetworkControllerCallback = vcnUnderlyingNetworkControllerCallback;
        this.mWakeLock = new VcnWakeLock(vcnContext.mContext);
        this.mUnderlyingNetworkController = new UnderlyingNetworkController(vcnContext, vcnGatewayConnectionConfig, parcelUuid, this.mLastSnapshot, vcnUnderlyingNetworkControllerCallback, new UnderlyingNetworkController.Dependencies());
        this.mIpSecManager = (IpSecManager) vcnContext.mContext.getSystemService(IpSecManager.class);
        this.mConnectivityManager = (ConnectivityManager) vcnContext.mContext.getSystemService(ConnectivityManager.class);
        ConnectivityDiagnosticsManager connectivityDiagnosticsManager = (ConnectivityDiagnosticsManager) vcnContext.mContext.getSystemService(ConnectivityDiagnosticsManager.class);
        this.mConnectivityDiagnosticsManager = connectivityDiagnosticsManager;
        VcnConnectivityDiagnosticsCallback vcnConnectivityDiagnosticsCallback = new VcnConnectivityDiagnosticsCallback();
        this.mConnectivityDiagnosticsCallback = vcnConnectivityDiagnosticsCallback;
        if (vcnGatewayConnectionConfig.hasGatewayOption(0)) {
            connectivityDiagnosticsManager.registerConnectivityDiagnosticsCallback(new NetworkRequest.Builder().addTransportType(0).build(), new HandlerExecutor(new Handler(vcnContext.mLooper)), vcnConnectivityDiagnosticsCallback);
        }
        addState(disconnectedState);
        addState(disconnectingState);
        addState(connectingState);
        addState(connectedState);
        addState(retryTimeoutState);
        setInitialState(disconnectedState);
        setDbg(false);
        start();
    }

    public static NetworkCapabilities buildNetworkCapabilities(VcnGatewayConnectionConfig vcnGatewayConnectionConfig, UnderlyingNetworkRecord underlyingNetworkRecord, boolean z) {
        NetworkCapabilities.Builder builder = new NetworkCapabilities.Builder();
        builder.addTransportType(0);
        builder.addCapability(28);
        builder.addCapability(20);
        builder.addCapability(21);
        Iterator it = vcnGatewayConnectionConfig.getAllExposedCapabilities().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (z || (intValue != 12 && intValue != 2)) {
                builder.addCapability(intValue);
            }
        }
        if (underlyingNetworkRecord != null) {
            NetworkCapabilities networkCapabilities = underlyingNetworkRecord.networkCapabilities;
            for (int i : MERGED_CAPABILITIES) {
                if (networkCapabilities.hasCapability(i)) {
                    builder.addCapability(i);
                }
            }
            int[] administratorUids = networkCapabilities.getAdministratorUids();
            Arrays.sort(administratorUids);
            if (networkCapabilities.getOwnerUid() > 0 && Arrays.binarySearch(administratorUids, networkCapabilities.getOwnerUid()) < 0) {
                administratorUids = Arrays.copyOf(administratorUids, administratorUids.length + 1);
                administratorUids[administratorUids.length - 1] = networkCapabilities.getOwnerUid();
                Arrays.sort(administratorUids);
            }
            builder.setOwnerUid(Process.myUid());
            int[] copyOf = Arrays.copyOf(administratorUids, administratorUids.length + 1);
            copyOf[copyOf.length - 1] = Process.myUid();
            builder.setAdministratorUids(copyOf);
            builder.setLinkUpstreamBandwidthKbps(networkCapabilities.getLinkUpstreamBandwidthKbps());
            builder.setLinkDownstreamBandwidthKbps(networkCapabilities.getLinkDownstreamBandwidthKbps());
            if (networkCapabilities.hasTransport(1) && (networkCapabilities.getTransportInfo() instanceof WifiInfo)) {
                builder.setTransportInfo(new VcnTransportInfo((WifiInfo) networkCapabilities.getTransportInfo(), vcnGatewayConnectionConfig.getMinUdpPort4500NatTimeoutSeconds()));
            } else if (networkCapabilities.hasTransport(0) && (networkCapabilities.getNetworkSpecifier() instanceof TelephonyNetworkSpecifier)) {
                builder.setTransportInfo(new VcnTransportInfo(((TelephonyNetworkSpecifier) networkCapabilities.getNetworkSpecifier()).getSubscriptionId(), vcnGatewayConnectionConfig.getMinUdpPort4500NatTimeoutSeconds()));
            } else {
                Slog.wtf("VcnGatewayConnection", "Unknown transport type or missing TransportInfo/NetworkSpecifier for non-null underlying network");
            }
            builder.setUnderlyingNetworks(List.of(underlyingNetworkRecord.network));
        } else {
            Slog.wtf("VcnGatewayConnection", "No underlying network while building network capabilities", new IllegalStateException());
        }
        return builder.build();
    }

    public static long getSafeModeTimeoutMs(VcnContext vcnContext, TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, ParcelUuid parcelUuid) {
        int i = vcnContext.mIsInTestMode ? 10 : 30;
        PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp = telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(parcelUuid);
        if (vcnContext.mFeatureFlags.safeModeTimeoutConfig() && carrierConfigForSubGrp != null) {
            i = carrierConfigForSubGrp.mBundle.getInt("vcn_safe_mode_timeout_seconds_key", i);
        }
        return TimeUnit.SECONDS.toMillis(i);
    }

    public final void acquireWakeLock() {
        this.mVcnContext.ensureRunningOnLooperThread();
        if (this.mIsQuitting.mValue) {
            return;
        }
        VcnWakeLock vcnWakeLock = this.mWakeLock;
        synchronized (vcnWakeLock) {
            vcnWakeLock.mImpl.acquire();
        }
        Objects.toString(this.mWakeLock);
    }

    public LinkProperties buildConnectedLinkProperties(VcnGatewayConnectionConfig vcnGatewayConnectionConfig, IpSecManager.IpSecTunnelInterface ipSecTunnelInterface, VcnChildSessionConfiguration vcnChildSessionConfiguration, UnderlyingNetworkRecord underlyingNetworkRecord, IkeSessionConnectionInfo ikeSessionConnectionInfo) {
        IkeTunnelConnectionParams tunnelConnectionParams = vcnGatewayConnectionConfig.getTunnelConnectionParams();
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setInterfaceName(ipSecTunnelInterface.getInterfaceName());
        Iterator it = vcnChildSessionConfiguration.mChildConfig.getInternalAddresses().iterator();
        while (it.hasNext()) {
            linkProperties.addLinkAddress((LinkAddress) it.next());
        }
        Iterator it2 = vcnChildSessionConfiguration.mChildConfig.getInternalDnsServers().iterator();
        while (it2.hasNext()) {
            linkProperties.addDnsServer((InetAddress) it2.next());
        }
        int i = 0;
        linkProperties.addRoute(new RouteInfo(new IpPrefix(Inet4Address.ANY, 0), null, null, 1));
        linkProperties.addRoute(new RouteInfo(new IpPrefix(Inet6Address.ANY, 0), null, null, 1));
        if (underlyingNetworkRecord != null) {
            LinkProperties linkProperties2 = underlyingNetworkRecord.linkProperties;
            linkProperties.setTcpBufferSizes(linkProperties2.getTcpBufferSizes());
            int mtu = linkProperties2.getMtu();
            if (mtu != 0 || linkProperties2.getInterfaceName() == null) {
                i = mtu;
            } else {
                Dependencies dependencies = this.mDeps;
                String interfaceName = linkProperties2.getInterfaceName();
                dependencies.getClass();
                try {
                    NetworkInterface byName = NetworkInterface.getByName(interfaceName);
                    if (byName != null) {
                        i = byName.getMTU();
                    }
                } catch (IOException e) {
                    Slog.d("VcnGatewayConnection", "Could not get MTU of underlying network", e);
                }
            }
        } else {
            Slog.wtf("VcnGatewayConnection", "No underlying network while building link properties", new IllegalStateException());
        }
        linkProperties.setMtu(MtuUtils.getMtu(tunnelConnectionParams.getTunnelModeChildSessionParams().getSaProposals(), vcnGatewayConnectionConfig.getMaxMtu(), ikeSessionConnectionInfo.getLocalAddress() instanceof Inet4Address, i));
        return linkProperties;
    }

    public VcnIkeSession buildIkeSession(Network network) {
        int i = this.mCurrentToken + 1;
        this.mCurrentToken = i;
        Dependencies dependencies = this.mDeps;
        VcnContext vcnContext = this.mVcnContext;
        IkeSessionParams.Builder builder = new IkeSessionParams.Builder(this.mConnectionConfig.getTunnelConnectionParams().getIkeSessionParams());
        builder.setNetwork(network);
        IkeSessionParams build = builder.build();
        TunnelModeChildSessionParams tunnelModeChildSessionParams = this.mConnectionConfig.getTunnelConnectionParams().getTunnelModeChildSessionParams();
        IkeSessionCallbackImpl ikeSessionCallbackImpl = new IkeSessionCallbackImpl(i);
        VcnChildSessionCallback vcnChildSessionCallback = new VcnChildSessionCallback(i, false);
        dependencies.getClass();
        return new VcnIkeSession(vcnContext, build, tunnelModeChildSessionParams, ikeSessionCallbackImpl, vcnChildSessionCallback);
    }

    public final void cancelSafeModeAlarm() {
        WakeupMessage wakeupMessage = this.mSafeModeTimeoutAlarm;
        if (wakeupMessage != null) {
            wakeupMessage.cancel();
            this.mSafeModeTimeoutAlarm = null;
        }
        removeEqualMessages(10, null);
    }

    public final WakeupMessage createScheduledAlarm(String str, final Message message, long j) {
        Handler handler = getHandler();
        if (handler != null) {
            Dependencies dependencies = this.mDeps;
            VcnContext vcnContext = this.mVcnContext;
            Runnable runnable = new Runnable() { // from class: com.android.server.vcn.VcnGatewayConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VcnGatewayConnection.$r8$lambda$dQrFsdoY5M3GVBsFSNJpWCtl8SQ(VcnGatewayConnection.this, message);
                }
            };
            dependencies.getClass();
            WakeupMessage wakeupMessage = new WakeupMessage(vcnContext.mContext, handler, str, runnable);
            this.mDeps.getClass();
            wakeupMessage.schedule(SystemClock.elapsedRealtime() + j);
            return wakeupMessage;
        }
        IllegalStateException illegalStateException = new IllegalStateException();
        Slog.w("VcnGatewayConnection", getLogPrefix() + "Attempted to schedule alarm after StateMachine has quit", illegalStateException);
        VcnManagementService.LOCAL_LOG.log("[WARN] " + getTagLogPrefix() + "Attempted to schedule alarm after StateMachine has quit" + illegalStateException);
        return null;
    }

    public ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback getConnectivityDiagnosticsCallback() {
        return this.mConnectivityDiagnosticsCallback;
    }

    public IkeSessionConnectionInfo getIkeConnectionInfo() {
        return this.mIkeConnectionInfo;
    }

    public VcnIkeSession getIkeSession() {
        return this.mIkeSession;
    }

    public final String getLogPrefix() {
        StringBuilder sb = new StringBuilder("(");
        ParcelUuid parcelUuid = this.mSubscriptionGroup;
        sb.append(parcelUuid == null ? null : HexDump.toHexString(parcelUuid.hashCode()));
        sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
        sb.append(this.mConnectionConfig.getGatewayConnectionName());
        sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
        sb.append(System.identityHashCode(this));
        sb.append(") ");
        return sb.toString();
    }

    public VcnNetworkAgent getNetworkAgent() {
        return this.mNetworkAgent;
    }

    public final String getTagLogPrefix() {
        return "[ VcnGatewayConnection " + getLogPrefix() + "]";
    }

    public UnderlyingNetworkRecord getUnderlyingNetwork() {
        return this.mUnderlying;
    }

    public UnderlyingNetworkController.UnderlyingNetworkControllerCallback getUnderlyingNetworkControllerCallback() {
        return this.mUnderlyingNetworkControllerCallback;
    }

    public boolean isQuitting() {
        return this.mIsQuitting.mValue;
    }

    public final void logDbg(String str) {
        Slog.d("VcnGatewayConnection", getLogPrefix() + str);
    }

    public final void logInfo(String str) {
        Slog.i("VcnGatewayConnection", getLogPrefix() + str);
        VcnManagementService.LOCAL_LOG.log("[INFO] " + getTagLogPrefix() + str);
    }

    public final void logWtf(String str) {
        Slog.wtf("VcnGatewayConnection", getLogPrefix() + str);
        VcnManagementService.LOCAL_LOG.log("[WTF ] " + str);
    }

    public final void onQuitting() {
        logInfo("Quitting VcnGatewayConnection");
        if (this.mNetworkAgent != null) {
            logWtf("NetworkAgent was non-null in onQuitting");
            unregister();
            this.mNetworkAgent = null;
        }
        if (this.mIkeSession != null) {
            logWtf("IkeSession was non-null in onQuitting");
            this.mIkeSession.mImpl.kill();
            this.mIkeSession = null;
        }
        IpSecManager.IpSecTunnelInterface ipSecTunnelInterface = this.mTunnelIface;
        if (ipSecTunnelInterface != null) {
            ipSecTunnelInterface.close();
        }
        this.mVcnContext.ensureRunningOnLooperThread();
        VcnWakeLock vcnWakeLock = this.mWakeLock;
        synchronized (vcnWakeLock) {
            vcnWakeLock.mImpl.release();
        }
        Objects.toString(this.mWakeLock);
        WakeupMessage wakeupMessage = this.mTeardownTimeoutAlarm;
        if (wakeupMessage != null) {
            wakeupMessage.cancel();
            this.mTeardownTimeoutAlarm = null;
        }
        removeEqualMessages(8, null);
        WakeupMessage wakeupMessage2 = this.mDisconnectRequestAlarm;
        if (wakeupMessage2 != null) {
            wakeupMessage2.cancel();
            this.mDisconnectRequestAlarm = null;
        }
        removeEqualMessages(7, new EventDisconnectRequestedInfo("Underlying Network lost", false));
        WakeupMessage wakeupMessage3 = this.mRetryTimeoutAlarm;
        if (wakeupMessage3 != null) {
            wakeupMessage3.cancel();
            this.mRetryTimeoutAlarm = null;
        }
        removeEqualMessages(2, null);
        cancelSafeModeAlarm();
        UnderlyingNetworkController underlyingNetworkController = this.mUnderlyingNetworkController;
        VcnContext vcnContext = underlyingNetworkController.mVcnContext;
        vcnContext.ensureRunningOnLooperThread();
        underlyingNetworkController.mIsQuitting = true;
        underlyingNetworkController.registerOrUpdateNetworkRequests();
        ((TelephonyManager) vcnContext.mContext.getSystemService(TelephonyManager.class)).unregisterTelephonyCallback(underlyingNetworkController.mActiveDataSubIdListener);
        this.mGatewayStatusCallback.onQuit();
        this.mConnectivityDiagnosticsManager.unregisterConnectivityDiagnosticsCallback(this.mConnectivityDiagnosticsCallback);
    }

    public final void removeEqualMessages(int i, EventDisconnectRequestedInfo eventDisconnectRequestedInfo) {
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeEqualMessages(i, eventDisconnectRequestedInfo);
        }
        Handler handler2 = getHandler();
        if (handler2 == null || !handler2.hasMessagesOrCallbacks()) {
            this.mVcnContext.ensureRunningOnLooperThread();
            VcnWakeLock vcnWakeLock = this.mWakeLock;
            synchronized (vcnWakeLock) {
                vcnWakeLock.mImpl.release();
            }
            Objects.toString(this.mWakeLock);
        }
    }

    public void sendDisconnectRequestedAndAcquireWakelock(String str, boolean z) {
        sendMessageAndAcquireWakeLock(7, Integer.MIN_VALUE, new EventDisconnectRequestedInfo(str, z));
    }

    public final void sendMessage(int i) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i);
    }

    public final void sendMessage(int i, int i2) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, i2);
    }

    public final void sendMessage(int i, int i2, int i3) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, i2, i3);
    }

    public final void sendMessage(int i, int i2, int i3, Object obj) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, i2, i3, obj);
    }

    public final void sendMessage(int i, Object obj) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(i, obj);
    }

    public final void sendMessage(Message message) {
        logWtf("sendMessage should not be used in VcnGatewayConnection. See sendMessageAndAcquireWakeLock()");
        super.sendMessage(message);
    }

    public final void sendMessageAndAcquireWakeLock(int i, int i2) {
        acquireWakeLock();
        super.sendMessage(i, i2);
    }

    public final void sendMessageAndAcquireWakeLock(int i, int i2, EventInfo eventInfo) {
        acquireWakeLock();
        super.sendMessage(i, i2, Integer.MIN_VALUE, eventInfo);
    }

    public void setIkeSession(VcnIkeSession vcnIkeSession) {
        this.mIkeSession = vcnIkeSession;
    }

    public void setNetworkAgent(VcnNetworkAgent vcnNetworkAgent) {
        this.mNetworkAgent = vcnNetworkAgent;
    }

    public void setQuitting() {
        this.mIsQuitting.mValue = true;
    }

    public void setSafeModeAlarm() {
        if ((!this.mVcnContext.mFeatureFlags.safeModeConfig() || this.mConnectionConfig.isSafeModeEnabled()) && this.mSafeModeTimeoutAlarm == null) {
            this.mSafeModeTimeoutAlarm = createScheduledAlarm(SAFEMODE_TIMEOUT_ALARM, obtainMessage(10, Integer.MIN_VALUE), getSafeModeTimeoutMs(this.mVcnContext, this.mLastSnapshot, this.mSubscriptionGroup));
        }
    }

    public void setTunnelInterface(IpSecManager.IpSecTunnelInterface ipSecTunnelInterface) {
        this.mTunnelIface = ipSecTunnelInterface;
    }

    public void setUnderlyingNetwork(UnderlyingNetworkRecord underlyingNetworkRecord) {
        this.mUnderlying = underlyingNetworkRecord;
    }

    public final void teardownAsynchronously() {
        logDbg("Triggering async teardown");
        sendDisconnectRequestedAndAcquireWakelock("teardown() called on VcnTunnel", true);
    }
}
