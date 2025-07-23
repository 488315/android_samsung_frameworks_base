package android.app.search;

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
public final class SearchAction implements Parcelable {
    public static final Parcelable.Creator<SearchAction> CREATOR = new Parcelable.Creator<SearchAction>() { // from class: android.app.search.SearchAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchAction createFromParcel(Parcel parcel) {
            return new SearchAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchAction[] newArray(int i) {
            return new SearchAction[i];
        }
    };
    private static final String TAG = "SearchAction";
    private final CharSequence mContentDescription;
    private final Bundle mExtras;
    private final Icon mIcon;
    private String mId;
    private final Intent mIntent;
    private final PendingIntent mPendingIntent;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SearchAction(Parcel parcel) {
        this.mId = parcel.readString();
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Icon) parcel.readTypedObject(Icon.CREATOR);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mPendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
        this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.mUserHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
        this.mExtras = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
    }

    private SearchAction(String str, CharSequence charSequence, Icon icon, CharSequence charSequence2, CharSequence charSequence3, PendingIntent pendingIntent, Intent intent, UserHandle userHandle, Bundle bundle) {
        this.mId = (String) Objects.requireNonNull(str);
        this.mTitle = (CharSequence) Objects.requireNonNull(charSequence);
        this.mIcon = icon;
        this.mSubtitle = charSequence2;
        this.mContentDescription = charSequence3;
        this.mPendingIntent = pendingIntent;
        this.mIntent = intent;
        this.mUserHandle = userHandle;
        this.mExtras = bundle == null ? new Bundle() : bundle;
        if (pendingIntent == null && intent == null) {
            throw new IllegalStateException("At least one type of intent should be available.");
        }
        if (pendingIntent != null && intent != null) {
            throw new IllegalStateException("Only one type of intent should be available.");
        }
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
        if (!(obj instanceof SearchAction)) {
            return false;
        }
        SearchAction searchAction = (SearchAction) obj;
        return this.mId.equals(searchAction.mId) && this.mTitle.equals(searchAction.mTitle);
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mTitle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        TextUtils.writeToParcel(this.mTitle, parcel, i);
        parcel.writeTypedObject(this.mIcon, i);
        TextUtils.writeToParcel(this.mSubtitle, parcel, i);
        TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeTypedObject(this.mExtras, i);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("id=");
        sb.append(this.mId);
        sb.append(" title=");
        sb.append((Object) this.mTitle);
        sb.append(" contentDescription=");
        sb.append((Object) this.mContentDescription);
        sb.append(" subtitle=");
        sb.append((Object) this.mSubtitle);
        sb.append(" icon=");
        sb.append(this.mIcon);
        sb.append(" pendingIntent=");
        PendingIntent pendingIntent = this.mPendingIntent;
        sb.append(pendingIntent == null ? "" : pendingIntent.getIntent());
        sb.append(" intent=");
        sb.append(this.mIntent);
        sb.append(" userHandle=");
        sb.append(this.mUserHandle);
        return sb.toString();
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

        public SearchAction build() {
            return new SearchAction(this.mId, this.mTitle, this.mIcon, this.mSubtitle, this.mContentDescription, this.mPendingIntent, this.mIntent, this.mUserHandle, this.mExtras);
        }
    }
}
