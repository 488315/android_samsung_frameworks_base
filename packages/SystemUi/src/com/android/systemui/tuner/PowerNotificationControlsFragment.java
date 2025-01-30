package com.android.systemui.tuner;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PowerNotificationControlsFragment extends Fragment {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final boolean isEnabled() {
        return Settings.Secure.getInt(getContext().getContentResolver(), "show_importance_slider", 0) == 1;
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.power_notification_controls_settings, viewGroup, false);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        MetricsLogger.visibility(getContext(), 392, false);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        MetricsLogger.visibility(getContext(), 392, true);
    }

    @Override // android.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        View findViewById = view.findViewById(R.id.switch_bar);
        final Switch r3 = (Switch) findViewById.findViewById(android.R.id.switch_widget);
        final TextView textView = (TextView) findViewById.findViewById(R.id.switch_text);
        r3.setChecked(isEnabled());
        textView.setText(isEnabled() ? getString(R.string.switch_bar_on) : getString(R.string.switch_bar_off));
        r3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.PowerNotificationControlsFragment.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PowerNotificationControlsFragment powerNotificationControlsFragment = PowerNotificationControlsFragment.this;
                int i = PowerNotificationControlsFragment.$r8$clinit;
                boolean z = !powerNotificationControlsFragment.isEnabled();
                MetricsLogger.action(PowerNotificationControlsFragment.this.getContext(), 393, z);
                Settings.Secure.putInt(PowerNotificationControlsFragment.this.getContext().getContentResolver(), "show_importance_slider", z ? 1 : 0);
                r3.setChecked(z);
                textView.setText(z ? PowerNotificationControlsFragment.this.getString(R.string.switch_bar_on) : PowerNotificationControlsFragment.this.getString(R.string.switch_bar_off));
            }
        });
    }
}
