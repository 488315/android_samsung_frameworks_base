package android.service.autofill;

import android.annotation.IntRange;
import android.annotation.NonNull;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class SavedDatasetsInfo {
    public static final String TYPE_OTHER = "other";
    public static final String TYPE_PASSWORDS = "passwords";
    private final int mCount;
    private final String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Deprecated
    private void __metadata() {
    }

    public SavedDatasetsInfo(String str, int i) {
        this.mType = str;
        if (!Objects.equals(str, "other") && !Objects.equals(str, TYPE_PASSWORDS)) {
            throw new IllegalArgumentException("type was " + str + " but must be one of: TYPE_OTHER(other), TYPE_PASSWORDS(passwords)");
        }
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mCount = i;
        AnnotationValidations.validate((Class<IntRange>) IntRange.class, (IntRange) null, i, "from", 0L);
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SavedDatasetsInfo savedDatasetsInfo = (SavedDatasetsInfo) obj;
            if (Objects.equals(this.mType, savedDatasetsInfo.mType) && this.mCount == savedDatasetsInfo.mCount) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mType) + 31) * 31) + this.mCount;
    }
}
