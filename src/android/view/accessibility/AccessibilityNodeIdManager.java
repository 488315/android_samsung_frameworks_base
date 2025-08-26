package android.view.accessibility;

import android.view.View;

/* loaded from: classes4.dex */
public final class AccessibilityNodeIdManager {
    private static AccessibilityNodeIdManager sIdManager;
    private WeakSparseArray<View> mIdsToViews = new WeakSparseArray<>();

    public static synchronized AccessibilityNodeIdManager getInstance() {
        if (sIdManager == null) {
            sIdManager = new AccessibilityNodeIdManager();
        }
        return sIdManager;
    }

    private AccessibilityNodeIdManager() {
    }

    public void registerViewWithId(View view, int i) {
        synchronized (this.mIdsToViews) {
            this.mIdsToViews.append(i, view);
        }
    }

    public void unregisterViewWithId(int i) {
        synchronized (this.mIdsToViews) {
            this.mIdsToViews.remove(i);
        }
    }

    public View findView(int i) {
        View view;
        synchronized (this.mIdsToViews) {
            view = this.mIdsToViews.get(i);
        }
        if (view == null || !view.includeForAccessibility()) {
            return null;
        }
        return view;
    }
}
