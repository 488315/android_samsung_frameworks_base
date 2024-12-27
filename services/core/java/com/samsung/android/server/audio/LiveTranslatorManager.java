package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioSystem;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import com.samsung.android.server.audio.utils.AudioUtils;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LiveTranslatorManager {
    public static ContentResolver mCr;
    public static LiveTranslatorManager sInstance;
    public static final EventLogger sVoipLiveTranslateLogger =
            new EventLogger(30, "Live translate history");
    public final AudioSettingsHelper mAudioSettingsHelper;
    public final Uri mCallAssistantUri;
    public boolean mIsLiveTranslateOn;
    public final boolean mIsVoiceCapable;
    public String mPackageName = "";
    public String mVoipPackageName = "";
    public int mCurAudioMode = 0;
    public int mStates = 0;

    public LiveTranslatorManager(Context context) {
        boolean z = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null && telephonyManager.isVoiceCapable()) {
            z = true;
        }
        this.mIsVoiceCapable = z;
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
        mCr = context.getContentResolver();
        this.mCallAssistantUri =
                Uri.parse("content://com.samsung.android.callassistant.voipprovider");
    }

    public final void setVoipTranslator(String str, boolean z) {
        boolean z2;
        EventLogger eventLogger = sVoipLiveTranslateLogger;
        if (z) {
            try {
                z2 =
                        mCr.call(
                                        this.mCallAssistantUri,
                                        "isSupportedVoipTranslation",
                                        str,
                                        (Bundle) null)
                                .getBoolean("is_support_voip_translation");
            } catch (Exception e) {
                Log.e("LiveTranslatorManager", e.getMessage());
                z2 = false;
            }
            if (z2) {
                String m =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "l_voip_translate_package_name=", str);
                eventLogger.enqueue(new EventLogger.StringEvent(m));
                AudioSystem.setParameters(m);
            }
        } else {
            z2 = false;
        }
        this.mIsLiveTranslateOn = z && z2;
        StringBuilder m2 =
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "setVoipTranslator enable ", z, ", supported ", z2, ", mode ");
        m2.append(this.mCurAudioMode);
        m2.append(", ");
        m2.append(str);
        eventLogger.enqueueAndLog(0, m2.toString(), "LiveTranslatorManager");
        ContentResolver contentResolver = mCr;
        Set set = AudioUtils.DEVICE_OUT_WIRED_DEVICE_SET;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putString(contentResolver, "voip_translator_package", str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            AudioUtils.setSettingsInt(
                    mCr, "voip_translator_enable", this.mIsLiveTranslateOn ? 1 : 0);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateAudioMode(int i, String str) {
        this.mPackageName = str;
        this.mCurAudioMode = i;
        Log.i(
                "LiveTranslatorManager",
                "updateAudioMode() audioMode=" + this.mCurAudioMode + ", caller=" + str);
        updateVoipTranslator();
        if (this.mCurAudioMode == 0) {
            this.mPackageName = "";
            this.mVoipPackageName = "";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a5, code lost:

       if (r1 != false) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVoipTranslator() {
        /*
            r7 = this;
            boolean r0 = r7.mIsVoiceCapable
            if (r0 == 0) goto Lbf
            java.lang.String r0 = "com.android.server.telecom"
            java.lang.String r1 = r7.mPackageName
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L1c
            java.lang.String r0 = "android"
            java.lang.String r1 = r7.mPackageName
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L19
            goto L1c
        L19:
            java.lang.String r0 = r7.mPackageName
            goto L1e
        L1c:
            java.lang.String r0 = r7.mVoipPackageName
        L1e:
            android.content.ContentResolver r1 = com.samsung.android.server.audio.LiveTranslatorManager.mCr
            java.lang.String r2 = "translate_during_calls"
            r3 = 1
            int r1 = android.provider.Settings.Global.getInt(r1, r2, r3)
            r2 = 0
            if (r1 == 0) goto La8
            int r1 = r7.mCurAudioMode
            r4 = 3
            if (r1 != r4) goto La8
            int r1 = r7.mStates
            r1 = r1 & r3
            if (r1 <= 0) goto L37
            goto La8
        L37:
            android.content.ContentResolver r1 = com.samsung.android.server.audio.LiveTranslatorManager.mCr
            java.lang.String r4 = "translate_during_allow_apps"
            java.lang.String r1 = android.provider.Settings.Global.getString(r1, r4)
            if (r1 != 0) goto L44
            r1 = 0
            goto L4a
        L44:
            java.lang.String r4 = ";"
            java.lang.String[] r1 = r1.split(r4)
        L4a:
            java.lang.String r4 = "LiveTranslatorManager"
            if (r1 != 0) goto L6a
            com.samsung.android.server.audio.AudioSettingsHelper r1 = r7.mAudioSettingsHelper
            java.util.concurrent.CopyOnWriteArrayList r5 = r1.mCallPolicyAllowList
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L5a
            r1 = r2
            goto L8e
        L5a:
            java.util.concurrent.CopyOnWriteArrayList r1 = r1.mCallPolicyAllowList
            java.util.stream.Stream r1 = r1.stream()
            com.samsung.android.server.audio.AudioSettingsHelper$$ExternalSyntheticLambda1 r5 = new com.samsung.android.server.audio.AudioSettingsHelper$$ExternalSyntheticLambda1
            r5.<init>(r0)
            boolean r1 = r1.anyMatch(r5)
            goto L8e
        L6a:
            java.util.List r1 = java.util.Arrays.asList(r1)
            boolean r1 = r1.contains(r0)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "voip allowPackages from globalDB packageName: "
            r5.<init>(r6)
            r5.append(r0)
            java.lang.String r6 = ", result:"
            r5.append(r6)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            com.android.server.utils.EventLogger r6 = com.samsung.android.server.audio.LiveTranslatorManager.sVoipLiveTranslateLogger
            r6.enqueueAndLog(r2, r5, r4)
        L8e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r0)
            java.lang.String r6 = " is supported voip "
            r5.append(r6)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
            if (r1 == 0) goto La8
            goto La9
        La8:
            r3 = r2
        La9:
            android.content.ContentResolver r1 = com.samsung.android.server.audio.LiveTranslatorManager.mCr
            java.lang.String r2 = "voip_translator_package"
            java.lang.String r1 = android.provider.Settings.System.getString(r1, r2)
            boolean r2 = r7.mIsLiveTranslateOn
            if (r2 != r3) goto Lbc
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto Lbf
        Lbc:
            r7.setVoipTranslator(r0, r3)
        Lbf:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.audio.LiveTranslatorManager.updateVoipTranslator():void");
    }
}
