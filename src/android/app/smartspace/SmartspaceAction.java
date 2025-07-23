package android.app.smartspace;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SmartspaceAction implements Parcelable {
    public static final Parcelable.Creator<SmartspaceAction> CREATOR = new Parcelable.Creator<SmartspaceAction>() { // from class: android.app.smartspace.SmartspaceAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceAction createFromParcel(Parcel parcel) {
            return new SmartspaceAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceAction[] newArray(int i) {
            return new SmartspaceAction[i];
        }
    };
    private static final String TAG = "SmartspaceAction";
    private final CharSequence mContentDescription;
    private Bundle mExtras;
    private final Icon mIcon;
    private final String mId;
    private final Intent mIntent;
    private final PendingIntent mPendingIntent;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SmartspaceAction(Parcel parcel) {
        this.mId = parcel.readString();
        this.mIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mPendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
        this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.mUserHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        this.mExtras = parcel.readBundle();
    }

    private SmartspaceAction(String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, PendingIntent pendingIntent, Intent intent, UserHandle userHandle, Bundle bundle) {
        this.mId = (String) Objects.requireNonNull(str);
        this.mIcon = icon;
        this.mTitle = (CharSequence) Objects.requireNonNull(charSequence);
        this.mSubtitle = charSequence2;
        this.mContentDescription = charSequence3;
        this.mPendingIntent = pendingIntent;
        this.mIntent = intent;
        this.mUserHandle = userHandle;
        this.mExtras = bundle;
    }

    public String getId() {
        return this.mId;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SmartspaceAction) {
            return this.mId.equals(((SmartspaceAction) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeTypedObject(this.mIcon, i);
        TextUtils.writeToParcel(this.mTitle, parcel, i);
        TextUtils.writeToParcel(this.mSubtitle, parcel, i);
        TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeBundle(this.mExtras);
    }

    public String toString() {
        return "SmartspaceAction{mId='" + this.mId + "', mIcon=" + this.mIcon + ", mTitle=" + ((Object) this.mTitle) + ", mSubtitle=" + ((Object) this.mSubtitle) + ", mContentDescription=" + ((Object) this.mContentDescription) + ", mPendingIntent=" + this.mPendingIntent + ", mIntent=" + this.mIntent + ", mUserHandle=" + this.mUserHandle + ", mExtras=" + this.mExtras + '}';
    }

    @SystemApi
    public static final class Builder {
        private CharSequence mContentDescription;
        private Bundle mExtras;
        private Icon mIcon;
        private String mId;
        private Intent mIntent;
        private PendingIntent mPendingIntent;
        private CharSequence mSubtitle;
        private CharSequence mTitle;
        private UserHandle mUserHandle;

        public Builder(String str, String str2) {
            this.mId = (String) Objects.requireNonNull(str);
            this.mTitle = (CharSequence) Objects.requireNonNull(str2);
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public Builder setContentDescription(CharSequence charSequence) {
            this.mContentDescription = charSequence;
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

        public Builder setIntent(Intent intent) {
            this.mIntent = intent;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public SmartspaceAction build() {
            Icon icon = this.mIcon;
            if (icon != null) {
                icon.convertToAshmem();
            }
            return new SmartspaceAction(this.mId, this.mIcon, this.mTitle, this.mSubtitle, this.mContentDescription, this.mPendingIntent, this.mIntent, this.mUserHandle, this.mExtras);
        }
    }
}
