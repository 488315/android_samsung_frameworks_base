package com.samsung.android.knox.custom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.sec.ims.settings.ImsSettings;
import java.io.ByteArrayOutputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PowerItem implements Parcelable {
    public static final int ACTION_SEND_BROADCAST = 1;
    public static final int ACTION_SEND_STICKY_BROADCAST = 2;
    public static final int ACTION_START_ACTIVITY = 4;
    public static final Parcelable.Creator<PowerItem> CREATOR = new Parcelable.Creator<PowerItem>() { // from class: com.samsung.android.knox.custom.PowerItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final PowerItem[] newArray(int i) {
            return new PowerItem[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final PowerItem createFromParcel(Parcel parcel) {
            return new PowerItem(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final PowerItem[] newArray(int i) {
            return new PowerItem[i];
        }
    };
    public final String TAG;
    public BitmapDrawable mIcon;
    public final String mIcon_KEY;
    public int mId;
    public final String mId_KEY;
    public Intent mIntent;
    public int mIntentAction;
    public final String mIntentAction_KEY;
    public final String mIntent_KEY;
    public String mText;
    public final String mText_KEY;

    public /* synthetic */ PowerItem(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final BitmapDrawable getIcon() {
        return this.mIcon;
    }

    public final int getId() {
        return this.mId;
    }

    public final Intent getIntent() {
        return this.mIntent;
    }

    public final int getIntentAction() {
        return this.mIntentAction;
    }

    public final String getText() {
        return this.mText;
    }

    public final String toString() {
        return "descr:0 id:" + this.mId + " icon:" + this.mIcon.hashCode() + " intent:" + this.mIntent.toString() + " intentAction:" + this.mIntentAction + " text:" + this.mText;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", this.mIntent);
        parcel.writeBundle(bundle);
        parcel.writeInt(this.mIntentAction);
        parcel.writeInt(this.mId);
        parcel.writeString(this.mText);
        Bitmap bitmap = this.mIcon.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        parcel.writeInt(byteArray.length);
        parcel.writeByteArray(byteArray);
    }

    public PowerItem(int i, BitmapDrawable bitmapDrawable, Intent intent, int i2, String str) {
        this.TAG = "PowerItem";
        this.mId_KEY = "ID";
        this.mIcon_KEY = "NAME";
        this.mIntent_KEY = "INTENT";
        this.mIntentAction_KEY = "INTENT_ACTION";
        this.mText_KEY = ImsSettings.TYPE_TEXT;
        this.mId = i;
        this.mIcon = bitmapDrawable;
        this.mIntent = intent;
        this.mIntentAction = i2;
        this.mText = str;
    }

    private PowerItem(Parcel parcel) {
        this.TAG = "PowerItem";
        this.mId_KEY = "ID";
        this.mIcon_KEY = "NAME";
        this.mIntent_KEY = "INTENT";
        this.mIntentAction_KEY = "INTENT_ACTION";
        this.mText_KEY = ImsSettings.TYPE_TEXT;
        this.mIntent = (Intent) parcel.readBundle().getParcelable("intent");
        this.mIntentAction = parcel.readInt();
        this.mId = parcel.readInt();
        this.mText = parcel.readString();
        int readInt = parcel.readInt();
        byte[] bArr = new byte[readInt];
        parcel.readByteArray(bArr);
        this.mIcon = new BitmapDrawable(BitmapFactory.decodeByteArray(bArr, 0, readInt));
    }
}
