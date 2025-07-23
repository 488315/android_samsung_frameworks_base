package com.samsung.android.lib.galaxyfinder.search.api.payload;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.util.Base64;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String encodeToString;
        StringBuilder sb = new StringBuilder("intent://");
        Intent intent = this.mIntent;
        if (intent != null) {
            Parcel obtain = Parcel.obtain();
            intent.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            byte[] marshall = obtain.marshall();
            obtain.recycle();
            encodeToString = Base64.encodeToString(marshall, 0);
        } else {
            Intent addFlags = new Intent(this.mIntentAction).setClassName(this.mIntentPackage, this.mIntentClass).setData(Uri.parse(this.mIntentDataUri)).addFlags(this.mIntentFlags);
            Parcel obtain2 = Parcel.obtain();
            addFlags.writeToParcel(obtain2, 0);
            obtain2.setDataPosition(0);
            byte[] marshall2 = obtain2.marshall();
            obtain2.recycle();
            encodeToString = Base64.encodeToString(marshall2, 0);
        }
        sb.append(encodeToString);
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
