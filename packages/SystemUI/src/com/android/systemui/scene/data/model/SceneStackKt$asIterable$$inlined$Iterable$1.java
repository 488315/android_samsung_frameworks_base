package com.android.systemui.scene.data.model;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

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
