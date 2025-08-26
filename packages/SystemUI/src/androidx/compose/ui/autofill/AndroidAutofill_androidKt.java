package androidx.compose.ui.autofill;

import android.util.Log;
import android.view.ViewStructure;
import androidx.compose.ui.autofill.ContentDataType;
import androidx.compose.ui.geometry.Rect;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        int iAddChildCount = viewStructure.addChildCount(size);
        for (Map.Entry entry : ((LinkedHashMap) autofillTree.children).entrySet()) {
            int iIntValue = ((Number) entry.getKey()).intValue();
            AutofillNode autofillNode = (AutofillNode) entry.getValue();
            AutofillApi26Helper.INSTANCE.getClass();
            ViewStructure viewStructureNewChild = viewStructure.newChild(iAddChildCount);
            viewStructureNewChild.setAutofillId(androidAutofill.rootAutofillId, iIntValue);
            viewStructureNewChild.setId(iIntValue, androidAutofill.view.getContext().getPackageName(), null, null);
            ContentDataType.Companion.getClass();
            viewStructureNewChild.setAutofillType(((AndroidContentDataType) ContentDataType.Companion.Text).androidAutofillType);
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
            viewStructureNewChild.setAutofillHints((String[]) arrayList.toArray(new String[0]));
            Rect rect = autofillNode.boundingBox;
            if (rect == null) {
                Log.w("Autofill Warning", "Bounding box not set.\n                        Did you call perform autofillTree before the component was positioned? ");
            } else {
                int iRound = Math.round(rect.left);
                int iRound2 = Math.round(rect.top);
                int iRound3 = Math.round(rect.right);
                int iRound4 = Math.round(rect.bottom) - iRound2;
                AutofillApi26Helper.INSTANCE.getClass();
                viewStructureNewChild.setDimens(iRound, iRound2, 0, 0, iRound3 - iRound, iRound4);
            }
            iAddChildCount++;
        }
    }
}
