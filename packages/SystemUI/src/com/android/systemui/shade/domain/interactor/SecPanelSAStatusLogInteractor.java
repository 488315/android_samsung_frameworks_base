package com.android.systemui.shade.domain.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.data.repository.SecPanelSAStatusLogRepository;
import com.android.systemui.shade.data.repository.SecQSExpansionStateRepository;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes3.dex */
public final class SecPanelSAStatusLogInteractor {
    public final SharedPreferences.Editor editor;
    public long onpfetc;
    public long onpfh;
    public long onpfhun;
    public long onpfk;
    public long onpfsdk;
    public long onpfsds;
    public long onpfsk;
    public long onpfsrk;
    public long onpfsrs;
    public long onpfss;
    public long oqpf1dek;
    public long oqpf1des;
    public long oqpf1dsk;
    public long oqpf1dss;
    public long oqpf2d;
    public long oqpf2d2f;
    public long oqpfhsk;
    public long oqpfhss;
    public long oqpfsk;
    public long oqpfss;
    public long oqpfwdk;
    public long oqpfwds;
    public final SecPanelSAStatusLogRepository repository;

    /* renamed from: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ SecQSExpansionStateInteractor $qsExpansionStateInteractor;
        int label;
        final /* synthetic */ SecPanelSAStatusLogInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(SecQSExpansionStateInteractor secQSExpansionStateInteractor, SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor, Continuation continuation) {
            super(2, continuation);
            this.$qsExpansionStateInteractor = secQSExpansionStateInteractor;
            this.this$0 = secPanelSAStatusLogInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$qsExpansionStateInteractor, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SecQSExpansionStateRepository repository = this.$qsExpansionStateInteractor.getRepository();
                SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.this$0;
                ReadonlyStateFlow readonlyStateFlow = repository.expanded;
                SecPanelSAStatusLogInteractor$2$1$1 secPanelSAStatusLogInteractor$2$1$1 = new SecPanelSAStatusLogInteractor$2$1$1(secPanelSAStatusLogInteractor, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlyStateFlow, secPanelSAStatusLogInteractor$2$1$1, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecPanelSAStatusLogInteractor(Context context, CoroutineScope coroutineScope, SecQSExpansionStateInteractor secQSExpansionStateInteractor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("QsStatusEventLog_prefs", 0);
        this.editor = sharedPreferences.edit();
        SecPanelSAStatusLogRepository secPanelSAStatusLogRepository = new SecPanelSAStatusLogRepository(sharedPreferences);
        this.repository = secPanelSAStatusLogRepository;
        final Flow[] flowArr = {secPanelSAStatusLogRepository.openQuickPanelFromStatusBarInShade, secPanelSAStatusLogRepository.openQuickPanelFromStatusBarInKeyguard, secPanelSAStatusLogRepository.openQuickPanelFromWipeDownInShade, secPanelSAStatusLogRepository.openQuickPanelFromWipeDownInKeyguard, secPanelSAStatusLogRepository.openQuickPanelFromHorizontalSwipingInShade, secPanelSAStatusLogRepository.openQuickPanelFromHorizontalSwipingInKeyguard};
        FlowKt.launchIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$1

            /* renamed from: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$1$3, reason: invalid class name */
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
                        long jLongValue = ((Number) objArr[5]).longValue();
                        long jLongValue2 = ((Number) obj6).longValue();
                        long jLongValue3 = ((Number) obj5).longValue();
                        long jLongValue4 = ((Number) obj4).longValue();
                        long jLongValue5 = ((Number) obj3).longValue();
                        long jLongValue6 = ((Number) obj2).longValue();
                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPBSE1004 => SS: ", jLongValue6, " : SK: ");
                        sbM.append(jLongValue5);
                        Log.d("SecPanelSAStatusLogInteractor", sbM.toString());
                        Log.d("SecPanelSAStatusLogInteractor", "QPBSE1004 => WDS: " + jLongValue4 + " : WDK: " + jLongValue3);
                        StringBuilder sbM2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPBSE1004 => HSS: ", jLongValue2, " : HSK: ");
                        sbM2.append(jLongValue);
                        Log.d("SecPanelSAStatusLogInteractor", sbM2.toString());
                        Long l = new Long(jLongValue6);
                        if (l.longValue() == this.this$0.oqpfss) {
                            l = null;
                        }
                        if (l != null) {
                            long jLongValue7 = l.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_status_bar_in_shade", jLongValue7);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.this$0;
                            secPanelSAStatusLogInteractor.oqpfss = jLongValue7;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l2 = new Long(jLongValue5);
                        if (l2.longValue() == this.this$0.oqpfsk) {
                            l2 = null;
                        }
                        if (l2 != null) {
                            long jLongValue8 = l2.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_status_bar_in_keyguard", jLongValue8);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.this$0;
                            secPanelSAStatusLogInteractor2.oqpfsk = jLongValue8;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor2, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l3 = new Long(jLongValue4);
                        if (l3.longValue() == this.this$0.oqpfwds) {
                            l3 = null;
                        }
                        if (l3 != null) {
                            long jLongValue9 = l3.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_wipe_down_in_shade", jLongValue9);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.this$0;
                            secPanelSAStatusLogInteractor3.oqpfwds = jLongValue9;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor3, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l4 = new Long(jLongValue3);
                        if (l4.longValue() == this.this$0.oqpfwdk) {
                            l4 = null;
                        }
                        if (l4 != null) {
                            long jLongValue10 = l4.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_wipe_down_in_keyguard", jLongValue10);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor4 = this.this$0;
                            secPanelSAStatusLogInteractor4.oqpfwdk = jLongValue10;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor4, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l5 = new Long(jLongValue2);
                        if (l5.longValue() == this.this$0.oqpfhss) {
                            l5 = null;
                        }
                        if (l5 != null) {
                            long jLongValue11 = l5.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_horizontal_swiping_in_shade", jLongValue11);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor5 = this.this$0;
                            secPanelSAStatusLogInteractor5.oqpfhss = jLongValue11;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor5, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l6 = new Long(jLongValue);
                        Long l7 = l6.longValue() == this.this$0.oqpfhsk ? null : l6;
                        if (l7 != null) {
                            long jLongValue12 = l7.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_horizontal_swiping_in_keyguard", jLongValue12);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor6 = this.this$0;
                            secPanelSAStatusLogInteractor6.oqpfhsk = jLongValue12;
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
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }), coroutineScope);
        final Flow[] flowArr2 = {secPanelSAStatusLogRepository.openQuickPanelFrom1DepthStatusBarInShade, secPanelSAStatusLogRepository.openQuickPanelFrom1DepthStatusBarInKeyguard, secPanelSAStatusLogRepository.openQuickPanelFrom1DepthEtcInShade, secPanelSAStatusLogRepository.openQuickPanelFrom1DepthEtcInKeyguard, secPanelSAStatusLogRepository.openQuickPanelFrom2Depth, secPanelSAStatusLogRepository.openQuickPanelFrom2Depth2Finger};
        FlowKt.launchIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$2

            /* renamed from: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$2$3, reason: invalid class name */
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
                        long jLongValue = ((Number) objArr[5]).longValue();
                        long jLongValue2 = ((Number) obj6).longValue();
                        long jLongValue3 = ((Number) obj5).longValue();
                        long jLongValue4 = ((Number) obj4).longValue();
                        long jLongValue5 = ((Number) obj3).longValue();
                        long jLongValue6 = ((Number) obj2).longValue();
                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPBSE1003 => 1DSS: ", jLongValue6, " : 1DSK: ");
                        sbM.append(jLongValue5);
                        Log.d("SecPanelSAStatusLogInteractor", sbM.toString());
                        Log.d("SecPanelSAStatusLogInteractor", "QPBSE1003 => 1DES: " + jLongValue4 + " : 1DEK: " + jLongValue3);
                        StringBuilder sbM2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPBSE1003 => 2D: ", jLongValue2, " : 2D2F: ");
                        sbM2.append(jLongValue);
                        Log.d("SecPanelSAStatusLogInteractor", sbM2.toString());
                        Long l = new Long(jLongValue6);
                        if (l.longValue() == this.this$0.oqpf1dss) {
                            l = null;
                        }
                        if (l != null) {
                            long jLongValue7 = l.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_1depth_status_bar_in shade", jLongValue7);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.this$0;
                            secPanelSAStatusLogInteractor.oqpf1dss = jLongValue7;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
                        }
                        Long l2 = new Long(jLongValue5);
                        if (l2.longValue() == this.this$0.oqpf1dsk) {
                            l2 = null;
                        }
                        if (l2 != null) {
                            long jLongValue8 = l2.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_1depth_status_bar_in_keyguard", jLongValue8);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.this$0;
                            secPanelSAStatusLogInteractor2.oqpf1dsk = jLongValue8;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor2, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
                        }
                        Long l3 = new Long(jLongValue4);
                        if (l3.longValue() == this.this$0.oqpf1des) {
                            l3 = null;
                        }
                        if (l3 != null) {
                            long jLongValue9 = l3.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_1depth_etc_in shade", jLongValue9);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.this$0;
                            secPanelSAStatusLogInteractor3.oqpf1des = jLongValue9;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor3, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
                        }
                        Long l4 = new Long(jLongValue3);
                        if (l4.longValue() == this.this$0.oqpf1dek) {
                            l4 = null;
                        }
                        if (l4 != null) {
                            long jLongValue10 = l4.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_1depth_etc_in_keyguard", jLongValue10);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor4 = this.this$0;
                            secPanelSAStatusLogInteractor4.oqpf1dek = jLongValue10;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor4, SystemUIAnalytics.SID_QUICKPANEL_OPENED);
                        }
                        Long l5 = new Long(jLongValue2);
                        if (l5.longValue() == this.this$0.oqpf2d) {
                            l5 = null;
                        }
                        if (l5 != null) {
                            long jLongValue11 = l5.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_2depth", jLongValue11);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor5 = this.this$0;
                            secPanelSAStatusLogInteractor5.oqpf2d = jLongValue11;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor5, SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
                        }
                        Long l6 = new Long(jLongValue);
                        Long l7 = l6.longValue() == this.this$0.oqpf2d2f ? null : l6;
                        if (l7 != null) {
                            long jLongValue12 = l7.longValue();
                            this.this$0.editor.putLong("open_quick_panel_from_2depth_2finger", jLongValue12);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor6 = this.this$0;
                            secPanelSAStatusLogInteractor6.oqpf2d2f = jLongValue12;
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
                final Flow[] flowArr3 = flowArr2;
                Object objCombineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$2.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr3.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }), coroutineScope);
        final Flow[] flowArr3 = {secPanelSAStatusLogRepository.openNotificationPanelFromStatusbarInKeyguard, secPanelSAStatusLogRepository.openNotificationPanelFromStatusbarInShade, secPanelSAStatusLogRepository.openNotificationPanelFromSwipeDownInKeyguard, secPanelSAStatusLogRepository.openNotificationPanelFromSwipeDownInShade, secPanelSAStatusLogRepository.openNotificationPanelFromSwipeRightInKeyguard, secPanelSAStatusLogRepository.openNotificationPanelFromSwipeRightInShade};
        FlowKt.launchIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$3

            /* renamed from: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$3$3, reason: invalid class name */
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
                        long jLongValue = ((Number) objArr[5]).longValue();
                        long jLongValue2 = ((Number) obj6).longValue();
                        long jLongValue3 = ((Number) obj5).longValue();
                        long jLongValue4 = ((Number) obj4).longValue();
                        long jLongValue5 = ((Number) obj3).longValue();
                        long jLongValue6 = ((Number) obj2).longValue();
                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPNE0040 => SK: ", jLongValue6, " : SS: ");
                        sbM.append(jLongValue5);
                        Log.d("SecPanelSAStatusLogInteractor", sbM.toString());
                        Log.d("SecPanelSAStatusLogInteractor", "QPNE0040 => SDK: " + jLongValue4 + " : SDS: " + jLongValue3);
                        StringBuilder sbM2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPNE0040 => SRK: ", jLongValue2, " : SRS: ");
                        sbM2.append(jLongValue);
                        Log.d("SecPanelSAStatusLogInteractor", sbM2.toString());
                        Long l = new Long(jLongValue6);
                        if (l.longValue() == this.this$0.onpfsk) {
                            l = null;
                        }
                        if (l != null) {
                            long jLongValue7 = l.longValue();
                            this.this$0.editor.putLong("open_notification_panel_from_statusbar_in_keyguard", jLongValue7);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.this$0;
                            secPanelSAStatusLogInteractor.onpfsk = jLongValue7;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor, SystemUIAnalytics.SID_NOTIFICATION_PANEL);
                        }
                        Long l2 = new Long(jLongValue5);
                        if (l2.longValue() == this.this$0.onpfss) {
                            l2 = null;
                        }
                        if (l2 != null) {
                            long jLongValue8 = l2.longValue();
                            this.this$0.editor.putLong("open_notification_panel_from_statusbar_in_shade", jLongValue8);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.this$0;
                            secPanelSAStatusLogInteractor2.onpfss = jLongValue8;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor2, SystemUIAnalytics.SID_NOTIFICATION_PANEL);
                        }
                        Long l3 = new Long(jLongValue4);
                        if (l3.longValue() == this.this$0.onpfsdk) {
                            l3 = null;
                        }
                        if (l3 != null) {
                            long jLongValue9 = l3.longValue();
                            this.this$0.editor.putLong("open_notification_panel_from_swipe_down_in_keyguard", jLongValue9);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.this$0;
                            secPanelSAStatusLogInteractor3.onpfsdk = jLongValue9;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor3, SystemUIAnalytics.SID_NOTIFICATION_PANEL);
                        }
                        Long l4 = new Long(jLongValue3);
                        if (l4.longValue() == this.this$0.onpfsds) {
                            l4 = null;
                        }
                        if (l4 != null) {
                            long jLongValue10 = l4.longValue();
                            this.this$0.editor.putLong("open_notification_panel_from_swipe_down_in_shade", jLongValue10);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor4 = this.this$0;
                            secPanelSAStatusLogInteractor4.onpfsds = jLongValue10;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor4, SystemUIAnalytics.SID_NOTIFICATION_PANEL);
                        }
                        Long l5 = new Long(jLongValue2);
                        if (l5.longValue() == this.this$0.onpfsrk) {
                            l5 = null;
                        }
                        if (l5 != null) {
                            long jLongValue11 = l5.longValue();
                            this.this$0.editor.putLong("open_notification_panel_from_swipe_right_in_keyguard", jLongValue11);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor5 = this.this$0;
                            secPanelSAStatusLogInteractor5.onpfsrk = jLongValue11;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor5, SystemUIAnalytics.SID_NOTIFICATION_PANEL);
                        }
                        Long l6 = new Long(jLongValue);
                        Long l7 = l6.longValue() == this.this$0.onpfsrs ? null : l6;
                        if (l7 != null) {
                            long jLongValue12 = l7.longValue();
                            this.this$0.editor.putLong("open_notification_panel_from_swipe_right_in_shade", jLongValue12);
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor6 = this.this$0;
                            secPanelSAStatusLogInteractor6.onpfsrs = jLongValue12;
                            SecPanelSAStatusLogInteractor.access$updateCurrentScreen(secPanelSAStatusLogInteractor6, SystemUIAnalytics.SID_NOTIFICATION_PANEL);
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
                final Flow[] flowArr4 = flowArr3;
                Object objCombineInternal = CombineKt.combineInternal(flowArr4, new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor$_init_$lambda$39$$inlined$combine$3.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr4.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }), coroutineScope);
        FlowKt.launchIn(FlowKt.distinctUntilChanged(FlowKt.combine(secPanelSAStatusLogRepository.openNotificationPanelFromKeyguard, secPanelSAStatusLogRepository.openNotificationPanelFromHomescreen, secPanelSAStatusLogRepository.openNotificationPanelFromHun, secPanelSAStatusLogRepository.openNotificationPanelFromEtc, new SecPanelSAStatusLogInteractor$1$4(this, null))), coroutineScope);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(secQSExpansionStateInteractor, this, null), 3);
    }

    public static final void access$updateCurrentScreen(SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor, String str) {
        secPanelSAStatusLogInteractor.getClass();
        SystemUIAnalytics.setCurrentScreenID(str);
    }

    public final void countOpenNotificationPanelFromLockscreen() {
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            StateFlowImpl stateFlowImpl = this.repository._openNotificationPanelFromKeyguard;
            LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
        }
    }

    public final void countOpenNotificationPanelFromStatusbarOnLockscreen() {
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            StateFlowImpl stateFlowImpl = this.repository._openNotificationPanelFromStatusbarInKeyguard;
            LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
        }
    }
}
