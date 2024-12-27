package android.os.strictmode;

public final class UntaggedSocketViolation extends Violation {
    public UntaggedSocketViolation() {
        super(
                "Untagged socket detected; use TrafficStats.setTrafficStatsTag() to track all"
                    + " network usage");
    }
}
