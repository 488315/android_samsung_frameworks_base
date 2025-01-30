package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.p009os.BatteryManager;
import android.p009os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import com.android.internal.C4337R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class AnimationUtils {
  private static final int SEQUENTIALLY = 1;
  private static final String TAG = "AnimationUtils";
  private static final int TOGETHER = 0;
  private static ThreadLocal<AnimationState> sAnimationState =
      new ThreadLocal<AnimationState>() { // from class: android.view.animation.AnimationUtils.1
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

    private AnimationState() {}
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
          Log.m96e(
              TAG,
              "RuntimeException for unknown animation name, resouce ID #0x"
                  + Integer.toHexString(id));
          Log.m96e(
              TAG,
              "loadAnimation: getConfiguration = " + context.getResources().getConfiguration());
          Log.m96e(
              TAG,
              "loadAnimation: getDisplayMetrics = " + context.getResources().getDisplayMetrics());
          try {
            resourceName = context.getResources().getResourceName(id);
          } catch (Resources.NotFoundException e) {
            resourceName = "unknown";
          }
          Log.m96e(TAG, "loadAnimation: resourceName = " + resourceName);
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
          } else if (name.equals(BatteryManager.EXTRA_SCALE)) {
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
      a = loadAnimation(c, C4337R.anim.slide_in_right);
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
      a = loadAnimation(c, C4337R.anim.slide_out_left);
    }
    a.setInterpolator(new AccelerateInterpolator());
    a.setStartTime(currentAnimationTimeMillis());
    return a;
  }

  public static Animation makeInChildBottomAnimation(Context c) {
    Animation a = loadAnimation(c, C4337R.anim.slide_in_child_bottom);
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
        return createInterpolatorFromXml(context.getResources(), context.getTheme(), parser);
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
  */
  private static Interpolator createInterpolatorFromXml(
      Resources res, Resources.Theme theme, XmlPullParser parser)
      throws XmlPullParserException, IOException, InflateException {
    BaseInterpolator interpolator = null;
    int depth = parser.getDepth();
    while (true) {
      int type = parser.next();
      if ((type != 3 || parser.getDepth() > depth) && type != 1) {
        if (type == 2) {
          AttributeSet attrs = Xml.asAttributeSet(parser);
          String name = parser.getName();
          if (name.equals("linearInterpolator")) {
            interpolator = new LinearInterpolator();
          } else if (name.equals("accelerateInterpolator")) {
            interpolator = new AccelerateInterpolator(res, theme, attrs);
          } else if (name.equals("decelerateInterpolator")) {
            interpolator = new DecelerateInterpolator(res, theme, attrs);
          } else if (name.equals("accelerateDecelerateInterpolator")) {
            interpolator = new AccelerateDecelerateInterpolator();
          } else if (name.equals("cycleInterpolator")) {
            interpolator = new CycleInterpolator(res, theme, attrs);
          } else if (name.equals("anticipateInterpolator")) {
            interpolator = new AnticipateInterpolator(res, theme, attrs);
          } else if (name.equals("overshootInterpolator")) {
            interpolator = new OvershootInterpolator(res, theme, attrs);
          } else if (name.equals("anticipateOvershootInterpolator")) {
            interpolator = new AnticipateOvershootInterpolator(res, theme, attrs);
          } else if (name.equals("bounceInterpolator")) {
            interpolator = new BounceInterpolator();
          } else if (name.equals("pathInterpolator")) {
            interpolator = new PathInterpolator(res, theme, attrs);
          } else {
            throw new InflateException("Unknown interpolator name: " + parser.getName());
          }
        }
      }
    }
  }
}
