package androidx.compose.foundation.text.handwriting;

import android.view.MotionEvent;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.focus.FocusEventModifierNode;
import androidx.compose.ui.focus.FocusRequesterModifierNode;
import androidx.compose.ui.focus.FocusRequesterModifierNodeKt;
import androidx.compose.ui.focus.FocusStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DpTouchBoundsExpansion;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.node.TouchBoundsExpansion;
import androidx.compose.ui.unit.Density;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public class StylusHandwritingNode extends DelegatingNode implements PointerInputModifierNode, FocusEventModifierNode, FocusRequesterModifierNode {
    public boolean focused;
    public Function0 onHandwritingSlopExceeded;
    public final SuspendingPointerInputModifierNode suspendingPointerInputModifierNode;

    public StylusHandwritingNode(Function0 function0) {
        this.onHandwritingSlopExceeded = function0;
        SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.text.handwriting.StylusHandwritingNode$suspendingPointerInputModifierNode$1

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

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0058, code lost:
                
                    if (r8 == r1) goto L78;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:38:0x00d3, code lost:
                
                    if (r10 == r1) goto L78;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:77:0x0177, code lost:
                
                    if (r3 != r1) goto L79;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:78:0x0179, code lost:
                
                    return r1;
                 */
                /* JADX WARN: Path cross not found for [B:29:0x00b3, B:24:0x0092], limit reached: 100 */
                /* JADX WARN: Path cross not found for [B:32:0x00ba, B:35:0x00c0], limit reached: 100 */
                /* JADX WARN: Path cross not found for [B:57:0x011f, B:66:0x0134], limit reached: 100 */
                /* JADX WARN: Path cross not found for [B:66:0x0134, B:54:0x010d], limit reached: 100 */
                /* JADX WARN: Removed duplicated region for block: B:70:0x0150  */
                /* JADX WARN: Removed duplicated region for block: B:72:0x0153  */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00d3 -> B:40:0x00d7). Please report as a decompilation issue!!! */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x0177 -> B:79:0x017a). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    AwaitPointerEventScope awaitPointerEventScope;
                    Object objAwaitFirstDown;
                    PointerInputChange pointerInputChange;
                    boolean z;
                    AwaitPointerEventScope awaitPointerEventScope2;
                    PointerEventPass pointerEventPass;
                    Object objAwaitPointerEvent;
                    Object obj2;
                    PointerInputChange pointerInputChange2;
                    AwaitPointerEventScope awaitPointerEventScope3;
                    Object objAwaitPointerEvent2;
                    Object obj3;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    int i2 = 2;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        PointerEventPass pointerEventPass2 = PointerEventPass.Initial;
                        this.L$0 = awaitPointerEventScope;
                        this.label = 1;
                        objAwaitFirstDown = TapGestureDetectorKt.awaitFirstDown(awaitPointerEventScope, true, pointerEventPass2, this);
                    } else if (i == 1) {
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        objAwaitFirstDown = obj;
                    } else if (i == 2) {
                        pointerEventPass = (PointerEventPass) this.L$2;
                        pointerInputChange = (PointerInputChange) this.L$1;
                        awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        objAwaitPointerEvent = obj;
                        PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent;
                        List list = pointerEvent.changes;
                        int size = list.size();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= size) {
                                obj2 = null;
                                break;
                            }
                            obj2 = list.get(i3);
                            PointerInputChange pointerInputChange3 = (PointerInputChange) obj2;
                            if (!pointerInputChange3.isConsumed()) {
                                if (PointerId.m593equalsimpl0(pointerInputChange3.id, pointerInputChange.id) && pointerInputChange3.pressed) {
                                    break;
                                }
                            }
                            i3++;
                        }
                        PointerInputChange pointerInputChange4 = (PointerInputChange) obj2;
                        if (pointerInputChange4 != null) {
                            if (pointerInputChange4.uptimeMillis - pointerInputChange.uptimeMillis < awaitPointerEventScope2.getViewConfiguration().getLongPressTimeoutMillis()) {
                                MotionEvent motionEvent$ui_release = pointerEvent.getMotionEvent$ui_release();
                                i2 = 2;
                                if (!((motionEvent$ui_release != null ? motionEvent$ui_release.getClassification() : 0) == 2)) {
                                    if (Offset.m399getDistanceimpl(Offset.m402minusMKHz9U(pointerInputChange4.position, pointerInputChange.position)) <= awaitPointerEventScope2.getViewConfiguration().getHandwritingSlop()) {
                                        this.L$0 = awaitPointerEventScope2;
                                        this.L$1 = pointerInputChange;
                                        this.L$2 = pointerEventPass;
                                        this.label = i2;
                                        objAwaitPointerEvent = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass, this);
                                    }
                                }
                                if (pointerInputChange4 != null) {
                                    return Unit.INSTANCE;
                                }
                                StylusHandwritingNode stylusHandwritingNode = this.this$0;
                                if (!stylusHandwritingNode.focused) {
                                    FocusRequesterModifierNodeKt.requestFocus(stylusHandwritingNode);
                                }
                                this.this$0.onHandwritingSlopExceeded.invoke();
                                pointerInputChange4.consume();
                                pointerInputChange2 = pointerInputChange;
                                awaitPointerEventScope3 = awaitPointerEventScope2;
                                PointerEventPass pointerEventPass3 = PointerEventPass.Initial;
                                this.L$0 = awaitPointerEventScope3;
                                this.L$1 = pointerInputChange2;
                                this.L$2 = null;
                                this.label = 3;
                                objAwaitPointerEvent2 = awaitPointerEventScope3.awaitPointerEvent(pointerEventPass3, this);
                            }
                        }
                        pointerInputChange4 = null;
                        if (pointerInputChange4 != null) {
                        }
                    } else {
                        if (i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        pointerInputChange2 = (PointerInputChange) this.L$1;
                        awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        objAwaitPointerEvent2 = obj;
                        List list2 = ((PointerEvent) objAwaitPointerEvent2).changes;
                        int size2 = list2.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size2) {
                                obj3 = null;
                                break;
                            }
                            obj3 = list2.get(i4);
                            PointerInputChange pointerInputChange5 = (PointerInputChange) obj3;
                            if (!pointerInputChange5.isConsumed()) {
                                if (PointerId.m593equalsimpl0(pointerInputChange5.id, pointerInputChange2.id) && pointerInputChange5.pressed) {
                                    break;
                                }
                            }
                            i4++;
                        }
                        PointerInputChange pointerInputChange6 = (PointerInputChange) obj3;
                        if (pointerInputChange6 == null) {
                            return Unit.INSTANCE;
                        }
                        pointerInputChange6.consume();
                        PointerEventPass pointerEventPass32 = PointerEventPass.Initial;
                        this.L$0 = awaitPointerEventScope3;
                        this.L$1 = pointerInputChange2;
                        this.L$2 = null;
                        this.label = 3;
                        objAwaitPointerEvent2 = awaitPointerEventScope3.awaitPointerEvent(pointerEventPass32, this);
                    }
                    pointerInputChange = (PointerInputChange) objAwaitFirstDown;
                    int i5 = pointerInputChange.type;
                    PointerType.Companion.getClass();
                    if (i5 != PointerType.Stylus) {
                        if (pointerInputChange.type != PointerType.Eraser) {
                            return Unit.INSTANCE;
                        }
                    }
                    long j = pointerInputChange.position;
                    int i6 = (int) (j >> 32);
                    if (Float.intBitsToFloat(i6) >= 0.0f && Float.intBitsToFloat(i6) < ((int) (awaitPointerEventScope.mo587getSizeYbymL2g() >> 32))) {
                        int i7 = (int) (j & 4294967295L);
                        if (Float.intBitsToFloat(i7) >= 0.0f && Float.intBitsToFloat(i7) < ((int) (awaitPointerEventScope.mo587getSizeYbymL2g() & 4294967295L))) {
                            z = true;
                        }
                        PointerEventPass pointerEventPass4 = (!this.this$0.focused || z) ? PointerEventPass.Initial : PointerEventPass.Main;
                        awaitPointerEventScope2 = awaitPointerEventScope;
                        pointerEventPass = pointerEventPass4;
                        this.L$0 = awaitPointerEventScope2;
                        this.L$1 = pointerInputChange;
                        this.L$2 = pointerEventPass;
                        this.label = i2;
                        objAwaitPointerEvent = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass, this);
                    }
                    z = false;
                    PointerEventPass pointerEventPass42 = (!this.this$0.focused || z) ? PointerEventPass.Initial : PointerEventPass.Main;
                    awaitPointerEventScope2 = awaitPointerEventScope;
                    pointerEventPass = pointerEventPass42;
                    this.L$0 = awaitPointerEventScope2;
                    this.L$1 = pointerInputChange;
                    this.L$2 = pointerEventPass;
                    this.label = i2;
                    objAwaitPointerEvent = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass, this);
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass1(this.this$0, null), continuation);
                return objAwaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitEachGesture : Unit.INSTANCE;
            }
        });
        delegate(suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode);
        this.suspendingPointerInputModifierNode = suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: getTouchBoundsExpansion-RZrCHBk, reason: not valid java name */
    public final long mo213getTouchBoundsExpansionRZrCHBk() {
        DpTouchBoundsExpansion dpTouchBoundsExpansion = StylusHandwritingKt.HandwritingBoundsExpansion;
        Density density = DelegatableNodeKt.requireLayoutNode(this).density;
        dpTouchBoundsExpansion.getClass();
        TouchBoundsExpansion.Companion companion = TouchBoundsExpansion.Companion;
        int iMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.start);
        int iMo52roundToPx0680j_42 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.top);
        int iMo52roundToPx0680j_43 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.end);
        int iMo52roundToPx0680j_44 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.bottom);
        companion.getClass();
        return TouchBoundsExpansion.Companion.pack$ui_release(dpTouchBoundsExpansion.isLayoutDirectionAware, iMo52roundToPx0680j_4, iMo52roundToPx0680j_42, iMo52roundToPx0680j_43, iMo52roundToPx0680j_44);
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
