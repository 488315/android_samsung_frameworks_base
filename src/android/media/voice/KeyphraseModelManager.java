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

    public KeyphraseModelManager(IVoiceInteractionManagerService iVoiceInteractionManagerService) {
        this.mVoiceInteractionManagerService = iVoiceInteractionManagerService;
    }

    public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, Locale locale) {
        Objects.requireNonNull(locale);
        try {
            return this.mVoiceInteractionManagerService.getKeyphraseSoundModel(i, locale.toLanguageTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        Objects.requireNonNull(keyphraseSoundModel);
        try {
            int updateKeyphraseSoundModel = this.mVoiceInteractionManagerService.updateKeyphraseSoundModel(keyphraseSoundModel);
            if (updateKeyphraseSoundModel == 0) {
            } else {
                throw new ServiceSpecificException(updateKeyphraseSoundModel);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteKeyphraseSoundModel(int i, Locale locale) {
        Objects.requireNonNull(locale);
        try {
            int deleteKeyphraseSoundModel = this.mVoiceInteractionManagerService.deleteKeyphraseSoundModel(i, locale.toLanguageTag());
            if (deleteKeyphraseSoundModel == 0) {
            } else {
                throw new ServiceSpecificException(deleteKeyphraseSoundModel);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setModelDatabaseForTestEnabled(boolean z) {
        try {
            this.mVoiceInteractionManagerService.setModelDatabaseForTestEnabled(z, new Binder());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
