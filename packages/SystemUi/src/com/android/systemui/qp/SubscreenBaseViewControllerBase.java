package com.android.systemui.qp;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
