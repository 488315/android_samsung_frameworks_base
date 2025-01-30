package com.android.systemui.statusbar.notification.row.dagger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory */
/* loaded from: classes2.dex */
public final class C2895x3e2d0aca implements Provider {
    public final Provider contextProvider;
    public final Provider statusBarNotificationProvider;

    public C2895x3e2d0aca(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.statusBarNotificationProvider = provider2;
    }

    public static String provideAppName(Context context, StatusBarNotification statusBarNotification) {
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(statusBarNotification.getUser().getIdentifier(), context);
        String packageName = statusBarNotification.getPackageName();
        try {
            ApplicationInfo applicationInfo = packageManagerForUser.getApplicationInfo(packageName, 8704);
            if (applicationInfo != null) {
                packageName = String.valueOf(packageManagerForUser.getApplicationLabel(applicationInfo));
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        Preconditions.checkNotNullFromProvides(packageName);
        return packageName;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideAppName((Context) this.contextProvider.get(), (StatusBarNotification) this.statusBarNotificationProvider.get());
    }
}
