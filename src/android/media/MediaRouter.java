package android.media;

import android.Manifest;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.IAudioRoutesObserver;
import android.media.IAudioService;
import android.media.IMediaRouterClient;
import android.media.IMediaRouterService;
import android.media.IRemoteVolumeObserver;
import android.media.MediaRouter;
import android.media.MediaRouterClientState;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.DisplayAddress;
import com.android.internal.R;
import com.android.server.display.feature.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class MediaRouter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int CALLBACK_FLAG_PASSIVE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    private static final boolean DEBUG_RESTORE_ROUTE = true;
    public static final String MIRRORING_GROUP_ID = "android.media.mirroring_group";
    static final int ROUTE_TYPE_ANY = 8388615;
    public static final int ROUTE_TYPE_LIVE_AUDIO = 1;
    public static final int ROUTE_TYPE_LIVE_VIDEO = 2;
    public static final int ROUTE_TYPE_REMOTE_DISPLAY = 4;
    public static final int ROUTE_TYPE_USER = 8388608;
    static Static sStatic;
    private static final String TAG = "MediaRouter";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final HashMap<Context, MediaRouter> sRouters = new HashMap<>();

    public static abstract class Callback {
        public abstract void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo);

        public abstract void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo);

        public abstract void onRouteGrouped(MediaRouter mediaRouter, RouteInfo routeInfo, RouteGroup routeGroup, int i);

        public void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        public abstract void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo);

        public abstract void onRouteSelected(MediaRouter mediaRouter, int i, RouteInfo routeInfo);

        public abstract void onRouteUngrouped(MediaRouter mediaRouter, RouteInfo routeInfo, RouteGroup routeGroup);

        public abstract void onRouteUnselected(MediaRouter mediaRouter, int i, RouteInfo routeInfo);

        public abstract void onRouteVolumeChanged(MediaRouter mediaRouter, RouteInfo routeInfo);
    }

    public static class SimpleCallback extends Callback {
        @Override // android.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteGrouped(MediaRouter mediaRouter, RouteInfo routeInfo, RouteGroup routeGroup, int i) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteSelected(MediaRouter mediaRouter, int i, RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUngrouped(MediaRouter mediaRouter, RouteInfo routeInfo, RouteGroup routeGroup) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUnselected(MediaRouter mediaRouter, int i, RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteVolumeChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        }
    }

    public static abstract class VolumeCallback {
        public abstract void onVolumeSetRequest(RouteInfo routeInfo, int i);

        public abstract void onVolumeUpdateRequest(RouteInfo routeInfo, int i);
    }

    static class Static implements DisplayManager.DisplayListener {
        boolean mActivelyScanningWifiDisplays;
        RouteInfo mBluetoothA2dpRoute;
        final boolean mCanConfigureWifiDisplays;
        IMediaRouterClient mClient;
        MediaRouterClientState mClientState;
        RouteInfo mDefaultAudioVideo;
        boolean mDiscoverRequestActiveScan;
        int mDiscoveryRequestRouteTypes;
        final DisplayManager mDisplayService;
        final Handler mHandler;
        boolean mIsBluetoothA2dpOn;
        final String mPackageName;
        String mPreviousActiveWifiDisplayAddress;
        final Resources mResources;
        RouteInfo mSelectedRoute;
        final RouteCategory mSystemCategory;
        final CopyOnWriteArrayList<CallbackInfo> mCallbacks = new CopyOnWriteArrayList<>();
        final ArrayList<RouteInfo> mRoutes = new ArrayList<>();
        final ArrayList<RouteCategory> mCategories = new ArrayList<>();
        final AudioRoutesInfo mCurAudioRoutesInfo = new AudioRoutesInfo();
        int mCurrentUserId = -1;
        SparseIntArray mStreamVolume = new SparseIntArray();
        final IAudioRoutesObserver.Stub mAudioRoutesObserver = new IAudioRoutesObserver.Stub() { // from class: android.media.MediaRouter.Static.1
            @Override // android.media.IAudioRoutesObserver
            public void dispatchAudioRoutesChanged(final AudioRoutesInfo audioRoutesInfo) {
                try {
                    Static r0 = Static.this;
                    r0.mIsBluetoothA2dpOn = r0.mAudioService.isBluetoothA2dpOn();
                } catch (RemoteException e) {
                    Log.e(MediaRouter.TAG, "Error querying Bluetooth A2DP state", e);
                }
                Static.this.mHandler.post(new Runnable() { // from class: android.media.MediaRouter.Static.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Static.this.updateAudioRoutes(audioRoutesInfo);
                    }
                });
            }
        };
        final IAudioService mAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        final IMediaRouterService mMediaRouterService = IMediaRouterService.Stub.asInterface(ServiceManager.getService(Context.MEDIA_ROUTER_SERVICE));

        Static(Context context) {
            this.mPackageName = context.getPackageName();
            this.mResources = context.getResources();
            this.mHandler = new Handler(context.getMainLooper());
            this.mDisplayService = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            RouteCategory routeCategory = new RouteCategory(R.string.default_audio_route_category_name, 3, false);
            this.mSystemCategory = routeCategory;
            routeCategory.mIsSystem = true;
            this.mCanConfigureWifiDisplays = context.checkPermission(Manifest.permission.CONFIGURE_WIFI_DISPLAY, Process.myPid(), Process.myUid()) == 0;
        }

        void startMonitoringRoutes(Context context) {
            AudioRoutesInfo audioRoutesInfo;
            RouteInfo routeInfo = new RouteInfo(this.mSystemCategory);
            this.mDefaultAudioVideo = routeInfo;
            routeInfo.mNameResId = R.string.default_audio_route_name;
            this.mDefaultAudioVideo.mSupportedTypes = 3;
            this.mDefaultAudioVideo.updatePresentationDisplay();
            if (((AudioManager) context.getSystemService("audio")).isVolumeFixed()) {
                this.mDefaultAudioVideo.mVolumeHandling = 0;
            }
            this.mDefaultAudioVideo.mGlobalRouteId = MediaRouter.sStatic.mResources.getString(R.string.default_audio_route_id);
            MediaRouter.addRouteStatic(this.mDefaultAudioVideo);
            MediaRouter.updateWifiDisplayStatus(this.mDisplayService.getWifiDisplayStatus());
            context.registerReceiver(new WifiDisplayStatusChangedReceiver(), new IntentFilter("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED"));
            context.registerReceiver(new VolumeChangeReceiver(), new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
            if (Flags.displayListenerPerformanceImprovements() && Flags.delayImplicitRrRegistrationUntilRrAccessed()) {
                this.mDisplayService.registerDisplayListener(this, this.mHandler, 7L);
            } else {
                this.mDisplayService.registerDisplayListener(this, this.mHandler);
            }
            try {
                this.mIsBluetoothA2dpOn = this.mAudioService.isBluetoothA2dpOn();
                audioRoutesInfo = this.mAudioService.startWatchingRoutes(this.mAudioRoutesObserver);
            } catch (RemoteException unused) {
                audioRoutesInfo = null;
            }
            if (audioRoutesInfo != null) {
                updateAudioRoutes(audioRoutesInfo);
            }
            rebindAsUser(UserHandle.myUserId());
            if (this.mSelectedRoute == null) {
                MediaRouter.selectDefaultRouteStatic();
            }
        }

        void updateAudioRoutes(AudioRoutesInfo audioRoutesInfo) {
            boolean z;
            boolean z2;
            RouteInfo routeInfo;
            int i;
            if (audioRoutesInfo.mainType != this.mCurAudioRoutesInfo.mainType) {
                this.mCurAudioRoutesInfo.mainType = audioRoutesInfo.mainType;
                if ((audioRoutesInfo.mainType & 2) != 0 || (audioRoutesInfo.mainType & 1) != 0) {
                    i = R.string.default_audio_route_name_headphones;
                } else if ((audioRoutesInfo.mainType & 4) != 0) {
                    i = R.string.default_audio_route_name_dock_speakers;
                } else if ((audioRoutesInfo.mainType & 8) != 0) {
                    i = R.string.default_audio_route_name_external_device;
                } else {
                    i = (audioRoutesInfo.mainType & 16) != 0 ? R.string.default_audio_route_name_usb : R.string.default_audio_route_name;
                }
                this.mDefaultAudioVideo.mNameResId = i;
                MediaRouter.dispatchRouteChanged(this.mDefaultAudioVideo);
                z = (audioRoutesInfo.mainType & 19) != 0;
                z2 = true;
            } else {
                z = false;
                z2 = false;
            }
            if (!TextUtils.equals(audioRoutesInfo.bluetoothName, this.mCurAudioRoutesInfo.bluetoothName)) {
                if (audioRoutesInfo.bluetoothName != null) {
                    RouteInfo routeInfo2 = this.mBluetoothA2dpRoute;
                    if (routeInfo2 == null) {
                        RouteInfo routeInfo3 = new RouteInfo(this.mSystemCategory);
                        routeInfo3.mName = audioRoutesInfo.bluetoothName;
                        routeInfo3.mDescription = this.mResources.getText(R.string.bluetooth_a2dp_audio_route_name);
                        routeInfo3.mSupportedTypes = 1;
                        routeInfo3.mDeviceType = 3;
                        routeInfo3.mGlobalRouteId = MediaRouter.sStatic.mResources.getString(R.string.bluetooth_a2dp_audio_route_id);
                        this.mBluetoothA2dpRoute = routeInfo3;
                        MediaRouter.addRouteStatic(routeInfo3);
                    } else {
                        routeInfo2.mName = audioRoutesInfo.bluetoothName;
                        MediaRouter.dispatchRouteChanged(this.mBluetoothA2dpRoute);
                    }
                } else {
                    RouteInfo routeInfo4 = this.mBluetoothA2dpRoute;
                    if (routeInfo4 != null) {
                        this.mBluetoothA2dpRoute = null;
                        MediaRouter.removeRouteStatic(routeInfo4);
                    }
                }
                z2 = true;
                z = false;
            } else if (TextUtils.equals(audioRoutesInfo.bluetoothName, this.mCurAudioRoutesInfo.bluetoothName) && this.mSelectedRoute != this.mBluetoothA2dpRoute && isBluetoothA2dpOn()) {
                Log.i(MediaRouter.TAG, "force audioRouteschanged true ");
                z2 = true;
            }
            if (z2) {
                Log.v(MediaRouter.TAG, "Audio routes updated: " + audioRoutesInfo + ", a2dp=" + isBluetoothA2dpOn());
                RouteInfo routeInfo5 = this.mSelectedRoute;
                if (routeInfo5 == null || routeInfo5.isDefault() || this.mSelectedRoute.isBluetooth()) {
                    if (z || (routeInfo = this.mBluetoothA2dpRoute) == null) {
                        MediaRouter.selectRouteStatic(1, this.mDefaultAudioVideo, false);
                    } else {
                        MediaRouter.selectRouteStatic(1, routeInfo, false);
                    }
                }
            }
            this.mCurAudioRoutesInfo.bluetoothName = audioRoutesInfo.bluetoothName;
        }

        int getStreamVolume(int i) {
            int indexOfKey = this.mStreamVolume.indexOfKey(i);
            if (indexOfKey < 0) {
                int i2 = 0;
                try {
                    try {
                        i2 = this.mAudioService.getStreamVolume(i);
                        this.mStreamVolume.put(i, i2);
                        return i2;
                    } catch (RemoteException e) {
                        Log.e(MediaRouter.TAG, "Error getting local stream volume", e);
                        return i2;
                    }
                } catch (Throwable unused) {
                    return i2;
                }
            }
            return this.mStreamVolume.valueAt(indexOfKey);
        }

        boolean isBluetoothA2dpOn() {
            return this.mBluetoothA2dpRoute != null && this.mIsBluetoothA2dpOn;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x003d A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void updateDiscoveryRequest() {
            /*
                r11 = this;
                java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter$CallbackInfo> r0 = r11.mCallbacks
                int r0 = r0.size()
                r1 = 0
                r2 = r1
                r3 = r2
                r4 = r3
                r5 = r4
                r6 = r5
            Lc:
                r7 = 4
                r8 = 1
                if (r2 >= r0) goto L40
                java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter$CallbackInfo> r9 = r11.mCallbacks
                java.lang.Object r9 = r9.get(r2)
                android.media.MediaRouter$CallbackInfo r9 = (android.media.MediaRouter.CallbackInfo) r9
                int r10 = r9.flags
                r10 = r10 & 5
                if (r10 == 0) goto L22
                int r10 = r9.type
            L20:
                r3 = r3 | r10
                goto L2f
            L22:
                int r10 = r9.flags
                r10 = r10 & 8
                if (r10 == 0) goto L2c
                int r10 = r9.type
                r5 = r5 | r10
                goto L2f
            L2c:
                int r10 = r9.type
                goto L20
            L2f:
                int r10 = r9.flags
                r10 = r10 & r8
                if (r10 == 0) goto L3d
                int r4 = r9.type
                r4 = r4 & r7
                if (r4 == 0) goto L3c
                r4 = r8
                r6 = r4
                goto L3d
            L3c:
                r4 = r8
            L3d:
                int r2 = r2 + 1
                goto Lc
            L40:
                if (r3 != 0) goto L44
                if (r4 == 0) goto L45
            L44:
                r3 = r3 | r5
            L45:
                boolean r0 = r11.mCanConfigureWifiDisplays
                if (r0 == 0) goto L6d
                android.media.MediaRouter$RouteInfo r0 = r11.mSelectedRoute
                if (r0 == 0) goto L54
                boolean r0 = r0.matchesTypes(r7)
                if (r0 == 0) goto L54
                r6 = r1
            L54:
                if (r6 == 0) goto L62
                boolean r0 = r11.mActivelyScanningWifiDisplays
                if (r0 != 0) goto L6d
                r11.mActivelyScanningWifiDisplays = r8
                android.hardware.display.DisplayManager r0 = r11.mDisplayService
                r0.startWifiDisplayScan()
                goto L6d
            L62:
                boolean r0 = r11.mActivelyScanningWifiDisplays
                if (r0 == 0) goto L6d
                r11.mActivelyScanningWifiDisplays = r1
                android.hardware.display.DisplayManager r0 = r11.mDisplayService
                r0.stopWifiDisplayScan()
            L6d:
                int r0 = r11.mDiscoveryRequestRouteTypes
                if (r3 != r0) goto L77
                boolean r0 = r11.mDiscoverRequestActiveScan
                if (r4 == r0) goto L76
                goto L77
            L76:
                return
            L77:
                r11.mDiscoveryRequestRouteTypes = r3
                r11.mDiscoverRequestActiveScan = r4
                r11.publishClientDiscoveryRequest()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaRouter.Static.updateDiscoveryRequest():void");
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            updatePresentationDisplays(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            updatePresentationDisplays(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            updatePresentationDisplays(i);
        }

        public void setRouterGroupId(String str) {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    this.mMediaRouterService.registerClientGroupId(iMediaRouterClient, str);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        public Display[] getAllPresentationDisplays() {
            try {
                return this.mDisplayService.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            } catch (RuntimeException e) {
                Log.e(MediaRouter.TAG, "Unable to get displays.", e);
                return null;
            }
        }

        private void updatePresentationDisplays(int i) {
            int size = this.mRoutes.size();
            for (int i2 = 0; i2 < size; i2++) {
                RouteInfo routeInfo = this.mRoutes.get(i2);
                if (routeInfo.updatePresentationDisplay() || (routeInfo.mPresentationDisplay != null && routeInfo.mPresentationDisplay.getDisplayId() == i)) {
                    MediaRouter.dispatchRoutePresentationDisplayChanged(routeInfo);
                }
            }
        }

        void handleGroupRouteSelected(String str) {
            RouteInfo routeInfo = isBluetoothA2dpOn() ? this.mBluetoothA2dpRoute : this.mDefaultAudioVideo;
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                RouteInfo routeInfo2 = this.mRoutes.get(i);
                if (TextUtils.equals(routeInfo2.mGlobalRouteId, str)) {
                    routeInfo = routeInfo2;
                }
            }
            if (routeInfo != this.mSelectedRoute) {
                MediaRouter.selectRouteStatic(routeInfo.mSupportedTypes, routeInfo, false);
            }
        }

        void setSelectedRoute(RouteInfo routeInfo, boolean z) {
            this.mSelectedRoute = routeInfo;
            publishClientSelectedRoute(z);
        }

        void rebindAsUser(int i) {
            if (this.mCurrentUserId != i || i < 0 || this.mClient == null) {
                IMediaRouterClient iMediaRouterClient = this.mClient;
                if (iMediaRouterClient != null) {
                    try {
                        this.mMediaRouterService.unregisterClient(iMediaRouterClient);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                    this.mClient = null;
                }
                this.mCurrentUserId = i;
                try {
                    Client client = new Client();
                    this.mMediaRouterService.registerClientAsUser(client, this.mPackageName, i);
                    this.mClient = client;
                } catch (RemoteException e2) {
                    Log.e(MediaRouter.TAG, "Unable to register media router client.", e2);
                }
                publishClientDiscoveryRequest();
                publishClientSelectedRoute(false);
                updateClientState();
            }
        }

        void publishClientDiscoveryRequest() {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    this.mMediaRouterService.setDiscoveryRequest(iMediaRouterClient, this.mDiscoveryRequestRouteTypes, this.mDiscoverRequestActiveScan);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        void publishClientSelectedRoute(boolean z) {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    IMediaRouterService iMediaRouterService = this.mMediaRouterService;
                    RouteInfo routeInfo = this.mSelectedRoute;
                    iMediaRouterService.setSelectedRoute(iMediaRouterClient, routeInfo != null ? routeInfo.mGlobalRouteId : null, z);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        void updateClientState() {
            this.mClientState = null;
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient != null) {
                try {
                    this.mClientState = this.mMediaRouterService.getState(iMediaRouterClient);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            MediaRouterClientState mediaRouterClientState = this.mClientState;
            ArrayList<MediaRouterClientState.RouteInfo> arrayList = mediaRouterClientState != null ? mediaRouterClientState.routes : null;
            int size = arrayList != null ? arrayList.size() : 0;
            for (int i = 0; i < size; i++) {
                MediaRouterClientState.RouteInfo routeInfo = arrayList.get(i);
                RouteInfo findGlobalRoute = findGlobalRoute(routeInfo.id);
                if (findGlobalRoute == null) {
                    MediaRouter.addRouteStatic(makeGlobalRoute(routeInfo));
                } else {
                    updateGlobalRoute(findGlobalRoute, routeInfo);
                }
            }
            int size2 = this.mRoutes.size();
            while (true) {
                int i2 = size2 - 1;
                if (size2 <= 0) {
                    return;
                }
                RouteInfo routeInfo2 = this.mRoutes.get(i2);
                String str = routeInfo2.mGlobalRouteId;
                if (!routeInfo2.isDefault() && !routeInfo2.isBluetooth() && str != null) {
                    int i3 = 0;
                    while (true) {
                        if (i3 < size) {
                            if (str.equals(arrayList.get(i3).id)) {
                                break;
                            } else {
                                i3++;
                            }
                        } else {
                            MediaRouter.removeRouteStatic(routeInfo2);
                            break;
                        }
                    }
                }
                size2 = i2;
            }
        }

        void requestSetVolume(RouteInfo routeInfo, int i) {
            IMediaRouterClient iMediaRouterClient;
            if (routeInfo.mGlobalRouteId == null || (iMediaRouterClient = this.mClient) == null) {
                return;
            }
            try {
                this.mMediaRouterService.requestSetVolume(iMediaRouterClient, routeInfo.mGlobalRouteId, i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        void requestUpdateVolume(RouteInfo routeInfo, int i) {
            IMediaRouterClient iMediaRouterClient;
            if (routeInfo.mGlobalRouteId == null || (iMediaRouterClient = this.mClient) == null) {
                return;
            }
            try {
                this.mMediaRouterService.requestUpdateVolume(iMediaRouterClient, routeInfo.mGlobalRouteId, i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        RouteInfo makeGlobalRoute(MediaRouterClientState.RouteInfo routeInfo) {
            RouteInfo routeInfo2 = new RouteInfo(this.mSystemCategory);
            routeInfo2.mGlobalRouteId = routeInfo.id;
            routeInfo2.mName = routeInfo.name;
            routeInfo2.mDescription = routeInfo.description;
            routeInfo2.mSupportedTypes = routeInfo.supportedTypes;
            routeInfo2.mDeviceType = routeInfo.deviceType;
            routeInfo2.mEnabled = routeInfo.enabled;
            routeInfo2.setRealStatusCode(routeInfo.statusCode);
            routeInfo2.mPlaybackType = routeInfo.playbackType;
            routeInfo2.mPlaybackStream = routeInfo.playbackStream;
            routeInfo2.mVolume = routeInfo.volume;
            routeInfo2.mVolumeMax = routeInfo.volumeMax;
            routeInfo2.mVolumeHandling = routeInfo.volumeHandling;
            routeInfo2.mPresentationDisplayId = routeInfo.presentationDisplayId;
            routeInfo2.updatePresentationDisplay();
            return routeInfo2;
        }

        void updateGlobalRoute(RouteInfo routeInfo, MediaRouterClientState.RouteInfo routeInfo2) {
            boolean z;
            boolean z2;
            boolean z3 = true;
            boolean z4 = false;
            if (Objects.equals(routeInfo.mName, routeInfo2.name)) {
                z = false;
            } else {
                routeInfo.mName = routeInfo2.name;
                z = true;
            }
            if (!Objects.equals(routeInfo.mDescription, routeInfo2.description)) {
                routeInfo.mDescription = routeInfo2.description;
                z = true;
            }
            int i = routeInfo.mSupportedTypes;
            if (i != routeInfo2.supportedTypes) {
                routeInfo.mSupportedTypes = routeInfo2.supportedTypes;
                z = true;
            }
            if (routeInfo.mEnabled != routeInfo2.enabled) {
                routeInfo.mEnabled = routeInfo2.enabled;
                z = true;
            }
            if (routeInfo.mRealStatusCode != routeInfo2.statusCode) {
                routeInfo.setRealStatusCode(routeInfo2.statusCode);
                z = true;
            }
            if (routeInfo.mPlaybackType != routeInfo2.playbackType) {
                routeInfo.mPlaybackType = routeInfo2.playbackType;
                z = true;
            }
            if (routeInfo.mPlaybackStream != routeInfo2.playbackStream) {
                routeInfo.mPlaybackStream = routeInfo2.playbackStream;
                z = true;
            }
            if (routeInfo.mVolume != routeInfo2.volume) {
                routeInfo.mVolume = routeInfo2.volume;
                z = true;
                z2 = true;
            } else {
                z2 = false;
            }
            if (routeInfo.mVolumeMax != routeInfo2.volumeMax) {
                routeInfo.mVolumeMax = routeInfo2.volumeMax;
                z = true;
                z2 = true;
            }
            if (routeInfo.mVolumeHandling != routeInfo2.volumeHandling) {
                routeInfo.mVolumeHandling = routeInfo2.volumeHandling;
                z = true;
                z2 = true;
            }
            if (routeInfo.mPresentationDisplayId != routeInfo2.presentationDisplayId) {
                routeInfo.mPresentationDisplayId = routeInfo2.presentationDisplayId;
                routeInfo.updatePresentationDisplay();
                z4 = true;
            } else {
                z3 = z;
            }
            if (z3) {
                MediaRouter.dispatchRouteChanged(routeInfo, i);
            }
            if (z2) {
                MediaRouter.dispatchRouteVolumeChanged(routeInfo);
            }
            if (z4) {
                MediaRouter.dispatchRoutePresentationDisplayChanged(routeInfo);
            }
        }

        RouteInfo findGlobalRoute(String str) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                RouteInfo routeInfo = this.mRoutes.get(i);
                if (str.equals(routeInfo.mGlobalRouteId)) {
                    return routeInfo;
                }
            }
            return null;
        }

        boolean isPlaybackActive() {
            IMediaRouterClient iMediaRouterClient = this.mClient;
            if (iMediaRouterClient == null) {
                return false;
            }
            try {
                return this.mMediaRouterService.isPlaybackActive(iMediaRouterClient);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return false;
            }
        }

        final class Client extends IMediaRouterClient.Stub {
            Client() {
            }

            @Override // android.media.IMediaRouterClient
            public void onStateChanged() {
                Static.this.mHandler.post(new Runnable() { // from class: android.media.MediaRouter.Static.Client.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Client client = Client.this;
                        if (client == Static.this.mClient) {
                            Static.this.updateClientState();
                        }
                    }
                });
            }

            @Override // android.media.IMediaRouterClient
            public void onRestoreRoute() {
                Static.this.mHandler.post(new Runnable() { // from class: android.media.MediaRouter$Static$Client$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaRouter.Static.Client.this.lambda$onRestoreRoute$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRestoreRoute$0() {
                if (this != Static.this.mClient || Static.this.mSelectedRoute == null) {
                    return;
                }
                if (Static.this.mSelectedRoute.isDefault() || Static.this.mSelectedRoute.isBluetooth()) {
                    if (Static.this.mSelectedRoute.isDefault() && Static.this.mBluetoothA2dpRoute != null) {
                        Log.d(MediaRouter.TAG, "onRestoreRoute() : selectedRoute=" + Static.this.mSelectedRoute + ", a2dpRoute=" + Static.this.mBluetoothA2dpRoute);
                    } else {
                        Log.d(MediaRouter.TAG, "onRestoreRoute() : route=" + Static.this.mSelectedRoute);
                    }
                    Static.this.mSelectedRoute.select();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onGroupRouteSelected(final String str) {
                Static.this.mHandler.post(new Runnable() { // from class: android.media.MediaRouter$Static$Client$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaRouter.Static.Client.this.lambda$onGroupRouteSelected$1(str);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGroupRouteSelected$1(String str) {
                if (this == Static.this.mClient) {
                    Static.this.handleGroupRouteSelected(str);
                }
            }
        }
    }

    static String typesToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("ROUTE_TYPE_LIVE_AUDIO ");
        }
        if ((i & 2) != 0) {
            sb.append("ROUTE_TYPE_LIVE_VIDEO ");
        }
        if ((i & 4) != 0) {
            sb.append("ROUTE_TYPE_REMOTE_DISPLAY ");
        }
        if ((i & 8388608) != 0) {
            sb.append("ROUTE_TYPE_USER ");
        }
        return sb.toString();
    }

    public MediaRouter(Context context) {
        synchronized (Static.class) {
            if (sStatic == null) {
                Context applicationContext = context.getApplicationContext();
                Static r0 = new Static(applicationContext);
                sStatic = r0;
                r0.startMonitoringRoutes(applicationContext);
            }
        }
    }

    public RouteInfo getDefaultRoute() {
        return sStatic.mDefaultAudioVideo;
    }

    public RouteInfo getFallbackRoute() {
        return sStatic.mBluetoothA2dpRoute != null ? sStatic.mBluetoothA2dpRoute : sStatic.mDefaultAudioVideo;
    }

    public RouteCategory getSystemCategory() {
        return sStatic.mSystemCategory;
    }

    public RouteInfo getSelectedRoute() {
        return getSelectedRoute(8388615);
    }

    public RouteInfo getSelectedRoute(int i) {
        if (sStatic.mSelectedRoute != null && (sStatic.mSelectedRoute.mSupportedTypes & i) != 0) {
            return sStatic.mSelectedRoute;
        }
        if (i == 8388608) {
            return null;
        }
        return sStatic.mDefaultAudioVideo;
    }

    public boolean isRouteAvailable(int i, int i2) {
        int size = sStatic.mRoutes.size();
        for (int i3 = 0; i3 < size; i3++) {
            RouteInfo routeInfo = sStatic.mRoutes.get(i3);
            if (routeInfo.matchesTypes(i) && ((i2 & 1) == 0 || routeInfo != sStatic.mDefaultAudioVideo)) {
                return true;
            }
        }
        return false;
    }

    public void setRouterGroupId(String str) {
        sStatic.setRouterGroupId(str);
    }

    public void addCallback(int i, Callback callback) {
        addCallback(i, callback, 0);
    }

    public void addCallback(int i, Callback callback, int i2) {
        int findCallbackInfo = findCallbackInfo(callback);
        if (findCallbackInfo >= 0) {
            CallbackInfo callbackInfo = sStatic.mCallbacks.get(findCallbackInfo);
            callbackInfo.type = i | callbackInfo.type;
            callbackInfo.flags |= i2;
        } else {
            sStatic.mCallbacks.add(new CallbackInfo(callback, i, i2, this));
        }
        sStatic.updateDiscoveryRequest();
    }

    public void removeCallback(Callback callback) {
        int findCallbackInfo = findCallbackInfo(callback);
        if (findCallbackInfo >= 0) {
            sStatic.mCallbacks.remove(findCallbackInfo);
            sStatic.updateDiscoveryRequest();
        } else {
            Log.w(TAG, "removeCallback(" + callback + "): callback not registered");
        }
    }

    private int findCallbackInfo(Callback callback) {
        int size = sStatic.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (sStatic.mCallbacks.get(i).cb == callback) {
                return i;
            }
        }
        return -1;
    }

    public void selectRoute(int i, RouteInfo routeInfo) {
        if (routeInfo == null) {
            throw new IllegalArgumentException("Route cannot be null.");
        }
        selectRouteStatic(i, routeInfo, true);
    }

    public void selectRouteInt(int i, RouteInfo routeInfo, boolean z) {
        selectRouteStatic(i, routeInfo, z);
    }

    static void selectRouteStatic(int i, RouteInfo routeInfo, boolean z) {
        Log.v(TAG, "Selecting route: " + routeInfo);
        RouteInfo routeInfo2 = sStatic.mSelectedRoute;
        RouteInfo routeInfo3 = sStatic.isBluetoothA2dpOn() ? sStatic.mBluetoothA2dpRoute : sStatic.mDefaultAudioVideo;
        boolean z2 = routeInfo2 != null && (routeInfo2.isDefault() || routeInfo2.isBluetooth());
        if (routeInfo2 != routeInfo || (z2 && routeInfo != routeInfo3)) {
            if (!routeInfo.matchesTypes(i)) {
                Log.w(TAG, "selectRoute ignored; cannot select route with supported types " + typesToString(routeInfo.getSupportedTypes()) + " into route types " + typesToString(i));
                return;
            }
            if (sStatic.isPlaybackActive() && sStatic.mBluetoothA2dpRoute != null && (i & 1) != 0 && (routeInfo.isBluetooth() || routeInfo.isDefault())) {
                try {
                    sStatic.mMediaRouterService.setBluetoothA2dpOn(sStatic.mClient, routeInfo.isBluetooth());
                } catch (RemoteException e) {
                    Log.e(TAG, "Error changing Bluetooth A2DP state", e);
                }
            } else {
                Log.i(TAG, "Skip setBluetoothA2dpOn(): types=" + i + ", isPlaybackActive()=" + sStatic.isPlaybackActive() + ", BT route=" + sStatic.mBluetoothA2dpRoute);
            }
            WifiDisplay activeDisplay = sStatic.mDisplayService.getWifiDisplayStatus().getActiveDisplay();
            boolean z3 = (routeInfo2 == null || routeInfo2.mDeviceAddress == null) ? false : true;
            boolean z4 = routeInfo.mDeviceAddress != null;
            if (activeDisplay != null || z3 || z4) {
                if (z4 && !matchesDeviceAddress(activeDisplay, routeInfo)) {
                    if (sStatic.mCanConfigureWifiDisplays) {
                        sStatic.mDisplayService.connectWifiDisplay(routeInfo.mDeviceAddress);
                    } else {
                        Log.e(TAG, "Cannot connect to wifi displays because this process is not allowed to do so.");
                    }
                } else if (activeDisplay != null && !z4 && z3 && activeDisplay.getDeviceAddress().equals(routeInfo2.mDeviceAddress) && "0".equals(SystemProperties.get("secmm.wfd.demo", "0"))) {
                    sStatic.mDisplayService.disconnectWifiDisplay();
                }
            }
            sStatic.setSelectedRoute(routeInfo, z);
            if (routeInfo2 != null) {
                dispatchRouteUnselected(routeInfo2.getSupportedTypes() & i, routeInfo2);
                if (routeInfo2.resolveStatusCode()) {
                    dispatchRouteChanged(routeInfo2);
                }
            }
            if (routeInfo != null) {
                if (routeInfo.resolveStatusCode()) {
                    dispatchRouteChanged(routeInfo);
                }
                dispatchRouteSelected(i & routeInfo.getSupportedTypes(), routeInfo);
            }
            sStatic.updateDiscoveryRequest();
        }
    }

    static void selectDefaultRouteStatic() {
        if (sStatic.mSelectedRoute != sStatic.mBluetoothA2dpRoute && sStatic.mBluetoothA2dpRoute != null && isAudioPathA2DPStatic()) {
            selectRouteStatic(8388615, sStatic.mBluetoothA2dpRoute, false);
        } else {
            selectRouteStatic(8388615, sStatic.mDefaultAudioVideo, false);
        }
    }

    static boolean matchesDeviceAddress(WifiDisplay wifiDisplay, RouteInfo routeInfo) {
        boolean z = (routeInfo == null || routeInfo.mDeviceAddress == null) ? false : true;
        if (wifiDisplay == null && !z) {
            return true;
        }
        if (wifiDisplay == null || !z) {
            return false;
        }
        return wifiDisplay.getDeviceAddress().equals(routeInfo.mDeviceAddress);
    }

    public void addUserRoute(UserRouteInfo userRouteInfo) {
        addRouteStatic(userRouteInfo);
    }

    public void addRouteInt(RouteInfo routeInfo) {
        addRouteStatic(routeInfo);
    }

    static void addRouteStatic(RouteInfo routeInfo) {
        if (DEBUG) {
            Log.d(TAG, "Adding route: " + routeInfo);
        }
        RouteCategory category = routeInfo.getCategory();
        if (!sStatic.mCategories.contains(category)) {
            sStatic.mCategories.add(category);
        }
        if (category.isGroupable() && !(routeInfo instanceof RouteGroup)) {
            RouteGroup routeGroup = new RouteGroup(routeInfo.getCategory());
            routeGroup.mSupportedTypes = routeInfo.mSupportedTypes;
            sStatic.mRoutes.add(routeGroup);
            dispatchRouteAdded(routeGroup);
            routeGroup.addRoute(routeInfo);
            return;
        }
        sStatic.mRoutes.add(routeInfo);
        dispatchRouteAdded(routeInfo);
    }

    public void removeUserRoute(UserRouteInfo userRouteInfo) {
        removeRouteStatic(userRouteInfo);
    }

    public void clearUserRoutes() {
        int i = 0;
        while (i < sStatic.mRoutes.size()) {
            RouteInfo routeInfo = sStatic.mRoutes.get(i);
            if ((routeInfo instanceof UserRouteInfo) || (routeInfo instanceof RouteGroup)) {
                removeRouteStatic(routeInfo);
                i--;
            }
            i++;
        }
    }

    public void removeRouteInt(RouteInfo routeInfo) {
        removeRouteStatic(routeInfo);
    }

    static void removeRouteStatic(RouteInfo routeInfo) {
        if (DEBUG) {
            Log.d(TAG, "Removing route: " + routeInfo);
        }
        if (sStatic.mRoutes.remove(routeInfo)) {
            RouteCategory category = routeInfo.getCategory();
            int size = sStatic.mRoutes.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (category == sStatic.mRoutes.get(i).getCategory()) {
                    z = true;
                    break;
                }
                i++;
            }
            if (routeInfo.isSelected()) {
                selectDefaultRouteStatic();
            }
            if (!z) {
                sStatic.mCategories.remove(category);
            }
            dispatchRouteRemoved(routeInfo);
        }
    }

    public int getCategoryCount() {
        return sStatic.mCategories.size();
    }

    public RouteCategory getCategoryAt(int i) {
        return sStatic.mCategories.get(i);
    }

    public int getRouteCount() {
        return sStatic.mRoutes.size();
    }

    public RouteInfo getRouteAt(int i) {
        return sStatic.mRoutes.get(i);
    }

    static int getRouteCountStatic() {
        return sStatic.mRoutes.size();
    }

    static RouteInfo getRouteAtStatic(int i) {
        return sStatic.mRoutes.get(i);
    }

    public UserRouteInfo createUserRoute(RouteCategory routeCategory) {
        return new UserRouteInfo(routeCategory);
    }

    public RouteCategory createRouteCategory(CharSequence charSequence, boolean z) {
        return new RouteCategory(charSequence, 8388608, z);
    }

    public RouteCategory createRouteCategory(int i, boolean z) {
        return new RouteCategory(i, 8388608, z);
    }

    public void rebindAsUser(int i) {
        sStatic.rebindAsUser(i);
    }

    static void updateRoute(RouteInfo routeInfo) {
        dispatchRouteChanged(routeInfo);
    }

    static void dispatchRouteSelected(int i, RouteInfo routeInfo) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteSelected(next.router, i, routeInfo);
            }
        }
    }

    static void dispatchRouteUnselected(int i, RouteInfo routeInfo) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteUnselected(next.router, i, routeInfo);
            }
        }
    }

    static void dispatchRouteChanged(RouteInfo routeInfo) {
        dispatchRouteChanged(routeInfo, routeInfo.mSupportedTypes);
    }

    static void dispatchRouteChanged(RouteInfo routeInfo, int i) {
        if (DEBUG) {
            Log.d(TAG, "Dispatching route change: " + routeInfo);
        }
        int i2 = routeInfo.mSupportedTypes;
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            boolean filterRouteEvent = next.filterRouteEvent(i);
            boolean filterRouteEvent2 = next.filterRouteEvent(i2);
            if (!filterRouteEvent && filterRouteEvent2) {
                next.cb.onRouteAdded(next.router, routeInfo);
                if (routeInfo.isSelected()) {
                    next.cb.onRouteSelected(next.router, i2, routeInfo);
                }
            }
            if (filterRouteEvent || filterRouteEvent2) {
                next.cb.onRouteChanged(next.router, routeInfo);
            }
            if (filterRouteEvent && !filterRouteEvent2) {
                if (routeInfo.isSelected()) {
                    next.cb.onRouteUnselected(next.router, i, routeInfo);
                }
                next.cb.onRouteRemoved(next.router, routeInfo);
            }
        }
    }

    static void dispatchRouteAdded(RouteInfo routeInfo) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteAdded(next.router, routeInfo);
            }
        }
    }

    static void dispatchRouteRemoved(RouteInfo routeInfo) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteRemoved(next.router, routeInfo);
            }
        }
    }

    static void dispatchRouteGrouped(RouteInfo routeInfo, RouteGroup routeGroup, int i) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeGroup)) {
                next.cb.onRouteGrouped(next.router, routeInfo, routeGroup, i);
            }
        }
    }

    static void dispatchRouteUngrouped(RouteInfo routeInfo, RouteGroup routeGroup) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeGroup)) {
                next.cb.onRouteUngrouped(next.router, routeInfo, routeGroup);
            }
        }
    }

    static void dispatchRouteVolumeChanged(RouteInfo routeInfo) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteVolumeChanged(next.router, routeInfo);
            }
        }
    }

    static void dispatchRoutePresentationDisplayChanged(RouteInfo routeInfo) {
        Iterator<CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRoutePresentationDisplayChanged(next.router, routeInfo);
            }
        }
    }

    static void systemVolumeChanged(int i) {
        RouteInfo routeInfo = sStatic.mSelectedRoute;
        if (routeInfo == null) {
            return;
        }
        if (routeInfo.isBluetooth() || routeInfo.isDefault()) {
            dispatchRouteVolumeChanged(routeInfo);
        } else if (sStatic.mBluetoothA2dpRoute != null) {
            dispatchRouteVolumeChanged(sStatic.mIsBluetoothA2dpOn ? sStatic.mBluetoothA2dpRoute : sStatic.mDefaultAudioVideo);
        } else {
            dispatchRouteVolumeChanged(sStatic.mDefaultAudioVideo);
        }
    }

    static void updateWifiDisplayStatus(WifiDisplayStatus wifiDisplayStatus) {
        WifiDisplay[] wifiDisplayArr;
        WifiDisplay wifiDisplay;
        WifiDisplay findWifiDisplay;
        if (wifiDisplayStatus.getFeatureState() == 3 && wifiDisplayStatus.getConnectedState() != 3) {
            wifiDisplayArr = wifiDisplayStatus.getDisplays();
            wifiDisplay = wifiDisplayStatus.getActiveDisplay();
            if (!sStatic.mCanConfigureWifiDisplays) {
                if (wifiDisplay != null) {
                    wifiDisplayArr = new WifiDisplay[]{wifiDisplay};
                } else {
                    wifiDisplayArr = WifiDisplay.EMPTY_ARRAY;
                }
            }
        } else {
            wifiDisplayArr = WifiDisplay.EMPTY_ARRAY;
            wifiDisplay = null;
        }
        String deviceAddress = wifiDisplay != null ? wifiDisplay.getDeviceAddress() : null;
        for (WifiDisplay wifiDisplay2 : wifiDisplayArr) {
            if (shouldShowWifiDisplay(wifiDisplay2, wifiDisplay)) {
                RouteInfo findWifiDisplayRoute = findWifiDisplayRoute(wifiDisplay2);
                if (findWifiDisplayRoute == null) {
                    findWifiDisplayRoute = makeWifiDisplayRoute(wifiDisplay2, wifiDisplayStatus);
                    addRouteStatic(findWifiDisplayRoute);
                } else {
                    String deviceAddress2 = wifiDisplay2.getDeviceAddress();
                    updateWifiDisplayRoute(findWifiDisplayRoute, wifiDisplay2, wifiDisplayStatus, !deviceAddress2.equals(deviceAddress) && deviceAddress2.equals(sStatic.mPreviousActiveWifiDisplayAddress));
                }
                if (wifiDisplay2.equals(wifiDisplay)) {
                    selectRouteStatic(findWifiDisplayRoute.getSupportedTypes(), findWifiDisplayRoute, false);
                }
            }
        }
        int size = sStatic.mRoutes.size();
        while (true) {
            int i = size - 1;
            if (size > 0) {
                RouteInfo routeInfo = sStatic.mRoutes.get(i);
                if (routeInfo.mDeviceAddress != null && ((findWifiDisplay = findWifiDisplay(wifiDisplayArr, routeInfo.mDeviceAddress)) == null || !shouldShowWifiDisplay(findWifiDisplay, wifiDisplay))) {
                    removeRouteStatic(routeInfo);
                }
                size = i;
            } else {
                sStatic.mPreviousActiveWifiDisplayAddress = deviceAddress;
                return;
            }
        }
    }

    private static boolean shouldShowWifiDisplay(WifiDisplay wifiDisplay, WifiDisplay wifiDisplay2) {
        return wifiDisplay.isRemembered() || wifiDisplay.equals(wifiDisplay2);
    }

    static int getWifiDisplayStatusCode(WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus) {
        int i;
        if (wifiDisplayStatus.getScanState() == 1) {
            i = 1;
        } else if (wifiDisplay.isAvailable()) {
            i = wifiDisplay.canConnect() ? 3 : 5;
        } else {
            i = 4;
        }
        if (wifiDisplay.equals(wifiDisplayStatus.getActiveDisplay())) {
            int activeDisplayState = wifiDisplayStatus.getActiveDisplayState();
            if (activeDisplayState == 0) {
                Log.e(TAG, "Active display is not connected!");
            } else {
                if (activeDisplayState == 1) {
                    return 2;
                }
                if (activeDisplayState == 2) {
                    return 6;
                }
            }
        }
        return i;
    }

    static boolean isWifiDisplayEnabled(WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus) {
        if (wifiDisplay.isAvailable()) {
            return wifiDisplay.canConnect() || wifiDisplay.equals(wifiDisplayStatus.getActiveDisplay());
        }
        return false;
    }

    static RouteInfo makeWifiDisplayRoute(WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus) {
        RouteInfo routeInfo = new RouteInfo(sStatic.mSystemCategory);
        routeInfo.mDeviceAddress = wifiDisplay.getDeviceAddress();
        routeInfo.mSupportedTypes = 7;
        routeInfo.mVolumeHandling = 0;
        routeInfo.mPlaybackType = 1;
        routeInfo.setRealStatusCode(getWifiDisplayStatusCode(wifiDisplay, wifiDisplayStatus));
        routeInfo.mEnabled = isWifiDisplayEnabled(wifiDisplay, wifiDisplayStatus);
        routeInfo.mName = wifiDisplay.getFriendlyDisplayName();
        routeInfo.mDescription = sStatic.mResources.getText(R.string.wireless_display_route_description);
        routeInfo.updatePresentationDisplay();
        routeInfo.mDeviceType = 1;
        return routeInfo;
    }

    private static void updateWifiDisplayRoute(RouteInfo routeInfo, WifiDisplay wifiDisplay, WifiDisplayStatus wifiDisplayStatus, boolean z) {
        boolean z2;
        String friendlyDisplayName = wifiDisplay.getFriendlyDisplayName();
        if (routeInfo.getName().equals(friendlyDisplayName)) {
            z2 = false;
        } else {
            routeInfo.mName = friendlyDisplayName;
            z2 = true;
        }
        boolean isWifiDisplayEnabled = isWifiDisplayEnabled(wifiDisplay, wifiDisplayStatus);
        boolean z3 = routeInfo.mEnabled != isWifiDisplayEnabled;
        routeInfo.mEnabled = isWifiDisplayEnabled;
        if (routeInfo.setRealStatusCode(getWifiDisplayStatusCode(wifiDisplay, wifiDisplayStatus)) | z2 | z3) {
            dispatchRouteChanged(routeInfo);
        }
        if ((!isWifiDisplayEnabled || z) && routeInfo.isSelected()) {
            selectDefaultRouteStatic();
        }
    }

    private static WifiDisplay findWifiDisplay(WifiDisplay[] wifiDisplayArr, String str) {
        for (WifiDisplay wifiDisplay : wifiDisplayArr) {
            if (wifiDisplay.getDeviceAddress().equals(str)) {
                return wifiDisplay;
            }
        }
        return null;
    }

    private static RouteInfo findWifiDisplayRoute(WifiDisplay wifiDisplay) {
        int size = sStatic.mRoutes.size();
        for (int i = 0; i < size; i++) {
            RouteInfo routeInfo = sStatic.mRoutes.get(i);
            if (wifiDisplay.getDeviceAddress().equals(routeInfo.mDeviceAddress)) {
                return routeInfo;
            }
        }
        return null;
    }

    public static class RouteInfo {
        private static final int DEFAULT_PLAYBACK_MAX_VOLUME = 15;
        private static final int DEFAULT_PLAYBACK_VOLUME = 15;
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int SEM_STATUS_CONNECTED = 6;
        public static final int STATUS_AVAILABLE = 3;
        public static final int STATUS_CONNECTED = 6;
        public static final int STATUS_CONNECTING = 2;
        public static final int STATUS_IN_USE = 5;
        public static final int STATUS_NONE = 0;
        public static final int STATUS_NOT_AVAILABLE = 4;
        public static final int STATUS_SCANNING = 1;
        final RouteCategory mCategory;
        CharSequence mDescription;
        String mDeviceAddress;
        String mGlobalRouteId;
        RouteGroup mGroup;
        Drawable mIcon;
        CharSequence mName;
        int mNameResId;
        Display mPresentationDisplay;
        private int mRealStatusCode;
        private int mResolvedStatusCode;
        private CharSequence mStatus;
        int mSupportedTypes;
        private Object mTag;
        VolumeCallbackInfo mVcb;
        int mPlaybackType = 0;
        int mVolumeMax = 15;
        int mVolume = 15;
        int mVolumeHandling = 1;
        int mPlaybackStream = 3;
        int mPresentationDisplayId = -1;
        boolean mEnabled = true;
        final IRemoteVolumeObserver.Stub mRemoteVolObserver = new IRemoteVolumeObserver.Stub() { // from class: android.media.MediaRouter.RouteInfo.1
            @Override // android.media.IRemoteVolumeObserver
            public void dispatchRemoteVolumeUpdate(final int i, final int i2) {
                MediaRouter.sStatic.mHandler.post(new Runnable() { // from class: android.media.MediaRouter.RouteInfo.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (RouteInfo.this.mVcb != null) {
                            if (i != 0) {
                                RouteInfo.this.mVcb.vcb.onVolumeUpdateRequest(RouteInfo.this.mVcb.route, i);
                            } else {
                                RouteInfo.this.mVcb.vcb.onVolumeSetRequest(RouteInfo.this.mVcb.route, i2);
                            }
                        }
                    }
                });
            }
        };
        int mDeviceType = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DeviceType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PlaybackType {
        }

        @Retention(RetentionPolicy.SOURCE)
        private @interface PlaybackVolume {
        }

        public RouteInfo(RouteCategory routeCategory) {
            this.mCategory = routeCategory;
        }

        public CharSequence getName() {
            return getName(MediaRouter.sStatic.mResources);
        }

        public CharSequence getName(Context context) {
            return getName(context.getResources());
        }

        CharSequence getName(Resources resources) {
            int i = this.mNameResId;
            if (i != 0) {
                return resources.getText(i);
            }
            return this.mName;
        }

        public CharSequence getDescription() {
            return this.mDescription;
        }

        public CharSequence getStatus() {
            return this.mStatus;
        }

        boolean setRealStatusCode(int i) {
            if (this.mRealStatusCode == i) {
                return false;
            }
            this.mRealStatusCode = i;
            return resolveStatusCode();
        }

        boolean resolveStatusCode() {
            int i = this.mRealStatusCode;
            if (isSelected() && (i == 1 || i == 3)) {
                i = 2;
            }
            int i2 = 0;
            if (this.mResolvedStatusCode == i) {
                return false;
            }
            this.mResolvedStatusCode = i;
            if (i == 1) {
                i2 = R.string.media_route_status_scanning;
            } else if (i == 2) {
                i2 = R.string.media_route_status_connecting;
            } else if (i == 3) {
                i2 = R.string.media_route_status_available;
            } else if (i == 4) {
                i2 = R.string.media_route_status_not_available;
            } else if (i == 5) {
                i2 = R.string.media_route_status_in_use;
            }
            this.mStatus = i2 != 0 ? MediaRouter.sStatic.mResources.getText(i2) : null;
            return true;
        }

        public int getStatusCode() {
            return this.mResolvedStatusCode;
        }

        public int getSupportedTypes() {
            return this.mSupportedTypes;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        public boolean matchesTypes(int i) {
            return (this.mSupportedTypes & i) != 0;
        }

        public RouteGroup getGroup() {
            return this.mGroup;
        }

        public RouteCategory getCategory() {
            return this.mCategory;
        }

        public Drawable getIconDrawable() {
            return this.mIcon;
        }

        public void setTag(Object obj) {
            this.mTag = obj;
            routeUpdated();
        }

        public Object getTag() {
            return this.mTag;
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getVolume() {
            if (this.mPlaybackType == 0) {
                return MediaRouter.sStatic.getStreamVolume(this.mPlaybackStream);
            }
            return this.mVolume;
        }

        public void requestSetVolume(int i) {
            if (this.mPlaybackType == 0) {
                try {
                    MediaRouter.sStatic.mAudioService.setStreamVolumeWithAttribution(this.mPlaybackStream, i, 0, ActivityThread.currentPackageName(), null);
                    return;
                } catch (RemoteException e) {
                    Log.e(MediaRouter.TAG, "Error setting local stream volume", e);
                    return;
                }
            }
            MediaRouter.sStatic.requestSetVolume(this, i);
        }

        public void requestUpdateVolume(int i) {
            if (this.mPlaybackType == 0) {
                try {
                    MediaRouter.sStatic.mAudioService.setStreamVolumeWithAttribution(this.mPlaybackStream, Math.max(0, Math.min(getVolume() + i, getVolumeMax())), 0, ActivityThread.currentPackageName(), null);
                    return;
                } catch (RemoteException e) {
                    Log.e(MediaRouter.TAG, "Error setting local stream volume", e);
                    return;
                }
            }
            MediaRouter.sStatic.requestUpdateVolume(this, i);
        }

        public int getVolumeMax() {
            if (this.mPlaybackType == 0) {
                try {
                    return MediaRouter.sStatic.mAudioService.getStreamMaxVolume(this.mPlaybackStream);
                } catch (RemoteException e) {
                    Log.e(MediaRouter.TAG, "Error getting local stream volume", e);
                    return 0;
                }
            }
            return this.mVolumeMax;
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public Display getPresentationDisplay() {
            return this.mPresentationDisplay;
        }

        public boolean updatePresentationDisplay() {
            Display choosePresentationDisplay = choosePresentationDisplay();
            if (this.mPresentationDisplay == choosePresentationDisplay) {
                return false;
            }
            this.mPresentationDisplay = choosePresentationDisplay;
            return true;
        }

        private Display choosePresentationDisplay() {
            Display[] allPresentationDisplays;
            if ((getSupportedTypes() & 2) != 0 && (allPresentationDisplays = getAllPresentationDisplays()) != null && allPresentationDisplays.length != 0) {
                if (this.mPresentationDisplayId >= 0) {
                    for (Display display : allPresentationDisplays) {
                        if (display.getDisplayId() == this.mPresentationDisplayId) {
                            return display;
                        }
                    }
                    return null;
                }
                if (getDeviceAddress() != null) {
                    for (Display display2 : allPresentationDisplays) {
                        if (display2.getType() == 3 && displayAddressEquals(display2)) {
                            return display2;
                        }
                    }
                }
                for (Display display3 : allPresentationDisplays) {
                    if (display3.getType() == 2) {
                        return display3;
                    }
                }
                for (Display display4 : allPresentationDisplays) {
                    if (display4.getType() == 1) {
                        return display4;
                    }
                }
                if (this == getDefaultAudioVideo()) {
                    return allPresentationDisplays[0];
                }
            }
            return null;
        }

        public Display[] getAllPresentationDisplays() {
            return MediaRouter.sStatic.getAllPresentationDisplays();
        }

        public RouteInfo getDefaultAudioVideo() {
            return MediaRouter.sStatic.mDefaultAudioVideo;
        }

        private boolean displayAddressEquals(Display display) {
            DisplayAddress address = display.getAddress();
            if (address instanceof DisplayAddress.Network) {
                return getDeviceAddress().equals(((DisplayAddress.Network) address).toString());
            }
            return false;
        }

        public String getDeviceAddress() {
            return this.mDeviceAddress;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isConnecting() {
            return this.mResolvedStatusCode == 2;
        }

        public boolean isSelected() {
            return this == MediaRouter.sStatic.mSelectedRoute;
        }

        public boolean isDefault() {
            return this == MediaRouter.sStatic.mDefaultAudioVideo;
        }

        public boolean isBluetooth() {
            return this.mDeviceType == 3;
        }

        public void select() {
            MediaRouter.selectRouteStatic(this.mSupportedTypes, this, true);
        }

        void setStatusInt(CharSequence charSequence) {
            if (charSequence.equals(this.mStatus)) {
                return;
            }
            this.mStatus = charSequence;
            RouteGroup routeGroup = this.mGroup;
            if (routeGroup != null) {
                routeGroup.memberStatusChanged(this, charSequence);
            }
            routeUpdated();
        }

        void routeUpdated() {
            MediaRouter.updateRoute(this);
        }

        public String toString() {
            return getClass().getSimpleName() + "{ name=" + ((Object) getName()) + ", description=" + ((Object) getDescription()) + ", status=" + ((Object) getStatus()) + ", category=" + getCategory() + ", supportedTypes=" + MediaRouter.typesToString(getSupportedTypes()) + ", presentationDisplay=" + this.mPresentationDisplay + " }";
        }

        public int semGetStatusCode() {
            return getStatusCode();
        }

        public String semGetDeviceAddress() {
            return getDeviceAddress();
        }

        public void semSelect() {
            select();
        }
    }

    public static class UserRouteInfo extends RouteInfo {
        RemoteControlClient mRcc;
        SessionVolumeProvider mSvp;

        UserRouteInfo(RouteCategory routeCategory) {
            super(routeCategory);
            this.mSupportedTypes = 8388608;
            this.mPlaybackType = 1;
            this.mVolumeHandling = 0;
        }

        public void setName(CharSequence charSequence) {
            this.mNameResId = 0;
            this.mName = charSequence;
            routeUpdated();
        }

        public void setName(int i) {
            this.mNameResId = i;
            this.mName = null;
            routeUpdated();
        }

        public void setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            routeUpdated();
        }

        public void setStatus(CharSequence charSequence) {
            setStatusInt(charSequence);
        }

        public void setRemoteControlClient(RemoteControlClient remoteControlClient) {
            this.mRcc = remoteControlClient;
            updatePlaybackInfoOnRcc();
        }

        public RemoteControlClient getRemoteControlClient() {
            return this.mRcc;
        }

        public void setIconDrawable(Drawable drawable) {
            this.mIcon = drawable;
        }

        public void setIconResource(int i) {
            setIconDrawable(MediaRouter.sStatic.mResources.getDrawable(i));
        }

        public void setVolumeCallback(VolumeCallback volumeCallback) {
            this.mVcb = new VolumeCallbackInfo(volumeCallback, this);
        }

        public void setPlaybackType(int i) {
            if (this.mPlaybackType != i) {
                this.mPlaybackType = i;
                configureSessionVolume();
            }
        }

        public void setVolumeHandling(int i) {
            if (this.mVolumeHandling != i) {
                this.mVolumeHandling = i;
                configureSessionVolume();
            }
        }

        public void setVolume(int i) {
            int max = Math.max(0, Math.min(i, getVolumeMax()));
            if (this.mVolume != max) {
                this.mVolume = max;
                SessionVolumeProvider sessionVolumeProvider = this.mSvp;
                if (sessionVolumeProvider != null) {
                    sessionVolumeProvider.setCurrentVolume(this.mVolume);
                }
                MediaRouter.dispatchRouteVolumeChanged(this);
                if (this.mGroup != null) {
                    this.mGroup.memberVolumeChanged(this);
                }
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestSetVolume(int i) {
            if (this.mVolumeHandling == 1) {
                if (this.mVcb == null) {
                    Log.e(MediaRouter.TAG, "Cannot requestSetVolume on user route - no volume callback set");
                } else {
                    this.mVcb.vcb.onVolumeSetRequest(this, i);
                }
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestUpdateVolume(int i) {
            if (this.mVolumeHandling == 1) {
                if (this.mVcb == null) {
                    Log.e(MediaRouter.TAG, "Cannot requestChangeVolume on user route - no volumec callback set");
                } else {
                    this.mVcb.vcb.onVolumeUpdateRequest(this, i);
                }
            }
        }

        public void setVolumeMax(int i) {
            if (this.mVolumeMax != i) {
                this.mVolumeMax = i;
                configureSessionVolume();
            }
        }

        public void setPlaybackStream(int i) {
            if (this.mPlaybackStream != i) {
                this.mPlaybackStream = i;
                configureSessionVolume();
            }
        }

        private void updatePlaybackInfoOnRcc() {
            configureSessionVolume();
        }

        private void configureSessionVolume() {
            RemoteControlClient remoteControlClient = this.mRcc;
            if (remoteControlClient == null) {
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "No Rcc to configure volume for route " + ((Object) getName()));
                    return;
                }
                return;
            }
            MediaSession mediaSession = remoteControlClient.getMediaSession();
            if (mediaSession == null) {
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Rcc has no session to configure volume");
                    return;
                }
                return;
            }
            if (this.mPlaybackType == 1) {
                int i = this.mVolumeHandling != 1 ? 0 : 2;
                SessionVolumeProvider sessionVolumeProvider = this.mSvp;
                if (sessionVolumeProvider != null && sessionVolumeProvider.getVolumeControl() == i && this.mSvp.getMaxVolume() == this.mVolumeMax) {
                    return;
                }
                SessionVolumeProvider sessionVolumeProvider2 = new SessionVolumeProvider(i, this.mVolumeMax, this.mVolume);
                this.mSvp = sessionVolumeProvider2;
                mediaSession.setPlaybackToRemote(sessionVolumeProvider2);
                return;
            }
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            builder.setLegacyStreamType(this.mPlaybackStream);
            mediaSession.setPlaybackToLocal(builder.build());
            this.mSvp = null;
        }

        class SessionVolumeProvider extends VolumeProvider {
            SessionVolumeProvider(int i, int i2, int i3) {
                super(i, i2, i3);
            }

            @Override // android.media.VolumeProvider
            public void onSetVolumeTo(final int i) {
                MediaRouter.sStatic.mHandler.post(new Runnable() { // from class: android.media.MediaRouter.UserRouteInfo.SessionVolumeProvider.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (UserRouteInfo.this.mVcb != null) {
                            UserRouteInfo.this.mVcb.vcb.onVolumeSetRequest(UserRouteInfo.this.mVcb.route, i);
                        }
                    }
                });
            }

            @Override // android.media.VolumeProvider
            public void onAdjustVolume(final int i) {
                MediaRouter.sStatic.mHandler.post(new Runnable() { // from class: android.media.MediaRouter.UserRouteInfo.SessionVolumeProvider.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (UserRouteInfo.this.mVcb != null) {
                            UserRouteInfo.this.mVcb.vcb.onVolumeUpdateRequest(UserRouteInfo.this.mVcb.route, i);
                        }
                    }
                });
            }
        }
    }

    public static class RouteGroup extends RouteInfo {
        final ArrayList<RouteInfo> mRoutes;
        private boolean mUpdateName;

        RouteGroup(RouteCategory routeCategory) {
            super(routeCategory);
            this.mRoutes = new ArrayList<>();
            this.mGroup = this;
            this.mVolumeHandling = 0;
        }

        @Override // android.media.MediaRouter.RouteInfo
        CharSequence getName(Resources resources) {
            if (this.mUpdateName) {
                updateName();
            }
            return super.getName(resources);
        }

        public void addRoute(RouteInfo routeInfo) {
            if (routeInfo.getGroup() != null) {
                throw new IllegalStateException("Route " + routeInfo + " is already part of a group.");
            }
            if (routeInfo.getCategory() != this.mCategory) {
                throw new IllegalArgumentException("Route cannot be added to a group with a different category. (Route category=" + routeInfo.getCategory() + " group category=" + this.mCategory + NavigationBarInflaterView.KEY_CODE_END);
            }
            int size = this.mRoutes.size();
            this.mRoutes.add(routeInfo);
            routeInfo.mGroup = this;
            this.mUpdateName = true;
            updateVolume();
            routeUpdated();
            MediaRouter.dispatchRouteGrouped(routeInfo, this, size);
        }

        public void addRoute(RouteInfo routeInfo, int i) {
            if (routeInfo.getGroup() != null) {
                throw new IllegalStateException("Route " + routeInfo + " is already part of a group.");
            }
            if (routeInfo.getCategory() != this.mCategory) {
                throw new IllegalArgumentException("Route cannot be added to a group with a different category. (Route category=" + routeInfo.getCategory() + " group category=" + this.mCategory + NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mRoutes.add(i, routeInfo);
            routeInfo.mGroup = this;
            this.mUpdateName = true;
            updateVolume();
            routeUpdated();
            MediaRouter.dispatchRouteGrouped(routeInfo, this, i);
        }

        public void removeRoute(RouteInfo routeInfo) {
            if (routeInfo.getGroup() != this) {
                throw new IllegalArgumentException("Route " + routeInfo + " is not a member of this group.");
            }
            this.mRoutes.remove(routeInfo);
            routeInfo.mGroup = null;
            this.mUpdateName = true;
            updateVolume();
            MediaRouter.dispatchRouteUngrouped(routeInfo, this);
            routeUpdated();
        }

        public void removeRoute(int i) {
            RouteInfo remove = this.mRoutes.remove(i);
            remove.mGroup = null;
            this.mUpdateName = true;
            updateVolume();
            MediaRouter.dispatchRouteUngrouped(remove, this);
            routeUpdated();
        }

        public int getRouteCount() {
            return this.mRoutes.size();
        }

        public RouteInfo getRouteAt(int i) {
            return this.mRoutes.get(i);
        }

        public void setIconDrawable(Drawable drawable) {
            this.mIcon = drawable;
        }

        public void setIconResource(int i) {
            setIconDrawable(MediaRouter.sStatic.mResources.getDrawable(i));
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestSetVolume(int i) {
            int volumeMax = getVolumeMax();
            if (volumeMax == 0) {
                return;
            }
            float f = i / volumeMax;
            int routeCount = getRouteCount();
            for (int i2 = 0; i2 < routeCount; i2++) {
                getRouteAt(i2).requestSetVolume((int) (r3.getVolumeMax() * f));
            }
            if (i != this.mVolume) {
                this.mVolume = i;
                MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestUpdateVolume(int i) {
            if (getVolumeMax() == 0) {
                return;
            }
            int routeCount = getRouteCount();
            int i2 = 0;
            for (int i3 = 0; i3 < routeCount; i3++) {
                RouteInfo routeAt = getRouteAt(i3);
                routeAt.requestUpdateVolume(i);
                int volume = routeAt.getVolume();
                if (volume > i2) {
                    i2 = volume;
                }
            }
            if (i2 != this.mVolume) {
                this.mVolume = i2;
                MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        void memberNameChanged(RouteInfo routeInfo, CharSequence charSequence) {
            this.mUpdateName = true;
            routeUpdated();
        }

        void memberStatusChanged(RouteInfo routeInfo, CharSequence charSequence) {
            setStatusInt(charSequence);
        }

        void memberVolumeChanged(RouteInfo routeInfo) {
            updateVolume();
        }

        void updateVolume() {
            int routeCount = getRouteCount();
            int i = 0;
            for (int i2 = 0; i2 < routeCount; i2++) {
                int volume = getRouteAt(i2).getVolume();
                if (volume > i) {
                    i = volume;
                }
            }
            if (i != this.mVolume) {
                this.mVolume = i;
                MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        void routeUpdated() {
            int size = this.mRoutes.size();
            if (size == 0) {
                MediaRouter.removeRouteStatic(this);
                return;
            }
            int i = 1;
            int i2 = 1;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                RouteInfo routeInfo = this.mRoutes.get(i5);
                i3 |= routeInfo.mSupportedTypes;
                int volumeMax = routeInfo.getVolumeMax();
                if (volumeMax > i4) {
                    i4 = volumeMax;
                }
                i &= routeInfo.getPlaybackType() == 0 ? 1 : 0;
                i2 &= routeInfo.getVolumeHandling() == 0 ? 1 : 0;
            }
            this.mPlaybackType = i ^ 1;
            this.mVolumeHandling = i2 ^ 1;
            this.mSupportedTypes = i3;
            this.mVolumeMax = i4;
            this.mIcon = size == 1 ? this.mRoutes.get(0).getIconDrawable() : null;
            super.routeUpdated();
        }

        void updateName() {
            StringBuilder sb = new StringBuilder();
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                RouteInfo routeInfo = this.mRoutes.get(i);
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(routeInfo.getName());
            }
            this.mName = sb.toString();
            this.mUpdateName = false;
        }

        @Override // android.media.MediaRouter.RouteInfo
        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString());
            sb.append('[');
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mRoutes.get(i));
            }
            sb.append(']');
            return sb.toString();
        }
    }

    public static class RouteCategory {
        final boolean mGroupable;
        boolean mIsSystem;
        CharSequence mName;
        int mNameResId;
        int mTypes;

        RouteCategory(CharSequence charSequence, int i, boolean z) {
            this.mName = charSequence;
            this.mTypes = i;
            this.mGroupable = z;
        }

        RouteCategory(int i, int i2, boolean z) {
            this.mNameResId = i;
            this.mTypes = i2;
            this.mGroupable = z;
        }

        public CharSequence getName() {
            return getName(MediaRouter.sStatic.mResources);
        }

        public CharSequence getName(Context context) {
            return getName(context.getResources());
        }

        CharSequence getName(Resources resources) {
            int i = this.mNameResId;
            if (i != 0) {
                return resources.getText(i);
            }
            return this.mName;
        }

        public List<RouteInfo> getRoutes(List<RouteInfo> list) {
            if (list == null) {
                list = new ArrayList<>();
            } else {
                list.clear();
            }
            int routeCountStatic = MediaRouter.getRouteCountStatic();
            for (int i = 0; i < routeCountStatic; i++) {
                RouteInfo routeAtStatic = MediaRouter.getRouteAtStatic(i);
                if (routeAtStatic.mCategory == this) {
                    list.add(routeAtStatic);
                }
            }
            return list;
        }

        public int getSupportedTypes() {
            return this.mTypes;
        }

        public boolean isGroupable() {
            return this.mGroupable;
        }

        public boolean isSystem() {
            return this.mIsSystem;
        }

        public String toString() {
            return "RouteCategory{ name=" + ((Object) getName()) + " types=" + MediaRouter.typesToString(this.mTypes) + " groupable=" + this.mGroupable + " }";
        }
    }

    static class CallbackInfo {
        public final Callback cb;
        public int flags;
        public final MediaRouter router;
        public int type;

        public CallbackInfo(Callback callback, int i, int i2, MediaRouter mediaRouter) {
            this.cb = callback;
            this.type = i;
            this.flags = i2;
            this.router = mediaRouter;
        }

        public boolean filterRouteEvent(RouteInfo routeInfo) {
            return filterRouteEvent(routeInfo.mSupportedTypes);
        }

        public boolean filterRouteEvent(int i) {
            return ((this.flags & 2) == 0 && (this.type & i) == 0) ? false : true;
        }
    }

    static class VolumeCallbackInfo {
        public final RouteInfo route;
        public final VolumeCallback vcb;

        public VolumeCallbackInfo(VolumeCallback volumeCallback, RouteInfo routeInfo) {
            this.vcb = volumeCallback;
            this.route = routeInfo;
        }
    }

    static class VolumeChangeReceiver extends BroadcastReceiver {
        VolumeChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                MediaRouter.sStatic.mStreamVolume.put(intExtra, intExtra2);
                if (intExtra == 3 && intExtra2 != intent.getIntExtra(AudioManager.EXTRA_PREV_VOLUME_STREAM_VALUE, 0)) {
                    MediaRouter.systemVolumeChanged(intExtra2);
                }
            }
        }
    }

    static class WifiDisplayStatusChangedReceiver extends BroadcastReceiver {
        WifiDisplayStatusChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED")) {
                MediaRouter.updateWifiDisplayStatus((WifiDisplayStatus) intent.getParcelableExtra("android.hardware.display.extra.WIFI_DISPLAY_STATUS", WifiDisplayStatus.class));
            }
        }
    }

    public RouteInfo semGetA2dpRoute() {
        return sStatic.mBluetoothA2dpRoute;
    }

    static boolean isAudioPathA2DPStatic() {
        int i;
        int devicesForStream;
        if (sStatic.mSelectedRoute == null || (i = sStatic.mSelectedRoute.getPlaybackStream()) < 0 || i > AudioSystem.getNumStreamTypes()) {
            i = 3;
        }
        try {
            devicesForStream = sStatic.mAudioService.getDeviceMaskForStream(i);
        } catch (RemoteException unused) {
            devicesForStream = AudioSystem.getDevicesForStream(i);
        }
        if (((devicesForStream - 1) & devicesForStream) != 0) {
            if ((devicesForStream & 2) != 0) {
                devicesForStream = 2;
            } else {
                Iterator<Integer> it = AudioSystem.DEVICE_OUT_ALL_A2DP_SET.iterator();
                while (it.hasNext()) {
                    devicesForStream &= it.next().intValue();
                }
            }
        }
        return AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(devicesForStream));
    }
}
