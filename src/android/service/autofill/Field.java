package android.service.autofill;

import android.service.autofill.Dataset;
import android.view.autofill.AutofillValue;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class Field {
    private Dataset.DatasetFieldFilter mFilter;
    private Presentations mPresentations;
    private AutofillValue mValue;

    Field(AutofillValue autofillValue, Dataset.DatasetFieldFilter datasetFieldFilter, Presentations presentations) {
        this.mValue = autofillValue;
        this.mFilter = datasetFieldFilter;
        this.mPresentations = presentations;
    }

    public AutofillValue getValue() {
        return this.mValue;
    }

    public Dataset.DatasetFieldFilter getDatasetFieldFilter() {
        return this.mFilter;
    }

    public Pattern getFilter() {
        Dataset.DatasetFieldFilter datasetFieldFilter = this.mFilter;
        if (datasetFieldFilter == null) {
            return null;
        }
        return datasetFieldFilter.pattern;
    }

    public Presentations getPresentations() {
        return this.mPresentations;
    }

    public static final class Builder {
        private AutofillValue mValue = null;
        private Dataset.DatasetFieldFilter mFilter = null;
        private Presentations mPresentations = null;
        private boolean mDestroyed = false;

        public Builder setValue(AutofillValue autofillValue) {
            checkNotUsed();
            this.mValue = autofillValue;
            return this;
        }

        public Builder setFilter(Pattern pattern) {
            checkNotUsed();
            this.mFilter = new Dataset.DatasetFieldFilter(pattern);
            return this;
        }

        public Builder setPresentations(Presentations presentations) {
            checkNotUsed();
            this.mPresentations = presentations;
            return this;
        }

        public Field build() {
            checkNotUsed();
            this.mDestroyed = true;
            return new Field(this.mValue, this.mFilter, this.mPresentations);
        }

        private void checkNotUsed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
