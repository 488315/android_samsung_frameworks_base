package com.android.systemui.statusbar.phone.ongoingcall.shared.model;

import android.app.PendingIntent;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface OngoingCallModel {

    public final class InCall implements OngoingCallModel {
        public final String appName;
        public final PendingIntent intent;
        public final boolean isAppVisible;
        public final StatusBarIconView notificationIconView;
        public final InstanceId notificationInstanceId;
        public final String notificationKey;
        public final PromotedNotificationContentModels promotedContent;
        public final long startTimeMs;

        public InCall(long j, StatusBarIconView statusBarIconView, PendingIntent pendingIntent, String str, String str2, PromotedNotificationContentModels promotedNotificationContentModels, boolean z, InstanceId instanceId) {
            this.startTimeMs = j;
            this.notificationIconView = statusBarIconView;
            this.intent = pendingIntent;
            this.notificationKey = str;
            this.appName = str2;
            this.promotedContent = promotedNotificationContentModels;
            this.isAppVisible = z;
            this.notificationInstanceId = instanceId;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InCall)) {
                return false;
            }
            InCall inCall = (InCall) obj;
            return this.startTimeMs == inCall.startTimeMs && Intrinsics.areEqual(this.notificationIconView, inCall.notificationIconView) && Intrinsics.areEqual(this.intent, inCall.intent) && Intrinsics.areEqual(this.notificationKey, inCall.notificationKey) && Intrinsics.areEqual(this.appName, inCall.appName) && Intrinsics.areEqual(this.promotedContent, inCall.promotedContent) && this.isAppVisible == inCall.isAppVisible && Intrinsics.areEqual(this.notificationInstanceId, inCall.notificationInstanceId);
        }

        public final int hashCode() {
            int iHashCode = Long.hashCode(this.startTimeMs) * 31;
            StatusBarIconView statusBarIconView = this.notificationIconView;
            int iHashCode2 = (iHashCode + (statusBarIconView == null ? 0 : statusBarIconView.hashCode())) * 31;
            PendingIntent pendingIntent = this.intent;
            int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode2 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31, 31, this.notificationKey), 31, this.appName);
            PromotedNotificationContentModels promotedNotificationContentModels = this.promotedContent;
            int iM2 = TransitionData$$ExternalSyntheticOutline0.m((iM + (promotedNotificationContentModels == null ? 0 : promotedNotificationContentModels.hashCode())) * 31, 31, this.isAppVisible);
            InstanceId instanceId = this.notificationInstanceId;
            return iM2 + (instanceId != null ? instanceId.hashCode() : 0);
        }

        @Override // com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel
        public final String logString() {
            boolean z = this.promotedContent != null;
            StringBuilder sb = new StringBuilder("InCall(notifKey=");
            sb.append(this.notificationKey);
            sb.append(" hasPromotedContent=");
            sb.append(z);
            sb.append(" isAppVisible=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isAppVisible, ")");
        }

        public final String toString() {
            return "InCall(startTimeMs=" + this.startTimeMs + ", notificationIconView=" + this.notificationIconView + ", intent=" + this.intent + ", notificationKey=" + this.notificationKey + ", appName=" + this.appName + ", promotedContent=" + this.promotedContent + ", isAppVisible=" + this.isAppVisible + ", notificationInstanceId=" + this.notificationInstanceId + ")";
        }
    }

    public final class NoCall implements OngoingCallModel {
        public static final NoCall INSTANCE = new NoCall();

        private NoCall() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoCall);
        }

        public final int hashCode() {
            return 2137519088;
        }

        @Override // com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel
        public final String logString() {
            return "NoCall";
        }

        public final String toString() {
            return "NoCall";
        }
    }

    String logString();
}
