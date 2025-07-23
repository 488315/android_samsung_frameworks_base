package com.android.systemui.edgelighting.manager;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EdgeLightingSettingsObserver {
    public static EdgeLightingSettingsObserver sInstance;
    public final HashMap mGlobalObservers = new HashMap();
    public final HashMap mSystemObservers = new HashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ContentObserverWrapper extends ContentObserver {
        public final ArrayList mObservers;

        public ContentObserverWrapper(Handler handler) {
            super(handler);
            this.mObservers = new ArrayList();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            ArrayList arrayList = this.mObservers;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                EdgeLightingObserver edgeLightingObserver = (EdgeLightingObserver) obj;
                Handler handler = edgeLightingObserver.getHandler();
                if (handler != null) {
                    handler.post(new Runnable(this, edgeLightingObserver, z) { // from class: com.android.systemui.edgelighting.manager.EdgeLightingSettingsObserver.ContentObserverWrapper.1
                        public final /* synthetic */ EdgeLightingObserver val$observer;

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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
