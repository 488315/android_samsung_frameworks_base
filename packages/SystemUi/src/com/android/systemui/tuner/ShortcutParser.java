package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ShortcutParser {
    public AttributeSet mAttrs;
    public final Context mContext;
    public final String mName;
    public final String mPkg;
    public final int mResId;
    public Resources mResources;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Shortcut {
        public Icon icon;

        /* renamed from: id */
        public String f363id;
        public Intent intent;
        public String label;
        public String name;
        public String pkg;

        public final String toString() {
            return this.pkg + "::" + this.name + "::" + this.f363id;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ShortcutParser(Context context, ComponentName componentName) {
        this(context, r0, r1, (r2 == null || !r2.containsKey("android.app.shortcuts")) ? 0 : r6.metaData.getInt("android.app.shortcuts"));
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();
        ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(componentName, 128);
        Bundle bundle = activityInfo.metaData;
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
        shortcut.f363id = string;
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
