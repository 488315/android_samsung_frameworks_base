package androidx.picker.decorator;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.picker.adapter.HeaderFooterAdapter;
import androidx.picker.adapter.viewholder.AppListItemViewHolder;
import androidx.picker.features.composable.ComposableType;
import androidx.picker.features.composable.ComposableTypeSet;
import androidx.picker.helper.ContextHelperKt;
import androidx.picker.model.viewdata.CategoryViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ListSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public final Context context;
    public final int spacing;

    public ListSpacingItemDecoration(Context context) {
        this.context = context;
        this.spacing = context.getResources().getDimensionPixelOffset(R.dimen.picker_app_list_category_margin_left);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        HeaderFooterAdapter headerFooterAdapter = adapter instanceof HeaderFooterAdapter ? (HeaderFooterAdapter) adapter : null;
        if (headerFooterAdapter == null) {
            return;
        }
        RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
        if (childViewHolder instanceof AppListItemViewHolder) {
            ComposableType composableType = ((AppListItemViewHolder) childViewHolder).composableType;
            ComposableType.Companion companion = ComposableType.Companion;
            ComposableTypeSet composableTypeSet = ComposableTypeSet.CheckBox_Expander;
            companion.getClass();
            boolean isSame = ComposableType.Companion.isSame(composableType, composableTypeSet);
            boolean z = true;
            if (isSame || ComposableType.Companion.isSame(composableType, ComposableTypeSet.AllSwitch)) {
                return;
            }
            ArrayList arrayList = (ArrayList) headerFooterAdapter.wrappedAdapter.mDataSetFiltered;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((ViewData) it.next()) instanceof CategoryViewData) {
                        break;
                    }
                }
            }
            z = false;
            if (!z) {
                rect.set(0, 0, 0, 0);
                return;
            }
            boolean isRTL = ContextHelperKt.isRTL(this.context);
            int i = this.spacing;
            if (isRTL) {
                rect.set(0, 0, i, 0);
            } else {
                rect.set(i, 0, 0, 0);
            }
        }
    }
}
