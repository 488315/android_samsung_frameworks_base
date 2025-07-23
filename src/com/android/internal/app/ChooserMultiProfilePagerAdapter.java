package com.android.internal.app;

import android.content.Context;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.ChooserActivity;
import com.android.internal.widget.GridLayoutManager;
import com.android.internal.widget.RecyclerView;

/* loaded from: classes5.dex */
public class ChooserMultiProfilePagerAdapter extends AbstractMultiProfilePagerAdapter {
    private static final int SINGLE_CELL_SPAN_SIZE = 1;
    private int mBottomOffset;
    private final ChooserProfileDescriptor[] mItems;
    private int mMaxTargetsPerRow;

    ChooserMultiProfilePagerAdapter(Context context, ChooserActivity.ChooserGridAdapter chooserGridAdapter, AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, UserHandle userHandle, UserHandle userHandle2, int i) {
        super(context, 0, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mItems = new ChooserProfileDescriptor[]{createProfileDescriptor(chooserGridAdapter)};
        this.mMaxTargetsPerRow = i;
    }

    ChooserMultiProfilePagerAdapter(Context context, ChooserActivity.ChooserGridAdapter chooserGridAdapter, ChooserActivity.ChooserGridAdapter chooserGridAdapter2, AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, int i, UserHandle userHandle, UserHandle userHandle2, int i2) {
        super(context, i, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mItems = new ChooserProfileDescriptor[]{createProfileDescriptor(chooserGridAdapter), createProfileDescriptor(chooserGridAdapter2)};
        this.mMaxTargetsPerRow = i2;
    }

    private ChooserProfileDescriptor createProfileDescriptor(ChooserActivity.ChooserGridAdapter chooserGridAdapter) {
        ChooserProfileDescriptor chooserProfileDescriptor = new ChooserProfileDescriptor(this, (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.chooser_list_per_profile, (ViewGroup) null, false), chooserGridAdapter);
        chooserProfileDescriptor.recyclerView.setAccessibilityDelegateCompat(new ChooserRecyclerViewAccessibilityDelegate(chooserProfileDescriptor.recyclerView));
        return chooserProfileDescriptor;
    }

    public void setMaxTargetsPerRow(int i) {
        this.mMaxTargetsPerRow = i;
    }

    RecyclerView getListViewForIndex(int i) {
        return getItem(i).recyclerView;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserProfileDescriptor getItem(int i) {
        return this.mItems[i];
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    int getItemCount() {
        return this.mItems.length;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserActivity.ChooserGridAdapter getAdapterForIndex(int i) {
        return this.mItems[i].chooserGridAdapter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserListAdapter getListAdapterForUserHandle(UserHandle userHandle) {
        if (getPersonalListAdapter().getUserHandle().equals(userHandle) || userHandle.equals(getCloneUserHandle())) {
            return getPersonalListAdapter();
        }
        if (getWorkListAdapter() == null || !getWorkListAdapter().getUserHandle().equals(userHandle)) {
            return null;
        }
        return getWorkListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    void setupListAdapter(int i) {
        RecyclerView recyclerView = getItem(i).recyclerView;
        final ChooserActivity.ChooserGridAdapter chooserGridAdapter = getItem(i).chooserGridAdapter;
        final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        gridLayoutManager.setSpanCount(this.mMaxTargetsPerRow);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(this) { // from class: com.android.internal.app.ChooserMultiProfilePagerAdapter.1
            @Override // com.android.internal.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i2) {
                if (chooserGridAdapter.shouldCellSpan(i2)) {
                    return 1;
                }
                return gridLayoutManager.getSpanCount();
            }
        });
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserListAdapter getActiveListAdapter() {
        return getAdapterForIndex(getCurrentPage()).getListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserListAdapter getInactiveListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1 - getCurrentPage()).getListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserListAdapter getPersonalListAdapter() {
        return getAdapterForIndex(0).getListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserListAdapter getWorkListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1).getListAdapter();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ChooserActivity.ChooserGridAdapter getCurrentRootAdapter() {
        return getAdapterForIndex(getCurrentPage());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public RecyclerView getActiveAdapterView() {
        return getListViewForIndex(getCurrentPage());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public RecyclerView getInactiveAdapterView() {
        if (getCount() == 1) {
            return null;
        }
        return getListViewForIndex(1 - getCurrentPage());
    }

    void setEmptyStateBottomOffset(int i) {
        this.mBottomOffset = i;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    protected void setupContainerPadding(View view) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), getContext().getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_bottom) + this.mBottomOffset);
    }

    class ChooserProfileDescriptor extends AbstractMultiProfilePagerAdapter.ProfileDescriptor {
        private ChooserActivity.ChooserGridAdapter chooserGridAdapter;
        private RecyclerView recyclerView;

        ChooserProfileDescriptor(ChooserMultiProfilePagerAdapter chooserMultiProfilePagerAdapter, ViewGroup viewGroup, ChooserActivity.ChooserGridAdapter chooserGridAdapter) {
            super(viewGroup);
            this.chooserGridAdapter = chooserGridAdapter;
            this.recyclerView = (RecyclerView) viewGroup.findViewById(R.id.resolver_list);
        }
    }
}
