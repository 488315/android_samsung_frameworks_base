package com.android.internal.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.util.Slog;
import android.view.InflateException;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.window.ScreenCapture;
import com.android.internal.R;
import com.android.internal.policy.AttributeCache;
import com.samsung.android.rune.CoreRune;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes5.dex */
public class TransitionAnimation {
    private static final int CLIP_REVEAL_TRANSLATION_Y_DP = 8;
    public static final int DEFAULT_APP_TRANSITION_DURATION = 336;
    private static final String DEFAULT_PACKAGE = "android";
    private static final int MAX_CLIP_REVEAL_TRANSITION_DURATION = 420;
    private static final float RECENTS_THUMBNAIL_FADEIN_FRACTION = 0.5f;
    private static final float RECENTS_THUMBNAIL_FADEOUT_FRACTION = 0.5f;
    private static final int THUMBNAIL_APP_TRANSITION_DURATION = 336;
    private static final int THUMBNAIL_TRANSITION_ENTER_SCALE_DOWN = 2;
    private static final int THUMBNAIL_TRANSITION_ENTER_SCALE_UP = 0;
    private static final int THUMBNAIL_TRANSITION_EXIT_SCALE_DOWN = 3;
    private static final int THUMBNAIL_TRANSITION_EXIT_SCALE_UP = 1;
    static final Interpolator TOUCH_RESPONSE_INTERPOLATOR = new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
    public static final int WALLPAPER_TRANSITION_CHANGE = 1;
    public static final int WALLPAPER_TRANSITION_CLOSE = 3;
    public static final int WALLPAPER_TRANSITION_INTRA_CLOSE = 5;
    public static final int WALLPAPER_TRANSITION_INTRA_OPEN = 4;
    public static final int WALLPAPER_TRANSITION_NONE = 0;
    public static final int WALLPAPER_TRANSITION_OPEN = 2;
    public static final int WALLPAPER_TRANSITION_TRANSLUCENT_OPEN = 6;
    private final int mClipRevealTranslationY;
    private final int mConfigShortAnimTime;
    private final Context mContext;
    private final boolean mDebug;
    private final Interpolator mDecelerateInterpolator;
    private final int mDefaultWindowAnimationStyleResId;
    private final Interpolator mFastOutLinearInInterpolator;
    private final Interpolator mLinearOutSlowInInterpolator;
    private final String mTag;
    private final LogDecelerateInterpolator mInterpolator = new LogDecelerateInterpolator(100, 0);
    private final Interpolator mTouchResponseInterpolator = new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
    private final Interpolator mClipHorizontalInterpolator = new PathInterpolator(0.0f, 0.0f, 0.4f, 1.0f);
    private final Rect mTmpFromClipRect = new Rect();
    private final Rect mTmpToClipRect = new Rect();
    private final Rect mTmpRect = new Rect();
    private int mDisplayId = -1;
    private final Interpolator mThumbnailFadeInInterpolator = new Interpolator() { // from class: com.android.internal.policy.TransitionAnimation$$ExternalSyntheticLambda0
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return this.f$0.lambda$new$0(f);
        }
    };
    private final Interpolator mThumbnailFadeOutInterpolator = new Interpolator() { // from class: com.android.internal.policy.TransitionAnimation$$ExternalSyntheticLambda1
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return this.f$0.lambda$new$1(f);
        }
    };
    private final boolean mLowRamRecentsEnabled = ActivityManager.isLowRamDeviceStatic();

    private int getThumbnailTransitionState(boolean z, boolean z2) {
        return z ? z2 ? 0 : 2 : z2 ? 1 : 3;
    }

    private static int getTransitCompatType(int i, int i2) {
        if (i2 == 4) {
            return 14;
        }
        if (i2 == 5) {
            return 15;
        }
        if (i == 1) {
            return 6;
        }
        return i == 2 ? 7 : 0;
    }

    public static boolean isDefaultPackageAnimRes(int i) {
        return (i & (-16777216)) == 16777216;
    }

    private boolean shouldScaleDownThumbnailTransition(int i) {
        return i == 1;
    }

    private int updateToCustomAnimIfNeeded(int i, boolean z) {
        switch (i) {
            case R.anim.activity_close_enter /* 17432589 */:
                return R.anim.samsung_activity_close_enter;
            case R.anim.activity_close_exit /* 17432590 */:
                return R.anim.samsung_activity_close_exit;
            case R.anim.activity_open_enter /* 17432591 */:
                return R.anim.samsung_activity_open_enter;
            case R.anim.activity_open_exit /* 17432592 */:
                return R.anim.samsung_activity_open_exit;
            case R.anim.activity_translucent_close_exit /* 17432593 */:
                return R.anim.samsung_activity_translucent_close_exit;
            case R.anim.activity_translucent_open_enter /* 17432594 */:
                return R.anim.samsung_activity_translucent_open_enter;
            case R.anim.task_close_enter /* 17432937 */:
                return R.anim.samsung_task_close_enter;
            case R.anim.task_close_exit /* 17432938 */:
                return R.anim.samsung_task_close_exit;
            case R.anim.task_open_enter /* 17432947 */:
                return R.anim.samsung_task_open_enter;
            case R.anim.task_open_exit /* 17432949 */:
                return R.anim.samsung_task_open_exit;
            case R.anim.wallpaper_close_enter /* 17432962 */:
                return R.anim.samsung_wallpaper_close_enter;
            case R.anim.wallpaper_close_exit /* 17432963 */:
                return R.anim.samsung_wallpaper_close_exit;
            case R.anim.wallpaper_open_enter /* 17432970 */:
                return R.anim.samsung_wallpaper_open_enter;
            case R.anim.wallpaper_open_exit /* 17432971 */:
                return R.anim.samsung_wallpaper_open_exit;
            default:
                return i;
        }
    }

    private static int updateToTranslucentAnimIfNeeded(int i) {
        return i == 17432591 ? R.anim.activity_translucent_open_enter : i == 17432590 ? R.anim.activity_translucent_close_exit : i;
    }

    private static int updateToTranslucentAnimIfNeeded(int i, int i2) {
        return (i2 == 24 && i == 17432591) ? R.anim.activity_translucent_open_enter : (i2 == 25 && i == 17432590) ? R.anim.activity_translucent_close_exit : i;
    }

    public TransitionAnimation(Context context, boolean z, String str) {
        this.mContext = context;
        this.mDebug = z;
        this.mTag = str;
        this.mDecelerateInterpolator = AnimationUtils.loadInterpolator(context, 17563651);
        this.mFastOutLinearInInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mClipRevealTranslationY = (int) (context.getResources().getDisplayMetrics().density * 8.0f);
        this.mConfigShortAnimTime = context.getResources().getInteger(17694720);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(R.styleable.Window);
        this.mDefaultWindowAnimationStyleResId = typedArrayObtainStyledAttributes.getResourceId(8, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ float lambda$new$0(float f) {
        if (f < 0.5f) {
            return 0.0f;
        }
        return this.mFastOutLinearInInterpolator.getInterpolation((f - 0.5f) / 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ float lambda$new$1(float f) {
        if (f >= 0.5f) {
            return 1.0f;
        }
        return this.mLinearOutSlowInInterpolator.getInterpolation(f / 0.5f);
    }

    public Animation loadKeyguardExitAnimation(int i, boolean z) {
        if ((i & 2) != 0) {
            return null;
        }
        return createHiddenByKeyguardExit(this.mContext, this.mInterpolator, z, (i & 1) != 0, (i & 8) != 0);
    }

    public Animation loadKeyguardUnoccludeAnimation(int i) {
        return loadDefaultAnimationRes(R.anim.wallpaper_open_exit, i);
    }

    public Animation loadKeyguardUnoccludeAnimation() {
        return loadKeyguardUnoccludeAnimation(-2);
    }

    public Animation loadVoiceActivityOpenAnimation(boolean z, int i) {
        return loadDefaultAnimationRes(z ? R.anim.voice_activity_open_enter : R.anim.voice_activity_open_exit, i);
    }

    public Animation loadVoiceActivityOpenAnimation(boolean z) {
        return loadVoiceActivityOpenAnimation(z, -2);
    }

    public Animation loadVoiceActivityExitAnimation(boolean z, int i) {
        return loadDefaultAnimationRes(z ? R.anim.voice_activity_close_enter : R.anim.voice_activity_close_exit, i);
    }

    public Animation loadVoiceActivityExitAnimation(boolean z) {
        return loadVoiceActivityExitAnimation(z, -2);
    }

    public Animation loadAppTransitionAnimation(String str, int i) {
        return loadAnimationRes(str, i);
    }

    public Animation loadCrossProfileAppEnterAnimation(int i) {
        return loadAnimationRes("android", R.anim.task_open_enter_cross_profile_apps, i);
    }

    public Animation loadCrossProfileAppEnterAnimation() {
        return loadCrossProfileAppEnterAnimation(-2);
    }

    public Animation loadCrossProfileAppThumbnailEnterAnimation() {
        return loadAnimationRes("android", R.anim.cross_profile_apps_thumbnail_enter);
    }

    public Animation createCrossProfileAppsThumbnailAnimationLocked(Rect rect) {
        return prepareThumbnailAnimationWithDuration(loadCrossProfileAppThumbnailEnterAnimation(), rect.width(), rect.height(), 0L, null);
    }

    public Animation loadAnimationRes(String str, int i, int i2) {
        AttributeCache.Entry cachedAnimations;
        if (!ResourceId.isValid(i) || (cachedAnimations = getCachedAnimations(str, i, i2)) == null) {
            return null;
        }
        return loadAnimationSafely(cachedAnimations.context, i, this.mTag);
    }

    public Animation loadAnimationRes(String str, int i) {
        return loadAnimationRes(str, i, -2);
    }

    public Animation loadDefaultAnimationRes(int i, int i2) {
        return loadAnimationRes("android", i, i2);
    }

    public Animation loadDefaultAnimationRes(int i) {
        return loadAnimationRes("android", i, -2);
    }

    public Animation loadAnimationAttr(WindowManager.LayoutParams layoutParams, int i, int i2) {
        int resourceId;
        AttributeCache.Entry cachedAnimations;
        Context context = this.mContext;
        boolean z = false;
        if (i < 0 || (cachedAnimations = getCachedAnimations(layoutParams)) == null) {
            resourceId = 0;
        } else {
            context = cachedAnimations.context;
            resourceId = cachedAnimations.array.getResourceId(i, 0);
        }
        int iUpdateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(resourceId, i2);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM) {
            int iUpdateToCustomAnimIfNeeded = updateToCustomAnimIfNeeded(iUpdateToTranslucentAnimIfNeeded, CoreRune.FW_LARGE_FLIP_TRANSITION && this.mDisplayId == 1);
            if (iUpdateToCustomAnimIfNeeded != iUpdateToTranslucentAnimIfNeeded) {
                iUpdateToTranslucentAnimIfNeeded = iUpdateToCustomAnimIfNeeded;
                z = true;
            }
        }
        if (!ResourceId.isValid(iUpdateToTranslucentAnimIfNeeded)) {
            return null;
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM && z) {
            Animation animationLoadAnimationSafely = loadAnimationSafely(context, iUpdateToTranslucentAnimIfNeeded, this.mTag);
            if (animationLoadAnimationSafely != null) {
                animationLoadAnimationSafely.setIsSystemAnimation(true);
            }
            return animationLoadAnimationSafely;
        }
        return loadAnimationSafely(context, iUpdateToTranslucentAnimIfNeeded, this.mTag);
    }

    public int getAnimationResId(WindowManager.LayoutParams layoutParams, int i, int i2) {
        AttributeCache.Entry cachedAnimations;
        int resourceId = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations(layoutParams)) != null) {
            resourceId = cachedAnimations.array.getResourceId(i, 0);
        }
        int iUpdateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(resourceId, i2);
        return CoreRune.FW_CUSTOM_BASIC_ANIM ? updateToCustomAnimIfNeeded(iUpdateToTranslucentAnimIfNeeded) : iUpdateToTranslucentAnimIfNeeded;
    }

    public int getDefaultAnimationResId(int i, int i2) {
        AttributeCache.Entry cachedAnimations;
        int resourceId = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations("android", this.mDefaultWindowAnimationStyleResId)) != null) {
            resourceId = cachedAnimations.array.getResourceId(i, 0);
        }
        int iUpdateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(resourceId, i2);
        return CoreRune.FW_CUSTOM_BASIC_ANIM ? updateToCustomAnimIfNeeded(iUpdateToTranslucentAnimIfNeeded) : iUpdateToTranslucentAnimIfNeeded;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Animation loadAnimationAttr(String str, int i, int i2, boolean z, int i3) {
        int iUpdateToTranslucentAnimIfNeeded;
        if (i == 0) {
            return null;
        }
        Context context = this.mContext;
        boolean z2 = false;
        if (i2 < 0) {
            iUpdateToTranslucentAnimIfNeeded = 0;
        } else {
            if (str == null) {
                str = "android";
            }
            AttributeCache.Entry cachedAnimations = getCachedAnimations(str, i);
            if (cachedAnimations != null) {
                context = cachedAnimations.context;
                iUpdateToTranslucentAnimIfNeeded = cachedAnimations.array.getResourceId(i2, 0);
            }
        }
        if (z) {
            iUpdateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(iUpdateToTranslucentAnimIfNeeded);
        } else if (i3 != -1) {
            iUpdateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(iUpdateToTranslucentAnimIfNeeded, i3);
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM) {
            int iUpdateToCustomAnimIfNeeded = updateToCustomAnimIfNeeded(iUpdateToTranslucentAnimIfNeeded, CoreRune.FW_LARGE_FLIP_TRANSITION && this.mDisplayId == 1);
            if (iUpdateToCustomAnimIfNeeded != iUpdateToTranslucentAnimIfNeeded) {
                iUpdateToTranslucentAnimIfNeeded = iUpdateToCustomAnimIfNeeded;
                z2 = true;
            }
        }
        if (!ResourceId.isValid(iUpdateToTranslucentAnimIfNeeded)) {
            return null;
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM && z2) {
            Animation animationLoadAnimationSafely = loadAnimationSafely(context, iUpdateToTranslucentAnimIfNeeded, this.mTag);
            if (animationLoadAnimationSafely != null) {
                animationLoadAnimationSafely.setIsSystemAnimation(true);
            }
            return animationLoadAnimationSafely;
        }
        return loadAnimationSafely(context, iUpdateToTranslucentAnimIfNeeded, this.mTag);
    }

    public Animation loadAnimationAttr(String str, int i, int i2, boolean z) {
        return loadAnimationAttr(str, i, i2, z, -1);
    }

    public Animation loadDefaultAnimationAttr(int i, boolean z) {
        return loadAnimationAttr("android", this.mDefaultWindowAnimationStyleResId, i, z);
    }

    public Animation loadDefaultAnimationAttr(int i, int i2) {
        return loadAnimationAttr("android", this.mDefaultWindowAnimationStyleResId, i, false, i2);
    }

    private AttributeCache.Entry getCachedAnimations(WindowManager.LayoutParams layoutParams) {
        if (this.mDebug) {
            String str = this.mTag;
            StringBuilder sb = new StringBuilder("Loading animations: layout params pkg=");
            sb.append(layoutParams != null ? layoutParams.packageName : null);
            sb.append(" resId=0x");
            sb.append(layoutParams != null ? Integer.toHexString(layoutParams.windowAnimations) : null);
            Slog.v(str, sb.toString());
        }
        if (layoutParams == null || layoutParams.windowAnimations == 0) {
            return null;
        }
        String str2 = layoutParams.packageName != null ? layoutParams.packageName : "android";
        int animationStyleResId = getAnimationStyleResId(layoutParams);
        if (((-16777216) & animationStyleResId) == 16777216) {
            str2 = "android";
        }
        if (this.mDebug || !"android".equals(str2)) {
            Slog.v(this.mTag, "Loading animations: picked package=" + str2);
        }
        return AttributeCache.instance().get(str2, animationStyleResId, R.styleable.WindowAnimation);
    }

    private AttributeCache.Entry getCachedAnimations(String str, int i, int i2) {
        if (this.mDebug) {
            Slog.v(this.mTag, "Loading animations: package=" + str + " resId=0x" + Integer.toHexString(i) + " for user=" + i2);
        }
        if (str == null) {
            return null;
        }
        if (((-16777216) & i) == 16777216) {
            str = "android";
        }
        if (this.mDebug || !"android".equals(str)) {
            Slog.v(this.mTag, "Loading animations: picked package=" + str);
        }
        return AttributeCache.instance().get(str, i, R.styleable.WindowAnimation, i2);
    }

    private AttributeCache.Entry getCachedAnimations(String str, int i) {
        return getCachedAnimations(str, i, -2);
    }

    public int getAnimationStyleResId(WindowManager.LayoutParams layoutParams) {
        return layoutParams.type == 3 ? this.mDefaultWindowAnimationStyleResId : layoutParams.windowAnimations;
    }

    public Animation createRelaunchAnimation(Rect rect, Rect rect2, Rect rect3) {
        setupDefaultNextAppTransitionStartRect(rect3, this.mTmpFromClipRect);
        this.mTmpFromClipRect.offset(-this.mTmpFromClipRect.left, -this.mTmpFromClipRect.top);
        int i = 0;
        this.mTmpToClipRect.set(0, 0, rect.width(), rect.height());
        AnimationSet animationSet = new AnimationSet(true);
        float fWidth = this.mTmpFromClipRect.width();
        float fWidth2 = this.mTmpToClipRect.width();
        float fHeight = this.mTmpFromClipRect.height();
        float fHeight2 = (this.mTmpToClipRect.height() - rect2.top) - rect2.bottom;
        if (fWidth <= fWidth2 && fHeight <= fHeight2) {
            animationSet.addAnimation(new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect));
        } else {
            animationSet.addAnimation(new ScaleAnimation(fWidth / fWidth2, 1.0f, fHeight / fHeight2, 1.0f));
            i = (int) ((rect2.top * fHeight) / fHeight2);
        }
        animationSet.addAnimation(new TranslateAnimation(r12 - rect.left, 0.0f, (r0 - rect.top) - i, 0.0f));
        animationSet.setDuration(336L);
        animationSet.setZAdjustment(1);
        return animationSet;
    }

    private void setupDefaultNextAppTransitionStartRect(Rect rect, Rect rect2) {
        if (rect == null) {
            Slog.e(this.mTag, "Starting rect for app requested, but none available", new Throwable());
            rect2.setEmpty();
        } else {
            rect2.set(rect);
        }
    }

    public Animation createClipRevealAnimationLocked(int i, int i2, boolean z, Rect rect, Rect rect2, Rect rect3) {
        return createClipRevealAnimationLockedCompat(getTransitCompatType(i, i2), z, rect, rect2, rect3);
    }

    public Animation createClipRevealAnimationLockedCompat(int i, boolean z, Rect rect, Rect rect2, Rect rect3) {
        AlphaAnimation alphaAnimation;
        boolean z2;
        int i2;
        boolean z3;
        int i3;
        int i4;
        Interpolator interpolator;
        if (z) {
            int iWidth = rect.width();
            int iHeight = rect.height();
            setupDefaultNextAppTransitionStartRect(rect3, this.mTmpRect);
            int iHeight2 = this.mClipRevealTranslationY + ((int) ((rect2.height() / 7.0f) * (iHeight > 0 ? this.mTmpRect.top / rect2.height() : 0.0f)));
            int iCenterX = this.mTmpRect.centerX();
            int iCenterY = this.mTmpRect.centerY();
            int iWidth2 = this.mTmpRect.width() / 2;
            int iHeight3 = this.mTmpRect.height() / 2;
            int i5 = iCenterX - iWidth2;
            int iWidth3 = i5 - rect.left;
            int i6 = iCenterY - iHeight3;
            int i7 = i6 - rect.top;
            if (rect.top > i6) {
                iHeight2 = i6 - rect.top;
                i2 = 0;
                i3 = 0;
                z3 = true;
            } else {
                i2 = i7;
                z3 = false;
                i3 = iHeight2;
            }
            if (rect.left > i5) {
                i4 = i5 - rect.left;
                iWidth3 = 0;
                z3 = true;
            } else {
                i4 = 0;
            }
            int i8 = iCenterX + iWidth2;
            if (rect.right < i8) {
                i4 = i8 - rect.right;
                iWidth3 = iWidth - this.mTmpRect.width();
                z3 = true;
            }
            float f = i4;
            float f2 = iHeight2;
            long jCalculateClipRevealTransitionDuration = calculateClipRevealTransitionDuration(z3, f, f2, rect2);
            ClipRectLRAnimation clipRectLRAnimation = new ClipRectLRAnimation(iWidth3, this.mTmpRect.width() + iWidth3, 0, iWidth);
            clipRectLRAnimation.setInterpolator(this.mClipHorizontalInterpolator);
            clipRectLRAnimation.setDuration((long) (jCalculateClipRevealTransitionDuration / 2.5f));
            TranslateAnimation translateAnimation = new TranslateAnimation(f, 0.0f, f2, 0.0f);
            if (z3) {
                interpolator = this.mTouchResponseInterpolator;
            } else {
                interpolator = this.mLinearOutSlowInInterpolator;
            }
            translateAnimation.setInterpolator(interpolator);
            translateAnimation.setDuration(jCalculateClipRevealTransitionDuration);
            ClipRectTBAnimation clipRectTBAnimation = new ClipRectTBAnimation(i2, this.mTmpRect.height() + i2, 0, iHeight, i3, 0, this.mLinearOutSlowInInterpolator);
            clipRectTBAnimation.setInterpolator(this.mTouchResponseInterpolator);
            clipRectTBAnimation.setDuration(jCalculateClipRevealTransitionDuration);
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.5f, 1.0f);
            alphaAnimation2.setDuration(jCalculateClipRevealTransitionDuration / 4);
            alphaAnimation2.setInterpolator(this.mLinearOutSlowInInterpolator);
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(clipRectLRAnimation);
            animationSet.addAnimation(clipRectTBAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation2);
            animationSet.setZAdjustment(1);
            animationSet.initialize(iWidth, iHeight, iWidth, iHeight);
            return animationSet;
        }
        long j = (i == 6 || i == 7) ? this.mConfigShortAnimTime : 336L;
        if (i == 14 || i == 15) {
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            z2 = true;
            alphaAnimation.setDetachWallpaper(true);
        } else {
            alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
            z2 = true;
        }
        alphaAnimation.setInterpolator(this.mDecelerateInterpolator);
        alphaAnimation.setDuration(j);
        alphaAnimation.setFillAfter(z2);
        return alphaAnimation;
    }

    public Animation createScaleUpAnimationLocked(int i, int i2, boolean z, Rect rect, Rect rect2) {
        return createScaleUpAnimationLockedCompat(getTransitCompatType(i, i2), z, rect, rect2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation createScaleUpAnimationLockedCompat(int i, boolean z, Rect rect, Rect rect2) {
        AlphaAnimation alphaAnimation;
        setupDefaultNextAppTransitionStartRect(rect2, this.mTmpRect);
        int iWidth = rect.width();
        int iHeight = rect.height();
        if (z) {
            float fWidth = this.mTmpRect.width() / iWidth;
            float fHeight = this.mTmpRect.height() / iHeight;
            ScaleAnimation scaleAnimation = new ScaleAnimation(fWidth, 1.0f, fHeight, 1.0f, computePivot(this.mTmpRect.left, fWidth), computePivot(this.mTmpRect.top, fHeight));
            scaleAnimation.setInterpolator(this.mDecelerateInterpolator);
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation2.setInterpolator(this.mThumbnailFadeOutInterpolator);
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation2);
            animationSet.setDetachWallpaper(true);
            alphaAnimation = animationSet;
        } else if (i == 14 || i == 15) {
            AlphaAnimation alphaAnimation3 = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation3.setDetachWallpaper(true);
            alphaAnimation = alphaAnimation3;
        } else {
            alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
        }
        alphaAnimation.setDuration((i == 6 || i == 7) ? this.mConfigShortAnimTime : 336L);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setInterpolator(this.mDecelerateInterpolator);
        alphaAnimation.initialize(iWidth, iHeight, iWidth, iHeight);
        return alphaAnimation;
    }

    public Animation createThumbnailEnterExitAnimationLocked(boolean z, boolean z2, Rect rect, int i, int i2, HardwareBuffer hardwareBuffer, Rect rect2) {
        return createThumbnailEnterExitAnimationLockedCompat(z, z2, rect, getTransitCompatType(i, i2), hardwareBuffer, rect2);
    }

    public Animation createThumbnailEnterExitAnimationLockedCompat(boolean z, boolean z2, Rect rect, int i, HardwareBuffer hardwareBuffer, Rect rect2) {
        Animation scaleAnimation;
        AlphaAnimation alphaAnimation;
        int iWidth = rect.width();
        int iHeight = rect.height();
        setupDefaultNextAppTransitionStartRect(rect2, this.mTmpRect);
        int width = hardwareBuffer != null ? hardwareBuffer.getWidth() : iWidth;
        float f = width > 0 ? width : 1.0f;
        int height = hardwareBuffer != null ? hardwareBuffer.getHeight() : iHeight;
        float f2 = height > 0 ? height : 1.0f;
        int thumbnailTransitionState = getThumbnailTransitionState(z, z2);
        if (thumbnailTransitionState != 0) {
            if (thumbnailTransitionState != 1) {
                if (thumbnailTransitionState == 2) {
                    alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
                } else if (thumbnailTransitionState == 3) {
                    float f3 = f / iWidth;
                    float f4 = f2 / iHeight;
                    ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, f3, 1.0f, f4, computePivot(this.mTmpRect.left, f3), computePivot(this.mTmpRect.top, f4));
                    AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(scaleAnimation2);
                    animationSet.addAnimation(alphaAnimation2);
                    animationSet.setZAdjustment(1);
                    scaleAnimation = animationSet;
                } else {
                    throw new RuntimeException("Invalid thumbnail transition state");
                }
            } else if (i == 14) {
                alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            } else {
                alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
            }
            scaleAnimation = alphaAnimation;
        } else {
            float f5 = f / iWidth;
            float f6 = f2 / iHeight;
            scaleAnimation = new ScaleAnimation(f5, 1.0f, f6, 1.0f, computePivot(this.mTmpRect.left, f5), computePivot(this.mTmpRect.top, f6));
        }
        return prepareThumbnailAnimation(scaleAnimation, iWidth, iHeight, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Animation createAspectScaledThumbnailEnterExitAnimationLocked(boolean z, boolean z2, int i, int i2, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z3, Rect rect5, Rect rect6) {
        ClipRectAnimation clipRectAnimation;
        Animation animationCreateCurvedMotion;
        ClipRectAnimation clipRectAnimation2;
        Animation animationCreateCurvedMotion2;
        Animation animationCreateAspectScaledThumbnailExitFreeformAnimationLocked;
        int iWidth = rect.width();
        int iHeight = rect.height();
        setupDefaultNextAppTransitionStartRect(rect6, this.mTmpRect);
        int iWidth2 = this.mTmpRect.width();
        float f = iWidth2 > 0 ? iWidth2 : 1.0f;
        int iHeight2 = this.mTmpRect.height();
        float f2 = iHeight2 > 0 ? iHeight2 : 1.0f;
        int i3 = (this.mTmpRect.left - rect.left) - rect2.left;
        int i4 = this.mTmpRect.top - rect.top;
        int thumbnailTransitionState = getThumbnailTransitionState(z, z2);
        if (thumbnailTransitionState == 0) {
            if (!z3 && z2) {
                animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = createAspectScaledThumbnailEnterFreeformAnimationLocked(rect, rect3, rect5, rect6);
            } else if (!z3) {
                animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = createAspectScaledThumbnailExitFreeformAnimationLocked(rect, rect3, rect5, rect6);
            } else {
                AnimationSet animationSet = new AnimationSet(true);
                this.mTmpFromClipRect.set(rect);
                this.mTmpToClipRect.set(rect);
                this.mTmpFromClipRect.offsetTo(0, 0);
                this.mTmpToClipRect.offsetTo(0, 0);
                this.mTmpFromClipRect.inset(rect2);
                if (shouldScaleDownThumbnailTransition(i)) {
                    float f3 = f / ((iWidth - rect2.left) - rect2.right);
                    Rect rect7 = this.mTmpFromClipRect;
                    rect7.bottom = rect7.top + ((int) (f2 / f3));
                    ScaleAnimation scaleAnimation = new ScaleAnimation(z2 ? f3 : 1.0f, z2 ? 1.0f : f3, z2 ? f3 : 1.0f, z2 ? 1.0f : f3, rect.width() / 2.0f, (rect.height() / 2.0f) + rect2.top);
                    float f4 = this.mTmpRect.left - rect.left;
                    float fWidth = (rect.width() / 2.0f) - ((rect.width() / 2.0f) * f3);
                    float f5 = this.mTmpRect.top - rect.top;
                    float fHeight = (rect.height() / 2.0f) - ((rect.height() / 2.0f) * f3);
                    if (this.mLowRamRecentsEnabled && rect2.top == 0 && z2) {
                        this.mTmpFromClipRect.top += rect4.top;
                        fHeight += rect4.top;
                    }
                    float f6 = f4 - fWidth;
                    float f7 = f5 - fHeight;
                    if (z2) {
                        clipRectAnimation2 = new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
                    } else {
                        clipRectAnimation2 = new ClipRectAnimation(this.mTmpToClipRect, this.mTmpFromClipRect);
                    }
                    if (z2) {
                        animationCreateCurvedMotion2 = createCurvedMotion(f6, 0.0f, f7 - rect2.top, 0.0f);
                    } else {
                        animationCreateCurvedMotion2 = createCurvedMotion(0.0f, f6, 0.0f, f7 - rect2.top);
                    }
                    animationSet.addAnimation(clipRectAnimation2);
                    animationSet.addAnimation(scaleAnimation);
                    animationSet.addAnimation(animationCreateCurvedMotion2);
                } else {
                    Rect rect8 = this.mTmpFromClipRect;
                    rect8.bottom = rect8.top + iHeight2;
                    Rect rect9 = this.mTmpFromClipRect;
                    rect9.right = rect9.left + iWidth2;
                    if (z2) {
                        clipRectAnimation = new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
                    } else {
                        clipRectAnimation = new ClipRectAnimation(this.mTmpToClipRect, this.mTmpFromClipRect);
                    }
                    if (z2) {
                        animationCreateCurvedMotion = createCurvedMotion(i3, 0.0f, i4 - rect2.top, 0.0f);
                    } else {
                        animationCreateCurvedMotion = createCurvedMotion(0.0f, i3, 0.0f, i4 - rect2.top);
                    }
                    animationSet.addAnimation(clipRectAnimation);
                    animationSet.addAnimation(animationCreateCurvedMotion);
                }
                animationSet.setZAdjustment(1);
                animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = animationSet;
            }
        } else if (thumbnailTransitionState != 1) {
            if (thumbnailTransitionState != 2) {
                if (thumbnailTransitionState != 3) {
                    throw new RuntimeException("Invalid thumbnail transition state");
                }
                if (!z3) {
                    if (!z3) {
                    }
                }
            } else if (i2 == 14) {
                animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = new AlphaAnimation(0.0f, 1.0f);
            } else {
                animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = new AlphaAnimation(1.0f, 1.0f);
            }
        } else if (i2 == 14) {
            animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = new AlphaAnimation(1.0f, 0.0f);
        } else {
            animationCreateAspectScaledThumbnailExitFreeformAnimationLocked = new AlphaAnimation(1.0f, 1.0f);
        }
        return prepareThumbnailAnimationWithDuration(animationCreateAspectScaledThumbnailExitFreeformAnimationLocked, iWidth, iHeight, 336L, this.mTouchResponseInterpolator);
    }

    public Animation createThumbnailAspectScaleAnimationLocked(Rect rect, Rect rect2, HardwareBuffer hardwareBuffer, int i, Rect rect3, Rect rect4, boolean z) {
        float f;
        float f2;
        float fWidth;
        float fHeight;
        float fWidth2;
        float fHeight2;
        AnimationSet animationSet;
        int width = hardwareBuffer.getWidth();
        float f3 = width > 0 ? width : 1.0f;
        int height = hardwareBuffer.getHeight();
        int iWidth = rect.width();
        float f4 = iWidth / f3;
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        if (shouldScaleDownThumbnailTransition(i)) {
            f = this.mTmpRect.left;
            f2 = this.mTmpRect.top;
            fWidth = ((this.mTmpRect.width() / 2) * (f4 - 1.0f)) + rect.left;
            fHeight = ((rect.height() / 2) * (1.0f - (1.0f / f4))) + rect.top;
            fHeight2 = (rect.height() / 2) / f4;
            fWidth2 = this.mTmpRect.width() / 2;
        } else {
            f = this.mTmpRect.left;
            f2 = this.mTmpRect.top;
            fWidth = rect.left;
            fHeight = rect.top;
            fWidth2 = 0.0f;
            fHeight2 = 0.0f;
        }
        float f5 = f;
        if (z) {
            float f6 = f2;
            float f7 = fHeight;
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, f4, 1.0f, f4, fWidth2, fHeight2);
            Interpolator interpolator = TOUCH_RESPONSE_INTERPOLATOR;
            scaleAnimation.setInterpolator(interpolator);
            scaleAnimation.setDuration(336L);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setInterpolator(this.mThumbnailFadeOutInterpolator);
            alphaAnimation.setDuration(336L);
            Animation animationCreateCurvedMotion = createCurvedMotion(f5, fWidth, f6, f7);
            animationCreateCurvedMotion.setInterpolator(interpolator);
            animationCreateCurvedMotion.setDuration(336L);
            this.mTmpFromClipRect.set(0, 0, width, height);
            this.mTmpToClipRect.set(rect);
            this.mTmpToClipRect.offsetTo(0, 0);
            this.mTmpToClipRect.right = (int) (r3.right / f4);
            this.mTmpToClipRect.bottom = (int) (r3.bottom / f4);
            if (rect2 != null) {
                this.mTmpToClipRect.inset((int) ((-rect2.left) * f4), (int) ((-rect2.top) * f4), (int) ((-rect2.right) * f4), (int) ((-rect2.bottom) * f4));
            }
            ClipRectAnimation clipRectAnimation = new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
            clipRectAnimation.setInterpolator(interpolator);
            clipRectAnimation.setDuration(336L);
            animationSet = new AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(animationCreateCurvedMotion);
            animationSet.addAnimation(clipRectAnimation);
        } else {
            float f8 = f2;
            float f9 = fWidth;
            ScaleAnimation scaleAnimation2 = new ScaleAnimation(f4, 1.0f, f4, 1.0f, fWidth2, fHeight2);
            Interpolator interpolator2 = TOUCH_RESPONSE_INTERPOLATOR;
            scaleAnimation2.setInterpolator(interpolator2);
            scaleAnimation2.setDuration(336L);
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation2.setInterpolator(this.mThumbnailFadeInInterpolator);
            alphaAnimation2.setDuration(336L);
            Animation animationCreateCurvedMotion2 = createCurvedMotion(f9, f5, fHeight, f8);
            animationCreateCurvedMotion2.setInterpolator(interpolator2);
            animationCreateCurvedMotion2.setDuration(336L);
            AnimationSet animationSet2 = new AnimationSet(false);
            animationSet2.addAnimation(scaleAnimation2);
            animationSet2.addAnimation(alphaAnimation2);
            animationSet2.addAnimation(animationCreateCurvedMotion2);
            animationSet = animationSet2;
        }
        return prepareThumbnailAnimationWithDuration(animationSet, iWidth, rect.height(), 0L, null);
    }

    public HardwareBuffer createCrossProfileAppsThumbnail(Drawable drawable, Rect rect) throws Resources.NotFoundException {
        int iWidth = rect.width();
        int iHeight = rect.height();
        Picture picture = new Picture();
        Canvas canvasBeginRecording = picture.beginRecording(iWidth, iHeight);
        canvasBeginRecording.drawColor(Color.argb(0.6f, 0.0f, 0.0f, 0.0f));
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.cross_profile_apps_thumbnail_size);
        drawable.setBounds((iWidth - dimensionPixelSize) / 2, (iHeight - dimensionPixelSize) / 2, (iWidth + dimensionPixelSize) / 2, (iHeight + dimensionPixelSize) / 2);
        drawable.setTint(this.mContext.getColor(17170443));
        drawable.draw(canvasBeginRecording);
        picture.endRecording();
        return Bitmap.createBitmap(picture).getHardwareBuffer();
    }

    private Animation prepareThumbnailAnimation(Animation animation, int i, int i2, int i3) {
        return prepareThumbnailAnimationWithDuration(animation, i, i2, (i3 == 6 || i3 == 7) ? this.mConfigShortAnimTime : 336, this.mDecelerateInterpolator);
    }

    private Animation createAspectScaledThumbnailEnterFreeformAnimationLocked(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        return createAspectScaledThumbnailFreeformAnimationLocked(this.mTmpRect, rect, rect2, true);
    }

    private Animation createAspectScaledThumbnailExitFreeformAnimationLocked(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        return createAspectScaledThumbnailFreeformAnimationLocked(rect, this.mTmpRect, rect2, false);
    }

    private void getNextAppTransitionStartRect(Rect rect, Rect rect2, Rect rect3) {
        if (rect == null && rect2 == null) {
            Slog.e(this.mTag, "Starting rect for container not available", new Throwable());
            rect3.setEmpty();
        } else {
            if (rect == null) {
                rect = rect2;
            }
            rect3.set(rect);
        }
    }

    private AnimationSet createAspectScaledThumbnailFreeformAnimationLocked(Rect rect, Rect rect2, Rect rect3, boolean z) {
        ScaleAnimation scaleAnimation;
        TranslateAnimation translateAnimation;
        float fWidth = rect.width();
        float fHeight = rect.height();
        float fWidth2 = rect2.width();
        float fHeight2 = rect2.height();
        float f = z ? fWidth / fWidth2 : fWidth2 / fWidth;
        float f2 = z ? fHeight / fHeight2 : fHeight2 / fHeight;
        AnimationSet animationSet = new AnimationSet(true);
        int i = rect3 == null ? 0 : rect3.left + rect3.right;
        int i2 = rect3 != null ? rect3.top + rect3.bottom : 0;
        if (z) {
            fWidth = fWidth2;
        }
        float f3 = (fWidth + i) / 2.0f;
        if (z) {
            fHeight = fHeight2;
        }
        float f4 = (fHeight + i2) / 2.0f;
        if (z) {
            scaleAnimation = new ScaleAnimation(f, 1.0f, f2, 1.0f, f3, f4);
        } else {
            scaleAnimation = new ScaleAnimation(1.0f, f, 1.0f, f2, f3, f4);
        }
        int iWidth = rect.left + (rect.width() / 2);
        int iHeight = rect.top + (rect.height() / 2);
        int iWidth2 = rect2.left + (rect2.width() / 2);
        int iHeight2 = rect2.top + (rect2.height() / 2);
        int i3 = z ? iWidth - iWidth2 : iWidth2 - iWidth;
        int i4 = z ? iHeight - iHeight2 : iHeight2 - iHeight;
        if (z) {
            translateAnimation = new TranslateAnimation(i3, 0.0f, i4, 0.0f);
        } else {
            translateAnimation = new TranslateAnimation(0.0f, i3, 0.0f, i4);
        }
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    private static long calculateClipRevealTransitionDuration(boolean z, float f, float f2, Rect rect) {
        if (z) {
            return (long) ((Math.max(Math.abs(f) / rect.width(), Math.abs(f2) / rect.height()) * 84.0f) + 336.0f);
        }
        return 336L;
    }

    public static Animation prepareThumbnailAnimationWithDuration(Animation animation, int i, int i2, long j, Interpolator interpolator) {
        if (animation == null) {
            return null;
        }
        if (j > 0) {
            animation.setDuration(j);
        }
        animation.setFillAfter(true);
        if (interpolator != null) {
            animation.setInterpolator(interpolator);
        }
        animation.initialize(i, i2, i, i2);
        return animation;
    }

    private static Animation createCurvedMotion(float f, float f2, float f3, float f4) {
        return new TranslateAnimation(f, f2, f3, f4);
    }

    public static float computePivot(int i, float f) {
        float f2 = f - 1.0f;
        return Math.abs(f2) < 1.0E-4f ? i : (-i) / f2;
    }

    public static Animation loadAnimationSafely(Context context, int i, String str) {
        try {
            return AnimationUtils.loadAnimation(context, i);
        } catch (Resources.NotFoundException | InflateException e) {
            Slog.w(str, "Unable to load animation resource", e);
            return null;
        }
    }

    public static Animation createHiddenByKeyguardExit(Context context, LogDecelerateInterpolator logDecelerateInterpolator, boolean z, boolean z2, boolean z3) {
        if (z2) {
            return AnimationUtils.loadAnimation(context, R.anim.lock_screen_behind_enter_fade_in);
        }
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(context, z3 ? R.anim.lock_screen_behind_enter_subtle : z ? R.anim.lock_screen_behind_enter_wallpaper : R.anim.lock_screen_behind_enter);
        List<Animation> animations = animationSet.getAnimations();
        for (int size = animations.size() - 1; size >= 0; size--) {
            animations.get(size).setInterpolator(logDecelerateInterpolator);
        }
        return animationSet;
    }

    public static void configureScreenshotLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
        transaction.setBuffer(surfaceControl, screenshotHardwareBuffer.getHardwareBuffer());
        transaction.setDataSpace(surfaceControl, screenshotHardwareBuffer.getColorSpace().getDataSpace());
        if (screenshotHardwareBuffer.containsHdrLayers()) {
            transaction.setDimmingEnabled(surfaceControl, false);
        }
    }

    public static boolean hasProtectedContent(HardwareBuffer hardwareBuffer) {
        return (hardwareBuffer.getUsage() & 16384) == 16384;
    }

    public static float getBorderLuma(HardwareBuffer hardwareBuffer, ColorSpace colorSpace, SurfaceControl surfaceControl) {
        if (hasProtectedContent(hardwareBuffer)) {
            return getBorderLuma(surfaceControl, hardwareBuffer.getWidth(), hardwareBuffer.getHeight());
        }
        return getBorderLuma(hardwareBuffer, colorSpace);
    }

    public static float getBorderLuma(SurfaceControl surfaceControl, int i, int i2) {
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBufferCaptureLayers = ScreenCapture.captureLayers(surfaceControl, new Rect(0, 0, i, i2), 1.0f);
        if (screenshotHardwareBufferCaptureLayers == null) {
            return 0.0f;
        }
        HardwareBuffer hardwareBuffer = screenshotHardwareBufferCaptureLayers.getHardwareBuffer();
        float borderLuma = getBorderLuma(hardwareBuffer, screenshotHardwareBufferCaptureLayers.getColorSpace());
        if (hardwareBuffer != null) {
            hardwareBuffer.close();
        }
        return borderLuma;
    }

    public static float getBorderLuma(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        int format;
        if (hardwareBuffer != null && (format = hardwareBuffer.getFormat()) == 1 && !hasProtectedContent(hardwareBuffer)) {
            ImageReader imageReaderNewInstance = ImageReader.newInstance(hardwareBuffer.getWidth(), hardwareBuffer.getHeight(), format, 1);
            imageReaderNewInstance.getSurface().attachAndQueueBufferWithColorSpace(hardwareBuffer, colorSpace);
            Image imageAcquireLatestImage = imageReaderNewInstance.acquireLatestImage();
            if (imageAcquireLatestImage != null && imageAcquireLatestImage.getPlaneCount() >= 1) {
                int i = 0;
                Image.Plane plane = imageAcquireLatestImage.getPlanes()[0];
                ByteBuffer buffer = plane.getBuffer();
                int width = imageAcquireLatestImage.getWidth();
                int height = imageAcquireLatestImage.getHeight();
                int pixelStride = plane.getPixelStride();
                int rowStride = plane.getRowStride();
                int[] iArr = new int[256];
                int i2 = width - 10;
                for (int i3 = 0; i3 < i2; i3 += 10) {
                    int pixelLuminance = getPixelLuminance(buffer, i3, 0, pixelStride, rowStride);
                    int pixelLuminance2 = getPixelLuminance(buffer, i3, height - 1, pixelStride, rowStride);
                    iArr[pixelLuminance] = iArr[pixelLuminance] + 1;
                    iArr[pixelLuminance2] = iArr[pixelLuminance2] + 1;
                }
                int i4 = height - 10;
                for (int i5 = 0; i5 < i4; i5 += 10) {
                    int pixelLuminance3 = getPixelLuminance(buffer, 0, i5, pixelStride, rowStride);
                    int pixelLuminance4 = getPixelLuminance(buffer, width - 1, i5, pixelStride, rowStride);
                    iArr[pixelLuminance3] = iArr[pixelLuminance3] + 1;
                    iArr[pixelLuminance4] = iArr[pixelLuminance4] + 1;
                }
                imageReaderNewInstance.close();
                int i6 = (width + height) / 10;
                int i7 = 0;
                int i8 = 0;
                while (true) {
                    if (i7 >= 256) {
                        break;
                    }
                    i8 += iArr[i7];
                    if (i8 >= i6) {
                        i = i7;
                        break;
                    }
                    i7++;
                }
                return i / 255.0f;
            }
        }
        return 0.0f;
    }

    private static int getPixelLuminance(ByteBuffer byteBuffer, int i, int i2, int i3, int i4) {
        int i5 = byteBuffer.getInt((i2 * i4) + (i * i3));
        return ((((i5 & 255) * 8) + (((i5 >> 8) & 255) * 22)) + (((i5 >> 16) & 255) * 2)) >> 5;
    }

    public static void initAttributeCache(Context context, Handler handler) {
        AttributeCache.init(context);
        AttributeCache.instance().monitorPackageRemove(handler);
    }

    private int updateToCustomAnimIfNeeded(int i) {
        return updateToCustomAnimIfNeeded(i, false);
    }

    public static Animation loadDimAnimation(Context context, int i) {
        return AnimationUtils.loadAnimation(context, i == 1 ? R.anim.samsung_activity_open_dim_anim : R.anim.samsung_activity_close_dim_anim);
    }

    public Animation loadResumeAffordanceAnimation() {
        return loadDefaultAnimationRes(R.anim.samsung_resumed_affordance);
    }

    public void overrideDisplayId(int i) {
        this.mDisplayId = i;
    }

    public static String wallpaperTransitTypeToString(int i) {
        switch (i) {
            case 0:
                return "WALLPAPER_NONE";
            case 1:
                return "WALLPAPER_CHANGE";
            case 2:
                return "WALLPAPER_OPEN";
            case 3:
                return "WALLPAPER_CLOSE";
            case 4:
                return "WALLPAPER_INTRA_OPEN";
            case 5:
                return "WALLPAPER_INTRA_CLOSE";
            case 6:
                return "WALLPAPER_TRANSLUCENT_OPEN";
            default:
                return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
