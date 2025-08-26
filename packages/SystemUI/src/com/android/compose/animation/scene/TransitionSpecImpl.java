package com.android.compose.animation.scene;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TransitionSpecImpl {
    public final Integer cuj;
    public final ContentKey from;
    public final TransitionKey key;
    public final Function1 previewTransformationSpec;
    public final Function1 reversePreviewTransformationSpec;
    public final ContentKey to;
    public final Function1 transformationSpec;

    public TransitionSpecImpl(TransitionKey transitionKey, ContentKey contentKey, ContentKey contentKey2, Integer num, Function1 function1, Function1 function12, Function1 function13) {
        this.key = transitionKey;
        this.from = contentKey;
        this.to = contentKey2;
        this.cuj = num;
        this.previewTransformationSpec = function1;
        this.reversePreviewTransformationSpec = function12;
        this.transformationSpec = function13;
    }

    public /* synthetic */ TransitionSpecImpl(TransitionKey transitionKey, ContentKey contentKey, ContentKey contentKey2, Integer num, Function1 function1, Function1 function12, Function1 function13, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionKey, contentKey, contentKey2, num, (i & 16) != 0 ? null : function1, (i & 32) != 0 ? null : function12, function13);
    }
}
