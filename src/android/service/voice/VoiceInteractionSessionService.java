package android.service.voice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.voice.IVoiceInteractionSessionService;
import android.util.Log;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class VoiceInteractionSessionService extends Service {
    static final int MSG_NEW_SESSION = 1;
    private static final String TAG = "VoiceInteractionSession";
    HandlerCaller mHandlerCaller;
    VoiceInteractionSession mSession;
    IVoiceInteractionManagerService mSystemService;
    IVoiceInteractionSessionService mInterface = new IVoiceInteractionSessionService.Stub() { // from class: android.service.voice.VoiceInteractionSessionService.1
        @Override // android.service.voice.IVoiceInteractionSessionService
        public void newSession(IBinder iBinder, Bundle bundle, int i) {
            VoiceInteractionSessionService.this.mHandlerCaller.sendMessage(VoiceInteractionSessionService.this.mHandlerCaller.obtainMessageIOO(1, i, iBinder, bundle));
        }
    };
    final HandlerCaller.Callback mHandlerCallerCallback = new HandlerCaller.Callback() { // from class: android.service.voice.VoiceInteractionSessionService.2
        @Override // com.android.internal.os.HandlerCaller.Callback
        public void executeMessage(Message message) {
            SomeArgs someArgs = (SomeArgs) message.obj;
            if (message.what != 1) {
                return;
            }
            VoiceInteractionSessionService.this.doNewSession((IBinder) someArgs.arg1, (Bundle) someArgs.arg2, someArgs.argi1);
        }
    };

    public abstract VoiceInteractionSession onNewSession(Bundle bundle);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mSystemService = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService(Context.VOICE_INTERACTION_MANAGER_SERVICE));
        this.mHandlerCaller = new HandlerCaller(this, Looper.myLooper(), this.mHandlerCallerCallback, true);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mInterface.asBinder();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.onLowMemory();
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.onTrimMemory(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mSession == null) {
            printWriter.println("(no active session)");
        } else {
            printWriter.println("VoiceInteractionSession:");
            this.mSession.dump("  ", fileDescriptor, printWriter, strArr);
        }
    }

    void doNewSession(IBinder iBinder, Bundle bundle, int i) {
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.doDestroy();
            this.mSession = null;
        }
        this.mSession = onNewSession(bundle);
        if (deliverSession(iBinder)) {
            this.mSession.doCreate(this.mSystemService, iBinder);
        } else {
            this.mSession.doDestroy();
            this.mSession = null;
        }
    }

    private boolean deliverSession(IBinder iBinder) {
        try {
            return this.mSystemService.deliverNewSession(iBinder, this.mSession.mSession, this.mSession.mInteractor);
        } catch (DeadObjectException unused) {
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to deliver session: " + e);
            return false;
        }
    }
}
