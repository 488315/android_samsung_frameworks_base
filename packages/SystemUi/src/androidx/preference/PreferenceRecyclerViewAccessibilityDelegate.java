package androidx.preference;

import android.os.Bundle;
import android.view.View;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PreferenceRecyclerViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
    public final RecyclerViewAccessibilityDelegate.ItemDelegate mDefaultItemDelegate;
    public final C04191 mItemDelegate;
    public final RecyclerView mRecyclerView;

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.preference.PreferenceRecyclerViewAccessibilityDelegate$1] */
    public PreferenceRecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        super(recyclerView);
        this.mDefaultItemDelegate = super.mItemDelegate;
        this.mItemDelegate = new AccessibilityDelegateCompat() { // from class: androidx.preference.PreferenceRecyclerViewAccessibilityDelegate.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                PreferenceRecyclerViewAccessibilityDelegate preferenceRecyclerViewAccessibilityDelegate = PreferenceRecyclerViewAccessibilityDelegate.this;
                preferenceRecyclerViewAccessibilityDelegate.mDefaultItemDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                RecyclerView recyclerView2 = preferenceRecyclerViewAccessibilityDelegate.mRecyclerView;
                recyclerView2.getClass();
                int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
                RecyclerView.Adapter adapter = recyclerView2.mAdapter;
                if (adapter instanceof PreferenceGroupAdapter) {
                    ((PreferenceGroupAdapter) adapter).getItem(childAdapterPosition);
                }
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                return PreferenceRecyclerViewAccessibilityDelegate.this.mDefaultItemDelegate.performAccessibilityAction(view, i, bundle);
            }
        };
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
    public final AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }
}
