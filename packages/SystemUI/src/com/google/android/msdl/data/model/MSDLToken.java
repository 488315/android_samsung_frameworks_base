package com.google.android.msdl.data.model;

import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class MSDLToken {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ MSDLToken[] $VALUES;
    public static final MSDLToken KEYPRESS_DELETE = null;
    public static final MSDLToken LONG_PRESS;
    public static final MSDLToken SWIPE_THRESHOLD_INDICATOR;
    public static final MSDLToken SWITCH_OFF;
    public static final MSDLToken SWITCH_ON;
    private final HapticToken hapticToken;
    private final FeedbackLevel minimumFeedbackLevel;
    private final SoundToken soundToken;

    static {
        HapticToken hapticToken = HapticToken.NEGATIVE_CONFIRMATION_HIGH_EMPHASIS;
        SoundToken soundToken = SoundToken.FAILURE_HIGH_EMPHASIS;
        FeedbackLevel feedbackLevel = FeedbackLevel.MINIMAL;
        MSDLToken mSDLToken = new MSDLToken("FAILURE_HIGH_EMPHASIS", 0, hapticToken, soundToken, feedbackLevel);
        MSDLToken mSDLToken2 = new MSDLToken("FAILURE", 1, HapticToken.NEGATIVE_CONFIRMATION_MEDIUM_EMPHASIS, SoundToken.FAILURE, feedbackLevel);
        MSDLToken mSDLToken3 = new MSDLToken(KnoxForesight.SUCCESS, 2, HapticToken.POSITIVE_CONFIRMATION_HIGH_EMPHASIS, SoundToken.SUCCESS, feedbackLevel);
        HapticToken hapticToken2 = HapticToken.NEUTRAL_CONFIRMATION_HIGH_EMPHASIS;
        SoundToken soundToken2 = SoundToken.START;
        FeedbackLevel feedbackLevel2 = FeedbackLevel.DEFAULT;
        MSDLToken mSDLToken4 = new MSDLToken("START", 3, hapticToken2, soundToken2, feedbackLevel2);
        MSDLToken mSDLToken5 = new MSDLToken("PAUSE", 4, HapticToken.NEUTRAL_CONFIRMATION_MEDIUM_EMPHASIS, SoundToken.PAUSE, feedbackLevel2);
        HapticToken hapticToken3 = HapticToken.POSITIVE_CONFIRMATION_MEDIUM_EMPHASIS;
        MSDLToken mSDLToken6 = new MSDLToken("STOP", 5, hapticToken3, SoundToken.STOP, feedbackLevel2);
        MSDLToken mSDLToken7 = new MSDLToken("CANCEL", 6, hapticToken3, SoundToken.CANCEL, feedbackLevel2);
        MSDLToken mSDLToken8 = new MSDLToken("SWITCH_ON", 7, hapticToken3, SoundToken.SWITCH_ON, feedbackLevel2);
        SWITCH_ON = mSDLToken8;
        MSDLToken mSDLToken9 = new MSDLToken("SWITCH_OFF", 8, hapticToken3, SoundToken.SWITCH_OFF, feedbackLevel2);
        SWITCH_OFF = mSDLToken9;
        HapticToken hapticToken4 = HapticToken.POSITIVE_CONFIRMATION_LOW_EMPHASIS;
        MSDLToken mSDLToken10 = new MSDLToken("UNLOCK", 9, hapticToken4, SoundToken.UNLOCK, feedbackLevel2);
        MSDLToken mSDLToken11 = new MSDLToken("LOCK", 10, hapticToken4, SoundToken.LOCK, feedbackLevel2);
        MSDLToken mSDLToken12 = new MSDLToken("LONG_PRESS", 11, HapticToken.LONG_PRESS, SoundToken.LONG_PRESS, feedbackLevel);
        LONG_PRESS = mSDLToken12;
        MSDLToken mSDLToken13 = new MSDLToken("SWIPE_THRESHOLD_INDICATOR", 12, HapticToken.SWIPE_THRESHOLD_INDICATOR, SoundToken.SWIPE_THRESHOLD_INDICATOR, feedbackLevel);
        SWIPE_THRESHOLD_INDICATOR = mSDLToken13;
        HapticToken hapticToken5 = HapticToken.TAP_HIGH_EMPHASIS;
        SoundToken soundToken3 = SoundToken.TAP_HIGH_EMPHASIS;
        FeedbackLevel feedbackLevel3 = FeedbackLevel.EXPRESSIVE;
        MSDLToken[] mSDLTokenArr = {mSDLToken, mSDLToken2, mSDLToken3, mSDLToken4, mSDLToken5, mSDLToken6, mSDLToken7, mSDLToken8, mSDLToken9, mSDLToken10, mSDLToken11, mSDLToken12, mSDLToken13, new MSDLToken("TAP_HIGH_EMPHASIS", 13, hapticToken5, soundToken3, feedbackLevel3), new MSDLToken("TAP_MEDIUM_EMPHASIS", 14, HapticToken.TAP_MEDIUM_EMPHASIS, SoundToken.TAP_MEDIUM_EMPHASIS, feedbackLevel2), new MSDLToken("DRAG_THRESHOLD_INDICATOR_LIMIT", 15, HapticToken.DRAG_THRESHOLD_INDICATOR, SoundToken.DRAG_THRESHOLD_INDICATOR_LIMIT, feedbackLevel2), new MSDLToken("DRAG_INDICATOR_CONTINUOUS", 16, HapticToken.DRAG_INDICATOR_CONTINUOUS, SoundToken.NO_SOUND, feedbackLevel2), new MSDLToken("DRAG_INDICATOR_DISCRETE", 17, HapticToken.DRAG_INDICATOR_DISCRETE, SoundToken.DRAG_INDICATOR, feedbackLevel2), new MSDLToken("TAP_LOW_EMPHASIS", 18, HapticToken.TAP_LOW_EMPHASIS, SoundToken.TAP_LOW_EMPHASIS, feedbackLevel3), new MSDLToken("KEYPRESS_STANDARD", 19, HapticToken.KEYPRESS_STANDARD, SoundToken.KEYPRESS_STANDARD, feedbackLevel2), new MSDLToken("KEYPRESS_SPACEBAR", 20, HapticToken.KEYPRESS_SPACEBAR, SoundToken.KEYPRESS_SPACEBAR, feedbackLevel2), new MSDLToken("KEYPRESS_RETURN", 21, HapticToken.KEYPRESS_RETURN, SoundToken.KEYPRESS_RETURN, feedbackLevel2), new MSDLToken("KEYPRESS_DELETE", 22, HapticToken.KEYPRESS_DELETE, SoundToken.KEYPRESS_DELETE, feedbackLevel2)};
        $VALUES = mSDLTokenArr;
        $ENTRIES = EnumEntriesKt.enumEntries(mSDLTokenArr);
    }

    private MSDLToken(String str, int i, HapticToken hapticToken, SoundToken soundToken, FeedbackLevel feedbackLevel) {
        this.hapticToken = hapticToken;
        this.soundToken = soundToken;
        this.minimumFeedbackLevel = feedbackLevel;
    }

    public static MSDLToken valueOf(String str) {
        return (MSDLToken) Enum.valueOf(MSDLToken.class, str);
    }

    public static MSDLToken[] values() {
        return (MSDLToken[]) $VALUES.clone();
    }

    public final HapticToken getHapticToken() {
        return this.hapticToken;
    }

    public final FeedbackLevel getMinimumFeedbackLevel() {
        return this.minimumFeedbackLevel;
    }
}
