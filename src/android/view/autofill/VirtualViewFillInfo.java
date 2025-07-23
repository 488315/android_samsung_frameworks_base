package android.view.autofill;

import android.annotation.SuppressLint;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

/* loaded from: classes4.dex */
public final class VirtualViewFillInfo {
    private String[] mAutofillHints;

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] defaultAutofillHints() {
        return null;
    }

    VirtualViewFillInfo(String[] strArr) {
        this.mAutofillHints = strArr;
        AnnotationValidations.validate((Class<? extends Annotation>) SuppressLint.class, (Annotation) null, strArr, "value", "NullableCollection");
    }

    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public static final class Builder {
        private String[] mAutofillHints;
        private long mBuilderFieldsSet = 0;

        public Builder setAutofillHints(String... strArr) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAutofillHints = strArr;
            return this;
        }

        public VirtualViewFillInfo build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 2;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mAutofillHints = VirtualViewFillInfo.defaultAutofillHints();
            }
            return new VirtualViewFillInfo(this.mAutofillHints);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
