package androidx.slice.compat;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import androidx.collection.ArraySet;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.SliceItemHolder;
import androidx.slice.SliceProvider;
import androidx.versionedparcelable.VersionedParcelable;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes.dex */
public class SliceProviderCompat {
    public final String mCallback;
    public final Context mContext;
    public final CompatPermissionManager mPermissionManager;
    public final SliceProvider mProvider;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final AnonymousClass1 mAnr = new Runnable() { // from class: androidx.slice.compat.SliceProviderCompat.1
        @Override // java.lang.Runnable
        public final void run() {
            Process.sendSignal(Process.myPid(), 3);
            Log.wtf("SliceProviderCompat", "Timed out while handling slice callback " + SliceProviderCompat.this.mCallback);
        }
    };

    /* renamed from: androidx.slice.compat.SliceProviderCompat$2, reason: invalid class name */
    public class AnonymousClass2 {
        public final /* synthetic */ Context val$context;

        public AnonymousClass2(Context context) {
            this.val$context = context;
        }

        public final void handle(SliceItemHolder sliceItemHolder) {
            VersionedParcelable versionedParcelable = sliceItemHolder.mVersionedParcelable;
            if (versionedParcelable instanceof IconCompat) {
                IconCompat iconCompat = (IconCompat) versionedParcelable;
                iconCompat.checkResource(this.val$context);
                int type = iconCompat.mType;
                if (type == -1) {
                    type = ((Icon) iconCompat.mObj1).getType();
                }
                if (type == 2 && iconCompat.getResId() == 0) {
                    sliceItemHolder.mVersionedParcelable = null;
                }
            }
        }
    }

    public class ProviderHolder implements AutoCloseable {
        public final ContentProviderClient mProvider;

        public ProviderHolder(ContentProviderClient contentProviderClient) {
            this.mProvider = contentProviderClient;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            ContentProviderClient contentProviderClient = this.mProvider;
            if (contentProviderClient == null) {
                return;
            }
            contentProviderClient.close();
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.slice.compat.SliceProviderCompat$1] */
    public SliceProviderCompat(SliceProvider sliceProvider, CompatPermissionManager compatPermissionManager, Context context) {
        this.mProvider = sliceProvider;
        this.mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("slice_data_all_slice_files", 0);
        Set<String> stringSet = sharedPreferences.getStringSet("slice_data_all_slice_files", Collections.EMPTY_SET);
        if (!stringSet.contains("slice_data_androidx.slice.compat.SliceProviderCompat")) {
            ArraySet arraySet = new ArraySet(stringSet);
            arraySet.add("slice_data_androidx.slice.compat.SliceProviderCompat");
            sharedPreferences.edit().putStringSet("slice_data_all_slice_files", arraySet).commit();
        }
        new CompatPinnedList(context, "slice_data_androidx.slice.compat.SliceProviderCompat");
        this.mPermissionManager = compatPermissionManager;
    }
}
