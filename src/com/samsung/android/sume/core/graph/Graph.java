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

    default MediaBuffer run(MediaBuffer mediaBuffer) {
        ArrayList arrayList = new ArrayList();
        run(new ArrayList<MediaBuffer>(mediaBuffer) { // from class: com.samsung.android.sume.core.graph.Graph.1
            final /* synthetic */ MediaBuffer val$buffer;

            {
                this.val$buffer = mediaBuffer;
                add(mediaBuffer);
            }
        }, arrayList);
        Def.require(arrayList.size() == 1);
        return arrayList.get(0);
    }

    default void run(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2) {
        ArrayList<MediaBuffer> arrayList = new ArrayList<MediaBuffer>(mediaBuffer2) { // from class: com.samsung.android.sume.core.graph.Graph.2
            final /* synthetic */ MediaBuffer val$outputBuffer;

            {
                this.val$outputBuffer = mediaBuffer2;
                add(mediaBuffer2);
            }
        };
        run(new ArrayList<MediaBuffer>(mediaBuffer) { // from class: com.samsung.android.sume.core.graph.Graph.3
            final /* synthetic */ MediaBuffer val$inputBuffer;

            {
                this.val$inputBuffer = mediaBuffer;
                add(mediaBuffer);
            }
        }, arrayList);
        Def.require(arrayList.size() == 1);
    }

    default List<MediaBuffer> run(List<MediaBuffer> list) {
        ArrayList arrayList = new ArrayList();
        run(list, arrayList);
        Def.require(list.size() == arrayList.size());
        return arrayList;
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
        public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() { // from class: com.samsung.android.sume.core.graph.Graph.Option.1
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

        public long getMaxDuration(TimeUnit timeUnit) {
            return timeUnit.convert(((Long) get(0, 0L)).longValue(), TimeUnit.MILLISECONDS);
        }

        public Option setMaxDuration(long j, TimeUnit timeUnit) {
            set(0, Long.valueOf(timeUnit.toMillis(j)));
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

        public Option ignoreFilterException(Object... objArr) {
            set(6, Arrays.asList(objArr));
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
        public OptionBase set(int i) {
            return super.set(i);
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public OptionBase set(int i, Object obj) {
            return super.set(i, obj);
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public void clear() {
            if (get(1) != null) {
                ((DiskCache) get(1)).close();
            }
            super.clear();
        }
    }
}
