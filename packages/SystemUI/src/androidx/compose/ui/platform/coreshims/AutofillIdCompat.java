package androidx.compose.ui.platform.coreshims;

import android.view.autofill.AutofillId;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AutofillIdCompat {
    public final Object mWrappedObj;

    private AutofillIdCompat(AutofillId autofillId) {
        this.mWrappedObj = autofillId;
    }

    public static AutofillIdCompat toAutofillIdCompat(AutofillId autofillId) {
        return new AutofillIdCompat(autofillId);
    }
}
