package androidx.picker.adapter.viewholder;

import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GridRemoveViewHolder extends GridViewHolder {
    public final ImageView removeIcon;

    public GridRemoveViewHolder(View view) {
        super(view);
        View findViewById = view.findViewById(R.id.remove_icon);
        Intrinsics.checkNotNull(findViewById);
        this.removeIcon = (ImageView) findViewById;
    }

    @Override // androidx.picker.adapter.viewholder.GridViewHolder, androidx.picker.adapter.viewholder.PickerViewHolder
    public final void bindData(ViewData viewData) {
        super.bindData(viewData);
        if (viewData instanceof AppInfoViewData) {
            this.removeIcon.setVisibility(((AppInfoViewData) viewData).getDimmed() ? 8 : 0);
        }
        Object systemService = this.itemView.getContext().getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        View view = this.item;
        view.setContentDescription(String.format(view.getContext().getResources().getText(R.string.accs_remove).toString(), Arrays.copyOf(new Object[]{this.appName.getText()}, 1)));
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public final void setViewEnableState(boolean z) {
        this.item.setEnabled(z);
    }
}
