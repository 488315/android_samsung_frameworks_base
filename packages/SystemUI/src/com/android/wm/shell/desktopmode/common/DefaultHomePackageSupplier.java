package com.android.wm.shell.desktopmode.common;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.android.wm.shell.sysui.ShellInit;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class DefaultHomePackageSupplier extends BroadcastReceiver implements Supplier {
    public final Context context;
    public String defaultHomePackage;
    public boolean isSetupWizard;
    public final Handler mainHandler;

    public DefaultHomePackageSupplier(Context context, ShellInit shellInit, Handler handler) {
        this.context = context;
        this.mainHandler = handler;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.common.DefaultHomePackageSupplier.1
            @Override // java.lang.Runnable
            public final void run() {
                DefaultHomePackageSupplier defaultHomePackageSupplier = DefaultHomePackageSupplier.this;
                defaultHomePackageSupplier.context.registerReceiver(defaultHomePackageSupplier, new IntentFilter("android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED"), null, defaultHomePackageSupplier.mainHandler);
            }
        }, this);
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.isSetupWizard) {
            return null;
        }
        String str = this.defaultHomePackage;
        return str == null ? updateDefaultHomePackage() : str;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        updateDefaultHomePackage();
    }

    public final String updateDefaultHomePackage() {
        ComponentName homeActivities = this.context.getPackageManager().getHomeActivities(new ArrayList());
        String packageName = homeActivities != null ? homeActivities.getPackageName() : null;
        this.defaultHomePackage = packageName;
        this.isSetupWizard = (packageName == null || this.context.getPackageManager().resolveActivity(new Intent().setPackage(this.defaultHomePackage).addCategory("android.intent.category.SETUP_WIZARD"), 1048576) == null) ? false : true;
        return this.defaultHomePackage;
    }
}
