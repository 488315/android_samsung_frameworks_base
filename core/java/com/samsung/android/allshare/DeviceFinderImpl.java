package com.samsung.android.allshare;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.sec.clipboard.data.ClipboardConstants;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class DeviceFinderImpl extends DeviceFinder {
  private static final String TAG_CLASS = "DeviceFinderImpl(v1)";
  private static HashMap<String, Device.DeviceType> mDeviceEventToDeviceTypeMap;
  private static HashMap<Device.DeviceType, String> mDeviceTypeToEventMap;
  private IAllShareConnector mAllShareConnector;
  private HashMap<String, DeviceFinder.IDeviceFinderEventListener> mDiscoveryListenerMap =
      new HashMap<>();
  private HashMap<String, ProviderImpl> mProviderMap = new HashMap<>();
  private HashMap<String, AVPlayerImpl> mAVPlayerMap = new HashMap<>();
  private HashMap<String, ImageViewerImpl> mImageViewerMap = new HashMap<>();
  private HashMap<String, FileReceiverImpl> mFileReceiverMap = new HashMap<>();
  private HashMap<String, ScreenSharingDeviceImpl> mScreenSharingDeviceMap = new HashMap<>();
  private HashMap<String, DeviceImpl> mUnknownDeviceMap = new HashMap<>();
  private AllShareEventHandler mEventHandler =
      new AllShareEventHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.DeviceFinderImpl.1
        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
          String evt_id = cvm.getEventID();
          DeviceFinder.IDeviceFinderEventListener listener = null;
          try {
            listener =
                (DeviceFinder.IDeviceFinderEventListener)
                    DeviceFinderImpl.this.mDiscoveryListenerMap.get(evt_id);
          } catch (Exception e) {
            DLog.w_api(
                DeviceFinderImpl.TAG_CLASS, "mEventHandler.handleEventMessage : Exception", e);
          }
          Device.DeviceType deviceType =
              (Device.DeviceType) DeviceFinderImpl.mDeviceEventToDeviceTypeMap.get(evt_id);
          Bundle msgBundle = cvm.getBundle();
          String eventType = msgBundle.getString(AllShareKey.BUNDLE_STRING_TYPE);
          Bundle deviceBundle =
              (Bundle) msgBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE);
          if (deviceBundle == null) {
            DLog.w_api(
                DeviceFinderImpl.TAG_CLASS,
                "mEventHandler.handleEventMessage : deviceBundle is null");
            return;
          }
          Device device = DeviceFinderImpl.this.getDeviceFromMap(deviceBundle, deviceType);
          if (device == null) {
            DLog.w_api(
                DeviceFinderImpl.TAG_CLASS, "mEventHandler.handleEventMessage : device is null");
            return;
          }
          if (ClipboardConstants.USER_ADDED.equals(eventType)) {
            if (listener != null) {
              try {
                listener.onDeviceAdded(deviceType, device, ERROR.SUCCESS);
                DLog.i_api(DeviceFinderImpl.TAG_CLASS, "[ADDED] " + device);
                return;
              } catch (Error err) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[ADDED] Error", err);
                return;
              } catch (Exception e2) {
                DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[ADDED] Exception", e2);
                return;
              }
            }
            return;
          }
          if (ClipboardConstants.USER_REMOVED.equals(eventType)) {
            try {
              DeviceFinderImpl.this.removeDeviceFromMap(deviceBundle, deviceType);
              ERROR error = ERROR.stringToEnum(msgBundle.getString("BUNDLE_ENUM_ERROR"));
              if (listener != null) {
                listener.onDeviceRemoved(deviceType, device, error);
                DLog.i_api(DeviceFinderImpl.TAG_CLASS, "[REMOVED] " + device);
                return;
              }
              return;
            } catch (Error err2) {
              DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[REMOVED] Exception", err2);
              return;
            } catch (Exception e3) {
              DLog.w_api(DeviceFinderImpl.TAG_CLASS, "[REMOVED] Exception", e3);
              return;
            }
          }
          DLog.w_api(
              DeviceFinderImpl.TAG_CLASS,
              "mEventHandler.handleEventMessage : eventType=" + eventType);
        }
      };

  static {
    mDeviceTypeToEventMap = null;
    HashMap<Device.DeviceType, String> hashMap = new HashMap<>();
    mDeviceTypeToEventMap = hashMap;
    hashMap.put(Device.DeviceType.DEVICE_PROVIDER, AllShareEvent.EVENT_PROVIDER_DISCOVERY);
    mDeviceTypeToEventMap.put(
        Device.DeviceType.DEVICE_AVPLAYER, AllShareEvent.EVENT_AV_PLAYER_DISCOVERY);
    mDeviceTypeToEventMap.put(
        Device.DeviceType.DEVICE_IMAGEVIEWER, AllShareEvent.EVENT_IMAGE_VIEWER_DISCOVERY);
    mDeviceTypeToEventMap.put(
        Device.DeviceType.DEVICE_FILERECEIVER, AllShareEvent.EVENT_FILERECEIVER_DISCOVERY);
    mDeviceTypeToEventMap.put(
        Device.DeviceType.DEVICE_SCREENSHARING, AllShareEvent.EVENT_SCREENSHARING_DISCOVERY);
    mDeviceTypeToEventMap.put(Device.DeviceType.UNKNOWN, AllShareEvent.EVENT_DMR_DISCOVERY);
    mDeviceEventToDeviceTypeMap = null;
    HashMap<String, Device.DeviceType> hashMap2 = new HashMap<>();
    mDeviceEventToDeviceTypeMap = hashMap2;
    hashMap2.put(AllShareEvent.EVENT_PROVIDER_DISCOVERY, Device.DeviceType.DEVICE_PROVIDER);
    mDeviceEventToDeviceTypeMap.put(
        AllShareEvent.EVENT_AV_PLAYER_DISCOVERY, Device.DeviceType.DEVICE_AVPLAYER);
    mDeviceEventToDeviceTypeMap.put(
        AllShareEvent.EVENT_IMAGE_VIEWER_DISCOVERY, Device.DeviceType.DEVICE_IMAGEVIEWER);
    mDeviceEventToDeviceTypeMap.put(
        AllShareEvent.EVENT_FILERECEIVER_DISCOVERY, Device.DeviceType.DEVICE_FILERECEIVER);
    mDeviceEventToDeviceTypeMap.put(
        AllShareEvent.EVENT_SCREENSHARING_DISCOVERY, Device.DeviceType.DEVICE_SCREENSHARING);
    mDeviceEventToDeviceTypeMap.put(AllShareEvent.EVENT_DMR_DISCOVERY, Device.DeviceType.UNKNOWN);
  }

  DeviceFinderImpl(IAllShareConnector connector) {
    this.mAllShareConnector = null;
    if (connector == null) {
      DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
    } else {
      this.mAllShareConnector = connector;
    }
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public final void refresh() {
    DLog.i_api(TAG_CLASS, "refresh");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "refresh : mAllShareConnector is null");
      return;
    }
    String applicationID = "";
    Context ctx = ServiceConnector.getContext();
    if (ctx != null && (applicationID = ctx.getPackageName()) == null) {
      applicationID = "";
    }
    SyncActionInvoker builder = new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REFRESH);
    builder.putString("BUNDLE_STRING_ID", applicationID);
    builder.invoke();
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public void refresh(Device.DeviceType type) {
    DLog.i_api(TAG_CLASS, "refresh(" + type + NavigationBarInflaterView.KEY_CODE_END);
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
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
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
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
      this.mAllShareConnector.subscribeAllShareEvent(deviceTypeEvent, null, this.mEventHandler);
    } else if (oldListener != null && l == null) {
      this.mAllShareConnector.unsubscribeAllShareEvent(deviceTypeEvent, null, this.mEventHandler);
    }
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public final ArrayList<Device> getDevices(Device.DeviceType deviceType, String NIC) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
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
        AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_TYPE_IFACE_SYNC, null, deviceType, NIC);
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public final ArrayList<Device> getDevices(
      Device.DeviceDomain domain, Device.DeviceType deviceType) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
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
        AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_BY_DOMAIN_SYNC, domain, deviceType, null);
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public final ArrayList<Device> getDevices(Device.DeviceType deviceType) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return new ArrayList<>();
    }
    DLog.i_api(
        TAG_CLASS, "getDevices - type[" + deviceType + NavigationBarInflaterView.SIZE_MOD_END);
    return privateGetDevices(
        AllShareAction.ACTION_DEVICE_FINDER_GET_DEVICES_SYNC, null, deviceType, null);
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public final Device getDevice(String id, Device.DeviceType deviceType) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null
        || !iAllShareConnector.isAllShareServiceConnected()
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
    Bundle req_bundle = (Bundle) device_bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE);
    return getDeviceFromMap(req_bundle, deviceType);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void removeDeviceFromMap(Bundle bundle, Device.DeviceType type) {
    if (bundle == null) {
      DLog.w_api(TAG_CLASS, "removeDeviceFromMap : bundle is null");
      return;
    }
    String id = bundle.getString("BUNDLE_STRING_ID");
    if (id == null || id.isEmpty()) {
      DLog.w_api(TAG_CLASS, "removeDeviceFromMap : id is Empty");
      return;
    }
    try {
      switch (C48422.$SwitchMap$com$samsung$android$allshare$Device$DeviceType[type.ordinal()]) {
        case 1:
          AVPlayerImpl av = this.mAVPlayerMap.get(id);
          if (av == null) {
            DLog.w_api(TAG_CLASS, "cannot get AVPlayer with id: " + id);
            break;
          } else {
            av.removeEventHandler();
            this.mAVPlayerMap.remove(id);
            break;
          }
        case 2:
          ImageViewerImpl iv = this.mImageViewerMap.get(id);
          if (iv == null) {
            DLog.w_api(TAG_CLASS, "cannot get ImageViewer with id: " + id);
            break;
          } else {
            iv.removeEventHandler();
            this.mImageViewerMap.remove(id);
            break;
          }
        case 3:
          ProviderImpl p = this.mProviderMap.get(id);
          if (p == null) {
            DLog.w_api(TAG_CLASS, "cannot get Provider with id: " + id);
            break;
          } else {
            p.removeEventHandler();
            this.mProviderMap.remove(id);
            break;
          }
        case 4:
          FileReceiverImpl fr = this.mFileReceiverMap.get(id);
          if (fr == null) {
            DLog.w_api(TAG_CLASS, "cannot get FileReceiver with id: " + id);
            break;
          } else {
            fr.removeEventHandler();
            this.mFileReceiverMap.remove(id);
            break;
          }
        case 5:
          ScreenSharingDeviceImpl kd = this.mScreenSharingDeviceMap.get(id);
          if (kd == null) {
            DLog.w_api(TAG_CLASS, "cannot get ScreenSharingDevice with id: " + id);
            break;
          } else {
            kd.removeEventHandler();
            this.mScreenSharingDeviceMap.remove(id);
            break;
          }
        case 6:
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

  /* renamed from: com.samsung.android.allshare.DeviceFinderImpl$2 */
  static /* synthetic */ class C48422 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Device$DeviceType;

    static {
      int[] iArr = new int[Device.DeviceType.values().length];
      $SwitchMap$com$samsung$android$allshare$Device$DeviceType = iArr;
      try {
        iArr[Device.DeviceType.DEVICE_AVPLAYER.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Device$DeviceType[
                Device.DeviceType.DEVICE_IMAGEVIEWER.ordinal()] =
            2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Device$DeviceType[
                Device.DeviceType.DEVICE_PROVIDER.ordinal()] =
            3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Device$DeviceType[
                Device.DeviceType.DEVICE_FILERECEIVER.ordinal()] =
            4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Device$DeviceType[
                Device.DeviceType.DEVICE_SCREENSHARING.ordinal()] =
            5;
      } catch (NoSuchFieldError e5) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Device$DeviceType[
                Device.DeviceType.UNKNOWN.ordinal()] =
            6;
      } catch (NoSuchFieldError e6) {
      }
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
    try {
      deviceImpl = new DeviceImpl(bundle);
    } catch (Exception e) {
      DLog.w_api(TAG_CLASS, "getDeviceFromMap : Exception", e);
    }
    switch (C48422.$SwitchMap$com$samsung$android$allshare$Device$DeviceType[type.ordinal()]) {
      case 1:
        if (!this.mAVPlayerMap.containsKey(id)) {
          AVPlayerImpl avPlayer = new AVPlayerImpl(this.mAllShareConnector, deviceImpl);
          this.mAVPlayerMap.put(id, avPlayer);
        }
        return this.mAVPlayerMap.get(id);
      case 2:
        if (!this.mImageViewerMap.containsKey(id)) {
          ImageViewerImpl imageViewer = new ImageViewerImpl(this.mAllShareConnector, deviceImpl);
          this.mImageViewerMap.put(id, imageViewer);
        }
        return this.mImageViewerMap.get(id);
      case 3:
        if (!this.mProviderMap.containsKey(id)) {
          ProviderImpl provider = new ProviderImpl(this.mAllShareConnector, deviceImpl);
          this.mProviderMap.put(id, provider);
        }
        return this.mProviderMap.get(id);
      case 4:
        if (!this.mFileReceiverMap.containsKey(id)) {
          FileReceiverImpl fileReceiver = new FileReceiverImpl(this.mAllShareConnector, deviceImpl);
          this.mFileReceiverMap.put(id, fileReceiver);
        }
        return this.mFileReceiverMap.get(id);
      case 5:
        if (!this.mScreenSharingDeviceMap.containsKey(id)) {
          ScreenSharingDeviceImpl upnpDevice =
              new ScreenSharingDeviceImpl(this.mAllShareConnector, deviceImpl);
          this.mScreenSharingDeviceMap.put(id, upnpDevice);
        }
        return this.mScreenSharingDeviceMap.get(id);
      case 6:
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
      String action, Device.DeviceDomain domain, Device.DeviceType deviceType, String deviceIface) {
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
      DLog.w_api(TAG_CLASS, "resBundle is null");
      return result;
    }
    ArrayList<Bundle> devices =
        resBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_DEVICE);
    if (devices == null || devices.size() == 0) {
      DLog.w_api(TAG_CLASS, "devices.size is null or 0");
      return result;
    }
    Iterator<Bundle> it = devices.iterator();
    while (it.hasNext()) {
      Bundle b = it.next();
      Device d = getDeviceFromMap(b, deviceType);
      if (d == null) {
        DLog.d_api(TAG_CLASS, "getDeviceFromMap is null");
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
      CVMessage cVMessage = new CVMessage();
      this.mMessage = cVMessage;
      cVMessage.setActionID(action_id);
    }

    void putString(String key, String value) {
      this.mMessage.getBundle().putString(key, value);
    }

    void putStringArrayList(String key, ArrayList<String> value) {
      this.mMessage.getBundle().putStringArrayList(key, value);
    }

    Bundle invoke() {
      CVMessage resMessage;
      if (DeviceFinderImpl.this.mAllShareConnector == null
          || !DeviceFinderImpl.this.mAllShareConnector.isAllShareServiceConnected()
          || (resMessage = DeviceFinderImpl.this.mAllShareConnector.requestCVMSync(this.mMessage))
              == null) {
        return null;
      }
      return resMessage.getBundle();
    }
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public void registerSearchTarget(ArrayList<Device.DeviceType> deviceTypeList) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null
        || !iAllShareConnector.isAllShareServiceConnected()
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
        new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_REGISTER_SEARCH_TARGET_SYNC);
    builder.putString("BUNDLE_STRING_ID", applicationID);
    builder.putStringArrayList(AllShareKey.BUNDLE_STRINGARRAYLIST_DEVICE_TYPE_LIST, devTypeList);
    builder.invoke();
  }

  @Override // com.samsung.android.allshare.DeviceFinder
  public void unregisterSearchTarget(ArrayList<Device.DeviceType> deviceTypeList) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null
        || !iAllShareConnector.isAllShareServiceConnected()
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
        new SyncActionInvoker(AllShareAction.ACTION_DEVICE_FINDER_UNREGISTER_SEARCH_TARGET_SYNC);
    builder.putString("BUNDLE_STRING_ID", applicationID);
    builder.putStringArrayList(AllShareKey.BUNDLE_STRINGARRAYLIST_DEVICE_TYPE_LIST, devTypeList);
    builder.invoke();
  }
}
