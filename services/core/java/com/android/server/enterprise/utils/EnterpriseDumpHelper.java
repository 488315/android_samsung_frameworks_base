package com.android.server.enterprise.utils;

import android.content.ContentValues;
import android.content.Context;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EnterpriseDumpHelper {
    public final EdmStorageProvider mEdmStorageProvider;

    public EnterpriseDumpHelper(Context context) {
        this.mEdmStorageProvider = new EdmStorageProvider(context);
    }

    public static String getBitNumbers(String str) {
        StringBuilder sb = new StringBuilder();
        int parseInt = Integer.parseInt(str);
        sb.append("(0x" + Integer.toHexString(parseInt) + ")");
        if (parseInt != 0) {
            sb.append(": ");
        }
        int i = 0;
        while (parseInt != 0) {
            if ((parseInt & 1) == 1) {
                sb.append(i + " ");
            }
            parseInt >>= 1;
            i++;
        }
        return sb.toString();
    }

    public final void dumpTable(PrintWriter printWriter, String str, String[] strArr, String[] strArr2) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("[", str, " table]");
        m.append(System.lineSeparator());
        printWriter.write(m.toString());
        if (!"generic".equals(str)) {
            String readColumns = readColumns(str, strArr, strArr2, null);
            if (readColumns != null) {
                printWriter.write(readColumns);
                return;
            }
            return;
        }
        String[] strArr3 = {"name", "value", "containerID", "userID"};
        for (String str2 : strArr) {
            String readColumns2 = readColumns(str, strArr3, strArr2, AccountManagerService$$ExternalSyntheticOutline0.m("name", str2));
            if (readColumns2 != null) {
                printWriter.write(readColumns2);
            }
        }
    }

    public final String readColumns(String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
        ArrayList arrayList = (ArrayList) this.mEdmStorageProvider.getValues(str, strArr, contentValues);
        if (arrayList.isEmpty()) {
            return null;
        }
        List asList = strArr2 != null ? Arrays.asList(strArr2) : null;
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
            for (String str2 : strArr) {
                String asString = contentValues2.getAsString(str2);
                sb.append("   " + str2 + ": " + asString);
                if (asList != null && asList.contains(str2)) {
                    try {
                        sb.append(getBitNumbers(asString));
                    } catch (NumberFormatException unused) {
                    }
                }
                sb.append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
