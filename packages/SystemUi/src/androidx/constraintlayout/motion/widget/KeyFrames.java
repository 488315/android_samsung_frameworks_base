package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyFrames {
    public static final HashMap sKeyMakers;
    public final HashMap mFramesMap = new HashMap();

    static {
        HashMap hashMap = new HashMap();
        sKeyMakers = hashMap;
        try {
            hashMap.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
            hashMap.put("KeyPosition", KeyPosition.class.getConstructor(new Class[0]));
            hashMap.put("KeyCycle", KeyCycle.class.getConstructor(new Class[0]));
            hashMap.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
            hashMap.put("KeyTrigger", KeyTrigger.class.getConstructor(new Class[0]));
        } catch (NoSuchMethodException e) {
            Log.e("KeyFrames", "unable to load", e);
        }
    }

    public KeyFrames() {
    }

    public final void addFrames(MotionController motionController) {
        Integer valueOf = Integer.valueOf(motionController.mId);
        HashMap hashMap = this.mFramesMap;
        ArrayList arrayList = (ArrayList) hashMap.get(valueOf);
        if (arrayList != null) {
            motionController.mKeyList.addAll(arrayList);
        }
        ArrayList arrayList2 = (ArrayList) hashMap.get(-1);
        if (arrayList2 != null) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                Key key = (Key) it.next();
                String str = ((ConstraintLayout.LayoutParams) motionController.mView.getLayoutParams()).constraintTag;
                String str2 = key.mTargetString;
                if ((str2 == null || str == null) ? false : str.matches(str2)) {
                    motionController.addKey(key);
                }
            }
        }
    }

    public final void addKey(Key key) {
        Integer valueOf = Integer.valueOf(key.mTargetId);
        HashMap hashMap = this.mFramesMap;
        if (!hashMap.containsKey(valueOf)) {
            hashMap.put(Integer.valueOf(key.mTargetId), new ArrayList());
        }
        ArrayList arrayList = (ArrayList) hashMap.get(Integer.valueOf(key.mTargetId));
        if (arrayList != null) {
            arrayList.add(key);
        }
    }

    public KeyFrames(Context context, XmlPullParser xmlPullParser) {
        Exception e;
        Key key;
        Constructor constructor;
        HashMap hashMap;
        HashMap hashMap2;
        try {
            int eventType = xmlPullParser.getEventType();
            Key key2 = null;
            while (eventType != 1) {
                if (eventType != 2) {
                    if (eventType == 3 && "KeyFrameSet".equals(xmlPullParser.getName())) {
                        return;
                    }
                } else {
                    String name = xmlPullParser.getName();
                    HashMap hashMap3 = sKeyMakers;
                    if (hashMap3.containsKey(name)) {
                        try {
                            constructor = (Constructor) hashMap3.get(name);
                        } catch (Exception e2) {
                            Key key3 = key2;
                            e = e2;
                            key = key3;
                        }
                        if (constructor != null) {
                            key = (Key) constructor.newInstance(new Object[0]);
                            try {
                                key.load(context, Xml.asAttributeSet(xmlPullParser));
                                addKey(key);
                            } catch (Exception e3) {
                                e = e3;
                                Log.e("KeyFrames", "unable to create ", e);
                                key2 = key;
                                eventType = xmlPullParser.next();
                            }
                            key2 = key;
                        } else {
                            throw new NullPointerException("Keymaker for " + name + " not found");
                        }
                    } else if (name.equalsIgnoreCase("CustomAttribute")) {
                        if (key2 != null && (hashMap2 = key2.mCustomConstraints) != null) {
                            ConstraintAttribute.parse(context, xmlPullParser, hashMap2);
                        }
                    } else if (name.equalsIgnoreCase("CustomMethod") && key2 != null && (hashMap = key2.mCustomConstraints) != null) {
                        ConstraintAttribute.parse(context, xmlPullParser, hashMap);
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        } catch (XmlPullParserException e5) {
            e5.printStackTrace();
        }
    }
}
