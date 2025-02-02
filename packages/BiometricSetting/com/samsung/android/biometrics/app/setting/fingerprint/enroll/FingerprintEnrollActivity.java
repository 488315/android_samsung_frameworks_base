package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
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
import android.view.DisplayCutout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.android.bio.fingerprint.SemFingerprintManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SALoggingHelper;
import com.samsung.android.biometrics.app.setting.SettingHelper;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class FingerprintEnrollActivity extends Activity implements View.OnClickListener, View.OnTouchListener, TextureView.SurfaceTextureListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final int TIME_ENROLL_DELAY;
    private Handler mAuthErrorHandler;
    private ImageView mAuthErrorImage;
    private Runnable mAuthErrorRunnable;
    private TextView mAuthErrorText;
    private CancellationSignal mAuthenticateCancel;
    private int mDisplayType;
    private boolean mDoNotMove;
    private FingerprintEnrollGuideFrame mEnrollGuideFrame;
    private Handler mEnrollHandler;
    private CountDownTimer mEnrollStartTimer;
    private int mEnrolledCount;
    private CancellationSignal mEnrollmentCancel;
    private Toast mExitToast;
    private Rect mFingerPosition;
    private FingerprintManager mFingerprintManager;
    private RelativeLayout mFirstGuideScreen;
    private int mGuideStep;
    private TextView mGuideTitle;
    private Handler mHideErrorHandler;
    private Runnable mHideErrorRunnable;
    private ImageView mImageViewKnoxBi;
    private boolean mIsCalledFingerLeave;
    private boolean mIsCaptureStarted;
    private boolean mIsSupportDualDisplay;
    private boolean mIsSupportSecondGuide;
    private boolean mIsTalkbackEnabled;
    private boolean mIsTouchedFingerFrame;
    private boolean mIsTouchedOutside;
    private boolean mIsTouchedSensorRectView;
    private boolean mIsTspBlock;
    private boolean mIsUSA;
    private LiftFingerMessage mLiftFingerMessage;
    private int mLogging_Dirty;
    private int mLogging_DoNotMove;
    private int mLogging_LiftOff;
    private int mLogging_Ok;
    private int mLogging_Partial;
    private int mLogging_PressHarder;
    private int mLogging_TooShort;
    private int mLogging_UpAndDown;
    private IBinder mMaskViewToken;
    private MediaPlayer mMediaPlayer;
    private Button mNextButton;
    private RelativeLayout mNextButtonArea;
    private PowerManager mPowerManager;
    private RelativeLayout mRegisterScreen;
    private RelativeLayout mSecondGuideScreen;
    private SemFingerprintManager mSemFingerprintManager;
    private View mSensorPositionView;
    private View mSensorRectView;
    private Handler mShowErrorHandler;
    private Runnable mShowErrorRunnable;
    private FingerprintEnrollSideGuideFrame mSideSensorFrame;
    private Surface mSurface;
    private RelativeLayout mSwipeEnrollGuideScreen;
    private TextureView mTextureView;
    private Handler mTouchGuideHandler;
    private FingerprintEnrollActivity$$ExternalSyntheticLambda1 mTouchGuideRunnable;
    private int mTouchGuideStrId;
    private TextView mTxtViewProgress;
    private LinearLayout mUnfoldGuideScreen;
    private int mCurrentPercent = 0;
    private TextToSpeech mTts = null;
    private boolean mBackEnabled = true;
    private int mFilmErrorCount = 0;
    private FingerprintProgressEffectView mFinger_ProgressView = null;
    private boolean mIsShowErrorMsg = false;
    private boolean mIsFirstGuideShow = false;
    private boolean mIsFirstGuideShowClose = false;
    private boolean mIsShownLiftMsg = false;
    private boolean mIsCalledLiftMsg = false;
    private Handler mMsgHandler = null;
    private Runnable mMsgRunnable = null;
    private EnrollState mEnrollState = EnrollState.NONE;
    private byte[] mToken = null;
    private boolean mIsPauseRegistration = false;
    private boolean mIsFromSetupWizard = false;
    private boolean mIsRegisterCompleted = false;
    private boolean mIsFinishRegistration = false;
    private boolean mIsSkipGuideScreen = false;
    private int mUserId = 0;
    private boolean mIsRotateGuideShow = false;
    private boolean mIsSwipeEnrollGuideShow = false;
    private boolean mIsFoldingGuideShow = false;
    private boolean mIsSupportFoldEnroll = false;
    private int mAuthGuidePlayCount = 0;
    private int mSensorStatus = 0;
    private int mCompleteCheckDuration = 1000;
    private boolean mIsRearSensor = false;
    private boolean mIsSideSensor = false;
    private boolean mIsDisplaySensor = false;
    private boolean mIsSupportSwipeEnroll = false;
    private boolean mIsFingerGestureSet = false;
    private boolean mIsReCreated = false;
    private boolean mIsButtonClicked = false;
    private boolean mIsAdditionalRegistration = false;
    private boolean mIsBackSecond = false;
    private boolean mIsVibrationSupport = false;
    private boolean mIsShowSensorErrorDialog = false;
    private boolean mIsBlockedPowerKey = false;
    private boolean mIsForcedPortrait = false;
    private boolean mIsFromKnoxFingerprintPlus = false;
    private long mEnrollStartRemainTime = 200;
    private Handler mBackHandler = new Handler() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            FingerprintEnrollActivity.this.mIsBackSecond = false;
        }
    };
    private FingerprintManager.EnrollmentCallback mEnrollmentCallback = new FingerprintManager.EnrollmentCallback() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.3
        public final void onEnrollmentError(int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", "onEnrollmentError : errMsgId = " + i + " , errString = " + ((Object) charSequence));
            FingerprintEnrollActivity.this.showSensorErrorDialog(i, charSequence);
        }

        public final void onEnrollmentHelp(final int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", "onEnrollmentHelp : helpMsgId = " + i + " , helpString = " + ((Object) charSequence));
            if (FingerprintEnrollActivity.this.mIsPauseRegistration || FingerprintEnrollActivity.this.mIsRegisterCompleted) {
                return;
            }
            final FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            fingerprintEnrollActivity.getClass();
            final String charSequence2 = charSequence != null ? charSequence.toString() : "";
            fingerprintEnrollActivity.runOnUiThread(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.4
                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = i;
                    if (i2 == 0) {
                        if (fingerprintEnrollActivity.mIsTspBlock) {
                            fingerprintEnrollActivity.mIsTspBlock = false;
                            fingerprintEnrollActivity.removeErrorMessageHandler();
                            if (fingerprintEnrollActivity.mHideErrorHandler != null) {
                                fingerprintEnrollActivity.mHideErrorHandler.postDelayed(fingerprintEnrollActivity.mHideErrorRunnable, 100L);
                            }
                            return;
                        }
                        return;
                    }
                    if (i2 == 1 || i2 == 2) {
                        fingerprintEnrollActivity.mLogging_Partial++;
                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity, charSequence2);
                            return;
                        }
                        FingerprintEnrollActivity.m202$$Nest$mtuneTouchGuideFrame(fingerprintEnrollActivity);
                        if (!fingerprintEnrollActivity.mIsTalkbackEnabled || fingerprintEnrollActivity.mTouchGuideStrId <= 0) {
                            FingerprintEnrollActivity fingerprintEnrollActivity2 = fingerprintEnrollActivity;
                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity2, fingerprintEnrollActivity2.getString(fingerprintEnrollActivity2.mIsTalkbackEnabled ? R.string.fingerprint_enroll_partial_tts : R.string.fingerprint_enroll_partial));
                            return;
                        } else {
                            FingerprintEnrollActivity fingerprintEnrollActivity3 = fingerprintEnrollActivity;
                            fingerprintEnrollActivity3.runTextToSpeech(0, fingerprintEnrollActivity3.getString(fingerprintEnrollActivity3.mTouchGuideStrId));
                            return;
                        }
                    }
                    if (i2 == 3) {
                        fingerprintEnrollActivity.mLogging_Dirty++;
                        FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity, charSequence2);
                        return;
                    }
                    if (i2 == 4) {
                        FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i2, fingerprintEnrollActivity, charSequence2);
                        return;
                    }
                    if (i2 == 5) {
                        fingerprintEnrollActivity.mLogging_TooShort++;
                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity, charSequence2);
                            return;
                        } else {
                            FingerprintEnrollActivity fingerprintEnrollActivity4 = fingerprintEnrollActivity;
                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity4, fingerprintEnrollActivity4.getString(R.string.fingerprint_enroll_longer));
                            return;
                        }
                    }
                    switch (i2) {
                        case 1001:
                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i2, fingerprintEnrollActivity, charSequence2);
                            break;
                        case 1002:
                            fingerprintEnrollActivity.mLogging_UpAndDown++;
                            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                FingerprintEnrollActivity fingerprintEnrollActivity5 = fingerprintEnrollActivity;
                                FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity5, fingerprintEnrollActivity5.getString(R.string.fingerprint_duplicate_guide_msg));
                                break;
                            } else {
                                fingerprintEnrollActivity.mEnrollGuideFrame.tuneMovingArea(true);
                                fingerprintEnrollActivity.mEnrollGuideFrame.moveNext();
                                FingerprintEnrollActivity fingerprintEnrollActivity6 = fingerprintEnrollActivity;
                                FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity6, fingerprintEnrollActivity6.getString(R.string.fingerprint_enroll_move_finger));
                                break;
                            }
                        case 1003:
                            fingerprintEnrollActivity.mLogging_PressHarder++;
                            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity, charSequence2);
                                break;
                            } else {
                                FingerprintEnrollActivity fingerprintEnrollActivity7 = fingerprintEnrollActivity;
                                FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(i, fingerprintEnrollActivity7, fingerprintEnrollActivity7.getString(R.string.fingerprint_enroll_longer));
                                break;
                            }
                        case 1004:
                            if (fingerprintEnrollActivity.mIsFirstGuideShow) {
                                fingerprintEnrollActivity.hideGuideScreen(300);
                            }
                            fingerprintEnrollActivity.mIsTspBlock = true;
                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(0, fingerprintEnrollActivity, charSequence2);
                            break;
                        default:
                            switch (i2) {
                                case 10001:
                                    if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX && fingerprintEnrollActivity.mIsCalledFingerLeave) {
                                        fingerprintEnrollActivity.mIsCalledLiftMsg = false;
                                        fingerprintEnrollActivity.mEnrollGuideFrame.setProgressResult(3);
                                        fingerprintEnrollActivity.mEnrollGuideFrame.finishScan();
                                    }
                                    if (fingerprintEnrollActivity.mFinger_ProgressView != null) {
                                        fingerprintEnrollActivity.mFinger_ProgressView.setFingerStatus(0);
                                    }
                                    if (fingerprintEnrollActivity.mMsgHandler != null) {
                                        fingerprintEnrollActivity.mMsgHandler.removeCallbacks(fingerprintEnrollActivity.mMsgRunnable);
                                        fingerprintEnrollActivity.mMsgHandler = null;
                                    }
                                    if (fingerprintEnrollActivity.mLiftFingerMessage != null) {
                                        fingerprintEnrollActivity.mLiftFingerMessage.interrupt();
                                        fingerprintEnrollActivity.mLiftFingerMessage = null;
                                    }
                                    if (fingerprintEnrollActivity.mIsShownLiftMsg) {
                                        fingerprintEnrollActivity.mMsgHandler = new Handler();
                                        fingerprintEnrollActivity.mMsgRunnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.4.1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                if (fingerprintEnrollActivity.mIsShownLiftMsg) {
                                                    fingerprintEnrollActivity.mIsShownLiftMsg = false;
                                                    fingerprintEnrollActivity.setFingerGuideTitle(400);
                                                    if (!fingerprintEnrollActivity.mIsShowErrorMsg) {
                                                        FingerprintEnrollActivity fingerprintEnrollActivity8 = fingerprintEnrollActivity;
                                                        fingerprintEnrollActivity8.startViewAnimation(fingerprintEnrollActivity8.mGuideTitle, 210);
                                                        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                                            FingerprintEnrollActivity fingerprintEnrollActivity9 = fingerprintEnrollActivity;
                                                            fingerprintEnrollActivity9.startViewAnimation(fingerprintEnrollActivity9.mTxtViewProgress, 204);
                                                        }
                                                    }
                                                    if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                                        return;
                                                    }
                                                    FingerprintEnrollActivity fingerprintEnrollActivity10 = fingerprintEnrollActivity;
                                                    fingerprintEnrollActivity10.runTextToSpeech(1, String.valueOf(fingerprintEnrollActivity10.mGuideTitle.getText()));
                                                }
                                            }
                                        };
                                        fingerprintEnrollActivity.mMsgHandler.postDelayed(fingerprintEnrollActivity.mMsgRunnable, 330L);
                                        break;
                                    }
                                    break;
                                case 10002:
                                    if (fingerprintEnrollActivity.mSideSensorFrame != null) {
                                        fingerprintEnrollActivity.mSideSensorFrame.startScan();
                                    }
                                    if (fingerprintEnrollActivity.mTouchGuideHandler != null) {
                                        fingerprintEnrollActivity.mIsTouchedFingerFrame = false;
                                        fingerprintEnrollActivity.mTouchGuideHandler.removeCallbacks(fingerprintEnrollActivity.mTouchGuideRunnable);
                                        fingerprintEnrollActivity.mEnrollGuideFrame.startScan();
                                    }
                                    boolean z = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
                                    if (z && !fingerprintEnrollActivity.mIsCaptureStarted) {
                                        FingerprintEnrollActivity fingerprintEnrollActivity8 = fingerprintEnrollActivity;
                                        fingerprintEnrollActivity8.runTextToSpeech(0, fingerprintEnrollActivity8.getString(R.string.fingerprint_tts_guide_scan_activated));
                                    }
                                    fingerprintEnrollActivity.mIsCaptureStarted = true;
                                    fingerprintEnrollActivity.mIsCalledFingerLeave = false;
                                    if (fingerprintEnrollActivity.mPowerManager != null) {
                                        fingerprintEnrollActivity.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                                    }
                                    if (fingerprintEnrollActivity.mIsFirstGuideShow) {
                                        fingerprintEnrollActivity.hideGuideScreen(300);
                                        if (fingerprintEnrollActivity.mIsDisplaySensor) {
                                            FingerprintEnrollActivity fingerprintEnrollActivity9 = fingerprintEnrollActivity;
                                            fingerprintEnrollActivity9.runTextToSpeech(0, fingerprintEnrollActivity9.getString(R.string.sec_fingerprint_sensor_activated));
                                        }
                                    }
                                    if (fingerprintEnrollActivity.mLiftFingerMessage != null) {
                                        fingerprintEnrollActivity.mLiftFingerMessage.interrupt();
                                        fingerprintEnrollActivity.mLiftFingerMessage = null;
                                    }
                                    fingerprintEnrollActivity.mIsCalledLiftMsg = false;
                                    if (z) {
                                        fingerprintEnrollActivity.setFingerGuideTitle(400);
                                        fingerprintEnrollActivity.mIsShownLiftMsg = false;
                                        if (fingerprintEnrollActivity.mIsShowErrorMsg) {
                                            FingerprintEnrollActivity fingerprintEnrollActivity10 = fingerprintEnrollActivity;
                                            fingerprintEnrollActivity10.startViewAnimation(fingerprintEnrollActivity10.mGuideTitle, 204);
                                        }
                                    }
                                    if (!fingerprintEnrollActivity.mIsSupportSwipeEnroll) {
                                        fingerprintEnrollActivity.mFinger_ProgressView.setFingerStatus(1);
                                        break;
                                    } else {
                                        if (fingerprintEnrollActivity.mIsShowErrorMsg) {
                                            fingerprintEnrollActivity.startViewAnimation(null, 206);
                                            fingerprintEnrollActivity.mIsShowErrorMsg = false;
                                            fingerprintEnrollActivity.setFingerGuideTitle(400);
                                            FingerprintEnrollActivity fingerprintEnrollActivity11 = fingerprintEnrollActivity;
                                            fingerprintEnrollActivity11.startViewAnimation(fingerprintEnrollActivity11.mGuideTitle, 204);
                                        }
                                        fingerprintEnrollActivity.removeErrorMessageHandler();
                                        break;
                                    }
                                    break;
                                case 10003:
                                    if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX && fingerprintEnrollActivity.mEnrollGuideFrame != null) {
                                        fingerprintEnrollActivity.mEnrollGuideFrame.completeCapture();
                                        break;
                                    }
                                    break;
                                case 10004:
                                    if (fingerprintEnrollActivity.mSideSensorFrame != null) {
                                        fingerprintEnrollActivity.mSideSensorFrame.finishScan();
                                    }
                                    fingerprintEnrollActivity.mIsCalledFingerLeave = true;
                                    if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                        fingerprintEnrollActivity.mEnrollGuideFrame.setProgressResult(3);
                                        fingerprintEnrollActivity.mEnrollGuideFrame.finishScan();
                                        if (fingerprintEnrollActivity.mDoNotMove) {
                                            fingerprintEnrollActivity.mDoNotMove = false;
                                            fingerprintEnrollActivity.mIsShownLiftMsg = false;
                                            FingerprintEnrollActivity fingerprintEnrollActivity12 = fingerprintEnrollActivity;
                                            FingerprintEnrollActivity.m195$$Nest$mshowErrorMessage(-1, fingerprintEnrollActivity12, fingerprintEnrollActivity12.getString(R.string.fingerprint_enroll_dont_move));
                                        } else if (fingerprintEnrollActivity.mIsCalledLiftMsg) {
                                            if (fingerprintEnrollActivity.mIsShownLiftMsg) {
                                                fingerprintEnrollActivity.mIsShownLiftMsg = false;
                                                fingerprintEnrollActivity.setFingerGuideTitle(400);
                                                FingerprintEnrollActivity fingerprintEnrollActivity13 = fingerprintEnrollActivity;
                                                fingerprintEnrollActivity13.startViewAnimation(fingerprintEnrollActivity13.mGuideTitle, 204);
                                            }
                                            if (fingerprintEnrollActivity.mIsTalkbackEnabled) {
                                                int talkBackPositionIndex = fingerprintEnrollActivity.mEnrollGuideFrame.getTalkBackPositionIndex();
                                                fingerprintEnrollActivity.mEnrollGuideFrame.moveNext();
                                                FingerprintEnrollActivity.m187$$Nest$mlaunchMovingGuideTalkBack(fingerprintEnrollActivity, talkBackPositionIndex, fingerprintEnrollActivity.mEnrollGuideFrame.getTalkBackPositionIndex());
                                            } else {
                                                fingerprintEnrollActivity.mEnrollGuideFrame.moveNext();
                                            }
                                        }
                                    }
                                    fingerprintEnrollActivity.mIsCalledLiftMsg = false;
                                    break;
                                case 10005:
                                    fingerprintEnrollActivity.mLogging_Ok++;
                                    if (fingerprintEnrollActivity.mIsShowErrorMsg) {
                                        fingerprintEnrollActivity.startViewAnimation(null, 206);
                                        fingerprintEnrollActivity.mIsShowErrorMsg = false;
                                        fingerprintEnrollActivity.removeErrorMessageHandler();
                                        FingerprintEnrollActivity fingerprintEnrollActivity14 = fingerprintEnrollActivity;
                                        fingerprintEnrollActivity14.setFingerGuideTitle(FingerprintEnrollActivity.m185$$Nest$mgetFingerRegisterText(fingerprintEnrollActivity14));
                                    }
                                    if (fingerprintEnrollActivity.mLiftFingerMessage != null) {
                                        fingerprintEnrollActivity.mLiftFingerMessage.interrupt();
                                        fingerprintEnrollActivity.mLiftFingerMessage = null;
                                    }
                                    fingerprintEnrollActivity.mIsCalledLiftMsg = true;
                                    fingerprintEnrollActivity.mLiftFingerMessage = fingerprintEnrollActivity.new LiftFingerMessage();
                                    fingerprintEnrollActivity.mLiftFingerMessage.start();
                                    if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                        if (fingerprintEnrollActivity.mGuideTitle.getVisibility() != 0) {
                                            FingerprintEnrollActivity fingerprintEnrollActivity15 = fingerprintEnrollActivity;
                                            fingerprintEnrollActivity15.startViewAnimation(fingerprintEnrollActivity15.mGuideTitle, 204);
                                            break;
                                        }
                                    } else {
                                        fingerprintEnrollActivity.mEnrollGuideFrame.setProgressResult(1);
                                        break;
                                    }
                                    break;
                                case 10006:
                                    if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                        fingerprintEnrollActivity.mEnrollGuideFrame.setProgressResult(3);
                                        fingerprintEnrollActivity.mEnrollGuideFrame.finishScan();
                                        break;
                                    }
                                    break;
                            }
                    }
                }
            });
        }

        public final void onEnrollmentProgress(int i) {
            if (FingerprintEnrollActivity.this.mIsPauseRegistration || FingerprintEnrollActivity.this.mIsRegisterCompleted) {
                return;
            }
            int i2 = 100 - i;
            int i3 = FingerprintProgressEffectView.$r8$clinit;
            int i4 = 0;
            if (i2 < 80) {
                double log = Math.log(50);
                i2 = (i2 > 0 ? 9 - (i2 / 8) : 0) + ((int) (((Math.log(i2 + 50) - log) / (Math.log(130) - log)) * 80.0d));
            }
            if (FingerprintEnrollActivity.this.mCurrentPercent == i2) {
                return;
            }
            if (FingerprintEnrollActivity.this.mCurrentPercent == 0) {
                FingerprintEnrollActivity.this.mGuideTitle.performAccessibilityAction(128, null);
                i4 = 1;
            }
            FingerprintEnrollActivity.this.mCurrentPercent = i2;
            Log.i("BSS_FingerprintEnrollActivity", "onEnrollmentProgress : " + FingerprintEnrollActivity.this.mCurrentPercent);
            FingerprintEnrollActivity.this.mTxtViewProgress.setText(TextUtils.expandTemplate(FingerprintEnrollActivity.this.getText(R.string.sec_fingerprint_percent_format), String.format("%d", Integer.valueOf(FingerprintEnrollActivity.this.mCurrentPercent))));
            FingerprintEnrollActivity.this.mTxtViewProgress.performAccessibilityAction(128, null);
            FingerprintEnrollActivity.this.mFinger_ProgressView.setPercent(FingerprintEnrollActivity.this.mCurrentPercent);
            if (!FingerprintEnrollActivity.this.mIsShowErrorMsg && (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX || FingerprintEnrollActivity.this.mCurrentPercent < 100)) {
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity.runTextToSpeech(i4, fingerprintEnrollActivity.getString(R.string.fingerprint_confirm_percent_for_talkback, new Object[]{Integer.valueOf(fingerprintEnrollActivity.mCurrentPercent)}));
            }
            if (FingerprintEnrollActivity.this.mIsSupportSecondGuide && (FingerprintEnrollActivity.this.mCurrentPercent == 80 || (FingerprintEnrollActivity.this.mCurrentPercent == 68 && FingerprintEnrollActivity.this.mIsSupportDualDisplay))) {
                if (FingerprintEnrollActivity.this.mIsRotateGuideShow || Utils.Config.FEATURE_SUPPORT_DISABLED_MENU_K05) {
                    return;
                }
                if (FingerprintEnrollActivity.this.mEnrollState == EnrollState.ENROLL && FingerprintEnrollActivity.this.mFingerprintManager != null) {
                    FingerprintEnrollActivity.this.mEnrollState = EnrollState.PAUSE;
                    FingerprintEnrollActivity.this.mFingerprintManager.semPauseEnroll();
                }
                new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.3.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (FingerprintEnrollActivity.this.mIsPauseRegistration) {
                            return;
                        }
                        FingerprintEnrollActivity.this.showGuideScreen(301);
                    }
                }, 300L);
                return;
            }
            if (FingerprintEnrollActivity.this.mCurrentPercent == 100) {
                FingerprintEnrollActivity.this.mEnrollState = EnrollState.NONE;
                if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                    FingerprintEnrollActivity.this.mEnrollGuideFrame.completeScan();
                    FingerprintEnrollActivity.m189$$Nest$mremoveTouchGuideFrame(FingerprintEnrollActivity.this);
                }
                if (FingerprintEnrollActivity.this.mSideSensorFrame != null) {
                    FingerprintEnrollActivity.this.mSideSensorFrame.hide();
                    FingerprintEnrollActivity.this.mSideSensorFrame = null;
                }
                FingerprintEnrollActivity.m190$$Nest$mrunCompleteRegistration(FingerprintEnrollActivity.this);
            }
        }
    };
    private SemFingerprintManager.AuthenticationCallback mSemAuthCallback = new SemFingerprintManager.AuthenticationCallback() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.17
        public final void onAuthenticationError(int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", ((Object) charSequence) + "(errMsgId : " + i + ")");
            if (i != 5) {
                FingerprintEnrollActivity.m194$$Nest$mshowAuthenticateResult(101, FingerprintEnrollActivity.this, charSequence.toString());
                new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.17.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintEnrollActivity.this.finishRegistration();
                    }
                }, 1500L);
            }
        }

        public final void onAuthenticationFailed() {
            Log.d("BSS_FingerprintEnrollActivity", "onAuthenticationFailed");
            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            FingerprintEnrollActivity.m194$$Nest$mshowAuthenticateResult(101, fingerprintEnrollActivity, fingerprintEnrollActivity.getString(R.string.face_auth_fail));
        }

        public final void onAuthenticationHelp(int i, CharSequence charSequence) {
            Log.d("BSS_FingerprintEnrollActivity", ((Object) charSequence) + "(helpMsgId : " + i + ")");
            FingerprintEnrollActivity.m194$$Nest$mshowAuthenticateResult(i, FingerprintEnrollActivity.this, charSequence.toString());
        }

        public final void onAuthenticationSucceeded(SemFingerprintManager.AuthenticationResult authenticationResult) {
            Log.d("BSS_FingerprintEnrollActivity", "onAuthenticationSucceeded");
            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
            FingerprintEnrollActivity.m194$$Nest$mshowAuthenticateResult(0, fingerprintEnrollActivity, fingerprintEnrollActivity.getString(R.string.fingerprint_register_swipe_enroll_guide_success));
            new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.17.1
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintEnrollActivity.this.finishRegistration();
                }
            }, 1500L);
        }
    };
    private final View.OnTouchListener mTouchGuideViewListener = new View.OnTouchListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.21
        @Override // android.view.View.OnTouchListener
        @SuppressLint({"ClickableViewAccessibility"})
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                if (FingerprintEnrollActivity.this.mTouchGuideHandler != null) {
                    FingerprintEnrollActivity.this.mTouchGuideHandler.postDelayed(FingerprintEnrollActivity.this.mTouchGuideRunnable, 300L);
                }
                if (FingerprintEnrollActivity.this.mEnrollGuideFrame != null) {
                    FingerprintEnrollActivity.this.mEnrollGuideFrame.startScan();
                }
                FingerprintEnrollActivity.this.mIsTouchedFingerFrame = true;
            } else if (action == 1) {
                if (FingerprintEnrollActivity.this.mIsTouchedFingerFrame) {
                    if (FingerprintEnrollActivity.this.mTouchGuideHandler != null) {
                        FingerprintEnrollActivity.this.mTouchGuideHandler.removeCallbacks(FingerprintEnrollActivity.this.mTouchGuideRunnable);
                    }
                    if (FingerprintEnrollActivity.this.mGuideTitle != null) {
                        FingerprintEnrollActivity.this.mGuideTitle.setText(R.string.fingerprint_enroll_guide);
                    }
                    FingerprintEnrollActivity.this.mIsTouchedFingerFrame = false;
                    FingerprintEnrollActivity.m202$$Nest$mtuneTouchGuideFrame(FingerprintEnrollActivity.this);
                    if (FingerprintEnrollActivity.this.mEnrollGuideFrame != null) {
                        FingerprintEnrollActivity.this.mEnrollGuideFrame.finishScan();
                    }
                }
                if (FingerprintEnrollActivity.this.mEnrollGuideFrame != null && FingerprintEnrollActivity.this.mEnrollGuideFrame.isFingerMoved()) {
                    FingerprintEnrollActivity.this.mDoNotMove = true;
                    FingerprintEnrollActivity.this.mLogging_DoNotMove++;
                }
            }
            return false;
        }
    };

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$18 */
    final class RunnableC025418 implements Runnable {
        RunnableC025418() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            final LinearLayout linearLayout = null;
            if (FingerprintEnrollActivity.this.mLiftFingerMessage != null) {
                FingerprintEnrollActivity.this.mLiftFingerMessage.interrupt();
                FingerprintEnrollActivity.this.mLiftFingerMessage = null;
            }
            FingerprintEnrollActivity.this.showKnoxBi(false);
            FingerprintEnrollActivity.this.mFinger_ProgressView.setCompleteImage();
            boolean z = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
            if (!z) {
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mTxtViewProgress, 203);
            }
            int semGetMaxEnrollmentNumber = FingerprintEnrollActivity.this.mFingerprintManager.semGetMaxEnrollmentNumber();
            List enrolledFingerprints = FingerprintEnrollActivity.this.mFingerprintManager.getEnrolledFingerprints(FingerprintEnrollActivity.this.mUserId);
            if (enrolledFingerprints != null) {
                FingerprintEnrollActivity.this.mEnrolledCount = enrolledFingerprints.size();
            } else {
                FingerprintEnrollActivity.this.mEnrolledCount = semGetMaxEnrollmentNumber;
            }
            Log.d("BSS_FingerprintEnrollActivity", "enrolledCount = " + FingerprintEnrollActivity.this.mEnrolledCount + " | maxCount = " + semGetMaxEnrollmentNumber);
            FingerprintEnrollActivity.this.setFingerGuideTitle(403);
            FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
            fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mGuideTitle, 205);
            if (FingerprintEnrollActivity.this.mEnrolledCount >= semGetMaxEnrollmentNumber || FingerprintEnrollActivity.this.mIsFromKnoxFingerprintPlus) {
                if (!FingerprintEnrollActivity.this.mIsFromSetupWizard) {
                    if (z) {
                        FingerprintEnrollActivity.this.mCompleteCheckDuration += 1000;
                    }
                    linearLayout = (LinearLayout) FingerprintEnrollActivity.this.findViewById(R.id.done_button_layout);
                }
            } else if (FingerprintEnrollActivity.this.mIsFromSetupWizard) {
                linearLayout = (LinearLayout) FingerprintEnrollActivity.this.findViewById(R.id.add_button_layout);
                if (z) {
                    FingerprintEnrollActivity.this.mCompleteCheckDuration += 1000;
                }
            } else {
                linearLayout = (LinearLayout) FingerprintEnrollActivity.this.findViewById(R.id.add_done_button_layout);
            }
            if (linearLayout != null) {
                if (z) {
                    new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$18$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FingerprintEnrollActivity.RunnableC025418 runnableC025418 = FingerprintEnrollActivity.RunnableC025418.this;
                            LinearLayout linearLayout2 = linearLayout;
                            runnableC025418.getClass();
                            linearLayout2.setVisibility(0);
                            if (FingerprintEnrollActivity.this.mEnrollGuideFrame != null) {
                                FingerprintEnrollActivity.this.mEnrollGuideFrame.hide();
                            }
                        }
                    }, FingerprintEnrollActivity.this.mCompleteCheckDuration);
                } else {
                    linearLayout.setVisibility(0);
                }
            }
            if (!FingerprintEnrollActivity.this.mIsFromSetupWizard || FingerprintEnrollActivity.this.mNextButtonArea == null || FingerprintEnrollActivity.this.mNextButton == null) {
                return;
            }
            FingerprintEnrollActivity.this.mNextButton.setText(R.string.next_button_label);
            if (z) {
                new Handler(FingerprintEnrollActivity.this.getMainLooper()).postDelayed(new FingerprintEnrollActivity$$ExternalSyntheticLambda1(3, this), FingerprintEnrollActivity.this.mCompleteCheckDuration);
            } else {
                FingerprintEnrollActivity.this.mNextButtonArea.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    enum EnrollState {
        NONE,
        PAUSE,
        ENROLL;

        EnrollState() {
        }
    }

    class LiftFingerMessage extends Thread {
        LiftFingerMessage() {
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
                        if (FingerprintEnrollActivity.this.mEnrollState == EnrollState.ENROLL && FingerprintEnrollActivity.this.mIsCalledLiftMsg) {
                            FingerprintEnrollActivity.this.setFingerGuideTitle(401);
                            FingerprintEnrollActivity.this.mLogging_LiftOff++;
                            FingerprintEnrollActivity.this.mIsShownLiftMsg = true;
                            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                                fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mTxtViewProgress, 211);
                            }
                            FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                            fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mGuideTitle, 210);
                        }
                    }
                });
            } catch (InterruptedException unused) {
                if (!FingerprintEnrollActivity.this.mIsCalledLiftMsg || FingerprintEnrollActivity.this.mIsRegisterCompleted) {
                    Log.d("BSS_FingerprintEnrollActivity", "LiftFingerMessage : Interrupted ");
                    return;
                }
                Log.d("BSS_FingerprintEnrollActivity", "LiftFingerMessage : Interrupted after start");
                FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity.mLiftFingerMessage = fingerprintEnrollActivity.new LiftFingerMessage();
                FingerprintEnrollActivity.this.mLiftFingerMessage.start();
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$6GQRyTAek17YwRVrWpHipskdhY8(FingerprintEnrollActivity fingerprintEnrollActivity, MotionEvent motionEvent) {
        fingerprintEnrollActivity.getClass();
        if (motionEvent.getAction() == 9 && fingerprintEnrollActivity.mIsFirstGuideShow) {
            fingerprintEnrollActivity.runTextToSpeech(0, fingerprintEnrollActivity.getString(R.string.sec_fingerprint_sensor_activated));
            Utils.playVibration(fingerprintEnrollActivity);
            fingerprintEnrollActivity.hideGuideScreen(300);
        }
    }

    /* renamed from: $r8$lambda$CiJ1LOzUG-9_rLQj6mhFReqNOBQ, reason: not valid java name */
    public static /* synthetic */ void m97$r8$lambda$CiJ1LOzUG9_rLQj6mhFReqNOBQ(FingerprintEnrollActivity fingerprintEnrollActivity, MotionEvent motionEvent) {
        fingerprintEnrollActivity.getClass();
        if (motionEvent.getAction() == 0) {
            fingerprintEnrollActivity.mIsTouchedSensorRectView = true;
        }
    }

    public static /* synthetic */ void $r8$lambda$MCmSJ4RT0eFf_EMTOoPdXnt8vRM(FingerprintEnrollActivity fingerprintEnrollActivity, View view) {
        MediaPlayer mediaPlayer = fingerprintEnrollActivity.mMediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                fingerprintEnrollActivity.mMediaPlayer.pause();
                view.announceForAccessibility(fingerprintEnrollActivity.getString(R.string.fingerprint_tts_stopped));
            } else {
                fingerprintEnrollActivity.mMediaPlayer.start();
                view.announceForAccessibility(fingerprintEnrollActivity.getString(R.string.fingerprint_tts_playing));
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$bwrpQrRZWrTXu0uAgf2hdQIyHJY(FingerprintEnrollActivity fingerprintEnrollActivity, int i, Intent intent) {
        fingerprintEnrollActivity.getClass();
        Log.secD("BSS_FingerprintEnrollActivity", "showSensorErrorDialog dismiss!!");
        if (i != 600) {
            if (i == 3) {
                fingerprintEnrollActivity.setResult(3);
                fingerprintEnrollActivity.finish();
                return;
            } else {
                intent.putExtra("enrollResult", 1);
                intent.putExtra("hw_auth_token", fingerprintEnrollActivity.mToken);
                fingerprintEnrollActivity.setResult(0, intent);
                fingerprintEnrollActivity.finish();
                return;
            }
        }
        FingerprintManager fingerprintManager = fingerprintEnrollActivity.mFingerprintManager;
        if (fingerprintManager != null) {
            fingerprintManager.semResumeEnroll();
        }
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = fingerprintEnrollActivity.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame != null) {
            fingerprintEnrollGuideFrame.show();
        }
        FingerprintEnrollSideGuideFrame fingerprintEnrollSideGuideFrame = fingerprintEnrollActivity.mSideSensorFrame;
        if (fingerprintEnrollSideGuideFrame != null) {
            fingerprintEnrollSideGuideFrame.show();
        }
    }

    /* renamed from: $r8$lambda$e6WjuVlGAwpRPnR-bR9j9T8BMII, reason: not valid java name */
    public static void m98$r8$lambda$e6WjuVlGAwpRPnRbR9j9T8BMII(FingerprintEnrollActivity fingerprintEnrollActivity, MotionEvent motionEvent) {
        int i;
        TextToSpeech textToSpeech;
        if (fingerprintEnrollActivity.mEnrollState != EnrollState.ENROLL) {
            return;
        }
        int i2 = 2;
        if (motionEvent.getActionMasked() == 10 && fingerprintEnrollActivity.mIsShownLiftMsg && !fingerprintEnrollActivity.mIsCaptureStarted) {
            Handler handler = new Handler();
            fingerprintEnrollActivity.mMsgHandler = handler;
            FingerprintEnrollActivity$$ExternalSyntheticLambda1 fingerprintEnrollActivity$$ExternalSyntheticLambda1 = new FingerprintEnrollActivity$$ExternalSyntheticLambda1(i2, fingerprintEnrollActivity);
            fingerprintEnrollActivity.mMsgRunnable = fingerprintEnrollActivity$$ExternalSyntheticLambda1;
            handler.postDelayed(fingerprintEnrollActivity$$ExternalSyntheticLambda1, 330L);
        }
        if (motionEvent.getActionMasked() == 9 || !(!fingerprintEnrollActivity.mIsFirstGuideShow || (textToSpeech = fingerprintEnrollActivity.mTts) == null || textToSpeech.isSpeaking())) {
            int inDisplayFingerPositionStringId = Utils.getInDisplayFingerPositionStringId(fingerprintEnrollActivity.mFingerPosition, motionEvent.getX(), motionEvent.getY());
            Rect rect = fingerprintEnrollActivity.mFingerPosition;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (rect == null) {
                i = R.string.fingerprint_tts_indisplay_position_up;
            } else {
                int[] iArr = {R.string.fingerprint_tts_indisplay_position_down_right, R.string.fingerprint_tts_indisplay_position_down, R.string.fingerprint_tts_indisplay_position_down_left, R.string.fingerprint_tts_indisplay_position_right, R.string.fingerprint_tts_indisplay_position_up, R.string.fingerprint_tts_indisplay_position_left, R.string.fingerprint_tts_indisplay_position_up_right, R.string.fingerprint_tts_indisplay_position_up, R.string.fingerprint_tts_indisplay_position_up_left};
                int i3 = (rect.left + rect.right) / 2;
                int i4 = (rect.top + rect.bottom) / 2;
                float f = i3;
                int i5 = x < f ? 0 : x > f ? 2 : 1;
                float f2 = i4;
                if (y < f2) {
                    i2 = 0;
                } else if (y <= f2) {
                    i2 = 1;
                }
                i = iArr[(i2 * 3) + i5];
            }
            fingerprintEnrollActivity.mTouchGuideStrId = i;
            if (inDisplayFingerPositionStringId <= 0) {
                fingerprintEnrollActivity.mIsTouchedOutside = false;
            } else {
                fingerprintEnrollActivity.runTextToSpeech(0, fingerprintEnrollActivity.getString(inDisplayFingerPositionStringId));
                fingerprintEnrollActivity.mIsTouchedOutside = true;
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$hPgBPFgWHYutmn2ibHDGmlbkQt4(FingerprintEnrollActivity fingerprintEnrollActivity) {
        fingerprintEnrollActivity.mIsShownLiftMsg = false;
        fingerprintEnrollActivity.setFingerGuideTitle(400);
        if (!fingerprintEnrollActivity.mIsShowErrorMsg) {
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mGuideTitle, 204);
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mTxtViewProgress, 204);
        }
        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
            return;
        }
        fingerprintEnrollActivity.runTextToSpeech(1, String.valueOf(fingerprintEnrollActivity.mGuideTitle.getText()));
    }

    public static void $r8$lambda$nnA8EVhUoLub6z16zP8Re6tZqZY(FingerprintEnrollActivity fingerprintEnrollActivity) {
        if (fingerprintEnrollActivity.mEnrollGuideFrame != null) {
            fingerprintEnrollActivity.startEnrollment();
            fingerprintEnrollActivity.mEnrollGuideFrame.show();
            boolean z = fingerprintEnrollActivity.mIsTalkbackEnabled;
            if (z || fingerprintEnrollActivity.mEnrollGuideFrame == null || z) {
                return;
            }
            fingerprintEnrollActivity.mTouchGuideHandler = new Handler(fingerprintEnrollActivity.getMainLooper());
            fingerprintEnrollActivity.mTouchGuideRunnable = new FingerprintEnrollActivity$$ExternalSyntheticLambda1(1, fingerprintEnrollActivity);
            fingerprintEnrollActivity.mEnrollGuideFrame.setTouchWindowListener(fingerprintEnrollActivity.mTouchGuideViewListener, fingerprintEnrollActivity.mFingerprintManager.semGetFingerIconRectInDisplay());
        }
    }

    public static void $r8$lambda$rZDxvTxrwQWkmM7M7Q4QW7t7nbk(FingerprintEnrollActivity fingerprintEnrollActivity) {
        TextView textView;
        if (!fingerprintEnrollActivity.mIsTouchedFingerFrame || (textView = fingerprintEnrollActivity.mGuideTitle) == null) {
            return;
        }
        textView.setText(fingerprintEnrollActivity.mIsTalkbackEnabled ? R.string.fingerprint_enroll_partial_tts : R.string.fingerprint_enroll_partial);
        fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mGuideTitle, 210);
        int i = fingerprintEnrollActivity.mLogging_Partial + 1;
        fingerprintEnrollActivity.mLogging_Partial = i;
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = fingerprintEnrollActivity.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame != null && i > 0 && i % 2 == 0) {
            fingerprintEnrollGuideFrame.tuneMovingArea(false);
            fingerprintEnrollActivity.mLogging_Partial = 0;
        }
        if (fingerprintEnrollActivity.mEnrollGuideFrame != null) {
            Log.d("BSS_FingerprintEnrollActivity", "TouchGuideRunnable - finishScan");
            fingerprintEnrollActivity.mEnrollGuideFrame.finishScan();
        }
    }

    /* renamed from: -$$Nest$mchangeGuideVideo, reason: not valid java name */
    static void m183$$Nest$mchangeGuideVideo(FingerprintEnrollActivity fingerprintEnrollActivity, int i) {
        Uri guideClipURI = fingerprintEnrollActivity.getGuideClipURI(i, 2);
        if (i == 300) {
            fingerprintEnrollActivity.mIsFirstGuideShowClose = true;
        }
        if (guideClipURI != null) {
            try {
                fingerprintEnrollActivity.mMediaPlayer.reset();
                fingerprintEnrollActivity.mMediaPlayer.setDataSource(fingerprintEnrollActivity, guideClipURI);
                fingerprintEnrollActivity.mMediaPlayer.setLooping(true);
                fingerprintEnrollActivity.mMediaPlayer.prepare();
                fingerprintEnrollActivity.mMediaPlayer.start();
            } catch (RuntimeException unused) {
                Log.e("BSS_FingerprintEnrollActivity", "Runtime Exception");
                fingerprintEnrollActivity.finish();
            } catch (Exception unused2) {
                Log.e("BSS_FingerprintEnrollActivity", "Exception");
                fingerprintEnrollActivity.finish();
            }
        }
    }

    /* renamed from: -$$Nest$mgetFingerRegisterText, reason: not valid java name */
    static int m185$$Nest$mgetFingerRegisterText(FingerprintEnrollActivity fingerprintEnrollActivity) {
        return fingerprintEnrollActivity.mCurrentPercent < 100 ? 400 : 402;
    }

    /* renamed from: -$$Nest$mlaunchMovingGuideTalkBack, reason: not valid java name */
    static void m187$$Nest$mlaunchMovingGuideTalkBack(FingerprintEnrollActivity fingerprintEnrollActivity, int i, int i2) {
        fingerprintEnrollActivity.getClass();
        fingerprintEnrollActivity.runTextToSpeech(1, fingerprintEnrollActivity.getString(((i == 8 && i2 == 5) || (i == 5 && i2 == 2)) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_up_inch : R.string.fingerprint_tts_guide_move_up : ((i == 4 && i2 == 7) || (i == 0 && i2 == 3) || (i == 3 && i2 == 6)) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_down_inch : R.string.fingerprint_tts_guide_move_down : ((i == 2 && i2 == 1) || (i == 1 && i2 == 0)) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_left_inch : R.string.fingerprint_tts_guide_move_left : ((i == 7 && i2 == 8) || (i == 3 && i2 == 4)) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_right_inch : R.string.fingerprint_tts_guide_move_right : (i == 5 && i2 == 1) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_up_left_inch : R.string.fingerprint_tts_guide_move_up_left : (i == 1 && i2 == 3) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_down_left_inch : R.string.fingerprint_tts_guide_move_down_left : ((i == 7 && i2 == 5) || (i == 6 && i2 == 4)) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_up_right_inch : R.string.fingerprint_tts_guide_move_up_right : (i == 4 && i2 == 7) ? fingerprintEnrollActivity.mIsUSA ? R.string.fingerprint_tts_guide_move_down_right_inch : R.string.fingerprint_tts_guide_move_down_right : R.string.fingerprint_tts_enroll_guide));
    }

    /* renamed from: -$$Nest$mremoveTouchGuideFrame, reason: not valid java name */
    static void m189$$Nest$mremoveTouchGuideFrame(FingerprintEnrollActivity fingerprintEnrollActivity) {
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = fingerprintEnrollActivity.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame != null) {
            fingerprintEnrollGuideFrame.setTouchWindowListener(null, null);
        }
        Handler handler = fingerprintEnrollActivity.mTouchGuideHandler;
        if (handler != null) {
            handler.removeCallbacks(fingerprintEnrollActivity.mTouchGuideRunnable);
        }
    }

    /* renamed from: -$$Nest$mrunCompleteRegistration, reason: not valid java name */
    static void m190$$Nest$mrunCompleteRegistration(FingerprintEnrollActivity fingerprintEnrollActivity) {
        fingerprintEnrollActivity.mIsRegisterCompleted = true;
        fingerprintEnrollActivity.mIsRotateGuideShow = false;
        fingerprintEnrollActivity.mBackEnabled = true;
        fingerprintEnrollActivity.setResult(-1);
        new Handler().postDelayed(fingerprintEnrollActivity.new RunnableC025418(), 300L);
        SALoggingHelper.insertEventLogging(1, fingerprintEnrollActivity.mLogging_Ok);
        SALoggingHelper.insertEventLogging(2, fingerprintEnrollActivity.mLogging_UpAndDown);
        SALoggingHelper.insertEventLogging(3, fingerprintEnrollActivity.mLogging_TooShort);
        SALoggingHelper.insertEventLogging(4, fingerprintEnrollActivity.mLogging_Partial);
        SALoggingHelper.insertEventLogging(5, fingerprintEnrollActivity.mLogging_Dirty);
        SALoggingHelper.insertEventLogging(6, fingerprintEnrollActivity.mLogging_LiftOff);
        SALoggingHelper.insertEventLogging(7, fingerprintEnrollActivity.mLogging_PressHarder);
        SALoggingHelper.insertEventLogging(8, fingerprintEnrollActivity.mLogging_DoNotMove);
        SALoggingHelper.insertFlowLogging(8234);
        if (fingerprintEnrollActivity.mIsFromSetupWizard) {
            SALoggingHelper.insertEventLogging(8262, 1);
        } else {
            SALoggingHelper.insertEventLogging(8262, 2);
        }
    }

    /* renamed from: -$$Nest$msetViewVisibility, reason: not valid java name */
    static void m193$$Nest$msetViewVisibility(FingerprintEnrollActivity fingerprintEnrollActivity, ViewGroup viewGroup) {
        fingerprintEnrollActivity.getClass();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setVisibility(8);
            childAt.setAlpha(0.0f);
        }
    }

    /* renamed from: -$$Nest$mshowAuthenticateResult, reason: not valid java name */
    static void m194$$Nest$mshowAuthenticateResult(int i, FingerprintEnrollActivity fingerprintEnrollActivity, String str) {
        Handler handler = fingerprintEnrollActivity.mAuthErrorHandler;
        if (handler != null) {
            handler.removeCallbacks(fingerprintEnrollActivity.mAuthErrorRunnable);
            fingerprintEnrollActivity.mAuthErrorHandler = null;
        }
        PowerManager powerManager = fingerprintEnrollActivity.mPowerManager;
        if (powerManager != null) {
            powerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
        }
        fingerprintEnrollActivity.runTextToSpeech(0, str);
        if (i == 0 || i == 100) {
            TextView textView = fingerprintEnrollActivity.mAuthErrorText;
            if (textView != null && str != null) {
                textView.setVisibility(4);
                fingerprintEnrollActivity.mAuthErrorText.setText(str);
                fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mAuthErrorText, 200);
            }
            str = fingerprintEnrollActivity.getString(R.string.fingerprint_register_swipe_enroll_guide_title);
        } else if (fingerprintEnrollActivity.mAuthErrorText.getVisibility() == 0) {
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mAuthErrorText, 203);
        }
        TextView textView2 = (TextView) fingerprintEnrollActivity.findViewById(R.id.swipe_enroll_guide_title);
        if (textView2 != null && str != null) {
            textView2.setVisibility(4);
            textView2.setText(str);
            fingerprintEnrollActivity.startViewAnimation(textView2, 210);
        }
        ImageView imageView = fingerprintEnrollActivity.mAuthErrorImage;
        if (imageView != null && i == 0) {
            imageView.setVisibility(4);
            fingerprintEnrollActivity.mAuthErrorImage.setImageResource(R.drawable.sec_fingerprint_verification_success_color);
            fingerprintEnrollActivity.mAuthErrorImage.setAlpha(1.0f);
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mAuthErrorImage, 200);
        }
        if (i == 0 || i == 100) {
            return;
        }
        if (fingerprintEnrollActivity.mAuthErrorRunnable == null) {
            fingerprintEnrollActivity.mAuthErrorRunnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.8
                @Override // java.lang.Runnable
                public final void run() {
                    FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                    FingerprintEnrollActivity.m194$$Nest$mshowAuthenticateResult(100, fingerprintEnrollActivity2, fingerprintEnrollActivity2.getString(R.string.fingerprint_register_swipe_enroll_guide_tap_text));
                }
            };
        }
        Handler handler2 = new Handler();
        fingerprintEnrollActivity.mAuthErrorHandler = handler2;
        handler2.postDelayed(fingerprintEnrollActivity.mAuthErrorRunnable, 5000L);
    }

    /* renamed from: -$$Nest$mshowErrorMessage, reason: not valid java name */
    static void m195$$Nest$mshowErrorMessage(int i, FingerprintEnrollActivity fingerprintEnrollActivity, final String str) {
        int intDb;
        if (fingerprintEnrollActivity.mIsTalkbackEnabled && fingerprintEnrollActivity.mIsTouchedOutside) {
            return;
        }
        Log.d("BSS_FingerprintEnrollActivity", "imageQuality[" + str + "]");
        if (Utils.Config.FP_FEATURE_SENSOR_IS_ULTRASONIC && (i == 1 || i == 1003)) {
            int i2 = fingerprintEnrollActivity.mFilmErrorCount + 1;
            fingerprintEnrollActivity.mFilmErrorCount = i2;
            if (i2 == 10 && (intDb = Utils.getIntDb(fingerprintEnrollActivity.getBaseContext(), "fingerprint_protective_film_guideline_displayed", true, 0)) < 2) {
                fingerprintEnrollActivity.showSensorErrorDialog(600, null);
                Utils.putIntDb(fingerprintEnrollActivity.getBaseContext(), "fingerprint_protective_film_guideline_displayed", true, intDb + 1);
                return;
            }
        }
        fingerprintEnrollActivity.removeErrorMessageHandler();
        boolean z = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
        if (!z || fingerprintEnrollActivity.mIsShowErrorMsg) {
            fingerprintEnrollActivity.runTextToSpeech(0, str);
        } else {
            fingerprintEnrollActivity.runTextToSpeech(1, str);
        }
        if (!fingerprintEnrollActivity.mIsShowErrorMsg) {
            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mGuideTitle, 203);
            if (!z) {
                fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mTxtViewProgress, 203);
            }
            fingerprintEnrollActivity.mIsShowErrorMsg = true;
        }
        if (!z) {
            fingerprintEnrollActivity.runTextToSpeech(1, String.valueOf(fingerprintEnrollActivity.mGuideTitle.getText()));
        }
        Handler handler = new Handler();
        fingerprintEnrollActivity.mShowErrorHandler = handler;
        Runnable runnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.9
            @Override // java.lang.Runnable
            public final void run() {
                if (!FingerprintEnrollActivity.this.mIsShowErrorMsg || FingerprintEnrollActivity.this.mGuideTitle == null) {
                    return;
                }
                FingerprintEnrollActivity.this.mGuideTitle.setText(str);
                FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mGuideTitle, 210);
            }
        };
        fingerprintEnrollActivity.mShowErrorRunnable = runnable;
        handler.postDelayed(runnable, 200L);
        if (fingerprintEnrollActivity.mHideErrorHandler == null) {
            fingerprintEnrollActivity.mHideErrorHandler = new Handler();
            fingerprintEnrollActivity.mHideErrorRunnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.10
                @Override // java.lang.Runnable
                public final void run() {
                    if (FingerprintEnrollActivity.this.mIsShowErrorMsg) {
                        FingerprintEnrollActivity.this.mIsShowErrorMsg = false;
                        try {
                            Thread.sleep(130L);
                        } catch (InterruptedException unused) {
                            Log.d("BSS_FingerprintEnrollActivity", "mHideErrorRunnable : Interrupted");
                        }
                        FingerprintEnrollActivity.this.setFingerGuideTitle(400);
                        FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                        fingerprintEnrollActivity2.startViewAnimation(fingerprintEnrollActivity2.mGuideTitle, 210);
                        if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            return;
                        }
                        FingerprintEnrollActivity fingerprintEnrollActivity3 = FingerprintEnrollActivity.this;
                        fingerprintEnrollActivity3.startViewAnimation(fingerprintEnrollActivity3.mTxtViewProgress, 204);
                    }
                }
            };
        }
        if (i != 0) {
            Log.d("BSS_FingerprintEnrollActivity", "Run_Runnable_ErrorMSG : " + fingerprintEnrollActivity.mHideErrorHandler.postDelayed(fingerprintEnrollActivity.mHideErrorRunnable, 5000L));
        }
    }

    /* renamed from: -$$Nest$mstartAuthentication, reason: not valid java name */
    static void m199$$Nest$mstartAuthentication(FingerprintEnrollActivity fingerprintEnrollActivity) {
        fingerprintEnrollActivity.getClass();
        Log.d("BSS_FingerprintEnrollActivity", "startAuthentication");
        int i = fingerprintEnrollActivity.mAuthGuidePlayCount;
        if (i <= 0 && !fingerprintEnrollActivity.mIsSideSensor) {
            fingerprintEnrollActivity.mAuthGuidePlayCount = i + 1;
            MediaPlayer mediaPlayer = fingerprintEnrollActivity.mMediaPlayer;
            if (mediaPlayer != null) {
                mediaPlayer.start();
                return;
            }
        }
        MediaPlayer mediaPlayer2 = fingerprintEnrollActivity.mMediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.stop();
            fingerprintEnrollActivity.mMediaPlayer.release();
            fingerprintEnrollActivity.mMediaPlayer = null;
        }
        fingerprintEnrollActivity.hideGuideScreen(302);
        SemFingerprintManager createInstance = SemFingerprintManager.createInstance(fingerprintEnrollActivity);
        fingerprintEnrollActivity.mSemFingerprintManager = createInstance;
        if (createInstance == null) {
            Log.w("BSS_FingerprintEnrollActivity", "mSemFingerprintManager == null");
            fingerprintEnrollActivity.showSensorErrorDialog(0, null);
        } else {
            fingerprintEnrollActivity.mAuthenticateCancel = new CancellationSignal();
            Bundle bundle = new Bundle();
            bundle.putInt("sem_privileged_attr", 1);
            fingerprintEnrollActivity.mSemFingerprintManager.authenticate((SemFingerprintManager.CryptoObject) null, fingerprintEnrollActivity.mAuthenticateCancel, fingerprintEnrollActivity.mSemAuthCallback, (Handler) null, fingerprintEnrollActivity.mUserId, bundle);
        }
    }

    /* renamed from: -$$Nest$mtuneTouchGuideFrame, reason: not valid java name */
    static void m202$$Nest$mtuneTouchGuideFrame(FingerprintEnrollActivity fingerprintEnrollActivity) {
        int i;
        FingerprintEnrollGuideFrame fingerprintEnrollGuideFrame = fingerprintEnrollActivity.mEnrollGuideFrame;
        if (fingerprintEnrollGuideFrame == null || (i = fingerprintEnrollActivity.mLogging_Partial) <= 0 || i % 2 != 0) {
            return;
        }
        fingerprintEnrollGuideFrame.tuneMovingArea(false);
        fingerprintEnrollActivity.mLogging_Partial = 0;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustTextureViewRatio(int i, int i2) {
        TextureView textureView = this.mTextureView;
        if (textureView == null) {
            return;
        }
        int width = textureView.getWidth();
        Matrix matrix = new Matrix();
        this.mTextureView.getTransform(matrix);
        matrix.setScale(((int) (this.mTextureView.getHeight() * (i / i2))) / width, 1.0f);
        matrix.postTranslate((width - r5) / 2, 0.0f);
        this.mTextureView.setTransform(matrix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishRegistration() {
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

    /* JADX WARN: Code restructure failed: missing block: B:27:0x003d, code lost:
    
        if (r7 == 1) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003f, code lost:
    
        r6 = "sec_fingerprint_v_01";
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0047, code lost:
    
        if (com.samsung.android.biometrics.app.setting.Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Uri getGuideClipURI(int i, int i2) {
        String str;
        switch (i) {
            case 300:
                if (!this.mIsSupportFoldEnroll) {
                    if (i2 == 1) {
                        break;
                    }
                    str = "sec_fingerprint_v_02";
                    break;
                } else if (getResources().getConfiguration().semDisplayDeviceType == 5) {
                    if (i2 != 1) {
                        str = "sec_fingerprint_v_fold_02";
                        break;
                    } else {
                        str = "sec_fingerprint_v_fold_01";
                        break;
                    }
                }
                break;
            case 301:
                if (i2 != 1) {
                    str = "sec_fingerprint_h_02";
                    break;
                } else {
                    str = "sec_fingerprint_h_01";
                    break;
                }
            case 302:
                str = "sec_fingerprint_recognition_tap";
                break;
            case 303:
                if (!this.mIsSupportFoldEnroll || this.mDisplayType != 5) {
                    str = "sec_fingerprint_unfold";
                    break;
                } else {
                    str = "sec_fingerprint_fold";
                    break;
                }
            default:
                str = null;
                break;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return Uri.parse("android.resource://" + getPackageName() + "/raw/" + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideGuideScreen(int i) {
        RelativeLayout relativeLayout;
        FingerprintManager fingerprintManager;
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
                new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (!FingerprintEnrollActivity.this.mIsTalkbackEnabled || FingerprintEnrollActivity.this.mIsCaptureStarted || Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                            FingerprintEnrollActivity.this.setFingerGuideTitle(400);
                            if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                FingerprintEnrollActivity.this.mRegisterScreen.announceForAccessibility(FingerprintEnrollActivity.this.getString(R.string.fingerprint_tts_enroll_guide));
                                FingerprintEnrollActivity.this.mRegisterScreen.announceForAccessibility(FingerprintEnrollActivity.this.getString(R.string.fingerprint_tts_guide_scan));
                            }
                        } else {
                            FingerprintEnrollActivity.this.setFingerGuideTitle(401);
                            FingerprintEnrollActivity.this.mIsShownLiftMsg = true;
                            FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                            fingerprintEnrollActivity.startViewAnimation(fingerprintEnrollActivity.mTxtViewProgress, 211);
                        }
                        FingerprintEnrollActivity fingerprintEnrollActivity2 = FingerprintEnrollActivity.this;
                        FingerprintEnrollActivity.m193$$Nest$msetViewVisibility(fingerprintEnrollActivity2, fingerprintEnrollActivity2.mFirstGuideScreen);
                    }
                }, 500L);
                break;
            case 301:
                showKnoxBi(true);
                startViewAnimation(this.mSecondGuideScreen, 201);
                startViewAnimation(this.mRegisterScreen, 200);
                if (this.mIsFromSetupWizard && (relativeLayout = this.mNextButtonArea) != null) {
                    relativeLayout.setVisibility(4);
                }
                setFingerGuideTitle(400);
                new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.6
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintEnrollActivity fingerprintEnrollActivity = FingerprintEnrollActivity.this;
                        FingerprintEnrollActivity.m193$$Nest$msetViewVisibility(fingerprintEnrollActivity, fingerprintEnrollActivity.mSecondGuideScreen);
                    }
                }, 300L);
                new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.7
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (FingerprintEnrollActivity.this.mEnrollState != EnrollState.PAUSE || FingerprintEnrollActivity.this.mFingerprintManager == null) {
                            return;
                        }
                        FingerprintEnrollActivity.this.mEnrollState = EnrollState.ENROLL;
                        FingerprintEnrollActivity.this.mFingerprintManager.semResumeEnroll();
                    }
                }, 500L);
                break;
            case 302:
                this.mIsSwipeEnrollGuideShow = false;
                TextureView textureView = this.mTextureView;
                if (textureView != null) {
                    textureView.setVisibility(8);
                }
                startViewAnimation(findViewById(R.id.swipe_enroll_guide_clip_layout), 201);
                startViewAnimation(findViewById(R.id.swipe_enroll_guide_vi_layout), 200);
                break;
            case 303:
                this.mIsFoldingGuideShow = false;
                startViewAnimation(this.mUnfoldGuideScreen, 211);
                if (this.mGuideStep == 0 && this.mEnrollState == EnrollState.PAUSE && (fingerprintManager = this.mFingerprintManager) != null) {
                    this.mEnrollState = EnrollState.ENROLL;
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
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        TextureView textureView2 = this.mTextureView;
        if (textureView2 != null) {
            textureView2.destroyDrawingCache();
            this.mTextureView.setSurfaceTextureListener(null);
            this.mTextureView = null;
        }
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
            this.mSurface = null;
        }
    }

    private void initLayoutHeight() {
        int i;
        int i2;
        getDisplay().getRealSize(new Point());
        if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
            int i3 = (int) (((1.0f - getResources().getFloat(R.dimen.sec_fingerprint_register_width_ratio)) * r1.x) / 2.0f);
            if (Utils.isTablet() && Utils.isScreenLandscape(this)) {
                i = R.dimen.f37x29e0a996;
                i2 = R.dimen.sec_fingerprint_register_progress_vi_height_ratio_tablet_land;
            } else {
                i = R.dimen.sec_fingerprint_register_progress_first_vi_height_ratio;
                i2 = R.dimen.sec_fingerprint_register_progress_vi_height_ratio;
            }
            setViewWeight(R.id.register_first_guide_vi_layout, i);
            setViewWeight(R.id.register_second_guide_vi_layout, i);
            setViewWeight(R.id.swipe_enroll_guide_clip_vi_layout, i);
            setViewWeight(R.id.swipe_enroll_guide_vi_image_layout, i);
            setViewWeight(R.id.finger_view, i2);
            findViewById(R.id.first_guide_screen).setPadding(i3, 0, i3, 0);
            findViewById(R.id.register_layout).setPadding(i3, 0, i3, 0);
            findViewById(R.id.second_guide_screen_layout).setPadding(i3, 0, i3, 0);
            findViewById(R.id.swipe_enroll_guide_screen).setPadding(i3, 0, i3, 0);
            return;
        }
        double d = getResources().getFloat(R.dimen.sec_fingerprint_register_width_ratio) * r1.x;
        if (Utils.isTablet()) {
            double d2 = r1.y * getResources().getFloat(R.dimen.sec_fingerprint_register_marginTop_ratio);
            View findViewById = findViewById(R.id.register_screen);
            if (findViewById != null) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.width = (int) d;
                layoutParams.topMargin = (int) 0.0d;
                findViewById.requestLayout();
            }
            View findViewById2 = findViewById(R.id.first_guide_screen);
            if (findViewById2 != null) {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) findViewById2.getLayoutParams();
                layoutParams2.width = (int) d;
                layoutParams2.topMargin = (int) d2;
                findViewById2.requestLayout();
            }
            setViewWeight(R.id.register_guide_top_margin_layout, R.dimen.sec_fingerprint_enroll_1st_layout_ratio);
            setViewWeight(R.id.register_guide_text_layout, R.dimen.sec_fingerprint_enroll_2nd_layout_ratio);
            setViewWeight(R.id.progress_text, R.dimen.sec_fingerprint_enroll_3rd_layout_ratio);
            setViewWeight(R.id.finger_area_view, R.dimen.sec_fingerprint_enroll_4th_layout_ratio);
        }
        double d3 = r1.y * getResources().getFloat(R.dimen.sec_fingerprint_register_guide_height_ratio);
        View findViewById3 = findViewById(R.id.register_first_guide_vi_layout);
        if (findViewById3 != null) {
            findViewById3.getLayoutParams().height = (int) d3;
            findViewById3.requestLayout();
        }
        View findViewById4 = findViewById(R.id.register_second_guide_vi_layout);
        if (findViewById4 != null) {
            findViewById4.getLayoutParams().height = (int) d3;
            findViewById4.requestLayout();
        }
    }

    private boolean isSystemKeyEventRequested(int i) {
        try {
            return IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSystemKeyEventRequested(i, getComponentName());
        } catch (RemoteException e) {
            Log.e("BSS_FingerprintEnrollActivity", "isSystemKeyEventRequested - " + e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeErrorMessageHandler() {
        Handler handler = this.mHideErrorHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mHideErrorRunnable);
        }
        Handler handler2 = this.mShowErrorHandler;
        if (handler2 != null) {
            handler2.removeCallbacks(this.mShowErrorRunnable);
        }
    }

    private void requestSystemKeyEvent(int i, boolean z) {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).requestSystemKeyEvent(i, getComponentName(), z);
        } catch (RemoteException e) {
            Log.e("BSS_FingerprintEnrollActivity", "requestSystemKeyEvent - " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runTextToSpeech(int i, String str) {
        if (this.mTts == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.mTts.speak(str, i, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFingerGuideTitle(int i) {
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
                this.mGuideTitle.append("\n\n" + getString(R.string.fingerprint_register_add_another_fingerprint));
                return;
            }
            return;
        }
        if (i == 400) {
            if (!this.mIsRotateGuideShow) {
                if (this.mIsSupportSwipeEnroll || this.mIsVibrationSupport) {
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setSensorPosition(View view, boolean z) {
        int width;
        int height;
        int i;
        int i2;
        int i3;
        DisplayCutout displayCutout;
        if (!this.mIsDisplaySensor || view == null) {
            return;
        }
        Rect semGetFingerIconRectInDisplay = this.mFingerprintManager.semGetFingerIconRectInDisplay();
        this.mFingerPosition = semGetFingerIconRectInDisplay;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        int rotation = Utils.getRotation(getBaseContext());
        if (rotation != 0) {
            if (rotation == 1) {
                width = semGetFingerIconRectInDisplay.height();
                height = semGetFingerIconRectInDisplay.width();
                if (z) {
                    i = width / 3;
                    i2 = height / 10;
                } else {
                    i = 0;
                    i2 = 0;
                }
                WindowInsets rootWindowInsets = getWindow().getDecorView().getRootWindowInsets();
                if (rootWindowInsets != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                    i3 = displayCutout.getSafeInsetTop();
                }
                i3 = 0;
            } else if (rotation != 2) {
                if (rotation != 3) {
                    width = 0;
                    height = 0;
                    i = 0;
                    i2 = i;
                    i3 = i2;
                } else {
                    width = semGetFingerIconRectInDisplay.height();
                    height = semGetFingerIconRectInDisplay.width();
                    if (z) {
                        i = width / 3;
                        i2 = height / 10;
                    } else {
                        i = 0;
                        i2 = 0;
                    }
                    if (!SettingHelper.isNavigationBarHidden(this) && !Utils.isTablet()) {
                        i3 = getResources().getDimensionPixelSize(android.R.dimen.notification_custom_view_max_image_height_low_ram);
                    }
                    i3 = 0;
                }
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.setMarginStart((semGetFingerIconRectInDisplay.left + i) - i3);
            layoutParams.rightMargin = semGetFingerIconRectInDisplay.right - i;
            if (z) {
                layoutParams.height -= i2 * 2;
                layoutParams.width -= i * 2;
            }
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            layoutParams.topMargin = (semGetFingerIconRectInDisplay.top - (identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0)) + i2;
            addContentView(view, layoutParams);
        }
        width = semGetFingerIconRectInDisplay.width();
        height = semGetFingerIconRectInDisplay.height();
        if (z) {
            i = width / 10;
            i2 = height / 3;
            i3 = 0;
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(width, height);
            layoutParams2.setMarginStart((semGetFingerIconRectInDisplay.left + i) - i3);
            layoutParams2.rightMargin = semGetFingerIconRectInDisplay.right - i;
            if (z) {
            }
            int identifier2 = getResources().getIdentifier("status_bar_height", "dimen", "android");
            layoutParams2.topMargin = (semGetFingerIconRectInDisplay.top - (identifier2 > 0 ? getResources().getDimensionPixelSize(identifier2) : 0)) + i2;
            addContentView(view, layoutParams2);
        }
        i = 0;
        i2 = i;
        i3 = i2;
        RelativeLayout.LayoutParams layoutParams22 = new RelativeLayout.LayoutParams(width, height);
        layoutParams22.setMarginStart((semGetFingerIconRectInDisplay.left + i) - i3);
        layoutParams22.rightMargin = semGetFingerIconRectInDisplay.right - i;
        if (z) {
        }
        int identifier22 = getResources().getIdentifier("status_bar_height", "dimen", "android");
        layoutParams22.topMargin = (semGetFingerIconRectInDisplay.top - (identifier22 > 0 ? getResources().getDimensionPixelSize(identifier22) : 0)) + i2;
        addContentView(view, layoutParams22);
    }

    private void setViewWeight(int i, int i2) {
        View findViewById = findViewById(i);
        float f = getResources().getFloat(i2);
        if (findViewById != null) {
            ((LinearLayout.LayoutParams) findViewById.getLayoutParams()).weight = f;
            findViewById.requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuideScreen(int i) {
        final View findViewById;
        Button button;
        SemPersonaManager semPersonaManager;
        FingerprintManager fingerprintManager;
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
        switch (i) {
            case 300:
                this.mIsFirstGuideShow = true;
                this.mIsFirstGuideShowClose = false;
                TextView textView = (TextView) findViewById(R.id.first_guide_title);
                if (textView != null) {
                    if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                        if (this.mIsSupportSwipeEnroll || this.mIsRearSensor || this.mIsVibrationSupport) {
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
                this.mFirstGuideScreen.setVisibility(0);
                this.mTextureView = (TextureView) findViewById(R.id.register_popup_img_first);
                Settings.System.putInt(getContentResolver(), "fingerprint_guide_shown", 1);
                if (Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL && !Utils.Config.FP_FEATURE_LOCAL_HBM && !Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE && Settings.System.getInt(getContentResolver(), "fingerprint_blf_off_guide_shown", 0) == 0) {
                    if (Settings.System.getInt(getBaseContext().getContentResolver(), "blue_light_filter", 0) != 0) {
                        Toast.makeText(getBaseContext(), R.string.fingerprint_eye_comfort_shield_off, 0).show();
                        Settings.System.putInt(getContentResolver(), "fingerprint_blf_off_guide_shown", 1);
                    }
                }
                if (this.mIsTalkbackEnabled && (findViewById = findViewById(R.id.register_first_guide_vi_layout)) != null) {
                    findViewById.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.22
                        @Override // android.view.View.AccessibilityDelegate
                        public final void sendAccessibilityEvent(View view, int i2) {
                            if (i2 == 32768 && FingerprintEnrollActivity.this.mMediaPlayer != null) {
                                StringBuilder sb = new StringBuilder(FingerprintEnrollActivity.this.getString(R.string.fingerprint_tts_animation_showing));
                                int i3 = FingerprintEnrollActivity.this.mMediaPlayer.isPlaying() ? R.string.fingerprint_tts_stop : R.string.fingerprint_tts_play;
                                sb.append(", ");
                                sb.append(FingerprintEnrollActivity.this.getString(i3));
                                sb.append(", ");
                                sb.append(FingerprintEnrollActivity.this.getString(R.string.fingerprint_tts_button));
                                view.setContentDescription(sb);
                            }
                            super.sendAccessibilityEvent(view, i2);
                        }
                    });
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            FingerprintEnrollActivity.$r8$lambda$MCmSJ4RT0eFf_EMTOoPdXnt8vRM(FingerprintEnrollActivity.this, findViewById);
                        }
                    });
                    break;
                }
                break;
            case 301:
                this.mIsRotateGuideShow = true;
                showKnoxBi(false);
                startViewAnimation(this.mRegisterScreen, 201);
                startViewAnimation(this.mSecondGuideScreen, 207);
                if (!this.mIsFromSetupWizard) {
                    new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.14
                        @Override // java.lang.Runnable
                        public final void run() {
                            View findViewById2 = FingerprintEnrollActivity.this.findViewById(R.id.continue_raised_button_layout);
                            Button button2 = (Button) FingerprintEnrollActivity.this.findViewById(R.id.continue_raised_button);
                            if (findViewById2 == null || button2 == null) {
                                return;
                            }
                            findViewById2.setVisibility(0);
                            button2.semSetButtonShapeEnabled(true);
                        }
                    }, 500L);
                } else if (this.mNextButtonArea != null && (button = this.mNextButton) != null) {
                    button.setText(R.string.fingerprint_continue_button_text);
                    this.mNextButtonArea.setVisibility(0);
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
                RelativeLayout relativeLayout = this.mNextButtonArea;
                if (relativeLayout != null && this.mIsFromSetupWizard) {
                    relativeLayout.setVisibility(4);
                }
                startViewAnimation(this.mSwipeEnrollGuideScreen, 207);
                this.mTextureView = (TextureView) findViewById(R.id.swipe_enroll_guide_vi);
                if (SemPersonaManager.isKnoxId(this.mUserId)) {
                    Context baseContext = getBaseContext();
                    int i2 = this.mUserId;
                    boolean z = Utils.DEBUG;
                    this.mAuthErrorText.setText(getString(R.string.fingerprint_register_swipe_enroll_guide_text_workspace, new Object[]{(baseContext == null || (semPersonaManager = (SemPersonaManager) baseContext.getSystemService("persona")) == null) ? "Knox" : SemPersonaManager.isSecureFolderId(i2) ? baseContext.getString(R.string.secure_folder_set_ppp_title) : semPersonaManager.getContainerName(i2, baseContext)}));
                }
                TextView textView5 = (TextView) findViewById(R.id.swipe_enroll_guide_title);
                if (textView5 != null) {
                    runTextToSpeech(1, String.valueOf(textView5.getText()));
                }
                TextView textView6 = this.mAuthErrorText;
                if (textView6 != null) {
                    runTextToSpeech(1, String.valueOf(textView6.getText()));
                    break;
                }
                break;
            case 303:
                this.mIsFoldingGuideShow = true;
                if (this.mEnrollState == EnrollState.ENROLL && (fingerprintManager = this.mFingerprintManager) != null) {
                    this.mEnrollState = EnrollState.PAUSE;
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
                TextView textView7 = (TextView) findViewById(R.id.folding_guide_text);
                if (textView7 != null) {
                    textView7.setText(i3);
                }
                startViewAnimation(this.mUnfoldGuideScreen, 207);
                this.mTextureView = (TextureView) findViewById(R.id.unfold_guide_clip);
                runTextToSpeech(0, getString(i3));
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

    /* JADX INFO: Access modifiers changed from: private */
    public void showKnoxBi(boolean z) {
        ImageView imageView = this.mImageViewKnoxBi;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSensorErrorDialog(final int i, CharSequence charSequence) {
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
        intent.putExtra("fingerprint_settings_destroy", false);
        if (i == 3) {
            r1 = getResources().getString(R.string.sec_fingerprint_error_dialog_could_not_register);
            string = getResources().getString(R.string.fingerprint_error_message_timed_out);
        } else if (i != 4) {
            if (i != 18) {
                if (i == 600) {
                    r1 = getResources().getString(R.string.fingerprint_error_title_trouble_with_sensor);
                    String string2 = getResources().getString(R.string.fingerprint_error_message_bullet);
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
                        intent.putExtra("fingerprint_settings_destroy", true);
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
            intent.putExtra("fingerprint_settings_destroy", true);
        } else {
            r1 = getResources().getString(R.string.fingerprint_error_title_no_space);
            string = getResources().getString(R.string.fingerprint_error_message_no_space);
        }
        AlertDialog create = new AlertDialog.Builder(this, (!Utils.isColorThemeEnabled(this) || Utils.isNightThemeOn(this)) ? android.R.style.Theme.DeviceDefault.Dialog.Alert : 0).setTitle(r1).setMessage(string).setPositiveButton(android.R.string.ok, new FingerprintEnrollActivity$$ExternalSyntheticLambda2()).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                FingerprintEnrollActivity.$r8$lambda$bwrpQrRZWrTXu0uAgf2hdQIyHJY(FingerprintEnrollActivity.this, i, intent);
            }
        }).create();
        create.setCanceledOnTouchOutside(false);
        create.show();
        create.getButton(-1).setTextColor(getResources().getColor(R.color.fingerprint_verification_negative_text_color));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEnrollStep() {
        final int i = 0;
        this.mGuideTitle.setVisibility(0);
        this.mTxtViewProgress.setVisibility(0);
        if (this.mIsTalkbackEnabled && this.mTts != null && this.mIsDisplaySensor) {
            View view = new View(this);
            this.mSensorPositionView = view;
            view.setClickable(false);
            if (!Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                this.mSensorPositionView.setContentDescription(getString(R.string.fingerprint_tts_indisplay_position_guide));
            }
            this.mSensorPositionView.setOnHoverListener(new View.OnHoverListener(this) { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda5
                public final /* synthetic */ FingerprintEnrollActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view2, MotionEvent motionEvent) {
                    switch (i) {
                        case 0:
                            FingerprintEnrollActivity.$r8$lambda$6GQRyTAek17YwRVrWpHipskdhY8(this.f$0, motionEvent);
                            break;
                        default:
                            FingerprintEnrollActivity.m98$r8$lambda$e6WjuVlGAwpRPnRbR9j9T8BMII(this.f$0, motionEvent);
                            break;
                    }
                    return false;
                }
            });
            final int i2 = 1;
            setSensorPosition(this.mSensorPositionView, true);
            getWindow().getDecorView().setOnHoverListener(new View.OnHoverListener(this) { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity$$ExternalSyntheticLambda5
                public final /* synthetic */ FingerprintEnrollActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view2, MotionEvent motionEvent) {
                    switch (i2) {
                        case 0:
                            FingerprintEnrollActivity.$r8$lambda$6GQRyTAek17YwRVrWpHipskdhY8(this.f$0, motionEvent);
                            break;
                        default:
                            FingerprintEnrollActivity.m98$r8$lambda$e6WjuVlGAwpRPnRbR9j9T8BMII(this.f$0, motionEvent);
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
            handler.postDelayed(new FingerprintEnrollActivity$$ExternalSyntheticLambda1(i, this), this.mEnrollStartRemainTime);
            return;
        }
        if (this.mIsSideSensor) {
            if (this.mIsSupportFoldEnroll) {
                this.mDisplayType = getResources().getConfiguration().semDisplayDeviceType;
            }
            FingerprintEnrollSensorHelper fingerprintEnrollSensorHelper = new FingerprintEnrollSensorHelper(this);
            if (fingerprintEnrollSensorHelper.height > 0) {
                if ((getResources().getConfiguration().semDisplayDeviceType == 5 ? fingerprintEnrollSensorHelper.foldTopMargin : fingerprintEnrollSensorHelper.topMargin) > 0) {
                    if (this.mSideSensorFrame == null) {
                        this.mSideSensorFrame = new FingerprintEnrollSideGuideFrame(this, fingerprintEnrollSensorHelper);
                    }
                    this.mSideSensorFrame.show();
                }
            }
        }
        startEnrollment();
        this.mGuideTitle.requestAccessibilityFocus();
    }

    private void startEnrollment() {
        Log.d("BSS_FingerprintEnrollActivity", "startEnrollment");
        if (this.mIsPauseRegistration || this.mEnrollState != EnrollState.NONE) {
            Log.d("BSS_FingerprintEnrollActivity", "Skip startEnrollment!! mIsPauseRegistration = " + this.mIsPauseRegistration + " | mEnrollState = " + this.mEnrollState);
            return;
        }
        byte[] bArr = this.mToken;
        if (bArr != null && bArr.length == 1 && bArr[0] == -1) {
            Log.d("BSS_FingerprintEnrollActivity", "Challenge is incorrect");
            showSensorErrorDialog(1003, null);
        }
        if (this.mToken == null || this.mFingerprintManager == null) {
            Log.secD("BSS_FingerprintEnrollActivity", "startEnrollment : mToken or mFingerprintManager is null");
            showSensorErrorDialog(0, null);
            return;
        }
        showKnoxBi(true);
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
            this.mFingerprintManager.enroll(this.mToken, this.mEnrollmentCancel, this.mUserId, this.mEnrollmentCallback, 2);
        }
        SALoggingHelper.insertFlowLogging(8255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startViewAnimation(View view, int i) {
        if (view == null) {
            if (i == 202) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
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
            animationSet2.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
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
                AlphaAnimation alphaAnimation3 = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation3.setDuration(500L);
                alphaAnimation3.setFillAfter(true);
                view.startAnimation(alphaAnimation3);
                break;
            case 201:
                AlphaAnimation alphaAnimation4 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation4.setDuration(200L);
                alphaAnimation4.setFillAfter(true);
                view.startAnimation(alphaAnimation4);
                if (view != findViewById(R.id.swipe_enroll_guide_vi_layout) && view != findViewById(R.id.swipe_enroll_guide_clip_layout)) {
                    view.setVisibility(4);
                    break;
                } else {
                    view.setVisibility(8);
                    break;
                }
                break;
            case 203:
                AnimationSet animationSet3 = new AnimationSet(true);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f, 1, 0.5f, 1, 0.5f);
                scaleAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f));
                scaleAnimation.setDuration(150L);
                scaleAnimation.setFillAfter(true);
                AlphaAnimation alphaAnimation5 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation5.setDuration(150L);
                alphaAnimation5.setFillAfter(true);
                animationSet3.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                animationSet3.addAnimation(alphaAnimation5);
                animationSet3.addAnimation(scaleAnimation);
                animationSet3.setFillAfter(true);
                view.startAnimation(animationSet3);
                view.setVisibility(4);
                break;
            case 204:
                view.setVisibility(0);
                AnimationSet animationSet4 = new AnimationSet(true);
                TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.03f, 1, 0.0f);
                translateAnimation.setDuration(400L);
                AlphaAnimation alphaAnimation6 = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation6.setDuration(400L);
                alphaAnimation6.setFillAfter(true);
                animationSet4.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                animationSet4.addAnimation(alphaAnimation6);
                animationSet4.addAnimation(translateAnimation);
                animationSet4.setFillAfter(true);
                view.startAnimation(animationSet4);
                break;
            case 205:
                view.setVisibility(0);
                AnimationSet animationSet5 = new AnimationSet(true);
                AlphaAnimation alphaAnimation7 = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation7.setDuration(500L);
                alphaAnimation7.setFillAfter(true);
                animationSet5.addAnimation(alphaAnimation7);
                animationSet5.setFillAfter(true);
                view.startAnimation(animationSet5);
                break;
            case 207:
                view.setVisibility(0);
                AnimationSet animationSet6 = new AnimationSet(true);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.1f, 1, 0.0f);
                translateAnimation2.setDuration(500L);
                AlphaAnimation alphaAnimation8 = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation8.setDuration(330L);
                alphaAnimation8.setFillAfter(true);
                animationSet6.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                animationSet6.addAnimation(translateAnimation2);
                animationSet6.addAnimation(alphaAnimation8);
                animationSet6.setFillAfter(true);
                view.startAnimation(animationSet6);
                break;
            case 208:
                AnimationSet animationSet7 = new AnimationSet(true);
                TranslateAnimation translateAnimation3 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 0.1f);
                translateAnimation3.setDuration(500L);
                AlphaAnimation alphaAnimation9 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation9.setDuration(330L);
                alphaAnimation9.setFillAfter(true);
                animationSet7.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f));
                animationSet7.addAnimation(alphaAnimation9);
                animationSet7.addAnimation(translateAnimation3);
                animationSet7.setFillAfter(true);
                view.startAnimation(animationSet7);
                view.setVisibility(4);
                break;
            case 209:
                view.setVisibility(0);
                AnimationSet animationSet8 = new AnimationSet(true);
                AlphaAnimation alphaAnimation10 = new AlphaAnimation(0.0f, 1.0f);
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
                AlphaAnimation alphaAnimation11 = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation11.setDuration(0L);
                alphaAnimation11.setFillAfter(true);
                view.startAnimation(alphaAnimation11);
                view.setVisibility(4);
                break;
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
                this.mBackHandler.sendEmptyMessageDelayed(0, 2000L);
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
                SALoggingHelper.insertEventLogging();
                if (!this.mIsButtonClicked) {
                    this.mIsButtonClicked = true;
                    this.mIsReCreated = true;
                    this.mIsFinishRegistration = true;
                    recreate();
                    break;
                }
                break;
            case R.id.done2_button /* 2131361956 */:
            case R.id.done_button /* 2131361957 */:
            case R.id.next_text_btn /* 2131362090 */:
                if (!this.mIsRotateGuideShow) {
                    SALoggingHelper.insertEventLogging(8236, this.mEnrolledCount);
                    if (!this.mIsButtonClicked) {
                        this.mIsButtonClicked = true;
                        if (!this.mIsSupportSwipeEnroll || !this.mFingerprintManager.hasEnrolledFingerprints(this.mUserId)) {
                            startViewAnimation(this.mRegisterScreen, 201);
                            finishRegistration();
                            break;
                        } else {
                            showGuideScreen(302);
                            break;
                        }
                    }
                } else {
                    hideGuideScreen(301);
                    break;
                }
                break;
            case R.id.register_button /* 2131362127 */:
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
                }
                showGuideScreen(303);
            } else {
                if (this.mIsSupportFoldEnroll && this.mEnrollState == enrollState2) {
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
                    }
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
        super.onConfigurationChanged(configuration);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x019b  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        boolean z;
        Button button;
        UserInfo userInfo;
        Log.d("BSS_FingerprintEnrollActivity", "onCreate");
        getWindow().addSystemFlags(524288);
        boolean z2 = Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX;
        if (z2) {
            setTitle(getString(R.string.fingerprint_tts_registration_help));
        } else {
            setTitle(" ");
        }
        super.onCreate(bundle);
        SALoggingHelper.setConfiguration(getApplication());
        this.mIsForcedPortrait = getRequestedOrientation() == 1;
        this.mIsTalkbackEnabled = Utils.isTalkBackEnabled(this);
        this.mIsUSA = "US".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            Log.e("BSS_FingerprintEnrollActivity", "intent should have extra value");
            finish();
            return;
        }
        this.mIsFromSetupWizard = intent.getBooleanExtra("fromSetupWizard", false);
        Log.secD("BSS_FingerprintEnrollActivity", "mIsFromSetupwizard : " + this.mIsFromSetupWizard);
        if (this.mIsFromSetupWizard && !this.mIsForcedPortrait) {
            setRequestedOrientation(getResources().getInteger(R.integer.sec_fingerprint_enroll_activity_suw_orientation));
        }
        if (z2) {
            setContentView(R.layout.fingerprint_display_enroll_layout);
        } else {
            setContentView(R.layout.sec_fingerprint_register_v10);
        }
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        Log.secD("BSS_FingerprintEnrollActivity", "mUserId : " + this.mUserId);
        this.mToken = intent.getByteArrayExtra("hw_auth_token");
        StringBuilder sb = new StringBuilder("mToken=");
        byte[] bArr = this.mToken;
        sb.append(bArr != null ? Arrays.toString(bArr) : "NULL");
        Log.secD("BSS_FingerprintEnrollActivity", sb.toString());
        this.mIsSkipGuideScreen = intent.getBooleanExtra("skipGuideScreen", false);
        Log.secD("BSS_FingerprintEnrollActivity", "mIsSkipGuideScreen : " + this.mIsSkipGuideScreen);
        if (bundle != null) {
            this.mIsReCreated = bundle.getBoolean("IsReCreated", false);
            this.mMaskViewToken = bundle.getBinder("MaskViewToken");
            if (this.mIsReCreated) {
                this.mIsAdditionalRegistration = true;
            }
        }
        this.mIsFromKnoxFingerprintPlus = intent.getBooleanExtra("isFromKnoxFingerprintPlus", false);
        Log.d("BSS_FingerprintEnrollActivity", "onCreate : isFromKnoxFingerprintPlus = " + this.mIsFromKnoxFingerprintPlus);
        Vibrator vibrator = (Vibrator) getSystemService(Vibrator.class);
        if (vibrator != null && vibrator.semGetSupportedVibrationType() >= 2) {
            if (!(Settings.System.getInt(getBaseContext().getContentResolver(), "VIB_FEEDBACK_MAGNITUDE", 0) <= 0)) {
                z = true;
                this.mIsVibrationSupport = z;
                if (!(UserHandle.myUserId() == 0 && (userInfo = UserManager.get(getBaseContext()).getUserInfo(UserHandle.myUserId())) != null && userInfo.isGuest())) {
                    Intent intent2 = new Intent();
                    intent2.putExtra("enrollResult", 0);
                    intent2.putExtra("hw_auth_token", this.mToken);
                    setResult(0, intent2);
                    finish();
                    return;
                }
                this.mFingerprintManager = (FingerprintManager) getSystemService(FingerprintManager.class);
                this.mPowerManager = (PowerManager) getSystemService(PowerManager.class);
                FingerprintManager fingerprintManager = this.mFingerprintManager;
                if (fingerprintManager == null) {
                    Log.secD("BSS_FingerprintEnrollActivity", "[onCreate] mFingerprintManager == null");
                    showSensorErrorDialog(0, null);
                    return;
                }
                if (this.mFingerprintManager.getEnrolledFingerprints(this.mUserId).size() >= fingerprintManager.semGetMaxEnrollmentNumber()) {
                    Log.w("BSS_FingerprintEnrollActivity", "onCreate: Max Enrolled Fingerprints");
                    finish();
                }
                boolean isHardwareDetected = this.mFingerprintManager.isHardwareDetected();
                int semGetSensorStatus = this.mFingerprintManager.semGetSensorStatus();
                this.mSensorStatus = semGetSensorStatus;
                if (!isHardwareDetected || (semGetSensorStatus != 100040 && semGetSensorStatus != 100041)) {
                    Log.e("BSS_FingerprintEnrollActivity", "[onCreate] isHardwareDetected = " + isHardwareDetected + ", requestGetSensorStatus = " + this.mSensorStatus);
                    showSensorErrorDialog(this.mSensorStatus, null);
                    return;
                }
                this.mIsSupportSwipeEnroll = this.mFingerprintManager.semHasFeature(2);
                if (!this.mIsReCreated && Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
                    this.mMaskViewToken = this.mFingerprintManager.semAddMaskView();
                }
                this.mIsRearSensor = Utils.Config.FP_FEATURE_SENSOR_IS_REAR;
                this.mIsSideSensor = Utils.Config.FP_FEATURE_SENSOR_IS_SIDE;
                this.mIsDisplaySensor = Utils.Config.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
                Log.d("BSS_FingerprintEnrollActivity", "onCreate : mIsRearSensor=" + this.mIsRearSensor + ",mIsSideSensor=" + this.mIsSideSensor + ",mIsDisplaySensor=" + this.mIsDisplaySensor + ",mIsSensorInLandscape=false");
                byte[] bArr2 = this.mToken;
                if (bArr2 == null || (bArr2.length == 1 && bArr2[0] == -1)) {
                    Log.e("BSS_FingerprintEnrollActivity", "Token is not available!!");
                    finish();
                    return;
                }
                boolean z3 = Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY;
                this.mIsSupportDualDisplay = z3;
                boolean z4 = z3 && new FingerprintEnrollSensorHelper(this).height > 0;
                this.mIsSupportFoldEnroll = z4;
                if (!z4) {
                    this.mDisplayType = getResources().getConfiguration().semDisplayDeviceType;
                }
                this.mRegisterScreen = (RelativeLayout) findViewById(R.id.register_screen);
                this.mFirstGuideScreen = (RelativeLayout) findViewById(R.id.first_guide_screen);
                if (!z2) {
                    this.mSecondGuideScreen = (RelativeLayout) findViewById(R.id.second_guide_screen);
                    this.mUnfoldGuideScreen = (LinearLayout) findViewById(R.id.unfold_guide_screen);
                }
                if (this.mIsSupportSwipeEnroll) {
                    this.mSwipeEnrollGuideScreen = (RelativeLayout) findViewById(R.id.swipe_enroll_guide_screen);
                }
                this.mFinger_ProgressView = (FingerprintProgressEffectView) findViewById(R.id.finger_view);
                this.mIsSupportSecondGuide = false;
                TextView textView = (TextView) findViewById(R.id.progress_text);
                this.mTxtViewProgress = textView;
                textView.setText(TextUtils.expandTemplate(getText(R.string.sec_fingerprint_percent_format), String.format("%d", 0)));
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
                if (z2 && (button = (Button) findViewById(R.id.register_button)) != null) {
                    button.setOnClickListener(this);
                    button.semSetButtonShapeEnabled(true);
                }
                TextView textView2 = (TextView) findViewById(R.id.register_guide_title);
                this.mGuideTitle = textView2;
                textView2.setVisibility(4);
                this.mImageViewKnoxBi = (ImageView) findViewById(R.id.image_knox_bi);
                if (this.mIsFromSetupWizard) {
                    this.mBackEnabled = false;
                    this.mNextButtonArea = (RelativeLayout) findViewById(R.id.next_button_area);
                    Button button5 = (Button) findViewById(R.id.next_text_btn);
                    this.mNextButton = button5;
                    if (button5 != null) {
                        button5.setOnClickListener(this);
                    }
                    Button button6 = (Button) findViewById(R.id.add2_button);
                    if (button6 != null) {
                        button6.setOnClickListener(this);
                    }
                }
                if (this.mIsTalkbackEnabled && this.mIsDisplaySensor) {
                    this.mIsSkipGuideScreen = false;
                    this.mIsReCreated = false;
                }
                initLayoutHeight();
                if (!this.mIsRearSensor) {
                    if (this.mIsSideSensor) {
                        requestSystemKeyEvent(26, true);
                        this.mIsBlockedPowerKey = true;
                    } else {
                        if (!((SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME", "").isEmpty() || Settings.Global.getInt(getContentResolver(), "navigation_bar_gesture_while_hidden", 0) == 0) ? false : true)) {
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
                boolean z5 = Settings.System.getInt(getContentResolver(), "fingerprint_gesture_quick", 0) != 0;
                this.mIsFingerGestureSet = z5;
                if (z5) {
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
                if (!this.mIsTalkbackEnabled || z2) {
                    fingerprintManager2.semForceCBGE();
                    this.mEnrollStartRemainTime = 2000L;
                    this.mEnrollStartTimer = new CountDownTimer() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.11
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
                            FingerprintEnrollActivity.m97$r8$lambda$CiJ1LOzUG9_rLQj6mhFReqNOBQ(FingerprintEnrollActivity.this, motionEvent);
                            return false;
                        }
                    });
                    setSensorPosition(this.mSensorRectView, false);
                    return;
                }
                return;
            }
        }
        z = false;
        this.mIsVibrationSupport = z;
        if (!(UserHandle.myUserId() == 0 && (userInfo = UserManager.get(getBaseContext()).getUserInfo(UserHandle.myUserId())) != null && userInfo.isGuest())) {
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
        FingerprintManager fingerprintManager;
        Log.d("BSS_FingerprintEnrollActivity", "onPause");
        super.onPause();
        removeErrorMessageHandler();
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
        this.mIsPauseRegistration = true;
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
        if (!this.mIsReCreated && (fingerprintManager = this.mFingerprintManager) != null && Utils.Config.FP_FEATURE_SENSOR_IS_OPTICAL) {
            fingerprintManager.semRemoveMaskView(this.mMaskViewToken);
        }
        if (this.mIsFingerGestureSet) {
            Settings.System.putInt(getContentResolver(), "fingerprint_gesture_quick", 1);
        }
        if (this.mEnrollState != EnrollState.NONE) {
            Log.d("BSS_FingerprintEnrollActivity", "cancelEnrollment");
            this.mIsPauseRegistration = true;
            this.mEnrollmentCancel.cancel();
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
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("BSS_FingerprintEnrollActivity", "onStop");
        super.onStop();
        if (this.mIsRearSensor) {
            return;
        }
        if (this.mIsSideSensor) {
            if (isSystemKeyEventRequested(26)) {
                requestSystemKeyEvent(26, false);
                this.mIsBlockedPowerKey = false;
            }
            if (isSystemKeyEventRequested(1082)) {
                requestSystemKeyEvent(1082, false);
                return;
            }
            return;
        }
        if (isSystemKeyEventRequested(3)) {
            requestSystemKeyEvent(3, false);
        }
        if (isSystemKeyEventRequested(187)) {
            requestSystemKeyEvent(187, false);
        }
        if (isSystemKeyEventRequested(1001)) {
            requestSystemKeyEvent(1001, false);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Uri guideClipURI;
        boolean z;
        this.mSurface = new Surface(surfaceTexture);
        try {
            boolean z2 = false;
            if (this.mIsFoldingGuideShow) {
                guideClipURI = getGuideClipURI(303, 1);
                z = true;
            } else {
                guideClipURI = this.mIsFirstGuideShow ? getGuideClipURI(300, 1) : this.mIsRotateGuideShow ? getGuideClipURI(301, 1) : this.mIsSwipeEnrollGuideShow ? getGuideClipURI(302, 1) : null;
                z = false;
            }
            if (guideClipURI != null) {
                MediaPlayer mediaPlayer = this.mMediaPlayer;
                if (mediaPlayer == null) {
                    MediaPlayer mediaPlayer2 = new MediaPlayer();
                    this.mMediaPlayer = mediaPlayer2;
                    mediaPlayer2.setAudioStreamType(1);
                    this.mMediaPlayer.semSetParameter(2500, 1);
                    this.mMediaPlayer.semSetParameter(35004, 1);
                } else {
                    mediaPlayer.reset();
                    z2 = true;
                }
                this.mMediaPlayer.setSurface(this.mSurface);
                this.mMediaPlayer.setDataSource(this, guideClipURI);
                this.mMediaPlayer.setLooping(z);
                if (z2) {
                    this.mMediaPlayer.prepare();
                    this.mMediaPlayer.start();
                } else {
                    this.mMediaPlayer.prepareAsync();
                }
                this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.19
                    @Override // android.media.MediaPlayer.OnPreparedListener
                    public final void onPrepared(MediaPlayer mediaPlayer3) {
                        Log.d("BSS_FingerprintEnrollActivity", "onSurfaceTextureAvailable : onPrepared");
                        if (FingerprintEnrollActivity.this.mEnrollState == EnrollState.ENROLL) {
                            Log.d("BSS_FingerprintEnrollActivity", "onPrepared : MediaPlayer.stop");
                            if (FingerprintEnrollActivity.this.mMediaPlayer != null) {
                                FingerprintEnrollActivity.this.mMediaPlayer.stop();
                                FingerprintEnrollActivity.this.mMediaPlayer.release();
                                FingerprintEnrollActivity.this.mMediaPlayer = null;
                                return;
                            }
                            return;
                        }
                        if (mediaPlayer3 == null) {
                            Log.w("BSS_FingerprintEnrollActivity", "onPrepared : mediaPlayer == null");
                            FingerprintEnrollActivity.this.setResult(0);
                            FingerprintEnrollActivity.this.finish();
                            return;
                        }
                        FingerprintEnrollActivity.this.adjustTextureViewRatio(mediaPlayer3.getVideoWidth(), mediaPlayer3.getVideoHeight());
                        mediaPlayer3.start();
                        if (FingerprintEnrollActivity.this.mIsFirstGuideShow && FingerprintEnrollActivity.this.mIsFirstGuideShowClose) {
                            Handler handler = new Handler();
                            Runnable runnable = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.19.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    if (FingerprintEnrollActivity.this.mIsFoldingGuideShow) {
                                        return;
                                    }
                                    if (FingerprintEnrollActivity.this.mEnrollState == EnrollState.NONE && !Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                        FingerprintEnrollActivity.this.startEnrollStep();
                                        return;
                                    }
                                    if (FingerprintEnrollActivity.this.mEnrollState != EnrollState.PAUSE || FingerprintEnrollActivity.this.mFingerprintManager == null) {
                                        return;
                                    }
                                    FingerprintEnrollActivity.this.mEnrollState = EnrollState.ENROLL;
                                    FingerprintEnrollActivity.this.mFingerprintManager.semResumeEnroll();
                                    if (FingerprintEnrollActivity.this.mSideSensorFrame != null) {
                                        FingerprintEnrollActivity.this.mSideSensorFrame.show();
                                    }
                                }
                            };
                            if (Utils.Config.FP_FEATURE_ENROLL_FRAME_MOVING_UX) {
                                return;
                            }
                            handler.postDelayed(runnable, FingerprintEnrollActivity.TIME_ENROLL_DELAY);
                        }
                    }
                });
                this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintEnrollActivity.20
                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public final void onCompletion(MediaPlayer mediaPlayer3) {
                        Log.d("BSS_FingerprintEnrollActivity", "onSurfaceTextureAvailable : onCompletion");
                        if (FingerprintEnrollActivity.this.mIsFirstGuideShow) {
                            FingerprintEnrollActivity.m183$$Nest$mchangeGuideVideo(FingerprintEnrollActivity.this, 300);
                        } else if (FingerprintEnrollActivity.this.mIsRotateGuideShow) {
                            FingerprintEnrollActivity.m183$$Nest$mchangeGuideVideo(FingerprintEnrollActivity.this, 301);
                        } else if (FingerprintEnrollActivity.this.mIsSwipeEnrollGuideShow) {
                            FingerprintEnrollActivity.m199$$Nest$mstartAuthentication(FingerprintEnrollActivity.this);
                        }
                    }
                });
            }
        } catch (IOException unused) {
            Log.e("BSS_FingerprintEnrollActivity", "IOException");
            finish();
        } catch (IllegalArgumentException unused2) {
            Log.e("BSS_FingerprintEnrollActivity", "IllegalArgumentException");
            finish();
        } catch (IllegalStateException unused3) {
            Log.e("BSS_FingerprintEnrollActivity", "IllegalStateException");
            finish();
        } catch (SecurityException unused4) {
            Log.e("BSS_FingerprintEnrollActivity", "SecurityException");
            finish();
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

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
