package com.android.systemui.qs.external;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TileRequestDialog extends SystemUIDialog {

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
