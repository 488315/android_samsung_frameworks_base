package androidx.compose.foundation;

import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.node.PointerInputModifierNode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final class HoverableNode extends Modifier.Node implements PointerInputModifierNode {
    public HoverInteraction$Enter hoverInteraction;
    public MutableInteractionSource interactionSource;

    public HoverableNode(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$emitEnter(HoverableNode hoverableNode, ContinuationImpl continuationImpl) {
        HoverableNode$emitEnter$1 hoverableNode$emitEnter$1;
        HoverInteraction$Enter hoverInteraction$Enter;
        hoverableNode.getClass();
        if (continuationImpl instanceof HoverableNode$emitEnter$1) {
            hoverableNode$emitEnter$1 = (HoverableNode$emitEnter$1) continuationImpl;
            int i = hoverableNode$emitEnter$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                hoverableNode$emitEnter$1.label = i - Integer.MIN_VALUE;
            } else {
                hoverableNode$emitEnter$1 = new HoverableNode$emitEnter$1(hoverableNode, continuationImpl);
            }
        }
        Object obj = hoverableNode$emitEnter$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = hoverableNode$emitEnter$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (hoverableNode.hoverInteraction == null) {
                hoverInteraction$Enter = new HoverInteraction$Enter();
                MutableInteractionSource mutableInteractionSource = hoverableNode.interactionSource;
                hoverableNode$emitEnter$1.L$0 = hoverableNode;
                hoverableNode$emitEnter$1.L$1 = hoverInteraction$Enter;
                hoverableNode$emitEnter$1.label = 1;
                if (mutableInteractionSource.emit(hoverInteraction$Enter, hoverableNode$emitEnter$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        HoverInteraction$Enter hoverInteraction$Enter2 = (HoverInteraction$Enter) hoverableNode$emitEnter$1.L$1;
        HoverableNode hoverableNode2 = (HoverableNode) hoverableNode$emitEnter$1.L$0;
        ResultKt.throwOnFailure(obj);
        hoverInteraction$Enter = hoverInteraction$Enter2;
        hoverableNode = hoverableNode2;
        hoverableNode.hoverInteraction = hoverInteraction$Enter;
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$emitExit(HoverableNode hoverableNode, ContinuationImpl continuationImpl) {
        HoverableNode$emitExit$1 hoverableNode$emitExit$1;
        hoverableNode.getClass();
        if (continuationImpl instanceof HoverableNode$emitExit$1) {
            hoverableNode$emitExit$1 = (HoverableNode$emitExit$1) continuationImpl;
            int i = hoverableNode$emitExit$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                hoverableNode$emitExit$1.label = i - Integer.MIN_VALUE;
            } else {
                hoverableNode$emitExit$1 = new HoverableNode$emitExit$1(hoverableNode, continuationImpl);
            }
        }
        Object obj = hoverableNode$emitExit$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = hoverableNode$emitExit$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            HoverInteraction$Enter hoverInteraction$Enter = hoverableNode.hoverInteraction;
            if (hoverInteraction$Enter != null) {
                HoverInteraction$Exit hoverInteraction$Exit = new HoverInteraction$Exit(hoverInteraction$Enter);
                MutableInteractionSource mutableInteractionSource = hoverableNode.interactionSource;
                hoverableNode$emitExit$1.L$0 = hoverableNode;
                hoverableNode$emitExit$1.label = 1;
                if (mutableInteractionSource.emit(hoverInteraction$Exit, hoverableNode$emitExit$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        hoverableNode = (HoverableNode) hoverableNode$emitExit$1.L$0;
        ResultKt.throwOnFailure(obj);
        hoverableNode.hoverInteraction = null;
        return Unit.INSTANCE;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        tryEmitExit();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        tryEmitExit();
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        if (pointerEventPass == PointerEventPass.Main) {
            int i = pointerEvent.type;
            PointerEventType.Companion.getClass();
            if (i == PointerEventType.Enter) {
                BuildersKt.launch$default(getCoroutineScope(), null, null, new HoverableNode$onPointerEvent$1(this, null), 3);
            } else if (i == PointerEventType.Exit) {
                BuildersKt.launch$default(getCoroutineScope(), null, null, new HoverableNode$onPointerEvent$2(this, null), 3);
            }
        }
    }

    public final void tryEmitExit() {
        HoverInteraction$Enter hoverInteraction$Enter = this.hoverInteraction;
        if (hoverInteraction$Enter != null) {
            this.interactionSource.tryEmit(new HoverInteraction$Exit(hoverInteraction$Enter));
            this.hoverInteraction = null;
        }
    }
}
