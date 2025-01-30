package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.systemui.R;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VideoControlsPanel {
    public final SparseArray mActionViewIdMap;
    public final Context mContext;
    public TextView mCurrentText;
    public int mDisplayX;
    public int mDisplayY;
    public TextView mDurationText;
    public final LinearLayout mFloatingPanelView;
    public final HandlerC3943H mHandler;
    public MediaController mMediaController;
    public LinearLayout mMediaNextButton;
    public MediaPanelPopup mMediaPanelPopup;
    public int mMediaPanelPopupType;
    public LinearLayout mMediaPauseButton;
    public LinearLayout mMediaPreviousButton;
    public LinearLayout mMediaResumeButton;
    public SeslSeekBar mMediaSeekBarView;
    public LinearLayout mMediaSeekNextButton;
    public LinearLayout mMediaSeekPreviousButton;
    public PlaybackState mPlaybackState;
    public final C39421 mSeekBarControlListener;
    public TextView mSeekBarText;
    public long mSeekPosition;
    public final VideoControlsPanel$$ExternalSyntheticLambda0 mUpdateTimer;
    public boolean mMetadataChanged = false;
    public boolean mSeekBarFromUser = false;
    public boolean mSeekBarEnabled = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$H */
    public final class HandlerC3943H extends Handler {
        public HandlerC3943H() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            VideoControlsPanel videoControlsPanel = VideoControlsPanel.this;
            videoControlsPanel.mMediaPanelPopupType = 0;
            videoControlsPanel.mSeekPosition = 0L;
            MediaPanelPopup mediaPanelPopup = videoControlsPanel.mMediaPanelPopup;
            if (mediaPanelPopup != null) {
                mediaPanelPopup.removeView();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.appcompat.widget.SeslSeekBar$OnSeekBarChangeListener, com.android.wm.shell.controlpanel.activity.VideoControlsPanel$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda0] */
    public VideoControlsPanel(Context context, LinearLayout linearLayout, MediaController mediaController) {
        ?? r0 = new SeslSeekBar.OnSeekBarChangeListener() { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel.1
            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
                if (z) {
                    Log.i("MediaPanel", "MediaPanel mSeekBarControlListener onProgressChanged");
                    VideoControlsPanel videoControlsPanel = VideoControlsPanel.this;
                    videoControlsPanel.mSeekBarFromUser = true;
                    Context context2 = videoControlsPanel.mContext;
                    if (((AccessibilityManager) context2.getSystemService("accessibility")).semIsScreenReaderEnabled()) {
                        videoControlsPanel.mMediaController.getTransportControls().seekTo(seslSeekBar.getProgress());
                    }
                    videoControlsPanel.mSeekBarText.setText(VideoControlsPanel.makeCurrentText(seslSeekBar.getProgress()));
                    SeslSeekBar seslSeekBar2 = videoControlsPanel.mMediaSeekBarView;
                    int width = (seslSeekBar2.getWidth() - seslSeekBar2.getPaddingLeft()) - seslSeekBar2.getPaddingRight();
                    int progress = seslSeekBar2.getProgress() / 1000;
                    int max = seslSeekBar2.getMax() / 1000;
                    int displayX = ControlPanelUtils.getDisplayX(context2);
                    float paddingLeft = ((float) ((displayX * 21.5d) / 100.0d)) + seslSeekBar2.getPaddingLeft() + ((width * progress) / max);
                    if (paddingLeft - (videoControlsPanel.mSeekBarText.getWidth() / 2) < 0.0f) {
                        videoControlsPanel.mSeekBarText.setX(0.0f);
                    } else if ((videoControlsPanel.mSeekBarText.getWidth() / 2) + paddingLeft > displayX) {
                        videoControlsPanel.mSeekBarText.setX(displayX - r6.getWidth());
                    } else {
                        videoControlsPanel.mSeekBarText.setX(paddingLeft - (r6.getWidth() / 2));
                    }
                }
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar) {
                MediaMetadata metadata;
                Log.i("MediaPanel", "MediaPanel mSeekBarControlListener onStartTrackingTouch");
                VideoControlsPanel videoControlsPanel = VideoControlsPanel.this;
                videoControlsPanel.mHandler.removeCallbacks(videoControlsPanel.mUpdateTimer);
                MediaController mediaController2 = videoControlsPanel.mMediaController;
                if (mediaController2 == null || (metadata = mediaController2.getMetadata()) == null) {
                    return;
                }
                videoControlsPanel.mMediaSeekBarView.setMax((int) metadata.getLong("android.media.metadata.DURATION"));
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
                Log.i("MediaPanel", "MediaPanel mSeekBarControlListener onStopTrackingTouch : " + seslSeekBar.getProgress());
                VideoControlsPanel videoControlsPanel = VideoControlsPanel.this;
                MediaController mediaController2 = videoControlsPanel.mMediaController;
                if (mediaController2 == null) {
                    return;
                }
                mediaController2.getTransportControls().seekTo(seslSeekBar.getProgress());
                videoControlsPanel.checkPlaybackPosition(0L);
                MediaController mediaController3 = videoControlsPanel.mMediaController;
                HashMap hashMap = new HashMap();
                hashMap.put("packageName", mediaController3.getPackageName());
                ControlPanelUtils.eventLogging("F003", videoControlsPanel.mContext.getString(R.string.seekbar_sa_logging), hashMap);
                videoControlsPanel.mSeekBarText.setText("");
            }
        };
        this.mSeekBarControlListener = r0;
        this.mUpdateTimer = new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VideoControlsPanel videoControlsPanel = VideoControlsPanel.this;
                if (videoControlsPanel.mSeekBarEnabled) {
                    videoControlsPanel.updateSeekbarPosition();
                }
                PlaybackState playbackState = videoControlsPanel.mPlaybackState;
                if (playbackState == null || playbackState.getState() != 3) {
                    Log.i("MediaPanel", "MediaPanel mUpdateTimer else");
                    videoControlsPanel.mHandler.removeCallbacks(videoControlsPanel.mUpdateTimer);
                } else {
                    Log.i("MediaPanel", "MediaPanel mUpdateTimer PlaybackState.STATE_PLAYING");
                    videoControlsPanel.checkPlaybackPosition(1000L);
                }
            }
        };
        SparseArray sparseArray = new SparseArray();
        this.mActionViewIdMap = sparseArray;
        sparseArray.put(R.id.action_skip_previous, 16L);
        sparseArray.put(R.id.action_skip_next, 32L);
        sparseArray.put(R.id.action_resume, 516L);
        sparseArray.put(R.id.action_pause, 514L);
        sparseArray.put(R.id.action_seek_previous, 256L);
        sparseArray.put(R.id.action_seek_next, 256L);
        this.mContext = context;
        this.mFloatingPanelView = linearLayout;
        this.mHandler = new HandlerC3943H();
        this.mMediaController = mediaController;
        this.mMediaSeekBarView = (SeslSeekBar) linearLayout.findViewById(R.id.media_seekbar);
        this.mMediaResumeButton = (LinearLayout) linearLayout.findViewById(R.id.action_resume);
        this.mMediaPauseButton = (LinearLayout) linearLayout.findViewById(R.id.action_pause);
        this.mMediaPreviousButton = (LinearLayout) linearLayout.findViewById(R.id.action_skip_previous);
        this.mMediaNextButton = (LinearLayout) linearLayout.findViewById(R.id.action_skip_next);
        this.mMediaSeekBarView.mOnSeekBarChangeListener = r0;
        this.mCurrentText = (TextView) linearLayout.findViewById(R.id.current_time);
        this.mDurationText = (TextView) linearLayout.findViewById(R.id.duration_time);
        this.mSeekBarText = (TextView) linearLayout.findViewById(R.id.seekbar_value);
        this.mMediaSeekPreviousButton = (LinearLayout) linearLayout.findViewById(R.id.action_seek_previous);
        this.mMediaSeekNextButton = (LinearLayout) linearLayout.findViewById(R.id.action_seek_next);
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        this.mDisplayX = point.x;
        this.mDisplayY = point.y;
        this.mMediaResumeButton.setLayoutParams(getRatioLayoutParams(6.24d, 7.54d));
        this.mMediaPauseButton.setLayoutParams(getRatioLayoutParams(6.24d, 7.54d));
        this.mMediaPreviousButton.setLayoutParams(getRatioLayoutParams(3.77d, 4.55d));
        this.mMediaNextButton.setLayoutParams(getRatioLayoutParams(3.77d, 4.55d));
        this.mMediaSeekPreviousButton.setLayoutParams(getRatioLayoutParams(4.68d, 5.59d));
        this.mMediaSeekNextButton.setLayoutParams(getRatioLayoutParams(4.68d, 5.59d));
        linearLayout.findViewById(R.id.pause_image).setLayoutParams(getRatioLayoutParams(4.8d, 5.8d));
        linearLayout.findViewById(R.id.resume_image).setLayoutParams(getRatioLayoutParams(4.8d, 5.8d));
        linearLayout.findViewById(R.id.skip_image).setLayoutParams(getRatioLayoutParams(2.9d, 3.5d));
        linearLayout.findViewById(R.id.prev_image).setLayoutParams(getRatioLayoutParams(2.9d, 3.5d));
        linearLayout.findViewById(R.id.skip_image2).setLayoutParams(getRatioLayoutParams(3.6d, 4.3d));
        linearLayout.findViewById(R.id.prev_image2).setLayoutParams(getRatioLayoutParams(3.6d, 4.3d));
        linearLayout.findViewById(R.id.media_panel_button_top_margin_layout).setLayoutParams(getRatioLayoutParams(0.0d, 2.5d));
        linearLayout.findViewById(R.id.button_margin1).setLayoutParams(getRatioLayoutParams(6.5d, 0.0d));
        linearLayout.findViewById(R.id.button_margin2).setLayoutParams(getRatioLayoutParams(6.5d, 0.0d));
        linearLayout.findViewById(R.id.button_margin3).setLayoutParams(getRatioLayoutParams(6.5d, 0.0d));
        linearLayout.findViewById(R.id.button_margin4).setLayoutParams(getRatioLayoutParams(6.5d, 0.0d));
        setupButtons();
    }

    public static String makeCurrentText(int i) {
        String str;
        String str2;
        int i2;
        int i3 = i / 1000;
        int i4 = i3 / 3600;
        if (i4 != 0) {
            str = i4 + ":";
        } else {
            str = "";
        }
        if (str.equals("") || (i2 = (i3 / 60) % 60) >= 10) {
            str2 = ((i3 / 60) % 60) + ":";
        } else {
            str2 = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, i2, ":");
        }
        int i5 = i3 % 60;
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, str2, i5 < 10 ? AbstractC0000x2c234b15.m0m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, i5) : String.valueOf(i5));
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String makeDurationText(long j) {
        String str;
        StringBuilder sb;
        long j2;
        long j3 = j / 1000;
        long j4 = j3 / 3600;
        if (j4 != 0) {
            str = j4 + ":";
        } else {
            str = "";
        }
        if (!str.equals("")) {
            j2 = (j3 / 60) % 60;
            if (j2 < 10) {
                sb = new StringBuilder(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                sb.append(j2);
                sb.append(":");
                long j5 = j3 % 60;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, sb.toString(), j5 >= 10 ? ValueAnimator$$ExternalSyntheticOutline0.m25m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, j5) : String.valueOf(j5));
            }
        }
        sb = new StringBuilder();
        j2 = (j3 / 60) % 60;
        sb.append(j2);
        sb.append(":");
        long j52 = j3 % 60;
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, sb.toString(), j52 >= 10 ? ValueAnimator$$ExternalSyntheticOutline0.m25m(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, j52) : String.valueOf(j52));
    }

    public final void checkPlaybackPosition(long j) {
        Log.i("MediaPanel", "MediaPanel checkPlaybackPosition");
        HandlerC3943H handlerC3943H = this.mHandler;
        VideoControlsPanel$$ExternalSyntheticLambda0 videoControlsPanel$$ExternalSyntheticLambda0 = this.mUpdateTimer;
        if (handlerC3943H.hasCallbacks(videoControlsPanel$$ExternalSyntheticLambda0)) {
            return;
        }
        handlerC3943H.postDelayed(videoControlsPanel$$ExternalSyntheticLambda0, j);
    }

    public final LinearLayout.LayoutParams getRatioLayoutParams(double d, double d2) {
        return new LinearLayout.LayoutParams((int) ((this.mDisplayX * d) / 100.0d), (int) ((this.mDisplayY * d2) / 100.0d));
    }

    public final void onClickButton(int i, int i2) {
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || mediaController.getPlaybackState() == null) {
            Log.e("MediaPanel", "MediaPanel onClickButton mMediaController or getPlaybackState == null");
            return;
        }
        this.mPlaybackState = this.mMediaController.getPlaybackState();
        StringBuilder sb = new StringBuilder("MediaPanel onClick mMediaPauseButton mMediaController : ");
        sb.append(this.mMediaController.getPackageName());
        sb.append(", logging : ");
        Context context = this.mContext;
        sb.append(context.getResources().getString(i2));
        sb.append(", mPlaybackState : ");
        sb.append(this.mPlaybackState);
        Log.i("MediaPanel", sb.toString());
        if (i == R.id.action_skip_previous) {
            this.mMediaController.getTransportControls().skipToPrevious();
        } else if (i == R.id.action_skip_next) {
            this.mMediaController.getTransportControls().skipToNext();
        } else if (i == R.id.action_resume) {
            this.mMediaController.getTransportControls().play();
        } else if (i == R.id.action_pause) {
            this.mMediaController.getTransportControls().pause();
        } else if (i == R.id.action_seek_next) {
            MediaMetadata metadata = this.mMediaController.getMetadata();
            if (!this.mSeekBarEnabled || metadata == null) {
                return;
            }
            long position = this.mPlaybackState.getPosition();
            long j = metadata.getLong("android.media.metadata.DURATION");
            long j2 = this.mSeekPosition;
            if (j2 < 0) {
                return;
            }
            long j3 = position + 10000;
            if (j3 >= j) {
                return;
            }
            this.mSeekPosition = j2 + 10;
            if (j3 > j) {
                this.mMediaController.getTransportControls().seekTo(j);
            } else {
                this.mMediaController.getTransportControls().seekTo(this.mPlaybackState.getPosition() + 10000);
            }
            onMediaPanelPopupShow(1);
        } else if (i == R.id.action_seek_previous) {
            if (!this.mSeekBarEnabled) {
                return;
            }
            long position2 = this.mPlaybackState.getPosition();
            long j4 = this.mSeekPosition;
            if (j4 > 0) {
                return;
            }
            long j5 = position2 - 10000;
            if (j5 <= 0) {
                return;
            }
            this.mSeekPosition = j4 - 10;
            if (j5 < 0) {
                this.mMediaController.getTransportControls().seekTo(0L);
            } else {
                this.mMediaController.getTransportControls().seekTo(this.mPlaybackState.getPosition() - 10000);
            }
            onMediaPanelPopupShow(2);
        }
        MediaController mediaController2 = this.mMediaController;
        HashMap hashMap = new HashMap();
        hashMap.put("packageName", mediaController2.getPackageName());
        ControlPanelUtils.eventLogging("F003", context.getString(i2), hashMap);
    }

    public final void onMediaPanelPopupShow(int i) {
        int i2 = this.mMediaPanelPopupType;
        Context context = this.mContext;
        if (i2 != i || this.mMediaPanelPopup == null) {
            MediaPanelPopup mediaPanelPopup = this.mMediaPanelPopup;
            if (mediaPanelPopup != null) {
                mediaPanelPopup.removeView();
            }
            MediaPanelPopup mediaPanelPopup2 = new MediaPanelPopup(context, i == 1);
            this.mMediaPanelPopup = mediaPanelPopup2;
            mediaPanelPopup2.showView();
            this.mMediaPanelPopup.mLottieAnimationView.playAnimation();
        }
        HandlerC3943H handlerC3943H = this.mHandler;
        handlerC3943H.removeMessages(1);
        this.mMediaPanelPopupType = i;
        int i3 = i == 1 ? (int) this.mSeekPosition : ((int) this.mSeekPosition) * (-1);
        this.mMediaPanelPopup.mSeekTextView.setText(context.getResources().getQuantityString(R.plurals.cross_fade_sec, i3, Integer.valueOf(i3)));
        handlerC3943H.sendEmptyMessageDelayed(1, 700L);
    }

    public final void setupButtons() {
        Log.i("MediaPanel", "MediaPanel setupButtons");
        final int i = 0;
        this.mMediaPauseButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda1
            public final /* synthetic */ VideoControlsPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        break;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        break;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        break;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        break;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        break;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mMediaResumeButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda1
            public final /* synthetic */ VideoControlsPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        break;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        break;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        break;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        break;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        break;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mMediaPreviousButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda1
            public final /* synthetic */ VideoControlsPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        break;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        break;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        break;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        break;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        break;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        break;
                }
            }
        });
        final int i4 = 3;
        this.mMediaNextButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda1
            public final /* synthetic */ VideoControlsPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i4) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        break;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        break;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        break;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        break;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        break;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        break;
                }
            }
        });
        final int i5 = 4;
        this.mMediaSeekPreviousButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda1
            public final /* synthetic */ VideoControlsPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i5) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        break;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        break;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        break;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        break;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        break;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        break;
                }
            }
        });
        final int i6 = 5;
        this.mMediaSeekNextButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.VideoControlsPanel$$ExternalSyntheticLambda1
            public final /* synthetic */ VideoControlsPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i6) {
                    case 0:
                        this.f$0.onClickButton(R.id.action_pause, R.string.playpause_sa_logging);
                        break;
                    case 1:
                        this.f$0.onClickButton(R.id.action_resume, R.string.playpause_sa_logging);
                        break;
                    case 2:
                        this.f$0.onClickButton(R.id.action_skip_previous, R.string.previouse_sa_logging);
                        break;
                    case 3:
                        this.f$0.onClickButton(R.id.action_skip_next, R.string.next_sa_logging);
                        break;
                    case 4:
                        this.f$0.onClickButton(R.id.action_seek_previous, R.string.skip_backward_sa_logging);
                        break;
                    default:
                        this.f$0.onClickButton(R.id.action_seek_next, R.string.skip_forward_sa_logging);
                        break;
                }
            }
        });
    }

    public final void updatePanel() {
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            Log.e("MediaPanel", "MediaPanel updateMediaPanel mMediaController == null");
            return;
        }
        PlaybackState playbackState = mediaController.getPlaybackState();
        this.mPlaybackState = playbackState;
        if (playbackState == null) {
            Log.e("MediaPanel", "MediaPanel updateMediaPanel getPlaybackState == null");
            return;
        }
        Log.i("MediaPanel", "MediaPanel updateMediaPanel playbackState.getState : " + this.mPlaybackState.getState() + ", mPlaybackState.getPosition : " + this.mPlaybackState.getPosition());
        MediaMetadata metadata = this.mMediaController.getMetadata();
        long j = 0;
        boolean z = (this.mPlaybackState.getActions() & 256) != 0;
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("updateSeekbarInfo isSupportSeekBar : ", z, "MediaPanel");
        Context context = this.mContext;
        if (metadata == null || !z) {
            Log.i("MediaPanel", "MediaFloatingUI updateSeekbarInfo mediaMetadata is null");
            this.mSeekBarEnabled = false;
            this.mMediaSeekBarView.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_thumb_disable, context));
            this.mMediaSeekBarView.setEnabled(false);
            this.mMediaSeekBarView.setProgress(0);
        } else {
            long j2 = metadata.getLong("android.media.metadata.DURATION");
            int position = (int) this.mPlaybackState.getPosition();
            if (j2 > 0) {
                this.mCurrentText.setText(makeCurrentText(position));
                this.mDurationText.setText(makeDurationText(j2));
            } else {
                this.mCurrentText.setText("");
                this.mDurationText.setText("");
            }
            Log.i("MediaPanel", "MediaPanel updateSeekbarInfo duration : " + j2);
            if (j2 <= 100) {
                Log.i("MediaPanel", "MediaPanel updateSeekbarInfo hide seekbar");
                this.mSeekBarEnabled = false;
                this.mMediaSeekBarView.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_thumb_disable, context));
                this.mMediaSeekBarView.setEnabled(false);
                this.mMediaSeekBarView.setProgress(0);
            } else {
                this.mSeekBarEnabled = true;
                this.mMediaSeekBarView.setThumbTintList(ContextCompat.getColorStateList(R.color.seekbar_color_expand, context));
                this.mMediaSeekBarView.setEnabled(true);
                this.mMediaSeekBarView.setMax((int) j2);
                if (this.mMetadataChanged) {
                    this.mMediaSeekBarView.setProgress(0);
                    this.mMetadataChanged = false;
                }
                updateSeekbarPosition();
            }
            PlaybackState playbackState2 = this.mPlaybackState;
            if (playbackState2 != null && playbackState2.getState() == 3) {
                checkPlaybackPosition(0L);
            }
        }
        long actions = this.mPlaybackState.getActions();
        LinearLayout linearLayout = this.mFloatingPanelView;
        if (linearLayout != null) {
            SparseArray sparseArray = this.mActionViewIdMap;
            int size = sparseArray.size();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < size) {
                int keyAt = sparseArray.keyAt(i);
                long longValue = ((Long) sparseArray.valueAt(i)).longValue();
                LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(keyAt);
                long j3 = actions & longValue;
                linearLayout2.setEnabled(j3 != j);
                if (j3 != j) {
                    linearLayout2.setAlpha(1.0f);
                    arrayList.add(linearLayout2.getContentDescription());
                } else {
                    linearLayout2.setAlpha(0.4f);
                }
                i++;
                j = 0;
            }
            Log.i("MediaPanel", "action count : " + size + " enable buttons : " + arrayList);
            MediaMetadata metadata2 = this.mMediaController.getMetadata();
            if (metadata2 != null) {
                updateSeekButton((int) this.mPlaybackState.getPosition(), metadata2.getLong("android.media.metadata.DURATION"));
            }
        }
        try {
            if (this.mPlaybackState.getState() == 0) {
                this.mMediaPauseButton.setVisibility(8);
                this.mMediaResumeButton.setVisibility(0);
                this.mMediaResumeButton.setAlpha(0.4f);
            } else {
                if (this.mPlaybackState.getState() != 3 && this.mPlaybackState.getState() != 6) {
                    this.mMediaPauseButton.setVisibility(8);
                    this.mMediaResumeButton.setVisibility(0);
                }
                this.mMediaResumeButton.setVisibility(8);
                this.mMediaPauseButton.setVisibility(0);
            }
        } catch (NullPointerException unused) {
            Log.e("MediaPanel", "MediaPanel updatePlayPauseIcon mPlaybackState.getState is null");
        }
        setupButtons();
    }

    public final void updateSeekButton(int i, long j) {
        LinearLayout linearLayout = this.mFloatingPanelView;
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(R.id.action_seek_previous);
        if (i <= 100 || j == 0 || !this.mSeekBarEnabled) {
            linearLayout2.setAlpha(0.4f);
        } else {
            linearLayout2.setAlpha(1.0f);
        }
        LinearLayout linearLayout3 = (LinearLayout) linearLayout.findViewById(R.id.action_seek_next);
        if (i + 1000 >= j || j == 0 || !this.mSeekBarEnabled) {
            linearLayout3.setAlpha(0.4f);
        } else {
            linearLayout3.setAlpha(1.0f);
        }
    }

    public final void updateSeekbarPosition() {
        MediaMetadata metadata = this.mMediaController.getMetadata();
        if (metadata == null) {
            return;
        }
        this.mPlaybackState = this.mMediaController.getPlaybackState();
        StringBuilder sb = new StringBuilder("MediaPanel updateSeekbarPosition mMediaController : ");
        sb.append(this.mMediaController.getPackageName());
        sb.append(", mPlaybackState : ");
        sb.append(this.mPlaybackState);
        sb.append(", mSeekbarFromUser : ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mSeekBarFromUser, "MediaPanel");
        PlaybackState playbackState = this.mPlaybackState;
        if (playbackState != null && !this.mSeekBarFromUser) {
            int position = (int) playbackState.getPosition();
            int i = position / 1000;
            int max = this.mMediaSeekBarView.getMax();
            long j = metadata.getLong("android.media.metadata.DURATION");
            Log.i("MediaPanel", "MediaPanel updateSeekbarPosition mPlaybackState.getState() : " + this.mPlaybackState.getState() + ", currentPosition : " + position + ", current : " + i + ", max : " + max);
            if (position > max) {
                this.mMediaSeekBarView.setProgress(i);
            } else {
                if (j > 0) {
                    this.mCurrentText.setText(makeCurrentText(position));
                    this.mDurationText.setText(makeDurationText(j));
                } else {
                    this.mCurrentText.setText("");
                    this.mDurationText.setText("");
                }
                this.mMediaSeekBarView.setProgress(position);
            }
            updateSeekButton(position, j);
        }
        this.mSeekBarFromUser = false;
    }
}
