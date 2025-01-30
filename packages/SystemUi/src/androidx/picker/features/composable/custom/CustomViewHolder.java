package androidx.picker.features.composable.custom;

import android.view.View;
import androidx.picker.features.composable.ComposableViewHolder;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CustomViewHolder extends ComposableViewHolder {
    public CustomViewHolder(View view) {
        super(view);
    }

    public abstract void bindData(AppInfoData appInfoData);

    @Override // androidx.picker.features.composable.ComposableViewHolder
    public void bindData(ViewData viewData) {
        if (viewData instanceof AppInfoViewData) {
            bindData(((AppInfoViewData) viewData).appInfoData);
        }
    }
}
