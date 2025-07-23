package androidx.compose.ui.autofill;

import android.util.Log;
import android.view.ViewStructure;
import androidx.compose.ui.autofill.ContentDataType;
import androidx.compose.ui.geometry.Rect;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidAutofill_androidKt {
    public static final void populateViewStructure(AndroidAutofill androidAutofill, ViewStructure viewStructure) {
        if (androidAutofill.autofillTree.children.isEmpty()) {
            return;
        }
        AutofillApi26Helper autofillApi26Helper = AutofillApi26Helper.INSTANCE;
        AutofillTree autofillTree = androidAutofill.autofillTree;
        int size = autofillTree.children.size();
        autofillApi26Helper.getClass();
        int addChildCount = viewStructure.addChildCount(size);
        for (Map.Entry entry : ((LinkedHashMap) autofillTree.children).entrySet()) {
            int intValue = ((Number) entry.getKey()).intValue();
            AutofillNode autofillNode = (AutofillNode) entry.getValue();
            AutofillApi26Helper.INSTANCE.getClass();
            ViewStructure newChild = viewStructure.newChild(addChildCount);
            newChild.setAutofillId(androidAutofill.rootAutofillId, intValue);
            newChild.setId(intValue, androidAutofill.view.getContext().getPackageName(), null, null);
            ContentDataType.Companion.getClass();
            newChild.setAutofillType(((AndroidContentDataType) ContentDataType.Companion.Text).androidAutofillType);
            List list = autofillNode.autofillTypes;
            ArrayList arrayList = new ArrayList(list.size());
            int size2 = list.size();
            for (int i = 0; i < size2; i++) {
                String str = (String) AndroidAutofillType_androidKt.androidAutofillTypes.get((AutofillType) list.get(i));
                if (str == null) {
                    throw new IllegalArgumentException("Unsupported autofill type");
                }
                arrayList.add(str);
            }
            newChild.setAutofillHints((String[]) arrayList.toArray(new String[0]));
            Rect rect = autofillNode.boundingBox;
            if (rect == null) {
                Log.w("Autofill Warning", "Bounding box not set.\n                        Did you call perform autofillTree before the component was positioned? ");
            } else {
                int round = Math.round(rect.left);
                int round2 = Math.round(rect.top);
                int round3 = Math.round(rect.right);
                int round4 = Math.round(rect.bottom) - round2;
                AutofillApi26Helper.INSTANCE.getClass();
                newChild.setDimens(round, round2, 0, 0, round3 - round, round4);
            }
            addChildCount++;
        }
    }
}
