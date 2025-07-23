package com.android.systemui.scene.data.model;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneStackKt$asIterable$$inlined$Iterable$1 implements Iterable, KMappedMarker {
    public final /* synthetic */ SceneStack $this_asIterable$inlined;

    public SceneStackKt$asIterable$$inlined$Iterable$1(SceneStack sceneStack) {
        this.$this_asIterable$inlined = sceneStack;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return SequencesKt__SequenceBuilderKt.iterator(new SceneStackKt$asIterable$1$1(this.$this_asIterable$inlined, null));
    }
}
