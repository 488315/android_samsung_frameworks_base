package com.android.wm.shell.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.R;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiInstanceHelper implements ShellCommandHandler.ShellCommandActionHandler {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final PackageManager packageManager;
    public final ShellCommandHandler shellCommandHandler;
    public final String[] staticAppsSupportingMultiInstance;
    public final boolean supportsMultiInstanceProperty;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MultiInstanceHelper(Context context, PackageManager packageManager, ShellInit shellInit, ShellCommandHandler shellCommandHandler, boolean z) {
        this(context, packageManager, null, shellInit, shellCommandHandler, z, 4, null);
    }

    public static final boolean samePackage(int i, int i2, String str, String str2) {
        Companion.getClass();
        return str != null && str.equals(str2) && i == i2;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        if (printWriter == null || strArr == null || strArr.length == 0 || !Intrinsics.areEqual(strArr[0], "list")) {
            return false;
        }
        printWriter.println("Static allow list (for all users):");
        for (String str : this.staticAppsSupportingMultiInstance) {
            printWriter.println("  " + str);
        }
        List<PackageManager.Property> sortedWith = CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.plus((Iterable) this.packageManager.queryActivityProperty("android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI"), (Collection) this.packageManager.queryApplicationProperty("android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI")), new Comparator() { // from class: com.android.wm.shell.common.MultiInstanceHelper$dumpSupportedApps$appsWithProperty$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                String className;
                PackageManager.Property property = (PackageManager.Property) obj;
                PackageManager.Property property2 = (PackageManager.Property) obj2;
                if (!Intrinsics.areEqual(property != null ? property.getPackageName() : null, property2 != null ? property2.getPackageName() : null)) {
                    String packageName = property != null ? property.getPackageName() : null;
                    packageName.getClass();
                    className = property2 != null ? property2.getPackageName() : null;
                    className.getClass();
                    return packageName.compareTo(className);
                }
                if ((property != null ? property.getClassName() : null) != null) {
                    String className2 = property.getClassName();
                    className2.getClass();
                    className = property2 != null ? property2.getClassName() : null;
                    className.getClass();
                    return className2.compareTo(className);
                }
                if ((property2 != null ? property2.getClassName() : null) == null) {
                    return 0;
                }
                String className3 = property2.getClassName();
                className3.getClass();
                className = property != null ? property.getClassName() : null;
                className.getClass();
                return -className3.compareTo(className);
            }
        });
        if (sortedWith.isEmpty()) {
            return true;
        }
        printWriter.println("Apps (User " + this.context.getUserId() + "):");
        for (PackageManager.Property property : sortedWith) {
            if (property.isBoolean() && property.getBoolean()) {
                if (property.getClassName() != null) {
                    printWriter.println("  " + property.getPackageName() + "/" + property.getClassName());
                } else {
                    ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "  ", property.getPackageName());
                }
            }
        }
        return true;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        printWriter.println("    list");
        printWriter.println("       Lists all the packages that support the multiinstance property");
    }

    public final boolean supportsMultiInstanceSplit(int i, ComponentName componentName) {
        PackageManager.Property propertyAsUser;
        if (componentName != null && componentName.getPackageName() != null) {
            String packageName = componentName.getPackageName();
            for (String str : this.staticAppsSupportingMultiInstance) {
                if (Intrinsics.areEqual(str, packageName)) {
                    ProtoLog.v(ShellProtoLogGroup.WM_SHELL, "application=%s in allowlist supports multi-instance", new Object[]{packageName});
                    return true;
                }
            }
            if (!this.supportsMultiInstanceProperty) {
                return false;
            }
            try {
                propertyAsUser = this.packageManager.getPropertyAsUser("android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI", componentName.getPackageName(), componentName.getClassName(), i);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (propertyAsUser.isBoolean()) {
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL, "activity=%s supports multi-instance", new Object[]{componentName});
                return propertyAsUser.getBoolean();
            }
            ProtoLog.w(ShellProtoLogGroup.WM_SHELL, "Warning: property=%s for activity=%s has non-bool type=%d", new Object[]{"android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI", packageName, Integer.valueOf(propertyAsUser.getType())});
            try {
                PackageManager.Property propertyAsUser2 = this.packageManager.getPropertyAsUser("android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI", packageName, null, i);
                if (propertyAsUser2.isBoolean()) {
                    ProtoLog.v(ShellProtoLogGroup.WM_SHELL, "application=%s supports multi-instance", new Object[]{packageName});
                    return propertyAsUser2.getBoolean();
                }
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL, "Warning: property=%s for application=%s has non-bool type=%d", new Object[]{"android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI", packageName, Integer.valueOf(propertyAsUser2.getType())});
            } catch (PackageManager.NameNotFoundException unused2) {
            }
        }
        return false;
    }

    public MultiInstanceHelper(Context context, PackageManager packageManager, String[] strArr, ShellInit shellInit, ShellCommandHandler shellCommandHandler, boolean z) {
        this.context = context;
        this.packageManager = packageManager;
        this.staticAppsSupportingMultiInstance = strArr;
        this.shellCommandHandler = shellCommandHandler;
        this.supportsMultiInstanceProperty = z;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.MultiInstanceHelper.1
            @Override // java.lang.Runnable
            public final void run() {
                MultiInstanceHelper multiInstanceHelper = MultiInstanceHelper.this;
                multiInstanceHelper.shellCommandHandler.addCommandCallback("multi-instance", multiInstanceHelper, multiInstanceHelper);
            }
        }, this);
    }

    public /* synthetic */ MultiInstanceHelper(Context context, PackageManager packageManager, String[] strArr, ShellInit shellInit, ShellCommandHandler shellCommandHandler, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, packageManager, (i & 4) != 0 ? context.getResources().getStringArray(R.array.config_appsSupportMultiInstancesSplit) : strArr, shellInit, shellCommandHandler, z);
    }
}
