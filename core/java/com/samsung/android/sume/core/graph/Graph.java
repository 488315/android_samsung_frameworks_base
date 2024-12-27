package com.samsung.android.sume.core.graph;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.cache.DiskCache;
import com.samsung.android.sume.core.message.MessageSubscriber;
import com.samsung.android.sume.core.types.OptionBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public interface Graph<T> {
    void pause();

    void release();

    void resume();

    void run(List<MediaBuffer> list, List<MediaBuffer> list2);

    void setMessageSubscriber(MessageSubscriber messageSubscriber);

    default MediaBuffer run(final MediaBuffer buffer) {
        List<MediaBuffer> output = new ArrayList<>();
        run(
                new ArrayList<
                        MediaBuffer>() { // from class: com.samsung.android.sume.core.graph.Graph.1
                    {
                        add(buffer);
                    }
                },
                output);
        Def.require(output.size() == 1);
        return output.get(0);
    }

    default void run(final MediaBuffer inputBuffer, final MediaBuffer outputBuffer) {
        List<MediaBuffer> output =
                new ArrayList<
                        MediaBuffer>() { // from class: com.samsung.android.sume.core.graph.Graph.2
                    {
                        add(outputBuffer);
                    }
                };
        run(
                new ArrayList<
                        MediaBuffer>() { // from class: com.samsung.android.sume.core.graph.Graph.3
                    {
                        add(inputBuffer);
                    }
                },
                output);
        Def.require(output.size() == 1);
    }

    default List<MediaBuffer> run(List<MediaBuffer> buffers) {
        List<MediaBuffer> output = new ArrayList<>();
        run(buffers, output);
        Def.require(buffers.size() == output.size());
        return output;
    }

    public static class Option extends OptionBase {
        public static final int CACHEABLE = 1;
        public static final int IGNORE_FILTER_EXCEPTION = 6;
        public static final int MAX_DURATION = 0;
        public static final int OUTPUT_ON_EVENT_CALLBACK = 9;
        public static final int PACKED_IO_BUFFERS = 2;
        public static final int RECEIVE_ALL_EXCEPTION = 7;
        public static final int RESTORE_META_DATA = 4;
        public static final int RUN_ONE_BY_ONE = 3;
        public static final int SUPPORT_ALPHA_CHANNEL = 5;
        public static final int TRACE_MEDIAFILTER = 8;
        private static final String TAG = Def.tagOf((Class<?>) Option.class);
        public static final Parcelable.Creator<Option> CREATOR =
                new Parcelable.Creator<
                        Option>() { // from class:
                                    // com.samsung.android.sume.core.graph.Graph.Option.1
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

        public Option() {}

        protected Option(Parcel in) {
            super(in);
        }

        public long getMaxDuration(TimeUnit timeUnit) {
            long maxDurationMs = ((Long) get(0, 0L)).longValue();
            return timeUnit.convert(maxDurationMs, TimeUnit.MILLISECONDS);
        }

        public Option setMaxDuration(long maxDuration, TimeUnit timeUnit) {
            set(0, Long.valueOf(timeUnit.toMillis(maxDuration)));
            return this;
        }

        public boolean isCacheable() {
            return contains(1);
        }

        public Option cacheable() {
            set(1);
            return this;
        }

        public boolean isPackedIOBuffers() {
            return contains(2);
        }

        public Option packedIOBuffers() {
            set(2);
            return this;
        }

        public boolean isRunOneByOne() {
            return contains(3);
        }

        public Option runOneByOne() {
            set(3);
            return this;
        }

        public boolean isRestoreMetadata() {
            return contains(4);
        }

        public Option restoreMetadata() {
            set(4);
            return this;
        }

        public boolean isSupportAlphaChannel() {
            return contains(5);
        }

        public Option supportAlphaChannel() {
            set(5);
            return this;
        }

        public boolean isIgnoreFilterException() {
            return contains(6);
        }

        public <V> V getIgnoreFilterException() {
            return (V) get(6);
        }

        public Option ignoreFilterException(Object... obj) {
            set(6, Arrays.asList(obj));
            return this;
        }

        public Option receiveAllException() {
            set(7);
            return this;
        }

        public Option traceMediaFilter() {
            set(8);
            return this;
        }

        public boolean isTraceMediaFilter() {
            return contains(8);
        }

        public Option outputOnEventCallback() {
            set(9);
            return this;
        }

        public boolean isOutputOnEventCallback() {
            return contains(9);
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public OptionBase set(int option) {
            return super.set(option);
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public OptionBase set(int option, Object data) {
            return super.set(option, data);
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public void clear() {
            if (get(1) != null) {
                DiskCache cache = (DiskCache) get(1);
                cache.close();
            }
            super.clear();
        }
    }
}
