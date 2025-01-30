package com.android.server.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.p005os.IInstalld;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import java.io.PrintWriter;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class SamsungWindowDumpUtils {
  public static String windowTypeToString(int i) {
    return i != 1
        ? i != 2
            ? i != 3
                ? i != 4
                    ? i != 5 ? "TYPE_Unknown" : "TYPE_SPLIT_SCREEN_DIVIDER"
                    : "TYPE_ACCESSIBILITY_OVERLAY"
                : "TYPE_SYSTEM"
            : "TYPE_INPUT_METHOD"
        : "TYPE_APPLICATION";
  }

  public static boolean isDebuggableBinary() {
    String str = Build.TYPE;
    return "eng".equals(str) || "userdebug".equals(str);
  }

  public static boolean isCustomDumpCommands(String[] strArr) {
    if (isDebuggableBinary()) {
      return isPrintWindows(strArr) || isPrintNodes(strArr);
    }
    return false;
  }

  public static boolean hasMatchedArgument(String str, String[] strArr) {
    for (String str2 : strArr) {
      if (str.equals(str2)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isPrintWindows(String[] strArr) {
    return hasMatchedArgument("print-accessibilitywindowinfo", strArr);
  }

  public static boolean isPrintNodes(String[] strArr) {
    return hasMatchedArgument("print-accessibilitynodeinfo", strArr);
  }

  public static boolean isPrintNodeSimple(String[] strArr) {
    return hasMatchedArgument("simple", strArr);
  }

  public static boolean isPrintNodeVisibleOnly(String[] strArr) {
    return hasMatchedArgument("visibleOnly", strArr);
  }

  public static int getTargetWindowId(String[] strArr) {
    if (strArr != null && strArr.length >= 3) {
      try {
        return Integer.valueOf(strArr[3]).intValue();
      } catch (Exception unused) {
      }
    }
    return -1;
  }

  public static int getConnectionId(String[] strArr) {
    if (strArr != null && strArr.length >= 4) {
      try {
        return Integer.valueOf(strArr[4]).intValue();
      } catch (Exception unused) {
      }
    }
    return -1;
  }

  public static String[] createArgsWithConnectionId(String[] strArr, int i) {
    String[] strArr2 = new String[5];
    if (strArr != null) {
      int length = strArr.length < 4 ? strArr.length : 4;
      for (int i2 = 0; i2 < length; i2++) {
        strArr2[i2] = strArr[i2];
      }
    }
    strArr2[4] = Integer.toString(i);
    return strArr2;
  }

  public static void printNodeTreeOfWindow(
      PrintWriter printWriter,
      AccessibilityWindowInfo accessibilityWindowInfo,
      boolean z,
      boolean z2) {
    StringBuilder sb = new StringBuilder();
    sb.append("<window id:");
    sb.append(accessibilityWindowInfo.getId());
    sb.append(", ");
    if (accessibilityWindowInfo.getTitle() != null) {
      sb.append(accessibilityWindowInfo.getTitle());
      sb.append(", ");
    }
    AccessibilityNodeInfo root = accessibilityWindowInfo.getRoot();
    if (root != null && root.getPackageName() != null) {
      sb.append(root.getPackageName());
      sb.append(", ");
    }
    sb.append(windowTypeToString(accessibilityWindowInfo.getType()));
    sb.append(", ");
    if (accessibilityWindowInfo.isActive()) {
      sb.append("Active");
      sb.append(", ");
    }
    if (accessibilityWindowInfo.isFocused()) {
      sb.append("Focused");
      sb.append(", ");
    }
    sb.append('>');
    printWriter.println(sb.toString());
    printWriter.append("[root] ");
    printNode(printWriter, root, 0, z, z2);
  }

  public static String nodeToSimpleString(AccessibilityNodeInfo accessibilityNodeInfo) {
    StringBuilder sb = new StringBuilder();
    if (accessibilityNodeInfo.getClassName() != null) {
      sb.append(accessibilityNodeInfo.getClassName());
      sb.append(", ");
    }
    Rect rect = new Rect();
    accessibilityNodeInfo.getBoundsInScreen(rect);
    sb.append(rect);
    sb.append(", ");
    if (accessibilityNodeInfo.getText() != null) {
      sb.append("T:");
      sb.append(accessibilityNodeInfo.getText());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.getError() != null) {
      sb.append("Err:");
      sb.append(accessibilityNodeInfo.getError());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.getMaxTextLength() != -1) {
      sb.append("MTL:");
      sb.append(accessibilityNodeInfo.getMaxTextLength());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.getStateDescription() != null) {
      sb.append("SD:");
      sb.append(accessibilityNodeInfo.getStateDescription());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.getContentDescription() != null) {
      sb.append("CD:");
      sb.append(accessibilityNodeInfo.getContentDescription());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.getTooltipText() != null) {
      sb.append("TTT:");
      sb.append(accessibilityNodeInfo.getTooltipText());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.getViewIdResourceName() != null) {
      sb.append("VID:");
      sb.append(accessibilityNodeInfo.getViewIdResourceName());
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isVisibleToUser()) {
      sb.append("V");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isAccessibilityFocused()) {
      sb.append("AFd");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isEnabled()) {
      sb.append("E");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isCheckable()) {
      sb.append("Ck");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isChecked()) {
      sb.append("Ckd");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isFocusable()) {
      sb.append("F");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isFocused()) {
      sb.append("Fd");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isSelected()) {
      sb.append("Sd");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isClickable()) {
      sb.append("C");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isLongClickable()) {
      sb.append("LC");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isContextClickable()) {
      sb.append("CC");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isPassword()) {
      sb.append("PWD");
      sb.append(", ");
    }
    if (accessibilityNodeInfo.isScrollable()) {
      sb.append("S");
      sb.append(", ");
    }
    sb.append("Actions={");
    Iterator<AccessibilityNodeInfo.AccessibilityAction> it =
        accessibilityNodeInfo.getActionList().iterator();
    while (it.hasNext()) {
      sb.append(actionToString(it.next()));
      sb.append(", ");
    }
    sb.append("}");
    return sb.toString();
  }

  public static void printNode(
      PrintWriter printWriter,
      AccessibilityNodeInfo accessibilityNodeInfo,
      int i,
      boolean z,
      boolean z2) {
    if (accessibilityNodeInfo == null) {
      printWriter.println();
      return;
    }
    if (z2 && !accessibilityNodeInfo.isVisibleToUser()) {
      printWriter.println();
      return;
    }
    if (z) {
      printWriter.println(nodeToSimpleString(accessibilityNodeInfo));
    } else {
      printWriter.println(accessibilityNodeInfo.toString());
    }
    String str = "";
    for (int i2 = -1; i2 < i; i2++) {
      str = str + "  ";
    }
    int childCount = accessibilityNodeInfo.getChildCount();
    for (int i3 = 0; i3 < childCount; i3++) {
      printWriter.append((CharSequence) (str + "[" + i3 + "] "));
      printNode(printWriter, accessibilityNodeInfo.getChild(i3), i + 1, z, z2);
    }
  }

  public static String actionToString(
      AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
    String str;
    int id = accessibilityAction.getId();
    if (id == 1) {
      str = "F";
    } else if (id != 2) {
      switch (id) {
        case 4:
          str = "S";
          break;
        case 8:
          str = "CS";
          break;
        case 16:
          str = "C";
          break;
        case 32:
          str = "LC";
          break;
        case 64:
          str = "AF";
          break;
        case 128:
          str = "CAF";
          break;
        case 256:
          str = "NMG";
          break;
        case 512:
          str = "PMG";
          break;
        case 1024:
          str = "NHE";
          break;
        case IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES /* 2048 */:
          str = "PHE";
          break;
        case IInstalld.FLAG_USE_QUOTA /* 4096 */:
          str = "SF";
          break;
        case IInstalld.FLAG_FORCE /* 8192 */:
          str = "SB";
          break;
        case 16384:
          str = "CP";
          break;
        case 32768:
          str = "PA";
          break;
        case 65536:
          str = "CT";
          break;
        case IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES /* 131072 */:
          str = "SS";
          break;
        case 262144:
          str = "AEXP";
          break;
        case 524288:
          str = "CLP";
          break;
        case 1048576:
          str = "DIS";
          break;
        case 2097152:
          str = "ST";
          break;
        default:
          str = "";
          break;
      }
    } else {
      str = "CF";
    }
    if (accessibilityAction.getLabel() == null) {
      return str;
    }
    return str + '(' + ((Object) accessibilityAction.getLabel()) + ')';
  }
}
