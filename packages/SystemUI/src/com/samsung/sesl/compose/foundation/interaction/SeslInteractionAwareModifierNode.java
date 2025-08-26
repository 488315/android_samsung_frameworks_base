package com.samsung.sesl.compose.foundation.interaction;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.Modifier;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes4.dex */
public final class SeslInteractionAwareModifierNode extends Modifier.Node {
    public InteractionSource interactionSource;
    public StandaloneCoroutine observeInteractionsJob;
    public final Function1 onState;
    public SeslInteractionState previous;
    public final List pressInteraction = new ArrayList();
    public final List touchInteraction = new ArrayList();
    public final List dragInteraction = new ArrayList();
    public final List hoverInteraction = new ArrayList();
    public final List focusInteraction = new ArrayList();

    /* renamed from: com.samsung.sesl.compose.foundation.interaction.SeslInteractionAwareModifierNode$onAttach$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SeslInteractionAwareModifierNode.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SeslInteractionAwareModifierNode seslInteractionAwareModifierNode = SeslInteractionAwareModifierNode.this;
            StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(seslInteractionAwareModifierNode.getCoroutineScope(), null, null, new SeslInteractionAwareModifierNode$collectInteractionEvents$1(seslInteractionAwareModifierNode.interactionSource, seslInteractionAwareModifierNode, null), 3);
            StandaloneCoroutine standaloneCoroutine = seslInteractionAwareModifierNode.observeInteractionsJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            seslInteractionAwareModifierNode.observeInteractionsJob = standaloneCoroutineLaunch$default;
            return Unit.INSTANCE;
        }
    }

    public SeslInteractionAwareModifierNode(InteractionSource interactionSource, Function1 function1) {
        this.onState = function1;
        this.interactionSource = interactionSource;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        BuildersKt.launch$default(getCoroutineScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        StandaloneCoroutine standaloneCoroutine = this.observeInteractionsJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.observeInteractionsJob = null;
        ((ArrayList) this.pressInteraction).clear();
        ((ArrayList) this.touchInteraction).clear();
        ((ArrayList) this.dragInteraction).clear();
        ((ArrayList) this.hoverInteraction).clear();
        ((ArrayList) this.focusInteraction).clear();
        SeslInteractionState seslInteractionState = this.previous;
        SeslInteractionState.Companion.getClass();
        SeslInteractionState seslInteractionState2 = SeslInteractionState.None;
        if (Intrinsics.areEqual(seslInteractionState, seslInteractionState2)) {
            return;
        }
        this.onState.mo781invoke(seslInteractionState2);
        this.previous = seslInteractionState2;
    }

    public final void setInteractionSource(InteractionSource interactionSource) {
        this.interactionSource = interactionSource;
        if (this.isAttached) {
            BuildersKt.launch$default(getCoroutineScope(), null, null, new SeslInteractionAwareModifierNode$interactionSource$1(this, null), 3);
        }
    }
}
