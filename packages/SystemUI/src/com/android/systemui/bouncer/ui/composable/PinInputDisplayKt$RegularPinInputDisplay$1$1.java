package com.android.systemui.bouncer.ui.composable;

import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PinInputDisplayKt$RegularPinInputDisplay$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PinInputRow $pinInputRow;
    final /* synthetic */ PinBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$RegularPinInputDisplay$1$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ CoroutineScope $$this$LaunchedEffect;
        public final /* synthetic */ Ref$ObjectRef $currentClearAll;
        public final /* synthetic */ PinInputRow $pinInputRow;

        public AnonymousClass1(PinInputRow pinInputRow, Ref$ObjectRef<EntryToken.ClearAll> ref$ObjectRef, CoroutineScope coroutineScope) {
            this.$pinInputRow = pinInputRow;
            this.$currentClearAll = ref$ObjectRef;
            this.$$this$LaunchedEffect = coroutineScope;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:16:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel r18, kotlin.coroutines.Continuation r19) {
            /*
                Method dump skipped, instructions count: 341
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$RegularPinInputDisplay$1$1.AnonymousClass1.emit(com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputDisplayKt$RegularPinInputDisplay$1$1(PinBouncerViewModel pinBouncerViewModel, PinInputRow pinInputRow, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = pinBouncerViewModel;
        this.$pinInputRow = pinInputRow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PinInputDisplayKt$RegularPinInputDisplay$1$1 pinInputDisplayKt$RegularPinInputDisplay$1$1 = new PinInputDisplayKt$RegularPinInputDisplay$1$1(this.$viewModel, this.$pinInputRow, continuation);
        pinInputDisplayKt$RegularPinInputDisplay$1$1.L$0 = obj;
        return pinInputDisplayKt$RegularPinInputDisplay$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinInputDisplayKt$RegularPinInputDisplay$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [T, com.android.systemui.bouncer.ui.viewmodel.EntryToken$ClearAll] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            Object value = this.$viewModel.pinInput.getValue();
            PinInputRow pinInputRow = this.$pinInputRow;
            PinInputViewModel pinInputViewModel = (PinInputViewModel) value;
            ?? mostRecentClearAll = pinInputViewModel.mostRecentClearAll();
            List digits = pinInputViewModel.getDigits(mostRecentClearAll);
            SnapshotStateList snapshotStateList = pinInputRow.entries;
            snapshotStateList.clear();
            List list = digits;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new PinInputEntry((EntryToken.Digit) it.next(), pinInputRow.shapeAnimations));
            }
            snapshotStateList.addAll(arrayList);
            ref$ObjectRef.element = mostRecentClearAll;
            StateFlowImpl stateFlowImpl = this.$viewModel.pinInput;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$pinInputRow, ref$ObjectRef, coroutineScope);
            this.label = 1;
            if (stateFlowImpl.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
