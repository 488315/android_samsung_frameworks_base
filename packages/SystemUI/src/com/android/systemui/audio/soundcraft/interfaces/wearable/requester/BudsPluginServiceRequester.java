package com.android.systemui.audio.soundcraft.interfaces.wearable.requester;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BudsPluginServiceRequester {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String budsPluginPackageName;
    public final Context context;
    public Messenger messenger;
    public final Lazy mainThreadHandler$delegate = LazyKt__LazyJVMKt.lazy(new BudsPluginServiceRequester$$ExternalSyntheticLambda0());
    public final BudsPluginServiceRequester$$ExternalSyntheticLambda1 unbindServiceRunnable = new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            int i = BudsPluginServiceRequester.$r8$clinit;
            BudsPluginServiceRequester budsPluginServiceRequester = BudsPluginServiceRequester.this;
            try {
                int i2 = Result.$r8$clinit;
                if (budsPluginServiceRequester.messenger != null) {
                    Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "unbindService");
                    budsPluginServiceRequester.context.unbindService(budsPluginServiceRequester.serviceConnection);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                int i3 = Result.$r8$clinit;
                new Result.Failure(th);
            }
            return Unit.INSTANCE;
        }
    };
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
            Handler handler = (Handler) BudsPluginServiceRequester.this.mainThreadHandler$delegate.getValue();
            final BudsPluginServiceRequester$$ExternalSyntheticLambda1 budsPluginServiceRequester$$ExternalSyntheticLambda1 = BudsPluginServiceRequester.this.unbindServiceRunnable;
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$sam$java_lang_Runnable$0
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    Function0.this.invoke();
                }
            }, 500L);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("SoundCraft.wearable.BudsPluginServiceRequester", "onServiceDisconnected");
            BudsPluginServiceRequester.this.messenger = null;
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.audio.soundcraft.interfaces.wearable.requester.BudsPluginServiceRequester$serviceConnection$1] */
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
