package com.samsung.android.privacydashboard;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes6.dex */
public class PermissionAccessInformationController {
    private static final int OP_CAPTURE_SCREEN = 1000;
    private static final String TAG = "PermissionAccessInformationController";
    private final Context mContext;
    private final Executor mFlushExecutor;
    private final PermissionAccessInformationWriter mPermissionAccessInformationWriter;
    private Set<PermissionAccessInformation> mPermissionAccessInformations;
    private final Lock mReadLock;
    private final Lock mWriterLock;
    private final String[] mUselessPackages = {"android"};
    private final HashMap<Integer, Boolean> mPermissionGroupMap = new HashMap<Integer, Boolean>() { // from class: com.samsung.android.privacydashboard.PermissionAccessInformationController.2
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
            put(19, true);
            put(57, true);
            put(21, true);
            put(17, true);
            put(59, true);
            put(60, true);
            put(90, true);
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
            put(114, true);
            put(111, true);
            put(77, true);
            put(112, true);
            put(116, true);
            put(6, true);
            put(7, true);
            put(54, true);
            put(51, true);
            put(65, true);
            put(13, true);
            put(52, true);
            put(53, true);
            put(69, true);
            put(74, true);
            put(27, true);
            put(79, true);
            put(26, true);
            put(56, true);
            put(11, true);
            put(126, true);
            put(1000, true);
        }
    };

    public PermissionAccessInformationController(Context context) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriterLock = reentrantReadWriteLock.writeLock();
        this.mContext = context;
        this.mFlushExecutor = Executors.newSingleThreadExecutor();
        this.mPermissionAccessInformationWriter = new PermissionAccessInformationWriter();
        this.mPermissionAccessInformations = new HashSet();
    }

    private void write(PermissionAccessInformation permissionAccessInformation) {
        try {
            this.mReadLock.lock();
            if (this.mPermissionAccessInformations.contains(permissionAccessInformation)) {
                this.mPermissionAccessInformations.remove(permissionAccessInformation);
            }
            this.mPermissionAccessInformations.add(permissionAccessInformation);
        } finally {
            this.mReadLock.unlock();
        }
    }

    public void write(int i, int i2, String str, String str2, String str3, int i3) {
        if (isPackageEnable(str) && isOpCodeEnable(i)) {
            write(new PermissionAccessInformation(i, i2, str, str2, str3, i3 >= 300, System.currentTimeMillis()));
            if (this.mPermissionAccessInformations.size() > 1000) {
                flushAsync();
            }
        }
    }

    public void flushAsync() {
        this.mFlushExecutor.execute(new Runnable() { // from class: com.samsung.android.privacydashboard.PermissionAccessInformationController.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    PermissionAccessInformationController.this.flush();
                } catch (Exception e) {
                    Log.w(PermissionAccessInformationController.TAG, e.getMessage(), e);
                }
            }
        });
    }

    public void flush() throws IOException {
        try {
            this.mWriterLock.lock();
            Set<PermissionAccessInformation> set = this.mPermissionAccessInformations;
            this.mPermissionAccessInformations = new HashSet();
            if (set != null) {
                try {
                    this.mPermissionAccessInformationWriter.write(this.mContext, set.iterator());
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

    private boolean isPackageEnable(String str) {
        int i = 0;
        while (true) {
            String[] strArr = this.mUselessPackages;
            if (i >= strArr.length) {
                return true;
            }
            if (str.equalsIgnoreCase(strArr[i])) {
                return false;
            }
            i++;
        }
    }

    private boolean isOpCodeEnable(int i) {
        return this.mPermissionGroupMap.containsKey(Integer.valueOf(i));
    }
}
