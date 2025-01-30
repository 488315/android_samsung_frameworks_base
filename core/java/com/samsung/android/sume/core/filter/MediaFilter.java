package com.samsung.android.sume.core.filter;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.p044nn.NNDescriptor;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.message.MessageConsumer;
import com.samsung.android.sume.core.message.MessageProducer;
import com.samsung.android.sume.core.types.OptionBase;
import com.samsung.android.sume.core.types.PadType;
import com.samsung.android.sume.core.types.SplitType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
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

    Stream<MediaFilter> stream();

    default void prepare() {
    }

    default void release() {
    }

    default String getId() {
        MFDescriptor descriptor = getDescriptor();
        return descriptor instanceof NNDescriptor ? ((NNDescriptor) descriptor).getModelId() : descriptor.getFilterId();
    }

    default void pause() {
    }

    default void resume() {
    }

    default void setMessageProducer(MessageProducer messageProducer) {
    }

    default void accept(MediaFilterRetriever retriever, MediaFilter parent) {
        retriever.retrieve(this, parent);
    }

    public static class Option extends OptionBase {
        private static final String TAG = Def.tagOf((Class<?>) Option.class);
        public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() { // from class: com.samsung.android.sume.core.filter.MediaFilter.Option.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Option createFromParcel(Parcel in) {
                return new Option(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Option[] newArray(int size) {
                return new Option[size];
            }
        };

        public Option() {
        }

        protected Option(Parcel in) {
            super(in);
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

        public void setPad(Pair<PadType, Integer> pad) {
            set(1, (Object) pad);
        }

        public boolean isAllowPartialConnection() {
            return ((Boolean) get(0, false)).booleanValue();
        }

        public void setAllowPartialConnection(boolean allowPartialConnection) {
            set(0, (Object) Boolean.valueOf(allowPartialConnection));
        }

        public boolean getUseExternalBufferComposer() {
            return ((Boolean) get(3, false)).booleanValue();
        }

        public void setUseExternalBufferComposer(boolean useExternalBufferComposer) {
            set(3, (Object) Boolean.valueOf(useExternalBufferComposer));
        }

        public boolean isKeepFilterDatatype() {
            return ((Boolean) get(4, false)).booleanValue();
        }

        public void setKeepFilterDatatype(boolean keepFilterDatatype) {
            set(4, (Object) Boolean.valueOf(keepFilterDatatype));
        }

        public boolean isBatchIO() {
            return ((Boolean) get(5, false)).booleanValue();
        }

        public void setBatchIO(boolean batchIO) {
            set(5, (Object) Boolean.valueOf(batchIO));
        }

        public boolean isWaitToReceiveAll() {
            return ((Boolean) get(6, false)).booleanValue();
        }

        public void setWaitToReceiveAll(boolean waitToReceiveAll) {
            set(6, (Object) Boolean.valueOf(waitToReceiveAll));
        }

        public boolean isInputWithEvaluationValue() {
            return ((Boolean) get(7, false)).booleanValue();
        }

        public void setInputWithEvaluationValue(boolean inputWithEvaluationValue) {
            set(7, (Object) Boolean.valueOf(inputWithEvaluationValue));
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

        public void setFilterIgnorable(boolean ignorable) {
            set(10, (Object) Boolean.valueOf(ignorable));
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public Option set(int option) {
            super.set(option);
            return this;
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public Option set(int option, Object data) {
            super.set(option, data);
            return this;
        }
    }
}
