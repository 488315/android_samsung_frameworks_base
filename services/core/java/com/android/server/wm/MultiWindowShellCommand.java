package com.android.server.wm;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.ShellCommand;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.wm.DexSizeCompatController;
import com.android.server.wm.SizeCompatPolicyManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class MultiWindowShellCommand extends ShellCommand {
    private static final String CMD_ADD_SPLIT_ACTIVITY_INFO = "add-split-activity";
    private static final String CMD_ALLOW_MULTIWINDOW = "allow-multiwindow";
    private static final String CMD_BLOCK_MULTIWINDOW = "block-multiwindow";
    private static final String CMD_DUMP_EMBED_ACTIVITY_INFO = "dump-embed-activity";
    private static final String CMD_DUMP_SPLIT_ACTIVITY_INFO = "dump-split-activity";
    private static final String CMD_GET_EMBED_ACTIVITY_PACKAGE_ENABLED = "get-embed-activity-package-enabled";
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
    private static final String CMD_SHOW_ALL_COMMANDS = "showall";
    private static final String CMD_SPLIT_IMMERSIVE = "split-immersive";
    private static final String CMD_TOP_TASK_SUPPORTS_MULTIWINDOW = "top-task-supports-mw";
    private static final String CMD_VISIBLETASKS = "visibletasks";
    private static final boolean SUPPORTS_ALL_COMMANDS = false;
    private final ActivityTaskManagerService mAtm;
    private final WindowManagerGlobalLock mLock;
    private final HashMap mCommandMethods = new HashMap();
    private boolean mInitialized = false;
    private final MultiWindowManager mMultiWindowManager = new MultiWindowManager();

    public MultiWindowShellCommand(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mLock = activityTaskManagerService.mGlobalLock;
    }

    private void initCommands() {
        if (this.mInitialized) {
            return;
        }
        for (Method method : MultiWindowShellCommand.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MWCommandInfo.class)) {
                MWCommandInfo mWCommandInfo = (MWCommandInfo) method.getAnnotation(MWCommandInfo.class);
                if (mWCommandInfo.supportsReleaseBuild()) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cmdShowAllCommands$0(PrintWriter printWriter, String str, Method method) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("cmd=", str, "    method=");
        m.append(method.getName());
        printWriter.println(m.toString());
    }

    private void printHelp(PrintWriter printWriter) {
        printWriter.println("MultiWindow Shell Commands:");
        printWriter.println("adb shell dumpsys activity mt : print all about multi-tasking");
    }

    @MWCommandInfo(cmd = CMD_ADD_SPLIT_ACTIVITY_INFO)
    public boolean cmdAddSplitActivityInfo(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_ALLOW_MULTIWINDOW)
    public boolean cmdAllowMultiWindow(String[] strArr, PrintWriter printWriter) {
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository;
        if (strArr == null || strArr.length < 2) {
            printWriter.println("Error: allow-multiwindow  option requires [add/remove] [packageName]");
        } else {
            String str = strArr[0];
            String str2 = strArr[1];
            if ("add".equals(str)) {
                MultiWindowSupportPolicyController multiWindowSupportPolicyController = this.mAtm.mMwSupportPolicyController;
                if (multiWindowSupportPolicyController.mAllowListRepository == null) {
                    multiWindowSupportPolicyController.mAllowListRepository = new MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository(0, multiWindowSupportPolicyController.mAtm, "AllowList");
                }
                MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 = multiWindowSupportPolicyController.mAllowListRepository;
                if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 != null) {
                    multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2.addPackage(str2);
                }
            } else if ("remove".equals(str) && (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository = this.mAtm.mMwSupportPolicyController.mAllowListRepository) != null) {
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository.removePackage(str2);
            }
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_BLOCK_MULTIWINDOW)
    public boolean cmdBlockMultiWindow(String[] strArr, PrintWriter printWriter) {
        MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository;
        if (strArr == null || strArr.length < 2) {
            printWriter.println("Error: block-multiwindow  option requires [add/remove] [packageName]");
        } else {
            String str = strArr[0];
            String str2 = strArr[1];
            if ("add".equals(str)) {
                MultiWindowSupportPolicyController multiWindowSupportPolicyController = this.mAtm.mMwSupportPolicyController;
                if (multiWindowSupportPolicyController.mBlockListRepository == null) {
                    MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2 = new MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository(1, multiWindowSupportPolicyController.mAtm, "BlockList");
                    PackageFeature.DISPLAY_COMPAT.registerCallback(multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2);
                    multiWindowSupportPolicyController.mBlockListRepository = multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository2;
                }
                MultiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository3 = multiWindowSupportPolicyController.mBlockListRepository;
                if (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository3 != null) {
                    multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository3.addPackage(str2);
                }
            } else if ("remove".equals(str) && (multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository = this.mAtm.mMwSupportPolicyController.mBlockListRepository) != null) {
                multiWindowSupportPolicyController$MultiWindowSupportRepository$AllowListRepository.removePackage(str2);
            }
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_DUMP_EMBED_ACTIVITY_INFO)
    public boolean cmdDumpEmbedActivityInfo(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return false;
        }
        ActivityEmbeddedPackageRepository activityEmbeddedPackageRepository = this.mAtm.mMultiTaskingController.mActivityEmbeddedPackageRepository;
        synchronized (activityEmbeddedPackageRepository) {
            printWriter.print("    ");
            printWriter.print("ActivityEmbeddedPackageRepository : ");
            if (((ArrayList) activityEmbeddedPackageRepository.mRepository).isEmpty()) {
                printWriter.println("Empty");
                return true;
            }
            printWriter.print(((ArrayList) activityEmbeddedPackageRepository.mRepository).size());
            printWriter.println();
            Iterator it = ((ArrayList) activityEmbeddedPackageRepository.mRepository).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                printWriter.print("    ");
                printWriter.print("    ");
                printWriter.print(str);
                printWriter.println();
            }
            return true;
        }
    }

    @MWCommandInfo(cmd = CMD_DUMP_SPLIT_ACTIVITY_INFO)
    public boolean cmdDumpSplitActivityInfo(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_GET_EMBED_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdGetEmbedActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return false;
        }
        if (strArr == null || strArr.length < 2) {
            printWriter.println("Error: get-embed-activity-package-enabled  option requires [packageName, userId]");
        } else {
            String str = strArr[0];
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, " activity embedding "), this.mAtm.mMultiTaskingController.getEmbedActivityPackageEnabled(str, Integer.valueOf(strArr[1]).intValue()) ? "Enabled" : "Disabled", printWriter);
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_GET_SPLIT_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdGetSplitActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        return false;
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

    @MWCommandInfo(cmd = CMD_VISIBLETASKS)
    public boolean cmdGetVisibleTasks(String[] strArr, PrintWriter printWriter) {
        Iterator it = this.mMultiWindowManager.getVisibleTasks().iterator();
        while (it.hasNext()) {
            printWriter.println("Result = " + ((ActivityManager.RunningTaskInfo) it.next()));
        }
        return true;
    }

    @MWCommandInfo(cmd = CMD_SPLIT_IMMERSIVE)
    public boolean cmdImmersiveSplitMode(String[] strArr, PrintWriter printWriter) {
        if (strArr == null || strArr.length <= 0 || !("on".equals(strArr[0]) || "off".equals(strArr[0]))) {
            printWriter.println("Invalid argument: split-immersive  option requires [on/off]");
            return true;
        }
        this.mAtm.mMultiWindowEnableController.setSplitImmersiveModeLocked("on".equals(strArr[0]));
        this.mAtm.mWindowManager.getDefaultDisplayContentLocked().setLayoutNeeded();
        this.mAtm.mWindowManager.requestTraversal();
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

    @MWCommandInfo(cmd = CMD_MW_DYNAMIC_ENABLED)
    public boolean cmdMultiWindowOnAndOff(String[] strArr, PrintWriter printWriter) {
        int currentUserId = this.mAtm.mAmInternal.getCurrentUserId();
        if (strArr == null || strArr.length <= 0 || !("on".equals(strArr[0]) || "off".equals(strArr[0]))) {
            printWriter.println("Error: support  option requires [on/off]");
            return false;
        }
        boolean equals = "on".equals(strArr[0]);
        String str = (strArr.length <= 1 || TextUtils.isEmpty(strArr[1])) ? "shell_command" : strArr[1];
        MultiWindowEnableController multiWindowEnableController = this.mAtm.mMultiWindowEnableController;
        WindowManagerGlobalLock windowManagerGlobalLock = multiWindowEnableController.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                multiWindowEnableController.setEnableForUser(currentUserId, str, str, equals);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
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

    @MWCommandInfo(cmd = CMD_REMOVE_SPLIT_ACTIVITY_INFO)
    public boolean cmdRemoveSplitActivityInfo(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_SET_DEX_SIZE_COMPAT_MODE_ASPECT_RATIO_SCALE)
    public boolean cmdSetDexSizeCompatModeAspectRatioScale(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MT_DEX_SIZE_COMPAT_MODE || strArr.length <= 0) {
            return false;
        }
        DexSizeCompatController dexSizeCompatController = DexSizeCompatController.LazyHolder.sInstance;
        float floatValue = Float.valueOf(strArr[0]).floatValue();
        printWriter.println("Request: aspectRatioScale=" + floatValue);
        printWriter.println("Results:");
        float f = dexSizeCompatController.mAspectRatioScale;
        printWriter.println("  aspectRatioScale(old)=" + f);
        if (floatValue == f) {
            return true;
        }
        dexSizeCompatController.mAspectRatioScale = floatValue;
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(new StringBuilder("  aspectRatioScale(new)="), dexSizeCompatController.mAspectRatioScale, printWriter);
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_DEX_SIZE_COMPAT_MODE_DEFAULT_SCALE)
    public boolean cmdSetDexSizeCompatModeDefaultScale(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MT_DEX_SIZE_COMPAT_MODE || strArr.length == 0) {
            return false;
        }
        DexSizeCompatController dexSizeCompatController = DexSizeCompatController.LazyHolder.sInstance;
        float floatValue = Float.valueOf(strArr[0]).floatValue();
        printWriter.println("Request: defaultScale=" + floatValue);
        printWriter.println("Results:");
        float f = dexSizeCompatController.mDefaultScale;
        printWriter.println("  defaultScale(old)=" + f);
        if (floatValue == f) {
            return true;
        }
        dexSizeCompatController.mDefaultScale = floatValue;
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(new StringBuilder("  defaultScale(new)="), dexSizeCompatController.mDefaultScale, printWriter);
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_EMBED_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdSetEmbedActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MW_EMBED_ACTIVITY_PACKAGE_ENABLED) {
            return false;
        }
        if (strArr == null || strArr.length < 3) {
            printWriter.println("Error: set-embed-activity-package-enabled  option requires [packageName, enabled, userId]");
        } else {
            this.mAtm.mMultiTaskingController.setEmbedActivityPackageEnabled(strArr[0], Boolean.valueOf(strArr[1]).booleanValue(), Integer.valueOf(strArr[2]).intValue());
        }
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

    @MWCommandInfo(cmd = CMD_SET_SIZE_COMPAT_LAUNCH_POLICY)
    public boolean cmdSetSizeCompatLaunchPolicy(String[] strArr, PrintWriter printWriter) {
        if (!CoreRune.MT_SIZE_COMPAT_POLICY || strArr.length == 0) {
            return false;
        }
        SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.LazyHolder.sManager;
        int intValue = Integer.valueOf(strArr[0]).intValue();
        printWriter.println("Request: launchPolicy=" + Integer.toString(intValue));
        printWriter.println("Results:");
        int i = sizeCompatPolicyManager.mLaunchPolicy;
        printWriter.println("  launchPolicy(old)=" + Integer.toString(i));
        if (intValue == i) {
            return true;
        }
        if (intValue != sizeCompatPolicyManager.mLaunchPolicy && intValue >= 0 && intValue <= 2) {
            Slog.d("SizeCompatPolicy", "LaunchPolicy is changed from " + sizeCompatPolicyManager.mLaunchPolicy + " to " + intValue);
            sizeCompatPolicyManager.mLaunchPolicy = intValue;
        }
        printWriter.println("  launchPolicy(new)=" + Integer.toString(sizeCompatPolicyManager.mLaunchPolicy));
        return true;
    }

    @MWCommandInfo(cmd = CMD_SET_SPLIT_ACTIVITY_PACKAGE_ENABLED)
    public boolean cmdSetSplitActivityPackageEnabled(String[] strArr, PrintWriter printWriter) {
        return false;
    }

    @MWCommandInfo(cmd = CMD_SHOW_ALL_COMMANDS, supportsReleaseBuild = true)
    public boolean cmdShowAllCommands(String[] strArr, final PrintWriter printWriter) {
        printWriter.println("[Commands    SUPPORTS_ALL_COMMANDS=false]");
        this.mCommandMethods.forEach(new BiConsumer() { // from class: com.android.server.wm.MultiWindowShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MultiWindowShellCommand.lambda$cmdShowAllCommands$0(printWriter, (String) obj, (Method) obj2);
            }
        });
        return true;
    }

    @MWCommandInfo(cmd = CMD_TOP_TASK_SUPPORTS_MULTIWINDOW)
    public boolean cmdTopTaskSupportsMultiWindow(String[] strArr, PrintWriter printWriter) {
        ActivityRecord activityRecord = this.mAtm.mRootWindowContainer.topRunningActivity();
        if (activityRecord == null) {
            printWriter.println("top activity is null");
            return true;
        }
        printWriter.println(activityRecord.toString() + " supports multiwindow = " + MultiWindowManager.getInstance().supportsMultiWindow(activityRecord.token));
        return true;
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
                printWriter.println("Unknown command: ".concat(str));
                WindowManagerService.resetPriorityAfterLockedSection();
                return -1;
            }
            printHelp(printWriter);
            WindowManagerService.resetPriorityAfterLockedSection();
            return -1;
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
}
