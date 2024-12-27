package com.android.server.soundtrigger_middleware;

import android.content.Context;
import android.content.PermissionChecker;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.Identity;
import android.media.permission.PermissionUtil;
import android.media.permission.SafeCloseable;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.os.IBinder;

import com.android.internal.util.LatencyTracker;
import com.android.server.SystemService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public final class SoundTriggerMiddlewareService extends ISoundTriggerMiddlewareService.Stub {
    public final Context mContext;
    public final ISoundTriggerMiddlewareInternal mDelegate;
    public final SoundTriggerInjection mInjection;

    public final class Lifecycle extends SystemService {
        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            SoundTriggerInjection soundTriggerInjection = new SoundTriggerInjection();
            HalFactory[] halFactoryArr = {
                new DefaultHalFactory(), new FakeHalFactory(soundTriggerInjection)
            };
            Context context = getContext();
            publishBinderService(
                    "soundtrigger_middleware",
                    new SoundTriggerMiddlewareService(
                            new SoundTriggerMiddlewareLogging(
                                    LatencyTracker.getInstance(context),
                                    new SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda0(),
                                    new SoundTriggerMiddlewarePermission(
                                            new SoundTriggerMiddlewareValidation(
                                                    new SoundTriggerMiddlewareImpl(
                                                            halFactoryArr,
                                                            new AudioSessionProviderImpl())),
                                            getContext())),
                            getContext(),
                            soundTriggerInjection));
        }
    }

    public final class ModuleService extends ISoundTriggerModule.Stub {
        public final ISoundTriggerModule mDelegate;

        public ModuleService(ISoundTriggerModule iSoundTriggerModule) {
            this.mDelegate = iSoundTriggerModule;
        }

        public final void detach() {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                this.mDelegate.detach();
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void forceRecognitionEvent(int i) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                this.mDelegate.forceRecognitionEvent(i);
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int getModelParameter(int i, int i2) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                int modelParameter = this.mDelegate.getModelParameter(i, i2);
                if (create != null) {
                    create.close();
                }
                return modelParameter;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int loadModel(SoundModel soundModel) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                int loadModel = this.mDelegate.loadModel(soundModel);
                if (create != null) {
                    create.close();
                }
                return loadModel;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                int loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
                if (create != null) {
                    create.close();
                }
                return loadPhraseModel;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final ModelParameterRange queryModelParameterSupport(int i, int i2) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                ModelParameterRange queryModelParameterSupport =
                        this.mDelegate.queryModelParameterSupport(i, i2);
                if (create != null) {
                    create.close();
                }
                return queryModelParameterSupport;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void setModelParameter(int i, int i2, int i3) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                this.mDelegate.setModelParameter(i, i2, i3);
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                IBinder startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
                if (create != null) {
                    create.close();
                }
                return startRecognition;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void stopRecognition(int i) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                this.mDelegate.stopRecognition(i);
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void unloadModel(int i) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                this.mDelegate.unloadModel(i);
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public SoundTriggerMiddlewareService(
            SoundTriggerMiddlewareLogging soundTriggerMiddlewareLogging,
            Context context,
            SoundTriggerInjection soundTriggerInjection) {
        this.mDelegate = soundTriggerMiddlewareLogging;
        this.mContext = context;
        this.mInjection = soundTriggerInjection;
    }

    public final ISoundTriggerModule attachAsMiddleman(
            int i,
            Identity identity,
            Identity identity2,
            ISoundTriggerCallback iSoundTriggerCallback,
            boolean z) {
        Objects.requireNonNull(identity);
        Objects.requireNonNull(identity2);
        SafeCloseable establishIdentityIndirect =
                PermissionUtil.establishIdentityIndirect(
                        this.mContext,
                        "android.permission.SOUNDTRIGGER_DELEGATE_IDENTITY",
                        identity,
                        identity2);
        try {
            ModuleService moduleService =
                    new ModuleService(this.mDelegate.attach(i, iSoundTriggerCallback, z));
            if (establishIdentityIndirect != null) {
                establishIdentityIndirect.close();
            }
            return moduleService;
        } catch (Throwable th) {
            if (establishIdentityIndirect != null) {
                try {
                    establishIdentityIndirect.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final ISoundTriggerModule attachAsOriginator(
            int i, Identity identity, ISoundTriggerCallback iSoundTriggerCallback) {
        Objects.requireNonNull(identity);
        SafeCloseable establishIdentityDirect = PermissionUtil.establishIdentityDirect(identity);
        try {
            ModuleService moduleService =
                    new ModuleService(this.mDelegate.attach(i, iSoundTriggerCallback, false));
            if (establishIdentityDirect != null) {
                establishIdentityDirect.close();
            }
            return moduleService;
        } catch (Throwable th) {
            if (establishIdentityDirect != null) {
                try {
                    establishIdentityDirect.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void attachFakeHalInjection(ISoundTriggerInjection iSoundTriggerInjection) {
        PermissionChecker.checkCallingOrSelfPermissionForPreflight(
                this.mContext, "android.permission.MANAGE_SOUND_TRIGGER");
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            SoundTriggerInjection soundTriggerInjection = this.mInjection;
            Objects.requireNonNull(iSoundTriggerInjection);
            soundTriggerInjection.registerClient(iSoundTriggerInjection);
            if (create != null) {
                create.close();
            }
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
        if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
            ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
        }
    }

    public final SoundTriggerModuleDescriptor[] listModulesAsMiddleman(
            Identity identity, Identity identity2) {
        SafeCloseable establishIdentityIndirect =
                PermissionUtil.establishIdentityIndirect(
                        this.mContext,
                        "android.permission.SOUNDTRIGGER_DELEGATE_IDENTITY",
                        identity,
                        identity2);
        try {
            SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
            if (establishIdentityIndirect != null) {
                establishIdentityIndirect.close();
            }
            return listModules;
        } catch (Throwable th) {
            if (establishIdentityIndirect != null) {
                try {
                    establishIdentityIndirect.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) {
        SafeCloseable establishIdentityDirect = PermissionUtil.establishIdentityDirect(identity);
        try {
            SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
            if (establishIdentityDirect != null) {
                establishIdentityDirect.close();
            }
            return listModules;
        } catch (Throwable th) {
            if (establishIdentityDirect != null) {
                try {
                    establishIdentityDirect.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
