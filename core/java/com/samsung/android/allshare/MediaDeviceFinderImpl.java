package com.samsung.android.allshare;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Looper;
import android.sec.clipboard.data.ClipboardConstants;

import com.samsung.android.allshare.media.MediaDeviceFinder;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes3.dex */
final class MediaDeviceFinderImpl extends MediaDeviceFinder {
    private static final String TAG_CLASS = "MediaDeviceFinderImpl";
    private static HashMap<String, Device.DeviceType> mDeviceEventToDeviceTypeMap;
    private static HashMap<Device.DeviceType, String> mDeviceTypeToEventMap;
    private IAllShareConnector mAllShareConnector;
    private AllShareEventHandler mEventHandler;
    private HashMap<String, DeviceFinder.IDeviceFinderEventListener> mDiscoveryListenerMap =
            new HashMap<>();
    private HashMap<String, AVPlayerImpl> mAVPlayerMap = new HashMap<>();
    private HashMap<String, ImageViewerImpl> mImageViewerMap = new HashMap<>();
    private HashMap<String, ScreenSharingDeviceImpl> mScreenSharingDeviceMap = new HashMap<>();
    private HashMap<String, DeviceImpl> mUnknownDeviceMap = new HashMap<>();

    static {
        mDeviceTypeToEventMap = null;
        mDeviceTypeToEventMap = new HashMap<>();
        mDeviceTypeToEventMap.put(
                Device.DeviceType.DEVICE_PROVIDER, AllShareEvent.EVENT_PROVIDER_DISCOVERY);
        mDeviceTypeToEventMap.put(
                Device.DeviceType.DEVICE_AVPLAYER, AllShareEvent.EVENT_AV_PLAYER_DISCOVERY);
        mDeviceTypeToEventMap.put(
                Device.DeviceType.DEVICE_IMAGEVIEWER, AllShareEvent.EVENT_IMAGE_VIEWER_DISCOVERY);
        mDeviceTypeToEventMap.put(
                Device.DeviceType.DEVICE_SCREENSHARING,
                AllShareEvent.EVENT_SCREENSHARING_DISCOVERY);
        mDeviceTypeToEventMap.put(Device.DeviceType.UNKNOWN, AllShareEvent.EVENT_DMR_DISCOVERY);
        mDeviceEventToDeviceTypeMap = null;
        mDeviceEventToDeviceTypeMap = new HashMap<>();
        mDeviceEventToDeviceTypeMap.put(
                AllShareEvent.EVENT_PROVIDER_DISCOVERY, Device.DeviceType.DEVICE_PROVIDER);
        mDeviceEventToDeviceTypeMap.put(
                AllShareEvent.EVENT_AV_PLAYER_DISCOVERY, Device.DeviceType.DEVICE_AVPLAYER);
        mDeviceEventToDeviceTypeMap.put(
                AllShareEvent.EVENT_IMAGE_VIEWER_DISCOVERY, Device.DeviceType.DEVICE_IMAGEVIEWER);
        mDeviceEventToDeviceTypeMap.put(
                AllShareEvent.EVENT_SCREENSHARING_DISCOVERY,
                Device.DeviceType.DEVICE_SCREENSHARING);
        mDeviceEventToDeviceTypeMap.put(
                AllShareEvent.EVENT_DMR_DISCOVERY, Device.DeviceType.UNKNOWN);
    }

    MediaDeviceFinderImpl(IAllShareConnector connector) {
        this.mAllShareConnector = null;
        if (connector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = connector;
            this.mEventHandler =
                    new AllShareEventHandlerForMediaDevice(ServiceConnector.getMainLooper(), this);
        }
    }

    static class AllShareEventHandlerForMediaDevice extends AllShareEventHandler {
        private WeakReference<MediaDeviceFinderImpl> mWeakRef;

