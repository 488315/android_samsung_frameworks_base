package com.samsung.android.kmxservice.ai.privacy;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes6.dex */
public class PermissionDataController {
    private static final String TAG = "AI::PermissionDataController";
    private final Context mContext;
    private final Executor mFlushExecutor;
    private Set<PermissionData> mPermissionDatas;
    private final HashMap<Integer, Boolean> mPermissionGroupMap = new HashMap<Integer, Boolean>() { // from class: com.samsung.android.kmxservice.ai.privacy.PermissionDataController.2
        {
            put(4, true);
            put(5, true);
            put(62, true);
            put(8, true);
            put(9, true);
            put(20, true);
            put(16, true);
            put(14, true);
            put(18, true);
            put(57, true);
            put(81, true);
            put(83, true);
            put(85, true);
            put(123, true);
            put(0, true);
            put(1, true);
            put(2, true);
            put(10, true);
            put(41, true);
            put(42, true);
            put(6, true);
            put(7, true);
            put(54, true);
            put(27, true);
            put(26, true);
        }
    };
    private final Lock mReadLock;
    private final Lock mWriterLock;
    private static Set<String> zeroLevelPackages = new HashSet();
    private static final Integer UID_FILTER = 10000;
    public static final Uri PROVIDER_URI = Uri.parse("content://com.samsung.android.kmxservice.ai.privacy/permission");

    public PermissionDataController(Context context) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriterLock = reentrantReadWriteLock.writeLock();
        this.mContext = context;
        this.mFlushExecutor = Executors.newSingleThreadExecutor();
        this.mPermissionDatas = new HashSet();
    }

    private void write(PermissionData permissionData) {
        try {
            this.mReadLock.lock();
            this.mPermissionDatas.add(permissionData);
        } finally {
            this.mReadLock.unlock();
        }
    }

    public void write(int i, int i2, String str, int i3) {
        if (i2 < UID_FILTER.intValue()) {
            return;
        }
        if (zeroLevelPackages.isEmpty()) {
            initialPackages();
        }
        if (isPackageEnable(str) && isOpCodeEnable(i)) {
            write(new PermissionData(i, i2, str, i3));
            if (this.mPermissionDatas.size() > 499) {
                flushAsync();
            }
        }
    }

    private void pushPermissionDatas(Iterator<PermissionData> it) {
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            PermissionData next = it.next();
            ContentValues contentValues = next.toContentValues();
            if (UserHandle.getUserHandleForUid(next.getUid()).semGetIdentifier() == 0) {
                arrayList.add(contentValues);
            }
        }
        if (arrayList.size() > 0) {
            ContentValues[] contentValuesArr = new ContentValues[arrayList.size()];
            arrayList.toArray(contentValuesArr);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.getContentResolver().bulkInsert(PROVIDER_URI, contentValuesArr);
                Log.d(TAG, "bulkInsert success");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void flushAsync() {
        this.mFlushExecutor.execute(new Runnable() { // from class: com.samsung.android.kmxservice.ai.privacy.PermissionDataController.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    PermissionDataController.this.flush();
                } catch (Exception e) {
                    Log.w(PermissionDataController.TAG, e.getMessage(), e);
                }
            }
        });
    }

    public void flush() throws IOException {
        try {
            this.mWriterLock.lock();
            Set<PermissionData> set = this.mPermissionDatas;
            this.mPermissionDatas = new HashSet();
            if (set != null) {
                try {
                    Log.d(TAG, "flush start...");
                    pushPermissionDatas(set.iterator());
                } finally {
                    if (set != null) {
                        set.clear();
                    }
                }
            }
        } finally {
            this.mWriterLock.unlock();
        }
    }

    private void initialPackages() {
        String[] strArr = {"/system/priv-app", "/system/app", "/system/system_ext", "/product/app", "/product/overlay", "/apex", "/system/framework", "/product/priv-app", "/system_ext/priv-app"};
        for (PackageInfo packageInfo : this.mContext.getPackageManager().getInstalledPackages(128)) {
            try {
                String str = this.mContext.getPackageManager().getPackageInfo(packageInfo.packageName, 4096).applicationInfo.sourceDir;
                int i = 0;
                while (true) {
                    if (i >= 9) {
                        break;
                    }
                    if (str.startsWith(strArr[i])) {
                        zeroLevelPackages.add(packageInfo.packageName);
                        break;
                    }
                    i++;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Can not found source directory of " + packageInfo.packageName + e);
            }
        }
        Log.d(TAG, "Building initial packages list success");
    }

    private boolean isPackageEnable(String str) {
        return !zeroLevelPackages.contains(str);
    }

    private boolean isOpCodeEnable(int i) {
        return this.mPermissionGroupMap.containsKey(Integer.valueOf(i));
    }
}
