package com.android.server.enterprise.auditlog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Binder;
import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/* loaded from: classes2.dex */
public class CircularBuffer {
  public static ScheduledThreadPoolExecutor mSte = new ScheduledThreadPoolExecutor(1);
  public float mAdminCriticalSize;
  public String mAdminDirectoryPath;
  public float mAdminMaximumSize;
  public long mBufferLimitSize;
  public volatile long mCircularBufferSize;
  public Context mContext;
  public boolean mCriticalIntent;
  public PartialFileNode mCurrentNode;
  public List mDumpList;
  public final EdmStorageProvider mEdmStorageProvider;
  public float mFullBuffer;
  public boolean mFullIntent;
  public boolean mIsPseudoAdminOfOrganizationOwnedDevice;
  public volatile String mLastDumpedFile;
  public boolean mMaximumIntent;
  public volatile int mNumberOfDeprecatedFiles;
  public String mPackageName;
  public List mPendingIntentErrors;
  public long mTotalDirectoryOccupation;
  public final int mUid;
  public Object mLock = new Object();
  public volatile boolean mIsDumping = false;
  public volatile boolean mTypeOfDump = false;
  public volatile boolean mIsBootCompleted = false;

  public CircularBuffer(int i, Context context, String str) {
    mSte.allowCoreThreadTimeOut(false);
    this.mUid = i;
    this.mAdminCriticalSize = 70.0f;
    this.mAdminMaximumSize = 90.0f;
    this.mFullBuffer = 97.0f;
    this.mContext = context;
    this.mCircularBufferSize = 0L;
    this.mTotalDirectoryOccupation = 0L;
    this.mLastDumpedFile = null;
    this.mPackageName = str;
    EdmStorageProvider edmStorageProvider = new EdmStorageProvider(this.mContext);
    this.mEdmStorageProvider = edmStorageProvider;
    try {
      this.mIsPseudoAdminOfOrganizationOwnedDevice = edmStorageProvider.checkPseudoAdminForUid(i);
      Log.d(
          "CircularBuffer",
          "mIsPseudoAdminOfOrganizationOwnedDevice = "
              + this.mIsPseudoAdminOfOrganizationOwnedDevice);
    } catch (SettingNotFoundException e) {
      Log.e(
          "CircularBuffer", "mEdmStorageProvider.checkPseudoAdminForUid: error " + e.getMessage());
    }
    this.mBufferLimitSize = getBufferLogSize();
    this.mNumberOfDeprecatedFiles = getNumberOfDeprecatedFiles();
    this.mDumpList = Collections.synchronizedList(new ArrayList());
    this.mPendingIntentErrors = Collections.synchronizedList(new ArrayList());
    this.mAdminDirectoryPath = "/data/system/" + String.valueOf(this.mUid);
    populateCircularBuffer();
  }

