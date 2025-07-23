package android.app.slice;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;
import android.util.ArraySet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Deprecated
/* loaded from: classes.dex */
public abstract class SliceProvider extends ContentProvider {
    private static final boolean DEBUG = false;
    public static final String EXTRA_BIND_URI = "slice_uri";
    public static final String EXTRA_INTENT = "slice_intent";
    public static final String EXTRA_PKG = "pkg";
    public static final String EXTRA_RESULT = "result";
    public static final String EXTRA_SLICE = "slice";
    public static final String EXTRA_SLICE_DESCENDANTS = "slice_descendants";
    public static final String EXTRA_SUPPORTED_SPECS = "supported_specs";
    public static final String METHOD_GET_DESCENDANTS = "get_descendants";
    public static final String METHOD_GET_PERMISSIONS = "get_permissions";
    public static final String METHOD_MAP_INTENT = "map_slice";
    public static final String METHOD_MAP_ONLY_INTENT = "map_only";
    public static final String METHOD_PIN = "pin";
    public static final String METHOD_SLICE = "bind_slice";
    public static final String METHOD_UNPIN = "unpin";
    private static final long SLICE_BIND_ANR = 2000;
    public static final String SLICE_TYPE = "vnd.android.slice";
    private static final String TAG = "SliceProvider";
    private final Runnable mAnr;
    private final String[] mAutoGrantPermissions;
    private String mCallback;
    private SliceManager mSliceManager;

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public Slice onBindSlice(Uri uri, Set<SliceSpec> set) {
        return null;
    }

    public void onSlicePinned(Uri uri) {
    }

    public void onSliceUnpinned(Uri uri) {
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public SliceProvider(String... strArr) {
        this.mAnr = new Runnable() { // from class: android.app.slice.SliceProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SliceProvider.this.lambda$new$0();
            }
        };
        this.mAutoGrantPermissions = strArr;
    }

