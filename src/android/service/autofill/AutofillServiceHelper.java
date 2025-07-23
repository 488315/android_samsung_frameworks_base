package android.service.autofill;

import android.view.autofill.AutofillId;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
final class AutofillServiceHelper {
    static AutofillId[] assertValid(AutofillId[] autofillIdArr) {
        Preconditions.checkArgument(autofillIdArr != null && autofillIdArr.length > 0, "must have at least one id");
        for (int i = 0; i < autofillIdArr.length; i++) {
            if (autofillIdArr[i] == null) {
                throw new IllegalArgumentException("ids[" + i + "] must not be null");
            }
        }
        return autofillIdArr;
    }

    private AutofillServiceHelper() {
        throw new UnsupportedOperationException("contains static members only");
    }
}
