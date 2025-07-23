package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.IResultReceiver;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.os.ResultReceiver.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ResultReceiver[i];
        }
    };
    public final Handler mHandler;
    public IResultReceiver mReceiver;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MyResultReceiver extends IResultReceiver.Stub {
        public MyResultReceiver() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MyRunnable implements Runnable {
        public final int mResultCode;
        public final Bundle mResultData;

        public MyRunnable(int i, Bundle bundle) {
            this.mResultCode = i;
            this.mResultData = bundle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

    public ResultReceiver(Handler handler) {
        this.mHandler = handler;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            try {
                if (this.mReceiver == null) {
                    this.mReceiver = new MyResultReceiver();
                }
                parcel.writeStrongBinder(this.mReceiver.asBinder());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ResultReceiver(Parcel parcel) {
        IResultReceiver iResultReceiver = null;
        this.mHandler = null;
        IBinder readStrongBinder = parcel.readStrongBinder();
        int i = IResultReceiver.Stub.$r8$clinit;
        if (readStrongBinder != null) {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface(IResultReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResultReceiver)) {
                iResultReceiver = (IResultReceiver) queryLocalInterface;
            } else {
                iResultReceiver = new IResultReceiver.Stub.Proxy(readStrongBinder);
            }
        }
        this.mReceiver = iResultReceiver;
    }

    public void onReceiveResult(int i, Bundle bundle) {
    }
}
