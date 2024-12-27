package com.android.systemui.biometrics.domain.interactor;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface CredentialStatus {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Fail extends CredentialStatus {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Error implements Fail {
            public final String error;
            public final Integer remainingAttempts;
            public final String urgentMessage;

            public Error() {
                this(null, null, null, 7, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Error)) {
                    return false;
                }
                Error error = (Error) obj;
                return Intrinsics.areEqual(this.error, error.error) && Intrinsics.areEqual(this.remainingAttempts, error.remainingAttempts) && Intrinsics.areEqual(this.urgentMessage, error.urgentMessage);
            }

            public final int hashCode() {
                String str = this.error;
                int hashCode = (str == null ? 0 : str.hashCode()) * 31;
                Integer num = this.remainingAttempts;
                int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
                String str2 = this.urgentMessage;
                return hashCode2 + (str2 != null ? str2.hashCode() : 0);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Error(error=");
                sb.append(this.error);
                sb.append(", remainingAttempts=");
                sb.append(this.remainingAttempts);
                sb.append(", urgentMessage=");
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.urgentMessage, ")");
            }

            public Error(String str, Integer num, String str2) {
                this.error = str;
                this.remainingAttempts = num;
                this.urgentMessage = str2;
            }

            public /* synthetic */ Error(String str, Integer num, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : str2);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Throttled implements Fail {
            public final String error;

            public Throttled(String str) {
                this.error = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Throttled) && Intrinsics.areEqual(this.error, ((Throttled) obj).error);
            }

            public final int hashCode() {
                return this.error.hashCode();
            }

            public final String toString() {
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Throttled(error="), this.error, ")");
            }
        }
    }
}
