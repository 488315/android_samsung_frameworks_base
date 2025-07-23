package androidx.picker.eyeDropper;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.KeyguardManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.picker3.app.SeslColorPickerDialog$$ExternalSyntheticLambda1;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final void onCreate(Bundle bundle) {
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
                Bitmap bitmap;
                final SeslEyeDropperActivity seslEyeDropperActivity = SeslEyeDropperActivity.this;
                WeakReference weakReference = SeslBitmapHolder.sBitmapWeakReference;
                if (weakReference != null) {
                    SeslColorPickerDialog$$ExternalSyntheticLambda1 seslColorPickerDialog$$ExternalSyntheticLambda1 = SeslEyeDropperActivity.mOnColorPickListener;
                    bitmap = (Bitmap) weakReference.get();
                } else {
                    bitmap = null;
                }
                int width = seslEyeDropperActivity.mBitmapView.getWidth();
                int height = seslEyeDropperActivity.mBitmapView.getHeight();
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawColor(-16777216);
                if (bitmap != null) {
                    float min = Math.min(width / bitmap.getWidth(), height / bitmap.getHeight());
                    if (bitmap.getWidth() > width || bitmap.getHeight() > height) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * min), (int) (bitmap.getHeight() * min), false);
                    }
                    canvas.drawBitmap(bitmap, (width - bitmap.getWidth()) / 2, (height - bitmap.getHeight()) / 2, (Paint) null);
                }
                seslEyeDropperActivity.mImageBitmap = createBitmap;
                final int width2 = createBitmap.getWidth() / 2;
                final int height2 = seslEyeDropperActivity.mImageBitmap.getHeight() / 2;
                seslEyeDropperActivity.mCurrentPixelColor = seslEyeDropperActivity.mImageBitmap.getPixel(width2, height2);
                seslEyeDropperActivity.mBitmapView.post(new Runnable() { // from class: androidx.picker.eyeDropper.SeslEyeDropperActivity$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SeslEyeDropperActivity seslEyeDropperActivity2 = SeslEyeDropperActivity.this;
                        int i = width2;
                        int i2 = height2;
                        seslEyeDropperActivity2.mBitmapView.setImageBitmap(seslEyeDropperActivity2.mImageBitmap);
                        SeslMagnifyingView seslMagnifyingView = seslEyeDropperActivity2.mMagnifyingView;
                        Bitmap bitmap2 = seslEyeDropperActivity2.mImageBitmap;
                        seslEyeDropperActivity2.mPointerView.getWidth();
                        seslEyeDropperActivity2.mPointerView.getHeight();
                        int i3 = seslEyeDropperActivity2.mCurrentPixelColor;
                        seslMagnifyingView.mScreenShotBitmap = bitmap2;
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
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mPointerView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mPointerView, "scaleY", 0.0f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mMagnifyingView, "scaleX", 0.0f, 1.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mMagnifyingView, "scaleY", 0.0f, 1.0f);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mPointerView, "translationY", 0.0f, dimensionPixelSize);
        ofFloat3.setDuration(400L).setInterpolator(pathInterpolator);
        ofFloat4.setDuration(400L).setInterpolator(pathInterpolator);
        ofFloat.setDuration(400L).setInterpolator(pathInterpolator);
        ofFloat2.setDuration(400L).setInterpolator(pathInterpolator);
        ofFloat5.setDuration(400L).setInterpolator(pathInterpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat3, ofFloat4, ofFloat, ofFloat2, ofFloat5);
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
            /* JADX WARN: Code restructure failed: missing block: B:15:0x0050, code lost:
            
                if (r8 != 3) goto L24;
             */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean onTouch(android.view.View r7, android.view.MotionEvent r8) {
                /*
                    r6 = this;
                    androidx.picker.eyeDropper.SeslEyeDropperActivity r6 = androidx.picker.eyeDropper.SeslEyeDropperActivity.this
                    androidx.picker3.app.SeslColorPickerDialog$$ExternalSyntheticLambda1 r7 = androidx.picker.eyeDropper.SeslEyeDropperActivity.mOnColorPickListener
                    float r7 = r8.getX()
                    int r7 = (int) r7
                    float r0 = r8.getY()
                    int r0 = (int) r0
                    r1 = 1
                    if (r7 < 0) goto L7b
                    android.graphics.Bitmap r2 = r6.mImageBitmap
                    int r2 = r2.getWidth()
                    if (r7 >= r2) goto L7b
                    float r2 = (float) r0
                    android.view.View r3 = r6.mPointerView
                    int r3 = r3.getHeight()
                    float r3 = (float) r3
                    r4 = 1073741824(0x40000000, float:2.0)
                    float r3 = r3 / r4
                    int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                    if (r3 <= 0) goto L7b
                    android.graphics.Bitmap r3 = r6.mImageBitmap
                    int r3 = r3.getHeight()
                    float r3 = (float) r3
                    android.view.View r5 = r6.mPointerView
                    int r5 = r5.getHeight()
                    float r5 = (float) r5
                    float r5 = r5 / r4
                    float r3 = r3 - r5
                    int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                    if (r2 >= 0) goto L7b
                    android.graphics.Bitmap r2 = r6.mImageBitmap
                    int r2 = r2.getPixel(r7, r0)
                    r6.mCurrentPixelColor = r2
                    int r8 = r8.getActionMasked()
                    if (r8 == 0) goto L76
                    if (r8 == r1) goto L53
                    r2 = 2
                    if (r8 == r2) goto L76
                    r7 = 3
                    if (r8 == r7) goto L53
                    goto L7b
                L53:
                    androidx.picker3.app.SeslColorPickerDialog$$ExternalSyntheticLambda1 r7 = androidx.picker.eyeDropper.SeslEyeDropperActivity.mOnColorPickListener
                    if (r7 == 0) goto L72
                    int r8 = r6.mCurrentPixelColor
                    int r0 = androidx.picker3.app.SeslColorPickerDialog.$r8$clinit
                    androidx.picker3.app.SeslColorPickerDialog r7 = r7.f$0
                    r0 = 0
                    androidx.picker.eyeDropper.SeslBitmapHolder.sBitmapWeakReference = r0
                    r7.show()
                    java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                    r7.mCurrentColor = r8
                    androidx.picker3.widget.SeslColorPicker r7 = r7.mColorPicker
                    androidx.picker3.widget.SeslRecentColorInfo r0 = r7.mRecentColorInfo
                    r0.mNewColor = r8
                    r7.updateRecentColorLayout()
                L72:
                    r6.finishAfterTransition()
                    return r1
                L76:
                    int r8 = r6.mCurrentPixelColor
                    r6.positionMagnifierAndPointer(r7, r0, r8)
                L7b:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.picker.eyeDropper.SeslEyeDropperActivity$$ExternalSyntheticLambda0.onTouch(android.view.View, android.view.MotionEvent):boolean");
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
