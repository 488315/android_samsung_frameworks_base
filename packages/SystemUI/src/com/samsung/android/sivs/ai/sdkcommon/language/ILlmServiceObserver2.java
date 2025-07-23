package com.samsung.android.sivs.ai.sdkcommon.language;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.sdk.scs.ai.language.ResultErrorException;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceRunnable;
import com.samsung.android.sdk.scs.base.ResultException;
import com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ILlmServiceObserver2 extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ILlmServiceObserver2 {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements ILlmServiceObserver2 {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
        }

        public static ILlmServiceObserver2 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ILlmServiceObserver2)) ? new Proxy(iBinder) : (ILlmServiceObserver2) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            TaskCompletionSource taskCompletionSource;
            TaskCompletionSource taskCompletionSource2;
            TaskCompletionSource taskCompletionSource3;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(Bundle.CREATOR);
                LlmServiceRunnable.AnonymousClass1 anonymousClass1 = (LlmServiceRunnable.AnonymousClass1) this;
                taskCompletionSource = ((TaskRunnable) LlmServiceRunnable.this).mSource;
                taskCompletionSource.setResult(LlmServiceRunnable.this.resultMapper.apply(createTypedArrayList));
                parcel2.writeNoException();
                return true;
            }
            if (i != 2) {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeNoException();
                return true;
            }
            Bundle bundle = (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null);
            LlmServiceRunnable.AnonymousClass1 anonymousClass12 = (LlmServiceRunnable.AnonymousClass1) this;
            if (bundle == null) {
                Log.e("LlmServiceRunnable", "onError= error is null");
                taskCompletionSource3 = ((TaskRunnable) LlmServiceRunnable.this).mSource;
                taskCompletionSource3.setException(new ResultException(5, "error is null"));
            } else {
                Log.e("LlmServiceRunnable", "onError= " + bundle.getInt("error_code") + bundle.getString("error_message"));
                taskCompletionSource2 = ((TaskRunnable) LlmServiceRunnable.this).mSource;
                taskCompletionSource2.setException(new ResultErrorException(500, bundle.getInt("error_code"), bundle.getString("error_message")));
            }
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
