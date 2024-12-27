package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.util.Utils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CastControllerImpl implements CastController {
    public static final boolean DEBUG = Log.isLoggable("CastController", 3);
    public boolean mCallbackRegistered;
    public final Context mContext;
    public boolean mDiscovering;
    public final MediaRouter mMediaRouter;
    public MediaProjectionInfo mProjection;
    public final AnonymousClass2 mProjectionCallback;
    public final MediaProjectionManager mProjectionManager;
    public final ArrayList mCallbacks = new ArrayList();
    public final ArrayMap mRoutes = new ArrayMap();
    public final Object mDiscoveringLock = new Object();
    public final Object mProjectionLock = new Object();
    public final AnonymousClass1 mMediaCallback = new MediaRouter.SimpleCallback() { // from class: com.android.systemui.statusbar.policy.CastControllerImpl.1
        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            if (CastControllerImpl.DEBUG) {
                Log.d("CastController", "onRouteAdded: " + CastControllerImpl.routeToString(routeInfo));
            }
            CastControllerImpl.m2239$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            if (CastControllerImpl.DEBUG) {
                Log.d("CastController", "onRouteChanged: " + CastControllerImpl.routeToString(routeInfo));
            }
            CastControllerImpl.m2239$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            if (CastControllerImpl.DEBUG) {
                Log.d("CastController", "onRouteRemoved: " + CastControllerImpl.routeToString(routeInfo));
            }
            CastControllerImpl.m2239$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            if (CastControllerImpl.DEBUG) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onRouteSelected(", "): ");
                m.append(CastControllerImpl.routeToString(routeInfo));
                Log.d("CastController", m.toString());
            }
            CastControllerImpl.m2239$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            if (CastControllerImpl.DEBUG) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onRouteUnselected(", "): ");
                m.append(CastControllerImpl.routeToString(routeInfo));
                Log.d("CastController", m.toString());
            }
            CastControllerImpl.m2239$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }
    };

    /* renamed from: -$$Nest$msetProjection, reason: not valid java name */
    public static void m2238$$Nest$msetProjection(CastControllerImpl castControllerImpl, MediaProjectionInfo mediaProjectionInfo, boolean z) {
        boolean z2;
        MediaProjectionInfo mediaProjectionInfo2 = castControllerImpl.mProjection;
        synchronized (castControllerImpl.mProjectionLock) {
            try {
                boolean equals = Objects.equals(mediaProjectionInfo, castControllerImpl.mProjection);
                z2 = true;
                if (z && !equals) {
                    castControllerImpl.mProjection = mediaProjectionInfo;
                } else if (z || !equals) {
                    z2 = false;
                } else {
                    castControllerImpl.mProjection = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            if (DEBUG) {
                Log.d("CastController", "setProjection: " + mediaProjectionInfo2 + " -> " + castControllerImpl.mProjection);
            }
            castControllerImpl.fireOnCastDevicesChanged();
        }
    }

    /* renamed from: -$$Nest$mupdateRemoteDisplays, reason: not valid java name */
    public static void m2239$$Nest$mupdateRemoteDisplays(CastControllerImpl castControllerImpl) {
        synchronized (castControllerImpl.mRoutes) {
            try {
                castControllerImpl.mRoutes.clear();
                int routeCount = castControllerImpl.mMediaRouter.getRouteCount();
                for (int i = 0; i < routeCount; i++) {
                    MediaRouter.RouteInfo routeAt = castControllerImpl.mMediaRouter.getRouteAt(i);
                    if (routeAt.isEnabled() && routeAt.matchesTypes(4)) {
                        if (routeAt.getTag() == null) {
                            routeAt.setTag(UUID.randomUUID().toString());
                        }
                        castControllerImpl.mRoutes.put(routeAt.getTag().toString(), routeAt);
                    }
                }
                MediaRouter.RouteInfo selectedRoute = castControllerImpl.mMediaRouter.getSelectedRoute(4);
                if (selectedRoute != null && !selectedRoute.isDefault()) {
                    if (selectedRoute.getTag() == null) {
                        selectedRoute.setTag(UUID.randomUUID().toString());
                    }
                    castControllerImpl.mRoutes.put(selectedRoute.getTag().toString(), selectedRoute);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        castControllerImpl.fireOnCastDevicesChanged();
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.policy.CastControllerImpl$1] */
    public CastControllerImpl(Context context, DumpManager dumpManager) {
        MediaProjectionManager.Callback callback = new MediaProjectionManager.Callback() { // from class: com.android.systemui.statusbar.policy.CastControllerImpl.2
            public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
                CastControllerImpl.m2238$$Nest$msetProjection(CastControllerImpl.this, mediaProjectionInfo, true);
            }

            public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
                CastControllerImpl.m2238$$Nest$msetProjection(CastControllerImpl.this, mediaProjectionInfo, false);
            }
        };
        this.mContext = context;
        MediaRouter mediaRouter = (MediaRouter) context.getSystemService("media_router");
        this.mMediaRouter = mediaRouter;
        mediaRouter.setRouterGroupId("android.media.mirroring_group");
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) context.getSystemService("media_projection");
        this.mProjectionManager = mediaProjectionManager;
        this.mProjection = mediaProjectionManager.getActiveProjectionInfo();
        mediaProjectionManager.addCallback(callback, new Handler());
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "CastController", this);
        if (DEBUG) {
            Log.d("CastController", "new CastController()");
        }
    }

    public static String routeToString(MediaRouter.RouteInfo routeInfo) {
        if (routeInfo == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(routeInfo.getName());
        sb.append('/');
        sb.append(routeInfo.getDescription());
        sb.append('@');
        sb.append(routeInfo.getDeviceAddress());
        sb.append(",status=");
        sb.append(routeInfo.getStatus());
        if (routeInfo.isDefault()) {
            sb.append(",default");
        }
        if (routeInfo.isEnabled()) {
            sb.append(",enabled");
        }
        if (routeInfo.isConnecting()) {
            sb.append(",connecting");
        }
        if (routeInfo.isSelected()) {
            sb.append(",selected");
        }
        sb.append(",id=");
        sb.append(routeInfo.getTag());
        return sb.toString();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        CastController.Callback callback = (CastController.Callback) obj;
        synchronized (this.mCallbacks) {
            this.mCallbacks.add(callback);
        }
        callback.onCastDevicesChanged();
        synchronized (this.mDiscoveringLock) {
            handleDiscoveryChangeLocked();
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CastController state:");
        printWriter.print("  mDiscovering=");
        printWriter.println(this.mDiscovering);
        printWriter.print("  mCallbackRegistered=");
        printWriter.println(this.mCallbackRegistered);
        printWriter.print("  mCallbacks.size=");
        synchronized (this.mCallbacks) {
            printWriter.println(this.mCallbacks.size());
        }
        printWriter.print("  mRoutes.size=");
        printWriter.println(this.mRoutes.size());
        for (int i = 0; i < this.mRoutes.size(); i++) {
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) this.mRoutes.valueAt(i);
            printWriter.print("    ");
            printWriter.println(routeToString(routeInfo));
        }
        printWriter.print("  mProjection=");
        printWriter.println(this.mProjection);
    }

    public void fireOnCastDevicesChanged() {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = new ArrayList(this.mCallbacks);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((CastController.Callback) it.next()).onCastDevicesChanged();
        }
    }

    public final String getAppName(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        if (Utils.isHeadlessRemoteDisplayProvider(packageManager, str)) {
            return "";
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                CharSequence loadLabel = applicationInfo.loadLabel(packageManager);
                if (!TextUtils.isEmpty(loadLabel)) {
                    return loadLabel.toString();
                }
            }
            Log.w("CastController", "No label found for package: " + str);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("CastController", "Error getting appName for package: " + str, e);
        }
        return str;
    }

    public final List getCastDevices() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRoutes) {
            try {
                for (MediaRouter.RouteInfo routeInfo : this.mRoutes.values()) {
                    CastController.CastDevice castDevice = new CastController.CastDevice();
                    routeInfo.getTag().toString();
                    CharSequence name = routeInfo.getName(this.mContext);
                    castDevice.name = name != null ? name.toString() : null;
                    CharSequence description = routeInfo.getDescription();
                    if (description != null) {
                        description.toString();
                    }
                    int statusCode = routeInfo.getStatusCode();
                    if (statusCode == 2) {
                        castDevice.state = 1;
                    } else {
                        if (!routeInfo.isSelected() && statusCode != 6) {
                            castDevice.state = 0;
                        }
                        castDevice.state = 2;
                    }
                    castDevice.tag = routeInfo;
                    arrayList.add(castDevice);
                }
            } finally {
            }
        }
        synchronized (this.mProjectionLock) {
            try {
                if (this.mProjection != null) {
                    CastController.CastDevice castDevice2 = new CastController.CastDevice();
                    this.mProjection.getPackageName();
                    castDevice2.name = getAppName(this.mProjection.getPackageName());
                    this.mContext.getString(R.string.quick_settings_casting);
                    castDevice2.state = 2;
                    castDevice2.tag = this.mProjection;
                    arrayList.add(castDevice2);
                }
            } finally {
            }
        }
        return arrayList;
    }

    public final void handleDiscoveryChangeLocked() {
        boolean isEmpty;
        if (this.mCallbackRegistered) {
            this.mMediaRouter.removeCallback(this.mMediaCallback);
            this.mCallbackRegistered = false;
        }
        if (this.mDiscovering) {
            this.mMediaRouter.addCallback(4, this.mMediaCallback, 4);
            this.mCallbackRegistered = true;
            return;
        }
        synchronized (this.mCallbacks) {
            isEmpty = this.mCallbacks.isEmpty();
        }
        if (isEmpty) {
            return;
        }
        this.mMediaRouter.addCallback(4, this.mMediaCallback, 8);
        this.mCallbackRegistered = true;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        CastController.Callback callback = (CastController.Callback) obj;
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
        }
        synchronized (this.mDiscoveringLock) {
            handleDiscoveryChangeLocked();
        }
    }
}
