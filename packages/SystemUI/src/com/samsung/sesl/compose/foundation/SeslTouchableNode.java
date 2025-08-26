package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.platform.ViewConfiguration;
import com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Move;
import com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Press;
import com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Release;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class SeslTouchableNode extends Modifier.Node implements PointerInputModifierNode, CompositionLocalConsumerModifierNode {
    public PointerInputChange downInputChange;
    public boolean enabled;
    public MutableInteractionSource interactionSource;
    public Function1 onTouchDown;
    public SeslTouchInteraction$Press press;

    public SeslTouchableNode(boolean z, MutableInteractionSource mutableInteractionSource, Function1 function1) {
        this.enabled = z;
        this.interactionSource = mutableInteractionSource;
        this.onTouchDown = function1;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        SeslTouchInteraction$Press seslTouchInteraction$Press = this.press;
        if (seslTouchInteraction$Press != null) {
            this.interactionSource.tryEmit(new SeslTouchInteraction$Release(seslTouchInteraction$Press));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v6, types: [androidx.compose.foundation.interaction.Interaction, com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Press] */
    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        SeslTouchInteraction$Press seslTouchInteraction$Press;
        if (this.enabled && pointerEventPass == PointerEventPass.Main) {
            int i = pointerEvent.type;
            PointerEventType.Companion.getClass();
            if (i == PointerEventType.Press) {
                PointerInputChange pointerInputChange = (PointerInputChange) pointerEvent.changes.get(0);
                if (PointerEventKt.changedToDown(pointerInputChange)) {
                    ?? r6 = new Interaction() { // from class: com.samsung.sesl.compose.foundation.interaction.SeslTouchInteraction$Press
                    };
                    this.interactionSource.tryEmit(r6);
                    this.press = r6;
                    this.onTouchDown.mo781invoke(true);
                    this.downInputChange = pointerInputChange;
                    return;
                }
                return;
            }
            if (i != PointerEventType.Move) {
                if (i == PointerEventType.Release) {
                    SeslTouchInteraction$Press seslTouchInteraction$Press2 = this.press;
                    if (seslTouchInteraction$Press2 != null) {
                        this.interactionSource.tryEmit(new SeslTouchInteraction$Release(seslTouchInteraction$Press2));
                    }
                    this.press = null;
                    this.onTouchDown.mo781invoke(false);
                    this.downInputChange = null;
                    return;
                }
                return;
            }
            PointerInputChange pointerInputChange2 = (PointerInputChange) CollectionsKt___CollectionsKt.getOrNull(0, pointerEvent.changes);
            if (pointerInputChange2 == null || (seslTouchInteraction$Press = this.press) == null) {
                return;
            }
            PointerInputChange pointerInputChange3 = this.downInputChange;
            if (pointerInputChange3 != null) {
                long j2 = pointerInputChange3.position;
                float fM400getXimpl = Offset.m400getXimpl(j2);
                long j3 = pointerInputChange2.position;
                float fAbs = Math.abs(fM400getXimpl - Offset.m400getXimpl(j3));
                float fAbs2 = Math.abs(Offset.m401getYimpl(j2) - Offset.m401getYimpl(j3));
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = androidx.compose.ui.platform.CompositionLocalsKt.LocalViewConfiguration;
                if (fAbs > ((ViewConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, staticProvidableCompositionLocal)).getTouchSlop() || fAbs2 > ((ViewConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, staticProvidableCompositionLocal)).getTouchSlop()) {
                    this.downInputChange = null;
                }
            }
            if (this.downInputChange == null) {
                this.interactionSource.tryEmit(new SeslTouchInteraction$Move(seslTouchInteraction$Press));
            }
        }
    }
}
