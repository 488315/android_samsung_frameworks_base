package com.android.systemui.audio.soundcraft.viewbinding;

import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.feature.SoundCraftDebugFeatures;
import com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView;
import com.android.systemui.audio.soundcraft.viewbinding.actionbar.SoundCraftActionBarBinding;
import com.android.systemui.audio.soundcraft.viewbinding.audioeffect.AudioEffectBoxLayoutBinding;
import com.android.systemui.audio.soundcraft.viewbinding.battery.BatteryInfoBoxViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.noisecontrol.NoiseControlBoxViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.routine.RoutineTestViewBinding;
import com.android.systemui.audio.soundcraft.viewbinding.volume.VolumeBarViewBinding;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SoundCraftViewBinding {
    public final SoundCraftActionBarBinding actionBar;
    public final AudioEffectBoxLayoutBinding audioEffectBox;
    public final BatteryInfoBoxViewBinding batteryInfoBox;
    public final SoundCraftDetailPageView detailPageContent;
    public final NoiseControlBoxViewBinding noiseControlBox;
    public final ViewGroup root;
    public final RoutineTestViewBinding routineTestView;
    public final VolumeBarViewBinding volumeBar;
    public final LinearLayout volumeContainer;

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

    public SoundCraftViewBinding(View view) {
        this.root = (ViewGroup) view.requireViewById(R.id.soundcraft_root);
        this.detailPageContent = (SoundCraftDetailPageView) view.requireViewById(R.id.soundcraft_detail_page_content);
        this.actionBar = new SoundCraftActionBarBinding(view);
        this.batteryInfoBox = new BatteryInfoBoxViewBinding(view);
        this.noiseControlBox = new NoiseControlBoxViewBinding(view);
        this.audioEffectBox = new AudioEffectBoxLayoutBinding(view);
        this.volumeBar = new VolumeBarViewBinding(view);
        this.volumeContainer = (LinearLayout) view.requireViewById(R.id.soundcraft_volume_container_background);
        SoundCraftDebugFeatures soundCraftDebugFeatures = SoundCraftDebugFeatures.INSTANCE;
        Context context = view.getContext();
        soundCraftDebugFeatures.getClass();
        this.routineTestView = Settings.System.getInt(context.getContentResolver(), "audio_soundcraft_debug_routine_view", 0) == 1 ? new RoutineTestViewBinding((ViewStub) view.requireViewById(R.id.soundcraft_routine_test_stub)) : null;
    }
}
