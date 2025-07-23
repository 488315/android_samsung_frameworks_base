package android.service.controls;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class ControlsProviderInfo implements Parcelable {
    public static final Parcelable.Creator<ControlsProviderInfo> CREATOR = new Parcelable.Creator<ControlsProviderInfo>() { // from class: android.service.controls.ControlsProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlsProviderInfo createFromParcel(Parcel parcel) {
            return new ControlsProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlsProviderInfo[] newArray(int i) {
            return new ControlsProviderInfo[i];
        }
    };
    private static final String TAG = "ControlsProviderInfo";
    private final PendingIntent mAppIntent;
    private final boolean mAutoRemove;
    private final Icon mIcon;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PendingIntent getAppIntent() {
        return this.mAppIntent;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public boolean getAutoRemove() {
        return this.mAutoRemove;
    }

    public ControlsProviderInfo(PendingIntent pendingIntent, Icon icon) {
        this.mAppIntent = pendingIntent;
        this.mIcon = icon;
        this.mAutoRemove = false;
    }

    public ControlsProviderInfo(PendingIntent pendingIntent, Icon icon, boolean z) {
        this.mAppIntent = pendingIntent;
        this.mIcon = icon;
        this.mAutoRemove = z;
    }

    ControlsProviderInfo(Parcel parcel) {
        this.mAppIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        if (parcel.readByte() == 1) {
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mAutoRemove = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mAppIntent.writeToParcel(parcel, i);
        if (this.mIcon != null) {
            parcel.writeByte((byte) 1);
            this.mIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeBoolean(this.mAutoRemove);
    }
}
