package android.media;

import android.media.SubtitleTrack;
import java.util.Vector;

/* compiled from: Cea708CaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea708CaptionTrack extends SubtitleTrack {
    private final Cea708CCParser mCCParser;
    private final Cea708CCWidget mRenderingWidget;

    @Override // android.media.SubtitleTrack
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
    }

    Cea708CaptionTrack(Cea708CCWidget cea708CCWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = cea708CCWidget;
        this.mCCParser = new Cea708CCParser(cea708CCWidget);
    }

    @Override // android.media.SubtitleTrack
    public void onData(byte[] bArr, boolean z, long j) {
        this.mCCParser.parse(bArr);
    }

    @Override // android.media.SubtitleTrack
    public SubtitleTrack.RenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }
}
