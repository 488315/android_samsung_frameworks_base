package android.view.textclassifier;

import android.app.Person;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.view.textclassifier.TextClassifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ConversationActions implements Parcelable {
    public static final Parcelable.Creator<ConversationActions> CREATOR = new Parcelable.Creator<ConversationActions>() { // from class: android.view.textclassifier.ConversationActions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationActions createFromParcel(Parcel parcel) {
            return new ConversationActions(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConversationActions[] newArray(int i) {
            return new ConversationActions[i];
        }
    };
    private final List<ConversationAction> mConversationActions;
    private final String mId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConversationActions(List<ConversationAction> list, String str) {
        this.mConversationActions = Collections.unmodifiableList((List) Objects.requireNonNull(list));
        this.mId = str;
    }

    private ConversationActions(Parcel parcel) {
        this.mConversationActions = Collections.unmodifiableList(parcel.createTypedArrayList(ConversationAction.CREATOR));
        this.mId = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mConversationActions);
        parcel.writeString(this.mId);
    }

    public List<ConversationAction> getConversationActions() {
        return this.mConversationActions;
    }

    public String getId() {
        return this.mId;
    }

    public static final class Message implements Parcelable {
        private final Person mAuthor;
        private final Bundle mExtras;
        private final ZonedDateTime mReferenceTime;
        private final CharSequence mText;
        public static final Person PERSON_USER_SELF = new Person.Builder().setKey("text-classifier-conversation-actions-user-self").build();
        public static final Person PERSON_USER_OTHERS = new Person.Builder().setKey("text-classifier-conversation-actions-user-others").build();
        public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() { // from class: android.view.textclassifier.ConversationActions.Message.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Message createFromParcel(Parcel parcel) {
                return new Message(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Message[] newArray(int i) {
                return new Message[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Message(Person person, ZonedDateTime zonedDateTime, CharSequence charSequence, Bundle bundle) {
            this.mAuthor = person;
            this.mReferenceTime = zonedDateTime;
            this.mText = charSequence;
            this.mExtras = (Bundle) Objects.requireNonNull(bundle);
        }

        private Message(Parcel parcel) {
            this.mAuthor = (Person) parcel.readParcelable(null, Person.class);
            this.mReferenceTime = parcel.readInt() != 0 ? ZonedDateTime.parse(parcel.readString(), DateTimeFormatter.ISO_ZONED_DATE_TIME) : null;
            this.mText = parcel.readCharSequence();
            this.mExtras = parcel.readBundle();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mAuthor, i);
            parcel.writeInt(this.mReferenceTime != null ? 1 : 0);
            ZonedDateTime zonedDateTime = this.mReferenceTime;
            if (zonedDateTime != null) {
                parcel.writeString(zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            }
            parcel.writeCharSequence(this.mText);
            parcel.writeBundle(this.mExtras);
        }

        public Person getAuthor() {
            return this.mAuthor;
        }

        public ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private Person mAuthor;
            private Bundle mExtras;
            private ZonedDateTime mReferenceTime;
            private CharSequence mText;

            public Builder(Person person) {
                this.mAuthor = (Person) Objects.requireNonNull(person);
            }

            public Builder setText(CharSequence charSequence) {
                this.mText = charSequence;
                return this;
            }

            public Builder setReferenceTime(ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Message build() {
                Person person = this.mAuthor;
                ZonedDateTime zonedDateTime = this.mReferenceTime;
                SpannedString spannedString = this.mText == null ? null : new SpannedString(this.mText);
                Bundle bundle = this.mExtras;
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                return new Message(person, zonedDateTime, spannedString, bundle);
            }
        }
    }

    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.ConversationActions.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Request createFromParcel(Parcel parcel) {
                return Request.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Request[] newArray(int i) {
                return new Request[i];
            }
        };
        public static final String HINT_FOR_IN_APP = "in_app";
        public static final String HINT_FOR_NOTIFICATION = "notification";
        private final List<Message> mConversation;
        private Bundle mExtras;
        private final List<String> mHints;
        private final int mMaxSuggestions;
        private SystemTextClassifierMetadata mSystemTcMetadata;
        private final TextClassifier.EntityConfig mTypeConfig;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Hint {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Request(List<Message> list, TextClassifier.EntityConfig entityConfig, int i, List<String> list2, Bundle bundle) {
            this.mConversation = (List) Objects.requireNonNull(list);
            this.mTypeConfig = (TextClassifier.EntityConfig) Objects.requireNonNull(entityConfig);
            this.mMaxSuggestions = i;
            this.mHints = list2;
            this.mExtras = bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            parcel.readParcelableList(arrayList, null, Message.class);
            TextClassifier.EntityConfig entityConfig = (TextClassifier.EntityConfig) parcel.readParcelable(null, TextClassifier.EntityConfig.class);
            int i = parcel.readInt();
            ArrayList arrayList2 = new ArrayList();
            parcel.readStringList(arrayList2);
            Bundle bundle = parcel.readBundle();
            SystemTextClassifierMetadata systemTextClassifierMetadata = (SystemTextClassifierMetadata) parcel.readParcelable(null, SystemTextClassifierMetadata.class);
            Request request = new Request(arrayList, entityConfig, i, arrayList2, bundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelableList(this.mConversation, i);
            parcel.writeParcelable(this.mTypeConfig, i);
            parcel.writeInt(this.mMaxSuggestions);
            parcel.writeStringList(this.mHints);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        public TextClassifier.EntityConfig getTypeConfig() {
            return this.mTypeConfig;
        }

        public List<Message> getConversation() {
            return this.mConversation;
        }

        public int getMaxSuggestions() {
            return this.mMaxSuggestions;
        }

        public List<String> getHints() {
            return this.mHints;
        }

        public String getCallingPackageName() {
            SystemTextClassifierMetadata systemTextClassifierMetadata = this.mSystemTcMetadata;
            if (systemTextClassifierMetadata != null) {
                return systemTextClassifierMetadata.getCallingPackageName();
            }
            return null;
        }

        void setSystemTextClassifierMetadata(SystemTextClassifierMetadata systemTextClassifierMetadata) {
            this.mSystemTcMetadata = systemTextClassifierMetadata;
        }

        public SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
            return this.mSystemTcMetadata;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private List<Message> mConversation;
            private Bundle mExtras;
            private List<String> mHints;
            private int mMaxSuggestions = -1;
            private TextClassifier.EntityConfig mTypeConfig;

            public Builder(List<Message> list) {
                this.mConversation = (List) Objects.requireNonNull(list);
            }

            public Builder setHints(List<String> list) {
                this.mHints = list;
                return this;
            }

            public Builder setTypeConfig(TextClassifier.EntityConfig entityConfig) {
                this.mTypeConfig = entityConfig;
                return this;
            }

            public Builder setMaxSuggestions(int i) {
                if (i < -1) {
                    throw new IllegalArgumentException("maxSuggestions has to be greater than or equal to -1.");
                }
                this.mMaxSuggestions = i;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Request build() {
                List listUnmodifiableList;
                List listUnmodifiableList2 = Collections.unmodifiableList(this.mConversation);
                TextClassifier.EntityConfig entityConfigBuild = this.mTypeConfig;
                if (entityConfigBuild == null) {
                    entityConfigBuild = new TextClassifier.EntityConfig.Builder().build();
                }
                int i = this.mMaxSuggestions;
                List<String> list = this.mHints;
                if (list == null) {
                    listUnmodifiableList = Collections.EMPTY_LIST;
                } else {
                    listUnmodifiableList = Collections.unmodifiableList(list);
                }
                Bundle bundle = this.mExtras;
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                return new Request(listUnmodifiableList2, entityConfigBuild, i, listUnmodifiableList, bundle);
            }
        }
    }
}
