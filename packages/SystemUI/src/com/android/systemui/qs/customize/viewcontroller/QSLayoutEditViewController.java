package com.android.systemui.qs.customize.viewcontroller;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
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
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecTileChunkLayout;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.bar.repository.BarOrderRepository;
import com.android.systemui.qs.customize.CustomActionId;
import com.android.systemui.qs.customize.CustomActionManager;
import com.android.systemui.qs.customize.CustomTileInfo;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.AnimatableTileGridLayout;
import com.android.systemui.qs.customize.view.BarRecyclerAdapter;
import com.android.systemui.qs.customize.view.CustomizerNoLabelTileView;
import com.android.systemui.qs.customize.view.FullChunkResizeableFrame;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ValueAnimatorUtil;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes2.dex */
public final class QSLayoutEditViewController extends ViewControllerBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList barHiddenStateList;
    public final ArrayList barItems;
    public ArrayList barList;
    public final BarOrderInteractor barOrderInteractor;
    public int collapsedBarRow;
    public final QSLayoutEditViewController$collapsedBarRowConsumer$1 collapsedBarRowConsumer;
    public final CustomActionManager customActionManager;
    public int cutoutBottomMargin;
    public int cutoutTopMargin;
    public final SecQSSettingEditResources editResources;
    public final View.OnClickListener fullTileCustomizerClickListener;
    public final ArrayList hiddenEditableBars;
    public int lastTileChunkHeight;
    public int maxRow;
    public ArrayList originBars;
    public final RecyclerView recyclerView;
    public final SecQSPanelResourcePicker resourcePicker;
    public final View.OnClickListener settingClickListener;
    public final QSLayoutEditViewController$simpleCallback$1 simpleCallback;
    public final int sumOfItems;
    public final int tileChunkLayoutBarColumns;
    public final QSLayoutEditViewController$tileEditClickListener$1 tileEditClickListener;
    public FullChunkResizeableFrame tileLayoutContainer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class InfiniteInflateLayoutManager extends LinearLayoutManager {
        public final int MAX_INT;

        public InfiniteInflateLayoutManager(QSLayoutEditViewController qSLayoutEditViewController, Context context) {
            super(context);
            this.MAX_INT = 10000;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public final int getExtraLayoutSpace(RecyclerView.State state) {
            return this.MAX_INT;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$collapsedBarRowConsumer$1] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$tileEditClickListener$1] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$simpleCallback$1] */
    public QSLayoutEditViewController(final Context context, BarOrderInteractor barOrderInteractor, ColoredBGHelper coloredBGHelper, ArrayList<BarItemImpl> arrayList, SecQSSettingEditResources secQSSettingEditResources, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, int i, int i2) throws Resources.NotFoundException {
        SecTileChunkLayout secTileChunkLayout;
        View viewFindViewById;
        TextView textView;
        int dimensionPixelSize;
        super(LayoutInflater.from(context).inflate(R.layout.qs_customize_layout_edit, (ViewGroup) null, false));
        int i3 = 0;
        this.barOrderInteractor = barOrderInteractor;
        this.barItems = arrayList;
        this.editResources = secQSSettingEditResources;
        this.fullTileCustomizerClickListener = onClickListener;
        this.settingClickListener = onClickListener2;
        this.cutoutTopMargin = i;
        this.cutoutBottomMargin = i2;
        this.recyclerView = (RecyclerView) this.mView.requireViewById(R.id.bar_list);
        this.simpleCallback = new ItemTouchHelper.SimpleCallback() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$simpleCallback$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(51, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i4, int i5, long j) {
                return i5 > 0 ? 20 : -20;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i4, boolean z) {
                float width = recyclerView.getWidth() / 2;
                super.onChildDraw(canvas, recyclerView, viewHolder, RangesKt___RangesKt.coerceIn(f, -width, width), f2, i4, z);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i4, boolean z) throws Resources.NotFoundException {
                super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i4, z);
                if (f == 0.0f && f2 == 0.0f) {
                    return;
                }
                View view = viewHolder.itemView;
                Context context2 = context;
                Drawable drawable = context2.getResources().getDrawable(R.drawable.qs_customizer_shape_roundborder_shadow, context2.getTheme());
                Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                rect.offset(view.getLeft(), view.getTop());
                drawable.setBounds(rect);
                drawable.draw(canvas);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
                int absoluteAdapterPosition2 = viewHolder2.getAbsoluteAdapterPosition();
                QSLayoutEditViewController qSLayoutEditViewController = this.this$0;
                if (absoluteAdapterPosition < absoluteAdapterPosition2) {
                    int i4 = absoluteAdapterPosition;
                    while (i4 < absoluteAdapterPosition2) {
                        int i5 = i4 + 1;
                        Collections.swap(qSLayoutEditViewController.barList, i4, i5);
                        i4 = i5;
                    }
                } else {
                    int i6 = absoluteAdapterPosition2 + 1;
                    if (i6 <= absoluteAdapterPosition) {
                        int i7 = absoluteAdapterPosition;
                        while (true) {
                            Collections.swap(qSLayoutEditViewController.barList, i7, i7 - 1);
                            if (i7 == i6) {
                                break;
                            }
                            i7--;
                        }
                    }
                }
                int i8 = QSLayoutEditViewController.$r8$clinit;
                qSLayoutEditViewController.notifyItemMoved(absoluteAdapterPosition, absoluteAdapterPosition2);
                Log.d("QSLayoutEditViewController", qSLayoutEditViewController.barList.toString());
                viewHolder2.itemView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i4) {
                ValueAnimator valueAnimator;
                super.onSelectedChanged(viewHolder, i4);
                BarRecyclerAdapter.BorderOutlineViewHolder borderOutlineViewHolder = viewHolder instanceof BarRecyclerAdapter.BorderOutlineViewHolder ? (BarRecyclerAdapter.BorderOutlineViewHolder) viewHolder : null;
                if (borderOutlineViewHolder != null) {
                    if ((i4 == 0 || i4 == 2) && (valueAnimator = borderOutlineViewHolder.releaseAnimator) != null) {
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
            public final void accept(int i4) {
                this.this$0.collapsedBarRow = i4;
            }
        };
        this.tileEditClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$tileEditClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.this$0.fullTileCustomizerClickListener.onClick(view);
                SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_EDIT_FULL_QUICK_SETTINGS_BUTTONS, SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            }
        };
        this.customActionManager = new CustomActionManager();
        int size = arrayList.size();
        int i4 = 0;
        int i5 = 0;
        while (i5 < size) {
            BarItemImpl barItemImpl = arrayList.get(i5);
            i5++;
            BarItemImpl barItemImpl2 = barItemImpl;
            if (this.barOrderInteractor.repository.nonEditableBars.contains(barItemImpl2.getClass().getSimpleName())) {
                dimensionPixelSize = 0;
            } else if (barItemImpl2 instanceof TileChunkLayoutBar) {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qs_customize_sumOf_margin_and_border_size);
            } else {
                dimensionPixelSize = (barItemImpl2.mBarRootView.getHeight() != 0 ? context.getResources().getDimensionPixelSize(R.dimen.qs_customize_sumOf_margin_and_border_size) : 0) + barItemImpl2.mBarRootView.getHeight();
            }
            i4 += dimensionPixelSize;
        }
        this.sumOfItems = i4;
        if (isLargeScreen$8()) {
            LinearLayout linearLayout = (LinearLayout) this.mView.findViewById(R.id.layout_edit_view);
            ViewGroup.LayoutParams layoutParams = linearLayout != null ? linearLayout.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.height = -2;
                linearLayout.setLayoutParams(layoutParams);
            }
        }
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.2
            @Override // java.lang.Runnable
            public final void run() {
                QSLayoutEditViewController qSLayoutEditViewController = QSLayoutEditViewController.this;
                int i6 = QSLayoutEditViewController.$r8$clinit;
                int i7 = qSLayoutEditViewController.collapsedBarRow;
                BarOrderInteractor barOrderInteractor2 = qSLayoutEditViewController.barOrderInteractor;
                barOrderInteractor2.repository.setCollapsedBarRow(i7);
                barOrderInteractor2.sendCollapsedRowStatusLog();
                qSLayoutEditViewController.addHiddenBarsToList();
                ArrayList arrayList2 = qSLayoutEditViewController.barList;
                barOrderInteractor2.getClass();
                List listDistinct = CollectionsKt___CollectionsKt.distinct(arrayList2);
                BarOrderRepository barOrderRepository = barOrderInteractor2.repository;
                if (Intrinsics.areEqual(barOrderRepository.barOrder, listDistinct)) {
                    QSPanelHost qSPanelHost = barOrderInteractor2.host;
                    if (qSPanelHost != null) {
                        TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) qSPanelHost.mBarController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
                        if (tileChunkLayoutBar != null) {
                            tileChunkLayoutBar.mCollapsedRowSettingValue = barOrderRepository.collapsedBarRow;
                        }
                        ArrayList barItems = qSPanelHost.getBarItems();
                        if (barItems != null) {
                            int size2 = barItems.size();
                            int i8 = 0;
                            while (i8 < size2) {
                                Object obj = barItems.get(i8);
                                i8++;
                                ((BarItemImpl) obj).updateHeightMargins();
                            }
                        }
                    }
                } else {
                    barOrderRepository.setBarOrder(listDistinct);
                    barOrderInteractor2.sendOrderStatusLog();
                    barOrderInteractor2.applyBarOrder();
                }
                qSLayoutEditViewController.close();
                QSCMainViewController$showView$1$1 qSCMainViewController$showView$1$1 = qSLayoutEditViewController.doneCallback;
                if (qSCMainViewController$showView$1$1 != null) {
                    qSCMainViewController$showView$1$1.run();
                }
            }
        };
        this.mView.requireViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$setOnClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                runnable.run();
            }
        });
        this.mView.requireViewById(R.id.left_button).setOnClickListener(this.settingClickListener);
        View viewFindViewById2 = this.mView.findViewById(R.id.left_button);
        if (viewFindViewById2 != null) {
            View viewFindViewById3 = viewFindViewById2.findViewById(R.id.button_text);
            if (viewFindViewById3 != null) {
                viewFindViewById3.setVisibility(8);
            }
            View viewFindViewById4 = viewFindViewById2.findViewById(R.id.button_icon_text_container);
            if (viewFindViewById4 != null) {
                viewFindViewById4.setVisibility(0);
            }
        }
        setBarList(new ArrayList(this.barOrderInteractor.loadBarOrderList()));
        this.customActionManager.setCustomAction(CustomActionId.MOVE_ITEM_UP, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSLayoutEditViewController.access$moveItem(QSLayoutEditViewController.this, (View) obj, new QSLayoutEditViewController$4$$ExternalSyntheticLambda0(0), new QSLayoutEditViewController$4$$ExternalSyntheticLambda0(1));
            }
        });
        this.customActionManager.setCustomAction(CustomActionId.MOVE_ITEM_TO_TOP, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSLayoutEditViewController.access$moveItem(QSLayoutEditViewController.this, (View) obj, new QSLayoutEditViewController$4$$ExternalSyntheticLambda0(2), new QSLayoutEditViewController$4$$ExternalSyntheticLambda0(3));
            }
        });
        this.customActionManager.setCustomAction(CustomActionId.MOVE_ITEM_DOWN, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSLayoutEditViewController qSLayoutEditViewController = QSLayoutEditViewController.this;
                QSLayoutEditViewController.access$moveItem(qSLayoutEditViewController, (View) obj, new QSLayoutEditViewController$6$$ExternalSyntheticLambda0(qSLayoutEditViewController, 0), new QSLayoutEditViewController$4$$ExternalSyntheticLambda0(4));
            }
        });
        this.customActionManager.setCustomAction(CustomActionId.MOVE_ITEM_TO_BOTTOM, new Consumer() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSLayoutEditViewController qSLayoutEditViewController = QSLayoutEditViewController.this;
                QSLayoutEditViewController.access$moveItem(qSLayoutEditViewController, (View) obj, new QSLayoutEditViewController$6$$ExternalSyntheticLambda0(qSLayoutEditViewController, 1), new QSLayoutEditViewController$6$$ExternalSyntheticLambda0(qSLayoutEditViewController, 2));
            }
        });
        this.resourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && (viewFindViewById = this.mView.findViewById(R.id.right_button)) != null && (textView = (TextView) viewFindViewById.findViewById(R.id.button_text)) != null) {
            textView.post(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController.8
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    View viewFindViewById5 = ((ViewController) QSLayoutEditViewController.this).mView.findViewById(R.id.right_button);
                    int width = viewFindViewById5 != null ? viewFindViewById5.getWidth() : 0;
                    int dimensionPixelSize2 = QSLayoutEditViewController.this.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_buttons_margin);
                    int popOverMargin = QSLayoutEditViewController.this.resourcePicker.getPopOverMargin(context) * 2;
                    TextView leftButtonTextView = QSLayoutEditViewController.this.getLeftButtonTextView();
                    if (leftButtonTextView != null) {
                        leftButtonTextView.setMaxWidth(QSLayoutEditViewController.this.resourcePicker.getPanelWidth(context) - ((width + dimensionPixelSize2) + popOverMargin));
                    }
                    TextView leftButtonTextView2 = QSLayoutEditViewController.this.getLeftButtonTextView();
                    if (leftButtonTextView2 != null) {
                        leftButtonTextView2.requestLayout();
                    }
                }
            });
        }
        ArrayList arrayList2 = this.barItems;
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        int i6 = 0;
        while (i6 < size2) {
            Object obj = arrayList2.get(i6);
            i6++;
            if (obj instanceof TileChunkLayoutBar) {
                arrayList3.add(obj);
            }
        }
        TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList3);
        if (tileChunkLayoutBar != null && (secTileChunkLayout = tileChunkLayoutBar.mTileLayout) != null) {
            i3 = secTileChunkLayout.columns;
        }
        this.tileChunkLayoutBarColumns = i3;
    }

    public static final void access$moveItem(QSLayoutEditViewController qSLayoutEditViewController, View view, Function1 function1, Function1 function12) {
        if (view == null) {
            qSLayoutEditViewController.getClass();
            return;
        }
        int absoluteAdapterPosition = qSLayoutEditViewController.recyclerView.getChildViewHolder(view).getAbsoluteAdapterPosition();
        if (((Boolean) function1.mo781invoke(Integer.valueOf(absoluteAdapterPosition))).booleanValue()) {
            int iIntValue = ((Number) function12.mo781invoke(Integer.valueOf(absoluteAdapterPosition))).intValue();
            if (absoluteAdapterPosition < iIntValue) {
                int i = absoluteAdapterPosition;
                while (i < iIntValue) {
                    int i2 = i + 1;
                    Collections.swap(qSLayoutEditViewController.barList, i, i2);
                    i = i2;
                }
            } else {
                int i3 = iIntValue + 1;
                if (i3 <= absoluteAdapterPosition) {
                    int i4 = absoluteAdapterPosition;
                    while (true) {
                        Collections.swap(qSLayoutEditViewController.barList, i4, i4 - 1);
                        if (i4 == i3) {
                            break;
                        } else {
                            i4--;
                        }
                    }
                }
            }
            qSLayoutEditViewController.notifyItemMoved(absoluteAdapterPosition, iIntValue);
        }
    }

    public static boolean isLargeScreen$8() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public final void addHiddenBarsToList() {
        ArrayList arrayList = this.barHiddenStateList;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            int i3 = i + 1;
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
            i = i3;
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void close() {
        super.close();
        TextView leftButtonTextView = getLeftButtonTextView();
        int i = 0;
        if (leftButtonTextView != null) {
            leftButtonTextView.setSelected(false);
        }
        FullChunkResizeableFrame fullChunkResizeableFrame = this.tileLayoutContainer;
        if (fullChunkResizeableFrame == null) {
            fullChunkResizeableFrame = null;
        }
        fullChunkResizeableFrame.getClass();
        ColoredBGHelper coloredBGHelper = (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
        LinearLayout linearLayout = fullChunkResizeableFrame.dummyContainer;
        coloredBGHelper.removeFromBarBackground(linearLayout != null ? linearLayout : null);
        ArrayList arrayList = this.barItems;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BarItemImpl) obj).removeCloneTileBG();
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void configChanged() throws Resources.NotFoundException {
        updateRecyclerViewHeight(null);
    }

    public final TextView getLeftButtonTextView() {
        View viewFindViewById = this.mView.findViewById(R.id.left_button);
        if (viewFindViewById != null) {
            return (TextView) viewFindViewById.findViewById(R.id.button_text_with_icon);
        }
        return null;
    }

    public final void notifyItemMoved(int i, int i2) {
        RecyclerView recyclerView = this.recyclerView;
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        if (adapter != null) {
            adapter.notifyItemMoved(i, i2);
        }
        ArrayList arrayList = this.barList;
        int size = arrayList.size();
        int i3 = 0;
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            int i5 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(i3);
            if (viewHolderFindViewHolderForAdapterPosition != null) {
                ((BarRecyclerAdapter) recyclerView.mAdapter).addAccessibilityInfo((BarRecyclerAdapter.BorderOutlineViewHolder) viewHolderFindViewHolderForAdapterPosition, i3);
            }
            i3 = i5;
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void resolveMessage(Integer num) {
        if (num != null && num.intValue() == 100) {
            updateTileLayout(false);
            return;
        }
        if (num != null && num.intValue() == 300) {
            updateTileLayout(true);
            addHiddenBarsToList();
            setBarList(this.barList);
            RecyclerView recyclerView = this.recyclerView;
            recyclerView.setAdapter(null);
            ArrayList arrayList = this.barList;
            ArrayList arrayList2 = this.barItems;
            FullChunkResizeableFrame fullChunkResizeableFrame = this.tileLayoutContainer;
            BarRecyclerAdapter barRecyclerAdapter = new BarRecyclerAdapter(arrayList, arrayList2, fullChunkResizeableFrame != null ? fullChunkResizeableFrame : null, this.customActionManager);
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
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            this.barHiddenStateList.add(Boolean.FALSE);
        }
        ArrayList arrayList3 = this.barItems;
        int size2 = arrayList3.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList3.get(i3);
            i3++;
            BarItemImpl barItemImpl = (BarItemImpl) obj2;
            String simpleName = barItemImpl.getClass().getSimpleName();
            if (!barItemImpl.isNeedToEdit() && (barItemImpl.mClonedBarView == null || !barItemImpl.isAvailable() || !barItemImpl.mShowing)) {
                this.hiddenEditableBars.add(simpleName);
            }
        }
        ArrayList arrayList4 = this.hiddenEditableBars;
        int size3 = arrayList4.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj3 = arrayList4.get(i4);
            i4++;
            String str = (String) obj3;
            ArrayList arrayList5 = this.originBars;
            int size4 = arrayList5.size();
            int i5 = 0;
            int i6 = 0;
            while (i6 < size4) {
                Object obj4 = arrayList5.get(i6);
                i6++;
                int i7 = i5 + 1;
                if (i5 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                if (Intrinsics.areEqual(str, (String) obj4)) {
                    this.barHiddenStateList.set(i5, Boolean.TRUE);
                }
                i5 = i7;
            }
        }
        ArrayList arrayList6 = this.barHiddenStateList;
        int size5 = arrayList6.size();
        int i8 = 0;
        while (i8 < size5) {
            Object obj5 = arrayList6.get(i8);
            i8++;
            int i9 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            if (((Boolean) obj5).booleanValue()) {
                arrayList.remove((String) this.originBars.get(i));
            }
            i = i9;
        }
        this.barList = arrayList;
    }

    public final void setUpView() {
        boolean zIsTablet = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        int popOverMargin = zIsTablet ? secQSPanelResourcePicker.getPopOverMargin(getContext()) : secQSPanelResourcePicker.getPanelSidePadding(getContext());
        FullChunkResizeableFrame fullChunkResizeableFrame = new FullChunkResizeableFrame(getContext(), this.recyclerView, this.collapsedBarRowConsumer, new QSLayoutEditViewController$createDummyTileLayout$1(this), null, 16, null);
        this.tileLayoutContainer = fullChunkResizeableFrame;
        fullChunkResizeableFrame.requireViewById(R.id.tile_edit_button).setOnClickListener(this.tileEditClickListener);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        FullChunkResizeableFrame fullChunkResizeableFrame2 = this.tileLayoutContainer;
        if (fullChunkResizeableFrame2 == null) {
            fullChunkResizeableFrame2 = null;
        }
        fullChunkResizeableFrame2.setLayoutParams(layoutParams);
        updateTileLayout(false);
        FullChunkResizeableFrame fullChunkResizeableFrame3 = this.tileLayoutContainer;
        if (fullChunkResizeableFrame3 == null) {
            fullChunkResizeableFrame3 = null;
        }
        this.tileLayoutContainer = fullChunkResizeableFrame3;
        RecyclerView recyclerView = this.recyclerView;
        recyclerView.mIsPenSelectionEnabled = false;
        recyclerView.setPadding(popOverMargin, 0, popOverMargin, 0);
        View viewFindViewById = this.mView.findViewById(R.id.qs_customize_top_summary_buttons);
        if (viewFindViewById != null) {
            viewFindViewById.setPadding(popOverMargin, viewFindViewById.getPaddingTop(), popOverMargin, viewFindViewById.getPaddingBottom());
            int iM = isLargeScreen$8() ? QSLayoutEditViewController$$ExternalSyntheticOutline0.m(viewFindViewById, R.dimen.qs_pop_over_layout_edit_buttons_top_margin) : QSLayoutEditViewController$$ExternalSyntheticOutline0.m(viewFindViewById, R.dimen.qs_edit_buttons_top_margin);
            FrameLayout frameLayout = (FrameLayout) viewFindViewById.findViewById(R.id.qs_customize_top_summary_buttons_button_area);
            ((LinearLayout.LayoutParams) (frameLayout != null ? frameLayout.getLayoutParams() : null)).topMargin = iM;
        }
        recyclerView.setLayoutManager(new InfiniteInflateLayoutManager(this, recyclerView.getContext()));
        ArrayList arrayList = this.barList;
        ArrayList arrayList2 = this.barItems;
        FullChunkResizeableFrame fullChunkResizeableFrame4 = this.tileLayoutContainer;
        BarRecyclerAdapter barRecyclerAdapter = new BarRecyclerAdapter(arrayList, arrayList2, fullChunkResizeableFrame4 != null ? fullChunkResizeableFrame4 : null, this.customActionManager);
        barRecyclerAdapter.mObservable.registerObserver(new QSLayoutEditViewController$makeBarRecyclerAdapter$1(this));
        recyclerView.setAdapter(barRecyclerAdapter);
        new ItemTouchHelper(this.simpleCallback).attachToRecyclerView(recyclerView);
        TextView textView = (TextView) this.mView.requireViewById(R.id.qs_customize_top_summary_buttons).requireViewById(R.id.qs_edit_summary);
        textView.setSelected(true);
        textView.setText(R.string.qs_edit_layout_summary);
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        if (this.isShown) {
            return;
        }
        setUpView();
        TextView leftButtonTextView = getLeftButtonTextView();
        if (leftButtonTextView != null) {
            leftButtonTextView.setSelected(true);
        }
        super.show(runnable);
    }

    public final void updateRecyclerViewHeight(Integer num) throws Resources.NotFoundException {
        boolean z;
        boolean z2;
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER && isLargeScreen$8()) {
            int height = 0;
            if (num != null) {
                int iIntValue = num.intValue();
                z = iIntValue > this.lastTileChunkHeight;
                this.lastTileChunkHeight = iIntValue;
            } else {
                z = false;
            }
            int displayHeight = DeviceState.getDisplayHeight(getContext()) - ((this.cutoutBottomMargin + getContext().getResources().getDimensionPixelSize(R.dimen.qs_pop_over_layout_edit_items_top_margin)) + this.cutoutTopMargin);
            int i = this.sumOfItems + this.lastTileChunkHeight;
            RecyclerView recyclerView = this.recyclerView;
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            if (i > displayHeight) {
                i = displayHeight;
            }
            layoutParams.height = i;
            recyclerView.setLayoutParams(layoutParams);
            RecyclerView.Adapter adapter = recyclerView.mAdapter;
            if ((adapter instanceof BarRecyclerAdapter) && z && layoutParams.height == displayHeight && (recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
                ArrayList arrayList = this.barList;
                int size = arrayList.size();
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        i2 = -1;
                        break;
                    }
                    Object obj = arrayList.get(i3);
                    i3++;
                    String str = (String) obj;
                    if (Intrinsics.areEqual(str, "TileChunkLayoutBar") || Intrinsics.areEqual(str, "ExpandableChunkTileLayoutBar")) {
                        break;
                    } else {
                        i2++;
                    }
                }
                int iCoerceIn = RangesKt___RangesKt.coerceIn(i2, 0, ((BarRecyclerAdapter) adapter).barItems.size() - 1);
                RecyclerView.ViewHolder viewHolderFindViewHolderForPosition = recyclerView.findViewHolderForPosition(iCoerceIn, false);
                if (viewHolderFindViewHolderForPosition != null) {
                    z2 = viewHolderFindViewHolderForPosition.itemView.getHeight() + viewHolderFindViewHolderForPosition.itemView.getTop() > displayHeight;
                    height = displayHeight - viewHolderFindViewHolderForPosition.itemView.getHeight();
                } else {
                    z2 = false;
                }
                if (z2) {
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(iCoerceIn, height);
                }
            }
        }
    }

    public final void updateTileLayout(boolean z) {
        ArrayList arrayList;
        if (this.tileLayoutContainer == null) {
            return;
        }
        SecQSSettingEditResources secQSSettingEditResources = this.editResources;
        int panelColumns = this.tileChunkLayoutBarColumns;
        if (panelColumns == 0) {
            panelColumns = secQSSettingEditResources.getPanelColumns();
        }
        FullChunkResizeableFrame fullChunkResizeableFrame = this.tileLayoutContainer;
        if (fullChunkResizeableFrame == null) {
            fullChunkResizeableFrame = null;
        }
        GridLayout gridLayout = (GridLayout) fullChunkResizeableFrame.requireViewById(R.id.tileLayout);
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(panelColumns);
        if (z) {
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secQSSettingEditResources.tileFullAdapter;
            if (secQSCustomizerTileAdapter != null) {
                secQSCustomizerTileAdapter.updateTiles();
            }
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSSettingEditResources.tileTopAdapter;
            if (secQSCustomizerTileAdapter2 != null) {
                secQSCustomizerTileAdapter2.updateTiles();
            }
        }
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter3 = secQSSettingEditResources.tileFullAdapter;
        if (secQSCustomizerTileAdapter3 == null || (arrayList = secQSCustomizerTileAdapter3.mActiveTiles) == null) {
            return;
        }
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
            barOrderInteractor.sendCollapsedRowStatusLog();
        } else if (i == 0) {
            size = arrayList.size() == 0 ? 0 : this.maxRow < 2 ? 1 : 2;
            this.collapsedBarRow = size;
            barOrderInteractor.repository.setCollapsedBarRow(size);
            barOrderInteractor.sendCollapsedRowStatusLog();
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
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj = arrayList.get(i3);
            i3++;
            CustomTileInfo customTileInfo = (CustomTileInfo) obj;
            FrameLayout frameLayout = new FrameLayout(getContext());
            customTileInfo.getClass();
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
        int size3 = panelColumns - arrayList.size();
        if (size3 < panelColumns) {
            for (int i4 = 0; i4 < size3; i4++) {
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
        int i5 = fullChunkResizeableFrame3.cellHeight * size;
        fullChunkResizeableFrame3.currentRow = size;
        AnimatableTileGridLayout animatableTileGridLayout = fullChunkResizeableFrame3.gridLayout;
        AnimatableTileGridLayout animatableTileGridLayout2 = animatableTileGridLayout == null ? null : animatableTileGridLayout;
        if (animatableTileGridLayout2.getChildCount() > 0) {
            int childCount = animatableTileGridLayout2.getChildCount();
            for (int i6 = 0; i6 < childCount && i6 < animatableTileGridLayout2.getColumnCount() * size; i6++) {
                AnimatableTileGridLayout.setViewFraction(animatableTileGridLayout2.getChildAt(i6), 1.0f, animatableTileGridLayout2.SCALE_FACTOR, 1.0f);
            }
        }
        int i7 = fullChunkResizeableFrame3.cellHeight;
        if (i5 < i7) {
            i5 = i7;
        }
        fullChunkResizeableFrame3.updateLayoutHeight(i5);
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void windowInsetChanged(int i, int i2) throws Resources.NotFoundException {
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER && isLargeScreen$8()) {
            if (this.cutoutTopMargin == i && this.cutoutBottomMargin == i2) {
                return;
            }
            this.cutoutTopMargin = i;
            this.cutoutBottomMargin = i2;
            updateRecyclerViewHeight(null);
        }
    }
}
