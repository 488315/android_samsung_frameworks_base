package com.android.systemui.audio.soundcraft.viewmodel.common.audioeffect;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.utils.IconExt;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.Result;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AudioEffectHeaderViewModel extends BaseViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final MutableLiveData icon = new MutableLiveData(null);
    public final MutableLiveData title = new MutableLiveData("");
    public final MutableLiveData isVisible = new MutableLiveData(Boolean.FALSE);
    public final MutableLiveData marginTop = new MutableLiveData(0);

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

    public AudioEffectHeaderViewModel(Context context, ModelProvider modelProvider) {
        this.context = context;
        this.modelProvider = modelProvider;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        String str;
        Object failure;
        Object failure2;
        ModelProvider modelProvider = this.modelProvider;
        AppSettingModel appSettingModel = modelProvider.appSettingModel;
        boolean z = appSettingModel.readyToUpdateRoutine;
        MutableLiveData mutableLiveData = this.title;
        MutableLiveData mutableLiveData2 = this.icon;
        if (z && (str = appSettingModel.playingAudioPackageName) != null) {
            IconExt iconExt = IconExt.INSTANCE;
            PackageManager packageManager = this.context.getPackageManager();
            iconExt.getClass();
            try {
                int i = Result.$r8$clinit;
                failure = packageManager.getApplicationIcon(str);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            if (failure instanceof Result.Failure) {
                failure = null;
            }
            mutableLiveData2.setValue((Drawable) failure);
            IconExt iconExt2 = IconExt.INSTANCE;
            PackageManager packageManager2 = this.context.getPackageManager();
            iconExt2.getClass();
            try {
                failure2 = packageManager2.getApplicationLabel(packageManager2.getApplicationInfo(str, 0)).toString();
            } catch (Throwable th2) {
                int i3 = Result.$r8$clinit;
                failure2 = new Result.Failure(th2);
            }
            mutableLiveData.setValue((String) (failure2 instanceof Result.Failure ? null : failure2));
        }
        this.isVisible.setValue(Boolean.valueOf((!modelProvider.appSettingModel.readyToUpdateRoutine || mutableLiveData2.getValue() == null || mutableLiveData.getValue() == null) ? false : true));
        this.marginTop.setValue(modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS ? Integer.valueOf((int) (20 * Resources.getSystem().getDisplayMetrics().density)) : 0);
    }

    public final String toString() {
        return "[title=" + this.title + "]";
    }
}
