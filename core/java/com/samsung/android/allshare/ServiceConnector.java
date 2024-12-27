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

/* loaded from: classes3.dex */
public class ServiceConnector {
    private static final String TAG_CONNECTOR = "ServiceConnector";
    private static WeakReference<Context> mContextRef = null;

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

    ServiceConnector() {}

    static Context getContext() {
        Context ctx;
        if (mContextRef == null || (ctx = mContextRef.get()) == null) {
            return null;
        }
        return ctx;
    }

    public static ERROR createServiceProvider(
            final Context ctx, final IServiceConnectEventListener l) {
        DLog.v_api(TAG_CONNECTOR, "createServiceProvider(v1)");
        if (ctx == null || l == null) {
            DLog.w_api(
                    TAG_CONNECTOR,
                    "Context or ServiceConnectEventListener is null : " + ctx + " || " + l);
            return ERROR.INVALID_ARGUMENT;
        }
        DLog.setAPIVersionTag();
        mContextRef = new WeakReference<>(ctx);
        final AllShareConnector connector = new AllShareConnector(ctx, null);
        Handler.Callback callback =
                new Handler
                        .Callback() { // from class: com.samsung.android.allshare.ServiceConnector.1
                    private IServiceConnectEventListener mListener;
                    private ServiceProviderImpl mServiceProvider;

                    {
                        this.mListener = IServiceConnectEventListener.this;
                        this.mServiceProvider = new ServiceProviderImpl(ctx, connector);
                    }

                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message msg) {
                        if (msg.obj == null
                                || !(msg.obj instanceof IAllShareConnector.AllShareServiceState)) {
                            return false;
                        }
                        IAllShareConnector.AllShareServiceState state =
                                (IAllShareConnector.AllShareServiceState) msg.obj;
                        switch (AnonymousClass3
                                .$SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[
                                state.ordinal()]) {
                            case 1:
                                this.mServiceProvider.mServiceState = ServiceState.ENABLED;
                                try {
                                    this.mListener.onCreated(
                                            this.mServiceProvider, ServiceState.ENABLED);
                                    return true;
                                } catch (Error e) {
                                    DLog.w_api(
                                            ServiceConnector.TAG_CONNECTOR,
                                            "handleMessage Error",
                                            e);
                                    return true;
                                } catch (Exception e2) {
                                    DLog.w_api(
                                            ServiceConnector.TAG_CONNECTOR,
                                            "handleMessage Exception",
                                            e2);
                                    return true;
                                }
                            case 2:
                                this.mServiceProvider.mServiceState = ServiceState.DISABLED;
                                try {
                                    this.mListener.onDeleted(this.mServiceProvider);
                                    return true;
                                } catch (Error err) {
                                    DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", err);
                                    return true;
                                } catch (Exception e3) {
                                    DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", e3);
                                    return true;
                                }
                            default:
                                return true;
                        }
                    }
                };
        connector.setCallback(callback);
        connector.connect();
        return ERROR.SUCCESS;
    }

    /* renamed from: com.samsung.android.allshare.ServiceConnector$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState =
                        new int[IAllShareConnector.AllShareServiceState.values().length];

        static {
            try {
                $SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[
                                IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_CONNECTED
                                        .ordinal()] =
                        1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[
                                IAllShareConnector.AllShareServiceState
                                        .ALLSHARE_SERVICE_DISCONNECTED
                                        .ordinal()] =
                        2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static ERROR createServiceProvider(
            final Context ctx, final IServiceConnectEventListener l, final String serviceType) {
        DLog.v_api(TAG_CONNECTOR, "createServiceProvider of " + serviceType);
        if (ctx == null || l == null) {
            DLog.w_api(
                    TAG_CONNECTOR,
                    "Context or ServiceConnectEventListener is null : " + ctx + " || " + l);
            return ERROR.INVALID_ARGUMENT;
        }
        SemFloatingFeature floatingFeature = SemFloatingFeature.getInstance();
        if (floatingFeature != null) {
            DLog.i_api(
                    TAG_CONNECTOR,
                    "ALLSHARE_CONFIG : "
                            + floatingFeature.getString(
                                    "SEC_FLOATING_FEATURE_ALLSHARE_CONFIG_VERSION"));
        }
        DLog.setAPIVersionTag();
        mContextRef = new WeakReference<>(ctx);
        final AllShareConnector connector = new AllShareConnector(ctx, null);
        Handler.Callback callback =
                new Handler
                        .Callback() { // from class: com.samsung.android.allshare.ServiceConnector.2
                    private IServiceConnectEventListener mListener;
                    private ServiceProvider mServiceProvider;

                    {
                        this.mListener = IServiceConnectEventListener.this;
                        this.mServiceProvider =
                                createServiceProvierImpl(ctx, connector, serviceType);
                    }

                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message msg) {
                        if (msg.obj == null
                                || !(msg.obj instanceof IAllShareConnector.AllShareServiceState)) {
                            return false;
                        }
                        IAllShareConnector.AllShareServiceState state =
                                (IAllShareConnector.AllShareServiceState) msg.obj;
                        switch (AnonymousClass3
                                .$SwitchMap$com$samsung$android$allshare$IAllShareConnector$AllShareServiceState[
                                state.ordinal()]) {
                            case 1:
                                ((IServiceStateSetter) this.mServiceProvider)
                                        .setServiceState(ServiceState.ENABLED);
                                try {
                                    this.mListener.onCreated(
                                            this.mServiceProvider, ServiceState.ENABLED);
                                    return true;
                                } catch (Error e) {
                                    DLog.w_api(
                                            ServiceConnector.TAG_CONNECTOR,
                                            "handleMessage Error",
                                            e);
                                    return true;
                                } catch (Exception e2) {
                                    DLog.w_api(
                                            ServiceConnector.TAG_CONNECTOR,
                                            "handleMessage Exception",
                                            e2);
                                    return true;
                                }
                            case 2:
                                ((IServiceStateSetter) this.mServiceProvider)
                                        .setServiceState(ServiceState.DISABLED);
                                try {
                                    this.mListener.onDeleted(this.mServiceProvider);
                                    return true;
                                } catch (Error err) {
                                    DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", err);
                                    return true;
                                } catch (Exception e3) {
                                    DLog.w_api(ServiceConnector.TAG_CONNECTOR, "", e3);
                                    return true;
                                }
                            default:
                                return true;
                        }
                    }

                    private ServiceProvider createServiceProvierImpl(
                            Context ctx2, AllShareConnector connector2, String serviceType2) {
                        if (serviceType2 != null
                                && serviceType2.equals(ServiceProvider.SERVICE_MEDIA)) {
                            connector2.setProfileConstData(
                                    new ProfileConstData(Const.SERVICE_PACKAGE));
                            return new MediaServiceProviderImpl(ctx2, connector2);
                        }
                        return new ServiceProviderImpl(ctx2, connector2);
                    }
                };
        connector.setCallback(callback);
        connector.connect();
        return ERROR.SUCCESS;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void deleteServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProvider == 0) {
            DLog.w_api(TAG_CONNECTOR, "deleteServiceProvider : ServiceProvider is null");
            return;
        }
        AllShareConnector connector =
                ((IServiceConnectorGetter) serviceProvider).getAllShareConnector();
        connector.unsubscribeAllEvents();
        connector.destroyInstance();
    }

    static Looper getMainLooper() {
        Context ctx;
        if (mContextRef == null || (ctx = mContextRef.get()) == null) {
            return null;
        }
        return ctx.getMainLooper();
    }

    private static class ServiceProviderImpl extends ServiceProvider
            implements IServiceStateSetter, IServiceConnectorGetter {
        AllShareConnector mConnector;
        DeviceFinderImpl mDeviceFinder;
        SECDownloader mDownloader;
        ServiceState mServiceState = ServiceState.DISABLED;

        public ServiceProviderImpl(Context ctx, AllShareConnector connector) {
            this.mDeviceFinder = null;
            this.mConnector = null;
            this.mDownloader = null;
            this.mConnector = connector;
            this.mDeviceFinder = new DeviceFinderImpl(connector);
            this.mDownloader = new SECDownloader(connector);
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
            if (this.mConnector == null) {
                DLog.w_api(
                        "ServiceProviderImpl",
                        "Connection FAIL: AllShare Service Connector does not exist");
                return "";
            }
            return this.mConnector.getServiceVersion();
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

    private static class MediaServiceProviderImpl extends MediaServiceProvider
            implements IServiceStateSetter, IServiceConnectorGetter {
        SECDownloader mDownloader;
        MediaDeviceFinderImpl mMediaDeviceFinder;
        AllShareConnector mMediaServiceConnector;
        ServiceState mMediaServiceState = ServiceState.DISABLED;

        public MediaServiceProviderImpl(Context ctx, AllShareConnector connector) {
            this.mMediaDeviceFinder = null;
            this.mMediaServiceConnector = null;
            this.mDownloader = null;
            this.mMediaServiceConnector = connector;
            this.mMediaDeviceFinder = new MediaDeviceFinderImpl(connector);
            this.mDownloader = new SECDownloader(connector);
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public SECDownloader getDownloader() {
            return this.mDownloader;
        }

        @Override // com.samsung.android.allshare.media.MediaServiceProvider,
                  // com.samsung.android.allshare.ServiceProvider
        public MediaDeviceFinderImpl getDeviceFinder() {
            return this.mMediaDeviceFinder;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public ServiceState getServiceState() {
            return this.mMediaServiceState;
        }

        @Override // com.samsung.android.allshare.ServiceProvider
        public String getServiceVersion() {
            if (this.mMediaServiceConnector == null) {
                DLog.w_api(
                        "MediaServiceProviderImpl",
                        "Connection FAIL: AllShare Media Service Connector does not exist");
                return "";
            }
            return this.mMediaServiceConnector.getServiceVersion();
        }

        @Override // com.samsung.android.allshare.ServiceConnector.IServiceStateSetter
        public void setServiceState(ServiceState serviceState) {
            this.mMediaServiceState = serviceState;
            if (ServiceState.DISABLED == serviceState && this.mMediaDeviceFinder != null) {
                this.mMediaDeviceFinder.cleanup();
                this.mMediaDeviceFinder = null;
            }
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
            this.SERVICE_MANAGER_NAME_VERSION_1 =
                    com.sec.android.allshare.iface.Const.SERVICE_MANAGER_NAME_VERSION_1;
            this.SUBSCRIBER_FIELD = "com.sec.android.allshare.iface.subscriber";
            this.SUBSCRIPTION_MESSAGE = com.sec.android.allshare.iface.Const.SUBSCRIPTION_MESSAGE;
            this.SET_NAME_MESSAGE = com.sec.android.allshare.iface.Const.SET_NAME_MESSAGE;
            this.CP_NAME = com.sec.android.allshare.iface.Const.CP_NAME;
        }

        public ProfileConstData(String serviceType) {
            this.ALLSHARE_FRAMEWORK_VERSION = 1L;
            this.DEV_MODE = com.sec.android.allshare.iface.Const.DEV_MODE;
            this.START_SERVICE = com.sec.android.allshare.iface.Const.START_SERVICE;
            this.START_MESSAGE = com.sec.android.allshare.iface.Const.START_MESSAGE;
            this.STOP_MESSAGE = com.sec.android.allshare.iface.Const.STOP_MESSAGE;
            this.SERVICE_MANAGER_NAME_VERSION_1 =
                    com.sec.android.allshare.iface.Const.SERVICE_MANAGER_NAME_VERSION_1;
            this.SUBSCRIBER_FIELD = "com.sec.android.allshare.iface.subscriber";
            this.SUBSCRIPTION_MESSAGE = com.sec.android.allshare.iface.Const.SUBSCRIPTION_MESSAGE;
            this.SET_NAME_MESSAGE = com.sec.android.allshare.iface.Const.SET_NAME_MESSAGE;
            this.CP_NAME = com.sec.android.allshare.iface.Const.CP_NAME;
            if (serviceType != null && serviceType.equals(Const.SERVICE_PACKAGE)) {
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
    }

    private static final class AllShareConnector implements IAllShareConnector {
        public static final long INVALID_REQUEST_ID = -1;
        private static final String TAG = "AllShareConnector";
        private ProfileConstData mConstData;
        private WeakReference<Context> mContextRef;
        private String mID;
        private String mSubscriberTag;
        private ISubscriber mISubscriber = null;
        private boolean mConnecting = false;
        private Handler.Callback mConnectionCallback = null;
        private final HashSet<EventHandler> mEventHandlerSet = new HashSet<>();
        private ComponentName mComponentName = null;
        private ServiceConnection mAllShareConnection =
                new ServiceConnection() { // from class:
                                          // com.samsung.android.allshare.ServiceConnector.AllShareConnector.1
                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName component, IBinder binder) {
                        DLog.i_api(
                                AllShareConnector.TAG,
                                "Subscriber onServiceConnected to "
                                        + AllShareConnector.this.mSubscriberTag);
                        AllShareConnector.this.mISubscriber = ISubscriber.Stub.asInterface(binder);
                        AllShareConnector.this.mComponentName = component;
                        AllShareConnector.this.onConnected();
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName component) {
                        DLog.i_api(
                                AllShareConnector.TAG,
                                "Subscriber onServiceDisconnected from "
                                        + AllShareConnector.this.mSubscriberTag);
                        AllShareConnector.this.mISubscriber = null;
                        AllShareConnector.this.mComponentName = null;
                        AllShareConnector.this.onDisconnected();
                    }
                };
        private BroadcastReceiver mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.allshare.ServiceConnector.AllShareConnector.2
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (action == null) {
                            DLog.w_api(
                                    AllShareConnector.TAG,
                                    AllShareConnector.this.mSubscriberTag
                                            + " : intent.getAction() == null!");
                            return;
                        }
                        if (action.equals(AllShareConnector.this.mConstData.START_MESSAGE)) {
                            DLog.d_api(
                                    AllShareConnector.TAG,
                                    AllShareConnector.this.mSubscriberTag
                                            + " : onReceive AllShare Service Start message...^^");
                            AllShareConnector.this.connect();
                        } else if (action.equals(AllShareConnector.this.mConstData.STOP_MESSAGE)) {
                            DLog.d_api(
                                    AllShareConnector.TAG,
                                    AllShareConnector.this.mSubscriberTag
                                            + " : onReceive AllShare Service Stop message...^^");
                            AllShareConnector.this.onDisconnected();
                        } else {
                            DLog.w_api(
                                    AllShareConnector.TAG,
                                    AllShareConnector.this.mSubscriberTag
                                            + " : onReceive Unknown action - "
                                            + action);
                        }
                    }
                };

        public AllShareConnector(Context context, String id) {
            this.mContextRef = null;
            this.mID = null;
            this.mSubscriberTag = null;
            this.mConstData = null;
            this.mContextRef = new WeakReference<>(context);
            if (id == null || id.isEmpty()) {
                this.mID = context.getApplicationInfo().packageName;
            } else {
                this.mID = context.getApplicationInfo().packageName + id;
            }
            String[] str_array = this.mID.split("\\.");
            this.mSubscriberTag = str_array[str_array.length - 1];
            this.mConstData = new ProfileConstData();
        }

        public void setProfileConstData(ProfileConstData data) {
            if (data != null) {
                this.mConstData = data;
            }
        }

        public void setCallback(Handler.Callback callback) {
            this.mConnectionCallback = callback;
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public synchronized void connect() {
            if (this.mConnecting) {
                DLog.i_api(TAG, this.mSubscriberTag + " is Already trying to connecting...wait..");
                return;
            }
            this.mConnecting = true;
            if (isAllShareServiceConnected()) {
                DLog.i_api(
                        TAG,
                        this.mSubscriberTag
                                + " is Already connected to AllShare service framework");
                return;
            }
            if (startAllShareLauncher()) {
                bindAllShareService();
            }
            registerSvcCastReceiver();
        }

        private boolean bindAllShareService() {
            DLog.v_api(TAG, this.mSubscriberTag + " : bindAllShareService...");
            Context context = this.mContextRef.get();
            if (context == null) {
                DLog.e_api(
                        TAG,
                        this.mSubscriberTag + " : bindAllShareService error - context is null");
                return false;
            }
            Intent bind_service = new Intent(this.mConstData.SUBSCRIPTION_MESSAGE);
            bind_service.putExtra(this.mConstData.SUBSCRIBER_FIELD, this.mID);
            if (this.mConstData.SUBSCRIPTION_MESSAGE.startsWith(Const.SERVICE_PACKAGE)) {
                bind_service.setPackage(Const.SERVICE_PACKAGE);
            }
            boolean bindResult = context.bindService(bind_service, this.mAllShareConnection, 0);
            if (!bindResult) {
                startAllShareLauncher();
                this.mConnecting = false;
                DLog.e_api(
                        TAG,
                        this.mSubscriberTag
                                + " : bindAllShareService FAIL - check if app use"
                                + " ApplicationContext or not");
                return false;
            }
            return true;
        }

        private void registerSvcCastReceiver() {
            Context context = this.mContextRef.get();
            if (context == null) {
                DLog.w_api(
                        TAG,
                        this.mSubscriberTag + " registerSvcCastReceiver error - context is null");
                return;
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction(this.mConstData.START_MESSAGE);
            filter.addAction(this.mConstData.STOP_MESSAGE);
            context.registerReceiver(this.mReceiver, filter, 2);
        }

        private void unregisterSvcCastReceiver() {
            try {
                Context context = this.mContextRef.get();
                if (context == null) {
                    DLog.w_api(
                            TAG,
                            this.mSubscriberTag
                                    + " unregisterSvcCastReceiver error - context is null");
                } else {
                    context.unregisterReceiver(this.mReceiver);
                }
            } catch (Exception ex) {
                DLog.w_api(TAG, "unregisterSvcCastReceiver Exception ", ex);
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public void disconnect() {
            unregisterSvcCastReceiver();
            if (this.mAllShareConnection != null) {
                try {
                    Context context = this.mContextRef.get();
                    if (context == null) {
                        DLog.w_api(
                                TAG, this.mSubscriberTag + " disconnect error - context is null");
                    } else {
                        context.unbindService(this.mAllShareConnection);
                        if (isAllShareServiceConnected()) {
                            this.mAllShareConnection.onServiceDisconnected(this.mComponentName);
                        }
                    }
                } catch (Exception e) {
                    DLog.w_api(TAG, "disconnect Exception", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onConnected() {
            DLog.d_api(TAG, "onConnected to " + this.mSubscriberTag);
            this.mConnecting = false;
            notifyAllShareEnable();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDisconnected() {
            DLog.d_api(TAG, "onDisconnected from " + this.mSubscriberTag);
            this.mConnecting = false;
            notifyAllShareDisable();
        }

        private void notifyAllShareEnable() {
            if (this.mConnectionCallback != null) {
                Message msg = new Message();
                IAllShareConnector.AllShareServiceState state =
                        IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_CONNECTED;
                msg.obj = state;
                this.mConnectionCallback.handleMessage(msg);
            }
        }

        private void notifyAllShareDisable() {
            if (this.mConnectionCallback != null) {
                Message msg = new Message();
                IAllShareConnector.AllShareServiceState state =
                        IAllShareConnector.AllShareServiceState.ALLSHARE_SERVICE_DISCONNECTED;
                msg.obj = state;
                this.mConnectionCallback.handleMessage(msg);
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public boolean isAllShareServiceConnected() {
            if (this.mISubscriber == null) {
                return false;
            }
            return true;
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public String getCaptionFilePathFromURI(String uri) {
            try {
                String filePath = this.mISubscriber.getCaptionFilePathFromURI(uri);
                return filePath == null ? "" : filePath;
            } catch (RemoteException e) {
                DLog.w_api(TAG, "getCaptionFilePathFromURI RemoteException", e);
                return "";
            } catch (Exception e2) {
                DLog.w_api(TAG, "getCaptionFilePathFromURI Exception", e2);
                return "";
            }
        }

        private boolean startAllShareLauncher() {
            Context context = this.mContextRef.get();
            if (context == null) {
                DLog.w_api(
                        TAG,
                        this.mSubscriberTag + " startAllShareLauncher error - context is null");
                return false;
            }
            Intent i = new Intent(this.mConstData.START_SERVICE);
            if (this.mConstData.START_SERVICE.startsWith(Const.SERVICE_PACKAGE)) {
                i.setPackage(Const.SERVICE_PACKAGE);
            }
            try {
                if (context.startService(i) == null) {
                    DLog.w_api(
                            TAG,
                            this.mSubscriberTag + " : AllShare Service is not installed yet...");
                    return false;
                }
                return true;
            } catch (SecurityException e) {
                DLog.w_api(
                        TAG,
                        this.mSubscriberTag + " startAllShareLauncher error...SecurityException ",
                        e);
                return false;
            } catch (Exception e2) {
                DLog.w_api(TAG, this.mSubscriberTag + " startAllShareLauncher exception ", e2);
                return false;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public void destroyInstance() {
            disconnect();
            DLog.d_api(TAG, "before destroyInstance, mConnecting=" + this.mConnecting);
            this.mConnecting = false;
            this.mConnectionCallback = null;
            this.mISubscriber = null;
            DLog.d_api(TAG, "after destroyInstance, mConnecting=" + this.mConnecting);
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public long requestCVMAsync(CVMessage cvm, AllShareResponseHandler handler) {
            if (this.mISubscriber == null || handler == null) {
                return -1L;
            }
            if (cvm.getBundle() == null) {
                cvm.setBundle(new Bundle());
            }
            long req_id = System.nanoTime();
            cvm.setMsgID(req_id);
            cvm.setMsgType(2);
            cvm.setMessenger(new Messenger(handler));
            try {
                if (!this.mISubscriber.requestCVAsync(this.mID, cvm)) {
                    DLog.d_api(
                            TAG,
                            this.mSubscriberTag
                                    + " requestCVMAsync fail...Maybe Invalid Action Request");
                    return -1L;
                }
                return req_id;
            } catch (RemoteException rex) {
                DLog.w_api(
                        TAG, this.mSubscriberTag + " requestCVMAsync error...RemoteException", rex);
                return -1L;
            } catch (Exception ex) {
                DLog.w_api(TAG, this.mSubscriberTag + " requestCVMAsync error...Exception", ex);
                return -1L;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public CVMessage requestCVMSync(CVMessage cvm) {
            if (this.mISubscriber == null || cvm == null) {
                return new CVMessage();
            }
            if (cvm.getBundle() == null) {
                cvm.setBundle(new Bundle());
            }
            cvm.setMsgType(2);
            try {
                CVMessage res = this.mISubscriber.requestCVSync(this.mID, cvm);
                return res;
            } catch (RemoteException rex) {
                CVMessage res2 = new CVMessage();
                DLog.w_api(
                        TAG, this.mSubscriberTag + " requestCVMSync error...RemoteException", rex);
                return res2;
            } catch (Exception ex) {
                DLog.w_api(TAG, this.mSubscriberTag + " requestCVMSync error...Exception", ex);
                return null;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public boolean subscribeAllShareEvent(
                String event, Bundle bundle, AllShareEventHandler handler) {
            if (this.mISubscriber == null) {
                return false;
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            CVMessage cvm = new CVMessage(4, event, bundle);
            Messenger messenger = new Messenger(handler);
            cvm.setMessenger(messenger);
            addHandlerToHashSet(event, bundle, handler);
            try {
                boolean res = this.mISubscriber.subscribeEvent(this.mID, cvm);
                return res;
            } catch (RemoteException e) {
                DLog.w_api(TAG, "subscribeAllShareEvent RemoteException", e);
                return false;
            } catch (RuntimeException e2) {
                DLog.w_api(TAG, "subscribeAllShareEvent RuntimeException", e2);
                return false;
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public void unsubscribeAllShareEvent(
                String event, Bundle bundle, AllShareEventHandler handler) {
            if (this.mISubscriber == null) {
                return;
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            CVMessage cvm = new CVMessage(4, event, bundle);
            Messenger messenger = new Messenger(handler);
            cvm.setMessenger(messenger);
            removeHandlerFromHashSet(event, bundle, handler);
            try {
                this.mISubscriber.unsubscribeEvent(this.mID, cvm);
            } catch (RemoteException e) {
                DLog.w_api(TAG, "unsubscribeAllShareEvent RemoteException", e);
            } catch (RuntimeException e2) {
                DLog.w_api(TAG, "unsubscribeAllShareEvent RuntimeException", e2);
            }
        }

        public void unsubscribeAllEvents() {
            synchronized (this.mEventHandlerSet) {
                HashSet<EventHandler> copySet = (HashSet) this.mEventHandlerSet.clone();
                Iterator<EventHandler> iter = copySet.iterator();
                while (iter.hasNext()) {
                    EventHandler value = iter.next();
                    unsubscribeAllShareEvent(value.mEventId, value.mBundle, value.mHanlder);
                }
                this.mEventHandlerSet.clear();
            }
        }

        public String getServiceVersion() {
            try {
                String version = this.mISubscriber.getServiceVersion();
                return version == null ? ColorExtractor.VERSION : version;
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

            public EventHandler(String event, Bundle bundle, AllShareEventHandler handler) {
                this.mEventId = null;
                this.mBundle = null;
                this.mHanlder = null;
                this.mEventId = event;
                this.mBundle = bundle;
                this.mHanlder = handler;
            }

            public boolean equals(Object o) {
                if (o == null) {
                    return false;
                }
                if (this == o) {
                    return true;
                }
                if (!(o instanceof EventHandler)) {
                    return false;
                }
                EventHandler obj = (EventHandler) o;
                if (!obj.mEventId.equals(this.mEventId)
                        || !obj.mBundle.equals(this.mBundle)
                        || !obj.mHanlder.equals(this.mHanlder)) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return super.hashCode();
            }
        }

        private void addHandlerToHashSet(
                String event, Bundle bundle, AllShareEventHandler handler) {
            synchronized (this.mEventHandlerSet) {
                EventHandler value = new EventHandler(event, bundle, handler);
                this.mEventHandlerSet.add(value);
            }
        }

        private void removeHandlerFromHashSet(
                String event, Bundle bundle, AllShareEventHandler handler) {
            synchronized (this.mEventHandlerSet) {
                EventHandler value = new EventHandler(event, bundle, handler);
                this.mEventHandlerSet.remove(value);
            }
        }

        @Override // com.samsung.android.allshare.IAllShareConnector
        public ContentResolver getContentResolver() {
            if (this.mContextRef == null || this.mContextRef.get() == null) {
                return null;
            }
            return this.mContextRef.get().getContentResolver();
        }
    }
}
