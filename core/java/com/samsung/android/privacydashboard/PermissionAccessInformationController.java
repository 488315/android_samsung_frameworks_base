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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private final HashMap<Integer, Boolean> mPermissionGroupMap =
            new HashMap<
                    Integer,
                    Boolean>() { // from class:
                                 // com.samsung.android.privacydashboard.PermissionAccessInformationController.2
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
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.mReadLock = readWriteLock.readLock();
        this.mWriterLock = readWriteLock.writeLock();
        this.mContext = context;
        this.mFlushExecutor = Executors.newSingleThreadExecutor();
        this.mPermissionAccessInformationWriter = new PermissionAccessInformationWriter();
        this.mPermissionAccessInformations = new HashSet();
    }

    private void write(PermissionAccessInformation information) {
        try {
            this.mReadLock.lock();
            if (this.mPermissionAccessInformations.contains(information)) {
                this.mPermissionAccessInformations.remove(information);
            }
            this.mPermissionAccessInformations.add(information);
        } finally {
            this.mReadLock.unlock();
        }
    }

    public void write(
            int op,
            int uid,
            String packageName,
            String proxyPackageName,
            String proxyAttributionTag,
            int state) {
        if (isPackageEnable(packageName) && isOpCodeEnable(op)) {
            PermissionAccessInformation information =
                    new PermissionAccessInformation(
                            op,
                            uid,
                            packageName,
                            proxyPackageName,
                            proxyAttributionTag,
                            state >= 300,
                            System.currentTimeMillis());
            write(information);
            if (this.mPermissionAccessInformations.size() > 1000) {
                flushAsync();
            }
        }
    }

    public void flushAsync() {
        this.mFlushExecutor.execute(
                new Runnable() { // from class:
                                 // com.samsung.android.privacydashboard.PermissionAccessInformationController.1
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
            Set<PermissionAccessInformation> cache = this.mPermissionAccessInformations;
            this.mPermissionAccessInformations = new HashSet();
            if (cache != null) {
                try {
                    this.mPermissionAccessInformationWriter.write(this.mContext, cache.iterator());
                } finally {
                    if (cache != null) {
                        cache.clear();
                    }
                }
            }
        } finally {
            this.mWriterLock.unlock();
        }
    }

    private boolean isPackageEnable(String packageName) {
        for (int i = 0; i < this.mUselessPackages.length; i++) {
            if (packageName.equalsIgnoreCase(this.mUselessPackages[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isOpCodeEnable(int op) {
        return this.mPermissionGroupMap.containsKey(Integer.valueOf(op));
    }
}
