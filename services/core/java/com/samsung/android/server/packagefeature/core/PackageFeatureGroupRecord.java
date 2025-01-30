package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class PackageFeatureGroupRecord {
    public final PackageFeatureGroup mGroup;
    public PackageFeatureGroupData mGroupData;
    public PackageFeatureGroupData mGroupDataDummy;
    public final PackageFeatureGroupDataUtil mGroupDataUtil;
    public final Handler mHandler;
    public final Supplier mIsAllFeaturesDisabled;
    public final CoreLogger mLogger;
    public final Map mCallbacks = new ConcurrentHashMap();
    public GroupDataSource mGroupDataSource = GroupDataSource.NULL;

    enum GroupDataSource {
        NULL,
        INITIALIZE,
        CACHE_FILE,
        RAW_RESOURCE,
        SCPM,
        POLICY_DISABLED,
        DEBUG_MODE
    }

    public PackageFeatureGroupRecord(Context context, Handler handler, CoreLogger coreLogger, PackageFeatureGroup packageFeatureGroup, Supplier supplier) {
        this.mHandler = handler;
        this.mLogger = coreLogger;
        this.mGroup = packageFeatureGroup;
        this.mGroupDataUtil = new PackageFeatureGroupDataUtilWithEncryption(context, coreLogger, packageFeatureGroup);
        this.mIsAllFeaturesDisabled = supplier;
    }

    public void initialize(BiConsumer biConsumer) {
        this.mGroupDataUtil.setConsumerForFailed(biConsumer);
        initialize();
    }

    public void initialize() {
        this.mLogger.log(4, "initialize: GroupName=" + this.mGroup.mName);
        setGroupData(null, GroupDataSource.INITIALIZE);
        updateGroupData(this.mGroupDataUtil.loadFromCacheFile(), GroupDataSource.CACHE_FILE);
        updateGroupData(this.mGroupDataUtil.loadFromRawResource(getCurrentVersion()), GroupDataSource.RAW_RESOURCE);
        propagateToCallbacks();
    }

    public void registerCallback(PackageFeature packageFeature, PackageFeatureCallback packageFeatureCallback) {
        if (!packageFeature.mEnabled) {
            this.mLogger.log(5, "PackageFeature(" + packageFeature.mName + ") is not enabled.");
            return;
        }
        String str = packageFeature.mName;
        List list = (List) this.mCallbacks.get(str);
        if (list == null) {
            list = new ArrayList();
        }
        list.add(packageFeatureCallback);
        this.mCallbacks.put(str, list);
        if (this.mGroupData == null) {
            return;
        }
        propagateToCallback(str, packageFeatureCallback, getPackageFeatureData(str), 1, 1, "registerCallback");
    }

    public void updateGroupDataFromScpm(Function function) {
        FileReader fileReader;
        FileDescriptor fileDescriptor = (FileDescriptor) function.apply(this.mGroup.mName);
        if (fileDescriptor != null) {
            try {
                fileReader = new FileReader(fileDescriptor);
            } catch (Throwable th) {
                this.mLogger.log(5, "Failed to newFileReader", th);
                return;
            }
        } else {
            fileReader = null;
        }
        if (fileReader == null) {
            if (fileReader != null) {
                fileReader.close();
            }
        } else {
            try {
                PackageFeatureGroupData loadFromScpm = this.mGroupDataUtil.loadFromScpm(getCurrentVersion(), fileReader);
                if (updateGroupData(loadFromScpm, GroupDataSource.SCPM)) {
                    propagateToCallbacks();
                    this.mGroupDataUtil.saveToCacheFile(loadFromScpm);
                }
                fileReader.close();
            } finally {
            }
        }
    }

    public void updateGroupDataDummyFromScpm() {
        if (this.mGroupDataDummy == null) {
            this.mGroupDataDummy = new PackageFeatureGroupData(0);
        }
        updateGroupData(this.mGroupDataDummy, GroupDataSource.SCPM);
    }

    public int getCurrentVersion() {
        PackageFeatureGroupData packageFeatureGroupData = this.mGroupData;
        if (packageFeatureGroupData != null) {
            return packageFeatureGroupData.getVersion();
        }
        return 0;
    }

    public final boolean updateGroupData(PackageFeatureGroupData packageFeatureGroupData, GroupDataSource groupDataSource) {
        if (packageFeatureGroupData == null) {
            return false;
        }
        setGroupData(packageFeatureGroupData, groupDataSource);
        return true;
    }

    public final void setGroupData(PackageFeatureGroupData packageFeatureGroupData, GroupDataSource groupDataSource) {
        this.mGroupData = packageFeatureGroupData;
        this.mGroupDataSource = groupDataSource;
    }

    public void propagateToCallbacks() {
        if (this.mGroupData == null || this.mCallbacks.isEmpty()) {
            return;
        }
        String name = (((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue() ? GroupDataSource.POLICY_DISABLED : this.mGroupDataSource).name();
        this.mLogger.log(3, "Start to propagate to callbacks(" + this.mCallbacks.size() + ") for " + this.mGroup.mName + ", reason=" + name);
        for (Map.Entry entry : this.mCallbacks.entrySet()) {
            String str = (String) entry.getKey();
            List list = (List) entry.getValue();
            if (list.size() != 0) {
                PackageFeatureData packageFeatureData = getPackageFeatureData(str);
                int size = list.size();
                int i = 0;
                while (i < size) {
                    int i2 = i + 1;
                    propagateToCallback(str, (PackageFeatureCallback) list.get(i), packageFeatureData, i2, size, name);
                    i = i2;
                }
            }
        }
    }

    public void propagateToCallback(String str, PackageFeatureCallback packageFeatureCallback, PackageFeatureData packageFeatureData, int i, int i2, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append("to propagate to ");
        sb.append(str);
        sb.append(" callback");
        if (i2 > 1) {
            str3 = "(" + i + "/" + i2 + ")";
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(" for ");
        sb.append(this.mGroup.mName);
        sb.append(", reason=");
        sb.append(str2);
        sb.append(", size=");
        sb.append(packageFeatureData.size());
        dispatchPackageFeatureDataChanged(sb.toString(), packageFeatureCallback, packageFeatureData);
    }

    public void dispatchPackageFeatureDataChanged(final String str, final PackageFeatureCallback packageFeatureCallback, final PackageFeatureData packageFeatureData) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PackageFeatureGroupRecord.this.lambda$dispatchPackageFeatureDataChanged$0(str, packageFeatureCallback, packageFeatureData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchPackageFeatureDataChanged$0(String str, PackageFeatureCallback packageFeatureCallback, PackageFeatureData packageFeatureData) {
        try {
            this.mLogger.log(2, "Start " + str);
            packageFeatureCallback.onPackageFeatureDataChanged(packageFeatureData);
        } catch (Throwable th) {
            this.mLogger.log(5, "Failed " + str, th);
        }
    }

    public void dispatchUnformattedPackageFeatureFileChanged(final String str, final PackageFeatureCallback packageFeatureCallback, final String str2, final Function function) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PackageFeatureGroupRecord.this.lambda$dispatchUnformattedPackageFeatureFileChanged$1(str, packageFeatureCallback, str2, function);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchUnformattedPackageFeatureFileChanged$1(String str, PackageFeatureCallback packageFeatureCallback, String str2, Function function) {
        try {
            this.mLogger.log(2, "Start " + str);
            packageFeatureCallback.onUnformattedPackageFeatureFileChanged(str2, function);
        } catch (Throwable th) {
            this.mLogger.log(5, "Failed " + str, th);
        }
    }

    public final PackageFeatureData getPackageFeatureData(String str) {
        if (this.mGroupData == null || ((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue()) {
            return new PackageFeatureData();
        }
        return this.mGroupData.getCopiedPackageFeature(str);
    }

    public String executeDebugMode(String str, String[] strArr, String str2) {
        if (this.mCallbacks.isEmpty()) {
            return "Can not execute, There is no registered callback.";
        }
        if (((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue()) {
            return "All package features disabled.";
        }
        PackageFeatureGroupData packageFeatureGroupData = this.mGroupData;
        if (packageFeatureGroupData == null) {
            packageFeatureGroupData = new PackageFeatureGroupData(0);
        }
        updateGroupData(packageFeatureGroupData, GroupDataSource.DEBUG_MODE);
        for (String str3 : strArr) {
            packageFeatureGroupData.putPackageFeature(str, null, str3, str2);
        }
        propagateToCallbacks();
        return "Packages=" + strArr.length + ", Extra=" + str2;
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("GroupName=");
        printWriter.print(this.mGroup.mName);
        if (this.mGroup.mUnformatted) {
            printWriter.print(", Unformatted=true");
        }
        printWriter.print(", Version=");
        printWriter.print(getCurrentVersion());
        printWriter.println(", Source=" + this.mGroupDataSource.name());
        PackageFeatureGroupData packageFeatureGroupData = this.mGroupData;
        if (packageFeatureGroupData == null || packageFeatureGroupData == this.mGroupDataDummy || ((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue()) {
            return;
        }
        this.mGroupData.dump(printWriter, str + "  ", this.mCallbacks.keySet());
    }
}
