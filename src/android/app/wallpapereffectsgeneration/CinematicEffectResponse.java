package android.app.wallpapereffectsgeneration;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class CinematicEffectResponse implements Parcelable {
    public static final int CINEMATIC_EFFECT_STATUS_ANIMATION_FAILURE = 10;
    public static final int CINEMATIC_EFFECT_STATUS_CONTENT_TARGET_ERROR = 8;
    public static final int CINEMATIC_EFFECT_STATUS_CONTENT_TOO_FLAT = 9;
    public static final int CINEMATIC_EFFECT_STATUS_CONTENT_UNSUPPORTED = 7;
    public static final int CINEMATIC_EFFECT_STATUS_ERROR = 0;
    public static final int CINEMATIC_EFFECT_STATUS_FEATURE_DISABLED = 5;
    public static final int CINEMATIC_EFFECT_STATUS_IMAGE_FORMAT_NOT_SUITABLE = 6;
    public static final int CINEMATIC_EFFECT_STATUS_NOT_READY = 2;
    public static final int CINEMATIC_EFFECT_STATUS_OK = 1;
    public static final int CINEMATIC_EFFECT_STATUS_PENDING = 3;
    public static final int CINEMATIC_EFFECT_STATUS_TOO_MANY_REQUESTS = 4;
    public static final Parcelable.Creator<CinematicEffectResponse> CREATOR = new Parcelable.Creator<CinematicEffectResponse>() { // from class: android.app.wallpapereffectsgeneration.CinematicEffectResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CinematicEffectResponse createFromParcel(Parcel parcel) {
            return new CinematicEffectResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CinematicEffectResponse[] newArray(int i) {
            return new CinematicEffectResponse[i];
        }
    };
    public static final int IMAGE_CONTENT_TYPE_LANDSCAPE = 2;
    public static final int IMAGE_CONTENT_TYPE_OTHER = 3;
    public static final int IMAGE_CONTENT_TYPE_PEOPLE_PORTRAIT = 1;
    public static final int IMAGE_CONTENT_TYPE_UNKNOWN = 0;
    private CameraAttributes mEndKeyFrame;
    private int mImageContentType;
    private CameraAttributes mStartKeyFrame;
    private int mStatusCode;
    private String mTaskId;
    private List<TexturedMesh> mTexturedMeshes;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CinematicEffectStatusCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageContentType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CinematicEffectResponse(Parcel parcel) {
        this.mStatusCode = parcel.readInt();
        this.mTaskId = parcel.readString();
        this.mImageContentType = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.mTexturedMeshes = arrayList;
        parcel.readTypedList(arrayList, TexturedMesh.CREATOR);
        this.mStartKeyFrame = (CameraAttributes) parcel.readTypedObject(CameraAttributes.CREATOR);
        this.mEndKeyFrame = (CameraAttributes) parcel.readTypedObject(CameraAttributes.CREATOR);
    }

    private CinematicEffectResponse(int i, String str, int i2, List<TexturedMesh> list, CameraAttributes cameraAttributes, CameraAttributes cameraAttributes2) {
        this.mStatusCode = i;
        this.mTaskId = str;
        this.mImageContentType = i2;
        this.mStartKeyFrame = cameraAttributes;
        this.mEndKeyFrame = cameraAttributes2;
        this.mTexturedMeshes = list;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public String getTaskId() {
        return this.mTaskId;
    }

    public int getImageContentType() {
        return this.mImageContentType;
    }

    public List<TexturedMesh> getTexturedMeshes() {
        return this.mTexturedMeshes;
    }

    public CameraAttributes getStartKeyFrame() {
        return this.mStartKeyFrame;
    }

    public CameraAttributes getEndKeyFrame() {
        return this.mEndKeyFrame;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatusCode);
        parcel.writeString(this.mTaskId);
        parcel.writeInt(this.mImageContentType);
        parcel.writeTypedList(this.mTexturedMeshes, i);
        parcel.writeTypedObject(this.mStartKeyFrame, i);
        parcel.writeTypedObject(this.mEndKeyFrame, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mTaskId.equals(((CinematicEffectResponse) obj).mTaskId);
    }

    public int hashCode() {
        return Objects.hash(this.mTaskId);
    }

    @SystemApi
    public static final class Builder {
        private CameraAttributes mEndKeyFrame;
        private int mImageContentType;
        private CameraAttributes mStartKeyFrame;
        private int mStatusCode;
        private String mTaskId;
        private List<TexturedMesh> mTexturedMeshes;

        @SystemApi
        public Builder(int i, String str) {
            this.mStatusCode = i;
            this.mTaskId = str;
        }

        public Builder setImageContentType(int i) {
            this.mImageContentType = i;
            return this;
        }

        public Builder setTexturedMeshes(List<TexturedMesh> list) {
            this.mTexturedMeshes = list;
            return this;
        }

        public Builder setStartKeyFrame(CameraAttributes cameraAttributes) {
            this.mStartKeyFrame = cameraAttributes;
            return this;
        }

        public Builder setEndKeyFrame(CameraAttributes cameraAttributes) {
            this.mEndKeyFrame = cameraAttributes;
            return this;
        }

        public CinematicEffectResponse build() {
            if (this.mTexturedMeshes == null) {
                this.mTexturedMeshes = new ArrayList(0);
            }
            return new CinematicEffectResponse(this.mStatusCode, this.mTaskId, this.mImageContentType, this.mTexturedMeshes, this.mStartKeyFrame, this.mEndKeyFrame);
        }
    }
}
