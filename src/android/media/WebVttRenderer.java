package android.media;

import android.content.Context;
import android.media.SubtitleController;

/* loaded from: classes2.dex */
public class WebVttRenderer extends SubtitleController.Renderer {
    private final Context mContext;
    private WebVttRenderingWidget mRenderingWidget;

    public WebVttRenderer(Context context) {
        this.mContext = context;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return mediaFormat.getString("mime").equals("text/vtt");
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (this.mRenderingWidget == null) {
            this.mRenderingWidget = new WebVttRenderingWidget(this.mContext);
        }
        return new WebVttTrack(this.mRenderingWidget, mediaFormat);
    }
}
