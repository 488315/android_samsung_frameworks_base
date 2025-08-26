package com.android.internal.os;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

@Deprecated
/* loaded from: classes5.dex */
public class HandlerCaller {
    final Callback mCallback;
    final Handler mH;
    final Looper mMainLooper;

    public interface Callback {
        void executeMessage(Message message);
    }

    class MyHandler extends Handler {
        MyHandler(Looper looper, boolean z) {
            super(looper, null, z);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            HandlerCaller.this.mCallback.executeMessage(message);
        }
    }

    public HandlerCaller(Context context, Looper looper, Callback callback, boolean z) {
        looper = looper == null ? context.getMainLooper() : looper;
        this.mMainLooper = looper;
        this.mH = new MyHandler(looper, z);
        this.mCallback = callback;
    }

    public Handler getHandler() {
        return this.mH;
    }

    public void executeOrSendMessage(Message message) {
        if (Looper.myLooper() == this.mMainLooper) {
            this.mCallback.executeMessage(message);
            message.recycle();
        } else {
            this.mH.sendMessage(message);
        }
    }

    public void sendMessageDelayed(Message message, long j) {
        this.mH.sendMessageDelayed(message, j);
    }

    public boolean hasMessages(int i) {
        return this.mH.hasMessages(i);
    }

    public void removeMessages(int i) {
        this.mH.removeMessages(i);
    }

    public void removeMessages(int i, Object obj) {
        this.mH.removeMessages(i, obj);
    }

    public void sendMessage(Message message) {
        this.mH.sendMessage(message);
    }

    public SomeArgs sendMessageAndWait(Message message) {
        if (Looper.myLooper() == this.mH.getLooper()) {
            throw new IllegalStateException("Can't wait on same thread as looper");
        }
        SomeArgs someArgs = (SomeArgs) message.obj;
        someArgs.mWaitState = 1;
        this.mH.sendMessage(message);
        synchronized (someArgs) {
            while (someArgs.mWaitState == 1) {
                try {
                    someArgs.wait();
                } catch (InterruptedException unused) {
                    return null;
                }
            }
        }
        someArgs.mWaitState = 0;
        return someArgs;
    }

    public Message obtainMessage(int i) {
        return this.mH.obtainMessage(i);
    }

    public Message obtainMessageBO(int i, boolean z, Object obj) {
        return this.mH.obtainMessage(i, z ? 1 : 0, 0, obj);
    }

    public Message obtainMessageBOO(int i, boolean z, Object obj, Object obj2) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        return this.mH.obtainMessage(i, z ? 1 : 0, 0, someArgsObtain);
    }

    public Message obtainMessageO(int i, Object obj) {
        return this.mH.obtainMessage(i, 0, 0, obj);
    }

    public Message obtainMessageI(int i, int i2) {
        return this.mH.obtainMessage(i, i2, 0);
    }

    public Message obtainMessageII(int i, int i2, int i3) {
        return this.mH.obtainMessage(i, i2, i3);
    }

    public Message obtainMessageIO(int i, int i2, Object obj) {
        return this.mH.obtainMessage(i, i2, 0, obj);
    }

    public Message obtainMessageIIO(int i, int i2, int i3, Object obj) {
        return this.mH.obtainMessage(i, i2, i3, obj);
    }

    public Message obtainMessageIIOO(int i, int i2, int i3, Object obj, Object obj2) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        return this.mH.obtainMessage(i, i2, i3, someArgsObtain);
    }

    public Message obtainMessageIOO(int i, int i2, Object obj, Object obj2) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        return this.mH.obtainMessage(i, i2, 0, someArgsObtain);
    }

    public Message obtainMessageIOOO(int i, int i2, Object obj, Object obj2, Object obj3) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        return this.mH.obtainMessage(i, i2, 0, someArgsObtain);
    }

    public Message obtainMessageIIOOO(int i, int i2, int i3, Object obj, Object obj2, Object obj3) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        return this.mH.obtainMessage(i, i2, i3, someArgsObtain);
    }

    public Message obtainMessageIIOOOO(int i, int i2, int i3, Object obj, Object obj2, Object obj3, Object obj4) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        someArgsObtain.arg4 = obj4;
        return this.mH.obtainMessage(i, i2, i3, someArgsObtain);
    }

    public Message obtainMessageOO(int i, Object obj, Object obj2) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageOOO(int i, Object obj, Object obj2, Object obj3) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageOOOO(int i, Object obj, Object obj2, Object obj3, Object obj4) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        someArgsObtain.arg4 = obj4;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageOOOOO(int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        someArgsObtain.arg4 = obj4;
        someArgsObtain.arg5 = obj5;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageOOOOII(int i, Object obj, Object obj2, Object obj3, Object obj4, int i2, int i3) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.arg2 = obj2;
        someArgsObtain.arg3 = obj3;
        someArgsObtain.arg4 = obj4;
        someArgsObtain.argi5 = i2;
        someArgsObtain.argi6 = i3;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageIIII(int i, int i2, int i3, int i4, int i5) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = i2;
        someArgsObtain.argi2 = i3;
        someArgsObtain.argi3 = i4;
        someArgsObtain.argi4 = i5;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageIIIIII(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = i2;
        someArgsObtain.argi2 = i3;
        someArgsObtain.argi3 = i4;
        someArgsObtain.argi4 = i5;
        someArgsObtain.argi5 = i6;
        someArgsObtain.argi6 = i7;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }

    public Message obtainMessageIIIIO(int i, int i2, int i3, int i4, int i5, Object obj) {
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = obj;
        someArgsObtain.argi1 = i2;
        someArgsObtain.argi2 = i3;
        someArgsObtain.argi3 = i4;
        someArgsObtain.argi4 = i5;
        return this.mH.obtainMessage(i, 0, 0, someArgsObtain);
    }
}
