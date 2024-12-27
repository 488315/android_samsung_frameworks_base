package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.android.server.audio.AudioService;
import com.android.server.utils.EventLogger;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class SoundAppPolicyManager {
    public static SoundAppPolicyManager sInstance;
    public final ContentResolver mContentResolver;
    public final AudioSettingsHelper mSettingHelper;
    public String mToken;
    public int mVersion = 0;
    public List appList = null;
    public int mLiveTranslateAllowListVersion = -1;
    public List mLiveTranslateAllowList = null;

    public SoundAppPolicyManager(Context context, AudioSettingsHelper audioSettingsHelper) {
        this.mToken = null;
        this.mContentResolver = context.getContentResolver();
        this.mSettingHelper = audioSettingsHelper;
        this.mToken = null;
        Executors.newSingleThreadScheduledExecutor()
                .schedule(
                        new SoundAppPolicyManager$$ExternalSyntheticLambda0(this, context),
                        60L,
                        TimeUnit.SECONDS);
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

    public final void checkAndUpdateAppList() {
        List list;
        AudioSettingsHelper audioSettingsHelper = this.mSettingHelper;
        Log.d("SoundAppPolicyManager", "checkAndUpdateAppList");
        try {
            getData();
            int intValue = audioSettingsHelper.getIntValue(0, "APP_LIST_VERSION");
            if (intValue >= this.mVersion
                    || (list = this.appList) == null
                    || ((ArrayList) list).isEmpty()) {
                Log.w(
                        "SoundAppPolicyManager",
                        "App list is already latest version. Local version = "
                                + intValue
                                + " SCPM version = "
                                + this.mVersion);
            } else {
                Log.i(
                        "SoundAppPolicyManager",
                        "checkAndUpdateAppList update app list version = " + this.mVersion);
                audioSettingsHelper.resetAllowedListTable();
                Iterator it = ((ArrayList) this.appList).iterator();
                while (it.hasNext()) {
                    Data data = (Data) it.next();
                    Log.d(
                            "SoundAppPolicyManager",
                            "package = "
                                    + data.packageName
                                    + " categoryName = "
                                    + data.categoryName);
                    String str = data.packageName;
                    String str2 = data.categoryName;
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_package", str);
                    contentValues.put("_category", str2);
                    audioSettingsHelper.add("category_packages", contentValues);
                }
                audioSettingsHelper.setIntValue(this.mVersion, "APP_LIST_VERSION");
                setBtGameLatencyList(audioSettingsHelper.getAppList());
                AudioService.sScpmLogger.enqueue(
                        new EventLogger.StringEvent(
                                "checkAndUpdateAppList : Success to update version = "
                                        + this.mVersion));
            }
            this.appList = null;
        } catch (Throwable th) {
            this.appList = null;
            throw th;
        }
    }

    public final void checkAndUpdateLiveTranslateList() {
        AudioSettingsHelper audioSettingsHelper = this.mSettingHelper;
        Log.d("SoundAppPolicyManager", "checkAndUpdateLiveTranslateList enforceSkip=false");
        try {
            if (!getDataForPolicyCall()) {
                Log.d(
                        "SoundAppPolicyManager",
                        "Skip updating live translate allow list! reason: failed getData");
                return;
            }
            if (!audioSettingsHelper.mCallPolicyAllowList.isEmpty()) {
                audioSettingsHelper.resetCallPolicyTable();
            }
            Iterator it = ((ArrayList) this.mLiveTranslateAllowList).iterator();
            while (it.hasNext()) {
                Data data = (Data) it.next();
                Log.d(
                        "SoundAppPolicyManager",
                        "package = " + data.packageName + " categoryName = " + data.categoryName);
                audioSettingsHelper.putCallPolicyAllowList(data.packageName, data.categoryName);
            }
            audioSettingsHelper.setIntValue(
                    this.mLiveTranslateAllowListVersion, "LIVE_TRANSLATE_ALLOW_LIST_VERSION");
            AudioService.sScpmLogger.enqueue(
                    new EventLogger.StringEvent(
                            "checkAndUpdateLiveTranslateList : Success to update"));
        } finally {
            this.mLiveTranslateAllowList = null;
        }
    }

    public final void getData() {
        Log.d("SoundAppPolicyManager", "getData");
        if (!register()) {
            AudioService.sScpmLogger.enqueue(
                    new EventLogger.StringEvent("getData : Fail to register, token is null"));
            return;
        }
        try {
            ParcelFileDescriptor scpmParcelFile = getScpmParcelFile("Audio");
            if (scpmParcelFile == null) {
                if (scpmParcelFile != null) {
                    scpmParcelFile.close();
                    return;
                }
                return;
            }
            try {
                FileDescriptor fileDescriptor = scpmParcelFile.getFileDescriptor();
                if (fileDescriptor != null) {
                    try {
                        BufferedReader bufferedReader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                new FileInputStream(fileDescriptor),
                                                StandardCharsets.UTF_8));
                        try {
                            this.appList = new ArrayList();
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
                                    this.appList.add(data);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                scpmParcelFile.close();
            } finally {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00aa, code lost:

       if (0 == 0) goto L51;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean getDataForPolicyCall() {
        /*
            r8 = this;
            java.lang.String r0 = "getData(POLOCY_CALL)"
            java.lang.String r1 = "SoundAppPolicyManager"
            android.util.Log.d(r1, r0)
            boolean r0 = r8.register()
            r2 = 0
            if (r0 != 0) goto L1b
            com.android.server.utils.EventLogger r8 = com.android.server.audio.AudioService.sScpmLogger
            com.android.server.utils.EventLogger$StringEvent r0 = new com.android.server.utils.EventLogger$StringEvent
            java.lang.String r1 = "getData(POLOCY_CALL) : Fail to register, token is null"
            r0.<init>(r1)
            r8.enqueue(r0)
            return r2
        L1b:
            r0 = 1
            r3 = 0
            java.lang.String r4 = "voip-live-translate-allow-list-a7f6"
            android.os.ParcelFileDescriptor r3 = r8.getScpmParcelFile(r4)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> La1
            if (r3 != 0) goto L2c
            if (r3 == 0) goto L2b
            r3.close()     // Catch: java.io.IOException -> L2b
        L2b:
            return r2
        L2c:
            java.io.FileDescriptor r4 = r3.getFileDescriptor()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> La1
            if (r4 == 0) goto La3
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
            r6.<init>(r4)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L52
            r4.<init>()     // Catch: java.lang.Throwable -> L52
            r8.mLiveTranslateAllowList = r4     // Catch: java.lang.Throwable -> L52
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L52
            r4.<init>()     // Catch: java.lang.Throwable -> L52
        L48:
            java.lang.String r6 = r5.readLine()     // Catch: java.lang.Throwable -> L52
            if (r6 == 0) goto L54
            r4.append(r6)     // Catch: java.lang.Throwable -> L52
            goto L48
        L52:
            r8 = move-exception
            goto L94
        L54:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            r6.<init>(r4)     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            java.lang.String r4 = "voip_allow_list"
            org.json.JSONArray r4 = r6.getJSONArray(r4)     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
        L64:
            int r6 = r4.length()     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            if (r2 >= r6) goto L84
            com.samsung.android.server.audio.Data r6 = new com.samsung.android.server.audio.Data     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            r6.<init>()     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            java.lang.String r7 = r4.getString(r2)     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            r6.packageName = r7     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            java.lang.String r7 = "voip_live_translate_allow"
            r6.categoryName = r7     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            java.util.List r7 = r8.mLiveTranslateAllowList     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            r7.add(r6)     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            int r2 = r2 + 1
            goto L64
        L82:
            r8 = move-exception
            goto L87
        L84:
            r8.mLiveTranslateAllowListVersion = r0     // Catch: java.lang.Throwable -> L52 org.json.JSONException -> L82
            goto L8c
        L87:
            java.lang.String r2 = "getDataForPolicyCall: Failed to parse json string."
            android.util.Log.w(r1, r2, r8)     // Catch: java.lang.Throwable -> L52
        L8c:
            r5.close()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
            goto La3
        L90:
            r8 = move-exception
            goto Lae
        L92:
            r8 = move-exception
            goto L9d
        L94:
            r5.close()     // Catch: java.lang.Throwable -> L98
            goto L9c
        L98:
            r1 = move-exception
            r8.addSuppressed(r1)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
        L9c:
            throw r8     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L92
        L9d:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> La1
            goto La3
        La1:
            r8 = move-exception
            goto La7
        La3:
            r3.close()     // Catch: java.io.IOException -> Lad
            goto Lad
        La7:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L90
            if (r3 == 0) goto Lad
            goto La3
        Lad:
            return r0
        Lae:
            if (r3 == 0) goto Lb3
            r3.close()     // Catch: java.io.IOException -> Lb3
        Lb3:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.audio.SoundAppPolicyManager.getDataForPolicyCall():boolean");
    }

    public final ParcelFileDescriptor getScpmParcelFile(String str) {
        Uri parse =
                Uri.parse("content://com.samsung.android.scpm.policy/" + this.mToken + "/" + str);
        try {
            ParcelFileDescriptor openFileDescriptor =
                    this.mContentResolver.openFileDescriptor(parse, "r");
            if (openFileDescriptor != null) {
                return openFileDescriptor;
            }
            Bundle bundle = new Bundle();
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, this.mToken);
            Bundle call = this.mContentResolver.call(parse, "getLastError", "android", bundle);
            if (call == null) {
                AudioService.sScpmLogger.enqueue(
                        new EventLogger.StringEvent("getScpmFileDescriptor : bundle is null"));
                return null;
            }
            AudioService.sScpmLogger.enqueue(
                    new EventLogger.StringEvent(
                            "getScpmParcelFile, code="
                                    + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1)
                                    + ", msg="
                                    + call.getString(
                                            KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE)));
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            AudioService.sScpmLogger.enqueue(
                    new EventLogger.StringEvent("getScpmParcelFile : Fail to update"));
            return null;
        }
    }

    public final void loadDefaultAllowedPackageList(int i, String str) {
        for (String str2 : Resources.getSystem().getStringArray(i)) {
            AudioSettingsHelper audioSettingsHelper = this.mSettingHelper;
            audioSettingsHelper.getClass();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_package", str2);
            contentValues.put("_category", str);
            audioSettingsHelper.add("category_packages", contentValues);
        }
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
        Bundle call =
                this.mContentResolver.call(ScpmApiContract.URI, "register", "android", bundle);
        if (call == null) {
            AudioService.sScpmLogger.enqueue(
                    new EventLogger.StringEvent("register : Fail to register, bundle is null"));
            return false;
        }
        this.mToken = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN);
        AudioService.sScpmLogger.enqueue(
                new EventLogger.StringEvent(
                        "Register, result="
                                + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 2)
                                + ", code="
                                + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1)
                                + ", msg="
                                + call.getString(
                                        KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE)));
        return this.mToken != null;
    }
}
