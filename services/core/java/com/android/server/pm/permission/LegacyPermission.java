package com.android.server.pm.permission;

import android.content.pm.PermissionInfo;

import com.android.modules.utils.TypedXmlSerializer;

import libcore.util.EmptyArray;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LegacyPermission {
    public final int[] mGids;
    public final PermissionInfo mPermissionInfo;
    public final int mType;
    public final int mUid;

    public LegacyPermission(int i, String str, String str2) {
        PermissionInfo permissionInfo = new PermissionInfo();
        this.mPermissionInfo = permissionInfo;
        permissionInfo.name = str;
        permissionInfo.packageName = str2;
        permissionInfo.protectionLevel = 2;
        this.mType = i;
        this.mUid = 0;
        this.mGids = EmptyArray.INT;
    }

    public LegacyPermission(PermissionInfo permissionInfo, int i, int i2, int[] iArr) {
        this.mPermissionInfo = permissionInfo;
        this.mType = i;
        this.mUid = i2;
        this.mGids = iArr;
    }

    public final void write(TypedXmlSerializer typedXmlSerializer) {
        if (this.mPermissionInfo.packageName == null) {
            return;
        }
        typedXmlSerializer.startTag((String) null, "item");
        typedXmlSerializer.attribute((String) null, "name", this.mPermissionInfo.name);
        typedXmlSerializer.attribute((String) null, "package", this.mPermissionInfo.packageName);
        int i = this.mPermissionInfo.protectionLevel;
        if (i != 0) {
            typedXmlSerializer.attributeInt((String) null, "protection", i);
        }
        if (this.mType == 2) {
            typedXmlSerializer.attribute((String) null, "type", "dynamic");
            int i2 = this.mPermissionInfo.icon;
            if (i2 != 0) {
                typedXmlSerializer.attributeInt((String) null, KnoxCustomManagerService.ICON, i2);
            }
            CharSequence charSequence = this.mPermissionInfo.nonLocalizedLabel;
            if (charSequence != null) {
                typedXmlSerializer.attribute((String) null, "label", charSequence.toString());
            }
        }
        typedXmlSerializer.endTag((String) null, "item");
    }
}
