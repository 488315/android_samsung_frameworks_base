package com.samsung.android.allshare;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import com.android.internal.accessibility.common.ShortcutConstants;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
class TVMessageSender extends Thread {
  private static final String TAG = "TVMessageSender";
  ByteBuffer mBuf;
  IAppControlAPI mControlAPI;
  String mDeviceName;
  private SocketAddress mRemoteAddr;
  public Socket mSocket;
  private int mCIPORT = 2020;
  int mProtocol = 0;
  String mServerIp = null;
  String mLocalAddr = null;
  String mMacAddr = null;
  String mTargetName = null;
  public String mDeviceClass = null;
  String mTargetDtvName = null;
  OutputStream mOutStream = null;
  DataOutputStream mDOutStream = null;
  public ExecutorService mExcutor = Executors.newCachedThreadPool();
  public Handler mHandler = null;

  public void initSender(NetworkSocketInfo netinfo) {
    this.mProtocol = netinfo.mProtocol;
    this.mServerIp = netinfo.mIpAddress;
    this.mCIPORT = netinfo.mPort;
    this.mDeviceClass = netinfo.mDeviceClass;
    this.mMacAddr = netinfo.mMacAddr.replace(ShortcutConstants.SERVICES_SEPARATOR, '-');
    if (this.mProtocol == 1) {
      Socket socket = this.mSocket;
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          DLog.w_api(TAG, "", e);
        }
        this.mSocket = null;
      }
      this.mBuf = ByteBuffer.allocate(4096);
      this.mSocket = new Socket();
      InetSocketAddress inetSocketAddress = new InetSocketAddress(this.mServerIp, this.mCIPORT);
      this.mRemoteAddr = inetSocketAddress;
      try {
        this.mSocket.connect(inetSocketAddress, 3000);
        if (this.mSocket.isConnected()) {
          DLog.i_api(TAG, "initSender : mSocket is connected.");
          this.mBuf.order(ByteOrder.LITTLE_ENDIAN);
          this.mLocalAddr = this.mSocket.getLocalAddress().toString().substring(1);
          try {
            this.mOutStream = this.mSocket.getOutputStream();
            this.mDOutStream = new DataOutputStream(this.mOutStream);
          } catch (IOException e1) {
            DLog.w_api(TAG, "", e1);
          }
          return;
        }
        DLog.i_api(TAG, "initSender : mSocket connecting is failed.");
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
  }

  public TVMessageSender(IAppControlAPI controlAPI) {
    this.mDeviceName = null;
    this.mControlAPI = null;
    this.mControlAPI = controlAPI;
    this.mDeviceName = Build.MODEL;
  }

  @Override // java.lang.Thread, java.lang.Runnable
  public void run() {
    Looper.prepare();
    createHandler();
    Looper.loop();
  }

  public void closeSender() {
    Socket socket;
    if (this.mProtocol == 1 && (socket = this.mSocket) != null) {
      if (socket.isConnected()) {
        try {
          this.mSocket.close();
        } catch (IOException e) {
          DLog.w_api(TAG, "", e);
        }
      }
      this.mSocket = null;
    }
    this.mBuf.clear();
    this.mBuf = null;
  }

