package com.android.wm.shell.sysui;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.SurfaceControlRegistry;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ShellController {
    public final Context mContext;
    public final DisplayInsetsController mDisplayInsetsController;
    public Configuration mLastConfiguration;
    public final ShellExecutor mMainExecutor;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellInit mShellInit;
    public final ShellInterfaceImpl mImpl = new ShellInterfaceImpl(this, 0);
    public final CopyOnWriteArrayList mConfigChangeListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mKeyguardChangeListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mUserChangeListeners = new CopyOnWriteArrayList();
    public final ConcurrentHashMap mDisplayImeChangeListeners = new ConcurrentHashMap();
    public final ArrayMap mExternalInterfaceSuppliers = new ArrayMap();
    public final ArrayMap mExternalInterfaces = new ArrayMap();
    public final AnonymousClass1 mInsetsChangeListener = new DisplayInsetsController.OnInsetsChangedListener() { // from class: com.android.wm.shell.sysui.ShellController.1
        public InsetsState mInsetsState = new InsetsState();

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            InsetsState insetsState2 = this.mInsetsState;
            if (insetsState2 == insetsState) {
                return;
            }
            int i = InsetsSource.ID_IME;
            InsetsSource peekSource = insetsState2.peekSource(i);
            boolean z = false;
            boolean z2 = peekSource != null && peekSource.isVisible();
            Rect frame = z2 ? peekSource.getFrame() : null;
            InsetsSource peekSource2 = insetsState.peekSource(i);
            if (peekSource2 != null && peekSource2.isVisible()) {
                z = true;
            }
            Rect frame2 = z ? peekSource2.getFrame() : null;
            ShellController shellController = ShellController.this;
            if (z2 != z) {
                shellController.onImeVisibilityChanged(z);
            }
            if (frame2 != null && !frame2.equals(frame)) {
                shellController.onImeBoundsChanged(frame2);
            }
            this.mInsetsState = insetsState;
        }
    };
    public final AnonymousClass2 mDumpCommandHandler = new ShellCommandHandler.ShellCommandActionHandler() { // from class: com.android.wm.shell.sysui.ShellController.2
        @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
        public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
            ShellController.m3264$$Nest$mhandleDump(ShellController.this, printWriter);
            return true;
        }

        @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
        public final void printShellCommandHelp(PrintWriter printWriter, String str) {
            printWriter.println("    Dump all Window Manager Shell internal state");
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ShellInterfaceImpl implements ShellInterface {
        public /* synthetic */ ShellInterfaceImpl(ShellController shellController, int i) {
            this();
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void createExternalInterfaces(Bundle bundle) {
            try {
                ShellController.this.mMainExecutor.executeBlocking(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2(this, bundle, 1));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to get Shell command in 2s", e);
            }
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void dump(PrintWriter printWriter) {
            try {
                ShellController.this.mMainExecutor.executeBlocking(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2(this, printWriter, 0));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to dump the Shell in 2s", e);
            }
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final boolean handleCommand(final PrintWriter printWriter, final String[] strArr) {
            try {
                final boolean[] zArr = new boolean[1];
                ShellController.this.mMainExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.sysui.ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda1
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
                            if (str.toLowerCase().equals("help")) {
                                printWriter2.println("Window Manager Shell commands:");
                                for (String str2 : shellCommandHandler.mCommands.keySet()) {
                                    ActionReceiver$$ExternalSyntheticOutline0.m(printWriter2, "  ", str2);
                                    ((ShellCommandHandler.ShellCommandActionHandler) shellCommandHandler.mCommands.get(str2)).printShellCommandHelp(printWriter2, "    ");
                                }
                                printWriter2.println("  help");
                                printWriter2.println("      Print this help text.");
                            } else if (shellCommandHandler.mCommands.containsKey(str)) {
                                ((ShellCommandHandler.ShellCommandActionHandler) shellCommandHandler.mCommands.get(strArr2[1])).onShellCommand(printWriter2, (String[]) Arrays.copyOfRange(strArr2, 2, strArr2.length));
                            }
                            zArr2[0] = z;
                        }
                        z = false;
                        zArr2[0] = z;
                    }
                });
                return zArr[0];
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to handle Shell command in 2s", e);
            }
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void onConfigurationChanged(Configuration configuration) {
            ShellController.this.mMainExecutor.execute(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2(this, configuration, 2));
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void onInit() {
            ShellController shellController = ShellController.this;
            shellController.mMainExecutor.execute(new ShellController$$ExternalSyntheticLambda2(shellController, 3));
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void onKeyguardDismissAnimationFinished() {
            ShellController.this.mMainExecutor.execute(new ShellController$$ExternalSyntheticLambda2(this, 4));
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void onKeyguardVisibilityChanged(final boolean z, final boolean z2, final boolean z3) {
            ShellController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.sysui.ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ShellController.ShellInterfaceImpl shellInterfaceImpl = ShellController.ShellInterfaceImpl.this;
                    ShellController.this.onKeyguardVisibilityChanged(z, z2, z3);
                }
            });
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void onUserChanged(final int i, final Context context) {
            ShellController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.sysui.ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ShellController.ShellInterfaceImpl shellInterfaceImpl = ShellController.ShellInterfaceImpl.this;
                    ShellController.this.onUserChanged(i, context);
                }
            });
        }

        @Override // com.android.wm.shell.sysui.ShellInterface
        public final void onUserProfilesChanged(List list) {
            ShellController.this.mMainExecutor.execute(new ShellController$ShellInterfaceImpl$$ExternalSyntheticLambda2(this, list, 3));
        }

        private ShellInterfaceImpl() {
        }
    }

    /* renamed from: -$$Nest$mhandleDump, reason: not valid java name */
    public static void m3264$$Nest$mhandleDump(ShellController shellController, PrintWriter printWriter) {
        ShellCommandHandler shellCommandHandler = shellController.mShellCommandHandler;
        Iterator it = shellCommandHandler.mDumpables.keySet().iterator();
        while (it.hasNext()) {
            ((BiConsumer) shellCommandHandler.mDumpables.get((String) it.next())).accept(printWriter, "");
            printWriter.println();
        }
        SurfaceControlRegistry.dump(100, false, printWriter);
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.wm.shell.sysui.ShellController$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.wm.shell.sysui.ShellController$2] */
    public ShellController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, DisplayInsetsController displayInsetsController, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mShellInit = shellInit;
        this.mShellCommandHandler = shellCommandHandler;
        this.mDisplayInsetsController = displayInsetsController;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new ShellController$$ExternalSyntheticLambda2(this, 0), this);
    }

    public final void addConfigurationChangeListener(ConfigurationChangeListener configurationChangeListener) {
        this.mConfigChangeListeners.remove(configurationChangeListener);
        this.mConfigChangeListeners.add(configurationChangeListener);
    }

    public final void addExternalInterface(String str, Supplier supplier, Object obj) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_INIT_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_INIT, 1169202424525373405L, 0, obj.getClass().getSimpleName(), str);
        }
        if (this.mExternalInterfaceSuppliers.containsKey(str)) {
            throw new IllegalArgumentException("Supplier with same key already exists: ".concat(str));
        }
        this.mExternalInterfaceSuppliers.put(str, supplier);
    }

    public final void addKeyguardChangeListener(KeyguardChangeListener keyguardChangeListener) {
        this.mKeyguardChangeListeners.remove(keyguardChangeListener);
        this.mKeyguardChangeListeners.add(keyguardChangeListener);
    }

    public final void addUserChangeListener(UserChangeListener userChangeListener) {
        this.mUserChangeListeners.remove(userChangeListener);
        this.mUserChangeListeners.add(userChangeListener);
    }

    public void createExternalInterfaces(Bundle bundle) {
        for (int i = 0; i < this.mExternalInterfaces.size(); i++) {
            ((ExternalInterfaceBinder) this.mExternalInterfaces.valueAt(i)).invalidate();
        }
        this.mExternalInterfaces.clear();
        for (int i2 = 0; i2 < this.mExternalInterfaceSuppliers.size(); i2++) {
            String str = (String) this.mExternalInterfaceSuppliers.keyAt(i2);
            ExternalInterfaceBinder externalInterfaceBinder = (ExternalInterfaceBinder) ((Supplier) this.mExternalInterfaceSuppliers.valueAt(i2)).get();
            this.mExternalInterfaces.put(str, externalInterfaceBinder);
            bundle.putBinder(str, externalInterfaceBinder.asBinder());
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        Configuration configuration2 = this.mLastConfiguration;
        if (configuration2 == null) {
            this.mLastConfiguration = new Configuration(configuration);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -3140463631940501752L, 0, String.valueOf(configuration));
                return;
            }
            return;
        }
        int diff = configuration.diff(configuration2);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 5422673531719793357L, 0, String.valueOf(configuration));
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -308891106308048671L, 0, String.valueOf(Configuration.configurationDiffToString(diff)));
        }
        boolean z = ((1073741824 & diff) == 0 && (diff & 4096) == 0) ? false : true;
        boolean z2 = ((Integer.MIN_VALUE & diff) == 0 && (diff & 512) == 0) ? false : true;
        if ((diff & 4) == 0) {
            int i = diff & 8192;
        }
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

    public void onImeBoundsChanged(final Rect rect) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 6711228585917822804L, 0, null);
        }
        this.mDisplayImeChangeListeners.forEach(new BiConsumer() { // from class: com.android.wm.shell.sysui.ShellController$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ShellController shellController = ShellController.this;
                Rect rect2 = rect;
                if (obj != null) {
                    throw new ClassCastException();
                }
                ((Executor) obj2).execute(new ShellController$$ExternalSyntheticLambda2(shellController, rect2));
            }
        });
    }

    public void onImeVisibilityChanged(final boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -4536758676284752066L, 3, Boolean.valueOf(z));
        }
        this.mDisplayImeChangeListeners.forEach(new BiConsumer() { // from class: com.android.wm.shell.sysui.ShellController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ShellController shellController = ShellController.this;
                boolean z2 = z;
                if (obj != null) {
                    throw new ClassCastException();
                }
                ((Executor) obj2).execute(new ShellController$$ExternalSyntheticLambda2(shellController, z2));
            }
        });
    }

    public void onKeyguardDismissAnimationFinished() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 5111757128463758136L, 0, null);
        }
        Iterator it = this.mKeyguardChangeListeners.iterator();
        while (it.hasNext()) {
            ((KeyguardChangeListener) it.next()).onKeyguardDismissAnimationFinished();
        }
    }

    public void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -1112500327174164343L, 63, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3));
        }
        Iterator it = this.mKeyguardChangeListeners.iterator();
        while (it.hasNext()) {
            ((KeyguardChangeListener) it.next()).onKeyguardVisibilityChanged(z, z2, z3);
        }
    }

    public void onUserChanged(int i, Context context) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, 8165881968506254140L, 1, Long.valueOf(i));
        }
        Iterator it = this.mUserChangeListeners.iterator();
        while (it.hasNext()) {
            ((UserChangeListener) it.next()).onUserChanged(i, context);
        }
    }

    public void onUserProfilesChanged(List<UserInfo> list) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SYSUI_EVENTS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SYSUI_EVENTS, -4504001331358256160L, 0, null);
        }
        Iterator it = this.mUserChangeListeners.iterator();
        while (it.hasNext()) {
            ((UserChangeListener) it.next()).onUserProfilesChanged(list);
        }
    }
}
