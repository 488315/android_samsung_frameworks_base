package com.android.systemui.statusbar;

import android.app.Notification;
import android.util.ArraySet;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.util.SystemUIAnalytics;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SmartReplyController implements Dumpable {
    public final IStatusBarService mBarService;
    public Callback mCallback;
    public final NotificationClickNotifier mClickNotifier;
    public final Set mSendingKeys = new ArraySet();
    public final NotificationVisibilityProvider mVisibilityProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence);
    }

    public SmartReplyController(DumpManager dumpManager, NotificationVisibilityProvider notificationVisibilityProvider, IStatusBarService iStatusBarService, NotificationClickNotifier notificationClickNotifier) {
        this.mBarService = iStatusBarService;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mClickNotifier = notificationClickNotifier;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mSendingKeys: " + ((ArraySet) this.mSendingKeys).size());
        Iterator it = ((ArraySet) this.mSendingKeys).iterator();
        while (it.hasNext()) {
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, " * ", (String) it.next());
        }
    }

    public final void smartActionClicked(NotificationEntry notificationEntry, int i, Notification.Action action, boolean z) {
        NotificationVisibility obtain = ((NotificationVisibilityProviderImpl) this.mVisibilityProvider).obtain(notificationEntry);
        NotificationClickNotifier notificationClickNotifier = this.mClickNotifier;
        Executor executor = notificationClickNotifier.backgroundExecutor;
        String str = notificationEntry.mKey;
        executor.execute(new NotificationClickNotifier$onNotificationActionClick$1(notificationClickNotifier, str, i, action, obtain, z));
        notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$2(notificationClickNotifier, str));
        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_QUICK_REPLY_BUTTON_AND_ACTIONS, "type", SystemUIAnalytics.QPNE_VID_ACTIONS, SystemUIAnalytics.QPNE_KEY_APP, notificationEntry.mSbn.getPackageName());
    }

    public final void stopSending(NotificationEntry notificationEntry) {
        if (notificationEntry != null) {
            ((ArraySet) this.mSendingKeys).remove(notificationEntry.mSbn.getKey());
        }
    }
}
