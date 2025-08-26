package com.android.systemui.statusbar.policy;

import android.app.INotificationManager;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ServiceManager;
import com.android.systemui.NotiRune;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.SmartReplyView;

/* loaded from: classes3.dex */
public final class SmartReplyInflaterImpl implements SmartReplyInflater {
    public final SmartReplyConstants constants;
    public final Context context;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final INotificationManager notifManager;
    public final NotificationRemoteInputManager remoteInputManager;
    public final SmartReplyController smartReplyController;

    public SmartReplyInflaterImpl(SmartReplyConstants smartReplyConstants, KeyguardDismissUtil keyguardDismissUtil, NotificationRemoteInputManager notificationRemoteInputManager, SmartReplyController smartReplyController, Context context) {
        this.constants = smartReplyConstants;
        this.keyguardDismissUtil = keyguardDismissUtil;
        this.remoteInputManager = notificationRemoteInputManager;
        this.smartReplyController = smartReplyController;
        this.context = context;
        this.notifManager = NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY ? INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION)) : null;
    }

    public static Intent createRemoteInputIntent(SmartReplyView.SmartReplies smartReplies, CharSequence charSequence) {
        Bundle bundle = new Bundle();
        bundle.putString(smartReplies.remoteInput.getResultKey(), charSequence.toString());
        Intent intentAddFlags = new Intent().addFlags(268435456);
        RemoteInput.addResultsToIntent(new RemoteInput[]{smartReplies.remoteInput}, intentAddFlags, bundle);
        RemoteInput.setResultsSource(intentAddFlags, 1);
        return intentAddFlags;
    }
}
