package android.service.quicksettings;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes3.dex */
public final class Tile implements Parcelable {
    public static final Parcelable.Creator<Tile> CREATOR = new Parcelable.Creator<Tile>() { // from class: android.service.quicksettings.Tile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Tile createFromParcel(Parcel parcel) {
            return new Tile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Tile[] newArray(int i) {
            return new Tile[i];
        }
    };
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_INACTIVE = 1;
    public static final int STATE_UNAVAILABLE = 0;
    private static final String TAG = "Tile";
    private CharSequence mContentDescription;
    private CharSequence mDefaultLabel;
    private Icon mIcon;
    private CharSequence mLabel;
    private PendingIntent mPendingIntent;
    private IQSService mService;
    private int mState = 1;
    private CharSequence mStateDescription;
    private CharSequence mSubtitle;
    private IBinder mToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Tile(Parcel parcel) {
        readFromParcel(parcel);
    }

    public Tile() {
    }

    public void setService(IQSService iQSService, IBinder iBinder) {
        this.mService = iQSService;
        this.mToken = iBinder;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public void setIcon(Icon icon) {
        this.mIcon = icon;
    }

    public CharSequence getLabel() {
        CharSequence charSequence = this.mLabel;
        return charSequence != null ? charSequence : this.mDefaultLabel;
    }

    public CharSequence getCustomLabel() {
        return this.mLabel;
    }

    public void setDefaultLabel(CharSequence charSequence) {
        this.mDefaultLabel = charSequence;
    }

    public void setLabel(CharSequence charSequence) {
        this.mLabel = charSequence;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
    }

    public void setStateDescription(CharSequence charSequence) {
        this.mStateDescription = charSequence;
    }

    public void updateTile() {
        try {
            this.mService.updateQsTile(this, this.mToken);
        } catch (RemoteException unused) {
            Log.e(TAG, "Couldn't update tile");
        }
    }

    public PendingIntent getActivityLaunchForClick() {
        return this.mPendingIntent;
    }

    public void setActivityLaunchForClick(PendingIntent pendingIntent) {
        if (pendingIntent != null && !pendingIntent.isActivity()) {
            throw new IllegalArgumentException();
        }
        this.mPendingIntent = pendingIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mIcon != null) {
            parcel.writeByte((byte) 1);
            this.mIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mPendingIntent != null) {
            parcel.writeByte((byte) 1);
            this.mPendingIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mState);
        TextUtils.writeToParcel(this.mLabel, parcel, i);
        TextUtils.writeToParcel(this.mDefaultLabel, parcel, i);
        TextUtils.writeToParcel(this.mSubtitle, parcel, i);
        TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        TextUtils.writeToParcel(this.mStateDescription, parcel, i);
    }

    private void readFromParcel(Parcel parcel) {
        if (parcel.readByte() != 0) {
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        if (parcel.readByte() != 0) {
            this.mPendingIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        } else {
            this.mPendingIntent = null;
        }
        this.mState = parcel.readInt();
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDefaultLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mStateDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }
}
