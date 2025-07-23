package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.android.systemui.widget.SystemUITextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyguardUsimTextView extends SystemUITextView {
    public int mCurrentSimState;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public TelephonyManager mTelephonyManager;
    public KeyguardUpdateMonitor mUpdateMonitor;

    public KeyguardUsimTextView(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        KeyguardUpdateMonitor keyguardUpdateMonitor;
        super.onAttachedToWindow();
        if (!CscRune.LOCKUI_BOTTOM_USIM_TEXT || (keyguardUpdateMonitor = this.mUpdateMonitor) == null) {
            return;
        }
        keyguardUpdateMonitor.registerCallback(this.mInfoCallback);
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateText(this.mCurrentSimState);
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.view.View
    public final void onDetachedFromWindow() {
        KeyguardUpdateMonitor keyguardUpdateMonitor;
        super.onDetachedFromWindow();
        if (!CscRune.LOCKUI_BOTTOM_USIM_TEXT || (keyguardUpdateMonitor = this.mUpdateMonitor) == null) {
            return;
        }
        keyguardUpdateMonitor.removeCallback(this.mInfoCallback);
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTelephonyManager = (TelephonyManager) ((TextView) this).mContext.getSystemService("phone");
        this.mUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        if (((TextView) this).mContext.getResources().getBoolean(17892015)) {
            updateText(this.mCurrentSimState);
        } else {
            setVisibility(8);
        }
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(Math.min(f, 1.0f));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.widget.TextView, com.android.systemui.statusbar.phone.KeyguardUsimTextView, com.android.systemui.widget.SystemUITextView] */
    public final void updateText(int i) {
        Log.d("KeyguardUsimTextView", "updateText(simState): " + i);
        setVisibility(0);
        Resources resources = getResources();
        int i2 = resources.getConfiguration().orientation;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(0, i2 == 1 ? resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_top) : resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_top_land), 0, 0);
        setLayoutParams(layoutParams);
        if (this.mUpdateMonitor.isIccBlockedPermanently()) {
            setText(getContext().getString(R.string.keyguard_usim_not_used, 10));
            return;
        }
        if (!DeviceState.isNoSimState(getContext())) {
            if (this.mTelephonyManager.isVoiceCapable() && this.mUpdateMonitor.isAllEnabledSlotOutOfService()) {
                setText(R.string.keyguard_nosim_text_kor_emergency_only);
                return;
            }
            if (i == 5 && CscRune.SECURITY_SKT_USIM_TEXT && !SystemProperties.get("ril.simtype").equals("") && 19 == Integer.valueOf(SystemProperties.get("ril.simtype")).intValue()) {
                setText(R.string.keyguard_skt_usim_unregistered);
                return;
            } else {
                setVisibility(8);
                return;
            }
        }
        if (this.mUpdateMonitor.mDeviceProvisioned) {
            setText(CscRune.LOCKUI_LGU_USIM_TEXT ? R.string.keyguard_nosim_text_lgt_only : R.string.keyguard_nosim_text_kor_only);
            return;
        }
        StringBuilder sb = new StringBuilder();
        CharSequence text = getContext().getText(R.string.keyguard_missing_sim_message_short);
        if (text == null) {
            text = "";
        }
        sb.append(text.toString());
        ?? text2 = getContext().getText(R.string.keyguard_missing_sim_message);
        sb.append((text2 != 0 ? text2 : "").toString());
        String sb2 = sb.toString();
        setText(sb2.subSequence(0, sb2.length()));
    }

    public KeyguardUsimTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentSimState = 1;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardUsimTextView.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
                    KeyguardUsimTextView keyguardUsimTextView = KeyguardUsimTextView.this;
                    keyguardUsimTextView.mCurrentSimState = i3;
                    keyguardUsimTextView.updateText(i3);
                }
            }
        };
    }
}
