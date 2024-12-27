package android.hardware.soundtrigger;

import android.media.AudioFormat;
import android.media.audio.common.AidlConversion;
import android.media.audio.common.AudioConfig;
import android.media.soundtrigger.ConfidenceLevel;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseRecognitionExtra;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.os.ParcelFileDescriptor;
import android.os.SharedMemory;
import android.system.ErrnoException;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

public class ConversionUtil {
    public static SoundTrigger.ModuleProperties aidl2apiModuleDescriptor(
            SoundTriggerModuleDescriptor aidlDesc) {
        Properties properties = aidlDesc.properties;
        return new SoundTrigger.ModuleProperties(
                aidlDesc.handle,
                properties.implementor,
                properties.description,
                properties.uuid,
                properties.version,
                properties.supportedModelArch,
                properties.maxSoundModels,
                properties.maxKeyPhrases,
                properties.maxUsers,
                aidl2apiRecognitionModes(properties.recognitionModes),
                properties.captureTransition,
                properties.maxBufferMs,
                properties.concurrentCapture,
                properties.powerConsumptionMw,
                properties.triggerInEvent,
                aidl2apiAudioCapabilities(properties.audioCapabilities));
    }

    public static int aidl2apiRecognitionModes(int aidlModes) {
        int result = 0;
        if ((aidlModes & 1) != 0) {
            result = 0 | 1;
        }
        if ((aidlModes & 2) != 0) {
            result |= 2;
        }
        if ((aidlModes & 4) != 0) {
            result |= 4;
        }
        if ((aidlModes & 8) != 0) {
            return result | 8;
        }
        return result;
    }

    public static int api2aidlRecognitionModes(int apiModes) {
        int result = 0;
        if ((apiModes & 1) != 0) {
            result = 0 | 1;
        }
        if ((apiModes & 2) != 0) {
            result |= 2;
        }
        if ((apiModes & 4) != 0) {
            result |= 4;
        }
        if ((apiModes & 8) != 0) {
            return result | 8;
        }
        return result;
    }

    public static SoundModel api2aidlGenericSoundModel(SoundTrigger.GenericSoundModel apiModel) {
        return api2aidlSoundModel(apiModel);
    }

    public static SoundModel api2aidlSoundModel(SoundTrigger.SoundModel apiModel) {
        SoundModel aidlModel = new SoundModel();
        aidlModel.type = apiModel.getType();
        aidlModel.uuid = api2aidlUuid(apiModel.getUuid());
        aidlModel.vendorUuid = api2aidlUuid(apiModel.getVendorUuid());
        byte[] data = apiModel.getData();
        aidlModel.data = byteArrayToSharedMemory(data, "SoundTrigger SoundModel");
        aidlModel.dataSize = data.length;
        return aidlModel;
    }

    public static String api2aidlUuid(UUID apiUuid) {
        return apiUuid.toString();
    }

    public static PhraseSoundModel api2aidlPhraseSoundModel(
            SoundTrigger.KeyphraseSoundModel apiModel) {
        PhraseSoundModel aidlModel = new PhraseSoundModel();
        aidlModel.common = api2aidlSoundModel(apiModel);
        aidlModel.phrases = new Phrase[apiModel.getKeyphrases().length];
        for (int i = 0; i < apiModel.getKeyphrases().length; i++) {
            aidlModel.phrases[i] = api2aidlPhrase(apiModel.getKeyphrases()[i]);
        }
        return aidlModel;
    }

    public static Phrase api2aidlPhrase(SoundTrigger.Keyphrase apiPhrase) {
        Phrase aidlPhrase = new Phrase();
        aidlPhrase.id = apiPhrase.getId();
        aidlPhrase.recognitionModes = api2aidlRecognitionModes(apiPhrase.getRecognitionModes());
        aidlPhrase.users = Arrays.copyOf(apiPhrase.getUsers(), apiPhrase.getUsers().length);
        aidlPhrase.locale = apiPhrase.getLocale().toLanguageTag();
        aidlPhrase.text = apiPhrase.getText();
        return aidlPhrase;
    }

    public static SoundTrigger.Keyphrase aidl2apiPhrase(Phrase aidlPhrase) {
        return new SoundTrigger.Keyphrase(
                aidlPhrase.id,
                aidl2apiRecognitionModes(aidlPhrase.recognitionModes),
                new Locale.Builder().setLanguageTag(aidlPhrase.locale).build(),
                aidlPhrase.text,
                Arrays.copyOf(aidlPhrase.users, aidlPhrase.users.length));
    }

