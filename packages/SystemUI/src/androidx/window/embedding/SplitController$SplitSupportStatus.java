package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SplitController$SplitSupportStatus {
    public static final SplitController$SplitSupportStatus SPLIT_AVAILABLE;
    public static final SplitController$SplitSupportStatus SPLIT_ERROR_PROPERTY_NOT_DECLARED;
    public static final SplitController$SplitSupportStatus SPLIT_UNAVAILABLE;
    public final int rawValue;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        SPLIT_AVAILABLE = new SplitController$SplitSupportStatus(0);
        SPLIT_UNAVAILABLE = new SplitController$SplitSupportStatus(1);
        SPLIT_ERROR_PROPERTY_NOT_DECLARED = new SplitController$SplitSupportStatus(2);
    }

    private SplitController$SplitSupportStatus(int i) {
        this.rawValue = i;
    }

    public final String toString() {
        int i = this.rawValue;
        return i != 0 ? i != 1 ? i != 2 ? "UNKNOWN" : "SplitSupportStatus: ERROR_SPLIT_PROPERTY_NOT_DECLARED" : "SplitSupportStatus: UNAVAILABLE" : "SplitSupportStatus: AVAILABLE";
    }
}
