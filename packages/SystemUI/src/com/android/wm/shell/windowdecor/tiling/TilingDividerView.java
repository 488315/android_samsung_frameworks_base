package com.android.wm.shell.windowdecor.tiling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.DeviceConfig;
import android.util.AttributeSet;
import android.util.Size;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.R;
import com.android.wm.shell.common.split.DividerHandleView;
import com.android.wm.shell.common.split.DividerRoundedCorner;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.samsung.android.knox.EnterpriseContainerCallback;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TilingDividerView extends FrameLayout implements View.OnTouchListener, DragDetector.MotionEventHandler {
    public final Rect backgroundRect;
    public DesktopTilingDividerWindowManager callback;
    public boolean canResize;
    public DividerRoundedCorner corners;
    public DecorThemeUtil decorThemeUtil;
    public FrameLayout dividerBar;
    public final Rect dividerBounds;
    public DragDetector dragDetector;
    public DividerHandleView handle;
    public int handleRegionHeight;
    public IntRange handleY;
    public boolean isDarkMode;
    public int lastAcceptedPos;
    public boolean moving;
    public final Paint paint;
    public boolean resized;
    public int startPos;
    public int touchElevation;

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
    }

    public TilingDividerView(Context context) {
        super(context);
        this.paint = new Paint();
        this.backgroundRect = new Rect();
        this.handleY = new IntRange(0, 0);
        this.decorThemeUtil = new DecorThemeUtil(getContext());
        this.dividerBounds = new Rect();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        if (r3 != 3) goto L83;
     */
    @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleMotionEvent(android.view.View r19, android.view.MotionEvent r20) {
        /*
            Method dump skipped, instructions count: 597
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.tiling.TilingDividerView.handleMotionEvent(android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        canvas.drawRect(this.backgroundRect, this.paint);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.dividerBar = (FrameLayout) requireViewById(R.id.divider_bar);
        this.handle = (DividerHandleView) requireViewById(R.id.docked_divider_handle);
        this.corners = (DividerRoundedCorner) requireViewById(R.id.docked_divider_rounded_corner);
        this.touchElevation = getResources().getDimensionPixelSize(R.dimen.docked_stack_divider_lift_elevation);
        setOnTouchListener(this);
        View findViewById = findViewById(R.id.docked_divider_background);
        findViewById.getClass();
        findViewById.setBackgroundColor(0);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        if (!DeviceConfig.getBoolean("systemui", "cursor_hover_states_enabled", false)) {
            return false;
        }
        if (motionEvent.getAction() == 9) {
            setHovering();
            return true;
        }
        if (motionEvent.getAction() != 10) {
            return false;
        }
        releaseHovering();
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
            int width = (getWidth() - dimensionPixelSize) / 2;
            int height = getHeight();
            this.backgroundRect.set(width, 0, dimensionPixelSize + width, height);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        return PointerIcon.getSystemIcon(getContext(), EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL);
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        DragDetector dragDetector = this.dragDetector;
        if (dragDetector == null) {
            dragDetector = null;
        }
        return dragDetector.onMotionEvent(view, motionEvent);
    }

    public final void releaseHovering() {
        DividerHandleView dividerHandleView = this.handle;
        if (dividerHandleView == null) {
            dividerHandleView = null;
        }
        dividerHandleView.setHovering(false);
        DividerHandleView dividerHandleView2 = this.handle;
        (dividerHandleView2 != null ? dividerHandleView2 : null).animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).start();
    }

    public final void setHovering() {
        DividerHandleView dividerHandleView = this.handle;
        if (dividerHandleView == null) {
            dividerHandleView = null;
        }
        dividerHandleView.setHovering(true);
        DividerHandleView dividerHandleView2 = this.handle;
        (dividerHandleView2 != null ? dividerHandleView2 : null).animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.touchElevation).start();
    }

    public final void setup(DesktopTilingDividerWindowManager desktopTilingDividerWindowManager, Rect rect, Size size, boolean z) {
        this.callback = desktopTilingDividerWindowManager;
        this.dividerBounds.set(rect);
        this.isDarkMode = z;
        Paint paint = this.paint;
        DecorThemeUtil decorThemeUtil = this.decorThemeUtil;
        paint.setColor(ColorKt.m467toArgb8_81llA((z ? decorThemeUtil.darkColors : decorThemeUtil.lightColors).outlineVariant));
        DividerHandleView dividerHandleView = this.handle;
        if (dividerHandleView == null) {
            dividerHandleView = null;
        }
        dividerHandleView.setIsLeftRightSplit(true);
        DividerHandleView dividerHandleView2 = this.handle;
        if (dividerHandleView2 == null) {
            dividerHandleView2 = null;
        }
        dividerHandleView2.getClass();
        int displayId = dividerHandleView2.getContext().getDisplayId();
        DesktopStateImpl.Companion.getClass();
        if (!DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
            dividerHandleView2.mPaint.setColor(z ? dividerHandleView2.getResources().getColor(R.color.tiling_handle_background_dark, null) : dividerHandleView2.getResources().getColor(R.color.tiling_handle_background_light, null));
            dividerHandleView2.setAlpha(0.9f);
        }
        DividerRoundedCorner dividerRoundedCorner = this.corners;
        (dividerRoundedCorner != null ? dividerRoundedCorner : null).mIsLeftRightSplit = true;
        this.handleRegionHeight = size.getHeight();
        size.getWidth();
        RoundedCorner roundedCorner = getContext().getDisplay().getRoundedCorner(0);
        if (roundedCorner != null) {
            roundedCorner.getRadius();
        }
        int height = this.dividerBounds.height();
        int i = this.handleRegionHeight;
        int i2 = (height - i) / 2;
        this.handleY = new IntRange(i2, i + i2);
        this.dragDetector = new DragDetector(this, 0L, ViewConfiguration.get(((FrameLayout) this).mContext).getScaledTouchSlop());
    }

    public TilingDividerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
        this.backgroundRect = new Rect();
        this.handleY = new IntRange(0, 0);
        this.decorThemeUtil = new DecorThemeUtil(getContext());
        this.dividerBounds = new Rect();
    }

    public TilingDividerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint();
        this.backgroundRect = new Rect();
        this.handleY = new IntRange(0, 0);
        this.decorThemeUtil = new DecorThemeUtil(getContext());
        this.dividerBounds = new Rect();
    }

    public TilingDividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.paint = new Paint();
        this.backgroundRect = new Rect();
        this.handleY = new IntRange(0, 0);
        this.decorThemeUtil = new DecorThemeUtil(getContext());
        this.dividerBounds = new Rect();
    }

    public static /* synthetic */ void getHandleY$annotations() {
    }
}
