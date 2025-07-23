package com.android.systemui.navigationbar.gestural;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.graphics.Region;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.gestural.OneHandOpGestureHandler.IncomingHandler;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OneHandOpGestureHandler {
    public static final Companion Companion = new Companion(null);
    public static final boolean SAFE_DEBUG;
    public final Context context;
    public int disablePolicy;
    public IncomingHandler incomingHandler;
    public boolean isOneHandOpServiceConnected;
    public Messenger replyMessenger;
    public Messenger sendMessenger;
    public final Region oneHandOpGestureRegion = new Region();
    public final OneHandOpGestureHandler$mConnection$1 mConnection = new ServiceConnection() { // from class: com.android.systemui.navigationbar.gestural.OneHandOpGestureHandler$mConnection$1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("OneHandOpGestureHandler", "onServiceConnected() className=" + componentName);
            OneHandOpGestureHandler.this.sendMessenger = new Messenger(iBinder);
            OneHandOpGestureHandler.this.incomingHandler = OneHandOpGestureHandler.this.new IncomingHandler();
            OneHandOpGestureHandler.this.replyMessenger = new Messenger(OneHandOpGestureHandler.this.incomingHandler);
            OneHandOpGestureHandler.this.isOneHandOpServiceConnected = true;
            Message obtain = Message.obtain(null, 1, null);
            OneHandOpGestureHandler oneHandOpGestureHandler = OneHandOpGestureHandler.this;
            obtain.replyTo = oneHandOpGestureHandler.replyMessenger;
            try {
                Messenger messenger = oneHandOpGestureHandler.sendMessenger;
                messenger.getClass();
                messenger.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.i("OneHandOpGestureHandler", "onServiceDisconnected() className=" + componentName);
            OneHandOpGestureHandler oneHandOpGestureHandler = OneHandOpGestureHandler.this;
            oneHandOpGestureHandler.sendMessenger = null;
            oneHandOpGestureHandler.replyMessenger = null;
            oneHandOpGestureHandler.incomingHandler = null;
            oneHandOpGestureHandler.isOneHandOpServiceConnected = false;
            oneHandOpGestureHandler.oneHandOpGestureRegion.setEmpty();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IncomingHandler extends Handler {
        public IncomingHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Companion companion = OneHandOpGestureHandler.Companion;
            companion.getClass();
            boolean z = OneHandOpGestureHandler.SAFE_DEBUG;
            if (z) {
                ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(message.what, "RECEIVED Message in systemui. msg.what=", "OneHandOpGestureHandler");
            }
            int i = message.what;
            OneHandOpGestureHandler oneHandOpGestureHandler = OneHandOpGestureHandler.this;
            if (i != 2) {
                if (i != 3) {
                    super.handleMessage(message);
                    return;
                } else {
                    oneHandOpGestureHandler.unbindOHOService();
                    return;
                }
            }
            Region region = (Region) message.obj;
            companion.getClass();
            if (z) {
                Log.i("OneHandOpGestureHandler", "RECEIVED msg in systemui. region=" + region);
            }
            oneHandOpGestureHandler.oneHandOpGestureRegion.setEmpty();
            if (region.isEmpty()) {
                return;
            }
            oneHandOpGestureHandler.oneHandOpGestureRegion.set(region);
        }
    }

    static {
        String str = Build.TYPE;
        SAFE_DEBUG = str.equals("userdebug") | str.equals("eng");
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.navigationbar.gestural.OneHandOpGestureHandler$mConnection$1] */
    public OneHandOpGestureHandler(Context context) {
        this.context = context;
    }

    public final boolean isGestureBlockedByPolicy(MotionEvent motionEvent) {
        int i = this.disablePolicy;
        if (i != 1) {
            if (!this.isOneHandOpServiceConnected || i != 2) {
                return false;
            }
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            if (!this.oneHandOpGestureRegion.contains(rawX, rawY)) {
                return false;
            }
            if (SAFE_DEBUG) {
                Region region = this.oneHandOpGestureRegion;
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(rawX, rawY, "isGestureBlockedByPolicy() true. pos=(", ",", ") region=");
                m.append(region);
                Log.d("OneHandOpGestureHandler", m.toString());
            }
        }
        return true;
    }

    public final void unbindOHOService() {
        if (this.isOneHandOpServiceConnected) {
            Log.d("OneHandOpGestureHandler", "unbindOHOService()");
            this.isOneHandOpServiceConnected = false;
            try {
                this.context.unbindService(this.mConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
