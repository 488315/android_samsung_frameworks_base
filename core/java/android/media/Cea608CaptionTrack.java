package android.media;

import java.util.Vector;

class Cea608CaptionTrack extends SubtitleTrack {
    private final Cea608CCParser mCCParser;
    private final Cea608CCWidget mRenderingWidget;

    Cea608CaptionTrack(Cea608CCWidget renderingWidget, MediaFormat format) {
        super(format);
        this.mRenderingWidget = renderingWidget;
        this.mCCParser = new Cea608CCParser(this.mRenderingWidget);
    }

    @Override // android.media.SubtitleTrack
    public void onData(byte[] data, boolean eos, long runID) {
        this.mCCParser.parse(data);
    }

    @Override // android.media.SubtitleTrack
    public SubtitleTrack.RenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }

    @Override // android.media.SubtitleTrack
    public void updateView(Vector<SubtitleTrack.Cue> activeCues) {}
}
