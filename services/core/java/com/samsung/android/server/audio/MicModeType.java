package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.media.AudioSystem;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.server.audio.utils.AudioUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class MicModeType {
    public static final /* synthetic */ MicModeType[] $VALUES;
    public static final List AVAILABLE_DEVICE_TYPES;
    public static final MicModeType NONE;
    public static final MicModeType TYPE_2MIC;
    public static final MicModeType TYPE_2MIC_VOICE;
    public static final MicModeType TYPE_3MIC;
    public static final MicModeType TYPE_DEFAULT;
    public static final MicModeType TYPE_QP;
    public static final MicModeType TYPE_VOICE;
    public static final ArrayMap sMicModeParamTable;
    private int mCallMicMode = 0;
    private int mVoipCallMicMode = 0;
    private final int type;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$1, reason: invalid class name */
    enum AnonymousClass1 extends MicModeType {
        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_NONE";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            ArrayMap arrayMap = MicModeType.sMicModeParamTable;
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(getCallMicMode() + 3)));
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(getVoipCallMicMode())));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$2, reason: invalid class name */
    enum AnonymousClass2 extends MicModeType {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_QP";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            return (i == 2 || i == 3) && MicModeType.AVAILABLE_DEVICE_TYPES.stream().anyMatch(new MicModeType$2$$ExternalSyntheticLambda0(i2, 0));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            ArrayMap arrayMap = MicModeType.sMicModeParamTable;
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(getCallMicMode() + 3)));
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(getVoipCallMicMode())));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
            String str = audioParameter.get("l_mic_input_control_mode_call");
            if (str != null) {
                setCallMicMode(Integer.parseInt(str), contentResolver);
                return;
            }
            String str2 = audioParameter.get("l_mic_input_control_mode");
            if (str2 != null) {
                setVoipCallMicMode(Integer.parseInt(str2), contentResolver);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$3, reason: invalid class name */
    enum AnonymousClass3 extends MicModeType {
        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_2MIC";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            return i == 3 && i2 == 2 && !MicModeType.isStateEnabled(i3, 1);
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            AudioSystem.setParameters((String) MicModeType.sMicModeParamTable.get(Integer.valueOf(getVoipCallMicMode())));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
            String str = audioParameter.get("l_mic_input_control_mode_2mic");
            if (str != null) {
                setVoipCallMicMode(Integer.parseInt(str), contentResolver);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$4, reason: invalid class name */
    enum AnonymousClass4 extends MicModeType {
        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_3MIC";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            return i == 3 && i2 == 2 && !MicModeType.isStateEnabled(i3, 1);
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            AudioSystem.setParameters((String) MicModeType.sMicModeParamTable.get(Integer.valueOf(getVoipCallMicMode())));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
            String str = audioParameter.get("l_mic_input_control_mode_call");
            if (str != null) {
                setCallMicMode(Integer.parseInt(str), contentResolver);
                return;
            }
            String str2 = audioParameter.get("l_mic_input_control_mode");
            if (str2 != null) {
                setVoipCallMicMode(Integer.parseInt(str2), contentResolver);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$5, reason: invalid class name */
    enum AnonymousClass5 extends MicModeType {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_2MIC_VOICE";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            if (i >= 2 && i <= 3) {
                if (i == 2 || (i == 3 && MicModeType.isStateEnabled(i3, 1))) {
                    if (!MicModeType.AVAILABLE_DEVICE_TYPES.stream().anyMatch(new MicModeType$2$$ExternalSyntheticLambda0(i2, 1)) || MicModeType.isStateEnabled(i3, 2)) {
                    }
                } else if (i == 3 && i2 != 2) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            ArrayMap arrayMap = MicModeType.sMicModeParamTable;
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(getCallMicMode() + 3)));
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(getVoipCallMicMode())));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
            String str = audioParameter.get("l_call_nc_booster_enable");
            if (str != null) {
                setCallMicMode(Integer.parseInt(str), contentResolver);
                return;
            }
            String str2 = audioParameter.get("l_mic_input_control_mode_2mic");
            if (str2 != null) {
                setVoipCallMicMode(Integer.parseInt(str2), contentResolver);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$6, reason: invalid class name */
    enum AnonymousClass6 extends MicModeType {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_VOICE";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            return (i == 2 || (i == 3 && MicModeType.isStateEnabled(i3, 1))) && MicModeType.AVAILABLE_DEVICE_TYPES.stream().anyMatch(new MicModeType$2$$ExternalSyntheticLambda0(i2, 2)) && !MicModeType.isStateEnabled(i3, 2);
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            AudioSystem.setParameters((String) MicModeType.sMicModeParamTable.get(Integer.valueOf(getCallMicMode() + 3)));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
            String str = audioParameter.get("l_call_nc_booster_enable");
            if (str != null) {
                setCallMicMode(Integer.parseInt(str), contentResolver);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.server.audio.MicModeType$7, reason: invalid class name */
    enum AnonymousClass7 extends MicModeType {
        @Override // com.samsung.android.server.audio.MicModeType
        public final String getTypeToString() {
            return "TYPE_DEFAULT";
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final boolean isMicModeSupported(int i, int i2, int i3) {
            return i == 3 && i2 == 2 && !MicModeType.isStateEnabled(i3, 1);
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void restoreMicMode(ContentResolver contentResolver) {
            restoreMode(contentResolver);
            AudioSystem.setParameters((String) MicModeType.sMicModeParamTable.get(Integer.valueOf(getVoipCallMicMode())));
        }

        @Override // com.samsung.android.server.audio.MicModeType
        public final void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter) {
            String str = audioParameter.get("l_mic_input_control_mode_call");
            if (str != null) {
                setCallMicMode(Integer.parseInt(str), contentResolver);
                return;
            }
            String str2 = audioParameter.get("l_mic_input_control_mode");
            if (str2 != null) {
                setVoipCallMicMode(Integer.parseInt(str2), contentResolver);
            }
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(0, 0, "NONE");
        NONE = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(1, 1, "TYPE_QP");
        TYPE_QP = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(2, 2, "TYPE_2MIC");
        TYPE_2MIC = anonymousClass3;
        AnonymousClass4 anonymousClass4 = new AnonymousClass4(3, 3, "TYPE_3MIC");
        TYPE_3MIC = anonymousClass4;
        AnonymousClass5 anonymousClass5 = new AnonymousClass5(4, 4, "TYPE_2MIC_VOICE");
        TYPE_2MIC_VOICE = anonymousClass5;
        AnonymousClass6 anonymousClass6 = new AnonymousClass6(5, 5, "TYPE_VOICE");
        TYPE_VOICE = anonymousClass6;
        AnonymousClass7 anonymousClass7 = new AnonymousClass7(6, 6, "TYPE_DEFAULT");
        TYPE_DEFAULT = anonymousClass7;
        $VALUES = new MicModeType[]{anonymousClass1, anonymousClass2, anonymousClass3, anonymousClass4, anonymousClass5, anonymousClass6, anonymousClass7};
        AVAILABLE_DEVICE_TYPES = Arrays.asList(1, 2);
        new HashMap() { // from class: com.samsung.android.server.audio.MicModeType.8
            {
                put(0, MicModeType.NONE);
                put(1, MicModeType.TYPE_QP);
                put(2, MicModeType.TYPE_2MIC);
                put(3, MicModeType.TYPE_3MIC);
                put(4, MicModeType.TYPE_2MIC_VOICE);
                put(5, MicModeType.TYPE_VOICE);
                put(6, MicModeType.TYPE_DEFAULT);
            }
        };
        ArrayMap arrayMap = new ArrayMap();
        sMicModeParamTable = arrayMap;
        arrayMap.put(0, "l_mic_input_control_mode=0");
        arrayMap.put(1, "l_mic_input_control_mode=1");
        arrayMap.put(2, "l_mic_input_control_mode=2");
        arrayMap.put(3, "l_mic_input_control_mode_call=0");
        arrayMap.put(4, "l_mic_input_control_mode_call=1");
    }

    public MicModeType(int i, int i2, String str) {
        this.type = i2;
    }

    public static MicModeType getMicModeType() {
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_MICMODE_QUICK_PANEL")) {
            return TYPE_QP;
        }
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL");
        Log.i("MicModeManager", "getMicModeFeature() floatingVideoCallFeature : " + string);
        if ("DEFAULT".equals(string)) {
            return TYPE_DEFAULT;
        }
        if ("3MIC".equals(string)) {
            return TYPE_3MIC;
        }
        boolean equals = "2MIC".equals(string);
        MicModeType micModeType = TYPE_2MIC;
        if (equals) {
            updateParamTableFor2Mic();
            return micModeType;
        }
        boolean equals2 = "2MIC;VOICE".equals(string);
        MicModeType micModeType2 = TYPE_2MIC_VOICE;
        if (equals2) {
            updateParamTableFor2Mic();
            updateParamTableForVoice();
            return micModeType2;
        }
        boolean equals3 = "VOICE".equals(string);
        MicModeType micModeType3 = TYPE_VOICE;
        if (equals3) {
            updateParamTableForVoice();
            return micModeType3;
        }
        String string2 = SemCscFeature.getInstance().getString("CscFeature_Audio_ConfigEffectsVideoCall");
        Log.i("MicModeManager", "getMicModeFeature() cscVideoCallFeature : " + string2);
        if ("2MIC".equals(string2)) {
            updateParamTableFor2Mic();
            return micModeType;
        }
        if ("2MIC;VOICE".equals(string2)) {
            updateParamTableFor2Mic();
            updateParamTableForVoice();
            return micModeType2;
        }
        if (!"VOICE".equals(string2)) {
            return NONE;
        }
        updateParamTableForVoice();
        return micModeType3;
    }

    public static boolean isStateEnabled(int i, int i2) {
        return (i & i2) > 0;
    }

    public static void updateParamTableFor2Mic() {
        ArrayMap arrayMap = sMicModeParamTable;
        arrayMap.put(0, "l_mic_input_control_mode_2mic=0");
        arrayMap.put(1, "l_mic_input_control_mode_2mic=1");
        arrayMap.put(2, "l_mic_input_control_mode_2mic=2");
    }

    public static void updateParamTableForVoice() {
        ArrayMap arrayMap = sMicModeParamTable;
        arrayMap.put(3, "l_call_nc_booster_enable=0");
        arrayMap.put(4, "l_call_nc_booster_enable=1");
    }

    public static MicModeType valueOf(String str) {
        return (MicModeType) Enum.valueOf(MicModeType.class, str);
    }

    public static MicModeType[] values() {
        return (MicModeType[]) $VALUES.clone();
    }

    public final int getCallMicMode() {
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("getCallMicMode callMicMode = "), this.mCallMicMode, "MicModeManager");
        return this.mCallMicMode;
    }

    public final String getCallModeToString(int i) {
        if (i == 0) {
            return "MODE_STANDARD";
        }
        if (i == 1) {
            return "MODE_FOCUS_ON_VOICE";
        }
        if (i == 2) {
            return "MODE_FOCUS_ON_ALL_SOUNDS";
        }
        if (i == 3) {
            return "MODE_CALL_STANDARD";
        }
        if (i == 4) {
            return "MODE_CALL_FOCUS_ON_VOICE";
        }
        return "Invalid mode " + this.mCallMicMode;
    }

    public final int getType() {
        return this.type;
    }

    public abstract String getTypeToString();

    public final int getVoipCallMicMode() {
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("getVoipCallMicMode voipCallMicMode = "), this.mVoipCallMicMode, "MicModeManager");
        return this.mVoipCallMicMode;
    }

    public abstract boolean isMicModeSupported(int i, int i2, int i3);

    public abstract void restoreMicMode(ContentResolver contentResolver);

    public final void restoreMode(ContentResolver contentResolver) {
        this.mCallMicMode = Settings.System.getInt(contentResolver, "call_mic_mode", 0);
        this.mVoipCallMicMode = Settings.System.getInt(contentResolver, "voip_call_mic_mode", 0);
        StringBuilder sb = new StringBuilder("restoreMicMode callMicMode = ");
        sb.append(this.mCallMicMode);
        sb.append(", voipCallMicMode = ");
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, this.mVoipCallMicMode, "MicModeManager");
    }

    public final void setCallMicMode(int i, ContentResolver contentResolver) {
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "setCallMicMode callMicMode = ", "MicModeManager");
        this.mCallMicMode = i;
        AudioUtils.setSettingsInt(contentResolver, "mic_mode_effect", i);
        AudioUtils.setSettingsInt(contentResolver, "call_mic_mode", i);
    }

    public abstract void setMicInputControlMode(ContentResolver contentResolver, AudioParameter audioParameter);

    public final void setVoipCallMicMode(int i, ContentResolver contentResolver) {
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "setVoipCallMicMode voipCallMicMode = ", "MicModeManager");
        this.mVoipCallMicMode = i;
        AudioUtils.setSettingsInt(contentResolver, "mic_mode_effect", i);
        AudioUtils.setSettingsInt(contentResolver, "voip_call_mic_mode", i);
    }
}
