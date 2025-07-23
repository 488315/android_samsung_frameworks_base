package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoverLayoutProviderImpl implements LayoutProvider {
    public final Context context;
    public int navigationMode = 0;

    public CoverLayoutProviderImpl(Context context) {
        this.context = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonDistanceSize(Point point, boolean z) {
        return this.navigationMode == 2 ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_distance) : this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_distance);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonWidth(Point point, boolean z) {
        return this.navigationMode == 2 ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_width) : this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_width);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getGesturalLayout(boolean z, boolean z2) {
        int i = ((NavBarStateManagerImpl) ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(1)).states.rotation;
        if (z) {
            this.navigationMode = 2;
            if (i == 0) {
                String string = this.context.getString(z2 ? R.string.config_navBarB5CoverGestureRevLayout : R.string.config_navBarB5CoverGestureLayout);
                string.getClass();
                return string;
            }
            String string2 = this.context.getString(z2 ? R.string.config_navBarB5CoverGestureRevLayoutRotation180 : R.string.config_navBarB5CoverGestureLayoutRotation180);
            string2.getClass();
            return string2;
        }
        this.navigationMode = 1;
        if (i == 0) {
            String string3 = this.context.getString(z2 ? R.string.config_navBarB5CoverRevLayoutHandle : R.string.config_navBarB5CoverLayoutHandle);
            string3.getClass();
            return string3;
        }
        String string4 = this.context.getString(R.string.config_navBarB5CoverLayoutHandleRotation180);
        string4.getClass();
        return string4;
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
        return z2 ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_gesture_space) : this.navigationMode == 2 ? this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_bottom_gesture_space) : this.context.getResources().getDimensionPixelSize(R.dimen.large_cover_button_space);
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
