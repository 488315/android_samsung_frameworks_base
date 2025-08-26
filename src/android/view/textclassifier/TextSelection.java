package android.view.textclassifier;

import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.util.ArrayMap;
import android.view.textclassifier.TextClassifier;
import com.android.internal.util.Preconditions;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TextSelection implements Parcelable {
    public static final Parcelable.Creator<TextSelection> CREATOR = new Parcelable.Creator<TextSelection>() { // from class: android.view.textclassifier.TextSelection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextSelection createFromParcel(Parcel parcel) {
            return new TextSelection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextSelection[] newArray(int i) {
            return new TextSelection[i];
        }
    };
    private final int mEndIndex;
    private final EntityConfidence mEntityConfidence;
    private final Bundle mExtras;
    private final String mId;
    private final int mStartIndex;
    private final TextClassification mTextClassification;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextSelection(int i, int i2, Map<String, Float> map, String str, TextClassification textClassification, Bundle bundle) {
        this.mStartIndex = i;
        this.mEndIndex = i2;
        this.mEntityConfidence = new EntityConfidence(map);
        this.mId = str;
        this.mTextClassification = textClassification;
        this.mExtras = bundle;
    }

    public int getSelectionStartIndex() {
        return this.mStartIndex;
    }

    public int getSelectionEndIndex() {
        return this.mEndIndex;
    }

    public int getEntityCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public String getEntity(int i) {
        return this.mEntityConfidence.getEntities().get(i);
    }

    public float getConfidenceScore(String str) {
        return this.mEntityConfidence.getConfidenceScore(str);
    }

    public String getId() {
        return this.mId;
    }

    public TextClassification getTextClassification() {
        return this.mTextClassification;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Builder toBuilder() {
        return new Builder(this.mStartIndex, this.mEndIndex).setId(this.mId).setEntityConfidence(this.mEntityConfidence).setTextClassification(this.mTextClassification).setExtras(this.mExtras);
    }

    public String toString() {
        return String.format(Locale.US, "TextSelection {id=%s, startIndex=%d, endIndex=%d, entities=%s}", this.mId, Integer.valueOf(this.mStartIndex), Integer.valueOf(this.mEndIndex), this.mEntityConfidence);
    }

    public static final class Builder {
        private final int mEndIndex;
        private final Map<String, Float> mEntityConfidence = new ArrayMap();
        private Bundle mExtras;
        private String mId;
        private final int mStartIndex;
        private TextClassification mTextClassification;

        public Builder(int i, int i2) {
            Preconditions.checkArgument(i >= 0);
            Preconditions.checkArgument(i2 > i);
            this.mStartIndex = i;
            this.mEndIndex = i2;
        }

        public Builder setEntityType(String str, float f) {
            Objects.requireNonNull(str);
            this.mEntityConfidence.put(str, Float.valueOf(f));
            return this;
        }

        Builder setEntityConfidence(EntityConfidence entityConfidence) {
            this.mEntityConfidence.clear();
            this.mEntityConfidence.putAll(entityConfidence.toMap());
            return this;
        }

        public Builder setId(String str) {
            this.mId = str;
            return this;
        }

        public Builder setTextClassification(TextClassification textClassification) {
            this.mTextClassification = textClassification;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public TextSelection build() {
            int i = this.mStartIndex;
            int i2 = this.mEndIndex;
            Map<String, Float> map = this.mEntityConfidence;
            String str = this.mId;
            TextClassification textClassification = this.mTextClassification;
            Bundle bundle = this.mExtras;
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            return new TextSelection(i, i2, map, str, textClassification, bundle);
        }
    }

    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.TextSelection.Request.1
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
        private final boolean mDarkLaunchAllowed;
        private final LocaleList mDefaultLocales;
        private final int mEndIndex;
        private final Bundle mExtras;
        private final boolean mIncludeTextClassification;
        private final int mStartIndex;
        private SystemTextClassifierMetadata mSystemTcMetadata;
        private final CharSequence mText;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Request(CharSequence charSequence, int i, int i2, LocaleList localeList, boolean z, boolean z2, Bundle bundle) {
            this.mText = charSequence;
            this.mStartIndex = i;
            this.mEndIndex = i2;
            this.mDefaultLocales = localeList;
            this.mDarkLaunchAllowed = z;
            this.mIncludeTextClassification = z2;
            this.mExtras = bundle;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public int getStartIndex() {
            return this.mStartIndex;
        }

        public int getEndIndex() {
            return this.mEndIndex;
        }

        public boolean isDarkLaunchAllowed() {
            return this.mDarkLaunchAllowed;
        }

        public LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public String getCallingPackageName() {
            SystemTextClassifierMetadata systemTextClassifierMetadata = this.mSystemTcMetadata;
            if (systemTextClassifierMetadata != null) {
                return systemTextClassifierMetadata.getCallingPackageName();
            }
            return null;
        }

        public void setSystemTextClassifierMetadata(SystemTextClassifierMetadata systemTextClassifierMetadata) {
            this.mSystemTcMetadata = systemTextClassifierMetadata;
        }

        public SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
            return this.mSystemTcMetadata;
        }

        public boolean shouldIncludeTextClassification() {
            return this.mIncludeTextClassification;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private boolean mDarkLaunchAllowed;
            private LocaleList mDefaultLocales;
            private final int mEndIndex;
            private Bundle mExtras;
            private boolean mIncludeTextClassification;
            private final int mStartIndex;
            private final CharSequence mText;

            public Builder(CharSequence charSequence, int i, int i2) {
                TextClassifier.Utils.checkArgument(charSequence, i, i2);
                this.mText = charSequence;
                this.mStartIndex = i;
                this.mEndIndex = i2;
            }

            public Builder setDefaultLocales(LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public Builder setDarkLaunchAllowed(boolean z) {
                this.mDarkLaunchAllowed = z;
                return this;
            }

            public Builder setIncludeTextClassification(boolean z) {
                this.mIncludeTextClassification = z;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Request build() {
                SpannedString spannedString = new SpannedString(this.mText);
                int i = this.mStartIndex;
                int i2 = this.mEndIndex;
                LocaleList localeList = this.mDefaultLocales;
                boolean z = this.mDarkLaunchAllowed;
                boolean z2 = this.mIncludeTextClassification;
                Bundle bundle = this.mExtras;
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                return new Request(spannedString, i, i2, localeList, z, z2, bundle);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeCharSequence(this.mText);
            parcel.writeInt(this.mStartIndex);
            parcel.writeInt(this.mEndIndex);
            parcel.writeParcelable(this.mDefaultLocales, i);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
            parcel.writeBoolean(this.mIncludeTextClassification);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel parcel) {
            CharSequence charSequence = parcel.readCharSequence();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            LocaleList localeList = (LocaleList) parcel.readParcelable(null, LocaleList.class);
            Bundle bundle = parcel.readBundle();
            SystemTextClassifierMetadata systemTextClassifierMetadata = (SystemTextClassifierMetadata) parcel.readParcelable(null, SystemTextClassifierMetadata.class);
            Request request = new Request(charSequence, i, i2, localeList, false, parcel.readBoolean(), bundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStartIndex);
        parcel.writeInt(this.mEndIndex);
        this.mEntityConfidence.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeBundle(this.mExtras);
        parcel.writeParcelable(this.mTextClassification, i);
    }

    private TextSelection(Parcel parcel) {
        this.mStartIndex = parcel.readInt();
        this.mEndIndex = parcel.readInt();
        this.mEntityConfidence = EntityConfidence.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mExtras = parcel.readBundle();
        this.mTextClassification = (TextClassification) parcel.readParcelable(TextClassification.class.getClassLoader(), TextClassification.class);
    }
}
