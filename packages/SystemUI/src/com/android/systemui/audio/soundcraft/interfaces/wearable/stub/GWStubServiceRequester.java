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

public abstract class GWStubServiceRequester {
    public final Context context;
    public Messenger messenger;
    public final GWStubServiceRequester$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.android.systemui.audio.soundcraft.interfaces.wearable.stub.GWStubServiceRequester$serviceConnection$1
        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            super.onBindingDied(componentName);
            Log.d("SoundCraft.wearable.GWStubServiceRequester", "onBindingDied");
            GWStubServiceRequester.this.messenger = null;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("SoundCraft.wearable.GWStubServiceRequester", "onServiceConnected");
            GWStubServiceRequester.this.messenger = new Messenger(iBinder);
            GWStubServiceRequester.this.execute();
            GWStubServiceRequester gWStubServiceRequester = GWStubServiceRequester.this;
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
            GWStubServiceRequester.this.messenger = null;
        }
    };

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

    public GWStubServiceRequester(Context context) {
        this.context = context;
    }

    public abstract void execute();
}
