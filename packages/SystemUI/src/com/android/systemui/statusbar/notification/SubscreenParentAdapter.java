package com.android.systemui.statusbar.notification;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SubscreenParentAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public SubscreenDeviceModelParent mDeviceModel;
    public SubscreenSubRoomNotificaitonAnimatorManager mNotificationAnimatorManager;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public RecyclerView mNotificationRecyclerView;
    public SubscreenSubRoomNotification mSubRoomNotification;
}
