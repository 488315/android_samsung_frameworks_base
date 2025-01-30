package com.android.server.wm;

import android.content.Context;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/* loaded from: classes3.dex */
public abstract class PolicyControl {
  public static Filter sImmersiveNavigationFilter;
  public static Filter sImmersiveStatusFilter;
  public static String sSettingValue;

  public static boolean shouldApplyImmersiveStatus(WindowState windowState) {
    if (windowState == null || isKeyguardShowingAndNotOccluded(windowState.getDisplayContent())) {
      return false;
    }
    if (!(windowState.getAttrs().type == 2040
            || (windowState.getParentWindow() != null
                && windowState.getParentWindow().getAttrs().type == 2040))
        && shouldApplySplitImmersiveStatusBar(windowState)) {
      return true;
    }
    Filter filter = sImmersiveStatusFilter;
    return filter != null && filter.matches(windowState.mAttrs);
  }

  public static boolean shouldApplyImmersiveNavigation(WindowState windowState) {
    return shouldApplyImmersiveNavigation(windowState, false);
  }

  public static boolean shouldApplyImmersiveNavigation(WindowState windowState, boolean z) {
    if (windowState == null || isKeyguardShowingAndNotOccluded(windowState.getDisplayContent())) {
      return false;
    }
    boolean z2 =
        windowState.getAttrs().type == 2040
            || (windowState.getParentWindow() != null
                && windowState.getParentWindow().getAttrs().type == 2040);
    if (!z2 && shouldApplySplitImmersiveNavigation(windowState)) {
      return true;
    }
    if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS
        && !z2
        && FlexPanelController.isImmersiveVideoControlsEnabled(windowState)) {
      return true;
    }
    if (!z && windowState.isDexMode()) {
      return windowState
          .getDisplayContent()
          .mAtmService
          .mDexController
          .isDexForceImmersiveModeEnabled();
    }
    Filter filter = sImmersiveNavigationFilter;
    return filter != null && filter.matches(windowState.mAttrs);
  }

  public static boolean isKeyguardShowingAndNotOccluded(DisplayContent displayContent) {
    return (displayContent == null
            || !displayContent.mWmService.mPolicy.isKeyguardShowing()
            || displayContent.mWmService.mPolicy.isKeyguardOccluded())
        ? false
        : true;
  }

  public static boolean reloadFromSetting(Context context) {
    String str = null;
    try {
      str = Settings.Global.getStringForUser(context.getContentResolver(), "policy_control", -2);
      String str2 = sSettingValue;
      if (str2 != null && str2.equals(str)) {
        return false;
      }
      setFilters(str);
      sSettingValue = str;
      return true;
    } catch (Throwable th) {
      Slog.w("PolicyControl", "Error loading policy control, value=" + str, th);
      return false;
    }
  }

  public static void dump(String str, PrintWriter printWriter) {
    dump("sImmersiveStatusFilter", sImmersiveStatusFilter, str, printWriter);
    dump("sImmersiveNavigationFilter", sImmersiveNavigationFilter, str, printWriter);
  }

  public static void dump(String str, Filter filter, String str2, PrintWriter printWriter) {
    printWriter.print(str2);
    printWriter.print("PolicyControl.");
    printWriter.print(str);
    printWriter.print('=');
    if (filter == null) {
      printWriter.println("null");
    } else {
      filter.dump(printWriter);
      printWriter.println();
    }
  }

  public static void setFilters(String str) {
    sImmersiveStatusFilter = null;
    sImmersiveNavigationFilter = null;
    if (str == null) {
      return;
    }
    for (String str2 : str.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
      int indexOf = str2.indexOf(61);
      if (indexOf != -1) {
        String substring = str2.substring(0, indexOf);
        String substring2 = str2.substring(indexOf + 1);
        if (substring.equals("immersive.full")) {
          Filter parse = Filter.parse(substring2);
          sImmersiveNavigationFilter = parse;
          sImmersiveStatusFilter = parse;
        } else if (substring.equals("immersive.status")) {
          sImmersiveStatusFilter = Filter.parse(substring2);
        } else if (substring.equals("immersive.navigation")) {
          sImmersiveNavigationFilter = Filter.parse(substring2);
        }
      }
    }
  }

  public static boolean shouldApplySplitImmersiveNavigation(WindowState windowState) {
    return (MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
            || MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED)
        && canBeSplitImmersiveTarget(windowState);
  }

  public static boolean shouldApplySplitImmersiveStatusBar(WindowState windowState) {
    return MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
        && canBeSplitImmersiveTarget(windowState);
  }

  public static boolean canBeSplitImmersiveTarget(WindowState windowState) {
    DisplayContent displayContent = windowState.getDisplayContent();
    if (displayContent != null
        && displayContent.isDefaultDisplay
        && displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
      return windowState.getTask() == null || !windowState.getTask().isActivityTypeHomeOrRecents();
    }
    return false;
  }

  public class Filter {
    public final ArraySet mAllowList;
    public final ArraySet mBlockList;

    public Filter(ArraySet arraySet, ArraySet arraySet2) {
      this.mAllowList = arraySet;
      this.mBlockList = arraySet2;
    }

    public boolean matches(WindowManager.LayoutParams layoutParams) {
      if (layoutParams == null) {
        return false;
      }
      int i = layoutParams.type;
      boolean z = i >= 1 && i <= 99;
      if ((z && this.mBlockList.contains("apps")) || onBlockList(layoutParams.packageName)) {
        return false;
      }
      if (z && this.mAllowList.contains("apps")) {
        return true;
      }
      return onAllowList(layoutParams.packageName);
    }

    public final boolean onBlockList(String str) {
      return this.mBlockList.contains("*") || this.mBlockList.contains(str);
    }

    public final boolean onAllowList(String str) {
      return this.mAllowList.contains("*") || this.mAllowList.contains(str);
    }

    public void dump(PrintWriter printWriter) {
      printWriter.print("Filter[");
      dump("allowList", this.mAllowList, printWriter);
      printWriter.print(',');
      dump("blockList", this.mBlockList, printWriter);
      printWriter.print(']');
    }

    public final void dump(String str, ArraySet arraySet, PrintWriter printWriter) {
      printWriter.print(str);
      printWriter.print("=(");
      int size = arraySet.size();
      for (int i = 0; i < size; i++) {
        if (i > 0) {
          printWriter.print(',');
        }
        printWriter.print((String) arraySet.valueAt(i));
      }
      printWriter.print(')');
    }

    public String toString() {
      StringWriter stringWriter = new StringWriter();
      dump(new PrintWriter((Writer) stringWriter, true));
      return stringWriter.toString();
    }

    public static Filter parse(String str) {
      if (str == null) {
        return null;
      }
      ArraySet arraySet = new ArraySet();
      ArraySet arraySet2 = new ArraySet();
      for (String str2 : str.split(",")) {
        String trim = str2.trim();
        if (trim.startsWith(PackageManagerShellCommandDataLoader.STDIN_PATH) && trim.length() > 1) {
          arraySet2.add(trim.substring(1));
        } else {
          arraySet.add(trim);
        }
      }
      return new Filter(arraySet, arraySet2);
    }
  }
}
