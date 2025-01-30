package com.android.server.integrity.serializer;

import android.content.integrity.AtomicFormula;
import android.content.integrity.CompoundFormula;
import android.content.integrity.InstallerAllowedByManifestFormula;
import android.content.integrity.IntegrityFormula;
import android.content.integrity.IntegrityUtils;
import android.content.integrity.Rule;
import com.android.internal.util.Preconditions;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda0;
import com.android.server.integrity.model.BitOutputStream;
import com.android.server.integrity.model.ByteTrackedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class RuleBinarySerializer implements RuleSerializer {
    @Override // com.android.server.integrity.serializer.RuleSerializer
    public void serialize(List list, Optional optional, OutputStream outputStream, OutputStream outputStream2) {
        try {
            if (list == null) {
                throw new IllegalArgumentException("Null rules cannot be serialized.");
            }
            if (list.size() > 200000) {
                throw new IllegalArgumentException("Too many rules provided: " + list.size());
            }
            Map splitRulesIntoIndexBuckets = RuleIndexingDetailsIdentifier.splitRulesIntoIndexBuckets(list);
            verifySize((Map) splitRulesIntoIndexBuckets.get(1), 100000);
            verifySize((Map) splitRulesIntoIndexBuckets.get(2), 100000);
            verifySize((Map) splitRulesIntoIndexBuckets.get(0), 1000);
            ByteTrackedOutputStream byteTrackedOutputStream = new ByteTrackedOutputStream(outputStream);
            serializeRuleFileMetadata(optional, byteTrackedOutputStream);
            LinkedHashMap serializeRuleList = serializeRuleList((Map) splitRulesIntoIndexBuckets.get(1), byteTrackedOutputStream);
            LinkedHashMap serializeRuleList2 = serializeRuleList((Map) splitRulesIntoIndexBuckets.get(2), byteTrackedOutputStream);
            LinkedHashMap serializeRuleList3 = serializeRuleList((Map) splitRulesIntoIndexBuckets.get(0), byteTrackedOutputStream);
            BitOutputStream bitOutputStream = new BitOutputStream(outputStream2);
            serializeIndexGroup(serializeRuleList, bitOutputStream, true);
            serializeIndexGroup(serializeRuleList2, bitOutputStream, true);
            serializeIndexGroup(serializeRuleList3, bitOutputStream, false);
            bitOutputStream.flush();
        } catch (Exception e) {
            throw new RuleSerializeException(e.getMessage(), e);
        }
    }

    public final void verifySize(Map map, int i) {
        int intValue = ((Integer) map.values().stream().map(new Function() { // from class: com.android.server.integrity.serializer.RuleBinarySerializer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer lambda$verifySize$0;
                lambda$verifySize$0 = RuleBinarySerializer.lambda$verifySize$0((List) obj);
                return lambda$verifySize$0;
            }
        }).collect(Collectors.summingInt(new AudioService$$ExternalSyntheticLambda0()))).intValue();
        if (intValue <= i) {
            return;
        }
        throw new IllegalArgumentException("Too many rules provided in the indexing group. Provided " + intValue + " limit " + i);
    }

    public static /* synthetic */ Integer lambda$verifySize$0(List list) {
        return Integer.valueOf(list.size());
    }

    public final void serializeRuleFileMetadata(Optional optional, ByteTrackedOutputStream byteTrackedOutputStream) {
        int intValue = ((Integer) optional.orElse(1)).intValue();
        BitOutputStream bitOutputStream = new BitOutputStream(byteTrackedOutputStream);
        bitOutputStream.setNext(8, intValue);
        bitOutputStream.flush();
    }

    public final LinkedHashMap serializeRuleList(Map map, ByteTrackedOutputStream byteTrackedOutputStream) {
        Preconditions.checkArgument(map != null, "serializeRuleList should never be called with null rule list.");
        BitOutputStream bitOutputStream = new BitOutputStream(byteTrackedOutputStream);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("START_KEY", Integer.valueOf(byteTrackedOutputStream.getWrittenBytesCount()));
        int i = 0;
        for (String str : (List) map.keySet().stream().sorted().collect(Collectors.toList())) {
            if (i >= 50) {
                linkedHashMap.put(str, Integer.valueOf(byteTrackedOutputStream.getWrittenBytesCount()));
                i = 0;
            }
            Iterator it = ((List) map.get(str)).iterator();
            while (it.hasNext()) {
                serializeRule((Rule) it.next(), bitOutputStream);
                bitOutputStream.flush();
                i++;
            }
        }
        linkedHashMap.put("END_KEY", Integer.valueOf(byteTrackedOutputStream.getWrittenBytesCount()));
        return linkedHashMap;
    }

    public final void serializeRule(Rule rule, BitOutputStream bitOutputStream) {
        if (rule == null) {
            throw new IllegalArgumentException("Null rule can not be serialized");
        }
        bitOutputStream.setNext();
        serializeFormula(rule.getFormula(), bitOutputStream);
        bitOutputStream.setNext(3, rule.getEffect());
        bitOutputStream.setNext();
    }

    public final void serializeFormula(IntegrityFormula integrityFormula, BitOutputStream bitOutputStream) {
        if (integrityFormula instanceof AtomicFormula) {
            serializeAtomicFormula((AtomicFormula) integrityFormula, bitOutputStream);
        } else if (integrityFormula instanceof CompoundFormula) {
            serializeCompoundFormula((CompoundFormula) integrityFormula, bitOutputStream);
        } else {
            if (integrityFormula instanceof InstallerAllowedByManifestFormula) {
                bitOutputStream.setNext(3, 3);
                return;
            }
            throw new IllegalArgumentException(String.format("Invalid formula type: %s", integrityFormula.getClass()));
        }
    }

    public final void serializeCompoundFormula(CompoundFormula compoundFormula, BitOutputStream bitOutputStream) {
        if (compoundFormula == null) {
            throw new IllegalArgumentException("Null compound formula can not be serialized");
        }
        bitOutputStream.setNext(3, 1);
        bitOutputStream.setNext(2, compoundFormula.getConnector());
        Iterator it = compoundFormula.getFormulas().iterator();
        while (it.hasNext()) {
            serializeFormula((IntegrityFormula) it.next(), bitOutputStream);
        }
        bitOutputStream.setNext(3, 2);
    }

    public final void serializeAtomicFormula(AtomicFormula atomicFormula, BitOutputStream bitOutputStream) {
        if (atomicFormula == null) {
            throw new IllegalArgumentException("Null atomic formula can not be serialized");
        }
        bitOutputStream.setNext(3, 0);
        bitOutputStream.setNext(4, atomicFormula.getKey());
        if (atomicFormula.getTag() == 1) {
            AtomicFormula.StringAtomicFormula stringAtomicFormula = (AtomicFormula.StringAtomicFormula) atomicFormula;
            bitOutputStream.setNext(3, 0);
            serializeStringValue(stringAtomicFormula.getValue(), stringAtomicFormula.getIsHashedValue().booleanValue(), bitOutputStream);
        } else {
            if (atomicFormula.getTag() == 2) {
                AtomicFormula.LongAtomicFormula longAtomicFormula = (AtomicFormula.LongAtomicFormula) atomicFormula;
                bitOutputStream.setNext(3, longAtomicFormula.getOperator().intValue());
                long longValue = longAtomicFormula.getValue().longValue();
                serializeIntValue((int) (longValue >>> 32), bitOutputStream);
                serializeIntValue((int) longValue, bitOutputStream);
                return;
            }
            if (atomicFormula.getTag() == 3) {
                bitOutputStream.setNext(3, 0);
                serializeBooleanValue(((AtomicFormula.BooleanAtomicFormula) atomicFormula).getValue().booleanValue(), bitOutputStream);
                return;
            }
            throw new IllegalArgumentException(String.format("Invalid atomic formula type: %s", atomicFormula.getClass()));
        }
    }

    public final void serializeIndexGroup(LinkedHashMap linkedHashMap, BitOutputStream bitOutputStream, boolean z) {
        serializeStringValue("START_KEY", false, bitOutputStream);
        serializeIntValue(((Integer) linkedHashMap.get("START_KEY")).intValue(), bitOutputStream);
        if (z) {
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                if (!((String) entry.getKey()).equals("START_KEY") && !((String) entry.getKey()).equals("END_KEY")) {
                    serializeStringValue((String) entry.getKey(), false, bitOutputStream);
                    serializeIntValue(((Integer) entry.getValue()).intValue(), bitOutputStream);
                }
            }
        }
        serializeStringValue("END_KEY", false, bitOutputStream);
        serializeIntValue(((Integer) linkedHashMap.get("END_KEY")).intValue(), bitOutputStream);
    }

    public final void serializeStringValue(String str, boolean z, BitOutputStream bitOutputStream) {
        if (str == null) {
            throw new IllegalArgumentException("String value can not be null.");
        }
        byte[] bytesForString = getBytesForString(str, z);
        bitOutputStream.setNext(z);
        bitOutputStream.setNext(8, bytesForString.length);
        for (byte b : bytesForString) {
            bitOutputStream.setNext(8, b);
        }
    }

    public final void serializeIntValue(int i, BitOutputStream bitOutputStream) {
        bitOutputStream.setNext(32, i);
    }

    public final void serializeBooleanValue(boolean z, BitOutputStream bitOutputStream) {
        bitOutputStream.setNext(z);
    }

    public static byte[] getBytesForString(String str, boolean z) {
        if (!z) {
            return str.getBytes(StandardCharsets.UTF_8);
        }
        return IntegrityUtils.getBytesFromHexDigest(str);
    }
}
