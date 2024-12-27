package com.android.systemui.controls.controller;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.internal.content.PackageMonitor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.Intrinsics;

public final class PackageUpdateMonitor extends PackageMonitor {
    public final Handler bgHandler;
    public final Runnable callback;
    public final Context context;
    public final AtomicBoolean monitoring = new AtomicBoolean(false);
    public final String packageName;
    public final UserHandle user;

    public interface Factory {
        PackageUpdateMonitor create(UserHandle userHandle, String str, Runnable runnable);
    }

    public PackageUpdateMonitor(UserHandle userHandle, String str, Runnable runnable, Handler handler, Context context) {
        this.user = userHandle;
        this.packageName = str;
        this.callback = runnable;
        this.bgHandler = handler;
        this.context = context;
    }

    public final void onPackageUpdateFinished(String str, int i) {
        super.onPackageUpdateFinished(str, i);
        if (Intrinsics.areEqual(str, this.packageName) && Intrinsics.areEqual(UserHandle.getUserHandleForUid(i), this.user)) {
            this.callback.run();
        }
    }
}
