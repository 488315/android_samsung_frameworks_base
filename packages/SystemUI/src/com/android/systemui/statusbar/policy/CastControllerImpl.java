package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.util.ArrayMap;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.CastControllerLogger;
import com.android.systemui.statusbar.policy.CastDevice;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public class CastControllerImpl implements CastController {
    public boolean mCallbackRegistered;
    public final Context mContext;
    public final CastControllerLogger mLogger;
    public final MediaRouter mMediaRouter;
    public final PackageManager mPackageManager;
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
            CastControllerLogger castControllerLogger = CastControllerImpl.this.mLogger;
            castControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(8);
            LogBuffer logBuffer = castControllerLogger.logger;
            LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
            CastControllerLogger.Companion.getClass();
            ((LogMessageImpl) logMessageObtain).str1 = CastControllerLogger.Companion.toLogString(routeInfo);
            logBuffer.commit(logMessageObtain);
            CastControllerImpl.m3099$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            CastControllerLogger castControllerLogger = CastControllerImpl.this.mLogger;
            castControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer = castControllerLogger.logger;
            LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
            CastControllerLogger.Companion.getClass();
            ((LogMessageImpl) logMessageObtain).str1 = CastControllerLogger.Companion.toLogString(routeInfo);
            logBuffer.commit(logMessageObtain);
            CastControllerImpl.m3099$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            CastControllerLogger castControllerLogger = CastControllerImpl.this.mLogger;
            castControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer = castControllerLogger.logger;
            LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
            CastControllerLogger.Companion.getClass();
            ((LogMessageImpl) logMessageObtain).str1 = CastControllerLogger.Companion.toLogString(routeInfo);
            logBuffer.commit(logMessageObtain);
            CastControllerImpl.m3099$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            CastControllerLogger castControllerLogger = CastControllerImpl.this.mLogger;
            castControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer = castControllerLogger.logger;
            LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
            CastControllerLogger.Companion.getClass();
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = CastControllerLogger.Companion.toLogString(routeInfo);
            logMessageImpl.int1 = i;
            logBuffer.commit(logMessageObtain);
            CastControllerImpl.m3099$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            CastControllerLogger castControllerLogger = CastControllerImpl.this.mLogger;
            castControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = castControllerLogger.logger;
            LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
            CastControllerLogger.Companion.getClass();
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = CastControllerLogger.Companion.toLogString(routeInfo);
            logMessageImpl.int1 = i;
            logBuffer.commit(logMessageObtain);
            CastControllerImpl.m3099$$Nest$mupdateRemoteDisplays(CastControllerImpl.this);
        }
    };

    /* renamed from: -$$Nest$msetProjection, reason: not valid java name */
    public static void m3098$$Nest$msetProjection(CastControllerImpl castControllerImpl, MediaProjectionInfo mediaProjectionInfo, boolean z) {
        boolean z2;
        MediaProjectionInfo mediaProjectionInfo2 = castControllerImpl.mProjection;
        synchronized (castControllerImpl.mProjectionLock) {
            try {
                boolean zEquals = Objects.equals(mediaProjectionInfo, castControllerImpl.mProjection);
                z2 = true;
                if (z && !zEquals) {
                    castControllerImpl.mProjection = mediaProjectionInfo;
                } else if (z || !zEquals) {
                    z2 = false;
                } else {
                    castControllerImpl.mProjection = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            CastControllerLogger castControllerLogger = castControllerImpl.mLogger;
            MediaProjectionInfo mediaProjectionInfo3 = castControllerImpl.mProjection;
            castControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer = castControllerLogger.logger;
            LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = String.valueOf(mediaProjectionInfo2);
            logMessageImpl.str2 = String.valueOf(mediaProjectionInfo3);
            logBuffer.commit(logMessageObtain);
            castControllerImpl.fireOnCastDevicesChanged();
        }
    }

    /* renamed from: -$$Nest$mupdateRemoteDisplays, reason: not valid java name */
    public static void m3099$$Nest$mupdateRemoteDisplays(CastControllerImpl castControllerImpl) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.policy.CastControllerImpl$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.media.projection.MediaProjectionManager$Callback, com.android.systemui.statusbar.policy.CastControllerImpl$2] */
    public CastControllerImpl(Context context, PackageManager packageManager, DumpManager dumpManager, CastControllerLogger castControllerLogger) {
        ?? r0 = new MediaProjectionManager.Callback() { // from class: com.android.systemui.statusbar.policy.CastControllerImpl.2
            public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
                CastControllerImpl.m3098$$Nest$msetProjection(CastControllerImpl.this, mediaProjectionInfo, true);
            }

            public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
                CastControllerImpl.m3098$$Nest$msetProjection(CastControllerImpl.this, mediaProjectionInfo, false);
            }
        };
        this.mProjectionCallback = r0;
        this.mContext = context;
        this.mPackageManager = packageManager;
        this.mLogger = castControllerLogger;
        MediaRouter mediaRouter = (MediaRouter) context.getSystemService("media_router");
        this.mMediaRouter = mediaRouter;
        mediaRouter.setRouterGroupId("android.media.mirroring_group");
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) context.getSystemService("media_projection");
        this.mProjectionManager = mediaProjectionManager;
        this.mProjection = mediaProjectionManager.getActiveProjectionInfo();
        mediaProjectionManager.addCallback(r0, new Handler());
        dumpManager.registerNormalDumpable("CastController", this);
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
        printWriter.println(false);
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
            CastControllerLogger.Companion.getClass();
            printWriter.println(CastControllerLogger.Companion.toLogString(routeInfo));
        }
        printWriter.print("  mProjection=");
        printWriter.println(this.mProjection);
    }

    public void fireOnCastDevicesChanged() {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = new ArrayList(this.mCallbacks);
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((CastController.Callback) obj).onCastDevicesChanged();
        }
    }

    public final List getCastDevices() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRoutes) {
            try {
                for (MediaRouter.RouteInfo routeInfo : this.mRoutes.values()) {
                    CastDevice.Companion companion = CastDevice.Companion;
                    Context context = this.mContext;
                    companion.getClass();
                    arrayList.add(CastDevice.Companion.toCastDevice(routeInfo, context));
                }
            } finally {
            }
        }
        synchronized (this.mProjectionLock) {
            try {
                MediaProjectionInfo mediaProjectionInfo = this.mProjection;
                if (mediaProjectionInfo != null) {
                    CastDevice.Companion companion2 = CastDevice.Companion;
                    Context context2 = this.mContext;
                    PackageManager packageManager = this.mPackageManager;
                    CastControllerLogger castControllerLogger = this.mLogger;
                    companion2.getClass();
                    arrayList.add(CastDevice.Companion.toCastDevice(mediaProjectionInfo, context2, packageManager, castControllerLogger));
                }
            } finally {
            }
        }
        return arrayList;
    }

    public final void handleDiscoveryChangeLocked() {
        boolean zIsEmpty;
        if (this.mCallbackRegistered) {
            this.mMediaRouter.removeCallback(this.mMediaCallback);
            this.mCallbackRegistered = false;
        }
        synchronized (this.mCallbacks) {
            zIsEmpty = this.mCallbacks.isEmpty();
        }
        if (zIsEmpty) {
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

    public final void stopCasting(CastDevice castDevice, int i) {
        boolean z = castDevice.tag instanceof MediaProjectionInfo;
        CastControllerLogger castControllerLogger = this.mLogger;
        castControllerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        CastControllerLogger$$ExternalSyntheticLambda0 castControllerLogger$$ExternalSyntheticLambda0 = new CastControllerLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = castControllerLogger.logger;
        LogMessage logMessageObtain = logBuffer.obtain("CastController", logLevel, castControllerLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        if (!z) {
            logBuffer.commit(logBuffer.obtain("CastController", logLevel, new CastControllerLogger$$ExternalSyntheticLambda0(2), null));
            this.mMediaRouter.getFallbackRoute().select();
            return;
        }
        MediaProjectionInfo mediaProjectionInfo = (MediaProjectionInfo) castDevice.tag;
        if (Objects.equals(this.mProjectionManager.getActiveProjectionInfo(), mediaProjectionInfo)) {
            this.mProjectionManager.stopActiveProjection(i);
            return;
        }
        LogMessage logMessageObtain2 = logBuffer.obtain("CastController", LogLevel.WARNING, new CastControllerLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) logMessageObtain2).str1 = mediaProjectionInfo.toString();
        logBuffer.commit(logMessageObtain2);
    }
}
