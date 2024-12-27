package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverLayoutProviderImpl implements LayoutProvider {
    public final Context context;
    public final int MODE_BOTTOM_SIDE_GESTURE = 1;
    public final int MODE_BOTTOM_GESTURE = 2;
    public int navigationMode = 0;

    public CoverLayoutProviderImpl(Context context) {
        this.context = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonDistanceSize(Point point, boolean z) {
        return this.navigationMode == this.MODE_BOTTOM_GESTURE ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_distance) : this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_distance);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonWidth(Point point, boolean z) {
        return this.navigationMode == this.MODE_BOTTOM_GESTURE ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_width) : this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_width);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getGesturalLayout(boolean z, boolean z2) {
        String string;
        int i = ((NavBarStateManagerImpl) ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(1)).states.rotation;
        if (z) {
            this.navigationMode = this.MODE_BOTTOM_GESTURE;
            if (i == 0) {
                string = this.context.getString(z2 ? R.string.config_navBarB5CoverGestureRevLayout : R.string.config_navBarB5CoverGestureLayout);
            } else {
                string = this.context.getString(z2 ? R.string.config_navBarB5CoverGestureRevLayoutRotation180 : R.string.config_navBarB5CoverGestureLayoutRotation180);
            }
            Intrinsics.checkNotNull(string);
        } else {
            this.navigationMode = this.MODE_BOTTOM_SIDE_GESTURE;
            if (i == 0) {
                string = this.context.getString(z2 ? R.string.config_navBarB5CoverRevLayoutHandle : R.string.config_navBarB5CoverLayoutHandle);
            } else {
                string = this.context.getString(R.string.config_navBarB5CoverLayoutHandleRotation180);
            }
            Intrinsics.checkNotNull(string);
        }
        return string;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getGestureWidth(Point point, boolean z) {
        return this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_gesture_width);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z, int i) {
        return getLayout(z);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z) {
        return 0;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceWidth(Point point, boolean z, boolean z2) {
        return z2 ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_gesture_space) : this.navigationMode == this.MODE_BOTTOM_GESTURE ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_space) : this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_space);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getVerticalLayoutID(boolean z) {
        return z ? R.layout.samsung_navigation_layout_vertical : R.layout.samsung_navigation_layout;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z) {
        this.navigationMode = 0;
        return this.context.getString(z ? R.string.config_navBarB5CoverRevLayout : R.string.config_navBarB5CoverLayout);
    }
}
