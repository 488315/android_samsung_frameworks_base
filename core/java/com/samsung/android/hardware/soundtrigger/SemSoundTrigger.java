package com.samsung.android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.p009os.Binder;
import android.p009os.Handler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes5.dex */
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

  private SemSoundTrigger() {}

  public static class ModuleProperties {

    /* renamed from: id */
    public final int f3003id;
    public final boolean supportsConcurrentCapture;
    public final UUID uuid;

    ModuleProperties(
        int id,
        String implementor,
        String description,
        String uuid,
        int version,
        String supportedModelArch,
        int maxSoundModels,
        int maxKeyphrases,
        int maxUsers,
        int recognitionModes,
        boolean supportsCaptureTransition,
        int maxBufferMs,
        boolean supportsConcurrentCapture,
        int powerConsumptionMw,
        boolean returnsTriggerInEvent,
        int audioCapabilities) {
      this.f3003id = id;
      this.uuid = UUID.fromString(uuid);
      this.supportsConcurrentCapture = supportsConcurrentCapture;
    }

    public String toString() {
      return "ModuleProperties [id="
          + this.f3003id
          + ", uuid="
          + this.uuid
          + ", supportsConcurrentCapture="
          + this.supportsConcurrentCapture
          + NavigationBarInflaterView.SIZE_MOD_END;
    }
  }

  public static class Keyphrase {

    /* renamed from: id */
    private final int f3001id;
    SoundTrigger.Keyphrase instance;
    private final Locale locale;
    private final int recognitionModes;
    private final String text;
    private final int[] users;

    public Keyphrase(int id, int recognitionModes, String locale, String text, int[] users) {
      this.f3001id = id;
      this.recognitionModes = recognitionModes;
      Locale locale2 = new Locale(locale);
      this.locale = locale2;
      this.text = text;
      this.users = users;
      this.instance = new SoundTrigger.Keyphrase(id, recognitionModes, locale2, text, users);
    }

    public String toString() {
      return "Keyphrase [id="
          + this.f3001id
          + ", recognitionModes="
          + this.recognitionModes
          + ", locale="
          + this.locale
          + ", text="
          + this.text
          + ", users="
          + Arrays.toString(this.users)
          + NavigationBarInflaterView.SIZE_MOD_END;
    }
  }

  public static class KeyphraseSoundModel {
    SoundTrigger.KeyphraseSoundModel instance;
    public final Keyphrase[] keyphrases;
    public final UUID uuid;

    public KeyphraseSoundModel(UUID uuid, UUID vendorUuid, byte[] data, Keyphrase[] keyphrases) {
      SoundTrigger.Keyphrase[] stKeyphrases = new SoundTrigger.Keyphrase[keyphrases.length];
      for (int i = 0; i < keyphrases.length; i++) {
        stKeyphrases[i] =
            new SoundTrigger.Keyphrase(
                keyphrases[i].f3001id,
                keyphrases[i].recognitionModes,
                keyphrases[i].locale,
                keyphrases[i].text,
                keyphrases[i].users);
      }
      this.instance = new SoundTrigger.KeyphraseSoundModel(uuid, vendorUuid, data, stKeyphrases);
      this.keyphrases = keyphrases;
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

    RecognitionEvent(
        int status,
        int soundModelHandle,
        boolean captureAvailable,
        int captureSession,
        int captureDelayMs,
        int capturePreambleMs,
        boolean triggerInData,
        AudioFormat captureFormat,
        byte[] data) {
      this.status = status;
      this.soundModelHandle = soundModelHandle;
      this.captureAvailable = captureAvailable;
      this.captureSession = captureSession;
      this.captureDelayMs = captureDelayMs;
      this.capturePreambleMs = capturePreambleMs;
      this.triggerInData = triggerInData;
      this.captureFormat = captureFormat;
      this.data = data;
    }

    public String toString() {
      StringBuilder append =
          new StringBuilder()
              .append("RecognitionEvent [status=")
              .append(this.status)
              .append(", soundModelHandle=")
              .append(this.soundModelHandle)
              .append(", captureAvailable=")
              .append(this.captureAvailable)
              .append(", captureSession=")
              .append(this.captureSession)
              .append(", captureDelayMs=")
              .append(this.captureDelayMs)
              .append(", capturePreambleMs=")
              .append(this.capturePreambleMs)
              .append(", triggerInData=")
              .append(this.triggerInData)
              .append(
                  this.captureFormat == null
                      ? ""
                      : ", sampleRate=" + this.captureFormat.getSampleRate())
              .append(
                  this.captureFormat == null
                      ? ""
                      : ", encoding=" + this.captureFormat.getEncoding())
              .append(
                  this.captureFormat != null
                      ? ", channelMask=" + this.captureFormat.getChannelMask()
                      : "")
              .append(", data=");
      byte[] bArr = this.data;
      return append
          .append(bArr == null ? 0 : bArr.length)
          .append(NavigationBarInflaterView.SIZE_MOD_END)
          .toString();
    }
  }

  public static class RecognitionConfig {
    private final boolean allowMultipleTriggers;
    private final boolean captureRequested;
    private final byte[] data;
    SoundTrigger.RecognitionConfig instance;
    private final KeyphraseRecognitionExtra[] keyphrases;

    public RecognitionConfig(
        boolean captureRequested,
        boolean allowMultipleTriggers,
        KeyphraseRecognitionExtra[] keyphrases,
        byte[] data) {
      SoundTrigger.KeyphraseRecognitionExtra[] stKeyphrases =
          new SoundTrigger.KeyphraseRecognitionExtra[keyphrases.length];
      for (int i = 0; i < keyphrases.length; i++) {
        SoundTrigger.ConfidenceLevel[] confidenceLevels =
            new SoundTrigger.ConfidenceLevel[keyphrases[i].confidenceLevels.length];
        for (int j = 0; j < keyphrases[i].confidenceLevels.length; j++) {
          confidenceLevels[j] =
              new SoundTrigger.ConfidenceLevel(
                  keyphrases[i].confidenceLevels[j].userId,
                  keyphrases[i].confidenceLevels[j].confidenceLevel);
        }
        stKeyphrases[i] =
            new SoundTrigger.KeyphraseRecognitionExtra(
                keyphrases[i].f3002id,
                keyphrases[i].recognitionModes,
                keyphrases[i].coarseConfidenceLevel,
                confidenceLevels);
      }
      this.instance =
          new SoundTrigger.RecognitionConfig(
              captureRequested, allowMultipleTriggers, stKeyphrases, data);
      this.captureRequested = captureRequested;
      this.allowMultipleTriggers = allowMultipleTriggers;
      this.keyphrases = keyphrases;
      this.data = data;
    }
  }

  public static class ConfidenceLevel {
    public final int confidenceLevel;
    SoundTrigger.ConfidenceLevel instance;
    public final int userId;

    public ConfidenceLevel(int userId, int confidenceLevel) {
      SoundTrigger.ConfidenceLevel confidenceLevel2 =
          new SoundTrigger.ConfidenceLevel(userId, confidenceLevel);
      this.instance = confidenceLevel2;
      this.userId = confidenceLevel2.userId;
      this.confidenceLevel = this.instance.confidenceLevel;
    }
  }

  public static class KeyphraseRecognitionExtra {
    public final int coarseConfidenceLevel;
    public final ConfidenceLevel[] confidenceLevels;

    /* renamed from: id */
    public final int f3002id;
    SoundTrigger.KeyphraseRecognitionExtra instance;
    public final int recognitionModes;

    public KeyphraseRecognitionExtra(
        int id,
        int recognitionModes,
        int coarseConfidenceLevel,
        ConfidenceLevel[] confidenceLevels) {
      SoundTrigger.ConfidenceLevel[] stConfidenceLevels =
          new SoundTrigger.ConfidenceLevel[confidenceLevels.length];
      for (int i = 0; i < confidenceLevels.length; i++) {
        stConfidenceLevels[i] =
            new SoundTrigger.ConfidenceLevel(
                confidenceLevels[i].userId, confidenceLevels[i].confidenceLevel);
      }
      this.instance =
          new SoundTrigger.KeyphraseRecognitionExtra(
              id, recognitionModes, coarseConfidenceLevel, stConfidenceLevels);
      this.f3002id = id;
      this.recognitionModes = recognitionModes;
      this.coarseConfidenceLevel = coarseConfidenceLevel;
      this.confidenceLevels = confidenceLevels;
    }
  }

  public static class KeyphraseRecognitionEvent extends RecognitionEvent {
    public final KeyphraseRecognitionExtra[] keyphraseExtras;

    public KeyphraseRecognitionEvent(
        int status,
        int soundModelHandle,
        boolean captureAvailable,
        int captureSession,
        int captureDelayMs,
        int capturePreambleMs,
        boolean triggerInData,
        AudioFormat captureFormat,
        byte[] data,
        KeyphraseRecognitionExtra[] keyphraseExtras) {
      super(
          status,
          soundModelHandle,
          captureAvailable,
          captureSession,
          captureDelayMs,
          capturePreambleMs,
          triggerInData,
          captureFormat,
          data);
      this.keyphraseExtras = keyphraseExtras;
    }

    @Override // com.samsung.android.hardware.soundtrigger.SemSoundTrigger.RecognitionEvent
    public String toString() {
      return "KeyphraseRecognitionEvent [keyphraseExtras="
          + Arrays.toString(this.keyphraseExtras)
          + ", status="
          + this.status
          + ", soundModelHandle="
          + this.soundModelHandle
          + ", captureAvailable="
          + this.captureAvailable
          + ", captureSession="
          + this.captureSession
          + ", captureDelayMs="
          + this.captureDelayMs
          + ", capturePreambleMs="
          + this.capturePreambleMs
          + ", triggerInData="
          + this.triggerInData
          + (this.captureFormat == null ? "" : ", sampleRate=" + this.captureFormat.getSampleRate())
          + (this.captureFormat == null ? "" : ", encoding=" + this.captureFormat.getEncoding())
          + (this.captureFormat != null
              ? ", channelMask=" + this.captureFormat.getChannelMask()
              : "")
          + ", data="
          + (this.data == null ? 0 : this.data.length)
          + NavigationBarInflaterView.SIZE_MOD_END;
    }
  }

  public static int listModules(ArrayList<ModuleProperties> modules) {
    ArrayList<SoundTrigger.ModuleProperties> soundTriggerModules = new ArrayList<>();
    Identity originatorIdentity = new Identity();
    originatorIdentity.pid = Binder.getCallingPid();
    originatorIdentity.uid = Binder.getCallingUid();
    SoundTrigger.listModulesAsOriginator(soundTriggerModules, originatorIdentity);
    Iterator<SoundTrigger.ModuleProperties> it = soundTriggerModules.iterator();
    while (it.hasNext()) {
      SoundTrigger.ModuleProperties module = it.next();
      ModuleProperties moduleProperties =
          new ModuleProperties(
              module.getId(),
              module.getImplementor(),
              module.getDescription(),
              module.getUuid().toString(),
              module.getVersion(),
              module.getSupportedModelArch(),
              module.getMaxSoundModels(),
              module.getMaxKeyphrases(),
              module.getMaxUsers(),
              module.getRecognitionModes(),
              module.isCaptureTransitionSupported(),
              module.getMaxBufferMillis(),
              module.isConcurrentCaptureSupported(),
              module.getPowerConsumptionMw(),
              module.isTriggerReturnedInEvent(),
              module.getAudioCapabilities());
      modules.add(moduleProperties);
    }
    return 0;
  }

  public static SemSoundTriggerModule attachModule(
      int moduleId, StatusListener listener, Handler handler) {
    return new SemSoundTriggerModule(moduleId, listener, handler);
  }
}
