package com.android.systemui.qp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.SecStatusBarAudioManagerHelper;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SubroomSoundSettingsView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final AnonymousClass1 mReceiver;
    public LinearLayout mSoundBackground;
    public ImageView mSoundButton;
    public int mSoundProfile;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qp.SubroomSoundSettingsView$1] */
    public SubroomSoundSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qp.SubroomSoundSettingsView.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(intent.getAction())) {
                    SubroomSoundSettingsView subroomSoundSettingsView = SubroomSoundSettingsView.this;
                    subroomSoundSettingsView.mSoundProfile = SecStatusBarAudioManagerHelper.getInstance(subroomSoundSettingsView.mContext).getRingerMode(true);
                    SubroomSoundSettingsView subroomSoundSettingsView2 = SubroomSoundSettingsView.this;
                    subroomSoundSettingsView2.setSoundIcon(subroomSoundSettingsView2.mSoundProfile, true);
                }
            }
        };
        this.mContext = context;
        this.mSoundProfile = SecStatusBarAudioManagerHelper.getInstance(context).getRingerMode(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSoundProfile = SecStatusBarAudioManagerHelper.getInstance(this.mContext).getRingerMode(true);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", "android.settings.ALL_SOUND_MUTE"), this.mReceiver);
        setSoundIcon((this.mSoundProfile == 2 && Settings.Global.getInt(this.mContext.getContentResolver(), "all_sound_off", 0) == 1) ? 4 : this.mSoundProfile, false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mReceiver);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSoundButton = (ImageView) findViewById(R.id.sound_image_view);
        this.mSoundBackground = (LinearLayout) findViewById(R.id.sound_background);
        this.mSoundButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomSoundSettingsView$$ExternalSyntheticLambda0
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubroomSoundSettingsView subroomSoundSettingsView = SubroomSoundSettingsView.this;
                int i = SubroomSoundSettingsView.$r8$clinit;
                subroomSoundSettingsView.getClass();
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isSoundModeTileBlocked()) {
                    Log.d("SubroomSoundSettingsView", "Subscreen Soundmode tile not available by KnoxStateMonitor.");
                } else {
                    int ringerMode = SecStatusBarAudioManagerHelper.getInstance(subroomSoundSettingsView.mContext).getRingerMode(true);
                    SecStatusBarAudioManagerHelper.getInstance(subroomSoundSettingsView.mContext).setRingerModeInternal(ringerMode != 1 ? ringerMode != 2 ? Settings.Global.getInt(subroomSoundSettingsView.mContext.getContentResolver(), "all_sound_off", 0) == 1 ? DeviceType.isVibratorSupported(subroomSoundSettingsView.mContext) : 2 : DeviceType.isVibratorSupported(subroomSoundSettingsView.mContext) : 0);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_SOUND_MODE_COVER);
            }
        });
        this.mSoundButton.setOnLongClickListener(new SubroomSoundSettingsView$$ExternalSyntheticLambda1());
    }

    public final void setSoundIcon(int i, boolean z) {
        int i2 = R.string.accessibility_desc_on;
        if (i == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getString(R.string.quick_settings_sound_mode_mute_label));
            sb.append(", ");
            Context context = this.mContext;
            if (!z) {
                i2 = R.string.subscreen_sound_mode_talkback_sound_label;
            }
            this.mSoundButton.setContentDescription(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(context, i2, sb));
        } else if (i == 1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mContext.getString(R.string.quick_settings_sound_mode_vibrate_label));
            sb2.append(", ");
            Context context2 = this.mContext;
            if (!z) {
                i2 = R.string.subscreen_sound_mode_talkback_mute_label;
            }
            this.mSoundButton.setContentDescription(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(context2, i2, sb2));
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.mContext.getString(R.string.quick_settings_sound_mode_sound_label));
            sb3.append(", ");
            Context context3 = this.mContext;
            if (!z) {
                i2 = R.string.subscreen_sound_mode_talkback_vibrate_label;
            }
            this.mSoundButton.setContentDescription(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(context3, i2, sb3));
        }
        int i3 = R.drawable.quick_panel_icon_mute_all_sound;
        if (z) {
            ImageView imageView = this.mSoundButton;
            Resources resources = this.mContext.getResources();
            if (i == 0) {
                i3 = R.drawable.quick_panel_icon_sound_mute;
            } else if (i == 1) {
                i3 = R.drawable.quick_panel_icon_sound_vibrate;
            } else if (i == 2) {
                i3 = R.drawable.quick_panel_icon_sound;
            } else if (i != 4) {
                i3 = 0;
            }
            imageView.setImageDrawable(resources.getDrawable(i3));
            ((AnimationDrawable) this.mSoundButton.getDrawable()).start();
        } else {
            ImageView imageView2 = this.mSoundButton;
            Resources resources2 = this.mContext.getResources();
            if (i == 0) {
                i3 = R.drawable.quick_panel_icon_sound_mute_008;
            } else if (i == 1) {
                i3 = R.drawable.quick_panel_icon_sound_vibrate_015;
            } else if (i == 2) {
                i3 = R.drawable.quick_panel_icon_sound_011;
            } else if (i != 4) {
                i3 = 0;
            }
            imageView2.setImageDrawable(resources2.getDrawable(i3));
        }
        LinearLayout linearLayout = this.mSoundBackground;
        Resources resources3 = this.mContext.getResources();
        int i4 = R.drawable.subroom_inactive_background;
        if (i != 0 && i != 4) {
            i4 = R.drawable.subroom_active_background;
        }
        linearLayout.setBackground(resources3.getDrawable(i4));
    }
}
