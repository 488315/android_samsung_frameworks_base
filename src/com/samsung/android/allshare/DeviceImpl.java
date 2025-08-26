package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import com.samsung.android.allshare.Device;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
final class DeviceImpl extends Device implements IBundleHolder, IHandlerHolder {
    private static final String TAG = "DeviceImpl";
    private Bundle mDeviceBundle;
    private IAllShareConnector mAllShareConnector = null;
    AllShareResponseHandler mResponseHandler = new AllShareResponseHandler(this, ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.DeviceImpl.1
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cVMessage) {
            String actionID = cVMessage.getActionID();
            Bundle bundle = cVMessage.getBundle();
            if (actionID == null || bundle == null) {
                DLog.w_api(DeviceImpl.TAG, "handleResponseMessage : actionID == null || resBundle == null");
                return;
            }
            ERROR errorStringToEnum = ERROR.FAIL;
            String string = bundle.getString("BUNDLE_ENUM_ERROR");
            if (string != null) {
                errorStringToEnum = ERROR.stringToEnum(string);
            }
            if (actionID.equals(AllShareAction.ACTION_REQUEST_MOBILE_TO_TV) && errorStringToEnum.equals(ERROR.SUCCESS)) {
                DLog.w_api(DeviceImpl.TAG, "handleResponseMessage : actionID :ACTION_REQUEST_MOBILE_TO_TV response SUCCESS");
            }
        }
    };

    @Override // com.sec.android.allshare.iface.IHandlerHolder
    public void removeEventHandler() {
    }

    DeviceImpl(Bundle bundle) {
        this.mDeviceBundle = null;
        this.mDeviceBundle = bundle;
    }

    void setAllShareConnector(IAllShareConnector iAllShareConnector) {
        if (iAllShareConnector == null) {
            DLog.w_api(TAG, "Connection FAIL: AllShare Service Connector does not exist");
        } else if (this.mDeviceBundle == null) {
            DLog.w_api(TAG, "deviceImpl is null");
        } else {
            this.mAllShareConnector = iAllShareConnector;
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
        return (Uri) (bundle == null ? null : bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_DEVICE_DEFAULT_ICON));
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        Bundle bundle = this.mDeviceBundle;
        ArrayList parcelableArrayList = bundle == null ? null : bundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_DEVICE_ICONLIST);
        ArrayList<Icon> arrayList = new ArrayList<>();
        if (parcelableArrayList != null) {
            Iterator it = parcelableArrayList.iterator();
            while (it.hasNext()) {
                arrayList.add(new IconImpl((Bundle) ((Parcelable) it.next())));
            }
        }
        return arrayList;
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
        Device.DeviceType deviceTypeStringToEnum = Device.DeviceType.stringToEnum(this.mDeviceBundle.getString(AllShareKey.BUNDLE_ENUM_DEVICE_TYPE));
        return deviceTypeStringToEnum == null ? Device.DeviceType.UNKNOWN : deviceTypeStringToEnum;
    }

    @Override // com.samsung.android.allshare.Device
    public String getIPAddress() {
        Bundle bundle = this.mDeviceBundle;
        return bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_DEVICE_IP_ADDRESS);
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        Device.DeviceDomain deviceDomainStringToEnum = Device.DeviceDomain.stringToEnum(this.mDeviceBundle.getString(AllShareKey.BUNDLE_ENUM_DEVICE_DOMAIN));
        return deviceDomainStringToEnum == null ? Device.DeviceDomain.UNKNOWN : deviceDomainStringToEnum;
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
    public void requestMobileToTV(String str, int i) {
        DLog.w_api(TAG, "requestMobileToTV : call requestMobileToTV");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG, "requestMobileToTV : SERVICE_NOT_CONNECTED");
            return;
        }
        if (str == null || i == 0) {
            DLog.w_api(TAG, "requestMobileToTV Fail :  ip is null or port is wrong ");
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_REQUEST_MOBILE_TO_TV);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_SCREENSHARING_IP, str);
        bundle.putInt(AllShareKey.BUNDLE_STRING_SCREENSHARING_PORT, i);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG, "requestMobileToTV : port : " + i + NavigationBarInflaterView.SIZE_MOD_END);
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        return getProductCapInfo(Device.InformationType.P2P_MAC_ADDRESS);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        return getScreenSharingInfo(Device.InformationType.P2P_MAC_ADDRESS);
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType informationType) {
        Bundle bundle = this.mDeviceBundle;
        String string = bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_SECPRODUCTCAP);
        if (string == null || "".equals(string)) {
            return "";
        }
        int i = AnonymousClass2.$SwitchMap$com$samsung$android$allshare$Device$InformationType[informationType.ordinal()];
        if (i == 1) {
            return string;
        }
        if (i != 2 || !string.toLowerCase().contains("ScreenMirroringP2PMAC=".toLowerCase())) {
            return "";
        }
        String lowerCase = string.toLowerCase();
        int iIndexOf = lowerCase.indexOf("ScreenMirroringP2PMAC=".toLowerCase());
        return lowerCase.substring(iIndexOf + 22, iIndexOf + 39);
    }

    /* renamed from: com.samsung.android.allshare.DeviceImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Device$InformationType;

        static {
            int[] iArr = new int[Device.InformationType.values().length];
            $SwitchMap$com$samsung$android$allshare$Device$InformationType = iArr;
            try {
                iArr[Device.InformationType.ALL_INFO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Device$InformationType[Device.InformationType.P2P_MAC_ADDRESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType informationType) {
        Bundle bundle = this.mDeviceBundle;
        String string = bundle == null ? "" : bundle.getString(AllShareKey.BUNDLE_STRING_SCREENSHARING);
        if (string == null || "".equals(string)) {
            return "";
        }
        int i = AnonymousClass2.$SwitchMap$com$samsung$android$allshare$Device$InformationType[informationType.ordinal()];
        if (i == 1) {
            return string;
        }
        if (i != 2 || !string.toLowerCase().contains("p2pDeviceAddress:".toLowerCase())) {
            return "";
        }
        String lowerCase = string.toLowerCase();
        int iIndexOf = lowerCase.indexOf("p2pDeviceAddress:".toLowerCase());
        String strSubstring = lowerCase.substring(iIndexOf + 17, iIndexOf + 34);
        DLog.w_api(TAG, "getScreenSharingInfo macAddress : " + strSubstring);
        return strSubstring;
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int i) {
        String str;
        Bundle bundle = this.mDeviceBundle;
        boolean z = false;
        if (bundle == null) {
            DLog.w_api(TAG, "[isSupportedByType] : [Type]UNKNOWNmDeviceBundle  is null");
            return false;
        }
        if (i == 1) {
            z = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_IMAGE);
            str = "IMAGE";
        } else if (i == 2) {
            z = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_VIDEO);
            str = "VIDEO";
        } else if (i != 3) {
            str = "UNKNOWN";
        } else {
            z = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_AUDIO);
            str = "AUDIO";
        }
        DLog.i_api(TAG, "[isSupportedByType] : [Type]" + str + "[isSupported]" + z);
        return z;
    }
}
