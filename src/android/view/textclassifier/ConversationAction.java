package android.view.textclassifier;

import android.app.RemoteAction;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ConversationAction implements Parcelable {
    public static final Parcelable.Creator<ConversationAction> CREATOR = new Parcelable.Creator<ConversationAction>() { // from class: android.view.textclassifier.ConversationAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationAction createFromParcel(Parcel parcel) {
            return new ConversationAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationAction[] newArray(int i) {
            return new ConversationAction[i];
        }
    };
    public static final String TYPE_ADD_CONTACT = "add_contact";
    public static final String TYPE_CALL_PHONE = "call_phone";
    public static final String TYPE_COPY = "copy";
    public static final String TYPE_CREATE_REMINDER = "create_reminder";
    public static final String TYPE_OPEN_URL = "open_url";
    public static final String TYPE_SEND_EMAIL = "send_email";
    public static final String TYPE_SEND_SMS = "send_sms";
    public static final String TYPE_SHARE_LOCATION = "share_location";
    public static final String TYPE_TEXT_REPLY = "text_reply";
    public static final String TYPE_TRACK_FLIGHT = "track_flight";
    public static final String TYPE_VIEW_CALENDAR = "view_calendar";
    public static final String TYPE_VIEW_MAP = "view_map";
    private final RemoteAction mAction;
    private final Bundle mExtras;
    private final float mScore;
    private final CharSequence mTextReply;
    private final String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ConversationAction(String str, RemoteAction remoteAction, CharSequence charSequence, float f, Bundle bundle) {
        this.mType = (String) Objects.requireNonNull(str);
        this.mAction = remoteAction;
        this.mTextReply = charSequence;
        this.mScore = f;
        this.mExtras = (Bundle) Objects.requireNonNull(bundle);
    }

    private ConversationAction(Parcel parcel) {
        this.mType = parcel.readString();
        this.mAction = (RemoteAction) parcel.readParcelable(null, RemoteAction.class);
        this.mTextReply = parcel.readCharSequence();
        this.mScore = parcel.readFloat();
        this.mExtras = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mType);
        parcel.writeParcelable(this.mAction, i);
        parcel.writeCharSequence(this.mTextReply);
        parcel.writeFloat(this.mScore);
        parcel.writeBundle(this.mExtras);
    }

    public String getType() {
        return this.mType;
    }

    public RemoteAction getAction() {
        return this.mAction;
    }

    public float getConfidenceScore() {
        return this.mScore;
    }

    public CharSequence getTextReply() {
        return this.mTextReply;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Builder toBuilder() {
        return new Builder(this.mType).setTextReply(this.mTextReply).setAction(this.mAction).setConfidenceScore(this.mScore).setExtras(this.mExtras);
    }

    public static final class Builder {
        private RemoteAction mAction;
        private Bundle mExtras;
        private float mScore;
        private CharSequence mTextReply;
        private String mType;

        public Builder(String str) {
            this.mType = (String) Objects.requireNonNull(str);
        }

        public Builder setAction(RemoteAction remoteAction) {
            this.mAction = remoteAction;
            return this;
        }

        public Builder setTextReply(CharSequence charSequence) {
            this.mTextReply = charSequence;
            return this;
        }

        public Builder setConfidenceScore(float f) {
            this.mScore = f;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public ConversationAction build() {
            String str = this.mType;
            RemoteAction remoteAction = this.mAction;
            CharSequence charSequence = this.mTextReply;
            float f = this.mScore;
            Bundle bundle = this.mExtras;
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            return new ConversationAction(str, remoteAction, charSequence, f, bundle);
        }
    }
}
