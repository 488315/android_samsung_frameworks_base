package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TargetFragmentUsageViolation extends Violation {
    public TargetFragmentUsageViolation(Fragment fragment, String str) {
        super(fragment, str);
    }

    public /* synthetic */ TargetFragmentUsageViolation(Fragment fragment, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fragment, (i & 2) != 0 ? null : str);
    }
}
