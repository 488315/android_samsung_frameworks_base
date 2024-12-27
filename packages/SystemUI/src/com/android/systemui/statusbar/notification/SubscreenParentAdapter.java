package com.android.systemui.statusbar.notification;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SubscreenParentAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public SubscreenDeviceModelParent mDeviceModel;
    public SubscreenSubRoomNotificaitonAnimatorManager mNotificationAnimatorManager;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public RecyclerView mNotificationRecyclerView;
    public SubscreenSubRoomNotification mSubRoomNotification;
}
