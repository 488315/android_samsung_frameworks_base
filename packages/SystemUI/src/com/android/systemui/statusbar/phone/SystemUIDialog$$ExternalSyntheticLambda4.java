package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.R;
import java.util.function.Predicate;

public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda4 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((View) obj).getTag(R.id.tag_dialog_background) != null;
    }
}
