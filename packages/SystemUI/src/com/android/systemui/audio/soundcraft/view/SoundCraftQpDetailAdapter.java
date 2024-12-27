package com.android.systemui.audio.soundcraft.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.routine.condition.PlayingAudioConditionHandler;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveIntentFactory;
import com.android.systemui.audio.soundcraft.model.EffectOutDeviceType;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import com.android.systemui.qs.FullScreenDetailAdapter;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SoundCraftQpDetailAdapter extends FullScreenDetailAdapter {
    public final Context context;
    public final ModelProvider modelProvider;
    public final SoundAliveIntentFactory soundAliveIntentFactory;
    public final SoundCraftViewComponent viewComponent;

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

    public SoundCraftQpDetailAdapter(Context context, SoundCraftViewComponent soundCraftViewComponent, SoundAliveIntentFactory soundAliveIntentFactory, ModelProvider modelProvider) {
        this.context = context;
        this.viewComponent = soundCraftViewComponent;
        this.soundAliveIntentFactory = soundAliveIntentFactory;
        this.modelProvider = modelProvider;
        Log.d("SoundCraft.QpDetailAdapter", "initialized");
        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
        PlayingAudioConditionHandler playingAudioConditionHandler = new PlayingAudioConditionHandler(context);
        routineSdkImpl.getClass();
        com.samsung.android.sdk.routines.v3.internal.Log.b("RoutineSdkImpl", "setConditionHandler - tag=playing_audio, conditionHandler=" + playingAudioConditionHandler);
        routineSdkImpl.d.set(playingAudioConditionHandler, "playing_audio");
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        if (context == null) {
            return new View(context);
        }
        Log.d("SoundCraft.QpDetailAdapter", "createDetailView :cachedView=" + view + ", parent=" + viewGroup);
        SoundCraftViewComponent soundCraftViewComponent = this.viewComponent;
        soundCraftViewComponent.onCreate(context, viewGroup);
        SoundCraftViewBinding soundCraftViewBinding = soundCraftViewComponent.binding;
        Intrinsics.checkNotNull(soundCraftViewBinding);
        return soundCraftViewBinding.root;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void dismissListPopupWindow() {
        super.dismissListPopupWindow();
        Log.d("SoundCraft.QpDetailAdapter", "dismissListPopupWindow");
        this.viewComponent.onDestroy();
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 5030;
    }

    @Override // com.android.systemui.qs.FullScreenDetailAdapter, com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        ModelProvider modelProvider = this.soundAliveIntentFactory.modelProvider;
        AppSettingModel appSettingModel = modelProvider.appSettingModel;
        Log.d("SoundCraft.SoundAliveIntentFactory", "getSettingsIntent : readyToUpdateRoutine=" + appSettingModel.readyToUpdateRoutine + ", routineId=" + appSettingModel.routineId);
        String str = modelProvider.appSettingModel.routineId;
        if (str == null) {
            Intent intent = new Intent("android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL");
            intent.putExtra("android.media.extra.AUDIO_SESSION", 0);
            return intent;
        }
        Intent intent2 = new Intent();
        intent2.setClassName("com.sec.android.app.soundalive", "com.sec.android.app.soundalive.activity.SoundCraftDetailActivity");
        intent2.putExtra("routine_id", str);
        return intent2;
    }

    @Override // com.android.systemui.qs.FullScreenDetailAdapter, com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.context.getString(R.string.soundcraft_sound_effects_title);
    }

    @Override // com.android.systemui.qs.FullScreenDetailAdapter, com.android.systemui.plugins.qs.DetailAdapter
    public final boolean shouldUseFullScreen() {
        this.viewComponent.updateModel(false);
        ModelProvider modelProvider = this.modelProvider;
        Log.d("SoundCraft.QpDetailAdapter", "shouldUseFullScreen : modelProvider.effectOutDeviceType=" + modelProvider.effectOutDeviceType);
        return modelProvider.effectOutDeviceType == EffectOutDeviceType.BUDS;
    }
}
