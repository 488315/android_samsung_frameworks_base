package android.media;

import android.media.SubtitleTrack;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlCue extends SubtitleTrack.Cue {
    public String mText;
    public String mTtmlFragment;

    public TtmlCue(long j, long j2, String str, String str2, long j3) {
        this.mStartTimeMs = j;
        this.mEndTimeMs = j2;
        this.mRunID = j3;
        this.mText = str;
        this.mTtmlFragment = str2;
    }
}
