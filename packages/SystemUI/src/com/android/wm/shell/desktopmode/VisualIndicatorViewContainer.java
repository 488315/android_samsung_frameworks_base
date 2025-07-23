package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.VisualIndicatorViewContainer;
import com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VisualIndicatorViewContainer {
    public final BubbleDropTargetBoundsProvider bubbleBoundsProvider;
    public final ShellExecutor desktopExecutor;
    public final SurfaceControl.Builder indicatorBuilder;
    public SurfaceControl indicatorLeash;
    public View indicatorView;
    public SurfaceControlViewHost indicatorViewHost;
    public boolean isReleased;
    public final ShellExecutor mainExecutor;
    public final SnapEventHandler snapEventHandler;
    public final WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory;
    public final SyncTransactionQueue syncQueue;

    public VisualIndicatorViewContainer(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SurfaceControl.Builder builder, SyncTransactionQueue syncTransactionQueue, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler) {
        this(shellExecutor, shellExecutor2, builder, syncTransactionQueue, null, bubbleDropTargetBoundsProvider, snapEventHandler, 16, null);
    }

    public final void fadeInIndicatorInternal(DisplayLayout displayLayout, DesktopModeVisualIndicator.IndicatorType indicatorType, int i, SnapEventHandler snapEventHandler) {
        if (indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW || indicatorType == DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR) {
            return;
        }
        ((HandlerExecutor) this.desktopExecutor).assertCurrentThread();
        View view = this.indicatorView;
        if (view != null) {
            DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
            companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                view.setBackgroundResource(R.drawable.desktop_windowing_transition_background);
            }
            VisualIndicatorAnimator.Companion.getClass();
            Rect indicatorBounds = VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout, indicatorType, this.bubbleBoundsProvider, i, snapEventHandler);
            Rect minBounds = VisualIndicatorAnimator.Companion.getMinBounds(indicatorBounds);
            companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                view.getBackground().setBounds(minBounds);
            }
            VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorAnimator(view, minBounds, indicatorBounds, displayLayout, i);
            visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
            VisualIndicatorAnimator.Companion.setupIndicatorAnimation(visualIndicatorAnimator, VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_IN_ANIM, indicatorType);
            visualIndicatorAnimator.start();
        }
    }

    public final Rect getIndicatorBounds() {
        Drawable background;
        Rect bounds;
        View view = this.indicatorView;
        return (view == null || (background = view.getBackground()) == null || (bounds = background.getBounds()) == null) ? new Rect() : bounds;
    }

    public VisualIndicatorViewContainer(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SurfaceControl.Builder builder, SyncTransactionQueue syncTransactionQueue, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler) {
        this.desktopExecutor = shellExecutor;
        this.mainExecutor = shellExecutor2;
        this.indicatorBuilder = builder;
        this.syncQueue = syncTransactionQueue;
        this.surfaceControlViewHostFactory = surfaceControlViewHostFactory;
        this.bubbleBoundsProvider = bubbleDropTargetBoundsProvider;
        this.snapEventHandler = snapEventHandler;
    }

    public /* synthetic */ VisualIndicatorViewContainer(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SurfaceControl.Builder builder, SyncTransactionQueue syncTransactionQueue, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(shellExecutor, shellExecutor2, builder, syncTransactionQueue, (i & 16) != 0 ? new WindowDecoration.SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer.1
        } : surfaceControlViewHostFactory, bubbleDropTargetBoundsProvider, snapEventHandler);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VisualIndicatorAnimator extends ValueAnimator {
        public static final Companion Companion = new Companion(null);
        public final View blurView;
        public final int displayId;
        public final Rect indicatorEndBounds;
        public final Rect indicatorStartBounds;
        public final View indicatorView;
        public final Rect insetBounds;
        public final RectEvaluator mRectEvaluator;
        public final View targetView;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        final class AlphaAnimType {
            public static final /* synthetic */ AlphaAnimType[] $VALUES;
            public static final AlphaAnimType ALPHA_FADE_IN_ANIM;
            public static final AlphaAnimType ALPHA_FADE_OUT_ANIM;
            public static final AlphaAnimType ALPHA_NO_CHANGE_ANIM;

            static {
                AlphaAnimType alphaAnimType = new AlphaAnimType("ALPHA_FADE_IN_ANIM", 0);
                ALPHA_FADE_IN_ANIM = alphaAnimType;
                AlphaAnimType alphaAnimType2 = new AlphaAnimType("ALPHA_FADE_OUT_ANIM", 1);
                ALPHA_FADE_OUT_ANIM = alphaAnimType2;
                AlphaAnimType alphaAnimType3 = new AlphaAnimType("ALPHA_NO_CHANGE_ANIM", 2);
                ALPHA_NO_CHANGE_ANIM = alphaAnimType3;
                AlphaAnimType[] alphaAnimTypeArr = {alphaAnimType, alphaAnimType2, alphaAnimType3};
                $VALUES = alphaAnimTypeArr;
                EnumEntriesKt.enumEntries(alphaAnimTypeArr);
            }

            private AlphaAnimType(String str, int i) {
            }

            public static AlphaAnimType valueOf(String str) {
                return (AlphaAnimType) Enum.valueOf(AlphaAnimType.class, str);
            }

            public static AlphaAnimType[] values() {
                return (AlphaAnimType[]) $VALUES.clone();
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[DesktopModeVisualIndicator.IndicatorType.values().length];
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_FULLSCREEN_INDICATOR.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_LEFT_INDICATOR.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_RIGHT_INDICATOR.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_BUBBLE_LEFT_INDICATOR.ordinal()] = 6;
                    } catch (NoSuchFieldError unused6) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_BUBBLE_RIGHT_INDICATOR.ordinal()] = 7;
                    } catch (NoSuchFieldError unused7) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static Rect getIndicatorBounds(DisplayLayout displayLayout, DesktopModeVisualIndicator.IndicatorType indicatorType, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, int i, SnapEventHandler snapEventHandler) {
                Rect snapBounds;
                Rect rect = new Rect();
                displayLayout.getStableBounds(rect, false);
                int i2 = rect.top;
                switch (WhenMappings.$EnumSwitchMapping$0[indicatorType.ordinal()]) {
                    case 1:
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                            return rect;
                        }
                        rect.top += i2;
                        rect.bottom -= i2;
                        rect.left += i2;
                        rect.right -= i2;
                        return rect;
                    case 2:
                        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                        rect.top += i2;
                        return rect;
                    case 3:
                        float f = 1.0f - DesktopTasksController.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                        float f2 = 2;
                        return new Rect((int) ((rect.width() * f) / f2), (int) ((rect.height() * f) / f2), (int) (rect.width() - ((rect.width() * f) / f2)), (int) (rect.height() - ((f * rect.height()) / f2)));
                    case 4:
                        DesktopTilingDecorViewModel desktopTilingDecorViewModel = ((DesktopModeWindowDecorViewModel) snapEventHandler).mDesktopTilingDecorViewModel;
                        DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(i);
                        snapBounds = desktopTilingWindowDecoration != null ? desktopTilingWindowDecoration.getSnapBounds(DesktopTasksController.SnapPosition.LEFT) : null;
                        if (snapBounds != null) {
                            return snapBounds;
                        }
                        DisplayController displayController = desktopTilingDecorViewModel.displayController;
                        DisplayLayout displayLayout2 = displayController.getDisplayLayout(i);
                        Rect rect2 = new Rect();
                        if (displayLayout2 != null) {
                            displayLayout2.getStableBounds(rect2, false);
                        }
                        Context displayContext = displayController.getDisplayContext(i);
                        if (displayContext == null) {
                            displayContext = desktopTilingDecorViewModel.context;
                        }
                        int i3 = rect2.left;
                        return new Rect(i3, rect2.top, ((rect2.width() / 2) + i3) - (displayContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width) / 2), rect2.bottom);
                    case 5:
                        DesktopTilingDecorViewModel desktopTilingDecorViewModel2 = ((DesktopModeWindowDecorViewModel) snapEventHandler).mDesktopTilingDecorViewModel;
                        DesktopTilingWindowDecoration desktopTilingWindowDecoration2 = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel2.tilingTransitionHandlerByDisplayId.get(i);
                        snapBounds = desktopTilingWindowDecoration2 != null ? desktopTilingWindowDecoration2.getSnapBounds(DesktopTasksController.SnapPosition.RIGHT) : null;
                        if (snapBounds != null) {
                            return snapBounds;
                        }
                        DisplayController displayController2 = desktopTilingDecorViewModel2.displayController;
                        DisplayLayout displayLayout3 = displayController2.getDisplayLayout(i);
                        Rect rect3 = new Rect();
                        if (displayLayout3 != null) {
                            displayLayout3.getStableBounds(rect3, false);
                        }
                        Context displayContext2 = displayController2.getDisplayContext(i);
                        if (displayContext2 == null) {
                            displayContext2 = desktopTilingDecorViewModel2.context;
                        }
                        return new Rect((displayContext2.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width) / 2) + (rect3.width() / 2) + rect3.left, rect3.top, rect3.right, rect3.bottom);
                    case 6:
                        return bubbleDropTargetBoundsProvider != null ? ((BubblePositioner) bubbleDropTargetBoundsProvider).getBubbleBarExpandedViewDropTargetBounds(true) : new Rect();
                    case 7:
                        return bubbleDropTargetBoundsProvider != null ? ((BubblePositioner) bubbleDropTargetBoundsProvider).getBubbleBarExpandedViewDropTargetBounds(false) : new Rect();
                    default:
                        throw new IllegalArgumentException("Invalid indicator type provided.");
                }
            }

            public static Rect getMinBounds(Rect rect) {
                return new Rect((int) ((rect.width() * 0.015f) + rect.left), (int) ((rect.height() * 0.015f) + rect.top), (int) (rect.right - (rect.width() * 0.015f)), (int) (rect.bottom - (rect.height() * 0.015f)));
            }

            public static void setupIndicatorAnimation(final VisualIndicatorAnimator visualIndicatorAnimator, final AlphaAnimType alphaAnimType, final DesktopModeVisualIndicator.IndicatorType indicatorType) {
                DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                int i = visualIndicatorAnimator.displayId;
                companion.getClass();
                final boolean inDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i);
                visualIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$VisualIndicatorAnimator$Companion$setupIndicatorAnimation$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        VisualIndicatorViewContainer.VisualIndicatorAnimator visualIndicatorAnimator2;
                        Rect rect;
                        View view;
                        Drawable background;
                        Rect bounds;
                        Drawable background2;
                        Rect bounds2;
                        Drawable background3;
                        Drawable background4;
                        Drawable background5;
                        VisualIndicatorViewContainer.VisualIndicatorAnimator visualIndicatorAnimator3 = VisualIndicatorViewContainer.VisualIndicatorAnimator.this;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        View view2 = VisualIndicatorViewContainer.VisualIndicatorAnimator.this.indicatorView;
                        if (!Intrinsics.areEqual(visualIndicatorAnimator3.indicatorStartBounds, visualIndicatorAnimator3.indicatorEndBounds)) {
                            Rect evaluate = visualIndicatorAnimator3.mRectEvaluator.evaluate(animatedFraction, visualIndicatorAnimator3.indicatorStartBounds, visualIndicatorAnimator3.indicatorEndBounds);
                            DesktopStateImpl.Companion companion2 = DesktopStateImpl.Companion;
                            int i2 = visualIndicatorAnimator3.displayId;
                            companion2.getClass();
                            if (DesktopStateImpl.Companion.inDesktopWindowing(i2)) {
                                View view3 = visualIndicatorAnimator3.targetView;
                                if (view3 != null && (background5 = view3.getBackground()) != null) {
                                    background5.setBounds(evaluate);
                                }
                                View view4 = visualIndicatorAnimator3.blurView;
                                if (view4 != null && (background4 = view4.getBackground()) != null) {
                                    background4.setBounds(evaluate);
                                }
                            } else if (view2 != null && (background3 = view2.getBackground()) != null) {
                                background3.setBounds(evaluate);
                            }
                        }
                        if (inDesktopWindowing && indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_FULLSCREEN_INDICATOR && (rect = (visualIndicatorAnimator2 = VisualIndicatorViewContainer.VisualIndicatorAnimator.this).insetBounds) != null && (view = visualIndicatorAnimator2.targetView) != null && (background = view.getBackground()) != null && (bounds = background.getBounds()) != null) {
                            View view5 = visualIndicatorAnimator2.targetView;
                            Integer valueOf = (view5 == null || (background2 = view5.getBackground()) == null || (bounds2 = background2.getBounds()) == null) ? null : Integer.valueOf(bounds2.bottom);
                            valueOf.getClass();
                            bounds.bottom = valueOf.intValue() - rect.bottom;
                        }
                        VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType alphaAnimType2 = alphaAnimType;
                        if (alphaAnimType2 == VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_IN_ANIM) {
                            VisualIndicatorViewContainer.VisualIndicatorAnimator.access$updateIndicatorAlpha(VisualIndicatorViewContainer.VisualIndicatorAnimator.this, valueAnimator.getAnimatedFraction(), VisualIndicatorViewContainer.VisualIndicatorAnimator.this.indicatorView);
                        } else if (alphaAnimType2 == VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_OUT_ANIM) {
                            VisualIndicatorViewContainer.VisualIndicatorAnimator.access$updateIndicatorAlpha(VisualIndicatorViewContainer.VisualIndicatorAnimator.this, 1 - valueAnimator.getAnimatedFraction(), VisualIndicatorViewContainer.VisualIndicatorAnimator.this.indicatorView);
                        }
                    }
                });
                visualIndicatorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$VisualIndicatorAnimator$Companion$setupIndicatorAnimation$2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        if (inDesktopWindowing) {
                            return;
                        }
                        visualIndicatorAnimator.indicatorView.getBackground().setBounds(visualIndicatorAnimator.indicatorEndBounds);
                    }
                });
                visualIndicatorAnimator.setDuration(200L);
            }

            private Companion() {
            }
        }

        public VisualIndicatorAnimator(View view, Rect rect, Rect rect2, DisplayLayout displayLayout, int i) {
            Drawable background;
            this.indicatorView = view;
            Rect rect3 = new Rect(rect);
            this.indicatorStartBounds = rect3;
            this.indicatorEndBounds = rect2;
            this.displayId = i;
            this.insetBounds = displayLayout != null ? displayLayout.mStableInsets : null;
            setFloatValues(0.0f, 1.0f);
            this.mRectEvaluator = new RectEvaluator(new Rect());
            Resources resources = view.getContext().getResources();
            View findViewById = view.findViewById(R.id.targetView);
            this.targetView = findViewById;
            if (findViewById != null && (background = findViewById.getBackground()) != null) {
                background.setBounds(rect3);
            }
            SemBlurInfo build = new SemBlurInfo.Builder(0).setRadius(103).setBackgroundCornerRadius(resources.getDimension(R.dimen.desktop_dnd_drop_corner_radius_size)).build();
            View findViewById2 = view.findViewById(R.id.blurView);
            this.blurView = findViewById2;
            if (findViewById2 != null) {
                findViewById2.semSetBlurInfo(build);
            }
        }

        public static final void access$updateIndicatorAlpha(VisualIndicatorAnimator visualIndicatorAnimator, float f, View view) {
            Drawable background;
            visualIndicatorAnimator.getClass();
            DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
            int i = visualIndicatorAnimator.displayId;
            companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                if ((view != null ? view.getBackground() : null) == null && view != null) {
                    view.setBackgroundResource(R.drawable.desktop_windowing_transition_background);
                }
                LayerDrawable layerDrawable = (LayerDrawable) (view != null ? view.getBackground() : null);
                float f2 = 255 * f;
                layerDrawable.findDrawableByLayerId(R.id.indicator_stroke).setAlpha((int) f2);
                layerDrawable.findDrawableByLayerId(R.id.indicator_solid).setAlpha((int) (f2 * 0.35f));
                return;
            }
            View view2 = visualIndicatorAnimator.targetView;
            if (view2 != null) {
                view2.setAlpha(255 * f);
            }
            View view3 = visualIndicatorAnimator.blurView;
            if (view3 == null || (background = view3.getBackground()) == null) {
                return;
            }
            background.setAlpha((int) (255 * f));
        }

        public static /* synthetic */ void getIndicatorEndBounds$annotations() {
        }

        public static /* synthetic */ void getIndicatorStartBounds$annotations() {
        }
    }

    public static /* synthetic */ void getIndicatorView$annotations() {
    }
}
