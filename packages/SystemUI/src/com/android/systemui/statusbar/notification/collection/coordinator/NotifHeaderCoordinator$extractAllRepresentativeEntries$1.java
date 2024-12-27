package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.Sequence;

final /* synthetic */ class NotifHeaderCoordinator$extractAllRepresentativeEntries$1 extends FunctionReferenceImpl implements Function1 {
    public NotifHeaderCoordinator$extractAllRepresentativeEntries$1(Object obj) {
        super(1, obj, NotifHeaderCoordinator.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/ListEntry;)Lkotlin/sequences/Sequence;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Sequence invoke(ListEntry listEntry) {
        Sequence extractAllRepresentativeEntries;
        extractAllRepresentativeEntries = ((NotifHeaderCoordinator) this.receiver).extractAllRepresentativeEntries(listEntry);
        return extractAllRepresentativeEntries;
    }
}
