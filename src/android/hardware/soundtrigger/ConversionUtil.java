package android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes2.dex */
public class ConversionUtil {
    public static int aidl2apiAudioCapabilities(int i) {
        int i2 = (i & 1) != 0 ? 1 : 0;
        return (i & 2) != 0 ? i2 | 2 : i2;
    }

    public static int aidl2apiRecognitionModes(int i) {
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        return (i & 8) != 0 ? i2 | 8 : i2;
    }

    public static int api2aidlAudioCapabilities(int i) {
        int i2 = (i & 1) != 0 ? 1 : 0;
        return (i & 2) != 0 ? i2 | 2 : i2;
    }

    public static int api2aidlModelParameter(int i) {
        return i != 0 ? -1 : 0;
    }

    public static int api2aidlRecognitionModes(int i) {
        int i2 = (i & 1) != 0 ? 1 : 0;
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        return (i & 8) != 0 ? i2 | 8 : i2;
    }

    public static SoundTrigger.ModuleProperties aidl2apiModuleDescriptor(SoundTriggerModuleDescriptor soundTriggerModuleDescriptor) {
        Properties properties = soundTriggerModuleDescriptor.properties;
        return new SoundTrigger.ModuleProperties(soundTriggerModuleDescriptor.handle, properties.implementor, properties.description, properties.uuid, properties.version, properties.supportedModelArch, properties.maxSoundModels, properties.maxKeyPhrases, properties.maxUsers, aidl2apiRecognitionModes(properties.recognitionModes), properties.captureTransition, properties.maxBufferMs, properties.concurrentCapture, properties.powerConsumptionMw, properties.triggerInEvent, aidl2apiAudioCapabilities(properties.audioCapabilities));
    }

