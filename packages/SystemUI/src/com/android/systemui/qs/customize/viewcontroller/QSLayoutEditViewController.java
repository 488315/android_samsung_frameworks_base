package com.android.systemui.qs.customize.viewcontroller;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.bar.repository.BarOrderRepository;
import com.android.systemui.qs.customize.CustomTileInfo;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.AnimatableTileGridLayout;
import com.android.systemui.qs.customize.view.BarRecyclerAdapter;
import com.android.systemui.qs.customize.view.CustomizerNoLabelTileView;
import com.android.systemui.qs.customize.view.FullChunkResizeableFrame;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ValueAnimatorUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSLayoutEditViewController extends ViewControllerBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList barHiddenStateList;
    public final ArrayList barItems;
    public ArrayList barList;
    public final BarOrderInteractor barOrderInteractor;
    public int collapsedBarRow;
    public final QSLayoutEditViewController$collapsedBarRowConsumer$1 collapsedBarRowConsumer;
    public final SecQSSettingEditResources editResources;
    public final View.OnClickListener fullTileCustomizerClickListener;
    public final ArrayList hiddenEditableBars;
    public int maxRow;
    public ArrayList originBars;
    public final RecyclerView recyclerView;
    public final QSLayoutEditViewController$simpleCallback$1 simpleCallback;
    public final QSLayoutEditViewController$tileEditClickListener$1 tileEditClickListener;
    public FullChunkResizeableFrame tileLayoutContainer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$collapsedBarRowConsumer$1] */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$tileEditClickListener$1] */
    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$simpleCallback$1] */
    public QSLayoutEditViewController(final Context context, BarOrderInteractor barOrderInteractor, ArrayList<BarItemImpl> arrayList, SecQSSettingEditResources secQSSettingEditResources, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        super(LayoutInflater.from(context).inflate(R.layout.qs_customize_layout_edit, (ViewGroup) null, false));
        this.barOrderInteractor = barOrderInteractor;
        this.barItems = arrayList;
        this.editResources = secQSSettingEditResources;
        this.fullTileCustomizerClickListener = onClickListener;
        this.recyclerView = (RecyclerView) this.mView.requireViewById(R.id.bar_list);
        this.simpleCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$simpleCallback$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(51, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, long j) {
                return i2 > 0 ? 20 : -20;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
                View view;
                super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, z);
                if ((f == 0.0f && f2 == 0.0f) || (view = viewHolder.itemView) == null) {
                    return;
                }
                Context context2 = context;
                Drawable drawable = context2.getResources().getDrawable(R.drawable.qs_customizer_shape_roundborder_shadow, context2.getTheme());
                Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                rect.offset(view.getLeft(), view.getTop());
                drawable.setBounds(rect);
                drawable.draw(canvas);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
                int absoluteAdapterPosition2 = viewHolder2.getAbsoluteAdapterPosition();
                QSLayoutEditViewController qSLayoutEditViewController = QSLayoutEditViewController.this;
                if (absoluteAdapterPosition < absoluteAdapterPosition2) {
                    int i = absoluteAdapterPosition;
                    while (i < absoluteAdapterPosition2) {
                        int i2 = i + 1;
                        Collections.swap(qSLayoutEditViewController.barList, i, i2);
                        i = i2;
                    }
                } else {
                    int i3 = absoluteAdapterPosition2 + 1;
                    if (i3 <= absoluteAdapterPosition) {
                        int i4 = absoluteAdapterPosition;
                        while (true) {
                            Collections.swap(qSLayoutEditViewController.barList, i4, i4 - 1);
                            if (i4 == i3) {
                                break;
                            }
                            i4--;
                        }
                    }
                }
                RecyclerView.Adapter adapter = recyclerView.mAdapter;
                if (adapter != null) {
                    adapter.notifyItemMoved(absoluteAdapterPosition, absoluteAdapterPosition2);
                }
                Log.d("QSLayoutEditViewController", qSLayoutEditViewController.barList.toString());
                viewHolder2.itemView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
                ValueAnimator valueAnimator;
                super.onSelectedChanged(viewHolder, i);
                BarRecyclerAdapter.BorderOutlineViewHolder borderOutlineViewHolder = viewHolder instanceof BarRecyclerAdapter.BorderOutlineViewHolder ? (BarRecyclerAdapter.BorderOutlineViewHolder) viewHolder : null;
                if (borderOutlineViewHolder != null) {
                    if ((i == 0 || i == 2) && (valueAnimator = borderOutlineViewHolder.releaseAnimator) != null) {
                        ValueAnimatorUtil.INSTANCE.startReleaseScaleAnim(valueAnimator, borderOutlineViewHolder.parentView, 1.0f, borderOutlineViewHolder.downAnimator);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
            }
        };
        this.originBars = new ArrayList();
        this.hiddenEditableBars = new ArrayList();
        this.barHiddenStateList = new ArrayList();
        this.barList = new ArrayList();
        this.collapsedBarRow = barOrderInteractor.repository.collapsedBarRow;
        this.collapsedBarRowConsumer = new IntConsumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$collapsedBarRowConsumer$1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                QSLayoutEditViewController.this.collapsedBarRow = i;
            }
        };
        this.tileEditClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$tileEditClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSLayoutEditViewController.this.fullTileCustomizerClickListener.onClick(view);
                SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_EDIT_FULL_TILES, SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            }
        };
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.1
            @Override // java.lang.Runnable
            public final void run() {
                QSLayoutEditViewController qSLayoutEditViewController = QSLayoutEditViewController.this;
                int i = QSLayoutEditViewController.$r8$clinit;
                int i2 = qSLayoutEditViewController.collapsedBarRow;
                BarOrderInteractor barOrderInteractor2 = qSLayoutEditViewController.barOrderInteractor;
                barOrderInteractor2.repository.setCollapsedBarRow(i2);
                qSLayoutEditViewController.addHiddenBarsToList();
                ArrayList arrayList2 = qSLayoutEditViewController.barList;
                barOrderInteractor2.getClass();
                List distinct = CollectionsKt___CollectionsKt.distinct(arrayList2);
                BarOrderRepository barOrderRepository = barOrderInteractor2.repository;
                if (Intrinsics.areEqual(barOrderRepository.barOrder, distinct)) {
                    QSPanelHost qSPanelHost = barOrderInteractor2.host;
                    if (qSPanelHost != null) {
                        TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) qSPanelHost.mBarController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
                        if (tileChunkLayoutBar != null) {
                            tileChunkLayoutBar.mCollapsedRow = barOrderRepository.collapsedBarRow;
                        }
                        ArrayList barItems = qSPanelHost.getBarItems();
                        if (barItems != null) {
                            Iterator it = barItems.iterator();
                            while (it.hasNext()) {
                                ((BarItemImpl) it.next()).updateHeightMargins();
                            }
                        }
                    }
                } else {
                    barOrderRepository.setBarOrder(distinct);
                    barOrderInteractor2.applyBarOrder();
                }
                qSLayoutEditViewController.close();
                Runnable runnable2 = qSLayoutEditViewController.doneCallback;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        };
        this.mView.requireViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$setOnClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                runnable.run();
            }
        });
        this.mView.requireViewById(R.id.left_button).setOnClickListener(onClickListener2);
        setBarList(new ArrayList(barOrderInteractor.toFilteredNonEditBars(barOrderInteractor.repository.barOrder)));
    }

    public final void addHiddenBarsToList() {
        int i = 0;
        for (Object obj : this.barHiddenStateList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            if (((Boolean) obj).booleanValue()) {
                String str = (String) this.originBars.get(i);
                if (i >= this.barList.size()) {
                    this.barList.add(str);
                } else if (!Intrinsics.areEqual(this.barList.get(i), str)) {
                    this.barList.add(i, str);
                }
            }
            i = i2;
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void resolveMessage(Integer num) {
        if (num != null && num.intValue() == 100) {
            updateTileLayout();
            return;
        }
        if (num != null && num.intValue() == 300) {
            addHiddenBarsToList();
            setBarList(this.barList);
            RecyclerView recyclerView = this.recyclerView;
            recyclerView.setAdapter(null);
            ArrayList arrayList = this.barList;
            ArrayList arrayList2 = this.barItems;
            FullChunkResizeableFrame fullChunkResizeableFrame = this.tileLayoutContainer;
            BarRecyclerAdapter barRecyclerAdapter = new BarRecyclerAdapter(arrayList, arrayList2, fullChunkResizeableFrame != null ? fullChunkResizeableFrame : null);
            barRecyclerAdapter.mObservable.registerObserver(new QSLayoutEditViewController$makeBarRecyclerAdapter$1(this));
            recyclerView.setAdapter(barRecyclerAdapter);
        }
    }

    public final void setBarList(ArrayList arrayList) {
        this.originBars.clear();
        this.barHiddenStateList.clear();
        this.hiddenEditableBars.clear();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(arrayList);
        this.originBars = arrayList2;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            this.barHiddenStateList.add(Boolean.FALSE);
        }
        for (BarItemImpl barItemImpl : this.barItems) {
            String simpleName = barItemImpl.getClass().getSimpleName();
            if (!barItemImpl.isNeedToEdit() && (barItemImpl.mClonedBarView == null || !barItemImpl.isAvailable() || !barItemImpl.mShowing)) {
                this.hiddenEditableBars.add(simpleName);
            }
        }
        Iterator it2 = this.hiddenEditableBars.iterator();
        while (true) {
            int i = 0;
            if (!it2.hasNext()) {
                Iterator it3 = this.barHiddenStateList.iterator();
                while (it3.hasNext()) {
                    Object next = it3.next();
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                    if (((Boolean) next).booleanValue()) {
                        arrayList.remove((String) this.originBars.get(i));
                    }
                    i = i2;
                }
                this.barList = arrayList;
                return;
            }
            String str = (String) it2.next();
            Iterator it4 = this.originBars.iterator();
            while (it4.hasNext()) {
                Object next2 = it4.next();
                int i3 = i + 1;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                if (Intrinsics.areEqual(str, (String) next2)) {
                    this.barHiddenStateList.set(i, Boolean.TRUE);
                }
                i = i3;
            }
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        TextView textView;
        if (this.isShown) {
            return;
        }
        int notificationSidePadding = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getNotificationSidePadding(getContext(), false);
        FullChunkResizeableFrame fullChunkResizeableFrame = new FullChunkResizeableFrame(getContext(), this.recyclerView, this.collapsedBarRowConsumer, null, 8, null);
        this.tileLayoutContainer = fullChunkResizeableFrame;
        fullChunkResizeableFrame.requireViewById(R.id.tile_edit_button).setOnClickListener(this.tileEditClickListener);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        FullChunkResizeableFrame fullChunkResizeableFrame2 = this.tileLayoutContainer;
        if (fullChunkResizeableFrame2 == null) {
            fullChunkResizeableFrame2 = null;
        }
        fullChunkResizeableFrame2.setLayoutParams(layoutParams);
        updateTileLayout();
        FullChunkResizeableFrame fullChunkResizeableFrame3 = this.tileLayoutContainer;
        if (fullChunkResizeableFrame3 == null) {
            fullChunkResizeableFrame3 = null;
        }
        this.tileLayoutContainer = fullChunkResizeableFrame3;
        RecyclerView recyclerView = this.recyclerView;
        recyclerView.mIsPenSelectionEnabled = false;
        recyclerView.setPadding(notificationSidePadding, 0, notificationSidePadding, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ArrayList arrayList = this.barList;
        ArrayList arrayList2 = this.barItems;
        FullChunkResizeableFrame fullChunkResizeableFrame4 = this.tileLayoutContainer;
        BarRecyclerAdapter barRecyclerAdapter = new BarRecyclerAdapter(arrayList, arrayList2, fullChunkResizeableFrame4 != null ? fullChunkResizeableFrame4 : null);
        barRecyclerAdapter.mObservable.registerObserver(new QSLayoutEditViewController$makeBarRecyclerAdapter$1(this));
        recyclerView.setAdapter(barRecyclerAdapter);
        new ItemTouchHelper(this.simpleCallback).attachToRecyclerView(recyclerView);
        View requireViewById = this.mView.requireViewById(R.id.qs_customize_top_summary_buttons);
        View findViewById = requireViewById.findViewById(R.id.left_button);
        if (findViewById != null && (textView = (TextView) findViewById.findViewById(R.id.button_text)) != null) {
            textView.setText(R.string.qs_customizer_settings);
        }
        TextView textView2 = (TextView) requireViewById.requireViewById(R.id.qs_edit_summary);
        textView2.setSelected(true);
        textView2.setText(R.string.qs_edit_layout_summary);
        super.show(runnable);
    }

    public final void updateTileLayout() {
        if (this.tileLayoutContainer == null) {
            return;
        }
        SecQSSettingEditResources secQSSettingEditResources = this.editResources;
        int panelColumns = secQSSettingEditResources.getPanelColumns();
        FullChunkResizeableFrame fullChunkResizeableFrame = this.tileLayoutContainer;
        if (fullChunkResizeableFrame == null) {
            fullChunkResizeableFrame = null;
        }
        GridLayout gridLayout = (GridLayout) fullChunkResizeableFrame.requireViewById(R.id.tileLayout);
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(panelColumns);
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secQSSettingEditResources.tileFullAdapter;
        if (secQSCustomizerTileAdapter != null) {
            ArrayList arrayList = secQSCustomizerTileAdapter.mActiveTiles;
            int size = (arrayList.size() / panelColumns) + (arrayList.size() % panelColumns != 0 ? 1 : 0);
            this.maxRow = size;
            int i = this.collapsedBarRow;
            if (i <= size) {
                size = i;
            }
            BarOrderInteractor barOrderInteractor = this.barOrderInteractor;
            if (i > size) {
                this.collapsedBarRow = size;
                barOrderInteractor.repository.setCollapsedBarRow(size);
            } else if (i == 0) {
                size = arrayList.size() == 0 ? 0 : this.maxRow < 2 ? 1 : 2;
                this.collapsedBarRow = size;
                barOrderInteractor.repository.setCollapsedBarRow(size);
            }
            FullChunkResizeableFrame fullChunkResizeableFrame2 = this.tileLayoutContainer;
            if (fullChunkResizeableFrame2 == null) {
                fullChunkResizeableFrame2 = null;
            }
            int i2 = this.maxRow;
            fullChunkResizeableFrame2.maximumFrameSize = i2 == 0 ? fullChunkResizeableFrame2.cellHeight : fullChunkResizeableFrame2.cellHeight * i2;
            ImageView imageView = fullChunkResizeableFrame2.handlerBar;
            if (imageView == null) {
                imageView = null;
            }
            imageView.setVisibility((i2 == 0 || i2 == 1) ? 4 : 0);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CustomTileInfo customTileInfo = (CustomTileInfo) it.next();
                FrameLayout frameLayout = new FrameLayout(getContext());
                Intrinsics.checkNotNull(customTileInfo);
                CustomizerNoLabelTileView customizerNoLabelTileView = new CustomizerNoLabelTileView(getContext(), (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class));
                customizerNoLabelTileView.onStateChanged(customTileInfo.state);
                customizerNoLabelTileView.setClickable(false);
                customizerNoLabelTileView.setFocusable(false);
                frameLayout.addView(customizerNoLabelTileView);
                gridLayout.addView(frameLayout);
                GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) frameLayout.getLayoutParams();
                layoutParams.bottomMargin = getContext().getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin);
                layoutParams.columnSpec = GridLayout.spec(Integer.MIN_VALUE, 1.0f);
                layoutParams.setGravity(17);
                frameLayout.setLayoutParams(layoutParams);
            }
            int size2 = panelColumns - arrayList.size();
            if (size2 < panelColumns) {
                for (int i3 = 0; i3 < size2; i3++) {
                    FrameLayout frameLayout2 = new FrameLayout(getContext());
                    gridLayout.addView(frameLayout2);
                    GridLayout.LayoutParams layoutParams2 = (GridLayout.LayoutParams) frameLayout2.getLayoutParams();
                    layoutParams2.height = getContext().getResources().getDimensionPixelSize(R.dimen.sec_style_qs_tile_icon_size);
                    layoutParams2.width = getContext().getResources().getDimensionPixelSize(R.dimen.sec_style_qs_tile_icon_size);
                    layoutParams2.bottomMargin = getContext().getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin);
                    layoutParams2.columnSpec = GridLayout.spec(Integer.MIN_VALUE, 1.0f);
                    layoutParams2.setGravity(17);
                    frameLayout2.setLayoutParams(layoutParams2);
                }
            }
            FullChunkResizeableFrame fullChunkResizeableFrame3 = this.tileLayoutContainer;
            if (fullChunkResizeableFrame3 == null) {
                fullChunkResizeableFrame3 = null;
            }
            int i4 = fullChunkResizeableFrame3.cellHeight * size;
            fullChunkResizeableFrame3.currentRow = size;
            AnimatableTileGridLayout animatableTileGridLayout = fullChunkResizeableFrame3.gridLayout;
            AnimatableTileGridLayout animatableTileGridLayout2 = animatableTileGridLayout == null ? null : animatableTileGridLayout;
            if (animatableTileGridLayout2.getChildCount() > 0) {
                int childCount = animatableTileGridLayout2.getChildCount();
                for (int i5 = 0; i5 < childCount && i5 < animatableTileGridLayout2.getColumnCount() * size; i5++) {
                    AnimatableTileGridLayout.setViewFraction(animatableTileGridLayout2.getChildAt(i5), 1.0f, animatableTileGridLayout2.SCALE_FACTOR, 1.0f);
                }
            }
            int i6 = fullChunkResizeableFrame3.cellHeight;
            if (i4 < i6) {
                i4 = i6;
            }
            fullChunkResizeableFrame3.updateLayoutHeight(i4);
        }
    }
}
