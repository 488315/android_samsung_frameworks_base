package androidx.core.os;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CancellationSignal {
    public boolean mCancelInProgress;
    public boolean mIsCanceled;
    public OnCancelListener mOnCancelListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnCancelListener {
        void onCancel();
    }

    public final void cancel() {
        synchronized (this) {
            if (this.mIsCanceled) {
                return;
            }
            this.mIsCanceled = true;
            this.mCancelInProgress = true;
            OnCancelListener onCancelListener = this.mOnCancelListener;
            if (onCancelListener != null) {
                try {
                    onCancelListener.onCancel();
                } catch (Throwable th) {
                    synchronized (this) {
                        this.mCancelInProgress = false;
                        notifyAll();
                        throw th;
                    }
                }
            }
            synchronized (this) {
                this.mCancelInProgress = false;
                notifyAll();
            }
        }
    }

    public final void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            while (this.mCancelInProgress) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (this.mIsCanceled) {
                onCancelListener.onCancel();
            }
        }
    }
}
