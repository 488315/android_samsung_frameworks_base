package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ScreenSharingDevice;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;

/* loaded from: classes6.dex */
final class ScreenSharingDeviceImpl extends ScreenSharingDevice implements IBundleHolder, IHandlerHolder {
    private static final String TAG_CLASS = "ScreenSharingDeviceImpl";
    private ScreenSharingDevice.IScreenSharingActionResponseListner mActionResponseListener;
    private IAllShareConnector mAllShareConnector;
    private AllShareResponseHandler mAllShareRespHandler;
    private DeviceImpl mDeviceImpl;
    private AllShareEventHandler mEventHandler;
    private boolean mIsSubscribed;
    private ScreenSharingDevice.IScreenSharingEventListener mUPnPDeviceEventListener;

    @Override // com.samsung.android.allshare.Device
    public boolean isSeekableOnPaused() {
        return false;
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isWholeHomeAudio() {
        return false;
    }

    protected ScreenSharingDeviceImpl() {
        this.mDeviceImpl = null;
        this.mUPnPDeviceEventListener = null;
        this.mActionResponseListener = null;
        this.mIsSubscribed = false;
        this.mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.1
            @Override // com.samsung.android.allshare.AllShareEventHandler
            public void handleEventMessage(CVMessage cVMessage) {
                ERROR error = ERROR.FAIL;
                try {
                    ERROR stringToEnum = ERROR.stringToEnum(cVMessage.getBundle().getString("BUNDLE_ENUM_ERROR"));
                    if (stringToEnum == null) {
                        stringToEnum = ERROR.FAIL;
                    }
                    ScreenSharingDeviceImpl.this.mUPnPDeviceEventListener.onEventReceived("", "", stringToEnum);
                } catch (Error e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", e);
                } catch (Exception unused) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Exception");
                }
            }
        };
        this.mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.2
            @Override // com.samsung.android.allshare.AllShareResponseHandler
            public void handleResponseMessage(CVMessage cVMessage) {
                String actionID = cVMessage.getActionID();
                Bundle bundle = cVMessage.getBundle();
                if (actionID == null || bundle == null) {
                    return;
                }
                ERROR stringToEnum = ERROR.stringToEnum(bundle.getString("BUNDLE_ENUM_ERROR"));
                try {
                    if (ScreenSharingDeviceImpl.this.mActionResponseListener != null) {
                        if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV)) {
                            if (stringToEnum.equals(ERROR.SUCCESS)) {
                                String string = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                                String string2 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                                String string3 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                                DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV response SUCCESS");
                                ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingM2TV(string, string2, string3);
                                return;
                            }
                            return;
                        }
                        if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE) && stringToEnum.equals(ERROR.SUCCESS)) {
                            String string4 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                            String string5 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                            String string6 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                            String string7 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WFDSOURCEPORT);
                            DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE response SUCCESS");
                            ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingTV2M(string4, string5, string6, string7);
                        }
                    }
                } catch (Error e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Error", e);
                } catch (Exception e2) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Exception", e2);
                }
            }
        };
    }

    ScreenSharingDeviceImpl(IAllShareConnector iAllShareConnector, DeviceImpl deviceImpl) {
        this.mDeviceImpl = null;
        this.mUPnPDeviceEventListener = null;
        this.mActionResponseListener = null;
        this.mIsSubscribed = false;
        this.mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.1
            @Override // com.samsung.android.allshare.AllShareEventHandler
            public void handleEventMessage(CVMessage cVMessage) {
                ERROR error = ERROR.FAIL;
                try {
                    ERROR stringToEnum = ERROR.stringToEnum(cVMessage.getBundle().getString("BUNDLE_ENUM_ERROR"));
                    if (stringToEnum == null) {
                        stringToEnum = ERROR.FAIL;
                    }
                    ScreenSharingDeviceImpl.this.mUPnPDeviceEventListener.onEventReceived("", "", stringToEnum);
                } catch (Error e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", e);
                } catch (Exception unused) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Exception");
                }
            }
        };
        this.mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.2
            @Override // com.samsung.android.allshare.AllShareResponseHandler
            public void handleResponseMessage(CVMessage cVMessage) {
                String actionID = cVMessage.getActionID();
                Bundle bundle = cVMessage.getBundle();
                if (actionID == null || bundle == null) {
                    return;
                }
                ERROR stringToEnum = ERROR.stringToEnum(bundle.getString("BUNDLE_ENUM_ERROR"));
                try {
                    if (ScreenSharingDeviceImpl.this.mActionResponseListener != null) {
                        if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV)) {
                            if (stringToEnum.equals(ERROR.SUCCESS)) {
                                String string = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                                String string2 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                                String string3 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                                DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV response SUCCESS");
                                ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingM2TV(string, string2, string3);
                                return;
                            }
                            return;
                        }
                        if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE) && stringToEnum.equals(ERROR.SUCCESS)) {
                            String string4 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                            String string5 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                            String string6 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                            String string7 = bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WFDSOURCEPORT);
                            DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE response SUCCESS");
                            ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingTV2M(string4, string5, string6, string7);
                        }
                    }
                } catch (Error e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Error", e);
                } catch (Exception e2) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Exception", e2);
                }
            }
        };
        if (iAllShareConnector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = iAllShareConnector;
            this.mDeviceImpl = deviceImpl;
        }
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Device.DeviceDomain.UNKNOWN;
        }
        return deviceImpl.getDeviceDomain();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Device.DeviceType.UNKNOWN;
        }
        return deviceImpl.getDeviceType();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getID() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getID();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getIPAddress() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getIPAddress();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public Uri getIcon() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Uri.parse("");
        }
        return deviceImpl.getIcon();
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return new ArrayList<>();
        }
        return deviceImpl.getIconList();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getModelName() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getModelName();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getName() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getName();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return new Bundle();
        }
        return deviceImpl.getBundle();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public void setResponseListener(ScreenSharingDevice.IScreenSharingActionResponseListner iScreenSharingActionResponseListner) {
        DLog.d_api(TAG_CLASS, "setResponseListener to " + iScreenSharingActionResponseListner);
        this.mActionResponseListener = iScreenSharingActionResponseListner;
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public ERROR setEventListener(ScreenSharingDevice.IScreenSharingEventListener iScreenSharingEventListener) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return ERROR.SERVICE_NOT_CONNECTED;
        }
        this.mUPnPDeviceEventListener = iScreenSharingEventListener;
        boolean z = this.mIsSubscribed;
        if (!z && iScreenSharingEventListener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (z && iScreenSharingEventListener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
        return ERROR.SUCCESS;
    }

    @Override // com.samsung.android.allshare.Device
    public String getNIC() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getNIC();
    }

    @Override // com.sec.android.allshare.iface.IHandlerHolder
    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.Device
    public String getP2pMacAddress() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getP2pMacAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getScreenSharingInfo();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public void connectScreenSharingM2TV(String str, String str2, String str3, int i) {
        DLog.w_api(TAG_CLASS, "connectScreenSharingM2TV : call connectScreenSharingM2TV");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingM2TV : SERVICE_NOT_CONNECTED");
            return;
        }
        if (str == null || str2 == null || str3 == null || i == 0) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingM2TV Fail :  Address is null or port is wrong ");
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_WLANMACADDRESS, str);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_P2PDEVICEADDRESS, str2);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_BLUETOOTHMACADDRESS, str3);
        bundle.putInt(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_WFDSOURCEPORT, i);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
        DLog.i_api(TAG_CLASS, "connectScreenSharingM2TV : [ WlanMacAddress : " + str + " P2pDeviceAddress : " + str2 + " BluetoothMacAddress : " + str3 + " WFDSourcePort : " + i + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public void connectScreenSharingTV2M(String str, String str2, String str3) {
        DLog.w_api(TAG_CLASS, "connectScreenSharingTV2M : call connectScreenSharingM2TV");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingTV2M : SERVICE_NOT_CONNECTED");
            return;
        }
        if (str == null || str2 == null || str3 == null) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingTV2M Fail :  Address is null or port is wrong ");
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_WLANMACADDRESS, str);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_P2PDEVICEADDRESS, str2);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_BLUETOOTHMACADDRESS, str3);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
        DLog.i_api(TAG_CLASS, "connectScreenSharingTV2M : [ WlanMacAddress : " + str + " P2pDeviceAddress : " + str2 + " BluetoothMacAddress : " + str3 + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.samsung.android.allshare.Device
    public void requestMobileToTV(String str, int i) {
        DLog.w_api(TAG_CLASS, "requestMobileToTV : call requestMobileToTV");
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return;
        }
        deviceImpl.requestMobileToTV(str, i);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getScreenSharingP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getSecProductP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType informationType) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getProductCapInfo(informationType);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType informationType) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getScreenSharingInfo(informationType);
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int i) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return false;
        }
        return deviceImpl.isSupportedByType(i);
    }
}
