package com.android.wm.shell.windowdecor.tiling;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.DeviceConfig;
import android.util.AttributeSet;
import android.util.Size;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RoundedCorner;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.window.WindowContainerTransaction;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.R;
import com.android.wm.shell.common.split.DividerHandleView;
import com.android.wm.shell.common.split.DividerRoundedCorner;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.ResizeVeil;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.samsung.android.rune.CoreRune;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

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

    /* JADX WARN: Removed duplicated region for block: B:24:0x0071  */
    @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean handleMotionEvent(View view, MotionEvent motionEvent) {
        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper;
        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper2;
        int rawX = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            IntRange intRange = this.handleY;
            int i = intRange.first;
            if (y <= intRange.last && i <= y) {
                DesktopTilingDividerWindowManager desktopTilingDividerWindowManager = this.callback;
                if (desktopTilingDividerWindowManager == null) {
                    desktopTilingDividerWindowManager = null;
                }
                desktopTilingDividerWindowManager.setSlippery(false);
                DesktopTilingWindowDecoration desktopTilingWindowDecoration = desktopTilingDividerWindowManager.transitionHandler;
                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper3 = desktopTilingWindowDecoration.leftTaskResizingHelper;
                if (appResizingHelper3 != null && (appResizingHelper = desktopTilingWindowDecoration.rightTaskResizingHelper) != null) {
                    DesktopModeEventLogger.Companion.getClass();
                    DesktopModeEventLogger.Companion.InputMethod inputMethodFromMotionEvent = DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent);
                    DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.TILING_DIVIDER;
                    desktopTilingWindowDecoration.desktopModeEventLogger.logTaskResizingStarted(resizeTrigger, inputMethodFromMotionEvent, appResizingHelper3.taskInfo, Integer.valueOf(appResizingHelper3.bounds.width()), Integer.valueOf(appResizingHelper3.bounds.height()), desktopTilingWindowDecoration.displayController, null);
                    desktopTilingWindowDecoration.desktopModeEventLogger.logTaskResizingStarted(resizeTrigger, inputMethodFromMotionEvent, appResizingHelper.taskInfo, Integer.valueOf(appResizingHelper.bounds.width()), Integer.valueOf(appResizingHelper.bounds.height()), desktopTilingWindowDecoration.displayController, null);
                }
                DividerHandleView dividerHandleView = this.handle;
                if (dividerHandleView == null) {
                    dividerHandleView = null;
                }
                dividerHandleView.getClass();
                DividerHandleView dividerHandleView2 = this.handle;
                (dividerHandleView2 != null ? dividerHandleView2 : null).animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.touchElevation).start();
                this.canResize = true;
            }
        } else if (actionMasked == 1) {
            if (this.canResize) {
                if (this.moving && this.resized) {
                    Rect rect = this.dividerBounds;
                    int i2 = (rect.left + this.lastAcceptedPos) - this.startPos;
                    rect.left = i2;
                    DesktopTilingDividerWindowManager desktopTilingDividerWindowManager2 = this.callback;
                    if (desktopTilingDividerWindowManager2 == null) {
                        desktopTilingDividerWindowManager2 = null;
                    }
                    desktopTilingDividerWindowManager2.setSlippery(true);
                    SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) desktopTilingDividerWindowManager2.transactionSupplier.get();
                    transaction.setPosition(desktopTilingDividerWindowManager2.leash, i2 - desktopTilingDividerWindowManager2.maxRoundedCornerRadius, desktopTilingDividerWindowManager2.dividerBounds.top);
                    int iWidth = desktopTilingDividerWindowManager2.dividerBounds.width();
                    Rect rect2 = desktopTilingDividerWindowManager2.dividerBounds;
                    rect2.set(i2, rect2.top, iWidth + i2, rect2.bottom);
                    DesktopTilingWindowDecoration desktopTilingWindowDecoration2 = desktopTilingDividerWindowManager2.transitionHandler;
                    Rect rect3 = desktopTilingDividerWindowManager2.dividerBounds;
                    DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper4 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                    if (appResizingHelper4 != null && (appResizingHelper2 = desktopTilingWindowDecoration2.rightTaskResizingHelper) != null) {
                        DesktopModeEventLogger.Companion.getClass();
                        DesktopModeEventLogger.Companion.InputMethod inputMethodFromMotionEvent2 = DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent);
                        DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger2 = DesktopModeEventLogger.Companion.ResizeTrigger.TILING_DIVIDER;
                        desktopTilingWindowDecoration2.desktopModeEventLogger.logTaskResizingEnded(resizeTrigger2, inputMethodFromMotionEvent2, appResizingHelper4.taskInfo, Integer.valueOf(appResizingHelper4.newBounds.width()), Integer.valueOf(appResizingHelper4.newBounds.height()), desktopTilingWindowDecoration2.displayController, null);
                        desktopTilingWindowDecoration2.desktopModeEventLogger.logTaskResizingEnded(resizeTrigger2, inputMethodFromMotionEvent2, appResizingHelper2.taskInfo, Integer.valueOf(appResizingHelper2.newBounds.width()), Integer.valueOf(appResizingHelper2.newBounds.height()), desktopTilingWindowDecoration2.displayController, null);
                        if (Intrinsics.areEqual(appResizingHelper4.newBounds, appResizingHelper4.bounds)) {
                            ResizeVeil resizeVeil = appResizingHelper4.resizeVeil;
                            if (resizeVeil == null) {
                                resizeVeil = null;
                            }
                            resizeVeil.hideVeil();
                            ResizeVeil resizeVeil2 = appResizingHelper2.resizeVeil;
                            if (resizeVeil2 == null) {
                                resizeVeil2 = null;
                            }
                            resizeVeil2.hideVeil();
                            desktopTilingWindowDecoration2.isResizing = false;
                        } else {
                            appResizingHelper4.bounds.set(appResizingHelper4.newBounds);
                            appResizingHelper2.bounds.set(appResizingHelper2.newBounds);
                            desktopTilingWindowDecoration2.onDividerHandleMoved(rect3, transaction);
                            desktopTilingWindowDecoration2.isResizing = false;
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            windowContainerTransaction.setBounds(appResizingHelper4.taskInfo.token, appResizingHelper4.bounds);
                            windowContainerTransaction.setBounds(appResizingHelper2.taskInfo.token, appResizingHelper2.bounds);
                            if (CoreRune.DW_SHELL_CHANGE_TRANSITION) {
                                windowContainerTransaction.setChangeTransitStartBounds(appResizingHelper4.taskInfo.token, appResizingHelper4.bounds);
                                ActivityManager.RunningTaskInfo runningTaskInfo = appResizingHelper4.taskInfo;
                                windowContainerTransaction.setChangeTransitMode(runningTaskInfo.token, 1, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "onDividerHandleDragEnd(", ")"));
                                windowContainerTransaction.setChangeTransitStartBounds(appResizingHelper2.taskInfo.token, appResizingHelper2.bounds);
                                ActivityManager.RunningTaskInfo runningTaskInfo2 = appResizingHelper2.taskInfo;
                                windowContainerTransaction.setChangeTransitMode(runningTaskInfo2.token, 1, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(runningTaskInfo2.taskId, "onDividerHandleDragEnd(", ")"));
                            }
                            desktopTilingWindowDecoration2.transitions.startTransition(6, windowContainerTransaction, desktopTilingWindowDecoration2);
                        }
                    }
                }
                this.moving = false;
                this.canResize = false;
                this.resized = false;
                DividerHandleView dividerHandleView3 = this.handle;
                if (dividerHandleView3 == null) {
                    dividerHandleView3 = null;
                }
                dividerHandleView3.getClass();
                DividerHandleView dividerHandleView4 = this.handle;
                (dividerHandleView4 != null ? dividerHandleView4 : null).animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).start();
                return true;
            }
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
            }
        } else if (this.canResize) {
            if (!this.moving) {
                this.startPos = rawX;
                this.moving = true;
            }
            int i3 = (this.dividerBounds.left + rawX) - this.startPos;
            DesktopTilingDividerWindowManager desktopTilingDividerWindowManager3 = this.callback;
            DesktopTilingDividerWindowManager desktopTilingDividerWindowManager4 = desktopTilingDividerWindowManager3 != null ? desktopTilingDividerWindowManager3 : null;
            SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) desktopTilingDividerWindowManager4.transactionSupplier.get();
            transaction2.setPosition(desktopTilingDividerWindowManager4.leash, i3 - desktopTilingDividerWindowManager4.maxRoundedCornerRadius, desktopTilingDividerWindowManager4.dividerBounds.top);
            int iWidth2 = desktopTilingDividerWindowManager4.dividerBounds.width();
            Rect rect4 = desktopTilingDividerWindowManager4.dividerBounds;
            rect4.set(i3, rect4.top, iWidth2 + i3, rect4.bottom);
            if (desktopTilingDividerWindowManager4.transitionHandler.onDividerHandleMoved(desktopTilingDividerWindowManager4.dividerBounds, transaction2)) {
                this.lastAcceptedPos = rawX;
                this.resized = true;
                return true;
            }
        }
        return true;
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
        View viewFindViewById = findViewById(R.id.docked_divider_background);
        viewFindViewById.getClass();
        viewFindViewById.setBackgroundColor(0);
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
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
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
        int y = (int) motionEvent.getY();
        IntRange intRange = this.handleY;
        return (y > intRange.last || intRange.first > y) ? super.onResolvePointerIcon(motionEvent, i) : PointerIcon.getSystemIcon(getContext(), 20006);
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
        paint.setColor(ColorKt.m469toArgb8_81llA((z ? decorThemeUtil.darkColors : decorThemeUtil.lightColors).outlineVariant));
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
        int iHeight = this.dividerBounds.height();
        int i = this.handleRegionHeight;
        int i2 = (iHeight - i) / 2;
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
