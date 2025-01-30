package com.android.systemui.statusbar.phone.datausage;

import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                    i += dataUsageLabelView.mContext.getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_height);
                }
                if (layoutParams.height != i) {
                    layoutParams.height = i;
                    if (DataUsageLabelManager.DEBUG) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("updateLayoutParamHeight() newHeight:", i, "DataUsageLabelManager");
                    }
                    viewGroup.setLayoutParams(layoutParams);
                    break;
                }
                break;
        }
    }
}
