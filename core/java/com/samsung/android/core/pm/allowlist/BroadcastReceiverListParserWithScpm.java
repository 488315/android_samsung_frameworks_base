package com.samsung.android.core.pm.allowlist;

import android.os.Environment;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.Slog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BroadcastReceiverListParserWithScpm extends BroadcastReceiverListParser {
    private static final long DEFAULT_VERSION = 2022010101;
    private static final String PACKAGE_NAME_TAG = "package name";
    private static final String PROP_PKG_CACHE_CLEAR_NEEDED =
            "persist.sys.clear_package_cache_needed";
    private static final String PROP_VERSION_NAME = "persist.sys.package_feature.version.br";
    private static final boolean SAFE_DEBUG = false;
    private static final String VERSION_NAME_TAG = "version name";
    private boolean mShouldDecode;
    private static final String DATA_SYSTEM_PATH =
            Environment.getDataDirectory().getPath() + "/system/br/";
    private static final String TEMP_FILE_PATH = DATA_SYSTEM_PATH + "temp.xml";
    private static final String FILE_PATH = DATA_SYSTEM_PATH + "broadcast_allowlist_from_scpm.xml";
    private static final Pattern NAME_PATTERN = Pattern.compile("name=\"(.+)\"", 32);

    @Override // com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser
    public void parseAllowList() {
        if (new File(FILE_PATH).exists()) {
            this.mShouldDecode = true;
            try {
                parseAllowList(FILE_PATH);
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
    List<String> parsePackages(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<String> packages = super.parsePackages(parser);
        if (!this.mShouldDecode) {
            return packages;
        }
        List<String> decodedPackages = new ArrayList<>();
        for (String encodedPackage : packages) {
            decodedPackages.add(convertBase64String(encodedPackage, false));
        }
        return decodedPackages;
    }

    public void dump(PrintWriter pw) {
        pw.print("BroadcastReceiverListParserWithScpm: ");
        try {
            pw.print("FileExists=" + new File(FILE_PATH).exists());
        } catch (Throwable th) {
        }
        pw.print(", ScpmVersion=");
        pw.print(getScpmVersion());
        pw.println();
    }

    private static long getScpmVersion() {
        return SystemProperties.getLong(PROP_VERSION_NAME, DEFAULT_VERSION);
    }

    private static String convertBase64String(String input, boolean encode) {
        if (input == null) {
            return null;
        }
        return new String(
                encode ? Base64.encode(input.getBytes(), 2) : Base64.decode(input.getBytes(), 2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0178, code lost:

       if (r0.exists() != false) goto L91;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0071, code lost:

       r0.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0074, code lost:

       r0.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a3, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:?, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00a6, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01de, code lost:

       if (r0.exists() == false) goto L93;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01e0, code lost:

       r0.delete();
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01e4, code lost:

       return r6;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser
            updateParserIfNeeded(java.io.FileDescriptor r19) {
        /*
            Method dump skipped, instructions count: 531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.core.pm.allowlist.BroadcastReceiverListParserWithScpm.updateParserIfNeeded(java.io.FileDescriptor):com.samsung.android.core.pm.allowlist.BroadcastReceiverListParser");
    }

    private static String getNameInPattern(String line) {
        Matcher matcher = NAME_PATTERN.matcher(line);
        if (matcher.find()) {
            return line.substring(matcher.start() + 6, matcher.end() - 1);
        }
        return null;
    }
}
