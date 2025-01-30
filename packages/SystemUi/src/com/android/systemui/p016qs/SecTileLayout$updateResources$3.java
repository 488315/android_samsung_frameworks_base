package com.android.systemui.p016qs;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class SecTileLayout$updateResources$3 extends FunctionReferenceImpl implements Function2 {
    public static final SecTileLayout$updateResources$3 INSTANCE = new SecTileLayout$updateResources$3();

    public SecTileLayout$updateResources$3() {
        super(2, SecQSPanelResourcePicker.class, "getQsTileRow", "getQsTileRow(Landroid/content/Context;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Resources resources = ((Context) obj2).getResources();
        return Integer.valueOf(QpRune.QUICK_TABLET ? resources.getInteger(R.integer.sec_quick_settings_max_rows_tablet) : resources.getInteger(R.integer.sec_quick_settings_max_rows));
    }
}
