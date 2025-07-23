package android.view.textclassifier;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TextLanguage implements Parcelable {
    public static final Parcelable.Creator<TextLanguage> CREATOR = new Parcelable.Creator<TextLanguage>() { // from class: android.view.textclassifier.TextLanguage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextLanguage createFromParcel(Parcel parcel) {
            return TextLanguage.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextLanguage[] newArray(int i) {
            return new TextLanguage[i];
        }
    };
    static final TextLanguage EMPTY = new Builder().build();
    private final Bundle mBundle;
    private final EntityConfidence mEntityConfidence;
    private final String mId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextLanguage(String str, EntityConfidence entityConfidence, Bundle bundle) {
        this.mId = str;
        this.mEntityConfidence = entityConfidence;
        this.mBundle = bundle;
    }

    public String getId() {
        return this.mId;
    }

    public int getLocaleHypothesisCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public ULocale getLocale(int i) {
        return ULocale.forLanguageTag(this.mEntityConfidence.getEntities().get(i));
    }

    public float getConfidenceScore(ULocale uLocale) {
        return this.mEntityConfidence.getConfidenceScore(uLocale.toLanguageTag());
    }

    public Bundle getExtras() {
        return this.mBundle;
    }

    public String toString() {
        return String.format(Locale.US, "TextLanguage {id=%s, locales=%s, bundle=%s}", this.mId, this.mEntityConfidence, this.mBundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        this.mEntityConfidence.writeToParcel(parcel, i);
        parcel.writeBundle(this.mBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TextLanguage readFromParcel(Parcel parcel) {
        return new TextLanguage(parcel.readString(), EntityConfidence.CREATOR.createFromParcel(parcel), parcel.readBundle());
    }

    public static final class Builder {
        private Bundle mBundle;
        private final Map<String, Float> mEntityConfidenceMap = new ArrayMap();
        private String mId;

        public Builder putLocale(ULocale uLocale, float f) {
            Objects.requireNonNull(uLocale);
            this.mEntityConfidenceMap.put(uLocale.toLanguageTag(), Float.valueOf(f));
            return this;
        }

        public Builder setId(String str) {
            this.mId = str;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mBundle = (Bundle) Objects.requireNonNull(bundle);
            return this;
        }

        public TextLanguage build() {
            Bundle bundle = this.mBundle;
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            this.mBundle = bundle;
            return new TextLanguage(this.mId, new EntityConfidence(this.mEntityConfidenceMap), this.mBundle);
        }
    }

    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.TextLanguage.Request.1
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
        private final Bundle mExtra;
        private SystemTextClassifierMetadata mSystemTcMetadata;
        private final CharSequence mText;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Request(CharSequence charSequence, Bundle bundle) {
            this.mText = charSequence;
            this.mExtra = bundle;
        }

        public CharSequence getText() {
            return this.mText;
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

        public Bundle getExtras() {
            return this.mExtra;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeCharSequence(this.mText);
            parcel.writeBundle(this.mExtra);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel parcel) {
            CharSequence readCharSequence = parcel.readCharSequence();
            Bundle readBundle = parcel.readBundle();
            SystemTextClassifierMetadata systemTextClassifierMetadata = (SystemTextClassifierMetadata) parcel.readParcelable(null, SystemTextClassifierMetadata.class);
            Request request = new Request(readCharSequence, readBundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }

        public static final class Builder {
            private Bundle mBundle;
            private final CharSequence mText;

            public Builder(CharSequence charSequence) {
                this.mText = (CharSequence) Objects.requireNonNull(charSequence);
            }

            public Builder setExtras(Bundle bundle) {
                this.mBundle = (Bundle) Objects.requireNonNull(bundle);
                return this;
            }

            public Request build() {
                String charSequence = this.mText.toString();
                Bundle bundle = this.mBundle;
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                return new Request(charSequence, bundle);
            }
        }
    }
}
