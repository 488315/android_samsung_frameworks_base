package com.android.server.tare;

import android.os.Environment;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.SparseLongArray;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.clipboard.ClipboardService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class Scribe {
  public static final boolean DEBUG;
  public static final String TAG;
  public final Analyst mAnalyst;
  public final Runnable mCleanRunnable;
  public final InternalResourceService mIrs;
  public long mLastReclamationTime;
  public long mLastStockRecalculationTime;
  public final SparseArrayMap mLedgers;
  public long mLoadedTimeSinceFirstSetup;
  public final SparseLongArray mRealtimeSinceUsersAddedOffsets;
  public long mRemainingConsumableCakes;
  public long mSatiatedConsumptionLimit;
  public final AtomicFile mStateFile;
  public final Runnable mWriteRunnable;

  static {
    String str = "TARE-" + Scribe.class.getSimpleName();
    TAG = str;
    DEBUG = InternalResourceService.DEBUG || Log.isLoggable(str, 3);
  }

  public Scribe(InternalResourceService internalResourceService, Analyst analyst) {
    this(internalResourceService, analyst, Environment.getDataSystemDirectory());
  }

  public Scribe(InternalResourceService internalResourceService, Analyst analyst, File file) {
    this.mLedgers = new SparseArrayMap();
    this.mRealtimeSinceUsersAddedOffsets = new SparseLongArray();
    this.mCleanRunnable =
        new Runnable() { // from class: com.android.server.tare.Scribe$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            Scribe.this.cleanupLedgers();
          }
        };
    this.mWriteRunnable =
        new Runnable() { // from class: com.android.server.tare.Scribe$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            Scribe.this.writeState();
          }
        };
    this.mIrs = internalResourceService;
    this.mAnalyst = analyst;
    File file2 = new File(file, "tare");
    file2.mkdirs();
    this.mStateFile = new AtomicFile(new File(file2, "state.xml"), "tare");
  }

  public void adjustRemainingConsumableCakesLocked(long j) {
    long j2 = this.mRemainingConsumableCakes;
    long j3 = j + j2;
    this.mRemainingConsumableCakes = j3;
    if (j3 < 0) {
      Slog.w(
          TAG,
          "Overdrew consumable cakes by "
              + TareUtils.cakeToString(-this.mRemainingConsumableCakes));
      this.mRemainingConsumableCakes = 0L;
    }
    if (this.mRemainingConsumableCakes != j2) {
      postWrite();
    }
  }

  public void discardLedgerLocked(int i, String str) {
    this.mLedgers.delete(i, str);
    postWrite();
  }

  public void onUserRemovedLocked(int i) {
    this.mLedgers.delete(i);
    this.mRealtimeSinceUsersAddedOffsets.delete(i);
    postWrite();
  }

  public long getSatiatedConsumptionLimitLocked() {
    return this.mSatiatedConsumptionLimit;
  }

  public long getLastReclamationTimeLocked() {
    return this.mLastReclamationTime;
  }

  public long getLastStockRecalculationTimeLocked() {
    return this.mLastStockRecalculationTime;
  }

  public Ledger getLedgerLocked(int i, String str) {
    Ledger ledger = (Ledger) this.mLedgers.get(i, str);
    if (ledger != null) {
      return ledger;
    }
    Ledger ledger2 = new Ledger();
    this.mLedgers.add(i, str, ledger2);
    return ledger2;
  }

  public SparseArrayMap getLedgersLocked() {
    return this.mLedgers;
  }

  public long getCakesInCirculationForLoggingLocked() {
    long j = 0;
    for (int numMaps = this.mLedgers.numMaps() - 1; numMaps >= 0; numMaps--) {
      for (int numElementsForKeyAt = this.mLedgers.numElementsForKeyAt(numMaps) - 1;
          numElementsForKeyAt >= 0;
          numElementsForKeyAt--) {
        j += ((Ledger) this.mLedgers.valueAt(numMaps, numElementsForKeyAt)).getCurrentBalance();
      }
    }
    return j;
  }

  public long getRealtimeSinceFirstSetupMs(long j) {
    return this.mLoadedTimeSinceFirstSetup + j;
  }

  public long getRemainingConsumableCakesLocked() {
    return this.mRemainingConsumableCakes;
  }

  public SparseLongArray getRealtimeSinceUsersAddedLocked(long j) {
    SparseLongArray sparseLongArray = new SparseLongArray();
    for (int size = this.mRealtimeSinceUsersAddedOffsets.size() - 1; size >= 0; size--) {
      sparseLongArray.put(
          this.mRealtimeSinceUsersAddedOffsets.keyAt(size),
          this.mRealtimeSinceUsersAddedOffsets.valueAt(size) + j);
    }
    return sparseLongArray;
  }

  /* JADX WARN: Removed duplicated region for block: B:70:0x0127  */
  /* JADX WARN: Removed duplicated region for block: B:75:0x0155 A[Catch: all -> 0x01b1, TryCatch #0 {all -> 0x01b1, blocks: (B:27:0x0073, B:31:0x0080, B:34:0x0087, B:36:0x008b, B:41:0x0098, B:43:0x00a6, B:46:0x00b1, B:51:0x00d2, B:57:0x0197, B:58:0x00e9, B:61:0x00f0, B:72:0x012b, B:73:0x0142, B:74:0x014a, B:75:0x0155, B:77:0x0104, B:80:0x010e, B:83:0x0119, B:87:0x01a3), top: B:26:0x0073 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void loadFromDiskLocked() {
    String name;
    int i;
    SparseArray sparseArray;
    int attributeInt;
    this.mLedgers.clear();
    if (!recordExists()) {
      this.mSatiatedConsumptionLimit = this.mIrs.getInitialSatiatedConsumptionLimitLocked();
      this.mRemainingConsumableCakes = this.mIrs.getConsumptionLimitLocked();
      return;
    }
    this.mSatiatedConsumptionLimit = 0L;
    this.mRemainingConsumableCakes = 0L;
    SparseArray sparseArray2 = new SparseArray();
    SparseArrayMap installedPackages = this.mIrs.getInstalledPackages();
    int i2 = 1;
    for (int numMaps = installedPackages.numMaps() - 1; numMaps >= 0; numMaps--) {
      int keyAt = installedPackages.keyAt(numMaps);
      for (int numElementsForKeyAt = installedPackages.numElementsForKeyAt(numMaps) - 1;
          numElementsForKeyAt >= 0;
          numElementsForKeyAt--) {
        InstalledPackageInfo installedPackageInfo =
            (InstalledPackageInfo) installedPackages.valueAt(numMaps, numElementsForKeyAt);
        if (installedPackageInfo.uid != -1) {
          ArraySet arraySet = (ArraySet) sparseArray2.get(keyAt);
          if (arraySet == null) {
            arraySet = new ArraySet();
            sparseArray2.put(keyAt, arraySet);
          }
          arraySet.add(installedPackageInfo.packageName);
        }
      }
    }
    ArrayList arrayList = new ArrayList();
    try {
      FileInputStream openRead = this.mStateFile.openRead();
      try {
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
        int eventType = resolvePullParser.getEventType();
        while (eventType != 2 && eventType != 1) {
          eventType = resolvePullParser.next();
        }
        if (eventType == 1) {
          if (DEBUG) {
            Slog.w(TAG, "No persisted state.");
          }
          if (openRead != null) {
            openRead.close();
            return;
          }
          return;
        }
        if ("tare".equals(resolvePullParser.getName())
            && ((attributeInt = resolvePullParser.getAttributeInt((String) null, "version")) < 0
                || attributeInt > 0)) {
          Slog.e(TAG, "Invalid version number (" + attributeInt + "), aborting file read");
          if (openRead != null) {
            openRead.close();
            return;
          }
          return;
        }
        long currentTimeMillis = System.currentTimeMillis() - 691200000;
        long j = Long.MAX_VALUE;
        for (int next = resolvePullParser.next(); next != i2; next = resolvePullParser.next()) {
          if (next == 2 && (name = resolvePullParser.getName()) != null) {
            int hashCode = name.hashCode();
            if (hashCode == -934521548) {
              if (name.equals("report")) {
                i = 2;
                if (i == 0) {}
              }
              i = -1;
              if (i == 0) {}
            } else if (hashCode != 3599307) {
              if (hashCode == 689502574 && name.equals("irs-state")) {
                i = 0;
                if (i == 0) {
                  if (i == i2) {
                    j =
                        Math.min(
                            j,
                            readUserFromXmlLocked(
                                resolvePullParser, sparseArray2, currentTimeMillis));
                  } else if (i == 2) {
                    arrayList.add(readReportFromXml(resolvePullParser));
                  } else {
                    Slog.e(TAG, "Unexpected tag: " + name);
                  }
                  sparseArray = sparseArray2;
                  sparseArray2 = sparseArray;
                  i2 = 1;
                } else {
                  this.mLastReclamationTime =
                      resolvePullParser.getAttributeLong((String) null, "lastReclamationTime");
                  sparseArray = sparseArray2;
                  this.mLastStockRecalculationTime =
                      resolvePullParser.getAttributeLong(
                          (String) null, "lastStockRecalculationTime", 0L);
                  this.mLoadedTimeSinceFirstSetup =
                      resolvePullParser.getAttributeLong(
                          (String) null, "timeSinceFirstSetup", -SystemClock.elapsedRealtime());
                  this.mSatiatedConsumptionLimit =
                      resolvePullParser.getAttributeLong(
                          (String) null,
                          "consumptionLimit",
                          this.mIrs.getInitialSatiatedConsumptionLimitLocked());
                  long consumptionLimitLocked = this.mIrs.getConsumptionLimitLocked();
                  this.mRemainingConsumableCakes =
                      Math.min(
                          consumptionLimitLocked,
                          resolvePullParser.getAttributeLong(
                              (String) null, "remainingConsumableCakes", consumptionLimitLocked));
                  sparseArray2 = sparseArray;
                  i2 = 1;
                }
              }
              i = -1;
              if (i == 0) {}
            } else {
              if (name.equals("user")) {
                i = i2;
                if (i == 0) {}
              }
              i = -1;
              if (i == 0) {}
            }
          }
          sparseArray = sparseArray2;
          sparseArray2 = sparseArray;
          i2 = 1;
        }
        this.mAnalyst.loadReports(arrayList);
        scheduleCleanup(j);
        if (openRead != null) {
          openRead.close();
        }
      } finally {
      }
    } catch (IOException | XmlPullParserException e) {
      Slog.wtf(TAG, "Error reading state from disk", e);
    }
  }

  public void postWrite() {
    TareHandlerThread.getHandler().postDelayed(this.mWriteRunnable, 30000L);
  }

  public boolean recordExists() {
    return this.mStateFile.exists();
  }

  public void setConsumptionLimitLocked(long j) {
    long j2 = this.mRemainingConsumableCakes;
    if (j2 > j) {
      this.mRemainingConsumableCakes = j;
    } else {
      long j3 = this.mSatiatedConsumptionLimit;
      if (j > j3) {
        this.mRemainingConsumableCakes = j - (j3 - j2);
      }
    }
    this.mSatiatedConsumptionLimit = j;
    postWrite();
  }

  public void setLastReclamationTimeLocked(long j) {
    this.mLastReclamationTime = j;
    postWrite();
  }

  public void setLastStockRecalculationTimeLocked(long j) {
    this.mLastStockRecalculationTime = j;
    postWrite();
  }

  public void setUserAddedTimeLocked(int i, long j) {
    this.mRealtimeSinceUsersAddedOffsets.put(i, -j);
  }

  public void tearDownLocked() {
    TareHandlerThread.getHandler().removeCallbacks(this.mCleanRunnable);
    TareHandlerThread.getHandler().removeCallbacks(this.mWriteRunnable);
    this.mLedgers.clear();
    this.mRemainingConsumableCakes = 0L;
    this.mSatiatedConsumptionLimit = 0L;
    this.mLastReclamationTime = 0L;
  }

  public void writeImmediatelyForTesting() {
    this.mWriteRunnable.run();
  }

  public final void cleanupLedgers() {
    synchronized (this.mIrs.getLock()) {
      TareHandlerThread.getHandler().removeCallbacks(this.mCleanRunnable);
      long j = Long.MAX_VALUE;
      for (int numMaps = this.mLedgers.numMaps() - 1; numMaps >= 0; numMaps--) {
        int keyAt = this.mLedgers.keyAt(numMaps);
        for (int numElementsForKey = this.mLedgers.numElementsForKey(keyAt) - 1;
            numElementsForKey >= 0;
            numElementsForKey--) {
          Ledger.Transaction removeOldTransactions =
              ((Ledger)
                      this.mLedgers.get(
                          keyAt, (String) this.mLedgers.keyAt(numMaps, numElementsForKey)))
                  .removeOldTransactions(691200000L);
          if (removeOldTransactions != null) {
            j = Math.min(j, removeOldTransactions.endTimeMs);
          }
        }
      }
      scheduleCleanup(j);
    }
  }

  public static Pair readLedgerFromXml(
      TypedXmlPullParser typedXmlPullParser, ArraySet arraySet, long j) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "pkgName");
    long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "currentBalance");
    boolean contains = arraySet.contains(attributeValue);
    if (!contains) {
      Slog.w(TAG, "Invalid pkg " + attributeValue + " is saved to disk");
    }
    int next = typedXmlPullParser.next();
    while (next != 1) {
      String name = typedXmlPullParser.getName();
      if (next == 3) {
        if ("ledger".equals(name)) {
          break;
        }
      } else {
        if (next != 2 || name == null) {
          Slog.e(TAG, "Unexpected event: (" + next + ") " + name);
          return null;
        }
        if (contains) {
          boolean z = DEBUG;
          if (z) {
            Slog.d(TAG, "Starting ledger tag: " + name);
          }
          if (name.equals("rewardBucket")) {
            arrayList2.add(readRewardBucketFromXml(typedXmlPullParser));
          } else if (name.equals("transaction")) {
            long attributeLong2 = typedXmlPullParser.getAttributeLong((String) null, "endTime");
            if (attributeLong2 > j) {
              arrayList.add(
                  new Ledger.Transaction(
                      typedXmlPullParser.getAttributeLong((String) null, "startTime"),
                      attributeLong2,
                      typedXmlPullParser.getAttributeInt((String) null, "eventId"),
                      typedXmlPullParser.getAttributeValue((String) null, "tag"),
                      typedXmlPullParser.getAttributeLong((String) null, "delta"),
                      typedXmlPullParser.getAttributeLong((String) null, "ctp")));
            } else if (z) {
              Slog.d(TAG, "Skipping event because it's too old.");
            }
          } else {
            Slog.e(TAG, "Unexpected event: (" + next + ") " + name);
            return null;
          }
        } else {
          continue;
        }
      }
      next = typedXmlPullParser.next();
    }
    if (contains) {
      return Pair.create(attributeValue, new Ledger(attributeLong, arrayList, arrayList2));
    }
    return null;
  }

  public final long readUserFromXmlLocked(
      TypedXmlPullParser typedXmlPullParser, SparseArray sparseArray, long j) {
    Pair readLedgerFromXml;
    Ledger ledger;
    int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "userId");
    ArraySet arraySet = (ArraySet) sparseArray.get(attributeInt);
    if (arraySet == null) {
      Slog.w(TAG, "Invalid user " + attributeInt + " is saved to disk");
      attributeInt = -10000;
    }
    if (attributeInt != -10000) {
      this.mRealtimeSinceUsersAddedOffsets.put(
          attributeInt,
          typedXmlPullParser.getAttributeLong(
              (String) null, "timeSinceFirstSetup", -SystemClock.elapsedRealtime()));
    }
    int next = typedXmlPullParser.next();
    long j2 = Long.MAX_VALUE;
    while (next != 1) {
      String name = typedXmlPullParser.getName();
      if (next == 3) {
        if ("user".equals(name)) {
          break;
        }
      } else if (!"ledger".equals(name)) {
        Slog.e(TAG, "Unknown tag: " + name);
      } else if (attributeInt != -10000
          && (readLedgerFromXml = readLedgerFromXml(typedXmlPullParser, arraySet, j)) != null
          && (ledger = (Ledger) readLedgerFromXml.second) != null) {
        this.mLedgers.add(attributeInt, (String) readLedgerFromXml.first, ledger);
        Ledger.Transaction earliestTransaction = ledger.getEarliestTransaction();
        if (earliestTransaction != null) {
          j2 = Math.min(j2, earliestTransaction.endTimeMs);
        }
      }
      next = typedXmlPullParser.next();
    }
    return j2;
  }

  public static Analyst.Report readReportFromXml(TypedXmlPullParser typedXmlPullParser) {
    Analyst.Report report = new Analyst.Report();
    report.cumulativeBatteryDischarge =
        typedXmlPullParser.getAttributeInt((String) null, "discharge");
    report.currentBatteryLevel = typedXmlPullParser.getAttributeInt((String) null, "batteryLevel");
    report.cumulativeProfit = typedXmlPullParser.getAttributeLong((String) null, "profit");
    report.numProfitableActions = typedXmlPullParser.getAttributeInt((String) null, "numProfits");
    report.cumulativeLoss = typedXmlPullParser.getAttributeLong((String) null, "loss");
    report.numUnprofitableActions = typedXmlPullParser.getAttributeInt((String) null, "numLoss");
    report.cumulativeRewards = typedXmlPullParser.getAttributeLong((String) null, "rewards");
    report.numRewards = typedXmlPullParser.getAttributeInt((String) null, "numRewards");
    report.cumulativePositiveRegulations =
        typedXmlPullParser.getAttributeLong((String) null, "posRegulations");
    report.numPositiveRegulations =
        typedXmlPullParser.getAttributeInt((String) null, "numPosRegulations");
    report.cumulativeNegativeRegulations =
        typedXmlPullParser.getAttributeLong((String) null, "negRegulations");
    report.numNegativeRegulations =
        typedXmlPullParser.getAttributeInt((String) null, "numNegRegulations");
    report.screenOffDurationMs =
        typedXmlPullParser.getAttributeLong((String) null, "screenOffDurationMs", 0L);
    report.screenOffDischargeMah =
        typedXmlPullParser.getAttributeLong((String) null, "screenOffDischargeMah", 0L);
    return report;
  }

  public static Ledger.RewardBucket readRewardBucketFromXml(TypedXmlPullParser typedXmlPullParser) {
    Ledger.RewardBucket rewardBucket = new Ledger.RewardBucket();
    rewardBucket.startTimeMs = typedXmlPullParser.getAttributeLong((String) null, "startTime");
    int next = typedXmlPullParser.next();
    while (next != 1) {
      String name = typedXmlPullParser.getName();
      if (next == 3) {
        if ("rewardBucket".equals(name)) {
          break;
        }
      } else {
        if (next != 2 || !"delta".equals(name)) {
          Slog.e(TAG, "Unexpected event: (" + next + ") " + name);
          return null;
        }
        rewardBucket.cumulativeDelta.put(
            typedXmlPullParser.getAttributeInt((String) null, "eventId"),
            typedXmlPullParser.getAttributeLong((String) null, "delta"));
      }
      next = typedXmlPullParser.next();
    }
    return rewardBucket;
  }

  public final void scheduleCleanup(long j) {
    if (j == Long.MAX_VALUE) {
      return;
    }
    TareHandlerThread.getHandler()
        .postDelayed(
            this.mCleanRunnable,
            Math.max(
                ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS,
                (j + 691200000) - System.currentTimeMillis()));
  }

  public final void writeState() {
    synchronized (this.mIrs.getLock()) {
      TareHandlerThread.getHandler().removeCallbacks(this.mWriteRunnable);
      TareHandlerThread.getHandler().removeCallbacks(this.mCleanRunnable);
      if (this.mIrs.getEnabledMode() == 0) {
        return;
      }
      long j = Long.MAX_VALUE;
      try {
        FileOutputStream startWrite = this.mStateFile.startWrite();
        try {
          TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
          resolveSerializer.startDocument((String) null, Boolean.TRUE);
          resolveSerializer.startTag((String) null, "tare");
          resolveSerializer.attributeInt((String) null, "version", 0);
          resolveSerializer.startTag((String) null, "irs-state");
          resolveSerializer.attributeLong(
              (String) null, "lastReclamationTime", this.mLastReclamationTime);
          resolveSerializer.attributeLong(
              (String) null, "lastStockRecalculationTime", this.mLastStockRecalculationTime);
          resolveSerializer.attributeLong(
              (String) null,
              "timeSinceFirstSetup",
              this.mLoadedTimeSinceFirstSetup + SystemClock.elapsedRealtime());
          resolveSerializer.attributeLong(
              (String) null, "consumptionLimit", this.mSatiatedConsumptionLimit);
          resolveSerializer.attributeLong(
              (String) null, "remainingConsumableCakes", this.mRemainingConsumableCakes);
          resolveSerializer.endTag((String) null, "irs-state");
          for (int numMaps = this.mLedgers.numMaps() - 1; numMaps >= 0; numMaps--) {
            j = Math.min(j, writeUserLocked(resolveSerializer, this.mLedgers.keyAt(numMaps)));
          }
          List reports = this.mAnalyst.getReports();
          int size = reports.size();
          for (int i = 0; i < size; i++) {
            writeReport(resolveSerializer, (Analyst.Report) reports.get(i));
          }
          resolveSerializer.endTag((String) null, "tare");
          resolveSerializer.endDocument();
          this.mStateFile.finishWrite(startWrite);
          if (startWrite != null) {
            startWrite.close();
          }
        } catch (Throwable th) {
          if (startWrite != null) {
            try {
              startWrite.close();
            } catch (Throwable th2) {
              th.addSuppressed(th2);
            }
          }
          throw th;
        }
      } catch (IOException e) {
        Slog.e(TAG, "Error writing state to disk", e);
      }
      scheduleCleanup(j);
    }
  }

  public final long writeUserLocked(TypedXmlSerializer typedXmlSerializer, int i) {
    int indexOfKey = this.mLedgers.indexOfKey(i);
    String str = null;
    String str2 = "user";
    typedXmlSerializer.startTag((String) null, "user");
    typedXmlSerializer.attributeInt((String) null, "userId", i);
    typedXmlSerializer.attributeLong(
        (String) null,
        "timeSinceFirstSetup",
        this.mRealtimeSinceUsersAddedOffsets.get(i, this.mLoadedTimeSinceFirstSetup)
            + SystemClock.elapsedRealtime());
    int numElementsForKey = this.mLedgers.numElementsForKey(i) - 1;
    long j = Long.MAX_VALUE;
    while (numElementsForKey >= 0) {
      String str3 = (String) this.mLedgers.keyAt(indexOfKey, numElementsForKey);
      Ledger ledger = (Ledger) this.mLedgers.get(i, str3);
      ledger.removeOldTransactions(691200000L);
      typedXmlSerializer.startTag(str, "ledger");
      typedXmlSerializer.attribute(str, "pkgName", str3);
      typedXmlSerializer.attributeLong(str, "currentBalance", ledger.getCurrentBalance());
      List transactions = ledger.getTransactions();
      int i2 = 0;
      while (i2 < transactions.size()) {
        Ledger.Transaction transaction = (Ledger.Transaction) transactions.get(i2);
        String str4 = str2;
        if (i2 == 0) {
          j = Math.min(j, transaction.endTimeMs);
        }
        writeTransaction(typedXmlSerializer, transaction);
        i2++;
        str2 = str4;
      }
      String str5 = str2;
      List rewardBuckets = ledger.getRewardBuckets();
      for (int i3 = 0; i3 < rewardBuckets.size(); i3++) {
        writeRewardBucket(typedXmlSerializer, (Ledger.RewardBucket) rewardBuckets.get(i3));
      }
      typedXmlSerializer.endTag((String) null, "ledger");
      numElementsForKey--;
      str = null;
      str2 = str5;
    }
    typedXmlSerializer.endTag(str, str2);
    return j;
  }

  public static void writeTransaction(
      TypedXmlSerializer typedXmlSerializer, Ledger.Transaction transaction) {
    typedXmlSerializer.startTag((String) null, "transaction");
    typedXmlSerializer.attributeLong((String) null, "startTime", transaction.startTimeMs);
    typedXmlSerializer.attributeLong((String) null, "endTime", transaction.endTimeMs);
    typedXmlSerializer.attributeInt((String) null, "eventId", transaction.eventId);
    String str = transaction.tag;
    if (str != null) {
      typedXmlSerializer.attribute((String) null, "tag", str);
    }
    typedXmlSerializer.attributeLong((String) null, "delta", transaction.delta);
    typedXmlSerializer.attributeLong((String) null, "ctp", transaction.ctp);
    typedXmlSerializer.endTag((String) null, "transaction");
  }

  public static void writeRewardBucket(
      TypedXmlSerializer typedXmlSerializer, Ledger.RewardBucket rewardBucket) {
    int size = rewardBucket.cumulativeDelta.size();
    if (size == 0) {
      return;
    }
    typedXmlSerializer.startTag((String) null, "rewardBucket");
    typedXmlSerializer.attributeLong((String) null, "startTime", rewardBucket.startTimeMs);
    for (int i = 0; i < size; i++) {
      typedXmlSerializer.startTag((String) null, "delta");
      typedXmlSerializer.attributeInt(
          (String) null, "eventId", rewardBucket.cumulativeDelta.keyAt(i));
      typedXmlSerializer.attributeLong(
          (String) null, "delta", rewardBucket.cumulativeDelta.valueAt(i));
      typedXmlSerializer.endTag((String) null, "delta");
    }
    typedXmlSerializer.endTag((String) null, "rewardBucket");
  }

  public static void writeReport(TypedXmlSerializer typedXmlSerializer, Analyst.Report report) {
    typedXmlSerializer.startTag((String) null, "report");
    typedXmlSerializer.attributeInt((String) null, "discharge", report.cumulativeBatteryDischarge);
    typedXmlSerializer.attributeInt((String) null, "batteryLevel", report.currentBatteryLevel);
    typedXmlSerializer.attributeLong((String) null, "profit", report.cumulativeProfit);
    typedXmlSerializer.attributeInt((String) null, "numProfits", report.numProfitableActions);
    typedXmlSerializer.attributeLong((String) null, "loss", report.cumulativeLoss);
    typedXmlSerializer.attributeInt((String) null, "numLoss", report.numUnprofitableActions);
    typedXmlSerializer.attributeLong((String) null, "rewards", report.cumulativeRewards);
    typedXmlSerializer.attributeInt((String) null, "numRewards", report.numRewards);
    typedXmlSerializer.attributeLong(
        (String) null, "posRegulations", report.cumulativePositiveRegulations);
    typedXmlSerializer.attributeInt(
        (String) null, "numPosRegulations", report.numPositiveRegulations);
    typedXmlSerializer.attributeLong(
        (String) null, "negRegulations", report.cumulativeNegativeRegulations);
    typedXmlSerializer.attributeInt(
        (String) null, "numNegRegulations", report.numNegativeRegulations);
    typedXmlSerializer.attributeLong(
        (String) null, "screenOffDurationMs", report.screenOffDurationMs);
    typedXmlSerializer.attributeLong(
        (String) null, "screenOffDischargeMah", report.screenOffDischargeMah);
    typedXmlSerializer.endTag((String) null, "report");
  }

  public void dumpLocked(final IndentingPrintWriter indentingPrintWriter, final boolean z) {
    indentingPrintWriter.println("Ledgers:");
    indentingPrintWriter.increaseIndent();
    this.mLedgers.forEach(
        new SparseArrayMap
            .TriConsumer() { // from class: com.android.server.tare.Scribe$$ExternalSyntheticLambda2
          public final void accept(int i, Object obj, Object obj2) {
            Scribe.this.lambda$dumpLocked$0(
                indentingPrintWriter, z, i, (String) obj, (Ledger) obj2);
          }
        });
    indentingPrintWriter.decreaseIndent();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$dumpLocked$0(
      IndentingPrintWriter indentingPrintWriter, boolean z, int i, String str, Ledger ledger) {
    indentingPrintWriter.print(TareUtils.appToString(i, str));
    if (this.mIrs.isSystem(i, str)) {
      indentingPrintWriter.print(" (system)");
    }
    indentingPrintWriter.println();
    indentingPrintWriter.increaseIndent();
    ledger.dump(indentingPrintWriter, z ? Integer.MAX_VALUE : 25);
    indentingPrintWriter.decreaseIndent();
  }
}
