package com.android.systemui.bouncer.domain.interactor;

import android.content.Context;
import android.os.Handler;
import android.os.Trace;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.bouncer.ui.BouncerView;
import com.android.systemui.bouncer.ui.BouncerViewImpl;
import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.data.repository.TrustRepository;
import com.android.systemui.keyguard.data.repository.TrustRepositoryImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PrimaryBouncerInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 bouncerExpansion;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final FalsingCollector falsingCollector;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 isBackButtonEnabled;
    public final ReadonlyStateFlow isInflated;
    public final PrimaryBouncerInteractor$special$$inlined$map$3 isInteractable;
    public final ReadonlyStateFlow isShowing;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 keyguardAuthenticatedBiometrics;
    public final PrimaryBouncerInteractor$special$$inlined$map$1 keyguardAuthenticatedBiometricsHandled;
    public final ReadonlySharedFlow keyguardAuthenticatedPrimaryAuth;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 keyguardPosition;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ReadonlyStateFlow lastShownSecurityMode;
    public final Handler mainHandler;
    public final ReadonlyStateFlow panelExpansionAmount;
    public boolean pendingBouncerViewDelegate;
    public final PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor;
    public final ReadonlyStateFlow primaryBouncerUpdating;
    public final BouncerView primaryBouncerView;
    public final KeyguardBouncerRepository repository;
    public final ReadonlySharedFlow reset;
    public final PrimaryBouncerInteractor$special$$inlined$filter$3 resourceUpdateRequests;
    public final SelectedUserInteractor selectedUserInteractor;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 showMessage;
    public final PrimaryBouncerInteractor$showRunnable$1 showRunnable;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 startingDisappearAnimation;
    public final PrimaryBouncerInteractor$special$$inlined$map$2 startingToHide;
    public final TrustRepository trustRepository;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 userRequestedBouncerWhenAlreadyAuthenticated;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PrimaryBouncerInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final PrimaryBouncerInteractor primaryBouncerInteractor = PrimaryBouncerInteractor.this;
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = ((TrustRepositoryImpl) primaryBouncerInteractor.trustRepository).isCurrentUserActiveUnlockRunning;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).booleanValue();
                        int i2 = PrimaryBouncerInteractor.$r8$clinit;
                        PrimaryBouncerInteractor.this.getClass();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$showRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1] */
    public PrimaryBouncerInteractor(KeyguardBouncerRepository keyguardBouncerRepository, BouncerView bouncerView, Handler handler, KeyguardStateController keyguardStateController, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, FalsingCollector falsingCollector, DismissCallbackRegistry dismissCallbackRegistry, Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, TrustRepository trustRepository, CoroutineScope coroutineScope, SelectedUserInteractor selectedUserInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor) {
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
        this.applicationScope = coroutineScope;
        this.selectedUserInteractor = selectedUserInteractor;
        context.getResources().getInteger(R.integer.primary_bouncer_passive_auth_delay);
        this.showRunnable = new Runnable() { // from class: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$showRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) PrimaryBouncerInteractor.this.primaryBouncerView).getDelegate();
                int i = 0;
                if (delegate != null) {
                    delegate.$securityContainerController.setSecurityContainerVisibility(0);
                }
                KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) PrimaryBouncerInteractor.this.repository;
                keyguardBouncerRepositoryImpl._primaryBouncerShow.updateState(null, Boolean.TRUE);
                KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl2 = (KeyguardBouncerRepositoryImpl) PrimaryBouncerInteractor.this.repository;
                Boolean bool = Boolean.FALSE;
                keyguardBouncerRepositoryImpl2._primaryBouncerShowingSoon.updateState(null, bool);
                ((KeyguardBouncerRepositoryImpl) PrimaryBouncerInteractor.this.repository)._primaryBouncerUpdating.updateState(null, bool);
                ArrayList arrayList = PrimaryBouncerInteractor.this.primaryBouncerCallbackInteractor.expansionCallbacks;
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) obj).onVisibilityChanged(true);
                }
            }
        };
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) keyguardBouncerRepository;
        this.keyguardAuthenticatedPrimaryAuth = keyguardBouncerRepositoryImpl.keyguardAuthenticatedPrimaryAuth;
        this.keyguardAuthenticatedBiometrics = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.keyguardAuthenticatedBiometrics);
        final ReadonlyStateFlow readonlyStateFlow = keyguardBouncerRepositoryImpl.keyguardAuthenticatedBiometrics;
        final Flow flow = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L42
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        if (r6 != 0) goto L42
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L42
                        return r1
                    L42:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.keyguardAuthenticatedBiometricsHandled = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.userRequestedBouncerWhenAlreadyAuthenticated = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.userRequestedBouncerWhenAlreadyAuthenticated);
        this.isShowing = keyguardBouncerRepositoryImpl.primaryBouncerShow;
        final ReadonlyStateFlow readonlyStateFlow2 = keyguardBouncerRepositoryImpl.primaryBouncerStartingToHide;
        this.startingToHide = new PrimaryBouncerInteractor$special$$inlined$map$2(new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isBackButtonEnabled = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.isBackButtonEnabled);
        this.showMessage = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.showMessage);
        this.startingDisappearAnimation = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.primaryBouncerStartingDisappearAnimation);
        this.resourceUpdateRequests = new PrimaryBouncerInteractor$special$$inlined$filter$3(keyguardBouncerRepositoryImpl.resourceUpdateRequests);
        this.keyguardPosition = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(keyguardBouncerRepositoryImpl.keyguardPosition);
        ReadonlyStateFlow readonlyStateFlow3 = keyguardBouncerRepositoryImpl.panelExpansionAmount;
        this.panelExpansionAmount = readonlyStateFlow3;
        this.lastShownSecurityMode = keyguardBouncerRepositoryImpl.lastShownSecurityMode;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow3, keyguardBouncerRepositoryImpl.primaryBouncerShow, new PrimaryBouncerInteractor$bouncerExpansion$1(null));
        this.bouncerExpansion = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.isInteractable = new PrimaryBouncerInteractor$special$$inlined$map$3(flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
        this.isInflated = keyguardBouncerRepositoryImpl.primaryBouncerInflate;
        this.primaryBouncerUpdating = keyguardBouncerRepositoryImpl.primaryBouncerUpdating;
        this.reset = keyguardBouncerRepositoryImpl.reset;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    public final void hide() {
        Trace.beginSection("KeyguardBouncer#hide");
        if (isFullyShowing()) {
            SysUiStatsLog.write(63, 1);
            this.dismissCallbackRegistry.notifyDismissCancelled();
        }
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        keyguardBouncerRepositoryImpl.primaryBouncerStartingDisappearAnimation.tryEmit(null);
        this.falsingCollector.onBouncerHidden();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if (keyguardStateControllerImpl.mPrimaryBouncerShowing) {
            keyguardStateControllerImpl.mPrimaryBouncerShowing = false;
            keyguardStateControllerImpl.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(1));
        }
        boolean z = DejankUtils.STRICT_MODE_ENABLED;
        Assert.isMainThread();
        ArrayList arrayList = DejankUtils.sPendingRunnables;
        PrimaryBouncerInteractor$showRunnable$1 primaryBouncerInteractor$showRunnable$1 = this.showRunnable;
        arrayList.remove(primaryBouncerInteractor$showRunnable$1);
        DejankUtils.sHandler.removeCallbacks(primaryBouncerInteractor$showRunnable$1);
        this.mainHandler.removeCallbacks(primaryBouncerInteractor$showRunnable$1);
        Boolean bool = Boolean.FALSE;
        keyguardBouncerRepositoryImpl._primaryBouncerUpdating.updateState(null, bool);
        keyguardBouncerRepositoryImpl._primaryBouncerShowingSoon.updateState(null, bool);
        keyguardBouncerRepositoryImpl._primaryBouncerShow.updateState(null, bool);
        keyguardBouncerRepositoryImpl._panelExpansionAmount.updateState(null, Float.valueOf(1.0f));
        ArrayList arrayList2 = this.primaryBouncerCallbackInteractor.expansionCallbacks;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) obj).onVisibilityChanged(false);
        }
        Trace.endSection();
    }

    public final boolean isBouncerShowing() {
        return ((Boolean) this.isShowing.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isFullyShowing() {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        return (((Boolean) keyguardBouncerRepositoryImpl.primaryBouncerShowingSoon.$$delegate_0.getValue()).booleanValue() || isBouncerShowing()) && ((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.$$delegate_0.getValue()).floatValue() == 0.0f && !keyguardBouncerRepositoryImpl.isPrimaryBouncerStartingDisappearAnimation();
    }

    public final boolean isInTransit() {
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        if (((Boolean) keyguardBouncerRepositoryImpl.primaryBouncerShowingSoon.$$delegate_0.getValue()).booleanValue()) {
            return true;
        }
        return (((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.$$delegate_0.getValue()).floatValue() == 1.0f || ((Number) keyguardBouncerRepositoryImpl.panelExpansionAmount.$$delegate_0.getValue()).floatValue() == 0.0f) ? false : true;
    }

    public final boolean isScrimmed() {
        return ((Boolean) ((KeyguardBouncerRepositoryImpl) this.repository).primaryBouncerScrimmed.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isSwipeBouncer() {
        return this.keyguardSecurityModel.getSecurityMode(this.selectedUserInteractor.getSelectedUserId()) == KeyguardSecurityModel.SecurityMode.Swipe;
    }

    public final void notifyKeyguardAuthenticatedPrimaryAuth(int i) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new PrimaryBouncerInteractor$notifyKeyguardAuthenticatedPrimaryAuth$1(this, i, null), 7);
    }

    public final void notifyUserRequestedBouncerWhenAlreadyAuthenticated(int i) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new PrimaryBouncerInteractor$notifyUserRequestedBouncerWhenAlreadyAuthenticated$1(this, i, null), 7);
    }

    public final void setBackButtonEnabled(boolean z) {
        ((KeyguardBouncerRepositoryImpl) this.repository)._isBackButtonEnabled.updateState(null, Boolean.valueOf(z));
    }

    public final void setDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        ActivityStarter.OnDismissAction onDismissAction2;
        Runnable runnable2;
        if (onDismissAction == null || runnable == null) {
            onDismissAction2 = onDismissAction;
            runnable2 = runnable;
        } else {
            onDismissAction2 = onDismissAction;
            runnable2 = runnable;
            new BouncerDismissActionModel(onDismissAction2, runnable2, null, 4, null);
        }
        this.repository.getClass();
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.primaryBouncerView).getDelegate();
        if (delegate != null) {
            delegate.$securityContainerController.setOnDismissAction(onDismissAction2, runnable2);
        }
    }

    public final void setPanelExpansion(float f) {
        int i = 0;
        boolean z = 0.0f == f;
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) this.repository;
        if (!keyguardBouncerRepositoryImpl.isPrimaryBouncerStartingDisappearAnimation()) {
            keyguardBouncerRepositoryImpl._panelExpansionAmount.updateState(null, Float.valueOf(f));
        }
        PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor = this.primaryBouncerCallbackInteractor;
        if (f == 1.0f) {
            hide();
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor$setPanelExpansion$1
                @Override // java.lang.Runnable
                public final void run() {
                    Iterator it = PrimaryBouncerInteractor.this.primaryBouncerCallbackInteractor.resetCallbacks.iterator();
                    if (it.hasNext()) {
                        throw FragmentManager$$ExternalSyntheticOutline0.m(it);
                    }
                }
            });
            ArrayList arrayList = primaryBouncerCallbackInteractor.expansionCallbacks;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) obj).onFullyHidden();
            }
        } else if (f != 0.0f) {
            ArrayList arrayList2 = primaryBouncerCallbackInteractor.expansionCallbacks;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) obj2).onStartingToHide();
            }
            keyguardBouncerRepositoryImpl._primaryBouncerStartingToHide.updateState(null, Boolean.TRUE);
        }
        if (z) {
            return;
        }
        ArrayList arrayList3 = primaryBouncerCallbackInteractor.expansionCallbacks;
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj3 = arrayList3.get(i);
            i++;
            ((PrimaryBouncerCallbackInteractor.PrimaryBouncerExpansionCallback) obj3).onExpansionChanged(f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x007c A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008e A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009a A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b4 A[Catch: all -> 0x0130, TRY_LEAVE, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e2 A[Catch: all -> 0x0130, TRY_LEAVE, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00eb A[Catch: all -> 0x0130, TRY_ENTER, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x010d A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0121 A[Catch: all -> 0x0130, LOOP:0: B:41:0x011f->B:42:0x0121, LOOP_END, TRY_LEAVE, TryCatch #0 {all -> 0x0130, blocks: (B:7:0x001f, B:10:0x0043, B:13:0x006e, B:15:0x007c, B:16:0x0080, B:18:0x008e, B:20:0x009a, B:21:0x00ac, B:23:0x00b4, B:27:0x00ca, B:29:0x00ce, B:31:0x00d4, B:33:0x00e2, B:36:0x00eb, B:37:0x00f0, B:39:0x010d, B:40:0x0117, B:42:0x0121, B:46:0x0056, B:48:0x0064), top: B:6:0x001f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean show(java.lang.String r15, boolean r16) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor.show(java.lang.String, boolean):boolean");
    }

    public final void startDisappearAnimation(Runnable runnable) {
        KeyguardSecurityModel.SecurityMode securityMode = this.keyguardSecurityModel.getSecurityMode(this.selectedUserInteractor.getSelectedUserId());
        if (willRunDismissFromKeyguard() || securityMode == KeyguardSecurityModel.SecurityMode.None) {
            runnable.run();
        } else {
            ((KeyguardBouncerRepositoryImpl) this.repository).primaryBouncerStartingDisappearAnimation.tryEmit(runnable);
        }
    }

    public final boolean willDismissWithAction() {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.primaryBouncerView).getDelegate();
        if (delegate == null) {
            return false;
        }
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = delegate.$securityContainerController;
        return (keyguardSecSecurityContainerController.mDismissAction == null && keyguardSecSecurityContainerController.mCancelAction == null) ? false : true;
    }

    public final boolean willRunDismissFromKeyguard() {
        KeyguardBouncerViewBinder$bind$delegate$1 delegate = ((BouncerViewImpl) this.primaryBouncerView).getDelegate();
        return delegate != null && delegate.$securityContainerController.mWillRunDismissFromKeyguard;
    }
}
