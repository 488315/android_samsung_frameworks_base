package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.telephony.TelephonyFrameworkInitializer;
import android.telephony.ims.aidl.IImsRcsController;
import android.telephony.ims.aidl.IRcsUceControllerCallback;
import android.telephony.ims.aidl.IRcsUcePublishStateCallback;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class RcsUceAdapter {

    @Deprecated
    public static final int CAPABILITY_TYPE_OPTIONS_UCE = 1;

    @SystemApi
    @Deprecated
    public static final int CAPABILITY_TYPE_PRESENCE_UCE = 2;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_ETAG_EXPIRED = 1;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_2G = 7;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_3G = 6;

    @SystemApi
    @Deprecated
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_EHRPD = 4;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_HSPAPLUS = 5;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_INTERNET_PDN = 12;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_IWLAN = 9;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_LTE_VOPS_DISABLED = 2;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_LTE_VOPS_ENABLED = 3;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_NR5G_VOPS_DISABLED = 10;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_NR5G_VOPS_ENABLED = 11;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_MOVE_TO_WLAN = 8;

    @SystemApi
    public static final int CAPABILITY_UPDATE_TRIGGER_UNKNOWN = 0;

    @SystemApi
    public static final int ERROR_FORBIDDEN = 6;

    @SystemApi
    public static final int ERROR_GENERIC_FAILURE = 1;

    @SystemApi
    public static final int ERROR_INSUFFICIENT_MEMORY = 10;

    @SystemApi
    public static final int ERROR_LOST_NETWORK = 11;

    @SystemApi
    public static final int ERROR_NOT_AUTHORIZED = 5;

    @SystemApi
    public static final int ERROR_NOT_AVAILABLE = 3;

    @SystemApi
    public static final int ERROR_NOT_ENABLED = 2;

    @SystemApi
    public static final int ERROR_NOT_FOUND = 7;

    @SystemApi
    public static final int ERROR_NOT_REGISTERED = 4;

    @SystemApi
    public static final int ERROR_REQUEST_TIMEOUT = 9;

    @SystemApi
    public static final int ERROR_REQUEST_TOO_LARGE = 8;

    @SystemApi
    public static final int ERROR_SERVER_UNAVAILABLE = 12;

    @SystemApi
    public static final int PUBLISH_STATE_NOT_PUBLISHED = 2;

    @SystemApi
    public static final int PUBLISH_STATE_OK = 1;

    @SystemApi
    public static final int PUBLISH_STATE_OTHER_ERROR = 6;

    @SystemApi
    public static final int PUBLISH_STATE_PUBLISHING = 7;

    @SystemApi
    public static final int PUBLISH_STATE_RCS_PROVISION_ERROR = 4;

    @SystemApi
    public static final int PUBLISH_STATE_REQUEST_TIMEOUT = 5;

    @SystemApi
    public static final int PUBLISH_STATE_VOICE_PROVISION_ERROR = 3;
    private static final String TAG = "RcsUceAdapter";
    private final Context mContext;
    private final Map<OnPublishStateChangedListener, PublishStateCallbackAdapter> mPublishStateCallbacks = new HashMap();
    private final int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PublishState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface RcsImsCapabilityFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StackPublishTriggerType {
    }

    @SystemApi
    public interface OnPublishStateChangedListener {
        @Deprecated
        void onPublishStateChange(int i);

        default void onPublishStateChange(PublishAttributes publishAttributes) {
            onPublishStateChange(publishAttributes.getPublishState());
        }
    }

    public static class PublishStateCallbackAdapter {
        private final PublishStateBinder mBinder;

        /* JADX INFO: Access modifiers changed from: private */
        static class PublishStateBinder extends IRcsUcePublishStateCallback.Stub {
            private final Executor mExecutor;
            private final OnPublishStateChangedListener mPublishStateChangeListener;

            PublishStateBinder(Executor executor, OnPublishStateChangedListener onPublishStateChangedListener) {
                this.mExecutor = executor;
                this.mPublishStateChangeListener = onPublishStateChangedListener;
            }

            @Override // android.telephony.ims.aidl.IRcsUcePublishStateCallback
            public void onPublishUpdated(final PublishAttributes publishAttributes) {
                if (this.mPublishStateChangeListener == null) {
                    return;
                }
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$PublishStateCallbackAdapter$PublishStateBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onPublishUpdated$0(publishAttributes);
                        }
                    });
                } finally {
                    restoreCallingIdentity(jClearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onPublishUpdated$0(PublishAttributes publishAttributes) {
                this.mPublishStateChangeListener.onPublishStateChange(publishAttributes);
            }
        }

        public PublishStateCallbackAdapter(Executor executor, OnPublishStateChangedListener onPublishStateChangedListener) {
            this.mBinder = new PublishStateBinder(executor, onPublishStateChangedListener);
        }

        public final IRcsUcePublishStateCallback getBinder() {
            return this.mBinder;
        }
    }

    @SystemApi
    public interface CapabilitiesCallback {
        void onCapabilitiesReceived(List<RcsContactUceCapability> list);

        default void onComplete() {
        }

        default void onError(int i, long j) {
        }

        default void onComplete(SipDetails sipDetails) {
            onComplete();
        }

        default void onError(int i, long j, SipDetails sipDetails) {
            onError(i, j);
        }
    }

    RcsUceAdapter(Context context, int i) {
        this.mContext = context;
        this.mSubId = i;
    }

    @SystemApi
    public void requestCapabilities(Collection<Uri> collection, Executor executor, CapabilitiesCallback capabilitiesCallback) throws ImsException {
        if (capabilitiesCallback == null) {
            throw new IllegalArgumentException("Must include a non-null CapabilitiesCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        if (collection == null) {
            throw new IllegalArgumentException("Must include non-null contact number list.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "requestCapabilities: IImsRcsController is null");
            throw new ImsException("Can not find remote IMS service", 1);
        }
        try {
            iImsRcsController.requestCapabilities(this.mSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), new ArrayList(collection), new AnonymousClass1(this, executor, capabilitiesCallback));
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#requestCapabilities", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.toString(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.RcsUceAdapter$1, reason: invalid class name */
    class AnonymousClass1 extends IRcsUceControllerCallback.Stub {
        final /* synthetic */ CapabilitiesCallback val$c;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(RcsUceAdapter rcsUceAdapter, Executor executor, CapabilitiesCallback capabilitiesCallback) {
            this.val$executor = executor;
            this.val$c = capabilitiesCallback;
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(final List<RcsContactUceCapability> list) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        capabilitiesCallback.onCapabilitiesReceived(list);
                    }
                });
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(final SipDetails sipDetails) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        capabilitiesCallback.onComplete(sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(final int i, final long j, final SipDetails sipDetails) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        capabilitiesCallback.onError(i, j, sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    @SystemApi
    public void requestAvailability(Uri uri, Executor executor, CapabilitiesCallback capabilitiesCallback) throws ImsException {
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Must include non-null contact number.");
        }
        if (capabilitiesCallback == null) {
            throw new IllegalArgumentException("Must include a non-null CapabilitiesCallback.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "requestAvailability: IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        try {
            iImsRcsController.requestAvailability(this.mSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), uri, new AnonymousClass2(this, executor, capabilitiesCallback));
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#requestAvailability", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.toString(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.RcsUceAdapter$2, reason: invalid class name */
    class AnonymousClass2 extends IRcsUceControllerCallback.Stub {
        final /* synthetic */ CapabilitiesCallback val$c;
        final /* synthetic */ Executor val$executor;

        AnonymousClass2(RcsUceAdapter rcsUceAdapter, Executor executor, CapabilitiesCallback capabilitiesCallback) {
            this.val$executor = executor;
            this.val$c = capabilitiesCallback;
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(final List<RcsContactUceCapability> list) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        capabilitiesCallback.onCapabilitiesReceived(list);
                    }
                });
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(final SipDetails sipDetails) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        capabilitiesCallback.onComplete(sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(final int i, final long j, final SipDetails sipDetails) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CapabilitiesCallback capabilitiesCallback = this.val$c;
                executor.execute(new Runnable() { // from class: android.telephony.ims.RcsUceAdapter$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        capabilitiesCallback.onError(i, j, sipDetails);
                    }
                });
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    @SystemApi
    public int getUcePublishState() throws ImsException {
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "getUcePublishState: IImsRcsController is null");
            throw new ImsException("Can not find remote IMS service", 1);
        }
        try {
            return iImsRcsController.getUcePublishState(this.mSubId);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#getUcePublishState", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @SystemApi
    public void addOnPublishStateChangedListener(Executor executor, OnPublishStateChangedListener onPublishStateChangedListener) throws ImsException {
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        if (onPublishStateChangedListener == null) {
            throw new IllegalArgumentException("Must include a non-null OnPublishStateChangedListener.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "addOnPublishStateChangedListener : IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        try {
            iImsRcsController.registerUcePublishStateCallback(this.mSubId, addPublishStateCallback(executor, onPublishStateChangedListener).getBinder());
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#registerUcePublishStateCallback", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @SystemApi
    public void removeOnPublishStateChangedListener(OnPublishStateChangedListener onPublishStateChangedListener) throws ImsException {
        if (onPublishStateChangedListener == null) {
            throw new IllegalArgumentException("Must include a non-null OnPublishStateChangedListener.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "removeOnPublishStateChangedListener: IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        PublishStateCallbackAdapter publishStateCallbackAdapterRemovePublishStateCallback = removePublishStateCallback(onPublishStateChangedListener);
        if (publishStateCallbackAdapterRemovePublishStateCallback == null) {
            return;
        }
        try {
            iImsRcsController.unregisterUcePublishStateCallback(this.mSubId, publishStateCallbackAdapterRemovePublishStateCallback.getBinder());
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#unregisterUcePublishStateCallback", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public boolean isUceSettingEnabled() throws ImsException {
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "isUceSettingEnabled: IImsRcsController is null");
            throw new ImsException("Can not find remote IMS service", 1);
        }
        try {
            return iImsRcsController.isUceSettingEnabled(this.mSubId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#isUceSettingEnabled", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        }
    }

    @SystemApi
    public void setUceSettingEnabled(boolean z) throws ImsException {
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.e(TAG, "setUceSettingEnabled: IImsRcsController is null");
            throw new ImsException("Can not find remote IMS service", 1);
        }
        try {
            iImsRcsController.setUceSettingEnabled(this.mSubId, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling IImsRcsController#setUceSettingEnabled", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        }
    }

    private PublishStateCallbackAdapter addPublishStateCallback(Executor executor, OnPublishStateChangedListener onPublishStateChangedListener) {
        PublishStateCallbackAdapter publishStateCallbackAdapter = new PublishStateCallbackAdapter(executor, onPublishStateChangedListener);
        synchronized (this.mPublishStateCallbacks) {
            this.mPublishStateCallbacks.put(onPublishStateChangedListener, publishStateCallbackAdapter);
        }
        return publishStateCallbackAdapter;
    }

    private PublishStateCallbackAdapter removePublishStateCallback(OnPublishStateChangedListener onPublishStateChangedListener) {
        PublishStateCallbackAdapter publishStateCallbackAdapterRemove;
        synchronized (this.mPublishStateCallbacks) {
            publishStateCallbackAdapterRemove = this.mPublishStateCallbacks.remove(onPublishStateChangedListener);
        }
        return publishStateCallbackAdapterRemove;
    }

    private IImsRcsController getIImsRcsController() {
        return IImsRcsController.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyImsServiceRegisterer().get());
    }
}
