package com.android.systemui.communal.data.repository;

import android.appwidget.AppWidgetProviderInfo;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PackageChangeRepository $packageChangeRepository$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, PackageChangeRepository packageChangeRepository, CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl) {
        super(3, continuation);
        this.$packageChangeRepository$inlined = packageChangeRepository;
        this.this$0 = communalWidgetRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1 communalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1 = new CommunalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$packageChangeRepository$inlined, this.this$0);
        communalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        communalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return communalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final List list = (List) this.L$1;
            List<CommunalWidgetRepositoryImpl.CommunalWidgetEntry> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (((CommunalWidgetRepositoryImpl.CommunalWidgetEntry) it.next()).providerInfo == null) {
                        final ReadonlyStateFlow readonlyStateFlow = ((PackageChangeRepositoryImpl) this.$packageChangeRepository$inlined).packageInstallSessionsForPrimaryUser;
                        final CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl = this.this$0;
                        flow = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1

                            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                            /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ List $widgetEntries$inlined;
                                public final /* synthetic */ CommunalWidgetRepositoryImpl this$0;

                                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                                /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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

                                public AnonymousClass2(FlowCollector flowCollector, List list, CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.$widgetEntries$inlined = list;
                                    this.this$0 = communalWidgetRepositoryImpl;
                                }

                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                                /* JADX WARN: Type inference failed for: r8v6, types: [com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Pending] */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                                    /*
                                        r17 = this;
                                        r0 = r17
                                        r1 = r19
                                        boolean r2 = r1 instanceof com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r2 == 0) goto L17
                                        r2 = r1
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1$2$1 r2 = (com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1) r2
                                        int r3 = r2.label
                                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r5 = r3 & r4
                                        if (r5 == 0) goto L17
                                        int r3 = r3 - r4
                                        r2.label = r3
                                        goto L1c
                                    L17:
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1$2$1 r2 = new com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1$2$1
                                        r2.<init>(r1)
                                    L1c:
                                        java.lang.Object r1 = r2.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r4 = r2.label
                                        r5 = 1
                                        if (r4 == 0) goto L34
                                        if (r4 != r5) goto L2c
                                        kotlin.ResultKt.throwOnFailure(r1)
                                        goto Lc0
                                    L2c:
                                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                                        r0.<init>(r1)
                                        throw r0
                                    L34:
                                        kotlin.ResultKt.throwOnFailure(r1)
                                        r1 = r18
                                        java.util.List r1 = (java.util.List) r1
                                        java.util.List r4 = r0.$widgetEntries$inlined
                                        java.lang.Iterable r4 = (java.lang.Iterable) r4
                                        java.util.ArrayList r6 = new java.util.ArrayList
                                        r6.<init>()
                                        java.util.Iterator r4 = r4.iterator()
                                    L48:
                                        boolean r7 = r4.hasNext()
                                        if (r7 == 0) goto Lb5
                                        java.lang.Object r7 = r4.next()
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$CommunalWidgetEntry r7 = (com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl.CommunalWidgetEntry) r7
                                        int r8 = com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl.$r8$clinit
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl r8 = r0.this$0
                                        r8.getClass()
                                        android.appwidget.AppWidgetProviderInfo r8 = r7.providerInfo
                                        if (r8 == 0) goto L69
                                        com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Available r9 = new com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Available
                                        int r10 = r7.appWidgetId
                                        int r7 = r7.priority
                                        r9.<init>(r10, r8, r7)
                                        goto Laf
                                    L69:
                                        r8 = r1
                                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                                        java.util.Iterator r8 = r8.iterator()
                                    L70:
                                        boolean r9 = r8.hasNext()
                                        r10 = 0
                                        if (r9 == 0) goto L95
                                        java.lang.Object r9 = r8.next()
                                        r11 = r9
                                        com.android.systemui.common.shared.model.PackageInstallSession r11 = (com.android.systemui.common.shared.model.PackageInstallSession) r11
                                        java.lang.String r11 = r11.packageName
                                        java.lang.String r12 = r7.componentName
                                        android.content.ComponentName r12 = android.content.ComponentName.unflattenFromString(r12)
                                        if (r12 == 0) goto L8d
                                        java.lang.String r12 = r12.getPackageName()
                                        goto L8e
                                    L8d:
                                        r12 = r10
                                    L8e:
                                        boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual(r11, r12)
                                        if (r11 == 0) goto L70
                                        goto L96
                                    L95:
                                        r9 = r10
                                    L96:
                                        com.android.systemui.common.shared.model.PackageInstallSession r9 = (com.android.systemui.common.shared.model.PackageInstallSession) r9
                                        if (r9 == 0) goto Lae
                                        com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Pending r8 = new com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Pending
                                        android.graphics.Bitmap r15 = r9.icon
                                        android.os.UserHandle r10 = r9.user
                                        java.lang.String r14 = r9.packageName
                                        int r12 = r7.appWidgetId
                                        int r13 = r7.priority
                                        r11 = r8
                                        r16 = r10
                                        r11.<init>(r12, r13, r14, r15, r16)
                                        r9 = r8
                                        goto Laf
                                    Lae:
                                        r9 = r10
                                    Laf:
                                        if (r9 == 0) goto L48
                                        r6.add(r9)
                                        goto L48
                                    Lb5:
                                        r2.label = r5
                                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                                        java.lang.Object r0 = r0.emit(r6, r2)
                                        if (r0 != r3) goto Lc0
                                        return r3
                                    Lc0:
                                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                                        return r0
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$communalWidgets$lambda$3$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, list, communalWidgetRepositoryImpl), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        };
                        break;
                    }
                }
            }
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (CommunalWidgetRepositoryImpl.CommunalWidgetEntry communalWidgetEntry : list2) {
                CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl2 = this.this$0;
                int i2 = CommunalWidgetRepositoryImpl.$r8$clinit;
                communalWidgetRepositoryImpl2.getClass();
                int i3 = communalWidgetEntry.appWidgetId;
                AppWidgetProviderInfo appWidgetProviderInfo = communalWidgetEntry.providerInfo;
                Intrinsics.checkNotNull(appWidgetProviderInfo);
                arrayList.add(new CommunalWidgetContentModel.Available(i3, appWidgetProviderInfo, communalWidgetEntry.priority));
            }
            flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(arrayList);
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
