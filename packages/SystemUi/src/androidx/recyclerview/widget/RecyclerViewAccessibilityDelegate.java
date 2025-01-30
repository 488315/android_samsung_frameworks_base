package androidx.recyclerview.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    public final ItemDelegate mItemDelegate;
    public final RecyclerView mRecyclerView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ItemDelegate extends AccessibilityDelegateCompat {
        public final Map mOriginalItemDelegates = new WeakHashMap();
        public final RecyclerViewAccessibilityDelegate mRecyclerViewDelegate;

        public ItemDelegate(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
            this.mRecyclerViewDelegate = recyclerViewAccessibilityDelegate;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            return accessibilityDelegateCompat != null ? accessibilityDelegateCompat.dispatchPopulateAccessibilityEvent(view, accessibilityEvent) : super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            return accessibilityDelegateCompat != null ? accessibilityDelegateCompat.getAccessibilityNodeProvider(view) : super.getAccessibilityNodeProvider(view);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.onInitializeAccessibilityEvent(view, accessibilityEvent);
            } else {
                super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            RecyclerView.LayoutManager layoutManager;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = this.mRecyclerViewDelegate;
            RecyclerView recyclerView = recyclerViewAccessibilityDelegate.mRecyclerView;
            boolean z = !recyclerView.mFirstLayoutComplete || recyclerView.mDataSetHasChangedAfterLayout || recyclerView.mAdapterHelper.hasPendingUpdates();
            AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
            View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
            if (z || (layoutManager = recyclerViewAccessibilityDelegate.mRecyclerView.mLayout) == null) {
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                return;
            }
            layoutManager.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            } else {
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.onPopulateAccessibilityEvent(view, accessibilityEvent);
            } else {
                super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(viewGroup);
            return accessibilityDelegateCompat != null ? accessibilityDelegateCompat.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent) : super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = this.mRecyclerViewDelegate;
            RecyclerView recyclerView = recyclerViewAccessibilityDelegate.mRecyclerView;
            if (!(!recyclerView.mFirstLayoutComplete || recyclerView.mDataSetHasChangedAfterLayout || recyclerView.mAdapterHelper.hasPendingUpdates())) {
                RecyclerView recyclerView2 = recyclerViewAccessibilityDelegate.mRecyclerView;
                if (recyclerView2.mLayout != null) {
                    AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
                    if (accessibilityDelegateCompat != null) {
                        if (accessibilityDelegateCompat.performAccessibilityAction(view, i, bundle)) {
                            return true;
                        }
                    } else if (super.performAccessibilityAction(view, i, bundle)) {
                        return true;
                    }
                    RecyclerView.Recycler recycler = recyclerView2.mLayout.mRecyclerView.mRecycler;
                    return false;
                }
            }
            return super.performAccessibilityAction(view, i, bundle);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void sendAccessibilityEvent(View view, int i) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.sendAccessibilityEvent(view, i);
            } else {
                super.sendAccessibilityEvent(view, i);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            AccessibilityDelegateCompat accessibilityDelegateCompat = (AccessibilityDelegateCompat) ((WeakHashMap) this.mOriginalItemDelegates).get(view);
            if (accessibilityDelegateCompat != null) {
                accessibilityDelegateCompat.sendAccessibilityEventUnchecked(view, accessibilityEvent);
            } else {
                super.sendAccessibilityEventUnchecked(view, accessibilityEvent);
            }
        }
    }

    public RecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        AccessibilityDelegateCompat itemDelegate = getItemDelegate();
        if (itemDelegate == null || !(itemDelegate instanceof ItemDelegate)) {
            this.mItemDelegate = new ItemDelegate(this);
        } else {
            this.mItemDelegate = (ItemDelegate) itemDelegate;
        }
    }

    public AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        RecyclerView.LayoutManager layoutManager;
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = this.mRecyclerView;
            if ((!recyclerView.mFirstLayoutComplete || recyclerView.mDataSetHasChangedAfterLayout || recyclerView.mAdapterHelper.hasPendingUpdates()) || (layoutManager = ((RecyclerView) view).mLayout) == null) {
                return;
            }
            layoutManager.onInitializeAccessibilityEvent(accessibilityEvent);
        }
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        RecyclerView.LayoutManager layoutManager;
        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
        RecyclerView recyclerView = this.mRecyclerView;
        if ((!recyclerView.mFirstLayoutComplete || recyclerView.mDataSetHasChangedAfterLayout || recyclerView.mAdapterHelper.hasPendingUpdates()) || (layoutManager = recyclerView.mLayout) == null) {
            return;
        }
        RecyclerView recyclerView2 = layoutManager.mRecyclerView;
        layoutManager.onInitializeAccessibilityNodeInfo(recyclerView2.mRecycler, recyclerView2.mState, accessibilityNodeInfoCompat);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        RecyclerView.LayoutManager layoutManager;
        boolean z = true;
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView.mFirstLayoutComplete && !recyclerView.mDataSetHasChangedAfterLayout && !recyclerView.mAdapterHelper.hasPendingUpdates()) {
            z = false;
        }
        if (z || (layoutManager = recyclerView.mLayout) == null) {
            return false;
        }
        RecyclerView recyclerView2 = layoutManager.mRecyclerView;
        return layoutManager.performAccessibilityAction(recyclerView2.mRecycler, recyclerView2.mState, i, bundle);
    }
}
