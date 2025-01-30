package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.RoundedCorner;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class LetterboxUiController {
  public static final Predicate FIRST_OPAQUE_NOT_FINISHING_ACTIVITY_PREDICATE =
      new Predicate() { // from class:
                        // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda0
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
          boolean lambda$static$0;
          lambda$static$0 = LetterboxUiController.lambda$static$0((ActivityRecord) obj);
          return lambda$static$0;
        }
      };
  static final int MIN_COUNT_TO_IGNORE_REQUEST_IN_LOOP = 2;
  static final int SET_ORIENTATION_REQUEST_COUNTER_TIMEOUT_MS = 1000;
  public final ActivityRecord mActivityRecord;
  public final Boolean mBooleanPropertyAllowDisplayOrientationOverride;
  public final Boolean mBooleanPropertyAllowForceResizeOverride;
  public final Boolean mBooleanPropertyAllowIgnoringOrientationRequestWhenLoopDetected;
  public final Boolean mBooleanPropertyAllowMinAspectRatioOverride;
  public final Boolean mBooleanPropertyAllowOrientationOverride;
  public final Boolean mBooleanPropertyCameraCompatAllowForceRotation;
  public final Boolean mBooleanPropertyCameraCompatAllowRefresh;
  public final Boolean mBooleanPropertyCameraCompatEnableRefreshViaPause;
  public final Boolean mBooleanPropertyFakeFocus;
  public final Boolean mBooleanPropertyIgnoreRequestedOrientation;
  public boolean mDoubleTapEvent;
  ActivityRecord mFirstOpaqueActivityBeneath;
  public ActivityRecord.CompatDisplayInsets mInheritedCompatDisplayInsets;
  public final boolean mIsOverrideAnyOrientationEnabled;
  public final boolean mIsOverrideCameraCompatDisableForceRotationEnabled;
  public final boolean mIsOverrideCameraCompatDisableRefreshEnabled;
  public final boolean mIsOverrideCameraCompatEnableRefreshViaPauseEnabled;
  public final boolean mIsOverrideEnableCompatFakeFocusEnabled;

  /* renamed from: mIsOverrideEnableCompatIgnoreOrientationRequestWhenLoopDetectedEnabled */
  public final boolean f1742xf34fdb28;
  public final boolean mIsOverrideEnableCompatIgnoreRequestedOrientationEnabled;
  public final boolean mIsOverrideForceNonResizeApp;
  public final boolean mIsOverrideForceResizeApp;
  public final boolean mIsOverrideMinAspectRatio;
  public final boolean mIsOverrideOrientationOnlyForCameraEnabled;
  public final boolean mIsOverrideRespectRequestedOrientationEnabled;
  public final boolean mIsOverrideToNosensorOrientationEnabled;
  public final boolean mIsOverrideToPortraitOrientationEnabled;
  public final boolean mIsOverrideToReverseLandscapeOrientationEnabled;
  public final boolean mIsOverrideUseDisplayLandscapeNaturalOrientationEnabled;
  public boolean mIsRefreshAfterRotationRequested;
  public boolean mIsRelaunchingAfterRequestedOrientationChanged;
  public boolean mLastShouldShowLetterboxDummy;
  public boolean mLastShouldShowLetterboxUi;
  public Letterbox mLetterbox;
  public WindowContainerListener mLetterboxConfigListener;
  public final LetterboxConfiguration mLetterboxConfiguration;
  public Letterbox mLetterboxDummy;
  public boolean mShowWallpaperForLetterboxBackground;
  public final Point mTmpPoint = new Point();
  public final List mDestroyListeners = new ArrayList();
  public float mInheritedMinAspectRatio = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public float mInheritedMaxAspectRatio = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  public long mTimeMsLastSetOrientationRequest = 0;
  public int mInheritedOrientation = 0;
  public int mInheritedAppCompatState = 0;
  public int mSetOrientationRequestCounter = 0;
  public Runnable mOnSingleTap =
      new Runnable() { // from class:
                       // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda10
        @Override // java.lang.Runnable
        public final void run() {
          LetterboxUiController.this.onSingleTap();
        }
      };

  public static /* synthetic */ boolean lambda$shouldOverrideForceNonResizeApp$6() {
    return true;
  }

  public static /* synthetic */ boolean lambda$shouldOverrideForceResizeApp$5() {
    return true;
  }

  public static /* synthetic */ boolean lambda$shouldOverrideMinAspectRatio$4() {
    return true;
  }

  public static /* synthetic */ boolean lambda$static$0(ActivityRecord activityRecord) {
    return activityRecord.fillsParent() && !activityRecord.isFinishing();
  }

  public LetterboxUiController(
      WindowManagerService windowManagerService, ActivityRecord activityRecord) {
    LetterboxConfiguration letterboxConfiguration =
        CoreRune.FW_CUSTOM_LETTERBOX
            ? new LetterboxConfiguration(
                windowManagerService.mLetterboxConfiguration, activityRecord)
            : windowManagerService.mLetterboxConfiguration;
    this.mLetterboxConfiguration = letterboxConfiguration;
    this.mActivityRecord = activityRecord;
    PackageManager packageManager = windowManagerService.mContext.getPackageManager();
    String str = activityRecord.packageName;
    Objects.requireNonNull(letterboxConfiguration);
    this.mBooleanPropertyIgnoreRequestedOrientation =
        readComponentProperty(
            packageManager,
            str,
            new LetterboxUiController$$ExternalSyntheticLambda9(letterboxConfiguration),
            "android.window.PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION");
    String str2 = activityRecord.packageName;
    Objects.requireNonNull(letterboxConfiguration);
    this.mBooleanPropertyAllowIgnoringOrientationRequestWhenLoopDetected =
        readComponentProperty(
            packageManager,
            str2,
            new LetterboxUiController$$ExternalSyntheticLambda9(letterboxConfiguration),
            "android.window.PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED");
    String str3 = activityRecord.packageName;
    Objects.requireNonNull(letterboxConfiguration);
    this.mBooleanPropertyFakeFocus =
        readComponentProperty(
            packageManager,
            str3,
            new LetterboxUiController$$ExternalSyntheticLambda11(letterboxConfiguration),
            "android.window.PROPERTY_COMPAT_ENABLE_FAKE_FOCUS");
    this.mBooleanPropertyCameraCompatAllowForceRotation =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            new BooleanSupplier() { // from class:
                                    // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda12
              @Override // java.util.function.BooleanSupplier
              public final boolean getAsBoolean() {
                boolean lambda$new$1;
                lambda$new$1 = LetterboxUiController.this.lambda$new$1();
                return lambda$new$1;
              }
            },
            "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_FORCE_ROTATION");
    this.mBooleanPropertyCameraCompatAllowRefresh =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            new BooleanSupplier() { // from class:
                                    // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda13
              @Override // java.util.function.BooleanSupplier
              public final boolean getAsBoolean() {
                boolean lambda$new$2;
                lambda$new$2 = LetterboxUiController.this.lambda$new$2();
                return lambda$new$2;
              }
            },
            "android.window.PROPERTY_CAMERA_COMPAT_ALLOW_REFRESH");
    this.mBooleanPropertyCameraCompatEnableRefreshViaPause =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            new BooleanSupplier() { // from class:
                                    // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda14
              @Override // java.util.function.BooleanSupplier
              public final boolean getAsBoolean() {
                boolean lambda$new$3;
                lambda$new$3 = LetterboxUiController.this.lambda$new$3();
                return lambda$new$3;
              }
            },
            "android.window.PROPERTY_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE");
    this.mBooleanPropertyAllowOrientationOverride =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            null,
            "android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE");
    this.mBooleanPropertyAllowDisplayOrientationOverride =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            null,
            "android.window.PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE");
    this.mBooleanPropertyAllowMinAspectRatioOverride =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            null,
            "android.window.PROPERTY_COMPAT_ALLOW_MIN_ASPECT_RATIO_OVERRIDE");
    this.mBooleanPropertyAllowForceResizeOverride =
        readComponentProperty(
            packageManager,
            activityRecord.packageName,
            null,
            "android.window.PROPERTY_COMPAT_ALLOW_RESIZEABLE_ACTIVITY_OVERRIDES");
    this.mIsOverrideAnyOrientationEnabled = isCompatChangeEnabled(265464455L);
    this.mIsOverrideToPortraitOrientationEnabled = isCompatChangeEnabled(265452344L);
    this.mIsOverrideToReverseLandscapeOrientationEnabled = isCompatChangeEnabled(266124927L);
    this.mIsOverrideToNosensorOrientationEnabled = isCompatChangeEnabled(265451093L);
    this.mIsOverrideOrientationOnlyForCameraEnabled = isCompatChangeEnabled(265456536L);
    this.mIsOverrideUseDisplayLandscapeNaturalOrientationEnabled =
        isCompatChangeEnabled(255940284L);
    this.mIsOverrideRespectRequestedOrientationEnabled = isCompatChangeEnabled(236283604L);
    this.mIsOverrideCameraCompatDisableForceRotationEnabled = isCompatChangeEnabled(263959004L);
    this.mIsOverrideCameraCompatDisableRefreshEnabled = isCompatChangeEnabled(264304459L);
    this.mIsOverrideCameraCompatEnableRefreshViaPauseEnabled = isCompatChangeEnabled(264301586L);
    this.mIsOverrideEnableCompatIgnoreRequestedOrientationEnabled =
        isCompatChangeEnabled(254631730L);
    this.f1742xf34fdb28 = isCompatChangeEnabled(273509367L);
    this.mIsOverrideEnableCompatFakeFocusEnabled = isCompatChangeEnabled(263259275L);
    this.mIsOverrideMinAspectRatio = isCompatChangeEnabled(174042980L);
    this.mIsOverrideForceResizeApp = isCompatChangeEnabled(174042936L);
    this.mIsOverrideForceNonResizeApp = isCompatChangeEnabled(181136395L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$new$1() {
    return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(true);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$new$2() {
    return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(true);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$new$3() {
    return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(true);
  }

  public static Boolean readComponentProperty(
      PackageManager packageManager, String str, BooleanSupplier booleanSupplier, String str2) {
    if (booleanSupplier != null && !booleanSupplier.getAsBoolean()) {
      return null;
    }
    try {
      return Boolean.valueOf(packageManager.getProperty(str2, str).getBoolean());
    } catch (PackageManager.NameNotFoundException unused) {
      return null;
    }
  }

  public void destroy() {
    Letterbox letterbox = this.mLetterbox;
    if (letterbox != null) {
      letterbox.destroy();
      this.mLetterbox = null;
    }
    Letterbox letterbox2 = this.mLetterboxDummy;
    if (letterbox2 != null) {
      letterbox2.destroy();
      this.mLetterboxDummy = null;
    }
    for (int size = this.mDestroyListeners.size() - 1; size >= 0; size--) {
      ((LetterboxUiController) this.mDestroyListeners.get(size)).updateInheritedLetterbox();
    }
    this.mDestroyListeners.clear();
    WindowContainerListener windowContainerListener = this.mLetterboxConfigListener;
    if (windowContainerListener != null) {
      windowContainerListener.onRemoved();
      this.mLetterboxConfigListener = null;
    }
  }

  public void onMovedToDisplay(int i) {
    Letterbox letterbox = this.mLetterbox;
    if (letterbox != null) {
      letterbox.onMovedToDisplay(i);
    }
  }

  public boolean shouldIgnoreRequestedOrientation(int i) {
    LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
    Objects.requireNonNull(letterboxConfiguration);
    if (shouldEnableWithOverrideAndProperty(
        new LetterboxUiController$$ExternalSyntheticLambda9(letterboxConfiguration),
        this.mIsOverrideEnableCompatIgnoreRequestedOrientationEnabled,
        this.mBooleanPropertyIgnoreRequestedOrientation)) {
      if (this.mIsRelaunchingAfterRequestedOrientationChanged) {
        Slog.w(
            "ActivityTaskManager",
            "Ignoring orientation update to "
                + ActivityInfo.screenOrientationToString(i)
                + " due to relaunching after setRequestedOrientation for "
                + this.mActivityRecord);
        return true;
      }
      if (isCameraCompatTreatmentActive()) {
        Slog.w(
            "ActivityTaskManager",
            "Ignoring orientation update to "
                + ActivityInfo.screenOrientationToString(i)
                + " due to camera compat treatment for "
                + this.mActivityRecord);
        return true;
      }
    }
    if (!shouldIgnoreOrientationRequestLoop()) {
      return false;
    }
    Slog.w(
        "ActivityTaskManager",
        "Ignoring orientation update to "
            + ActivityInfo.screenOrientationToString(i)
            + " as orientation request loop was detected for "
            + this.mActivityRecord);
    return true;
  }

  public boolean shouldIgnoreOrientationRequestLoop() {
    LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
    Objects.requireNonNull(letterboxConfiguration);
    if (!shouldEnableWithOptInOverrideAndOptOutProperty(
        new LetterboxUiController$$ExternalSyntheticLambda9(letterboxConfiguration),
        this.f1742xf34fdb28,
        this.mBooleanPropertyAllowIgnoringOrientationRequestWhenLoopDetected)) {
      return false;
    }
    long currentTimeMillis = System.currentTimeMillis();
    if (currentTimeMillis - this.mTimeMsLastSetOrientationRequest < 1000) {
      this.mSetOrientationRequestCounter++;
    } else {
      this.mSetOrientationRequestCounter = 0;
    }
    this.mTimeMsLastSetOrientationRequest = currentTimeMillis;
    return this.mSetOrientationRequestCounter >= 2
        && !this.mActivityRecord.isLetterboxedForFixedOrientationAndAspectRatio();
  }

  public int getSetOrientationRequestCounter() {
    return this.mSetOrientationRequestCounter;
  }

  public boolean shouldSendFakeFocus() {
    LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
    Objects.requireNonNull(letterboxConfiguration);
    return shouldEnableWithOverrideAndProperty(
        new LetterboxUiController$$ExternalSyntheticLambda11(letterboxConfiguration),
        this.mIsOverrideEnableCompatFakeFocusEnabled,
        this.mBooleanPropertyFakeFocus);
  }

  public boolean shouldOverrideMinAspectRatio() {
    return shouldEnableWithOptInOverrideAndOptOutProperty(
        new BooleanSupplier() { // from class:
                                // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda7
          @Override // java.util.function.BooleanSupplier
          public final boolean getAsBoolean() {
            boolean lambda$shouldOverrideMinAspectRatio$4;
            lambda$shouldOverrideMinAspectRatio$4 =
                LetterboxUiController.lambda$shouldOverrideMinAspectRatio$4();
            return lambda$shouldOverrideMinAspectRatio$4;
          }
        },
        this.mIsOverrideMinAspectRatio,
        this.mBooleanPropertyAllowMinAspectRatioOverride);
  }

  public boolean shouldOverrideForceResizeApp() {
    return shouldEnableWithOptInOverrideAndOptOutProperty(
        new BooleanSupplier() { // from class:
                                // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda2
          @Override // java.util.function.BooleanSupplier
          public final boolean getAsBoolean() {
            boolean lambda$shouldOverrideForceResizeApp$5;
            lambda$shouldOverrideForceResizeApp$5 =
                LetterboxUiController.lambda$shouldOverrideForceResizeApp$5();
            return lambda$shouldOverrideForceResizeApp$5;
          }
        },
        this.mIsOverrideForceResizeApp,
        this.mBooleanPropertyAllowForceResizeOverride);
  }

  public boolean shouldOverrideForceNonResizeApp() {
    return shouldEnableWithOptInOverrideAndOptOutProperty(
        new BooleanSupplier() { // from class:
                                // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda1
          @Override // java.util.function.BooleanSupplier
          public final boolean getAsBoolean() {
            boolean lambda$shouldOverrideForceNonResizeApp$6;
            lambda$shouldOverrideForceNonResizeApp$6 =
                LetterboxUiController.lambda$shouldOverrideForceNonResizeApp$6();
            return lambda$shouldOverrideForceNonResizeApp$6;
          }
        },
        this.mIsOverrideForceNonResizeApp,
        this.mBooleanPropertyAllowForceResizeOverride);
  }

  public void setRelaunchingAfterRequestedOrientationChanged(boolean z) {
    this.mIsRelaunchingAfterRequestedOrientationChanged = z;
  }

  public boolean isRefreshAfterRotationRequested() {
    return this.mIsRefreshAfterRotationRequested;
  }

  public void setIsRefreshAfterRotationRequested(boolean z) {
    this.mIsRefreshAfterRotationRequested = z;
  }

  public boolean isOverrideRespectRequestedOrientationEnabled() {
    return this.mIsOverrideRespectRequestedOrientationEnabled;
  }

  public boolean shouldUseDisplayLandscapeNaturalOrientation() {
    return shouldEnableWithOptInOverrideAndOptOutProperty(
        new BooleanSupplier() { // from class:
                                // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda8
          @Override // java.util.function.BooleanSupplier
          public final boolean getAsBoolean() {
            boolean lambda$shouldUseDisplayLandscapeNaturalOrientation$7;
            lambda$shouldUseDisplayLandscapeNaturalOrientation$7 =
                LetterboxUiController.this.lambda$shouldUseDisplayLandscapeNaturalOrientation$7();
            return lambda$shouldUseDisplayLandscapeNaturalOrientation$7;
          }
        },
        this.mIsOverrideUseDisplayLandscapeNaturalOrientationEnabled,
        this.mBooleanPropertyAllowDisplayOrientationOverride);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$shouldUseDisplayLandscapeNaturalOrientation$7() {
    ActivityRecord activityRecord = this.mActivityRecord;
    return (activityRecord.mDisplayContent == null
            || activityRecord.getTask() == null
            || !this.mActivityRecord.mDisplayContent.getIgnoreOrientationRequest()
            || this.mActivityRecord.getTask().inMultiWindowMode()
            || this.mActivityRecord.mDisplayContent.getNaturalOrientation() != 2)
        ? false
        : true;
  }

  public int overrideOrientationIfNeeded(int i) {
    DisplayRotationCompatPolicy displayRotationCompatPolicy;
    int mapOrientationRequest = this.mActivityRecord.mWmService.mapOrientationRequest(i);
    if (Boolean.FALSE.equals(this.mBooleanPropertyAllowOrientationOverride)) {
      return mapOrientationRequest;
    }
    ActivityRecord activityRecord = this.mActivityRecord;
    DisplayContent displayContent = activityRecord.mDisplayContent;
    if (this.mIsOverrideOrientationOnlyForCameraEnabled
        && displayContent != null
        && ((displayRotationCompatPolicy = displayContent.mDisplayRotationCompatPolicy) == null
            || !displayRotationCompatPolicy.isActivityEligibleForOrientationOverride(
                activityRecord))) {
      return mapOrientationRequest;
    }
    if (this.mIsOverrideToReverseLandscapeOrientationEnabled
        && (ActivityInfo.isFixedOrientationLandscape(mapOrientationRequest)
            || this.mIsOverrideAnyOrientationEnabled)) {
      Slog.w(
          "ActivityTaskManager",
          "Requested orientation  "
              + ActivityInfo.screenOrientationToString(mapOrientationRequest)
              + " for "
              + this.mActivityRecord
              + " is overridden to "
              + ActivityInfo.screenOrientationToString(8));
      return 8;
    }
    if (!this.mIsOverrideAnyOrientationEnabled
        && ActivityInfo.isFixedOrientation(mapOrientationRequest)) {
      return mapOrientationRequest;
    }
    if (this.mIsOverrideToPortraitOrientationEnabled) {
      Slog.w(
          "ActivityTaskManager",
          "Requested orientation  "
              + ActivityInfo.screenOrientationToString(mapOrientationRequest)
              + " for "
              + this.mActivityRecord
              + " is overridden to "
              + ActivityInfo.screenOrientationToString(1));
      return 1;
    }
    if (!this.mIsOverrideToNosensorOrientationEnabled) {
      return mapOrientationRequest;
    }
    Slog.w(
        "ActivityTaskManager",
        "Requested orientation  "
            + ActivityInfo.screenOrientationToString(mapOrientationRequest)
            + " for "
            + this.mActivityRecord
            + " is overridden to "
            + ActivityInfo.screenOrientationToString(5));
    return 5;
  }

  public boolean isOverrideOrientationOnlyForCameraEnabled() {
    return this.mIsOverrideOrientationOnlyForCameraEnabled;
  }

  public boolean shouldRefreshActivityForCameraCompat() {
    return shouldEnableWithOptOutOverrideAndProperty(
        new BooleanSupplier() { // from class:
                                // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda5
          @Override // java.util.function.BooleanSupplier
          public final boolean getAsBoolean() {
            boolean lambda$shouldRefreshActivityForCameraCompat$8;
            lambda$shouldRefreshActivityForCameraCompat$8 =
                LetterboxUiController.this.lambda$shouldRefreshActivityForCameraCompat$8();
            return lambda$shouldRefreshActivityForCameraCompat$8;
          }
        },
        this.mIsOverrideCameraCompatDisableRefreshEnabled,
        this.mBooleanPropertyCameraCompatAllowRefresh);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$shouldRefreshActivityForCameraCompat$8() {
    return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(true);
  }

  public boolean shouldRefreshActivityViaPauseForCameraCompat() {
    return shouldEnableWithOverrideAndProperty(
        new BooleanSupplier() { // from class:
                                // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda3
          @Override // java.util.function.BooleanSupplier
          public final boolean getAsBoolean() {
            boolean lambda$shouldRefreshActivityViaPauseForCameraCompat$9;
            lambda$shouldRefreshActivityViaPauseForCameraCompat$9 =
                LetterboxUiController.this.lambda$shouldRefreshActivityViaPauseForCameraCompat$9();
            return lambda$shouldRefreshActivityViaPauseForCameraCompat$9;
          }
        },
        this.mIsOverrideCameraCompatEnableRefreshViaPauseEnabled,
        this.mBooleanPropertyCameraCompatEnableRefreshViaPause);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$shouldRefreshActivityViaPauseForCameraCompat$9() {
    return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(true);
  }

  public boolean shouldForceRotateForCameraCompat() {
    if (!CoreRune.FW_ORIENTATION_CONTROL_WITH_CAMERA_COMPAT
        || OrientationController.isEnabled(this.mActivityRecord.getTask())) {
      return shouldEnableWithOptOutOverrideAndProperty(
          new BooleanSupplier() { // from class:
                                  // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda27
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
              boolean lambda$shouldForceRotateForCameraCompat$10;
              lambda$shouldForceRotateForCameraCompat$10 =
                  LetterboxUiController.this.lambda$shouldForceRotateForCameraCompat$10();
              return lambda$shouldForceRotateForCameraCompat$10;
            }
          },
          this.mIsOverrideCameraCompatDisableForceRotationEnabled,
          this.mBooleanPropertyCameraCompatAllowForceRotation);
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$shouldForceRotateForCameraCompat$10() {
    return this.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(true);
  }

  public final boolean isCameraCompatTreatmentActive() {
    DisplayRotationCompatPolicy displayRotationCompatPolicy;
    ActivityRecord activityRecord = this.mActivityRecord;
    DisplayContent displayContent = activityRecord.mDisplayContent;
    return (displayContent == null
            || (displayRotationCompatPolicy = displayContent.mDisplayRotationCompatPolicy) == null
            || !displayRotationCompatPolicy.isTreatmentEnabledForActivity(activityRecord))
        ? false
        : true;
  }

  public final boolean isCompatChangeEnabled(long j) {
    return this.mActivityRecord.info.isChangeEnabled(j);
  }

  public final boolean shouldEnableWithOptOutOverrideAndProperty(
      BooleanSupplier booleanSupplier, boolean z, Boolean bool) {
    return (!booleanSupplier.getAsBoolean() || Boolean.FALSE.equals(bool) || z) ? false : true;
  }

  public final boolean shouldEnableWithOptInOverrideAndOptOutProperty(
      BooleanSupplier booleanSupplier, boolean z, Boolean bool) {
    return booleanSupplier.getAsBoolean() && !Boolean.FALSE.equals(bool) && z;
  }

  public final boolean shouldEnableWithOverrideAndProperty(
      BooleanSupplier booleanSupplier, boolean z, Boolean bool) {
    if (booleanSupplier.getAsBoolean() && !Boolean.FALSE.equals(bool)) {
      return Boolean.TRUE.equals(bool) || z;
    }
    return false;
  }

  public boolean hasWallpaperBackgroundForLetterbox() {
    if (CoreRune.FW_CUSTOM_LETTERBOX
        && CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(this.mActivityRecord)) {
      return true;
    }
    return this.mShowWallpaperForLetterboxBackground;
  }

  public Rect getLetterboxInsets() {
    if (this.mLetterboxDummy != null && shouldShowLetterboxDummy()) {
      return this.mLetterboxDummy.getInsets();
    }
    Letterbox letterbox = this.mLetterbox;
    if (letterbox != null) {
      return letterbox.getInsets();
    }
    return new Rect();
  }

  public void getLetterboxInnerBounds(Rect rect) {
    Letterbox letterbox = this.mLetterbox;
    if (letterbox != null) {
      rect.set(letterbox.getInnerFrame());
      WindowState findMainWindow = this.mActivityRecord.findMainWindow();
      if (findMainWindow != null) {
        adjustBoundsForTaskbar(findMainWindow, rect);
        return;
      }
      return;
    }
    rect.setEmpty();
  }

  public final void getLetterboxOuterBounds(Rect rect) {
    Letterbox letterbox = this.mLetterbox;
    if (letterbox != null) {
      rect.set(letterbox.getOuterFrame());
    } else {
      rect.setEmpty();
    }
  }

  public boolean isFullyTransparentBarAllowed(Rect rect) {
    Letterbox letterbox = this.mLetterbox;
    return letterbox == null || letterbox.notIntersectsOrFullyContains(rect);
  }

  public void updateLetterboxSurface(WindowState windowState) {
    updateLetterboxSurface(windowState, this.mActivityRecord.getSyncTransaction());
  }

  public void updateLetterboxSurface(
      WindowState windowState, SurfaceControl.Transaction transaction) {
    WindowState findMainWindow = this.mActivityRecord.findMainWindow();
    if (findMainWindow == windowState || windowState == null || findMainWindow == null) {
      layoutLetterbox(windowState);
      Letterbox letterbox = this.mLetterbox;
      if (letterbox == null || !letterbox.needsApplySurfaceChanges()) {
        return;
      }
      this.mLetterbox.applySurfaceChanges(transaction);
    }
  }

  public void updateLetterboxSurface(SurfaceControl.Transaction transaction) {
    Letterbox letterbox = this.mLetterbox;
    if (letterbox != null) {
      letterbox.clearSurfaceFrame();
    }
    if (transaction == null || !this.mActivityRecord.isVisibleRequested()) {
      return;
    }
    layoutLetterbox(null);
    Letterbox letterbox2 = this.mLetterbox;
    if (letterbox2 == null || !letterbox2.needsApplySurfaceChanges()) {
      return;
    }
    this.mLetterbox.applySurfaceChanges(transaction);
  }

  public void layoutLetterbox(WindowState windowState) {
    WindowState findMainWindow = this.mActivityRecord.findMainWindow();
    if (findMainWindow != null) {
      if (windowState == null || findMainWindow == windowState) {
        updateRoundedCornersIfNeeded(findMainWindow);
        WindowState findMainWindow2 = this.mActivityRecord.findMainWindow(false);
        if (findMainWindow2 != null && findMainWindow2 != findMainWindow) {
          updateRoundedCornersIfNeeded(findMainWindow2);
        }
        updateWallpaperForLetterbox(findMainWindow);
        if (shouldShowLetterboxUi(findMainWindow)) {
          if (this.mLetterbox == null) {
            Letterbox letterbox =
                new Letterbox(
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda15
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        SurfaceControl.Builder lambda$layoutLetterbox$11;
                        lambda$layoutLetterbox$11 =
                            LetterboxUiController.this.lambda$layoutLetterbox$11();
                        return lambda$layoutLetterbox$11;
                      }
                    },
                    this.mActivityRecord.mWmService.mTransactionFactory,
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda18
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        boolean shouldLetterboxHaveRoundedCorners;
                        shouldLetterboxHaveRoundedCorners =
                            LetterboxUiController.this.shouldLetterboxHaveRoundedCorners();
                        return Boolean.valueOf(shouldLetterboxHaveRoundedCorners);
                      }
                    },
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda19
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        Color letterboxBackgroundColor;
                        letterboxBackgroundColor =
                            LetterboxUiController.this.getLetterboxBackgroundColor();
                        return letterboxBackgroundColor;
                      }
                    },
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda20
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        return Boolean.valueOf(
                            LetterboxUiController.this.hasWallpaperBackgroundForLetterbox());
                      }
                    },
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda21
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        int letterboxWallpaperBlurRadius;
                        letterboxWallpaperBlurRadius =
                            LetterboxUiController.this.getLetterboxWallpaperBlurRadius();
                        return Integer.valueOf(letterboxWallpaperBlurRadius);
                      }
                    },
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda22
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        float letterboxWallpaperDarkScrimAlpha;
                        letterboxWallpaperDarkScrimAlpha =
                            LetterboxUiController.this.getLetterboxWallpaperDarkScrimAlpha();
                        return Float.valueOf(letterboxWallpaperDarkScrimAlpha);
                      }
                    },
                    new IntConsumer() { // from class:
                                        // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda23
                      @Override // java.util.function.IntConsumer
                      public final void accept(int i) {
                        LetterboxUiController.this.handleHorizontalDoubleTap(i);
                      }
                    },
                    new IntConsumer() { // from class:
                                        // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda24
                      @Override // java.util.function.IntConsumer
                      public final void accept(int i) {
                        LetterboxUiController.this.handleVerticalDoubleTap(i);
                      }
                    },
                    new Supplier() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda25
                      @Override // java.util.function.Supplier
                      public final Object get() {
                        return LetterboxUiController.this.getLetterboxParentSurface();
                      }
                    });
            this.mLetterbox = letterbox;
            letterbox.attachInput(findMainWindow);
          }
          if (this.mActivityRecord.isInLetterboxAnimation()) {
            this.mActivityRecord.getTask().getPosition(this.mTmpPoint);
          } else {
            this.mActivityRecord.getPosition(this.mTmpPoint);
          }
          Rect fixedRotationTransformDisplayBounds =
              this.mActivityRecord.getFixedRotationTransformDisplayBounds();
          if (fixedRotationTransformDisplayBounds == null) {
            if (this.mActivityRecord.inMultiWindowMode()) {
              fixedRotationTransformDisplayBounds =
                  this.mActivityRecord.getTaskFragment().getBounds();
            } else {
              fixedRotationTransformDisplayBounds =
                  this.mActivityRecord.getRootTask().getParent().getBounds();
            }
          }
          if (CoreRune.MW_EMBED_ACTIVITY && this.mActivityRecord.isEmbedded()) {
            fixedRotationTransformDisplayBounds.set(
                this.mActivityRecord.getTaskFragment().getBounds());
          }
          if (CoreRune.FW_CUSTOM_LETTERBOX) {
            this.mLetterbox.mCanHaveLetterboxSurfaceSupplier =
                new Supplier() { // from class:
                                 // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda26
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    boolean canHaveLetterboxSurface;
                    canHaveLetterboxSurface = LetterboxUiController.this.canHaveLetterboxSurface();
                    return Boolean.valueOf(canHaveLetterboxSurface);
                  }
                };
          }
          if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX) {
            this.mLetterbox.mBlurColorCurveSupplier =
                new Supplier() { // from class:
                                 // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda16
                  @Override // java.util.function.Supplier
                  public final Object get() {
                    SemBlurInfo.ColorCurve letterboxWallpaperBlurColorCurve;
                    letterboxWallpaperBlurColorCurve =
                        LetterboxUiController.this.getLetterboxWallpaperBlurColorCurve();
                    return letterboxWallpaperBlurColorCurve;
                  }
                };
          }
          if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE) {
            this.mLetterbox.mSingleTapCallback =
                new IntConsumer() { // from class:
                                    // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda17
                  @Override // java.util.function.IntConsumer
                  public final void accept(int i) {
                    LetterboxUiController.this.handleSingleTap(i);
                  }
                };
          }
          this.mLetterbox.layout(
              fixedRotationTransformDisplayBounds,
              hasInheritedLetterboxBehavior()
                  ? this.mActivityRecord.getBounds()
                  : findMainWindow.getFrame(),
              this.mTmpPoint);
          this.mActivityRecord.getTask().dispatchTaskInfoChangedIfNeeded(true);
        } else {
          Letterbox letterbox2 = this.mLetterbox;
          if (letterbox2 != null) {
            letterbox2.hide();
          }
        }
        layoutLetterboxDummyIfNeeded(findMainWindow);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ SurfaceControl.Builder lambda$layoutLetterbox$11() {
    return this.mActivityRecord.makeChildSurface(null);
  }

  public boolean isFromDoubleTap() {
    boolean z = this.mDoubleTapEvent;
    this.mDoubleTapEvent = false;
    return z;
  }

  public SurfaceControl getLetterboxParentSurface() {
    if (this.mActivityRecord.isInLetterboxAnimation()) {
      return this.mActivityRecord.getTask().getSurfaceControl();
    }
    return this.mActivityRecord.getSurfaceControl();
  }

  public final boolean shouldLetterboxHaveRoundedCorners() {
    return this.mLetterboxConfiguration.isLetterboxActivityCornersRounded()
        && this.mActivityRecord.fillsParent();
  }

  public final boolean isDisplayFullScreenAndInPosture(
      DeviceStateController.DeviceState deviceState, boolean z) {
    Task task = this.mActivityRecord.getTask();
    DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
    return displayContent != null
        && displayContent.getDisplayRotation().isDeviceInPosture(deviceState, z)
        && task != null
        && task.getWindowingMode() == 1;
  }

  public final boolean isDisplayFullScreenAndSeparatingHinge() {
    Task task = this.mActivityRecord.getTask();
    DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
    return displayContent != null
        && displayContent.getDisplayRotation().isDisplaySeparatingHinge()
        && task != null
        && task.getWindowingMode() == 1;
  }

  public float getHorizontalPositionMultiplier(Configuration configuration) {
    boolean isFullScreenAndBookModeEnabled = isFullScreenAndBookModeEnabled();
    if (isHorizontalReachabilityEnabled(configuration)) {
      return this.mLetterboxConfiguration.getHorizontalMultiplierForReachability(
          isFullScreenAndBookModeEnabled);
    }
    return this.mLetterboxConfiguration.getLetterboxHorizontalPositionMultiplier(
        isFullScreenAndBookModeEnabled);
  }

  public final boolean isFullScreenAndBookModeEnabled() {
    return isDisplayFullScreenAndInPosture(DeviceStateController.DeviceState.HALF_FOLDED, false)
        && this.mLetterboxConfiguration.getIsAutomaticReachabilityInBookModeEnabled();
  }

  public float getVerticalPositionMultiplier(Configuration configuration) {
    boolean isDisplayFullScreenAndInPosture =
        isDisplayFullScreenAndInPosture(DeviceStateController.DeviceState.HALF_FOLDED, true);
    if (isVerticalReachabilityEnabled(configuration)) {
      return this.mLetterboxConfiguration.getVerticalMultiplierForReachability(
          isDisplayFullScreenAndInPosture);
    }
    return this.mLetterboxConfiguration.getLetterboxVerticalPositionMultiplier(
        isDisplayFullScreenAndInPosture);
  }

  public float getFixedOrientationLetterboxAspectRatio(Configuration configuration) {
    if (shouldUseSplitScreenAspectRatio(configuration)) {
      return getSplitScreenAspectRatio();
    }
    if (this.mActivityRecord.shouldCreateCompatDisplayInsets()) {
      return getDefaultMinAspectRatioForUnresizableApps();
    }
    return getDefaultMinAspectRatio();
  }

  public void recomputeConfigurationForCameraCompatIfNeeded() {
    if (isOverrideOrientationOnlyForCameraEnabled()
        || isCameraCompatSplitScreenAspectRatioAllowed()) {
      this.mActivityRecord.recomputeConfiguration();
    }
  }

  public boolean isCameraCompatSplitScreenAspectRatioAllowed() {
    return this.mLetterboxConfiguration.isCameraCompatSplitScreenAspectRatioEnabled()
        && !this.mActivityRecord.shouldCreateCompatDisplayInsets();
  }

  public final boolean shouldUseSplitScreenAspectRatio(Configuration configuration) {
    DeviceStateController.DeviceState deviceState = DeviceStateController.DeviceState.HALF_FOLDED;
    return (isDisplayFullScreenAndInPosture(deviceState, false)
            && ((getHorizontalPositionMultiplier(configuration) > 0.5f
                    ? 1
                    : (getHorizontalPositionMultiplier(configuration) == 0.5f ? 0 : -1))
                != 0))
        || isDisplayFullScreenAndInPosture(deviceState, true)
        || (isCameraCompatSplitScreenAspectRatioAllowed() && isCameraCompatTreatmentActive());
  }

  public final float getDefaultMinAspectRatioForUnresizableApps() {
    if (this.mLetterboxConfiguration.getIsSplitScreenAspectRatioForUnresizableAppsEnabled()
        && this.mActivityRecord.getDisplayContent() != null) {
      return getSplitScreenAspectRatio();
    }
    if (this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps() > 1.0f) {
      return this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps();
    }
    return getDefaultMinAspectRatio();
  }

  public float getSplitScreenAspectRatio() {
    DisplayContent displayContent = this.mActivityRecord.getDisplayContent();
    if (displayContent == null) {
      return getDefaultMinAspectRatioForUnresizableApps();
    }
    int dimensionPixelSize =
        getResources().getDimensionPixelSize(R.dimen.dropdownitem_text_padding_left)
            - (getResources().getDimensionPixelSize(R.dimen.dropdownitem_icon_width) * 2);
    Rect rect = new Rect(displayContent.getWindowConfiguration().getAppBounds());
    if (rect.width() >= rect.height()) {
      rect.inset(dimensionPixelSize / 2, 0);
      rect.right = rect.centerX();
    } else {
      rect.inset(0, dimensionPixelSize / 2);
      rect.bottom = rect.centerY();
    }
    return ActivityRecord.computeAspectRatio(rect);
  }

  public final float getDefaultMinAspectRatio() {
    DisplayContent displayContent = this.mActivityRecord.getDisplayContent();
    if (displayContent == null
        || !this.mLetterboxConfiguration
            .getIsDisplayAspectRatioEnabledForFixedOrientationLetterbox()) {
      return this.mLetterboxConfiguration.getFixedOrientationLetterboxAspectRatio();
    }
    return ActivityRecord.computeAspectRatio(new Rect(displayContent.getBounds()));
  }

  public Resources getResources() {
    return this.mActivityRecord.mWmService.mContext.getResources();
  }

  public int getLetterboxPositionForVerticalReachability() {
    return this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(
        isDisplayFullScreenAndSeparatingHinge());
  }

  public int getLetterboxPositionForHorizontalReachability() {
    return this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(
        isFullScreenAndBookModeEnabled());
  }

  public void handleHorizontalDoubleTap(int i) {
    if (!isHorizontalReachabilityEnabled() || this.mActivityRecord.isInTransition()) {
      return;
    }
    if (this.mLetterbox.getInnerFrame().left > i || this.mLetterbox.getInnerFrame().right < i) {
      boolean z =
          isDisplayFullScreenAndSeparatingHinge()
              && this.mLetterboxConfiguration.getIsAutomaticReachabilityInBookModeEnabled();
      int letterboxPositionForHorizontalReachability =
          this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(z);
      if (this.mLetterbox.getInnerFrame().left > i) {
        this.mLetterboxConfiguration.movePositionForHorizontalReachabilityToNextLeftStop(z);
        logLetterboxPositionChange(letterboxPositionForHorizontalReachability == 1 ? 1 : 4);
      } else if (this.mLetterbox.getInnerFrame().right < i) {
        this.mLetterboxConfiguration.movePositionForHorizontalReachabilityToNextRightStop(z);
        logLetterboxPositionChange(letterboxPositionForHorizontalReachability == 1 ? 3 : 2);
      }
      this.mDoubleTapEvent = true;
      this.mActivityRecord.recomputeConfiguration();
    }
  }

  public void handleVerticalDoubleTap(int i) {
    if (!isVerticalReachabilityEnabled() || this.mActivityRecord.isInTransition()) {
      return;
    }
    if (this.mLetterbox.getInnerFrame().top > i || this.mLetterbox.getInnerFrame().bottom < i) {
      boolean isDisplayFullScreenAndSeparatingHinge = isDisplayFullScreenAndSeparatingHinge();
      int letterboxPositionForVerticalReachability =
          this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(
              isDisplayFullScreenAndSeparatingHinge);
      if (this.mLetterbox.getInnerFrame().top > i) {
        this.mLetterboxConfiguration.movePositionForVerticalReachabilityToNextTopStop(
            isDisplayFullScreenAndSeparatingHinge);
        logLetterboxPositionChange(letterboxPositionForVerticalReachability == 1 ? 5 : 8);
      } else if (this.mLetterbox.getInnerFrame().bottom < i) {
        this.mLetterboxConfiguration.movePositionForVerticalReachabilityToNextBottomStop(
            isDisplayFullScreenAndSeparatingHinge);
        logLetterboxPositionChange(letterboxPositionForVerticalReachability == 1 ? 7 : 6);
      }
      this.mDoubleTapEvent = true;
      this.mActivityRecord.recomputeConfiguration();
    }
  }

  public final boolean isHorizontalReachabilityEnabled(Configuration configuration) {
    return this.mLetterboxConfiguration.getIsHorizontalReachabilityEnabled()
        && configuration.windowConfiguration.getWindowingMode() == 1
        && configuration.orientation == 2
        && this.mActivityRecord.getOrientationForReachability() == 1
        && configuration.windowConfiguration.getAppBounds().height()
            <= this.mActivityRecord.getScreenResolvedBounds().height();
  }

  public boolean isHorizontalReachabilityEnabled() {
    return isHorizontalReachabilityEnabled(this.mActivityRecord.getParent().getConfiguration());
  }

  public boolean isLetterboxDoubleTapEducationEnabled() {
    return isHorizontalReachabilityEnabled() || isVerticalReachabilityEnabled();
  }

  public final boolean isVerticalReachabilityEnabled(Configuration configuration) {
    return this.mLetterboxConfiguration.getIsVerticalReachabilityEnabled()
        && configuration.windowConfiguration.getWindowingMode() == 1
        && configuration.orientation == 1
        && this.mActivityRecord.getOrientationForReachability() == 2
        && configuration.windowConfiguration.getBounds().width()
            == this.mActivityRecord.getScreenResolvedBounds().width();
  }

  public boolean isVerticalReachabilityEnabled() {
    return isVerticalReachabilityEnabled(this.mActivityRecord.getParent().getConfiguration());
  }

  public boolean shouldShowLetterboxUi(WindowState windowState) {
    boolean z;
    if (this.mIsRelaunchingAfterRequestedOrientationChanged || !isSurfaceReadyToShow(windowState)) {
      return this.mLastShouldShowLetterboxUi;
    }
    boolean z2 =
        (this.mActivityRecord.isInLetterboxAnimation() || isSurfaceVisible(windowState))
            && windowState.areAppWindowBoundsLetterboxed()
            && ((windowState.getAttrs().flags & 1048576) == 0
                || (CoreRune.FW_CUSTOM_LETTERBOX
                    && CustomLetterboxConfiguration.isCustomLetterboxEnabled(
                        this.mActivityRecord)));
    if (!z2
        || ((CoreRune.FW_CUSTOM_LETTERBOX
                && CustomLetterboxConfiguration.isCustomLetterboxEnabled(this.mActivityRecord)
                && this.mActivityRecord.isRootOfTask())
            || this.mActivityRecord.fillsParent())) {
      z = false;
    } else {
      z = true;
      z2 = false;
    }
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ASPECT_RATIO && !z2) {
      BoundsCompatRecord boundsCompatRecord = this.mActivityRecord.mCompatRecord;
      if (boundsCompatRecord.mIsTaskOrientationMismatched
          && boundsCompatRecord.isAboveEmbeddedTaskFragment()) {
        z2 = true;
      }
    }
    this.mLastShouldShowLetterboxDummy = !z2 && z;
    this.mLastShouldShowLetterboxUi = z2;
    return z2;
  }

  public boolean isSurfaceReadyToShow(WindowState windowState) {
    return windowState.isDrawn() || windowState.isDragResizeChanged();
  }

  public boolean isSurfaceVisible(WindowState windowState) {
    return windowState.isOnScreen()
        && (this.mActivityRecord.isVisible() || this.mActivityRecord.isVisibleRequested());
  }

  public final Color getLetterboxBackgroundColor() {
    WindowState findMainWindow = this.mActivityRecord.findMainWindow();
    if (findMainWindow == null || findMainWindow.isLetterboxedForDisplayCutout()) {
      if (findMainWindow != null && findMainWindow.getStageType() != 0) {
        return Color.valueOf(this.mLetterboxConfiguration.getMultiWindowLetterboxBackgroundColor());
      }
      return Color.valueOf(-16777216);
    }
    int letterboxBackgroundType = this.mLetterboxConfiguration.getLetterboxBackgroundType();
    ActivityManager.TaskDescription taskDescription = this.mActivityRecord.taskDescription;
    if (letterboxBackgroundType == 0) {
      return this.mLetterboxConfiguration.getLetterboxBackgroundColor();
    }
    if (letterboxBackgroundType != 1) {
      if (letterboxBackgroundType != 2) {
        if (letterboxBackgroundType == 3) {
          if (hasWallpaperBackgroundForLetterbox()) {
            return Color.valueOf(-16777216);
          }
          Slog.w(
              "ActivityTaskManager",
              "Wallpaper option is selected for letterbox background but blur is not supported by a"
                  + " device or not supported in the current window configuration or both alpha"
                  + " scrim and blur radius aren't provided so using solid color background");
        } else {
          throw new AssertionError(
              "Unexpected letterbox background type: " + letterboxBackgroundType);
        }
      } else if (taskDescription != null && taskDescription.getBackgroundColorFloating() != 0) {
        return Color.valueOf(taskDescription.getBackgroundColorFloating());
      }
    } else if (taskDescription != null && taskDescription.getBackgroundColor() != 0) {
      return Color.valueOf(taskDescription.getBackgroundColor());
    }
    return this.mLetterboxConfiguration.getLetterboxBackgroundColor();
  }

  public final void updateRoundedCornersIfNeeded(WindowState windowState) {
    SurfaceControl surfaceControl = windowState.getSurfaceControl();
    if (surfaceControl == null || !surfaceControl.isValid()) {
      return;
    }
    this.mActivityRecord
        .getSyncTransaction()
        .setCrop(surfaceControl, getCropBoundsIfNeeded(windowState))
        .setCornerRadius(surfaceControl, getRoundedCornersRadius(windowState));
  }

  public Rect getCropBoundsIfNeeded(WindowState windowState) {
    if (!requiresRoundedCorners(windowState) || this.mActivityRecord.isInLetterboxAnimation()) {
      return null;
    }
    Rect rect = new Rect(this.mActivityRecord.getBounds());
    if (hasInheritedLetterboxBehavior()
        && (rect.width() != windowState.mRequestedWidth
            || rect.height() != windowState.mRequestedHeight)) {
      return null;
    }
    adjustBoundsForTaskbar(windowState, rect);
    float f = windowState.mInvGlobalScale;
    if (f != 1.0f && f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      rect.scale(f);
    }
    rect.offsetTo(0, 0);
    return rect;
  }

  public final boolean requiresRoundedCorners(WindowState windowState) {
    if (CoreRune.FW_CUSTOM_LETTERBOX
        && CustomLetterboxConfiguration.getCustomLetterboxActivityCornersRadius(
                this.mActivityRecord)
            > 0
        && isSurfaceReadyToShow(windowState)
        && windowState.areAppWindowBoundsLetterboxed()) {
      return true;
    }
    return isLetterboxedNotForDisplayCutout(windowState)
        && this.mLetterboxConfiguration.isLetterboxActivityCornersRounded();
  }

  public int getRoundedCornersRadius(WindowState windowState) {
    int min;
    if (!requiresRoundedCorners(windowState)) {
      return 0;
    }
    if (this.mLetterboxConfiguration.getLetterboxActivityCornersRadius() >= 0) {
      min = this.mLetterboxConfiguration.getLetterboxActivityCornersRadius();
    } else {
      InsetsState insetsState = windowState.getInsetsState();
      min =
          Math.min(
              getInsetsStateCornerRadius(insetsState, 3),
              getInsetsStateCornerRadius(insetsState, 2));
    }
    float f = windowState.mInvGlobalScale;
    return (f == 1.0f || f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) ? min : (int) (f * min);
  }

  public InsetsSource getExpandedTaskbarOrNull(WindowState windowState) {
    InsetsState insetsState = windowState.getInsetsState();
    for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
      if (sourceAt.getType() == WindowInsets.Type.navigationBars()
          && sourceAt.insetsRoundedCornerFrame()
          && sourceAt.isVisible()) {
        return sourceAt;
      }
    }
    return null;
  }

  public boolean getIsRelaunchingAfterRequestedOrientationChanged() {
    return this.mIsRelaunchingAfterRequestedOrientationChanged;
  }

  public final void adjustBoundsForTaskbar(WindowState windowState, Rect rect) {
    InsetsSource expandedTaskbarOrNull = getExpandedTaskbarOrNull(windowState);
    if (expandedTaskbarOrNull != null) {
      rect.bottom = Math.min(rect.bottom, expandedTaskbarOrNull.getFrame().top);
    }
  }

  public final int getInsetsStateCornerRadius(InsetsState insetsState, int i) {
    RoundedCorner roundedCorner = insetsState.getRoundedCorners().getRoundedCorner(i);
    if (roundedCorner == null) {
      return 0;
    }
    return roundedCorner.getRadius();
  }

  public final boolean isLetterboxedNotForDisplayCutout(WindowState windowState) {
    return shouldShowLetterboxUi(windowState) && !windowState.isLetterboxedForDisplayCutout();
  }

  public final void updateWallpaperForLetterbox(final WindowState windowState) {
    boolean z =
        this.mLetterboxConfiguration.getLetterboxBackgroundType() == 3
            && isLetterboxedNotForDisplayCutout(windowState)
            && (getLetterboxWallpaperBlurRadius() > 0
                || ((CoreRune.FW_CUSTOM_LETTERBOX
                        && CustomLetterboxConfiguration.isCustomLetterboxEnabled(
                            this.mActivityRecord))
                    || getLetterboxWallpaperDarkScrimAlpha()
                        > DisplayPowerController2.RATE_FROM_DOZE_TO_ON))
            && (getLetterboxWallpaperBlurRadius() <= 0
                || ((CoreRune.FW_CUSTOM_LETTERBOX
                        && CustomLetterboxConfiguration.isCustomLetterboxEnabled(
                            this.mActivityRecord))
                    || isLetterboxWallpaperBlurSupported()));
    if (this.mShowWallpaperForLetterboxBackground != z) {
      this.mShowWallpaperForLetterboxBackground = z;
      this.mActivityRecord.requestUpdateWallpaperIfNeeded();
      final DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
      if (displayContent == null
          || z
          || windowState != displayContent.mWallpaperController.getWallpaperTarget()) {
        return;
      }
      if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX
          && !CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(
              this.mActivityRecord)) {
        BlurWallpaperLetterbox.onAdjustWallpaperWindows(displayContent, false);
      }
      displayContent.mWmService.f1749mH.post(
          new Runnable() { // from class:
                           // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
              LetterboxUiController.this.lambda$updateWallpaperForLetterbox$12(
                  displayContent, windowState);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateWallpaperForLetterbox$12(
      DisplayContent displayContent, WindowState windowState) {
    WindowManagerGlobalLock windowManagerGlobalLock = displayContent.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (!this.mShowWallpaperForLetterboxBackground
            && windowState == displayContent.mWallpaperController.getWallpaperTarget()) {
          displayContent.pendingLayoutChanges |= 4;
          displayContent.setLayoutNeeded();
          displayContent.mWmService.mWindowPlacerLocked.requestTraversal();
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final int getLetterboxWallpaperBlurRadius() {
    int letterboxBackgroundWallpaperBlurRadius =
        this.mLetterboxConfiguration.getLetterboxBackgroundWallpaperBlurRadius();
    if (letterboxBackgroundWallpaperBlurRadius < 0) {
      return 0;
    }
    return letterboxBackgroundWallpaperBlurRadius;
  }

  public final float getLetterboxWallpaperDarkScrimAlpha() {
    float letterboxBackgroundWallpaperDarkScrimAlpha =
        this.mLetterboxConfiguration.getLetterboxBackgroundWallpaperDarkScrimAlpha();
    return (letterboxBackgroundWallpaperDarkScrimAlpha
                < DisplayPowerController2.RATE_FROM_DOZE_TO_ON
            || letterboxBackgroundWallpaperDarkScrimAlpha >= 1.0f)
        ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        : letterboxBackgroundWallpaperDarkScrimAlpha;
  }

  public final boolean isLetterboxWallpaperBlurSupported() {
    return ((WindowManager)
            this.mLetterboxConfiguration.mContext.getSystemService(WindowManager.class))
        .isCrossWindowBlurEnabled();
  }

  public void dump(PrintWriter printWriter, String str) {
    WindowState findMainWindow = this.mActivityRecord.findMainWindow();
    if (findMainWindow == null) {
      return;
    }
    boolean areAppWindowBoundsLetterboxed = findMainWindow.areAppWindowBoundsLetterboxed();
    printWriter.println(str + "areBoundsLetterboxed=" + areAppWindowBoundsLetterboxed);
    if (areAppWindowBoundsLetterboxed) {
      printWriter.println(str + "  letterboxReason=" + getLetterboxReasonString(findMainWindow));
      if (CoreRune.FW_CUSTOM_LETTERBOX
          && CustomLetterboxConfiguration.isCustomLetterboxEnabled(this.mActivityRecord)) {
        printWriter.println(
            str
                + "  letterboxController="
                + this.mActivityRecord.mCompatRecord.getController().toString());
      }
      printWriter.println(
          str
              + "  activityAspectRatio="
              + ActivityRecord.computeAspectRatio(this.mActivityRecord.getBounds()));
      boolean shouldShowLetterboxUi = shouldShowLetterboxUi(findMainWindow);
      printWriter.println(str + "shouldShowLetterboxUi=" + shouldShowLetterboxUi);
      if (shouldShowLetterboxUi) {
        printWriter.println(
            str
                + "  letterboxBackgroundColor="
                + Integer.toHexString(getLetterboxBackgroundColor().toArgb()));
        printWriter.println(
            str
                + "  letterboxBackgroundType="
                + LetterboxConfiguration.letterboxBackgroundTypeToString(
                    this.mLetterboxConfiguration.getLetterboxBackgroundType()));
        printWriter.println(
            str + "  letterboxCornerRadius=" + getRoundedCornersRadius(findMainWindow));
        if (this.mLetterboxConfiguration.getLetterboxBackgroundType() == 3) {
          printWriter.println(
              str + "  isLetterboxWallpaperBlurSupported=" + isLetterboxWallpaperBlurSupported());
          printWriter.println(
              str
                  + "  letterboxBackgroundWallpaperDarkScrimAlpha="
                  + getLetterboxWallpaperDarkScrimAlpha());
          printWriter.println(
              str
                  + "  letterboxBackgroundWallpaperBlurRadius="
                  + getLetterboxWallpaperBlurRadius());
        }
        printWriter.println(
            str + "  isHorizontalReachabilityEnabled=" + isHorizontalReachabilityEnabled());
        printWriter.println(
            str + "  isVerticalReachabilityEnabled=" + isVerticalReachabilityEnabled());
        printWriter.println(
            str
                + "  letterboxHorizontalPositionMultiplier="
                + getHorizontalPositionMultiplier(
                    this.mActivityRecord.getParent().getConfiguration()));
        printWriter.println(
            str
                + "  letterboxVerticalPositionMultiplier="
                + getVerticalPositionMultiplier(
                    this.mActivityRecord.getParent().getConfiguration()));
        printWriter.println(
            str
                + "  letterboxPositionForHorizontalReachability="
                + LetterboxConfiguration.letterboxHorizontalReachabilityPositionToString(
                    this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(
                        false)));
        printWriter.println(
            str
                + "  letterboxPositionForVerticalReachability="
                + LetterboxConfiguration.letterboxVerticalReachabilityPositionToString(
                    this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(
                        false)));
        printWriter.println(
            str
                + "  fixedOrientationLetterboxAspectRatio="
                + this.mLetterboxConfiguration.getFixedOrientationLetterboxAspectRatio());
        printWriter.println(
            str
                + "  defaultMinAspectRatioForUnresizableApps="
                + this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps());
        printWriter.println(
            str
                + "  isSplitScreenAspectRatioForUnresizableAppsEnabled="
                + this.mLetterboxConfiguration
                    .getIsSplitScreenAspectRatioForUnresizableAppsEnabled());
        printWriter.println(
            str
                + "  isDisplayAspectRatioEnabledForFixedOrientationLetterbox="
                + this.mLetterboxConfiguration
                    .getIsDisplayAspectRatioEnabledForFixedOrientationLetterbox());
      }
    }
  }

  public final String getLetterboxReasonString(WindowState windowState) {
    return this.mActivityRecord.inSizeCompatMode()
        ? "SIZE_COMPAT_MODE"
        : this.mActivityRecord.isLetterboxedForFixedOrientationAndAspectRatio()
            ? "FIXED_ORIENTATION"
            : windowState.isLetterboxedForDisplayCutout()
                ? "DISPLAY_CUTOUT"
                : this.mActivityRecord.isAspectRatioApplied() ? "ASPECT_RATIO" : "UNKNOWN_REASON";
  }

  public final boolean shouldShowLetterboxDummy() {
    return this.mLastShouldShowLetterboxDummy && !this.mLastShouldShowLetterboxUi;
  }

  public final void layoutLetterboxDummyIfNeeded(WindowState windowState) {
    if (!shouldShowLetterboxDummy()) {
      Letterbox letterbox = this.mLetterboxDummy;
      if (letterbox != null) {
        letterbox.hide();
        return;
      }
      return;
    }
    if (this.mLetterboxDummy == null) {
      this.mLetterboxDummy =
          new Letterbox(
              null,
              null,
              new Supplier() { // from class:
                               // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda29
                @Override // java.util.function.Supplier
                public final Object get() {
                  Boolean bool;
                  bool = Boolean.FALSE;
                  return bool;
                }
              },
              null,
              new Supplier() { // from class:
                               // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda30
                @Override // java.util.function.Supplier
                public final Object get() {
                  Boolean bool;
                  bool = Boolean.FALSE;
                  return bool;
                }
              },
              null,
              null,
              null,
              null,
              null);
    }
    if (this.mActivityRecord.isInLetterboxAnimation()) {
      this.mActivityRecord.getTask().getPosition(this.mTmpPoint);
    } else {
      this.mActivityRecord.getPosition(this.mTmpPoint);
    }
    Rect fixedRotationTransformDisplayBounds =
        this.mActivityRecord.getFixedRotationTransformDisplayBounds();
    if (fixedRotationTransformDisplayBounds == null) {
      if (this.mActivityRecord.inMultiWindowMode()) {
        fixedRotationTransformDisplayBounds = this.mActivityRecord.getTaskFragment().getBounds();
      } else {
        fixedRotationTransformDisplayBounds =
            this.mActivityRecord.getRootTask().getParent().getBounds();
      }
    }
    this.mLetterboxDummy.layout(
        fixedRotationTransformDisplayBounds,
        hasInheritedLetterboxBehavior() ? this.mActivityRecord.getBounds() : windowState.getFrame(),
        this.mTmpPoint);
  }

  public final boolean canHaveLetterboxSurface() {
    if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX
        && CustomLetterboxConfiguration.isCustomLetterboxEnabled(this.mActivityRecord)
        && BlurWallpaperLetterbox.isBlurWallpaperLetterboxActive(
            this.mActivityRecord.mDisplayContent)) {
      return false;
    }
    return !CustomLetterboxConfiguration.isCustomLetterboxEnabled(this.mActivityRecord)
        || CustomLetterboxConfiguration.hasLetterboxSurface(this.mActivityRecord.mDisplayContent);
  }

  public final SemBlurInfo.ColorCurve getLetterboxWallpaperBlurColorCurve() {
    DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
    if (displayContent != null) {
      return BlurWallpaperLetterbox.getLetterboxWallpaperBlurColorCurve(displayContent);
    }
    return null;
  }

  public boolean reparentToWallpaperParentImmediately(
      SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
    Letterbox letterbox = this.mLetterbox;
    return letterbox != null
        && letterbox.reparentToWallpaperParentImmediately(transaction, surfaceControl);
  }

  public final void handleSingleTap(int i) {
    this.mActivityRecord.mAtmService.f1734mH.removeCallbacks(this.mOnSingleTap);
    this.mActivityRecord.mAtmService.f1734mH.post(this.mOnSingleTap);
  }

  public final void onSingleTap() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mActivityRecord.mAtmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Task task = this.mActivityRecord.getTask();
        if (task == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        task.mSingleTap = true;
        try {
          task.dispatchTaskInfoChangedIfNeeded(true);
          WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
          task.mSingleTap = false;
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public final int letterboxHorizontalReachabilityPositionToLetterboxPosition(int i) {
    if (i == 0) {
      return 3;
    }
    if (i == 1) {
      return 2;
    }
    if (i == 2) {
      return 4;
    }
    throw new AssertionError("Unexpected letterbox horizontal reachability position type: " + i);
  }

  public final int letterboxVerticalReachabilityPositionToLetterboxPosition(int i) {
    if (i == 0) {
      return 5;
    }
    if (i == 1) {
      return 2;
    }
    if (i == 2) {
      return 6;
    }
    throw new AssertionError("Unexpected letterbox vertical reachability position type: " + i);
  }

  public int getLetterboxPositionForLogging() {
    if (isHorizontalReachabilityEnabled()) {
      return letterboxHorizontalReachabilityPositionToLetterboxPosition(
          getLetterboxConfiguration()
              .getLetterboxPositionForHorizontalReachability(
                  isDisplayFullScreenAndInPosture(
                      DeviceStateController.DeviceState.HALF_FOLDED, false)));
    }
    if (isVerticalReachabilityEnabled()) {
      return letterboxVerticalReachabilityPositionToLetterboxPosition(
          getLetterboxConfiguration()
              .getLetterboxPositionForVerticalReachability(
                  isDisplayFullScreenAndInPosture(
                      DeviceStateController.DeviceState.HALF_FOLDED, true)));
    }
    return 0;
  }

  public final LetterboxConfiguration getLetterboxConfiguration() {
    return this.mLetterboxConfiguration;
  }

  public final void logLetterboxPositionChange(int i) {
    this.mActivityRecord
        .mTaskSupervisor
        .getActivityMetricsLogger()
        .logLetterboxPositionChange(this.mActivityRecord, i);
  }

  public LetterboxDetails getLetterboxDetails() {
    WindowState findMainWindow = this.mActivityRecord.findMainWindow();
    if (this.mLetterbox != null
        && findMainWindow != null
        && !findMainWindow.isLetterboxedForDisplayCutout()) {
      Rect rect = new Rect();
      Rect rect2 = new Rect();
      getLetterboxInnerBounds(rect);
      getLetterboxOuterBounds(rect2);
      if (!rect.isEmpty() && !rect2.isEmpty()) {
        return new LetterboxDetails(rect, rect2, findMainWindow.mAttrs.insetsFlags.appearance);
      }
    }
    return null;
  }

  public int getLetterboxDirection() {
    DisplayContent displayContent = this.mActivityRecord.mDisplayContent;
    if (this.mLetterbox == null || displayContent == null) {
      return 0;
    }
    DisplayCutout calculateDisplayCutoutForRotation =
        displayContent.calculateDisplayCutoutForRotation(
            displayContent.getRotation(), this.mActivityRecord.isLayoutNeededInUdcCutout());
    return this.mLetterbox.getDirection(
        calculateDisplayCutoutForRotation.getSafeInsetLeft(),
        calculateDisplayCutoutForRotation.getSafeInsetRight(),
        calculateDisplayCutoutForRotation.getSafeInsetTop(),
        calculateDisplayCutoutForRotation.getSafeInsetBottom());
  }

  public void updateInheritedLetterbox() {
    final WindowContainer parent = this.mActivityRecord.getParent();
    if (parent != null && this.mLetterboxConfiguration.isTranslucentLetterboxingEnabled()) {
      WindowContainerListener windowContainerListener = this.mLetterboxConfigListener;
      if (windowContainerListener != null) {
        windowContainerListener.onRemoved();
        clearInheritedConfig();
      }
      ActivityRecord activity =
          this.mActivityRecord
              .getTask()
              .getActivity(
                  FIRST_OPAQUE_NOT_FINISHING_ACTIVITY_PREDICATE, this.mActivityRecord, false, true);
      this.mFirstOpaqueActivityBeneath = activity;
      if (activity == null || activity.isEmbedded()) {
        this.mActivityRecord.recomputeConfiguration();
        return;
      }
      if (this.mActivityRecord.getTask() == null
          || this.mActivityRecord.fillsParent()
          || this.mActivityRecord.hasCompatDisplayInsetsWithoutInheritance()) {
        return;
      }
      this.mFirstOpaqueActivityBeneath.mLetterboxUiController.mDestroyListeners.add(this);
      inheritConfiguration(this.mFirstOpaqueActivityBeneath);
      this.mLetterboxConfigListener =
          WindowContainer.overrideConfigurationPropagation(
              this.mActivityRecord,
              this.mFirstOpaqueActivityBeneath,
              new WindowContainer
                  .ConfigurationMerger() { // from class:
                                           // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda4
                @Override // com.android.server.wm.WindowContainer.ConfigurationMerger
                public final Configuration merge(
                    Configuration configuration, Configuration configuration2) {
                  Configuration lambda$updateInheritedLetterbox$15;
                  lambda$updateInheritedLetterbox$15 =
                      LetterboxUiController.this.lambda$updateInheritedLetterbox$15(
                          parent, configuration, configuration2);
                  return lambda$updateInheritedLetterbox$15;
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Configuration lambda$updateInheritedLetterbox$15(
      WindowContainer windowContainer, Configuration configuration, Configuration configuration2) {
    resetTranslucentOverrideConfig(configuration2);
    Rect bounds = windowContainer.getWindowConfiguration().getBounds();
    Rect bounds2 = configuration2.windowConfiguration.getBounds();
    Rect bounds3 = configuration.windowConfiguration.getBounds();
    int i = bounds.left;
    bounds2.set(i, bounds.top, bounds3.width() + i, bounds.top + bounds3.height());
    configuration2.windowConfiguration.setAppBounds(new Rect());
    inheritConfiguration(this.mFirstOpaqueActivityBeneath);
    return configuration2;
  }

  public boolean hasInheritedLetterboxBehavior() {
    return this.mLetterboxConfigListener != null;
  }

  public boolean hasInheritedOrientation() {
    return hasInheritedLetterboxBehavior() && this.mActivityRecord.getOverrideOrientation() != -1;
  }

  public float getInheritedMinAspectRatio() {
    return this.mInheritedMinAspectRatio;
  }

  public float getInheritedMaxAspectRatio() {
    return this.mInheritedMaxAspectRatio;
  }

  public int getInheritedAppCompatState() {
    return this.mInheritedAppCompatState;
  }

  public int getInheritedOrientation() {
    return this.mInheritedOrientation;
  }

  public ActivityRecord.CompatDisplayInsets getInheritedCompatDisplayInsets() {
    return this.mInheritedCompatDisplayInsets;
  }

  public void clearInheritedCompatDisplayInsets() {
    this.mInheritedCompatDisplayInsets = null;
  }

  public boolean applyOnOpaqueActivityBelow(final Consumer consumer) {
    return ((Boolean)
            findOpaqueNotFinishingActivityBelow()
                .map(
                    new Function() { // from class:
                                     // com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda6
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Boolean lambda$applyOnOpaqueActivityBelow$16;
                        lambda$applyOnOpaqueActivityBelow$16 =
                            LetterboxUiController.lambda$applyOnOpaqueActivityBelow$16(
                                consumer, (ActivityRecord) obj);
                        return lambda$applyOnOpaqueActivityBelow$16;
                      }
                    })
                .orElse(Boolean.FALSE))
        .booleanValue();
  }

  public static /* synthetic */ Boolean lambda$applyOnOpaqueActivityBelow$16(
      Consumer consumer, ActivityRecord activityRecord) {
    consumer.accept(activityRecord);
    return Boolean.TRUE;
  }

  public Optional findOpaqueNotFinishingActivityBelow() {
    if (!hasInheritedLetterboxBehavior() || this.mActivityRecord.getTask() == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(this.mFirstOpaqueActivityBeneath);
  }

  public static void resetTranslucentOverrideConfig(Configuration configuration) {
    configuration.orientation = 0;
    configuration.compatScreenWidthDp = 0;
    configuration.screenWidthDp = 0;
    configuration.compatScreenHeightDp = 0;
    configuration.screenHeightDp = 0;
    configuration.compatSmallestScreenWidthDp = 0;
    configuration.smallestScreenWidthDp = 0;
  }

  public final void inheritConfiguration(ActivityRecord activityRecord) {
    if (this.mActivityRecord.getMinAspectRatio() != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      this.mInheritedMinAspectRatio = activityRecord.getMinAspectRatio();
    }
    if (this.mActivityRecord.getMaxAspectRatio() != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      this.mInheritedMaxAspectRatio = activityRecord.getMaxAspectRatio();
    }
    this.mInheritedOrientation = activityRecord.getRequestedConfigurationOrientation();
    this.mInheritedAppCompatState = activityRecord.getAppCompatState();
    this.mInheritedCompatDisplayInsets = activityRecord.getCompatDisplayInsets();
  }

  public final void clearInheritedConfig() {
    ActivityRecord activityRecord = this.mFirstOpaqueActivityBeneath;
    if (activityRecord != null) {
      activityRecord.mLetterboxUiController.mDestroyListeners.remove(this);
    }
    this.mFirstOpaqueActivityBeneath = null;
    this.mLetterboxConfigListener = null;
    this.mInheritedMinAspectRatio = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mInheritedMaxAspectRatio = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mInheritedOrientation = 0;
    this.mInheritedAppCompatState = 0;
    this.mInheritedCompatDisplayInsets = null;
  }
}
