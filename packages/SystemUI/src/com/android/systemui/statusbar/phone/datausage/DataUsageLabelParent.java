package com.android.systemui.statusbar.phone.datausage;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import com.android.systemui.R;
import com.android.systemui.shade.NotificationPanelView;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class DataUsageLabelParent {
    public final DoubleSupplier mExpansionHeightSupplier;
    public final BooleanSupplier mFullyExpandedSupplier;
    public final IntSupplier mMaxPanelHeightSupplier;
    public final IntSupplier mMinExpansionHeightSupplier;
    public final BooleanSupplier mOnKeyguardStateSupplier;
    public final Supplier mPanelViewSupplier;
    public ViewGroup mParent;

    public DataUsageLabelParent(Supplier<NotificationPanelView> supplier, BooleanSupplier booleanSupplier, DoubleSupplier doubleSupplier, IntSupplier intSupplier, BooleanSupplier booleanSupplier2, IntSupplier intSupplier2) {
        this.mPanelViewSupplier = supplier;
        this.mOnKeyguardStateSupplier = booleanSupplier;
        this.mExpansionHeightSupplier = doubleSupplier;
        this.mMinExpansionHeightSupplier = intSupplier;
        this.mFullyExpandedSupplier = booleanSupplier2;
        this.mMaxPanelHeightSupplier = intSupplier2;
    }

    public final ViewGroup getParentViewGroup() {
        NotificationPanelView notificationPanelView = (NotificationPanelView) this.mPanelViewSupplier.get();
        if (this.mParent == null && notificationPanelView != null) {
            View viewInflate = ((ViewStub) notificationPanelView.findViewById(R.id.sec_panel_data_usage_container_stub)).inflate();
            if (viewInflate instanceof ViewGroup) {
                this.mParent = (ViewGroup) viewInflate;
            } else {
                Log.e("DataUsageLabelParent", "ERROR: it shows that two or more objects using the same view name may exist at the same time: " + viewInflate);
            }
        }
        return this.mParent;
    }
}
