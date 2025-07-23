package com.android.systemui.statusbar.phone.datausage;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            View inflate = ((ViewStub) notificationPanelView.findViewById(R.id.sec_panel_data_usage_container_stub)).inflate();
            if (inflate instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) inflate;
                this.mParent = viewGroup;
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
                if (getViewContext() != null) {
                    if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                        layoutParams.topMargin = getViewContext().getResources().getDimensionPixelSize(R.dimen.data_usage_label_view_top_margin_tablet);
                    } else {
                        layoutParams.topMargin = getViewContext().getResources().getDimensionPixelSize(R.dimen.data_usage_label_view_top_margin);
                    }
                    this.mParent.setLayoutParams(layoutParams);
                }
            } else {
                Log.e("DataUsageLabelParent", "ERROR: it shows that two or more objects using the same view name may exist at the same time: " + inflate);
            }
        }
        return this.mParent;
    }

    public final Context getViewContext() {
        NotificationPanelView notificationPanelView = (NotificationPanelView) this.mPanelViewSupplier.get();
        if (notificationPanelView != null) {
            return notificationPanelView.getContext();
        }
        return null;
    }
}
