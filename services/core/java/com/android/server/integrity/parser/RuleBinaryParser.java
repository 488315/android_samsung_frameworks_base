package com.android.server.integrity.parser;

import android.content.integrity.AtomicFormula;
import android.content.integrity.CompoundFormula;
import android.content.integrity.InstallerAllowedByManifestFormula;
import android.content.integrity.IntegrityFormula;
import android.content.integrity.Rule;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import com.android.server.integrity.model.BitInputStream;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RuleBinaryParser implements RuleParser {
    public static IntegrityFormula parseFormula(BitInputStream bitInputStream) {
        int next = bitInputStream.getNext(3);
        if (next == 0) {
            int next2 = bitInputStream.getNext(4);
            int next3 = bitInputStream.getNext(3);
            switch (next2) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 7:
                case 8:
                    boolean z = bitInputStream.getNext(1) == 1;
                    return new AtomicFormula.StringAtomicFormula(next2, BinaryFileOperations.getStringValue(bitInputStream, bitInputStream.getNext(8), z), z);
                case 4:
                    return new AtomicFormula.LongAtomicFormula(next2, next3, (bitInputStream.getNext(32) << 32) | bitInputStream.getNext(32));
                case 5:
                case 6:
                    return new AtomicFormula.BooleanAtomicFormula(next2, bitInputStream.getNext(1) == 1);
                default:
                    throw new IllegalArgumentException(String.format("Unknown key: %d", Integer.valueOf(next2)));
            }
        }
        if (next != 1) {
            if (next == 2) {
                return null;
            }
            if (next == 3) {
                return new InstallerAllowedByManifestFormula();
            }
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(next, "Unknown formula separator: "));
        }
        int next4 = bitInputStream.getNext(2);
        ArrayList arrayList = new ArrayList();
        IntegrityFormula parseFormula = parseFormula(bitInputStream);
        while (parseFormula != null) {
            arrayList.add(parseFormula);
            parseFormula = parseFormula(bitInputStream);
        }
        return new CompoundFormula(next4, arrayList);
    }

    public static List parseRules(RandomAccessInputStream randomAccessInputStream, List list) {
        randomAccessInputStream.skip(1L);
        if (list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            BitInputStream bitInputStream = new BitInputStream(new BufferedInputStream(randomAccessInputStream));
            while (bitInputStream.mInputStream.available() > 0) {
                if (bitInputStream.getNext(1) == 1) {
                    IntegrityFormula parseFormula = parseFormula(bitInputStream);
                    int next = bitInputStream.getNext(3);
                    if (bitInputStream.getNext(1) != 1) {
                        throw new IllegalArgumentException("A rule must end with a '1' bit.");
                    }
                    arrayList.add(new Rule(parseFormula, next));
                }
            }
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RuleIndexRange ruleIndexRange = (RuleIndexRange) it.next();
            int i = ruleIndexRange.mStartIndex;
            randomAccessInputStream.mRandomAccessObject.mRandomAccessFile.seek(i);
            randomAccessInputStream.mPosition = i;
            BitInputStream bitInputStream2 = new BitInputStream(new BufferedInputStream(new LimitInputStream(randomAccessInputStream, ruleIndexRange.mEndIndex - ruleIndexRange.mStartIndex)));
            while (bitInputStream2.mInputStream.available() > 0) {
                if (bitInputStream2.getNext(1) == 1) {
                    IntegrityFormula parseFormula2 = parseFormula(bitInputStream2);
                    int next2 = bitInputStream2.getNext(3);
                    if (bitInputStream2.getNext(1) != 1) {
                        throw new IllegalArgumentException("A rule must end with a '1' bit.");
                    }
                    arrayList2.add(new Rule(parseFormula2, next2));
                }
            }
        }
        return arrayList2;
    }
}
