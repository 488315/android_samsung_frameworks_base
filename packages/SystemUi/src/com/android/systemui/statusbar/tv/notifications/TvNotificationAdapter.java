package com.android.systemui.statusbar.tv.notifications;

import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvNotificationAdapter extends RecyclerView.Adapter {
    public SparseArray mNotifications;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TvNotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mDetails;
        public PendingIntent mPendingIntent;
        public final TextView mTitle;

        public TvNotificationViewHolder(View view) {
            super(view);
            this.mTitle = (TextView) view.findViewById(R.id.tv_notification_title);
            this.mDetails = (TextView) view.findViewById(R.id.tv_notification_details);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            try {
                if (this.mPendingIntent != null) {
                    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                    makeBasic.setInteractive(true);
                    this.mPendingIntent.send(makeBasic.toBundle());
                }
            } catch (PendingIntent.CanceledException unused) {
                Log.d("TvNotificationAdapter", "Pending intent canceled for : " + this.mPendingIntent);
            }
        }
    }

    public TvNotificationAdapter() {
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        SparseArray sparseArray = this.mNotifications;
        if (sparseArray == null) {
            return 0;
        }
        return sparseArray.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return this.mNotifications.keyAt(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SparseArray sparseArray = this.mNotifications;
        if (sparseArray == null) {
            Log.e("TvNotificationAdapter", "Could not bind view holder because the notification is missing");
            return;
        }
        TvNotificationViewHolder tvNotificationViewHolder = (TvNotificationViewHolder) viewHolder;
        Notification notification2 = ((StatusBarNotification) sparseArray.valueAt(i)).getNotification();
        tvNotificationViewHolder.mTitle.setText(notification2.extras.getString("android.title"));
        tvNotificationViewHolder.mDetails.setText(notification2.extras.getString("android.text"));
        tvNotificationViewHolder.mPendingIntent = notification2.contentIntent;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new TvNotificationViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.tv_notification_item, (ViewGroup) recyclerView, false));
    }
}
