package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class WrongNestedHierarchyViolation extends Violation {
    private final int containerId;
    private final Fragment expectedParentFragment;

    public WrongNestedHierarchyViolation(Fragment fragment, Fragment fragment2, int i) {
        StringBuilder sb = new StringBuilder("Attempting to nest fragment ");
        sb.append(fragment);
        sb.append(" within the view of parent fragment ");
        sb.append(fragment2);
        sb.append(" via container with ID ");
        super(fragment, ReorderTile$$ExternalSyntheticOutline0.m(i, " without using parent's childFragmentManager", sb));
        this.expectedParentFragment = fragment2;
        this.containerId = i;
    }
}
