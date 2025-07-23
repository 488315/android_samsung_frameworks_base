package com.android.internal.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.LruCache;
import android.util.SparseArray;

/* loaded from: classes5.dex */
public final class AttributeCache {
    private static final int CACHE_SIZE = 4;
    private static AttributeCache sInstance;
    private final Context mContext;
    private PackageMonitor mPackageMonitor;
    private final LruCache<String, Package> mPackages = new LruCache<>(4);
    private final Configuration mConfiguration = new Configuration();

    public static final class Package {
        public final Context context;
        private final SparseArray<ArrayMap<int[], Entry>> mMap = new SparseArray<>();

        public Package(Context context) {
            this.context = context;
        }
    }

    public static final class Entry {
        public final TypedArray array;
        public final Context context;

        public Entry(Context context, TypedArray typedArray) {
            this.context = context;
            this.array = typedArray;
        }

        void recycle() {
            TypedArray typedArray = this.array;
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new AttributeCache(context);
        }
    }

    void monitorPackageRemove(Handler handler) {
        if (this.mPackageMonitor == null) {
            this.mPackageMonitor = new PackageMonitor(this.mContext, handler);
        }
    }

    static class PackageMonitor extends BroadcastReceiver {
        PackageMonitor(Context context, Handler handler) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            context.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, handler);
            IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
            intentFilter2.setPriority(1000);
            context.registerReceiverAsUser(this, UserHandle.ALL, intentFilter2, null, handler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(intent.getAction())) {
                String[] stringArrayExtra = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                if (stringArrayExtra == null || stringArrayExtra.length <= 0) {
                    return;
                }
                for (String str : stringArrayExtra) {
                    AttributeCache.instance().removePackage(str);
                }
                return;
            }
            Uri data = intent.getData();
            if (data != null) {
                AttributeCache.instance().removePackage(data.getEncodedSchemeSpecificPart());
            }
        }
    }

    public static AttributeCache instance() {
        return sInstance;
    }

    public AttributeCache(Context context) {
        this.mContext = context;
    }

    public void removePackage(String str) {
        synchronized (this) {
            Package remove = this.mPackages.remove(str);
            if (remove != null) {
                for (int i = 0; i < remove.mMap.size(); i++) {
                    ArrayMap arrayMap = (ArrayMap) remove.mMap.valueAt(i);
                    for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                        ((Entry) arrayMap.valueAt(i2)).recycle();
                    }
                }
                remove.context.getResources().flushLayoutCache();
            }
        }
    }

    public void updateConfiguration(Configuration configuration) {
        synchronized (this) {
            if ((this.mConfiguration.updateFrom(configuration) & (-1073741985)) != 0) {
                this.mPackages.evictAll();
            }
        }
    }

    public Entry get(String str, int i, int[] iArr) {
        return get(str, i, iArr, -2);
    }

    public Entry get(String str, int i, int[] iArr, int i2) {
        ArrayMap arrayMap;
        Entry entry;
        synchronized (this) {
            Package r0 = this.mPackages.get(str);
            if (r0 != null) {
                arrayMap = (ArrayMap) r0.mMap.get(i);
                if (arrayMap != null && (entry = (Entry) arrayMap.get(iArr)) != null) {
                    return entry;
                }
            } else {
                try {
                    Context createPackageContextAsUser = this.mContext.createPackageContextAsUser(str, 0, new UserHandle(i2));
                    if (createPackageContextAsUser == null) {
                        return null;
                    }
                    r0 = new Package(createPackageContextAsUser);
                    this.mPackages.put(str, r0);
                    arrayMap = null;
                } catch (PackageManager.NameNotFoundException unused) {
                    return null;
                }
            }
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                r0.mMap.put(i, arrayMap);
            }
            try {
                Entry entry2 = new Entry(r0.context, r0.context.obtainStyledAttributes(i, iArr));
                arrayMap.put(iArr, entry2);
                return entry2;
            } catch (Resources.NotFoundException unused2) {
                return null;
            }
        }
    }
}
