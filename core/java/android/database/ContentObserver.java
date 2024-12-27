package android.database;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class ContentObserver {
    private static final long ADD_CONTENT_OBSERVER_FLAGS = 150939131;
    private final Executor mExecutor;
    Handler mHandler;
    private final Object mLock;
    private Transport mTransport;

    public ContentObserver(Handler handler) {
        this.mLock = new Object();
        this.mHandler = handler;
        this.mExecutor = null;
    }

    public ContentObserver(Executor executor, int unused) {
        this.mLock = new Object();
        this.mExecutor = executor;
    }

    public IContentObserver getContentObserver() {
        Transport transport;
        synchronized (this.mLock) {
            if (this.mTransport == null) {
                this.mTransport = new Transport(this);
            }
            transport = this.mTransport;
        }
        return transport;
    }

    public IContentObserver releaseContentObserver() {
        Transport oldTransport;
        synchronized (this.mLock) {
            oldTransport = this.mTransport;
            if (oldTransport != null) {
                oldTransport.releaseContentObserver();
                this.mTransport = null;
            }
        }
        return oldTransport;
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    public void onChange(boolean selfChange) {}

    public void onChange(boolean selfChange, Uri uri) {
        onChange(selfChange);
    }

    public void onChange(boolean selfChange, Uri uri, int flags) {
        onChange(selfChange, uri);
    }

    public void onChange(boolean selfChange, Collection<Uri> uris, int flags) {
        for (Uri uri : uris) {
            onChange(selfChange, uri, flags);
        }
    }

    @SystemApi
    public void onChange(boolean selfChange, Collection<Uri> uris, int flags, UserHandle user) {
        onChange(selfChange, uris, user.getIdentifier());
    }

    public void onChange(boolean selfChange, Collection<Uri> uris, int flags, int userId) {
        if (!isChangeEnabledAddContentObserverFlags() || Process.myUid() == 1000) {
            onChange(selfChange, uris, flags, UserHandle.of(userId));
        } else {
            onChange(selfChange, uris, flags);
        }
    }

    private static boolean isChangeEnabledAddContentObserverFlags() {
        return CompatChanges.isChangeEnabled(ADD_CONTENT_OBSERVER_FLAGS);
    }

    private static boolean isChangeEnabledAddContentObserverFlags$ravenwood() {
        return true;
    }

    @Deprecated
    public final void dispatchChange(boolean selfChange) {
        dispatchChange(selfChange, null);
    }

    public final void dispatchChange(boolean selfChange, Uri uri) {
        dispatchChange(selfChange, uri, 0);
    }

    public final void dispatchChange(boolean selfChange, Uri uri, int flags) {
        dispatchChange(selfChange, Arrays.asList(uri), flags);
    }

    public final void dispatchChange(boolean selfChange, Collection<Uri> uris, int flags) {
        dispatchChange(selfChange, uris, flags, UserHandle.getCallingUserId());
    }

    public final void dispatchChange(
            final boolean selfChange,
            final Collection<Uri> uris,
            final int flags,
            final int userId) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.database.ContentObserver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContentObserver.this.lambda$dispatchChange$0(
                                    selfChange, uris, flags, userId);
                        }
                    });
        } else if (this.mHandler != null) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // android.database.ContentObserver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ContentObserver.this.lambda$dispatchChange$1(
                                    selfChange, uris, flags, userId);
                        }
                    });
        } else {
            onChange(selfChange, uris, flags, userId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChange$0(
            boolean selfChange, Collection uris, int flags, int userId) {
        onChange(selfChange, (Collection<Uri>) uris, flags, userId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChange$1(
            boolean selfChange, Collection uris, int flags, int userId) {
        onChange(selfChange, (Collection<Uri>) uris, flags, userId);
    }

    private static final class Transport extends IContentObserver.Stub {
        private ContentObserver mContentObserver;

        public Transport(ContentObserver contentObserver) {
            this.mContentObserver = contentObserver;
        }

        @Override // android.database.IContentObserver
        public void onChange(boolean selfChange, Uri uri, int userId) {
            onChangeEtc(selfChange, new Uri[] {uri}, 0, userId);
        }

        @Override // android.database.IContentObserver
        public void onChangeEtc(boolean selfChange, Uri[] uris, int flags, int userId) {
            ContentObserver contentObserver = this.mContentObserver;
            if (contentObserver != null) {
                contentObserver.dispatchChange(selfChange, Arrays.asList(uris), flags, userId);
            }
        }

        public void releaseContentObserver() {
            this.mContentObserver = null;
        }
    }
}
