package com.samsung.android.lib.galaxyfinder.search.api.payload;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.util.Base64;

/* loaded from: classes4.dex */
public class IntentResultItemPayload implements ResultItemPayload {
    public final Intent mIntent;
    public final String mIntentAction;
    public final String mIntentClass;
    public final String mIntentDataUri;
    public final int mIntentFlags;
    public final String mIntentPackage;

    public IntentResultItemPayload(Intent intent) {
        this.mIntent = intent;
        this.mIntentAction = null;
        this.mIntentPackage = null;
        this.mIntentClass = null;
        this.mIntentDataUri = null;
        this.mIntentFlags = 0;
    }

    public final String getStringFromPayload() {
        String strEncodeToString;
        StringBuilder sb = new StringBuilder("intent://");
        Intent intent = this.mIntent;
        if (intent != null) {
            Parcel parcelObtain = Parcel.obtain();
            intent.writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            byte[] bArrMarshall = parcelObtain.marshall();
            parcelObtain.recycle();
            strEncodeToString = Base64.encodeToString(bArrMarshall, 0);
        } else {
            Intent intentAddFlags = new Intent(this.mIntentAction).setClassName(this.mIntentPackage, this.mIntentClass).setData(Uri.parse(this.mIntentDataUri)).addFlags(this.mIntentFlags);
            Parcel parcelObtain2 = Parcel.obtain();
            intentAddFlags.writeToParcel(parcelObtain2, 0);
            parcelObtain2.setDataPosition(0);
            byte[] bArrMarshall2 = parcelObtain2.marshall();
            parcelObtain2.recycle();
            strEncodeToString = Base64.encodeToString(bArrMarshall2, 0);
        }
        sb.append(strEncodeToString);
        return sb.toString();
    }

    public IntentResultItemPayload(String str, String str2, String str3, String str4, int i) {
        this.mIntentAction = str;
        this.mIntentPackage = str2;
        this.mIntentClass = str3;
        this.mIntentDataUri = str4;
        this.mIntentFlags = i;
        this.mIntent = null;
    }
}
