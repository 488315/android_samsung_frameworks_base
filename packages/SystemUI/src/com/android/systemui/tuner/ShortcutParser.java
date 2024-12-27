package com.android.systemui.tuner;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

public final class ShortcutParser {
    public AttributeSet mAttrs;
    public final Context mContext;
    public final String mName;
    public final String mPkg;
    public final int mResId;
    public Resources mResources;

    public final class Shortcut {
        public Icon icon;
        public String id;
        public Intent intent;
        public String label;
        public String name;
        public String pkg;

        public final String toString() {
            return this.pkg + "::" + this.name + "::" + this.id;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ShortcutParser(android.content.Context r5, android.content.ComponentName r6) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            r4 = this;
            java.lang.String r0 = r6.getPackageName()
            java.lang.String r1 = r6.getClassName()
            android.content.pm.PackageManager r2 = r5.getPackageManager()
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ActivityInfo r6 = r2.getActivityInfo(r6, r3)
            android.os.Bundle r2 = r6.metaData
            if (r2 == 0) goto L25
            java.lang.String r3 = "android.app.shortcuts"
            boolean r2 = r2.containsKey(r3)
            if (r2 == 0) goto L25
            android.os.Bundle r6 = r6.metaData
            int r6 = r6.getInt(r3)
            goto L26
        L25:
            r6 = 0
        L26:
            r4.<init>(r5, r0, r1, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.tuner.ShortcutParser.<init>(android.content.Context, android.content.ComponentName):void");
    }

    public final List getShortcuts() {
        Shortcut parseShortcut;
        ArrayList arrayList = new ArrayList();
        int i = this.mResId;
        if (i != 0) {
            try {
                Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(this.mPkg);
                this.mResources = resourcesForApplication;
                XmlResourceParser xml = resourcesForApplication.getXml(i);
                this.mAttrs = Xml.asAttributeSet(xml);
                while (true) {
                    int next = xml.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2 && xml.getName().equals("shortcut") && (parseShortcut = parseShortcut(xml)) != null) {
                        arrayList.add(parseShortcut);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public final Shortcut parseShortcut(XmlResourceParser xmlResourceParser) {
        TypedArray obtainAttributes = this.mResources.obtainAttributes(this.mAttrs, R.styleable.Shortcut);
        Shortcut shortcut = new Shortcut();
        if (!obtainAttributes.getBoolean(1, true)) {
            return null;
        }
        String string = obtainAttributes.getString(2);
        int resourceId = obtainAttributes.getResourceId(0, 0);
        int resourceId2 = obtainAttributes.getResourceId(3, 0);
        String str = this.mPkg;
        shortcut.pkg = str;
        shortcut.icon = Icon.createWithResource(str, resourceId);
        shortcut.id = string;
        shortcut.label = this.mResources.getString(resourceId2);
        shortcut.name = this.mName;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 3) {
                break;
            }
            if (next == 2 && xmlResourceParser.getName().equals("intent")) {
                shortcut.intent = Intent.parseIntent(this.mResources, xmlResourceParser, this.mAttrs);
            }
        }
        if (shortcut.intent != null) {
            return shortcut;
        }
        return null;
    }

    public ShortcutParser(Context context, String str, String str2, int i) {
        this.mContext = context;
        this.mPkg = str;
        this.mResId = i;
        this.mName = str2;
    }
}
