package com.android.server.texttospeech;

import android.content.Context;
import android.os.RemoteException;
import android.os.UserHandle;
import android.speech.tts.ITextToSpeechManager;
import android.speech.tts.ITextToSpeechSessionCallback;
import android.util.Slog;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.texttospeech.TextToSpeechManagerPerUserService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TextToSpeechManagerService extends AbstractMasterSystemService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TextToSpeechManagerServiceStub extends ITextToSpeechManager.Stub {
        public TextToSpeechManagerServiceStub() {
        }

        public final void createSession(String str, ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            synchronized (TextToSpeechManagerService.this.mLock) {
                try {
                    if (str == null) {
                        try {
                            iTextToSpeechSessionCallback.onError("Engine cannot be null");
                        } catch (RemoteException e) {
                            Slog.i("TextToSpeechManagerPerUserService", "Failed running callback method: " + e);
                        }
                        return;
                    }
                    TextToSpeechManagerPerUserService textToSpeechManagerPerUserService = (TextToSpeechManagerPerUserService) TextToSpeechManagerService.this.getServiceForUserLocked(UserHandle.getCallingUserId());
                    if (textToSpeechManagerPerUserService != null) {
                        TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.start(textToSpeechManagerPerUserService.mMaster.getContext(), textToSpeechManagerPerUserService.mUserId, str, iTextToSpeechSessionCallback);
                    } else {
                        try {
                            iTextToSpeechSessionCallback.onError("Service is not available for user");
                        } catch (RemoteException e2) {
                            Slog.i("TextToSpeechManagerPerUserService", "Failed running callback method: " + e2);
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
