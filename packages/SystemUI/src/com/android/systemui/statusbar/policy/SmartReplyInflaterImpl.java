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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public static final Intent access$createRemoteInputIntent(SmartReplyInflaterImpl smartReplyInflaterImpl, SmartReplyView.SmartReplies smartReplies, CharSequence charSequence) {
        smartReplyInflaterImpl.getClass();
        Bundle bundle = new Bundle();
        bundle.putString(smartReplies.remoteInput.getResultKey(), charSequence.toString());
        Intent addFlags = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        RemoteInput.addResultsToIntent(new RemoteInput[]{smartReplies.remoteInput}, addFlags, bundle);
        RemoteInput.setResultsSource(addFlags, 1);
        return addFlags;
    }
}
