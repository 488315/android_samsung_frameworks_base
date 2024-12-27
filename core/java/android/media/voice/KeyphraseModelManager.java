package android.media.voice;

import android.annotation.SystemApi;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;

import com.android.internal.app.IVoiceInteractionManagerService;

import java.util.Locale;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class KeyphraseModelManager {
    private static final boolean DBG = false;
    private static final String TAG = "KeyphraseModelManager";
    private final IVoiceInteractionManagerService mVoiceInteractionManagerService;

    public KeyphraseModelManager(IVoiceInteractionManagerService voiceInteractionManagerService) {
        this.mVoiceInteractionManagerService = voiceInteractionManagerService;
    }

    public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int keyphraseId, Locale locale) {
        Objects.requireNonNull(locale);
        try {
            return this.mVoiceInteractionManagerService.getKeyphraseSoundModel(
                    keyphraseId, locale.toLanguageTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel model) {
        Objects.requireNonNull(model);
        try {
            int status = this.mVoiceInteractionManagerService.updateKeyphraseSoundModel(model);
            if (status != 0) {
                throw new ServiceSpecificException(status);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteKeyphraseSoundModel(int keyphraseId, Locale locale) {
        Objects.requireNonNull(locale);
        try {
            int status =
                    this.mVoiceInteractionManagerService.deleteKeyphraseSoundModel(
                            keyphraseId, locale.toLanguageTag());
            if (status != 0) {
                throw new ServiceSpecificException(status);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setModelDatabaseForTestEnabled(boolean enabled) {
        try {
            this.mVoiceInteractionManagerService.setModelDatabaseForTestEnabled(
                    enabled, new Binder());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
