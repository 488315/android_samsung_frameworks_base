package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.smartspace.SmartspaceUtils;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TapAction implements Parcelable {
    public static final Parcelable.Creator<TapAction> CREATOR = new Parcelable.Creator<TapAction>() { // from class: android.app.smartspace.uitemplatedata.TapAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapAction createFromParcel(Parcel parcel) {
            return new TapAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TapAction[] newArray(int i) {
            return new TapAction[i];
        }
    };
    private final Bundle mExtras;
    private final CharSequence mId;
    private final Intent mIntent;
    private final PendingIntent mPendingIntent;
    private final boolean mShouldShowOnLockscreen;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TapAction(Parcel parcel) {
        this.mId = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.mPendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
        this.mUserHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        this.mExtras = parcel.readBundle();
        this.mShouldShowOnLockscreen = parcel.readBoolean();
    }

    private TapAction(CharSequence charSequence, Intent intent, PendingIntent pendingIntent, UserHandle userHandle, Bundle bundle, boolean z) {
        this.mId = charSequence;
        this.mIntent = intent;
        this.mPendingIntent = pendingIntent;
        this.mUserHandle = userHandle;
        this.mExtras = bundle;
        this.mShouldShowOnLockscreen = z;
    }

    public CharSequence getId() {
        return this.mId;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean shouldShowOnLockscreen() {
        return this.mShouldShowOnLockscreen;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.mId, parcel, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeBundle(this.mExtras);
        parcel.writeBoolean(this.mShouldShowOnLockscreen);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TapAction) {
            return SmartspaceUtils.isEqual(this.mId, ((TapAction) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mId);
    }

    public String toString() {
        return "SmartspaceTapAction{mId=" + ((Object) this.mId) + "mIntent=" + this.mIntent + ", mPendingIntent=" + this.mPendingIntent + ", mUserHandle=" + this.mUserHandle + ", mExtras=" + this.mExtras + ", mShouldShowOnLockscreen=" + this.mShouldShowOnLockscreen + '}';
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private CharSequence mId;
        private Intent mIntent;
        private PendingIntent mPendingIntent;
        private boolean mShouldShowOnLockScreen = false;
        private UserHandle mUserHandle;

        public Builder(CharSequence charSequence) {
            this.mId = (CharSequence) Objects.requireNonNull(charSequence);
        }

        public Builder setIntent(Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public Builder setUserHandle(UserHandle userHandle) {
            this.mUserHandle = userHandle;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setShouldShowOnLockscreen(boolean z) {
            this.mShouldShowOnLockScreen = z;
            return this;
        }

        public TapAction build() {
            if (this.mIntent == null && this.mPendingIntent == null && this.mExtras == null) {
                throw new IllegalStateException("Please assign at least 1 valid tap field");
            }
            return new TapAction(this.mId, this.mIntent, this.mPendingIntent, this.mUserHandle, this.mExtras, this.mShouldShowOnLockScreen);
        }
    }
}
