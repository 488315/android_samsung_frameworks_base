package com.samsung.android.lib.dexcontrol.uvdm.sender;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.utils.Util;
import com.samsung.android.lib.dexcontrol.uvdm.UvdmFileHelper;
import com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener;
import java.util.UUID;

/* loaded from: classes2.dex */
public abstract class UvdmSendExecutor implements IUvdmSender {
    public boolean mIsEnabled;
    public IResponseListener mListener;
    public final int mPid;
    public ExecutorHandler mRequestProvider = new ExecutorHandler();
    public UvdmFileHelper mUvdmFileHelper;

    public abstract String getTag();

    public abstract void sendData(byte[] bArr, int i);

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender
    public void setInMsgMinLength(int i) {
    }

    public UvdmSendExecutor(int i) {
        this.mUvdmFileHelper = null;
        this.mIsEnabled = true;
        this.mUvdmFileHelper = new UvdmFileHelper();
        this.mRequestProvider.startThread();
        this.mPid = i;
        this.mIsEnabled = true;
    }

    public int getPid() {
        return this.mPid;
    }

    public boolean getSenderEnable() {
        return this.mIsEnabled;
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender
    public void destroy() {
        SLog.m126d(getTag(), "destroy");
        this.mIsEnabled = false;
        ExecutorHandler executorHandler = this.mRequestProvider;
        if (executorHandler != null) {
            executorHandler.stopThread();
            if (!this.mRequestProvider.isSending()) {
                this.mUvdmFileHelper = null;
            }
            this.mRequestProvider = null;
        }
    }

    public final void enqueueRequest(byte[] bArr, int i) {
        ExecutorHandler executorHandler = this.mRequestProvider;
        if (executorHandler != null) {
            executorHandler.enqueueRequest(bArr, i);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender
    public void setResponseListener(IResponseListener iResponseListener) {
        this.mListener = iResponseListener;
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender
    public void send(byte[] bArr) {
        send(bArr, 500);
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender
    public void send(byte[] bArr, int i) {
        if (getSenderEnable()) {
            if (bArr != null && bArr.length > 0) {
                enqueueRequest(bArr, i);
                return;
            } else {
                SLog.m127e(getTag(), "message is wrong.");
                return;
            }
        }
        SLog.m127e(getTag(), "Sender is not enabled.");
    }

    public class ExecutorHandler {
        public final String REQUEST_THREAD_NAME;
        public boolean isSending;
        public HandlerThread requestThread;
        public Handler requestThreadHandler;

        public ExecutorHandler() {
            this.REQUEST_THREAD_NAME = UUID.randomUUID().toString() + "REQUEST_THREAD_NAME";
            this.isSending = false;
        }

        public void startThread() {
            HandlerThread handlerThread = new HandlerThread(this.REQUEST_THREAD_NAME);
            this.requestThread = handlerThread;
            handlerThread.start();
            this.requestThreadHandler = new Handler(this.requestThread.getLooper()) { // from class: com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor.ExecutorHandler.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message != null) {
                        SLog.m128i(UvdmSendExecutor.this.getTag(), Util.byteArrayToHex((byte[]) message.obj));
                        ExecutorHandler.this.isSending = true;
                        try {
                            UvdmSendExecutor.this.sendData((byte[]) message.obj, message.arg1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ExecutorHandler.this.isSending = false;
                        message.obj = null;
                    }
                }
            };
        }

        public boolean isSending() {
            return this.isSending;
        }

        public void stopThread() {
            Handler handler = this.requestThreadHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            HandlerThread handlerThread = this.requestThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                try {
                    this.requestThread.join();
                    this.requestThread = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.requestThreadHandler = null;
        }

        public void enqueueRequest(byte[] bArr, int i) {
            Handler handler = this.requestThreadHandler;
            if (handler != null) {
                Message obtainMessage = handler.obtainMessage();
                obtainMessage.arg1 = i;
                obtainMessage.obj = bArr;
                this.requestThreadHandler.sendMessage(obtainMessage);
            }
        }
    }
}
