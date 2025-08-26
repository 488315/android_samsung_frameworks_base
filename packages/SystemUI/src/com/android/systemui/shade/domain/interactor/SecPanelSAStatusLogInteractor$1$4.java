package com.android.systemui.shade.domain.interactor;

import android.util.Log;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes3.dex */
final class SecPanelSAStatusLogInteractor$1$4 extends SuspendLambda implements Function5 {
    /* synthetic */ long J$0;
    /* synthetic */ long J$1;
    /* synthetic */ long J$2;
    /* synthetic */ long J$3;
    int label;
    final /* synthetic */ SecPanelSAStatusLogInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPanelSAStatusLogInteractor$1$4(SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor, Continuation continuation) {
        super(5, continuation);
        this.this$0 = secPanelSAStatusLogInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        long jLongValue = ((Number) obj).longValue();
        long jLongValue2 = ((Number) obj2).longValue();
        long jLongValue3 = ((Number) obj3).longValue();
        long jLongValue4 = ((Number) obj4).longValue();
        SecPanelSAStatusLogInteractor$1$4 secPanelSAStatusLogInteractor$1$4 = new SecPanelSAStatusLogInteractor$1$4(this.this$0, (Continuation) obj5);
        secPanelSAStatusLogInteractor$1$4.J$0 = jLongValue;
        secPanelSAStatusLogInteractor$1$4.J$1 = jLongValue2;
        secPanelSAStatusLogInteractor$1$4.J$2 = jLongValue3;
        secPanelSAStatusLogInteractor$1$4.J$3 = jLongValue4;
        return secPanelSAStatusLogInteractor$1$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        long j = this.J$0;
        long j2 = this.J$1;
        long j3 = this.J$2;
        long j4 = this.J$3;
        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("QPNE0040 => K: ", j, " : H: ");
        sbM.append(j2);
        Log.d("SecPanelSAStatusLogInteractor", sbM.toString());
        Log.d("SecPanelSAStatusLogInteractor", "QPNE0040 => H: " + j3 + " : E: " + j4);
        Long l = new Long(j);
        if (l.longValue() == this.this$0.onpfk) {
            l = null;
        }
        if (l != null) {
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = this.this$0;
            long jLongValue = l.longValue();
            secPanelSAStatusLogInteractor.editor.putLong("open_notification_panel_from_keyguard", jLongValue);
            secPanelSAStatusLogInteractor.onpfk = jLongValue;
            SystemUIAnalytics.setCurrentScreenID(SystemUIAnalytics.SID_NOTIFICATION_PANEL);
        }
        Long l2 = new Long(j2);
        if (l2.longValue() == this.this$0.onpfh) {
            l2 = null;
        }
        if (l2 != null) {
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor2 = this.this$0;
            long jLongValue2 = l2.longValue();
            secPanelSAStatusLogInteractor2.editor.putLong("open_notification_panel_from_homescreen", jLongValue2);
            secPanelSAStatusLogInteractor2.onpfh = jLongValue2;
            SystemUIAnalytics.setCurrentScreenID(SystemUIAnalytics.SID_NOTIFICATION_PANEL);
        }
        Long l3 = new Long(j3);
        if (l3.longValue() == this.this$0.onpfhun) {
            l3 = null;
        }
        if (l3 != null) {
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor3 = this.this$0;
            long jLongValue3 = l3.longValue();
            secPanelSAStatusLogInteractor3.editor.putLong("open_notification_panel_from_hun", jLongValue3);
            secPanelSAStatusLogInteractor3.onpfhun = jLongValue3;
            SystemUIAnalytics.setCurrentScreenID(SystemUIAnalytics.SID_NOTIFICATION_PANEL);
        }
        Long l4 = new Long(j4);
        Long l5 = l4.longValue() != this.this$0.onpfetc ? l4 : null;
        if (l5 != null) {
            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor4 = this.this$0;
            long jLongValue4 = l5.longValue();
            secPanelSAStatusLogInteractor4.editor.putLong("open_notification_panel_from_etc", jLongValue4);
            secPanelSAStatusLogInteractor4.onpfetc = jLongValue4;
            SystemUIAnalytics.setCurrentScreenID(SystemUIAnalytics.SID_NOTIFICATION_PANEL);
        }
        this.this$0.editor.apply();
        return Unit.INSTANCE;
    }
}
