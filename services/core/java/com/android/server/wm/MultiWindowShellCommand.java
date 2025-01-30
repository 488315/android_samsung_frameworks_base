package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.ShellCommand;
import android.text.TextUtils;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class MultiWindowShellCommand extends ShellCommand {
    private static final String CMD_ADD_SPLIT_ACTIVITY_INFO = "add-split-activity";
    private static final String CMD_ALLOW_MULTIWINDOW = "allow-multiwindow";
    private static final String CMD_ASSISTANT_ACTIVITY_TO_SPLIT = "startAssistantActivity";
    private static final String CMD_BLOCK_MULTIWINDOW = "block-multiwindow";
    private static final String CMD_CORNER_GESTURE_CUSTOM_VALUE = "corner-gesture-custom-value";
    private static final String CMD_DESKTOP_MODE = "desktopmode";
    private static final String CMD_DISMISS_SPLIT_AND_START = "dismiss-split-and-start";
    private static final String CMD_DUMP_EMBED_ACTIVITY_INFO = "dump-embed-activity";
    private static final String CMD_DUMP_SPLIT_ACTIVITY_INFO = "dump-split-activity";
    private static final String CMD_GET_EMBED_ACTIVITY_PACKAGE_ENABLED = "get-embed-activity-package-enabled";
    private static final String CMD_GET_MULTI_SPLIT_FLAGS = "get-multi-split-flags";
    private static final String CMD_GET_SPLIT_ACTIVITY_PACKAGE_ENABLED = "get-split-activity-package-enabled";
    private static final String CMD_GET_TASKID_FROM_PACKAGE_NAME = "getTaskIdFromPackageName";
    private static final String CMD_MINIMIZE_ALL = "minimize-all";
    private static final String CMD_MINIMIZE_ALL_INTENT = "minimize-all-intent";
    private static final String CMD_MW_DYNAMIC_ENABLED = "support";
    private static final String CMD_REMOVE_FOCUSED_TASK = "removeFocusedTask";
    private static final String CMD_REMOVE_SPLIT_ACTIVITY_INFO = "remove-split-activity";
    private static final String CMD_SET_DEX_SIZE_COMPAT_MODE_ASPECT_RATIO_SCALE = "setDexSizeCompatModeAspectRatioScale";
    private static final String CMD_SET_DEX_SIZE_COMPAT_MODE_DEFAULT_SCALE = "setDexSizeCompatModeDefaultScale";
    private static final String CMD_SET_EMBED_ACTIVITY_PACKAGE_ENABLED = "set-embed-activity-package-enabled";
    private static final String CMD_SET_MULTISTAR_ENSURE_LAUNCH_SPLIT = "setMultiStarEnsureLaunchSplit";
    private static final String CMD_SET_NAVSTAR_IMMERSIVE_MODE = "setNavStarImmersiveMode";
    private static final String CMD_SET_SIZE_COMPAT_LAUNCH_POLICY = "setSizeCompatLaunchPolicy";
    private static final String CMD_SET_SPLIT_ACTIVITY_PACKAGE_ENABLED = "set-split-activity-package-enabled";
    static final String CMD_SHOW_ALL_COMMANDS = "showall";
    private static final String CMD_SPLIT_IMMERSIVE = "split-immersive";
    private static final String CMD_TOGGLE_FREEFORM = "toggle-freeform";
    private static final String CMD_TOP_TASK_SUPPORTS_MULTIWINDOW = "top-task-supports-mw";
    private static final String CMD_VISIBLETASKS = "visibletasks";
    private static final boolean SUPPORTS_ALL_COMMANDS = CoreRune.SAFE_DEBUG;
    private final ActivityTaskManagerService mAtm;
    private final WindowManagerGlobalLock mLock;
    private final HashMap mCommandMethods = new HashMap();
    private boolean mInitialized = false;
    private final MultiWindowManager mMultiWindowManager = new MultiWindowManager();

    @MWCommandInfo(cmd = CMD_ADD_SPLIT_ACTIVITY_INFO)
    public boolean cmdAddSplitActivityInfo(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_DUMP_SPLIT_ACTIVITY_INFO)
    public boolean cmdDumpSplitActivityInfo(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_GET_SPLIT_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdGetSplitActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_REMOVE_SPLIT_ACTIVITY_INFO)
    public boolean cmdRemoveSplitActivityInfo(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_SET_SPLIT_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdSetSplitActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    public MultiWindowShellCommand(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mLock = activityTaskManagerService.getGlobalLock();
    }

    private void initCommands() {
        if (this.mInitialized) {
            return;
        }
        for (Method method : MultiWindowShellCommand.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MWCommandInfo.class)) {
                MWCommandInfo mWCommandInfo = (MWCommandInfo) method.getAnnotation(MWCommandInfo.class);
                if (SUPPORTS_ALL_COMMANDS || mWCommandInfo.supportsReleaseBuild()) {
                    this.mCommandMethods.put(mWCommandInfo.cmd(), method);
                }
            }
        }
        this.mInitialized = true;
    }

    private boolean invokeCommand(String str, String[] strArr, PrintWriter printWriter) {
        Method method = (Method) this.mCommandMethods.get(str);
        if (method == null) {
            return false;
        }
        try {
            printWriter.println("invoke cmd=" + str + "    method=" + method.getName());
            return ((Boolean) method.invoke(this, strArr, printWriter)).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int onCommand(String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        ArrayList arrayList = new ArrayList();
        String nextArg = getNextArg();
        while (nextArg != null) {
            arrayList.add(nextArg);
            nextArg = getNextArg();
        }
        return execute(str, (String[]) arrayList.toArray(new String[0]), outPrintWriter);
    }

    public void onHelp() {
        printHelp(getOutPrintWriter());
    }

    public int execute(String str, String[] strArr, PrintWriter printWriter) {
        initCommands();
        WindowManagerGlobalLock windowManagerGlobalLock = this.mLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (str != null) {
                try {
                    if (invokeCommand(str, strArr, printWriter)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return 0;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (str != null && !"help".equals(str) && !"-h".equals(str)) {
                printWriter.println("Unknown command: " + str);
                WindowManagerService.resetPriorityAfterLockedSection();
                return -1;
            }
            printHelp(printWriter);
            WindowManagerService.resetPriorityAfterLockedSection();
            return -1;
        }
    }

    @MWCommandInfo(cmd = CMD_MW_DYNAMIC_ENABLED)
    public boolean cmdMultiWindowOnAndOff(String[] strArr, PrintWriter printWriter) {
        int currentUserId = this.mAtm.mAmInternal.getCurrentUserId();
        if (strArr != null && strArr.length > 0 && ("on".equals(strArr[0]) || "off".equals(strArr[0]))) {
            boolean equals = "on".equals(strArr[0]);
            String str = (strArr.length <= 1 || TextUtils.isEmpty(strArr[1])) ? "shell_command" : strArr[1];
            this.mAtm.mMultiWindowEnableController.setMultiWindowEnabledForUser(str, str, equals, currentUserId);
            return true;
        }
        printWriter.println("Error: support  option requires [on/off]");
        return false;
    }

    @MWCommandInfo(cmd = CMD_ALLOW_MULTIWINDOW)
    public boolean cmdAllowMultiWindow(String[] strArr, PrintWriter printWriter) {
        if (strArr != null && strArr.length >= 2) {
            String str = strArr[0];
            String str2 = strArr[1];
            if ("add".equals(str)) {
                this.mAtm.mMwSupportPolicyController.addAllowPackage(str2);
            } else if ("remove".equals(str)) {
                this.mAtm.mMwSupportPolicyController.removeAllowPackage(str2);
            }
        } else {
            printWriter.println("Error: allow-multiwindow  option requires [add/remove] [packageName]");
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_BLOCK_MULTIWINDOW)
    public boolean cmdBlockMultiWindow(String[] strArr, PrintWriter printWriter) {
        if (strArr != null && strArr.length >= 2) {
            String str = strArr[0];
            String str2 = strArr[1];
            if ("add".equals(str)) {
                this.mAtm.mMwSupportPolicyController.addBlockPackage(str2);
            } else if ("remove".equals(str)) {
                this.mAtm.mMwSupportPolicyController.removeBlockPackage(str2);
            }
        } else {
            printWriter.println("Error: block-multiwindow  option requires [add/remove] [packageName]");
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_TOP_TASK_SUPPORTS_MULTIWINDOW)
    public boolean cmdTopTaskSupportsMultiWindow(String[] strArr, PrintWriter printWriter) {
        ActivityRecord activityRecord = this.mAtm.mRootWindowContainer.topRunningActivity();
        if (activityRecord != null) {
            printWriter.println(activityRecord.toString() + " supports multiwindow = " + MultiWindowManager.getInstance().supportsMultiWindow(activityRecord.token));
            return true;
        }
        printWriter.println("top activity is null");
        return true;
    }

    @MWCommandInfo(cmd = CMD_TOGGLE_FREEFORM)
    public boolean cmdToggleFreeform(String[] strArr, PrintWriter printWriter) {
        DisplayContent topFocusedDisplayContent = this.mAtm.mRootWindowContainer.getTopFocusedDisplayContent();
        ActivityRecord activityRecord = topFocusedDisplayContent != null ? topFocusedDisplayContent.mFocusedApp : null;
        if (activityRecord != null && activityRecord.isActivityTypeStandard()) {
            this.mAtm.mActivityClientController.toggleFreeformWindowingMode(activityRecord.token);
            printWriter.println("run toggleFreeformWindowingMode, r=" + activityRecord);
            return true;
        }
        printWriter.println("failed, cannot find candidate activity");
        return true;
    }

    @MWCommandInfo(cmd = CMD_DISMISS_SPLIT_AND_START)
    public boolean cmdDismissSplitAndStart(String[] strArr, PrintWriter printWriter) {
        if (strArr != null && strArr.length > 0) {
            Intent launchIntentForPackage = this.mAtm.mContext.getPackageManager().getLaunchIntentForPackage(strArr[0]);
            launchIntentForPackage.setFlags(268435456);
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setDismissSplitBeforeLaunch(true);
            this.mAtm.mContext.startActivity(launchIntentForPackage, makeBasic.toBundle());
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_DUMP_EMBED_ACTIVITY_INFO)
    public boolean cmdDumpEmbedActivityInfo(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return false;
        }
        this.mAtm.mMultiTaskingController.dumpActivityEmbeddedPackageRepository(printWriter);
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_EMBED_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdSetEmbedActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return false;
        }
        if (strArr != null && strArr.length >= 3) {
            this.mAtm.mMultiTaskingController.setEmbedActivityPackageEnabled(strArr[0], Boolean.valueOf(strArr[1]).booleanValue(), Integer.valueOf(strArr[2]).intValue());
        } else {
            printWriter.println("Error: set-embed-activity-package-enabled  option requires [packageName, enabled, userId]");
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_GET_EMBED_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdGetEmbedActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return false;
        }
        if (strArr != null && strArr.length >= 2) {
            String str = strArr[0];
            boolean embedActivityPackageEnabled = this.mAtm.mMultiTaskingController.getEmbedActivityPackageEnabled(str, Integer.valueOf(strArr[1]).intValue());
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" activity embedding ");
            sb.append(embedActivityPackageEnabled ? "Enabled" : "Disabled");
            printWriter.println(sb.toString());
        } else {
            printWriter.println("Error: get-embed-activity-package-enabled  option requires [packageName, userId]");
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_CORNER_GESTURE_CUSTOM_VALUE)
    public boolean cmdSetCornerGestureCustomValue(String[] strArr, PrintWriter printWriter) {
        if (strArr == null || strArr.length <= 0) {
            return true;
        }
        this.mAtm.mMultiTaskingController.setCornerGestureCustomValue((int) ((this.mAtm.mContext.getResources().getDisplayMetrics().density * Integer.valueOf(strArr[0]).intValue()) + 0.5f));
        return true;
    }

    @MWCommandInfo(cmd = CMD_SHOW_ALL_COMMANDS, supportsReleaseBuild = true)
    public boolean cmdShowAllCommands(String[] strArr, final PrintWriter printWriter) {
        printWriter.println("[Commands    SUPPORTS_ALL_COMMANDS=" + SUPPORTS_ALL_COMMANDS + "]");
        this.mCommandMethods.forEach(new BiConsumer() { // from class: com.android.server.wm.MultiWindowShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MultiWindowShellCommand.lambda$cmdShowAllCommands$0(printWriter, (String) obj, (Method) obj2);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cmdShowAllCommands$0(PrintWriter printWriter, String str, Method method) {
        printWriter.println("cmd=" + str + "    method=" + method.getName());
    }

    @MWCommandInfo(cmd = CMD_GET_TASKID_FROM_PACKAGE_NAME)
    public boolean cmdGetTaskIdFromPackageName(String[] strArr, PrintWriter printWriter) {
        if (strArr == null || strArr.length <= 0) {
            return true;
        }
        Iterator it = this.mMultiWindowManager.getTaskInfoFromPackageName(strArr[0]).iterator();
        while (it.hasNext()) {
            printWriter.println("Result = " + ((ActivityManager.RecentTaskInfo) it.next()));
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_REMOVE_FOCUSED_TASK)
    public boolean cmdRemoveFocusedTask(String[] strArr, PrintWriter printWriter) {
        if (strArr == null || strArr.length <= 0) {
            return true;
        }
        printWriter.println("Result = " + this.mMultiWindowManager.removeFocusedTask(Integer.parseInt(strArr[0])));
        return true;
    }

    @MWCommandInfo(cmd = CMD_VISIBLETASKS)
    public boolean cmdGetVisibleTasks(String[] strArr, PrintWriter printWriter) {
        Iterator it = this.mMultiWindowManager.getVisibleTasks().iterator();
        while (it.hasNext()) {
            printWriter.println("Result = " + ((ActivityManager.RunningTaskInfo) it.next()));
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_MINIMIZE_ALL)
    public boolean cmdMinimizeAll(String[] strArr, PrintWriter printWriter) {
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            i = Integer.valueOf(strArr[0]).intValue();
        }
        this.mAtm.mMultiTaskingController.minimizeAllTasksLocked(i, true);
        return true;
    }

    @MWCommandInfo(cmd = CMD_MINIMIZE_ALL_INTENT)
    public boolean cmdMinimizeAllIntent(String[] strArr, PrintWriter printWriter) {
        this.mAtm.mContext.sendBroadcast(new Intent("com.samsung.android.multiwindow.MINIMIZE_ALL_BY_SYSTEM"));
        return true;
    }

    @MWCommandInfo(cmd = CMD_SPLIT_IMMERSIVE)
    public boolean cmdImmersiveSplitMode(String[] strArr, PrintWriter printWriter) {
        if (strArr != null && strArr.length > 0 && ("on".equals(strArr[0]) || "off".equals(strArr[0]))) {
            this.mAtm.mMultiWindowEnableController.setSplitImmersiveModeLocked("on".equals(strArr[0]));
            this.mAtm.mWindowManager.getDefaultDisplayContentLocked().setLayoutNeeded();
            this.mAtm.mWindowManager.requestTraversal();
            return true;
        }
        printWriter.println("Invalid argument: split-immersive  option requires [on/off]");
        return true;
    }

    @MWCommandInfo(cmd = CMD_GET_MULTI_SPLIT_FLAGS)
    public boolean cmdGetMultiSplitFlags(String[] strArr, PrintWriter printWriter) {
        printWriter.println("flags = " + ((Object) MultiWindowManager.multiSplitFlagsToString(this.mMultiWindowManager.getMultiSplitFlags())));
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_SIZE_COMPAT_LAUNCH_POLICY)
    public boolean cmdSetSizeCompatLaunchPolicy(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MT_SUPPORT_SIZE_COMPAT || strArr.length == 0) {
            return false;
        }
        SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.get();
        int intValue = Integer.valueOf(strArr[0]).intValue();
        printWriter.println("Request: launchPolicy=" + SizeCompatPolicyManager.launchPolicyToString(intValue));
        printWriter.println("Results:");
        int launchPolicy = sizeCompatPolicyManager.getLaunchPolicy();
        printWriter.println("  launchPolicy(old)=" + SizeCompatPolicyManager.launchPolicyToString(launchPolicy));
        if (intValue == launchPolicy) {
            return true;
        }
        sizeCompatPolicyManager.setLaunchPolicy(intValue);
        printWriter.println("  launchPolicy(new)=" + SizeCompatPolicyManager.launchPolicyToString(sizeCompatPolicyManager.getLaunchPolicy()));
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_DEX_SIZE_COMPAT_MODE_DEFAULT_SCALE)
    public boolean cmdSetDexSizeCompatModeDefaultScale(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MT_DEX_SIZE_COMPAT_MODE || strArr.length == 0) {
            return false;
        }
        DexSizeCompatController dexSizeCompatController = DexSizeCompatController.getInstance();
        float floatValue = Float.valueOf(strArr[0]).floatValue();
        printWriter.println("Request: defaultScale=" + floatValue);
        printWriter.println("Results:");
        float defaultScale = dexSizeCompatController.getDefaultScale();
        printWriter.println("  defaultScale(old)=" + defaultScale);
        if (floatValue == defaultScale) {
            return true;
        }
        dexSizeCompatController.setDefaultScale(floatValue);
        printWriter.println("  defaultScale(new)=" + dexSizeCompatController.getDefaultScale());
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_DEX_SIZE_COMPAT_MODE_ASPECT_RATIO_SCALE)
    public boolean cmdSetDexSizeCompatModeAspectRatioScale(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MT_DEX_SIZE_COMPAT_MODE || strArr.length <= 0) {
            return false;
        }
        DexSizeCompatController dexSizeCompatController = DexSizeCompatController.getInstance();
        float floatValue = Float.valueOf(strArr[0]).floatValue();
        printWriter.println("Request: aspectRatioScale=" + floatValue);
        printWriter.println("Results:");
        float aspectRatioScale = dexSizeCompatController.getAspectRatioScale();
        printWriter.println("  aspectRatioScale(old)=" + aspectRatioScale);
        if (floatValue == aspectRatioScale) {
            return true;
        }
        dexSizeCompatController.setAspectRatioScale(floatValue);
        printWriter.println("  aspectRatioScale(new)=" + dexSizeCompatController.getAspectRatioScale());
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_NAVSTAR_IMMERSIVE_MODE)
    public boolean cmdSetNavStarImmersiveMode(String[] strArr, PrintWriter printWriter) {
        if (strArr.length <= 0) {
            return false;
        }
        boolean parseBoolean = Boolean.parseBoolean(strArr[0]);
        printWriter.println(" enabled=" + parseBoolean);
        this.mMultiWindowManager.setNaviBarImmersiveModeLocked(parseBoolean);
        return true;
    }

    @MWCommandInfo(cmd = CMD_DESKTOP_MODE)
    public boolean cmdDesktopMode(String[] strArr, PrintWriter printWriter) {
        if (strArr.length == 0) {
            printNewDexHelp(printWriter);
            return true;
        }
        this.mAtm.mNewDexController.onCommand(strArr, printWriter);
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_MULTISTAR_ENSURE_LAUNCH_SPLIT)
    public boolean cmdSetMultiStarEnsureLaunchSplit(String[] strArr, PrintWriter printWriter) {
        if (strArr.length <= 0) {
            return false;
        }
        boolean parseBoolean = Boolean.parseBoolean(strArr[0]);
        printWriter.println(" enabled=" + parseBoolean);
        this.mMultiWindowManager.setEnsureLaunchSplitEnabled(parseBoolean);
        return true;
    }

    @MWCommandInfo(cmd = CMD_ASSISTANT_ACTIVITY_TO_SPLIT)
    public boolean cmdAssistantActivityToSplit(String[] strArr, PrintWriter printWriter) {
        Intent launchIntentForPackage;
        if (!CoreRune.MW_SUPPORT_ASSISTANT_HOT_KEY) {
            return false;
        }
        if (strArr != null && strArr.length > 0 && (launchIntentForPackage = this.mAtm.mContext.getPackageManager().getLaunchIntentForPackage(strArr[0])) != null) {
            launchIntentForPackage.setFlags(268435456);
            int multiWindowModeForAssistantHotKey = this.mAtm.mInternal.getMultiWindowModeForAssistantHotKey();
            if (multiWindowModeForAssistantHotKey == 1) {
                this.mAtm.mContext.startActivity(launchIntentForPackage, null);
            } else if (multiWindowModeForAssistantHotKey == 2) {
                this.mAtm.mInternal.startAssistantActivityToSplit(launchIntentForPackage, 0.7f);
            } else if (multiWindowModeForAssistantHotKey == 3) {
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setLaunchBounds(new Rect(100, 100, 800, 800));
                makeBasic.setForceLaunchWindowingMode(5);
                this.mAtm.mContext.startActivity(launchIntentForPackage, makeBasic.toBundle());
            }
        }
        return true;
    }

    private void printHelp(PrintWriter printWriter) {
        printWriter.println("MultiWindow Shell Commands:");
        if (CoreRune.SAFE_DEBUG) {
            printWriter.println("support [on/off] : multiwindow dynamic enable/disable");
            printWriter.println("toggle-freeform : toggle freeform mode of top focused activity");
            printWriter.println("visibletasks : visible task list");
            if (CoreRune.MT_NEW_DEX) {
                printNewDexHelp(printWriter);
            }
        }
        printWriter.println("adb shell dumpsys activity mt : print all about multi-tasking");
    }

    private static void printNewDexHelp(PrintWriter printWriter) {
        printWriter.println("desktopmode cmd options:");
        printWriter.println("  WHAT may be one of:");
        printWriter.println("  overlay : make 1 overlay display device");
        printWriter.println("  overlay2 : make 2 overlay display devices");
        printWriter.println("  switch-overlay : switch overlay display device and default display device");
        printWriter.println("  remove-display : remove desktop virtual display");
        printWriter.println("  info : print useful information for development");
    }
}
