package com.samsung.android.sume.core.filter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNDescriptor;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.message.MessageConsumer;
import com.samsung.android.sume.core.message.MessageProducer;
import com.samsung.android.sume.core.types.OptionBase;
import com.samsung.android.sume.core.types.PadType;
import com.samsung.android.sume.core.types.SplitType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public interface MediaFilter extends MessageConsumer, Operator {
    public static final int OPTION_ALLOW_PARTIAL_CONNECTION = 0;
    public static final int OPTION_AS_INPUT = 8;
    public static final int OPTION_AS_OUTPUT = 9;
    public static final int OPTION_BATCH_IO = 5;
    public static final int OPTION_EXTERNAL_BUFFER_COMPOSER = 3;
    public static final int OPTION_IGNORABLE_FILTER = 10;
    public static final int OPTION_INPUT_WITH_EVALUATION_VALUE = 7;
    public static final int OPTION_KEEP_FILTER_DATA_TYPE = 4;
    public static final int OPTION_PAD = 1;
    public static final int OPTION_SPLIT_TYPE = 2;
    public static final int OPTION_WAIT_RECEIVE_ALL = 6;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OptionType {
    }

    public enum Type {
        NN,
        IMGP,
        CODEC,
        PLUGIN
    }

    MFDescriptor getDescriptor();

    default void pause() {
    }

    default void prepare() {
    }

    default void release() {
    }

    default void resume() {
    }

    default void setMessageProducer(MessageProducer messageProducer) {
    }

    Stream<MediaFilter> stream();

    default String getId() {
        MFDescriptor descriptor = getDescriptor();
        return descriptor instanceof NNDescriptor ? ((NNDescriptor) descriptor).getModelId() : descriptor.getFilterId();
    }

    default void accept(MediaFilterRetriever mediaFilterRetriever, MediaFilter mediaFilter) {
        mediaFilterRetriever.retrieve(this, mediaFilter);
    }

    public static class Option extends OptionBase {
        private static final String TAG = Def.tagOf((Class<?>) Option.class);
        public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() { // from class: com.samsung.android.sume.core.filter.MediaFilter.Option.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Option createFromParcel(Parcel parcel) {
                return new Option(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Option[] newArray(int i) {
                return new Option[i];
            }
        };

        public Option() {
        }

        protected Option(Parcel parcel) {
            super(parcel);
        }

        public SplitType getSplitType() {
            return (SplitType) get(2);
        }

        public void setSplitType(SplitType splitType) {
            set(2, (Object) splitType);
        }

        public Pair<PadType, Integer> getPad() {
            return (Pair) get(1);
        }

        public void setPad(Pair<PadType, Integer> pair) {
            set(1, (Object) pair);
        }

        public boolean isAllowPartialConnection() {
            return ((Boolean) get(0, false)).booleanValue();
        }

        public void setAllowPartialConnection(boolean z) {
            set(0, (Object) Boolean.valueOf(z));
        }

        public boolean getUseExternalBufferComposer() {
            return ((Boolean) get(3, false)).booleanValue();
        }

        public void setUseExternalBufferComposer(boolean z) {
            set(3, (Object) Boolean.valueOf(z));
        }

        public boolean isKeepFilterDatatype() {
            return ((Boolean) get(4, false)).booleanValue();
        }

        public void setKeepFilterDatatype(boolean z) {
            set(4, (Object) Boolean.valueOf(z));
        }

        public boolean isBatchIO() {
            return ((Boolean) get(5, false)).booleanValue();
        }

        public void setBatchIO(boolean z) {
            set(5, (Object) Boolean.valueOf(z));
        }

        public boolean isWaitToReceiveAll() {
            return ((Boolean) get(6, false)).booleanValue();
        }

        public void setWaitToReceiveAll(boolean z) {
            set(6, (Object) Boolean.valueOf(z));
        }

        public boolean isInputWithEvaluationValue() {
            return ((Boolean) get(7, false)).booleanValue();
        }

        public void setInputWithEvaluationValue(boolean z) {
            set(7, (Object) Boolean.valueOf(z));
        }

        public Option asInputOption() {
            remove(9);
            return set(8);
        }

        public boolean isInputOption() {
            return ((Boolean) get(8, false)).booleanValue();
        }

        public Option asOutputOption() {
            remove(8);
            return set(9);
        }

        public boolean isOutputOption() {
            return ((Boolean) get(9, false)).booleanValue();
        }

        public boolean isIgnorableFilter() {
            return ((Boolean) get(10, false)).booleanValue();
        }

        public void setFilterIgnorable(boolean z) {
            set(10, (Object) Boolean.valueOf(z));
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public Option set(int i) {
            super.set(i);
            return this;
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public Option set(int i, Object obj) {
            super.set(i, obj);
            return this;
        }
    }
}
