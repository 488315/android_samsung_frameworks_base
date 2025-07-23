package com.android.internal.app;

import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Context;
import android.os.UserHandle;
import com.android.internal.R;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.ResolverActivity;
import java.util.Iterator;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class NoAppsAvailableEmptyStateProvider implements AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final Context mContext;
    private final String mMetricsCategory;
    private final UserHandle mPersonalProfileUserHandle;
    private final UserHandle mTabOwnerUserHandleForLaunch;
    private final UserHandle mWorkProfileUserHandle;

    public static class DefaultEmptyState implements AbstractMultiProfilePagerAdapter.EmptyState {
        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public boolean useDefaultEmptyView() {
            return true;
        }
    }

    public NoAppsAvailableEmptyStateProvider(Context context, UserHandle userHandle, UserHandle userHandle2, String str, UserHandle userHandle3) {
        this.mContext = context;
        this.mWorkProfileUserHandle = userHandle;
        this.mPersonalProfileUserHandle = userHandle2;
        this.mMetricsCategory = str;
        this.mTabOwnerUserHandleForLaunch = userHandle3;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(ResolverListAdapter resolverListAdapter) {
        String string;
        UserHandle userHandle = resolverListAdapter.getUserHandle();
        if (this.mWorkProfileUserHandle != null && (this.mTabOwnerUserHandleForLaunch.equals(userHandle) || !hasAppsInOtherProfile(resolverListAdapter))) {
            if (userHandle == this.mPersonalProfileUserHandle) {
                string = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_NO_PERSONAL_APPS, new Supplier() { // from class: com.android.internal.app.NoAppsAvailableEmptyStateProvider$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        String lambda$getEmptyState$0;
                        lambda$getEmptyState$0 = NoAppsAvailableEmptyStateProvider.this.lambda$getEmptyState$0();
                        return lambda$getEmptyState$0;
                    }
                });
            } else {
                string = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.RESOLVER_NO_WORK_APPS, new Supplier() { // from class: com.android.internal.app.NoAppsAvailableEmptyStateProvider$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        String lambda$getEmptyState$1;
                        lambda$getEmptyState$1 = NoAppsAvailableEmptyStateProvider.this.lambda$getEmptyState$1();
                        return lambda$getEmptyState$1;
                    }
                });
            }
            return new NoAppsAvailableEmptyState(string, this.mMetricsCategory, userHandle == this.mPersonalProfileUserHandle);
        }
        if (this.mWorkProfileUserHandle == null) {
            return new DefaultEmptyState();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getEmptyState$0() {
        return this.mContext.getString(R.string.resolver_no_personal_apps_available);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getEmptyState$1() {
        return this.mContext.getString(R.string.resolver_no_work_apps_available);
    }

    private boolean hasAppsInOtherProfile(ResolverListAdapter resolverListAdapter) {
        if (this.mWorkProfileUserHandle == null) {
            return false;
        }
        Iterator<ResolverActivity.ResolvedComponentInfo> it = resolverListAdapter.getResolversForUser(this.mTabOwnerUserHandleForLaunch).iterator();
        while (it.hasNext()) {
            if (it.next().getResolveInfoAt(0).targetUserId != -2) {
                return true;
            }
        }
        return false;
    }

    public static class NoAppsAvailableEmptyState implements AbstractMultiProfilePagerAdapter.EmptyState {
        private boolean mIsPersonalProfile;
        private String mMetricsCategory;
        private String mTitle;

        public NoAppsAvailableEmptyState(String str, String str2, boolean z) {
            this.mTitle = str;
            this.mMetricsCategory = str2;
            this.mIsPersonalProfile = z;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public String getTitle() {
            return this.mTitle;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            DevicePolicyEventLogger.createEvent(160).setStrings(this.mMetricsCategory).setBoolean(this.mIsPersonalProfile).write();
        }
    }
}
