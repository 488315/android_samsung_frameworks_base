package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AppCompatDelegate {
    public static final AppLocalesStorageHelper$SerialExecutor sSerialExecutorForLocalesStorage = new AppLocalesStorageHelper$SerialExecutor(new Executor() { // from class: androidx.appcompat.app.AppLocalesStorageHelper$ThreadPerTaskExecutor
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    });
    public static final int sDefaultNightMode = -100;
    public static Boolean sIsAutoStoreLocalesOptedIn = null;
    public static boolean sIsFrameworkSyncChecked = false;
    public static Object sLocaleManager = null;
    public static Context sAppContext = null;
    public static final ArraySet sActivityDelegates = new ArraySet();
    public static final Object sActivityDelegatesLock = new Object();
    public static final Object sAppLocalesStorageSyncLock = new Object();

    public static Object getLocaleManagerForApplication() {
        Context contextForDelegate;
        Object obj = sLocaleManager;
        if (obj != null) {
            return obj;
        }
        if (sAppContext == null) {
            ArraySet arraySet = sActivityDelegates;
            arraySet.getClass();
            ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
            while (true) {
                if (!elementIterator.hasNext()) {
                    break;
                }
                AppCompatDelegate appCompatDelegate = (AppCompatDelegate) ((WeakReference) elementIterator.next()).get();
                if (appCompatDelegate != null && (contextForDelegate = appCompatDelegate.getContextForDelegate()) != null) {
                    sAppContext = contextForDelegate;
                    break;
                }
            }
        }
        Context context = sAppContext;
        if (context != null) {
            sLocaleManager = context.getSystemService(SpeechRecognitionConst.Key.LOCALE);
        }
        return sLocaleManager;
    }

    public static boolean isAutoStorageOptedIn(Context context) {
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

    public static void removeDelegateFromActives(AppCompatDelegate appCompatDelegate) {
        synchronized (sActivityDelegatesLock) {
            ArraySet arraySet = sActivityDelegates;
            arraySet.getClass();
            ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
            while (elementIterator.hasNext()) {
                AppCompatDelegate appCompatDelegate2 = (AppCompatDelegate) ((WeakReference) elementIterator.next()).get();
                if (appCompatDelegate2 == appCompatDelegate || appCompatDelegate2 == null) {
                    elementIterator.remove();
                }
            }
        }
    }

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract View findViewById(int i);

    public Context getContextForDelegate() {
        return null;
    }

    public abstract MenuInflater getMenuInflater();

    public abstract ActionBar getSupportActionBar();

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onCreate();

    public abstract void onDestroy();

    public abstract void onPostResume();

    public abstract void onStart();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void setLocalNightMode(int i);

    public abstract void setSupportActionBar(Toolbar toolbar);

    public abstract void setTitle(CharSequence charSequence);

    public Context attachBaseContext2(Context context) {
        return context;
    }

    public void setTheme(int i) {
    }
}
