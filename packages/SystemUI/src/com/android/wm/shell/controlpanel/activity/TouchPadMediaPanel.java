package com.android.wm.shell.controlpanel.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TouchPadMediaPanel {
    public final int displayX;
    public final int displayY;
    public final SparseArray mActionViewIdMapFlip;
    public final SparseArray mActionViewIdMapFold;
    public final Context mContext;
    public final LinearLayout mFloatingPanelView;
    public final TextView mMediaArtistText;
    public MediaController mMediaController;
    public final ImageView mMediaImageView;
    public final LinearLayout mMediaNextButton;
    public final LinearLayout mMediaPauseButton;
    public final LinearLayout mMediaPreviousButton;
    public final LinearLayout mMediaResumeButton;
    public final TextView mMediaTitleText;
    public PlaybackState mPlaybackState;

    public TouchPadMediaPanel(Context context, LinearLayout linearLayout, MediaController mediaController) {
        this.mMediaController = null;
        SparseArray sparseArray = new SparseArray();
        this.mActionViewIdMapFold = sparseArray;
        sparseArray.put(R.id.action_skip_previous, 16L);
        sparseArray.put(R.id.action_skip_next, 32L);
        sparseArray.put(R.id.action_resume, 4L);
        sparseArray.put(R.id.action_pause, 2L);
        SparseArray sparseArray2 = new SparseArray();
        this.mActionViewIdMapFlip = sparseArray2;
        sparseArray2.put(R.id.action_resume, 4L);
        sparseArray2.put(R.id.action_pause, 2L);
        this.mContext = context;
        this.mFloatingPanelView = linearLayout;
        this.mMediaController = mediaController;
        this.mMediaTitleText = (TextView) linearLayout.findViewById(R.id.title_text);
        this.mMediaArtistText = (TextView) linearLayout.findViewById(R.id.artist_text);
        this.mMediaImageView = (ImageView) linearLayout.findViewById(R.id.media_image_view);
        this.mMediaResumeButton = (LinearLayout) linearLayout.findViewById(R.id.action_resume);
        this.mMediaPauseButton = (LinearLayout) linearLayout.findViewById(R.id.action_pause);
        if (ControlPanelUtils.isTypeFold()) {
            this.mMediaPreviousButton = (LinearLayout) linearLayout.findViewById(R.id.action_skip_previous);
            this.mMediaNextButton = (LinearLayout) linearLayout.findViewById(R.id.action_skip_next);
        }
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        this.displayX = point.x;
        this.displayY = point.y;
        if (ControlPanelUtils.isTypeFold()) {
            linearLayout.setY(((float) ((this.displayY * 31.9d) / 100.0d)) + context.getResources().getDimensionPixelSize(R.dimen.basic_panel_top_margin));
            linearLayout.findViewById(R.id.media_text_left_margin_layout).setLayoutParams(getRatioLayoutParams(1.9d, 0.0d));
            linearLayout.findViewById(R.id.media_text_middle_margin_layout).setLayoutParams(getRatioLayoutParams(1.9d, 0.0d));
            linearLayout.findViewById(R.id.media_text_right_margin_layout).setLayoutParams(getRatioLayoutParams(2.9d, 0.0d));
            linearLayout.findViewById(R.id.media_button_left_margin_layout).setLayoutParams(getRatioLayoutParams(1.9d, 0.0d));
            linearLayout.findViewById(R.id.media_button_right_margin_layout).setLayoutParams(getRatioLayoutParams(1.9d, 0.0d));
            this.mMediaTitleText.setLayoutParams(getRatioLayoutParamsWidth(45.8d));
            this.mMediaArtistText.setLayoutParams(getRatioLayoutParamsWidth(8.7d));
            linearLayout.findViewById(R.id.media_image_view).setLayoutParams(getRatioLayoutParams(4.34d, 5.21d));
            this.mMediaResumeButton.setLayoutParams(getRatioLayoutParams(3.86d, 4.63d));
            this.mMediaPauseButton.setLayoutParams(getRatioLayoutParams(3.86d, 4.63d));
            this.mMediaPreviousButton.setLayoutParams(getRatioLayoutParams(2.89d, 3.47d));
            this.mMediaNextButton.setLayoutParams(getRatioLayoutParams(2.89d, 3.47d));
            linearLayout.findViewById(R.id.pause_image).setLayoutParams(getRatioLayoutParams(3.86d, 4.63d));
            linearLayout.findViewById(R.id.resume_image).setLayoutParams(getRatioLayoutParams(3.86d, 4.63d));
            linearLayout.findViewById(R.id.skip_image).setLayoutParams(getRatioLayoutParams(2.89d, 3.47d));
            linearLayout.findViewById(R.id.prev_image).setLayoutParams(getRatioLayoutParams(2.89d, 3.47d));
        } else {
            linearLayout.findViewById(R.id.media_text_left_margin_layout).setLayoutParams(getRatioLayoutParams(2.2d, 0.0d));
            linearLayout.findViewById(R.id.media_text_right_margin_layout).setLayoutParams(getRatioLayoutParams(2.2d, 0.0d));
            linearLayout.findViewById(R.id.touchpad_media_panel_bottom_margin).setLayoutParams(getRatioLayoutParams(0.0d, 2.0d));
            linearLayout.findViewById(R.id.title_text).setLayoutParams(getRatioLayoutParamsWidth(60.0d));
            linearLayout.findViewById(R.id.artist_text).setLayoutParams(getRatioLayoutParamsWidth(60.0d));
            linearLayout.findViewById(R.id.media_image_view).setLayoutParams(getRatioLayoutParams(10.0d, 4.09d));
            this.mMediaResumeButton.setLayoutParams(getRatioLayoutParams(8.88d, 3.64d));
            this.mMediaPauseButton.setLayoutParams(getRatioLayoutParams(8.88d, 3.64d));
            linearLayout.findViewById(R.id.pause_image).setLayoutParams(getRatioLayoutParams(8.88d, 3.64d));
            linearLayout.findViewById(R.id.resume_image).setLayoutParams(getRatioLayoutParams(8.88d, 3.64d));
        }
        Log.i("TouchPadMediaPanel", "MediaPanel setupButtons");
        final int i = 0;
        this.mMediaPauseButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.TouchPadMediaPanel$$ExternalSyntheticLambda0
            public final /* synthetic */ TouchPadMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                TouchPadMediaPanel touchPadMediaPanel = this.f$0;
                switch (i2) {
                    case 0:
                        touchPadMediaPanel.onClickButton(R.id.action_pause);
                        break;
                    case 1:
                        touchPadMediaPanel.onClickButton(R.id.action_resume);
                        break;
                    case 2:
                        touchPadMediaPanel.onClickButton(R.id.action_skip_previous);
                        break;
                    default:
                        touchPadMediaPanel.onClickButton(R.id.action_skip_next);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mMediaResumeButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.TouchPadMediaPanel$$ExternalSyntheticLambda0
            public final /* synthetic */ TouchPadMediaPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                TouchPadMediaPanel touchPadMediaPanel = this.f$0;
                switch (i22) {
                    case 0:
                        touchPadMediaPanel.onClickButton(R.id.action_pause);
                        break;
                    case 1:
                        touchPadMediaPanel.onClickButton(R.id.action_resume);
                        break;
                    case 2:
                        touchPadMediaPanel.onClickButton(R.id.action_skip_previous);
                        break;
                    default:
                        touchPadMediaPanel.onClickButton(R.id.action_skip_next);
                        break;
                }
            }
        });
        if (ControlPanelUtils.isTypeFold()) {
            final int i3 = 2;
            this.mMediaPreviousButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.TouchPadMediaPanel$$ExternalSyntheticLambda0
                public final /* synthetic */ TouchPadMediaPanel f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i3;
                    TouchPadMediaPanel touchPadMediaPanel = this.f$0;
                    switch (i22) {
                        case 0:
                            touchPadMediaPanel.onClickButton(R.id.action_pause);
                            break;
                        case 1:
                            touchPadMediaPanel.onClickButton(R.id.action_resume);
                            break;
                        case 2:
                            touchPadMediaPanel.onClickButton(R.id.action_skip_previous);
                            break;
                        default:
                            touchPadMediaPanel.onClickButton(R.id.action_skip_next);
                            break;
                    }
                }
            });
            final int i4 = 3;
            this.mMediaNextButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.controlpanel.activity.TouchPadMediaPanel$$ExternalSyntheticLambda0
                public final /* synthetic */ TouchPadMediaPanel f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i4;
                    TouchPadMediaPanel touchPadMediaPanel = this.f$0;
                    switch (i22) {
                        case 0:
                            touchPadMediaPanel.onClickButton(R.id.action_pause);
                            break;
                        case 1:
                            touchPadMediaPanel.onClickButton(R.id.action_resume);
                            break;
                        case 2:
                            touchPadMediaPanel.onClickButton(R.id.action_skip_previous);
                            break;
                        default:
                            touchPadMediaPanel.onClickButton(R.id.action_skip_next);
                            break;
                    }
                }
            });
        }
    }

    public final LinearLayout.LayoutParams getRatioLayoutParams(double d, double d2) {
        return new LinearLayout.LayoutParams((int) ((this.displayX * d) / 100.0d), (int) ((this.displayY * d2) / 100.0d));
    }

    public final LinearLayout.LayoutParams getRatioLayoutParamsWidth(double d) {
        return new LinearLayout.LayoutParams((int) ((this.displayX * d) / 100.0d), -2);
    }

    public final void onClickButton(int i) {
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || mediaController.getPlaybackState() == null) {
            Log.e("TouchPadMediaPanel", "MediaPanel onClickButton mMediaController or getPlaybackState == null");
            return;
        }
        this.mPlaybackState = this.mMediaController.getPlaybackState();
        Log.i("TouchPadMediaPanel", "MediaPanel onClick mMediaPauseButton mMediaController : " + this.mMediaController.getPackageName() + ", logging : , mPlaybackState : " + this.mPlaybackState);
        if (i == R.id.action_skip_previous) {
            this.mMediaController.getTransportControls().skipToPrevious();
            return;
        }
        if (i == R.id.action_skip_next) {
            this.mMediaController.getTransportControls().skipToNext();
        } else if (i == R.id.action_resume) {
            this.mMediaController.getTransportControls().play();
        } else if (i == R.id.action_pause) {
            this.mMediaController.getTransportControls().pause();
        }
    }

    public final void updateTouchPadMediaPanel() {
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            Log.e("TouchPadMediaPanel", "TouchPadMediaPanel updateTouchPadMediaPanel mMediaController == null");
            return;
        }
        PlaybackState playbackState = mediaController.getPlaybackState();
        this.mPlaybackState = playbackState;
        if (playbackState == null) {
            Log.e("TouchPadMediaPanel", "TouchPadMediaPanel updateTouchPadMediaPanel getPlaybackState == null");
            return;
        }
        long actions = playbackState.getActions();
        if (this.mFloatingPanelView != null) {
            SparseArray sparseArray = this.mActionViewIdMapFlip;
            if (ControlPanelUtils.isTypeFold()) {
                sparseArray = this.mActionViewIdMapFold;
            }
            int size = sparseArray.size();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                long longValue = ((Long) sparseArray.valueAt(i)).longValue();
                LinearLayout linearLayout = (LinearLayout) this.mFloatingPanelView.findViewById(keyAt);
                long j = longValue & actions;
                linearLayout.setEnabled(j != 0);
                if (j != 0) {
                    linearLayout.setAlpha(1.0f);
                    arrayList.add(linearLayout.getContentDescription());
                } else {
                    linearLayout.setAlpha(0.4f);
                }
            }
            Log.i("TouchPadMediaPanel", "action count : " + size + " enable buttons : " + arrayList);
        }
        if (this.mPlaybackState.getState() == 0) {
            this.mMediaPauseButton.setVisibility(8);
            this.mMediaResumeButton.setVisibility(0);
            this.mMediaResumeButton.setAlpha(0.4f);
        } else if (this.mPlaybackState.getState() == 3 || this.mPlaybackState.getState() == 6) {
            this.mMediaResumeButton.setVisibility(8);
            this.mMediaPauseButton.setVisibility(0);
        } else {
            this.mMediaPauseButton.setVisibility(8);
            this.mMediaResumeButton.setVisibility(0);
        }
        MediaMetadata metadata = this.mMediaController.getMetadata();
        if (metadata == null) {
            return;
        }
        this.mMediaArtistText.setText(metadata.getString("android.media.metadata.ARTIST"));
        Bitmap bitmap = metadata.getBitmap("android.media.metadata.ALBUM_ART");
        if (bitmap != null) {
            this.mMediaImageView.setImageBitmap(bitmap);
            this.mMediaImageView.setClipToOutline(true);
        } else {
            this.mMediaImageView.setImageDrawable(this.mContext.getDrawable(R.drawable.default_media_image));
        }
        String string = metadata.getString("android.media.metadata.TITLE");
        if (string == null) {
            string = metadata.getString("android.media.metadata.DISPLAY_TITLE");
        }
        if (this.mMediaTitleText.getText().equals(string)) {
            return;
        }
        this.mMediaTitleText.setText(string);
        this.mMediaTitleText.setSingleLine(true);
        this.mMediaTitleText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.mMediaTitleText.setSelected(true);
    }
}
