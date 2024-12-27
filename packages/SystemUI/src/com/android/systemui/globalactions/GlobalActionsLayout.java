package com.android.systemui.globalactions;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.MultiListLayout;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class GlobalActionsLayout extends MultiListLayout {
    public boolean mBackgroundsSet;

    public GlobalActionsLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void addToListView(View view, boolean z) {
        if (z) {
            getListView().addView(view, 0);
        } else {
            getListView().addView(view);
        }
    }

    public HardwareBgDrawable getBackgroundDrawable(int i) {
        HardwareBgDrawable hardwareBgDrawable = new HardwareBgDrawable(true, true, getContext());
        hardwareBgDrawable.setTint(i);
        return hardwareBgDrawable;
    }

    public int getCurrentLayoutDirection() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
    }

    public int getCurrentRotation() {
        return RotationUtils.getRotation(((LinearLayout) this).mContext);
    }

    @Override // com.android.systemui.MultiListLayout
    public ViewGroup getListView() {
        return (ViewGroup) findViewById(R.id.list);
    }

    @Override // com.android.systemui.MultiListLayout
    public final ViewGroup getSeparatedView() {
        return (ViewGroup) findViewById(com.android.systemui.R.id.separated_button);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        HardwareBgDrawable backgroundDrawable;
        super.onMeasure(i, i2);
        if (getListView() == null || this.mBackgroundsSet) {
            return;
        }
        ViewGroup listView = getListView();
        HardwareBgDrawable backgroundDrawable2 = getBackgroundDrawable(getResources().getColor(com.android.systemui.R.color.global_actions_grid_background, null));
        if (backgroundDrawable2 != null) {
            listView.setBackground(backgroundDrawable2);
        }
        if (getSeparatedView() != null && (backgroundDrawable = getBackgroundDrawable(getResources().getColor(com.android.systemui.R.color.global_actions_separated_background, null))) != null) {
            getSeparatedView().setBackground(backgroundDrawable);
        }
        this.mBackgroundsSet = true;
    }

    @Override // com.android.systemui.MultiListLayout
    public void onUpdateList() {
        super.onUpdateList();
        ViewGroup separatedView = getSeparatedView();
        ViewGroup listView = getListView();
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            boolean shouldBeSeparated = this.mAdapter.shouldBeSeparated(i);
            View view = shouldBeSeparated ? this.mAdapter.getView(i, null, separatedView) : this.mAdapter.getView(i, null, listView);
            if (shouldBeSeparated) {
                ViewGroup separatedView2 = getSeparatedView();
                if (separatedView2 != null) {
                    separatedView2.addView(view);
                } else {
                    addToListView(view, false);
                }
            } else {
                addToListView(view, shouldReverseListItems());
            }
        }
    }

    public abstract boolean shouldReverseListItems();
}
