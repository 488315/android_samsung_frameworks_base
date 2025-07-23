package android.media;

import android.media.Tokenizer;
import java.util.Vector;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class UnstyledTextExtractor implements Tokenizer.OnTokenListener {
    long mLastTimestamp;
    StringBuilder mLine = new StringBuilder();
    Vector<TextTrackCueSpan[]> mLines = new Vector<>();
    Vector<TextTrackCueSpan> mCurrentLine = new Vector<>();

    @Override // android.media.Tokenizer.OnTokenListener
    public void onEnd(String str) {
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onStart(String str, String[] strArr, String str2) {
    }

    UnstyledTextExtractor() {
        init();
    }

    private void init() {
        StringBuilder sb = this.mLine;
        sb.delete(0, sb.length());
        this.mLines.clear();
        this.mCurrentLine.clear();
        this.mLastTimestamp = -1L;
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onData(String str) {
        this.mLine.append(str);
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onTimeStamp(long j) {
        if (this.mLine.length() > 0 && j != this.mLastTimestamp) {
            this.mCurrentLine.add(new TextTrackCueSpan(this.mLine.toString(), this.mLastTimestamp));
            StringBuilder sb = this.mLine;
            sb.delete(0, sb.length());
        }
        this.mLastTimestamp = j;
    }

    @Override // android.media.Tokenizer.OnTokenListener
    public void onLineEnd() {
        if (this.mLine.length() > 0) {
            this.mCurrentLine.add(new TextTrackCueSpan(this.mLine.toString(), this.mLastTimestamp));
            StringBuilder sb = this.mLine;
            sb.delete(0, sb.length());
        }
        TextTrackCueSpan[] textTrackCueSpanArr = new TextTrackCueSpan[this.mCurrentLine.size()];
        this.mCurrentLine.toArray(textTrackCueSpanArr);
        this.mCurrentLine.clear();
        this.mLines.add(textTrackCueSpanArr);
    }

    public TextTrackCueSpan[][] getText() {
        if (this.mLine.length() > 0 || this.mCurrentLine.size() > 0) {
            onLineEnd();
        }
        TextTrackCueSpan[][] textTrackCueSpanArr = new TextTrackCueSpan[this.mLines.size()][];
        this.mLines.toArray(textTrackCueSpanArr);
        init();
        return textTrackCueSpanArr;
    }
}
