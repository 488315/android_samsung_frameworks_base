package android.view.textclassifier;

import android.annotation.SystemApi;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.ArrayMap;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.samsung.android.lock.LsConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public interface TextClassifier {

    @SystemApi
    public static final int CLASSIFIER_TYPE_ANDROID_DEFAULT = 2;

    @SystemApi
    public static final int CLASSIFIER_TYPE_DEVICE_DEFAULT = 1;

    @SystemApi
    public static final int CLASSIFIER_TYPE_SELF_PROVIDED = 0;
    public static final int DEFAULT_SYSTEM = 2;
    public static final String EXTRA_FROM_TEXT_CLASSIFIER = "android.view.textclassifier.extra.FROM_TEXT_CLASSIFIER";
    public static final String EXTRA_TEXT_ORIGIN_PACKAGE = "android.view.textclassifier.extra.TEXT_ORIGIN_PACKAGE";
    public static final String HINT_TEXT_IS_EDITABLE = "android.text_is_editable";
    public static final String HINT_TEXT_IS_NOT_EDITABLE = "android.text_is_not_editable";
    public static final int LOCAL = 0;
    public static final String LOG_TAG = "androidtc";
    public static final TextClassifier NO_OP = new TextClassifier() { // from class: android.view.textclassifier.TextClassifier.1
        public String toString() {
            return "TextClassifier.NO_OP";
        }
    };
    public static final int SYSTEM = 1;
    public static final String TYPE_ADDRESS = "address";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DATE_TIME = "datetime";
    public static final String TYPE_DICTIONARY = "dictionary";
    public static final String TYPE_EMAIL = "email";
    public static final String TYPE_FLIGHT_NUMBER = "flight";
    public static final String TYPE_OTHER = "other";
    public static final String TYPE_OTP = "otp";
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_UNKNOWN = "";
    public static final String TYPE_URL = "url";
    public static final String WIDGET_TYPE_CLIPBOARD = "clipboard";
    public static final String WIDGET_TYPE_CUSTOM_EDITTEXT = "customedit";
    public static final String WIDGET_TYPE_CUSTOM_TEXTVIEW = "customview";
    public static final String WIDGET_TYPE_CUSTOM_UNSELECTABLE_TEXTVIEW = "nosel-customview";
    public static final String WIDGET_TYPE_EDITTEXT = "edittext";
    public static final String WIDGET_TYPE_EDIT_WEBVIEW = "edit-webview";
    public static final String WIDGET_TYPE_NOTIFICATION = "notification";
    public static final String WIDGET_TYPE_TEXTVIEW = "textview";
    public static final String WIDGET_TYPE_UNKNOWN = "unknown";
    public static final String WIDGET_TYPE_UNSELECTABLE_TEXTVIEW = "nosel-textview";
    public static final String WIDGET_TYPE_WEBVIEW = "webview";

    @Retention(RetentionPolicy.SOURCE)
    public @interface EntityType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Hints {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextClassifierType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WidgetType {
    }

    default void destroy() {
    }

    default void dump(IndentingPrintWriter indentingPrintWriter) {
    }

    default int getMaxGenerateLinksTextLength() {
        return Integer.MAX_VALUE;
    }

    default boolean isDestroyed() {
        return false;
    }

    default void onSelectionEvent(SelectionEvent selectionEvent) {
    }

    default void onTextClassifierEvent(TextClassifierEvent textClassifierEvent) {
    }

    static String typeToString(int i) {
        if (i == 0) {
            return "Local";
        }
        if (i == 1) {
            return "System";
        }
        if (i == 2) {
            return "Default system";
        }
        return LsConstants.TAG_UNKNOWN;
    }

    default TextSelection suggestSelection(TextSelection.Request request) {
        Objects.requireNonNull(request);
        Utils.checkMainThread();
        return new TextSelection.Builder(request.getStartIndex(), request.getEndIndex()).build();
    }

    default TextSelection suggestSelection(CharSequence charSequence, int i, int i2, LocaleList localeList) {
        return suggestSelection(new TextSelection.Request.Builder(charSequence, i, i2).setDefaultLocales(localeList).build());
    }

    default TextClassification classifyText(TextClassification.Request request) {
        Objects.requireNonNull(request);
        Utils.checkMainThread();
        return TextClassification.EMPTY;
    }

    default TextClassification classifyText(CharSequence charSequence, int i, int i2, LocaleList localeList) {
        return classifyText(new TextClassification.Request.Builder(charSequence, i, i2).setDefaultLocales(localeList).build());
    }

    default TextLinks generateLinks(TextLinks.Request request) {
        Objects.requireNonNull(request);
        Utils.checkMainThread();
        return new TextLinks.Builder(request.getText().toString()).build();
    }

    default TextLanguage detectLanguage(TextLanguage.Request request) {
        Objects.requireNonNull(request);
        Utils.checkMainThread();
        return TextLanguage.EMPTY;
    }

    default ConversationActions suggestConversationActions(ConversationActions.Request request) {
        Objects.requireNonNull(request);
        Utils.checkMainThread();
        return new ConversationActions((List<ConversationAction>) Collections.EMPTY_LIST, (String) null);
    }

    public static final class EntityConfig implements Parcelable {
        public static final Parcelable.Creator<EntityConfig> CREATOR = new Parcelable.Creator<EntityConfig>() { // from class: android.view.textclassifier.TextClassifier.EntityConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EntityConfig createFromParcel(Parcel parcel) {
                return new EntityConfig(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public EntityConfig[] newArray(int i) {
                return new EntityConfig[i];
            }
        };
        private final List<String> mExcludedTypes;
        private final List<String> mHints;
        private final boolean mIncludeTypesFromTextClassifier;
        private final List<String> mIncludedTypes;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private EntityConfig(List<String> list, List<String> list2, List<String> list3, boolean z) {
            this.mIncludedTypes = (List) Objects.requireNonNull(list);
            this.mExcludedTypes = (List) Objects.requireNonNull(list2);
            this.mHints = (List) Objects.requireNonNull(list3);
            this.mIncludeTypesFromTextClassifier = z;
        }

        private EntityConfig(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            this.mIncludedTypes = arrayList;
            parcel.readStringList(arrayList);
            ArrayList arrayList2 = new ArrayList();
            this.mExcludedTypes = arrayList2;
            parcel.readStringList(arrayList2);
            ArrayList arrayList3 = new ArrayList();
            parcel.readStringList(arrayList3);
            this.mHints = Collections.unmodifiableList(arrayList3);
            this.mIncludeTypesFromTextClassifier = parcel.readByte() != 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeStringList(this.mIncludedTypes);
            parcel.writeStringList(this.mExcludedTypes);
            parcel.writeStringList(this.mHints);
            parcel.writeByte(this.mIncludeTypesFromTextClassifier ? (byte) 1 : (byte) 0);
        }

        @Deprecated
        public static EntityConfig createWithHints(Collection<String> collection) {
            return new Builder().includeTypesFromTextClassifier(true).setHints(collection).build();
        }

        @Deprecated
        public static EntityConfig create(Collection<String> collection, Collection<String> collection2, Collection<String> collection3) {
            return new Builder().setIncludedTypes(collection2).setExcludedTypes(collection3).setHints(collection).includeTypesFromTextClassifier(true).build();
        }

        @Deprecated
        public static EntityConfig createWithExplicitEntityList(Collection<String> collection) {
            return new Builder().setIncludedTypes(collection).includeTypesFromTextClassifier(false).build();
        }

        public Collection<String> resolveEntityListModifications(Collection<String> collection) {
            HashSet hashSet = new HashSet();
            if (this.mIncludeTypesFromTextClassifier) {
                hashSet.addAll(collection);
            }
            hashSet.addAll(this.mIncludedTypes);
            hashSet.removeAll(this.mExcludedTypes);
            return hashSet;
        }

        public Collection<String> getHints() {
            return this.mHints;
        }

        public boolean shouldIncludeTypesFromTextClassifier() {
            return this.mIncludeTypesFromTextClassifier;
        }

        public static final class Builder {
            private Collection<String> mExcludedTypes;
            private Collection<String> mHints;
            private boolean mIncludeTypesFromTextClassifier = true;
            private Collection<String> mIncludedTypes;

            public Builder setIncludedTypes(Collection<String> collection) {
                this.mIncludedTypes = collection;
                return this;
            }

            public Builder setExcludedTypes(Collection<String> collection) {
                this.mExcludedTypes = collection;
                return this;
            }

            public Builder includeTypesFromTextClassifier(boolean z) {
                this.mIncludeTypesFromTextClassifier = z;
                return this;
            }

            public Builder setHints(Collection<String> collection) {
                this.mHints = collection;
                return this;
            }

            public EntityConfig build() {
                List arrayList;
                List arrayList2;
                List listUnmodifiableList;
                if (this.mIncludedTypes == null) {
                    arrayList = Collections.EMPTY_LIST;
                } else {
                    arrayList = new ArrayList(this.mIncludedTypes);
                }
                if (this.mExcludedTypes == null) {
                    arrayList2 = Collections.EMPTY_LIST;
                } else {
                    arrayList2 = new ArrayList(this.mExcludedTypes);
                }
                if (this.mHints == null) {
                    listUnmodifiableList = Collections.EMPTY_LIST;
                } else {
                    listUnmodifiableList = Collections.unmodifiableList(new ArrayList(this.mHints));
                }
                return new EntityConfig(arrayList, arrayList2, listUnmodifiableList, this.mIncludeTypesFromTextClassifier);
            }
        }
    }

    public static final class Utils {
        private static final BreakIterator WORD_ITERATOR = BreakIterator.getWordInstance();

        static void checkArgument(CharSequence charSequence, int i, int i2) {
            Preconditions.checkArgument(charSequence != null);
            Preconditions.checkArgument(i >= 0);
            Preconditions.checkArgument(i2 <= charSequence.length());
            Preconditions.checkArgument(i2 > i);
        }

        static boolean checkTextLength(CharSequence charSequence, int i) {
            int length = charSequence.length();
            return length >= 0 && length <= i;
        }

        public static String getSubString(String str, int i, int i2, int i3) {
            String strSubstring;
            Preconditions.checkArgument(i >= 0);
            Preconditions.checkArgument(i2 <= str.length());
            Preconditions.checkArgument(i <= i2);
            if (str.length() < i3) {
                return str;
            }
            int i4 = i2 - i;
            if (i4 >= i3) {
                return str.substring(i, i2);
            }
            int iMax = Math.max(0, Math.min(i - ((i3 - i4) / 2), str.length() - i3));
            int iMin = Math.min(str.length(), i3 + iMax);
            BreakIterator breakIterator = WORD_ITERATOR;
            synchronized (breakIterator) {
                breakIterator.setText(str);
                if (!breakIterator.isBoundary(iMax)) {
                    iMax = Math.max(0, breakIterator.preceding(iMax));
                }
                if (!breakIterator.isBoundary(iMin)) {
                    iMin = Math.max(iMin, breakIterator.following(iMin));
                }
                breakIterator.setText("");
                strSubstring = str.substring(iMax, iMin);
            }
            return strSubstring;
        }

        public static TextLinks generateLegacyLinks(TextLinks.Request request) {
            String string = request.getText().toString();
            TextLinks.Builder builder = new TextLinks.Builder(string);
            Collection<String> collectionResolveEntityListModifications = request.getEntityConfig().resolveEntityListModifications(Collections.EMPTY_LIST);
            if (collectionResolveEntityListModifications.contains("url")) {
                addLinks(builder, string, "url");
            }
            if (collectionResolveEntityListModifications.contains("phone")) {
                addLinks(builder, string, "phone");
            }
            if (collectionResolveEntityListModifications.contains("email")) {
                addLinks(builder, string, "email");
            }
            return builder.build();
        }

        private static void addLinks(TextLinks.Builder builder, String str, String str2) {
            SpannableString spannableString = new SpannableString(str);
            if (Linkify.addLinks(spannableString, linkMask(str2))) {
                for (URLSpan uRLSpan : (URLSpan[]) spannableString.getSpans(0, spannableString.length(), URLSpan.class)) {
                    builder.addLink(spannableString.getSpanStart(uRLSpan), spannableString.getSpanEnd(uRLSpan), entityScores(str2), uRLSpan);
                }
            }
        }

        private static int linkMask(String str) {
            str.hashCode();
            switch (str) {
                case "url":
                    return 1;
                case "email":
                    return 2;
                case "phone":
                    return 4;
                default:
                    return 0;
            }
        }

        private static Map<String, Float> entityScores(String str) {
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put(str, Float.valueOf(1.0f));
            return arrayMap;
        }

        static void checkMainThread() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Log.w(TextClassifier.LOG_TAG, "TextClassifier called on main thread");
            }
        }
    }
}
