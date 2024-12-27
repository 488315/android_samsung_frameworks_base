package com.android.internal.app;

import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;

import com.android.internal.R;

import java.util.List;
import java.util.function.Supplier;

public class NoAppsAvailableEmptyStateProvider
        implements AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final Context mContext;
    private final String mMetricsCategory;
    private final UserHandle mPersonalProfileUserHandle;
    private final UserHandle mTabOwnerUserHandleForLaunch;
    private final UserHandle mWorkProfileUserHandle;

    public NoAppsAvailableEmptyStateProvider(
            Context context,
            UserHandle workProfileUserHandle,
            UserHandle personalProfileUserHandle,
            String metricsCategory,
            UserHandle tabOwnerUserHandleForLaunch) {
        this.mContext = context;
        this.mWorkProfileUserHandle = workProfileUserHandle;
        this.mPersonalProfileUserHandle = personalProfileUserHandle;
        this.mMetricsCategory = metricsCategory;
        this.mTabOwnerUserHandleForLaunch = tabOwnerUserHandleForLaunch;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(
            ResolverListAdapter resolverListAdapter) {
        String title;
        UserHandle listUserHandle = resolverListAdapter.getUserHandle();
        if (this.mWorkProfileUserHandle != null
                && (this.mTabOwnerUserHandleForLaunch.equals(listUserHandle)
                        || !hasAppsInOtherProfile(resolverListAdapter))) {
            if (listUserHandle == this.mPersonalProfileUserHandle) {
                title =
                        ((DevicePolicyManager)
                                        this.mContext.getSystemService(DevicePolicyManager.class))
                                .getResources()
                                .getString(
                                        DevicePolicyResources.Strings.Core
                                                .RESOLVER_NO_PERSONAL_APPS,
                                        new Supplier() { // from class:
                                                         // com.android.internal.app.NoAppsAvailableEmptyStateProvider$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                String lambda$getEmptyState$0;
                                                lambda$getEmptyState$0 =
                                                        NoAppsAvailableEmptyStateProvider.this
                                                                .lambda$getEmptyState$0();
                                                return lambda$getEmptyState$0;
                                            }
                                        });
            } else {
                title =
                        ((DevicePolicyManager)
                                        this.mContext.getSystemService(DevicePolicyManager.class))
                                .getResources()
                                .getString(
                                        DevicePolicyResources.Strings.Core.RESOLVER_NO_WORK_APPS,
                                        new Supplier() { // from class:
                                                         // com.android.internal.app.NoAppsAvailableEmptyStateProvider$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                String lambda$getEmptyState$1;
                                                lambda$getEmptyState$1 =
                                                        NoAppsAvailableEmptyStateProvider.this
                                                                .lambda$getEmptyState$1();
                                                return lambda$getEmptyState$1;
                                            }
                                        });
            }
            return new NoAppsAvailableEmptyState(
                    title,
                    this.mMetricsCategory,
                    listUserHandle == this.mPersonalProfileUserHandle);
        }
        if (this.mWorkProfileUserHandle == null) {
            return new DefaultEmptyState();
        }
        return null;
    }

    public /* synthetic */ String lambda$getEmptyState$0() {
        return this.mContext.getString(R.string.resolver_no_personal_apps_available);
    }

    public /* synthetic */ String lambda$getEmptyState$1() {
        return this.mContext.getString(R.string.resolver_no_work_apps_available);
    }

    private boolean hasAppsInOtherProfile(ResolverListAdapter adapter) {
        if (this.mWorkProfileUserHandle == null) {
            return false;
        }
        List<ResolverActivity.ResolvedComponentInfo> resolversForIntent =
                adapter.getResolversForUser(this.mTabOwnerUserHandleForLaunch);
        for (ResolverActivity.ResolvedComponentInfo info : resolversForIntent) {
            ResolveInfo resolveInfo = info.getResolveInfoAt(0);
            if (resolveInfo.targetUserId != -2) {
                return true;
            }
        }
        return false;
    }

    public static class DefaultEmptyState implements AbstractMultiProfilePagerAdapter.EmptyState {
        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public boolean useDefaultEmptyView() {
            return true;
        }
    }

    public static class NoAppsAvailableEmptyState
            implements AbstractMultiProfilePagerAdapter.EmptyState {
        private boolean mIsPersonalProfile;
        private String mMetricsCategory;
        private String mTitle;

        public NoAppsAvailableEmptyState(
                String title, String metricsCategory, boolean isPersonalProfile) {
            this.mTitle = title;
            this.mMetricsCategory = metricsCategory;
            this.mIsPersonalProfile = isPersonalProfile;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public String getTitle() {
            return this.mTitle;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            DevicePolicyEventLogger.createEvent(160)
                    .setStrings(this.mMetricsCategory)
                    .setBoolean(this.mIsPersonalProfile)
                    .write();
        }
    }
}
