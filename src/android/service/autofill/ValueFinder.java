package android.service.autofill;

import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

/* loaded from: classes3.dex */
public interface ValueFinder {
    AutofillValue findRawValueByAutofillId(AutofillId autofillId);

    default String findByAutofillId(AutofillId autofillId) {
        AutofillValue findRawValueByAutofillId = findRawValueByAutofillId(autofillId);
        if (findRawValueByAutofillId == null || !findRawValueByAutofillId.isText()) {
            return null;
        }
        return findRawValueByAutofillId.getTextValue().toString();
    }
}
