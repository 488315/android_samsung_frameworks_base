package android.flags;

import android.app.backup.FullBackup;
import android.content.Context;
import android.flags.IFeatureFlags;
import android.flags.IFeatureFlagsCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArraySet;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class FeatureFlags {
    private static final String TAG = "FeatureFlags";
    private static FeatureFlags sInstance;
    private static final Object sInstanceLock = new Object();
    private final Map<String, Map<String, Boolean>> mBooleanOverrides;
    private final Set<Flag<?>> mDirtyFlags;
    private IFeatureFlags mIFeatureFlags;
    private final IFeatureFlagsCallback mIFeatureFlagsCallback;
    private final Set<Flag<?>> mKnownFlags;
    private final Set<ChangeListener> mListeners;

    public interface ChangeListener {
        void onFlagChanged(DynamicFlag<?> dynamicFlag);
    }

    public boolean isEnabled(FusedOffFlag fusedOffFlag) {
        return false;
    }

    public boolean isEnabled(FusedOnFlag fusedOnFlag) {
        return true;
    }

    public static FeatureFlags getInstance() {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new FeatureFlags();
            }
        }
        return sInstance;
    }

    public static void setInstance(FeatureFlags featureFlags) {
        synchronized (sInstanceLock) {
            sInstance = featureFlags;
        }
    }

    private FeatureFlags() {
        this(null);
    }

    public FeatureFlags(IFeatureFlags iFeatureFlags) {
        this.mKnownFlags = new ArraySet();
        this.mDirtyFlags = new ArraySet();
        this.mBooleanOverrides = new HashMap();
        this.mListeners = new HashSet();
        IFeatureFlagsCallback.Stub stub = new IFeatureFlagsCallback.Stub() { // from class: android.flags.FeatureFlags.1
            @Override // android.flags.IFeatureFlagsCallback
            public void onFlagChange(SyncableFlag syncableFlag) {
                for (Flag flag : FeatureFlags.this.mKnownFlags) {
                    if (FeatureFlags.flagEqualsSyncableFlag(flag, syncableFlag)) {
                        if (flag instanceof DynamicFlag) {
                            if (flag instanceof DynamicBooleanFlag) {
                                String value = syncableFlag.getValue();
                                if (value == null) {
                                    value = ((DynamicBooleanFlag) flag).getDefault().toString();
                                }
                                FeatureFlags.this.addBooleanOverride(syncableFlag.getNamespace(), syncableFlag.getName(), value);
                            }
                            FeatureFlags.this.onFlagChange((DynamicFlag) flag);
                            return;
                        }
                        return;
                    }
                }
            }
        };
        this.mIFeatureFlagsCallback = stub;
        this.mIFeatureFlags = iFeatureFlags;
        if (iFeatureFlags != null) {
            try {
                iFeatureFlags.registerCallback(stub);
            } catch (RemoteException e) {
                Log.e(TAG, "Could not register callbacks!", e);
            }
        }
    }

    public static BooleanFlag booleanFlag(String str, String str2, boolean z) {
        return (BooleanFlag) getInstance().addFlag(new BooleanFlag(str, str2, z));
    }

    public static FusedOffFlag fusedOffFlag(String str, String str2) {
        return (FusedOffFlag) getInstance().addFlag(new FusedOffFlag(str, str2));
    }

    public static FusedOnFlag fusedOnFlag(String str, String str2) {
        return (FusedOnFlag) getInstance().addFlag(new FusedOnFlag(str, str2));
    }

    public static DynamicBooleanFlag dynamicBooleanFlag(String str, String str2, boolean z) {
        return (DynamicBooleanFlag) getInstance().addFlag(new DynamicBooleanFlag(str, str2, z));
    }

    public void addChangeListener(ChangeListener changeListener) {
        this.mListeners.add(changeListener);
    }

    public void removeChangeListener(ChangeListener changeListener) {
        this.mListeners.remove(changeListener);
    }

    protected void onFlagChange(DynamicFlag<?> dynamicFlag) {
        Iterator<ChangeListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onFlagChanged(dynamicFlag);
        }
    }

    public boolean isEnabled(BooleanFlag booleanFlag) {
        return getBooleanInternal(booleanFlag);
    }

    public boolean isCurrentlyEnabled(DynamicBooleanFlag dynamicBooleanFlag) {
        return getBooleanInternal(dynamicBooleanFlag);
    }

    private boolean getBooleanInternal(Flag<Boolean> flag) {
        sync();
        Map<String, Boolean> map = this.mBooleanOverrides.get(flag.getNamespace());
        Boolean bool = map != null ? map.get(flag.getName()) : null;
        if (bool == null) {
            throw new IllegalStateException("Boolean flag being read but was not synced: " + flag);
        }
        return bool.booleanValue();
    }

    private <T extends Flag<?>> T addFlag(T t) {
        synchronized (FeatureFlags.class) {
            this.mDirtyFlags.add(t);
            this.mKnownFlags.add(t);
        }
        return t;
    }

    public void sync() {
        synchronized (FeatureFlags.class) {
            if (this.mDirtyFlags.isEmpty()) {
                return;
            }
            syncInternal(this.mDirtyFlags);
            this.mDirtyFlags.clear();
        }
    }

    protected void syncInternal(Set<Flag<?>> set) {
        IFeatureFlags bind = bind();
        ArrayList arrayList = new ArrayList();
        Iterator<Flag<?>> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(flagToSyncableFlag(it.next()));
        }
        List<SyncableFlag> list = Collections.EMPTY_LIST;
        try {
            list = bind.syncFlags(arrayList);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        for (Flag<?> flag : set) {
            Iterator<SyncableFlag> it2 = list.iterator();
            while (true) {
                if (it2.hasNext()) {
                    SyncableFlag next = it2.next();
                    if (flagEqualsSyncableFlag(flag, next)) {
                        if ((flag instanceof BooleanFlag) || (flag instanceof DynamicBooleanFlag)) {
                            addBooleanOverride(next.getNamespace(), next.getName(), next.getValue());
                        }
                    }
                } else if (flag instanceof BooleanFlag) {
                    addBooleanOverride(flag.getNamespace(), flag.getName(), ((BooleanFlag) flag).getDefault().booleanValue() ? "true" : "false");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addBooleanOverride(String str, String str2, String str3) {
        Map<String, Boolean> map = this.mBooleanOverrides.get(str);
        if (map == null) {
            map = new HashMap<>();
            this.mBooleanOverrides.put(str, map);
        }
        map.put(str2, Boolean.valueOf(parseBoolean(str3)));
    }

    private SyncableFlag flagToSyncableFlag(Flag<?> flag) {
        return new SyncableFlag(flag.getNamespace(), flag.getName(), flag.getDefault().toString(), flag instanceof DynamicFlag);
    }

    private IFeatureFlags bind() {
        if (this.mIFeatureFlags == null) {
            IFeatureFlags asInterface = IFeatureFlags.Stub.asInterface(ServiceManager.getService(Context.FEATURE_FLAGS_SERVICE));
            this.mIFeatureFlags = asInterface;
            try {
                asInterface.registerCallback(this.mIFeatureFlagsCallback);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to listen for flag changes!");
            }
        }
        return this.mIFeatureFlags;
    }

    static boolean parseBoolean(String str) {
        boolean z = str.equalsIgnoreCase("true") || str.equals("1") || str.equalsIgnoreCase("t") || str.equalsIgnoreCase("on");
        if (!z && !str.equalsIgnoreCase("false") && !str.equals("0") && !str.equalsIgnoreCase(FullBackup.FILES_TREE_TOKEN) && !str.equalsIgnoreCase("off")) {
            Log.e(TAG, "Tried parsing " + str + " as boolean but it doesn't look like one. Value expected to be one of true|false, 1|0, t|f, on|off.");
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean flagEqualsSyncableFlag(Flag<?> flag, SyncableFlag syncableFlag) {
        return flag.getName().equals(syncableFlag.getName()) && flag.getNamespace().equals(syncableFlag.getNamespace());
    }
}
