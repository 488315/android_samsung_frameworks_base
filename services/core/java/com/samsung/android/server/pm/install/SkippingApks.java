package com.samsung.android.server.pm.install;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SkippingApks {
    public ArrayList mSkippingApkList;

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0060 -> B:21:0x007b). Please report as a decompilation issue!!! */
    public static List getApkNamesFromFile(String str) {
        int length;
        ArrayList arrayList = new ArrayList();
        DataInputStream dataInputStream = null;
        DataInputStream dataInputStream2 = null;
        DataInputStream dataInputStream3 = null;
        dataInputStream = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
            dataInputStream = dataInputStream;
        }
        try {
            try {
                DataInputStream dataInputStream4 =
                        new DataInputStream(new BufferedInputStream(new FileInputStream(str)));
                try {
                    int available = dataInputStream4.available();
                    int i = available;
                    if (available > 0) {
                        String trim = dataInputStream4.readLine().trim();
                        if (trim == null || (length = trim.length()) <= 0) {
                            throw new FileNotFoundException("FileNotFoundException: " + str);
                        }
                        i = length;
                    }
                    while (dataInputStream4.available() != 0) {
                        arrayList.add(dataInputStream4.readLine().trim());
                    }
                    dataInputStream4.close();
                    dataInputStream = i;
                } catch (FileNotFoundException e2) {
                    e = e2;
                    dataInputStream2 = dataInputStream4;
                    e.printStackTrace();
                    dataInputStream = dataInputStream2;
                    if (dataInputStream2 != null) {
                        dataInputStream2.close();
                        dataInputStream = dataInputStream2;
                    }
                    return arrayList;
                } catch (IOException e3) {
                    e = e3;
                    dataInputStream3 = dataInputStream4;
                    e.printStackTrace();
                    dataInputStream = dataInputStream3;
                    if (dataInputStream3 != null) {
                        dataInputStream3.close();
                        dataInputStream = dataInputStream3;
                    }
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    dataInputStream = dataInputStream4;
                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
            } catch (IOException e6) {
                e = e6;
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void addSkippingPackage(String str) {
        if (TextUtils.isEmpty(str) || this.mSkippingApkList.contains(str)) {
            return;
        }
        this.mSkippingApkList.add(str);
    }
}
