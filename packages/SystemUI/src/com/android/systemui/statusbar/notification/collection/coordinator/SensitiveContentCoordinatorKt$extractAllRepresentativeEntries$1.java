package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.Sequence;

final /* synthetic */ class SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1 extends FunctionReferenceImpl implements Function1 {
    public static final SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1 INSTANCE = new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1();

    public SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1() {
        super(1, SensitiveContentCoordinatorKt.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/ListEntry;)Lkotlin/sequences/Sequence;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Sequence invoke(ListEntry listEntry) {
        Sequence extractAllRepresentativeEntries;
        extractAllRepresentativeEntries = SensitiveContentCoordinatorKt.extractAllRepresentativeEntries(listEntry);
        return extractAllRepresentativeEntries;
    }
}
