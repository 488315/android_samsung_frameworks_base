package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
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
import com.android.wm.shell.common.split.SplitScreenUtils;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.VisualIndicatorViewContainer;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
    public DesktopModeVisualIndicator(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SyncTransactionQueue syncTransactionQueue, final ActivityManager.RunningTaskInfo runningTaskInfo, DisplayController displayController, final Context context, final SurfaceControl surfaceControl, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DragStartState dragStartState, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler, boolean z, boolean z2, final RecentTasksController recentTasksController) throws Resources.NotFoundException {
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
                    final SurfaceControl surfaceControl2 = null;
                    visualIndicatorViewContainer.indicatorView = LayoutInflater.from(context).inflate(R.layout.desktop_drop_view, (ViewGroup) null);
                    final SurfaceControl surfaceControlBuild = visualIndicatorViewContainer.indicatorBuilder.setName("Desktop Mode Visual Indicator(Task=" + runningTaskInfo.taskId + ")").setContainerLayer().setCallsite("DesktopModeVisualIndicator.createView").build();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2, 8, -2);
                    layoutParams.setTitle("Desktop Mode Visual Indicator");
                    layoutParams.setTrustedOverlay();
                    layoutParams.inputFeatures |= 1;
                    WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(runningTaskInfo.configuration, surfaceControlBuild, (InputTransferToken) null);
                    VisualIndicatorViewContainer visualIndicatorViewContainer2 = visualIndicatorViewContainer;
                    WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory = visualIndicatorViewContainer2.surfaceControlViewHostFactory;
                    Context context2 = context;
                    Display display2 = display;
                    surfaceControlViewHostFactory.getClass();
                    visualIndicatorViewContainer2.indicatorViewHost = new SurfaceControlViewHost(context2, display2, windowlessWindowManager, "VisualIndicatorViewContainer");
                    VisualIndicatorViewContainer visualIndicatorViewContainer3 = visualIndicatorViewContainer;
                    View view = visualIndicatorViewContainer3.indicatorView;
                    if (view != null && (surfaceControlViewHost = visualIndicatorViewContainer3.indicatorViewHost) != null) {
                        surfaceControlViewHost.setView(view, layoutParams);
                    }
                    if (!CoreRune.DW_TASK_SNAPSHOT_BLUR) {
                        final VisualIndicatorViewContainer visualIndicatorViewContainer4 = visualIndicatorViewContainer;
                        final SurfaceControl surfaceControl3 = surfaceControl;
                        visualIndicatorViewContainer4.getClass();
                        visualIndicatorViewContainer4.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                SurfaceControl surfaceControl4;
                                visualIndicatorViewContainer4.indicatorLeash = surfaceControlBuild;
                                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                transaction.show(visualIndicatorViewContainer4.indicatorLeash);
                                transaction.setRelativeLayer(visualIndicatorViewContainer4.indicatorLeash, surfaceControl3, -1);
                                if (CoreRune.DW_TASK_SNAPSHOT_BLUR && (surfaceControl4 = surfaceControl2) != null) {
                                    VisualIndicatorViewContainer visualIndicatorViewContainer5 = visualIndicatorViewContainer4;
                                    visualIndicatorViewContainer5.taskSnapshotLeash = surfaceControl4;
                                    transaction.show(surfaceControl4);
                                    transaction.setRelativeLayer(visualIndicatorViewContainer5.taskSnapshotLeash, visualIndicatorViewContainer5.indicatorLeash, -1);
                                }
                                visualIndicatorViewContainer4.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$2.2
                                    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                                    public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                                        transaction2.merge(transaction);
                                        transaction.close();
                                    }
                                });
                            }
                        });
                        return;
                    }
                    visualIndicatorViewContainer.taskSnapshotView = LayoutInflater.from(context).inflate(R.layout.desktop_drop_view, (ViewGroup) null);
                    VisualIndicatorViewContainer visualIndicatorViewContainer5 = visualIndicatorViewContainer;
                    visualIndicatorViewContainer5.recentsTasksController = recentTasksController;
                    final SurfaceControl surfaceControlBuild2 = visualIndicatorViewContainer5.indicatorBuilder.setName("Desktop Mode Tasks Snapshot(Task=" + runningTaskInfo.taskId + ")").setContainerLayer().setCallsite("DesktopModeVisualIndicator.createView").build();
                    VisualIndicatorViewContainer visualIndicatorViewContainer6 = visualIndicatorViewContainer;
                    WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory2 = visualIndicatorViewContainer6.surfaceControlViewHostFactory;
                    Context context3 = context;
                    Display display3 = display;
                    WindowlessWindowManager windowlessWindowManager2 = new WindowlessWindowManager(runningTaskInfo.configuration, surfaceControlBuild2, (InputTransferToken) null);
                    surfaceControlViewHostFactory2.getClass();
                    visualIndicatorViewContainer6.taskSnapshotViewHost = new SurfaceControlViewHost(context3, display3, windowlessWindowManager2, "VisualIndicatorViewContainer");
                    VisualIndicatorViewContainer visualIndicatorViewContainer7 = visualIndicatorViewContainer;
                    View view2 = visualIndicatorViewContainer7.taskSnapshotView;
                    if (view2 != null) {
                        view2.setTranslationX(displayLayout.mWidth);
                        SurfaceControlViewHost surfaceControlViewHost2 = visualIndicatorViewContainer7.taskSnapshotViewHost;
                        if (surfaceControlViewHost2 != null) {
                            surfaceControlViewHost2.setView(view2, layoutParams);
                        }
                    }
                    final VisualIndicatorViewContainer visualIndicatorViewContainer8 = visualIndicatorViewContainer;
                    final SurfaceControl surfaceControl4 = surfaceControl;
                    visualIndicatorViewContainer8.getClass();
                    visualIndicatorViewContainer8.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceControl surfaceControl42;
                            visualIndicatorViewContainer8.indicatorLeash = surfaceControlBuild;
                            final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                            transaction.show(visualIndicatorViewContainer8.indicatorLeash);
                            transaction.setRelativeLayer(visualIndicatorViewContainer8.indicatorLeash, surfaceControl4, -1);
                            if (CoreRune.DW_TASK_SNAPSHOT_BLUR && (surfaceControl42 = surfaceControlBuild2) != null) {
                                VisualIndicatorViewContainer visualIndicatorViewContainer52 = visualIndicatorViewContainer8;
                                visualIndicatorViewContainer52.taskSnapshotLeash = surfaceControl42;
                                transaction.show(surfaceControl42);
                                transaction.setRelativeLayer(visualIndicatorViewContainer52.taskSnapshotLeash, visualIndicatorViewContainer52.indicatorLeash, -1);
                            }
                            visualIndicatorViewContainer8.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$2.2
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
        int iOrdinal = dragStartState.ordinal();
        if (iOrdinal != 1) {
            if (iOrdinal != 2) {
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

    public Rect calculateBubbleLeftRegion(DisplayLayout displayLayout) throws Resources.NotFoundException {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mUseSmallTabletRegions ? R.dimen.drag_zone_bubble_fold : R.dimen.drag_zone_bubble_tablet);
        int i = displayLayout.mHeight;
        return new Rect(0, i - dimensionPixelSize, dimensionPixelSize, i);
    }

    public Rect calculateBubbleRightRegion(DisplayLayout displayLayout) throws Resources.NotFoundException {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mUseSmallTabletRegions ? R.dimen.drag_zone_bubble_fold : R.dimen.drag_zone_bubble_tablet);
        int i = displayLayout.mWidth;
        int i2 = displayLayout.mHeight;
        return new Rect(i - dimensionPixelSize, i2 - dimensionPixelSize, i, i2);
    }

    public Region calculateFullscreenRegion(DisplayLayout displayLayout) {
        return calculateFullscreenRegion(displayLayout, null, 0);
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

    public final String toString() {
        StringBuilder sb = new StringBuilder("DesktopModeVisualIndicator{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" tid=");
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        sb.append(runningTaskInfo != null ? Integer.valueOf(runningTaskInfo.taskId) : "-1");
        sb.append(" t=");
        sb.append(this.mCurrentType);
        sb.append(" d=");
        sb.append(this.mDragStartState);
        sb.append("}");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final IndicatorType updateIndicatorType(PointF pointF, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3, boolean z4) {
        IndicatorType indicatorType;
        boolean z5 = this.mUseSmallTabletRegions;
        DragStartState dragStartState = this.mDragStartState;
        if (z5) {
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
            if (displayLayout == null) {
                indicatorType = IndicatorType.NO_INDICATOR;
            } else if (runningTaskInfo.isFreeform() && !z3) {
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
                    Region regionCalculateFullscreenRegion = calculateFullscreenRegion(displayLayout, new Region(), dimensionPixelSize);
                    if (CoreRune.MW_CAPTION_DESKTOP) {
                        int i = runningTaskInfo.displayId;
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            Rect rectCalculateSplitLeftRegion = calculateSplitLeftRegion(displayLayout, dimensionPixelSize, desktopViewAppHeaderHeightPx);
                            Rect rectCalculateSplitRightRegion = calculateSplitRightRegion(displayLayout, dimensionPixelSize, desktopViewAppHeaderHeightPx);
                            int i2 = (int) pointF.x;
                            int i3 = (int) pointF.y;
                            if (regionCalculateFullscreenRegion.contains(i2, i3) && runningTaskInfo.getDisplayId() == 0) {
                                indicatorType2 = IndicatorType.TO_FULLSCREEN_INDICATOR;
                            }
                            if (rectCalculateSplitLeftRegion.contains(i2, i3)) {
                                indicatorType2 = IndicatorType.TO_SPLIT_LEFT_INDICATOR;
                            }
                            if (rectCalculateSplitRightRegion.contains(i2, i3)) {
                                indicatorType2 = IndicatorType.TO_SPLIT_RIGHT_INDICATOR;
                            }
                        } else if (runningTaskInfo.getWindowingMode() == 1 && regionCalculateFullscreenRegion.contains((int) pointF.x, (int) pointF.y)) {
                            indicatorType = IndicatorType.TO_FULLSCREEN_INDICATOR;
                        }
                        indicatorType = indicatorType2;
                    }
                }
            }
        }
        final IndicatorType indicatorType3 = indicatorType;
        if (z4 && dragStartState != DragStartState.DRAGGED_INTENT) {
            final ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
            final IndicatorType indicatorType4 = this.mCurrentType;
            final VisualIndicatorViewContainer visualIndicatorViewContainer = this.mVisualIndicatorViewContainer;
            if (indicatorType4 == indicatorType3) {
                visualIndicatorViewContainer.getClass();
            } else if (!visualIndicatorViewContainer.isReleased && (!CoreRune.DW_TASK_SNAPSHOT_BLUR || indicatorType4 != IndicatorType.NO_INDICATOR || indicatorType3 != IndicatorType.TO_DESKTOP_INDICATOR)) {
                final DisplayController displayController = this.mDisplayController;
                visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$transitionIndicator$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopModeVisualIndicator.IndicatorType indicatorType5;
                        DisplayLayout displayLayout2 = displayController.getDisplayLayout(runningTaskInfo2.displayId);
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
                        DesktopModeVisualIndicator.IndicatorType indicatorTypeValueOf = DesktopModeVisualIndicator.IndicatorType.valueOf(indicatorType6.name());
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
                        VisualIndicatorViewContainer.VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorViewContainer.VisualIndicatorAnimator(view, VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout2, indicatorTypeValueOf, bubbleDropTargetBoundsProvider, i5, snapEventHandler2), VisualIndicatorViewContainer.VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout2, indicatorType9, bubbleDropTargetBoundsProvider, i5, snapEventHandler2), displayLayout2, i5);
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

    public Region calculateFullscreenRegion(DisplayLayout displayLayout, Region region, int i) {
        Region region2 = new Region();
        DragStartState dragStartState = DragStartState.FROM_FREEFORM;
        DragStartState dragStartState2 = this.mDragStartState;
        int statusBarHeight = (dragStartState2 == dragStartState || dragStartState2 == DragStartState.DRAGGED_INTENT) ? SystemBarUtils.getStatusBarHeight(this.mContext) + 1 : displayLayout.mStableInsets.top * 2;
        this.fullscreenTransitionHeight = statusBarHeight;
        if (dragStartState2 == dragStartState) {
            this.mContext.getResources().getFloat(R.dimen.desktop_mode_fullscreen_region_scale);
            float dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.drag_hint_to_fullscreen_width);
            int i2 = displayLayout.mWidth;
            float f = dimensionPixelSize / 2.0f;
            region2.union(new Rect((int) ((i2 / 2.0f) - f), -32768, (int) ((i2 / 2.0f) + f), statusBarHeight));
            if (region != null) {
                region.op(new Region(new Rect(i, -32768, displayLayout.mWidth - i, statusBarHeight)), region2, Region.Op.DIFFERENCE);
            }
        }
        if (dragStartState2 != DragStartState.FROM_FULLSCREEN && dragStartState2 != DragStartState.FROM_SPLIT && dragStartState2 != DragStartState.DRAGGED_INTENT) {
            return region2;
        }
        region2.union(new Rect(0, -32768, displayLayout.mWidth, statusBarHeight));
        return region2;
    }

    public DesktopModeVisualIndicator(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SyncTransactionQueue syncTransactionQueue, int i, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Context context, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler, final RecentTasksController recentTasksController) {
        ShellExecutor shellExecutor3;
        ShellExecutor shellExecutor4;
        this.fullscreenTransitionHeight = 0;
        SurfaceControl.Builder builder = new SurfaceControl.Builder();
        rootTaskDisplayAreaOrganizer.attachToDisplayArea(i, builder);
        if (DesktopModeFlags.ENABLE_DESKTOP_INDICATOR_IN_SEPARATE_THREAD_BUGFIX.isTrue()) {
            shellExecutor3 = shellExecutor;
            shellExecutor4 = shellExecutor2;
        } else {
            shellExecutor3 = shellExecutor2;
            shellExecutor4 = shellExecutor3;
        }
        final VisualIndicatorViewContainer visualIndicatorViewContainer = new VisualIndicatorViewContainer(shellExecutor3, shellExecutor4, builder, syncTransactionQueue, bubbleDropTargetBoundsProvider, snapEventHandler);
        this.mVisualIndicatorViewContainer = visualIndicatorViewContainer;
        this.mDisplayController = displayController;
        this.mCurrentType = IndicatorType.NO_INDICATOR;
        this.mSnapEventHandler = snapEventHandler;
        Context displayContext = displayController.getDisplayContext(i);
        final Context context2 = displayContext != null ? displayContext : context;
        this.mContext = context2;
        final Display display = displayController.mDisplayManager.getDisplay(i);
        final DisplayLayout displayLayout = displayController.getDisplayLayout(i);
        if (!visualIndicatorViewContainer.isReleased) {
            visualIndicatorViewContainer.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$createViewForDeskLabel$1
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceControlViewHost surfaceControlViewHost;
                    context2.getResources().getDisplayMetrics();
                    DisplayLayout displayLayout2 = displayLayout;
                    int i2 = displayLayout2.mWidth;
                    int i3 = displayLayout2.mHeight;
                    visualIndicatorViewContainer.indicatorView = LayoutInflater.from(context2).inflate(R.layout.desktop_drop_view, (ViewGroup) null);
                    final SurfaceControl surfaceControlBuild = visualIndicatorViewContainer.indicatorBuilder.setName("Desktop Label Indicator").setContainerLayer().setCallsite("DesktopModeVisualIndicator.createViewForDeskLabel").build();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i2, i3, 2, 8, -2);
                    layoutParams.setTitle("Desktop Label Indicator");
                    layoutParams.setTrustedOverlay();
                    layoutParams.inputFeatures |= 4;
                    WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(context2.getResources().getConfiguration(), surfaceControlBuild, (InputTransferToken) null);
                    VisualIndicatorViewContainer visualIndicatorViewContainer2 = visualIndicatorViewContainer;
                    WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory = visualIndicatorViewContainer2.surfaceControlViewHostFactory;
                    Context context3 = context2;
                    Display display2 = display;
                    surfaceControlViewHostFactory.getClass();
                    visualIndicatorViewContainer2.indicatorViewHost = new SurfaceControlViewHost(context3, display2, windowlessWindowManager, "VisualIndicatorViewContainer");
                    VisualIndicatorViewContainer visualIndicatorViewContainer3 = visualIndicatorViewContainer;
                    View view = visualIndicatorViewContainer3.indicatorView;
                    if (view != null && (surfaceControlViewHost = visualIndicatorViewContainer3.indicatorViewHost) != null) {
                        surfaceControlViewHost.setView(view, layoutParams);
                    }
                    final VisualIndicatorViewContainer visualIndicatorViewContainer4 = visualIndicatorViewContainer;
                    visualIndicatorViewContainer4.recentsTasksController = recentTasksController;
                    visualIndicatorViewContainer4.getClass();
                    visualIndicatorViewContainer4.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceControl surfaceControl = surfaceControlBuild;
                            if (!surfaceControl.isValid()) {
                                surfaceControl = null;
                            }
                            if (surfaceControl != null) {
                                VisualIndicatorViewContainer visualIndicatorViewContainer5 = visualIndicatorViewContainer4;
                                visualIndicatorViewContainer5.indicatorLeash = surfaceControl;
                                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
                                transaction.show(visualIndicatorViewContainer5.indicatorLeash);
                                visualIndicatorViewContainer5.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$showIndicator$1$2$1
                                    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                                    public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                                        transaction2.merge(transaction);
                                        transaction.close();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
        this.mTaskInfo = null;
        this.mDragStartState = null;
        this.mUseSmallTabletRegions = false;
        this.mSortedRegions = null;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public DesktopModeVisualIndicator(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SyncTransactionQueue syncTransactionQueue, ActivityManager.RunningTaskInfo runningTaskInfo, DisplayController displayController, Context context, SurfaceControl surfaceControl, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DragStartState dragStartState, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler, RecentTasksController recentTasksController) {
        DisplayLayout displayLayout = displayController.getDisplayLayout(runningTaskInfo.displayId);
        this(shellExecutor, shellExecutor2, syncTransactionQueue, runningTaskInfo, displayController, context, surfaceControl, rootTaskDisplayAreaOrganizer, dragStartState, bubbleDropTargetBoundsProvider, snapEventHandler, false, SplitScreenUtils.isLeftRightSplit(context.getResources().getBoolean(android.R.bool.config_notificationReviewPermissions), true, displayLayout != null && displayLayout.isLandscape(), false, -1), recentTasksController);
    }
}
