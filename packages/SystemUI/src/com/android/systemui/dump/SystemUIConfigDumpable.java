package com.android.systemui.dump;

import android.content.Context;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemUIConfigDumpable implements Dumpable {
    public final Context context;
    public final Map startables;

    public SystemUIConfigDumpable(DumpManager dumpManager, Context context, Map<Class<?>, Provider> map) {
        this.context = context;
        this.startables = map;
        dumpManager.registerCriticalDumpable("SystemUiServiceComponents", this);
    }

    public static void dumpServiceList(PrintWriter printWriter, String str, String[] strArr) {
        printWriter.print(str);
        printWriter.print(": ");
        if (strArr == null) {
            printWriter.println(PeripheralConstants.Result.NOT_AVAILABLE);
            return;
        }
        printWriter.print(strArr.length);
        printWriter.println(" services");
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            printWriter.print("  ");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(strArr[i]);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SystemUiServiceComponents configuration:");
        printWriter.print("vendor component: ");
        printWriter.println(this.context.getResources().getString(R.string.config_systemUIVendorServiceComponent));
        Set keySet = this.startables.keySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet, 10));
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            arrayList.add(((Class) it.next()).getSimpleName());
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        arrayList2.add(this.context.getResources().getString(R.string.config_systemUIVendorServiceComponent));
        dumpServiceList(printWriter, "global", (String[]) arrayList2.toArray(new String[0]));
        dumpServiceList(printWriter, "per-user", this.context.getResources().getStringArray(R.array.config_systemUIServiceComponentsPerUser));
    }
}
