package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.ColoredBGHelper;
import java.util.function.IntConsumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class FullChunkResizeableFrame extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long ANIM_DURATION;
    public final int DOWN;
    public final int UP;
    public final int cellHeight;
    public final IntConsumer collapsedBarRowConsumer;
    public int currentRow;
    public int draggingDirection;
    public final View editButton;
    public final FrameLayout frame;
    public final View greyScreen;
    public final AnimatableTileGridLayout gridLayout;
    public final LinearLayout handleView;
    public final ImageView handlerBar;
    public int initTouchY;
    public int maximumFrameSize;
    public View parentView;
    public final ViewGroup recyclerView;
    public int step;
    public final int verticalCapSize;

    public /* synthetic */ FullChunkResizeableFrame(Context context, ViewGroup viewGroup, IntConsumer intConsumer, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, viewGroup, intConsumer, (i & 8) != 0 ? null : attributeSet);
    }

    public final int getNewStep() {
        FrameLayout frameLayout = this.frame;
        if (frameLayout == null) {
            frameLayout = null;
        }
        return (frameLayout.getBottom() - this.verticalCapSize) / this.cellHeight;
    }

    public final void showGreyScreen(boolean z) {
        ViewPropertyAnimator duration;
        View view = this.editButton;
        if (view == null) {
            view = null;
        }
        view.setClickable(z);
        View view2 = this.greyScreen;
        if (view2 == null) {
            view2 = null;
        }
        ViewPropertyAnimator animate = view2.animate();
        if (animate != null) {
            ViewPropertyAnimator alpha = animate.alpha(z ? 1.0f : 0.0f);
            if (alpha != null && (duration = alpha.setDuration(this.ANIM_DURATION)) != null) {
                duration.start();
            }
        }
        View view3 = this.editButton;
        ViewPropertyAnimator animate2 = (view3 != null ? view3 : null).animate();
        if (animate2 != null) {
            ViewPropertyAnimator alpha2 = animate2.alpha(z ? 1.0f : 0.0f);
            if (alpha2 != null) {
                ViewPropertyAnimator duration2 = alpha2.setDuration(z ? 100L : this.ANIM_DURATION);
                if (duration2 != null) {
                    duration2.start();
                }
            }
        }
    }

    public final void updateLayoutHeight(int i) {
        AnimatableTileGridLayout animatableTileGridLayout = this.gridLayout;
        if (animatableTileGridLayout == null) {
            animatableTileGridLayout = null;
        }
        animatableTileGridLayout.getLayoutParams().height = i;
        AnimatableTileGridLayout animatableTileGridLayout2 = this.gridLayout;
        (animatableTileGridLayout2 != null ? animatableTileGridLayout2 : null).requestLayout();
    }

    public FullChunkResizeableFrame(Context context, ViewGroup viewGroup, IntConsumer intConsumer, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.recyclerView = viewGroup;
        this.collapsedBarRowConsumer = intConsumer;
        this.UP = 1;
        this.DOWN = -1;
        this.ANIM_DURATION = 500L;
        this.currentRow = 2;
        this.draggingDirection = 0;
        LayoutInflater.from(context).inflate(R.layout.qs_customize_tilechunklayoutbar_dummy, this);
        this.verticalCapSize = getResources().getDimensionPixelSize(R.dimen.qs_customize_tile_layout_handler);
        this.cellHeight = getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin) + ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileIconSize(context);
        this.frame = (FrameLayout) requireViewById(R.id.frame_layout);
        this.handleView = (LinearLayout) requireViewById(R.id.handler);
        this.handlerBar = (ImageView) requireViewById(R.id.handler_bar);
        this.editButton = requireViewById(R.id.tile_edit_button);
        this.gridLayout = (AnimatableTileGridLayout) requireViewById(R.id.tileLayout);
        this.greyScreen = requireViewById(R.id.grey_screen);
        LinearLayout linearLayout = this.handleView;
        (linearLayout == null ? null : linearLayout).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.view.FullChunkResizeableFrame$initViews$1
            /* JADX WARN: Code restructure failed: missing block: B:24:0x0057, code lost:
            
                if (r5.getHeight() >= r3) goto L30;
             */
            /* JADX WARN: Code restructure failed: missing block: B:25:0x006a, code lost:
            
                r5 = r4.cellHeight;
             */
            /* JADX WARN: Code restructure failed: missing block: B:26:0x006c, code lost:
            
                if (r3 >= r5) goto L40;
             */
            /* JADX WARN: Code restructure failed: missing block: B:27:0x006e, code lost:
            
                r3 = r5;
             */
            /* JADX WARN: Code restructure failed: missing block: B:28:0x0075, code lost:
            
                r4.updateLayoutHeight(r3);
             */
            /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
            
                if (r4.step == r4.getNewStep()) goto L53;
             */
            /* JADX WARN: Code restructure failed: missing block: B:30:0x0080, code lost:
            
                r5 = r4.gridLayout;
             */
            /* JADX WARN: Code restructure failed: missing block: B:31:0x0082, code lost:
            
                if (r5 != null) goto L48;
             */
            /* JADX WARN: Code restructure failed: missing block: B:32:0x0084, code lost:
            
                r5 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x0085, code lost:
            
                r6 = r4.step;
             */
            /* JADX WARN: Code restructure failed: missing block: B:34:0x008b, code lost:
            
                if (r6 >= r4.getNewStep()) goto L51;
             */
            /* JADX WARN: Code restructure failed: missing block: B:35:0x008d, code lost:
            
                r3 = 1.0f;
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x0091, code lost:
            
                r5.setPosition(r3, r6);
                r4.step = r4.getNewStep();
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0090, code lost:
            
                r3 = 0.0f;
             */
            /* JADX WARN: Code restructure failed: missing block: B:38:0x009a, code lost:
            
                r5 = r4.gridLayout;
             */
            /* JADX WARN: Code restructure failed: missing block: B:39:0x009c, code lost:
            
                if (r5 != null) goto L56;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x009f, code lost:
            
                r2 = r5;
             */
            /* JADX WARN: Code restructure failed: missing block: B:41:0x00a0, code lost:
            
                r2.setPosition((r0 - (r4.step * r6)) / r4.cellHeight, r4.getNewStep());
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x0070, code lost:
            
                r5 = r4.maximumFrameSize;
             */
            /* JADX WARN: Code restructure failed: missing block: B:43:0x0072, code lost:
            
                if (r3 <= r5) goto L43;
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x0068, code lost:
            
                if (r5.getHeight() > r3) goto L37;
             */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onTouch(android.view.View r5, android.view.MotionEvent r6) {
                /*
                    Method dump skipped, instructions count: 267
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.view.FullChunkResizeableFrame$initViews$1.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
        ((ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class)).addBarBackground(requireViewById(R.id.qs_customize_tile_chunk_dummy_container), false);
    }
}
