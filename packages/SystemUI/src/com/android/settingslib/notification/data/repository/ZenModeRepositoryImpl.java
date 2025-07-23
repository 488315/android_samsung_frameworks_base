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
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            public final Object mo779invoke(Object obj) {
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
                            public final Object mo779invoke(Object obj) {
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
                            public final Object mo779invoke(Object obj) {
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
                            public final Object mo779invoke(Object obj) {
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
                            public final Object mo779invoke(Object obj) {
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
                            public final Object mo779invoke(Object obj) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1$2$1 r0 = (com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1$2$1 r0 = new com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        android.content.Intent r6 = (android.content.Intent) r6
                        java.lang.String r2 = r4.$intentAction$inlined
                        java.lang.String r6 = r6.getAction()
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r6)
                        if (r6 == 0) goto L4c
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, str), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        return FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ZenModeRepositoryImpl$flowFromBroadcast$3(function1, null), new Flow() { // from class: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1$2$1 r0 = (com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1$2$1 r0 = new com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.content.Intent r5 = (android.content.Intent) r5
                        kotlin.jvm.functions.Function1 r6 = r4.$mapper$inlined
                        java.lang.Object r5 = r6.mo779invoke(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl$flowFromBroadcast$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function1), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), this.backgroundCoroutineContext), this.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null);
    }
}
