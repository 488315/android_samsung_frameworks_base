package com.samsung.android.privacydashboard;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.SemUserInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.UserHandle;
import android.os.UserManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class PermissionAccessInformationWriter {
    private static final Uri PROVIDER_URI = Uri.parse("content://com.samsung.android.privacydashboard.provider/permissionAccessInformations");

    public void write(Context context, Iterator<PermissionAccessInformation> iterator) {
        long ident;
        List<ContentValues> informationList = new ArrayList<>();
        List<ContentValues> subUserinformationList = new ArrayList<>();
        UserManager um = (UserManager) context.getSystemService("user");
        while (iterator.hasNext()) {
            PermissionAccessInformation permissionAccessInformation = iterator.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("op", String.valueOf(permissionAccessInformation.getOp()));
            contentValues.put("uid", String.valueOf(permissionAccessInformation.getUid()));
            contentValues.put("package", permissionAccessInformation.getPackageName());
            contentValues.put("proxyPackage", permissionAccessInformation.getProxyPackageName());
            contentValues.put("proxyAttributionTag", permissionAccessInformation.getProxyAttributionTag());
            contentValues.put("isBackground", String.valueOf(permissionAccessInformation.isBackground()));
            contentValues.put("accessTime", String.valueOf(permissionAccessInformation.getAccessTime()));
            UserHandle appUserHandle = UserHandle.getUserHandleForUid(permissionAccessInformation.getUid());
            if (appUserHandle.semGetIdentifier() == 0 || um.isManagedProfile(appUserHandle.getIdentifier())) {
                informationList.add(contentValues);
            } else {
                subUserinformationList.add(contentValues);
            }
        }
        if (informationList.size() > 0) {
            ContentValues[] bulkArray = new ContentValues[informationList.size()];
            informationList.toArray(bulkArray);
            ident = Binder.clearCallingIdentity();
            try {
                try {
                    context.getContentResolver().bulkInsert(PROVIDER_URI, bulkArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
            }
        }
        if (subUserinformationList.size() > 0) {
            for (SemUserInfo userInfo : um.semGetUsers()) {
                if (userInfo.getUserHandle().semGetIdentifier() != 0 && !um.isManagedProfile(userInfo.getUserHandle().semGetIdentifier())) {
                    ContentValues[] bulkArray2 = new ContentValues[subUserinformationList.size()];
                    subUserinformationList.toArray(bulkArray2);
                    ident = Binder.clearCallingIdentity();
                    try {
                        try {
                            context.getContentResolver().bulkInsert(ContentProvider.maybeAddUserId(PROVIDER_URI, userInfo.getUserHandle().semGetIdentifier()), bulkArray2);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } finally {
                    }
                }
            }
        }
    }
}
