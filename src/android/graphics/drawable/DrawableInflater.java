package android.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.InflateException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class DrawableInflater {
    private static final HashMap<String, Constructor<? extends Drawable>> CONSTRUCTOR_MAP = new HashMap<>();
    private final ClassLoader mClassLoader;
    private final Resources mRes;

    public static Drawable loadDrawable(Context context, int i) {
        return loadDrawable(context.getResources(), context.getTheme(), i);
    }

    public static Drawable loadDrawable(Resources resources, Resources.Theme theme, int i) {
        return resources.getDrawable(i, theme);
    }

    public DrawableInflater(Resources resources, ClassLoader classLoader) {
        this.mRes = resources;
        this.mClassLoader = classLoader;
    }

    public Drawable inflateFromXml(String str, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        return inflateFromXmlForDensity(str, xmlPullParser, attributeSet, 0, theme);
    }

    Drawable inflateFromXmlForDensity(String str, XmlPullParser xmlPullParser, AttributeSet attributeSet, int i, Resources.Theme theme) throws XmlPullParserException, IOException {
        if (str.equals("drawable") && (str = attributeSet.getAttributeValue(null, "class")) == null) {
            throw new InflateException("<drawable> tag must specify class attribute");
        }
        Drawable inflateSpr = inflateSpr(str, xmlPullParser, attributeSet);
        if (inflateSpr == null) {
            inflateSpr = inflateFromTag(str);
        }
        if (inflateSpr == null) {
            inflateSpr = inflateFromClass(str);
        }
        inflateSpr.setSrcDensityOverride(i);
        inflateSpr.inflate(this.mRes, xmlPullParser, attributeSet, theme);
        return inflateSpr;
    }

    private Drawable inflateFromTag(String str) {
        str.hashCode();
        switch (str) {
            case "adaptive-icon":
                return new AdaptiveIconDrawable();
            case "transition":
                return new TransitionDrawable();
            case "nine-patch":
                return new NinePatchDrawable();
            case "animation-list":
                return new AnimationDrawable();
            case "bitmap":
                return new BitmapDrawable();
            case "ripple":
                return new RippleDrawable();
            case "rotate":
                return new RotateDrawable();
            case "vector":
                return new VectorDrawable();
            case "animated-selector":
                return new AnimatedStateListDrawable();
            case "layer-list":
                return new LayerDrawable();
            case "clip":
                return new ClipDrawable();
            case "color":
                return new ColorDrawable();
            case "inset":
                return new InsetDrawable();
            case "scale":
                return new ScaleDrawable();
            case "shape":
                return new GradientDrawable();
            case "level-list":
                return new LevelListDrawable();
            case "selector":
                return new StateListDrawable();
            case "animated-image":
                return new AnimatedImageDrawable();
            case "animated-rotate":
                return new AnimatedRotateDrawable();
            case "animated-vector":
                return new AnimatedVectorDrawable();
            default:
                return null;
        }
    }

    private Drawable inflateFromClass(String str) {
        Constructor<? extends Drawable> constructor;
        try {
            HashMap<String, Constructor<? extends Drawable>> hashMap = CONSTRUCTOR_MAP;
            synchronized (hashMap) {
                constructor = hashMap.get(str);
                if (constructor == null) {
                    Class[] clsArr = new Class[0];
                    constructor = this.mClassLoader.loadClass(str).asSubclass(Drawable.class).getConstructor(null);
                    hashMap.put(str, constructor);
                }
            }
            return constructor.newInstance(null);
        } catch (ClassCastException e) {
            InflateException inflateException = new InflateException("Class is not a Drawable " + str);
            inflateException.initCause(e);
            throw inflateException;
        } catch (ClassNotFoundException e2) {
            InflateException inflateException2 = new InflateException("Class not found " + str);
            inflateException2.initCause(e2);
            throw inflateException2;
        } catch (NoSuchMethodException e3) {
            InflateException inflateException3 = new InflateException("Error inflating class " + str);
            inflateException3.initCause(e3);
            throw inflateException3;
        } catch (Exception e4) {
            InflateException inflateException4 = new InflateException("Error inflating class " + str);
            inflateException4.initCause(e4);
            throw inflateException4;
        }
    }

    private Drawable inflateSpr(String str, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int attributeResourceValue;
        InputStream openRawResource;
        InputStream inputStream = null;
        if (!"bitmap".equalsIgnoreCase(str) || (attributeResourceValue = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0)) == 0) {
            return null;
        }
        byte[] bArr = new byte[3];
        try {
            openRawResource = this.mRes.openRawResource(attributeResourceValue);
        } catch (Throwable th) {
            th = th;
        }
        try {
            openRawResource.read(bArr, 0, 3);
            if (openRawResource != null) {
                openRawResource.close();
            }
            if (bArr[0] != 83 || bArr[1] != 80 || bArr[2] != 82) {
                return null;
            }
            try {
                return (Drawable) Class.forName("com.samsung.android.graphics.spr.SemPathRenderingDrawable").newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": unable to load spr." + str);
            }
        } catch (Throwable th2) {
            th = th2;
            inputStream = openRawResource;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }
}
