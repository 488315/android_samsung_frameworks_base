package com.android.server.wm;

import android.R;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.DisplayShape;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.PrivacyIndicatorBounds;
import android.view.RoundedCorners;
import android.view.WindowInsets;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.wm.utils.RotationCache;
import com.android.server.wm.utils.WmDisplayCutout;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class UdcCutoutPolicy {
  public static ConcurrentHashMap sUseLayoutInUdcCutoutActivities;
  public static ConcurrentHashMap sUseLayoutInUdcCutoutApplications;
  public static ConcurrentHashMap sUseLayoutInUdcCutoutWindows;
  public final Context mContext;
  public final DisplayContent mDisplayContent;
  public final RotationCache mDisplayCutoutCache;
  public Rect mTmpBarContentFrame;
  public Configuration mUdcConfiguration;
  public DisplayCutout mUdcCutout;
  public DisplayFrames mUdcDisplayFrames;

  public UdcCutoutPolicy(DisplayContent displayContent, RotationCache rotationCache) {
    this.mDisplayContent = displayContent;
    this.mContext = displayContent.mWmService.mContext;
    this.mDisplayCutoutCache = rotationCache;
  }

  public void updateUdcCutout(DisplayInfo displayInfo, int i, int i2) {
    int proportionalDensity =
        DisplayContent.getProportionalDensity(
            displayInfo.getNaturalWidth(), i, displayInfo.logicalDensityDpi);
    String string = this.mContext.getString(R.string.duration_hours_relative);
    DisplayCutout fromResourcesRectApproximation =
        TextUtils.isEmpty(string)
            ? null
            : DisplayCutout.fromResourcesRectApproximation(
                this.mContext.getResources(), i, i2, i, i2, proportionalDensity, string);
    this.mUdcCutout = fromResourcesRectApproximation;
    if (fromResourcesRectApproximation != null) {
      Slog.v(StartingSurfaceController.TAG, "UdcCutoutPolicy: updateUdcCutout=" + this.mUdcCutout);
      return;
    }
    if (CoreRune.isSamsungLogEnabled()) {
      Slog.v(
          StartingSurfaceController.TAG,
          "UdcCutoutPolicy: updateUdcCutout=null, isPrimaryDisplay=true");
    }
  }

  public boolean hasUdcCutout() {
    return this.mUdcCutout != null;
  }

  public DisplayCutout calculateDisplayCutoutForRotation(int i) {
    return ((WmDisplayCutout) this.mDisplayCutoutCache.getOrCompute(this.mUdcCutout, i))
        .getDisplayCutout();
  }

  public void dump(PrintWriter printWriter, String str) {
    DisplayCutout displayCutout = this.mUdcCutout;
    if (displayCutout == null || displayCutout.isEmpty()) {
      return;
    }
    printWriter.print(str);
    printWriter.print(" udcCutout=");
    printWriter.print(this.mUdcCutout);
    Configuration configuration = this.mUdcConfiguration;
    if (configuration != null && !configuration.equals(Configuration.EMPTY)) {
      printWriter.print(", config=");
      printWriter.print(this.mUdcConfiguration);
    }
    printWriter.println();
  }

  /* JADX WARN: Code restructure failed: missing block: B:18:0x002b, code lost:

     if (supportsUdcCutout(getApplicationInfo(r0)) == false) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:25:0x0044, code lost:

     if (supportsUdcCutout(r0.info.applicationInfo) != false) goto L12;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static void updateUseLayoutInUdcCutoutIfNeeded(WindowContainer windowContainer) {
    if (CoreRune.SAFE_DEBUG && updateUseForceLayoutInUdcCutoutIfNeeded(windowContainer)) {
      return;
    }
    WindowState asWindowState = windowContainer.asWindowState();
    boolean z = true;
    boolean z2 = false;
    if (asWindowState != null) {
      ActivityRecord activityRecord = asWindowState.mActivityRecord;
      if (activityRecord != null) {
        z = activityRecord.mUseLayoutInUdcCutout;
        z2 = z;
      } else if ((asWindowState.mAttrs.samsungFlags & IInstalld.FLAG_FORCE) == 0) {
      }
    } else {
      ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
      if (asActivityRecord != null) {
        if (!supportsUdcCutout(asActivityRecord.info)) {}
        z2 = z;
      }
      z = false;
    }
    windowContainer.mUseLayoutInUdcCutout = z;
    windowContainer.mUseConfigurationInUdcCutout = z2;
  }

  public static boolean supportsUdcCutout(PackageItemInfo packageItemInfo) {
    Bundle bundle;
    return (packageItemInfo == null
            || (bundle = packageItemInfo.metaData) == null
            || bundle.getBoolean("com.samsung.android.supports_udc_cutout", true))
        ? false
        : true;
  }

  public static ApplicationInfo getApplicationInfo(WindowState windowState) {
    PackageManagerInternal packageManagerInternal = windowState.mWmService.mPmInternal;
    String str = windowState.mAttrs.packageName;
    int i = windowState.mOwnerUid;
    return packageManagerInternal.getApplicationInfo(str, 0L, i, UserHandle.getUserId(i));
  }

  public void onDisplayInfoUpdated(
      InsetsState insetsState,
      int i,
      int i2,
      int i3,
      RoundedCorners roundedCorners,
      PrivacyIndicatorBounds privacyIndicatorBounds,
      DisplayShape displayShape) {
    DisplayCutout calculateDisplayCutoutForRotation = calculateDisplayCutoutForRotation(i);
    DisplayFrames displayFrames = this.mUdcDisplayFrames;
    if (displayFrames == null) {
      DisplayInfo displayInfo = new DisplayInfo();
      displayInfo.rotation = i;
      displayInfo.logicalWidth = i2;
      displayInfo.logicalHeight = i3;
      this.mUdcDisplayFrames =
          new DisplayFrames(
              new InsetsState(insetsState, true),
              displayInfo,
              calculateDisplayCutoutForRotation,
              roundedCorners,
              privacyIndicatorBounds,
              displayShape);
      return;
    }
    displayFrames.mInsetsState.set(insetsState, true);
    this.mUdcDisplayFrames.update(
        i,
        i2,
        i3,
        calculateDisplayCutoutForRotation,
        roundedCorners,
        privacyIndicatorBounds,
        displayShape);
  }

  public Rect getIntersectedCutout(Rect rect) {
    if (this.mTmpBarContentFrame == null) {
      this.mTmpBarContentFrame = new Rect();
    }
    this.mTmpBarContentFrame.set(rect);
    this.mTmpBarContentFrame.intersectUnchecked(this.mUdcDisplayFrames.mDisplayCutoutSafe);
    return this.mTmpBarContentFrame;
  }

  public void adjustInsetsForUdc(WindowContainer windowContainer, InsetsState insetsState) {
    updateInsetsStateForDisplayCutout(
        getFixedRotationTransformDisplayFrames(windowContainer), insetsState);
  }

  public final void updateInsetsStateForDisplayCutout(
      DisplayFrames displayFrames, InsetsState insetsState) {
    DisplayCutout displayCutout = displayFrames.mInsetsState.getDisplayCutout();
    insetsState.setDisplayCutout(displayCutout);
    if (displayCutout.isEmpty()) {
      for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
        if (insetsState.sourceAt(sourceSize).getType() == WindowInsets.Type.displayCutout()) {
          insetsState.removeSourceAt(sourceSize);
        }
      }
      return;
    }
    Rect rect = displayFrames.mUnrestricted;
    Rect rect2 = displayFrames.mDisplayCutoutSafe;
    for (int sourceSize2 = insetsState.sourceSize() - 1; sourceSize2 >= 0; sourceSize2--) {
      InsetsSource sourceAt = insetsState.sourceAt(sourceSize2);
      if (sourceAt.getType() == WindowInsets.Type.displayCutout()) {
        int i = rect2.left;
        if (i != 0) {
          sourceAt.setFrame(rect.left, rect.top, i, rect.bottom);
          return;
        }
        int i2 = rect2.top;
        if (i2 != 0) {
          sourceAt.setFrame(rect.left, rect.top, rect.right, i2);
          return;
        }
        int i3 = rect2.right;
        if (i3 != 0) {
          sourceAt.setFrame(i3, rect.top, rect.right, rect.bottom);
          return;
        }
        int i4 = rect2.bottom;
        if (i4 != 0) {
          sourceAt.setFrame(rect.left, i4, rect.right, rect.bottom);
          return;
        }
        return;
      }
    }
  }

  public final DisplayFrames getFixedRotationTransformDisplayFrames(
      WindowContainer windowContainer) {
    DisplayFrames displayFrames;
    if (windowContainer instanceof ActivityRecord) {
      displayFrames = windowContainer.asActivityRecord().getFixedRotationTransformDisplayFrames();
    } else if (windowContainer instanceof WindowState) {
      WindowState asWindowState = windowContainer.asWindowState();
      ActivityRecord activityRecord = asWindowState.mActivityRecord;
      DisplayFrames fixedRotationTransformDisplayFrames =
          activityRecord != null ? activityRecord.getFixedRotationTransformDisplayFrames() : null;
      displayFrames =
          fixedRotationTransformDisplayFrames == null
              ? asWindowState.mToken.getFixedRotationTransformDisplayFrames()
              : fixedRotationTransformDisplayFrames;
    } else {
      displayFrames = null;
    }
    return displayFrames != null ? displayFrames : this.mUdcDisplayFrames;
  }

  public void onRequestedOverrideConfigurationChanged(Configuration configuration) {
    int rotation = configuration.windowConfiguration.getRotation();
    if (rotation == -1) {
      return;
    }
    Configuration configuration2 = this.mUdcConfiguration;
    if (configuration2 == null) {
      this.mUdcConfiguration = new Configuration();
    } else {
      configuration2.unset();
    }
    this.mDisplayContent.computeScreenConfiguration(this.mUdcConfiguration, rotation, true);
  }

  static {
    sUseLayoutInUdcCutoutApplications = CoreRune.SAFE_DEBUG ? new ConcurrentHashMap() : null;
    sUseLayoutInUdcCutoutActivities = CoreRune.SAFE_DEBUG ? new ConcurrentHashMap() : null;
    sUseLayoutInUdcCutoutWindows = CoreRune.SAFE_DEBUG ? new ConcurrentHashMap() : null;
  }

  /* JADX WARN: Removed duplicated region for block: B:26:0x007e  */
  /* JADX WARN: Removed duplicated region for block: B:32:0x0092  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static boolean executeShellCommandLocked(
      String str,
      String[] strArr,
      PrintWriter printWriter,
      WindowManagerService windowManagerService) {
    boolean booleanValue;
    if ("-udc_reset".equals(str)) {
      sUseLayoutInUdcCutoutApplications.clear();
      sUseLayoutInUdcCutoutActivities.clear();
      sUseLayoutInUdcCutoutWindows.clear();
      updateUseForceLayoutInUdcCutoutIfNeeded(windowManagerService);
      printWriter.println("Reset");
      return true;
    }
    int i = 0;
    if (!"-udc".equals(str) && !"-udc_a".equals(str) && !"-udc_w".equals(str)) {
      return false;
    }
    if ((strArr.length == 1 || strArr.length == 2) && !TextUtils.isEmpty(strArr[0])) {
      if (strArr.length == 2) {
        try {
          booleanValue = Boolean.valueOf(strArr[1]).booleanValue();
        } catch (Exception unused) {
        }
        printWriter.println(str + ", Enabled=" + booleanValue);
        String[] split = strArr[0].split(XmlUtils.STRING_ARRAY_SEPARATOR);
        if (!"-udc".equals(str)) {
          int length = split.length;
          while (i < length) {
            String str2 = split[i];
            sUseLayoutInUdcCutoutApplications.put(str2, Boolean.valueOf(booleanValue));
            printWriter.println(str2);
            i++;
          }
        } else if ("-udc_a".equals(str)) {
          int length2 = split.length;
          while (i < length2) {
            String str3 = split[i];
            sUseLayoutInUdcCutoutActivities.put(str3, Boolean.valueOf(booleanValue));
            printWriter.println(str3);
            i++;
          }
        } else if ("-udc_w".equals(str)) {
          int length3 = split.length;
          while (i < length3) {
            String str4 = split[i];
            sUseLayoutInUdcCutoutWindows.put(str4, Boolean.valueOf(booleanValue));
            printWriter.println(str4);
            i++;
          }
        }
        updateUseForceLayoutInUdcCutoutIfNeeded(windowManagerService);
        return true;
      }
      booleanValue = true;
      printWriter.println(str + ", Enabled=" + booleanValue);
      String[] split2 = strArr[0].split(XmlUtils.STRING_ARRAY_SEPARATOR);
      if (!"-udc".equals(str)) {}
      updateUseForceLayoutInUdcCutoutIfNeeded(windowManagerService);
      return true;
    }
    printWriter.println(
        "Error: -udc requires [packageName | packageName:packageName:...] [true/false]");
    printWriter.println(
        "Error: -udc_a requires [ComponentName | ComponentName:ComponentName:...] [true/false]");
    printWriter.println(
        "Error: -udc_w requires [WindowTitle | WindowTitle:WindowTitle:...] [true/false]");
    return true;
  }

  public static void updateUseForceLayoutInUdcCutoutIfNeeded(
      WindowManagerService windowManagerService) {
    DisplayContent defaultDisplayContentLocked =
        windowManagerService.getDefaultDisplayContentLocked();
    if (defaultDisplayContentLocked == null) {
      return;
    }
    defaultDisplayContentLocked.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.UdcCutoutPolicy$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            UdcCutoutPolicy.updateUseForceLayoutInUdcCutoutIfNeeded((ActivityRecord) obj);
          }
        });
    defaultDisplayContentLocked.forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.UdcCutoutPolicy$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            UdcCutoutPolicy.updateUseForceLayoutInUdcCutoutIfNeeded((WindowState) obj);
          }
        },
        true);
  }

  public static boolean updateUseForceLayoutInUdcCutoutIfNeeded(WindowContainer windowContainer) {
    WindowState asWindowState = windowContainer.asWindowState();
    if (asWindowState != null) {
      if (asWindowState.mActivityRecord == null) {
        String str = asWindowState.mAttrs.packageName;
        if (sUseLayoutInUdcCutoutApplications.containsKey(str)) {
          asWindowState.mUseLayoutInUdcCutout =
              ((Boolean) sUseLayoutInUdcCutoutApplications.get(str)).booleanValue();
          asWindowState.mUseConfigurationInUdcCutout = false;
          return true;
        }
        String charSequence = asWindowState.mAttrs.getTitle().toString();
        if (!TextUtils.isEmpty(charSequence)
            && sUseLayoutInUdcCutoutWindows.containsKey(charSequence)) {
          asWindowState.mUseLayoutInUdcCutout =
              ((Boolean) sUseLayoutInUdcCutoutWindows.get(charSequence)).booleanValue();
          asWindowState.mUseConfigurationInUdcCutout = false;
          return true;
        }
      }
    } else {
      ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
      if (asActivityRecord != null) {
        String str2 = asActivityRecord.packageName;
        if (sUseLayoutInUdcCutoutApplications.containsKey(str2)) {
          boolean booleanValue =
              ((Boolean) sUseLayoutInUdcCutoutApplications.get(str2)).booleanValue();
          asActivityRecord.mUseLayoutInUdcCutout = booleanValue;
          asActivityRecord.mUseConfigurationInUdcCutout = booleanValue;
          return true;
        }
        String flattenToShortString = asActivityRecord.intent.getComponent().flattenToShortString();
        if (sUseLayoutInUdcCutoutActivities.containsKey(flattenToShortString)) {
          boolean booleanValue2 =
              ((Boolean) sUseLayoutInUdcCutoutActivities.get(flattenToShortString)).booleanValue();
          asActivityRecord.mUseLayoutInUdcCutout = booleanValue2;
          asActivityRecord.mUseConfigurationInUdcCutout = booleanValue2;
          return true;
        }
      }
    }
    return false;
  }
}
