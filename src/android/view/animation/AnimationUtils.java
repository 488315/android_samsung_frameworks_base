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
        String str;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser animation = context.getResources().getAnimation(i);
                try {
                    Animation createAnimationFromXml = createAnimationFromXml(context, animation);
                    if (animation != null) {
                        animation.close();
                    }
                    return createAnimationFromXml;
                } catch (RuntimeException e) {
                    Log.e(TAG, "RuntimeException for unknown animation name, resouce ID #0x" + Integer.toHexString(i));
                    Log.e(TAG, "loadAnimation: getConfiguration = " + context.getResources().getConfiguration());
                    Log.e(TAG, "loadAnimation: getDisplayMetrics = " + context.getResources().getDisplayMetrics());
                    try {
                        str = context.getResources().getResourceName(i);
                    } catch (Resources.NotFoundException unused) {
                        str = "unknown";
                    }
                    Log.e(TAG, "loadAnimation: resourceName = " + str);
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

    private static Animation createAnimationFromXml(Context context, XmlPullParser xmlPullParser, AnimationSet animationSet, AttributeSet attributeSet) throws XmlPullParserException, IOException, InflateException {
        int depth = xmlPullParser.getDepth();
        Animation animation = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    String name = xmlPullParser.getName();
                    if (name.equals("set")) {
                        AnimationSet animationSet2 = new AnimationSet(context, attributeSet);
                        createAnimationFromXml(context, xmlPullParser, animationSet2, attributeSet);
                        animation = animationSet2;
                    } else if (name.equals("alpha")) {
                        animation = new AlphaAnimation(context, attributeSet);
                    } else if (name.equals("scale")) {
                        animation = new ScaleAnimation(context, attributeSet);
                    } else if (name.equals("rotate")) {
                        animation = new RotateAnimation(context, attributeSet);
                    } else if (name.equals("translate")) {
                        animation = new TranslateAnimation(context, attributeSet);
                    } else if (name.equals("cliprect")) {
                        animation = new ClipRectAnimation(context, attributeSet);
                    } else if (name.equals("extend")) {
                        animation = new ExtendAnimation(context, attributeSet);
                    } else {
                        throw new InflateException("Unknown animation name: " + xmlPullParser.getName());
                    }
                    if (animationSet != null) {
                        animationSet.addAnimation(animation);
                    }
                }
            }
        }
        return animation;
    }

    public static LayoutAnimationController loadLayoutAnimation(Context context, int i) throws Resources.NotFoundException {
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = context.getResources().getAnimation(i);
                return createLayoutAnimationFromXml(context, xmlResourceParser);
            } catch (InflateException | IOException | XmlPullParserException e) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, InflateException {
        return createLayoutAnimationFromXml(context, xmlPullParser, Xml.asAttributeSet(xmlPullParser));
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException, InflateException {
        int depth = xmlPullParser.getDepth();
        LayoutAnimationController layoutAnimationController = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
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
        return layoutAnimationController;
    }

    public static Animation makeInAnimation(Context context, boolean z) {
        Animation loadAnimation;
        if (z) {
            loadAnimation = loadAnimation(context, 17432578);
        } else {
            loadAnimation = loadAnimation(context, R.anim.slide_in_right);
        }
        loadAnimation.setInterpolator(new DecelerateInterpolator());
        loadAnimation.setStartTime(currentAnimationTimeMillis());
        return loadAnimation;
    }

    public static Animation makeOutAnimation(Context context, boolean z) {
        Animation loadAnimation;
        if (z) {
            loadAnimation = loadAnimation(context, 17432579);
        } else {
            loadAnimation = loadAnimation(context, R.anim.slide_out_left);
        }
        loadAnimation.setInterpolator(new AccelerateInterpolator());
        loadAnimation.setStartTime(currentAnimationTimeMillis());
        return loadAnimation;
    }

    public static Animation makeInChildBottomAnimation(Context context) {
        Animation loadAnimation = loadAnimation(context, R.anim.slide_in_child_bottom);
        loadAnimation.setInterpolator(new AccelerateInterpolator());
        loadAnimation.setStartTime(currentAnimationTimeMillis());
        return loadAnimation;
    }

    public static Interpolator loadInterpolator(Context context, int i) throws Resources.NotFoundException {
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = context.getResources().getAnimation(i);
                return createInterpolatorFromXml(context.getResources(), context.getTheme(), xmlResourceParser);
            } catch (InflateException | IOException | XmlPullParserException e) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    public static Interpolator loadInterpolator(Resources resources, Resources.Theme theme, int i) throws Resources.NotFoundException {
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = resources.getAnimation(i);
                return createInterpolatorFromXml(resources, theme, xmlResourceParser);
            } catch (InflateException | IOException | XmlPullParserException e) {
                throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x00c9, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.view.animation.Interpolator createInterpolatorFromXml(android.content.res.Resources r4, android.content.res.Resources.Theme r5, org.xmlpull.v1.XmlPullParser r6) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.view.InflateException {
        /*
            int r0 = r6.getDepth()
            r1 = 0
        L5:
            int r2 = r6.next()
            r3 = 3
            if (r2 != r3) goto L12
            int r3 = r6.getDepth()
            if (r3 <= r0) goto Lc9
        L12:
            r3 = 1
            if (r2 == r3) goto Lc9
            r3 = 2
            if (r2 == r3) goto L19
            goto L5
        L19:
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r6)
            java.lang.String r2 = r6.getName()
            java.lang.String r3 = "linearInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L2f
            android.view.animation.LinearInterpolator r1 = new android.view.animation.LinearInterpolator
            r1.<init>()
            goto L5
        L2f:
            java.lang.String r3 = "accelerateInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L3e
            android.view.animation.AccelerateInterpolator r2 = new android.view.animation.AccelerateInterpolator
            r2.<init>(r4, r5, r1)
        L3c:
            r1 = r2
            goto L5
        L3e:
            java.lang.String r3 = "decelerateInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L4c
            android.view.animation.DecelerateInterpolator r2 = new android.view.animation.DecelerateInterpolator
            r2.<init>(r4, r5, r1)
            goto L3c
        L4c:
            java.lang.String r3 = "accelerateDecelerateInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L5a
            android.view.animation.AccelerateDecelerateInterpolator r1 = new android.view.animation.AccelerateDecelerateInterpolator
            r1.<init>()
            goto L5
        L5a:
            java.lang.String r3 = "cycleInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L68
            android.view.animation.CycleInterpolator r2 = new android.view.animation.CycleInterpolator
            r2.<init>(r4, r5, r1)
            goto L3c
        L68:
            java.lang.String r3 = "anticipateInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L76
            android.view.animation.AnticipateInterpolator r2 = new android.view.animation.AnticipateInterpolator
            r2.<init>(r4, r5, r1)
            goto L3c
        L76:
            java.lang.String r3 = "overshootInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L85
            android.view.animation.OvershootInterpolator r2 = new android.view.animation.OvershootInterpolator
            r2.<init>(r4, r5, r1)
            goto L3c
        L85:
            java.lang.String r3 = "anticipateOvershootInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L93
            android.view.animation.AnticipateOvershootInterpolator r2 = new android.view.animation.AnticipateOvershootInterpolator
            r2.<init>(r4, r5, r1)
            goto L3c
        L93:
            java.lang.String r3 = "bounceInterpolator"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto La2
            android.view.animation.BounceInterpolator r1 = new android.view.animation.BounceInterpolator
            r1.<init>()
            goto L5
        La2:
            java.lang.String r3 = "pathInterpolator"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto Lb1
            android.view.animation.PathInterpolator r2 = new android.view.animation.PathInterpolator
            r2.<init>(r4, r5, r1)
            goto L3c
        Lb1:
            android.view.InflateException r4 = new android.view.InflateException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "Unknown interpolator name: "
            r5.<init>(r0)
            java.lang.String r6 = r6.getName()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        Lc9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.animation.AnimationUtils.createInterpolatorFromXml(android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser):android.view.animation.Interpolator");
    }
}
