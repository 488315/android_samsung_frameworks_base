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

/* loaded from: classes3.dex */
public class SoundTriggerInjection implements ISoundTriggerInjection, IBinder.DeathRecipient {
  public final Object mClientLock = new Object();
  public ISoundTriggerInjection mClient = null;
  public IInjectGlobalEvent mGlobalEventInjection = null;

  public void registerClient(ISoundTriggerInjection iSoundTriggerInjection) {
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

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {
    Slog.wtf("SoundTriggerInjection", "Binder died without params");
  }

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied(IBinder iBinder) {
    synchronized (this.mClientLock) {
      ISoundTriggerInjection iSoundTriggerInjection = this.mClient;
      if (iSoundTriggerInjection != null && iBinder == iSoundTriggerInjection.asBinder()) {
        this.mClient = null;
      }
    }
  }

  public void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) {
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

  public void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) {
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

  public void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) {
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

  public void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent) {
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

  public void onClientDetached(IBinder iBinder) {
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

  public void onSoundModelLoaded(
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

  public void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) {
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

  public void onRecognitionStarted(
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

  public void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) {
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

  public void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) {
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

  public void onPreempted() {
    Slog.wtf("SoundTriggerInjection", "Unexpected preempted!");
  }

  public IBinder asBinder() {
    Slog.wtf("SoundTriggerInjection", "Unexpected asBinder!");
    throw new UnsupportedOperationException("Calling asBinder on a fake binder object");
  }
}
