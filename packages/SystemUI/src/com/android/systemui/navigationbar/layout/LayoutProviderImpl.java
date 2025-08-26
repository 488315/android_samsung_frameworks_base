package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LayoutProviderImpl implements LayoutProvider {
    public final Context mContext;

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

    public LayoutProviderImpl(Context context) {
        this.mContext = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonDistanceSize(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.11d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getButtonWidth(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.2222d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getGesturalLayout(boolean z, boolean z2) {
        return z ? z2 ? this.mContext.getString(R.string.config_secNavBarGestureRevLayoutHandle) : this.mContext.getString(R.string.config_secNavBarGestureLayoutHandle) : z2 ? this.mContext.getString(R.string.config_secNavBarRevLayoutHandle) : this.mContext.getString(R.string.config_secNavBarLayoutHandle);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getGestureWidth(Point point, boolean z) {
        return (int) (Math.min(point.x, point.y) * 0.35d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z) {
        if (!z) {
            return this.mContext.getString(R.string.config_secNavBarLayout);
        }
        String string = this.mContext.getString(R.string.config_secNavBarRevLayout);
        string.getClass();
        return string;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z) {
        return getSpaceSidePadding(point, z, false);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceWidth(Point point, boolean z, boolean z2) {
        double dMin;
        double d;
        if (z2) {
            dMin = Math.min(point.x, point.y);
            d = 0.14d;
        } else {
            dMin = Math.min(point.x, point.y);
            d = 0.11d;
        }
        return (int) (dMin * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getVerticalLayoutID(boolean z) {
        return z ? R.layout.samsung_navigation_layout_vertical : R.layout.samsung_navigation_layout;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z, boolean z2) {
        double dMin;
        double d;
        if (z2) {
            dMin = Math.min(point.x, point.y);
            d = 0.077d;
        } else {
            dMin = Math.min(point.x, point.y);
            d = 0.0d;
        }
        return (int) (dMin * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z, int i) {
        return getLayout(z);
    }
}
