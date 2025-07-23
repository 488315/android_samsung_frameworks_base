package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class EditorBoundsInfo implements Parcelable {
    public static final Parcelable.Creator<EditorBoundsInfo> CREATOR = new Parcelable.Creator<EditorBoundsInfo>() { // from class: android.view.inputmethod.EditorBoundsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditorBoundsInfo createFromParcel(Parcel parcel) {
            return new EditorBoundsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditorBoundsInfo[] newArray(int i) {
            return new EditorBoundsInfo[i];
        }
    };
    private final RectF mEditorBounds;
    private final RectF mHandwritingBounds;
    private final int mHashCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private EditorBoundsInfo(Parcel parcel) {
        this.mHashCode = parcel.readInt();
        this.mEditorBounds = (RectF) parcel.readTypedObject(RectF.CREATOR);
        this.mHandwritingBounds = (RectF) parcel.readTypedObject(RectF.CREATOR);
    }

    public RectF getEditorBounds() {
        return this.mEditorBounds;
    }

    public RectF getHandwritingBounds() {
        return this.mHandwritingBounds;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    public String toString() {
        return "EditorBoundsInfo{mEditorBounds=" + this.mEditorBounds + " mHandwritingBounds=" + this.mHandwritingBounds + "}";
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof EditorBoundsInfo)) {
            EditorBoundsInfo editorBoundsInfo = (EditorBoundsInfo) obj;
            if (Objects.equals(editorBoundsInfo.mEditorBounds, this.mEditorBounds) && Objects.equals(editorBoundsInfo.mHandwritingBounds, this.mHandwritingBounds)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHashCode);
        parcel.writeTypedObject(this.mEditorBounds, i);
        parcel.writeTypedObject(this.mHandwritingBounds, i);
    }

    public static final class Builder {
        private RectF mEditorBounds = null;
        private RectF mHandwritingBounds = null;

        public Builder setEditorBounds(RectF rectF) {
            this.mEditorBounds = rectF;
            return this;
        }

        public Builder setHandwritingBounds(RectF rectF) {
            this.mHandwritingBounds = rectF;
            return this;
        }

        public EditorBoundsInfo build() {
            return new EditorBoundsInfo(this);
        }
    }

    private EditorBoundsInfo(Builder builder) {
        RectF rectF = builder.mEditorBounds;
        this.mEditorBounds = rectF;
        RectF rectF2 = builder.mHandwritingBounds;
        this.mHandwritingBounds = rectF2;
        this.mHashCode = (Objects.hashCode(rectF) * 31) + Objects.hashCode(rectF2);
    }
}
