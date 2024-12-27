package com.android.systemui.qp;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenBaseViewControllerBase extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public final SubroomQuickSettingsBaseView mView;

    public SubscreenBaseViewControllerBase(SubroomQuickSettingsBaseView subroomQuickSettingsBaseView) {
        super(subroomQuickSettingsBaseView);
        this.mView = subroomQuickSettingsBaseView;
        this.mContext = getContext();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        SubroomQuickSettingsBaseView subroomQuickSettingsBaseView = this.mView;
        Objects.toString(subroomQuickSettingsBaseView);
        new SubscreenBrightnessController(this.mContext, (SubroomBrightnessSettingsView) subroomQuickSettingsBaseView.findViewById(R.id.subroom_brightness_settings)).init();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Objects.toString(this.mView);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
    }
}
