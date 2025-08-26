package com.samsung.android.core.pm.allowlist;

import android.os.Environment;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class BroadcastReceiverListParserWithScpm extends BroadcastReceiverListParser {
    private static final String DATA_SYSTEM_PATH;
    private static final long DEFAULT_VERSION = 2022010101;
    private static final String FILE_PATH;
    private static final Pattern NAME_PATTERN;
    private static final String PACKAGE_NAME_TAG = "package name";
    private static final String PROP_PKG_CACHE_CLEAR_NEEDED = "persist.sys.clear_package_cache_needed";
    private static final String PROP_VERSION_NAME = "persist.sys.package_feature.version.br";
    private static final boolean SAFE_DEBUG = false;
    private static final String TEMP_FILE_PATH;
    private static final String VERSION_NAME_TAG = "version name";
    private boolean mShouldDecode;

    static {
        String str = Environment.getDataDirectory().getPath() + "/system/br/";
        DATA_SYSTEM_PATH = str;
        TEMP_FILE_PATH = str + "temp.xml";
        FILE_PATH = str + "broadcast_allowlist_from_scpm.xml";
        NAME_PATTERN = Pattern.compile("name=\"(.+)\"", 32);
    }

    @Override // com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser
    public void parseAllowList() throws XmlPullParserException, IOException {
        String str = FILE_PATH;
        if (new File(str).exists()) {
            this.mShouldDecode = true;
            try {
                parseAllowList(str);
                Slog.d("BRListParser", "parseAllowList: ScpmVersion=" + getScpmVersion());
                return;
            } catch (Exception e) {
                Slog.e("BRListParser", "Failed to read SCPM allowlist file.", e);
            } finally {
                this.mShouldDecode = false;
            }
        }
        SystemProperties.set(PROP_VERSION_NAME, Long.toString(DEFAULT_VERSION));
        super.parseAllowList();
        Slog.w("BRListParser", "SCPM file was not existed or corrupted. Read system file");
    }

    @Override // com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser
    List<String> parsePackages(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        List<String> packages = super.parsePackages(xmlPullParser);
        if (!this.mShouldDecode) {
            return packages;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = packages.iterator();
        while (it.hasNext()) {
            arrayList.add(convertBase64String(it.next(), false));
        }
        return arrayList;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print("BroadcastReceiverListParserWithScpm: ");
        try {
            printWriter.print("FileExists=" + new File(FILE_PATH).exists());
        } catch (Throwable unused) {
        }
        printWriter.print(", ScpmVersion=");
        printWriter.print(getScpmVersion());
        printWriter.println();
    }

    private static long getScpmVersion() {
        return SystemProperties.getLong(PROP_VERSION_NAME, DEFAULT_VERSION);
    }

    private static String convertBase64String(String str, boolean z) {
        if (str == null) {
            return null;
        }
        return new String(z ? Base64.encode(str.getBytes(), 2) : Base64.decode(str.getBytes(), 2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00cd, code lost:
    
        r15.flush();
        r4 = new java.io.File(com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm.FILE_PATH);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00db, code lost:
    
        if (r4.exists() == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00dd, code lost:
    
        r4.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00e0, code lost:
    
        r12 = com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm.TEMP_FILE_PATH;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00eb, code lost:
    
        if (new java.io.File(r12).renameTo(r4) == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ed, code lost:
    
        android.os.SystemProperties.set(com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm.PROP_VERSION_NAME, java.lang.Long.toString(r7));
        android.os.SystemProperties.set(com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm.PROP_PKG_CACHE_CLEAR_NEEDED, java.lang.Boolean.toString(true));
        r4 = new com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0106, code lost:
    
        r4.parseAllowList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0109, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x010c, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x010f, code lost:
    
        android.util.Slog.d("BRListParser", "updateParserIfNeeded: newVersion=" + r7 + ", oldVersion=" + r2);
        r15 = new java.io.File(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x012d, code lost:
    
        if (r15.exists() == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x012f, code lost:
    
        r15.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0133, code lost:
    
        r15 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0134, code lost:
    
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0136, code lost:
    
        r15 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0137, code lost:
    
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0139, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x013a, code lost:
    
        r5 = r4;
        r4 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0145, code lost:
    
        throw new java.lang.RuntimeException("Failed to renameTo.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0147, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x014b, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x014c, code lost:
    
        r4.addSuppressed(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x014f, code lost:
    
        throw r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0151, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0155, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0156, code lost:
    
        r15.addSuppressed(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0159, code lost:
    
        throw r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0182, code lost:
    
        r15.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static BroadcastReceiverListParser updateParserIfNeeded(FileDescriptor fileDescriptor) {
        String str = TEMP_FILE_PATH;
        File file = new File(new File(str).getParent());
        BroadcastReceiverListParserWithScpm broadcastReceiverListParserWithScpm = null;
        if (!file.exists() && !file.mkdirs()) {
            Slog.w("BRListParser", "Failed to mkdirs");
            return null;
        }
        long scpmVersion = getScpmVersion();
        long jLongValue = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDescriptor));
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(str));
                int i = 0;
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        i++;
                        if (line.contains(VERSION_NAME_TAG)) {
                            jLongValue = Long.valueOf(getNameInPattern(line)).longValue();
                            if (jLongValue <= scpmVersion) {
                                bufferedWriter.close();
                                bufferedReader.close();
                                return null;
                            }
                        } else if (line.contains(PACKAGE_NAME_TAG)) {
                            try {
                                String nameInPattern = getNameInPattern(line);
                                line = line.replace(nameInPattern, convertBase64String(nameInPattern, true));
                            } catch (Throwable th) {
                                Slog.w("BRListParser", "Failed line=" + i + ", e=" + th);
                            }
                        }
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            try {
                Slog.w("BRListParser", "Failed to updateParserIfNeeded.", th);
                Slog.d("BRListParser", "updateParserIfNeeded: newVersion=" + jLongValue + ", oldVersion=" + scpmVersion);
                File file2 = new File(TEMP_FILE_PATH);
                if (file2.exists()) {
                }
                return broadcastReceiverListParserWithScpm;
            } finally {
                Slog.d("BRListParser", "updateParserIfNeeded: newVersion=" + jLongValue + ", oldVersion=" + scpmVersion);
                File file3 = new File(TEMP_FILE_PATH);
                if (file3.exists()) {
                    file3.delete();
                }
            }
        }
    }

    private static String getNameInPattern(String str) {
        Matcher matcher = NAME_PATTERN.matcher(str);
        if (matcher.find()) {
            return str.substring(matcher.start() + 6, matcher.end() - 1);
        }
        return null;
    }
}
