package com.android.server.integrity.serializer;

import android.content.integrity.AtomicFormula;
import android.content.integrity.CompoundFormula;
import android.content.integrity.IntegrityFormula;
import android.content.integrity.Rule;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class RuleIndexingDetailsIdentifier {
    public static RuleIndexingDetails getIndexingDetails(IntegrityFormula integrityFormula) {
        RuleIndexingDetails ruleIndexingDetails;
        int tag = integrityFormula.getTag();
        if (tag != 0) {
            if (tag != 1) {
                if (tag == 2 || tag == 3 || tag == 4) {
                    return new RuleIndexingDetails();
                }
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                integrityFormula.getTag(), "Invalid formula tag type: "));
            }
            AtomicFormula.StringAtomicFormula stringAtomicFormula =
                    (AtomicFormula.StringAtomicFormula) integrityFormula;
            int key = stringAtomicFormula.getKey();
            if (key == 0) {
                ruleIndexingDetails = new RuleIndexingDetails(1, stringAtomicFormula.getValue());
            } else {
                if (key != 1) {
                    return new RuleIndexingDetails();
                }
                ruleIndexingDetails = new RuleIndexingDetails(2, stringAtomicFormula.getValue());
            }
            return ruleIndexingDetails;
        }
        CompoundFormula compoundFormula = (CompoundFormula) integrityFormula;
        int connector = compoundFormula.getConnector();
        List formulas = compoundFormula.getFormulas();
        if (connector != 0 && connector != 1) {
            return new RuleIndexingDetails();
        }
        final int i = 0;
        Stream map =
                formulas.stream()
                        .map(
                                new Function() { // from class:
                                                 // com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        IntegrityFormula integrityFormula2 = (IntegrityFormula) obj;
                                        switch (i) {
                                        }
                                        return RuleIndexingDetailsIdentifier.getIndexingDetails(
                                                integrityFormula2);
                                    }
                                });
        final int i2 = 0;
        Optional findAny =
                map.filter(
                                new Predicate() { // from class:
                                                  // com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        RuleIndexingDetails ruleIndexingDetails2 =
                                                (RuleIndexingDetails) obj;
                                        switch (i2) {
                                            case 0:
                                                return ruleIndexingDetails2.mIndexType == 1;
                                            default:
                                                return ruleIndexingDetails2.mIndexType == 2;
                                        }
                                    }
                                })
                        .findAny();
        if (findAny.isPresent()) {
            return (RuleIndexingDetails) findAny.get();
        }
        final int i3 = 1;
        Stream map2 =
                formulas.stream()
                        .map(
                                new Function() { // from class:
                                                 // com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        IntegrityFormula integrityFormula2 = (IntegrityFormula) obj;
                                        switch (i3) {
                                        }
                                        return RuleIndexingDetailsIdentifier.getIndexingDetails(
                                                integrityFormula2);
                                    }
                                });
        final int i4 = 1;
        Optional findAny2 =
                map2.filter(
                                new Predicate() { // from class:
                                                  // com.android.server.integrity.serializer.RuleIndexingDetailsIdentifier$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        RuleIndexingDetails ruleIndexingDetails2 =
                                                (RuleIndexingDetails) obj;
                                        switch (i4) {
                                            case 0:
                                                return ruleIndexingDetails2.mIndexType == 1;
                                            default:
                                                return ruleIndexingDetails2.mIndexType == 2;
                                        }
                                    }
                                })
                        .findAny();
        return findAny2.isPresent()
                ? (RuleIndexingDetails) findAny2.get()
                : new RuleIndexingDetails();
    }

    public static Map splitRulesIntoIndexBuckets(List list) {
        if (list == null) {
            throw new IllegalArgumentException(
                    "Index buckets cannot be created for null rule list.");
        }
        HashMap hashMap = new HashMap();
        hashMap.put(0, new HashMap());
        hashMap.put(1, new HashMap());
        hashMap.put(2, new HashMap());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Rule rule = (Rule) it.next();
            try {
                RuleIndexingDetails indexingDetails = getIndexingDetails(rule.getFormula());
                int i = indexingDetails.mIndexType;
                Map map = (Map) hashMap.get(Integer.valueOf(i));
                String str = indexingDetails.mRuleKey;
                if (!map.containsKey(str)) {
                    ((Map) hashMap.get(Integer.valueOf(i))).put(str, new ArrayList());
                }
                ((List) ((Map) hashMap.get(Integer.valueOf(i))).get(str)).add(rule);
            } catch (Exception unused) {
                throw new IllegalArgumentException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Malformed rule identified. [", rule.toString(), "]"));
            }
        }
        return hashMap;
    }
}
