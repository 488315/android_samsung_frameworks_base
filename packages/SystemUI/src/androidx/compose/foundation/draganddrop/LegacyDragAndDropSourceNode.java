package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.draganddrop.DragAndDropModifierNode;
import androidx.compose.ui.draganddrop.DragAndDropNode;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class LegacyDragAndDropSourceNode extends DelegatingNode implements LayoutAwareModifierNode {
    public final Function2 dragAndDropSourceHandler;
    public final Function1 drawDragDecoration;

    public LegacyDragAndDropSourceNode(Function1 function1, Function2 function2) {
        this.drawDragDecoration = function1;
        this.dragAndDropSourceHandler = function2;
        IntSize.Companion.getClass();
        final DragAndDropNode dragAndDropNode = new DragAndDropNode(null, null, 2, null);
        delegate(dragAndDropNode);
        delegate(SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.draganddrop.LegacyDragAndDropSourceNode.1

            /* renamed from: androidx.compose.foundation.draganddrop.LegacyDragAndDropSourceNode$1$1, reason: invalid class name and collision with other inner class name */
            public final class C00041 implements DragAndDropSourceScope, PointerInputScope {
                public final /* synthetic */ PointerInputScope $$delegate_0;
                public final /* synthetic */ DragAndDropModifierNode $dragAndDropModifierNode;
                public final /* synthetic */ LegacyDragAndDropSourceNode this$0;

                public C00041(PointerInputScope pointerInputScope, DragAndDropModifierNode dragAndDropModifierNode, LegacyDragAndDropSourceNode legacyDragAndDropSourceNode) {
                    this.$dragAndDropModifierNode = dragAndDropModifierNode;
                    this.this$0 = legacyDragAndDropSourceNode;
                    this.$$delegate_0 = pointerInputScope;
                }

                @Override // androidx.compose.ui.input.pointer.PointerInputScope
                public final Object awaitPointerEventScope(Function2 function2, Continuation continuation) {
                    return this.$$delegate_0.awaitPointerEventScope(function2, continuation);
                }

                @Override // androidx.compose.ui.unit.Density
                public final float getDensity() {
                    return this.$$delegate_0.getDensity();
                }

                @Override // androidx.compose.ui.unit.FontScaling
                public final float getFontScale() {
                    return this.$$delegate_0.getFontScale();
                }

                @Override // androidx.compose.ui.input.pointer.PointerInputScope
                /* renamed from: getSize-YbymL2g, reason: not valid java name */
                public final long mo51getSizeYbymL2g() {
                    return this.$$delegate_0.mo51getSizeYbymL2g();
                }

                @Override // androidx.compose.ui.input.pointer.PointerInputScope
                public final ViewConfiguration getViewConfiguration() {
                    return this.$$delegate_0.getViewConfiguration();
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: roundToPx-0680j_4, reason: not valid java name */
                public final int mo52roundToPx0680j_4(float f) {
                    return this.$$delegate_0.mo52roundToPx0680j_4(f);
                }

                @Override // androidx.compose.ui.input.pointer.PointerInputScope
                public final void setInterceptOutOfBoundsChildEvents() {
                    this.$$delegate_0.setInterceptOutOfBoundsChildEvents();
                }

                @Override // androidx.compose.ui.unit.FontScaling
                /* renamed from: toDp-GaN1DYA, reason: not valid java name */
                public final float mo53toDpGaN1DYA(long j) {
                    return this.$$delegate_0.mo53toDpGaN1DYA(j);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toDp-u2uoSUM, reason: not valid java name */
                public final float mo54toDpu2uoSUM(float f) {
                    return this.$$delegate_0.mo54toDpu2uoSUM(f);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toDpSize-k-rfVVM, reason: not valid java name */
                public final long mo56toDpSizekrfVVM(long j) {
                    return this.$$delegate_0.mo56toDpSizekrfVVM(j);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toPx--R2X_6o, reason: not valid java name */
                public final float mo57toPxR2X_6o(long j) {
                    return this.$$delegate_0.mo57toPxR2X_6o(j);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toPx-0680j_4, reason: not valid java name */
                public final float mo58toPx0680j_4(float f) {
                    return this.$$delegate_0.mo58toPx0680j_4(f);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toSize-XkaWNTQ, reason: not valid java name */
                public final long mo59toSizeXkaWNTQ(long j) {
                    return this.$$delegate_0.mo59toSizeXkaWNTQ(j);
                }

                @Override // androidx.compose.ui.unit.FontScaling
                /* renamed from: toSp-0xMU5do, reason: not valid java name */
                public final long mo60toSp0xMU5do(float f) {
                    return this.$$delegate_0.mo60toSp0xMU5do(f);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toSp-kPz2Gy4, reason: not valid java name */
                public final long mo61toSpkPz2Gy4(float f) {
                    return this.$$delegate_0.mo61toSpkPz2Gy4(f);
                }

                @Override // androidx.compose.ui.unit.Density
                /* renamed from: toDp-u2uoSUM, reason: not valid java name */
                public final float mo55toDpu2uoSUM(int i) {
                    return this.$$delegate_0.mo55toDpu2uoSUM(i);
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                LegacyDragAndDropSourceNode legacyDragAndDropSourceNode = LegacyDragAndDropSourceNode.this;
                Object objInvoke = legacyDragAndDropSourceNode.dragAndDropSourceHandler.invoke(new C00041(pointerInputScope, dragAndDropNode, legacyDragAndDropSourceNode), continuation);
                return objInvoke == CoroutineSingletons.COROUTINE_SUSPENDED ? objInvoke : Unit.INSTANCE;
            }
        }));
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* renamed from: onRemeasured-ozmzZPI, reason: not valid java name */
    public final void mo50onRemeasuredozmzZPI(long j) {
    }
}
