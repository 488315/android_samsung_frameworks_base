package com.android.server.enterprise.utils;

import android.content.Context;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SecContentProviderUtil {
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

    public static void notifyPolicyChangesAsUser(final Context context, String str, final int i) {
        final Uri parse = Uri.parse("content://com.sec.knox.provider/".concat(str));
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.utils.SecContentProviderUtil$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                Context context2 = context;
                context2.getContentResolver().notifyChange(parse, null, true, i);
            }
        });
    }
}
