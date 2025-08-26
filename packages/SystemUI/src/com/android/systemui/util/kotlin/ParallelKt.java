package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AwaitAll;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Deferred;

/* loaded from: classes3.dex */
public final class ParallelKt {

    /* renamed from: com.android.systemui.util.kotlin.ParallelKt$flatMapParallel$1, reason: invalid class name */
    final class AnonymousClass1<A, B> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ParallelKt.flatMapParallel(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.ParallelKt$mapNotNullParallel$1, reason: invalid class name and case insensitive filesystem */
    final class C11671<A, B> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C11671(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ParallelKt.mapNotNullParallel(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.ParallelKt$mapParallel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Iterable<Object> $this_mapParallel;
        final /* synthetic */ Function2 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Iterable<Object> iterable, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_mapParallel = iterable;
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_mapParallel, this.$transform, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Iterable<Object> iterable = this.$this_mapParallel;
            Function2 function2 = this.$transform;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator<Object> it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(CoroutineTracingKt.asyncTraced$default(coroutineScope, null, CoroutineStart.LAZY, new ParallelKt$mapParallel$2$1$1(function2, it.next(), null), 3));
            }
            this.label = 1;
            Object objAwait = arrayList.isEmpty() ? EmptyList.INSTANCE : new AwaitAll((Deferred[]) arrayList.toArray(new Deferred[0])).await(this);
            return objAwait == coroutineSingletons ? coroutineSingletons : objAwait;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$1, reason: invalid class name and case insensitive filesystem */
    final class C11681<K, A, B> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C11681(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ParallelKt.mapValuesParallel(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$2, reason: invalid class name and case insensitive filesystem */
    final class C11692 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $transform;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11692(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C11692 c11692 = new C11692(this.$transform, continuation);
            c11692.L$0 = obj;
            return c11692;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Map.Entry entry = (Map.Entry) this.L$0;
                Object key = entry.getKey();
                Function2 function2 = this.$transform;
                this.L$0 = key;
                this.label = 1;
                obj = function2.invoke(entry, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj2 = key;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                obj2 = this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            return new Pair(obj2, obj);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Map.Entry<Object, Object> entry, Continuation continuation) {
            return ((C11692) create(entry, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final <A, B> Object flatMapParallel(Iterable<? extends A> iterable, Function2 function2, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objMapParallel = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMapParallel);
            anonymousClass1.label = 1;
            objMapParallel = mapParallel(iterable, function2, anonymousClass1);
            if (objMapParallel == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objMapParallel);
        }
        return CollectionsKt__IterablesKt.flatten((Iterable) objMapParallel);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final <A, B> Object mapNotNullParallel(Iterable<? extends A> iterable, Function2 function2, Continuation continuation) {
        C11671 c11671;
        if (continuation instanceof C11671) {
            c11671 = (C11671) continuation;
            int i = c11671.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11671.label = i - Integer.MIN_VALUE;
            } else {
                c11671 = new C11671(continuation);
            }
        }
        Object objMapParallel = c11671.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11671.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMapParallel);
            c11671.label = 1;
            objMapParallel = mapParallel(iterable, function2, c11671);
            if (objMapParallel == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objMapParallel);
        }
        return CollectionsKt___CollectionsKt.filterNotNull((Iterable) objMapParallel);
    }

    public static final <A, B> Object mapParallel(Iterable<? extends A> iterable, Function2 function2, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new AnonymousClass2(iterable, function2, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final <K, A, B> Object mapValuesParallel(Map<K, ? extends A> map, Function2 function2, Continuation continuation) {
        C11681 c11681;
        if (continuation instanceof C11681) {
            c11681 = (C11681) continuation;
            int i = c11681.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c11681.label = i - Integer.MIN_VALUE;
            } else {
                c11681 = new C11681(continuation);
            }
        }
        Object objMapParallel = c11681.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c11681.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMapParallel);
            Set<Map.Entry<K, ? extends A>> setEntrySet = map.entrySet();
            C11692 c11692 = new C11692(function2, null);
            c11681.label = 1;
            objMapParallel = mapParallel(setEntrySet, c11692, c11681);
            if (objMapParallel == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objMapParallel);
        }
        return MapsKt__MapsKt.toMap((Iterable) objMapParallel);
    }
}
