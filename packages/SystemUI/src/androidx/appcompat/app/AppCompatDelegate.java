package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AppCompatDelegate {
    public static final Object sAppLocalesStorageSyncLock = null;
    public static final SerialExecutor sSerialExecutorForLocalesStorage = new SerialExecutor(new ThreadPerTaskExecutor());
    public static int sDefaultNightMode = -100;
    public static Boolean sIsAutoStoreLocalesOptedIn = null;
    public static boolean sIsFrameworkSyncChecked = false;
    public static final ArraySet sActivityDelegates = new ArraySet();
    public static final Object sActivityDelegatesLock = new Object();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SerialExecutor implements Executor {
        public Runnable mActive;
        public final Executor mExecutor;
        public final Object mLock = new Object();
        public final Queue mTasks = new ArrayDeque();

        public SerialExecutor(Executor executor) {
            this.mExecutor = executor;
        }

        @Override // java.util.concurrent.Executor
        public final void execute(final Runnable runnable) {
            synchronized (this.mLock) {
                try {
                    ((ArrayDeque) this.mTasks).add(new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegate$SerialExecutor$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppCompatDelegate.SerialExecutor serialExecutor = AppCompatDelegate.SerialExecutor.this;
                            Runnable runnable2 = runnable;
                            serialExecutor.getClass();
                            try {
                                runnable2.run();
                            } finally {
                                serialExecutor.scheduleNext();
                            }
                        }
                    });
                    if (this.mActive == null) {
                        scheduleNext();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void scheduleNext() {
            synchronized (this.mLock) {
                try {
                    Runnable runnable = (Runnable) ((ArrayDeque) this.mTasks).poll();
                    this.mActive = runnable;
                    if (runnable != null) {
                        this.mExecutor.execute(runnable);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ThreadPerTaskExecutor implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
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

    public static void removeDelegateFromActives(AppCompatDelegateImpl appCompatDelegateImpl) {
        synchronized (sActivityDelegatesLock) {
            try {
                ArraySet arraySet = sActivityDelegates;
                arraySet.getClass();
                ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                while (elementIterator.hasNext()) {
                    AppCompatDelegate appCompatDelegate = (AppCompatDelegate) ((WeakReference) elementIterator.next()).get();
                    if (appCompatDelegate == appCompatDelegateImpl || appCompatDelegate == null) {
                        elementIterator.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract boolean applyDayNight();

    public abstract View findViewById(int i);

    public Context getContextForDelegate() {
        return null;
    }

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract void onCreate();

    public abstract void onDestroy();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void setLocalNightMode(int i);

    public abstract void setTitle(CharSequence charSequence);
}
