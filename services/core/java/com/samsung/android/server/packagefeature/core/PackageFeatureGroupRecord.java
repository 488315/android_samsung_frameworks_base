package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Handler;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PackageFeatureGroupRecord {
    public final PackageFeatureGroup mGroup;
    public PackageFeatureGroupData mGroupData;
    public PackageFeatureGroupData mGroupDataDummy;
    public final PackageFeatureGroupDataUtilWithEncryption mGroupDataUtil;
    public final Handler mHandler;
    public final Supplier mIsAllFeaturesDisabled;
    public final CoreLogger mLogger;
    public final Map mCallbacks = new ConcurrentHashMap();
    public GroupDataSource mGroupDataSource = GroupDataSource.NULL;

    final class GroupDataSource {
        public static final /* synthetic */ GroupDataSource[] $VALUES;
        public static final GroupDataSource CACHE_FILE;
        public static final GroupDataSource DEBUG_MODE;
        public static final GroupDataSource INITIALIZE;
        public static final GroupDataSource NULL;
        public static final GroupDataSource POLICY_DISABLED;
        public static final GroupDataSource RAW_RESOURCE;
        public static final GroupDataSource SCPM;

        static {
            GroupDataSource groupDataSource = new GroupDataSource("NULL", 0);
            NULL = groupDataSource;
            GroupDataSource groupDataSource2 = new GroupDataSource("INITIALIZE", 1);
            INITIALIZE = groupDataSource2;
            GroupDataSource groupDataSource3 = new GroupDataSource("CACHE_FILE", 2);
            CACHE_FILE = groupDataSource3;
            GroupDataSource groupDataSource4 = new GroupDataSource("RAW_RESOURCE", 3);
            RAW_RESOURCE = groupDataSource4;
            GroupDataSource groupDataSource5 = new GroupDataSource("SCPM", 4);
            SCPM = groupDataSource5;
            GroupDataSource groupDataSource6 = new GroupDataSource("POLICY_DISABLED", 5);
            POLICY_DISABLED = groupDataSource6;
            GroupDataSource groupDataSource7 = new GroupDataSource("DEBUG_MODE", 6);
            DEBUG_MODE = groupDataSource7;
            $VALUES =
                    new GroupDataSource[] {
                        groupDataSource,
                        groupDataSource2,
                        groupDataSource3,
                        groupDataSource4,
                        groupDataSource5,
                        groupDataSource6,
                        groupDataSource7
                    };
        }

        public static GroupDataSource valueOf(String str) {
            return (GroupDataSource) Enum.valueOf(GroupDataSource.class, str);
        }

        public static GroupDataSource[] values() {
            return (GroupDataSource[]) $VALUES.clone();
        }
    }

    public PackageFeatureGroupRecord(
            Context context,
            Handler handler,
            CoreLogger coreLogger,
            PackageFeatureGroup packageFeatureGroup,
            Supplier supplier) {
        this.mHandler = handler;
        this.mLogger = coreLogger;
        this.mGroup = packageFeatureGroup;
        this.mGroupDataUtil =
                new PackageFeatureGroupDataUtilWithEncryption(
                        context, coreLogger, packageFeatureGroup);
        this.mIsAllFeaturesDisabled = supplier;
    }

    public final String executeDebugMode(String[] strArr, String str, String str2) {
        PackageFeatureGroupData packageFeatureGroupData = this.mGroupData;
        if (packageFeatureGroupData == null) {
            packageFeatureGroupData = new PackageFeatureGroupData(0);
        }
        if (((ConcurrentHashMap) this.mCallbacks).isEmpty()) {
            return "Can not execute, There is no registered callback.";
        }
        if (((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue()) {
            return "All package features disabled.";
        }
        for (String str3 : strArr) {
            packageFeatureGroupData.putPackageFeature(str, null, str3, str2);
        }
        updateGroupData(packageFeatureGroupData, GroupDataSource.DEBUG_MODE);
        propagateToCallbacks();
        return "Packages=" + strArr.length + ", Extra=" + str2;
    }

    public final int getCurrentVersion() {
        PackageFeatureGroupData packageFeatureGroupData = this.mGroupData;
        if (packageFeatureGroupData != null) {
            return packageFeatureGroupData.getVersion();
        }
        return 0;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initialize$1() {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "initialize: GroupName="
            r0.<init>(r1)
            com.samsung.android.server.packagefeature.PackageFeatureGroup r1 = r7.mGroup
            java.lang.String r1 = r1.mName
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.samsung.android.server.util.CoreLogger r1 = r7.mLogger
            r2 = 4
            r3 = 0
            r1.log(r2, r0, r3)
            com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$GroupDataSource r0 = com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord.GroupDataSource.INITIALIZE
            r7.mGroupData = r3
            r7.mGroupDataSource = r0
            com.samsung.android.server.packagefeature.core.PackageFeatureGroupDataUtilWithEncryption r0 = r7.mGroupDataUtil
            r0.getClass()
            java.lang.String r1 = "loadFromCacheFile"
            java.io.File r2 = new java.io.File
            java.lang.String r4 = com.samsung.android.server.packagefeature.core.PackageFeatureGroupDataUtilWithEncryption.DIR_PATH
            r2.<init>(r4)
            java.io.File r4 = new java.io.File
            java.lang.String r5 = r0.mCacheFilePathName
            r4.<init>(r5)
            boolean r2 = r2.exists()
            if (r2 == 0) goto L6e
            boolean r2 = r4.exists()
            if (r2 != 0) goto L41
            goto L6e
        L41:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L57
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L57
            java.lang.Object r4 = r0.loadFromFileInputStream(r2)     // Catch: java.lang.Throwable -> L59
            boolean r5 = r4 instanceof com.samsung.android.server.packagefeature.core.PackageFeatureGroupData     // Catch: java.lang.Throwable -> L59
            if (r5 == 0) goto L5b
            r0.logSucceeded(r1)     // Catch: java.lang.Throwable -> L59
            com.samsung.android.server.packagefeature.core.PackageFeatureGroupData r4 = (com.samsung.android.server.packagefeature.core.PackageFeatureGroupData) r4     // Catch: java.lang.Throwable -> L59
            r2.close()     // Catch: java.lang.Throwable -> L57
            goto L6f
        L57:
            r2 = move-exception
            goto L6b
        L59:
            r4 = move-exception
            goto L62
        L5b:
            r0.logFailed(r1, r3)     // Catch: java.lang.Throwable -> L59
            r2.close()     // Catch: java.lang.Throwable -> L57
            goto L6e
        L62:
            r2.close()     // Catch: java.lang.Throwable -> L66
            goto L6a
        L66:
            r2 = move-exception
            r4.addSuppressed(r2)     // Catch: java.lang.Throwable -> L57
        L6a:
            throw r4     // Catch: java.lang.Throwable -> L57
        L6b:
            r0.logFailed(r1, r2)
        L6e:
            r4 = r3
        L6f:
            com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$GroupDataSource r1 = com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord.GroupDataSource.CACHE_FILE
            r7.updateGroupData(r4, r1)
            int r1 = r7.getCurrentVersion()
            java.lang.String r2 = "loadFromRawResource"
            com.samsung.android.server.packagefeature.PackageFeatureGroup r4 = r0.mGroup
            int r5 = r4.mRawResId
            if (r5 != 0) goto L81
            goto Lab
        L81:
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L9c
            android.content.Context r6 = r0.mContext     // Catch: java.lang.Throwable -> L9c
            android.content.res.Resources r6 = r6.getResources()     // Catch: java.lang.Throwable -> L9c
            int r4 = r4.mRawResId     // Catch: java.lang.Throwable -> L9c
            java.io.InputStream r4 = r6.openRawResource(r4)     // Catch: java.lang.Throwable -> L9c
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L9c
            r4 = 1
            com.samsung.android.server.packagefeature.core.PackageFeatureGroupData r1 = r0.loadFromReader(r1, r5, r2, r4)     // Catch: java.lang.Throwable -> L9e
            r5.close()     // Catch: java.lang.Throwable -> L9c
            r3 = r1
            goto Lab
        L9c:
            r1 = move-exception
            goto La8
        L9e:
            r1 = move-exception
            r5.close()     // Catch: java.lang.Throwable -> La3
            goto La7
        La3:
            r4 = move-exception
            r1.addSuppressed(r4)     // Catch: java.lang.Throwable -> L9c
        La7:
            throw r1     // Catch: java.lang.Throwable -> L9c
        La8:
            r0.logFailed(r2, r1)
        Lab:
            com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$GroupDataSource r0 = com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord.GroupDataSource.RAW_RESOURCE
            r7.updateGroupData(r3, r0)
            r7.propagateToCallbacks()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord.initialize$1():void");
    }

    public void propagateToCallback(
            final String str,
            final PackageFeatureCallback packageFeatureCallback,
            final PackageFeatureData packageFeatureData,
            final int i,
            final int i2,
            final String str2) {
        this.mHandler.post(
                new PackageFeatureGroupRecord$$ExternalSyntheticLambda3(
                        this,
                        new Supplier() { // from class:
                                         // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                PackageFeatureGroupRecord packageFeatureGroupRecord =
                                        PackageFeatureGroupRecord.this;
                                String str3 = str;
                                int i3 = i2;
                                int i4 = i;
                                String str4 = str2;
                                PackageFeatureData packageFeatureData2 = packageFeatureData;
                                packageFeatureGroupRecord.getClass();
                                StringBuilder sb = new StringBuilder("to propagate to ");
                                sb.append(str3);
                                sb.append(" callback");
                                sb.append(
                                        i3 > 1
                                                ? DualAppManagerService$$ExternalSyntheticOutline0
                                                        .m(i4, i3, "(", "/", ")")
                                                : "");
                                sb.append(" for ");
                                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                        sb,
                                        packageFeatureGroupRecord.mGroup.mName,
                                        ", reason=",
                                        str4,
                                        ", size=");
                                sb.append(packageFeatureData2.size());
                                return sb.toString();
                            }
                        },
                        new Consumer() { // from class:
                                         // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PackageFeatureCallback.this.onPackageFeatureDataChanged(
                                        packageFeatureData);
                            }
                        }));
    }

    public void propagateToCallbacks() {
        if (this.mGroupData == null || ((ConcurrentHashMap) this.mCallbacks).isEmpty()) {
            return;
        }
        String name =
                (((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue()
                                ? GroupDataSource.POLICY_DISABLED
                                : this.mGroupDataSource)
                        .name();
        StringBuilder sb = new StringBuilder("Start to propagate to callbacks(");
        sb.append(((ConcurrentHashMap) this.mCallbacks).size());
        sb.append(") for ");
        this.mLogger.log(
                3,
                BootReceiver$$ExternalSyntheticOutline0.m(sb, this.mGroup.mName, ", reason=", name),
                null);
        for (Map.Entry entry : ((ConcurrentHashMap) this.mCallbacks).entrySet()) {
            String str = (String) entry.getKey();
            PackageFeatureData packageFeatureData =
                    (this.mGroupData == null
                                    || ((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue())
                            ? new PackageFeatureData()
                            : this.mGroupData.getCopiedPackageFeature(str);
            List list = (List) entry.getValue();
            int size = list.size();
            int i = 0;
            while (i < size) {
                int i2 = i + 1;
                propagateToCallback(
                        str,
                        (PackageFeatureCallback) list.get(i),
                        packageFeatureData,
                        i2,
                        size,
                        name);
                i = i2;
            }
        }
    }

    public final void registerCallback(
            PackageFeature packageFeature, PackageFeatureCallback packageFeatureCallback) {
        if (!packageFeature.mEnabled) {
            this.mLogger.log(
                    5,
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder("PackageFeature("),
                            packageFeature.mName,
                            ") is not enabled."),
                    null);
            return;
        }
        String str = packageFeature.mName;
        ((List)
                        ((ConcurrentHashMap) this.mCallbacks)
                                .computeIfAbsent(
                                        str,
                                        new PackageFeatureGroupRecord$$ExternalSyntheticLambda2()))
                .add(packageFeatureCallback);
        PackageFeatureGroupData packageFeatureGroupData = this.mGroupData;
        if (packageFeatureGroupData == null) {
            return;
        }
        propagateToCallback(
                str,
                packageFeatureCallback,
                (packageFeatureGroupData == null
                                || ((Boolean) this.mIsAllFeaturesDisabled.get()).booleanValue())
                        ? new PackageFeatureData()
                        : this.mGroupData.getCopiedPackageFeature(str),
                1,
                1,
                "registerCallback");
    }

    public final boolean updateGroupData(
            PackageFeatureGroupData packageFeatureGroupData, GroupDataSource groupDataSource) {
        if (packageFeatureGroupData == null) {
            return false;
        }
        this.mGroupData = packageFeatureGroupData;
        this.mGroupDataSource = groupDataSource;
        return true;
    }

    public void updateGroupDataFromScpm(
            PackageFeatureManagerService$$ExternalSyntheticLambda1
                    packageFeatureManagerService$$ExternalSyntheticLambda1) {
        FileReader fileReader;
        PackageFeatureGroupDataUtilWithEncryption packageFeatureGroupDataUtilWithEncryption =
                this.mGroupDataUtil;
        FileDescriptor fileDescriptor =
                (FileDescriptor)
                        packageFeatureManagerService$$ExternalSyntheticLambda1.apply(
                                this.mGroup.mName);
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
                PackageFeatureGroupData loadFromReader =
                        packageFeatureGroupDataUtilWithEncryption.loadFromReader(
                                getCurrentVersion(), fileReader, "loadFromScpm", false);
                if (updateGroupData(loadFromReader, GroupDataSource.SCPM)) {
                    propagateToCallbacks();
                    packageFeatureGroupDataUtilWithEncryption.saveToCacheFile(loadFromReader);
                }
                fileReader.close();
            } finally {
            }
        }
    }
}
