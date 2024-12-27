package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.model.AllControlsModel;
import com.android.systemui.controls.management.model.SecControlStatusWrapper;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.SecStructureNameWrapper;
import com.android.systemui.controls.management.model.SecZoneNameWrapper;
import com.android.systemui.controls.management.model.VerticalPaddingWrapper;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.util.ControlsUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class StatelessControlAdapter extends RecyclerView.Adapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public AllControlsModel model;
    public final SpanManager spanManager;
    public final StatelessControlAdapter$spanSizeLookup$1 spanSizeLookup;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class SecMarginItemDecorator extends RecyclerView.ItemDecoration {
        public final int basicTextViewFocusedStrokeWidth;
        public final int itemBottomMargin;
        public final int structureStartMarginResize;
        public final int zoneSideMarginResize;

        public SecMarginItemDecorator(Context context) {
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
            Integer valueOf = adapter != null ? Integer.valueOf(adapter.getItemViewType(childAdapterPosition)) : null;
            boolean z = false;
            rect.top = 0;
            rect.left = 0;
            rect.right = 0;
            rect.bottom = 0;
            if ((valueOf != null && valueOf.intValue() == 1) || (valueOf != null && valueOf.intValue() == 103)) {
                z = true;
            }
            int i = this.itemBottomMargin;
            if (z) {
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
                if ((valueOf2 != null && valueOf2.intValue() == 1) || (valueOf2 != null && valueOf2.intValue() == 103)) {
                    rect.top = -i;
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x007d  */
    /* JADX WARN: Type inference failed for: r11v4, types: [com.android.systemui.controls.management.adapter.StatelessControlAdapter$spanSizeLookup$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StatelessControlAdapter(android.content.Context r11, com.android.systemui.controls.ui.util.LayoutUtil r12, com.android.systemui.controls.util.ControlsUtil r13, int r14) {
        /*
            r10 = this;
            r10.<init>()
            r10.controlsUtil = r13
            r10.currentUserId = r14
            com.android.systemui.controls.ui.util.SpanManager r14 = new com.android.systemui.controls.ui.util.SpanManager
            r14.<init>(r12)
            java.util.Map r12 = r14.spanInfos
            r0 = 0
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            com.android.systemui.controls.ui.util.SpanInfo r2 = new com.android.systemui.controls.ui.util.SpanInfo
            r3 = 3
            r4 = 0
            r2.<init>(r0, r0, r3, r4)
            r12.put(r1, r2)
            java.util.Map r12 = r14.spanInfos
            r1 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            com.android.systemui.controls.ui.util.SpanInfo r2 = new com.android.systemui.controls.ui.util.SpanInfo
            boolean r5 = com.android.systemui.BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD
            r6 = 2131169030(0x7f070f06, float:1.7952379E38)
            r7 = 2131165750(0x7f070236, float:1.7945726E38)
            if (r5 == 0) goto L42
            r13.getClass()
            boolean r8 = com.android.systemui.controls.util.ControlsUtil.isFoldDelta(r11)
            if (r8 == 0) goto L42
            android.content.res.Resources r8 = r11.getResources()
            int r8 = r8.getDimensionPixelSize(r7)
            goto L4a
        L42:
            android.content.res.Resources r8 = r11.getResources()
            int r8 = r8.getDimensionPixelSize(r6)
        L4a:
            r9 = 2
            r2.<init>(r8, r0, r9, r4)
            r12.put(r1, r2)
            java.util.Map r12 = r14.spanInfos
            r1 = 101(0x65, float:1.42E-43)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            com.android.systemui.controls.ui.util.SpanInfo r2 = new com.android.systemui.controls.ui.util.SpanInfo
            r2.<init>(r0, r0, r3, r4)
            r12.put(r1, r2)
            java.util.Map r12 = r14.spanInfos
            r1 = 102(0x66, float:1.43E-43)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            com.android.systemui.controls.ui.util.SpanInfo r2 = new com.android.systemui.controls.ui.util.SpanInfo
            r2.<init>(r0, r0, r3, r4)
            r12.put(r1, r2)
            java.util.Map r12 = r14.spanInfos
            r1 = 103(0x67, float:1.44E-43)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            com.android.systemui.controls.ui.util.SpanInfo r2 = new com.android.systemui.controls.ui.util.SpanInfo
            if (r5 == 0) goto L8f
            r13.getClass()
            boolean r13 = com.android.systemui.controls.util.ControlsUtil.isFoldDelta(r11)
            if (r13 == 0) goto L8f
            android.content.res.Resources r11 = r11.getResources()
            int r11 = r11.getDimensionPixelSize(r7)
            goto L97
        L8f:
            android.content.res.Resources r11 = r11.getResources()
            int r11 = r11.getDimensionPixelSize(r6)
        L97:
            r2.<init>(r11, r0, r9, r4)
            r12.put(r1, r2)
            r10.spanManager = r14
            com.android.systemui.controls.management.adapter.StatelessControlAdapter$spanSizeLookup$1 r11 = new com.android.systemui.controls.management.adapter.StatelessControlAdapter$spanSizeLookup$1
            r11.<init>()
            r10.spanSizeLookup = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.adapter.StatelessControlAdapter.<init>(android.content.Context, com.android.systemui.controls.ui.util.LayoutUtil, com.android.systemui.controls.util.ControlsUtil, int):void");
    }

    public static final View onCreateViewHolder$inflate(int i, ViewGroup viewGroup) {
        return MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, i, viewGroup, false);
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
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$onAttachedToRecyclerView$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    RecyclerView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    StatelessControlAdapter statelessControlAdapter = this;
                    RecyclerView recyclerView2 = RecyclerView.this;
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
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        if (i == 0) {
            return new SecZoneHolder(onCreateViewHolder$inflate(R.layout.sec_controls_zone_header, viewGroup));
        }
        ControlsUtil controlsUtil = this.controlsUtil;
        int i2 = this.currentUserId;
        if (i == 1) {
            return new SecControlHolder(onCreateViewHolder$inflate(R.layout.sec_controls_base_item, viewGroup), i2, controlsUtil, new Function2() { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$onCreateViewHolder$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    String str = (String) obj;
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    AllControlsModel allControlsModel = StatelessControlAdapter.this.model;
                    if (allControlsModel != null) {
                        allControlsModel.changeFavoriteStatus(str, booleanValue);
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        switch (i) {
            case 101:
                return new SecStructureHolder(onCreateViewHolder$inflate(R.layout.controls_structure_header, viewGroup), new Function2() { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$onCreateViewHolder$3
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Object obj3;
                        Object obj4;
                        String str = (String) obj;
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        AllControlsModel allControlsModel = StatelessControlAdapter.this.model;
                        if (allControlsModel != null) {
                            Iterator it = ((ArrayList) allControlsModel.elements).iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    obj3 = null;
                                    break;
                                }
                                obj3 = it.next();
                                SecElementWrapper secElementWrapper = (SecElementWrapper) obj3;
                                if ((secElementWrapper instanceof SecStructureNameWrapper) && Intrinsics.areEqual(((SecStructureNameWrapper) secElementWrapper).structureName, str)) {
                                    break;
                                }
                            }
                            SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj3;
                            if (secStructureNameWrapper != null && booleanValue != secStructureNameWrapper.favorite) {
                                for (ControlStatus controlStatus : allControlsModel.controls) {
                                    Iterator it2 = ((ArrayList) allControlsModel.elements).iterator();
                                    while (true) {
                                        if (!it2.hasNext()) {
                                            obj4 = null;
                                            break;
                                        }
                                        obj4 = it2.next();
                                        SecElementWrapper secElementWrapper2 = (SecElementWrapper) obj4;
                                        if (!(secElementWrapper2 instanceof SecControlStatusWrapper) || !Intrinsics.areEqual(((SecControlStatusWrapper) secElementWrapper2).controlStatus.control.getControlId(), controlStatus.control.getControlId())) {
                                        }
                                    }
                                    allControlsModel.setControlFavoriteStatus((SecControlStatusWrapper) obj4, booleanValue);
                                }
                                secStructureNameWrapper.favorite = booleanValue;
                                int indexOf = ((ArrayList) allControlsModel.elements).indexOf(secStructureNameWrapper);
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
                return new SecPaddingHolder(onCreateViewHolder$inflate(R.layout.controls_empty_padding, viewGroup));
            case 103:
                return new SecD2DControlHolder(onCreateViewHolder$inflate(R.layout.controls_d2d_base_item, viewGroup), i2, controlsUtil, new Function2() { // from class: com.android.systemui.controls.management.adapter.StatelessControlAdapter$onCreateViewHolder$2
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        String str = (String) obj;
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        AllControlsModel allControlsModel = StatelessControlAdapter.this.model;
                        if (allControlsModel != null) {
                            allControlsModel.changeFavoriteStatus(str, booleanValue);
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
