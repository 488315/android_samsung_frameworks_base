package com.android.systemui.shortcut;

import android.content.Context;
import android.os.RemoteException;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.systemui.CoreStartable;
import com.android.systemui.shortcut.ShortcutKeyServiceProxy;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.Optional;

public final class ShortcutKeyDispatcher implements CoreStartable, ShortcutKeyServiceProxy.Callbacks {
    public final Context mContext;
    public final Optional mSplitScreenOptional;
    public final ShortcutKeyServiceProxy mShortcutKeyServiceProxy = new ShortcutKeyServiceProxy(this);
    public final IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();

    public ShortcutKeyDispatcher(Context context, Optional<SplitScreenController> optional) {
        this.mContext = context;
        this.mSplitScreenOptional = optional;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        try {
            this.mWindowManagerService.registerShortcutKey(281474976710727L, this.mShortcutKeyServiceProxy);
        } catch (RemoteException unused) {
        }
        try {
            this.mWindowManagerService.registerShortcutKey(281474976710728L, this.mShortcutKeyServiceProxy);
        } catch (RemoteException unused2) {
        }
    }
}
