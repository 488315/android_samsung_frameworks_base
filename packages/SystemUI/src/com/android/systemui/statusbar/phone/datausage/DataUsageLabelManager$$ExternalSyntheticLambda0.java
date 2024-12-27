package com.android.systemui.statusbar.phone.datausage;

import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                dataUsageLabelManager.getClass();
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                int i = dataUsageLabelManager.mInsetNavigationBarBottomHeight;
                DataUsageLabelView dataUsageLabelView = dataUsageLabelManager.mLabelView;
                if (dataUsageLabelView != null) {
                    i = StrongAuthPopup$$ExternalSyntheticOutline0.m(dataUsageLabelView.mViewContext, R.dimen.notification_panel_carrier_label_height, i);
                }
                if (layoutParams.height != i) {
                    layoutParams.height = i;
                    if (DataUsageLabelManager.DEBUG) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "updateLayoutParamHeight() newHeight:", "DataUsageLabelManager");
                    }
                    viewGroup.setLayoutParams(layoutParams);
                    break;
                }
                break;
        }
    }
}
