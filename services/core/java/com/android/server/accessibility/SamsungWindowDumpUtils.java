package com.android.server.accessibility;

import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SamsungWindowDumpUtils {
    public static boolean hasMatchedArgument(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static void printNode(PrintWriter printWriter, AccessibilityNodeInfo accessibilityNodeInfo, int i, boolean z, boolean z2) {
        String str;
        if (accessibilityNodeInfo == null) {
            printWriter.println();
            return;
        }
        if (z2 && !accessibilityNodeInfo.isVisibleToUser()) {
            printWriter.println();
            return;
        }
        String str2 = "";
        if (z) {
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
                sb.append("V, ");
            }
            if (accessibilityNodeInfo.isAccessibilityFocused()) {
                sb.append("AFd, ");
            }
            if (accessibilityNodeInfo.isEnabled()) {
                sb.append("E, ");
            }
            if (accessibilityNodeInfo.isCheckable()) {
                sb.append("Ck, ");
            }
            if (accessibilityNodeInfo.isChecked()) {
                sb.append("Ckd, ");
            }
            if (accessibilityNodeInfo.isFocusable()) {
                sb.append("F, ");
            }
            if (accessibilityNodeInfo.isFocused()) {
                sb.append("Fd, ");
            }
            if (accessibilityNodeInfo.isSelected()) {
                sb.append("Sd, ");
            }
            if (accessibilityNodeInfo.isClickable()) {
                sb.append("C, ");
            }
            if (accessibilityNodeInfo.isLongClickable()) {
                sb.append("LC, ");
            }
            if (accessibilityNodeInfo.isContextClickable()) {
                sb.append("CC, ");
            }
            if (accessibilityNodeInfo.isPassword()) {
                sb.append("PWD, ");
            }
            if (accessibilityNodeInfo.isScrollable()) {
                sb.append("S, ");
            }
            sb.append("Actions={");
            for (AccessibilityNodeInfo.AccessibilityAction accessibilityAction : accessibilityNodeInfo.getActionList()) {
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
                        case 2048:
                            str = "PHE";
                            break;
                        case 4096:
                            str = "SF";
                            break;
                        case 8192:
                            str = "SB";
                            break;
                        case EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION /* 16384 */:
                            str = "CP";
                            break;
                        case 32768:
                            str = "PA";
                            break;
                        case EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT /* 65536 */:
                            str = "CT";
                            break;
                        case 131072:
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
                if (accessibilityAction.getLabel() != null) {
                    str = str + '(' + ((Object) accessibilityAction.getLabel()) + ')';
                }
                sb.append(str);
                sb.append(", ");
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, "}", printWriter);
        } else {
            printWriter.println(accessibilityNodeInfo.toString());
        }
        for (int i2 = -1; i2 < i; i2++) {
            str2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "  ");
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            printWriter.append((CharSequence) (str2 + "[" + i3 + "] "));
            printNode(printWriter, accessibilityNodeInfo.getChild(i3), i + 1, z, z2);
        }
    }

    public static void printNodeTreeOfWindow(PrintWriter printWriter, AccessibilityWindowInfo accessibilityWindowInfo, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder("<window id:");
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
        int type = accessibilityWindowInfo.getType();
        sb.append(type != 1 ? type != 2 ? type != 3 ? type != 4 ? type != 5 ? "TYPE_Unknown" : "TYPE_SPLIT_SCREEN_DIVIDER" : "TYPE_ACCESSIBILITY_OVERLAY" : "TYPE_SYSTEM" : "TYPE_INPUT_METHOD" : "TYPE_APPLICATION");
        sb.append(", ");
        if (accessibilityWindowInfo.isActive()) {
            sb.append("Active, ");
        }
        if (accessibilityWindowInfo.isFocused()) {
            sb.append("Focused, ");
        }
        sb.append('>');
        printWriter.println(sb.toString());
        printWriter.append("[root] ");
        printNode(printWriter, root, 0, z, z2);
    }
}
