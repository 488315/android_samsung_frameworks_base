package android.view.textclassifier;

import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinksParams;
import android.widget.TextView;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes4.dex */
public final class TextLinks implements Parcelable {
    public static final int APPLY_STRATEGY_IGNORE = 0;
    public static final int APPLY_STRATEGY_REPLACE = 1;
    public static final Parcelable.Creator<TextLinks> CREATOR = new Parcelable.Creator<TextLinks>() { // from class: android.view.textclassifier.TextLinks.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextLinks createFromParcel(Parcel parcel) {
            return new TextLinks(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextLinks[] newArray(int i) {
            return new TextLinks[i];
        }
    };
    public static final int STATUS_DIFFERENT_TEXT = 3;
    public static final int STATUS_LINKS_APPLIED = 0;
    public static final int STATUS_NO_LINKS_APPLIED = 2;
    public static final int STATUS_NO_LINKS_FOUND = 1;
    public static final int STATUS_UNSUPPORTED_CHARACTER = 4;
    private final Bundle mExtras;
    private final String mFullText;
    private final List<TextLink> mLinks;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplyStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextLinks(String str, ArrayList<TextLink> arrayList, Bundle bundle) {
        this.mFullText = str;
        this.mLinks = Collections.unmodifiableList(arrayList);
        this.mExtras = bundle;
    }

    public CharSequence getText() {
        return this.mFullText;
    }

    public Collection<TextLink> getLinks() {
        return this.mLinks;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int apply(Spannable spannable, int i, Function<TextLink, TextLinkSpan> function) {
        Objects.requireNonNull(spannable);
        return new TextLinksParams.Builder().setApplyStrategy(i).setSpanFactory(function).build().apply(spannable, this);
    }

    public String toString() {
        return String.format(Locale.US, "TextLinks{fullText=%s, links=%s}", this.mFullText, this.mLinks);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mFullText);
        parcel.writeTypedList(this.mLinks);
        parcel.writeBundle(this.mExtras);
    }

    private TextLinks(Parcel parcel) {
        this.mFullText = parcel.readString();
        this.mLinks = parcel.createTypedArrayList(TextLink.CREATOR);
        this.mExtras = parcel.readBundle();
    }

    public static final class TextLink implements Parcelable {
        public static final Parcelable.Creator<TextLink> CREATOR = new Parcelable.Creator<TextLink>() { // from class: android.view.textclassifier.TextLinks.TextLink.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TextLink createFromParcel(Parcel parcel) {
                return TextLink.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TextLink[] newArray(int i) {
                return new TextLink[i];
            }
        };
        private final int mEnd;
        private final EntityConfidence mEntityScores;
        private final Bundle mExtras;
        private final int mStart;
        private final URLSpan mUrlSpan;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private TextLink(int i, int i2, EntityConfidence entityConfidence, Bundle bundle, URLSpan uRLSpan) {
            Objects.requireNonNull(entityConfidence);
            Preconditions.checkArgument(!entityConfidence.getEntities().isEmpty());
            Preconditions.checkArgument(i <= i2);
            Objects.requireNonNull(bundle);
            this.mStart = i;
            this.mEnd = i2;
            this.mEntityScores = entityConfidence;
            this.mUrlSpan = uRLSpan;
            this.mExtras = bundle;
        }

        public int getStart() {
            return this.mStart;
        }

        public int getEnd() {
            return this.mEnd;
        }

        public int getEntityCount() {
            return this.mEntityScores.getEntities().size();
        }

        public String getEntity(int i) {
            return this.mEntityScores.getEntities().get(i);
        }

        public float getConfidenceScore(String str) {
            return this.mEntityScores.getConfidenceScore(str);
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String toString() {
            return String.format(Locale.US, "TextLink{start=%s, end=%s, entityScores=%s, urlSpan=%s}", Integer.valueOf(this.mStart), Integer.valueOf(this.mEnd), this.mEntityScores, this.mUrlSpan);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mEntityScores.writeToParcel(parcel, i);
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mEnd);
            parcel.writeBundle(this.mExtras);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TextLink readFromParcel(Parcel parcel) {
            return new TextLink(parcel.readInt(), parcel.readInt(), EntityConfidence.CREATOR.createFromParcel(parcel), parcel.readBundle(), null);
        }
    }

    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.TextLinks.Request.1
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
        private final LocaleList mDefaultLocales;
        private final TextClassifier.EntityConfig mEntityConfig;
        private final Bundle mExtras;
        private final boolean mLegacyFallback;
        private final ZonedDateTime mReferenceTime;
        private SystemTextClassifierMetadata mSystemTcMetadata;
        private final CharSequence mText;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Request(CharSequence charSequence, LocaleList localeList, TextClassifier.EntityConfig entityConfig, boolean z, ZonedDateTime zonedDateTime, Bundle bundle) {
            this.mText = charSequence;
            this.mDefaultLocales = localeList;
            this.mEntityConfig = entityConfig;
            this.mLegacyFallback = z;
            this.mReferenceTime = zonedDateTime;
            this.mExtras = bundle;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public TextClassifier.EntityConfig getEntityConfig() {
            return this.mEntityConfig;
        }

        public boolean isLegacyFallback() {
            return this.mLegacyFallback;
        }

        public ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
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
            return this.mExtras;
        }

        public static final class Builder {
            private LocaleList mDefaultLocales;
            private TextClassifier.EntityConfig mEntityConfig;
            private Bundle mExtras;
            private boolean mLegacyFallback = true;
            private ZonedDateTime mReferenceTime;
            private final CharSequence mText;

            public Builder(CharSequence charSequence) {
                this.mText = (CharSequence) Objects.requireNonNull(charSequence);
            }

            public Builder setDefaultLocales(LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public Builder setEntityConfig(TextClassifier.EntityConfig entityConfig) {
                this.mEntityConfig = entityConfig;
                return this;
            }

            public Builder setLegacyFallback(boolean z) {
                this.mLegacyFallback = z;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Builder setReferenceTime(ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }

            public Request build() {
                CharSequence charSequence = this.mText;
                LocaleList localeList = this.mDefaultLocales;
                TextClassifier.EntityConfig entityConfig = this.mEntityConfig;
                boolean z = this.mLegacyFallback;
                ZonedDateTime zonedDateTime = this.mReferenceTime;
                Bundle bundle = this.mExtras;
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                return new Request(charSequence, localeList, entityConfig, z, zonedDateTime, bundle);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mText.toString());
            parcel.writeParcelable(this.mDefaultLocales, i);
            parcel.writeParcelable(this.mEntityConfig, i);
            parcel.writeBundle(this.mExtras);
            ZonedDateTime zonedDateTime = this.mReferenceTime;
            parcel.writeString(zonedDateTime == null ? null : zonedDateTime.toString());
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel parcel) {
            String string = parcel.readString();
            LocaleList localeList = (LocaleList) parcel.readParcelable(null, LocaleList.class);
            TextClassifier.EntityConfig entityConfig = (TextClassifier.EntityConfig) parcel.readParcelable(null, TextClassifier.EntityConfig.class);
            Bundle bundle = parcel.readBundle();
            String string2 = parcel.readString();
            ZonedDateTime zonedDateTime = string2 == null ? null : ZonedDateTime.parse(string2);
            SystemTextClassifierMetadata systemTextClassifierMetadata = (SystemTextClassifierMetadata) parcel.readParcelable(null, SystemTextClassifierMetadata.class);
            Request request = new Request(string, localeList, entityConfig, true, zonedDateTime, bundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }
    }

    public static class TextLinkSpan extends ClickableSpan {
        public static final int INVOCATION_METHOD_KEYBOARD = 1;
        public static final int INVOCATION_METHOD_TOUCH = 0;
        public static final int INVOCATION_METHOD_UNSPECIFIED = -1;
        private final TextLink mTextLink;

        @Retention(RetentionPolicy.SOURCE)
        public @interface InvocationMethod {
        }

        public TextLinkSpan(TextLink textLink) {
            this.mTextLink = textLink;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            onClick(view, -1);
        }

        public final void onClick(View view, int i) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (TextClassificationManager.getSettings(textView.getContext()).isSmartLinkifyEnabled()) {
                    if (i == 0) {
                        textView.requestActionMode(this);
                        return;
                    } else {
                        textView.handleClick(this);
                        return;
                    }
                }
                if (this.mTextLink.mUrlSpan != null) {
                    this.mTextLink.mUrlSpan.onClick(textView);
                } else {
                    textView.handleClick(this);
                }
            }
        }

        public final TextLink getTextLink() {
            return this.mTextLink;
        }

        public final String getUrl() {
            if (this.mTextLink.mUrlSpan != null) {
                return this.mTextLink.mUrlSpan.getURL();
            }
            return null;
        }
    }

    public static final class Builder {
        private Bundle mExtras;
        private final String mFullText;
        private final ArrayList<TextLink> mLinks = new ArrayList<>();

        public Builder(String str) {
            this.mFullText = (String) Objects.requireNonNull(str);
        }

        public Builder addLink(int i, int i2, Map<String, Float> map) {
            return addLink(i, i2, map, Bundle.EMPTY, null);
        }

        public Builder addLink(int i, int i2, Map<String, Float> map, Bundle bundle) {
            return addLink(i, i2, map, bundle, null);
        }

        Builder addLink(int i, int i2, Map<String, Float> map, URLSpan uRLSpan) {
            return addLink(i, i2, map, Bundle.EMPTY, uRLSpan);
        }

        private Builder addLink(int i, int i2, Map<String, Float> map, Bundle bundle, URLSpan uRLSpan) {
            this.mLinks.add(new TextLink(i, i2, new EntityConfidence(map), bundle, uRLSpan));
            return this;
        }

        public Builder clearTextLinks() {
            this.mLinks.clear();
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public TextLinks build() {
            String str = this.mFullText;
            ArrayList<TextLink> arrayList = this.mLinks;
            Bundle bundle = this.mExtras;
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            return new TextLinks(str, arrayList, bundle);
        }
    }
}
