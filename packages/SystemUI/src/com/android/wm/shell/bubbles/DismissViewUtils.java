package com.android.wm.shell.bubbles;

import com.android.systemui.R;
import com.android.wm.shell.shared.bubbles.DismissView;

/* loaded from: classes3.dex */
public abstract class DismissViewUtils {
    public static final void setup(DismissView dismissView) {
        if ((dismissView.getResources().getConfiguration().uiMode & 48) == 32) {
            dismissView.setup(new DismissView.Config(R.id.dismiss_view, R.dimen.sec_noti_bubble_dismiss_button_width, R.dimen.sec_noti_bubble_dismiss_button_width, R.dimen.floating_dismiss_bottom_margin, R.dimen.floating_dismiss_gradient_height, android.R.color.system_neutral1_900, R.drawable.dismiss_circle_background, R.drawable.bubble_delete_ic_d));
        } else {
            dismissView.setup(new DismissView.Config(R.id.dismiss_view, R.dimen.sec_noti_bubble_dismiss_button_width, R.dimen.sec_noti_bubble_dismiss_button_width, R.dimen.floating_dismiss_bottom_margin, R.dimen.floating_dismiss_gradient_height, android.R.color.system_neutral1_900, R.drawable.dismiss_circle_background, R.drawable.bubble_delete_ic));
        }
    }
}
