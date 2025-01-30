package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainer;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.SecurityUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.data.BouncerViewDelegate;
import com.android.systemui.keyguard.data.BouncerViewImpl;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PrimaryBouncerInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 bouncerExpansion;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final FalsingCollector falsingCollector;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 isBackButtonEnabled;
    public final ReadonlyStateFlow isInflated;
    public final PrimaryBouncerInteractor$special$$inlined$map$2 isInteractable;
    public final ReadonlyStateFlow isShowing;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 keyguardAuthenticated;
    public final ReadonlyStateFlow keyguardPosition;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public final Handler mainHandler;
    public final ReadonlyStateFlow panelExpansionAmount;
    public boolean pendingBouncerViewDelegate;
    public final PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor;
    public final ReadonlyStateFlow primaryBouncerUpdating;
    public final BouncerView primaryBouncerView;
    public final KeyguardBouncerRepository repository;
    public final PrimaryBouncerInteractor$special$$inlined$filter$2 resourceUpdateRequests;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 showMessage;
    public final PrimaryBouncerInteractor$showRunnable$1 showRunnable;
    public final ReadonlyStateFlow sideFpsShowing;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 startingDisappearAnimation;
    public final PrimaryBouncerInteractor$special$$inlined$map$1 startingToHide;
    public final TrustRepository trustRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$showRunnable$1] */
    public PrimaryBouncerInteractor(KeyguardBouncerRepository keyguardBouncerRepository, BouncerView bouncerView, Handler handler, KeyguardStateController keyguardStateController, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, FalsingCollector falsingCollector, DismissCallbackRegistry dismissCallbackRegistry, Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, TrustRepository trustRepository, FeatureFlags featureFlags, CoroutineScope coroutineScope) {
        this.repository = keyguardBouncerRepository;
        this.primaryBouncerView = bouncerView;
        this.mainHandler = handler;
        this.keyguardStateController = keyguardStateController;
        this.keyguardSecurityModel = keyguardSecurityModel;
        this.primaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
        this.falsingCollector = falsingCollector;
        this.dismissCallbackRegistry = dismissCallbackRegistry;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.trustRepository = trustRepository;
        context.getResources().getInteger(R.integer.primary_bouncer_passive_auth_delay);
        this.showRunnable = new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$showRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                BouncerViewDelegate delegate = ((BouncerViewImpl) PrimaryBouncerInteractor.this.primaryBouncerView).getDelegate();
                if (delegate != null) {
                    ((KeyguardSecSecurityContainer) ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.mView).setVisibility(0);
                }
                ((KeyguardBouncerRepositoryImpl) PrimaryBouncerInteractor.this.repository)._primaryBouncerShow.setValue(Boolean.TRUE);
                StateFlowImpl stateFlowImpl = ((KeyguardBouncerRepositoryImpl) PrimaryBouncerInteractor.this.repository)._primaryBouncerShowingSoon;
                Boolean bool = Boolean.FALSE;
                stateFlowImpl.setValue(bool);
                ((KeyguardBouncerRepositoryImpl) PrimaryBouncerInteractor.this.repository)._primaryBouncerUpdating.setValue(bool);
                Iterator it = PrimaryBouncerInteractor.this.primaryBouncerCallbackInteractor.expansionCallbacks.iterator();
                while (it.hasNext()) {
                    ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) it.next()).onVisibilityChanged(true);
                }
            }
        };
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) keyguardBouncerRepository;
        this.keyguardAuthenticated = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.keyguardAuthenticated);
        this.isShowing = keyguardBouncerRepositoryImpl.primaryBouncerShow;
        final ReadonlyStateFlow readonlyStateFlow = keyguardBouncerRepositoryImpl.primaryBouncerStartingToHide;
        this.startingToHide = new PrimaryBouncerInteractor$special$$inlined$map$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2 */
            public final class C17272 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2", m277f = "PrimaryBouncerInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17272.this.emit(null, this);
                    }
                }

                public C17272(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((Boolean) obj).booleanValue()) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17272(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isBackButtonEnabled = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.isBackButtonEnabled);
        this.showMessage = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.showMessage);
        this.startingDisappearAnimation = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.primaryBouncerStartingDisappearAnimation);
        this.resourceUpdateRequests = new PrimaryBouncerInteractor$special$$inlined$filter$2(keyguardBouncerRepositoryImpl.resourceUpdateRequests);
        this.keyguardPosition = keyguardBouncerRepositoryImpl.keyguardPosition;
        ReadonlyStateFlow readonlyStateFlow2 = keyguardBouncerRepositoryImpl.panelExpansionAmount;
        this.panelExpansionAmount = readonlyStateFlow2;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, keyguardBouncerRepositoryImpl.primaryBouncerShow, new PrimaryBouncerInteractor$bouncerExpansion$1(null));
        this.bouncerExpansion = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.isInteractable = new PrimaryBouncerInteractor$special$$inlined$map$2(flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
        this.sideFpsShowing = keyguardBouncerRepositoryImpl.sideFpsShowing;
        this.isInflated = keyguardBouncerRepositoryImpl.primaryBouncerInflate;
        this.primaryBouncerUpdating = keyguardBouncerRepositoryImpl.primaryBouncerUpdating;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                PrimaryBouncerInteractor.this.updateSideFpsVisibility();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                PrimaryBouncerInteractor.this.updateSideFpsVisibility();
            }
        };
        this.keyguardUpdateMonitorCallback = keyguardUpdateMonitorCallback;
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        Flags flags = Flags.INSTANCE;
    }

    public final void hide() {
        Trace.beginSection("KeyguardBouncer#hide");
        if (isFullyShowing()) {
            SysUiStatsLog.write(63, 1);
            this.dismissCallbackRegistry.notifyDismissCancelled();
        }
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        keyguardBouncerRepositoryImpl._primaryBouncerDisappearAnimation.setValue(null);
        FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) this.falsingCollector;
        if (falsingCollectorImpl.mSessionStarted) {
            falsingCollectorImpl.mProximitySensor.register(falsingCollectorImpl.mSensorEventListener);
        }
        ((KeyguardStateControllerImpl) this.keyguardStateController).notifyPrimaryBouncerShowing(false);
        boolean z = DejankUtils.STRICT_MODE_ENABLED;
        Assert.isMainThread();
        ArrayList arrayList = DejankUtils.sPendingRunnables;
        PrimaryBouncerInteractor$showRunnable$1 primaryBouncerInteractor$showRunnable$1 = this.showRunnable;
        arrayList.remove(primaryBouncerInteractor$showRunnable$1);
        DejankUtils.sHandler.removeCallbacks(primaryBouncerInteractor$showRunnable$1);
        this.mainHandler.removeCallbacks(primaryBouncerInteractor$showRunnable$1);
        StateFlowImpl stateFlowImpl = keyguardBouncerRepositoryImpl._primaryBouncerUpdating;
        Boolean bool = Boolean.FALSE;
        stateFlowImpl.setValue(bool);
        keyguardBouncerRepositoryImpl._primaryBouncerShowingSoon.setValue(bool);
        keyguardBouncerRepositoryImpl._primaryBouncerShow.setValue(bool);
        keyguardBouncerRepositoryImpl._panelExpansionAmount.setValue(Float.valueOf(1.0f));
        Iterator it = this.primaryBouncerCallbackInteractor.expansionCallbacks.iterator();
        while (it.hasNext()) {
            ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) it.next()).onVisibilityChanged(false);
        }
        Trace.endSection();
    }

    public final boolean isAnimatingAway() {
        return ((KeyguardBouncerRepositoryImpl) this.repository).primaryBouncerStartingDisappearAnimation.getValue() != null;
    }

    public final boolean isBouncerShowing() {
        return ((Boolean) ((KeyguardBouncerRepositoryImpl) this.repository).primaryBouncerShow.getValue()).booleanValue();
    }

    public final boolean isFullyShowing() {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        if (((Boolean) keyguardBouncerRepositoryImpl.primaryBouncerShowingSoon.getValue()).booleanValue() || isBouncerShowing()) {
            return ((((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.getValue()).floatValue() > 0.0f ? 1 : (((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.getValue()).floatValue() == 0.0f ? 0 : -1)) == 0) && keyguardBouncerRepositoryImpl.primaryBouncerStartingDisappearAnimation.getValue() == null;
        }
        return false;
    }

    public final boolean isInTransit() {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        if (((Boolean) keyguardBouncerRepositoryImpl.primaryBouncerShowingSoon.getValue()).booleanValue()) {
            return true;
        }
        if (!(((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.getValue()).floatValue() == 1.0f)) {
            if (!(((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.getValue()).floatValue() == 0.0f)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isSwipeBouncer() {
        return this.keyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()) == KeyguardSecurityModel.SecurityMode.Swipe;
    }

    public final void setBackButtonEnabled(boolean z) {
        ((KeyguardBouncerRepositoryImpl) this.repository)._isBackButtonEnabled.setValue(Boolean.valueOf(z));
    }

    public final void setDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.primaryBouncerView).getDelegate();
        if (delegate != null) {
            ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.setOnDismissAction(onDismissAction, runnable);
        }
    }

    public final void setPanelExpansion(float f) {
        boolean z = !(0.0f == f);
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        if (keyguardBouncerRepositoryImpl.primaryBouncerStartingDisappearAnimation.getValue() == null) {
            keyguardBouncerRepositoryImpl._panelExpansionAmount.setValue(Float.valueOf(f));
        }
        boolean z2 = f == 1.0f;
        PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor = this.primaryBouncerCallbackInteractor;
        if (z2) {
            hide();
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$setPanelExpansion$1
                @Override // java.lang.Runnable
                public final void run() {
                    Iterator it = PrimaryBouncerInteractor.this.primaryBouncerCallbackInteractor.resetCallbacks.iterator();
                    if (it.hasNext()) {
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
                        throw null;
                    }
                }
            });
            Iterator it = primaryBouncerCallbackInteractor.expansionCallbacks.iterator();
            while (it.hasNext()) {
                ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) it.next()).onFullyHidden();
            }
        } else {
            if (!(f == 0.0f)) {
                Iterator it2 = primaryBouncerCallbackInteractor.expansionCallbacks.iterator();
                while (it2.hasNext()) {
                    ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) it2.next()).onStartingToHide();
                }
                keyguardBouncerRepositoryImpl._primaryBouncerStartingToHide.setValue(Boolean.TRUE);
            }
        }
        if (z) {
            Iterator it3 = primaryBouncerCallbackInteractor.expansionCallbacks.iterator();
            while (it3.hasNext()) {
                ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) it3.next()).onExpansionChanged(f);
            }
        }
    }

    public final void show(boolean z) {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        keyguardBouncerRepositoryImpl._keyguardAuthenticated.setValue(null);
        StateFlowImpl stateFlowImpl = keyguardBouncerRepositoryImpl._primaryBouncerStartingToHide;
        Boolean bool = Boolean.FALSE;
        stateFlowImpl.setValue(bool);
        boolean isBouncerShowing = isBouncerShowing();
        KeyguardSecurityModel keyguardSecurityModel = this.keyguardSecurityModel;
        boolean z2 = false;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        boolean z3 = (isBouncerShowing || ((Boolean) keyguardBouncerRepositoryImpl.primaryBouncerShowingSoon.getValue()).booleanValue()) && (SecurityUtils.checkFullscreenBouncer(keyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser())) || keyguardUpdateMonitor.isDismissActionExist());
        Trace.beginSection("KeyguardBouncer#show");
        keyguardBouncerRepositoryImpl._primaryBouncerScrimmed.setValue(Boolean.valueOf(z));
        if (z) {
            setPanelExpansion(0.0f);
        }
        if (SecurityUtils.checkFullscreenBouncer(keyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()))) {
            keyguardBouncerRepositoryImpl._primaryBouncerInflate.setValue(Boolean.TRUE);
        }
        if (z3) {
            keyguardBouncerRepositoryImpl._primaryBouncerUpdating.setValue(Boolean.TRUE);
            keyguardBouncerRepositoryImpl._primaryBouncerShow.setValue(bool);
        }
        BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) this.primaryBouncerView;
        if (bouncerViewImpl.getDelegate() == null) {
            Log.d("PrimaryBouncerInteractor", "BouncerViewDelegate is null");
            this.pendingBouncerViewDelegate = true;
        }
        BouncerViewDelegate delegate = bouncerViewImpl.getDelegate();
        if (delegate != null) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController;
            if (keyguardSecSecurityContainerController.mKeyguardSecurityCallback.dismiss(false, currentUser, false, keyguardSecSecurityContainerController.mCurrentSecurityMode)) {
                z2 = true;
            }
        }
        if (z2) {
            return;
        }
        if (LsRune.SECURITY_SIM_PERM_DISABLED && keyguardUpdateMonitor.isIccBlockedPermanently()) {
            if (!SecurityUtils.checkFullscreenBouncer(keyguardSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()))) {
                Log.d("PrimaryBouncerInteractor", "do not show by permanent state.");
                return;
            }
            Log.d("PrimaryBouncerInteractor", "Permanent state but it have to show bouncer");
        }
        keyguardBouncerRepositoryImpl._primaryBouncerShowingSoon.setValue(Boolean.TRUE);
        DejankUtils.postAfterTraversal(this.showRunnable);
        ((KeyguardStateControllerImpl) this.keyguardStateController).notifyPrimaryBouncerShowing(true);
        Iterator it = this.primaryBouncerCallbackInteractor.expansionCallbacks.iterator();
        while (it.hasNext()) {
            ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) it.next()).onStartingToShow();
        }
        Trace.endSection();
    }

    public final void updateSideFpsVisibility() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        boolean isFingerprintDetectionRunning = keyguardUpdateMonitor.isFingerprintDetectionRunning();
        boolean isUnlockingWithFingerprintAllowed = keyguardUpdateMonitor.isUnlockingWithFingerprintAllowed();
        isBouncerShowing();
        boolean isBouncerShowing = isBouncerShowing();
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("sideFpsToShow=false\nisBouncerShowing=", isBouncerShowing, "\nconfigEnabled=false\nfpsDetectionRunning=", isFingerprintDetectionRunning, "\nisUnlockingWithFpAllowed="), isUnlockingWithFingerprintAllowed, "\nisAnimatingAway=", isAnimatingAway(), "PrimaryBouncerInteractor");
        ((KeyguardBouncerRepositoryImpl) this.repository)._sideFpsShowing.setValue(Boolean.FALSE);
    }

    public final boolean willDismissWithAction() {
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.primaryBouncerView).getDelegate();
        if (delegate == null) {
            return false;
        }
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController;
        return keyguardSecSecurityContainerController.mDismissAction != null || keyguardSecSecurityContainerController.mCancelAction != null;
    }

    public final boolean willRunDismissFromKeyguard() {
        BouncerViewDelegate delegate = ((BouncerViewImpl) this.primaryBouncerView).getDelegate();
        return delegate != null && ((KeyguardBouncerViewBinder$bind$delegate$1) delegate).$securityContainerController.mWillRunDismissFromKeyguard;
    }
}
