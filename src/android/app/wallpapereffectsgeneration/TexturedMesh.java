package android.app.wallpapereffectsgeneration;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class TexturedMesh implements Parcelable {
    public static final Parcelable.Creator<TexturedMesh> CREATOR = new Parcelable.Creator<TexturedMesh>() { // from class: android.app.wallpapereffectsgeneration.TexturedMesh.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TexturedMesh createFromParcel(Parcel parcel) {
            return new TexturedMesh(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TexturedMesh[] newArray(int i) {
            return new TexturedMesh[i];
        }
    };
    public static final int INDICES_LAYOUT_TRIANGLES = 1;
    public static final int INDICES_LAYOUT_UNDEFINED = 0;
    public static final int VERTICES_LAYOUT_POSITION3_UV2 = 1;
    public static final int VERTICES_LAYOUT_UNDEFINED = 0;
    private Bitmap mBitmap;
    private int[] mIndices;
    private int mIndicesLayoutType;
    private float[] mVertices;
    private int mVerticesLayoutType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicesLayoutType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerticesLayoutType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TexturedMesh(Parcel parcel) {
        this.mIndicesLayoutType = parcel.readInt();
        this.mVerticesLayoutType = parcel.readInt();
        this.mBitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
        Parcel parcelObtain = Parcel.obtain();
        try {
            byte[] blob = parcel.readBlob();
            parcelObtain.unmarshall(blob, 0, blob.length);
            parcelObtain.setDataPosition(0);
            this.mIndices = parcelObtain.createIntArray();
            this.mVertices = parcelObtain.createFloatArray();
        } finally {
            parcelObtain.recycle();
        }
    }

    private TexturedMesh(Bitmap bitmap, int[] iArr, float[] fArr, int i, int i2) {
        this.mBitmap = bitmap;
        this.mIndices = iArr;
        this.mVertices = fArr;
        this.mIndicesLayoutType = i;
        this.mVerticesLayoutType = i2;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public int[] getIndices() {
        return this.mIndices;
    }

    public float[] getVertices() {
        return this.mVertices;
    }

    public int getIndicesLayoutType() {
        return this.mIndicesLayoutType;
    }

    public int getVerticesLayoutType() {
        return this.mVerticesLayoutType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIndicesLayoutType);
        parcel.writeInt(this.mVerticesLayoutType);
        parcel.writeTypedObject(this.mBitmap, i);
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeIntArray(this.mIndices);
            parcelObtain.writeFloatArray(this.mVertices);
            parcel.writeBlob(parcelObtain.marshall());
        } finally {
            parcelObtain.recycle();
        }
    }

    @SystemApi
    public static final class Builder {
        private Bitmap mBitmap;
        private int[] mIndices;
        private int mIndicesLayoutType;
        private float[] mVertices;
        private int mVerticesLayouttype;

        @SystemApi
        public Builder(Bitmap bitmap) {
            this.mBitmap = bitmap;
        }

        public Builder setIndices(int[] iArr) {
            this.mIndices = iArr;
            return this;
        }

        public Builder setVertices(float[] fArr) {
            this.mVertices = fArr;
            return this;
        }

        public Builder setIndicesLayoutType(int i) {
            this.mIndicesLayoutType = i;
            return this;
        }

        public Builder setVerticesLayoutType(int i) {
            this.mVerticesLayouttype = i;
            return this;
        }

        public TexturedMesh build() {
            return new TexturedMesh(this.mBitmap, this.mIndices, this.mVertices, this.mIndicesLayoutType, this.mVerticesLayouttype);
        }
    }
}
