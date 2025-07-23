package android.window;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.flags.Flags;
import com.android.internal.R;
import com.android.internal.policy.DecorView;

/* loaded from: classes5.dex */
public class SnapshotDrawerUtils {
    static final int FLAG_INHERIT_EXCLUDES = 830922810;
    private static final String TAG = "SnapshotDrawerUtils";
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue = Flags.toolkitSetFrameRateReadOnly();

    public static class SnapshotSurface {
        private final int mContainerH;
        private final int mContainerW;
        private final SurfaceControl mRootSurface;
        private final TaskSnapshot mSnapshot;
        private final int mSnapshotH;
        private final int mSnapshotW;
        private final CharSequence mTitle;
        private final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

        public SnapshotSurface(SurfaceControl surfaceControl, TaskSnapshot taskSnapshot, Rect rect, CharSequence charSequence) {
            this.mRootSurface = surfaceControl;
            this.mSnapshot = taskSnapshot;
            this.mTitle = charSequence;
            HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
            this.mSnapshotW = hardwareBuffer.getWidth();
            this.mSnapshotH = hardwareBuffer.getHeight();
            this.mContainerW = rect.width();
            this.mContainerH = rect.height();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawSnapshot(boolean z) {
            Rect letterboxInsets = this.mSnapshot.getLetterboxInsets();
            boolean z2 = (this.mContainerW == this.mSnapshotW && this.mContainerH == this.mSnapshotH && letterboxInsets.left == 0 && letterboxInsets.top == 0) ? false : true;
            Log.v(SnapshotDrawerUtils.TAG, "Drawing snapshot surface sizeMismatch=" + z2);
            if (z2) {
                drawSizeMismatchSnapshot();
            } else {
                drawSizeMatchSnapshot();
            }
            if (this.mSnapshot.getHardwareBuffer() != null) {
                this.mSnapshot.getHardwareBuffer().close();
            }
            if (z) {
                this.mRootSurface.release();
            }
        }

        private void drawSizeMatchSnapshot() {
            this.mTransaction.setBuffer(this.mRootSurface, this.mSnapshot.getHardwareBuffer()).setColorSpace(this.mRootSurface, this.mSnapshot.getColorSpace()).apply();
        }

        private void drawSizeMismatchSnapshot() {
            HardwareBuffer hardwareBuffer = this.mSnapshot.getHardwareBuffer();
            SurfaceControl build = new SurfaceControl.Builder().setName(((Object) this.mTitle) + " - task-snapshot-surface").setBLASTLayer().setFormat(hardwareBuffer.getFormat()).setParent(this.mRootSurface).setCallsite("TaskSnapshotWindow.drawSizeMismatchSnapshot").build();
            Rect letterboxInsets = this.mSnapshot.getLetterboxInsets();
            float f = (float) letterboxInsets.left;
            float f2 = (float) letterboxInsets.top;
            this.mTransaction.show(build);
            if (f != 0.0f || f2 != 0.0f) {
                this.mTransaction.setPosition(build, ((-f) * this.mContainerW) / this.mSnapshot.getTaskSize().x, ((-f2) * this.mContainerH) / this.mSnapshot.getTaskSize().y);
            }
            this.mTransaction.setScale(build, this.mContainerW / this.mSnapshotW, this.mContainerH / this.mSnapshotH);
            this.mTransaction.setColorSpace(build, this.mSnapshot.getColorSpace());
            this.mTransaction.setBuffer(build, this.mSnapshot.getHardwareBuffer());
            this.mTransaction.apply();
            build.release();
        }
    }

    public static ActivityManager.TaskDescription getOrCreateTaskDescription(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.taskDescription != null) {
            return runningTaskInfo.taskDescription;
        }
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription();
        taskDescription.setBackgroundColor(-1);
        return taskDescription;
    }

    public static void drawSnapshotOnSurface(WindowManager.LayoutParams layoutParams, SurfaceControl surfaceControl, TaskSnapshot taskSnapshot, Rect rect, boolean z) {
        if (rect.isEmpty()) {
            Log.e(TAG, "Unable to draw snapshot on an empty windowBounds");
        } else {
            new SnapshotSurface(surfaceControl, taskSnapshot, rect, layoutParams.getTitle()).drawSnapshot(z);
        }
    }

