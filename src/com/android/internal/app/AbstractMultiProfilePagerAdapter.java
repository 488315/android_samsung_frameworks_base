package com.android.internal.app;

import android.app.AppGlobals;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.os.Trace;
import android.os.UserHandle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.ResolverActivity;
import com.android.internal.widget.PagerAdapter;
import com.android.internal.widget.ViewPager;
import com.samsung.android.knox.SemPersonaManager;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public abstract class AbstractMultiProfilePagerAdapter extends PagerAdapter {
    static final int PROFILE_PERSONAL = 0;
    static final int PROFILE_WORK = 1;
    private static final String TAG = "AbstractMultiProfilePagerAdapter";
    private final UserHandle mCloneUserHandle;
    private final Context mContext;
    private int mCurrentPage;
    private final EmptyStateProvider mEmptyStateProvider;
    private Set<Integer> mLoadedPages = new HashSet();
    private boolean mNeedSortingInRebuildList;
    private OnProfileSelectedListener mOnProfileSelectedListener;
    private final QuietModeManager mQuietModeManager;
    private final UserHandle mWorkProfileUserHandle;

    public interface EmptyState {

        public interface ClickListener {
            void onClick(TabControl tabControl);
        }

        public interface TabControl {
            void showSpinner();
        }

        default ClickListener getButtonClickListener() {
            return null;
        }

        default String getSubtitle() {
            return null;
        }

        default String getTitle() {
            return null;
        }

        default void onEmptyStateShown() {
        }

        default boolean shouldSkipDataRebuild() {
            return false;
        }

        default boolean useDefaultEmptyView() {
            return false;
        }
    }

    public interface EmptyStateProvider {
        default EmptyState getEmptyState(ResolverListAdapter resolverListAdapter) {
            return null;
        }
    }

    public interface OnProfileSelectedListener {
        void onProfilePageStateChanged(int i);

        void onProfileSelected(int i);
    }

    interface OnSwitchOnWorkSelectedListener {
        void onSwitchOnWorkSelected();
    }

    @interface Profile {
    }

    public interface QuietModeManager {
        boolean isQuietModeEnabled(UserHandle userHandle);

        boolean isWaitingToEnableWorkProfile();

        void markWorkProfileEnabledBroadcastReceived();

        void requestQuietModeEnabled(boolean z, UserHandle userHandle);
    }

    abstract ViewGroup getActiveAdapterView();

    public abstract ResolverListAdapter getActiveListAdapter();

    public abstract Object getAdapterForIndex(int i);

    abstract Object getCurrentRootAdapter();

    abstract ViewGroup getInactiveAdapterView();

    public abstract ResolverListAdapter getInactiveListAdapter();

    public abstract ProfileDescriptor getItem(int i);

    abstract int getItemCount();

    abstract ResolverListAdapter getListAdapterForUserHandle(UserHandle userHandle);

    @Override // com.android.internal.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return null;
    }

    public abstract ResolverListAdapter getPersonalListAdapter();

    public abstract ResolverListAdapter getWorkListAdapter();

    @Override // com.android.internal.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    protected void setupContainerPadding(View view) {
    }

    abstract void setupListAdapter(int i);

    AbstractMultiProfilePagerAdapter(Context context, int i, EmptyStateProvider emptyStateProvider, QuietModeManager quietModeManager, UserHandle userHandle, UserHandle userHandle2) {
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mCurrentPage = i;
        this.mWorkProfileUserHandle = userHandle;
        this.mCloneUserHandle = userHandle2;
        this.mEmptyStateProvider = emptyStateProvider;
        this.mQuietModeManager = quietModeManager;
    }

    private boolean isQuietModeEnabled(UserHandle userHandle) {
        return this.mQuietModeManager.isQuietModeEnabled(userHandle);
    }

    void setOnProfileSelectedListener(OnProfileSelectedListener onProfileSelectedListener) {
        this.mOnProfileSelectedListener = onProfileSelectedListener;
    }

    Context getContext() {
        return this.mContext;
    }

    void setupViewPager(ViewPager viewPager) {
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter.1
            @Override // com.android.internal.widget.ViewPager.SimpleOnPageChangeListener, com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                AbstractMultiProfilePagerAdapter.this.semSetNeedSortingInRebuildList(true);
                AbstractMultiProfilePagerAdapter.this.mCurrentPage = i;
                if (!AbstractMultiProfilePagerAdapter.this.mLoadedPages.contains(Integer.valueOf(i))) {
                    AbstractMultiProfilePagerAdapter.this.rebuildActiveTab(true);
                    AbstractMultiProfilePagerAdapter.this.mLoadedPages.add(Integer.valueOf(i));
                }
                if (AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener != null) {
                    AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener.onProfileSelected(i);
                }
            }

            @Override // com.android.internal.widget.ViewPager.SimpleOnPageChangeListener, com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                if (AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener != null) {
                    AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener.onProfilePageStateChanged(i);
                }
            }
        });
        viewPager.setAdapter(this);
        viewPager.setCurrentItem(this.mCurrentPage);
        this.mLoadedPages.add(Integer.valueOf(this.mCurrentPage));
    }

    void clearInactiveProfileCache() {
        if (this.mLoadedPages.size() == 1) {
            return;
        }
        this.mLoadedPages.remove(Integer.valueOf(1 - this.mCurrentPage));
    }

    @Override // com.android.internal.widget.PagerAdapter
    public ViewGroup instantiateItem(ViewGroup viewGroup, int i) {
        ProfileDescriptor item = getItem(i);
        viewGroup.addView(item.rootView);
        return item.rootView;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // com.android.internal.widget.PagerAdapter
    public int getCount() {
        return getItemCount();
    }

    protected int getCurrentPage() {
        return this.mCurrentPage;
    }

    public UserHandle getCurrentUserHandle() {
        return getActiveListAdapter().mResolverListController.getUserHandle();
    }

    public UserHandle getCloneUserHandle() {
        return this.mCloneUserHandle;
    }

    boolean rebuildActiveTab(boolean z) {
        Trace.beginSection("MultiProfilePagerAdapter#rebuildActiveTab");
        boolean zRebuildTab = rebuildTab(getActiveListAdapter(), z);
        Trace.endSection();
        return zRebuildTab;
    }

    boolean rebuildInactiveTab(boolean z) {
        Trace.beginSection("MultiProfilePagerAdapter#rebuildInactiveTab");
        if (getItemCount() == 1) {
            Trace.endSection();
            return false;
        }
        boolean zRebuildTab = rebuildTab(getInactiveListAdapter(), z);
        Trace.endSection();
        return zRebuildTab;
    }

    private int userHandleToPageIndex(UserHandle userHandle) {
        return userHandle.equals(getPersonalListAdapter().mResolverListController.getUserHandle()) ? 0 : 1;
    }

    private boolean rebuildTab(ResolverListAdapter resolverListAdapter, boolean z) {
        if (shouldSkipRebuild(resolverListAdapter)) {
            resolverListAdapter.postListReadyRunnable(z, true);
            return false;
        }
        return resolverListAdapter.rebuildList(z);
    }

    private boolean shouldSkipRebuild(ResolverListAdapter resolverListAdapter) {
        EmptyState emptyState = this.mEmptyStateProvider.getEmptyState(resolverListAdapter);
        return emptyState != null && emptyState.shouldSkipDataRebuild();
    }

    void showEmptyResolverListEmptyState(final ResolverListAdapter resolverListAdapter) {
        final EmptyState emptyState = this.mEmptyStateProvider.getEmptyState(resolverListAdapter);
        if (emptyState == null) {
            return;
        }
        emptyState.onEmptyStateShown();
        showEmptyState(resolverListAdapter, emptyState, emptyState.getButtonClickListener() != null ? new View.OnClickListener() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showEmptyResolverListEmptyState$1(emptyState, resolverListAdapter, view);
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEmptyResolverListEmptyState$1(EmptyState emptyState, final ResolverListAdapter resolverListAdapter, View view) {
        emptyState.getButtonClickListener().onClick(new EmptyState.TabControl() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter$$ExternalSyntheticLambda1
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.TabControl
            public final void showSpinner() {
                this.f$0.lambda$showEmptyResolverListEmptyState$0(resolverListAdapter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEmptyResolverListEmptyState$0(ResolverListAdapter resolverListAdapter) {
        showSpinner(getItem(userHandleToPageIndex(resolverListAdapter.getUserHandle())).getEmptyStateView());
    }

    public static class MyUserIdProvider {
        public int getMyUserId() {
            return UserHandle.myUserId();
        }
    }

    public static class CrossProfileIntentsChecker {
        private final ContentResolver mContentResolver;

        public CrossProfileIntentsChecker(ContentResolver contentResolver) {
            this.mContentResolver = contentResolver;
        }

        public boolean hasCrossProfileIntents(List<Intent> list, final int i, final int i2) {
            final IPackageManager packageManager = AppGlobals.getPackageManager();
            return list.stream().anyMatch(new Predicate() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter$CrossProfileIntentsChecker$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.lambda$hasCrossProfileIntents$0(i, i2, packageManager, (Intent) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$hasCrossProfileIntents$0(int i, int i2, IPackageManager iPackageManager, Intent intent) {
            return IntentForwarderActivity.canForward(intent, i, i2, iPackageManager, this.mContentResolver) != null;
        }
    }

    protected void showEmptyState(ResolverListAdapter resolverListAdapter, EmptyState emptyState, View.OnClickListener onClickListener) {
        ProfileDescriptor item = getItem(userHandleToPageIndex(resolverListAdapter.getUserHandle()));
        item.rootView.findViewById(R.id.resolver_list).setVisibility(8);
        ViewGroup emptyStateView = item.getEmptyStateView();
        resetViewVisibilitiesForEmptyState(emptyStateView);
        emptyStateView.setVisibility(0);
        setupContainerPadding(emptyStateView.findViewById(R.id.resolver_empty_state_container));
        TextView textView = (TextView) emptyStateView.findViewById(R.id.resolver_empty_state_title);
        String title = emptyState.getTitle();
        if (title != null) {
            textView.setVisibility(0);
            textView.lambda$setTextAsync$0(title);
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) emptyStateView.findViewById(R.id.resolver_empty_state_subtitle);
        String subtitle = emptyState.getSubtitle();
        if (subtitle != null) {
            textView2.setVisibility(0);
            textView2.lambda$setTextAsync$0(subtitle);
        } else {
            textView2.setVisibility(8);
        }
        emptyStateView.findViewById(16908292).setVisibility(emptyState.useDefaultEmptyView() ? 0 : 8);
        Button button = (Button) emptyStateView.findViewById(R.id.resolver_empty_state_button);
        button.setVisibility(onClickListener != null ? 0 : 8);
        button.setOnClickListener(onClickListener);
        resolverListAdapter.markTabLoaded();
    }

    private void showSpinner(View view) {
        view.findViewById(R.id.resolver_empty_state_title).setVisibility(4);
        view.findViewById(R.id.resolver_empty_state_button).setVisibility(4);
        view.findViewById(R.id.resolver_empty_state_progress).setVisibility(0);
        view.findViewById(16908292).setVisibility(8);
    }

    private void resetViewVisibilitiesForEmptyState(View view) {
        view.findViewById(R.id.resolver_empty_state_title).setVisibility(0);
        view.findViewById(R.id.resolver_empty_state_subtitle).setVisibility(0);
        view.findViewById(R.id.resolver_empty_state_button).setVisibility(4);
        view.findViewById(R.id.resolver_empty_state_progress).setVisibility(8);
        view.findViewById(16908292).setVisibility(8);
    }

    protected void showListView(ResolverListAdapter resolverListAdapter) {
        ProfileDescriptor item = getItem(userHandleToPageIndex(resolverListAdapter.getUserHandle()));
        if (item.rootView.findViewById(R.id.resolver_list) != null) {
            item.rootView.findViewById(R.id.resolver_list).setVisibility(0);
        }
        if (item.rootView.findViewById(R.id.sem_resolver_second_depth_recycler_view) != null) {
            item.rootView.findViewById(R.id.sem_resolver_second_depth_recycler_view).setVisibility(0);
        }
        View viewFindViewById = item.rootView.findViewById(R.id.resolver_empty_state);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(8);
        }
    }

    private boolean hasAppsInOtherProfile(ResolverListAdapter resolverListAdapter) {
        if (this.mWorkProfileUserHandle == null) {
            return false;
        }
        Iterator<ResolverActivity.ResolvedComponentInfo> it = resolverListAdapter.getResolversForUser(UserHandle.of(UserHandle.myUserId())).iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfoAt = it.next().getResolveInfoAt(0);
            if (resolveInfoAt.targetUserId != -2 && !SemPersonaManager.isSecureFolderId(resolveInfoAt.targetUserId)) {
                return true;
            }
        }
        return false;
    }

    boolean shouldShowEmptyStateScreen(ResolverListAdapter resolverListAdapter) {
        if (resolverListAdapter.getUnfilteredCount() == 0 && resolverListAdapter.getPlaceholderCount() == 0) {
            return true;
        }
        return resolverListAdapter.getUserHandle().equals(this.mWorkProfileUserHandle) && isQuietModeEnabled(this.mWorkProfileUserHandle);
    }

    public static class ProfileDescriptor {
        private ViewGroup mEmptyStateView;
        public final ViewGroup rootView;

        ProfileDescriptor(ViewGroup viewGroup) {
            this.mEmptyStateView = null;
            this.rootView = viewGroup;
            this.mEmptyStateView = (ViewGroup) viewGroup.findViewById(R.id.resolver_empty_state);
        }

        protected ViewGroup getEmptyStateView() {
            return this.mEmptyStateView;
        }
    }

    public static class CompositeEmptyStateProvider implements EmptyStateProvider {
        private final EmptyStateProvider[] mProviders;

        public CompositeEmptyStateProvider(EmptyStateProvider... emptyStateProviderArr) {
            this.mProviders = emptyStateProviderArr;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
        public EmptyState getEmptyState(ResolverListAdapter resolverListAdapter) {
            for (EmptyStateProvider emptyStateProvider : this.mProviders) {
                EmptyState emptyState = emptyStateProvider.getEmptyState(resolverListAdapter);
                if (emptyState != null) {
                    return emptyState;
                }
            }
            return null;
        }
    }

    public void semSetNeedSortingInRebuildList(boolean z) {
        this.mNeedSortingInRebuildList = z;
    }

    public boolean semIsNeedSortingInRebuildList() {
        return this.mNeedSortingInRebuildList;
    }
}
