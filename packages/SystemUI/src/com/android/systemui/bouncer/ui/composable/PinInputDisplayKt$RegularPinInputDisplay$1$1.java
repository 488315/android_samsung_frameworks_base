package com.android.systemui.bouncer.ui.composable;

import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
final class PinInputDisplayKt$RegularPinInputDisplay$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PinInputRow $pinInputRow;
    final /* synthetic */ PinBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

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
        /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(PinInputViewModel pinInputViewModel, Continuation continuation) {
            PinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1 pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1;
            CoroutineScope coroutineScope;
            T t;
            AnonymousClass1 anonymousClass1 = this;
            if (continuation instanceof PinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1) {
                pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1 = (PinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1) continuation;
                int i = pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1 = new PinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1(anonymousClass1, continuation);
                }
            }
            Object obj = pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Ref$ObjectRef ref$ObjectRef = anonymousClass1.$currentClearAll;
                List digits = pinInputViewModel.getDigits((EntryToken.ClearAll) ref$ObjectRef.element);
                PinInputRow pinInputRow = anonymousClass1.$pinInputRow;
                pinInputRow.getClass();
                List list = digits;
                SnapshotStateList snapshotStateList = pinInputRow.entries;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(snapshotStateList, 10));
                ListIterator listIterator = snapshotStateList.listIterator();
                while (listIterator.hasNext()) {
                    arrayList.add(((PinInputEntry) listIterator.next()).digit);
                }
                List list2 = CollectionsKt___CollectionsKt.toList(CollectionsKt___CollectionsKt.minus((Iterable) list, (Iterable) CollectionsKt___CollectionsKt.toSet(arrayList)));
                ArrayList arrayList2 = new ArrayList();
                ListIterator listIterator2 = snapshotStateList.listIterator();
                while (listIterator2.hasNext()) {
                    Object next = listIterator2.next();
                    PinInputEntry pinInputEntry = (PinInputEntry) next;
                    if ((list instanceof Collection) && list.isEmpty()) {
                        arrayList2.add(next);
                    } else {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(pinInputEntry.digit, (EntryToken.Digit) it.next())) {
                                break;
                            }
                        }
                        arrayList2.add(next);
                    }
                }
                List list3 = CollectionsKt___CollectionsKt.toList(arrayList2);
                List list4 = list2;
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                Iterator it2 = list4.iterator();
                while (true) {
                    boolean zHasNext = it2.hasNext();
                    coroutineScope = anonymousClass1.$$this$LaunchedEffect;
                    if (!zHasNext) {
                        break;
                    }
                    PinInputEntry pinInputEntry2 = new PinInputEntry((EntryToken.Digit) it2.next(), pinInputRow.shapeAnimations);
                    BuildersKt.launch$default(coroutineScope, null, null, new PinInputRow$updateDigits$1$1$1(pinInputEntry2, null), 3);
                    arrayList3.add(pinInputEntry2);
                }
                snapshotStateList.addAll(arrayList3);
                Iterator it3 = list3.iterator();
                while (it3.hasNext()) {
                    BuildersKt.launch$default(coroutineScope, null, null, new PinInputRow$updateDigits$2$1((PinInputEntry) it3.next(), null), 3);
                }
                CollectionsKt__MutableCollectionsJVMKt.sortWith(snapshotStateList, new Comparator() { // from class: com.android.systemui.bouncer.ui.composable.PinInputRow$updateDigits$$inlined$compareBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        return ComparisonsKt__ComparisonsKt.compareValues(((PinInputEntry) obj2).digit, ((PinInputEntry) obj3).digit);
                    }
                });
                EntryToken.ClearAll clearAllMostRecentClearAll = pinInputViewModel.mostRecentClearAll();
                if (!Intrinsics.areEqual(ref$ObjectRef.element, clearAllMostRecentClearAll)) {
                    pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.L$0 = anonymousClass1;
                    pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.L$1 = clearAllMostRecentClearAll;
                    pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.label = 1;
                    t = clearAllMostRecentClearAll;
                    if (CoroutineScopeKt.coroutineScope(new PinInputRow$playClearAllAnimation$2(pinInputRow, null), pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            EntryToken.ClearAll clearAll = (EntryToken.ClearAll) pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.L$1;
            AnonymousClass1 anonymousClass12 = (AnonymousClass1) pinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1.L$0;
            ResultKt.throwOnFailure(obj);
            t = clearAll;
            anonymousClass1 = anonymousClass12;
            anonymousClass1.$currentClearAll.element = t;
            return Unit.INSTANCE;
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
            ?? MostRecentClearAll = pinInputViewModel.mostRecentClearAll();
            List digits = pinInputViewModel.getDigits(MostRecentClearAll);
            SnapshotStateList snapshotStateList = pinInputRow.entries;
            snapshotStateList.clear();
            List list = digits;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new PinInputEntry((EntryToken.Digit) it.next(), pinInputRow.shapeAnimations));
            }
            snapshotStateList.addAll(arrayList);
            ref$ObjectRef.element = MostRecentClearAll;
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
