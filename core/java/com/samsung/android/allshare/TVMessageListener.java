package com.samsung.android.allshare;

import android.content.Context;
import android.util.Base64;
import com.samsung.android.allshare.IAppControlAPI;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
class TVMessageListener extends Thread {
    private static final String TAG = "TVMessageListener";
    Context mContext;
    ReadableByteChannel mInput;
    int mProtocol;
    TVMessageSender mSender;
    Socket mSocket;
    byte[] mRebuf = new byte[512];
    String mStrCamera = "live_camera.jpg";
    public boolean mRunThread = false;
    private IAppControlAPI.IControlEventListener mEventListener = null;
    ByteBuffer mBuf = ByteBuffer.allocate(2048);
    CharBuffer mCbuf = CharBuffer.allocate(512);

    public void setOnEventListener(IAppControlAPI.IControlEventListener listener) {
        this.mEventListener = listener;
    }

    public TVMessageListener(TVMessageSender sender) {
        this.mSocket = null;
        this.mProtocol = 0;
        this.mSender = null;
        this.mProtocol = 1;
        this.mSender = sender;
        if (sender != null) {
            this.mSocket = sender.mSocket;
        }
    }

    public void deliverMsgData(int what, int arg1, int arg2) {
        EventSync isc = new EventSync();
        isc.mWhat = what;
        isc.mArg1 = arg1;
        isc.mArg2 = arg2;
        IAppControlAPI.IControlEventListener iControlEventListener = this.mEventListener;
        if (iControlEventListener != null) {
            iControlEventListener.controlEvent(isc);
        } else {
            DLog.w_api(TAG, "EventListener is null !!!");
        }
    }

    public void deliverMsgData(int what, int arg1, int arg2, String str) {
        EventSync isc = new EventSync();
        isc.mWhat = what;
        isc.mArg1 = arg1;
        isc.mArg2 = arg2;
        isc.mStr = str;
        IAppControlAPI.IControlEventListener iControlEventListener = this.mEventListener;
        if (iControlEventListener != null) {
            iControlEventListener.controlEvent(isc);
        } else {
            DLog.w_api(TAG, "EventListener is null !!!");
        }
    }

    public void stopThread() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        ReadableByteChannel readableByteChannel;
        int nRecv;
        InputStream inStream = null;
        Socket socket = this.mSocket;
        if (socket != null && socket.isConnected()) {
            try {
                try {
                    inStream = this.mSocket.getInputStream();
                    this.mInput = Channels.newChannel(inStream);
                    try {
                        try {
                            try {
                                readableByteChannel = this.mInput;
                            } catch (Throwable th) {
                                try {
                                    ReadableByteChannel readableByteChannel2 = this.mInput;
                                    if (readableByteChannel2 != null) {
                                        readableByteChannel2.close();
                                    }
                                } catch (IOException e2) {
                                    DLog.w_api(TAG, "", e2);
                                }
                                throw th;
                            }
                        } catch (IOException e) {
                            DLog.w_api(TAG, "", e);
                            ReadableByteChannel readableByteChannel3 = this.mInput;
                            if (readableByteChannel3 != null) {
                                readableByteChannel3.close();
                            }
                        }
                        if (readableByteChannel == null) {
                            if (readableByteChannel != null) {
                                try {
                                    readableByteChannel.close();
                                    return;
                                } catch (IOException e22) {
                                    DLog.w_api(TAG, "", e22);
                                    return;
                                }
                            }
                            return;
                        }
                        while (Thread.currentThread() == this && (nRecv = this.mInput.read(this.mBuf)) != -1) {
                            DLog.i_api(TAG, "TVMessageListener data received " + nRecv);
                            this.mBuf.order(ByteOrder.LITTLE_ENDIAN);
                            this.mBuf.flip();
                            byte tvStatus = this.mBuf.get();
                            short targetLen = this.mBuf.getShort();
                            byte[] srebuf = new byte[512];
                            if (targetLen > 512) {
                                DLog.i_api(TAG, "targetLen = " + ((int) targetLen) + " is larger than MaxSize:512,discard is this pack.");
                                this.mBuf.clear();
                            } else {
                                this.mBuf.get(srebuf, 0, targetLen);
                                short dataLen = this.mBuf.getShort();
                                short protocolId = this.mBuf.getShort();
                                DLog.i_api(TAG, "tvStatus :" + ((int) tvStatus) + " targetLen :" + ((int) targetLen) + " dataLen :" + ((int) dataLen) + " protocolId :" + ((int) protocolId));
                                switch (protocolId) {
                                    case 0:
                                        short response = this.mBuf.getShort();
                                        DLog.i_api(TAG, "IAPP_REMOCON : response :" + ((int) response));
                                        deliverMsgData(0, response, 0);
                                        break;
                                    case 3:
                                        short stringLength = this.mBuf.getShort();
                                        DLog.i_api(TAG, "IAPP_KEYBOARD_SYNC : stringLength :" + ((int) stringLength));
                                        this.mBuf.get(srebuf, 0, stringLength);
                                        String keySync = new String(srebuf, 0, (int) stringLength);
                                        if (!keySync.contentEquals("AA==")) {
                                            byte[] bArr = new byte[512];
                                            byte[] sync = Base64.decode(keySync, 1);
                                            String keySync2 = new String(sync);
                                            deliverMsgData(3, stringLength, 0, keySync2);
                                            break;
                                        } else {
                                            deliverMsgData(3, stringLength, 0, "");
                                            break;
                                        }
                                    case 10:
                                        int remoteType = this.mBuf.getInt();
                                        DLog.i_api(TAG, "IAPP_REMOTE_INPUT_TYPE : remoteType :" + remoteType);
                                        deliverMsgData(10, remoteType, 0);
                                        break;
                                    case 100:
                                        DLog.i_api(TAG, "IAPP_AUTHENTICATION");
                                        short authresponse = this.mBuf.getShort();
                                        deliverMsgData(100, authresponse, 0);
                                        break;
                                    case 101:
                                        DLog.i_api(TAG, "IAPP_AUTHENTICATION_TIMEOUT");
                                        deliverMsgData(101, 0, 0);
                                        break;
                                    case 200:
                                        DLog.i_api(TAG, "IAPP_STATUS");
                                        short statusType = this.mBuf.getShort();
                                        short statusVal = this.mBuf.getShort();
                                        deliverMsgData(200, statusType, statusVal);
                                        break;
                                    case 300:
                                        DLog.i_api(TAG, "IAPP_EXIT");
                                        short exit = this.mBuf.getShort();
                                        deliverMsgData(300, exit, 0);
                                        break;
                                }
                                this.mBuf.clear();
                            }
                        }
                        ReadableByteChannel readableByteChannel4 = this.mInput;
                        if (readableByteChannel4 != null) {
                            readableByteChannel4.close();
                        }
                    } catch (IOException e23) {
                        DLog.w_api(TAG, "", e23);
                    }
                } catch (IOException e3) {
                    DLog.w_api(TAG, "", e3);
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException e4) {
                            DLog.w_api(TAG, "", e4);
                        }
                    }
                }
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e5) {
                        DLog.w_api(TAG, "", e5);
                    }
                }
            }
        }
    }
}
