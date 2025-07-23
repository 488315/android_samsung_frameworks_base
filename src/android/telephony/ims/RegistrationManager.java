package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.telephony.ims.RegistrationManager;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public interface RegistrationManager {
    public static final Map<Integer, Integer> IMS_REG_TO_ACCESS_TYPE_MAP = Map.of(-1, -1, 0, 1, 3, 1, 1, 2, 2, 2);
    public static final int REGISTRATION_STATE_NOT_REGISTERED = 0;
    public static final int REGISTRATION_STATE_REGISTERED = 2;
    public static final int REGISTRATION_STATE_REGISTERING = 1;

    @SystemApi
    public static final int SUGGESTED_ACTION_NONE = 0;

    @SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_CLEAR_RAT_BLOCKS = 4;

    @SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_PLMN_BLOCK = 1;

    @SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_PLMN_BLOCK_WITH_TIMEOUT = 2;

    @SystemApi
    public static final int SUGGESTED_ACTION_TRIGGER_RAT_BLOCK = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsRegistrationState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuggestedAction {
    }

    void getRegistrationState(Executor executor, Consumer<Integer> consumer);

    void getRegistrationTransportType(Executor executor, Consumer<Integer> consumer);

    void registerImsRegistrationCallback(Executor executor, RegistrationCallback registrationCallback) throws ImsException;

    void unregisterImsRegistrationCallback(RegistrationCallback registrationCallback);

    static String registrationStateToString(int i) {
        if (i == 0) {
            return "REGISTRATION_STATE_NOT_REGISTERED";
        }
        if (i == 1) {
            return "REGISTRATION_STATE_REGISTERING";
        }
        if (i == 2) {
            return "REGISTRATION_STATE_REGISTERED";
        }
        return Integer.toString(i);
    }

    static int getAccessType(int i) {
        Map<Integer, Integer> map = IMS_REG_TO_ACCESS_TYPE_MAP;
        if (!map.containsKey(Integer.valueOf(i))) {
            Log.w("RegistrationManager", "getAccessType - invalid regType returned: " + i);
            return -1;
        }
        return map.get(Integer.valueOf(i)).intValue();
    }

    public static class RegistrationCallback {
        private final RegistrationBinder mBinder = new RegistrationBinder(this);

        @Deprecated
        public void onRegistered(int i) {
        }

        public void onRegistering(int i) {
        }

        public void onSubscriberAssociatedUriChanged(Uri[] uriArr) {
        }

        public void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) {
        }

        public void onUnregistered(ImsReasonInfo imsReasonInfo) {
        }

        @SystemApi
        public void onUnregistered(ImsReasonInfo imsReasonInfo, SipDetails sipDetails) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class RegistrationBinder extends IImsRegistrationCallback.Stub {
            private Bundle mBundle = new Bundle();
            private Executor mExecutor;
            private final RegistrationCallback mLocalCallback;

            RegistrationBinder(RegistrationCallback registrationCallback) {
                this.mLocalCallback = registrationCallback;
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistered(final ImsRegistrationAttributes imsRegistrationAttributes) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onRegistered$0(imsRegistrationAttributes);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRegistered$0(ImsRegistrationAttributes imsRegistrationAttributes) {
                this.mLocalCallback.onRegistered(imsRegistrationAttributes);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onRegistering(final ImsRegistrationAttributes imsRegistrationAttributes) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onRegistering$1(imsRegistrationAttributes);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRegistering$1(ImsRegistrationAttributes imsRegistrationAttributes) {
                this.mLocalCallback.onRegistering(imsRegistrationAttributes);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregistered(final ImsReasonInfo imsReasonInfo, final int i, final int i2) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onDeregistered$2(imsReasonInfo, i, i2);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDeregistered$2(ImsReasonInfo imsReasonInfo, int i, int i2) {
                this.mLocalCallback.onUnregistered(imsReasonInfo, i, i2);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onDeregisteredWithDetails(final ImsReasonInfo imsReasonInfo, final int i, final int i2, final SipDetails sipDetails) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onDeregisteredWithDetails$3(imsReasonInfo, i, i2);
                        }
                    });
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onDeregisteredWithDetails$4(imsReasonInfo, sipDetails);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDeregisteredWithDetails$3(ImsReasonInfo imsReasonInfo, int i, int i2) {
                this.mLocalCallback.onUnregistered(imsReasonInfo, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDeregisteredWithDetails$4(ImsReasonInfo imsReasonInfo, SipDetails sipDetails) {
                this.mLocalCallback.onUnregistered(imsReasonInfo, sipDetails);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onTechnologyChangeFailed(final int i, final ImsReasonInfo imsReasonInfo) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onTechnologyChangeFailed$5(i, imsReasonInfo);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onTechnologyChangeFailed$5(int i, ImsReasonInfo imsReasonInfo) {
                this.mLocalCallback.onTechnologyChangeFailed(RegistrationManager.getAccessType(i), imsReasonInfo);
            }

            @Override // android.telephony.ims.aidl.IImsRegistrationCallback
            public void onSubscriberAssociatedUriChanged(final Uri[] uriArr) {
                if (this.mLocalCallback == null) {
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.RegistrationManager$RegistrationCallback$RegistrationBinder$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            RegistrationManager.RegistrationCallback.RegistrationBinder.this.lambda$onSubscriberAssociatedUriChanged$6(uriArr);
                        }
                    });
                } finally {
                    restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onSubscriberAssociatedUriChanged$6(Uri[] uriArr) {
                this.mLocalCallback.onSubscriberAssociatedUriChanged(uriArr);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }
        }

        public void onRegistered(ImsRegistrationAttributes imsRegistrationAttributes) {
            onRegistered(imsRegistrationAttributes.getTransportType());
        }

        public void onRegistering(ImsRegistrationAttributes imsRegistrationAttributes) {
            onRegistering(imsRegistrationAttributes.getTransportType());
        }

        public void onUnregistered(ImsReasonInfo imsReasonInfo, int i, int i2) {
            onUnregistered(imsReasonInfo);
        }

        public final IImsRegistrationCallback getBinder() {
            return this.mBinder;
        }

        public void setExecutor(Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }
}
