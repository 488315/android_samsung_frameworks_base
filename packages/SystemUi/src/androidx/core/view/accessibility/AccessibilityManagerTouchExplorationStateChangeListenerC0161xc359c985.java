package androidx.core.view.accessibility;

import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import androidx.core.view.ViewCompat;
import com.google.android.material.textfield.DropdownMenuEndIconDelegate;
import com.google.android.material.textfield.DropdownMenuEndIconDelegate$$ExternalSyntheticLambda2;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper */
/* loaded from: classes.dex */
public final class AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985 implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final AccessibilityManagerCompat$TouchExplorationStateChangeListener mListener;

    public AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985(AccessibilityManagerCompat$TouchExplorationStateChangeListener accessibilityManagerCompat$TouchExplorationStateChangeListener) {
        this.mListener = accessibilityManagerCompat$TouchExplorationStateChangeListener;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985) {
            return this.mListener.equals(((AccessibilityManagerTouchExplorationStateChangeListenerC0161xc359c985) obj).mListener);
        }
        return false;
    }

    public final int hashCode() {
        return this.mListener.hashCode();
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        DropdownMenuEndIconDelegate dropdownMenuEndIconDelegate = ((DropdownMenuEndIconDelegate$$ExternalSyntheticLambda2) this.mListener).f$0;
        AutoCompleteTextView autoCompleteTextView = dropdownMenuEndIconDelegate.autoCompleteTextView;
        if (autoCompleteTextView != null) {
            if (autoCompleteTextView.getInputType() != 0) {
                return;
            }
            int i = z ? 2 : 1;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(dropdownMenuEndIconDelegate.endIconView, i);
        }
    }
}
