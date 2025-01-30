package com.android.server.pm.dex;

import android.util.AtomicFile;
import android.util.Slog;
import com.android.internal.util.FastPrintWriter;
import com.android.server.pm.AbstractStatsBase;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class PackageDynamicCodeLoading extends AbstractStatsBase {
  static final int MAX_FILES_PER_OWNER = 100;
  public static final Pattern PACKAGE_LINE_PATTERN =
      Pattern.compile("([A-Z]):([0-9]+):([^:]*):(.*)");
  public final Object mLock;
  public Map mPackageMap;

  public static boolean isValidFileType(int i) {
    return i == 68 || i == 78;
  }

  public PackageDynamicCodeLoading() {
    super("package-dcl.list", "PackageDynamicCodeLoading_DiskWriter", false);
    this.mLock = new Object();
    this.mPackageMap = new HashMap();
  }

  public boolean record(String str, String str2, int i, int i2, String str3) {
    boolean add;
    if (!isValidFileType(i)) {
      throw new IllegalArgumentException("Bad file type: " + i);
    }
    synchronized (this.mLock) {
      PackageDynamicCode packageDynamicCode = (PackageDynamicCode) this.mPackageMap.get(str);
      if (packageDynamicCode == null) {
        packageDynamicCode = new PackageDynamicCode();
        this.mPackageMap.put(str, packageDynamicCode);
      }
      add = packageDynamicCode.add(str2, (char) i, i2, str3);
    }
    return add;
  }

  public Set getAllPackagesWithDynamicCodeLoading() {
    HashSet hashSet;
    synchronized (this.mLock) {
      hashSet = new HashSet(this.mPackageMap.keySet());
    }
    return hashSet;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public PackageDynamicCode getPackageDynamicCodeInfo(String str) {
    PackageDynamicCode packageDynamicCode;
    synchronized (this.mLock) {
      PackageDynamicCode packageDynamicCode2 = (PackageDynamicCode) this.mPackageMap.get(str);
      packageDynamicCode = null;
      Object[] objArr = 0;
      if (packageDynamicCode2 != null) {
        packageDynamicCode = new PackageDynamicCode(packageDynamicCode2);
      }
    }
    return packageDynamicCode;
  }

  public boolean removePackage(String str) {
    boolean z;
    synchronized (this.mLock) {
      z = this.mPackageMap.remove(str) != null;
    }
    return z;
  }

  public boolean removeUserPackage(String str, int i) {
    synchronized (this.mLock) {
      PackageDynamicCode packageDynamicCode = (PackageDynamicCode) this.mPackageMap.get(str);
      if (packageDynamicCode == null) {
        return false;
      }
      if (!packageDynamicCode.removeUser(i)) {
        return false;
      }
      if (packageDynamicCode.mFileUsageMap.isEmpty()) {
        this.mPackageMap.remove(str);
      }
      return true;
    }
  }

  public boolean removeFile(String str, String str2, int i) {
    synchronized (this.mLock) {
      PackageDynamicCode packageDynamicCode = (PackageDynamicCode) this.mPackageMap.get(str);
      if (packageDynamicCode == null) {
        return false;
      }
      if (!packageDynamicCode.removeFile(str2, i)) {
        return false;
      }
      if (packageDynamicCode.mFileUsageMap.isEmpty()) {
        this.mPackageMap.remove(str);
      }
      return true;
    }
  }

  public void syncData(Map map) {
    synchronized (this.mLock) {
      Iterator it = this.mPackageMap.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry entry = (Map.Entry) it.next();
        Set set = (Set) map.get(entry.getKey());
        if (set == null) {
          it.remove();
        } else {
          PackageDynamicCode packageDynamicCode = (PackageDynamicCode) entry.getValue();
          packageDynamicCode.syncData(map, set);
          if (packageDynamicCode.mFileUsageMap.isEmpty()) {
            it.remove();
          }
        }
      }
    }
  }

  public void maybeWriteAsync() {
    super.maybeWriteAsync(null);
  }

  public void writeNow() {
    super.writeNow(null);
  }

  @Override // com.android.server.pm.AbstractStatsBase
  public final void writeInternal(Void r2) {
    FileOutputStream fileOutputStream;
    AtomicFile file = getFile();
    try {
      fileOutputStream = file.startWrite();
    } catch (IOException e) {
      e = e;
      fileOutputStream = null;
    }
    try {
      write(fileOutputStream);
      file.finishWrite(fileOutputStream);
    } catch (IOException e2) {
      e = e2;
      file.failWrite(fileOutputStream);
      Slog.e(
          "PackageDynamicCodeLoading",
          "Failed to write dynamic usage for secondary code files.",
          e);
    }
  }

  public void write(OutputStream outputStream) {
    HashMap hashMap;
    synchronized (this.mLock) {
      hashMap = new HashMap(this.mPackageMap.size());
      for (Map.Entry entry : this.mPackageMap.entrySet()) {
        hashMap.put(
            (String) entry.getKey(), new PackageDynamicCode((PackageDynamicCode) entry.getValue()));
      }
    }
    write(outputStream, hashMap);
  }

  public static void write(OutputStream outputStream, Map map) {
    FastPrintWriter fastPrintWriter = new FastPrintWriter(outputStream);
    fastPrintWriter.println("DCL1");
    for (Map.Entry entry : map.entrySet()) {
      fastPrintWriter.print("P:");
      fastPrintWriter.println((String) entry.getKey());
      for (Map.Entry entry2 : ((PackageDynamicCode) entry.getValue()).mFileUsageMap.entrySet()) {
        String str = (String) entry2.getKey();
        DynamicCodeFile dynamicCodeFile = (DynamicCodeFile) entry2.getValue();
        fastPrintWriter.print(dynamicCodeFile.mFileType);
        fastPrintWriter.print(':');
        fastPrintWriter.print(dynamicCodeFile.mUserId);
        fastPrintWriter.print(':');
        String str2 = "";
        for (String str3 : dynamicCodeFile.mLoadingPackages) {
          fastPrintWriter.print(str2);
          fastPrintWriter.print(str3);
          str2 = ",";
        }
        fastPrintWriter.print(':');
        fastPrintWriter.println(escape(str));
      }
    }
    fastPrintWriter.flush();
    if (fastPrintWriter.checkError()) {
      throw new IOException("Writer failed");
    }
  }

  public void read() {
    super.read((Object) null);
  }

  @Override // com.android.server.pm.AbstractStatsBase
  public final void readInternal(Void r3) {
    FileInputStream fileInputStream = null;
    try {
      try {
        fileInputStream = getFile().openRead();
        read((InputStream) fileInputStream);
      } catch (FileNotFoundException unused) {
      } catch (IOException e) {
        Slog.w(
            "PackageDynamicCodeLoading",
            "Failed to parse dynamic usage for secondary code files.",
            e);
      }
    } finally {
      IoUtils.closeQuietly(fileInputStream);
    }
  }

  public void read(InputStream inputStream) {
    HashMap hashMap = new HashMap();
    read(inputStream, hashMap);
    synchronized (this.mLock) {
      this.mPackageMap = hashMap;
    }
  }

  public static void read(InputStream inputStream, Map map) {
    String readLine;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    String readLine2 = bufferedReader.readLine();
    if (!"DCL1".equals(readLine2)) {
      throw new IOException("Incorrect version line: " + readLine2);
    }
    String readLine3 = bufferedReader.readLine();
    if (readLine3 != null && !readLine3.startsWith("P:")) {
      throw new IOException("Malformed line: " + readLine3);
    }
    while (readLine3 != null) {
      String substring = readLine3.substring(2);
      PackageDynamicCode packageDynamicCode = new PackageDynamicCode();
      while (true) {
        readLine = bufferedReader.readLine();
        if (readLine == null || readLine.startsWith("P:")) {
          break;
        } else {
          readFileInfo(readLine, packageDynamicCode);
        }
      }
      if (!packageDynamicCode.mFileUsageMap.isEmpty()) {
        map.put(substring, packageDynamicCode);
      }
      readLine3 = readLine;
    }
  }

  public static void readFileInfo(String str, PackageDynamicCode packageDynamicCode) {
    try {
      Matcher matcher = PACKAGE_LINE_PATTERN.matcher(str);
      if (!matcher.matches()) {
        throw new IOException("Malformed line: " + str);
      }
      char charAt = matcher.group(1).charAt(0);
      int parseInt = Integer.parseInt(matcher.group(2));
      String[] split = matcher.group(3).split(",");
      String unescape = unescape(matcher.group(4));
      if (split.length == 0) {
        throw new IOException("Malformed line: " + str);
      }
      if (!isValidFileType(charAt)) {
        throw new IOException("Unknown file type: " + str);
      }
      packageDynamicCode.mFileUsageMap.put(unescape, new DynamicCodeFile(charAt, parseInt, split));
    } catch (RuntimeException e) {
      throw new IOException("Unable to parse line: " + str, e);
    }
  }

  public static String escape(String str) {
    if (str.indexOf(92) == -1 && str.indexOf(10) == -1 && str.indexOf(13) == -1) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str.length() + 10);
    for (int i = 0; i < str.length(); i++) {
      char charAt = str.charAt(i);
      if (charAt == '\n') {
        sb.append("\\n");
      } else if (charAt == '\r') {
        sb.append("\\r");
      } else if (charAt == '\\') {
        sb.append("\\\\");
      } else {
        sb.append(charAt);
      }
    }
    return sb.toString();
  }

  public static String unescape(String str) {
    int indexOf = str.indexOf(92);
    if (indexOf == -1) {
      return str;
    }
    StringBuilder sb = new StringBuilder(str.length());
    int i = 0;
    while (indexOf < str.length() - 1) {
      sb.append((CharSequence) str, i, indexOf);
      char charAt = str.charAt(indexOf + 1);
      if (charAt == '\\') {
        sb.append('\\');
      } else if (charAt == 'n') {
        sb.append('\n');
      } else if (charAt == 'r') {
        sb.append('\r');
      } else {
        throw new IOException("Bad escape in: " + str);
      }
      i = indexOf + 2;
      indexOf = str.indexOf(92, i);
      if (indexOf == -1) {
        sb.append((CharSequence) str, i, str.length());
        return sb.toString();
      }
    }
    throw new IOException("Unexpected \\ in: " + str);
  }

  public class PackageDynamicCode {
    public final Map mFileUsageMap;

    public PackageDynamicCode() {
      this.mFileUsageMap = new HashMap();
    }

    public PackageDynamicCode(PackageDynamicCode packageDynamicCode) {
      this.mFileUsageMap = new HashMap(packageDynamicCode.mFileUsageMap.size());
      for (Map.Entry entry : packageDynamicCode.mFileUsageMap.entrySet()) {
        this.mFileUsageMap.put(
            (String) entry.getKey(), new DynamicCodeFile((DynamicCodeFile) entry.getValue()));
      }
    }

    public final boolean add(String str, char c, int i, String str2) {
      DynamicCodeFile dynamicCodeFile = (DynamicCodeFile) this.mFileUsageMap.get(str);
      if (dynamicCodeFile == null) {
        if (this.mFileUsageMap.size() >= 100) {
          return false;
        }
        this.mFileUsageMap.put(str, new DynamicCodeFile(c, i, new String[] {str2}));
        return true;
      }
      if (dynamicCodeFile.mUserId != i) {
        throw new IllegalArgumentException(
            "Cannot change userId for '" + str + "' from " + dynamicCodeFile.mUserId + " to " + i);
      }
      return dynamicCodeFile.mLoadingPackages.add(str2);
    }

    public final boolean removeUser(int i) {
      Iterator it = this.mFileUsageMap.values().iterator();
      boolean z = false;
      while (it.hasNext()) {
        if (((DynamicCodeFile) it.next()).mUserId == i) {
          it.remove();
          z = true;
        }
      }
      return z;
    }

    public final boolean removeFile(String str, int i) {
      DynamicCodeFile dynamicCodeFile = (DynamicCodeFile) this.mFileUsageMap.get(str);
      if (dynamicCodeFile == null || dynamicCodeFile.mUserId != i) {
        return false;
      }
      this.mFileUsageMap.remove(str);
      return true;
    }

    public final void syncData(Map map, Set set) {
      Iterator it = this.mFileUsageMap.values().iterator();
      while (it.hasNext()) {
        DynamicCodeFile dynamicCodeFile = (DynamicCodeFile) it.next();
        int i = dynamicCodeFile.mUserId;
        if (!set.contains(Integer.valueOf(i))) {
          it.remove();
        } else {
          Iterator it2 = dynamicCodeFile.mLoadingPackages.iterator();
          while (it2.hasNext()) {
            Set set2 = (Set) map.get((String) it2.next());
            if (set2 == null || !set2.contains(Integer.valueOf(i))) {
              it2.remove();
            }
          }
          if (dynamicCodeFile.mLoadingPackages.isEmpty()) {
            it.remove();
          }
        }
      }
    }
  }

  public class DynamicCodeFile {
    public final char mFileType;
    public final Set mLoadingPackages;
    public final int mUserId;

    public DynamicCodeFile(char c, int i, String... strArr) {
      this.mFileType = c;
      this.mUserId = i;
      this.mLoadingPackages = new HashSet(Arrays.asList(strArr));
    }

    public DynamicCodeFile(DynamicCodeFile dynamicCodeFile) {
      this.mFileType = dynamicCodeFile.mFileType;
      this.mUserId = dynamicCodeFile.mUserId;
      this.mLoadingPackages = new HashSet(dynamicCodeFile.mLoadingPackages);
    }
  }
}
