package android.view.inputmethod;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class InlineSuggestionsResponse implements Parcelable {
    public static final Parcelable.Creator<InlineSuggestionsResponse> CREATOR = new Parcelable.Creator<InlineSuggestionsResponse>() { // from class: android.view.inputmethod.InlineSuggestionsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionsResponse[] newArray(int i) {
            return new InlineSuggestionsResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionsResponse createFromParcel(Parcel parcel) {
            return new InlineSuggestionsResponse(parcel);
        }
    };
    private final List<InlineSuggestion> mInlineSuggestions;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static InlineSuggestionsResponse newInlineSuggestionsResponse(List<InlineSuggestion> list) {
        return new InlineSuggestionsResponse(list);
    }

    public InlineSuggestionsResponse(List<InlineSuggestion> list) {
        this.mInlineSuggestions = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
    }

    public List<InlineSuggestion> getInlineSuggestions() {
        return this.mInlineSuggestions;
    }

    public String toString() {
        return "InlineSuggestionsResponse { inlineSuggestions = " + this.mInlineSuggestions + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mInlineSuggestions, ((InlineSuggestionsResponse) obj).mInlineSuggestions);
    }

    public int hashCode() {
        return 31 + Objects.hashCode(this.mInlineSuggestions);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableList(this.mInlineSuggestions, i);
    }

    InlineSuggestionsResponse(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, InlineSuggestion.class.getClassLoader(), InlineSuggestion.class);
        this.mInlineSuggestions = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
    }
}
