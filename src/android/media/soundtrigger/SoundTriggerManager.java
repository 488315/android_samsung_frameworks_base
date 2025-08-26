package android.media.soundtrigger;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.Identity;
import android.media.permission.SafeCloseable;
import android.media.soundtrigger.SoundTriggerDetector;
import android.media.soundtrigger.SoundTriggerInstrumentation;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.ServiceManager;
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
/* loaded from: classes3.dex */
public final class SoundTriggerManager {
    private static final boolean DBG = false;
    public static final String EXTRA_MESSAGE_TYPE = "android.media.soundtrigger.MESSAGE_TYPE";
    public static final String EXTRA_RECOGNITION_EVENT = "android.media.soundtrigger.RECOGNITION_EVENT";
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

    public SoundTriggerManager(Context context, ISoundTriggerService iSoundTriggerService) {
        Binder binder = new Binder();
        this.mBinderToken = binder;
        this.mReceiverInstanceMap = new HashMap<>();
        try {
            Identity identity = new Identity();
            identity.packageName = ActivityThread.currentOpPackageName();
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                SoundTrigger.ModuleProperties modulePropertiesOrElse = iSoundTriggerService.listModuleProperties(identity).stream().filter(new Predicate() { // from class: android.media.soundtrigger.SoundTriggerManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return SoundTriggerManager.lambda$new$0((SoundTrigger.ModuleProperties) obj);
                    }
                }).findFirst().orElse(null);
                if (modulePropertiesOrElse != null) {
                    this.mSoundTriggerSession = iSoundTriggerService.attachAsOriginator(identity, modulePropertiesOrElse, binder);
                } else {
                    this.mSoundTriggerSession = null;
                }
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
                this.mContext = context;
                this.mSoundTriggerService = iSoundTriggerService;
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$new$0(SoundTrigger.ModuleProperties moduleProperties) {
        return !moduleProperties.getSupportedModelArch().equals("injection");
    }

    public SoundTriggerManager createManagerForModule(SoundTrigger.ModuleProperties moduleProperties) {
        return new SoundTriggerManager(this.mContext, this.mSoundTriggerService, (SoundTrigger.ModuleProperties) Objects.requireNonNull(moduleProperties));
    }

    public SoundTriggerManager createManagerForTestModule() {
        return new SoundTriggerManager(this.mContext, this.mSoundTriggerService, getTestModuleProperties());
    }

    private final SoundTrigger.ModuleProperties getTestModuleProperties() {
        SoundTrigger.ModuleProperties modulePropertiesOrElse = listModuleProperties().stream().filter(new Predicate() { // from class: android.media.soundtrigger.SoundTriggerManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((SoundTrigger.ModuleProperties) obj).getSupportedModelArch().equals("injection");
            }
        }).findFirst().orElse(null);
        if (modulePropertiesOrElse != null) {
            return modulePropertiesOrElse;
        }
        throw new AssertionError("Fake ST HAL should always be available");
    }

