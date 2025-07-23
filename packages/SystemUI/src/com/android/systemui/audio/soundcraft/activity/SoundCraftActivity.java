package com.android.systemui.audio.soundcraft.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.audio.soundcraft.SoundCraftCoverController;
import com.android.systemui.audio.soundcraft.SoundCraftCoverView;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SoundCraftActivity$onCreate$3 actionScreenReceiver;
    public final Provider controller;

    public SoundCraftActivity(Provider provider) {
        this.controller = provider;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.content.BroadcastReceiver, com.android.systemui.audio.soundcraft.activity.SoundCraftActivity$onCreate$3] */
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Display display = getDisplay();
        if (display.getDisplayId() != 1) {
            display = null;
        }
        if (display == null) {
            SoundCraftActivity$onCreate$3 soundCraftActivity$onCreate$3 = this.actionScreenReceiver;
            if (soundCraftActivity$onCreate$3 != null) {
                unregisterReceiver(soundCraftActivity$onCreate$3);
                this.actionScreenReceiver = null;
            }
            finish();
            return;
        }
        Provider provider = this.controller;
        SoundCraftCoverController soundCraftCoverController = (SoundCraftCoverController) provider.get();
        soundCraftCoverController.getClass();
        SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_BUDS_DETAIL_SETTING, SoundCraftSALogging.Event.SHOW_COVER, null, 12);
        Object obj = soundCraftCoverController.soundCraftCoverViewProvider.get();
        ((SoundCraftCoverView) obj).getClass();
        setContentView((View) obj);
        setShowWhenLocked(true);
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.audio.soundcraft.activity.SoundCraftActivity$onCreate$3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                SoundCraftActivity soundCraftActivity = SoundCraftActivity.this;
                int i = SoundCraftActivity.$r8$clinit;
                SoundCraftActivity$onCreate$3 soundCraftActivity$onCreate$32 = soundCraftActivity.actionScreenReceiver;
                if (soundCraftActivity$onCreate$32 != null) {
                    soundCraftActivity.unregisterReceiver(soundCraftActivity$onCreate$32);
                    soundCraftActivity.actionScreenReceiver = null;
                }
                SoundCraftActivity.this.finish();
            }
        };
        registerReceiver(r0, new IntentFilter("android.intent.action.SCREEN_OFF"));
        this.actionScreenReceiver = r0;
        ((SoundCraftCoverController) provider.get()).listener = new SoundCraftActivity$onCreate$5(this);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (attributes != null) {
            attributes.semSetScreenTimeout(6000L);
            attributes.semSetScreenDimDuration(0L);
            attributes.privateFlags |= 16;
            getWindow().setAttributes(attributes);
        }
    }
}
