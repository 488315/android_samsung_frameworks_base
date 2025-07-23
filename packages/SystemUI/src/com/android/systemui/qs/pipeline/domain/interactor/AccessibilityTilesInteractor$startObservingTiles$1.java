package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.Context;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl;
import com.android.systemui.qs.pipeline.domain.interactor.AccessibilityTilesInteractor;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AccessibilityTilesInteractor$startObservingTiles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CurrentTilesInteractor $currentTilesInteractor;
    int label;
    final /* synthetic */ AccessibilityTilesInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AccessibilityTilesInteractor$startObservingTiles$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (List) obj;
            anonymousClass1.L$1 = (Context) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            Context context = (Context) this.L$1;
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((TileModel) it.next()).spec);
            }
            return new AccessibilityTilesInteractor.Data(arrayList, context);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.AccessibilityTilesInteractor$startObservingTiles$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AccessibilityTilesInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AccessibilityTilesInteractor accessibilityTilesInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = accessibilityTilesInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((AccessibilityTilesInteractor.Data) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AccessibilityTilesInteractor.Data data = (AccessibilityTilesInteractor.Data) this.L$0;
                AccessibilityQsShortcutsRepository accessibilityQsShortcutsRepository = this.this$0.a11yQsShortcutsRepository;
                Context context = data.userContext;
                List list = data.currentTileSpecs;
                this.label = 1;
                if (((AccessibilityQsShortcutsRepositoryImpl) accessibilityQsShortcutsRepository).notifyAccessibilityManagerTilesChanged(context, list, this) == coroutineSingletons) {
                    return coroutineSingletons;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityTilesInteractor$startObservingTiles$1(CurrentTilesInteractor currentTilesInteractor, AccessibilityTilesInteractor accessibilityTilesInteractor, Continuation continuation) {
        super(2, continuation);
        this.$currentTilesInteractor = currentTilesInteractor;
        this.this$0 = accessibilityTilesInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AccessibilityTilesInteractor$startObservingTiles$1(this.$currentTilesInteractor, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityTilesInteractor$startObservingTiles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow sample = FlowKt.sample(this.$currentTilesInteractor.getCurrentTiles(), this.$currentTilesInteractor.getUserContext(), new AnonymousClass1(null));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, null);
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.collectLatest(sample, anonymousClass2, this) == coroutineSingletons) {
                return coroutineSingletons;
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
