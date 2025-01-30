package com.android.systemui.statusbar.notification;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SubscreenParentAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public SubscreenDeviceModelParent mDeviceModel;
    public SubscreenSubRoomNotificaitonAnimatorManager mNotificationAnimatorManager;
    public SubscreenNotificationInfoManager mNotificationInfoManager;
    public RecyclerView mNotificationRecyclerView;
    public SubscreenSubRoomNotification mSubRoomNotification;
}
