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
import com.android.systemui.utils.SafeIconLoader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
    public final SafeIconLoader safeIconLoader;
    public final SecControlActionCoordinator secControlActionCoordinator;
    public final SpanManager spanManager;
    public final StatefulControlAdapter$spanSizeLookup$1 spanSizeLookup;
    public final ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectedChangedCallback;
    public final ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback;
    public final DelayableExecutor uiExecutor;
    public int uid;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:8:0x0099  */
    /* JADX WARN: Type inference failed for: r14v7, types: [com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1] */
    /* JADX WARN: Type inference failed for: r14v8, types: [com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StatefulControlAdapter(android.content.Context r14, com.android.systemui.controls.controller.ControlsController r15, com.android.systemui.util.concurrency.DelayableExecutor r16, com.android.systemui.util.concurrency.DelayableExecutor r17, com.android.systemui.controls.ui.ControlActionCoordinator r18, com.android.systemui.controls.ui.SecControlActionCoordinator r19, com.android.systemui.controls.ControlsMetricsLogger r20, com.android.systemui.controls.ui.util.LayoutUtil r21, com.android.systemui.controls.util.ControlsUtil r22, com.android.systemui.controls.ui.SecControlsUiController.ControlsPositionChangedCallback r23, com.android.systemui.controls.ui.view.ControlsSpinner.SpinnerTouchCallback r24, com.android.systemui.controls.ui.view.ControlsSpinner.SpinnerItemSelectionChangedCallback r25, com.android.systemui.utils.SafeIconLoader r26, android.view.View.OnClickListener r27, com.android.systemui.controls.ui.util.AUIFacade r28, com.android.systemui.controls.util.SALogger r29, com.android.systemui.controls.controller.util.BadgeProvider r30, int r31) {
        /*
            r13 = this;
            r13.<init>()
            r13.context = r14
            r13.controlsController = r15
            r0 = r16
            r13.uiExecutor = r0
            r0 = r17
            r13.bgExecutor = r0
            r0 = r18
            r13.controlActionCoordinator = r0
            r0 = r19
            r13.secControlActionCoordinator = r0
            r0 = r20
            r13.controlsMetricsLogger = r0
            r0 = r22
            r13.controlsUtil = r0
            r1 = r23
            r13.positionChangedCallback = r1
            r1 = r24
            r13.spinnerTouchCallback = r1
            r1 = r25
            r13.spinnerItemSelectedChangedCallback = r1
            r1 = r26
            r13.safeIconLoader = r1
            r1 = r27
            r13.buttonClickCallback = r1
            r1 = r28
            r13.auiFacade = r1
            r1 = r29
            r13.saLogger = r1
            r1 = r30
            r13.badgeProvider = r1
            r1 = r31
            r13.currentUserId = r1
            com.android.systemui.controls.ui.util.SpanManager r1 = new com.android.systemui.controls.ui.util.SpanManager
            r2 = r21
            r1.<init>(r2)
            java.util.Map r2 = r1.spanInfos
            r3 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            com.android.systemui.controls.ui.util.SpanInfo r5 = new com.android.systemui.controls.ui.util.SpanInfo
            r6 = 3
            r7 = 0
            r5.<init>(r3, r3, r6, r7)
            r2.put(r4, r5)
            java.util.Map r2 = r1.spanInfos
            r4 = 1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            com.android.systemui.controls.ui.util.SpanInfo r5 = new com.android.systemui.controls.ui.util.SpanInfo
            boolean r8 = com.android.systemui.BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD
            r9 = 2131169418(0x7f07108a, float:1.7953166E38)
            r10 = 2131165800(0x7f070268, float:1.7945827E38)
            if (r8 == 0) goto L80
            r0.getClass()
            boolean r11 = com.android.systemui.controls.util.ControlsUtil.isFoldDelta(r14)
            if (r11 == 0) goto L80
            android.content.res.Resources r11 = r14.getResources()
            int r11 = r11.getDimensionPixelSize(r10)
            goto L88
        L80:
            android.content.res.Resources r11 = r14.getResources()
            int r11 = r11.getDimensionPixelSize(r9)
        L88:
            r12 = 2
            r5.<init>(r11, r3, r12, r7)
            r2.put(r4, r5)
            java.util.Map r2 = r1.spanInfos
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)
            com.android.systemui.controls.ui.util.SpanInfo r5 = new com.android.systemui.controls.ui.util.SpanInfo
            if (r8 == 0) goto Lab
            r0.getClass()
            boolean r0 = com.android.systemui.controls.util.ControlsUtil.isFoldDelta(r14)
            if (r0 == 0) goto Lab
            android.content.res.Resources r14 = r14.getResources()
            int r14 = r14.getDimensionPixelSize(r10)
            goto Lb3
        Lab:
            android.content.res.Resources r14 = r14.getResources()
            int r14 = r14.getDimensionPixelSize(r9)
        Lb3:
            r5.<init>(r14, r3, r12, r7)
            r2.put(r4, r5)
            java.util.Map r14 = r1.spanInfos
            r0 = 4
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            com.android.systemui.controls.ui.util.SpanInfo r2 = new com.android.systemui.controls.ui.util.SpanInfo
            r2.<init>(r3, r3, r6, r7)
            r14.put(r0, r2)
            r13.spanManager = r1
            r14 = -1
            r13.uid = r14
            kotlin.collections.EmptyList r14 = kotlin.collections.EmptyList.INSTANCE
            r13.models = r14
            com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1 r14 = new com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1
            r14.<init>()
            r13.spanSizeLookup = r14
            com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1 r14 = new com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1
            r14.<init>()
            r13.itemTouchHelperCallback = r14
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.adapter.StatefulControlAdapter.<init>(android.content.Context, com.android.systemui.controls.controller.ControlsController, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.controls.ui.ControlActionCoordinator, com.android.systemui.controls.ui.SecControlActionCoordinator, com.android.systemui.controls.ControlsMetricsLogger, com.android.systemui.controls.ui.util.LayoutUtil, com.android.systemui.controls.util.ControlsUtil, com.android.systemui.controls.ui.SecControlsUiController$ControlsPositionChangedCallback, com.android.systemui.controls.ui.view.ControlsSpinner$SpinnerTouchCallback, com.android.systemui.controls.ui.view.ControlsSpinner$SpinnerItemSelectionChangedCallback, com.android.systemui.utils.SafeIconLoader, android.view.View$OnClickListener, com.android.systemui.controls.ui.util.AUIFacade, com.android.systemui.controls.util.SALogger, com.android.systemui.controls.controller.util.BadgeProvider, int):void");
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
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            return new StructureHolder(from.inflate(R.layout.controls_main_zone_header, viewGroup, false));
        }
        ControlsUtil controlsUtil = this.controlsUtil;
        SecControlActionCoordinator secControlActionCoordinator = this.secControlActionCoordinator;
        if (i == 1) {
            View inflate = from.inflate(R.layout.sec_controls_base_item, viewGroup, false);
            int i2 = this.uid;
            ControlViewHolder controlViewHolder = new ControlViewHolder((ViewGroup) inflate, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, i2, this.currentUserId, this.safeIconLoader);
            controlViewHolder.getSecControlViewHolder().initialize(secControlActionCoordinator, controlsUtil, 0);
            return new ControlHolder(inflate, controlViewHolder, controlViewHolders);
        }
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
        ControlViewHolder controlViewHolder2 = new ControlViewHolder((ViewGroup) inflate2, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, i3, this.currentUserId, this.safeIconLoader);
        controlViewHolder2.getSecControlViewHolder().initialize(secControlActionCoordinator, controlsUtil, 1);
        return new ControlHolder(inflate2, controlViewHolder2, controlViewHolders);
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
