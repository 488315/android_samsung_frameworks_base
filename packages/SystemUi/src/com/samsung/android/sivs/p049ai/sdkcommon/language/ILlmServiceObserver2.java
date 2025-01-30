package com.samsung.android.sivs.p049ai.sdkcommon.language;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.sdk.scs.base.ResultException;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.sdk.scs.p048ai.language.Result;
import com.samsung.android.sdk.scs.p048ai.language.ResultErrorException;
import com.samsung.android.sdk.scs.p048ai.language.service.SmartReplyRunnable2;
import com.samsung.android.sivs.p049ai.sdkcommon.asr.SpeechRecognitionConst;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ILlmServiceObserver2 extends IInterface {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ILlmServiceObserver2 {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements ILlmServiceObserver2 {
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

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
                return true;
            }
            if (i == 1) {
                ArrayList<Bundle> createTypedArrayList = parcel.createTypedArrayList(Bundle.CREATOR);
                SmartReplyRunnable2.BinderC47601 binderC47601 = (SmartReplyRunnable2.BinderC47601) this;
                ArrayList arrayList = new ArrayList();
                for (Bundle bundle : createTypedArrayList) {
                    arrayList.add(new Result(bundle.getString("content"), bundle.getString("safety")));
                }
                SmartReplyRunnable2.this.mSource.task.setResult(arrayList);
                parcel2.writeNoException();
            } else if (i == 2) {
                Bundle bundle2 = (Bundle) (parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null);
                SmartReplyRunnable2.BinderC47601 binderC476012 = (SmartReplyRunnable2.BinderC47601) this;
                if (bundle2 == null) {
                    Log.m267e("SmartReplyRunnable", "onError= error is null");
                    SmartReplyRunnable2.this.mSource.setException(new ResultException(5, "error is null"));
                } else {
                    Log.m267e("SmartReplyRunnable", "onError= " + bundle2.getInt("error_code") + bundle2.getString(SpeechRecognitionConst.Key.ERROR_MESSAGE));
                    SmartReplyRunnable2.this.mSource.setException(new ResultErrorException(500, bundle2.getInt("error_code"), bundle2.getString(SpeechRecognitionConst.Key.ERROR_MESSAGE)));
                }
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
