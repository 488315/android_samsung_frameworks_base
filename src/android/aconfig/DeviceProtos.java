package android.aconfig;

import android.aconfig.nano.Aconfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DeviceProtos {
    private static final String APEX_ACONFIG_PATH_SUFFIX = "/etc/aconfig_flags.pb";
    private static final String APEX_DIR = "/apex";
    public static final String[] PATHS = {"/system/etc/aconfig_flags.pb", "/system_ext/etc/aconfig_flags.pb", "/product/etc/aconfig_flags.pb", "/vendor/etc/aconfig_flags.pb"};

    public static List<Aconfig.parsed_flag> loadAndParseFlagProtos() throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = parsedFlagsProtoPaths().iterator();
        while (it.hasNext()) {
            FileInputStream fileInputStream = new FileInputStream(it.next());
            try {
                for (Aconfig.parsed_flag parsed_flagVar : Aconfig.parsed_flags.parseFrom(fileInputStream.readAllBytes()).parsedFlag) {
                    arrayList.add(parsed_flagVar);
                }
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return arrayList;
    }

    public static List<String> parsedFlagsProtoPaths() {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList(Arrays.asList(PATHS));
        File file = new File(APEX_DIR);
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                if (!file2.getAbsolutePath().contains("@")) {
                    File file3 = new File(file2 + APEX_ACONFIG_PATH_SUFFIX);
                    if (file3.exists()) {
                        arrayList.add(file3.getAbsolutePath());
                    }
                }
            }
        }
        return arrayList;
    }
}
