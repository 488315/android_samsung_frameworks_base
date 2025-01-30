package com.android.server.soundtrigger_middleware;

import android.content.Context;
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
import android.os.IBinder;
import com.android.server.LocalServices;
import com.android.server.pm.permission.LegacyPermissionManagerInternal;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes3.dex */
public class SoundTriggerMiddlewarePermission implements ISoundTriggerMiddlewareInternal, Dumpable {
    public final Context mContext;
    public final ISoundTriggerMiddlewareInternal mDelegate;

    public SoundTriggerMiddlewarePermission(ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal, Context context) {
        this.mDelegate = iSoundTriggerMiddlewareInternal;
        this.mContext = context;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public SoundTriggerModuleDescriptor[] listModules() {
        enforcePermissionForPreflight(this.mContext, getIdentity(), "android.permission.CAPTURE_AUDIO_HOTWORD");
        return this.mDelegate.listModules();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public ISoundTriggerModule attach(int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        Identity identity = getIdentity();
        enforcePermissionsForPreflight(identity);
        ModuleWrapper moduleWrapper = new ModuleWrapper(identity, iSoundTriggerCallback, z);
        return moduleWrapper.attach(this.mDelegate.attach(i, moduleWrapper.getCallbackWrapper(), z));
    }

    public String toString() {
        return Objects.toString(this.mDelegate);
    }

    public static Identity getIdentity() {
        return IdentityContext.getNonNull();
    }

    public final void enforcePermissionsForPreflight(Identity identity) {
        enforcePermissionForPreflight(this.mContext, identity, "android.permission.RECORD_AUDIO");
        enforcePermissionForPreflight(this.mContext, identity, "android.permission.CAPTURE_AUDIO_HOTWORD");
    }

    public void enforcePermissionsForDataDelivery(Identity identity, String str) {
        enforceSoundTriggerRecordAudioPermissionForDataDelivery(identity, str);
        enforcePermissionForDataDelivery(this.mContext, identity, "android.permission.CAPTURE_AUDIO_HOTWORD", str);
    }

    public static void enforcePermissionForDataDelivery(Context context, Identity identity, String str, String str2) {
        if (PermissionUtil.checkPermissionForDataDelivery(context, identity, str, str2) != 0) {
            throw new SecurityException(String.format("Failed to obtain permission %s for identity %s", str, ObjectPrinter.print(identity, 16)));
        }
    }

    public static void enforceSoundTriggerRecordAudioPermissionForDataDelivery(Identity identity, String str) {
        if (((LegacyPermissionManagerInternal) LocalServices.getService(LegacyPermissionManagerInternal.class)).checkSoundTriggerRecordAudioPermissionForDataDelivery(identity.uid, identity.packageName, identity.attributionTag, str) != 0) {
            throw new SecurityException(String.format("Failed to obtain permission RECORD_AUDIO for identity %s", ObjectPrinter.print(identity, 16)));
        }
    }

    public static void enforcePermissionForPreflight(Context context, Identity identity, String str) {
        int checkPermissionForPreflight = PermissionUtil.checkPermissionForPreflight(context, identity, str);
        if (checkPermissionForPreflight == 0 || checkPermissionForPreflight == 1) {
            return;
        }
        if (checkPermissionForPreflight == 2) {
            throw new SecurityException(String.format("Failed to obtain permission %s for identity %s", str, ObjectPrinter.print(identity, 16)));
        }
        throw new RuntimeException("Unexpected perimission check result.");
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public void dump(PrintWriter printWriter) {
        ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
        if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
            ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
        }
    }

    public class ModuleWrapper extends ISoundTriggerModule.Stub {
        public final CallbackWrapper mCallbackWrapper;
        public ISoundTriggerModule mDelegate;
        public final boolean mIsTrusted;
        public final Identity mOriginatorIdentity;

        public ModuleWrapper(Identity identity, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
            this.mOriginatorIdentity = identity;
            this.mCallbackWrapper = new CallbackWrapper(iSoundTriggerCallback);
            this.mIsTrusted = z;
        }

        public ModuleWrapper attach(ISoundTriggerModule iSoundTriggerModule) {
            this.mDelegate = iSoundTriggerModule;
            return this;
        }

        public ISoundTriggerCallback getCallbackWrapper() {
            return this.mCallbackWrapper;
        }

        public int loadModel(SoundModel soundModel) {
            enforcePermissions();
            return this.mDelegate.loadModel(soundModel);
        }

        public int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
            enforcePermissions();
            return this.mDelegate.loadPhraseModel(phraseSoundModel);
        }

        public void unloadModel(int i) {
            this.mDelegate.unloadModel(i);
        }

        public IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
            enforcePermissions();
            return this.mDelegate.startRecognition(i, recognitionConfig);
        }

        public void stopRecognition(int i) {
            this.mDelegate.stopRecognition(i);
        }

        public void forceRecognitionEvent(int i) {
            enforcePermissions();
            this.mDelegate.forceRecognitionEvent(i);
        }

        public void setModelParameter(int i, int i2, int i3) {
            enforcePermissions();
            this.mDelegate.setModelParameter(i, i2, i3);
        }

        public int getModelParameter(int i, int i2) {
            enforcePermissions();
            return this.mDelegate.getModelParameter(i, i2);
        }

        public ModelParameterRange queryModelParameterSupport(int i, int i2) {
            enforcePermissions();
            return this.mDelegate.queryModelParameterSupport(i, i2);
        }

        public void detach() {
            this.mDelegate.detach();
        }

        public String toString() {
            return Objects.toString(this.mDelegate);
        }

        public final void enforcePermissions() {
            SoundTriggerMiddlewarePermission.this.enforcePermissionsForPreflight(this.mOriginatorIdentity);
        }

        public class CallbackWrapper implements ISoundTriggerCallback {
            public final ISoundTriggerCallback mDelegate;

            public CallbackWrapper(ISoundTriggerCallback iSoundTriggerCallback) {
                this.mDelegate = iSoundTriggerCallback;
            }

            public void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) {
                enforcePermissions("Sound trigger recognition.");
                this.mDelegate.onRecognition(i, recognitionEventSys, i2);
            }

            public void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
                enforcePermissions("Sound trigger phrase recognition.");
                this.mDelegate.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
            }

            public void onResourcesAvailable() {
                this.mDelegate.onResourcesAvailable();
            }

            public void onModelUnloaded(int i) {
                this.mDelegate.onModelUnloaded(i);
            }

            public void onModuleDied() {
                this.mDelegate.onModuleDied();
            }

            public IBinder asBinder() {
                return this.mDelegate.asBinder();
            }

            public String toString() {
                return this.mDelegate.toString();
            }

            public final void enforcePermissions(String str) {
                if (ModuleWrapper.this.mIsTrusted) {
                    ModuleWrapper moduleWrapper = ModuleWrapper.this;
                    SoundTriggerMiddlewarePermission.this.enforcePermissionsForPreflight(moduleWrapper.mOriginatorIdentity);
                } else {
                    ModuleWrapper moduleWrapper2 = ModuleWrapper.this;
                    SoundTriggerMiddlewarePermission.this.enforcePermissionsForDataDelivery(moduleWrapper2.mOriginatorIdentity, str);
                }
            }
        }
    }
}
