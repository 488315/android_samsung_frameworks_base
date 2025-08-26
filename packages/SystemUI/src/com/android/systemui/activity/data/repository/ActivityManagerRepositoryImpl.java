package com.android.systemui.activity.data.repository;

import android.app.ActivityManager;
import com.android.systemui.activity.data.model.AppVisibilityModel;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;

/* loaded from: classes.dex */
public final class ActivityManagerRepositoryImpl implements ActivityManagerRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityManager activityManager;
    public final CoroutineContext backgroundContext;
    public final SystemClock systemClock;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createAppVisibilityFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $identifyingLogTag;
        final /* synthetic */ Logger $logger;
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Logger logger, String str, Continuation continuation) {
            super(3, continuation);
            this.$logger = logger;
            this.$identifyingLogTag = str;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = ActivityManagerRepositoryImpl.this.new AnonymousClass1(this.$logger, this.$identifyingLogTag, (Continuation) obj3);
            anonymousClass1.L$0 = (AppVisibilityModel) obj;
            anonymousClass1.Z$0 = zBooleanValue;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AppVisibilityModel appVisibilityModel = (AppVisibilityModel) this.L$0;
            if (!this.Z$0) {
                Long l = appVisibilityModel.lastAppVisibleTime;
                appVisibilityModel.getClass();
                return new AppVisibilityModel(false, l);
            }
            long jCurrentTimeMillis = ActivityManagerRepositoryImpl.this.systemClock.currentTimeMillis();
            Logger logger = this.$logger;
            ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(1);
            String str = this.$identifyingLogTag;
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(str);
            logMessageObtain.setLong1(jCurrentTimeMillis);
            logger.getBuffer().commit(logMessageObtain);
            return new AppVisibilityModel(true, new Long(jCurrentTimeMillis));
        }
    }

    /* renamed from: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1, reason: invalid class name and case insensitive filesystem */
    final class C07861 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $creationUid;
        final /* synthetic */ String $identifyingLogTag;
        final /* synthetic */ Logger $logger;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07861(Logger logger, int i, String str, Continuation continuation) {
            super(2, continuation);
            this.$logger = logger;
            this.$creationUid = i;
            this.$identifyingLogTag = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C07861 c07861 = ActivityManagerRepositoryImpl.this.new C07861(this.$logger, this.$creationUid, this.$identifyingLogTag, continuation);
            c07861.L$0 = obj;
            return c07861;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07861) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v0, types: [android.app.ActivityManager$OnUidImportanceListener, com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$listener$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final int i2 = this.$creationUid;
                final ActivityManagerRepositoryImpl activityManagerRepositoryImpl = ActivityManagerRepositoryImpl.this;
                final Logger logger = this.$logger;
                final String str = this.$identifyingLogTag;
                final ?? r3 = new ActivityManager.OnUidImportanceListener() { // from class: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$listener$1
                    public final void onUidImportance(int i3, int i4) {
                        if (i3 != i2) {
                            return;
                        }
                        ActivityManagerRepositoryImpl activityManagerRepositoryImpl2 = activityManagerRepositoryImpl;
                        int i5 = ActivityManagerRepositoryImpl.$r8$clinit;
                        activityManagerRepositoryImpl2.getClass();
                        boolean z = i4 <= 100;
                        Logger logger2 = logger;
                        ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(2);
                        String str2 = str;
                        LogMessage logMessageObtain = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.DEBUG, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, null);
                        logMessageObtain.setStr1(str2);
                        logMessageObtain.setInt1(i4);
                        logMessageObtain.setBool1(z);
                        logger2.getBuffer().commit(logMessageObtain);
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                    }
                };
                try {
                    ActivityManagerRepositoryImpl.this.activityManager.addOnUidImportanceListener(r3, 100);
                } catch (SecurityException e) {
                    Logger logger2 = this.$logger;
                    ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(0);
                    String str2 = this.$identifyingLogTag;
                    LogMessage logMessageObtain = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.ERROR, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, e);
                    logMessageObtain.setStr1(str2);
                    logger2.getBuffer().commit(logMessageObtain);
                }
                final ActivityManagerRepositoryImpl activityManagerRepositoryImpl2 = ActivityManagerRepositoryImpl.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        activityManagerRepositoryImpl2.activityManager.removeOnUidImportanceListener(r3);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $creationUid;
        final /* synthetic */ String $identifyingLogTag;
        final /* synthetic */ Logger $logger;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Logger logger, String str, Continuation continuation) {
            super(2, continuation);
            this.$creationUid = i;
            this.$logger = logger;
            this.$identifyingLogTag = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ActivityManagerRepositoryImpl.this.new AnonymousClass2(this.$creationUid, this.$logger, this.$identifyingLogTag, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x006f, code lost:
        
            if (r11 == r0) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x009e, code lost:
        
            if (r1.emit(r12, r11) != r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00a0, code lost:
        
            return r0;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2, kotlin.coroutines.Continuation] */
        /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2, kotlin.coroutines.Continuation] */
        /* JADX WARN: Type inference failed for: r11v5 */
        /* JADX WARN: Type inference failed for: r11v7 */
        /* JADX WARN: Type inference failed for: r11v8 */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v1, types: [kotlinx.coroutines.flow.FlowCollector] */
        /* JADX WARN: Type inference failed for: r1v4 */
        /* JADX WARN: Type inference failed for: r1v7 */
        /* JADX WARN: Type inference failed for: r1v8 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            ?? r1 = this.label;
            try {
            } catch (SecurityException e) {
                Logger logger = this.$logger;
                ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(4);
                String str = this.$identifyingLogTag;
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, e);
                logMessageObtain.setStr1(str);
                logger.getBuffer().commit(logMessageObtain);
                Boolean bool = Boolean.FALSE;
                this.L$0 = null;
                this.label = 2;
            }
            if (r1 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                boolean z = ActivityManagerRepositoryImpl.this.activityManager.getUidImportance(this.$creationUid) <= 100;
                Logger logger2 = this.$logger;
                ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda02 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(3);
                String str2 = this.$identifyingLogTag;
                LogMessage logMessageObtain2 = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.DEBUG, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda02, null);
                logMessageObtain2.setStr1(str2);
                logMessageObtain2.setBool1(z);
                logger2.getBuffer().commit(logMessageObtain2);
                Boolean boolValueOf = Boolean.valueOf(z);
                this.L$0 = flowCollector;
                this.label = 1;
                Object objEmit = flowCollector.emit(boolValueOf, this);
                r1 = flowCollector;
                this = objEmit;
            } else {
                if (r1 != 1) {
                    if (r1 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                FlowCollector flowCollector2 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                r1 = flowCollector2;
                this = this;
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public ActivityManagerRepositoryImpl(CoroutineContext coroutineContext, SystemClock systemClock, ActivityManager activityManager) {
        this.backgroundContext = coroutineContext;
        this.systemClock = systemClock;
        this.activityManager = activityManager;
    }

    public final Flow createAppVisibilityFlow(int i, Logger logger, String str) {
        return FlowKt.distinctUntilChanged(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(new AppVisibilityModel(false, null, 3, null), FlowKt.distinctUntilChanged(createIsAppVisibleFlow(i, logger, str)), new AnonymousClass1(logger, str, null)));
    }

    public final Flow createIsAppVisibleFlow(int i, Logger logger, String str) {
        return FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(i, logger, str, null), FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new C07861(logger, i, str, null)))), this.backgroundContext);
    }
}
