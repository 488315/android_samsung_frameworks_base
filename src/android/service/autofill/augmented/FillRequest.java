package android.service.autofill.augmented;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.util.AnnotationValidations;

@SystemApi
/* loaded from: classes3.dex */
public final class FillRequest {
    private final InlineSuggestionsRequest mInlineSuggestionsRequest;
    private final AugmentedAutofillService.AutofillProxy mProxy;

    @Deprecated
    private void __metadata() {
    }

    public int getTaskId() {
        return this.mProxy.mTaskId;
    }

    public ComponentName getActivityComponent() {
        return this.mProxy.mComponentName;
    }

    public AutofillId getFocusedId() {
        return this.mProxy.getFocusedId();
    }

    public AutofillValue getFocusedValue() {
        return this.mProxy.getFocusedValue();
    }

    public AssistStructure.ViewNode getFocusedViewNode() {
        return this.mProxy.getFocusedViewNode();
    }

    public PresentationParams getPresentationParams() {
        return this.mProxy.getSmartSuggestionParams();
    }

    String proxyToString() {
        return "FillRequest[act=" + getActivityComponent().flattenToShortString() + ", id=" + this.mProxy.getFocusedId() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public FillRequest(AugmentedAutofillService.AutofillProxy autofillProxy, InlineSuggestionsRequest inlineSuggestionsRequest) {
        this.mProxy = autofillProxy;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) autofillProxy);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
    }

    public InlineSuggestionsRequest getInlineSuggestionsRequest() {
        return this.mInlineSuggestionsRequest;
    }

    public String toString() {
        return "FillRequest { proxy = " + proxyToString() + ", inlineSuggestionsRequest = " + this.mInlineSuggestionsRequest + " }";
    }
}
