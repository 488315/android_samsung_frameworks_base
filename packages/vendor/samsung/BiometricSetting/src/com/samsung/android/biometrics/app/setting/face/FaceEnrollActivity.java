package com.samsung.android.biometrics.app.setting.face;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.face.FaceEnrollOptions;
import android.hardware.face.FaceManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.MemoryFile;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.view.SemWindowManager;
import java.io.ByteArrayOutputStream;

public class FaceEnrollActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AnonymousClass2 mAttachStateChangeListener;
    public CancellationSignal mCancellationSignal;
    public long mChallenge;
    public boolean mCurrentIsFolded;
    public FaceEnrollFragment mEnrollFragment;
    public AlertDialog mErrorDialog;
    public Toast mExitToast;
    public AnonymousClass7 mFoldStateListener;
    public boolean mIsChallengeCreated;
    public boolean mIsGlassGuideShown;
    public boolean mIsPreviewStart;
    public boolean mIsSpokenPositionCorrect;
    public RelativeLayout mMainLayout;
    public HandlerThread mPreviewCBThread;
    public int mSensorId;
    public SharedPreferences mSharedPreferences;
    public CountDownTimer mShowPoseGuideTimer;
    public SoundPool mSoundPool;
    public int mUserId;
    public FaceManager mFaceManager = null;
    public boolean mIsFaceManagerBinded = false;
    public final boolean[] mPoseGuideShown = {false, false};
    public byte[] mHWToken = null;
    public long mGkPwHandle = 0;
    public boolean mIsWearingGlasses = false;
    public TextView mFaceGuideText = null;
    public TextView mFaceHelpGuideText = null;
    public TextView mFaceProgressText = null;
    public LottieAnimationView mGlassesOffVi = null;
    public LottieAnimationView mPoseGuideVi = null;
    public Button mGuideContinueBtn = null;
    public ImageView mCameraPreview = null;
    public FaceRegisterEffectView mEnrollAnimationView = null;
    public View mSetupWizardButtonLayout = null;
    public boolean mIsUSA = false;
    public boolean mIsHelpEnabled = false;
    public boolean mIsEnrolling = false;
    public boolean mIsBackSecond = false;
    public final AnonymousClass1 mBackHandler = new Handler() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            FaceEnrollActivity.this.mIsBackSecond = false;
        }
    };
    public boolean mIsSetupWizard = false;
    public boolean mEnrollPaused = false;
    public boolean mIsTalkbackEnabled = false;
    public TextToSpeech mTts = null;
    public View mSecuredKnoxLogoView = null;
    public final boolean mIsSupportDualDisplay = Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY;
    public Bitmap mPreviewImage = null;
    public byte[] mPreviewData = null;
    public Handler mPreviewHandler = null;
    public Handler mPreviewCBHandler = null;
    public TextureView mTextureView = null;
    public long mLastDecodingTimestamp = 0;
    public int mPercent = 0;
    public final AnonymousClass6 mEnrollmentCallback = new AnonymousClass6();

    /* renamed from: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$6, reason: invalid class name */
    public final class AnonymousClass6 extends FaceManager.EnrollmentCallback {
        public AnonymousClass6() {
        }

        public final void onEnrollmentError(int i, CharSequence charSequence) {
            PunchHoleVIView punchHoleVIView;
            Log.i("BSS_FaceEnrollActivity", "onEnrollmentError: " + ((Object) charSequence));
            FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
            if (faceEnrollActivity.mIsFaceManagerBinded) {
                FaceEnrollFragment faceEnrollFragment = faceEnrollActivity.mEnrollFragment;
                if (faceEnrollFragment != null && (punchHoleVIView = faceEnrollFragment.mPunchHoleVIView) != null) {
                    punchHoleVIView.pause();
                }
                if (i == 3) {
                    FaceEnrollActivity.this.setEnrollResult(-2);
                    return;
                }
                if (i == 4) {
                    FaceEnrollActivity faceEnrollActivity2 = FaceEnrollActivity.this;
                    faceEnrollActivity2.showFaceErrorDialog(i, faceEnrollActivity2.getString(R.string.face_error_message_no_space));
                } else if (i == 5) {
                    FaceEnrollActivity.this.setEnrollResult(0);
                } else if (i == 100002) {
                    FaceEnrollActivity.this.setEnrollResult(-4);
                } else if (FaceEnrollActivity.this.isResumed()) {
                    FaceEnrollActivity.this.showFaceErrorDialog(i, charSequence);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:56:0x0122  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01d0  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0192  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x019a  */
        /* JADX WARN: Removed duplicated region for block: B:84:0x01a2  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x01aa  */
        /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onEnrollmentFrame(int r9, java.lang.CharSequence r10, android.hardware.face.FaceEnrollCell r11, int r12, float r13, float r14, float r15) {
            /*
                Method dump skipped, instructions count: 550
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.AnonymousClass6.onEnrollmentFrame(int, java.lang.CharSequence, android.hardware.face.FaceEnrollCell, int, float, float, float):void");
        }

        public final void onEnrollmentProgress(int i) {
            View view;
            PunchHoleVIView punchHoleVIView;
            PunchHoleVIView punchHoleVIView2;
            TextToSpeech textToSpeech;
            if (Utils.Config.FEATURE_FACE_HAL) {
                i = 100 - i;
            }
            Log.i("BSS_FaceEnrollActivity", "onEnrollmentProgress : " + i);
            FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
            if (!faceEnrollActivity.mIsEnrolling) {
                faceEnrollActivity.mIsEnrolling = true;
            }
            if (faceEnrollActivity.mPercent >= i) {
                return;
            }
            faceEnrollActivity.mPercent = i;
            Log.i("BSS_FaceEnrollActivity", "Update percent : " + FaceEnrollActivity.this.mPercent);
            LottieAnimationView lottieAnimationView = FaceEnrollActivity.this.mPoseGuideVi;
            if (lottieAnimationView == null || !lottieAnimationView.isAnimating()) {
                FaceEnrollActivity faceEnrollActivity2 = FaceEnrollActivity.this;
                faceEnrollActivity2.startHelpGuideEffect(faceEnrollActivity2.getString(R.string.intelligentscan_guide_register_description));
            }
            FaceEnrollActivity faceEnrollActivity3 = FaceEnrollActivity.this;
            if (faceEnrollActivity3.mPoseGuideVi != null) {
                int i2 = faceEnrollActivity3.mPercent;
                Pose pose = Pose.CENTER;
                if (i2 > 70) {
                    if (faceEnrollActivity3.mPoseGuideShown[0]) {
                        FaceEnrollActivity.m6$$Nest$mshowPoseGuide(faceEnrollActivity3, pose, true, false);
                    }
                } else if (i2 > 30) {
                    if (faceEnrollActivity3.mPoseGuideShown[1]) {
                        FaceEnrollActivity.m6$$Nest$mshowPoseGuide(faceEnrollActivity3, Pose.UP, true, false);
                    }
                } else if (i2 > 0 && faceEnrollActivity3.mPoseGuideShown[0]) {
                    FaceEnrollActivity.m6$$Nest$mshowPoseGuide(faceEnrollActivity3, pose, true, false);
                }
            }
            FaceEnrollActivity faceEnrollActivity4 = FaceEnrollActivity.this;
            int i3 = faceEnrollActivity4.mPercent;
            FaceRegisterEffectView faceRegisterEffectView = faceEnrollActivity4.mEnrollAnimationView;
            if (faceRegisterEffectView != null) {
                faceRegisterEffectView.setProgress(i3);
            }
            TextView textView = faceEnrollActivity4.mFaceProgressText;
            if (textView != null) {
                textView.setText(TextUtils.expandTemplate(faceEnrollActivity4.getText(R.string.face_enroll_percent_format_text), String.format("%d", Integer.valueOf(i3))));
            }
            if (faceEnrollActivity4.mIsTalkbackEnabled) {
                if (i3 > 0 && !faceEnrollActivity4.mIsSpokenPositionCorrect) {
                    faceEnrollActivity4.mIsSpokenPositionCorrect = true;
                    faceEnrollActivity4.runTextToSpeech(0, faceEnrollActivity4.getString(R.string.intelligentscan_guide_register_description));
                }
                if (faceEnrollActivity4.mIsSpokenPositionCorrect && (textToSpeech = faceEnrollActivity4.mTts) != null && !textToSpeech.isSpeaking()) {
                    if (i3 == 30) {
                        faceEnrollActivity4.runTextToSpeech(0, faceEnrollActivity4.getString(R.string.intelligentscan_guide_register_talkback));
                    }
                    if (faceEnrollActivity4.mIsWearingGlasses) {
                        if (i3 == 60) {
                            faceEnrollActivity4.runTextToSpeech(0, faceEnrollActivity4.getString(R.string.intelligentscan_guide_register_talkback));
                        }
                    } else if (i3 == 70) {
                        faceEnrollActivity4.runTextToSpeech(0, faceEnrollActivity4.getString(R.string.intelligentscan_guide_register_talkback));
                    }
                }
            }
            if (i3 == 100) {
                if (faceEnrollActivity4.mIsHelpEnabled) {
                    faceEnrollActivity4.setHelpString(-1, true);
                    faceEnrollActivity4.mIsHelpEnabled = false;
                }
                FaceEnrollFragment faceEnrollFragment = faceEnrollActivity4.mEnrollFragment;
                if (faceEnrollFragment != null && (punchHoleVIView2 = faceEnrollFragment.mPunchHoleVIView) != null) {
                    punchHoleVIView2.pause();
                }
                faceEnrollActivity4.mSoundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(5).setContentType(4).build()).setMaxStreams(1).build();
                int streamMaxVolume = ((AudioManager) faceEnrollActivity4.getSystemService("audio")).getStreamMaxVolume(1);
                final float streamVolume = streamMaxVolume > 0 ? r1.getStreamVolume(1) / streamMaxVolume : 0.0f;
                final int load = faceEnrollActivity4.mSoundPool.load(faceEnrollActivity4, R.raw.face_regi_success, 1);
                faceEnrollActivity4.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$$ExternalSyntheticLambda4
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool, int i4, int i5) {
                        int i6 = load;
                        float f = streamVolume;
                        int i7 = FaceEnrollActivity.$r8$clinit;
                        soundPool.play(i6, f, f, 0, 0, 1.0f);
                    }
                });
                Utils.playVibration(faceEnrollActivity4, 126);
                if (faceEnrollActivity4.mFaceGuideText != null) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
                    alphaAnimation.setDuration(250L);
                    alphaAnimation.setFillAfter(true);
                    faceEnrollActivity4.mFaceGuideText.setAlpha(1.0f);
                    faceEnrollActivity4.mFaceGuideText.setVisibility(0);
                    faceEnrollActivity4.mFaceGuideText.setText(R.string.face_enroll_completed);
                    faceEnrollActivity4.mFaceGuideText.startAnimation(alphaAnimation);
                }
                TextView textView2 = faceEnrollActivity4.mFaceHelpGuideText;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                View findViewById = faceEnrollActivity4.findViewById(R.id.face_enroll_scroll_view);
                if (findViewById != null) {
                    findViewById.semRequestAccessibilityFocus();
                }
            }
            final FaceEnrollActivity faceEnrollActivity5 = FaceEnrollActivity.this;
            int i4 = faceEnrollActivity5.mPercent;
            if (i4 < 70 || faceEnrollActivity5.mEnrollPaused || !faceEnrollActivity5.mIsWearingGlasses) {
                if (i4 == 100) {
                    new Handler().postDelayed(new FaceEnrollActivity$6$$ExternalSyntheticLambda0(this, 1), 1500L);
                    return;
                }
                return;
            }
            faceEnrollActivity5.mEnrollPaused = true;
            faceEnrollActivity5.mIsSpokenPositionCorrect = false;
            AlphaAnimation alphaAnimation2 = FaceEnrollActivity.getAlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f, 230);
            FaceEnrollFragment faceEnrollFragment2 = faceEnrollActivity5.mEnrollFragment;
            if (faceEnrollFragment2 != null && (punchHoleVIView = faceEnrollFragment2.mPunchHoleVIView) != null) {
                punchHoleVIView.pause();
            }
            if (faceEnrollActivity5.mIsSetupWizard && (view = faceEnrollActivity5.mSecuredKnoxLogoView) != null) {
                view.setVisibility(4);
            }
            ImageView imageView = faceEnrollActivity5.mCameraPreview;
            if (imageView != null) {
                imageView.setColorFilter(faceEnrollActivity5.getColor(R.color.intelligent_enroll_background_color));
            }
            TextureView textureView = faceEnrollActivity5.mTextureView;
            if (textureView != null) {
                textureView.setVisibility(4);
            }
            FaceRegisterEffectView faceRegisterEffectView2 = faceEnrollActivity5.mEnrollAnimationView;
            if (faceRegisterEffectView2 != null) {
                faceRegisterEffectView2.stopScanEffect();
                faceEnrollActivity5.mEnrollAnimationView.setErrorState();
            }
            LottieAnimationView lottieAnimationView2 = faceEnrollActivity5.mPoseGuideVi;
            if (lottieAnimationView2 != null) {
                lottieAnimationView2.cancelAnimation();
            }
            LottieAnimationView lottieAnimationView3 = faceEnrollActivity5.mGlassesOffVi;
            if (lottieAnimationView3 != null) {
                lottieAnimationView3.startAnimation(alphaAnimation2);
                faceEnrollActivity5.mGlassesOffVi.setVisibility(0);
                faceEnrollActivity5.mGlassesOffVi.setRepeatCount(-1);
                faceEnrollActivity5.mGlassesOffVi.playAnimation();
                faceEnrollActivity5.setHelpString(-1, true);
                faceEnrollActivity5.mFaceHelpGuideText.setText("");
                if (faceEnrollActivity5.mIsTalkbackEnabled) {
                    faceEnrollActivity5.mFaceHelpGuideText.setVisibility(4);
                    faceEnrollActivity5.mFaceGuideText.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.4
                        @Override // android.view.View.AccessibilityDelegate
                        public final void sendAccessibilityEventUnchecked(View view2, AccessibilityEvent accessibilityEvent) {
                            TextToSpeech textToSpeech2;
                            if (accessibilityEvent.getEventType() == 2048) {
                                return;
                            }
                            if (accessibilityEvent.getEventType() == 32768 && (textToSpeech2 = FaceEnrollActivity.this.mTts) != null && textToSpeech2.isSpeaking()) {
                                return;
                            }
                            super.sendAccessibilityEventUnchecked(view2, accessibilityEvent);
                        }
                    });
                }
                String string = faceEnrollActivity5.getString(R.string.face_guide_wearing_glasses_text);
                faceEnrollActivity5.mFaceGuideText.setText(string);
                faceEnrollActivity5.runTextToSpeech(0, string);
                faceEnrollActivity5.runTextToSpeech(1, faceEnrollActivity5.getString(R.string.common_select_continue_button));
            }
            Button button = faceEnrollActivity5.mGuideContinueBtn;
            if (button != null) {
                if (!faceEnrollActivity5.mIsSetupWizard) {
                    button.setVisibility(0);
                    faceEnrollActivity5.mIsGlassGuideShown = true;
                    faceEnrollActivity5.mGuideContinueBtn.startAnimation(alphaAnimation2);
                    final int i5 = 1;
                    faceEnrollActivity5.mGuideContinueBtn.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            int i6 = i5;
                            FaceEnrollActivity faceEnrollActivity6 = faceEnrollActivity5;
                            switch (i6) {
                                case 0:
                                    int i7 = FaceEnrollActivity.$r8$clinit;
                                    faceEnrollActivity6.hideGlassesGuide(view2);
                                    break;
                                default:
                                    int i8 = FaceEnrollActivity.$r8$clinit;
                                    faceEnrollActivity6.hideGlassesGuide(view2);
                                    break;
                            }
                        }
                    });
                    return;
                }
                button.setVisibility(8);
                View findViewById2 = faceEnrollActivity5.findViewById(R.id.face_suw_bottom_container);
                faceEnrollActivity5.mSetupWizardButtonLayout = findViewById2;
                if (findViewById2 != null) {
                    findViewById2.setVisibility(0);
                    final int i6 = 0;
                    ((Button) faceEnrollActivity5.mSetupWizardButtonLayout.findViewById(R.id.next_button_area)).setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            int i62 = i6;
                            FaceEnrollActivity faceEnrollActivity6 = faceEnrollActivity5;
                            switch (i62) {
                                case 0:
                                    int i7 = FaceEnrollActivity.$r8$clinit;
                                    faceEnrollActivity6.hideGlassesGuide(view2);
                                    break;
                                default:
                                    int i8 = FaceEnrollActivity.$r8$clinit;
                                    faceEnrollActivity6.hideGlassesGuide(view2);
                                    break;
                            }
                        }
                    });
                }
            }
        }

        public final void onImageProcessed(final byte[] bArr, final int i, final int i2, final int i3, int i4, final Bundle bundle) {
            if (!Utils.Config.FEATURE_FACE_HAL) {
                Log.i("BSS_FaceEnrollActivity", "It's not face HAL");
                return;
            }
            FaceEnrollActivity.this.mPreviewHandler.post(new FaceEnrollActivity$6$$ExternalSyntheticLambda0(this, 0));
            if (Utils.DEBUG) {
                StringBuilder sb = new StringBuilder("onImageProcessed : w=");
                sb.append(i);
                sb.append(", h=");
                sb.append(i2);
                sb.append(", ori=");
                sb.append(i3);
                sb.append(", imageFormat=");
                sb.append(i4);
                sb.append(", size=");
                sb.append(bArr != null ? Integer.valueOf(bArr.length) : "null");
                sb.append(", b=");
                sb.append(bundle);
                Log.d("BSS_FaceEnrollActivity", sb.toString());
            }
            if (bArr == null || bArr.length == 0) {
                Log.d("BSS_FaceEnrollActivity", "data is null or 0");
            } else {
                FaceEnrollActivity.this.mPreviewCBHandler.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$6$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FaceEnrollActivity.AnonymousClass6 anonymousClass6 = FaceEnrollActivity.AnonymousClass6.this;
                        byte[] bArr2 = bArr;
                        Bundle bundle2 = bundle;
                        int i5 = i;
                        int i6 = i2;
                        int i7 = i3;
                        FaceEnrollActivity.this.mPreviewData = bArr2;
                        ParcelFileDescriptor parcelFileDescriptor = bundle2 != null ? (ParcelFileDescriptor) bundle2.getParcelable("memoryfile_descriptor") : null;
                        if (parcelFileDescriptor != null) {
                            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                            try {
                                try {
                                    try {
                                        parcelFileDescriptor.seekTo(0L);
                                        FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
                                        int size = MemoryFile.getSize(parcelFileDescriptor.getFileDescriptor());
                                        faceEnrollActivity.getClass();
                                        byte[] bArr3 = new byte[size];
                                        int i8 = 0;
                                        while (true) {
                                            int read = autoCloseInputStream.read(bArr3, i8, bArr3.length - i8);
                                            if (read <= 0) {
                                                break;
                                            }
                                            i8 += read;
                                            int available = autoCloseInputStream.available();
                                            if (available > bArr3.length - i8) {
                                                byte[] bArr4 = new byte[available + i8];
                                                System.arraycopy(bArr3, 0, bArr4, 0, i8);
                                                bArr3 = bArr4;
                                            }
                                        }
                                        faceEnrollActivity.mPreviewData = bArr3;
                                        autoCloseInputStream.close();
                                        parcelFileDescriptor.close();
                                    } catch (Exception e) {
                                        Log.w("BSS_FaceEnrollActivity", "Unable to read statistics stream", e);
                                        autoCloseInputStream.close();
                                        parcelFileDescriptor.close();
                                    }
                                } catch (Exception e2) {
                                    Log.w("BSS_FaceEnrollActivity", "Unable to close stream", e2);
                                }
                            } catch (Throwable th) {
                                try {
                                    autoCloseInputStream.close();
                                    parcelFileDescriptor.close();
                                } catch (Exception e3) {
                                    Log.w("BSS_FaceEnrollActivity", "Unable to close stream", e3);
                                }
                                throw th;
                            }
                        }
                        if (FaceEnrollActivity.this.mPreviewImage != null) {
                            Log.i("BSS_FaceEnrollActivity", "onImageProcessed : setImageBitmap not done");
                            return;
                        }
                        if (SystemClock.elapsedRealtime() - FaceEnrollActivity.this.mLastDecodingTimestamp < 30) {
                            Log.i("BSS_FaceEnrollActivity", "onImageProcessed : Too fast");
                            return;
                        }
                        try {
                            YuvImage yuvImage = new YuvImage(FaceEnrollActivity.this.mPreviewData, 17, i5, i6, null);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            yuvImage.compressToJpeg(new Rect(0, 0, i5, i6), 100, byteArrayOutputStream);
                            FaceEnrollActivity.this.mPreviewImage = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                            Matrix matrix = new Matrix();
                            if (i7 == 0) {
                                matrix.setRotate(180.0f, FaceEnrollActivity.this.mPreviewImage.getWidth() / 2.0f, FaceEnrollActivity.this.mPreviewImage.getHeight() / 2.0f);
                            } else if (i7 == 270) {
                                matrix.setRotate(270.0f, FaceEnrollActivity.this.mPreviewImage.getWidth() / 2.0f, FaceEnrollActivity.this.mPreviewImage.getHeight() / 2.0f);
                            }
                            matrix.preScale(1.0f, -1.0f);
                            FaceEnrollActivity faceEnrollActivity2 = FaceEnrollActivity.this;
                            Bitmap bitmap = faceEnrollActivity2.mPreviewImage;
                            faceEnrollActivity2.mPreviewImage = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), FaceEnrollActivity.this.mPreviewImage.getHeight(), matrix, true);
                            FaceEnrollActivity.this.mPreviewHandler.post(new FaceEnrollActivity$6$$ExternalSyntheticLambda0(anonymousClass6, 2));
                        } catch (Exception unused) {
                            Log.e("BSS_FaceEnrollActivity", "Image process failed");
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class Pose {
        public static final /* synthetic */ Pose[] $VALUES;
        public static final Pose CENTER;
        public static final Pose UP;

        static {
            Pose pose = new Pose("CENTER", 0);
            CENTER = pose;
            Pose pose2 = new Pose("UP", 1);
            UP = pose2;
            $VALUES = new Pose[]{pose, pose2};
        }

        public static Pose valueOf(String str) {
            return (Pose) Enum.valueOf(Pose.class, str);
        }

        public static Pose[] values() {
            return (Pose[]) $VALUES.clone();
        }
    }

    /* renamed from: -$$Nest$mshowPoseGuide, reason: not valid java name */
    public static void m6$$Nest$mshowPoseGuide(FaceEnrollActivity faceEnrollActivity, Pose pose, boolean z, boolean z2) {
        faceEnrollActivity.getClass();
        Log.i("BSS_FaceEnrollActivity", "showPoseGuide : " + pose + " , bRecognized : " + z);
        if (faceEnrollActivity.mPoseGuideVi == null) {
            return;
        }
        final int ordinal = pose.ordinal();
        boolean[] zArr = faceEnrollActivity.mPoseGuideShown;
        if (z) {
            faceEnrollActivity.startHelpGuideEffect(faceEnrollActivity.getString(R.string.face_guide_look_ahead));
            faceEnrollActivity.mPoseGuideVi.setVisibility(4);
            FaceRegisterEffectView faceRegisterEffectView = faceEnrollActivity.mEnrollAnimationView;
            faceRegisterEffectView.mIsBlurMask = false;
            faceRegisterEffectView.startScanEffect();
            zArr[ordinal] = false;
            faceEnrollActivity.mPoseGuideVi.removeAllAnimatorListeners();
            return;
        }
        if (zArr[ordinal]) {
            return;
        }
        Utils.playVibration(faceEnrollActivity, z2 ? 127 : 1);
        FaceRegisterEffectView faceRegisterEffectView2 = faceEnrollActivity.mEnrollAnimationView;
        faceRegisterEffectView2.mIsBlurMask = true;
        faceRegisterEffectView2.stopScanEffect();
        zArr[ordinal] = true;
        if (pose == Pose.CENTER) {
            if (z2) {
                faceEnrollActivity.mPoseGuideVi.setAnimation("face_recognition_center.json");
            } else {
                faceEnrollActivity.mPoseGuideVi.setAnimation("face_recognition_down.json");
            }
        } else if (pose == Pose.UP) {
            faceEnrollActivity.mPoseGuideVi.setAnimation("face_recognition_up.json");
        }
        faceEnrollActivity.mPoseGuideVi.playAnimation();
        faceEnrollActivity.mPoseGuideVi.setVisibility(0);
        faceEnrollActivity.mPoseGuideVi.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.3
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                FaceEnrollActivity faceEnrollActivity2 = FaceEnrollActivity.this;
                boolean[] zArr2 = faceEnrollActivity2.mPoseGuideShown;
                zArr2[ordinal] = false;
                if (zArr2[1] || zArr2[0]) {
                    Log.i("BSS_FaceEnrollActivity", "onAnimationEnd skip");
                    return;
                }
                faceEnrollActivity2.mPoseGuideVi.setVisibility(4);
                FaceRegisterEffectView faceRegisterEffectView3 = FaceEnrollActivity.this.mEnrollAnimationView;
                faceRegisterEffectView3.mIsBlurMask = false;
                faceRegisterEffectView3.startScanEffect();
                animator.removeAllListeners();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
    }

    public static AlphaAnimation getAlphaAnimation(float f, float f2, int i) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setDuration(i);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.2f, 1.0f));
        return alphaAnimation;
    }

    public final String getPositionGuideText(boolean z) {
        return this.mIsTalkbackEnabled ? this.mIsUSA ? getString(R.string.face_guide_register_description, new Object[]{Integer.valueOf(getResources().getInteger(R.integer.sec_face_enroll_distance_inch_from)), Integer.valueOf(getResources().getInteger(R.integer.sec_face_enroll_distance_inch_to))}) : z ? getString(R.string.face_guide_register_description_cm_tts, new Object[]{Integer.valueOf(getResources().getInteger(R.integer.sec_face_enroll_distance_cm_from)), Integer.valueOf(getResources().getInteger(R.integer.sec_face_enroll_distance_cm_to))}) : getString(R.string.face_guide_register_description_cm, new Object[]{Integer.valueOf(getResources().getInteger(R.integer.sec_face_enroll_distance_cm_from)), Integer.valueOf(getResources().getInteger(R.integer.sec_face_enroll_distance_cm_to))}) : getString(R.string.face_guide_register_additional_description);
    }

    public final void hideGlassesGuide(View view) {
        PunchHoleVIView punchHoleVIView;
        AlphaAnimation alphaAnimation = getAlphaAnimation(1.0f, RecyclerView.DECELERATION_RATE, 230);
        this.mIsGlassGuideShown = false;
        this.mFaceGuideText.setText(R.string.face_guide_look_ahead);
        this.mFaceGuideText.announceForAccessibility(getPositionGuideText(true));
        setHelpString(-1, true);
        if (view != null) {
            view.setEnabled(false);
            view.setVisibility(8);
            view.startAnimation(alphaAnimation);
        }
        LottieAnimationView lottieAnimationView = this.mGlassesOffVi;
        if (lottieAnimationView != null) {
            lottieAnimationView.cancelAnimation();
            this.mGlassesOffVi.setVisibility(8);
            this.mGlassesOffVi.startAnimation(alphaAnimation);
        }
        ImageView imageView = this.mCameraPreview;
        if (imageView != null) {
            imageView.setColorFilter((ColorFilter) null);
        }
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.setVisibility(0);
        }
        FaceRegisterEffectView faceRegisterEffectView = this.mEnrollAnimationView;
        if (faceRegisterEffectView != null) {
            faceRegisterEffectView.startScanEffect();
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null) {
            faceManager.semResumeEnroll();
            FaceEnrollFragment faceEnrollFragment = this.mEnrollFragment;
            if (faceEnrollFragment != null && (punchHoleVIView = faceEnrollFragment.mPunchHoleVIView) != null) {
                punchHoleVIView.play();
            }
        }
        if (this.mIsSetupWizard) {
            View view2 = this.mSetupWizardButtonLayout;
            if (view2 != null) {
                view2.setVisibility(4);
            }
            View view3 = this.mSecuredKnoxLogoView;
            if (view3 != null) {
                view3.setVisibility(0);
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        if (!this.mIsBackSecond) {
            this.mIsBackSecond = true;
            if (!this.mIsTalkbackEnabled) {
                sendEmptyMessageDelayed(0, 2000L);
            }
            this.mExitToast.show();
            return;
        }
        Log.d("BSS_FaceEnrollActivity", "Face enroll screen is terminated by Back key !!");
        this.mIsBackSecond = false;
        this.mExitToast.cancel();
        setEnrollResult(0);
        super.onBackPressed();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.view.View$OnAttachStateChangeListener, com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$2] */
    /* JADX WARN: Type inference failed for: r4v28, types: [com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$7] */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Log.d("BSS_FaceEnrollActivity", "onCreate");
        setTitle(" ");
        super.onCreate(bundle);
        if (getIntent().getExtras() == null) {
            Log.e("BSS_FaceEnrollActivity", "intent should have extra value");
            finish();
            return;
        }
        setContentView(R.layout.face_register_activity);
        Utils.Config.FEATURE_FACE_HAL = getPackageManager().hasSystemFeature("android.hardware.biometrics.face");
        this.mPreviewHandler = new Handler();
        HandlerThread handlerThread = new HandlerThread("Preview Thread", 0);
        this.mPreviewCBThread = handlerThread;
        handlerThread.start();
        this.mPreviewCBHandler = this.mPreviewCBThread.getThreadHandler();
        this.mIsSetupWizard = getIntent().getBooleanExtra("fromSetupWizard", false);
        this.mSharedPreferences = getPreferences(0);
        boolean z = Utils.DEBUG;
        this.mIsUSA = "US".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
        this.mIsTalkbackEnabled = Utils.isTalkBackEnabled(this);
        this.mUserId = getIntent().getIntExtra("android.intent.extra.USER_ID", 0);
        if (this.mIsSupportDualDisplay) {
            if (this.mFoldStateListener == null) {
                this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.7
                    public final void onFoldStateChanged(boolean z2) {
                        FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
                        if (faceEnrollActivity.mCurrentIsFolded != z2) {
                            RelativeLayout relativeLayout = faceEnrollActivity.mMainLayout;
                            if (relativeLayout != null) {
                                relativeLayout.setVisibility(8);
                            }
                            Toast.makeText(FaceEnrollActivity.this.getApplicationContext(), FaceEnrollActivity.this.getString(R.string.face_enroll_cancel), 1).show();
                            FaceEnrollActivity faceEnrollActivity2 = FaceEnrollActivity.this;
                            if (faceEnrollActivity2.mIsSetupWizard) {
                                faceEnrollActivity2.setEnrollResult(-5);
                            } else {
                                faceEnrollActivity2.setEnrollResult(0);
                            }
                        }
                    }

                    public final void onTableModeChanged(boolean z2) {
                    }
                };
                SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
            }
            this.mCurrentIsFolded = getResources().getConfiguration().semDisplayDeviceType == 5;
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        this.mMainLayout = relativeLayout;
        if (relativeLayout != 0) {
            ?? r0 = new View.OnAttachStateChangeListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.2
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    Log.d("BSS_FaceEnrollActivity", "onViewAttachedToWindow");
                    if (Utils.isDesktopStandaloneMode(FaceEnrollActivity.this.getApplicationContext())) {
                        ViewGroup viewGroup = (ViewGroup) FaceEnrollActivity.this.getWindow().getDecorView();
                        View findViewById = viewGroup.findViewById(viewGroup.getResources().getIdentifier("reduce_window", "id", "android"));
                        if (findViewById != null) {
                            findViewById.setVisibility(8);
                        }
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    Log.d("BSS_FaceEnrollActivity", "onViewDetachedFromWindow");
                    FaceEnrollActivity.this.finish();
                }
            };
            this.mAttachStateChangeListener = r0;
            relativeLayout.addOnAttachStateChangeListener(r0);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        super.onMultiWindowModeChanged(z, configuration);
        Log.d("BSS_FaceEnrollActivity", "onMultiWindowModeChanged : " + z);
        if (z) {
            Toast.makeText(this, R.string.face_doesnt_support_multi_window_text, 0).show();
            setEnrollResult(0);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        Log.d("BSS_FaceEnrollActivity", "onPause");
        super.onPause();
        setRequestedOrientation(4);
        Log.d("BSS_FaceEnrollActivity", "stopEnrollment");
        if (this.mIsFaceManagerBinded) {
            this.mIsFaceManagerBinded = false;
            CancellationSignal cancellationSignal = this.mCancellationSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
                this.mCancellationSignal = null;
            }
            if (this.mFaceManager != null && (this.mIsChallengeCreated || !isFinishing())) {
                this.mFaceManager.revokeChallenge(this.mSensorId, this.mUserId, this.mChallenge);
            }
        }
        CountDownTimer countDownTimer = this.mShowPoseGuideTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mShowPoseGuideTimer = null;
        }
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.mTts.shutdown();
            this.mTts = null;
        }
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
            this.mSoundPool = null;
        }
        HandlerThread handlerThread = this.mPreviewCBThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        if (this.mIsSetupWizard) {
            setEnrollResult(-2);
        } else {
            if (isFinishing()) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("biometrics_settings_destroy", true);
            setResult(0, intent);
            finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x016f  */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.onResume():void");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        Log.d("BSS_FaceEnrollActivity", "onStop");
        super.onStop();
        if (this.mFoldStateListener == null) {
            return;
        }
        SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
    }

    public final void runTextToSpeech(int i, String str) {
        if (!this.mIsTalkbackEnabled || this.mTts == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.mTts.speak(str, i, null);
    }

    public final void setEnrollResult(int i) {
        AnonymousClass2 anonymousClass2;
        Log.d("BSS_FaceEnrollActivity", "setEnrollResult to " + i);
        AlertDialog alertDialog = this.mErrorDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            RelativeLayout relativeLayout = this.mMainLayout;
            if (relativeLayout != null && (anonymousClass2 = this.mAttachStateChangeListener) != null) {
                relativeLayout.removeOnAttachStateChangeListener(anonymousClass2);
                this.mAttachStateChangeListener = null;
            }
            Intent intent = new Intent();
            intent.putExtra("face_wear_glasses", this.mIsWearingGlasses);
            setResult(i, intent);
            finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setHelpString(int r5, boolean r6) {
        /*
            r4 = this;
            android.widget.TextView r0 = r4.mFaceHelpGuideText
            if (r0 == 0) goto L77
            boolean r1 = r4.mIsGlassGuideShown
            if (r1 == 0) goto La
            goto L77
        La:
            r1 = 330(0x14a, float:4.62E-43)
            r2 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r6 == 0) goto L21
            int r6 = r0.getVisibility()
            if (r6 != 0) goto L21
            android.view.animation.AlphaAnimation r5 = getAlphaAnimation(r3, r2, r1)
            android.widget.TextView r4 = r4.mFaceHelpGuideText
            r4.startAnimation(r5)
            return
        L21:
            r6 = 1
            r0 = 0
            if (r5 == r6) goto L55
            r6 = 11
            if (r5 == r6) goto L50
            r6 = 19
            if (r5 == r6) goto L48
            r6 = 21
            if (r5 == r6) goto L55
            r6 = 1017(0x3f9, float:1.425E-42)
            if (r5 == r6) goto L48
            r6 = 3
            if (r5 == r6) goto L40
            r6 = 4
            if (r5 == r6) goto L50
            r6 = 5
            if (r5 == r6) goto L50
            r5 = 0
            goto L5c
        L40:
            r5 = 2131755139(0x7f100083, float:1.9141149E38)
            java.lang.String r5 = r4.getString(r5)
            goto L5c
        L48:
            r5 = 2131755138(0x7f100082, float:1.9141147E38)
            java.lang.String r5 = r4.getString(r5)
            goto L5c
        L50:
            java.lang.String r5 = r4.getPositionGuideText(r0)
            goto L5c
        L55:
            r5 = 2131755136(0x7f100080, float:1.9141143E38)
            java.lang.String r5 = r4.getString(r5)
        L5c:
            android.widget.TextView r6 = r4.mFaceHelpGuideText
            r6.setText(r5)
            android.view.animation.AlphaAnimation r5 = getAlphaAnimation(r2, r3, r1)
            android.widget.TextView r6 = r4.mFaceHelpGuideText
            int r6 = r6.getVisibility()
            if (r6 == 0) goto L72
            android.widget.TextView r6 = r4.mFaceHelpGuideText
            r6.setVisibility(r0)
        L72:
            android.widget.TextView r4 = r4.mFaceHelpGuideText
            r4.startAnimation(r5)
        L77:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity.setHelpString(int, boolean):void");
    }

    public final void showFaceErrorDialog(final int i, CharSequence charSequence) {
        AlertDialog.Builder onDismissListener = new AlertDialog.Builder(this, (!Utils.isColorThemeEnabled(this) || Utils.isNightThemeOn(this)) ? android.R.style.Theme.DeviceDefault.Dialog.Alert : 0).setMessage(charSequence).setPositiveButton(R.string.face_enroll_error_button_ok, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
                int i2 = i;
                int i3 = FaceEnrollActivity.$r8$clinit;
                faceEnrollActivity.getClass();
                if (i2 != 1005 && i2 != 1 && i2 != 2) {
                    faceEnrollActivity.setEnrollResult(0);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("biometrics_settings_destroy", true);
                faceEnrollActivity.setResult(-3, intent);
                faceEnrollActivity.finish();
            }
        });
        if (i == 1005) {
            onDismissListener.setTitle(getString(R.string.face_enroll_error_title));
        } else if (i == 4) {
            onDismissListener.setTitle(getString(R.string.face_error_title_no_space));
        } else {
            onDismissListener.setTitle(getString(R.string.face_error_unable_to_process_popup_title));
        }
        View view = this.mSecuredKnoxLogoView;
        if (view != null) {
            view.setVisibility(4);
        }
        AlertDialog create = onDismissListener.create();
        this.mErrorDialog = create;
        create.setCanceledOnTouchOutside(false);
        this.mErrorDialog.show();
        this.mErrorDialog.getButton(-1).setTextColor(getResources().getColor(R.color.fingerprint_verification_negative_text_color));
    }

    public final void showFolderOpenDialog() {
        AlertDialog create = new AlertDialog.Builder(this, (!Utils.isColorThemeEnabled(this) || Utils.isNightThemeOn(this)) ? android.R.style.Theme.DeviceDefault.Dialog.Alert : 0).setTitle(getResources().getString(R.string.face_enroll_error_title)).setMessage(getResources().getString(R.string.face_enroll_error_folder_open)).setPositiveButton(R.string.face_enroll_error_button_ok, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
                int i2 = FaceEnrollActivity.$r8$clinit;
                faceEnrollActivity.setEnrollResult(0);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollActivity$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                FaceEnrollActivity faceEnrollActivity = FaceEnrollActivity.this;
                int i = FaceEnrollActivity.$r8$clinit;
                faceEnrollActivity.setEnrollResult(0);
            }
        }).create();
        create.show();
        create.getButton(-1).setTextColor(getResources().getColor(R.color.fingerprint_verification_negative_text_color));
    }

    public final void startEnrollFragment() {
        if (isFinishing()) {
            return;
        }
        this.mEnrollFragment = new FaceEnrollFragment();
        FragmentManagerImpl fragmentManagerImpl = this.mFragments.mHost.fragmentManager;
        fragmentManagerImpl.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
        backStackRecord.doAddOp(R.id.face_fragment_view, this.mEnrollFragment, null, 2);
        backStackRecord.commitInternal(true);
    }

    public final void startEnrollment() {
        if (Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY && !Utils.getFolderOpenState(this, this.mIsSupportDualDisplay)) {
            showFolderOpenDialog();
            Log.d("BSS_FaceEnrollActivity", "startEnrollment fail : Not open flip");
        } else if (this.mIsFaceManagerBinded) {
            Log.d("BSS_FaceEnrollActivity", "startEnrollment");
            this.mCancellationSignal = new CancellationSignal();
            if (this.mIsWearingGlasses) {
                this.mFaceManager.semPauseEnroll();
            }
            TextureView textureView = (TextureView) findViewById(R.id.face_preview);
            this.mTextureView = textureView;
            this.mFaceManager.enroll(this.mUserId, this.mHWToken, this.mCancellationSignal, this.mEnrollmentCallback, new int[0], textureView != null ? new Surface(textureView.getSurfaceTexture()) : null, true, new FaceEnrollOptions.Builder().build());
        }
    }

    public final void startHelpGuideEffect(String str) {
        TextView textView = this.mFaceGuideText;
        if (textView == null) {
            Log.d("BSS_FaceEnrollActivity", "Help view is null : close effect");
        } else {
            if (this.mIsGlassGuideShown) {
                return;
            }
            textView.setText(str);
        }
    }
}
