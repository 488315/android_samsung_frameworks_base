package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.hardware.HardwareBuffer;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Pair;
import android.util.Slog;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.ScreenCapture;
import android.window.SnapshotDrawerUtils;
import android.window.TaskSnapshot;
import com.android.internal.graphics.ColorUtils;
import com.android.server.wm.Transition;
import com.android.server.wm.utils.InsetUtils;
import com.android.server.policy.WindowManagerPolicy;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class AbsAppSnapshotController {
    static final int SNAPSHOT_MODE_APP_THEME = 1;
    static final int SNAPSHOT_MODE_NONE = 2;
    static final int SNAPSHOT_MODE_REAL = 0;
    public SnapshotCache mCache;
    public final boolean mIsRunningOnIoT;
    public final boolean mIsRunningOnTv;
    public final WindowManagerService mService;
    public boolean mSnapshotEnabled;
    public final Rect mTmpRect = new Rect();
    public final float mHighResSnapshotScale = initSnapshotScale();

    public abstract ActivityRecord findAppTokenForSnapshot(WindowContainer windowContainer);

    public abstract ActivityManager.TaskDescription getTaskDescription(WindowContainer windowContainer);

    public abstract ActivityRecord getTopActivity(WindowContainer windowContainer);

    public abstract ActivityRecord getTopFullscreenActivity(WindowContainer windowContainer);

    public abstract boolean use16BitFormat();

    public AbsAppSnapshotController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mIsRunningOnTv = windowManagerService.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        this.mIsRunningOnIoT = windowManagerService.mContext.getPackageManager().hasSystemFeature("android.hardware.type.embedded");
    }

    public float initSnapshotScale() {
        return Math.max(Math.min(this.mService.mContext.getResources().getFloat(R.dimen.config_closeToSquareDisplayMaxAspectRatio), 1.0f), 0.1f);
    }

    public void initialize(SnapshotCache snapshotCache) {
        this.mCache = snapshotCache;
    }

    public void setSnapshotEnabled(boolean z) {
        this.mSnapshotEnabled = z;
    }

    public boolean shouldDisableSnapshots() {
        return this.mIsRunningOnTv || this.mIsRunningOnIoT || !this.mSnapshotEnabled;
    }

    public TaskSnapshot captureSnapshot(WindowContainer windowContainer, boolean z) {
        if (z) {
            return snapshot(windowContainer);
        }
        int snapshotMode = getSnapshotMode(windowContainer);
        if (snapshotMode == 0) {
            return snapshot(windowContainer);
        }
        if (snapshotMode != 1) {
            return snapshotMode != 2 ? null : null;
        }
        return drawAppThemeSnapshot(windowContainer);
    }

    public final TaskSnapshot recordSnapshotInner(WindowContainer windowContainer, boolean z) {
        if (shouldDisableSnapshots()) {
            return null;
        }
        TaskSnapshot captureSnapshot = captureSnapshot(windowContainer, z && windowContainer.isActivityTypeHome());
        if (captureSnapshot == null) {
            return null;
        }
        HardwareBuffer hardwareBuffer = captureSnapshot.getHardwareBuffer();
        if (hardwareBuffer.getWidth() == 0 || hardwareBuffer.getHeight() == 0) {
            hardwareBuffer.close();
            Slog.e(StartingSurfaceController.TAG, "Invalid snapshot dimensions " + hardwareBuffer.getWidth() + "x" + hardwareBuffer.getHeight());
            return null;
        }
        this.mCache.putSnapshot(windowContainer, captureSnapshot);
        return captureSnapshot;
    }

    public int getSnapshotMode(WindowContainer windowContainer) {
        ActivityRecord topActivity = getTopActivity(windowContainer);
        if (windowContainer.isActivityTypeStandardOrUndefined() || windowContainer.isActivityTypeAssistant()) {
            return (topActivity == null || !topActivity.shouldUseAppThemeSnapshot()) ? 0 : 1;
        }
        return 2;
    }

    public TaskSnapshot snapshot(WindowContainer windowContainer) {
        return snapshot(windowContainer, 0);
    }

    public TaskSnapshot snapshot(WindowContainer windowContainer, int i) {
        ScreenCapture.ScreenshotHardwareBuffer createSnapshot;
        TaskSnapshot.Builder builder = new TaskSnapshot.Builder();
        if (!prepareTaskSnapshot(windowContainer, i, builder) || (createSnapshot = createSnapshot(windowContainer, builder)) == null) {
            return null;
        }
        builder.setCaptureTime(SystemClock.elapsedRealtimeNanos());
        builder.setSnapshot(createSnapshot.getHardwareBuffer());
        builder.setColorSpace(createSnapshot.getColorSpace());
        builder.setContainsSecureLayers(createSnapshot.containsSecureLayers());
        builder.setContainsBlurLayers(createSnapshot.containsBlurLayers());
        return builder.build();
    }

    public ScreenCapture.ScreenshotHardwareBuffer createSnapshot(WindowContainer windowContainer, TaskSnapshot.Builder builder) {
        Point point = new Point();
        Trace.traceBegin(32L, "createSnapshot");
        ScreenCapture.ScreenshotHardwareBuffer createSnapshot = createSnapshot(windowContainer, this.mHighResSnapshotScale, builder.getPixelFormat(), point, builder);
        Trace.traceEnd(32L);
        builder.setTaskSize(point);
        return createSnapshot;
    }

    public ScreenCapture.ScreenshotHardwareBuffer createSnapshot(WindowContainer windowContainer, float f, int i, Point point, TaskSnapshot.Builder builder) {
        SurfaceControl surfaceControl;
        SurfaceControl surfaceControl2;
        Transition.ChangeInfo changeInfo;
        if (windowContainer.getSurfaceControl() == null) {
            Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot. No surface control for " + windowContainer);
            return null;
        }
        this.mTmpRect.setEmpty();
        if (windowContainer.mTransitionController.inFinishingTransition(windowContainer) && (changeInfo = (Transition.ChangeInfo) windowContainer.mTransitionController.mFinishingTransition.mChanges.get(windowContainer)) != null) {
            this.mTmpRect.set(changeInfo.mAbsoluteBounds);
        }
        if (this.mTmpRect.isEmpty()) {
            windowContainer.getBounds(this.mTmpRect);
        }
        boolean z = false;
        this.mTmpRect.offsetTo(0, 0);
        ArrayList arrayList = new ArrayList();
        if (windowContainer.getDisplayContent() != null) {
            WindowState windowState = windowContainer.getDisplayContent().mInputMethodWindow;
            boolean z2 = (windowState == null || windowState.getSurfaceControl() == null || windowContainer.getDisplayContent().shouldImeAttachedToApp()) ? false : true;
            WindowState navigationBar = windowContainer.getDisplayContent().getDisplayPolicy().getNavigationBar();
            boolean z3 = navigationBar != null;
            if (z2 && (surfaceControl2 = windowState.getSurfaceControl()) != null) {
                arrayList.add(surfaceControl2);
            }
            if (z3 && (surfaceControl = navigationBar.getSurfaceControl()) != null) {
                arrayList.add(surfaceControl);
            }
            if (CoreRune.MW_CAPTION_SHELL && (windowContainer instanceof Task)) {
                arrayList.addAll(this.mService.getExcludeLayers(windowContainer));
            }
            if (!z2 && windowState != null && windowState.isVisible()) {
                z = true;
            }
            builder.setHasImeSurface(z);
        }
        SurfaceControl[] surfaceControlArr = new SurfaceControl[arrayList.size()];
        if (!arrayList.isEmpty()) {
            arrayList.toArray(surfaceControlArr);
        }
        ScreenCapture.ScreenshotHardwareBuffer captureLayersExcluding = ScreenCapture.captureLayersExcluding(windowContainer.getSurfaceControl(), this.mTmpRect, f, i, surfaceControlArr);
        if (point != null) {
            point.x = this.mTmpRect.width();
            point.y = this.mTmpRect.height();
        }
        HardwareBuffer hardwareBuffer = captureLayersExcluding == null ? null : captureLayersExcluding.getHardwareBuffer();
        if (!isInvalidHardwareBuffer(hardwareBuffer)) {
            return captureLayersExcluding;
        }
        Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot. isInvalidHardwareBuffer buffer=" + hardwareBuffer);
        return null;
    }

    public static boolean isInvalidHardwareBuffer(HardwareBuffer hardwareBuffer) {
        return hardwareBuffer == null || hardwareBuffer.isClosed() || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1;
    }

    public boolean prepareTaskSnapshot(WindowContainer windowContainer, int i, TaskSnapshot.Builder builder) {
        Rect systemBarInsets;
        Pair checkIfReadyToSnapshot = checkIfReadyToSnapshot(windowContainer);
        boolean z = false;
        if (checkIfReadyToSnapshot == null) {
            return false;
        }
        ActivityRecord activityRecord = (ActivityRecord) checkIfReadyToSnapshot.first;
        WindowState windowState = (WindowState) checkIfReadyToSnapshot.second;
        if (!activityRecord.inFreeformWindowingMode()) {
            systemBarInsets = getSystemBarInsetsWithoutCaptionBar(windowState.getFrame(), windowState.getInsetsStateWithVisibilityOverride());
        } else {
            systemBarInsets = getSystemBarInsets(windowState.getFrame(), windowState.getInsetsStateWithVisibilityOverride());
        }
        Rect letterboxInsets = activityRecord.getLetterboxInsets();
        InsetUtils.addInsets(systemBarInsets, letterboxInsets);
        builder.setIsRealSnapshot(true);
        builder.setId(System.currentTimeMillis());
        builder.setContentInsets(systemBarInsets);
        builder.setLetterboxInsets(letterboxInsets);
        builder.setCutoutInsets(getCutoutInsets(windowState.getFrame(), windowState.getInsetsStateWithVisibilityOverride()));
        boolean z2 = windowState.getAttrs().format != -1;
        boolean hasWallpaper = windowState.hasWallpaper();
        if (i == 0) {
            i = (use16BitFormat() && activityRecord.fillsParent() && (!z2 || !hasWallpaper)) ? 4 : 1;
        }
        if (PixelFormat.formatHasAlpha(i) && (!activityRecord.fillsParent() || z2)) {
            z = true;
        }
        builder.setTopActivityComponent(activityRecord.mActivityComponent);
        builder.setPixelFormat(i);
        builder.setIsTranslucent(z);
        builder.setOrientation(activityRecord.getTask().getConfiguration().orientation);
        builder.setRotation(activityRecord.getTask().getDisplayContent().getRotation());
        builder.setWindowingMode(windowContainer.getWindowingMode());
        builder.setAppearance(getAppearance(windowContainer));
        return true;
    }

    public Pair checkIfReadyToSnapshot(WindowContainer windowContainer) {
        WindowManagerPolicy windowManagerPolicy = this.mService.mPolicy;
        DisplayContent displayContent = windowContainer.mDisplayContent;
        if (!windowManagerPolicy.isScreenOn(displayContent != null ? displayContent.getDisplayId() : 0)) {
            Slog.i(StartingSurfaceController.TAG, "Attempted to take screenshot while display was off.");
            return null;
        }
        ActivityRecord findAppTokenForSnapshot = findAppTokenForSnapshot(windowContainer);
        if (findAppTokenForSnapshot == null) {
            Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot. No visible windows for " + windowContainer);
            return null;
        }
        if (findAppTokenForSnapshot.hasCommittedReparentToAnimationLeash()) {
            Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot. App is animating " + findAppTokenForSnapshot);
            return null;
        }
        WindowState findMainWindow = findAppTokenForSnapshot.findMainWindow();
        if (findMainWindow == null) {
            Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot. No main window for " + windowContainer);
            return null;
        }
        if (findAppTokenForSnapshot.hasFixedRotationTransform()) {
            Slog.i(StartingSurfaceController.TAG, "Skip taking screenshot. App has fixed rotation " + findAppTokenForSnapshot);
            return null;
        }
        return new Pair(findAppTokenForSnapshot, findMainWindow);
    }

    public final TaskSnapshot drawAppThemeSnapshot(WindowContainer windowContainer) {
        WindowState findMainWindow;
        Rect systemBarInsets;
        ActivityRecord topActivity = getTopActivity(windowContainer);
        if (topActivity == null || (findMainWindow = topActivity.findMainWindow()) == null) {
            return null;
        }
        ActivityManager.TaskDescription taskDescription = getTaskDescription(windowContainer);
        int alphaComponent = ColorUtils.setAlphaComponent(taskDescription.getBackgroundColor(), IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        WindowManager.LayoutParams attrs = findMainWindow.getAttrs();
        Rect bounds = windowContainer.getBounds();
        InsetsState insetsStateWithVisibilityOverride = findMainWindow.getInsetsStateWithVisibilityOverride();
        if (!topActivity.inFreeformWindowingMode()) {
            systemBarInsets = getSystemBarInsetsWithoutCaptionBar(findMainWindow.getFrame(), insetsStateWithVisibilityOverride);
        } else {
            systemBarInsets = getSystemBarInsets(findMainWindow.getFrame(), insetsStateWithVisibilityOverride);
        }
        Rect rect = systemBarInsets;
        SnapshotDrawerUtils.SystemBarBackgroundPainter systemBarBackgroundPainter = new SnapshotDrawerUtils.SystemBarBackgroundPainter(attrs.flags, attrs.privateFlags, attrs.insetsFlags.appearance, taskDescription, this.mHighResSnapshotScale, findMainWindow.getRequestedVisibleTypes());
        int width = bounds.width();
        int height = bounds.height();
        float f = this.mHighResSnapshotScale;
        int i = (int) (width * f);
        int i2 = (int) (height * f);
        RenderNode create = RenderNode.create("SnapshotController", null);
        create.setLeftTopRightBottom(0, 0, i, i2);
        create.setClipToBounds(false);
        RecordingCanvas start = create.start(i, i2);
        start.drawColor(alphaComponent);
        systemBarBackgroundPainter.setInsets(rect);
        systemBarBackgroundPainter.drawDecors(start, (Rect) null);
        create.end(start);
        Bitmap createHardwareBitmap = ThreadedRenderer.createHardwareBitmap(create, i, i2);
        if (createHardwareBitmap == null) {
            return null;
        }
        Rect rect2 = new Rect(rect);
        Rect letterboxInsets = topActivity.getLetterboxInsets();
        InsetUtils.addInsets(rect2, letterboxInsets);
        return new TaskSnapshot(System.currentTimeMillis(), SystemClock.elapsedRealtimeNanos(), topActivity.mActivityComponent, createHardwareBitmap.getHardwareBuffer(), createHardwareBitmap.getColorSpace(), findMainWindow.getConfiguration().orientation, findMainWindow.getWindowConfiguration().getRotation(), new Point(width, height), rect2, letterboxInsets, false, false, windowContainer.getWindowingMode(), getAppearance(windowContainer), false, false, getCutoutInsets(findMainWindow.getFrame(), insetsStateWithVisibilityOverride));
    }

    public static Rect getSystemBarInsets(Rect rect, InsetsState insetsState) {
        return insetsState.calculateInsets(rect, WindowInsets.Type.systemBars(), false).toRect();
    }

    public static Rect getSystemBarInsetsWithoutCaptionBar(Rect rect, InsetsState insetsState) {
        return insetsState.calculateInsets(rect, WindowInsets.Type.systemBarsWithoutCaptionBar(), false).toRect();
    }

    public final int getAppearance(WindowContainer windowContainer) {
        ActivityRecord topFullscreenActivity = getTopFullscreenActivity(windowContainer);
        WindowState topFullscreenOpaqueWindow = topFullscreenActivity != null ? topFullscreenActivity.getTopFullscreenOpaqueWindow() : null;
        if (topFullscreenOpaqueWindow != null) {
            return topFullscreenOpaqueWindow.mAttrs.insetsFlags.appearance;
        }
        return 0;
    }

    public void onAppRemoved(ActivityRecord activityRecord) {
        this.mCache.onAppRemoved(activityRecord);
    }

    public void onAppDied(ActivityRecord activityRecord) {
        this.mCache.onAppDied(activityRecord);
    }

    public boolean isAnimatingByRecents(Task task) {
        return task.isAnimatingByRecents();
    }

    public static Rect getCutoutInsets(Rect rect, InsetsState insetsState) {
        return insetsState.calculateInsets(rect, WindowInsets.Type.displayCutout(), true).toRect();
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "mHighResSnapshotScale=" + this.mHighResSnapshotScale);
        printWriter.println(str + "mSnapshotEnabled=" + this.mSnapshotEnabled);
        this.mCache.dump(printWriter, str);
    }
}
