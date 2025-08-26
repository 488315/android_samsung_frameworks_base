package com.android.systemui.reardisplay;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.domain.interactor.RearDisplayStateInteractor;
import com.android.systemui.display.domain.interactor.RearDisplayStateInteractorImpl;
import com.android.systemui.reardisplay.RearDisplayInnerDialogDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class RearDisplayCoreStartable implements CoreStartable, AutoCloseable {
    public final AccessibilityManager accessibilityManager;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final Handler handler;
    public final KeyguardUpdateMonitorCallback keyguardCallback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final StateFlowImpl keyguardVisible;
    public final ReadonlyStateFlow keyguardVisibleFlow;
    public final RearDisplayInnerDialogDelegate.Factory rearDisplayInnerDialogDelegateFactory;
    public final RearDisplayStateInteractor rearDisplayStateInteractor;
    public final CoroutineScope scope;
    public StandaloneCoroutine stateChangeListener;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$ObjectRef<SystemUIDialog> $dialog;
        final /* synthetic */ Ref$ObjectRef<AtomicBoolean> $touchExplorationEnabled;
        int label;

        /* renamed from: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ boolean Z$0;
            int label;

            public AnonymousClass1(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
                anonymousClass1.L$0 = (RearDisplayStateInteractor.State) obj;
                anonymousClass1.Z$0 = zBooleanValue;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return new Pair((RearDisplayStateInteractor.State) this.L$0, Boolean.valueOf(this.Z$0));
            }
        }

        /* renamed from: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$2$2, reason: invalid class name and collision with other inner class name */
        final class C04292 extends SuspendLambda implements Function2 {
            final /* synthetic */ Ref$ObjectRef<SystemUIDialog> $dialog;
            final /* synthetic */ Ref$ObjectRef<AtomicBoolean> $touchExplorationEnabled;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ RearDisplayCoreStartable this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04292(RearDisplayCoreStartable rearDisplayCoreStartable, Ref$ObjectRef<AtomicBoolean> ref$ObjectRef, Ref$ObjectRef<SystemUIDialog> ref$ObjectRef2, Continuation continuation) {
                super(2, continuation);
                this.this$0 = rearDisplayCoreStartable;
                this.$touchExplorationEnabled = ref$ObjectRef;
                this.$dialog = ref$ObjectRef2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04292 c04292 = new C04292(this.this$0, this.$touchExplorationEnabled, this.$dialog, continuation);
                c04292.L$0 = obj;
                return c04292;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04292) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Type inference failed for: r4v16, types: [T, android.app.AlertDialog, com.android.systemui.statusbar.phone.SystemUIDialog] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                RearDisplayStateInteractor.State state = (RearDisplayStateInteractor.State) pair.component1();
                boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
                if (state instanceof RearDisplayStateInteractor.State.Enabled) {
                    if (!zBooleanValue) {
                        Context contextCreateDisplayContext = this.this$0.context.createDisplayContext(((RearDisplayStateInteractor.State.Enabled) state).innerDisplay);
                        RearDisplayInnerDialogDelegate.Factory factory = this.this$0.rearDisplayInnerDialogDelegateFactory;
                        contextCreateDisplayContext.getClass();
                        final DeviceStateManager deviceStateManager = this.this$0.deviceStateManager;
                        RearDisplayInnerDialogDelegate rearDisplayInnerDialogDelegateCreate = factory.create(contextCreateDisplayContext, new Runnable() { // from class: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$2$2$delegate$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                deviceStateManager.cancelStateRequest();
                            }
                        }, this.$touchExplorationEnabled.element.get());
                        Ref$ObjectRef<SystemUIDialog> ref$ObjectRef = this.$dialog;
                        ?? CreateDialog = rearDisplayInnerDialogDelegateCreate.createDialog();
                        CreateDialog.show();
                        ref$ObjectRef.element = CreateDialog;
                    }
                } else {
                    if (!(state instanceof RearDisplayStateInteractor.State.Disabled)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    SystemUIDialog systemUIDialog = this.$dialog.element;
                    if (systemUIDialog != null) {
                        systemUIDialog.dismiss();
                    }
                    this.$dialog.element = null;
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Ref$ObjectRef<AtomicBoolean> ref$ObjectRef, Ref$ObjectRef<SystemUIDialog> ref$ObjectRef2, Continuation continuation) {
            super(2, continuation);
            this.$touchExplorationEnabled = ref$ObjectRef;
            this.$dialog = ref$ObjectRef2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RearDisplayCoreStartable.this.new AnonymousClass2(this.$touchExplorationEnabled, this.$dialog, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                RearDisplayCoreStartable rearDisplayCoreStartable = RearDisplayCoreStartable.this;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((RearDisplayStateInteractorImpl) rearDisplayCoreStartable.rearDisplayStateInteractor).state, rearDisplayCoreStartable.keyguardVisibleFlow, new AnonymousClass1(null));
                C04292 c04292 = new C04292(RearDisplayCoreStartable.this, this.$touchExplorationEnabled, this.$dialog, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, c04292, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public RearDisplayCoreStartable(Context context, DeviceStateManager deviceStateManager, RearDisplayStateInteractor rearDisplayStateInteractor, RearDisplayInnerDialogDelegate.Factory factory, CoroutineScope coroutineScope, KeyguardUpdateMonitor keyguardUpdateMonitor, AccessibilityManager accessibilityManager, Handler handler) {
        this.context = context;
        this.deviceStateManager = deviceStateManager;
        this.rearDisplayStateInteractor = rearDisplayStateInteractor;
        this.rearDisplayInnerDialogDelegateFactory = factory;
        this.scope = coroutineScope;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.accessibilityManager = accessibilityManager;
        this.handler = handler;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.keyguardVisible = stateFlowImplMutableStateFlow;
        this.keyguardVisibleFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.keyguardCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.reardisplay.RearDisplayCoreStartable$keyguardCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                this.this$0.keyguardVisible.updateState(null, Boolean.valueOf(z));
            }
        };
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        StandaloneCoroutine standaloneCoroutine = this.stateChangeListener;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.concurrent.atomic.AtomicBoolean] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        ref$ObjectRef2.element = new AtomicBoolean(false);
        this.accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.reardisplay.RearDisplayCoreStartable.start.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                ((AtomicBoolean) ref$ObjectRef2.element).set(z);
            }
        }, this.handler);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardCallback);
        this.stateChangeListener = BuildersKt.launch$default(this.scope, null, null, new AnonymousClass2(ref$ObjectRef2, ref$ObjectRef, null), 3);
    }

    public static /* synthetic */ void getKeyguardCallback$annotations() {
    }

    public static /* synthetic */ void getStateChangeListener$annotations() {
    }
}
