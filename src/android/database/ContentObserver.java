package android.database;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public abstract class ContentObserver {
    private static final long ADD_CONTENT_OBSERVER_FLAGS = 150939131;
    private final Executor mExecutor;
    Handler mHandler;
    private final Object mLock;
    private Transport mTransport;

    private static boolean isChangeEnabledAddContentObserverFlags$ravenwood() {
        return true;
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    public void onChange(boolean z) {
    }

    public ContentObserver(Handler handler) {
        this.mLock = new Object();
        this.mHandler = handler;
        this.mExecutor = null;
    }

    public ContentObserver(Executor executor, int i) {
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
        Transport transport;
        synchronized (this.mLock) {
            transport = this.mTransport;
            if (transport != null) {
                transport.releaseContentObserver();
                this.mTransport = null;
            }
        }
        return transport;
    }

    public void onChange(boolean z, Uri uri) {
        onChange(z);
    }

    public void onChange(boolean z, Uri uri, int i) {
        onChange(z, uri);
    }

    public void onChange(boolean z, Collection<Uri> collection, int i) {
        Iterator<Uri> it = collection.iterator();
        while (it.hasNext()) {
            onChange(z, it.next(), i);
        }
    }

    @SystemApi
    public void onChange(boolean z, Collection<Uri> collection, int i, UserHandle userHandle) {
        onChange(z, collection, userHandle.getIdentifier());
    }

    public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
        if (!isChangeEnabledAddContentObserverFlags() || Process.myUid() == 1000) {
            onChange(z, collection, i, UserHandle.of(i2));
        } else {
            onChange(z, collection, i);
        }
    }

    private static boolean isChangeEnabledAddContentObserverFlags() {
        return CompatChanges.isChangeEnabled(ADD_CONTENT_OBSERVER_FLAGS);
    }

    @Deprecated
    public final void dispatchChange(boolean z) {
        dispatchChange(z, null);
    }

    public final void dispatchChange(boolean z, Uri uri) {
        dispatchChange(z, uri, 0);
    }

    public final void dispatchChange(boolean z, Uri uri, int i) {
        dispatchChange(z, Arrays.asList(uri), i);
    }

    public final void dispatchChange(boolean z, Collection<Uri> collection, int i) {
        dispatchChange(z, collection, i, UserHandle.getCallingUserId());
    }

    public final void dispatchChange(final boolean z, final Collection<Uri> collection, final int i, final int i2) {
        Executor executor = this.mExecutor;
        if (executor != null) {
            executor.execute(new Runnable() { // from class: android.database.ContentObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$dispatchChange$0(z, collection, i, i2);
                }
            });
            return;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: android.database.ContentObserver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$dispatchChange$1(z, collection, i, i2);
                }
            });
        } else {
            onChange(z, collection, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChange$0(boolean z, Collection collection, int i, int i2) {
        onChange(z, (Collection<Uri>) collection, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChange$1(boolean z, Collection collection, int i, int i2) {
        onChange(z, (Collection<Uri>) collection, i, i2);
    }

    private static final class Transport extends IContentObserver.Stub {
        private ContentObserver mContentObserver;

        public Transport(ContentObserver contentObserver) {
            this.mContentObserver = contentObserver;
        }

        @Override // android.database.IContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            onChangeEtc(z, new Uri[]{uri}, 0, i);
        }

        @Override // android.database.IContentObserver
        public void onChangeEtc(boolean z, Uri[] uriArr, int i, int i2) {
            ContentObserver contentObserver = this.mContentObserver;
            if (contentObserver != null) {
                contentObserver.dispatchChange(z, Arrays.asList(uriArr), i, i2);
            }
        }

        public void releaseContentObserver() {
            this.mContentObserver = null;
        }
    }
}
