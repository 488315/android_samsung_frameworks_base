package com.android.systemui.deviceentry.data.repository;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.os.CancellationSignal;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.FaceAuthenticationLogger;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceEntryFaceAuthRepositoryImpl implements DeviceEntryFaceAuthRepository, Dumpable {
    public final StateFlowImpl _authenticationStatus;
    public final StateFlowImpl _detectionStatus;
    public final StateFlowImpl _isAuthRunning;
    public final StateFlowImpl _isAuthenticated;
    public final StateFlowImpl _isLockedOut;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final CoroutineScope applicationScope;
    public CancellationSignal authCancellationSignal;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final ReadonlyStateFlow canRunDetection;
    public final ReadonlyStateFlow canRunFaceAuth;
    public Job cancelNotReceivedHandlerJob;
    public final StateFlowImpl cancellationInProgress;
    public CancellationSignal detectCancellationSignal;
    public final DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1 detectionCallback;
    public final DisplayStateInteractor displayStateInteractor;
    public final Set faceAcquiredInfoIgnoreList;
    public final DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1 faceAuthCallback;
    public final FaceAuthenticationLogger faceAuthLogger;
    public final DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1 faceLockoutResetCallback;
    public final FaceManager faceManager;
    public Job halErrorRetryJob;
    public final StateFlowImpl isAuthenticated;
    public final Flow isBypassEnabled;
    public final boolean isDetectionSupported;
    public final StateFlowImpl isLockedOut;
    public final KeyguardBypassController keyguardBypassController;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardRepository keyguardRepository;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final StateFlowImpl pendingAuthenticateRequest;
    public int retryCount;
    public final SessionTracker sessionTracker;
    public final UiEventLogger uiEventsLogger;
    public final UserRepository userRepository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x00a1, code lost:
    
        if (r3 == null) goto L16;
     */
    /* JADX WARN: Type inference failed for: r12v7, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceLockoutResetCallback$1] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$faceAuthCallback$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$detectionCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DeviceEntryFaceAuthRepositoryImpl(android.content.Context r18, android.hardware.face.FaceManager r19, com.android.systemui.user.data.repository.UserRepository r20, com.android.systemui.statusbar.phone.KeyguardBypassController r21, kotlinx.coroutines.CoroutineScope r22, kotlinx.coroutines.CoroutineDispatcher r23, kotlinx.coroutines.CoroutineDispatcher r24, java.util.concurrent.Executor r25, com.android.systemui.log.SessionTracker r26, com.android.internal.logging.UiEventLogger r27, com.android.systemui.log.FaceAuthenticationLogger r28, com.android.systemui.keyguard.data.repository.BiometricSettingsRepository r29, com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository r30, com.android.systemui.keyguard.data.repository.KeyguardRepository r31, com.android.systemui.power.domain.interactor.PowerInteractor r32, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r33, com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor r34, com.android.systemui.log.table.TableLogBuffer r35, com.android.systemui.log.table.TableLogBuffer r36, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r37, com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor r38, com.android.systemui.dump.DumpManager r39) {
        /*
            Method dump skipped, instructions count: 647
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl.<init>(android.content.Context, android.hardware.face.FaceManager, com.android.systemui.user.data.repository.UserRepository, com.android.systemui.statusbar.phone.KeyguardBypassController, kotlinx.coroutines.CoroutineScope, kotlinx.coroutines.CoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher, java.util.concurrent.Executor, com.android.systemui.log.SessionTracker, com.android.internal.logging.UiEventLogger, com.android.systemui.log.FaceAuthenticationLogger, com.android.systemui.keyguard.data.repository.BiometricSettingsRepository, com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository, com.android.systemui.keyguard.data.repository.KeyguardRepository, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.keyguard.domain.interactor.KeyguardInteractor, com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor, com.android.systemui.log.table.TableLogBuffer, com.android.systemui.log.table.TableLogBuffer, com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor, com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor, com.android.systemui.dump.DumpManager):void");
    }

    public static final void access$clearPendingAuthRequest(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl, String str) {
        StateFlowImpl stateFlowImpl = deviceEntryFaceAuthRepositoryImpl.pendingAuthenticateRequest;
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) stateFlowImpl.getValue();
        FaceAuthUiEvent faceAuthUiEvent = authenticationRequest != null ? authenticationRequest.uiEvent : null;
        AuthenticationRequest authenticationRequest2 = (AuthenticationRequest) stateFlowImpl.getValue();
        deviceEntryFaceAuthRepositoryImpl.faceAuthLogger.clearingPendingAuthRequest(str, faceAuthUiEvent, authenticationRequest2 != null ? Boolean.valueOf(authenticationRequest2.fallbackToDetection) : null);
        stateFlowImpl.setValue(null);
    }

    public static final void access$onFaceAuthRequestCompleted(DeviceEntryFaceAuthRepositoryImpl deviceEntryFaceAuthRepositoryImpl) {
        Job job = deviceEntryFaceAuthRepositoryImpl.cancelNotReceivedHandlerJob;
        if (job != null) {
            job.cancel(null);
        }
        Boolean bool = Boolean.FALSE;
        deviceEntryFaceAuthRepositoryImpl._isAuthRunning.updateState(null, bool);
        deviceEntryFaceAuthRepositoryImpl.authCancellationSignal = null;
        deviceEntryFaceAuthRepositoryImpl.cancellationInProgress.updateState(null, bool);
    }

    public final void cancel() {
        CancellationSignal cancellationSignal = this.authCancellationSignal;
        if (cancellationSignal == null) {
            return;
        }
        cancellationSignal.cancel();
        Job job = this.cancelNotReceivedHandlerJob;
        if (job != null) {
            job.cancel(null);
        }
        this.cancelNotReceivedHandlerJob = BuildersKt.launch$default(this.applicationScope, null, null, new DeviceEntryFaceAuthRepositoryImpl$cancel$1(this, null), 3);
        this.cancellationInProgress.updateState(null, Boolean.TRUE);
        this._isAuthRunning.updateState(null, Boolean.FALSE);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        List sensorPropertiesInternal;
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        printWriter.println("DeviceEntryFaceAuthRepositoryImpl state:");
        printWriter.println("  cancellationInProgress: " + this.cancellationInProgress);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _isLockedOut.value: ", this._isLockedOut.getValue(), printWriter);
        printWriter.println("  _isAuthRunning.value: " + this._isAuthRunning.getValue());
        printWriter.println("  isDetectionSupported: " + this.isDetectionSupported);
        printWriter.println("  FaceManager state:");
        printWriter.println("    faceManager: " + this.faceManager);
        FaceManager faceManager = this.faceManager;
        Boolean bool = null;
        printWriter.println("    sensorPropertiesInternal: " + (faceManager != null ? faceManager.getSensorPropertiesInternal() : null));
        FaceManager faceManager2 = this.faceManager;
        if (faceManager2 != null && (sensorPropertiesInternal = faceManager2.getSensorPropertiesInternal()) != null && (faceSensorPropertiesInternal = (FaceSensorPropertiesInternal) CollectionsKt___CollectionsKt.firstOrNull(sensorPropertiesInternal)) != null) {
            bool = Boolean.valueOf(faceSensorPropertiesInternal.supportsFaceDetection);
        }
        printWriter.println("    supportsFaceDetection: " + bool);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _pendingAuthenticateRequest: ", this.pendingAuthenticateRequest.getValue(), printWriter);
        printWriter.println("  authCancellationSignal: " + this.authCancellationSignal);
        printWriter.println("  detectCancellationSignal: " + this.detectCancellationSignal);
        printWriter.println("  faceAcquiredInfoIgnoreList: " + this.faceAcquiredInfoIgnoreList);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _authenticationStatus: ", this._authenticationStatus.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  _detectionStatus: ", this._detectionStatus.getValue(), printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  currentUserId: ", ((UserRepositoryImpl) this.userRepository).getSelectedUserInfo().id, printWriter);
        printWriter.println("  keyguardSessionId: " + this.sessionTracker.getSessionId(1));
        KeyguardBypassController keyguardBypassController = this.keyguardBypassController;
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  lockscreenBypassEnabled: ", keyguardBypassController != null ? keyguardBypassController.getBypassEnabled() : false, printWriter);
    }

    public final Pair[] gatingConditionsForAuthAndDetect() {
        DisplayRepositoryImpl$special$$inlined$map$9 displayRepositoryImpl$special$$inlined$map$9 = ((DisplayStateInteractorImpl) this.displayStateInteractor).isDefaultDisplayOff;
        KeyguardState.Companion companion = KeyguardState.Companion;
        DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1 deviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1 = new DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1(companion);
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.keyguardTransitionInteractor;
        Pair pair = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayRepositoryImpl$special$$inlined$map$9, FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, deviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$1)), new DeviceEntryFaceAuthRepositoryKt$and$1(null))), "displayIsNotOffWhileFullyTransitionedToAwake");
        BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = (BiometricSettingsRepositoryImpl) this.biometricSettingsRepository;
        Pair pair2 = new Pair(biometricSettingsRepositoryImpl.isFaceAuthEnrolledAndEnabled, "isFaceAuthEnrolledAndEnabled");
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.keyguardRepository;
        Pair pair3 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardRepositoryImpl.isKeyguardGoingAway), "keyguardNotGoingAway");
        Pair pair4 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(keyguardTransitionInteractor.isInTransitionToStateWhere(new DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$2(companion))), "deviceNotTransitioningToAsleepState");
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        Pair pair5 = new Pair(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1((Flow) keyguardInteractor.isSecureCameraActive$delegate.getValue()), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.alternateBouncerInteractor.isVisible, keyguardInteractor.primaryBouncerShowing, new DeviceEntryFaceAuthRepositoryKt$or$1(null)), new DeviceEntryFaceAuthRepositoryKt$or$1(null)), "secureCameraNotActiveOrAnyBouncerIsShowing");
        Pair pair6 = new Pair(biometricSettingsRepositoryImpl.isFaceAuthSupportedInCurrentPosture, "isFaceAuthSupportedInCurrentPosture");
        Pair pair7 = new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(biometricSettingsRepositoryImpl.isCurrentUserInLockdown), "userHasNotLockedDownDevice");
        Pair pair8 = new Pair(keyguardRepositoryImpl.isKeyguardShowing, "isKeyguardShowing");
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) this.userRepository).selectedUser;
        return new Pair[]{pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, new Pair(new DeviceEntryFaceAuthRepositoryKt$isFalse$$inlined$map$1(new Flow() { // from class: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.user.data.model.SelectedUserModel r5 = (com.android.systemui.user.data.model.SelectedUserModel) r5
                        com.android.systemui.user.data.model.SelectionStatus r5 = r5.selectionStatus
                        com.android.systemui.user.data.model.SelectionStatus r6 = com.android.systemui.user.data.model.SelectionStatus.SELECTION_IN_PROGRESS
                        if (r5 != r6) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$gatingConditionsForAuthAndDetect$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "userSwitchingInProgress")};
    }

    public /* synthetic */ DeviceEntryFaceAuthRepositoryImpl(Context context, FaceManager faceManager, UserRepository userRepository, KeyguardBypassController keyguardBypassController, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, Executor executor, SessionTracker sessionTracker, UiEventLogger uiEventLogger, FaceAuthenticationLogger faceAuthenticationLogger, BiometricSettingsRepository biometricSettingsRepository, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, KeyguardRepository keyguardRepository, PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor, AlternateBouncerInteractor alternateBouncerInteractor, TableLogBuffer tableLogBuffer, TableLogBuffer tableLogBuffer2, KeyguardTransitionInteractor keyguardTransitionInteractor, DisplayStateInteractor displayStateInteractor, DumpManager dumpManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : faceManager, userRepository, (i & 8) != 0 ? null : keyguardBypassController, coroutineScope, coroutineDispatcher, coroutineDispatcher2, executor, sessionTracker, uiEventLogger, faceAuthenticationLogger, biometricSettingsRepository, deviceEntryFingerprintAuthRepository, keyguardRepository, powerInteractor, keyguardInteractor, alternateBouncerInteractor, tableLogBuffer, tableLogBuffer2, keyguardTransitionInteractor, displayStateInteractor, dumpManager);
    }
}
