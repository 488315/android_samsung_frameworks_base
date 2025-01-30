package android.media.soundtrigger;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.Identity;
import android.media.permission.SafeCloseable;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.ParcelUuid;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.app.ISoundTriggerService;
import com.android.internal.app.ISoundTriggerSession;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

@SystemApi
/* loaded from: classes2.dex */
public final class SoundTriggerManager {
  private static final boolean DBG = false;
  public static final String EXTRA_MESSAGE_TYPE = "android.media.soundtrigger.MESSAGE_TYPE";
  public static final String EXTRA_RECOGNITION_EVENT =
      "android.media.soundtrigger.RECOGNITION_EVENT";
  public static final String EXTRA_STATUS = "android.media.soundtrigger.STATUS";
  public static final int FLAG_MESSAGE_TYPE_RECOGNITION_ERROR = 1;
  public static final int FLAG_MESSAGE_TYPE_RECOGNITION_EVENT = 0;
  public static final int FLAG_MESSAGE_TYPE_RECOGNITION_PAUSED = 2;
  public static final int FLAG_MESSAGE_TYPE_RECOGNITION_RESUMED = 3;
  public static final int FLAG_MESSAGE_TYPE_UNKNOWN = -1;
  private static final String TAG = "SoundTriggerManager";
  private final IBinder mBinderToken;
  private final Context mContext;
  private final HashMap<UUID, SoundTriggerDetector> mReceiverInstanceMap;
  private final ISoundTriggerService mSoundTriggerService;
  private final ISoundTriggerSession mSoundTriggerSession;

