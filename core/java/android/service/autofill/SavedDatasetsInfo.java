package android.service.autofill;

import android.annotation.IntRange;
import android.annotation.NonNull;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class SavedDatasetsInfo {
    public static final String TYPE_OTHER = "other";
    public static final String TYPE_PASSWORDS = "passwords";
    private final int mCount;
    private final String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

    public SavedDatasetsInfo(String type, int count) {
        this.mType = type;
        if (!Objects.equals(this.mType, "other") && !Objects.equals(this.mType, TYPE_PASSWORDS)) {
            throw new IllegalArgumentException(
                    "type was "
                            + this.mType
                            + " but must be one of: TYPE_OTHER(other), TYPE_PASSWORDS("
                            + TYPE_PASSWORDS
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mType);
        this.mCount = count;
        AnnotationValidations.validate(
                (Class<IntRange>) IntRange.class, (IntRange) null, this.mCount, "from", 0L);
    }

    public String getType() {
        return this.mType;
    }

    public int getCount() {
        return this.mCount;
    }

    public String toString() {
        return "SavedDatasetsInfo { type = " + this.mType + ", count = " + this.mCount + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SavedDatasetsInfo that = (SavedDatasetsInfo) o;
        if (Objects.equals(this.mType, that.mType) && this.mCount == that.mCount) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mType);
        return (_hash * 31) + this.mCount;
    }

    @Deprecated
    private void __metadata() {}
}
