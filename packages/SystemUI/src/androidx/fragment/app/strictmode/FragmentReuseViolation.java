package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;

/* loaded from: classes.dex */
public final class FragmentReuseViolation extends Violation {
    private final String previousFragmentId;

    public FragmentReuseViolation(Fragment fragment, String str) {
        super(fragment, "Attempting to reuse fragment " + fragment + " with previous ID " + str);
        this.previousFragmentId = str;
    }
}
