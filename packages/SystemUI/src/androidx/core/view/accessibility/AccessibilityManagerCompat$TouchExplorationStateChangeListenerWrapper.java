package androidx.core.view.accessibility;

import android.view.accessibility.AccessibilityManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final AccessibilityManagerCompat$TouchExplorationStateChangeListener mListener;

    public AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(AccessibilityManagerCompat$TouchExplorationStateChangeListener accessibilityManagerCompat$TouchExplorationStateChangeListener) {
        this.mListener = accessibilityManagerCompat$TouchExplorationStateChangeListener;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper) {
            return this.mListener.equals(((AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper) obj).mListener);
        }
        return false;
    }

    public final int hashCode() {
        return this.mListener.hashCode();
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        this.mListener.onTouchExplorationStateChanged(z);
    }
}
