package com.samsung.android.lib.dexcontrol.uvdm.sender;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.utils.Util;
import com.samsung.android.lib.dexcontrol.uvdm.UvdmFileHelper;
import com.samsung.android.lib.dexcontrol.uvdm.response.IResponseListener;

import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UvdmSendExecutor {
    public boolean mIsEnabled;
    public IResponseListener mListener;
    public final int mPid;
    public ExecutorHandler mRequestProvider = new ExecutorHandler();
    public UvdmFileHelper mUvdmFileHelper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExecutorHandler {
        public final String REQUEST_THREAD_NAME =
                UUID.randomUUID().toString() + "REQUEST_THREAD_NAME";
        public boolean isSending = false;
        public HandlerThread requestThread;
        public AnonymousClass1 requestThreadHandler;

        public ExecutorHandler() {}
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor$ExecutorHandler$1] */
    public UvdmSendExecutor(int i) {
        this.mUvdmFileHelper = null;
        this.mIsEnabled = true;
        this.mUvdmFileHelper = new UvdmFileHelper();
        final ExecutorHandler executorHandler = this.mRequestProvider;
        executorHandler.getClass();
        HandlerThread handlerThread = new HandlerThread(executorHandler.REQUEST_THREAD_NAME);
        executorHandler.requestThread = handlerThread;
        handlerThread.start();
        executorHandler.requestThreadHandler =
                new Handler(
                        executorHandler.requestThread
                                .getLooper()) { // from class:
                                                // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor.ExecutorHandler.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message != null) {
                            ExecutorHandler executorHandler2 = ExecutorHandler.this;
                            SLog.i(
                                    UvdmSendExecutor.this.getTag(),
                                    Util.byteArrayToHex((byte[]) message.obj));
                            executorHandler2.isSending = true;
                            try {
                                UvdmSendExecutor.this.sendData(message.arg1, (byte[]) message.obj);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            executorHandler2.isSending = false;
                            message.obj = null;
                        }
                    }
                };
        this.mPid = i;
        this.mIsEnabled = true;
    }

    public final void destroy() {
        SLog.d(getTag(), "destroy");
        this.mIsEnabled = false;
        ExecutorHandler executorHandler = this.mRequestProvider;
        if (executorHandler != null) {
            ExecutorHandler.AnonymousClass1 anonymousClass1 = executorHandler.requestThreadHandler;
            if (anonymousClass1 != null) {
                anonymousClass1.removeCallbacksAndMessages(null);
            }
            HandlerThread handlerThread = executorHandler.requestThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                try {
                    executorHandler.requestThread.join();
                    executorHandler.requestThread = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            executorHandler.requestThreadHandler = null;
            if (!this.mRequestProvider.isSending) {
                this.mUvdmFileHelper = null;
            }
            this.mRequestProvider = null;
        }
    }

    public abstract String getTag();

    public final void send(int i, byte[] bArr) {
        ExecutorHandler.AnonymousClass1 anonymousClass1;
        if (!this.mIsEnabled) {
            SLog.e(getTag(), "Sender is not enabled.");
            return;
        }
        if (bArr.length <= 0) {
            SLog.e(getTag(), "message is wrong.");
            return;
        }
        ExecutorHandler executorHandler = this.mRequestProvider;
        if (executorHandler == null
                || (anonymousClass1 = executorHandler.requestThreadHandler) == null) {
            return;
        }
        Message obtainMessage = anonymousClass1.obtainMessage();
        obtainMessage.arg1 = i;
        obtainMessage.obj = bArr;
        executorHandler.requestThreadHandler.sendMessage(obtainMessage);
    }

    public abstract void sendData(int i, byte[] bArr);
}
