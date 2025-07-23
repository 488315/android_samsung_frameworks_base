package android.view.textclassifier;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class TextClassifierEvent implements Parcelable {
    public static final int CATEGORY_CONVERSATION_ACTIONS = 3;
    public static final int CATEGORY_LANGUAGE_DETECTION = 4;
    public static final int CATEGORY_LINKIFY = 2;
    public static final int CATEGORY_SELECTION = 1;
    public static final Parcelable.Creator<TextClassifierEvent> CREATOR = new Parcelable.Creator<TextClassifierEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassifierEvent createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == 1) {
                return new TextSelectionEvent(parcel);
            }
            if (readInt == 2) {
                return new TextLinkifyEvent(parcel);
            }
            if (readInt == 4) {
                return new LanguageDetectionEvent(parcel);
            }
            if (readInt == 3) {
                return new ConversationActionsEvent(parcel);
            }
            throw new IllegalStateException("Unexpected input event type token in parcel.");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextClassifierEvent[] newArray(int i) {
            return new TextClassifierEvent[i];
        }
    };
    private static final int PARCEL_TOKEN_CONVERSATION_ACTION_EVENT = 3;
    private static final int PARCEL_TOKEN_LANGUAGE_DETECTION_EVENT = 4;
    private static final int PARCEL_TOKEN_TEXT_LINKIFY_EVENT = 2;
    private static final int PARCEL_TOKEN_TEXT_SELECTION_EVENT = 1;
    public static final int TYPE_ACTIONS_GENERATED = 20;
    public static final int TYPE_ACTIONS_SHOWN = 6;
    public static final int TYPE_AUTO_SELECTION = 5;
    public static final int TYPE_COPY_ACTION = 9;
    public static final int TYPE_CUT_ACTION = 11;
    public static final int TYPE_LINKS_GENERATED = 21;
    public static final int TYPE_LINK_CLICKED = 7;
    public static final int TYPE_MANUAL_REPLY = 19;
    public static final int TYPE_OTHER_ACTION = 16;
    public static final int TYPE_OVERTYPE = 8;
    public static final int TYPE_PASTE_ACTION = 10;
    public static final int TYPE_READ_CLIPBOARD = 22;
    public static final int TYPE_SELECTION_DESTROYED = 15;
    public static final int TYPE_SELECTION_DRAG = 14;
    public static final int TYPE_SELECTION_MODIFIED = 2;
    public static final int TYPE_SELECTION_RESET = 18;
    public static final int TYPE_SELECTION_STARTED = 1;
    public static final int TYPE_SELECT_ALL = 17;
    public static final int TYPE_SHARE_ACTION = 12;
    public static final int TYPE_SMART_ACTION = 13;
    public static final int TYPE_SMART_SELECTION_MULTI = 4;
    public static final int TYPE_SMART_SELECTION_SINGLE = 3;
    private final int[] mActionIndices;
    private final String[] mEntityTypes;
    private final int mEventCategory;
    private TextClassificationContext mEventContext;
    private final int mEventIndex;
    private final int mEventType;
    private final Bundle mExtras;
    public TextClassificationSessionId mHiddenTempSessionId;
    private final ULocale mLocale;
    private final String mModelName;
    private final String mResultId;
    private final float[] mScores;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    void toString(StringBuilder sb) {
    }

    private TextClassifierEvent(Builder builder) {
        this.mEventCategory = builder.mEventCategory;
        this.mEventType = builder.mEventType;
        this.mEntityTypes = builder.mEntityTypes;
        this.mEventContext = builder.mEventContext;
        this.mResultId = builder.mResultId;
        this.mEventIndex = builder.mEventIndex;
        this.mScores = builder.mScores;
        this.mModelName = builder.mModelName;
        this.mActionIndices = builder.mActionIndices;
        this.mLocale = builder.mLocale;
        this.mExtras = builder.mExtras == null ? Bundle.EMPTY : builder.mExtras;
    }

    private TextClassifierEvent(Parcel parcel) {
        this.mEventCategory = parcel.readInt();
        this.mEventType = parcel.readInt();
        this.mEntityTypes = parcel.readStringArray();
        this.mEventContext = (TextClassificationContext) parcel.readParcelable(null, TextClassificationContext.class);
        this.mResultId = parcel.readString();
        this.mEventIndex = parcel.readInt();
        float[] fArr = new float[parcel.readInt()];
        this.mScores = fArr;
        parcel.readFloatArray(fArr);
        this.mModelName = parcel.readString();
        this.mActionIndices = parcel.createIntArray();
        String readString = parcel.readString();
        this.mLocale = readString != null ? ULocale.forLanguageTag(readString) : null;
        this.mExtras = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getParcelToken());
        parcel.writeInt(this.mEventCategory);
        parcel.writeInt(this.mEventType);
        parcel.writeStringArray(this.mEntityTypes);
        parcel.writeParcelable(this.mEventContext, i);
        parcel.writeString(this.mResultId);
        parcel.writeInt(this.mEventIndex);
        parcel.writeInt(this.mScores.length);
        parcel.writeFloatArray(this.mScores);
        parcel.writeString(this.mModelName);
        parcel.writeIntArray(this.mActionIndices);
        ULocale uLocale = this.mLocale;
        parcel.writeString(uLocale == null ? null : uLocale.toLanguageTag());
        parcel.writeBundle(this.mExtras);
    }

    private int getParcelToken() {
        if (this instanceof TextSelectionEvent) {
            return 1;
        }
        if (this instanceof TextLinkifyEvent) {
            return 2;
        }
        if (this instanceof LanguageDetectionEvent) {
            return 4;
        }
        if (this instanceof ConversationActionsEvent) {
            return 3;
        }
        throw new IllegalArgumentException("Unexpected type: " + getClass().getSimpleName());
    }

    public int getEventCategory() {
        return this.mEventCategory;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public String[] getEntityTypes() {
        return this.mEntityTypes;
    }

    public TextClassificationContext getEventContext() {
        return this.mEventContext;
    }

    void setEventContext(TextClassificationContext textClassificationContext) {
        this.mEventContext = textClassificationContext;
    }

    public String getResultId() {
        return this.mResultId;
    }

    public int getEventIndex() {
        return this.mEventIndex;
    }

    public float[] getScores() {
        return this.mScores;
    }

    public String getModelName() {
        return this.mModelName;
    }

    public int[] getActionIndices() {
        return this.mActionIndices;
    }

    public ULocale getLocale() {
        return this.mLocale;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName());
        sb.append("{mEventCategory=");
        sb.append(this.mEventCategory);
        sb.append(", mEventType=");
        sb.append(this.mEventType);
        sb.append(", mEntityTypes=");
        sb.append(Arrays.toString(this.mEntityTypes));
        sb.append(", mEventContext=");
        sb.append(this.mEventContext);
        sb.append(", mResultId=");
        sb.append(this.mResultId);
        sb.append(", mEventIndex=");
        sb.append(this.mEventIndex);
        sb.append(", mExtras=");
        sb.append(this.mExtras);
        sb.append(", mScores=");
        sb.append(Arrays.toString(this.mScores));
        sb.append(", mModelName=");
        sb.append(this.mModelName);
        sb.append(", mActionIndices=");
        sb.append(Arrays.toString(this.mActionIndices));
        toString(sb);
        sb.append("}");
        return sb.toString();
    }

    public final SelectionEvent toSelectionEvent() {
        int i;
        int eventCategory = getEventCategory();
        int i2 = 2;
        if (eventCategory == 1) {
            i = 1;
        } else {
            if (eventCategory != 2) {
                return null;
            }
            i = 2;
        }
        SelectionEvent selectionEvent = new SelectionEvent(0, 0, 0, getEntityTypes().length > 0 ? getEntityTypes()[0] : "", 0, "");
        selectionEvent.setInvocationMethod(i);
        if (getEventContext() != null) {
            selectionEvent.setTextClassificationSessionContext(getEventContext());
        }
        selectionEvent.setSessionId(this.mHiddenTempSessionId);
        String resultId = getResultId();
        selectionEvent.setResultId(resultId != null ? resultId : "");
        selectionEvent.setEventIndex(getEventIndex());
        switch (getEventType()) {
            case 1:
                i2 = 1;
                break;
            case 2:
                break;
            case 3:
                i2 = 3;
                break;
            case 4:
                i2 = 4;
                break;
            case 5:
                i2 = 5;
                break;
            case 6:
            case 7:
            default:
                i2 = 0;
                break;
            case 8:
                i2 = 100;
                break;
            case 9:
                i2 = 101;
                break;
            case 10:
                i2 = 102;
                break;
            case 11:
                i2 = 103;
                break;
            case 12:
                i2 = 104;
                break;
            case 13:
                i2 = 105;
                break;
            case 14:
                i2 = 106;
                break;
            case 15:
                i2 = 107;
                break;
            case 16:
                i2 = 108;
                break;
            case 17:
                i2 = 200;
                break;
            case 18:
                i2 = 201;
                break;
        }
        selectionEvent.setEventType(i2);
        if (this instanceof TextSelectionEvent) {
            TextSelectionEvent textSelectionEvent = (TextSelectionEvent) this;
            selectionEvent.setStart(textSelectionEvent.getRelativeWordStartIndex());
            selectionEvent.setEnd(textSelectionEvent.getRelativeWordEndIndex());
            selectionEvent.setSmartStart(textSelectionEvent.getRelativeSuggestedWordStartIndex());
            selectionEvent.setSmartEnd(textSelectionEvent.getRelativeSuggestedWordEndIndex());
        }
        return selectionEvent;
    }

    public static abstract class Builder<T extends Builder<T>> {
        private int[] mActionIndices;
        private String[] mEntityTypes;
        private final int mEventCategory;
        private TextClassificationContext mEventContext;
        private int mEventIndex;
        private final int mEventType;
        private Bundle mExtras;
        private ULocale mLocale;
        private String mModelName;
        private String mResultId;
        private float[] mScores;

        abstract T self();

        private Builder(int i, int i2) {
            this.mEntityTypes = new String[0];
            this.mScores = new float[0];
            this.mActionIndices = new int[0];
            this.mEventCategory = i;
            this.mEventType = i2;
        }

        public T setEntityTypes(String... strArr) {
            Objects.requireNonNull(strArr);
            String[] strArr2 = new String[strArr.length];
            this.mEntityTypes = strArr2;
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            return self();
        }

        public T setEventContext(TextClassificationContext textClassificationContext) {
            this.mEventContext = textClassificationContext;
            return self();
        }

        public T setResultId(String str) {
            this.mResultId = str;
            return self();
        }

        public T setEventIndex(int i) {
            this.mEventIndex = i;
            return self();
        }

        public T setScores(float... fArr) {
            Objects.requireNonNull(fArr);
            float[] fArr2 = new float[fArr.length];
            this.mScores = fArr2;
            System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
            return self();
        }

        public T setModelName(String str) {
            this.mModelName = str;
            return self();
        }

        public T setActionIndices(int... iArr) {
            int[] iArr2 = new int[iArr.length];
            this.mActionIndices = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            return self();
        }

        public T setLocale(ULocale uLocale) {
            this.mLocale = uLocale;
            return self();
        }

        public T setExtras(Bundle bundle) {
            this.mExtras = (Bundle) Objects.requireNonNull(bundle);
            return self();
        }
    }

    public static final class TextSelectionEvent extends TextClassifierEvent implements Parcelable {
        public static final Parcelable.Creator<TextSelectionEvent> CREATOR = new Parcelable.Creator<TextSelectionEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TextSelectionEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new TextSelectionEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TextSelectionEvent[] newArray(int i) {
                return new TextSelectionEvent[i];
            }
        };
        final int mRelativeSuggestedWordEndIndex;
        final int mRelativeSuggestedWordStartIndex;
        final int mRelativeWordEndIndex;
        final int mRelativeWordStartIndex;

        private TextSelectionEvent(Builder builder) {
            super(builder);
            this.mRelativeWordStartIndex = builder.mRelativeWordStartIndex;
            this.mRelativeWordEndIndex = builder.mRelativeWordEndIndex;
            this.mRelativeSuggestedWordStartIndex = builder.mRelativeSuggestedWordStartIndex;
            this.mRelativeSuggestedWordEndIndex = builder.mRelativeSuggestedWordEndIndex;
        }

        private TextSelectionEvent(Parcel parcel) {
            super(parcel);
            this.mRelativeWordStartIndex = parcel.readInt();
            this.mRelativeWordEndIndex = parcel.readInt();
            this.mRelativeSuggestedWordStartIndex = parcel.readInt();
            this.mRelativeSuggestedWordEndIndex = parcel.readInt();
        }

        @Override // android.view.textclassifier.TextClassifierEvent, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mRelativeWordStartIndex);
            parcel.writeInt(this.mRelativeWordEndIndex);
            parcel.writeInt(this.mRelativeSuggestedWordStartIndex);
            parcel.writeInt(this.mRelativeSuggestedWordEndIndex);
        }

        public int getRelativeWordStartIndex() {
            return this.mRelativeWordStartIndex;
        }

        public int getRelativeWordEndIndex() {
            return this.mRelativeWordEndIndex;
        }

        public int getRelativeSuggestedWordStartIndex() {
            return this.mRelativeSuggestedWordStartIndex;
        }

        public int getRelativeSuggestedWordEndIndex() {
            return this.mRelativeSuggestedWordEndIndex;
        }

        @Override // android.view.textclassifier.TextClassifierEvent
        void toString(StringBuilder sb) {
            sb.append(", getRelativeWordStartIndex=");
            sb.append(this.mRelativeWordStartIndex);
            sb.append(", getRelativeWordEndIndex=");
            sb.append(this.mRelativeWordEndIndex);
            sb.append(", getRelativeSuggestedWordStartIndex=");
            sb.append(this.mRelativeSuggestedWordStartIndex);
            sb.append(", getRelativeSuggestedWordEndIndex=");
            sb.append(this.mRelativeSuggestedWordEndIndex);
        }

        public static final class Builder extends Builder<Builder> {
            int mRelativeSuggestedWordEndIndex;
            int mRelativeSuggestedWordStartIndex;
            int mRelativeWordEndIndex;
            int mRelativeWordStartIndex;

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public Builder self() {
                return this;
            }

            public Builder(int i) {
                super(1, i);
            }

            public Builder setRelativeWordStartIndex(int i) {
                this.mRelativeWordStartIndex = i;
                return this;
            }

            public Builder setRelativeWordEndIndex(int i) {
                this.mRelativeWordEndIndex = i;
                return this;
            }

            public Builder setRelativeSuggestedWordStartIndex(int i) {
                this.mRelativeSuggestedWordStartIndex = i;
                return this;
            }

            public Builder setRelativeSuggestedWordEndIndex(int i) {
                this.mRelativeSuggestedWordEndIndex = i;
                return this;
            }

            public TextSelectionEvent build() {
                return new TextSelectionEvent(this);
            }
        }
    }

    public static final class TextLinkifyEvent extends TextClassifierEvent implements Parcelable {
        public static final Parcelable.Creator<TextLinkifyEvent> CREATOR = new Parcelable.Creator<TextLinkifyEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TextLinkifyEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new TextLinkifyEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TextLinkifyEvent[] newArray(int i) {
                return new TextLinkifyEvent[i];
            }
        };

        private TextLinkifyEvent(Parcel parcel) {
            super(parcel);
        }

        private TextLinkifyEvent(Builder builder) {
            super(builder);
        }

        public static final class Builder extends Builder<Builder> {
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public Builder self() {
                return this;
            }

            public Builder(int i) {
                super(2, i);
            }

            public TextLinkifyEvent build() {
                return new TextLinkifyEvent(this);
            }
        }
    }

    public static final class LanguageDetectionEvent extends TextClassifierEvent implements Parcelable {
        public static final Parcelable.Creator<LanguageDetectionEvent> CREATOR = new Parcelable.Creator<LanguageDetectionEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LanguageDetectionEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new LanguageDetectionEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LanguageDetectionEvent[] newArray(int i) {
                return new LanguageDetectionEvent[i];
            }
        };

        private LanguageDetectionEvent(Parcel parcel) {
            super(parcel);
        }

        private LanguageDetectionEvent(Builder builder) {
            super(builder);
        }

        public static final class Builder extends Builder<Builder> {
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public Builder self() {
                return this;
            }

            public Builder(int i) {
                super(4, i);
            }

            public LanguageDetectionEvent build() {
                return new LanguageDetectionEvent(this);
            }
        }
    }

    public static final class ConversationActionsEvent extends TextClassifierEvent implements Parcelable {
        public static final Parcelable.Creator<ConversationActionsEvent> CREATOR = new Parcelable.Creator<ConversationActionsEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ConversationActionsEvent createFromParcel(Parcel parcel) {
                parcel.readInt();
                return new ConversationActionsEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ConversationActionsEvent[] newArray(int i) {
                return new ConversationActionsEvent[i];
            }
        };

        private ConversationActionsEvent(Parcel parcel) {
            super(parcel);
        }

        private ConversationActionsEvent(Builder builder) {
            super(builder);
        }

        public static final class Builder extends Builder<Builder> {
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public Builder self() {
                return this;
            }

            public Builder(int i) {
                super(3, i);
            }

            public ConversationActionsEvent build() {
                return new ConversationActionsEvent(this);
            }
        }
    }
}
