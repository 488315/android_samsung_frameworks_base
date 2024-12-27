package com.android.systemui.statusbar.notification.row;

import android.view.LayoutInflater;
import android.widget.RadioButton;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.row.SnoozeOptionManager;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SnoozeOptionManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ SnoozeOptionManager f$0;
    public final /* synthetic */ LayoutInflater f$1;

    public /* synthetic */ SnoozeOptionManager$$ExternalSyntheticLambda0(SnoozeOptionManager snoozeOptionManager, LayoutInflater layoutInflater) {
        this.f$0 = snoozeOptionManager;
        this.f$1 = layoutInflater;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SnoozeOptionManager snoozeOptionManager = this.f$0;
        NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) obj;
        RadioButton radioButton = (RadioButton) this.f$1.inflate(R.layout.sec_notification_snooze_option, snoozeOptionManager.mSnoozeOptionContainer, false);
        snoozeOptionManager.mSnoozeOptionContainer.addView(radioButton);
        radioButton.setText(snoozeOption.getDescription());
        radioButton.setTag(snoozeOption);
        radioButton.setOnClickListener((SecNotificationSnooze) snoozeOptionManager.mParent);
        SnoozeOptionManager.NotificationSnoozeOption notificationSnoozeOption = snoozeOptionManager.mDefaultOption;
        if (notificationSnoozeOption == null || !snoozeOption.equals(notificationSnoozeOption)) {
            return;
        }
        radioButton.setChecked(true);
    }
}
