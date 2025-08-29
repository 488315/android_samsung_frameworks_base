package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class NotificationStatsLoggerBinder$bindLogger$6 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationStatsLogger $logger;
    final /* synthetic */ NotificationStackScrollLayout $view;
    final /* synthetic */ NotificationLoggerViewModel $viewModel;
    /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$6$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair((Callable) obj, (Map) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationStatsLoggerBinder$bindLogger$6(NotificationStatsLogger notificationStatsLogger, NotificationStackScrollLayout notificationStackScrollLayout, NotificationLoggerViewModel notificationLoggerViewModel, Continuation continuation) {
        super(2, continuation);
        this.$logger = notificationStatsLogger;
        this.$view = notificationStackScrollLayout;
        this.$viewModel = notificationLoggerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationStatsLoggerBinder$bindLogger$6 notificationStatsLoggerBinder$bindLogger$6 = new NotificationStatsLoggerBinder$bindLogger$6(this.$logger, this.$view, this.$viewModel, continuation);
        notificationStatsLoggerBinder$bindLogger$6.L$0 = obj;
        return notificationStatsLoggerBinder$bindLogger$6;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationStatsLoggerBinder$bindLogger$6) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Triple triple = (Triple) this.L$0;
            boolean zBooleanValue = ((Boolean) triple.component1()).booleanValue();
            boolean zBooleanValue2 = ((Boolean) triple.component2()).booleanValue();
            List list = (List) triple.component3();
            if (zBooleanValue) {
                ((NotificationStatsLoggerImpl) this.$logger).onLockscreenOrShadeInteractive(list, zBooleanValue2);
                NotificationStackScrollLayout notificationStackScrollLayout = this.$view;
                ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
                NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1 notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1 = new NotificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1(notificationStackScrollLayout, null);
                conflatedCallbackFlow.getClass();
                Flow flowSample = FlowKt.sample(FlowKt.throttle$default(FlowConflatedKt.conflatedCallbackFlow(notificationStatsLoggerBinderKt$onNotificationLocationsUpdated$1), 500L, null, 2, null), this.$viewModel.activeNotificationRanks, AnonymousClass3.INSTANCE);
                final NotificationStatsLogger notificationStatsLogger = this.$logger;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$6.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        Callable callable = (Callable) pair.component1();
                        Map map = (Map) pair.component2();
                        callable.getClass();
                        BufferedChannel bufferedChannel = ((NotificationStatsLoggerImpl) notificationStatsLogger).visibilityLogger;
                        Map map2 = (Map) callable.call();
                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map2.size()));
                        for (Map.Entry entry : map2.entrySet()) {
                            Object key = entry.getKey();
                            String str = (String) entry.getKey();
                            int iIntValue = ((Number) entry.getValue()).intValue();
                            Integer num = (Integer) map.get(entry.getKey());
                            linkedHashMap.put(key, new NotificationStatsLoggerImpl.VisibilityState(str, iIntValue, num != null ? num.intValue() : -1));
                        }
                        bufferedChannel.mo3475trySendJP2dKIU(new NotificationStatsLoggerImpl.VisibilityAction.Change(linkedHashMap, map.size()));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowSample.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                ((NotificationStatsLoggerImpl) this.$logger).visibilityLogger.mo3475trySendJP2dKIU(new NotificationStatsLoggerImpl.VisibilityAction.Clear(list.size()));
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
