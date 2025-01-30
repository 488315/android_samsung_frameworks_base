package com.android.server.om;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.util.CollectionUtils;
import com.android.server.SystemConfig;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class OverlayReferenceMapper {
    public boolean mDeferRebuild;
    public final Provider mProvider;
    public final Object mLock = new Object();
    public final ArrayMap mActorToTargetToOverlays = new ArrayMap();
    public final ArrayMap mActorPkgToPkgs = new ArrayMap();

    public interface Provider {
        String getActorPkg(String str);

        Map getTargetToOverlayables(AndroidPackage androidPackage);
    }

    public OverlayReferenceMapper(boolean z, Provider provider) {
        this.mDeferRebuild = z;
        this.mProvider = provider == null ? new Provider() { // from class: com.android.server.om.OverlayReferenceMapper.1
            @Override // com.android.server.om.OverlayReferenceMapper.Provider
            public String getActorPkg(String str) {
                return (String) OverlayActorEnforcer.getPackageNameForActor(str, SystemConfig.getInstance().getNamedActors()).first;
            }

            @Override // com.android.server.om.OverlayReferenceMapper.Provider
            public Map getTargetToOverlayables(AndroidPackage androidPackage) {
                String overlayTarget = androidPackage.getOverlayTarget();
                if (TextUtils.isEmpty(overlayTarget)) {
                    return Collections.emptyMap();
                }
                String overlayTargetOverlayableName = androidPackage.getOverlayTargetOverlayableName();
                HashMap hashMap = new HashMap();
                HashSet hashSet = new HashSet();
                hashSet.add(overlayTargetOverlayableName);
                hashMap.put(overlayTarget, hashSet);
                return hashMap;
            }
        } : provider;
    }

    public Map getActorPkgToPkgs() {
        return this.mActorPkgToPkgs;
    }

    public boolean isValidActor(String str, String str2) {
        boolean z;
        synchronized (this.mLock) {
            ensureMapBuilt();
            Set set = (Set) this.mActorPkgToPkgs.get(str2);
            z = set != null && set.contains(str);
        }
        return z;
    }

    public ArraySet addPkg(AndroidPackage androidPackage, Map map) {
        ArraySet arraySet;
        synchronized (this.mLock) {
            arraySet = new ArraySet();
            if (!androidPackage.getOverlayables().isEmpty()) {
                addTarget(androidPackage, map, arraySet);
            }
            if (!this.mProvider.getTargetToOverlayables(androidPackage).isEmpty()) {
                addOverlay(androidPackage, map, arraySet);
            }
            if (!this.mDeferRebuild) {
                rebuild();
            }
        }
        return arraySet;
    }

    public ArraySet removePkg(String str) {
        ArraySet arraySet;
        synchronized (this.mLock) {
            arraySet = new ArraySet();
            removeTarget(str, arraySet);
            removeOverlay(str, arraySet);
            if (!this.mDeferRebuild) {
                rebuild();
            }
        }
        return arraySet;
    }

    public final void removeTarget(String str, Collection collection) {
        synchronized (this.mLock) {
            for (int size = this.mActorToTargetToOverlays.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.valueAt(size);
                if (arrayMap.containsKey(str)) {
                    arrayMap.remove(str);
                    collection.add(this.mProvider.getActorPkg((String) this.mActorToTargetToOverlays.keyAt(size)));
                    if (arrayMap.isEmpty()) {
                        this.mActorToTargetToOverlays.removeAt(size);
                    }
                }
            }
        }
    }

    public final void addTarget(AndroidPackage androidPackage, Map map, Collection collection) {
        synchronized (this.mLock) {
            String packageName = androidPackage.getPackageName();
            removeTarget(packageName, collection);
            Map overlayables = androidPackage.getOverlayables();
            for (String str : overlayables.keySet()) {
                String str2 = (String) overlayables.get(str);
                addTargetToMap(str2, packageName, collection);
                for (AndroidPackage androidPackage2 : map.values()) {
                    Set set = (Set) this.mProvider.getTargetToOverlayables(androidPackage2).get(packageName);
                    if (!CollectionUtils.isEmpty(set) && set.contains(str)) {
                        addOverlayToMap(str2, packageName, androidPackage2.getPackageName(), collection);
                    }
                }
            }
        }
    }

    public final void removeOverlay(String str, Collection collection) {
        synchronized (this.mLock) {
            for (int size = this.mActorToTargetToOverlays.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.valueAt(size);
                for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                    if (((Set) arrayMap.valueAt(size2)).remove(str)) {
                        collection.add(this.mProvider.getActorPkg((String) this.mActorToTargetToOverlays.keyAt(size)));
                    }
                }
                if (arrayMap.isEmpty()) {
                    this.mActorToTargetToOverlays.removeAt(size);
                }
            }
        }
    }

    public final void addOverlay(AndroidPackage androidPackage, Map map, Collection collection) {
        synchronized (this.mLock) {
            String packageName = androidPackage.getPackageName();
            removeOverlay(packageName, collection);
            for (Map.Entry entry : this.mProvider.getTargetToOverlayables(androidPackage).entrySet()) {
                String str = (String) entry.getKey();
                Set set = (Set) entry.getValue();
                AndroidPackage androidPackage2 = (AndroidPackage) map.get(str);
                if (androidPackage2 != null) {
                    String packageName2 = androidPackage2.getPackageName();
                    Map overlayables = androidPackage2.getOverlayables();
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) overlayables.get((String) it.next());
                        if (!TextUtils.isEmpty(str2)) {
                            addOverlayToMap(str2, packageName2, packageName, collection);
                        }
                    }
                }
            }
        }
    }

    public void rebuildIfDeferred() {
        synchronized (this.mLock) {
            if (this.mDeferRebuild) {
                rebuild();
                this.mDeferRebuild = false;
            }
        }
    }

    public final void ensureMapBuilt() {
        if (this.mDeferRebuild) {
            rebuildIfDeferred();
            Slog.w("OverlayReferenceMapper", "The actor map was queried before the system was ready, which mayresult in decreased performance.");
        }
    }

    public final void rebuild() {
        synchronized (this.mLock) {
            this.mActorPkgToPkgs.clear();
            for (String str : this.mActorToTargetToOverlays.keySet()) {
                String actorPkg = this.mProvider.getActorPkg(str);
                if (!TextUtils.isEmpty(actorPkg)) {
                    ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.get(str);
                    HashSet hashSet = new HashSet();
                    for (String str2 : arrayMap.keySet()) {
                        Set set = (Set) arrayMap.get(str2);
                        hashSet.add(str2);
                        hashSet.addAll(set);
                    }
                    this.mActorPkgToPkgs.put(actorPkg, hashSet);
                }
            }
        }
    }

    public final void addTargetToMap(String str, String str2, Collection collection) {
        ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.get(str);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.mActorToTargetToOverlays.put(str, arrayMap);
        }
        if (((ArraySet) arrayMap.get(str2)) == null) {
            arrayMap.put(str2, new ArraySet());
        }
        collection.add(this.mProvider.getActorPkg(str));
    }

    public final void addOverlayToMap(String str, String str2, String str3, Collection collection) {
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.get(str);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mActorToTargetToOverlays.put(str, arrayMap);
            }
            ArraySet arraySet = (ArraySet) arrayMap.get(str2);
            if (arraySet == null) {
                arraySet = new ArraySet();
                arrayMap.put(str2, arraySet);
            }
            arraySet.add(str3);
        }
        collection.add(this.mProvider.getActorPkg(str));
    }
}
