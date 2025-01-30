package com.android.server.tv;

import android.media.tv.ITvRemoteProvider;
import android.media.tv.ITvRemoteServiceInput;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
public final class TvRemoteServiceInput extends ITvRemoteServiceInput.Stub {
  public final Map mBridgeMap = new ArrayMap();
  public final Object mLock;
  public final ITvRemoteProvider mProvider;

  public void sendTimestamp(IBinder iBinder, long j) {}

  public TvRemoteServiceInput(Object obj, ITvRemoteProvider iTvRemoteProvider) {
    this.mLock = obj;
    this.mProvider = iTvRemoteProvider;
  }

  public void openInputBridge(final IBinder iBinder, String str, int i, int i2, int i3) {
    synchronized (this.mLock) {
      if (!this.mBridgeMap.containsKey(iBinder)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mBridgeMap.put(iBinder, new UinputBridge(iBinder, str, i, i2, i3));
          iBinder.linkToDeath(
              new IBinder
                  .DeathRecipient() { // from class: com.android.server.tv.TvRemoteServiceInput.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                  TvRemoteServiceInput.this.closeInputBridge(iBinder);
                }
              },
              0);
        } catch (RemoteException unused) {
          Slog.e("TvRemoteServiceInput", "Token is already dead");
          closeInputBridge(iBinder);
          return;
        } catch (IOException unused2) {
          Slog.e("TvRemoteServiceInput", "Cannot create device for " + str);
          return;
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }
    try {
      this.mProvider.onInputBridgeConnected(iBinder);
    } catch (RemoteException unused3) {
      Slog.e("TvRemoteServiceInput", "Failed remote call to onInputBridgeConnected");
    }
  }

  public void openGamepadBridge(final IBinder iBinder, String str) {
    synchronized (this.mLock) {
      if (!this.mBridgeMap.containsKey(iBinder)) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
          this.mBridgeMap.put(iBinder, UinputBridge.openGamepad(iBinder, str));
          iBinder.linkToDeath(
              new IBinder
                  .DeathRecipient() { // from class: com.android.server.tv.TvRemoteServiceInput.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                  TvRemoteServiceInput.this.closeInputBridge(iBinder);
                }
              },
              0);
        } catch (RemoteException unused) {
          Slog.e("TvRemoteServiceInput", "Token is already dead");
          closeInputBridge(iBinder);
          return;
        } catch (IOException unused2) {
          Slog.e("TvRemoteServiceInput", "Cannot create device for " + str);
          return;
        } finally {
          Binder.restoreCallingIdentity(clearCallingIdentity);
        }
      }
    }
    try {
      this.mProvider.onInputBridgeConnected(iBinder);
    } catch (RemoteException unused3) {
      Slog.e("TvRemoteServiceInput", "Failed remote call to onInputBridgeConnected");
    }
  }

  public void closeInputBridge(IBinder iBinder) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.remove(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.close(iBinder);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void clearInputBridge(IBinder iBinder) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.clear(iBinder);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendKeyDown(IBinder iBinder, int i) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendKeyDown(iBinder, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendKeyUp(IBinder iBinder, int i) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendKeyUp(iBinder, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendPointerDown(IBinder iBinder, int i, int i2, int i3) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendPointerDown(iBinder, i, i2, i3);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendPointerUp(IBinder iBinder, int i) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendPointerUp(iBinder, i);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendPointerSync(IBinder iBinder) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendPointerSync(iBinder);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendGamepadKeyUp(IBinder iBinder, int i) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendGamepadKey(iBinder, i, false);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendGamepadKeyDown(IBinder iBinder, int i) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendGamepadKey(iBinder, i, true);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void sendGamepadAxisValue(IBinder iBinder, int i, float f) {
    synchronized (this.mLock) {
      UinputBridge uinputBridge = (UinputBridge) this.mBridgeMap.get(iBinder);
      if (uinputBridge == null) {
        Slog.w(
            "TvRemoteServiceInput", String.format("Input bridge not found for token: %s", iBinder));
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        uinputBridge.sendGamepadAxisValue(iBinder, i, f);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }
}
