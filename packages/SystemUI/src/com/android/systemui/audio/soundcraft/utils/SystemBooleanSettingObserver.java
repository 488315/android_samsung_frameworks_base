package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SystemBooleanSettingObserver extends ContentObserver {
    public final Context context;
    public final Function1 onChanged;
    public boolean settingValue;
    public final String systemSettingName;

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

    public final void updateValue(boolean z) {
        boolean z2 = Settings.System.getInt(this.context.getContentResolver(), this.systemSettingName, 0) == 1;
        if (z && this.settingValue != z2) {
            this.onChanged.invoke(Boolean.valueOf(z2));
        }
        this.settingValue = z2;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateValue : settingValue=", "SoundCraft.SystemBooleanSettingObserver", z2);
    }
}
