package android.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.drawable.Icon;
import android.graphics.text.LineBreakConfig;
import android.os.LocaleList;
import android.os.PersistableBundle;
import android.text.Annotation;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityReplacementSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.LineBreakConfigSpan;
import android.text.style.LineHeightSpan;
import android.text.style.LocaleSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.SpellCheckSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TtsSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.text.flags.Flags;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.Function;

/* loaded from: classes5.dex */
public class RemoteViewsSerializers {
    private static final String TAG = "RemoteViews";

    public static void writeEasyEditSpanToProto(ProtoOutputStream protoOutputStream, EasyEditSpan easyEditSpan) {
    }

    public static void writeSpellCheckSpanToProto(ProtoOutputStream protoOutputStream, SpellCheckSpan spellCheckSpan) {
    }

    public static void writeStrikethroughSpanToProto(ProtoOutputStream protoOutputStream, StrikethroughSpan strikethroughSpan) {
    }

    public static void writeSubscriptSpanToProto(ProtoOutputStream protoOutputStream, SubscriptSpan subscriptSpan) {
    }

    public static void writeSuperscriptSpanToProto(ProtoOutputStream protoOutputStream, SuperscriptSpan superscriptSpan) {
    }

    public static void writeUnderlineSpanToProto(ProtoOutputStream protoOutputStream, UnderlineSpan underlineSpan) {
    }

