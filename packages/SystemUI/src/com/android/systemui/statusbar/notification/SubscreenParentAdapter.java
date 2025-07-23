package com.android.systemui.statusbar.notification;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
