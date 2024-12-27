package com.android.server.pm.permission;

import android.content.pm.PermissionInfo;
import android.util.ArrayMap;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.CrossProfileIntentFilter$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.PackageManagerTracedLock;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LegacyPermissionSettings {
    public final ArrayMap mPermissions = new ArrayMap();
    public final ArrayMap mPermissionTrees = new ArrayMap();
    public final PackageManagerTracedLock mLock = new PackageManagerTracedLock(null);

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
                if (typedXmlPullParser.getName().equals("item")) {
                    String attributeValue =
                            typedXmlPullParser.getAttributeValue((String) null, "name");
                    String attributeValue2 =
                            typedXmlPullParser.getAttributeValue((String) null, "package");
                    String attributeValue3 =
                            typedXmlPullParser.getAttributeValue((String) null, "type");
                    if (attributeValue == null || attributeValue2 == null) {
                        String m =
                                CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                                        typedXmlPullParser,
                                        new StringBuilder(
                                                "Error in package manager settings: permissions has"
                                                    + " no name at "));
                        boolean z = PackageManagerService.DEBUG_COMPRESSION;
                        PackageManagerServiceUtils.logCriticalInfo(5, m);
                    } else {
                        boolean equals = "dynamic".equals(attributeValue3);
                        LegacyPermission legacyPermission =
                                (LegacyPermission) arrayMap.get(attributeValue);
                        if (legacyPermission == null || legacyPermission.mType != 1) {
                            legacyPermission =
                                    new LegacyPermission(
                                            equals ? 2 : 0,
                                            attributeValue.intern(),
                                            attributeValue2);
                        }
                        legacyPermission.mPermissionInfo.protectionLevel =
                                typedXmlPullParser.getAttributeInt((String) null, "protection", 0);
                        PermissionInfo permissionInfo = legacyPermission.mPermissionInfo;
                        permissionInfo.protectionLevel =
                                PermissionInfo.fixProtectionLevel(permissionInfo.protectionLevel);
                        if (equals) {
                            legacyPermission.mPermissionInfo.icon =
                                    typedXmlPullParser.getAttributeInt(
                                            (String) null, KnoxCustomManagerService.ICON, 0);
                            legacyPermission.mPermissionInfo.nonLocalizedLabel =
                                    typedXmlPullParser.getAttributeValue((String) null, "label");
                        }
                        arrayMap.put(legacyPermission.mPermissionInfo.name, legacyPermission);
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
                String str =
                        "Unknown element reading permissions: "
                                + typedXmlPullParser.getName()
                                + " at "
                                + typedXmlPullParser.getPositionDescription();
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                PackageManagerServiceUtils.logCriticalInfo(5, str);
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public final List getPermissionTrees() {
        ArrayList arrayList;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                arrayList = new ArrayList(this.mPermissionTrees.values());
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return arrayList;
    }

    public final List getPermissions() {
        ArrayList arrayList;
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                arrayList = new ArrayList(this.mPermissions.values());
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return arrayList;
    }

    public final void readPermissionTrees(TypedXmlPullParser typedXmlPullParser) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                readPermissions(this.mPermissionTrees, typedXmlPullParser);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public final void readPermissions(TypedXmlPullParser typedXmlPullParser) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                readPermissions(this.mPermissions, typedXmlPullParser);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public final void replacePermissionTrees(List list) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mPermissionTrees.clear();
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    LegacyPermission legacyPermission = (LegacyPermission) arrayList.get(i);
                    this.mPermissionTrees.put(
                            legacyPermission.mPermissionInfo.name, legacyPermission);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void replacePermissions(List list) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mPermissions.clear();
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    LegacyPermission legacyPermission = (LegacyPermission) arrayList.get(i);
                    this.mPermissions.put(legacyPermission.mPermissionInfo.name, legacyPermission);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void writePermissionTrees(TypedXmlSerializer typedXmlSerializer) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Iterator it = this.mPermissionTrees.values().iterator();
                while (it.hasNext()) {
                    ((LegacyPermission) it.next()).write(typedXmlSerializer);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }

    public final void writePermissions(TypedXmlSerializer typedXmlSerializer) {
        PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Iterator it = this.mPermissions.values().iterator();
                while (it.hasNext()) {
                    ((LegacyPermission) it.next()).write(typedXmlSerializer);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
    }
}
