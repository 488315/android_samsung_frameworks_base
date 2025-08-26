package android.view.translation;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.autofill.AutofillId;
import com.android.internal.util.AnnotationValidations;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class ViewTranslationResponse implements Parcelable {
    public static final Parcelable.Creator<ViewTranslationResponse> CREATOR = new Parcelable.Creator<ViewTranslationResponse>() { // from class: android.view.translation.ViewTranslationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewTranslationResponse[] newArray(int i) {
            return new ViewTranslationResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ViewTranslationResponse createFromParcel(Parcel parcel) {
            return new ViewTranslationResponse(parcel);
        }
    };
    private final AutofillId mAutofillId;
    private final Map<String, TranslationResponseValue> mTranslationResponseValues;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TranslationResponseValue getValue(String str) {
        Objects.requireNonNull(str);
        if (!this.mTranslationResponseValues.containsKey(str)) {
            throw new IllegalArgumentException("Request does not contain value for key=" + str);
        }
        return this.mTranslationResponseValues.get(str);
    }

    public Set<String> getKeys() {
        return this.mTranslationResponseValues.keySet();
    }

    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, TranslationResponseValue> defaultTranslationResponseValues() {
        return Collections.EMPTY_MAP;
    }

    static abstract class BaseBuilder {
        abstract Builder setTranslationResponseValues(Map<String, TranslationResponseValue> map);

        BaseBuilder() {
        }

        public Builder setValue(String str, TranslationResponseValue translationResponseValue) {
            Builder builder = (Builder) this;
            if (builder.mTranslationResponseValues == null) {
                setTranslationResponseValues(new ArrayMap());
            }
            builder.mTranslationResponseValues.put(str, translationResponseValue);
            return builder;
        }
    }

    ViewTranslationResponse(AutofillId autofillId, Map<String, TranslationResponseValue> map) {
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mTranslationResponseValues = map;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) map);
    }

    public String toString() {
        return "ViewTranslationResponse { autofillId = " + this.mAutofillId + ", translationResponseValues = " + this.mTranslationResponseValues + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ViewTranslationResponse viewTranslationResponse = (ViewTranslationResponse) obj;
            if (Objects.equals(this.mAutofillId, viewTranslationResponse.mAutofillId) && Objects.equals(this.mTranslationResponseValues, viewTranslationResponse.mTranslationResponseValues)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mAutofillId) + 31) * 31) + Objects.hashCode(this.mTranslationResponseValues);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAutofillId, i);
        parcel.writeMap(this.mTranslationResponseValues);
    }

    ViewTranslationResponse(Parcel parcel) throws ClassNotFoundException, IOException {
        AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        parcel.readMap(linkedHashMap, TranslationResponseValue.class.getClassLoader());
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mTranslationResponseValues = linkedHashMap;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) linkedHashMap);
    }

    public static final class Builder extends BaseBuilder {
        private AutofillId mAutofillId;
        private long mBuilderFieldsSet = 0;
        private Map<String, TranslationResponseValue> mTranslationResponseValues;

        @Override // android.view.translation.ViewTranslationResponse.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setValue(String str, TranslationResponseValue translationResponseValue) {
            return super.setValue(str, translationResponseValue);
        }

        public Builder(AutofillId autofillId) {
            this.mAutofillId = autofillId;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        }

        @Override // android.view.translation.ViewTranslationResponse.BaseBuilder
        Builder setTranslationResponseValues(Map<String, TranslationResponseValue> map) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationResponseValues = map;
            return this;
        }

        public ViewTranslationResponse build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 4;
            this.mBuilderFieldsSet = j;
            if ((j & 2) == 0) {
                this.mTranslationResponseValues = ViewTranslationResponse.defaultTranslationResponseValues();
            }
            return new ViewTranslationResponse(this.mAutofillId, this.mTranslationResponseValues);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
