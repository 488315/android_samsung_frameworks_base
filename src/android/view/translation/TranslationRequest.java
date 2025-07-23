package android.view.translation;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntFunction;

/* loaded from: classes4.dex */
public final class TranslationRequest implements Parcelable {
    public static final Parcelable.Creator<TranslationRequest> CREATOR = new Parcelable.Creator<TranslationRequest>() { // from class: android.view.translation.TranslationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationRequest[] newArray(int i) {
            return new TranslationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationRequest createFromParcel(Parcel parcel) {
            return new TranslationRequest(parcel);
        }
    };
    public static final int FLAG_DICTIONARY_RESULT = 2;
    public static final int FLAG_PARTIAL_RESPONSES = 8;
    public static final int FLAG_TRANSLATION_RESULT = 1;
    public static final int FLAG_TRANSLITERATION_RESULT = 4;
    private final int mFlags;
    private final List<TranslationRequestValue> mTranslationRequestValues;
    private final List<ViewTranslationRequest> mViewTranslationRequests;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultFlags() {
        return 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<TranslationRequestValue> defaultTranslationRequestValues() {
        return Collections.EMPTY_LIST;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<ViewTranslationRequest> defaultViewTranslationRequests() {
        return Collections.EMPTY_LIST;
    }

    static abstract class BaseBuilder {
        @Deprecated
        public abstract Builder addTranslationRequestValue(TranslationRequestValue translationRequestValue);

        @Deprecated
        public abstract Builder addViewTranslationRequest(ViewTranslationRequest viewTranslationRequest);

        BaseBuilder() {
        }
    }

    public static String requestFlagsToString(int i) {
        return BitUtils.flagsToString(i, new IntFunction() { // from class: android.view.translation.TranslationRequest$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return TranslationRequest.singleRequestFlagsToString(i2);
            }
        });
    }

    static String singleRequestFlagsToString(int i) {
        if (i == 1) {
            return "FLAG_TRANSLATION_RESULT";
        }
        if (i == 2) {
            return "FLAG_DICTIONARY_RESULT";
        }
        if (i == 4) {
            return "FLAG_TRANSLITERATION_RESULT";
        }
        if (i == 8) {
            return "FLAG_PARTIAL_RESPONSES";
        }
        return Integer.toHexString(i);
    }

    TranslationRequest(int i, List<TranslationRequestValue> list, List<ViewTranslationRequest> list2) {
        this.mFlags = i;
        Preconditions.checkFlagsArgument(i, 15);
        this.mTranslationRequestValues = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.mViewTranslationRequests = list2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list2);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public List<TranslationRequestValue> getTranslationRequestValues() {
        return this.mTranslationRequestValues;
    }

    public List<ViewTranslationRequest> getViewTranslationRequests() {
        return this.mViewTranslationRequests;
    }

    public String toString() {
        return "TranslationRequest { flags = " + requestFlagsToString(this.mFlags) + ", translationRequestValues = " + this.mTranslationRequestValues + ", viewTranslationRequests = " + this.mViewTranslationRequests + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mFlags);
        parcel.writeParcelableList(this.mTranslationRequestValues, i);
        parcel.writeParcelableList(this.mViewTranslationRequests, i);
    }

    TranslationRequest(Parcel parcel) {
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, TranslationRequestValue.class.getClassLoader(), TranslationRequestValue.class);
        ArrayList arrayList2 = new ArrayList();
        parcel.readParcelableList(arrayList2, ViewTranslationRequest.class.getClassLoader(), ViewTranslationRequest.class);
        this.mFlags = readInt;
        Preconditions.checkFlagsArgument(readInt, 15);
        this.mTranslationRequestValues = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mViewTranslationRequests = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList2);
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private int mFlags;
        private List<TranslationRequestValue> mTranslationRequestValues;
        private List<ViewTranslationRequest> mViewTranslationRequests;

        public Builder setFlags(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mFlags = i;
            return this;
        }

        public Builder setTranslationRequestValues(List<TranslationRequestValue> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationRequestValues = list;
            return this;
        }

        @Override // android.view.translation.TranslationRequest.BaseBuilder
        @Deprecated
        public Builder addTranslationRequestValue(TranslationRequestValue translationRequestValue) {
            if (this.mTranslationRequestValues == null) {
                setTranslationRequestValues(new ArrayList());
            }
            this.mTranslationRequestValues.add(translationRequestValue);
            return this;
        }

        public Builder setViewTranslationRequests(List<ViewTranslationRequest> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mViewTranslationRequests = list;
            return this;
        }

        @Override // android.view.translation.TranslationRequest.BaseBuilder
        @Deprecated
        public Builder addViewTranslationRequest(ViewTranslationRequest viewTranslationRequest) {
            if (this.mViewTranslationRequests == null) {
                setViewTranslationRequests(new ArrayList());
            }
            this.mViewTranslationRequests.add(viewTranslationRequest);
            return this;
        }

        public TranslationRequest build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 8;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mFlags = TranslationRequest.defaultFlags();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mTranslationRequestValues = TranslationRequest.defaultTranslationRequestValues();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mViewTranslationRequests = TranslationRequest.defaultViewTranslationRequests();
            }
            return new TranslationRequest(this.mFlags, this.mTranslationRequestValues, this.mViewTranslationRequests);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
