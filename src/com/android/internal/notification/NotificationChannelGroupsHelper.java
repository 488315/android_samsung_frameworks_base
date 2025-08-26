package com.android.internal.notification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.util.ArrayMap;
import com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes5.dex */
public class NotificationChannelGroupsHelper {

    public static final class Params extends Record {
        private final Set<String> channelFilter;
        private final boolean includeAllBlockedWithFilter;
        private final boolean includeDeleted;
        private final boolean includeEmpty;
        private final boolean includeNonGrouped;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Params)) {
                return false;
            }
            Params params = (Params) obj;
            return this.includeDeleted == params.includeDeleted && this.includeNonGrouped == params.includeNonGrouped && this.includeEmpty == params.includeEmpty && this.includeAllBlockedWithFilter == params.includeAllBlockedWithFilter && Objects.equals(this.channelFilter, params.channelFilter);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Boolean.valueOf(this.includeDeleted), Boolean.valueOf(this.includeNonGrouped), Boolean.valueOf(this.includeEmpty), Boolean.valueOf(this.includeAllBlockedWithFilter), this.channelFilter};
        }

        public Params(boolean includeDeleted, boolean includeNonGrouped, boolean includeEmpty, boolean includeAllBlockedWithFilter, Set<String> channelFilter) {
            this.includeDeleted = includeDeleted;
            this.includeNonGrouped = includeNonGrouped;
            this.includeEmpty = includeEmpty;
            this.includeAllBlockedWithFilter = includeAllBlockedWithFilter;
            this.channelFilter = channelFilter;
        }

        public Set<String> channelFilter() {
            return this.channelFilter;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.includeDeleted, this.includeNonGrouped, this.includeEmpty, this.includeAllBlockedWithFilter, this.channelFilter);
        }

        public boolean includeAllBlockedWithFilter() {
            return this.includeAllBlockedWithFilter;
        }

        public boolean includeDeleted() {
            return this.includeDeleted;
        }

        public boolean includeEmpty() {
            return this.includeEmpty;
        }

        public boolean includeNonGrouped() {
            return this.includeNonGrouped;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), Params.class, "includeDeleted;includeNonGrouped;includeEmpty;includeAllBlockedWithFilter;channelFilter");
        }

        public static Params forAllGroups() {
            return new Params(false, false, true, true, null);
        }

        public static Params forAllChannels(boolean z) {
            return new Params(z, true, false, true, null);
        }

        public static Params onlySpecifiedOrBlockedChannels(Set<String> set) {
            return new Params(false, true, false, true, set);
        }
    }

    public static NotificationChannelGroup getGroupWithChannels(String str, Collection<NotificationChannel> collection, Map<String, NotificationChannelGroup> map, boolean z) {
        if (!map.containsKey(str)) {
            return null;
        }
        NotificationChannelGroup notificationChannelGroupM477clone = map.get(str).m477clone();
        notificationChannelGroupM477clone.setChannels(new ArrayList());
        for (NotificationChannel notificationChannel : collection) {
            if (z || !notificationChannel.isDeleted()) {
                if (str.equals(notificationChannel.getGroup())) {
                    notificationChannelGroupM477clone.addChannel(notificationChannel);
                }
            }
        }
        return notificationChannelGroupM477clone;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static List<NotificationChannelGroup> getGroupsWithChannels(Collection<NotificationChannel> collection, Map<String, NotificationChannelGroup> map, Params params) {
        ArrayMap arrayMap = new ArrayMap();
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(null, null);
        for (NotificationChannel notificationChannel : collection) {
            if (params.includeDeleted || !notificationChannel.isDeleted()) {
                if (params.channelFilter == null || ((params.includeAllBlockedWithFilter && notificationChannel.getImportance() == 0) || params.channelFilter.contains(notificationChannel.getId()))) {
                    if (!Flags.notificationClassification() || !NotificationChannel.SYSTEM_RESERVED_IDS.contains(notificationChannel.getId())) {
                        if (notificationChannel.getGroup() != null) {
                            if (map.get(notificationChannel.getGroup()) != null) {
                                NotificationChannelGroup notificationChannelGroupM477clone = (NotificationChannelGroup) arrayMap.get(notificationChannel.getGroup());
                                if (notificationChannelGroupM477clone == null) {
                                    notificationChannelGroupM477clone = map.get(notificationChannel.getGroup()).m477clone();
                                    notificationChannelGroupM477clone.setChannels(new ArrayList());
                                    arrayMap.put(notificationChannel.getGroup(), notificationChannelGroupM477clone);
                                }
                                notificationChannelGroupM477clone.addChannel(notificationChannel);
                            }
                        } else {
                            notificationChannelGroup.addChannel(notificationChannel);
                        }
                    }
                }
            }
        }
        if (params.includeNonGrouped && notificationChannelGroup.getChannels().size() > 0) {
            arrayMap.put(null, notificationChannelGroup);
        }
        if (params.includeEmpty) {
            for (NotificationChannelGroup notificationChannelGroup2 : map.values()) {
                if (!arrayMap.containsKey(notificationChannelGroup2.getId())) {
                    arrayMap.put(notificationChannelGroup2.getId(), notificationChannelGroup2);
                }
            }
        }
        return new ArrayList(arrayMap.values());
    }
}
