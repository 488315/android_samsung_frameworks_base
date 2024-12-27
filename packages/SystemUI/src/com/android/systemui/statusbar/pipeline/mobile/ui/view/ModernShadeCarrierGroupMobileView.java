package com.android.systemui.statusbar.pipeline.mobile.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ModernShadeCarrierGroupMobileView extends LinearLayout {
    public static final Companion Companion = null;
    public final int subId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ModernShadeCarrierGroupMobileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.subId = -1;
    }

    @Override // android.view.View
    public final String toString() {
        return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(this.subId, "ModernShadeCarrierGroupMobileView(subId=", ", viewString=", super.toString());
    }
}
