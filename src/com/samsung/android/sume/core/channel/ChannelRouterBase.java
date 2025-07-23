package com.samsung.android.sume.core.channel;

import android.app.PendingIntent$$ExternalSyntheticLambda2;
import android.util.NtpTrustedTime$$ExternalSyntheticLambda5;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.evaluate.EvalNone;
import com.samsung.android.sume.core.evaluate.Evaluator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
abstract class ChannelRouterBase extends BufferChannelGroupBase {
    protected Map<Evaluator, BufferChannel> evChannelMap;

    static /* synthetic */ BufferChannel lambda$new$2(BufferChannel bufferChannel, BufferChannel bufferChannel2) {
        return bufferChannel;
    }

    ChannelRouterBase(Map<Evaluator, BufferChannel> map) {
        Def.check(!map.isEmpty(), "no edge given", new Object[0]);
        Map map2 = (Map) map.entrySet().stream().collect(Collectors.partitioningBy(new Predicate() { // from class: com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ChannelRouterBase.lambda$new$0((Map.Entry) obj);
            }
        }));
        this.channels = (List) Optional.ofNullable((List) map2.get(true)).map(new Function() { // from class: com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ChannelRouterBase.lambda$new$1((List) obj);
            }
        }).orElseGet(new PendingIntent$$ExternalSyntheticLambda2());
        this.evChannelMap = (Map) Optional.ofNullable((List) map2.get(false)).map(new Function() { // from class: com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ChannelRouterBase.lambda$new$3((List) obj);
            }
        }).orElseGet(new NtpTrustedTime$$ExternalSyntheticLambda5());
    }

    static /* synthetic */ boolean lambda$new$0(Map.Entry entry) {
        return entry.getKey() == null || (entry.getKey() instanceof EvalNone);
    }

    static /* synthetic */ List lambda$new$1(List list) {
        return (List) list.stream().map(new ChannelRouterBase$$ExternalSyntheticLambda3()).collect(Collectors.toList());
    }

    static /* synthetic */ LinkedHashMap lambda$new$3(List list) {
        return (LinkedHashMap) list.stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Evaluator) ((Map.Entry) obj).getKey();
            }
        }, new ChannelRouterBase$$ExternalSyntheticLambda3(), new BinaryOperator() { // from class: com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ChannelRouterBase.lambda$new$2((BufferChannel) obj, (BufferChannel) obj2);
            }
        }, new NtpTrustedTime$$ExternalSyntheticLambda5()));
    }

    ChannelRouterBase(List<BufferChannel> list) {
        this.channels = list;
    }
}
