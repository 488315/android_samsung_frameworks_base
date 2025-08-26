package android.view.textclassifier;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.util.ArrayMap;
import android.view.View;
import android.view.textclassifier.TextClassifier;
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

/* loaded from: classes4.dex */
public final class TextClassification implements Parcelable {
    private static final String LOG_TAG = "TextClassification";
    private static final int MAX_LEGACY_ICON_SIZE = 192;
    private final List<RemoteAction> mActions;
    private final EntityConfidence mEntityConfidence;
    private final Bundle mExtras;
    private final String mId;
    private final Drawable mLegacyIcon;
    private final Intent mLegacyIntent;
    private final String mLegacyLabel;
    private final View.OnClickListener mLegacyOnClickListener;
    private final String mText;
    public static final TextClassification EMPTY = new Builder().build();
    public static final Parcelable.Creator<TextClassification> CREATOR = new Parcelable.Creator<TextClassification>() { // from class: android.view.textclassifier.TextClassification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassification createFromParcel(Parcel parcel) {
            return new TextClassification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassification[] newArray(int i) {
            return new TextClassification[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    private @interface IntentType {
        public static final int ACTIVITY = 0;
        public static final int SERVICE = 1;
        public static final int UNSUPPORTED = -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextClassification(String str, Drawable drawable, String str2, Intent intent, View.OnClickListener onClickListener, List<RemoteAction> list, EntityConfidence entityConfidence, String str3, Bundle bundle) {
        this.mText = str;
        this.mLegacyIcon = drawable;
        this.mLegacyLabel = str2;
        this.mLegacyIntent = intent;
        this.mLegacyOnClickListener = onClickListener;
        this.mActions = Collections.unmodifiableList(list);
        this.mEntityConfidence = (EntityConfidence) Objects.requireNonNull(entityConfidence);
        this.mId = str3;
        this.mExtras = bundle;
    }

    public String getText() {
        return this.mText;
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

    public List<RemoteAction> getActions() {
        return this.mActions;
    }

    @Deprecated
    public Drawable getIcon() {
        return this.mLegacyIcon;
    }

    @Deprecated
    public CharSequence getLabel() {
        return this.mLegacyLabel;
    }

    @Deprecated
    public Intent getIntent() {
        return this.mLegacyIntent;
    }

    public View.OnClickListener getOnClickListener() {
        return this.mLegacyOnClickListener;
    }

    public String getId() {
        return this.mId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Builder toBuilder() {
        return new Builder().setId(this.mId).setText(this.mText).addActions(this.mActions).setEntityConfidence(this.mEntityConfidence).setIcon(this.mLegacyIcon).setLabel(this.mLegacyLabel).setIntent(this.mLegacyIntent).setOnClickListener(this.mLegacyOnClickListener).setExtras(this.mExtras);
    }

    public String toString() {
        return String.format(Locale.US, "TextClassification {text=%s, entities=%s, actions=%s, id=%s, extras=%s}", this.mText, this.mEntityConfidence, this.mActions, this.mId, this.mExtras);
    }

    public static View.OnClickListener createIntentOnClickListener(final PendingIntent pendingIntent) {
        Objects.requireNonNull(pendingIntent);
        return new View.OnClickListener() { // from class: android.view.textclassifier.TextClassification$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TextClassification.lambda$createIntentOnClickListener$0(pendingIntent, view);
            }
        };
    }

    static /* synthetic */ void lambda$createIntentOnClickListener$0(PendingIntent pendingIntent, View view) {
        try {
            pendingIntent.send(ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.e(LOG_TAG, "Error sending PendingIntent", e);
        }
    }

    public static PendingIntent createPendingIntent(Context context, Intent intent, int i) {
        return PendingIntent.getActivity(context, i, intent, 201326592);
    }

    public static final class Builder {
        private Bundle mExtras;
        private String mId;
        private Drawable mLegacyIcon;
        private Intent mLegacyIntent;
        private String mLegacyLabel;
        private View.OnClickListener mLegacyOnClickListener;
        private String mText;
        private final List<RemoteAction> mActions = new ArrayList();
        private final Map<String, Float> mTypeScoreMap = new ArrayMap();

        public Builder setText(String str) {
            this.mText = str;
            return this;
        }

        public Builder setEntityType(String str, float f) {
            this.mTypeScoreMap.put(str, Float.valueOf(f));
            return this;
        }

        Builder setEntityConfidence(EntityConfidence entityConfidence) {
            this.mTypeScoreMap.clear();
            this.mTypeScoreMap.putAll(entityConfidence.toMap());
            return this;
        }

        public Builder clearEntityTypes() {
            this.mTypeScoreMap.clear();
            return this;
        }

        public Builder addAction(RemoteAction remoteAction) {
            Preconditions.checkArgument(remoteAction != null);
            this.mActions.add(remoteAction);
            return this;
        }

        public Builder addActions(Collection<RemoteAction> collection) {
            Objects.requireNonNull(collection);
            this.mActions.addAll(collection);
            return this;
        }

        public Builder clearActions() {
            this.mActions.clear();
            return this;
        }

        @Deprecated
        public Builder setIcon(Drawable drawable) {
            this.mLegacyIcon = drawable;
            return this;
        }

        @Deprecated
        public Builder setLabel(String str) {
            this.mLegacyLabel = str;
            return this;
        }

        @Deprecated
        public Builder setIntent(Intent intent) {
            this.mLegacyIntent = intent;
            return this;
        }

        @Deprecated
        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            this.mLegacyOnClickListener = onClickListener;
            return this;
        }

        public Builder setId(String str) {
            this.mId = str;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public TextClassification build() {
            EntityConfidence entityConfidence = new EntityConfidence(this.mTypeScoreMap);
            String str = this.mText;
            Drawable drawable = this.mLegacyIcon;
            String str2 = this.mLegacyLabel;
            Intent intent = this.mLegacyIntent;
            View.OnClickListener onClickListener = this.mLegacyOnClickListener;
            List<RemoteAction> list = this.mActions;
            String str3 = this.mId;
            Bundle bundle = this.mExtras;
            if (bundle == null) {
                bundle = Bundle.EMPTY;
            }
            return new TextClassification(str, drawable, str2, intent, onClickListener, list, entityConfidence, str3, bundle);
        }
    }

    public static final class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: android.view.textclassifier.TextClassification.Request.1
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
        private final int mEndIndex;
        private final Bundle mExtras;
        private final ZonedDateTime mReferenceTime;
        private final int mStartIndex;
        private SystemTextClassifierMetadata mSystemTcMetadata;
        private final CharSequence mText;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Request(CharSequence charSequence, int i, int i2, LocaleList localeList, ZonedDateTime zonedDateTime, Bundle bundle) {
            this.mText = charSequence;
            this.mStartIndex = i;
            this.mEndIndex = i2;
            this.mDefaultLocales = localeList;
            this.mReferenceTime = zonedDateTime;
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

        public LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
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
            private final int mEndIndex;
            private Bundle mExtras;
            private ZonedDateTime mReferenceTime;
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

            public Builder setReferenceTime(ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
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
                ZonedDateTime zonedDateTime = this.mReferenceTime;
                Bundle bundle = this.mExtras;
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                return new Request(spannedString, i, i2, localeList, zonedDateTime, bundle);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeCharSequence(this.mText);
            parcel.writeInt(this.mStartIndex);
            parcel.writeInt(this.mEndIndex);
            parcel.writeParcelable(this.mDefaultLocales, i);
            ZonedDateTime zonedDateTime = this.mReferenceTime;
            parcel.writeString(zonedDateTime == null ? null : zonedDateTime.toString());
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Request readFromParcel(Parcel parcel) {
            CharSequence charSequence = parcel.readCharSequence();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            LocaleList localeList = (LocaleList) parcel.readParcelable(null, LocaleList.class);
            String string = parcel.readString();
            ZonedDateTime zonedDateTime = string == null ? null : ZonedDateTime.parse(string);
            Bundle bundle = parcel.readBundle();
            SystemTextClassifierMetadata systemTextClassifierMetadata = (SystemTextClassifierMetadata) parcel.readParcelable(null, SystemTextClassifierMetadata.class);
            Request request = new Request(charSequence, i, i2, localeList, zonedDateTime, bundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mText);
        parcel.writeTypedList(this.mActions);
        this.mEntityConfidence.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeBundle(this.mExtras);
    }

    private TextClassification(Parcel parcel) {
        this.mText = parcel.readString();
        ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(RemoteAction.CREATOR);
        this.mActions = arrayListCreateTypedArrayList;
        if (!arrayListCreateTypedArrayList.isEmpty()) {
            RemoteAction remoteAction = (RemoteAction) arrayListCreateTypedArrayList.get(0);
            this.mLegacyIcon = maybeLoadDrawable(remoteAction.getIcon());
            this.mLegacyLabel = remoteAction.getTitle().toString();
            this.mLegacyOnClickListener = createIntentOnClickListener(((RemoteAction) arrayListCreateTypedArrayList.get(0)).getActionIntent());
        } else {
            this.mLegacyIcon = null;
            this.mLegacyLabel = null;
            this.mLegacyOnClickListener = null;
        }
        this.mLegacyIntent = null;
        this.mEntityConfidence = EntityConfidence.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    private static Drawable maybeLoadDrawable(Icon icon) {
        if (icon == null) {
            return null;
        }
        int type = icon.getType();
        if (type == 1) {
            return new BitmapDrawable(Resources.getSystem(), icon.getBitmap());
        }
        if (type == 3) {
            return new BitmapDrawable(Resources.getSystem(), BitmapFactory.decodeByteArray(icon.getDataBytes(), icon.getDataOffset(), icon.getDataLength()));
        }
        if (type != 5) {
            return null;
        }
        return new AdaptiveIconDrawable((Drawable) null, new BitmapDrawable(Resources.getSystem(), icon.getBitmap()));
    }
}
