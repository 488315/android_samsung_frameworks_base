package android.service.settings.suggestions;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class Suggestion implements Parcelable {
    public static final Parcelable.Creator<Suggestion> CREATOR = new Parcelable.Creator<Suggestion>() { // from class: android.service.settings.suggestions.Suggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Suggestion createFromParcel(Parcel parcel) {
            return new Suggestion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Suggestion[] newArray(int i) {
            return new Suggestion[i];
        }
    };
    public static final int FLAG_HAS_BUTTON = 1;
    public static final int FLAG_ICON_TINTABLE = 2;
    private final int mFlags;
    private final Icon mIcon;
    private final String mId;
    private final PendingIntent mPendingIntent;
    private final CharSequence mSummary;
    private final CharSequence mTitle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSummary() {
        return this.mSummary;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    private Suggestion(Builder builder) {
        this.mId = builder.mId;
        this.mTitle = builder.mTitle;
        this.mSummary = builder.mSummary;
        this.mIcon = builder.mIcon;
        this.mFlags = builder.mFlags;
        this.mPendingIntent = builder.mPendingIntent;
    }

    private Suggestion(Parcel parcel) {
        this.mId = parcel.readString();
        this.mTitle = parcel.readCharSequence();
        this.mSummary = parcel.readCharSequence();
        this.mIcon = (Icon) parcel.readParcelable(Icon.class.getClassLoader(), Icon.class);
        this.mFlags = parcel.readInt();
        this.mPendingIntent = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader(), PendingIntent.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeCharSequence(this.mSummary);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeInt(this.mFlags);
        parcel.writeParcelable(this.mPendingIntent, i);
    }

    public static class Builder {
        private int mFlags;
        private Icon mIcon;
        private final String mId;
        private PendingIntent mPendingIntent;
        private CharSequence mSummary;
        private CharSequence mTitle;

        public Builder(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Suggestion id cannot be empty");
            }
            this.mId = str;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public Builder setSummary(CharSequence charSequence) {
            this.mSummary = charSequence;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            this.mPendingIntent = pendingIntent;
            return this;
        }

        public Suggestion build() {
            return new Suggestion(this);
        }
    }
}
