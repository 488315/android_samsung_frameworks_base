package com.android.systemui.qs.customize.view;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.customize.CustomActionId;
import com.android.systemui.qs.customize.CustomActionListDelegate;
import com.android.systemui.qs.customize.CustomActionManager;
import com.android.systemui.qs.customize.view.BarRecyclerAdapter;
import com.android.systemui.util.ValueAnimatorUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BarRecyclerAdapter extends RecyclerView.Adapter {
    public static final Interpolator DOWN_INTERPOLATOR;
    public final ArrayList barItems;
    public final CustomActionManager customActionManager;
    public final FullChunkResizeableFrame tileLayoutContainer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BorderOutlineViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout barView;
        public final ValueAnimator downAnimator;
        public final ViewGroup parentView;
        public final ValueAnimator releaseAnimator;

        public BorderOutlineViewHolder(BarRecyclerAdapter barRecyclerAdapter, View view) {
            super(view);
            view.getContext();
            this.barView = (FrameLayout) view.requireViewById(R.id.dummy_bar_container);
            ViewGroup viewGroup = (ViewGroup) view.requireViewById(R.id.dummy_bar_parent);
            this.parentView = viewGroup;
            ValueAnimatorUtil valueAnimatorUtil = ValueAnimatorUtil.INSTANCE;
            this.downAnimator = valueAnimatorUtil.createScaleAnimator(viewGroup, 1.0f, 0.98f, BarRecyclerAdapter.DOWN_INTERPOLATOR, ViewConfiguration.getLongPressTimeout());
            this.releaseAnimator = valueAnimatorUtil.createScaleAnimator(viewGroup, 0.98f, 1.0f, valueAnimatorUtil.getRELEASE_INTERPOLATOR(), 450L);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DOWN_INTERPOLATOR = new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f);
    }

    public /* synthetic */ BarRecyclerAdapter(ArrayList arrayList, ArrayList arrayList2, FullChunkResizeableFrame fullChunkResizeableFrame, CustomActionManager customActionManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayList, arrayList2, fullChunkResizeableFrame, (i & 8) != 0 ? null : customActionManager);
    }

    public final void addAccessibilityInfo(BorderOutlineViewHolder borderOutlineViewHolder, int i) {
        int size = this.barItems.size();
        ViewGroup viewGroup = borderOutlineViewHolder.parentView;
        borderOutlineViewHolder.barView.setContentDescription(viewGroup.getContext().getString(R.string.quick_settings_row_of, Integer.valueOf(i + 1), Integer.valueOf(size)) + ", " + viewGroup.getContext().getString(R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder));
        List asList = i == 0 ? Arrays.asList(CustomActionId.MOVE_ITEM_DOWN, CustomActionId.MOVE_ITEM_TO_BOTTOM) : i == size + (-1) ? Arrays.asList(CustomActionId.MOVE_ITEM_UP, CustomActionId.MOVE_ITEM_TO_TOP) : Arrays.asList(CustomActionId.MOVE_ITEM_UP, CustomActionId.MOVE_ITEM_TO_TOP, CustomActionId.MOVE_ITEM_DOWN, CustomActionId.MOVE_ITEM_TO_BOTTOM);
        CustomActionManager customActionManager = this.customActionManager;
        if (customActionManager != null) {
            CustomActionListDelegate customActionListDelegate = new CustomActionListDelegate(asList);
            customActionListDelegate.customActionManager = customActionManager;
            viewGroup.setAccessibilityDelegate(customActionListDelegate);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.barItems.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Pair pair;
        final BorderOutlineViewHolder borderOutlineViewHolder = (BorderOutlineViewHolder) viewHolder;
        FrameLayout frameLayout = borderOutlineViewHolder.barView;
        BarItemImpl barItemImpl = (BarItemImpl) this.barItems.get(i);
        barItemImpl.updateClonedBar();
        if (frameLayout.getChildCount() > 0) {
            return;
        }
        if (barItemImpl instanceof TileChunkLayoutBar) {
            FrameLayout frameLayout2 = new FrameLayout(frameLayout.getContext());
            frameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
            frameLayout2.setBackgroundColor(0);
            FullChunkResizeableFrame fullChunkResizeableFrame = this.tileLayoutContainer;
            fullChunkResizeableFrame.getClass();
            ViewGroup viewGroup = borderOutlineViewHolder.parentView;
            fullChunkResizeableFrame.parentView = viewGroup;
            viewGroup.addView(frameLayout2);
            borderOutlineViewHolder.parentView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.view.BarRecyclerAdapter$setOnTouchListener$1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    ValueAnimator valueAnimator;
                    BarRecyclerAdapter barRecyclerAdapter = BarRecyclerAdapter.this;
                    motionEvent.getClass();
                    BarRecyclerAdapter.BorderOutlineViewHolder borderOutlineViewHolder2 = borderOutlineViewHolder;
                    Interpolator interpolator = BarRecyclerAdapter.DOWN_INTERPOLATOR;
                    barRecyclerAdapter.getClass();
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        ValueAnimator valueAnimator2 = borderOutlineViewHolder2.downAnimator;
                        if (valueAnimator2 != null) {
                            ValueAnimatorUtil.INSTANCE.startDownScaleAnim(valueAnimator2, borderOutlineViewHolder2.parentView, 0.98f);
                            Unit unit = Unit.INSTANCE;
                        }
                    } else if ((action == 1 || action == 3) && (valueAnimator = borderOutlineViewHolder2.releaseAnimator) != null) {
                        ValueAnimatorUtil.INSTANCE.startReleaseScaleAnim(valueAnimator, borderOutlineViewHolder2.parentView, 1.0f, borderOutlineViewHolder2.downAnimator);
                        Unit unit2 = Unit.INSTANCE;
                        return true;
                    }
                    return true;
                }
            });
            pair = new Pair(fullChunkResizeableFrame, Boolean.FALSE);
        } else {
            View clonedBarView = barItemImpl.getClonedBarView();
            if (clonedBarView != null) {
                ViewParent parent = clonedBarView.getParent();
                ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                if (viewGroup2 != null) {
                    viewGroup2.removeView(clonedBarView);
                }
            } else {
                clonedBarView = null;
            }
            pair = new Pair(clonedBarView, Boolean.TRUE);
        }
        View view = (View) pair.component1();
        boolean booleanValue = ((Boolean) pair.component2()).booleanValue();
        if (view != null) {
            ViewParent parent2 = view.getParent();
            ViewGroup viewGroup3 = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
            if (viewGroup3 != null) {
                viewGroup3.removeView(view);
            }
            frameLayout.addView(view);
            if (booleanValue) {
                FrameLayout frameLayout3 = new FrameLayout(frameLayout.getContext());
                frameLayout3.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                frameLayout3.setDescendantFocusability(393216);
                frameLayout3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.view.BarRecyclerAdapter$setOnTouchListener$1
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        ValueAnimator valueAnimator;
                        BarRecyclerAdapter barRecyclerAdapter = BarRecyclerAdapter.this;
                        motionEvent.getClass();
                        BarRecyclerAdapter.BorderOutlineViewHolder borderOutlineViewHolder2 = borderOutlineViewHolder;
                        Interpolator interpolator = BarRecyclerAdapter.DOWN_INTERPOLATOR;
                        barRecyclerAdapter.getClass();
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            ValueAnimator valueAnimator2 = borderOutlineViewHolder2.downAnimator;
                            if (valueAnimator2 != null) {
                                ValueAnimatorUtil.INSTANCE.startDownScaleAnim(valueAnimator2, borderOutlineViewHolder2.parentView, 0.98f);
                                Unit unit = Unit.INSTANCE;
                            }
                        } else if ((action == 1 || action == 3) && (valueAnimator = borderOutlineViewHolder2.releaseAnimator) != null) {
                            ValueAnimatorUtil.INSTANCE.startReleaseScaleAnim(valueAnimator, borderOutlineViewHolder2.parentView, 1.0f, borderOutlineViewHolder2.downAnimator);
                            Unit unit2 = Unit.INSTANCE;
                            return true;
                        }
                        return true;
                    }
                });
                frameLayout.addView(frameLayout3);
            }
        }
        frameLayout.setFocusable(true);
        frameLayout.setFocusableInTouchMode(true);
        addAccessibilityInfo(borderOutlineViewHolder, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new BorderOutlineViewHolder(this, KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.customizer_border_item, viewGroup, false));
    }

    public BarRecyclerAdapter(ArrayList<String> arrayList, ArrayList<BarItemImpl> arrayList2, FullChunkResizeableFrame fullChunkResizeableFrame, CustomActionManager customActionManager) {
        BarItemImpl barItemImpl;
        this.tileLayoutContainer = fullChunkResizeableFrame;
        this.customActionManager = customActionManager;
        this.barItems = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            String str = arrayList.get(i2);
            i2++;
            int i3 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            if (Intrinsics.areEqual(str, "ExpandableChunkTileLayoutBar")) {
                arrayList.set(i, "TileChunkLayoutBar");
            }
            i = i3;
        }
        int size2 = arrayList.size();
        int i4 = 0;
        while (i4 < size2) {
            String str2 = arrayList.get(i4);
            i4++;
            String str3 = str2;
            int size3 = arrayList2.size();
            int i5 = 0;
            while (true) {
                if (i5 >= size3) {
                    barItemImpl = null;
                    break;
                }
                barItemImpl = arrayList2.get(i5);
                i5++;
                if (Intrinsics.areEqual(str3, barItemImpl.getClass().getSimpleName())) {
                    break;
                }
            }
            BarItemImpl barItemImpl2 = barItemImpl;
            if (barItemImpl2 != null) {
                this.barItems.add(barItemImpl2);
            }
        }
    }
}
