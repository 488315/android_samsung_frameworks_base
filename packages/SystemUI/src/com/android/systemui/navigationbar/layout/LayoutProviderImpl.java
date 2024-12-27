package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LayoutProviderImpl implements LayoutProvider {
    public final Context mContext;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        String string;
        if (z) {
            string = z2 ? this.mContext.getString(R.string.config_secNavBarGestureRevLayoutHandle) : this.mContext.getString(R.string.config_secNavBarGestureLayoutHandle);
            Intrinsics.checkNotNull(string);
        } else {
            string = z2 ? this.mContext.getString(R.string.config_secNavBarRevLayoutHandle) : this.mContext.getString(R.string.config_secNavBarLayoutHandle);
            Intrinsics.checkNotNull(string);
        }
        return string;
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
        Intrinsics.checkNotNull(string);
        return string;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z) {
        return getSpaceSidePadding(point, z, false);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceWidth(Point point, boolean z, boolean z2) {
        double min;
        double d;
        if (z2) {
            min = Math.min(point.x, point.y);
            d = 0.14d;
        } else {
            min = Math.min(point.x, point.y);
            d = 0.11d;
        }
        return (int) (min * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getVerticalLayoutID(boolean z) {
        return z ? R.layout.samsung_navigation_layout_vertical : R.layout.samsung_navigation_layout;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final int getSpaceSidePadding(Point point, boolean z, boolean z2) {
        double min;
        double d;
        if (z2) {
            min = Math.min(point.x, point.y);
            d = 0.077d;
        } else {
            min = Math.min(point.x, point.y);
            d = 0.0d;
        }
        return (int) (min * d);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProvider
    public final String getLayout(boolean z, int i) {
        return getLayout(z);
    }
}
