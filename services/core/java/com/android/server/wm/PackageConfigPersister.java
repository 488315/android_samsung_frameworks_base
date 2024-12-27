package com.android.server.wm;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.LocaleList;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.PackageConfigPersister;
import com.android.server.wm.PersisterQueue;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageConfigPersister {
    public final ActivityTaskManagerService mAtm;
    public final PersisterQueue mPersisterQueue;
    public final Object mLock = new Object();
    public final SparseArray mPendingWrite = new SparseArray();
    public final SparseArray mModified = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeletePackageItem implements PersisterQueue.WriteQueueItem {
        public final String mPackageName;
        public final int mUserId;

        public DeletePackageItem(int i, String str) {
            this.mUserId = i;
            this.mPackageName = str;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            File file = new File(Environment.getDataSystemCeDirectory(this.mUserId), "package_configs");
            if (file.isDirectory()) {
                AtomicFile atomicFile = new AtomicFile(new File(file, AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mPackageName, "_config.xml")));
                if (atomicFile.exists()) {
                    atomicFile.delete();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageConfigRecord {
        public Integer mGrammaticalGender;
        public LocaleList mLocales;
        public final String mName;
        public Integer mNightMode;
        public final int mUserId;

        public PackageConfigRecord(String str, int i) {
            this.mName = str;
            this.mUserId = i;
        }

        public final String toString() {
            return "PackageConfigRecord package name: " + this.mName + " userId " + this.mUserId + " nightMode " + this.mNightMode + " locales " + this.mLocales;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WriteProcessItem implements PersisterQueue.WriteQueueItem {
        public final PackageConfigRecord mRecord;

        public WriteProcessItem(PackageConfigRecord packageConfigRecord) {
            this.mRecord = packageConfigRecord;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            FileOutputStream fileOutputStream;
            byte[] bArr;
            AtomicFile atomicFile;
            synchronized (PackageConfigPersister.this.mLock) {
                fileOutputStream = null;
                try {
                    bArr = saveToXml();
                } catch (Exception unused) {
                    bArr = null;
                }
                SparseArray sparseArray = PackageConfigPersister.this.mPendingWrite;
                PackageConfigRecord packageConfigRecord = this.mRecord;
                HashMap hashMap = (HashMap) sparseArray.get(packageConfigRecord.mUserId);
                if (hashMap != null) {
                    hashMap.remove(packageConfigRecord.mName);
                }
            }
            if (bArr == null) {
                return;
            }
            try {
                File file = new File(Environment.getDataSystemCeDirectory(this.mRecord.mUserId), "package_configs");
                if (!file.isDirectory() && !file.mkdirs()) {
                    Slog.e("PackageConfigPersister", "Failure creating tasks directory for user " + this.mRecord.mUserId + ": " + file);
                    return;
                }
                atomicFile = new AtomicFile(new File(file, this.mRecord.mName + "_config.xml"));
                try {
                    fileOutputStream = atomicFile.startWrite();
                    fileOutputStream.write(bArr);
                    atomicFile.finishWrite(fileOutputStream);
                } catch (IOException e) {
                    e = e;
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    Slog.e("PackageConfigPersister", "Unable to open " + atomicFile + " for persisting. " + e);
                }
            } catch (IOException e2) {
                e = e2;
                atomicFile = null;
            }
        }

        public final byte[] saveToXml() {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "config");
            PackageConfigRecord packageConfigRecord = this.mRecord;
            resolveSerializer.attribute((String) null, "package_name", packageConfigRecord.mName);
            Integer num = packageConfigRecord.mNightMode;
            if (num != null) {
                resolveSerializer.attributeInt((String) null, "night_mode", num.intValue());
            }
            LocaleList localeList = packageConfigRecord.mLocales;
            if (localeList != null) {
                resolveSerializer.attribute((String) null, "locale_list", localeList.toLanguageTags());
            }
            resolveSerializer.endTag((String) null, "config");
            resolveSerializer.endDocument();
            resolveSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    public PackageConfigPersister(PersisterQueue persisterQueue, ActivityTaskManagerService activityTaskManagerService) {
        this.mPersisterQueue = persisterQueue;
        this.mAtm = activityTaskManagerService;
    }

    public static PackageConfigRecord findRecord(SparseArray sparseArray, String str, int i) {
        HashMap hashMap = (HashMap) sparseArray.get(i);
        if (hashMap == null) {
            return null;
        }
        return (PackageConfigRecord) hashMap.get(str);
    }

    public static PackageConfigRecord findRecordOrCreate(SparseArray sparseArray, String str, int i) {
        HashMap hashMap = (HashMap) sparseArray.get(i);
        if (hashMap == null) {
            hashMap = new HashMap();
            sparseArray.put(i, hashMap);
        }
        PackageConfigRecord packageConfigRecord = (PackageConfigRecord) hashMap.get(str);
        if (packageConfigRecord != null) {
            return packageConfigRecord;
        }
        PackageConfigRecord packageConfigRecord2 = new PackageConfigRecord(str, i);
        hashMap.put(str, packageConfigRecord2);
        return packageConfigRecord2;
    }

    public final ActivityTaskManagerInternal.PackageConfig findPackageConfiguration(int i, String str) {
        synchronized (this.mLock) {
            try {
                PackageConfigRecord findRecord = findRecord(this.mModified, str, i);
                if (findRecord != null) {
                    return new ActivityTaskManagerInternal.PackageConfig(findRecord.mNightMode, findRecord.mLocales, findRecord.mGrammaticalGender);
                }
                Slog.w("PackageConfigPersister", "App-specific configuration not found for packageName: " + str + " and userId: " + i);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removePackage(int i, String str) {
        final PackageConfigRecord findRecord = findRecord(this.mPendingWrite, str, i);
        PersisterQueue persisterQueue = this.mPersisterQueue;
        if (findRecord != null) {
            HashMap hashMap = (HashMap) this.mPendingWrite.get(findRecord.mUserId);
            if (hashMap != null) {
                hashMap.remove(findRecord.mName);
            }
            persisterQueue.removeItems(new Predicate() { // from class: com.android.server.wm.PackageConfigPersister$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    PackageConfigPersister.PackageConfigRecord packageConfigRecord = PackageConfigPersister.PackageConfigRecord.this;
                    PackageConfigPersister.PackageConfigRecord packageConfigRecord2 = ((PackageConfigPersister.WriteProcessItem) obj).mRecord;
                    return packageConfigRecord2.mName == packageConfigRecord.mName && packageConfigRecord2.mUserId == packageConfigRecord.mUserId;
                }
            }, WriteProcessItem.class);
        }
        PackageConfigRecord findRecord2 = findRecord(this.mModified, str, i);
        if (findRecord2 != null) {
            HashMap hashMap2 = (HashMap) this.mModified.get(findRecord2.mUserId);
            if (hashMap2 != null) {
                hashMap2.remove(findRecord2.mName);
            }
            persisterQueue.addItem(new DeletePackageItem(i, str), false);
        }
    }

    public final void removeUser(int i) {
        synchronized (this.mLock) {
            try {
                HashMap hashMap = (HashMap) this.mModified.get(i);
                HashMap hashMap2 = (HashMap) this.mPendingWrite.get(i);
                if (hashMap != null) {
                    if (hashMap.size() == 0) {
                    }
                    new HashMap(hashMap).forEach(new BiConsumer() { // from class: com.android.server.wm.PackageConfigPersister$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            PackageConfigPersister packageConfigPersister = PackageConfigPersister.this;
                            PackageConfigPersister.PackageConfigRecord packageConfigRecord = (PackageConfigPersister.PackageConfigRecord) obj2;
                            packageConfigPersister.getClass();
                            packageConfigPersister.removePackage(packageConfigRecord.mUserId, packageConfigRecord.mName);
                        }
                    });
                }
                if (hashMap2 != null) {
                    if (hashMap2.size() == 0) {
                    }
                    new HashMap(hashMap).forEach(new BiConsumer() { // from class: com.android.server.wm.PackageConfigPersister$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            PackageConfigPersister packageConfigPersister = PackageConfigPersister.this;
                            PackageConfigPersister.PackageConfigRecord packageConfigRecord = (PackageConfigPersister.PackageConfigRecord) obj2;
                            packageConfigPersister.getClass();
                            packageConfigPersister.removePackage(packageConfigRecord.mUserId, packageConfigRecord.mName);
                        }
                    });
                }
            } finally {
            }
        }
    }

    public final void updateConfigIfNeeded(ConfigurationContainer configurationContainer, int i, String str) {
        synchronized (this.mLock) {
            try {
                PackageConfigRecord findRecord = findRecord(this.mModified, str, i);
                if (findRecord != null) {
                    configurationContainer.applyAppSpecificConfig(findRecord.mNightMode, LocaleOverlayHelper.combineLocalesIfOverlayExists(findRecord.mLocales, this.mAtm.getGlobalConfiguration().getLocales()), findRecord.mGrammaticalGender);
                } else {
                    configurationContainer.applyAppSpecificConfig(null, null, null);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0055, code lost:
    
        if (r7.intValue() != 0) goto L42;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0051 A[Catch: all -> 0x0062, TryCatch #0 {all -> 0x0062, blocks: (B:4:0x0003, B:7:0x0016, B:9:0x001a, B:12:0x0023, B:13:0x0028, B:15:0x002c, B:18:0x0035, B:19:0x003a, B:22:0x004d, B:24:0x0051, B:29:0x0076, B:32:0x0078, B:34:0x0084, B:35:0x008e, B:37:0x0092, B:40:0x009b, B:41:0x00bd, B:42:0x00c7, B:44:0x009e, B:46:0x00a2, B:49:0x00ab, B:50:0x00ae, B:52:0x00b2, B:55:0x00bb, B:56:0x00c9, B:58:0x0057, B:60:0x005b, B:63:0x0065, B:65:0x0069, B:68:0x00cb, B:69:0x00ce, B:71:0x004a, B:74:0x000f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0084 A[Catch: all -> 0x0062, TryCatch #0 {all -> 0x0062, blocks: (B:4:0x0003, B:7:0x0016, B:9:0x001a, B:12:0x0023, B:13:0x0028, B:15:0x002c, B:18:0x0035, B:19:0x003a, B:22:0x004d, B:24:0x0051, B:29:0x0076, B:32:0x0078, B:34:0x0084, B:35:0x008e, B:37:0x0092, B:40:0x009b, B:41:0x00bd, B:42:0x00c7, B:44:0x009e, B:46:0x00a2, B:49:0x00ab, B:50:0x00ae, B:52:0x00b2, B:55:0x00bb, B:56:0x00c9, B:58:0x0057, B:60:0x005b, B:63:0x0065, B:65:0x0069, B:68:0x00cb, B:69:0x00ce, B:71:0x004a, B:74:0x000f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x005b A[Catch: all -> 0x0062, TryCatch #0 {all -> 0x0062, blocks: (B:4:0x0003, B:7:0x0016, B:9:0x001a, B:12:0x0023, B:13:0x0028, B:15:0x002c, B:18:0x0035, B:19:0x003a, B:22:0x004d, B:24:0x0051, B:29:0x0076, B:32:0x0078, B:34:0x0084, B:35:0x008e, B:37:0x0092, B:40:0x009b, B:41:0x00bd, B:42:0x00c7, B:44:0x009e, B:46:0x00a2, B:49:0x00ab, B:50:0x00ae, B:52:0x00b2, B:55:0x00bb, B:56:0x00c9, B:58:0x0057, B:60:0x005b, B:63:0x0065, B:65:0x0069, B:68:0x00cb, B:69:0x00ce, B:71:0x004a, B:74:0x000f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0069 A[Catch: all -> 0x0062, TryCatch #0 {all -> 0x0062, blocks: (B:4:0x0003, B:7:0x0016, B:9:0x001a, B:12:0x0023, B:13:0x0028, B:15:0x002c, B:18:0x0035, B:19:0x003a, B:22:0x004d, B:24:0x0051, B:29:0x0076, B:32:0x0078, B:34:0x0084, B:35:0x008e, B:37:0x0092, B:40:0x009b, B:41:0x00bd, B:42:0x00c7, B:44:0x009e, B:46:0x00a2, B:49:0x00ab, B:50:0x00ae, B:52:0x00b2, B:55:0x00bb, B:56:0x00c9, B:58:0x0057, B:60:0x005b, B:63:0x0065, B:65:0x0069, B:68:0x00cb, B:69:0x00ce, B:71:0x004a, B:74:0x000f), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x004a A[Catch: all -> 0x0062, TryCatch #0 {all -> 0x0062, blocks: (B:4:0x0003, B:7:0x0016, B:9:0x001a, B:12:0x0023, B:13:0x0028, B:15:0x002c, B:18:0x0035, B:19:0x003a, B:22:0x004d, B:24:0x0051, B:29:0x0076, B:32:0x0078, B:34:0x0084, B:35:0x008e, B:37:0x0092, B:40:0x009b, B:41:0x00bd, B:42:0x00c7, B:44:0x009e, B:46:0x00a2, B:49:0x00ab, B:50:0x00ae, B:52:0x00b2, B:55:0x00bb, B:56:0x00c9, B:58:0x0057, B:60:0x005b, B:63:0x0065, B:65:0x0069, B:68:0x00cb, B:69:0x00ce, B:71:0x004a, B:74:0x000f), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateFromImpl(java.lang.String r9, int r10, com.android.server.wm.PackageConfigurationUpdaterImpl r11) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.PackageConfigPersister.updateFromImpl(java.lang.String, int, com.android.server.wm.PackageConfigurationUpdaterImpl):boolean");
    }
}
