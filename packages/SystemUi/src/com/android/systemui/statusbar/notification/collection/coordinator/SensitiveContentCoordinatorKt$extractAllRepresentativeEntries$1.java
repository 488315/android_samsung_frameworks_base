package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1 extends FunctionReferenceImpl implements Function1 {
    public static final SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1 INSTANCE = new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1();

    public SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1() {
        super(1, SensitiveContentCoordinatorKt.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/ListEntry;)Lkotlin/sequences/Sequence;", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2((ListEntry) obj, null));
    }
}
