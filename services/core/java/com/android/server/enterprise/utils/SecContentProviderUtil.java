package com.android.server.enterprise.utils;

import android.content.Context;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class SecContentProviderUtil {
    public static void notifyPolicyChangesAsUser(final Context context, String str, final int i) {
        final Uri parse = Uri.parse("content://com.sec.knox.provider/" + str);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.utils.SecContentProviderUtil$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                SecContentProviderUtil.lambda$notifyPolicyChangesAsUser$0(context, parse, i);
            }
        });
    }

    public static /* synthetic */ void lambda$notifyPolicyChangesAsUser$0(Context context, Uri uri, int i) {
        context.getContentResolver().notifyChange(uri, null, true, i);
    }

    public static void notifyPolicyChangesAsUser(Context context, Uri uri, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                context.getContentResolver().notifyChange(uri, null, true, i);
            } catch (Exception e) {
                Log.e("SecContentProviderUtil", "notifyPolicyChangesAsUser() : failed to notify. userId = " + i + ", uri = " + uri, e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void notifyPolicyChangesAllUser(Context context, Uri uri) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = PackageManagerAdapter.getUsers(false).iterator();
                while (it.hasNext()) {
                    context.getContentResolver().notifyChange(uri, null, true, ((UserInfo) it.next()).id);
                }
            } catch (Exception e) {
                Log.e("SecContentProviderUtil", "notifyPolicyChangesAllUser() : failed to notify. uri = " + uri, e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