    public static RecognitionConfig api2aidlRecognitionConfig(
            SoundTrigger.RecognitionConfig apiConfig) {
        RecognitionConfig aidlConfig = new RecognitionConfig();
        aidlConfig.captureRequested = apiConfig.captureRequested;
        aidlConfig.phraseRecognitionExtras =
                new PhraseRecognitionExtra[apiConfig.keyphrases.length];
        for (int i = 0; i < apiConfig.keyphrases.length; i++) {
            aidlConfig.phraseRecognitionExtras[i] =
                    api2aidlPhraseRecognitionExtra(apiConfig.keyphrases[i]);
        }
        aidlConfig.data = Arrays.copyOf(apiConfig.data, apiConfig.data.length);
        aidlConfig.audioCapabilities = api2aidlAudioCapabilities(apiConfig.audioCapabilities);
        return aidlConfig;
    }

    public static SoundTrigger.RecognitionConfig aidl2apiRecognitionConfig(
            RecognitionConfig aidlConfig) {
        SoundTrigger.KeyphraseRecognitionExtra[] keyphrases =
                new SoundTrigger.KeyphraseRecognitionExtra
                        [aidlConfig.phraseRecognitionExtras.length];
        PhraseRecognitionExtra[] phraseRecognitionExtraArr = aidlConfig.phraseRecognitionExtras;
        int length = phraseRecognitionExtraArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            PhraseRecognitionExtra extras = phraseRecognitionExtraArr[i];
            keyphrases[i2] = aidl2apiPhraseRecognitionExtra(extras);
            i++;
            i2++;
        }
        return new SoundTrigger.RecognitionConfig(
                aidlConfig.captureRequested,
                false,
                keyphrases,
                Arrays.copyOf(aidlConfig.data, aidlConfig.data.length),
                aidl2apiAudioCapabilities(aidlConfig.audioCapabilities));
    }

    public static PhraseRecognitionExtra api2aidlPhraseRecognitionExtra(
            SoundTrigger.KeyphraseRecognitionExtra apiExtra) {
        PhraseRecognitionExtra aidlExtra = new PhraseRecognitionExtra();
        aidlExtra.id = apiExtra.id;
        aidlExtra.recognitionModes = api2aidlRecognitionModes(apiExtra.recognitionModes);
        aidlExtra.confidenceLevel = apiExtra.coarseConfidenceLevel;
        aidlExtra.levels = new ConfidenceLevel[apiExtra.confidenceLevels.length];
        for (int i = 0; i < apiExtra.confidenceLevels.length; i++) {
            aidlExtra.levels[i] = api2aidlConfidenceLevel(apiExtra.confidenceLevels[i]);
        }
        return aidlExtra;
    }

    public static SoundTrigger.KeyphraseRecognitionExtra aidl2apiPhraseRecognitionExtra(
            PhraseRecognitionExtra aidlExtra) {
        SoundTrigger.ConfidenceLevel[] apiLevels =
                new SoundTrigger.ConfidenceLevel[aidlExtra.levels.length];
        for (int i = 0; i < aidlExtra.levels.length; i++) {
            apiLevels[i] = aidl2apiConfidenceLevel(aidlExtra.levels[i]);
        }
        return new SoundTrigger.KeyphraseRecognitionExtra(
                aidlExtra.id,
                aidl2apiRecognitionModes(aidlExtra.recognitionModes),
                aidlExtra.confidenceLevel,
                apiLevels);
    }

    public static ConfidenceLevel api2aidlConfidenceLevel(SoundTrigger.ConfidenceLevel apiLevel) {
        ConfidenceLevel aidlLevel = new ConfidenceLevel();
        aidlLevel.levelPercent = apiLevel.confidenceLevel;
        aidlLevel.userId = apiLevel.userId;
        return aidlLevel;
    }

    public static SoundTrigger.ConfidenceLevel aidl2apiConfidenceLevel(ConfidenceLevel apiLevel) {
        return new SoundTrigger.ConfidenceLevel(apiLevel.userId, apiLevel.levelPercent);
    }

    public static SoundTrigger.RecognitionEvent aidl2apiRecognitionEvent(
            int modelHandle, int captureSession, RecognitionEventSys aidlEvent) {
        RecognitionEvent recognitionEvent = aidlEvent.recognitionEvent;
        AudioFormat audioFormat =
                aidl2apiAudioFormatWithDefault(recognitionEvent.audioConfig, true);
        return new SoundTrigger.GenericRecognitionEvent(
                recognitionEvent.status,
                modelHandle,
                recognitionEvent.captureAvailable,
                captureSession,
                recognitionEvent.captureDelayMs,
                recognitionEvent.capturePreambleMs,
                recognitionEvent.triggerInData,
                audioFormat,
                recognitionEvent.data,
                recognitionEvent.recognitionStillActive,
                aidlEvent.halEventReceivedMillis,
                aidlEvent.token);
    }

    public static SoundTrigger.RecognitionEvent aidl2apiPhraseRecognitionEvent(
            int modelHandle, int captureSession, PhraseRecognitionEventSys aidlEvent) {
        PhraseRecognitionEvent recognitionEvent = aidlEvent.phraseRecognitionEvent;
        SoundTrigger.KeyphraseRecognitionExtra[] apiExtras =
                new SoundTrigger.KeyphraseRecognitionExtra[recognitionEvent.phraseExtras.length];
        for (int i = 0; i < recognitionEvent.phraseExtras.length; i++) {
            apiExtras[i] = aidl2apiPhraseRecognitionExtra(recognitionEvent.phraseExtras[i]);
        }
        AudioFormat audioFormat =
                aidl2apiAudioFormatWithDefault(recognitionEvent.common.audioConfig, true);
        return new SoundTrigger.KeyphraseRecognitionEvent(
                recognitionEvent.common.status,
                modelHandle,
                recognitionEvent.common.captureAvailable,
                captureSession,
                recognitionEvent.common.captureDelayMs,
                recognitionEvent.common.capturePreambleMs,
                recognitionEvent.common.triggerInData,
                audioFormat,
                recognitionEvent.common.data,
                apiExtras,
                aidlEvent.halEventReceivedMillis,
                aidlEvent.token);
    }

    public static AudioFormat aidl2apiAudioFormatWithDefault(
            AudioConfig audioConfig, boolean isInput) {
        if (audioConfig != null) {
            return AidlConversion.aidl2api_AudioConfig_AudioFormat(audioConfig, isInput);
        }
        return new AudioFormat.Builder()
                .setSampleRate(48000)
                .setEncoding(2)
                .setChannelMask(16)
                .build();
    }

    public static int api2aidlModelParameter(int apiParam) {
        switch (apiParam) {
            case 0:
                return 0;
            default:
                return -1;
        }
    }

    public static SoundTrigger.ModelParamRange aidl2apiModelParameterRange(
            ModelParameterRange aidlRange) {
        if (aidlRange == null) {
            return null;
        }
        return new SoundTrigger.ModelParamRange(aidlRange.minInclusive, aidlRange.maxInclusive);
    }

    public static int aidl2apiAudioCapabilities(int aidlCapabilities) {
        int result = 0;
        if ((aidlCapabilities & 1) != 0) {
            result = 0 | 1;
        }
        if ((aidlCapabilities & 2) != 0) {
            return result | 2;
        }
        return result;
    }

    public static int api2aidlAudioCapabilities(int apiCapabilities) {
        int result = 0;
        if ((apiCapabilities & 1) != 0) {
            result = 0 | 1;
        }
        if ((apiCapabilities & 2) != 0) {
            return result | 2;
        }
        return result;
    }

    public static ParcelFileDescriptor byteArrayToSharedMemory(byte[] data, String name) {
        if (data.length == 0) {
            return null;
        }
        try {
            SharedMemory shmem = SharedMemory.create(name != null ? name : "", data.length);
            ByteBuffer buffer = shmem.mapReadWrite();
            buffer.put(data);
            SharedMemory.unmap(buffer);
            ParcelFileDescriptor fd = shmem.getFdDup();
            shmem.close();
            return fd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sharedMemoryToByteArray(ParcelFileDescriptor pfd, int size) {
        if (pfd == null || size == 0) {
            return new byte[0];
        }
        try {
            SharedMemory mem = SharedMemory.fromFileDescriptor(pfd);
            try {
                ByteBuffer buffer = mem.mapReadOnly();
                byte[] data = new byte[size > mem.getSize() ? mem.getSize() : size];
                buffer.get(data);
                SharedMemory.unmap(buffer);
                if (mem != null) {
                    mem.close();
                }
                return data;
            } finally {
            }
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }
}
