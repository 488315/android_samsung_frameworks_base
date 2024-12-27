package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatureController extends Thread
        implements Consumer, PackageFeatureSettings.Callback {
    public Context mContext;
    public Function mGetFileDescriptor;
    public Handler mHandler;
    public CoreLogger mLogger;
    public PackageFeatures mPackageFeatures;
    public PackageFeatureSettings mSettings;
    public PackageFeatureShellCommand mShellCommand;
    public boolean mStarted;
    public final Object mLock = new Object();
    public final Set mUpdateRequestedGroupNames = new HashSet();
    public final Set mTmpUpdateRequestedGroupNames = new HashSet();
    public final PackageFeatureController$$ExternalSyntheticLambda0 mUpdateGroupDataImmediately =
            new PackageFeatureController$$ExternalSyntheticLambda0(this, 0);

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.mHandler.post(
                new PackageFeatureController$$ExternalSyntheticLambda2(this, (String) obj, 1000L));
    }

    public final void dump(final PrintWriter printWriter) {
        printWriter.println("PackageFeatureController");
        synchronized (this.mLock) {
            try {
                if (!this.mStarted) {
                    logNotStarted(printWriter, "dump");
                    return;
                }
                PackageFeatures packageFeatures = this.mPackageFeatures;
                packageFeatures.getClass();
                printWriter.println(
                        "  mAllFeaturesDisabled=" + packageFeatures.mAllFeaturesDisabled);
                packageFeatures.forAllGroups(
                        new Consumer() { // from class:
                                         // com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda3
                            public final /* synthetic */ String f$1 = "  ";

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PrintWriter printWriter2 = printWriter;
                                String str = this.f$1;
                                PackageFeatureGroupRecord packageFeatureGroupRecord =
                                        (PackageFeatureGroupRecord) obj;
                                packageFeatureGroupRecord.getClass();
                                printWriter2.print(str);
                                printWriter2.print("GroupName=");
                                PackageFeatureGroup packageFeatureGroup =
                                        packageFeatureGroupRecord.mGroup;
                                printWriter2.print(packageFeatureGroup.mName);
                                if (packageFeatureGroup.mUnformatted) {
                                    printWriter2.print(", Unformatted=true");
                                }
                                printWriter2.print(", Version=");
                                printWriter2.print(packageFeatureGroupRecord.getCurrentVersion());
                                printWriter2.println(
                                        ", Source="
                                                + packageFeatureGroupRecord.mGroupDataSource
                                                        .name());
                                PackageFeatureGroupData packageFeatureGroupData =
                                        packageFeatureGroupRecord.mGroupData;
                                if (packageFeatureGroupData == null
                                        || packageFeatureGroupData
                                                == packageFeatureGroupRecord.mGroupDataDummy
                                        || ((Boolean)
                                                        packageFeatureGroupRecord
                                                                .mIsAllFeaturesDisabled.get())
                                                .booleanValue()) {
                                    return;
                                }
                                packageFeatureGroupRecord.mGroupData.dump(
                                        printWriter2,
                                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                                str, "  "),
                                        ((ConcurrentHashMap) packageFeatureGroupRecord.mCallbacks)
                                                .keySet());
                            }
                        });
                CoreLogger coreLogger = this.mLogger;
                List list = coreLogger.mBuffer;
                if (list == null) {
                    return;
                }
                synchronized (list) {
                    try {
                        if (!coreLogger.mBuffer.isEmpty()) {
                            printWriter.println("  " + coreLogger.mDumpTitle);
                            coreLogger.mBuffer.forEach(
                                    new Consumer() { // from class:
                                                     // com.samsung.android.server.util.CoreLogger$$ExternalSyntheticLambda0
                                        public final /* synthetic */ String f$1 = "  ";

                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            printWriter.println(this.f$1 + ((String) obj));
                                        }
                                    });
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final Set getGroupNames() {
        synchronized (this.mLock) {
            try {
                if (this.mStarted) {
                    return ((ConcurrentHashMap) this.mPackageFeatures.mGroups).keySet();
                }
                logNotStarted(null, "getGroupNames");
                return new HashSet();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void initializeGroups() {
        synchronized (this.mLock) {
            this.mPackageFeatures.forAllGroups(
                    new Consumer() { // from class:
                                     // com.samsung.android.server.packagefeature.core.PackageFeatureController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            final PackageFeatureController packageFeatureController =
                                    PackageFeatureController.this;
                            PackageFeatureGroupRecord packageFeatureGroupRecord =
                                    (PackageFeatureGroupRecord) obj;
                            packageFeatureController.getClass();
                            packageFeatureGroupRecord.mGroupDataUtil.mConsumerForFailed =
                                    new BiConsumer() { // from class:
                                                       // com.samsung.android.server.packagefeature.core.PackageFeatureController$$ExternalSyntheticLambda3
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj2, Object obj3) {
                                            PackageFeatureController packageFeatureController2 =
                                                    PackageFeatureController.this;
                                            packageFeatureController2.mHandler.post(
                                                    new PackageFeatureController$$ExternalSyntheticLambda2(
                                                            packageFeatureController2,
                                                            (String) obj2,
                                                            ((Long) obj3).longValue()));
                                        }
                                    };
                            packageFeatureGroupRecord.initialize$1();
                        }
                    });
        }
    }

    public final void logNotStarted(PrintWriter printWriter, String str) {
        this.mLogger.log(5, str.concat(": The controller has not started yet."), null);
        if (printWriter != null) {
            printWriter.println("The controller has not started yet.");
        }
    }

    public final void registerCallback(
            PackageFeature packageFeature, PackageFeatureCallback packageFeatureCallback) {
        synchronized (this.mLock) {
            try {
                if (!this.mStarted) {
                    logNotStarted(null, "registerCallback");
                    return;
                }
                PackageFeatures packageFeatures = this.mPackageFeatures;
                PackageFeatureGroupRecord packageFeatureGroupRecord =
                        (PackageFeatureGroupRecord)
                                ((ConcurrentHashMap) packageFeatures.mGroups)
                                        .get(packageFeature.mGroup.mName);
                if (packageFeatureGroupRecord != null) {
                    packageFeatureGroupRecord.registerCallback(
                            packageFeature, packageFeatureCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        this.mLogger.log(
                3,
                PackageFeatureGroupDataUtilWithEncryption.deleteCacheFiles(
                        PackageFeatureGroupDataUtilWithEncryption.LEGACY_DIR_PATH),
                null);
        initializeGroups();
        while (true) {
            synchronized (this.mLock) {
                try {
                    this.mLock.wait();
                    Iterator it = ((HashSet) this.mUpdateRequestedGroupNames).iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        if (this.mGetFileDescriptor == null) {
                            break;
                        }
                        PackageFeatureGroupRecord packageFeatureGroupRecord =
                                (PackageFeatureGroupRecord)
                                        ((ConcurrentHashMap) this.mPackageFeatures.mGroups)
                                                .get(str);
                        if (packageFeatureGroupRecord != null) {
                            packageFeatureGroupRecord.updateGroupDataFromScpm(
                                    (PackageFeatureManagerService$$ExternalSyntheticLambda1)
                                            this.mGetFileDescriptor);
                        }
                    }
                    ((HashSet) this.mUpdateRequestedGroupNames).clear();
                } catch (Throwable th) {
                    this.mLogger.log(6, "What a Terrible Failure: " + th, th);
                }
            }
        }
    }
}
