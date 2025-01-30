package com.android.keyguard;

import androidx.slice.widget.SliceContent;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSliceViewController$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !"content://com.android.systemui.keyguard/action".equals(((SliceContent) obj).mSliceItem.getSlice().getUri().toString());
    }
}
