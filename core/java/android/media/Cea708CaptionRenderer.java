package android.media;

import android.content.Context;

/* loaded from: classes2.dex */
public class Cea708CaptionRenderer extends SubtitleController.Renderer {
    private Cea708CCWidget mCCWidget;
    private final Context mContext;

    public Cea708CaptionRenderer(Context context) {
        this.mContext = context;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(MediaFormat format) {
        if (format.containsKey("mime")) {
            String mimeType = format.getString("mime");
            return "text/cea-708".equals(mimeType);
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public SubtitleTrack createTrack(MediaFormat format) {
        String mimeType = format.getString("mime");
        if ("text/cea-708".equals(mimeType)) {
            if (this.mCCWidget == null) {
                this.mCCWidget = new Cea708CCWidget(this.mContext);
            }
            return new Cea708CaptionTrack(this.mCCWidget, format);
        }
        throw new RuntimeException("No matching format: " + format.toString());
    }
}
