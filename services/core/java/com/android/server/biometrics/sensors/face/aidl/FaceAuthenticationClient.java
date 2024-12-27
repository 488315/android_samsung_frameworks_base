package com.android.server.biometrics.sensors.face.aidl;

import android.R;
import android.adaptiveauth.Flags;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.events.AuthenticationAcquiredInfo;
import android.hardware.biometrics.events.AuthenticationErrorInfo;
import android.hardware.biometrics.events.AuthenticationFailedInfo;
import android.hardware.biometrics.events.AuthenticationHelpInfo;
import android.hardware.biometrics.events.AuthenticationStartedInfo;
import android.hardware.biometrics.events.AuthenticationStoppedInfo;
import android.hardware.biometrics.events.AuthenticationSucceededInfo;
import android.hardware.biometrics.face.ISession;
import android.hardware.common.NativeHandle;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import android.view.Surface;

import com.android.server.biometrics.BiometricHandlerProvider;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.SemBiometricNotification;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricContextProvider;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.log.CallbackWithProbe;
import com.android.server.biometrics.sensors.AuthSessionCoordinator;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.AuthenticationStateListeners;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.ClientMonitorCallbackConverter;
import com.android.server.biometrics.sensors.ClientMonitorCompositeCallback;
import com.android.server.biometrics.sensors.LockoutConsumer;
import com.android.server.biometrics.sensors.LockoutTracker;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.face.SemFaceUtils;
import com.android.server.biometrics.sensors.face.UsageStats;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter.Cancellation;

import vendor.samsung.hardware.biometrics.face.ISehSession;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;

import java.util.ArrayList;
import java.util.function.Supplier;

