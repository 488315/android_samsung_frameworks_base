package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.FocusInteraction$Unfocus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.interaction.PressInteraction$Release;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Stroke;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class ShortcutHelperInteractionsNode extends Modifier.Node implements DrawModifierNode {
    public final InteractionSource interactionSource;
    public final InteractionsConfig interactionsConfig;
    public final MutableState isFocused;
    public final MutableState isHovered;
    public final MutableState isPressed;

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperInteractionsNode$onAttach$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutHelperInteractionsNode.this.new AnonymousClass1(continuation);
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
                final ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                final ArrayList arrayList3 = new ArrayList();
                SharedFlowImpl interactions = ShortcutHelperInteractionsNode.this.interactionSource.getInteractions();
                final ShortcutHelperInteractionsNode shortcutHelperInteractionsNode = ShortcutHelperInteractionsNode.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperInteractionsNode.onAttach.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Interaction interaction = (Interaction) obj2;
                        if (interaction instanceof FocusInteraction$Focus) {
                            arrayList2.add(interaction);
                        } else if (interaction instanceof FocusInteraction$Unfocus) {
                            arrayList2.remove(((FocusInteraction$Unfocus) interaction).focus);
                        } else if (interaction instanceof HoverInteraction$Enter) {
                            arrayList.add(interaction);
                        } else if (interaction instanceof HoverInteraction$Exit) {
                            arrayList.remove(((HoverInteraction$Exit) interaction).enter);
                        } else if (interaction instanceof PressInteraction$Press) {
                            arrayList3.add(interaction);
                        } else if (interaction instanceof PressInteraction$Release) {
                            arrayList3.remove(((PressInteraction$Release) interaction).press);
                        } else if (interaction instanceof PressInteraction$Cancel) {
                            arrayList3.remove(((PressInteraction$Cancel) interaction).press);
                        }
                        ShortcutHelperInteractionsNode shortcutHelperInteractionsNode2 = shortcutHelperInteractionsNode;
                        ((SnapshotMutableStateImpl) shortcutHelperInteractionsNode2.isHovered).setValue(Boolean.valueOf(!arrayList.isEmpty()));
                        ((SnapshotMutableStateImpl) shortcutHelperInteractionsNode2.isPressed).setValue(Boolean.valueOf(!arrayList3.isEmpty()));
                        ((SnapshotMutableStateImpl) shortcutHelperInteractionsNode2.isFocused).setValue(Boolean.valueOf(!arrayList2.isEmpty()));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                interactions.getClass();
                if (SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this) == coroutineSingletons) {
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

    public ShortcutHelperInteractionsNode(InteractionSource interactionSource, InteractionsConfig interactionsConfig) {
        this.interactionSource = interactionSource;
        this.interactionsConfig = interactionsConfig;
        Boolean bool = Boolean.FALSE;
        this.isFocused = SnapshotStateKt.mutableStateOf$default(bool);
        this.isHovered = SnapshotStateKt.mutableStateOf$default(bool);
        this.isPressed = SnapshotStateKt.mutableStateOf$default(bool);
    }

    public static final Rect draw$getRectangleWithPadding(ShortcutHelperInteractionsNode shortcutHelperInteractionsNode, LayoutNodeDrawScope layoutNodeDrawScope, float f, long j) {
        Offset.Companion.getClass();
        Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw(0L, j);
        Dp.Companion companion = Dp.Companion;
        return Float.compare(shortcutHelperInteractionsNode.interactionsConfig.focusOutlinePadding, (float) 0) > 0 ? rectM413Recttz77jQw.inflate(layoutNodeDrawScope.mo58toPx0680j_4(f)) : rectM413Recttz77jQw.inflate(-layoutNodeDrawScope.mo58toPx0680j_4(-f));
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        long j;
        InteractionsConfig interactionsConfig;
        CanvasDrawScope canvasDrawScope;
        layoutNodeDrawScope.drawContent();
        boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.isHovered).getValue()).booleanValue();
        InteractionsConfig interactionsConfig2 = this.interactionsConfig;
        CanvasDrawScope canvasDrawScope2 = layoutNodeDrawScope.canvasDrawScope;
        if (zBooleanValue) {
            Rect rectDraw$getRectangleWithPadding = draw$getRectangleWithPadding(this, layoutNodeDrawScope, interactionsConfig2.pressedPadding, canvasDrawScope2.mo547getSizeNHjbRc());
            float fMo58toPx0680j_4 = layoutNodeDrawScope.mo58toPx0680j_4(interactionsConfig2.surfaceCornerRadius);
            long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_4) & 4294967295L);
            CornerRadius.Companion companion = CornerRadius.Companion;
            j = 4294967295L;
            canvasDrawScope = canvasDrawScope2;
            interactionsConfig = interactionsConfig2;
            DrawScope.m543drawRoundRectuAw5IA$default(layoutNodeDrawScope, interactionsConfig2.hoverOverlayColor, rectDraw$getRectangleWithPadding.m411getTopLeftF1C5BW0(), rectDraw$getRectangleWithPadding.m410getSizeNHjbRc(), jFloatToRawIntBits, null, interactionsConfig2.hoverOverlayAlpha, 208);
        } else {
            j = 4294967295L;
            interactionsConfig = interactionsConfig2;
            canvasDrawScope = canvasDrawScope2;
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.isPressed).getValue()).booleanValue()) {
            Rect rectDraw$getRectangleWithPadding2 = draw$getRectangleWithPadding(this, layoutNodeDrawScope, interactionsConfig.pressedPadding, canvasDrawScope.mo547getSizeNHjbRc());
            float fMo58toPx0680j_42 = layoutNodeDrawScope.mo58toPx0680j_4(interactionsConfig.surfaceCornerRadius);
            long jFloatToRawIntBits2 = (Float.floatToRawIntBits(fMo58toPx0680j_42) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & j);
            CornerRadius.Companion companion2 = CornerRadius.Companion;
            DrawScope.m543drawRoundRectuAw5IA$default(layoutNodeDrawScope, interactionsConfig.pressedOverlayColor, rectDraw$getRectangleWithPadding2.m411getTopLeftF1C5BW0(), rectDraw$getRectangleWithPadding2.m410getSizeNHjbRc(), jFloatToRawIntBits2, null, interactionsConfig.pressedOverlayAlpha, 208);
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.isFocused).getValue()).booleanValue()) {
            Rect rectDraw$getRectangleWithPadding3 = draw$getRectangleWithPadding(this, layoutNodeDrawScope, interactionsConfig.focusOutlinePadding, canvasDrawScope.mo547getSizeNHjbRc());
            Stroke stroke = new Stroke(layoutNodeDrawScope.mo58toPx0680j_4(interactionsConfig.focusOutlineStrokeWidth), 0.0f, 0, 0, null, 30, null);
            long jM411getTopLeftF1C5BW0 = rectDraw$getRectangleWithPadding3.m411getTopLeftF1C5BW0();
            long jM410getSizeNHjbRc = rectDraw$getRectangleWithPadding3.m410getSizeNHjbRc();
            float fMo58toPx0680j_43 = layoutNodeDrawScope.mo58toPx0680j_4(interactionsConfig.focusOutlineCornerRadius);
            long jFloatToRawIntBits3 = (Float.floatToRawIntBits(fMo58toPx0680j_43) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_43) & j);
            CornerRadius.Companion companion3 = CornerRadius.Companion;
            DrawScope.m543drawRoundRectuAw5IA$default(layoutNodeDrawScope, interactionsConfig.focusOutlineColor, jM411getTopLeftF1C5BW0, jM410getSizeNHjbRc, jFloatToRawIntBits3, stroke, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        CoroutineTracingKt.launchTraced$default(getCoroutineScope(), null, null, new AnonymousClass1(null), 7);
    }
}
