package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScopeKt;

public final class ParallelKt {
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <A, B> java.lang.Object flatMapParallel(java.lang.Iterable<? extends A> r4, kotlin.jvm.functions.Function2 r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof com.android.systemui.util.kotlin.ParallelKt$flatMapParallel$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.util.kotlin.ParallelKt$flatMapParallel$1 r0 = (com.android.systemui.util.kotlin.ParallelKt$flatMapParallel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.ParallelKt$flatMapParallel$1 r0 = new com.android.systemui.util.kotlin.ParallelKt$flatMapParallel$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3b
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r3
            java.lang.Object r6 = mapParallel(r4, r5, r0)
            if (r6 != r1) goto L3b
            return r1
        L3b:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.List r4 = kotlin.collections.CollectionsKt__IterablesKt.flatten(r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.ParallelKt.flatMapParallel(java.lang.Iterable, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <A, B> java.lang.Object mapNotNullParallel(java.lang.Iterable<? extends A> r4, kotlin.jvm.functions.Function2 r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof com.android.systemui.util.kotlin.ParallelKt$mapNotNullParallel$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.util.kotlin.ParallelKt$mapNotNullParallel$1 r0 = (com.android.systemui.util.kotlin.ParallelKt$mapNotNullParallel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.ParallelKt$mapNotNullParallel$1 r0 = new com.android.systemui.util.kotlin.ParallelKt$mapNotNullParallel$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3b
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r3
            java.lang.Object r6 = mapParallel(r4, r5, r0)
            if (r6 != r1) goto L3b
            return r1
        L3b:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.List r4 = kotlin.collections.CollectionsKt___CollectionsKt.filterNotNull(r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.ParallelKt.mapNotNullParallel(java.lang.Iterable, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <A, B> Object mapParallel(Iterable<? extends A> iterable, Function2 function2, Continuation continuation) {
        return CoroutineScopeKt.coroutineScope(new ParallelKt$mapParallel$2(iterable, function2, null), continuation);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <K, A, B> java.lang.Object mapValuesParallel(java.util.Map<K, ? extends A> r4, kotlin.jvm.functions.Function2 r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$1 r0 = (com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$1 r0 = new com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L47
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.Set r4 = r4.entrySet()
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$2 r6 = new com.android.systemui.util.kotlin.ParallelKt$mapValuesParallel$2
            r2 = 0
            r6.<init>(r5, r2)
            r0.label = r3
            java.lang.Object r6 = mapParallel(r4, r6, r0)
            if (r6 != r1) goto L47
            return r1
        L47:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.Map r4 = kotlin.collections.MapsKt__MapsKt.toMap(r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.ParallelKt.mapValuesParallel(java.util.Map, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