public final class FaceAuthenticationClient extends AuthenticationClient
        implements LockoutConsumer {
    public final AuthSessionCoordinator mAuthSessionCoordinator;
    public final AuthenticationStateListeners mAuthenticationStateListeners;
    public SemBiometricNotification mBackgroundNotification;
    public AnonymousClass1 mBackgroundNotificationAction;
    public final int[] mBiometricPromptIgnoreList;
    public final int[] mBiometricPromptIgnoreListVendor;
    public final Bundle mBundle;
    public final boolean mCanIgnoreLockout;
    public ICancellationSignal mCancellationSignal;
    public final boolean mIsStrongBiometric;
    public final int[] mKeyguardIgnoreList;
    public final int[] mKeyguardIgnoreListVendor;
    public final int mPrivilegedAttr;
    public final AnonymousClass2 mSemCancelDaemonCallback;
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final UsageStats mUsageStats;

    public FaceAuthenticationClient(
            Context context,
            Supplier supplier,
            IBinder iBinder,
            long j,
            ClientMonitorCallbackConverter clientMonitorCallbackConverter,
            long j2,
            boolean z,
            FaceAuthenticateOptions faceAuthenticateOptions,
            int i,
            boolean z2,
            BiometricLogger biometricLogger,
            BiometricContext biometricContext,
            boolean z3,
            UsageStats usageStats,
            LockoutTracker lockoutTracker,
            boolean z4,
            SensorPrivacyManager sensorPrivacyManager,
            int i2,
            AuthenticationStateListeners authenticationStateListeners) {
        super(
                context,
                supplier,
                iBinder,
                clientMonitorCallbackConverter,
                j2,
                z,
                faceAuthenticateOptions,
                i,
                z2,
                biometricLogger,
                biometricContext,
                z3,
                null,
                lockoutTracker,
                z4,
                i2);
        this.mSemCancelDaemonCallback =
                new ClientMonitorCallback() { // from class:
                                              // com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.2
                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientFinished(
                            BaseClientMonitor baseClientMonitor, boolean z5) {
                        Slog.i(
                                "FaceAuthenticationClient",
                                "mSemCancelDaemonCallback.onClientFinished");
                        FaceAuthenticationClient faceAuthenticationClient =
                                FaceAuthenticationClient.this;
                        faceAuthenticationClient.dismissNotification();
                        if (faceAuthenticationClient.mCancellationSignal != null) {
                            faceAuthenticationClient
                                    .getServiceExtImpl()
                                    .daemonCancel(
                                            faceAuthenticationClient.mCancellationSignal, true);
                        }
                    }

                    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {}
                };
        setRequestId(j);
        this.mIsStrongBiometric = z3;
        this.mUsageStats = usageStats;
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mAuthSessionCoordinator =
                ((BiometricContextProvider) biometricContext).mAuthSessionCoordinator;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        Resources resources = context.getResources();
        this.mBiometricPromptIgnoreList = resources.getIntArray(R.array.networks_not_clear_data);
        this.mBiometricPromptIgnoreListVendor =
                resources.getIntArray(R.array.pause_wallpaper_render_when_state_change);
        this.mKeyguardIgnoreList = resources.getIntArray(R.array.non_removable_euicc_slots);
        this.mKeyguardIgnoreListVendor = resources.getIntArray(R.array.preloaded_color_state_lists);
        Bundle bundle = SemFaceUtils.mBundle;
        this.mBundle = bundle;
        PromptInfo promptInfo = null;
        SemFaceUtils.mBundle = null;
        if (bundle != null) {
            int i3 = bundle.getInt("sem_privileged_attr", 0);
            this.mPrivilegedAttr = i3;
            Slog.d("FaceAuthenticationClient", "mPrivilegedAttr = " + i3 + ", bundle = " + bundle);
        } else {
            this.mPrivilegedAttr = 0;
        }
        if (isBiometricPrompt()) {
            try {
                promptInfo =
                        IBiometricService.Stub.asInterface(ServiceManager.getService("biometric"))
                                .semGetPromptInfo(this.mCookie);
            } catch (RemoteException e) {
                Slog.w(
                        "FaceAuthenticationClient",
                        "getBiometricPrompt: failed to get prompt info" + e.getMessage());
            }
            if (promptInfo != null) {
                int semGetPrivilegedFlag = promptInfo.semGetPrivilegedFlag();
                if (semGetPrivilegedFlag != 0 && (semGetPrivilegedFlag & 4) != 0) {
                    this.mPrivilegedAttr |= 1;
                }
                promptInfo.semGetDisplayId();
            }
        }
        if (Utils.isKeyguard(this.mContext, this.mOwner) || (this.mPrivilegedAttr & 1) != 0) {
            this.mCanIgnoreLockout = true;
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    public final boolean canIgnoreLockout() {
        return this.mCanIgnoreLockout;
    }

    public final void dismissNotification() {
        Slog.i("FaceAuthenticationClient", "dismissNotification");
        AnonymousClass1 anonymousClass1 = this.mBackgroundNotificationAction;
        if (anonymousClass1 != null) {
            Utils.unregisterBroadcast(this.mContext, anonymousClass1);
            this.mBackgroundNotificationAction = null;
        }
        SemBiometricNotification semBiometricNotification = this.mBackgroundNotification;
        if (semBiometricNotification != null) {
            semBiometricNotification.cancelNotification();
            this.mBackgroundNotification = null;
        }
    }

    public SemFaceServiceExImpl getServiceExtImpl() {
        return SemFaceServiceExImpl.getInstance();
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public final void handleLifecycleAfterAuth(boolean z) {
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(BiometricSourceType.FACE, getRequestReason())
                        .build());
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void onAcquired(int i, int i2) {
        boolean shouldSendAcquiredMessage$1 = shouldSendAcquiredMessage$1(i, i2);
        if (shouldSendAcquiredMessage$1) {
            AuthenticationStateListeners authenticationStateListeners =
                    this.mAuthenticationStateListeners;
            BiometricSourceType biometricSourceType = BiometricSourceType.FACE;
            authenticationStateListeners.onAuthenticationAcquired(
                    new AuthenticationAcquiredInfo.Builder(
                                    biometricSourceType, getRequestReason(), i)
                            .build());
            String authHelpMessage = FaceManager.getAuthHelpMessage(this.mContext, i, i2);
            if (authHelpMessage != null) {
                this.mAuthenticationStateListeners.onAuthenticationHelp(
                        new AuthenticationHelpInfo.Builder(
                                        biometricSourceType,
                                        getRequestReason(),
                                        authHelpMessage,
                                        i == 22 ? i2 + 1000 : i)
                                .build());
            }
        }
        onAcquiredInternal(i, i2, shouldSendAcquiredMessage$1);
        LockoutTracker lockoutTracker = this.mLockoutTracker;
        if (lockoutTracker == null
                || lockoutTracker.getLockoutModeForUser(this.mTargetUserId) == 0) {
            PerformanceTracker.getInstanceForSensorId(this.mSensorId)
                    .incrementAcquireForUser(this.mTargetUserId, isCryptoOperation());
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient,
              // com.android.server.biometrics.sensors.AuthenticationConsumer
    public final void onAuthenticated(
            BiometricAuthenticator.Identifier identifier, boolean z, ArrayList arrayList) {
        super.onAuthenticated(identifier, z, arrayList);
        this.mState = 4;
        this.mUsageStats.addEvent(
                new UsageStats.AuthenticationEvent(
                        this.mStartTimeMs,
                        System.currentTimeMillis() - this.mStartTimeMs,
                        z,
                        0,
                        0,
                        this.mTargetUserId));
        if (Flags.reportBiometricAuthAttempts()) {
            if (z) {
                this.mAuthenticationStateListeners.onAuthenticationSucceeded(
                        new AuthenticationSucceededInfo.Builder(
                                        BiometricSourceType.FACE,
                                        getRequestReason(),
                                        this.mIsStrongBiometric,
                                        this.mTargetUserId)
                                .build());
            } else {
                this.mAuthenticationStateListeners.onAuthenticationFailed(
                        new AuthenticationFailedInfo.Builder(
                                        BiometricSourceType.FACE,
                                        getRequestReason(),
                                        this.mTargetUserId)
                                .build());
            }
        }
        dismissNotification();
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient,
              // com.android.server.biometrics.sensors.AcquisitionClient,
              // com.android.server.biometrics.sensors.ErrorConsumer
    public final void onError(int i, int i2) {
        dismissNotification();
        this.mUsageStats.addEvent(
                new UsageStats.AuthenticationEvent(
                        this.mStartTimeMs,
                        System.currentTimeMillis() - this.mStartTimeMs,
                        false,
                        i,
                        i2,
                        this.mTargetUserId));
        AuthenticationStateListeners authenticationStateListeners =
                this.mAuthenticationStateListeners;
        BiometricSourceType biometricSourceType = BiometricSourceType.FACE;
        authenticationStateListeners.onAuthenticationError(
                new AuthenticationErrorInfo.Builder(
                                biometricSourceType,
                                getRequestReason(),
                                FaceManager.getErrorString(this.mContext, i, i2),
                                i)
                        .build());
        super.onError(i, i2);
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(biometricSourceType, getRequestReason())
                        .build());
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public final void onLockoutPermanent() {
        this.mAuthSessionCoordinator.lockedOutFor(
                this.mTargetUserId, this.mSensorStrength, this.mSensorId, this.mRequestId);
        this.mLogger.logOnError(this.mContext, getOperationContext(), 9, 0, this.mTargetUserId);
        PerformanceTracker.getInstanceForSensorId(this.mSensorId)
                .incrementPermanentLockoutForUser(this.mTargetUserId);
        onError(9, 0);
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public final void onLockoutTimed(long j) {
        this.mAuthSessionCoordinator.lockOutTimed(
                this.mTargetUserId, this.mSensorStrength, this.mSensorId, j, this.mRequestId);
        this.mLogger.logOnError(this.mContext, getOperationContext(), 7, 0, this.mTargetUserId);
        PerformanceTracker.getInstanceForSensorId(this.mSensorId)
                .incrementTimedLockoutForUser(this.mTargetUserId);
        onError(7, 0);
    }

    public final void semDoAuthenticate() {
        Parcel obtain;
        Parcel obtain2;
        getServiceExtImpl().startOperation(2);
        Slog.w("FaceAuthenticationClient", "authenticate START");
        long currentTimeMillis = System.currentTimeMillis();
        if (getServiceExtImpl().mIsHIDL) {
            AidlSession aidlSession = (AidlSession) this.mLazyDaemon.get();
            SemFaceServiceExImpl serviceExtImpl = getServiceExtImpl();
            long j = this.mOperationId;
            serviceExtImpl.getClass();
            StringBuilder sb = new StringBuilder("authenticate BILG ");
            long currentTimeMillis2 = System.currentTimeMillis();
            serviceExtImpl.mAuthStartTime = currentTimeMillis2;
            sb.append(currentTimeMillis2);
            Slog.i("SemFace", sb.toString());
            if (SemBiometricFeature.FEATURE_JDM_HAL) {
                aidlSession.mSession.authenticate(j);
            } else if (serviceExtImpl.mIsAuthenticationExtOperation) {
                HidlToAidlSessionAdapter hidlToAidlSessionAdapter =
                        (HidlToAidlSessionAdapter) aidlSession.mSession;
                Slog.w(
                        "HidlToAidlSessionAdapter",
                        "sehAuthenticateForIssuance RESULT: "
                                + ((ISehBiometricsFace) hidlToAidlSessionAdapter.mSession.get())
                                        .sehAuthenticateForIssuance(
                                                SemFaceUtils.getSecurityMode(
                                                        serviceExtImpl.mContext),
                                                j,
                                                SemFaceUtils.getFidoRequestDataAsArrayList(),
                                                true,
                                                serviceExtImpl.mPreviewSurface != null));
                serviceExtImpl.mCancellationSignal = hidlToAidlSessionAdapter.new Cancellation();
            } else {
                HidlToAidlSessionAdapter hidlToAidlSessionAdapter2 =
                        (HidlToAidlSessionAdapter) aidlSession.mSession;
                Slog.w(
                        "HidlToAidlSessionAdapter",
                        "sehAuthenticate RESULT: "
                                + ((vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace)
                                                hidlToAidlSessionAdapter2.mSession.get())
                                        .sehAuthenticate(
                                                SemFaceUtils.getSecurityMode(
                                                        serviceExtImpl.mContext),
                                                j,
                                                SemFaceUtils.getFidoRequestDataAsArrayList()));
                serviceExtImpl.mCancellationSignal = hidlToAidlSessionAdapter2.new Cancellation();
            }
            this.mCancellationSignal = serviceExtImpl.mCancellationSignal;
        } else if (getServiceExtImpl().isUsingSehAPI()) {
            SemFaceServiceExImpl serviceExtImpl2 = getServiceExtImpl();
            long j2 = this.mOperationId;
            serviceExtImpl2.getClass();
            Slog.i("SemFace", "authenticate BILG ");
            ISession iSession = serviceExtImpl2.mISession;
            ICancellationSignal iCancellationSignal = null;
            if (iSession == null) {
                Slog.e("SemFace", "authenticate(): no ISession!");
            } else {
                ISehSession iSehSession = serviceExtImpl2.mISehSession;
                if (iSehSession == null) {
                    serviceExtImpl2.mCancellationSignal = iSession.authenticate(j2);
                } else if (!serviceExtImpl2.mIsAuthenticationExtOperation) {
                    int securityMode = SemFaceUtils.getSecurityMode(serviceExtImpl2.mContext);
                    byte[] bArr = SemFaceUtils.mFidoRequestData;
                    SemFaceUtils.mFidoRequestData = null;
                    if (bArr == null) {
                        bArr = new byte[0];
                    }
                    ISehSession.Stub.Proxy proxy = (ISehSession.Stub.Proxy) iSehSession;
                    obtain = Parcel.obtain(proxy.mRemote);
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                        obtain.writeLong(j2);
                        obtain.writeInt(securityMode);
                        obtain.writeByteArray(bArr);
                        if (!proxy.mRemote.transact(1, obtain, obtain2, 0)) {
                            throw new RemoteException(
                                    "Method authenticateExtension is unimplemented.");
                        }
                        obtain2.readException();
                        ICancellationSignal asInterface =
                                ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                        obtain2.recycle();
                        obtain.recycle();
                        serviceExtImpl2.mCancellationSignal = asInterface;
                    } finally {
                    }
                } else if (iSession.getInterfaceVersion() >= 4) {
                    ISehSession iSehSession2 = serviceExtImpl2.mISehSession;
                    int securityMode2 = SemFaceUtils.getSecurityMode(serviceExtImpl2.mContext);
                    byte[] bArr2 = SemFaceUtils.mFidoRequestData;
                    SemFaceUtils.mFidoRequestData = null;
                    if (bArr2 == null) {
                        bArr2 = new byte[0];
                    }
                    Surface surface = serviceExtImpl2.mPreviewSurface;
                    ISehSession.Stub.Proxy proxy2 = (ISehSession.Stub.Proxy) iSehSession2;
                    obtain = Parcel.obtain(proxy2.mRemote);
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                        obtain.writeLong(j2);
                        obtain.writeInt(securityMode2);
                        obtain.writeByteArray(bArr2);
                        obtain.writeBoolean(true);
                        obtain.writeTypedObject(surface, 0);
                        if (!proxy2.mRemote.transact(16, obtain, obtain2, 0)) {
                            throw new RemoteException(
                                    "Method authenticateForIssuanceEx is unimplemented.");
                        }
                        obtain2.readException();
                        ICancellationSignal asInterface2 =
                                ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                        obtain2.recycle();
                        obtain.recycle();
                        serviceExtImpl2.mCancellationSignal = asInterface2;
                    } finally {
                    }
                } else {
                    ISehSession iSehSession3 = serviceExtImpl2.mISehSession;
                    int securityMode3 = SemFaceUtils.getSecurityMode(serviceExtImpl2.mContext);
                    byte[] bArr3 = SemFaceUtils.mFidoRequestData;
                    SemFaceUtils.mFidoRequestData = null;
                    if (bArr3 == null) {
                        bArr3 = new byte[0];
                    }
                    NativeHandle nativeHandle = serviceExtImpl2.mHwPreviewHandle;
                    ISehSession.Stub.Proxy proxy3 = (ISehSession.Stub.Proxy) iSehSession3;
                    obtain = Parcel.obtain(proxy3.mRemote);
                    obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                        obtain.writeLong(j2);
                        obtain.writeInt(securityMode3);
                        obtain.writeByteArray(bArr3);
                        obtain.writeBoolean(true);
                        obtain.writeTypedObject(nativeHandle, 0);
                        if (!proxy3.mRemote.transact(13, obtain, obtain2, 0)) {
                            throw new RemoteException(
                                    "Method authenticateForIssuance is unimplemented.");
                        }
                        obtain2.readException();
                        ICancellationSignal asInterface3 =
                                ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                        obtain2.recycle();
                        obtain.recycle();
                        serviceExtImpl2.mCancellationSignal = asInterface3;
                    } finally {
                    }
                }
                iCancellationSignal = serviceExtImpl2.mCancellationSignal;
            }
            this.mCancellationSignal = iCancellationSignal;
            Bundle bundle = this.mBundle;
            if ((4 & (bundle != null ? bundle.getInt("sem_privileged_attr", 0) : 0)) != 0
                    && !isKeyguard()
                    && !Utils.isSystem(this.mContext, this.mOwner)) {
                if (this.mBackgroundNotification == null) {
                    this.mBackgroundNotification =
                            new SemBiometricNotification(this.mContext, this.mOwner, 8);
                }
                Intent intent =
                        new Intent("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION");
                intent.putExtra("package", this.mOwner);
                intent.putExtra("authenticator", 8);
                if (this.mBackgroundNotificationAction == null) {
                    this.mBackgroundNotificationAction =
                            new BroadcastReceiver() { // from class:
                                                      // com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.1
                                @Override // android.content.BroadcastReceiver
                                public final void onReceive(Context context, Intent intent2) {
                                    if ("com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION"
                                                    .equals(intent2.getAction())
                                            && FaceAuthenticationClient.this.mOwner.equals(
                                                    intent2.getStringExtra("package"))
                                            && intent2.getIntExtra("authenticator", 0) == 8
                                            && FaceAuthenticationClient.this.mCancellationSignal
                                                    != null) {
                                        Slog.i(
                                                "FaceAuthenticationClient",
                                                "Cancel authentication by Notification action");
                                        FaceAuthenticationClient.this
                                                .getServiceExtImpl()
                                                .daemonCancel(
                                                        FaceAuthenticationClient.this
                                                                .mCancellationSignal,
                                                        false);
                                    }
                                }
                            };
                    Utils.registerBroadcastAsUser(
                            this.mContext,
                            this.mBackgroundNotificationAction,
                            new IntentFilter(
                                    "com.samsung.android.server.biometrics.BIOMETRICS_NOTIFICATION"),
                            UserHandle.CURRENT,
                            BiometricHandlerProvider.sBiometricHandlerProvider.getFaceHandler());
                }
                this.mBackgroundNotification.postNotification(intent);
            }
        } else {
            AidlSession aidlSession2 = (AidlSession) this.mLazyDaemon.get();
            aidlSession2.getClass();
            this.mCancellationSignal = aidlSession2.mSession.authenticate(this.mOperationId);
        }
        Slog.w(
                "FaceAuthenticationClient",
                "authenticate FINISH ("
                        + (System.currentTimeMillis() - currentTimeMillis)
                        + "ms) RESULT: "
                        + this.mCancellationSignal);
    }

    public final boolean shouldSendAcquiredMessage$1(int i, int i2) {
        if (i == 22) {
            if (Utils.listContains(
                    i2,
                    isBiometricPrompt()
                            ? this.mBiometricPromptIgnoreListVendor
                            : this.mKeyguardIgnoreListVendor)) {
                return false;
            }
        } else {
            if (Utils.listContains(
                    i,
                    isBiometricPrompt()
                            ? this.mBiometricPromptIgnoreList
                            : this.mKeyguardIgnoreList)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient,
              // com.android.server.biometrics.sensors.BaseClientMonitor
    public final void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        this.mState = 1;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public final void startHalOperation() {
        this.mAuthenticationStateListeners.onAuthenticationStarted(
                new AuthenticationStartedInfo.Builder(BiometricSourceType.FACE, getRequestReason())
                        .build());
        try {
            SensorPrivacyManager sensorPrivacyManager = this.mSensorPrivacyManager;
            if (sensorPrivacyManager == null
                    || !sensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
                semDoAuthenticate();
            } else {
                onError(8, 100003);
                this.mCallback.onClientFinished(this, false);
            }
        } catch (RemoteException e) {
            Slog.e("FaceAuthenticationClient", "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void stopHalOperation() {
        this.mAuthenticationStateListeners.onAuthenticationStopped(
                new AuthenticationStoppedInfo.Builder(BiometricSourceType.FACE, getRequestReason())
                        .build());
        unsubscribeBiometricContext();
        dismissNotification();
        if (this.mCancellationSignal == null) {
            Slog.e("FaceAuthenticationClient", "Cancellation signal is null");
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            getServiceExtImpl().daemonCancel(this.mCancellationSignal, false);
        } catch (RemoteException e) {
            Slog.e("FaceAuthenticationClient", "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public final void vibrateError() {
        if (Utils.isKeyguard(this.mContext, this.mOwner)) {
            return;
        }
        if (isBiometricPrompt()) {
            Utils.semVibrate(this.mContext, 113);
        } else {
            Utils.semVibrate(this.mContext, 5);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public final ClientMonitorCallback wrapCallbackForStart(
            ClientMonitorCallback clientMonitorCallback) {
        return new ClientMonitorCompositeCallback(
                this.mSemCancelDaemonCallback,
                new CallbackWithProbe(this.mLogger.mALSProbe, true),
                clientMonitorCallback);
    }
}
