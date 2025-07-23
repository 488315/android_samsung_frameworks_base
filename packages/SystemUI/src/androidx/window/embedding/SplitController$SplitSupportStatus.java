package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SplitController$SplitSupportStatus {
    public static final SplitController$SplitSupportStatus SPLIT_AVAILABLE;
    public static final SplitController$SplitSupportStatus SPLIT_ERROR_PROPERTY_NOT_DECLARED;
    public static final SplitController$SplitSupportStatus SPLIT_UNAVAILABLE;
    public final int rawValue;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
