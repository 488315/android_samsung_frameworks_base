package androidx.slice;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Process;
import androidx.core.app.CoreComponentFactory;
import androidx.slice.compat.CompatPermissionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SliceProvider extends ContentProvider implements CoreComponentFactory.CompatWrapped {
    public static Set sSpecs;
    public String[] mAuthorities;
    public String mAuthority;
    public final String[] mAutoGrantPermissions;
    public final Object mCompatLock;
    public Context mContext;
    public List mPinnedSliceUris;
    public final Object mPinnedSliceUrisLock;

    public SliceProvider(String... strArr) {
        this.mContext = null;
        this.mCompatLock = new Object();
        this.mPinnedSliceUrisLock = new Object();
        this.mAutoGrantPermissions = strArr;
    }

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        String str;
        super.attachInfo(context, providerInfo);
        if (this.mContext == null) {
            this.mContext = context;
            if (providerInfo == null || (str = providerInfo.authority) == null) {
                return;
            }
            if (str.indexOf(59) == -1) {
                this.mAuthority = str;
                this.mAuthorities = null;
            } else {
                this.mAuthority = null;
                this.mAuthorities = str.split(";");
            }
        }
    }

    @Override // android.content.ContentProvider
    public final int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri canonicalize(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public final List getPinnedSlices() {
        synchronized (this.mPinnedSliceUrisLock) {
            if (this.mPinnedSliceUris == null) {
                this.mPinnedSliceUris = new ArrayList(new SliceManagerWrapper(getContext()).mManager.getPinnedSlices());
            }
        }
        return this.mPinnedSliceUris;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return "vnd.android.slice";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public abstract Slice onBindSlice();

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        onCreateSliceProvider();
        return true;
    }

    public CompatPermissionManager onCreatePermissionManager(String[] strArr) {
        return new CompatPermissionManager(getContext(), "slice_perms_".concat(getClass().getName()), Process.myUid(), strArr);
    }

    public abstract void onCreateSliceProvider();

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        return null;
    }

    public SliceProvider() {
        this.mContext = null;
        this.mCompatLock = new Object();
        this.mPinnedSliceUrisLock = new Object();
        this.mAutoGrantPermissions = new String[0];
    }
}
