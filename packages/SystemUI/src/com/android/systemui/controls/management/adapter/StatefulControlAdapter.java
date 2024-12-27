package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.management.adapter.Holder;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.ControlActionCoordinator;
import com.android.systemui.controls.ui.ControlViewHolder;
import com.android.systemui.controls.ui.SecControlActionCoordinator;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.ui.view.ControlsSpinner;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatefulControlAdapter extends RecyclerView.Adapter {
    public static final Map controlViewHolders;
    public final AUIFacade auiFacade;
    public final BadgeProvider badgeProvider;
    public final DelayableExecutor bgExecutor;
    public final View.OnClickListener buttonClickCallback;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public final StatefulControlAdapter$itemTouchHelperCallback$1 itemTouchHelperCallback;
    public List models;
    public final SecControlsUiController.ControlsPositionChangedCallback positionChangedCallback;
    public RecyclerView recyclerView;
    public final SALogger saLogger;
    public final SecControlActionCoordinator secControlActionCoordinator;
    public final SpanManager spanManager;
    public final StatefulControlAdapter$spanSizeLookup$1 spanSizeLookup;
    public final ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectedChangedCallback;
    public final ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback;
    public final DelayableExecutor uiExecutor;
    public int uid;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ControlsItemDecoration extends RecyclerView.ItemDecoration {
        public final int basicTextViewFocusedStrokeWidth;
        public final int controlTopDownMargin;
        public final int subHeaderSideMargin;

        public ControlsItemDecoration(StatefulControlAdapter statefulControlAdapter, Context context) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.basic_interaction_side_margin);
            this.basicTextViewFocusedStrokeWidth = context.getResources().getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
            this.subHeaderSideMargin = dimensionPixelSize - (context.getResources().getDimensionPixelSize(R.dimen.control_list_horizontal_margin) - context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin));
            this.controlTopDownMargin = context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin) * 2;
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
            if ((valueOf != null && valueOf.intValue() == 1) || (valueOf != null && valueOf.intValue() == 3)) {
                z = true;
            }
            int i = this.controlTopDownMargin;
            if (z) {
                rect.bottom = i;
                return;
            }
            if (valueOf != null && valueOf.intValue() == 0) {
                int i2 = this.subHeaderSideMargin - this.basicTextViewFocusedStrokeWidth;
                rect.left = i2;
                rect.right = i2;
                if (childAdapterPosition > 0) {
                    RecyclerView.Adapter adapter2 = recyclerView.mAdapter;
                    Integer valueOf2 = adapter2 != null ? Integer.valueOf(adapter2.getItemViewType(childAdapterPosition - 1)) : null;
                    if ((valueOf2 != null && valueOf2.intValue() == 1) || (valueOf2 != null && valueOf2.intValue() == 3)) {
                        rect.top = -i;
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MainModel.Type.values().length];
            try {
                iArr[MainModel.Type.CONTROL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MainModel.Type.SMALL_CONTROL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[MainModel.Type.STRUCTURE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[MainModel.Type.COMPONENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        controlViewHolders = new LinkedHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x009a  */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StatefulControlAdapter(android.content.Context r16, com.android.systemui.controls.controller.ControlsController r17, com.android.systemui.util.concurrency.DelayableExecutor r18, com.android.systemui.util.concurrency.DelayableExecutor r19, com.android.systemui.controls.ui.ControlActionCoordinator r20, com.android.systemui.controls.ui.SecControlActionCoordinator r21, com.android.systemui.controls.ControlsMetricsLogger r22, com.android.systemui.controls.ui.util.LayoutUtil r23, com.android.systemui.controls.util.ControlsUtil r24, com.android.systemui.controls.ui.SecControlsUiController.ControlsPositionChangedCallback r25, com.android.systemui.controls.ui.view.ControlsSpinner.SpinnerTouchCallback r26, com.android.systemui.controls.ui.view.ControlsSpinner.SpinnerItemSelectionChangedCallback r27, android.view.View.OnClickListener r28, com.android.systemui.controls.ui.util.AUIFacade r29, com.android.systemui.controls.util.SALogger r30, com.android.systemui.controls.controller.util.BadgeProvider r31, int r32) {
        /*
            r15 = this;
            r0 = r15
            r15.<init>()
            r1 = r16
            r0.context = r1
            r2 = r17
            r0.controlsController = r2
            r2 = r18
            r0.uiExecutor = r2
            r2 = r19
            r0.bgExecutor = r2
            r2 = r20
            r0.controlActionCoordinator = r2
            r2 = r21
            r0.secControlActionCoordinator = r2
            r2 = r22
            r0.controlsMetricsLogger = r2
            r2 = r24
            r0.controlsUtil = r2
            r3 = r25
            r0.positionChangedCallback = r3
            r3 = r26
            r0.spinnerTouchCallback = r3
            r3 = r27
            r0.spinnerItemSelectedChangedCallback = r3
            r3 = r28
            r0.buttonClickCallback = r3
            r3 = r29
            r0.auiFacade = r3
            r3 = r30
            r0.saLogger = r3
            r3 = r31
            r0.badgeProvider = r3
            r3 = r32
            r0.currentUserId = r3
            com.android.systemui.controls.ui.util.SpanManager r3 = new com.android.systemui.controls.ui.util.SpanManager
            r4 = r23
            r3.<init>(r4)
            java.util.Map r4 = r3.spanInfos
            r5 = 0
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            com.android.systemui.controls.ui.util.SpanInfo r7 = new com.android.systemui.controls.ui.util.SpanInfo
            r8 = 3
            r9 = 0
            r7.<init>(r5, r5, r8, r9)
            r4.put(r6, r7)
            java.util.Map r4 = r3.spanInfos
            r6 = 1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            com.android.systemui.controls.ui.util.SpanInfo r7 = new com.android.systemui.controls.ui.util.SpanInfo
            boolean r10 = com.android.systemui.BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD
            r11 = 2131169030(0x7f070f06, float:1.7952379E38)
            r12 = 2131165750(0x7f070236, float:1.7945726E38)
            if (r10 == 0) goto L81
            r24.getClass()
            boolean r13 = com.android.systemui.controls.util.ControlsUtil.isFoldDelta(r16)
            if (r13 == 0) goto L81
            android.content.res.Resources r13 = r16.getResources()
            int r13 = r13.getDimensionPixelSize(r12)
            goto L89
        L81:
            android.content.res.Resources r13 = r16.getResources()
            int r13 = r13.getDimensionPixelSize(r11)
        L89:
            r14 = 2
            r7.<init>(r13, r5, r14, r9)
            r4.put(r6, r7)
            java.util.Map r4 = r3.spanInfos
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)
            com.android.systemui.controls.ui.util.SpanInfo r7 = new com.android.systemui.controls.ui.util.SpanInfo
            if (r10 == 0) goto Lac
            r24.getClass()
            boolean r2 = com.android.systemui.controls.util.ControlsUtil.isFoldDelta(r16)
            if (r2 == 0) goto Lac
            android.content.res.Resources r1 = r16.getResources()
            int r1 = r1.getDimensionPixelSize(r12)
            goto Lb4
        Lac:
            android.content.res.Resources r1 = r16.getResources()
            int r1 = r1.getDimensionPixelSize(r11)
        Lb4:
            r7.<init>(r1, r5, r14, r9)
            r4.put(r6, r7)
            java.util.Map r1 = r3.spanInfos
            r2 = 4
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            com.android.systemui.controls.ui.util.SpanInfo r4 = new com.android.systemui.controls.ui.util.SpanInfo
            r4.<init>(r5, r5, r8, r9)
            r1.put(r2, r4)
            r0.spanManager = r3
            r1 = -1
            r0.uid = r1
            kotlin.collections.EmptyList r1 = kotlin.collections.EmptyList.INSTANCE
            r0.models = r1
            com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1 r1 = new com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1
            r1.<init>()
            r0.spanSizeLookup = r1
            com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1 r1 = new com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1
            r1.<init>()
            r0.itemTouchHelperCallback = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.adapter.StatefulControlAdapter.<init>(android.content.Context, com.android.systemui.controls.controller.ControlsController, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.controls.ui.ControlActionCoordinator, com.android.systemui.controls.ui.SecControlActionCoordinator, com.android.systemui.controls.ControlsMetricsLogger, com.android.systemui.controls.ui.util.LayoutUtil, com.android.systemui.controls.util.ControlsUtil, com.android.systemui.controls.ui.SecControlsUiController$ControlsPositionChangedCallback, com.android.systemui.controls.ui.view.ControlsSpinner$SpinnerTouchCallback, com.android.systemui.controls.ui.view.ControlsSpinner$SpinnerItemSelectionChangedCallback, android.view.View$OnClickListener, com.android.systemui.controls.ui.util.AUIFacade, com.android.systemui.controls.util.SALogger, com.android.systemui.controls.controller.util.BadgeProvider, int):void");
    }

    public final void attachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        int measuredWidth = recyclerView.getMeasuredWidth();
        SpanManager spanManager = this.spanManager;
        spanManager.updateSpanInfos(measuredWidth);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.context, spanManager.maxSpan);
        gridLayoutManager.mSpanSizeLookup = this.spanSizeLookup;
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new ControlsItemDecoration(this, recyclerView.getContext()));
        new ItemTouchHelper(this.itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.models.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[((MainModel) this.models.get(i)).getType().ordinal()];
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            return 3;
        }
        if (i2 == 3) {
            return 0;
        }
        if (i2 == 4) {
            return 4;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        if (recyclerView.getMeasuredWidth() == 0) {
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.controls.management.adapter.StatefulControlAdapter$onAttachedToRecyclerView$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    RecyclerView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    StatefulControlAdapter statefulControlAdapter = this;
                    RecyclerView recyclerView2 = RecyclerView.this;
                    Map map = StatefulControlAdapter.controlViewHolders;
                    statefulControlAdapter.attachedToRecyclerView(recyclerView2);
                }
            });
        } else {
            attachedToRecyclerView(recyclerView);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((Holder) viewHolder).bindData((MainModel) this.models.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        ControlHolder controlHolder;
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            return new StructureHolder(from.inflate(R.layout.controls_main_zone_header, viewGroup, false));
        }
        ControlsUtil controlsUtil = this.controlsUtil;
        SecControlActionCoordinator secControlActionCoordinator = this.secControlActionCoordinator;
        if (i == 1) {
            View inflate = from.inflate(R.layout.sec_controls_base_item, viewGroup, false);
            int i2 = this.uid;
            ControlViewHolder controlViewHolder = new ControlViewHolder((ViewGroup) inflate, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, i2, this.currentUserId);
            controlViewHolder.getSecControlViewHolder().initialize(secControlActionCoordinator, controlsUtil, 0);
            controlHolder = new ControlHolder(inflate, controlViewHolder, controlViewHolders);
        } else {
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong viewType: "));
                }
                return new SpinnerLayoutHolder(from.inflate(R.layout.controls_spinner_layout, viewGroup, false), this.spinnerTouchCallback, this.spinnerItemSelectedChangedCallback, this.buttonClickCallback, this.badgeProvider);
            }
            View inflate2 = from.inflate(R.layout.controls_d2d_base_item, viewGroup, false);
            ViewStub viewStub = (ViewStub) inflate2.requireViewById(R.id.d2d_layout_view_stub);
            viewStub.setLayoutResource(R.layout.controls_status_info);
            viewStub.inflate();
            int i3 = this.uid;
            ControlViewHolder controlViewHolder2 = new ControlViewHolder((ViewGroup) inflate2, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, i3, this.currentUserId);
            controlViewHolder2.getSecControlViewHolder().initialize(secControlActionCoordinator, controlsUtil, 1);
            controlHolder = new ControlHolder(inflate2, controlViewHolder2, controlViewHolders);
        }
        return controlHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        Holder holder = (Holder) viewHolder;
        if (holder instanceof ControlHolder) {
            int size = this.models.size();
            ControlHolder controlHolder = (ControlHolder) holder;
            int i = controlHolder.mPreLayoutPosition;
            if (size > (i == -1 ? controlHolder.mPosition : i)) {
                List list = this.models;
                if (i == -1) {
                    i = controlHolder.mPosition;
                }
                controlHolder.controlViewHolder.getSecControlViewHolder().layout.setAlpha(((MainModel) list.get(i)).needToMakeDim ? 0.5f : 1.0f);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        Holder holder = (Holder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder(holder, i);
            return;
        }
        List list2 = list;
        boolean z = list2 instanceof Collection;
        if (!z || !list2.isEmpty()) {
            Iterator it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next() == Holder.UpdateReq.UPDATE_DIM_STATUS) {
                    if (holder instanceof ControlHolder) {
                        ((ControlHolder) holder).controlViewHolder.getSecControlViewHolder().layout.setAlpha(((MainModel) this.models.get(i)).needToMakeDim ? 0.5f : 1.0f);
                    }
                }
            }
        }
        if (z && list2.isEmpty()) {
            return;
        }
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            if (it2.next() != Holder.UpdateReq.UPDATE_DIM_STATUS) {
                onBindViewHolder(holder, i);
                return;
            }
        }
    }
}
