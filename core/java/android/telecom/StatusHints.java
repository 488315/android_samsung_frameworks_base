package android.telecom;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class StatusHints implements Parcelable {
    public static final Parcelable.Creator<StatusHints> CREATOR = new Parcelable.Creator<StatusHints>() { // from class: android.telecom.StatusHints.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusHints createFromParcel(Parcel in) {
            return new StatusHints(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusHints[] newArray(int size) {
            return new StatusHints[size];
        }
    };
    private final Bundle mExtras;
    private Icon mIcon;
    private final CharSequence mLabel;

    @SystemApi
    @Deprecated
    public StatusHints(ComponentName packageName, CharSequence label, int iconResId, Bundle extras) {
        this(label, iconResId == 0 ? null : Icon.createWithResource(packageName.getPackageName(), iconResId), extras);
    }

    public StatusHints(CharSequence label, Icon icon, Bundle extras) {
        this.mLabel = label;
        this.mIcon = validateAccountIconUserBoundary(icon, Binder.getCallingUserHandle());
        this.mExtras = extras;
    }

    public StatusHints(Icon icon) {
        this.mLabel = null;
        this.mExtras = null;
        this.mIcon = icon;
    }

    public void setIcon(Icon icon) {
        this.mIcon = icon;
    }

    @SystemApi
    @Deprecated
    public ComponentName getPackageName() {
        return new ComponentName("", "");
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    @SystemApi
    @Deprecated
    public int getIconResId() {
        return 0;
    }

    @SystemApi
    @Deprecated
    public Drawable getIcon(Context context) {
        return this.mIcon.loadDrawable(context);
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static Icon validateAccountIconUserBoundary(Icon icon, UserHandle callingUserHandle) {
        String encodedUser;
        if (icon != null && ((icon.getType() == 4 || icon.getType() == 6) && (encodedUser = icon.getUri().getEncodedUserInfo()) != null)) {
            int userId = Integer.parseInt(encodedUser);
            if (userId != callingUserHandle.getIdentifier()) {
                return null;
            }
        }
        return icon;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeCharSequence(this.mLabel);
        out.writeParcelable(this.mIcon, 0);
        out.writeParcelable(this.mExtras, 0);
    }

    private StatusHints(Parcel in) {
        this.mLabel = in.readCharSequence();
        this.mIcon = (Icon) in.readParcelable(getClass().getClassLoader(), Icon.class);
        this.mExtras = (Bundle) in.readParcelable(getClass().getClassLoader(), Bundle.class);
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof StatusHints)) {
            return false;
        }
        StatusHints otherHints = (StatusHints) other;
        return Objects.equals(otherHints.getLabel(), getLabel()) && Objects.equals(otherHints.getIcon(), getIcon()) && Objects.equals(otherHints.getExtras(), getExtras());
    }

    public int hashCode() {
        return Objects.hashCode(this.mLabel) + Objects.hashCode(this.mIcon) + Objects.hashCode(this.mExtras);
    }
}
