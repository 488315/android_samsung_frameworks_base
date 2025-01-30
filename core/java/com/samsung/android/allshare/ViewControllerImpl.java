package com.samsung.android.allshare;

import android.content.Context;
import android.graphics.Point;
import android.hardware.scontext.SContextConstants;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Message;
import android.util.Xml;
import android.view.Display;
import android.view.WindowManager;
import com.samsung.android.allshare.IAppControlAPI;
import com.samsung.android.allshare.media.ViewController;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
final class ViewControllerImpl extends ViewController implements IBundleHolder {
    private static final int EVENT_CONTROL_MULTI_TOUCH_BEGIN = 37;
    private static final int EVENT_CONTROL_MULTI_TOUCH_END = 38;
    private static final int EVENT_CONTROL_MULTI_TOUCH_MOVE = 39;
    private static final int EVENT_CONTROL_TOUCH_GESTURE_MOVE = 13;
    private static final int EVENT_CONTROL_TOUCH_GESTURE_UP = 12;
    private static final int MODE_TOUCH = 0;
    private static final String TAG = "ViewControllerImpl";
    private IAllShareConnector mAllShareConnector;
    private DeviceImpl mDeviceImpl;
    private String mDeviceName;
    private IAppControlAPI mIAppComponent;
    private String mIPAddress;
    private String mMACAddress;
    private float mTvHeight;
    private float mTvRatio;
    private float mTvWidth;
    private ViewController.IEventListener mEventListener = null;
    private ViewController.IResponseListener mResponseListener = null;
    private float mImageWidth = 0.0f;
    private float mImageHeight = 0.0f;
    private boolean mIsConnected = false;
    private boolean mIsSubscribed = false;
    private float mRelativeZoomRate = 1.0f;
    private float mImageRatio = 0.0f;
    private float mMobilePhoneRatio = 0.0f;
    private int mMarginX = 0;
    private int mMarginY = 0;
    private int mOrgCenterX = 0;
    private int mOrgCenterY = 0;
    private int mOrgX = 0;
    private int mOrgY = 0;
    private float mTvOrgX = 0.0f;
    private float mTvOrgY = 0.0f;
    private float mTvOrgX0 = 0.0f;
    private float mTvOrgY0 = 0.0f;
    private float mZoomedImageWidth = 0.0f;
    private float mZoomedImageHeight = 0.0f;
    private float mMobileWidth = 0.0f;
    private float mMobileHeight = 0.0f;
    private float mAbsZoomRate = 1.0f;
    private float mPrevAbsZoomRate = 1.0f;
    private int mPrevAngle = 0;
    private AllShareEventHandler mAllShareEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ViewControllerImpl.1
        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
            Bundle resBundle = cvm.getBundle();
            if (resBundle == null) {
                return;
            }
            String strEventID = resBundle.getString(AllShareKey.BUNDLE_STRING_MAIN_TV_EVENT_ID);
            String strXML = resBundle.getString(AllShareKey.BUNDLE_STRING_MAIN_TV_EVENT_XML);
            DLog.i_api(ViewControllerImpl.TAG, "[TVControl] mAllShareEvent : " + strEventID + " " + strXML);
            LastChangeEvent lastChangeEvent = new LastChangeEvent();
            lastChangeEvent.parseFromXML(strXML);
            if (lastChangeEvent.getPowerOff().equalsIgnoreCase("PowerOFF") && ViewControllerImpl.this.mEventListener != null) {
                ViewControllerImpl.this.disconnect();
                ViewControllerImpl.this.mEventListener.onDisconnected(ViewControllerImpl.this, ERROR.SUCCESS);
            }
        }
    };
    private IAppControlAPI.IControlEventListener mControlEventListener = new IAppControlAPI.IControlEventListener() { // from class: com.samsung.android.allshare.ViewControllerImpl.2
        @Override // com.samsung.android.allshare.IAppControlAPI.IControlEventListener
        public void controlEvent(EventSync event) {
            Message msg;
            if (ViewControllerImpl.this.mEventHandler != null && (msg = ViewControllerImpl.this.mEventHandler.obtainMessage()) != null) {
                msg.what = event.mWhat;
                msg.arg1 = event.mArg1;
                msg.arg2 = event.mArg2;
                msg.obj = event.mStr;
                ViewControllerImpl.this.mEventHandler.sendMessage(msg);
            }
        }
    };
    private Handler mEventHandler = new Handler() { // from class: com.samsung.android.allshare.ViewControllerImpl.3
        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    DLog.i_api(ViewControllerImpl.TAG, "[ViewControl] Event : IAPP_AUTHENTICATION arg : " + msg.arg1);
                    if (msg.arg1 == 0) {
                        ViewControllerImpl.this.mIsConnected = false;
                        if (ViewControllerImpl.this.mResponseListener != null) {
                            ViewControllerImpl.this.mResponseListener.onConnectResponseReceived(ViewControllerImpl.this, ERROR.PERMISSION_NOT_ALLOWED);
                            break;
                        }
                    } else if (msg.arg1 == 1 || msg.arg1 == 2) {
                        ViewControllerImpl.this.mIsConnected = true;
                        ViewControllerImpl.this.mIAppComponent.setTouchGestureTouchMode(0);
                        if (ViewControllerImpl.this.mResponseListener != null) {
                            ViewControllerImpl.this.mResponseListener.onConnectResponseReceived(ViewControllerImpl.this, ERROR.SUCCESS);
                            break;
                        }
                    }
                    break;
                case 101:
                    DLog.i_api(ViewControllerImpl.TAG, "[ViewControl] Event : IAPP_AUTHENTICATION_TIMEOUT arg : " + msg.arg1);
                    ViewControllerImpl.this.mIsConnected = false;
                    if (ViewControllerImpl.this.mResponseListener != null) {
                        ViewControllerImpl.this.mResponseListener.onConnectResponseReceived(ViewControllerImpl.this, ERROR.PERMISSION_NOT_ALLOWED);
                        break;
                    }
                    break;
                case 300:
                    DLog.i_api(ViewControllerImpl.TAG, "[ViewControl] Event : IAPP_EXIT arg : " + msg.arg1);
                    ViewControllerImpl.this.mIsConnected = false;
                    if (msg.arg1 == 0) {
                        if (ViewControllerImpl.this.mEventListener != null) {
                            ViewControllerImpl.this.mEventListener.onDisconnected(ViewControllerImpl.this, ERROR.PERMISSION_NOT_ALLOWED);
                            break;
                        }
                    } else if (msg.arg1 == 1 && ViewControllerImpl.this.mEventListener != null) {
                        ViewControllerImpl.this.mEventListener.onDisconnected(ViewControllerImpl.this, ERROR.PERMISSION_NOT_ALLOWED);
                        break;
                    }
                    break;
                case 9999:
                    ViewControllerImpl.this.mIsConnected = false;
                    if (ViewControllerImpl.this.mResponseListener != null) {
                        ViewControllerImpl.this.mResponseListener.onConnectResponseReceived(ViewControllerImpl.this, ERROR.FAIL);
                        break;
                    }
                    break;
                default:
                    DLog.i_api(ViewControllerImpl.TAG, "[ViewControl] Event : Others : " + msg.obj);
                    break;
            }
        }
    };

    ViewControllerImpl(IAllShareConnector connector, DeviceImpl deviceImpl, int tvWidth, int tvHeight) {
        String str;
        this.mIAppComponent = null;
        this.mAllShareConnector = null;
        this.mDeviceImpl = null;
        this.mMACAddress = null;
        this.mIPAddress = null;
        this.mDeviceName = null;
        this.mTvWidth = 0.0f;
        this.mTvHeight = 0.0f;
        this.mTvRatio = 0.0f;
        if (connector == null) {
            DLog.w_api(TAG, "Connection FAIL: AllShare Service Connector does not exist");
            return;
        }
        Context context = ServiceConnector.getContext();
        if (context != null) {
            ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conManager.getNetworkInfo(13);
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
                this.mMACAddress = wifiManager.getConnectionInfo().getMacAddress();
            }
            if (networkInfo != null && networkInfo.isConnected() && (str = this.mMACAddress) != null) {
                String[] strMACPart = str.split(":");
                if (strMACPart.length >= 6) {
                    int nTempMAC1 = Integer.parseInt(strMACPart[0], 16);
                    int nTempMAC2 = Integer.parseInt(strMACPart[4], 16);
                    strMACPart[0] = String.format("%02x", Integer.valueOf(nTempMAC1 | 2));
                    strMACPart[4] = String.format("%02x", Integer.valueOf(nTempMAC2 ^ 128));
                }
                this.mMACAddress = strMACPart[0] + ":" + strMACPart[1] + ":" + strMACPart[2] + ":" + strMACPart[3] + ":" + strMACPart[4] + ":" + strMACPart[5];
            }
        }
        this.mAllShareConnector = connector;
        this.mIAppComponent = new IAppControlAPI();
        this.mDeviceImpl = deviceImpl;
        this.mIPAddress = deviceImpl.getIPAddress();
        this.mDeviceName = this.mDeviceImpl.getName();
        float f = tvWidth;
        this.mTvWidth = f;
        float f2 = tvHeight;
        this.mTvHeight = f2;
        this.mTvRatio = f / f2;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void connect() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            innerDisconnect();
            ViewController.IResponseListener iResponseListener = this.mResponseListener;
            if (iResponseListener != null) {
                iResponseListener.onConnectResponseReceived(this, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        if (!this.mIsConnected) {
            boolean bRet = this.mIAppComponent.stopControl();
            if (!bRet) {
                this.mResponseListener.onConnectResponseReceived(this, ERROR.FAIL);
                return;
            }
            this.mIAppComponent.setOnEventListener(this.mControlEventListener);
            boolean bRet2 = this.mIAppComponent.startControl(this.mMACAddress, this.mIPAddress, this.mDeviceName);
            if (!bRet2) {
                this.mResponseListener.onConnectResponseReceived(this, ERROR.FAIL);
            }
        }
    }

    private void innerDisconnect() {
        IAppControlAPI iAppControlAPI = this.mIAppComponent;
        if (iAppControlAPI != null) {
            iAppControlAPI.sendMouseDestroy();
            this.mIAppComponent.stopControl();
        }
        boolean isConnected = isConnected();
        this.mIsConnected = isConnected;
        ViewController.IEventListener iEventListener = this.mEventListener;
        if (iEventListener != null) {
            if (isConnected) {
                iEventListener.onDisconnected(this, ERROR.SUCCESS);
            } else {
                iEventListener.onDisconnected(this, ERROR.SERVICE_NOT_CONNECTED);
            }
        }
        this.mIsConnected = false;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void disconnect() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            ViewController.IResponseListener iResponseListener = this.mResponseListener;
            if (iResponseListener != null) {
                iResponseListener.onDisconnectResponseReceived(this, ERROR.SERVICE_NOT_CONNECTED);
            }
            innerDisconnect();
            return;
        }
        if (!this.mIsConnected) {
            ViewController.IResponseListener iResponseListener2 = this.mResponseListener;
            if (iResponseListener2 != null) {
                iResponseListener2.onDisconnectResponseReceived(this, ERROR.INVALID_DEVICE);
            }
            innerDisconnect();
            return;
        }
        innerDisconnect();
        ViewController.IResponseListener iResponseListener3 = this.mResponseListener;
        if (iResponseListener3 != null) {
            iResponseListener3.onDisconnectResponseReceived(this, ERROR.SUCCESS);
        }
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return null;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public int getViewWidth() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return -1;
        }
        return (int) this.mTvWidth;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public int getViewHeight() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return -1;
        }
        return (int) this.mTvHeight;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public boolean isConnected() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return false;
        }
        return this.mIsConnected;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void setEventListener(ViewController.IEventListener listener) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG, "setEventListener error! AllShareService is not connected");
            return;
        }
        this.mEventListener = listener;
        boolean z = this.mIsSubscribed;
        if (!z && listener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mAllShareEventHandler);
            this.mIsSubscribed = true;
        } else if (z && listener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mAllShareEventHandler);
            this.mIsSubscribed = false;
        }
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void setViewAngle(int angle) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector != null && iAllShareConnector.isAllShareServiceConnected() && this.mIsConnected) {
            this.mIAppComponent.sendMultiTouchEvent(37, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0, 0, 0);
            this.mIAppComponent.sendMultiTouchEvent(39, 100.0d, angle, 0, 0);
            this.mIAppComponent.sendMultiTouchEvent(38, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0, 0, 0);
        }
    }

    private void initZoom() {
        this.mImageWidth = 0.0f;
        this.mImageHeight = 0.0f;
        this.mRelativeZoomRate = 1.0f;
        this.mImageRatio = 0.0f;
        this.mMobilePhoneRatio = 0.0f;
        this.mMarginX = 0;
        this.mMarginY = 0;
        this.mOrgCenterX = 0;
        this.mOrgCenterY = 0;
        this.mOrgX = 0;
        this.mOrgY = 0;
        this.mTvOrgX = 0.0f;
        this.mTvOrgY = 0.0f;
        this.mTvOrgX0 = 0.0f;
        this.mTvOrgY0 = 0.0f;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void zoom(int cx, int cy, int zoomPercent, int angle) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector != null && iAllShareConnector.isAllShareServiceConnected() && this.mIsConnected) {
            this.mIAppComponent.sendMultiTouchEvent(37, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0, cx, cy);
            this.mIAppComponent.sendMultiTouchEvent(39, zoomPercent, angle, cx, cy);
            this.mIAppComponent.sendMultiTouchEvent(38, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0, cx, cy);
        }
    }

    private void initZoomedImageSize(float mobileWidth, float mobileHeight) {
        float f = this.mMobilePhoneRatio;
        float f2 = this.mImageRatio;
        if (f >= f2) {
            this.mZoomedImageWidth = f2 * mobileHeight;
            this.mZoomedImageHeight = mobileHeight;
        } else {
            this.mZoomedImageWidth = mobileWidth;
            this.mZoomedImageHeight = f2 * mobileWidth;
        }
    }

    private void clacZoomedImageSize() {
        float f = this.mZoomedImageWidth;
        float f2 = this.mRelativeZoomRate;
        this.mZoomedImageWidth = f * f2;
        this.mZoomedImageHeight *= f2;
    }

    private void calcMobileResolution() {
        Context context = ServiceConnector.getContext();
        if (context == null) {
            return;
        }
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        this.mMobileWidth = outSize.f85x;
        float f = outSize.f86y;
        this.mMobileHeight = f;
        this.mMobilePhoneRatio = this.mMobileWidth / f;
    }

    private void calcOrgImageResolution(int sourceWidth, int sourceHeight) {
        float f = sourceWidth;
        this.mImageWidth = f;
        float f2 = sourceHeight;
        this.mImageHeight = f2;
        this.mImageRatio = f / f2;
    }

    private float getMagicRate() {
        float magicRate;
        float f = this.mTvHeight;
        float f2 = this.mImageHeight;
        if (f >= f2) {
            float f3 = this.mTvWidth;
            float f4 = this.mImageWidth;
            if (f3 >= f4) {
                if (this.mMobilePhoneRatio >= this.mImageRatio) {
                    magicRate = f4 / ((this.mMobileHeight * f4) / f2);
                } else {
                    magicRate = f4 / this.mMobileWidth;
                }
                DLog.i_api(TAG, "[zoom] magicRate : " + magicRate);
                return magicRate;
            }
        }
        float f5 = this.mTvRatio;
        float f6 = this.mImageRatio;
        if (f5 >= f6) {
            if (this.mMobilePhoneRatio >= f6) {
                magicRate = f / this.mMobileHeight;
            } else {
                float tempWidth = (f * this.mImageWidth) / f2;
                magicRate = tempWidth / this.mMobileWidth;
            }
        } else if (this.mMobilePhoneRatio >= f6) {
            magicRate = this.mTvWidth / this.mMobileWidth;
        } else {
            float tempWidth2 = (this.mTvWidth * f2) / this.mImageWidth;
            magicRate = tempWidth2 / this.mMobileHeight;
        }
        DLog.i_api(TAG, "[zoom] magicRate : " + magicRate);
        return magicRate;
    }

    private void calcMargin() {
        if (this.mMobilePhoneRatio >= this.mImageRatio) {
            this.mMarginX = (int) ((this.mMobileWidth - ((this.mImageWidth * this.mMobileHeight) / this.mImageHeight)) / 2.0f);
            this.mMarginY = 0;
        } else {
            this.mMarginX = 0;
            this.mMarginY = (int) ((this.mMobileHeight - ((this.mImageHeight * this.mMobileWidth) / this.mImageWidth)) / 2.0f);
        }
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void zoom(int cx, int cy, int zoomPercent, int angle, int sourceWidth, int sourceHeight) {
        int zoomPercent2;
        boolean isDoubleTapPinch;
        int tvZoomCenterX;
        int tvZoomCenterY;
        int tvZoomCenterY2;
        int zoomPercent3 = zoomPercent;
        DLog.i_api(TAG, "[zoom] cx : " + cx + " cy : " + cy + " zoomPercent : " + zoomPercent3 + " angle : " + angle);
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return;
        }
        if (((int) this.mImageWidth) != sourceWidth || ((int) this.mImageHeight) != sourceHeight || this.mPrevAngle != angle) {
            DLog.i_api(TAG, "[zoom] mImageWidth : " + this.mImageWidth + " mImageHeight : " + this.mImageHeight + " sourceWidth : " + sourceWidth + " sourceHeight : " + sourceHeight);
            initZoom();
            calcMobileResolution();
            calcOrgImageResolution(sourceWidth, sourceHeight);
            initZoomedImageSize(this.mMobileWidth, this.mMobileHeight);
            DLog.i_api(TAG, "[zoom] mTvWidth : " + this.mTvWidth + " mTvHeight : " + this.mTvHeight + " mImageWidth : " + this.mImageWidth + " mImageHeight : " + this.mImageHeight);
            float f = this.mTvHeight;
            float f2 = this.mImageHeight;
            if (f >= f2) {
                float f3 = this.mTvWidth;
                float f4 = this.mImageWidth;
                if (f3 >= f4) {
                    this.mTvOrgX0 = (f3 - f4) / 2.0f;
                    this.mTvOrgY0 = (f - f2) / 2.0f;
                    DLog.i_api(TAG, "[zoom] mTvOrgX0 : " + this.mTvOrgX0 + " mTvOrgY0 : " + this.mTvOrgY0 + " mTvOrgX : " + this.mTvOrgX + " mTvOrgY : " + this.mTvOrgY);
                    this.mTvOrgX = this.mTvOrgX0;
                    this.mTvOrgY = this.mTvOrgY0;
                    this.mPrevAngle = angle;
                    calcMargin();
                    this.mOrgX = this.mMarginX;
                    this.mOrgY = this.mMarginY;
                }
            }
            if (this.mTvRatio >= this.mImageRatio) {
                this.mTvOrgX0 = (int) ((this.mTvWidth - ((this.mImageWidth / f2) * f)) / 2.0f);
                this.mTvOrgY0 = 0.0f;
            } else {
                this.mTvOrgX0 = 0.0f;
                this.mTvOrgY0 = (int) ((f - ((f2 / this.mImageWidth) * this.mTvWidth)) / 2.0f);
            }
            DLog.i_api(TAG, "[zoom] mTvOrgX0 : " + this.mTvOrgX0 + " mTvOrgY0 : " + this.mTvOrgY0 + " mTvOrgX : " + this.mTvOrgX + " mTvOrgY : " + this.mTvOrgY);
            this.mTvOrgX = this.mTvOrgX0;
            this.mTvOrgY = this.mTvOrgY0;
            this.mPrevAngle = angle;
            calcMargin();
            this.mOrgX = this.mMarginX;
            this.mOrgY = this.mMarginY;
        }
        if (this.mIsConnected) {
            calcMobileResolution();
            if (zoomPercent3 == 0 || this.mPrevAngle != angle) {
                if (zoomPercent3 < 100) {
                    zoomPercent3 = 0;
                }
                this.mRelativeZoomRate = 1.0f;
                this.mPrevAbsZoomRate = 1.0f;
                this.mAbsZoomRate = 1.0f;
                initZoomedImageSize(this.mMobileWidth, this.mMobileHeight);
                calcMargin();
                this.mOrgX = this.mMarginX;
                this.mOrgY = this.mMarginY;
                zoomPercent2 = zoomPercent3;
                isDoubleTapPinch = true;
            } else {
                float f5 = zoomPercent3 / 100.0f;
                this.mRelativeZoomRate = f5;
                float f6 = this.mAbsZoomRate * f5;
                this.mAbsZoomRate = f6;
                if (f6 >= 1.0f) {
                    zoomPercent2 = zoomPercent3;
                    isDoubleTapPinch = false;
                } else {
                    if (zoomPercent3 < 100) {
                        zoomPercent3 = 0;
                    }
                    this.mRelativeZoomRate = 1.0f;
                    this.mPrevAbsZoomRate = 1.0f;
                    this.mAbsZoomRate = 1.0f;
                    initZoomedImageSize(this.mMobileWidth, this.mMobileHeight);
                    calcMargin();
                    this.mOrgX = this.mMarginX;
                    this.mOrgY = this.mMarginY;
                    zoomPercent2 = zoomPercent3;
                    isDoubleTapPinch = true;
                }
            }
            clacZoomedImageSize();
            DLog.i_api(TAG, "[zoom] mMobileWidth : " + this.mMobileWidth + " mMobileHeight : " + this.mMobileHeight + " mMobilePhoneRatio : " + this.mMobilePhoneRatio + " mRelativeZoomRate : " + this.mRelativeZoomRate);
            float magicRate = getMagicRate();
            calcMargin();
            int i = cx - this.mOrgX;
            this.mOrgCenterX = i;
            int i2 = cy - this.mOrgY;
            this.mOrgCenterY = i2;
            if (i < 0) {
                this.mOrgCenterX = 0;
            }
            if (i2 < 0) {
                this.mOrgCenterY = 0;
            }
            DLog.i_api(TAG, "[zoom] mMarginX : " + this.mMarginX + " mMarginY : " + this.mMarginY + " mOrgX : " + this.mOrgX + " mOrgY : " + this.mOrgY + " mOrgCenterX : " + this.mOrgCenterX + " mOrgCenterY : " + this.mOrgCenterY);
            if (isDoubleTapPinch) {
                tvZoomCenterX = (int) (this.mTvWidth / 2.0f);
                tvZoomCenterY = (int) (this.mTvHeight / 2.0f);
            } else {
                int tvZoomCenterX2 = this.mOrgCenterX;
                float f7 = this.mPrevAbsZoomRate;
                tvZoomCenterX = (int) (((tvZoomCenterX2 / f7) * magicRate) + this.mTvOrgX);
                tvZoomCenterY = (int) (((this.mOrgCenterY / f7) * magicRate) + this.mTvOrgY);
            }
            if (this.mZoomedImageWidth <= this.mMobileWidth) {
                tvZoomCenterX = (int) (this.mTvWidth / 2.0f);
            }
            if (this.mZoomedImageHeight > this.mMobileHeight) {
                tvZoomCenterY2 = tvZoomCenterY;
            } else {
                int tvZoomCenterY3 = (int) (this.mTvHeight / 2.0f);
                tvZoomCenterY2 = tvZoomCenterY3;
            }
            DLog.i_api(TAG, "[zoom] tvCenterX : " + tvZoomCenterX + " tvCenterY : " + tvZoomCenterY2 + " mTvOrgX : " + this.mTvOrgX + " mTvOrgY : " + this.mTvOrgY + " mOrgCenterX : " + this.mOrgCenterX + " mOrgCenterY : " + this.mOrgCenterY);
            float f8 = this.mOrgX;
            float f9 = this.mOrgCenterX;
            float f10 = this.mRelativeZoomRate;
            this.mOrgX = (int) (f8 - (f9 * (f10 - 1.0f)));
            this.mOrgY = (int) (this.mOrgY - (this.mOrgCenterY * (f10 - 1.0f)));
            int i3 = tvZoomCenterX;
            int i4 = tvZoomCenterY2;
            this.mIAppComponent.sendMultiTouchEvent(37, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0, i3, i4);
            int zoomPercent4 = tvZoomCenterY2;
            this.mIAppComponent.sendMultiTouchEvent(39, zoomPercent2, angle, tvZoomCenterX, zoomPercent4);
            this.mIAppComponent.sendMultiTouchEvent(38, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0, i3, i4);
            this.mPrevAbsZoomRate *= this.mRelativeZoomRate;
            DLog.i_api(TAG, "[zoom] mOrgX : " + this.mOrgX + " mOrgY : " + this.mOrgY + " mTvOrgX : " + this.mTvOrgX + " mTvOrgY : " + this.mTvOrgY);
            DLog.i_api(TAG, "[zoom] mPrevAbsZoomRate : " + this.mPrevAbsZoomRate);
        }
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void move(int cx, int cy, boolean isReleased) {
        Context context;
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected() || !this.mIsConnected || (context = ServiceConnector.getContext()) == null) {
            return;
        }
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        int mobileWidth = outSize.f85x;
        int mobileHeight = outSize.f86y;
        float widthRatio = this.mTvWidth / mobileWidth;
        float widthRatio2 = this.mTvHeight;
        float heightRatio = widthRatio2 / mobileHeight;
        int tvX = (int) (cx * widthRatio);
        int tvY = (int) (cy * heightRatio);
        if (isReleased) {
            this.mIAppComponent.setTouchGestureTouchMode(0);
            this.mIAppComponent.sendTouchGestureEvent(12, tvX, tvY, 0, 0);
            this.mIAppComponent.sendTouchGestureEvent(13, tvX, tvY, 0, 0);
            return;
        }
        this.mIAppComponent.sendTouchGestureEvent(13, tvX, tvY, 0, 0);
    }

    static class LastChangeEvent {
        private String mEventID;
        private String mPowerOff;

        LastChangeEvent() {
        }

        private void clean() {
            this.mEventID = null;
            this.mPowerOff = null;
        }

        private void setValue(String key, String value) {
            if (key != null) {
                if (key.equalsIgnoreCase("EventID")) {
                    this.mEventID = value;
                } else if (key.equalsIgnoreCase("PowerOFF")) {
                    this.mPowerOff = value;
                }
            }
        }

        public String getEventID() {
            String str = this.mEventID;
            if (str != null) {
                return str;
            }
            return "";
        }

        public String getPowerOff() {
            String str = this.mPowerOff;
            if (str != null) {
                return str;
            }
            return "";
        }

        public void parseFromXML(String xml) {
            clean();
            if (xml == null || xml.length() <= 0) {
                DLog.w_api(ViewControllerImpl.TAG, "[ViewControl] LastChangeEvent.parseFromXML() paramter xml is null or empty!");
                return;
            }
            XmlPullParser parser = Xml.newPullParser();
            String key = null;
            String value = null;
            try {
                parser.setInput(new StringReader(xml));
                for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                    switch (eventType) {
                        case 2:
                            key = parser.getName();
                            break;
                        case 3:
                            if (key != null && value != null) {
                                setValue(key, value);
                                break;
                            }
                            break;
                        case 4:
                            value = parser.getText();
                            break;
                    }
                }
            } catch (IOException e) {
                DLog.w_api(ViewControllerImpl.TAG, "[ViewControl] LastChangeEvent.parseFromXML() exception : " + e.toString());
            } catch (NullPointerException e2) {
            } catch (XmlPullParserException e3) {
                DLog.w_api(ViewControllerImpl.TAG, "[ViewControl] LastChangeEvent.parseFromXML() exception : " + e3.toString());
            }
        }
    }

    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mAllShareEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.media.ViewController
    public void setResponseListener(ViewController.IResponseListener listener) {
        this.mResponseListener = listener;
    }
}
