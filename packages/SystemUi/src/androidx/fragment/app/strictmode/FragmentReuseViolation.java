package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentReuseViolation extends Violation {
    private final String previousFragmentId;

    public FragmentReuseViolation(Fragment fragment, String str) {
        super(fragment, "Attempting to reuse fragment " + fragment + " with previous ID " + str);
        this.previousFragmentId = str;
    }
}
