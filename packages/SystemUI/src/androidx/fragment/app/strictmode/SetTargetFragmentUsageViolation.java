package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SetTargetFragmentUsageViolation extends TargetFragmentUsageViolation {
    private final int requestCode;
    private final Fragment targetFragment;

    public SetTargetFragmentUsageViolation(Fragment fragment, Fragment fragment2, int i) {
        super(fragment, "Attempting to set target fragment " + fragment2 + " with request code " + i + " for fragment " + fragment);
        this.targetFragment = fragment2;
        this.requestCode = i;
    }
}
