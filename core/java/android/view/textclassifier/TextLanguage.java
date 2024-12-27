package android.view.textclassifier;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class TextLanguage implements Parcelable {
    public static final Parcelable.Creator<TextLanguage> CREATOR =
            new Parcelable.Creator<
                    TextLanguage>() { // from class: android.view.textclassifier.TextLanguage.1
                @Override // android.os.Parcelable.Creator
                public TextLanguage createFromParcel(Parcel in) {
                    return TextLanguage.readFromParcel(in);
                }

                @Override // android.os.Parcelable.Creator
                public TextLanguage[] newArray(int size) {
                    return new TextLanguage[size];
                }
            };
    static final TextLanguage EMPTY = new Builder().build();
    private final Bundle mBundle;
    private final EntityConfidence mEntityConfidence;
    private final String mId;

    private TextLanguage(String id, EntityConfidence entityConfidence, Bundle bundle) {
        this.mId = id;
        this.mEntityConfidence = entityConfidence;
        this.mBundle = bundle;
    }

    public String getId() {
        return this.mId;
    }

    public int getLocaleHypothesisCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public ULocale getLocale(int index) {
        return ULocale.forLanguageTag(this.mEntityConfidence.getEntities().get(index));
    }

    public float getConfidenceScore(ULocale locale) {
        return this.mEntityConfidence.getConfidenceScore(locale.toLanguageTag());
    }

    public Bundle getExtras() {
        return this.mBundle;
    }

    public String toString() {
        return String.format(
                Locale.US,
                "TextLanguage {id=%s, locales=%s, bundle=%s}",
                this.mId,
                this.mEntityConfidence,
                this.mBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        this.mEntityConfidence.writeToParcel(dest, flags);
        dest.writeBundle(this.mBundle);
    }

    public static TextLanguage readFromParcel(Parcel in) {
        return new TextLanguage(
                in.readString(), EntityConfidence.CREATOR.createFromParcel(in), in.readBundle());
    }

    public static final class Builder {
        private Bundle mBundle;
        private final Map<String, Float> mEntityConfidenceMap = new ArrayMap();
        private String mId;

        public Builder putLocale(ULocale locale, float confidenceScore) {
            Objects.requireNonNull(locale);
            this.mEntityConfidenceMap.put(locale.toLanguageTag(), Float.valueOf(confidenceScore));
            return this;
        }

        public Builder setId(String id) {
            this.mId = id;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mBundle = (Bundle) Objects.requireNonNull(bundle);
            return this;
        }

        public TextLanguage build() {
            this.mBundle = this.mBundle == null ? Bundle.EMPTY : this.mBundle;
            return new TextLanguage(
                    this.mId, new EntityConfidence(this.mEntityConfidenceMap), this.mBundle);
        }
    }

    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR =
                new Parcelable.Creator<
                        Request>() { // from class:
                                     // android.view.textclassifier.TextLanguage.Request.1
                    @Override // android.os.Parcelable.Creator
                    public Request createFromParcel(Parcel in) {
                        return Request.readFromParcel(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public Request[] newArray(int size) {
                        return new Request[size];
                    }
                };
        private final Bundle mExtra;
        private SystemTextClassifierMetadata mSystemTcMetadata;
        private final CharSequence mText;

        private Request(CharSequence text, Bundle bundle) {
            this.mText = text;
            this.mExtra = bundle;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public String getCallingPackageName() {
            if (this.mSystemTcMetadata != null) {
                return this.mSystemTcMetadata.getCallingPackageName();
            }
            return null;
        }

        public void setSystemTextClassifierMetadata(SystemTextClassifierMetadata systemTcMetadata) {
            this.mSystemTcMetadata = systemTcMetadata;
        }

        public SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
            return this.mSystemTcMetadata;
        }

        public Bundle getExtras() {
            return this.mExtra;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeCharSequence(this.mText);
            dest.writeBundle(this.mExtra);
            dest.writeParcelable(this.mSystemTcMetadata, flags);
        }

        public static Request readFromParcel(Parcel in) {
            CharSequence text = in.readCharSequence();
            Bundle extra = in.readBundle();
            SystemTextClassifierMetadata systemTcMetadata =
                    (SystemTextClassifierMetadata)
                            in.readParcelable(null, SystemTextClassifierMetadata.class);
            Request request = new Request(text, extra);
            request.setSystemTextClassifierMetadata(systemTcMetadata);
            return request;
        }

        public static final class Builder {
            private Bundle mBundle;
            private final CharSequence mText;

            public Builder(CharSequence text) {
                this.mText = (CharSequence) Objects.requireNonNull(text);
            }

            public Builder setExtras(Bundle bundle) {
                this.mBundle = (Bundle) Objects.requireNonNull(bundle);
                return this;
            }

            public Request build() {
                return new Request(
                        this.mText.toString(), this.mBundle == null ? Bundle.EMPTY : this.mBundle);
            }
        }
    }
}
