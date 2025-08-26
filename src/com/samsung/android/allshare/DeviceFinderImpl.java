package com.samsung.android.allshare;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.sec.clipboard.data.ClipboardConstants;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.DeviceFinder;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes6.dex */
final class DeviceFinderImpl extends DeviceFinder {
    private static final String TAG_CLASS = "DeviceFinderImpl(v1)";
    private static HashMap<String, Device.DeviceType> mDeviceEventToDeviceTypeMap;
    private static HashMap<Device.DeviceType, String> mDeviceTypeToEventMap;
    private IAllShareConnector mAllShareConnector;
    private HashMap<String, DeviceFinder.IDeviceFinderEventListener> mDiscoveryListenerMap = new HashMap<>();
    private HashMap<String, AVPlayerImpl> mAVPlayerMap = new HashMap<>();
    private HashMap<String, ImageViewerImpl> mImageViewerMap = new HashMap<>();
    private HashMap<String, ScreenSharingDeviceImpl> mScreenSharingDeviceMap = new HashMap<>();
    private HashMap<String, DeviceImpl> mUnknownDeviceMap = new HashMap<>();
    private AllShareEventHandler mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.DeviceFinderImpl.1
        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cVMessage) {
            DeviceFinder.IDeviceFinderEventListener iDeviceFinderEventListener;
            String eventID = cVMessage.getEventID();
            try {
                iDeviceFinderEventListener = (DeviceFinder.IDeviceFinderEventListener) DeviceFinderImpl.this.mDiscoveryListenerMap.get(eventID);
            } catch (Exception e) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "mEventHandler.handleEventMessage : Exception", e);
                iDeviceFinderEventListener = null;
            }
            Device.DeviceType deviceType = (Device.DeviceType) DeviceFinderImpl.mDeviceEventToDeviceTypeMap.get(eventID);
            Bundle bundle = cVMessage.getBundle();
            String string = bundle.getString(AllShareKey.BUNDLE_STRING_TYPE);
            Bundle bundle2 = (Bundle) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE);
            if (bundle2 == null) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "mEventHandler.handleEventMessage : deviceBundle is null");
                return;
            }
            Device deviceFromMap = DeviceFinderImpl.this.getDeviceFromMap(bundle2, deviceType);
            if (deviceFromMap == null) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "mEventHandler.handleEventMessage : device is null");
                return;
            }
            if (ClipboardConstants.USER_ADDED.equals(string)) {
                if (iDeviceFinderEventListener != null) {
                    try {
                        iDeviceFinderEventListener.onDeviceAdded(deviceType, deviceFromMap, ERROR.SUCCESS);
                        DLog.i_api(DeviceFinderImpl.TAG_CLASS, "[ADDED] " + deviceFromMap);
                        return;
                    } catch (Error e2) {
                        DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[ADDED] Error", e2);
                        return;
                    } catch (Exception e3) {
                        DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[ADDED] Exception", e3);
                        return;
                    }
                }
                return;
            }
            if (!ClipboardConstants.USER_REMOVED.equals(string)) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "mEventHandler.handleEventMessage : eventType=" + string);
                return;
            }
            try {
                DeviceFinderImpl.this.removeDeviceFromMap(bundle2, deviceType);
                ERROR errorStringToEnum = ERROR.stringToEnum(bundle.getString("BUNDLE_ENUM_ERROR"));
                if (iDeviceFinderEventListener != null) {
                    iDeviceFinderEventListener.onDeviceRemoved(deviceType, deviceFromMap, errorStringToEnum);
                    DLog.i_api(DeviceFinderImpl.TAG_CLASS, "[REMOVED] " + deviceFromMap);
                }
            } catch (Error e4) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[REMOVED] Exception", e4);
            } catch (Exception e5) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[REMOVED] Exception", e5);
            }
        }
    };

    static {
        HashMap<Device.DeviceType, String> map = new HashMap<>();
        mDeviceTypeToEventMap = map;
        map.put(Device.DeviceType.DEVICE_PROVIDER, AllShareEvent.EVENT_PROVIDER_DISCOVERY);
        mDeviceTypeToEventMap.put(Device.DeviceType.DEVICE_AVPLAYER, AllShareEvent.EVENT_AV_PLAYER_DISCOVERY);
        mDeviceTypeToEventMap.put(Device.DeviceType.DEVICE_IMAGEVIEWER, AllShareEvent.EVENT_IMAGE_VIEWER_DISCOVERY);
        mDeviceTypeToEventMap.put(Device.DeviceType.DEVICE_FILERECEIVER, AllShareEvent.EVENT_FILERECEIVER_DISCOVERY);
        mDeviceTypeToEventMap.put(Device.DeviceType.DEVICE_SCREENSHARING, AllShareEvent.EVENT_SCREENSHARING_DISCOVERY);
        mDeviceTypeToEventMap.put(Device.DeviceType.UNKNOWN, AllShareEvent.EVENT_DMR_DISCOVERY);
        mDeviceEventToDeviceTypeMap = null;
        HashMap<String, Device.DeviceType> map2 = new HashMap<>();
        mDeviceEventToDeviceTypeMap = map2;
        map2.put(AllShareEvent.EVENT_PROVIDER_DISCOVERY, Device.DeviceType.DEVICE_PROVIDER);
        mDeviceEventToDeviceTypeMap.put(AllShareEvent.EVENT_AV_PLAYER_DISCOVERY, Device.DeviceType.DEVICE_AVPLAYER);
        mDeviceEventToDeviceTypeMap.put(AllShareEvent.EVENT_IMAGE_VIEWER_DISCOVERY, Device.DeviceType.DEVICE_IMAGEVIEWER);
        mDeviceEventToDeviceTypeMap.put(AllShareEvent.EVENT_FILERECEIVER_DISCOVERY, Device.DeviceType.DEVICE_FILERECEIVER);
        mDeviceEventToDeviceTypeMap.put(AllShareEvent.EVENT_SCREENSHARING_DISCOVERY, Device.DeviceType.DEVICE_SCREENSHARING);
        mDeviceEventToDeviceTypeMap.put(AllShareEvent.EVENT_DMR_DISCOVERY, Device.DeviceType.UNKNOWN);
    }

    DeviceFinderImpl(IAllShareConnector iAllShareConnector) {
        this.mAllShareConnector = null;
        if (iAllShareConnector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = iAllShareConnector;
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final void refresh() {
        String packageName;
        DLog.i_api(TAG_CLASS, "refresh");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "refresh : mAllShareConnector is null");
            return;
        }
        Context context = ServiceConnector.getContext();
        String str = "";
        if (context != null && (packageName = context.getPackageName()) != null) {
            str = packageName;
        }
        SyncActionInvoker syncActionInvoker = new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REFRESH);
        syncActionInvoker.putString("BUNDLE_STRING_ID", str);
        syncActionInvoker.invoke();
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void refresh(Device.DeviceType deviceType) {
        DLog.i_api(TAG_CLASS, "refresh(" + deviceType + NavigationBarInflaterView.KEY_CODE_END);
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "refresh(" + deviceType + ") : mAllShareConnector is null");
            return;
        }
        SyncActionInvoker syncActionInvoker = new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REFRESH_TARGET);
        syncActionInvoker.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, String.valueOf(deviceType));
        syncActionInvoker.invoke();
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void setDeviceFinderEventListener(Device.DeviceType deviceType, DeviceFinder.IDeviceFinderEventListener iDeviceFinderEventListener) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        if (deviceType == null) {
            DLog.w_api(TAG_CLASS, "setEventListener error! deviceType is null");
            return;
        }
        String str = mDeviceTypeToEventMap.get(deviceType);
        if (str == null) {
            DLog.w_api(TAG_CLASS, "setEventListener error! deviceTypeEvent is null");
            return;
        }
        DeviceFinder.IDeviceFinderEventListener iDeviceFinderEventListener2 = this.mDiscoveryListenerMap.get(str);
        this.mDiscoveryListenerMap.put(str, iDeviceFinderEventListener);
        if (iDeviceFinderEventListener2 == null && iDeviceFinderEventListener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(str, null, this.mEventHandler);
        } else {
            if (iDeviceFinderEventListener2 == null || iDeviceFinderEventListener != null) {
                return;
            }
            this.mAllShareConnector.unsubscribeAllShareEvent(str, null, this.mEventHandler);
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final ArrayList<Device> getDevices(Device.DeviceType deviceType, String str) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return new ArrayList<>();
        }
        DLog.i_api(TAG_CLASS, "getDevices - type[" + deviceType + "], NIC[" + str + NavigationBarInflaterView.SIZE_MOD_END);
        return privateGetDevices(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_TYPE_IFACE_SYNC, null, deviceType, str);
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final ArrayList<Device> getDevices(Device.DeviceDomain deviceDomain, Device.DeviceType deviceType) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return new ArrayList<>();
        }
        DLog.i_api(TAG_CLASS, "getDevices - type[" + deviceType + "], domain[" + deviceDomain + NavigationBarInflaterView.SIZE_MOD_END);
        return privateGetDevices(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_DOMAIN_SYNC, deviceDomain, deviceType, null);
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final ArrayList<Device> getDevices(Device.DeviceType deviceType) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return new ArrayList<>();
        }
        DLog.i_api(TAG_CLASS, "getDevices - type[" + deviceType + NavigationBarInflaterView.SIZE_MOD_END);
        return privateGetDevices(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_SYNC, null, deviceType, null);
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final Device getDevice(String str, Device.DeviceType deviceType) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector) || str == null || str.isEmpty() || deviceType == null) {
            return null;
        }
        SyncActionInvoker syncActionInvoker = new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICE_BY_ID_SYNC);
        syncActionInvoker.putString("BUNDLE_STRING_ID", str);
        syncActionInvoker.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
        Bundle bundleInvoke = syncActionInvoker.invoke();
        if (bundleInvoke == null) {
            return null;
        }
        return getDeviceFromMap((Bundle) bundleInvoke.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE), deviceType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDeviceFromMap(Bundle bundle, Device.DeviceType deviceType) {
        if (bundle == null) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : bundle is null");
            return;
        }
        String string = bundle.getString("BUNDLE_STRING_ID");
        if (string == null || string.isEmpty()) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : id is Empty");
            return;
        }
        try {
            int i = AnonymousClass2.$SwitchMap$com$samsung$android$allshare$Device$DeviceType[deviceType.ordinal()];
            if (i == 1) {
                AVPlayerImpl aVPlayerImpl = this.mAVPlayerMap.get(string);
                if (aVPlayerImpl != null) {
                    aVPlayerImpl.removeEventHandler();
                    this.mAVPlayerMap.remove(string);
                    return;
                } else {
                    DLog.w_api(TAG_CLASS, "cannot get AVPlayer with id: " + string);
                    return;
                }
            }
            if (i == 2) {
                ImageViewerImpl imageViewerImpl = this.mImageViewerMap.get(string);
                if (imageViewerImpl != null) {
                    imageViewerImpl.removeEventHandler();
                    this.mImageViewerMap.remove(string);
                    return;
                } else {
                    DLog.w_api(TAG_CLASS, "cannot get ImageViewer with id: " + string);
                    return;
                }
            }
            if (i == 3) {
                ScreenSharingDeviceImpl screenSharingDeviceImpl = this.mScreenSharingDeviceMap.get(string);
                if (screenSharingDeviceImpl != null) {
                    screenSharingDeviceImpl.removeEventHandler();
                    this.mScreenSharingDeviceMap.remove(string);
                    return;
                } else {
                    DLog.w_api(TAG_CLASS, "cannot get ScreenSharingDevice with id: " + string);
                    return;
                }
            }
            if (i != 4) {
                return;
            }
            DeviceImpl deviceImpl = this.mUnknownDeviceMap.get(string);
            if (deviceImpl != null) {
                deviceImpl.removeEventHandler();
                this.mUnknownDeviceMap.remove(string);
            } else {
                DLog.w_api(TAG_CLASS, "cannot get Device(UNKNOWN) with id: " + string);
            }
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : Exception", e);
        }
    }

    /* renamed from: com.samsung.android.allshare.DeviceFinderImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Device$DeviceType;

        static {
            int[] iArr = new int[Device.DeviceType.values().length];
            $SwitchMap$com$samsung$android$allshare$Device$DeviceType = iArr;
            try {
                iArr[Device.DeviceType.DEVICE_AVPLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Device$DeviceType[Device.DeviceType.DEVICE_IMAGEVIEWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Device$DeviceType[Device.DeviceType.DEVICE_SCREENSHARING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Device$DeviceType[Device.DeviceType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Device getDeviceFromMap(Bundle bundle, Device.DeviceType deviceType) {
        DeviceImpl deviceImpl;
        int i;
        if (bundle == null) {
            DLog.w_api(TAG_CLASS, "getDeviceFromMap : bundle is null");
            return null;
        }
        String string = bundle.getString("BUNDLE_STRING_ID");
        if (string == null || string.isEmpty()) {
            DLog.w_api(TAG_CLASS, "getDeviceFromMap : id is null");
            return null;
        }
        try {
            deviceImpl = new DeviceImpl(bundle);
            i = AnonymousClass2.$SwitchMap$com$samsung$android$allshare$Device$DeviceType[deviceType.ordinal()];
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "getDeviceFromMap : Exception", e);
        }
        if (i == 1) {
            if (!this.mAVPlayerMap.containsKey(string)) {
                this.mAVPlayerMap.put(string, new AVPlayerImpl(this.mAllShareConnector, deviceImpl));
            }
            return this.mAVPlayerMap.get(string);
        }
        if (i == 2) {
            if (!this.mImageViewerMap.containsKey(string)) {
                this.mImageViewerMap.put(string, new ImageViewerImpl(this.mAllShareConnector, deviceImpl));
            }
            return this.mImageViewerMap.get(string);
        }
        if (i == 3) {
            if (!this.mScreenSharingDeviceMap.containsKey(string)) {
                this.mScreenSharingDeviceMap.put(string, new ScreenSharingDeviceImpl(this.mAllShareConnector, deviceImpl));
            }
            return this.mScreenSharingDeviceMap.get(string);
        }
        if (i != 4) {
            return null;
        }
        if (!this.mUnknownDeviceMap.containsKey(string)) {
            if (!deviceImpl.isSupportedByType(1) && !deviceImpl.isSupportedByType(3) && !deviceImpl.isSupportedByType(2)) {
                DLog.w_api(TAG_CLASS, "all types are not supported");
            } else {
                this.mUnknownDeviceMap.put(string, deviceImpl);
            }
        }
        return this.mUnknownDeviceMap.get(string);
    }

    private ArrayList<Device> privateGetDevices(String str, Device.DeviceDomain deviceDomain, Device.DeviceType deviceType, String str2) {
        ArrayList<Device> arrayList = new ArrayList<>();
        if (deviceType != null) {
            SyncActionInvoker syncActionInvoker = new SyncActionInvoker(str);
            if (str.equals(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_DOMAIN_SYNC) && deviceDomain != null) {
                syncActionInvoker.putString(AllShareKey.BUNDLE_ENUM_DEVICE_DOMAIN, deviceDomain.enumToString());
                syncActionInvoker.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
            } else if (str.equals(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_TYPE_IFACE_SYNC) && str2 != null && str2.length() > 0) {
                syncActionInvoker.putString(AllShareKey.BUNDLE_STRING_BOUND_INTERFACE, str2);
                syncActionInvoker.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
            } else if (str.equals(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_SYNC)) {
                syncActionInvoker.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
            }
            Bundle bundleInvoke = syncActionInvoker.invoke();
            if (bundleInvoke == null) {
                DLog.w_api(TAG_CLASS, "resBundle is null");
                return arrayList;
            }
            ArrayList parcelableArrayList = bundleInvoke.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_DEVICE);
            if (parcelableArrayList == null || parcelableArrayList.size() == 0) {
                DLog.w_api(TAG_CLASS, "devices.size is null or 0");
            } else {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    Device deviceFromMap = getDeviceFromMap((Bundle) it.next(), deviceType);
                    if (deviceFromMap == null) {
                        DLog.d_api(TAG_CLASS, "getDeviceFromMap is null");
                    } else {
                        arrayList.add(deviceFromMap);
                        DLog.d_api(TAG_CLASS, "add " + deviceFromMap + " to result");
                    }
                }
            }
        }
        return arrayList;
    }

    private class SyncActionInvoker {
        private CVMessage mMessage;

        private SyncActionInvoker() {
            this.mMessage = new CVMessage();
        }

        private SyncActionInvoker(String str) {
            CVMessage cVMessage = new CVMessage();
            this.mMessage = cVMessage;
            cVMessage.setActionID(str);
        }

        void putString(String str, String str2) {
            this.mMessage.getBundle().putString(str, str2);
        }

        void putStringArrayList(String str, ArrayList<String> arrayList) {
            this.mMessage.getBundle().putStringArrayList(str, arrayList);
        }

        Bundle invoke() {
            CVMessage cVMessageRequestCVMSync;
            if (ServiceConnectionChecker.isAllShareServiceConnected(DeviceFinderImpl.this.mAllShareConnector) && (cVMessageRequestCVMSync = DeviceFinderImpl.this.mAllShareConnector.requestCVMSync(this.mMessage)) != null) {
                return cVMessageRequestCVMSync.getBundle();
            }
            return null;
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void registerSearchTarget(ArrayList<Device.DeviceType> arrayList) {
        String packageName;
        if (ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector) && arrayList != null) {
            Context context = ServiceConnector.getContext();
            String str = "";
            if (context != null && (packageName = context.getPackageName()) != null) {
                str = packageName;
            }
            ArrayList<String> arrayList2 = new ArrayList<>();
            Iterator<Device.DeviceType> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().enumToString());
            }
            SyncActionInvoker syncActionInvoker = new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REGISTER_SEARCH_TARGET_SYNC);
            syncActionInvoker.putString("BUNDLE_STRING_ID", str);
            syncActionInvoker.putStringArrayList(AllShareKey.BUNDLE_STRINGARRAYLIST_DEVICE_TYPE_LIST, arrayList2);
            syncActionInvoker.invoke();
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void unregisterSearchTarget(ArrayList<Device.DeviceType> arrayList) {
        String packageName;
        if (ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector) && arrayList != null) {
            Context context = ServiceConnector.getContext();
            String str = "";
            if (context != null && (packageName = context.getPackageName()) != null) {
                str = packageName;
            }
            ArrayList<String> arrayList2 = new ArrayList<>();
            Iterator<Device.DeviceType> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(it.next().enumToString());
            }
            SyncActionInvoker syncActionInvoker = new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_UNREGISTER_SEARCH_TARGET_SYNC);
            syncActionInvoker.putString("BUNDLE_STRING_ID", str);
            syncActionInvoker.putStringArrayList(AllShareKey.BUNDLE_STRINGARRAYLIST_DEVICE_TYPE_LIST, arrayList2);
            syncActionInvoker.invoke();
        }
    }
}
