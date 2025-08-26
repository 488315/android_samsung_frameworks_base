package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return KeyguardCoordinatorLogger.logTrackingUnseen$lambda$3((LogMessage) obj);
            case 1:
                return KeyguardCoordinatorLogger.logSeenOnLockscreen$lambda$1((LogMessage) obj);
            case 2:
                return KeyguardCoordinatorLogger.logStopTrackingLockscreenSeenDuration$lambda$22((LogMessage) obj);
            case 3:
                return KeyguardCoordinatorLogger.logAllMarkedSeenOnUnlock$lambda$5((LogMessage) obj);
            case 4:
                return KeyguardCoordinatorLogger.logUnseenRemoved$lambda$11((LogMessage) obj);
            case 5:
                return KeyguardCoordinatorLogger.logTrackingLockscreenSeenDuration$lambda$20((LogMessage) obj);
            case 6:
                return KeyguardCoordinatorLogger.logTrackingLockscreenSeenDuration$lambda$18((LogMessage) obj);
            case 7:
                return ((NotificationEntry) obj).mKey;
            case 8:
                return KeyguardCoordinatorLogger.logProviderHasFilteredOutSeenNotifs$lambda$13((LogMessage) obj);
            case 9:
                return KeyguardCoordinatorLogger.logRemoveSeenOnLockscreen$lambda$26((LogMessage) obj);
            case 10:
                return KeyguardCoordinatorLogger.logResetSeenOnLockscreen$lambda$24((LogMessage) obj);
            case 11:
                return KeyguardCoordinatorLogger.logUnseenUpdated$lambda$9((LogMessage) obj);
            case 12:
                return KeyguardCoordinatorLogger.logUnseenAdded$lambda$7((LogMessage) obj);
            default:
                return KeyguardCoordinatorLogger.logUnseenHun$lambda$15((LogMessage) obj);
        }
    }
}
