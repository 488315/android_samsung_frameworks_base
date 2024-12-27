package com.android.server.locksettings;

import android.hardware.rebootescrow.IRebootEscrow;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Slog;

import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;

import java.util.NoSuchElementException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RebootEscrowProviderHalImpl implements RebootEscrowProviderInterface {
    public final Injector mInjector;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v6, types: [android.hardware.rebootescrow.IRebootEscrow] */
        public static IRebootEscrow getRebootEscrow() {
            IRebootEscrow.Stub.Proxy proxy;
            try {
                IBinder service =
                        ServiceManager.getService(
                                "android.hardware.rebootescrow.IRebootEscrow/default");
                int i = IRebootEscrow.Stub.$r8$clinit;
                if (service == null) {
                    return null;
                }
                IInterface queryLocalInterface =
                        service.queryLocalInterface(IRebootEscrow.DESCRIPTOR);
                if (queryLocalInterface == null
                        || !(queryLocalInterface instanceof IRebootEscrow)) {
                    IRebootEscrow.Stub.Proxy proxy2 = new IRebootEscrow.Stub.Proxy();
                    proxy2.mRemote = service;
                    proxy = proxy2;
                } else {
                    proxy = (IRebootEscrow) queryLocalInterface;
                }
                return proxy;
            } catch (NoSuchElementException unused) {
                Slog.i("RebootEscrowProviderHal", "Device doesn't implement RebootEscrow HAL");
                return null;
            }
        }
    }

    public RebootEscrowProviderHalImpl() {
        this.mInjector = new Injector();
    }

    public RebootEscrowProviderHalImpl(Injector injector) {
        this.mInjector = injector;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final void clearRebootEscrowKey() {
        this.mInjector.getClass();
        IRebootEscrow rebootEscrow = Injector.getRebootEscrow();
        if (rebootEscrow == null) {
            return;
        }
        try {
            ((IRebootEscrow.Stub.Proxy) rebootEscrow).storeKey(new byte[32]);
        } catch (RemoteException | ServiceSpecificException unused) {
            Slog.w("RebootEscrowProviderHal", "Could not call RebootEscrow HAL to shred key");
        }
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final RebootEscrowKey getAndClearRebootEscrowKey(SecretKey secretKey) {
        this.mInjector.getClass();
        IRebootEscrow rebootEscrow = Injector.getRebootEscrow();
        if (rebootEscrow == null) {
            Slog.w(
                    "RebootEscrowProviderHal",
                    "Had reboot escrow data for users, but RebootEscrow HAL is unavailable");
            return null;
        }
        try {
            IRebootEscrow.Stub.Proxy proxy = (IRebootEscrow.Stub.Proxy) rebootEscrow;
            byte[] retrieveKey = proxy.retrieveKey();
            if (retrieveKey == null) {
                Slog.w(
                        "RebootEscrowProviderHal",
                        "Had reboot escrow data for users, but could not retrieve key");
                return null;
            }
            if (retrieveKey.length != 32) {
                Slog.e(
                        "RebootEscrowProviderHal",
                        "IRebootEscrow returned key of incorrect size " + retrieveKey.length);
                return null;
            }
            int i = 0;
            for (byte b : retrieveKey) {
                i |= b;
            }
            if (i == 0) {
                Slog.w("RebootEscrowProviderHal", "IRebootEscrow returned an all-zeroes key");
                return null;
            }
            proxy.storeKey(new byte[32]);
            return new RebootEscrowKey(new SecretKeySpec(retrieveKey, "AES"));
        } catch (RemoteException unused) {
            Slog.w("RebootEscrowProviderHal", "Could not retrieve escrow data");
            return null;
        } catch (ServiceSpecificException e) {
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Got service-specific exception: "),
                    e.errorCode,
                    "RebootEscrowProviderHal");
            return null;
        }
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final int getType() {
        return 0;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final boolean hasRebootEscrowSupport() {
        this.mInjector.getClass();
        return Injector.getRebootEscrow() != null;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final boolean storeRebootEscrowKey(
            RebootEscrowKey rebootEscrowKey, SecretKey secretKey) {
        this.mInjector.getClass();
        IRebootEscrow rebootEscrow = Injector.getRebootEscrow();
        if (rebootEscrow == null) {
            Slog.w(
                    "RebootEscrowProviderHal",
                    "Escrow marked as ready, but RebootEscrow HAL is unavailable");
            return false;
        }
        try {
            ((IRebootEscrow.Stub.Proxy) rebootEscrow).storeKey(rebootEscrowKey.mKey.getEncoded());
            Slog.i("RebootEscrowProviderHal", "Reboot escrow key stored with RebootEscrow HAL");
            return true;
        } catch (RemoteException | ServiceSpecificException e) {
            Slog.e("RebootEscrowProviderHal", "Failed escrow secret to RebootEscrow HAL", e);
            return false;
        }
    }
}
