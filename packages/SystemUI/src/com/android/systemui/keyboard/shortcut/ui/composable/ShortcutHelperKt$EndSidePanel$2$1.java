package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.lazy.LazyListState;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutHelperKt$EndSidePanel$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ShortcutCategoryUi $category;
    final /* synthetic */ LazyListState $listState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperKt$EndSidePanel$2$1(ShortcutCategoryUi shortcutCategoryUi, LazyListState lazyListState, Continuation continuation) {
        super(2, continuation);
        this.$category = shortcutCategoryUi;
        this.$listState = lazyListState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperKt$EndSidePanel$2$1(this.$category, this.$listState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperKt$EndSidePanel$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$category != null) {
                LazyListState lazyListState = this.$listState;
                this.label = 1;
                if (LazyListState.animateScrollToItem$default(lazyListState, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
