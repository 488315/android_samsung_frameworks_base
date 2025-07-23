package androidx.slice;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import androidx.slice.widget.SliceLiveData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SliceViewManagerWrapper extends SliceViewManagerBase {
    public final ArrayMap mCachedAuthorities;
    public final ArrayMap mCachedSuspendFlags;
    public final android.app.slice.SliceManager mManager;
    public final ArraySet mSpecs;

    public SliceViewManagerWrapper(Context context) {
        this(context, (android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class));
    }

    @Override // androidx.slice.SliceViewManager
    public final Slice bindSlice(Uri uri) {
        if (isAuthoritySuspended(uri.getAuthority())) {
            return null;
        }
        return SliceConvert.wrap(this.mManager.bindSlice(uri, this.mSpecs), this.mContext);
    }

    public final boolean isAuthoritySuspended(String str) {
        ArrayMap arrayMap = this.mCachedAuthorities;
        String str2 = (String) arrayMap.get(str);
        if (str2 == null) {
            ProviderInfo resolveContentProvider = this.mContext.getPackageManager().resolveContentProvider(str, 0);
            if (resolveContentProvider == null) {
                return false;
            }
            str2 = resolveContentProvider.packageName;
            arrayMap.put(str, str2);
        }
        return isPackageSuspended(str2);
    }

    public final boolean isPackageSuspended(String str) {
        ArrayMap arrayMap = this.mCachedSuspendFlags;
        Boolean bool = (Boolean) arrayMap.get(str);
        if (bool == null) {
            try {
                Boolean valueOf = Boolean.valueOf((this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 1073741824) != 0);
                arrayMap.put(str, valueOf);
                bool = valueOf;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }
        return bool.booleanValue();
    }

    @Override // androidx.slice.SliceViewManager
    public final void pinSlice(Uri uri) {
        try {
            this.mManager.pinSlice(uri, this.mSpecs);
        } catch (RuntimeException e) {
            ContentProviderClient acquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(uri);
            if (acquireContentProviderClient != null) {
                acquireContentProviderClient.release();
                throw e;
            }
            throw new IllegalArgumentException("No provider found for " + uri);
        }
    }

    @Override // androidx.slice.SliceViewManager
    public final void unpinSlice(Uri uri) {
        try {
            this.mManager.unpinSlice(uri);
        } catch (IllegalStateException unused) {
        }
    }

    public SliceViewManagerWrapper(Context context, android.app.slice.SliceManager sliceManager) {
        super(context);
        this.mCachedSuspendFlags = new ArrayMap();
        this.mCachedAuthorities = new ArrayMap();
        this.mManager = sliceManager;
        ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
        ArraySet arraySet2 = new ArraySet();
        if (arraySet != null) {
            ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
            while (elementIterator.hasNext()) {
                SliceSpec sliceSpec = (SliceSpec) elementIterator.next();
                arraySet2.add(sliceSpec == null ? null : new android.app.slice.SliceSpec(sliceSpec.mType, sliceSpec.mRevision));
            }
        }
        this.mSpecs = arraySet2;
    }

    public final Slice bindSlice(Intent intent) {
        boolean isAuthoritySuspended;
        if (intent.getComponent() != null) {
            isAuthoritySuspended = isPackageSuspended(intent.getComponent().getPackageName());
        } else if (intent.getPackage() != null) {
            isAuthoritySuspended = isPackageSuspended(intent.getPackage());
        } else {
            isAuthoritySuspended = intent.getData() != null ? isAuthoritySuspended(intent.getData().getAuthority()) : false;
        }
        if (isAuthoritySuspended) {
            return null;
        }
        return SliceConvert.wrap(this.mManager.bindSlice(intent, this.mSpecs), this.mContext);
    }
}
