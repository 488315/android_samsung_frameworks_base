package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TabletLayoutProviderImpl implements LayoutProvider {
    public final Context mContext;
    public int mCurrentAlign = 1;
    public int mCurrentNavigationMode;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TabletLayoutProviderImpl(Context context) {
        this.mContext = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonDistanceSize(Point point, boolean z) {
        double dMin;
        double d;
        int iMax = z ? Math.max(point.x, point.y) : Math.min(point.x, point.y);
        if (this.mCurrentNavigationMode == 0) {
            dMin = iMax;
            d = 0.013d;
        } else {
            dMin = Math.min(point.x, point.y);
            d = 0.095d;
        }
        return (int) (dMin * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonWidth(Point point, boolean z) {
        int iMax = z ? Math.max(point.x, point.y) : Math.min(point.x, point.y);
        if (this.mCurrentNavigationMode == 0) {
            return (int) (iMax * (this.mCurrentAlign == 1 ? 0.207d : 0.12d));
        }
        return (int) (Math.min(point.x, point.y) * 0.13d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getGesturalLayout(boolean z, boolean z2) {
        if (z) {
            this.mCurrentNavigationMode = 2;
            String string = this.mContext.getString(z2 ? R.string.config_secNavBarGestureTabletRevLayoutHandle : R.string.config_secNavBarGestureTabletLayoutHandle);
            string.getClass();
            return string;
        }
        this.mCurrentNavigationMode = 1;
        String string2 = this.mContext.getString(z2 ? R.string.config_secNavBarRevLayoutHandle : R.string.config_secNavBarLayoutHandle);
        string2.getClass();
        return string2;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getGestureWidth(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.3d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z) {
        if (!z) {
            return this.mContext.getString(R.string.config_navBarTabletLayout);
        }
        String string = this.mContext.getString(R.string.config_navBarRevTabletLayout);
        string.getClass();
        return string;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z) {
        int iMax = z ? Math.max(point.x, point.y) : Math.min(point.x, point.y);
        if (this.mCurrentNavigationMode == 0) {
            return (int) (iMax * (this.mCurrentAlign != 1 ? 0.0d : 0.03325d));
        }
        return (int) (Math.min(point.x, point.y) * 0.03325d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceWidth(Point point, boolean z, boolean z2) {
        int iMax = z ? Math.max(point.x, point.y) : Math.min(point.x, point.y);
        if (this.mCurrentNavigationMode == 0) {
            return (int) (iMax * (this.mCurrentAlign != 1 ? 0.075d : 0.11d));
        }
        return (int) (iMax * 0.11d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getVerticalLayoutID(boolean z) {
        return z ? R.layout.samsung_navigation_layout_tablet : R.layout.samsung_navigation_layout;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z, int i) {
        this.mCurrentAlign = i;
        this.mCurrentNavigationMode = 0;
        if (i == 0) {
            return this.mContext.getString(z ? R.string.config_navBarRevFoldLayoutAlignLeft : R.string.config_navBarFoldLayoutAlignLeft);
        }
        if (i != 2) {
            return getLayout(z);
        }
        return this.mContext.getString(z ? R.string.config_navBarRevFoldLayoutAlignRight : R.string.config_navBarFoldLayoutAlignRight);
    }
}
