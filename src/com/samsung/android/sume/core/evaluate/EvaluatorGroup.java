package com.samsung.android.sume.core.evaluate;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
abstract class EvaluatorGroup implements Evaluator {
    private List<Evaluator> evaluators;
    private volatile boolean sorted = false;

    EvaluatorGroup(Evaluator... evaluatorArr) {
        this.evaluators = Arrays.asList(evaluatorArr);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Class<?> getValueType() {
        return (Class) this.evaluators.stream().findFirst().map(new Function() { // from class: com.samsung.android.sume.core.evaluate.EvaluatorGroup$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Evaluator) obj).getValueType();
            }
        }).orElse(null);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public <V> V getValue() {
        throw new UnsupportedOperationException("EvaluatorGroup doesn't support this!!!");
    }

    EvaluatorGroup add(Evaluator evaluator) {
        this.evaluators.add(evaluator);
        this.sorted = false;
        return this;
    }

    EvaluatorGroup remove(Evaluator evaluator) {
        this.evaluators.remove(evaluator);
        return this;
    }

    List<Evaluator> getEvaluators() {
        return this.evaluators;
    }

    void sort() {
        if (this.sorted) {
            return;
        }
        this.evaluators = (List) stream().sorted().collect(Collectors.toList());
        this.sorted = true;
    }

    <T extends Comparable> T front() {
        sort();
        Evaluator evaluator = this.evaluators.get(0);
        if (evaluator instanceof GenericEvaluator) {
            return (T) ((GenericEvaluator) evaluator).getValue();
        }
        return (T) ((EvaluatorGroup) evaluator).front();
    }

    <T extends Comparable> T back() {
        sort();
        Evaluator evaluator = this.evaluators.get(r1.size() - 1);
        if (evaluator instanceof GenericEvaluator) {
            return (T) ((GenericEvaluator) evaluator).getValue();
        }
        return (T) ((EvaluatorGroup) evaluator).back();
    }

    @Override // java.lang.Comparable
    public int compareTo(Evaluator evaluator) {
        if (evaluator instanceof GenericEvaluator) {
            return front().compareTo(((GenericEvaluator) evaluator).getValue());
        }
        EvaluatorGroup evaluatorGroup = (EvaluatorGroup) evaluator;
        evaluatorGroup.sort();
        return front().compareTo(evaluatorGroup.front());
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Stream<Evaluator> stream() {
        return this.evaluators.stream();
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator and(Evaluator evaluator) {
        if (this instanceof AndEvaluatorGroup) {
            return add(evaluator);
        }
        if (evaluator instanceof AndEvaluatorGroup) {
            return ((AndEvaluatorGroup) evaluator).add(this);
        }
        return new AndEvaluatorGroup(this, evaluator);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator or(Evaluator evaluator) {
        if (this instanceof OrEvaluatorGroup) {
            return add(evaluator);
        }
        if (evaluator instanceof OrEvaluatorGroup) {
            return ((OrEvaluatorGroup) evaluator).add(this);
        }
        return new OrEvaluatorGroup(this, evaluator);
    }

    public String toString() {
        return (String) this.evaluators.stream().map(new Function() { // from class: com.samsung.android.sume.core.evaluate.EvaluatorGroup$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EvaluatorGroup.lambda$toString$0((Evaluator) obj);
            }
        }).collect(Collectors.joining(this instanceof OrEvaluatorGroup ? " or " : " and "));
    }

    static /* synthetic */ String lambda$toString$0(Evaluator evaluator) {
        return NavigationBarInflaterView.SIZE_MOD_START + evaluator + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
