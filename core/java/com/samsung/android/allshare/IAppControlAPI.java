package com.samsung.android.allshare;

import android.p009os.Message;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes5.dex */
class IAppControlAPI {
  private static final int DTVSERVER_PORT = 55000;
  private static final String TAG = "IAppControlAPI";
  private String mDeviceId;
  private String mServerIp = "";
  TVMessageSender mTvMsgSender = null;
  TVMessageListener mTvMsgListener = null;
  private int mSetIpaddress = 0;
  private boolean bIsRunningConnection = false;
  private Object mLock = new Object();
  public ExecutorService mExcutor = Executors.newCachedThreadPool();
  private IControlEventListener mEventListener = null;

  public interface IControlEventListener {
    void controlEvent(EventSync eventSync);
  }

  public void sendSocketIsNotConnectedEvent() {
    EventSync event = new EventSync();
    event.mWhat = 9999;
    IControlEventListener iControlEventListener = this.mEventListener;
    if (iControlEventListener != null) {
      iControlEventListener.controlEvent(event);
    }
  }

  public void setOnEventListener(IControlEventListener listener) {
    this.mEventListener = listener;
  }

  private void setIpaddress(String ipaddress, int port) {
    this.mServerIp = ipaddress;
    this.mSetIpaddress = 1;
  }

  private void initControl(String Deviceid) {
    this.mDeviceId = Deviceid;
    TVMessageSender tVMessageSender = new TVMessageSender(this);
    this.mTvMsgSender = tVMessageSender;
    tVMessageSender.start();
  }

  private void initControlReceiver(TVMessageSender tl) {
    TVMessageListener tVMessageListener = new TVMessageListener(tl);
    this.mTvMsgListener = tVMessageListener;
    tVMessageListener.setOnEventListener(this.mEventListener);
    this.mTvMsgListener.start();
  }

  private void sendMsgToDevice(Message msg) {
    TVMessageSender tVMessageSender = this.mTvMsgSender;
    if (tVMessageSender != null && tVMessageSender.mHandler != null) {
      this.mTvMsgSender.mHandler.sendMessage(msg);
    }
  }

  public void startControl(final NetworkSocketInfo netinfo, String devicdid) {
    setIpaddress(netinfo.mIpAddress, netinfo.mPort);
    initControl(devicdid);
    TVMessageSender tVMessageSender = this.mTvMsgSender;
    if (tVMessageSender != null) {
      if (this.mTvMsgListener == null) {
        initControlReceiver(tVMessageSender);
      }
      this.mExcutor.execute(
          new Runnable() { // from class: com.samsung.android.allshare.IAppControlAPI.1
            @Override // java.lang.Runnable
            public void run() {
              IAppControlAPI.this.mTvMsgSender.initSender(netinfo);
              IAppControlAPI.this.sendMouseCreate();
            }
          });
    }
  }

