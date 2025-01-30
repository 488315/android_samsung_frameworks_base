package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.model.AllControlsModel;
import com.android.systemui.controls.management.model.CustomControlStatusWrapper;
import com.android.systemui.controls.management.model.CustomControlsModel;
import com.android.systemui.controls.management.model.CustomElementWrapper;
import com.android.systemui.controls.management.model.CustomStructureNameWrapper;
import com.android.systemui.controls.management.model.CustomZoneNameWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SpanInfo;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlAdapter extends RecyclerView.Adapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public CustomControlsModel model;
    public final SpanManager spanManager;
    public final CustomControlAdapter$spanSizeLookup$1 spanSizeLookup;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomMarginItemDecorator extends RecyclerView.ItemDecoration {
        public final int basicTextViewFocusedStrokeWidth;
        public final int itemBottomMargin;
        public final int structureStartMarginResize;
        public final int zoneSideMarginResize;

        public CustomMarginItemDecorator(Context context) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.controls_list_margin_horizontal) - context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin);
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.basic_interaction_side_margin);
            this.basicTextViewFocusedStrokeWidth = context.getResources().getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.basic_interaction_checkbox_side_margin);
            this.zoneSideMarginResize = dimensionPixelSize2 - dimensionPixelSize;
            this.structureStartMarginResize = dimensionPixelSize3 - dimensionPixelSize;
            this.itemBottomMargin = context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin) * 2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            recyclerView.getClass();
            int childAdapterPosition = RecyclerView.getChildAdapterPosition(view);
            if (childAdapterPosition == -1) {
                return;
            }
            RecyclerView.Adapter adapter = recyclerView.mAdapter;
            Integer valueOf = adapter != null ? Integer.valueOf(adapter.getItemViewType(childAdapterPosition)) : null;
            boolean z = false;
            rect.top = 0;
            rect.left = 0;
            rect.right = 0;
            rect.bottom = 0;
            boolean z2 = (valueOf != null && valueOf.intValue() == 1) || (BasicRune.CONTROLS_LAYOUT_TYPE && valueOf != null && valueOf.intValue() == 103);
            int i = this.itemBottomMargin;
            if (z2) {
                rect.bottom = i;
                return;
            }
            int i2 = this.zoneSideMarginResize;
            if (valueOf != null && valueOf.intValue() == 101) {
                rect.left = this.structureStartMarginResize;
                rect.right = i2;
            } else if (valueOf != null && valueOf.intValue() == 0) {
                int i3 = i2 - this.basicTextViewFocusedStrokeWidth;
                rect.left = i3;
                rect.right = i3;
            }
            if (childAdapterPosition > 0) {
                RecyclerView.Adapter adapter2 = recyclerView.mAdapter;
                Integer valueOf2 = adapter2 != null ? Integer.valueOf(adapter2.getItemViewType(childAdapterPosition - 1)) : null;
                if ((valueOf2 != null && valueOf2.intValue() == 1) || (BasicRune.CONTROLS_LAYOUT_TYPE && valueOf2 != null && valueOf2.intValue() == 103)) {
                    z = true;
                }
                if (z) {
                    rect.top = -i;
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0077  */
    /* JADX WARN: Type inference failed for: r10v4, types: [com.android.systemui.controls.management.adapter.CustomControlAdapter$spanSizeLookup$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CustomControlAdapter(Context context, LayoutUtil layoutUtil, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, int i) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.currentUserId = i;
        SpanManager spanManager = new SpanManager(layoutUtil);
        Map map = spanManager.spanInfos;
        map.put(0, new SpanInfo(0, 0, 3, null));
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD;
        if (z) {
            controlsUtil.getClass();
            if (ControlsUtil.isFoldDelta(context)) {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size_fold);
                map.put(1, new SpanInfo(dimensionPixelSize, 0, 2, null));
                map.put(101, new SpanInfo(0, 0, 3, null));
                map.put(102, new SpanInfo(0, 0, 3, null));
                if (z) {
                    controlsUtil.getClass();
                    if (ControlsUtil.isFoldDelta(context)) {
                        dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size_fold);
                        map.put(103, new SpanInfo(dimensionPixelSize2, 0, 2, null));
                        this.spanManager = spanManager;
                        this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$spanSizeLookup$1
                            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                            public final int getSpanSize(int i2) {
                                CustomControlAdapter customControlAdapter = CustomControlAdapter.this;
                                SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) customControlAdapter.spanManager.spanInfos).get(Integer.valueOf(customControlAdapter.getItemViewType(i2)));
                                if (spanInfo != null) {
                                    return spanInfo.span;
                                }
                                return 0;
                            }
                        };
                    }
                }
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size);
                map.put(103, new SpanInfo(dimensionPixelSize2, 0, 2, null));
                this.spanManager = spanManager;
                this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$spanSizeLookup$1
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public final int getSpanSize(int i2) {
                        CustomControlAdapter customControlAdapter = CustomControlAdapter.this;
                        SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) customControlAdapter.spanManager.spanInfos).get(Integer.valueOf(customControlAdapter.getItemViewType(i2)));
                        if (spanInfo != null) {
                            return spanInfo.span;
                        }
                        return 0;
                    }
                };
            }
        }
        dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size);
        map.put(1, new SpanInfo(dimensionPixelSize, 0, 2, null));
        map.put(101, new SpanInfo(0, 0, 3, null));
        map.put(102, new SpanInfo(0, 0, 3, null));
        if (z) {
        }
        dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size);
        map.put(103, new SpanInfo(dimensionPixelSize2, 0, 2, null));
        this.spanManager = spanManager;
        this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$spanSizeLookup$1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i2) {
                CustomControlAdapter customControlAdapter = CustomControlAdapter.this;
                SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) customControlAdapter.spanManager.spanInfos).get(Integer.valueOf(customControlAdapter.getItemViewType(i2)));
                if (spanInfo != null) {
                    return spanInfo.span;
                }
                return 0;
            }
        };
    }

    public static final View onCreateViewHolder$inflate(RecyclerView recyclerView, int i) {
        return LayoutInflater.from(recyclerView.getContext()).inflate(i, (ViewGroup) recyclerView, false);
    }

    public final void attachedToRecyclerView(RecyclerView recyclerView) {
        int measuredWidth = recyclerView.getMeasuredWidth();
        SpanManager spanManager = this.spanManager;
        spanManager.updateSpanInfos(measuredWidth);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), spanManager.maxSpan);
        gridLayoutManager.mSpanSizeLookup = this.spanSizeLookup;
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new CustomMarginItemDecorator(recyclerView.getContext()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list;
        CustomControlsModel customControlsModel = this.model;
        if (customControlsModel == null || (list = ((AllControlsModel) customControlsModel).elements) == null) {
            return 0;
        }
        return ((ArrayList) list).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        CustomControlsModel customControlsModel = this.model;
        if (customControlsModel == null) {
            throw new IllegalStateException("Getting item type for null model");
        }
        CustomElementWrapper customElementWrapper = (CustomElementWrapper) ((ArrayList) ((AllControlsModel) customControlsModel).elements).get(i);
        if (customElementWrapper instanceof CustomZoneNameWrapper) {
            return 0;
        }
        if (customElementWrapper instanceof CustomControlStatusWrapper) {
            return (BasicRune.CONTROLS_LAYOUT_TYPE && ((CustomControlStatusWrapper) customElementWrapper).controlStatus.control.getCustomControl().getLayoutType() == 1) ? 103 : 1;
        }
        if (customElementWrapper instanceof CustomStructureNameWrapper) {
            return 101;
        }
        if (customElementWrapper instanceof VerticalPaddingWrapper) {
            return 102;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        if (recyclerView.getMeasuredWidth() == 0) {
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$onAttachedToRecyclerView$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    RecyclerView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    CustomControlAdapter customControlAdapter = this;
                    RecyclerView recyclerView2 = RecyclerView.this;
                    int i = CustomControlAdapter.$r8$clinit;
                    customControlAdapter.attachedToRecyclerView(recyclerView2);
                }
            });
        } else {
            attachedToRecyclerView(recyclerView);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CustomHolder customHolder = (CustomHolder) viewHolder;
        CustomControlsModel customControlsModel = this.model;
        if (customControlsModel != null) {
            customHolder.bindData((CustomElementWrapper) ((ArrayList) ((AllControlsModel) customControlsModel).elements).get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        if (i == 0) {
            return new ZoneCustomHolder(onCreateViewHolder$inflate(recyclerView, R.layout.controls_custom_zone_header));
        }
        if (i == 1) {
            return new ControlCustomHolder(onCreateViewHolder$inflate(recyclerView, R.layout.controls_custom_base_item), this.currentUserId, this.controlsUtil, this.controlsRuneWrapper, new Function2() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$onCreateViewHolder$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    String str = (String) obj;
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    CustomControlsModel customControlsModel = CustomControlAdapter.this.model;
                    if (customControlsModel != null) {
                        ((AllControlsModel) customControlsModel).changeFavoriteStatus(str, booleanValue);
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        switch (i) {
            case 101:
                return new StructureCustomHolder(onCreateViewHolder$inflate(recyclerView, R.layout.controls_structure_category_header), new Function2() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$onCreateViewHolder$3
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Object obj3;
                        Object obj4;
                        String str = (String) obj;
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        CustomControlsModel customControlsModel = CustomControlAdapter.this.model;
                        if (customControlsModel != null) {
                            AllControlsModel allControlsModel = (AllControlsModel) customControlsModel;
                            ArrayList arrayList = (ArrayList) allControlsModel.elements;
                            Iterator it = arrayList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    obj3 = null;
                                    break;
                                }
                                obj3 = it.next();
                                CustomElementWrapper customElementWrapper = (CustomElementWrapper) obj3;
                                if ((customElementWrapper instanceof CustomStructureNameWrapper) && Intrinsics.areEqual(((CustomStructureNameWrapper) customElementWrapper).structureName, str)) {
                                    break;
                                }
                            }
                            CustomStructureNameWrapper customStructureNameWrapper = (CustomStructureNameWrapper) obj3;
                            if (customStructureNameWrapper != null && booleanValue != customStructureNameWrapper.favorite) {
                                for (ControlStatus controlStatus : allControlsModel.controls) {
                                    Iterator it2 = arrayList.iterator();
                                    while (true) {
                                        if (!it2.hasNext()) {
                                            obj4 = null;
                                            break;
                                        }
                                        obj4 = it2.next();
                                        CustomElementWrapper customElementWrapper2 = (CustomElementWrapper) obj4;
                                        if ((customElementWrapper2 instanceof CustomControlStatusWrapper) && Intrinsics.areEqual(((CustomControlStatusWrapper) customElementWrapper2).controlStatus.control.getControlId(), controlStatus.getControlId())) {
                                            break;
                                        }
                                    }
                                    allControlsModel.setControlFavoriteStatus((CustomControlStatusWrapper) obj4, booleanValue);
                                }
                                customStructureNameWrapper.favorite = booleanValue;
                                int indexOf = arrayList.indexOf(customStructureNameWrapper);
                                RecyclerView.Adapter adapter = allControlsModel.adapter;
                                if (adapter != null) {
                                    adapter.notifyItemChanged(indexOf, new Object());
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
            case 102:
                return new PaddingCustomHolder(onCreateViewHolder$inflate(recyclerView, R.layout.controls_empty_padding));
            case 103:
                return new SmallControlCustomHolder(onCreateViewHolder$inflate(recyclerView, R.layout.controls_small_layout_item), this.currentUserId, this.controlsUtil, this.controlsRuneWrapper, new Function2() { // from class: com.android.systemui.controls.management.adapter.CustomControlAdapter$onCreateViewHolder$2
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        String str = (String) obj;
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        CustomControlsModel customControlsModel = CustomControlAdapter.this.model;
                        if (customControlsModel != null) {
                            ((AllControlsModel) customControlsModel).changeFavoriteStatus(str, booleanValue);
                        }
                        return Unit.INSTANCE;
                    }
                });
            default:
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Wrong viewType: ", i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        CustomHolder customHolder = (CustomHolder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder(customHolder, i);
            return;
        }
        CustomControlsModel customControlsModel = this.model;
        if (customControlsModel != null) {
            Object obj = (CustomElementWrapper) ((ArrayList) ((AllControlsModel) customControlsModel).elements).get(i);
            if (obj instanceof ControlInterface) {
                customHolder.updateFavorite(((ControlInterface) obj).getFavorite());
            } else if (obj instanceof CustomStructureNameWrapper) {
                customHolder.updateFavorite(((CustomStructureNameWrapper) obj).favorite);
            }
        }
    }
}
