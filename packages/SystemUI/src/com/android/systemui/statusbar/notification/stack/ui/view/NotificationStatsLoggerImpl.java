package com.android.systemui.statusbar.notification.stack.ui.view;

import android.service.notification.NotificationListenerService;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationStatsLoggerImpl implements NotificationStatsLogger {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final Map expansionStates;
    public final Map lastLoggedVisibilities;
    public final Map lastReportedExpansionValues;
    public final NotificationListenerService notificationListenerService;
    public final NotificationPanelLogger notificationPanelLogger;
    public final IStatusBarService statusBarService;

    public NotificationStatsLoggerImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, NotificationListenerService notificationListenerService, NotificationPanelLogger notificationPanelLogger, IStatusBarService iStatusBarService) {
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.statusBarService = iStatusBarService;
        new LinkedHashMap();
        this.expansionStates = new ConcurrentHashMap();
        this.lastReportedExpansionValues = new ConcurrentHashMap();
    }

    @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger
    public final void onNotificationExpansionChanged(String str, boolean z, int i, boolean z2) {
        ExpansionState expansionState = new ExpansionState(str, z, i, z2);
        ((ConcurrentHashMap) this.expansionStates).put(str, expansionState);
        if (expansionState.visible) {
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.lastReportedExpansionValues;
            String str2 = expansionState.key;
            Boolean bool = (Boolean) concurrentHashMap.get(str2);
            boolean z3 = expansionState.isExpanded;
            if (bool != null || z3) {
                if (bool == null || !bool.equals(Boolean.valueOf(z3))) {
                    BuildersKt.launch$default(this.applicationScope, null, null, new NotificationStatsLoggerImpl$logNotificationExpansionChange$1(this, expansionState, null), 3);
                    ((ConcurrentHashMap) this.lastReportedExpansionValues).put(str2, Boolean.valueOf(z3));
                }
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ExpansionState {
        public final boolean isExpanded;
        public final boolean isUserAction;
        public final String key;
        public final int location;
        public final boolean visible;

        public ExpansionState(String str, boolean z, boolean z2, boolean z3, int i) {
            this.key = str;
            this.isUserAction = z;
            this.isExpanded = z2;
            this.visible = z3;
            this.location = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ExpansionState)) {
                return false;
            }
            ExpansionState expansionState = (ExpansionState) obj;
            return Intrinsics.areEqual(this.key, expansionState.key) && this.isUserAction == expansionState.isUserAction && this.isExpanded == expansionState.isExpanded && this.visible == expansionState.visible && this.location == expansionState.location;
        }

        public final int hashCode() {
            return Integer.hashCode(this.location) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.isUserAction), 31, this.isExpanded), 31, this.visible);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ExpansionState(key=");
            sb.append(this.key);
            sb.append(", isUserAction=");
            sb.append(this.isUserAction);
            sb.append(", isExpanded=");
            sb.append(this.isExpanded);
            sb.append(", visible=");
            sb.append(this.visible);
            sb.append(", location=");
            return Anchor$$ExternalSyntheticOutline0.m(this.location, ")", sb);
        }

        public ExpansionState(String str, boolean z, int i, boolean z2) {
            this(str, z2, z, (i & 5) != 0, i);
        }
    }

    public static /* synthetic */ void getLastReportedExpansionValues$annotations() {
    }
}
