package com.android.settingslib.media;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.LocalMediaManager;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public abstract class InfoMediaManager {
    public final LocalBluetoothManager mBluetoothManager;
    public final Context mContext;
    public MediaController.PlaybackInfo mLastKnownPlaybackInfo;
    public MediaController mMediaController;
    public final String mPackageName;
    public final MediaRouter2Manager mRouterManager;
    public final UserHandle mUserHandle;
    public final List mMediaDevices = new CopyOnWriteArrayList();
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Map mPreferenceItemMap = new ConcurrentHashMap();
    public final MediaControllerCallback mMediaControllerCallback = new MediaControllerCallback(this, 0);

    public class Api34Impl {
        public static List<MediaRoute2Info> arrangeRouteListByPreference(List<MediaRoute2Info> list, List<MediaRoute2Info> list2, RouteListingPreference routeListingPreference) {
            List<RouteListingPreference.Item> listComposePreferenceRouteListing = composePreferenceRouteListing(routeListingPreference);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            boolean zPreferRouteListingOrdering = preferRouteListingOrdering(routeListingPreference);
            HashSet hashSet = new HashSet();
            if (zPreferRouteListingOrdering) {
                Iterator<MediaRoute2Info> it = list.iterator();
                while (it.hasNext()) {
                    hashSet.add(it.next().getId());
                }
                for (RouteListingPreference.Item item : listComposePreferenceRouteListing) {
                    if (hashSet.contains(item.getRouteId())) {
                        linkedHashSet.add(item.getRouteId());
                    }
                }
            }
            if (linkedHashSet.size() != list.size()) {
                Iterator<MediaRoute2Info> it2 = list.iterator();
                while (it2.hasNext()) {
                    linkedHashSet.add(it2.next().getId());
                }
            }
            for (MediaRoute2Info mediaRoute2Info : list2) {
                if (mediaRoute2Info.isSystemRoute()) {
                    linkedHashSet.add(mediaRoute2Info.getId());
                }
            }
            final Map map = (Map) Stream.concat(list.stream(), list2.stream()).collect(Collectors.toMap(new InfoMediaManager$Api34Impl$$ExternalSyntheticLambda1(), Function.identity(), new InfoMediaManager$Api34Impl$$ExternalSyntheticLambda2()));
            Iterator<RouteListingPreference.Item> it3 = listComposePreferenceRouteListing.iterator();
            while (it3.hasNext()) {
                MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) map.get(it3.next().getRouteId());
                if (mediaRoute2Info2 != null) {
                    linkedHashSet.add(mediaRoute2Info2.getId());
                }
            }
            Stream stream = linkedHashSet.stream();
            Objects.requireNonNull(map);
            return (List) stream.map(new Function() { // from class: com.android.settingslib.media.InfoMediaManager$Api34Impl$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (MediaRoute2Info) map.get((String) obj);
                }
            }).collect(Collectors.toList());
        }

        public static List<RouteListingPreference.Item> composePreferenceRouteListing(RouteListingPreference routeListingPreference) {
            boolean zPreferRouteListingOrdering = preferRouteListingOrdering(routeListingPreference);
            ArrayList arrayList = new ArrayList();
            for (RouteListingPreference.Item item : routeListingPreference.getItems()) {
                if (zPreferRouteListingOrdering || (item.getFlags() & 4) == 0) {
                    arrayList.add(item);
                } else {
                    arrayList.add(0, item);
                }
            }
            return arrayList;
        }

        public static synchronized List<MediaRoute2Info> filterDuplicatedIds(List<MediaRoute2Info> list) {
            ArrayList arrayList;
            arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            for (MediaRoute2Info mediaRoute2Info : list) {
                if (Collections.disjoint(mediaRoute2Info.getDeduplicationIds(), hashSet)) {
                    arrayList.add(mediaRoute2Info);
                    hashSet.addAll(mediaRoute2Info.getDeduplicationIds());
                }
            }
            return arrayList;
        }

        public static ComponentName getLinkedItemComponentName(RouteListingPreference routeListingPreference) {
            if (routeListingPreference == null) {
                return null;
            }
            return routeListingPreference.getLinkedItemComponentName();
        }

        public static void onRouteListingPreferenceUpdated(RouteListingPreference routeListingPreference, final Map<String, RouteListingPreference.Item> map) {
            map.clear();
            if (routeListingPreference != null) {
                routeListingPreference.getItems().forEach(new Consumer() { // from class: com.android.settingslib.media.InfoMediaManager$Api34Impl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RouteListingPreference.Item item = (RouteListingPreference.Item) obj;
                        map.put(item.getRouteId(), item);
                    }
                });
            }
        }

        public static boolean preferRouteListingOrdering(RouteListingPreference routeListingPreference) {
            return (routeListingPreference == null || routeListingPreference.getUseSystemOrdering()) ? false : true;
        }
    }

    public final class MediaControllerCallback extends MediaController.Callback {
        public /* synthetic */ MediaControllerCallback(InfoMediaManager infoMediaManager, int i) {
            this();
        }

        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            if (playbackInfo.getPlaybackType() != InfoMediaManager.this.mLastKnownPlaybackInfo.getPlaybackType() || !TextUtils.equals(playbackInfo.getVolumeControlId(), InfoMediaManager.this.mLastKnownPlaybackInfo.getVolumeControlId())) {
                InfoMediaManager.this.refreshDevices();
            }
            InfoMediaManager.this.mLastKnownPlaybackInfo = playbackInfo;
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            InfoMediaManager infoMediaManager = InfoMediaManager.this;
            infoMediaManager.mMediaController = null;
            infoMediaManager.refreshDevices();
        }

        private MediaControllerCallback() {
        }
    }

    public class PackageNotAvailableException extends Exception {
        public PackageNotAvailableException(String str) {
            super(str);
        }
    }

    public InfoMediaManager(Context context, String str, UserHandle userHandle, LocalBluetoothManager localBluetoothManager, MediaController mediaController) {
        this.mContext = context;
        this.mBluetoothManager = localBluetoothManager;
        this.mPackageName = str;
        this.mUserHandle = userHandle;
        this.mMediaController = mediaController;
        if (mediaController != null) {
            this.mLastKnownPlaybackInfo = mediaController.getPlaybackInfo();
        }
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
    }

    public static InfoMediaManager createInstance(Context context, String str, UserHandle userHandle, LocalBluetoothManager localBluetoothManager, MediaSession.Token token) {
        Context context2;
        LocalBluetoothManager localBluetoothManager2;
        MediaController mediaController = token != null ? new MediaController(context, token) : null;
        if (TextUtils.isEmpty(str)) {
            str = context.getPackageName();
        }
        String str2 = str;
        if (userHandle == null) {
            userHandle = Process.myUserHandle();
        }
        UserHandle userHandle2 = userHandle;
        try {
            context2 = context;
            localBluetoothManager2 = localBluetoothManager;
        } catch (PackageNotAvailableException unused) {
            context2 = context;
            localBluetoothManager2 = localBluetoothManager;
        }
        try {
            return new RouterInfoMediaManager(context2, str2, userHandle2, localBluetoothManager2, mediaController);
        } catch (PackageNotAvailableException unused2) {
            Log.w("InfoMediaManager", "Returning a no-op InfoMediaManager for package " + str2);
            return new NoOpInfoMediaManager(context2, str2, userHandle2, localBluetoothManager2, mediaController);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addMediaDevice(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
        MediaDevice infoMediaDevice;
        int type = mediaRoute2Info.getType();
        if (type != 0) {
            infoMediaDevice = null;
            if (type == 19 || type == 29) {
                infoMediaDevice = new PhoneMediaDevice(this.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) this.mPreferenceItemMap).getOrDefault(mediaRoute2Info.getId(), null));
            } else if (type == 2000) {
                infoMediaDevice = new InfoMediaDevice(this.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) this.mPreferenceItemMap).get(mediaRoute2Info.getId()));
            } else if (type != 2 && type != 3 && type != 4 && type != 5 && type != 6 && type != 22) {
                if (type == 23) {
                    if (mediaRoute2Info.getAddress() == null) {
                        Log.e("InfoMediaManager", "Ignoring bluetooth route with no set address: " + mediaRoute2Info);
                    } else {
                        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = this.mBluetoothManager.mCachedDeviceManager.findDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mediaRoute2Info.getAddress()));
                        if (cachedBluetoothDeviceFindDevice != null) {
                            infoMediaDevice = new BluetoothMediaDevice(this.mContext, cachedBluetoothDeviceFindDevice, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) this.mPreferenceItemMap).getOrDefault(mediaRoute2Info.getId(), null));
                        }
                    }
                } else if (type != 25) {
                    if (type != 26) {
                        switch (type) {
                            case 8:
                                break;
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                                break;
                            default:
                                switch (type) {
                                    case 1001:
                                    case 1002:
                                    case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                                    case 1005:
                                    case 1006:
                                    case 1007:
                                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS /* 1008 */:
                                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE /* 1009 */:
                                    case EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS /* 1010 */:
                                        break;
                                    case 1003:
                                        infoMediaDevice = new ComplexMediaDevice(this.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) this.mPreferenceItemMap).get(mediaRoute2Info.getId()));
                                        break;
                                    default:
                                        RecordingInputConnection$$ExternalSyntheticOutline0.m(type, "addMediaDevice() unknown device type : ", "InfoMediaManager");
                                        break;
                                }
                        }
                    }
                }
            }
        }
        if (infoMediaDevice != null) {
            ((CopyOnWriteArrayList) this.mMediaDevices).add(infoMediaDevice);
        }
    }

    public abstract void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo);

    public final void dispatchDeviceListAdded(List list) {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            LocalMediaManager.MediaDeviceCallback mediaDeviceCallback = (LocalMediaManager.MediaDeviceCallback) it.next();
            ArrayList arrayList = new ArrayList(list);
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                try {
                    LocalMediaManager.this.mMediaDevices.clear();
                    LocalMediaManager.this.mMediaDevices.addAll(arrayList);
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        int i2 = ((MediaDevice) obj).mType;
                        if (i2 == 2 || i2 == 3 || i2 == 1) {
                            PackageManager packageManager = LocalMediaManager.this.mContext.getPackageManager();
                            if (packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback")) {
                                LocalMediaManager.this.mMediaDevices.addAll(mediaDeviceCallback.buildDisconnectedBluetoothDevice());
                            } else {
                                BluetoothMediaDevice mutingExpectedDevice = mediaDeviceCallback.getMutingExpectedDevice();
                                if (mutingExpectedDevice != null) {
                                    LocalMediaManager.this.mMediaDevices.add(mutingExpectedDevice);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            LocalMediaManager.this.mInfoMediaManager.getClass();
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice = localMediaManager.updateCurrentConnectedDevice();
            LocalMediaManager localMediaManager2 = LocalMediaManager.this;
            localMediaManager2.getClass();
            ArrayList arrayList2 = new ArrayList(localMediaManager2.mMediaDevices);
            Iterator it2 = ((CopyOnWriteArrayList) localMediaManager2.getCallbacks()).iterator();
            while (it2.hasNext()) {
                ((LocalMediaManager.DeviceCallback) it2.next()).onDeviceListUpdate(arrayList2);
            }
            MediaDevice mediaDevice = LocalMediaManager.this.mOnTransferBluetoothDevice;
            if (mediaDevice != null && mediaDevice.isConnected()) {
                LocalMediaManager localMediaManager3 = LocalMediaManager.this;
                localMediaManager3.connectDevice(localMediaManager3.mOnTransferBluetoothDevice);
                LocalMediaManager localMediaManager4 = LocalMediaManager.this;
                MediaDevice mediaDevice2 = localMediaManager4.mOnTransferBluetoothDevice;
                mediaDevice2.mState = 0;
                localMediaManager4.dispatchSelectedDeviceStateChanged(mediaDevice2);
                LocalMediaManager.this.mOnTransferBluetoothDevice = null;
            }
        }
    }

    public final void dispatchOnRequestFailed(int i) {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            LocalMediaManager.MediaDeviceCallback mediaDeviceCallback = (LocalMediaManager.MediaDeviceCallback) it.next();
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                try {
                    for (MediaDevice mediaDevice : LocalMediaManager.this.mMediaDevices) {
                        if (mediaDevice.mState == 1) {
                            mediaDevice.mState = 3;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Iterator it2 = ((CopyOnWriteArrayList) LocalMediaManager.this.getCallbacks()).iterator();
            while (it2.hasNext()) {
                ((LocalMediaManager.DeviceCallback) it2.next()).onRequestFailed(i);
            }
        }
    }

    public final RoutingSessionInfo getActiveRoutingSession() {
        List<RoutingSessionInfo> routingSessionsForPackage = getRoutingSessionsForPackage();
        RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, routingSessionsForPackage);
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
            if (playbackInfo.getPlaybackType() == 1) {
                return (RoutingSessionInfo) routingSessionsForPackage.get(0);
            }
            String volumeControlId = playbackInfo.getVolumeControlId();
            for (RoutingSessionInfo routingSessionInfo2 : routingSessionsForPackage) {
                if (TextUtils.equals(volumeControlId, routingSessionInfo2.getId()) || (TextUtils.equals(volumeControlId, routingSessionInfo2.getOriginalId()) && TextUtils.equals(this.mMediaController.getPackageName(), routingSessionInfo2.getOwnerPackageName()))) {
                    return routingSessionInfo2;
                }
            }
        }
        return routingSessionInfo;
    }

    public abstract List getDeselectableRoutes(RoutingSessionInfo routingSessionInfo);

    public abstract RouteListingPreference getRouteListingPreference();

    public abstract List getRoutingSessionsForPackage();

    public abstract List getSelectableRoutes(RoutingSessionInfo routingSessionInfo);

    public abstract List getSelectedRoutes(RoutingSessionInfo routingSessionInfo);

    public abstract List getTransferableRoutes(RoutingSessionInfo routingSessionInfo);

    public final void notifyCurrentConnectedDeviceChanged() {
        Iterator it = new CopyOnWriteArrayList(this.mCallbacks).iterator();
        while (it.hasNext()) {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            MediaDevice mediaDeviceById = localMediaManager.getMediaDeviceById(null);
            if (mediaDeviceById == null) {
                mediaDeviceById = localMediaManager.updateCurrentConnectedDevice();
            }
            localMediaManager.mCurrentConnectedDevice = mediaDeviceById;
            if (mediaDeviceById != null) {
                mediaDeviceById.mState = 0;
                localMediaManager.dispatchSelectedDeviceStateChanged(mediaDeviceById);
            }
        }
    }

    public final void rebuildDeviceList() {
        Iterator it = this.mRouterManager.getAllRoutes().iterator();
        while (it.hasNext()) {
            addMediaDevice((MediaRoute2Info) it.next(), null);
        }
    }

    public final synchronized void refreshDevices() {
        rebuildDeviceList();
        dispatchDeviceListAdded(this.mMediaDevices);
    }

    public abstract void registerRouter();

    public abstract void releaseSession(RoutingSessionInfo routingSessionInfo);

    public abstract void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo);

    public abstract void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i);

    public abstract void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i);

    public abstract void startScanOnRouter();

    public abstract void stopScanOnRouter();

    public abstract void transferToRoute(MediaRoute2Info mediaRoute2Info);

    public abstract void unregisterRouter();
}