  public SoundTriggerManager(Context context, ISoundTriggerService soundTriggerService) {
    Binder binder = new Binder();
    this.mBinderToken = binder;
    this.mReceiverInstanceMap = new HashMap<>();
    try {
      Identity originatorIdentity = new Identity();
      originatorIdentity.packageName = ActivityThread.currentOpPackageName();
      SafeCloseable ignored = ClearCallingIdentityContext.create();
      try {
        SoundTrigger.ModuleProperties moduleProperties =
            soundTriggerService.listModuleProperties(originatorIdentity).stream()
                .filter(
                    new Predicate() { // from class:
                                      // android.media.soundtrigger.SoundTriggerManager$$ExternalSyntheticLambda1
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        return SoundTriggerManager.lambda$new$0(
                            (SoundTrigger.ModuleProperties) obj);
                      }
                    })
                .findFirst()
                .orElse(null);
        if (moduleProperties != null) {
          this.mSoundTriggerSession =
              soundTriggerService.attachAsOriginator(originatorIdentity, moduleProperties, binder);
        } else {
          this.mSoundTriggerSession = null;
        }
        if (ignored != null) {
          ignored.close();
        }
        this.mContext = context;
        this.mSoundTriggerService = soundTriggerService;
      } finally {
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static /* synthetic */ boolean lambda$new$0(SoundTrigger.ModuleProperties prop) {
    return !prop.getSupportedModelArch().equals("injection");
  }

  public SoundTriggerManager createManagerForModule(
      SoundTrigger.ModuleProperties moduleProperties) {
    return new SoundTriggerManager(
        this.mContext,
        this.mSoundTriggerService,
        (SoundTrigger.ModuleProperties) Objects.requireNonNull(moduleProperties));
  }

  public SoundTriggerManager createManagerForTestModule() {
    return new SoundTriggerManager(
        this.mContext, this.mSoundTriggerService, getTestModuleProperties());
  }

  private final SoundTrigger.ModuleProperties getTestModuleProperties() {
    SoundTrigger.ModuleProperties moduleProps =
        listModuleProperties().stream()
            .filter(
                new Predicate() { // from class:
                                  // android.media.soundtrigger.SoundTriggerManager$$ExternalSyntheticLambda0
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean equals;
                    equals =
                        ((SoundTrigger.ModuleProperties) obj)
                            .getSupportedModelArch()
                            .equals("injection");
                    return equals;
                  }
                })
            .findFirst()
            .orElse(null);
    if (moduleProps == null) {
      throw new AssertionError("Fake ST HAL should always be available");
    }
    return moduleProps;
  }

  private SoundTriggerManager(
      Context context,
      ISoundTriggerService soundTriggerService,
      SoundTrigger.ModuleProperties properties) {
    Binder binder = new Binder();
    this.mBinderToken = binder;
    this.mReceiverInstanceMap = new HashMap<>();
    try {
      Identity originatorIdentity = new Identity();
      originatorIdentity.packageName = ActivityThread.currentOpPackageName();
      SafeCloseable ignored = ClearCallingIdentityContext.create();
      try {
        this.mSoundTriggerSession =
            soundTriggerService.attachAsOriginator(
                originatorIdentity,
                (SoundTrigger.ModuleProperties) Objects.requireNonNull(properties),
                binder);
        if (ignored != null) {
          ignored.close();
        }
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mSoundTriggerService =
            (ISoundTriggerService) Objects.requireNonNull(soundTriggerService);
      } finally {
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public static List<SoundTrigger.ModuleProperties> listModuleProperties() {
    try {
      ISoundTriggerService service =
          ISoundTriggerService.Stub.asInterface(
              ServiceManager.getService(Context.SOUND_TRIGGER_SERVICE));
      Identity originatorIdentity = new Identity();
      originatorIdentity.packageName = ActivityThread.currentOpPackageName();
      SafeCloseable ignored = ClearCallingIdentityContext.create();
      try {
        List<SoundTrigger.ModuleProperties> listModuleProperties =
            service.listModuleProperties(originatorIdentity);
        if (ignored != null) {
          ignored.close();
        }
        return listModuleProperties;
      } finally {
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Deprecated
  public void updateModel(Model model) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      iSoundTriggerSession.updateSoundModel(model.getGenericSoundModel());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Deprecated
  public Model getModel(UUID soundModelId) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      SoundTrigger.GenericSoundModel model =
          iSoundTriggerSession.getSoundModel(
              new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)));
      if (model == null) {
        return null;
      }
      return new Model(model);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Deprecated
  public void deleteModel(UUID soundModelId) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      iSoundTriggerSession.deleteSoundModel(
          new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)));
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @Deprecated
  public SoundTriggerDetector createSoundTriggerDetector(
      UUID soundModelId, SoundTriggerDetector.Callback callback, Handler handler) {
    if (this.mSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    this.mReceiverInstanceMap.get(soundModelId);
    try {
      ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
      SoundTriggerDetector newInstance =
          new SoundTriggerDetector(
              iSoundTriggerSession,
              iSoundTriggerSession.getSoundModel(
                  new ParcelUuid((UUID) Objects.requireNonNull(soundModelId))),
              callback,
              handler);
      this.mReceiverInstanceMap.put(soundModelId, newInstance);
      return newInstance;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public static class Model {
    private SoundTrigger.GenericSoundModel mGenericSoundModel;

    Model(SoundTrigger.GenericSoundModel soundTriggerModel) {
      this.mGenericSoundModel = soundTriggerModel;
    }

    public static Model create(UUID modelUuid, UUID vendorUuid, byte[] data, int version) {
      Objects.requireNonNull(modelUuid);
      Objects.requireNonNull(vendorUuid);
      return new Model(new SoundTrigger.GenericSoundModel(modelUuid, vendorUuid, data, version));
    }

    public static Model create(UUID modelUuid, UUID vendorUuid, byte[] data) {
      return create(modelUuid, vendorUuid, data, -1);
    }

    public UUID getModelUuid() {
      return this.mGenericSoundModel.getUuid();
    }

    public UUID getVendorUuid() {
      return this.mGenericSoundModel.getVendorUuid();
    }

    public int getVersion() {
      return this.mGenericSoundModel.getVersion();
    }

    public byte[] getModelData() {
      return this.mGenericSoundModel.getData();
    }

    SoundTrigger.GenericSoundModel getGenericSoundModel() {
      return this.mGenericSoundModel;
    }

    public SoundTrigger.SoundModel getSoundModel() {
      return this.mGenericSoundModel;
    }
  }

  public int loadSoundModel(SoundTrigger.SoundModel soundModel) {
    if (this.mSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      switch (soundModel.getType()) {
        case 0:
          return this.mSoundTriggerSession.loadKeyphraseSoundModel(
              (SoundTrigger.KeyphraseSoundModel) soundModel);
        case 1:
          return this.mSoundTriggerSession.loadGenericSoundModel(
              (SoundTrigger.GenericSoundModel) soundModel);
        default:
          Slog.m115e(TAG, "Unkown model type");
          return Integer.MIN_VALUE;
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int startRecognition(
      UUID soundModelId,
      Bundle params,
      ComponentName detectionService,
      SoundTrigger.RecognitionConfig config) {
    Objects.requireNonNull(soundModelId);
    Objects.requireNonNull(detectionService);
    Objects.requireNonNull(config);
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      return iSoundTriggerSession.startRecognitionForService(
          new ParcelUuid(soundModelId), params, detectionService, config);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int stopRecognition(UUID soundModelId) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      return iSoundTriggerSession.stopRecognitionForService(
          new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)));
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int unloadSoundModel(UUID soundModelId) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      return iSoundTriggerSession.unloadSoundModel(
          new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)));
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isRecognitionActive(UUID soundModelId) {
    ISoundTriggerSession iSoundTriggerSession;
    if (soundModelId == null || (iSoundTriggerSession = this.mSoundTriggerSession) == null) {
      return false;
    }
    try {
      return iSoundTriggerSession.isRecognitionActive(new ParcelUuid(soundModelId));
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int getDetectionServiceOperationsTimeout() {
    try {
      return Settings.Global.getInt(
          this.mContext.getContentResolver(),
          Settings.Global.SOUND_TRIGGER_DETECTION_SERVICE_OP_TIMEOUT);
    } catch (Settings.SettingNotFoundException e) {
      return Integer.MAX_VALUE;
    }
  }

  public int getModelState(UUID soundModelId) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    if (soundModelId == null) {
      return Integer.MIN_VALUE;
    }
    try {
      return iSoundTriggerSession.getModelState(new ParcelUuid(soundModelId));
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public SoundTrigger.ModuleProperties getModuleProperties() {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      return null;
    }
    try {
      return iSoundTriggerSession.getModuleProperties();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int setParameter(UUID soundModelId, int modelParam, int value) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      return iSoundTriggerSession.setParameter(
          new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)), modelParam, value);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int getParameter(UUID soundModelId, int modelParam) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      return iSoundTriggerSession.getParameter(
          new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)), modelParam);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public SoundTrigger.ModelParamRange queryParameter(UUID soundModelId, int modelParam) {
    ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
    if (iSoundTriggerSession == null) {
      throw new IllegalStateException("No underlying SoundTriggerModule available");
    }
    try {
      return iSoundTriggerSession.queryParameter(
          new ParcelUuid((UUID) Objects.requireNonNull(soundModelId)), modelParam);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public static SoundTriggerInstrumentation attachInstrumentation(
      Executor executor, SoundTriggerInstrumentation.GlobalCallback callback) {
    ISoundTriggerService service =
        ISoundTriggerService.Stub.asInterface(
            ServiceManager.getService(Context.SOUND_TRIGGER_SERVICE));
    return new SoundTriggerInstrumentation(service, executor, callback);
  }
}
