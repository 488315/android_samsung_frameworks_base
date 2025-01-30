package androidx.slice;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.slice.widget.SliceLiveData;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceViewManagerWrapper extends SliceViewManagerBase {
    public final ArrayMap mCachedAuthorities;
    public final ArrayMap mCachedSuspendFlags;
    public final android.app.slice.SliceManager mManager;
    public final ArraySet mSpecs;

    public SliceViewManagerWrapper(Context context) {
        this(context, (android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class));
    }

    public final Slice bindSlice(Intent intent) {
        if (intent.getComponent() != null ? isPackageSuspended(intent.getComponent().getPackageName()) : intent.getPackage() != null ? isPackageSuspended(intent.getPackage()) : intent.getData() != null ? isAuthoritySuspended(intent.getData().getAuthority()) : false) {
            return null;
        }
        return SliceConvert.wrap(this.mManager.bindSlice(intent, this.mSpecs), this.mContext);
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
                Boolean valueOf = Boolean.valueOf((this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0);
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
        this.mSpecs = SliceConvert.unwrap(SliceLiveData.SUPPORTED_SPECS);
    }
}
