package com.android.server.permission.access.permission;

import android.content.pm.PermissionInfo;

import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.immutable.IndexedMap;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppIdPermissionPersistence {
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0109, code lost:

       r1 = r13.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x010d, code lost:

       if (r1 == 1) goto L79;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x010f, code lost:

       if (r1 == 2) goto L80;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0111, code lost:

       if (r1 == 3) goto L81;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0137, code lost:

       r13 = r15.map.size() - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x013f, code lost:

       if ((-1) >= r13) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0141, code lost:

       r1 = r15.map.keyAt(r13);
       r2 = (com.android.server.permission.access.permission.Permission) r15.map.valueAt(r13);
       r1 = (java.lang.String) r1;
       r1 = r2.permissionInfo.packageName;
       r3 = r14.getExternalState();
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x015f, code lost:

       if (r3.packageStates.containsKey(r1) != false) goto L98;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0167, code lost:

       if (r3.disabledSystemPackageStates.containsKey(r1) != false) goto L99;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0169, code lost:

       android.util.Slog.w("AppIdPermissionPersistence", com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0.m("Dropping permission ", r2.permissionInfo.name, " from unknown package ", r1, " when parsing permissions"));
       r15.map.removeAt(r13);
       r0.writeMode = java.lang.Math.max(r0.writeMode, 1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0185, code lost:

       r13 = r13 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0188, code lost:

       return;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void parsePermissions(
            com.android.modules.utils.BinaryXmlPullParser r13,
            com.android.server.permission.access.MutableAccessState r14,
            boolean r15) {
        /*
            Method dump skipped, instructions count: 393
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.AppIdPermissionPersistence.parsePermissions(com.android.modules.utils.BinaryXmlPullParser,"
                    + " com.android.server.permission.access.MutableAccessState, boolean):void");
    }

    public static void serializePermissions(
            BinaryXmlSerializer binaryXmlSerializer, String str, IndexedMap indexedMap) {
        String obj;
        binaryXmlSerializer.startTag((String) null, str);
        int size = indexedMap.map.size();
        for (int i = 0; i < size; i++) {
            Object keyAt = indexedMap.map.keyAt(i);
            Permission permission = (Permission) indexedMap.map.valueAt(i);
            binaryXmlSerializer.startTag((String) null, "permission");
            binaryXmlSerializer.attributeInterned(
                    (String) null, "name", permission.permissionInfo.name);
            binaryXmlSerializer.attributeInterned(
                    (String) null, "packageName", permission.permissionInfo.packageName);
            binaryXmlSerializer.attributeIntHex(
                    (String) null, "protectionLevel", permission.permissionInfo.protectionLevel);
            int i2 = permission.type;
            binaryXmlSerializer.attributeInt((String) null, "type", i2);
            if (i2 == 2) {
                PermissionInfo permissionInfo = permission.permissionInfo;
                int i3 = permissionInfo.icon;
                if (i3 != 0) {
                    binaryXmlSerializer.attributeIntHex(
                            (String) null, KnoxCustomManagerService.ICON, i3);
                }
                CharSequence charSequence = permissionInfo.nonLocalizedLabel;
                if (charSequence != null && (obj = charSequence.toString()) != null) {
                    binaryXmlSerializer.attribute((String) null, "label", obj);
                }
            }
            binaryXmlSerializer.endTag((String) null, "permission");
        }
        binaryXmlSerializer.endTag((String) null, str);
    }
}
