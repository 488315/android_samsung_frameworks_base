package com.android.internal.app;

import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserHandle;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class NoCrossProfileEmptyStateProvider implements AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker mCrossProfileIntentsChecker;
    private final AbstractMultiProfilePagerAdapter.EmptyState mNoPersonalToWorkEmptyState;
    private final AbstractMultiProfilePagerAdapter.EmptyState mNoWorkToPersonalEmptyState;
    private final UserHandle mPersonalProfileUserHandle;
    private final UserHandle mTabOwnerUserHandleForLaunch;

    public NoCrossProfileEmptyStateProvider(UserHandle userHandle, AbstractMultiProfilePagerAdapter.EmptyState emptyState, AbstractMultiProfilePagerAdapter.EmptyState emptyState2, AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker crossProfileIntentsChecker, UserHandle userHandle2) {
        this.mPersonalProfileUserHandle = userHandle;
        this.mNoWorkToPersonalEmptyState = emptyState;
        this.mNoPersonalToWorkEmptyState = emptyState2;
        this.mCrossProfileIntentsChecker = crossProfileIntentsChecker;
        this.mTabOwnerUserHandleForLaunch = userHandle2;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(ResolverListAdapter resolverListAdapter) {
        if (this.mTabOwnerUserHandleForLaunch.equals(resolverListAdapter.getUserHandle()) || this.mCrossProfileIntentsChecker.hasCrossProfileIntents(resolverListAdapter.getIntents(), this.mTabOwnerUserHandleForLaunch.getIdentifier(), resolverListAdapter.getUserHandle().getIdentifier())) {
            return null;
        }
        if (resolverListAdapter.getUserHandle().equals(this.mPersonalProfileUserHandle)) {
            return this.mNoWorkToPersonalEmptyState;
        }
        return this.mNoPersonalToWorkEmptyState;
    }

    public static class DevicePolicyBlockerEmptyState implements AbstractMultiProfilePagerAdapter.EmptyState {
        private final Context mContext;
        private final int mDefaultSubtitleResource;
        private final int mDefaultTitleResource;
        private final String mDevicePolicyStringSubtitleId;
        private final String mDevicePolicyStringTitleId;
        private final String mEventCategory;
        private final int mEventId;

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public boolean shouldSkipDataRebuild() {
            return true;
        }

        public DevicePolicyBlockerEmptyState(Context context, String str, int i, String str2, int i2, int i3, String str3) {
            this.mContext = context;
            this.mDevicePolicyStringTitleId = str;
            this.mDefaultTitleResource = i;
            this.mDevicePolicyStringSubtitleId = str2;
            this.mDefaultSubtitleResource = i2;
            this.mEventId = i3;
            this.mEventCategory = str3;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public String getTitle() {
            return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(this.mDevicePolicyStringTitleId, new Supplier() { // from class: com.android.internal.app.NoCrossProfileEmptyStateProvider$DevicePolicyBlockerEmptyState$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getTitle$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getTitle$0() {
            return this.mContext.getString(this.mDefaultTitleResource);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public String getSubtitle() {
            return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString(this.mDevicePolicyStringSubtitleId, new Supplier() { // from class: com.android.internal.app.NoCrossProfileEmptyStateProvider$DevicePolicyBlockerEmptyState$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getSubtitle$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getSubtitle$1() {
            return this.mContext.getString(this.mDefaultSubtitleResource);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            DevicePolicyEventLogger.createEvent(this.mEventId).setStrings(this.mEventCategory).write();
        }
    }
}
