package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;

/* loaded from: classes3.dex */
public interface VisualInterruptionDecisionProvider extends CoreStartable {

    public interface Decision {
        String getLogReason();

        boolean getShouldInterrupt();
    }

    void addLegacySuppressor(StatusBarNotificationPresenter.AnonymousClass3 anonymousClass3);

    void logFullScreenIntentDecision(NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl fullScreenIntentDecisionImpl);

    Decision makeAndLogBubbleDecision(NotificationEntry notificationEntry);

    Decision makeAndLogHeadsUpDecision(NotificationEntry notificationEntry);

    NotificationInterruptStateProviderWrapper.FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry);

    Decision makeUnloggedHeadsUpDecision(NotificationEntry notificationEntry);

    void removeCondition(VisualInterruptionCondition visualInterruptionCondition);

    void removeFilter(VisualInterruptionFilter visualInterruptionFilter);

    void removeLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor);

    @Override // com.android.systemui.CoreStartable
    default void start() {
    }
}
