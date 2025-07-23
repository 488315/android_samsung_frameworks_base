package android.service.autofill;

import android.annotation.NonNull;
import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.inline.InlinePresentationSpec;
import com.android.internal.util.AnnotationValidations;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class InlinePresentation implements Parcelable {
    public static final Parcelable.Creator<InlinePresentation> CREATOR = new Parcelable.Creator<InlinePresentation>() { // from class: android.service.autofill.InlinePresentation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlinePresentation[] newArray(int i) {
            return new InlinePresentation[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlinePresentation createFromParcel(Parcel parcel) {
            return new InlinePresentation(parcel);
        }
    };
    private final InlinePresentationSpec mInlinePresentationSpec;
    private final boolean mPinned;
    private final Slice mSlice;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String[] getAutofillHints() {
        List<String> hints = this.mSlice.getHints();
        return (String[]) hints.toArray(new String[hints.size()]);
    }

    public static InlinePresentation createTooltipPresentation(Slice slice, InlinePresentationSpec inlinePresentationSpec) {
        return new InlinePresentation(slice, inlinePresentationSpec, false);
    }

    public InlinePresentation(Slice slice, InlinePresentationSpec inlinePresentationSpec, boolean z) {
        this.mSlice = slice;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) slice);
        this.mInlinePresentationSpec = inlinePresentationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) inlinePresentationSpec);
        this.mPinned = z;
    }

    public Slice getSlice() {
        return this.mSlice;
    }

    public InlinePresentationSpec getInlinePresentationSpec() {
        return this.mInlinePresentationSpec;
    }

    public boolean isPinned() {
        return this.mPinned;
    }

    public String toString() {
        return "InlinePresentation { slice = " + this.mSlice + ", inlinePresentationSpec = " + this.mInlinePresentationSpec + ", pinned = " + this.mPinned + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InlinePresentation inlinePresentation = (InlinePresentation) obj;
            if (Objects.equals(this.mSlice, inlinePresentation.mSlice) && Objects.equals(this.mInlinePresentationSpec, inlinePresentation.mInlinePresentationSpec) && this.mPinned == inlinePresentation.mPinned) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mSlice) + 31) * 31) + Objects.hashCode(this.mInlinePresentationSpec)) * 31) + Boolean.hashCode(this.mPinned);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mPinned ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeTypedObject(this.mInlinePresentationSpec, i);
    }

    InlinePresentation(Parcel parcel) {
        boolean z = (parcel.readByte() & 4) != 0;
        Slice slice = (Slice) parcel.readTypedObject(Slice.CREATOR);
        InlinePresentationSpec inlinePresentationSpec = (InlinePresentationSpec) parcel.readTypedObject(InlinePresentationSpec.CREATOR);
        this.mSlice = slice;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) slice);
        this.mInlinePresentationSpec = inlinePresentationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) inlinePresentationSpec);
        this.mPinned = z;
    }
}
