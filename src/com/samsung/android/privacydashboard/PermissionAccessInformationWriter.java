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

/* loaded from: classes6.dex */
public class PermissionAccessInformationWriter {
    private static final Uri PROVIDER_URI = Uri.parse("content://com.samsung.android.privacydashboard.provider/permissionAccessInformations");

    public void write(Context context, Iterator<PermissionAccessInformation> it) {
        long jClearCallingIdentity;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        UserManager userManager = (UserManager) context.getSystemService("user");
        while (it.hasNext()) {
            PermissionAccessInformation next = it.next();
            ContentValues contentValues = new ContentValues();
            contentValues.put("op", String.valueOf(next.getOp()));
            contentValues.put("uid", String.valueOf(next.getUid()));
            contentValues.put("package", next.getPackageName());
            contentValues.put("proxyPackage", next.getProxyPackageName());
            contentValues.put("proxyAttributionTag", next.getProxyAttributionTag());
            contentValues.put("isBackground", String.valueOf(next.isBackground()));
            contentValues.put("accessTime", String.valueOf(next.getAccessTime()));
            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(next.getUid());
            if (userHandleForUid.semGetIdentifier() == 0 || userManager.isManagedProfile(userHandleForUid.getIdentifier()) || userManager.isPrivateProfile()) {
                arrayList.add(contentValues);
            } else {
                arrayList2.add(contentValues);
            }
        }
        if (arrayList.size() > 0) {
            ContentValues[] contentValuesArr = new ContentValues[arrayList.size()];
            arrayList.toArray(contentValuesArr);
            jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    context.getContentResolver().bulkInsert(PROVIDER_URI, contentValuesArr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
            }
        }
        if (arrayList2.size() > 0) {
            for (SemUserInfo semUserInfo : userManager.semGetUsers()) {
                if (semUserInfo.getUserHandle().semGetIdentifier() != 0 && !userManager.isManagedProfile(semUserInfo.getUserHandle().semGetIdentifier()) && !userManager.isPrivateProfile()) {
                    ContentValues[] contentValuesArr2 = new ContentValues[arrayList2.size()];
                    arrayList2.toArray(contentValuesArr2);
                    jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            context.getContentResolver().bulkInsert(ContentProvider.maybeAddUserId(PROVIDER_URI, semUserInfo.getUserHandle().semGetIdentifier()), contentValuesArr2);
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