        public AllShareEventHandlerForMediaDevice(Looper looper, MediaDeviceFinderImpl mdfi) {
            super(looper);
            this.mWeakRef = new WeakReference<>(mdfi);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
            String evt_id = cvm.getEventID();
            DeviceFinder.IDeviceFinderEventListener listener = null;
            MediaDeviceFinderImpl mdfi = this.mWeakRef.get();
            if (mdfi == null) {
                DLog.w_api(
                        MediaDeviceFinderImpl.TAG_CLASS,
                        "mEventHandler.handleEventMessage : mWeakRef is null");
                return;
            }
            try {
                listener =
                        (DeviceFinder.IDeviceFinderEventListener)
                                mdfi.mDiscoveryListenerMap.get(evt_id);
            } catch (Exception e) {
                DLog.w_api(
                        MediaDeviceFinderImpl.TAG_CLASS,
                        "mEventHandler.handleEventMessage : Exception",
                        e);
            }
            Device.DeviceType deviceType =
                    (Device.DeviceType)
                            MediaDeviceFinderImpl.mDeviceEventToDeviceTypeMap.get(evt_id);
            Bundle msgBundle = cvm.getBundle();
            String eventType = msgBundle.getString(AllShareKey.BUNDLE_STRING_TYPE);
            Bundle deviceBundle =
                    (Bundle) msgBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE);
            if (deviceBundle == null) {
                DLog.w_api(
                        MediaDeviceFinderImpl.TAG_CLASS,
                        "mEventHandler.handleEventMessage : deviceBundle is null");
                return;
            }
            Device device = mdfi.getDeviceFromMap(deviceBundle, deviceType);
            if (device == null) {
                DLog.w_api(
                        MediaDeviceFinderImpl.TAG_CLASS,
                        "mEventHandler.handleEventMessage : device is null");
                return;
            }
            if (ClipboardConstants.USER_ADDED.equals(eventType)) {
                if (listener != null) {
                    try {
                        listener.onDeviceAdded(deviceType, device, ERROR.SUCCESS);
                        DLog.i_api(MediaDeviceFinderImpl.TAG_CLASS, "[ADDED] " + device);
                        return;
                    } catch (Error err) {
                        DLog.w_api(MediaDeviceFinderImpl.TAG_CLASS, "[ADDED] Error", err);
                        return;
                    } catch (Exception e2) {
                        DLog.w_api(MediaDeviceFinderImpl.TAG_CLASS, "[ADDED] Exception", e2);
                        return;
                    }
                }
                return;
            }
            if (!ClipboardConstants.USER_REMOVED.equals(eventType)) {
                DLog.w_api(
                        MediaDeviceFinderImpl.TAG_CLASS,
                        "mEventHandler.handleEventMessage : eventType=" + eventType);
                return;
            }
            try {
                mdfi.removeDeviceFromMap(deviceBundle, deviceType);
                ERROR error = ERROR.stringToEnum(msgBundle.getString("BUNDLE_ENUM_ERROR"));
                if (listener != null) {
                    listener.onDeviceRemoved(deviceType, device, error);
                    DLog.i_api(MediaDeviceFinderImpl.TAG_CLASS, "[REMOVED] " + device);
                }
            } catch (Error err2) {
                DLog.w_api(MediaDeviceFinderImpl.TAG_CLASS, "[REMOVED] Exception", err2);
            } catch (Exception e3) {
                DLog.w_api(MediaDeviceFinderImpl.TAG_CLASS, "[REMOVED] Exception", e3);
            }
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final void refresh() {
        DLog.i_api(TAG_CLASS, "refresh");
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "refresh : mAllShareConnector is null");
            return;
        }
        String applicationID = "";
        Context ctx = ServiceConnector.getContext();
        if (ctx != null && (applicationID = ctx.getPackageName()) == null) {
            applicationID = "";
        }
        SyncActionInvoker builder =
                new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REFRESH);
        builder.putString("BUNDLE_STRING_ID", applicationID);
        builder.invoke();
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void refresh(Device.DeviceType type) {
        DLog.i_api(TAG_CLASS, "refresh(" + type + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "refresh(" + type + ") : mAllShareConnector is null");
            return;
        }
        SyncActionInvoker builder =
                new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REFRESH_TARGET);
        builder.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, String.valueOf(type));
        builder.invoke();
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void setDeviceFinderEventListener(
            Device.DeviceType deviceType, DeviceFinder.IDeviceFinderEventListener l) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        if (deviceType == null) {
            DLog.w_api(TAG_CLASS, "setEventListener error! deviceType is null");
            return;
        }
        String deviceTypeEvent = mDeviceTypeToEventMap.get(deviceType);
        if (deviceTypeEvent == null) {
            DLog.w_api(TAG_CLASS, "setEventListener error! deviceTypeEvent is null");
            return;
        }
        DeviceFinder.IDeviceFinderEventListener oldListener =
                this.mDiscoveryListenerMap.get(deviceTypeEvent);
        this.mDiscoveryListenerMap.put(deviceTypeEvent, l);
        if (oldListener == null && l != null) {
            this.mAllShareConnector.subscribeAllShareEvent(
                    deviceTypeEvent, null, this.mEventHandler);
        } else if (oldListener != null && l == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(
                    deviceTypeEvent, null, this.mEventHandler);
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final ArrayList<Device> getDevices(Device.DeviceType deviceType, String NIC) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            return new ArrayList<>();
        }
        DLog.i_api(
                TAG_CLASS,
                "getDevices - type["
                        + deviceType
                        + "], NIC["
                        + NIC
                        + NavigationBarInflaterView.SIZE_MOD_END);
        return privateGetDevices(
                AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_TYPE_IFACE_SYNC,
                null,
                deviceType,
                NIC);
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final ArrayList<Device> getDevices(
            Device.DeviceDomain domain, Device.DeviceType deviceType) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            return new ArrayList<>();
        }
        DLog.i_api(
                TAG_CLASS,
                "getDevices - type["
                        + deviceType
                        + "], domain["
                        + domain
                        + NavigationBarInflaterView.SIZE_MOD_END);
        return privateGetDevices(
                AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_DOMAIN_SYNC,
                domain,
                deviceType,
                null);
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final ArrayList<Device> getDevices(Device.DeviceType deviceType) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            return new ArrayList<>();
        }
        DLog.i_api(
                TAG_CLASS,
                "getDevices - type[" + deviceType + NavigationBarInflaterView.SIZE_MOD_END);
        return privateGetDevices(
                AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_SYNC, null, deviceType, null);
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public final Device getDevice(String id, Device.DeviceType deviceType) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()
                || id == null
                || id.isEmpty()
                || deviceType == null) {
            return null;
        }
        SyncActionInvoker builder =
                new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICE_BY_ID_SYNC);
        builder.putString("BUNDLE_STRING_ID", id);
        builder.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
        Bundle device_bundle = builder.invoke();
        if (device_bundle == null) {
            return null;
        }
        Bundle req_bundle =
                (Bundle) device_bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE);
        return getDeviceFromMap(req_bundle, deviceType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDeviceFromMap(Bundle bundle, Device.DeviceType type) {
        if (bundle == null) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : bundle is null");
            return;
        }
        String id = bundle.getString("BUNDLE_STRING_ID");
        if (id == null) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : id is null");
            return;
        }
        if (id.isEmpty()) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : id is Empty");
            return;
        }
        try {
            switch (type) {
                case DEVICE_AVPLAYER:
                    AVPlayerImpl av = this.mAVPlayerMap.get(id);
                    if (av == null) {
                        DLog.w_api(TAG_CLASS, "cannot get AVPlayer with id: " + id);
                        break;
                    } else {
                        av.removeEventHandler();
                        this.mAVPlayerMap.remove(id);
                        break;
                    }
                case DEVICE_IMAGEVIEWER:
                    ImageViewerImpl iv = this.mImageViewerMap.get(id);
                    if (iv == null) {
                        DLog.w_api(TAG_CLASS, "cannot get ImageViewer with id: " + id);
                        break;
                    } else {
                        iv.removeEventHandler();
                        this.mImageViewerMap.remove(id);
                        break;
                    }
                case DEVICE_SCREENSHARING:
                    ScreenSharingDeviceImpl kd = this.mScreenSharingDeviceMap.get(id);
                    if (kd == null) {
                        DLog.w_api(TAG_CLASS, "cannot get ScreenSharingDevice with id: " + id);
                        break;
                    } else {
                        kd.removeEventHandler();
                        this.mScreenSharingDeviceMap.remove(id);
                        break;
                    }
                case UNKNOWN:
                    DeviceImpl deviceImpl = this.mUnknownDeviceMap.get(id);
                    if (deviceImpl == null) {
                        DLog.w_api(TAG_CLASS, "cannot get Device(UNKNOWN) with id: " + id);
                        break;
                    } else {
                        deviceImpl.removeEventHandler();
                        this.mUnknownDeviceMap.remove(id);
                        break;
                    }
            }
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "removeDeviceFromMap : Exception", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Device getDeviceFromMap(Bundle bundle, Device.DeviceType type) {
        DeviceImpl deviceImpl;
        if (bundle == null) {
            DLog.w_api(TAG_CLASS, "getDeviceFromMap : bundle is null");
            return null;
        }
        String id = bundle.getString("BUNDLE_STRING_ID");
        if (id == null || id.isEmpty()) {
            DLog.w_api(TAG_CLASS, "getDeviceFromMap : id is null");
            return null;
        }
        DLog.i_api(TAG_CLASS, "Getting device from maps id: " + id + " with type: " + type);
        try {
            deviceImpl = new DeviceImpl(bundle);
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "getDeviceFromMap : Exception", e);
        }
        switch (type) {
            case DEVICE_AVPLAYER:
                if (!this.mAVPlayerMap.containsKey(id)) {
                    AVPlayerImpl avPlayer = new AVPlayerImpl(this.mAllShareConnector, deviceImpl);
                    this.mAVPlayerMap.put(id, avPlayer);
                }
                return this.mAVPlayerMap.get(id);
            case DEVICE_IMAGEVIEWER:
                if (!this.mImageViewerMap.containsKey(id)) {
                    ImageViewerImpl imageViewer =
                            new ImageViewerImpl(this.mAllShareConnector, deviceImpl);
                    this.mImageViewerMap.put(id, imageViewer);
                }
                return this.mImageViewerMap.get(id);
            case DEVICE_SCREENSHARING:
                if (!this.mScreenSharingDeviceMap.containsKey(id)) {
                    ScreenSharingDeviceImpl upnpDevice =
                            new ScreenSharingDeviceImpl(this.mAllShareConnector, deviceImpl);
                    this.mScreenSharingDeviceMap.put(id, upnpDevice);
                }
                return this.mScreenSharingDeviceMap.get(id);
            case UNKNOWN:
                if (!this.mUnknownDeviceMap.containsKey(id)) {
                    if (!deviceImpl.isSupportedByType(1)
                            && !deviceImpl.isSupportedByType(3)
                            && !deviceImpl.isSupportedByType(2)) {
                        DLog.w_api(TAG_CLASS, "all types are not supported");
                    }
                    this.mUnknownDeviceMap.put(id, deviceImpl);
                }
                return this.mUnknownDeviceMap.get(id);
            default:
                return null;
        }
    }

    private ArrayList<Device> privateGetDevices(
            String action,
            Device.DeviceDomain domain,
            Device.DeviceType deviceType,
            String deviceIface) {
        ArrayList<Device> result = new ArrayList<>();
        if (deviceType == null) {
            return result;
        }
        SyncActionInvoker builder = new SyncActionInvoker(action);
        if (action.equals(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_DOMAIN_SYNC)
                && domain != null) {
            builder.putString(AllShareKey.BUNDLE_ENUM_DEVICE_DOMAIN, domain.enumToString());
            builder.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
        } else if (action.equals(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_TYPE_IFACE_SYNC)
                && deviceIface != null
                && deviceIface.length() > 0) {
            builder.putString(AllShareKey.BUNDLE_STRING_BOUND_INTERFACE, deviceIface);
            builder.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
        } else if (action.equals(AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_SYNC)) {
            builder.putString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE, deviceType.enumToString());
        } else {
            return result;
        }
        Bundle resBundle = builder.invoke();
        if (resBundle == null) {
            return result;
        }
        ArrayList<Bundle> devices =
                resBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_DEVICE);
        if (devices == null || devices.size() == 0) {
            DLog.d_api(TAG_CLASS, "device list is empty or null!");
            return result;
        }
        DLog.d_api(TAG_CLASS, "Returned bundle list size: " + devices.size());
        Iterator<Bundle> it = devices.iterator();
        while (it.hasNext()) {
            Bundle b = it.next();
            Device d = getDeviceFromMap(b, deviceType);
            if (d == null) {
                DLog.d_api(TAG_CLASS, "device not present in any map");
            } else {
                result.add(d);
                DLog.d_api(TAG_CLASS, "add " + d + " to result");
            }
        }
        return result;
    }

    private class SyncActionInvoker {
        private CVMessage mMessage;

        private SyncActionInvoker() {
            this.mMessage = new CVMessage();
        }

        private SyncActionInvoker(String action_id) {
            this.mMessage = new CVMessage();
            this.mMessage.setActionID(action_id);
        }

        void putString(String key, String value) {
            this.mMessage.getBundle().putString(key, value);
        }

        void putStringArrayList(String key, ArrayList<String> value) {
            this.mMessage.getBundle().putStringArrayList(key, value);
        }

        Bundle invoke() {
            CVMessage resMessage;
            if (MediaDeviceFinderImpl.this.mAllShareConnector == null
                    || !MediaDeviceFinderImpl.this.mAllShareConnector.isAllShareServiceConnected()
                    || (resMessage =
                                    MediaDeviceFinderImpl.this.mAllShareConnector.requestCVMSync(
                                            this.mMessage))
                            == null) {
                return null;
            }
            return resMessage.getBundle();
        }
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void registerSearchTarget(ArrayList<Device.DeviceType> deviceTypeList) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()
                || deviceTypeList == null) {
            return;
        }
        String applicationID = "";
        Context ctx = ServiceConnector.getContext();
        if (ctx != null && (applicationID = ctx.getPackageName()) == null) {
            applicationID = "";
        }
        ArrayList<String> devTypeList = new ArrayList<>();
        Iterator<Device.DeviceType> it = deviceTypeList.iterator();
        while (it.hasNext()) {
            Device.DeviceType devType = it.next();
            devTypeList.add(devType.enumToString());
        }
        SyncActionInvoker builder =
                new SyncActionInvoker(
                        AllShareAction.ACTION_DEVICE_FINDER_REGISTER_SEARCH_TARGET_SYNC);
        builder.putString("BUNDLE_STRING_ID", applicationID);
        builder.putStringArrayList(
                AllShareKey.BUNDLE_STRINGARRAYLIST_DEVICE_TYPE_LIST, devTypeList);
        builder.invoke();
    }

    @Override // com.samsung.android.allshare.DeviceFinder
    public void unregisterSearchTarget(ArrayList<Device.DeviceType> deviceTypeList) {
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()
                || deviceTypeList == null) {
            return;
        }
        String applicationID = "";
        Context ctx = ServiceConnector.getContext();
        if (ctx != null && (applicationID = ctx.getPackageName()) == null) {
            applicationID = "";
        }
        ArrayList<String> devTypeList = new ArrayList<>();
        Iterator<Device.DeviceType> it = deviceTypeList.iterator();
        while (it.hasNext()) {
            Device.DeviceType devType = it.next();
            devTypeList.add(devType.enumToString());
        }
        SyncActionInvoker builder =
                new SyncActionInvoker(
                        AllShareAction.ACTION_DEVICE_FINDER_UNREGISTER_SEARCH_TARGET_SYNC);
        builder.putString("BUNDLE_STRING_ID", applicationID);
        builder.putStringArrayList(
                AllShareKey.BUNDLE_STRINGARRAYLIST_DEVICE_TYPE_LIST, devTypeList);
        builder.invoke();
    }

    public void cleanup() {
        this.mDiscoveryListenerMap.clear();
        this.mDiscoveryListenerMap = null;
        this.mAVPlayerMap.clear();
        this.mAVPlayerMap = null;
        this.mImageViewerMap.clear();
        this.mImageViewerMap = null;
        this.mScreenSharingDeviceMap.clear();
        this.mScreenSharingDeviceMap = null;
        this.mUnknownDeviceMap.clear();
        this.mUnknownDeviceMap = null;
        this.mEventHandler = null;
        this.mAllShareConnector = null;
    }
}
