package com.android.server.location.injector;

import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class PackageResetHelper {
    public final CopyOnWriteArrayList mResponders = new CopyOnWriteArrayList();

    public interface Responder {
        boolean isResetableForPackage(String str);

        void onPackageReset(String str);
    }

    public abstract void onRegister();

    public abstract void onUnregister();

    public synchronized void register(Responder responder) {
        boolean isEmpty = this.mResponders.isEmpty();
        this.mResponders.add(responder);
        if (isEmpty) {
            onRegister();
        }
    }

    public synchronized void unregister(Responder responder) {
        this.mResponders.remove(responder);
        if (this.mResponders.isEmpty()) {
            onUnregister();
        }
    }

    public final void notifyPackageReset(String str) {
        Log.d("LocationManagerService", "package " + str + " reset");
        Iterator it = this.mResponders.iterator();
        while (it.hasNext()) {
            ((Responder) it.next()).onPackageReset(str);
        }
    }

    public final boolean queryResetableForPackage(String str) {
        Iterator it = this.mResponders.iterator();
        while (it.hasNext()) {
            if (((Responder) it.next()).isResetableForPackage(str)) {
                return true;
            }
        }
        return false;
    }
}
