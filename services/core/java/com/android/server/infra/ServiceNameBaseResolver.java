package com.android.server.infra;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ServiceNameBaseResolver {
    public final Context mContext;
    public final boolean mIsMultiple;
    public ServiceNameResolver$NameResolverListener mOnSetCallback;
    public AnonymousClass1 mTemporaryHandler;
    public long mTemporaryServiceExpiration;
    public final Object mLock = new Object();
    public final SparseArray mTemporaryServiceNamesList = new SparseArray();
    public final SparseBooleanArray mDefaultServicesDisabled = new SparseBooleanArray();

    public ServiceNameBaseResolver(Context context, boolean z) {
        this.mContext = context;
        this.mIsMultiple = z;
    }

    public void dumpShort(int i, PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                String[] strArr = (String[]) this.mTemporaryServiceNamesList.get(i);
                if (strArr != null) {
                    printWriter.print("tmpName=");
                    printWriter.print(Arrays.toString(strArr));
                    long elapsedRealtime = this.mTemporaryServiceExpiration - SystemClock.elapsedRealtime();
                    printWriter.print(" (expires in ");
                    TimeUtils.formatDuration(elapsedRealtime, printWriter);
                    printWriter.print("), ");
                }
                printWriter.print("defaultName=");
                printWriter.print(getDefaultServiceName(i));
                printWriter.println(this.mDefaultServicesDisabled.get(i) ? " (disabled)" : " (enabled)");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void dumpShort(PrintWriter printWriter);

    public final String getDefaultServiceName(int i) {
        String[] defaultServiceNameList = getDefaultServiceNameList(i);
        if (defaultServiceNameList == null || defaultServiceNameList.length == 0) {
            return null;
        }
        return defaultServiceNameList[0];
    }

    public final String[] getDefaultServiceNameList(int i) {
        synchronized (this.mLock) {
            if (!this.mIsMultiple) {
                String readServiceName = readServiceName(i);
                return TextUtils.isEmpty(readServiceName) ? new String[0] : new String[]{readServiceName};
            }
            String[] readServiceNameList = readServiceNameList(i);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < readServiceNameList.length; i2++) {
                try {
                    if (!TextUtils.isEmpty(readServiceNameList[i2])) {
                        if (AppGlobals.getPackageManager().getServiceInfo(ComponentName.unflattenFromString(readServiceNameList[i2]), 786432L, i) != null) {
                            arrayList.add(readServiceNameList[i2]);
                        }
                    }
                } catch (Exception e) {
                    Slog.e("ServiceNameBaseResolver", "Could not validate provided services.", e);
                }
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
    }

    public final String getServiceName(int i) {
        String[] serviceNameList = getServiceNameList(i);
        if (serviceNameList == null || serviceNameList.length == 0) {
            return null;
        }
        return serviceNameList[0];
    }

    public final String[] getServiceNameList(int i) {
        synchronized (this.mLock) {
            try {
                String[] strArr = (String[]) this.mTemporaryServiceNamesList.get(i);
                if (strArr != null) {
                    Slog.w("ServiceNameBaseResolver", "getServiceName(): using temporary name " + Arrays.toString(strArr) + " for user " + i);
                    return strArr;
                }
                if (!this.mDefaultServicesDisabled.get(i)) {
                    return getDefaultServiceNameList(i);
                }
                Slog.w("ServiceNameBaseResolver", "getServiceName(): temporary name not set and default disabled for user " + i);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isTemporary(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mTemporaryServiceNamesList.get(i) != null;
        }
        return z;
    }

    public abstract String readServiceName(int i);

    public abstract String[] readServiceNameList(int i);

    public final void resetTemporaryService(int i) {
        synchronized (this.mLock) {
            try {
                Slog.i("ServiceNameBaseResolver", "resetting temporary service for user " + i + " from " + Arrays.toString((Object[]) this.mTemporaryServiceNamesList.get(i)));
                this.mTemporaryServiceNamesList.remove(i);
                AnonymousClass1 anonymousClass1 = this.mTemporaryHandler;
                if (anonymousClass1 != null) {
                    anonymousClass1.removeMessages(0);
                    this.mTemporaryHandler = null;
                }
                ServiceNameResolver$NameResolverListener serviceNameResolver$NameResolverListener = this.mOnSetCallback;
                if (serviceNameResolver$NameResolverListener != null) {
                    serviceNameResolver$NameResolverListener.onNameResolved(i, null, false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setDefaultServiceEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if ((!this.mDefaultServicesDisabled.get(i)) == z) {
                    Slog.i("ServiceNameBaseResolver", "setDefaultServiceEnabled(" + i + "): already " + z);
                    return false;
                }
                if (z) {
                    Slog.i("ServiceNameBaseResolver", "disabling default service for user " + i);
                    this.mDefaultServicesDisabled.delete(i);
                } else {
                    Slog.i("ServiceNameBaseResolver", "enabling default service for user " + i);
                    this.mDefaultServicesDisabled.put(i, true);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.infra.ServiceNameBaseResolver$1] */
    public final void setTemporaryServices(final int i, int i2, String[] strArr) {
        synchronized (this.mLock) {
            try {
                this.mTemporaryServiceNamesList.put(i, strArr);
                AnonymousClass1 anonymousClass1 = this.mTemporaryHandler;
                if (anonymousClass1 == null) {
                    this.mTemporaryHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.server.infra.ServiceNameBaseResolver.1
                        @Override // android.os.Handler
                        public final void handleMessage(Message message) {
                            if (message.what == 0) {
                                synchronized (ServiceNameBaseResolver.this.mLock) {
                                    ServiceNameBaseResolver.this.resetTemporaryService(i);
                                }
                            } else {
                                Slog.wtf("ServiceNameBaseResolver", "invalid handler msg: " + message);
                            }
                        }
                    };
                } else {
                    anonymousClass1.removeMessages(0);
                }
                long j = i2;
                this.mTemporaryServiceExpiration = SystemClock.elapsedRealtime() + j;
                sendEmptyMessageDelayed(0, j);
                for (String str : strArr) {
                    ServiceNameResolver$NameResolverListener serviceNameResolver$NameResolverListener = this.mOnSetCallback;
                    if (serviceNameResolver$NameResolverListener != null) {
                        serviceNameResolver$NameResolverListener.onNameResolved(i, str, true);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public String toString() {
        String str;
        synchronized (this.mLock) {
            str = "FrameworkResourcesServiceNamer[temps=" + this.mTemporaryServiceNamesList + "]";
        }
        return str;
    }
}
