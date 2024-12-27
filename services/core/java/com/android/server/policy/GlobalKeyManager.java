package com.android.server.policy;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GlobalKeyManager {
    public final SparseArray mKeyMapping = new SparseArray();
    public boolean mBeganFromNonInteractive = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GlobalKeyAction {
        public final ComponentName mComponentName;
        public final boolean mDispatchWhenNonInteractive;

        public GlobalKeyAction(String str, String str2) {
            this.mComponentName = ComponentName.unflattenFromString(str);
            this.mDispatchWhenNonInteractive = Boolean.parseBoolean(str2);
        }
    }

    public GlobalKeyManager(Context context) {
        try {
            XmlResourceParser xml = context.getResources().getXml(R.xml.password_kbd_qwerty);
            try {
                XmlUtils.beginDocument(xml, "global_keys");
                if (1 == xml.getAttributeIntValue(null, "version", 0)) {
                    while (true) {
                        XmlUtils.nextElement(xml);
                        String name = xml.getName();
                        if (name == null) {
                            break;
                        }
                        if ("key".equals(name)) {
                            String attributeValue = xml.getAttributeValue(null, "keyCode");
                            String attributeValue2 = xml.getAttributeValue(null, "component");
                            String attributeValue3 = xml.getAttributeValue(null, "dispatchWhenNonInteractive");
                            if (attributeValue != null && attributeValue2 != null) {
                                int keyCodeFromString = KeyEvent.keyCodeFromString(attributeValue);
                                if (keyCodeFromString != 0) {
                                    this.mKeyMapping.put(keyCodeFromString, new GlobalKeyAction(attributeValue2, attributeValue3));
                                } else {
                                    Log.wtf("GlobalKeyManager", "Global keys entry does not map to a valid key code: " + attributeValue);
                                }
                            }
                            Log.wtf("GlobalKeyManager", "Failed to parse global keys entry: " + xml.getText());
                        }
                    }
                }
                xml.close();
            } catch (Throwable th) {
                if (xml != null) {
                    try {
                        xml.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Resources.NotFoundException e) {
            Log.wtf("GlobalKeyManager", "global keys file not found", e);
        } catch (IOException e2) {
            Log.e("GlobalKeyManager", "I/O exception reading global keys file", e2);
        } catch (XmlPullParserException e3) {
            Log.wtf("GlobalKeyManager", "XML parser exception reading global keys file", e3);
        }
    }
}
