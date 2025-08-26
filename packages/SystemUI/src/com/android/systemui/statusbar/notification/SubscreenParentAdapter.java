package com.android.systemui.statusbar.notification;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public abstract class SubscreenParentAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public SubscreenNotificationController mController;
    public SubscreenDeviceModelParent mDeviceModel;
    public SubscreenSubRoomNotificaitonAnimatorManager mNotificationAnimatorManager;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public RecyclerView mNotificationRecyclerView;
    public SubscreenSubRoomNotification mSubRoomNotification;
}
