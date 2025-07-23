package android.view.contentcapture;

import android.content.LocusId;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DebugUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ContentCaptureCondition implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureCondition> CREATOR = new Parcelable.Creator<ContentCaptureCondition>() { // from class: android.view.contentcapture.ContentCaptureCondition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureCondition createFromParcel(Parcel parcel) {
            return new ContentCaptureCondition((LocusId) parcel.readParcelable(null, LocusId.class), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureCondition[] newArray(int i) {
            return new ContentCaptureCondition[i];
        }
    };
    public static final int FLAG_IS_REGEX = 2;
    private final int mFlags;
    private final LocusId mLocusId;

    @Retention(RetentionPolicy.SOURCE)
    @interface Flags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentCaptureCondition(LocusId locusId, int i) {
        this.mLocusId = (LocusId) Objects.requireNonNull(locusId);
        this.mFlags = i;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int hashCode() {
        int i = (this.mFlags + 31) * 31;
        LocusId locusId = this.mLocusId;
        return i + (locusId == null ? 0 : locusId.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ContentCaptureCondition contentCaptureCondition = (ContentCaptureCondition) obj;
        if (this.mFlags != contentCaptureCondition.mFlags) {
            return false;
        }
        LocusId locusId = this.mLocusId;
        if (locusId == null) {
            if (contentCaptureCondition.mLocusId != null) {
                return false;
            }
        } else if (!locusId.equals(contentCaptureCondition.mLocusId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.mLocusId.toString());
        if (this.mFlags != 0) {
            sb.append(" (");
            sb.append(DebugUtils.flagsToString(ContentCaptureCondition.class, "FLAG_", this.mFlags));
            sb.append(')');
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mLocusId, i);
        parcel.writeInt(this.mFlags);
    }
}
