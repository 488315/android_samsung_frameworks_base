package com.samsung.sesl.compose.foundation;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.graphics.vector.PathNode;
import com.samsung.sesl.compose.utils.ext.PathNodeExtKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class SeslCheckboxDefaults$RightCheck$PathDataTransition$animatePathData$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $fraction;
    final /* synthetic */ MutableState<List<PathNode>> $pathNodes;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeslCheckboxDefaults$RightCheck$PathDataTransition$animatePathData$1$1(MutableState<List<PathNode>> mutableState, float f, Continuation continuation) {
        super(2, continuation);
        this.$pathNodes = mutableState;
        this.$fraction = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeslCheckboxDefaults$RightCheck$PathDataTransition$animatePathData$1$1(this.$pathNodes, this.$fraction, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeslCheckboxDefaults$RightCheck$PathDataTransition$animatePathData$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState<List<PathNode>> mutableState = this.$pathNodes;
        SeslCheckboxDefaults$RightCheck$PathDataTransition seslCheckboxDefaults$RightCheck$PathDataTransition = SeslCheckboxDefaults$RightCheck$PathDataTransition.INSTANCE;
        seslCheckboxDefaults$RightCheck$PathDataTransition.getClass();
        List list = (List) SeslCheckboxDefaults$RightCheck$PathDataTransition.pathStartNodes$delegate.getValue();
        seslCheckboxDefaults$RightCheck$PathDataTransition.getClass();
        List<Pair> zip = CollectionsKt___CollectionsKt.zip(list, (List) SeslCheckboxDefaults$RightCheck$PathDataTransition.pathEndNodes$delegate.getValue());
        float f = this.$fraction;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(zip, 10));
        for (Pair pair : zip) {
            arrayList.add(PathNodeExtKt.transition((PathNode) pair.component1(), (PathNode) pair.component2(), f));
        }
        mutableState.setValue(arrayList);
        return Unit.INSTANCE;
    }
}
