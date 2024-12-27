package com.android.server.autofill;

import android.app.IUriGrantsManager;
import android.app.UriGrantsManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.uri.UriPermissionOwner;
import com.android.server.uri.UriPermissionOwner.ExternalToken;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AutofillUriGrantsManager {
    public final int mSourceUid;
    public final int mSourceUserId;
    public final ActivityTaskManagerInternal mActivityTaskMgrInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
    public final IUriGrantsManager mUgm = UriGrantsManager.getService();

    public AutofillUriGrantsManager(int i) {
        this.mSourceUid = i;
        this.mSourceUserId = UserHandle.getUserId(i);
    }

    public final void grantUriPermissions(ComponentName componentName, IBinder iBinder, int i, ClipData clipData) {
        UriPermissionOwner.ExternalToken externalToken;
        UriPermissionOwner.ExternalToken externalToken2;
        UriPermissionOwner.ExternalToken externalToken3;
        int i2;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        int i3;
        String str6;
        String str7;
        int i4;
        String str8;
        IUriGrantsManager iUriGrantsManager;
        AutofillUriGrantsManager autofillUriGrantsManager = this;
        ClipData clipData2 = clipData;
        String packageName = componentName.getPackageName();
        ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) autofillUriGrantsManager.mActivityTaskMgrInternal;
        localService.getClass();
        ActivityTaskManagerService.enforceNotIsolatedCaller("getUriPermissionOwnerForActivity");
        WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    externalToken = null;
                } else {
                    UriPermissionOwner uriPermissionsLocked = isInRootTaskLocked.getUriPermissionsLocked();
                    if (uriPermissionsLocked.externalToken == null) {
                        uriPermissionsLocked.externalToken = uriPermissionsLocked.new ExternalToken();
                    }
                    externalToken = uriPermissionsLocked.externalToken;
                }
                externalToken2 = externalToken;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (externalToken2 == null) {
            Slog.w("AutofillUriGrantsManager", "Can't grant URI permissions, because the target activity token is invalid: clip=" + clipData2 + ", targetActivity=" + componentName + ", targetUserId=" + i + ", targetActivityToken=" + Integer.toHexString(iBinder.hashCode()));
            return;
        }
        int i5 = 0;
        while (i5 < clipData.getItemCount()) {
            Uri uri = clipData2.getItemAt(i5).getUri();
            if (uri == null || !"content".equals(uri.getScheme())) {
                externalToken3 = externalToken2;
                i2 = i5;
            } else {
                int userIdFromUri = ContentProvider.getUserIdFromUri(uri, autofillUriGrantsManager.mSourceUserId);
                boolean z = Helper.sVerbose;
                int i6 = autofillUriGrantsManager.mSourceUid;
                if (z) {
                    str = "Granting URI permissions failed: uri=";
                    StringBuilder sb = new StringBuilder("Granting URI permissions: uri=");
                    sb.append(uri);
                    sb.append(", sourceUid=");
                    sb.append(i6);
                    sb.append(", sourceUserId=");
                    AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(userIdFromUri, ", targetPkg=", packageName, ", targetUserId=", sb);
                    sb.append(i);
                    sb.append(", permissionOwner=");
                    sb.append(Integer.toHexString(externalToken2.hashCode()));
                    Slog.v("AutofillUriGrantsManager", sb.toString());
                } else {
                    str = "Granting URI permissions failed: uri=";
                }
                Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        iUriGrantsManager = autofillUriGrantsManager.mUgm;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (RemoteException e) {
                    e = e;
                    str2 = ", sourceUid=";
                    str3 = ", sourceUserId=";
                }
                try {
                    int i7 = autofillUriGrantsManager.mSourceUid;
                    str2 = ", sourceUid=";
                    UriPermissionOwner.ExternalToken externalToken4 = externalToken2;
                    str3 = ", sourceUserId=";
                    str4 = ", targetPkg=";
                    str5 = ", targetUserId=";
                    i2 = i5;
                    str8 = ", permissionOwner=";
                    i3 = userIdFromUri;
                    str6 = "AutofillUriGrantsManager";
                    externalToken3 = externalToken2;
                    str7 = str;
                    i4 = i6;
                    try {
                        iUriGrantsManager.grantUriPermissionFromOwner(externalToken4, i7, packageName, uriWithoutUserId, 1, userIdFromUri, i);
                    } catch (RemoteException e2) {
                        e = e2;
                        Slog.e(str6, str7 + uri + str2 + i4 + str3 + i3 + str4 + packageName + str5 + i + str8 + Integer.toHexString(externalToken3.hashCode()), e);
                        i5 = i2 + 1;
                        autofillUriGrantsManager = this;
                        clipData2 = clipData;
                        externalToken2 = externalToken3;
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    str3 = ", sourceUserId=";
                    str2 = ", sourceUid=";
                    str4 = ", targetPkg=";
                    str5 = ", targetUserId=";
                    i3 = userIdFromUri;
                    str6 = "AutofillUriGrantsManager";
                    externalToken3 = externalToken2;
                    i2 = i5;
                    str7 = str;
                    i4 = i6;
                    str8 = ", permissionOwner=";
                    Slog.e(str6, str7 + uri + str2 + i4 + str3 + i3 + str4 + packageName + str5 + i + str8 + Integer.toHexString(externalToken3.hashCode()), e);
                    i5 = i2 + 1;
                    autofillUriGrantsManager = this;
                    clipData2 = clipData;
                    externalToken2 = externalToken3;
                }
            }
            i5 = i2 + 1;
            autofillUriGrantsManager = this;
            clipData2 = clipData;
            externalToken2 = externalToken3;
        }
    }
}
