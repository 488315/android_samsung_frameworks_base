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
            long jStart = protoOutputStream.start(1146756268034L);
            icon.getTintList().writeToProto(protoOutputStream);
            protoOutputStream.end(jStart);
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
                    long jStart = protoInputStream.start(1146756268034L);
                    longSparseArray.put(1146756268034L, ColorStateList.createFromProto(protoInputStream));
                    protoInputStream.end(jStart);
                    break;
                case 3:
                    byte[] bytes = protoInputStream.readBytes(1151051235331L);
                    longSparseArray.put(1151051235331L, BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
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
                    byte[] bytes2 = protoInputStream.readBytes(1151051235336L);
                    longSparseArray.put(1151051235336L, BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length));
                    break;
                default:
                    Log.w(TAG, "Unhandled field while reading Icon proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    break;
            }
        }
        return new Function() { // from class: android.widget.RemoteViewsSerializers$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RemoteViewsSerializers.lambda$createIconFromProto$0(longSparseArray, (Resources) obj);
            }
        };
    }

    static /* synthetic */ Icon lambda$createIconFromProto$0(LongSparseArray longSparseArray, Resources resources) {
        Icon iconCreateWithAdaptiveBitmapContentUri;
        int iIntValue = ((Integer) longSparseArray.get(1120986464257L, -1)).intValue();
        ColorStateList colorStateList = (ColorStateList) longSparseArray.get(1146756268034L);
        Bitmap bitmap = (Bitmap) longSparseArray.get(1151051235331L);
        Bitmap bitmap2 = (Bitmap) longSparseArray.get(1151051235336L);
        String str = (String) longSparseArray.get(1138166333444L);
        int identifier = str != null ? resources.getIdentifier(str, null, null) : -1;
        byte[] bArr = (byte[]) longSparseArray.get(1151051235333L);
        String str2 = (String) longSparseArray.get(1138166333446L);
        String str3 = (String) longSparseArray.get(1138166333447L);
        if (bitmap != null) {
            iconCreateWithAdaptiveBitmapContentUri = Icon.createWithBitmap(bitmap);
        } else if (bitmap2 != null) {
            iconCreateWithAdaptiveBitmapContentUri = Icon.createWithAdaptiveBitmap(bitmap2);
        } else if (identifier != -1) {
            iconCreateWithAdaptiveBitmapContentUri = Icon.createWithResource(resources, identifier);
        } else if (bArr != null) {
            iconCreateWithAdaptiveBitmapContentUri = Icon.createWithData(bArr, 0, bArr.length);
        } else if (str2 != null) {
            iconCreateWithAdaptiveBitmapContentUri = Icon.createWithContentUri(str2);
        } else {
            if (str3 == null) {
                return null;
            }
            iconCreateWithAdaptiveBitmapContentUri = Icon.createWithAdaptiveBitmapContentUri(str3);
        }
        if (colorStateList != null) {
            iconCreateWithAdaptiveBitmapContentUri.setTintList(colorStateList);
        }
        if (iIntValue != -1) {
            iconCreateWithAdaptiveBitmapContentUri.setTintBlendMode(BlendMode.fromValue(iIntValue));
        }
        return iconCreateWithAdaptiveBitmapContentUri;
    }

    public static void writeCharSequenceToProto(ProtoOutputStream protoOutputStream, CharSequence charSequence) {
        protoOutputStream.write(1138166333441L, charSequence.toString());
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            for (Object obj : spanned.getSpans(0, charSequence.length(), Object.class)) {
                Object underlying = obj instanceof CharacterStyle ? ((CharacterStyle) obj).getUnderlying() : obj;
                long jStart = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1120986464257L, spanned.getSpanStart(obj));
                protoOutputStream.write(1120986464258L, spanned.getSpanEnd(obj));
                protoOutputStream.write(1120986464259L, spanned.getSpanFlags(obj));
                if (underlying instanceof AbsoluteSizeSpan) {
                    long jStart2 = protoOutputStream.start(2246267895812L);
                    writeAbsoluteSizeSpanToProto(protoOutputStream, (AbsoluteSizeSpan) underlying);
                    protoOutputStream.end(jStart2);
                } else if (underlying instanceof AccessibilityClickableSpan) {
                    long jStart3 = protoOutputStream.start(2246267895813L);
                    writeAccessibilityClickableSpanToProto(protoOutputStream, (AccessibilityClickableSpan) underlying);
                    protoOutputStream.end(jStart3);
                } else if (underlying instanceof AccessibilityReplacementSpan) {
                    long jStart4 = protoOutputStream.start(2246267895814L);
                    writeAccessibilityReplacementSpanToProto(protoOutputStream, (AccessibilityReplacementSpan) underlying);
                    protoOutputStream.end(jStart4);
                } else if (underlying instanceof AccessibilityURLSpan) {
                    long jStart5 = protoOutputStream.start(2246267895815L);
                    writeAccessibilityURLSpanToProto(protoOutputStream, (AccessibilityURLSpan) underlying);
                    protoOutputStream.end(jStart5);
                } else if (underlying instanceof Annotation) {
                    long jStart6 = protoOutputStream.start(2246267895817L);
                    writeAnnotationToProto(protoOutputStream, (Annotation) underlying);
                    protoOutputStream.end(jStart6);
                } else if (underlying instanceof BackgroundColorSpan) {
                    long jStart7 = protoOutputStream.start(2246267895818L);
                    writeBackgroundColorSpanToProto(protoOutputStream, (BackgroundColorSpan) underlying);
                    protoOutputStream.end(jStart7);
                } else if (underlying instanceof BulletSpan) {
                    long jStart8 = protoOutputStream.start(2246267895819L);
                    writeBulletSpanToProto(protoOutputStream, (BulletSpan) underlying);
                    protoOutputStream.end(jStart8);
                } else if (underlying instanceof EasyEditSpan) {
                    long jStart9 = protoOutputStream.start(2246267895820L);
                    writeEasyEditSpanToProto(protoOutputStream, (EasyEditSpan) underlying);
                    protoOutputStream.end(jStart9);
                } else if (underlying instanceof ForegroundColorSpan) {
                    long jStart10 = protoOutputStream.start(2246267895821L);
                    writeForegroundColorSpanToProto(protoOutputStream, (ForegroundColorSpan) underlying);
                    protoOutputStream.end(jStart10);
                } else if (Flags.noBreakNoHyphenationSpan() && (underlying instanceof LineBreakConfigSpan)) {
                    long jStart11 = protoOutputStream.start(2246267895824L);
                    writeLineBreakConfigSpanToProto(protoOutputStream, (LineBreakConfigSpan) underlying);
                    protoOutputStream.end(jStart11);
                } else if (underlying instanceof LocaleSpan) {
                    long jStart12 = protoOutputStream.start(2246267895826L);
                    writeLocaleSpanToProto(protoOutputStream, (LocaleSpan) underlying);
                    protoOutputStream.end(jStart12);
                } else if (underlying instanceof QuoteSpan) {
                    long jStart13 = protoOutputStream.start(2246267895827L);
                    writeQuoteSpanToProto(protoOutputStream, (QuoteSpan) underlying);
                    protoOutputStream.end(jStart13);
                } else if (underlying instanceof RelativeSizeSpan) {
                    long jStart14 = protoOutputStream.start(2246267895828L);
                    writeRelativeSizeSpanToProto(protoOutputStream, (RelativeSizeSpan) underlying);
                    protoOutputStream.end(jStart14);
                } else if (underlying instanceof ScaleXSpan) {
                    long jStart15 = protoOutputStream.start(2246267895829L);
                    writeScaleXSpanToProto(protoOutputStream, (ScaleXSpan) underlying);
                    protoOutputStream.end(jStart15);
                } else if (underlying instanceof SpellCheckSpan) {
                    long jStart16 = protoOutputStream.start(2246267895830L);
                    writeSpellCheckSpanToProto(protoOutputStream, (SpellCheckSpan) underlying);
                    protoOutputStream.end(jStart16);
                } else if (underlying instanceof LineBackgroundSpan.Standard) {
                    long jStart17 = protoOutputStream.start(2246267895823L);
                    writeLineBackgroundSpanStandardToProto(protoOutputStream, (LineBackgroundSpan.Standard) underlying);
                    protoOutputStream.end(jStart17);
                } else if (underlying instanceof LineHeightSpan.Standard) {
                    long jStart18 = protoOutputStream.start(2246267895825L);
                    writeLineHeightSpanStandardToProto(protoOutputStream, (LineHeightSpan.Standard) underlying);
                    protoOutputStream.end(jStart18);
                } else if (underlying instanceof LeadingMarginSpan.Standard) {
                    long jStart19 = protoOutputStream.start(2246267895822L);
                    writeLeadingMarginSpanStandardToProto(protoOutputStream, (LeadingMarginSpan.Standard) underlying);
                    protoOutputStream.end(jStart19);
                } else if (underlying instanceof AlignmentSpan.Standard) {
                    long jStart20 = protoOutputStream.start(2246267895816L);
                    writeAlignmentSpanStandardToProto(protoOutputStream, (AlignmentSpan.Standard) underlying);
                    protoOutputStream.end(jStart20);
                } else if (underlying instanceof StrikethroughSpan) {
                    long jStart21 = protoOutputStream.start(2246267895831L);
                    writeStrikethroughSpanToProto(protoOutputStream, (StrikethroughSpan) underlying);
                    protoOutputStream.end(jStart21);
                } else if (underlying instanceof StyleSpan) {
                    long jStart22 = protoOutputStream.start(2246267895832L);
                    writeStyleSpanToProto(protoOutputStream, (StyleSpan) underlying);
                    protoOutputStream.end(jStart22);
                } else if (underlying instanceof SubscriptSpan) {
                    long jStart23 = protoOutputStream.start(2246267895833L);
                    writeSubscriptSpanToProto(protoOutputStream, (SubscriptSpan) underlying);
                    protoOutputStream.end(jStart23);
                } else if (underlying instanceof SuggestionRangeSpan) {
                    long jStart24 = protoOutputStream.start(2246267895835L);
                    writeSuggestionRangeSpanToProto(protoOutputStream, (SuggestionRangeSpan) underlying);
                    protoOutputStream.end(jStart24);
                } else if (underlying instanceof SuggestionSpan) {
                    long jStart25 = protoOutputStream.start(2246267895834L);
                    writeSuggestionSpanToProto(protoOutputStream, (SuggestionSpan) underlying);
                    protoOutputStream.end(jStart25);
                } else if (underlying instanceof SuperscriptSpan) {
                    long jStart26 = protoOutputStream.start(2246267895836L);
                    writeSuperscriptSpanToProto(protoOutputStream, (SuperscriptSpan) underlying);
                    protoOutputStream.end(jStart26);
                } else if (underlying instanceof TextAppearanceSpan) {
                    long jStart27 = protoOutputStream.start(2246267895837L);
                    writeTextAppearanceSpanToProto(protoOutputStream, (TextAppearanceSpan) underlying);
                    protoOutputStream.end(jStart27);
                } else if (underlying instanceof TtsSpan) {
                    long jStart28 = protoOutputStream.start(2246267895838L);
                    writeTtsSpanToProto(protoOutputStream, (TtsSpan) underlying);
                    protoOutputStream.end(jStart28);
                } else if (underlying instanceof TypefaceSpan) {
                    long jStart29 = protoOutputStream.start(2246267895839L);
                    writeTypefaceSpanToProto(protoOutputStream, (TypefaceSpan) underlying);
                    protoOutputStream.end(jStart29);
                } else if (underlying instanceof URLSpan) {
                    long jStart30 = protoOutputStream.start(2246267895841L);
                    writeURLSpanToProto(protoOutputStream, (URLSpan) underlying);
                    protoOutputStream.end(jStart30);
                } else if (underlying instanceof UnderlineSpan) {
                    long jStart31 = protoOutputStream.start(2246267895840L);
                    writeUnderlineSpanToProto(protoOutputStream, (UnderlineSpan) underlying);
                    protoOutputStream.end(jStart31);
                }
                protoOutputStream.end(jStart);
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
                long jStart = protoInputStream.start(2246267895810L);
                createSpanFromProto(protoInputStream, spannableStringBuilder);
                protoInputStream.end(jStart);
                z = true;
            } else {
                Log.w(TAG, "Unhandled field while reading CharSequence proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return z ? spannableStringBuilder : spannableStringBuilder.toString();
    }

    private static void createSpanFromProto(ProtoInputStream protoInputStream, SpannableStringBuilder spannableStringBuilder) throws Exception {
        Object objCreateAbsoluteSizeSpanFromProto;
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
                    long jStart = protoInputStream.start(2246267895812L);
                    objCreateAbsoluteSizeSpanFromProto = createAbsoluteSizeSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart);
                    break;
                case 5:
                    long jStart2 = protoInputStream.start(2246267895813L);
                    objCreateAbsoluteSizeSpanFromProto = createAccessibilityClickableSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart2);
                    break;
                case 6:
                    long jStart3 = protoInputStream.start(2246267895814L);
                    objCreateAbsoluteSizeSpanFromProto = createAccessibilityReplacementSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart3);
                    break;
                case 7:
                    long jStart4 = protoInputStream.start(2246267895815L);
                    objCreateAbsoluteSizeSpanFromProto = createAccessibilityURLSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart4);
                    break;
                case 8:
                    long jStart5 = protoInputStream.start(2246267895816L);
                    objCreateAbsoluteSizeSpanFromProto = createAlignmentSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(jStart5);
                    break;
                case 9:
                    long jStart6 = protoInputStream.start(2246267895817L);
                    objCreateAbsoluteSizeSpanFromProto = createAnnotationFromProto(protoInputStream);
                    protoInputStream.end(jStart6);
                    break;
                case 10:
                    long jStart7 = protoInputStream.start(2246267895818L);
                    objCreateAbsoluteSizeSpanFromProto = createBackgroundColorSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart7);
                    break;
                case 11:
                    long jStart8 = protoInputStream.start(2246267895819L);
                    objCreateAbsoluteSizeSpanFromProto = createBulletSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart8);
                    break;
                case 12:
                    long jStart9 = protoInputStream.start(2246267895820L);
                    objCreateAbsoluteSizeSpanFromProto = createEasyEditSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart9);
                    break;
                case 13:
                    long jStart10 = protoInputStream.start(2246267895821L);
                    objCreateAbsoluteSizeSpanFromProto = createForegroundColorSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart10);
                    break;
                case 14:
                    long jStart11 = protoInputStream.start(2246267895822L);
                    objCreateAbsoluteSizeSpanFromProto = createLeadingMarginSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(jStart11);
                    break;
                case 15:
                    long jStart12 = protoInputStream.start(2246267895823L);
                    objCreateAbsoluteSizeSpanFromProto = createLineBackgroundSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(jStart12);
                    break;
                case 16:
                    if (Flags.noBreakNoHyphenationSpan()) {
                        long jStart13 = protoInputStream.start(2246267895824L);
                        objCreateAbsoluteSizeSpanFromProto = createLineBreakConfigSpanFromProto(protoInputStream);
                        protoInputStream.end(jStart13);
                        break;
                    }
                case 17:
                    long jStart14 = protoInputStream.start(2246267895825L);
                    objCreateAbsoluteSizeSpanFromProto = createLineHeightSpanStandardFromProto(protoInputStream);
                    protoInputStream.end(jStart14);
                    break;
                case 18:
                    long jStart15 = protoInputStream.start(2246267895826L);
                    objCreateAbsoluteSizeSpanFromProto = createLocaleSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart15);
                    break;
                case 19:
                    long jStart16 = protoInputStream.start(2246267895827L);
                    objCreateAbsoluteSizeSpanFromProto = createQuoteSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart16);
                    break;
                case 20:
                    long jStart17 = protoInputStream.start(2246267895828L);
                    objCreateAbsoluteSizeSpanFromProto = createRelativeSizeSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart17);
                    break;
                case 21:
                    long jStart18 = protoInputStream.start(2246267895829L);
                    objCreateAbsoluteSizeSpanFromProto = createScaleXSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart18);
                    break;
                case 22:
                    long jStart19 = protoInputStream.start(2246267895830L);
                    objCreateAbsoluteSizeSpanFromProto = createSpellCheckSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart19);
                    break;
                case 23:
                    long jStart20 = protoInputStream.start(2246267895831L);
                    objCreateAbsoluteSizeSpanFromProto = createStrikethroughSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart20);
                    break;
                case 24:
                    long jStart21 = protoInputStream.start(2246267895832L);
                    objCreateAbsoluteSizeSpanFromProto = createStyleSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart21);
                    break;
                case 25:
                    long jStart22 = protoInputStream.start(2246267895833L);
                    objCreateAbsoluteSizeSpanFromProto = createSubscriptSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart22);
                    break;
                case 26:
                    long jStart23 = protoInputStream.start(2246267895834L);
                    objCreateAbsoluteSizeSpanFromProto = createSuggestionSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart23);
                    break;
                case 27:
                    long jStart24 = protoInputStream.start(2246267895835L);
                    objCreateAbsoluteSizeSpanFromProto = createSuggestionRangeSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart24);
                    break;
                case 28:
                    long jStart25 = protoInputStream.start(2246267895836L);
                    objCreateAbsoluteSizeSpanFromProto = createSuperscriptSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart25);
                    break;
                case 29:
                    long jStart26 = protoInputStream.start(2246267895837L);
                    objCreateAbsoluteSizeSpanFromProto = createTextAppearanceSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart26);
                    break;
                case 30:
                    long jStart27 = protoInputStream.start(2246267895838L);
                    objCreateAbsoluteSizeSpanFromProto = createTtsSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart27);
                    break;
                case 31:
                    long jStart28 = protoInputStream.start(2246267895839L);
                    objCreateAbsoluteSizeSpanFromProto = createTypefaceSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart28);
                    break;
                case 32:
                    long jStart29 = protoInputStream.start(2246267895840L);
                    objCreateAbsoluteSizeSpanFromProto = createUnderlineSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart29);
                    break;
                case 33:
                    long jStart30 = protoInputStream.start(2246267895841L);
                    objCreateAbsoluteSizeSpanFromProto = createURLSpanFromProto(protoInputStream);
                    protoInputStream.end(jStart30);
                    break;
                default:
                    Log.w(TAG, "Unhandled field while reading CharSequence proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    continue;
            }
            obj = objCreateAbsoluteSizeSpanFromProto;
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
                long jStart = protoInputStream.start(1146756268033L);
                CharSequence charSequenceCreateCharSequenceFromProto = createCharSequenceFromProto(protoInputStream);
                protoInputStream.end(jStart);
                charSequence = charSequenceCreateCharSequenceFromProto;
            } else {
                Log.w("AccessibilityReplacemen", "Unhandled field while reading AccessibilityReplacementSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AccessibilityReplacementSpan(charSequence);
    }

    public static void writeAccessibilityReplacementSpanToProto(ProtoOutputStream protoOutputStream, AccessibilityReplacementSpan accessibilityReplacementSpan) {
        long jStart = protoOutputStream.start(1146756268033L);
        CharSequence contentDescription = accessibilityReplacementSpan.getContentDescription();
        if (contentDescription != null) {
            writeCharSequenceToProto(protoOutputStream, contentDescription);
        }
        protoOutputStream.end(jStart);
    }

    public static AccessibilityURLSpan createAccessibilityURLSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String string = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("AccessibilityURLSpan", "Unhandled field while reading AccessibilityURLSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AccessibilityURLSpan(new URLSpan(string));
    }

    public static void writeAccessibilityURLSpanToProto(ProtoOutputStream protoOutputStream, AccessibilityURLSpan accessibilityURLSpan) {
        protoOutputStream.write(1138166333441L, accessibilityURLSpan.getURL());
    }

    public static AlignmentSpan.Standard createAlignmentSpanStandardFromProto(ProtoInputStream protoInputStream) throws Exception {
        String string = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("AlignmentSpan", "Unhandled field while reading AlignmentSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new AlignmentSpan.Standard(Layout.Alignment.valueOf(string));
    }

    public static void writeAlignmentSpanStandardToProto(ProtoOutputStream protoOutputStream, AlignmentSpan.Standard standard) {
        protoOutputStream.write(1138166333441L, standard.getAlignment().name());
    }

    public static Annotation createAnnotationFromProto(ProtoInputStream protoInputStream) throws Exception {
        String string = null;
        String string2 = null;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                string2 = protoInputStream.readString(1138166333442L);
            } else {
                Log.w("Annotation", "Unhandled field while reading Annotation proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new Annotation(string, string2);
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
        String string = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("LocaleSpan", "Unhandled field while reading LocaleSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new LocaleSpan(LocaleList.forLanguageTags(string));
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
        String string = null;
        String string2 = null;
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
                    string = protoInputStream.readString(1138166333443L);
                    break;
                case 4:
                    string2 = protoInputStream.readString(1138166333444L);
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
        return new SuggestionSpan(strArr, i, string, string2, i2, i3, f, i4, f2, i5, f3, i6, f4);
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
        String string = null;
        ColorStateList colorStateListCreateFromProto = null;
        ColorStateList colorStateListCreateFromProto2 = null;
        LocaleList localeListForLanguageTags = null;
        String string2 = null;
        String string3 = null;
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
                    string = protoInputStream.readString(1138166333441L);
                    break;
                case 2:
                    i = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    i2 = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    long jStart = protoInputStream.start(1146756268036L);
                    colorStateListCreateFromProto = ColorStateList.createFromProto(protoInputStream);
                    protoInputStream.end(jStart);
                    break;
                case 5:
                    long jStart2 = protoInputStream.start(1146756268037L);
                    colorStateListCreateFromProto2 = ColorStateList.createFromProto(protoInputStream);
                    protoInputStream.end(jStart2);
                    break;
                case 6:
                default:
                    Log.w("TextAppearanceSpan", "Unhandled field while reading TextAppearanceSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    break;
                case 7:
                    i3 = protoInputStream.readInt(1120986464263L);
                    break;
                case 8:
                    localeListForLanguageTags = LocaleList.forLanguageTags(protoInputStream.readString(1138166333448L));
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
                    string2 = protoInputStream.readString(1138166333457L);
                    break;
                case 18:
                    string3 = protoInputStream.readString(1138166333458L);
                    break;
            }
        }
        return new TextAppearanceSpan(string, i, i2, colorStateListCreateFromProto, colorStateListCreateFromProto2, null, i3, localeListForLanguageTags, f, f2, f3, i4, z, z2, z3, f4, string2, string3);
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
            long jStart = protoOutputStream.start(1146756268036L);
            textAppearanceSpan.getTextColor().writeToProto(protoOutputStream);
            protoOutputStream.end(jStart);
        }
        if (textAppearanceSpan.getLinkTextColor() != null) {
            long jStart2 = protoOutputStream.start(1146756268037L);
            textAppearanceSpan.getLinkTextColor().writeToProto(protoOutputStream);
            protoOutputStream.end(jStart2);
        }
    }

    public static TtsSpan createTtsSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String string = null;
        PersistableBundle fromStream = null;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else if (fieldNumber == 2) {
                fromStream = PersistableBundle.readFromStream(new ByteArrayInputStream(protoInputStream.readString(1138166333442L).getBytes()));
            } else {
                Log.w("TtsSpan", "Unhandled field while reading TtsSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new TtsSpan(string, fromStream);
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
        String string = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("TypefaceSpan", "Unhandled field while reading TypefaceSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new TypefaceSpan(string);
    }

    public static void writeTypefaceSpanToProto(ProtoOutputStream protoOutputStream, TypefaceSpan typefaceSpan) {
        protoOutputStream.write(1138166333441L, typefaceSpan.getFamily());
    }

    public static URLSpan createURLSpanFromProto(ProtoInputStream protoInputStream) throws Exception {
        String string = null;
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                string = protoInputStream.readString(1138166333441L);
            } else {
                Log.w("URLSpan", "Unhandled field while reading URLSpan proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new URLSpan(string);
    }

    public static void writeURLSpanToProto(ProtoOutputStream protoOutputStream, URLSpan uRLSpan) {
        protoOutputStream.write(1138166333441L, uRLSpan.getURL());
    }

    public static UnderlineSpan createUnderlineSpanFromProto(ProtoInputStream protoInputStream) {
        return new UnderlineSpan();
    }
}
