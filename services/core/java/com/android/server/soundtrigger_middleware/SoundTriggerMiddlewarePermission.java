package com.android.server.soundtrigger_middleware;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.PermissionChecker;
import android.media.permission.Identity;
import android.media.permission.IdentityContext;
import android.media.permission.PermissionUtil;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.IBinder;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerMiddlewarePermission implements ISoundTriggerMiddlewareInternal, Dumpable {
    public final Context mContext;
    public final ISoundTriggerMiddlewareInternal mDelegate;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModuleWrapper extends ISoundTriggerModule.Stub {
        public final CallbackWrapper mCallbackWrapper;
        public ISoundTriggerModule mDelegate;
        public final boolean mIsTrusted;
        public final Identity mOriginatorIdentity;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class CallbackWrapper implements ISoundTriggerCallback {
            public final ISoundTriggerCallback mDelegate;

            public CallbackWrapper(ISoundTriggerCallback iSoundTriggerCallback) {
                this.mDelegate = iSoundTriggerCallback;
            }

            public final IBinder asBinder() {
                return this.mDelegate.asBinder();
            }

            public final void enforcePermissions(String str) {
                ModuleWrapper moduleWrapper = ModuleWrapper.this;
                if (moduleWrapper.mIsTrusted) {
                    SoundTriggerMiddlewarePermission.this.enforcePermissionsForPreflight(moduleWrapper.mOriginatorIdentity);
                    return;
                }
                SoundTriggerMiddlewarePermission soundTriggerMiddlewarePermission = SoundTriggerMiddlewarePermission.this;
                Identity identity = moduleWrapper.mOriginatorIdentity;
                soundTriggerMiddlewarePermission.getClass();
                LegacyPermissionManagerService.Internal internal = (LegacyPermissionManagerService.Internal) LocalServices.getService(LegacyPermissionManagerService.Internal.class);
                int i = identity.uid;
                String str2 = identity.packageName;
                String str3 = identity.attributionTag;
                LegacyPermissionManagerService legacyPermissionManagerService = LegacyPermissionManagerService.this;
                int checkPermissionForPreflight = PermissionChecker.checkPermissionForPreflight(legacyPermissionManagerService.mContext, "android.permission.RECORD_AUDIO", -1, i, str2);
                if (checkPermissionForPreflight == 0) {
                    ((AppOpsManager) legacyPermissionManagerService.mContext.getSystemService(AppOpsManager.class)).noteOpNoThrow(120, i, str2, str3, str);
                }
                if (checkPermissionForPreflight != 0) {
                    throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Failed to obtain permission RECORD_AUDIO for identity ", ObjectPrinter.print(identity)));
                }
                if (PermissionUtil.checkPermissionForDataDelivery(soundTriggerMiddlewarePermission.mContext, identity, "android.permission.CAPTURE_AUDIO_HOTWORD", str) != 0) {
                    throw new SecurityException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Failed to obtain permission android.permission.CAPTURE_AUDIO_HOTWORD for identity ", ObjectPrinter.print(identity)));
                }
            }

            public final void onModelUnloaded(int i) {
                this.mDelegate.onModelUnloaded(i);
            }

            public final void onModuleDied() {
                this.mDelegate.onModuleDied();
            }

            public final void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
                enforcePermissions("Sound trigger phrase recognition.");
                this.mDelegate.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
            }

            public final void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) {
                enforcePermissions("Sound trigger recognition.");
                this.mDelegate.onRecognition(i, recognitionEventSys, i2);
            }

            public final void onResourcesAvailable() {
                this.mDelegate.onResourcesAvailable();
            }

            public final String toString() {
                return this.mDelegate.toString();
            }
        }

        public ModuleWrapper(Identity identity, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
            this.mOriginatorIdentity = identity;
            this.mCallbackWrapper = new CallbackWrapper(iSoundTriggerCallback);
            this.mIsTrusted = z;
        }

        public final void detach() {
            this.mDelegate.detach();
        }

        public final void enforcePermissions() {
            SoundTriggerMiddlewarePermission.this.enforcePermissionsForPreflight(this.mOriginatorIdentity);
        }

        public final void forceRecognitionEvent(int i) {
            enforcePermissions();
            this.mDelegate.forceRecognitionEvent(i);
        }

        public final int getModelParameter(int i, int i2) {
            enforcePermissions();
            return this.mDelegate.getModelParameter(i, i2);
        }

        public final int loadModel(SoundModel soundModel) {
            enforcePermissions();
            return this.mDelegate.loadModel(soundModel);
        }

        public final int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
            enforcePermissions();
            return this.mDelegate.loadPhraseModel(phraseSoundModel);
        }

        public final ModelParameterRange queryModelParameterSupport(int i, int i2) {
            enforcePermissions();
            return this.mDelegate.queryModelParameterSupport(i, i2);
        }

        public final void setModelParameter(int i, int i2, int i3) {
            enforcePermissions();
            this.mDelegate.setModelParameter(i, i2, i3);
        }

        public final IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
            enforcePermissions();
            return this.mDelegate.startRecognition(i, recognitionConfig);
        }

        public final void stopRecognition(int i) {
            this.mDelegate.stopRecognition(i);
        }

        public final String toString() {
            return Objects.toString(this.mDelegate);
        }

        public final void unloadModel(int i) {
            this.mDelegate.unloadModel(i);
        }
    }

    public SoundTriggerMiddlewarePermission(SoundTriggerMiddlewareValidation soundTriggerMiddlewareValidation, Context context) {
        this.mDelegate = soundTriggerMiddlewareValidation;
        this.mContext = context;
    }

    public static void enforcePermissionForPreflight(Context context, Identity identity, String str) {
        int checkPermissionForPreflight = PermissionUtil.checkPermissionForPreflight(context, identity, str);
        if (checkPermissionForPreflight == 0 || checkPermissionForPreflight == 1) {
            return;
        }
        if (checkPermissionForPreflight == 2) {
            throw new SecurityException(BootReceiver$$ExternalSyntheticOutline0.m("Failed to obtain permission ", str, " for identity ", ObjectPrinter.print(identity)));
        }
        throw new RuntimeException("Unexpected perimission check result.");
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final ISoundTriggerModule attach(int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        Identity nonNull = IdentityContext.getNonNull();
        enforcePermissionsForPreflight(nonNull);
        ModuleWrapper moduleWrapper = new ModuleWrapper(nonNull, iSoundTriggerCallback, z);
        moduleWrapper.mDelegate = this.mDelegate.attach(i, moduleWrapper.mCallbackWrapper, z);
        return moduleWrapper;
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public final void dump(PrintWriter printWriter) {
        ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
        if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
            ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
        }
    }

    public final void enforcePermissionsForPreflight(Identity identity) {
        enforcePermissionForPreflight(this.mContext, identity, "android.permission.RECORD_AUDIO");
        enforcePermissionForPreflight(this.mContext, identity, "android.permission.CAPTURE_AUDIO_HOTWORD");
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final SoundTriggerModuleDescriptor[] listModules() {
        enforcePermissionForPreflight(this.mContext, IdentityContext.getNonNull(), "android.permission.CAPTURE_AUDIO_HOTWORD");
        return this.mDelegate.listModules();
    }

    public final String toString() {
        return Objects.toString(this.mDelegate);
    }
}