  public void sendKeyboardEnd() {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 2 + targetLen + 5;
    DLog.i_api(TAG, "sendDTVKeyboardEnd targetLen " + targetLen);
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 2);
    this.mBuf.putShort((short) 4);
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendAuthentication() {
    StringBuffer strBuffer = new StringBuffer(4096);
    strBuffer.append("omnia.iapp.samsung");
    this.mTargetDtvName = strBuffer.toString();
    String ipAddr64 = Base64.encodeToString(this.mLocalAddr.getBytes(), 0);
    String macAddr64 = Base64.encodeToString(this.mMacAddr.getBytes(), 0);
    String deviceName64 = Base64.encodeToString(this.mDeviceName.getBytes(), 0);
    int ipLen = ipAddr64.length();
    int macLen = macAddr64.length();
    int nameLen = deviceName64.length();
    int targetLen = this.mTargetDtvName.length();
    int dataLen = ipLen + macLen + nameLen + 8;
    int packetLen = dataLen + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) dataLen);
    this.mBuf.putShort((short) 100);
    this.mBuf.putShort((short) ipLen);
    this.mBuf.put(ipAddr64.getBytes());
    this.mBuf.putShort((short) macLen);
    this.mBuf.put(macAddr64.getBytes());
    this.mBuf.putShort((short) nameLen);
    this.mBuf.put(deviceName64.getBytes());
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendTouchGestureSemanticEvent(int eventType, int distance, int degree, int x, int y) {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 22 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 22);
    this.mBuf.putShort((short) 8);
    this.mBuf.putInt(eventType);
    this.mBuf.putInt(distance);
    this.mBuf.putInt(degree);
    this.mBuf.putInt(x);
    this.mBuf.putInt(y);
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendTouchGuestureEvent2012(
      int eventType, int accellevel, int fingerid, int x, int y, int dx, int dy) {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 30 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 30);
    this.mBuf.putShort((short) 7);
    this.mBuf.putInt(eventType);
    this.mBuf.putInt(x);
    this.mBuf.putInt(y);
    this.mBuf.putInt(dx);
    this.mBuf.putInt(dy);
    this.mBuf.putInt(accellevel);
    this.mBuf.putInt(fingerid);
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendTouchGestureEvent(short x, short y, byte dx, byte dy) {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 14 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 14);
    this.mBuf.putShort((short) 5);
    this.mBuf.putInt(-12);
    this.mBuf.putShort(x);
    this.mBuf.putShort(y);
    this.mBuf.put(dy);
    this.mBuf.put((byte) (-dx));
    this.mBuf.putShort((short) 0);
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendRemoteControlKey(String keycode, int mode) {
    if (this.mTargetDtvName == null || !this.mSocket.isConnected() || keycode == null) {
      return;
    }
    String keyCode64 = Base64.encodeToString(keycode.getBytes(), 0);
    int keyLen = keyCode64.length();
    int targetLen = this.mTargetDtvName.length();
    int dataLen = keyLen + 5;
    int packetLen = dataLen + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) dataLen);
    this.mBuf.putShort((short) 0);
    this.mBuf.put((byte) mode);
    this.mBuf.putShort((short) keyLen);
    this.mBuf.put(keyCode64.getBytes());
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendKeyboardString(int encoding, String keycode) {
    if (this.mTargetDtvName == null || !this.mSocket.isConnected() || keycode == null) {
      return;
    }
    String strEncoding = "UNICODE";
    if (encoding == 1) {
      strEncoding = "UTF-8";
    } else if (encoding == 2) {
      strEncoding = "UTF-16";
    }
    try {
      String keyCode64 = Base64.encodeToString(keycode.getBytes(strEncoding), 0);
      if (keyCode64 != null) {
        int keyLen = keyCode64.length();
        int targetLen = this.mTargetDtvName.length();
        int dataLen = keyLen + 4;
        int packetLen = dataLen + targetLen + 5;
        this.mBuf.clear();
        this.mBuf.put((byte) 0);
        this.mBuf.putShort((short) targetLen);
        this.mBuf.put(this.mTargetDtvName.getBytes());
        this.mBuf.putShort((short) dataLen);
        this.mBuf.putShort((short) 1);
        this.mBuf.putShort((short) keyLen);
        this.mBuf.put(keyCode64.getBytes());
        this.mBuf.flip();
        try {
          this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
          this.mDOutStream.flush();
        } catch (IOException e) {
          DLog.w_api(TAG, "", e);
        }
        this.mBuf.clear();
      }
    } catch (UnsupportedEncodingException e1) {
      DLog.w_api(TAG, "", e1);
    }
  }

  public void setTouchGestureTouchMode(int mode) {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 6 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 6);
    this.mBuf.putShort((short) 11);
    this.mBuf.putInt(mode);
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendMouseCreate() {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 2 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 2);
    this.mBuf.putShort((short) 15);
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendMouseDestroy() {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 2 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 2);
    this.mBuf.putShort((short) 16);
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  public void sendMouseProcess(int eventType, int x, int y, int dx, int dy, int Button) {
    String str = this.mTargetDtvName;
    if (str == null) {
      return;
    }
    int targetLen = str.length();
    int packetLen = 26 + targetLen + 5;
    this.mBuf.clear();
    this.mBuf.put((byte) 0);
    this.mBuf.putShort((short) targetLen);
    this.mBuf.put(this.mTargetDtvName.getBytes());
    this.mBuf.putShort((short) 26);
    this.mBuf.putShort((short) 17);
    this.mBuf.putInt(eventType);
    this.mBuf.putInt(x);
    this.mBuf.putInt(y);
    this.mBuf.putInt(dx);
    this.mBuf.putInt(dy);
    this.mBuf.putInt(Button);
    this.mBuf.flip();
    try {
      this.mDOutStream.write(this.mBuf.array(), 0, packetLen);
      this.mDOutStream.flush();
    } catch (IOException e) {
      DLog.w_api(TAG, "", e);
    }
    this.mBuf.clear();
  }

  private void createHandler() {
    this.mHandler =
        new Handler() { // from class: com.samsung.android.allshare.TVMessageSender.1
          @Override // android.os.Handler
          public void handleMessage(Message msg) {
            switch (msg.what) {
              case 0:
                if (TVMessageSender.this.mProtocol == 1 && TVMessageSender.this.mSocket != null) {
                  try {
                    TVMessageSender.this.mSocket.close();
                  } catch (IOException e) {
                    DLog.w_api(TVMessageSender.TAG, "", e);
                  }
                  TVMessageSender.this.mSocket = null;
                  break;
                }
                break;
              case 1:
                if (TVMessageSender.this.mProtocol == 1) {
                  if (TVMessageSender.this.mSocket != null
                      && TVMessageSender.this.mSocket.isConnected()) {
                    TVMessageSender.this.sendKeyboardString(msg.arg1, (String) msg.obj);
                    break;
                  } else {
                    DLog.w_api(
                        TVMessageSender.TAG,
                        "CONTROLLER_EVENT_KEYBOARD_INPUT : socket is not connected");
                    break;
                  }
                }
                break;
              case 2:
                EventMouse mEventMouse = (EventMouse) msg.obj;
                if (TVMessageSender.this.mProtocol == 1) {
                  if (TVMessageSender.this.mSocket != null
                      && TVMessageSender.this.mSocket.isConnected()) {
                    TVMessageSender.this.sendMouseProcess(
                        mEventMouse.mType,
                        mEventMouse.f2946mX,
                        mEventMouse.f2947mY,
                        mEventMouse.mDX,
                        mEventMouse.mDY,
                        mEventMouse.mButton);
                    break;
                  } else {
                    DLog.w_api(
                        TVMessageSender.TAG, "CONTROLLER_EVENT_MOUSE : socket is not connected");
                    break;
                  }
                }
                break;
              case 5:
                EventTouch mEvent = (EventTouch) msg.obj;
                if (TVMessageSender.this.mProtocol == 1) {
                  if (TVMessageSender.this.mSocket != null
                      && TVMessageSender.this.mSocket.isConnected()) {
                    TVMessageSender.this.sendTouchGuestureEvent2012(
                        mEvent.mType,
                        10,
                        0,
                        mEvent.f2948mX,
                        mEvent.f2949mY,
                        mEvent.mDX,
                        mEvent.mDY);
                    break;
                  } else {
                    DLog.w_api(
                        TVMessageSender.TAG, "CONTROLLER_EVENT_GESTURE : socket is not connected");
                    break;
                  }
                }
                break;
              case 6:
                EventTouch mEvent2 = (EventTouch) msg.obj;
                if (TVMessageSender.this.mProtocol == 1) {
                  if (TVMessageSender.this.mSocket != null
                      && TVMessageSender.this.mSocket.isConnected()) {
                    TVMessageSender.this.sendTouchGestureSemanticEvent(
                        mEvent2.mType,
                        mEvent2.mDistance,
                        mEvent2.mDegree,
                        mEvent2.f2948mX,
                        mEvent2.f2949mY);
                    break;
                  } else {
                    DLog.w_api(
                        TVMessageSender.TAG, "CONTROLLER_EVENT_SEMANTIC : socket is not connected");
                    break;
                  }
                }
                break;
              case 8:
                if (TVMessageSender.this.mSocket != null
                    && TVMessageSender.this.mSocket.isConnected()) {
                  DLog.i_api(TVMessageSender.TAG, "sendDTVKeyboardEnd");
                  TVMessageSender.this.sendKeyboardEnd();
                  break;
                }
                break;
              case 9:
                TVMessageSender.this.setTouchGestureTouchMode(msg.arg1);
                break;
              case 14:
                if (TVMessageSender.this.mProtocol == 1) {
                  if (TVMessageSender.this.mSocket != null
                      && TVMessageSender.this.mSocket.isConnected()) {
                    if (msg.arg1 == 0) {
                      TVMessageSender.this.sendRemoteControlKey((String) msg.obj, msg.arg1);
                      break;
                    }
                  } else {
                    DLog.w_api(
                        TVMessageSender.TAG,
                        "CONTROLLER_EVENT_REMOTECONTROL_KEY : socket is not connected");
                    break;
                  }
                }
                break;
              case 15:
                if (TVMessageSender.this.mSocket.isConnected()) {
                  TVMessageSender.this.sendMouseCreate();
                  break;
                } else {
                  DLog.w_api(
                      TVMessageSender.TAG,
                      "CONTROLLER_EVENT_MOUSE_CREATE : socket is not connected");
                  break;
                }
              case 16:
                if (TVMessageSender.this.mSocket != null
                    && TVMessageSender.this.mSocket.isConnected()) {
                  TVMessageSender.this.sendMouseDestroy();
                  break;
                } else {
                  DLog.w_api(
                      TVMessageSender.TAG,
                      "CONTROLLER_EVENT_MOUSE_DESTROY : socket is not connected");
                  break;
                }
                break;
              case 52:
                if (TVMessageSender.this.mSocket != null
                    && TVMessageSender.this.mSocket.isConnected()) {
                  TVMessageSender.this.sendAuthentication();
                  break;
                } else {
                  DLog.w_api(
                      TVMessageSender.TAG,
                      "CONTROLLER_EVENT_DEVICE_AUTHENTICATION : socket is not connected");
                  TVMessageSender.this.mControlAPI.sendSocketIsNotConnectedEvent();
                  break;
                }
                break;
            }
          }
        };
  }
}
