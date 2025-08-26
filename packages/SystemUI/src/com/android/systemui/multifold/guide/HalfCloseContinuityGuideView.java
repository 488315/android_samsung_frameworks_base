package com.android.systemui.multifold.guide;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.sesl.transparentvideo.TransparentVideoView;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import java.io.IOException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public class HalfCloseContinuityGuideView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean mGuideVisible;
    public final AssetFileDescriptor mResourceFd;
    public TransparentVideoView mTransparentVideoView;

    public HalfCloseContinuityGuideView(Context context, LayoutInflater layoutInflater, boolean z, boolean z2) {
        super(context);
        this.mGuideVisible = z2;
        this.mResourceFd = getResources().openRawResourceFd(R.raw.half_close_continuity_guide);
        layoutInflater.cloneInContext(context).inflate(R.layout.half_close_continuity_guide_layout, this);
        setBackgroundColor(0);
        if (z2) {
            loadVideoResource();
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.half_close_continuity_guide_view);
        if (z2) {
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(4);
        }
        semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(z2 ? z ? 119 : 104 : z ? 134 : 131).build());
    }

    public final void loadVideoResource() {
        try {
            this.mTransparentVideoView = (TransparentVideoView) findViewById(R.id.half_close_continuity_guide_animation);
            this.mTransparentVideoView.load(this.mResourceFd, new TransparentVideoView.Configs(0.2f, true, null), new Runnable() { // from class: com.android.systemui.multifold.guide.HalfCloseContinuityGuideView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HalfCloseContinuityGuideView halfCloseContinuityGuideView = this.f$0;
                    int i = HalfCloseContinuityGuideView.$r8$clinit;
                    halfCloseContinuityGuideView.getClass();
                    Log.d("HalfCloseContinuityGuideView", "Play half close continuity video animation");
                    halfCloseContinuityGuideView.mTransparentVideoView.play();
                }
            }, new Function1() { // from class: com.android.systemui.multifold.guide.HalfCloseContinuityGuideView.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    IMediaPlayer$MediaError iMediaPlayer$MediaError = (IMediaPlayer$MediaError) obj;
                    Log.e("HalfCloseContinuityGuideView", "MediaError errorType=" + iMediaPlayer$MediaError.type + ", message=" + iMediaPlayer$MediaError.message);
                    int i = HalfCloseContinuityGuideView.$r8$clinit;
                    HalfCloseContinuityGuideView halfCloseContinuityGuideView = HalfCloseContinuityGuideView.this;
                    halfCloseContinuityGuideView.getClass();
                    try {
                        TransparentVideoView transparentVideoView = halfCloseContinuityGuideView.mTransparentVideoView;
                        if (transparentVideoView != null && transparentVideoView.isRunning()) {
                            halfCloseContinuityGuideView.mTransparentVideoView.stop();
                        }
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("Failed to release video resource : ", e, "HalfCloseContinuityGuideView");
                    }
                    halfCloseContinuityGuideView.loadVideoResource();
                    return Unit.INSTANCE;
                }
            });
        } catch (Exception e) {
            Log.e("HalfCloseContinuityGuideView", "Failed to load video resource: " + e);
            e.printStackTrace();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() throws IOException {
        try {
            TransparentVideoView transparentVideoView = this.mTransparentVideoView;
            if (transparentVideoView != null) {
                transparentVideoView.stop();
                this.mTransparentVideoView.release();
            }
            AssetFileDescriptor assetFileDescriptor = this.mResourceFd;
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("Failed to release video resource : ", e, "HalfCloseContinuityGuideView");
        }
        super.onDetachedFromWindow();
    }
}
