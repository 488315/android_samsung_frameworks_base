package com.android.systemui.screenshot.policy;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.data.model.DisplayContentModel;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface CapturePolicy {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PolicyResult {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Matched implements PolicyResult {
            public final LegacyCaptureParameters parameters;
            public final String policy;
            public final String reason;

            public Matched(String str, String str2, LegacyCaptureParameters legacyCaptureParameters) {
                this.policy = str;
                this.reason = str2;
                this.parameters = legacyCaptureParameters;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Matched)) {
                    return false;
                }
                Matched matched = (Matched) obj;
                return Intrinsics.areEqual(this.policy, matched.policy) && Intrinsics.areEqual(this.reason, matched.reason) && Intrinsics.areEqual(this.parameters, matched.parameters);
            }

            public final int hashCode() {
                return this.parameters.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.policy.hashCode() * 31, 31, this.reason);
            }

            public final String toString() {
                return "Matched(policy=" + this.policy + ", reason=" + this.reason + ", parameters=" + this.parameters + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class NotMatched implements PolicyResult {
            public final String policy;
            public final String reason;

            public NotMatched(String str, String str2) {
                this.policy = str;
                this.reason = str2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof NotMatched)) {
                    return false;
                }
                NotMatched notMatched = (NotMatched) obj;
                return Intrinsics.areEqual(this.policy, notMatched.policy) && Intrinsics.areEqual(this.reason, notMatched.reason);
            }

            public final int hashCode() {
                return this.reason.hashCode() + (this.policy.hashCode() * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("NotMatched(policy=");
                sb.append(this.policy);
                sb.append(", reason=");
                return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.reason, ")");
            }
        }
    }

    Object check(DisplayContentModel displayContentModel, ContinuationImpl continuationImpl);
}
