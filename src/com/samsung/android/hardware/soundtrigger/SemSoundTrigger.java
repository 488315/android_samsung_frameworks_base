package com.samsung.android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.os.Binder;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes6.dex */
public class SemSoundTrigger {
    public static final int RECOGNITION_MODE_USER_AUTHENTICATION = 4;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int RECOGNITION_STATUS_ABORT = 1;
    public static final int RECOGNITION_STATUS_FAILURE = 2;
    public static final int RECOGNITION_STATUS_SUCCESS = 0;
    public static final int SERVICE_STATE_DISABLED = 1;
    public static final int SERVICE_STATE_ENABLED = 0;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_OK = 0;
    public static final int STATUS_PERMISSION_DENIED = SoundTrigger.STATUS_PERMISSION_DENIED;
    public static final int STATUS_NO_INIT = SoundTrigger.STATUS_NO_INIT;
    public static final int STATUS_BAD_VALUE = SoundTrigger.STATUS_BAD_VALUE;
    public static final int STATUS_DEAD_OBJECT = SoundTrigger.STATUS_DEAD_OBJECT;
    public static final int STATUS_INVALID_OPERATION = SoundTrigger.STATUS_INVALID_OPERATION;

    public interface StatusListener {
        void onRecognition(RecognitionEvent recognitionEvent);

        void onServiceDied();

        void onServiceStateChange(int i);
    }

    private SemSoundTrigger() {
    }

    public static class ModuleProperties {
        public final int id;
        public final boolean supportsConcurrentCapture;
        public final UUID uuid;

        ModuleProperties(int i, String str, String str2, String str3, int i2, String str4, int i3, int i4, int i5, int i6, boolean z, int i7, boolean z2, int i8, boolean z3, int i9) {
            this.id = i;
            this.uuid = UUID.fromString(str3);
            this.supportsConcurrentCapture = z2;
        }

