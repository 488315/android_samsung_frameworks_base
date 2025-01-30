package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.Rune;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.media.AudioParameter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class MicModeManager {
    public static AudioManager mAudioManager;
    public static Context mContext;
    public static ContentResolver mCr;
    public static DesktopModeHelper mDesktopModeHelper;
    public static MicModeManager sInstance;
    public static final EventLogger sMicModeLogger;
    public static final ArrayMap sMicModeParamTable;
    public static final EventLogger sVoipLiveTranslateLogger;
    public final AudioSettingsHelper mAudioSettingsHelper;
    public final Uri mCallAssistantUri;
    public boolean mIsLiveTranslateOn;
    public boolean mIsMicModeOn;
    public boolean mIsVoiceCapable;
    public static final List AVAILABLE_STREAM_TYPES = Arrays.asList(0, 2);
    public static final List AVAILABLE_DEVICE_TYPES = Arrays.asList(1, 2);
    public int mMicModeType = 0;
    public String mPackageName = "";
    public String mVoipPackageName = "";
    public int mCurCallDevice = 2;
    public int mCurAudioMode = 0;
    public boolean mWifiCallState = false;
    public boolean mVideoCallState = false;
    public boolean mDexState = false;
    public boolean mSmartViewState = false;
    public boolean mCallTranslationState = false;
    public boolean mGameChatState = false;
    public int mCallMicMode = 0;
    public int mVoipCallMicMode = 0;

    public final String micModeTypeToString(int i) {
        switch (i) {
            case 1:
                return "TYPE_QP";
            case 2:
                return "TYPE_2MIC";
            case 3:
                return "TYPE_3MIC";
            case 4:
                return "TYPE_2MIC_VOICE";
            case 5:
                return "TYPE_VOICE";
            case 6:
                return "TYPE_DEFAULT";
            default:
                return "TYPE_NONE";
        }
    }

    public MicModeManager(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) context.getSystemService("audio");
        mDesktopModeHelper = DesktopModeHelper.getInstance(mContext);
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
        mCr = context.getContentResolver();
        this.mCallAssistantUri = Uri.parse("content://com.samsung.android.callassistant.voipprovider");
        this.mIsVoiceCapable = isVoiceCapable(context);
        initMicModeType();
    }

    public static synchronized MicModeManager getInstance(Context context) {
        MicModeManager micModeManager;
        synchronized (MicModeManager.class) {
            if (sInstance == null) {
                sInstance = new MicModeManager(context);
            }
            micModeManager = sInstance;
        }
        return micModeManager;
    }

    public final void initMicModeType() {
        try {
            this.mMicModeType = getMicModeFeature();
            Log.i("MicModeManager", "initMicModeType() mMicModeType : " + this.mMicModeType);
        } catch (NullPointerException unused) {
            Log.w("MicModeManager", "initMicModeType: NullPointerException");
        } catch (RuntimeException unused2) {
            Log.w("MicModeManager", "initMicModeType: RuntimeException");
        }
    }

    public final int getMicModeFeature() {
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_MICMODE_QUICK_PANEL")) {
            return 1;
        }
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_CONFIG_EFFECTS_VIDEOCALL");
        Log.i("MicModeManager", "getMicModeFeature() floatingVideoCallFeature : " + string);
        if ("DEFAULT".equals(string)) {
            return 6;
        }
        if ("3MIC".equals(string)) {
            return 3;
        }
        if ("2MIC".equals(string)) {
            updateParamTableFor2Mic();
            return 2;
        }
        if ("2MIC;VOICE".equals(string)) {
            updateParamTableFor2Mic();
            updateParamTableForVoice();
            return 4;
        }
        if ("VOICE".equals(string)) {
            updateParamTableForVoice();
            return 5;
        }
        String string2 = SemCscFeature.getInstance().getString("CscFeature_Audio_ConfigEffectsVideoCall");
        Log.i("MicModeManager", "getMicModeFeature() cscVideoCallFeature : " + string2);
        if ("2MIC".equals(string2)) {
            updateParamTableFor2Mic();
            return 2;
        }
        if ("2MIC;VOICE".equals(string2)) {
            updateParamTableFor2Mic();
            updateParamTableForVoice();
            return 4;
        }
        if (!"VOICE".equals(string2)) {
            return 0;
        }
        updateParamTableForVoice();
        return 5;
    }

    public int getMicModeType() {
        return this.mMicModeType;
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        sMicModeParamTable = arrayMap;
        arrayMap.put(0, "l_mic_input_control_mode=0");
        arrayMap.put(1, "l_mic_input_control_mode=1");
        arrayMap.put(2, "l_mic_input_control_mode=2");
        arrayMap.put(3, "l_mic_input_control_mode_call=0");
        arrayMap.put(4, "l_mic_input_control_mode_call=1");
        sMicModeLogger = new EventLogger(30, "Mic mode history");
        sVoipLiveTranslateLogger = new EventLogger(30, "Live translate history");
    }

    public void updateAudioMode(String str, int i) {
        this.mPackageName = str;
        this.mCurAudioMode = i;
        Log.i("MicModeManager", "updateAudioMode() audioMode=" + this.mCurAudioMode + ", caller=" + str + ", curCallDevice=" + this.mCurCallDevice);
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            updateVoipTranslator();
        }
        if (this.mCurAudioMode == 0) {
            disableMicMode();
            this.mPackageName = "";
            this.mVoipPackageName = "";
        } else if (isMicModeSupported()) {
            enableMicMode();
        } else {
            disableMicMode();
        }
    }

    public final void updateAudioDevice() {
        Log.i("MicModeManager", "updateAudioDevice() audioMode: " + this.mCurAudioMode + ", curDevice: " + this.mCurCallDevice);
        if (isMicModeSupported()) {
            enableMicMode();
        } else {
            disableMicMode();
        }
    }

    public void setCommunicationDevice(int i) {
        this.mCurCallDevice = i;
        Log.i("MicModeManager", "setCommunicationDevice() deviceType: " + i);
        updateAudioDevice();
    }

    public static /* synthetic */ boolean lambda$streamDevicesChanged$0(Intent intent, Integer num) {
        return num.intValue() == intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
    }

    public void streamDevicesChanged(final Intent intent) {
        if (AVAILABLE_STREAM_TYPES.stream().noneMatch(new Predicate() { // from class: com.samsung.android.server.audio.MicModeManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$streamDevicesChanged$0;
                lambda$streamDevicesChanged$0 = MicModeManager.lambda$streamDevicesChanged$0(intent, (Integer) obj);
                return lambda$streamDevicesChanged$0;
            }
        })) {
            return;
        }
        int intExtra = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", 0);
        int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", 0);
        this.mCurCallDevice = intExtra2;
        Log.i("MicModeManager", "streamDevicesChanged() prevDevice=" + intExtra + ", curDevice=" + intExtra2);
        updateAudioDevice();
    }

    public void updateWifiCallState(boolean z) {
        Log.i("MicModeManager", "updateWifiCallState() state=" + z);
        this.mWifiCallState = z;
        if (z) {
            setSettingsInt("mic_mode_wificall", 1);
        } else {
            setSettingsInt("mic_mode_wificall", 0);
        }
        if (isMicModeSupported()) {
            enableMicMode();
        } else {
            disableMicMode();
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            updateVoipTranslator();
        }
    }

    public void updateVideoCallState(boolean z) {
        Log.i("MicModeManager", "updateVideoCallState() state=" + z);
        this.mVideoCallState = z;
        updateMicModeState(z);
    }

    public void updateDexState(boolean z) {
        Log.i("MicModeManager", "updateDexState() state=" + z);
        this.mDexState = z;
        updateMicModeState(z);
    }

    public void updateSmartViewState(boolean z) {
        Log.i("MicModeManager", "updateSmartViewState() state=" + z);
        this.mSmartViewState = z;
        updateMicModeState(z);
    }

    public void updateGameChatState(boolean z) {
        Log.i("MicModeManager", "updateGameChatState() state=" + z);
        this.mGameChatState = z;
        updateMicModeState(z);
    }

    public void updateCallTranslationState(boolean z) {
        Log.i("MicModeManager", "updateCallTranslationState() state=" + z);
        this.mCallTranslationState = z;
        updateMicModeState(z);
    }

    public final void updateMicModeState(boolean z) {
        if (!isMicModeSupported()) {
            disableMicMode();
        } else {
            if (z) {
                return;
            }
            enableMicMode();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00bd, code lost:
    
        if (r6.mVideoCallState == false) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMicModeSupported() {
        Log.i("MicModeManager", "isMicModeSupported() MicModeType: " + this.mMicModeType + ", audioMode: " + this.mCurAudioMode + ", curDevice: " + this.mCurCallDevice + ", translationState: " + this.mCallTranslationState + ", dexState: " + this.mDexState + ", smartViewState: " + this.mSmartViewState + ", gameChatState: " + this.mGameChatState + ", wifiCallState: " + this.mWifiCallState + ", videoCallState: " + this.mVideoCallState);
        switch (this.mMicModeType) {
            case 1:
                int i = this.mCurAudioMode;
                if ((i != 2 && i != 3) || !AVAILABLE_DEVICE_TYPES.stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.MicModeManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$isMicModeSupported$1;
                        lambda$isMicModeSupported$1 = MicModeManager.this.lambda$isMicModeSupported$1((Integer) obj);
                        return lambda$isMicModeSupported$1;
                    }
                })) {
                    return false;
                }
                break;
            case 2:
            case 3:
            case 6:
                if (this.mCurAudioMode != 3 || this.mCurCallDevice != 2 || this.mWifiCallState) {
                    return false;
                }
                break;
            case 4:
                int i2 = this.mCurAudioMode;
                if (i2 >= 2 && i2 <= 3) {
                    if (i2 == 2 || (i2 == 3 && this.mWifiCallState)) {
                        if (AVAILABLE_DEVICE_TYPES.stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.MicModeManager$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$isMicModeSupported$2;
                                lambda$isMicModeSupported$2 = MicModeManager.this.lambda$isMicModeSupported$2((Integer) obj);
                                return lambda$isMicModeSupported$2;
                            }
                        })) {
                            break;
                        }
                    } else if (i2 == 3 && this.mCurCallDevice != 2) {
                        return false;
                    }
                }
                return false;
            case 5:
                int i3 = this.mCurAudioMode;
                if ((i3 != 2 && (i3 != 3 || !this.mWifiCallState)) || !AVAILABLE_DEVICE_TYPES.stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.MicModeManager$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$isMicModeSupported$3;
                        lambda$isMicModeSupported$3 = MicModeManager.this.lambda$isMicModeSupported$3((Integer) obj);
                        return lambda$isMicModeSupported$3;
                    }
                }) || this.mVideoCallState) {
                    return false;
                }
                break;
            default:
                return false;
        }
        if (this.mCallTranslationState) {
            return false;
        }
        if (this.mCurCallDevice == 2) {
            if (this.mDexState) {
                return false;
            }
            if (this.mCurAudioMode == 3 && !this.mWifiCallState && (this.mSmartViewState || this.mGameChatState)) {
                return false;
            }
        }
        Log.i("MicModeManager", "isMicModeSupported() MicMode ON");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isMicModeSupported$1(Integer num) {
        return this.mCurCallDevice == num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isMicModeSupported$2(Integer num) {
        return this.mCurCallDevice == num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isMicModeSupported$3(Integer num) {
        return this.mCurCallDevice == num.intValue();
    }

    public final boolean isVoiceCapable(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager != null && telephonyManager.isVoiceCapable();
    }

    public void updateRealPackageName(String str) {
        sVoipLiveTranslateLogger.enqueueAndLog("update connection service package to " + str + " from " + this.mPackageName, 0, "MicModeManager");
        this.mVoipPackageName = str;
        updateVoipTranslator();
    }

    public final void updateVoipTranslator() {
        if (this.mIsVoiceCapable) {
            String voipPackageName = getVoipPackageName();
            boolean z = Settings.Global.getInt(mCr, "translate_during_calls", 1) != 0 && this.mCurAudioMode == 3 && !this.mWifiCallState && isSupportedVoipTranslate(voipPackageName);
            String string = Settings.System.getString(mCr, "voip_translator_package");
            if (this.mIsLiveTranslateOn == z && voipPackageName.equals(string)) {
                return;
            }
            setVoipTranslator(z, voipPackageName);
        }
    }

    public final void setVoipTranslator(boolean z, String str) {
        boolean z2;
        if (z) {
            z2 = isSupportedVoipTranslation(str);
            if (z2) {
                String str2 = "l_voip_translate_package_name=" + str;
                sVoipLiveTranslateLogger.enqueue(new EventLogger.StringEvent(str2));
                AudioSystem.setParameters(str2);
            }
        } else {
            z2 = false;
        }
        this.mIsLiveTranslateOn = z && z2;
        sVoipLiveTranslateLogger.enqueueAndLog("setVoipTranslator enable " + z + ", supported " + z2 + ", mode " + this.mCurAudioMode + ", " + str, 0, "MicModeManager");
        setSettingsStr("voip_translator_package", str);
        setSettingsInt("voip_translator_enable", this.mIsLiveTranslateOn ? 1 : 0);
    }

    public final boolean isSupportedVoipTranslation(String str) {
        try {
            return mCr.call(this.mCallAssistantUri, "isSupportedVoipTranslation", str, (Bundle) null).getBoolean("is_support_voip_translation");
        } catch (Exception e) {
            Log.e("MicModeManager", e.getMessage());
            return false;
        }
    }

    public final boolean isSupportedVoipTranslate(String str) {
        boolean contains;
        String[] allowedPackages = getAllowedPackages();
        if (allowedPackages == null) {
            contains = this.mAudioSettingsHelper.checkCallPolicyCategory(str, "voip_live_translate_allow");
        } else {
            contains = Arrays.asList(allowedPackages).contains(str);
            sVoipLiveTranslateLogger.enqueueAndLog("voip allowPackages from globalDB packageName: " + str + ", result:" + contains, 0, "MicModeManager");
        }
        Log.d("MicModeManager", str + " is supported voip " + contains);
        return contains;
    }

    public final String[] getAllowedPackages() {
        String string = Settings.Global.getString(mCr, "translate_during_allow_apps");
        if (string == null) {
            return null;
        }
        return string.split(KnoxVpnFirewallHelper.DELIMITER);
    }

    public final String getVoipPackageName() {
        return ("com.android.server.telecom".equals(this.mPackageName) || "android".equals(this.mPackageName)) ? this.mVoipPackageName : this.mPackageName;
    }

    public final void enableMicMode() {
        int i = this.mCurAudioMode;
        if (i == 2 || (i == 3 && this.mWifiCallState)) {
            updateMicModeSettings(true, getCallMicMode());
        } else if (i == 3) {
            updateMicModeSettings(true, getVoipCallMicMode());
        }
    }

    public final void disableMicMode() {
        updateMicModeSettings(false, 0);
    }

    public final void updateMicModeSettings(boolean z, int i) {
        if (this.mIsMicModeOn != z) {
            this.mIsMicModeOn = z;
            sMicModeLogger.enqueueAndLog("updateMicModeSettings is enabled " + z + ", effect:" + i + ", " + this.mPackageName, 0, "MicModeManager");
        }
        if (z) {
            setSettingsInt("mic_mode_enable", 1);
            setSettingsInt("mic_mode_effect", i);
            setSettingsStr("mic_mode_package", this.mPackageName);
            return;
        }
        setSettingsInt("mic_mode_enable", 0);
    }

    public final void updateParamTableFor2Mic() {
        ArrayMap arrayMap = sMicModeParamTable;
        arrayMap.put(0, "l_mic_input_control_mode_2mic=0");
        arrayMap.put(1, "l_mic_input_control_mode_2mic=1");
        arrayMap.put(2, "l_mic_input_control_mode_2mic=2");
    }

    public final void updateParamTableForVoice() {
        ArrayMap arrayMap = sMicModeParamTable;
        arrayMap.put(3, "l_call_nc_booster_enable=0");
        arrayMap.put(4, "l_call_nc_booster_enable=1");
    }

    public void setMicInputControlMode(int i) {
        String str;
        ArrayMap arrayMap = sMicModeParamTable;
        if (!arrayMap.containsKey(Integer.valueOf(i))) {
            sMicModeLogger.enqueueAndLog("attempt to call setMicInputControlMode() invalid mode :" + i, 1, "MicModeManager");
            return;
        }
        sMicModeLogger.enqueueAndLog("setMicInputControlMode: " + i, 0, "MicModeManager");
        String str2 = (String) arrayMap.get(Integer.valueOf(i));
        Log.i("MicModeManager", "setMicInputControlMode setparam : " + str2);
        AudioSystem.setParameters(str2);
        AudioParameter audioParameter = new AudioParameter(str2);
        int i2 = this.mMicModeType;
        if (i2 == 1 || i2 == 6 || i2 == 3) {
            String str3 = audioParameter.get("l_mic_input_control_mode_call");
            if (str3 != null) {
                setCallMicMode(Integer.parseInt(str3));
                return;
            }
            String str4 = audioParameter.get("l_mic_input_control_mode");
            if (str4 != null) {
                setVoipCallMicMode(Integer.parseInt(str4));
                return;
            }
            return;
        }
        if (i2 == 2) {
            String str5 = audioParameter.get("l_mic_input_control_mode_2mic");
            if (str5 != null) {
                setVoipCallMicMode(Integer.parseInt(str5));
                return;
            }
            return;
        }
        if (i2 != 4) {
            if (i2 != 5 || (str = audioParameter.get("l_call_nc_booster_enable")) == null) {
                return;
            }
            setCallMicMode(Integer.parseInt(str));
            return;
        }
        String str6 = audioParameter.get("l_call_nc_booster_enable");
        if (str6 != null) {
            setCallMicMode(Integer.parseInt(str6));
            return;
        }
        String str7 = audioParameter.get("l_mic_input_control_mode_2mic");
        if (str7 != null) {
            setVoipCallMicMode(Integer.parseInt(str7));
        }
    }

    public final void setCallMicMode(int i) {
        Log.i("MicModeManager", "setCallMicMode callMicMode = " + i);
        this.mCallMicMode = i;
        setSettingsInt("mic_mode_effect", i);
        setSettingsInt("call_mic_mode", i);
    }

    public final int getCallMicMode() {
        Log.i("MicModeManager", "getCallMicMode callMicMode = " + this.mCallMicMode);
        return this.mCallMicMode;
    }

    public final void setVoipCallMicMode(int i) {
        Log.i("MicModeManager", "setVoipCallMicMode voipCallMicMode = " + i);
        this.mVoipCallMicMode = i;
        setSettingsInt("mic_mode_effect", i);
        setSettingsInt("voip_call_mic_mode", i);
    }

    public final int getVoipCallMicMode() {
        Log.i("MicModeManager", "getVoipCallMicMode voipCallMicMode = " + this.mVoipCallMicMode);
        return this.mVoipCallMicMode;
    }

    public void restoreMicMode() {
        this.mCallMicMode = Settings.System.getInt(mCr, "call_mic_mode", 0);
        this.mVoipCallMicMode = Settings.System.getInt(mCr, "voip_call_mic_mode", 0);
        Log.i("MicModeManager", "restoreMicMode callMicMode = " + this.mCallMicMode + ", voipCallMicMode = " + this.mVoipCallMicMode);
        int i = this.mMicModeType;
        if (i == 6 || i == 3 || i == 2) {
            AudioSystem.setParameters((String) sMicModeParamTable.get(Integer.valueOf(this.mVoipCallMicMode)));
        } else {
            if (i == 5) {
                AudioSystem.setParameters((String) sMicModeParamTable.get(Integer.valueOf(this.mCallMicMode + 3)));
                return;
            }
            ArrayMap arrayMap = sMicModeParamTable;
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(this.mCallMicMode + 3)));
            AudioSystem.setParameters((String) arrayMap.get(Integer.valueOf(this.mVoipCallMicMode)));
        }
    }

    public void restoreVoipTranslator() {
        if (this.mIsVoiceCapable) {
            boolean z = Settings.System.getInt(mCr, "voip_translator_enable", 0) != 0;
            String string = Settings.System.getString(mCr, "voip_translator_package");
            Log.i("MicModeManager", "restoreVoipTranslator enabled = " + z + ", packageName = " + string);
            if (z) {
                setVoipTranslator(true, string);
            }
        }
    }

    public final void setSettingsStr(String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putString(mCr, str, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSettingsInt(String str, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putInt(mCr, str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("\nMicModeManager dump:");
        printWriter.println("  mMicModeType:" + micModeTypeToString(this.mMicModeType));
        printWriter.println("  mCallMicMode:" + callMicModeToString(this.mCallMicMode));
        printWriter.println("  mVoipCallMicMode:" + callMicModeToString(this.mVoipCallMicMode));
        printWriter.println("  mIsVoiceCapable:" + this.mIsVoiceCapable + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sMicModeLogger.dump(printWriter);
        printWriter.println();
        sVoipLiveTranslateLogger.dump(printWriter);
    }

    public final String callMicModeToString(int i) {
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
}
