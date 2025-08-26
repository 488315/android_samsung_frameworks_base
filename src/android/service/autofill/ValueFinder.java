package android.service.autofill;

import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

/* loaded from: classes3.dex */
public interface ValueFinder {
    AutofillValue findRawValueByAutofillId(AutofillId autofillId);

    default String findByAutofillId(AutofillId autofillId) {
        AutofillValue autofillValueFindRawValueByAutofillId = findRawValueByAutofillId(autofillId);
        if (autofillValueFindRawValueByAutofillId == null || !autofillValueFindRawValueByAutofillId.isText()) {
            return null;
        }
        return autofillValueFindRawValueByAutofillId.getTextValue().toString();
    }
}
