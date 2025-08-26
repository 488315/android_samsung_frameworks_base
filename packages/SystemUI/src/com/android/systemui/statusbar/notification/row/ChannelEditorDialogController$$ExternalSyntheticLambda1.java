package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import java.util.Comparator;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ChannelEditorDialogController$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ChannelEditorDialogController$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) obj;
                return SequencesKt___SequencesKt.filterNot(new SequencesKt___SequencesKt$sortedWith$1(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(notificationChannelGroup.getChannels()), new Comparator() { // from class: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$getDisplayableChannels$lambda$4$$inlined$compareBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        String id;
                        String id2;
                        CharSequence name = notificationChannelGroup.getName();
                        if (name == null || (id = name.toString()) == null) {
                            id = notificationChannelGroup.getId();
                        }
                        CharSequence name2 = notificationChannelGroup.getName();
                        if (name2 == null || (id2 = name2.toString()) == null) {
                            id2 = notificationChannelGroup.getId();
                        }
                        return ComparisonsKt__ComparisonsKt.compareValues(id, id2);
                    }
                }), new ChannelEditorDialogController$$ExternalSyntheticLambda1(1));
            default:
                return Boolean.valueOf(((NotificationChannel) obj).isImportanceLockedByCriticalDeviceFunction());
        }
    }
}
