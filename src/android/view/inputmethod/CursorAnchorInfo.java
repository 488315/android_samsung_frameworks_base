package android.view.inputmethod;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.inputmethod.SparseRectFArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CursorAnchorInfo implements Parcelable {
    public static final Parcelable.Creator<CursorAnchorInfo> CREATOR = new Parcelable.Creator<CursorAnchorInfo>() { // from class: android.view.inputmethod.CursorAnchorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CursorAnchorInfo createFromParcel(Parcel parcel) {
            return new CursorAnchorInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CursorAnchorInfo[] newArray(int i) {
            return new CursorAnchorInfo[i];
        }
    };
    public static final int FLAG_HAS_INVISIBLE_REGION = 2;
    public static final int FLAG_HAS_VISIBLE_REGION = 1;
    public static final int FLAG_IS_RTL = 4;
    private final SparseRectFArray mCharacterBoundsArray;
    private final CharSequence mComposingText;
    private final int mComposingTextStart;
    private final EditorBoundsInfo mEditorBoundsInfo;
    private final int mHashCode;
    private final float mInsertionMarkerBaseline;
    private final float mInsertionMarkerBottom;
    private final int mInsertionMarkerFlags;
    private final float mInsertionMarkerHorizontal;
    private final float mInsertionMarkerTop;
    private final float[] mMatrixValues;
    private final int mSelectionEnd;
    private final int mSelectionStart;
    private final TextAppearanceInfo mTextAppearanceInfo;
    private final float[] mVisibleLineBounds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CursorAnchorInfo(Parcel parcel) {
        this.mHashCode = parcel.readInt();
        this.mSelectionStart = parcel.readInt();
        this.mSelectionEnd = parcel.readInt();
        this.mComposingTextStart = parcel.readInt();
        this.mComposingText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mInsertionMarkerFlags = parcel.readInt();
        this.mInsertionMarkerHorizontal = parcel.readFloat();
        this.mInsertionMarkerTop = parcel.readFloat();
        this.mInsertionMarkerBaseline = parcel.readFloat();
        this.mInsertionMarkerBottom = parcel.readFloat();
        this.mCharacterBoundsArray = (SparseRectFArray) parcel.readTypedObject(SparseRectFArray.CREATOR);
        this.mEditorBoundsInfo = (EditorBoundsInfo) parcel.readTypedObject(EditorBoundsInfo.CREATOR);
        this.mMatrixValues = parcel.createFloatArray();
        this.mVisibleLineBounds = parcel.createFloatArray();
        this.mTextAppearanceInfo = (TextAppearanceInfo) parcel.readTypedObject(TextAppearanceInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHashCode);
        parcel.writeInt(this.mSelectionStart);
        parcel.writeInt(this.mSelectionEnd);
        parcel.writeInt(this.mComposingTextStart);
        TextUtils.writeToParcel(this.mComposingText, parcel, i);
        parcel.writeInt(this.mInsertionMarkerFlags);
        parcel.writeFloat(this.mInsertionMarkerHorizontal);
        parcel.writeFloat(this.mInsertionMarkerTop);
        parcel.writeFloat(this.mInsertionMarkerBaseline);
        parcel.writeFloat(this.mInsertionMarkerBottom);
        parcel.writeTypedObject(this.mCharacterBoundsArray, i);
        parcel.writeTypedObject(this.mEditorBoundsInfo, i);
        parcel.writeFloatArray(this.mMatrixValues);
        parcel.writeFloatArray(this.mVisibleLineBounds);
        parcel.writeTypedObject(this.mTextAppearanceInfo, i);
    }

    public int hashCode() {
        return this.mHashCode;
    }

    private static boolean areSameFloatImpl(float f, float f2) {
        return (Float.isNaN(f) && Float.isNaN(f2)) || f == f2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursorAnchorInfo)) {
            return false;
        }
        CursorAnchorInfo cursorAnchorInfo = (CursorAnchorInfo) obj;
        if (hashCode() != cursorAnchorInfo.hashCode() || this.mSelectionStart != cursorAnchorInfo.mSelectionStart || this.mSelectionEnd != cursorAnchorInfo.mSelectionEnd || this.mInsertionMarkerFlags != cursorAnchorInfo.mInsertionMarkerFlags || !areSameFloatImpl(this.mInsertionMarkerHorizontal, cursorAnchorInfo.mInsertionMarkerHorizontal) || !areSameFloatImpl(this.mInsertionMarkerTop, cursorAnchorInfo.mInsertionMarkerTop) || !areSameFloatImpl(this.mInsertionMarkerBaseline, cursorAnchorInfo.mInsertionMarkerBaseline) || !areSameFloatImpl(this.mInsertionMarkerBottom, cursorAnchorInfo.mInsertionMarkerBottom) || !Objects.equals(this.mCharacterBoundsArray, cursorAnchorInfo.mCharacterBoundsArray) || !Objects.equals(this.mEditorBoundsInfo, cursorAnchorInfo.mEditorBoundsInfo) || !Arrays.equals(this.mVisibleLineBounds, cursorAnchorInfo.mVisibleLineBounds) || this.mComposingTextStart != cursorAnchorInfo.mComposingTextStart || !Objects.equals(this.mComposingText, cursorAnchorInfo.mComposingText) || this.mMatrixValues.length != cursorAnchorInfo.mMatrixValues.length) {
            return false;
        }
        int i = 0;
        while (true) {
            float[] fArr = this.mMatrixValues;
            if (i >= fArr.length) {
                return Objects.equals(this.mTextAppearanceInfo, cursorAnchorInfo.mTextAppearanceInfo);
            }
            if (fArr[i] != cursorAnchorInfo.mMatrixValues[i]) {
                return false;
            }
            i++;
        }
    }

    public String toString() {
        return "CursorAnchorInfo{mHashCode=" + this.mHashCode + " mSelection=" + this.mSelectionStart + "," + this.mSelectionEnd + " mComposingTextStart=" + this.mComposingTextStart + " mComposingText=" + ((Object) this.mComposingText) + " mInsertionMarkerFlags=" + this.mInsertionMarkerFlags + " mInsertionMarkerHorizontal=" + this.mInsertionMarkerHorizontal + " mInsertionMarkerTop=" + this.mInsertionMarkerTop + " mInsertionMarkerBaseline=" + this.mInsertionMarkerBaseline + " mInsertionMarkerBottom=" + this.mInsertionMarkerBottom + " mCharacterBoundsArray=" + this.mCharacterBoundsArray + " mEditorBoundsInfo=" + this.mEditorBoundsInfo + " mVisibleLineBounds=" + getVisibleLineBounds() + " mMatrix=" + Arrays.toString(this.mMatrixValues) + " mTextAppearanceInfo=" + this.mTextAppearanceInfo + "}";
    }

    public static final class Builder {
        private static final int LINE_BOUNDS_INITIAL_SIZE = 4;
        private int mSelectionStart = -1;
        private int mSelectionEnd = -1;
        private int mComposingTextStart = -1;
        private CharSequence mComposingText = null;
        private float mInsertionMarkerHorizontal = Float.NaN;
        private float mInsertionMarkerTop = Float.NaN;
        private float mInsertionMarkerBaseline = Float.NaN;
        private float mInsertionMarkerBottom = Float.NaN;
        private int mInsertionMarkerFlags = 0;
        private SparseRectFArray.SparseRectFArrayBuilder mCharacterBoundsArrayBuilder = null;
        private EditorBoundsInfo mEditorBoundsInfo = null;
        private float[] mMatrixValues = null;
        private boolean mMatrixInitialized = false;
        private float[] mVisibleLineBounds = new float[16];
        private int mVisibleLineBoundsCount = 0;
        private TextAppearanceInfo mTextAppearanceInfo = null;

        public Builder setSelectionRange(int i, int i2) {
            this.mSelectionStart = i;
            this.mSelectionEnd = i2;
            return this;
        }

        public Builder setComposingText(int i, CharSequence charSequence) {
            this.mComposingTextStart = i;
            if (charSequence == null) {
                this.mComposingText = null;
                return this;
            }
            this.mComposingText = new SpannedString(charSequence);
            return this;
        }

        public Builder setInsertionMarkerLocation(float f, float f2, float f3, float f4, int i) {
            this.mInsertionMarkerHorizontal = f;
            this.mInsertionMarkerTop = f2;
            this.mInsertionMarkerBaseline = f3;
            this.mInsertionMarkerBottom = f4;
            this.mInsertionMarkerFlags = i;
            return this;
        }

        public Builder addCharacterBounds(int i, float f, float f2, float f3, float f4, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("index must not be a negative integer.");
            }
            if (this.mCharacterBoundsArrayBuilder == null) {
                this.mCharacterBoundsArrayBuilder = new SparseRectFArray.SparseRectFArrayBuilder();
            }
            this.mCharacterBoundsArrayBuilder.append(i, f, f2, f3, f4, i2);
            return this;
        }

        public Builder setEditorBoundsInfo(EditorBoundsInfo editorBoundsInfo) {
            this.mEditorBoundsInfo = editorBoundsInfo;
            return this;
        }

        public Builder setMatrix(Matrix matrix) {
            if (this.mMatrixValues == null) {
                this.mMatrixValues = new float[9];
            }
            if (matrix == null) {
                matrix = Matrix.IDENTITY_MATRIX;
            }
            matrix.getValues(this.mMatrixValues);
            this.mMatrixInitialized = true;
            return this;
        }

        public Builder setTextAppearanceInfo(TextAppearanceInfo textAppearanceInfo) {
            this.mTextAppearanceInfo = textAppearanceInfo;
            return this;
        }

        public Builder addVisibleLineBounds(float f, float f2, float f3, float f4) {
            float[] fArr = this.mVisibleLineBounds;
            int length = fArr.length;
            int i = this.mVisibleLineBoundsCount;
            if (length <= i + 4) {
                this.mVisibleLineBounds = Arrays.copyOf(fArr, (i + 4) * 2);
            }
            float[] fArr2 = this.mVisibleLineBounds;
            int i2 = this.mVisibleLineBoundsCount;
            int i3 = i2 + 1;
            this.mVisibleLineBoundsCount = i3;
            fArr2[i2] = f;
            int i4 = i2 + 2;
            this.mVisibleLineBoundsCount = i4;
            fArr2[i3] = f2;
            int i5 = i2 + 3;
            this.mVisibleLineBoundsCount = i5;
            fArr2[i4] = f3;
            this.mVisibleLineBoundsCount = i2 + 4;
            fArr2[i5] = f4;
            return this;
        }

        public Builder clearVisibleLineBounds() {
            this.mVisibleLineBoundsCount = 0;
            return this;
        }

        public CursorAnchorInfo build() {
            if (!this.mMatrixInitialized) {
                SparseRectFArray.SparseRectFArrayBuilder sparseRectFArrayBuilder = this.mCharacterBoundsArrayBuilder;
                boolean z = (sparseRectFArrayBuilder == null || sparseRectFArrayBuilder.isEmpty()) ? false : true;
                boolean z2 = this.mVisibleLineBounds != null && this.mVisibleLineBoundsCount > 0;
                if (z || z2 || !Float.isNaN(this.mInsertionMarkerHorizontal) || !Float.isNaN(this.mInsertionMarkerTop) || !Float.isNaN(this.mInsertionMarkerBaseline) || !Float.isNaN(this.mInsertionMarkerBottom)) {
                    throw new IllegalArgumentException("Coordinate transformation matrix is required when positional parameters are specified.");
                }
            }
            return CursorAnchorInfo.create(this);
        }

        public void reset() {
            this.mSelectionStart = -1;
            this.mSelectionEnd = -1;
            this.mComposingTextStart = -1;
            this.mComposingText = null;
            this.mInsertionMarkerFlags = 0;
            this.mInsertionMarkerHorizontal = Float.NaN;
            this.mInsertionMarkerTop = Float.NaN;
            this.mInsertionMarkerBaseline = Float.NaN;
            this.mInsertionMarkerBottom = Float.NaN;
            this.mMatrixInitialized = false;
            SparseRectFArray.SparseRectFArrayBuilder sparseRectFArrayBuilder = this.mCharacterBoundsArrayBuilder;
            if (sparseRectFArrayBuilder != null) {
                sparseRectFArrayBuilder.reset();
            }
            this.mEditorBoundsInfo = null;
            clearVisibleLineBounds();
            this.mTextAppearanceInfo = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CursorAnchorInfo create(Builder builder) {
        SparseRectFArray sparseRectFArrayBuild = builder.mCharacterBoundsArrayBuilder != null ? builder.mCharacterBoundsArrayBuilder.build() : null;
        float[] fArr = new float[9];
        if (builder.mMatrixInitialized) {
            System.arraycopy(builder.mMatrixValues, 0, fArr, 0, 9);
        } else {
            Matrix.IDENTITY_MATRIX.getValues(fArr);
        }
        return new CursorAnchorInfo(builder.mSelectionStart, builder.mSelectionEnd, builder.mComposingTextStart, builder.mComposingText, builder.mInsertionMarkerFlags, builder.mInsertionMarkerHorizontal, builder.mInsertionMarkerTop, builder.mInsertionMarkerBaseline, builder.mInsertionMarkerBottom, sparseRectFArrayBuild, builder.mEditorBoundsInfo, fArr, Arrays.copyOf(builder.mVisibleLineBounds, builder.mVisibleLineBoundsCount), builder.mTextAppearanceInfo);
    }

    private CursorAnchorInfo(int i, int i2, int i3, CharSequence charSequence, int i4, float f, float f2, float f3, float f4, SparseRectFArray sparseRectFArray, EditorBoundsInfo editorBoundsInfo, float[] fArr, float[] fArr2, TextAppearanceInfo textAppearanceInfo) {
        this.mSelectionStart = i;
        this.mSelectionEnd = i2;
        this.mComposingTextStart = i3;
        this.mComposingText = charSequence;
        this.mInsertionMarkerFlags = i4;
        this.mInsertionMarkerHorizontal = f;
        this.mInsertionMarkerTop = f2;
        this.mInsertionMarkerBaseline = f3;
        this.mInsertionMarkerBottom = f4;
        this.mCharacterBoundsArray = sparseRectFArray;
        this.mEditorBoundsInfo = editorBoundsInfo;
        this.mMatrixValues = fArr;
        this.mVisibleLineBounds = fArr2;
        this.mTextAppearanceInfo = textAppearanceInfo;
        this.mHashCode = (Objects.hashCode(charSequence) * 31) + Arrays.hashCode(fArr);
    }

    public static CursorAnchorInfo createForAdditionalParentMatrix(CursorAnchorInfo cursorAnchorInfo, Matrix matrix) {
        return new CursorAnchorInfo(cursorAnchorInfo.mSelectionStart, cursorAnchorInfo.mSelectionEnd, cursorAnchorInfo.mComposingTextStart, cursorAnchorInfo.mComposingText, cursorAnchorInfo.mInsertionMarkerFlags, cursorAnchorInfo.mInsertionMarkerHorizontal, cursorAnchorInfo.mInsertionMarkerTop, cursorAnchorInfo.mInsertionMarkerBaseline, cursorAnchorInfo.mInsertionMarkerBottom, cursorAnchorInfo.mCharacterBoundsArray, cursorAnchorInfo.mEditorBoundsInfo, computeMatrixValues(matrix, cursorAnchorInfo), cursorAnchorInfo.mVisibleLineBounds, cursorAnchorInfo.mTextAppearanceInfo);
    }

    private static float[] computeMatrixValues(Matrix matrix, CursorAnchorInfo cursorAnchorInfo) {
        if (matrix.isIdentity()) {
            return cursorAnchorInfo.mMatrixValues;
        }
        Matrix matrix2 = new Matrix();
        matrix2.setValues(cursorAnchorInfo.mMatrixValues);
        matrix2.postConcat(matrix);
        float[] fArr = new float[9];
        matrix2.getValues(fArr);
        return fArr;
    }

    public int getSelectionStart() {
        return this.mSelectionStart;
    }

    public int getSelectionEnd() {
        return this.mSelectionEnd;
    }

    public int getComposingTextStart() {
        return this.mComposingTextStart;
    }

    public CharSequence getComposingText() {
        return this.mComposingText;
    }

    public int getInsertionMarkerFlags() {
        return this.mInsertionMarkerFlags;
    }

    public float getInsertionMarkerHorizontal() {
        return this.mInsertionMarkerHorizontal;
    }

    public float getInsertionMarkerTop() {
        return this.mInsertionMarkerTop;
    }

    public float getInsertionMarkerBaseline() {
        return this.mInsertionMarkerBaseline;
    }

    public float getInsertionMarkerBottom() {
        return this.mInsertionMarkerBottom;
    }

    public RectF getCharacterBounds(int i) {
        SparseRectFArray sparseRectFArray = this.mCharacterBoundsArray;
        if (sparseRectFArray == null) {
            return null;
        }
        return sparseRectFArray.get(i);
    }

    public int getCharacterBoundsFlags(int i) {
        SparseRectFArray sparseRectFArray = this.mCharacterBoundsArray;
        if (sparseRectFArray == null) {
            return 0;
        }
        return sparseRectFArray.getFlags(i, 0);
    }

    public List<RectF> getVisibleLineBounds() {
        if (this.mVisibleLineBounds == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(this.mVisibleLineBounds.length / 4);
        int i = 0;
        while (i < this.mVisibleLineBounds.length) {
            float[] fArr = this.mVisibleLineBounds;
            float f = fArr[i];
            float f2 = fArr[i + 1];
            int i2 = i + 3;
            float f3 = fArr[i + 2];
            i += 4;
            arrayList.add(new RectF(f, f2, f3, fArr[i2]));
        }
        return arrayList;
    }

    public EditorBoundsInfo getEditorBoundsInfo() {
        return this.mEditorBoundsInfo;
    }

    public TextAppearanceInfo getTextAppearanceInfo() {
        return this.mTextAppearanceInfo;
    }

    public Matrix getMatrix() {
        Matrix matrix = new Matrix();
        matrix.setValues(this.mMatrixValues);
        return matrix;
    }
}
