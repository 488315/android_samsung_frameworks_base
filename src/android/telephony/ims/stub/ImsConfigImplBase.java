package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.Context;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.telephony.ims.RcsClientConfiguration;
import android.telephony.ims.RcsConfig;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IRcsConfigCallback;
import android.util.Log;
import com.android.internal.telephony.util.RemoteCallbackListExt;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes4.dex */
public class ImsConfigImplBase {
    public static final int CONFIG_RESULT_FAILED = 1;
    public static final int CONFIG_RESULT_SUCCESS = 0;
    public static final int CONFIG_RESULT_UNKNOWN = -1;
    private static final String TAG = "ImsConfigImplBase";
    private final RemoteCallbackListExt<IImsConfigCallback> mCallbacks;
    ImsConfigStub mImsConfigStub;
    private final RemoteCallbackListExt<IRcsConfigCallback> mRcsCallbacks;
    private byte[] mRcsConfigData;
    private final Object mRcsConfigDataLock;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetConfigResult {
    }

    public int getConfigInt(int i) {
        return -1;
    }

    public String getConfigString(int i) {
        return null;
    }

    public String getRcsClientConfiguration(int i) {
        return null;
    }

    public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) {
    }

    public void notifyRcsAutoConfigurationRemoved() {
    }

    public int setConfig(int i, int i2) {
        return 1;
    }

    public int setConfig(int i, String str) {
        return 1;
    }

    public void setRcsClientConfiguration(RcsClientConfiguration rcsClientConfiguration) {
    }

    public void triggerAutoConfiguration() {
    }

    public void updateImsCarrierConfigs(PersistableBundle persistableBundle) {
    }

    public static class ImsConfigStub extends IImsConfig.Stub {
        private Executor mExecutor;
        WeakReference<ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private HashMap<Integer, Integer> mProvisionedIntValue = new HashMap<>();
        private HashMap<Integer, String> mProvisionedStringValue = new HashMap<>();
        private final Object mLock = new Object();

        public ImsConfigStub(ImsConfigImplBase imsConfigImplBase, Executor executor) {
            this.mExecutor = executor;
            this.mImsConfigImplBaseWeakReference = new WeakReference<>(imsConfigImplBase);
        }

        private Executor getExecutor() {
            if (this.mExecutor == null) {
                this.mExecutor = new PendingIntent$$ExternalSyntheticLambda0();
            }
            return this.mExecutor;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addImsConfigCallback(final IImsConfigCallback iImsConfigCallback) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$addImsConfigCallback$0(iImsConfigCallback, atomicReference);
                }
            }, "addImsConfigCallback");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception addImsConfigCallback");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addImsConfigCallback$0(IImsConfigCallback iImsConfigCallback, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().addImsConfigCallback(iImsConfigCallback);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeImsConfigCallback(final IImsConfigCallback iImsConfigCallback) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeImsConfigCallback$1(iImsConfigCallback, atomicReference);
                }
            }, "removeImsConfigCallback");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception removeImsConfigCallback");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeImsConfigCallback$1(IImsConfigCallback iImsConfigCallback, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().removeImsConfigCallback(iImsConfigCallback);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int getConfigInt(final int i) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            int iIntValue = ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getConfigInt$2(i, atomicReference);
                }
            }, "getConfigInt")).intValue();
            if (atomicReference.get() == null) {
                return iIntValue;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception getConfigString");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$getConfigInt$2(int i, AtomicReference atomicReference) {
            synchronized (this.mLock) {
                if (this.mProvisionedIntValue.containsKey(Integer.valueOf(i))) {
                    return this.mProvisionedIntValue.get(Integer.valueOf(i));
                }
                int i2 = -1;
                try {
                    int configInt = getImsConfigImpl().getConfigInt(i);
                    if (configInt != -1) {
                        try {
                            this.mProvisionedIntValue.put(Integer.valueOf(i), Integer.valueOf(configInt));
                        } catch (RemoteException e) {
                            e = e;
                            i2 = configInt;
                            atomicReference.set(e);
                            return Integer.valueOf(i2);
                        }
                    }
                    return Integer.valueOf(configInt);
                } catch (RemoteException e2) {
                    e = e2;
                }
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public String getConfigString(final int i) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            String str = (String) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getConfigString$3(i, atomicReference);
                }
            }, "getConfigString");
            if (atomicReference.get() == null) {
                return str;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception getConfigString");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getConfigString$3(int i, AtomicReference atomicReference) {
            String str;
            synchronized (this.mLock) {
                if (this.mProvisionedStringValue.containsKey(Integer.valueOf(i))) {
                    str = this.mProvisionedStringValue.get(Integer.valueOf(i));
                } else {
                    String configString = null;
                    try {
                        configString = getImsConfigImpl().getConfigString(i);
                        if (configString != null) {
                            this.mProvisionedStringValue.put(Integer.valueOf(i), configString);
                        }
                        str = configString;
                    } catch (RemoteException e) {
                        atomicReference.set(e);
                        return configString;
                    }
                }
            }
            return str;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigInt(final int i, final int i2) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            int iIntValue = ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$setConfigInt$4(i, i2, atomicReference);
                }
            }, "setConfigInt")).intValue();
            if (atomicReference.get() == null) {
                return iIntValue;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception setConfigInt");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$setConfigInt$4(int i, int i2, AtomicReference atomicReference) {
            int config = -1;
            try {
                synchronized (this.mLock) {
                    this.mProvisionedIntValue.remove(Integer.valueOf(i));
                    config = getImsConfigImpl().setConfig(i, i2);
                    if (config == 0) {
                        this.mProvisionedIntValue.put(Integer.valueOf(i), Integer.valueOf(i2));
                    } else {
                        Log.d(ImsConfigImplBase.TAG, "Set provision value of " + i + " to " + i2 + " failed with error code " + config);
                    }
                }
                notifyImsConfigChanged(i, i2);
                return Integer.valueOf(config);
            } catch (RemoteException e) {
                atomicReference.set(e);
                return Integer.valueOf(config);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigString(final int i, final String str) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            int iIntValue = ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$setConfigString$5(i, str, atomicReference);
                }
            }, "setConfigString")).intValue();
            if (atomicReference.get() == null) {
                return iIntValue;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception setConfigInt");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$setConfigString$5(int i, String str, AtomicReference atomicReference) {
            int config = -1;
            try {
                synchronized (this.mLock) {
                    this.mProvisionedStringValue.remove(Integer.valueOf(i));
                    config = getImsConfigImpl().setConfig(i, str);
                    if (config == 0) {
                        this.mProvisionedStringValue.put(Integer.valueOf(i), str);
                    }
                }
                notifyImsConfigChanged(i, str);
                return Integer.valueOf(config);
            } catch (RemoteException e) {
                atomicReference.set(e);
                return Integer.valueOf(config);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void updateImsCarrierConfigs(final PersistableBundle persistableBundle) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateImsCarrierConfigs$6(persistableBundle, atomicReference);
                }
            }, "updateImsCarrierConfigs");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception updateImsCarrierConfigs");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateImsCarrierConfigs$6(PersistableBundle persistableBundle, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().updateImsCarrierConfigs(persistableBundle);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        private ImsConfigImplBase getImsConfigImpl() throws RemoteException {
            ImsConfigImplBase imsConfigImplBase = this.mImsConfigImplBaseWeakReference.get();
            if (imsConfigImplBase != null) {
                return imsConfigImplBase;
            }
            throw new RemoteException("Fail to get ImsConfigImpl");
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationReceived(final byte[] bArr, final boolean z) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyRcsAutoConfigurationReceived$7(bArr, z, atomicReference);
                }
            }, "notifyRcsAutoConfigurationReceived");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyRcsAutoConfigurationReceived");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRcsAutoConfigurationReceived$7(byte[] bArr, boolean z, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().onNotifyRcsAutoConfigurationReceived(bArr, z);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationRemoved() throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyRcsAutoConfigurationRemoved$8(atomicReference);
                }
            }, "notifyRcsAutoConfigurationRemoved");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyRcsAutoConfigurationRemoved");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRcsAutoConfigurationRemoved$8(AtomicReference atomicReference) {
            try {
                getImsConfigImpl().onNotifyRcsAutoConfigurationRemoved();
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationErrorReceived(int i, String str) throws RemoteException {
            getImsConfigImpl().notifyAutoConfigurationErrorReceived(i, str);
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsPreConfigurationReceived(byte[] bArr) throws RemoteException {
            getImsConfigImpl().notifyPreProvisioningReceived(bArr);
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyProvisionedIntValueChanged(int i, int i2) throws RemoteException {
            getImsConfigImpl().notifyProvisionedValueChanged(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyProvisionedStringValueChanged(int i, String str) throws RemoteException {
            getImsConfigImpl().notifyProvisionedValueChanged(i, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyImsConfigChanged(int i, int i2) throws RemoteException {
            getImsConfigImpl().notifyConfigChanged(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyImsConfigChanged(int i, String str) throws RemoteException {
            getImsConfigImpl().notifyConfigChanged(i, str);
        }

        protected void updateCachedValue(int i, int i2) {
            synchronized (this.mLock) {
                this.mProvisionedIntValue.put(Integer.valueOf(i), Integer.valueOf(i2));
            }
        }

        protected void updateCachedValue(int i, String str) {
            synchronized (this.mLock) {
                this.mProvisionedStringValue.put(Integer.valueOf(i), str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addRcsConfigCallback(final IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$addRcsConfigCallback$9(iRcsConfigCallback, atomicReference);
                }
            }, "addRcsConfigCallback");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception addRcsConfigCallback");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addRcsConfigCallback$9(IRcsConfigCallback iRcsConfigCallback, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().addRcsConfigCallback(iRcsConfigCallback);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeRcsConfigCallback(final IRcsConfigCallback iRcsConfigCallback) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeRcsConfigCallback$10(iRcsConfigCallback, atomicReference);
                }
            }, "removeRcsConfigCallback");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception removeRcsConfigCallback");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeRcsConfigCallback$10(IRcsConfigCallback iRcsConfigCallback, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().removeRcsConfigCallback(iRcsConfigCallback);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void triggerRcsReconfiguration() throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$triggerRcsReconfiguration$11(atomicReference);
                }
            }, "triggerRcsReconfiguration");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception triggerRcsReconfiguration");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerRcsReconfiguration$11(AtomicReference atomicReference) {
            try {
                getImsConfigImpl().triggerAutoConfiguration();
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public String getRcsClientConfiguration(int i) throws RemoteException {
            return getImsConfigImpl().getRcsClientConfiguration(i);
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void setRcsClientConfiguration(final RcsClientConfiguration rcsClientConfiguration) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setRcsClientConfiguration$12(rcsClientConfiguration, atomicReference);
                }
            }, "setRcsClientConfiguration");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception setRcsClientConfiguration");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setRcsClientConfiguration$12(RcsClientConfiguration rcsClientConfiguration, AtomicReference atomicReference) {
            try {
                getImsConfigImpl().setRcsClientConfiguration(rcsClientConfiguration);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyIntImsConfigChanged(final int i, final int i2) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyIntImsConfigChanged$13(i, i2, atomicReference);
                }
            }, "notifyIntImsConfigChanged");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyIntImsConfigChanged");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyIntImsConfigChanged$13(int i, int i2, AtomicReference atomicReference) {
            try {
                notifyImsConfigChanged(i, i2);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyStringImsConfigChanged(final int i, final String str) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyStringImsConfigChanged$14(i, str, atomicReference);
                }
            }, "notifyStringImsConfigChanged");
            if (atomicReference.get() == null) {
                return;
            }
            Log.d(ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyStringImsConfigChanged");
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyStringImsConfigChanged$14(int i, String str, AtomicReference atomicReference) {
            try {
                notifyImsConfigChanged(i, str);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        public void clearCachedValue() {
            Log.i(ImsConfigImplBase.TAG, "clearCachedValue");
            synchronized (this.mLock) {
                this.mProvisionedIntValue.clear();
                this.mProvisionedStringValue.clear();
            }
        }

        private void executeMethodAsync(final Runnable runnable, String str) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, getExecutor()).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsConfigImplBase.TAG, "ImsConfigImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) throws RemoteException {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return TelephonyUtils.runWithCleanCallingIdentity(supplier);
                    }
                }, getExecutor()).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(ImsConfigImplBase.TAG, "ImsConfigImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public ImsConfigImplBase(Executor executor) {
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mRcsCallbacks = new RemoteCallbackListExt<>();
        this.mRcsConfigDataLock = new Object();
        this.mImsConfigStub = new ImsConfigStub(this, executor);
    }

    public ImsConfigImplBase(Context context) {
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mRcsCallbacks = new RemoteCallbackListExt<>();
        this.mRcsConfigDataLock = new Object();
        this.mImsConfigStub = new ImsConfigStub(this, null);
    }

    public ImsConfigImplBase() {
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mRcsCallbacks = new RemoteCallbackListExt<>();
        this.mRcsConfigDataLock = new Object();
        this.mImsConfigStub = new ImsConfigStub(this, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addImsConfigCallback(IImsConfigCallback iImsConfigCallback) {
        this.mCallbacks.register(iImsConfigCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsConfigCallback(IImsConfigCallback iImsConfigCallback) {
        this.mCallbacks.unregister(iImsConfigCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyConfigChanged(final int i, final int i2) {
        RemoteCallbackListExt<IImsConfigCallback> remoteCallbackListExt = this.mCallbacks;
        if (remoteCallbackListExt == null) {
            return;
        }
        synchronized (remoteCallbackListExt) {
            this.mCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImsConfigImplBase.lambda$notifyConfigChanged$0(i, i2, (IImsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyConfigChanged$0(int i, int i2, IImsConfigCallback iImsConfigCallback) {
        try {
            iImsConfigCallback.onIntConfigChanged(i, i2);
        } catch (RemoteException unused) {
            Log.w(TAG, "notifyConfigChanged(int): dead binder in notify, skipping.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigChanged(final int i, final String str) {
        RemoteCallbackListExt<IImsConfigCallback> remoteCallbackListExt = this.mCallbacks;
        if (remoteCallbackListExt == null) {
            return;
        }
        synchronized (remoteCallbackListExt) {
            this.mCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImsConfigImplBase.lambda$notifyConfigChanged$1(i, str, (IImsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyConfigChanged$1(int i, String str, IImsConfigCallback iImsConfigCallback) {
        try {
            iImsConfigCallback.onStringConfigChanged(i, str);
        } catch (RemoteException unused) {
            Log.w(TAG, "notifyConfigChanged(string): dead binder in notify, skipping.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) {
        this.mRcsCallbacks.register(iRcsConfigCallback);
        synchronized (this.mRcsConfigDataLock) {
            byte[] bArr = this.mRcsConfigData;
            if (bArr == null) {
                return;
            }
            byte[] bArr2 = (byte[]) bArr.clone();
            try {
                iRcsConfigCallback.onConfigurationChanged(bArr2);
            } catch (RemoteException unused) {
                Log.w(TAG, "dead binder to call onConfigurationChanged, skipping.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRcsConfigCallback(IRcsConfigCallback iRcsConfigCallback) {
        this.mRcsCallbacks.unregister(iRcsConfigCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) {
        final byte[] bArrDecompressGzip = z ? RcsConfig.decompressGzip(bArr) : bArr;
        synchronized (this.mRcsConfigDataLock) {
            if (Arrays.equals(this.mRcsConfigData, bArr)) {
                return;
            }
            this.mRcsConfigData = bArrDecompressGzip;
            RemoteCallbackListExt<IRcsConfigCallback> remoteCallbackListExt = this.mRcsCallbacks;
            if (remoteCallbackListExt != null) {
                synchronized (remoteCallbackListExt) {
                    this.mRcsCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ImsConfigImplBase.lambda$onNotifyRcsAutoConfigurationReceived$2(bArrDecompressGzip, (IRcsConfigCallback) obj);
                        }
                    });
                }
            }
            notifyRcsAutoConfigurationReceived(bArr, z);
        }
    }

    static /* synthetic */ void lambda$onNotifyRcsAutoConfigurationReceived$2(byte[] bArr, IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onConfigurationChanged((byte[]) bArr.clone());
        } catch (RemoteException unused) {
            Log.w(TAG, "dead binder in notifyRcsAutoConfigurationReceived, skipping.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotifyRcsAutoConfigurationRemoved() {
        synchronized (this.mRcsConfigDataLock) {
            this.mRcsConfigData = null;
        }
        RemoteCallbackListExt<IRcsConfigCallback> remoteCallbackListExt = this.mRcsCallbacks;
        if (remoteCallbackListExt != null) {
            synchronized (remoteCallbackListExt) {
                this.mRcsCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ImsConfigImplBase.lambda$onNotifyRcsAutoConfigurationRemoved$3((IRcsConfigCallback) obj);
                    }
                });
            }
        }
        notifyRcsAutoConfigurationRemoved();
    }

    static /* synthetic */ void lambda$onNotifyRcsAutoConfigurationRemoved$3(IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onConfigurationReset();
        } catch (RemoteException unused) {
            Log.w(TAG, "dead binder in notifyRcsAutoConfigurationRemoved, skipping.");
        }
    }

    public IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public final void notifyProvisionedValueChanged(int i, int i2) {
        this.mImsConfigStub.updateCachedValue(i, i2);
        try {
            this.mImsConfigStub.notifyImsConfigChanged(i, i2);
        } catch (RemoteException unused) {
            Log.w(TAG, "notifyProvisionedValueChanged(int): Framework connection is dead.");
        }
    }

    public final void notifyProvisionedValueChanged(int i, String str) {
        this.mImsConfigStub.updateCachedValue(i, str);
        try {
            this.mImsConfigStub.notifyImsConfigChanged(i, str);
        } catch (RemoteException unused) {
            Log.w(TAG, "notifyProvisionedValueChanged(string): Framework connection is dead.");
        }
    }

    public final void notifyAutoConfigurationErrorReceived(final int i, final String str) {
        RemoteCallbackListExt<IRcsConfigCallback> remoteCallbackListExt = this.mRcsCallbacks;
        if (remoteCallbackListExt == null) {
            return;
        }
        synchronized (remoteCallbackListExt) {
            this.mRcsCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImsConfigImplBase.lambda$notifyAutoConfigurationErrorReceived$4(i, str, (IRcsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyAutoConfigurationErrorReceived$4(int i, String str, IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onAutoConfigurationErrorReceived(i, str);
        } catch (RemoteException unused) {
            Log.w(TAG, "dead binder in notifyAutoConfigurationErrorReceived, skipping.");
        }
    }

    public final void notifyPreProvisioningReceived(final byte[] bArr) {
        RemoteCallbackListExt<IRcsConfigCallback> remoteCallbackListExt = this.mRcsCallbacks;
        if (remoteCallbackListExt == null) {
            return;
        }
        synchronized (remoteCallbackListExt) {
            this.mRcsCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImsConfigImplBase.lambda$notifyPreProvisioningReceived$5(bArr, (IRcsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyPreProvisioningReceived$5(byte[] bArr, IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onPreProvisioningReceived(bArr);
        } catch (RemoteException unused) {
            Log.w(TAG, "dead binder in notifyPreProvisioningReceived, skipping.");
        }
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mImsConfigStub.mExecutor == null) {
            this.mImsConfigStub.mExecutor = executor;
        }
    }

    public final void clearConfigurationCache() {
        this.mImsConfigStub.clearCachedValue();
        synchronized (this.mRcsConfigDataLock) {
            this.mRcsConfigData = null;
        }
    }
}
