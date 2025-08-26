package com.android.systemui.controls.ui.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import com.android.systemui.R;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class AUIFacadeImpl implements AUIFacade {
    public final AudioManager audioManager;
    public final Context context;
    public SoundPool soundPool;
    public final List soundResources = Arrays.asList(new Pair(SoundId.GeneralOff, Integer.valueOf(R.raw.general_off)), new Pair(SoundId.GeneralOn, Integer.valueOf(R.raw.general_on)), new Pair(SoundId.LightOff, Integer.valueOf(R.raw.light_off)), new Pair(SoundId.LightOn, Integer.valueOf(R.raw.light_on)), new Pair(SoundId.AutomationError, Integer.valueOf(R.raw.automation_error)), new Pair(SoundId.AutomationSuccess, Integer.valueOf(R.raw.automation_success)), new Pair(SoundId.MediaPause, Integer.valueOf(R.raw.media_pause)), new Pair(SoundId.MediaPlayResume, Integer.valueOf(R.raw.media_play_resume)));
    public final Map soundIdMap = new LinkedHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class SoundId {
        public static final /* synthetic */ SoundId[] $VALUES;
        public static final SoundId AutomationError;
        public static final SoundId AutomationSuccess;
        public static final SoundId GeneralOff;
        public static final SoundId GeneralOn;
        public static final SoundId LightOff;
        public static final SoundId LightOn;
        public static final SoundId MediaPause;
        public static final SoundId MediaPlayResume;

        static {
            SoundId soundId = new SoundId("GeneralOff", 0);
            GeneralOff = soundId;
            SoundId soundId2 = new SoundId("GeneralOn", 1);
            GeneralOn = soundId2;
            SoundId soundId3 = new SoundId("LightOff", 2);
            LightOff = soundId3;
            SoundId soundId4 = new SoundId("LightOn", 3);
            LightOn = soundId4;
            SoundId soundId5 = new SoundId("AutomationError", 4);
            AutomationError = soundId5;
            SoundId soundId6 = new SoundId("AutomationSuccess", 5);
            AutomationSuccess = soundId6;
            SoundId soundId7 = new SoundId("MediaPause", 6);
            MediaPause = soundId7;
            SoundId soundId8 = new SoundId("MediaPlayResume", 7);
            MediaPlayResume = soundId8;
            SoundId[] soundIdArr = {soundId, soundId2, soundId3, soundId4, soundId5, soundId6, soundId7, soundId8};
            $VALUES = soundIdArr;
            EnumEntriesKt.enumEntries(soundIdArr);
        }

        private SoundId(String str, int i) {
        }

        public static SoundId valueOf(String str) {
            return (SoundId) Enum.valueOf(SoundId.class, str);
        }

        public static SoundId[] values() {
            return (SoundId[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
    }

    public AUIFacadeImpl(Context context, AudioManager audioManager) {
        this.context = context;
        this.audioManager = audioManager;
    }

    @Override // com.android.systemui.controls.ui.util.AUIFacade
    public final void finalize() {
        SoundPool soundPool = this.soundPool;
        if (soundPool != null) {
            ((LinkedHashMap) this.soundIdMap).clear();
            soundPool.release();
        }
        this.soundPool = null;
    }

    public final void initialize() throws IllegalArgumentException {
        if (this.soundPool == null) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(SoundId.values().length);
            AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
            builder2.semAddAudioTag("stv_touch_tone");
            builder2.setUsage(13);
            builder2.setContentType(4);
            builder.setAudioAttributes(builder2.build());
            SoundPool soundPoolBuild = builder.build();
            for (Pair pair : this.soundResources) {
                this.soundIdMap.put(pair.getFirst(), Integer.valueOf(soundPoolBuild.load(this.context, ((Number) pair.getSecond()).intValue(), 1)));
            }
            soundPoolBuild.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.android.systemui.controls.ui.util.AUIFacadeImpl$initialize$1$1
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                    this.$tmp0.soundPool = soundPool;
                }
            });
        }
    }

    public final void playClick(int i, int i2, View view, boolean z) {
        Integer num;
        SoundPool soundPool = this.soundPool;
        if (soundPool != null) {
            Integer numValueOf = null;
            if (i != 0) {
                if (i == 1) {
                    numValueOf = (Integer) ((LinkedHashMap) this.soundIdMap).get(SoundId.AutomationError);
                } else if (i == 2) {
                    numValueOf = (Integer) ((LinkedHashMap) this.soundIdMap).get(SoundId.AutomationSuccess);
                } else if (i == 3) {
                    numValueOf = (Integer) ((LinkedHashMap) this.soundIdMap).get(SoundId.MediaPause);
                } else if (i == 4) {
                    numValueOf = (Integer) ((LinkedHashMap) this.soundIdMap).get(SoundId.MediaPlayResume);
                }
                if (numValueOf == null) {
                    numValueOf = Integer.valueOf(Log.w("AUIFacadeImpl", "getSoundId(): can't find customSound:" + i));
                }
            }
            if (numValueOf == null) {
                if (i2 == 13) {
                    num = (Integer) ((LinkedHashMap) this.soundIdMap).get(z ? SoundId.LightOn : SoundId.LightOff);
                } else {
                    num = (Integer) ((LinkedHashMap) this.soundIdMap).get(z ? SoundId.GeneralOn : SoundId.GeneralOff);
                }
                numValueOf = num;
                Unit unit = Unit.INSTANCE;
            }
            if (numValueOf != null) {
                int iIntValue = numValueOf.intValue();
                float fSemGetSituationVolume = this.audioManager.semGetSituationVolume(1, 0);
                soundPool.play(iIntValue, fSemGetSituationVolume, fSemGetSituationVolume, 0, 0, 1.0f);
            }
        }
        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
    }
}
