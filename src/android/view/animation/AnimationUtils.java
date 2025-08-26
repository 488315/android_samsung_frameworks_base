package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.flags.Flags;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class AnimationUtils {
    private static final int SEQUENTIALLY = 1;
    private static final String TAG = "AnimationUtils";
    private static final int TOGETHER = 0;
    private static boolean sExpectedPresentationTimeFlagValue = Flags.expectedPresentationTimeReadOnly();
    private static ThreadLocal<AnimationState> sAnimationState = new ThreadLocal<AnimationState>() { // from class: android.view.animation.AnimationUtils.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public AnimationState initialValue() {
            return new AnimationState();
        }
    };

    private static class AnimationState {
        boolean animationClockLocked;
        long currentVsyncTimeMillis;
        long lastReportedTimeMillis;
        long mExpectedPresentationTimeNanos;

        private AnimationState() {
        }
    }

    public static void lockAnimationClock(long j, long j2) {
        AnimationState animationState = sAnimationState.get();
        animationState.animationClockLocked = true;
        animationState.currentVsyncTimeMillis = j;
        if (sExpectedPresentationTimeFlagValue) {
            return;
        }
        animationState.mExpectedPresentationTimeNanos = j2;
    }

    public static void lockAnimationClock(long j) {
        AnimationState animationState = sAnimationState.get();
        animationState.animationClockLocked = true;
        animationState.currentVsyncTimeMillis = j;
    }

    public static void unlockAnimationClock() {
        sAnimationState.get().animationClockLocked = false;
    }

    public static long currentAnimationTimeMillis() {
        AnimationState animationState = sAnimationState.get();
        if (animationState.animationClockLocked) {
            return Math.max(animationState.currentVsyncTimeMillis, animationState.lastReportedTimeMillis);
        }
        animationState.lastReportedTimeMillis = SystemClock.uptimeMillis();
        return animationState.lastReportedTimeMillis;
    }

    public static long getExpectedPresentationTimeNanos() {
        if (!sExpectedPresentationTimeFlagValue) {
            return SystemClock.uptimeMillis() * 1000000;
        }
        return sAnimationState.get().mExpectedPresentationTimeNanos;
    }

    public static long getExpectedPresentationTimeMillis() {
        return getExpectedPresentationTimeNanos() / 1000000;
    }

    public static Animation loadAnimation(Context context, int i) throws Resources.NotFoundException {
        String resourceName;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser animation = context.getResources().getAnimation(i);
                try {
                    Animation animationCreateAnimationFromXml = createAnimationFromXml(context, animation);
                    if (animation != null) {
                        animation.close();
                    }
                    return animationCreateAnimationFromXml;
                } catch (RuntimeException e) {
                    Log.e(TAG, "RuntimeException for unknown animation name, resouce ID #0x" + Integer.toHexString(i));
                    Log.e(TAG, "loadAnimation: getConfiguration = " + context.getResources().getConfiguration());
                    Log.e(TAG, "loadAnimation: getDisplayMetrics = " + context.getResources().getDisplayMetrics());
                    try {
                        resourceName = context.getResources().getResourceName(i);
                    } catch (Resources.NotFoundException unused) {
                        resourceName = "unknown";
                    }
                    Log.e(TAG, "loadAnimation: resourceName = " + resourceName);
                    throw e;
                }
            } catch (IOException | XmlPullParserException e2) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    private static Animation createAnimationFromXml(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return createAnimationFromXml(context, xmlPullParser, null, Xml.asAttributeSet(xmlPullParser));
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a7, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Animation createAnimationFromXml(Context context, XmlPullParser xmlPullParser, AnimationSet animationSet, AttributeSet attributeSet) throws XmlPullParserException, InflateException, IOException {
        int depth = xmlPullParser.getDepth();
        Animation extendAnimation = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                break;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("set")) {
                    AnimationSet animationSet2 = new AnimationSet(context, attributeSet);
                    createAnimationFromXml(context, xmlPullParser, animationSet2, attributeSet);
                    extendAnimation = animationSet2;
                } else if (name.equals("alpha")) {
                    extendAnimation = new AlphaAnimation(context, attributeSet);
                } else if (name.equals("scale")) {
                    extendAnimation = new ScaleAnimation(context, attributeSet);
                } else if (name.equals("rotate")) {
                    extendAnimation = new RotateAnimation(context, attributeSet);
                } else if (name.equals("translate")) {
                    extendAnimation = new TranslateAnimation(context, attributeSet);
                } else if (name.equals("cliprect")) {
                    extendAnimation = new ClipRectAnimation(context, attributeSet);
                } else if (name.equals("extend")) {
                    extendAnimation = new ExtendAnimation(context, attributeSet);
                } else {
                    throw new InflateException("Unknown animation name: " + xmlPullParser.getName());
                }
                if (animationSet != null) {
                    animationSet.addAnimation(extendAnimation);
                }
            }
        }
    }

    public static LayoutAnimationController loadLayoutAnimation(Context context, int i) throws Resources.NotFoundException {
        XmlResourceParser animation = null;
        try {
            try {
                animation = context.getResources().getAnimation(i);
                return createLayoutAnimationFromXml(context, animation);
            } catch (InflateException | IOException | XmlPullParserException e) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            }
        } finally {
            if (animation != null) {
                animation.close();
            }
        }
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, InflateException, IOException {
        return createLayoutAnimationFromXml(context, xmlPullParser, Xml.asAttributeSet(xmlPullParser));
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004d, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static LayoutAnimationController createLayoutAnimationFromXml(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, InflateException, IOException {
        int depth = xmlPullParser.getDepth();
        LayoutAnimationController layoutAnimationController = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                break;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if ("layoutAnimation".equals(name)) {
                    layoutAnimationController = new LayoutAnimationController(context, attributeSet);
                } else if ("gridLayoutAnimation".equals(name)) {
                    layoutAnimationController = new GridLayoutAnimationController(context, attributeSet);
                } else {
                    throw new InflateException("Unknown layout animation name: " + name);
                }
            }
        }
    }

    public static Animation makeInAnimation(Context context, boolean z) throws Resources.NotFoundException {
        Animation animationLoadAnimation;
        if (z) {
            animationLoadAnimation = loadAnimation(context, 17432578);
        } else {
            animationLoadAnimation = loadAnimation(context, R.anim.slide_in_right);
        }
        animationLoadAnimation.setInterpolator(new DecelerateInterpolator());
        animationLoadAnimation.setStartTime(currentAnimationTimeMillis());
        return animationLoadAnimation;
    }

    public static Animation makeOutAnimation(Context context, boolean z) throws Resources.NotFoundException {
        Animation animationLoadAnimation;
        if (z) {
            animationLoadAnimation = loadAnimation(context, 17432579);
        } else {
            animationLoadAnimation = loadAnimation(context, R.anim.slide_out_left);
        }
        animationLoadAnimation.setInterpolator(new AccelerateInterpolator());
        animationLoadAnimation.setStartTime(currentAnimationTimeMillis());
        return animationLoadAnimation;
    }

    public static Animation makeInChildBottomAnimation(Context context) throws Resources.NotFoundException {
        Animation animationLoadAnimation = loadAnimation(context, R.anim.slide_in_child_bottom);
        animationLoadAnimation.setInterpolator(new AccelerateInterpolator());
        animationLoadAnimation.setStartTime(currentAnimationTimeMillis());
        return animationLoadAnimation;
    }

    public static Interpolator loadInterpolator(Context context, int i) throws Resources.NotFoundException {
        XmlResourceParser animation = null;
        try {
            try {
                animation = context.getResources().getAnimation(i);
                return createInterpolatorFromXml(context.getResources(), context.getTheme(), animation);
            } catch (InflateException | IOException | XmlPullParserException e) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            }
        } finally {
            if (animation != null) {
                animation.close();
            }
        }
    }

    public static Interpolator loadInterpolator(Resources resources, Resources.Theme theme, int i) throws Resources.NotFoundException {
        XmlResourceParser animation = null;
        try {
            try {
                animation = resources.getAnimation(i);
                return createInterpolatorFromXml(resources, theme, animation);
            } catch (InflateException | IOException | XmlPullParserException e) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            }
        } finally {
            if (animation != null) {
                animation.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00c9, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Interpolator createInterpolatorFromXml(Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser) throws XmlPullParserException, InflateException, IOException {
        Interpolator accelerateInterpolator;
        int depth = xmlPullParser.getDepth();
        Interpolator linearInterpolator = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                break;
            }
            if (next == 2) {
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
                String name = xmlPullParser.getName();
                if (name.equals("linearInterpolator")) {
                    linearInterpolator = new LinearInterpolator();
                } else {
                    if (name.equals("accelerateInterpolator")) {
                        accelerateInterpolator = new AccelerateInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else if (name.equals("decelerateInterpolator")) {
                        accelerateInterpolator = new DecelerateInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else if (name.equals("accelerateDecelerateInterpolator")) {
                        linearInterpolator = new AccelerateDecelerateInterpolator();
                    } else if (name.equals("cycleInterpolator")) {
                        accelerateInterpolator = new CycleInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else if (name.equals("anticipateInterpolator")) {
                        accelerateInterpolator = new AnticipateInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else if (name.equals("overshootInterpolator")) {
                        accelerateInterpolator = new OvershootInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else if (name.equals("anticipateOvershootInterpolator")) {
                        accelerateInterpolator = new AnticipateOvershootInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else if (name.equals("bounceInterpolator")) {
                        linearInterpolator = new BounceInterpolator();
                    } else if (name.equals("pathInterpolator")) {
                        accelerateInterpolator = new PathInterpolator(resources, theme, attributeSetAsAttributeSet);
                    } else {
                        throw new InflateException("Unknown interpolator name: " + xmlPullParser.getName());
                    }
                    linearInterpolator = accelerateInterpolator;
                }
            }
        }
    }
}
