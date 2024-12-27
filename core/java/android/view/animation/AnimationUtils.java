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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class AnimationUtils {
    private static final int SEQUENTIALLY = 1;
    private static final String TAG = "AnimationUtils";
    private static final int TOGETHER = 0;
    private static boolean sExpectedPresentationTimeFlagValue =
            Flags.expectedPresentationTimeReadOnly();
    private static ThreadLocal<AnimationState> sAnimationState =
            new ThreadLocal<
                    AnimationState>() { // from class: android.view.animation.AnimationUtils.1
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

        private AnimationState() {}
    }

    public static void lockAnimationClock(long vsyncMillis, long expectedPresentationTimeNanos) {
        AnimationState state = sAnimationState.get();
        state.animationClockLocked = true;
        state.currentVsyncTimeMillis = vsyncMillis;
        if (!sExpectedPresentationTimeFlagValue) {
            state.mExpectedPresentationTimeNanos = expectedPresentationTimeNanos;
        }
    }

    public static void lockAnimationClock(long vsyncMillis) {
        AnimationState state = sAnimationState.get();
        state.animationClockLocked = true;
        state.currentVsyncTimeMillis = vsyncMillis;
    }

    public static void unlockAnimationClock() {
        sAnimationState.get().animationClockLocked = false;
    }

    public static long currentAnimationTimeMillis() {
        AnimationState state = sAnimationState.get();
        if (state.animationClockLocked) {
            return Math.max(state.currentVsyncTimeMillis, state.lastReportedTimeMillis);
        }
        state.lastReportedTimeMillis = SystemClock.uptimeMillis();
        return state.lastReportedTimeMillis;
    }

    public static long getExpectedPresentationTimeNanos() {
        if (!sExpectedPresentationTimeFlagValue) {
            return SystemClock.uptimeMillis() * 1000000;
        }
        AnimationState state = sAnimationState.get();
        return state.mExpectedPresentationTimeNanos;
    }

    public static long getExpectedPresentationTimeMillis() {
        return getExpectedPresentationTimeNanos() / 1000000;
    }

    public static Animation loadAnimation(Context context, int id)
            throws Resources.NotFoundException {
        String resourceName;
        XmlResourceParser parser = null;
        try {
            try {
                parser = context.getResources().getAnimation(id);
                try {
                    return createAnimationFromXml(context, parser);
                } catch (RuntimeException re) {
                    Log.e(
                            TAG,
                            "RuntimeException for unknown animation name, resouce ID #0x"
                                    + Integer.toHexString(id));
                    Log.e(
                            TAG,
                            "loadAnimation: getConfiguration = "
                                    + context.getResources().getConfiguration());
                    Log.e(
                            TAG,
                            "loadAnimation: getDisplayMetrics = "
                                    + context.getResources().getDisplayMetrics());
                    try {
                        resourceName = context.getResources().getResourceName(id);
                    } catch (Resources.NotFoundException e) {
                        resourceName = "unknown";
                    }
                    Log.e(TAG, "loadAnimation: resourceName = " + resourceName);
                    throw re;
                }
            } catch (IOException | XmlPullParserException ex) {
                throw new Resources.NotFoundException(
                        "Can't load animation resource ID #0x" + Integer.toHexString(id), ex);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    private static Animation createAnimationFromXml(Context c, XmlPullParser parser)
            throws XmlPullParserException, IOException {
        return createAnimationFromXml(c, parser, null, Xml.asAttributeSet(parser));
    }

    private static Animation createAnimationFromXml(
            Context c, XmlPullParser parser, AnimationSet parent, AttributeSet attrs)
            throws XmlPullParserException, IOException, InflateException {
        Animation anim = null;
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if (name.equals("set")) {
                        anim = new AnimationSet(c, attrs);
                        createAnimationFromXml(c, parser, (AnimationSet) anim, attrs);
                    } else if (name.equals("alpha")) {
                        anim = new AlphaAnimation(c, attrs);
                    } else if (name.equals("scale")) {
                        anim = new ScaleAnimation(c, attrs);
                    } else if (name.equals("rotate")) {
                        anim = new RotateAnimation(c, attrs);
                    } else if (name.equals("translate")) {
                        anim = new TranslateAnimation(c, attrs);
                    } else if (name.equals("cliprect")) {
                        anim = new ClipRectAnimation(c, attrs);
                    } else if (name.equals("extend")) {
                        anim = new ExtendAnimation(c, attrs);
                    } else {
                        throw new InflateException("Unknown animation name: " + parser.getName());
                    }
                    if (parent != null) {
                        parent.addAnimation(anim);
                    }
                }
            }
        }
        return anim;
    }

    public static LayoutAnimationController loadLayoutAnimation(Context context, int id)
            throws Resources.NotFoundException {
        XmlResourceParser parser = null;
        try {
            try {
                parser = context.getResources().getAnimation(id);
                return createLayoutAnimationFromXml(context, parser);
            } catch (InflateException | IOException | XmlPullParserException ex) {
                throw new Resources.NotFoundException(
                        "Can't load animation resource ID #0x" + Integer.toHexString(id), ex);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(
            Context c, XmlPullParser parser)
            throws XmlPullParserException, IOException, InflateException {
        return createLayoutAnimationFromXml(c, parser, Xml.asAttributeSet(parser));
    }

    private static LayoutAnimationController createLayoutAnimationFromXml(
            Context c, XmlPullParser parser, AttributeSet attrs)
            throws XmlPullParserException, IOException, InflateException {
        LayoutAnimationController controller = null;
        int depth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if ((type != 3 || parser.getDepth() > depth) && type != 1) {
                if (type == 2) {
                    String name = parser.getName();
                    if ("layoutAnimation".equals(name)) {
                        controller = new LayoutAnimationController(c, attrs);
                    } else if ("gridLayoutAnimation".equals(name)) {
                        controller = new GridLayoutAnimationController(c, attrs);
                    } else {
                        throw new InflateException("Unknown layout animation name: " + name);
                    }
                }
            }
        }
        return controller;
    }

    public static Animation makeInAnimation(Context c, boolean fromLeft) {
        Animation a;
        if (fromLeft) {
            a = loadAnimation(c, 17432578);
        } else {
            a = loadAnimation(c, R.anim.slide_in_right);
        }
        a.setInterpolator(new DecelerateInterpolator());
        a.setStartTime(currentAnimationTimeMillis());
        return a;
    }

    public static Animation makeOutAnimation(Context c, boolean toRight) {
        Animation a;
        if (toRight) {
            a = loadAnimation(c, 17432579);
        } else {
            a = loadAnimation(c, R.anim.slide_out_left);
        }
        a.setInterpolator(new AccelerateInterpolator());
        a.setStartTime(currentAnimationTimeMillis());
        return a;
    }

    public static Animation makeInChildBottomAnimation(Context c) {
        Animation a = loadAnimation(c, R.anim.slide_in_child_bottom);
        a.setInterpolator(new AccelerateInterpolator());
        a.setStartTime(currentAnimationTimeMillis());
        return a;
    }

    public static Interpolator loadInterpolator(Context context, int id)
            throws Resources.NotFoundException {
        XmlResourceParser parser = null;
        try {
            try {
                parser = context.getResources().getAnimation(id);
                return createInterpolatorFromXml(
                        context.getResources(), context.getTheme(), parser);
            } catch (InflateException | IOException | XmlPullParserException ex) {
                throw new Resources.NotFoundException(
                        "Can't load animation resource ID #0x" + Integer.toHexString(id), ex);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    public static Interpolator loadInterpolator(Resources res, Resources.Theme theme, int id)
            throws Resources.NotFoundException {
        XmlResourceParser parser = null;
        try {
            try {
                parser = res.getAnimation(id);
                return createInterpolatorFromXml(res, theme, parser);
            } catch (InflateException | IOException | XmlPullParserException ex) {
                throw new Resources.NotFoundException(
                        "Can't load animation resource ID #0x" + Integer.toHexString(id), ex);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x00db, code lost:

       return r0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.view.animation.Interpolator createInterpolatorFromXml(
            android.content.res.Resources r8,
            android.content.res.Resources.Theme r9,
            org.xmlpull.v1.XmlPullParser r10)
            throws org.xmlpull.v1.XmlPullParserException,
                    java.io.IOException,
                    android.view.InflateException {
        /*
            r0 = 0
            int r1 = r10.getDepth()
        L5:
            int r2 = r10.next()
            r3 = r2
            r4 = 3
            if (r2 != r4) goto L13
            int r2 = r10.getDepth()
            if (r2 <= r1) goto Ldb
        L13:
            r2 = 1
            if (r3 == r2) goto Ldb
            r2 = 2
            if (r3 == r2) goto L1a
            goto L5
        L1a:
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r10)
            java.lang.String r4 = r10.getName()
            java.lang.String r5 = "linearInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L32
            android.view.animation.LinearInterpolator r5 = new android.view.animation.LinearInterpolator
            r5.<init>()
            r0 = r5
            goto Lbc
        L32:
            java.lang.String r5 = "accelerateInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L42
            android.view.animation.AccelerateInterpolator r5 = new android.view.animation.AccelerateInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
            goto Lbc
        L42:
            java.lang.String r5 = "decelerateInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L52
            android.view.animation.DecelerateInterpolator r5 = new android.view.animation.DecelerateInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
            goto Lbc
        L52:
            java.lang.String r5 = "accelerateDecelerateInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L61
            android.view.animation.AccelerateDecelerateInterpolator r5 = new android.view.animation.AccelerateDecelerateInterpolator
            r5.<init>()
            r0 = r5
            goto Lbc
        L61:
            java.lang.String r5 = "cycleInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L70
            android.view.animation.CycleInterpolator r5 = new android.view.animation.CycleInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
            goto Lbc
        L70:
            java.lang.String r5 = "anticipateInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L7f
            android.view.animation.AnticipateInterpolator r5 = new android.view.animation.AnticipateInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
            goto Lbc
        L7f:
            java.lang.String r5 = "overshootInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L8f
            android.view.animation.OvershootInterpolator r5 = new android.view.animation.OvershootInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
            goto Lbc
        L8f:
            java.lang.String r5 = "anticipateOvershootInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L9e
            android.view.animation.AnticipateOvershootInterpolator r5 = new android.view.animation.AnticipateOvershootInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
            goto Lbc
        L9e:
            java.lang.String r5 = "bounceInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto Lad
            android.view.animation.BounceInterpolator r5 = new android.view.animation.BounceInterpolator
            r5.<init>()
            r0 = r5
            goto Lbc
        Lad:
            java.lang.String r5 = "pathInterpolator"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto Lbe
            android.view.animation.PathInterpolator r5 = new android.view.animation.PathInterpolator
            r5.<init>(r8, r9, r2)
            r0 = r5
        Lbc:
            goto L5
        Lbe:
            android.view.InflateException r5 = new android.view.InflateException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unknown interpolator name: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = r10.getName()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        Ldb:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.animation.AnimationUtils.createInterpolatorFromXml(android.content.res.Resources,"
                    + " android.content.res.Resources$Theme,"
                    + " org.xmlpull.v1.XmlPullParser):android.view.animation.Interpolator");
    }
}
