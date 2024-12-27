package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayableInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Pair;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.server.SystemConfig;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class OverlayActorEnforcer {
    public final OverlayManagerService.PackageManagerHelperImpl mPackageManager;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class ActorState {
        public static final /* synthetic */ ActorState[] $VALUES;
        public static final ActorState ACTOR_NOT_FOUND;
        public static final ActorState ACTOR_NOT_PREINSTALLED;
        public static final ActorState ALLOWED;
        public static final ActorState ERROR_READING_OVERLAYABLE;
        public static final ActorState INVALID_ACTOR;
        public static final ActorState INVALID_OVERLAYABLE_ACTOR_NAME;
        public static final ActorState MISSING_ACTOR_NAME;
        public static final ActorState MISSING_LEGACY_PERMISSION;
        public static final ActorState MISSING_NAMESPACE;
        public static final ActorState MISSING_OVERLAYABLE;
        public static final ActorState MISSING_TARGET_OVERLAYABLE_NAME;
        public static final ActorState NO_NAMED_ACTORS;
        public static final ActorState NO_PACKAGES_FOR_UID;
        public static final ActorState TARGET_NOT_FOUND;
        public static final ActorState UNABLE_TO_GET_TARGET_OVERLAYABLE;

        static {
            ActorState actorState = new ActorState("TARGET_NOT_FOUND", 0);
            TARGET_NOT_FOUND = actorState;
            ActorState actorState2 = new ActorState("NO_PACKAGES_FOR_UID", 1);
            NO_PACKAGES_FOR_UID = actorState2;
            ActorState actorState3 = new ActorState("MISSING_TARGET_OVERLAYABLE_NAME", 2);
            MISSING_TARGET_OVERLAYABLE_NAME = actorState3;
            ActorState actorState4 = new ActorState("MISSING_LEGACY_PERMISSION", 3);
            MISSING_LEGACY_PERMISSION = actorState4;
            ActorState actorState5 = new ActorState("ERROR_READING_OVERLAYABLE", 4);
            ERROR_READING_OVERLAYABLE = actorState5;
            ActorState actorState6 = new ActorState("UNABLE_TO_GET_TARGET_OVERLAYABLE", 5);
            UNABLE_TO_GET_TARGET_OVERLAYABLE = actorState6;
            ActorState actorState7 = new ActorState("MISSING_OVERLAYABLE", 6);
            MISSING_OVERLAYABLE = actorState7;
            ActorState actorState8 = new ActorState("INVALID_OVERLAYABLE_ACTOR_NAME", 7);
            INVALID_OVERLAYABLE_ACTOR_NAME = actorState8;
            ActorState actorState9 = new ActorState("NO_NAMED_ACTORS", 8);
            NO_NAMED_ACTORS = actorState9;
            ActorState actorState10 = new ActorState("MISSING_NAMESPACE", 9);
            MISSING_NAMESPACE = actorState10;
            ActorState actorState11 = new ActorState("MISSING_ACTOR_NAME", 10);
            MISSING_ACTOR_NAME = actorState11;
            ActorState actorState12 = new ActorState("ACTOR_NOT_FOUND", 11);
            ACTOR_NOT_FOUND = actorState12;
            ActorState actorState13 = new ActorState("ACTOR_NOT_PREINSTALLED", 12);
            ACTOR_NOT_PREINSTALLED = actorState13;
            ActorState actorState14 = new ActorState("INVALID_ACTOR", 13);
            INVALID_ACTOR = actorState14;
            ActorState actorState15 = new ActorState("ALLOWED", 14);
            ALLOWED = actorState15;
            $VALUES =
                    new ActorState[] {
                        actorState,
                        actorState2,
                        actorState3,
                        actorState4,
                        actorState5,
                        actorState6,
                        actorState7,
                        actorState8,
                        actorState9,
                        actorState10,
                        actorState11,
                        actorState12,
                        actorState13,
                        actorState14,
                        actorState15
                    };
        }

        public static ActorState valueOf(String str) {
            return (ActorState) Enum.valueOf(ActorState.class, str);
        }

        public static ActorState[] values() {
            return (ActorState[]) $VALUES.clone();
        }
    }

    public OverlayActorEnforcer(
            OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl) {
        this.mPackageManager = packageManagerHelperImpl;
    }

    public static Pair getPackageNameForActor(String str, Map map) {
        Uri parse = Uri.parse(str);
        String scheme = parse.getScheme();
        List<String> pathSegments = parse.getPathSegments();
        if (!"overlay".equals(scheme) || CollectionUtils.size(pathSegments) != 1) {
            return Pair.create(null, ActorState.INVALID_OVERLAYABLE_ACTOR_NAME);
        }
        if (map.isEmpty()) {
            return Pair.create(null, ActorState.NO_NAMED_ACTORS);
        }
        Map map2 = (Map) map.get(parse.getAuthority());
        if (ArrayUtils.isEmpty(map2)) {
            return Pair.create(null, ActorState.MISSING_NAMESPACE);
        }
        String str2 = (String) map2.get(pathSegments.get(0));
        return TextUtils.isEmpty(str2)
                ? Pair.create(null, ActorState.MISSING_ACTOR_NAME)
                : Pair.create(str2, ActorState.ALLOWED);
    }

    public ActorState isAllowedActor(String str, OverlayInfo overlayInfo, int i, int i2) {
        ActorState actorState = ActorState.ALLOWED;
        if (i == 0 || i == 1000) {
            return actorState;
        }
        String str2 = overlayInfo.targetPackageName;
        OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl =
                this.mPackageManager;
        PackageState packageStateForUser =
                packageManagerHelperImpl.getPackageStateForUser(i2, str2);
        String[] strArr = null;
        AndroidPackage androidPackage =
                packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
        if (androidPackage == null) {
            return ActorState.TARGET_NOT_FOUND;
        }
        if (androidPackage.isDebuggable()) {
            return actorState;
        }
        try {
            strArr = packageManagerHelperImpl.mPackageManager.getPackagesForUid(i);
        } catch (RemoteException unused) {
        }
        if (ArrayUtils.isEmpty(strArr)) {
            return ActorState.NO_PACKAGES_FOR_UID;
        }
        if (ArrayUtils.contains(strArr, str2)) {
            return actorState;
        }
        String str3 = overlayInfo.targetOverlayableName;
        boolean isEmpty = TextUtils.isEmpty(str3);
        ActorState actorState2 = ActorState.MISSING_LEGACY_PERMISSION;
        if (isEmpty) {
            try {
                if (packageManagerHelperImpl.doesTargetDefineOverlayable(i2, str2)) {
                    return ActorState.MISSING_TARGET_OVERLAYABLE_NAME;
                }
                try {
                    packageManagerHelperImpl.mContext.enforceCallingOrSelfPermission(
                            "android.permission.CHANGE_OVERLAY_PACKAGES", str);
                    return actorState;
                } catch (SecurityException unused2) {
                    return actorState2;
                }
            } catch (IOException unused3) {
                return ActorState.ERROR_READING_OVERLAYABLE;
            }
        }
        try {
            OverlayableInfo overlayableForTarget =
                    packageManagerHelperImpl.getOverlayableForTarget(i2, str2, str3);
            if (overlayableForTarget == null) {
                return ActorState.MISSING_OVERLAYABLE;
            }
            String str4 = overlayableForTarget.actor;
            if (TextUtils.isEmpty(str4)) {
                try {
                    packageManagerHelperImpl.mContext.enforceCallingOrSelfPermission(
                            "android.permission.CHANGE_OVERLAY_PACKAGES", str);
                    return actorState;
                } catch (SecurityException unused4) {
                    return actorState2;
                }
            }
            Map map = SystemConfig.getInstance().mNamedActors;
            if (map == null) {
                map = Collections.emptyMap();
            }
            Pair packageNameForActor = getPackageNameForActor(str4, map);
            ActorState actorState3 = (ActorState) packageNameForActor.second;
            if (actorState3 != actorState) {
                return actorState3;
            }
            String str5 = (String) packageNameForActor.first;
            PackageState packageStateForUser2 =
                    packageManagerHelperImpl.getPackageStateForUser(i2, str5);
            return (packageStateForUser2 == null
                            || packageStateForUser2.getAndroidPackage() == null)
                    ? ActorState.ACTOR_NOT_FOUND
                    : !packageStateForUser2.isSystem()
                            ? ActorState.ACTOR_NOT_PREINSTALLED
                            : ArrayUtils.contains(strArr, str5)
                                    ? actorState
                                    : ActorState.INVALID_ACTOR;
        } catch (IOException unused5) {
            return ActorState.UNABLE_TO_GET_TARGET_OVERLAYABLE;
        }
    }
}
