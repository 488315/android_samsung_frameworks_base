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

/* loaded from: classes4.dex */
public final class StatusHints implements Parcelable {
    public static final Parcelable.Creator<StatusHints> CREATOR = new Parcelable.Creator<StatusHints>() { // from class: android.telecom.StatusHints.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusHints createFromParcel(Parcel parcel) {
            return new StatusHints(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusHints[] newArray(int i) {
            return new StatusHints[i];
        }
    };
    private static final String TAG = "StatusHints";
    private final Bundle mExtras;
    private Icon mIcon;
    private final CharSequence mLabel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    @Deprecated
    public int getIconResId() {
        return 0;
    }

    @SystemApi
    @Deprecated
    public StatusHints(ComponentName componentName, CharSequence charSequence, int i, Bundle bundle) {
        this(charSequence, i == 0 ? null : Icon.createWithResource(componentName.getPackageName(), i), bundle);
    }

    public StatusHints(CharSequence charSequence, Icon icon, Bundle bundle) {
        this.mLabel = charSequence;
        this.mIcon = validateAccountIconUserBoundary(icon, Binder.getCallingUserHandle());
        this.mExtras = bundle;
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
    public Drawable getIcon(Context context) {
        return this.mIcon.loadDrawable(context);
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public static Icon validateAccountIconUserBoundary(Icon icon, UserHandle userHandle) {
        if (icon == null) {
            return icon;
        }
        if (icon.getType() != 4 && icon.getType() != 6) {
            return icon;
        }
        int identifier = userHandle.getIdentifier();
        if (identifier != getUserIdFromAuthority(icon.getUri().getAuthority(), identifier)) {
            return null;
        }
        return icon;
    }

    public static int getUserIdFromAuthority(String str, int i) {
        int iLastIndexOf;
        if (str == null || (iLastIndexOf = str.lastIndexOf(64)) == -1) {
            return i;
        }
        try {
            return Integer.parseInt(str.substring(0, iLastIndexOf));
        } catch (NumberFormatException e) {
            android.util.Log.w(TAG, "Error parsing userId." + e);
            return -10000;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeParcelable(this.mIcon, 0);
        parcel.writeParcelable(this.mExtras, 0);
    }

    private StatusHints(Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mIcon = (Icon) parcel.readParcelable(getClass().getClassLoader(), Icon.class);
        this.mExtras = (Bundle) parcel.readParcelable(getClass().getClassLoader(), Bundle.class);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof StatusHints)) {
            StatusHints statusHints = (StatusHints) obj;
            if (Objects.equals(statusHints.getLabel(), getLabel()) && Objects.equals(statusHints.getIcon(), getIcon()) && Objects.equals(statusHints.getExtras(), getExtras())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.mLabel) + Objects.hashCode(this.mIcon) + Objects.hashCode(this.mExtras);
    }
}
