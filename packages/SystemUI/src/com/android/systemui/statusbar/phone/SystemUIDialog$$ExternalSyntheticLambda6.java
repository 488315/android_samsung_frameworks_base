package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.R;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = SystemUIDialog.$r8$clinit;
        return ((View) obj).getTag(R.id.tag_dialog_background) != null;
    }
}