        public String toString() {
            return "ModuleProperties [id=" + this.id + ", uuid=" + this.uuid + ", supportsConcurrentCapture=" + this.supportsConcurrentCapture + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class Keyphrase {
        private final int id;
        SoundTrigger.Keyphrase instance;
        private final Locale locale;
        private final int recognitionModes;
        private final String text;
        private final int[] users;

        public Keyphrase(int i, int i2, String str, String str2, int[] iArr) {
            this.id = i;
            this.recognitionModes = i2;
            Locale locale = new Locale(str);
            this.locale = locale;
            this.text = str2;
            this.users = iArr;
            this.instance = new SoundTrigger.Keyphrase(i, i2, locale, str2, iArr);
        }

        public String toString() {
            return "Keyphrase [id=" + this.id + ", recognitionModes=" + this.recognitionModes + ", locale=" + this.locale + ", text=" + this.text + ", users=" + Arrays.toString(this.users) + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class KeyphraseSoundModel {
        SoundTrigger.KeyphraseSoundModel instance;
        public final Keyphrase[] keyphrases;
        public final UUID uuid;

        public KeyphraseSoundModel(UUID uuid, UUID uuid2, byte[] bArr, Keyphrase[] keyphraseArr) {
            SoundTrigger.Keyphrase[] keyphraseArr2 = new SoundTrigger.Keyphrase[keyphraseArr.length];
            for (int i = 0; i < keyphraseArr.length; i++) {
                keyphraseArr2[i] = new SoundTrigger.Keyphrase(keyphraseArr[i].id, keyphraseArr[i].recognitionModes, keyphraseArr[i].locale, keyphraseArr[i].text, keyphraseArr[i].users);
            }
            this.instance = new SoundTrigger.KeyphraseSoundModel(uuid, uuid2, bArr, keyphraseArr2);
            this.keyphrases = keyphraseArr;
            this.uuid = uuid;
        }
    }

    public static class RecognitionEvent {
        public final boolean captureAvailable;
        public final int captureDelayMs;
        public AudioFormat captureFormat;
        public final int capturePreambleMs;
        public final int captureSession;
        public final byte[] data;
        public final int soundModelHandle;
        public final int status;
        public final boolean triggerInData;

        RecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, AudioFormat audioFormat, byte[] bArr) {
            this.status = i;
            this.soundModelHandle = i2;
            this.captureAvailable = z;
            this.captureSession = i3;
            this.captureDelayMs = i4;
            this.capturePreambleMs = i5;
            this.triggerInData = z2;
            this.captureFormat = audioFormat;
            this.data = bArr;
        }

        public String toString() {
            String str;
            String str2;
            StringBuilder sb = new StringBuilder("RecognitionEvent [status=");
            sb.append(this.status);
            sb.append(", soundModelHandle=");
            sb.append(this.soundModelHandle);
            sb.append(", captureAvailable=");
            sb.append(this.captureAvailable);
            sb.append(", captureSession=");
            sb.append(this.captureSession);
            sb.append(", captureDelayMs=");
            sb.append(this.captureDelayMs);
            sb.append(", capturePreambleMs=");
            sb.append(this.capturePreambleMs);
            sb.append(", triggerInData=");
            sb.append(this.triggerInData);
            String str3 = "";
            if (this.captureFormat == null) {
                str = "";
            } else {
                str = ", sampleRate=" + this.captureFormat.getSampleRate();
            }
            sb.append(str);
            if (this.captureFormat == null) {
                str2 = "";
            } else {
                str2 = ", encoding=" + this.captureFormat.getEncoding();
            }
            sb.append(str2);
            if (this.captureFormat != null) {
                str3 = ", channelMask=" + this.captureFormat.getChannelMask();
            }
            sb.append(str3);
            sb.append(", data=");
            byte[] bArr = this.data;
            sb.append(bArr == null ? 0 : bArr.length);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }
    }

    public static class RecognitionConfig {
        private final boolean allowMultipleTriggers;
        private final boolean captureRequested;
        private final byte[] data;
        SoundTrigger.RecognitionConfig instance;
        private final KeyphraseRecognitionExtra[] keyphrases;

        public RecognitionConfig(boolean z, boolean z2, KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr, byte[] bArr) {
            SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr2 = new SoundTrigger.KeyphraseRecognitionExtra[keyphraseRecognitionExtraArr.length];
            for (int i = 0; i < keyphraseRecognitionExtraArr.length; i++) {
                SoundTrigger.ConfidenceLevel[] confidenceLevelArr = new SoundTrigger.ConfidenceLevel[keyphraseRecognitionExtraArr[i].confidenceLevels.length];
                for (int i2 = 0; i2 < keyphraseRecognitionExtraArr[i].confidenceLevels.length; i2++) {
                    confidenceLevelArr[i2] = new SoundTrigger.ConfidenceLevel(keyphraseRecognitionExtraArr[i].confidenceLevels[i2].userId, keyphraseRecognitionExtraArr[i].confidenceLevels[i2].confidenceLevel);
                }
                keyphraseRecognitionExtraArr2[i] = new SoundTrigger.KeyphraseRecognitionExtra(keyphraseRecognitionExtraArr[i].id, keyphraseRecognitionExtraArr[i].recognitionModes, keyphraseRecognitionExtraArr[i].coarseConfidenceLevel, confidenceLevelArr);
            }
            this.instance = new SoundTrigger.RecognitionConfig(z, z2, keyphraseRecognitionExtraArr2, bArr);
            this.captureRequested = z;
            this.allowMultipleTriggers = z2;
            this.keyphrases = keyphraseRecognitionExtraArr;
            this.data = bArr;
        }
    }

    public static class ConfidenceLevel {
        public final int confidenceLevel;
        SoundTrigger.ConfidenceLevel instance;
        public final int userId;

        public ConfidenceLevel(int i, int i2) {
            SoundTrigger.ConfidenceLevel confidenceLevel = new SoundTrigger.ConfidenceLevel(i, i2);
            this.instance = confidenceLevel;
            this.userId = confidenceLevel.userId;
            this.confidenceLevel = this.instance.confidenceLevel;
        }
    }

    public static class KeyphraseRecognitionExtra {
        public final int coarseConfidenceLevel;
        public final ConfidenceLevel[] confidenceLevels;
        public final int id;
        SoundTrigger.KeyphraseRecognitionExtra instance;
        public final int recognitionModes;

        public KeyphraseRecognitionExtra(int i, int i2, int i3, ConfidenceLevel[] confidenceLevelArr) {
            SoundTrigger.ConfidenceLevel[] confidenceLevelArr2 = new SoundTrigger.ConfidenceLevel[confidenceLevelArr.length];
            for (int i4 = 0; i4 < confidenceLevelArr.length; i4++) {
                confidenceLevelArr2[i4] = new SoundTrigger.ConfidenceLevel(confidenceLevelArr[i4].userId, confidenceLevelArr[i4].confidenceLevel);
            }
            this.instance = new SoundTrigger.KeyphraseRecognitionExtra(i, i2, i3, confidenceLevelArr2);
            this.id = i;
            this.recognitionModes = i2;
            this.coarseConfidenceLevel = i3;
            this.confidenceLevels = confidenceLevelArr;
        }
    }

    public static class KeyphraseRecognitionEvent extends RecognitionEvent {
        public final KeyphraseRecognitionExtra[] keyphraseExtras;

        public KeyphraseRecognitionEvent(int i, int i2, boolean z, int i3, int i4, int i5, boolean z2, AudioFormat audioFormat, byte[] bArr, KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr) {
            super(i, i2, z, i3, i4, i5, z2, audioFormat, bArr);
            this.keyphraseExtras = keyphraseRecognitionExtraArr;
        }

        @Override // com.samsung.android.hardware.soundtrigger.SemSoundTrigger.RecognitionEvent
        public String toString() {
            String str;
            String str2;
            StringBuilder sb = new StringBuilder("KeyphraseRecognitionEvent [keyphraseExtras=");
            sb.append(Arrays.toString(this.keyphraseExtras));
            sb.append(", status=");
            sb.append(this.status);
            sb.append(", soundModelHandle=");
            sb.append(this.soundModelHandle);
            sb.append(", captureAvailable=");
            sb.append(this.captureAvailable);
            sb.append(", captureSession=");
            sb.append(this.captureSession);
            sb.append(", captureDelayMs=");
            sb.append(this.captureDelayMs);
            sb.append(", capturePreambleMs=");
            sb.append(this.capturePreambleMs);
            sb.append(", triggerInData=");
            sb.append(this.triggerInData);
            String str3 = "";
            if (this.captureFormat == null) {
                str = "";
            } else {
                str = ", sampleRate=" + this.captureFormat.getSampleRate();
            }
            sb.append(str);
            if (this.captureFormat == null) {
                str2 = "";
            } else {
                str2 = ", encoding=" + this.captureFormat.getEncoding();
            }
            sb.append(str2);
            if (this.captureFormat != null) {
                str3 = ", channelMask=" + this.captureFormat.getChannelMask();
            }
            sb.append(str3);
            sb.append(", data=");
            sb.append(this.data == null ? 0 : this.data.length);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }
    }

    public static int listModules(ArrayList<ModuleProperties> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Identity identity = new Identity();
        identity.pid = Binder.getCallingPid();
        identity.uid = Binder.getCallingUid();
        SoundTrigger.listModulesAsOriginator(arrayList2, identity);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            SoundTrigger.ModuleProperties moduleProperties = (SoundTrigger.ModuleProperties) it.next();
            arrayList.add(new ModuleProperties(moduleProperties.getId(), moduleProperties.getImplementor(), moduleProperties.getDescription(), moduleProperties.getUuid().toString(), moduleProperties.getVersion(), moduleProperties.getSupportedModelArch(), moduleProperties.getMaxSoundModels(), moduleProperties.getMaxKeyphrases(), moduleProperties.getMaxUsers(), moduleProperties.getRecognitionModes(), moduleProperties.isCaptureTransitionSupported(), moduleProperties.getMaxBufferMillis(), moduleProperties.isConcurrentCaptureSupported(), moduleProperties.getPowerConsumptionMw(), moduleProperties.isTriggerReturnedInEvent(), moduleProperties.getAudioCapabilities()));
        }
        return 0;
    }

    public static SemSoundTriggerModule attachModule(int i, StatusListener statusListener, Handler handler) {
        return new SemSoundTriggerModule(i, statusListener, handler);
    }
}
