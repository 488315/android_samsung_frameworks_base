package android.view.autofill;

import android.annotation.SuppressLint;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;

public final class VirtualViewFillInfo {
    private String[] mAutofillHints;

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] defaultAutofillHints() {
        return null;
    }

    VirtualViewFillInfo(String[] autofillHints) {
        this.mAutofillHints = autofillHints;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) SuppressLint.class,
                (Annotation) null,
                this.mAutofillHints,
                "value",
                "NullableCollection");
    }

    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public static final class Builder {
        private String[] mAutofillHints;
        private long mBuilderFieldsSet = 0;

        public Builder setAutofillHints(String... value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAutofillHints = value;
            return this;
        }

        public VirtualViewFillInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mAutofillHints = VirtualViewFillInfo.defaultAutofillHints();
            }
            VirtualViewFillInfo o = new VirtualViewFillInfo(this.mAutofillHints);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
