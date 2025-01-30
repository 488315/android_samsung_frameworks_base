package com.samsung.android.server.audio;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Trace;
import android.util.Log;
import com.android.server.audio.AudioService;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.Rune;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SoundAppPolicyManager {
    public static SoundAppPolicyManager sInstance;
    public final ContentResolver mContentResolver;
    public final AudioSettingsHelper mSettingHelper;
    public String mToken = null;
    public int mVersion = 0;
    public List mAppList = null;
    public int mLiveTranslateAllowListVersion = -1;
    public List mLiveTranslateAllowList = null;

    public SoundAppPolicyManager(Context context, AudioSettingsHelper audioSettingsHelper) {
        this.mContentResolver = context.getContentResolver();
        this.mSettingHelper = audioSettingsHelper;
        init(context, true);
    }

    public static SoundAppPolicyManager getInstance(Context context, AudioSettingsHelper audioSettingsHelper) {
        if (sInstance == null) {
            sInstance = new SoundAppPolicyManager(context, audioSettingsHelper);
        }
        return sInstance;
    }

    public void init(final Context context, final boolean z) {
        this.mToken = null;
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() { // from class: com.samsung.android.server.audio.SoundAppPolicyManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SoundAppPolicyManager.this.lambda$init$0(context, z);
            }
        }, 60L, TimeUnit.SECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(Context context, boolean z) {
        if (isAvailable(context)) {
            Log.i("SoundAppPolicyManager", "init SCPMv2");
            if (register()) {
                checkAndUpdateAppList();
                if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                    checkAndUpdateLiveTranslateList(z);
                }
            }
        }
    }

    public final boolean isAvailable(Context context) {
        return context.getPackageManager().resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0) != null;
    }

    public final boolean register() {
        if (this.mToken != null) {
            return true;
        }
        Bundle bundle = new Bundle();
        bundle.putString("packageName", "android");
        bundle.putString("appId", "ifdzefg1lz");
        bundle.putString("version", ScpmConsumerInfo.VERSION);
        bundle.putString("receiverPackageName", "android");
        Bundle callScpmApi = callScpmApi(ScpmApiContract.URI, "register", bundle);
        if (callScpmApi == null) {
            AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("register : Fail to register, bundle is null"));
            return false;
        }
        this.mToken = callScpmApi.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN);
        AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Register, result=" + callScpmApi.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 2) + ", code=" + callScpmApi.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1) + ", msg=" + callScpmApi.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE)));
        return this.mToken != null;
    }

    public final ParcelFileDescriptor getScpmParcelFile(String str) {
        Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/" + this.mToken + "/" + str);
        try {
            ParcelFileDescriptor openFileDescriptor = this.mContentResolver.openFileDescriptor(parse, "r");
            if (openFileDescriptor != null) {
                return openFileDescriptor;
            }
            Bundle bundle = new Bundle();
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, this.mToken);
            Bundle callScpmApi = callScpmApi(parse, "getLastError", bundle);
            if (callScpmApi == null) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("getScpmFileDescriptor : bundle is null"));
                return null;
            }
            AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("getScpmParcelFile, code=" + callScpmApi.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1) + ", msg=" + callScpmApi.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE)));
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("getScpmParcelFile : Fail to update"));
            return null;
        }
    }

    public final Bundle callScpmApi(Uri uri, String str, Bundle bundle) {
        return this.mContentResolver.call(uri, str, "android", bundle);
    }

    public final void getDataForPolicyAudio() {
        Log.d("SoundAppPolicyManager", "getData(POLOCY_AUDIO)");
        if (!register()) {
            AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("getData(POLOCY_AUDIO) : Fail to register, token is null"));
            return;
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            try {
                parcelFileDescriptor = getScpmParcelFile("Audio");
            } catch (Exception e) {
                e.printStackTrace();
                if (0 == 0) {
                    return;
                }
            }
            if (parcelFileDescriptor == null) {
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                        return;
                    } catch (IOException unused) {
                        return;
                    }
                }
                return;
            }
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            if (fileDescriptor != null) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDescriptor));
                    try {
                        this.mAppList = new ArrayList();
                        boolean z = false;
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            String[] split = readLine.split(",");
                            if (z) {
                                Data data = new Data();
                                data.packageName = split[0];
                                data.categoryName = split[1];
                                this.mAppList.add(data);
                            } else {
                                this.mVersion = Integer.parseInt(split[1]);
                                Log.d("SoundAppPolicyManager", "getVersion = " + this.mVersion);
                                z = true;
                            }
                        }
                        bufferedReader.close();
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused2) {
            }
        } catch (Throwable th3) {
            if (0 != 0) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException unused3) {
                }
            }
            throw th3;
        }
    }

    public void checkAndUpdateAppList() {
        List list;
        Log.d("SoundAppPolicyManager", "checkAndUpdateAppList");
        try {
            getDataForPolicyAudio();
            int intValue = this.mSettingHelper.getIntValue("APP_LIST_VERSION", 0);
            if (intValue < this.mVersion && (list = this.mAppList) != null && list.size() > 0) {
                Log.i("SoundAppPolicyManager", "checkAndUpdateAppList update app list version = " + this.mVersion);
                this.mSettingHelper.resetAllowedListTable();
                for (Data data : this.mAppList) {
                    Log.d("SoundAppPolicyManager", "package = " + data.packageName + " categoryName = " + data.categoryName);
                    this.mSettingHelper.putAppList(data.packageName, data.categoryName);
                }
                this.mSettingHelper.setIntValue("APP_LIST_VERSION", this.mVersion);
                setBtGameLatencyList(this.mSettingHelper.getAppList());
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("checkAndUpdateAppList : Success to update version = " + this.mVersion));
            } else {
                Log.w("SoundAppPolicyManager", "App list is already latest version. Local version = " + intValue + " SCPM version = " + this.mVersion);
            }
        } finally {
            this.mAppList = null;
        }
    }

    public void setDefaultAllowList() {
        Trace.traceBegin(256L, "setDefaultAllowList");
        try {
            Log.i("SoundAppPolicyManager", "SoundAppPolicy setDefaultAllowList()");
            this.mSettingHelper.resetAllowedListTable();
            loadDefaultAllowedPackageList(R.array.common_nicknames, "bt_game_latency_deny");
            loadDefaultAllowedPackageList(R.array.config_allowedGlobalInstantAppSettings, "delay_loss_audio_focus");
            loadDefaultAllowedPackageList(R.array.config_allowedSecureInstantAppSettings, "karaoke_allow");
            loadDefaultAllowedPackageList(R.array.config_allowedSystemInstantAppSettings, "karaoke_listenback_allow");
            loadDefaultAllowedPackageList(R.array.config_ambientBrighteningThresholds, "media_button_deny");
            loadDefaultAllowedPackageList(R.array.config_ambientDarkeningThresholds, "virtual_vibration_sound_allowance");
            this.mSettingHelper.setIntValue("APP_LIST_VERSION", 2022070700);
        } finally {
            Trace.endSection();
        }
    }

    public final void loadDefaultAllowedPackageList(int i, String str) {
        for (String str2 : Resources.getSystem().getStringArray(i)) {
            this.mSettingHelper.putAppList(str2, str);
        }
    }

    public static void setBtGameLatencyList(Hashtable hashtable) {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("l_bt_game_latency");
        sb.append("=");
        for (Map.Entry entry : hashtable.entrySet()) {
            String str = (String) entry.getKey();
            if ("bt_game_latency_deny".equals((String) entry.getValue())) {
                sb.append(str);
                sb.append("|");
            }
        }
        Log.i("SoundAppPolicyManager", sb.toString());
        AudioSystem.setParameters(sb.toString());
    }

    public void checkAndUpdateLiveTranslateList(boolean z) {
        Log.d("SoundAppPolicyManager", "checkAndUpdateLiveTranslateList enforceSkip=" + z);
        try {
            if (z) {
                Log.d("SoundAppPolicyManager", "Skip updating live translate allow list! reason: enforceSkip");
                return;
            }
            if (!getDataForPolicyCall()) {
                Log.d("SoundAppPolicyManager", "Skip updating live translate allow list! reason: failed getData");
                return;
            }
            if (!this.mSettingHelper.isCallPolicyEmpty()) {
                this.mSettingHelper.resetCallPolicyTable();
            }
            for (Data data : this.mLiveTranslateAllowList) {
                Log.d("SoundAppPolicyManager", "package = " + data.packageName + " categoryName = " + data.categoryName);
                this.mSettingHelper.putCallPolicyAllowList(data.packageName, data.categoryName);
            }
            this.mSettingHelper.setIntValue("LIVE_TRANSLATE_ALLOW_LIST_VERSION", this.mLiveTranslateAllowListVersion);
            AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("checkAndUpdateLiveTranslateList : Success to update"));
        } finally {
            this.mLiveTranslateAllowList = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a5, code lost:
    
        if (0 == 0) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean getDataForPolicyCall() {
        Log.d("SoundAppPolicyManager", "getData(POLOCY_CALL)");
        if (!register()) {
            AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("getData(POLOCY_CALL) : Fail to register, token is null"));
            return false;
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            try {
                parcelFileDescriptor = getScpmParcelFile("voip-live-translate-allow-list-a7f6");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (parcelFileDescriptor == null) {
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException unused) {
                    }
                }
                return false;
            }
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            if (fileDescriptor != null) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDescriptor));
                    try {
                        this.mLiveTranslateAllowList = new ArrayList();
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                sb.append(readLine);
                            } else {
                                try {
                                    break;
                                } catch (JSONException e2) {
                                    Log.w("SoundAppPolicyManager", "getDataForPolicyCall: Failed to parse json string.", e2);
                                }
                            }
                        }
                        JSONArray jSONArray = new JSONObject(sb.toString()).getJSONArray("voip_allow_list");
                        for (int i = 0; i < jSONArray.length(); i++) {
                            Data data = new Data();
                            data.packageName = jSONArray.getString(i);
                            data.categoryName = "voip_live_translate_allow";
                            this.mLiveTranslateAllowList.add(data);
                        }
                        this.mLiveTranslateAllowListVersion = 1;
                        bufferedReader.close();
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused2) {
            }
            return true;
        } catch (Throwable th3) {
            if (0 != 0) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException unused3) {
                }
            }
            throw th3;
        }
    }

    public void setDefaultCallPolicyAllowList() {
        Trace.traceBegin(256L, "setDefaultCallPolicyAllowList");
        try {
            Log.i("SoundAppPolicyManager", "setDefaultCallPolicyAllowList()");
            this.mSettingHelper.resetCallPolicyTable();
            loadDefaultCallPolicyAllowList(R.array.config_ambientThresholdLevels, "voip_live_translate_allow");
            this.mSettingHelper.setIntValue("LIVE_TRANSLATE_ALLOW_LIST_VERSION", 0);
            this.mLiveTranslateAllowListVersion = 0;
        } finally {
            Trace.endSection();
        }
    }

    public final void loadDefaultCallPolicyAllowList(int i, String str) {
        for (String str2 : Resources.getSystem().getStringArray(i)) {
            this.mSettingHelper.putCallPolicyAllowList(str2, str);
        }
    }
}
