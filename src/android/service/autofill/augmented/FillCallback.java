package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.util.Log;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class FillCallback {
    private static final String TAG = "FillCallback";
    private final AugmentedAutofillService.AutofillProxy mProxy;

    FillCallback(AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public void onSuccess(FillResponse fillResponse) {
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "onSuccess(): " + fillResponse);
        }
        boolean z = true;
        if (fillResponse == null) {
            this.mProxy.logEvent(1);
            this.mProxy.reportResult(null, null, false);
            return;
        }
        List<Dataset> inlineSuggestions = fillResponse.getInlineSuggestions();
        Bundle clientState = fillResponse.getClientState();
        FillWindow fillWindow = fillResponse.getFillWindow();
        if (inlineSuggestions != null && !inlineSuggestions.isEmpty()) {
            this.mProxy.logEvent(4);
        } else {
            if (fillWindow != null) {
                fillWindow.show();
            }
            this.mProxy.reportResult(inlineSuggestions, clientState, z);
        }
        z = false;
        this.mProxy.reportResult(inlineSuggestions, clientState, z);
    }
}
