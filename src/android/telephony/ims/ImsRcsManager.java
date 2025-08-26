package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.telephony.BinderCacheManager;
import android.telephony.TelephonyFrameworkInitializer;
import android.telephony.ims.RegistrationManager;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsRcsController;
import android.util.Log;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ImsRcsManager {
    public static final String ACTION_SHOW_CAPABILITY_DISCOVERY_OPT_IN = "android.telephony.ims.action.SHOW_CAPABILITY_DISCOVERY_OPT_IN";
    public static final int CAPABILITY_TYPE_MAX = 3;
    public static final int CAPABILITY_TYPE_NONE = 0;
    public static final int CAPABILITY_TYPE_OPTIONS_UCE = 1;
    public static final int CAPABILITY_TYPE_PRESENCE_UCE = 2;
    private static final String TAG = "ImsRcsManager";
    private final Map<OnAvailabilityChangedListener, AvailabilityCallbackAdapter> mAvailabilityChangedCallbacks = new HashMap();
    private final BinderCacheManager<IImsRcsController> mBinderCache;
    private final Context mContext;
    private final int mSubId;
    private final BinderCacheManager<ITelephony> mTelephonyBinderCache;

    @SystemApi
    public interface OnAvailabilityChangedListener {
        void onAvailabilityChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RcsImsCapabilityFlag {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AvailabilityCallbackAdapter {
        private final CapabilityBinder mBinder;

        /* JADX INFO: Access modifiers changed from: private */
        static class CapabilityBinder extends IImsCapabilityCallback.Stub {
            private final Executor mExecutor;
            private final OnAvailabilityChangedListener mOnAvailabilityChangedListener;

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onChangeCapabilityConfigurationError(int i, int i2, int i3) {
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onQueryCapabilityConfiguration(int i, int i2, boolean z) {
            }

            CapabilityBinder(OnAvailabilityChangedListener onAvailabilityChangedListener, Executor executor) {
                this.mExecutor = executor;
                this.mOnAvailabilityChangedListener = onAvailabilityChangedListener;
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onCapabilitiesStatusChanged(final int i) {
                if (this.mOnAvailabilityChangedListener == null) {
                    return;
                }
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.ImsRcsManager$AvailabilityCallbackAdapter$CapabilityBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCapabilitiesStatusChanged$0(i);
                        }
                    });
                } finally {
                    restoreCallingIdentity(jClearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCapabilitiesStatusChanged$0(int i) {
                this.mOnAvailabilityChangedListener.onAvailabilityChanged(i);
            }
        }

        AvailabilityCallbackAdapter(Executor executor, OnAvailabilityChangedListener onAvailabilityChangedListener) {
            this.mBinder = new CapabilityBinder(onAvailabilityChangedListener, executor);
        }

        public final IImsCapabilityCallback getBinder() {
            return this.mBinder;
        }
    }

    public ImsRcsManager(Context context, int i, BinderCacheManager<IImsRcsController> binderCacheManager, BinderCacheManager<ITelephony> binderCacheManager2) {
        this.mSubId = i;
        this.mContext = context;
        this.mBinderCache = binderCacheManager;
        this.mTelephonyBinderCache = binderCacheManager2;
    }

    public RcsUceAdapter getUceAdapter() {
        return new RcsUceAdapter(this.mContext, this.mSubId);
    }

    public void registerImsRegistrationCallback(Executor executor, RegistrationManager.RegistrationCallback registrationCallback) throws ImsException {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "Register registration callback: IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        registrationCallback.setExecutor(executor);
        try {
            iImsRcsController.registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException | IllegalStateException e) {
            throw new ImsException(e.getMessage(), 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.toString(), e2.errorCode);
        }
    }

    public void unregisterImsRegistrationCallback(RegistrationManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "Unregister registration callback: IImsRcsController is null");
            throw new IllegalStateException("Cannot find remote IMS service");
        }
        try {
            iImsRcsController.unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void getRegistrationState(Executor executor, final Consumer<Integer> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Must include a non-null stateCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "Get registration state error: IImsRcsController is null");
            throw new IllegalStateException("Cannot find remote IMS service");
        }
        try {
            iImsRcsController.getImsRcsRegistrationState(this.mSubId, new AnonymousClass1(this, executor, consumer));
        } catch (RemoteException | ServiceSpecificException e) {
            Log.w(TAG, "Get registration state error: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.ims.ImsRcsManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(0);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsRcsManager$1, reason: invalid class name */
    class AnonymousClass1 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$stateCallback;

        AnonymousClass1(ImsRcsManager imsRcsManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$stateCallback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$stateCallback;
                executor.execute(new Runnable() { // from class: android.telephony.ims.ImsRcsManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(i));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    public void getRegistrationTransportType(Executor executor, final Consumer<Integer> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Must include a non-null transportTypeCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "Get registration transport type error: IImsRcsController is null");
            throw new IllegalStateException("Cannot find remote IMS service");
        }
        try {
            iImsRcsController.getImsRcsRegistrationTransportType(this.mSubId, new AnonymousClass2(this, executor, consumer));
        } catch (RemoteException | ServiceSpecificException e) {
            Log.w(TAG, "Get registration transport type error: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.ims.ImsRcsManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(-1);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsRcsManager$2, reason: invalid class name */
    class AnonymousClass2 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$transportTypeCallback;

        AnonymousClass2(ImsRcsManager imsRcsManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$transportTypeCallback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$transportTypeCallback;
                executor.execute(new Runnable() { // from class: android.telephony.ims.ImsRcsManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(i));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    @SystemApi
    public void addOnAvailabilityChangedListener(Executor executor, OnAvailabilityChangedListener onAvailabilityChangedListener) throws ImsException {
        if (onAvailabilityChangedListener == null) {
            throw new IllegalArgumentException("Must include a non-nullOnAvailabilityChangedListener.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "Add availability changed listener: IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        try {
            iImsRcsController.registerRcsAvailabilityCallback(this.mSubId, addAvailabilityChangedListenerToCollection(executor, onAvailabilityChangedListener).getBinder());
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling IImsRcsController#registerRcsAvailabilityCallback", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.toString(), e2.errorCode);
        }
    }

    @SystemApi
    public void removeOnAvailabilityChangedListener(OnAvailabilityChangedListener onAvailabilityChangedListener) {
        if (onAvailabilityChangedListener == null) {
            throw new IllegalArgumentException("Must include a non-nullOnAvailabilityChangedListener.");
        }
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "Remove availability changed listener: IImsRcsController is null");
            return;
        }
        AvailabilityCallbackAdapter availabilityCallbackAdapterRemoveAvailabilityChangedListenerFromCollection = removeAvailabilityChangedListenerFromCollection(onAvailabilityChangedListener);
        if (availabilityCallbackAdapterRemoveAvailabilityChangedListenerFromCollection == null) {
            return;
        }
        try {
            iImsRcsController.unregisterRcsAvailabilityCallback(this.mSubId, availabilityCallbackAdapterRemoveAvailabilityChangedListenerFromCollection.getBinder());
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling IImsRcsController#unregisterRcsAvailabilityCallback", e);
        }
    }

    @SystemApi
    public boolean isCapable(int i, int i2) throws ImsException {
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "isCapable: IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        try {
            return iImsRcsController.isCapable(this.mSubId, i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling IImsRcsController#isCapable", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @SystemApi
    public boolean isAvailable(int i, int i2) throws ImsException {
        IImsRcsController iImsRcsController = getIImsRcsController();
        if (iImsRcsController == null) {
            Log.w(TAG, "isAvailable: IImsRcsController is null");
            throw new ImsException("Cannot find remote IMS service", 1);
        }
        try {
            return iImsRcsController.isAvailable(this.mSubId, i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling IImsRcsController#isAvailable", e);
            throw new ImsException("Remote IMS Service is not available", 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void registerImsStateCallback(Executor executor, ImsStateCallback imsStateCallback) throws ImsException {
        Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        Objects.requireNonNull(executor, "Must include a non-null Executor.");
        imsStateCallback.init(executor);
        BinderCacheManager<ITelephony> binderCacheManager = this.mTelephonyBinderCache;
        Objects.requireNonNull(imsStateCallback);
        ITelephony iTelephony = (ITelephony) binderCacheManager.listenOnBinder(imsStateCallback, new ImsMmTelManager$$ExternalSyntheticLambda3(imsStateCallback));
        if (iTelephony == null) {
            throw new ImsException("Telephony server is down", 1);
        }
        try {
            iTelephony.registerImsStateCallback(this.mSubId, 2, imsStateCallback.getCallbackBinder(), this.mContext.getOpPackageName());
        } catch (RemoteException | IllegalStateException e) {
            throw new ImsException(e.getMessage(), 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void unregisterImsStateCallback(ImsStateCallback imsStateCallback) {
        Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        ITelephony iTelephony = (ITelephony) this.mTelephonyBinderCache.removeRunnable(imsStateCallback);
        if (iTelephony != null) {
            try {
                iTelephony.unregisterImsStateCallback(imsStateCallback.getCallbackBinder());
            } catch (RemoteException unused) {
            }
        }
    }

    private AvailabilityCallbackAdapter addAvailabilityChangedListenerToCollection(Executor executor, OnAvailabilityChangedListener onAvailabilityChangedListener) {
        AvailabilityCallbackAdapter availabilityCallbackAdapter = new AvailabilityCallbackAdapter(executor, onAvailabilityChangedListener);
        synchronized (this.mAvailabilityChangedCallbacks) {
            this.mAvailabilityChangedCallbacks.put(onAvailabilityChangedListener, availabilityCallbackAdapter);
        }
        return availabilityCallbackAdapter;
    }

    private AvailabilityCallbackAdapter removeAvailabilityChangedListenerFromCollection(OnAvailabilityChangedListener onAvailabilityChangedListener) {
        AvailabilityCallbackAdapter availabilityCallbackAdapterRemove;
        synchronized (this.mAvailabilityChangedCallbacks) {
            availabilityCallbackAdapterRemove = this.mAvailabilityChangedCallbacks.remove(onAvailabilityChangedListener);
        }
        return availabilityCallbackAdapterRemove;
    }

    private IImsRcsController getIImsRcsController() {
        return IImsRcsController.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyImsServiceRegisterer().get());
    }
}
