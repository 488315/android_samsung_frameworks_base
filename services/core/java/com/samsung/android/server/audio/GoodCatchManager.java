package com.samsung.android.server.audio;

import android.content.Context;
import android.text.TextUtils;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.sepunion.SemGoodCatchManager;

public final class GoodCatchManager {
    public static final String[] SOUND_FUNC = {"ringermode", "callmode", "mediavolume", "playback"};
    public final AnonymousClass1 mAudioStateListener;
    public final String mModule;
    public final SemGoodCatchManager mSemGoodCatchManager;
    public final AnonymousClass1 mVibrateStateListener;
    public final boolean[] mSoundFunc = {false, false, false, false};
    public boolean mVibrationFunc = false;

    public GoodCatchManager(Context context, String str) {
        final int i = 0;
        SemGoodCatchManager.OnStateChangeListener onStateChangeListener =
                new SemGoodCatchManager.OnStateChangeListener(
                        this) { // from class: com.samsung.android.server.audio.GoodCatchManager.1
                    public final /* synthetic */ GoodCatchManager this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onStart(String str2) {
                        switch (i) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStart ", str2, "AS.GoodCatchManager");
                                String[] strArr = GoodCatchManager.SOUND_FUNC;
                                if (!strArr[0].equals(str2)) {
                                    if (!strArr[1].equals(str2)) {
                                        if (!strArr[2].equals(str2)) {
                                            if (strArr[3].equals(str2)) {
                                                this.this$0.mSoundFunc[3] = true;
                                                break;
                                            }
                                        } else {
                                            this.this$0.mSoundFunc[2] = true;
                                            break;
                                        }
                                    } else {
                                        this.this$0.mSoundFunc[1] = true;
                                        break;
                                    }
                                } else {
                                    this.this$0.mSoundFunc[0] = true;
                                    break;
                                }
                                break;
                            default:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStart ", str2, "AS.GoodCatchManager");
                                this.this$0.mVibrationFunc = true;
                                break;
                        }
                    }

                    public final void onStop(String str2) {
                        switch (i) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStop ", str2, "AS.GoodCatchManager");
                                String[] strArr = GoodCatchManager.SOUND_FUNC;
                                if (!strArr[0].equals(str2)) {
                                    if (!strArr[1].equals(str2)) {
                                        if (!strArr[2].equals(str2)) {
                                            if (strArr[3].equals(str2)) {
                                                this.this$0.mSoundFunc[3] = false;
                                                break;
                                            }
                                        } else {
                                            this.this$0.mSoundFunc[2] = false;
                                            break;
                                        }
                                    } else {
                                        this.this$0.mSoundFunc[1] = false;
                                        break;
                                    }
                                } else {
                                    this.this$0.mSoundFunc[0] = false;
                                    break;
                                }
                                break;
                            default:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStop ", str2, "AS.GoodCatchManager");
                                this.this$0.mVibrationFunc = false;
                                break;
                        }
                    }
                };
        final int i2 = 1;
        SemGoodCatchManager.OnStateChangeListener onStateChangeListener2 =
                new SemGoodCatchManager.OnStateChangeListener(
                        this) { // from class: com.samsung.android.server.audio.GoodCatchManager.1
                    public final /* synthetic */ GoodCatchManager this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onStart(String str2) {
                        switch (i2) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStart ", str2, "AS.GoodCatchManager");
                                String[] strArr = GoodCatchManager.SOUND_FUNC;
                                if (!strArr[0].equals(str2)) {
                                    if (!strArr[1].equals(str2)) {
                                        if (!strArr[2].equals(str2)) {
                                            if (strArr[3].equals(str2)) {
                                                this.this$0.mSoundFunc[3] = true;
                                                break;
                                            }
                                        } else {
                                            this.this$0.mSoundFunc[2] = true;
                                            break;
                                        }
                                    } else {
                                        this.this$0.mSoundFunc[1] = true;
                                        break;
                                    }
                                } else {
                                    this.this$0.mSoundFunc[0] = true;
                                    break;
                                }
                                break;
                            default:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStart ", str2, "AS.GoodCatchManager");
                                this.this$0.mVibrationFunc = true;
                                break;
                        }
                    }

                    public final void onStop(String str2) {
                        switch (i2) {
                            case 0:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStop ", str2, "AS.GoodCatchManager");
                                String[] strArr = GoodCatchManager.SOUND_FUNC;
                                if (!strArr[0].equals(str2)) {
                                    if (!strArr[1].equals(str2)) {
                                        if (!strArr[2].equals(str2)) {
                                            if (strArr[3].equals(str2)) {
                                                this.this$0.mSoundFunc[3] = false;
                                                break;
                                            }
                                        } else {
                                            this.this$0.mSoundFunc[2] = false;
                                            break;
                                        }
                                    } else {
                                        this.this$0.mSoundFunc[1] = false;
                                        break;
                                    }
                                } else {
                                    this.this$0.mSoundFunc[0] = false;
                                    break;
                                }
                                break;
                            default:
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        "onStop ", str2, "AS.GoodCatchManager");
                                this.this$0.mVibrationFunc = false;
                                break;
                        }
                    }
                };
        this.mModule = str;
        if (TextUtils.equals("AudioService", str)) {
            this.mSemGoodCatchManager =
                    new SemGoodCatchManager(
                            context, "AudioService", SOUND_FUNC, onStateChangeListener);
        } else if (TextUtils.equals("VibratorService", str)) {
            this.mSemGoodCatchManager =
                    new SemGoodCatchManager(
                            context,
                            "VibratorService",
                            new String[] {"vibration"},
                            onStateChangeListener2);
        }
    }
}
