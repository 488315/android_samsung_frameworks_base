package com.android.systemui.shade.domain.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecPanelSAStatusLogInteractor {
    public final SharedPreferences.Editor editor;
    public long hstnp;
    public long hstqp;
    public long oqpf1d;
    public long oqpf2d;
    public long oqpfhs;
    public long oqpfwd;
    public final SecPanelSAStatusLogRepository repository;

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

    public SecPanelSAStatusLogInteractor(Context context, CoroutineScope coroutineScope) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("QsStatusEventLog_prefs", 0);
        this.editor = sharedPreferences.edit();
        SecPanelSAStatusLogRepository secPanelSAStatusLogRepository = new SecPanelSAStatusLogRepository(sharedPreferences);
        this.repository = secPanelSAStatusLogRepository;
        final Flow[] flowArr = {secPanelSAStatusLogRepository.openQuickPanelFromWipeDown, secPanelSAStatusLogRepository.openQuickPanelFromHorizontalSwiping, secPanelSAStatusLogRepository.horizontalSwipingToQuickPanel, secPanelSAStatusLogRepository.horizontalSwipingToNotificationPanel, secPanelSAStatusLogRepository.openQuickPanelFrom1Depth, secPanelSAStatusLogRepository.openQuickPanelFrom2Depth};
        FlowKt.launchIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$13$$inlined$combine$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$13$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ SecPanelSAStatusLogInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor) {
                    super(3, continuation);
                    this.this$0 = secPanelSAStatusLogInteractor;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        long longValue = ((Number) objArr[5]).longValue();
                        long longValue2 = ((Number) obj6).longValue();
                        long longValue3 = ((Number) obj5).longValue();
                        long longValue4 = ((Number) obj4).longValue();
                        long longValue5 = ((Number) obj3).longValue();
                        Long l = new Long(((Number) obj2).longValue());
                        Long l2 = l.longValue() == this.this$0.oqpfwd ? null : l;
                        if (l2 != null) {
                            long longValue6 = l2.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_wipe_down", longValue6);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.this$0;
                            secPanelSAStatusLogInteractor.oqpfwd = longValue6;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l3 = new Long(longValue5);
                        if (l3.longValue() == this.this$0.oqpfhs) {
                            l3 = null;
                        }
                        if (l3 != null) {
                            long longValue7 = l3.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_horizontal_swiping", longValue7);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.this$0;
                            secPanelSAStatusLogInteractor2.oqpfhs = longValue7;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor2, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l4 = new Long(longValue4);
                        if (l4.longValue() == this.this$0.hstqp) {
                            l4 = null;
                        }
                        if (l4 != null) {
                            long longValue8 = l4.longValue();
                            this.this$0.editor.putLong("horizontal_swiping_to_quick_panel", longValue8);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.this$0;
                            secPanelSAStatusLogInteractor3.hstqp = longValue8;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor3, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l5 = new Long(longValue3);
                        if (l5.longValue() == this.this$0.hstnp) {
                            l5 = null;
                        }
                        if (l5 != null) {
                            long longValue9 = l5.longValue();
                            this.this$0.editor.putLong("horizontal_swiping_to_notification_panel", longValue9);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor4 = this.this$0;
                            secPanelSAStatusLogInteractor4.hstnp = longValue9;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor4, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
                        }
                        Long l6 = new Long(longValue2);
                        if (l6.longValue() == this.this$0.oqpf1d) {
                            l6 = null;
                        }
                        if (l6 != null) {
                            long longValue10 = l6.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_1depth", longValue10);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor5 = this.this$0;
                            secPanelSAStatusLogInteractor5.oqpf1d = longValue10;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor5, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
                        }
                        Long l7 = new Long(longValue);
                        if (l7.longValue() == this.this$0.oqpf2d) {
                            l7 = null;
                        }
                        if (l7 != null) {
                            long longValue11 = l7.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_2depth", longValue11);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor6 = this.this$0;
                            secPanelSAStatusLogInteractor6.oqpf2d = longValue11;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor6, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        this.this$0.editor.apply();
                        Unit unit = Unit.INSTANCE;
                        this.label = 1;
                        if (flowCollector.emit(unit, this) == coroutineSingletons) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$13$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }), coroutineScope);
    }

    public static final void access$updateCurrentScreen(SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor, String str) {
        secPanelSAStatusLogInteractor.getClass();
        SystemUIAnalytics.setCurrentScreenID(str);
    }
}
