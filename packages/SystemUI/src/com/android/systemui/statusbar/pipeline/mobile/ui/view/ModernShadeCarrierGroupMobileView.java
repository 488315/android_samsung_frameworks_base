package com.android.systemui.statusbar.pipeline.mobile.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ModernShadeCarrierGroupMobileView extends LinearLayout {
    public static final Companion Companion = null;
    public final int subId;

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

    public ModernShadeCarrierGroupMobileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.subId = -1;
    }

    @Override // android.view.View
    public final String toString() {
        return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(this.subId, "ModernShadeCarrierGroupMobileView(subId=", ", viewString=", super.toString());
    }
}
