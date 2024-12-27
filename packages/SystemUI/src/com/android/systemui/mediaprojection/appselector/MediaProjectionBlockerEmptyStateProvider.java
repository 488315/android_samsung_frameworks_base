package com.android.systemui.mediaprojection.appselector;

import android.content.Context;
import android.os.UserHandle;
import com.android.internal.app.AbstractMultiProfilePagerAdapter;
import com.android.internal.app.ResolverListAdapter;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import kotlin.jvm.internal.Intrinsics;

public final class MediaProjectionBlockerEmptyStateProvider implements AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    public final Context context;
    public final UserHandle hostAppHandle;
    public final UserHandle personalProfileHandle;
    public final ScreenCaptureDevicePolicyResolver policyResolver;

    public MediaProjectionBlockerEmptyStateProvider(UserHandle userHandle, UserHandle userHandle2, ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver, Context context) {
        this.hostAppHandle = userHandle;
        this.personalProfileHandle = userHandle2;
        this.policyResolver = screenCaptureDevicePolicyResolver;
        this.context = context;
    }

    public final AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(ResolverListAdapter resolverListAdapter) {
        boolean isScreenCaptureAllowed = this.policyResolver.isScreenCaptureAllowed(resolverListAdapter.getUserHandle(), this.hostAppHandle);
        final int i = Intrinsics.areEqual(this.hostAppHandle, this.personalProfileHandle) ? 17042567 : 17042568;
        if (isScreenCaptureAllowed) {
            return null;
        }
        return new AbstractMultiProfilePagerAdapter.EmptyState() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionBlockerEmptyStateProvider$getEmptyState$1
            public final String getSubtitle() {
                return MediaProjectionBlockerEmptyStateProvider.this.context.getResources().getString(i);
            }

            public final String getTitle() {
                return MediaProjectionBlockerEmptyStateProvider.this.context.getResources().getString(R.string.screen_capturing_disabled_by_policy_dialog_title);
            }

            public final boolean shouldSkipDataRebuild() {
                return true;
            }

            public final void onEmptyStateShown() {
            }
        };
    }
}