    public static SoundModel api2aidlGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) {
        return api2aidlSoundModel(genericSoundModel);
    }

    public static SoundModel api2aidlSoundModel(SoundTrigger.SoundModel soundModel) {
        SoundModel soundModel2 = new SoundModel();
        soundModel2.type = soundModel.getType();
        soundModel2.uuid = api2aidlUuid(soundModel.getUuid());
        soundModel2.vendorUuid = api2aidlUuid(soundModel.getVendorUuid());
        byte[] data = soundModel.getData();
        soundModel2.data = byteArrayToSharedMemory(data, "SoundTrigger SoundModel");
        soundModel2.dataSize = data.length;
        return soundModel2;
    }

    public static String api2aidlUuid(UUID uuid) {
        return uuid.toString();
    }

    public static PhraseSoundModel api2aidlPhraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        PhraseSoundModel phraseSoundModel = new PhraseSoundModel();
        phraseSoundModel.common = api2aidlSoundModel(keyphraseSoundModel);
        phraseSoundModel.phrases = new Phrase[keyphraseSoundModel.getKeyphrases().length];
        for (int i = 0; i < keyphraseSoundModel.getKeyphrases().length; i++) {
            phraseSoundModel.phrases[i] = api2aidlPhrase(keyphraseSoundModel.getKeyphrases()[i]);
        }
        return phraseSoundModel;
    }

    public static Phrase api2aidlPhrase(SoundTrigger.Keyphrase keyphrase) {
        Phrase phrase = new Phrase();
        phrase.id = keyphrase.getId();
        phrase.recognitionModes = api2aidlRecognitionModes(keyphrase.getRecognitionModes());
        phrase.users = Arrays.copyOf(keyphrase.getUsers(), keyphrase.getUsers().length);
        phrase.locale = keyphrase.getLocale().toLanguageTag();
        phrase.text = keyphrase.getText();
        return phrase;
    }

    public static SoundTrigger.Keyphrase aidl2apiPhrase(Phrase phrase) {
        return new SoundTrigger.Keyphrase(phrase.id, aidl2apiRecognitionModes(phrase.recognitionModes), new Locale.Builder().setLanguageTag(phrase.locale).build(), phrase.text, Arrays.copyOf(phrase.users, phrase.users.length));
    }

    public static RecognitionConfig api2aidlRecognitionConfig(SoundTrigger.RecognitionConfig recognitionConfig) {
        RecognitionConfig recognitionConfig2 = new RecognitionConfig();
        recognitionConfig2.captureRequested = recognitionConfig.isCaptureRequested();
        recognitionConfig2.phraseRecognitionExtras = new PhraseRecognitionExtra[recognitionConfig.getKeyphrases().size()];
        for (int i = 0; i < recognitionConfig.getKeyphrases().size(); i++) {
            recognitionConfig2.phraseRecognitionExtras[i] = api2aidlPhraseRecognitionExtra(recognitionConfig.getKeyphrases().get(i));
        }
        recognitionConfig2.data = Arrays.copyOf(recognitionConfig.getData(), recognitionConfig.getData().length);
        recognitionConfig2.audioCapabilities = api2aidlAudioCapabilities(recognitionConfig.getAudioCapabilities());
        return recognitionConfig2;
    }

    public static SoundTrigger.RecognitionConfig aidl2apiRecognitionConfig(RecognitionConfig recognitionConfig) {
        ArrayList arrayList = new ArrayList(recognitionConfig.phraseRecognitionExtras.length);
        for (PhraseRecognitionExtra phraseRecognitionExtra : recognitionConfig.phraseRecognitionExtras) {
            arrayList.add(aidl2apiPhraseRecognitionExtra(phraseRecognitionExtra));
        }
        return new SoundTrigger.RecognitionConfig.Builder().setCaptureRequested(recognitionConfig.captureRequested).setMultipleTriggersAllowed(false).setKeyphrases(arrayList).setData(Arrays.copyOf(recognitionConfig.data, recognitionConfig.data.length)).setAudioCapabilities(aidl2apiAudioCapabilities(recognitionConfig.audioCapabilities)).build();
    }

    public static PhraseRecognitionExtra api2aidlPhraseRecognitionExtra(SoundTrigger.KeyphraseRecognitionExtra keyphraseRecognitionExtra) {
        PhraseRecognitionExtra phraseRecognitionExtra = new PhraseRecognitionExtra();
        phraseRecognitionExtra.id = keyphraseRecognitionExtra.id;
        phraseRecognitionExtra.recognitionModes = api2aidlRecognitionModes(keyphraseRecognitionExtra.recognitionModes);
        phraseRecognitionExtra.confidenceLevel = keyphraseRecognitionExtra.coarseConfidenceLevel;
        phraseRecognitionExtra.levels = new ConfidenceLevel[keyphraseRecognitionExtra.confidenceLevels.length];
        for (int i = 0; i < keyphraseRecognitionExtra.confidenceLevels.length; i++) {
            phraseRecognitionExtra.levels[i] = api2aidlConfidenceLevel(keyphraseRecognitionExtra.confidenceLevels[i]);
        }
        return phraseRecognitionExtra;
    }

    public static SoundTrigger.KeyphraseRecognitionExtra aidl2apiPhraseRecognitionExtra(PhraseRecognitionExtra phraseRecognitionExtra) {
        SoundTrigger.ConfidenceLevel[] confidenceLevelArr = new SoundTrigger.ConfidenceLevel[phraseRecognitionExtra.levels.length];
        for (int i = 0; i < phraseRecognitionExtra.levels.length; i++) {
            confidenceLevelArr[i] = aidl2apiConfidenceLevel(phraseRecognitionExtra.levels[i]);
        }
        return new SoundTrigger.KeyphraseRecognitionExtra(phraseRecognitionExtra.id, aidl2apiRecognitionModes(phraseRecognitionExtra.recognitionModes), phraseRecognitionExtra.confidenceLevel, confidenceLevelArr);
    }

    public static ConfidenceLevel api2aidlConfidenceLevel(SoundTrigger.ConfidenceLevel confidenceLevel) {
        ConfidenceLevel confidenceLevel2 = new ConfidenceLevel();
        confidenceLevel2.levelPercent = confidenceLevel.confidenceLevel;
        confidenceLevel2.userId = confidenceLevel.userId;
        return confidenceLevel2;
    }

    public static SoundTrigger.ConfidenceLevel aidl2apiConfidenceLevel(ConfidenceLevel confidenceLevel) {
        return new SoundTrigger.ConfidenceLevel(confidenceLevel.userId, confidenceLevel.levelPercent);
    }

    public static SoundTrigger.RecognitionEvent aidl2apiRecognitionEvent(int i, int i2, RecognitionEventSys recognitionEventSys) {
        RecognitionEvent recognitionEvent = recognitionEventSys.recognitionEvent;
        return new SoundTrigger.GenericRecognitionEvent(recognitionEvent.status, i, recognitionEvent.captureAvailable, i2, recognitionEvent.captureDelayMs, recognitionEvent.capturePreambleMs, recognitionEvent.triggerInData, aidl2apiAudioFormatWithDefault(recognitionEvent.audioConfig, true), recognitionEvent.data, recognitionEvent.recognitionStillActive, recognitionEventSys.halEventReceivedMillis, recognitionEventSys.token);
    }

    public static SoundTrigger.RecognitionEvent aidl2apiPhraseRecognitionEvent(int i, int i2, PhraseRecognitionEventSys phraseRecognitionEventSys) {
        PhraseRecognitionEvent phraseRecognitionEvent = phraseRecognitionEventSys.phraseRecognitionEvent;
        SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr = new SoundTrigger.KeyphraseRecognitionExtra[phraseRecognitionEvent.phraseExtras.length];
        for (int i3 = 0; i3 < phraseRecognitionEvent.phraseExtras.length; i3++) {
            keyphraseRecognitionExtraArr[i3] = aidl2apiPhraseRecognitionExtra(phraseRecognitionEvent.phraseExtras[i3]);
        }
        return new SoundTrigger.KeyphraseRecognitionEvent(phraseRecognitionEvent.common.status, i, phraseRecognitionEvent.common.captureAvailable, i2, phraseRecognitionEvent.common.captureDelayMs, phraseRecognitionEvent.common.capturePreambleMs, phraseRecognitionEvent.common.triggerInData, aidl2apiAudioFormatWithDefault(phraseRecognitionEvent.common.audioConfig, true), phraseRecognitionEvent.common.data, keyphraseRecognitionExtraArr, phraseRecognitionEventSys.halEventReceivedMillis, phraseRecognitionEventSys.token);
    }

    public static AudioFormat aidl2apiAudioFormatWithDefault(AudioConfig audioConfig, boolean z) {
        if (audioConfig != null) {
            return AidlConversion.aidl2api_AudioConfig_AudioFormat(audioConfig, z);
        }
        return new AudioFormat.Builder().setSampleRate(48000).setEncoding(2).setChannelMask(16).build();
    }

    public static SoundTrigger.ModelParamRange aidl2apiModelParameterRange(ModelParameterRange modelParameterRange) {
        if (modelParameterRange == null) {
            return null;
        }
        return new SoundTrigger.ModelParamRange(modelParameterRange.minInclusive, modelParameterRange.maxInclusive);
    }

    public static ParcelFileDescriptor byteArrayToSharedMemory(byte[] bArr, String str) {
        if (bArr.length == 0) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        try {
            SharedMemory sharedMemoryCreate = SharedMemory.create(str, bArr.length);
            ByteBuffer byteBufferMapReadWrite = sharedMemoryCreate.mapReadWrite();
            byteBufferMapReadWrite.put(bArr);
            SharedMemory.unmap(byteBufferMapReadWrite);
            ParcelFileDescriptor fdDup = sharedMemoryCreate.getFdDup();
            sharedMemoryCreate.close();
            return fdDup;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sharedMemoryToByteArray(ParcelFileDescriptor parcelFileDescriptor, int i) {
        if (parcelFileDescriptor == null || i == 0) {
            return new byte[0];
        }
        try {
            SharedMemory sharedMemoryFromFileDescriptor = SharedMemory.fromFileDescriptor(parcelFileDescriptor);
            try {
                ByteBuffer byteBufferMapReadOnly = sharedMemoryFromFileDescriptor.mapReadOnly();
                if (i > sharedMemoryFromFileDescriptor.getSize()) {
                    i = sharedMemoryFromFileDescriptor.getSize();
                }
                byte[] bArr = new byte[i];
                byteBufferMapReadOnly.get(bArr);
                SharedMemory.unmap(byteBufferMapReadOnly);
                if (sharedMemoryFromFileDescriptor != null) {
                    sharedMemoryFromFileDescriptor.close();
                }
                return bArr;
            } finally {
            }
        } catch (ErrnoException e) {
            throw new RuntimeException(e);
        }
    }
}
