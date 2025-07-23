package com.android.systemui.qs.external;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileRequestDialog extends SystemUIDialog {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TileRequestDialog(Context context) {
        super(context, R.style.Theme_SystemUI_Dialog_Alert);
    }
}
