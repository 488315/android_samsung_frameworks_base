package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SystemBooleanSettingObserver extends ContentObserver {
    public final Context context;
    public final Function1 onChanged;
    public boolean settingValue;
    public final String systemSettingName;

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

    public SystemBooleanSettingObserver(Context context, String str, Function1 function1) {
        super(new Handler(Looper.getMainLooper()));
        this.context = context;
        this.systemSettingName = str;
        this.onChanged = function1;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        super.onChange(z);
        updateValue(true);
    }

    public final void register() {
        Log.d("SoundCraft.SystemBooleanSettingObserver", "register : settingName=" + this.systemSettingName);
        updateValue(false);
        this.context.getContentResolver().registerContentObserver(Settings.System.getUriFor(this.systemSettingName), false, this);
    }

    public final void updateValue(boolean z) {
        boolean z2 = Settings.System.getInt(this.context.getContentResolver(), this.systemSettingName, 0) == 1;
        if (z && this.settingValue != z2) {
            this.onChanged.mo781invoke(Boolean.valueOf(z2));
        }
        this.settingValue = z2;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateValue : settingValue=", "SoundCraft.SystemBooleanSettingObserver", z2);
    }
}
