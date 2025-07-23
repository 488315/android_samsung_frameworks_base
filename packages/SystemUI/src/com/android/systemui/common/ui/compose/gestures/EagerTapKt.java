package com.android.systemui.common.ui.compose.gestures;

import androidx.compose.ui.input.pointer.PointerInputScope;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1;
import com.android.systemui.qs.panels.ui.compose.selection.MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EagerTapKt {
    public static final Object detectEagerTapGestures(PointerInputScope pointerInputScope, MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0 mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0, MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1 mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1, MutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2 mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new EagerTapKt$detectEagerTapGestures$2(pointerInputScope, mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda0, mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda2, mutableSelectionStateKt$selectableTile$1$1$$ExternalSyntheticLambda1, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
