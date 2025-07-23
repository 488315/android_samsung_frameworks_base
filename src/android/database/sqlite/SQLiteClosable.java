package android.database.sqlite;

import java.io.Closeable;

/* loaded from: classes.dex */
public abstract class SQLiteClosable implements Closeable {
    private int mReferenceCount = 1;
    protected boolean mTrackClosure = false;
    private Throwable mClosedBy = null;

    protected abstract void onAllReferencesReleased();

    @Deprecated
    protected void onAllReferencesReleasedFromContainer() {
        onAllReferencesReleased();
    }

    public void acquireReference() {
        synchronized (this) {
            int i = this.mReferenceCount;
            if (i <= 0) {
                throw new IllegalStateException("attempt to re-open an already-closed object: " + this, this.mClosedBy);
            }
            this.mReferenceCount = i + 1;
        }
    }

    public void releaseReference() {
        boolean z;
        synchronized (this) {
            z = true;
            int i = this.mReferenceCount - 1;
            this.mReferenceCount = i;
            if (i != 0) {
                z = false;
            }
        }
        if (z) {
            onAllReferencesReleased();
        }
    }

    @Deprecated
    public void releaseReferenceFromContainer() {
        boolean z;
        synchronized (this) {
            z = true;
            int i = this.mReferenceCount - 1;
            this.mReferenceCount = i;
            if (i != 0) {
                z = false;
            }
        }
        if (z) {
            onAllReferencesReleasedFromContainer();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        releaseReference();
        synchronized (this) {
            if (this.mTrackClosure && this.mClosedBy == null) {
                this.mClosedBy = new Exception("closed by " + getClass().getName() + ".close()").fillInStackTrace();
            }
        }
    }
}
