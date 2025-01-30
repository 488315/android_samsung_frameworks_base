package androidx.picker.adapter;

import android.content.Context;
import androidx.picker.adapter.viewholder.AppListItemViewHolder;
import androidx.picker.adapter.viewholder.GroupTitleViewHolder;
import androidx.picker.features.composable.ComposableBitConverter;
import androidx.picker.features.composable.ComposableFactory;
import androidx.picker.features.composable.ComposableFrame;
import androidx.picker.features.composable.ComposableStrategy;
import androidx.picker.features.composable.ComposableType;
import androidx.picker.model.viewdata.GroupTitleViewData;
import androidx.picker.model.viewdata.ViewData;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.ranges.IntRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ListAdapter extends AbsAdapter {
    public final int TYPE_GROUP_HEADER;
    public final ComposableFactory mComposableFactory;
    public final IntRange mComposableViewTypeRange;

    public ListAdapter(Context context, ComposableStrategy composableStrategy) {
        super(context);
        ComposableFactory composableFactory = new ComposableFactory(composableStrategy);
        this.mComposableFactory = composableFactory;
        IntRange intRange = composableFactory.viewTypeRange;
        this.mComposableViewTypeRange = intRange;
        this.TYPE_GROUP_HEADER = intRange.last + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        Integer num;
        int i2;
        ViewData viewData = (ViewData) ((ArrayList) this.mDataSetFiltered).get(i);
        ComposableFactory composableFactory = this.mComposableFactory;
        ComposableType selectComposableType = composableFactory.composableStrategy.selectComposableType(viewData);
        if (selectComposableType != null) {
            ComposableBitConverter composableBitConverter = composableFactory.converter;
            Map map = composableBitConverter.cachedMapByComposableType;
            Integer num2 = (Integer) ((LinkedHashMap) map).get(selectComposableType);
            if (num2 != null) {
                i2 = num2.intValue();
            } else {
                int i3 = 0;
                for (Pair pair : CollectionsKt__CollectionsKt.listOf(new Pair(selectComposableType.getLeftFrame(), 0), new Pair(selectComposableType.getIconFrame(), 1), new Pair(selectComposableType.getTitleFrame(), 2), new Pair(selectComposableType.getWidgetFrame(), 3))) {
                    ComposableFrame composableFrame = (ComposableFrame) pair.component1();
                    int intValue = ((Number) pair.component2()).intValue();
                    if (composableFrame != null) {
                        int indexOf = composableBitConverter.frameInfo[intValue].indexOf(composableFrame) + 1;
                        i3 |= indexOf == 0 ? 0 : indexOf << composableBitConverter.rangeList[intValue].first;
                    }
                }
                map.put(selectComposableType, Integer.valueOf(i3));
                i2 = i3;
            }
            num = Integer.valueOf(i2);
        } else {
            num = null;
        }
        if (num != null) {
            return num.intValue();
        }
        if (viewData instanceof GroupTitleViewData) {
            return this.TYPE_GROUP_HEADER;
        }
        throw new RuntimeException("Not Implemented");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        IntRange intRange = this.mComposableViewTypeRange;
        if (intRange.first <= i && i <= intRange.last) {
            return new AppListItemViewHolder(this.mComposableFactory.inflateComposableView(recyclerView, i), this.mComposableFactory.getComposableType(i));
        }
        if (i == this.TYPE_GROUP_HEADER) {
            return new GroupTitleViewHolder(AbsAdapter.inflate(recyclerView, R.layout.picker_app_text));
        }
        throw new RuntimeException("Not Implemented");
    }
}
