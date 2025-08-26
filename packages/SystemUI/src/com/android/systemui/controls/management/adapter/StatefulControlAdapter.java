package com.android.systemui.controls.management.adapter;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.management.adapter.Holder;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.ControlActionCoordinator;
import com.android.systemui.controls.ui.ControlViewHolder;
import com.android.systemui.controls.ui.SecControlActionCoordinator;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl$controlsPositionChangedCallback$1;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SpanInfo;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.ui.view.ControlsSpinner;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.utils.SafeIconLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ControlsItemDecoration extends RecyclerView.ItemDecoration {
        public final int basicTextViewFocusedStrokeWidth;
        public final int controlTopDownMargin;
        public final int subHeaderSideMargin;

        public ControlsItemDecoration(StatefulControlAdapter statefulControlAdapter, Context context) throws Resources.NotFoundException {
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
            Integer numValueOf = adapter != null ? Integer.valueOf(adapter.getItemViewType(childAdapterPosition)) : null;
            boolean z = false;
            rect.top = 0;
            rect.left = 0;
            rect.right = 0;
            rect.bottom = 0;
            if ((numValueOf != null && numValueOf.intValue() == 1) || (numValueOf != null && numValueOf.intValue() == 3)) {
                z = true;
            }
            int i = this.controlTopDownMargin;
            if (z) {
                rect.bottom = i;
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == 0) {
                int i2 = this.subHeaderSideMargin - this.basicTextViewFocusedStrokeWidth;
                rect.left = i2;
                rect.right = i2;
                if (childAdapterPosition > 0) {
                    RecyclerView.Adapter adapter2 = recyclerView.mAdapter;
                    Integer numValueOf2 = adapter2 != null ? Integer.valueOf(adapter2.getItemViewType(childAdapterPosition - 1)) : null;
                    if ((numValueOf2 != null && numValueOf2.intValue() == 1) || (numValueOf2 != null && numValueOf2.intValue() == 3)) {
                        rect.top = -i;
                    }
                }
            }
        }
    }

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

    /* JADX WARN: Removed duplicated region for block: B:13:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0080  */
    /* JADX WARN: Type inference failed for: r14v7, types: [com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1] */
    /* JADX WARN: Type inference failed for: r14v8, types: [com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StatefulControlAdapter(Context context, ControlsController controlsController, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator, SecControlActionCoordinator secControlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, LayoutUtil layoutUtil, ControlsUtil controlsUtil, SecControlsUiController.ControlsPositionChangedCallback controlsPositionChangedCallback, ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback, ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectionChangedCallback, SafeIconLoader safeIconLoader, View.OnClickListener onClickListener, AUIFacade aUIFacade, SALogger sALogger, BadgeProvider badgeProvider, int i) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        this.context = context;
        this.controlsController = controlsController;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.secControlActionCoordinator = secControlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.controlsUtil = controlsUtil;
        this.positionChangedCallback = controlsPositionChangedCallback;
        this.spinnerTouchCallback = spinnerTouchCallback;
        this.spinnerItemSelectedChangedCallback = spinnerItemSelectionChangedCallback;
        this.safeIconLoader = safeIconLoader;
        this.buttonClickCallback = onClickListener;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.badgeProvider = badgeProvider;
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
        Map map2 = spanManager.spanInfos;
        if (z) {
            controlsUtil.getClass();
            dimensionPixelSize2 = ControlsUtil.isFoldDelta(context) ? context.getResources().getDimensionPixelSize(R.dimen.control_base_item_size_fold) : context.getResources().getDimensionPixelSize(R.dimen.sec_control_base_item_size);
        }
        map2.put(3, new SpanInfo(dimensionPixelSize2, 0, 2, null));
        spanManager.spanInfos.put(4, new SpanInfo(0, 0, 3, null));
        this.spanManager = spanManager;
        this.uid = -1;
        this.models = EmptyList.INSTANCE;
        this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.StatefulControlAdapter$spanSizeLookup$1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i2) {
                StatefulControlAdapter statefulControlAdapter = this.this$0;
                if (i2 >= statefulControlAdapter.models.size()) {
                    return 0;
                }
                SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) statefulControlAdapter.spanManager.spanInfos).get(Integer.valueOf(statefulControlAdapter.getItemViewType(i2)));
                if (spanInfo != null) {
                    return spanInfo.span;
                }
                return 0;
            }
        };
        this.itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.adapter.StatefulControlAdapter$itemTouchHelperCallback$1
            public final int DEFAULT_POS;
            public final int MOVEMENT;
            public int dragPos;
            public boolean startDrag;

            {
                super(0, 0);
                this.DEFAULT_POS = -1;
                this.dragPos = -1;
                this.MOVEMENT = 15;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean canDropOver(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1) {
                    return false;
                }
                StatefulControlAdapter statefulControlAdapter = this.this$0;
                MainModel mainModel = (MainModel) statefulControlAdapter.models.get(bindingAdapterPosition);
                if (mainModel.getType() == MainModel.Type.STRUCTURE) {
                    return false;
                }
                int iIndexOf = statefulControlAdapter.models.indexOf(mainModel);
                int i2 = iIndexOf;
                while (i2 > 0 && ((MainModel) statefulControlAdapter.models.get(i2)).getType() == MainModel.Type.CONTROL) {
                    i2--;
                }
                while (iIndexOf < statefulControlAdapter.models.size() - 1 && ((MainModel) statefulControlAdapter.models.get(iIndexOf)).getType() == MainModel.Type.CONTROL) {
                    iIndexOf++;
                }
                MainModel.Type type = ((MainModel) statefulControlAdapter.models.get(i2)).getType();
                MainModel.Type type2 = MainModel.Type.STRUCTURE;
                if (type == type2) {
                    i2++;
                }
                if (((MainModel) statefulControlAdapter.models.get(iIndexOf)).getType() == type2) {
                    iIndexOf--;
                }
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                return i2 <= bindingAdapterPosition2 && bindingAdapterPosition2 <= iIndexOf;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1) {
                    return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                }
                StatefulControlAdapter statefulControlAdapter = this.this$0;
                MainModel mainModel = (MainModel) statefulControlAdapter.models.get(bindingAdapterPosition);
                if (mainModel.getType() != MainModel.Type.CONTROL && mainModel.getType() != MainModel.Type.SMALL_CONTROL) {
                    return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                }
                ControlsUtil controlsUtil2 = statefulControlAdapter.controlsUtil;
                if (controlsUtil2.isSecureLocked()) {
                    Context context2 = statefulControlAdapter.context;
                    controlsUtil2.getClass();
                    if (Settings.Secure.getInt(context2.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
                        Log.d("StatefulControlAdapter", "request DismissKeyguard");
                        ((KeyguardManager) statefulControlAdapter.context.getSystemService("keyguard")).requestDismissKeyguard((Activity) statefulControlAdapter.context, null);
                        return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                    }
                }
                return mainModel.getType() == MainModel.Type.SMALL_CONTROL ? ItemTouchHelper.Callback.makeMovementFlags(0, 0) : ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i2, boolean z2) throws Resources.NotFoundException {
                super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i2, z2);
                int scaledTouchSlop = ViewConfiguration.get(recyclerView.getContext()).getScaledTouchSlop();
                int i3 = this.dragPos;
                StatefulControlAdapter statefulControlAdapter = this.this$0;
                int i4 = this.DEFAULT_POS;
                if (i3 == i4 && scaledTouchSlop < ((float) Math.hypot(f, f2))) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    this.dragPos = bindingAdapterPosition;
                    Object obj = statefulControlAdapter.models.get(bindingAdapterPosition);
                    MainControlModel mainControlModel = obj instanceof MainControlModel ? (MainControlModel) obj : null;
                    if (mainControlModel != null) {
                        List list = statefulControlAdapter.models;
                        ArrayList arrayList = new ArrayList();
                        for (Object obj2 : list) {
                            if (obj2 instanceof MainControlModel) {
                                arrayList.add(obj2);
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        int size = arrayList.size();
                        int i5 = 0;
                        while (i5 < size) {
                            Object obj3 = arrayList.get(i5);
                            i5++;
                            if (!Intrinsics.areEqual(((MainControlModel) obj3).structure, mainControlModel.structure)) {
                                arrayList2.add(obj3);
                            }
                        }
                        int size2 = arrayList2.size();
                        int i6 = 0;
                        while (i6 < size2) {
                            Object obj4 = arrayList2.get(i6);
                            i6++;
                            ((MainControlModel) obj4).needToMakeDim = true;
                        }
                        statefulControlAdapter.mObservable.notifyItemRangeChanged(0, statefulControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                    }
                } else if (f == 0.0f && f2 == 0.0f) {
                    if (this.dragPos != i4) {
                        List list2 = statefulControlAdapter.models;
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj5 : list2) {
                            if (obj5 instanceof MainControlModel) {
                                arrayList3.add(obj5);
                            }
                        }
                        int size3 = arrayList3.size();
                        int i7 = 0;
                        while (i7 < size3) {
                            Object obj6 = arrayList3.get(i7);
                            i7++;
                            ((MainControlModel) obj6).needToMakeDim = false;
                        }
                        statefulControlAdapter.mObservable.notifyItemRangeChanged(0, statefulControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                    }
                    this.dragPos = i4;
                }
                if (f == 0.0f && f2 == 0.0f) {
                    return;
                }
                View view = viewHolder.itemView;
                Drawable drawable = view.getContext().getResources().getDrawable(R.drawable.control_reorder_card_guide_line, view.getContext().getTheme());
                Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                rect.offset(view.getLeft(), view.getTop());
                drawable.setBounds(rect);
                drawable.draw(canvas);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                StatefulControlAdapter statefulControlAdapter = this.this$0;
                statefulControlAdapter.getClass();
                if (bindingAdapterPosition < bindingAdapterPosition2) {
                    int i2 = bindingAdapterPosition;
                    while (i2 < bindingAdapterPosition2) {
                        int i3 = i2 + 1;
                        Collections.swap(statefulControlAdapter.models, i2, i3);
                        i2 = i3;
                    }
                } else {
                    int i4 = bindingAdapterPosition2 + 1;
                    if (i4 <= bindingAdapterPosition) {
                        int i5 = bindingAdapterPosition;
                        while (true) {
                            Collections.swap(statefulControlAdapter.models, i5, i5 - 1);
                            if (i5 == i4) {
                                break;
                            }
                            i5--;
                        }
                    }
                }
                statefulControlAdapter.notifyItemMoved(bindingAdapterPosition, bindingAdapterPosition2);
                Object obj = statefulControlAdapter.models.get(bindingAdapterPosition);
                if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) == null) {
                    return true;
                }
                SecControlsUiControllerImpl secControlsUiControllerImpl = ((SecControlsUiControllerImpl$controlsPositionChangedCallback$1) statefulControlAdapter.positionChangedCallback).this$0;
                secControlsUiControllerImpl.isChanged = true;
                secControlsUiControllerImpl.verificationStructureInfos = secControlsUiControllerImpl.getStructureInfosByUI(secControlsUiControllerImpl.selectedItem.getComponentName());
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i2) {
                super.onSelectedChanged(viewHolder, i2);
                StatefulControlAdapter statefulControlAdapter = this.this$0;
                if (i2 == 0) {
                    if (this.startDrag) {
                        statefulControlAdapter.saLogger.sendEvent(SALogger.Event.MoveCard.INSTANCE);
                    }
                    this.startDrag = false;
                } else {
                    if (i2 != 2) {
                        return;
                    }
                    this.startDrag = true;
                    if (viewHolder != null) {
                        View view = viewHolder.itemView;
                        ((AUIFacadeImpl) statefulControlAdapter.auiFacade).audioManager.playSoundEffect(106);
                        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
            }
        };
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
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.controls.management.adapter.StatefulControlAdapter.onAttachedToRecyclerView.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    StatefulControlAdapter statefulControlAdapter = this;
                    RecyclerView recyclerView2 = recyclerView;
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
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) throws Resources.NotFoundException {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            return new StructureHolder(layoutInflaterFrom.inflate(R.layout.controls_main_zone_header, viewGroup, false));
        }
        ControlsUtil controlsUtil = this.controlsUtil;
        SecControlActionCoordinator secControlActionCoordinator = this.secControlActionCoordinator;
        if (i == 1) {
            View viewInflate = layoutInflaterFrom.inflate(R.layout.sec_controls_base_item, viewGroup, false);
            int i2 = this.uid;
            ControlViewHolder controlViewHolder = new ControlViewHolder((ViewGroup) viewInflate, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, i2, this.currentUserId, this.safeIconLoader);
            controlViewHolder.getSecControlViewHolder().initialize(secControlActionCoordinator, controlsUtil, 0);
            return new ControlHolder(viewInflate, controlViewHolder, controlViewHolders);
        }
        if (i != 3) {
            if (i != 4) {
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong viewType: "));
            }
            return new SpinnerLayoutHolder(layoutInflaterFrom.inflate(R.layout.controls_spinner_layout, viewGroup, false), this.spinnerTouchCallback, this.spinnerItemSelectedChangedCallback, this.buttonClickCallback, this.badgeProvider);
        }
        View viewInflate2 = layoutInflaterFrom.inflate(R.layout.controls_d2d_base_item, viewGroup, false);
        ViewStub viewStub = (ViewStub) viewInflate2.requireViewById(R.id.d2d_layout_view_stub);
        viewStub.setLayoutResource(R.layout.controls_status_info);
        viewStub.inflate();
        int i3 = this.uid;
        ControlViewHolder controlViewHolder2 = new ControlViewHolder((ViewGroup) viewInflate2, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, i3, this.currentUserId, this.safeIconLoader);
        controlViewHolder2.getSecControlViewHolder().initialize(secControlActionCoordinator, controlsUtil, 1);
        return new ControlHolder(viewInflate2, controlViewHolder2, controlViewHolders);
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
