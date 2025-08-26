package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.channels.ChannelResult;

/* loaded from: classes4.dex */
final /* synthetic */ class BufferedChannel$onReceiveCatching$2 extends FunctionReferenceImpl implements Function3 {
    public static final BufferedChannel$onReceiveCatching$2 INSTANCE = new BufferedChannel$onReceiveCatching$2();

    public BufferedChannel$onReceiveCatching$2() {
        super(3, BufferedChannel.class, "processResultSelectReceiveCatching", "processResultSelectReceiveCatching(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BufferedChannel bufferedChannel = (BufferedChannel) obj;
        bufferedChannel.getClass();
        if (obj3 == BufferedChannelKt.CHANNEL_CLOSED) {
            ChannelResult.Companion companion = ChannelResult.Companion;
            Throwable closeCause = bufferedChannel.getCloseCause();
            companion.getClass();
            obj3 = ChannelResult.Companion.m3480closedJP2dKIU(closeCause);
        } else {
            ChannelResult.Companion.getClass();
        }
        return ChannelResult.m3477boximpl(obj3);
    }
}
