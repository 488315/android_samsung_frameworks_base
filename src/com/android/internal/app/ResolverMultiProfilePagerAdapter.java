package com.android.internal.app;

import android.content.Context;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;

/* loaded from: classes5.dex */
public class ResolverMultiProfilePagerAdapter extends AbstractMultiProfilePagerAdapter {
    private Context mContext;
    private final ResolverProfileDescriptor[] mItems;
    private boolean mUseLayoutWithDefault;

    ResolverMultiProfilePagerAdapter(Context context, ResolverListAdapter resolverListAdapter, AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, UserHandle userHandle, UserHandle userHandle2) {
        super(context, 0, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mContext = context;
        this.mItems = new ResolverProfileDescriptor[]{createProfileDescriptor(resolverListAdapter)};
    }

    ResolverMultiProfilePagerAdapter(Context context, ResolverListAdapter resolverListAdapter, ResolverListAdapter resolverListAdapter2, AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, int i, UserHandle userHandle, UserHandle userHandle2) {
        super(context, i, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mContext = context;
        this.mItems = new ResolverProfileDescriptor[]{createProfileDescriptor(resolverListAdapter), createProfileDescriptor(resolverListAdapter2)};
    }

    private ResolverProfileDescriptor createProfileDescriptor(ResolverListAdapter resolverListAdapter) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        Context context = this.mContext;
        return new ResolverProfileDescriptor(this, (ViewGroup) layoutInflaterFrom.inflate(((context instanceof ResolverActivity) && ((ResolverActivity) context).mIsAiAssist) ? R.layout.sem_resolver_ai_list : R.layout.sem_resolver_list_per_profile, (ViewGroup) null, false), resolverListAdapter);
    }

    AbsListView getListViewForIndex(int i) {
        return getItem(i).listView;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverProfileDescriptor getItem(int i) {
        return this.mItems[i];
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    int getItemCount() {
        return this.mItems.length;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    void setupListAdapter(int i) {
        getItem(i).listView.setAdapter((ListAdapter) getItem(i).resolverListAdapter);
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverListAdapter getAdapterForIndex(int i) {
        return this.mItems[i].resolverListAdapter;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter, com.android.internal.widget.PagerAdapter
    public ViewGroup instantiateItem(ViewGroup viewGroup, int i) {
        setupListAdapter(i);
        return super.instantiateItem(viewGroup, i);
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    ResolverListAdapter getListAdapterForUserHandle(UserHandle userHandle) {
        if (getPersonalListAdapter().getUserHandle().equals(userHandle) || userHandle.equals(getCloneUserHandle())) {
            return getPersonalListAdapter();
        }
        if (getWorkListAdapter() == null || !getWorkListAdapter().getUserHandle().equals(userHandle)) {
            return null;
        }
        return getWorkListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverListAdapter getActiveListAdapter() {
        return getAdapterForIndex(getCurrentPage());
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverListAdapter getInactiveListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1 - getCurrentPage());
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverListAdapter getPersonalListAdapter() {
        return getAdapterForIndex(0);
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverListAdapter getWorkListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public ResolverListAdapter getCurrentRootAdapter() {
        return getActiveListAdapter();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public AbsListView getActiveAdapterView() {
        return getListViewForIndex(getCurrentPage());
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    ViewGroup getInactiveAdapterView() {
        if (getCount() == 1) {
            return null;
        }
        return getListViewForIndex(1 - getCurrentPage());
    }

    void setUseLayoutWithDefault(boolean z) {
        this.mUseLayoutWithDefault = z;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    protected void setupContainerPadding(View view) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), this.mUseLayoutWithDefault ? view.getPaddingBottom() : 0);
    }

    class ResolverProfileDescriptor extends AbstractMultiProfilePagerAdapter.ProfileDescriptor {
        final AbsListView listView;
        private ResolverListAdapter resolverListAdapter;

        ResolverProfileDescriptor(ResolverMultiProfilePagerAdapter resolverMultiProfilePagerAdapter, ViewGroup viewGroup, ResolverListAdapter resolverListAdapter) {
            super(viewGroup);
            this.resolverListAdapter = resolverListAdapter;
            this.listView = (AbsListView) viewGroup.findViewById(R.id.resolver_list);
        }
    }
}
