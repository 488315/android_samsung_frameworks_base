package com.android.systemui.edgelighting.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.util.SemLog;
import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TalkBackUtil implements TextToSpeech.OnInitListener {
    public static TalkBackUtil mInstance;
    public final AccessibilityManager mAccessibilityManager;
    public final Context mContext;
    public boolean mIsTalkbackMode = false;

    private TalkBackUtil(Context context) {
        new UtteranceProgressListener() { // from class: com.android.systemui.edgelighting.utils.TalkBackUtil.1
            @Override // android.speech.tts.UtteranceProgressListener
            public final void onDone(String str) {
                if (str.equals("TTS_END")) {
                    SemLog.v("TalkBackUtils", "TTS speaking is done!!!");
                    TalkBackUtil.this.getClass();
                    SemLog.v("TalkBackUtils", "TTS stop!!!");
                }
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public final void onError(String str) {
                if (str.equals("TTS_END")) {
                    SemLog.e("TalkBackUtils", "Error occured during TTS speaks!!!");
                    TalkBackUtil.this.getClass();
                    SemLog.v("TalkBackUtils", "TTS stop!!!");
                }
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public final void onStart(String str) {
                SemLog.v("TalkBackUtils", "TTS speaks now!!!");
            }
        };
        this.mContext = context;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES), false, new ContentObserver(null) { // from class: com.android.systemui.edgelighting.utils.TalkBackUtil.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                super.onChange(z);
                TalkBackUtil.this.setTalkBackMode();
            }
        });
        setTalkBackMode();
        SemLog.d("TalkBackUtils", "TalkBackUtil instance create!!");
    }

    public static TalkBackUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TalkBackUtil(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override // android.speech.tts.TextToSpeech.OnInitListener
    public final void onInit(int i) {
        if (i == 0) {
            Locale locale = this.mContext.getResources().getConfiguration().locale;
        } else if (i == -1) {
            SemLog.d("TalkBackUtils", "Do not init TTS!!");
        }
    }

    public final void setTalkBackMode() {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES);
        boolean z = false;
        if (string != null && (string.matches("(?i).*com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.android.marvin.talkback.TalkBackService.*"))) {
            z = true;
        }
        this.mIsTalkbackMode = z;
    }
}