  public final void populateCircularBuffer() {
    File file = new File(this.mAdminDirectoryPath);
    if (!file.exists()) {
      file.mkdir();
      this.mCurrentNode = addNode();
      return;
    }
    File[] dirListByAscendingDate = dirListByAscendingDate(file);
    if (dirListByAscendingDate != null) {
      int i = 0;
      for (File file2 : dirListByAscendingDate) {
        if (file2.isDirectory()) {
          deleteDirectory(file2);
        } else {
          if (file2.length() == 0) {
            file2.delete();
          } else {
            PartialFileNode partialFileNode = new PartialFileNode(file2, this.mPackageName);
            partialFileNode.setWasWritten(true);
            try {
              if (!isCompressed(file2)) {
                formatIfEmptyOrCorrupted(file2);
                if (file2.length() == 0) {
                  file2.delete();
                } else {
                  partialFileNode.compressFile();
                }
              }
            } catch (IOException e) {
              Log.d("CircularBuffer", "IOException: " + e.toString());
            }
            this.mDumpList.add(partialFileNode);
            if (i > this.mNumberOfDeprecatedFiles) {
              this.mCircularBufferSize += partialFileNode.getFileSize();
            } else {
              partialFileNode.setDeprecated(true);
            }
          }
        }
        i++;
      }
      reCalculateDirectoryAndBubbleSizes(dirListByAscendingDate);
    }
    this.mCurrentNode = addNode();
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x003e, code lost:

     if (r1 == null) goto L14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:9:0x0041, code lost:

     return r2;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int getNumberOfDeprecatedFiles() {
    Cursor cursor = null;
    int i = 0;
    try {
      try {
        cursor =
            this.mEdmStorageProvider.getCursorByAdmin(
                "AUDITLOG", this.mUid, new String[] {"auditNumberOfDepFiles"});
        if (cursor != null) {
          cursor.moveToFirst();
          i = cursor.getInt(0);
        }
      } catch (SQLException e) {
        Log.e("CircularBuffer", "Exception occurred accessing Enterprise db " + e.getMessage());
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  public final void setNumberOfDeprecatedFiles(int i) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("auditNumberOfDepFiles", Integer.valueOf(i));
    this.mEdmStorageProvider.putValues(this.mUid, "AUDITLOG", contentValues);
  }

  public final File[] dirListByAscendingDate(File file) {
    File[] listFiles;
    if (!file.isDirectory() || (listFiles = file.listFiles()) == null) {
      return null;
    }
    Arrays.sort(
        listFiles,
        new Comparator() { // from class: com.android.server.enterprise.auditlog.CircularBuffer.1
          @Override // java.util.Comparator
          public int compare(Object obj, Object obj2) {
            return Long.valueOf(((File) obj).lastModified())
                .compareTo(Long.valueOf(((File) obj2).lastModified()));
          }
        });
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

  public final void reCalculateDirectoryAndBubbleSizes(File[] fileArr) {
    if (fileArr != null) {
      for (File file : fileArr) {
        this.mTotalDirectoryOccupation += file.length();
      }
    }
    resizeBubbleFile(this.mBufferLimitSize - this.mTotalDirectoryOccupation);
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:32:0x005f
  	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
  	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
  	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
  */
  public final void formatIfEmptyOrCorrupted(java.io.File r12) {
    /*
        r11 = this;
        java.lang.String r0 = "formatIfEmptyOrCorrupted.IOException"
        java.lang.String r1 = "CircularBuffer"
        r2 = 0
        java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
        java.lang.String r4 = "rwd"
        r3.<init>(r12, r4)     // Catch: java.lang.Throwable -> L4c java.lang.Exception -> L4e
        r12 = 65536(0x10000, float:9.1835E-41)
        byte[] r12 = new byte[r12]     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
        r4 = 0
    L13:
        int r2 = r3.read(r12)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
        r6 = 0
        if (r2 <= 0) goto L36
        int r7 = r2 + (-1)
        r7 = r12[r7]     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
        if (r7 != 0) goto L32
        r7 = r6
        r8 = r7
    L22:
        if (r7 >= r2) goto L37
        r9 = r12[r7]     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
        r10 = 10
        if (r9 != r10) goto L2b
        r8 = r7
    L2b:
        if (r9 != 0) goto L2f
        r6 = 1
        goto L37
    L2f:
        int r7 = r7 + 1
        goto L22
    L32:
        r6 = 1
        long r4 = r4 + r6
        goto L13
    L36:
        r8 = r6
    L37:
        if (r6 == 0) goto L42
        r6 = 65536(0x10000, double:3.2379E-319)
        long r4 = r4 * r6
        long r6 = (long) r8     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
        long r4 = r4 + r6
        r3.setLength(r4)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
    L42:
        r3.close()     // Catch: java.io.IOException -> L5f
        goto L62
    L46:
        r11 = move-exception
        r2 = r3
        goto L63
    L49:
        r12 = move-exception
        r2 = r3
        goto L4f
    L4c:
        r11 = move-exception
        goto L63
    L4e:
        r12 = move-exception
    L4f:
        java.lang.String r3 = "formatIfEmptyOrCorrupted.Exception"
        android.util.Log.e(r1, r3)     // Catch: java.lang.Throwable -> L4c
        java.util.List r11 = r11.mPendingIntentErrors     // Catch: java.lang.Throwable -> L4c
        r11.add(r12)     // Catch: java.lang.Throwable -> L4c
        if (r2 == 0) goto L62
        r2.close()     // Catch: java.io.IOException -> L5f
        goto L62
    L5f:
        android.util.Log.e(r1, r0)
    L62:
        return
    L63:
        if (r2 == 0) goto L6c
        r2.close()     // Catch: java.io.IOException -> L69
        goto L6c
    L69:
        android.util.Log.e(r1, r0)
    L6c:
        throw r11
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " com.android.server.enterprise.auditlog.CircularBuffer.formatIfEmptyOrCorrupted(java.io.File):void");
  }

  public final PartialFileNode addNode() {
    if (this.mIsBootCompleted) {
      checkCriticalSizes();
    }
    if (!this.mIsDumping && this.mDumpList.size() > totalNumberFiles()) {
      cleanBuffer();
    }
    return new PartialFileNode(this.mAdminDirectoryPath, this.mPackageName);
  }

  public void write(String str) {
    try {
      if (str == null) {
        this.mCurrentNode.setTimestamp();
        this.mCurrentNode.compressFile();
        this.mTotalDirectoryOccupation += this.mCurrentNode.getFileSize();
        this.mCircularBufferSize += this.mCurrentNode.getFileSize();
        if (this.mCurrentNode.getFile() != null) {
          this.mLastDumpedFile = this.mCurrentNode.getFile().getName();
        }
        this.mCurrentNode.closeFile();
        this.mDumpList.add(this.mCurrentNode);
        this.mCurrentNode = addNode();
        return;
      }
      synchronized (this.mLock) {
        if (!this.mCurrentNode.write(str)) {
          this.mCurrentNode.setTimestamp();
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
      }
    } catch (Exception e) {
      Log.e("CircularBuffer", "write.Exception: " + e.toString());
      InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
    }
  }

  public Object getDumpFilesList() {
    ArrayList arrayList;
    synchronized (this.mDumpList) {
      arrayList = new ArrayList(this.mDumpList);
    }
    return arrayList;
  }

  public void setCriticalLogSize(int i) {
    this.mAdminCriticalSize = i;
    this.mCriticalIntent = false;
  }

  public int getCriticalLogSize() {
    return (int) this.mAdminCriticalSize;
  }

  public void setMaximumLogSize(int i) {
    this.mAdminMaximumSize = i;
    this.mMaximumIntent = false;
  }

  public int getMaximumLogSize() {
    return (int) this.mAdminMaximumSize;
  }

  public int getCurrentLogFileSize() {
    int i = (int) ((this.mCircularBufferSize * 100) / this.mBufferLimitSize);
    if (i > 100) {
      return 100;
    }
    return i;
  }

  public void setBufferLogSize(long j) {
    this.mBufferLimitSize = j;
    createBubbleDir();
  }

  public long getBufferLogSize() {
    ContentValues contentValues = new ContentValues();
    contentValues.put("adminUid", Integer.valueOf(this.mUid));
    return this.mEdmStorageProvider.getLong("AUDITLOG", "auditLogBufferSize", contentValues);
  }

  public void closeFile() {
    synchronized (this.mLock) {
      if (this.mCurrentNode.getWasWritten()) {
        this.mCurrentNode.compressFile();
        this.mCurrentNode.closeFile();
      } else {
        this.mCurrentNode.delete();
      }
      this.mCurrentNode = addNode();
    }
  }

  public void setBootCompleted(boolean z) {
    this.mIsBootCompleted = z;
    if (this.mIsBootCompleted) {
      synchronized (this.mPendingIntentErrors) {
        Iterator it = this.mPendingIntentErrors.iterator();
        while (it.hasNext()) {
          InformFailure.getInstance().broadcastFailure((Exception) it.next(), this.mPackageName);
        }
      }
    }
  }

  public void deleteAllFiles() {
    if (this.mCurrentNode != null) {
      synchronized (this.mLock) {
        this.mCurrentNode.closeFile();
        this.mCurrentNode.delete();
      }
    }
    synchronized (this.mDumpList) {
      Iterator it = this.mDumpList.iterator();
      while (it.hasNext()) {
        ((PartialFileNode) it.next()).delete();
        it.remove();
      }
    }
    deleteDirectory(new File(this.mAdminDirectoryPath));
    deleteDirectory(new File("/data/system/" + this.mUid + "_bubble/bubbleFile"));
    deleteDirectory(new File("/data/system/" + this.mUid + "_bubble"));
  }

  public synchronized void setIsDumping(boolean z, boolean z2) {
    this.mIsDumping = z;
    if (!z && z2 && this.mTypeOfDump) {
      this.mTypeOfDump = false;
      markDeprecatedFiles();
    }
  }

  public void setTypeOfDump(boolean z) {
    this.mTypeOfDump = z;
  }

  public final void markDeprecatedFiles() {
    synchronized (this.mDumpList) {
      List list = this.mDumpList;
      ListIterator listIterator = list.listIterator(list.size());
      boolean z = false;
      while (listIterator.hasPrevious()) {
        PartialFileNode partialFileNode = (PartialFileNode) listIterator.previous();
        if (!partialFileNode.getWasWritten()) {
          partialFileNode.delete();
          listIterator.remove();
        } else if (z) {
          if (partialFileNode.isDeprecated()) {
            break;
          }
          this.mCircularBufferSize -= partialFileNode.getFileSize();
          partialFileNode.setDeprecated(true);
          this.mNumberOfDeprecatedFiles++;
        } else if (this.mLastDumpedFile != null
            && partialFileNode.getFile().getName().equals(this.mLastDumpedFile)) {
          this.mLastDumpedFile = null;
          listIterator.next();
          z = true;
        }
      }
      setNumberOfDeprecatedFiles(this.mNumberOfDeprecatedFiles);
    }
  }

  public final void checkCriticalSizes() {
    float f = (int) ((this.mCircularBufferSize * 100) / this.mBufferLimitSize);
    if (f >= this.mAdminCriticalSize) {
      if (!this.mCriticalIntent) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.AUDIT_CRITICAL_SIZE");
        intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
        intent.setPackage(this.mPackageName);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        this.mContext.sendBroadcastAsUser(
            intent,
            new UserHandle(getTargetUserId()),
            "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        try {
          String kpuPackageName = KpuHelper.getInstance(this.mContext).getKpuPackageName();
          Intent intent2 = new Intent("com.samsung.android.knox.intent.action.AUDIT_CRITICAL_SIZE");
          intent2.putExtra(
              "com.samsung.android.knox.intent.extra.ADMIN_UID",
              this.mContext
                  .getPackageManager()
                  .getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
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
    } else {
      this.mCriticalIntent = false;
    }
    if (f >= this.mAdminMaximumSize) {
      if (!this.mMaximumIntent) {
        Intent intent3 = new Intent("com.samsung.android.knox.intent.action.AUDIT_MAXIMUM_SIZE");
        intent3.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
        intent3.setPackage(this.mPackageName);
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        this.mContext.sendBroadcastAsUser(
            intent3,
            new UserHandle(getTargetUserId()),
            "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        try {
          String kpuPackageName2 = KpuHelper.getInstance(this.mContext).getKpuPackageName();
          Intent intent4 = new Intent("com.samsung.android.knox.intent.action.AUDIT_MAXIMUM_SIZE");
          intent4.putExtra(
              "com.samsung.android.knox.intent.extra.ADMIN_UID",
              this.mContext
                  .getPackageManager()
                  .getPackageUidAsUser(kpuPackageName2, UserHandle.getCallingUserId()));
          intent4.setPackage(kpuPackageName2);
          this.mContext.sendBroadcast(
              intent4, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
        } catch (Exception e2) {
          e2.printStackTrace();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity2);
        this.mMaximumIntent = true;
      }
    } else {
      this.mMaximumIntent = false;
    }
    if (f >= this.mFullBuffer) {
      if (this.mFullIntent) {
        return;
      }
      Intent intent5 = new Intent("com.samsung.android.knox.intent.action.AUDIT_FULL_SIZE");
      intent5.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mUid);
      intent5.setPackage(this.mPackageName);
      long clearCallingIdentity3 = Binder.clearCallingIdentity();
      this.mContext.sendBroadcastAsUser(
          intent5,
          new UserHandle(getTargetUserId()),
          "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
      try {
        String kpuPackageName3 = KpuHelper.getInstance(this.mContext).getKpuPackageName();
        Intent intent6 = new Intent("com.samsung.android.knox.intent.action.AUDIT_FULL_SIZE");
        intent6.putExtra(
            "com.samsung.android.knox.intent.extra.ADMIN_UID",
            this.mContext
                .getPackageManager()
                .getPackageUidAsUser(kpuPackageName3, UserHandle.getCallingUserId()));
        intent6.setPackage(kpuPackageName3);
        this.mContext.sendBroadcast(intent6, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
      } catch (Exception e3) {
        e3.printStackTrace();
      }
      Binder.restoreCallingIdentity(clearCallingIdentity3);
      this.mFullIntent = true;
      InformFailure.getInstance().broadcastFailure("Full Size Reached!", this.mPackageName);
      return;
    }
    this.mFullIntent = false;
  }

  public final int getTargetUserId() {
    EnterpriseDeviceManagerService enterpriseDeviceManagerService;
    int userId = UserHandle.getUserId(this.mUid);
    return (!this.mIsPseudoAdminOfOrganizationOwnedDevice
            || (enterpriseDeviceManagerService = EnterpriseDeviceManagerService.getInstance())
                == null)
        ? userId
        : enterpriseDeviceManagerService.getOrganizationOwnedProfileUserId();
  }

  public final void cleanBuffer() {
    synchronized (this.mDumpList) {
      Iterator it = this.mDumpList.iterator();
      while (this.mDumpList.size() > totalNumberFiles()) {
        PartialFileNode partialFileNode = (PartialFileNode) it.next();
        if (partialFileNode.isDeprecated()) {
          int i = this.mNumberOfDeprecatedFiles;
          this.mNumberOfDeprecatedFiles = i - 1;
          setNumberOfDeprecatedFiles(i);
        } else {
          this.mCircularBufferSize -= partialFileNode.getFileSize();
        }
        long fileSize = this.mTotalDirectoryOccupation - partialFileNode.getFileSize();
        this.mTotalDirectoryOccupation = fileSize;
        resizeBubbleFile(this.mBufferLimitSize - fileSize);
        partialFileNode.delete();
        it.remove();
      }
    }
  }

  public void createBubbleDir() {
    File file = new File("/data/system/" + this.mUid + "_bubble");
    if (file.exists()) {
      return;
    }
    file.mkdir();
  }

  /* JADX WARN: Code restructure failed: missing block: B:22:0x0073, code lost:

     if (r2 == null) goto L37;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void resizeBubbleFile(long j) {
    RandomAccessFile randomAccessFile;
    RandomAccessFile randomAccessFile2;
    int i;
    if (j <= 0) {
      new File("/data/system/" + this.mUid + "_bubble/bubbleFile").delete();
      return;
    }
    RandomAccessFile randomAccessFile3 = null;
    randomAccessFile3 = null;
    randomAccessFile3 = null;
    randomAccessFile3 = null;
    try {
      try {
        try {
          randomAccessFile2 =
              new RandomAccessFile(
                  new File("/data/system/" + this.mUid + "_bubble/bubbleFile"), "rws");
          i = ((int) j) - 1;
        } catch (IOException unused) {
          Log.e("CircularBuffer", "resizeBubbleFile.IOException");
        }
      } catch (FileNotFoundException unused2) {
      } catch (IOException unused3) {
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      byte[] bArr = new byte[i];
      randomAccessFile2.setLength(j);
      randomAccessFile2.write(bArr, 0, i);
      randomAccessFile2.close();
      randomAccessFile3 = bArr;
    } catch (FileNotFoundException unused4) {
      randomAccessFile3 = randomAccessFile2;
      Log.e("CircularBuffer", "resizeBubbleFile.FileNotFoundException");
      randomAccessFile = randomAccessFile3;
    } catch (IOException unused5) {
      randomAccessFile3 = randomAccessFile2;
      Log.e("CircularBuffer", "resizeBubbleFile.IOException");
      if (randomAccessFile3 != null) {
        randomAccessFile = randomAccessFile3;
        randomAccessFile.close();
        randomAccessFile3 = randomAccessFile;
      }
    } catch (Throwable th2) {
      th = th2;
      randomAccessFile3 = randomAccessFile2;
      if (randomAccessFile3 != null) {
        try {
          randomAccessFile3.close();
        } catch (IOException unused6) {
          Log.e("CircularBuffer", "resizeBubbleFile.IOException");
        }
      }
      throw th;
    }
  }

  public final void deleteDirectory(File file) {
    if (file.isDirectory()) {
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
          return;
        }
        return;
      }
      return;
    }
    file.delete();
    Log.d("CircularBuffer", "File is deleted : " + file.getAbsolutePath());
  }

  public final boolean isCompressed(File file) {
    FileInputStream fileInputStream = new FileInputStream(file);
    byte[] bArr = new byte[2];
    boolean z = false;
    try {
      fileInputStream.read(bArr, 0, 2);
      if (bArr[0] == 31) {
        if (bArr[1] == -117) {
          z = true;
        }
      }
      return z;
    } finally {
      try {
        fileInputStream.close();
      } catch (Exception unused) {
      }
    }
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

  public void setLastTimestamp(ArrayList arrayList) {
    Iterator it = arrayList.iterator();
    PartialFileNode partialFileNode = null;
    while (it.hasNext()) {
      partialFileNode = (PartialFileNode) it.next();
    }
    if (partialFileNode != null) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(
          "auditLogLastTimestamp",
          Long.valueOf(Long.parseLong(partialFileNode.getFile().getName())));
      this.mEdmStorageProvider.putValues(this.mUid, "AUDITLOG", contentValues);
    }
  }
}
