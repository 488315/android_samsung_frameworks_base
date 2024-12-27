package com.android.server.companion;

import android.companion.AssociationInfo;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;

import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CompanionDeviceManagerService$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        CompanionDeviceManagerService companionDeviceManagerService =
                (CompanionDeviceManagerService) obj;
        companionDeviceManagerService.getClass();
        Slog.d("CDM_CompanionDeviceManagerService", "maybeGrantAutoRevokeExemptions()");
        PackageManager packageManager =
                companionDeviceManagerService.getContext().getPackageManager();
        for (int i :
                ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                        .getUserIds()) {
            SharedPreferences sharedPreferences =
                    companionDeviceManagerService
                            .getContext()
                            .getSharedPreferences(
                                    new File(
                                            Environment.getUserSystemDirectory(i),
                                            "companion_device_preferences.xml"),
                                    0);
            if (!sharedPreferences.getBoolean("auto_revoke_grants_done", false)) {
                try {
                    for (AssociationInfo associationInfo :
                            companionDeviceManagerService.mAssociationStore
                                    .getActiveAssociationsByUser(i)) {
                        try {
                            companionDeviceManagerService.exemptFromAutoRevoke(
                                    packageManager.getPackageUidAsUser(
                                            associationInfo.getPackageName(), i),
                                    associationInfo.getPackageName());
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.w(
                                    "CDM_CompanionDeviceManagerService",
                                    "Unknown companion package: "
                                            + associationInfo.getPackageName(),
                                    e);
                        }
                    }
                } finally {
                    sharedPreferences.edit().putBoolean("auto_revoke_grants_done", true).apply();
                }
            }
        }
    }
}
