package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.Sequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class NotifHeaderCoordinator$extractAllRepresentativeEntries$1 extends FunctionReferenceImpl implements Function1 {
    public NotifHeaderCoordinator$extractAllRepresentativeEntries$1(Object obj) {
        super(1, obj, NotifHeaderCoordinator.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/PipelineEntry;)Lkotlin/sequences/Sequence;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
    public final Sequence mo779invoke(PipelineEntry pipelineEntry) {
        Sequence extractAllRepresentativeEntries;
        extractAllRepresentativeEntries = ((NotifHeaderCoordinator) this.receiver).extractAllRepresentativeEntries(pipelineEntry);
        return extractAllRepresentativeEntries;
    }
}
