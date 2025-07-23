package android.text.style;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.provider.Browser;
import android.text.ParcelableSpan;
import android.util.Log;
import android.view.View;

/* loaded from: classes4.dex */
public class URLSpan extends ClickableSpan implements ParcelableSpan {
    private final String mURL;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSpanTypeIdInternal() {
        return 11;
    }

    public URLSpan(String str) {
        this.mURL = str;
    }

    public URLSpan(Parcel parcel) {
        this.mURL = parcel.readString();
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeString(this.mURL);
    }

    public String getURL() {
        return this.mURL;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        Uri parse = Uri.parse(getURL());
        Context context = view.getContext();
        Intent intent = new Intent("android.intent.action.VIEW", parse);
        if (!(context instanceof Activity)) {
            intent.setFlags(268435456);
        }
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.w("URLSpan", "Activity was not found for intent, " + intent.toString());
        }
    }

    @Override // android.text.style.ClickableSpan
    public String toString() {
        return "URLSpan{URL='" + getURL() + "'}";
    }
}
