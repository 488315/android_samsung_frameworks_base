package androidx.picker.adapter.layoutmanager;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.picker.common.log.LogTag;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AutoFitGridLayoutManager extends GridLayoutManager implements LogTag {
    public final int columnWidth;
    public boolean columnWidthChanged;
    public final int horizontalInterval;
    public final String logTag;
    public int prevWidth;

    public AutoFitGridLayoutManager(Context context) {
        super(context, 1);
        this.logTag = "AutoFitGridLayoutManager";
        this.columnWidth = context.getResources().getDimensionPixelOffset(R.dimen.picker_app_grid_item_view_item_width_land);
        this.horizontalInterval = context.getResources().getDimensionPixelOffset(R.dimen.picker_app_selected_layout_horizontal_interval);
        this.columnWidthChanged = true;
    }

    @Override // androidx.picker.common.log.LogTag
    public final String getLogTag() {
        return this.logTag;
    }

    @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i;
        int i2;
        int i3 = this.prevWidth;
        int i4 = this.mWidth;
        int i5 = this.columnWidth;
        if (i3 != i4 || (this.columnWidthChanged && i5 > 0)) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                i = ViewCompat.Api17Impl.getPaddingStart(recyclerView);
            } else {
                i = 0;
            }
            int i6 = i4 - i;
            RecyclerView recyclerView2 = this.mRecyclerView;
            if (recyclerView2 != null) {
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                i2 = ViewCompat.Api17Impl.getPaddingEnd(recyclerView2);
            } else {
                i2 = 0;
            }
            int i7 = i6 - i2;
            int i8 = this.horizontalInterval;
            int i9 = (i7 + i8) / (i5 + i8);
            if (1 >= i9) {
                i9 = 1;
            }
            StringBuilder sb = new StringBuilder("onLayoutChildren ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.mSpanCount, " -> ", i9, ", availableWidth=");
            sb.append(i7);
            LogTagHelperKt.debug(this, sb.toString());
            setSpanCount(i9);
            this.columnWidthChanged = false;
            this.prevWidth = this.mWidth;
        }
        super.onLayoutChildren(recycler, state);
    }
}
