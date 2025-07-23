package android.animation;

/* loaded from: classes.dex */
public abstract class BidirectionalTypeConverter<T, V> extends TypeConverter<T, V> {
    private BidirectionalTypeConverter mInvertedConverter;

    public abstract T convertBack(V v);

    public BidirectionalTypeConverter(Class<T> cls, Class<V> cls2) {
        super(cls, cls2);
    }

    public BidirectionalTypeConverter<V, T> invert() {
        if (this.mInvertedConverter == null) {
            this.mInvertedConverter = new InvertedConverter(this);
        }
        return this.mInvertedConverter;
    }

    private static class InvertedConverter<From, To> extends BidirectionalTypeConverter<From, To> {
        private BidirectionalTypeConverter<To, From> mConverter;

        public InvertedConverter(BidirectionalTypeConverter<To, From> bidirectionalTypeConverter) {
            super(bidirectionalTypeConverter.getTargetType(), bidirectionalTypeConverter.getSourceType());
            this.mConverter = bidirectionalTypeConverter;
        }

        @Override // android.animation.BidirectionalTypeConverter
        public From convertBack(To to) {
            return this.mConverter.convert(to);
        }

        @Override // android.animation.TypeConverter
        public To convert(From from) {
            return this.mConverter.convertBack(from);
        }
    }
}
