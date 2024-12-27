package com.android.server.enterprise.auditlog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;

import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CircularBuffer {
    public static final ScheduledThreadPoolExecutor mSte = new ScheduledThreadPoolExecutor(1);
    public float mAdminCriticalSize;
    public final String mAdminDirectoryPath;
    public float mAdminMaximumSize;
    public long mBufferLimitSize;
    public volatile long mCircularBufferSize;
    public final Context mContext;
    public boolean mCriticalIntent;
    public PartialFileNode mCurrentNode;
    public final List mDumpList;
    public final EdmStorageProvider mEdmStorageProvider;
    public final float mFullBuffer;
    public boolean mFullIntent;
    public final boolean mIsPseudoAdminOfOrganizationOwnedDevice;
    public volatile String mLastDumpedFile;
    public boolean mMaximumIntent;
    public volatile int mNumberOfDeprecatedFiles;
    public final String mPackageName;
    public final List mPendingIntentErrors;
    public long mTotalDirectoryOccupation;
    public final int mUid;
    public final Object mLock = new Object();
    public volatile boolean mIsDumping = false;
    public volatile boolean mTypeOfDump = false;
    public volatile boolean mIsBootCompleted = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.auditlog.CircularBuffer$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Long.valueOf(((File) obj).lastModified())
                    .compareTo(Long.valueOf(((File) obj2).lastModified()));
        }
    }

    public CircularBuffer(Context context, String str, int i) {
        int i2;
        mSte.allowCoreThreadTimeOut(false);
        this.mUid = i;
        this.mAdminCriticalSize = 70.0f;
        this.mAdminMaximumSize = 90.0f;
        this.mFullBuffer = 97.0f;
        this.mContext = context;
        this.mCircularBufferSize = 0L;
        this.mTotalDirectoryOccupation = 0L;
        Cursor cursor = null;
        this.mLastDumpedFile = null;
        this.mPackageName = str;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        try {
            boolean checkPseudoAdminForUid = edmStorageProvider.checkPseudoAdminForUid(i);
            this.mIsPseudoAdminOfOrganizationOwnedDevice = checkPseudoAdminForUid;
            Log.d(
                    "CircularBuffer",
                    "mIsPseudoAdminOfOrganizationOwnedDevice = " + checkPseudoAdminForUid);
        } catch (SettingNotFoundException e) {
            Log.e(
                    "CircularBuffer",
                    "mEdmStorageProvider.checkPseudoAdminForUid: error " + e.getMessage());
        }
        this.mBufferLimitSize = getBufferLogSize();
        try {
            try {
                cursor =
                        this.mEdmStorageProvider.getCursorByAdmin(
                                this.mUid, 0, "AUDITLOG", new String[] {"auditNumberOfDepFiles"});
                if (cursor != null) {
                    cursor.moveToFirst();
                    i2 = cursor.getInt(0);
                } else {
                    i2 = 0;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLException e2) {
                Log.e(
                        "CircularBuffer",
                        "Exception occurred accessing Enterprise db " + e2.getMessage());
                if (cursor != null) {
                    cursor.close();
                }
                i2 = 0;
            }
            this.mNumberOfDeprecatedFiles = i2;
            this.mDumpList = Collections.synchronizedList(new ArrayList());
            this.mPendingIntentErrors = Collections.synchronizedList(new ArrayList());
            String str2 = "/data/system/" + String.valueOf(this.mUid);
            this.mAdminDirectoryPath = str2;
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
                this.mCurrentNode = addNode();
                return;
            }
            File[] dirListByAscendingDate = dirListByAscendingDate(file);
            if (dirListByAscendingDate != null) {
                int i3 = 0;
                for (File file2 : dirListByAscendingDate) {
                    if (file2.isDirectory()) {
                        deleteDirectory(file2);
                    } else {
                        if (file2.length() == 0) {
                            file2.delete();
                        } else {
                            PartialFileNode partialFileNode =
                                    new PartialFileNode(file2, this.mPackageName);
                            partialFileNode.mWasWritten = true;
                            try {
                                FileInputStream fileInputStream = new FileInputStream(file2);
                                byte[] bArr = new byte[2];
                                try {
                                    fileInputStream.read(bArr, 0, 2);
                                    boolean z = bArr[0] == 31 && bArr[1] == -117;
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception unused) {
                                    }
                                    if (!z) {
                                        formatIfEmptyOrCorrupted(file2);
                                        if (file2.length() == 0) {
                                            file2.delete();
                                        } else {
                                            partialFileNode.compressFile();
                                        }
                                    }
                                } catch (Throwable th) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception unused2) {
                                    }
                                    throw th;
                                }
                            } catch (IOException e3) {
                                Log.d("CircularBuffer", "IOException: " + e3.toString());
                            }
                            this.mDumpList.add(partialFileNode);
                            if (i3 > this.mNumberOfDeprecatedFiles) {
                                this.mCircularBufferSize =
                                        partialFileNode.getFileSize() + this.mCircularBufferSize;
                            } else {
                                synchronized (partialFileNode) {
                                    if (!partialFileNode.mMarkAsDeprecated) {
                                        partialFileNode.mMarkAsDeprecated = true;
                                    }
                                }
                            }
                        }
                    }
                    i3++;
                }
                for (File file3 : dirListByAscendingDate) {
                    this.mTotalDirectoryOccupation =
                            file3.length() + this.mTotalDirectoryOccupation;
                }
                resizeBubbleFile(this.mBufferLimitSize - this.mTotalDirectoryOccupation);
            }
            this.mCurrentNode = addNode();
        } catch (Throwable th2) {
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
    }

    public static void deleteDirectory(File file) {
        if (!file.isDirectory()) {
            file.delete();
            Log.d("CircularBuffer", "File is deleted : " + file.getAbsolutePath());
            return;
        }
        String[] list = file.list();
        if (list != null) {
            if (list.length == 0) {
                file.delete();
                System.out.println("Directory is deleted : " + file.getAbsolutePath());
                return;
            }
            for (String str : list) {
                deleteDirectory(new File(file, str));
            }
            if (file.list().length == 0) {
                file.delete();
                System.out.println("Directory is deleted : " + file.getAbsolutePath());
            }
        }
    }

    public static File[] dirListByAscendingDate(File file) {
        File[] listFiles;
        if (!file.isDirectory() || (listFiles = file.listFiles()) == null) {
            return null;
        }
        Arrays.sort(listFiles, new AnonymousClass1());
        if (listFiles.length <= 0) {
            return null;
        }
        File file2 = listFiles[listFiles.length - 1];
        if (!file2.getName().endsWith("_tmp") && !file2.isDirectory()) {
            return listFiles;
        }
        deleteDirectory(file2);
        return dirListByAscendingDate(file);
    }

    public final PartialFileNode addNode() {
        if (this.mIsBootCompleted) {
            float f = (int) ((this.mCircularBufferSize * 100) / this.mBufferLimitSize);
            if (f < this.mAdminCriticalSize) {
                this.mCriticalIntent = false;
            } else if (!this.mCriticalIntent) {
                Intent intent =
                        new Intent("com.samsung.android.knox.intent.action.AUDIT_CRITICAL_SIZE");
                intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
                intent.setPackage(this.mPackageName);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(getTargetUserId()),
                        "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                try {
                    String kpuPackageName =
                            KpuHelper.getInstance(this.mContext).getKpuPackageName();
                    Intent intent2 =
                            new Intent(
                                    "com.samsung.android.knox.intent.action.AUDIT_CRITICAL_SIZE");
                    intent2.putExtra(
                            "com.samsung.android.knox.intent.extra.ADMIN_UID",
                            this.mContext
                                    .getPackageManager()
                                    .getPackageUidAsUser(
                                            kpuPackageName, UserHandle.getCallingUserId()));
                    intent2.setPackage(kpuPackageName);
                    this.mContext.sendBroadcast(
                            intent2, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                this.mCriticalIntent = true;
                AuditLog.logAsUser(
                        4,
                        2,
                        true,
                        Process.myPid(),
                        "CircularBuffer",
                        String.format(
                                "AuditLog has reached its critical size. Percentage is %.2f",
                                Float.valueOf(this.mAdminCriticalSize)),
                        UserHandle.getUserId(this.mUid));
            }
            if (f < this.mAdminMaximumSize) {
                this.mMaximumIntent = false;
            } else if (!this.mMaximumIntent) {
                Intent intent3 =
                        new Intent("com.samsung.android.knox.intent.action.AUDIT_MAXIMUM_SIZE");
                intent3.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
                intent3.setPackage(this.mPackageName);
                long clearCallingIdentity2 = Binder.clearCallingIdentity();
                this.mContext.sendBroadcastAsUser(
                        intent3,
                        new UserHandle(getTargetUserId()),
                        "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                try {
                    String kpuPackageName2 =
                            KpuHelper.getInstance(this.mContext).getKpuPackageName();
                    Intent intent4 =
                            new Intent("com.samsung.android.knox.intent.action.AUDIT_MAXIMUM_SIZE");
                    intent4.putExtra(
                            "com.samsung.android.knox.intent.extra.ADMIN_UID",
                            this.mContext
                                    .getPackageManager()
                                    .getPackageUidAsUser(
                                            kpuPackageName2, UserHandle.getCallingUserId()));
                    intent4.setPackage(kpuPackageName2);
                    this.mContext.sendBroadcast(
                            intent4, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity2);
                this.mMaximumIntent = true;
            }
            if (f < this.mFullBuffer) {
                this.mFullIntent = false;
            } else if (!this.mFullIntent) {
                Intent intent5 =
                        new Intent("com.samsung.android.knox.intent.action.AUDIT_FULL_SIZE");
                intent5.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
                intent5.setPackage(this.mPackageName);
                long clearCallingIdentity3 = Binder.clearCallingIdentity();
                this.mContext.sendBroadcastAsUser(
                        intent5,
                        new UserHandle(getTargetUserId()),
                        "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                try {
                    String kpuPackageName3 =
                            KpuHelper.getInstance(this.mContext).getKpuPackageName();
                    Intent intent6 =
                            new Intent("com.samsung.android.knox.intent.action.AUDIT_FULL_SIZE");
                    intent6.putExtra(
                            "com.samsung.android.knox.intent.extra.ADMIN_UID",
                            this.mContext
                                    .getPackageManager()
                                    .getPackageUidAsUser(
                                            kpuPackageName3, UserHandle.getCallingUserId()));
                    intent6.setPackage(kpuPackageName3);
                    this.mContext.sendBroadcast(
                            intent6, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity3);
                this.mFullIntent = true;
                InformFailure.getInstance()
                        .broadcastFailure("Full Size Reached!", this.mPackageName);
            }
        }
        if (!this.mIsDumping && this.mDumpList.size() > totalNumberFiles()) {
            synchronized (this.mDumpList) {
                try {
                    Iterator it = this.mDumpList.iterator();
                    while (this.mDumpList.size() > totalNumberFiles()) {
                        PartialFileNode partialFileNode = (PartialFileNode) it.next();
                        if (partialFileNode.mMarkAsDeprecated) {
                            int i = this.mNumberOfDeprecatedFiles;
                            this.mNumberOfDeprecatedFiles = i - 1;
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("auditNumberOfDepFiles", Integer.valueOf(i));
                            this.mEdmStorageProvider.putValues(
                                    this.mUid, 0, "AUDITLOG", contentValues);
                        } else {
                            this.mCircularBufferSize -= partialFileNode.getFileSize();
                        }
                        long fileSize =
                                this.mTotalDirectoryOccupation - partialFileNode.getFileSize();
                        this.mTotalDirectoryOccupation = fileSize;
                        resizeBubbleFile(this.mBufferLimitSize - fileSize);
                        partialFileNode.delete();
                        it.remove();
                    }
                } finally {
                }
            }
        }
        return new PartialFileNode(this.mAdminDirectoryPath, this.mPackageName);
    }

    public final void closeFile() {
        synchronized (this.mLock) {
            try {
                PartialFileNode partialFileNode = this.mCurrentNode;
                if (partialFileNode.mWasWritten) {
                    partialFileNode.compressFile();
                    this.mCurrentNode.closeFile();
                } else {
                    partialFileNode.delete();
                }
                this.mCurrentNode = addNode();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void createBubbleDir() {
        File file =
                new File(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                this.mUid, new StringBuilder("/data/system/"), "_bubble"));
        if (file.exists()) {
            return;
        }
        file.mkdir();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:34:0x004d
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [int] */
    /* JADX WARN: Type inference failed for: r2v9 */
    public final void formatIfEmptyOrCorrupted(java.io.File r12) {
        /*
            r11 = this;
            java.lang.String r0 = "formatIfEmptyOrCorrupted.IOException"
            java.lang.String r1 = "CircularBuffer"
            r2 = 0
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r4 = "rwd"
            r3.<init>(r12, r4)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            r12 = 65536(0x10000, float:9.18355E-41)
            byte[] r12 = new byte[r12]     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            r4 = 0
        L14:
            int r2 = r3.read(r12)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            r6 = 0
            if (r2 <= 0) goto L3d
            int r7 = r2 + (-1)
            r7 = r12[r7]     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            if (r7 != 0) goto L39
            r7 = r6
            r8 = r7
        L23:
            if (r7 >= r2) goto L3e
            r9 = r12[r7]     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            r10 = 10
            if (r9 != r10) goto L2c
            r8 = r7
        L2c:
            if (r9 != 0) goto L30
            r6 = 1
            goto L3e
        L30:
            int r7 = r7 + 1
            goto L23
        L33:
            r11 = move-exception
            r2 = r3
            goto L65
        L36:
            r12 = move-exception
            r2 = r3
            goto L54
        L39:
            r6 = 1
            long r4 = r4 + r6
            goto L14
        L3d:
            r8 = r6
        L3e:
            if (r6 == 0) goto L49
            r6 = 65536(0x10000, double:3.2379E-319)
            long r4 = r4 * r6
            long r6 = (long) r8     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            long r4 = r4 + r6
            r3.setLength(r4)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
        L49:
            r3.close()     // Catch: java.io.IOException -> L4d
            goto L64
        L4d:
            android.util.Log.e(r1, r0)
            goto L64
        L51:
            r11 = move-exception
            goto L65
        L53:
            r12 = move-exception
        L54:
            java.lang.String r3 = "formatIfEmptyOrCorrupted.Exception"
            android.util.Log.e(r1, r3)     // Catch: java.lang.Throwable -> L51
            java.util.List r11 = r11.mPendingIntentErrors     // Catch: java.lang.Throwable -> L51
            r11.add(r12)     // Catch: java.lang.Throwable -> L51
            if (r2 == 0) goto L64
            r2.close()     // Catch: java.io.IOException -> L4d
        L64:
            return
        L65:
            if (r2 == 0) goto L6e
            r2.close()     // Catch: java.io.IOException -> L6b
            goto L6e
        L6b:
            android.util.Log.e(r1, r0)
        L6e:
            throw r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.CircularBuffer.formatIfEmptyOrCorrupted(java.io.File):void");
    }

    public final long getBufferLogSize() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(this.mUid));
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getLongList(
                                contentValues, "AUDITLOG", "auditLogBufferSize");
        if (arrayList.size() > 0) {
            return ((Long) arrayList.get(0)).longValue();
        }
        return -1L;
    }

    public final int getTargetUserId() {
        int userId = UserHandle.getUserId(this.mUid);
        if (!this.mIsPseudoAdminOfOrganizationOwnedDevice) {
            return userId;
        }
        int i = EnterpriseDeviceManagerService.$r8$clinit;
        EnterpriseDeviceManagerService enterpriseDeviceManagerService =
                (EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance;
        return enterpriseDeviceManagerService != null
                ? enterpriseDeviceManagerService.getOrganizationOwnedProfileUserId()
                : userId;
    }

    public final void markDeprecatedFiles() {
        synchronized (this.mDumpList) {
            try {
                List list = this.mDumpList;
                ListIterator listIterator = list.listIterator(list.size());
                boolean z = false;
                while (listIterator.hasPrevious()) {
                    PartialFileNode partialFileNode = (PartialFileNode) listIterator.previous();
                    if (!partialFileNode.mWasWritten) {
                        partialFileNode.delete();
                        listIterator.remove();
                    } else if (z) {
                        if (partialFileNode.mMarkAsDeprecated) {
                            break;
                        }
                        this.mCircularBufferSize -= partialFileNode.getFileSize();
                        synchronized (partialFileNode) {
                            if (!partialFileNode.mMarkAsDeprecated) {
                                partialFileNode.mMarkAsDeprecated = true;
                            }
                        }
                        this.mNumberOfDeprecatedFiles++;
                    } else if (this.mLastDumpedFile != null
                            && partialFileNode.mFile.getName().equals(this.mLastDumpedFile)) {
                        this.mLastDumpedFile = null;
                        listIterator.next();
                        z = true;
                    }
                }
                int i = this.mNumberOfDeprecatedFiles;
                ContentValues contentValues = new ContentValues();
                contentValues.put("auditNumberOfDepFiles", Integer.valueOf(i));
                this.mEdmStorageProvider.putValues(this.mUid, 0, "AUDITLOG", contentValues);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:20:0x004b
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r2v9 */
    public final void resizeBubbleFile(long r9) {
        /*
            r8 = this;
            java.lang.String r0 = "resizeBubbleFile.IOException"
            java.lang.String r1 = "CircularBuffer"
            r2 = 0
            int r2 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            java.lang.String r3 = "_bubble/bubbleFile"
            int r8 = r8.mUid
            java.lang.String r4 = "/data/system/"
            if (r2 > 0) goto L1e
            java.io.File r9 = new java.io.File
            java.lang.String r8 = com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r8, r4, r3)
            r9.<init>(r8)
            r9.delete()
            return
        L1e:
            r2 = 0
            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            r7.<init>(r4)     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            r7.append(r8)     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            r7.append(r3)     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            java.lang.String r8 = r7.toString()     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            r6.<init>(r8)     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            java.lang.String r8 = "rws"
            r5.<init>(r6, r8)     // Catch: java.lang.Throwable -> L59 java.lang.OutOfMemoryError -> L5b java.io.IOException -> L68 java.io.FileNotFoundException -> L6e
            int r8 = (int) r9
            int r8 = r8 + (-1)
            byte[] r2 = new byte[r8]     // Catch: java.lang.Throwable -> L4f java.lang.OutOfMemoryError -> L52 java.io.IOException -> L55 java.io.FileNotFoundException -> L57
            r5.setLength(r9)     // Catch: java.lang.Throwable -> L4f java.lang.OutOfMemoryError -> L52 java.io.IOException -> L55 java.io.FileNotFoundException -> L57
            r9 = 0
            r5.write(r2, r9, r8)     // Catch: java.lang.Throwable -> L4f java.lang.OutOfMemoryError -> L52 java.io.IOException -> L55 java.io.FileNotFoundException -> L57
            r5.close()     // Catch: java.io.IOException -> L4b
            goto L77
        L4b:
            android.util.Log.e(r1, r0)
            goto L77
        L4f:
            r8 = move-exception
            r2 = r5
            goto L78
        L52:
            r8 = move-exception
            r2 = r5
            goto L5c
        L55:
            r2 = r5
            goto L68
        L57:
            r2 = r5
            goto L6e
        L59:
            r8 = move-exception
            goto L78
        L5b:
            r8 = move-exception
        L5c:
            java.lang.String r9 = "resizeBubbleFile.OutOfMemoryError"
            android.util.Log.e(r1, r9, r8)     // Catch: java.lang.Throwable -> L59
            if (r2 == 0) goto L77
        L64:
            r2.close()     // Catch: java.io.IOException -> L4b
            goto L77
        L68:
            android.util.Log.e(r1, r0)     // Catch: java.lang.Throwable -> L59
            if (r2 == 0) goto L77
            goto L64
        L6e:
            java.lang.String r8 = "resizeBubbleFile.FileNotFoundException"
            android.util.Log.e(r1, r8)     // Catch: java.lang.Throwable -> L59
            if (r2 == 0) goto L77
            goto L64
        L77:
            return
        L78:
            if (r2 == 0) goto L81
            r2.close()     // Catch: java.io.IOException -> L7e
            goto L81
        L7e:
            android.util.Log.e(r1, r0)
        L81:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.CircularBuffer.resizeBubbleFile(long):void");
    }

    public final long totalNumberFiles() {
        if (this.mDumpList.size() != 0) {
            long size = this.mTotalDirectoryOccupation / this.mDumpList.size();
            if (size != 0) {
                return this.mBufferLimitSize / size;
            }
        }
        return 2300L;
    }

    public final void write(String str) {
        try {
            if (str == null) {
                PartialFileNode partialFileNode = this.mCurrentNode;
                if (partialFileNode.mFile != null) {
                    partialFileNode.mTimestamp = partialFileNode.mFile.lastModified();
                }
                this.mCurrentNode.compressFile();
                this.mTotalDirectoryOccupation += this.mCurrentNode.getFileSize();
                this.mCircularBufferSize += this.mCurrentNode.getFileSize();
                if (this.mCurrentNode.mFile != null) {
                    this.mLastDumpedFile = this.mCurrentNode.mFile.getName();
                }
                this.mCurrentNode.closeFile();
                this.mDumpList.add(this.mCurrentNode);
                this.mCurrentNode = addNode();
                return;
            }
            synchronized (this.mLock) {
                try {
                    if (!this.mCurrentNode.write(str)) {
                        PartialFileNode partialFileNode2 = this.mCurrentNode;
                        if (partialFileNode2.mFile != null) {
                            partialFileNode2.mTimestamp = partialFileNode2.mFile.lastModified();
                        }
                        this.mCurrentNode.compressFile();
                        this.mTotalDirectoryOccupation += this.mCurrentNode.getFileSize();
                        this.mCircularBufferSize += this.mCurrentNode.getFileSize();
                        this.mCurrentNode.closeFile();
                        this.mDumpList.add(this.mCurrentNode);
                        PartialFileNode addNode = addNode();
                        this.mCurrentNode = addNode;
                        addNode.write(str);
                        resizeBubbleFile(this.mBufferLimitSize - this.mTotalDirectoryOccupation);
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            Log.e("CircularBuffer", "write.Exception: " + e.toString());
            InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
        }
    }
}
