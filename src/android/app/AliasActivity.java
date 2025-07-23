package android.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
/* loaded from: classes.dex */
public class AliasActivity extends Activity {
    public final String ALIAS_META_DATA = "android.app.alias";

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData = getPackageManager().getActivityInfo(getComponentName(), 128).loadXmlMetaData(getPackageManager(), "android.app.alias");
                if (loadXmlMetaData == null) {
                    throw new RuntimeException("Alias requires a meta-data field android.app.alias");
                }
                Intent parseAlias = parseAlias(loadXmlMetaData);
                if (parseAlias == null) {
                    throw new RuntimeException("No <intent> tag found in alias description");
                }
                startActivity(parseAlias);
                finish();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
            } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
                throw new RuntimeException("Error parsing alias", e);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    private Intent parseAlias(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 1) {
                break;
            }
        } while (next != 2);
        String name = xmlPullParser.getName();
        if (!"alias".equals(name)) {
            throw new RuntimeException("Alias meta-data must start with <alias> tag; found" + name + " at " + xmlPullParser.getPositionDescription());
        }
        int depth = xmlPullParser.getDepth();
        Intent intent = null;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1 || (next2 == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 != 3 && next2 != 4) {
                if ("intent".equals(xmlPullParser.getName())) {
                    Intent parseIntent = Intent.parseIntent(getResources(), xmlPullParser, asAttributeSet);
                    if (intent == null) {
                        intent = parseIntent;
                    }
                } else {
                    XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return intent;
    }
}
