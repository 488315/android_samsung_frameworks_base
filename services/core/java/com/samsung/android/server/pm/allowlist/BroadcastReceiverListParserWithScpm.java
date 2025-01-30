package com.samsung.android.server.pm.allowlist;

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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class BroadcastReceiverListParserWithScpm extends BroadcastReceiverListParser {
    public static final String DATA_SYSTEM_PATH;
    public static final String FILE_PATH;
    public static final Pattern NAME_PATTERN;
    public static final String TEMP_FILE_PATH;
    public boolean mShouldDecode;

    static {
        String str = Environment.getDataDirectory().getPath() + "/system/br/";
        DATA_SYSTEM_PATH = str;
        TEMP_FILE_PATH = str + "temp.xml";
        FILE_PATH = str + "broadcast_allowlist_from_scpm.xml";
        NAME_PATTERN = Pattern.compile("name=\"(.+)\"", 32);
    }

    @Override // com.samsung.android.server.pm.allowlist.BroadcastReceiverListParser
    public void parseAllowList() {
        long scpmVersion = getScpmVersion();
        String str = FILE_PATH;
        if (!new File(str).exists()) {
            SystemProperties.set("persist.sys.package_feature.version.br", Long.toString(2022010101L));
            super.parseAllowList();
            Slog.w("BRListParser", "SCPM file was not existed. Read system file");
            return;
        }
        this.mShouldDecode = true;
        parseAllowList(str);
        this.mShouldDecode = false;
        Slog.d("BRListParser", "parseAllowList: ScpmVersion=" + scpmVersion);
    }

    @Override // com.samsung.android.server.pm.allowlist.BroadcastReceiverListParser
    public List parsePackages(XmlPullParser xmlPullParser) {
        List parsePackages = super.parsePackages(xmlPullParser);
        if (!this.mShouldDecode) {
            return parsePackages;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = parsePackages.iterator();
        while (it.hasNext()) {
            arrayList.add(convertBase64String((String) it.next(), false));
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

    public static long getScpmVersion() {
        return SystemProperties.getLong("persist.sys.package_feature.version.br", 2022010101L);
    }

    public static String convertBase64String(String str, boolean z) {
        if (str == null) {
            return null;
        }
        return new String(z ? Base64.encode(str.getBytes(), 2) : Base64.decode(str.getBytes(), 2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d0, code lost:
    
        r15.flush();
        r4 = new java.io.File(com.samsung.android.server.pm.allowlist.BroadcastReceiverListParserWithScpm.FILE_PATH);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00de, code lost:
    
        if (r4.exists() == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e0, code lost:
    
        r4.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e3, code lost:
    
        r12 = com.samsung.android.server.pm.allowlist.BroadcastReceiverListParserWithScpm.TEMP_FILE_PATH;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ee, code lost:
    
        if (new java.io.File(r12).renameTo(r4) == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f0, code lost:
    
        android.os.SystemProperties.set("persist.sys.package_feature.version.br", java.lang.Long.toString(r7));
        android.os.SystemProperties.set("persist.sys.clear_package_cache_needed", java.lang.Boolean.toString(true));
        r4 = new com.samsung.android.server.pm.allowlist.BroadcastReceiverListParserWithScpm();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0109, code lost:
    
        r4.parseAllowList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x010c, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x010f, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0112, code lost:
    
        android.util.Slog.d("BRListParser", "updateParserIfNeeded: newVersion=" + r7 + ", oldVersion=" + r2);
        r15 = new java.io.File(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0133, code lost:
    
        if (r15.exists() == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0135, code lost:
    
        r15.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:?, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0139, code lost:
    
        r15 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x013a, code lost:
    
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0161, code lost:
    
        android.util.Slog.w("BRListParser", "Failed to updateParserIfNeeded.", r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0166, code lost:
    
        android.util.Slog.d("BRListParser", "updateParserIfNeeded: newVersion=" + r7 + ", oldVersion=" + r2);
        r15 = new java.io.File(com.samsung.android.server.pm.allowlist.BroadcastReceiverListParserWithScpm.TEMP_FILE_PATH);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0189, code lost:
    
        if (r15.exists() != false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x018b, code lost:
    
        r15.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x018f, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0190, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0191, code lost:
    
        android.util.Slog.d("BRListParser", "updateParserIfNeeded: newVersion=" + r7 + ", oldVersion=" + r2);
        r0 = new java.io.File(com.samsung.android.server.pm.allowlist.BroadcastReceiverListParserWithScpm.TEMP_FILE_PATH);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01b4, code lost:
    
        if (r0.exists() != false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01b6, code lost:
    
        r0.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01b9, code lost:
    
        throw r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x013c, code lost:
    
        r15 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x013d, code lost:
    
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x013f, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0140, code lost:
    
        r5 = r4;
        r4 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x014b, code lost:
    
        throw new java.lang.RuntimeException("Failed to renameTo.");
     */
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
        long j = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDescriptor));
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(str));
                int i = 0;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        i++;
                        if (readLine.contains("version name")) {
                            j = Long.valueOf(getNameInPattern(readLine)).longValue();
                            if (j <= scpmVersion) {
                                bufferedWriter.close();
                                bufferedReader.close();
                                return null;
                            }
                        } else if (readLine.contains("package name")) {
                            try {
                                String nameInPattern = getNameInPattern(readLine);
                                readLine = readLine.replace(nameInPattern, convertBase64String(nameInPattern, true));
                            } catch (Throwable th) {
                                Slog.w("BRListParser", "Failed line=" + i + ", e=" + th);
                            }
                        }
                        bufferedWriter.write(readLine);
                        bufferedWriter.newLine();
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            bufferedWriter.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                try {
                    bufferedReader.close();
                } catch (Throwable th5) {
                    th.addSuppressed(th5);
                }
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public static String getNameInPattern(String str) {
        Matcher matcher = NAME_PATTERN.matcher(str);
        if (matcher.find()) {
            return str.substring(matcher.start() + 6, matcher.end() - 1);
        }
        return null;
    }
}
