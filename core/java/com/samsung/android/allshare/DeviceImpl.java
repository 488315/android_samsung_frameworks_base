package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Parcelable;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class DeviceImpl extends Device implements IBundleHolder, IHandlerHolder {
  private static final String TAG = "DeviceImpl";
  private Bundle mDeviceBundle;
  private IAllShareConnector mAllShareConnector = null;
  AllShareResponseHandler mResponseHandler =
      new AllShareResponseHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.DeviceImpl.1
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
          String actionID = cvm.getActionID();
          Bundle resBundle = cvm.getBundle();
          if (actionID == null || resBundle == null) {
            DLog.w_api(
                DeviceImpl.TAG, "handleResponseMessage : actionID == null || resBundle == null");
            return;
          }
          ERROR error = ERROR.FAIL;
          String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
          if (errorStr != null) {
            error = ERROR.stringToEnum(errorStr);
          }
          if (actionID.equals(AllShareAction.ACTION_REQUEST_MOBILE_TO_TV)
              && error.equals(ERROR.SUCCESS)) {
            DLog.w_api(
                DeviceImpl.TAG,
                "handleResponseMessage : actionID :ACTION_REQUEST_MOBILE_TO_TV response SUCCESS");
          }
        }
      };

  DeviceImpl(Bundle bundle) {
    this.mDeviceBundle = null;
    this.mDeviceBundle = bundle;
  }

  void setAllShareConnector(IAllShareConnector connector) {
    if (connector == null) {
      DLog.w_api(TAG, "Connection FAIL: AllShare Service Connector does not exist");
    } else if (this.mDeviceBundle == null) {
      DLog.w_api(TAG, "deviceImpl is null");
    } else {
      this.mAllShareConnector = connector;
    }
  }

  @Override // com.samsung.android.allshare.Device
  public String getName() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_DEVICE_NAME);
  }

  @Override // com.samsung.android.allshare.Device
  public Uri getIcon() {
    Bundle bundle = this.mDeviceBundle;
    return (Uri)
        (bundle == null
            ? null
            : bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE_DEFAULT_ICON));
  }

  @Override // com.samsung.android.allshare.Device
  public ArrayList<Icon> getIconList() {
    Bundle bundle = this.mDeviceBundle;
    ArrayList<Parcelable> iconList =
        bundle == null
            ? null
            : bundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_DEVICE_ICONLIST);
    ArrayList<Icon> result = new ArrayList<>();
    if (iconList == null) {
      return result;
    }
    Iterator<Parcelable> it = iconList.iterator();
    while (it.hasNext()) {
      Parcelable parcel = it.next();
      result.add(new IconImpl((Bundle) parcel));
    }
    return result;
  }

  @Override // com.samsung.android.allshare.Device
  public String getID() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString("BUNDLE_STRING_ID");
  }

  @Override // com.samsung.android.allshare.Device
  public String getModelName() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_DEVICE_MODELNAME);
  }

  @Override // com.samsung.android.allshare.Device
  public Device.DeviceType getDeviceType() {
    Device.DeviceType deviceType =
        Device.DeviceType.stringToEnum(
            this.mDeviceBundle.getString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE));
    if (deviceType == null) {
      return Device.DeviceType.UNKNOWN;
    }
    return deviceType;
  }

  @Override // com.samsung.android.allshare.Device
  @Deprecated
  public String getIPAdress() {
    return getIPAddress();
  }

  @Override // com.samsung.android.allshare.Device
  public String getIPAddress() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_DEVICE_IP_ADDRESS);
  }

  @Override // com.samsung.android.allshare.Device
  public Device.DeviceDomain getDeviceDomain() {
    Device.DeviceDomain deviceDomain =
        Device.DeviceDomain.stringToEnum(
            this.mDeviceBundle.getString(AllShareKey.BUNDLE_ENUM_DEVICE_DOMAIN));
    if (deviceDomain == null) {
      return Device.DeviceDomain.UNKNOWN;
    }
    return deviceDomain;
  }

  @Override // com.sec.android.allshare.iface.IBundleHolder
  public Bundle getBundle() {
    Bundle bundle = this.mDeviceBundle;
    if (bundle == null) {
      return null;
    }
    return bundle;
  }

  @Override // com.samsung.android.allshare.Device
  public String getNIC() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_BOUND_INTERFACE);
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isSeekableOnPaused() {
    return this.mDeviceBundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SMSC_iS_SEEKABLE_ON_PAUSE);
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isWholeHomeAudio() {
    return this.mDeviceBundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SMSC_IS_WHOLE_HOME_AUDIO);
  }

  @Override // com.samsung.android.allshare.Device
  public String getP2pMacAddress() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_MIRRORING_MAC);
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingInfo() {
    Bundle bundle = this.mDeviceBundle;
    return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING);
  }

  @Override // com.samsung.android.allshare.Device
  public void requestMobileToTV(String ip, int port) {
    DLog.w_api(TAG, "requestMobileToTV : call requestMobileToTV");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG, "requestMobileToTV : SERVICE_NOT_CONNECTED");
      return;
    }
    if (ip == null || port == 0) {
      DLog.w_api(TAG, "requestMobileToTV Fail :  ip is null or port is wrong ");
      return;
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_REQUEST_MOBILE_TO_TV);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_IP, ip);
    bundle.putInt(AllShareKey.BUNDLE_STRING_SCREENSHARING_PORT, port);
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
    DLog.i_api(TAG, "requestMobileToTV : port : " + port + NavigationBarInflaterView.SIZE_MOD_END);
  }

  @Override // com.samsung.android.allshare.Device
  public String getSecProductP2pMacAddr() {
    return getProductCapInfo(Device.InformationType.P2P_MAC_ADDRESS);
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingP2pMacAddr() {
    return getScreenSharingInfo(Device.InformationType.P2P_MAC_ADDRESS);
  }

  @Override // com.sec.android.allshare.iface.IHandlerHolder
  public void removeEventHandler() {}

  @Override // com.samsung.android.allshare.Device
  public String getProductCapInfo(Device.InformationType infoType) {
    Bundle bundle = this.mDeviceBundle;
    String productCap =
        bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_SECPRODUCTCAP);
    if (productCap == null || "".equals(productCap)) {
      return "";
    }
    switch (C48442.$SwitchMap$com$samsung$android$allshare$Device$InformationType[
        infoType.ordinal()]) {
      case 1:
        String result = productCap;
        return result;
      case 2:
        if (!productCap.toLowerCase().contains("ScreenMirroringP2PMAC=".toLowerCase())) {
          return "";
        }
        String secProductCap = productCap.toLowerCase();
        int start =
            secProductCap.indexOf("ScreenMirroringP2PMAC=".toLowerCase())
                + "ScreenMirroringP2PMAC=".length();
        String result2 = secProductCap.substring(start, start + 17);
        return result2;
      default:
        return "";
    }
  }

  /* renamed from: com.samsung.android.allshare.DeviceImpl$2 */
  static /* synthetic */ class C48442 {
    static final /* synthetic */ int[]
        $SwitchMap$com$samsung$android$allshare$Device$InformationType;

    static {
      int[] iArr = new int[Device.InformationType.values().length];
      $SwitchMap$com$samsung$android$allshare$Device$InformationType = iArr;
      try {
        iArr[Device.InformationType.ALL_INFO.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$android$allshare$Device$InformationType[
                Device.InformationType.P2P_MAC_ADDRESS.ordinal()] =
            2;
      } catch (NoSuchFieldError e2) {
      }
    }
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingInfo(Device.InformationType infoType) {
    Bundle bundle = this.mDeviceBundle;
    String screenSharing =
        bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING);
    if (screenSharing == null || "".equals(screenSharing)) {
      return "";
    }
    switch (C48442.$SwitchMap$com$samsung$android$allshare$Device$InformationType[
        infoType.ordinal()]) {
      case 1:
        return screenSharing;
      case 2:
        if (!screenSharing.toLowerCase().contains("p2pDeviceAddress:".toLowerCase())) {
          return "";
        }
        String secSreenSharing = screenSharing.toLowerCase();
        int start =
            secSreenSharing.indexOf("p2pDeviceAddress:".toLowerCase())
                + "p2pDeviceAddress:".length();
        String result = secSreenSharing.substring(start, start + 17);
        DLog.w_api(TAG, "getScreenSharingInfo macAddress : " + result);
        return result;
      default:
        return "";
    }
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isSupportedByType(int type) {
    String str = "UNKNOWN";
    boolean isSupported = false;
    Bundle bundle = this.mDeviceBundle;
    if (bundle == null) {
      DLog.w_api(TAG, "[isSupportedByType] : [Type]UNKNOWNmDeviceBundle  is null");
      return false;
    }
    switch (type) {
      case 1:
        str = "IMAGE";
        isSupported = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_IMAGE);
        break;
      case 2:
        str = "VIDEO";
        isSupported = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_VIDEO);
        break;
      case 3:
        str = "AUDIO";
        isSupported = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_AUDIO);
        break;
    }
    DLog.i_api(TAG, "[isSupportedByType] : [Type]" + str + "[isSupported]" + isSupported);
    return isSupported;
  }
}
