package com.android.systemui.reardisplay;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
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
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RearDisplayCoreStartable$start$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<SystemUIDialog> $dialog;
    final /* synthetic */ Ref$ObjectRef<AtomicBoolean> $touchExplorationEnabled;
    int label;
    final /* synthetic */ RearDisplayCoreStartable this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (RearDisplayStateInteractor.State) obj;
            anonymousClass1.Z$0 = booleanValue;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$ObjectRef<SystemUIDialog> $dialog;
        final /* synthetic */ Ref$ObjectRef<AtomicBoolean> $touchExplorationEnabled;
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ RearDisplayCoreStartable this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(RearDisplayCoreStartable rearDisplayCoreStartable, Ref$ObjectRef<AtomicBoolean> ref$ObjectRef, Ref$ObjectRef<SystemUIDialog> ref$ObjectRef2, Continuation continuation) {
            super(2, continuation);
            this.this$0 = rearDisplayCoreStartable;
            this.$touchExplorationEnabled = ref$ObjectRef;
            this.$dialog = ref$ObjectRef2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, this.$touchExplorationEnabled, this.$dialog, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            boolean booleanValue = ((Boolean) pair.component2()).booleanValue();
            if (state instanceof RearDisplayStateInteractor.State.Enabled) {
                if (!booleanValue) {
                    Context createDisplayContext = this.this$0.context.createDisplayContext(((RearDisplayStateInteractor.State.Enabled) state).innerDisplay);
                    RearDisplayInnerDialogDelegate.Factory factory = this.this$0.rearDisplayInnerDialogDelegateFactory;
                    createDisplayContext.getClass();
                    final DeviceStateManager deviceStateManager = this.this$0.deviceStateManager;
                    RearDisplayInnerDialogDelegate create = factory.create(createDisplayContext, new Runnable() { // from class: com.android.systemui.reardisplay.RearDisplayCoreStartable$start$2$2$delegate$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            deviceStateManager.cancelStateRequest();
                        }
                    }, this.$touchExplorationEnabled.element.get());
                    Ref$ObjectRef<SystemUIDialog> ref$ObjectRef = this.$dialog;
                    ?? createDialog = create.createDialog();
                    createDialog.show();
                    ref$ObjectRef.element = createDialog;
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
    public RearDisplayCoreStartable$start$2(RearDisplayCoreStartable rearDisplayCoreStartable, Ref$ObjectRef<AtomicBoolean> ref$ObjectRef, Ref$ObjectRef<SystemUIDialog> ref$ObjectRef2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = rearDisplayCoreStartable;
        this.$touchExplorationEnabled = ref$ObjectRef;
        this.$dialog = ref$ObjectRef2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RearDisplayCoreStartable$start$2(this.this$0, this.$touchExplorationEnabled, this.$dialog, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RearDisplayCoreStartable$start$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RearDisplayCoreStartable rearDisplayCoreStartable = this.this$0;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((RearDisplayStateInteractorImpl) rearDisplayCoreStartable.rearDisplayStateInteractor).state, rearDisplayCoreStartable.keyguardVisibleFlow, new AnonymousClass1(null));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, this.$touchExplorationEnabled, this.$dialog, null);
            this.label = 1;
            if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, anonymousClass2, this) == coroutineSingletons) {
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
