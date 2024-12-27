package com.android.keyguard;

import android.net.Uri;
import androidx.slice.widget.SliceContent;
import java.util.function.Predicate;

public final /* synthetic */ class KeyguardSliceViewController$$ExternalSyntheticLambda3 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !"content://com.android.systemui.keyguard/action".equals(Uri.parse(((SliceContent) obj).mSliceItem.getSlice().mUri).toString());
    }
}
