package androidx.compose.foundation;

import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.node.PointerInputModifierNode;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HoverableNode extends Modifier.Node implements PointerInputModifierNode {
    public HoverInteraction$Enter hoverInteraction;
    public MutableInteractionSource interactionSource;

    public HoverableNode(MutableInteractionSource mutableInteractionSource) {
        this.interactionSource = mutableInteractionSource;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$emitEnter(androidx.compose.foundation.HoverableNode r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof androidx.compose.foundation.HoverableNode$emitEnter$1
            if (r0 == 0) goto L16
            r0 = r5
            androidx.compose.foundation.HoverableNode$emitEnter$1 r0 = (androidx.compose.foundation.HoverableNode$emitEnter$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.foundation.HoverableNode$emitEnter$1 r0 = new androidx.compose.foundation.HoverableNode$emitEnter$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r4 = r0.L$1
            androidx.compose.foundation.interaction.HoverInteraction$Enter r4 = (androidx.compose.foundation.interaction.HoverInteraction$Enter) r4
            java.lang.Object r0 = r0.L$0
            androidx.compose.foundation.HoverableNode r0 = (androidx.compose.foundation.HoverableNode) r0
            kotlin.ResultKt.throwOnFailure(r5)
            r5 = r4
            r4 = r0
            goto L57
        L34:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3c:
            kotlin.ResultKt.throwOnFailure(r5)
            androidx.compose.foundation.interaction.HoverInteraction$Enter r5 = r4.hoverInteraction
            if (r5 != 0) goto L59
            androidx.compose.foundation.interaction.HoverInteraction$Enter r5 = new androidx.compose.foundation.interaction.HoverInteraction$Enter
            r5.<init>()
            androidx.compose.foundation.interaction.MutableInteractionSource r2 = r4.interactionSource
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r0 = r2.emit(r5, r0)
            if (r0 != r1) goto L57
            return r1
        L57:
            r4.hoverInteraction = r5
        L59:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.HoverableNode.access$emitEnter(androidx.compose.foundation.HoverableNode, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$emitExit(androidx.compose.foundation.HoverableNode r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof androidx.compose.foundation.HoverableNode$emitExit$1
            if (r0 == 0) goto L16
            r0 = r5
            androidx.compose.foundation.HoverableNode$emitExit$1 r0 = (androidx.compose.foundation.HoverableNode$emitExit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            androidx.compose.foundation.HoverableNode$emitExit$1 r0 = new androidx.compose.foundation.HoverableNode$emitExit$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            androidx.compose.foundation.HoverableNode r4 = (androidx.compose.foundation.HoverableNode) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4f
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r5)
            androidx.compose.foundation.interaction.HoverInteraction$Enter r5 = r4.hoverInteraction
            if (r5 == 0) goto L52
            androidx.compose.foundation.interaction.HoverInteraction$Exit r2 = new androidx.compose.foundation.interaction.HoverInteraction$Exit
            r2.<init>(r5)
            androidx.compose.foundation.interaction.MutableInteractionSource r5 = r4.interactionSource
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r5.emit(r2, r0)
            if (r5 != r1) goto L4f
            return r1
        L4f:
            r5 = 0
            r4.hoverInteraction = r5
        L52:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.HoverableNode.access$emitExit(androidx.compose.foundation.HoverableNode, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
