package androidx.compose.ui.autofill;

import android.view.View;
import android.view.autofill.AutofillId;
import androidx.compose.ui.platform.coreshims.AutofillIdCompat;

/* loaded from: classes.dex */
public final class AndroidAutofill {
    public final android.view.autofill.AutofillManager autofillManager;
    public final AutofillTree autofillTree;
    public final AutofillId rootAutofillId;
    public final View view;

    public AndroidAutofill(View view, AutofillTree autofillTree) {
        this.view = view;
        this.autofillTree = autofillTree;
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) view.getContext().getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager == null) {
            throw new IllegalStateException("Autofill service could not be located.");
        }
        this.autofillManager = autofillManager;
        view.setImportantForAutofill(1);
        AutofillId autofillId = (AutofillId) AutofillIdCompat.toAutofillIdCompat(view.getAutofillId()).mWrappedObj;
        if (autofillId == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Required value was null.");
        }
        this.rootAutofillId = autofillId;
    }
}
