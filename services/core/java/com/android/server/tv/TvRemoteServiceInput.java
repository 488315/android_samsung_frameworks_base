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

public final class TvRemoteServiceInput extends ITvRemoteServiceInput.Stub {
    public final Map mBridgeMap = new ArrayMap();
    public final Object mLock;
    public final ITvRemoteProvider mProvider;

    /* renamed from: com.android.server.tv.TvRemoteServiceInput$1, reason: invalid class name */
    public final class AnonymousClass1 implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ TvRemoteServiceInput this$0;
        public final /* synthetic */ IBinder val$token;

        public /* synthetic */ AnonymousClass1(
                TvRemoteServiceInput tvRemoteServiceInput, IBinder iBinder, int i) {
            this.$r8$classId = i;
            this.this$0 = tvRemoteServiceInput;
            this.val$token = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.closeInputBridge(this.val$token);
                    break;
                default:
                    this.this$0.closeInputBridge(this.val$token);
                    break;
            }
        }
    }

    public TvRemoteServiceInput(Object obj, ITvRemoteProvider iTvRemoteProvider) {
        this.mLock = obj;
        this.mProvider = iTvRemoteProvider;
    }

    public final void clearInputBridge(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.clear(iBinder);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void closeInputBridge(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).remove(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.close(iBinder);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void openGamepadBridge(IBinder iBinder, String str) {
        synchronized (this.mLock) {
            if (!((ArrayMap) this.mBridgeMap).containsKey(iBinder)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        ((ArrayMap) this.mBridgeMap)
                                .put(iBinder, UinputBridge.openGamepad(iBinder, str));
                        iBinder.linkToDeath(new AnonymousClass1(this, iBinder, 1), 0);
                    } catch (RemoteException unused) {
                        Slog.e("TvRemoteServiceInput", "Token is already dead");
                        closeInputBridge(iBinder);
                        return;
                    } catch (IOException unused2) {
                        Slog.e("TvRemoteServiceInput", "Cannot create device for " + str);
                        return;
                    }
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

    public final void openInputBridge(IBinder iBinder, String str, int i, int i2, int i3) {
        synchronized (this.mLock) {
            if (!((ArrayMap) this.mBridgeMap).containsKey(iBinder)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        ((ArrayMap) this.mBridgeMap)
                                .put(iBinder, new UinputBridge(iBinder, str, i, i2, i3));
                        iBinder.linkToDeath(new AnonymousClass1(this, iBinder, 0), 0);
                    } catch (RemoteException unused) {
                        Slog.e("TvRemoteServiceInput", "Token is already dead");
                        closeInputBridge(iBinder);
                        return;
                    } catch (IOException unused2) {
                        Slog.e("TvRemoteServiceInput", "Cannot create device for " + str);
                        return;
                    }
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

    public final void sendGamepadAxisValue(IBinder iBinder, int i, float f) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendGamepadAxisValue(iBinder, i, f);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendGamepadKeyDown(IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendGamepadKey(i, true, iBinder);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendGamepadKeyUp(IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendGamepadKey(i, false, iBinder);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendKeyDown(IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendKeyDown(iBinder, i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendKeyUp(IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendKeyUp(iBinder, i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendPointerDown(IBinder iBinder, int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendPointerDown(iBinder, i, i2, i3);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendPointerSync(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendPointerSync(iBinder);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendPointerUp(IBinder iBinder, int i) {
        synchronized (this.mLock) {
            try {
                UinputBridge uinputBridge =
                        (UinputBridge) ((ArrayMap) this.mBridgeMap).get(iBinder);
                if (uinputBridge == null) {
                    Slog.w(
                            "TvRemoteServiceInput",
                            String.format("Input bridge not found for token: %s", iBinder));
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    uinputBridge.sendPointerUp(iBinder, i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendTimestamp(IBinder iBinder, long j) {}
}
