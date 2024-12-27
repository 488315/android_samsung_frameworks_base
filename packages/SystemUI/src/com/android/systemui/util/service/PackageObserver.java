package com.android.systemui.util.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.systemui.util.service.Observer;
import com.google.android.collect.Lists;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class PackageObserver implements Observer {
    private final Context mContext;
    private final String mPackageName;
    private static final String TAG = "PackageObserver";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private final ArrayList<WeakReference<Observer.Callback>> mCallbacks = Lists.newArrayList();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.util.service.PackageObserver.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (PackageObserver.DEBUG) {
                Log.d(PackageObserver.TAG, "package added receiver - onReceive");
            }
            Iterator it = PackageObserver.this.mCallbacks.iterator();
            while (it.hasNext()) {
                Observer.Callback callback = (Observer.Callback) ((WeakReference) it.next()).get();
                if (callback != null) {
                    callback.onSourceChanged();
                } else {
                    it.remove();
                }
            }
        }
    };

    public PackageObserver(Context context, ComponentName componentName) {
        this.mContext = context;
        this.mPackageName = componentName.getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeCallback$0(Observer.Callback callback, WeakReference weakReference) {
        return weakReference.get() == callback;
    }

    @Override // com.android.systemui.util.service.Observer
    public void addCallback(Observer.Callback callback) {
        if (DEBUG) {
            Log.d(TAG, "addCallback:" + callback);
        }
        this.mCallbacks.add(new WeakReference<>(callback));
        if (this.mCallbacks.size() > 1) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(this.mPackageName, 0);
        this.mContext.registerReceiver(this.mReceiver, intentFilter, 2);
    }

    @Override // com.android.systemui.util.service.Observer
    public void removeCallback(final Observer.Callback callback) {
        if (DEBUG) {
            Log.d(TAG, "removeCallback:" + callback);
        }
        if (this.mCallbacks.removeIf(new Predicate() { // from class: com.android.systemui.util.service.PackageObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$removeCallback$0;
                lambda$removeCallback$0 = PackageObserver.lambda$removeCallback$0(Observer.Callback.this, (WeakReference) obj);
                return lambda$removeCallback$0;
            }
        }) && this.mCallbacks.isEmpty()) {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
    }
}
