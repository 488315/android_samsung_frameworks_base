package com.samsung.android.server.packagefeature.core;

import android.os.Build;
import android.provider.Settings;
import com.samsung.android.server.corescpm.ScpmController;
import com.samsung.android.server.corescpm.ScpmControllerImpl;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureDebugCommand;
import com.samsung.android.server.packagefeature.core.PackageFeatureShellCommand;
import com.samsung.android.server.util.CoreLogger;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatureManagerService {
    public final CoreLogger mLogger = new CoreLogger("PackageFeature", 200, "*** Logs ***", true, true);
    public final PackageFeatureController mPackageFeatureController;
    public final ScpmController mScpmController;
    public Map mTmpPackageFeatureCallbacks;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class LazyHolder {
        public static final PackageFeatureManagerService sInstance = new PackageFeatureManagerService();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScpmConsumerInfo extends ScpmController.ConsumerInfo {
        public static final String VERSION = String.valueOf(Build.VERSION.SEM_PLATFORM_INT);
    }

    public PackageFeatureManagerService() {
        ScpmController scpmController;
        ScpmConsumerInfo scpmConsumerInfo = new ScpmConsumerInfo();
        Map map = ScpmControllerImpl.sControllers;
        synchronized (ScpmControllerImpl.class) {
            Map map2 = ScpmControllerImpl.sControllers;
            scpmController = (ScpmController) ((ConcurrentHashMap) map2).get(scpmConsumerInfo);
            if (scpmController == null) {
                scpmController = new ScpmControllerImpl(scpmConsumerInfo);
                ((ConcurrentHashMap) map2).put(scpmConsumerInfo, scpmController);
            }
        }
        this.mScpmController = scpmController;
        this.mPackageFeatureController = new PackageFeatureController();
        this.mTmpPackageFeatureCallbacks = new HashMap();
    }

    public final void dump(PrintWriter printWriter) {
        ScpmControllerImpl scpmControllerImpl = (ScpmControllerImpl) this.mScpmController;
        synchronized (scpmControllerImpl.mLock) {
            printWriter.println("SCPMv2 Token=" + scpmControllerImpl.mToken);
        }
        this.mPackageFeatureController.dump(printWriter);
        printWriter.println();
    }

    public final boolean executeShellCommand(String str, String[] strArr, PrintWriter printWriter) {
        PackageFeatureDebugCommand packageFeatureDebugCommand;
        String executeDebugMode;
        PackageFeatureController packageFeatureController = this.mPackageFeatureController;
        synchronized (packageFeatureController.mLock) {
            try {
                if (!packageFeatureController.mStarted) {
                    packageFeatureController.logNotStarted(printWriter, "executeShellCommand");
                    return false;
                }
                if (packageFeatureController.mShellCommand == null) {
                    packageFeatureController.mShellCommand = new PackageFeatureShellCommand(packageFeatureController);
                }
                PackageFeatureShellCommand packageFeatureShellCommand = packageFeatureController.mShellCommand;
                packageFeatureShellCommand.getClass();
                if (!PackageFeatureShellCommand.Command.PACKAGE_FEATURE.mCommand.equals(str)) {
                    for (PackageFeature packageFeature : PackageFeature.values()) {
                        if (packageFeature.mEnabled && (packageFeatureDebugCommand = packageFeature.mDebugCommand) != null) {
                            for (String str2 : packageFeatureDebugCommand.mCommands) {
                                if (str2.equals(str)) {
                                    if (packageFeatureDebugCommand.assertValidOptions(str, strArr, printWriter)) {
                                        String[] split = strArr[0].split(":");
                                        String adjustExtra = packageFeatureDebugCommand.adjustExtra(strArr.length > 1 ? strArr[1] : null);
                                        PackageFeatureController packageFeatureController2 = packageFeatureShellCommand.mImpl;
                                        synchronized (packageFeatureController2.mLock) {
                                            try {
                                                PackageFeatureGroupRecord packageFeatureGroupRecord = (PackageFeatureGroupRecord) ((ConcurrentHashMap) packageFeatureController2.mPackageFeatures.mGroups).get(packageFeature.mGroup.mName);
                                                executeDebugMode = packageFeatureGroupRecord == null ? "Group is null." : packageFeatureGroupRecord.executeDebugMode(split, packageFeature.mName, adjustExtra);
                                            } finally {
                                            }
                                        }
                                        packageFeatureController2.mLogger.log(3, executeDebugMode, null);
                                        printWriter.println(executeDebugMode);
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }
                if (strArr.length <= 0 || !PackageFeatureShellCommand.Command.PACKAGE_FEATURE_OPTION_SHOW_PACKAGE_NAME.mCommand.equals(strArr[0])) {
                    packageFeatureShellCommand.mImpl.dump(printWriter);
                    if (strArr.length == 1) {
                        printWriter.println();
                        String str3 = strArr[0];
                        if (PackageFeatureShellCommand.Command.PACKAGE_FEATURE_OPTION_FORCE_UPDATE.mCommand.equals(str3)) {
                            printWriter.println("Started update.");
                            packageFeatureShellCommand.mImpl.initializeGroups();
                            PackageFeatureController packageFeatureController3 = packageFeatureShellCommand.mImpl;
                            packageFeatureController3.mHandler.post(new PackageFeatureController$$ExternalSyntheticLambda2(packageFeatureController3, null, 1000L));
                        } else if (PackageFeatureShellCommand.Command.PACKAGE_FEATURE_OPTION_POLICY_RESET.mCommand.equals(str3)) {
                            printWriter.println("Started reset.");
                            PackageFeatureController packageFeatureController4 = packageFeatureShellCommand.mImpl;
                            synchronized (packageFeatureController4.mLock) {
                                packageFeatureController4.mPackageFeatures.forAllGroups(new PackageFeatureController$$ExternalSyntheticLambda4());
                            }
                            printWriter.println(PackageFeatureGroupDataUtilWithEncryption.deleteCacheFiles(PackageFeatureGroupDataUtilWithEncryption.DIR_PATH));
                            packageFeatureShellCommand.mImpl.initializeGroups();
                            PackageFeatureController packageFeatureController5 = packageFeatureShellCommand.mImpl;
                            packageFeatureController5.mHandler.post(new PackageFeatureController$$ExternalSyntheticLambda2(packageFeatureController5, null, 1000L));
                        }
                    }
                } else {
                    int i = Settings.Global.getInt(packageFeatureShellCommand.mImpl.mContext.getContentResolver(), "hidden_api_policy", -1);
                    if (i == 1 || i == 2) {
                        boolean z = strArr.length == 1 || Boolean.parseBoolean(strArr[1]);
                        PackageFeatureRawData.sShowPackageName = z;
                        packageFeatureShellCommand.mImpl.dump(printWriter);
                        printWriter.println("ShowPackageName=" + z);
                    } else {
                        printWriter.println("Illegal access.");
                    }
                }
                return true;
            } finally {
            }
        }
    }
}
