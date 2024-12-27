package com.android.systemui.shared.customization.data.content;

import android.content.Context;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomizationProviderClientImpl implements CustomizationProviderClient {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CustomizationProviderClientImpl(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object deleteAllSelections(String str, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new CustomizationProviderClientImpl$deleteAllSelections$2(this, str, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    public final Object insertSelection(String str, String str2, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new CustomizationProviderClientImpl$insertSelection$2(this, str, str2, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1] */
    public final CustomizationProviderClientImpl$observeSelections$$inlined$map$1 observeSelections() {
        CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
        final Flow flowOn = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CustomizationProviderClientImpl$observeUri$2(null), FlowKt.callbackFlow(new CustomizationProviderClientImpl$observeUri$1(this, CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null))), this.backgroundDispatcher);
        return new Flow() { // from class: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CustomizationProviderClientImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, CustomizationProviderClientImpl customizationProviderClientImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = customizationProviderClientImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:19:0x005a A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2$1 r0 = (com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2$1 r0 = new com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5b
                    L2a:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L32:
                        java.lang.Object r5 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L4f
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        kotlinx.coroutines.flow.FlowCollector r6 = r5.$this_unsafeFlow
                        r0.L$0 = r6
                        r0.label = r4
                        com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl r5 = r5.this$0
                        java.lang.Object r7 = r5.querySelections(r0)
                        if (r7 != r1) goto L4e
                        return r1
                    L4e:
                        r5 = r6
                    L4f:
                        r6 = 0
                        r0.L$0 = r6
                        r0.label = r3
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L5b
                        return r1
                    L5b:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object querySelections(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$1 r0 = (com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$1 r0 = new com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$2 r5 = new com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            java.util.List r5 = (java.util.List) r5
            if (r5 != 0) goto L49
            kotlin.collections.EmptyList r5 = kotlin.collections.EmptyList.INSTANCE
        L49:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl.querySelections(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
