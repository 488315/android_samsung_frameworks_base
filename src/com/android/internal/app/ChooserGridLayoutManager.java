package com.android.internal.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;
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
            if (view.getId() == 16909819) {
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0043 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.view.accessibility.AccessibilityNodeInfo.CollectionInfo createShortcutsA11yCollectionInfo(android.view.ViewGroup r9) {
        /*
            r8 = this;
            r8 = 0
            r0 = r8
            r1 = r0
            r2 = r1
        L4:
            int r3 = r9.getChildCount()
            if (r0 >= r3) goto L46
            android.view.View r3 = r9.getChildAt(r0)
            boolean r4 = r3 instanceof android.view.ViewGroup
            if (r4 == 0) goto L3a
            r4 = r3
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            int r3 = r3.getVisibility()
            if (r3 != 0) goto L3a
            r3 = r8
            r5 = r3
        L1d:
            int r6 = r4.getChildCount()
            if (r3 >= r6) goto L3b
            android.view.View r6 = r4.getChildAt(r3)
            if (r6 == 0) goto L37
            int r7 = r6.getVisibility()
            if (r7 != 0) goto L37
            int r5 = r5 + 1
            boolean r6 = r6 instanceof android.widget.TextView
            if (r6 == 0) goto L37
            r5 = 1
            goto L3b
        L37:
            int r3 = r3 + 1
            goto L1d
        L3a:
            r5 = r8
        L3b:
            if (r5 <= 0) goto L43
            int r1 = r1 + 1
            int r2 = java.lang.Math.max(r2, r5)
        L43:
            int r0 = r0 + 1
            goto L4
        L46:
            android.view.accessibility.AccessibilityNodeInfo$CollectionInfo r8 = android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(r1, r2, r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.ChooserGridLayoutManager.createShortcutsA11yCollectionInfo(android.view.ViewGroup):android.view.accessibility.AccessibilityNodeInfo$CollectionInfo");
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
