package com.android.server.integrity.serializer;

import android.content.integrity.AtomicFormula;
import android.content.integrity.CompoundFormula;
import android.content.integrity.InstallerAllowedByManifestFormula;
import android.content.integrity.IntegrityFormula;
import android.content.integrity.IntegrityUtils;
import android.content.integrity.Rule;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.integrity.model.BitOutputStream;
import com.android.server.integrity.model.ByteTrackedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RuleBinarySerializer implements RuleSerializer {
    public static void serializeFormula(IntegrityFormula integrityFormula, BitOutputStream bitOutputStream) {
        if (!(integrityFormula instanceof AtomicFormula)) {
            if (!(integrityFormula instanceof CompoundFormula)) {
                if (integrityFormula instanceof InstallerAllowedByManifestFormula) {
                    bitOutputStream.setNext(3, 3);
                    return;
                }
                throw new IllegalArgumentException("Invalid formula type: " + integrityFormula.getClass());
            }
            CompoundFormula compoundFormula = (CompoundFormula) integrityFormula;
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
            return;
        }
        AtomicFormula.StringAtomicFormula stringAtomicFormula = (AtomicFormula) integrityFormula;
        if (stringAtomicFormula == null) {
            throw new IllegalArgumentException("Null atomic formula can not be serialized");
        }
        bitOutputStream.setNext(3, 0);
        bitOutputStream.setNext(4, stringAtomicFormula.getKey());
        if (stringAtomicFormula.getTag() == 1) {
            AtomicFormula.StringAtomicFormula stringAtomicFormula2 = stringAtomicFormula;
            bitOutputStream.setNext(3, 0);
            serializeStringValue(stringAtomicFormula2.getValue(), stringAtomicFormula2.getIsHashedValue().booleanValue(), bitOutputStream);
        } else {
            if (stringAtomicFormula.getTag() == 2) {
                AtomicFormula.LongAtomicFormula longAtomicFormula = (AtomicFormula.LongAtomicFormula) stringAtomicFormula;
                bitOutputStream.setNext(3, longAtomicFormula.getOperator().intValue());
                long longValue = longAtomicFormula.getValue().longValue();
                bitOutputStream.setNext(32, (int) (longValue >>> 32));
                bitOutputStream.setNext(32, (int) longValue);
                return;
            }
            if (stringAtomicFormula.getTag() == 3) {
                bitOutputStream.setNext(3, 0);
                bitOutputStream.setNext(((AtomicFormula.BooleanAtomicFormula) stringAtomicFormula).getValue().booleanValue());
            } else {
                throw new IllegalArgumentException("Invalid atomic formula type: " + stringAtomicFormula.getClass());
            }
        }
    }

    public static void serializeIndexGroup(LinkedHashMap linkedHashMap, BitOutputStream bitOutputStream, boolean z) {
        serializeStringValue("START_KEY", false, bitOutputStream);
        bitOutputStream.setNext(32, ((Integer) linkedHashMap.get("START_KEY")).intValue());
        if (z) {
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                if (!((String) entry.getKey()).equals("START_KEY") && !((String) entry.getKey()).equals("END_KEY")) {
                    serializeStringValue((String) entry.getKey(), false, bitOutputStream);
                    bitOutputStream.setNext(32, ((Integer) entry.getValue()).intValue());
                }
            }
        }
        serializeStringValue("END_KEY", false, bitOutputStream);
        bitOutputStream.setNext(32, ((Integer) linkedHashMap.get("END_KEY")).intValue());
    }

    public static LinkedHashMap serializeRuleList(Map map, ByteTrackedOutputStream byteTrackedOutputStream) {
        Preconditions.checkArgument(map != null, "serializeRuleList should never be called with null rule list.");
        BitOutputStream bitOutputStream = new BitOutputStream(byteTrackedOutputStream);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("START_KEY", Integer.valueOf(byteTrackedOutputStream.mWrittenBytesCount));
        int i = 0;
        for (String str : (List) map.keySet().stream().sorted().collect(Collectors.toList())) {
            if (i >= 50) {
                linkedHashMap.put(str, Integer.valueOf(byteTrackedOutputStream.mWrittenBytesCount));
                i = 0;
            }
            for (Rule rule : (List) map.get(str)) {
                if (rule == null) {
                    throw new IllegalArgumentException("Null rule can not be serialized");
                }
                bitOutputStream.setNext(true);
                serializeFormula(rule.getFormula(), bitOutputStream);
                bitOutputStream.setNext(3, rule.getEffect());
                bitOutputStream.setNext(true);
                bitOutputStream.flush();
                i++;
            }
        }
        linkedHashMap.put("END_KEY", Integer.valueOf(byteTrackedOutputStream.mWrittenBytesCount));
        return linkedHashMap;
    }

    public static void serializeStringValue(String str, boolean z, BitOutputStream bitOutputStream) {
        if (str == null) {
            throw new IllegalArgumentException("String value can not be null.");
        }
        byte[] bytes = !z ? str.getBytes(StandardCharsets.UTF_8) : IntegrityUtils.getBytesFromHexDigest(str);
        bitOutputStream.setNext(z);
        bitOutputStream.setNext(8, bytes.length);
        for (byte b : bytes) {
            bitOutputStream.setNext(8, b);
        }
    }

    public static void verifySize(int i, Map map) {
        int intValue = ((Integer) map.values().stream().map(new RuleBinarySerializer$$ExternalSyntheticLambda0()).collect(Collectors.summingInt(new AudioService$$ExternalSyntheticLambda1(2)))).intValue();
        if (intValue > i) {
            throw new IllegalArgumentException(ArrayUtils$$ExternalSyntheticOutline0.m(intValue, i, "Too many rules provided in the indexing group. Provided ", " limit "));
        }
    }

    public final void serialize(List list, Optional optional, OutputStream outputStream, OutputStream outputStream2) {
        try {
            if (list == null) {
                throw new IllegalArgumentException("Null rules cannot be serialized.");
            }
            if (list.size() > 200000) {
                throw new IllegalArgumentException("Too many rules provided: " + list.size());
            }
            HashMap hashMap = (HashMap) RuleIndexingDetailsIdentifier.splitRulesIntoIndexBuckets(list);
            verifySize(100000, (Map) hashMap.get(1));
            verifySize(100000, (Map) hashMap.get(2));
            verifySize(1000, (Map) hashMap.get(0));
            ByteTrackedOutputStream byteTrackedOutputStream = new ByteTrackedOutputStream(outputStream);
            int intValue = ((Integer) optional.orElse(1)).intValue();
            BitOutputStream bitOutputStream = new BitOutputStream(byteTrackedOutputStream);
            bitOutputStream.setNext(8, intValue);
            bitOutputStream.flush();
            LinkedHashMap serializeRuleList = serializeRuleList((Map) hashMap.get(1), byteTrackedOutputStream);
            LinkedHashMap serializeRuleList2 = serializeRuleList((Map) hashMap.get(2), byteTrackedOutputStream);
            LinkedHashMap serializeRuleList3 = serializeRuleList((Map) hashMap.get(0), byteTrackedOutputStream);
            BitOutputStream bitOutputStream2 = new BitOutputStream(outputStream2);
            serializeIndexGroup(serializeRuleList, bitOutputStream2, true);
            serializeIndexGroup(serializeRuleList2, bitOutputStream2, true);
            serializeIndexGroup(serializeRuleList3, bitOutputStream2, false);
            bitOutputStream2.flush();
        } catch (Exception e) {
            throw new RuleSerializeException(e.getMessage(), e);
        }
    }
}
