package com.samsung.android.allshare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.p009os.Bundle;
import com.samsung.android.allshare.media.ViewController2;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes5.dex */
final class ViewController2Impl extends ViewController2 implements IBundleHolder {
  private static final String TAG = "ViewController2Impl";
  private IAllShareConnector mAllShareConnector;
  private DeviceImpl mDeviceImpl;
  private String mIPAddress;
  private String mMACAddress;
  private ViewController2.IViewController2EventListener mEventListener = null;
  private ViewController2.IViewController2ResponseListener mResponseListener = null;
  private boolean mIsConnected = false;
  private boolean mIsSubscribed = false;
  private Socket mSocket = null;
  private DataOutputStream mDos = null;
  private int mPacketSize = 13;
  private ExecutorService mExcutor = Executors.newCachedThreadPool();
  private boolean mConnectResult = true;
  AllShareResponseHandler mResponseHandler =
      new AllShareResponseHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.ViewController2Impl.1
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
          String actionID = cvm.getActionID();
          Bundle resBundle = cvm.getBundle();
          if (actionID == null
              || resBundle == null
              || ViewController2Impl.this.mResponseListener == null) {
            return;
          }
          ERROR error = ERROR.FAIL;
          String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
          if (errorStr != null) {
            error = ERROR.stringToEnum(errorStr);
          }
          int portNum = 0;
          if (actionID.equals(AllShareAction.ACTION_VIEWCONTROLLER_REQUEST_GET_ZOOM_PORT)) {
            if (error.equals(ERROR.SUCCESS)) {
              portNum = resBundle.getInt(AllShareKey.BUNDLE_INT_ZOOM_PORT_NUMBER);
              DLog.i_api(ViewController2Impl.TAG, "ZoomPort : " + portNum);
            } else {
              ViewController2Impl.this.mResponseListener.onConnectResponseReceived(
                  ViewController2Impl.this, error);
              return;
            }
          }
          ViewController2Impl viewController2Impl = ViewController2Impl.this;
          if (viewController2Impl.connect(viewController2Impl.mIPAddress, portNum)) {
            ViewController2Impl.this.mResponseListener.onConnectResponseReceived(
                ViewController2Impl.this, ERROR.SUCCESS);
          } else {
            ViewController2Impl.this.mResponseListener.onConnectResponseReceived(
                ViewController2Impl.this, ERROR.FAIL);
          }
        }
      };
  private AllShareEventHandler mAllShareEventHandler =
      new AllShareEventHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.ViewController2Impl.2
        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
          cvm.getBundle();
        }
      };

  ViewController2Impl(IAllShareConnector connector, DeviceImpl deviceImpl) {
    String str;
    this.mAllShareConnector = null;
    this.mDeviceImpl = null;
    this.mMACAddress = null;
    this.mIPAddress = null;
    if (connector == null) {
      DLog.w_api(TAG, "Connection FAIL: AllShare Service Connector does not exist");
      return;
    }
    Context context = ServiceConnector.getContext();
    if (context != null) {
      ConnectivityManager conManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        this.mMACAddress =
            strMACPart[0]
                + ":"
                + strMACPart[1]
                + ":"
                + strMACPart[2]
                + ":"
                + strMACPart[3]
                + ":"
                + strMACPart[4]
                + ":"
                + strMACPart[5];
      }
    }
    this.mAllShareConnector = connector;
    this.mDeviceImpl = deviceImpl;
    this.mIPAddress = deviceImpl.getIPAddress();
  }

  public String getID() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getID();
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public void connect() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      ViewController2.IViewController2ResponseListener iViewController2ResponseListener =
          this.mResponseListener;
      if (iViewController2ResponseListener != null) {
        iViewController2ResponseListener.onConnectResponseReceived(
            this, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_VIEWCONTROLLER_REQUEST_GET_ZOOM_PORT);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public void disconnect() {
    this.mIsConnected = false;
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      ViewController2.IViewController2ResponseListener iViewController2ResponseListener =
          this.mResponseListener;
      if (iViewController2ResponseListener != null) {
        iViewController2ResponseListener.onDisconnectResponseReceived(
            this, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    ViewController2.IViewController2ResponseListener iViewController2ResponseListener2 =
        this.mResponseListener;
    if (iViewController2ResponseListener2 != null) {
      this.mIsConnected = false;
      iViewController2ResponseListener2.onDisconnectResponseReceived(this, ERROR.SUCCESS);
    }
  }

  @Override // com.sec.android.allshare.iface.IBundleHolder
  public Bundle getBundle() {
    return null;
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public boolean isConnected() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG, "setEventListener error! AllShareService is not connected");
      return false;
    }
    return this.mIsConnected;
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public void setViewAngle(int angle) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null
        || !iAllShareConnector.isAllShareServiceConnected()
        || this.mSocket == null) {
      return;
    }
    byte[] bArr = new byte[4];
    byte[] buf = new byte[this.mPacketSize];
    buf[0] = 4;
    byte[] buf1 = int2bytes(angle);
    System.arraycopy(buf1, 0, buf, 1, buf1.length);
    try {
      DataOutputStream dataOutputStream = new DataOutputStream(this.mSocket.getOutputStream());
      this.mDos = dataOutputStream;
      dataOutputStream.write(buf, 0, this.mPacketSize);
      this.mDos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public void zoom(int posX, int posY, float zoomRatio) {
    if (this.mSocket == null) {
      return;
    }
    byte[] bArr = new byte[4];
    byte[] bArr2 = new byte[4];
    byte[] bArr3 = new byte[4];
    byte[] buf = new byte[this.mPacketSize];
    buf[0] = 9;
    byte[] buf1 = float2bytes(posX);
    byte[] buf2 = float2bytes(posY);
    byte[] buf3 = float2bytes(zoomRatio);
    System.arraycopy(buf1, 0, buf, 1, buf1.length);
    System.arraycopy(buf2, 0, buf, buf1.length + 1, buf2.length);
    System.arraycopy(buf3, 0, buf, buf1.length + buf2.length + 1, buf3.length);
    try {
      DataOutputStream dataOutputStream = new DataOutputStream(this.mSocket.getOutputStream());
      this.mDos = dataOutputStream;
      dataOutputStream.write(buf, 0, this.mPacketSize);
      this.mDos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public void setEventListener(ViewController2.IViewController2EventListener listener) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG, "setEventListener error! AllShareService is not connected");
      return;
    }
    this.mEventListener = listener;
    boolean z = this.mIsSubscribed;
    if (!z && listener != null) {
      this.mAllShareConnector.subscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE,
          this.mDeviceImpl.getBundle(),
          this.mAllShareEventHandler);
      this.mIsSubscribed = true;
    } else if (z && listener == null) {
      this.mAllShareConnector.unsubscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE,
          this.mDeviceImpl.getBundle(),
          this.mAllShareEventHandler);
      this.mIsSubscribed = false;
    }
  }

  @Override // com.samsung.android.allshare.media.ViewController2
  public void setResponseListener(ViewController2.IViewController2ResponseListener listener) {
    this.mResponseListener = listener;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean connect(final String serverIp, final int port) {
    this.mSocket = null;
    this.mExcutor.execute(
        new Runnable() { // from class: com.samsung.android.allshare.ViewController2Impl.3
          @Override // java.lang.Runnable
          public void run() {
            try {
              ViewController2Impl.this.mSocket = new Socket(serverIp, port);
            } catch (UnknownHostException e) {
              ViewController2Impl.this.mConnectResult = false;
              e.printStackTrace();
            } catch (IOException e2) {
              ViewController2Impl.this.mConnectResult = false;
              e2.printStackTrace();
            }
          }
        });
    boolean z = this.mConnectResult;
    this.mIsConnected = z;
    return z;
  }

  private byte[] int2bytes(int i) {
    byte[] dest = {
      (byte) ((i >> 25) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)
    };
    return dest;
  }

  private byte[] float2bytes(float value) {
    int intBits = Float.floatToIntBits(value);
    byte[] array = {
      (byte) ((intBits & 255) >> 0),
      (byte) ((65280 & intBits) >> 8),
      (byte) ((16711680 & intBits) >> 16),
      (byte) (((-16777216) & intBits) >> 24)
    };
    return array;
  }

  public void removeEventHandler() {
    this.mAllShareConnector.unsubscribeAllShareEvent(
        AllShareEvent.EVENT_DEVICE_SUBSCRIBE,
        this.mDeviceImpl.getBundle(),
        this.mAllShareEventHandler);
    this.mIsSubscribed = false;
  }
}
