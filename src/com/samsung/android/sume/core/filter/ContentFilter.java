package com.samsung.android.sume.core.filter;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.exception.ContentFilterOutException;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.functional.PlaceHolder;
import com.samsung.android.sume.core.types.DataType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class ContentFilter extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) ContentFilter.class);
    private final Map<Integer, Object> filterMap;
    private final PlaceHolder<String> message;

    private boolean evaluateMediaType(Object obj, String str, PlaceHolder<String> placeHolder) {
        return false;
    }

    static /* synthetic */ boolean lambda$evaluateDataType$1(DataType dataType, DataType dataType2) {
        return dataType2 == dataType;
    }

    static /* synthetic */ boolean lambda$evaluateDataType$2(DataType dataType, DataType dataType2) {
        return dataType2 == dataType;
    }

    public ContentFilter(ContentFilterRegister contentFilterRegister, MediaFilter mediaFilter) {
        super(mediaFilter);
        this.filterMap = new HashMap();
        this.message = new PlaceHolder<String>() { // from class: com.samsung.android.sume.core.filter.ContentFilter.1
            private String buf;

            @Override // com.samsung.android.sume.core.functional.PlaceHolder
            public void put(String str) {
                this.buf = str;
            }

            @Override // com.samsung.android.sume.core.functional.PlaceHolder
            public String reset() {
                String str = this.buf;
                this.buf = null;
                return str;
            }

            @Override // com.samsung.android.sume.core.functional.PlaceHolder
            public boolean isEmpty() {
                return this.buf == null;
            }

            @Override // com.samsung.android.sume.core.functional.PlaceHolder
            public boolean isNotEmpty() {
                return this.buf != null;
            }
        };
        contentFilterRegister.registerFilter(new ContentFilterRegistry() { // from class: com.samsung.android.sume.core.filter.ContentFilter.2
            @Override // com.samsung.android.sume.core.filter.ContentFilterRegistry
            public void addFilter(int i, Object obj) {
                ContentFilter.this.filterMap.put(Integer.valueOf(i), obj);
            }

            @Override // com.samsung.android.sume.core.filter.ContentFilterRegistry
            public <R> R getFilter(int i) {
                return (R) ContentFilter.this.filterMap.get(Integer.valueOf(i));
            }
        });
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        filterOut(mediaBuffer.getFormat());
        return super.run(mediaBuffer, mutableMediaBuffer);
    }

    private void filterOut(final MediaFormat mediaFormat) {
        if (this.filterMap.entrySet().stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ContentFilter.this.m9537x2ca42333(mediaFormat, (Map.Entry) obj);
            }
        })) {
            throw new ContentFilterOutException(this.message.reset());
        }
    }

    /* renamed from: lambda$filterOut$0$com-samsung-android-sume-core-filter-ContentFilter, reason: not valid java name */
    /* synthetic */ boolean m9537x2ca42333(MediaFormat mediaFormat, Map.Entry entry) {
        int intValue = ((Integer) entry.getKey()).intValue();
        if (intValue == 1) {
            return evaluateDimension(entry.getValue(), mediaFormat.getShape(), this.message);
        }
        if (intValue == 2) {
            return evaluateDataType(entry.getValue(), mediaFormat.getDataType(), this.message);
        }
        if (intValue == 3) {
            return evaluateMediaType(entry.getValue(), (String) mediaFormat.get("mime-type"), this.message);
        }
        throw new IllegalArgumentException("");
    }

    private boolean evaluateDimension(Object obj, Shape shape, PlaceHolder<String> placeHolder) {
        Def.require(obj instanceof Evaluator);
        boolean evaluate = ((Evaluator) obj).evaluate(Integer.valueOf(shape.getDimension()));
        if (evaluate) {
            placeHolder.put(getTag() + shape + " is not supported by filter: " + obj);
        }
        return evaluate;
    }

    private boolean evaluateDataType(Object obj, final DataType dataType, PlaceHolder<String> placeHolder) {
        if (obj instanceof DataType) {
            boolean z = obj == dataType;
            if (z) {
                placeHolder.put(getTag() + dataType + " is not supported by filter: " + obj);
            }
            return z;
        }
        if (obj instanceof DataType[]) {
            DataType[] dataTypeArr = (DataType[]) obj;
            boolean anyMatch = Arrays.stream(dataTypeArr).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj2) {
                    return ContentFilter.lambda$evaluateDataType$1(DataType.this, (DataType) obj2);
                }
            });
            if (anyMatch) {
                placeHolder.put(getTag() + dataType + " is not supported by filter: " + ((String) Arrays.stream(dataTypeArr).map(new Function() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return ((DataType) obj2).toString();
                    }
                }).collect(Collectors.joining())));
            }
            return anyMatch;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            boolean anyMatch2 = list.stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj2) {
                    return ContentFilter.lambda$evaluateDataType$2(DataType.this, (DataType) obj2);
                }
            });
            if (anyMatch2) {
                placeHolder.put(getTag() + dataType + " is not supported by filter: " + ((String) list.stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return ((DataType) obj2).toString();
                    }
                }).collect(Collectors.joining())));
            }
            return anyMatch2;
        }
        throw new IllegalArgumentException("invalid filter value: " + obj);
    }

    private String getTag() {
        return NavigationBarInflaterView.SIZE_MOD_START + getSuccessorFilter().getId() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
