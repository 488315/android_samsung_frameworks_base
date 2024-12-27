package com.android.keyguard;

import android.net.Uri;
import androidx.slice.widget.SliceContent;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSliceViewController$$ExternalSyntheticLambda3 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !"content://com.android.systemui.keyguard/action".equals(Uri.parse(((SliceContent) obj).mSliceItem.getSlice().mUri).toString());
    }
}
