package com.android.internal.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.ChooserActivity;
import com.android.internal.hidden_from_bootclasspath.android.service.chooser.Flags;
import com.android.internal.widget.GridLayoutManager;
import com.android.internal.widget.RecyclerView;

/* loaded from: classes5.dex */
public class ChooserGridLayoutManager extends GridLayoutManager {
    private CharSequence mAllAppListGroupTitle;
    private RecyclerView mRecyclerView;
    private CharSequence mShortcutGroupTitle;
    private CharSequence mSuggestedAppsGroupTitle;
    private boolean mVerticalScrollEnabled;

    public ChooserGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mShortcutGroupTitle = "";
        this.mSuggestedAppsGroupTitle = "";
        this.mAllAppListGroupTitle = "";
        this.mVerticalScrollEnabled = true;
        if (Flags.announceShortcutsAndSuggestedAppsLegacy()) {
            readGroupTitles(context);
        }
    }

    public ChooserGridLayoutManager(Context context, int i) {
        super(context, i);
        this.mShortcutGroupTitle = "";
        this.mSuggestedAppsGroupTitle = "";
        this.mAllAppListGroupTitle = "";
        this.mVerticalScrollEnabled = true;
        if (Flags.announceShortcutsAndSuggestedAppsLegacy()) {
            readGroupTitles(context);
        }
    }

    public ChooserGridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
        this.mShortcutGroupTitle = "";
        this.mSuggestedAppsGroupTitle = "";
        this.mAllAppListGroupTitle = "";
        this.mVerticalScrollEnabled = true;
        if (Flags.announceShortcutsAndSuggestedAppsLegacy()) {
            readGroupTitles(context);
        }
    }

    private void readGroupTitles(Context context) {
        this.mShortcutGroupTitle = context.getString(R.string.shortcut_group_a11y_title);
        this.mSuggestedAppsGroupTitle = context.getString(R.string.suggested_apps_group_a11y_title);
        this.mAllAppListGroupTitle = context.getString(R.string.all_apps_group_a11y_title);
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        this.mRecyclerView = null;
    }

    @Override // com.android.internal.widget.GridLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getRowCountForAccessibility(recycler, state) - 1;
    }

    void setVerticalScrollEnabled(boolean z) {
        this.mVerticalScrollEnabled = z;
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mVerticalScrollEnabled && super.canScrollVertically();
    }

    @Override // com.android.internal.widget.GridLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(recycler, state, view, accessibilityNodeInfo);
        if (Flags.announceShortcutsAndSuggestedAppsLegacy() && (view instanceof ViewGroup)) {
            if (view.getId() == 16909820) {
                accessibilityNodeInfo.setClassName(ListView.class.getName());
                accessibilityNodeInfo.setContainerTitle(this.mShortcutGroupTitle);
                accessibilityNodeInfo.setCollectionInfo(createShortcutsA11yCollectionInfo((ViewGroup) view));
            } else if (view.getId() == 16908932) {
                RecyclerView recyclerView = this.mRecyclerView;
                RecyclerView.Adapter adapter = recyclerView == null ? null : recyclerView.getAdapter();
                ChooserListAdapter listAdapter = adapter instanceof ChooserActivity.ChooserGridAdapter ? ((ChooserActivity.ChooserGridAdapter) adapter).getListAdapter() : null;
                accessibilityNodeInfo.setClassName(ListView.class.getName());
                accessibilityNodeInfo.setCollectionInfo(createSuggestedAppsA11yCollectionInfo((ViewGroup) view));
                if (listAdapter == null || listAdapter.getAlphaTargetCount() > 0) {
                    accessibilityNodeInfo.setContainerTitle(this.mSuggestedAppsGroupTitle);
                } else {
                    accessibilityNodeInfo.setContainerTitle(this.mAllAppListGroupTitle);
                }
            }
        }
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfo);
        if (Flags.announceShortcutsAndSuggestedAppsLegacy()) {
            accessibilityNodeInfo.setContainerTitle(this.mAllAppListGroupTitle);
        }
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public boolean isLayoutHierarchical(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return Flags.announceShortcutsAndSuggestedAppsLegacy() || super.isLayoutHierarchical(recycler, state);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private AccessibilityNodeInfo.CollectionInfo createShortcutsA11yCollectionInfo(ViewGroup viewGroup) {
        int i;
        int i2 = 0;
        int iMax = 0;
        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
            View childAt = viewGroup.getChildAt(i3);
            if (childAt instanceof ViewGroup) {
                ViewGroup viewGroup2 = (ViewGroup) childAt;
                if (childAt.getVisibility() == 0) {
                    int i4 = 0;
                    i = 0;
                    while (true) {
                        if (i4 >= viewGroup2.getChildCount()) {
                            break;
                        }
                        View childAt2 = viewGroup2.getChildAt(i4);
                        if (childAt2 != null && childAt2.getVisibility() == 0) {
                            i++;
                            if (childAt2 instanceof TextView) {
                                i = 1;
                                break;
                            }
                        }
                        i4++;
                    }
                } else {
                    i = 0;
                }
            }
            if (i > 0) {
                i2++;
                iMax = Math.max(iMax, i);
            }
        }
        return AccessibilityNodeInfo.CollectionInfo.obtain(i2, iMax, false);
    }

    private AccessibilityNodeInfo.CollectionInfo createSuggestedAppsA11yCollectionInfo(ViewGroup viewGroup) {
        int i = 0;
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            if (viewGroup.getChildAt(i2).getVisibility() == 0) {
                i++;
            }
        }
        return AccessibilityNodeInfo.CollectionInfo.obtain(1, i, false);
    }
}
