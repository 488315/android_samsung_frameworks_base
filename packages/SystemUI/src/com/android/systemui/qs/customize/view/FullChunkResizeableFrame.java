package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.ColoredBGHelper;
import java.util.function.IntConsumer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FullChunkResizeableFrame extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long ANIM_DURATION;
    public final int DOWN;
    public final int UP;
    public final int cellHeight;
    public final IntConsumer collapsedBarRowConsumer;
    public int currentRow;
    public int draggingDirection;
    public final LinearLayout dummyContainer;
    public final View editButton;
    public final FrameLayout frame;
    public final View greyScreen;
    public final AnimatableTileGridLayout gridLayout;
    public final LinearLayout handleView;
    public final ImageView handlerBar;
    public final Function1 heightChange;
    public final int heightWithoutTiles;
    public int initTouchY;
    public int maximumFrameSize;
    public View parentView;
    public final ViewGroup recyclerView;
    public int step;
    public final int verticalCapSize;

    public /* synthetic */ FullChunkResizeableFrame(Context context, ViewGroup viewGroup, IntConsumer intConsumer, Function1 function1, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, viewGroup, intConsumer, function1, (i & 16) != 0 ? null : attributeSet);
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
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER) {
            this.heightChange.mo779invoke(Integer.valueOf(this.heightWithoutTiles + i));
        }
    }

    public FullChunkResizeableFrame(Context context, ViewGroup viewGroup, IntConsumer intConsumer, Function1 function1, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayerDrawable layerDrawable;
        Drawable findDrawableByLayerId;
        this.recyclerView = viewGroup;
        this.collapsedBarRowConsumer = intConsumer;
        this.heightChange = function1;
        this.UP = 1;
        this.DOWN = -1;
        this.ANIM_DURATION = 500L;
        this.currentRow = 2;
        this.draggingDirection = 0;
        this.heightWithoutTiles = StrongAuthPopup$$ExternalSyntheticOutline0.m(context, R.dimen.qs_customize_tile_layout_handler, context.getResources().getDimensionPixelSize(R.dimen.qs_customize_tile_layout_padding_top));
        LayoutInflater.from(context).inflate(R.layout.qs_customize_tilechunklayoutbar_dummy, this);
        this.verticalCapSize = getResources().getDimensionPixelSize(R.dimen.qs_customize_tile_layout_handler);
        this.cellHeight = getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin) + ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileIconSize(context);
        this.frame = (FrameLayout) requireViewById(R.id.frame_layout);
        this.handleView = (LinearLayout) requireViewById(R.id.handler);
        this.handlerBar = (ImageView) requireViewById(R.id.handler_bar);
        this.editButton = requireViewById(R.id.tile_edit_button);
        this.gridLayout = (AnimatableTileGridLayout) requireViewById(R.id.tileLayout);
        this.greyScreen = requireViewById(R.id.grey_screen);
        this.dummyContainer = (LinearLayout) requireViewById(R.id.qs_customize_tile_chunk_dummy_container);
        LinearLayout linearLayout = this.handleView;
        LayerDrawable layerDrawable2 = null;
        (linearLayout == null ? null : linearLayout).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.view.FullChunkResizeableFrame$initViews$1
            /* JADX WARN: Code restructure failed: missing block: B:29:0x0063, code lost:
            
                if (r8.getHeight() >= r3) goto L36;
             */
            /* JADX WARN: Code restructure failed: missing block: B:30:0x0076, code lost:
            
                r6.updateLayoutHeight(r5);
                r8 = r6.step;
             */
            /* JADX WARN: Code restructure failed: missing block: B:31:0x007b, code lost:
            
                if (r8 == r4) goto L54;
             */
            /* JADX WARN: Code restructure failed: missing block: B:32:0x007d, code lost:
            
                if (r8 > r4) goto L47;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
            
                r0 = 1.0f;
             */
            /* JADX WARN: Code restructure failed: missing block: B:35:0x0083, code lost:
            
                if (r8 >= r4) goto L79;
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x0085, code lost:
            
                r3 = r6.gridLayout;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0087, code lost:
            
                if (r3 != null) goto L81;
             */
            /* JADX WARN: Code restructure failed: missing block: B:38:0x0089, code lost:
            
                r3 = null;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x008a, code lost:
            
                r3.setPosition(r0, r8);
                r8 = r8 + 1;
             */
            /* JADX WARN: Code restructure failed: missing block: B:43:0x008f, code lost:
            
                r6.step = r4;
             */
            /* JADX WARN: Code restructure failed: missing block: B:44:0x0082, code lost:
            
                r0 = 0.0f;
             */
            /* JADX WARN: Code restructure failed: missing block: B:45:0x0091, code lost:
            
                r8 = r6.gridLayout;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x0093, code lost:
            
                if (r8 != null) goto L57;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x0096, code lost:
            
                r2 = r8;
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x0097, code lost:
            
                r2.setPosition((r1 - (r6.step * r8)) / r6.cellHeight, r4);
             */
            /* JADX WARN: Code restructure failed: missing block: B:49:0x00a3, code lost:
            
                return true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:56:0x0074, code lost:
            
                if (r8.getHeight() > r3) goto L43;
             */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onTouch(android.view.View r7, android.view.MotionEvent r8) {
                /*
                    Method dump skipped, instructions count: 264
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.view.FullChunkResizeableFrame$initViews$1.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
        LinearLayout linearLayout2 = this.dummyContainer;
        linearLayout2 = linearLayout2 == null ? null : linearLayout2;
        ColoredBGHelper coloredBGHelper = (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
        Drawable drawable = coloredBGHelper.context.getDrawable(R.drawable.sec_coloring_container_background);
        if (drawable != null && (findDrawableByLayerId = (layerDrawable = (LayerDrawable) drawable).findDrawableByLayerId(R.id.colored_bg_solid)) != null) {
            findDrawableByLayerId.setTint(((Number) coloredBGHelper.coloredBackgroundInteractor.backgroundColor.$$delegate_0.getValue()).intValue());
            layerDrawable2 = layerDrawable;
        }
        linearLayout2.setBackground(layerDrawable2);
    }
}
