package android.media;

import android.media.SubtitleTrack;
import java.util.Vector;

/* compiled from: ClosedCaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea608CaptionTrack extends SubtitleTrack {
    private final Cea608CCParser mCCParser;
    private final Cea608CCWidget mRenderingWidget;

    @Override // android.media.SubtitleTrack
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
    }

    Cea608CaptionTrack(Cea608CCWidget cea608CCWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = cea608CCWidget;
        this.mCCParser = new Cea608CCParser(cea608CCWidget);
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
