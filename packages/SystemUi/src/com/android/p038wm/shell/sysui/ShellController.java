package com.android.p038wm.shell.sysui;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LogPrinter;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.common.ExternalInterfaceBinder;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.sysui.ShellCommandHandler;
import com.android.p038wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShellController {
    public final Context mContext;
    public Configuration mLastConfiguration;
    public final ShellExecutor mMainExecutor;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellInit mShellInit;
    public final ShellInterfaceImpl mImpl = new ShellInterfaceImpl(this, 0);
    public final CopyOnWriteArrayList mConfigChangeListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mKeyguardChangeListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mUserChangeListeners = new CopyOnWriteArrayList();
    public final ArrayMap mExternalInterfaceSuppliers = new ArrayMap();
    public final ArrayMap mExternalInterfaces = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShellInterfaceImpl implements ShellInterface {
        public /* synthetic */ ShellInterfaceImpl(ShellController shellController, int i) {
            this();
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void createExternalInterfaces(Bundle bundle) {
            try {
                ShellController.this.mMainExecutor.executeBlocking(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda0(this, bundle, 1));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to get Shell command in 2s", e);
            }
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void dump(PrintWriter printWriter) {
            try {
                ShellController.this.mMainExecutor.executeBlocking(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda0(this, printWriter, 3));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to dump the Shell in 2s", e);
            }
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final boolean handleCommand(final PrintWriter printWriter, final String[] strArr) {
            ShellController shellController = ShellController.this;
            try {
                final boolean[] zArr = new boolean[1];
                Log.d("ShellController", "handleCommand t state : " + Thread.currentThread().getState());
                shellController.mMainExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.sysui.ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        boolean z;
                        ShellController.ShellInterfaceImpl shellInterfaceImpl = ShellController.ShellInterfaceImpl.this;
                        boolean[] zArr2 = zArr;
                        String[] strArr2 = strArr;
                        PrintWriter printWriter2 = printWriter;
                        ShellCommandHandler shellCommandHandler = ShellController.this.mShellCommandHandler;
                        shellCommandHandler.getClass();
                        if (strArr2.length >= 2) {
                            z = true;
                            String str = strArr2[1];
                            boolean equals = str.toLowerCase().equals("help");
                            TreeMap treeMap = shellCommandHandler.mCommands;
                            if (equals) {
                                printWriter2.println("Window Manager Shell commands:");
                                for (String str2 : treeMap.keySet()) {
                                    printWriter2.println("  " + str2);
                                    ((ShellCommandHandler.ShellCommandActionHandler) treeMap.get(str2)).printShellCommandHelp(printWriter2, "    ");
                                }
                                printWriter2.println("  help");
                                printWriter2.println("      Print this help text.");
                                printWriter2.println("  <no arguments provided>");
                                printWriter2.println("    Dump all Window Manager Shell internal state");
                            } else if (treeMap.containsKey(str)) {
                                ((ShellCommandHandler.ShellCommandActionHandler) treeMap.get(strArr2[1])).onShellCommand(printWriter2, (String[]) Arrays.copyOfRange(strArr2, 2, strArr2.length));
                            }
                            zArr2[0] = z;
                        }
                        z = false;
                        zArr2[0] = z;
                    }
                });
                return zArr[0];
            } catch (InterruptedException unused) {
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Failed handleCommand, args="), Arrays.toString(strArr), "ShellController");
                Looper looper = ((HandlerExecutor) shellController.mMainExecutor).mHandler.getLooper();
                looper.dump(new LogPrinter(3, "ShellController"), "");
                StringBuilder sb = new StringBuilder();
                sb.append(looper.getThread().toString());
                sb.append('\n');
                for (StackTraceElement stackTraceElement : looper.getThread().getStackTrace()) {
                    sb.append("\tat ");
                    sb.append(stackTraceElement.toString());
                    sb.append('\n');
                }
                Log.d("ShellController", "stack=" + ((Object) sb));
                return false;
            }
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void onConfigurationChanged(Configuration configuration) {
            ((HandlerExecutor) ShellController.this.mMainExecutor).execute(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda0(this, configuration, 0));
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void onInit() {
            try {
                ShellController.this.mMainExecutor.executeBlocking(new ShellController$$ExternalSyntheticLambda0(this, 1));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to initialize the Shell in 2s", e);
            }
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void onKeyguardDismissAnimationFinished() {
            ((HandlerExecutor) ShellController.this.mMainExecutor).execute(new ShellController$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void onKeyguardVisibilityChanged(final boolean z, final boolean z2, final boolean z3) {
            ((HandlerExecutor) ShellController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.sysui.ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ShellController.ShellInterfaceImpl shellInterfaceImpl = ShellController.ShellInterfaceImpl.this;
                    ShellController.this.onKeyguardVisibilityChanged(z, z2, z3);
                }
            });
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void onUserChanged(final int i, final Context context) {
            ((HandlerExecutor) ShellController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.sysui.ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ShellController.ShellInterfaceImpl shellInterfaceImpl = ShellController.ShellInterfaceImpl.this;
                    ShellController.this.onUserChanged(i, context);
                }
            });
        }

        @Override // com.android.p038wm.shell.sysui.ShellInterface
        public final void onUserProfilesChanged(List list) {
            ((HandlerExecutor) ShellController.this.mMainExecutor).execute(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda0(this, list, 2));
        }

        private ShellInterfaceImpl() {
        }
    }

    public ShellController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mShellInit = shellInit;
        this.mShellCommandHandler = shellCommandHandler;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new ShellController$$ExternalSyntheticLambda0(this, 0), this);
    }

    public final void addConfigurationChangeListener(ConfigurationChangeListener configurationChangeListener) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mConfigChangeListeners;
        copyOnWriteArrayList.remove(configurationChangeListener);
        copyOnWriteArrayList.add(configurationChangeListener);
    }

    public final void addExternalInterface(String str, Supplier supplier, Object obj) {
        if (ShellProtoLogCache.WM_SHELL_INIT_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_INIT, 1861372954, 0, "Adding external interface from %s with key %s", obj.getClass().getSimpleName(), str);
        }
        ArrayMap arrayMap = this.mExternalInterfaceSuppliers;
        if (arrayMap.containsKey(str)) {
            throw new IllegalArgumentException("Supplier with same key already exists: ".concat(str));
        }
        arrayMap.put(str, supplier);
    }

    public void createExternalInterfaces(Bundle bundle) {
        ArrayMap arrayMap;
        int i = 0;
        int i2 = 0;
        while (true) {
            arrayMap = this.mExternalInterfaces;
            if (i2 >= arrayMap.size()) {
                break;
            }
            ((ExternalInterfaceBinder) arrayMap.valueAt(i2)).invalidate();
            i2++;
        }
        arrayMap.clear();
        while (true) {
            ArrayMap arrayMap2 = this.mExternalInterfaceSuppliers;
            if (i >= arrayMap2.size()) {
                return;
            }
            String str = (String) arrayMap2.keyAt(i);
            ExternalInterfaceBinder externalInterfaceBinder = (ExternalInterfaceBinder) ((Supplier) arrayMap2.valueAt(i)).get();
            arrayMap.put(str, externalInterfaceBinder);
            bundle.putBinder(str, externalInterfaceBinder.asBinder());
            i++;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = this.mLastConfiguration;
        if (configuration2 == null) {
            this.mLastConfiguration = new Configuration(configuration);
            if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 1481510239, 0, null, String.valueOf(configuration));
                return;
            }
            return;
        }
        int diff = configuration.diff(configuration2);
        if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -414557065, 0, null, String.valueOf(configuration));
        }
        if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -1967163262, 0, null, String.valueOf(Configuration.configurationDiffToString(diff)));
        }
        boolean z = ((1073741824 & diff) == 0 && (diff & 4096) == 0) ? false : true;
        boolean z2 = ((Integer.MIN_VALUE & diff) == 0 && (diff & 512) == 0) ? false : true;
        this.mLastConfiguration.updateFrom(configuration);
        Iterator it = this.mConfigChangeListeners.iterator();
        while (it.hasNext()) {
            ConfigurationChangeListener configurationChangeListener = (ConfigurationChangeListener) it.next();
            configurationChangeListener.onConfigurationChanged(configuration);
            if (z) {
                configurationChangeListener.onDensityOrFontScaleChanged$1();
            }
            if (z2) {
                configurationChangeListener.onThemeChanged();
            }
        }
    }

    public void onKeyguardDismissAnimationFinished() {
        if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 628675951, 0, null, null);
        }
        Iterator it = this.mKeyguardChangeListeners.iterator();
        while (it.hasNext()) {
            ((KeyguardChangeListener) it.next()).onKeyguardDismissAnimationFinished();
        }
    }

    public void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
        if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 1941251996, 63, null, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3));
        }
        Iterator it = this.mKeyguardChangeListeners.iterator();
        while (it.hasNext()) {
            ((KeyguardChangeListener) it.next()).onKeyguardVisibilityChanged(z, z3);
        }
    }

    public void onUserChanged(int i, Context context) {
        if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -1330260979, 1, null, Long.valueOf(i));
        }
        Iterator it = this.mUserChangeListeners.iterator();
        while (it.hasNext()) {
            ((UserChangeListener) it.next()).onUserChanged$1(i);
        }
    }

    public void onUserProfilesChanged(List<UserInfo> list) {
        if (ShellProtoLogCache.WM_SHELL_SYSUI_EVENTS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -186408060, 0, null, null);
        }
        Iterator it = this.mUserChangeListeners.iterator();
        while (it.hasNext()) {
            ((UserChangeListener) it.next()).getClass();
        }
    }
}
