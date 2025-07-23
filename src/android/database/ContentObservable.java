package android.database;

import android.net.Uri;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ContentObservable extends Observable<ContentObserver> {
    @Override // android.database.Observable
    public void registerObserver(ContentObserver contentObserver) {
        super.registerObserver((ContentObservable) contentObserver);
    }

    @Deprecated
    public void dispatchChange(boolean z) {
        dispatchChange(z, null);
    }

    public void dispatchChange(boolean z, Uri uri) {
        synchronized (this.mObservers) {
            Iterator it = this.mObservers.iterator();
            while (it.hasNext()) {
                ContentObserver contentObserver = (ContentObserver) it.next();
                if (!z || contentObserver.deliverSelfNotifications()) {
                    contentObserver.dispatchChange(z, uri);
                }
            }
        }
    }

    @Deprecated
    public void notifyChange(boolean z) {
        synchronized (this.mObservers) {
            Iterator it = this.mObservers.iterator();
            while (it.hasNext()) {
                ((ContentObserver) it.next()).onChange(z, null);
            }
        }
    }
}
