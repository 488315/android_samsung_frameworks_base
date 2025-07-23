package com.android.internal.inputmethod;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.autofill.AutofillId;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class InlineSuggestionsRequestInfo implements Parcelable {
    public static final Parcelable.Creator<InlineSuggestionsRequestInfo> CREATOR = new Parcelable.Creator<InlineSuggestionsRequestInfo>() { // from class: com.android.internal.inputmethod.InlineSuggestionsRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionsRequestInfo[] newArray(int i) {
            return new InlineSuggestionsRequestInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionsRequestInfo createFromParcel(Parcel parcel) {
            return new InlineSuggestionsRequestInfo(parcel);
        }
    };
    private final AutofillId mAutofillId;
    private final ComponentName mComponentName;
    private final Bundle mUiExtras;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InlineSuggestionsRequestInfo(ComponentName componentName, AutofillId autofillId, Bundle bundle) {
        this.mComponentName = componentName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) componentName);
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mUiExtras = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    public Bundle getUiExtras() {
        return this.mUiExtras;
    }

    public String toString() {
        return "InlineSuggestionsRequestInfo { componentName = " + this.mComponentName + ", autofillId = " + this.mAutofillId + ", uiExtras = " + this.mUiExtras + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo = (InlineSuggestionsRequestInfo) obj;
            if (Objects.equals(this.mComponentName, inlineSuggestionsRequestInfo.mComponentName) && Objects.equals(this.mAutofillId, inlineSuggestionsRequestInfo.mAutofillId) && Objects.equals(this.mUiExtras, inlineSuggestionsRequestInfo.mUiExtras)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mComponentName) + 31) * 31) + Objects.hashCode(this.mAutofillId)) * 31) + Objects.hashCode(this.mUiExtras);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mComponentName, i);
        parcel.writeTypedObject(this.mAutofillId, i);
        parcel.writeBundle(this.mUiExtras);
    }

    InlineSuggestionsRequestInfo(Parcel parcel) {
        ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
        Bundle readBundle = parcel.readBundle();
        this.mComponentName = componentName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) componentName);
        this.mAutofillId = autofillId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillId);
        this.mUiExtras = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
    }
}
