package com.android.systemui.statusbar.notification;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenSubRoomNotificationTip extends SubscreenParentAdapter {
    public static SubscreenSubRoomNotificationTip sInstance;
    public TextView mDismissBtn;
    public TextView mSettingsBtn;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TipViewHolder extends RecyclerView.ViewHolder {
        public TipViewHolder(View view) {
            super(view);
            SubscreenSubRoomNotificationTip.this.mDeviceModel.setDimOnMainBackground(view);
            SubscreenSubRoomNotificationTip.this.mDeviceModel.setTipViewPadding(view);
            SubscreenSubRoomNotificationTip.this.mSettingsBtn = (TextView) view.findViewById(R.id.settings_button);
            SubscreenSubRoomNotificationTip.this.mDismissBtn = (TextView) view.findViewById(R.id.dismiss_button);
            SubscreenSubRoomNotificationTip.this.mSettingsBtn.setOnClickListener(new View.OnClickListener(SubscreenSubRoomNotificationTip.this) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificationTip.TipViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Log.d("SubscreenSubRoomNotificationTip", "settings button click");
                    Intent intent = new Intent("com.samsung.settings.COVER_SCREEN_NOTIFICATION_SETTINGS");
                    Bundle bundle = new Bundle();
                    bundle.putString(":settings:fragment_args_key", "show_notifications_on_cover_screen");
                    intent.putExtra(":settings:show_fragment_args", bundle);
                    PendingIntent activityAsUser = PendingIntent.getActivityAsUser(SubscreenSubRoomNotificationTip.this.mContext, 0, intent, 201326592, null, UserHandle.CURRENT);
                    KeyguardManager keyguardManager = (KeyguardManager) SubscreenSubRoomNotificationTip.this.mContext.getSystemService("keyguard");
                    Intent intent2 = new Intent();
                    intent2.putExtra("showCoverToast", true);
                    intent2.putExtra("ignoreKeyguardState", true);
                    keyguardManager.semSetPendingIntentAfterUnlock(activityAsUser, intent2);
                }
            });
            SubscreenSubRoomNotificationTip.this.mDismissBtn.setOnClickListener(new View.OnClickListener(SubscreenSubRoomNotificationTip.this) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificationTip.TipViewHolder.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Log.d("SubscreenSubRoomNotificationTip", "dismiss button click");
                    SubscreenSubRoomNotificationTip.this.mDeviceModel.onTipButtonClicked();
                    SubscreenSubRoomNotificationTip.this.mSubRoomNotification.notifyClockSubRoomRequest();
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TipViewHolder(LayoutInflater.from(this.mContext).inflate(this.mDeviceModel.getSubscreenNotificationTipResource(), viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }
}
