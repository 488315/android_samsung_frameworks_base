package com.android.systemui.statusbar;

import android.app.PendingIntent;
import android.util.Pair;
import android.view.View;
import android.widget.RemoteViews;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationRemoteInputManager$1$$ExternalSyntheticLambda0 {
    public final /* synthetic */ NotificationRemoteInputManager.AnonymousClass1 f$0;
    public final /* synthetic */ RemoteViews.RemoteResponse f$1;
    public final /* synthetic */ View f$2;
    public final /* synthetic */ NotificationEntry f$3;
    public final /* synthetic */ PendingIntent f$4;
    public final /* synthetic */ Integer f$5;

    public /* synthetic */ NotificationRemoteInputManager$1$$ExternalSyntheticLambda0(NotificationRemoteInputManager.AnonymousClass1 anonymousClass1, RemoteViews.RemoteResponse remoteResponse, View view, NotificationEntry notificationEntry, PendingIntent pendingIntent, Integer num) {
        this.f$0 = anonymousClass1;
        this.f$1 = remoteResponse;
        this.f$2 = view;
        this.f$3 = notificationEntry;
        this.f$4 = pendingIntent;
        this.f$5 = num;
    }

    public final boolean handleClick() {
        RemoteViews.RemoteResponse remoteResponse = this.f$1;
        View view = this.f$2;
        PendingIntent pendingIntent = this.f$4;
        NotificationRemoteInputManager.AnonymousClass1 anonymousClass1 = this.f$0;
        anonymousClass1.getClass();
        Pair launchOptions = remoteResponse.getLaunchOptions(view);
        ActionClickLogger actionClickLogger = NotificationRemoteInputManager.this.mLogger;
        actionClickLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ActionClickLogger$logStartingIntentWithDefaultHandler$2 actionClickLogger$logStartingIntentWithDefaultHandler$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logStartingIntentWithDefaultHandler$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt1(), ")", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("  [Action click] Launching intent ", logMessage.getStr2(), " via default handler (for ", logMessage.getStr1(), " at index "));
            }
        };
        LogBuffer logBuffer = actionClickLogger.buffer;
        LogMessage obtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$logStartingIntentWithDefaultHandler$2, null);
        NotificationEntry notificationEntry = this.f$3;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = notificationEntry != null ? notificationEntry.mKey : null;
        logMessageImpl.str2 = pendingIntent.toString();
        Integer num = this.f$5;
        logMessageImpl.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
        logBuffer.commit(obtain);
        boolean startPendingIntent = RemoteViews.startPendingIntent(view, pendingIntent, launchOptions);
        if (startPendingIntent) {
            NotificationRemoteInputManager notificationRemoteInputManager = NotificationRemoteInputManager.this;
            if (notificationEntry == null) {
                notificationRemoteInputManager.getClass();
            } else {
                RemoteInputCoordinator remoteInputCoordinator = notificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.releaseNotificationIfKeptForRemoteInputHistory(notificationEntry);
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
