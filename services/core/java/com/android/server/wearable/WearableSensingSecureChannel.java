package com.android.server.wearable;

import android.companion.AssociationInfo;
import android.companion.AssociationRequest;
import android.companion.CompanionDeviceManager;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.app.wearable.Flags;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WearableSensingSecureChannel {
    public final CompanionDeviceManager mCompanionDeviceManager;
    public final InputStream mLocalIn;
    public final OutputStream mLocalOut;
    public final ParcelFileDescriptor mRemoteFd;
    public final WearableSensingManagerPerUserService.AnonymousClass1 mSecureTransportListener;
    public final ParcelFileDescriptor mUnderlyingTransport;
    public final Object mLock = new Object();
    public final SoftShutdownExecutor mMessageFromWearableExecutor =
            new SoftShutdownExecutor(Executors.newSingleThreadExecutor());
    public final SoftShutdownExecutor mMessageToWearableExecutor =
            new SoftShutdownExecutor(Executors.newSingleThreadExecutor());
    public final SoftShutdownExecutor mLightWeightExecutor =
            new SoftShutdownExecutor(Executors.newSingleThreadExecutor());
    public final AtomicBoolean mTransportAvailable = new AtomicBoolean(false);
    public final WearableSensingSecureChannel$$ExternalSyntheticLambda0
            mOnTransportsChangedListener = new Consumer() { // from class:
                        // com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            final WearableSensingSecureChannel wearableSensingSecureChannel =
                                    WearableSensingSecureChannel.this;
                            List list = (List) obj;
                            synchronized (wearableSensingSecureChannel.mLock) {
                                try {
                                    if (wearableSensingSecureChannel.mClosed) {
                                        return;
                                    }
                                    if (wearableSensingSecureChannel.mAssociationId == null) {
                                        Slog.e(
                                                "WearableSensingSecureChannel",
                                                "mAssociationId is null when transport changed");
                                        return;
                                    }
                                    boolean anyMatch =
                                            list.stream()
                                                    .anyMatch(
                                                            new Predicate() { // from class:
                                                                // com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda2
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj2) {
                                                                    WearableSensingSecureChannel
                                                                            wearableSensingSecureChannel2 =
                                                                                    WearableSensingSecureChannel
                                                                                            .this;
                                                                    wearableSensingSecureChannel2
                                                                            .getClass();
                                                                    return ((AssociationInfo) obj2)
                                                                                    .getId()
                                                                            == wearableSensingSecureChannel2
                                                                                    .mAssociationId
                                                                                    .intValue();
                                                                }
                                                            });
                                    if (!anyMatch
                                            || !wearableSensingSecureChannel.mTransportAvailable
                                                    .compareAndSet(false, true)) {
                                        if (anyMatch
                                                || !wearableSensingSecureChannel.mTransportAvailable
                                                        .compareAndSet(true, false)) {
                                            return;
                                        }
                                        Slog.i(
                                                "WearableSensingSecureChannel",
                                                "CDM transport is detached. This is not"
                                                    + " recoverable.");
                                        wearableSensingSecureChannel.onError();
                                        return;
                                    }
                                    Slog.i("WearableSensingSecureChannel", "Transport available");
                                    wearableSensingSecureChannel.mMessageToWearableExecutor.execute(
                                            new Runnable() { // from class:
                                                // com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda3
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    WearableSensingSecureChannel
                                                            wearableSensingSecureChannel2 =
                                                                    WearableSensingSecureChannel
                                                                            .this;
                                                    int[] iArr = {
                                                        wearableSensingSecureChannel2.mAssociationId
                                                                .intValue()
                                                    };
                                                    byte[] bArr = new byte[8192];
                                                    while (true) {
                                                        try {
                                                            int read =
                                                                    wearableSensingSecureChannel2
                                                                            .mLocalIn.read(bArr);
                                                            if (read == -1) {
                                                                Slog.i(
                                                                        "WearableSensingSecureChannel",
                                                                        "Reached EOF when reading"
                                                                            + " from remote stream."
                                                                            + " Reporting this as"
                                                                            + " an error.");
                                                                wearableSensingSecureChannel2
                                                                        .onError();
                                                                return;
                                                            } else {
                                                                byte[] bArr2 = new byte[read];
                                                                System.arraycopy(
                                                                        bArr, 0, bArr2, 0, read);
                                                                Slog.v(
                                                                        "WearableSensingSecureChannel",
                                                                        "Sending message to"
                                                                            + " wearable");
                                                                wearableSensingSecureChannel2
                                                                        .mCompanionDeviceManager
                                                                        .sendMessage(
                                                                                1132755335,
                                                                                bArr2,
                                                                                iArr);
                                                            }
                                                        } catch (IOException unused) {
                                                            Slog.i(
                                                                    "WearableSensingSecureChannel",
                                                                    "IOException while reading from"
                                                                        + " remote stream.");
                                                            wearableSensingSecureChannel2.onError();
                                                            return;
                                                        }
                                                    }
                                                }
                                            });
                                    WearableSensingManagerPerUserService.AnonymousClass1
                                            anonymousClass1 =
                                                    wearableSensingSecureChannel
                                                            .mSecureTransportListener;
                                    ParcelFileDescriptor parcelFileDescriptor =
                                            wearableSensingSecureChannel.mRemoteFd;
                                    anonymousClass1.getClass();
                                    Slog.i(
                                            "WearableSensingManagerPerUserService",
                                            "calling over to remote service.");
                                    synchronized (WearableSensingManagerPerUserService.this.mLock) {
                                        WearableSensingManagerPerUserService.this
                                                .ensureRemoteServiceInitiated$2();
                                        WearableSensingManagerPerUserService.this.mRemoteService
                                                .provideSecureConnection(
                                                        parcelFileDescriptor,
                                                        (WearableSensingManagerPerUserService
                                                                        .AnonymousClass3)
                                                                anonymousClass1
                                                                        .val$wrappedWearableSensingCallback,
                                                        anonymousClass1.val$statusCallback);
                                    }
                                } finally {
                                }
                            }
                        }
                    };
    public final WearableSensingSecureChannel$$ExternalSyntheticLambda1 mOnMessageReceivedListener =
            new BiConsumer() { // from class:
                // com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    WearableSensingSecureChannel wearableSensingSecureChannel =
                            WearableSensingSecureChannel.this;
                    byte[] bArr = (byte[]) obj2;
                    if (((Integer) obj).intValue()
                            != wearableSensingSecureChannel.mAssociationId.intValue()) {
                        Slog.v(
                                "WearableSensingSecureChannel",
                                "Received CDM message of type MESSAGE_ONEWAY_FROM_WEARABLE, but it"
                                    + " is for another association. Ignoring the message.");
                        return;
                    }
                    Slog.v("WearableSensingSecureChannel", "Received message from wearable.");
                    try {
                        wearableSensingSecureChannel.mLocalOut.write(bArr);
                        wearableSensingSecureChannel.mLocalOut.flush();
                    } catch (IOException unused) {
                        Slog.i(
                                "WearableSensingSecureChannel",
                                "IOException when writing to remote stream. Closing the secure"
                                    + " channel.");
                        wearableSensingSecureChannel.onError();
                    }
                }
            };
    public boolean mClosed = false;
    public Integer mAssociationId = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoftShutdownExecutor implements Executor {
        public final ExecutorService mExecutorService;

        public SoftShutdownExecutor(ExecutorService executorService) {
            this.mExecutorService = executorService;
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            try {
                this.mExecutorService.execute(runnable);
            } catch (RejectedExecutionException unused) {
                Slog.d(
                        "WearableSensingSecureChannel",
                        "Received new runnable after shutdown. Ignoring.");
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.wearable.WearableSensingSecureChannel$$ExternalSyntheticLambda1] */
    public WearableSensingSecureChannel(
            CompanionDeviceManager companionDeviceManager,
            ParcelFileDescriptor parcelFileDescriptor,
            WearableSensingManagerPerUserService.AnonymousClass1 anonymousClass1,
            ParcelFileDescriptor parcelFileDescriptor2,
            ParcelFileDescriptor parcelFileDescriptor3) {
        this.mCompanionDeviceManager = companionDeviceManager;
        this.mUnderlyingTransport = parcelFileDescriptor;
        this.mSecureTransportListener = anonymousClass1;
        this.mRemoteFd = parcelFileDescriptor2;
        this.mLocalIn = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor3);
        this.mLocalOut = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor3);
    }

    public static WearableSensingSecureChannel create(
            CompanionDeviceManager companionDeviceManager,
            ParcelFileDescriptor parcelFileDescriptor,
            WearableSensingManagerPerUserService.AnonymousClass1 anonymousClass1) {
        Objects.requireNonNull(companionDeviceManager);
        Objects.requireNonNull(parcelFileDescriptor);
        ParcelFileDescriptor[] createSocketPair = ParcelFileDescriptor.createSocketPair();
        WearableSensingSecureChannel wearableSensingSecureChannel =
                new WearableSensingSecureChannel(
                        companionDeviceManager,
                        parcelFileDescriptor,
                        anonymousClass1,
                        createSocketPair[0],
                        createSocketPair[1]);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Slog.d("WearableSensingSecureChannel", "Requesting CDM association.");
            wearableSensingSecureChannel.mCompanionDeviceManager.associate(
                    new AssociationRequest.Builder()
                            .setDisplayName("PlaceholderDisplayNameFromWSM")
                            .setSelfManaged(true)
                            .build(),
                    wearableSensingSecureChannel.mLightWeightExecutor,
                    new CompanionDeviceManager
                            .Callback() { // from class:
                                          // com.android.server.wearable.WearableSensingSecureChannel.1
                        @Override // android.companion.CompanionDeviceManager.Callback
                        public final void onAssociationCreated(AssociationInfo associationInfo) {
                            WearableSensingSecureChannel wearableSensingSecureChannel2 =
                                    WearableSensingSecureChannel.this;
                            int id = associationInfo.getId();
                            wearableSensingSecureChannel2.getClass();
                            Slog.i("WearableSensingSecureChannel", "CDM association created.");
                            synchronized (wearableSensingSecureChannel2.mLock) {
                                try {
                                    if (wearableSensingSecureChannel2.mClosed) {
                                        return;
                                    }
                                    wearableSensingSecureChannel2.mAssociationId =
                                            Integer.valueOf(id);
                                    wearableSensingSecureChannel2.mCompanionDeviceManager
                                            .addOnMessageReceivedListener(
                                                    wearableSensingSecureChannel2
                                                            .mMessageFromWearableExecutor,
                                                    1131446919,
                                                    wearableSensingSecureChannel2
                                                            .mOnMessageReceivedListener);
                                    wearableSensingSecureChannel2.mCompanionDeviceManager
                                            .addOnTransportsChangedListener(
                                                    wearableSensingSecureChannel2
                                                            .mLightWeightExecutor,
                                                    wearableSensingSecureChannel2
                                                            .mOnTransportsChangedListener);
                                    wearableSensingSecureChannel2.mCompanionDeviceManager
                                            .attachSystemDataTransport(
                                                    id,
                                                    new ParcelFileDescriptor.AutoCloseInputStream(
                                                            wearableSensingSecureChannel2
                                                                    .mUnderlyingTransport),
                                                    new ParcelFileDescriptor.AutoCloseOutputStream(
                                                            wearableSensingSecureChannel2
                                                                    .mUnderlyingTransport));
                                } finally {
                                }
                            }
                        }

                        @Override // android.companion.CompanionDeviceManager.Callback
                        public final void onFailure(CharSequence charSequence) {
                            Slog.e(
                                    "WearableSensingSecureChannel",
                                    "Failed to create CompanionDeviceManager association: "
                                            + ((Object) charSequence));
                            WearableSensingSecureChannel.this.onError();
                        }
                    });
            return wearableSensingSecureChannel;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            try {
                if (this.mClosed) {
                    return;
                }
                Slog.i("WearableSensingSecureChannel", "Closing WearableSensingSecureChannel.");
                this.mClosed = true;
                if (this.mAssociationId != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mCompanionDeviceManager.removeOnTransportsChangedListener(
                                this.mOnTransportsChangedListener);
                        this.mCompanionDeviceManager.removeOnMessageReceivedListener(
                                1131446919, this.mOnMessageReceivedListener);
                        this.mCompanionDeviceManager.detachSystemDataTransport(
                                this.mAssociationId.intValue());
                        this.mCompanionDeviceManager.disassociate(this.mAssociationId.intValue());
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                try {
                    this.mLocalIn.close();
                } catch (IOException e) {
                    Slog.e(
                            "WearableSensingSecureChannel",
                            "Encountered IOException when closing local input stream.",
                            e);
                }
                try {
                    this.mLocalOut.close();
                } catch (IOException e2) {
                    Slog.e(
                            "WearableSensingSecureChannel",
                            "Encountered IOException when closing local output stream.",
                            e2);
                }
                this.mMessageFromWearableExecutor.mExecutorService.shutdown();
                this.mMessageToWearableExecutor.mExecutorService.shutdown();
                this.mLightWeightExecutor.mExecutorService.shutdown();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final void onError() {
        synchronized (this.mLock) {
            try {
                if (this.mClosed) {
                    return;
                }
                WearableSensingManagerPerUserService.AnonymousClass1 anonymousClass1 =
                        this.mSecureTransportListener;
                anonymousClass1.getClass();
                if (Flags.enableRestartWssProcess()) {
                    synchronized (WearableSensingManagerPerUserService.this.mSecureChannelLock) {
                        try {
                            WearableSensingSecureChannel wearableSensingSecureChannel =
                                    WearableSensingManagerPerUserService.this.mSecureChannel;
                            if (wearableSensingSecureChannel != null
                                    && wearableSensingSecureChannel
                                            == anonymousClass1.val$currentSecureChannelRef.get()) {
                                RemoteWearableSensingService remoteWearableSensingService =
                                        WearableSensingManagerPerUserService.this.mRemoteService;
                                remoteWearableSensingService.getClass();
                                remoteWearableSensingService.post(
                                        new RemoteWearableSensingService$$ExternalSyntheticLambda7(
                                                0));
                                WearableSensingManagerPerUserService.this.mSecureChannel = null;
                            }
                        } finally {
                        }
                    }
                }
                if (Flags.enableProvideWearableConnectionApi()) {
                    WearableSensingManagerPerUserService.notifyStatusCallback(
                            7, anonymousClass1.val$statusCallback);
                }
                close();
            } finally {
            }
        }
    }
}
