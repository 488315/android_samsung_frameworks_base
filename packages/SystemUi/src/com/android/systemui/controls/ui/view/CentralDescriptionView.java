package com.android.systemui.controls.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.google.android.material.appbar.AppBarLayout;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CentralDescriptionView extends ConstraintLayout {
    public final CentralDescriptionView$mOnOffsetChangedListener$1 mOnOffsetChangedListener;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.ui.view.CentralDescriptionView$mOnOffsetChangedListener$1] */
    public CentralDescriptionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNull(context);
        this.mOnOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() { // from class: com.android.systemui.controls.ui.view.CentralDescriptionView$mOnOffsetChangedListener$1
            @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int height = (((int) (appBarLayout.getHeight() - appBarLayout.seslGetCollapsedHeight())) + i) / 2;
                CentralDescriptionView centralDescriptionView = CentralDescriptionView.this;
                if (centralDescriptionView.getChildCount() > 0) {
                    View childAt = centralDescriptionView.getChildAt(0);
                    int measuredHeight = (centralDescriptionView.getMeasuredHeight() - (childAt != null ? childAt.getMeasuredHeight() : 0)) / 2;
                    if (height > measuredHeight) {
                        height = measuredHeight;
                    }
                }
                centralDescriptionView.setTranslationY(-height);
            }
        };
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        View rootView = getRootView();
        AppBarLayout appBarLayout = rootView != null ? (AppBarLayout) rootView.findViewById(R.id.app_bar) : null;
        if (appBarLayout != null) {
            if (appBarLayout.getVisibility() == 0) {
                appBarLayout.addOnOffsetChangedListener(this.mOnOffsetChangedListener);
                return;
            }
            CentralDescriptionView$mOnOffsetChangedListener$1 centralDescriptionView$mOnOffsetChangedListener$1 = this.mOnOffsetChangedListener;
            List list = appBarLayout.listeners;
            if (list == null || centralDescriptionView$mOnOffsetChangedListener$1 == null) {
                return;
            }
            ((ArrayList) list).remove(centralDescriptionView$mOnOffsetChangedListener$1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.ui.view.CentralDescriptionView$mOnOffsetChangedListener$1] */
    public CentralDescriptionView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNull(context);
        this.mOnOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() { // from class: com.android.systemui.controls.ui.view.CentralDescriptionView$mOnOffsetChangedListener$1
            @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                int height = (((int) (appBarLayout.getHeight() - appBarLayout.seslGetCollapsedHeight())) + i2) / 2;
                CentralDescriptionView centralDescriptionView = CentralDescriptionView.this;
                if (centralDescriptionView.getChildCount() > 0) {
                    View childAt = centralDescriptionView.getChildAt(0);
                    int measuredHeight = (centralDescriptionView.getMeasuredHeight() - (childAt != null ? childAt.getMeasuredHeight() : 0)) / 2;
                    if (height > measuredHeight) {
                        height = measuredHeight;
                    }
                }
                centralDescriptionView.setTranslationY(-height);
            }
        };
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.ui.view.CentralDescriptionView$mOnOffsetChangedListener$1] */
    public CentralDescriptionView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNull(context);
        this.mOnOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() { // from class: com.android.systemui.controls.ui.view.CentralDescriptionView$mOnOffsetChangedListener$1
            @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i22) {
                int height = (((int) (appBarLayout.getHeight() - appBarLayout.seslGetCollapsedHeight())) + i22) / 2;
                CentralDescriptionView centralDescriptionView = CentralDescriptionView.this;
                if (centralDescriptionView.getChildCount() > 0) {
                    View childAt = centralDescriptionView.getChildAt(0);
                    int measuredHeight = (centralDescriptionView.getMeasuredHeight() - (childAt != null ? childAt.getMeasuredHeight() : 0)) / 2;
                    if (height > measuredHeight) {
                        height = measuredHeight;
                    }
                }
                centralDescriptionView.setTranslationY(-height);
            }
        };
    }
}
