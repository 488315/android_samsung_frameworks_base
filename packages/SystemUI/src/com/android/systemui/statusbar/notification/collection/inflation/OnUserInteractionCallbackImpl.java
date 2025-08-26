package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;

/* loaded from: classes3.dex */
public class OnUserInteractionCallbackImpl implements OnUserInteractionCallback {
    public final HeadsUpManager mHeadsUpManager;
    public final NotifCollection mNotifCollection;
    public final StatusBarStateController mStatusBarStateController;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final VisualStabilityCoordinator mVisualStabilityCoordinator;

    public OnUserInteractionCallbackImpl(NotificationVisibilityProvider notificationVisibilityProvider, NotifCollection notifCollection, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, VisualStabilityCoordinator visualStabilityCoordinator) {
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mNotifCollection = notifCollection;
        this.mHeadsUpManager = headsUpManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mVisualStabilityCoordinator = visualStabilityCoordinator;
    }

    public final NotifCollection.FutureDismissal registerFutureDismissal(NotificationEntry notificationEntry, int i) {
        OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0 onUserInteractionCallbackImpl$$ExternalSyntheticLambda0 = new OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0(this);
        NotifCollection notifCollection = this.mNotifCollection;
        NotifCollection.FutureDismissal futureDismissal = (NotifCollection.FutureDismissal) notifCollection.mFutureDismissals.get(notificationEntry.mKey);
        NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
        if (futureDismissal != null) {
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(10);
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) logMessageObtain).str1 = futureDismissal.mLabel;
            logBuffer.commit(logMessageObtain);
            return futureDismissal;
        }
        NotifCollection.FutureDismissal futureDismissal2 = new NotifCollection.FutureDismissal(notifCollection, notificationEntry, i, onUserInteractionCallbackImpl$$ExternalSyntheticLambda0);
        notifCollection.mFutureDismissals.put(notificationEntry.mKey, futureDismissal2);
        notifCollectionLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda32 = new NotifCollectionLogger$$ExternalSyntheticLambda3(11);
        LogBuffer logBuffer2 = notifCollectionLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda32, null);
        ((LogMessageImpl) logMessageObtain2).str1 = futureDismissal2.mLabel;
        logBuffer2.commit(logMessageObtain2);
        return futureDismissal2;
    }
}
