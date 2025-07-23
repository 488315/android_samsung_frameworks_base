package android.service.notification;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class Adjustment implements Parcelable {
    public static final Parcelable.Creator<Adjustment> CREATOR = new Parcelable.Creator<Adjustment>() { // from class: android.service.notification.Adjustment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Adjustment createFromParcel(Parcel parcel) {
            return new Adjustment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Adjustment[] newArray(int i) {
            return new Adjustment[i];
        }
    };
    public static final String KEY_CONTEXTUAL_ACTIONS = "key_contextual_actions";
    public static final String KEY_GROUP_KEY = "key_group_key";
    public static final String KEY_IMPORTANCE = "key_importance";
    public static final String KEY_IMPORTANCE_PROPOSAL = "key_importance_proposal";

    @SystemApi
    public static final String KEY_NOT_CONVERSATION = "key_not_conversation";

    @SystemApi
    public static final String KEY_PEOPLE = "key_people";
    public static final String KEY_RANKING_SCORE = "key_ranking_score";
    public static final String KEY_SENSITIVE_CONTENT = "key_sensitive_content";
    public static final String KEY_SNOOZE_CRITERIA = "key_snooze_criteria";
    public static final String KEY_SUMMARIZATION = "key_summarization";
    public static final String KEY_TEXT_REPLIES = "key_text_replies";
    public static final String KEY_TYPE = "key_type";
    public static final String KEY_USER_SENTIMENT = "key_user_sentiment";
    public static final int TYPE_CONTENT_RECOMMENDATION = 4;
    public static final int TYPE_NEWS = 3;
    public static final int TYPE_OTHER = 0;
    public static final int TYPE_PROMOTION = 1;
    public static final int TYPE_SOCIAL_MEDIA = 2;
    private final CharSequence mExplanation;
    private String mIssuer;
    private final String mKey;
    private final String mPackage;
    private final Bundle mSignals;
    private final int mUser;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Keys {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Types {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public Adjustment(String str, String str2, Bundle bundle, CharSequence charSequence, int i) {
        this.mPackage = str;
        this.mKey = str2;
        this.mSignals = bundle;
        this.mExplanation = charSequence;
        this.mUser = i;
    }

    public Adjustment(String str, String str2, Bundle bundle, CharSequence charSequence, UserHandle userHandle) {
        this.mPackage = str;
        this.mKey = str2;
        this.mSignals = bundle;
        this.mExplanation = charSequence;
        this.mUser = userHandle.getIdentifier();
    }

    @SystemApi
    protected Adjustment(Parcel parcel) {
        if (parcel.readInt() == 1) {
            this.mPackage = parcel.readString();
        } else {
            this.mPackage = null;
        }
        if (parcel.readInt() == 1) {
            this.mKey = parcel.readString();
        } else {
            this.mKey = null;
        }
        if (parcel.readInt() == 1) {
            this.mExplanation = parcel.readCharSequence();
        } else {
            this.mExplanation = null;
        }
        this.mSignals = parcel.readBundle();
        this.mUser = parcel.readInt();
        this.mIssuer = parcel.readString();
    }

    public String getPackage() {
        return this.mPackage;
    }

    public String getKey() {
        return this.mKey;
    }

    public CharSequence getExplanation() {
        return this.mExplanation;
    }

    public Bundle getSignals() {
        return this.mSignals;
    }

    @SystemApi
    public int getUser() {
        return this.mUser;
    }

    public UserHandle getUserHandle() {
        return UserHandle.of(this.mUser);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mPackage != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mPackage);
        } else {
            parcel.writeInt(0);
        }
        if (this.mKey != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mKey);
        } else {
            parcel.writeInt(0);
        }
        if (this.mExplanation != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mExplanation);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBundle(this.mSignals);
        parcel.writeInt(this.mUser);
        parcel.writeString(this.mIssuer);
    }

    public String toString() {
        return "Adjustment{mSignals=" + this.mSignals + '}';
    }

    public void setIssuer(String str) {
        this.mIssuer = str;
    }

    public String getIssuer() {
        return this.mIssuer;
    }
}
