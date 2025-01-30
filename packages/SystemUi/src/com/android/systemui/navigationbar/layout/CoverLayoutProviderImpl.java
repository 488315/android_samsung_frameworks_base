package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        int i = this.navigationMode;
        int i2 = this.MODE_BOTTOM_GESTURE;
        Context context = this.context;
        return i == i2 ? context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_distance) : context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_distance);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonWidth(Point point, boolean z) {
        int i = this.navigationMode;
        int i2 = this.MODE_BOTTOM_GESTURE;
        Context context = this.context;
        return i == i2 ? context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_width) : context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_width);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getGesturalLayout(boolean z, boolean z2) {
        int i = ((NavBarStoreImpl) ((NavBarStore) Dependency.get(NavBarStore.class))).getNavStateManager(1).states.rotation;
        Context context = this.context;
        if (z) {
            this.navigationMode = this.MODE_BOTTOM_GESTURE;
            if (i == 0) {
                return context.getString(z2 ? R.string.config_navBarB5CoverGestureRevLayout : R.string.config_navBarB5CoverGestureLayout);
            }
            return context.getString(z2 ? R.string.config_navBarB5CoverGestureRevLayoutRotation180 : R.string.config_navBarB5CoverGestureLayoutRotation180);
        }
        this.navigationMode = this.MODE_BOTTOM_SIDE_GESTURE;
        if (i == 0) {
            return context.getString(z2 ? R.string.config_navBarB5CoverRevLayoutHandle : R.string.config_navBarB5CoverLayoutHandle);
        }
        return context.getString(R.string.config_navBarB5CoverLayoutHandleRotation180);
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
        Context context = this.context;
        return z2 ? context.getResources().getDimensionPixelSize(R.dimen.large_cover_gesture_space) : this.navigationMode == this.MODE_BOTTOM_GESTURE ? context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_space) : context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_space);
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
