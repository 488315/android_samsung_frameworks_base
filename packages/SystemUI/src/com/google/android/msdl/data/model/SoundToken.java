package com.google.android.msdl.data.model;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SoundToken {
    public static final /* synthetic */ SoundToken[] $VALUES;
    public static final SoundToken CANCEL;
    public static final SoundToken DRAG_INDICATOR;
    public static final SoundToken DRAG_THRESHOLD_INDICATOR_LIMIT;
    public static final SoundToken FAILURE;
    public static final SoundToken FAILURE_HIGH_EMPHASIS;
    public static final SoundToken KEYPRESS_DELETE;
    public static final SoundToken KEYPRESS_RETURN;
    public static final SoundToken KEYPRESS_SPACEBAR;
    public static final SoundToken KEYPRESS_STANDARD;
    public static final SoundToken LOCK;
    public static final SoundToken LONG_PRESS;
    public static final SoundToken NO_SOUND;
    public static final SoundToken PAUSE;
    public static final SoundToken START;
    public static final SoundToken STOP;
    public static final SoundToken SUCCESS;
    public static final SoundToken SWIPE_THRESHOLD_INDICATOR;
    public static final SoundToken SWITCH_OFF;
    public static final SoundToken SWITCH_ON;
    public static final SoundToken TAP_HIGH_EMPHASIS;
    public static final SoundToken TAP_LOW_EMPHASIS;
    public static final SoundToken TAP_MEDIUM_EMPHASIS;
    public static final SoundToken UNLOCK;

    static {
        SoundToken soundToken = new SoundToken("FAILURE_HIGH_EMPHASIS", 0);
        FAILURE_HIGH_EMPHASIS = soundToken;
        SoundToken soundToken2 = new SoundToken("FAILURE", 1);
        FAILURE = soundToken2;
        SoundToken soundToken3 = new SoundToken(KnoxForesight.SUCCESS, 2);
        SUCCESS = soundToken3;
        SoundToken soundToken4 = new SoundToken("START", 3);
        START = soundToken4;
        SoundToken soundToken5 = new SoundToken("PAUSE", 4);
        PAUSE = soundToken5;
        SoundToken soundToken6 = new SoundToken("STOP", 5);
        STOP = soundToken6;
        SoundToken soundToken7 = new SoundToken("CANCEL", 6);
        CANCEL = soundToken7;
        SoundToken soundToken8 = new SoundToken("SWITCH_ON", 7);
        SWITCH_ON = soundToken8;
        SoundToken soundToken9 = new SoundToken("SWITCH_OFF", 8);
        SWITCH_OFF = soundToken9;
        SoundToken soundToken10 = new SoundToken("UNLOCK", 9);
        UNLOCK = soundToken10;
        SoundToken soundToken11 = new SoundToken("LOCK", 10);
        LOCK = soundToken11;
        SoundToken soundToken12 = new SoundToken("LONG_PRESS", 11);
        LONG_PRESS = soundToken12;
        SoundToken soundToken13 = new SoundToken("SWIPE_THRESHOLD_INDICATOR", 12);
        SWIPE_THRESHOLD_INDICATOR = soundToken13;
        SoundToken soundToken14 = new SoundToken("TAP_HIGH_EMPHASIS", 13);
        TAP_HIGH_EMPHASIS = soundToken14;
        SoundToken soundToken15 = new SoundToken("TAP_MEDIUM_EMPHASIS", 14);
        TAP_MEDIUM_EMPHASIS = soundToken15;
        SoundToken soundToken16 = new SoundToken("DRAG_THRESHOLD_INDICATOR_LIMIT", 15);
        DRAG_THRESHOLD_INDICATOR_LIMIT = soundToken16;
        SoundToken soundToken17 = new SoundToken("DRAG_INDICATOR", 16);
        DRAG_INDICATOR = soundToken17;
        SoundToken soundToken18 = new SoundToken("TAP_LOW_EMPHASIS", 17);
        TAP_LOW_EMPHASIS = soundToken18;
        SoundToken soundToken19 = new SoundToken("KEYPRESS_STANDARD", 18);
        KEYPRESS_STANDARD = soundToken19;
        SoundToken soundToken20 = new SoundToken("KEYPRESS_SPACEBAR", 19);
        KEYPRESS_SPACEBAR = soundToken20;
        SoundToken soundToken21 = new SoundToken("KEYPRESS_RETURN", 20);
        KEYPRESS_RETURN = soundToken21;
        SoundToken soundToken22 = new SoundToken("KEYPRESS_DELETE", 21);
        KEYPRESS_DELETE = soundToken22;
        SoundToken soundToken23 = new SoundToken("NO_SOUND", 22);
        NO_SOUND = soundToken23;
        SoundToken[] soundTokenArr = {soundToken, soundToken2, soundToken3, soundToken4, soundToken5, soundToken6, soundToken7, soundToken8, soundToken9, soundToken10, soundToken11, soundToken12, soundToken13, soundToken14, soundToken15, soundToken16, soundToken17, soundToken18, soundToken19, soundToken20, soundToken21, soundToken22, soundToken23};
        $VALUES = soundTokenArr;
        EnumEntriesKt.enumEntries(soundTokenArr);
    }

    private SoundToken(String str, int i) {
    }

    public static SoundToken valueOf(String str) {
        return (SoundToken) Enum.valueOf(SoundToken.class, str);
    }

    public static SoundToken[] values() {
        return (SoundToken[]) $VALUES.clone();
    }
}
