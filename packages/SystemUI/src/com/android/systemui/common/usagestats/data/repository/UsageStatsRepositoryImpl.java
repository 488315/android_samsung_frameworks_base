package com.android.systemui.common.usagestats.data.repository;

import android.app.usage.UsageEvents;
import android.app.usage.UsageEventsQuery;
import android.app.usage.UsageStatsManager;
import com.android.systemui.common.usagestats.data.model.UsageStatsQuery;
import com.android.systemui.common.usagestats.shared.model.ActivityEventModel;
import java.util.Arrays;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class UsageStatsRepositoryImpl implements UsageStatsRepository {
    public final CoroutineContext bgContext;
    public final UsageStatsManager usageStatsManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UsageStatsRepositoryImpl.this.queryActivityEvents(null, this);
        }
    }

    /* renamed from: com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ UsageStatsQuery $query;
        int label;
        final /* synthetic */ UsageStatsRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UsageStatsQuery usageStatsQuery, UsageStatsRepositoryImpl usageStatsRepositoryImpl, Continuation continuation) {
            super(2, continuation);
            this.$query = usageStatsQuery;
            this.this$0 = usageStatsRepositoryImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$query, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UsageStatsQuery usageStatsQuery = this.$query;
            UsageEventsQuery.Builder builder = new UsageEventsQuery.Builder(usageStatsQuery.startTime, usageStatsQuery.endTime);
            UsageStatsQuery usageStatsQuery2 = this.$query;
            builder.setUserId(usageStatsQuery2.user.getIdentifier());
            builder.setEventTypes(new int[]{1, 2, 23, 24});
            if (!usageStatsQuery2.packageNames.isEmpty()) {
                String[] strArr = (String[]) usageStatsQuery2.packageNames.toArray(new String[0]);
                builder.setPackageNames((String[]) Arrays.copyOf(strArr, strArr.length));
            }
            UsageEvents usageEventsQueryEvents = this.this$0.usageStatsManager.queryEvents(builder.build());
            ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            if (usageEventsQueryEvents != null) {
                UsageEvents.Event event = new UsageEvents.Event();
                while (usageEventsQueryEvents.getNextEvent(event)) {
                    int eventType = event.getEventType();
                    listBuilderCreateListBuilder.add(new ActivityEventModel(event.getInstanceId(), event.getPackageName(), eventType != 1 ? eventType != 2 ? eventType != 23 ? eventType != 24 ? ActivityEventModel.Lifecycle.UNKNOWN : ActivityEventModel.Lifecycle.DESTROYED : ActivityEventModel.Lifecycle.STOPPED : ActivityEventModel.Lifecycle.PAUSED : ActivityEventModel.Lifecycle.RESUMED, event.getTimeStamp()));
                }
            }
            return listBuilderCreateListBuilder.build();
        }
    }

    static {
        new Companion(null);
    }

    public UsageStatsRepositoryImpl(CoroutineContext coroutineContext, UsageStatsManager usageStatsManager) {
        this.bgContext = coroutineContext;
        this.usageStatsManager = usageStatsManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object queryActivityEvents(UsageStatsQuery usageStatsQuery, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(usageStatsQuery, this, null);
        anonymousClass1.label = 1;
        Object objWithContext = BuildersKt.withContext(this.bgContext, anonymousClass2, anonymousClass1);
        return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
    }
}
