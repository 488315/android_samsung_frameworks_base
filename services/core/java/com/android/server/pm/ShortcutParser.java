package com.android.server.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R;
import com.android.server.pm.ShareTargetInfo;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ShortcutParser {
    static final String METADATA_KEY = "android.app.shortcuts";

    public static ShareTargetInfo parseShareTargetAttributes(ShortcutService shortcutService, AttributeSet attributeSet) {
        TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, R.styleable.Intent);
        try {
            String string = obtainAttributes.getString(4);
            if (!TextUtils.isEmpty(string)) {
                return new ShareTargetInfo(null, string, null);
            }
            Log.w("ShortcutService", "android:targetClass must be provided.");
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static ShareTargetInfo.TargetData parseShareTargetData(ShortcutService shortcutService, AttributeSet attributeSet) {
        TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, R.styleable.AndroidManifestData);
        try {
            if (obtainAttributes.getType(0) == 3) {
                return new ShareTargetInfo.TargetData(obtainAttributes.getString(1), obtainAttributes.getString(2), obtainAttributes.getString(3), obtainAttributes.getString(4), obtainAttributes.getString(6), obtainAttributes.getString(5), obtainAttributes.getString(0));
            }
            Log.w("ShortcutService", "android:mimeType must be string literal.");
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static ShortcutInfo parseShortcutAttributes(ShortcutService shortcutService, AttributeSet attributeSet, String str, ComponentName componentName, int i, int i2) {
        TypedArray typedArray;
        TypedArray obtainAttributes = shortcutService.mContext.getResources().obtainAttributes(attributeSet, R.styleable.Shortcut);
        try {
            if (obtainAttributes.getType(2) != 3) {
                Log.w("ShortcutService", "android:shortcutId must be string literal. activity=" + componentName);
                obtainAttributes.recycle();
                return null;
            }
            String nonResourceString = obtainAttributes.getNonResourceString(2);
            boolean z = obtainAttributes.getBoolean(1, true);
            int resourceId = obtainAttributes.getResourceId(0, 0);
            int resourceId2 = obtainAttributes.getResourceId(3, 0);
            int resourceId3 = obtainAttributes.getResourceId(4, 0);
            int resourceId4 = obtainAttributes.getResourceId(5, 0);
            int resourceId5 = obtainAttributes.getResourceId(6, 0);
            String resourceName = resourceId5 != 0 ? shortcutService.mContext.getResources().getResourceName(resourceId5) : null;
            if (TextUtils.isEmpty(nonResourceString)) {
                Log.w("ShortcutService", "android:shortcutId must be provided. activity=" + componentName);
                obtainAttributes.recycle();
                return null;
            }
            if (resourceId2 == 0) {
                Log.w("ShortcutService", "android:shortcutShortLabel must be provided. activity=" + componentName);
                obtainAttributes.recycle();
                return null;
            }
            typedArray = obtainAttributes;
            try {
                ShortcutInfo shortcutInfo = new ShortcutInfo(i, nonResourceString, str, componentName, null, null, resourceId2, null, null, resourceId3, null, null, resourceId4, null, null, null, i2, null, shortcutService.injectCurrentTimeMillis(), (z ? 32 : 64) | 256 | (resourceId != 0 ? 4 : 0), resourceId, null, null, null, !z ? 1 : 0, null, null, resourceName, null);
                typedArray.recycle();
                return shortcutInfo;
            } catch (Throwable th) {
                th = th;
                typedArray.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            typedArray = obtainAttributes;
        }
    }

    public static List parseShortcuts(ShortcutService shortcutService, String str, int i, List list) {
        long time = shortcutService.mStatLogger.getTime();
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List queryActivities = shortcutService.queryActivities(intent, str, null, i);
            if (queryActivities != null && queryActivities.size() != 0) {
                list.clear();
                try {
                    int size = queryActivities.size();
                    List list2 = null;
                    for (int i2 = 0; i2 < size; i2++) {
                        ActivityInfo activityInfo = ((ResolveInfo) queryActivities.get(i2)).activityInfo;
                        if (activityInfo != null) {
                            ActivityInfo injectGetActivityInfoWithMetadataWithUninstalled = shortcutService.injectGetActivityInfoWithMetadataWithUninstalled(activityInfo.getComponentName(), i);
                            ActivityInfo activityInfo2 = (injectGetActivityInfoWithMetadataWithUninstalled == null || !ShortcutService.isInstalled(injectGetActivityInfoWithMetadataWithUninstalled.applicationInfo)) ? null : injectGetActivityInfoWithMetadataWithUninstalled;
                            if (activityInfo2 != null) {
                                list2 = parseShortcutsOneFile(shortcutService, activityInfo2, str, i, list2, list);
                            }
                        }
                    }
                    return list2;
                } catch (RuntimeException e) {
                    shortcutService.wtf("Exception caught while parsing shortcut XML for package=" + str, e);
                }
            }
            return null;
        } finally {
            shortcutService.logDurationStat(12, time);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
    
        r11 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x03c7, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x03ca, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List parseShortcutsOneFile(com.android.server.pm.ShortcutService r24, android.content.pm.ActivityInfo r25, java.lang.String r26, int r27, java.util.List r28, java.util.List r29) {
        /*
            Method dump skipped, instructions count: 981
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ShortcutParser.parseShortcutsOneFile(com.android.server.pm.ShortcutService, android.content.pm.ActivityInfo, java.lang.String, int, java.util.List, java.util.List):java.util.List");
    }
}
