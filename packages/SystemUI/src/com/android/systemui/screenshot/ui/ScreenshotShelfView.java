package com.android.systemui.screenshot.ui;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenshotShelfView extends FrameLayout {
    public View actionsContainer;
    public View actionsContainerBackground;
    public View dismissButton;
    public final DisplayMetrics displayMetrics;
    public final GestureDetector gestureDetector;
    public Function1 onTouchInterceptListener;
    public ImageView screenshotPreview;
    public ViewGroup screenshotStatic;
    public final Rect tmpRect;
    public Function0 userInteractionCallback;

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

    public /* synthetic */ ScreenshotShelfView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static int max(int i, int i2, int... iArr) {
        if (i <= i2) {
            i = i2;
        }
        for (int i3 : iArr) {
            if (i3 > i) {
                i = i3;
            }
        }
        return i;
    }

    public final void addInsetView(Region region, View view, int i) {
        view.getBoundsOnScreen(this.tmpRect);
        this.tmpRect.inset(i, i);
        region.op(this.tmpRect, Region.Op.UNION);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setFocusableInTouchMode(true);
        this.screenshotPreview = (ImageView) requireViewById(R.id.screenshot_preview);
        this.screenshotStatic = (ViewGroup) requireViewById(R.id.screenshot_static);
        this.actionsContainerBackground = requireViewById(R.id.actions_container_background);
        this.actionsContainer = requireViewById(R.id.actions_container);
        this.dismissButton = requireViewById(R.id.screenshot_dismiss_button);
        View view = this.actionsContainer;
        if (view == null) {
            view = null;
        }
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotShelfView$onFinishInflate$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                Function0 function0 = ScreenshotShelfView.this.userInteractionCallback;
                if (function0 == null) {
                    return false;
                }
                function0.invoke();
                return false;
            }
        });
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        Function0 function0 = this.userInteractionCallback;
        if (function0 != null) {
            function0.invoke();
        }
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Function1 function1;
        Function0 function0 = this.userInteractionCallback;
        if (function0 != null) {
            function0.invoke();
        }
        if (motionEvent.getActionMasked() == 0 && (function1 = this.onTouchInterceptListener) != null) {
        }
        return this.gestureDetector.onTouchEvent(motionEvent);
    }

    public final void updateInsets(WindowInsets windowInsets) {
        boolean z = ((FrameLayout) this).mContext.getResources().getConfiguration().orientation == 1;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        int dimensionPixelOffset = ((FrameLayout) this).mContext.getResources().getDimensionPixelOffset(R.dimen.screenshot_shelf_vertical_margin);
        int dimensionPixelOffset2 = getContext().getResources().getDimensionPixelOffset(R.dimen.overlay_action_container_minimum_edge_spacing);
        if (displayCutout == null) {
            ViewGroup viewGroup = this.screenshotStatic;
            (viewGroup != null ? viewGroup : null).setPadding(0, 0, 0, insets.bottom);
            return;
        }
        Insets waterfallInsets = displayCutout.getWaterfallInsets();
        if (z) {
            ViewGroup viewGroup2 = this.screenshotStatic;
            (viewGroup2 != null ? viewGroup2 : null).setPadding(waterfallInsets.left, max(displayCutout.getSafeInsetTop(), waterfallInsets.top, new int[0]), waterfallInsets.right, max(insets.bottom + dimensionPixelOffset, displayCutout.getSafeInsetBottom() + dimensionPixelOffset, waterfallInsets.bottom + dimensionPixelOffset, dimensionPixelOffset2));
        } else {
            ViewGroup viewGroup3 = this.screenshotStatic;
            (viewGroup3 != null ? viewGroup3 : null).setPadding(max(displayCutout.getSafeInsetLeft(), waterfallInsets.left, new int[0]), waterfallInsets.top, max(displayCutout.getSafeInsetRight(), waterfallInsets.right, new int[0]), max(insets.bottom + dimensionPixelOffset, waterfallInsets.bottom + dimensionPixelOffset, dimensionPixelOffset2));
        }
    }

    public ScreenshotShelfView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.displayMetrics = context.getResources().getDisplayMetrics();
        this.tmpRect = new Rect();
        GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotShelfView$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                boolean z;
                ScreenshotShelfView screenshotShelfView = ScreenshotShelfView.this;
                View view = screenshotShelfView.actionsContainer;
                if (view == null) {
                    view = null;
                }
                view.getBoundsOnScreen(screenshotShelfView.tmpRect);
                if (ScreenshotShelfView.this.tmpRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY())) {
                    View view2 = ScreenshotShelfView.this.actionsContainer;
                    if ((view2 != null ? view2 : null).canScrollHorizontally((int) f)) {
                        z = true;
                        return !z;
                    }
                }
                z = false;
                return !z;
            }
        });
        this.gestureDetector = gestureDetector;
        setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotShelfView.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                Function0 function0 = ScreenshotShelfView.this.userInteractionCallback;
                if (function0 != null) {
                    function0.invoke();
                }
                Function1 function1 = ScreenshotShelfView.this.onTouchInterceptListener;
                if (function1 != null) {
                    return ((Boolean) function1.invoke(motionEvent)).booleanValue();
                }
                return false;
            }
        });
        gestureDetector.setIsLongpressEnabled(false);
    }
}
