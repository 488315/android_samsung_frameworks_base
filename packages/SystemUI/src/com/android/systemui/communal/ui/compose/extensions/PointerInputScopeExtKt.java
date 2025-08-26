package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes2.dex */
public abstract class PointerInputScopeExtKt {
    /* JADX WARN: Removed duplicated region for block: B:17:0x0049 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0059 A[LOOP:0: B:19:0x0057->B:20:0x0059, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0047 -> B:18:0x004a). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$consumeUntilUp(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, BaseContinuationImpl baseContinuationImpl) {
        PointerInputScopeExtKt$consumeUntilUp$1 pointerInputScopeExtKt$consumeUntilUp$1;
        int size;
        int i;
        int i2;
        int size2;
        if (baseContinuationImpl instanceof PointerInputScopeExtKt$consumeUntilUp$1) {
            pointerInputScopeExtKt$consumeUntilUp$1 = (PointerInputScopeExtKt$consumeUntilUp$1) baseContinuationImpl;
            int i3 = pointerInputScopeExtKt$consumeUntilUp$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                pointerInputScopeExtKt$consumeUntilUp$1.label = i3 - Integer.MIN_VALUE;
            } else {
                pointerInputScopeExtKt$consumeUntilUp$1 = new PointerInputScopeExtKt$consumeUntilUp$1(baseContinuationImpl);
            }
        }
        Object objAwaitPointerEvent = pointerInputScopeExtKt$consumeUntilUp$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = pointerInputScopeExtKt$consumeUntilUp$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objAwaitPointerEvent);
            pointerInputScopeExtKt$consumeUntilUp$1.L$0 = awaitPointerEventScope;
            pointerInputScopeExtKt$consumeUntilUp$1.L$1 = pointerEventPass;
            pointerInputScopeExtKt$consumeUntilUp$1.label = 1;
            objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, pointerInputScopeExtKt$consumeUntilUp$1);
            if (objAwaitPointerEvent == coroutineSingletons) {
            }
            PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent;
            List list = pointerEvent.changes;
            size = list.size();
            i = 0;
            while (i2 < size) {
            }
            List list2 = pointerEvent.changes;
            size2 = list2.size();
            while (i < size2) {
            }
            return Unit.INSTANCE;
        }
        if (i4 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        PointerEventPass pointerEventPass2 = (PointerEventPass) pointerInputScopeExtKt$consumeUntilUp$1.L$1;
        AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) pointerInputScopeExtKt$consumeUntilUp$1.L$0;
        ResultKt.throwOnFailure(objAwaitPointerEvent);
        pointerEventPass = pointerEventPass2;
        awaitPointerEventScope = awaitPointerEventScope2;
        PointerEvent pointerEvent2 = (PointerEvent) objAwaitPointerEvent;
        List list3 = pointerEvent2.changes;
        size = list3.size();
        i = 0;
        for (i2 = 0; i2 < size; i2++) {
            ((PointerInputChange) list3.get(i2)).consume();
        }
        List list22 = pointerEvent2.changes;
        size2 = list22.size();
        while (i < size2) {
            if (((PointerInputChange) list22.get(i)).pressed) {
                pointerInputScopeExtKt$consumeUntilUp$1.L$0 = awaitPointerEventScope;
                pointerInputScopeExtKt$consumeUntilUp$1.L$1 = pointerEventPass;
                pointerInputScopeExtKt$consumeUntilUp$1.label = 1;
                objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, pointerInputScopeExtKt$consumeUntilUp$1);
                if (objAwaitPointerEvent == coroutineSingletons) {
                    return coroutineSingletons;
                }
                PointerEvent pointerEvent22 = (PointerEvent) objAwaitPointerEvent;
                List list32 = pointerEvent22.changes;
                size = list32.size();
                i = 0;
                while (i2 < size) {
                }
                List list222 = pointerEvent22.changes;
                size2 = list222.size();
                while (i < size2) {
                }
            } else {
                i++;
            }
        }
        return Unit.INSTANCE;
    }

    public static Object detectLongPressGesture$default(PointerInputScope pointerInputScope, Function1 function1, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new PointerInputScopeExtKt$detectLongPressGesture$2(pointerInputScope, PointerEventPass.Initial, function1, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public static Object observeTaps$default(PointerInputScope pointerInputScope, Function1 function1, Continuation continuation, int i) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new PointerInputScopeExtKt$observeTaps$2(function1, pointerInputScope, PointerEventPass.Initial, (i & 2) == 0, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }
}
