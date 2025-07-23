package com.android.systemui.qp;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SubscreenBaseViewControllerBase extends ViewController implements SubscreenQSControllerContract$BaseViewController {
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
