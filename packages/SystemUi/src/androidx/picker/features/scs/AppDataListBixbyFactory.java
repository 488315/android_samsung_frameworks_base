package androidx.picker.features.scs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoDataImpl;
import androidx.reflect.p001os.SeslUserHandleReflector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppDataListBixbyFactory extends AbstractAppDataListFactory {
    public final Context mContext;

    public AppDataListBixbyFactory(Context context) {
        this.mContext = context;
    }

    public final AppInfoDataImpl createAppInfoData(int i, ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        String str = activityInfo.applicationInfo.packageName;
        String str2 = activityInfo.name;
        AppInfo.Companion.getClass();
        AppInfoDataImpl appInfoDataImpl = new AppInfoDataImpl(new AppInfo(str, str2, i), 0);
        appInfoDataImpl.label = resolveInfo.loadLabel(this.mContext.getPackageManager()).toString();
        return appInfoDataImpl;
    }

    public String getAuthority() {
        return "com.samsung.android.bixby.service.bixbysearch/v1";
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x005a, code lost:
    
        if (r7 != null) goto L19;
     */
    @Override // androidx.picker.features.scs.AbstractAppDataListFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List getDataList() {
        Context context = this.mContext;
        LogTagHelperKt.info(this, "getDataListFromSCS");
        ArrayList arrayList = new ArrayList();
        Uri withAppendedPath = Uri.withAppendedPath(Uri.parse("content://" + getAuthority()), "application");
        Bundle bundle = new Bundle();
        bundle.putString("android:query-arg-sql-selection", "*");
        bundle.putBoolean("query-arg-all-apps", true);
        bundle.putInt("android:query-arg-limit", 10000);
        try {
            Cursor query = context.getContentResolver().query(withAppendedPath, null, bundle, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        do {
                            int columnIndex = query.getColumnIndex("label");
                            int columnIndex2 = query.getColumnIndex("componentName");
                            int columnIndex3 = query.getColumnIndex("packageName");
                            int columnIndex4 = query.getColumnIndex("user");
                            if (columnIndex != -1 && columnIndex2 != -1 && columnIndex3 != -1) {
                                String string = query.getString(columnIndex);
                                String string2 = query.getString(columnIndex2);
                                String string3 = query.getString(columnIndex3);
                                String string4 = query.getString(columnIndex4);
                                AppInfo.Companion companion = AppInfo.Companion;
                                int parseInt = Integer.parseInt(string4);
                                companion.getClass();
                                AppInfoDataImpl appInfoDataImpl = new AppInfoDataImpl(new AppInfo(string3, string2, parseInt), 0);
                                appInfoDataImpl.label = string;
                                arrayList.add(appInfoDataImpl);
                            }
                            Log.e("SeslAppPicker[1.0.44-sesl6]." + getLogTag(), String.format("Can't find columnIndex (%s : %d, %s : %d, %s : %d, %s : %d)", "label", Integer.valueOf(columnIndex), "componentName", Integer.valueOf(columnIndex2), "packageName", Integer.valueOf(columnIndex3), "user", Integer.valueOf(columnIndex4)));
                        } while (query.moveToNext());
                    }
                } finally {
                }
            }
            query.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (arrayList.size() == 0) {
            LogTagHelperKt.info(this, "getDataListFromPackageManager");
            arrayList = new ArrayList();
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            try {
                Iterator<UserHandle> it = ((UserManager) context.getSystemService("user")).getUserProfiles().iterator();
                while (it.hasNext()) {
                    int semGetIdentifier = it.next().semGetIdentifier();
                    List semQueryIntentActivitiesAsUser = context.getPackageManager().semQueryIntentActivitiesAsUser(intent, 0, semGetIdentifier);
                    int size = semQueryIntentActivitiesAsUser.size();
                    for (int i = 0; i < size; i++) {
                        arrayList.add(createAppInfoData(semGetIdentifier, (ResolveInfo) semQueryIntentActivitiesAsUser.get(i)));
                    }
                }
            } catch (NoSuchMethodError unused) {
                LogTagHelperKt.warn(this, "Failed to call semGetIdentifier and semQueryIntentActivitiesAsUser");
                int myUserId = SeslUserHandleReflector.myUserId();
                List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
                int size2 = queryIntentActivities.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arrayList.add(createAppInfoData(myUserId, queryIntentActivities.get(i2)));
                }
            }
        }
        LogTagHelperKt.info(this, "getDataList=" + arrayList.size());
        return arrayList;
    }

    @Override // androidx.picker.features.scs.AbstractAppDataListFactory, androidx.picker.common.log.LogTag
    public String getLogTag() {
        return "AppDataListBixbyFactory";
    }
}
