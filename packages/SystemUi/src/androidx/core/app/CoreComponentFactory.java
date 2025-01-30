package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.app.slice.Slice;
import android.app.slice.SliceManager;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import androidx.slice.SliceConvert;
import androidx.slice.SliceProvider;
import com.samsung.android.knox.net.firewall.FirewallRule;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class CoreComponentFactory extends android.app.AppComponentFactory {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CompatWrapped {
    }

    public static Object checkCompatWrapper(Object obj) {
        if (!(obj instanceof CompatWrapped)) {
            return obj;
        }
        final SliceProvider sliceProvider = (SliceProvider) ((CompatWrapped) obj);
        sliceProvider.getClass();
        final String[] strArr = sliceProvider.mAutoGrantPermissions;
        return new android.app.slice.SliceProvider(sliceProvider, strArr) { // from class: androidx.slice.compat.SliceProviderWrapperContainer$SliceProviderWrapper
            public final String[] mAutoGrantPermissions;
            public SliceManager mSliceManager;
            public final SliceProvider mSliceProvider;

            {
                super(strArr);
                this.mAutoGrantPermissions = (strArr == null || strArr.length == 0) ? null : strArr;
                this.mSliceProvider = sliceProvider;
            }

            @Override // android.app.slice.SliceProvider, android.content.ContentProvider
            public final void attachInfo(Context context, ProviderInfo providerInfo) {
                this.mSliceProvider.attachInfo(context, providerInfo);
                super.attachInfo(context, providerInfo);
                this.mSliceManager = (SliceManager) context.getSystemService(SliceManager.class);
            }

            @Override // android.app.slice.SliceProvider, android.content.ContentProvider
            public final Bundle call(String str, String str2, Bundle bundle) {
                Intent intent;
                Uri uri;
                if (this.mAutoGrantPermissions != null) {
                    if ("bind_slice".equals(str)) {
                        if (bundle != null) {
                            uri = (Uri) bundle.getParcelable("slice_uri");
                            if (uri != null && this.mSliceManager.checkSlicePermission(uri, Binder.getCallingPid(), Binder.getCallingUid()) != 0) {
                                checkPermissions(uri);
                            }
                        }
                    } else if ("map_slice".equals(str) && (intent = (Intent) bundle.getParcelable("slice_intent")) != null) {
                        onMapIntentToUri(intent);
                        throw null;
                    }
                    uri = null;
                    if (uri != null) {
                        checkPermissions(uri);
                    }
                }
                if (!"androidx.remotecallback.method.PROVIDER_CALLBACK".equals(str)) {
                    return super.call(str, str2, bundle);
                }
                this.mSliceProvider.getClass();
                return null;
            }

            public final void checkPermissions(Uri uri) {
                if (uri != null) {
                    for (String str : this.mAutoGrantPermissions) {
                        if (getContext().checkCallingPermission(str) == 0) {
                            this.mSliceManager.grantSlicePermission(str, uri);
                            getContext().getContentResolver().notifyChange(uri, null);
                            return;
                        }
                    }
                }
            }

            @Override // android.app.slice.SliceProvider
            public final Slice onBindSlice(Uri uri, Set set) {
                SliceProvider.sSpecs = SliceConvert.wrap(set);
                try {
                    return SliceConvert.unwrap(this.mSliceProvider.onBindSlice());
                } catch (Exception e) {
                    Log.wtf("SliceProviderWrapper", "Slice with URI " + uri.toString() + FirewallRule.IS_INVALID, e);
                    return null;
                } finally {
                    SliceProvider.sSpecs = null;
                }
            }

            @Override // android.content.ContentProvider
            public final boolean onCreate() {
                return true;
            }

            @Override // android.app.slice.SliceProvider
            public final PendingIntent onCreatePermissionRequest(Uri uri) {
                if (this.mAutoGrantPermissions != null) {
                    checkPermissions(uri);
                }
                SliceProvider sliceProvider2 = this.mSliceProvider;
                getCallingPackage();
                sliceProvider2.getClass();
                return super.onCreatePermissionRequest(uri);
            }

            @Override // android.app.slice.SliceProvider
            public final Collection onGetSliceDescendants(Uri uri) {
                this.mSliceProvider.getClass();
                return Collections.emptyList();
            }

            @Override // android.app.slice.SliceProvider
            public final Uri onMapIntentToUri(Intent intent) {
                this.mSliceProvider.getClass();
                throw new UnsupportedOperationException("This provider has not implemented intent to uri mapping");
            }

            @Override // android.app.slice.SliceProvider
            public final void onSlicePinned(Uri uri) {
                this.mSliceProvider.getClass();
                List pinnedSlices = this.mSliceProvider.getPinnedSlices();
                if (pinnedSlices.contains(uri)) {
                    return;
                }
                pinnedSlices.add(uri);
            }

            @Override // android.app.slice.SliceProvider
            public final void onSliceUnpinned(Uri uri) {
                this.mSliceProvider.getClass();
                List pinnedSlices = this.mSliceProvider.getPinnedSlices();
                if (pinnedSlices.contains(uri)) {
                    pinnedSlices.remove(uri);
                }
            }
        };
    }

    @Override // android.app.AppComponentFactory
    public final Activity instantiateActivity(ClassLoader classLoader, String str, Intent intent) {
        return (Activity) checkCompatWrapper(super.instantiateActivity(classLoader, str, intent));
    }

    @Override // android.app.AppComponentFactory
    public final Application instantiateApplication(ClassLoader classLoader, String str) {
        return (Application) checkCompatWrapper(super.instantiateApplication(classLoader, str));
    }

    @Override // android.app.AppComponentFactory
    public final ContentProvider instantiateProvider(ClassLoader classLoader, String str) {
        return (ContentProvider) checkCompatWrapper(super.instantiateProvider(classLoader, str));
    }

    @Override // android.app.AppComponentFactory
    public final BroadcastReceiver instantiateReceiver(ClassLoader classLoader, String str, Intent intent) {
        return (BroadcastReceiver) checkCompatWrapper(super.instantiateReceiver(classLoader, str, intent));
    }

    @Override // android.app.AppComponentFactory
    public final Service instantiateService(ClassLoader classLoader, String str, Intent intent) {
        return (Service) checkCompatWrapper(super.instantiateService(classLoader, str, intent));
    }
}
