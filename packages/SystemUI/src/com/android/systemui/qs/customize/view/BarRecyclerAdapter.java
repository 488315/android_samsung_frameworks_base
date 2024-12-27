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
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.customize.view.BarRecyclerAdapter;
import com.android.systemui.util.ValueAnimatorUtil;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BarRecyclerAdapter extends RecyclerView.Adapter {
    public static final Interpolator DOWN_INTERPOLATOR;
    public final ArrayList barItems = new ArrayList();
    public final FullChunkResizeableFrame tileLayoutContainer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        DOWN_INTERPOLATOR = new PathInterpolator(0.8f, 0.0f, 0.83f, 0.83f);
    }

    public BarRecyclerAdapter(ArrayList<String> arrayList, ArrayList<BarItemImpl> arrayList2, FullChunkResizeableFrame fullChunkResizeableFrame) {
        Object obj;
        this.tileLayoutContainer = fullChunkResizeableFrame;
        int i = 0;
        for (Object obj2 : arrayList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            if (Intrinsics.areEqual((String) obj2, "ExpandableChunkTileLayoutBar")) {
                arrayList.set(i, "TileChunkLayoutBar");
            }
            i = i2;
        }
        for (String str : arrayList) {
            Iterator<T> it = arrayList2.iterator();
            while (true) {
                if (it.hasNext()) {
                    obj = it.next();
                    if (Intrinsics.areEqual(str, ((BarItemImpl) obj).getClass().getSimpleName())) {
                        break;
                    }
                } else {
                    obj = null;
                    break;
                }
            }
            BarItemImpl barItemImpl = (BarItemImpl) obj;
            if (barItemImpl != null) {
                this.barItems.add(barItemImpl);
            }
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
                    Intrinsics.checkNotNull(motionEvent);
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
                        Intrinsics.checkNotNull(motionEvent);
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
                        }
                        return true;
                    }
                });
                frameLayout.addView(frameLayout3);
            }
        }
        int size = this.barItems.size();
        frameLayout.setFocusable(true);
        frameLayout.setFocusableInTouchMode(true);
        frameLayout.setContentDescription(frameLayout.getContext().getString(R.string.quick_settings_row_of, Integer.valueOf(i + 1), Integer.valueOf(size)) + ", " + frameLayout.getContext().getString(R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        return new BorderOutlineViewHolder(this, MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.customizer_border_item, viewGroup, false));
    }
}
