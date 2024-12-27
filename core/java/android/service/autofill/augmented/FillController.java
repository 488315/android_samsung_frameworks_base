package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

import java.util.List;
import java.util.Objects;

@SystemApi
public final class FillController {
    private static final String TAG = FillController.class.getSimpleName();
    private final AugmentedAutofillService.AutofillProxy mProxy;

    FillController(AugmentedAutofillService.AutofillProxy proxy) {
        this.mProxy = proxy;
    }

    public void autofill(List<Pair<AutofillId, AutofillValue>> values) {
        Objects.requireNonNull(values);
        if (AugmentedAutofillService.sDebug) {
            Log.d(TAG, "autofill() with " + values.size() + " values");
        }
        try {
            this.mProxy.autofill(values);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        FillWindow fillWindow = this.mProxy.getFillWindow();
        if (fillWindow != null) {
            fillWindow.destroy();
        }
    }
}
