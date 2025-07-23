package android.database;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class CursorToBulkCursorAdaptor extends BulkCursorNative implements IBinder.DeathRecipient {
    private static final String TAG = "Cursor";
    private CrossProcessCursor mCursor;
    private CursorWindow mFilledWindow;
    private final Object mLock;
    private ContentObserverProxy mObserver;
    private final String mProviderName;

    private static final class ContentObserverProxy extends ContentObserver {
        protected IContentObserver mRemote;

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return false;
        }

        public ContentObserverProxy(IContentObserver iContentObserver, IBinder.DeathRecipient deathRecipient) {
            super(null);
            this.mRemote = iContentObserver;
            try {
                iContentObserver.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException unused) {
            }
        }

        public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient) {
            return this.mRemote.asBinder().unlinkToDeath(deathRecipient, 0);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
            final ArrayList arrayList = new ArrayList();
            collection.forEach(new Consumer() { // from class: android.database.CursorToBulkCursorAdaptor$ContentObserverProxy$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((Uri) obj);
                }
            });
            try {
                this.mRemote.onChangeEtc(z, (Uri[]) arrayList.toArray(new Uri[arrayList.size()]), i, i2);
            } catch (RemoteException unused) {
            }
        }
    }

    public CursorToBulkCursorAdaptor(Cursor cursor, IContentObserver iContentObserver, String str) {
        Object obj = new Object();
        this.mLock = obj;
        if (cursor instanceof CrossProcessCursor) {
            this.mCursor = (CrossProcessCursor) cursor;
        } else {
            this.mCursor = new CrossProcessCursorWrapper(cursor);
        }
        this.mProviderName = str;
        synchronized (obj) {
            createAndRegisterObserverProxyLocked(iContentObserver);
        }
    }

    private void closeFilledWindowLocked() {
        CursorWindow cursorWindow = this.mFilledWindow;
        if (cursorWindow != null) {
            cursorWindow.close();
            this.mFilledWindow = null;
        }
    }

    private void disposeLocked() {
        if (this.mCursor != null) {
            unregisterObserverProxyLocked();
            this.mCursor.close();
            this.mCursor = null;
        }
        closeFilledWindowLocked();
    }

    private void throwIfCursorIsClosed() {
        if (this.mCursor == null) {
            throw new StaleDataException("Attempted to access a cursor after it has been closed.");
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mLock) {
            disposeLocked();
        }
    }

    public BulkCursorDescriptor getBulkCursorDescriptor() {
        BulkCursorDescriptor bulkCursorDescriptor;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            bulkCursorDescriptor = new BulkCursorDescriptor();
            bulkCursorDescriptor.cursor = this;
            bulkCursorDescriptor.columnNames = this.mCursor.getColumnNames();
            bulkCursorDescriptor.wantsAllOnMoveCalls = this.mCursor.getWantsAllOnMoveCalls();
            bulkCursorDescriptor.count = this.mCursor.getCount();
            bulkCursorDescriptor.window = this.mCursor.getWindow();
            if (bulkCursorDescriptor.window != null) {
                bulkCursorDescriptor.window.acquireReference();
            }
        }
        return bulkCursorDescriptor;
    }

    @Override // android.database.IBulkCursor
    public CursorWindow getWindow(int i) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            if (!this.mCursor.moveToPosition(i)) {
                closeFilledWindowLocked();
                return null;
            }
            CursorWindow window = this.mCursor.getWindow();
            if (window != null) {
                closeFilledWindowLocked();
            } else {
                window = this.mFilledWindow;
                if (window == null) {
                    window = new CursorWindow(this.mProviderName);
                    this.mFilledWindow = window;
                } else if (i < window.getStartPosition() || i >= window.getStartPosition() + window.getNumRows()) {
                    window.clear();
                }
                this.mCursor.fillWindow(i, window);
            }
            if (window != null) {
                window.acquireReference();
            }
            return window;
        }
    }

    @Override // android.database.IBulkCursor
    public void onMove(int i) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            CrossProcessCursor crossProcessCursor = this.mCursor;
            crossProcessCursor.onMove(crossProcessCursor.getPosition(), i);
        }
    }

    @Override // android.database.IBulkCursor
    public void deactivate() {
        synchronized (this.mLock) {
            if (this.mCursor != null) {
                unregisterObserverProxyLocked();
                this.mCursor.deactivate();
            }
            closeFilledWindowLocked();
        }
    }

    @Override // android.database.IBulkCursor
    public void close() {
        synchronized (this.mLock) {
            disposeLocked();
        }
    }

    @Override // android.database.IBulkCursor
    public int requery(IContentObserver iContentObserver) {
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            closeFilledWindowLocked();
            try {
                if (!this.mCursor.requery()) {
                    return -1;
                }
                unregisterObserverProxyLocked();
                createAndRegisterObserverProxyLocked(iContentObserver);
                return this.mCursor.getCount();
            } catch (IllegalStateException e) {
                throw new IllegalStateException(this.mProviderName + " Requery misuse db, mCursor isClosed:" + this.mCursor.isClosed(), e);
            }
        }
    }

    private void createAndRegisterObserverProxyLocked(IContentObserver iContentObserver) {
        if (this.mObserver != null) {
            throw new IllegalStateException("an observer is already registered");
        }
        ContentObserverProxy contentObserverProxy = new ContentObserverProxy(iContentObserver, this);
        this.mObserver = contentObserverProxy;
        this.mCursor.registerContentObserver(contentObserverProxy);
    }

    private void unregisterObserverProxyLocked() {
        ContentObserverProxy contentObserverProxy = this.mObserver;
        if (contentObserverProxy != null) {
            this.mCursor.unregisterContentObserver(contentObserverProxy);
            this.mObserver.unlinkToDeath(this);
            this.mObserver = null;
        }
    }

    @Override // android.database.IBulkCursor
    public Bundle getExtras() {
        Bundle extras;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            extras = this.mCursor.getExtras();
        }
        return extras;
    }

    @Override // android.database.IBulkCursor
    public Bundle respond(Bundle bundle) {
        Bundle respond;
        synchronized (this.mLock) {
            throwIfCursorIsClosed();
            respond = this.mCursor.respond(bundle);
        }
        return respond;
    }
}
