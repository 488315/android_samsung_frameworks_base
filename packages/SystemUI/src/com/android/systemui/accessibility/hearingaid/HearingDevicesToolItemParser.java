package com.android.systemui.accessibility.hearingaid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class HearingDevicesToolItemParser {
    static final int MAX_NUM = 3;

    public static ImmutableList parseStringArray(Context context, String[] strArr, String[] strArr2) {
        if (strArr.length == 0) {
            Log.i("HearingDevicesToolItemParser", "Empty hearing device related tool name in array.");
            return ImmutableList.of();
        }
        String[] strArr3 = (String[]) Arrays.copyOfRange(strArr, 0, Math.min(strArr.length, 3));
        String[] strArr4 = (String[]) Arrays.copyOfRange(strArr2, 0, Math.min(strArr2.length, 3));
        PackageManager packageManager = context.getPackageManager();
        ImmutableList.Itr itr = ImmutableList.EMPTY_ITR;
        ImmutableList.Builder builder = new ImmutableList.Builder();
        PackageManager packageManager2 = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        for (String str : strArr3) {
            if (str.split("/").length == 2) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                try {
                    arrayList.add(packageManager2.getActivityInfo(unflattenFromString, 0));
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("HearingDevicesToolItemParser", "Unable to find hearing device related tool: " + unflattenFromString.flattenToString());
                }
            } else {
                Log.e("HearingDevicesToolItemParser", "Malformed hearing device related tool name item in array: ".concat(str));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : strArr4) {
            try {
                arrayList2.add(context.getDrawable(context.getResources().getIdentifier(str2, BriefViewController.URI_PATH_DRAWABLE, context.getPackageName())));
            } catch (Resources.NotFoundException unused2) {
                Log.e("HearingDevicesToolItemParser", "Resource does not exist: " + str2);
            }
        }
        int size = arrayList.size();
        boolean z = size == arrayList2.size();
        for (int i = 0; i < size; i++) {
            ToolItem toolItem = new ToolItem(((ActivityInfo) arrayList.get(i)).loadLabel(packageManager).toString(), z ? (Drawable) arrayList2.get(i) : ((ActivityInfo) arrayList.get(i)).loadIcon(packageManager), new Intent("android.intent.action.MAIN").setComponent(((ActivityInfo) arrayList.get(i)).getComponentName()));
            int i2 = builder.size;
            int i3 = i2 + 1;
            Object[] objArr = builder.contents;
            if (objArr.length < i3) {
                int length = objArr.length;
                if (i3 < 0) {
                    throw new AssertionError("cannot store more than MAX_VALUE elements");
                }
                int i4 = length + (length >> 1) + 1;
                if (i4 < i3) {
                    i4 = Integer.highestOneBit(i2) << 1;
                }
                if (i4 < 0) {
                    i4 = Integer.MAX_VALUE;
                }
                builder.contents = Arrays.copyOf(objArr, i4);
                builder.forceCopy = false;
            } else if (builder.forceCopy) {
                builder.contents = (Object[]) objArr.clone();
                builder.forceCopy = false;
            }
            Object[] objArr2 = builder.contents;
            int i5 = builder.size;
            builder.size = i5 + 1;
            objArr2[i5] = toolItem;
        }
        builder.forceCopy = true;
        return ImmutableList.asImmutableList(builder.size, builder.contents);
    }
}
