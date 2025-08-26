package androidx.activity.compose;

import androidx.activity.OnBackPressedCallback;
import java.util.concurrent.CancellationException;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes.dex */
public final class OnBackInstance {
    public final BufferedChannel channel = ChannelKt.Channel$default(-2, BufferOverflow.SUSPEND, null, 4);
    public boolean isPredictiveBack;
    public final StandaloneCoroutine job;

    public OnBackInstance(CoroutineScope coroutineScope, boolean z, Function2 function2, OnBackPressedCallback onBackPressedCallback) {
        this.isPredictiveBack = z;
        this.job = BuildersKt.launch$default(coroutineScope, null, null, new OnBackInstance$job$1(onBackPressedCallback, function2, this, null), 3);
    }

    public final void cancel() {
        this.channel.closeOrCancelImpl(new CancellationException("onBack cancelled"), true);
        this.job.cancel(null);
    }
}
