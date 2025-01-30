package androidx.picker.adapter.viewholder;

import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.CheckBox;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GridCheckBoxViewHolder extends GridViewHolder {
    public final CheckBox checkBox;
    public DisposableHandle disposableHandle;

    public GridCheckBoxViewHolder(View view) {
        super(view);
        View findViewById = view.findViewById(R.id.check_widget);
        Intrinsics.checkNotNull(findViewById);
        CheckBox checkBox = (CheckBox) findViewById;
        checkBox.setVisibility(0);
        this.checkBox = checkBox;
    }

    @Override // androidx.picker.adapter.viewholder.GridViewHolder, androidx.picker.adapter.viewholder.PickerViewHolder
    public final void bindData(ViewData viewData) {
        final SelectableItem selectableItem;
        super.bindData(viewData);
        boolean z = viewData instanceof AppInfoViewData;
        CheckBox checkBox = this.checkBox;
        if (z && (selectableItem = ((AppInfoViewData) viewData).selectableItem) != null) {
            DisposableHandle disposableHandle = this.disposableHandle;
            if (disposableHandle != null) {
                disposableHandle.dispose();
            }
            this.disposableHandle = selectableItem.bind(new Function1() { // from class: androidx.picker.adapter.viewholder.GridCheckBoxViewHolder$bindData$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    GridCheckBoxViewHolder.this.checkBox.setChecked(((Boolean) obj).booleanValue());
                    return Unit.INSTANCE;
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() { // from class: androidx.picker.adapter.viewholder.GridCheckBoxViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SelectableItem.this.setValue(Boolean.valueOf(this.checkBox.isChecked()));
                }
            });
        }
        Object systemService = this.itemView.getContext().getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return;
        }
        checkBox.setFocusable(false);
        checkBox.setClickable(false);
        this.item.setContentDescription(this.appName.getText());
    }

    @Override // androidx.picker.adapter.viewholder.GridViewHolder, androidx.picker.adapter.viewholder.PickerViewHolder
    public final void onViewRecycled() {
        super.onViewRecycled();
        this.checkBox.setOnClickListener(null);
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
    }
}
