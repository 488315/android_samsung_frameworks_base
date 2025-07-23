package android.media;

import android.content.Context;
import android.media.SubtitleController;
import android.os.Handler;

/* loaded from: classes2.dex */
public class SRTRenderer extends SubtitleController.Renderer {
    private final Context mContext;
    private final Handler mEventHandler;
    private final boolean mRender;
    private WebVttRenderingWidget mRenderingWidget;

    public SRTRenderer(Context context) {
        this(context, null);
    }

    SRTRenderer(Context context, Handler handler) {
        this.mContext = context;
        this.mRender = handler == null;
        this.mEventHandler = handler;
    }

    @Override // android.media.SubtitleController.Renderer
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime") && mediaFormat.getString("mime").equals("application/x-subrip")) {
            return this.mRender == (mediaFormat.getInteger(MediaFormat.KEY_IS_TIMED_TEXT, 0) == 0);
        }
        return false;
    }

    @Override // android.media.SubtitleController.Renderer
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (this.mRender && this.mRenderingWidget == null) {
            this.mRenderingWidget = new WebVttRenderingWidget(this.mContext);
        }
        if (this.mRender) {
            return new SRTTrack(this.mRenderingWidget, mediaFormat);
        }
        return new SRTTrack(this.mEventHandler, mediaFormat);
    }
}
