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
            float lambda$new$0;
            lambda$new$0 = TransitionAnimation.this.lambda$new$0(f);
            return lambda$new$0;
        }
    };
    private final Interpolator mThumbnailFadeOutInterpolator = new Interpolator() { // from class: com.android.internal.policy.TransitionAnimation$$ExternalSyntheticLambda1
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float lambda$new$1;
            lambda$new$1 = TransitionAnimation.this.lambda$new$1(f);
            return lambda$new$1;
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
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(R.styleable.Window);
        this.mDefaultWindowAnimationStyleResId = obtainStyledAttributes.getResourceId(8, 0);
        obtainStyledAttributes.recycle();
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
        int i3;
        AttributeCache.Entry cachedAnimations;
        Context context = this.mContext;
        boolean z = false;
        if (i < 0 || (cachedAnimations = getCachedAnimations(layoutParams)) == null) {
            i3 = 0;
        } else {
            context = cachedAnimations.context;
            i3 = cachedAnimations.array.getResourceId(i, 0);
        }
        int updateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(i3, i2);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM) {
            int updateToCustomAnimIfNeeded = updateToCustomAnimIfNeeded(updateToTranslucentAnimIfNeeded, CoreRune.FW_LARGE_FLIP_TRANSITION && this.mDisplayId == 1);
            if (updateToCustomAnimIfNeeded != updateToTranslucentAnimIfNeeded) {
                updateToTranslucentAnimIfNeeded = updateToCustomAnimIfNeeded;
                z = true;
            }
        }
        if (!ResourceId.isValid(updateToTranslucentAnimIfNeeded)) {
            return null;
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM && z) {
            Animation loadAnimationSafely = loadAnimationSafely(context, updateToTranslucentAnimIfNeeded, this.mTag);
            if (loadAnimationSafely != null) {
                loadAnimationSafely.setIsSystemAnimation(true);
            }
            return loadAnimationSafely;
        }
        return loadAnimationSafely(context, updateToTranslucentAnimIfNeeded, this.mTag);
    }

    public int getAnimationResId(WindowManager.LayoutParams layoutParams, int i, int i2) {
        AttributeCache.Entry cachedAnimations;
        int i3 = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations(layoutParams)) != null) {
            i3 = cachedAnimations.array.getResourceId(i, 0);
        }
        int updateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(i3, i2);
        return CoreRune.FW_CUSTOM_BASIC_ANIM ? updateToCustomAnimIfNeeded(updateToTranslucentAnimIfNeeded) : updateToTranslucentAnimIfNeeded;
    }

    public int getDefaultAnimationResId(int i, int i2) {
        AttributeCache.Entry cachedAnimations;
        int i3 = 0;
        if (i >= 0 && (cachedAnimations = getCachedAnimations("android", this.mDefaultWindowAnimationStyleResId)) != null) {
            i3 = cachedAnimations.array.getResourceId(i, 0);
        }
        int updateToTranslucentAnimIfNeeded = updateToTranslucentAnimIfNeeded(i3, i2);
        return CoreRune.FW_CUSTOM_BASIC_ANIM ? updateToCustomAnimIfNeeded(updateToTranslucentAnimIfNeeded) : updateToTranslucentAnimIfNeeded;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.view.animation.Animation loadAnimationAttr(java.lang.String r4, int r5, int r6, boolean r7, int r8) {
        /*
            r3 = this;
            r0 = 0
            if (r5 != 0) goto L4
            return r0
        L4:
            android.content.Context r1 = r3.mContext
            r2 = 0
            if (r6 < 0) goto L1d
            if (r4 == 0) goto Lc
            goto Le
        Lc:
            java.lang.String r4 = "android"
        Le:
            com.android.internal.policy.AttributeCache$Entry r4 = r3.getCachedAnimations(r4, r5)
            if (r4 == 0) goto L1d
            android.content.Context r1 = r4.context
            android.content.res.TypedArray r4 = r4.array
            int r4 = r4.getResourceId(r6, r2)
            goto L1e
        L1d:
            r4 = r2
        L1e:
            if (r7 == 0) goto L25
            int r4 = updateToTranslucentAnimIfNeeded(r4)
            goto L2c
        L25:
            r5 = -1
            if (r8 == r5) goto L2c
            int r4 = updateToTranslucentAnimIfNeeded(r4, r8)
        L2c:
            boolean r5 = com.samsung.android.rune.CoreRune.FW_CUSTOM_BASIC_ANIM
            r6 = 1
            if (r5 == 0) goto L44
            boolean r5 = com.samsung.android.rune.CoreRune.FW_LARGE_FLIP_TRANSITION
            if (r5 == 0) goto L3b
            int r5 = r3.mDisplayId
            if (r5 != r6) goto L3b
            r5 = r6
            goto L3c
        L3b:
            r5 = r2
        L3c:
            int r5 = r3.updateToCustomAnimIfNeeded(r4, r5)
            if (r5 == r4) goto L44
            r4 = r5
            r2 = r6
        L44:
            boolean r5 = android.content.res.ResourceId.isValid(r4)
            if (r5 == 0) goto L63
            boolean r5 = com.samsung.android.rune.CoreRune.FW_CUSTOM_BASIC_ANIM
            if (r5 == 0) goto L5c
            if (r2 == 0) goto L5c
            java.lang.String r3 = r3.mTag
            android.view.animation.Animation r3 = loadAnimationSafely(r1, r4, r3)
            if (r3 == 0) goto L5b
            r3.setIsSystemAnimation(r6)
        L5b:
            return r3
        L5c:
            java.lang.String r3 = r3.mTag
            android.view.animation.Animation r3 = loadAnimationSafely(r1, r4, r3)
            return r3
        L63:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.policy.TransitionAnimation.loadAnimationAttr(java.lang.String, int, int, boolean, int):android.view.animation.Animation");
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
        float width = this.mTmpFromClipRect.width();
        float width2 = this.mTmpToClipRect.width();
        float height = this.mTmpFromClipRect.height();
        float height2 = (this.mTmpToClipRect.height() - rect2.top) - rect2.bottom;
        if (width <= width2 && height <= height2) {
            animationSet.addAnimation(new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect));
        } else {
            animationSet.addAnimation(new ScaleAnimation(width / width2, 1.0f, height / height2, 1.0f));
            i = (int) ((rect2.top * height) / height2);
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
            int width = rect.width();
            int height = rect.height();
            setupDefaultNextAppTransitionStartRect(rect3, this.mTmpRect);
            int height2 = this.mClipRevealTranslationY + ((int) ((rect2.height() / 7.0f) * (height > 0 ? this.mTmpRect.top / rect2.height() : 0.0f)));
            int centerX = this.mTmpRect.centerX();
            int centerY = this.mTmpRect.centerY();
            int width2 = this.mTmpRect.width() / 2;
            int height3 = this.mTmpRect.height() / 2;
            int i5 = centerX - width2;
            int i6 = i5 - rect.left;
            int i7 = centerY - height3;
            int i8 = i7 - rect.top;
            if (rect.top > i7) {
                height2 = i7 - rect.top;
                i2 = 0;
                i3 = 0;
                z3 = true;
            } else {
                i2 = i8;
                z3 = false;
                i3 = height2;
            }
            if (rect.left > i5) {
                i4 = i5 - rect.left;
                i6 = 0;
                z3 = true;
            } else {
                i4 = 0;
            }
            int i9 = centerX + width2;
            if (rect.right < i9) {
                i4 = i9 - rect.right;
                i6 = width - this.mTmpRect.width();
                z3 = true;
            }
            float f = i4;
            float f2 = height2;
            long calculateClipRevealTransitionDuration = calculateClipRevealTransitionDuration(z3, f, f2, rect2);
            ClipRectLRAnimation clipRectLRAnimation = new ClipRectLRAnimation(i6, this.mTmpRect.width() + i6, 0, width);
            clipRectLRAnimation.setInterpolator(this.mClipHorizontalInterpolator);
            clipRectLRAnimation.setDuration((long) (calculateClipRevealTransitionDuration / 2.5f));
            TranslateAnimation translateAnimation = new TranslateAnimation(f, 0.0f, f2, 0.0f);
            if (z3) {
                interpolator = this.mTouchResponseInterpolator;
            } else {
                interpolator = this.mLinearOutSlowInInterpolator;
            }
            translateAnimation.setInterpolator(interpolator);
            translateAnimation.setDuration(calculateClipRevealTransitionDuration);
            ClipRectTBAnimation clipRectTBAnimation = new ClipRectTBAnimation(i2, this.mTmpRect.height() + i2, 0, height, i3, 0, this.mLinearOutSlowInInterpolator);
            clipRectTBAnimation.setInterpolator(this.mTouchResponseInterpolator);
            clipRectTBAnimation.setDuration(calculateClipRevealTransitionDuration);
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.5f, 1.0f);
            alphaAnimation2.setDuration(calculateClipRevealTransitionDuration / 4);
            alphaAnimation2.setInterpolator(this.mLinearOutSlowInInterpolator);
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(clipRectLRAnimation);
            animationSet.addAnimation(clipRectTBAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation2);
            animationSet.setZAdjustment(1);
            animationSet.initialize(width, height, width, height);
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
        int width = rect.width();
        int height = rect.height();
        if (z) {
            float width2 = this.mTmpRect.width() / width;
            float height2 = this.mTmpRect.height() / height;
            ScaleAnimation scaleAnimation = new ScaleAnimation(width2, 1.0f, height2, 1.0f, computePivot(this.mTmpRect.left, width2), computePivot(this.mTmpRect.top, height2));
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
        alphaAnimation.initialize(width, height, width, height);
        return alphaAnimation;
    }

    public Animation createThumbnailEnterExitAnimationLocked(boolean z, boolean z2, Rect rect, int i, int i2, HardwareBuffer hardwareBuffer, Rect rect2) {
        return createThumbnailEnterExitAnimationLockedCompat(z, z2, rect, getTransitCompatType(i, i2), hardwareBuffer, rect2);
    }

    public Animation createThumbnailEnterExitAnimationLockedCompat(boolean z, boolean z2, Rect rect, int i, HardwareBuffer hardwareBuffer, Rect rect2) {
        Animation scaleAnimation;
        AlphaAnimation alphaAnimation;
        int width = rect.width();
        int height = rect.height();
        setupDefaultNextAppTransitionStartRect(rect2, this.mTmpRect);
        int width2 = hardwareBuffer != null ? hardwareBuffer.getWidth() : width;
        float f = width2 > 0 ? width2 : 1.0f;
        int height2 = hardwareBuffer != null ? hardwareBuffer.getHeight() : height;
        float f2 = height2 > 0 ? height2 : 1.0f;
        int thumbnailTransitionState = getThumbnailTransitionState(z, z2);
        if (thumbnailTransitionState != 0) {
            if (thumbnailTransitionState != 1) {
                if (thumbnailTransitionState == 2) {
                    alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
                } else if (thumbnailTransitionState == 3) {
                    float f3 = f / width;
                    float f4 = f2 / height;
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
            float f5 = f / width;
            float f6 = f2 / height;
            scaleAnimation = new ScaleAnimation(f5, 1.0f, f6, 1.0f, computePivot(this.mTmpRect.left, f5), computePivot(this.mTmpRect.top, f6));
        }
        return prepareThumbnailAnimation(scaleAnimation, width, height, i);
    }

    public Animation createAspectScaledThumbnailEnterExitAnimationLocked(boolean z, boolean z2, int i, int i2, Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z3, Rect rect5, Rect rect6) {
        ClipRectAnimation clipRectAnimation;
        Animation createCurvedMotion;
        ClipRectAnimation clipRectAnimation2;
        Animation createCurvedMotion2;
        Animation animation;
        int width = rect.width();
        int height = rect.height();
        setupDefaultNextAppTransitionStartRect(rect6, this.mTmpRect);
        int width2 = this.mTmpRect.width();
        float f = width2 > 0 ? width2 : 1.0f;
        int height2 = this.mTmpRect.height();
        float f2 = height2 > 0 ? height2 : 1.0f;
        int i3 = (this.mTmpRect.left - rect.left) - rect2.left;
        int i4 = this.mTmpRect.top - rect.top;
        int thumbnailTransitionState = getThumbnailTransitionState(z, z2);
        if (thumbnailTransitionState != 0) {
            if (thumbnailTransitionState != 1) {
                if (thumbnailTransitionState != 2) {
                    if (thumbnailTransitionState != 3) {
                        throw new RuntimeException("Invalid thumbnail transition state");
                    }
                } else if (i2 == 14) {
                    animation = new AlphaAnimation(0.0f, 1.0f);
                } else {
                    animation = new AlphaAnimation(1.0f, 1.0f);
                }
            } else if (i2 == 14) {
                animation = new AlphaAnimation(1.0f, 0.0f);
            } else {
                animation = new AlphaAnimation(1.0f, 1.0f);
            }
            return prepareThumbnailAnimationWithDuration(animation, width, height, 336L, this.mTouchResponseInterpolator);
        }
        if (z3 && z2) {
            animation = createAspectScaledThumbnailEnterFreeformAnimationLocked(rect, rect3, rect5, rect6);
        } else if (z3) {
            animation = createAspectScaledThumbnailExitFreeformAnimationLocked(rect, rect3, rect5, rect6);
        } else {
            AnimationSet animationSet = new AnimationSet(true);
            this.mTmpFromClipRect.set(rect);
            this.mTmpToClipRect.set(rect);
            this.mTmpFromClipRect.offsetTo(0, 0);
            this.mTmpToClipRect.offsetTo(0, 0);
            this.mTmpFromClipRect.inset(rect2);
            if (shouldScaleDownThumbnailTransition(i)) {
                float f3 = f / ((width - rect2.left) - rect2.right);
                Rect rect7 = this.mTmpFromClipRect;
                rect7.bottom = rect7.top + ((int) (f2 / f3));
                ScaleAnimation scaleAnimation = new ScaleAnimation(z2 ? f3 : 1.0f, z2 ? 1.0f : f3, z2 ? f3 : 1.0f, z2 ? 1.0f : f3, rect.width() / 2.0f, (rect.height() / 2.0f) + rect2.top);
                float f4 = this.mTmpRect.left - rect.left;
                float width3 = (rect.width() / 2.0f) - ((rect.width() / 2.0f) * f3);
                float f5 = this.mTmpRect.top - rect.top;
                float height3 = (rect.height() / 2.0f) - ((rect.height() / 2.0f) * f3);
                if (this.mLowRamRecentsEnabled && rect2.top == 0 && z2) {
                    this.mTmpFromClipRect.top += rect4.top;
                    height3 += rect4.top;
                }
                float f6 = f4 - width3;
                float f7 = f5 - height3;
                if (z2) {
                    clipRectAnimation2 = new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
                } else {
                    clipRectAnimation2 = new ClipRectAnimation(this.mTmpToClipRect, this.mTmpFromClipRect);
                }
                if (z2) {
                    createCurvedMotion2 = createCurvedMotion(f6, 0.0f, f7 - rect2.top, 0.0f);
                } else {
                    createCurvedMotion2 = createCurvedMotion(0.0f, f6, 0.0f, f7 - rect2.top);
                }
                animationSet.addAnimation(clipRectAnimation2);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(createCurvedMotion2);
            } else {
                Rect rect8 = this.mTmpFromClipRect;
                rect8.bottom = rect8.top + height2;
                Rect rect9 = this.mTmpFromClipRect;
                rect9.right = rect9.left + width2;
                if (z2) {
                    clipRectAnimation = new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
                } else {
                    clipRectAnimation = new ClipRectAnimation(this.mTmpToClipRect, this.mTmpFromClipRect);
                }
                if (z2) {
                    createCurvedMotion = createCurvedMotion(i3, 0.0f, i4 - rect2.top, 0.0f);
                } else {
                    createCurvedMotion = createCurvedMotion(0.0f, i3, 0.0f, i4 - rect2.top);
                }
                animationSet.addAnimation(clipRectAnimation);
                animationSet.addAnimation(createCurvedMotion);
            }
            animationSet.setZAdjustment(1);
            animation = animationSet;
        }
        return prepareThumbnailAnimationWithDuration(animation, width, height, 336L, this.mTouchResponseInterpolator);
    }

    public Animation createThumbnailAspectScaleAnimationLocked(Rect rect, Rect rect2, HardwareBuffer hardwareBuffer, int i, Rect rect3, Rect rect4, boolean z) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        AnimationSet animationSet;
        int width = hardwareBuffer.getWidth();
        float f7 = width > 0 ? width : 1.0f;
        int height = hardwareBuffer.getHeight();
        int width2 = rect.width();
        float f8 = width2 / f7;
        getNextAppTransitionStartRect(rect3, rect4, this.mTmpRect);
        if (shouldScaleDownThumbnailTransition(i)) {
            f = this.mTmpRect.left;
            f2 = this.mTmpRect.top;
            f3 = ((this.mTmpRect.width() / 2) * (f8 - 1.0f)) + rect.left;
            f4 = ((rect.height() / 2) * (1.0f - (1.0f / f8))) + rect.top;
            f6 = (rect.height() / 2) / f8;
            f5 = this.mTmpRect.width() / 2;
        } else {
            f = this.mTmpRect.left;
            f2 = this.mTmpRect.top;
            f3 = rect.left;
            f4 = rect.top;
            f5 = 0.0f;
            f6 = 0.0f;
        }
        float f9 = f;
        if (z) {
            float f10 = f2;
            float f11 = f4;
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, f8, 1.0f, f8, f5, f6);
            Interpolator interpolator = TOUCH_RESPONSE_INTERPOLATOR;
            scaleAnimation.setInterpolator(interpolator);
            scaleAnimation.setDuration(336L);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setInterpolator(this.mThumbnailFadeOutInterpolator);
            alphaAnimation.setDuration(336L);
            Animation createCurvedMotion = createCurvedMotion(f9, f3, f10, f11);
            createCurvedMotion.setInterpolator(interpolator);
            createCurvedMotion.setDuration(336L);
            this.mTmpFromClipRect.set(0, 0, width, height);
            this.mTmpToClipRect.set(rect);
            this.mTmpToClipRect.offsetTo(0, 0);
            this.mTmpToClipRect.right = (int) (r3.right / f8);
            this.mTmpToClipRect.bottom = (int) (r3.bottom / f8);
            if (rect2 != null) {
                this.mTmpToClipRect.inset((int) ((-rect2.left) * f8), (int) ((-rect2.top) * f8), (int) ((-rect2.right) * f8), (int) ((-rect2.bottom) * f8));
            }
            ClipRectAnimation clipRectAnimation = new ClipRectAnimation(this.mTmpFromClipRect, this.mTmpToClipRect);
            clipRectAnimation.setInterpolator(interpolator);
            clipRectAnimation.setDuration(336L);
            animationSet = new AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(createCurvedMotion);
            animationSet.addAnimation(clipRectAnimation);
        } else {
            float f12 = f2;
            float f13 = f3;
            ScaleAnimation scaleAnimation2 = new ScaleAnimation(f8, 1.0f, f8, 1.0f, f5, f6);
            Interpolator interpolator2 = TOUCH_RESPONSE_INTERPOLATOR;
            scaleAnimation2.setInterpolator(interpolator2);
            scaleAnimation2.setDuration(336L);
            AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation2.setInterpolator(this.mThumbnailFadeInInterpolator);
            alphaAnimation2.setDuration(336L);
            Animation createCurvedMotion2 = createCurvedMotion(f13, f9, f4, f12);
            createCurvedMotion2.setInterpolator(interpolator2);
            createCurvedMotion2.setDuration(336L);
            AnimationSet animationSet2 = new AnimationSet(false);
            animationSet2.addAnimation(scaleAnimation2);
            animationSet2.addAnimation(alphaAnimation2);
            animationSet2.addAnimation(createCurvedMotion2);
            animationSet = animationSet2;
        }
        return prepareThumbnailAnimationWithDuration(animationSet, width2, rect.height(), 0L, null);
    }

    public HardwareBuffer createCrossProfileAppsThumbnail(Drawable drawable, Rect rect) {
        int width = rect.width();
        int height = rect.height();
        Picture picture = new Picture();
        Canvas beginRecording = picture.beginRecording(width, height);
        beginRecording.drawColor(Color.argb(0.6f, 0.0f, 0.0f, 0.0f));
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.cross_profile_apps_thumbnail_size);
        drawable.setBounds((width - dimensionPixelSize) / 2, (height - dimensionPixelSize) / 2, (width + dimensionPixelSize) / 2, (height + dimensionPixelSize) / 2);
        drawable.setTint(this.mContext.getColor(17170443));
        drawable.draw(beginRecording);
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
        float width = rect.width();
        float height = rect.height();
        float width2 = rect2.width();
        float height2 = rect2.height();
        float f = z ? width / width2 : width2 / width;
        float f2 = z ? height / height2 : height2 / height;
        AnimationSet animationSet = new AnimationSet(true);
        int i = rect3 == null ? 0 : rect3.left + rect3.right;
        int i2 = rect3 != null ? rect3.top + rect3.bottom : 0;
        if (z) {
            width = width2;
        }
        float f3 = (width + i) / 2.0f;
        if (z) {
            height = height2;
        }
        float f4 = (height + i2) / 2.0f;
        if (z) {
            scaleAnimation = new ScaleAnimation(f, 1.0f, f2, 1.0f, f3, f4);
        } else {
            scaleAnimation = new ScaleAnimation(1.0f, f, 1.0f, f2, f3, f4);
        }
        int width3 = rect.left + (rect.width() / 2);
        int height3 = rect.top + (rect.height() / 2);
        int width4 = rect2.left + (rect2.width() / 2);
        int height4 = rect2.top + (rect2.height() / 2);
        int i3 = z ? width3 - width4 : width4 - width3;
        int i4 = z ? height3 - height4 : height4 - height3;
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
        ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(surfaceControl, new Rect(0, 0, i, i2), 1.0f);
        if (captureLayers == null) {
            return 0.0f;
        }
        HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
        float borderLuma = getBorderLuma(hardwareBuffer, captureLayers.getColorSpace());
        if (hardwareBuffer != null) {
            hardwareBuffer.close();
        }
        return borderLuma;
    }

    public static float getBorderLuma(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        int format;
        if (hardwareBuffer != null && (format = hardwareBuffer.getFormat()) == 1 && !hasProtectedContent(hardwareBuffer)) {
            ImageReader newInstance = ImageReader.newInstance(hardwareBuffer.getWidth(), hardwareBuffer.getHeight(), format, 1);
            newInstance.getSurface().attachAndQueueBufferWithColorSpace(hardwareBuffer, colorSpace);
            Image acquireLatestImage = newInstance.acquireLatestImage();
            if (acquireLatestImage != null && acquireLatestImage.getPlaneCount() >= 1) {
                int i = 0;
                Image.Plane plane = acquireLatestImage.getPlanes()[0];
                ByteBuffer buffer = plane.getBuffer();
                int width = acquireLatestImage.getWidth();
                int height = acquireLatestImage.getHeight();
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
                newInstance.close();
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
