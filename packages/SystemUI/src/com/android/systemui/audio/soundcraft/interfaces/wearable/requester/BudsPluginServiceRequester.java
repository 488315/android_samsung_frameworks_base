package com.android.systemui.audio.soundcraft.interfaces.wearable.requester;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class BudsPluginServiceRequester {
    public final String budsPluginPackageName;
    public final Context context;
    public Messenger messenger;
    public final BudsPluginServiceRequester$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$serviceConnection$1
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$serviceConnection$1] */
    public BudsPluginServiceRequester(Context context, String str) {
        this.context = context;
        this.budsPluginPackageName = str;
    }

    public boolean bindService() {
        Object failure;
        String str = this.budsPluginPackageName;
        try {
            int i = Result.$r8$clinit;
            Context context = this.context;
            Intent intent = new Intent("com.samsung.accessory.hearablemgr.BUDS_CONTROLLER");
            intent.setPackage(str);
            intent.setComponent(new ComponentName(str, "com.samsung.accessory.hearablemgr.core.budscontroller.BudsControllerQuickPanelService"));
            failure = Boolean.valueOf(context.bindService(intent, this.serviceConnection, 1));
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Object obj = Boolean.FALSE;
        if (failure instanceof Result.Failure) {
            failure = obj;
        }
        boolean booleanValue = ((Boolean) failure).booleanValue();
        Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "budsPluginPackageName=" + str + ", isSuccess=" + booleanValue);
        return booleanValue;
    }

    public abstract void execute();
}
