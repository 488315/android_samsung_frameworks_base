package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ShortcutParser {
    public AttributeSet mAttrs;
    public final Context mContext;
    public final String mName;
    public final String mPkg;
    public final int mResId;
    public Resources mResources;

    public class Shortcut {
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
    public ShortcutParser(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();
        ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, 128);
        Bundle bundle = activityInfo.metaData;
        this(context, packageName, className, (bundle == null || !bundle.containsKey("android.app.shortcuts")) ? 0 : activityInfo.metaData.getInt("android.app.shortcuts"));
    }

    public final List getShortcuts() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        Shortcut shortcut;
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
                    if (next == 2 && xml.getName().equals("shortcut") && (shortcut = parseShortcut(xml)) != null) {
                        arrayList.add(shortcut);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public final Shortcut parseShortcut(XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = this.mResources.obtainAttributes(this.mAttrs, R.styleable.Shortcut);
        Shortcut shortcut = new Shortcut();
        if (!typedArrayObtainAttributes.getBoolean(1, true)) {
            return null;
        }
        String string = typedArrayObtainAttributes.getString(2);
        int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
        int resourceId2 = typedArrayObtainAttributes.getResourceId(3, 0);
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
