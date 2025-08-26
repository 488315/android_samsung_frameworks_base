package com.android.systemui.audio.soundcraft.interfaces.wearable.stub;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class GWStubServiceRequester {
    public final Context context;
    public Messenger messenger;
    public final GWStubServiceRequester$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.stub.GWStubServiceRequester$serviceConnection$1
        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            super.onBindingDied(componentName);
            Log.d("SoundCraft.wearable.GWStubServiceRequester", "onBindingDied");
            this.this$0.messenger = null;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SoundCraft.wearable.GWStubServiceRequester", "onServiceConnected");
            this.this$0.messenger = new Messenger(iBinder);
            this.this$0.execute();
            GWStubServiceRequester gWStubServiceRequester = this.this$0;
            try {
                int i = Result.$r8$clinit;
                gWStubServiceRequester.context.unbindService(this);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                new Result.Failure(th);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("SoundCraft.wearable.GWStubServiceRequester", "onServiceDisconnected");
            this.this$0.messenger = null;
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.audio.soundcraft.interfaces.wearable.stub.GWStubServiceRequester$serviceConnection$1] */
    public GWStubServiceRequester(Context context) {
        this.context = context;
    }

    public abstract void execute();
}
