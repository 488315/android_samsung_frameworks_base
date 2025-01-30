package com.android.systemui.statusbar.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.CommandQueue;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvStatusBar implements CoreStartable, CommandQueue.Callbacks {
    public final Lazy mAssistManagerLazy;
    public final CommandQueue mCommandQueue;
    public final Context mContext;

    public TvStatusBar(Context context, CommandQueue commandQueue, Lazy lazy) {
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mAssistManagerLazy = lazy;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showPictureInPictureMenu() {
        Intent intent = new Intent("com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU");
        Context context = this.mContext;
        context.sendBroadcast(intent.setPackage(context.getPackageName()), "com.android.systemui.permission.SELF");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        CommandQueue commandQueue = this.mCommandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        try {
            asInterface.registerStatusBar(commandQueue);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void startAssist(Bundle bundle) {
        ((AssistManager) this.mAssistManagerLazy.get()).startAssist(bundle);
    }
}
