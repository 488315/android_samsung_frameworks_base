package com.android.systemui.audio.soundcraft.interfaces.wearable.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.utils.PackageExt;
import java.util.List;
import kotlin.Result;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BudsSettingIntentFactory {
    public final Context context;
    public final ModelProvider modelProvider;
    public final SoundCraftSettings settings;

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

    public BudsSettingIntentFactory(Context context, ModelProvider modelProvider, SoundCraftSettings soundCraftSettings) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.settings = soundCraftSettings;
    }

    public final Intent createIntent() {
        Object failure;
        ModelProvider modelProvider = this.modelProvider;
        AppSettingModel appSettingModel = modelProvider.appSettingModel;
        ExifInterface$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("createIntent : readyToUpdateRoutine=", ", routineExistOnPlugin=", ",routineId=", appSettingModel.readyToUpdateRoutine, appSettingModel.routineExistOnPlugin), appSettingModel.routineId, "SoundCraft.BudsSettingIntentFactory");
        AppSettingModel appSettingModel2 = modelProvider.appSettingModel;
        if (!appSettingModel2.routineExistOnPlugin) {
            return createMainPageIntent();
        }
        if (appSettingModel2.routineId != null) {
            Intent intent = new Intent();
            SoundCraftSettings soundCraftSettings = this.settings;
            intent.setPackage(soundCraftSettings.budsPluginPackageName);
            intent.addFlags(268435456);
            intent.setComponent(new ComponentName(soundCraftSettings.budsPluginPackageName, "com.samsung.accessory.hearablemgr.module.soundeffect.SoundCraftDetailActivity"));
            String str = modelProvider.appSettingModel.routineId;
            if (str != null) {
                intent.putExtra("routine_id", str);
            }
            PackageExt packageExt = PackageExt.INSTANCE;
            Context context = this.context;
            packageExt.getClass();
            try {
                int i = Result.$r8$clinit;
                failure = context.getPackageManager().queryIntentActivities(intent, 0);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            if (!(((List) failure) != null ? !r2.isEmpty() : false)) {
                intent = null;
            }
            if (intent != null) {
                return intent;
            }
        }
        return createMainPageIntent();
    }

    public final Intent createMainPageIntent() {
        Intent intent = new Intent();
        SoundCraftSettings soundCraftSettings = this.settings;
        intent.setPackage(soundCraftSettings.budsPluginPackageName);
        intent.addFlags(268435456);
        intent.setComponent(new ComponentName(soundCraftSettings.budsPluginPackageName, "com.samsung.accessory.hearablemgr.module.soundeffect.SoundEffectActivity"));
        String str = this.modelProvider.appSettingModel.routineId;
        if (str != null) {
            intent.putExtra("routine_id", str);
        }
        return intent;
    }
}
