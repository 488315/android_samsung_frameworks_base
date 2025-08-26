package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.telephony.BinderCacheManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyFrameworkInitializer;
import android.telephony.ims.RegistrationManager;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Log;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class ImsMmTelManager implements RegistrationManager {
    private static final String TAG = "ImsMmTelManager";
    public static final int WIFI_MODE_CELLULAR_PREFERRED = 1;
    public static final int WIFI_MODE_UNKNOWN = -1;
    public static final int WIFI_MODE_WIFI_ONLY = 0;
    public static final int WIFI_MODE_WIFI_PREFERRED = 2;
    private static final BinderCacheManager<ITelephony> sTelephonyCache = new BinderCacheManager<>(new BinderCacheManager.BinderInterfaceFactory() { // from class: android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda0
        @Override // android.telephony.BinderCacheManager.BinderInterfaceFactory
        public final Object create() {
            return ImsMmTelManager.getITelephonyInterface();
        }
    });
    private final BinderCacheManager<ITelephony> mBinderCache;
    private final Context mContext;
    private final int mSubId;

    @SystemApi
    @Deprecated
    public static class RegistrationCallback extends RegistrationManager.RegistrationCallback {
        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onRegistered(int i) {
        }

        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onRegistering(int i) {
        }

        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) {
        }

        @Override // android.telephony.ims.RegistrationManager.RegistrationCallback
        public void onUnregistered(ImsReasonInfo imsReasonInfo) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WiFiCallingMode {
    }

    public static class CapabilityCallback {
        private final CapabilityBinder mBinder = new CapabilityBinder(this);

        public void onCapabilitiesStatusChanged(MmTelFeature.MmTelCapabilities mmTelCapabilities) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class CapabilityBinder extends IImsCapabilityCallback.Stub {
            private Executor mExecutor;
            private final CapabilityCallback mLocalCallback;

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onChangeCapabilityConfigurationError(int i, int i2, int i3) {
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onQueryCapabilityConfiguration(int i, int i2, boolean z) {
            }

            CapabilityBinder(CapabilityCallback capabilityCallback) {
                this.mLocalCallback = capabilityCallback;
            }

            @Override // android.telephony.ims.aidl.IImsCapabilityCallback
            public void onCapabilitiesStatusChanged(final int i) {
                Log.i(ImsMmTelManager.TAG, "onCapabilitiesStatusChanged()");
                if (this.mLocalCallback == null) {
                    return;
                }
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$CapabilityCallback$CapabilityBinder$$ExternalSyntheticLambda0
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
                this.mLocalCallback.onCapabilitiesStatusChanged(new MmTelFeature.MmTelCapabilities(i));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }
        }

        public final IImsCapabilityCallback getBinder() {
            return this.mBinder;
        }

        public final void setExecutor(Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }

    @SystemApi
    @Deprecated
    public static ImsMmTelManager createForSubscriptionId(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        return new ImsMmTelManager(i, sTelephonyCache);
    }

    public ImsMmTelManager(int i, BinderCacheManager<ITelephony> binderCacheManager) {
        this(null, i, binderCacheManager);
    }

    public ImsMmTelManager(Context context, int i, BinderCacheManager<ITelephony> binderCacheManager) {
        this.mContext = context;
        this.mSubId = i;
        this.mBinderCache = binderCacheManager;
    }

    @SystemApi
    @Deprecated
    public void registerImsRegistrationCallback(Executor executor, RegistrationCallback registrationCallback) throws ImsException {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        registrationCallback.setExecutor(executor);
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException | IllegalStateException e) {
            throw new ImsException(e.getMessage(), 1);
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    public void registerImsRegistrationCallback(Executor executor, RegistrationManager.RegistrationCallback registrationCallback) throws ImsException {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        registrationCallback.setExecutor(executor);
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException | IllegalStateException e) {
            throw new ImsException(e.getMessage(), 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @SystemApi
    @Deprecated
    public void unregisterImsRegistrationCallback(RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    public void unregisterImsRegistrationCallback(RegistrationManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void registerImsEmergencyRegistrationCallback(Executor executor, RegistrationManager.RegistrationCallback registrationCallback) throws ImsException {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        registrationCallback.setExecutor(executor);
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerImsEmergencyRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        } catch (IllegalStateException e3) {
            throw new ImsException(e3.getMessage(), 1);
        }
    }

    @SystemApi
    public void unregisterImsEmergencyRegistrationCallback(RegistrationManager.RegistrationCallback registrationCallback) {
        if (registrationCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            Log.w(TAG, "Could not find Telephony Service.");
            return;
        }
        try {
            iTelephony.unregisterImsEmergencyRegistrationCallback(this.mSubId, registrationCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.telephony.ims.RegistrationManager
    @SystemApi
    public void getRegistrationState(Executor executor, final Consumer<Integer> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Must include a non-null callback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.getImsMmTelRegistrationState(this.mSubId, new AnonymousClass1(this, executor, consumer));
        } catch (RemoteException | ServiceSpecificException e) {
            Log.w(TAG, "Error getting registration state: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(0);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$1, reason: invalid class name */
    class AnonymousClass1 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$stateCallback;

        AnonymousClass1(ImsMmTelManager imsMmTelManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$stateCallback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$stateCallback;
                executor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$1$$ExternalSyntheticLambda0
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

    @Override // android.telephony.ims.RegistrationManager
    public void getRegistrationTransportType(Executor executor, final Consumer<Integer> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Must include a non-null callback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.getImsMmTelRegistrationTransportType(this.mSubId, new AnonymousClass2(this, executor, consumer));
        } catch (RemoteException | ServiceSpecificException e) {
            Log.w(TAG, "Error getting transport type: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(-1);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$2, reason: invalid class name */
    class AnonymousClass2 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$transportTypeCallback;

        AnonymousClass2(ImsMmTelManager imsMmTelManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$transportTypeCallback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$transportTypeCallback;
                executor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$2$$ExternalSyntheticLambda0
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

    public void registerMmTelCapabilityCallback(Executor executor, CapabilityCallback capabilityCallback) throws ImsException {
        if (capabilityCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        capabilityCallback.setExecutor(executor);
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.registerMmTelCapabilityCallback(this.mSubId, capabilityCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        } catch (IllegalStateException e3) {
            throw new ImsException(e3.getMessage(), 1);
        }
    }

    public void unregisterMmTelCapabilityCallback(CapabilityCallback capabilityCallback) {
        if (capabilityCallback == null) {
            throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            Log.w(TAG, "Could not find Telephony Service.");
            return;
        }
        try {
            iTelephony.unregisterMmTelCapabilityCallback(this.mSubId, capabilityCallback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isAdvancedCallingSettingEnabled() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isAdvancedCallingSettingEnabled(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setAdvancedCallingSettingEnabled(boolean z) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setAdvancedCallingSettingEnabled(this.mSubId, z);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public boolean isCapable(int i, int i2) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isCapable(this.mSubId, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public boolean isAvailable(int i, int i2) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isAvailable(this.mSubId, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void isSupported(int i, int i2, Executor executor, Consumer<Boolean> consumer) throws ImsException {
        if (consumer == null) {
            throw new IllegalArgumentException("Must include a non-null Consumer.");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.isMmTelCapabilitySupported(this.mSubId, new AnonymousClass3(this, executor, consumer), i, i2);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$3, reason: invalid class name */
    class AnonymousClass3 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass3(ImsMmTelManager imsMmTelManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Boolean.valueOf(i == 1));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    public boolean isVtSettingEnabled() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isVtSettingEnabled(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setVtSettingEnabled(boolean z) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVtSettingEnabled(this.mSubId, z);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    public boolean isVoWiFiSettingEnabled() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isVoWiFiSettingEnabled(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setVoWiFiSettingEnabled(boolean z) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiSettingEnabled(this.mSubId, z);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    public boolean isCrossSimCallingEnabled() throws ImsException {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            return iTelephony.isCrossSimCallingEnabledByUser(this.mSubId);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    @SystemApi
    public void setCrossSimCallingEnabled(boolean z) throws ImsException {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.setCrossSimCallingEnabled(this.mSubId, z);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public boolean isVoWiFiRoamingSettingEnabled() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isVoWiFiRoamingSettingEnabled(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setVoWiFiRoamingSettingEnabled(boolean z) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiRoamingSettingEnabled(this.mSubId, z);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setVoWiFiNonPersistent(boolean z, int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiNonPersistent(this.mSubId, z, i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    public int getVoWiFiModeSetting() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.getVoWiFiModeSetting(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setVoWiFiModeSetting(int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiModeSetting(this.mSubId, i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public int getVoWiFiRoamingModeSetting() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.getVoWiFiRoamingModeSetting(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setVoWiFiRoamingModeSetting(int i) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setVoWiFiRoamingModeSetting(this.mSubId, i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void setRttCapabilitySetting(boolean z) {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            iTelephony.setRttCapabilitySetting(this.mSubId, z);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    public boolean isTtyOverVolteEnabled() {
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new RuntimeException("Could not find Telephony Service.");
        }
        try {
            return iTelephony.isTtyOverVolteEnabled(this.mSubId);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                throw new IllegalArgumentException(e2.getMessage());
            }
            throw new RuntimeException(e2.getMessage());
        }
    }

    @SystemApi
    public void getFeatureState(Executor executor, Consumer<Integer> consumer) throws ImsException {
        if (executor == null) {
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        if (consumer == null) {
            throw new IllegalArgumentException("Must include a non-null Consumer.");
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new ImsException("Could not find Telephony Service.", 1);
        }
        try {
            iTelephony.getImsMmTelFeatureState(this.mSubId, new AnonymousClass4(this, executor, consumer));
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    /* renamed from: android.telephony.ims.ImsMmTelManager$4, reason: invalid class name */
    class AnonymousClass4 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass4(ImsMmTelManager imsMmTelManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.ims.ImsMmTelManager$4$$ExternalSyntheticLambda0
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

    public void registerImsStateCallback(Executor executor, ImsStateCallback imsStateCallback) throws ImsException {
        Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        Objects.requireNonNull(executor, "Must include a non-null Executor.");
        imsStateCallback.init(executor);
        BinderCacheManager<ITelephony> binderCacheManager = this.mBinderCache;
        Objects.requireNonNull(imsStateCallback);
        ITelephony iTelephony = (ITelephony) binderCacheManager.listenOnBinder(imsStateCallback, new ImsMmTelManager$$ExternalSyntheticLambda3(imsStateCallback));
        if (iTelephony == null) {
            throw new ImsException("Telephony server is down", 1);
        }
        try {
            iTelephony.registerImsStateCallback(this.mSubId, 1, imsStateCallback.getCallbackBinder(), getOpPackageName());
        } catch (RemoteException | IllegalStateException e) {
            throw new ImsException(e.getMessage(), 1);
        } catch (ServiceSpecificException e2) {
            throw new ImsException(e2.getMessage(), e2.errorCode);
        }
    }

    public void unregisterImsStateCallback(ImsStateCallback imsStateCallback) {
        Objects.requireNonNull(imsStateCallback, "Must include a non-null ImsStateCallback.");
        ITelephony iTelephony = (ITelephony) this.mBinderCache.removeRunnable(imsStateCallback);
        if (iTelephony != null) {
            try {
                iTelephony.unregisterImsStateCallback(imsStateCallback.getCallbackBinder());
            } catch (RemoteException unused) {
            }
        }
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context != null) {
            return context.getOpPackageName();
        }
        return null;
    }

    private ITelephony getITelephony() {
        return (ITelephony) this.mBinderCache.getBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ITelephony getITelephonyInterface() {
        return ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }

    public static String wifiCallingModeToString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "WIFI_ONLY";
        }
        if (i == 1) {
            return "CELLULAR_PREFERRED";
        }
        if (i == 2) {
            return "WIFI_PREFERRED";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }
}
