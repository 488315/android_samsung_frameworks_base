package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes2.dex */
public final class ModifierExtKt$allowGestures$1 implements PointerInputEventHandler {
    public static final ModifierExtKt$allowGestures$1 INSTANCE = new ModifierExtKt$allowGestures$1();

    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new PointerInputScopeExtKt$consumeAllGestures$2(pointerInputScope, null), continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objCoroutineScope != coroutineSingletons) {
            objCoroutineScope = Unit.INSTANCE;
        }
        return objCoroutineScope == coroutineSingletons ? objCoroutineScope : Unit.INSTANCE;
    }
}
