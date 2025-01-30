package com.android.systemui.qs.bar.soundcraft.interfaces.wearable.requester;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BudsPluginServiceRequester {
    public final String budsPluginPackageName;
    public final Context context;
    public Messenger messenger;
    public final BudsPluginServiceRequester$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$serviceConnection$1
        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            super.onBindingDied(componentName);
            Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "onBindingDied");
            BudsPluginServiceRequester.this.messenger = null;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "onServiceConnected");
            BudsPluginServiceRequester.this.messenger = new Messenger(iBinder);
            BudsPluginServiceRequester.this.execute();
            BudsPluginServiceRequester budsPluginServiceRequester = BudsPluginServiceRequester.this;
            try {
                int i = Result.$r8$clinit;
                budsPluginServiceRequester.context.unbindService(this);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                new Result.Failure(th);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "onServiceDisconnected");
            BudsPluginServiceRequester.this.messenger = null;
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.bar.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$serviceConnection$1] */
    public BudsPluginServiceRequester(Context context, String str) {
        this.context = context;
        this.budsPluginPackageName = str;
    }

    public final void bindService() {
        Intent intent = new Intent("com.samsung.accessory.hearablemgr.BUDS_CONTROLLER");
        String str = this.budsPluginPackageName;
        intent.setPackage(str);
        intent.setComponent(new ComponentName(str, "com.samsung.accessory.hearablemgr.core.budscontroller.BudsControllerQuickPanelService"));
        Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "budsPluginPackageName=" + str + ", isSuccess=" + this.context.bindService(intent, this.serviceConnection, 1));
    }

    public abstract void execute();
}
