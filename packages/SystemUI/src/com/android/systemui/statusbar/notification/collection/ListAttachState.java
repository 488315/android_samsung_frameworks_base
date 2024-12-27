package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ListAttachState {
    public static final Companion Companion = new Companion(null);
    public NotifFilter excludingFilter;
    public String groupPruneReason;
    public GroupEntry parent;
    public NotifPromoter promoter;
    public NotifSection section;
    public int stableIndex;
    public final SuppressedAttachState suppressedChanges;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ ListAttachState(GroupEntry groupEntry, NotifSection notifSection, NotifFilter notifFilter, NotifPromoter notifPromoter, String str, SuppressedAttachState suppressedAttachState, DefaultConstructorMarker defaultConstructorMarker) {
        this(groupEntry, notifSection, notifFilter, notifPromoter, str, suppressedAttachState);
    }

    public static final ListAttachState create() {
        Companion.getClass();
        SuppressedAttachState.Companion.getClass();
        return new ListAttachState(null, null, null, null, null, new SuppressedAttachState(null, 0 == true ? 1 : 0, false, 0 == true ? 1 : 0), null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ListAttachState)) {
            return false;
        }
        ListAttachState listAttachState = (ListAttachState) obj;
        return Intrinsics.areEqual(this.parent, listAttachState.parent) && Intrinsics.areEqual(this.section, listAttachState.section) && Intrinsics.areEqual(this.excludingFilter, listAttachState.excludingFilter) && Intrinsics.areEqual(this.promoter, listAttachState.promoter) && Intrinsics.areEqual(this.groupPruneReason, listAttachState.groupPruneReason) && Intrinsics.areEqual(this.suppressedChanges, listAttachState.suppressedChanges);
    }

    public final int hashCode() {
        GroupEntry groupEntry = this.parent;
        int hashCode = (groupEntry == null ? 0 : groupEntry.hashCode()) * 31;
        NotifSection notifSection = this.section;
        int hashCode2 = (hashCode + (notifSection == null ? 0 : notifSection.hashCode())) * 31;
        NotifFilter notifFilter = this.excludingFilter;
        int hashCode3 = (hashCode2 + (notifFilter == null ? 0 : notifFilter.hashCode())) * 31;
        NotifPromoter notifPromoter = this.promoter;
        int hashCode4 = (hashCode3 + (notifPromoter == null ? 0 : notifPromoter.hashCode())) * 31;
        String str = this.groupPruneReason;
        return this.suppressedChanges.hashCode() + ((hashCode4 + (str != null ? str.hashCode() : 0)) * 31);
    }

    public final String toString() {
        return "ListAttachState(parent=" + this.parent + ", section=" + this.section + ", excludingFilter=" + this.excludingFilter + ", promoter=" + this.promoter + ", groupPruneReason=" + this.groupPruneReason + ", suppressedChanges=" + this.suppressedChanges + ")";
    }

    private ListAttachState(GroupEntry groupEntry, NotifSection notifSection, NotifFilter notifFilter, NotifPromoter notifPromoter, String str, SuppressedAttachState suppressedAttachState) {
        this.parent = groupEntry;
        this.section = notifSection;
        this.excludingFilter = notifFilter;
        this.promoter = notifPromoter;
        this.groupPruneReason = str;
        this.suppressedChanges = suppressedAttachState;
        this.stableIndex = -1;
    }
}
