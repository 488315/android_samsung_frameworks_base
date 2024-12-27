package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.IInjectGlobalEvent;
import android.media.soundtrigger_middleware.IInjectModelEvent;
import android.media.soundtrigger_middleware.IInjectRecognitionEvent;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SoundTriggerInjection implements ISoundTriggerInjection, IBinder.DeathRecipient {
    public final Object mClientLock = new Object();
    public ISoundTriggerInjection mClient = null;
    public IInjectGlobalEvent mGlobalEventInjection = null;

    public final IBinder asBinder() {
        Slog.wtf("SoundTriggerInjection", "Unexpected asBinder!");
        throw new UnsupportedOperationException("Calling asBinder on a fake binder object");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.wtf("SoundTriggerInjection", "Binder died without params");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        synchronized (this.mClientLock) {
            try {
                ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
                if (iSoundTriggerInjection != null
                        && iBinder == iSoundTriggerInjection.asBinder()) {
                    this.mClient = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onClientAttached(iBinder, iInjectGlobalEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onClientDetached(IBinder iBinder) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onClientDetached(iBinder);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onFrameworkDetached(iInjectGlobalEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onParamSet(i, i2, iInjectModelEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onPreempted() {
        Slog.wtf("SoundTriggerInjection", "Unexpected preempted!");
    }

    public final void onRecognitionStarted(
            int i,
            RecognitionConfig recognitionConfig,
            IInjectRecognitionEvent iInjectRecognitionEvent,
            IInjectModelEvent iInjectModelEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onRecognitionStarted(
                        i, recognitionConfig, iInjectRecognitionEvent, iInjectModelEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onRecognitionStopped(iInjectRecognitionEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onRestarted(iInjectGlobalEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onSoundModelLoaded(
            SoundModel soundModel,
            Phrase[] phraseArr,
            IInjectModelEvent iInjectModelEvent,
            IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onSoundModelLoaded(
                        soundModel, phraseArr, iInjectModelEvent, iInjectGlobalEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) {
        synchronized (this.mClientLock) {
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.onSoundModelUnloaded(iInjectModelEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void registerClient(ISoundTriggerInjection iSoundTriggerInjection) {
        synchronized (this.mClientLock) {
            Objects.requireNonNull(iSoundTriggerInjection);
            ISoundTriggerInjection iSoundTriggerInjection2 = this.mClient;
            if (iSoundTriggerInjection2 != null) {
                try {
                    iSoundTriggerInjection2.onPreempted();
                } catch (RemoteException e) {
                    Slog.e("SoundTriggerInjection", "RemoteException when handling preemption", e);
                }
                this.mClient.asBinder().unlinkToDeath(this, 0);
            }
            this.mClient = iSoundTriggerInjection;
            try {
                iSoundTriggerInjection.asBinder().linkToDeath(this, 0);
                IInjectGlobalEvent iInjectGlobalEvent = this.mGlobalEventInjection;
                if (iInjectGlobalEvent != null) {
                    this.mClient.registerGlobalEventInjection(iInjectGlobalEvent);
                }
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }

    public final void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) {
        synchronized (this.mClientLock) {
            this.mGlobalEventInjection = iInjectGlobalEvent;
            ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
            if (iSoundTriggerInjection == null) {
                return;
            }
            try {
                iSoundTriggerInjection.registerGlobalEventInjection(iInjectGlobalEvent);
            } catch (RemoteException unused) {
                this.mClient = null;
            }
        }
    }
}
