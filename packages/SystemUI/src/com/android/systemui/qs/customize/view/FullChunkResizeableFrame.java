package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.function.IntConsumer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

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
        ViewPropertyAnimator viewPropertyAnimatorAnimate = view2.animate();
        if (viewPropertyAnimatorAnimate != null) {
            ViewPropertyAnimator viewPropertyAnimatorAlpha = viewPropertyAnimatorAnimate.alpha(z ? 1.0f : 0.0f);
            if (viewPropertyAnimatorAlpha != null && (duration = viewPropertyAnimatorAlpha.setDuration(this.ANIM_DURATION)) != null) {
                duration.start();
            }
        }
        View view3 = this.editButton;
        ViewPropertyAnimator viewPropertyAnimatorAnimate2 = (view3 != null ? view3 : null).animate();
        if (viewPropertyAnimatorAnimate2 != null) {
            ViewPropertyAnimator viewPropertyAnimatorAlpha2 = viewPropertyAnimatorAnimate2.alpha(z ? 1.0f : 0.0f);
            if (viewPropertyAnimatorAlpha2 != null) {
                ViewPropertyAnimator duration2 = viewPropertyAnimatorAlpha2.setDuration(z ? 100L : this.ANIM_DURATION);
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
            this.heightChange.mo781invoke(Integer.valueOf(this.heightWithoutTiles + i));
        }
    }

    public FullChunkResizeableFrame(Context context, ViewGroup viewGroup, IntConsumer intConsumer, Function1 function1, AttributeSet attributeSet) {
        LayerDrawable layerDrawable;
        Drawable drawableFindDrawableByLayerId;
        super(context, attributeSet);
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
            /* JADX WARN: Removed duplicated region for block: B:49:0x00a0  */
            /* JADX WARN: Removed duplicated region for block: B:56:0x00b1  */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i;
                FullChunkResizeableFrame fullChunkResizeableFrame = this.this$0;
                int i2 = FullChunkResizeableFrame.$r8$clinit;
                fullChunkResizeableFrame.getClass();
                int i3 = 0;
                if (motionEvent == null) {
                    return false;
                }
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action == 1) {
                        fullChunkResizeableFrame.initTouchY = 0;
                        fullChunkResizeableFrame.draggingDirection = 0;
                        fullChunkResizeableFrame.showGreyScreen(true);
                        fullChunkResizeableFrame.recyclerView.requestDisallowInterceptTouchEvent(false);
                        View view2 = fullChunkResizeableFrame.parentView;
                        if (view2 != null) {
                            view2.setHapticFeedbackEnabled(true);
                        }
                        FrameLayout frameLayout = fullChunkResizeableFrame.frame;
                        int bottom = (frameLayout != null ? frameLayout : null).getBottom() - fullChunkResizeableFrame.verticalCapSize;
                        int i4 = fullChunkResizeableFrame.cellHeight;
                        int i5 = bottom < i4 ? 1 : bottom / i4;
                        fullChunkResizeableFrame.step = i5;
                        if (fullChunkResizeableFrame.currentRow != 0) {
                            fullChunkResizeableFrame.updateLayoutHeight(i4 * i5);
                            int i6 = fullChunkResizeableFrame.step;
                            fullChunkResizeableFrame.currentRow = i6;
                            fullChunkResizeableFrame.collapsedBarRowConsumer.accept(i6);
                            return true;
                        }
                    } else if (action == 2) {
                        fullChunkResizeableFrame.recyclerView.requestDisallowInterceptTouchEvent(true);
                        View view3 = fullChunkResizeableFrame.parentView;
                        if (view3 != null) {
                            view3.setHapticFeedbackEnabled(false);
                        }
                        AnimatableTileGridLayout animatableTileGridLayout = fullChunkResizeableFrame.gridLayout;
                        if (animatableTileGridLayout == null) {
                            animatableTileGridLayout = null;
                        }
                        int height = animatableTileGridLayout.getHeight();
                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(motionEvent.getRawY(0));
                        int i7 = iRoundToInt - fullChunkResizeableFrame.initTouchY;
                        try {
                            int[] iArr = new int[2];
                            fullChunkResizeableFrame.recyclerView.getLocationOnScreen(iArr);
                            if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && i7 == 0) {
                                if (iArr[1] + fullChunkResizeableFrame.recyclerView.getHeight() < iRoundToInt) {
                                    i3 = 10;
                                }
                            }
                        } catch (Exception e) {
                            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("fail to getLocationOnScreen ", e, "FullChunkResizeableFrame");
                        }
                        int i8 = height + i7 + i3;
                        int i9 = fullChunkResizeableFrame.cellHeight;
                        if (i8 < i9) {
                            i = i9;
                        } else {
                            i = fullChunkResizeableFrame.maximumFrameSize;
                            if (i8 <= i) {
                                i = i8;
                            }
                        }
                        int i10 = i / i9;
                        fullChunkResizeableFrame.initTouchY += i7;
                        fullChunkResizeableFrame.draggingDirection = i7 > 0 ? fullChunkResizeableFrame.DOWN : fullChunkResizeableFrame.UP;
                        if (i3 != 0) {
                            fullChunkResizeableFrame.draggingDirection = fullChunkResizeableFrame.DOWN;
                        }
                        if (fullChunkResizeableFrame.draggingDirection == fullChunkResizeableFrame.DOWN) {
                            AnimatableTileGridLayout animatableTileGridLayout2 = fullChunkResizeableFrame.gridLayout;
                            if (animatableTileGridLayout2 == null) {
                                animatableTileGridLayout2 = null;
                            }
                            if (animatableTileGridLayout2.getHeight() >= i8) {
                            }
                        } else if (fullChunkResizeableFrame.draggingDirection == fullChunkResizeableFrame.UP) {
                            AnimatableTileGridLayout animatableTileGridLayout3 = fullChunkResizeableFrame.gridLayout;
                            if (animatableTileGridLayout3 == null) {
                                animatableTileGridLayout3 = null;
                            }
                            if (animatableTileGridLayout3.getHeight() > i8) {
                                fullChunkResizeableFrame.updateLayoutHeight(i);
                                int i11 = fullChunkResizeableFrame.step;
                                if (i11 != i10) {
                                    float f = i11 <= i10 ? 1.0f : 0.0f;
                                    while (i11 < i10) {
                                        AnimatableTileGridLayout animatableTileGridLayout4 = fullChunkResizeableFrame.gridLayout;
                                        if (animatableTileGridLayout4 == null) {
                                            animatableTileGridLayout4 = null;
                                        }
                                        animatableTileGridLayout4.setPosition(f, i11);
                                        i11++;
                                    }
                                    fullChunkResizeableFrame.step = i10;
                                }
                                AnimatableTileGridLayout animatableTileGridLayout5 = fullChunkResizeableFrame.gridLayout;
                                AnimatableTileGridLayout animatableTileGridLayout6 = animatableTileGridLayout5 != null ? animatableTileGridLayout5 : null;
                                animatableTileGridLayout6.setPosition((height - (fullChunkResizeableFrame.step * r9)) / fullChunkResizeableFrame.cellHeight, i10);
                            }
                        }
                    }
                } else {
                    fullChunkResizeableFrame.draggingDirection = 0;
                    fullChunkResizeableFrame.initTouchY = MathKt__MathJVMKt.roundToInt(motionEvent.getRawY(0));
                    fullChunkResizeableFrame.showGreyScreen(false);
                    fullChunkResizeableFrame.recyclerView.requestDisallowInterceptTouchEvent(true);
                    ViewGroup viewGroup2 = fullChunkResizeableFrame.recyclerView;
                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                    motionEventObtain.setAction(3);
                    viewGroup2.onTouchEvent(motionEventObtain);
                    View view4 = fullChunkResizeableFrame.parentView;
                    if (view4 != null) {
                        view4.setHapticFeedbackEnabled(false);
                    }
                }
                return true;
            }
        });
        LinearLayout linearLayout2 = this.dummyContainer;
        linearLayout2 = linearLayout2 == null ? null : linearLayout2;
        ColoredBGHelper coloredBGHelper = (ColoredBGHelper) Dependency.sDependency.getDependencyInner(ColoredBGHelper.class);
        Drawable drawable = coloredBGHelper.context.getDrawable(R.drawable.sec_coloring_container_background);
        if (drawable != null && (drawableFindDrawableByLayerId = (layerDrawable = (LayerDrawable) drawable).findDrawableByLayerId(R.id.colored_bg_solid)) != null) {
            drawableFindDrawableByLayerId.setTint(((Number) coloredBGHelper.coloredBackgroundInteractor.backgroundColor.$$delegate_0.getValue()).intValue());
            layerDrawable2 = layerDrawable;
        }
        linearLayout2.setBackground(layerDrawable2);
    }
}
