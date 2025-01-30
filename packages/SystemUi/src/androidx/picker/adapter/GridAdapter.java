package androidx.picker.adapter;

import android.content.Context;
import androidx.picker.adapter.viewholder.GridCheckBoxViewHolder;
import androidx.picker.adapter.viewholder.GridRemoveViewHolder;
import androidx.picker.adapter.viewholder.GridViewHolder;
import androidx.picker.adapter.viewholder.GroupTitleViewHolder;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.CustomViewData;
import androidx.picker.model.viewdata.GroupTitleViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GridAdapter extends AbsAdapter {
    public GridAdapter(Context context) {
        super(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        ViewData viewData = (ViewData) ((ArrayList) this.mDataSetFiltered).get(i);
        if (viewData instanceof CustomViewData) {
            return 261;
        }
        if (viewData instanceof GroupTitleViewData) {
            return 260;
        }
        if (!(viewData instanceof AppInfoViewData)) {
            return 257;
        }
        AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
        if (appInfoViewData.getItemType() == 2) {
            return 258;
        }
        return appInfoViewData.getItemType() == 3 ? 259 : 257;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return i == 260 ? new GroupTitleViewHolder(AbsAdapter.inflate(recyclerView, R.layout.picker_app_text)) : i == 258 ? new GridCheckBoxViewHolder(AbsAdapter.inflate(recyclerView, R.layout.picker_app_grid_item_view)) : i == 259 ? new GridRemoveViewHolder(AbsAdapter.inflate(recyclerView, R.layout.picker_app_grid_item_view_remove)) : new GridViewHolder(AbsAdapter.inflate(recyclerView, R.layout.picker_app_grid_item_view));
    }
}
