package com.android.internal.app;

import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Context;
import android.os.UserHandle;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class WorkProfilePausedEmptyStateProvider implements AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final Context mContext;
    private final String mMetricsCategory;
    private final AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener mOnSwitchOnWorkSelectedListener;
    private final AbstractMultiProfilePagerAdapter.QuietModeManager mQuietModeManager;
    private final UserHandle mWorkProfileUserHandle;

    public WorkProfilePausedEmptyStateProvider(Context context, UserHandle userHandle, AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener onSwitchOnWorkSelectedListener, String str) {
        this.mContext = context;
        this.mWorkProfileUserHandle = userHandle;
        this.mQuietModeManager = quietModeManager;
        this.mMetricsCategory = str;
        this.mOnSwitchOnWorkSelectedListener = onSwitchOnWorkSelectedListener;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(ResolverListAdapter resolverListAdapter) {
        if (resolverListAdapter.getUserHandle().equals(this.mWorkProfileUserHandle) && this.mQuietModeManager.isQuietModeEnabled(this.mWorkProfileUserHandle) && resolverListAdapter.getCount() != 0) {
            return new WorkProfileOffEmptyState(((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_WORK_PAUSED_TITLE, new Supplier() { // from class: com.android.internal.app.WorkProfilePausedEmptyStateProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getEmptyState$0();
                }
            }), new AbstractMultiProfilePagerAdapter.EmptyState.ClickListener() { // from class: com.android.internal.app.WorkProfilePausedEmptyStateProvider$$ExternalSyntheticLambda1
                @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener
                public final void onClick(AbstractMultiProfilePagerAdapter.EmptyState.TabControl tabControl) {
                    this.f$0.lambda$getEmptyState$1(tabControl);
                }
            }, this.mMetricsCategory);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getEmptyState$0() {
        return this.mContext.getString(R.string.resolver_turn_on_work_apps);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getEmptyState$1(AbstractMultiProfilePagerAdapter.EmptyState.TabControl tabControl) {
        tabControl.showSpinner();
        AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener onSwitchOnWorkSelectedListener = this.mOnSwitchOnWorkSelectedListener;
        if (onSwitchOnWorkSelectedListener != null) {
            onSwitchOnWorkSelectedListener.onSwitchOnWorkSelected();
        }
        this.mQuietModeManager.requestQuietModeEnabled(false, this.mWorkProfileUserHandle);
    }

    public static class WorkProfileOffEmptyState implements AbstractMultiProfilePagerAdapter.EmptyState {
        private final String mMetricsCategory;
        private final AbstractMultiProfilePagerAdapter.EmptyState.ClickListener mOnClick;
        private final String mTitle;

        public WorkProfileOffEmptyState(String str, AbstractMultiProfilePagerAdapter.EmptyState.ClickListener clickListener, String str2) {
            this.mTitle = str;
            this.mOnClick = clickListener;
            this.mMetricsCategory = str2;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public String getTitle() {
            return this.mTitle;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public AbstractMultiProfilePagerAdapter.EmptyState.ClickListener getButtonClickListener() {
            return this.mOnClick;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            DevicePolicyEventLogger.createEvent(157).setStrings(this.mMetricsCategory).write();
        }
    }
}
