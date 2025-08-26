package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.R;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = SystemUIDialog.$r8$clinit;
        return ((View) obj).getTag(R.id.tag_dialog_background) != null;
    }
}
