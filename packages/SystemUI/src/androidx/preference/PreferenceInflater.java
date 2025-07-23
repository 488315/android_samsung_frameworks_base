package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PreferenceInflater {
    public final Context mContext;
    public final PreferenceManager mPreferenceManager;
    public static final Class[] CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    public static final HashMap CONSTRUCTOR_MAP = new HashMap();
    public final Object[] mConstructorArgs = new Object[2];
    public final String[] mDefaultPackages = {Preference.class.getPackage().getName() + ".", SwitchPreference.class.getPackage().getName() + "."};

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        this.mContext = context;
        this.mPreferenceManager = preferenceManager;
    }

    public final Preference createItem(String str, String[] strArr, AttributeSet attributeSet) {
        Class<?> cls;
        Constructor<?> constructor = (Constructor) CONSTRUCTOR_MAP.get(str);
        if (constructor == null) {
            try {
                try {
                    ClassLoader classLoader = this.mContext.getClassLoader();
                    if (strArr != null && strArr.length != 0) {
                        cls = null;
                        ClassNotFoundException e = null;
                        for (String str2 : strArr) {
                            try {
                                cls = Class.forName(str2 + str, false, classLoader);
                                break;
                            } catch (ClassNotFoundException e2) {
                                e = e2;
                            }
                        }
                        if (cls == null) {
                            if (e != null) {
                                throw e;
                            }
                            throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
                        }
                        constructor = cls.getConstructor(CONSTRUCTOR_SIGNATURE);
                        constructor.setAccessible(true);
                        CONSTRUCTOR_MAP.put(str, constructor);
                    }
                    cls = Class.forName(str, false, classLoader);
                    constructor = cls.getConstructor(CONSTRUCTOR_SIGNATURE);
                    constructor.setAccessible(true);
                    CONSTRUCTOR_MAP.put(str, constructor);
                } catch (Exception e3) {
                    InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
                    inflateException.initCause(e3);
                    throw inflateException;
                }
            } catch (ClassNotFoundException e4) {
                throw e4;
            }
        }
        Object[] objArr = this.mConstructorArgs;
        objArr[1] = attributeSet;
        return (Preference) constructor.newInstance(objArr);
    }

    public final Preference createItemFromTag(String str, AttributeSet attributeSet) {
        try {
            return -1 == str.indexOf(46) ? createItem(str, this.mDefaultPackages, attributeSet) : createItem(str, null, attributeSet);
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class (not found)" + str);
            inflateException.initCause(e2);
            throw inflateException;
        } catch (Exception e3) {
            InflateException inflateException2 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
            inflateException2.initCause(e3);
            throw inflateException2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v4, types: [androidx.preference.Preference, androidx.preference.PreferenceGroup] */
    public final PreferenceGroup inflate(XmlPullParser xmlPullParser, PreferenceScreen preferenceScreen) {
        int next;
        synchronized (this.mConstructorArgs) {
            AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
            this.mConstructorArgs[0] = this.mContext;
            do {
                try {
                    try {
                        try {
                            next = xmlPullParser.next();
                            if (next == 2) {
                                break;
                            }
                        } catch (XmlPullParserException e) {
                            InflateException inflateException = new InflateException(e.getMessage());
                            inflateException.initCause(e);
                            throw inflateException;
                        }
                    } catch (InflateException e2) {
                        throw e2;
                    }
                } catch (IOException e3) {
                    InflateException inflateException2 = new InflateException(xmlPullParser.getPositionDescription() + ": " + e3.getMessage());
                    inflateException2.initCause(e3);
                    throw inflateException2;
                }
            } while (next != 1);
            if (next != 2) {
                throw new InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
            }
            ?? r2 = (PreferenceGroup) createItemFromTag(xmlPullParser.getName(), asAttributeSet);
            if (preferenceScreen == null) {
                r2.onAttachedToHierarchy(this.mPreferenceManager);
                preferenceScreen = r2;
            }
            rInflate(xmlPullParser, preferenceScreen, asAttributeSet);
        }
        return preferenceScreen;
    }

    public final void rInflate(XmlPullParser xmlPullParser, Preference preference, AttributeSet attributeSet) {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                String name = xmlPullParser.getName();
                if ("intent".equals(name)) {
                    try {
                        preference.mIntent = Intent.parseIntent(this.mContext.getResources(), xmlPullParser, attributeSet);
                    } catch (IOException e) {
                        XmlPullParserException xmlPullParserException = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException.initCause(e);
                        throw xmlPullParserException;
                    }
                } else if ("extra".equals(name)) {
                    Resources resources = this.mContext.getResources();
                    if (preference.mExtras == null) {
                        preference.mExtras = new Bundle();
                    }
                    resources.parseBundleExtra("extra", attributeSet, preference.mExtras);
                    try {
                        int depth2 = xmlPullParser.getDepth();
                        while (true) {
                            int next2 = xmlPullParser.next();
                            if (next2 != 1 && (next2 != 3 || xmlPullParser.getDepth() > depth2)) {
                            }
                        }
                    } catch (IOException e2) {
                        XmlPullParserException xmlPullParserException2 = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException2.initCause(e2);
                        throw xmlPullParserException2;
                    }
                } else {
                    Preference createItemFromTag = createItemFromTag(name, attributeSet);
                    ((PreferenceGroup) preference).addPreference(createItemFromTag);
                    rInflate(xmlPullParser, createItemFromTag, attributeSet);
                }
            }
        }
    }
}
