package com.android.systemui.edgelighting.manager;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class EdgeLightingSettingsObserver {
    public static EdgeLightingSettingsObserver sInstance;
    public final HashMap mGlobalObservers = new HashMap();
    public final HashMap mSystemObservers = new HashMap();

    public final class ContentObserverWrapper extends ContentObserver {
        public final ArrayList mObservers;

        public ContentObserverWrapper(Handler handler) {
            super(handler);
            this.mObservers = new ArrayList();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Iterator it = this.mObservers.iterator();
            while (it.hasNext()) {
                EdgeLightingObserver edgeLightingObserver = (EdgeLightingObserver) it.next();
                Handler handler = edgeLightingObserver.getHandler();
                if (handler != null) {
                    handler.post(new Runnable(this, edgeLightingObserver, z) { // from class: com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.ContentObserverWrapper.1
                        public final /* synthetic */ EdgeLightingObserver val$observer;
                        public final /* synthetic */ boolean val$selfChange;

                        @Override // java.lang.Runnable
                        public final void run() {
                            this.val$observer.onChange();
                        }
                    });
                } else {
                    edgeLightingObserver.onChange();
                }
            }
        }
    }

    public interface EdgeLightingObserver {
        Handler getHandler();

        void onChange();
    }

    public static synchronized EdgeLightingSettingsObserver getInstance() {
        EdgeLightingSettingsObserver edgeLightingSettingsObserver;
        synchronized (EdgeLightingSettingsObserver.class) {
            try {
                if (sInstance == null) {
                    sInstance = new EdgeLightingSettingsObserver();
                }
                edgeLightingSettingsObserver = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return edgeLightingSettingsObserver;
    }

    public final void unregisterContentObserver(ContentResolver contentResolver, Class cls, EdgeLightingObserver edgeLightingObserver) {
        HashMap hashMap;
        if (cls == Settings.System.class) {
            hashMap = this.mSystemObservers;
        } else {
            if (cls != Settings.Global.class) {
                Slog.e("EdgeLightingSettingsObserver", "unregisterContentObserver : wrong table");
                return;
            }
            hashMap = this.mGlobalObservers;
        }
        ContentObserverWrapper contentObserverWrapper = (ContentObserverWrapper) hashMap.get(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
        if (contentObserverWrapper != null) {
            contentObserverWrapper.mObservers.remove(edgeLightingObserver);
            if (contentObserverWrapper.mObservers.size() == 0) {
                contentResolver.unregisterContentObserver(contentObserverWrapper);
                hashMap.remove(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            }
        }
    }
}
