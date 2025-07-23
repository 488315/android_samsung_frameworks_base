package androidx.compose.foundation.text.handwriting;

import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusRequesterModifierNode;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DpTouchBoundsExpansion;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.node.TouchBoundsExpansion;
import androidx.compose.ui.unit.Density;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class StylusHandwritingNode extends DelegatingNode implements PointerInputModifierNode, FocusEventModifierNode, FocusRequesterModifierNode {
    public boolean focused;
    public Function0 onHandwritingSlopExceeded;
    public final SuspendingPointerInputModifierNode suspendingPointerInputModifierNode;

    public StylusHandwritingNode(Function0 function0) {
        this.onHandwritingSlopExceeded = function0;
        SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.handwriting.StylusHandwritingNode$suspendingPointerInputModifierNode$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: androidx.compose.foundation.text.handwriting.StylusHandwritingNode$suspendingPointerInputModifierNode$1$1, reason: invalid class name */
            final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                private /* synthetic */ Object L$0;
                Object L$1;
                Object L$2;
                int label;
                final /* synthetic */ StylusHandwritingNode this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(StylusHandwritingNode stylusHandwritingNode, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = stylusHandwritingNode;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:23:0x0177, code lost:
                
                    if (r3 != r1) goto L79;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:24:0x0179, code lost:
                
                    return r1;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:65:0x00d3, code lost:
                
                    if (r10 == r1) goto L78;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:99:0x0058, code lost:
                
                    if (r8 == r1) goto L78;
                 */
                /* JADX WARN: Removed duplicated region for block: B:57:0x0150  */
                /* JADX WARN: Removed duplicated region for block: B:59:0x0153  */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0177 -> B:7:0x017a). Please report as a decompilation issue!!! */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x00d3 -> B:29:0x00d7). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r19) {
                    /*
                        Method dump skipped, instructions count: 442
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.handwriting.StylusHandwritingNode$suspendingPointerInputModifierNode$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass1(StylusHandwritingNode.this, null), continuation);
                return awaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(SuspendingPointerInputModifierNode);
        this.suspendingPointerInputModifierNode = SuspendingPointerInputModifierNode;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: getTouchBoundsExpansion-RZrCHBk, reason: not valid java name */
    public final long mo212getTouchBoundsExpansionRZrCHBk() {
        DpTouchBoundsExpansion dpTouchBoundsExpansion = StylusHandwritingKt.HandwritingBoundsExpansion;
        Density density = DelegatableNodeKt.requireLayoutNode(this).density;
        dpTouchBoundsExpansion.getClass();
        TouchBoundsExpansion.Companion companion = TouchBoundsExpansion.Companion;
        int mo51roundToPx0680j_4 = density.mo51roundToPx0680j_4(dpTouchBoundsExpansion.start);
        int mo51roundToPx0680j_42 = density.mo51roundToPx0680j_4(dpTouchBoundsExpansion.top);
        int mo51roundToPx0680j_43 = density.mo51roundToPx0680j_4(dpTouchBoundsExpansion.end);
        int mo51roundToPx0680j_44 = density.mo51roundToPx0680j_4(dpTouchBoundsExpansion.bottom);
        companion.getClass();
        return TouchBoundsExpansion.Companion.pack$ui_release(dpTouchBoundsExpansion.isLayoutDirectionAware, mo51roundToPx0680j_4, mo51roundToPx0680j_42, mo51roundToPx0680j_43, mo51roundToPx0680j_44);
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        ((SuspendingPointerInputModifierNodeImpl) this.suspendingPointerInputModifierNode).onCancelPointerInput();
    }

    @Override // androidx.compose.ui.focus.FocusEventModifierNode
    public final void onFocusEvent(FocusStateImpl focusStateImpl) {
        this.focused = focusStateImpl.isFocused();
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        ((SuspendingPointerInputModifierNodeImpl) this.suspendingPointerInputModifierNode).mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
    }
}
