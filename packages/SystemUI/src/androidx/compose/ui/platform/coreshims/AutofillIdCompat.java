package androidx.compose.ui.platform.coreshims;

import android.view.autofill.AutofillId;

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
