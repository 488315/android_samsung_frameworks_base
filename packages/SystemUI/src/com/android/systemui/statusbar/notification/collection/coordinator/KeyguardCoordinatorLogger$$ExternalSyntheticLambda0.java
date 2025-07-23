package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String logTrackingUnseen$lambda$3;
        String logSeenOnLockscreen$lambda$1;
        String logStopTrackingLockscreenSeenDuration$lambda$22;
        String logAllMarkedSeenOnUnlock$lambda$5;
        String logUnseenRemoved$lambda$11;
        String logTrackingLockscreenSeenDuration$lambda$20;
        String logTrackingLockscreenSeenDuration$lambda$18;
        CharSequence charSequence;
        String logProviderHasFilteredOutSeenNotifs$lambda$13;
        String logRemoveSeenOnLockscreen$lambda$26;
        String logResetSeenOnLockscreen$lambda$24;
        String logUnseenUpdated$lambda$9;
        String logUnseenAdded$lambda$7;
        String logUnseenHun$lambda$15;
        switch (this.$r8$classId) {
            case 0:
                logTrackingUnseen$lambda$3 = KeyguardCoordinatorLogger.logTrackingUnseen$lambda$3((LogMessage) obj);
                return logTrackingUnseen$lambda$3;
            case 1:
                logSeenOnLockscreen$lambda$1 = KeyguardCoordinatorLogger.logSeenOnLockscreen$lambda$1((LogMessage) obj);
                return logSeenOnLockscreen$lambda$1;
            case 2:
                logStopTrackingLockscreenSeenDuration$lambda$22 = KeyguardCoordinatorLogger.logStopTrackingLockscreenSeenDuration$lambda$22((LogMessage) obj);
                return logStopTrackingLockscreenSeenDuration$lambda$22;
            case 3:
                logAllMarkedSeenOnUnlock$lambda$5 = KeyguardCoordinatorLogger.logAllMarkedSeenOnUnlock$lambda$5((LogMessage) obj);
                return logAllMarkedSeenOnUnlock$lambda$5;
            case 4:
                logUnseenRemoved$lambda$11 = KeyguardCoordinatorLogger.logUnseenRemoved$lambda$11((LogMessage) obj);
                return logUnseenRemoved$lambda$11;
            case 5:
                logTrackingLockscreenSeenDuration$lambda$20 = KeyguardCoordinatorLogger.logTrackingLockscreenSeenDuration$lambda$20((LogMessage) obj);
                return logTrackingLockscreenSeenDuration$lambda$20;
            case 6:
                logTrackingLockscreenSeenDuration$lambda$18 = KeyguardCoordinatorLogger.logTrackingLockscreenSeenDuration$lambda$18((LogMessage) obj);
                return logTrackingLockscreenSeenDuration$lambda$18;
            case 7:
                charSequence = ((NotificationEntry) obj).mKey;
                return charSequence;
            case 8:
                logProviderHasFilteredOutSeenNotifs$lambda$13 = KeyguardCoordinatorLogger.logProviderHasFilteredOutSeenNotifs$lambda$13((LogMessage) obj);
                return logProviderHasFilteredOutSeenNotifs$lambda$13;
            case 9:
                logRemoveSeenOnLockscreen$lambda$26 = KeyguardCoordinatorLogger.logRemoveSeenOnLockscreen$lambda$26((LogMessage) obj);
                return logRemoveSeenOnLockscreen$lambda$26;
            case 10:
                logResetSeenOnLockscreen$lambda$24 = KeyguardCoordinatorLogger.logResetSeenOnLockscreen$lambda$24((LogMessage) obj);
                return logResetSeenOnLockscreen$lambda$24;
            case 11:
                logUnseenUpdated$lambda$9 = KeyguardCoordinatorLogger.logUnseenUpdated$lambda$9((LogMessage) obj);
                return logUnseenUpdated$lambda$9;
            case 12:
                logUnseenAdded$lambda$7 = KeyguardCoordinatorLogger.logUnseenAdded$lambda$7((LogMessage) obj);
                return logUnseenAdded$lambda$7;
            default:
                logUnseenHun$lambda$15 = KeyguardCoordinatorLogger.logUnseenHun$lambda$15((LogMessage) obj);
                return logUnseenHun$lambda$15;
        }
    }
}
