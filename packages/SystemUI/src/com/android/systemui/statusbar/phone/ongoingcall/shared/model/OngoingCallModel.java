package com.android.systemui.statusbar.phone.ongoingcall.shared.model;

import android.app.PendingIntent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface OngoingCallModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class InCall implements OngoingCallModel {
        public final PendingIntent intent;
        public final long startTimeMs;

        public InCall(long j, PendingIntent pendingIntent) {
            this.startTimeMs = j;
            this.intent = pendingIntent;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InCall)) {
                return false;
            }
            InCall inCall = (InCall) obj;
            return this.startTimeMs == inCall.startTimeMs && Intrinsics.areEqual(this.intent, inCall.intent);
        }

        public final int hashCode() {
            int hashCode = Long.hashCode(this.startTimeMs) * 31;
            PendingIntent pendingIntent = this.intent;
            return hashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode());
        }

        public final String toString() {
            return "InCall(startTimeMs=" + this.startTimeMs + ", intent=" + this.intent + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

        public final String toString() {
            return "NoCall";
        }
    }
}
