package androidx.appcompat.app;

import android.app.LocaleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.util.Xml;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArraySet;
import androidx.collection.IndexBasedArrayIterator;
import androidx.core.os.LocaleListCompat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class AppCompatDelegate {
    static AppLocalesStorageHelper$SerialExecutor sSerialExecutorForLocalesStorage = new AppLocalesStorageHelper$SerialExecutor(new AppLocalesStorageHelper$ThreadPerTaskExecutor());
    private static int sDefaultNightMode = -100;
    private static Boolean sIsAutoStoreLocalesOptedIn = null;
    private static boolean sIsFrameworkSyncChecked = false;
    private static final ArraySet<WeakReference<AppCompatDelegate>> sActivityDelegates = new ArraySet<>();
    private static final Object sActivityDelegatesLock = new Object();
    private static final Object sAppLocalesStorageSyncLock = new Object();

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0059, code lost:
    
        if (r4 != null) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0052, code lost:
    
        r3 = r5.getAttributeValue(null, "application_locales");
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0065, code lost:
    
        if (r4 == null) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void $r8$lambda$F36VbET_i_Y_e98J3kKLOS37EAQ(Context context) {
        String str;
        Object systemService;
        ComponentName componentName = new ComponentName(context, "androidx.appcompat.app.AppLocalesMetadataHolderService");
        if (context.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
            if (getApplicationLocales().isEmpty()) {
                str = "";
                try {
                    FileInputStream openFileInput = context.openFileInput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                    try {
                        try {
                            XmlPullParser newPullParser = Xml.newPullParser();
                            newPullParser.setInput(openFileInput, "UTF-8");
                            int depth = newPullParser.getDepth();
                            while (true) {
                                int next = newPullParser.next();
                                if (next == 1 || (next == 3 && newPullParser.getDepth() <= depth)) {
                                    break;
                                }
                                if (next != 3 && next != 4 && newPullParser.getName().equals("locales")) {
                                    break;
                                }
                            }
                        } catch (IOException | XmlPullParserException unused) {
                            Log.w("AppLocalesStorageHelper", "Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                        }
                    } catch (Throwable th) {
                        if (openFileInput != null) {
                            try {
                                openFileInput.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                }
            }
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        }
        sIsFrameworkSyncChecked = true;
        if (str.isEmpty()) {
            context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
        }
        systemService = context.getSystemService("locale");
        if (systemService != null) {
            ((LocaleManager) systemService).setApplicationLocales(LocaleList.forLanguageTags(str));
        }
        context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        sIsFrameworkSyncChecked = true;
        systemService = context.getSystemService("locale");
        if (systemService != null) {
        }
        context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        sIsFrameworkSyncChecked = true;
    }

    AppCompatDelegate() {
    }

    static void addActiveDelegate(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            removeDelegateFromActives(appCompatDelegate);
            sActivityDelegates.add(new WeakReference<>(appCompatDelegate));
        }
    }

    public static LocaleListCompat getApplicationLocales() {
        Object obj;
        Context contextForDelegate;
        Iterator<WeakReference<AppCompatDelegate>> it = sActivityDelegates.iterator();
        while (true) {
            IndexBasedArrayIterator indexBasedArrayIterator = (IndexBasedArrayIterator) it;
            if (!indexBasedArrayIterator.hasNext()) {
                obj = null;
                break;
            }
            AppCompatDelegate appCompatDelegate = (AppCompatDelegate) ((WeakReference) indexBasedArrayIterator.next()).get();
            if (appCompatDelegate != null && (contextForDelegate = appCompatDelegate.getContextForDelegate()) != null) {
                obj = contextForDelegate.getSystemService("locale");
                break;
            }
        }
        return obj != null ? LocaleListCompat.wrap(((LocaleManager) obj).getApplicationLocales()) : LocaleListCompat.getEmptyLocaleList();
    }

    public static int getDefaultNightMode() {
        return sDefaultNightMode;
    }

    static boolean isAutoStorageOptedIn(Context context) {
        if (sIsAutoStoreLocalesOptedIn == null) {
            try {
                int i = AppLocalesMetadataHolderService.$r8$clinit;
                Bundle bundle = context.getPackageManager().getServiceInfo(new ComponentName(context, (Class<?>) AppLocalesMetadataHolderService.class), 640).metaData;
                if (bundle != null) {
                    sIsAutoStoreLocalesOptedIn = Boolean.valueOf(bundle.getBoolean("autoStoreLocales"));
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d("AppCompatDelegate", "Checking for metadata for AppLocalesMetadataHolderService : Service not found");
                sIsAutoStoreLocalesOptedIn = Boolean.FALSE;
            }
        }
        return sIsAutoStoreLocalesOptedIn.booleanValue();
    }

    static void removeActivityDelegate(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            removeDelegateFromActives(appCompatDelegate);
        }
    }

    private static void removeDelegateFromActives(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            Iterator<WeakReference<AppCompatDelegate>> it = sActivityDelegates.iterator();
            while (true) {
                IndexBasedArrayIterator indexBasedArrayIterator = (IndexBasedArrayIterator) it;
                if (indexBasedArrayIterator.hasNext()) {
                    AppCompatDelegate appCompatDelegate2 = (AppCompatDelegate) ((WeakReference) indexBasedArrayIterator.next()).get();
                    if (appCompatDelegate2 == appCompatDelegate || appCompatDelegate2 == null) {
                        indexBasedArrayIterator.remove();
                    }
                }
            }
        }
    }

    static void syncRequestedAndStoredLocales(final Context context) {
        if (isAutoStorageOptedIn(context) && !sIsFrameworkSyncChecked) {
            sSerialExecutorForLocalesStorage.execute(new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppCompatDelegate.$r8$lambda$F36VbET_i_Y_e98J3kKLOS37EAQ(context);
                }
            });
        }
    }

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract <T extends View> T findViewById(int i);

    public Context getContextForDelegate() {
        return null;
    }

    public int getLocalNightMode() {
        return -100;
    }

    public abstract MenuInflater getMenuInflater();

    public abstract WindowDecorActionBar getSupportActionBar();

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onCreate();

    public abstract void onDestroy();

    public abstract void onPostCreate();

    public abstract void onPostResume();

    public abstract void onSaveInstanceState();

    public abstract void onStart();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void setTitle(CharSequence charSequence);

    public Context attachBaseContext2(Context context) {
        return context;
    }

    public void setTheme(int i) {
    }
}
