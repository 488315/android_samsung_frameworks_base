package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.hardware.HardwareBuffer;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.SystemClock;
import android.util.Pair;
import android.util.Slog;
import android.view.InsetsState;
import android.view.ThreadedRenderer;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.SnapshotDrawerUtils;
import android.window.TaskSnapshot;

import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.utils.InsetUtils;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AbsAppSnapshotController {
    static final int SNAPSHOT_MODE_APP_THEME = 1;
    static final int SNAPSHOT_MODE_NONE = 2;
    static final int SNAPSHOT_MODE_REAL = 0;
    public SnapshotCache mCache;
    public Transition.ChangeInfo mCurrentChangeInfo;
    public final float mHighResSnapshotScale = initSnapshotScale();
    public final boolean mIsRunningOnIoT;
    public final boolean mIsRunningOnTv;
    public final WindowManagerService mService;
    public boolean mSnapshotEnabled;

    public AbsAppSnapshotController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mIsRunningOnTv =
                windowManagerService
                        .mContext
                        .getPackageManager()
                        .hasSystemFeature("android.software.leanback");
        this.mIsRunningOnIoT =
                windowManagerService
                        .mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.embedded");
    }

    public static TaskSnapshot validateSnapshot(TaskSnapshot taskSnapshot) {
        HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
        if (hardwareBuffer.getWidth() != 0 && hardwareBuffer.getHeight() != 0) {
            return taskSnapshot;
        }
        hardwareBuffer.close();
        Slog.e(
                "WindowManager",
                "Invalid snapshot dimensions "
                        + hardwareBuffer.getWidth()
                        + "x"
                        + hardwareBuffer.getHeight());
        return null;
    }

    public TaskSnapshot captureSnapshot(WindowContainer windowContainer) {
        WindowState findMainWindow;
        int snapshotMode = getSnapshotMode(windowContainer);
        float f = this.mHighResSnapshotScale;
        if (snapshotMode == 0) {
            return snapshot(windowContainer, f);
        }
        if (snapshotMode != 1) {
            return snapshotMode != 2 ? null : null;
        }
        ActivityRecord topActivity = getTopActivity(windowContainer);
        if (topActivity == null || (findMainWindow = topActivity.findMainWindow(true)) == null) {
            return null;
        }
        ActivityManager.TaskDescription taskDescription = getTaskDescription(windowContainer);
        int alphaComponent =
                ColorUtils.setAlphaComponent(
                        taskDescription.getBackgroundColor(),
                        IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        WindowManager.LayoutParams layoutParams = findMainWindow.mAttrs;
        Rect bounds = windowContainer.getBounds();
        InsetsState insetsStateWithVisibilityOverride =
                findMainWindow.getInsetsStateWithVisibilityOverride();
        Rect rect =
                !topActivity.inFreeformWindowingMode()
                        ? insetsStateWithVisibilityOverride
                                .calculateInsets(
                                        findMainWindow.mWindowFrames.mFrame,
                                        WindowInsets.Type.systemBarsWithoutCaptionBar(),
                                        false)
                                .toRect()
                        : insetsStateWithVisibilityOverride
                                .calculateInsets(
                                        findMainWindow.mWindowFrames.mFrame,
                                        WindowInsets.Type.systemBars(),
                                        false)
                                .toRect();
        SnapshotDrawerUtils.SystemBarBackgroundPainter systemBarBackgroundPainter =
                new SnapshotDrawerUtils.SystemBarBackgroundPainter(
                        layoutParams.flags,
                        layoutParams.privateFlags,
                        layoutParams.insetsFlags.appearance,
                        taskDescription,
                        this.mHighResSnapshotScale,
                        findMainWindow.mRequestedVisibleTypes);
        int width = bounds.width();
        int height = bounds.height();
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
        Rect letterboxInsets = getLetterboxInsets(topActivity);
        InsetUtils.addInsets(rect2, letterboxInsets);
        return validateSnapshot(
                new TaskSnapshot(
                        System.currentTimeMillis(),
                        SystemClock.elapsedRealtimeNanos(),
                        topActivity.mActivityComponent,
                        createHardwareBitmap.getHardwareBuffer(),
                        createHardwareBitmap.getColorSpace(),
                        findMainWindow.getConfiguration().orientation,
                        findMainWindow.getWindowConfiguration().getRotation(),
                        new Point(width, height),
                        rect2,
                        letterboxInsets,
                        false,
                        false,
                        windowContainer.getWindowingMode(),
                        layoutParams.insetsFlags.appearance,
                        false,
                        false,
                        insetsStateWithVisibilityOverride
                                .calculateInsets(
                                        findMainWindow.mWindowFrames.mFrame,
                                        WindowInsets.Type.displayCutout(),
                                        true)
                                .toRect()));
    }

    public final Pair checkIfReadyToSnapshot(WindowContainer windowContainer) {
        WindowManagerPolicy windowManagerPolicy = this.mService.mPolicy;
        DisplayContent displayContent = windowContainer.mDisplayContent;
        if (!((PhoneWindowManager) windowManagerPolicy)
                .isScreenOn(displayContent != null ? displayContent.mDisplayId : 0)) {
            Slog.i("WindowManager", "Attempted to take screenshot while display was off.");
            return null;
        }
        ActivityRecord findAppTokenForSnapshot = findAppTokenForSnapshot(windowContainer);
        if (findAppTokenForSnapshot == null) {
            Slog.w(
                    "WindowManager",
                    "Failed to take screenshot. No visible windows for " + windowContainer);
            return null;
        }
        if (findAppTokenForSnapshot.hasCommittedReparentToAnimationLeash()) {
            Slog.w(
                    "WindowManager",
                    "Failed to take screenshot. App is animating " + findAppTokenForSnapshot);
            return null;
        }
        WindowState findMainWindow = findAppTokenForSnapshot.findMainWindow(true);
        if (findMainWindow == null) {
            Slog.w(
                    "WindowManager",
                    "Failed to take screenshot. No main window for " + windowContainer);
            return null;
        }
        if (!findAppTokenForSnapshot.hasFixedRotationTransform()) {
            return new Pair(findAppTokenForSnapshot, findMainWindow);
        }
        Slog.i(
                "WindowManager",
                "Skip taking screenshot. App has fixed rotation " + findAppTokenForSnapshot);
        return null;
    }

    public void dump(PrintWriter printWriter) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" mHighResSnapshotScale="),
                        this.mHighResSnapshotScale,
                        printWriter,
                        " mSnapshotEnabled="),
                this.mSnapshotEnabled,
                printWriter);
        SnapshotCache snapshotCache = this.mCache;
        snapshotCache.getClass();
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder(" SnapshotCache "), snapshotCache.mName, printWriter);
        synchronized (snapshotCache.mLock) {
            try {
                for (int size = snapshotCache.mRunningCache.size() - 1; size >= 0; size += -1) {
                    SnapshotCache.CacheEntry cacheEntry =
                            (SnapshotCache.CacheEntry) snapshotCache.mRunningCache.valueAt(size);
                    printWriter.println(
                            "   Entry token=" + snapshotCache.mRunningCache.keyAt(size));
                    printWriter.println("     topApp=" + cacheEntry.topApp);
                    printWriter.println("     snapshot=" + cacheEntry.snapshot);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.print(" ");
        printWriter.print("MaxSnapshotCache=");
        printWriter.println(SnapshotCache.sMaxSnapshotCache);
    }

    public abstract ActivityRecord findAppTokenForSnapshot(WindowContainer windowContainer);

    public abstract Rect getLetterboxInsets(ActivityRecord activityRecord);

    public int getSnapshotMode(WindowContainer windowContainer) {
        ActivityRecord topActivity;
        int activityType = windowContainer.getActivityType();
        if (activityType == 3 || activityType == 5) {
            return 2;
        }
        return (activityType == 2
                        || (topActivity = getTopActivity(windowContainer)) == null
                        || (topActivity.mEnableRecentsScreenshot
                                && !topActivity.forAllWindows(
                                        (ToBooleanFunction)
                                                new ActivityRecord$$ExternalSyntheticLambda18(1),
                                        true)))
                ? 0
                : 1;
    }

    public abstract ActivityManager.TaskDescription getTaskDescription(
            WindowContainer windowContainer);

    public abstract ActivityRecord getTopActivity(WindowContainer windowContainer);

    public float initSnapshotScale() {
        return Math.max(
                Math.min(
                        this.mService
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_preferredHyphenationFrequency),
                        1.0f),
                0.1f);
    }

    public Rect prepareTaskSnapshot(WindowContainer windowContainer, TaskSnapshot.Builder builder) {
        int i;
        Pair checkIfReadyToSnapshot = checkIfReadyToSnapshot(windowContainer);
        if (checkIfReadyToSnapshot == null) {
            return null;
        }
        ActivityRecord activityRecord = (ActivityRecord) checkIfReadyToSnapshot.first;
        WindowState windowState = (WindowState) checkIfReadyToSnapshot.second;
        Rect rect =
                !activityRecord.inFreeformWindowingMode()
                        ? windowState
                                .getInsetsStateWithVisibilityOverride()
                                .calculateInsets(
                                        windowState.mWindowFrames.mFrame,
                                        WindowInsets.Type.systemBarsWithoutCaptionBar(),
                                        false)
                                .toRect()
                        : windowState
                                .getInsetsStateWithVisibilityOverride()
                                .calculateInsets(
                                        windowState.mWindowFrames.mFrame,
                                        WindowInsets.Type.systemBars(),
                                        false)
                                .toRect();
        Rect letterboxInsets = getLetterboxInsets(activityRecord);
        InsetUtils.addInsets(rect, letterboxInsets);
        builder.setIsRealSnapshot(true);
        builder.setId(System.currentTimeMillis());
        builder.setContentInsets(rect);
        builder.setLetterboxInsets(letterboxInsets);
        builder.setCutoutInsets(
                windowState
                        .getInsetsStateWithVisibilityOverride()
                        .calculateInsets(
                                windowState.mWindowFrames.mFrame,
                                WindowInsets.Type.displayCutout(),
                                true)
                        .toRect());
        boolean z = windowState.mAttrs.format != -1;
        boolean hasWallpaper = windowState.hasWallpaper();
        int pixelFormat = builder.getPixelFormat();
        if (pixelFormat == 0) {
            pixelFormat =
                    (use16BitFormat()
                                    && activityRecord.occludesParent(true)
                                    && (!z || !hasWallpaper))
                            ? 4
                            : 1;
        }
        boolean z2 =
                PixelFormat.formatHasAlpha(pixelFormat)
                        && (!activityRecord.occludesParent(true) || z);
        builder.setTopActivityComponent(activityRecord.mActivityComponent);
        builder.setPixelFormat(pixelFormat);
        builder.setIsTranslucent(z2);
        builder.setWindowingMode(windowContainer.getWindowingMode());
        builder.setAppearance(windowState.mAttrs.insetsFlags.appearance);
        Configuration configuration = activityRecord.task.getConfiguration();
        int displayRotation = configuration.windowConfiguration.getDisplayRotation();
        Rect rect2 = new Rect();
        Point point = new Point();
        Transition.ChangeInfo changeInfo = this.mCurrentChangeInfo;
        if (changeInfo == null || (i = changeInfo.mRotation) == displayRotation) {
            Configuration configuration2 = windowContainer.getConfiguration();
            rect2.set(configuration2.windowConfiguration.getBounds());
            Rect bounds = configuration.windowConfiguration.getBounds();
            point.set(bounds.width(), bounds.height());
            builder.setRotation(displayRotation);
            builder.setOrientation(configuration2.orientation);
        } else {
            rect2.set(changeInfo.mAbsoluteBounds);
            Rect rect3 = changeInfo.mAbsoluteBounds;
            point.set(rect3.right, rect3.bottom);
            builder.setRotation(i);
            builder.setOrientation(
                    changeInfo.mAbsoluteBounds.height() < changeInfo.mAbsoluteBounds.width()
                            ? 2
                            : 1);
        }
        rect2.offsetTo(0, 0);
        builder.setTaskSize(point);
        return rect2;
    }

    public final boolean shouldDisableSnapshots() {
        return this.mIsRunningOnTv || this.mIsRunningOnIoT || !this.mSnapshotEnabled;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00e9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.window.TaskSnapshot snapshot(
            com.android.server.wm.WindowContainer r17, float r18) {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.AbsAppSnapshotController.snapshot(com.android.server.wm.WindowContainer,"
                    + " float):android.window.TaskSnapshot");
    }

    public abstract boolean use16BitFormat();
}
