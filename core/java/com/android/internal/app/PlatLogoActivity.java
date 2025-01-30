package com.android.internal.app;

import android.animation.ObjectAnimator;
import android.animation.TimeAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.scontext.SContextConstants;
import android.p009os.Bundle;
import android.p009os.CombinedVibration;
import android.p009os.Handler;
import android.p009os.HandlerThread;
import android.p009os.Message;
import android.p009os.VibrationEffect;
import android.p009os.VibratorManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.C4337R;
import java.util.Random;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PlatLogoActivity extends Activity {
    private static final boolean FINISH_AFTER_NEXT_STAGE_LAUNCH = false;
    private static final long LAUNCH_TIME = 5000;
    private static final float MAX_WARP = 10.0f;
    private static final float MIN_WARP = 1.0f;
    private static final String TAG = "PlatLogoActivity";
    static final String TOUCH_STATS = "touch.stats";
    private static final String U_EGG_UNLOCK_SETTING = "egg_mode_u";
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
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case 0:
                    PlatLogoActivity.this.measureTouchPressure(event);
                    PlatLogoActivity.this.startWarp();
                    break;
                case 1:
                case 3:
                    PlatLogoActivity.this.stopWarp();
                    break;
            }
            return true;
        }
    };
    private final Runnable mLaunchNextStage = new Runnable() { // from class: com.android.internal.app.PlatLogoActivity$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            PlatLogoActivity.this.lambda$new$0();
        }
    };
    private final TimeAnimator.TimeListener mTimeListener = new TimeAnimator.TimeListener() { // from class: com.android.internal.app.PlatLogoActivity.2
        @Override // android.animation.TimeAnimator.TimeListener
        public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
            PlatLogoActivity.this.mStarfield.update(deltaTime);
            float warpFrac = (PlatLogoActivity.this.mStarfield.getWarp() - 1.0f) / 9.0f;
            if (PlatLogoActivity.this.mAnimationsEnabled) {
                PlatLogoActivity.this.mLogo.setTranslationX(PlatLogoActivity.this.mRandom.nextFloat() * warpFrac * 5.0f * PlatLogoActivity.this.mDp);
                PlatLogoActivity.this.mLogo.setTranslationY(PlatLogoActivity.this.mRandom.nextFloat() * warpFrac * 5.0f * PlatLogoActivity.this.mDp);
            }
            if (warpFrac > 0.0f) {
                PlatLogoActivity.this.mRumble.rumble(warpFrac);
            }
            PlatLogoActivity.this.mLayout.postInvalidate();
        }
    };
    double mPressureMin = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    double mPressureMax = -1.0d;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
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
        public boolean handleMessage(Message msg) {
            float warpFrac = msg.arg1 / 100.0f;
            if (!this.mSpinPrimitiveSupported) {
                if (PlatLogoActivity.this.mRandom.nextFloat() < warpFrac) {
                    PlatLogoActivity.this.mLogo.performHapticFeedback(4);
                    return false;
                }
                return false;
            }
            if (msg.getWhen() > this.mLastVibe + 50) {
                this.mLastVibe = msg.getWhen();
                this.mVibeMan.vibrate(CombinedVibration.createParallel(VibrationEffect.startComposition().addPrimitive(3, (float) Math.pow(warpFrac, 3.0d)).compose()));
                return false;
            }
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
        public void rumble(float warpFrac) {
            if (this.mVibeThread.isAlive()) {
                Message msg = Message.obtain();
                msg.what = MSG;
                msg.arg1 = (int) (100.0f * warpFrac);
                this.mVibeHandler.removeMessages(MSG);
                this.mVibeHandler.sendMessage(msg);
            }
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mRumble.destroy();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDecorFitsSystemWindows(false);
        getWindow().setNavigationBarColor(0);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().getWindowInsetsController().hide(WindowInsets.Type.systemBars());
        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.hide();
        }
        try {
            this.mAnimationsEnabled = Settings.Global.getFloat(getContentResolver(), "animator_duration_scale") > 0.0f;
        } catch (Settings.SettingNotFoundException e) {
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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float f = dm.density;
        int minSide = Math.min(dm.widthPixels, dm.heightPixels);
        int widgetSize = (int) (minSide * 0.75d);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(widgetSize, widgetSize);
        lp.gravity = 17;
        ImageView imageView = new ImageView(this);
        this.mLogo = imageView;
        imageView.setImageResource(C4337R.drawable.platlogo);
        this.mLogo.setOnTouchListener(this.mTouchListener);
        this.mLogo.requestFocus();
        this.mLayout.addView(this.mLogo, lp);
        Log.m100v(TAG, "Hello");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 62) {
            if (event.getRepeatCount() == 0) {
                startWarp();
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 62) {
            stopWarp();
            return true;
        }
        return false;
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

    private void launchNextStage(boolean locked) {
        ContentResolver cr = getContentResolver();
        try {
            if (shouldWriteSettings()) {
                Log.m100v(TAG, "Saving egg locked=" + locked);
                syncTouchPressure();
                Settings.System.putLong(cr, U_EGG_UNLOCK_SETTING, locked ? 0L : System.currentTimeMillis());
            }
        } catch (RuntimeException e) {
            Log.m97e(TAG, "Can't write settings", e);
        }
        try {
            Intent eggActivity = new Intent(Intent.ACTION_MAIN).setFlags(268468224).addCategory("com.android.internal.category.PLATLOGO");
            Log.m100v(TAG, "launching: " + eggActivity);
            startActivity(eggActivity);
        } catch (ActivityNotFoundException e2) {
            Log.m96e("com.android.internal.app.PlatLogoActivity", "No more eggs.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void measureTouchPressure(MotionEvent event) {
        float pressure = event.getPressure();
        switch (event.getActionMasked()) {
            case 0:
                if (this.mPressureMax < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                    double d = pressure;
                    this.mPressureMax = d;
                    this.mPressureMin = d;
                    break;
                }
                break;
            case 2:
                if (pressure < this.mPressureMin) {
                    this.mPressureMin = pressure;
                }
                if (pressure > this.mPressureMax) {
                    this.mPressureMax = pressure;
                    break;
                }
                break;
        }
    }

    private void syncTouchPressure() {
        try {
            String touchDataJson = Settings.System.getString(getContentResolver(), TOUCH_STATS);
            JSONObject touchData = new JSONObject(touchDataJson != null ? touchDataJson : "{}");
            if (touchData.has("min")) {
                this.mPressureMin = Math.min(this.mPressureMin, touchData.getDouble("min"));
            }
            if (touchData.has("max")) {
                this.mPressureMax = Math.max(this.mPressureMax, touchData.getDouble("max"));
            }
            if (this.mPressureMax >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                touchData.put("min", this.mPressureMin);
                touchData.put("max", this.mPressureMax);
                if (shouldWriteSettings()) {
                    Settings.System.putString(getContentResolver(), TOUCH_STATS, touchData.toString());
                }
            }
        } catch (Exception e) {
            Log.m97e("com.android.internal.app.PlatLogoActivity", "Can't write touch settings", e);
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        syncTouchPressure();
    }

    @Override // android.app.Activity
    public void onStop() {
        syncTouchPressure();
        super.onStop();
    }

    private static class Starfield extends Drawable {
        private static final int NUM_PLANES = 2;
        private static final int NUM_STARS = 34;
        private float mBuffer;
        private final Random mRng;
        private final float mSize;
        private final Paint mStarPaint;
        private float mVx;
        private float mVy;
        private final float[] mStars = new float[136];
        private long mDt = 0;
        private final Rect mSpace = new Rect();
        private float mWarp = 1.0f;

        public void setWarp(float warp) {
            this.mWarp = warp;
        }

        public float getWarp() {
            return this.mWarp;
        }

        Starfield(Random rng, float size) {
            this.mRng = rng;
            this.mSize = size;
            Paint paint = new Paint();
            this.mStarPaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(-1);
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(Rect bounds) {
            this.mSpace.set(bounds);
            float f = this.mSize * 2.0f * 2.0f * PlatLogoActivity.MAX_WARP;
            this.mBuffer = f;
            this.mSpace.inset(-((int) f), -((int) f));
            float w = this.mSpace.width();
            float h = this.mSpace.height();
            for (int i = 0; i < 34; i++) {
                this.mStars[i * 4] = this.mRng.nextFloat() * w;
                this.mStars[(i * 4) + 1] = this.mRng.nextFloat() * h;
                float[] fArr = this.mStars;
                fArr[(i * 4) + 2] = fArr[i * 4];
                fArr[(i * 4) + 3] = fArr[(i * 4) + 1];
            }
        }

        public void setVelocity(float x, float y) {
            this.mVx = x;
            this.mVy = y;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            float dtSec = this.mDt / 1000.0f;
            float f = this.mVx * dtSec;
            float f2 = this.mWarp;
            float dx = f * f2;
            float dy = this.mVy * dtSec * f2;
            int i = 0;
            int i2 = 1;
            boolean inWarp = f2 > 1.0f;
            canvas.drawColor(-16777216);
            long j = this.mDt;
            if (j > 0 && j < 1000) {
                canvas.translate((-this.mBuffer) + (this.mRng.nextFloat() * (this.mWarp - 1.0f)), (-this.mBuffer) + (this.mRng.nextFloat() * (this.mWarp - 1.0f)));
                float w = this.mSpace.width();
                float h = this.mSpace.height();
                int i3 = 0;
                while (i3 < 34) {
                    int plane = ((int) ((i3 / 34.0f) * 2.0f)) + i2;
                    float[] fArr = this.mStars;
                    fArr[(i3 * 4) + 2] = ((fArr[(i3 * 4) + 2] + (plane * dx)) + w) % w;
                    fArr[(i3 * 4) + 3] = ((fArr[(i3 * 4) + 3] + (plane * dy)) + h) % h;
                    fArr[(i3 * 4) + i] = inWarp ? fArr[(i3 * 4) + 2] - (((this.mWarp * dx) * 2.0f) * plane) : -100.0f;
                    fArr[(i3 * 4) + 1] = inWarp ? fArr[(i3 * 4) + 3] - (((this.mWarp * dy) * 2.0f) * plane) : -100.0f;
                    i3++;
                    i = 0;
                    i2 = 1;
                }
            }
            int slice = ((this.mStars.length / 2) / 4) * 4;
            for (int p = 0; p < 2; p++) {
                this.mStarPaint.setStrokeWidth(this.mSize * (p + 1));
                if (inWarp) {
                    canvas.drawLines(this.mStars, p * slice, slice, this.mStarPaint);
                }
                canvas.drawPoints(this.mStars, p * slice, slice, this.mStarPaint);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

        public void update(long dt) {
            this.mDt = dt;
        }
    }
}
