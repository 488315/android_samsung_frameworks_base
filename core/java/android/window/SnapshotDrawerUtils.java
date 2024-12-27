package android.window;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.GraphicBuffer;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.IBinder;
import android.util.Log;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.flags.Flags;

import com.android.internal.R;
import com.android.internal.policy.DecorView;

import com.samsung.android.media.SemExtendedFormat;

/* loaded from: classes4.dex */
public class SnapshotDrawerUtils {
    static final int FLAG_INHERIT_EXCLUDES = 830922810;
    private static final String TAG = "SnapshotDrawerUtils";
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue =
            Flags.toolkitSetFrameRateReadOnly();
    private static final Paint sBackgroundPaint = new Paint();

    public static class SnapshotSurface {
        private final SurfaceControl mRootSurface;
        private boolean mSizeMismatch;
        private final TaskSnapshot mSnapshot;
        private final int mSnapshotH;
        private final int mSnapshotW;
        private SystemBarBackgroundPainter mSystemBarBackgroundPainter;
        private final CharSequence mTitle;
        private final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
        private final Rect mFrame = new Rect();
        private final Rect mSystemBarInsets = new Rect();

        public SnapshotSurface(
                SurfaceControl rootSurface, TaskSnapshot snapshot, CharSequence title) {
            this.mRootSurface = rootSurface;
            this.mSnapshot = snapshot;
            this.mTitle = title;
            HardwareBuffer hwBuffer = snapshot.getHardwareBuffer();
            this.mSnapshotW = hwBuffer.getWidth();
            this.mSnapshotH = hwBuffer.getHeight();
        }

        public void initiateSystemBarPainter(
                int windowFlags,
                int windowPrivateFlags,
                int appearance,
                ActivityManager.TaskDescription taskDescription,
                int requestedVisibleTypes) {
            this.mSystemBarBackgroundPainter =
                    new SystemBarBackgroundPainter(
                            windowFlags,
                            windowPrivateFlags,
                            appearance,
                            taskDescription,
                            1.0f,
                            requestedVisibleTypes);
            int backgroundColor = taskDescription.getBackgroundColor();
            SnapshotDrawerUtils.sBackgroundPaint.setColor(
                    backgroundColor != 0 ? backgroundColor : -1);
        }

