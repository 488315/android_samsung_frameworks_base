package androidx.compose.ui.autofill;

import android.graphics.Rect;
import android.view.View;
import android.view.autofill.AutofillId;
import androidx.collection.MutableIntSet;
import androidx.compose.ui.platform.coreshims.AutofillIdCompat;
import androidx.compose.ui.semantics.SemanticsOwner;
import androidx.compose.ui.spatial.RectManager;

/* loaded from: classes.dex */
public final class AndroidAutofillManager extends AutofillManager {
    public final PlatformAutofillManager platformAutofillManager;
    public final Rect reusableRect = new Rect();
    public final View view;

    public AndroidAutofillManager(PlatformAutofillManager platformAutofillManager, SemanticsOwner semanticsOwner, View view, RectManager rectManager, String str) {
        this.platformAutofillManager = platformAutofillManager;
        this.view = view;
        view.setImportantForAutofill(1);
        if (((AutofillId) AutofillIdCompat.toAutofillIdCompat(view.getAutofillId()).mWrappedObj) == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Required value was null.");
        }
        new MutableIntSet(0, 1, null);
        new MutableIntSet(0, 1, null);
    }
}
