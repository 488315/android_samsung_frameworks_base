package com.android.systemui.statusbar.phone.ongoingcall;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCallController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OngoingCallController f$0;

    public /* synthetic */ OngoingCallController$$ExternalSyntheticLambda0(OngoingCallController ongoingCallController, int i) {
        this.$r8$classId = i;
        this.f$0 = ongoingCallController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        OngoingCallChronometer ongoingCallChronometer;
        switch (this.$r8$classId) {
            case 0:
                View view = this.f$0.chipView;
                if (view != null && (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) != null) {
                    ongoingCallChronometer.stop();
                }
                return Unit.INSTANCE;
            default:
                return Integer.valueOf(((StatusBarWindowControllerImpl) ((StatusBarWindowController) this.f$0.statusBarWindowControllerStore.getDefaultDisplay())).mBarHeight);
        }
    }
}
