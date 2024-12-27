package com.android.server.locksettings;

import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.service.resumeonreboot.IResumeOnRebootService;
import android.util.Slog;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RebootEscrowProviderServerBasedImpl implements RebootEscrowProviderInterface {
    public final Injector mInjector;
    public byte[] mServerBlob;
    public final LockSettingsStorage mStorage;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection mServiceConnection;
    }

    public RebootEscrowProviderServerBasedImpl(
            LockSettingsStorage lockSettingsStorage, Injector injector) {
        this.mStorage = lockSettingsStorage;
        this.mInjector = injector;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final void clearRebootEscrowKey() {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.deleteFile(lockSettingsStorage.getRebootEscrowServerBlobFile());
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final RebootEscrowKey getAndClearRebootEscrowKey(SecretKey secretKey) {
        byte[] bArr = this.mServerBlob;
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        if (bArr == null) {
            this.mServerBlob =
                    lockSettingsStorage.readFile(
                            lockSettingsStorage.getRebootEscrowServerBlobFile());
        }
        lockSettingsStorage.deleteFile(lockSettingsStorage.getRebootEscrowServerBlobFile());
        if (this.mServerBlob == null) {
            Slog.w(
                    "RebootEscrowProviderServerBased",
                    "Failed to read reboot escrow server blob from storage");
            return null;
        }
        if (secretKey == null) {
            Slog.w(
                    "RebootEscrowProviderServerBased",
                    "Failed to decrypt the escrow key; decryption key from keystore is null.");
            return null;
        }
        Slog.i("RebootEscrowProviderServerBased", "Loaded reboot escrow server blob from storage");
        try {
            byte[] unwrapServerBlob = unwrapServerBlob(this.mServerBlob, secretKey);
            if (unwrapServerBlob == null) {
                Slog.w(
                        "RebootEscrowProviderServerBased",
                        "Decrypted reboot escrow key bytes should not be null");
                return null;
            }
            if (unwrapServerBlob.length == 32) {
                return new RebootEscrowKey(new SecretKeySpec(unwrapServerBlob, "AES"));
            }
            Slog.e(
                    "RebootEscrowProviderServerBased",
                    "Decrypted reboot escrow key has incorrect size " + unwrapServerBlob.length);
            return null;
        } catch (RemoteException | TimeoutException e) {
            Slog.w("RebootEscrowProviderServerBased", "Failed to decrypt the server blob ", e);
            return null;
        }
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final int getType() {
        return 1;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final boolean hasRebootEscrowSupport() {
        return this.mInjector.mServiceConnection != null;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public final boolean storeRebootEscrowKey(
            RebootEscrowKey rebootEscrowKey, SecretKey secretKey) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.deleteFile(lockSettingsStorage.getRebootEscrowServerBlobFile());
        try {
            byte[] wrapEscrowKey = wrapEscrowKey(rebootEscrowKey.mKey.getEncoded(), secretKey);
            if (wrapEscrowKey == null) {
                Slog.w(
                        "RebootEscrowProviderServerBased",
                        "Failed to encrypt the reboot escrow key");
                return false;
            }
            lockSettingsStorage.writeFile(
                    lockSettingsStorage.getRebootEscrowServerBlobFile(), wrapEscrowKey, true);
            Slog.i("RebootEscrowProviderServerBased", "Reboot escrow key encrypted and stored.");
            return true;
        } catch (RemoteException | IOException | TimeoutException e) {
            Slog.w(
                    "RebootEscrowProviderServerBased",
                    "Failed to encrypt the reboot escrow key ",
                    e);
            return false;
        }
    }

    public final byte[] unwrapServerBlob(byte[] bArr, SecretKey secretKey) {
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection
                resumeOnRebootServiceConnection = this.mInjector.mServiceConnection;
        if (resumeOnRebootServiceConnection == null) {
            Slog.w(
                    "RebootEscrowProviderServerBased",
                    "Had reboot escrow data for users, but resume on reboot server service is"
                        + " unavailable");
            return null;
        }
        Objects.requireNonNull(secretKey);
        Objects.requireNonNull(bArr);
        byte[] decrypt =
                AesEncryptionUtil.decrypt(
                        secretKey, new DataInputStream(new ByteArrayInputStream(bArr)));
        if (decrypt == null) {
            Slog.w("RebootEscrowProviderServerBased", "Decrypted server blob should not be null");
            return null;
        }
        resumeOnRebootServiceConnection.bindToService(
                DeviceConfig.getLong("ota", "server_based_service_timeout_in_seconds", 10L));
        long j = DeviceConfig.getLong("ota", "server_based_service_timeout_in_seconds", 10L);
        IResumeOnRebootService iResumeOnRebootService = resumeOnRebootServiceConnection.mBinder;
        if (iResumeOnRebootService == null || !iResumeOnRebootService.asBinder().isBinderAlive()) {
            throw new RemoteException("Service not bound");
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback resumeOnRebootServiceCallback =
                new ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback(countDownLatch);
        resumeOnRebootServiceConnection.mBinder.unwrap(
                decrypt, new RemoteCallback(resumeOnRebootServiceCallback));
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.waitForLatch(
                countDownLatch, j, "unWrapSecret");
        if (resumeOnRebootServiceCallback.mResult.containsKey("exception_key")) {
            ParcelableException parcelableException =
                    (ParcelableException)
                            resumeOnRebootServiceCallback.mResult.getParcelable(
                                    "exception_key", ParcelableException.class);
            if (parcelableException == null
                    || !(parcelableException.getCause() instanceof IOException)) {
                throw new RemoteException(
                        "ResumeOnRebootServiceConnection wrap/unwrap failed",
                        parcelableException,
                        true,
                        true);
            }
            parcelableException.maybeRethrow(IOException.class);
        }
        byte[] byteArray = resumeOnRebootServiceCallback.mResult.getByteArray("unrwapped_blob_key");
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.AnonymousClass1
                anonymousClass1 = resumeOnRebootServiceConnection.mServiceConnection;
        if (anonymousClass1 != null) {
            resumeOnRebootServiceConnection.mContext.unbindService(anonymousClass1);
        }
        resumeOnRebootServiceConnection.mBinder = null;
        return byteArray;
    }

    public final byte[] wrapEscrowKey(byte[] bArr, SecretKey secretKey) {
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection
                resumeOnRebootServiceConnection = this.mInjector.mServiceConnection;
        if (resumeOnRebootServiceConnection == null) {
            Slog.w(
                    "RebootEscrowProviderServerBased",
                    "Failed to encrypt the reboot escrow key: resume on reboot server service is"
                        + " unavailable");
            return null;
        }
        resumeOnRebootServiceConnection.bindToService(
                DeviceConfig.getLong("ota", "server_based_service_timeout_in_seconds", 10L));
        long j =
                DeviceConfig.getLong("ota", "server_based_server_blob_lifetime_in_millis", 600000L);
        long j2 = DeviceConfig.getLong("ota", "server_based_service_timeout_in_seconds", 10L);
        IResumeOnRebootService iResumeOnRebootService = resumeOnRebootServiceConnection.mBinder;
        if (iResumeOnRebootService == null || !iResumeOnRebootService.asBinder().isBinderAlive()) {
            throw new RemoteException("Service not bound");
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback resumeOnRebootServiceCallback =
                new ResumeOnRebootServiceProvider.ResumeOnRebootServiceCallback(countDownLatch);
        resumeOnRebootServiceConnection.mBinder.wrapSecret(
                bArr, j, new RemoteCallback(resumeOnRebootServiceCallback));
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.waitForLatch(
                countDownLatch, j2, "wrapSecret");
        if (resumeOnRebootServiceCallback.mResult.containsKey("exception_key")) {
            ParcelableException parcelableException =
                    (ParcelableException)
                            resumeOnRebootServiceCallback.mResult.getParcelable(
                                    "exception_key", ParcelableException.class);
            if (parcelableException == null
                    || !(parcelableException.getCause() instanceof IOException)) {
                throw new RemoteException(
                        "ResumeOnRebootServiceConnection wrap/unwrap failed",
                        parcelableException,
                        true,
                        true);
            }
            parcelableException.maybeRethrow(IOException.class);
        }
        byte[] byteArray = resumeOnRebootServiceCallback.mResult.getByteArray("wrapped_blob_key");
        ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.AnonymousClass1
                anonymousClass1 = resumeOnRebootServiceConnection.mServiceConnection;
        if (anonymousClass1 != null) {
            resumeOnRebootServiceConnection.mContext.unbindService(anonymousClass1);
        }
        resumeOnRebootServiceConnection.mBinder = null;
        if (byteArray != null) {
            return AesEncryptionUtil.encrypt(byteArray, secretKey);
        }
        Slog.w(
                "RebootEscrowProviderServerBased",
                "Server encrypted reboot escrow key cannot be null");
        return null;
    }
}
