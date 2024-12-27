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

/* loaded from: classes3.dex */
final class ScreenSharingDeviceImpl extends ScreenSharingDevice implements IBundleHolder, IHandlerHolder {
    private static final String TAG_CLASS = "ScreenSharingDeviceImpl";
    private ScreenSharingDevice.IScreenSharingActionResponseListner mActionResponseListener;
    private IAllShareConnector mAllShareConnector;
    private AllShareResponseHandler mAllShareRespHandler;
    private DeviceImpl mDeviceImpl;
    private AllShareEventHandler mEventHandler;
    private boolean mIsSubscribed;
    private ScreenSharingDevice.IScreenSharingEventListener mUPnPDeviceEventListener;

    protected ScreenSharingDeviceImpl() {
        this.mDeviceImpl = null;
        this.mUPnPDeviceEventListener = null;
        this.mActionResponseListener = null;
        this.mIsSubscribed = false;
        this.mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.1
            @Override // com.samsung.android.allshare.AllShareEventHandler
            public void handleEventMessage(CVMessage cvm) {
                ERROR error = ERROR.FAIL;
                try {
                    Bundle resBundle = cvm.getBundle();
                    ERROR error2 = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
                    if (error2 == null) {
                        error2 = ERROR.FAIL;
                    }
                    ScreenSharingDeviceImpl.this.mUPnPDeviceEventListener.onEventReceived("", "", error2);
                } catch (Error err) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", err);
                } catch (Exception e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Exception");
                }
            }
        };
        this.mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.2
            @Override // com.samsung.android.allshare.AllShareResponseHandler
            public void handleResponseMessage(CVMessage cvm) {
                String actionID = cvm.getActionID();
                Bundle resBundle = cvm.getBundle();
                if (actionID == null || resBundle == null) {
                    return;
                }
                ERROR err = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
                try {
                    if (ScreenSharingDeviceImpl.this.mActionResponseListener != null) {
                        if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV)) {
                            if (err.equals(ERROR.SUCCESS)) {
                                String tBSSID = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                                String tWlanFreq = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                                String tListenFreq = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                                DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV response SUCCESS");
                                ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingM2TV(tBSSID, tWlanFreq, tListenFreq);
                            }
                        } else if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE) && err.equals(ERROR.SUCCESS)) {
                            String tBSSID2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                            String tWlanFreq2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                            String tListenFreq2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                            String tWFDSourcePort = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WFDSOURCEPORT);
                            DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE response SUCCESS");
                            ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingTV2M(tBSSID2, tWlanFreq2, tListenFreq2, tWFDSourcePort);
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

    ScreenSharingDeviceImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
        this.mDeviceImpl = null;
        this.mUPnPDeviceEventListener = null;
        this.mActionResponseListener = null;
        this.mIsSubscribed = false;
        this.mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.1
            @Override // com.samsung.android.allshare.AllShareEventHandler
            public void handleEventMessage(CVMessage cvm) {
                ERROR error = ERROR.FAIL;
                try {
                    Bundle resBundle = cvm.getBundle();
                    ERROR error2 = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
                    if (error2 == null) {
                        error2 = ERROR.FAIL;
                    }
                    ScreenSharingDeviceImpl.this.mUPnPDeviceEventListener.onEventReceived("", "", error2);
                } catch (Error err) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", err);
                } catch (Exception e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mEventHandler.handleEventMessage Exception");
                }
            }
        };
        this.mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ScreenSharingDeviceImpl.2
            @Override // com.samsung.android.allshare.AllShareResponseHandler
            public void handleResponseMessage(CVMessage cvm) {
                String actionID = cvm.getActionID();
                Bundle resBundle = cvm.getBundle();
                if (actionID == null || resBundle == null) {
                    return;
                }
                ERROR err = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
                try {
                    if (ScreenSharingDeviceImpl.this.mActionResponseListener != null) {
                        if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV)) {
                            if (err.equals(ERROR.SUCCESS)) {
                                String tBSSID = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                                String tWlanFreq = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                                String tListenFreq = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                                DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV response SUCCESS");
                                ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingM2TV(tBSSID, tWlanFreq, tListenFreq);
                            }
                        } else if (actionID.equals(AllShareAction.ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE) && err.equals(ERROR.SUCCESS)) {
                            String tBSSID2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_BSSID);
                            String tWlanFreq2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WLANFREQ);
                            String tListenFreq2 = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_LISTENFREQ);
                            String tWFDSourcePort = resBundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING_TV_WFDSOURCEPORT);
                            DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "handleResponseMessage : actionID :ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE response SUCCESS");
                            ScreenSharingDeviceImpl.this.mActionResponseListener.onConnectScreenSharingTV2M(tBSSID2, tWlanFreq2, tListenFreq2, tWFDSourcePort);
                        }
                    }
                } catch (Error e) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Error", e);
                } catch (Exception e2) {
                    DLog.w_api(ScreenSharingDeviceImpl.TAG_CLASS, "mAllShareRespHandler.handleResponseMessage Exception", e2);
                }
            }
        };
        if (connector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = connector;
            this.mDeviceImpl = deviceImpl;
        }
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        if (this.mDeviceImpl == null) {
            return Device.DeviceDomain.UNKNOWN;
        }
        return this.mDeviceImpl.getDeviceDomain();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        if (this.mDeviceImpl == null) {
            return Device.DeviceType.UNKNOWN;
        }
        return this.mDeviceImpl.getDeviceType();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getID() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getID();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getIPAddress() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getIPAddress();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public Uri getIcon() {
        if (this.mDeviceImpl == null) {
            return Uri.parse("");
        }
        return this.mDeviceImpl.getIcon();
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        if (this.mDeviceImpl == null) {
            return new ArrayList<>();
        }
        return this.mDeviceImpl.getIconList();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getModelName() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getModelName();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice, com.samsung.android.allshare.Device
    public String getName() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getName();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        if (this.mDeviceImpl == null) {
            return new Bundle();
        }
        return this.mDeviceImpl.getBundle();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public void setResponseListener(ScreenSharingDevice.IScreenSharingActionResponseListner listener) {
        DLog.d_api(TAG_CLASS, "setResponseListener to " + listener);
        this.mActionResponseListener = listener;
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public ERROR setEventListener(ScreenSharingDevice.IScreenSharingEventListener listener) {
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return ERROR.SERVICE_NOT_CONNECTED;
        }
        this.mUPnPDeviceEventListener = listener;
        if (!this.mIsSubscribed && listener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (this.mIsSubscribed && listener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
        return ERROR.SUCCESS;
    }

    @Override // com.samsung.android.allshare.Device
    public String getNIC() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getNIC();
    }

    @Override // com.sec.android.allshare.iface.IHandlerHolder
    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSeekableOnPaused() {
        return false;
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isWholeHomeAudio() {
        return false;
    }

    @Override // com.samsung.android.allshare.Device
    public String getP2pMacAddress() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getP2pMacAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getScreenSharingInfo();
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public void connectScreenSharingM2TV(String mWlanMacAddress, String mP2pDeviceAddress, String mBluetoothMacAddress, int mWFDSourcePort) {
        DLog.w_api(TAG_CLASS, "connectScreenSharingM2TV : call connectScreenSharingM2TV");
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingM2TV : SERVICE_NOT_CONNECTED");
            return;
        }
        if (mWlanMacAddress == null || mP2pDeviceAddress == null || mBluetoothMacAddress == null || mWFDSourcePort == 0) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingM2TV Fail :  Address is null or port is wrong ");
            return;
        }
        CVMessage cvm = new CVMessage();
        cvm.setActionID(AllShareAction.ACTION_CONNECT_SCREENSHARING_MOBILE_TO_TV);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_WLANMACADDRESS, mWlanMacAddress);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_P2PDEVICEADDRESS, mP2pDeviceAddress);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_BLUETOOTHMACADDRESS, mBluetoothMacAddress);
        bundle.putInt(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_WFDSOURCEPORT, mWFDSourcePort);
        cvm.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cvm, this.mAllShareRespHandler);
        DLog.i_api(TAG_CLASS, "connectScreenSharingM2TV : [ WlanMacAddress : " + mWlanMacAddress + " P2pDeviceAddress : " + mP2pDeviceAddress + " BluetoothMacAddress : " + mBluetoothMacAddress + " WFDSourcePort : " + mWFDSourcePort + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.samsung.android.allshare.ScreenSharingDevice
    public void connectScreenSharingTV2M(String mWlanMacAddress, String mP2pDeviceAddress, String mBluetoothMacAddress) {
        DLog.w_api(TAG_CLASS, "connectScreenSharingTV2M : call connectScreenSharingM2TV");
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingTV2M : SERVICE_NOT_CONNECTED");
            return;
        }
        if (mWlanMacAddress == null || mP2pDeviceAddress == null || mBluetoothMacAddress == null) {
            DLog.w_api(TAG_CLASS, "connectScreenSharingTV2M Fail :  Address is null or port is wrong ");
            return;
        }
        CVMessage cvm = new CVMessage();
        cvm.setActionID(AllShareAction.ACTION_CONNECT_SCREENSHARING_TV_TO_MOBILE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_WLANMACADDRESS, mWlanMacAddress);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_P2PDEVICEADDRESS, mP2pDeviceAddress);
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_MOBILE_BLUETOOTHMACADDRESS, mBluetoothMacAddress);
        cvm.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cvm, this.mAllShareRespHandler);
        DLog.i_api(TAG_CLASS, "connectScreenSharingTV2M : [ WlanMacAddress : " + mWlanMacAddress + " P2pDeviceAddress : " + mP2pDeviceAddress + " BluetoothMacAddress : " + mBluetoothMacAddress + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.samsung.android.allshare.Device
    public void requestMobileToTV(String ip, int port) {
        DLog.w_api(TAG_CLASS, "requestMobileToTV : call requestMobileToTV");
        if (this.mDeviceImpl == null) {
            return;
        }
        this.mDeviceImpl.requestMobileToTV(ip, port);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getScreenSharingP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getSecProductP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType infoType) {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getProductCapInfo(infoType);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType infoType) {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getScreenSharingInfo(infoType);
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int type) {
        if (this.mDeviceImpl == null) {
            return false;
        }
        return this.mDeviceImpl.isSupportedByType(type);
    }
}
