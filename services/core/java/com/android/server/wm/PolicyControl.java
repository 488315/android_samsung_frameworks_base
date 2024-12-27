package com.android.server.wm;

import android.util.ArraySet;
import android.view.WindowManager;

import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.policy.PhoneWindowManager;

import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public abstract class PolicyControl {
    public static Filter sImmersiveNavigationFilter;
    public static Filter sImmersiveStatusFilter;
    public static String sSettingValue;

    public final class Filter {
        public final ArraySet mAllowList;
        public final ArraySet mBlockList;

        public Filter(ArraySet arraySet, ArraySet arraySet2) {
            this.mAllowList = arraySet;
            this.mBlockList = arraySet2;
        }

        public static void dump(String str, ArraySet arraySet, PrintWriter printWriter) {
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

        public static Filter parse(String str) {
            if (str == null) {
                return null;
            }
            ArraySet arraySet = new ArraySet();
            ArraySet arraySet2 = new ArraySet();
            for (String str2 : str.split(",")) {
                String trim = str2.trim();
                if (!trim.startsWith(PackageManagerShellCommandDataLoader.STDIN_PATH)
                        || trim.length() <= 1) {
                    arraySet.add(trim);
                } else {
                    arraySet2.add(trim.substring(1));
                }
            }
            return new Filter(arraySet, arraySet2);
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.print("Filter[");
            dump("allowList", this.mAllowList, printWriter);
            printWriter.print(',');
            dump("blockList", this.mBlockList, printWriter);
            printWriter.print(']');
        }

        public final boolean matches(WindowManager.LayoutParams layoutParams) {
            if (layoutParams == null) {
                return false;
            }
            int i = layoutParams.type;
            boolean z = i >= 1 && i <= 99;
            if (z && this.mBlockList.contains("apps")) {
                return false;
            }
            String str = layoutParams.packageName;
            if (this.mBlockList.contains("*") || this.mBlockList.contains(str)) {
                return false;
            }
            if (z && this.mAllowList.contains("apps")) {
                return true;
            }
            return this.mAllowList.contains("*")
                    || this.mAllowList.contains(layoutParams.packageName);
        }

        public final String toString() {
            StringWriter stringWriter = new StringWriter();
            dump(new PrintWriter((Writer) stringWriter, true));
            return stringWriter.toString();
        }
    }

    public static boolean canBeSplitImmersiveTarget(WindowState windowState) {
        DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent != null
                && displayContent.isDefaultDisplay
                && displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
            return windowState.getTask() == null
                    || !windowState.getTask().isActivityTypeHomeOrRecents();
        }
        return false;
    }

    public static void dump(String str, Filter filter, PrintWriter printWriter) {
        printWriter.print("  ");
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

    public static boolean isKeyguardShowingAndNotOccluded(DisplayContent displayContent) {
        return (displayContent == null
                        || !((PhoneWindowManager) displayContent.mWmService.mPolicy)
                                .isKeyguardShowing()
                        || ((PhoneWindowManager) displayContent.mWmService.mPolicy)
                                .isKeyguardOccluded())
                ? false
                : true;
    }

    public static void setFilters(String str) {
        sImmersiveStatusFilter = null;
        sImmersiveNavigationFilter = null;
        if (str == null) {
            return;
        }
        for (String str2 : str.split(":")) {
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

    public static boolean shouldApplyImmersiveNavigation(WindowState windowState, boolean z) {
        if (windowState == null
                || isKeyguardShowingAndNotOccluded(windowState.getDisplayContent())) {
            return false;
        }
        if (windowState.mAttrs.type != 2040
                && ((windowState.getParentWindow() == null
                                || windowState.getParentWindow().mAttrs.type != 2040)
                        && ((MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
                                        || MultiWindowCoreState
                                                .MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED)
                                && canBeSplitImmersiveTarget(windowState)))) {
            return true;
        }
        if (!z && windowState.isDexMode()) {
            return windowState.getDisplayContent()
                    .mAtmService
                    .mDexController
                    .mIsDexForceImmersiveModeEnabled;
        }
        Filter filter = sImmersiveNavigationFilter;
        return filter != null && filter.matches(windowState.mAttrs);
    }

    public static boolean shouldApplyImmersiveStatus(WindowState windowState) {
        if (windowState == null
                || isKeyguardShowingAndNotOccluded(windowState.getDisplayContent())) {
            return false;
        }
        boolean z =
                windowState.mAttrs.type == 2040
                        || (windowState.getParentWindow() != null
                                && windowState.getParentWindow().mAttrs.type == 2040);
        if (CoreRune.MW_SPLIT_FLEX_PANEL_SYSTEMUI_VISIBILITY
                && !z
                && FlexPanelController.isFlexPanelTopEnabled(windowState)) {
            return true;
        }
        if (!z
                && MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
                && canBeSplitImmersiveTarget(windowState)) {
            return true;
        }
        Filter filter = sImmersiveStatusFilter;
        return filter != null && filter.matches(windowState.mAttrs);
    }
}
