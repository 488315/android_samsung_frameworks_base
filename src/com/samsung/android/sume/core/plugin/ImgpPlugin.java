package com.samsung.android.sume.core.plugin;

import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda8;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.functional.OperatorChain;
import com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda4;
import com.samsung.android.sume.core.functional.OperatorWrapper;
import com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import com.samsung.android.sume.core.types.ImgpType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class ImgpPlugin extends PluginFixture<ImgpPlugin> {
    private Map<Enum<?>, Operator> processorMap;

    public enum Type {
        ANY,
        SIMGP,
        OPENCV,
        RENDERSCRIPT,
        CUSTOM,
        NATIVE_UNIIMGP
    }

    public ImgpPlugin(Plugin<ImgpPlugin> plugin) {
        super(plugin);
        if (this.processorMap == null) {
            this.processorMap = new HashMap();
        }
    }

    public Operator getImgProcessor(Enum<?> r2) {
        if (r2 == ImgpType.ANY) {
            return OperatorWrapper.of(this.processorMap);
        }
        if (this.processorMap.containsKey(r2)) {
            return OperatorWrapper.of(r2, this.processorMap.get(r2));
        }
        return null;
    }

    public Operator getImgProcessor(final String str) {
        return (Operator) this.processorMap.keySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.plugin.ImgpPlugin$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Enum) obj).name().equals(str);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.plugin.ImgpPlugin$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9611x24c2933((Enum) obj);
            }
        }).orElse(null);
    }

    /* renamed from: lambda$getImgProcessor$1$com-samsung-android-sume-core-plugin-ImgpPlugin, reason: not valid java name */
    /* synthetic */ Operator m9611x24c2933(Enum r1) {
        return OperatorWrapper.of(r1, this.processorMap.get(r1));
    }

    public ImgpPlugin setImgProcessor(Enum<?> r2, Operator operator) {
        if (this.processorMap == null) {
            this.processorMap = new HashMap();
        }
        this.processorMap.put(r2, operator);
        return this;
    }

    @Override // com.samsung.android.sume.core.plugin.PluginFixture
    public boolean validate() {
        return !this.processorMap.keySet().isEmpty();
    }

    public static ImgpPlugin join(ImgpPlugin... imgpPluginArr) {
        return new ImgpPlugin(new ImgpPluginGroup(imgpPluginArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ImgpPluginGroup implements Plugin<ImgpPlugin> {
        private final Map<Enum<?>, Operator> processorMap;

        ImgpPluginGroup(ImgpPlugin... imgpPluginArr) {
            this.processorMap = (Map) Arrays.stream(imgpPluginArr).flatMap(new Function() { // from class: com.samsung.android.sume.core.plugin.ImgpPlugin$ImgpPluginGroup$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((ImgpPlugin) obj).processorMap.entrySet().stream();
                }
            }).collect(Collectors.toMap(new OperatorWrapper$$ExternalSyntheticLambda3(), new OperatorMap$$ExternalSyntheticLambda4(), new BinaryOperator() { // from class: com.samsung.android.sume.core.plugin.ImgpPlugin$ImgpPluginGroup$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ImgpPlugin.ImgpPluginGroup.lambda$new$1((Operator) obj, (Operator) obj2);
                }
            }, new MediaBufferBase$$ExternalSyntheticLambda8()));
        }

        static /* synthetic */ Operator lambda$new$1(Operator operator, Operator operator2) {
            return new OperatorChain(operator, operator2);
        }

        @Override // com.samsung.android.sume.core.plugin.Plugin
        public void bindToFixture(ImgpPlugin imgpPlugin) {
            imgpPlugin.processorMap = this.processorMap;
        }
    }
}
