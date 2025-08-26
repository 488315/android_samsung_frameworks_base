package com.android.settingslib.notification.data.repository;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import com.android.settingslib.notification.modes.ZenMode;
import com.android.settingslib.notification.modes.ZenModesBackend;
import java.time.Duration;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class ZenModeRepositoryImpl implements ZenModeRepository {
    public final CoroutineScope applicationScope;
    public final ZenModesBackend backend;
    public final CoroutineContext backgroundCoroutineContext;
    public final Handler backgroundHandler;
    public final Lazy consolidatedNotificationPolicy$delegate;
    public final ContentResolver contentResolver;
    public final Context context;
    public final Lazy globalZenMode$delegate;
    public final StateFlowImpl modes;
    public final Lazy notificationBroadcasts$delegate;
    public final NotificationManager notificationManager;

    /* renamed from: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $mapper;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$mapper = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$mapper, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object objMo781invoke = this.$mapper.mo781invoke(null);
                this.label = 1;
                if (flowCollector.emit(objMo781invoke, this) == coroutineSingletons) {
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

    public ZenModeRepositoryImpl(Context context, NotificationManager notificationManager, ZenModesBackend zenModesBackend, ContentResolver contentResolver, CoroutineScope coroutineScope, CoroutineContext coroutineContext, Handler handler) {
        this.context = context;
        this.notificationManager = notificationManager;
        this.backend = zenModesBackend;
        this.contentResolver = contentResolver;
        this.applicationScope = coroutineScope;
        this.backgroundCoroutineContext = coroutineContext;
        this.backgroundHandler = handler;
        final int i = 0;
        this.notificationBroadcasts$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ ZenModeRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        ZenModeRepositoryImpl zenModeRepositoryImpl = this.f$0;
                        return FlowKt.shareIn(FlowKt.flowOn(FlowKt.callbackFlow(new ZenModeRepositoryImpl$notificationBroadcasts$2$1(zenModeRepositoryImpl, null)), zenModeRepositoryImpl.backgroundCoroutineContext), zenModeRepositoryImpl.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
                    case 1:
                        final ZenModeRepositoryImpl zenModeRepositoryImpl2 = this.f$0;
                        final int i2 = 1;
                        return zenModeRepositoryImpl2.flowFromBroadcast("android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED", new Function1() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Bundle extras;
                                NotificationManager.Policy policy;
                                Intent intent = (Intent) obj;
                                switch (i2) {
                                    case 0:
                                        return Integer.valueOf(zenModeRepositoryImpl2.notificationManager.getZenMode());
                                    default:
                                        return (intent == null || (extras = intent.getExtras()) == null || (policy = (NotificationManager.Policy) extras.getParcelable("android.app.extra.NOTIFICATION_POLICY", NotificationManager.Policy.class)) == null) ? zenModeRepositoryImpl2.notificationManager.getConsolidatedNotificationPolicy() : policy;
                                }
                            }
                        });
                    default:
                        final ZenModeRepositoryImpl zenModeRepositoryImpl3 = this.f$0;
                        final int i3 = 0;
                        return zenModeRepositoryImpl3.flowFromBroadcast("android.app.action.INTERRUPTION_FILTER_CHANGED", new Function1() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Bundle extras;
                                NotificationManager.Policy policy;
                                Intent intent = (Intent) obj;
                                switch (i3) {
                                    case 0:
                                        return Integer.valueOf(zenModeRepositoryImpl3.notificationManager.getZenMode());
                                    default:
                                        return (intent == null || (extras = intent.getExtras()) == null || (policy = (NotificationManager.Policy) extras.getParcelable("android.app.extra.NOTIFICATION_POLICY", NotificationManager.Policy.class)) == null) ? zenModeRepositoryImpl3.notificationManager.getConsolidatedNotificationPolicy() : policy;
                                }
                            }
                        });
                }
            }
        });
        final int i2 = 1;
        this.consolidatedNotificationPolicy$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ ZenModeRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        ZenModeRepositoryImpl zenModeRepositoryImpl = this.f$0;
                        return FlowKt.shareIn(FlowKt.flowOn(FlowKt.callbackFlow(new ZenModeRepositoryImpl$notificationBroadcasts$2$1(zenModeRepositoryImpl, null)), zenModeRepositoryImpl.backgroundCoroutineContext), zenModeRepositoryImpl.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
                    case 1:
                        final ZenModeRepositoryImpl zenModeRepositoryImpl2 = this.f$0;
                        final int i22 = 1;
                        return zenModeRepositoryImpl2.flowFromBroadcast("android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED", new Function1() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Bundle extras;
                                NotificationManager.Policy policy;
                                Intent intent = (Intent) obj;
                                switch (i22) {
                                    case 0:
                                        return Integer.valueOf(zenModeRepositoryImpl2.notificationManager.getZenMode());
                                    default:
                                        return (intent == null || (extras = intent.getExtras()) == null || (policy = (NotificationManager.Policy) extras.getParcelable("android.app.extra.NOTIFICATION_POLICY", NotificationManager.Policy.class)) == null) ? zenModeRepositoryImpl2.notificationManager.getConsolidatedNotificationPolicy() : policy;
                                }
                            }
                        });
                    default:
                        final ZenModeRepositoryImpl zenModeRepositoryImpl3 = this.f$0;
                        final int i3 = 0;
                        return zenModeRepositoryImpl3.flowFromBroadcast("android.app.action.INTERRUPTION_FILTER_CHANGED", new Function1() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Bundle extras;
                                NotificationManager.Policy policy;
                                Intent intent = (Intent) obj;
                                switch (i3) {
                                    case 0:
                                        return Integer.valueOf(zenModeRepositoryImpl3.notificationManager.getZenMode());
                                    default:
                                        return (intent == null || (extras = intent.getExtras()) == null || (policy = (NotificationManager.Policy) extras.getParcelable("android.app.extra.NOTIFICATION_POLICY", NotificationManager.Policy.class)) == null) ? zenModeRepositoryImpl3.notificationManager.getConsolidatedNotificationPolicy() : policy;
                                }
                            }
                        });
                }
            }
        });
        final int i3 = 2;
        this.globalZenMode$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ ZenModeRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        ZenModeRepositoryImpl zenModeRepositoryImpl = this.f$0;
                        return FlowKt.shareIn(FlowKt.flowOn(FlowKt.callbackFlow(new ZenModeRepositoryImpl$notificationBroadcasts$2$1(zenModeRepositoryImpl, null)), zenModeRepositoryImpl.backgroundCoroutineContext), zenModeRepositoryImpl.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
                    case 1:
                        final ZenModeRepositoryImpl zenModeRepositoryImpl2 = this.f$0;
                        final int i22 = 1;
                        return zenModeRepositoryImpl2.flowFromBroadcast("android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED", new Function1() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Bundle extras;
                                NotificationManager.Policy policy;
                                Intent intent = (Intent) obj;
                                switch (i22) {
                                    case 0:
                                        return Integer.valueOf(zenModeRepositoryImpl2.notificationManager.getZenMode());
                                    default:
                                        return (intent == null || (extras = intent.getExtras()) == null || (policy = (NotificationManager.Policy) extras.getParcelable("android.app.extra.NOTIFICATION_POLICY", NotificationManager.Policy.class)) == null) ? zenModeRepositoryImpl2.notificationManager.getConsolidatedNotificationPolicy() : policy;
                                }
                            }
                        });
                    default:
                        final ZenModeRepositoryImpl zenModeRepositoryImpl3 = this.f$0;
                        final int i32 = 0;
                        return zenModeRepositoryImpl3.flowFromBroadcast("android.app.action.INTERRUPTION_FILTER_CHANGED", new Function1() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$$ExternalSyntheticLambda4
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Bundle extras;
                                NotificationManager.Policy policy;
                                Intent intent = (Intent) obj;
                                switch (i32) {
                                    case 0:
                                        return Integer.valueOf(zenModeRepositoryImpl3.notificationManager.getZenMode());
                                    default:
                                        return (intent == null || (extras = intent.getExtras()) == null || (policy = (NotificationManager.Policy) extras.getParcelable("android.app.extra.NOTIFICATION_POLICY", NotificationManager.Policy.class)) == null) ? zenModeRepositoryImpl3.notificationManager.getConsolidatedNotificationPolicy() : policy;
                                }
                            }
                        });
                }
            }
        });
        LazyKt__LazyJVMKt.lazy(new ZenModeRepositoryImpl$$ExternalSyntheticLambda3());
        this.modes = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
    }

    public final void activateMode(ZenMode zenMode, Duration duration) {
        ZenModesBackend zenModesBackend = this.backend;
        zenModesBackend.getClass();
        if (zenMode.isManualDnd()) {
            zenModesBackend.mNotificationManager.setZenMode(1, duration != null ? ZenModeConfig.toTimeCondition(zenModesBackend.mContext, (int) duration.toMinutes(), ActivityManager.getCurrentUser(), true).id : null, "ZenModeBackend", true);
        } else {
            if (duration != null) {
                throw new IllegalArgumentException("Only the manual DND mode can be activated for a specific duration");
            }
            zenModesBackend.mNotificationManager.setAutomaticZenRuleState(zenMode.mId, new Condition(zenMode.mRule.getConditionId(), "", 1, 1));
        }
    }

    public final ReadonlyStateFlow flowFromBroadcast(final String str, final Function1 function1) {
        final SharedFlow sharedFlow = (SharedFlow) this.notificationBroadcasts$delegate.getValue();
        final Flow flow = new Flow() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1

            /* renamed from: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ String $intentAction$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, String str) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$intentAction$inlined = str;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
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
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (Intrinsics.areEqual(this.$intentAction$inlined, ((Intent) obj).getAction())) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = sharedFlow.collect(new AnonymousClass2(flowCollector, str), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        return FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass3(function1, null), new Flow() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1

            /* renamed from: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $mapper$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Function1 function1) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$mapper$inlined = function1;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
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
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Object objMo781invoke = this.$mapper$inlined.mo781invoke((Intent) obj);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objMo781invoke, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, function1), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), this.backgroundCoroutineContext), this.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null);
    }
}
