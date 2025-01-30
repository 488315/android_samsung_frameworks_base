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

/* loaded from: classes4.dex */
public class ContentFilter extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) ContentFilter.class);
    private final Map<Integer, Object> filterMap;
    private final PlaceHolder<String> message;

    public ContentFilter(ContentFilterRegister contentFilterRegister, MediaFilter successor) {
        super(successor);
        this.filterMap = new HashMap();
        this.message = new PlaceHolder<String>() { // from class: com.samsung.android.sume.core.filter.ContentFilter.1
            private String buf;

            @Override // com.samsung.android.sume.core.functional.PlaceHolder
            public void put(String instance) {
                this.buf = instance;
            }

            @Override // com.samsung.android.sume.core.functional.PlaceHolder
            public String reset() {
                String ret = this.buf;
                this.buf = null;
                return ret;
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
            public void addFilter(int filterType, Object data) {
                ContentFilter.this.filterMap.put(Integer.valueOf(filterType), data);
            }

            @Override // com.samsung.android.sume.core.filter.ContentFilterRegistry
            public <R> R getFilter(int i) {
                return (R) ContentFilter.this.filterMap.get(Integer.valueOf(i));
            }
        });
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        MediaFormat mediaFormat = ibuf.getFormat();
        filterOut(mediaFormat);
        return super.run(ibuf, obuf);
    }

    private void filterOut(final MediaFormat mediaFormat) {
        boolean isFiltered = this.filterMap.entrySet().stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ContentFilter.this.m368x2ca42333(mediaFormat, (Map.Entry) obj);
            }
        });
        if (isFiltered) {
            throw new ContentFilterOutException(this.message.reset());
        }
    }

    /* renamed from: lambda$filterOut$0$com-samsung-android-sume-core-filter-ContentFilter */
    /* synthetic */ boolean m368x2ca42333(MediaFormat mediaFormat, Map.Entry it) {
        switch (((Integer) it.getKey()).intValue()) {
            case 1:
                return evaluateDimension(it.getValue(), mediaFormat.getShape(), this.message);
            case 2:
                return evaluateDataType(it.getValue(), mediaFormat.getDataType(), this.message);
            case 3:
                return evaluateMediaType(it.getValue(), (String) mediaFormat.get("mime-type"), this.message);
            default:
                throw new IllegalArgumentException("");
        }
    }

    private boolean evaluateDimension(Object filterValue, Shape shape, PlaceHolder<String> message) {
        Def.require(filterValue instanceof Evaluator);
        boolean isFiltered = ((Evaluator) filterValue).evaluate(Integer.valueOf(shape.getDimension()));
        if (isFiltered) {
            message.put(getTag() + shape + " is not supported by filter: " + filterValue);
        }
        return isFiltered;
    }

    private boolean evaluateDataType(Object filterValue, final DataType dataType, PlaceHolder<String> message) {
        boolean isFiltered;
        if (filterValue instanceof DataType) {
            isFiltered = filterValue == dataType;
            if (isFiltered) {
                message.put(getTag() + dataType + " is not supported by filter: " + filterValue);
            }
        } else {
            boolean isFiltered2 = filterValue instanceof DataType[];
            if (isFiltered2) {
                isFiltered = Arrays.stream((DataType[]) filterValue).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ContentFilter.lambda$evaluateDataType$1(DataType.this, (DataType) obj);
                    }
                });
                if (isFiltered) {
                    String filter = (String) Arrays.stream((DataType[]) filterValue).map(new Function() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return ((DataType) obj).toString();
                        }
                    }).collect(Collectors.joining());
                    message.put(getTag() + dataType + " is not supported by filter: " + filter);
                }
            } else {
                boolean isFiltered3 = filterValue instanceof List;
                if (isFiltered3) {
                    isFiltered = ((List) filterValue).stream().anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return ContentFilter.lambda$evaluateDataType$2(DataType.this, (DataType) obj);
                        }
                    });
                    if (isFiltered) {
                        String filter2 = (String) ((List) filterValue).stream().map(new Function() { // from class: com.samsung.android.sume.core.filter.ContentFilter$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((DataType) obj).toString();
                            }
                        }).collect(Collectors.joining());
                        message.put(getTag() + dataType + " is not supported by filter: " + filter2);
                    }
                } else {
                    throw new IllegalArgumentException("invalid filter value: " + filterValue);
                }
            }
        }
        return isFiltered;
    }

    static /* synthetic */ boolean lambda$evaluateDataType$1(DataType dataType, DataType it) {
        return it == dataType;
    }

    static /* synthetic */ boolean lambda$evaluateDataType$2(DataType dataType, DataType it) {
        return it == dataType;
    }

    private boolean evaluateMediaType(Object filterValue, String mediaTypeInfo, PlaceHolder<String> message) {
        return false;
    }

    private String getTag() {
        return NavigationBarInflaterView.SIZE_MOD_START + getSuccessorFilter().getId() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
