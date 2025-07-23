package com.android.systemui.communal.data.repository;

import android.appwidget.AppWidgetProviderInfo;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PackageChangeRepository $packageChangeRepository$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryLocalImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1(Continuation continuation, PackageChangeRepository packageChangeRepository, CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl) {
        super(3, continuation);
        this.$packageChangeRepository$inlined = packageChangeRepository;
        this.this$0 = communalWidgetRepositoryLocalImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1 communalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1 = new CommunalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$packageChangeRepository$inlined, this.this$0);
        communalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        communalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return communalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
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
            List<CommunalWidgetRepositoryLocalImpl.CommunalWidgetEntry> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    if (((CommunalWidgetRepositoryLocalImpl.CommunalWidgetEntry) it.next()).providerInfo == null) {
                        final ReadonlyStateFlow readonlyStateFlow = ((PackageChangeRepositoryImpl) this.$packageChangeRepository$inlined).packageInstallSessionsForPrimaryUser;
                        final CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl = this.this$0;
                        flow = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ List $widgetEntries$inlined;
                                public final /* synthetic */ CommunalWidgetRepositoryLocalImpl this$0;

                                /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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

                                public AnonymousClass2(FlowCollector flowCollector, List list, CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.$widgetEntries$inlined = list;
                                    this.this$0 = communalWidgetRepositoryLocalImpl;
                                }

                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Pending] */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r14, kotlin.coroutines.Continuation r15) {
                                    /*
                                        r13 = this;
                                        boolean r0 = r15 instanceof com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r15
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1$2$1
                                        r0.<init>(r15)
                                    L18:
                                        java.lang.Object r15 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L30
                                        if (r2 != r3) goto L28
                                        kotlin.ResultKt.throwOnFailure(r15)
                                        goto Lc1
                                    L28:
                                        java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                                        java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
                                        r13.<init>(r14)
                                        throw r13
                                    L30:
                                        kotlin.ResultKt.throwOnFailure(r15)
                                        java.util.List r14 = (java.util.List) r14
                                        java.util.List r15 = r13.$widgetEntries$inlined
                                        java.lang.Iterable r15 = (java.lang.Iterable) r15
                                        java.util.ArrayList r2 = new java.util.ArrayList
                                        r2.<init>()
                                        java.util.Iterator r15 = r15.iterator()
                                    L42:
                                        boolean r4 = r15.hasNext()
                                        if (r4 == 0) goto Lb6
                                        java.lang.Object r4 = r15.next()
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$CommunalWidgetEntry r4 = (com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl.CommunalWidgetEntry) r4
                                        int r5 = com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl.$r8$clinit
                                        com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl r5 = r13.this$0
                                        r5.getClass()
                                        android.appwidget.AppWidgetProviderInfo r5 = r4.providerInfo
                                        if (r5 == 0) goto L6a
                                        com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Available r5 = new com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Available
                                        android.appwidget.AppWidgetProviderInfo r6 = r4.providerInfo
                                        r6.getClass()
                                        int r7 = r4.spanY
                                        int r8 = r4.appWidgetId
                                        int r4 = r4.rank
                                        r5.<init>(r8, r6, r4, r7)
                                        goto Lb0
                                    L6a:
                                        java.lang.String r5 = r4.componentName
                                        android.content.ComponentName r9 = android.content.ComponentName.unflattenFromString(r5)
                                        r5 = r14
                                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                                        java.util.Iterator r5 = r5.iterator()
                                    L77:
                                        boolean r6 = r5.hasNext()
                                        r7 = 0
                                        if (r6 == 0) goto L96
                                        java.lang.Object r6 = r5.next()
                                        r8 = r6
                                        com.android.systemui.common.shared.model.PackageInstallSession r8 = (com.android.systemui.common.shared.model.PackageInstallSession) r8
                                        java.lang.String r8 = r8.packageName
                                        if (r9 == 0) goto L8e
                                        java.lang.String r10 = r9.getPackageName()
                                        goto L8f
                                    L8e:
                                        r10 = r7
                                    L8f:
                                        boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r10)
                                        if (r8 == 0) goto L77
                                        goto L97
                                    L96:
                                        r6 = r7
                                    L97:
                                        com.android.systemui.common.shared.model.PackageInstallSession r6 = (com.android.systemui.common.shared.model.PackageInstallSession) r6
                                        if (r9 == 0) goto Laf
                                        if (r6 == 0) goto Laf
                                        r5 = r6
                                        com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Pending r6 = new com.android.systemui.communal.shared.model.CommunalWidgetContentModel$Pending
                                        android.graphics.Bitmap r10 = r5.icon
                                        android.os.UserHandle r11 = r5.user
                                        int r12 = r4.spanY
                                        int r7 = r4.appWidgetId
                                        int r8 = r4.rank
                                        r6.<init>(r7, r8, r9, r10, r11, r12)
                                        r5 = r6
                                        goto Lb0
                                    Laf:
                                        r5 = r7
                                    Lb0:
                                        if (r5 == 0) goto L42
                                        r2.add(r5)
                                        goto L42
                                    Lb6:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r13 = r13.$this_unsafeFlow
                                        java.lang.Object r13 = r13.emit(r2, r0)
                                        if (r13 != r1) goto Lc1
                                        return r1
                                    Lc1:
                                        kotlin.Unit r13 = kotlin.Unit.INSTANCE
                                        return r13
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$communalWidgets$lambda$3$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, list, communalWidgetRepositoryLocalImpl), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        };
                        break;
                    }
                }
            }
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (CommunalWidgetRepositoryLocalImpl.CommunalWidgetEntry communalWidgetEntry : list2) {
                CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl2 = this.this$0;
                int i2 = CommunalWidgetRepositoryLocalImpl.$r8$clinit;
                communalWidgetRepositoryLocalImpl2.getClass();
                int i3 = communalWidgetEntry.appWidgetId;
                AppWidgetProviderInfo appWidgetProviderInfo = communalWidgetEntry.providerInfo;
                appWidgetProviderInfo.getClass();
                arrayList.add(new CommunalWidgetContentModel.Available(i3, appWidgetProviderInfo, communalWidgetEntry.rank, communalWidgetEntry.spanY));
            }
            flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(arrayList);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
