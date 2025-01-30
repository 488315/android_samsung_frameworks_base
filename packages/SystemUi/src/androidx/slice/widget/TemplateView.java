package androidx.slice.widget;

import android.R;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.widget.SliceViewPolicy;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TemplateView extends SliceChildView implements SliceViewPolicy.PolicyChangeListener {
    public SliceAdapter mAdapter;
    public List mDisplayedItems;
    public int mDisplayedItemsHeight;
    public final View mForeground;
    public ListContent mListContent;
    public final int[] mLoc;
    public SliceView mParent;
    public final RecyclerView mRecyclerView;

    public TemplateView(Context context) {
        super(context);
        this.mDisplayedItems = new ArrayList();
        this.mDisplayedItemsHeight = 0;
        this.mLoc = new int[2];
        RecyclerView recyclerView = new RecyclerView(getContext());
        this.mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SliceAdapter sliceAdapter = new SliceAdapter(context);
        this.mAdapter = sliceAdapter;
        recyclerView.setAdapter(sliceAdapter);
        SliceAdapter sliceAdapter2 = new SliceAdapter(context);
        this.mAdapter = sliceAdapter2;
        recyclerView.setAdapter(sliceAdapter2);
        addView(recyclerView);
        View view = new View(getContext());
        this.mForeground = view;
        view.setBackground(SliceViewUtil.getDrawable(R.attr.selectableItemBackground, getContext()));
        addView(view);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        view.setLayoutParams(layoutParams);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final Set getLoadingActions() {
        return this.mAdapter.mLoadingActions;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        SliceView sliceView = (SliceView) getParent();
        this.mParent = sliceView;
        SliceAdapter sliceAdapter = this.mAdapter;
        sliceAdapter.mParent = sliceView;
        sliceAdapter.mTemplateView = this;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        if (!this.mViewPolicy.mScrollable && this.mDisplayedItems.size() > 0 && this.mDisplayedItemsHeight != size) {
            updateDisplayedItems(size);
        }
        super.onMeasure(i, i2);
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void resetView() {
        this.mDisplayedItemsHeight = 0;
        this.mDisplayedItems.clear();
        this.mAdapter.setSliceItems(getMode(), null);
        this.mListContent = null;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setAllowTwoLines(boolean z) {
        SliceAdapter sliceAdapter = this.mAdapter;
        sliceAdapter.mAllowTwoLines = z;
        sliceAdapter.notifyHeaderChanged();
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setInsets(int i, int i2, int i3, int i4) {
        super.setInsets(i, i2, i3, i4);
        SliceAdapter sliceAdapter = this.mAdapter;
        sliceAdapter.mInsetStart = i;
        sliceAdapter.mInsetTop = i2;
        sliceAdapter.mInsetEnd = i3;
        sliceAdapter.mInsetBottom = i4;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setLastUpdated(long j) {
        this.mLastUpdated = j;
        SliceAdapter sliceAdapter = this.mAdapter;
        if (sliceAdapter.mLastUpdated != j) {
            sliceAdapter.mLastUpdated = j;
            sliceAdapter.notifyHeaderChanged();
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setLoadingActions(Set set) {
        SliceAdapter sliceAdapter = this.mAdapter;
        if (set == null) {
            sliceAdapter.mLoadingActions.clear();
        } else {
            sliceAdapter.mLoadingActions = set;
        }
        sliceAdapter.notifyDataSetChanged();
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setPolicy(SliceViewPolicy sliceViewPolicy) {
        this.mViewPolicy = sliceViewPolicy;
        this.mAdapter.mPolicy = sliceViewPolicy;
        sliceViewPolicy.mListener = this;
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setShowLastUpdated(boolean z) {
        this.mShowLastUpdated = z;
        SliceAdapter sliceAdapter = this.mAdapter;
        if (sliceAdapter.mShowLastUpdated != z) {
            sliceAdapter.mShowLastUpdated = z;
            sliceAdapter.notifyHeaderChanged();
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceActionListener(VolumePanelDialog$$ExternalSyntheticLambda4 volumePanelDialog$$ExternalSyntheticLambda4) {
        this.mObserver = volumePanelDialog$$ExternalSyntheticLambda4;
        SliceAdapter sliceAdapter = this.mAdapter;
        if (sliceAdapter != null) {
            sliceAdapter.mSliceObserver = volumePanelDialog$$ExternalSyntheticLambda4;
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceActions(List list) {
        SliceAdapter sliceAdapter = this.mAdapter;
        sliceAdapter.mSliceActions = list;
        sliceAdapter.notifyHeaderChanged();
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setSliceContent(ListContent listContent) {
        this.mListContent = listContent;
        updateDisplayedItems(listContent.getHeight(this.mSliceStyle, this.mViewPolicy));
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setStyle(SliceStyle sliceStyle, RowStyle rowStyle) {
        this.mSliceStyle = sliceStyle;
        this.mRowStyle = rowStyle;
        SliceAdapter sliceAdapter = this.mAdapter;
        sliceAdapter.mSliceStyle = sliceStyle;
        sliceAdapter.notifyDataSetChanged();
        if (rowStyle.mDisableRecyclerViewItemAnimator) {
            this.mRecyclerView.setItemAnimator(null);
        }
    }

    @Override // androidx.slice.widget.SliceChildView
    public final void setTint(int i) {
        this.mTintColor = i;
        updateDisplayedItems(getMeasuredHeight());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.util.List] */
    public final void updateDisplayedItems(int i) {
        DisplayedListItems displayedListItems;
        ListContent listContent = this.mListContent;
        if (listContent == null || !listContent.isValid()) {
            resetView();
            return;
        }
        ListContent listContent2 = this.mListContent;
        SliceStyle sliceStyle = this.mSliceStyle;
        SliceViewPolicy sliceViewPolicy = this.mViewPolicy;
        listContent2.getClass();
        int i2 = sliceViewPolicy.mMode;
        ArrayList arrayList = listContent2.mRowItems;
        if (i2 == 1) {
            displayedListItems = new DisplayedListItems(new ArrayList(Arrays.asList(listContent2.mHeaderContent)), arrayList.size() - 1);
        } else if (sliceViewPolicy.mScrollable || i <= 0) {
            sliceStyle.getClass();
            int size = arrayList.size();
            ArrayList arrayList2 = arrayList;
            if (size > 0) {
                boolean shouldSkipFirstListItem = sliceStyle.shouldSkipFirstListItem(arrayList);
                arrayList2 = arrayList;
                if (shouldSkipFirstListItem) {
                    arrayList2 = arrayList.subList(1, arrayList.size());
                }
            }
            displayedListItems = new DisplayedListItems(arrayList2, 0);
        } else {
            displayedListItems = sliceStyle.getListItemsForNonScrollingList(listContent2, i, sliceViewPolicy);
        }
        List list = displayedListItems.mDisplayedItems;
        this.mDisplayedItems = list;
        this.mDisplayedItemsHeight = this.mSliceStyle.getListItemsHeight(list, this.mViewPolicy);
        this.mAdapter.setSliceItems(this.mViewPolicy.mMode, this.mDisplayedItems);
        this.mRecyclerView.setOverScrollMode((this.mViewPolicy.mScrollable && (this.mDisplayedItemsHeight > getMeasuredHeight())) ? 1 : 2);
    }
}
