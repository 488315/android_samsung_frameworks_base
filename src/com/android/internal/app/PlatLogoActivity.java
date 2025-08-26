package com.android.internal.app;

import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.scontext.SContextConstants;
import android.media.quality.ParameterCapability;
import android.os.Bundle;
import android.os.CombinedVibration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.VibratorManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.R;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PlatLogoActivity extends Activity {
    private static final String EGG_UNLOCK_SETTING = "egg_mode_v";
    private static final boolean FINISH_AFTER_NEXT_STAGE_LAUNCH = false;
    private static final long LAUNCH_TIME = 5000;
    private static final float MAX_WARP = 16.0f;
    private static final float MIN_WARP = 1.0f;
    private static final String TAG = "PlatLogoActivity";
    static final String TOUCH_STATS = "touch.stats";
    private TimeAnimator mAnim;
    private float mDp;
    private FrameLayout mLayout;
    private ImageView mLogo;
    private Random mRandom;
    private RumblePack mRumble;
    private Starfield mStarfield;
    private ObjectAnimator mWarpAnim;
    private boolean mAnimationsEnabled = true;
    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() { // from class: com.android.internal.app.PlatLogoActivity.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                PlatLogoActivity.this.measureTouchPressure(motionEvent);
                PlatLogoActivity.this.startWarp();
            } else if (actionMasked == 1 || actionMasked == 3) {
                PlatLogoActivity.this.stopWarp();
            }
            return true;
        }
    };
    private final Runnable mLaunchNextStage = new Runnable() { // from class: com.android.internal.app.PlatLogoActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() throws JSONException {
            this.f$0.lambda$new$0();
        }
    };
    private final TimeAnimator.TimeListener mTimeListener = new TimeAnimator.TimeListener() { // from class: com.android.internal.app.PlatLogoActivity.2
        @Override // android.animation.TimeAnimator.TimeListener
        public void onTimeUpdate(TimeAnimator timeAnimator, long j, long j2) {
            PlatLogoActivity.this.mStarfield.update(j2);
            float warp = (PlatLogoActivity.this.mStarfield.getWarp() - 1.0f) / 15.0f;
            if (PlatLogoActivity.this.mAnimationsEnabled) {
                PlatLogoActivity.this.mLogo.setTranslationX(PlatLogoActivity.this.mRandom.nextFloat() * warp * 5.0f * PlatLogoActivity.this.mDp);
                PlatLogoActivity.this.mLogo.setTranslationY(PlatLogoActivity.this.mRandom.nextFloat() * warp * 5.0f * PlatLogoActivity.this.mDp);
            }
            if (warp > 0.0f) {
                PlatLogoActivity.this.mRumble.rumble(warp);
            }
            PlatLogoActivity.this.mLayout.postInvalidate();
        }
    };
    double mPressureMin = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    double mPressureMax = -1.0d;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() throws JSONException {
        stopWarp();
        launchNextStage(false);
    }

    private class RumblePack implements Handler.Callback {
        private static final int INTERVAL = 50;
        private static final int MSG = 6464;
        private long mLastVibe = 0;
        private boolean mSpinPrimitiveSupported;
        private final Handler mVibeHandler;
        private final VibratorManager mVibeMan;
        private final HandlerThread mVibeThread;

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            float f = message.arg1 / 100.0f;
            if (!this.mSpinPrimitiveSupported) {
                if (PlatLogoActivity.this.mRandom.nextFloat() >= f) {
                    return false;
                }
                PlatLogoActivity.this.mLogo.performHapticFeedback(4);
                return false;
            }
            if (message.getWhen() <= this.mLastVibe + 50) {
                return false;
            }
            this.mLastVibe = message.getWhen();
            this.mVibeMan.vibrate(CombinedVibration.createParallel(VibrationEffect.startComposition().addPrimitive(3, (float) Math.pow(f, 3.0d)).compose()));
            return false;
        }

        RumblePack() {
            VibratorManager vibratorManager = (VibratorManager) PlatLogoActivity.this.getSystemService(VibratorManager.class);
            this.mVibeMan = vibratorManager;
            this.mSpinPrimitiveSupported = vibratorManager.getDefaultVibrator().areAllPrimitivesSupported(3);
            HandlerThread handlerThread = new HandlerThread("VibratorThread");
            this.mVibeThread = handlerThread;
            handlerThread.start();
            this.mVibeHandler = Handler.createAsync(handlerThread.getLooper(), this);
        }

        public void destroy() {
            this.mVibeThread.quit();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void rumble(float f) {
            if (this.mVibeThread.isAlive()) {
                Message messageObtain = Message.obtain();
                messageObtain.what = MSG;
                messageObtain.arg1 = (int) (f * 100.0f);
                this.mVibeHandler.removeMessages(MSG);
                this.mVibeHandler.sendMessage(messageObtain);
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mRumble.destroy();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setDecorFitsSystemWindows(false);
        getWindow().setNavigationBarColor(0);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().getWindowInsetsController().hide(WindowInsets.Type.systemBars());
        getWindow().setColorMode(2);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        try {
            this.mAnimationsEnabled = Settings.Global.getFloat(getContentResolver(), "animator_duration_scale") > 0.0f;
        } catch (Settings.SettingNotFoundException unused) {
            this.mAnimationsEnabled = true;
        }
        this.mRumble = new RumblePack();
        this.mLayout = new FrameLayout(this);
        this.mRandom = new Random();
        this.mDp = getResources().getDisplayMetrics().density;
        Starfield starfield = new Starfield(this.mRandom, this.mDp * 2.0f);
        this.mStarfield = starfield;
        starfield.setVelocity((this.mRandom.nextFloat() - 0.5f) * 200.0f, (this.mRandom.nextFloat() - 0.5f) * 200.0f);
        this.mLayout.setBackground(this.mStarfield);
        float f = getResources().getDisplayMetrics().density;
        int iMin = (int) (Math.min(r5.widthPixels, r5.heightPixels) * 0.75d);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iMin, iMin);
        layoutParams.gravity = 17;
        ImageView imageView = new ImageView(this);
        this.mLogo = imageView;
        imageView.setImageResource(R.drawable.platlogo);
        this.mLogo.setOnTouchListener(this.mTouchListener);
        this.mLogo.requestFocus();
        this.mLayout.addView(this.mLogo, layoutParams);
        Log.v(TAG, "Hello");
        setContentView(this.mLayout);
    }

    private void startAnimating() {
        TimeAnimator timeAnimator = new TimeAnimator();
        this.mAnim = timeAnimator;
        timeAnimator.setTimeListener(this.mTimeListener);
        this.mAnim.start();
    }

    private void stopAnimating() {
        this.mAnim.cancel();
        this.mAnim = null;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 62) {
            if (keyEvent.getRepeatCount() != 0) {
                return true;
            }
            startWarp();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 62) {
            stopWarp();
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWarp() {
        stopWarp();
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.mStarfield, "warp", 1.0f, MAX_WARP).setDuration(5000L);
        this.mWarpAnim = duration;
        duration.start();
        this.mLogo.postDelayed(this.mLaunchNextStage, 6000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopWarp() {
        ObjectAnimator objectAnimator = this.mWarpAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mWarpAnim.removeAllListeners();
            this.mWarpAnim = null;
        }
        this.mStarfield.setWarp(1.0f);
        this.mLogo.removeCallbacks(this.mLaunchNextStage);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        startAnimating();
    }

    @Override // android.app.Activity
    public void onPause() {
        stopWarp();
        stopAnimating();
        super.onPause();
    }

    private boolean shouldWriteSettings() {
        return getPackageName().equals("android");
    }

    private void launchNextStage(boolean z) throws JSONException {
        ContentResolver contentResolver = getContentResolver();
        try {
            if (shouldWriteSettings()) {
                Log.v(TAG, "Saving egg locked=" + z);
                syncTouchPressure();
                Settings.System.putLong(contentResolver, EGG_UNLOCK_SETTING, z ? 0L : System.currentTimeMillis());
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Can't write settings", e);
        }
        try {
            Intent intentAddCategory = new Intent(Intent.ACTION_MAIN).setFlags(268468224).addCategory("com.android.internal.category.PLATLOGO");
            Log.v(TAG, "launching: " + intentAddCategory);
            startActivity(intentAddCategory);
        } catch (ActivityNotFoundException unused) {
            Log.e("com.android.internal.app.PlatLogoActivity", "No more eggs.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void measureTouchPressure(MotionEvent motionEvent) {
        float pressure = motionEvent.getPressure();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (this.mPressureMax < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                double d = pressure;
                this.mPressureMax = d;
                this.mPressureMin = d;
                return;
            }
            return;
        }
        if (actionMasked != 2) {
            return;
        }
        double d2 = pressure;
        if (d2 < this.mPressureMin) {
            this.mPressureMin = d2;
        }
        if (d2 > this.mPressureMax) {
            this.mPressureMax = d2;
        }
    }

    private void syncTouchPressure() throws JSONException {
        try {
            String string = Settings.System.getString(getContentResolver(), TOUCH_STATS);
            if (string == null) {
                string = "{}";
            }
            JSONObject jSONObject = new JSONObject(string);
            if (jSONObject.has(ParameterCapability.CAPABILITY_MIN)) {
                this.mPressureMin = Math.min(this.mPressureMin, jSONObject.getDouble(ParameterCapability.CAPABILITY_MIN));
            }
            if (jSONObject.has("max")) {
                this.mPressureMax = Math.max(this.mPressureMax, jSONObject.getDouble("max"));
            }
            if (this.mPressureMax >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                jSONObject.put(ParameterCapability.CAPABILITY_MIN, this.mPressureMin);
                jSONObject.put("max", this.mPressureMax);
                if (shouldWriteSettings()) {
                    Settings.System.putString(getContentResolver(), TOUCH_STATS, jSONObject.toString());
                }
            }
        } catch (Exception e) {
            Log.e("com.android.internal.app.PlatLogoActivity", "Can't write touch settings", e);
        }
    }

    @Override // android.app.Activity
    public void onStart() throws JSONException {
        super.onStart();
        syncTouchPressure();
    }

    @Override // android.app.Activity
    public void onStop() throws JSONException {
        syncTouchPressure();
        super.onStop();
    }

    private static class Starfield extends Drawable {
        private static final int NUM_PLANES = 4;
        private static final int NUM_STARS = 128;
        private static final float ROTATION = 45.0f;
        private static final ColorSpace sSrgbExt = ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB);
        private float mBuffer;
        private final Random mRng;
        private final float mSize;
        private final Paint mStarPaint;
        private float mVx;
        private float mVy;
        private final float[] mStars = new float[512];
        private long mDt = 0;
        private float mRadius = 0.0f;
        private float mWarp = 1.0f;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public void setWarp(float f) {
            this.mWarp = f;
        }

        public float getWarp() {
            return this.mWarp;
        }

        Starfield(Random random, float f) {
            this.mRng = random;
            this.mSize = f;
            Paint paint = new Paint();
            this.mStarPaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(-1);
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(Rect rect) {
            this.mBuffer = this.mSize * 4.0f * 2.0f * PlatLogoActivity.MAX_WARP;
            this.mRadius = (((float) Math.hypot(rect.width(), rect.height())) / 2.0f) + this.mBuffer;
            for (int i = 0; i < 128; i++) {
                float[] fArr = this.mStars;
                int i2 = i * 4;
                float fNextFloat = this.mRng.nextFloat() * 2.0f;
                float f = this.mRadius;
                fArr[i2] = (fNextFloat * f) - f;
                float[] fArr2 = this.mStars;
                int i3 = i2 + 1;
                float fNextFloat2 = this.mRng.nextFloat() * 2.0f;
                float f2 = this.mRadius;
                fArr2[i3] = (fNextFloat2 * f2) - f2;
                float[] fArr3 = this.mStars;
                fArr3[i2 + 2] = fArr3[i2];
                fArr3[i2 + 3] = fArr3[i3];
            }
        }

        public void setVelocity(float f, float f2) {
            this.mVx = f;
            this.mVy = f2;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            float f = this.mDt / 1000.0f;
            float f2 = this.mVx * f;
            float f3 = this.mWarp;
            float f4 = f2 * f3;
            float f5 = this.mVy * f * f3;
            int i = 1;
            boolean z = f3 > 1.0f;
            float f6 = this.mRadius;
            float f7 = f6 * 2.0f;
            float f8 = f6 * 3.0f;
            canvas.drawColor(-16777216);
            canvas.translate(getBounds().width() / 2.0f, getBounds().height() / 2.0f);
            canvas.rotate(ROTATION);
            long j = this.mDt;
            if (j > 0 && j < 1000) {
                canvas.translate(this.mRng.nextFloat() * (this.mWarp - 1.0f), this.mRng.nextFloat() * (this.mWarp - 1.0f));
                int i2 = 0;
                while (i2 < 128) {
                    float[] fArr = this.mStars;
                    int i3 = i2 * 4;
                    int i4 = i3 + 2;
                    float f9 = ((int) ((i2 / 128.0f) * 4.0f)) + i;
                    float f10 = ((fArr[i4] + (f4 * f9)) + f8) % f7;
                    float f11 = this.mRadius;
                    fArr[i4] = f10 - f11;
                    int i5 = i3 + 3;
                    fArr[i5] = (((fArr[i5] + (f5 * f9)) + f8) % f7) - f11;
                    fArr[i3] = z ? fArr[i4] - ((this.mWarp * f4) * f9) : -10000.0f;
                    fArr[i3 + 1] = z ? fArr[i5] - ((this.mWarp * f5) * f9) : -10000.0f;
                    i2++;
                    i = 1;
                }
            }
            int length = ((this.mStars.length / 4) / 4) * 4;
            int i6 = 0;
            while (i6 < 4) {
                this.mStarPaint.setColor(packHdrColor((i6 + 1.0f) / 3.0f, 1.0f));
                int i7 = i6 + 1;
                this.mStarPaint.setStrokeWidth(this.mSize * i7);
                if (z) {
                    canvas.drawLines(this.mStars, i6 * length, length, this.mStarPaint);
                }
                canvas.drawPoints(this.mStars, i6 * length, length, this.mStarPaint);
                i6 = i7;
            }
            if (z) {
                float f12 = (this.mWarp - 1.0f) / 15.0f;
                canvas.drawColor(packHdrColor(2.0f, f12 * f12));
            }
        }

        public void update(long j) {
            this.mDt = j;
        }

        public static long packHdrColor(float f, float f2) {
            return Color.valueOf(f, f, f, f2, sSrgbExt).pack();
        }
    }
}
