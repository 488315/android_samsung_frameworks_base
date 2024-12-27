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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class OverlayReferenceMapper {
    public final Provider mProvider;
    public final Object mLock = new Object();
    public final ArrayMap mActorToTargetToOverlays = new ArrayMap();
    public final ArrayMap mActorPkgToPkgs = new ArrayMap();
    public boolean mDeferRebuild = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.om.OverlayReferenceMapper$1, reason: invalid class name */
    public final class AnonymousClass1 implements Provider {
        public final String getActorPkg(String str) {
            Map map = SystemConfig.getInstance().mNamedActors;
            if (map == null) {
                map = Collections.emptyMap();
            }
            return (String) OverlayActorEnforcer.getPackageNameForActor(str, map).first;
        }

        public final Map getTargetToOverlayables(AndroidPackage androidPackage) {
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Provider {}

    public OverlayReferenceMapper(Provider provider) {
        this.mProvider = provider == null ? new AnonymousClass1() : provider;
    }

    public final void addOverlay(AndroidPackage androidPackage, Map map, Collection collection) {
        synchronized (this.mLock) {
            try {
                String packageName = androidPackage.getPackageName();
                removeOverlay(packageName, collection);
                for (Map.Entry entry :
                        ((AnonymousClass1) this.mProvider)
                                .getTargetToOverlayables(androidPackage)
                                .entrySet()) {
                    String str = (String) entry.getKey();
                    Set set = (Set) entry.getValue();
                    AndroidPackage androidPackage2 = (AndroidPackage) ((ArrayMap) map).get(str);
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addOverlayToMap(String str, String str2, String str3, Collection collection) {
        synchronized (this.mLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
        ((ArraySet) collection).add(((AnonymousClass1) this.mProvider).getActorPkg(str));
    }

    public final ArraySet addPkg(AndroidPackage androidPackage, Map map) {
        ArraySet arraySet;
        synchronized (this.mLock) {
            try {
                arraySet = new ArraySet();
                if (!androidPackage.getOverlayables().isEmpty()) {
                    addTarget(androidPackage, map, arraySet);
                }
                if (!((AnonymousClass1) this.mProvider)
                        .getTargetToOverlayables(androidPackage)
                        .isEmpty()) {
                    addOverlay(androidPackage, map, arraySet);
                }
                if (!this.mDeferRebuild) {
                    rebuild();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arraySet;
    }

    public final void addTarget(AndroidPackage androidPackage, Map map, Collection collection) {
        synchronized (this.mLock) {
            try {
                String packageName = androidPackage.getPackageName();
                removeTarget(packageName, collection);
                Map overlayables = androidPackage.getOverlayables();
                for (String str : overlayables.keySet()) {
                    String str2 = (String) overlayables.get(str);
                    addTargetToMap(str2, packageName, collection);
                    for (AndroidPackage androidPackage2 : ((ArrayMap) map).values()) {
                        Set set =
                                (Set)
                                        ((AnonymousClass1) this.mProvider)
                                                .getTargetToOverlayables(androidPackage2)
                                                .get(packageName);
                        if (!CollectionUtils.isEmpty(set) && set.contains(str)) {
                            addOverlayToMap(
                                    str2,
                                    packageName,
                                    androidPackage2.getPackageName(),
                                    collection);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
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
        ((ArraySet) collection).add(((AnonymousClass1) this.mProvider).getActorPkg(str));
    }

    public Map getActorPkgToPkgs() {
        return this.mActorPkgToPkgs;
    }

    public final boolean isValidActor(String str, String str2) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = false;
                if (this.mDeferRebuild) {
                    synchronized (this.mLock) {
                        if (this.mDeferRebuild) {
                            rebuild();
                            this.mDeferRebuild = false;
                        }
                    }
                    Slog.w(
                            "OverlayReferenceMapper",
                            "The actor map was queried before the system was ready, which mayresult"
                                + " in decreased performance.");
                }
                Set set = (Set) this.mActorPkgToPkgs.get(str2);
                if (set != null && set.contains(str)) {
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        }
        return z;
    }

    public final void rebuild() {
        synchronized (this.mLock) {
            try {
                this.mActorPkgToPkgs.clear();
                for (String str : this.mActorToTargetToOverlays.keySet()) {
                    String actorPkg = ((AnonymousClass1) this.mProvider).getActorPkg(str);
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOverlay(String str, Collection collection) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mActorToTargetToOverlays.size() - 1; size >= 0; size--) {
                    ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.valueAt(size);
                    for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                        if (((Set) arrayMap.valueAt(size2)).remove(str)) {
                            ((ArraySet) collection)
                                    .add(
                                            ((AnonymousClass1) this.mProvider)
                                                    .getActorPkg(
                                                            (String)
                                                                    this.mActorToTargetToOverlays
                                                                            .keyAt(size)));
                        }
                    }
                    if (arrayMap.isEmpty()) {
                        this.mActorToTargetToOverlays.removeAt(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ArraySet removePkg(String str) {
        ArraySet arraySet;
        synchronized (this.mLock) {
            try {
                arraySet = new ArraySet();
                removeTarget(str, arraySet);
                removeOverlay(str, arraySet);
                if (!this.mDeferRebuild) {
                    rebuild();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arraySet;
    }

    public final void removeTarget(String str, Collection collection) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mActorToTargetToOverlays.size() - 1; size >= 0; size--) {
                    ArrayMap arrayMap = (ArrayMap) this.mActorToTargetToOverlays.valueAt(size);
                    if (arrayMap.containsKey(str)) {
                        arrayMap.remove(str);
                        ((ArraySet) collection)
                                .add(
                                        ((AnonymousClass1) this.mProvider)
                                                .getActorPkg(
                                                        (String)
                                                                this.mActorToTargetToOverlays.keyAt(
                                                                        size)));
                        if (arrayMap.isEmpty()) {
                            this.mActorToTargetToOverlays.removeAt(size);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
