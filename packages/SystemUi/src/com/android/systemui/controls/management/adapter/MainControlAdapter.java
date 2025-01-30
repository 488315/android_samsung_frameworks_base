package com.android.systemui.controls.management.adapter;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.android.systemui.controls.ui.CustomControlActionCoordinator;
import com.android.systemui.controls.ui.CustomControlsUiController;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl$controlsPositionChangedCallback$1;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.controls.ui.util.SpanInfo;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.ui.view.ControlsSpinner;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.material.appbar.AppBarLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MainControlAdapter extends RecyclerView.Adapter {
    public static final Map controlViewHolders;
    public final AppBarLayout appBarLayout;
    public final AUIFacade auiFacade;
    public final BadgeProvider badgeProvider;
    public final DelayableExecutor bgExecutor;
    public final View.OnClickListener buttonClickCallback;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final int currentUserId;
    public final CustomControlActionCoordinator customControlActionCoordinator;
    public final MainControlAdapter$itemTouchHelperCallback$1 itemTouchHelperCallback;
    public List models;
    public final CustomControlsUiController.ControlsPanelUpdatedCallback panelUpdatedCallback;
    public final CustomControlsUiController.ControlsPositionChangedCallback positionChangedCallback;
    public final SALogger saLogger;
    public final SpanManager spanManager;
    public final MainControlAdapter$spanSizeLookup$1 spanSizeLookup;
    public final ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectedChangedCallback;
    public final ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback;
    public final DelayableExecutor uiExecutor;
    public int uid;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ControlsItemDecoration extends RecyclerView.ItemDecoration {
        public final int basicTextViewFocusedStrokeWidth;
        public final int controlTopDownMargin;
        public final int listMarginResize;
        public final int subheaderSideMargin;

        public ControlsItemDecoration(MainControlAdapter mainControlAdapter, Context context) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.basic_interaction_side_margin);
            this.basicTextViewFocusedStrokeWidth = context.getResources().getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.controls_list_margin_horizontal) - context.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin);
            this.listMarginResize = dimensionPixelSize2;
            this.subheaderSideMargin = dimensionPixelSize - dimensionPixelSize2;
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
            boolean z2 = (valueOf != null && valueOf.intValue() == 1) || (BasicRune.CONTROLS_LAYOUT_TYPE && valueOf != null && valueOf.intValue() == 3);
            int i = this.controlTopDownMargin;
            if (z2) {
                rect.bottom = i;
                return;
            }
            if (valueOf == null || valueOf.intValue() != 0) {
                if (valueOf != null && valueOf.intValue() == 5) {
                    int i2 = -this.listMarginResize;
                    rect.left = i2;
                    rect.right = i2;
                    rect.top = i;
                    return;
                }
                return;
            }
            int i3 = this.subheaderSideMargin - this.basicTextViewFocusedStrokeWidth;
            rect.left = i3;
            rect.right = i3;
            if (childAdapterPosition > 0) {
                RecyclerView.Adapter adapter2 = recyclerView.mAdapter;
                Integer valueOf2 = adapter2 != null ? Integer.valueOf(adapter2.getItemViewType(childAdapterPosition - 1)) : null;
                if ((valueOf2 != null && valueOf2.intValue() == 1) || (BasicRune.CONTROLS_LAYOUT_TYPE && valueOf2 != null && valueOf2.intValue() == 3)) {
                    z = true;
                }
                if (z) {
                    rect.top = -i;
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            try {
                iArr[MainModel.Type.PANEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        controlViewHolders = new LinkedHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x00a2  */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.controls.management.adapter.MainControlAdapter$spanSizeLookup$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.controls.management.adapter.MainControlAdapter$itemTouchHelperCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MainControlAdapter(Context context, ControlsController controlsController, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator, CustomControlActionCoordinator customControlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, AppBarLayout appBarLayout, LayoutUtil layoutUtil, ControlsUtil controlsUtil, CustomControlsUiController.ControlsPositionChangedCallback controlsPositionChangedCallback, CustomControlsUiController.ControlsPanelUpdatedCallback controlsPanelUpdatedCallback, ControlsSpinner.SpinnerTouchCallback spinnerTouchCallback, ControlsSpinner.SpinnerItemSelectionChangedCallback spinnerItemSelectionChangedCallback, View.OnClickListener onClickListener, AUIFacade aUIFacade, SALogger sALogger, BadgeProvider badgeProvider, ControlsRuneWrapper controlsRuneWrapper, int i) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        this.context = context;
        this.controlsController = controlsController;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.customControlActionCoordinator = customControlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.appBarLayout = appBarLayout;
        this.controlsUtil = controlsUtil;
        this.positionChangedCallback = controlsPositionChangedCallback;
        this.panelUpdatedCallback = controlsPanelUpdatedCallback;
        this.spinnerTouchCallback = spinnerTouchCallback;
        this.spinnerItemSelectedChangedCallback = spinnerItemSelectionChangedCallback;
        this.buttonClickCallback = onClickListener;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.badgeProvider = badgeProvider;
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
                if (z) {
                    controlsUtil.getClass();
                    if (ControlsUtil.isFoldDelta(context)) {
                        dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size_fold);
                        map.put(3, new SpanInfo(dimensionPixelSize2, 0, 2, null));
                        map.put(4, new SpanInfo(0, 0, 3, null));
                        map.put(5, new SpanInfo(0, 0, 3, null));
                        this.spanManager = spanManager;
                        this.uid = -1;
                        this.models = EmptyList.INSTANCE;
                        this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$spanSizeLookup$1
                            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                            public final int getSpanSize(int i2) {
                                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                                if (i2 >= mainControlAdapter.getItemCount()) {
                                    return 0;
                                }
                                SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) mainControlAdapter.spanManager.spanInfos).get(Integer.valueOf(mainControlAdapter.getItemViewType(i2)));
                                if (spanInfo != null) {
                                    return spanInfo.span;
                                }
                                return 0;
                            }
                        };
                        this.itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$itemTouchHelperCallback$1
                            public final int DEFAULT_POS;
                            public final int MOVEMENT;
                            public int dragPos;
                            public boolean needToCheckAppBar;
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
                                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                                MainModel mainModel = (MainModel) mainControlAdapter.models.get(bindingAdapterPosition);
                                if (mainModel.getType() == MainModel.Type.STRUCTURE) {
                                    return false;
                                }
                                int indexOf = mainControlAdapter.models.indexOf(mainModel);
                                int i2 = indexOf;
                                while (i2 > 0 && ((MainModel) mainControlAdapter.models.get(i2)).getType() == MainModel.Type.CONTROL) {
                                    i2--;
                                }
                                while (indexOf < mainControlAdapter.models.size() - 1 && ((MainModel) mainControlAdapter.models.get(indexOf)).getType() == MainModel.Type.CONTROL) {
                                    indexOf++;
                                }
                                MainModel.Type type = ((MainModel) mainControlAdapter.models.get(i2)).getType();
                                MainModel.Type type2 = MainModel.Type.STRUCTURE;
                                if (type == type2) {
                                    i2++;
                                }
                                if (((MainModel) mainControlAdapter.models.get(indexOf)).getType() == type2) {
                                    indexOf--;
                                }
                                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                                return i2 <= bindingAdapterPosition2 && bindingAdapterPosition2 <= indexOf;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                                super.clearView(recyclerView, viewHolder);
                                this.needToCheckAppBar = false;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                                int makeMovementFlags = ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                                if (bindingAdapterPosition == -1) {
                                    return makeMovementFlags;
                                }
                                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                                MainModel mainModel = (MainModel) mainControlAdapter.models.get(bindingAdapterPosition);
                                if (mainModel.getType() != MainModel.Type.CONTROL && mainModel.getType() != MainModel.Type.SMALL_CONTROL) {
                                    return makeMovementFlags;
                                }
                                if (BasicRune.CONTROLS_REQUEST_UNLOCK_WHEN_LONG_PRESSED_CARD) {
                                    ControlsUtil controlsUtil2 = mainControlAdapter.controlsUtil;
                                    if (controlsUtil2.isSecureLocked()) {
                                        controlsUtil2.getClass();
                                        Context context2 = mainControlAdapter.context;
                                        if (Settings.Secure.getInt(context2.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
                                            Log.d("MainControlAdapter", "request DismissKeyguard");
                                            ((KeyguardManager) context2.getSystemService("keyguard")).requestDismissKeyguard((Activity) context2, null);
                                            return makeMovementFlags;
                                        }
                                    }
                                }
                                return mainModel.getType() == MainModel.Type.SMALL_CONTROL ? makeMovementFlags : ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0);
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final boolean isItemViewSwipeEnabled() {
                                return false;
                            }

                            /* JADX WARN: Code restructure failed: missing block: B:46:0x0112, code lost:
                            
                                if ((r21 == 0.0f) == false) goto L64;
                             */
                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i2, boolean z2) {
                                super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i2, z2);
                                boolean z3 = BasicRune.CONTROLS_CARD_REORDER_DIM;
                                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                                View view = viewHolder.itemView;
                                if (z3) {
                                    int scaledTouchSlop = ViewConfiguration.get(recyclerView.getContext()).getScaledTouchSlop();
                                    int i3 = this.dragPos;
                                    int i4 = this.DEFAULT_POS;
                                    if (i3 == i4 && scaledTouchSlop < ((float) Math.hypot(f, f2))) {
                                        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                                        this.dragPos = bindingAdapterPosition;
                                        Object obj = mainControlAdapter.models.get(bindingAdapterPosition);
                                        if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) != null) {
                                            List list = mainControlAdapter.models;
                                            ArrayList arrayList = new ArrayList();
                                            for (Object obj2 : list) {
                                                if (obj2 instanceof MainControlModel) {
                                                    arrayList.add(obj2);
                                                }
                                            }
                                            ArrayList arrayList2 = new ArrayList();
                                            Iterator it = arrayList.iterator();
                                            while (it.hasNext()) {
                                                Object next = it.next();
                                                if (!Intrinsics.areEqual(((MainControlModel) next).structure, r3.structure)) {
                                                    arrayList2.add(next);
                                                }
                                            }
                                            Iterator it2 = arrayList2.iterator();
                                            while (it2.hasNext()) {
                                                ((MainControlModel) it2.next()).needToMakeDim = true;
                                            }
                                            mainControlAdapter.mObservable.notifyItemRangeChanged(0, mainControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                                        }
                                    } else if (f == 0.0f) {
                                        if (f2 == 0.0f) {
                                            if (this.dragPos != i4) {
                                                List list2 = mainControlAdapter.models;
                                                ArrayList arrayList3 = new ArrayList();
                                                for (Object obj3 : list2) {
                                                    if (obj3 instanceof MainControlModel) {
                                                        arrayList3.add(obj3);
                                                    }
                                                }
                                                Iterator it3 = arrayList3.iterator();
                                                while (it3.hasNext()) {
                                                    ((MainControlModel) it3.next()).needToMakeDim = false;
                                                }
                                                mainControlAdapter.mObservable.notifyItemRangeChanged(0, mainControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                                            }
                                            this.dragPos = i4;
                                        }
                                    }
                                    if (f == 0.0f) {
                                    }
                                    Drawable drawable = mainControlAdapter.context.getResources().getDrawable(R.drawable.control_reorder_card_guide_line, mainControlAdapter.context.getTheme());
                                    Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                                    rect.offset(view.getLeft(), view.getTop());
                                    drawable.setBounds(rect);
                                    drawable.draw(canvas);
                                }
                                if (this.needToCheckAppBar && z2 && !mainControlAdapter.appBarLayout.lifted) {
                                    Rect rect2 = new Rect();
                                    AppBarLayout appBarLayout2 = mainControlAdapter.appBarLayout;
                                    appBarLayout2.getWindowVisibleDisplayFrame(rect2);
                                    if (view.getBottom() + ((int) f2) > rect2.height() - appBarLayout2.getHeight()) {
                                        this.needToCheckAppBar = false;
                                        appBarLayout2.setExpanded(false, true, true);
                                    }
                                }
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                                mainControlAdapter.getClass();
                                if (bindingAdapterPosition < bindingAdapterPosition2) {
                                    int i2 = bindingAdapterPosition;
                                    while (i2 < bindingAdapterPosition2) {
                                        int i3 = i2 + 1;
                                        Collections.swap(mainControlAdapter.models, i2, i3);
                                        i2 = i3;
                                    }
                                } else {
                                    int i4 = bindingAdapterPosition2 + 1;
                                    if (i4 <= bindingAdapterPosition) {
                                        int i5 = bindingAdapterPosition;
                                        while (true) {
                                            int i6 = i5 - 1;
                                            Collections.swap(mainControlAdapter.models, i5, i6);
                                            if (i5 == i4) {
                                                break;
                                            }
                                            i5 = i6;
                                        }
                                    }
                                }
                                mainControlAdapter.notifyItemMoved(bindingAdapterPosition, bindingAdapterPosition2);
                                Object obj = mainControlAdapter.models.get(bindingAdapterPosition);
                                if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) != null) {
                                    CustomControlsUiControllerImpl customControlsUiControllerImpl = ((CustomControlsUiControllerImpl$controlsPositionChangedCallback$1) mainControlAdapter.positionChangedCallback).this$0;
                                    customControlsUiControllerImpl.isChanged = true;
                                    customControlsUiControllerImpl.verificationStructureInfos = customControlsUiControllerImpl.getStructureInfosByUI(customControlsUiControllerImpl.selectedItem.getComponentName());
                                }
                                return true;
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i2) {
                                View view;
                                super.onSelectedChanged(viewHolder, i2);
                                this.needToCheckAppBar = true;
                                if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                                    MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                                    if (i2 == 0) {
                                        if (this.startDrag) {
                                            mainControlAdapter.saLogger.sendEvent(SALogger.Event.MoveCard.INSTANCE);
                                        }
                                        this.startDrag = false;
                                    } else {
                                        if (i2 != 2) {
                                            return;
                                        }
                                        this.startDrag = true;
                                        if (!BasicRune.CONTROLS_AUI || viewHolder == null || (view = viewHolder.itemView) == null) {
                                            return;
                                        }
                                        ((AUIFacadeImpl) mainControlAdapter.auiFacade).audioManager.playSoundEffect(106);
                                        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                                    }
                                }
                            }

                            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
                            }
                        };
                    }
                }
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size);
                map.put(3, new SpanInfo(dimensionPixelSize2, 0, 2, null));
                map.put(4, new SpanInfo(0, 0, 3, null));
                map.put(5, new SpanInfo(0, 0, 3, null));
                this.spanManager = spanManager;
                this.uid = -1;
                this.models = EmptyList.INSTANCE;
                this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$spanSizeLookup$1
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public final int getSpanSize(int i2) {
                        MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                        if (i2 >= mainControlAdapter.getItemCount()) {
                            return 0;
                        }
                        SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) mainControlAdapter.spanManager.spanInfos).get(Integer.valueOf(mainControlAdapter.getItemViewType(i2)));
                        if (spanInfo != null) {
                            return spanInfo.span;
                        }
                        return 0;
                    }
                };
                this.itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$itemTouchHelperCallback$1
                    public final int DEFAULT_POS;
                    public final int MOVEMENT;
                    public int dragPos;
                    public boolean needToCheckAppBar;
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
                        MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                        MainModel mainModel = (MainModel) mainControlAdapter.models.get(bindingAdapterPosition);
                        if (mainModel.getType() == MainModel.Type.STRUCTURE) {
                            return false;
                        }
                        int indexOf = mainControlAdapter.models.indexOf(mainModel);
                        int i2 = indexOf;
                        while (i2 > 0 && ((MainModel) mainControlAdapter.models.get(i2)).getType() == MainModel.Type.CONTROL) {
                            i2--;
                        }
                        while (indexOf < mainControlAdapter.models.size() - 1 && ((MainModel) mainControlAdapter.models.get(indexOf)).getType() == MainModel.Type.CONTROL) {
                            indexOf++;
                        }
                        MainModel.Type type = ((MainModel) mainControlAdapter.models.get(i2)).getType();
                        MainModel.Type type2 = MainModel.Type.STRUCTURE;
                        if (type == type2) {
                            i2++;
                        }
                        if (((MainModel) mainControlAdapter.models.get(indexOf)).getType() == type2) {
                            indexOf--;
                        }
                        int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                        return i2 <= bindingAdapterPosition2 && bindingAdapterPosition2 <= indexOf;
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        super.clearView(recyclerView, viewHolder);
                        this.needToCheckAppBar = false;
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                        int makeMovementFlags = ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                        if (bindingAdapterPosition == -1) {
                            return makeMovementFlags;
                        }
                        MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                        MainModel mainModel = (MainModel) mainControlAdapter.models.get(bindingAdapterPosition);
                        if (mainModel.getType() != MainModel.Type.CONTROL && mainModel.getType() != MainModel.Type.SMALL_CONTROL) {
                            return makeMovementFlags;
                        }
                        if (BasicRune.CONTROLS_REQUEST_UNLOCK_WHEN_LONG_PRESSED_CARD) {
                            ControlsUtil controlsUtil2 = mainControlAdapter.controlsUtil;
                            if (controlsUtil2.isSecureLocked()) {
                                controlsUtil2.getClass();
                                Context context2 = mainControlAdapter.context;
                                if (Settings.Secure.getInt(context2.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
                                    Log.d("MainControlAdapter", "request DismissKeyguard");
                                    ((KeyguardManager) context2.getSystemService("keyguard")).requestDismissKeyguard((Activity) context2, null);
                                    return makeMovementFlags;
                                }
                            }
                        }
                        return mainModel.getType() == MainModel.Type.SMALL_CONTROL ? makeMovementFlags : ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0);
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public final boolean isItemViewSwipeEnabled() {
                        return false;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:46:0x0112, code lost:
                    
                        if ((r21 == 0.0f) == false) goto L64;
                     */
                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i2, boolean z2) {
                        super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i2, z2);
                        boolean z3 = BasicRune.CONTROLS_CARD_REORDER_DIM;
                        MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                        View view = viewHolder.itemView;
                        if (z3) {
                            int scaledTouchSlop = ViewConfiguration.get(recyclerView.getContext()).getScaledTouchSlop();
                            int i3 = this.dragPos;
                            int i4 = this.DEFAULT_POS;
                            if (i3 == i4 && scaledTouchSlop < ((float) Math.hypot(f, f2))) {
                                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                                this.dragPos = bindingAdapterPosition;
                                Object obj = mainControlAdapter.models.get(bindingAdapterPosition);
                                if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) != null) {
                                    List list = mainControlAdapter.models;
                                    ArrayList arrayList = new ArrayList();
                                    for (Object obj2 : list) {
                                        if (obj2 instanceof MainControlModel) {
                                            arrayList.add(obj2);
                                        }
                                    }
                                    ArrayList arrayList2 = new ArrayList();
                                    Iterator it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        Object next = it.next();
                                        if (!Intrinsics.areEqual(((MainControlModel) next).structure, r3.structure)) {
                                            arrayList2.add(next);
                                        }
                                    }
                                    Iterator it2 = arrayList2.iterator();
                                    while (it2.hasNext()) {
                                        ((MainControlModel) it2.next()).needToMakeDim = true;
                                    }
                                    mainControlAdapter.mObservable.notifyItemRangeChanged(0, mainControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                                }
                            } else if (f == 0.0f) {
                                if (f2 == 0.0f) {
                                    if (this.dragPos != i4) {
                                        List list2 = mainControlAdapter.models;
                                        ArrayList arrayList3 = new ArrayList();
                                        for (Object obj3 : list2) {
                                            if (obj3 instanceof MainControlModel) {
                                                arrayList3.add(obj3);
                                            }
                                        }
                                        Iterator it3 = arrayList3.iterator();
                                        while (it3.hasNext()) {
                                            ((MainControlModel) it3.next()).needToMakeDim = false;
                                        }
                                        mainControlAdapter.mObservable.notifyItemRangeChanged(0, mainControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                                    }
                                    this.dragPos = i4;
                                }
                            }
                            if (f == 0.0f) {
                            }
                            Drawable drawable = mainControlAdapter.context.getResources().getDrawable(R.drawable.control_reorder_card_guide_line, mainControlAdapter.context.getTheme());
                            Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                            rect.offset(view.getLeft(), view.getTop());
                            drawable.setBounds(rect);
                            drawable.draw(canvas);
                        }
                        if (this.needToCheckAppBar && z2 && !mainControlAdapter.appBarLayout.lifted) {
                            Rect rect2 = new Rect();
                            AppBarLayout appBarLayout2 = mainControlAdapter.appBarLayout;
                            appBarLayout2.getWindowVisibleDisplayFrame(rect2);
                            if (view.getBottom() + ((int) f2) > rect2.height() - appBarLayout2.getHeight()) {
                                this.needToCheckAppBar = false;
                                appBarLayout2.setExpanded(false, true, true);
                            }
                        }
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                        int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                        MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                        mainControlAdapter.getClass();
                        if (bindingAdapterPosition < bindingAdapterPosition2) {
                            int i2 = bindingAdapterPosition;
                            while (i2 < bindingAdapterPosition2) {
                                int i3 = i2 + 1;
                                Collections.swap(mainControlAdapter.models, i2, i3);
                                i2 = i3;
                            }
                        } else {
                            int i4 = bindingAdapterPosition2 + 1;
                            if (i4 <= bindingAdapterPosition) {
                                int i5 = bindingAdapterPosition;
                                while (true) {
                                    int i6 = i5 - 1;
                                    Collections.swap(mainControlAdapter.models, i5, i6);
                                    if (i5 == i4) {
                                        break;
                                    }
                                    i5 = i6;
                                }
                            }
                        }
                        mainControlAdapter.notifyItemMoved(bindingAdapterPosition, bindingAdapterPosition2);
                        Object obj = mainControlAdapter.models.get(bindingAdapterPosition);
                        if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) != null) {
                            CustomControlsUiControllerImpl customControlsUiControllerImpl = ((CustomControlsUiControllerImpl$controlsPositionChangedCallback$1) mainControlAdapter.positionChangedCallback).this$0;
                            customControlsUiControllerImpl.isChanged = true;
                            customControlsUiControllerImpl.verificationStructureInfos = customControlsUiControllerImpl.getStructureInfosByUI(customControlsUiControllerImpl.selectedItem.getComponentName());
                        }
                        return true;
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i2) {
                        View view;
                        super.onSelectedChanged(viewHolder, i2);
                        this.needToCheckAppBar = true;
                        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                            MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                            if (i2 == 0) {
                                if (this.startDrag) {
                                    mainControlAdapter.saLogger.sendEvent(SALogger.Event.MoveCard.INSTANCE);
                                }
                                this.startDrag = false;
                            } else {
                                if (i2 != 2) {
                                    return;
                                }
                                this.startDrag = true;
                                if (!BasicRune.CONTROLS_AUI || viewHolder == null || (view = viewHolder.itemView) == null) {
                                    return;
                                }
                                ((AUIFacadeImpl) mainControlAdapter.auiFacade).audioManager.playSoundEffect(106);
                                view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                            }
                        }
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
                    }
                };
            }
        }
        dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size);
        map.put(1, new SpanInfo(dimensionPixelSize, 0, 2, null));
        if (z) {
        }
        dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.control_custom_base_item_size);
        map.put(3, new SpanInfo(dimensionPixelSize2, 0, 2, null));
        map.put(4, new SpanInfo(0, 0, 3, null));
        map.put(5, new SpanInfo(0, 0, 3, null));
        this.spanManager = spanManager;
        this.uid = -1;
        this.models = EmptyList.INSTANCE;
        this.spanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$spanSizeLookup$1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i2) {
                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                if (i2 >= mainControlAdapter.getItemCount()) {
                    return 0;
                }
                SpanInfo spanInfo = (SpanInfo) ((LinkedHashMap) mainControlAdapter.spanManager.spanInfos).get(Integer.valueOf(mainControlAdapter.getItemViewType(i2)));
                if (spanInfo != null) {
                    return spanInfo.span;
                }
                return 0;
            }
        };
        this.itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$itemTouchHelperCallback$1
            public final int DEFAULT_POS;
            public final int MOVEMENT;
            public int dragPos;
            public boolean needToCheckAppBar;
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
                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                MainModel mainModel = (MainModel) mainControlAdapter.models.get(bindingAdapterPosition);
                if (mainModel.getType() == MainModel.Type.STRUCTURE) {
                    return false;
                }
                int indexOf = mainControlAdapter.models.indexOf(mainModel);
                int i2 = indexOf;
                while (i2 > 0 && ((MainModel) mainControlAdapter.models.get(i2)).getType() == MainModel.Type.CONTROL) {
                    i2--;
                }
                while (indexOf < mainControlAdapter.models.size() - 1 && ((MainModel) mainControlAdapter.models.get(indexOf)).getType() == MainModel.Type.CONTROL) {
                    indexOf++;
                }
                MainModel.Type type = ((MainModel) mainControlAdapter.models.get(i2)).getType();
                MainModel.Type type2 = MainModel.Type.STRUCTURE;
                if (type == type2) {
                    i2++;
                }
                if (((MainModel) mainControlAdapter.models.get(indexOf)).getType() == type2) {
                    indexOf--;
                }
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                return i2 <= bindingAdapterPosition2 && bindingAdapterPosition2 <= indexOf;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                this.needToCheckAppBar = false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int getMovementFlags(RecyclerView.ViewHolder viewHolder) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                int makeMovementFlags = ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                if (bindingAdapterPosition == -1) {
                    return makeMovementFlags;
                }
                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                MainModel mainModel = (MainModel) mainControlAdapter.models.get(bindingAdapterPosition);
                if (mainModel.getType() != MainModel.Type.CONTROL && mainModel.getType() != MainModel.Type.SMALL_CONTROL) {
                    return makeMovementFlags;
                }
                if (BasicRune.CONTROLS_REQUEST_UNLOCK_WHEN_LONG_PRESSED_CARD) {
                    ControlsUtil controlsUtil2 = mainControlAdapter.controlsUtil;
                    if (controlsUtil2.isSecureLocked()) {
                        controlsUtil2.getClass();
                        Context context2 = mainControlAdapter.context;
                        if (Settings.Secure.getInt(context2.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
                            Log.d("MainControlAdapter", "request DismissKeyguard");
                            ((KeyguardManager) context2.getSystemService("keyguard")).requestDismissKeyguard((Activity) context2, null);
                            return makeMovementFlags;
                        }
                    }
                }
                return mainModel.getType() == MainModel.Type.SMALL_CONTROL ? makeMovementFlags : ItemTouchHelper.Callback.makeMovementFlags(this.MOVEMENT, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean isItemViewSwipeEnabled() {
                return false;
            }

            /* JADX WARN: Code restructure failed: missing block: B:46:0x0112, code lost:
            
                if ((r21 == 0.0f) == false) goto L64;
             */
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i2, boolean z2) {
                super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i2, z2);
                boolean z3 = BasicRune.CONTROLS_CARD_REORDER_DIM;
                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                View view = viewHolder.itemView;
                if (z3) {
                    int scaledTouchSlop = ViewConfiguration.get(recyclerView.getContext()).getScaledTouchSlop();
                    int i3 = this.dragPos;
                    int i4 = this.DEFAULT_POS;
                    if (i3 == i4 && scaledTouchSlop < ((float) Math.hypot(f, f2))) {
                        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                        this.dragPos = bindingAdapterPosition;
                        Object obj = mainControlAdapter.models.get(bindingAdapterPosition);
                        if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) != null) {
                            List list = mainControlAdapter.models;
                            ArrayList arrayList = new ArrayList();
                            for (Object obj2 : list) {
                                if (obj2 instanceof MainControlModel) {
                                    arrayList.add(obj2);
                                }
                            }
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                Object next = it.next();
                                if (!Intrinsics.areEqual(((MainControlModel) next).structure, r3.structure)) {
                                    arrayList2.add(next);
                                }
                            }
                            Iterator it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                ((MainControlModel) it2.next()).needToMakeDim = true;
                            }
                            mainControlAdapter.mObservable.notifyItemRangeChanged(0, mainControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                        }
                    } else if (f == 0.0f) {
                        if (f2 == 0.0f) {
                            if (this.dragPos != i4) {
                                List list2 = mainControlAdapter.models;
                                ArrayList arrayList3 = new ArrayList();
                                for (Object obj3 : list2) {
                                    if (obj3 instanceof MainControlModel) {
                                        arrayList3.add(obj3);
                                    }
                                }
                                Iterator it3 = arrayList3.iterator();
                                while (it3.hasNext()) {
                                    ((MainControlModel) it3.next()).needToMakeDim = false;
                                }
                                mainControlAdapter.mObservable.notifyItemRangeChanged(0, mainControlAdapter.models.size(), Holder.UpdateReq.UPDATE_DIM_STATUS);
                            }
                            this.dragPos = i4;
                        }
                    }
                    if (f == 0.0f) {
                    }
                    Drawable drawable = mainControlAdapter.context.getResources().getDrawable(R.drawable.control_reorder_card_guide_line, mainControlAdapter.context.getTheme());
                    Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                    rect.offset(view.getLeft(), view.getTop());
                    drawable.setBounds(rect);
                    drawable.draw(canvas);
                }
                if (this.needToCheckAppBar && z2 && !mainControlAdapter.appBarLayout.lifted) {
                    Rect rect2 = new Rect();
                    AppBarLayout appBarLayout2 = mainControlAdapter.appBarLayout;
                    appBarLayout2.getWindowVisibleDisplayFrame(rect2);
                    if (view.getBottom() + ((int) f2) > rect2.height() - appBarLayout2.getHeight()) {
                        this.needToCheckAppBar = false;
                        appBarLayout2.setExpanded(false, true, true);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                int bindingAdapterPosition2 = viewHolder2.getBindingAdapterPosition();
                MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                mainControlAdapter.getClass();
                if (bindingAdapterPosition < bindingAdapterPosition2) {
                    int i2 = bindingAdapterPosition;
                    while (i2 < bindingAdapterPosition2) {
                        int i3 = i2 + 1;
                        Collections.swap(mainControlAdapter.models, i2, i3);
                        i2 = i3;
                    }
                } else {
                    int i4 = bindingAdapterPosition2 + 1;
                    if (i4 <= bindingAdapterPosition) {
                        int i5 = bindingAdapterPosition;
                        while (true) {
                            int i6 = i5 - 1;
                            Collections.swap(mainControlAdapter.models, i5, i6);
                            if (i5 == i4) {
                                break;
                            }
                            i5 = i6;
                        }
                    }
                }
                mainControlAdapter.notifyItemMoved(bindingAdapterPosition, bindingAdapterPosition2);
                Object obj = mainControlAdapter.models.get(bindingAdapterPosition);
                if ((obj instanceof MainControlModel ? (MainControlModel) obj : null) != null) {
                    CustomControlsUiControllerImpl customControlsUiControllerImpl = ((CustomControlsUiControllerImpl$controlsPositionChangedCallback$1) mainControlAdapter.positionChangedCallback).this$0;
                    customControlsUiControllerImpl.isChanged = true;
                    customControlsUiControllerImpl.verificationStructureInfos = customControlsUiControllerImpl.getStructureInfosByUI(customControlsUiControllerImpl.selectedItem.getComponentName());
                }
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i2) {
                View view;
                super.onSelectedChanged(viewHolder, i2);
                this.needToCheckAppBar = true;
                if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                    MainControlAdapter mainControlAdapter = MainControlAdapter.this;
                    if (i2 == 0) {
                        if (this.startDrag) {
                            mainControlAdapter.saLogger.sendEvent(SALogger.Event.MoveCard.INSTANCE);
                        }
                        this.startDrag = false;
                    } else {
                        if (i2 != 2) {
                            return;
                        }
                        this.startDrag = true;
                        if (!BasicRune.CONTROLS_AUI || viewHolder == null || (view = viewHolder.itemView) == null) {
                            return;
                        }
                        ((AUIFacadeImpl) mainControlAdapter.auiFacade).audioManager.playSoundEffect(106);
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
        int measuredWidth = recyclerView.getMeasuredWidth();
        SpanManager spanManager = this.spanManager;
        spanManager.updateSpanInfos(measuredWidth);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), spanManager.maxSpan);
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
        if (i2 == 5) {
            return 5;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        if (recyclerView.getMeasuredWidth() == 0) {
            recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.controls.management.adapter.MainControlAdapter$onAttachedToRecyclerView$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    RecyclerView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    MainControlAdapter mainControlAdapter = this;
                    RecyclerView recyclerView2 = RecyclerView.this;
                    Map map = MainControlAdapter.controlViewHolders;
                    mainControlAdapter.attachedToRecyclerView(recyclerView2);
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
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        ControlHolder controlHolder;
        LayoutInflater from = LayoutInflater.from(recyclerView.getContext());
        if (i == 0) {
            return new StructureHolder(from.inflate(R.layout.controls_custom_main_zone_header, (ViewGroup) recyclerView, false));
        }
        Map map = controlViewHolders;
        ControlsRuneWrapper controlsRuneWrapper = this.controlsRuneWrapper;
        ControlsUtil controlsUtil = this.controlsUtil;
        CustomControlActionCoordinator customControlActionCoordinator = this.customControlActionCoordinator;
        if (i == 1) {
            View inflate = from.inflate(R.layout.controls_custom_base_item, (ViewGroup) recyclerView, false);
            ControlViewHolder controlViewHolder = new ControlViewHolder((ViewGroup) inflate, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, this.uid, this.currentUserId);
            controlViewHolder.getCustomControlViewHolder().initialize(customControlActionCoordinator, controlsUtil, 0, controlsRuneWrapper);
            controlHolder = new ControlHolder(inflate, controlViewHolder, map);
        } else {
            if (i != 3) {
                if (i == 4) {
                    return new SpinnerLayoutHolder(from.inflate(R.layout.controls_spinner_layout, (ViewGroup) recyclerView, false), this.spinnerTouchCallback, this.spinnerItemSelectedChangedCallback, this.buttonClickCallback, this.badgeProvider);
                }
                if (i == 5) {
                    return new PanelHolder(from.inflate(R.layout.controls_panel_layout, (ViewGroup) recyclerView, false), this.panelUpdatedCallback);
                }
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Wrong viewType: ", i));
            }
            View inflate2 = from.inflate(R.layout.controls_small_layout_item, (ViewGroup) recyclerView, false);
            ViewStub viewStub = (ViewStub) inflate2.requireViewById(R.id.small_layout_viewstub);
            viewStub.setLayoutResource(R.layout.controls_status_info);
            viewStub.inflate();
            ControlViewHolder controlViewHolder2 = new ControlViewHolder((ViewGroup) inflate2, this.controlsController, this.uiExecutor, this.bgExecutor, this.controlActionCoordinator, this.controlsMetricsLogger, this.uid, this.currentUserId);
            controlViewHolder2.getCustomControlViewHolder().initialize(customControlActionCoordinator, controlsUtil, 1, controlsRuneWrapper);
            controlHolder = new ControlHolder(inflate2, controlViewHolder2, map);
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
                controlHolder.updateDimStatus(((MainModel) list.get(i)).needToMakeDim);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        boolean z;
        Holder holder = (Holder) viewHolder;
        if (list.isEmpty()) {
            onBindViewHolder(holder, i);
            return;
        }
        boolean z2 = true;
        if (!list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (it.next() == Holder.UpdateReq.UPDATE_DIM_STATUS) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (z && (holder instanceof ControlHolder)) {
            ((ControlHolder) holder).updateDimStatus(((MainModel) this.models.get(i)).needToMakeDim);
        }
        if (!list.isEmpty()) {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                if (it2.next() != Holder.UpdateReq.UPDATE_DIM_STATUS) {
                    break;
                }
            }
        }
        z2 = false;
        if (z2) {
            onBindViewHolder(holder, i);
        }
    }
}
