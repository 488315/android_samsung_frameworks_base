package com.android.server.texttospeech;

import android.content.Context;
import android.os.RemoteException;
import android.os.UserHandle;
import android.speech.tts.ITextToSpeechManager;
import android.speech.tts.ITextToSpeechSessionCallback;
import android.util.Slog;

import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;

public final class TextToSpeechManagerService extends AbstractMasterSystemService {

    public final class TextToSpeechManagerServiceStub extends ITextToSpeechManager.Stub {
        public TextToSpeechManagerServiceStub() {}

        public final void createSession(
                String str, ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            synchronized (TextToSpeechManagerService.this.mLock) {
                try {
                    if (str == null) {
                        try {
                            iTextToSpeechSessionCallback.onError("Engine cannot be null");
                        } catch (RemoteException e) {
                            Slog.i(
                                    "TextToSpeechManagerPerUserService",
                                    "Failed running callback method: " + e);
                        }
                        return;
                    }
                    TextToSpeechManagerPerUserService textToSpeechManagerPerUserService =
                            (TextToSpeechManagerPerUserService)
                                    TextToSpeechManagerService.this.getServiceForUserLocked(
                                            UserHandle.getCallingUserId());
                    if (textToSpeechManagerPerUserService != null) {
                        TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.start(
                                textToSpeechManagerPerUserService.mMaster.getContext(),
                                textToSpeechManagerPerUserService.mUserId,
                                str,
                                iTextToSpeechSessionCallback);
                    } else {
                        try {
                            iTextToSpeechSessionCallback.onError(
                                    "Service is not available for user");
                        } catch (RemoteException e2) {
                            Slog.i(
                                    "TextToSpeechManagerPerUserService",
                                    "Failed running callback method: " + e2);
                        }
                    }
                    return;
                } catch (Throwable th) {
                    throw th;
                }
                throw th;
            }
        }
    }

    public TextToSpeechManagerService(Context context) {
        super(context, null, null);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new TextToSpeechManagerPerUserService(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("texttospeech", new TextToSpeechManagerServiceStub());
    }
}
