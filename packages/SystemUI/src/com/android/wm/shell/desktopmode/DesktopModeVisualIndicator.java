package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Pair;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.animation.DecelerateInterpolator;
import android.window.DesktopModeFlags;
import android.window.InputTransferToken;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.VisualIndicatorViewContainer;
import com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DesktopModeVisualIndicator {
    public int fullscreenTransitionHeight;
    public final Context mContext;
    public IndicatorType mCurrentType;
    public final DisplayController mDisplayController;
    public final DragStartState mDragStartState;
    public final SnapEventHandler mSnapEventHandler;
    public final List mSortedRegions;
    public final ActivityManager.RunningTaskInfo mTaskInfo;
    public final boolean mUseSmallTabletRegions;
    public final VisualIndicatorViewContainer mVisualIndicatorViewContainer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum DragStartState {
        FROM_FREEFORM,
        FROM_SPLIT,
        FROM_FULLSCREEN,
        DRAGGED_INTENT;

        public static DragStartState getDragStartState(ActivityManager.RunningTaskInfo runningTaskInfo) {
            if (runningTaskInfo.getWindowingMode() == 1) {
                return FROM_FULLSCREEN;
            }
            if (runningTaskInfo.getWindowingMode() == 6) {
                return FROM_SPLIT;
            }
            if (runningTaskInfo.isFreeform()) {
                return FROM_FREEFORM;
            }
            return null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum IndicatorType {
        NO_INDICATOR,
        TO_DESKTOP_INDICATOR,
        TO_FULLSCREEN_INDICATOR,
        TO_SPLIT_LEFT_INDICATOR,
        TO_SPLIT_RIGHT_INDICATOR,
        TO_BUBBLE_LEFT_INDICATOR,
        TO_BUBBLE_RIGHT_INDICATOR,
        TO_DESKTOP_MAXIMIZED_WINDOW
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.List] */
    public DesktopModeVisualIndicator(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SyncTransactionQueue syncTransactionQueue, final ActivityManager.RunningTaskInfo runningTaskInfo, DisplayController displayController, final Context context, final SurfaceControl surfaceControl, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DragStartState dragStartState, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler, boolean z, boolean z2) {
        ShellExecutor shellExecutor3;
        ShellExecutor shellExecutor4;
        ArrayList arrayList;
        this.fullscreenTransitionHeight = 0;
        SurfaceControl.Builder builder = new SurfaceControl.Builder();
        if ((dragStartState != DragStartState.FROM_FULLSCREEN && dragStartState != DragStartState.FROM_SPLIT) || !DesktopModeFlags.ENABLE_VISUAL_INDICATOR_IN_TRANSITION_BUGFIX.isTrue()) {
            rootTaskDisplayAreaOrganizer.attachToDisplayArea(runningTaskInfo.displayId, builder);
        }
        if (DesktopModeFlags.ENABLE_DESKTOP_INDICATOR_IN_SEPARATE_THREAD_BUGFIX.isTrue()) {
            shellExecutor3 = shellExecutor;
            shellExecutor4 = shellExecutor2;
        } else {
            shellExecutor3 = shellExecutor2;
            shellExecutor4 = shellExecutor3;
        }
        final VisualIndicatorViewContainer visualIndicatorViewContainer = new VisualIndicatorViewContainer(shellExecutor3, shellExecutor4, builder, syncTransactionQueue, bubbleDropTargetBoundsProvider, snapEventHandler);
        this.mVisualIndicatorViewContainer = visualIndicatorViewContainer;
        this.mTaskInfo = runningTaskInfo;
        this.mDisplayController = displayController;
        this.mContext = context;
        this.mCurrentType = IndicatorType.NO_INDICATOR;
        this.mDragStartState = dragStartState;
        this.mSnapEventHandler = snapEventHandler;
        final Display display = displayController.mDisplayManager.getDisplay(runningTaskInfo.displayId);
        final DisplayLayout displayLayout = displayController.getDisplayLayout(runningTaskInfo.displayId);
        if (!visualIndicatorViewContainer.isReleased) {
            visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$createView$1
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceControlViewHost surfaceControlViewHost;
                    context.getResources().getDisplayMetrics();
                    DisplayLayout displayLayout2 = displayLayout;
                    int i = displayLayout2.mWidth;
                    int i2 = displayLayout2.mHeight;
                    VisualIndicatorViewContainer visualIndicatorViewContainer2 = visualIndicatorViewContainer;
                    DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                    int i3 = runningTaskInfo.displayId;
                    companion.getClass();
                    visualIndicatorViewContainer2.indicatorView = DesktopStateImpl.Companion.inDesktopWindowing(i3) ? LayoutInflater.from(context).inflate(R.layout.desktop_drop_view, (ViewGroup) null) : new View(context);
                    final SurfaceControl build = visualIndicatorViewContainer.indicatorBuilder.setName("Desktop Mode Visual Indicator").setContainerLayer().setCallsite("DesktopModeVisualIndicator.createView").build();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2, 8, -2);
                    layoutParams.setTitle("Desktop Mode Visual Indicator");
                    layoutParams.setTrustedOverlay();
                    layoutParams.inputFeatures |= 1;
                    WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(runningTaskInfo.configuration, build, (InputTransferToken) null);
                    VisualIndicatorViewContainer visualIndicatorViewContainer3 = visualIndicatorViewContainer;
                    WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory = visualIndicatorViewContainer3.surfaceControlViewHostFactory;
                    Context context2 = context;
                    Display display2 = display;
                    surfaceControlViewHostFactory.getClass();
                    visualIndicatorViewContainer3.indicatorViewHost = new SurfaceControlViewHost(context2, display2, windowlessWindowManager, "VisualIndicatorViewContainer");
                    VisualIndicatorViewContainer visualIndicatorViewContainer4 = visualIndicatorViewContainer;
                    View view = visualIndicatorViewContainer4.indicatorView;
                    if (view != null && (surfaceControlViewHost = visualIndicatorViewContainer4.indicatorViewHost) != null) {
                        surfaceControlViewHost.setView(view, layoutParams);
                    }
                    final VisualIndicatorViewContainer visualIndicatorViewContainer5 = visualIndicatorViewContainer;
                    final SurfaceControl surfaceControl2 = surfaceControl;
                    visualIndicatorViewContainer5.getClass();
                    visualIndicatorViewContainer5.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            VisualIndicatorViewContainer.this.indicatorLeash = build;
                            final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                            transaction.show(VisualIndicatorViewContainer.this.indicatorLeash);
                            transaction.setRelativeLayer(VisualIndicatorViewContainer.this.indicatorLeash, surfaceControl2, -1);
                            VisualIndicatorViewContainer.this.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$1.1
                                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                                public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                                    transaction2.merge(transaction);
                                    transaction.close();
                                }
                            });
                        }
                    });
                }
            });
        }
        this.mUseSmallTabletRegions = z;
        if (!z) {
            this.mSortedRegions = Collections.EMPTY_LIST;
            return;
        }
        int ordinal = dragStartState.ordinal();
        if (ordinal != 1) {
            if (ordinal != 2) {
                arrayList = Collections.EMPTY_LIST;
            } else {
                ArrayList arrayList2 = new ArrayList();
                if (z2) {
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.drag_zone_h_split_from_app_width_fold);
                    arrayList2.add(new Pair(calculateSplitLeftRegion(displayLayout, dimensionPixelSize, 0), IndicatorType.TO_SPLIT_LEFT_INDICATOR));
                    arrayList2.add(new Pair(calculateSplitRightRegion(displayLayout, dimensionPixelSize, 0), IndicatorType.TO_SPLIT_RIGHT_INDICATOR));
                }
                arrayList2.add(new Pair(new Rect(), IndicatorType.TO_FULLSCREEN_INDICATOR));
                arrayList = arrayList2;
            }
        } else if (z2) {
            ArrayList arrayList3 = new ArrayList();
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.drag_zone_h_split_from_app_width_fold);
            arrayList3.add(new Pair(calculateSplitLeftRegion(displayLayout, dimensionPixelSize2, 0), IndicatorType.TO_SPLIT_LEFT_INDICATOR));
            arrayList3.add(new Pair(calculateSplitRightRegion(displayLayout, dimensionPixelSize2, 0), IndicatorType.TO_SPLIT_RIGHT_INDICATOR));
            arrayList3.add(new Pair(new Rect(), IndicatorType.TO_FULLSCREEN_INDICATOR));
            arrayList = arrayList3;
        } else {
            arrayList = Collections.EMPTY_LIST;
        }
        this.mSortedRegions = arrayList;
    }

    public Rect calculateBubbleLeftRegion(DisplayLayout displayLayout) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mUseSmallTabletRegions ? R.dimen.drag_zone_bubble_fold : R.dimen.drag_zone_bubble_tablet);
        int i = displayLayout.mHeight;
        return new Rect(0, i - dimensionPixelSize, dimensionPixelSize, i);
    }

    public Rect calculateBubbleRightRegion(DisplayLayout displayLayout) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mUseSmallTabletRegions ? R.dimen.drag_zone_bubble_fold : R.dimen.drag_zone_bubble_tablet);
        int i = displayLayout.mWidth;
        int i2 = displayLayout.mHeight;
        return new Rect(i - dimensionPixelSize, i2 - dimensionPixelSize, i, i2);
    }

    public Region calculateFullscreenRegion(DisplayLayout displayLayout, int i) {
        return calculateFullscreenRegion(displayLayout, i, null, 0);
    }

    public Rect calculateSplitLeftRegion(DisplayLayout displayLayout, int i, int i2) {
        int dimensionPixelSize = this.mDragStartState == DragStartState.FROM_FREEFORM ? this.mContext.getResources().getDimensionPixelSize(R.dimen.desktop_mode_split_from_desktop_height) : -i2;
        int i3 = this.mTaskInfo.displayId;
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
            dimensionPixelSize = 0;
        }
        return new Rect(0, dimensionPixelSize, i, displayLayout.mHeight);
    }

    public Rect calculateSplitRightRegion(DisplayLayout displayLayout, int i, int i2) {
        int dimensionPixelSize = this.mDragStartState == DragStartState.FROM_FREEFORM ? this.mContext.getResources().getDimensionPixelSize(R.dimen.desktop_mode_split_from_desktop_height) : -i2;
        int i3 = this.mTaskInfo.displayId;
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
            dimensionPixelSize = 0;
        }
        int i4 = displayLayout.mWidth;
        return new Rect(i4 - i, dimensionPixelSize, i4, displayLayout.mHeight);
    }

    public Rect getIndicatorBounds() {
        return this.mVisualIndicatorViewContainer.getIndicatorBounds();
    }

    public final IndicatorType updateIndicatorType(PointF pointF, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {
        IndicatorType indicatorType;
        boolean z4 = this.mUseSmallTabletRegions;
        DragStartState dragStartState = this.mDragStartState;
        if (z4) {
            Iterator it = this.mSortedRegions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    indicatorType = IndicatorType.NO_INDICATOR;
                    break;
                }
                Pair pair = (Pair) it.next();
                if (((Rect) pair.first).isEmpty()) {
                    indicatorType = (IndicatorType) pair.second;
                    break;
                }
                if (((Rect) pair.first).contains((int) pointF.x, (int) pointF.y)) {
                    indicatorType = (IndicatorType) pair.second;
                    break;
                }
            }
        } else {
            DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
            if (runningTaskInfo.isFreeform() && !z3) {
                indicatorType = IndicatorType.NO_INDICATOR;
            } else if (CoreRune.MW_CAPTION_KEYGUARD && z) {
                indicatorType = IndicatorType.NO_INDICATOR;
            } else if (CoreRune.MW_CAPTION_FREEFORM_STASH && runningTaskInfo.isFreeform() && z2) {
                indicatorType = IndicatorType.NO_INDICATOR;
            } else {
                float f = pointF.x;
                if (f < 0.0f) {
                    indicatorType = IndicatorType.TO_SPLIT_LEFT_INDICATOR;
                } else if (f > displayLayout.mWidth) {
                    indicatorType = IndicatorType.TO_SPLIT_RIGHT_INDICATOR;
                } else {
                    IndicatorType indicatorType2 = dragStartState == DragStartState.FROM_FREEFORM ? IndicatorType.NO_INDICATOR : IndicatorType.TO_DESKTOP_INDICATOR;
                    int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.desktop_mode_transition_region_thickness);
                    int desktopViewAppHeaderHeightPx = SystemBarUtils.getDesktopViewAppHeaderHeightPx(this.mContext);
                    Region calculateFullscreenRegion = calculateFullscreenRegion(displayLayout, desktopViewAppHeaderHeightPx, new Region(), dimensionPixelSize);
                    if (CoreRune.MW_CAPTION_DESKTOP) {
                        int i = runningTaskInfo.displayId;
                        DesktopStateImpl.Companion.getClass();
                        if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            if (runningTaskInfo.getWindowingMode() == 1 && calculateFullscreenRegion.contains((int) pointF.x, (int) pointF.y)) {
                                indicatorType = IndicatorType.TO_FULLSCREEN_INDICATOR;
                            }
                            indicatorType = indicatorType2;
                        }
                    }
                    Rect calculateSplitLeftRegion = calculateSplitLeftRegion(displayLayout, dimensionPixelSize, desktopViewAppHeaderHeightPx);
                    Rect calculateSplitRightRegion = calculateSplitRightRegion(displayLayout, dimensionPixelSize, desktopViewAppHeaderHeightPx);
                    int i2 = (int) pointF.x;
                    int i3 = (int) pointF.y;
                    if (calculateFullscreenRegion.contains(i2, i3) && runningTaskInfo.getDisplayId() == 0) {
                        indicatorType2 = IndicatorType.TO_FULLSCREEN_INDICATOR;
                    }
                    if (calculateSplitLeftRegion.contains(i2, i3)) {
                        indicatorType2 = IndicatorType.TO_SPLIT_LEFT_INDICATOR;
                    }
                    if (calculateSplitRightRegion.contains(i2, i3)) {
                        indicatorType2 = IndicatorType.TO_SPLIT_RIGHT_INDICATOR;
                    }
                    indicatorType = indicatorType2;
                }
            }
        }
        final IndicatorType indicatorType3 = indicatorType;
        if (dragStartState != DragStartState.DRAGGED_INTENT) {
            final ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
            final IndicatorType indicatorType4 = this.mCurrentType;
            final VisualIndicatorViewContainer visualIndicatorViewContainer = this.mVisualIndicatorViewContainer;
            if (indicatorType4 == indicatorType3) {
                visualIndicatorViewContainer.getClass();
            } else if (!visualIndicatorViewContainer.isReleased) {
                final DisplayController displayController = this.mDisplayController;
                visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$transitionIndicator$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopModeVisualIndicator.IndicatorType indicatorType5;
                        DisplayLayout displayLayout2 = DisplayController.this.getDisplayLayout(runningTaskInfo2.displayId);
                        if (displayLayout2 == null) {
                            throw new IllegalStateException(("Expected to find DisplayLayout for taskId" + runningTaskInfo2.taskId + ".").toString());
                        }
                        DesktopModeVisualIndicator.IndicatorType indicatorType6 = indicatorType4;
                        DesktopModeVisualIndicator.IndicatorType indicatorType7 = DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR;
                        if (indicatorType6 == indicatorType7 || indicatorType6 == (indicatorType5 = DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW)) {
                            VisualIndicatorViewContainer visualIndicatorViewContainer2 = visualIndicatorViewContainer;
                            visualIndicatorViewContainer2.fadeInIndicatorInternal(displayLayout2, indicatorType3, runningTaskInfo2.displayId, visualIndicatorViewContainer2.snapEventHandler);
                            return;
                        }
                        DesktopModeVisualIndicator.IndicatorType indicatorType8 = indicatorType3;
                        if (indicatorType8 == indicatorType7 || indicatorType8 == indicatorType5) {
                            VisualIndicatorViewContainer visualIndicatorViewContainer3 = visualIndicatorViewContainer;
                            int i4 = runningTaskInfo2.displayId;
                            SnapEventHandler snapEventHandler = visualIndicatorViewContainer3.snapEventHandler;
                            if (indicatorType6 == indicatorType7 || indicatorType6 == indicatorType5) {
                                return;
                            }
                            visualIndicatorViewContainer3.desktopExecutor.execute(new VisualIndicatorViewContainer$fadeOutIndicator$1(visualIndicatorViewContainer3, indicatorType6, displayLayout2, i4, snapEventHandler, null));
                            return;
                        }
                        DesktopModeVisualIndicator.IndicatorType valueOf = DesktopModeVisualIndicator.IndicatorType.valueOf(indicatorType6.name());
                        VisualIndicatorViewContainer visualIndicatorViewContainer4 = visualIndicatorViewContainer;
                        View view = visualIndicatorViewContainer4.indicatorView;
                        if (view == null) {
                            return;
                        }
                        VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion companion = VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion;
                        DesktopModeVisualIndicator.IndicatorType indicatorType9 = indicatorType3;
                        int i5 = runningTaskInfo2.displayId;
                        companion.getClass();
                        BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider = visualIndicatorViewContainer4.bubbleBoundsProvider;
                        SnapEventHandler snapEventHandler2 = visualIndicatorViewContainer4.snapEventHandler;
                        VisualIndicatorViewContainer.VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorViewContainer.VisualIndicatorAnimator(view, VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout2, valueOf, bubbleDropTargetBoundsProvider, i5, snapEventHandler2), VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout2, indicatorType9, bubbleDropTargetBoundsProvider, i5, snapEventHandler2), displayLayout2, i5);
                        visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
                        VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.setupIndicatorAnimation(visualIndicatorAnimator, VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType.ALPHA_NO_CHANGE_ANIM, indicatorType9);
                        visualIndicatorAnimator.start();
                    }
                });
            }
            this.mCurrentType = indicatorType3;
        }
        return indicatorType3;
    }

    public Region calculateFullscreenRegion(DisplayLayout displayLayout, int i, Region region, int i2) {
        Region region2 = new Region();
        DragStartState dragStartState = DragStartState.FROM_FREEFORM;
        DragStartState dragStartState2 = this.mDragStartState;
        int statusBarHeight = (dragStartState2 == dragStartState || dragStartState2 == DragStartState.DRAGGED_INTENT) ? SystemBarUtils.getStatusBarHeight(this.mContext) : displayLayout.mStableInsets.top * 2;
        this.fullscreenTransitionHeight = statusBarHeight;
        if (dragStartState2 == dragStartState) {
            this.mContext.getResources().getFloat(R.dimen.desktop_mode_fullscreen_region_scale);
            float dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.drag_hint_to_fullscreen_width);
            int i3 = displayLayout.mWidth;
            float f = dimensionPixelSize / 2.0f;
            region2.union(new Rect((int) ((i3 / 2.0f) - f), -32768, (int) ((i3 / 2.0f) + f), statusBarHeight));
            if (region != null) {
                region.op(new Region(new Rect(i2, -32768, displayLayout.mWidth - i2, statusBarHeight)), region2, Region.Op.DIFFERENCE);
            }
        }
        if (dragStartState2 != DragStartState.FROM_FULLSCREEN && dragStartState2 != DragStartState.FROM_SPLIT && dragStartState2 != DragStartState.DRAGGED_INTENT) {
            return region2;
        }
        region2.union(new Rect(0, -32768, displayLayout.mWidth, statusBarHeight));
        return region2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DesktopModeVisualIndicator(com.android.wm.shell.common.ShellExecutor r15, com.android.wm.shell.common.ShellExecutor r16, com.android.wm.shell.common.SyncTransactionQueue r17, android.app.ActivityManager.RunningTaskInfo r18, com.android.wm.shell.common.DisplayController r19, android.content.Context r20, android.view.SurfaceControl r21, com.android.wm.shell.RootTaskDisplayAreaOrganizer r22, com.android.wm.shell.desktopmode.DesktopModeVisualIndicator.DragStartState r23, com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider r24, com.android.wm.shell.windowdecor.tiling.SnapEventHandler r25) {
        /*
            r14 = this;
            r4 = r18
            int r0 = r4.displayId
            r5 = r19
            com.android.wm.shell.common.DisplayLayout r0 = r5.getDisplayLayout(r0)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L16
            boolean r0 = r0.isLandscape()
            if (r0 == 0) goto L16
            r0 = r2
            goto L17
        L16:
            r0 = r1
        L17:
            android.content.res.Resources r3 = r20.getResources()
            r6 = 17891811(0x11101e3, float:2.6633648E-38)
            boolean r3 = r3.getBoolean(r6)
            r6 = -1
            boolean r13 = com.android.wm.shell.common.split.SplitScreenUtils.isLeftRightSplit(r3, r2, r0, r1, r6)
            r12 = 0
            r0 = r14
            r1 = r15
            r2 = r16
            r3 = r17
            r6 = r20
            r7 = r21
            r8 = r22
            r9 = r23
            r10 = r24
            r11 = r25
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.DesktopModeVisualIndicator.<init>(com.android.wm.shell.common.ShellExecutor, com.android.wm.shell.common.ShellExecutor, com.android.wm.shell.common.SyncTransactionQueue, android.app.ActivityManager$RunningTaskInfo, com.android.wm.shell.common.DisplayController, android.content.Context, android.view.SurfaceControl, com.android.wm.shell.RootTaskDisplayAreaOrganizer, com.android.wm.shell.desktopmode.DesktopModeVisualIndicator$DragStartState, com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider, com.android.wm.shell.windowdecor.tiling.SnapEventHandler):void");
    }
}
