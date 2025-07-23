package com.android.systemui.qs.composefragment;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0072, code lost:
    
        if (((java.lang.Boolean) (r3 != null ? r3 : null).isQsFullyExpanded$delegate.getValue()).booleanValue() == false) goto L32;
     */
    @Override // kotlin.jvm.functions.Function0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invoke() {
        /*
            r3 = this;
            r0 = 0
            java.lang.Object r1 = r3.f$0
            int r3 = r3.$r8$classId
            switch(r3) {
                case 0: goto L91;
                case 1: goto L82;
                case 2: goto L56;
                case 3: goto L45;
                case 4: goto L12;
                default: goto L8;
            }
        L8:
            java.lang.Runnable r1 = (java.lang.Runnable) r1
            int r3 = com.android.systemui.qs.composefragment.QSFragmentCompose.$r8$clinit
            r1.run()
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            return r3
        L12:
            com.android.systemui.qs.composefragment.QSFragmentCompose r1 = (com.android.systemui.qs.composefragment.QSFragmentCompose) r1
            kotlinx.coroutines.flow.StateFlowImpl r3 = r1.scrollListener
            java.lang.Object r3 = r3.getValue()
            com.android.systemui.plugins.qs.QS$ScrollListener r3 = (com.android.systemui.plugins.qs.QS.ScrollListener) r3
            if (r3 == 0) goto L27
            androidx.compose.foundation.ScrollState r2 = r1.scrollState
            int r2 = r2.getValue()
            r3.onQsPanelScrollChanged(r2)
        L27:
            kotlinx.coroutines.flow.StateFlowImpl r3 = r1.collapsedMediaVisibilityChangedListener
            java.lang.Object r3 = r3.getValue()
            java.util.function.Consumer r3 = (java.util.function.Consumer) r3
            if (r3 == 0) goto L42
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel r1 = r1.viewModel
            if (r1 != 0) goto L36
            goto L37
        L36:
            r0 = r1
        L37:
            boolean r0 = r0.getQqsMediaVisible()
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r3.accept(r0)
        L42:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        L45:
            com.android.systemui.qs.composefragment.QSFragmentCompose r1 = (com.android.systemui.qs.composefragment.QSFragmentCompose) r1
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel r3 = r1.viewModel
            if (r3 != 0) goto L4c
            goto L4d
        L4c:
            r0 = r3
        L4d:
            androidx.compose.runtime.State r3 = r0.expansionState$delegate
            java.lang.Object r3 = r3.getValue()
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$QSExpansionState r3 = (com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel.QSExpansionState) r3
            return r3
        L56:
            com.android.systemui.qs.composefragment.QSFragmentCompose r1 = (com.android.systemui.qs.composefragment.QSFragmentCompose) r1
            androidx.compose.foundation.ScrollState r3 = r1.scrollState
            boolean r3 = r3.getCanScrollForward()
            if (r3 == 0) goto L74
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel r3 = r1.viewModel
            if (r3 != 0) goto L65
            goto L66
        L65:
            r0 = r3
        L66:
            androidx.compose.runtime.State r3 = r0.isQsFullyExpanded$delegate
            java.lang.Object r3 = r3.getValue()
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            if (r3 != 0) goto L7a
        L74:
            boolean r3 = r1.isCustomizing()
            if (r3 == 0) goto L7c
        L7a:
            r3 = 1
            goto L7d
        L7c:
            r3 = 0
        L7d:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            return r3
        L82:
            com.android.systemui.qs.composefragment.QSFragmentCompose r1 = (com.android.systemui.qs.composefragment.QSFragmentCompose) r1
            com.android.systemui.qs.composefragment.QSFragmentCompose$notificationScrimClippingParams$1 r3 = r1.notificationScrimClippingParams
            androidx.compose.runtime.MutableState r3 = r3.params$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r3 = (androidx.compose.runtime.SnapshotMutableStateImpl) r3
            java.lang.Object r3 = r3.getValue()
            com.android.systemui.qs.composefragment.ui.NotificationScrimClipParams r3 = (com.android.systemui.qs.composefragment.ui.NotificationScrimClipParams) r3
            return r3
        L91:
            com.android.systemui.qs.composefragment.QSFragmentCompose r1 = (com.android.systemui.qs.composefragment.QSFragmentCompose) r1
            com.android.systemui.qs.composefragment.QSFragmentCompose$notificationScrimClippingParams$1 r3 = r1.notificationScrimClippingParams
            androidx.compose.runtime.MutableState r3 = r3.isEnabled$delegate
            androidx.compose.runtime.SnapshotMutableStateImpl r3 = (androidx.compose.runtime.SnapshotMutableStateImpl) r3
            java.lang.Object r3 = r3.getValue()
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            r3.booleanValue()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose$$ExternalSyntheticLambda0.invoke():java.lang.Object");
    }
}
