package com.samsung.android.allshare;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.samsung.android.allshare.IAllShareConnector;
import com.samsung.android.allshare.extension.SECDownloader;
import com.samsung.android.allshare.media.Const;
import com.samsung.android.allshare.media.MediaServiceProvider;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaper.legibilitycolors.utils.ColorExtractor;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.ISubscriber;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class ServiceConnector {
    private static final String TAG_CONNECTOR = "ServiceConnector";
    private static WeakReference<Context> mContextRef;

    public interface IServiceConnectEventListener {
        void onCreated(ServiceProvider serviceProvider, ServiceState serviceState);

        void onDeleted(ServiceProvider serviceProvider);
    }

    private interface IServiceConnectorGetter {
        AllShareConnector getAllShareConnector();
    }

    private interface IServiceStateSetter {
        void setServiceState(ServiceState serviceState);
    }

    public enum ServiceState {
        ENABLED,
        DISABLED,
        UNABLE_TO_CONNECT,
        UNABLE_TO_DISCONNECT,
        UNKNOWN
    }

    ServiceConnector() {
    }

    static Context getContext() {
        Context context;
        WeakReference<Context> weakReference = mContextRef;
        if (weakReference == null || (context = weakReference.get()) == null) {
            return null;
        }
        return context;
    }

    public static ERROR createServiceProvider(Context context, IServiceConnectEventListener iServiceConnectEventListener) {
        DLog.v_api(TAG_CONNECTOR, "createServiceProvider(v1)");
        if (context == null || iServiceConnectEventListener == null) {
            DLog.w_api(TAG_CONNECTOR, "Context or ServiceConnectEventListener is null : " + context + " || " + iServiceConnectEventListener);
            return ERROR.INVALID_ARGUMENT;
        }
        DLog.setAPIVersionTag();
        mContextRef = new WeakReference<>(context);
        AllShareConnector allShareConnector = new AllShareConnector(context, null);
        allShareConnector.setCallback(new Handler.Callback(context, allShareConnector) { // from class: com.samsung.android.allshare.ServiceConnector.1
            private IServiceConnectEventListener mListener;
            private ServiceProviderImpl mServiceProvider;
            final /* synthetic */ AllShareConnector val$connector;
            final /* synthetic */ Context val$ctx;

            {
                this.val$ctx = context;
                this.val$connector = allShareConnector;
                this.mListener = this.val$l;
                this.mServiceProvider = new ServiceProviderImpl(context, allShareConnector);
            }

            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.obj == null || !(message.obj instanceof IAllShareConnector.AllShareServiceState)) {
                    return false;
                }
                int i = AnonymousClass3.$SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[((IAllShareConnector.AllShareServiceState) message.obj).ordinal()];
                if (i == 1) {
                    this.mServiceProvider.mServiceState = ServiceState.ENABLED;
                    try {
                        this.mListener.onCreated(this.mServiceProvider, ServiceState.ENABLED);
                    } catch (Error e) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "handleMessage Error", e);
                    } catch (Exception e2) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "handleMessage Exception", e2);
                    }
                } else if (i == 2) {
                    this.mServiceProvider.mServiceState = ServiceState.DISABLED;
                    try {
                        this.mListener.onDeleted(this.mServiceProvider);
                    } catch (Error e3) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", e3);
                    } catch (Exception e4) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", e4);
                    }
                }
                return true;
            }
        });
        allShareConnector.connect();
        return ERROR.SUCCESS;
    }

    /* renamed from: com.samsung.android.allshare.ServiceConnector$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState;

        static {
            int[] iArr = new int[IAllShareConnector.AllShareServiceState.values().length];
            $SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState = iArr;
            try {
                iArr[IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static ERROR createServiceProvider(Context context, IServiceConnectEventListener iServiceConnectEventListener, String str) {
        DLog.v_api(TAG_CONNECTOR, "createServiceProvider of " + str);
        if (context == null || iServiceConnectEventListener == null) {
            DLog.w_api(TAG_CONNECTOR, "Context or ServiceConnectEventListener is null : " + context + " || " + iServiceConnectEventListener);
            return ERROR.INVALID_ARGUMENT;
        }
        SemFloatingFeature semFloatingFeature = SemFloatingFeature.getInstance();
        if (semFloatingFeature != null) {
            DLog.i_api(TAG_CONNECTOR, "ALLSHARE_CONFIG : " + semFloatingFeature.getString("SEC_FLOATING_FEATURE_ALLSHARE_CONFIG_VERSION"));
        }
        DLog.setAPIVersionTag();
        mContextRef = new WeakReference<>(context);
        AllShareConnector allShareConnector = new AllShareConnector(context, null);
        allShareConnector.setCallback(new Handler.Callback(context, allShareConnector, str) { // from class: com.samsung.android.allshare.ServiceConnector.2
            private IServiceConnectEventListener mListener;
            private ServiceProvider mServiceProvider;
            final /* synthetic */ AllShareConnector val$connector;
            final /* synthetic */ Context val$ctx;
            final /* synthetic */ String val$serviceType;

            {
                this.val$ctx = context;
                this.val$connector = allShareConnector;
                this.val$serviceType = str;
                this.mListener = this.val$l;
                this.mServiceProvider = createServiceProvierImpl(context, allShareConnector, str);
            }

            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.obj == null || !(message.obj instanceof IAllShareConnector.AllShareServiceState)) {
                    return false;
                }
                int i = AnonymousClass3.$SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[((IAllShareConnector.AllShareServiceState) message.obj).ordinal()];
                if (i == 1) {
                    ((IServiceStateSetter) this.mServiceProvider).setServiceState(ServiceState.ENABLED);
                    try {
                        this.mListener.onCreated(this.mServiceProvider, ServiceState.ENABLED);
                    } catch (Error e) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "handleMessage Error", e);
                    } catch (Exception e2) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "handleMessage Exception", e2);
                    }
                } else if (i == 2) {
                    ((IServiceStateSetter) this.mServiceProvider).setServiceState(ServiceState.DISABLED);
                    try {
                        this.mListener.onDeleted(this.mServiceProvider);
                    } catch (Error e3) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", e3);
                    } catch (Exception e4) {
                        DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", e4);
                    }
                }
                return true;
            }

            private ServiceProvider createServiceProvierImpl(Context context2, AllShareConnector allShareConnector2, String str2) {
                if (str2 != null && str2.equals(ServiceProvider.SERVICE_MEDIA)) {
                    allShareConnector2.setProfileConstData(new ProfileConstData(Const.SERVICE_PACKAGE));
                    return new MediaServiceProviderImpl(context2, allShareConnector2);
                }
                return new ServiceProviderImpl(context2, allShareConnector2);
            }
        });
        allShareConnector.connect();
        return ERROR.SUCCESS;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void deleteServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProvider == 0) {
            DLog.w_api(TAG_CONNECTOR, "deleteServiceProvider : ServiceProvider is null");
            return;
        }
        AllShareConnector allShareConnector = ((IServiceConnectorGetter) serviceProvider).getAllShareConnector();
        if (!allShareConnector.isServiceBound()) {
            DLog.i_api(TAG_CONNECTOR, "Service deletion request has been made.");
        } else {
            allShareConnector.unsubscribeAllEvents();
            allShareConnector.destroyInstance();
        }
    }

    static Looper getMainLooper() {
        Context context;
        WeakReference<Context> weakReference = mContextRef;
        if (weakReference == null || (context = weakReference.get()) == null) {
            return null;
        }
        return context.getMainLooper();
    }

    private static class ServiceProviderImpl extends ServiceProvider implements IServiceStateSetter, IServiceConnectorGetter {
        AllShareConnector mConnector;
        DeviceFinderImpl mDeviceFinder;
        SECDownloader mDownloader;
        ServiceState mServiceState = ServiceState.DISABLED;

        public ServiceProviderImpl(Context context, AllShareConnector allShareConnector) {
            this.mDeviceFinder = null;
            this.mDownloader = null;
            this.mConnector = allShareConnector;
            this.mDeviceFinder = new DeviceFinderImpl(allShareConnector);
            this.mDownloader = new SECDownloader(allShareConnector);
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public SECDownloader getDownloader() {
            return this.mDownloader;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public DeviceFinderImpl getDeviceFinder() {
            return this.mDeviceFinder;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public ServiceState getServiceState() {
            return this.mServiceState;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public String getServiceVersion() {
            AllShareConnector allShareConnector = this.mConnector;
            if (allShareConnector == null) {
                DLog.w_api("ServiceProviderImpl", "Connection FAIL: AllShare Service Connector does not exist");
                return "";
            }
            return allShareConnector.getServiceVersion();
        }

        @Override // com.samsung.android.allshare.ServiceConnector.IServiceStateSetter
        public void setServiceState(ServiceState serviceState) {
            this.mServiceState = serviceState;
        }

        @Override // com.samsung.android.allshare.ServiceConnector.IServiceConnectorGetter
        public AllShareConnector getAllShareConnector() {
            return this.mConnector;
        }
    }

    private static class MediaServiceProviderImpl extends MediaServiceProvider implements IServiceStateSetter, IServiceConnectorGetter {
        SECDownloader mDownloader;
        MediaDeviceFinderImpl mMediaDeviceFinder;
        AllShareConnector mMediaServiceConnector;
        ServiceState mMediaServiceState = ServiceState.DISABLED;

        public MediaServiceProviderImpl(Context context, AllShareConnector allShareConnector) {
            this.mMediaDeviceFinder = null;
            this.mDownloader = null;
            this.mMediaServiceConnector = allShareConnector;
            this.mMediaDeviceFinder = new MediaDeviceFinderImpl(allShareConnector);
            this.mDownloader = new SECDownloader(allShareConnector);
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public SECDownloader getDownloader() {
            return this.mDownloader;
        }

        @Override // com.samsung.android.allshare.media.MediaServiceProvider, com.samsung.android.allshare.ServiceProvider
        public MediaDeviceFinderImpl getDeviceFinder() {
            return this.mMediaDeviceFinder;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public ServiceState getServiceState() {
            return this.mMediaServiceState;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public String getServiceVersion() {
            AllShareConnector allShareConnector = this.mMediaServiceConnector;
            if (allShareConnector == null) {
                DLog.w_api("MediaServiceProviderImpl", "Connection FAIL: AllShare Media Service Connector does not exist");
                return "";
            }
            return allShareConnector.getServiceVersion();
        }

        @Override // com.samsung.android.allshare.ServiceConnector.IServiceStateSetter
        public void setServiceState(ServiceState serviceState) {
            MediaDeviceFinderImpl mediaDeviceFinderImpl;
            this.mMediaServiceState = serviceState;
            if (ServiceState.DISABLED != serviceState || (mediaDeviceFinderImpl = this.mMediaDeviceFinder) == null) {
                return;
            }
            mediaDeviceFinderImpl.cleanup();
            this.mMediaDeviceFinder = null;
        }

        @Override // com.samsung.android.allshare.ServiceConnector.IServiceConnectorGetter
        public AllShareConnector getAllShareConnector() {
            return this.mMediaServiceConnector;
        }
    }

    static class ProfileConstData {
        public long ALLSHARE_FRAMEWORK_VERSION;
        public String CP_NAME;
        public boolean DEV_MODE;
        public String SERVICE_MANAGER_NAME_VERSION_1;
        public String SET_NAME_MESSAGE;
        public String START_MESSAGE;
        public String START_SERVICE;
        public String STOP_MESSAGE;
        public String SUBSCRIBER_FIELD;
        public String SUBSCRIPTION_MESSAGE;

        public ProfileConstData() {
            this.ALLSHARE_FRAMEWORK_VERSION = 1L;
            this.DEV_MODE = com.sec.android.allshare.iface.Const.DEV_MODE;
            this.START_SERVICE = com.sec.android.allshare.iface.Const.START_SERVICE;
            this.START_MESSAGE = com.sec.android.allshare.iface.Const.START_MESSAGE;
            this.STOP_MESSAGE = com.sec.android.allshare.iface.Const.STOP_MESSAGE;
            this.SERVICE_MANAGER_NAME_VERSION_1 = com.sec.android.allshare.iface.Const.SERVICE_MANAGER_NAME_VERSION_1;
            this.SUBSCRIBER_FIELD = "com.sec.android.allshare.iface.subscriber";
            this.SUBSCRIPTION_MESSAGE = com.sec.android.allshare.iface.Const.SUBSCRIPTION_MESSAGE;
            this.SET_NAME_MESSAGE = com.sec.android.allshare.iface.Const.SET_NAME_MESSAGE;
            this.CP_NAME = com.sec.android.allshare.iface.Const.CP_NAME;
        }

        public ProfileConstData(String str) {
            this.ALLSHARE_FRAMEWORK_VERSION = 1L;
            this.DEV_MODE = com.sec.android.allshare.iface.Const.DEV_MODE;
            this.START_SERVICE = com.sec.android.allshare.iface.Const.START_SERVICE;
            this.START_MESSAGE = com.sec.android.allshare.iface.Const.START_MESSAGE;
            this.STOP_MESSAGE = com.sec.android.allshare.iface.Const.STOP_MESSAGE;
            this.SERVICE_MANAGER_NAME_VERSION_1 = com.sec.android.allshare.iface.Const.SERVICE_MANAGER_NAME_VERSION_1;
            this.SUBSCRIBER_FIELD = "com.sec.android.allshare.iface.subscriber";
            this.SUBSCRIPTION_MESSAGE = com.sec.android.allshare.iface.Const.SUBSCRIPTION_MESSAGE;
            this.SET_NAME_MESSAGE = com.sec.android.allshare.iface.Const.SET_NAME_MESSAGE;
            this.CP_NAME = com.sec.android.allshare.iface.Const.CP_NAME;
            if (str == null || !str.equals(Const.SERVICE_PACKAGE)) {
                return;
            }
            this.ALLSHARE_FRAMEWORK_VERSION = 1L;
            this.DEV_MODE = Const.DEV_MODE;
            this.START_SERVICE = Const.START_SERVICE;
            this.START_MESSAGE = Const.START_MESSAGE;
            this.STOP_MESSAGE = Const.STOP_MESSAGE;
            this.SERVICE_MANAGER_NAME_VERSION_1 = Const.SERVICE_MANAGER_NAME_VERSION_1;
            this.SUBSCRIBER_FIELD = "com.sec.android.allshare.iface.subscriber";
            this.SUBSCRIPTION_MESSAGE = Const.SUBSCRIPTION_MESSAGE;
            this.SET_NAME_MESSAGE = Const.SET_NAME_MESSAGE;
            this.CP_NAME = Const.CP_NAME;
        }
    }

    private static final class AllShareConnector implements IAllShareConnector {
        public static final long INVALID_REQUEST_ID = -1;
        private static final String TAG = "AllShareConnector";
        private ProfileConstData mConstData;
        private WeakReference<Context> mContextRef;
        private String mID;
        private String mSubscriberTag;
        private ISubscriber mISubscriber = null;
        private Handler.Callback mConnectionCallback = null;
        private final HashSet<EventHandler> mEventHandlerSet = new HashSet<>();
        private ComponentName mComponentName = null;
        private boolean mIsBound = false;
        private ServiceConnection mAllShareConnection = new ServiceConnection() { // from class: com.samsung.android.allshare.ServiceConnector.AllShareConnector.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                DLog.i_api(AllShareConnector.TAG, "Subscriber onServiceConnected to " + AllShareConnector.this.mSubscriberTag);
                AllShareConnector.this.mISubscriber = ISubscriber.Stub.asInterface(iBinder);
                AllShareConnector.this.mComponentName = componentName;
                AllShareConnector.this.mIsBound = true;
                AllShareConnector.this.onConnected();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                DLog.i_api(AllShareConnector.TAG, "Subscriber onServiceDisconnected from " + AllShareConnector.this.mSubscriberTag);
                AllShareConnector.this.mISubscriber = null;
                AllShareConnector.this.mComponentName = null;
                AllShareConnector.this.mIsBound = false;
                AllShareConnector.this.onDisconnected();
            }
        };
        private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.samsung.android.allshare.ServiceConnector.AllShareConnector.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    DLog.w_api(AllShareConnector.TAG, AllShareConnector.this.mSubscriberTag + " : intent.getAction() == null!");
                    return;
                }
                if (action.equals(AllShareConnector.this.mConstData.START_MESSAGE)) {
                    DLog.d_api(AllShareConnector.TAG, AllShareConnector.this.mSubscriberTag + " : onReceive AllShare Service Start message...^^");
                    AllShareConnector.this.connect();
                    return;
                }
                if (action.equals(AllShareConnector.this.mConstData.STOP_MESSAGE)) {
                    DLog.d_api(AllShareConnector.TAG, AllShareConnector.this.mSubscriberTag + " : onReceive AllShare Service Stop message...^^");
                    AllShareConnector.this.onDisconnected();
                    return;
                }
                DLog.w_api(AllShareConnector.TAG, AllShareConnector.this.mSubscriberTag + " : onReceive Unknown action - " + action);
            }
        };

        public AllShareConnector(Context context, String str) {
            this.mContextRef = null;
            this.mID = null;
            this.mSubscriberTag = null;
            this.mConstData = null;
            this.mContextRef = new WeakReference<>(context);
            if (str == null || str.isEmpty()) {
                this.mID = context.getApplicationInfo().packageName;
            } else {
                this.mID = context.getApplicationInfo().packageName + str;
            }
            this.mSubscriberTag = this.mID.split("\\.")[r3.length - 1];
            this.mConstData = new ProfileConstData();
        }

        public void setProfileConstData(ProfileConstData profileConstData) {
            if (profileConstData != null) {
                this.mConstData = profileConstData;
            }
        }

        public void setCallback(Handler.Callback callback) {
            this.mConnectionCallback = callback;
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public synchronized void connect() {
            if (isAllShareServiceConnected()) {
                DLog.i_api(TAG, this.mSubscriberTag + " is Already connected to AllShare service framework");
                return;
            }
            if (startAllShareLauncher()) {
                bindAllShareService();
            }
            registerSvcCastReceiver();
        }

        private void bindAllShareService() {
            DLog.v_api(TAG, this.mSubscriberTag + " : bindAllShareService...");
            Context context = this.mContextRef.get();
            if (context == null) {
                DLog.e_api(TAG, this.mSubscriberTag + " : bindAllShareService error - context is null");
                return;
            }
            Intent intent = new Intent(this.mConstData.SUBSCRIPTION_MESSAGE);
            intent.putExtra(this.mConstData.SUBSCRIBER_FIELD, this.mID);
            if (this.mConstData.SUBSCRIPTION_MESSAGE.startsWith(Const.SERVICE_PACKAGE)) {
                intent.setPackage(Const.SERVICE_PACKAGE);
            }
            if (context.bindService(intent, this.mAllShareConnection, 0)) {
                return;
            }
            startAllShareLauncher();
            context.unbindService(this.mAllShareConnection);
            this.mIsBound = false;
            DLog.e_api(TAG, this.mSubscriberTag + " : bindAllShareService FAIL - check if app use ApplicationContext or not");
        }

        private void registerSvcCastReceiver() {
            Context context = this.mContextRef.get();
            if (context == null) {
                DLog.w_api(TAG, this.mSubscriberTag + " registerSvcCastReceiver error - context is null");
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.mConstData.START_MESSAGE);
            intentFilter.addAction(this.mConstData.STOP_MESSAGE);
            context.registerReceiver(this.mReceiver, intentFilter, 2);
        }

        private void unregisterSvcCastReceiver() {
            try {
                Context context = this.mContextRef.get();
                if (context == null) {
                    DLog.w_api(TAG, this.mSubscriberTag + " unregisterSvcCastReceiver error - context is null");
                    return;
                }
                context.unregisterReceiver(this.mReceiver);
            } catch (Exception e) {
                DLog.w_api(TAG, "unregisterSvcCastReceiver Exception ", e);
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public void disconnect() {
            unregisterSvcCastReceiver();
            if (this.mAllShareConnection != null) {
                try {
                    Context context = this.mContextRef.get();
                    if (context == null) {
                        DLog.w_api(TAG, this.mSubscriberTag + " disconnect error - context is null");
                        return;
                    }
                    context.unbindService(this.mAllShareConnection);
                    if (isAllShareServiceConnected()) {
                        this.mAllShareConnection.onServiceDisconnected(this.mComponentName);
                    }
                } catch (Exception e) {
                    DLog.w_api(TAG, "disconnect Exception", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onConnected() {
            DLog.d_api(TAG, "onConnected to " + this.mSubscriberTag);
            notifyAllShareEnable();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDisconnected() {
            DLog.d_api(TAG, "onDisconnected from " + this.mSubscriberTag);
            notifyAllShareDisable();
        }

        private void notifyAllShareEnable() {
            if (this.mConnectionCallback != null) {
                Message message = new Message();
                message.obj = IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_CONNECTED;
                this.mConnectionCallback.handleMessage(message);
            }
        }

        private void notifyAllShareDisable() {
            if (this.mConnectionCallback != null) {
                Message message = new Message();
                message.obj = IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_DISCONNECTED;
                this.mConnectionCallback.handleMessage(message);
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public boolean isAllShareServiceConnected() {
            return this.mISubscriber != null;
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public String getCaptionFilePathFromURI(String str) {
            try {
                String captionFilePathFromURI = this.mISubscriber.getCaptionFilePathFromURI(str);
                return captionFilePathFromURI == null ? "" : captionFilePathFromURI;
            } catch (RemoteException e) {
                DLog.w_api(TAG, "getCaptionFilePathFromURI RemoteException", e);
                return "";
            } catch (Exception e2) {
                DLog.w_api(TAG, "getCaptionFilePathFromURI Exception", e2);
                return "";
            }
        }

        public boolean isServiceBound() {
            return this.mIsBound;
        }

        private boolean startAllShareLauncher() {
            Context context = this.mContextRef.get();
            if (context == null) {
                DLog.w_api(TAG, this.mSubscriberTag + " startAllShareLauncher error - context is null");
                return false;
            }
            Intent intent = new Intent(this.mConstData.START_SERVICE);
            if (this.mConstData.START_SERVICE.startsWith(Const.SERVICE_PACKAGE)) {
                intent.setPackage(Const.SERVICE_PACKAGE);
            }
            try {
                if (context.startService(intent) != null) {
                    return true;
                }
                DLog.w_api(TAG, this.mSubscriberTag + " : AllShare Service is not installed yet...");
                return false;
            } catch (SecurityException e) {
                DLog.w_api(TAG, this.mSubscriberTag + " startAllShareLauncher error...SecurityException ", e);
                return false;
            } catch (Exception e2) {
                DLog.w_api(TAG, this.mSubscriberTag + " startAllShareLauncher exception ", e2);
                return false;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public void destroyInstance() {
            disconnect();
            this.mConnectionCallback = null;
            this.mISubscriber = null;
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public long requestCVMAsync(CVMessage cVMessage, AllShareResponseHandler allShareResponseHandler) {
            if (this.mISubscriber != null && allShareResponseHandler != null) {
                if (cVMessage.getBundle() == null) {
                    cVMessage.setBundle(new Bundle());
                }
                long jNanoTime = System.nanoTime();
                cVMessage.setMsgID(jNanoTime);
                cVMessage.setMsgType(2);
                cVMessage.setMessenger(new Messenger(allShareResponseHandler));
                try {
                    if (this.mISubscriber.requestCVAsync(this.mID, cVMessage)) {
                        return jNanoTime;
                    }
                    DLog.d_api(TAG, this.mSubscriberTag + " requestCVMAsync fail...Maybe Invalid Action Request");
                    return -1L;
                } catch (RemoteException e) {
                    DLog.w_api(TAG, this.mSubscriberTag + " requestCVMAsync error...RemoteException", e);
                } catch (Exception e2) {
                    DLog.w_api(TAG, this.mSubscriberTag + " requestCVMAsync error...Exception", e2);
                    return -1L;
                }
            }
            return -1L;
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public CVMessage requestCVMSync(CVMessage cVMessage) {
            if (this.mISubscriber == null || cVMessage == null) {
                return new CVMessage();
            }
            if (cVMessage.getBundle() == null) {
                cVMessage.setBundle(new Bundle());
            }
            cVMessage.setMsgType(2);
            try {
                return this.mISubscriber.requestCVSync(this.mID, cVMessage);
            } catch (RemoteException e) {
                CVMessage cVMessage2 = new CVMessage();
                DLog.w_api(TAG, this.mSubscriberTag + " requestCVMSync error...RemoteException", e);
                return cVMessage2;
            } catch (Exception e2) {
                DLog.w_api(TAG, this.mSubscriberTag + " requestCVMSync error...Exception", e2);
                return null;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public boolean subscribeAllShareEvent(String str, Bundle bundle, AllShareEventHandler allShareEventHandler) {
            if (this.mISubscriber == null) {
                return false;
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            CVMessage cVMessage = new CVMessage(4, str, bundle);
            cVMessage.setMessenger(new Messenger(allShareEventHandler));
            addHandlerToHashSet(str, bundle, allShareEventHandler);
            try {
                return this.mISubscriber.subscribeEvent(this.mID, cVMessage);
            } catch (RemoteException e) {
                DLog.w_api(TAG, "subscribeAllShareEvent RemoteException", e);
                return false;
            } catch (RuntimeException e2) {
                DLog.w_api(TAG, "subscribeAllShareEvent RuntimeException", e2);
                return false;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public void unsubscribeAllShareEvent(String str, Bundle bundle, AllShareEventHandler allShareEventHandler) {
            if (this.mISubscriber == null) {
                return;
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            CVMessage cVMessage = new CVMessage(4, str, bundle);
            cVMessage.setMessenger(new Messenger(allShareEventHandler));
            removeHandlerFromHashSet(str, bundle, allShareEventHandler);
            try {
                this.mISubscriber.unsubscribeEvent(this.mID, cVMessage);
            } catch (RemoteException e) {
                DLog.w_api(TAG, "unsubscribeAllShareEvent RemoteException", e);
            } catch (RuntimeException e2) {
                DLog.w_api(TAG, "unsubscribeAllShareEvent RuntimeException", e2);
            }
        }

        public void unsubscribeAllEvents() {
            synchronized (this.mEventHandlerSet) {
                Iterator it = ((HashSet) this.mEventHandlerSet.clone()).iterator();
                while (it.hasNext()) {
                    EventHandler eventHandler = (EventHandler) it.next();
                    unsubscribeAllShareEvent(eventHandler.mEventId, eventHandler.mBundle, eventHandler.mHanlder);
                }
                this.mEventHandlerSet.clear();
            }
        }

        public String getServiceVersion() {
            try {
                String serviceVersion = this.mISubscriber.getServiceVersion();
                return serviceVersion == null ? ColorExtractor.VERSION : serviceVersion;
            } catch (RemoteException e) {
                DLog.w_api(TAG, "getServiceVersion RemoteException", e);
                return "";
            } catch (Exception e2) {
                DLog.w_api(TAG, "getServiceVersion Exception", e2);
                return "";
            }
        }

        private static class EventHandler {
            Bundle mBundle;
            String mEventId;
            AllShareEventHandler mHanlder;

            public EventHandler(String str, Bundle bundle, AllShareEventHandler allShareEventHandler) {
                this.mEventId = str;
                this.mBundle = bundle;
                this.mHanlder = allShareEventHandler;
            }

            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof EventHandler)) {
                    return false;
                }
                EventHandler eventHandler = (EventHandler) obj;
                return eventHandler.mEventId.equals(this.mEventId) && eventHandler.mBundle.equals(this.mBundle) && eventHandler.mHanlder.equals(this.mHanlder);
            }

            public int hashCode() {
                return super.hashCode();
            }
        }

        private void addHandlerToHashSet(String str, Bundle bundle, AllShareEventHandler allShareEventHandler) {
            synchronized (this.mEventHandlerSet) {
                this.mEventHandlerSet.add(new EventHandler(str, bundle, allShareEventHandler));
            }
        }

        private void removeHandlerFromHashSet(String str, Bundle bundle, AllShareEventHandler allShareEventHandler) {
            synchronized (this.mEventHandlerSet) {
                this.mEventHandlerSet.remove(new EventHandler(str, bundle, allShareEventHandler));
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public ContentResolver getContentResolver() {
            WeakReference<Context> weakReference = this.mContextRef;
            if (weakReference == null || weakReference.get() == null) {
                return null;
            }
            return this.mContextRef.get().getContentResolver();
        }
    }
}
