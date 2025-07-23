package android.media;

import android.content.Context;
import android.media.SubtitleController;

/* loaded from: classes2.dex */
public class Cea708CaptionRenderer extends SubtitleController.Renderer {
    private Cea708CCWidget mCCWidget;
    private final Context mContext;

    public Cea708CaptionRenderer(Context context) {
        this.mContext = context;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return "text/cea-708".equals(mediaFormat.getString("mime"));
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if ("text/cea-708".equals(mediaFormat.getString("mime"))) {
            if (this.mCCWidget == null) {
                this.mCCWidget = new Cea708CCWidget(this.mContext);
            }
            return new Cea708CaptionTrack(this.mCCWidget, mediaFormat);
        }
        throw new RuntimeException("No matching format: " + mediaFormat.toString());
    }
}