    public static void writeIconToProto(ProtoOutputStream protoOutputStream, Resources resources, Icon icon) {
        if (icon.getTintList() != null) {
            long start = protoOutputStream.start(1146756268034L);
            icon.getTintList().writeToProto(protoOutputStream);
            protoOutputStream.end(start);
        }
        protoOutputStream.write(1120986464257L, BlendMode.toValue(icon.getTintBlendMode()));
        switch (icon.getType()) {
            case 1:
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                icon.getBitmap().compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, byteArrayOutputStream);
                protoOutputStream.write(1151051235331L, byteArrayOutputStream.toByteArray());
                break;
            case 2:
                protoOutputStream.write(1138166333444L, resources.getResourceName(icon.getResId()));
                break;
            case 3:
                protoOutputStream.write(1151051235333L, icon.getDataBytes());
                break;
            case 4:
                protoOutputStream.write(1138166333446L, icon.getUriString());
                break;
            case 5:
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                icon.getBitmap().compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, byteArrayOutputStream2);
                protoOutputStream.write(1151051235336L, byteArrayOutputStream2.toByteArray());
                break;
            case 6:
                protoOutputStream.write(1138166333447L, icon.getUriString());
                break;
            default:
                Log.e(TAG, "Tried to serialize unknown Icon type " + icon.getType());
                break;
        }
    }

    public static Function<Resources, Icon> createIconFromProto(ProtoInputStream protoInputStream) throws Exception {
        final LongSparseArray longSparseArray = new LongSparseArray();
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    longSparseArray.put(1120986464257L, Integer.valueOf(protoInputStream.readInt(1120986464257L)));
                    break;
                case 2:
                    long start = protoInputStream.start(1146756268034L);
                    longSparseArray.put(1146756268034L, ColorStateList.createFromProto(protoInputStream));
                    protoInputStream.end(start);
                    break;
                case 3:
                    byte[] readBytes = protoInputStream.readBytes(1151051235331L);
                    longSparseArray.put(1151051235331L, BitmapFactory.decodeByteArray(readBytes, 0, readBytes.length));
                    break;
                case 4:
                    longSparseArray.put(1138166333444L, protoInputStream.readString(1138166333444L));
                    break;
                case 5:
                    longSparseArray.put(1151051235333L, protoInputStream.readBytes(1151051235333L));
                    break;
                case 6:
                    longSparseArray.put(1138166333446L, protoInputStream.readString(1138166333446L));
                    break;
                case 7:
                    longSparseArray.put(1138166333447L, protoInputStream.readString(1138166333447L));
                    break;
                case 8:
                    byte[] readBytes2 = protoInputStream.readBytes(1151051235336L);
                    longSparseArray.put(1151051235336L, BitmapFactory.decodeByteArray(readBytes2, 0, readBytes2.length));
                    break;
                default:
                    Log.w(TAG, "Unhandled field while reading Icon proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        return new Function() { // from class: android.widget.RemoteViewsSerializers$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RemoteViewsSerializers.lambda$createIconFromProto$0(LongSparseArray.this, (Resources) obj);
            }
        };
    }

    static /* synthetic */ Icon lambda$createIconFromProto$0(LongSparseArray longSparseArray, Resources resources) {
        Icon createWithAdaptiveBitmapContentUri;
        int intValue = ((Integer) longSparseArray.get(1120986464257L, -1)).intValue();
        ColorStateList colorStateList = (ColorStateList) longSparseArray.get(1146756268034L);
        Bitmap bitmap = (Bitmap) longSparseArray.get(1151051235331L);
        Bitmap bitmap2 = (Bitmap) longSparseArray.get(1151051235336L);
        String str = (String) longSparseArray.get(1138166333444L);
        int identifier = str != null ? resources.getIdentifier(str, null, null) : -1;
        byte[] bArr = (byte[]) longSparseArray.get(1151051235333L);
        String str2 = (String) longSparseArray.get(1138166333446L);
        String str3 = (String) longSparseArray.get(1138166333447L);
        if (bitmap != null) {
            createWithAdaptiveBitmapContentUri = Icon.createWithBitmap(bitmap);
        } else if (bitmap2 != null) {
            createWithAdaptiveBitmapContentUri = Icon.createWithAdaptiveBitmap(bitmap2);
        } else if (identifier != -1) {
            createWithAdaptiveBitmapContentUri = Icon.createWithResource(resources, identifier);
        } else if (bArr != null) {
            createWithAdaptiveBitmapContentUri = Icon.createWithData(bArr, 0, bArr.length);
        } else if (str2 != null) {
            createWithAdaptiveBitmapContentUri = Icon.createWithContentUri(str2);
        } else {
            if (str3 == null) {
                return null;
            }
            createWithAdaptiveBitmapContentUri = Icon.createWithAdaptiveBitmapContentUri(str3);
        }
        if (colorStateList != null) {
            createWithAdaptiveBitmapContentUri.setTintList(colorStateList);
        }
        if (intValue != -1) {
            createWithAdaptiveBitmapContentUri.setTintBlendMode(BlendMode.fromValue(intValue));
        }
        return createWithAdaptiveBitmapContentUri;
    }

    public static void writeCharSequenceToProto(ProtoOutputStream protoOutputStream, CharSequence charSequence) {
        protoOutputStream.write(1138166333441L, charSequence.toString());
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            for (Object obj : spanned.getSpans(0, charSequence.length(), Object.class)) {
                Object underlying = obj instanceof CharacterStyle ? ((CharacterStyle) obj).getUnderlying() : obj;
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1120986464257L, spanned.getSpanStart(obj));
                protoOutputStream.write(1120986464258L, spanned.getSpanEnd(obj));
                protoOutputStream.write(1120986464259L, spanned.getSpanFlags(obj));
                if (underlying instanceof AbsoluteSizeSpan) {
                    long start2 = protoOutputStream.start(2246267895812L);
                    writeAbsoluteSizeSpanToProto(protoOutputStream, (AbsoluteSizeSpan) underlying);
                    protoOutputStream.end(start2);
                } else if (underlying instanceof AccessibilityClickableSpan) {
                    long start3 = protoOutputStream.start(2246267895813L);
                    writeAccessibilityClickableSpanToProto(protoOutputStream, (AccessibilityClickableSpan) underlying);
                    protoOutputStream.end(start3);
                } else if (underlying instanceof AccessibilityReplacementSpan) {
                    long start4 = protoOutputStream.start(2246267895814L);
                    writeAccessibilityReplacementSpanToProto(protoOutputStream, (AccessibilityReplacementSpan) underlying);
                    protoOutputStream.end(start4);
                } else if (underlying instanceof AccessibilityURLSpan) {
                    long start5 = protoOutputStream.start(2246267895815L);
                    writeAccessibilityURLSpanToProto(protoOutputStream, (AccessibilityURLSpan) underlying);
                    protoOutputStream.end(start5);
                } else if (underlying instanceof Annotation) {
                    long start6 = protoOutputStream.start(2246267895817L);
                    writeAnnotationToProto(protoOutputStream, (Annotation) underlying);
                    protoOutputStream.end(start6);
                } else if (underlying instanceof BackgroundColorSpan) {
                    long start7 = protoOutputStream.start(2246267895818L);
                    writeBackgroundColorSpanToProto(protoOutputStream, (BackgroundColorSpan) underlying);
                    protoOutputStream.end(start7);
                } else if (underlying instanceof BulletSpan) {
                    long start8 = protoOutputStream.start(2246267895819L);
                    writeBulletSpanToProto(protoOutputStream, (BulletSpan) underlying);
                    protoOutputStream.end(start8);
                } else if (underlying instanceof EasyEditSpan) {
                    long start9 = protoOutputStream.start(2246267895820L);
                    writeEasyEditSpanToProto(protoOutputStream, (EasyEditSpan) underlying);
                    protoOutputStream.end(start9);
                } else if (underlying instanceof ForegroundColorSpan) {
                    long start10 = protoOutputStream.start(2246267895821L);
                    writeForegroundColorSpanToProto(protoOutputStream, (ForegroundColorSpan) underlying);
                    protoOutputStream.end(start10);
                } else if (Flags.noBreakNoHyphenationSpan() && (underlying instanceof LineBreakConfigSpan)) {
                    long start11 = protoOutputStream.start(2246267895824L);
                    writeLineBreakConfigSpanToProto(protoOutputStream, (LineBreakConfigSpan) underlying);
                    protoOutputStream.end(start11);
                } else if (underlying instanceof LocaleSpan) {
                    long start12 = protoOutputStream.start(2246267895826L);
                    writeLocaleSpanToProto(protoOutputStream, (LocaleSpan) underlying);
                    protoOutputStream.end(start12);
                } else if (underlying instanceof QuoteSpan) {
                    long start13 = protoOutputStream.start(2246267895827L);
                    writeQuoteSpanToProto(protoOutputStream, (QuoteSpan) underlying);
                    protoOutputStream.end(start13);
                } else if (underlying instanceof RelativeSizeSpan) {
                    long start14 = protoOutputStream.start(2246267895828L);
                    writeRelativeSizeSpanToProto(protoOutputStream, (RelativeSizeSpan) underlying);
                    protoOutputStream.end(start14);
                } else if (underlying instanceof ScaleXSpan) {
                    long start15 = protoOutputStream.start(2246267895829L);
                    writeScaleXSpanToProto(protoOutputStream, (ScaleXSpan) underlying);
                    protoOutputStream.end(start15);
                } else if (underlying instanceof SpellCheckSpan) {
                    long start16 = protoOutputStream.start(2246267895830L);
                    writeSpellCheckSpanToProto(protoOutputStream, (SpellCheckSpan) underlying);
                    protoOutputStream.end(start16);
                } else if (underlying instanceof LineBackgroundSpan.Standard) {
                    long start17 = protoOutputStream.start(2246267895823L);
                    writeLineBackgroundSpanStandardToProto(protoOutputStream, (LineBackgroundSpan.Standard) underlying);
                    protoOutputStream.end(start17);
                } else if (underlying instanceof LineHeightSpan.Standard) {
                    long start18 = protoOutputStream.start(2246267895825L);
                    writeLineHeightSpanStandardToProto(protoOutputStream, (LineHeightSpan.Standard) underlying);
                    protoOutputStream.end(start18);
                } else if (underlying instanceof LeadingMarginSpan.Standard) {
                    long start19 = protoOutputStream.start(2246267895822L);
                    writeLeadingMarginSpanStandardToProto(protoOutputStream, (LeadingMarginSpan.Standard) underlying);
                    protoOutputStream.end(start19);
                } else if (underlying instanceof AlignmentSpan.Standard) {
                    long start20 = protoOutputStream.start(2246267895816L);
                    writeAlignmentSpanStandardToProto(protoOutputStream, (AlignmentSpan.Standard) underlying);
                    protoOutputStream.end(start20);
                } else if (underlying instanceof StrikethroughSpan) {
                    long start21 = protoOutputStream.start(2246267895831L);
                    writeStrikethroughSpanToProto(protoOutputStream, (StrikethroughSpan) underlying);
                    protoOutputStream.end(start21);
                } else if (underlying instanceof StyleSpan) {
                    long start22 = protoOutputStream.start(2246267895832L);
                    writeStyleSpanToProto(protoOutputStream, (StyleSpan) underlying);
                    protoOutputStream.end(start22);
                } else if (underlying instanceof SubscriptSpan) {
                    long start23 = protoOutputStream.start(2246267895833L);
                    writeSubscriptSpanToProto(protoOutputStream, (SubscriptSpan) underlying);
                    protoOutputStream.end(start23);
                } else if (underlying instanceof SuggestionRangeSpan) {
                    long start24 = protoOutputStream.start(2246267895835L);
                    writeSuggestionRangeSpanToProto(protoOutputStream, (SuggestionRangeSpan) underlying);
                    protoOutputStream.end(start24);
                } else if (underlying instanceof SuggestionSpan) {
                    long start25 = protoOutputStream.start(2246267895834L);
                    writeSuggestionSpanToProto(protoOutputStream, (SuggestionSpan) underlying);
                    protoOutputStream.end(start25);
                } else if (underlying instanceof SuperscriptSpan) {
                    long start26 = protoOutputStream.start(2246267895836L);
                    writeSuperscriptSpanToProto(protoOutputStream, (SuperscriptSpan) underlying);
                    protoOutputStream.end(start26);
                } else if (underlying instanceof TextAppearanceSpan) {
                    long start27 = protoOutputStream.start(2246267895837L);
                    writeTextAppearanceSpanToProto(protoOutputStream, (TextAppearanceSpan) underlying);
                    protoOutputStream.end(start27);
                } else if (underlying instanceof TtsSpan) {
                    long start28 = protoOutputStream.start(2246267895838L);
                    writeTtsSpanToProto(protoOutputStream, (TtsSpan) underlying);
                    protoOutputStream.end(start28);
                } else if (underlying instanceof TypefaceSpan) {
                    long start29 = protoOutputStream.start(2246267895839L);
                    writeTypefaceSpanToProto(protoOutputStream, (TypefaceSpan) underlying);
                    protoOutputStream.end(start29);
                } else if (underlying instanceof URLSpan) {
                    long start30 = protoOutputStream.start(2246267895841L);
                    writeURLSpanToProto(protoOutputStream, (URLSpan) underlying);
                    protoOutputStream.end(start30);
                } else if (underlying instanceof UnderlineSpan) {
                    long start31 = protoOutputStream.start(2246267895840L);
                    writeUnderlineSpanToProto(protoOutputStream, (UnderlineSpan) underlying);
                    protoOutputStream.end(start31);
                }
                protoOutputStream.end(start);
            }
        }
    }

    public static CharSequence createCharSequenceFromProto(ProtoInputStream protoInputStream) throws Exception {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        boolean z = false;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                spannableStringBuilder.append((CharSequence) protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                long start = protoInputStream.start(2246267895810L);
                createSpanFromProto(protoInputStream, spannableStringBuilder);
                protoInputStream.end(start);
                z = true;
            } else {
                Log.w(TAG, "Unhandled field while reading CharSequence proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return z ? spannableStringBuilder : spannableStringBuilder.toString();
    }

    private static void createSpanFromProto(ProtoInputStream protoInputStream, SpannableStringBuilder spannableStringBuilder) throws Exception {
        Object createAbsoluteSizeSpanFromProto;
        int i = 0;
        int i2 = 0;
        Object obj = null;
        int i3 = 0;
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    i = protoInputStream.readInt(1120986464257L);
                    continue;
                case 2:
                    i3 = protoInputStream.readInt(1120986464258L);
                    continue;
                case 3:
                    i2 = protoInputStream.readInt(1120986464259L);
                    continue;
                case 4:
                    long start = protoInputStream.start(2246267895812L);
                    createAbsoluteSizeSpanFromProto = createAbsoluteSizeSpanFromProto(protoInputStream);
                    protoInputStream.end(start);
                    break;
                case 5:
                    long start2 = protoInputStream.start(2246267895813L);
                    createAbsoluteSizeSpanFromProto = createAccessibilityClickableSpanFromProto(protoInputStream);
                    protoInputStream.end(start2);
                    break;
                case 6:
                    long start3 = protoInputStream.start(2246267895814L);
                    createAbsoluteSizeSpanFromProto = createAccessibilityReplacementSpanFromProto(protoInputStream);
                    protoInputStream.end(start3);
                    break;
                case 7:
                    long start4 = protoInputStream.start(2246267895815L);
                    createAbsoluteSizeSpanFromProto = createAccessibilityURLSpanFromProto(protoInputStream);
                    protoInputStream.end(start4);
                    break;
                case 8:
                    long start5 = protoInputStream.start(2246267895816L);
                    createAbsoluteSizeSpanFromProto = createAlignmentSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(start5);
                    break;
                case 9:
                    long start6 = protoInputStream.start(2246267895817L);
                    createAbsoluteSizeSpanFromProto = createAnnotationFromProto(protoInputStream);
                    protoInputStream.end(start6);
                    break;
                case 10:
                    long start7 = protoInputStream.start(2246267895818L);
                    createAbsoluteSizeSpanFromProto = createBackgroundColorSpanFromProto(protoInputStream);
                    protoInputStream.end(start7);
                    break;
                case 11:
                    long start8 = protoInputStream.start(2246267895819L);
                    createAbsoluteSizeSpanFromProto = createBulletSpanFromProto(protoInputStream);
                    protoInputStream.end(start8);
                    break;
                case 12:
                    long start9 = protoInputStream.start(2246267895820L);
                    createAbsoluteSizeSpanFromProto = createEasyEditSpanFromProto(protoInputStream);
                    protoInputStream.end(start9);
                    break;
                case 13:
                    long start10 = protoInputStream.start(2246267895821L);
                    createAbsoluteSizeSpanFromProto = createForegroundColorSpanFromProto(protoInputStream);
                    protoInputStream.end(start10);
                    break;
                case 14:
                    long start11 = protoInputStream.start(2246267895822L);
                    createAbsoluteSizeSpanFromProto = createLeadingMarginSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(start11);
                    break;
                case 15:
                    long start12 = protoInputStream.start(2246267895823L);
                    createAbsoluteSizeSpanFromProto = createLineBackgroundSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(start12);
                    break;
                case 16:
                    if (!Flags.noBreakNoHyphenationSpan()) {
                        break;
                    } else {
                        long start13 = protoInputStream.start(2246267895824L);
                        createAbsoluteSizeSpanFromProto = createLineBreakConfigSpanFromProto(protoInputStream);
                        protoInputStream.end(start13);
                        break;
                    }
                case 17:
                    long start14 = protoInputStream.start(2246267895825L);
                    createAbsoluteSizeSpanFromProto = createLineHeightSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(start14);
                    break;
                case 18:
                    long start15 = protoInputStream.start(2246267895826L);
                    createAbsoluteSizeSpanFromProto = createLocaleSpanFromProto(protoInputStream);
                    protoInputStream.end(start15);
                    break;
                case 19:
                    long start16 = protoInputStream.start(2246267895827L);
                    createAbsoluteSizeSpanFromProto = createQuoteSpanFromProto(protoInputStream);
                    protoInputStream.end(start16);
                    break;
                case 20:
                    long start17 = protoInputStream.start(2246267895828L);
                    createAbsoluteSizeSpanFromProto = createRelativeSizeSpanFromProto(protoInputStream);
                    protoInputStream.end(start17);
                    break;
                case 21:
                    long start18 = protoInputStream.start(2246267895829L);
                    createAbsoluteSizeSpanFromProto = createScaleXSpanFromProto(protoInputStream);
                    protoInputStream.end(start18);
                    break;
                case 22:
                    long start19 = protoInputStream.start(2246267895830L);
                    createAbsoluteSizeSpanFromProto = createSpellCheckSpanFromProto(protoInputStream);
                    protoInputStream.end(start19);
                    break;
                case 23:
                    long start20 = protoInputStream.start(2246267895831L);
                    createAbsoluteSizeSpanFromProto = createStrikethroughSpanFromProto(protoInputStream);
                    protoInputStream.end(start20);
                    break;
                case 24:
                    long start21 = protoInputStream.start(2246267895832L);
                    createAbsoluteSizeSpanFromProto = createStyleSpanFromProto(protoInputStream);
                    protoInputStream.end(start21);
                    break;
                case 25:
                    long start22 = protoInputStream.start(2246267895833L);
                    createAbsoluteSizeSpanFromProto = createSubscriptSpanFromProto(protoInputStream);
                    protoInputStream.end(start22);
                    break;
                case 26:
                    long start23 = protoInputStream.start(2246267895834L);
                    createAbsoluteSizeSpanFromProto = createSuggestionSpanFromProto(protoInputStream);
                    protoInputStream.end(start23);
                    break;
                case 27:
                    long start24 = protoInputStream.start(2246267895835L);
                    createAbsoluteSizeSpanFromProto = createSuggestionRangeSpanFromProto(protoInputStream);
                    protoInputStream.end(start24);
                    break;
                case 28:
                    long start25 = protoInputStream.start(2246267895836L);
                    createAbsoluteSizeSpanFromProto = createSuperscriptSpanFromProto(protoInputStream);
                    protoInputStream.end(start25);
                    break;
                case 29:
                    long start26 = protoInputStream.start(2246267895837L);
                    createAbsoluteSizeSpanFromProto = createTextAppearanceSpanFromProto(protoInputStream);
                    protoInputStream.end(start26);
                    break;
                case 30:
                    long start27 = protoInputStream.start(2246267895838L);
                    createAbsoluteSizeSpanFromProto = createTtsSpanFromProto(protoInputStream);
                    protoInputStream.end(start27);
                    break;
                case 31:
                    long start28 = protoInputStream.start(2246267895839L);
                    createAbsoluteSizeSpanFromProto = createTypefaceSpanFromProto(protoInputStream);
                    protoInputStream.end(start28);
                    break;
                case 32:
                    long start29 = protoInputStream.start(2246267895840L);
                    createAbsoluteSizeSpanFromProto = createUnderlineSpanFromProto(protoInputStream);
                    protoInputStream.end(start29);
                    break;
                case 33:
                    long start30 = protoInputStream.start(2246267895841L);
                    createAbsoluteSizeSpanFromProto = createURLSpanFromProto(protoInputStream);
                    protoInputStream.end(start30);
                    break;
                default:
                    Log.w(TAG, "Unhandled field while reading CharSequence proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    continue;
            }
            obj = createAbsoluteSizeSpanFromProto;
        }
        if (obj == null) {
            return;
        }
        spannableStringBuilder.setSpan(obj, i, i3, i2);
    }

    public static AbsoluteSizeSpan createAbsoluteSizeSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        boolean z = false;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                z = protoInputStream.readBoolean(1133871366146L);
            } else {
                Log.w("AbsoluteSizeSpan", "Unhandled field while reading AbsoluteSizeSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AbsoluteSizeSpan(i, z);
    }

    public static void writeAbsoluteSizeSpanToProto(ProtoOutputStream protoOutputStream, AbsoluteSizeSpan absoluteSizeSpan) {
        protoOutputStream.write(1120986464257L, absoluteSizeSpan.getSize());
        protoOutputStream.write(1133871366146L, absoluteSizeSpan.getDip());
    }

    public static AccessibilityClickableSpan createAccessibilityClickableSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else {
                Log.w("AccessibilityClickable", "Unhandled field while reading AccessibilityClickableSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AccessibilityClickableSpan(i);
    }

    public static void writeAccessibilityClickableSpanToProto(ProtoOutputStream protoOutputStream, AccessibilityClickableSpan accessibilityClickableSpan) {
        protoOutputStream.write(1120986464257L, accessibilityClickableSpan.getOriginalClickableSpanId());
    }

    public static AccessibilityReplacementSpan createAccessibilityReplacementSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        CharSequence charSequence = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                long start = protoInputStream.start(1146756268033L);
                CharSequence createCharSequenceFromProto = createCharSequenceFromProto(protoInputStream);
                protoInputStream.end(start);
                charSequence = createCharSequenceFromProto;
            } else {
                Log.w("AccessibilityReplacemen", "Unhandled field while reading AccessibilityReplacementSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AccessibilityReplacementSpan(charSequence);
    }

    public static void writeAccessibilityReplacementSpanToProto(ProtoOutputStream protoOutputStream, AccessibilityReplacementSpan accessibilityReplacementSpan) {
        long start = protoOutputStream.start(1146756268033L);
        CharSequence contentDescription = accessibilityReplacementSpan.getContentDescription();
        if (contentDescription != null) {
            writeCharSequenceToProto(protoOutputStream, contentDescription);
        }
        protoOutputStream.end(start);
    }

    public static AccessibilityURLSpan createAccessibilityURLSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("AccessibilityURLSpan", "Unhandled field while reading AccessibilityURLSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AccessibilityURLSpan(new URLSpan(str));
    }

    public static void writeAccessibilityURLSpanToProto(ProtoOutputStream protoOutputStream, AccessibilityURLSpan accessibilityURLSpan) {
        protoOutputStream.write(1138166333441L, accessibilityURLSpan.getURL());
    }

    public static AlignmentSpan.Standard createAlignmentSpanStandardFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("AlignmentSpan", "Unhandled field while reading AlignmentSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AlignmentSpan.Standard(Layout.Alignment.valueOf(str));
    }

    public static void writeAlignmentSpanStandardToProto(ProtoOutputStream protoOutputStream, AlignmentSpan.Standard standard) {
        protoOutputStream.write(1138166333441L, standard.getAlignment().name());
    }

    public static Annotation createAnnotationFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        String str2 = null;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                str2 = protoInputStream.readString(1138166333442L);
            } else {
                Log.w("Annotation", "Unhandled field while reading Annotation proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new Annotation(str, str2);
    }

    public static void writeAnnotationToProto(ProtoOutputStream protoOutputStream, Annotation annotation) {
        protoOutputStream.write(1138166333441L, annotation.getKey());
        protoOutputStream.write(1138166333442L, annotation.getValue());
    }

    public static BackgroundColorSpan createBackgroundColorSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else {
                Log.w("BackgroundColorSpan", "Unhandled field while reading BackgroundColorSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new BackgroundColorSpan(i);
    }

    public static void writeBackgroundColorSpanToProto(ProtoOutputStream protoOutputStream, BackgroundColorSpan backgroundColorSpan) {
        protoOutputStream.write(1120986464257L, backgroundColorSpan.getBackgroundColor());
    }

    public static BulletSpan createBulletSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                i2 = protoInputStream.readInt(1120986464258L);
            } else if (fieldNumber == 3) {
                i3 = protoInputStream.readInt(1120986464259L);
            } else if (fieldNumber == 4) {
                z = protoInputStream.readBoolean(1133871366148L);
            } else {
                Log.w("BulletSpan", "Unhandled field while reading BulletSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new BulletSpan(i, i2, z, i3);
    }

    public static void writeBulletSpanToProto(ProtoOutputStream protoOutputStream, BulletSpan bulletSpan) {
        protoOutputStream.write(1120986464259L, bulletSpan.getBulletRadius());
        protoOutputStream.write(1120986464258L, bulletSpan.getColor());
        protoOutputStream.write(1120986464257L, bulletSpan.getGapWidth());
        protoOutputStream.write(1133871366148L, bulletSpan.getWantColor());
    }

    public static EasyEditSpan createEasyEditSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        return new EasyEditSpan();
    }

    public static ForegroundColorSpan createForegroundColorSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else {
                Log.w("ForegroundColorSpan", "Unhandled field while reading ForegroundColorSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new ForegroundColorSpan(i);
    }

    public static LeadingMarginSpan.Standard createLeadingMarginSpanStandardFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        int i2 = 0;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                i2 = protoInputStream.readInt(1120986464258L);
            } else {
                Log.w("LeadingMarginSpan", "Unhandled field while reading LeadingMarginSpanproto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new LeadingMarginSpan.Standard(i, i2);
    }

    public static void writeLeadingMarginSpanStandardToProto(ProtoOutputStream protoOutputStream, LeadingMarginSpan.Standard standard) {
        protoOutputStream.write(1120986464257L, standard.getLeadingMargin(true));
        protoOutputStream.write(1120986464258L, standard.getLeadingMargin(false));
    }

    public static void writeForegroundColorSpanToProto(ProtoOutputStream protoOutputStream, ForegroundColorSpan foregroundColorSpan) {
        protoOutputStream.write(1120986464257L, foregroundColorSpan.getForegroundColor());
    }

    public static LineBackgroundSpan.Standard createLineBackgroundSpanStandardFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else {
                Log.w("LineBackgroundSpan", "Unhandled field while reading LineBackgroundSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new LineBackgroundSpan.Standard(i);
    }

    public static void writeLineBackgroundSpanStandardToProto(ProtoOutputStream protoOutputStream, LineBackgroundSpan.Standard standard) {
        protoOutputStream.write(1120986464257L, standard.getColor());
    }

    public static LineBreakConfigSpan createLineBreakConfigSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                i2 = protoInputStream.readInt(1120986464258L);
            } else if (fieldNumber == 3) {
                i3 = protoInputStream.readInt(1120986464259L);
            } else {
                Log.w("LineBreakConfigSpan", "Unhandled field while reading LineBreakConfigSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new LineBreakConfigSpan(new LineBreakConfig.Builder().setLineBreakStyle(i).setLineBreakWordStyle(i2).setHyphenation(i3).build());
    }

    public static void writeLineBreakConfigSpanToProto(ProtoOutputStream protoOutputStream, LineBreakConfigSpan lineBreakConfigSpan) {
        protoOutputStream.write(1120986464257L, lineBreakConfigSpan.getLineBreakConfig().getLineBreakStyle());
        protoOutputStream.write(1120986464258L, lineBreakConfigSpan.getLineBreakConfig().getLineBreakWordStyle());
        protoOutputStream.write(1120986464259L, lineBreakConfigSpan.getLineBreakConfig().getHyphenation());
    }

    public static LineHeightSpan.Standard createLineHeightSpanStandardFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else {
                Log.w("LineHeightSpan.Standard", "Unhandled field while reading LineHeightSpan.Standard proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new LineHeightSpan.Standard(i);
    }

    public static void writeLineHeightSpanStandardToProto(ProtoOutputStream protoOutputStream, LineHeightSpan.Standard standard) {
        protoOutputStream.write(1120986464257L, standard.getHeight());
    }

    public static LocaleSpan createLocaleSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("LocaleSpan", "Unhandled field while reading LocaleSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new LocaleSpan(LocaleList.forLanguageTags(str));
    }

    public static void writeLocaleSpanToProto(ProtoOutputStream protoOutputStream, LocaleSpan localeSpan) {
        protoOutputStream.write(1138166333441L, localeSpan.getLocales().toLanguageTags());
    }

    public static QuoteSpan createQuoteSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                i2 = protoInputStream.readInt(1120986464258L);
            } else if (fieldNumber == 3) {
                i3 = protoInputStream.readInt(1120986464259L);
            } else {
                Log.w("QuoteSpan", "Unhandled field while reading QuoteSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new QuoteSpan(i, i2, i3);
    }

    public static void writeQuoteSpanToProto(ProtoOutputStream protoOutputStream, QuoteSpan quoteSpan) {
        protoOutputStream.write(1120986464257L, quoteSpan.getColor());
        protoOutputStream.write(1120986464258L, quoteSpan.getStripeWidth());
        protoOutputStream.write(1120986464259L, quoteSpan.getGapWidth());
    }

    public static RelativeSizeSpan createRelativeSizeSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        float f = 0.0f;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                f = protoInputStream.readFloat(1108101562369L);
            } else {
                Log.w("RelativeSizeSpan", "Unhandled field while reading RelativeSizeSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new RelativeSizeSpan(f);
    }

    public static void writeRelativeSizeSpanToProto(ProtoOutputStream protoOutputStream, RelativeSizeSpan relativeSizeSpan) {
        protoOutputStream.write(1108101562369L, relativeSizeSpan.getSizeChange());
    }

    public static ScaleXSpan createScaleXSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        float f = 0.0f;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                f = protoInputStream.readFloat(1108101562369L);
            } else {
                Log.w("ScaleXSpan", "Unhandled field while reading ScaleXSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new ScaleXSpan(f);
    }

    public static void writeScaleXSpanToProto(ProtoOutputStream protoOutputStream, ScaleXSpan scaleXSpan) {
        protoOutputStream.write(1108101562369L, scaleXSpan.getScaleX());
    }

    public static SpellCheckSpan createSpellCheckSpanFromProto(ProtoInputStream protoInputStream) {
        return new SpellCheckSpan();
    }

    public static StrikethroughSpan createStrikethroughSpanFromProto(ProtoInputStream protoInputStream) {
        return new StrikethroughSpan();
    }

    public static StyleSpan createStyleSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        int i2 = 0;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                i2 = protoInputStream.readInt(1120986464258L);
            } else {
                Log.w("StyleSpan", "Unhandled field while reading StyleSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new StyleSpan(i, i2);
    }

    public static void writeStyleSpanToProto(ProtoOutputStream protoOutputStream, StyleSpan styleSpan) {
        protoOutputStream.write(1120986464257L, styleSpan.getStyle());
        protoOutputStream.write(1120986464258L, styleSpan.getFontWeightAdjustment());
    }

    public static SubscriptSpan createSubscriptSpanFromProto(ProtoInputStream protoInputStream) {
        return new SubscriptSpan();
    }

    public static SuggestionRangeSpan createSuggestionRangeSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        int i = 0;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                i = protoInputStream.readInt(1120986464257L);
            } else {
                Log.w("SuggestionRangeSpan", "Unhandled field while reading SuggestionRangeSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        SuggestionRangeSpan suggestionRangeSpan = new SuggestionRangeSpan();
        suggestionRangeSpan.setBackgroundColor(i);
        return suggestionRangeSpan;
    }

    public static void writeSuggestionRangeSpanToProto(ProtoOutputStream protoOutputStream, SuggestionRangeSpan suggestionRangeSpan) {
        protoOutputStream.write(1120986464257L, suggestionRangeSpan.getBackgroundColor());
    }

    public static SuggestionSpan createSuggestionSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        String str = null;
        String str2 = null;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    arrayList.add(protoInputStream.readString(2237677961217L));
                    break;
                case 2:
                    i = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    str = protoInputStream.readString(1138166333443L);
                    break;
                case 4:
                    str2 = protoInputStream.readString(1138166333444L);
                    break;
                case 5:
                    i2 = protoInputStream.readInt(1120986464261L);
                    break;
                case 6:
                    i3 = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    f = protoInputStream.readFloat(1108101562375L);
                    break;
                case 8:
                    i4 = protoInputStream.readInt(1120986464264L);
                    break;
                case 9:
                    f2 = protoInputStream.readFloat(1108101562377L);
                    break;
                case 10:
                    i5 = protoInputStream.readInt(1120986464266L);
                    break;
                case 11:
                    f3 = protoInputStream.readFloat(1108101562379L);
                    break;
                case 12:
                    i6 = protoInputStream.readInt(1120986464268L);
                    break;
                case 13:
                    f4 = protoInputStream.readFloat(1108101562381L);
                    break;
                default:
                    Log.w("SuggestionSpan", "Unhandled field while reading SuggestionSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return new SuggestionSpan(strArr, i, str, str2, i2, i3, f, i4, f2, i5, f3, i6, f4);
    }

    public static void writeSuggestionSpanToProto(ProtoOutputStream protoOutputStream, SuggestionSpan suggestionSpan) {
        for (String str : suggestionSpan.getSuggestions()) {
            protoOutputStream.write(2237677961217L, str);
        }
        protoOutputStream.write(1120986464258L, suggestionSpan.getFlags());
        protoOutputStream.write(1138166333443L, suggestionSpan.getLocale());
        if (suggestionSpan.getLocaleObject() != null) {
            protoOutputStream.write(1138166333444L, suggestionSpan.getLocaleObject().toLanguageTag());
        }
        protoOutputStream.write(1120986464261L, suggestionSpan.hashCode());
        protoOutputStream.write(1120986464262L, suggestionSpan.getEasyCorrectUnderlineColor());
        protoOutputStream.write(1108101562375L, suggestionSpan.getEasyCorrectUnderlineThickness());
        protoOutputStream.write(1120986464264L, suggestionSpan.getMisspelledUnderlineColor());
        protoOutputStream.write(1108101562377L, suggestionSpan.getMisspelledUnderlineThickness());
        protoOutputStream.write(1120986464266L, suggestionSpan.getAutoCorrectionUnderlineColor());
        protoOutputStream.write(1108101562379L, suggestionSpan.getAutoCorrectionUnderlineThickness());
        protoOutputStream.write(1120986464268L, suggestionSpan.getGrammarErrorUnderlineColor());
        protoOutputStream.write(1108101562381L, suggestionSpan.getGrammarErrorUnderlineThickness());
    }

    public static SuperscriptSpan createSuperscriptSpanFromProto(ProtoInputStream protoInputStream) {
        return new SuperscriptSpan();
    }

    public static TextAppearanceSpan createTextAppearanceSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        ColorStateList colorStateList = null;
        ColorStateList colorStateList2 = null;
        LocaleList localeList = null;
        String str2 = null;
        String str3 = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    break;
                case 2:
                    i = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    i2 = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    long start = protoInputStream.start(1146756268036L);
                    colorStateList = ColorStateList.createFromProto(protoInputStream);
                    protoInputStream.end(start);
                    break;
                case 5:
                    long start2 = protoInputStream.start(1146756268037L);
                    colorStateList2 = ColorStateList.createFromProto(protoInputStream);
                    protoInputStream.end(start2);
                    break;
                case 6:
                default:
                    Log.w("TextAppearanceSpan", "Unhandled field while reading TextAppearanceSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    break;
                case 7:
                    i3 = protoInputStream.readInt(1120986464263L);
                    break;
                case 8:
                    localeList = LocaleList.forLanguageTags(protoInputStream.readString(1138166333448L));
                    break;
                case 9:
                    f = protoInputStream.readFloat(1108101562377L);
                    break;
                case 10:
                    f2 = protoInputStream.readFloat(1108101562378L);
                    break;
                case 11:
                    f3 = protoInputStream.readFloat(1108101562379L);
                    break;
                case 12:
                    i4 = protoInputStream.readInt(1120986464268L);
                    break;
                case 13:
                    z = protoInputStream.readBoolean(1133871366157L);
                    break;
                case 14:
                    z2 = protoInputStream.readBoolean(1133871366158L);
                    break;
                case 15:
                    z3 = protoInputStream.readBoolean(1133871366159L);
                    break;
                case 16:
                    f4 = protoInputStream.readFloat(1108101562384L);
                    break;
                case 17:
                    str2 = protoInputStream.readString(1138166333457L);
                    break;
                case 18:
                    str3 = protoInputStream.readString(1138166333458L);
                    break;
            }
        }
        return new TextAppearanceSpan(str, i, i2, colorStateList, colorStateList2, null, i3, localeList, f, f2, f3, i4, z, z2, z3, f4, str2, str3);
    }

    public static void writeTextAppearanceSpanToProto(ProtoOutputStream protoOutputStream, TextAppearanceSpan textAppearanceSpan) {
        protoOutputStream.write(1138166333441L, textAppearanceSpan.getFamily());
        protoOutputStream.write(1120986464258L, textAppearanceSpan.getTextStyle());
        protoOutputStream.write(1120986464259L, textAppearanceSpan.getTextSize());
        protoOutputStream.write(1120986464263L, textAppearanceSpan.getTextFontWeight());
        if (textAppearanceSpan.getTextLocales() != null) {
            protoOutputStream.write(1138166333448L, textAppearanceSpan.getTextLocales().toLanguageTags());
        }
        protoOutputStream.write(1108101562377L, textAppearanceSpan.getShadowRadius());
        protoOutputStream.write(1108101562378L, textAppearanceSpan.getShadowDx());
        protoOutputStream.write(1108101562379L, textAppearanceSpan.getShadowDy());
        protoOutputStream.write(1120986464268L, textAppearanceSpan.getShadowColor());
        protoOutputStream.write(1133871366157L, textAppearanceSpan.hasElegantTextHeight());
        protoOutputStream.write(1133871366158L, textAppearanceSpan.isElegantTextHeight());
        protoOutputStream.write(1133871366159L, textAppearanceSpan.hasLetterSpacing());
        protoOutputStream.write(1108101562384L, textAppearanceSpan.getLetterSpacing());
        protoOutputStream.write(1138166333457L, textAppearanceSpan.getFontFeatureSettings());
        protoOutputStream.write(1138166333458L, textAppearanceSpan.getFontVariationSettings());
        if (textAppearanceSpan.getTextColor() != null) {
            long start = protoOutputStream.start(1146756268036L);
            textAppearanceSpan.getTextColor().writeToProto(protoOutputStream);
            protoOutputStream.end(start);
        }
        if (textAppearanceSpan.getLinkTextColor() != null) {
            long start2 = protoOutputStream.start(1146756268037L);
            textAppearanceSpan.getLinkTextColor().writeToProto(protoOutputStream);
            protoOutputStream.end(start2);
        }
    }

    public static TtsSpan createTtsSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        PersistableBundle persistableBundle = null;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                persistableBundle = PersistableBundle.readFromStream(new ByteArrayInputStream(protoInputStream.readString(1138166333442L).getBytes()));
            } else {
                Log.w("TtsSpan", "Unhandled field while reading TtsSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new TtsSpan(str, persistableBundle);
    }

    public static void writeTtsSpanToProto(ProtoOutputStream protoOutputStream, TtsSpan ttsSpan) {
        protoOutputStream.write(1138166333441L, ttsSpan.getType());
        if (ttsSpan.getArgs() != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ttsSpan.getArgs().writeToStream(byteArrayOutputStream);
                protoOutputStream.write(1138166333442L, byteArrayOutputStream.toString(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static TypefaceSpan createTypefaceSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("TypefaceSpan", "Unhandled field while reading TypefaceSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new TypefaceSpan(str);
    }

    public static void writeTypefaceSpanToProto(ProtoOutputStream protoOutputStream, TypefaceSpan typefaceSpan) {
        protoOutputStream.write(1138166333441L, typefaceSpan.getFamily());
    }

    public static URLSpan createURLSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String str = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("URLSpan", "Unhandled field while reading URLSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new URLSpan(str);
    }

    public static void writeURLSpanToProto(ProtoOutputStream protoOutputStream, URLSpan uRLSpan) {
        protoOutputStream.write(1138166333441L, uRLSpan.getURL());
    }

    public static UnderlineSpan createUnderlineSpanFromProto(ProtoInputStream protoInputStream) {
        return new UnderlineSpan();
    }
}
