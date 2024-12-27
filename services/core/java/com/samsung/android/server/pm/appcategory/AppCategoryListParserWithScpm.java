package com.samsung.android.server.pm.appcategory;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.Slog;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCategoryListParserWithScpm extends AppCategoryListParser {
    public static final String FILE_PATH;
    public static final Pattern NAME_PATTERN;
    public static final String TEMP_FILE_PATH;
    public boolean mShouldDecode;

    static {
        String str = Environment.getDataDirectory().getPath() + "/system/appcategory/";
        TEMP_FILE_PATH = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "tempcategory.xml");
        FILE_PATH = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "pm_app_category_from_scpm.xml");
        NAME_PATTERN = Pattern.compile("name=\"(.+)\"", 32);
    }

    public static String getNameInPattern(String str) {
        Matcher matcher = NAME_PATTERN.matcher(str);
        if (matcher.find()) {
            return str.substring(matcher.start() + 6, matcher.end() - 1);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x015a, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x015b, code lost:
    
        r7 = r16;
        r5 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0095, code lost:
    
        r3 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0183, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:?, code lost:
    
        throw r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0187, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0189, code lost:
    
        r3.addSuppressed(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x018c, code lost:
    
        throw r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0160, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0161, code lost:
    
        r7 = r16;
        r5 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x016a, code lost:
    
        r7 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0173, code lost:
    
        throw new java.lang.RuntimeException("Failed to renameTo.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0166, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0167, code lost:
    
        r7 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0101, code lost:
    
        r16 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0103, code lost:
    
        r12.flush();
        r0 = new java.io.File(com.samsung.android.server.pm.appcategory.AppCategoryListParserWithScpm.FILE_PATH);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0111, code lost:
    
        if (r0.exists() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0113, code lost:
    
        r0.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x011f, code lost:
    
        if (new java.io.File(r4).renameTo(r0) == false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0121, code lost:
    
        android.os.SystemProperties.set("persist.sys.package_feature.version.appcategory", java.lang.Long.toString(r9));
        r3 = new com.samsung.android.server.pm.appcategory.AppCategoryListParserWithScpm();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x012d, code lost:
    
        r3.parseAppCategoryList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0130, code lost:
    
        r12.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0133, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0136, code lost:
    
        r0 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m("updateParserIfNeeded: newVersion=", r9, ", oldVersion=");
        r0.append(r16);
        android.util.Slog.d("AppCategoryListParserWithScpm", r0.toString());
        r0 = new java.io.File(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x014f, code lost:
    
        if (r0.exists() == false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0151, code lost:
    
        r0.delete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:?, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0155, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0156, code lost:
    
        r7 = r16;
        r5 = r3;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01ad  */
    /* JADX WARN: Type inference failed for: r13v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r15v1, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.server.pm.appcategory.AppCategoryListParserWithScpm updateParserIfNeeded(java.io.FileDescriptor r18) {
        /*
            Method dump skipped, instructions count: 464
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.pm.appcategory.AppCategoryListParserWithScpm.updateParserIfNeeded(java.io.FileDescriptor):com.samsung.android.server.pm.appcategory.AppCategoryListParserWithScpm");
    }

    @Override // com.samsung.android.server.pm.appcategory.AppCategoryListParser
    public final void parseAppCategoryList() {
        String str = FILE_PATH;
        if (new File(str).exists()) {
            this.mShouldDecode = true;
            try {
                parseAppCategoryList(str);
                Slog.d("AppCategoryListParserWithScpm", "parseAppCategoryList: ScpmVersion=" + SystemProperties.getLong("persist.sys.package_feature.version.appcategory", 2024021301L));
                return;
            } catch (Exception e) {
                Slog.e("AppCategoryListParserWithScpm", "Failed to read SCPM file.", e);
            } finally {
                this.mShouldDecode = false;
            }
        }
        SystemProperties.set("persist.sys.package_feature.version.appcategory", Long.toString(2024021301L));
        parseAppCategoryList(null);
        Slog.w("AppCategoryListParserWithScpm", "SCPM file was not existed or corrupted. Read system file");
    }

    @Override // com.samsung.android.server.pm.appcategory.AppCategoryListParser
    public final List parsePackages(XmlPullParser xmlPullParser) {
        List parsePackages = super.parsePackages(xmlPullParser);
        if (!this.mShouldDecode) {
            return parsePackages;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) parsePackages).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            arrayList.add(str == null ? null : new String(Base64.decode(str.getBytes(), 2)));
        }
        return arrayList;
    }

    @Override // com.samsung.android.server.pm.appcategory.AppCategoryListParser
    public final void restoreToSystemFile() {
        this.mShouldDecode = false;
        SystemProperties.set("persist.sys.package_feature.version.appcategory", Long.toString(2024021301L));
        super.parseAppCategoryList();
        Slog.w("AppCategoryListParserWithScpm", "SCPM file might be corrupted. Read system file instead");
    }
}
