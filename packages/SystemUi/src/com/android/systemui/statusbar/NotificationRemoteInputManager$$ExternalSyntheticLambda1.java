package com.android.systemui.statusbar;

import android.app.PendingIntent;
import android.util.Pair;
import android.view.View;
import android.widget.RemoteViews;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationRemoteInputManager$$ExternalSyntheticLambda1 {
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ View f$2;
    public final /* synthetic */ PendingIntent f$3;
    public final /* synthetic */ Object f$4;

    public /* synthetic */ NotificationRemoteInputManager$$ExternalSyntheticLambda1(NotificationRemoteInputManager.C25901 c25901, RemoteViews.RemoteResponse remoteResponse, View view, NotificationEntry notificationEntry, PendingIntent pendingIntent) {
        this.f$0 = c25901;
        this.f$1 = remoteResponse;
        this.f$2 = view;
        this.f$4 = notificationEntry;
        this.f$3 = pendingIntent;
    }

    public final boolean handleClick() {
        boolean booleanValue;
        NotificationRemoteInputManager.C25901 c25901 = (NotificationRemoteInputManager.C25901) this.f$0;
        RemoteViews.RemoteResponse remoteResponse = (RemoteViews.RemoteResponse) this.f$1;
        NotificationEntry notificationEntry = (NotificationEntry) this.f$4;
        c25901.getClass();
        View view = this.f$2;
        Pair launchOptions = remoteResponse.getLaunchOptions(view);
        ActionClickLogger actionClickLogger = NotificationRemoteInputManager.this.mLogger;
        actionClickLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ActionClickLogger$logStartingIntentWithDefaultHandler$2 actionClickLogger$logStartingIntentWithDefaultHandler$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logStartingIntentWithDefaultHandler$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return MotionLayout$$ExternalSyntheticOutline0.m22m("  [Action click] Launching intent ", logMessage.getStr2(), " via default handler (for ", logMessage.getStr1(), ")");
            }
        };
        LogBuffer logBuffer = actionClickLogger.buffer;
        LogMessage obtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$logStartingIntentWithDefaultHandler$2, null);
        obtain.setStr1(notificationEntry != null ? notificationEntry.mKey : null);
        PendingIntent pendingIntent = this.f$3;
        obtain.setStr2(pendingIntent.getIntent().toString());
        logBuffer.commit(obtain);
        boolean startPendingIntent = RemoteViews.startPendingIntent(view, pendingIntent, launchOptions);
        if (startPendingIntent) {
            NotificationRemoteInputManager notificationRemoteInputManager = NotificationRemoteInputManager.this;
            if (notificationEntry == null) {
                notificationRemoteInputManager.getClass();
            } else {
                RemoteInputCoordinator remoteInputCoordinator = notificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    booleanValue = ((Boolean) RemoteInputCoordinatorKt.DEBUG$delegate.getValue()).booleanValue();
                    String str = notificationEntry.mKey;
                    if (booleanValue) {
                        AbstractC0689x6838b71d.m68m("releaseNotificationIfKeptForRemoteInputHistory(entry=", str, ")", "RemoteInputCoordinator");
                    }
                    remoteInputCoordinator.mRemoteInputHistoryExtender.endLifetimeExtensionAfterDelay(str, 200L);
                    remoteInputCoordinator.mSmartReplyHistoryExtender.endLifetimeExtensionAfterDelay(str, 200L);
                    remoteInputCoordinator.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(str, 200L);
                }
                Iterator it = notificationRemoteInputManager.mActionPressListeners.iterator();
                while (it.hasNext()) {
                    ((Consumer) it.next()).accept(notificationEntry);
                }
            }
        }
        return startPendingIntent;
    }
}
