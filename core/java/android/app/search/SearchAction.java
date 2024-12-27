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
    public static final Parcelable.Creator<SearchAction> CREATOR =
            new Parcelable.Creator<
                    SearchAction>() { // from class: android.app.search.SearchAction.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SearchAction createFromParcel(Parcel in) {
                    return new SearchAction(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SearchAction[] newArray(int size) {
                    return new SearchAction[size];
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

    SearchAction(Parcel in) {
        this.mId = in.readString();
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mIcon = (Icon) in.readTypedObject(Icon.CREATOR);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mPendingIntent = (PendingIntent) in.readTypedObject(PendingIntent.CREATOR);
        this.mIntent = (Intent) in.readTypedObject(Intent.CREATOR);
        this.mUserHandle = (UserHandle) in.readTypedObject(UserHandle.CREATOR);
        this.mExtras = (Bundle) in.readTypedObject(Bundle.CREATOR);
    }

    private SearchAction(
            String id,
            CharSequence title,
            Icon icon,
            CharSequence subtitle,
            CharSequence contentDescription,
            PendingIntent pendingIntent,
            Intent intent,
            UserHandle userHandle,
            Bundle extras) {
        this.mId = (String) Objects.requireNonNull(id);
        this.mTitle = (CharSequence) Objects.requireNonNull(title);
        this.mIcon = icon;
        this.mSubtitle = subtitle;
        this.mContentDescription = contentDescription;
        this.mPendingIntent = pendingIntent;
        this.mIntent = intent;
        this.mUserHandle = userHandle;
        this.mExtras = extras != null ? extras : new Bundle();
        if (this.mPendingIntent == null && this.mIntent == null) {
            throw new IllegalStateException("At least one type of intent should be available.");
        }
        if (this.mPendingIntent != null && this.mIntent != null) {
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchAction)) {
            return false;
        }
        SearchAction that = (SearchAction) o;
        return this.mId.equals(that.mId) && this.mTitle.equals(that.mTitle);
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mTitle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mId);
        TextUtils.writeToParcel(this.mTitle, out, flags);
        out.writeTypedObject(this.mIcon, flags);
        TextUtils.writeToParcel(this.mSubtitle, out, flags);
        TextUtils.writeToParcel(this.mContentDescription, out, flags);
        out.writeTypedObject(this.mPendingIntent, flags);
        out.writeTypedObject(this.mIntent, flags);
        out.writeTypedObject(this.mUserHandle, flags);
        out.writeTypedObject(this.mExtras, flags);
    }

    public String toString() {
        String str =
                "id="
                        + this.mId
                        + " title="
                        + ((Object) this.mTitle)
                        + " contentDescription="
                        + ((Object) this.mContentDescription)
                        + " subtitle="
                        + ((Object) this.mSubtitle)
                        + " icon="
                        + this.mIcon
                        + " pendingIntent="
                        + (this.mPendingIntent == null ? "" : this.mPendingIntent.getIntent())
                        + " intent="
                        + this.mIntent
                        + " userHandle="
                        + this.mUserHandle;
        return str;
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

        public Builder(String id, String title) {
            this.mId = (String) Objects.requireNonNull(id);
            this.mTitle = (CharSequence) Objects.requireNonNull(title);
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setSubtitle(CharSequence subtitle) {
            this.mSubtitle = subtitle;
            return this;
        }

        public Builder setContentDescription(CharSequence contentDescription) {
            this.mContentDescription = contentDescription;
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

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public SearchAction build() {
            return new SearchAction(
                    this.mId,
                    this.mTitle,
                    this.mIcon,
                    this.mSubtitle,
                    this.mContentDescription,
                    this.mPendingIntent,
                    this.mIntent,
                    this.mUserHandle,
                    this.mExtras);
        }
    }
}
