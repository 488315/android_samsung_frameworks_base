package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.node.PointerInputModifierNode;
import com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Press;
import com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Release;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslTouchableNode extends Modifier.Node implements PointerInputModifierNode {
    public boolean enabled;
    public MutableInteractionSource interactionSource;
    public Function1 onTouchDown;
    public SeslTouchInteraction$Press press;

    public /* synthetic */ SeslTouchableNode(boolean z, MutableInteractionSource mutableInteractionSource, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, mutableInteractionSource, (i & 4) != 0 ? null : function1);
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        SeslTouchInteraction$Press seslTouchInteraction$Press = this.press;
        if (seslTouchInteraction$Press != null) {
            this.interactionSource.tryEmit(new SeslTouchInteraction$Release(seslTouchInteraction$Press));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v9, types: [androidx.compose.foundation.interaction.Interaction, com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Press] */
    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        SeslTouchInteraction$Press seslTouchInteraction$Press;
        if (this.enabled && pointerEventPass == PointerEventPass.Main) {
            int i = pointerEvent.type;
            PointerEventType.Companion.getClass();
            if (i == PointerEventType.Press) {
                if (PointerEventKt.changedToDownIgnoreConsumed((PointerInputChange) pointerEvent.changes.get(0))) {
                    ?? r1 = new Interaction() { // from class: com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Press
                    };
                    this.interactionSource.tryEmit(r1);
                    this.press = r1;
                    Function1 function1 = this.onTouchDown;
                    if (function1 != null) {
                        function1.mo779invoke(Boolean.TRUE);
                        return;
                    }
                    return;
                }
                return;
            }
            if (i == PointerEventType.Move) {
                final SeslTouchInteraction$Press seslTouchInteraction$Press2 = this.press;
                if (seslTouchInteraction$Press2 != null) {
                    this.interactionSource.tryEmit(new Interaction(seslTouchInteraction$Press2) { // from class: com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Move
                    });
                    return;
                }
                return;
            }
            if (i != PointerEventType.Release || (seslTouchInteraction$Press = this.press) == null) {
                return;
            }
            this.interactionSource.tryEmit(new SeslTouchInteraction$Release(seslTouchInteraction$Press));
        }
    }

    public SeslTouchableNode(boolean z, MutableInteractionSource mutableInteractionSource, Function1 function1) {
        this.enabled = z;
        this.interactionSource = mutableInteractionSource;
        this.onTouchDown = function1;
    }
}
