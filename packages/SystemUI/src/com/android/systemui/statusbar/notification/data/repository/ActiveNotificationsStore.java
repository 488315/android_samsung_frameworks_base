package com.android.systemui.statusbar.notification.data.repository;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.List;
import java.util.Map;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActiveNotificationsStore {
    public final Map groups;
    public final Map individuals;
    public final Map rankingsMap;
    public final List renderList;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Key {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Group extends Key {
            public final String key;

            public Group(String str) {
                super(null);
                this.key = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Group) && Intrinsics.areEqual(this.key, ((Group) obj).key);
            }

            public final int hashCode() {
                return this.key.hashCode();
            }

            public final String toString() {
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Group(key="), this.key, ")");
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Individual extends Key {
            public final String key;

            public Individual(String str) {
                super(null);
                this.key = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Individual) && Intrinsics.areEqual(this.key, ((Individual) obj).key);
            }

            public final int hashCode() {
                return this.key.hashCode();
            }

            public final String toString() {
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Individual(key="), this.key, ")");
            }
        }

        private Key() {
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ActiveNotificationsStore() {
        this(null, null, null, null, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationsStore)) {
            return false;
        }
        ActiveNotificationsStore activeNotificationsStore = (ActiveNotificationsStore) obj;
        return Intrinsics.areEqual(this.groups, activeNotificationsStore.groups) && Intrinsics.areEqual(this.individuals, activeNotificationsStore.individuals) && Intrinsics.areEqual(this.renderList, activeNotificationsStore.renderList) && Intrinsics.areEqual(this.rankingsMap, activeNotificationsStore.rankingsMap);
    }

    public final int hashCode() {
        return this.rankingsMap.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.renderList, (this.individuals.hashCode() + (this.groups.hashCode() * 31)) * 31, 31);
    }

    public final String toString() {
        return "ActiveNotificationsStore(groups=" + this.groups + ", individuals=" + this.individuals + ", renderList=" + this.renderList + ", rankingsMap=" + this.rankingsMap + ")";
    }

    public ActiveNotificationsStore(Map<String, ActiveNotificationGroupModel> map, Map<String, ActiveNotificationModel> map2, List<? extends Key> list, Map<String, Integer> map3) {
        this.groups = map;
        this.individuals = map2;
        this.renderList = list;
        this.rankingsMap = map3;
    }

    public ActiveNotificationsStore(Map map, Map map2, List list, Map map3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? MapsKt__MapsKt.emptyMap() : map, (i & 2) != 0 ? MapsKt__MapsKt.emptyMap() : map2, (i & 4) != 0 ? EmptyList.INSTANCE : list, (i & 8) != 0 ? MapsKt__MapsKt.emptyMap() : map3);
    }
}
