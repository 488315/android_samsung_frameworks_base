package android.flags;

import android.app.backup.FullBackup;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArraySet;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static FeatureFlags getInstance() {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new FeatureFlags();
            }
        }
        return sInstance;
    }

    public static void setInstance(FeatureFlags instance) {
        synchronized (sInstanceLock) {
            sInstance = instance;
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
        this.mIFeatureFlagsCallback =
                new IFeatureFlagsCallback.Stub() { // from class: android.flags.FeatureFlags.1
                    @Override // android.flags.IFeatureFlagsCallback
                    public void onFlagChange(SyncableFlag flag) {
                        for (Flag<?> f : FeatureFlags.this.mKnownFlags) {
                            if (FeatureFlags.flagEqualsSyncableFlag(f, flag)) {
                                if (f instanceof DynamicFlag) {
                                    if (f instanceof DynamicBooleanFlag) {
                                        String value = flag.getValue();
                                        if (value == null) {
                                            value =
                                                    ((DynamicBooleanFlag) f)
                                                            .getDefault()
                                                            .toString();
                                        }
                                        FeatureFlags.this.addBooleanOverride(
                                                flag.getNamespace(), flag.getName(), value);
                                    }
                                    FeatureFlags.this.onFlagChange((DynamicFlag) f);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                };
        this.mIFeatureFlags = iFeatureFlags;
        if (this.mIFeatureFlags != null) {
            try {
                this.mIFeatureFlags.registerCallback(this.mIFeatureFlagsCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Could not register callbacks!", e);
            }
        }
    }

    public static BooleanFlag booleanFlag(String namespace, String name, boolean def) {
        return (BooleanFlag) getInstance().addFlag(new BooleanFlag(namespace, name, def));
    }

    public static FusedOffFlag fusedOffFlag(String namespace, String name) {
        return (FusedOffFlag) getInstance().addFlag(new FusedOffFlag(namespace, name));
    }

    public static FusedOnFlag fusedOnFlag(String namespace, String name) {
        return (FusedOnFlag) getInstance().addFlag(new FusedOnFlag(namespace, name));
    }

    public static DynamicBooleanFlag dynamicBooleanFlag(
            String namespace, String name, boolean def) {
        return (DynamicBooleanFlag)
                getInstance().addFlag(new DynamicBooleanFlag(namespace, name, def));
    }

    public void addChangeListener(ChangeListener listener) {
        this.mListeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        this.mListeners.remove(listener);
    }

    protected void onFlagChange(DynamicFlag<?> flag) {
        for (ChangeListener l : this.mListeners) {
            l.onFlagChanged(flag);
        }
    }

    public boolean isEnabled(BooleanFlag flag) {
        return getBooleanInternal(flag);
    }

    public boolean isEnabled(FusedOffFlag flag) {
        return false;
    }

    public boolean isEnabled(FusedOnFlag flag) {
        return true;
    }

    public boolean isCurrentlyEnabled(DynamicBooleanFlag flag) {
        return getBooleanInternal(flag);
    }

    private boolean getBooleanInternal(Flag<Boolean> flag) {
        sync();
        Map<String, Boolean> ns = this.mBooleanOverrides.get(flag.getNamespace());
        Boolean value = null;
        if (ns != null) {
            Boolean value2 = ns.get(flag.getName());
            value = value2;
        }
        if (value == null) {
            throw new IllegalStateException("Boolean flag being read but was not synced: " + flag);
        }
        return value.booleanValue();
    }

    private <T extends Flag<?>> T addFlag(T flag) {
        synchronized (FeatureFlags.class) {
            this.mDirtyFlags.add(flag);
            this.mKnownFlags.add(flag);
        }
        return flag;
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

    protected void syncInternal(Set<Flag<?>> dirtyFlags) {
        IFeatureFlags iFeatureFlags = bind();
        List<SyncableFlag> syncableFlags = new ArrayList<>();
        Iterator<Flag<?>> it = dirtyFlags.iterator();
        while (it.hasNext()) {
            syncableFlags.add(flagToSyncableFlag(it.next()));
        }
        List<SyncableFlag> serverFlags = List.of();
        try {
            serverFlags = iFeatureFlags.syncFlags(syncableFlags);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        for (Flag<?> f : dirtyFlags) {
            boolean found = false;
            Iterator<SyncableFlag> it2 = serverFlags.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                SyncableFlag sf = it2.next();
                if (flagEqualsSyncableFlag(f, sf)) {
                    if ((f instanceof BooleanFlag) || (f instanceof DynamicBooleanFlag)) {
                        addBooleanOverride(sf.getNamespace(), sf.getName(), sf.getValue());
                    }
                    found = true;
                }
            }
            if (!found && (f instanceof BooleanFlag)) {
                addBooleanOverride(
                        f.getNamespace(),
                        f.getName(),
                        ((BooleanFlag) f).getDefault().booleanValue() ? "true" : "false");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addBooleanOverride(String namespace, String name, String override) {
        Map<String, Boolean> nsOverrides = this.mBooleanOverrides.get(namespace);
        if (nsOverrides == null) {
            nsOverrides = new HashMap();
            this.mBooleanOverrides.put(namespace, nsOverrides);
        }
        nsOverrides.put(name, Boolean.valueOf(parseBoolean(override)));
    }

    private SyncableFlag flagToSyncableFlag(Flag<?> f) {
        return new SyncableFlag(
                f.getNamespace(), f.getName(), f.getDefault().toString(), f instanceof DynamicFlag);
    }

    private IFeatureFlags bind() {
        if (this.mIFeatureFlags == null) {
            this.mIFeatureFlags =
                    IFeatureFlags.Stub.asInterface(
                            ServiceManager.getService(Context.FEATURE_FLAGS_SERVICE));
            try {
                this.mIFeatureFlags.registerCallback(this.mIFeatureFlagsCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to listen for flag changes!");
            }
        }
        return this.mIFeatureFlags;
    }

    static boolean parseBoolean(String value) {
        boolean result =
                value.equalsIgnoreCase("true")
                        || value.equals("1")
                        || value.equalsIgnoreCase("t")
                        || value.equalsIgnoreCase("on");
        if (!result
                && !value.equalsIgnoreCase("false")
                && !value.equals("0")
                && !value.equalsIgnoreCase(FullBackup.FILES_TREE_TOKEN)
                && !value.equalsIgnoreCase("off")) {
            Log.e(
                    TAG,
                    "Tried parsing "
                            + value
                            + " as boolean but it doesn't look like one. Value expected to be one"
                            + " of true|false, 1|0, t|f, on|off.");
        }
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean flagEqualsSyncableFlag(Flag<?> f, SyncableFlag sf) {
        return f.getName().equals(sf.getName()) && f.getNamespace().equals(sf.getNamespace());
    }
}
