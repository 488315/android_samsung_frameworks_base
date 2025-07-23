package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.service.autofill.augmented.AugmentedAutofillService;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class FillController {
    private static final String TAG = "FillController";
    private final AugmentedAutofillService.AutofillProxy mProxy;

    FillController(AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public void autofill(List<Pair<AutofillId, AutofillValue>> list) {
        Objects.requireNonNull(list);
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "autofill() with " + list.size() + " values");
        }
        try {
            this.mProxy.autofill(list);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        FillWindow fillWindow = this.mProxy.getFillWindow();
        if (fillWindow != null) {
            fillWindow.destroy();
        }
    }
}
