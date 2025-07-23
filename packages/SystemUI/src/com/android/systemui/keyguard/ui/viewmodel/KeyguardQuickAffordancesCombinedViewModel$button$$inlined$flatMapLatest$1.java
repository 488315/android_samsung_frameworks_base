package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $overrideQuickAffordanceId$inlined;
    final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardQuickAffordancesCombinedViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, String str) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordancesCombinedViewModel;
        this.$position$inlined = keyguardQuickAffordancePosition;
        this.$overrideQuickAffordanceId$inlined = str;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1 = new KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$position$inlined, this.$overrideQuickAffordanceId$inlined);
        keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00b8, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r3, r14, r13) == r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r13.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L34
            if (r1 == r4) goto L28
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lbb
        L14:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L1c:
            java.lang.Object r1 = r13.L$1
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$PreviewMode r1 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel.PreviewMode) r1
            java.lang.Object r3 = r13.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r14)
            goto L75
        L28:
            java.lang.Object r1 = r13.L$1
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$PreviewMode r1 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel.PreviewMode) r1
            java.lang.Object r3 = r13.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r14)
            goto L5b
        L34:
            kotlin.ResultKt.throwOnFailure(r14)
            java.lang.Object r14 = r13.L$0
            kotlinx.coroutines.flow.FlowCollector r14 = (kotlinx.coroutines.flow.FlowCollector) r14
            java.lang.Object r1 = r13.L$1
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$PreviewMode r1 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel.PreviewMode) r1
            boolean r5 = r1.isInPreviewMode
            if (r5 == 0) goto L5f
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel r3 = r13.this$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r3 = r3.quickAffordanceInteractor
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r5 = r13.$position$inlined
            java.lang.String r6 = r13.$overrideQuickAffordanceId$inlined
            r13.L$0 = r14
            r13.L$1 = r1
            r13.label = r4
            java.lang.Object r3 = r3.quickAffordanceAlwaysVisible(r5, r6, r13)
            if (r3 != r0) goto L58
            goto Lba
        L58:
            r12 = r3
            r3 = r14
            r14 = r12
        L5b:
            kotlinx.coroutines.flow.Flow r14 = (kotlinx.coroutines.flow.Flow) r14
        L5d:
            r4 = r14
            goto L78
        L5f:
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel r4 = r13.this$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r4 = r4.quickAffordanceInteractor
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r5 = r13.$position$inlined
            r13.L$0 = r14
            r13.L$1 = r1
            r13.label = r3
            java.lang.Object r3 = r4.quickAffordance(r5, r13)
            if (r3 != r0) goto L72
            goto Lba
        L72:
            r12 = r3
            r3 = r14
            r14 = r12
        L75:
            kotlinx.coroutines.flow.Flow r14 = (kotlinx.coroutines.flow.Flow) r14
            goto L5d
        L78:
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel r14 = r13.this$0
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r14 = r14.keyguardInteractor
            kotlin.Lazy r14 = r14.animateDozingTransitions$delegate
            java.lang.Object r14 = r14.getValue()
            kotlinx.coroutines.flow.Flow r14 = (kotlinx.coroutines.flow.Flow) r14
            kotlinx.coroutines.flow.Flow r5 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r14)
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel r14 = r13.this$0
            kotlinx.coroutines.flow.Flow r6 = r14.areQuickAffordancesFullyOpaque
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r7 = r14.quickAffordanceInteractor
            com.android.systemui.dock.DockManager r8 = r7.dockManager
            kotlinx.coroutines.flow.Flow r8 = com.android.systemui.dock.DockManagerExtensionsKt.retrieveIsDocked(r8)
            r9 = r8
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1 r8 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$useLongPress$$inlined$map$1
            r8.<init>()
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$button$1$1 r9 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$button$1$1
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r7 = r13.$position$inlined
            com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel r10 = r13.this$0
            r11 = 0
            r9.<init>(r7, r10, r1, r11)
            kotlinx.coroutines.flow.StateFlowImpl r7 = r14.selectedPreviewSlotId
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 r14 = kotlinx.coroutines.flow.FlowKt.combine(r4, r5, r6, r7, r8, r9)
            kotlinx.coroutines.flow.Flow r14 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r14)
            r13.L$0 = r11
            r13.L$1 = r11
            r13.label = r2
            java.lang.Object r13 = kotlinx.coroutines.flow.FlowKt.emitAll(r3, r14, r13)
            if (r13 != r0) goto Lbb
        Lba:
            return r0
        Lbb:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
