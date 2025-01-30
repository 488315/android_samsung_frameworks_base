package com.samsung.android.sume.core.evaluate;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
abstract class EvaluatorGroup implements Evaluator {
    private List<Evaluator> evaluators;
    private volatile boolean sorted = false;

    EvaluatorGroup(Evaluator... evaluators) {
        this.evaluators = Arrays.asList(evaluators);
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
        if (!this.sorted) {
            this.evaluators = (List) stream().sorted().collect(Collectors.toList());
            this.sorted = true;
        }
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
        Evaluator evaluator = this.evaluators.get(r0.size() - 1);
        if (evaluator instanceof GenericEvaluator) {
            return (T) ((GenericEvaluator) evaluator).getValue();
        }
        return (T) ((EvaluatorGroup) evaluator).back();
    }

    @Override // java.lang.Comparable
    public int compareTo(Evaluator other) {
        if (other instanceof GenericEvaluator) {
            return front().compareTo(((GenericEvaluator) other).getValue());
        }
        ((EvaluatorGroup) other).sort();
        return front().compareTo(((EvaluatorGroup) other).front());
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Stream<Evaluator> stream() {
        return this.evaluators.stream();
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    public Evaluator and(Evaluator other) {
        if (this instanceof AndEvaluatorGroup) {
            return add(other);
        }
        if (other instanceof AndEvaluatorGroup) {
            return ((AndEvaluatorGroup) other).add(this);
        }
        return new AndEvaluatorGroup(this, other);
    }

    @Override // com.samsung.android.sume.core.evaluate.Evaluator
    /* renamed from: or */
    public Evaluator mo360or(Evaluator other) {
        if (this instanceof OrEvaluatorGroup) {
            return add(other);
        }
        if (other instanceof OrEvaluatorGroup) {
            return ((OrEvaluatorGroup) other).add(this);
        }
        return new OrEvaluatorGroup(this, other);
    }

    public String toString() {
        String delimiter = this instanceof OrEvaluatorGroup ? " or " : " and ";
        return (String) this.evaluators.stream().map(new Function() { // from class: com.samsung.android.sume.core.evaluate.EvaluatorGroup$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EvaluatorGroup.lambda$toString$0((Evaluator) obj);
            }
        }).collect(Collectors.joining(delimiter));
    }

    static /* synthetic */ String lambda$toString$0(Evaluator it) {
        return NavigationBarInflaterView.SIZE_MOD_START + it + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
