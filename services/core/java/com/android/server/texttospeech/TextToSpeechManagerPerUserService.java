package com.android.server.texttospeech;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.speech.tts.ITextToSpeechService;
import android.speech.tts.ITextToSpeechSession;
import android.speech.tts.ITextToSpeechSessionCallback;
import android.util.Slog;

import com.android.internal.infra.ServiceConnector;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractPerUserSystemService;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TextToSpeechManagerPerUserService extends AbstractPerUserSystemService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class TextToSpeechSessionConnection extends ServiceConnector.Impl {
        public static final /* synthetic */ int $r8$clinit = 0;
        private ITextToSpeechSessionCallback mCallback;
        private final String mEngine;
        private final IBinder.DeathRecipient mUnbindOnDeathHandler;

        /* renamed from: $r8$lambda$3-yVGMsgcEZQX1SgaZp7W8CoFnU, reason: not valid java name */
        public static void m970$r8$lambda$3yVGMsgcEZQX1SgaZp7W8CoFnU(
                TextToSpeechSessionConnection textToSpeechSessionConnection,
                ITextToSpeechService iTextToSpeechService) {
            if (iTextToSpeechService != null) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Connected successfully to TTS engine: "),
                        textToSpeechSessionConnection.mEngine,
                        "TextToSpeechManagerPerUserService");
                try {
                    textToSpeechSessionConnection.mCallback.onConnected(
                            new ITextToSpeechSession
                                    .Stub() { // from class:
                                              // com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.1
                                public final void disconnect() {
                                    TextToSpeechSessionConnection textToSpeechSessionConnection2 =
                                            TextToSpeechSessionConnection.this;
                                    int i = TextToSpeechSessionConnection.$r8$clinit;
                                    textToSpeechSessionConnection2.unbindEngine(
                                            "client disconnection request");
                                }
                            },
                            iTextToSpeechService.asBinder());
                    textToSpeechSessionConnection
                            .mCallback
                            .asBinder()
                            .linkToDeath(textToSpeechSessionConnection.mUnbindOnDeathHandler, 0);
                    return;
                } catch (RemoteException e) {
                    Slog.w(
                            "TextToSpeechManagerPerUserService",
                            "Error notifying the client on connection",
                            e);
                    textToSpeechSessionConnection.unbindEngine(
                            "failed communicating with the client - process is dead");
                    return;
                }
            }
            Slog.w("TextToSpeechManagerPerUserService", "Failed to obtain TTS engine binder");
            try {
                textToSpeechSessionConnection.mCallback.onError("Failed creating TTS session");
            } catch (RemoteException e2) {
                Slog.i(
                        "TextToSpeechManagerPerUserService",
                        "Failed running callback method: " + e2);
            }
        }

        public TextToSpeechSessionConnection(
                Context context,
                int i,
                String str,
                ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            super(
                    context,
                    new Intent("android.intent.action.TTS_SERVICE").setPackage(str),
                    524289,
                    i,
                    new TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda0());
            this.mEngine = str;
            this.mCallback = iTextToSpeechSessionCallback;
            this.mUnbindOnDeathHandler =
                    new IBinder
                            .DeathRecipient() { // from class:
                                                // com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda1
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            TextToSpeechManagerPerUserService.TextToSpeechSessionConnection
                                    textToSpeechSessionConnection =
                                            TextToSpeechManagerPerUserService
                                                    .TextToSpeechSessionConnection.this;
                            int i2 =
                                    TextToSpeechManagerPerUserService.TextToSpeechSessionConnection
                                            .$r8$clinit;
                            textToSpeechSessionConnection.unbindEngine(
                                    "client process death is reported");
                        }
                    };
        }

        public static void start(
                Context context,
                int i,
                String str,
                ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            final TextToSpeechSessionConnection textToSpeechSessionConnection =
                    new TextToSpeechSessionConnection(
                            context, i, str, iTextToSpeechSessionCallback);
            Slog.d(
                    "TextToSpeechManagerPerUserService",
                    "Trying to start connection to TTS engine: "
                            + textToSpeechSessionConnection.mEngine);
            textToSpeechSessionConnection
                    .connect()
                    .thenAccept(
                            new Consumer() { // from class:
                                             // com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    TextToSpeechManagerPerUserService.TextToSpeechSessionConnection
                                            .m970$r8$lambda$3yVGMsgcEZQX1SgaZp7W8CoFnU(
                                                    TextToSpeechManagerPerUserService
                                                            .TextToSpeechSessionConnection.this,
                                                    (ITextToSpeechService) obj);
                                }
                            })
                    .exceptionally(
                            new Function() { // from class:
                                             // com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda3
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    TextToSpeechManagerPerUserService.TextToSpeechSessionConnection
                                            textToSpeechSessionConnection2 =
                                                    TextToSpeechManagerPerUserService
                                                            .TextToSpeechSessionConnection.this;
                                    Throwable th = (Throwable) obj;
                                    int i2 =
                                            TextToSpeechManagerPerUserService
                                                    .TextToSpeechSessionConnection.$r8$clinit;
                                    textToSpeechSessionConnection2.getClass();
                                    Slog.w(
                                            "TextToSpeechManagerPerUserService",
                                            "TTS engine binding error",
                                            th);
                                    try {
                                        textToSpeechSessionConnection2.mCallback.onError(
                                                "Failed creating TTS session: " + th.getCause());
                                        return null;
                                    } catch (RemoteException e) {
                                        Slog.i(
                                                "TextToSpeechManagerPerUserService",
                                                "Failed running callback method: " + e);
                                        return null;
                                    }
                                }
                            });
        }

        public final long getAutoDisconnectTimeoutMs() {
            return 0L;
        }

        public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
            if (z) {
                return;
            }
            Slog.w("TextToSpeechManagerPerUserService", "Disconnected from TTS engine");
            ITextToSpeechSessionCallback iTextToSpeechSessionCallback = this.mCallback;
            Objects.requireNonNull(iTextToSpeechSessionCallback);
            try {
                iTextToSpeechSessionCallback.onDisconnected();
            } catch (RemoteException e) {
                Slog.i("TextToSpeechManagerPerUserService", "Failed running callback method: " + e);
            }
            try {
                this.mCallback.asBinder().unlinkToDeath(this.mUnbindOnDeathHandler, 0);
            } catch (NoSuchElementException unused) {
                Slog.d("TextToSpeechManagerPerUserService", "The death recipient was not linked.");
            }
            this.mCallback = null;
        }

        public final void unbindEngine(String str) {
            Slog.d(
                    "TextToSpeechManagerPerUserService",
                    "Unbinding TTS engine: " + this.mEngine + ". Reason: " + str);
            unbind();
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(
                    AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(
                            componentName, "Could not get service for "));
        }
    }
}
