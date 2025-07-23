package com.android.internal.infra;

import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import com.android.internal.util.FunctionalUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public abstract class RemoteStream<RES, IOSTREAM extends Closeable> extends AndroidFuture<RES> implements Runnable {
    private final FunctionalUtils.ThrowingFunction<IOSTREAM, RES> mHandleStream;
    private volatile ParcelFileDescriptor mLocalPipe;

    protected abstract IOSTREAM createStream(ParcelFileDescriptor parcelFileDescriptor);

    public static <R> AndroidFuture<R> receiveBytes(FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor> throwingConsumer, FunctionalUtils.ThrowingFunction<InputStream, R> throwingFunction) {
        return new RemoteStream<R, InputStream>(throwingConsumer, throwingFunction, AsyncTask.THREAD_POOL_EXECUTOR, true) { // from class: com.android.internal.infra.RemoteStream.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.android.internal.infra.RemoteStream
            public InputStream createStream(ParcelFileDescriptor parcelFileDescriptor) {
                return new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            }
        };
    }

    public static AndroidFuture<byte[]> receiveBytes(FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor> throwingConsumer) {
        return receiveBytes(throwingConsumer, new FunctionalUtils.ThrowingFunction() { // from class: com.android.internal.infra.RemoteStream$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final Object applyOrThrow(Object obj) {
                return RemoteStream.readAll((InputStream) obj);
            }
        });
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[16384];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static <R> AndroidFuture<R> sendBytes(FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor> throwingConsumer, FunctionalUtils.ThrowingFunction<OutputStream, R> throwingFunction) {
        return new RemoteStream<R, OutputStream>(throwingConsumer, throwingFunction, AsyncTask.THREAD_POOL_EXECUTOR, false) { // from class: com.android.internal.infra.RemoteStream.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.android.internal.infra.RemoteStream
            public OutputStream createStream(ParcelFileDescriptor parcelFileDescriptor) {
                return new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            }
        };
    }

    public static AndroidFuture<Void> sendBytes(FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor> throwingConsumer, final FunctionalUtils.ThrowingConsumer<OutputStream> throwingConsumer2) {
        return sendBytes(throwingConsumer, new FunctionalUtils.ThrowingFunction() { // from class: com.android.internal.infra.RemoteStream$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final Object applyOrThrow(Object obj) {
                return RemoteStream.lambda$sendBytes$0(FunctionalUtils.ThrowingConsumer.this, (OutputStream) obj);
            }
        });
    }

    static /* synthetic */ Void lambda$sendBytes$0(FunctionalUtils.ThrowingConsumer throwingConsumer, OutputStream outputStream) throws Exception {
        throwingConsumer.acceptOrThrow(outputStream);
        return null;
    }

    public static AndroidFuture<Void> sendBytes(FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor> throwingConsumer, final byte[] bArr) {
        return sendBytes(throwingConsumer, new FunctionalUtils.ThrowingFunction() { // from class: com.android.internal.infra.RemoteStream$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final Object applyOrThrow(Object obj) {
                return RemoteStream.lambda$sendBytes$1(bArr, (OutputStream) obj);
            }
        });
    }

    static /* synthetic */ Void lambda$sendBytes$1(byte[] bArr, OutputStream outputStream) throws Exception {
        outputStream.write(bArr);
        return null;
    }

    private RemoteStream(FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor> throwingConsumer, FunctionalUtils.ThrowingFunction<IOSTREAM, RES> throwingFunction, Executor executor, boolean z) {
        this.mHandleStream = throwingFunction;
        try {
            ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            ParcelFileDescriptor parcelFileDescriptor = createPipe[z ? 1 : 0];
            try {
                throwingConsumer.acceptOrThrow(parcelFileDescriptor);
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
                this.mLocalPipe = createPipe[!z ? 1 : 0];
                executor.execute(this);
                orTimeout(30L, TimeUnit.SECONDS);
            } finally {
            }
        } catch (Throwable th) {
            completeExceptionally(th);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            IOSTREAM createStream = createStream(this.mLocalPipe);
            try {
                complete(this.mHandleStream.applyOrThrow(createStream));
                if (createStream != null) {
                    createStream.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            completeExceptionally(th);
        }
    }

    @Override // com.android.internal.infra.AndroidFuture
    protected void onCompleted(RES res, Throwable th) {
        super.onCompleted(res, th);
        IoUtils.closeQuietly(this.mLocalPipe);
    }
}
