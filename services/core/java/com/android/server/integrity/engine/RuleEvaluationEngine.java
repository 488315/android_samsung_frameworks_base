package com.android.server.integrity.engine;

import android.content.integrity.AppInstallMetadata;
import android.content.integrity.Rule;
import android.util.Slog;

import com.android.server.integrity.IntegrityFileManager;
import com.android.server.integrity.model.IntegrityCheckResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class RuleEvaluationEngine {
    public final IntegrityFileManager mIntegrityFileManager;

    public RuleEvaluationEngine(IntegrityFileManager integrityFileManager) {
        this.mIntegrityFileManager = integrityFileManager;
    }

    public final IntegrityCheckResult evaluate(final AppInstallMetadata appInstallMetadata) {
        List emptyList;
        IntegrityFileManager integrityFileManager = this.mIntegrityFileManager;
        integrityFileManager.getClass();
        if (new File(integrityFileManager.mRulesDir, "rules").exists()
                && new File(integrityFileManager.mRulesDir, "metadata").exists()
                && new File(integrityFileManager.mRulesDir, "indexing").exists()) {
            try {
                emptyList = integrityFileManager.readRules(appInstallMetadata);
            } catch (Exception e) {
                Slog.e("RuleEvaluation", "Error loading rules.", e);
                emptyList = new ArrayList();
            }
        } else {
            Slog.w("RuleEvaluation", "Integrity rule files are not available.");
            emptyList = Collections.emptyList();
        }
        List list =
                (List)
                        emptyList.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.server.integrity.engine.RuleEvaluator$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((Rule) obj)
                                                        .getFormula()
                                                        .matches(appInstallMetadata);
                                            }
                                        })
                                .collect(Collectors.toList());
        final int i = 0;
        List list2 =
                (List)
                        list.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.server.integrity.engine.RuleEvaluator$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                Rule rule = (Rule) obj;
                                                switch (i) {
                                                    case 0:
                                                        return rule.getEffect() == 1;
                                                    default:
                                                        return rule.getEffect() == 0;
                                                }
                                            }
                                        })
                                .collect(Collectors.toList());
        boolean isEmpty = list2.isEmpty();
        IntegrityCheckResult.Effect effect = IntegrityCheckResult.Effect.ALLOW;
        if (!isEmpty) {
            return new IntegrityCheckResult(effect, list2);
        }
        final int i2 = 1;
        List list3 =
                (List)
                        list.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.server.integrity.engine.RuleEvaluator$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                Rule rule = (Rule) obj;
                                                switch (i2) {
                                                    case 0:
                                                        return rule.getEffect() == 1;
                                                    default:
                                                        return rule.getEffect() == 0;
                                                }
                                            }
                                        })
                                .collect(Collectors.toList());
        return !list3.isEmpty()
                ? new IntegrityCheckResult(IntegrityCheckResult.Effect.DENY, list3)
                : new IntegrityCheckResult(effect, Collections.emptyList());
    }
}
