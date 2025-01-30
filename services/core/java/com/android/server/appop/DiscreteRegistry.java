package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class DiscreteRegistry {
  public static final String TAG = "DiscreteRegistry";
  public static int sDiscreteFlags;
  public static long sDiscreteHistoryCutoff;
  public static long sDiscreteHistoryQuantization;
  public static int[] sDiscreteOps;
  public DiscreteOps mCachedOps;
  public boolean mDebugMode;
  public File mDiscreteAccessDir;
  public DiscreteOps mDiscreteOps;
  public final Object mInMemoryLock;
  public final Object mOnDiskLock;
  public static final long DEFAULT_DISCRETE_HISTORY_CUTOFF = Duration.ofDays(7).toMillis();
  public static final long MAXIMUM_DISCRETE_HISTORY_CUTOFF = Duration.ofDays(30).toMillis();
  public static final long DEFAULT_DISCRETE_HISTORY_QUANTIZATION = Duration.ofMinutes(1).toMillis();

  public DiscreteRegistry(Object obj) {
    Object obj2 = new Object();
    this.mOnDiskLock = obj2;
    this.mCachedOps = null;
    this.mDebugMode = false;
    this.mInMemoryLock = obj;
    synchronized (obj2) {
      this.mDiscreteAccessDir =
          new File(new File(Environment.getDataSystemDirectory(), "appops"), "discrete");
      createDiscreteAccessDirLocked();
      int readLargestChainIdFromDiskLocked = readLargestChainIdFromDiskLocked();
      synchronized (obj) {
        this.mDiscreteOps = new DiscreteOps(readLargestChainIdFromDiskLocked);
      }
    }
  }

  public void systemReady() {
    DeviceConfig.addOnPropertiesChangedListener(
        "privacy",
        AsyncTask.THREAD_POOL_EXECUTOR,
        new DeviceConfig
            .OnPropertiesChangedListener() { // from class:
                                             // com.android.server.appop.DiscreteRegistry$$ExternalSyntheticLambda0
          public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            DiscreteRegistry.this.lambda$systemReady$0(properties);
          }
        });
    lambda$systemReady$0(DeviceConfig.getProperties("privacy", new String[0]));
  }

  /* renamed from: setDiscreteHistoryParameters, reason: merged with bridge method [inline-methods] */
  public final void lambda$systemReady$0(DeviceConfig.Properties properties) {
    int[] parseOpsList;
    if (properties.getKeyset().contains("discrete_history_cutoff_millis")) {
      sDiscreteHistoryCutoff =
          properties.getLong("discrete_history_cutoff_millis", DEFAULT_DISCRETE_HISTORY_CUTOFF);
      if (!Build.IS_DEBUGGABLE && !this.mDebugMode) {
        sDiscreteHistoryCutoff = Long.min(MAXIMUM_DISCRETE_HISTORY_CUTOFF, sDiscreteHistoryCutoff);
      }
    } else {
      sDiscreteHistoryCutoff = DEFAULT_DISCRETE_HISTORY_CUTOFF;
    }
    if (properties.getKeyset().contains("discrete_history_quantization_millis")) {
      long j = DEFAULT_DISCRETE_HISTORY_QUANTIZATION;
      sDiscreteHistoryQuantization = properties.getLong("discrete_history_quantization_millis", j);
      if (!Build.IS_DEBUGGABLE && !this.mDebugMode) {
        sDiscreteHistoryQuantization = Math.max(j, sDiscreteHistoryQuantization);
      }
    } else {
      sDiscreteHistoryQuantization = DEFAULT_DISCRETE_HISTORY_QUANTIZATION;
    }
    int i = 11;
    if (properties.getKeyset().contains("discrete_history_op_flags")) {
      i = properties.getInt("discrete_history_op_flags", 11);
      sDiscreteFlags = i;
    }
    sDiscreteFlags = i;
    if (properties.getKeyset().contains("discrete_history_ops_cslist")) {
      parseOpsList =
          parseOpsList(
              properties.getString("discrete_history_ops_cslist", "1,0,26,27,100,101,120"));
    } else {
      parseOpsList = parseOpsList("1,0,26,27,100,101,120");
    }
    sDiscreteOps = parseOpsList;
  }

  public void recordDiscreteAccess(
      int i, String str, int i2, String str2, int i3, int i4, long j, long j2, int i5, int i6) {
    if (isDiscreteOp(i2, i3)) {
      synchronized (this.mInMemoryLock) {
        this.mDiscreteOps.addDiscreteAccess(i2, i, str, str2, i3, i4, j, j2, i5, i6);
      }
    }
  }

  public void writeAndClearAccessHistory() {
    DiscreteOps discreteOps;
    synchronized (this.mOnDiskLock) {
      if (this.mDiscreteAccessDir == null) {
        Slog.d(TAG, "State not saved - persistence not initialized.");
        return;
      }
      synchronized (this.mInMemoryLock) {
        discreteOps = this.mDiscreteOps;
        this.mDiscreteOps = new DiscreteOps(discreteOps.mChainIdOffset);
        this.mCachedOps = null;
      }
      deleteOldDiscreteHistoryFilesLocked();
      if (!discreteOps.isEmpty()) {
        persistDiscreteOpsLocked(discreteOps);
      }
    }
  }

  public void addFilteredDiscreteOpsToHistoricalOps(
      AppOpsManager.HistoricalOps historicalOps,
      long j,
      long j2,
      int i,
      int i2,
      String str,
      String[] strArr,
      String str2,
      int i3,
      Set set) {
    boolean z = set != null;
    DiscreteOps allDiscreteOps = getAllDiscreteOps();
    ArrayMap arrayMap = new ArrayMap();
    if (z) {
      arrayMap = createAttributionChains(allDiscreteOps, set);
    }
    ArrayMap arrayMap2 = arrayMap;
    allDiscreteOps.filter(
        Math.max(
            j,
            Instant.now()
                .minus(sDiscreteHistoryCutoff, (TemporalUnit) ChronoUnit.MILLIS)
                .toEpochMilli()),
        j2,
        i,
        i2,
        str,
        strArr,
        str2,
        i3,
        arrayMap2);
    allDiscreteOps.applyToHistoricalOps(historicalOps, arrayMap2);
  }

  public final int readLargestChainIdFromDiskLocked() {
    File[] listFiles = this.mDiscreteAccessDir.listFiles();
    if (listFiles != null && listFiles.length > 0) {
      long j = 0;
      File file = null;
      for (File file2 : listFiles) {
        String name = file2.getName();
        if (name.endsWith("tl")) {
          long longValue = Long.valueOf(name.substring(0, name.length() - 2)).longValue();
          if (j < longValue) {
            file = file2;
            j = longValue;
          }
        }
      }
      if (file == null) {
        return 0;
      }
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
          XmlUtils.beginDocument(resolvePullParser, "h");
          int attributeInt = resolvePullParser.getAttributeInt((String) null, "lc", 0);
          try {
            fileInputStream.close();
          } catch (IOException unused) {
          }
          return attributeInt;
        } catch (Throwable unused2) {
          fileInputStream.close();
        }
      } catch (FileNotFoundException | IOException unused3) {
      }
    }
    return 0;
  }

  public final ArrayMap createAttributionChains(DiscreteOps discreteOps, Set set) {
    ArrayMap arrayMap;
    List list;
    int i;
    int i2;
    ArrayMap arrayMap2;
    int i3;
    int i4;
    int i5;
    DiscreteOps discreteOps2 = discreteOps;
    ArrayMap arrayMap3 = new ArrayMap();
    int size = discreteOps2.mUids.size();
    int i6 = 0;
    while (i6 < size) {
      ArrayMap arrayMap4 = ((DiscreteUidOps) discreteOps2.mUids.valueAt(i6)).mPackages;
      int intValue = ((Integer) discreteOps2.mUids.keyAt(i6)).intValue();
      int size2 = arrayMap4.size();
      int i7 = 0;
      while (i7 < size2) {
        ArrayMap arrayMap5 = ((DiscretePackageOps) arrayMap4.valueAt(i7)).mPackageOps;
        String str = (String) arrayMap4.keyAt(i7);
        int size3 = arrayMap5.size();
        int i8 = 0;
        while (i8 < size3) {
          ArrayMap arrayMap6 = ((DiscreteOp) arrayMap5.valueAt(i8)).mAttributedOps;
          int intValue2 = ((Integer) arrayMap5.keyAt(i8)).intValue();
          int size4 = arrayMap6.size();
          int i9 = 0;
          while (i9 < size4) {
            List list2 = (List) arrayMap6.valueAt(i9);
            String str2 = (String) arrayMap6.keyAt(i9);
            int size5 = list2.size();
            int i10 = 0;
            while (i10 < size5) {
              int i11 = size;
              DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list2.get(i10);
              int i12 = size5;
              if (discreteOpEvent != null) {
                int i13 = discreteOpEvent.mAttributionChainId;
                arrayMap = arrayMap4;
                if (i13 != -1 && (discreteOpEvent.mAttributionFlags & 8) != 0) {
                  if (arrayMap3.containsKey(Integer.valueOf(i13))) {
                    i5 = size2;
                  } else {
                    i5 = size2;
                    arrayMap3.put(
                        Integer.valueOf(discreteOpEvent.mAttributionChainId),
                        new AttributionChain(set));
                  }
                  list = list2;
                  i = i9;
                  i2 = size4;
                  arrayMap2 = arrayMap6;
                  i3 = i8;
                  i4 = size3;
                  ((AttributionChain)
                          arrayMap3.get(Integer.valueOf(discreteOpEvent.mAttributionChainId)))
                      .addEvent(str, intValue, str2, intValue2, discreteOpEvent);
                  i10++;
                  i9 = i;
                  list2 = list;
                  size5 = i12;
                  size = i11;
                  arrayMap4 = arrayMap;
                  size2 = i5;
                  size4 = i2;
                  arrayMap6 = arrayMap2;
                  i8 = i3;
                  size3 = i4;
                }
              } else {
                arrayMap = arrayMap4;
              }
              list = list2;
              i = i9;
              i2 = size4;
              arrayMap2 = arrayMap6;
              i3 = i8;
              i4 = size3;
              i5 = size2;
              i10++;
              i9 = i;
              list2 = list;
              size5 = i12;
              size = i11;
              arrayMap4 = arrayMap;
              size2 = i5;
              size4 = i2;
              arrayMap6 = arrayMap2;
              i8 = i3;
              size3 = i4;
            }
            i9++;
            size2 = size2;
          }
          i8++;
          size2 = size2;
        }
        i7++;
        size2 = size2;
      }
      i6++;
      discreteOps2 = discreteOps;
    }
    return arrayMap3;
  }

  public final void readDiscreteOpsFromDisk(DiscreteOps discreteOps) {
    synchronized (this.mOnDiskLock) {
      long epochMilli =
          Instant.now()
              .minus(sDiscreteHistoryCutoff, (TemporalUnit) ChronoUnit.MILLIS)
              .toEpochMilli();
      File[] listFiles = this.mDiscreteAccessDir.listFiles();
      if (listFiles != null && listFiles.length > 0) {
        for (File file : listFiles) {
          String name = file.getName();
          if (name.endsWith("tl")
              && Long.valueOf(name.substring(0, name.length() - 2)).longValue() >= epochMilli) {
            discreteOps.readFromFile(file, epochMilli);
          }
        }
      }
    }
  }

  public void clearHistory() {
    synchronized (this.mOnDiskLock) {
      synchronized (this.mInMemoryLock) {
        this.mDiscreteOps = new DiscreteOps(0);
      }
      clearOnDiskHistoryLocked();
    }
  }

  public void clearHistory(int i, String str) {
    DiscreteOps allDiscreteOps;
    synchronized (this.mOnDiskLock) {
      synchronized (this.mInMemoryLock) {
        allDiscreteOps = getAllDiscreteOps();
        clearHistory();
      }
      allDiscreteOps.clearHistory(i, str);
      persistDiscreteOpsLocked(allDiscreteOps);
    }
  }

  public void offsetHistory(long j) {
    DiscreteOps allDiscreteOps;
    synchronized (this.mOnDiskLock) {
      synchronized (this.mInMemoryLock) {
        allDiscreteOps = getAllDiscreteOps();
        clearHistory();
      }
      allDiscreteOps.offsetHistory(j);
      persistDiscreteOpsLocked(allDiscreteOps);
    }
  }

  public void dump(
      PrintWriter printWriter,
      int i,
      String str,
      String str2,
      int i2,
      int i3,
      SimpleDateFormat simpleDateFormat,
      Date date,
      String str3,
      int i4) {
    DiscreteOps allDiscreteOps = getAllDiscreteOps();
    allDiscreteOps.filter(
        0L,
        Instant.now().toEpochMilli(),
        i2,
        i,
        str,
        i3 == -1 ? null : new String[] {AppOpsManager.opToPublicName(i3)},
        str2,
        31,
        new ArrayMap());
    printWriter.print(str3);
    printWriter.print("Largest chain id: ");
    printWriter.print(this.mDiscreteOps.mLargestChainId);
    printWriter.println();
    allDiscreteOps.dump(printWriter, simpleDateFormat, date, str3, i4);
  }

  public final void clearOnDiskHistoryLocked() {
    this.mCachedOps = null;
    FileUtils.deleteContentsAndDir(this.mDiscreteAccessDir);
    createDiscreteAccessDir();
  }

  public final DiscreteOps getAllDiscreteOps() {
    DiscreteOps discreteOps = new DiscreteOps(0);
    synchronized (this.mOnDiskLock) {
      synchronized (this.mInMemoryLock) {
        discreteOps.merge(this.mDiscreteOps);
      }
      if (this.mCachedOps == null) {
        DiscreteOps discreteOps2 = new DiscreteOps(0);
        this.mCachedOps = discreteOps2;
        readDiscreteOpsFromDisk(discreteOps2);
      }
      discreteOps.merge(this.mCachedOps);
    }
    return discreteOps;
  }

  public final class AttributionChain {
    public Set mExemptPkgs;
    public ArrayList mChain = new ArrayList();
    public OpEvent mStartEvent = null;
    public OpEvent mLastVisibleEvent = null;

    public final class OpEvent {
      public String mAttributionTag;
      public int mOpCode;
      public DiscreteOpEvent mOpEvent;
      public String mPkgName;
      public int mUid;

      public OpEvent(String str, int i, String str2, int i2, DiscreteOpEvent discreteOpEvent) {
        this.mPkgName = str;
        this.mUid = i;
        this.mAttributionTag = str2;
        this.mOpCode = i2;
        this.mOpEvent = discreteOpEvent;
      }

      public boolean matches(
          String str, int i, String str2, int i2, DiscreteOpEvent discreteOpEvent) {
        if (Objects.equals(str, this.mPkgName)
            && this.mUid == i
            && Objects.equals(str2, this.mAttributionTag)
            && this.mOpCode == i2) {
          DiscreteOpEvent discreteOpEvent2 = this.mOpEvent;
          if (discreteOpEvent2.mAttributionChainId == discreteOpEvent.mAttributionChainId
              && discreteOpEvent2.mAttributionFlags == discreteOpEvent.mAttributionFlags
              && discreteOpEvent2.mNoteTime == discreteOpEvent.mNoteTime) {
            return true;
          }
        }
        return false;
      }

      public boolean packageOpEquals(OpEvent opEvent) {
        return Objects.equals(opEvent.mPkgName, this.mPkgName)
            && opEvent.mUid == this.mUid
            && Objects.equals(opEvent.mAttributionTag, this.mAttributionTag)
            && this.mOpCode == opEvent.mOpCode;
      }

      public boolean equalsExceptDuration(OpEvent opEvent) {
        return opEvent.mOpEvent.mNoteDuration != this.mOpEvent.mNoteDuration
            && packageOpEquals(opEvent)
            && this.mOpEvent.equalsExceptDuration(opEvent.mOpEvent);
      }
    }

    public AttributionChain(Set set) {
      this.mExemptPkgs = set;
    }

    public boolean isComplete() {
      if (!this.mChain.isEmpty() && getStart() != null) {
        ArrayList arrayList = this.mChain;
        if (isEnd((OpEvent) arrayList.get(arrayList.size() - 1))) {
          return true;
        }
      }
      return false;
    }

    public boolean isStart(
        String str, int i, String str2, int i2, DiscreteOpEvent discreteOpEvent) {
      OpEvent opEvent = this.mStartEvent;
      if (opEvent == null || discreteOpEvent == null) {
        return false;
      }
      return opEvent.matches(str, i, str2, i2, discreteOpEvent);
    }

    public final OpEvent getStart() {
      if (this.mChain.isEmpty() || !isStart((OpEvent) this.mChain.get(0))) {
        return null;
      }
      return (OpEvent) this.mChain.get(0);
    }

    public final OpEvent getLastVisible() {
      for (int size = this.mChain.size() - 1; size > 0; size--) {
        OpEvent opEvent = (OpEvent) this.mChain.get(size);
        if (!this.mExemptPkgs.contains(opEvent.mPkgName)) {
          return opEvent;
        }
      }
      return null;
    }

    public void addEvent(String str, int i, String str2, int i2, DiscreteOpEvent discreteOpEvent) {
      OpEvent opEvent = new OpEvent(str, i, str2, i2, discreteOpEvent);
      int i3 = 0;
      for (int i4 = 0; i4 < this.mChain.size(); i4++) {
        OpEvent opEvent2 = (OpEvent) this.mChain.get(i4);
        if (opEvent2.equalsExceptDuration(opEvent)) {
          DiscreteOpEvent discreteOpEvent2 = opEvent.mOpEvent;
          if (discreteOpEvent2.mNoteDuration != -1) {
            opEvent2.mOpEvent = discreteOpEvent2;
            return;
          }
          return;
        }
      }
      if (this.mChain.isEmpty() || isEnd(opEvent)) {
        this.mChain.add(opEvent);
      } else if (isStart(opEvent)) {
        this.mChain.add(0, opEvent);
      } else {
        while (true) {
          if (i3 >= this.mChain.size()) {
            break;
          }
          OpEvent opEvent3 = (OpEvent) this.mChain.get(i3);
          if ((isStart(opEvent3) || opEvent3.mOpEvent.mNoteTime <= opEvent.mOpEvent.mNoteTime)
              && (i3 != this.mChain.size() - 1 || !isEnd(opEvent3))) {
            if (i3 == this.mChain.size() - 1) {
              this.mChain.add(opEvent);
              break;
            }
            i3++;
          }
        }
        this.mChain.add(i3, opEvent);
      }
      this.mStartEvent = isComplete() ? getStart() : null;
      this.mLastVisibleEvent = isComplete() ? getLastVisible() : null;
    }

    public final boolean isEnd(OpEvent opEvent) {
      return (opEvent == null || (opEvent.mOpEvent.mAttributionFlags & 1) == 0) ? false : true;
    }

    public final boolean isStart(OpEvent opEvent) {
      return (opEvent == null || (opEvent.mOpEvent.mAttributionFlags & 4) == 0) ? false : true;
    }
  }

  public final class DiscreteOps {
    public int mChainIdOffset;
    public int mLargestChainId;
    public ArrayMap mUids = new ArrayMap();

    public DiscreteOps(int i) {
      this.mChainIdOffset = i;
      this.mLargestChainId = i;
    }

    public boolean isEmpty() {
      return this.mUids.isEmpty();
    }

    public void merge(DiscreteOps discreteOps) {
      this.mLargestChainId = Math.max(this.mLargestChainId, discreteOps.mLargestChainId);
      int size = discreteOps.mUids.size();
      for (int i = 0; i < size; i++) {
        int intValue = ((Integer) discreteOps.mUids.keyAt(i)).intValue();
        getOrCreateDiscreteUidOps(intValue).merge((DiscreteUidOps) discreteOps.mUids.valueAt(i));
      }
    }

    public void addDiscreteAccess(
        int i, int i2, String str, String str2, int i3, int i4, long j, long j2, int i5, int i6) {
      int i7;
      int i8;
      if (i6 != -1) {
        int i9 = this.mChainIdOffset + i6;
        if (i9 > this.mLargestChainId) {
          this.mLargestChainId = i9;
        } else if (i9 < 0) {
          i9 = 0;
          this.mLargestChainId = 0;
          this.mChainIdOffset = i6 * (-1);
        }
        i8 = i2;
        i7 = i9;
      } else {
        i7 = i6;
        i8 = i2;
      }
      getOrCreateDiscreteUidOps(i8).addDiscreteAccess(i, str, str2, i3, i4, j, j2, i5, i7);
    }

    public final void filter(
        long j,
        long j2,
        int i,
        int i2,
        String str,
        String[] strArr,
        String str2,
        int i3,
        ArrayMap arrayMap) {
      if ((i & 1) != 0) {
        ArrayMap arrayMap2 = new ArrayMap();
        arrayMap2.put(Integer.valueOf(i2), getOrCreateDiscreteUidOps(i2));
        this.mUids = arrayMap2;
      }
      for (int size = this.mUids.size() - 1; size >= 0; size--) {
        ((DiscreteUidOps) this.mUids.valueAt(size))
            .filter(
                j,
                j2,
                i,
                str,
                strArr,
                str2,
                i3,
                ((Integer) this.mUids.keyAt(size)).intValue(),
                arrayMap);
        if (((DiscreteUidOps) this.mUids.valueAt(size)).isEmpty()) {
          this.mUids.removeAt(size);
        }
      }
    }

    public final void offsetHistory(long j) {
      int size = this.mUids.size();
      for (int i = 0; i < size; i++) {
        ((DiscreteUidOps) this.mUids.valueAt(i)).offsetHistory(j);
      }
    }

    public final void clearHistory(int i, String str) {
      if (this.mUids.containsKey(Integer.valueOf(i))) {
        ((DiscreteUidOps) this.mUids.get(Integer.valueOf(i))).clearPackage(str);
        if (((DiscreteUidOps) this.mUids.get(Integer.valueOf(i))).isEmpty()) {
          this.mUids.remove(Integer.valueOf(i));
        }
      }
    }

    public final void applyToHistoricalOps(
        AppOpsManager.HistoricalOps historicalOps, ArrayMap arrayMap) {
      int size = this.mUids.size();
      for (int i = 0; i < size; i++) {
        ((DiscreteUidOps) this.mUids.valueAt(i))
            .applyToHistory(historicalOps, ((Integer) this.mUids.keyAt(i)).intValue(), arrayMap);
      }
    }

    public final void writeToStream(FileOutputStream fileOutputStream) {
      TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
      resolveSerializer.startDocument((String) null, Boolean.TRUE);
      resolveSerializer.startTag((String) null, "h");
      resolveSerializer.attributeInt((String) null, "v", 1);
      resolveSerializer.attributeInt((String) null, "lc", this.mLargestChainId);
      int size = this.mUids.size();
      for (int i = 0; i < size; i++) {
        resolveSerializer.startTag((String) null, "u");
        resolveSerializer.attributeInt(
            (String) null, "ui", ((Integer) this.mUids.keyAt(i)).intValue());
        ((DiscreteUidOps) this.mUids.valueAt(i)).serialize(resolveSerializer);
        resolveSerializer.endTag((String) null, "u");
      }
      resolveSerializer.endTag((String) null, "h");
      resolveSerializer.endDocument();
    }

    public final void dump(
        PrintWriter printWriter, SimpleDateFormat simpleDateFormat, Date date, String str, int i) {
      int size = this.mUids.size();
      for (int i2 = 0; i2 < size; i2++) {
        printWriter.print(str);
        printWriter.print("Uid: ");
        printWriter.print(this.mUids.keyAt(i2));
        printWriter.println();
        ((DiscreteUidOps) this.mUids.valueAt(i2))
            .dump(printWriter, simpleDateFormat, date, str + "  ", i);
      }
    }

    public final DiscreteUidOps getOrCreateDiscreteUidOps(int i) {
      DiscreteUidOps discreteUidOps = (DiscreteUidOps) this.mUids.get(Integer.valueOf(i));
      if (discreteUidOps != null) {
        return discreteUidOps;
      }
      DiscreteUidOps discreteUidOps2 = DiscreteRegistry.this.new DiscreteUidOps();
      this.mUids.put(Integer.valueOf(i), discreteUidOps2);
      return discreteUidOps2;
    }

    public final void readFromFile(File file, long j) {
      TypedXmlPullParser resolvePullParser;
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          resolvePullParser = Xml.resolvePullParser(fileInputStream);
          XmlUtils.beginDocument(resolvePullParser, "h");
        } finally {
          try {
            try {
            } catch (IOException unused) {
              return;
            }
          } finally {
            try {
              fileInputStream.close();
            } catch (IOException unused2) {
            }
          }
        }
        if (resolvePullParser.getAttributeInt((String) null, "v") != 1) {
          throw new IllegalStateException("Dropping unsupported discrete history " + file);
        }
        int depth = resolvePullParser.getDepth();
        while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
          if ("u".equals(resolvePullParser.getName())) {
            getOrCreateDiscreteUidOps(resolvePullParser.getAttributeInt((String) null, "ui", -1))
                .deserialize(resolvePullParser, j);
          }
        }
      } catch (FileNotFoundException unused3) {
      }
    }
  }

  public final void createDiscreteAccessDir() {
    if (this.mDiscreteAccessDir.exists()) {
      return;
    }
    if (!this.mDiscreteAccessDir.mkdirs()) {
      Slog.e(TAG, "Failed to create DiscreteRegistry directory");
    }
    FileUtils.setPermissions(this.mDiscreteAccessDir.getPath(), 505, -1, -1);
  }

  public final void persistDiscreteOpsLocked(DiscreteOps discreteOps) {
    FileOutputStream fileOutputStream;
    long epochMilli = Instant.now().toEpochMilli();
    AtomicFile atomicFile = new AtomicFile(new File(this.mDiscreteAccessDir, epochMilli + "tl"));
    try {
      fileOutputStream = atomicFile.startWrite();
      try {
        discreteOps.writeToStream(fileOutputStream);
        atomicFile.finishWrite(fileOutputStream);
      } catch (Throwable th) {
        th = th;
        Slog.e(
            TAG,
            "Error writing timeline state: "
                + th.getMessage()
                + " "
                + Arrays.toString(th.getStackTrace()));
        if (fileOutputStream != null) {
          atomicFile.failWrite(fileOutputStream);
        }
      }
    } catch (Throwable th2) {
      th = th2;
      fileOutputStream = null;
    }
  }

  public final void deleteOldDiscreteHistoryFilesLocked() {
    File[] listFiles = this.mDiscreteAccessDir.listFiles();
    if (listFiles == null || listFiles.length <= 0) {
      return;
    }
    for (File file : listFiles) {
      String name = file.getName();
      if (name.endsWith("tl")) {
        try {
          if (Instant.now()
                  .minus(sDiscreteHistoryCutoff, (TemporalUnit) ChronoUnit.MILLIS)
                  .toEpochMilli()
              > Long.valueOf(name.substring(0, name.length() - 2)).longValue()) {
            file.delete();
            Slog.e(TAG, "Deleting file " + name);
          }
        } catch (Throwable th) {
          Slog.e(TAG, "Error while cleaning timeline files: ", th);
        }
      }
    }
  }

  public final void createDiscreteAccessDirLocked() {
    if (this.mDiscreteAccessDir.exists()) {
      return;
    }
    if (!this.mDiscreteAccessDir.mkdirs()) {
      Slog.e(TAG, "Failed to create DiscreteRegistry directory");
    }
    FileUtils.setPermissions(this.mDiscreteAccessDir.getPath(), 505, -1, -1);
  }

  public final class DiscreteUidOps {
    public ArrayMap mPackages = new ArrayMap();

    public DiscreteUidOps() {}

    public boolean isEmpty() {
      return this.mPackages.isEmpty();
    }

    public void merge(DiscreteUidOps discreteUidOps) {
      int size = discreteUidOps.mPackages.size();
      for (int i = 0; i < size; i++) {
        String str = (String) discreteUidOps.mPackages.keyAt(i);
        getOrCreateDiscretePackageOps(str)
            .merge((DiscretePackageOps) discreteUidOps.mPackages.valueAt(i));
      }
    }

    public final void filter(
        long j,
        long j2,
        int i,
        String str,
        String[] strArr,
        String str2,
        int i2,
        int i3,
        ArrayMap arrayMap) {
      if ((i & 2) != 0) {
        ArrayMap arrayMap2 = new ArrayMap();
        arrayMap2.put(str, getOrCreateDiscretePackageOps(str));
        this.mPackages = arrayMap2;
      }
      for (int size = this.mPackages.size() - 1; size >= 0; size--) {
        ((DiscretePackageOps) this.mPackages.valueAt(size))
            .filter(j, j2, i, strArr, str2, i2, i3, (String) this.mPackages.keyAt(size), arrayMap);
        if (((DiscretePackageOps) this.mPackages.valueAt(size)).isEmpty()) {
          this.mPackages.removeAt(size);
        }
      }
    }

    public final void offsetHistory(long j) {
      int size = this.mPackages.size();
      for (int i = 0; i < size; i++) {
        ((DiscretePackageOps) this.mPackages.valueAt(i)).offsetHistory(j);
      }
    }

    public final void clearPackage(String str) {
      this.mPackages.remove(str);
    }

    public void addDiscreteAccess(
        int i, String str, String str2, int i2, int i3, long j, long j2, int i4, int i5) {
      getOrCreateDiscretePackageOps(str).addDiscreteAccess(i, str2, i2, i3, j, j2, i4, i5);
    }

    public final DiscretePackageOps getOrCreateDiscretePackageOps(String str) {
      DiscretePackageOps discretePackageOps = (DiscretePackageOps) this.mPackages.get(str);
      if (discretePackageOps != null) {
        return discretePackageOps;
      }
      DiscretePackageOps discretePackageOps2 = DiscreteRegistry.this.new DiscretePackageOps();
      this.mPackages.put(str, discretePackageOps2);
      return discretePackageOps2;
    }

    public final void applyToHistory(
        AppOpsManager.HistoricalOps historicalOps, int i, ArrayMap arrayMap) {
      int size = this.mPackages.size();
      for (int i2 = 0; i2 < size; i2++) {
        ((DiscretePackageOps) this.mPackages.valueAt(i2))
            .applyToHistory(historicalOps, i, (String) this.mPackages.keyAt(i2), arrayMap);
      }
    }

    public void serialize(TypedXmlSerializer typedXmlSerializer) {
      int size = this.mPackages.size();
      for (int i = 0; i < size; i++) {
        typedXmlSerializer.startTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
        typedXmlSerializer.attribute((String) null, "pn", (String) this.mPackages.keyAt(i));
        ((DiscretePackageOps) this.mPackages.valueAt(i)).serialize(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, KnoxAnalyticsDataConverter.PAYLOAD);
      }
    }

    public final void dump(
        PrintWriter printWriter, SimpleDateFormat simpleDateFormat, Date date, String str, int i) {
      int size = this.mPackages.size();
      for (int i2 = 0; i2 < size; i2++) {
        printWriter.print(str);
        printWriter.print("Package: ");
        printWriter.print((String) this.mPackages.keyAt(i2));
        printWriter.println();
        ((DiscretePackageOps) this.mPackages.valueAt(i2))
            .dump(printWriter, simpleDateFormat, date, str + "  ", i);
      }
    }

    public void deserialize(TypedXmlPullParser typedXmlPullParser, long j) {
      int depth = typedXmlPullParser.getDepth();
      while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
        if (KnoxAnalyticsDataConverter.PAYLOAD.equals(typedXmlPullParser.getName())) {
          getOrCreateDiscretePackageOps(typedXmlPullParser.getAttributeValue((String) null, "pn"))
              .deserialize(typedXmlPullParser, j);
        }
      }
    }
  }

  public final class DiscretePackageOps {
    public ArrayMap mPackageOps = new ArrayMap();

    public DiscretePackageOps() {}

    public boolean isEmpty() {
      return this.mPackageOps.isEmpty();
    }

    public void addDiscreteAccess(
        int i, String str, int i2, int i3, long j, long j2, int i4, int i5) {
      getOrCreateDiscreteOp(i).addDiscreteAccess(str, i2, i3, j, j2, i4, i5);
    }

    public void merge(DiscretePackageOps discretePackageOps) {
      int size = discretePackageOps.mPackageOps.size();
      for (int i = 0; i < size; i++) {
        int intValue = ((Integer) discretePackageOps.mPackageOps.keyAt(i)).intValue();
        getOrCreateDiscreteOp(intValue)
            .merge((DiscreteOp) discretePackageOps.mPackageOps.valueAt(i));
      }
    }

    public final void filter(
        long j,
        long j2,
        int i,
        String[] strArr,
        String str,
        int i2,
        int i3,
        String str2,
        ArrayMap arrayMap) {
      for (int size = this.mPackageOps.size() - 1; size >= 0; size--) {
        int intValue = ((Integer) this.mPackageOps.keyAt(size)).intValue();
        if ((i & 8) != 0 && !ArrayUtils.contains(strArr, AppOpsManager.opToPublicName(intValue))) {
          this.mPackageOps.removeAt(size);
        }
        ((DiscreteOp) this.mPackageOps.valueAt(size))
            .filter(
                j,
                j2,
                i,
                str,
                i2,
                i3,
                str2,
                ((Integer) this.mPackageOps.keyAt(size)).intValue(),
                arrayMap);
        if (((DiscreteOp) this.mPackageOps.valueAt(size)).isEmpty()) {
          this.mPackageOps.removeAt(size);
        }
      }
    }

    public final void offsetHistory(long j) {
      int size = this.mPackageOps.size();
      for (int i = 0; i < size; i++) {
        ((DiscreteOp) this.mPackageOps.valueAt(i)).offsetHistory(j);
      }
    }

    public final DiscreteOp getOrCreateDiscreteOp(int i) {
      DiscreteOp discreteOp = (DiscreteOp) this.mPackageOps.get(Integer.valueOf(i));
      if (discreteOp != null) {
        return discreteOp;
      }
      DiscreteOp discreteOp2 = DiscreteRegistry.this.new DiscreteOp();
      this.mPackageOps.put(Integer.valueOf(i), discreteOp2);
      return discreteOp2;
    }

    public final void applyToHistory(
        AppOpsManager.HistoricalOps historicalOps, int i, String str, ArrayMap arrayMap) {
      int size = this.mPackageOps.size();
      for (int i2 = 0; i2 < size; i2++) {
        ((DiscreteOp) this.mPackageOps.valueAt(i2))
            .applyToHistory(
                historicalOps, i, str, ((Integer) this.mPackageOps.keyAt(i2)).intValue(), arrayMap);
      }
    }

    public void serialize(TypedXmlSerializer typedXmlSerializer) {
      int size = this.mPackageOps.size();
      for (int i = 0; i < size; i++) {
        typedXmlSerializer.startTag((String) null, "o");
        typedXmlSerializer.attributeInt(
            (String) null, "op", ((Integer) this.mPackageOps.keyAt(i)).intValue());
        ((DiscreteOp) this.mPackageOps.valueAt(i)).serialize(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, "o");
      }
    }

    public final void dump(
        PrintWriter printWriter, SimpleDateFormat simpleDateFormat, Date date, String str, int i) {
      int size = this.mPackageOps.size();
      for (int i2 = 0; i2 < size; i2++) {
        printWriter.print(str);
        printWriter.print(
            AppOpsManager.opToName(((Integer) this.mPackageOps.keyAt(i2)).intValue()));
        printWriter.println();
        ((DiscreteOp) this.mPackageOps.valueAt(i2))
            .dump(printWriter, simpleDateFormat, date, str + "  ", i);
      }
    }

    public void deserialize(TypedXmlPullParser typedXmlPullParser, long j) {
      int depth = typedXmlPullParser.getDepth();
      while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
        if ("o".equals(typedXmlPullParser.getName())) {
          getOrCreateDiscreteOp(typedXmlPullParser.getAttributeInt((String) null, "op"))
              .deserialize(typedXmlPullParser, j);
        }
      }
    }
  }

  public final class DiscreteOp {
    public ArrayMap mAttributedOps = new ArrayMap();

    public DiscreteOp() {}

    public boolean isEmpty() {
      return this.mAttributedOps.isEmpty();
    }

    public void merge(DiscreteOp discreteOp) {
      int size = discreteOp.mAttributedOps.size();
      for (int i = 0; i < size; i++) {
        String str = (String) discreteOp.mAttributedOps.keyAt(i);
        List list = (List) discreteOp.mAttributedOps.valueAt(i);
        this.mAttributedOps.put(
            str, DiscreteRegistry.stableListMerge(getOrCreateDiscreteOpEventsList(str), list));
      }
    }

    public final void filter(
        long j,
        long j2,
        int i,
        String str,
        int i2,
        int i3,
        String str2,
        int i4,
        ArrayMap arrayMap) {
      if ((i & 4) != 0) {
        ArrayMap arrayMap2 = new ArrayMap();
        arrayMap2.put(str, getOrCreateDiscreteOpEventsList(str));
        this.mAttributedOps = arrayMap2;
      }
      for (int size = this.mAttributedOps.size() - 1; size >= 0; size--) {
        String str3 = (String) this.mAttributedOps.keyAt(size);
        List filterEventsList =
            DiscreteRegistry.filterEventsList(
                (List) this.mAttributedOps.valueAt(size),
                j,
                j2,
                i2,
                i3,
                str2,
                i4,
                (String) this.mAttributedOps.keyAt(size),
                arrayMap);
        this.mAttributedOps.put(str3, filterEventsList);
        if (filterEventsList.size() == 0) {
          this.mAttributedOps.removeAt(size);
        }
      }
    }

    public final void offsetHistory(long j) {
      DiscreteOp discreteOp = this;
      int size = discreteOp.mAttributedOps.size();
      int i = 0;
      while (i < size) {
        List list = (List) discreteOp.mAttributedOps.valueAt(i);
        int size2 = list.size();
        int i2 = 0;
        while (i2 < size2) {
          DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list.get(i2);
          list.set(
              i2,
              DiscreteRegistry.this
              .new DiscreteOpEvent(
                  discreteOpEvent.mNoteTime - j,
                  discreteOpEvent.mNoteDuration,
                  discreteOpEvent.mUidState,
                  discreteOpEvent.mOpFlag,
                  discreteOpEvent.mAttributionFlags,
                  discreteOpEvent.mAttributionChainId));
          i2++;
          discreteOp = this;
        }
        i++;
        discreteOp = this;
      }
    }

    public void addDiscreteAccess(String str, int i, int i2, long j, long j2, int i3, int i4) {
      List orCreateDiscreteOpEventsList = getOrCreateDiscreteOpEventsList(str);
      for (int size = orCreateDiscreteOpEventsList.size(); size > 0; size--) {
        DiscreteOpEvent discreteOpEvent =
            (DiscreteOpEvent) orCreateDiscreteOpEventsList.get(size - 1);
        if (DiscreteRegistry.discretizeTimeStamp(discreteOpEvent.mNoteTime)
            < DiscreteRegistry.discretizeTimeStamp(j)) {
          break;
        }
        if (discreteOpEvent.mOpFlag == i && discreteOpEvent.mUidState == i2) {
          if (discreteOpEvent.mAttributionFlags == i3) {
            if (discreteOpEvent.mAttributionChainId == i4) {
              if (DiscreteRegistry.discretizeDuration(j2)
                  == DiscreteRegistry.discretizeDuration(discreteOpEvent.mNoteDuration)) {
                return;
              }
              orCreateDiscreteOpEventsList.add(
                  size, DiscreteRegistry.this.new DiscreteOpEvent(j, j2, i2, i, i3, i4));
            }
          }
        }
      }
      orCreateDiscreteOpEventsList.add(
          size, DiscreteRegistry.this.new DiscreteOpEvent(j, j2, i2, i, i3, i4));
    }

    public final List getOrCreateDiscreteOpEventsList(String str) {
      List list = (List) this.mAttributedOps.get(str);
      if (list != null) {
        return list;
      }
      ArrayList arrayList = new ArrayList();
      this.mAttributedOps.put(str, arrayList);
      return arrayList;
    }

    public final void applyToHistory(
        AppOpsManager.HistoricalOps historicalOps, int i, String str, int i2, ArrayMap arrayMap) {
      AttributionChain attributionChain;
      AttributionChain.OpEvent opEvent;
      int size = this.mAttributedOps.size();
      for (int i3 = 0; i3 < size; i3++) {
        String str2 = (String) this.mAttributedOps.keyAt(i3);
        List list = (List) this.mAttributedOps.valueAt(i3);
        int size2 = list.size();
        int i4 = 0;
        while (i4 < size2) {
          DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list.get(i4);
          int i5 = discreteOpEvent.mAttributionChainId;
          historicalOps.addDiscreteAccess(
              i2,
              i,
              str,
              str2,
              discreteOpEvent.mUidState,
              discreteOpEvent.mOpFlag,
              DiscreteRegistry.discretizeTimeStamp(discreteOpEvent.mNoteTime),
              DiscreteRegistry.discretizeDuration(discreteOpEvent.mNoteDuration),
              (i5 == -1
                      || arrayMap == null
                      || (attributionChain = (AttributionChain) arrayMap.get(Integer.valueOf(i5)))
                          == null
                      || !attributionChain.isComplete()
                      || !attributionChain.isStart(str, i, str2, i2, discreteOpEvent)
                      || (opEvent = attributionChain.mLastVisibleEvent) == null)
                  ? null
                  : new AppOpsManager.OpEventProxyInfo(
                      opEvent.mUid, opEvent.mPkgName, opEvent.mAttributionTag));
          i4++;
          size2 = size2;
          list = list;
        }
      }
    }

    public final void dump(
        PrintWriter printWriter, SimpleDateFormat simpleDateFormat, Date date, String str, int i) {
      int size = this.mAttributedOps.size();
      for (int i2 = 0; i2 < size; i2++) {
        printWriter.print(str);
        printWriter.print("Attribution: ");
        printWriter.print((String) this.mAttributedOps.keyAt(i2));
        printWriter.println();
        List list = (List) this.mAttributedOps.valueAt(i2);
        int size2 = list.size();
        for (int max = i < 1 ? 0 : Math.max(0, size2 - i); max < size2; max++) {
          ((DiscreteOpEvent) list.get(max)).dump(printWriter, simpleDateFormat, date, str + "  ");
        }
      }
    }

    public void serialize(TypedXmlSerializer typedXmlSerializer) {
      int size = this.mAttributedOps.size();
      for (int i = 0; i < size; i++) {
        typedXmlSerializer.startTag((String) null, "a");
        if (((String) this.mAttributedOps.keyAt(i)) != null) {
          typedXmlSerializer.attribute((String) null, "at", (String) this.mAttributedOps.keyAt(i));
        }
        List list = (List) this.mAttributedOps.valueAt(i);
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
          typedXmlSerializer.startTag((String) null, KnoxAnalyticsDataConverter.EVENT);
          ((DiscreteOpEvent) list.get(i2)).serialize(typedXmlSerializer);
          typedXmlSerializer.endTag((String) null, KnoxAnalyticsDataConverter.EVENT);
        }
        typedXmlSerializer.endTag((String) null, "a");
      }
    }

    public void deserialize(TypedXmlPullParser typedXmlPullParser, long j) {
      int depth = typedXmlPullParser.getDepth();
      while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
        if ("a".equals(typedXmlPullParser.getName())) {
          List orCreateDiscreteOpEventsList =
              getOrCreateDiscreteOpEventsList(
                  typedXmlPullParser.getAttributeValue((String) null, "at"));
          int depth2 = typedXmlPullParser.getDepth();
          while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
            if (KnoxAnalyticsDataConverter.EVENT.equals(typedXmlPullParser.getName())) {
              long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "nt");
              long attributeLong2 = typedXmlPullParser.getAttributeLong((String) null, "nd", -1L);
              int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "us");
              int attributeInt2 =
                  typedXmlPullParser.getAttributeInt(
                      (String) null, KnoxAnalyticsDataConverter.FEATURE);
              int attributeInt3 = typedXmlPullParser.getAttributeInt((String) null, "af", 0);
              int attributeInt4 = typedXmlPullParser.getAttributeInt((String) null, "ci", -1);
              if (attributeLong + attributeLong2 >= j) {
                orCreateDiscreteOpEventsList.add(
                    DiscreteRegistry.this
                    .new DiscreteOpEvent(
                        attributeLong,
                        attributeLong2,
                        attributeInt,
                        attributeInt2,
                        attributeInt3,
                        attributeInt4));
              }
            }
          }
          Collections.sort(
              orCreateDiscreteOpEventsList,
              new Comparator() { // from class:
                                 // com.android.server.appop.DiscreteRegistry$DiscreteOp$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                  int lambda$deserialize$0;
                  lambda$deserialize$0 =
                      DiscreteRegistry.DiscreteOp.lambda$deserialize$0(
                          (DiscreteRegistry.DiscreteOpEvent) obj,
                          (DiscreteRegistry.DiscreteOpEvent) obj2);
                  return lambda$deserialize$0;
                }
              });
        }
      }
    }

    public static /* synthetic */ int lambda$deserialize$0(
        DiscreteOpEvent discreteOpEvent, DiscreteOpEvent discreteOpEvent2) {
      long j = discreteOpEvent.mNoteTime;
      long j2 = discreteOpEvent2.mNoteTime;
      if (j < j2) {
        return -1;
      }
      return j == j2 ? 0 : 1;
    }
  }

  public final class DiscreteOpEvent {
    public final int mAttributionChainId;
    public final int mAttributionFlags;
    public final long mNoteDuration;
    public final long mNoteTime;
    public final int mOpFlag;
    public final int mUidState;

    public DiscreteOpEvent(long j, long j2, int i, int i2, int i3, int i4) {
      this.mNoteTime = j;
      this.mNoteDuration = j2;
      this.mUidState = i;
      this.mOpFlag = i2;
      this.mAttributionFlags = i3;
      this.mAttributionChainId = i4;
    }

    public boolean equalsExceptDuration(DiscreteOpEvent discreteOpEvent) {
      return this.mNoteTime == discreteOpEvent.mNoteTime
          && this.mUidState == discreteOpEvent.mUidState
          && this.mOpFlag == discreteOpEvent.mOpFlag
          && this.mAttributionFlags == discreteOpEvent.mAttributionFlags
          && this.mAttributionChainId == discreteOpEvent.mAttributionChainId;
    }

    public final void dump(
        PrintWriter printWriter, SimpleDateFormat simpleDateFormat, Date date, String str) {
      printWriter.print(str);
      printWriter.print("Access [");
      printWriter.print(AppOpsManager.getUidStateName(this.mUidState));
      printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
      printWriter.print(AppOpsManager.flagsToString(this.mOpFlag));
      printWriter.print("] at ");
      date.setTime(DiscreteRegistry.discretizeTimeStamp(this.mNoteTime));
      printWriter.print(simpleDateFormat.format(date));
      if (this.mNoteDuration != -1) {
        printWriter.print(" for ");
        printWriter.print(DiscreteRegistry.discretizeDuration(this.mNoteDuration));
        printWriter.print(" milliseconds ");
      }
      if (this.mAttributionFlags != 0) {
        printWriter.print(" attribution flags=");
        printWriter.print(this.mAttributionFlags);
        printWriter.print(" with chainId=");
        printWriter.print(this.mAttributionChainId);
      }
      printWriter.println();
    }

    public final void serialize(TypedXmlSerializer typedXmlSerializer) {
      typedXmlSerializer.attributeLong((String) null, "nt", this.mNoteTime);
      long j = this.mNoteDuration;
      if (j != -1) {
        typedXmlSerializer.attributeLong((String) null, "nd", j);
      }
      int i = this.mAttributionFlags;
      if (i != 0) {
        typedXmlSerializer.attributeInt((String) null, "af", i);
      }
      int i2 = this.mAttributionChainId;
      if (i2 != -1) {
        typedXmlSerializer.attributeInt((String) null, "ci", i2);
      }
      typedXmlSerializer.attributeInt((String) null, "us", this.mUidState);
      typedXmlSerializer.attributeInt(
          (String) null, KnoxAnalyticsDataConverter.FEATURE, this.mOpFlag);
    }
  }

  public static int[] parseOpsList(String str) {
    String[] split = str.isEmpty() ? new String[0] : str.split(",");
    int length = split.length;
    int[] iArr = new int[length];
    for (int i = 0; i < length; i++) {
      try {
        iArr[i] = Integer.parseInt(split[i]);
      } catch (NumberFormatException e) {
        Slog.e(TAG, "Failed to parse Discrete ops list: " + e.getMessage());
        return parseOpsList("1,0,26,27,100,101,120");
      }
    }
    return iArr;
  }

  public static List stableListMerge(List list, List list2) {
    int i;
    int i2;
    int size = list.size();
    int size2 = list2.size();
    ArrayList arrayList = new ArrayList(size + size2);
    int i3 = 0;
    int i4 = 0;
    while (true) {
      if (i3 >= size && i4 >= size2) {
        return arrayList;
      }
      if (i3 == size) {
        i = i4 + 1;
        arrayList.add((DiscreteOpEvent) list2.get(i4));
      } else {
        if (i4 == size2) {
          i2 = i3 + 1;
          arrayList.add((DiscreteOpEvent) list.get(i3));
        } else if (((DiscreteOpEvent) list.get(i3)).mNoteTime
            < ((DiscreteOpEvent) list2.get(i4)).mNoteTime) {
          i2 = i3 + 1;
          arrayList.add((DiscreteOpEvent) list.get(i3));
        } else {
          i = i4 + 1;
          arrayList.add((DiscreteOpEvent) list2.get(i4));
        }
        i3 = i2;
      }
      i4 = i;
    }
  }

  public static List filterEventsList(
      List list,
      long j,
      long j2,
      int i,
      int i2,
      String str,
      int i3,
      String str2,
      ArrayMap arrayMap) {
    int size = list.size();
    ArrayList arrayList = new ArrayList(size);
    for (int i4 = 0; i4 < size; i4++) {
      DiscreteOpEvent discreteOpEvent = (DiscreteOpEvent) list.get(i4);
      AttributionChain attributionChain =
          (AttributionChain) arrayMap.get(Integer.valueOf(discreteOpEvent.mAttributionChainId));
      if ((attributionChain == null
              || attributionChain.isStart(str, i2, str2, i3, discreteOpEvent)
              || !attributionChain.isComplete()
              || discreteOpEvent.mAttributionChainId == -1)
          && (discreteOpEvent.mOpFlag & i) != 0) {
        long j3 = discreteOpEvent.mNoteTime;
        if (discreteOpEvent.mNoteDuration + j3 > j && j3 < j2) {
          arrayList.add(discreteOpEvent);
        }
      }
    }
    return arrayList;
  }

  public static boolean isDiscreteOp(int i, int i2) {
    return ArrayUtils.contains(sDiscreteOps, i) && (sDiscreteFlags & i2) != 0;
  }

  public static long discretizeTimeStamp(long j) {
    long j2 = sDiscreteHistoryQuantization;
    return (j / j2) * j2;
  }

  public static long discretizeDuration(long j) {
    if (j == -1) {
      return -1L;
    }
    long j2 = sDiscreteHistoryQuantization;
    return j2 * (((j + j2) - 1) / j2);
  }

  public void setDebugMode(boolean z) {
    this.mDebugMode = z;
  }
}
