package com.android.systemui.communal;

import android.app.StatsManager;
import android.util.StatsEvent;
import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.log.CommunalMetricsLogger;
import com.android.systemui.communal.shared.log.CommunalStatsLogProxyImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class CommunalMetricsStartable implements CoreStartable, StatsManager.StatsPullAtomCallback {
    public final Executor bgExecutor;
    public final CommunalInteractor communalInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final CommunalMetricsLogger metricsLogger;
    public final StatsManager statsManager;

    /* renamed from: com.android.systemui.communal.CommunalMetricsStartable$onPullAtom$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalMetricsStartable.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = CommunalMetricsStartable.this.communalInteractor.widgetContent;
                this.label = 1;
                obj = FlowKt.first(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Iterable iterable = (Iterable) obj;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(((CommunalContentModel.WidgetContent) it.next()).getComponentName().flattenToString());
            }
            return arrayList;
        }
    }

    public CommunalMetricsStartable(Executor executor, CommunalSettingsInteractor communalSettingsInteractor, CommunalInteractor communalInteractor, StatsManager statsManager, CommunalMetricsLogger communalMetricsLogger) {
        this.bgExecutor = executor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalInteractor = communalInteractor;
        this.statsManager = statsManager;
        this.metricsLogger = communalMetricsLogger;
    }

    public final int onPullAtom(int i, List list) {
        if (i != 10226) {
            return 1;
        }
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        List list2 = (List) BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
        communalMetricsLogger.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (communalMetricsLogger.isLoggable((String) obj)) {
                arrayList.add(obj);
            }
        }
        String[] strArr = (String[]) arrayList.toArray(new String[0]);
        int size = list2.size();
        ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).getClass();
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(10226);
        if (strArr == null) {
            strArr = new String[0];
        }
        builderNewBuilder.writeStringArray(strArr);
        builderNewBuilder.writeInt(size);
        list.add(builderNewBuilder.build());
        return 0;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (this.communalSettingsInteractor.isCommunalFlagEnabled()) {
            this.statsManager.setPullAtomCallback(10226, (StatsManager.PullAtomMetadata) null, this.bgExecutor, this);
        }
    }
}
