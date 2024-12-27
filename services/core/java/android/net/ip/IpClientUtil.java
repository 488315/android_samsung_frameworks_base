package android.net.ip;

import android.content.Context;
import android.net.DhcpResultsParcelable;
import android.net.LinkProperties;
import android.net.ip.IIpClientCallbacks;
import android.net.networkstack.ModuleNetworkStackClient;
import android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable;
import android.os.ConditionVariable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class IpClientUtil {
    public static final String DUMP_ARG = "ipclient";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IpClientCallbacksProxy extends IIpClientCallbacks.Stub {
        public final IpClientCallbacks mCb;

        public IpClientCallbacksProxy(IpClientCallbacks ipClientCallbacks) {
            this.mCb = ipClientCallbacks;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final String getInterfaceHash() {
            return "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final int getInterfaceVersion() {
            return 21;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void installPacketFilter(byte[] bArr) {
            this.mCb.installPacketFilter(bArr);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onIpClientCreated(IIpClient iIpClient) {
            this.mCb.onIpClientCreated(iIpClient);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onLinkPropertiesChange(LinkProperties linkProperties) {
            this.mCb.onLinkPropertiesChange(new LinkProperties(linkProperties));
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onNewDhcpResults(DhcpResultsParcelable dhcpResultsParcelable) {
            this.mCb.onNewDhcpResults(dhcpResultsParcelable);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onPostDhcpAction() {
            this.mCb.onPostDhcpAction();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onPreDhcpAction() {
            this.mCb.onPreDhcpAction();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onPreconnectionStart(List list) {
            this.mCb.onPreconnectionStart(list);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onProvisioningFailure(LinkProperties linkProperties) {
            this.mCb.onProvisioningFailure(new LinkProperties(linkProperties));
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onProvisioningSuccess(LinkProperties linkProperties) {
            this.mCb.onProvisioningSuccess(new LinkProperties(linkProperties));
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onQuit() {
            this.mCb.onQuit();
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onReachabilityFailure(ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) {
            this.mCb.onReachabilityFailure(reachabilityLossInfoParcelable);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void onReachabilityLost(String str) {
            this.mCb.onReachabilityLost(str);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void setFallbackMulticastFilter(boolean z) {
            this.mCb.setFallbackMulticastFilter(z);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void setMaxDtimMultiplier(int i) {
            this.mCb.setMaxDtimMultiplier(i);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void setNeighborDiscoveryOffload(boolean z) {
            this.mCb.setNeighborDiscoveryOffload(z);
        }

        @Override // android.net.ip.IIpClientCallbacks
        public final void startReadPacketFilter() {
            this.mCb.startReadPacketFilter();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class WaitForProvisioningCallbacks extends IpClientCallbacks {
        private final ConditionVariable mCV = new ConditionVariable();
        private LinkProperties mCallbackLinkProperties;

        @Override // android.net.ip.IpClientCallbacks
        public void onProvisioningFailure(LinkProperties linkProperties) {
            this.mCallbackLinkProperties = null;
            this.mCV.open();
        }

        @Override // android.net.ip.IpClientCallbacks
        public void onProvisioningSuccess(LinkProperties linkProperties) {
            this.mCallbackLinkProperties = linkProperties;
            this.mCV.open();
        }

        public LinkProperties waitForProvisioning() {
            this.mCV.block();
            return this.mCallbackLinkProperties;
        }
    }

    public static void dumpIpClient(IIpClient iIpClient, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("IpClient logs have moved to dumpsys network_stack");
    }

    public static void makeIpClient(Context context, String str, IpClientCallbacks ipClientCallbacks) {
        ModuleNetworkStackClient.getInstance(context).makeIpClient(str, new IpClientCallbacksProxy(ipClientCallbacks));
    }
}
