package android.app;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes.dex */
public final class RemoteAction implements Parcelable {
    public static final Parcelable.Creator<RemoteAction> CREATOR = new Parcelable.Creator<RemoteAction>() { // from class: android.app.RemoteAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAction createFromParcel(Parcel parcel) {
            return new RemoteAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAction[] newArray(int i) {
            return new RemoteAction[i];
        }
    };
    private static final String TAG = "RemoteAction";
    private final PendingIntent mActionIntent;
    private final CharSequence mContentDescription;
    private boolean mEnabled;
    private final Icon mIcon;
    private boolean mShouldShowIcon;
    private final CharSequence mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RemoteAction(Parcel parcel) {
        this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mActionIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        this.mEnabled = parcel.readBoolean();
        this.mShouldShowIcon = parcel.readBoolean();
    }

    public RemoteAction(Icon icon, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        if (icon == null || charSequence == null || charSequence2 == null || pendingIntent == null) {
            throw new IllegalArgumentException("Expected icon, title, content description and action callback");
        }
        this.mIcon = icon;
        this.mTitle = charSequence;
        this.mContentDescription = charSequence2;
        this.mActionIntent = pendingIntent;
        this.mEnabled = true;
        this.mShouldShowIcon = true;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setShouldShowIcon(boolean z) {
        this.mShouldShowIcon = z;
    }

    public boolean shouldShowIcon() {
        return this.mShouldShowIcon;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public PendingIntent getActionIntent() {
        return this.mActionIntent;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public RemoteAction m572clone() {
        RemoteAction remoteAction = new RemoteAction(this.mIcon, this.mTitle, this.mContentDescription, this.mActionIntent);
        remoteAction.setEnabled(this.mEnabled);
        remoteAction.setShouldShowIcon(this.mShouldShowIcon);
        return remoteAction;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemoteAction)) {
            return false;
        }
        RemoteAction remoteAction = (RemoteAction) obj;
        return this.mEnabled == remoteAction.mEnabled && this.mShouldShowIcon == remoteAction.mShouldShowIcon && this.mIcon.equals(remoteAction.mIcon) && this.mTitle.equals(remoteAction.mTitle) && this.mContentDescription.equals(remoteAction.mContentDescription) && this.mActionIntent.equals(remoteAction.mActionIntent);
    }

    public int hashCode() {
        return Objects.hash(this.mIcon, this.mTitle, this.mContentDescription, this.mActionIntent, Boolean.valueOf(this.mEnabled), Boolean.valueOf(this.mShouldShowIcon));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mIcon.writeToParcel(parcel, 0);
        TextUtils.writeToParcel(this.mTitle, parcel, i);
        TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        this.mActionIntent.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeBoolean(this.mShouldShowIcon);
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("title=" + ((Object) this.mTitle));
        printWriter.print(" enabled=" + this.mEnabled);
        printWriter.print(" contentDescription=" + ((Object) this.mContentDescription));
        printWriter.print(" icon=" + this.mIcon);
        printWriter.print(" action=" + this.mActionIntent.getIntent());
        printWriter.print(" shouldShowIcon=" + this.mShouldShowIcon);
        printWriter.println();
    }
}
