package com.samsung.android.server.pm.install;

import android.os.SystemProperties;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class SkippingApks {
    public ArrayList mSkippingApkList = new ArrayList();

    public void initSkippingApkList() {
        initSkippingApkList(null);
    }

    public void initSkippingApkList(File file) {
        String str;
        this.mSkippingApkList.clear();
        if (file != null) {
            str = file.getAbsolutePath();
        } else {
            String str2 = SystemProperties.get("mdc.sys.omc_etcpath", (String) null);
            String str3 = "/data/omc/etc/enforceskippingpackages.txt";
            if (!isFileExists("/data/omc/etc/enforceskippingpackages.txt")) {
                if (str2 != null && !str2.equals("")) {
                    String str4 = str2 + "/enforceskippingpackages_" + SystemProperties.get("ro.boot.activatedid", (String) null) + ".txt";
                    if (isFileExists(str4)) {
                        str = str4;
                    } else {
                        str3 = str2 + "/enforceskippingpackages_" + SystemProperties.get("ro.csc.countryiso_code", (String) null) + ".txt";
                        if (!isFileExists(str3)) {
                            str = str2 + "/enforceskippingpackages.txt";
                        }
                    }
                } else {
                    str = "/system/csc_contents/enforceskippingpackages.txt";
                    if (!isFileExists("/system/csc_contents/enforceskippingpackages.txt")) {
                        str = "/system/etc/enforceskippingpackages.txt";
                    }
                }
            }
            str = str3;
        }
        addSkippingApksFromFile(str);
        String str5 = SystemProperties.get("persist.sys.omcnw_path", (String) null);
        if (str5 != null && !str5.equals("")) {
            String str6 = str5 + "/etc/enforceinstallpackages_omcnw.txt";
            if (isFileExists(str6)) {
                removeSkippingApksFromFile(str6);
            }
        }
        SkippingApksExceptions skippingApksExceptions = new SkippingApksExceptions();
        if (skippingApksExceptions.hasAdditionalSkippingApkList()) {
            Iterator it = skippingApksExceptions.getAdditionalSkippingApkList().iterator();
            while (it.hasNext()) {
                addSkippingPackage((String) it.next());
            }
        }
    }

    public final boolean isFileExists(String str) {
        return new File(str).exists();
    }

    public final void addSkippingApksFromFile(String str) {
        this.mSkippingApkList.addAll(getApkNamesFromFile(str));
    }

    public final void removeSkippingApksFromFile(String str) {
        for (String str2 : getApkNamesFromFile(str)) {
            if (this.mSkippingApkList.contains(str2)) {
                this.mSkippingApkList.remove(str2);
            }
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0079 -> B:21:0x007c). Please report as a decompilation issue!!! */
    public final List getApkNamesFromFile(String str) {
        DataInputStream dataInputStream;
        int length;
        ArrayList arrayList = new ArrayList();
        DataInputStream dataInputStream2 = null;
        DataInputStream dataInputStream3 = null;
        DataInputStream dataInputStream4 = null;
        dataInputStream2 = null;
        try {
            try {
                try {
                    dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(str)));
                } catch (Throwable th) {
                    th = th;
                }
            } catch (FileNotFoundException e) {
                e = e;
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            dataInputStream2 = dataInputStream2;
        }
        try {
            int available = dataInputStream.available();
            int i = available;
            if (available > 0) {
                String trim = dataInputStream.readLine().trim();
                if (trim == null || (length = trim.length()) <= 0) {
                    throw new FileNotFoundException("FileNotFoundException: " + str);
                }
                i = length;
            }
            while (dataInputStream.available() != 0) {
                arrayList.add(dataInputStream.readLine().trim());
            }
            dataInputStream.close();
            dataInputStream2 = i;
        } catch (FileNotFoundException e4) {
            e = e4;
            dataInputStream3 = dataInputStream;
            e.printStackTrace();
            dataInputStream2 = dataInputStream3;
            if (dataInputStream3 != null) {
                dataInputStream3.close();
                dataInputStream2 = dataInputStream3;
            }
            return arrayList;
        } catch (IOException e5) {
            e = e5;
            dataInputStream4 = dataInputStream;
            e.printStackTrace();
            dataInputStream2 = dataInputStream4;
            if (dataInputStream4 != null) {
                dataInputStream4.close();
                dataInputStream2 = dataInputStream4;
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            dataInputStream2 = dataInputStream;
            if (dataInputStream2 != null) {
                try {
                    dataInputStream2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
        return arrayList;
    }

    public ArrayList getSkippingApkList() {
        return this.mSkippingApkList;
    }

    public boolean addSkippingPackage(String str) {
        if (TextUtils.isEmpty(str) || this.mSkippingApkList.contains(str)) {
            return false;
        }
        this.mSkippingApkList.add(str);
        return true;
    }

    public boolean isSkippingApk(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator it = this.mSkippingApkList.iterator();
        while (it.hasNext()) {
            if (str.equals((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("SkippingApks:");
        Iterator it = this.mSkippingApkList.iterator();
        while (it.hasNext()) {
            printWriter.println("  name : " + ((String) it.next()));
        }
    }
}
