package androidx.compose.foundation.gestures;

import android.view.MotionEvent;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
final class DragGestureDetectorKt$awaitLongPressOrCancellation$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<PointerInputChange> $currentDown;
    final /* synthetic */ Ref$BooleanRef $deepPress;
    final /* synthetic */ Ref$ObjectRef<PointerInputChange> $longPress;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DragGestureDetectorKt$awaitLongPressOrCancellation$2(Ref$BooleanRef ref$BooleanRef, Ref$ObjectRef<PointerInputChange> ref$ObjectRef, Ref$ObjectRef<PointerInputChange> ref$ObjectRef2, Continuation continuation) {
        super(2, continuation);
        this.$deepPress = ref$BooleanRef;
        this.$currentDown = ref$ObjectRef;
        this.$longPress = ref$ObjectRef2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DragGestureDetectorKt$awaitLongPressOrCancellation$2 dragGestureDetectorKt$awaitLongPressOrCancellation$2 = new DragGestureDetectorKt$awaitLongPressOrCancellation$2(this.$deepPress, this.$currentDown, this.$longPress, continuation);
        dragGestureDetectorKt$awaitLongPressOrCancellation$2.L$0 = obj;
        return dragGestureDetectorKt$awaitLongPressOrCancellation$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DragGestureDetectorKt$awaitLongPressOrCancellation$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x009f, code lost:
    
        r17 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a1, code lost:
    
        r2 = r5 ? 1 : 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00f2 A[EDGE_INSN: B:78:0x00f2->B:52:0x00f2 BREAK  A[LOOP:0: B:47:0x00df->B:51:0x00ef], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x006f A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v13, types: [T, androidx.compose.ui.input.pointer.PointerInputChange] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v7, types: [java.lang.Object] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00d0 -> B:46:0x00d3). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        AwaitPointerEventScope awaitPointerEventScope;
        int i;
        Object objAwaitPointerEvent;
        int size;
        int i2;
        int size2;
        int i3;
        AwaitPointerEventScope awaitPointerEventScope2;
        MotionEvent motionEvent$ui_release;
        Object objAwaitPointerEvent2;
        T t;
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = this.label;
        int i5 = 2;
        boolean z = true;
        z = true;
        z = true;
        Object obj3 = null;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj);
            awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
            i = 0;
            if (i != 0) {
            }
        } else {
            if (i4 == 1) {
                i = this.I$0;
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                objAwaitPointerEvent = obj;
                PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent;
                List list = pointerEvent.changes;
                size = list.size();
                i2 = 0;
                while (true) {
                    if (i2 >= size) {
                    }
                    i2++;
                }
                List list2 = pointerEvent.changes;
                size2 = list2.size();
                i3 = 0;
                while (i3 < size2) {
                }
                awaitPointerEventScope2 = awaitPointerEventScope;
                motionEvent$ui_release = pointerEvent.getMotionEvent$ui_release();
                if ((motionEvent$ui_release == null ? motionEvent$ui_release.getClassification() : 0) != i5 ? z ? 1 : 0 : false) {
                }
                PointerEventPass pointerEventPass = PointerEventPass.Final;
                AwaitPointerEventScope awaitPointerEventScope3 = awaitPointerEventScope2;
                this.L$0 = awaitPointerEventScope3;
                this.L$1 = pointerEvent;
                this.I$0 = i;
                this.label = i5;
                objAwaitPointerEvent2 = awaitPointerEventScope3.awaitPointerEvent(pointerEventPass, this);
                if (objAwaitPointerEvent2 != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$0;
            PointerEvent pointerEvent2 = (PointerEvent) this.L$1;
            AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            objAwaitPointerEvent2 = obj;
            List list3 = ((PointerEvent) objAwaitPointerEvent2).changes;
            int size3 = list3.size();
            int i6 = 0;
            while (true) {
                if (i6 >= size3) {
                    break;
                }
                if (((PointerInputChange) list3.get(i6)).isConsumed()) {
                    i = z ? 1 : 0;
                    break;
                }
                i6++;
            }
            if (!DragGestureDetectorKt.m74isPointerUpDmW0f2w(pointerEvent2, this.$currentDown.element.id)) {
                List list4 = pointerEvent2.changes;
                int size4 = list4.size();
                int i7 = 0;
                while (true) {
                    if (i7 >= size4) {
                        obj2 = null;
                        break;
                    }
                    obj2 = list4.get(i7);
                    if (((PointerInputChange) obj2).pressed) {
                        break;
                    }
                    i7++;
                }
                ?? r10 = (PointerInputChange) obj2;
                if (r10 == 0) {
                    i = z ? 1 : 0;
                    awaitPointerEventScope = awaitPointerEventScope4;
                    obj3 = null;
                    if (i != 0) {
                        return Unit.INSTANCE;
                    }
                    PointerEventPass pointerEventPass2 = PointerEventPass.Main;
                    this.L$0 = awaitPointerEventScope;
                    this.L$1 = obj3;
                    this.I$0 = i;
                    this.label = z ? 1 : 0;
                    objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass2, this);
                    if (objAwaitPointerEvent != coroutineSingletons) {
                        PointerEvent pointerEvent3 = (PointerEvent) objAwaitPointerEvent;
                        List list5 = pointerEvent3.changes;
                        size = list5.size();
                        i2 = 0;
                        while (true) {
                            if (i2 >= size) {
                                i = z ? 1 : 0;
                                break;
                            }
                            if (!PointerEventKt.changedToUpIgnoreConsumed((PointerInputChange) list5.get(i2))) {
                                break;
                            }
                            i2++;
                        }
                        List list22 = pointerEvent3.changes;
                        size2 = list22.size();
                        i3 = 0;
                        while (i3 < size2) {
                            PointerInputChange pointerInputChange = (PointerInputChange) list22.get(i3);
                            if (pointerInputChange.isConsumed()) {
                                break;
                            }
                            awaitPointerEventScope2 = awaitPointerEventScope;
                            if (PointerEventKt.m591isOutOfBoundsjwHxaWs(pointerInputChange, awaitPointerEventScope.mo587getSizeYbymL2g(), awaitPointerEventScope2.mo586getExtendedTouchPaddingNHjbRc())) {
                                break;
                            }
                            i3++;
                            awaitPointerEventScope = awaitPointerEventScope2;
                        }
                        awaitPointerEventScope2 = awaitPointerEventScope;
                        motionEvent$ui_release = pointerEvent3.getMotionEvent$ui_release();
                        if ((motionEvent$ui_release == null ? motionEvent$ui_release.getClassification() : 0) != i5 ? z ? 1 : 0 : false) {
                            this.$deepPress.element = z;
                            i = z ? 1 : 0;
                        }
                        PointerEventPass pointerEventPass3 = PointerEventPass.Final;
                        AwaitPointerEventScope awaitPointerEventScope32 = awaitPointerEventScope2;
                        this.L$0 = awaitPointerEventScope32;
                        this.L$1 = pointerEvent3;
                        this.I$0 = i;
                        this.label = i5;
                        objAwaitPointerEvent2 = awaitPointerEventScope32.awaitPointerEvent(pointerEventPass3, this);
                        if (objAwaitPointerEvent2 != coroutineSingletons) {
                            awaitPointerEventScope4 = awaitPointerEventScope32;
                            pointerEvent2 = pointerEvent3;
                            List list32 = ((PointerEvent) objAwaitPointerEvent2).changes;
                            int size32 = list32.size();
                            int i62 = 0;
                            while (true) {
                                if (i62 >= size32) {
                                }
                                i62++;
                            }
                            if (!DragGestureDetectorKt.m74isPointerUpDmW0f2w(pointerEvent2, this.$currentDown.element.id)) {
                                Ref$ObjectRef<PointerInputChange> ref$ObjectRef = this.$longPress;
                                List list6 = pointerEvent2.changes;
                                Ref$ObjectRef<PointerInputChange> ref$ObjectRef2 = this.$currentDown;
                                int size5 = list6.size();
                                int i8 = 0;
                                while (true) {
                                    if (i8 >= size5) {
                                        t = 0;
                                        break;
                                    }
                                    t = list6.get(i8);
                                    if (PointerId.m593equalsimpl0(((PointerInputChange) t).id, ref$ObjectRef2.element.id)) {
                                        break;
                                    }
                                    i8++;
                                }
                                ref$ObjectRef.element = t;
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                this.$currentDown.element = r10;
                this.$longPress.element = r10;
            }
            awaitPointerEventScope = awaitPointerEventScope4;
            i5 = 2;
            z = true;
            obj3 = null;
            if (i != 0) {
            }
        }
    }
}
