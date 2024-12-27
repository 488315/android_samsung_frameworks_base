package com.android.systemui.volume.view.warnings;

import android.view.Window;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.volume.util.ContextUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumeWarningDialog$initWindow$1 implements Runnable {
    public final /* synthetic */ VolumeWarningDialog this$0;

    public VolumeWarningDialog$initWindow$1(VolumeWarningDialog volumeWarningDialog) {
        this.this$0 = volumeWarningDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int dimenInt = ContextUtils.getDimenInt(R.dimen.volume_warning_popup_margin_horizontal_clear_cover, this.this$0.getContext());
        int dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_warning_popup_margin_bottom_clear_cover, this.this$0.getContext());
        int dimenInt3 = ContextUtils.getDimenInt(R.dimen.navigation_bar_height, this.this$0.getContext());
        this.this$0.getWindow().setLayout(ContextUtils.getDisplayWidth(this.this$0.getContext()) - (dimenInt * 2), -2);
        Window window = this.this$0.getWindow();
        WindowManager.LayoutParams attributes = this.this$0.getWindow().getAttributes();
        attributes.y = dimenInt2 - dimenInt3;
        window.setAttributes(attributes);
    }
}
