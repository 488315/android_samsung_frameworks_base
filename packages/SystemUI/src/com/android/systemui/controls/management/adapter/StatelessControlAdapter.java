package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.model.AllControlsModel;
import com.android.systemui.controls.management.model.SecControlStatusWrapper;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.SecStructureNameWrapper;
import com.android.systemui.controls.management.model.SecZoneNameWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SpanInfo;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.util.ControlsUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class StatelessControlAdapter extends RecyclerView.Adapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public AllControlsModel model;
    public final SpanManager spanManager;
    public final StatelessControlAdapter$spanSizeLookup$1 spanSizeLookup;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class SecMarginItemDecorator extends RecyclerView.ItemDecoration {
        public final int basicTextViewFocusedStrokeWidth;
        public final int itemBottomMargin;
        public final int structureStartMarginResize;
        public final int zoneSideMarginResize;

        public SecMarginItemDecorator(Context context) throws Resources.NotFoundException {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.control_list_horizontal_margin) - context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin);
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
            Integer numValueOf = adapter != null ? Integer.valueOf(adapter.getItemViewType(childAdapterPosition)) : null;
            boolean z = false;
            rect.top = 0;
            rect.left = 0;
            rect.right = 0;
            rect.bottom = 0;
            if ((numValueOf != null && numValueOf.intValue() == 1) || (numValueOf != null && numValueOf.intValue() == 103)) {
                z = true;
            }
            int i = this.itemBottomMargin;
            if (z) {
                rect.bottom = i;
                return;
            }
            int i2 = this.zoneSideMarginResize;
            if (numValueOf != null && numValueOf.intValue() == 101) {
                rect.left = this.structureStartMarginResize;
                rect.right = i2;
            } else if (numValueOf != null && numValueOf.intValue() == 0) {
                int i3 = i2 - this.basicTextViewFocusedStrokeWidth;
                rect.left = i3;
                rect.right = i3;
            }
            if (childAdapterPosition > 0) {
                RecyclerView.Adapter adapter2 = recyclerView.mAdapter;
                Integer numValueOf2 = adapter2 != null ? Integer.valueOf(adapter2.getItemViewType(childAdapterPosition - 1)) : null;
                if ((numValueOf2 != null && numValueOf2.intValue() == 1) || (numValueOf2 != null && numValueOf2.intValue() == 103)) {
                    rect.top = -i;
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0042  */
    /* JADX WARN: Type inference failed for: r11v4, types: [com.android.systemui.controls.management.adapter.StatelessControlAdapter$spanSizeLookup$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StatelessControlAdapter(Context context, LayoutUtil layoutUtil, ControlsUtil controlsUtil, int i) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        this.controlsUtil = controlsUtil;
        this.currentUserId = i;
        SpanManager spanManager = new SpanManager(layoutUtil);
        spanManager.spanInfos.put(0, new SpanInfo(0, 0, 3, null));
        Map map = spanManager.spanInfos;
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD;
        if (z) {
            controlsUtil.getClass();
            dimensionPixelSize = ControlsUtil.isFoldDelta(context) ? context.getResources().getDimensionPixelSize(R.dimen.control_base_item_size_fold) : context.getResources().getDimensionPixelSize(R.dimen.sec_control_base_item_size);
        }
        map.put(1, new SpanInfo(dimensionPixelSize, 0, 2, null));
        spanManager.spanInfos.put(101, new SpanInfo(0, 0, 3, null));
        spanManager.spanInfos.put(102, new SpanInfo(0, 0, 3, null));
        Map map2 = spanManager.spanInfos;
        if (z) {
            controlsUtil.getClass();
            dimensionPixelSize2 = ControlsUtil.isFoldDelta(context) ? context.getResources().getDimensionPixelSize(R.dimen.control_base_item_size_fold) : context.getResources().getDimensionPixelSize(R.dimen.sec_control_base_item_size);
        }
        map2.put(103, new SpanInfo(dimensionPixelSize2, 0, 2, null));
        this.spanManager = spanManager;
        this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$spanSizeLookup$1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i2) {
                StatelessControlAdapter statelessControlAdapter = this.this$0;
                SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) statelessControlAdapter.spanManager.spanInfos).get(Integer.valueOf(statelessControlAdapter.getItemViewType(i2)));
                if (spanInfo != null) {
                    return spanInfo.span;
                }
                return 0;
            }
        };
    }

    public static final View onCreateViewHolder$inflate(ViewGroup viewGroup, int i) {
        return KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, i, viewGroup, false);
    }

    public final void attachedToRecyclerView$1(RecyclerView recyclerView) {
        int measuredWidth = recyclerView.getMeasuredWidth();
        SpanManager spanManager = this.spanManager;
        spanManager.updateSpanInfos(measuredWidth);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), spanManager.maxSpan);
        gridLayoutManager.mSpanSizeLookup = this.spanSizeLookup;
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SecMarginItemDecorator(recyclerView.getContext()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        List list;
        AllControlsModel allControlsModel = this.model;
        if (allControlsModel == null || (list = allControlsModel.elements) == null) {
            return 0;
        }
        return ((ArrayList) list).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        AllControlsModel allControlsModel = this.model;
        if (allControlsModel == null) {
            throw new IllegalStateException("Getting item type for null model");
        }
        SecElementWrapper secElementWrapper = (SecElementWrapper) ((ArrayList) allControlsModel.elements).get(i);
        if (secElementWrapper instanceof SecZoneNameWrapper) {
            return 0;
        }
        if (secElementWrapper instanceof SecControlStatusWrapper) {
            return ((SecControlStatusWrapper) secElementWrapper).controlStatus.control.getCustomControl().getLayoutType() == 1 ? 103 : 1;
        }
        if (secElementWrapper instanceof SecStructureNameWrapper) {
            return 101;
        }
        if (secElementWrapper instanceof VerticalPaddingWrapper) {
            return 102;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        if (recyclerView.getMeasuredWidth() == 0) {
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter.onAttachedToRecyclerView.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    StatelessControlAdapter statelessControlAdapter = this;
                    RecyclerView recyclerView2 = recyclerView;
                    int i = StatelessControlAdapter.$r8$clinit;
                    statelessControlAdapter.attachedToRecyclerView$1(recyclerView2);
                }
            });
        } else {
            attachedToRecyclerView$1(recyclerView);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SecHolder secHolder = (SecHolder) viewHolder;
        AllControlsModel allControlsModel = this.model;
        if (allControlsModel != null) {
            secHolder.bindData((SecElementWrapper) ((ArrayList) allControlsModel.elements).get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new SecZoneHolder(onCreateViewHolder$inflate(viewGroup, R.layout.sec_controls_zone_header));
        }
        ControlsUtil controlsUtil = this.controlsUtil;
        int i2 = this.currentUserId;
        if (i == 1) {
            final int i3 = 0;
            return new SecControlHolder(onCreateViewHolder$inflate(viewGroup, R.layout.sec_controls_base_item), i2, controlsUtil, new Function2(this) { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$$ExternalSyntheticLambda0
                public final /* synthetic */ StatelessControlAdapter f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Object obj3;
                    Object obj4;
                    int i4 = i3;
                    String str = (String) obj;
                    boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                    switch (i4) {
                        case 0:
                            AllControlsModel allControlsModel = this.f$0.model;
                            if (allControlsModel != null) {
                                allControlsModel.changeFavoriteStatus(str, zBooleanValue);
                            }
                            break;
                        case 1:
                            AllControlsModel allControlsModel2 = this.f$0.model;
                            if (allControlsModel2 != null) {
                                allControlsModel2.changeFavoriteStatus(str, zBooleanValue);
                            }
                            break;
                        default:
                            AllControlsModel allControlsModel3 = this.f$0.model;
                            if (allControlsModel3 != null) {
                                ArrayList arrayList = (ArrayList) allControlsModel3.elements;
                                int size = arrayList.size();
                                int i5 = 0;
                                while (true) {
                                    if (i5 < size) {
                                        obj3 = arrayList.get(i5);
                                        i5++;
                                        SecElementWrapper secElementWrapper = (SecElementWrapper) obj3;
                                        if (!(secElementWrapper instanceof SecStructureNameWrapper) || !Intrinsics.areEqual(((SecStructureNameWrapper) secElementWrapper).structureName, str)) {
                                        }
                                    } else {
                                        obj3 = null;
                                    }
                                }
                                SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj3;
                                if (secStructureNameWrapper != null && zBooleanValue != secStructureNameWrapper.favorite) {
                                    for (ControlStatus controlStatus : allControlsModel3.controls) {
                                        ArrayList arrayList2 = (ArrayList) allControlsModel3.elements;
                                        int size2 = arrayList2.size();
                                        int i6 = 0;
                                        while (true) {
                                            if (i6 < size2) {
                                                obj4 = arrayList2.get(i6);
                                                i6++;
                                                SecElementWrapper secElementWrapper2 = (SecElementWrapper) obj4;
                                                if (!(secElementWrapper2 instanceof SecControlStatusWrapper) || !Intrinsics.areEqual(((SecControlStatusWrapper) secElementWrapper2).controlStatus.control.getControlId(), controlStatus.control.getControlId())) {
                                                }
                                            } else {
                                                obj4 = null;
                                            }
                                        }
                                        allControlsModel3.setControlFavoriteStatus((SecControlStatusWrapper) obj4, zBooleanValue);
                                    }
                                    secStructureNameWrapper.favorite = zBooleanValue;
                                    int iIndexOf = ((ArrayList) allControlsModel3.elements).indexOf(secStructureNameWrapper);
                                    StatelessControlAdapter statelessControlAdapter = allControlsModel3.adapter;
                                    if (statelessControlAdapter != null) {
                                        statelessControlAdapter.notifyItemChanged(iIndexOf, new Object());
                                    }
                                }
                            }
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        switch (i) {
            case 101:
                final int i4 = 2;
                return new SecStructureHolder(onCreateViewHolder$inflate(viewGroup, R.layout.controls_structure_header), new Function2(this) { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$$ExternalSyntheticLambda0
                    public final /* synthetic */ StatelessControlAdapter f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Object obj3;
                        Object obj4;
                        int i42 = i4;
                        String str = (String) obj;
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        switch (i42) {
                            case 0:
                                AllControlsModel allControlsModel = this.f$0.model;
                                if (allControlsModel != null) {
                                    allControlsModel.changeFavoriteStatus(str, zBooleanValue);
                                }
                                break;
                            case 1:
                                AllControlsModel allControlsModel2 = this.f$0.model;
                                if (allControlsModel2 != null) {
                                    allControlsModel2.changeFavoriteStatus(str, zBooleanValue);
                                }
                                break;
                            default:
                                AllControlsModel allControlsModel3 = this.f$0.model;
                                if (allControlsModel3 != null) {
                                    ArrayList arrayList = (ArrayList) allControlsModel3.elements;
                                    int size = arrayList.size();
                                    int i5 = 0;
                                    while (true) {
                                        if (i5 < size) {
                                            obj3 = arrayList.get(i5);
                                            i5++;
                                            SecElementWrapper secElementWrapper = (SecElementWrapper) obj3;
                                            if (!(secElementWrapper instanceof SecStructureNameWrapper) || !Intrinsics.areEqual(((SecStructureNameWrapper) secElementWrapper).structureName, str)) {
                                            }
                                        } else {
                                            obj3 = null;
                                        }
                                    }
                                    SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj3;
                                    if (secStructureNameWrapper != null && zBooleanValue != secStructureNameWrapper.favorite) {
                                        for (ControlStatus controlStatus : allControlsModel3.controls) {
                                            ArrayList arrayList2 = (ArrayList) allControlsModel3.elements;
                                            int size2 = arrayList2.size();
                                            int i6 = 0;
                                            while (true) {
                                                if (i6 < size2) {
                                                    obj4 = arrayList2.get(i6);
                                                    i6++;
                                                    SecElementWrapper secElementWrapper2 = (SecElementWrapper) obj4;
                                                    if (!(secElementWrapper2 instanceof SecControlStatusWrapper) || !Intrinsics.areEqual(((SecControlStatusWrapper) secElementWrapper2).controlStatus.control.getControlId(), controlStatus.control.getControlId())) {
                                                    }
                                                } else {
                                                    obj4 = null;
                                                }
                                            }
                                            allControlsModel3.setControlFavoriteStatus((SecControlStatusWrapper) obj4, zBooleanValue);
                                        }
                                        secStructureNameWrapper.favorite = zBooleanValue;
                                        int iIndexOf = ((ArrayList) allControlsModel3.elements).indexOf(secStructureNameWrapper);
                                        StatelessControlAdapter statelessControlAdapter = allControlsModel3.adapter;
                                        if (statelessControlAdapter != null) {
                                            statelessControlAdapter.notifyItemChanged(iIndexOf, new Object());
                                        }
                                    }
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                });
            case 102:
                return new SecPaddingHolder(onCreateViewHolder$inflate(viewGroup, R.layout.controls_empty_padding));
            case 103:
                final int i5 = 1;
                return new SecD2DControlHolder(onCreateViewHolder$inflate(viewGroup, R.layout.controls_d2d_base_item), i2, controlsUtil, new Function2(this) { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$$ExternalSyntheticLambda0
                    public final /* synthetic */ StatelessControlAdapter f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Object obj3;
                        Object obj4;
                        int i42 = i5;
                        String str = (String) obj;
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        switch (i42) {
                            case 0:
                                AllControlsModel allControlsModel = this.f$0.model;
                                if (allControlsModel != null) {
                                    allControlsModel.changeFavoriteStatus(str, zBooleanValue);
                                }
                                break;
                            case 1:
                                AllControlsModel allControlsModel2 = this.f$0.model;
                                if (allControlsModel2 != null) {
                                    allControlsModel2.changeFavoriteStatus(str, zBooleanValue);
                                }
                                break;
                            default:
                                AllControlsModel allControlsModel3 = this.f$0.model;
                                if (allControlsModel3 != null) {
                                    ArrayList arrayList = (ArrayList) allControlsModel3.elements;
                                    int size = arrayList.size();
                                    int i52 = 0;
                                    while (true) {
                                        if (i52 < size) {
                                            obj3 = arrayList.get(i52);
                                            i52++;
                                            SecElementWrapper secElementWrapper = (SecElementWrapper) obj3;
                                            if (!(secElementWrapper instanceof SecStructureNameWrapper) || !Intrinsics.areEqual(((SecStructureNameWrapper) secElementWrapper).structureName, str)) {
                                            }
                                        } else {
                                            obj3 = null;
                                        }
                                    }
                                    SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj3;
                                    if (secStructureNameWrapper != null && zBooleanValue != secStructureNameWrapper.favorite) {
                                        for (ControlStatus controlStatus : allControlsModel3.controls) {
                                            ArrayList arrayList2 = (ArrayList) allControlsModel3.elements;
                                            int size2 = arrayList2.size();
                                            int i6 = 0;
                                            while (true) {
                                                if (i6 < size2) {
                                                    obj4 = arrayList2.get(i6);
                                                    i6++;
                                                    SecElementWrapper secElementWrapper2 = (SecElementWrapper) obj4;
                                                    if (!(secElementWrapper2 instanceof SecControlStatusWrapper) || !Intrinsics.areEqual(((SecControlStatusWrapper) secElementWrapper2).controlStatus.control.getControlId(), controlStatus.control.getControlId())) {
                                                    }
                                                } else {
                                                    obj4 = null;
                                                }
                                            }
                                            allControlsModel3.setControlFavoriteStatus((SecControlStatusWrapper) obj4, zBooleanValue);
                                        }
                                        secStructureNameWrapper.favorite = zBooleanValue;
                                        int iIndexOf = ((ArrayList) allControlsModel3.elements).indexOf(secStructureNameWrapper);
                                        StatelessControlAdapter statelessControlAdapter = allControlsModel3.adapter;
                                        if (statelessControlAdapter != null) {
                                            statelessControlAdapter.notifyItemChanged(iIndexOf, new Object());
                                        }
                                    }
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                });
            default:
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong viewType: "));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        SecHolder secHolder = (SecHolder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder(secHolder, i);
            return;
        }
        AllControlsModel allControlsModel = this.model;
        if (allControlsModel != null) {
            Object obj = (SecElementWrapper) ((ArrayList) allControlsModel.elements).get(i);
            if (obj instanceof ControlInterface) {
                secHolder.updateFavorite(((ControlInterface) obj).getFavorite());
            } else if (obj instanceof SecStructureNameWrapper) {
                secHolder.updateFavorite(((SecStructureNameWrapper) obj).favorite);
            }
        }
    }
}
