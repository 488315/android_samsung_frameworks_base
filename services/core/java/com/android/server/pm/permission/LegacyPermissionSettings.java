package com.android.server.pm.permission;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.DumpState;
import com.android.server.pm.PackageManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class LegacyPermissionSettings {
    public final Object mLock;
    public final ArrayMap mPermissions = new ArrayMap();
    public final ArrayMap mPermissionTrees = new ArrayMap();

    public LegacyPermissionSettings(Object obj) {
        this.mLock = obj;
    }

    public List getPermissions() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mPermissions.values());
        }
        return arrayList;
    }

    public List getPermissionTrees() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mPermissionTrees.values());
        }
        return arrayList;
    }

    public void replacePermissions(List list) {
        synchronized (this.mLock) {
            this.mPermissions.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                LegacyPermission legacyPermission = (LegacyPermission) list.get(i);
                this.mPermissions.put(legacyPermission.getPermissionInfo().name, legacyPermission);
            }
        }
    }

    public void replacePermissionTrees(List list) {
        synchronized (this.mLock) {
            this.mPermissionTrees.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                LegacyPermission legacyPermission = (LegacyPermission) list.get(i);
                this.mPermissionTrees.put(legacyPermission.getPermissionInfo().name, legacyPermission);
            }
        }
    }

    public void readPermissions(TypedXmlPullParser typedXmlPullParser) {
        synchronized (this.mLock) {
            readPermissions(this.mPermissions, typedXmlPullParser);
        }
    }

    public void readPermissionTrees(TypedXmlPullParser typedXmlPullParser) {
        synchronized (this.mLock) {
            readPermissions(this.mPermissionTrees, typedXmlPullParser);
        }
    }

    public static void readPermissions(ArrayMap arrayMap, TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (!LegacyPermission.read(arrayMap, typedXmlPullParser)) {
                    PackageManagerService.reportSettingsProblem(5, "Unknown element reading permissions: " + typedXmlPullParser.getName() + " at " + typedXmlPullParser.getPositionDescription());
                }
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public void writePermissions(TypedXmlSerializer typedXmlSerializer) {
        synchronized (this.mLock) {
            Iterator it = this.mPermissions.values().iterator();
            while (it.hasNext()) {
                ((LegacyPermission) it.next()).write(typedXmlSerializer);
            }
        }
    }

    public void writePermissionTrees(TypedXmlSerializer typedXmlSerializer) {
        synchronized (this.mLock) {
            Iterator it = this.mPermissionTrees.values().iterator();
            while (it.hasNext()) {
                ((LegacyPermission) it.next()).write(typedXmlSerializer);
            }
        }
    }

    public static void dumpPermissions(PrintWriter printWriter, String str, ArraySet arraySet, List list, Map map, boolean z, DumpState dumpState) {
        int size = list.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            z2 = ((LegacyPermission) list.get(i)).dump(printWriter, str, arraySet, z, z2, dumpState);
        }
        if (str == null && arraySet == null) {
            boolean z3 = true;
            for (Map.Entry entry : map.entrySet()) {
                if (z3) {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("AppOp Permissions:");
                    z3 = false;
                }
                printWriter.print("  AppOp Permission ");
                printWriter.print((String) entry.getKey());
                printWriter.println(com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                for (String str2 : (Set) entry.getValue()) {
                    printWriter.print("    ");
                    printWriter.println(str2);
                }
            }
        }
    }
}