        public void setFrames(Rect frame, Rect systemBarInsets) {
            this.mFrame.set(frame);
            this.mSystemBarInsets.set(systemBarInsets);
            this.mSizeMismatch =
                    (this.mFrame.width() == this.mSnapshotW
                                    && this.mFrame.height() == this.mSnapshotH)
                            ? false
                            : true;
            if (!this.mSizeMismatch
                    && frame.left == 0
                    && frame.top == 0
                    && frame.width() == this.mSnapshot.getTaskSize().x
                    && frame.height() == this.mSnapshot.getTaskSize().y) {
                Rect letterboxInsets = this.mSnapshot.getLetterboxInsets();
                if (letterboxInsets.left != 0
                        || letterboxInsets.top != 0
                        || letterboxInsets.right != 0
                        || letterboxInsets.bottom != 0) {
                    Log.d(SnapshotDrawerUtils.TAG, "Size mismatch by letterbox.");
                    this.mSizeMismatch = true;
                }
            }
            this.mSystemBarBackgroundPainter.setInsets(systemBarInsets);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawSnapshot(boolean releaseAfterDraw) {
            Log.v(
                    SnapshotDrawerUtils.TAG,
                    "Drawing snapshot surface sizeMismatch=" + this.mSizeMismatch);
            if (this.mSizeMismatch) {
                drawSizeMismatchSnapshot();
            } else {
                drawSizeMatchSnapshot();
            }
            if (this.mSnapshot.getHardwareBuffer() != null) {
                this.mSnapshot.getHardwareBuffer().close();
            }
            if (releaseAfterDraw) {
                this.mRootSurface.release();
            }
        }

        private void drawSizeMatchSnapshot() {
            this.mTransaction
                    .setBuffer(this.mRootSurface, this.mSnapshot.getHardwareBuffer())
                    .setColorSpace(this.mRootSurface, this.mSnapshot.getColorSpace())
                    .apply();
        }

        private void drawSizeMismatchSnapshot() {
            Rect contentInsets;
            HardwareBuffer buffer = this.mSnapshot.getHardwareBuffer();
            SurfaceSession session = new SurfaceSession();
            boolean aspectRatioMismatch =
                    (SnapshotDrawerUtils.isAspectRatioMatch(
                                            this.mFrame, this.mSnapshotW, this.mSnapshotH)
                                    || com.android.window.flags.Flags
                                            .drawSnapshotAspectRatioMatch())
                            ? false
                            : true;
            SurfaceControl childSurfaceControl =
                    new SurfaceControl.Builder(session)
                            .setName(((Object) this.mTitle) + " - task-snapshot-surface")
                            .setBLASTLayer()
                            .setFormat(buffer.getFormat())
                            .setParent(this.mRootSurface)
                            .setCallsite("TaskSnapshotWindow.drawSizeMismatchSnapshot")
                            .build();
            Rect letterboxInsets = this.mSnapshot.getLetterboxInsets();
            float offsetX = letterboxInsets.left;
            float offsetY = letterboxInsets.top;
            this.mTransaction.show(childSurfaceControl);
            if (aspectRatioMismatch) {
                Rect crop = null;
                if (letterboxInsets.left != 0
                        || letterboxInsets.top != 0
                        || letterboxInsets.right != 0
                        || letterboxInsets.bottom != 0) {
                    crop = calculateSnapshotCrop(letterboxInsets);
                    aspectRatioMismatch =
                            !SnapshotDrawerUtils.isAspectRatioMatch(this.mFrame, crop);
                }
                if (aspectRatioMismatch) {
                    Rect contentInsets2 = this.mSnapshot.getContentInsets();
                    crop = calculateSnapshotCrop(contentInsets2);
                    offsetX = contentInsets2.left;
                    offsetY = contentInsets2.top;
                }
                contentInsets = calculateSnapshotFrame(crop);
                this.mTransaction.setCrop(childSurfaceControl, crop);
            } else {
                contentInsets = null;
            }
            if (offsetX != 0.0f || offsetY != 0.0f) {
                this.mTransaction.setPosition(
                        childSurfaceControl,
                        ((-offsetX) * this.mFrame.width()) / this.mSnapshot.getTaskSize().x,
                        ((-offsetY) * this.mFrame.height()) / this.mSnapshot.getTaskSize().y);
            }
            float scaleX = this.mFrame.width() / this.mSnapshotW;
            float scaleY = this.mFrame.height() / this.mSnapshotH;
            this.mTransaction.setScale(childSurfaceControl, scaleX, scaleY);
            this.mTransaction.setColorSpace(childSurfaceControl, this.mSnapshot.getColorSpace());
            this.mTransaction.setBuffer(childSurfaceControl, this.mSnapshot.getHardwareBuffer());
            if (aspectRatioMismatch) {
                GraphicBuffer background =
                        GraphicBuffer.create(
                                this.mFrame.width(),
                                this.mFrame.height(),
                                1,
                                SemExtendedFormat.DataType.FOOD_SHOT_INFO);
                Canvas c = background != null ? background.lockCanvas() : null;
                if (c == null) {
                    Log.e(
                            SnapshotDrawerUtils.TAG,
                            "Unable to draw snapshot: failed to allocate graphic buffer for "
                                    + ((Object) this.mTitle));
                    this.mTransaction.clear();
                    childSurfaceControl.release();
                    return;
                } else {
                    drawBackgroundAndBars(c, contentInsets);
                    background.unlockCanvasAndPost(c);
                    this.mTransaction.setBuffer(
                            this.mRootSurface, HardwareBuffer.createFromGraphicBuffer(background));
                }
            }
            this.mTransaction.apply();
            childSurfaceControl.release();
        }

        public Rect calculateSnapshotCrop(Rect insets) {
            Rect rect = new Rect();
            rect.set(0, 0, this.mSnapshotW, this.mSnapshotH);
            float scaleX = this.mSnapshotW / this.mSnapshot.getTaskSize().x;
            float scaleY = this.mSnapshotH / this.mSnapshot.getTaskSize().y;
            boolean isTop = this.mFrame.top == 0;
            rect.inset(
                    (int) (insets.left * scaleX),
                    isTop ? 0 : (int) (insets.top * scaleY),
                    (int) (insets.right * scaleX),
                    (int) (insets.bottom * scaleY));
            return rect;
        }

        public Rect calculateSnapshotFrame(Rect crop) {
            float scaleX = this.mSnapshotW / this.mSnapshot.getTaskSize().x;
            float scaleY = this.mSnapshotH / this.mSnapshot.getTaskSize().y;
            Rect frame =
                    new Rect(
                            0,
                            0,
                            (int) ((crop.width() / scaleX) + 0.5f),
                            (int) ((crop.height() / scaleY) + 0.5f));
            frame.offset(this.mSystemBarInsets.left, 0);
            return frame;
        }

        public void drawBackgroundAndBars(Canvas c, Rect frame) {
            int statusBarHeight = this.mSystemBarBackgroundPainter.getStatusBarColorViewHeight();
            boolean fillHorizontally = c.getWidth() > frame.right;
            boolean fillVertically = c.getHeight() > frame.bottom;
            if (fillHorizontally) {
                c.drawRect(
                        frame.right,
                        Color.alpha(this.mSystemBarBackgroundPainter.mStatusBarColor) == 255
                                ? statusBarHeight
                                : 0.0f,
                        c.getWidth(),
                        fillVertically ? frame.bottom : c.getHeight(),
                        SnapshotDrawerUtils.sBackgroundPaint);
            }
            if (fillVertically) {
                c.drawRect(
                        0.0f,
                        frame.bottom,
                        c.getWidth(),
                        c.getHeight(),
                        SnapshotDrawerUtils.sBackgroundPaint);
            }
            this.mSystemBarBackgroundPainter.drawDecors(c, frame);
        }

        public void drawStatusBarBackground(Canvas c, Rect alreadyDrawnFrame) {
            this.mSystemBarBackgroundPainter.drawStatusBarBackground(
                    c,
                    alreadyDrawnFrame,
                    this.mSystemBarBackgroundPainter.getStatusBarColorViewHeight());
        }

        public void drawNavigationBarBackground(Canvas c) {
            this.mSystemBarBackgroundPainter.drawNavigationBarBackground(c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAspectRatioMatch(Rect frame, int w, int h) {
        return !frame.isEmpty()
                && Math.abs(
                                (((float) w) / ((float) h))
                                        - (((float) frame.width()) / ((float) frame.height())))
                        <= 0.01f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAspectRatioMatch(Rect frame1, Rect frame2) {
        return (frame1.isEmpty()
                        || frame2.isEmpty()
                        || Math.abs(
                                        (((float) frame2.width()) / ((float) frame2.height()))
                                                - (((float) frame1.width())
                                                        / ((float) frame1.height())))
                                > 0.01f)
                ? false
                : true;
    }

    public static ActivityManager.TaskDescription getOrCreateTaskDescription(
            ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.taskDescription != null) {
            return runningTaskInfo.taskDescription;
        }
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription();
        taskDescription.setBackgroundColor(-1);
        return taskDescription;
    }

    public static void drawSnapshotOnSurface(
            StartingWindowInfo info,
            WindowManager.LayoutParams lp,
            SurfaceControl rootSurface,
            TaskSnapshot snapshot,
            Rect windowBounds,
            InsetsState topWindowInsetsState,
            boolean releaseAfterDraw) {
        WindowManager.LayoutParams attrs;
        if (windowBounds.isEmpty()) {
            Log.e(TAG, "Unable to draw snapshot on an empty windowBounds");
            return;
        }
        SnapshotSurface drawSurface = new SnapshotSurface(rootSurface, snapshot, lp.getTitle());
        if (!com.android.window.flags.Flags.drawSnapshotAspectRatioMatch()) {
            attrs = info.topOpaqueWindowLayoutParams;
        } else {
            attrs = info.mainWindowLayoutParams;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = info.taskInfo;
        ActivityManager.TaskDescription taskDescription =
                getOrCreateTaskDescription(runningTaskInfo);
        drawSurface.initiateSystemBarPainter(
                lp.flags,
                lp.privateFlags,
                attrs.insetsFlags.appearance,
                taskDescription,
                info.requestedVisibleTypes);
        Rect systemBarInsets = getSystemBarInsets(windowBounds, topWindowInsetsState);
        drawSurface.setFrames(windowBounds, systemBarInsets);
        drawSurface.drawSnapshot(releaseAfterDraw);
    }

    public static WindowManager.LayoutParams createLayoutParameters(
            StartingWindowInfo info,
            CharSequence title,
            int windowType,
            int pixelFormat,
            IBinder token) {
        WindowManager.LayoutParams attrs =
                com.android.window.flags.Flags.drawSnapshotAspectRatioMatch()
                        ? info.mainWindowLayoutParams
                        : info.topOpaqueWindowLayoutParams;
        WindowManager.LayoutParams mainWindowParams = info.mainWindowLayoutParams;
        InsetsState topWindowInsetsState = info.topOpaqueWindowInsetsState;
        if (attrs == null || mainWindowParams == null || topWindowInsetsState == null) {
            Log.w(TAG, "unable to create taskSnapshot surface ");
            return null;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int appearance = attrs.insetsFlags.appearance;
        int windowFlags = attrs.flags;
        int windowPrivateFlags = attrs.privateFlags;
        layoutParams.packageName = mainWindowParams.packageName;
        layoutParams.windowAnimations = mainWindowParams.windowAnimations;
        layoutParams.dimAmount = mainWindowParams.dimAmount;
        layoutParams.type = windowType;
        layoutParams.format = pixelFormat;
        layoutParams.flags = ((-830922811) & windowFlags) | 8 | 16;
        layoutParams.privateFlags = (34816 & windowPrivateFlags) | 536870912;
        layoutParams.token = token;
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.insetsFlags.appearance = appearance;
        layoutParams.insetsFlags.behavior = attrs.insetsFlags.behavior;
        layoutParams.layoutInDisplayCutoutMode = attrs.layoutInDisplayCutoutMode;
        layoutParams.setFitInsetsTypes(attrs.getFitInsetsTypes());
        layoutParams.setFitInsetsSides(attrs.getFitInsetsSides());
        layoutParams.setFitInsetsIgnoringVisibility(attrs.isFitInsetsIgnoringVisibility());
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            layoutParams.setFrameRatePowerSavingsBalanced(false);
        }
        layoutParams.setTitle(title);
        layoutParams.inputFeatures |= 1;
        return layoutParams;
    }

    static Rect getSystemBarInsets(Rect frame, InsetsState state) {
        return state.calculateInsets(frame, WindowInsets.Type.systemBars(), false).toRect();
    }

    public static class SystemBarBackgroundPainter {
        private final int mNavigationBarColor;
        private final int mRequestedVisibleTypes;
        private final float mScale;
        private final int mStatusBarColor;
        private final int mWindowFlags;
        private final int mWindowPrivateFlags;
        private final Paint mStatusBarPaint = new Paint();
        private final Paint mNavigationBarPaint = new Paint();
        private final Rect mSystemBarInsets = new Rect();

        public SystemBarBackgroundPainter(
                int windowFlags,
                int windowPrivateFlags,
                int appearance,
                ActivityManager.TaskDescription taskDescription,
                float scale,
                int requestedVisibleTypes) {
            this.mWindowFlags = windowFlags;
            this.mWindowPrivateFlags = windowPrivateFlags;
            this.mScale = scale;
            Context context = ActivityThread.currentActivityThread().getSystemUiContext();
            int semiTransparent = context.getColor(R.color.system_bar_background_semi_transparent);
            this.mStatusBarColor =
                    DecorView.calculateBarColor(
                            windowFlags,
                            67108864,
                            semiTransparent,
                            taskDescription.getStatusBarColor(),
                            appearance,
                            8,
                            taskDescription.getEnsureStatusBarContrastWhenTransparent(),
                            false);
            this.mNavigationBarColor =
                    DecorView.calculateBarColor(
                            windowFlags,
                            134217728,
                            semiTransparent,
                            taskDescription.getNavigationBarColor(),
                            appearance,
                            16,
                            taskDescription.getEnsureNavigationBarContrastWhenTransparent()
                                    && context.getResources()
                                            .getBoolean(R.bool.config_navBarNeedsScrim),
                            (windowPrivateFlags & 2048) != 0,
                            taskDescription.getDeviceDefaultNavigationBarColor(context));
            this.mStatusBarPaint.setColor(this.mStatusBarColor);
            this.mNavigationBarPaint.setColor(this.mNavigationBarColor);
            this.mRequestedVisibleTypes = requestedVisibleTypes;
        }

        public void setInsets(Rect systemBarInsets) {
            this.mSystemBarInsets.set(systemBarInsets);
        }

        int getStatusBarColorViewHeight() {
            boolean forceBarBackground = (this.mWindowPrivateFlags & 32768) != 0;
            if (DecorView.STATUS_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(
                    this.mRequestedVisibleTypes,
                    this.mStatusBarColor,
                    this.mWindowFlags,
                    forceBarBackground)) {
                return (int) (this.mSystemBarInsets.top * this.mScale);
            }
            return 0;
        }

        private boolean isNavigationBarColorViewVisible() {
            boolean forceBarBackground = (this.mWindowPrivateFlags & 32768) != 0;
            return DecorView.NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(
                    this.mRequestedVisibleTypes,
                    this.mNavigationBarColor,
                    this.mWindowFlags,
                    forceBarBackground);
        }

        public void drawDecors(Canvas c, Rect alreadyDrawnFrame) {
            drawStatusBarBackground(c, alreadyDrawnFrame, getStatusBarColorViewHeight());
            drawNavigationBarBackground(c);
        }

        void drawStatusBarBackground(Canvas c, Rect alreadyDrawnFrame, int statusBarHeight) {
            if (statusBarHeight > 0 && Color.alpha(this.mStatusBarColor) != 0) {
                if (alreadyDrawnFrame == null || c.getWidth() > alreadyDrawnFrame.right) {
                    int rightInset = (int) (this.mSystemBarInsets.right * this.mScale);
                    int left = alreadyDrawnFrame != null ? alreadyDrawnFrame.right : 0;
                    c.drawRect(
                            left,
                            0.0f,
                            c.getWidth() - rightInset,
                            statusBarHeight,
                            this.mStatusBarPaint);
                }
            }
        }

        void drawNavigationBarBackground(Canvas c) {
            Rect navigationBarRect = new Rect();
            DecorView.getNavigationBarRect(
                    c.getWidth(),
                    c.getHeight(),
                    this.mSystemBarInsets,
                    navigationBarRect,
                    this.mScale);
            boolean visible = isNavigationBarColorViewVisible();
            if (visible
                    && Color.alpha(this.mNavigationBarColor) != 0
                    && !navigationBarRect.isEmpty()) {
                c.drawRect(navigationBarRect, this.mNavigationBarPaint);
            }
        }
    }
}