  public boolean startControl(String mac, String ipaddress, String deviceid) {
    this.bIsRunningConnection = true;
    final NetworkSocketInfo inet = new NetworkSocketInfo();
    setIpaddress(ipaddress, DTVSERVER_PORT);
    inet.mMacAddr = mac;
    inet.mIpAddress = ipaddress;
    inet.mPort = DTVSERVER_PORT;
    inet.mProtocol = 1;
    initControl(deviceid);
    if (this.mTvMsgSender != null) {
      this.mExcutor.execute(
          new Runnable() { // from class: com.samsung.android.allshare.IAppControlAPI.2
            @Override // java.lang.Runnable
            public void run() {
              synchronized (IAppControlAPI.this.mLock) {
                if (IAppControlAPI.this.mTvMsgSender != null) {
                  IAppControlAPI.this.mTvMsgSender.initSender(inet);
                  IAppControlAPI.this.createControlReceiver();
                  IAppControlAPI.this.sendAuthentication();
                }
                IAppControlAPI.this.bIsRunningConnection = false;
              }
            }
          });
      return true;
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void createControlReceiver() {
    TVMessageSender tVMessageSender;
    if (this.mTvMsgListener == null && (tVMessageSender = this.mTvMsgSender) != null) {
      initControlReceiver(tVMessageSender);
    }
  }

  public boolean stopControl() {
    if (this.bIsRunningConnection) {
      return false;
    }
    removeControl();
    synchronized (this.mLock) {
      this.mTvMsgSender = null;
      this.mTvMsgListener = null;
    }
    return true;
  }

  public boolean addControlEventListener(IControlEventListener listener) {
    TVMessageListener tVMessageListener = this.mTvMsgListener;
    if (tVMessageListener != null) {
      tVMessageListener.setOnEventListener(this.mEventListener);
      return true;
    }
    return false;
  }

  public boolean removeControlEventListener(IControlEventListener listener) {
    return true;
  }

  public void removeControl() {
    Message msg = Message.obtain();
    if (msg != null) {
      msg.what = 0;
    }
    sendMsgToDevice(msg);
    this.mTvMsgListener = null;
    this.mSetIpaddress = 0;
  }

  public int startControlClient(final NetworkSocketInfo netinfo) {
    setIpaddress(netinfo.mIpAddress, netinfo.mPort);
    if (netinfo.mProtocol == 1
        && this.mDeviceId.equals("D0000000001")
        && this.mTvMsgSender != null) {
      this.mExcutor.execute(
          new Runnable() { // from class: com.samsung.android.allshare.IAppControlAPI.3
            @Override // java.lang.Runnable
            public void run() {
              IAppControlAPI.this.mTvMsgSender.initSender(netinfo);
              IAppControlAPI.this.mTvMsgListener.setOnEventListener(
                  IAppControlAPI.this.mEventListener);
              IAppControlAPI.this.mTvMsgListener.start();
            }
          });
      return 1;
    }
    return 0;
  }

  public void setTouchGestureTouchMode(int mode) {
    Message msg = Message.obtain();
    msg.what = 9;
    msg.arg1 = mode;
    sendMsgToDevice(msg);
  }

  public void sendAuthentication() {
    Message msg = Message.obtain();
    msg.what = 52;
    sendMsgToDevice(msg);
  }

  public void sendKeyEvent(int keycode) {
    Message msg = Message.obtain();
    msg.what = 1;
    msg.arg1 = keycode;
    sendMsgToDevice(msg);
  }

  public void sendKeyboardEvent(Message msg) {
    Message.obtain();
    sendMsgToDevice(msg);
  }

  public void sendKeyboardStringCommand(String keycode, int encoding) {
    Message msg = Message.obtain();
    msg.what = 1;
    msg.arg1 = encoding;
    msg.obj = keycode;
    sendMsgToDevice(msg);
  }

  public void sendRemoteControlKey(String keycode, int mode) {
    Message msg = Message.obtain();
    msg.what = 14;
    msg.arg1 = mode;
    msg.obj = keycode;
    sendMsgToDevice(msg);
  }

  public void sendIntCommand(int command_type, int keybutton_cmd) {
    Message msg = Message.obtain();
    msg.what = command_type;
    msg.arg1 = keybutton_cmd;
    sendMsgToDevice(msg);
  }

  public void sendTouchGestureEvent(int nType, int x, int y, int dx, int dy) {
    Message msg = Message.obtain();
    msg.what = 5;
    EventTouch ieventTouch = new EventTouch();
    ieventTouch.mType = nType;
    ieventTouch.f2948mX = x;
    ieventTouch.f2949mY = y;
    ieventTouch.mDX = dx;
    ieventTouch.mDY = dy;
    msg.obj = ieventTouch;
    sendMsgToDevice(msg);
  }

  public void sendMultiTouchEvent(int eventType, double zoomRate, int angle, int cx, int cy) {
    Message msg = Message.obtain();
    msg.what = 6;
    EventTouch ieventTouch = new EventTouch();
    ieventTouch.mType = eventType;
    ieventTouch.mDistance = (int) zoomRate;
    ieventTouch.mDegree = angle;
    ieventTouch.f2948mX = cx;
    ieventTouch.f2949mY = cy;
    msg.obj = ieventTouch;
    sendMsgToDevice(msg);
  }

  public void sendKeyboardEnd() {
    Message msg = Message.obtain();
    msg.what = 8;
    sendMsgToDevice(msg);
  }

  public void sendMouseCreate() {
    Message msg = Message.obtain();
    msg.what = 15;
    sendMsgToDevice(msg);
  }

  public void sendMouseDestroy() {
    Message msg = Message.obtain();
    msg.what = 16;
    sendMsgToDevice(msg);
  }

  public void sendMouseProcess(int eventType, int x, int y, int dx, int dy, int button) {
    Message msg = Message.obtain();
    msg.what = 2;
    EventMouse ieventMouse = new EventMouse();
    ieventMouse.mType = eventType;
    ieventMouse.f2946mX = x;
    ieventMouse.f2947mY = y;
    ieventMouse.mDX = dx;
    ieventMouse.mDY = dy;
    ieventMouse.mButton = button;
    msg.obj = ieventMouse;
    sendMsgToDevice(msg);
  }
}
