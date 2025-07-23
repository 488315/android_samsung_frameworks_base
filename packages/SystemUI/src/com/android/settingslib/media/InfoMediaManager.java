package com.android.settingslib.media;

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
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.LocalMediaManager;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api34Impl {
        public static List<MediaRoute2Info> arrangeRouteListByPreference(List<MediaRoute2Info> list, List<MediaRoute2Info> list2, RouteListingPreference routeListingPreference) {
            List<RouteListingPreference.Item> composePreferenceRouteListing = composePreferenceRouteListing(routeListingPreference);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            boolean preferRouteListingOrdering = preferRouteListingOrdering(routeListingPreference);
            HashSet hashSet = new HashSet();
            if (preferRouteListingOrdering) {
                Iterator<MediaRoute2Info> it = list.iterator();
                while (it.hasNext()) {
                    hashSet.add(it.next().getId());
                }
                for (RouteListingPreference.Item item : composePreferenceRouteListing) {
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
            Iterator<RouteListingPreference.Item> it3 = composePreferenceRouteListing.iterator();
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
            boolean preferRouteListingOrdering = preferRouteListingOrdering(routeListingPreference);
            ArrayList arrayList = new ArrayList();
            for (RouteListingPreference.Item item : routeListingPreference.getItems()) {
                if (preferRouteListingOrdering || (item.getFlags() & 4) == 0) {
                    arrayList.add(item);
                } else {
                    arrayList.add(0, item);
                }
            }
            return arrayList;
        }

        public static synchronized List<MediaRoute2Info> filterDuplicatedIds(List<MediaRoute2Info> list) {
            ArrayList arrayList;
            synchronized (Api34Impl.class) {
                arrayList = new ArrayList();
                HashSet hashSet = new HashSet();
                for (MediaRoute2Info mediaRoute2Info : list) {
                    if (Collections.disjoint(mediaRoute2Info.getDeduplicationIds(), hashSet)) {
                        arrayList.add(mediaRoute2Info);
                        hashSet.addAll(mediaRoute2Info.getDeduplicationIds());
                    }
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            try {
                return new RouterInfoMediaManager(context2, str2, userHandle2, localBluetoothManager2, mediaController);
            } catch (PackageNotAvailableException unused) {
                Log.w("InfoMediaManager", "Returning a no-op InfoMediaManager for package " + str2);
                return new NoOpInfoMediaManager(context2, str2, userHandle2, localBluetoothManager2, mediaController);
            }
        } catch (PackageNotAvailableException unused2) {
            context2 = context;
            localBluetoothManager2 = localBluetoothManager;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addMediaDevice(android.media.MediaRoute2Info r6, android.media.RoutingSessionInfo r7) {
        /*
            r5 = this;
            int r7 = r6.getType()
            if (r7 == 0) goto Lb4
            r0 = 19
            r1 = 0
            if (r7 == r0) goto L9d
            r0 = 29
            if (r7 == r0) goto L9d
            r0 = 2000(0x7d0, float:2.803E-42)
            if (r7 == r0) goto Lb4
            r0 = 2
            if (r7 == r0) goto L9d
            r0 = 3
            if (r7 == r0) goto L9d
            r0 = 4
            if (r7 == r0) goto L9d
            r0 = 5
            if (r7 == r0) goto L9d
            r0 = 6
            if (r7 == r0) goto L9d
            r0 = 22
            if (r7 == r0) goto L9d
            r0 = 23
            java.lang.String r2 = "InfoMediaManager"
            if (r7 == r0) goto L58
            r0 = 25
            if (r7 == r0) goto L9d
            r0 = 26
            if (r7 == r0) goto L58
            switch(r7) {
                case 8: goto L58;
                case 9: goto L9d;
                case 10: goto L9d;
                case 11: goto L9d;
                case 12: goto L9d;
                case 13: goto L9d;
                default: goto L37;
            }
        L37:
            switch(r7) {
                case 1001: goto Lb4;
                case 1002: goto Lb4;
                case 1003: goto L41;
                case 1004: goto Lb4;
                case 1005: goto Lb4;
                case 1006: goto Lb4;
                case 1007: goto Lb4;
                case 1008: goto Lb4;
                case 1009: goto Lb4;
                case 1010: goto Lb4;
                default: goto L3a;
            }
        L3a:
            java.lang.String r6 = "addMediaDevice() unknown device type : "
            androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0.m(r7, r6, r2)
            goto Lc9
        L41:
            com.android.settingslib.media.ComplexMediaDevice r1 = new com.android.settingslib.media.ComplexMediaDevice
            android.content.Context r7 = r5.mContext
            java.util.Map r0 = r5.mPreferenceItemMap
            java.lang.String r2 = r6.getId()
            java.util.concurrent.ConcurrentHashMap r0 = (java.util.concurrent.ConcurrentHashMap) r0
            java.lang.Object r0 = r0.get(r2)
            android.media.RouteListingPreference$Item r0 = (android.media.RouteListingPreference.Item) r0
            r1.<init>(r7, r6, r0)
            goto Lc9
        L58:
            java.lang.String r7 = r6.getAddress()
            if (r7 != 0) goto L70
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "Ignoring bluetooth route with no set address: "
            r7.<init>(r0)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            android.util.Log.e(r2, r6)
            goto Lc9
        L70:
            android.bluetooth.BluetoothAdapter r7 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            java.lang.String r0 = r6.getAddress()
            android.bluetooth.BluetoothDevice r7 = r7.getRemoteDevice(r0)
            com.android.settingslib.bluetooth.LocalBluetoothManager r0 = r5.mBluetoothManager
            com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r0 = r0.mCachedDeviceManager
            com.android.settingslib.bluetooth.CachedBluetoothDevice r7 = r0.findDevice(r7)
            if (r7 == 0) goto Lc9
            com.android.settingslib.media.BluetoothMediaDevice r0 = new com.android.settingslib.media.BluetoothMediaDevice
            android.content.Context r2 = r5.mContext
            java.util.Map r3 = r5.mPreferenceItemMap
            java.lang.String r4 = r6.getId()
            java.util.concurrent.ConcurrentHashMap r3 = (java.util.concurrent.ConcurrentHashMap) r3
            java.lang.Object r1 = r3.getOrDefault(r4, r1)
            android.media.RouteListingPreference$Item r1 = (android.media.RouteListingPreference.Item) r1
            r0.<init>(r2, r7, r6, r1)
            r1 = r0
            goto Lc9
        L9d:
            com.android.settingslib.media.PhoneMediaDevice r7 = new com.android.settingslib.media.PhoneMediaDevice
            android.content.Context r0 = r5.mContext
            java.util.Map r2 = r5.mPreferenceItemMap
            java.lang.String r3 = r6.getId()
            java.util.concurrent.ConcurrentHashMap r2 = (java.util.concurrent.ConcurrentHashMap) r2
            java.lang.Object r1 = r2.getOrDefault(r3, r1)
            android.media.RouteListingPreference$Item r1 = (android.media.RouteListingPreference.Item) r1
            r7.<init>(r0, r6, r1)
            r1 = r7
            goto Lc9
        Lb4:
            com.android.settingslib.media.InfoMediaDevice r1 = new com.android.settingslib.media.InfoMediaDevice
            android.content.Context r7 = r5.mContext
            java.util.Map r0 = r5.mPreferenceItemMap
            java.lang.String r2 = r6.getId()
            java.util.concurrent.ConcurrentHashMap r0 = (java.util.concurrent.ConcurrentHashMap) r0
            java.lang.Object r0 = r0.get(r2)
            android.media.RouteListingPreference$Item r0 = (android.media.RouteListingPreference.Item) r0
            r1.<init>(r7, r6, r0)
        Lc9:
            if (r1 == 0) goto Ld2
            java.util.List r5 = r5.mMediaDevices
            java.util.concurrent.CopyOnWriteArrayList r5 = (java.util.concurrent.CopyOnWriteArrayList) r5
            r5.add(r1)
        Ld2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.InfoMediaManager.addMediaDevice(android.media.MediaRoute2Info, android.media.RoutingSessionInfo):void");
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
                            if (!packageManager.hasSystemFeature("android.hardware.type.television") && !packageManager.hasSystemFeature("android.software.leanback")) {
                                BluetoothMediaDevice mutingExpectedDevice = mediaDeviceCallback.getMutingExpectedDevice();
                                if (mutingExpectedDevice != null) {
                                    LocalMediaManager.this.mMediaDevices.add(mutingExpectedDevice);
                                }
                            }
                            LocalMediaManager.this.mMediaDevices.addAll(mediaDeviceCallback.buildDisconnectedBluetoothDevice());
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
