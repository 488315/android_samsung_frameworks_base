package androidx.compose.foundation.text.input.internal;

import androidx.compose.ui.text.input.EditCommand;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class RecordingInputConnection$performHandwritingGesture$1 extends Lambda implements Function1 {
    final /* synthetic */ RecordingInputConnection this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecordingInputConnection$performHandwritingGesture$1(RecordingInputConnection recordingInputConnection) {
        super(1);
        this.this$0 = recordingInputConnection;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        this.this$0.addEditCommandWithBatch((EditCommand) obj);
        return Unit.INSTANCE;
    }
}
