package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.service.autofill.Dataset;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class FillResponse {
    private Bundle mClientState;
    private FillWindow mFillWindow;
    private List<Dataset> mInlineSuggestions;

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle defaultClientState() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FillWindow defaultFillWindow() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<Dataset> defaultInlineSuggestions() {
        return null;
    }

    static abstract class BaseBuilder {
        abstract Builder addInlineSuggestion(Dataset dataset);

        BaseBuilder() {
        }
    }

    FillResponse(FillWindow fillWindow, List<Dataset> list, Bundle bundle) {
        this.mFillWindow = fillWindow;
        this.mInlineSuggestions = list;
        this.mClientState = bundle;
    }

    public FillWindow getFillWindow() {
        return this.mFillWindow;
    }

    public List<Dataset> getInlineSuggestions() {
        return this.mInlineSuggestions;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private Bundle mClientState;
        private FillWindow mFillWindow;
        private List<Dataset> mInlineSuggestions;

        public Builder setFillWindow(FillWindow fillWindow) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mFillWindow = fillWindow;
            return this;
        }

        public Builder setInlineSuggestions(List<Dataset> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlineSuggestions = list;
            return this;
        }

        @Override // android.service.autofill.augmented.FillResponse.BaseBuilder
        Builder addInlineSuggestion(Dataset dataset) {
            if (this.mInlineSuggestions == null) {
                setInlineSuggestions(new ArrayList());
            }
            this.mInlineSuggestions.add(dataset);
            return this;
        }

        public Builder setClientState(Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mClientState = bundle;
            return this;
        }

        public FillResponse build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 8;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mFillWindow = FillResponse.defaultFillWindow();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mInlineSuggestions = FillResponse.defaultInlineSuggestions();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mClientState = FillResponse.defaultClientState();
            }
            return new FillResponse(this.mFillWindow, this.mInlineSuggestions, this.mClientState);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
