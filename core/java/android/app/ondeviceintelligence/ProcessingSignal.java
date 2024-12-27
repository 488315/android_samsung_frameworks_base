package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.PersistableBundle;
import android.os.RemoteException;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
public final class ProcessingSignal {
    private static final int MAX_QUEUE_SIZE = 10;
    private Executor mExecutor;
    private OnProcessingSignalCallback mOnProcessingSignalCallback;
    private IProcessingSignal mRemote;
    private final Object mLock = new Object();
    private final ArrayDeque<PersistableBundle> mActionParamsQueue = new ArrayDeque<>(10);

    public interface OnProcessingSignalCallback {
        void onSignalReceived(PersistableBundle persistableBundle);
    }

    public void sendSignal(PersistableBundle actionParams) {
        synchronized (this.mLock) {
            if (this.mActionParamsQueue.size() > 10) {
                throw new RuntimeException("Maximum actions that can be queued are : 10");
            }
            this.mActionParamsQueue.add(actionParams);
            final OnProcessingSignalCallback callback = this.mOnProcessingSignalCallback;
            IProcessingSignal remote = this.mRemote;
            if (callback != null) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    final PersistableBundle params = this.mActionParamsQueue.removeFirst();
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.app.ondeviceintelligence.ProcessingSignal$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ProcessingSignal.OnProcessingSignalCallback.this
                                            .onSignalReceived(params);
                                }
                            });
                }
            }
            if (remote != null) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    try {
                        remote.sendSignal(this.mActionParamsQueue.removeFirst());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void setOnProcessingSignalCallback(
            Executor executor, final OnProcessingSignalCallback callback) {
        Objects.requireNonNull(executor);
        synchronized (this.mLock) {
            if (this.mOnProcessingSignalCallback == callback) {
                return;
            }
            this.mOnProcessingSignalCallback = callback;
            this.mExecutor = executor;
            if (callback != null && !this.mActionParamsQueue.isEmpty()) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    final PersistableBundle params = this.mActionParamsQueue.removeFirst();
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                // android.app.ondeviceintelligence.ProcessingSignal$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ProcessingSignal.OnProcessingSignalCallback.this
                                            .onSignalReceived(params);
                                }
                            });
                }
            }
        }
    }

    void setRemote(IProcessingSignal remote) {
        synchronized (this.mLock) {
            this.mRemote = remote;
            if (!this.mActionParamsQueue.isEmpty() && remote != null) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    try {
                        remote.sendSignal(this.mActionParamsQueue.removeFirst());
                    } catch (RemoteException e) {
                        throw new RuntimeException("Failed to send action to remote signal", e);
                    }
                }
            }
        }
    }

    public static IProcessingSignal createTransport() {
        return new Transport();
    }

    public static ProcessingSignal fromTransport(IProcessingSignal transport) {
        if (transport instanceof Transport) {
            return ((Transport) transport).mProcessingSignal;
        }
        return null;
    }

    private static final class Transport extends IProcessingSignal.Stub {
        final ProcessingSignal mProcessingSignal;

        private Transport() {
            this.mProcessingSignal = new ProcessingSignal();
        }

        @Override // android.app.ondeviceintelligence.IProcessingSignal
        public void sendSignal(PersistableBundle actionParams) {
            this.mProcessingSignal.sendSignal(actionParams);
        }
    }
}
