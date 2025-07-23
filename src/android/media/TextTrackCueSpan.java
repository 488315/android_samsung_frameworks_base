package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class TextTrackCueSpan {
    boolean mEnabled;
    String mText;
    long mTimestampMs;

    TextTrackCueSpan(String str, long j) {
        this.mTimestampMs = j;
        this.mText = str;
        this.mEnabled = j < 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TextTrackCueSpan)) {
            return false;
        }
        TextTrackCueSpan textTrackCueSpan = (TextTrackCueSpan) obj;
        return this.mTimestampMs == textTrackCueSpan.mTimestampMs && this.mText.equals(textTrackCueSpan.mText);
    }
}
