package com.android.server.integrity.parser;

import android.content.integrity.AtomicFormula;
import android.content.integrity.CompoundFormula;
import android.content.integrity.InstallerAllowedByManifestFormula;
import android.content.integrity.IntegrityFormula;
import android.content.integrity.Rule;
import com.android.server.integrity.model.BitInputStream;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class RuleBinaryParser implements RuleParser {
    @Override // com.android.server.integrity.parser.RuleParser
    public List parse(RandomAccessObject randomAccessObject, List list) {
        try {
            RandomAccessInputStream randomAccessInputStream = new RandomAccessInputStream(randomAccessObject);
            try {
                List parseRules = parseRules(randomAccessInputStream, list);
                randomAccessInputStream.close();
                return parseRules;
            } finally {
            }
        } catch (Exception e) {
            throw new RuleParseException(e.getMessage(), e);
        }
    }

    public final List parseRules(RandomAccessInputStream randomAccessInputStream, List list) {
        randomAccessInputStream.skip(1L);
        if (list.isEmpty()) {
            return parseAllRules(randomAccessInputStream);
        }
        return parseIndexedRules(randomAccessInputStream, list);
    }

    public final List parseAllRules(RandomAccessInputStream randomAccessInputStream) {
        ArrayList arrayList = new ArrayList();
        BitInputStream bitInputStream = new BitInputStream(new BufferedInputStream(randomAccessInputStream));
        while (bitInputStream.hasNext()) {
            if (bitInputStream.getNext(1) == 1) {
                arrayList.add(parseRule(bitInputStream));
            }
        }
        return arrayList;
    }

    public final List parseIndexedRules(RandomAccessInputStream randomAccessInputStream, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RuleIndexRange ruleIndexRange = (RuleIndexRange) it.next();
            randomAccessInputStream.seek(ruleIndexRange.getStartIndex());
            BitInputStream bitInputStream = new BitInputStream(new BufferedInputStream(new LimitInputStream(randomAccessInputStream, ruleIndexRange.getEndIndex() - ruleIndexRange.getStartIndex())));
            while (bitInputStream.hasNext()) {
                if (bitInputStream.getNext(1) == 1) {
                    arrayList.add(parseRule(bitInputStream));
                }
            }
        }
        return arrayList;
    }

    public final Rule parseRule(BitInputStream bitInputStream) {
        IntegrityFormula parseFormula = parseFormula(bitInputStream);
        int next = bitInputStream.getNext(3);
        if (bitInputStream.getNext(1) != 1) {
            throw new IllegalArgumentException("A rule must end with a '1' bit.");
        }
        return new Rule(parseFormula, next);
    }

    public final IntegrityFormula parseFormula(BitInputStream bitInputStream) {
        int next = bitInputStream.getNext(3);
        if (next == 0) {
            return parseAtomicFormula(bitInputStream);
        }
        if (next == 1) {
            return parseCompoundFormula(bitInputStream);
        }
        if (next == 2) {
            return null;
        }
        if (next == 3) {
            return new InstallerAllowedByManifestFormula();
        }
        throw new IllegalArgumentException(String.format("Unknown formula separator: %s", Integer.valueOf(next)));
    }

    public final CompoundFormula parseCompoundFormula(BitInputStream bitInputStream) {
        int next = bitInputStream.getNext(2);
        ArrayList arrayList = new ArrayList();
        IntegrityFormula parseFormula = parseFormula(bitInputStream);
        while (parseFormula != null) {
            arrayList.add(parseFormula);
            parseFormula = parseFormula(bitInputStream);
        }
        return new CompoundFormula(next, arrayList);
    }

    public final AtomicFormula parseAtomicFormula(BitInputStream bitInputStream) {
        int next = bitInputStream.getNext(4);
        int next2 = bitInputStream.getNext(3);
        switch (next) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 7:
            case 8:
                boolean z = bitInputStream.getNext(1) == 1;
                return new AtomicFormula.StringAtomicFormula(next, BinaryFileOperations.getStringValue(bitInputStream, bitInputStream.getNext(8), z), z);
            case 4:
                return new AtomicFormula.LongAtomicFormula(next, next2, (BinaryFileOperations.getIntValue(bitInputStream) << 32) | BinaryFileOperations.getIntValue(bitInputStream));
            case 5:
            case 6:
                return new AtomicFormula.BooleanAtomicFormula(next, BinaryFileOperations.getBooleanValue(bitInputStream));
            default:
                throw new IllegalArgumentException(String.format("Unknown key: %d", Integer.valueOf(next)));
        }
    }
}
