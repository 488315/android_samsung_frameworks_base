package android.hardware.usb;

import android.hardware.usb.IUsbOperationInternal;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final class UsbOperationInternal extends IUsbOperationInternal.Stub {
    private static final String TAG = "UsbPortStatus";
    public static final int USB_OPERATION_ERROR_INTERNAL = 1;
    public static final int USB_OPERATION_ERROR_NOT_SUPPORTED = 2;
    public static final int USB_OPERATION_ERROR_PORT_MISMATCH = 3;
    public static final int USB_OPERATION_SUCCESS = 0;
    private static final int USB_OPERATION_TIMEOUT_MSECS = 5000;
    private boolean mAsynchronous;
    private Consumer<Integer> mConsumer;
    private Executor mExecutor;
    private final String mId;
    final ReentrantLock mLock;
    private boolean mOperationComplete;
    private final int mOperationID;
    final Condition mOperationWait;
    private int mResult;
    private int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    @interface UsbOperationStatus {
    }

    UsbOperationInternal(int i, String str, Executor executor, Consumer<Integer> consumer) {
        this.mAsynchronous = false;
        this.mResult = 0;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mOperationWait = reentrantLock.newCondition();
        this.mOperationID = i;
        this.mId = str;
        this.mExecutor = executor;
        this.mConsumer = consumer;
        this.mAsynchronous = true;
    }

    UsbOperationInternal(int i, String str) {
        this.mAsynchronous = false;
        this.mResult = 0;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mOperationWait = reentrantLock.newCondition();
        this.mOperationID = i;
        this.mId = str;
    }

    @Override // android.hardware.usb.IUsbOperationInternal
    public void onOperationComplete(int i) {
        this.mLock.lock();
        try {
            this.mOperationComplete = true;
            this.mStatus = i;
            Log.i(TAG, "Port:" + this.mId + " opID:" + this.mOperationID + " status:" + this.mStatus);
            if (this.mAsynchronous) {
                int i2 = this.mStatus;
                if (i2 == 0) {
                    this.mResult = 0;
                } else if (i2 == 1) {
                    this.mResult = 1;
                } else if (i2 == 2) {
                    this.mResult = 2;
                } else if (i2 == 3) {
                    this.mResult = 3;
                } else {
                    this.mResult = 4;
                }
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.usb.UsbOperationInternal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsbOperationInternal.this.lambda$onOperationComplete$0();
                    }
                });
            } else {
                this.mOperationWait.signal();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOperationComplete$0() {
        this.mConsumer.accept(Integer.valueOf(this.mResult));
    }

    public void waitForOperationComplete() {
        this.mLock.lock();
        try {
            long currentTimeMillis = System.currentTimeMillis() + 5000;
            do {
                this.mOperationWait.await(currentTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                if (this.mOperationComplete) {
                    break;
                }
            } while (System.currentTimeMillis() < currentTimeMillis);
            if (!this.mOperationComplete) {
                Log.e(TAG, "Port:" + this.mId + " opID:" + this.mOperationID + " operationComplete not received in 5000msecs");
            }
        } catch (InterruptedException unused) {
            Log.e(TAG, "Port:" + this.mId + " opID:" + this.mOperationID + " operationComplete interrupted");
        } finally {
            this.mLock.unlock();
        }
    }

    public int getStatus() {
        if (this.mOperationComplete) {
            return this.mStatus;
        }
        return 1;
    }
}
