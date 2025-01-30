package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureController;
import com.samsung.android.server.packagefeature.core.PackageFeatureSettings;
import com.samsung.android.server.util.CoreLogger;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class PackageFeatureControllerImpl extends Thread implements PackageFeatureController, PackageFeatureSettings.Callback, Consumer {
    public Function mGetFileDescriptor;
    public Handler mHandler;
    public final Object mLock;
    public CoreLogger mLogger;
    public PackageFeatures mPackageFeatures;
    public PackageFeatureSettings mSettings;
    public PackageFeatureShellCommand mShellCommand;
    public boolean mStarted;
    public final Set mTmpUpdateRequestedGroupNames;
    public final Runnable mUpdateGroupDataImmediately;
    public final Set mUpdateRequestedGroupNames;

    public abstract class LazyHolder {
        public static final PackageFeatureController sController = new PackageFeatureControllerImpl();
    }

    public static PackageFeatureController getController() {
        return LazyHolder.sController;
    }

    public PackageFeatureControllerImpl() {
        this.mLock = new Object();
        this.mUpdateRequestedGroupNames = new HashSet();
        this.mTmpUpdateRequestedGroupNames = new HashSet();
        this.mUpdateGroupDataImmediately = new Runnable() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PackageFeatureControllerImpl.this.updateGroupDataImmediately();
            }
        };
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public void startController(Context context, Handler handler, CoreLogger coreLogger) {
        synchronized (this.mLock) {
            if (this.mStarted) {
                this.mLogger.log(5, "The controller has already been started.");
                return;
            }
            this.mHandler = handler;
            this.mLogger = coreLogger;
            this.mPackageFeatures = new PackageFeatures(context, handler, this, coreLogger);
            this.mSettings = new PackageFeatureSettings(context, handler, this);
            handler.post(new Runnable() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureControllerImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    PackageFeatureControllerImpl.this.start();
                }
            });
            this.mStarted = true;
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public Set getGroupNames() {
        synchronized (this.mLock) {
            if (!this.mStarted) {
                logNotStarted("getGroupNames", null);
                return new HashSet();
            }
            return this.mPackageFeatures.getGroupNames();
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public void setFileDescriptorFunction(Function function) {
        synchronized (this.mLock) {
            this.mGetFileDescriptor = function;
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public void registerCallback(PackageFeature packageFeature, PackageFeatureCallback packageFeatureCallback) {
        synchronized (this.mLock) {
            if (!this.mStarted) {
                logNotStarted("registerCallback", null);
            } else {
                this.mPackageFeatures.getGroup(packageFeature.mGroup.mName).registerCallback(packageFeature, packageFeatureCallback);
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.mLogger.log(3, PackageFeatureGroupDataUtil.deleteLegacyFiles());
        initializeGroups();
        while (true) {
            synchronized (this.mLock) {
                try {
                    this.mLock.wait();
                    for (String str : this.mUpdateRequestedGroupNames) {
                        if (this.mGetFileDescriptor == null) {
                            break;
                        }
                        PackageFeatureGroupRecord group = this.mPackageFeatures.getGroup(str);
                        if (group != null) {
                            group.updateGroupDataFromScpm(this.mGetFileDescriptor);
                        }
                    }
                    this.mUpdateRequestedGroupNames.clear();
                } catch (Throwable th) {
                    this.mLogger.log(6, "What a Terrible Failure: " + th, th);
                }
            }
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public void updateGroupData(String str) {
        updateGroupData(str, 1000L);
    }

    public final void updateGroupData(final String str, final long j) {
        if (CoreRune.SAFE_DEBUG) {
            this.mLogger.log(3, "updateGroupData groupName=" + str);
        }
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PackageFeatureControllerImpl.this.lambda$updateGroupData$0(str, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroupData$0(String str, long j) {
        Set groupNames = getGroupNames();
        if (str == null) {
            this.mTmpUpdateRequestedGroupNames.addAll(groupNames);
        } else if (!groupNames.contains(str)) {
            return;
        } else {
            this.mTmpUpdateRequestedGroupNames.add(str);
        }
        this.mHandler.removeCallbacks(this.mUpdateGroupDataImmediately);
        this.mHandler.postDelayed(this.mUpdateGroupDataImmediately, j);
    }

    public final void updateGroupDataImmediately() {
        synchronized (this.mLock) {
            this.mUpdateRequestedGroupNames.addAll(this.mTmpUpdateRequestedGroupNames);
            this.mTmpUpdateRequestedGroupNames.clear();
            if (this.mUpdateRequestedGroupNames.isEmpty()) {
                return;
            }
            this.mLogger.log(4, "updateGroupData, Queue=" + this.mUpdateRequestedGroupNames);
            this.mLock.notifyAll();
        }
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureSettings.Callback
    public void onSettingsChanged() {
        boolean isAllFeaturesDisabled = this.mSettings.isAllFeaturesDisabled();
        synchronized (this.mLock) {
            if (this.mPackageFeatures.updateAllFeaturesDisabled(isAllFeaturesDisabled)) {
                this.mLogger.log(3, "onSettingsChanged, mAllFeaturesDisabled=" + isAllFeaturesDisabled);
            }
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public void dump(PrintWriter printWriter) {
        printWriter.println("PackageFeatureController");
        synchronized (this.mLock) {
            if (!this.mStarted) {
                logNotStarted("dump", printWriter);
            } else {
                this.mPackageFeatures.dump(printWriter, "  ");
                this.mLogger.print(printWriter, "  ");
            }
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public boolean executeShellCommand(PrintWriter printWriter, String[] strArr, String str) {
        synchronized (this.mLock) {
            if (!this.mStarted) {
                logNotStarted("executeShellCommand", printWriter);
                return false;
            }
            if (this.mShellCommand == null) {
                this.mShellCommand = new PackageFeatureShellCommand(this);
            }
            return this.mShellCommand.executeShellCommand(printWriter, strArr, str);
        }
    }

    public void initializeGroups() {
        synchronized (this.mLock) {
            this.mPackageFeatures.forAllGroups(new Consumer() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureControllerImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PackageFeatureControllerImpl.this.lambda$initializeGroups$1((PackageFeatureGroupRecord) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeGroups$1(PackageFeatureGroupRecord packageFeatureGroupRecord) {
        packageFeatureGroupRecord.initialize(new BiConsumer() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureControllerImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PackageFeatureControllerImpl.this.updateGroupData((String) obj, ((Long) obj2).longValue());
            }
        });
    }

    public String deleteCacheFiles() {
        return PackageFeatureGroupDataUtil.deleteCacheFiles();
    }

    public String executeDebugMode(PackageFeature packageFeature, String[] strArr, String str) {
        String executeDebugMode;
        synchronized (this.mLock) {
            PackageFeatureGroupRecord group = this.mPackageFeatures.getGroup(packageFeature.mGroup.mName);
            executeDebugMode = group == null ? "Group is null." : group.executeDebugMode(packageFeature.mName, strArr, str);
        }
        this.mLogger.log(3, executeDebugMode);
        return executeDebugMode;
    }

    public final void logNotStarted(String str, PrintWriter printWriter) {
        this.mLogger.log(5, str + ": The controller has not started yet.");
        if (printWriter != null) {
            printWriter.println("The controller has not started yet.");
        }
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureController
    public String getScpmVersion(String str) {
        synchronized (this.mLock) {
            PackageFeatureGroupRecord group = this.mPackageFeatures.getGroup(str);
            if (group == null) {
                return null;
            }
            return Integer.toString(group.getCurrentVersion());
        }
    }
}
