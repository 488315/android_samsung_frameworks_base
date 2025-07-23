package androidx.compose.foundation.internal;

import android.content.ClipData;
import android.os.Parcel;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Base64;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.platform.ClipEntry;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.unit.TextUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.ULong;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ClipboardUtils_androidKt {
    public static final AnnotatedString readAnnotatedString(ClipEntry clipEntry) {
        CharSequence text;
        CharSequence charSequence;
        byte b;
        byte b2;
        int i;
        int i2;
        int i3;
        byte b3 = 1;
        int i4 = ClipboardUtils.$r8$clinit;
        byte b4 = 0;
        ClipData.Item itemAt = clipEntry.clipData.getItemAt(0);
        if (itemAt == null || (text = itemAt.getText()) == null) {
            return null;
        }
        byte b5 = 2;
        if (!(text instanceof Spanned)) {
            return new AnnotatedString(text.toString(), null, 2, null);
        }
        Spanned spanned = (Spanned) text;
        Annotation[] annotationArr = (Annotation[]) spanned.getSpans(0, text.length(), Annotation.class);
        ArrayList arrayList = new ArrayList();
        int length = annotationArr.length - 1;
        if (length >= 0) {
            int i5 = 0;
            while (true) {
                Annotation annotation = annotationArr[i5];
                if (Intrinsics.areEqual(annotation.getKey(), "androidx.compose.text.SpanStyle")) {
                    int spanStart = spanned.getSpanStart(annotation);
                    int spanEnd = spanned.getSpanEnd(annotation);
                    DecodeHelper decodeHelper = new DecodeHelper(annotation.getValue());
                    MutableSpanStyle mutableSpanStyle = new MutableSpanStyle(0L, 0L, null, null, null, null, null, 0L, null, null, null, 0L, null, null, 16383, null);
                    while (decodeHelper.parcel.dataAvail() > b3) {
                        byte readByte = decodeHelper.parcel.readByte();
                        if (readByte == b3) {
                            if (decodeHelper.parcel.dataAvail() < 8) {
                                break;
                            }
                            long readLong = decodeHelper.parcel.readLong();
                            int i6 = ULong.$r8$clinit;
                            Color.Companion companion = Color.Companion;
                            mutableSpanStyle.color = readLong;
                        } else if (readByte != b5) {
                            b2 = b4;
                            if (readByte == 3) {
                                if (decodeHelper.parcel.dataAvail() < 4) {
                                    b = b3;
                                    break;
                                }
                                mutableSpanStyle.fontWeight = new FontWeight(decodeHelper.parcel.readInt());
                                b4 = b2;
                            } else if (readByte == 4) {
                                if (decodeHelper.parcel.dataAvail() < b3) {
                                    b = b3;
                                    break;
                                }
                                byte readByte2 = decodeHelper.parcel.readByte();
                                if (readByte2 == 0) {
                                    FontStyle.Companion.getClass();
                                } else if (readByte2 == b3) {
                                    FontStyle.Companion.getClass();
                                    i2 = FontStyle.Italic;
                                    mutableSpanStyle.fontStyle = FontStyle.m764boximpl(i2);
                                    b4 = b2;
                                } else {
                                    FontStyle.Companion.getClass();
                                }
                                i2 = b2;
                                mutableSpanStyle.fontStyle = FontStyle.m764boximpl(i2);
                                b4 = b2;
                            } else if (readByte != 5) {
                                if (readByte == 6) {
                                    mutableSpanStyle.fontFeatureSettings = decodeHelper.parcel.readString();
                                } else if (readByte == 7) {
                                    if (decodeHelper.parcel.dataAvail() < 5) {
                                        b = b3;
                                        break;
                                    }
                                    mutableSpanStyle.letterSpacing = decodeHelper.m88decodeTextUnitXSAIIZE();
                                } else if (readByte != 8) {
                                    if (readByte != 9) {
                                        if (readByte != 10) {
                                            if (readByte != 11) {
                                                b = b3;
                                                if (readByte == 12) {
                                                    if (decodeHelper.parcel.dataAvail() < 20) {
                                                        break;
                                                    }
                                                    long readLong2 = decodeHelper.parcel.readLong();
                                                    int i7 = ULong.$r8$clinit;
                                                    Color.Companion companion2 = Color.Companion;
                                                    Offset.Companion companion3 = Offset.Companion;
                                                    mutableSpanStyle.shadow = new Shadow(readLong2, (Float.floatToRawIntBits(decodeHelper.parcel.readFloat()) << 32) | (Float.floatToRawIntBits(decodeHelper.parcel.readFloat()) & 4294967295L), decodeHelper.parcel.readFloat(), null);
                                                    b4 = b2;
                                                    length = length;
                                                    b3 = b;
                                                    b5 = 2;
                                                }
                                            } else if (decodeHelper.parcel.dataAvail() >= 4) {
                                                int readInt = decodeHelper.parcel.readInt();
                                                TextDecoration.Companion.getClass();
                                                TextDecoration textDecoration = TextDecoration.LineThrough;
                                                byte b6 = (textDecoration.mask & readInt) != 0 ? b3 : b2;
                                                TextDecoration textDecoration2 = TextDecoration.Underline;
                                                b = b3;
                                                byte b7 = (textDecoration2.mask & readInt) != 0 ? b : b2;
                                                if (b6 != 0 && b7 != 0) {
                                                    List asList = Arrays.asList(textDecoration, textDecoration2);
                                                    Integer valueOf = Integer.valueOf(b2);
                                                    int size = asList.size();
                                                    for (int i8 = b2; i8 < size; i8++) {
                                                        valueOf = Integer.valueOf(valueOf.intValue() | ((TextDecoration) asList.get(i8)).mask);
                                                    }
                                                    textDecoration = new TextDecoration(valueOf.intValue());
                                                } else if (b6 == 0) {
                                                    textDecoration = b7 != 0 ? textDecoration2 : TextDecoration.None;
                                                }
                                                mutableSpanStyle.textDecoration = textDecoration;
                                            }
                                            b4 = b2;
                                            b3 = b;
                                        } else if (decodeHelper.parcel.dataAvail() >= 8) {
                                            long readLong3 = decodeHelper.parcel.readLong();
                                            int i9 = ULong.$r8$clinit;
                                            Color.Companion companion4 = Color.Companion;
                                            mutableSpanStyle.background = readLong3;
                                        }
                                        b = b3;
                                        break;
                                    }
                                    if (decodeHelper.parcel.dataAvail() < 8) {
                                        b = b3;
                                        break;
                                    }
                                    mutableSpanStyle.textGeometricTransform = new TextGeometricTransform(decodeHelper.parcel.readFloat(), decodeHelper.parcel.readFloat());
                                } else {
                                    if (decodeHelper.parcel.dataAvail() < 4) {
                                        b = b3;
                                        break;
                                    }
                                    mutableSpanStyle.baselineShift = BaselineShift.m791boximpl(decodeHelper.parcel.readFloat());
                                }
                                b4 = b2;
                            } else {
                                if (decodeHelper.parcel.dataAvail() < b3) {
                                    b = b3;
                                    break;
                                }
                                byte readByte3 = decodeHelper.parcel.readByte();
                                if (readByte3 == 0) {
                                    FontSynthesis.Companion.getClass();
                                } else {
                                    if (readByte3 == b3) {
                                        FontSynthesis.Companion.getClass();
                                        i3 = FontSynthesis.All;
                                    } else if (readByte3 == 3) {
                                        FontSynthesis.Companion.getClass();
                                        i3 = FontSynthesis.Style;
                                    } else if (readByte3 == b5) {
                                        FontSynthesis.Companion.getClass();
                                        i3 = FontSynthesis.Weight;
                                    } else {
                                        FontSynthesis.Companion.getClass();
                                    }
                                    mutableSpanStyle.fontSynthesis = FontSynthesis.m766boximpl(i3);
                                    b4 = b2;
                                }
                                i3 = b2;
                                mutableSpanStyle.fontSynthesis = FontSynthesis.m766boximpl(i3);
                                b4 = b2;
                            }
                        } else {
                            if (decodeHelper.parcel.dataAvail() < 5) {
                                break;
                            }
                            mutableSpanStyle.fontSize = decodeHelper.m88decodeTextUnitXSAIIZE();
                        }
                    }
                    b = b3;
                    b2 = b4;
                    i = length;
                    charSequence = text;
                    arrayList.add(new AnnotatedString.Range(new SpanStyle(mutableSpanStyle.color, mutableSpanStyle.fontSize, mutableSpanStyle.fontWeight, mutableSpanStyle.fontStyle, mutableSpanStyle.fontSynthesis, mutableSpanStyle.fontFamily, mutableSpanStyle.fontFeatureSettings, mutableSpanStyle.letterSpacing, mutableSpanStyle.baselineShift, mutableSpanStyle.textGeometricTransform, mutableSpanStyle.localeList, mutableSpanStyle.background, mutableSpanStyle.textDecoration, mutableSpanStyle.shadow, (PlatformSpanStyle) null, (DrawStyle) null, 49152, (DefaultConstructorMarker) null), spanStart, spanEnd));
                } else {
                    b = b3;
                    charSequence = text;
                    b2 = b4;
                    i = length;
                }
                if (i5 == i) {
                    break;
                }
                i5++;
                b4 = b2;
                length = i;
                b3 = b;
                text = charSequence;
                b5 = 2;
            }
        } else {
            charSequence = text;
        }
        return new AnnotatedString(charSequence.toString(), arrayList, null, 4, null);
    }

    public static final ClipEntry toClipEntry(AnnotatedString annotatedString) {
        CharSequence charSequence;
        byte b;
        byte b2 = 1;
        int i = ClipboardUtils.$r8$clinit;
        List list = annotatedString.spanStylesOrNull;
        if (list == null) {
            list = EmptyList.INSTANCE;
        }
        if (list.isEmpty()) {
            charSequence = annotatedString.text;
        } else {
            SpannableString spannableString = new SpannableString(annotatedString.text);
            EncodeHelper encodeHelper = new EncodeHelper();
            List list2 = annotatedString.spanStylesOrNull;
            if (list2 == null) {
                list2 = EmptyList.INSTANCE;
            }
            int size = list2.size();
            int i2 = 0;
            while (i2 < size) {
                AnnotatedString.Range range = (AnnotatedString.Range) list2.get(i2);
                SpanStyle spanStyle = (SpanStyle) range.item;
                encodeHelper.parcel.recycle();
                encodeHelper.parcel = Parcel.obtain();
                long mo792getColor0d7_KjU = spanStyle.textForegroundStyle.mo792getColor0d7_KjU();
                Color.Companion.getClass();
                long j = Color.Unspecified;
                if (!ULong.m3427equalsimpl0(mo792getColor0d7_KjU, j)) {
                    encodeHelper.encode(b2);
                    encodeHelper.parcel.writeLong(spanStyle.textForegroundStyle.mo792getColor0d7_KjU());
                }
                TextUnit.Companion.getClass();
                long j2 = TextUnit.Unspecified;
                long j3 = spanStyle.fontSize;
                byte b3 = b2;
                if (!TextUnit.m866equalsimpl0(j3, j2)) {
                    encodeHelper.encode((byte) 2);
                    encodeHelper.m89encodeR2X_6o(j3);
                }
                FontWeight fontWeight = spanStyle.fontWeight;
                if (fontWeight != null) {
                    encodeHelper.encode((byte) 3);
                    encodeHelper.parcel.writeInt(fontWeight.weight);
                }
                FontStyle fontStyle = spanStyle.fontStyle;
                if (fontStyle != null) {
                    encodeHelper.encode((byte) 4);
                    FontStyle.Companion.getClass();
                    int i3 = fontStyle.value;
                    encodeHelper.encode((i3 != 0 && i3 == FontStyle.Italic) ? b3 : (byte) 0);
                }
                FontSynthesis fontSynthesis = spanStyle.fontSynthesis;
                if (fontSynthesis != null) {
                    encodeHelper.encode((byte) 5);
                    FontSynthesis.Companion.getClass();
                    int i4 = fontSynthesis.value;
                    if (i4 != 0) {
                        if (i4 == FontSynthesis.All) {
                            b = b3;
                        } else if (i4 == FontSynthesis.Weight) {
                            b = 2;
                        } else if (i4 == FontSynthesis.Style) {
                            b = 3;
                        }
                        encodeHelper.encode(b);
                    }
                    b = 0;
                    encodeHelper.encode(b);
                }
                String str = spanStyle.fontFeatureSettings;
                if (str != null) {
                    encodeHelper.encode((byte) 6);
                    encodeHelper.parcel.writeString(str);
                }
                long j4 = spanStyle.letterSpacing;
                if (!TextUnit.m866equalsimpl0(j4, j2)) {
                    encodeHelper.encode((byte) 7);
                    encodeHelper.m89encodeR2X_6o(j4);
                }
                BaselineShift baselineShift = spanStyle.baselineShift;
                if (baselineShift != null) {
                    encodeHelper.encode((byte) 8);
                    encodeHelper.encode(baselineShift.multiplier);
                }
                TextGeometricTransform textGeometricTransform = spanStyle.textGeometricTransform;
                if (textGeometricTransform != null) {
                    encodeHelper.encode((byte) 9);
                    encodeHelper.encode(textGeometricTransform.scaleX);
                    encodeHelper.encode(textGeometricTransform.skewX);
                }
                long j5 = spanStyle.background;
                if (!ULong.m3427equalsimpl0(j5, j)) {
                    encodeHelper.encode((byte) 10);
                    encodeHelper.parcel.writeLong(j5);
                }
                TextDecoration textDecoration = spanStyle.textDecoration;
                if (textDecoration != null) {
                    encodeHelper.encode((byte) 11);
                    encodeHelper.parcel.writeInt(textDecoration.mask);
                }
                Shadow shadow = spanStyle.shadow;
                if (shadow != null) {
                    encodeHelper.encode((byte) 12);
                    encodeHelper.parcel.writeLong(shadow.color);
                    long j6 = shadow.offset;
                    encodeHelper.encode(Float.intBitsToFloat((int) (j6 >> 32)));
                    encodeHelper.encode(Float.intBitsToFloat((int) (j6 & 4294967295L)));
                    encodeHelper.encode(shadow.blurRadius);
                }
                spannableString.setSpan(new Annotation("androidx.compose.text.SpanStyle", Base64.encodeToString(encodeHelper.parcel.marshall(), 0)), range.start, range.end, 33);
                i2++;
                b2 = b3;
            }
            charSequence = spannableString;
        }
        return new ClipEntry(ClipData.newPlainText("plain text", charSequence));
    }
}
