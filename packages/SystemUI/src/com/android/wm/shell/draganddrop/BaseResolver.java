package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.android.internal.app.ResolverActivity;
import com.android.wm.shell.draganddrop.AppResultFactory;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class BaseResolver {
    public final Context mContext;
    public ExecutableAppHolder.MultiInstanceAllowList mMultiInstanceAllowList;
    public final ExecutableAppHolder.MultiInstanceBlockList mMultiInstanceBlockList;
    public final PackageManager mPackageManager;
    public final ArrayList mTempList = new ArrayList();
    public final String TAG = "BaseResolver-" + getClass().getSimpleName().replace("Resolver", "");
    public final MultiWindowManager mMultiWindowManager = MultiWindowManager.getInstance();

    public BaseResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mMultiInstanceBlockList = multiInstanceBlockList;
    }

    public static String calculateContentType(Intent intent) {
        String type = intent.getType();
        if (type != null) {
            return type;
        }
        Uri data = intent.getData();
        if (data == null || data.getScheme() == null) {
            return null;
        }
        return data.getScheme();
    }

    public abstract Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra);

    public final void resolveActivities(final Intent intent, int i, ArrayList arrayList, AppResultFactory.ResultExtra resultExtra) {
        arrayList.clear();
        ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(intent, 128, i);
        if (resolveActivityAsUser == null) {
            return;
        }
        if (ResolverActivity.class.getName().equals(resolveActivityAsUser.activityInfo.name)) {
            arrayList.addAll(this.mPackageManager.queryIntentActivitiesAsUser(intent, 192, i));
        } else {
            arrayList.add(resolveActivityAsUser);
        }
        final int i2 = 0;
        arrayList.removeIf(new Predicate() { // from class: com.android.wm.shell.draganddrop.BaseResolver$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i2;
                Object obj2 = intent;
                switch (i3) {
                    case 0:
                        Intent intent2 = (Intent) obj2;
                        ActivityInfo activityInfo = ((ResolveInfo) obj).activityInfo;
                        if (activityInfo != null && (!"android.intent.action.SEND".equals(intent2.getAction()) || "com.samsung.android.app.notes".equals(activityInfo.packageName) || "com.sec.android.app.sbrowser".equals(activityInfo.packageName))) {
                        }
                        break;
                    default:
                        if ((((BaseResolver) obj2).mMultiWindowManager.getSupportedMultiWindowModes(((ResolveInfo) obj).activityInfo) & 3) == 0) {
                        }
                        break;
                }
                return false;
            }
        });
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() == 1 && TextUtils.isEmpty(resultExtra.mAppLabel)) {
            resultExtra.mAppLabel = ((ResolveInfo) arrayList.get(0)).loadLabel(this.mPackageManager);
        }
        final int i3 = 1;
        arrayList.removeIf(new Predicate() { // from class: com.android.wm.shell.draganddrop.BaseResolver$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i32 = i3;
                Object obj2 = this;
                switch (i32) {
                    case 0:
                        Intent intent2 = (Intent) obj2;
                        ActivityInfo activityInfo = ((ResolveInfo) obj).activityInfo;
                        if (activityInfo != null && (!"android.intent.action.SEND".equals(intent2.getAction()) || "com.samsung.android.app.notes".equals(activityInfo.packageName) || "com.sec.android.app.sbrowser".equals(activityInfo.packageName))) {
                        }
                        break;
                    default:
                        if ((((BaseResolver) obj2).mMultiWindowManager.getSupportedMultiWindowModes(((ResolveInfo) obj).activityInfo) & 3) == 0) {
                        }
                        break;
                }
                return false;
            }
        });
        if (arrayList.isEmpty()) {
            resultExtra.mNonResizeableAppOnly = true;
        }
    }

    public final void resolveActivitiesForSBrowser(Intent intent, int i, ArrayList arrayList, AppResultFactory.ResultExtra resultExtra) {
        ResolveInfo resolveInfo;
        arrayList.clear();
        arrayList.addAll(this.mPackageManager.queryIntentActivitiesAsUser(intent, 131264, i));
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                resolveInfo = null;
                break;
            }
            Object obj = arrayList.get(i2);
            i2++;
            resolveInfo = (ResolveInfo) obj;
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && "com.sec.android.app.sbrowser".equals(activityInfo.packageName)) {
                break;
            }
        }
        if (resolveInfo == null) {
            resolveActivities(intent, i, arrayList, resultExtra);
        } else {
            arrayList.clear();
            arrayList.add(resolveInfo);
        }
    }
}
