package com.samsung.android.biometrics.app.setting.face;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

import java.io.IOException;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class FaceGuideFragment extends Fragment implements TextureView.SurfaceTextureListener {
    public FaceEnrollActivity mActivity;
    public MediaPlayer mMediaPlayer;
    public TextureView mTextureView;

    public final void adjustTextureViewRatio(int i, int i2) {
        TextureView textureView = this.mTextureView;
        if (textureView == null) {
            return;
        }
        int width = textureView.getWidth();
        Matrix matrix = new Matrix();
        this.mTextureView.getTransform(matrix);
        matrix.setScale(((int) (this.mTextureView.getHeight() * (i / i2))) / width, 1.0f);
        matrix.postTranslate((width - r5) / 2, RecyclerView.DECELERATION_RATE);
        this.mTextureView.setTransform(matrix);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        FragmentActivity.HostCallbacks hostCallbacks = this.mHost;
        FaceEnrollActivity faceEnrollActivity =
                (FaceEnrollActivity)
                        (hostCallbacks == null ? null : (FragmentActivity) hostCallbacks.activity);
        this.mActivity = faceEnrollActivity;
        faceEnrollActivity.setTitle(getString(R.string.face_tts_registration_help));
        this.mCalled = true;
        restoreChildFragmentState();
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl.mCurState < 1) {
            fragmentManagerImpl.dispatchCreate();
        }
        Log.d("BSS_FaceGuideFragment", "onCreate");
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        Log.d("BSS_FaceGuideFragment", "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.face_guide_layout, viewGroup, false);
        View findViewById = inflate.findViewById(R.id.help_guide_layout);
        if (findViewById != null) {
            findViewById.getLayoutParams().height =
                    (int)
                            (Utils.getDisplayHeight(this.mActivity)
                                    * inflate.getResources()
                                            .getFloat(R.dimen.face_enroll_guide_clip_height_ratio));
            findViewById.requestLayout();
            TextureView textureView = (TextureView) inflate.findViewById(R.id.help_guide_video);
            this.mTextureView = textureView;
            if (textureView != null) {
                textureView.setVisibility(0);
                this.mTextureView.setSurfaceTextureListener(this);
                this.mTextureView.setFocusable(false);
            }
        }
        Button button = (Button) inflate.findViewById(R.id.register_button);
        if (button != null) {
            button.semSetButtonShapeEnabled(true);
            button.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.biometrics.app.setting.face.FaceGuideFragment$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FaceEnrollActivity faceEnrollActivity =
                                    FaceGuideFragment.this.mActivity;
                            if (faceEnrollActivity != null) {
                                faceEnrollActivity.startEnrollFragment();
                            }
                        }
                    });
        }
        View findViewById2 = inflate.findViewById(R.id.knox_logo_view);
        if (findViewById2 != null) {
            findViewById2.setVisibility(0);
        }
        if (Utils.isTalkBackEnabled(this.mActivity)) {
            TextView textView = (TextView) inflate.findViewById(R.id.guide_text);
            textView.setContentDescription(
                    ((Object) textView.getText())
                            + "\n"
                            + getString(R.string.fingerprint_tts_select_register_button));
            final View findViewById3 = inflate.findViewById(R.id.help_guide_layout);
            if (findViewById3 != null) {
                findViewById3.setAccessibilityDelegate(
                        new View
                                .AccessibilityDelegate() { // from class:
                                                           // com.samsung.android.biometrics.app.setting.face.FaceGuideFragment.1
                            @Override // android.view.View.AccessibilityDelegate
                            public final void sendAccessibilityEvent(View view, int i) {
                                if (i == 32768) {
                                    FaceGuideFragment faceGuideFragment = FaceGuideFragment.this;
                                    if (faceGuideFragment.mMediaPlayer != null) {
                                        StringBuilder sb =
                                                new StringBuilder(
                                                        faceGuideFragment.getString(
                                                                R.string
                                                                        .face_tts_animation_showing));
                                        int i2 =
                                                FaceGuideFragment.this.mMediaPlayer.isPlaying()
                                                        ? R.string.fingerprint_tts_stop
                                                        : R.string.fingerprint_tts_play;
                                        sb.append(", ");
                                        sb.append(FaceGuideFragment.this.getString(i2));
                                        sb.append(", ");
                                        sb.append(
                                                FaceGuideFragment.this.getString(
                                                        R.string.fingerprint_tts_button));
                                        view.setContentDescription(sb);
                                    }
                                }
                                super.sendAccessibilityEvent(view, i);
                            }
                        });
                findViewById3.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.biometrics.app.setting.face.FaceGuideFragment$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                FaceGuideFragment faceGuideFragment = FaceGuideFragment.this;
                                View view2 = findViewById3;
                                MediaPlayer mediaPlayer = faceGuideFragment.mMediaPlayer;
                                if (mediaPlayer != null) {
                                    if (mediaPlayer.isPlaying()) {
                                        faceGuideFragment.mMediaPlayer.pause();
                                        view2.announceForAccessibility(
                                                faceGuideFragment.getString(
                                                        R.string.fingerprint_tts_stopped));
                                    } else {
                                        faceGuideFragment.mMediaPlayer.start();
                                        view2.announceForAccessibility(
                                                faceGuideFragment.getString(
                                                        R.string.fingerprint_tts_playing));
                                    }
                                }
                            }
                        });
            }
        }
        Utils.setMaxTextScaleSize(
                (TextView) inflate.findViewById(R.id.help_guide_title),
                R.dimen.biometrics_common_guide_title_text_size);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        this.mCalled = true;
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.setSurfaceTextureListener(null);
            this.mTextureView = null;
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        try {
            Uri parse =
                    Uri.parse(
                            "android.resource://"
                                    + this.mActivity.getPackageName()
                                    + "/raw/face_enroll_help_guide");
            if (parse != null) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                this.mMediaPlayer = mediaPlayer;
                mediaPlayer.semSetParameter(2500, 1);
                this.mMediaPlayer.semSetParameter(35004, 1);
                this.mMediaPlayer.setSurface(new Surface(surfaceTexture));
                this.mMediaPlayer.setDataSource(this.mActivity, parse);
                this.mMediaPlayer.setLooping(true);
                this.mMediaPlayer.prepareAsync();
                this.mMediaPlayer.setOnPreparedListener(
                        new MediaPlayer
                                .OnPreparedListener() { // from class:
                                                        // com.samsung.android.biometrics.app.setting.face.FaceGuideFragment$$ExternalSyntheticLambda0
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mediaPlayer2) {
                                FaceGuideFragment faceGuideFragment = FaceGuideFragment.this;
                                faceGuideFragment.getClass();
                                Log.d("BSS_FaceGuideFragment", "setOnPreparedListener");
                                if (mediaPlayer2 == null) {
                                    Log.w(
                                            "BSS_FaceGuideFragment",
                                            "setOnPreparedListener : mediaPlayer == null");
                                } else {
                                    faceGuideFragment.adjustTextureViewRatio(
                                            mediaPlayer2.getVideoWidth(),
                                            mediaPlayer2.getVideoHeight());
                                    mediaPlayer2.start();
                                }
                            }
                        });
            }
        } catch (IOException unused) {
            Log.e("BSS_FaceGuideFragment", "IOException");
        } catch (IllegalArgumentException unused2) {
            Log.e("BSS_FaceGuideFragment", "IllegalArgumentException");
        } catch (IllegalStateException unused3) {
            Log.e("BSS_FaceGuideFragment", "IllegalStateException");
        } catch (SecurityException unused4) {
            Log.e("BSS_FaceGuideFragment", "SecurityException");
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            adjustTextureViewRatio(mediaPlayer.getVideoWidth(), this.mMediaPlayer.getVideoHeight());
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}
}
