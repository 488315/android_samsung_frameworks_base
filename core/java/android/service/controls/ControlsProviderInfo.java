package android.service.controls;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class ControlsProviderInfo implements Parcelable {
    public static final Parcelable.Creator<ControlsProviderInfo> CREATOR =
            new Parcelable.Creator<
                    ControlsProviderInfo>() { // from class:
                                              // android.service.controls.ControlsProviderInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ControlsProviderInfo createFromParcel(Parcel in) {
                    return new ControlsProviderInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ControlsProviderInfo[] newArray(int size) {
                    return new ControlsProviderInfo[size];
                }
            };
    private static final String TAG = "ControlsProviderInfo";
    private final PendingIntent mAppIntent;
    private final boolean mAutoRemove;
    private final Icon mIcon;

    public PendingIntent getAppIntent() {
        return this.mAppIntent;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public boolean getAutoRemove() {
        return this.mAutoRemove;
    }

    public ControlsProviderInfo(PendingIntent appIntent, Icon icon) {
        this.mAppIntent = appIntent;
        this.mIcon = icon;
        this.mAutoRemove = false;
    }

    public ControlsProviderInfo(PendingIntent appIntent, Icon icon, boolean autoRemove) {
        this.mAppIntent = appIntent;
        this.mIcon = icon;
        this.mAutoRemove = autoRemove;
    }

    ControlsProviderInfo(Parcel in) {
        this.mAppIntent = PendingIntent.CREATOR.createFromParcel(in);
        if (in.readByte() == 1) {
            this.mIcon = Icon.CREATOR.createFromParcel(in);
        } else {
            this.mIcon = null;
        }
        this.mAutoRemove = in.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mAppIntent.writeToParcel(dest, flags);
        if (this.mIcon != null) {
            dest.writeByte((byte) 1);
            this.mIcon.writeToParcel(dest, flags);
        } else {
            dest.writeByte((byte) 0);
        }
        dest.writeBoolean(this.mAutoRemove);
    }
}
