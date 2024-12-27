package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintEnrollOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.bio.fingerprint.SemFingerprintManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SALoggingHelper;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FingerprintEnrollActivity extends Activity implements View.OnClickListener, View.OnTouchListener, TextureView.SurfaceTextureListener {
    public static final int TIME_ENROLL_DELAY;
    public LinearLayout mAddButtonArea;
    public final AnonymousClass15 mAuthCallback;
    public Handler mAuthErrorHandler;
    public ImageView mAuthErrorImage;
    public AnonymousClass5 mAuthErrorRunnable;
    public TextView mAuthErrorText;
    public long mChallenge;
    public int mDisplayType;
    public boolean mDoNotMove;
    public FingerprintEnrollGuideFrame mEnrollGuideFrame;
    public Handler mEnrollHandler;
    public CountDownTimer mEnrollStartTimer;
    public int mEnrolledCount;
    public CancellationSignal mEnrollmentCancel;
    public Toast mExitToast;
    public Rect mFingerPosition;
    public FingerprintManager mFingerprintManager;
    public RelativeLayout mFirstGuideScreen;
    public int mGuideStep;
    public TextView mGuideTitle;
    public Handler mHideErrorHandler;
    public AnonymousClass5 mHideErrorRunnable;
    public ImageView mImageViewKnoxBi;
    public boolean mIsCalledFingerLeave;
    public boolean mIsCaptureStarted;
    public boolean mIsChallengeCreated;
    public boolean mIsChallengeRequested;
    public boolean mIsEnrollRequested;
    public boolean mIsSupportDualDisplay;
    public boolean mIsTalkbackEnabled;
    public boolean mIsTouchedFingerFrame;
    public boolean mIsTouchedOutside;
    public boolean mIsTouchedSensorRectView;
    public boolean mIsTspBlock;
    public boolean mIsUSA;
    public LiftFingerMessage mLiftFingerMessage;
    public int mLogging_Dirty;
    public int mLogging_DoNotMove;
    public int mLogging_LiftOff;
    public int mLogging_Ok;
    public int mLogging_Partial;
    public int mLogging_PressHarder;
    public int mLogging_TooShort;
    public int mLogging_UpAndDown;
    public IBinder mMaskViewToken;
    public MediaPlayer mMediaPlayer;
    public Button mNextButton;
    public RelativeLayout mNextButtonArea;
    public PowerManager mPowerManager;
    public RelativeLayout mRegisterScreen;
    public RelativeLayout mSecondGuideScreen;
    public FingerprintEnrollSensorHelper mSensorHelper;
    public View mSensorPositionView;
    public View mSensorRectView;
    public Handler mShowErrorHandler;
    public AnonymousClass11 mShowErrorRunnable;
    public FingerprintEnrollSideGuideFrame mSideSensorFrame;
    public Surface mSurface;
    public TextureView mTextureView;
    public Handler mTouchGuideHandler;
    public FingerprintEnrollActivity$$ExternalSyntheticLambda5 mTouchGuideRunnable;
    public int mTouchGuideStrId;
    public final AnonymousClass21 mTouchGuideViewListener;
    public TextView mTxtViewProgress;
    public LinearLayout mUnfoldGuideScreen;
    public int mCurrentPercent = 0;
    public TextToSpeech mTts = null;
    public boolean mBackEnabled = true;
    public int mFilmErrorCount = 0;
    public FingerprintProgressEffectView mFinger_ProgressView = null;
    public boolean mIsShowErrorMsg = false;
    public boolean mIsFirstGuideShow = false;
    public boolean mIsFirstGuideShowClose = false;
    public boolean mIsShownLiftMsg = false;
    public boolean mIsCalledLiftMsg = false;
    public Handler mMsgHandler = null;
    public Runnable mMsgRunnable = null;
    public EnrollState mEnrollState = EnrollState.NONE;
    public byte[] mToken = null;
    public long mGkPwHandle = 0;
    public boolean mIsPauseRegistration = false;
    public boolean mIsFromSetupWizard = false;
    public boolean mIsRegisterCompleted = false;
    public boolean mIsFinishRegistration = false;
    public boolean mIsSkipGuideScreen = false;
    public int mUserId = 0;
    public boolean mIsRotateGuideShow = false;
    public boolean mIsSwipeEnrollGuideShow = false;
    public boolean mIsFoldingGuideShow = false;
    public boolean mIsSwipeEnrollTouchGuideShow = false;
    public boolean mIsSupportFoldEnroll = false;
    public int mSensorStatus = 0;
    public int mCompleteCheckDuration = 1000;
    public boolean mIsRearSensor = false;
    public boolean mIsSideSensor = false;
    public boolean mIsDisplaySensor = false;
    public boolean mIsFingerGestureSet = false;
    public boolean mIsReCreated = false;
    public boolean mIsButtonClicked = false;
    public boolean mIsAdditionalRegistration = false;
    public boolean mIsBackSecond = false;
    public boolean mIsVibrationSupport = false;
    public boolean mIsShowSensorErrorDialog = false;
    public boolean mIsBlockedPowerKey = false;
    public boolean mIsForcedPortrait = false;
    public boolean mIsFromKnoxFingerprintPlus = false;
    public long mEnrollStartRemainTime = 200;
    public final AnonymousClass1 mBackHandler = new Handler() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            FingerprintEnrollActivity.this.mIsBackSecond = false;
        }
    };
    public final AnonymousClass4 mEnrollmentCallback = new FingerprintManager.EnrollmentCallback() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.4
        public final void onEnrollmentError(int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", "onEnrollmentError : errMsgId = " + i + " , errString = " + ((Object) charSequence));
            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            int i2 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
            fingerprintEnrollActivity.showSensorErrorDialog(i, charSequence);
        }

        public final void onEnrollmentHelp(final int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", "onEnrollmentHelp : helpMsgId = " + i + " , helpString = " + ((Object) charSequence));
            final FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            if (fingerprintEnrollActivity.mIsPauseRegistration || fingerprintEnrollActivity.mIsRegisterCompleted) {
                return;
            }
            fingerprintEnrollActivity.getClass();
            final String charSequence2 = charSequence != null ? charSequence.toString() : "";
            fingerprintEnrollActivity.runOnUiThread(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.6
                @Override // java.lang.Runnable
                public final void run() {
                    int i2;
                    FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame;
                    int i3 = 0;
                    int i4 = i;
                    if (i4 == 0) {
                        FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                        if (fingerprintEnrollActivity2.mIsTspBlock) {
                            fingerprintEnrollActivity2.mIsTspBlock = false;
                            fingerprintEnrollActivity2.removeErrorMessageHandler();
                            Handler handler = fingerprintEnrollActivity2.mHideErrorHandler;
                            if (handler != null) {
                                handler.postDelayed(fingerprintEnrollActivity2.mHideErrorRunnable, 100L);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (i4 == 1 || i4 == 2) {
                        FingerprintEnrollActivity fingerprintEnrollActivity3 = FingerprintEnrollActivity.this;
                        String str = charSequence2;
                        fingerprintEnrollActivity3.mLogging_Partial++;
                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            fingerprintEnrollActivity3.showErrorMessage(i4, str);
                            return;
                        }
                        fingerprintEnrollActivity3.tuneTouchGuideFrame();
                        boolean z = fingerprintEnrollActivity3.mIsTalkbackEnabled;
                        if (!z || (i2 = fingerprintEnrollActivity3.mTouchGuideStrId) <= 0) {
                            fingerprintEnrollActivity3.showErrorMessage(i4, fingerprintEnrollActivity3.getString(z ? R.string.fingerprint_enroll_partial_tts : R.string.fingerprint_enroll_partial));
                            return;
                        } else {
                            fingerprintEnrollActivity3.runTextToSpeech(0, fingerprintEnrollActivity3.getString(i2));
                            return;
                        }
                    }
                    if (i4 == 3) {
                        FingerprintEnrollActivity fingerprintEnrollActivity4 = FingerprintEnrollActivity.this;
                        fingerprintEnrollActivity4.mLogging_Dirty++;
                        fingerprintEnrollActivity4.showErrorMessage(i4, charSequence2);
                        return;
                    }
                    if (i4 != 4) {
                        if (i4 == 5) {
                            FingerprintEnrollActivity fingerprintEnrollActivity5 = FingerprintEnrollActivity.this;
                            String str2 = charSequence2;
                            fingerprintEnrollActivity5.mLogging_TooShort++;
                            if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                fingerprintEnrollActivity5.showErrorMessage(i4, fingerprintEnrollActivity5.getString(R.string.fingerprint_enroll_longer));
                                return;
                            } else {
                                fingerprintEnrollActivity5.showErrorMessage(i4, str2);
                                return;
                            }
                        }
                        if (i4 == 10013) {
                            FingerprintEnrollActivity fingerprintEnrollActivity6 = FingerprintEnrollActivity.this;
                            if (fingerprintEnrollActivity6.mIsAdditionalRegistration) {
                                return;
                            }
                            fingerprintEnrollActivity6.showGuideScreen(304);
                            return;
                        }
                        switch (i4) {
                            case 1001:
                                break;
                            case 1002:
                                FingerprintEnrollActivity fingerprintEnrollActivity7 = FingerprintEnrollActivity.this;
                                fingerprintEnrollActivity7.mLogging_UpAndDown++;
                                if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                    fingerprintEnrollActivity7.showErrorMessage(i4, fingerprintEnrollActivity7.getString(R.string.fingerprint_duplicate_guide_msg));
                                    break;
                                } else {
                                    fingerprintEnrollActivity7.mEnrollGuideFrame.tuneMovingArea(true);
                                    fingerprintEnrollActivity7.mEnrollGuideFrame.moveNext();
                                    fingerprintEnrollActivity7.showErrorMessage(i4, fingerprintEnrollActivity7.getString(R.string.fingerprint_enroll_move_finger));
                                    break;
                                }
                            case 1003:
                                FingerprintEnrollActivity fingerprintEnrollActivity8 = FingerprintEnrollActivity.this;
                                String str3 = charSequence2;
                                fingerprintEnrollActivity8.mLogging_PressHarder++;
                                if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                    fingerprintEnrollActivity8.showErrorMessage(i4, str3);
                                    break;
                                } else {
                                    fingerprintEnrollActivity8.showErrorMessage(i4, fingerprintEnrollActivity8.getString(R.string.fingerprint_enroll_longer));
                                    break;
                                }
                            case 1004:
                                FingerprintEnrollActivity fingerprintEnrollActivity9 = FingerprintEnrollActivity.this;
                                String str4 = charSequence2;
                                if (fingerprintEnrollActivity9.mIsFirstGuideShow) {
                                    fingerprintEnrollActivity9.hideGuideScreen(300);
                                }
                                fingerprintEnrollActivity9.mIsTspBlock = true;
                                fingerprintEnrollActivity9.showErrorMessage(0, str4);
                                break;
                            default:
                                switch (i4) {
                                    case 10001:
                                        FingerprintEnrollActivity fingerprintEnrollActivity10 = FingerprintEnrollActivity.this;
                                        int i5 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                                        fingerprintEnrollActivity10.getClass();
                                        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX && fingerprintEnrollActivity10.mIsCalledFingerLeave) {
                                            fingerprintEnrollActivity10.mIsCalledLiftMsg = false;
                                            fingerprintEnrollActivity10.mEnrollGuideFrame.setProgressResult(3);
                                            fingerprintEnrollActivity10.mEnrollGuideFrame.finishScan();
                                        }
                                        FingerprintProgressEffectView fingerprintProgressEffectView = fingerprintEnrollActivity10.mFinger_ProgressView;
                                        if (fingerprintProgressEffectView != null) {
                                            fingerprintProgressEffectView.setFingerStatus(0);
                                        }
                                        Handler handler2 = fingerprintEnrollActivity10.mMsgHandler;
                                        if (handler2 != null) {
                                            handler2.removeCallbacks(fingerprintEnrollActivity10.mMsgRunnable);
                                            fingerprintEnrollActivity10.mMsgHandler = null;
                                        }
                                        LiftFingerMessage liftFingerMessage = fingerprintEnrollActivity10.mLiftFingerMessage;
                                        if (liftFingerMessage != null) {
                                            liftFingerMessage.interrupt();
                                            fingerprintEnrollActivity10.mLiftFingerMessage = null;
                                        }
                                        if (fingerprintEnrollActivity10.mIsShownLiftMsg) {
                                            Handler handler3 = new Handler();
                                            fingerprintEnrollActivity10.mMsgHandler = handler3;
                                            AnonymousClass5 anonymousClass5 = new AnonymousClass5(i3, fingerprintEnrollActivity10);
                                            fingerprintEnrollActivity10.mMsgRunnable = anonymousClass5;
                                            handler3.postDelayed(anonymousClass5, 330L);
                                            break;
                                        }
                                        break;
                                    case 10002:
                                        FingerprintEnrollActivity fingerprintEnrollActivity11 = FingerprintEnrollActivity.this;
                                        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = fingerprintEnrollActivity11.mSideSensorFrame;
                                        if (fingerprintEnrollSideGuideFrame != null && fingerprintEnrollSideGuideFrame.mState == 1) {
                                            fingerprintEnrollSideGuideFrame.mTimerScan.start();
                                            fingerprintEnrollSideGuideFrame.mState = 2;
                                        }
                                        Handler handler4 = fingerprintEnrollActivity11.mTouchGuideHandler;
                                        if (handler4 != null) {
                                            fingerprintEnrollActivity11.mIsTouchedFingerFrame = false;
                                            handler4.removeCallbacks(fingerprintEnrollActivity11.mTouchGuideRunnable);
                                            fingerprintEnrollActivity11.mEnrollGuideFrame.startScan();
                                        }
                                        boolean z2 = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
                                        if (z2 && !fingerprintEnrollActivity11.mIsCaptureStarted) {
                                            fingerprintEnrollActivity11.runTextToSpeech(0, fingerprintEnrollActivity11.getString(R.string.fingerprint_tts_guide_scan_activated));
                                        }
                                        fingerprintEnrollActivity11.mIsCaptureStarted = true;
                                        fingerprintEnrollActivity11.mIsCalledFingerLeave = false;
                                        PowerManager powerManager = fingerprintEnrollActivity11.mPowerManager;
                                        if (powerManager != null) {
                                            powerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                                        }
                                        if (fingerprintEnrollActivity11.mIsFirstGuideShow) {
                                            fingerprintEnrollActivity11.hideGuideScreen(300);
                                            if (fingerprintEnrollActivity11.mIsDisplaySensor) {
                                                fingerprintEnrollActivity11.runTextToSpeech(0, fingerprintEnrollActivity11.getString(R.string.sec_fingerprint_sensor_activated));
                                            }
                                        }
                                        LiftFingerMessage liftFingerMessage2 = fingerprintEnrollActivity11.mLiftFingerMessage;
                                        if (liftFingerMessage2 != null) {
                                            liftFingerMessage2.interrupt();
                                            fingerprintEnrollActivity11.mLiftFingerMessage = null;
                                        }
                                        fingerprintEnrollActivity11.mIsCalledLiftMsg = false;
                                        if (z2) {
                                            fingerprintEnrollActivity11.setFingerGuideTitle(400);
                                            fingerprintEnrollActivity11.mIsShownLiftMsg = false;
                                            if (fingerprintEnrollActivity11.mIsShowErrorMsg) {
                                                fingerprintEnrollActivity11.startViewAnimation(fingerprintEnrollActivity11.mGuideTitle, 204);
                                            }
                                        }
                                        FingerprintProgressEffectView fingerprintProgressEffectView2 = fingerprintEnrollActivity11.mFinger_ProgressView;
                                        if (fingerprintProgressEffectView2 != null) {
                                            fingerprintProgressEffectView2.setFingerStatus(1);
                                            break;
                                        }
                                        break;
                                    case 10003:
                                        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX && (fingerprintEnrollGuideFrame = FingerprintEnrollActivity.this.mEnrollGuideFrame) != null) {
                                            fingerprintEnrollGuideFrame.addTouchValidation(2);
                                            break;
                                        }
                                        break;
                                    case 10004:
                                        FingerprintEnrollActivity fingerprintEnrollActivity12 = FingerprintEnrollActivity.this;
                                        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame2 = fingerprintEnrollActivity12.mSideSensorFrame;
                                        if (fingerprintEnrollSideGuideFrame2 != null && fingerprintEnrollSideGuideFrame2.mState == 2) {
                                            fingerprintEnrollSideGuideFrame2.mTimerInnerReady.start();
                                            fingerprintEnrollSideGuideFrame2.mTimerOuterReady.start();
                                            fingerprintEnrollSideGuideFrame2.mState = 1;
                                        }
                                        fingerprintEnrollActivity12.mIsCalledFingerLeave = true;
                                        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                            fingerprintEnrollActivity12.mEnrollGuideFrame.setProgressResult(3);
                                            fingerprintEnrollActivity12.mEnrollGuideFrame.finishScan();
                                            if (fingerprintEnrollActivity12.mDoNotMove) {
                                                fingerprintEnrollActivity12.mDoNotMove = false;
                                                fingerprintEnrollActivity12.mIsShownLiftMsg = false;
                                                fingerprintEnrollActivity12.showErrorMessage(-1, fingerprintEnrollActivity12.getString(R.string.fingerprint_enroll_dont_move));
                                            } else if (fingerprintEnrollActivity12.mIsCalledLiftMsg) {
                                                if (fingerprintEnrollActivity12.mIsShownLiftMsg) {
                                                    fingerprintEnrollActivity12.mIsShownLiftMsg = false;
                                                    fingerprintEnrollActivity12.setFingerGuideTitle(400);
                                                    fingerprintEnrollActivity12.startViewAnimation(fingerprintEnrollActivity12.mGuideTitle, 204);
                                                }
                                                if (fingerprintEnrollActivity12.mIsTalkbackEnabled) {
                                                    int talkBackPositionIndex = fingerprintEnrollActivity12.mEnrollGuideFrame.getTalkBackPositionIndex();
                                                    fingerprintEnrollActivity12.mEnrollGuideFrame.moveNext();
                                                    int talkBackPositionIndex2 = fingerprintEnrollActivity12.mEnrollGuideFrame.getTalkBackPositionIndex();
                                                    fingerprintEnrollActivity12.runTextToSpeech(1, fingerprintEnrollActivity12.getString(((talkBackPositionIndex == 8 && talkBackPositionIndex2 == 5) || (talkBackPositionIndex == 5 && talkBackPositionIndex2 == 2)) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_up_inch : R.string.fingerprint_tts_guide_move_up : ((talkBackPositionIndex == 4 && talkBackPositionIndex2 == 7) || (talkBackPositionIndex == 0 && talkBackPositionIndex2 == 3) || (talkBackPositionIndex == 3 && talkBackPositionIndex2 == 6)) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_down_inch : R.string.fingerprint_tts_guide_move_down : ((talkBackPositionIndex == 2 && talkBackPositionIndex2 == 1) || (talkBackPositionIndex == 1 && talkBackPositionIndex2 == 0)) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_left_inch : R.string.fingerprint_tts_guide_move_left : ((talkBackPositionIndex == 7 && talkBackPositionIndex2 == 8) || (talkBackPositionIndex == 3 && talkBackPositionIndex2 == 4)) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_right_inch : R.string.fingerprint_tts_guide_move_right : (talkBackPositionIndex == 5 && talkBackPositionIndex2 == 1) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_up_left_inch : R.string.fingerprint_tts_guide_move_up_left : (talkBackPositionIndex == 1 && talkBackPositionIndex2 == 3) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_down_left_inch : R.string.fingerprint_tts_guide_move_down_left : ((talkBackPositionIndex == 7 && talkBackPositionIndex2 == 5) || (talkBackPositionIndex == 6 && talkBackPositionIndex2 == 4)) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_up_right_inch : R.string.fingerprint_tts_guide_move_up_right : (talkBackPositionIndex == 4 && talkBackPositionIndex2 == 7) ? fingerprintEnrollActivity12.mIsUSA ? R.string.fingerprint_tts_guide_move_down_right_inch : R.string.fingerprint_tts_guide_move_down_right : R.string.fingerprint_tts_enroll_guide));
                                                } else {
                                                    fingerprintEnrollActivity12.mEnrollGuideFrame.moveNext();
                                                }
                                            }
                                        }
                                        fingerprintEnrollActivity12.mIsCalledLiftMsg = false;
                                        break;
                                    case 10005:
                                        FingerprintEnrollActivity fingerprintEnrollActivity13 = FingerprintEnrollActivity.this;
                                        fingerprintEnrollActivity13.mLogging_Ok++;
                                        if (fingerprintEnrollActivity13.mIsShowErrorMsg) {
                                            fingerprintEnrollActivity13.startViewAnimation(null, 206);
                                            fingerprintEnrollActivity13.mIsShowErrorMsg = false;
                                            fingerprintEnrollActivity13.removeErrorMessageHandler();
                                            fingerprintEnrollActivity13.setFingerGuideTitle(fingerprintEnrollActivity13.mCurrentPercent >= 100 ? 402 : 400);
                                        }
                                        LiftFingerMessage liftFingerMessage3 = fingerprintEnrollActivity13.mLiftFingerMessage;
                                        if (liftFingerMessage3 != null) {
                                            liftFingerMessage3.interrupt();
                                            fingerprintEnrollActivity13.mLiftFingerMessage = null;
                                        }
                                        fingerprintEnrollActivity13.mIsCalledLiftMsg = true;
                                        LiftFingerMessage liftFingerMessage4 = fingerprintEnrollActivity13.new LiftFingerMessage();
                                        fingerprintEnrollActivity13.mLiftFingerMessage = liftFingerMessage4;
                                        liftFingerMessage4.start();
                                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                            if (fingerprintEnrollActivity13.mGuideTitle.getVisibility() != 0) {
                                                fingerprintEnrollActivity13.startViewAnimation(fingerprintEnrollActivity13.mGuideTitle, 204);
                                                break;
                                            }
                                        } else {
                                            fingerprintEnrollActivity13.mEnrollGuideFrame.setProgressResult(1);
                                            break;
                                        }
                                        break;
                                    case 10006:
                                        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                            FingerprintEnrollActivity.this.mEnrollGuideFrame.setProgressResult(3);
                                            FingerprintEnrollActivity.this.mEnrollGuideFrame.finishScan();
                                            break;
                                        }
                                        break;
                                }
                        }
                        return;
                    }
                    FingerprintEnrollActivity fingerprintEnrollActivity14 = FingerprintEnrollActivity.this;
                    String str5 = charSequence2;
                    int i6 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                    fingerprintEnrollActivity14.showErrorMessage(i4, str5);
                }
            });
        }

        public final void onEnrollmentProgress(int i) {
            int i2;
            int i3 = 4;
            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            if (fingerprintEnrollActivity.mIsPauseRegistration || fingerprintEnrollActivity.mIsRegisterCompleted) {
                return;
            }
            int i4 = 100 - i;
            int i5 = FingerprintProgressEffectView.$r8$clinit;
            if (i4 < 80) {
                double log = Math.log(50);
                i4 = (i4 > 0 ? 9 - (i4 / 8) : 0) + ((int) (((Math.log(150 - i) - log) / (Math.log(130) - log)) * 80.0d));
            }
            FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
            int i6 = fingerprintEnrollActivity2.mCurrentPercent;
            if (i6 == i4) {
                return;
            }
            if (i6 == 0) {
                fingerprintEnrollActivity2.mGuideTitle.performAccessibilityAction(128, null);
                i2 = 1;
            } else {
                i2 = 0;
            }
            FingerprintEnrollActivity.this.mCurrentPercent = i4;
            Log.i("BSS_FingerprintEnrollActivity", "onEnrollmentProgress : " + FingerprintEnrollActivity.this.mCurrentPercent);
            FingerprintEnrollActivity fingerprintEnrollActivity3 = FingerprintEnrollActivity.this;
            fingerprintEnrollActivity3.mTxtViewProgress.setText(TextUtils.expandTemplate(fingerprintEnrollActivity3.getText(R.string.sec_fingerprint_percent_format), String.format("%d", Integer.valueOf(FingerprintEnrollActivity.this.mCurrentPercent))));
            FingerprintEnrollActivity.this.mTxtViewProgress.performAccessibilityAction(128, null);
            FingerprintProgressEffectView fingerprintProgressEffectView = FingerprintEnrollActivity.this.mFinger_ProgressView;
            if (fingerprintProgressEffectView != null) {
                fingerprintProgressEffectView.setPercent(r3.mCurrentPercent);
            }
            FingerprintEnrollActivity fingerprintEnrollActivity4 = FingerprintEnrollActivity.this;
            if (!fingerprintEnrollActivity4.mIsShowErrorMsg && (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX || fingerprintEnrollActivity4.mCurrentPercent < 100)) {
                fingerprintEnrollActivity4.runTextToSpeech(i2, fingerprintEnrollActivity4.getString(R.string.fingerprint_confirm_percent_for_talkback, new Object[]{Integer.valueOf(fingerprintEnrollActivity4.mCurrentPercent)}));
            }
            FingerprintEnrollActivity.this.getClass();
            FingerprintEnrollActivity fingerprintEnrollActivity5 = FingerprintEnrollActivity.this;
            if (fingerprintEnrollActivity5.mCurrentPercent == 100) {
                fingerprintEnrollActivity5.mEnrollState = EnrollState.NONE;
                if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                    fingerprintEnrollActivity5.mEnrollGuideFrame.completeScan();
                    FingerprintEnrollActivity fingerprintEnrollActivity6 = FingerprintEnrollActivity.this;
                    FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = fingerprintEnrollActivity6.mEnrollGuideFrame;
                    if (fingerprintEnrollGuideFrame != null) {
                        fingerprintEnrollGuideFrame.mTouchViewListener = null;
                        fingerprintEnrollGuideFrame.mRectFingerIcon = null;
                    }
                    Handler handler = fingerprintEnrollActivity6.mTouchGuideHandler;
                    if (handler != null) {
                        handler.removeCallbacks(fingerprintEnrollActivity6.mTouchGuideRunnable);
                    }
                }
                FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = FingerprintEnrollActivity.this.mSideSensorFrame;
                if (fingerprintEnrollSideGuideFrame != null) {
                    fingerprintEnrollSideGuideFrame.hide();
                    FingerprintEnrollActivity.this.mSideSensorFrame = null;
                }
                FingerprintEnrollActivity fingerprintEnrollActivity7 = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity7.mIsRegisterCompleted = true;
                fingerprintEnrollActivity7.mIsRotateGuideShow = false;
                fingerprintEnrollActivity7.mBackEnabled = true;
                fingerprintEnrollActivity7.setResult(-1);
                new Handler().postDelayed(new AnonymousClass5(i3, fingerprintEnrollActivity7), 300L);
                SALoggingHelper.insertEventLogging(1, fingerprintEnrollActivity7.mLogging_Ok);
                SALoggingHelper.insertEventLogging(2, fingerprintEnrollActivity7.mLogging_UpAndDown);
                SALoggingHelper.insertEventLogging(3, fingerprintEnrollActivity7.mLogging_TooShort);
                SALoggingHelper.insertEventLogging(4, fingerprintEnrollActivity7.mLogging_Partial);
                SALoggingHelper.insertEventLogging(5, fingerprintEnrollActivity7.mLogging_Dirty);
                SALoggingHelper.insertEventLogging(6, fingerprintEnrollActivity7.mLogging_LiftOff);
                SALoggingHelper.insertEventLogging(7, fingerprintEnrollActivity7.mLogging_PressHarder);
                SALoggingHelper.insertEventLogging(8, fingerprintEnrollActivity7.mLogging_DoNotMove);
                SALoggingHelper.insertSALog(-9999L, String.valueOf(8234), null, null);
                if (fingerprintEnrollActivity7.mIsFromSetupWizard) {
                    SALoggingHelper.insertEventLogging(8262, 1);
                } else {
                    SALoggingHelper.insertEventLogging(8262, 2);
                }
            }
        }
    };

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$15, reason: invalid class name */
    public final class AnonymousClass15 extends SemFingerprintManager.AuthenticationCallback {
        public AnonymousClass15() {
        }

        public final void onAuthenticationAcquired(int i) {
            Log.d("BSS_FingerprintEnrollActivity", "(acquireinfo : " + i + ")");
            if (i == 10012) {
                Context baseContext = FingerprintEnrollActivity.this.getBaseContext();
                int i2 = FingerprintEnrollActivity.this.mUserId;
                Utils.playVibration(baseContext, 5);
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                FingerprintEnrollActivity.m8$$Nest$mshowAuthenticateResult(fingerprintEnrollActivity, i, fingerprintEnrollActivity.getString(R.string.fingerprint_register_swipe_enroll_guide_tap_text));
            }
        }

        public final void onAuthenticationError(int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", ((Object) charSequence) + "(errMsgId : " + i + ")");
            if (i != 5) {
                FingerprintEnrollActivity.m8$$Nest$mshowAuthenticateResult(FingerprintEnrollActivity.this, 101, charSequence.toString());
                new Handler().postDelayed(new AnonymousClass5(7, this), 1500L);
            }
        }

        public final void onAuthenticationFailed() {
            Log.d("BSS_FingerprintEnrollActivity", "onAuthenticationFailed");
            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            FingerprintEnrollActivity.m8$$Nest$mshowAuthenticateResult(fingerprintEnrollActivity, 101, fingerprintEnrollActivity.getString(R.string.fingerprint_enroll_test_fingerprint_no_match));
        }

        public final void onAuthenticationHelp(int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", ((Object) charSequence) + "(helpMsgId : " + i + ")");
            FingerprintEnrollActivity.m8$$Nest$mshowAuthenticateResult(FingerprintEnrollActivity.this, i, charSequence.toString());
        }

        public final void onAuthenticationSucceeded(SemFingerprintManager.AuthenticationResult authenticationResult) {
            Log.d("BSS_FingerprintEnrollActivity", "onAuthenticationSucceeded");
            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            FingerprintEnrollActivity.m8$$Nest$mshowAuthenticateResult(fingerprintEnrollActivity, 0, fingerprintEnrollActivity.getString(R.string.fingerprint_register_swipe_enroll_guide_success));
            FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = FingerprintEnrollActivity.this.mSideSensorFrame;
            if (fingerprintEnrollSideGuideFrame != null) {
                fingerprintEnrollSideGuideFrame.hide();
            }
            ((LinearLayout) FingerprintEnrollActivity.this.findViewById(R.id.done2_button_layout)).setVisibility(0);
            FingerprintEnrollActivity.this.showKnoxBi(false);
        }
    }

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass5(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Button button;
            switch (this.$r8$classId) {
                case 0:
                    FingerprintEnrollActivity fingerprintEnrollActivity = (FingerprintEnrollActivity) this.this$0;
                    if (fingerprintEnrollActivity.mIsShownLiftMsg) {
                        fingerprintEnrollActivity.mIsShownLiftMsg = false;
                        fingerprintEnrollActivity.setFingerGuideTitle(400);
                        FingerprintEnrollActivity fingerprintEnrollActivity2 = (FingerprintEnrollActivity) this.this$0;
                        if (!fingerprintEnrollActivity2.mIsShowErrorMsg) {
                            fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mGuideTitle, 210);
                            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                FingerprintEnrollActivity fingerprintEnrollActivity3 = (FingerprintEnrollActivity) this.this$0;
                                fingerprintEnrollActivity3.startViewAnimation(fingerprintEnrollActivity3.mTxtViewProgress, 204);
                            }
                        }
                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            FingerprintEnrollActivity fingerprintEnrollActivity4 = (FingerprintEnrollActivity) this.this$0;
                            fingerprintEnrollActivity4.runTextToSpeech(1, String.valueOf(fingerprintEnrollActivity4.mGuideTitle.getText()));
                            break;
                        }
                    }
                    break;
                case 1:
                    FingerprintEnrollActivity fingerprintEnrollActivity5 = (FingerprintEnrollActivity) this.this$0;
                    FingerprintEnrollActivity.m8$$Nest$mshowAuthenticateResult(fingerprintEnrollActivity5, 100, fingerprintEnrollActivity5.getString(R.string.fingerprint_enroll_test_fingerprint_description));
                    break;
                case 2:
                    FingerprintEnrollActivity fingerprintEnrollActivity6 = (FingerprintEnrollActivity) this.this$0;
                    if (fingerprintEnrollActivity6.mIsShowErrorMsg) {
                        fingerprintEnrollActivity6.mIsShowErrorMsg = false;
                        try {
                            Thread.sleep(130L);
                        } catch (InterruptedException unused) {
                            Log.d("BSS_FingerprintEnrollActivity", "mHideErrorRunnable : Interrupted");
                        }
                        ((FingerprintEnrollActivity) this.this$0).setFingerGuideTitle(400);
                        FingerprintEnrollActivity fingerprintEnrollActivity7 = (FingerprintEnrollActivity) this.this$0;
                        fingerprintEnrollActivity7.startViewAnimation(fingerprintEnrollActivity7.mGuideTitle, 210);
                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            FingerprintEnrollActivity fingerprintEnrollActivity8 = (FingerprintEnrollActivity) this.this$0;
                            fingerprintEnrollActivity8.startViewAnimation(fingerprintEnrollActivity8.mTxtViewProgress, 204);
                            break;
                        }
                    }
                    break;
                case 3:
                    View findViewById = ((FingerprintEnrollActivity) this.this$0).findViewById(R.id.continue_raised_button_layout);
                    Button button2 = (Button) ((FingerprintEnrollActivity) this.this$0).findViewById(R.id.continue_raised_button);
                    if (findViewById != null && button2 != null) {
                        findViewById.setVisibility(0);
                        button2.semSetButtonShapeEnabled(true);
                        break;
                    }
                    break;
                case 4:
                    LiftFingerMessage liftFingerMessage = ((FingerprintEnrollActivity) this.this$0).mLiftFingerMessage;
                    final LinearLayout linearLayout = null;
                    if (liftFingerMessage != null) {
                        liftFingerMessage.interrupt();
                        ((FingerprintEnrollActivity) this.this$0).mLiftFingerMessage = null;
                    }
                    ((FingerprintEnrollActivity) this.this$0).showKnoxBi(false);
                    final FingerprintProgressEffectView fingerprintProgressEffectView = ((FingerprintEnrollActivity) this.this$0).mFinger_ProgressView;
                    if (fingerprintProgressEffectView != null) {
                        LottieAnimationView lottieAnimationView = fingerprintProgressEffectView.mFingerLottieEffect;
                        if (lottieAnimationView != null && lottieAnimationView.getVisibility() == 0) {
                            fingerprintProgressEffectView.mFingerLottieEffect.animate().alpha(RecyclerView.DECELERATION_RATE).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintProgressEffectView.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    FingerprintProgressEffectView fingerprintProgressEffectView2 = FingerprintProgressEffectView.this;
                                    fingerprintProgressEffectView2.removeView(fingerprintProgressEffectView2.mFingerLottieEffect);
                                }
                            });
                        }
                        ImageView imageView = fingerprintProgressEffectView.mSuccessImageView;
                        if (imageView != null) {
                            fingerprintProgressEffectView.addView(imageView);
                        }
                    }
                    boolean z = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
                    if (!z) {
                        FingerprintEnrollActivity fingerprintEnrollActivity9 = (FingerprintEnrollActivity) this.this$0;
                        fingerprintEnrollActivity9.startViewAnimation(fingerprintEnrollActivity9.mTxtViewProgress, 203);
                    }
                    int semGetMaxEnrollmentNumber = ((FingerprintEnrollActivity) this.this$0).mFingerprintManager.semGetMaxEnrollmentNumber();
                    FingerprintEnrollActivity fingerprintEnrollActivity10 = (FingerprintEnrollActivity) this.this$0;
                    List enrolledFingerprints = fingerprintEnrollActivity10.mFingerprintManager.getEnrolledFingerprints(fingerprintEnrollActivity10.mUserId);
                    if (enrolledFingerprints != null) {
                        ((FingerprintEnrollActivity) this.this$0).mEnrolledCount = enrolledFingerprints.size();
                    } else {
                        ((FingerprintEnrollActivity) this.this$0).mEnrolledCount = semGetMaxEnrollmentNumber;
                    }
                    Log.d("BSS_FingerprintEnrollActivity", "enrolledCount = " + ((FingerprintEnrollActivity) this.this$0).mEnrolledCount + " | maxCount = " + semGetMaxEnrollmentNumber);
                    ((FingerprintEnrollActivity) this.this$0).setFingerGuideTitle(403);
                    FingerprintEnrollActivity fingerprintEnrollActivity11 = (FingerprintEnrollActivity) this.this$0;
                    fingerprintEnrollActivity11.startViewAnimation(fingerprintEnrollActivity11.mGuideTitle, 205);
                    FingerprintEnrollActivity fingerprintEnrollActivity12 = (FingerprintEnrollActivity) this.this$0;
                    if (fingerprintEnrollActivity12.mEnrolledCount >= semGetMaxEnrollmentNumber || fingerprintEnrollActivity12.mIsFromKnoxFingerprintPlus) {
                        if (!fingerprintEnrollActivity12.mIsFromSetupWizard) {
                            if (z) {
                                fingerprintEnrollActivity12.mCompleteCheckDuration += 1000;
                            }
                            linearLayout = (LinearLayout) fingerprintEnrollActivity12.findViewById(R.id.done_button_layout);
                        }
                    } else if (fingerprintEnrollActivity12.mIsFromSetupWizard) {
                        linearLayout = fingerprintEnrollActivity12.mAddButtonArea;
                        if (z) {
                            fingerprintEnrollActivity12.mCompleteCheckDuration += 1000;
                        }
                    } else {
                        linearLayout = (LinearLayout) fingerprintEnrollActivity12.findViewById(R.id.add_done_button_layout);
                    }
                    if (linearLayout != null) {
                        if (z) {
                            new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$16$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FingerprintEnrollActivity.AnonymousClass5 anonymousClass5 = FingerprintEnrollActivity.AnonymousClass5.this;
                                    LinearLayout linearLayout2 = linearLayout;
                                    anonymousClass5.getClass();
                                    linearLayout2.setVisibility(0);
                                    FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = ((FingerprintEnrollActivity) anonymousClass5.this$0).mEnrollGuideFrame;
                                    if (fingerprintEnrollGuideFrame != null) {
                                        fingerprintEnrollGuideFrame.hide();
                                    }
                                }
                            }, ((FingerprintEnrollActivity) this.this$0).mCompleteCheckDuration);
                        } else {
                            linearLayout.setVisibility(0);
                        }
                    }
                    FingerprintEnrollActivity fingerprintEnrollActivity13 = (FingerprintEnrollActivity) this.this$0;
                    if (fingerprintEnrollActivity13.mIsFromSetupWizard && fingerprintEnrollActivity13.mNextButtonArea != null && (button = fingerprintEnrollActivity13.mNextButton) != null) {
                        button.setText(R.string.next_button_label);
                        if (!z) {
                            ((FingerprintEnrollActivity) this.this$0).mNextButtonArea.setVisibility(0);
                            break;
                        } else {
                            new Handler(((FingerprintEnrollActivity) this.this$0).getMainLooper()).postDelayed(new FingerprintEnrollActivity$$ExternalSyntheticLambda5(3, this), ((FingerprintEnrollActivity) this.this$0).mCompleteCheckDuration);
                            break;
                        }
                    }
                    break;
                case 5:
                    FingerprintEnrollActivity fingerprintEnrollActivity14 = (FingerprintEnrollActivity) this.this$0;
                    if (!fingerprintEnrollActivity14.mIsTalkbackEnabled || fingerprintEnrollActivity14.mIsCaptureStarted || Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                        if (!fingerprintEnrollActivity14.mIsShowErrorMsg) {
                            fingerprintEnrollActivity14.setFingerGuideTitle(400);
                        }
                        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            FingerprintEnrollActivity fingerprintEnrollActivity15 = (FingerprintEnrollActivity) this.this$0;
                            fingerprintEnrollActivity15.mRegisterScreen.announceForAccessibility(fingerprintEnrollActivity15.getString(R.string.fingerprint_tts_enroll_guide));
                            FingerprintEnrollActivity fingerprintEnrollActivity16 = (FingerprintEnrollActivity) this.this$0;
                            fingerprintEnrollActivity16.mRegisterScreen.announceForAccessibility(fingerprintEnrollActivity16.getString(R.string.fingerprint_tts_guide_scan));
                        }
                    } else {
                        fingerprintEnrollActivity14.setFingerGuideTitle(401);
                        FingerprintEnrollActivity fingerprintEnrollActivity17 = (FingerprintEnrollActivity) this.this$0;
                        fingerprintEnrollActivity17.mIsShownLiftMsg = true;
                        fingerprintEnrollActivity17.startViewAnimation(fingerprintEnrollActivity17.mTxtViewProgress, 211);
                    }
                    FingerprintEnrollActivity fingerprintEnrollActivity18 = (FingerprintEnrollActivity) this.this$0;
                    FingerprintEnrollActivity.m7$$Nest$msetViewVisibility(fingerprintEnrollActivity18, fingerprintEnrollActivity18.mFirstGuideScreen);
                    break;
                case 6:
                    FingerprintEnrollActivity fingerprintEnrollActivity19 = (FingerprintEnrollActivity) this.this$0;
                    FingerprintEnrollActivity.m7$$Nest$msetViewVisibility(fingerprintEnrollActivity19, fingerprintEnrollActivity19.mSecondGuideScreen);
                    break;
                default:
                    FingerprintEnrollActivity fingerprintEnrollActivity20 = FingerprintEnrollActivity.this;
                    int i = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                    fingerprintEnrollActivity20.finishRegistration();
                    break;
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class EnrollState {
        public static final /* synthetic */ EnrollState[] $VALUES;
        public static final EnrollState ENROLL;
        public static final EnrollState NONE;
        public static final EnrollState PAUSE;

        static {
            EnrollState enrollState = new EnrollState("NONE", 0);
            NONE = enrollState;
            EnrollState enrollState2 = new EnrollState("PAUSE", 1);
            PAUSE = enrollState2;
            EnrollState enrollState3 = new EnrollState("ENROLL", 2);
            ENROLL = enrollState3;
            $VALUES = new EnrollState[]{enrollState, enrollState2, enrollState3};
        }

        public static EnrollState valueOf(String str) {
            return (EnrollState) Enum.valueOf(EnrollState.class, str);
        }

        public static EnrollState[] values() {
            return (EnrollState[]) $VALUES.clone();
        }
    }

    public final class LiftFingerMessage extends Thread {
        public LiftFingerMessage() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                    Thread.sleep(1000L);
                } else {
                    Thread.sleep(1500L);
                }
                FingerprintEnrollActivity.this.runOnUiThread(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.LiftFingerMessage.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.d("BSS_FingerprintEnrollActivity", "LiftFingerMessage : run");
                        FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                        if (fingerprintEnrollActivity.mEnrollState == EnrollState.ENROLL && fingerprintEnrollActivity.mIsCalledLiftMsg) {
                            fingerprintEnrollActivity.setFingerGuideTitle(401);
                            FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                            fingerprintEnrollActivity2.mLogging_LiftOff++;
                            fingerprintEnrollActivity2.mIsShownLiftMsg = true;
                            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mTxtViewProgress, 211);
                            }
                            FingerprintEnrollActivity fingerprintEnrollActivity3 = FingerprintEnrollActivity.this;
                            fingerprintEnrollActivity3.startViewAnimation(fingerprintEnrollActivity3.mGuideTitle, 210);
                        }
                    }
                });
            } catch (InterruptedException unused) {
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                if (!fingerprintEnrollActivity.mIsCalledLiftMsg || fingerprintEnrollActivity.mIsRegisterCompleted) {
                    Log.d("BSS_FingerprintEnrollActivity", "LiftFingerMessage : Interrupted ");
                    return;
                }
                Log.d("BSS_FingerprintEnrollActivity", "LiftFingerMessage : Interrupted after start");
                FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity2.mLiftFingerMessage = fingerprintEnrollActivity2.new LiftFingerMessage();
                FingerprintEnrollActivity.this.mLiftFingerMessage.start();
            }
        }
    }

    /* renamed from: -$$Nest$msetViewVisibility, reason: not valid java name */
    public static void m7$$Nest$msetViewVisibility(FingerprintEnrollActivity fingerprintEnrollActivity, ViewGroup viewGroup) {
        fingerprintEnrollActivity.getClass();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setVisibility(8);
            childAt.setAlpha(RecyclerView.DECELERATION_RATE);
        }
    }

    /* renamed from: -$$Nest$mshowAuthenticateResult, reason: not valid java name */
    public static void m8$$Nest$mshowAuthenticateResult(FingerprintEnrollActivity fingerprintEnrollActivity, int i, String str) {
        AnonymousClass5 anonymousClass5;
        if (i == 100 && fingerprintEnrollActivity.mAuthErrorText.getVisibility() == 0) {
            return;
        }
        Handler handler = fingerprintEnrollActivity.mAuthErrorHandler;
        if (handler != null && i != 100) {
            handler.removeCallbacks(fingerprintEnrollActivity.mAuthErrorRunnable);
            fingerprintEnrollActivity.mAuthErrorHandler = null;
        }
        View findViewById = fingerprintEnrollActivity.findViewById(R.id.swipe_enroll_guide_vi_layout);
        View findViewById2 = fingerprintEnrollActivity.findViewById(R.id.swipe_enroll_guide_clip_layout);
        if (findViewById != null && findViewById2 != null) {
            if (i == 10012) {
                if (findViewById2.getVisibility() != 0) {
                    fingerprintEnrollActivity.startViewAnimation(findViewById, 212);
                }
                fingerprintEnrollActivity.startViewAnimation(findViewById2, 200);
                fingerprintEnrollActivity.playTryOutGuide();
            } else if (i == 100) {
                if (findViewById2.getVisibility() != 0) {
                    fingerprintEnrollActivity.startViewAnimation(findViewById, 212);
                    fingerprintEnrollActivity.startViewAnimation(findViewById2, 200);
                }
                fingerprintEnrollActivity.playTryOutGuide();
            } else {
                if (findViewById.getVisibility() != 0) {
                    fingerprintEnrollActivity.startViewAnimation(findViewById2, 212);
                }
                fingerprintEnrollActivity.startViewAnimation(findViewById, 200);
            }
        }
        PowerManager powerManager = fingerprintEnrollActivity.mPowerManager;
        if (powerManager != null) {
            powerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
        }
        if (i == 100) {
            TextView textView = fingerprintEnrollActivity.mAuthErrorText;
            if (textView != null && str != null) {
                textView.setVisibility(4);
                fingerprintEnrollActivity.mAuthErrorText.setText(str);
                fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mAuthErrorText, 200);
                str = fingerprintEnrollActivity.getString(R.string.fingerprint_enroll_test_fingerprint_title);
            }
        } else if (fingerprintEnrollActivity.mAuthErrorText.getVisibility() == 0) {
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mAuthErrorText, 203);
        }
        fingerprintEnrollActivity.runTextToSpeech(0, str);
        TextView textView2 = (TextView) fingerprintEnrollActivity.findViewById(R.id.swipe_enroll_guide_title);
        if (textView2 != null && str != null) {
            textView2.setVisibility(4);
            textView2.setText(str);
            if (i == 100) {
                textView2.setTypeface(Typeface.create(Typeface.create("sec", 0), 600, false));
            } else {
                textView2.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
            }
            fingerprintEnrollActivity.startViewAnimation(textView2, 210);
        }
        ImageView imageView = fingerprintEnrollActivity.mAuthErrorImage;
        if (imageView != null && i != 10012) {
            imageView.setVisibility(4);
            if (i == 0) {
                fingerprintEnrollActivity.mAuthErrorImage.setImageResource(R.drawable.fingerprint_trial_success);
            } else {
                fingerprintEnrollActivity.mAuthErrorImage.setImageResource(R.drawable.fingerprint_trial_error);
            }
            fingerprintEnrollActivity.mAuthErrorImage.setAlpha(1.0f);
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mAuthErrorImage, 200);
        }
        if (i != 0) {
            Handler handler2 = fingerprintEnrollActivity.mAuthErrorHandler;
            if (handler2 != null && (anonymousClass5 = fingerprintEnrollActivity.mAuthErrorRunnable) != null) {
                handler2.removeCallbacks(anonymousClass5);
            }
            fingerprintEnrollActivity.mAuthErrorRunnable = new AnonymousClass5(1, fingerprintEnrollActivity);
            Handler handler3 = new Handler();
            fingerprintEnrollActivity.mAuthErrorHandler = handler3;
            handler3.postDelayed(fingerprintEnrollActivity.mAuthErrorRunnable, 2000L);
        }
    }

    static {
        int i;
        if (Utils.Config.FP_FEATURE_SENSOR_IS_REAR) {
            boolean z = Utils.DEBUG;
            i = "JP".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO")) ? 2000 : 1000;
        } else {
            i = 0;
        }
        TIME_ENROLL_DELAY = i;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$4] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$21] */
    public FingerprintEnrollActivity() {
        new AnonymousClass15();
        this.mTouchGuideViewListener = new View.OnTouchListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.21
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                    Handler handler = fingerprintEnrollActivity.mTouchGuideHandler;
                    if (handler != null) {
                        handler.postDelayed(fingerprintEnrollActivity.mTouchGuideRunnable, 300L);
                    }
                    FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = FingerprintEnrollActivity.this.mEnrollGuideFrame;
                    if (fingerprintEnrollGuideFrame != null) {
                        fingerprintEnrollGuideFrame.startScan();
                    }
                    FingerprintEnrollActivity.this.mIsTouchedFingerFrame = true;
                } else if (action == 1) {
                    FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                    if (fingerprintEnrollActivity2.mIsTouchedFingerFrame) {
                        Handler handler2 = fingerprintEnrollActivity2.mTouchGuideHandler;
                        if (handler2 != null) {
                            handler2.removeCallbacks(fingerprintEnrollActivity2.mTouchGuideRunnable);
                        }
                        TextView textView = FingerprintEnrollActivity.this.mGuideTitle;
                        if (textView != null) {
                            textView.setText(R.string.fingerprint_enroll_guide);
                        }
                        FingerprintEnrollActivity fingerprintEnrollActivity3 = FingerprintEnrollActivity.this;
                        fingerprintEnrollActivity3.mIsTouchedFingerFrame = false;
                        fingerprintEnrollActivity3.tuneTouchGuideFrame();
                        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame2 = FingerprintEnrollActivity.this.mEnrollGuideFrame;
                        if (fingerprintEnrollGuideFrame2 != null) {
                            fingerprintEnrollGuideFrame2.finishScan();
                        }
                    }
                    FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame3 = FingerprintEnrollActivity.this.mEnrollGuideFrame;
                    if (fingerprintEnrollGuideFrame3 != null) {
                        Log.i("BSS_EnrollGuideFrame", "isFingerMoved=" + ("000" + Integer.toBinaryString(fingerprintEnrollGuideFrame3.mTouchValidationSet)).substring(r1.length() - 3));
                        int i = fingerprintEnrollGuideFrame3.mTouchValidationSet;
                        if (i == 2 || i == 6) {
                            FingerprintEnrollActivity fingerprintEnrollActivity4 = FingerprintEnrollActivity.this;
                            fingerprintEnrollActivity4.mDoNotMove = true;
                            fingerprintEnrollActivity4.mLogging_DoNotMove++;
                        }
                    }
                }
                return false;
            }
        };
    }

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

    public final void changeGuideVideo(int i) {
        Uri guideClipURI = getGuideClipURI(i, 2);
        if (i == 300) {
            this.mIsFirstGuideShowClose = true;
        }
        if (guideClipURI != null) {
            try {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.setDataSource(this, guideClipURI);
                this.mMediaPlayer.setLooping(true);
                this.mMediaPlayer.prepare();
                this.mMediaPlayer.start();
            } catch (RuntimeException unused) {
                Log.e("BSS_FingerprintEnrollActivity", "Runtime Exception");
                finish();
            } catch (Exception unused2) {
                Log.e("BSS_FingerprintEnrollActivity", "Exception");
                finish();
            }
        }
    }

    public final void finishRegistration() {
        if (this.mIsFinishRegistration) {
            Log.d("BSS_FingerprintEnrollActivity", "finishRegistration() already run.");
            return;
        }
        Log.d("BSS_FingerprintEnrollActivity", "finishRegistration()");
        this.mIsFinishRegistration = true;
        this.mIsReCreated = false;
        Intent intent = new Intent();
        intent.putExtra("hw_auth_token", this.mToken);
        intent.putExtra("enrollResult", -1);
        setResult(-1, intent);
        finish();
        semOverridePendingTransition(0, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x004c, code lost:
    
        if (r7 == 1) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x004e, code lost:
    
        r6 = "sec_fingerprint_v_01";
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0056, code lost:
    
        if (com.samsung.android.biometrics.app.setting.Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.net.Uri getGuideClipURI(int r6, int r7) {
        /*
            r5 = this;
            r0 = 5
            r1 = 1
            r2 = 0
            switch(r6) {
                case 300: goto L30;
                case 301: goto L28;
                case 302: goto L16;
                case 303: goto L8;
                default: goto L6;
            }
        L6:
            r6 = r2
            goto L59
        L8:
            boolean r6 = r5.mIsSupportFoldEnroll
            if (r6 == 0) goto L13
            int r6 = r5.mDisplayType
            if (r6 != r0) goto L13
            java.lang.String r6 = "sec_fingerprint_fold"
            goto L59
        L13:
            java.lang.String r6 = "sec_fingerprint_unfold"
            goto L59
        L16:
            android.content.res.Resources r6 = r5.getResources()
            android.content.res.Configuration r6 = r6.getConfiguration()
            int r6 = r6.semDisplayDeviceType
            if (r6 != r0) goto L25
            java.lang.String r6 = "sec_fingerprint_recognition_tap_fold"
            goto L59
        L25:
            java.lang.String r6 = "sec_fingerprint_recognition_tap"
            goto L59
        L28:
            if (r7 != r1) goto L2d
            java.lang.String r6 = "sec_fingerprint_h_01"
            goto L59
        L2d:
            java.lang.String r6 = "sec_fingerprint_h_02"
            goto L59
        L30:
            boolean r6 = r5.mIsSupportFoldEnroll
            java.lang.String r3 = "sec_fingerprint_v_02"
            java.lang.String r4 = "sec_fingerprint_v_01"
            if (r6 == 0) goto L52
            android.content.res.Resources r6 = r5.getResources()
            android.content.res.Configuration r6 = r6.getConfiguration()
            int r6 = r6.semDisplayDeviceType
            if (r6 != r0) goto L4c
            if (r7 != r1) goto L49
            java.lang.String r6 = "sec_fingerprint_v_fold_01"
            goto L59
        L49:
            java.lang.String r6 = "sec_fingerprint_v_fold_02"
            goto L59
        L4c:
            if (r7 != r1) goto L50
        L4e:
            r6 = r4
            goto L59
        L50:
            r6 = r3
            goto L59
        L52:
            if (r7 != r1) goto L50
            boolean r6 = com.samsung.android.biometrics.app.setting.Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX
            if (r6 != 0) goto L50
            goto L4e
        L59:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L7d
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "android.resource://"
            r7.<init>(r0)
            java.lang.String r5 = r5.getPackageName()
            r7.append(r5)
            java.lang.String r5 = "/raw/"
            r7.append(r5)
            r7.append(r6)
            java.lang.String r5 = r7.toString()
            android.net.Uri r2 = android.net.Uri.parse(r5)
        L7d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.getGuideClipURI(int, int):android.net.Uri");
    }

    public final void hideGuideScreen(int i) {
        FingerprintManager fingerprintManager;
        FingerprintManager fingerprintManager2;
        EnrollState enrollState = EnrollState.ENROLL;
        EnrollState enrollState2 = EnrollState.PAUSE;
        switch (i) {
            case 300:
                startViewAnimation(this.mFirstGuideScreen, 201);
                startViewAnimation(this.mRegisterScreen, 200);
                this.mIsFirstGuideShow = false;
                View view = this.mSensorPositionView;
                if (view != null) {
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.mSensorPositionView);
                    }
                    this.mSensorPositionView = null;
                }
                new Handler().postDelayed(new AnonymousClass5(5, this), 500L);
                break;
            case 301:
                startViewAnimation(this.mSecondGuideScreen, 201);
                startViewAnimation(this.mRegisterScreen, 200);
                if (this.mIsFromSetupWizard) {
                    showKnoxBi(true);
                    RelativeLayout relativeLayout = this.mNextButtonArea;
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(4);
                    }
                }
                setFingerGuideTitle(400);
                new Handler().postDelayed(new AnonymousClass5(6, this), 300L);
                new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.9
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintManager fingerprintManager3;
                        FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                        if (fingerprintEnrollActivity.mEnrollState != EnrollState.PAUSE || (fingerprintManager3 = fingerprintEnrollActivity.mFingerprintManager) == null) {
                            return;
                        }
                        fingerprintEnrollActivity.mEnrollState = EnrollState.ENROLL;
                        fingerprintManager3.semResumeEnroll();
                    }
                }, 500L);
                break;
            case 303:
                this.mIsFoldingGuideShow = false;
                startViewAnimation(this.mUnfoldGuideScreen, 211);
                if (!this.mIsSwipeEnrollTouchGuideShow && this.mGuideStep == 0 && this.mEnrollState == enrollState2 && (fingerprintManager = this.mFingerprintManager) != null) {
                    this.mEnrollState = enrollState;
                    fingerprintManager.semResumeEnroll();
                    FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = this.mSideSensorFrame;
                    if (fingerprintEnrollSideGuideFrame != null) {
                        fingerprintEnrollSideGuideFrame.show();
                    }
                    if (this.mIsCaptureStarted || !this.mIsFirstGuideShow) {
                        startViewAnimation(this.mRegisterScreen, 200);
                        break;
                    }
                }
                break;
            case 304:
                this.mIsSwipeEnrollTouchGuideShow = false;
                if (this.mEnrollState == enrollState2 && (fingerprintManager2 = this.mFingerprintManager) != null) {
                    this.mEnrollState = enrollState;
                    fingerprintManager2.semResumeEnroll();
                    FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame2 = this.mSideSensorFrame;
                    if (fingerprintEnrollSideGuideFrame2 != null) {
                        fingerprintEnrollSideGuideFrame2.show();
                    }
                    if (this.mIsCaptureStarted) {
                        startViewAnimation(this.mRegisterScreen, 200);
                        break;
                    }
                }
                break;
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.destroyDrawingCache();
            this.mTextureView.setSurfaceTextureListener(null);
            this.mTextureView = null;
        }
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
            this.mSurface = null;
        }
    }

    public final void initLayoutHeight() {
        int i;
        int i2;
        getDisplay().getRealSize(new Point());
        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
            int i3 = (int) (((1.0f - getResources().getFloat(R.dimen.sec_fingerprint_register_width_ratio)) * r1.x) / 2.0f);
            if (Utils.isTablet() && Utils.isScreenLandscape(this)) {
                i = R.dimen.sec_fingerprint_register_progress_first_vi_height_ratio_tablet_land;
                i2 = R.dimen.sec_fingerprint_register_progress_vi_height_ratio_tablet_land;
            } else {
                i = R.dimen.sec_fingerprint_register_progress_first_vi_height_ratio;
                i2 = R.dimen.sec_fingerprint_register_progress_vi_height_ratio;
            }
            findViewById(R.id.first_guide_screen).setPadding(i3, 0, i3, 0);
            setViewWeight(R.id.register_first_guide_vi_layout, i);
            setViewWeight(R.id.register_second_guide_vi_layout, i);
            setViewWeight(R.id.finger_view, i2);
            findViewById(R.id.register_layout).setPadding(i3, 0, i3, 0);
            findViewById(R.id.second_guide_screen_layout).setPadding(i3, 0, i3, 0);
            return;
        }
        if (Utils.isTablet()) {
            int i4 = (int) (getResources().getFloat(R.dimen.sec_fingerprint_register_width_ratio) * r1.x);
            View findViewById = findViewById(R.id.register_screen);
            if (findViewById != null) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.width = i4;
                layoutParams.topMargin = 0;
                findViewById.requestLayout();
            }
            View findViewById2 = findViewById(R.id.first_guide_screen);
            if (findViewById2 != null) {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) findViewById2.getLayoutParams();
                layoutParams2.width = i4;
                layoutParams2.topMargin = 0;
                findViewById2.requestLayout();
            }
            ((LinearLayout.LayoutParams) findViewById(R.id.register_guide_top_margin_layout).getLayoutParams()).weight = getResources().getFloat(R.dimen.sec_fingerprint_enroll_1st_layout_ratio);
            ((LinearLayout.LayoutParams) findViewById(R.id.register_guide_text_layout).getLayoutParams()).weight = getResources().getFloat(R.dimen.sec_fingerprint_enroll_2nd_layout_ratio);
            ((LinearLayout.LayoutParams) findViewById(R.id.finger_area_view).getLayoutParams()).weight = getResources().getFloat(R.dimen.sec_fingerprint_enroll_4th_layout_ratio);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.register_guide_layout);
            linearLayout.invalidate();
            linearLayout.requestLayout();
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.first_guide_screen);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
            if (Utils.isScreenLandscape(this)) {
                layoutParams3.removeRule(2);
            } else {
                layoutParams3.addRule(2, R.id.image_knox_bi);
            }
            ((RelativeLayout.LayoutParams) this.mImageViewKnoxBi.getLayoutParams()).setMarginEnd((int) getResources().getDimension(R.dimen.sec_fingerprint_enroll_knox_bi_marginEnd));
            ((RelativeLayout.LayoutParams) findViewById(R.id.register_first_guide_vi_layout).getLayoutParams()).topMargin = (int) getResources().getDimension(R.dimen.sec_fingerprint_enroll_guide_vi_margin_top);
            relativeLayout.setLayoutParams(layoutParams3);
        }
        double d = getResources().getFloat(R.dimen.sec_fingerprint_register_guide_height_ratio) * r1.y;
        View findViewById3 = findViewById(R.id.register_first_guide_vi_layout);
        if (findViewById3 != null) {
            findViewById3.getLayoutParams().height = (int) d;
            findViewById3.requestLayout();
        }
        View findViewById4 = findViewById(R.id.register_second_guide_vi_layout);
        if (findViewById4 != null) {
            findViewById4.getLayoutParams().height = (int) d;
            findViewById4.requestLayout();
        }
    }

    public final boolean isSystemKeyEventRequested(int i) {
        try {
            return IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSystemKeyEventRequested(i, getComponentName());
        } catch (RemoteException e) {
            Log.e("BSS_FingerprintEnrollActivity", "isSystemKeyEventRequested - " + e);
            return false;
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        if (this.mBackEnabled) {
            finishRegistration();
            return;
        }
        if (!this.mIsBackSecond) {
            this.mIsBackSecond = true;
            if (!Utils.isTalkBackEnabled(this)) {
                sendEmptyMessageDelayed(0, 2000L);
            }
            Toast toast = this.mExitToast;
            if (toast != null) {
                toast.show();
                return;
            }
            return;
        }
        Log.d("BSS_FingerprintEnrollActivity", "Fingerprint enroll screen is terminated by Back key !!");
        this.mIsBackSecond = false;
        Toast toast2 = this.mExitToast;
        if (toast2 != null) {
            toast2.cancel();
        }
        Intent intent = new Intent();
        intent.putExtra("hw_auth_token", this.mToken);
        if (this.mIsRegisterCompleted || this.mIsAdditionalRegistration) {
            SALoggingHelper.insertEventLogging(8237, this.mEnrolledCount);
            intent.putExtra("enrollResult", -1);
            setResult(-1, intent);
        } else {
            intent.putExtra("enrollResult", 0);
            setResult(0, intent);
        }
        super.onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add2_button /* 2131361861 */:
            case R.id.add_button /* 2131361862 */:
                SALoggingHelper.insertSALog(-9999L, String.valueOf(8234), String.valueOf(8235), null);
                if (!this.mIsButtonClicked) {
                    this.mIsButtonClicked = true;
                    this.mIsReCreated = true;
                    this.mIsFinishRegistration = true;
                    Intent intent = new Intent();
                    intent.putExtra("hw_auth_token", this.mToken);
                    intent.putExtra("enrollResult", -1);
                    intent.putExtra("IsReCreated", this.mIsReCreated);
                    setResult(-1, intent);
                    finish();
                    semOverridePendingTransition(0, 0);
                    break;
                }
                break;
            case R.id.done2_button /* 2131361957 */:
            case R.id.done_button /* 2131361960 */:
            case R.id.next_text_btn /* 2131362110 */:
                if (!this.mIsRotateGuideShow) {
                    SALoggingHelper.insertEventLogging(8236, this.mEnrolledCount);
                    if (!this.mIsButtonClicked) {
                        this.mIsButtonClicked = true;
                        startViewAnimation(this.mRegisterScreen, 201);
                        finishRegistration();
                        break;
                    }
                } else {
                    hideGuideScreen(301);
                    break;
                }
                break;
            case R.id.done3_button /* 2131361959 */:
                finishRegistration();
                break;
            case R.id.ok_button /* 2131362119 */:
                if (this.mIsSwipeEnrollTouchGuideShow) {
                    hideGuideScreen(304);
                    break;
                }
                break;
            case R.id.register_button /* 2131362150 */:
                if (this.mEnrollState == EnrollState.NONE) {
                    hideGuideScreen(300);
                    startEnrollStep();
                }
                view.setOnClickListener(null);
                break;
        }
    }

    public void onClickContinue(View view) {
        if (this.mIsRotateGuideShow) {
            hideGuideScreen(301);
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        Log.d("BSS_FingerprintEnrollActivity", "onConfigurationChanged");
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.knox_bi_layout);
        if (frameLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
            layoutParams.weight = getResources().getFloat(R.dimen.sec_fingerprint_register_width_ratio_knox_bi);
            frameLayout.setLayoutParams(layoutParams);
        }
        if (!this.mIsForcedPortrait) {
            initLayoutHeight();
        } else if (this.mIsSupportDualDisplay) {
            boolean z2 = this.mIsSupportFoldEnroll;
            EnrollState enrollState = EnrollState.NONE;
            if (z2) {
                if (this.mEnrollState == enrollState) {
                    this.mDisplayType = configuration.semDisplayDeviceType;
                    initLayoutHeight();
                }
                z = this.mDisplayType != configuration.semDisplayDeviceType;
            } else {
                int i = configuration.semDisplayDeviceType;
                z = this.mDisplayType == 0 && i == 5;
                this.mDisplayType = i;
            }
            EnrollState enrollState2 = EnrollState.PAUSE;
            if (z) {
                this.mGuideStep = 0;
                if (this.mIsFirstGuideShow) {
                    this.mGuideStep = 1;
                } else if (this.mIsRotateGuideShow && this.mEnrollState == enrollState2) {
                    this.mGuideStep = 2;
                } else if (this.mIsSwipeEnrollTouchGuideShow && this.mEnrollState == enrollState2) {
                    this.mGuideStep = 3;
                }
                showGuideScreen(303);
            } else {
                if (!this.mIsSwipeEnrollTouchGuideShow && this.mIsSupportFoldEnroll && this.mEnrollState == enrollState2) {
                    if (this.mGuideStep == 1) {
                        hideGuideScreen(300);
                    }
                    this.mGuideStep = 0;
                }
                hideGuideScreen(303);
                if (!this.mIsRegisterCompleted) {
                    int i2 = this.mGuideStep;
                    if (i2 == 1 || (!this.mIsSkipGuideScreen && this.mEnrollState == enrollState)) {
                        showGuideScreen(300);
                    } else if (i2 == 2) {
                        showGuideScreen(301);
                    } else if (i2 == 3) {
                        showGuideScreen(304);
                    }
                } else if (this.mIsSwipeEnrollGuideShow) {
                    showGuideScreen(302);
                }
            }
        }
        if (this.mIsFirstGuideShow) {
            if (this.mIsTalkbackEnabled) {
                setSensorPosition(this.mSensorPositionView, true);
            } else {
                setSensorPosition(this.mSensorRectView, false);
            }
        }
        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = this.mSideSensorFrame;
        if (fingerprintEnrollSideGuideFrame != null) {
            fingerprintEnrollSideGuideFrame.getVisibility();
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        TextView textView;
        Button button;
        UserInfo userInfo;
        Log.d("BSS_FingerprintEnrollActivity", "onCreate");
        getWindow().addSystemFlags(524288);
        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
            setTitle(getString(R.string.fingerprint_tts_registration_help));
        } else {
            setTitle(" ");
        }
        super.onCreate(bundle);
        Application application = getApplication();
        try {
            com.samsung.context.sdk.samsunganalytics.Configuration configuration = new com.samsung.context.sdk.samsunganalytics.Configuration();
            configuration.enableAutoDeviceId = false;
            configuration.auidType = -1;
            configuration.trackingId = "759-399-5199102";
            configuration.version = SALoggingHelper.getUiVersion(application.getApplicationContext());
            configuration.enableAutoDeviceId = true;
            SamsungAnalytics.getInstanceAndConfig(application, configuration);
        } catch (AnalyticsException unused) {
            Log.e("BSS_SALoggingHelper", "Exception occurred while SA logging setConfiguration");
        }
        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            Log.e("BSS_FingerprintEnrollActivity", "intent should have extra value");
            finish();
            return;
        }
        this.mIsFromSetupWizard = intent.getBooleanExtra("fromSetupWizard", false);
        Log.secD("BSS_FingerprintEnrollActivity", "mIsFromSetupwizard : " + this.mIsFromSetupWizard);
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        Log.secD("BSS_FingerprintEnrollActivity", "mUserId : " + this.mUserId);
        this.mIsSkipGuideScreen = intent.getBooleanExtra("skipGuideScreen", false);
        Log.secD("BSS_FingerprintEnrollActivity", "mIsSkipGuideScreen : " + this.mIsSkipGuideScreen);
        this.mToken = intent.getByteArrayExtra("hw_auth_token");
        this.mChallenge = intent.getLongExtra("challenge", 0L);
        if (intent.hasExtra("gk_pw_handle")) {
            this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
        }
        if (bundle != null) {
            this.mIsReCreated = bundle.getBoolean("IsReCreated", false);
            this.mMaskViewToken = bundle.getBinder("MaskViewToken");
            this.mGkPwHandle = bundle.getLong("gk_pw_handle", 0L);
        } else {
            this.mIsReCreated = intent.getBooleanExtra("IsReCreated", false);
        }
        if (this.mIsReCreated) {
            this.mIsAdditionalRegistration = true;
        }
        Context baseContext = getBaseContext();
        boolean z = Utils.DEBUG;
        if (UserHandle.myUserId() != 0 && (userInfo = UserManager.get(baseContext).getUserInfo(UserHandle.myUserId())) != null && userInfo.isGuest()) {
            Intent intent2 = new Intent();
            intent2.putExtra("enrollResult", 0);
            intent2.putExtra("hw_auth_token", this.mToken);
            setResult(0, intent2);
            finish();
        }
        boolean z2 = getRequestedOrientation() == 1;
        this.mIsForcedPortrait = z2;
        if (this.mIsFromSetupWizard && !z2) {
            setRequestedOrientation(getResources().getInteger(R.integer.sec_fingerprint_enroll_activity_suw_orientation));
        }
        boolean z3 = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
        if (z3) {
            setContentView(R.layout.fingerprint_display_enroll_layout);
        } else {
            setContentView(R.layout.sec_fingerprint_register_v10);
        }
        this.mIsFromKnoxFingerprintPlus = intent.getBooleanExtra("isFromKnoxFingerprintPlus", false);
        Log.d("BSS_FingerprintEnrollActivity", "onCreate : isFromKnoxFingerprintPlus = " + this.mIsFromKnoxFingerprintPlus);
        Vibrator vibrator = (Vibrator) getSystemService(Vibrator.class);
        this.mIsVibrationSupport = vibrator != null && vibrator.semGetSupportedVibrationType() >= 2 && Settings.System.getInt(getBaseContext().getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", 0) > 0;
        this.mIsTalkbackEnabled = Utils.isTalkBackEnabled(this);
        this.mIsUSA = "US".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
        this.mPowerManager = (PowerManager) getSystemService(PowerManager.class);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FingerprintManager.class);
        this.mFingerprintManager = fingerprintManager;
        if (fingerprintManager == null || this.mGkPwHandle == 0) {
            Log.e("BSS_FingerprintEnrollActivity", "FingerprintManager is null or GkPwHandle is 0L.");
            showSensorErrorDialog(0, null);
        } else {
            byte[] bArr = this.mToken;
            if (bArr == null || (bArr.length == 1 && bArr[0] == -1)) {
                Log.i("BSS_FingerprintEnrollActivity", "Token is not available. Request generateChallenge");
                this.mIsChallengeRequested = true;
                this.mFingerprintManager.generateChallenge(this.mUserId, new FingerprintManager.GenerateChallengeCallback() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda4
                    public final void onChallengeGenerated(int i, int i2, long j) {
                        byte[] gatekeeperHAT;
                        FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                        int i3 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                        fingerprintEnrollActivity.getClass();
                        Log.i("BSS_FingerprintEnrollActivity", "Received a result of generateChallenge");
                        fingerprintEnrollActivity.mIsChallengeRequested = false;
                        fingerprintEnrollActivity.mIsChallengeCreated = true;
                        fingerprintEnrollActivity.mChallenge = j;
                        VerifyCredentialResponse verifyGatekeeperPasswordHandle = new LockPatternUtils(fingerprintEnrollActivity).verifyGatekeeperPasswordHandle(fingerprintEnrollActivity.mGkPwHandle, fingerprintEnrollActivity.mChallenge, fingerprintEnrollActivity.mUserId);
                        if (verifyGatekeeperPasswordHandle.isMatched()) {
                            gatekeeperHAT = verifyGatekeeperPasswordHandle.getGatekeeperHAT();
                        } else {
                            Log.e("GatekeeperPasswordProvider", "Unable to request Gatekeeper HAT");
                            gatekeeperHAT = null;
                        }
                        fingerprintEnrollActivity.mToken = gatekeeperHAT;
                        StringBuilder sb = new StringBuilder("Token=");
                        byte[] bArr2 = fingerprintEnrollActivity.mToken;
                        sb.append(bArr2 == null ? "NULL" : Arrays.toString(bArr2));
                        Log.secD("BSS_FingerprintEnrollActivity", sb.toString());
                        if (fingerprintEnrollActivity.mToken == null) {
                            Log.e("BSS_FingerprintEnrollActivity", "Token from generateChallenge is null.");
                            fingerprintEnrollActivity.showSensorErrorDialog(3, null);
                        } else if (fingerprintEnrollActivity.mIsEnrollRequested) {
                            fingerprintEnrollActivity.mIsEnrollRequested = false;
                            Log.d("BSS_FingerprintEnrollActivity", "Received token from generateChallenge so start enroll again.");
                            fingerprintEnrollActivity.startEnrollment();
                        }
                    }
                });
            }
            if (this.mFingerprintManager.getEnrolledFingerprints(this.mUserId).size() >= this.mFingerprintManager.semGetMaxEnrollmentNumber()) {
                Log.w("BSS_FingerprintEnrollActivity", "onCreate: Max Enrolled Fingerprints");
                finish();
            }
            boolean isHardwareDetected = this.mFingerprintManager.isHardwareDetected();
            int semGetSensorStatus = this.mFingerprintManager.semGetSensorStatus();
            this.mSensorStatus = semGetSensorStatus;
            if (!isHardwareDetected || (semGetSensorStatus != 100040 && semGetSensorStatus != 100041)) {
                Log.e("BSS_FingerprintEnrollActivity", "[onCreate] isHardwareDetected = " + isHardwareDetected + ", requestGetSensorStatus = " + this.mSensorStatus);
                showSensorErrorDialog(this.mSensorStatus, null);
            } else if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
                this.mMaskViewToken = this.mFingerprintManager.semAddMaskView();
            }
        }
        this.mIsRearSensor = Utils.Config.FP_FEATURE_SENSOR_IS_REAR;
        this.mIsSideSensor = Utils.Config.FP_FEATURE_SENSOR_IS_SIDE;
        this.mIsDisplaySensor = Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
        Log.d("BSS_FingerprintEnrollActivity", "onCreate : mIsRearSensor=" + this.mIsRearSensor + ",mIsSideSensor=" + this.mIsSideSensor + ",mIsDisplaySensor=" + this.mIsDisplaySensor + ",mIsSensorInLandscape=false");
        boolean z4 = Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY;
        this.mIsSupportDualDisplay = z4;
        boolean z5 = z4 && new FingerprintEnrollSensorHelper(this).height > 0;
        this.mIsSupportFoldEnroll = z5;
        if (!z5) {
            this.mDisplayType = getResources().getConfiguration().semDisplayDeviceType;
        }
        this.mRegisterScreen = (RelativeLayout) findViewById(R.id.register_screen);
        this.mFirstGuideScreen = (RelativeLayout) findViewById(R.id.first_guide_screen);
        if (!z3) {
            this.mSecondGuideScreen = (RelativeLayout) findViewById(R.id.second_guide_screen);
            this.mUnfoldGuideScreen = (LinearLayout) findViewById(R.id.unfold_guide_screen);
        }
        this.mFinger_ProgressView = (FingerprintProgressEffectView) findViewById(R.id.finger_view);
        TextView textView2 = (TextView) findViewById(R.id.progress_text);
        this.mTxtViewProgress = textView2;
        textView2.setText(TextUtils.expandTemplate(getText(R.string.sec_fingerprint_percent_format), String.format("%d", 0)));
        TextView textView3 = (TextView) findViewById(R.id.register_guide_title);
        this.mGuideTitle = textView3;
        Utils.setMaxTextScaleSize(textView3, R.dimen.sec_fingerprint_register_guideText_size);
        this.mGuideTitle.setVisibility(4);
        this.mImageViewKnoxBi = (ImageView) findViewById(R.id.image_knox_bi);
        showKnoxBi(true);
        if (this.mIsTalkbackEnabled && this.mIsDisplaySensor) {
            this.mIsSkipGuideScreen = false;
            this.mIsReCreated = false;
        }
        Button button2 = (Button) findViewById(R.id.add_button);
        if (button2 != null) {
            button2.setContentDescription(getString(R.string.fingerprint_tts_add_fingerprint));
            button2.setOnClickListener(this);
            button2.semSetButtonShapeEnabled(true);
        }
        Button button3 = (Button) findViewById(R.id.done_button);
        if (button3 != null) {
            button3.setOnClickListener(this);
            button3.semSetButtonShapeEnabled(true);
        }
        Button button4 = (Button) findViewById(R.id.done2_button);
        if (button4 != null) {
            button4.setOnClickListener(this);
            button4.semSetButtonShapeEnabled(true);
        }
        if (z3 && (button = (Button) findViewById(R.id.register_button)) != null) {
            button.setOnClickListener(this);
            button.semSetButtonShapeEnabled(true);
        }
        if (this.mIsFromSetupWizard) {
            this.mBackEnabled = false;
            this.mNextButtonArea = (RelativeLayout) findViewById(R.id.next_button_area);
            this.mAddButtonArea = (LinearLayout) findViewById(R.id.add_button_layout);
            Button button5 = (Button) findViewById(R.id.next_text_btn);
            this.mNextButton = button5;
            if (button5 != null) {
                button5.setOnClickListener(this);
            }
            if (z3 && (textView = this.mTxtViewProgress) != null) {
                textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        LinearLayout linearLayout = FingerprintEnrollActivity.this.mAddButtonArea;
                        if (linearLayout != null) {
                            linearLayout.setY(i4);
                        }
                    }
                });
            }
            Button button6 = (Button) findViewById(R.id.add2_button);
            if (button6 != null) {
                button6.setOnClickListener(this);
            }
        }
        initLayoutHeight();
        if (!this.mIsRearSensor) {
            if (this.mIsSideSensor) {
                requestSystemKeyEvent(26, true);
                this.mIsBlockedPowerKey = true;
            } else {
                if (SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "").isEmpty() || Settings.Global.getInt(getContentResolver(), "navigation_bar_gesture_while_hidden", 0) == 0) {
                    this.mBackEnabled = false;
                }
                requestSystemKeyEvent(3, true);
                requestSystemKeyEvent(187, true);
                requestSystemKeyEvent(1001, true);
            }
        }
        if (!this.mIsFromSetupWizard && this.mIsDisplaySensor) {
            getWindow().getDecorView().getWindowInsetsController().hide(WindowInsets.Type.statusBars());
            getWindow().getDecorView().getWindowInsetsController().setSystemBarsBehavior(2);
        }
        boolean z6 = Settings.System.getInt(getContentResolver(), "fingerprint_gesture_quick", 0) != 0;
        this.mIsFingerGestureSet = z6;
        if (z6) {
            Settings.System.putInt(getContentResolver(), "fingerprint_gesture_quick", 0);
        }
        if (!this.mIsSupportFoldEnroll && this.mDisplayType == 5) {
            showGuideScreen(303);
            this.mIsSkipGuideScreen = false;
            return;
        }
        if (this.mIsSkipGuideScreen || this.mIsReCreated || Utils.Config.FEATURE_SUPPORT_DISABLED_MENU_K05) {
            this.mRegisterScreen.setVisibility(0);
            startEnrollStep();
            return;
        }
        showGuideScreen(300);
        if (!this.mIsDisplaySensor || this.mFingerprintManager == null) {
            return;
        }
        Log.d("BSS_FingerprintEnrollActivity", "Request calibration");
        FingerprintManager fingerprintManager2 = this.mFingerprintManager;
        if (fingerprintManager2 == null || !this.mIsDisplaySensor) {
            return;
        }
        if (!this.mIsTalkbackEnabled || z3) {
            fingerprintManager2.semForceCBGE();
            this.mEnrollStartRemainTime = 2000L;
            this.mEnrollStartTimer = new CountDownTimer() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.13
                @Override // android.os.CountDownTimer
                public final void onFinish() {
                    FingerprintEnrollActivity.this.mEnrollStartRemainTime = 200L;
                }

                @Override // android.os.CountDownTimer
                public final void onTick(long j) {
                    FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                    if (j < 200) {
                        j = 200;
                    }
                    fingerprintEnrollActivity.mEnrollStartRemainTime = j;
                }
            }.start();
            View view = new View(this);
            this.mSensorRectView = view;
            view.setClickable(false);
            this.mSensorRectView.setOnTouchListener(new View.OnTouchListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                    int i = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                    fingerprintEnrollActivity.getClass();
                    if (motionEvent.getAction() != 0) {
                        return false;
                    }
                    fingerprintEnrollActivity.mIsTouchedSensorRectView = true;
                    return false;
                }
            });
            setSensorPosition(this.mSensorRectView, false);
        }
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        Log.d("BSS_FingerprintEnrollActivity", "onMultiWindowModeChanged: " + z);
        if (z) {
            Toast.makeText(getApplicationContext(), R.string.fingerprint_doesnt_support_multi_window_text, 0).show();
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onPause() {
        PowerManager powerManager;
        Log.d("BSS_FingerprintEnrollActivity", "onPause");
        super.onPause();
        if (!this.mIsRearSensor) {
            if (!this.mIsSideSensor) {
                if (isSystemKeyEventRequested(3)) {
                    requestSystemKeyEvent(3, false);
                }
                if (isSystemKeyEventRequested(187)) {
                    requestSystemKeyEvent(187, false);
                }
                if (isSystemKeyEventRequested(1001)) {
                    requestSystemKeyEvent(1001, false);
                }
            } else if (isSystemKeyEventRequested(26)) {
                requestSystemKeyEvent(26, false);
                this.mIsBlockedPowerKey = false;
            }
        }
        removeErrorMessageHandler();
        Log.d("BSS_FingerprintEnrollActivity", "cancelEnrollment");
        this.mIsPauseRegistration = true;
        CancellationSignal cancellationSignal = this.mEnrollmentCancel;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.mEnrollmentCancel = null;
        }
        if (this.mFingerprintManager != null && (this.mIsChallengeCreated || !isFinishing())) {
            this.mFingerprintManager.revokeChallenge(this.mUserId, this.mChallenge);
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.destroyDrawingCache();
            this.mTextureView.setSurfaceTextureListener(null);
            this.mTextureView = null;
        }
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = this.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame != null) {
            fingerprintEnrollGuideFrame.hide();
            this.mEnrollGuideFrame = null;
        }
        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = this.mSideSensorFrame;
        if (fingerprintEnrollSideGuideFrame != null) {
            fingerprintEnrollSideGuideFrame.hide();
            this.mSideSensorFrame = null;
        }
        Handler handler = this.mEnrollHandler;
        if (handler != null) {
            handler.removeCallbacks(null);
        }
        CountDownTimer countDownTimer = this.mEnrollStartTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mEnrollStartTimer = null;
        }
        TextToSpeech textToSpeech = this.mTts;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.mTts.shutdown();
            this.mTts = null;
        }
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
            this.mSurface = null;
        }
        LiftFingerMessage liftFingerMessage = this.mLiftFingerMessage;
        if (liftFingerMessage != null) {
            liftFingerMessage.interrupt();
            this.mLiftFingerMessage = null;
        }
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null && Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            fingerprintManager.semRemoveMaskView(this.mMaskViewToken);
        }
        if (this.mIsFingerGestureSet) {
            Settings.System.putInt(getContentResolver(), "fingerprint_gesture_quick", 1);
        }
        if (isFinishing() || this.mIsReCreated) {
            return;
        }
        this.mIsReCreated = false;
        Intent intent = new Intent();
        intent.putExtra("biometrics_settings_destroy", true);
        byte[] bArr = this.mToken;
        if (bArr != null) {
            intent.putExtra("hw_auth_token", bArr);
        }
        intent.putExtra("enrollResult", 1);
        if (!this.mIsFromSetupWizard || this.mIsButtonClicked || (powerManager = this.mPowerManager) == null || !powerManager.isInteractive()) {
            setResult(this.mIsRegisterCompleted ? -1 : 0, intent);
        } else {
            setResult(1, intent);
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("BSS_FingerprintEnrollActivity", "onResume");
        if (Utils.isDesktopStandaloneMode(this)) {
            ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
            View findViewById = viewGroup.findViewById(viewGroup.getResources().getIdentifier("reduce_window", "id", "android"));
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
        }
        Toast makeText = Toast.makeText(this, R.string.fingerscanner_toast_back_text, 0);
        this.mExitToast = makeText;
        if (this.mIsTalkbackEnabled) {
            makeText.addCallback(new Toast.Callback() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.2
                @Override // android.widget.Toast.Callback
                public final void onToastHidden() {
                    FingerprintEnrollActivity.this.mIsBackSecond = false;
                }

                @Override // android.widget.Toast.Callback
                public final void onToastShown() {
                    FingerprintEnrollActivity.this.mIsBackSecond = true;
                }
            });
            TextToSpeech textToSpeech = new TextToSpeech(this, null);
            this.mTts = textToSpeech;
            textToSpeech.setAudioAttributes(new AudioAttributes.Builder().setUsage(11).build());
        }
        this.mIsPauseRegistration = false;
        this.mIsReCreated = false;
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d("BSS_FingerprintEnrollActivity", "onSaveInstanceState");
        bundle.putBoolean("IsReCreated", this.mIsReCreated);
        bundle.putBinder("MaskViewToken", this.mMaskViewToken);
        bundle.putLong("gk_pw_handle", this.mGkPwHandle);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0046 A[Catch: IOException -> 0x00a0, IllegalStateException -> 0x00a9, SecurityException -> 0x00b2, IllegalArgumentException -> 0x00bb, TryCatch #2 {IOException -> 0x00a0, IllegalArgumentException -> 0x00bb, IllegalStateException -> 0x00a9, SecurityException -> 0x00b2, blocks: (B:3:0x0009, B:5:0x000f, B:8:0x0046, B:10:0x004a, B:11:0x0068, B:13:0x007b, B:14:0x008b, B:18:0x0086, B:19:0x0064, B:21:0x0017, B:23:0x001b, B:26:0x0020, B:28:0x0024, B:30:0x002c, B:32:0x0030, B:34:0x0039), top: B:2:0x0009 }] */
    @Override // android.view.TextureView.SurfaceTextureListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSurfaceTextureAvailable(android.graphics.SurfaceTexture r5, int r6, int r7) {
        /*
            r4 = this;
            java.lang.String r6 = "BSS_FingerprintEnrollActivity"
            android.view.Surface r7 = new android.view.Surface
            r7.<init>(r5)
            r4.mSurface = r7
            boolean r5 = r4.mIsFoldingGuideShow     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r7 = 0
            r0 = 1
            if (r5 == 0) goto L17
            r5 = 303(0x12f, float:4.25E-43)
            android.net.Uri r5 = r4.getGuideClipURI(r5, r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
        L15:
            r1 = r0
            goto L44
        L17:
            boolean r5 = r4.mIsFirstGuideShow     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r5 != 0) goto L39
            boolean r5 = r4.mIsSwipeEnrollTouchGuideShow     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r5 == 0) goto L20
            goto L39
        L20:
            boolean r5 = r4.mIsRotateGuideShow     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r5 == 0) goto L2c
            r5 = 301(0x12d, float:4.22E-43)
            android.net.Uri r5 = r4.getGuideClipURI(r5, r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
        L2a:
            r1 = r7
            goto L44
        L2c:
            boolean r5 = r4.mIsSwipeEnrollGuideShow     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r5 == 0) goto L37
            r5 = 302(0x12e, float:4.23E-43)
            android.net.Uri r5 = r4.getGuideClipURI(r5, r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            goto L15
        L37:
            r5 = 0
            goto L2a
        L39:
            r5 = 300(0x12c, float:4.2E-43)
            android.net.Uri r5 = r4.getGuideClipURI(r5, r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            boolean r1 = r4.mIsSwipeEnrollTouchGuideShow     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r1 == 0) goto L2a
            goto L15
        L44:
            if (r5 == 0) goto Lc3
            android.media.MediaPlayer r2 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r2 != 0) goto L64
            android.media.MediaPlayer r2 = new android.media.MediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r2.<init>()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r4.mMediaPlayer = r2     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r2.setAudioStreamType(r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.media.MediaPlayer r2 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r3 = 2500(0x9c4, float:3.503E-42)
            r2.semSetParameter(r3, r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.media.MediaPlayer r2 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r3 = 35004(0x88bc, float:4.9051E-41)
            r2.semSetParameter(r3, r0)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            goto L68
        L64:
            r2.reset()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r7 = r0
        L68:
            android.media.MediaPlayer r0 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.view.Surface r2 = r4.mSurface     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r0.setSurface(r2)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.media.MediaPlayer r0 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r0.setDataSource(r4, r5)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r5.setLooping(r1)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            if (r7 == 0) goto L86
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r5.prepare()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r5.start()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            goto L8b
        L86:
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r5.prepareAsync()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
        L8b:
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$18 r7 = new com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$18     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r7.<init>()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r5.setOnPreparedListener(r7)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            android.media.MediaPlayer r5 = r4.mMediaPlayer     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$19 r7 = new com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$19     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r7.<init>()     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            r5.setOnCompletionListener(r7)     // Catch: java.io.IOException -> La0 java.lang.IllegalStateException -> La9 java.lang.SecurityException -> Lb2 java.lang.IllegalArgumentException -> Lbb
            goto Lc3
        La0:
            java.lang.String r5 = "IOException"
            android.util.Log.e(r6, r5)
            r4.finish()
            goto Lc3
        La9:
            java.lang.String r5 = "IllegalStateException"
            android.util.Log.e(r6, r5)
            r4.finish()
            goto Lc3
        Lb2:
            java.lang.String r5 = "SecurityException"
            android.util.Log.e(r6, r5)
            r4.finish()
            goto Lc3
        Lbb:
            java.lang.String r5 = "IllegalArgumentException"
            android.util.Log.e(r6, r5)
            r4.finish()
        Lc3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.onSurfaceTextureAvailable(android.graphics.SurfaceTexture, int, int):void");
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

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if ((action != 0 && action != 2) || this.mHideErrorHandler == null) {
            return true;
        }
        Log.d("BSS_FingerprintEnrollActivity", "Remove_Error_Runnable");
        this.mHideErrorHandler.removeCallbacks(this.mHideErrorRunnable);
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z || !this.mIsDisplaySensor || this.mIsShowSensorErrorDialog) {
            return;
        }
        Log.d("BSS_FingerprintEnrollActivity", "onWindowFocusChanged : " + z);
        finish();
    }

    public final void playTryOutGuide() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            return;
        }
        try {
            mediaPlayer.stop();
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.start();
        } catch (IOException unused) {
            Log.e("BSS_FingerprintEnrollActivity", "Exception during play TryOut Guide");
        }
    }

    public final void removeErrorMessageHandler() {
        Handler handler = this.mHideErrorHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mHideErrorRunnable);
        }
        Handler handler2 = this.mShowErrorHandler;
        if (handler2 != null) {
            handler2.removeCallbacks(this.mShowErrorRunnable);
        }
    }

    public final void requestSystemKeyEvent(int i, boolean z) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).requestSystemKeyEvent(i, getComponentName(), z);
        } catch (RemoteException e) {
            Log.e("BSS_FingerprintEnrollActivity", "requestSystemKeyEvent - " + e);
        }
    }

    public final void runTextToSpeech(int i, String str) {
        if (this.mTts == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.mTts.speak(str, i, null);
    }

    public final void setFingerGuideTitle(int i) {
        TextView textView = this.mGuideTitle;
        if (textView == null) {
            Log.d("BSS_FingerprintEnrollActivity", "mGuideTitle is NULL");
            return;
        }
        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
            if (i != 403) {
                if (i != 401) {
                    textView.setText(this.mIsTalkbackEnabled ? R.string.fingerprint_tts_enroll_guide : R.string.fingerprint_enroll_guide);
                    return;
                } else {
                    if (this.mIsCaptureStarted) {
                        int i2 = this.mIsTalkbackEnabled ? R.string.fingerprint_tts_enroll_lift : R.string.fingerprint_enroll_lift;
                        textView.setText(i2);
                        runTextToSpeech(1, getString(i2));
                        return;
                    }
                    return;
                }
            }
            View findViewById = findViewById(R.id.register_guide_layout);
            if (findViewById != null) {
                findViewById.setImportantForAccessibility(1);
            }
            this.mGuideTitle.setText(R.string.fingerprint_register_success);
            if (this.mEnrolledCount < this.mFingerprintManager.semGetMaxEnrollmentNumber()) {
                this.mGuideTitle.append("\n" + getString(R.string.fingerprint_register_add_another_fingerprint));
                return;
            }
            return;
        }
        if (i == 400) {
            if (!this.mIsRotateGuideShow) {
                if (this.mIsVibrationSupport) {
                    textView.setText(R.string.sec_fingerprint_register_guide_text);
                    return;
                } else {
                    textView.setText(R.string.sec_fingerprint_register_percentage_goes_up);
                    return;
                }
            }
            textView.setText(R.string.sec_fingerprint_second_guide_title);
            if (this.mIsSupportDualDisplay) {
                this.mGuideTitle.append("\n" + getString(R.string.sec_fingerprint_register_reposition_your_finger));
                return;
            }
            this.mGuideTitle.append("\n" + getString(R.string.fingerprint_rotate_guide_msg));
            return;
        }
        if (i == 401) {
            textView.setText(R.string.fingerprint_register_lift_your_finger);
            runTextToSpeech(1, getString(R.string.fingerprint_register_lift_your_finger));
            return;
        }
        if (i != 403) {
            return;
        }
        textView.setText(R.string.fingerprint_register_success);
        runTextToSpeech(1, getString(R.string.fingerprint_register_success));
        if (this.mEnrolledCount >= this.mFingerprintManager.semGetMaxEnrollmentNumber() || this.mIsFromKnoxFingerprintPlus) {
            return;
        }
        if (!this.mIsTalkbackEnabled) {
            this.mGuideTitle.append("\n" + getString(R.string.fingerprint_register_add_another_fingerprint));
            return;
        }
        this.mGuideTitle.append("\n" + getString(R.string.fingerprint_tts_register_add_another_fingerprint));
        runTextToSpeech(1, getString(R.string.fingerprint_tts_register_add_another_fingerprint));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setSensorPosition(android.view.View r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.setSensorPosition(android.view.View, boolean):void");
    }

    public final void setViewWeight(int i, int i2) {
        View findViewById = findViewById(i);
        float f = getResources().getFloat(i2);
        if (findViewById != null) {
            ((LinearLayout.LayoutParams) findViewById.getLayoutParams()).weight = f;
            findViewById.requestLayout();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$11, java.lang.Runnable] */
    public final void showErrorMessage(int i, final String str) {
        int intDb;
        if (this.mIsTalkbackEnabled && this.mIsTouchedOutside) {
            return;
        }
        Log.d("BSS_FingerprintEnrollActivity", "imageQuality[" + str + "]");
        if (Utils.Config.FP_FEATURE_SENSOR_IS_ULTRASONIC && (i == 1 || i == 1003)) {
            int i2 = this.mFilmErrorCount + 1;
            this.mFilmErrorCount = i2;
            if (i2 == 10 && (intDb = Utils.getIntDb(getBaseContext(), "fingerprint_protective_film_guideline_displayed", true, 0)) < 2) {
                showSensorErrorDialog(600, null);
                Utils.putIntDb(getBaseContext(), "fingerprint_protective_film_guideline_displayed", true, intDb + 1);
                return;
            }
        }
        removeErrorMessageHandler();
        boolean z = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
        if (!z || this.mIsShowErrorMsg) {
            runTextToSpeech(0, str);
        } else {
            runTextToSpeech(1, str);
        }
        if (!this.mIsShowErrorMsg) {
            startViewAnimation(this.mGuideTitle, 203);
            if (!z) {
                startViewAnimation(this.mTxtViewProgress, 203);
            }
            this.mIsShowErrorMsg = true;
        }
        if (!z) {
            runTextToSpeech(1, String.valueOf(this.mGuideTitle.getText()));
        }
        Handler handler = new Handler();
        this.mShowErrorHandler = handler;
        ?? r2 = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.11
            @Override // java.lang.Runnable
            public final void run() {
                TextView textView;
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                if (!fingerprintEnrollActivity.mIsShowErrorMsg || (textView = fingerprintEnrollActivity.mGuideTitle) == null) {
                    return;
                }
                textView.setText(str);
                FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mGuideTitle, 210);
            }
        };
        this.mShowErrorRunnable = r2;
        handler.postDelayed(r2, 200L);
        if (this.mHideErrorHandler == null) {
            this.mHideErrorHandler = new Handler();
            this.mHideErrorRunnable = new AnonymousClass5(2, this);
        }
        if (i != 0) {
            Log.d("BSS_FingerprintEnrollActivity", "Run_Runnable_ErrorMSG : " + this.mHideErrorHandler.postDelayed(this.mHideErrorRunnable, 5000L));
        }
    }

    public final void showGuideScreen(int i) {
        final View findViewById;
        Button button;
        SemPersonaManager semPersonaManager;
        FingerprintManager fingerprintManager;
        FingerprintManager fingerprintManager2;
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.destroyDrawingCache();
            this.mTextureView.setVisibility(8);
            this.mTextureView = null;
        }
        LiftFingerMessage liftFingerMessage = this.mLiftFingerMessage;
        if (liftFingerMessage != null) {
            liftFingerMessage.interrupt();
            this.mLiftFingerMessage = null;
            this.mIsShownLiftMsg = false;
            this.mIsCalledLiftMsg = false;
        }
        EnrollState enrollState = EnrollState.PAUSE;
        EnrollState enrollState2 = EnrollState.ENROLL;
        switch (i) {
            case 300:
                this.mIsFirstGuideShow = true;
                this.mIsFirstGuideShowClose = false;
                TextView textView = (TextView) findViewById(R.id.first_guide_title);
                if (textView != null) {
                    if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                        if (this.mIsRearSensor || this.mIsVibrationSupport) {
                            textView.setText(R.string.fingerscanner_register_text);
                        } else {
                            textView.setText(R.string.sec_fingerprint_register_percentage_goes_up);
                        }
                        if (this.mIsRearSensor && this.mIsTalkbackEnabled) {
                            textView.append(" \n\n");
                            textView.append(getString(R.string.fingerprint_tts_register_guide_rear_key));
                        }
                        if (this.mIsDisplaySensor) {
                            textView.setContentDescription(((Object) textView.getText()) + "\n" + getString(R.string.fingerprint_tts_indisplay_position_guide));
                        }
                    } else if (this.mIsTalkbackEnabled) {
                        textView.setText(R.string.fingerprint_tts_setup_guide);
                    }
                }
                TextView textView2 = (TextView) findViewById(R.id.register_text_first);
                if (textView2 != null) {
                    if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                        if (this.mIsTalkbackEnabled) {
                            textView2.setText(R.string.fingerprint_enroll_setup_guide2_tts);
                        }
                        textView2.setContentDescription(((Object) textView2.getText()) + "\n" + getString(R.string.fingerprint_tts_select_register_button));
                    } else if (this.mIsSideSensor && this.mIsBlockedPowerKey) {
                        textView2.setText(getString(R.string.fingerprint_register_guide_side_key));
                    }
                }
                Utils.setMaxTextScaleSize((TextView) findViewById(R.id.help_guide_title), R.dimen.biometrics_common_guide_title_text_size);
                this.mFirstGuideScreen.setVisibility(0);
                this.mTextureView = (TextureView) findViewById(R.id.register_popup_img_first);
                Settings.System.putInt(getContentResolver(), "fingerprint_guide_shown", 1);
                if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && !Utils.Config.FP_FEATURE_LOCAL_HBM && !Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE && Settings.System.getInt(getContentResolver(), "fingerprint_blf_off_guide_shown", 0) == 0 && Settings.System.getInt(getBaseContext().getContentResolver(), "blue_light_filter", 0) != 0) {
                    Toast.makeText(getBaseContext(), R.string.fingerprint_eye_comfort_shield_off, 0).show();
                    Settings.System.putInt(getContentResolver(), "fingerprint_blf_off_guide_shown", 1);
                }
                if (this.mIsTalkbackEnabled && (findViewById = findViewById(R.id.register_first_guide_vi_layout)) != null) {
                    findViewById.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.22
                        @Override // android.view.View.AccessibilityDelegate
                        public final void sendAccessibilityEvent(View view, int i2) {
                            if (i2 == 32768) {
                                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                                if (fingerprintEnrollActivity.mMediaPlayer != null) {
                                    StringBuilder sb = new StringBuilder(fingerprintEnrollActivity.getString(R.string.fingerprint_tts_animation_showing));
                                    int i3 = FingerprintEnrollActivity.this.mMediaPlayer.isPlaying() ? R.string.fingerprint_tts_stop : R.string.fingerprint_tts_play;
                                    sb.append(", ");
                                    sb.append(FingerprintEnrollActivity.this.getString(i3));
                                    sb.append(", ");
                                    sb.append(FingerprintEnrollActivity.this.getString(R.string.fingerprint_tts_button));
                                    view.setContentDescription(sb);
                                }
                            }
                            super.sendAccessibilityEvent(view, i2);
                        }
                    });
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                            View view2 = findViewById;
                            MediaPlayer mediaPlayer = fingerprintEnrollActivity.mMediaPlayer;
                            if (mediaPlayer != null) {
                                if (mediaPlayer.isPlaying()) {
                                    fingerprintEnrollActivity.mMediaPlayer.pause();
                                    view2.announceForAccessibility(fingerprintEnrollActivity.getString(R.string.fingerprint_tts_stopped));
                                } else {
                                    fingerprintEnrollActivity.mMediaPlayer.start();
                                    view2.announceForAccessibility(fingerprintEnrollActivity.getString(R.string.fingerprint_tts_playing));
                                }
                            }
                        }
                    });
                    break;
                }
                break;
            case 301:
                this.mIsRotateGuideShow = true;
                startViewAnimation(this.mRegisterScreen, 201);
                startViewAnimation(this.mSecondGuideScreen, 207);
                if (this.mIsFromSetupWizard) {
                    showKnoxBi(false);
                    if (this.mNextButtonArea != null && (button = this.mNextButton) != null) {
                        button.setText(R.string.fingerprint_continue_button_text);
                        this.mNextButtonArea.setVisibility(0);
                    }
                } else {
                    new Handler().postDelayed(new AnonymousClass5(3, this), 500L);
                }
                this.mTextureView = (TextureView) findViewById(R.id.register_popup_img_second);
                TextView textView3 = (TextView) findViewById(R.id.second_guide_title);
                if (textView3 != null) {
                    runTextToSpeech(1, String.valueOf(textView3.getText()));
                }
                TextView textView4 = (TextView) findViewById(R.id.second_guide_text);
                if (textView4 != null) {
                    runTextToSpeech(1, String.valueOf(textView4.getText()));
                }
                runTextToSpeech(1, getString(R.string.common_select_continue_button));
                break;
            case 302:
                this.mIsSwipeEnrollGuideShow = true;
                this.mAuthErrorText = (TextView) findViewById(R.id.swipe_enroll_guide_text);
                this.mAuthErrorImage = (ImageView) findViewById(R.id.swipe_enroll_guide_error);
                startViewAnimation(this.mRegisterScreen, 201);
                if (this.mIsFromSetupWizard) {
                    RelativeLayout relativeLayout = this.mNextButtonArea;
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(4);
                    }
                    LinearLayout linearLayout = this.mAddButtonArea;
                    if (linearLayout != null) {
                        linearLayout.setVisibility(4);
                    }
                }
                this.mTextureView = (TextureView) findViewById(R.id.swipe_enroll_guide_vi);
                TextView textView5 = (TextView) findViewById(R.id.swipe_enroll_guide_title);
                if (textView5 != null) {
                    runTextToSpeech(1, String.valueOf(textView5.getText()));
                }
                if (this.mAuthErrorText != null) {
                    if (SemPersonaManager.isKnoxId(this.mUserId)) {
                        Context baseContext = getBaseContext();
                        int i2 = this.mUserId;
                        boolean z = Utils.DEBUG;
                        this.mAuthErrorText.setText(getString(R.string.fingerprint_register_swipe_enroll_guide_text_workspace, new Object[]{(baseContext == null || (semPersonaManager = (SemPersonaManager) baseContext.getSystemService("persona")) == null) ? "Knox" : SemPersonaManager.isSecureFolderId(i2) ? baseContext.getString(R.string.secure_folder_set_ppp_title) : semPersonaManager.getContainerName(i2, baseContext)}));
                    }
                    runTextToSpeech(1, String.valueOf(this.mAuthErrorText.getText()));
                    break;
                }
                break;
            case 303:
                this.mIsFoldingGuideShow = true;
                if (this.mEnrollState == enrollState2 && (fingerprintManager = this.mFingerprintManager) != null) {
                    this.mEnrollState = enrollState;
                    fingerprintManager.semPauseEnroll();
                    FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = this.mSideSensorFrame;
                    if (fingerprintEnrollSideGuideFrame != null && fingerprintEnrollSideGuideFrame.isShown()) {
                        this.mSideSensorFrame.removeView();
                    }
                    if (this.mIsCaptureStarted) {
                        startViewAnimation(this.mRegisterScreen, 211);
                    }
                }
                int i3 = (this.mIsSupportFoldEnroll && this.mDisplayType == 5) ? R.string.sec_fingerprint_register_close_phone : R.string.sec_fingerprint_register_open_phone;
                TextView textView6 = (TextView) findViewById(R.id.folding_guide_text);
                if (textView6 != null) {
                    textView6.setText(i3);
                }
                startViewAnimation(this.mUnfoldGuideScreen, 207);
                this.mTextureView = (TextureView) findViewById(R.id.unfold_guide_clip);
                runTextToSpeech(0, getString(i3));
                break;
            case 304:
                this.mIsSwipeEnrollTouchGuideShow = true;
                if (this.mGuideStep == 0 && this.mEnrollState == enrollState2 && (fingerprintManager2 = this.mFingerprintManager) != null) {
                    this.mEnrollState = enrollState;
                    fingerprintManager2.semPauseEnroll();
                    FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame2 = this.mSideSensorFrame;
                    if (fingerprintEnrollSideGuideFrame2 != null && fingerprintEnrollSideGuideFrame2.isShown()) {
                        this.mSideSensorFrame.removeView();
                    }
                    if (this.mIsCaptureStarted) {
                        startViewAnimation(this.mRegisterScreen, 211);
                    }
                }
                TextView textView7 = (TextView) findViewById(R.id.swipe_touch_guide_description);
                TextView textView8 = (TextView) findViewById(R.id.swipe_touch_guide_title);
                if (textView8 != null) {
                    runTextToSpeech(1, String.valueOf(textView8.getText()));
                }
                if (textView7 != null) {
                    textView7.setText(getString(R.string.fingerprint_enroll_swipe_first_guide1) + " " + getString(R.string.fingerprint_enroll_swipe_first_guide2));
                    runTextToSpeech(1, String.valueOf(textView7.getText()));
                }
                this.mTextureView = (TextureView) findViewById(R.id.swipe_touch_guide_clip);
                runTextToSpeech(1, getString(R.string.common_select_ok_button));
                break;
        }
        TextureView textureView2 = this.mTextureView;
        if (textureView2 != null) {
            textureView2.setVisibility(0);
            this.mTextureView.setSurfaceTextureListener(this);
            this.mTextureView.setFocusable(false);
            if (this.mTextureView.isAvailable()) {
                onSurfaceTextureAvailable(this.mTextureView.getSurfaceTexture(), this.mTextureView.getWidth(), this.mTextureView.getHeight());
            }
        }
    }

    public final void showKnoxBi(boolean z) {
        ImageView imageView = this.mImageViewKnoxBi;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 4);
        }
    }

    public final void showSensorErrorDialog(final int i, CharSequence charSequence) {
        String string;
        if (this.mIsPauseRegistration) {
            return;
        }
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = this.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame != null) {
            fingerprintEnrollGuideFrame.hide();
        }
        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = this.mSideSensorFrame;
        if (fingerprintEnrollSideGuideFrame != null) {
            fingerprintEnrollSideGuideFrame.hide();
            this.mSideSensorFrame = null;
        }
        this.mIsShowSensorErrorDialog = true;
        final Intent intent = new Intent();
        intent.putExtra("biometrics_settings_destroy", false);
        if (i == 3) {
            r1 = getResources().getString(R.string.sec_fingerprint_error_dialog_could_not_register);
            string = getResources().getString(R.string.fingerprint_error_message_timed_out);
        } else if (i != 4) {
            if (i != 18) {
                if (i == 600) {
                    r1 = getResources().getString(R.string.fingerprint_error_title_trouble_with_sensor);
                    String string2 = getResources().getString(R.string.common_bullet);
                    string = getResources().getString(R.string.fingerprint_error_message_some_things_try) + "\n" + string2 + getResources().getString(R.string.fingerprint_error_message_protective_film) + "\n" + string2 + getResources().getString(R.string.fingerprint_error_message_dry);
                    this.mFingerprintManager.semPauseEnroll();
                } else if (i != 1001) {
                    if (i == 1003) {
                        r1 = getResources().getString(R.string.fingerprint_error_title_not_responding);
                        string = getResources().getString(R.string.fingerprint_error_message_not_responding);
                    } else if (i == 1007) {
                        r1 = getResources().getString(R.string.sec_fingerprint_error_dialog_could_not_register);
                        string = getResources().getString(R.string.sec_fingerprint_error_dialog_try_to_scan_central);
                    } else if (i == 5004) {
                        string = getResources().getString(R.string.sec_fingerprint_error_wireless_charger);
                    } else if (i == 100045) {
                        r1 = getResources().getString(R.string.sec_fingerprint_error_title_calibration);
                        string = getResources().getString(R.string.sec_fingerprint_error_contact_customer_service);
                        intent.putExtra("biometrics_settings_destroy", true);
                    } else if (i == 5 && Settings.Global.getInt(getContentResolver(), "always_finish_activities", 0) != 0) {
                        string = getResources().getString(R.string.fingerprint_error_message_always_finish_activities, getString(R.string.immediately_destroy_activities));
                    } else if (charSequence != null) {
                        string = charSequence.toString();
                    } else {
                        r1 = getResources().getString(R.string.fingerprint_error_title_not_responding);
                        string = getResources().getString(R.string.fingerprint_error_message_sensor_error);
                    }
                }
            }
            if (this.mIsTouchedSensorRectView) {
                string = getResources().getString(R.string.fingerprint_error_message_register_when_sensor_appears);
            } else {
                r1 = Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL ? getResources().getString(R.string.sec_fingerprint_error_title_calibration) : null;
                string = this.mIsFromSetupWizard ? getResources().getString(R.string.sec_fingerprint_sensor_error_calibration) : getResources().getString(R.string.fingerprint_error_message_something_on_sensor);
            }
            intent.putExtra("biometrics_settings_destroy", true);
        } else {
            r1 = getResources().getString(R.string.fingerprint_error_title_no_space);
            string = getResources().getString(R.string.fingerprint_error_message_no_space);
        }
        AlertDialog create = new AlertDialog.Builder(this, (!Utils.isColorThemeEnabled(this) || Utils.isNightThemeOn(this)) ? android.R.style.Theme.DeviceDefault.Dialog.Alert : 0).setTitle(r1).setMessage(string).setPositiveButton(android.R.string.ok, new FingerprintEnrollActivity$$ExternalSyntheticLambda1()).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                int i2 = i;
                Intent intent2 = intent;
                int i3 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                fingerprintEnrollActivity.getClass();
                Log.secD("BSS_FingerprintEnrollActivity", "showSensorErrorDialog dismiss!!");
                if (i2 != 600) {
                    if (i2 == 3) {
                        fingerprintEnrollActivity.setResult(3);
                        fingerprintEnrollActivity.finish();
                        return;
                    } else {
                        intent2.putExtra("enrollResult", 1);
                        intent2.putExtra("hw_auth_token", fingerprintEnrollActivity.mToken);
                        fingerprintEnrollActivity.setResult(0, intent2);
                        fingerprintEnrollActivity.finish();
                        return;
                    }
                }
                FingerprintManager fingerprintManager = fingerprintEnrollActivity.mFingerprintManager;
                if (fingerprintManager != null) {
                    fingerprintManager.semResumeEnroll();
                }
                FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame2 = fingerprintEnrollActivity.mEnrollGuideFrame;
                if (fingerprintEnrollGuideFrame2 != null) {
                    fingerprintEnrollGuideFrame2.show();
                }
                FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame2 = fingerprintEnrollActivity.mSideSensorFrame;
                if (fingerprintEnrollSideGuideFrame2 != null) {
                    fingerprintEnrollSideGuideFrame2.show();
                }
            }
        }).create();
        create.setCanceledOnTouchOutside(false);
        create.show();
        create.getButton(-1).setTextColor(getResources().getColor(R.color.fingerprint_verification_negative_text_color));
    }

    public final void startEnrollStep() {
        this.mGuideTitle.setVisibility(0);
        this.mTxtViewProgress.setVisibility(0);
        if (this.mIsTalkbackEnabled && this.mTts != null && this.mIsDisplaySensor) {
            View view = new View(this);
            this.mSensorPositionView = view;
            view.setClickable(false);
            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                this.mSensorPositionView.setContentDescription(getString(R.string.fingerprint_tts_indisplay_position_guide));
            }
            final int i = 1;
            this.mSensorPositionView.setOnHoverListener(new View.OnHoverListener(this) { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda9
                public final /* synthetic */ FingerprintEnrollActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view2, MotionEvent motionEvent) {
                    TextToSpeech textToSpeech;
                    int i2 = R.string.fingerprint_tts_indisplay_position_up;
                    int i3 = i;
                    FingerprintEnrollActivity fingerprintEnrollActivity = this.f$0;
                    switch (i3) {
                        case 0:
                            if (fingerprintEnrollActivity.mEnrollState == FingerprintEnrollActivity.EnrollState.ENROLL) {
                                if (motionEvent.getActionMasked() == 10 && fingerprintEnrollActivity.mIsShownLiftMsg && !fingerprintEnrollActivity.mIsCaptureStarted) {
                                    Handler handler = new Handler();
                                    fingerprintEnrollActivity.mMsgHandler = handler;
                                    FingerprintEnrollActivity$$ExternalSyntheticLambda5 fingerprintEnrollActivity$$ExternalSyntheticLambda5 = new FingerprintEnrollActivity$$ExternalSyntheticLambda5(1, fingerprintEnrollActivity);
                                    fingerprintEnrollActivity.mMsgRunnable = fingerprintEnrollActivity$$ExternalSyntheticLambda5;
                                    handler.postDelayed(fingerprintEnrollActivity$$ExternalSyntheticLambda5, 330L);
                                }
                                if (motionEvent.getActionMasked() == 9 || (fingerprintEnrollActivity.mIsFirstGuideShow && (textToSpeech = fingerprintEnrollActivity.mTts) != null && !textToSpeech.isSpeaking())) {
                                    int inDisplayFingerPositionStringId = Utils.getInDisplayFingerPositionStringId(fingerprintEnrollActivity.mFingerPosition, motionEvent.getX(), motionEvent.getY());
                                    Rect rect = fingerprintEnrollActivity.mFingerPosition;
                                    float x = motionEvent.getX();
                                    float y = motionEvent.getY();
                                    if (rect != null) {
                                        int[] iArr = {R.string.fingerprint_tts_indisplay_position_down_right, R.string.fingerprint_tts_indisplay_position_down, R.string.fingerprint_tts_indisplay_position_down_left, R.string.fingerprint_tts_indisplay_position_right, R.string.fingerprint_tts_indisplay_position_up, R.string.fingerprint_tts_indisplay_position_left, R.string.fingerprint_tts_indisplay_position_up_right, R.string.fingerprint_tts_indisplay_position_up, R.string.fingerprint_tts_indisplay_position_up_left};
                                        int i4 = rect.left + rect.right;
                                        int i5 = 2;
                                        int i6 = (rect.top + rect.bottom) / 2;
                                        float f = i4 / 2;
                                        int i7 = x < f ? 0 : x > f ? 2 : 1;
                                        float f2 = i6;
                                        if (y < f2) {
                                            i5 = 0;
                                        } else if (y <= f2) {
                                            i5 = 1;
                                        }
                                        i2 = iArr[(i5 * 3) + i7];
                                    }
                                    fingerprintEnrollActivity.mTouchGuideStrId = i2;
                                    if (inDisplayFingerPositionStringId <= 0) {
                                        fingerprintEnrollActivity.mIsTouchedOutside = false;
                                        break;
                                    } else {
                                        fingerprintEnrollActivity.runTextToSpeech(0, fingerprintEnrollActivity.getString(inDisplayFingerPositionStringId));
                                        fingerprintEnrollActivity.mIsTouchedOutside = true;
                                        break;
                                    }
                                }
                            }
                            break;
                        default:
                            int i8 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                            fingerprintEnrollActivity.getClass();
                            if (motionEvent.getAction() == 9 && fingerprintEnrollActivity.mIsFirstGuideShow) {
                                fingerprintEnrollActivity.runTextToSpeech(0, fingerprintEnrollActivity.getString(R.string.sec_fingerprint_sensor_activated));
                                Utils.playVibration(fingerprintEnrollActivity, 1);
                                fingerprintEnrollActivity.hideGuideScreen(300);
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            setSensorPosition(this.mSensorPositionView, true);
            final int i2 = 0;
            getWindow().getDecorView().setOnHoverListener(new View.OnHoverListener(this) { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda9
                public final /* synthetic */ FingerprintEnrollActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view2, MotionEvent motionEvent) {
                    TextToSpeech textToSpeech;
                    int i22 = R.string.fingerprint_tts_indisplay_position_up;
                    int i3 = i2;
                    FingerprintEnrollActivity fingerprintEnrollActivity = this.f$0;
                    switch (i3) {
                        case 0:
                            if (fingerprintEnrollActivity.mEnrollState == FingerprintEnrollActivity.EnrollState.ENROLL) {
                                if (motionEvent.getActionMasked() == 10 && fingerprintEnrollActivity.mIsShownLiftMsg && !fingerprintEnrollActivity.mIsCaptureStarted) {
                                    Handler handler = new Handler();
                                    fingerprintEnrollActivity.mMsgHandler = handler;
                                    FingerprintEnrollActivity$$ExternalSyntheticLambda5 fingerprintEnrollActivity$$ExternalSyntheticLambda5 = new FingerprintEnrollActivity$$ExternalSyntheticLambda5(1, fingerprintEnrollActivity);
                                    fingerprintEnrollActivity.mMsgRunnable = fingerprintEnrollActivity$$ExternalSyntheticLambda5;
                                    handler.postDelayed(fingerprintEnrollActivity$$ExternalSyntheticLambda5, 330L);
                                }
                                if (motionEvent.getActionMasked() == 9 || (fingerprintEnrollActivity.mIsFirstGuideShow && (textToSpeech = fingerprintEnrollActivity.mTts) != null && !textToSpeech.isSpeaking())) {
                                    int inDisplayFingerPositionStringId = Utils.getInDisplayFingerPositionStringId(fingerprintEnrollActivity.mFingerPosition, motionEvent.getX(), motionEvent.getY());
                                    Rect rect = fingerprintEnrollActivity.mFingerPosition;
                                    float x = motionEvent.getX();
                                    float y = motionEvent.getY();
                                    if (rect != null) {
                                        int[] iArr = {R.string.fingerprint_tts_indisplay_position_down_right, R.string.fingerprint_tts_indisplay_position_down, R.string.fingerprint_tts_indisplay_position_down_left, R.string.fingerprint_tts_indisplay_position_right, R.string.fingerprint_tts_indisplay_position_up, R.string.fingerprint_tts_indisplay_position_left, R.string.fingerprint_tts_indisplay_position_up_right, R.string.fingerprint_tts_indisplay_position_up, R.string.fingerprint_tts_indisplay_position_up_left};
                                        int i4 = rect.left + rect.right;
                                        int i5 = 2;
                                        int i6 = (rect.top + rect.bottom) / 2;
                                        float f = i4 / 2;
                                        int i7 = x < f ? 0 : x > f ? 2 : 1;
                                        float f2 = i6;
                                        if (y < f2) {
                                            i5 = 0;
                                        } else if (y <= f2) {
                                            i5 = 1;
                                        }
                                        i22 = iArr[(i5 * 3) + i7];
                                    }
                                    fingerprintEnrollActivity.mTouchGuideStrId = i22;
                                    if (inDisplayFingerPositionStringId <= 0) {
                                        fingerprintEnrollActivity.mIsTouchedOutside = false;
                                        break;
                                    } else {
                                        fingerprintEnrollActivity.runTextToSpeech(0, fingerprintEnrollActivity.getString(inDisplayFingerPositionStringId));
                                        fingerprintEnrollActivity.mIsTouchedOutside = true;
                                        break;
                                    }
                                }
                            }
                            break;
                        default:
                            int i8 = FingerprintEnrollActivity.TIME_ENROLL_DELAY;
                            fingerprintEnrollActivity.getClass();
                            if (motionEvent.getAction() == 9 && fingerprintEnrollActivity.mIsFirstGuideShow) {
                                fingerprintEnrollActivity.runTextToSpeech(0, fingerprintEnrollActivity.getString(R.string.sec_fingerprint_sensor_activated));
                                Utils.playVibration(fingerprintEnrollActivity, 1);
                                fingerprintEnrollActivity.hideGuideScreen(300);
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
        }
        setFingerGuideTitle(400);
        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
            this.mEnrollGuideFrame = new FingerprintEnrollGuideFrame(this);
            Handler handler = new Handler(getMainLooper());
            this.mEnrollHandler = handler;
            handler.postDelayed(new FingerprintEnrollActivity$$ExternalSyntheticLambda5(0, this), this.mEnrollStartRemainTime);
            return;
        }
        if (this.mIsSideSensor) {
            if (this.mIsSupportFoldEnroll) {
                this.mDisplayType = getResources().getConfiguration().semDisplayDeviceType;
            }
            FingerprintEnrollSensorHelper fingerprintEnrollSensorHelper = new FingerprintEnrollSensorHelper(this);
            this.mSensorHelper = fingerprintEnrollSensorHelper;
            if (fingerprintEnrollSensorHelper.height > 0) {
                if ((getResources().getConfiguration().semDisplayDeviceType == 5 ? this.mSensorHelper.foldTopMargin : this.mSensorHelper.topMargin) > 0) {
                    if (this.mSideSensorFrame == null) {
                        this.mSideSensorFrame = new FingerprintEnrollSideGuideFrame(this, this.mSensorHelper);
                    }
                    this.mSideSensorFrame.show();
                }
            }
        }
        startEnrollment();
        this.mGuideTitle.requestAccessibilityFocus();
    }

    public final void startEnrollment() {
        Log.d("BSS_FingerprintEnrollActivity", "startEnrollment");
        if (this.mIsPauseRegistration || this.mEnrollState != EnrollState.NONE) {
            Log.d("BSS_FingerprintEnrollActivity", "Skip startEnrollment!! mIsPauseRegistration = " + this.mIsPauseRegistration + " | mEnrollState = " + this.mEnrollState);
            return;
        }
        byte[] bArr = this.mToken;
        if (bArr == null) {
            if (this.mIsChallengeRequested) {
                this.mIsEnrollRequested = true;
                Log.d("BSS_FingerprintEnrollActivity", "Token is null. Enroll will be called when the challenge is received.");
                return;
            } else {
                Log.secD("BSS_FingerprintEnrollActivity", "startEnrollment : mToken or mFingerprintManager is null");
                showSensorErrorDialog(0, null);
                return;
            }
        }
        if (bArr.length == 1 && bArr[0] == -1) {
            Log.d("BSS_FingerprintEnrollActivity", "Challenge is incorrect");
            showSensorErrorDialog(1003, null);
            return;
        }
        this.mEnrollState = EnrollState.ENROLL;
        this.mEnrollmentCancel = new CancellationSignal();
        View view = this.mSensorRectView;
        if (view != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.mSensorRectView);
            }
            this.mSensorRectView = null;
        }
        if (!this.mIsPauseRegistration) {
            FingerprintEnrollOptions.Builder builder = new FingerprintEnrollOptions.Builder();
            builder.setEnrollReason(2);
            this.mFingerprintManager.enroll(this.mToken, this.mEnrollmentCancel, this.mUserId, this.mEnrollmentCallback, 2, builder.build());
        }
        SALoggingHelper.insertSALog(-9999L, String.valueOf(8255), null, null);
    }

    public final void startViewAnimation(View view, int i) {
        if (view == null) {
            if (i == 202) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.67f, 1.0f));
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.15f);
                alphaAnimation.setDuration(500L);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.setFillAfter(true);
                if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                    return;
                }
                this.mTxtViewProgress.startAnimation(animationSet);
            }
            if (i != 206) {
                return;
            }
            AnimationSet animationSet2 = new AnimationSet(true);
            animationSet2.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.67f, 1.0f));
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.15f, 1.0f);
            alphaAnimation2.setDuration(500L);
            alphaAnimation2.setFillAfter(true);
            animationSet2.addAnimation(alphaAnimation2);
            animationSet2.setFillAfter(true);
            if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                return;
            }
            this.mTxtViewProgress.startAnimation(animationSet2);
            return;
        }
        switch (i) {
            case 200:
                view.setVisibility(0);
                AlphaAnimation alphaAnimation3 = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
                alphaAnimation3.setDuration(500L);
                alphaAnimation3.setFillAfter(true);
                view.startAnimation(alphaAnimation3);
                break;
            case 201:
                AlphaAnimation alphaAnimation4 = new AlphaAnimation(1.0f, RecyclerView.DECELERATION_RATE);
                alphaAnimation4.setDuration(200L);
                alphaAnimation4.setFillAfter(true);
                view.startAnimation(alphaAnimation4);
                view.setVisibility(4);
                break;
            case 203:
                AnimationSet animationSet3 = new AnimationSet(true);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, 1, 0.5f, 1, 0.5f);
                scaleAnimation.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.2f, 1.0f));
                scaleAnimation.setDuration(150L);
                scaleAnimation.setFillAfter(true);
                AlphaAnimation alphaAnimation5 = new AlphaAnimation(1.0f, RecyclerView.DECELERATION_RATE);
                alphaAnimation5.setDuration(150L);
                alphaAnimation5.setFillAfter(true);
                animationSet3.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.67f, 1.0f));
                animationSet3.addAnimation(alphaAnimation5);
                animationSet3.addAnimation(scaleAnimation);
                animationSet3.setFillAfter(true);
                view.startAnimation(animationSet3);
                view.setVisibility(4);
                break;
            case 204:
                view.setVisibility(0);
                AnimationSet animationSet4 = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(1, RecyclerView.DECELERATION_RATE, 1, RecyclerView.DECELERATION_RATE, 1, 0.03f, 1, RecyclerView.DECELERATION_RATE);
                translateAnimation.setDuration(400L);
                AlphaAnimation alphaAnimation6 = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
                alphaAnimation6.setDuration(400L);
                alphaAnimation6.setFillAfter(true);
                animationSet4.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.67f, 1.0f));
                animationSet4.addAnimation(alphaAnimation6);
                animationSet4.addAnimation(translateAnimation);
                animationSet4.setFillAfter(true);
                view.startAnimation(animationSet4);
                break;
            case 205:
                view.setVisibility(0);
                AnimationSet animationSet5 = new AnimationSet(true);
                AlphaAnimation alphaAnimation7 = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
                alphaAnimation7.setDuration(500L);
                alphaAnimation7.setFillAfter(true);
                animationSet5.addAnimation(alphaAnimation7);
                animationSet5.setFillAfter(true);
                view.startAnimation(animationSet5);
                break;
            case 207:
                view.setVisibility(0);
                AnimationSet animationSet6 = new AnimationSet(true);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(1, RecyclerView.DECELERATION_RATE, 1, RecyclerView.DECELERATION_RATE, 1, 0.1f, 1, RecyclerView.DECELERATION_RATE);
                translateAnimation2.setDuration(500L);
                AlphaAnimation alphaAnimation8 = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
                alphaAnimation8.setDuration(330L);
                alphaAnimation8.setFillAfter(true);
                animationSet6.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.67f, 1.0f));
                animationSet6.addAnimation(translateAnimation2);
                animationSet6.addAnimation(alphaAnimation8);
                animationSet6.setFillAfter(true);
                view.startAnimation(animationSet6);
                break;
            case 208:
                AnimationSet animationSet7 = new AnimationSet(true);
                TranslateAnimation translateAnimation3 = new TranslateAnimation(1, RecyclerView.DECELERATION_RATE, 1, RecyclerView.DECELERATION_RATE, 1, RecyclerView.DECELERATION_RATE, 1, 0.1f);
                translateAnimation3.setDuration(500L);
                AlphaAnimation alphaAnimation9 = new AlphaAnimation(1.0f, RecyclerView.DECELERATION_RATE);
                alphaAnimation9.setDuration(330L);
                alphaAnimation9.setFillAfter(true);
                animationSet7.setInterpolator(new PathInterpolator(0.33f, RecyclerView.DECELERATION_RATE, 0.2f, 1.0f));
                animationSet7.addAnimation(alphaAnimation9);
                animationSet7.addAnimation(translateAnimation3);
                animationSet7.setFillAfter(true);
                view.startAnimation(animationSet7);
                view.setVisibility(4);
                break;
            case 209:
                view.setVisibility(0);
                AnimationSet animationSet8 = new AnimationSet(true);
                AlphaAnimation alphaAnimation10 = new AlphaAnimation(RecyclerView.DECELERATION_RATE, 1.0f);
                alphaAnimation10.setDuration(500L);
                alphaAnimation10.setFillAfter(true);
                animationSet8.addAnimation(alphaAnimation10);
                animationSet8.setFillAfter(true);
                if (this.mIsDisplaySensor) {
                    animationSet8.setStartOffset(750L);
                }
                view.startAnimation(animationSet8);
                break;
            case 210:
                view.setVisibility(0);
                view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.sec_fingerprint_enroll_error));
                break;
            case 211:
                AlphaAnimation alphaAnimation11 = new AlphaAnimation(1.0f, RecyclerView.DECELERATION_RATE);
                alphaAnimation11.setDuration(0L);
                alphaAnimation11.setFillAfter(true);
                view.startAnimation(alphaAnimation11);
                view.setVisibility(4);
                break;
            case 212:
                AlphaAnimation alphaAnimation12 = new AlphaAnimation(1.0f, RecyclerView.DECELERATION_RATE);
                alphaAnimation12.setDuration(0L);
                alphaAnimation12.setFillAfter(true);
                view.startAnimation(alphaAnimation12);
                view.setVisibility(8);
                break;
        }
    }

    public final void tuneTouchGuideFrame() {
        int i;
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = this.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame == null || (i = this.mLogging_Partial) <= 0 || i % 2 != 0) {
            return;
        }
        fingerprintEnrollGuideFrame.tuneMovingArea(false);
        this.mLogging_Partial = 0;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
