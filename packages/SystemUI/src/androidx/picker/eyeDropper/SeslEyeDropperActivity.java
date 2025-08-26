package androidx.picker.eyeDropper;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.KeyguardManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.picker3.app.SeslColorPickerDialog;
import androidx.picker3.app.SeslColorPickerDialog$$ExternalSyntheticLambda1;
import androidx.picker3.widget.SeslColorPicker;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class SeslEyeDropperActivity extends AppCompatActivity {
    public static SeslColorPickerDialog$$ExternalSyntheticLambda1 mOnColorPickListener;
    public ImageView mBitmapView;
    public int mCurrentPixelColor;
    public Bitmap mImageBitmap;
    public SeslMagnifyingView mMagnifyingView;
    public View mPointerView;

    @Override // android.app.Activity
    public final void finishAfterTransition() {
        super.finishAfterTransition();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        finishAfterTransition();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            keyguardManager.requestDismissKeyguard(this, null);
        }
        getWindow().setFlags(512, 512);
        setContentView(com.android.systemui.R.layout.activity_eye_dropper);
        ImageView imageView = (ImageView) findViewById(com.android.systemui.R.id.screenshotView);
        this.mBitmapView = imageView;
        imageView.setImportantForAccessibility(2);
        this.mMagnifyingView = (SeslMagnifyingView) findViewById(com.android.systemui.R.id.magnifierView);
        this.mPointerView = findViewById(com.android.systemui.R.id.pointerView);
        this.mBitmapView.post(new Runnable() { // from class: androidx.picker.eyeDropper.SeslEyeDropperActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Bitmap bitmapCreateScaledBitmap;
                final SeslEyeDropperActivity seslEyeDropperActivity = this.f$0;
                WeakReference weakReference = SeslBitmapHolder.sBitmapWeakReference;
                if (weakReference != null) {
                    SeslColorPickerDialog$$ExternalSyntheticLambda1 seslColorPickerDialog$$ExternalSyntheticLambda1 = SeslEyeDropperActivity.mOnColorPickListener;
                    bitmapCreateScaledBitmap = (Bitmap) weakReference.get();
                } else {
                    bitmapCreateScaledBitmap = null;
                }
                int width = seslEyeDropperActivity.mBitmapView.getWidth();
                int height = seslEyeDropperActivity.mBitmapView.getHeight();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                canvas.drawColor(-16777216);
                if (bitmapCreateScaledBitmap != null) {
                    float fMin = Math.min(width / bitmapCreateScaledBitmap.getWidth(), height / bitmapCreateScaledBitmap.getHeight());
                    if (bitmapCreateScaledBitmap.getWidth() > width || bitmapCreateScaledBitmap.getHeight() > height) {
                        bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateScaledBitmap, (int) (bitmapCreateScaledBitmap.getWidth() * fMin), (int) (bitmapCreateScaledBitmap.getHeight() * fMin), false);
                    }
                    canvas.drawBitmap(bitmapCreateScaledBitmap, (width - bitmapCreateScaledBitmap.getWidth()) / 2, (height - bitmapCreateScaledBitmap.getHeight()) / 2, (Paint) null);
                }
                seslEyeDropperActivity.mImageBitmap = bitmapCreateBitmap;
                final int width2 = bitmapCreateBitmap.getWidth() / 2;
                final int height2 = seslEyeDropperActivity.mImageBitmap.getHeight() / 2;
                seslEyeDropperActivity.mCurrentPixelColor = seslEyeDropperActivity.mImageBitmap.getPixel(width2, height2);
                seslEyeDropperActivity.mBitmapView.post(new Runnable() { // from class: androidx.picker.eyeDropper.SeslEyeDropperActivity$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SeslEyeDropperActivity seslEyeDropperActivity2 = seslEyeDropperActivity;
                        int i = width2;
                        int i2 = height2;
                        seslEyeDropperActivity2.mBitmapView.setImageBitmap(seslEyeDropperActivity2.mImageBitmap);
                        SeslMagnifyingView seslMagnifyingView = seslEyeDropperActivity2.mMagnifyingView;
                        Bitmap bitmap = seslEyeDropperActivity2.mImageBitmap;
                        seslEyeDropperActivity2.mPointerView.getWidth();
                        seslEyeDropperActivity2.mPointerView.getHeight();
                        int i3 = seslEyeDropperActivity2.mCurrentPixelColor;
                        seslMagnifyingView.mScreenShotBitmap = bitmap;
                        seslMagnifyingView.mTouchPosX = i;
                        seslMagnifyingView.mTouchPosY = i2;
                        seslMagnifyingView.mColorBorderColor = i3;
                        seslMagnifyingView.invalidate();
                        seslEyeDropperActivity2.positionMagnifierAndPointer(i, i2, seslEyeDropperActivity2.mImageBitmap.getPixel(i, i2));
                    }
                });
            }
        });
        this.mBitmapView.setClickable(false);
        this.mBitmapView.setEnabled(false);
        int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_eyedropper_y_animation_offset);
        PathInterpolator pathInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mPointerView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mPointerView, "scaleY", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.mMagnifyingView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.mMagnifyingView, "scaleY", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(this.mPointerView, "translationY", 0.0f, dimensionPixelSize);
        objectAnimatorOfFloat3.setDuration(400L).setInterpolator(pathInterpolator);
        objectAnimatorOfFloat4.setDuration(400L).setInterpolator(pathInterpolator);
        objectAnimatorOfFloat.setDuration(400L).setInterpolator(pathInterpolator);
        objectAnimatorOfFloat2.setDuration(400L).setInterpolator(pathInterpolator);
        objectAnimatorOfFloat5.setDuration(400L).setInterpolator(pathInterpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat5);
        this.mPointerView.setVisibility(0);
        this.mMagnifyingView.setVisibility(0);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: androidx.picker.eyeDropper.SeslEyeDropperActivity.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SeslEyeDropperActivity.this.mBitmapView.setClickable(true);
                SeslEyeDropperActivity.this.mBitmapView.setEnabled(true);
            }
        });
        animatorSet.start();
        this.mBitmapView.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.picker.eyeDropper.SeslEyeDropperActivity$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:16:0x0050, code lost:
            
                if (r8 != 3) goto L24;
             */
            /* JADX WARN: Removed duplicated region for block: B:23:0x0076  */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SeslEyeDropperActivity seslEyeDropperActivity = this.f$0;
                SeslColorPickerDialog$$ExternalSyntheticLambda1 seslColorPickerDialog$$ExternalSyntheticLambda1 = SeslEyeDropperActivity.mOnColorPickListener;
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (x >= 0 && x < seslEyeDropperActivity.mImageBitmap.getWidth()) {
                    float f = y;
                    if (f > seslEyeDropperActivity.mPointerView.getHeight() / 2.0f && f < seslEyeDropperActivity.mImageBitmap.getHeight() - (seslEyeDropperActivity.mPointerView.getHeight() / 2.0f)) {
                        seslEyeDropperActivity.mCurrentPixelColor = seslEyeDropperActivity.mImageBitmap.getPixel(x, y);
                        int actionMasked = motionEvent.getActionMasked();
                        if (actionMasked != 0) {
                            if (actionMasked != 1) {
                                if (actionMasked != 2) {
                                }
                            }
                            SeslColorPickerDialog$$ExternalSyntheticLambda1 seslColorPickerDialog$$ExternalSyntheticLambda12 = SeslEyeDropperActivity.mOnColorPickListener;
                            if (seslColorPickerDialog$$ExternalSyntheticLambda12 != null) {
                                int i = seslEyeDropperActivity.mCurrentPixelColor;
                                int i2 = SeslColorPickerDialog.$r8$clinit;
                                SeslColorPickerDialog seslColorPickerDialog = seslColorPickerDialog$$ExternalSyntheticLambda12.f$0;
                                SeslBitmapHolder.sBitmapWeakReference = null;
                                seslColorPickerDialog.show();
                                Integer numValueOf = Integer.valueOf(i);
                                seslColorPickerDialog.mCurrentColor = numValueOf;
                                SeslColorPicker seslColorPicker = seslColorPickerDialog.mColorPicker;
                                seslColorPicker.mRecentColorInfo.mNewColor = numValueOf;
                                seslColorPicker.updateRecentColorLayout();
                            }
                            seslEyeDropperActivity.finishAfterTransition();
                            return true;
                        }
                        seslEyeDropperActivity.positionMagnifierAndPointer(x, y, seslEyeDropperActivity.mCurrentPixelColor);
                    }
                }
                return true;
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        mOnColorPickListener = null;
        super.onDestroy();
    }

    public final void positionMagnifierAndPointer(int i, int i2, int i3) {
        SeslMagnifyingView seslMagnifyingView = this.mMagnifyingView;
        float f = i;
        float f2 = i2;
        seslMagnifyingView.mTouchPosX = f;
        seslMagnifyingView.mTouchPosY = f2;
        seslMagnifyingView.mColorBorderColor = i3;
        seslMagnifyingView.invalidate();
        if (i2 <= this.mImageBitmap.getHeight() * 0.2d) {
            this.mMagnifyingView.setY((this.mPointerView.getHeight() / 2.0f) + f2 + getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_eyedropper_y_offset));
        } else {
            this.mMagnifyingView.setY(f2 - (((this.mPointerView.getHeight() / 2.0f) + this.mMagnifyingView.getHeight()) + getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_eyedropper_y_offset)));
        }
        this.mMagnifyingView.setX(f - (r8.getWidth() / 2.0f));
        this.mPointerView.setX(f - (r8.getWidth() / 2.0f));
        this.mPointerView.setY(f2 - (r6.getHeight() / 2.0f));
    }
}
