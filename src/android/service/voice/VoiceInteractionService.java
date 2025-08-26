package android.service.voice;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.Service;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.soundtrigger.KeyphraseEnrollmentInfo;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.Identity;
import android.media.voice.KeyphraseModelManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SharedMemory;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.voice.AlwaysOnHotwordDetector;
import android.service.voice.HotwordDetector;
import android.service.voice.IVoiceInteractionService;
import android.service.voice.VisualQueryDetector;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.app.IVoiceActionCheckCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class VoiceInteractionService extends Service {
    static final long MULTIPLE_ACTIVE_HOTWORD_DETECTORS = 193232191;
    public static final String SERVICE_INTERFACE = "android.service.voice.VoiceInteractionService";
    public static final String SERVICE_META_DATA = "android.voice_interaction";
    private static final boolean SYSPROP_VISUAL_QUERY_SERVICE_ENABLED = SystemProperties.getBoolean("ro.hotword.visual_query_service_enabled", false);
    static final String TAG = "VoiceInteractionService";
    private VisualQueryDetector mActiveVisualQueryDetector;
    private KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    IVoiceInteractionManagerService mSystemService;
    IVoiceInteractionService mInterface = new AnonymousClass1();
    private final Object mLock = new Object();
    private final Set<HotwordDetector> mActiveDetectors = new ArraySet();
    private boolean mTestModuleForAlwaysOnHotwordDetectorEnabled = false;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.f$0.lambda$new$1();
        }
    };

    public void onLaunchVoiceAssistFromKeyguard() {
    }

    public void onPrepareToShowSession(Bundle bundle, int i) {
    }

    public void onShowSessionFailed(Bundle bundle) {
    }

    public void onShutdown() {
    }

    /* renamed from: android.service.voice.VoiceInteractionService$1, reason: invalid class name */
    class AnonymousClass1 extends IVoiceInteractionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void ready() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((VoiceInteractionService) obj).onReady();
                }
            }, VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void shutdown() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((VoiceInteractionService) obj).onShutdownInternal();
                }
            }, VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void soundModelsChanged() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((VoiceInteractionService) obj).onSoundModelsChangedInternal();
                }
            }, VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void launchVoiceAssistFromKeyguard() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((VoiceInteractionService) obj).onLaunchVoiceAssistFromKeyguard();
                }
            }, VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((VoiceInteractionService) obj).onHandleVoiceActionCheck((List) obj2, (IVoiceActionCheckCallback) obj3);
                }
            }, VoiceInteractionService.this, list, iVoiceActionCheckCallback));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void prepareToShowSession(Bundle bundle, int i) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((VoiceInteractionService) obj).onPrepareToShowSession((Bundle) obj2, ((Integer) obj3).intValue());
                }
            }, VoiceInteractionService.this, bundle, Integer.valueOf(i)));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void showSessionFailed(Bundle bundle) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((VoiceInteractionService) obj).onShowSessionFailed((Bundle) obj2);
                }
            }, VoiceInteractionService.this, bundle));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void detectorRemoteExceptionOccurred(IBinder iBinder, int i) {
            Log.d(VoiceInteractionService.TAG, "detectorRemoteExceptionOccurred");
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((VoiceInteractionService) obj).onDetectorRemoteException((IBinder) obj2, ((Integer) obj3).intValue());
                }
            }, VoiceInteractionService.this, iBinder, Integer.valueOf(i)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDetectorRemoteException(final IBinder iBinder, final int i) {
        Log.d(TAG, "onDetectorRemoteException for " + HotwordDetector.detectorTypeToString(i));
        this.mActiveDetectors.forEach(new Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                VoiceInteractionService.lambda$onDetectorRemoteException$0(i, iBinder, (HotwordDetector) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onDetectorRemoteException$0(int i, IBinder iBinder, HotwordDetector hotwordDetector) {
        if (i == 1 && (hotwordDetector instanceof AlwaysOnHotwordDetector)) {
            AlwaysOnHotwordDetector alwaysOnHotwordDetector = (AlwaysOnHotwordDetector) hotwordDetector;
            if (alwaysOnHotwordDetector.isSameToken(iBinder)) {
                alwaysOnHotwordDetector.onDetectorRemoteException();
                return;
            }
            return;
        }
        if (i == 2 && (hotwordDetector instanceof SoftwareHotwordDetector)) {
            SoftwareHotwordDetector softwareHotwordDetector = (SoftwareHotwordDetector) hotwordDetector;
            if (softwareHotwordDetector.isSameToken(iBinder)) {
                softwareHotwordDetector.onDetectorRemoteException();
            }
        }
    }

    public static boolean isActiveService(Context context, ComponentName componentName) {
        ComponentName componentNameUnflattenFromString;
        String string = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.VOICE_INTERACTION_SERVICE);
        if (string == null || string.isEmpty() || (componentNameUnflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return false;
        }
        return componentNameUnflattenFromString.equals(componentName);
    }

    public void setDisabledShowContext(int i) {
        try {
            this.mSystemService.setDisabledShowContext(i);
        } catch (RemoteException unused) {
        }
    }

    public int getDisabledShowContext() {
        try {
            return this.mSystemService.getDisabledShowContext();
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public void showSession(Bundle bundle, int i) {
        IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mSystemService;
        if (iVoiceInteractionManagerService == null) {
            throw new IllegalStateException("Not available until onReady() is called");
        }
        try {
            iVoiceInteractionManagerService.showSession(bundle, i, getAttributionTag());
        } catch (RemoteException unused) {
        }
    }

    public Set<String> onGetSupportedVoiceActions(Set<String> set) {
        return Collections.EMPTY_SET;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        return null;
    }

    public void onReady() {
        IVoiceInteractionManagerService iVoiceInteractionManagerServiceAsInterface = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService(Context.VOICE_INTERACTION_MANAGER_SERVICE));
        this.mSystemService = iVoiceInteractionManagerServiceAsInterface;
        Objects.requireNonNull(iVoiceInteractionManagerServiceAsInterface);
        try {
            this.mSystemService.asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (RemoteException unused) {
            Log.wtf(TAG, "unable to link to death with system service");
        }
        this.mKeyphraseEnrollmentInfo = new KeyphraseEnrollmentInfo(getPackageManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        Log.e(TAG, "system service binder died shutting down");
        Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((VoiceInteractionService) obj).onShutdownInternal();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShutdownInternal() {
        onShutdown();
        safelyShutdownAllHotwordDetectors(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSoundModelsChangedInternal() {
        synchronized (this) {
            this.mActiveDetectors.forEach(new Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    VoiceInteractionService.lambda$onSoundModelsChangedInternal$2((HotwordDetector) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$onSoundModelsChangedInternal$2(HotwordDetector hotwordDetector) {
        if (hotwordDetector instanceof AlwaysOnHotwordDetector) {
            ((AlwaysOnHotwordDetector) hotwordDetector).onSoundModelsChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHandleVoiceActionCheck(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        if (iVoiceActionCheckCallback != null) {
            try {
                iVoiceActionCheckCallback.onComplete(new ArrayList(onGetSupportedVoiceActions(new ArraySet(list))));
            } catch (RemoteException unused) {
            }
        }
    }

    public final List<SoundTrigger.ModuleProperties> listModuleProperties() {
        Identity identity = new Identity();
        identity.packageName = ActivityThread.currentOpPackageName();
        try {
            return this.mSystemService.listModuleProperties(identity);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(String str, Locale locale, AlwaysOnHotwordDetector.Callback callback) {
        return createAlwaysOnHotwordDetectorInternal(str, locale, false, null, null, null, null, callback);
    }

    @SystemApi
    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(String str, Locale locale, Executor executor, AlwaysOnHotwordDetector.Callback callback) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(locale);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, false, null, null, null, executor, callback);
    }

    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetectorForTest(String str, Locale locale, SoundTrigger.ModuleProperties moduleProperties, Executor executor, AlwaysOnHotwordDetector.Callback callback) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(locale);
        Objects.requireNonNull(moduleProperties);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, false, null, null, moduleProperties, executor, callback);
    }

    @SystemApi
    @Deprecated
    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(String str, Locale locale, PersistableBundle persistableBundle, SharedMemory sharedMemory, AlwaysOnHotwordDetector.Callback callback) {
        return createAlwaysOnHotwordDetectorInternal(str, locale, true, persistableBundle, sharedMemory, null, null, callback);
    }

    @SystemApi
    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(String str, Locale locale, PersistableBundle persistableBundle, SharedMemory sharedMemory, Executor executor, AlwaysOnHotwordDetector.Callback callback) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(locale);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, true, persistableBundle, sharedMemory, null, executor, callback);
    }

    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetectorForTest(String str, Locale locale, PersistableBundle persistableBundle, SharedMemory sharedMemory, SoundTrigger.ModuleProperties moduleProperties, Executor executor, AlwaysOnHotwordDetector.Callback callback) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(locale);
        Objects.requireNonNull(moduleProperties);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, true, persistableBundle, sharedMemory, moduleProperties, executor, callback);
    }

    private AlwaysOnHotwordDetector createAlwaysOnHotwordDetectorInternal(String str, Locale locale, boolean z, PersistableBundle persistableBundle, SharedMemory sharedMemory, SoundTrigger.ModuleProperties moduleProperties, Executor executor, AlwaysOnHotwordDetector.Callback callback) {
        AlwaysOnHotwordDetector alwaysOnHotwordDetector;
        if (this.mSystemService == null) {
            throw new IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            if (!CompatChanges.isChangeEnabled(MULTIPLE_ACTIVE_HOTWORD_DETECTORS)) {
                safelyShutdownAllHotwordDetectors(false);
            } else {
                for (HotwordDetector hotwordDetector : this.mActiveDetectors) {
                    if (hotwordDetector.isUsingSandboxedDetectionService() != z) {
                        throw new IllegalStateException("It disallows to create trusted and non-trusted detectors at the same time.");
                    }
                    if (hotwordDetector instanceof AlwaysOnHotwordDetector) {
                        throw new IllegalStateException("There is already an active AlwaysOnHotwordDetector. It must be destroyed to create a new one.");
                    }
                }
            }
            alwaysOnHotwordDetector = new AlwaysOnHotwordDetector(str, locale, executor, callback, this.mKeyphraseEnrollmentInfo, this.mSystemService, getApplicationContext().getApplicationInfo().targetSdkVersion, z, getAttributionTag());
            this.mActiveDetectors.add(alwaysOnHotwordDetector);
            try {
                alwaysOnHotwordDetector.registerOnDestroyListener(new VoiceInteractionService$$ExternalSyntheticLambda0(this));
                alwaysOnHotwordDetector.initialize(persistableBundle, sharedMemory, this.mTestModuleForAlwaysOnHotwordDetectorEnabled ? getTestModuleProperties() : moduleProperties);
            } catch (Exception e) {
                this.mActiveDetectors.remove(alwaysOnHotwordDetector);
                alwaysOnHotwordDetector.destroy();
                throw e;
            }
        }
        return alwaysOnHotwordDetector;
    }

    @SystemApi
    @Deprecated
    public final HotwordDetector createHotwordDetector(PersistableBundle persistableBundle, SharedMemory sharedMemory, HotwordDetector.Callback callback) {
        return createHotwordDetectorInternal(persistableBundle, sharedMemory, null, callback);
    }

    @SystemApi
    public final HotwordDetector createHotwordDetector(PersistableBundle persistableBundle, SharedMemory sharedMemory, Executor executor, HotwordDetector.Callback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        return createHotwordDetectorInternal(persistableBundle, sharedMemory, executor, callback);
    }

    private HotwordDetector createHotwordDetectorInternal(PersistableBundle persistableBundle, SharedMemory sharedMemory, Executor executor, HotwordDetector.Callback callback) {
        SoftwareHotwordDetector softwareHotwordDetector;
        if (this.mSystemService == null) {
            throw new IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            if (!CompatChanges.isChangeEnabled(MULTIPLE_ACTIVE_HOTWORD_DETECTORS)) {
                safelyShutdownAllHotwordDetectors(false);
            } else {
                for (HotwordDetector hotwordDetector : this.mActiveDetectors) {
                    if (!hotwordDetector.isUsingSandboxedDetectionService()) {
                        throw new IllegalStateException("It disallows to create trusted and non-trusted detectors at the same time.");
                    }
                    if (hotwordDetector instanceof SoftwareHotwordDetector) {
                        throw new IllegalStateException("There is already an active SoftwareHotwordDetector. It must be destroyed to create a new one.");
                    }
                }
            }
            softwareHotwordDetector = new SoftwareHotwordDetector(this.mSystemService, null, executor, callback, getAttributionTag());
            this.mActiveDetectors.add(softwareHotwordDetector);
            try {
                softwareHotwordDetector.registerOnDestroyListener(new VoiceInteractionService$$ExternalSyntheticLambda0(this));
                softwareHotwordDetector.initialize(persistableBundle, sharedMemory);
            } catch (Exception e) {
                this.mActiveDetectors.remove(softwareHotwordDetector);
                softwareHotwordDetector.destroy();
                throw e;
            }
        }
        return softwareHotwordDetector;
    }

    @SystemApi
    public final VisualQueryDetector createVisualQueryDetector(PersistableBundle persistableBundle, SharedMemory sharedMemory, Executor executor, VisualQueryDetector.Callback callback) {
        VisualQueryDetector visualQueryDetector;
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        if (!SYSPROP_VISUAL_QUERY_SERVICE_ENABLED) {
            throw new IllegalStateException("VisualQueryDetectionService is not enabled on this system. Please set ro.hotword.visual_query_service_enabled to true.");
        }
        if (this.mSystemService == null) {
            throw new IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            if (this.mActiveVisualQueryDetector != null) {
                throw new IllegalStateException("There is already an active VisualQueryDetector. It must be destroyed to create a new one.");
            }
            Iterator<HotwordDetector> it = this.mActiveDetectors.iterator();
            while (it.hasNext()) {
                if (!it.next().isUsingSandboxedDetectionService()) {
                    throw new IllegalStateException("It disallows to create trusted and non-trusted detectors at the same time.");
                }
            }
            visualQueryDetector = new VisualQueryDetector(this.mSystemService, executor, callback, this, getAttributionTag());
            HotwordDetector initializationDelegate = visualQueryDetector.getInitializationDelegate();
            this.mActiveDetectors.add(initializationDelegate);
            try {
                visualQueryDetector.registerOnDestroyListener(new VoiceInteractionService$$ExternalSyntheticLambda0(this));
                visualQueryDetector.initialize(persistableBundle, sharedMemory);
                this.mActiveVisualQueryDetector = visualQueryDetector;
            } catch (Exception e) {
                this.mActiveDetectors.remove(initializationDelegate);
                visualQueryDetector.destroy();
                throw e;
            }
        }
        return visualQueryDetector;
    }

    @SystemApi
    public final KeyphraseModelManager createKeyphraseModelManager() {
        KeyphraseModelManager keyphraseModelManager;
        if (this.mSystemService == null) {
            throw new IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            keyphraseModelManager = new KeyphraseModelManager(this.mSystemService);
        }
        return keyphraseModelManager;
    }

    protected final KeyphraseEnrollmentInfo getKeyphraseEnrollmentInfo() {
        return this.mKeyphraseEnrollmentInfo;
    }

    public final void setTestModuleForAlwaysOnHotwordDetectorEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mTestModuleForAlwaysOnHotwordDetectorEnabled = z;
        }
    }

    private final SoundTrigger.ModuleProperties getTestModuleProperties() {
        SoundTrigger.ModuleProperties modulePropertiesOrElse = listModuleProperties().stream().filter(new Predicate() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((SoundTrigger.ModuleProperties) obj).getSupportedModelArch().equals("injection");
            }
        }).findFirst().orElse(null);
        if (modulePropertiesOrElse != null) {
            return modulePropertiesOrElse;
        }
        throw new IllegalStateException("Fake ST HAL should always be available");
    }

    public final boolean isKeyphraseAndLocaleSupportedForHotword(String str, Locale locale) {
        KeyphraseEnrollmentInfo keyphraseEnrollmentInfo = this.mKeyphraseEnrollmentInfo;
        return (keyphraseEnrollmentInfo == null || keyphraseEnrollmentInfo.getKeyphraseMetadata(str, locale) == null) ? false : true;
    }

    private void safelyShutdownAllHotwordDetectors(final boolean z) {
        synchronized (this.mLock) {
            this.mActiveDetectors.forEach(new Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$safelyShutdownAllHotwordDetectors$4(z, (HotwordDetector) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safelyShutdownAllHotwordDetectors$4(boolean z, HotwordDetector hotwordDetector) {
        try {
            VisualQueryDetector visualQueryDetector = this.mActiveVisualQueryDetector;
            if (visualQueryDetector == null || hotwordDetector != visualQueryDetector.getInitializationDelegate() || z) {
                hotwordDetector.destroy();
            }
        } catch (Exception e) {
            Log.i(TAG, "exception destroying HotwordDetector", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHotwordDetectorDestroyed(HotwordDetector hotwordDetector) {
        synchronized (this.mLock) {
            VisualQueryDetector visualQueryDetector = this.mActiveVisualQueryDetector;
            if (visualQueryDetector != null && hotwordDetector == visualQueryDetector.getInitializationDelegate()) {
                this.mActiveVisualQueryDetector = null;
            }
            this.mActiveDetectors.remove(hotwordDetector);
        }
    }

    public final void setUiHints(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Hints must be non-null");
        }
        try {
            this.mSystemService.setUiHints(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        printWriter.println("VOICE INTERACTION");
        synchronized (this.mLock) {
            printWriter.println("  Sandboxed Detector(s):");
            if (this.mActiveDetectors.size() == 0) {
                printWriter.println("    No detector.");
            } else {
                this.mActiveDetectors.forEach(new Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        VoiceInteractionService.lambda$dump$5(printWriter, (HotwordDetector) obj);
                    }
                });
            }
            printWriter.println("Available Model Enrollment Applications:");
            printWriter.println("  " + this.mKeyphraseEnrollmentInfo);
        }
    }

    static /* synthetic */ void lambda$dump$5(PrintWriter printWriter, HotwordDetector hotwordDetector) {
        printWriter.print("  Using sandboxed detection service=");
        printWriter.println(hotwordDetector.isUsingSandboxedDetectionService());
        hotwordDetector.dump("    ", printWriter);
        printWriter.println();
    }
}
