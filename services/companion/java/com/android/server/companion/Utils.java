package com.android.server.companion;

import android.os.Parcel;
import android.os.ResultReceiver;

/* loaded from: classes.dex */
public abstract class Utils {
    public static ResultReceiver prepareForIpc(ResultReceiver resultReceiver) {
        Parcel obtain = Parcel.obtain();
        resultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        ResultReceiver resultReceiver2 = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return resultReceiver2;
    }
}
