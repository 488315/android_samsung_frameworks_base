package androidx.picker.adapter.viewholder;

import android.view.View;
import android.widget.TextView;
import androidx.picker.model.viewdata.GroupTitleViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GroupTitleViewHolder extends PickerViewHolder {
    public final TextView label;
    public final TextView title;

    public GroupTitleViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.label = (TextView) view.findViewById(R.id.label);
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public final void bindData(ViewData viewData) {
        if (viewData instanceof GroupTitleViewData) {
            GroupTitleViewData groupTitleViewData = (GroupTitleViewData) viewData;
            this.title.setText(groupTitleViewData.appData.group);
            String str = groupTitleViewData.label;
            int i = StringsKt__StringsJVMKt.isBlank(str) ? 8 : 0;
            TextView textView = this.label;
            textView.setVisibility(i);
            textView.setText(str);
        }
        super.bindData(viewData);
    }
}
