package android.text.style;

import android.os.Parcel;
import android.os.PersistableBundle;
import android.text.ParcelableSpan;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes4.dex */
public class TtsSpan implements ParcelableSpan {
    public static final String ANIMACY_ANIMATE = "android.animate";
    public static final String ANIMACY_INANIMATE = "android.inanimate";
    public static final String ARG_ANIMACY = "android.arg.animacy";
    public static final String ARG_CASE = "android.arg.case";
    public static final String ARG_COUNTRY_CODE = "android.arg.country_code";
    public static final String ARG_CURRENCY = "android.arg.money";
    public static final String ARG_DAY = "android.arg.day";
    public static final String ARG_DENOMINATOR = "android.arg.denominator";
    public static final String ARG_DIGITS = "android.arg.digits";
    public static final String ARG_DOMAIN = "android.arg.domain";
    public static final String ARG_EXTENSION = "android.arg.extension";
    public static final String ARG_FRACTIONAL_PART = "android.arg.fractional_part";
    public static final String ARG_FRAGMENT_ID = "android.arg.fragment_id";
    public static final String ARG_GENDER = "android.arg.gender";
    public static final String ARG_HOURS = "android.arg.hours";
    public static final String ARG_INTEGER_PART = "android.arg.integer_part";
    public static final String ARG_MINUTES = "android.arg.minutes";
    public static final String ARG_MONTH = "android.arg.month";
    public static final String ARG_MULTIPLICITY = "android.arg.multiplicity";
    public static final String ARG_NUMBER = "android.arg.number";
    public static final String ARG_NUMBER_PARTS = "android.arg.number_parts";
    public static final String ARG_NUMERATOR = "android.arg.numerator";
    public static final String ARG_PASSWORD = "android.arg.password";
    public static final String ARG_PATH = "android.arg.path";
    public static final String ARG_PORT = "android.arg.port";
    public static final String ARG_PROTOCOL = "android.arg.protocol";
    public static final String ARG_QUANTITY = "android.arg.quantity";
    public static final String ARG_QUERY_STRING = "android.arg.query_string";
    public static final String ARG_SECONDS = "android.arg.seconds";
    public static final String ARG_TEXT = "android.arg.text";
    public static final String ARG_UNIT = "android.arg.unit";
    public static final String ARG_USERNAME = "android.arg.username";
    public static final String ARG_VERBATIM = "android.arg.verbatim";
    public static final String ARG_WEEKDAY = "android.arg.weekday";
    public static final String ARG_YEAR = "android.arg.year";
    public static final String CASE_ABLATIVE = "android.ablative";
    public static final String CASE_ACCUSATIVE = "android.accusative";
    public static final String CASE_DATIVE = "android.dative";
    public static final String CASE_GENITIVE = "android.genitive";
    public static final String CASE_INSTRUMENTAL = "android.instrumental";
    public static final String CASE_LOCATIVE = "android.locative";
    public static final String CASE_NOMINATIVE = "android.nominative";
    public static final String CASE_VOCATIVE = "android.vocative";
    public static final String GENDER_FEMALE = "android.female";
    public static final String GENDER_MALE = "android.male";
    public static final String GENDER_NEUTRAL = "android.neutral";
    public static final int MONTH_APRIL = 3;
    public static final int MONTH_AUGUST = 7;
    public static final int MONTH_DECEMBER = 11;
    public static final int MONTH_FEBRUARY = 1;
    public static final int MONTH_JANUARY = 0;
    public static final int MONTH_JULY = 6;
    public static final int MONTH_JUNE = 5;
    public static final int MONTH_MARCH = 2;
    public static final int MONTH_MAY = 4;
    public static final int MONTH_NOVEMBER = 10;
    public static final int MONTH_OCTOBER = 9;
    public static final int MONTH_SEPTEMBER = 8;
    public static final String MULTIPLICITY_DUAL = "android.dual";
    public static final String MULTIPLICITY_PLURAL = "android.plural";
    public static final String MULTIPLICITY_SINGLE = "android.single";
    public static final String TYPE_CARDINAL = "android.type.cardinal";
    public static final String TYPE_DATE = "android.type.date";
    public static final String TYPE_DECIMAL = "android.type.decimal";
    public static final String TYPE_DIGITS = "android.type.digits";
    public static final String TYPE_DURATION = "android.type.duration";
    public static final String TYPE_ELECTRONIC = "android.type.electronic";
    public static final String TYPE_FRACTION = "android.type.fraction";
    public static final String TYPE_MEASURE = "android.type.measure";
    public static final String TYPE_MONEY = "android.type.money";
    public static final String TYPE_ORDINAL = "android.type.ordinal";
    public static final String TYPE_TELEPHONE = "android.type.telephone";
    public static final String TYPE_TEXT = "android.type.text";
    public static final String TYPE_TIME = "android.type.time";
    public static final String TYPE_VERBATIM = "android.type.verbatim";
    public static final int WEEKDAY_FRIDAY = 6;
    public static final int WEEKDAY_MONDAY = 2;
    public static final int WEEKDAY_SATURDAY = 7;
    public static final int WEEKDAY_SUNDAY = 1;
    public static final int WEEKDAY_THURSDAY = 5;
    public static final int WEEKDAY_TUESDAY = 3;
    public static final int WEEKDAY_WEDNESDAY = 4;
    private final PersistableBundle mArgs;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 24;
    }

    public TtsSpan(String str, PersistableBundle persistableBundle) {
        this.mType = str;
        this.mArgs = persistableBundle;
    }

    public TtsSpan(Parcel parcel) {
        this.mType = parcel.readString();
        this.mArgs = parcel.readPersistableBundle();
    }

    public String getType() {
        return this.mType;
    }

    public PersistableBundle getArgs() {
        return this.mArgs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeString(this.mType);
        parcel.writePersistableBundle(this.mArgs);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public static class Builder<C extends Builder<?>> {
        private PersistableBundle mArgs = new PersistableBundle();
        private final String mType;

        public Builder(String str) {
            this.mType = str;
        }

        public TtsSpan build() {
            return new TtsSpan(this.mType, this.mArgs);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public C setStringArgument(String str, String str2) {
            this.mArgs.putString(str, str2);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public C setIntArgument(String str, int i) {
            this.mArgs.putInt(str, i);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public C setLongArgument(String str, long j) {
            this.mArgs.putLong(str, j);
            return this;
        }
    }

    public static class SemioticClassBuilder<C extends SemioticClassBuilder<?>> extends Builder<C> {
        public SemioticClassBuilder(String str) {
            super(str);
        }

        public C setGender(String str) {
            return setStringArgument(TtsSpan.ARG_GENDER, str);
        }

        public C setAnimacy(String str) {
            return setStringArgument(TtsSpan.ARG_ANIMACY, str);
        }

        public C setMultiplicity(String str) {
            return setStringArgument(TtsSpan.ARG_MULTIPLICITY, str);
        }

        public C setCase(String str) {
            return setStringArgument(TtsSpan.ARG_CASE, str);
        }
    }

    public static class TextBuilder extends SemioticClassBuilder<TextBuilder> {
        public TextBuilder() {
            super(TtsSpan.TYPE_TEXT);
        }

        public TextBuilder(String str) {
            this();
            setText(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TextBuilder setText(String str) {
            return (TextBuilder) setStringArgument(TtsSpan.ARG_TEXT, str);
        }
    }

    public static class CardinalBuilder extends SemioticClassBuilder<CardinalBuilder> {
        public CardinalBuilder() {
            super(TtsSpan.TYPE_CARDINAL);
        }

        public CardinalBuilder(long j) {
            this();
            setNumber(j);
        }

        public CardinalBuilder(String str) {
            this();
            setNumber(str);
        }

        public CardinalBuilder setNumber(long j) {
            return setNumber(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public CardinalBuilder setNumber(String str) {
            return (CardinalBuilder) setStringArgument(TtsSpan.ARG_NUMBER, str);
        }
    }

    public static class OrdinalBuilder extends SemioticClassBuilder<OrdinalBuilder> {
        public OrdinalBuilder() {
            super(TtsSpan.TYPE_ORDINAL);
        }

        public OrdinalBuilder(long j) {
            this();
            setNumber(j);
        }

        public OrdinalBuilder(String str) {
            this();
            setNumber(str);
        }

        public OrdinalBuilder setNumber(long j) {
            return setNumber(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public OrdinalBuilder setNumber(String str) {
            return (OrdinalBuilder) setStringArgument(TtsSpan.ARG_NUMBER, str);
        }
    }

    public static class DecimalBuilder extends SemioticClassBuilder<DecimalBuilder> {
        public DecimalBuilder() {
            super(TtsSpan.TYPE_DECIMAL);
        }

        public DecimalBuilder(double d, int i, int i2) {
            this();
            setArgumentsFromDouble(d, i, i2);
        }

        public DecimalBuilder(String str, String str2) {
            this();
            setIntegerPart(str);
            setFractionalPart(str2);
        }

        public DecimalBuilder setArgumentsFromDouble(double d, int i, int i2) {
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setMinimumFractionDigits(i2);
            numberFormat.setMaximumFractionDigits(i2);
            numberFormat.setGroupingUsed(false);
            String str = numberFormat.format(d);
            int iIndexOf = str.indexOf(46);
            if (iIndexOf >= 0) {
                setIntegerPart(str.substring(0, iIndexOf));
                setFractionalPart(str.substring(iIndexOf + 1));
                return this;
            }
            setIntegerPart(str);
            return this;
        }

        public DecimalBuilder setIntegerPart(long j) {
            return setIntegerPart(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DecimalBuilder setIntegerPart(String str) {
            return (DecimalBuilder) setStringArgument(TtsSpan.ARG_INTEGER_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DecimalBuilder setFractionalPart(String str) {
            return (DecimalBuilder) setStringArgument(TtsSpan.ARG_FRACTIONAL_PART, str);
        }
    }

    public static class FractionBuilder extends SemioticClassBuilder<FractionBuilder> {
        public FractionBuilder() {
            super(TtsSpan.TYPE_FRACTION);
        }

        public FractionBuilder(long j, long j2, long j3) {
            this();
            setIntegerPart(j);
            setNumerator(j2);
            setDenominator(j3);
        }

        public FractionBuilder setIntegerPart(long j) {
            return setIntegerPart(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public FractionBuilder setIntegerPart(String str) {
            return (FractionBuilder) setStringArgument(TtsSpan.ARG_INTEGER_PART, str);
        }

        public FractionBuilder setNumerator(long j) {
            return setNumerator(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public FractionBuilder setNumerator(String str) {
            return (FractionBuilder) setStringArgument(TtsSpan.ARG_NUMERATOR, str);
        }

        public FractionBuilder setDenominator(long j) {
            return setDenominator(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public FractionBuilder setDenominator(String str) {
            return (FractionBuilder) setStringArgument(TtsSpan.ARG_DENOMINATOR, str);
        }
    }

    public static class MeasureBuilder extends SemioticClassBuilder<MeasureBuilder> {
        public MeasureBuilder() {
            super(TtsSpan.TYPE_MEASURE);
        }

        public MeasureBuilder setNumber(long j) {
            return setNumber(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MeasureBuilder setNumber(String str) {
            return (MeasureBuilder) setStringArgument(TtsSpan.ARG_NUMBER, str);
        }

        public MeasureBuilder setIntegerPart(long j) {
            return setIntegerPart(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MeasureBuilder setIntegerPart(String str) {
            return (MeasureBuilder) setStringArgument(TtsSpan.ARG_INTEGER_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MeasureBuilder setFractionalPart(String str) {
            return (MeasureBuilder) setStringArgument(TtsSpan.ARG_FRACTIONAL_PART, str);
        }

        public MeasureBuilder setNumerator(long j) {
            return setNumerator(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MeasureBuilder setNumerator(String str) {
            return (MeasureBuilder) setStringArgument(TtsSpan.ARG_NUMERATOR, str);
        }

        public MeasureBuilder setDenominator(long j) {
            return setDenominator(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MeasureBuilder setDenominator(String str) {
            return (MeasureBuilder) setStringArgument(TtsSpan.ARG_DENOMINATOR, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MeasureBuilder setUnit(String str) {
            return (MeasureBuilder) setStringArgument(TtsSpan.ARG_UNIT, str);
        }
    }

    public static class TimeBuilder extends SemioticClassBuilder<TimeBuilder> {
        public TimeBuilder() {
            super(TtsSpan.TYPE_TIME);
        }

        public TimeBuilder(int i, int i2) {
            this();
            setHours(i);
            setMinutes(i2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TimeBuilder setHours(int i) {
            return (TimeBuilder) setIntArgument(TtsSpan.ARG_HOURS, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TimeBuilder setMinutes(int i) {
            return (TimeBuilder) setIntArgument(TtsSpan.ARG_MINUTES, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TimeBuilder setSeconds(int i) {
            return (TimeBuilder) setIntArgument(TtsSpan.ARG_SECONDS, i);
        }
    }

    public static class DurationBuilder extends SemioticClassBuilder<DurationBuilder> {
        public DurationBuilder() {
            super(TtsSpan.TYPE_DURATION);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DurationBuilder setHours(int i) {
            return (DurationBuilder) setIntArgument(TtsSpan.ARG_HOURS, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DurationBuilder setMinutes(int i) {
            return (DurationBuilder) setIntArgument(TtsSpan.ARG_MINUTES, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DurationBuilder setSeconds(int i) {
            return (DurationBuilder) setIntArgument(TtsSpan.ARG_SECONDS, i);
        }
    }

    public static class DateBuilder extends SemioticClassBuilder<DateBuilder> {
        public DateBuilder() {
            super(TtsSpan.TYPE_DATE);
        }

        public DateBuilder(Integer num, Integer num2, Integer num3, Integer num4) {
            this();
            if (num != null) {
                setWeekday(num.intValue());
            }
            if (num2 != null) {
                setDay(num2.intValue());
            }
            if (num3 != null) {
                setMonth(num3.intValue());
            }
            if (num4 != null) {
                setYear(num4.intValue());
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DateBuilder setWeekday(int i) {
            return (DateBuilder) setIntArgument(TtsSpan.ARG_WEEKDAY, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DateBuilder setDay(int i) {
            return (DateBuilder) setIntArgument(TtsSpan.ARG_DAY, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DateBuilder setMonth(int i) {
            return (DateBuilder) setIntArgument(TtsSpan.ARG_MONTH, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DateBuilder setYear(int i) {
            return (DateBuilder) setIntArgument(TtsSpan.ARG_YEAR, i);
        }
    }

    public static class MoneyBuilder extends SemioticClassBuilder<MoneyBuilder> {
        public MoneyBuilder() {
            super(TtsSpan.TYPE_MONEY);
        }

        public MoneyBuilder setIntegerPart(long j) {
            return setIntegerPart(String.valueOf(j));
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MoneyBuilder setIntegerPart(String str) {
            return (MoneyBuilder) setStringArgument(TtsSpan.ARG_INTEGER_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MoneyBuilder setFractionalPart(String str) {
            return (MoneyBuilder) setStringArgument(TtsSpan.ARG_FRACTIONAL_PART, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MoneyBuilder setCurrency(String str) {
            return (MoneyBuilder) setStringArgument(TtsSpan.ARG_CURRENCY, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MoneyBuilder setQuantity(String str) {
            return (MoneyBuilder) setStringArgument(TtsSpan.ARG_QUANTITY, str);
        }
    }

    public static class TelephoneBuilder extends SemioticClassBuilder<TelephoneBuilder> {
        public TelephoneBuilder() {
            super(TtsSpan.TYPE_TELEPHONE);
        }

        public TelephoneBuilder(String str) {
            this();
            setNumberParts(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TelephoneBuilder setCountryCode(String str) {
            return (TelephoneBuilder) setStringArgument(TtsSpan.ARG_COUNTRY_CODE, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TelephoneBuilder setNumberParts(String str) {
            return (TelephoneBuilder) setStringArgument(TtsSpan.ARG_NUMBER_PARTS, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TelephoneBuilder setExtension(String str) {
            return (TelephoneBuilder) setStringArgument(TtsSpan.ARG_EXTENSION, str);
        }
    }

    public static class ElectronicBuilder extends SemioticClassBuilder<ElectronicBuilder> {
        public ElectronicBuilder() {
            super(TtsSpan.TYPE_ELECTRONIC);
        }

        public ElectronicBuilder setEmailArguments(String str, String str2) {
            return setDomain(str2).setUsername(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setProtocol(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_PROTOCOL, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setUsername(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_USERNAME, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setPassword(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_PASSWORD, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setDomain(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_DOMAIN, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setPort(int i) {
            return (ElectronicBuilder) setIntArgument(TtsSpan.ARG_PORT, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setPath(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_PATH, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setQueryString(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_QUERY_STRING, str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ElectronicBuilder setFragmentId(String str) {
            return (ElectronicBuilder) setStringArgument(TtsSpan.ARG_FRAGMENT_ID, str);
        }
    }

    public static class DigitsBuilder extends SemioticClassBuilder<DigitsBuilder> {
        public DigitsBuilder() {
            super(TtsSpan.TYPE_DIGITS);
        }

        public DigitsBuilder(String str) {
            this();
            setDigits(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DigitsBuilder setDigits(String str) {
            return (DigitsBuilder) setStringArgument(TtsSpan.ARG_DIGITS, str);
        }
    }

    public static class VerbatimBuilder extends SemioticClassBuilder<VerbatimBuilder> {
        public VerbatimBuilder() {
            super(TtsSpan.TYPE_VERBATIM);
        }

        public VerbatimBuilder(String str) {
            this();
            setVerbatim(str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public VerbatimBuilder setVerbatim(String str) {
            return (VerbatimBuilder) setStringArgument(TtsSpan.ARG_VERBATIM, str);
        }
    }
}