    private SoundTriggerManager(Context context, ISoundTriggerService iSoundTriggerService, SoundTrigger.ModuleProperties moduleProperties) {
        Binder binder = new Binder();
        this.mBinderToken = binder;
        this.mReceiverInstanceMap = new HashMap<>();
        try {
            Identity identity = new Identity();
            identity.packageName = ActivityThread.currentOpPackageName();
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                this.mSoundTriggerSession = iSoundTriggerService.attachAsOriginator(identity, (SoundTrigger.ModuleProperties) Objects.requireNonNull(moduleProperties), binder);
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
                this.mContext = (Context) Objects.requireNonNull(context);
                this.mSoundTriggerService = (ISoundTriggerService) Objects.requireNonNull(iSoundTriggerService);
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static List<SoundTrigger.ModuleProperties> listModuleProperties() {
        try {
            ISoundTriggerService iSoundTriggerServiceAsInterface = ISoundTriggerService.Stub.asInterface(ServiceManager.getService(Context.SOUND_TRIGGER_SERVICE));
            Identity identity = new Identity();
            identity.packageName = ActivityThread.currentOpPackageName();
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                List<SoundTrigger.ModuleProperties> listListModuleProperties = iSoundTriggerServiceAsInterface.listModuleProperties(identity);
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
                return listListModuleProperties;
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
    public Model getModel(UUID uuid) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            SoundTrigger.GenericSoundModel soundModel = iSoundTriggerSession.getSoundModel(new ParcelUuid((UUID) Objects.requireNonNull(uuid)));
            if (soundModel == null) {
                return null;
            }
            return new Model(soundModel);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void deleteModel(UUID uuid) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            iSoundTriggerSession.deleteSoundModel(new ParcelUuid((UUID) Objects.requireNonNull(uuid)));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public SoundTriggerDetector createSoundTriggerDetector(UUID uuid, SoundTriggerDetector.Callback callback, Handler handler) {
        if (this.mSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        this.mReceiverInstanceMap.get(uuid);
        try {
            ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
            SoundTriggerDetector soundTriggerDetector = new SoundTriggerDetector(iSoundTriggerSession, iSoundTriggerSession.getSoundModel(new ParcelUuid((UUID) Objects.requireNonNull(uuid))), callback, handler);
            this.mReceiverInstanceMap.put(uuid, soundTriggerDetector);
            return soundTriggerDetector;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class Model {
        private SoundTrigger.GenericSoundModel mGenericSoundModel;

        Model(SoundTrigger.GenericSoundModel genericSoundModel) {
            this.mGenericSoundModel = genericSoundModel;
        }

        public static Model create(UUID uuid, UUID uuid2, byte[] bArr, int i) {
            Objects.requireNonNull(uuid);
            Objects.requireNonNull(uuid2);
            return new Model(new SoundTrigger.GenericSoundModel(uuid, uuid2, bArr, i));
        }

        public static Model create(UUID uuid, UUID uuid2, byte[] bArr) {
            return create(uuid, uuid2, bArr, -1);
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
            int type = soundModel.getType();
            if (type == 0) {
                return this.mSoundTriggerSession.loadKeyphraseSoundModel((SoundTrigger.KeyphraseSoundModel) soundModel);
            }
            if (type == 1) {
                return this.mSoundTriggerSession.loadGenericSoundModel((SoundTrigger.GenericSoundModel) soundModel);
            }
            Slog.e(TAG, "Unkown model type");
            return Integer.MIN_VALUE;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startRecognition(UUID uuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(componentName);
        Objects.requireNonNull(recognitionConfig);
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return iSoundTriggerSession.startRecognitionForService(new ParcelUuid(uuid), bundle, componentName, recognitionConfig);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int stopRecognition(UUID uuid) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return iSoundTriggerSession.stopRecognitionForService(new ParcelUuid((UUID) Objects.requireNonNull(uuid)));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int unloadSoundModel(UUID uuid) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return iSoundTriggerSession.unloadSoundModel(new ParcelUuid((UUID) Objects.requireNonNull(uuid)));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRecognitionActive(UUID uuid) {
        ISoundTriggerSession iSoundTriggerSession;
        if (uuid == null || (iSoundTriggerSession = this.mSoundTriggerSession) == null) {
            return false;
        }
        try {
            return iSoundTriggerSession.isRecognitionActive(new ParcelUuid(uuid));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDetectionServiceOperationsTimeout() {
        try {
            return Settings.Global.getInt(this.mContext.getContentResolver(), Settings.Global.SOUND_TRIGGER_DETECTION_SERVICE_OP_TIMEOUT);
        } catch (Settings.SettingNotFoundException unused) {
            return Integer.MAX_VALUE;
        }
    }

    public int getModelState(UUID uuid) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        if (uuid == null) {
            return Integer.MIN_VALUE;
        }
        try {
            return iSoundTriggerSession.getModelState(new ParcelUuid(uuid));
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

    public int setParameter(UUID uuid, int i, int i2) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return iSoundTriggerSession.setParameter(new ParcelUuid((UUID) Objects.requireNonNull(uuid)), i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getParameter(UUID uuid, int i) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return iSoundTriggerSession.getParameter(new ParcelUuid((UUID) Objects.requireNonNull(uuid)), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SoundTrigger.ModelParamRange queryParameter(UUID uuid, int i) {
        ISoundTriggerSession iSoundTriggerSession = this.mSoundTriggerSession;
        if (iSoundTriggerSession == null) {
            throw new IllegalStateException("No underlying SoundTriggerModule available");
        }
        try {
            return iSoundTriggerSession.queryParameter(new ParcelUuid((UUID) Objects.requireNonNull(uuid)), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static SoundTriggerInstrumentation attachInstrumentation(Executor executor, SoundTriggerInstrumentation.GlobalCallback globalCallback) {
        return new SoundTriggerInstrumentation(ISoundTriggerService.Stub.asInterface(ServiceManager.getService(Context.SOUND_TRIGGER_SERVICE)), executor, globalCallback);
    }
}