    public static WindowManager.LayoutParams createLayoutParameters(StartingWindowInfo startingWindowInfo, CharSequence charSequence, int i, int i2, IBinder iBinder) {
        WindowManager.LayoutParams layoutParams = startingWindowInfo.mainWindowLayoutParams;
        if (layoutParams == null) {
            Log.w(TAG, "unable to create taskSnapshot surface ");
            return null;
        }
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        int i3 = layoutParams.insetsFlags.appearance;
        int i4 = layoutParams.flags;
        int i5 = layoutParams.privateFlags;
        layoutParams2.packageName = layoutParams.packageName;
        layoutParams2.windowAnimations = layoutParams.windowAnimations;
        layoutParams2.dimAmount = layoutParams.dimAmount;
        layoutParams2.type = i;
        layoutParams2.format = i2;
        layoutParams2.flags = ((-830922811) & i4) | 24;
        layoutParams2.privateFlags = (34816 & i5) | 536870912;
        layoutParams2.token = iBinder;
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        layoutParams2.insetsFlags.appearance = i3;
        layoutParams2.insetsFlags.behavior = layoutParams.insetsFlags.behavior;
        layoutParams2.layoutInDisplayCutoutMode = layoutParams.layoutInDisplayCutoutMode;
        layoutParams2.setFitInsetsTypes(layoutParams.getFitInsetsTypes());
        layoutParams2.setFitInsetsSides(layoutParams.getFitInsetsSides());
        layoutParams2.setFitInsetsIgnoringVisibility(layoutParams.isFitInsetsIgnoringVisibility());
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            layoutParams2.setFrameRatePowerSavingsBalanced(false);
        }
        layoutParams2.setTitle(charSequence);
        layoutParams2.inputFeatures |= 1;
        return layoutParams2;
    }

    public static class SystemBarBackgroundPainter {
        private final int mNavigationBarColor;
        private final Paint mNavigationBarPaint;
        private final int mRequestedVisibleTypes;
        private final float mScale;
        private final int mStatusBarColor;
        private final Paint mStatusBarPaint;
        private final Rect mSystemBarInsets;
        private final int mWindowFlags;
        private final int mWindowPrivateFlags;

        public SystemBarBackgroundPainter(int i, int i2, int i3, ActivityManager.TaskDescription taskDescription, float f, int i4) {
            Paint paint = new Paint();
            this.mStatusBarPaint = paint;
            Paint paint2 = new Paint();
            this.mNavigationBarPaint = paint2;
            this.mSystemBarInsets = new Rect();
            this.mWindowFlags = i;
            this.mWindowPrivateFlags = i2;
            this.mScale = f;
            Context systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
            int color = systemUiContext.getColor(R.color.system_bar_background_semi_transparent);
            int calculateBarColor = DecorView.calculateBarColor(i, 67108864, color, taskDescription.getStatusBarColor(), i3, 8, taskDescription.getEnsureStatusBarContrastWhenTransparent(), false);
            this.mStatusBarColor = calculateBarColor;
            int calculateBarColor2 = DecorView.calculateBarColor(i, 134217728, color, taskDescription.getNavigationBarColor(), i3, 16, taskDescription.getEnsureNavigationBarContrastWhenTransparent() && systemUiContext.getResources().getBoolean(R.bool.config_navBarNeedsScrim), (i2 & 2048) != 0, taskDescription.getDeviceDefaultNavigationBarColor(systemUiContext));
            this.mNavigationBarColor = calculateBarColor2;
            paint.setColor(calculateBarColor);
            paint2.setColor(calculateBarColor2);
            this.mRequestedVisibleTypes = i4;
        }

        public void setInsets(Rect rect) {
            this.mSystemBarInsets.set(rect);
        }

        int getStatusBarColorViewHeight() {
            if (DecorView.STATUS_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(this.mRequestedVisibleTypes, this.mStatusBarColor, this.mWindowFlags, (this.mWindowPrivateFlags & 32768) != 0)) {
                return (int) (this.mSystemBarInsets.top * this.mScale);
            }
            return 0;
        }

        private boolean isNavigationBarColorViewVisible() {
            return DecorView.NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(this.mRequestedVisibleTypes, this.mNavigationBarColor, this.mWindowFlags, (this.mWindowPrivateFlags & 32768) != 0);
        }

        public void drawDecors(Canvas canvas, Rect rect) {
            drawStatusBarBackground(canvas, rect, getStatusBarColorViewHeight());
            drawNavigationBarBackground(canvas);
        }

        void drawStatusBarBackground(Canvas canvas, Rect rect, int i) {
            if (i <= 0 || Color.alpha(this.mStatusBarColor) == 0) {
                return;
            }
            if (rect == null || canvas.getWidth() > rect.right) {
                canvas.drawRect(rect != null ? rect.right : 0, 0.0f, canvas.getWidth() - ((int) (this.mSystemBarInsets.right * this.mScale)), i, this.mStatusBarPaint);
            }
        }

        void drawNavigationBarBackground(Canvas canvas) {
            Rect rect = new Rect();
            DecorView.getNavigationBarRect(canvas.getWidth(), canvas.getHeight(), this.mSystemBarInsets, rect, this.mScale);
            if (!isNavigationBarColorViewVisible() || Color.alpha(this.mNavigationBarColor) == 0 || rect.isEmpty()) {
                return;
            }
            canvas.drawRect(rect, this.mNavigationBarPaint);
        }
    }
}
