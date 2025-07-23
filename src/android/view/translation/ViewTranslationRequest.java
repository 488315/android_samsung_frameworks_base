package android.view.translation;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.autofill.AutofillId;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class ViewTranslationRequest implements Parcelable {
    public static final Parcelable.Creator<ViewTranslationRequest> CREATOR = new Parcelable.Creator<ViewTranslationRequest>() { // from class: android.view.translation.ViewTranslationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewTranslationRequest[] newArray(int i) {
            return new ViewTranslationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewTranslationRequest createFromParcel(Parcel parcel) {
            return new ViewTranslationRequest(parcel);
        }
    };
    public static final String ID_CONTENT_DESCRIPTION = "android:content_description";
    public static final String ID_TEXT = "android:text";
    private final AutofillId mAutofillId;
    private final Map<String, TranslationRequestValue> mTranslationRequestValues;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Id {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TranslationRequestValue getValue(String str) {
        Objects.requireNonNull(str, "key should not be null");
        if (!this.mTranslationRequestValues.containsKey(str)) {
            throw new IllegalArgumentException("Request does not contain value for key=" + str);
        }
        return this.mTranslationRequestValues.get(str);
    }

    public Set<String> getKeys() {
        return this.mTranslationRequestValues.keySet();
    }

    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, TranslationRequestValue> defaultTranslationRequestValues() {
        return Collections.EMPTY_MAP;
    }

    public static final class Builder {
        private AutofillId mAutofillId;
        private long mBuilderFieldsSet = 0;
        private Map<String, TranslationRequestValue> mTranslationRequestValues;

        public Builder(AutofillId autofillId) {
            this.mAutofillId = autofillId;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        }

        public Builder(AutofillId autofillId, long j) {
            AutofillId autofillId2 = new AutofillId(autofillId, j, 0);
            this.mAutofillId = autofillId2;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId2);
        }

        public Builder setValue(String str, TranslationRequestValue translationRequestValue) {
            if (this.mTranslationRequestValues == null) {
                setTranslationRequestValues(new ArrayMap());
            }
            this.mTranslationRequestValues.put(str, translationRequestValue);
            return this;
        }

        public ViewTranslationRequest build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 4;
            this.mBuilderFieldsSet = j;
            if ((j & 2) == 0) {
                this.mTranslationRequestValues = ViewTranslationRequest.defaultTranslationRequestValues();
            }
            return new ViewTranslationRequest(this.mAutofillId, this.mTranslationRequestValues);
        }

        Builder setTranslationRequestValues(Map<String, TranslationRequestValue> map) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationRequestValues = map;
            return this;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    public ViewTranslationRequest(AutofillId autofillId, Map<String, TranslationRequestValue> map) {
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mTranslationRequestValues = map;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) map);
    }

    public String toString() {
        return "ViewTranslationRequest { autofillId = " + this.mAutofillId + ", translationRequestValues = " + this.mTranslationRequestValues + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ViewTranslationRequest viewTranslationRequest = (ViewTranslationRequest) obj;
            if (Objects.equals(this.mAutofillId, viewTranslationRequest.mAutofillId) && Objects.equals(this.mTranslationRequestValues, viewTranslationRequest.mTranslationRequestValues)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mAutofillId) + 31) * 31) + Objects.hashCode(this.mTranslationRequestValues);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAutofillId, i);
        parcel.writeMap(this.mTranslationRequestValues);
    }

    ViewTranslationRequest(Parcel parcel) {
        AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        parcel.readMap(linkedHashMap, TranslationRequestValue.class.getClassLoader());
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mTranslationRequestValues = linkedHashMap;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) linkedHashMap);
    }
}
