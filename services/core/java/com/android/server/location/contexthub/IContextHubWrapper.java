package com.android.server.location.contexthub;

import android.chre.flags.Flags;
import android.hardware.contexthub.ContextHubInfo;
import android.hardware.contexthub.ContextHubMessage;
import android.hardware.contexthub.HostEndpointInfo;
import android.hardware.contexthub.IContextHub;
import android.hardware.contexthub.IContextHubCallback;
import android.hardware.contexthub.MessageDeliveryStatus;
import android.hardware.contexthub.NanSessionRequest;
import android.hardware.contexthub.NanoappBinary;
import android.hardware.contexthub.NanoappInfo;
import android.hardware.contexthub.NanoappRpcService;
import android.hardware.contexthub.V1_0.ContextHub;
import android.hardware.contexthub.V1_0.ContextHubMsg;
import android.hardware.contexthub.V1_0.HubAppInfo;
import android.hardware.contexthub.V1_0.IContexthub;
import android.hardware.contexthub.V1_2.IContexthub;
import android.hardware.contexthub.V1_2.IContexthubCallback;
import android.hardware.location.NanoAppBinary;
import android.hardware.location.NanoAppMessage;
import android.hardware.location.NanoAppRpcService;
import android.hardware.location.NanoAppState;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.Pair;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public abstract class IContextHubWrapper {

    public final class ContextHubWrapperAidl extends IContextHubWrapper
            implements IBinder.DeathRecipient {
        public final Map mAidlCallbackMap = new HashMap();
        public IContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0
                mHandleServiceRestartCallback = null;
        public final Handler mHandler;
        public IContextHub mHub;

        public final class ContextHubAidlCallback extends IContextHubCallback.Stub {
            public static final byte[] UUID = {
                -102, 23, 0, -115, 107, -15, 68, 90, -112, 17, 109, 33, -67, -104, 91, 108
            };
            public final ContextHubService.ContextHubServiceCallback mCallback;

            public ContextHubAidlCallback(
                    ContextHubService.ContextHubServiceCallback contextHubServiceCallback) {
                this.mCallback = contextHubServiceCallback;
            }

            public final String getInterfaceHash() {
                return "03f1982c8e20e58494a4ff8c9736b1c257dfeb6c";
            }

            public final int getInterfaceVersion() {
                return 3;
            }

            public final String getName() {
                return "ContextHubService";
            }

            public final byte[] getUuid() {
                return UUID;
            }

            public final void handleContextHubAsyncEvent(final int i) {
                ContextHubWrapperAidl.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback
                                        contextHubAidlCallback =
                                                IContextHubWrapper.ContextHubWrapperAidl
                                                        .ContextHubAidlCallback.this;
                                int i2 = i;
                                ContextHubService.ContextHubServiceCallback
                                        contextHubServiceCallback =
                                                contextHubAidlCallback.mCallback;
                                DateTimeFormatter dateTimeFormatter =
                                        ContextHubServiceUtil.DATE_FORMATTER;
                                int i3 = 1;
                                if (i2 != 1) {
                                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                                            i2,
                                            "toContextHubEventFromAidl: Unknown event type: ",
                                            "ContextHubServiceUtil");
                                    i3 = 0;
                                }
                                contextHubServiceCallback.handleContextHubEvent(i3);
                            }
                        });
            }

            public final void handleContextHubMessage(
                    final ContextHubMessage contextHubMessage, final String[] strArr) {
                ContextHubWrapperAidl.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback
                                        contextHubAidlCallback =
                                                IContextHubWrapper.ContextHubWrapperAidl
                                                        .ContextHubAidlCallback.this;
                                ContextHubMessage contextHubMessage2 = contextHubMessage;
                                String[] strArr2 = strArr;
                                ContextHubService.ContextHubServiceCallback
                                        contextHubServiceCallback =
                                                contextHubAidlCallback.mCallback;
                                char c = contextHubMessage2.hostEndPoint;
                                short s = (short) c;
                                DateTimeFormatter dateTimeFormatter =
                                        ContextHubServiceUtil.DATE_FORMATTER;
                                contextHubServiceCallback.handleNanoappMessage(
                                        s,
                                        NanoAppMessage.createMessageFromNanoApp(
                                                contextHubMessage2.nanoappId,
                                                contextHubMessage2.messageType,
                                                contextHubMessage2.messageBody,
                                                c == 65535,
                                                contextHubMessage2.isReliable,
                                                contextHubMessage2.messageSequenceNumber),
                                        new ArrayList(
                                                Arrays.asList(contextHubMessage2.permissions)),
                                        new ArrayList(Arrays.asList(strArr2)));
                            }
                        });
            }

            public final void handleMessageDeliveryStatus(
                    char c, MessageDeliveryStatus messageDeliveryStatus) {
                if (Flags.reliableMessageImplementation()) {
                    ContextHubWrapperAidl.this.mHandler.post(
                            new IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda0(
                                    this, messageDeliveryStatus, 1));
                } else {
                    Log.w(
                            "IContextHubWrapper",
                            "handleMessageDeliveryStatus called when the"
                                + " reliableMessageImplementation flag is disabled");
                }
            }

            public final void handleNanSessionRequest(NanSessionRequest nanSessionRequest) {}

            public final void handleNanoappInfo(NanoappInfo[] nanoappInfoArr) {
                int i = 0;
                DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
                ArrayList arrayList = new ArrayList();
                for (NanoappInfo nanoappInfo : nanoappInfoArr) {
                    ArrayList arrayList2 = new ArrayList();
                    for (NanoappRpcService nanoappRpcService : nanoappInfo.rpcServices) {
                        arrayList2.add(
                                new NanoAppRpcService(
                                        nanoappRpcService.id, nanoappRpcService.version));
                    }
                    arrayList.add(
                            new NanoAppState(
                                    nanoappInfo.nanoappId,
                                    nanoappInfo.nanoappVersion,
                                    nanoappInfo.enabled,
                                    new ArrayList(Arrays.asList(nanoappInfo.permissions)),
                                    arrayList2));
                }
                ContextHubWrapperAidl.this.mHandler.post(
                        new IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda0(
                                this, arrayList, i));
            }

            public final void handleTransactionResult(final int i, final boolean z) {
                ContextHubWrapperAidl.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.location.contexthub.IContextHubWrapper$ContextHubWrapperAidl$ContextHubAidlCallback$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                IContextHubWrapper.ContextHubWrapperAidl.ContextHubAidlCallback
                                        contextHubAidlCallback =
                                                IContextHubWrapper.ContextHubWrapperAidl
                                                        .ContextHubAidlCallback.this;
                                contextHubAidlCallback.mCallback.handleTransactionResult(i, z);
                            }
                        });
            }
        }

        public ContextHubWrapperAidl(IContextHub iContextHub) {
            HandlerThread handlerThread = new HandlerThread("Context Hub AIDL callback", 10);
            synchronized (this) {
                this.mHub = iContextHub;
            }
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
            IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
                Log.e(
                        "IContextHubWrapper",
                        "Context Hub AIDL service death receipt could not be linked");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.i("IContextHubWrapper", "Context Hub AIDL HAL died");
            IContextHub maybeConnectToAidlGetProxy =
                    IContextHubWrapper.maybeConnectToAidlGetProxy();
            synchronized (this) {
                this.mHub = maybeConnectToAidlGetProxy;
            }
            if (getHub() == null) {
                Log.e("IContextHubWrapper", "Could not reconnect to Context Hub AIDL HAL");
                return;
            }
            IContextHub hub = getHub();
            if (hub != null) {
                try {
                    hub.asBinder().linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    Log.e(
                            "IContextHubWrapper",
                            "Context Hub AIDL service death receipt could not be linked");
                }
            }
            IContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0
                    iContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0 =
                            this.mHandleServiceRestartCallback;
            if (iContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0 != null) {
                iContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0.run();
            } else {
                Log.e("IContextHubWrapper", "mHandleServiceRestartCallback is not set");
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int disableNanoapp(int i, int i2, long j) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.disableNanoapp(i, j, i2);
                return 0;
            } catch (RemoteException
                    | ServiceSpecificException
                    | UnsupportedOperationException unused) {
                return 1;
            } catch (IllegalArgumentException unused2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int enableNanoapp(int i, int i2, long j) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.enableNanoapp(i, j, i2);
                return 0;
            } catch (RemoteException
                    | ServiceSpecificException
                    | UnsupportedOperationException unused) {
                return 1;
            } catch (IllegalArgumentException unused2) {
                return 2;
            }
        }

        public final synchronized IContextHub getHub() {
            return this.mHub;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final Pair getHubs() {
            IContextHub hub = getHub();
            if (hub == null) {
                return new Pair(new ArrayList(), new ArrayList());
            }
            HashSet hashSet = new HashSet();
            ArrayList arrayList = new ArrayList();
            for (ContextHubInfo contextHubInfo : hub.getContextHubs()) {
                arrayList.add(new android.hardware.location.ContextHubInfo(contextHubInfo));
                for (String str : contextHubInfo.supportedPermissions) {
                    hashSet.add(str);
                }
            }
            return new Pair(arrayList, new ArrayList(hashSet));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final long[] getPreloadedNanoappIds(int i) {
            IContextHub hub = getHub();
            if (hub == null) {
                return null;
            }
            try {
                return hub.getPreloadedNanoappIds(i);
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Exception while getting preloaded nanoapp IDs: "),
                        "IContextHubWrapper");
                return null;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int loadNanoapp(int i, NanoAppBinary nanoAppBinary, int i2) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
            NanoappBinary nanoappBinary = new NanoappBinary();
            nanoappBinary.nanoappId = nanoAppBinary.getNanoAppId();
            nanoappBinary.nanoappVersion = nanoAppBinary.getNanoAppVersion();
            nanoappBinary.flags = nanoAppBinary.getFlags();
            nanoappBinary.targetChreApiMajorVersion = nanoAppBinary.getTargetChreApiMajorVersion();
            nanoappBinary.targetChreApiMinorVersion = nanoAppBinary.getTargetChreApiMinorVersion();
            nanoappBinary.customBinary = new byte[0];
            try {
                nanoappBinary.customBinary = nanoAppBinary.getBinaryNoHeader();
            } catch (IndexOutOfBoundsException e) {
                Log.w("ContextHubServiceUtil", e.getMessage());
            } catch (NullPointerException unused) {
                Log.w("ContextHubServiceUtil", "NanoApp binary was null");
            }
            try {
                hub.loadNanoapp(i, nanoappBinary, i2);
                return 0;
            } catch (RemoteException
                    | ServiceSpecificException
                    | UnsupportedOperationException unused2) {
                return 1;
            } catch (IllegalArgumentException unused3) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onAirplaneModeSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 4);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onBtMainSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 6);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onBtScanningSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 7);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onHostEndpointConnected(HostEndpointInfo hostEndpointInfo) {
            IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.onHostEndpointConnected(hostEndpointInfo);
            } catch (RemoteException | ServiceSpecificException e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Exception in onHostEndpointConnected"),
                        "IContextHubWrapper");
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onHostEndpointDisconnected(short s) {
            IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.onHostEndpointDisconnected((char) s);
            } catch (RemoteException | ServiceSpecificException e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Exception in onHostEndpointDisconnected"),
                        "IContextHubWrapper");
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onLocationSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 1);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onMicrophoneSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 5);
        }

        public final void onSettingChanged(boolean z, byte b) {
            IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            try {
                hub.onSettingChanged(b, z);
            } catch (RemoteException | ServiceSpecificException e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Exception while sending setting update: "),
                        "IContextHubWrapper");
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiMainSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 2);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiScanningSettingChanged(boolean z) {
            onSettingChanged(z, (byte) 3);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiSettingChanged(boolean z) {}

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int queryNanoapps(int i) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.queryNanoapps(i);
                return 0;
            } catch (RemoteException
                    | ServiceSpecificException
                    | UnsupportedOperationException unused) {
                return 1;
            } catch (IllegalArgumentException unused2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void registerCallback(
                int i, ContextHubService.ContextHubServiceCallback contextHubServiceCallback) {
            if (getHub() == null) {
                return;
            }
            this.mHandleServiceRestartCallback =
                    new IContextHubWrapper$ContextHubWrapperAidl$$ExternalSyntheticLambda0(
                            contextHubServiceCallback);
            ((HashMap) this.mAidlCallbackMap)
                    .put(Integer.valueOf(i), new ContextHubAidlCallback(contextHubServiceCallback));
            registerExistingCallback(i);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void registerExistingCallback(int i) {
            IContextHub hub = getHub();
            if (hub == null) {
                return;
            }
            ContextHubAidlCallback contextHubAidlCallback =
                    (ContextHubAidlCallback)
                            ((HashMap) this.mAidlCallbackMap).get(Integer.valueOf(i));
            if (contextHubAidlCallback == null) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        i,
                        "Could not find existing callback to register for context hub ID = ",
                        "IContextHubWrapper");
                return;
            }
            try {
                hub.registerCallback(i, contextHubAidlCallback);
            } catch (RemoteException | ServiceSpecificException | IllegalArgumentException e) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Exception while registering callback: "),
                        "IContextHubWrapper");
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int sendMessageDeliveryStatusToContextHub(
                int i, MessageDeliveryStatus messageDeliveryStatus) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.sendMessageDeliveryStatusToHub(i, messageDeliveryStatus);
                return 0;
            } catch (RemoteException | ServiceSpecificException unused) {
                return 1;
            } catch (IllegalArgumentException unused2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int sendMessageToContextHub(short s, int i, NanoAppMessage nanoAppMessage) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.sendMessageToHub(
                        i, ContextHubServiceUtil.createAidlContextHubMessage(s, nanoAppMessage));
                return 0;
            } catch (RemoteException | ServiceSpecificException unused) {
                return 1;
            } catch (IllegalArgumentException unused2) {
                return 2;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean setTestMode(boolean z) {
            IContextHub hub = getHub();
            if (hub == null) {
                return false;
            }
            try {
                hub.setTestMode(z);
                return true;
            } catch (RemoteException | ServiceSpecificException e) {
                StringBuilder sb = new StringBuilder("Exception while setting test mode (enable: ");
                sb.append(z ? "true" : "false");
                sb.append("): ");
                sb.append(e.getMessage());
                Log.e("IContextHubWrapper", sb.toString());
                return false;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsAirplaneModeSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsBtSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsLocationSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsMicrophoneSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsWifiSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int unloadNanoapp(int i, int i2, long j) {
            IContextHub hub = getHub();
            if (hub == null) {
                return 2;
            }
            try {
                hub.unloadNanoapp(i, j, i2);
                return 0;
            } catch (RemoteException
                    | ServiceSpecificException
                    | UnsupportedOperationException unused) {
                return 1;
            } catch (IllegalArgumentException unused2) {
                return 2;
            }
        }
    }

    public abstract class ContextHubWrapperHidl extends IContextHubWrapper {
        public final Map mHidlCallbackMap = new HashMap();
        public final IContexthub mHub;

        public final class ContextHubWrapperHidlCallback extends IContexthubCallback.Stub {
            public final ContextHubService.ContextHubServiceCallback mCallback;

            public ContextHubWrapperHidlCallback(
                    ContextHubService.ContextHubServiceCallback contextHubServiceCallback) {
                this.mCallback = contextHubServiceCallback;
            }

            public final void handleAppAbort(final long j, final int i) {
                ContextHubService.ContextHubServiceCallback contextHubServiceCallback =
                        this.mCallback;
                ContextHubClientManager contextHubClientManager =
                        ContextHubService.this.mClientManager;
                contextHubClientManager.getClass();
                contextHubClientManager.forEachClientOfHub(
                        contextHubServiceCallback.mContextHubId,
                        new Consumer() { // from class:
                                         // com.android.server.location.contexthub.ContextHubClientManager$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                long j2 = j;
                                int i2 = i;
                                ContextHubClientBroker contextHubClientBroker =
                                        (ContextHubClientBroker) obj;
                                contextHubClientBroker.getClass();
                                contextHubClientBroker.invokeCallback(
                                        new ContextHubClientBroker$$ExternalSyntheticLambda2(
                                                i2, 1, j2));
                                ContextHubClientBroker$$ExternalSyntheticLambda3
                                        contextHubClientBroker$$ExternalSyntheticLambda3 =
                                                new ContextHubClientBroker$$ExternalSyntheticLambda3(
                                                        contextHubClientBroker, j2, i2, 1);
                                synchronized (contextHubClientBroker) {
                                    contextHubClientBroker.sendPendingIntent(
                                            contextHubClientBroker$$ExternalSyntheticLambda3,
                                            j2,
                                            null);
                                }
                            }
                        });
            }

            public final void handleAppsInfo(ArrayList arrayList) {
                DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    HubAppInfo hubAppInfo = (HubAppInfo) it.next();
                    android.hardware.contexthub.V1_2.HubAppInfo hubAppInfo2 =
                            new android.hardware.contexthub.V1_2.HubAppInfo();
                    HubAppInfo hubAppInfo3 = hubAppInfo2.info_1_0;
                    hubAppInfo3.appId = hubAppInfo.appId;
                    hubAppInfo3.version = hubAppInfo.version;
                    hubAppInfo3.memUsage = hubAppInfo.memUsage;
                    hubAppInfo3.enabled = hubAppInfo.enabled;
                    hubAppInfo2.permissions = new ArrayList();
                    arrayList2.add(hubAppInfo2);
                }
                handleAppsInfo_1_2(arrayList2);
            }

            public final void handleAppsInfo_1_2(ArrayList arrayList) {
                DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
                ArrayList arrayList2 = new ArrayList();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.hardware.contexthub.V1_2.HubAppInfo hubAppInfo =
                            (android.hardware.contexthub.V1_2.HubAppInfo) it.next();
                    HubAppInfo hubAppInfo2 = hubAppInfo.info_1_0;
                    arrayList2.add(
                            new NanoAppState(
                                    hubAppInfo2.appId,
                                    hubAppInfo2.version,
                                    hubAppInfo2.enabled,
                                    hubAppInfo.permissions));
                }
                this.mCallback.handleNanoappInfo(arrayList2);
            }

            public final void handleClientMsg(ContextHubMsg contextHubMsg) {
                this.mCallback.handleNanoappMessage(
                        contextHubMsg.hostEndPoint,
                        ContextHubServiceUtil.createNanoAppMessage(contextHubMsg),
                        Collections.emptyList(),
                        Collections.emptyList());
            }

            public final void handleClientMsg_1_2(
                    android.hardware.contexthub.V1_2.ContextHubMsg contextHubMsg,
                    ArrayList arrayList) {
                ContextHubService.ContextHubServiceCallback contextHubServiceCallback =
                        this.mCallback;
                ContextHubMsg contextHubMsg2 = contextHubMsg.msg_1_0;
                contextHubServiceCallback.handleNanoappMessage(
                        contextHubMsg2.hostEndPoint,
                        ContextHubServiceUtil.createNanoAppMessage(contextHubMsg2),
                        contextHubMsg.permissions,
                        arrayList);
            }

            public final void handleHubEvent(int i) {
                ContextHubService.ContextHubServiceCallback contextHubServiceCallback =
                        this.mCallback;
                DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
                int i2 = 1;
                if (i != 1) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            i, "toContextHubEvent: Unknown event type: ", "ContextHubServiceUtil");
                    i2 = 0;
                }
                contextHubServiceCallback.handleContextHubEvent(i2);
            }

            public final void handleTxnResult(int i, int i2) {
                this.mCallback.handleTransactionResult(i, i2 == 0);
            }
        }

        public ContextHubWrapperHidl(IContexthub iContexthub) {
            this.mHub = iContexthub;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int disableNanoapp(int i, int i2, long j) {
            return ContextHubServiceUtil.toTransactionResult(this.mHub.disableNanoApp(i, j, i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int enableNanoapp(int i, int i2, long j) {
            return ContextHubServiceUtil.toTransactionResult(this.mHub.enableNanoApp(i, j, i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final long[] getPreloadedNanoappIds(int i) {
            return new long[0];
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int loadNanoapp(int i, NanoAppBinary nanoAppBinary, int i2) {
            DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
            android.hardware.contexthub.V1_0.NanoAppBinary nanoAppBinary2 =
                    new android.hardware.contexthub.V1_0.NanoAppBinary();
            nanoAppBinary2.appId = nanoAppBinary.getNanoAppId();
            nanoAppBinary2.appVersion = nanoAppBinary.getNanoAppVersion();
            nanoAppBinary2.flags = nanoAppBinary.getFlags();
            nanoAppBinary2.targetChreApiMajorVersion = nanoAppBinary.getTargetChreApiMajorVersion();
            nanoAppBinary2.targetChreApiMinorVersion = nanoAppBinary.getTargetChreApiMinorVersion();
            try {
                byte[] binaryNoHeader = nanoAppBinary.getBinaryNoHeader();
                ArrayList arrayList = nanoAppBinary2.customBinary;
                arrayList.clear();
                arrayList.ensureCapacity(binaryNoHeader.length);
                for (byte b : binaryNoHeader) {
                    arrayList.add(Byte.valueOf(b));
                }
            } catch (IndexOutOfBoundsException e) {
                Log.w("ContextHubServiceUtil", e.getMessage());
            } catch (NullPointerException unused) {
                Log.w("ContextHubServiceUtil", "NanoApp binary was null");
            }
            return ContextHubServiceUtil.toTransactionResult(
                    this.mHub.loadNanoApp(i, nanoAppBinary2, i2));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onBtMainSettingChanged(boolean z) {}

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onBtScanningSettingChanged(boolean z) {}

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiMainSettingChanged(boolean z) {}

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiScanningSettingChanged(boolean z) {}

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int queryNanoapps(int i) {
            return ContextHubServiceUtil.toTransactionResult(this.mHub.queryApps(i));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public void registerCallback(
                int i, ContextHubService.ContextHubServiceCallback contextHubServiceCallback) {
            ((HashMap) this.mHidlCallbackMap)
                    .put(
                            Integer.valueOf(i),
                            new ContextHubWrapperHidlCallback(contextHubServiceCallback));
            this.mHub.registerCallback(
                    i,
                    (android.hardware.contexthub.V1_0.IContexthubCallback)
                            ((HashMap) this.mHidlCallbackMap).get(Integer.valueOf(i)));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void registerExistingCallback(int i) {
            ContextHubWrapperHidlCallback contextHubWrapperHidlCallback =
                    (ContextHubWrapperHidlCallback)
                            ((HashMap) this.mHidlCallbackMap).get(Integer.valueOf(i));
            if (contextHubWrapperHidlCallback == null) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        i,
                        "Could not find existing callback for context hub with ID = ",
                        "IContextHubWrapper");
            } else {
                this.mHub.registerCallback(i, contextHubWrapperHidlCallback);
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int sendMessageDeliveryStatusToContextHub(
                int i, MessageDeliveryStatus messageDeliveryStatus) {
            return 9;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int sendMessageToContextHub(short s, int i, NanoAppMessage nanoAppMessage) {
            if (nanoAppMessage.isReliable()) {
                Log.e(
                        "IContextHubWrapper",
                        "Reliable messages are only supported with the AIDL HAL");
                return 2;
            }
            DateTimeFormatter dateTimeFormatter = ContextHubServiceUtil.DATE_FORMATTER;
            ContextHubMsg contextHubMsg = new ContextHubMsg();
            contextHubMsg.appName = nanoAppMessage.getNanoAppId();
            contextHubMsg.hostEndPoint = s;
            contextHubMsg.msgType = nanoAppMessage.getMessageType();
            byte[] messageBody = nanoAppMessage.getMessageBody();
            ArrayList arrayList = contextHubMsg.msg;
            arrayList.clear();
            arrayList.ensureCapacity(messageBody.length);
            for (byte b : messageBody) {
                arrayList.add(Byte.valueOf(b));
            }
            return ContextHubServiceUtil.toTransactionResult(
                    this.mHub.sendMessageToHub(i, contextHubMsg));
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean setTestMode(boolean z) {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsBtSettingNotifications() {
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final int unloadNanoapp(int i, int i2, long j) {
            return ContextHubServiceUtil.toTransactionResult(this.mHub.unloadNanoApp(i, j, i2));
        }
    }

    public final class ContextHubWrapperV1_0 extends ContextHubWrapperHidl {
        public final /* synthetic */ int $r8$classId;
        public Object mHub;

        public /* synthetic */ ContextHubWrapperV1_0(IContexthub iContexthub, int i) {
            super(iContexthub);
            this.$r8$classId = i;
        }

        private final void
                onAirplaneModeSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_0(
                        boolean z) {}

        private final void
                onAirplaneModeSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_1(
                        boolean z) {}

        private final void
                onLocationSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_0(
                        boolean z) {}

        private final void
                onMicrophoneSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_0(
                        boolean z) {}

        private final void
                onMicrophoneSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_1(
                        boolean z) {}

        private final void
                onWifiSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_0(
                        boolean z) {}

        private final void
                onWifiSettingChanged$com$android$server$location$contexthub$IContextHubWrapper$ContextHubWrapperV1_1(
                        boolean z) {}

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final Pair getHubs() {
            switch (this.$r8$classId) {
                case 0:
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((IContexthub) this.mHub).getHubs().iterator();
                    while (it.hasNext()) {
                        arrayList.add(
                                new android.hardware.location.ContextHubInfo(
                                        (ContextHub) it.next()));
                    }
                    return new Pair(arrayList, new ArrayList());
                default:
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it2 =
                            ((android.hardware.contexthub.V1_1.IContexthub) this.mHub)
                                    .getHubs()
                                    .iterator();
                    while (it2.hasNext()) {
                        arrayList2.add(
                                new android.hardware.location.ContextHubInfo(
                                        (ContextHub) it2.next()));
                    }
                    return new Pair(arrayList2, new ArrayList());
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onAirplaneModeSettingChanged(boolean z) {
            int i = this.$r8$classId;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onLocationSettingChanged(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    break;
                default:
                    try {
                        ((android.hardware.contexthub.V1_1.IContexthub) this.mHub)
                                .onSettingChanged((byte) 0, z ? (byte) 1 : (byte) 0);
                        break;
                    } catch (RemoteException e) {
                        Log.e(
                                "IContextHubWrapper",
                                "Failed to send setting change to Contexthub",
                                e);
                    }
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onMicrophoneSettingChanged(boolean z) {
            int i = this.$r8$classId;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiSettingChanged(boolean z) {
            int i = this.$r8$classId;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsAirplaneModeSettingNotifications() {
            switch (this.$r8$classId) {
            }
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsLocationSettingNotifications() {
            switch (this.$r8$classId) {
                case 0:
                    return false;
                default:
                    return true;
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsMicrophoneSettingNotifications() {
            switch (this.$r8$classId) {
            }
            return false;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsWifiSettingNotifications() {
            switch (this.$r8$classId) {
            }
            return false;
        }
    }

    public final class ContextHubWrapperV1_2 extends ContextHubWrapperHidl
            implements IContexthub.getHubs_1_2Callback {
        public final android.hardware.contexthub.V1_2.IContexthub mHub;
        public Pair mHubInfo;

        public ContextHubWrapperV1_2(android.hardware.contexthub.V1_2.IContexthub iContexthub) {
            super(iContexthub);
            this.mHubInfo = new Pair(Collections.emptyList(), Collections.emptyList());
            this.mHub = iContexthub;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final Pair getHubs() {
            this.mHub.getHubs_1_2(this);
            return this.mHubInfo;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onAirplaneModeSettingChanged(boolean z) {
            sendSettingChanged((byte) 2, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onLocationSettingChanged(boolean z) {
            sendSettingChanged((byte) 0, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onMicrophoneSettingChanged(boolean z) {
            sendSettingChanged((byte) 3, z ? (byte) 1 : (byte) 0);
        }

        public final void onValues(ArrayList arrayList, ArrayList arrayList2) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList3.add(
                        new android.hardware.location.ContextHubInfo((ContextHub) it.next()));
            }
            this.mHubInfo = new Pair(arrayList3, arrayList2);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final void onWifiSettingChanged(boolean z) {
            sendSettingChanged((byte) 1, z ? (byte) 1 : (byte) 0);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ContextHubWrapperHidl, com.android.server.location.contexthub.IContextHubWrapper
        public final void registerCallback(
                int i, ContextHubService.ContextHubServiceCallback contextHubServiceCallback) {
            ((HashMap) this.mHidlCallbackMap)
                    .put(
                            Integer.valueOf(i),
                            new ContextHubWrapperHidl.ContextHubWrapperHidlCallback(
                                    contextHubServiceCallback));
            this.mHub.registerCallback_1_2(
                    i,
                    (IContexthubCallback)
                            ((HashMap) this.mHidlCallbackMap).get(Integer.valueOf(i)));
        }

        public final void sendSettingChanged(byte b, byte b2) {
            try {
                this.mHub.onSettingChanged_1_2(b, b2);
            } catch (RemoteException e) {
                Log.e("IContextHubWrapper", "Failed to send setting change to Contexthub", e);
            }
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsAirplaneModeSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsLocationSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsMicrophoneSettingNotifications() {
            return true;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper
        public final boolean supportsWifiSettingNotifications() {
            return true;
        }
    }

    public static IContextHub maybeConnectToAidlGetProxy() {
        String str = IContextHub.class.getCanonicalName() + "/default";
        if (!ServiceManager.isDeclared(str)) {
            Log.d("IContextHubWrapper", "Context Hub AIDL service is not declared");
            return null;
        }
        IContextHub asInterface = IContextHub.Stub.asInterface(ServiceManager.waitForService(str));
        if (asInterface != null) {
            return asInterface;
        }
        Log.e("IContextHubWrapper", "Context Hub AIDL service was declared but was not found");
        return asInterface;
    }

    public abstract int disableNanoapp(int i, int i2, long j);

    public abstract int enableNanoapp(int i, int i2, long j);

    public abstract Pair getHubs();

    public abstract long[] getPreloadedNanoappIds(int i);

    public abstract int loadNanoapp(int i, NanoAppBinary nanoAppBinary, int i2);

    public abstract void onAirplaneModeSettingChanged(boolean z);

    public abstract void onBtMainSettingChanged(boolean z);

    public abstract void onBtScanningSettingChanged(boolean z);

    public void onHostEndpointConnected(HostEndpointInfo hostEndpointInfo) {}

    public void onHostEndpointDisconnected(short s) {}

    public abstract void onLocationSettingChanged(boolean z);

    public abstract void onMicrophoneSettingChanged(boolean z);

    public abstract void onWifiMainSettingChanged(boolean z);

    public abstract void onWifiScanningSettingChanged(boolean z);

    public abstract void onWifiSettingChanged(boolean z);

    public abstract int queryNanoapps(int i);

    public abstract void registerCallback(
            int i, ContextHubService.ContextHubServiceCallback contextHubServiceCallback);

    public abstract void registerExistingCallback(int i);

    public abstract int sendMessageDeliveryStatusToContextHub(
            int i, MessageDeliveryStatus messageDeliveryStatus);

    public abstract int sendMessageToContextHub(short s, int i, NanoAppMessage nanoAppMessage);

    public abstract boolean setTestMode(boolean z);

    public abstract boolean supportsAirplaneModeSettingNotifications();

    public abstract boolean supportsBtSettingNotifications();

    public abstract boolean supportsLocationSettingNotifications();

    public abstract boolean supportsMicrophoneSettingNotifications();

    public abstract boolean supportsWifiSettingNotifications();

    public abstract int unloadNanoapp(int i, int i2, long j);
}
