package com.android.server.wm;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManagerGlobal;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayInfo;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;
import android.window.ScreenCapture;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class BlurWallpaperLetterbox {
  public static SparseArray sBlurWallpaperLetterboxArray;
  public SurfaceControl mBlurWallpaperLetterboxSurface;
  public final DisplayContent mDisplay;
  public boolean mShouldUseBlurWallpaperLetterbox;
  public final Runnable mShowBlurWallpaperLetterboxRunnable =
      new Runnable() { // from class:
                       // com.android.server.wm.BlurWallpaperLetterbox$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
          BlurWallpaperLetterbox.this.showBlurWallpaperLetterbox();
        }
      };

  public static boolean shouldUseBlurWallpaperLetterbox() {
    return CustomLetterboxConfiguration.shouldUseCapturedBlurWallpaper();
  }

  public static void onAdjustWallpaperWindows(DisplayContent displayContent, boolean z) {
    BlurWallpaperLetterbox blurWallpaperLetterbox = getBlurWallpaperLetterbox(displayContent);
    if (blurWallpaperLetterbox == null) {
      if (!z || !shouldUseBlurWallpaperLetterbox()) {
        return;
      }
      blurWallpaperLetterbox = new BlurWallpaperLetterbox(displayContent);
      if (sBlurWallpaperLetterboxArray == null) {
        sBlurWallpaperLetterboxArray = new SparseArray();
      }
      sBlurWallpaperLetterboxArray.put(displayContent.mDisplayId, blurWallpaperLetterbox);
    }
    blurWallpaperLetterbox.updateBlurWallpaperLetterbox(z);
  }

  public static void onMoveActivityToPinnedRootTask(
      DisplayContent displayContent, Task task, ActivityRecord activityRecord) {
    WindowState wallpaperTarget = displayContent.mWallpaperController.getWallpaperTarget();
    if (wallpaperTarget == null
        || wallpaperTarget.getTask() != task
        || task.getActivity(
                new Predicate() { // from class:
                                  // com.android.server.wm.BlurWallpaperLetterbox$$ExternalSyntheticLambda2
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean hasWallpaperBackgroundForLetterbox;
                    hasWallpaperBackgroundForLetterbox =
                        CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(
                            (ActivityRecord) obj);
                    return hasWallpaperBackgroundForLetterbox;
                  }
                })
            == null) {
      return;
    }
    Slog.d(
        "BlurWallpaperLetterbox",
        "Move activity with blur wallpaper to PIP. activity=" + activityRecord);
    onAdjustWallpaperWindows(displayContent, false);
  }

  public static void onConfigurationChanged(DisplayContent displayContent) {
    performBlurWallpaperLetterboxIfNonNull(
        displayContent,
        new Consumer() { // from class:
                         // com.android.server.wm.BlurWallpaperLetterbox$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((BlurWallpaperLetterbox) obj).updateBlurWallpaperLetterboxByForceUpdate();
          }
        });
  }

  public static boolean isBlurWallpaperLetterboxActive(DisplayContent displayContent) {
    BlurWallpaperLetterbox blurWallpaperLetterbox = getBlurWallpaperLetterbox(displayContent);
    return (blurWallpaperLetterbox == null
            || blurWallpaperLetterbox.mBlurWallpaperLetterboxSurface == null)
        ? false
        : true;
  }

  public static int getLetterboxWallpaperBlurRadius(DisplayContent displayContent) {
    return (int) getBlurPresetAttrs(displayContent)[0];
  }

  public static SemBlurInfo.ColorCurve getLetterboxWallpaperBlurColorCurve(
      DisplayContent displayContent) {
    float[] blurPresetAttrs = getBlurPresetAttrs(displayContent);
    return new SemBlurInfo.ColorCurve(
        blurPresetAttrs[1],
        blurPresetAttrs[2],
        blurPresetAttrs[3],
        blurPresetAttrs[4],
        blurPresetAttrs[5],
        blurPresetAttrs[6]);
  }

  public static float[] getBlurPresetAttrs(DisplayContent displayContent) {
    return SemBlurInfo.Builder.getBlurPresetAttrs(
        displayContent.getConfiguration().isNightModeActive() ? 16 : 13);
  }

  public static BlurWallpaperLetterbox getBlurWallpaperLetterbox(DisplayContent displayContent) {
    SparseArray sparseArray;
    if (displayContent == null || (sparseArray = sBlurWallpaperLetterboxArray) == null) {
      return null;
    }
    return (BlurWallpaperLetterbox) sparseArray.get(displayContent.mDisplayId);
  }

  public static void performBlurWallpaperLetterboxIfNonNull(
      DisplayContent displayContent, Consumer consumer) {
    BlurWallpaperLetterbox blurWallpaperLetterbox = getBlurWallpaperLetterbox(displayContent);
    if (blurWallpaperLetterbox != null) {
      consumer.accept(blurWallpaperLetterbox);
    }
  }

  public BlurWallpaperLetterbox(DisplayContent displayContent) {
    Objects.requireNonNull(displayContent);
    this.mDisplay = displayContent;
  }

  public final void updateBlurWallpaperLetterboxByForceUpdate() {
    updateBlurWallpaperLetterbox(
        this.mShouldUseBlurWallpaperLetterbox && shouldUseBlurWallpaperLetterbox(), true);
  }

  public final void updateBlurWallpaperLetterbox(boolean z) {
    updateBlurWallpaperLetterbox(z && shouldUseBlurWallpaperLetterbox(), false);
  }

  public final void updateBlurWallpaperLetterbox(boolean z, boolean z2) {
    if (!z2 && z && this.mShouldUseBlurWallpaperLetterbox) {
      return;
    }
    this.mShouldUseBlurWallpaperLetterbox = z;
    removeBlurWallpaperLetterboxSurface();
    if (z) {
      scheduleShowBlurWallpaperLetterbox();
    }
  }

  public final void scheduleShowBlurWallpaperLetterbox() {
    this.mDisplay.mWmService.f1749mH.removeCallbacks(this.mShowBlurWallpaperLetterboxRunnable);
    this.mDisplay.mWmService.f1749mH.postDelayed(this.mShowBlurWallpaperLetterboxRunnable, 5000L);
  }

  public final void showBlurWallpaperLetterbox() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplay.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        removeBlurWallpaperLetterboxSurface();
        if (!this.mShouldUseBlurWallpaperLetterbox) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        Transition collectingTransition =
            this.mDisplay.mTransitionController.getCollectingTransition();
        if (collectingTransition != null && collectingTransition.isOnDisplay(this.mDisplay)) {
          scheduleShowBlurWallpaperLetterbox();
          WindowManagerService.resetPriorityAfterLockedSection();
        } else {
          createBlurWallpaperLetterboxSurface();
          WindowManagerService.resetPriorityAfterLockedSection();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final void createBlurWallpaperLetterboxSurface() {
    if (this.mBlurWallpaperLetterboxSurface != null) {
      throw new IllegalStateException("The surface already exists.");
    }
    WindowState wallpaperTarget = this.mDisplay.mWallpaperController.getWallpaperTarget();
    if (wallpaperTarget == null) {
      Slog.d("BlurWallpaperLetterbox", "There is no wallpaper target.");
      return;
    }
    if (!CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(
        wallpaperTarget.mActivityRecord)) {
      Slog.d("BlurWallpaperLetterbox", "There is no wallpaper background for letterbox.");
      return;
    }
    if (wallpaperTarget.inMultiWindowMode()
        || wallpaperTarget.mActivityRecord.inMultiWindowMode()
        || (wallpaperTarget.getTask() != null && wallpaperTarget.getTask().inMultiWindowMode())) {
      Slog.d("BlurWallpaperLetterbox", "Multi window is not supported.");
      return;
    }
    WindowState topVisibleWallpaper = this.mDisplay.mWallpaperController.getTopVisibleWallpaper();
    if (topVisibleWallpaper == null) {
      Slog.d("BlurWallpaperLetterbox", "There is no wallpaper.");
      return;
    }
    SurfaceControl parentSurfaceControl = topVisibleWallpaper.getParentSurfaceControl();
    if (parentSurfaceControl == null) {
      Slog.d("BlurWallpaperLetterbox", "There is no wallpaper parent.");
      return;
    }
    Slog.d("BlurWallpaperLetterbox", "createBlurWallpaperLetterboxSurface");
    SurfaceControl.Transaction transaction =
        (SurfaceControl.Transaction) this.mDisplay.mWmService.mTransactionFactory.get();
    SurfaceControl createCapturedBlurWallpaperSurface =
        createCapturedBlurWallpaperSurface(
            transaction,
            wallpaperTarget.mActivityRecord.mLetterboxUiController,
            parentSurfaceControl);
    this.mBlurWallpaperLetterboxSurface = createCapturedBlurWallpaperSurface;
    if (createCapturedBlurWallpaperSurface == null) {
      return;
    }
    transaction.reparent(createCapturedBlurWallpaperSurface, parentSurfaceControl);
    transaction.setLayer(this.mBlurWallpaperLetterboxSurface, 1);
    transaction.show(this.mBlurWallpaperLetterboxSurface);
    updateLetterboxSurfaces(transaction);
    transaction.apply();
  }

  public final SurfaceControl createCapturedBlurWallpaperSurface(
      SurfaceControl.Transaction transaction,
      LetterboxUiController letterboxUiController,
      SurfaceControl surfaceControl) {
    if (!letterboxUiController.reparentToWallpaperParentImmediately(transaction, surfaceControl)) {
      Slog.w("BlurWallpaperLetterbox", "There is no letterbox.");
      return null;
    }
    Rect bounds = this.mDisplay.getBounds();
    Rect rect = new Rect();
    DisplayInfo displayInfo =
        DisplayManagerGlobal.getInstance().getDisplayInfo(this.mDisplay.getDisplayId());
    ScreenCapture.ScreenshotHardwareBuffer captureDisplay =
        ScreenCapture.captureDisplay(
            new ScreenCapture.DisplayCaptureArgs.Builder(
                    displayInfo != null
                        ? SurfaceControl.getDisplayToken(displayInfo.address)
                        : null)
                .setUseIdentityTransform(false)
                .setSourceCrop(rect)
                .setSize(bounds.width(), bounds.height())
                .setLayer(surfaceControl)
                .setCaptureSecureLayers(true)
                .build());
    if (captureDisplay == null) {
      Slog.w("BlurWallpaperLetterbox", "There is no screenshot buffer.");
      return null;
    }
    HardwareBuffer hardwareBuffer = captureDisplay.getHardwareBuffer();
    if (hardwareBuffer == null) {
      Slog.w("BlurWallpaperLetterbox", "There is no hardware buffer.");
      return null;
    }
    SurfaceControl build =
        new SurfaceControl.Builder()
            .setName("BlurWallpaperLetterbox")
            .setOpaque(true)
            .setFormat(hardwareBuffer.getFormat())
            .setSecure(captureDisplay.containsSecureLayers())
            .setCallsite("createCapturedBlurWallpaperSurface")
            .setBLASTLayer()
            .build();
    transaction.setBuffer(build, hardwareBuffer);
    return build;
  }

  public final void removeBlurWallpaperLetterboxSurface() {
    if (this.mBlurWallpaperLetterboxSurface == null) {
      return;
    }
    Slog.d("BlurWallpaperLetterbox", "removeBlurWallpaperLetterboxSurface");
    SurfaceControl.Transaction transaction =
        (SurfaceControl.Transaction) this.mDisplay.mWmService.mTransactionFactory.get();
    transaction.reparent(this.mBlurWallpaperLetterboxSurface, null);
    transaction.remove(this.mBlurWallpaperLetterboxSurface);
    this.mBlurWallpaperLetterboxSurface = null;
    updateLetterboxSurfaces(transaction);
    transaction.apply();
  }

  public final void updateLetterboxSurfaces(final SurfaceControl.Transaction transaction) {
    this.mDisplay.mWmService.mRoot.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.BlurWallpaperLetterbox$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            BlurWallpaperLetterbox.lambda$updateLetterboxSurfaces$2(
                transaction, (ActivityRecord) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$updateLetterboxSurfaces$2(
      SurfaceControl.Transaction transaction, ActivityRecord activityRecord) {
    activityRecord.mLetterboxUiController.updateLetterboxSurface(transaction);
  }
}
