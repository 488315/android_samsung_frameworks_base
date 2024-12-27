package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface VisualInterruptionDecisionProvider extends CoreStartable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Decision {
        String getLogReason();

        boolean getShouldInterrupt();
    }

    void addLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor);

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
