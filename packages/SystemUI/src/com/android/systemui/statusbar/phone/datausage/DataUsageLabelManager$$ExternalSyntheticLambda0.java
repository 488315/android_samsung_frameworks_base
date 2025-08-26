package com.android.systemui.statusbar.phone.datausage;

import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public final /* synthetic */ class DataUsageLabelManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DataUsageLabelManager f$0;
    public final /* synthetic */ ViewGroup f$1;

    public /* synthetic */ DataUsageLabelManager$$ExternalSyntheticLambda0(DataUsageLabelManager dataUsageLabelManager, ViewGroup viewGroup, int i) {
        this.$r8$classId = i;
        this.f$0 = dataUsageLabelManager;
        this.f$1 = viewGroup;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$1.setPadding(0, 0, 0, this.f$0.mInsetNavigationBarBottomHeight);
                break;
            default:
                DataUsageLabelManager dataUsageLabelManager = this.f$0;
                ViewGroup viewGroup = this.f$1;
                boolean z = DataUsageLabelManager.DEBUG;
                dataUsageLabelManager.getClass();
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                int iM = dataUsageLabelManager.mInsetNavigationBarBottomHeight;
                DataUsageLabelView dataUsageLabelView = dataUsageLabelManager.mLabelView;
                if (dataUsageLabelView != null) {
                    iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(dataUsageLabelView.mViewContext, R.dimen.notification_panel_carrier_label_height, iM);
                }
                if (layoutParams.height != iM) {
                    layoutParams.height = iM;
                    if (DataUsageLabelManager.DEBUG) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(iM, "updateLayoutParamHeight() newHeight:", "DataUsageLabelManager");
                    }
                    viewGroup.setLayoutParams(layoutParams);
                    break;
                }
                break;
        }
    }
}
