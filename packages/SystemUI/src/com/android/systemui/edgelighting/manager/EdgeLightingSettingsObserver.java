package com.android.systemui.edgelighting.manager;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class EdgeLightingSettingsObserver {
    public static EdgeLightingSettingsObserver sInstance;
    public final HashMap mGlobalObservers = new HashMap();
    public final HashMap mSystemObservers = new HashMap();

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

    public interface EdgeLightingObserver {
        Handler getHandler();

        void onChange();
    }

    public static synchronized EdgeLightingSettingsObserver getInstance() {
        try {
            if (sInstance == null) {
                sInstance = new EdgeLightingSettingsObserver();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public final void unregisterContentObserver(ContentResolver contentResolver, Class cls, EdgeLightingObserver edgeLightingObserver) {
        HashMap map;
        if (cls == Settings.System.class) {
            map = this.mSystemObservers;
        } else {
            if (cls != Settings.Global.class) {
                Slog.e("EdgeLightingSettingsObserver", "unregisterContentObserver : wrong table");
                return;
            }
            map = this.mGlobalObservers;
        }
        ContentObserverWrapper contentObserverWrapper = (ContentObserverWrapper) map.get(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
        if (contentObserverWrapper != null) {
            contentObserverWrapper.mObservers.remove(edgeLightingObserver);
            if (contentObserverWrapper.mObservers.size() == 0) {
                contentResolver.unregisterContentObserver(contentObserverWrapper);
                map.remove(SettingsHelper.INDEX_EDGE_LIGHTING_ON);
            }
        }
    }
}
