package android.service.autofill;

import android.content.IntentSender;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class SaveInfo implements Parcelable {
    public static final Parcelable.Creator<SaveInfo> CREATOR = new Parcelable.Creator<SaveInfo>() { // from class: android.service.autofill.SaveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SaveInfo createFromParcel(Parcel parcel) {
            Builder builder;
            int readInt = parcel.readInt();
            AutofillId[] autofillIdArr = (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class);
            if (autofillIdArr != null) {
                builder = new Builder(readInt, autofillIdArr);
            } else {
                builder = new Builder(readInt);
            }
            AutofillId[] autofillIdArr2 = (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class);
            if (autofillIdArr2 != null) {
                builder.setOptionalIds(autofillIdArr2);
            }
            int readInt2 = parcel.readInt();
            if (readInt2 == 999) {
                builder.semSetNegativeSecondAction(readInt2, (IntentSender) parcel.readParcelable(null, IntentSender.class), (IntentSender) parcel.readParcelable(null, IntentSender.class));
            } else {
                builder.setNegativeAction(readInt2, (IntentSender) parcel.readParcelable(null, IntentSender.class));
                parcel.readParcelable(null, IntentSender.class);
            }
            builder.setPositiveAction(parcel.readInt());
            builder.setDescription(parcel.readCharSequence());
            CustomDescription customDescription = (CustomDescription) parcel.readParcelable(null, CustomDescription.class);
            if (customDescription != null) {
                builder.setCustomDescription(customDescription);
            }
            InternalValidator internalValidator = (InternalValidator) parcel.readParcelable(null, InternalValidator.class);
            if (internalValidator != null) {
                builder.setValidator(internalValidator);
            }
            InternalSanitizer[] internalSanitizerArr = (InternalSanitizer[]) parcel.readParcelableArray(null, InternalSanitizer.class);
            if (internalSanitizerArr != null) {
                for (InternalSanitizer internalSanitizer : internalSanitizerArr) {
                    builder.addSanitizer(internalSanitizer, (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class));
                }
            }
            AutofillId autofillId = (AutofillId) parcel.readParcelable(null, AutofillId.class);
            if (autofillId != null) {
                builder.setTriggerId(autofillId);
            }
            builder.setFlags(parcel.readInt());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SaveInfo[] newArray(int i) {
            return new SaveInfo[i];
        }
    };
    public static final int FLAG_DELAY_SAVE = 4;
    public static final int FLAG_DONT_SAVE_ON_FINISH = 2;
    public static final int FLAG_SAVE_ON_ALL_VIEWS_INVISIBLE = 1;
    public static final int NEGATIVE_BUTTON_STYLE_CANCEL = 0;
    public static final int NEGATIVE_BUTTON_STYLE_NEVER = 2;
    public static final int NEGATIVE_BUTTON_STYLE_REJECT = 1;
    public static final int POSITIVE_BUTTON_STYLE_CONTINUE = 1;
    public static final int POSITIVE_BUTTON_STYLE_SAVE = 0;
    public static final int SAVE_DATA_TYPE_ADDRESS = 2;
    public static final int SAVE_DATA_TYPE_CREDIT_CARD = 4;
    public static final int SAVE_DATA_TYPE_DEBIT_CARD = 32;
    public static final int SAVE_DATA_TYPE_EMAIL_ADDRESS = 16;
    public static final int SAVE_DATA_TYPE_GENERIC = 0;
    public static final int SAVE_DATA_TYPE_GENERIC_CARD = 128;
    public static final int SAVE_DATA_TYPE_PASSWORD = 1;
    public static final int SAVE_DATA_TYPE_PAYMENT_CARD = 64;
    public static final int SAVE_DATA_TYPE_USERNAME = 8;
    public static final int SEM_NEGATIVE_BUTTON_STYLE_BOTH = 999;
    private final CustomDescription mCustomDescription;
    private final CharSequence mDescription;
    private final int mFlags;
    private final IntentSender mNegativeActionListener;
    private final int mNegativeButtonStyle;
    private final AutofillId[] mOptionalIds;
    private final int mPositiveButtonStyle;
    private final AutofillId[] mRequiredIds;
    private final InternalSanitizer[] mSanitizerKeys;
    private final AutofillId[][] mSanitizerValues;
    private final IntentSender mSemNegativeSecondActionListener;
    private final AutofillId mTriggerId;
    private final int mType;
    private final InternalValidator mValidator;

    @Retention(RetentionPolicy.SOURCE)
    @interface NegativeButtonStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface PositiveButtonStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface SaveDataType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface SaveInfoFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static SaveInfo copy(SaveInfo saveInfo, AutofillId[] autofillIdArr) {
        return new SaveInfo(saveInfo.mType, saveInfo.mNegativeButtonStyle, saveInfo.mPositiveButtonStyle, saveInfo.mNegativeActionListener, saveInfo.mSemNegativeSecondActionListener, saveInfo.mRequiredIds, AutofillServiceHelper.assertValid(autofillIdArr), saveInfo.mDescription, saveInfo.mFlags, saveInfo.mCustomDescription, saveInfo.mValidator, saveInfo.mSanitizerKeys, saveInfo.mSanitizerValues, saveInfo.mTriggerId);
    }

    private SaveInfo(int i, int i2, int i3, IntentSender intentSender, IntentSender intentSender2, AutofillId[] autofillIdArr, AutofillId[] autofillIdArr2, CharSequence charSequence, int i4, CustomDescription customDescription, InternalValidator internalValidator, InternalSanitizer[] internalSanitizerArr, AutofillId[][] autofillIdArr3, AutofillId autofillId) {
        this.mType = i;
        this.mNegativeButtonStyle = i2;
        this.mNegativeActionListener = intentSender;
        this.mSemNegativeSecondActionListener = intentSender2;
        this.mPositiveButtonStyle = i3;
        this.mRequiredIds = autofillIdArr;
        this.mOptionalIds = autofillIdArr2;
        this.mDescription = charSequence;
        this.mFlags = i4;
        this.mCustomDescription = customDescription;
        this.mValidator = internalValidator;
        this.mSanitizerKeys = internalSanitizerArr;
        this.mSanitizerValues = autofillIdArr3;
        this.mTriggerId = autofillId;
    }

    private SaveInfo(Builder builder) {
        this.mType = builder.mType;
        this.mNegativeButtonStyle = builder.mNegativeButtonStyle;
        this.mNegativeActionListener = builder.mNegativeActionListener;
        this.mSemNegativeSecondActionListener = builder.mSemNegativeSecondActionListener;
        this.mPositiveButtonStyle = builder.mPositiveButtonStyle;
        this.mRequiredIds = builder.mRequiredIds;
        this.mOptionalIds = builder.mOptionalIds;
        this.mDescription = builder.mDescription;
        this.mFlags = builder.mFlags;
        this.mCustomDescription = builder.mCustomDescription;
        this.mValidator = builder.mValidator;
        if (builder.mSanitizers == null) {
            this.mSanitizerKeys = null;
            this.mSanitizerValues = null;
        } else {
            int size = builder.mSanitizers.size();
            this.mSanitizerKeys = new InternalSanitizer[size];
            this.mSanitizerValues = new AutofillId[size][];
            for (int i = 0; i < size; i++) {
                this.mSanitizerKeys[i] = (InternalSanitizer) builder.mSanitizers.keyAt(i);
                this.mSanitizerValues[i] = (AutofillId[]) builder.mSanitizers.valueAt(i);
            }
        }
        this.mTriggerId = builder.mTriggerId;
    }

    public int getNegativeActionStyle() {
        return this.mNegativeButtonStyle;
    }

    public IntentSender getNegativeActionListener() {
        return this.mNegativeActionListener;
    }

    public IntentSender semGetNegativeSecondActionListener() {
        return this.mSemNegativeSecondActionListener;
    }

    public int getPositiveActionStyle() {
        return this.mPositiveButtonStyle;
    }

    public AutofillId[] getRequiredIds() {
        return this.mRequiredIds;
    }

    public AutofillId[] getOptionalIds() {
        return this.mOptionalIds;
    }

    public int getType() {
        return this.mType;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public CustomDescription getCustomDescription() {
        return this.mCustomDescription;
    }

    public InternalValidator getValidator() {
        return this.mValidator;
    }

    public InternalSanitizer[] getSanitizerKeys() {
        return this.mSanitizerKeys;
    }

    public AutofillId[][] getSanitizerValues() {
        return this.mSanitizerValues;
    }

    public AutofillId getTriggerId() {
        return this.mTriggerId;
    }

    public static final class Builder {
        private CustomDescription mCustomDescription;
        private CharSequence mDescription;
        private boolean mDestroyed;
        private int mFlags;
        private IntentSender mNegativeActionListener;
        private int mNegativeButtonStyle;
        private AutofillId[] mOptionalIds;
        private int mPositiveButtonStyle;
        private final AutofillId[] mRequiredIds;
        private ArraySet<AutofillId> mSanitizerIds;
        private ArrayMap<InternalSanitizer, AutofillId[]> mSanitizers;
        private IntentSender mSemNegativeSecondActionListener;
        private AutofillId mTriggerId;
        private final int mType;
        private InternalValidator mValidator;

        public Builder(int i, AutofillId[] autofillIdArr) {
            this.mNegativeButtonStyle = 0;
            this.mPositiveButtonStyle = 0;
            this.mType = i;
            this.mRequiredIds = AutofillServiceHelper.assertValid(autofillIdArr);
        }

        public Builder(int i) {
            this.mNegativeButtonStyle = 0;
            this.mPositiveButtonStyle = 0;
            this.mType = i;
            this.mRequiredIds = null;
        }

        public Builder setFlags(int i) {
            throwIfDestroyed();
            this.mFlags = Preconditions.checkFlagsArgument(i, 7);
            return this;
        }

        public Builder setOptionalIds(AutofillId[] autofillIdArr) {
            throwIfDestroyed();
            this.mOptionalIds = AutofillServiceHelper.assertValid(autofillIdArr);
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            throwIfDestroyed();
            Preconditions.checkState(this.mCustomDescription == null, "Can call setDescription() or setCustomDescription(), but not both");
            this.mDescription = charSequence;
            return this;
        }

        public Builder setCustomDescription(CustomDescription customDescription) {
            throwIfDestroyed();
            Preconditions.checkState(this.mDescription == null, "Can call setDescription() or setCustomDescription(), but not both");
            this.mCustomDescription = customDescription;
            return this;
        }

        public Builder setNegativeAction(int i, IntentSender intentSender) {
            throwIfDestroyed();
            Preconditions.checkArgumentInRange(i, 0, 2, "style");
            this.mNegativeButtonStyle = i;
            this.mNegativeActionListener = intentSender;
            return this;
        }

        public Builder semSetNegativeSecondAction(int i, IntentSender intentSender, IntentSender intentSender2) {
            throwIfDestroyed();
            if (i != 999) {
                throw new IllegalArgumentException("Invalid style: " + i);
            }
            this.mNegativeButtonStyle = i;
            this.mNegativeActionListener = intentSender;
            this.mSemNegativeSecondActionListener = intentSender2;
            return this;
        }

        public Builder setPositiveAction(int i) {
            throwIfDestroyed();
            Preconditions.checkArgumentInRange(i, 0, 1, "style");
            this.mPositiveButtonStyle = i;
            return this;
        }

        public Builder setValidator(Validator validator) {
            throwIfDestroyed();
            Preconditions.checkArgument(validator instanceof InternalValidator, "not provided by Android System: %s", validator);
            this.mValidator = (InternalValidator) validator;
            return this;
        }

        public Builder addSanitizer(Sanitizer sanitizer, AutofillId... autofillIdArr) {
            throwIfDestroyed();
            Preconditions.checkArgument(!ArrayUtils.isEmpty(autofillIdArr), "ids cannot be empty or null");
            Preconditions.checkArgument(sanitizer instanceof InternalSanitizer, "not provided by Android System: %s", sanitizer);
            if (this.mSanitizers == null) {
                this.mSanitizers = new ArrayMap<>();
                this.mSanitizerIds = new ArraySet<>(autofillIdArr.length);
            }
            for (AutofillId autofillId : autofillIdArr) {
                Preconditions.checkArgument(!this.mSanitizerIds.contains(autofillId), "already added %s", autofillId);
                this.mSanitizerIds.add(autofillId);
            }
            this.mSanitizers.put((InternalSanitizer) sanitizer, autofillIdArr);
            return this;
        }

        public Builder setTriggerId(AutofillId autofillId) {
            throwIfDestroyed();
            this.mTriggerId = (AutofillId) Objects.requireNonNull(autofillId);
            return this;
        }

        public SaveInfo build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new SaveInfo(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("Already called #build()");
            }
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder("SaveInfo: [type=");
        sb.append(DebugUtils.flagsToString(SaveInfo.class, "SAVE_DATA_TYPE_", this.mType));
        sb.append(", requiredIds=");
        sb.append(Arrays.toString(this.mRequiredIds));
        sb.append(", negative style=");
        sb.append(DebugUtils.flagsToString(SaveInfo.class, "NEGATIVE_BUTTON_STYLE_", this.mNegativeButtonStyle));
        sb.append(", positive style=");
        sb.append(DebugUtils.flagsToString(SaveInfo.class, "POSITIVE_BUTTON_STYLE_", this.mPositiveButtonStyle));
        if (this.mOptionalIds != null) {
            sb.append(", optionalIds=");
            sb.append(Arrays.toString(this.mOptionalIds));
        }
        if (this.mDescription != null) {
            sb.append(", description=");
            sb.append(this.mDescription);
        }
        if (this.mFlags != 0) {
            sb.append(", flags=");
            sb.append(this.mFlags);
        }
        if (this.mCustomDescription != null) {
            sb.append(", customDescription=");
            sb.append(this.mCustomDescription);
        }
        if (this.mValidator != null) {
            sb.append(", validator=");
            sb.append(this.mValidator);
        }
        if (this.mSanitizerKeys != null) {
            sb.append(", sanitizerKeys=");
            sb.append(this.mSanitizerKeys.length);
        }
        if (this.mSanitizerValues != null) {
            sb.append(", sanitizerValues=");
            sb.append(this.mSanitizerValues.length);
        }
        if (this.mTriggerId != null) {
            sb.append(", triggerId=");
            sb.append(this.mTriggerId);
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeParcelableArray(this.mRequiredIds, i);
        parcel.writeParcelableArray(this.mOptionalIds, i);
        parcel.writeInt(this.mNegativeButtonStyle);
        parcel.writeParcelable(this.mNegativeActionListener, i);
        parcel.writeParcelable(this.mSemNegativeSecondActionListener, i);
        parcel.writeInt(this.mPositiveButtonStyle);
        parcel.writeCharSequence(this.mDescription);
        parcel.writeParcelable(this.mCustomDescription, i);
        parcel.writeParcelable(this.mValidator, i);
        parcel.writeParcelableArray(this.mSanitizerKeys, i);
        if (this.mSanitizerKeys != null) {
            int i2 = 0;
            while (true) {
                AutofillId[][] autofillIdArr = this.mSanitizerValues;
                if (i2 >= autofillIdArr.length) {
                    break;
                }
                parcel.writeParcelableArray(autofillIdArr[i2], i);
                i2++;
            }
        }
        parcel.writeParcelable(this.mTriggerId, i);
        parcel.writeInt(this.mFlags);
    }
}
