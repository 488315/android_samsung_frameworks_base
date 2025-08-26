package android.telecom;

import android.app.ActivityManager;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class DefaultDialerManager {
    private static final String TAG = "DefaultDialerManager";

    public static boolean setDefaultDialerApplication(Context context, String str) {
        return setDefaultDialerApplication(context, str, ActivityManager.getCurrentUser());
    }

    public static boolean setDefaultDialerApplication(Context context, String str, int i) {
        String str2;
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                final CompletableFuture completableFuture = new CompletableFuture();
                str2 = str;
                try {
                    ((RoleManager) context.getSystemService(RoleManager.class)).addRoleHolderAsUser("android.app.role.DIALER", str2, 0, UserHandle.of(i), AsyncTask.THREAD_POOL_EXECUTOR, new Consumer() { // from class: android.telecom.DefaultDialerManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DefaultDialerManager.lambda$setDefaultDialerApplication$0(completableFuture, (Boolean) obj);
                        }
                    });
                    completableFuture.get(5L, TimeUnit.SECONDS);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    return true;
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e = e;
                    Slog.e(TAG, "Failed to set default dialer to " + str2 + " for user " + i, e);
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    return false;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                throw th;
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e2) {
            e = e2;
            str2 = str;
        }
    }

    static /* synthetic */ void lambda$setDefaultDialerApplication$0(CompletableFuture completableFuture, Boolean bool) {
        if (bool.booleanValue()) {
            completableFuture.complete(null);
        } else {
            completableFuture.completeExceptionally(new RuntimeException());
        }
    }

    public static String getDefaultDialerApplication(Context context) {
        return getDefaultDialerApplication(context, context.getUserId());
    }

    public static String getDefaultDialerApplication(Context context, int i) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (String) CollectionUtils.firstOrNull(((RoleManager) context.getSystemService(RoleManager.class)).getRoleHoldersAsUser("android.app.role.DIALER", UserHandle.of(i)));
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static List<String> getInstalledDialerApplications(Context context, int i) {
        List<ResolveInfo> listQueryIntentActivitiesAsUser = context.getPackageManager().queryIntentActivitiesAsUser(new Intent(Intent.ACTION_DIAL), 0, i);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : listQueryIntentActivitiesAsUser) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && !arrayList.contains(activityInfo.packageName) && resolveInfo.targetUserId == -2) {
                arrayList.add(activityInfo.packageName);
            }
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.fromParts(PhoneAccount.SCHEME_TEL, "", null));
        return filterByIntent(context, arrayList, intent, i);
    }

    public static List<String> getInstalledDialerApplications(Context context) {
        return getInstalledDialerApplications(context, Process.myUserHandle().getIdentifier());
    }

    public static boolean isDefaultOrSystemDialer(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        TelecomManager telecomManager = getTelecomManager(context);
        return str.equals(telecomManager.getDefaultDialerPackage()) || str.equals(telecomManager.getSystemDialerPackage());
    }

    private static List<String> filterByIntent(Context context, List<String> list, Intent intent, int i) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> listQueryIntentActivitiesAsUser = context.getPackageManager().queryIntentActivitiesAsUser(intent, 0, i);
        int size = listQueryIntentActivitiesAsUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            ActivityInfo activityInfo = listQueryIntentActivitiesAsUser.get(i2).activityInfo;
            if (activityInfo != null && list.contains(activityInfo.packageName) && !arrayList.contains(activityInfo.packageName)) {
                arrayList.add(activityInfo.packageName);
            }
        }
        return arrayList;
    }

    private static TelecomManager getTelecomManager(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }
}