    public SliceProvider() {
        this.mAnr = new Runnable() { // from class: android.app.slice.SliceProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SliceProvider.this.lambda$new$0();
            }
        };
        this.mAutoGrantPermissions = new String[0];
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        this.mSliceManager = (SliceManager) context.getSystemService(SliceManager.class);
    }

    public Collection<Uri> onGetSliceDescendants(Uri uri) {
        return Collections.EMPTY_LIST;
    }

    public Uri onMapIntentToUri(Intent intent) {
        throw new UnsupportedOperationException("This provider has not implemented intent to uri mapping");
    }

    public PendingIntent onCreatePermissionRequest(Uri uri) {
        return createPermissionPendingIntent(getContext(), uri, getCallingPackage());
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final String getType(Uri uri) {
        return SLICE_TYPE;
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        if (str.equals(METHOD_SLICE)) {
            Slice handleBindSlice = handleBindSlice(getUriWithoutUserId(validateIncomingUriOrNull((Uri) bundle.getParcelable("slice_uri", Uri.class))), bundle.getParcelableArrayList(EXTRA_SUPPORTED_SPECS, SliceSpec.class), getCallingPackage(), Binder.getCallingUid(), Binder.getCallingPid());
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("slice", handleBindSlice);
            return bundle2;
        }
        if (str.equals(METHOD_MAP_INTENT)) {
            Intent intent = (Intent) bundle.getParcelable(EXTRA_INTENT, Intent.class);
            if (intent == null) {
                return null;
            }
            Uri validateIncomingUriOrNull = validateIncomingUriOrNull(onMapIntentToUri(intent));
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(EXTRA_SUPPORTED_SPECS, SliceSpec.class);
            Bundle bundle3 = new Bundle();
            if (validateIncomingUriOrNull != null) {
                bundle3.putParcelable("slice", handleBindSlice(validateIncomingUriOrNull, parcelableArrayList, getCallingPackage(), Binder.getCallingUid(), Binder.getCallingPid()));
                return bundle3;
            }
            bundle3.putParcelable("slice", null);
            return bundle3;
        }
        if (str.equals(METHOD_MAP_ONLY_INTENT)) {
            Intent intent2 = (Intent) bundle.getParcelable(EXTRA_INTENT, Intent.class);
            if (intent2 == null) {
                return null;
            }
            Uri validateIncomingUriOrNull2 = validateIncomingUriOrNull(onMapIntentToUri(intent2));
            Bundle bundle4 = new Bundle();
            bundle4.putParcelable("slice", validateIncomingUriOrNull2);
            return bundle4;
        }
        if (str.equals(METHOD_PIN)) {
            Uri uriWithoutUserId = getUriWithoutUserId(validateIncomingUriOrNull((Uri) bundle.getParcelable("slice_uri", Uri.class)));
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system can pin/unpin slices");
            }
            handlePinSlice(uriWithoutUserId);
        } else if (str.equals(METHOD_UNPIN)) {
            Uri uriWithoutUserId2 = getUriWithoutUserId(validateIncomingUriOrNull((Uri) bundle.getParcelable("slice_uri", Uri.class)));
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Only the system can pin/unpin slices");
            }
            handleUnpinSlice(uriWithoutUserId2);
        } else {
            if (str.equals(METHOD_GET_DESCENDANTS)) {
                Uri uriWithoutUserId3 = getUriWithoutUserId(validateIncomingUriOrNull((Uri) bundle.getParcelable("slice_uri", Uri.class)));
                Bundle bundle5 = new Bundle();
                bundle5.putParcelableArrayList(EXTRA_SLICE_DESCENDANTS, new ArrayList<>(handleGetDescendants(uriWithoutUserId3)));
                return bundle5;
            }
            if (str.equals(METHOD_GET_PERMISSIONS)) {
                if (Binder.getCallingUid() != 1000) {
                    throw new SecurityException("Only the system can get permissions");
                }
                Bundle bundle6 = new Bundle();
                bundle6.putStringArray("result", this.mAutoGrantPermissions);
                return bundle6;
            }
        }
        return super.call(str, str2, bundle);
    }

    private Uri validateIncomingUriOrNull(Uri uri) {
        if (uri == null) {
            return null;
        }
        return validateIncomingUri(uri);
    }

    private Collection<Uri> handleGetDescendants(Uri uri) {
        this.mCallback = "onGetSliceDescendants";
        return onGetSliceDescendants(uri);
    }

    private void handlePinSlice(Uri uri) {
        this.mCallback = "onSlicePinned";
        Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
        try {
            onSlicePinned(uri);
        } finally {
            Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private void handleUnpinSlice(Uri uri) {
        this.mCallback = "onSliceUnpinned";
        Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
        try {
            onSliceUnpinned(uri);
        } finally {
            Handler.getMain().removeCallbacks(this.mAnr);
        }
    }

    private Slice handleBindSlice(Uri uri, List<SliceSpec> list, String str, int i, int i2) {
        if (str == null) {
            str = getContext().getPackageManager().getNameForUid(i);
        }
        try {
            this.mSliceManager.enforceSlicePermission(uri, i2, i, this.mAutoGrantPermissions);
            this.mCallback = "onBindSlice";
            Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
            try {
                return onBindSliceStrict(uri, list);
            } finally {
                Handler.getMain().removeCallbacks(this.mAnr);
            }
        } catch (SecurityException unused) {
            return createPermissionSlice(getContext(), uri, str);
        }
    }

    public Slice createPermissionSlice(Context context, Uri uri, String str) {
        this.mCallback = "onCreatePermissionRequest";
        Handler.getMain().postDelayed(this.mAnr, SLICE_BIND_ANR);
        try {
            PendingIntent onCreatePermissionRequest = onCreatePermissionRequest(uri);
            Handler.getMain().removeCallbacks(this.mAnr);
            Slice.Builder builder = new Slice.Builder(uri, null);
            Slice.Builder addAction = new Slice.Builder(builder).addIcon(Icon.createWithResource(context, R.drawable.ic_permission), null, Collections.EMPTY_LIST).addHints(Arrays.asList("title", "shortcut")).addAction(onCreatePermissionRequest, new Slice.Builder(builder).build(), null);
            TypedValue typedValue = new TypedValue();
            new ContextThemeWrapper(context, 16974123).getTheme().resolveAttribute(16843829, typedValue, true);
            builder.addSubSlice(new Slice.Builder(uri.buildUpon().appendPath("permission").build(), null).addIcon(Icon.createWithResource(context, R.drawable.ic_arrow_forward), null, Collections.EMPTY_LIST).addText(getPermissionString(context, str), null, Collections.EMPTY_LIST).addInt(typedValue.data, "color", Collections.EMPTY_LIST).addSubSlice(addAction.build(), null).build(), null);
            return builder.addHints(Arrays.asList(Slice.HINT_PERMISSION_REQUEST)).build();
        } catch (Throwable th) {
            Handler.getMain().removeCallbacks(this.mAnr);
            throw th;
        }
    }

    public static PendingIntent createPermissionPendingIntent(Context context, Uri uri, String str) {
        return PendingIntent.getActivity(context, 0, createPermissionIntent(context, uri, str), 67108864);
    }

    public static Intent createPermissionIntent(Context context, Uri uri, String str) {
        Intent intent = new Intent(SliceManager.ACTION_REQUEST_SLICE_PERMISSION);
        intent.setComponent(ComponentName.unflattenFromString(context.getResources().getString(R.string.config_slicePermissionComponent)));
        intent.putExtra("slice_uri", uri);
        intent.putExtra("pkg", str);
        intent.setData(uri.buildUpon().appendQueryParameter("package", str).build());
        return intent;
    }

    public static CharSequence getPermissionString(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return context.getString(R.string.slices_permission_request, packageManager.getApplicationInfo(str, 0).loadLabel(packageManager), context.getApplicationInfo().loadLabel(packageManager));
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unknown calling app", e);
        }
    }

    private Slice onBindSliceStrict(Uri uri, List<SliceSpec> list) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        try {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDeath().build());
            return onBindSlice(uri, new ArraySet(list));
        } finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        Process.sendSignal(Process.myPid(), 3);
        Log.wtf(TAG, "Timed out while handling slice callback " + this.mCallback);
    }
}
