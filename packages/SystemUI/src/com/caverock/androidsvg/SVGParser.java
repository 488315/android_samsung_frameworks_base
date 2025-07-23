package com.caverock.androidsvg;

import android.graphics.Matrix;
import android.util.Log;
import android.util.Xml;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tileimpl.SubtitleArrayMapping$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SVGParser {
    public int ignoreDepth;
    public SVG svgDocument = null;
    public SVG.SvgContainer currentElement = null;
    public boolean ignoring = false;
    public boolean inMetadataElement = false;
    public SVGElem metadataTag = null;
    public StringBuilder metadataElementContents = null;
    public boolean inStyleElement = false;
    public StringBuilder styleElementContents = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.caverock.androidsvg.SVGParser$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr;
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem;

        static {
            int[] iArr = new int[SVGAttr.values().length];
            $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr = iArr;
            try {
                iArr[SVGAttr.x.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.y.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.width.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.height.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.version.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.href.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.preserveAspectRatio.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.d.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.pathLength.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.rx.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.ry.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.cx.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.cy.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.r.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.x1.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.y1.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.x2.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.y2.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.dx.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.dy.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.requiredFeatures.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.requiredExtensions.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.systemLanguage.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.requiredFormats.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.requiredFonts.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.refX.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.refY.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.markerWidth.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.markerHeight.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.markerUnits.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.orient.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.gradientUnits.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.gradientTransform.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.spreadMethod.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fx.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fy.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.offset.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.clipPathUnits.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.startOffset.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.patternUnits.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.patternContentUnits.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.patternTransform.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.maskUnits.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.maskContentUnits.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.style.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.CLASS.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fill.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fill_rule.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fill_opacity.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_opacity.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_width.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_linecap.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_linejoin.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_miterlimit.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_dasharray.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stroke_dashoffset.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.opacity.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.color.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.font.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.font_family.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.font_size.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.font_weight.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.font_style.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.text_decoration.ordinal()] = 65;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.direction.ordinal()] = 66;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.text_anchor.ordinal()] = 67;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.overflow.ordinal()] = 68;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.marker.ordinal()] = 69;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.marker_start.ordinal()] = 70;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.marker_mid.ordinal()] = 71;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.marker_end.ordinal()] = 72;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.display.ordinal()] = 73;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.visibility.ordinal()] = 74;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stop_color.ordinal()] = 75;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.stop_opacity.ordinal()] = 76;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.clip.ordinal()] = 77;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.clip_path.ordinal()] = 78;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.clip_rule.ordinal()] = 79;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.mask.ordinal()] = 80;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.solid_color.ordinal()] = 81;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.solid_opacity.ordinal()] = 82;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.viewport_fill.ordinal()] = 83;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.viewport_fill_opacity.ordinal()] = 84;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.vector_effect.ordinal()] = 85;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.image_rendering.ordinal()] = 86;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.viewBox.ordinal()] = 87;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.type.ordinal()] = 88;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.media.ordinal()] = 89;
            } catch (NoSuchFieldError unused89) {
            }
            int[] iArr2 = new int[SVGElem.values().length];
            $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem = iArr2;
            try {
                iArr2[SVGElem.svg.ordinal()] = 1;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.g.ordinal()] = 2;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.a.ordinal()] = 3;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.defs.ordinal()] = 4;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.use.ordinal()] = 5;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.path.ordinal()] = 6;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.rect.ordinal()] = 7;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.circle.ordinal()] = 8;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.ellipse.ordinal()] = 9;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.line.ordinal()] = 10;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.polyline.ordinal()] = 11;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.polygon.ordinal()] = 12;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.text.ordinal()] = 13;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.tspan.ordinal()] = 14;
            } catch (NoSuchFieldError unused103) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.tref.ordinal()] = 15;
            } catch (NoSuchFieldError unused104) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.SWITCH.ordinal()] = 16;
            } catch (NoSuchFieldError unused105) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.symbol.ordinal()] = 17;
            } catch (NoSuchFieldError unused106) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.marker.ordinal()] = 18;
            } catch (NoSuchFieldError unused107) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.linearGradient.ordinal()] = 19;
            } catch (NoSuchFieldError unused108) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.radialGradient.ordinal()] = 20;
            } catch (NoSuchFieldError unused109) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.stop.ordinal()] = 21;
            } catch (NoSuchFieldError unused110) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.title.ordinal()] = 22;
            } catch (NoSuchFieldError unused111) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.desc.ordinal()] = 23;
            } catch (NoSuchFieldError unused112) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.clipPath.ordinal()] = 24;
            } catch (NoSuchFieldError unused113) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.textPath.ordinal()] = 25;
            } catch (NoSuchFieldError unused114) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.pattern.ordinal()] = 26;
            } catch (NoSuchFieldError unused115) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.image.ordinal()] = 27;
            } catch (NoSuchFieldError unused116) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.view.ordinal()] = 28;
            } catch (NoSuchFieldError unused117) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.mask.ordinal()] = 29;
            } catch (NoSuchFieldError unused118) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.style.ordinal()] = 30;
            } catch (NoSuchFieldError unused119) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.solidColor.ordinal()] = 31;
            } catch (NoSuchFieldError unused120) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AspectRatioKeywords {
        public static final Map aspectRatioKeywords;

        static {
            HashMap hashMap = new HashMap(10);
            aspectRatioKeywords = hashMap;
            hashMap.put(SignalSeverity.NONE, PreserveAspectRatio.Alignment.none);
            hashMap.put("xMinYMin", PreserveAspectRatio.Alignment.xMinYMin);
            hashMap.put("xMidYMin", PreserveAspectRatio.Alignment.xMidYMin);
            hashMap.put("xMaxYMin", PreserveAspectRatio.Alignment.xMaxYMin);
            hashMap.put("xMinYMid", PreserveAspectRatio.Alignment.xMinYMid);
            hashMap.put("xMidYMid", PreserveAspectRatio.Alignment.xMidYMid);
            hashMap.put("xMaxYMid", PreserveAspectRatio.Alignment.xMaxYMid);
            hashMap.put("xMinYMax", PreserveAspectRatio.Alignment.xMinYMax);
            hashMap.put("xMidYMax", PreserveAspectRatio.Alignment.xMidYMax);
            hashMap.put("xMaxYMax", PreserveAspectRatio.Alignment.xMaxYMax);
        }

        private AspectRatioKeywords() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ColourKeywords {
        public static final Map colourKeywords;

        static {
            HashMap hashMap = new HashMap(47);
            colourKeywords = hashMap;
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-984833, hashMap, "aliceblue", -332841, "antiquewhite");
            hashMap.put("aqua", -16711681);
            hashMap.put("aquamarine", -8388652);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-983041, hashMap, "azure", -657956, "beige");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-6972, hashMap, "bisque", -16777216, "black");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-5171, hashMap, "blanchedalmond", -16776961, "blue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7722014, hashMap, "blueviolet", -5952982, "brown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-2180985, hashMap, "burlywood", -10510688, "cadetblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8388864, hashMap, "chartreuse", -2987746, "chocolate");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-32944, hashMap, "coral", -10185235, "cornflowerblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1828, hashMap, "cornsilk", -2354116, "crimson");
            hashMap.put("cyan", -16711681);
            hashMap.put("darkblue", -16777077);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16741493, hashMap, "darkcyan", -4684277, "darkgoldenrod");
            hashMap.put("darkgray", -5658199);
            hashMap.put("darkgreen", -16751616);
            hashMap.put("darkgrey", -5658199);
            hashMap.put("darkkhaki", -4343957);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7667573, hashMap, "darkmagenta", -11179217, "darkolivegreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-29696, hashMap, "darkorange", -6737204, "darkorchid");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7667712, hashMap, "darkred", -1468806, "darksalmon");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7357297, hashMap, "darkseagreen", -12042869, "darkslateblue");
            hashMap.put("darkslategray", -13676721);
            hashMap.put("darkslategrey", -13676721);
            hashMap.put("darkturquoise", -16724271);
            hashMap.put("darkviolet", -7077677);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-60269, hashMap, "deeppink", -16728065, "deepskyblue");
            hashMap.put("dimgray", -9868951);
            hashMap.put("dimgrey", -9868951);
            hashMap.put("dodgerblue", -14774017);
            hashMap.put("firebrick", -5103070);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1296, hashMap, "floralwhite", -14513374, "forestgreen");
            hashMap.put("fuchsia", -65281);
            hashMap.put("gainsboro", -2302756);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-460545, hashMap, "ghostwhite", -10496, "gold");
            hashMap.put("goldenrod", -2448096);
            hashMap.put("gray", -8355712);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16744448, hashMap, "green", -5374161, "greenyellow");
            hashMap.put("grey", -8355712);
            hashMap.put("honeydew", -983056);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-38476, hashMap, "hotpink", -3318692, "indianred");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-11861886, hashMap, "indigo", -16, "ivory");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-989556, hashMap, "khaki", -1644806, "lavender");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-3851, hashMap, "lavenderblush", -8586240, "lawngreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1331, hashMap, "lemonchiffon", -5383962, "lightblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1015680, hashMap, "lightcoral", -2031617, "lightcyan");
            hashMap.put("lightgoldenrodyellow", -329006);
            hashMap.put("lightgray", -2894893);
            hashMap.put("lightgreen", -7278960);
            hashMap.put("lightgrey", -2894893);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-18751, hashMap, "lightpink", -24454, "lightsalmon");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-14634326, hashMap, "lightseagreen", -7876870, "lightskyblue");
            hashMap.put("lightslategray", -8943463);
            hashMap.put("lightslategrey", -8943463);
            hashMap.put("lightsteelblue", -5192482);
            hashMap.put("lightyellow", -32);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16711936, hashMap, "lime", -13447886, "limegreen");
            hashMap.put("linen", -331546);
            hashMap.put("magenta", -65281);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8388608, hashMap, "maroon", -10039894, "mediumaquamarine");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16777011, hashMap, "mediumblue", -4565549, "mediumorchid");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7114533, hashMap, "mediumpurple", -12799119, "mediumseagreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8689426, hashMap, "mediumslateblue", -16713062, "mediumspringgreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-12004916, hashMap, "mediumturquoise", -3730043, "mediumvioletred");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-15132304, hashMap, "midnightblue", -655366, "mintcream");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-6943, hashMap, "mistyrose", -6987, "moccasin");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8531, hashMap, "navajowhite", -16777088, "navy");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-133658, hashMap, "oldlace", -8355840, "olive");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-9728477, hashMap, "olivedrab", -23296, "orange");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-47872, hashMap, "orangered", -2461482, "orchid");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1120086, hashMap, "palegoldenrod", -6751336, "palegreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-5247250, hashMap, "paleturquoise", -2396013, "palevioletred");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-4139, hashMap, "papayawhip", -9543, "peachpuff");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-3308225, hashMap, "peru", -16181, "pink");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-2252579, hashMap, "plum", -5185306, "powderblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8388480, hashMap, "purple", -10079335, "rebeccapurple");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-65536, hashMap, "red", -4419697, "rosybrown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-12490271, hashMap, "royalblue", -7650029, "saddlebrown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-360334, hashMap, "salmon", -744352, "sandybrown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-13726889, hashMap, "seagreen", -2578, "seashell");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-6270419, hashMap, "sienna", -4144960, "silver");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7876885, hashMap, "skyblue", -9807155, "slateblue");
            hashMap.put("slategray", -9404272);
            hashMap.put("slategrey", -9404272);
            hashMap.put("snow", -1286);
            hashMap.put("springgreen", -16711809);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-12156236, hashMap, "steelblue", -2968436, "tan");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16744320, hashMap, "teal", -2572328, "thistle");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-40121, hashMap, "tomato", -12525360, "turquoise");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1146130, hashMap, "violet", -663885, "wheat");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1, hashMap, "white", -657931, "whitesmoke");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-256, hashMap, "yellow", -6632142, "yellowgreen");
            hashMap.put("transparent", 0);
        }

        private ColourKeywords() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FontSizeKeywords {
        public static final Map fontSizeKeywords;

        static {
            HashMap hashMap = new HashMap(9);
            fontSizeKeywords = hashMap;
            SVG.Unit unit = SVG.Unit.pt;
            hashMap.put("xx-small", new SVG.Length(0.694f, unit));
            hashMap.put("x-small", new SVG.Length(0.833f, unit));
            hashMap.put("small", new SVG.Length(10.0f, unit));
            hashMap.put("medium", new SVG.Length(12.0f, unit));
            hashMap.put("large", new SVG.Length(14.4f, unit));
            hashMap.put("x-large", new SVG.Length(17.3f, unit));
            hashMap.put("xx-large", new SVG.Length(20.7f, unit));
            SVG.Unit unit2 = SVG.Unit.percent;
            hashMap.put("smaller", new SVG.Length(83.33f, unit2));
            hashMap.put("larger", new SVG.Length(120.0f, unit2));
        }

        private FontSizeKeywords() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FontWeightKeywords {
        public static final Map fontWeightKeywords;

        static {
            HashMap hashMap = new HashMap(13);
            fontWeightKeywords = hashMap;
            hashMap.put(SystemUIAnalytics.QPNE_VID_NORMAL, 400);
            Integer valueOf = Integer.valueOf(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
            hashMap.put("bold", valueOf);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(1, hashMap, "bolder", -1, "lighter");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(100, hashMap, DATA.DM_FIELD_INDEX.UT_PDN, 200, SystemUIAnalytics.SID_WALLPAPER_LOCK_COMMON_PREVIEW);
            hashMap.put("300", 300);
            hashMap.put("400", 400);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(500, hashMap, SystemUIAnalytics.SID_SUBSCREEN_NORMAL, VolteConstants.ErrorCode.BUSY_EVERYWHERE, SystemUIAnalytics.SID_WALLPAPER_VIDEO_PREVIEW);
            hashMap.put("700", valueOf);
            hashMap.put(SystemUIAnalytics.SID_TOUCH_LOCKSCREEN_RESIZABLE, 800);
            hashMap.put("900", 900);
        }

        private FontWeightKeywords() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SAXHandler extends DefaultHandler2 {
        private SAXHandler() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void characters(char[] cArr, int i, int i2) {
            SVGParser.this.text(new String(cArr, i, i2));
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void endDocument() {
            SVGParser.this.getClass();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void endElement(String str, String str2, String str3) {
            SVGParser.this.endElement(str, str2, str3);
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void processingInstruction(String str, String str2) {
            TextScanner textScanner = new TextScanner(str2);
            SVGParser.this.getClass();
            SVGParser.parseProcessingInstructionAttributes(textScanner);
            SVGParser.this.getClass();
            str.equals("xml-stylesheet");
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void startDocument() {
            SVGParser sVGParser = SVGParser.this;
            sVGParser.getClass();
            sVGParser.svgDocument = new SVG();
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public final void startElement(String str, String str2, String str3, Attributes attributes) {
            SVGParser.this.startElement(str, str2, str3, attributes);
        }

        public /* synthetic */ SAXHandler(SVGParser sVGParser, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum SVGAttr {
        CLASS,
        clip,
        clip_path,
        clipPathUnits,
        clip_rule,
        color,
        cx,
        cy,
        direction,
        dx,
        dy,
        fx,
        fy,
        d,
        display,
        fill,
        fill_rule,
        fill_opacity,
        font,
        font_family,
        font_size,
        font_weight,
        font_style,
        gradientTransform,
        gradientUnits,
        height,
        href,
        image_rendering,
        marker,
        marker_start,
        marker_mid,
        marker_end,
        markerHeight,
        markerUnits,
        markerWidth,
        mask,
        maskContentUnits,
        maskUnits,
        media,
        offset,
        opacity,
        orient,
        overflow,
        pathLength,
        patternContentUnits,
        patternTransform,
        patternUnits,
        points,
        preserveAspectRatio,
        r,
        refX,
        refY,
        requiredFeatures,
        requiredExtensions,
        requiredFormats,
        requiredFonts,
        rx,
        ry,
        solid_color,
        solid_opacity,
        spreadMethod,
        startOffset,
        stop_color,
        stop_opacity,
        stroke,
        stroke_dasharray,
        stroke_dashoffset,
        stroke_linecap,
        stroke_linejoin,
        stroke_miterlimit,
        stroke_opacity,
        stroke_width,
        style,
        systemLanguage,
        text_anchor,
        text_decoration,
        transform,
        type,
        vector_effect,
        version,
        viewBox,
        width,
        x,
        y,
        x1,
        y1,
        x2,
        y2,
        viewport_fill,
        viewport_fill_opacity,
        visibility,
        UNSUPPORTED;

        public static final Map cache = new HashMap();

        static {
            for (SVGAttr sVGAttr : values()) {
                if (sVGAttr == CLASS) {
                    cache.put("class", sVGAttr);
                } else if (sVGAttr != UNSUPPORTED) {
                    cache.put(sVGAttr.name().replace('_', '-'), sVGAttr);
                }
            }
        }

        public static SVGAttr fromString(String str) {
            SVGAttr sVGAttr = (SVGAttr) ((HashMap) cache).get(str);
            return sVGAttr != null ? sVGAttr : UNSUPPORTED;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum SVGElem {
        svg,
        a,
        circle,
        clipPath,
        defs,
        desc,
        ellipse,
        g,
        image,
        line,
        linearGradient,
        marker,
        mask,
        path,
        pattern,
        polygon,
        polyline,
        radialGradient,
        rect,
        solidColor,
        stop,
        style,
        SWITCH,
        symbol,
        text,
        textPath,
        title,
        tref,
        tspan,
        use,
        view,
        UNSUPPORTED;

        public static final Map cache = new HashMap();

        static {
            for (SVGElem sVGElem : values()) {
                if (sVGElem == SWITCH) {
                    cache.put("switch", sVGElem);
                } else if (sVGElem != UNSUPPORTED) {
                    cache.put(sVGElem.name(), sVGElem);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TextScanner {
        public final String input;
        public final int inputLength;
        public int position = 0;
        public final NumberParser numberParser = new NumberParser();

        public TextScanner(String str) {
            this.inputLength = 0;
            String trim = str.trim();
            this.input = trim;
            this.inputLength = trim.length();
        }

        public static boolean isWhitespace(int i) {
            return i == 32 || i == 10 || i == 13 || i == 9;
        }

        public final int advanceChar() {
            int i = this.position;
            int i2 = this.inputLength;
            if (i == i2) {
                return -1;
            }
            int i3 = i + 1;
            this.position = i3;
            if (i3 < i2) {
                return this.input.charAt(i3);
            }
            return -1;
        }

        public final Boolean checkedNextFlag(Object obj) {
            if (obj == null) {
                return null;
            }
            skipCommaWhitespace();
            int i = this.position;
            if (i == this.inputLength) {
                return null;
            }
            char charAt = this.input.charAt(i);
            if (charAt != '0' && charAt != '1') {
                return null;
            }
            this.position++;
            return Boolean.valueOf(charAt == '1');
        }

        public final float checkedNextFloat(float f) {
            if (Float.isNaN(f)) {
                return Float.NaN;
            }
            skipCommaWhitespace();
            return nextFloat();
        }

        public final boolean consume(char c) {
            int i = this.position;
            boolean z = i < this.inputLength && this.input.charAt(i) == c;
            if (z) {
                this.position++;
            }
            return z;
        }

        public final boolean empty() {
            return this.position == this.inputLength;
        }

        public final Integer nextChar() {
            int i = this.position;
            if (i == this.inputLength) {
                return null;
            }
            this.position = i + 1;
            return Integer.valueOf(this.input.charAt(i));
        }

        public final float nextFloat() {
            int i = this.position;
            int i2 = this.inputLength;
            NumberParser numberParser = this.numberParser;
            float parseNumber = numberParser.parseNumber(i, i2, this.input);
            if (!Float.isNaN(parseNumber)) {
                this.position = numberParser.pos;
            }
            return parseNumber;
        }

        public final SVG.Length nextLength() {
            float nextFloat = nextFloat();
            if (Float.isNaN(nextFloat)) {
                return null;
            }
            SVG.Unit nextUnit = nextUnit();
            return nextUnit == null ? new SVG.Length(nextFloat, SVG.Unit.px) : new SVG.Length(nextFloat, nextUnit);
        }

        public final String nextQuotedString() {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            char charAt = str.charAt(i);
            if (charAt != '\'' && charAt != '\"') {
                return null;
            }
            int advanceChar = advanceChar();
            while (advanceChar != -1 && advanceChar != charAt) {
                advanceChar = advanceChar();
            }
            if (advanceChar == -1) {
                this.position = i;
                return null;
            }
            int i2 = this.position;
            this.position = i2 + 1;
            return str.substring(i + 1, i2);
        }

        public final String nextToken() {
            return nextToken(' ', false);
        }

        public final SVG.Unit nextUnit() {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            if (str.charAt(i) == '%') {
                this.position++;
                return SVG.Unit.percent;
            }
            int i2 = this.position;
            if (i2 > this.inputLength - 2) {
                return null;
            }
            try {
                SVG.Unit valueOf = SVG.Unit.valueOf(str.substring(i2, i2 + 2).toLowerCase(Locale.US));
                this.position += 2;
                return valueOf;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        public final float possibleNextFloat() {
            skipCommaWhitespace();
            int i = this.position;
            int i2 = this.inputLength;
            NumberParser numberParser = this.numberParser;
            float parseNumber = numberParser.parseNumber(i, i2, this.input);
            if (!Float.isNaN(parseNumber)) {
                this.position = numberParser.pos;
            }
            return parseNumber;
        }

        public final boolean skipCommaWhitespace() {
            skipWhitespace();
            int i = this.position;
            if (i == this.inputLength || this.input.charAt(i) != ',') {
                return false;
            }
            this.position++;
            skipWhitespace();
            return true;
        }

        public final void skipWhitespace() {
            while (true) {
                int i = this.position;
                if (i >= this.inputLength || !isWhitespace(this.input.charAt(i))) {
                    return;
                } else {
                    this.position++;
                }
            }
        }

        public final String nextToken(char c, boolean z) {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            char charAt = str.charAt(i);
            if ((!z && isWhitespace(charAt)) || charAt == c) {
                return null;
            }
            int i2 = this.position;
            int advanceChar = advanceChar();
            while (advanceChar != -1 && advanceChar != c && (z || !isWhitespace(advanceChar))) {
                advanceChar = advanceChar();
            }
            return str.substring(i2, this.position);
        }

        public final boolean consume(String str) {
            int length = str.length();
            int i = this.position;
            boolean z = i <= this.inputLength - length && this.input.substring(i, i + length).equals(str);
            if (z) {
                this.position += length;
            }
            return z;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class XPPAttributesWrapper implements Attributes {
        public final XmlPullParser parser;

        public XPPAttributesWrapper(SVGParser sVGParser, XmlPullParser xmlPullParser) {
            this.parser = xmlPullParser;
        }

        @Override // org.xml.sax.Attributes
        public final int getIndex(String str) {
            return -1;
        }

        @Override // org.xml.sax.Attributes
        public final int getLength() {
            return this.parser.getAttributeCount();
        }

        @Override // org.xml.sax.Attributes
        public final String getLocalName(int i) {
            return this.parser.getAttributeName(i);
        }

        @Override // org.xml.sax.Attributes
        public final String getQName(int i) {
            String attributeName = this.parser.getAttributeName(i);
            if (this.parser.getAttributePrefix(i) == null) {
                return attributeName;
            }
            return this.parser.getAttributePrefix(i) + ':' + attributeName;
        }

        @Override // org.xml.sax.Attributes
        public final String getType(int i) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getURI(int i) {
            return this.parser.getAttributeNamespace(i);
        }

        @Override // org.xml.sax.Attributes
        public final String getValue(String str) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final int getIndex(String str, String str2) {
            return -1;
        }

        @Override // org.xml.sax.Attributes
        public final String getType(String str) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getValue(String str, String str2) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getType(String str, String str2) {
            return null;
        }

        @Override // org.xml.sax.Attributes
        public final String getValue(int i) {
            return this.parser.getAttributeValue(i);
        }
    }

    public static int clamp255(float f) {
        if (f < 0.0f) {
            return 0;
        }
        if (f > 255.0f) {
            return 255;
        }
        return Math.round(f);
    }

    public static int hslToRgb(float f, float f2, float f3) {
        float f4 = f % 360.0f;
        if (f < 0.0f) {
            f4 += 360.0f;
        }
        float f5 = f4 / 60.0f;
        float f6 = f2 / 100.0f;
        float f7 = f3 / 100.0f;
        if (f6 < 0.0f) {
            f6 = 0.0f;
        } else if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        float f8 = f7 >= 0.0f ? f7 > 1.0f ? 1.0f : f7 : 0.0f;
        float f9 = f8 <= 0.5f ? (f6 + 1.0f) * f8 : (f8 + f6) - (f6 * f8);
        float f10 = (f8 * 2.0f) - f9;
        return clamp255(hueToRgb(f10, f9, f5 - 2.0f) * 256.0f) | (clamp255(hueToRgb(f10, f9, f5 + 2.0f) * 256.0f) << 16) | (clamp255(hueToRgb(f10, f9, f5) * 256.0f) << 8);
    }

    public static float hueToRgb(float f, float f2, float f3) {
        if (f3 < 0.0f) {
            f3 += 6.0f;
        }
        if (f3 >= 6.0f) {
            f3 -= 6.0f;
        }
        return f3 < 1.0f ? DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f, f3, f) : f3 < 3.0f ? f2 : f3 < 4.0f ? DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(4.0f, f3, f2 - f, f) : f;
    }

    public static void parseAttributesConditional(SVG.SvgConditional svgConditional, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()]) {
                case 21:
                    TextScanner textScanner = new TextScanner(trim);
                    HashSet hashSet = new HashSet();
                    while (!textScanner.empty()) {
                        String nextToken = textScanner.nextToken();
                        if (nextToken.startsWith("http://www.w3.org/TR/SVG11/feature#")) {
                            hashSet.add(nextToken.substring(35));
                        } else {
                            hashSet.add("UNSUPPORTED");
                        }
                        textScanner.skipWhitespace();
                    }
                    svgConditional.setRequiredFeatures(hashSet);
                    break;
                case 22:
                    svgConditional.setRequiredExtensions(trim);
                    break;
                case 23:
                    TextScanner textScanner2 = new TextScanner(trim);
                    HashSet hashSet2 = new HashSet();
                    while (!textScanner2.empty()) {
                        String nextToken2 = textScanner2.nextToken();
                        int indexOf = nextToken2.indexOf(45);
                        if (indexOf != -1) {
                            nextToken2 = nextToken2.substring(0, indexOf);
                        }
                        hashSet2.add(new Locale(nextToken2, "", "").getLanguage());
                        textScanner2.skipWhitespace();
                    }
                    svgConditional.setSystemLanguage(hashSet2);
                    break;
                case 24:
                    TextScanner textScanner3 = new TextScanner(trim);
                    HashSet hashSet3 = new HashSet();
                    while (!textScanner3.empty()) {
                        hashSet3.add(textScanner3.nextToken());
                        textScanner3.skipWhitespace();
                    }
                    svgConditional.setRequiredFormats(hashSet3);
                    break;
                case 25:
                    List parseFontFamily = parseFontFamily(trim);
                    svgConditional.setRequiredFonts(parseFontFamily != null ? new HashSet(parseFontFamily) : new HashSet(0));
                    break;
            }
        }
    }

    public static void parseAttributesCore(SVG.SvgElementBase svgElementBase, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String qName = attributes.getQName(i);
            if (qName.equals("id") || qName.equals("xml:id")) {
                svgElementBase.id = attributes.getValue(i).trim();
                return;
            }
            if (qName.equals("xml:space")) {
                String trim = attributes.getValue(i).trim();
                if ("default".equals(trim)) {
                    svgElementBase.spacePreserve = Boolean.FALSE;
                    return;
                } else {
                    if (!"preserve".equals(trim)) {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid value for \"xml:space\" attribute: ", trim));
                    }
                    svgElementBase.spacePreserve = Boolean.TRUE;
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x007f, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void parseAttributesGradient(com.caverock.androidsvg.SVG.GradientElement r4, org.xml.sax.Attributes r5) {
        /*
            r0 = 0
        L1:
            int r1 = r5.getLength()
            if (r0 >= r1) goto L82
            java.lang.String r1 = r5.getValue(r0)
            java.lang.String r1 = r1.trim()
            int[] r2 = com.caverock.androidsvg.SVGParser.AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr
            java.lang.String r3 = r5.getLocalName(r0)
            com.caverock.androidsvg.SVGParser$SVGAttr r3 = com.caverock.androidsvg.SVGParser.SVGAttr.fromString(r3)
            int r3 = r3.ordinal()
            r2 = r2[r3]
            r3 = 6
            if (r2 == r3) goto L65
            switch(r2) {
                case 32: goto L42;
                case 33: goto L3b;
                case 34: goto L26;
                default: goto L25;
            }
        L25:
            goto L7f
        L26:
            com.caverock.androidsvg.SVG$GradientSpread r2 = com.caverock.androidsvg.SVG.GradientSpread.valueOf(r1)     // Catch: java.lang.IllegalArgumentException -> L2d
            r4.spreadMethod = r2     // Catch: java.lang.IllegalArgumentException -> L2d
            goto L7f
        L2d:
            com.caverock.androidsvg.SVGParseException r4 = new com.caverock.androidsvg.SVGParseException
            java.lang.String r5 = "Invalid spreadMethod attribute. \""
            java.lang.String r0 = "\" is not a valid value."
            java.lang.String r5 = androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0.m(r5, r1, r0)
            r4.<init>(r5)
            throw r4
        L3b:
            android.graphics.Matrix r1 = parseTransformList(r1)
            r4.gradientTransform = r1
            goto L7f
        L42:
            java.lang.String r2 = "objectBoundingBox"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L4f
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r4.gradientUnitsAreUser = r1
            goto L7f
        L4f:
            java.lang.String r2 = "userSpaceOnUse"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L5d
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r4.gradientUnitsAreUser = r1
            goto L7f
        L5d:
            com.caverock.androidsvg.SVGParseException r4 = new com.caverock.androidsvg.SVGParseException
            java.lang.String r5 = "Invalid value for attribute gradientUnits"
            r4.<init>(r5)
            throw r4
        L65:
            java.lang.String r2 = ""
            java.lang.String r3 = r5.getURI(r0)
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L7d
            java.lang.String r2 = "http://www.w3.org/1999/xlink"
            java.lang.String r3 = r5.getURI(r0)
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L7f
        L7d:
            r4.href = r1
        L7f:
            int r0 = r0 + 1
            goto L1
        L82:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.parseAttributesGradient(com.caverock.androidsvg.SVG$GradientElement, org.xml.sax.Attributes):void");
    }

    public static void parseAttributesPolyLine(SVG.PolyLine polyLine, Attributes attributes, String str) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (SVGAttr.fromString(attributes.getLocalName(i)) == SVGAttr.points) {
                TextScanner textScanner = new TextScanner(attributes.getValue(i));
                ArrayList arrayList = new ArrayList();
                textScanner.skipWhitespace();
                while (!textScanner.empty()) {
                    float nextFloat = textScanner.nextFloat();
                    if (Float.isNaN(nextFloat)) {
                        throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid <", str, "> points attribute. Non-coordinate content found in list."));
                    }
                    textScanner.skipCommaWhitespace();
                    float nextFloat2 = textScanner.nextFloat();
                    if (Float.isNaN(nextFloat2)) {
                        throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid <", str, "> points attribute. There should be an even number of coordinates."));
                    }
                    textScanner.skipCommaWhitespace();
                    arrayList.add(Float.valueOf(nextFloat));
                    arrayList.add(Float.valueOf(nextFloat2));
                }
                polyLine.points = new float[arrayList.size()];
                int size = arrayList.size();
                int i2 = 0;
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    polyLine.points[i2] = ((Float) obj).floatValue();
                    i2++;
                }
            }
        }
    }

    public static void parseAttributesStyle(SVG.SvgElementBase svgElementBase, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            if (trim.length() != 0) {
                int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
                if (i2 == 45) {
                    TextScanner textScanner = new TextScanner(trim.replaceAll("/\\*.*?\\*/", ""));
                    while (true) {
                        String nextToken = textScanner.nextToken(':', false);
                        textScanner.skipWhitespace();
                        if (!textScanner.consume(':')) {
                            break;
                        }
                        textScanner.skipWhitespace();
                        String nextToken2 = textScanner.nextToken(';', true);
                        if (nextToken2 == null) {
                            break;
                        }
                        textScanner.skipWhitespace();
                        if (textScanner.empty() || textScanner.consume(';')) {
                            if (svgElementBase.style == null) {
                                svgElementBase.style = new SVG.Style();
                            }
                            processStyleProperty(svgElementBase.style, nextToken, nextToken2);
                            textScanner.skipWhitespace();
                        }
                    }
                } else if (i2 != 46) {
                    if (svgElementBase.baseStyle == null) {
                        svgElementBase.baseStyle = new SVG.Style();
                    }
                    processStyleProperty(svgElementBase.baseStyle, attributes.getLocalName(i), attributes.getValue(i).trim());
                } else {
                    CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(trim);
                    ArrayList arrayList = null;
                    while (!cSSTextScanner.empty()) {
                        String nextToken3 = cSSTextScanner.nextToken();
                        if (nextToken3 != null) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(nextToken3);
                            cSSTextScanner.skipWhitespace();
                        }
                    }
                    svgElementBase.classNames = arrayList;
                }
            }
        }
    }

    public static void parseAttributesTextPosition(SVG.TextPositionedContainer textPositionedContainer, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
            if (i2 == 1) {
                textPositionedContainer.x = parseLengthList(trim);
            } else if (i2 == 2) {
                textPositionedContainer.y = parseLengthList(trim);
            } else if (i2 == 19) {
                textPositionedContainer.dx = parseLengthList(trim);
            } else if (i2 == 20) {
                textPositionedContainer.dy = parseLengthList(trim);
            }
        }
    }

    public static void parseAttributesTransform(SVG.HasTransform hasTransform, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (SVGAttr.fromString(attributes.getLocalName(i)) == SVGAttr.transform) {
                hasTransform.setTransform(parseTransformList(attributes.getValue(i)));
            }
        }
    }

    public static void parseAttributesViewBox(SVG.SvgViewBoxContainer svgViewBoxContainer, Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String trim = attributes.getValue(i).trim();
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
            if (i2 == 7) {
                parsePreserveAspectRatio(svgViewBoxContainer, trim);
            } else if (i2 != 87) {
                continue;
            } else {
                TextScanner textScanner = new TextScanner(trim);
                textScanner.skipWhitespace();
                float nextFloat = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float nextFloat2 = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float nextFloat3 = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float nextFloat4 = textScanner.nextFloat();
                if (Float.isNaN(nextFloat) || Float.isNaN(nextFloat2) || Float.isNaN(nextFloat3) || Float.isNaN(nextFloat4)) {
                    throw new SVGParseException("Invalid viewBox definition - should have four numbers");
                }
                if (nextFloat3 < 0.0f) {
                    throw new SVGParseException("Invalid viewBox. width cannot be negative");
                }
                if (nextFloat4 < 0.0f) {
                    throw new SVGParseException("Invalid viewBox. height cannot be negative");
                }
                svgViewBoxContainer.viewBox = new SVG.Box(nextFloat, nextFloat2, nextFloat3, nextFloat4);
            }
        }
    }

    public static SVG.Colour parseColour(String str) {
        long j;
        int i;
        if (str.charAt(0) == '#') {
            int length = str.length();
            IntegerParser integerParser = null;
            if (1 < length) {
                long j2 = 0;
                int i2 = 1;
                while (i2 < length) {
                    char charAt = str.charAt(i2);
                    if (charAt < '0' || charAt > '9') {
                        if (charAt >= 'A' && charAt <= 'F') {
                            j = j2 * 16;
                            i = charAt - 'A';
                        } else {
                            if (charAt < 'a' || charAt > 'f') {
                                break;
                            }
                            j = j2 * 16;
                            i = charAt - 'a';
                        }
                        j2 = j + i + 10;
                    } else {
                        j2 = (j2 * 16) + (charAt - '0');
                    }
                    if (j2 > 4294967295L) {
                        break;
                    }
                    i2++;
                }
                if (i2 != 1) {
                    integerParser = new IntegerParser(j2, i2);
                }
            }
            if (integerParser == null) {
                throw new SVGParseException("Bad hex colour value: ".concat(str));
            }
            long j3 = integerParser.value;
            int i3 = integerParser.pos;
            if (i3 == 4) {
                int i4 = (int) j3;
                int i5 = i4 & 3840;
                int i6 = i4 & IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp;
                int i7 = i4 & 15;
                return new SVG.Colour(i7 | (i5 << 8) | (-16777216) | (i5 << 12) | (i6 << 8) | (i6 << 4) | (i7 << 4));
            }
            if (i3 != 5) {
                if (i3 == 7) {
                    return new SVG.Colour(((int) j3) | (-16777216));
                }
                if (i3 == 9) {
                    return new SVG.Colour((((int) j3) << 24) | (((int) j3) >>> 8));
                }
                throw new SVGParseException("Bad hex colour value: ".concat(str));
            }
            int i8 = (int) j3;
            int i9 = 61440 & i8;
            int i10 = i8 & 3840;
            int i11 = i8 & IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp;
            int i12 = i8 & 15;
            return new SVG.Colour((i12 << 24) | (i12 << 28) | (i9 << 8) | (i9 << 4) | (i10 << 4) | i10 | i11 | (i11 >> 4));
        }
        String lowerCase = str.toLowerCase(Locale.US);
        boolean startsWith = lowerCase.startsWith("rgba(");
        if (startsWith || lowerCase.startsWith("rgb(")) {
            TextScanner textScanner = new TextScanner(str.substring(startsWith ? 5 : 4));
            textScanner.skipWhitespace();
            float nextFloat = textScanner.nextFloat();
            if (!Float.isNaN(nextFloat) && textScanner.consume('%')) {
                nextFloat = (nextFloat * 256.0f) / 100.0f;
            }
            float checkedNextFloat = textScanner.checkedNextFloat(nextFloat);
            if (!Float.isNaN(checkedNextFloat) && textScanner.consume('%')) {
                checkedNextFloat = (checkedNextFloat * 256.0f) / 100.0f;
            }
            float checkedNextFloat2 = textScanner.checkedNextFloat(checkedNextFloat);
            if (!Float.isNaN(checkedNextFloat2) && textScanner.consume('%')) {
                checkedNextFloat2 = (checkedNextFloat2 * 256.0f) / 100.0f;
            }
            if (!startsWith) {
                textScanner.skipWhitespace();
                if (Float.isNaN(checkedNextFloat2) || !textScanner.consume(')')) {
                    throw new SVGParseException("Bad rgb() colour value: ".concat(str));
                }
                return new SVG.Colour((clamp255(nextFloat) << 16) | (-16777216) | (clamp255(checkedNextFloat) << 8) | clamp255(checkedNextFloat2));
            }
            float checkedNextFloat3 = textScanner.checkedNextFloat(checkedNextFloat2);
            textScanner.skipWhitespace();
            if (Float.isNaN(checkedNextFloat3) || !textScanner.consume(')')) {
                throw new SVGParseException("Bad rgba() colour value: ".concat(str));
            }
            return new SVG.Colour((clamp255(checkedNextFloat3 * 256.0f) << 24) | (clamp255(nextFloat) << 16) | (clamp255(checkedNextFloat) << 8) | clamp255(checkedNextFloat2));
        }
        boolean startsWith2 = lowerCase.startsWith("hsla(");
        if (!startsWith2 && !lowerCase.startsWith("hsl(")) {
            Integer num = (Integer) ((HashMap) ColourKeywords.colourKeywords).get(lowerCase);
            if (num != null) {
                return new SVG.Colour(num.intValue());
            }
            throw new SVGParseException("Invalid colour keyword: ".concat(lowerCase));
        }
        TextScanner textScanner2 = new TextScanner(str.substring(startsWith2 ? 5 : 4));
        textScanner2.skipWhitespace();
        float nextFloat2 = textScanner2.nextFloat();
        float checkedNextFloat4 = textScanner2.checkedNextFloat(nextFloat2);
        if (!Float.isNaN(checkedNextFloat4)) {
            textScanner2.consume('%');
        }
        float checkedNextFloat5 = textScanner2.checkedNextFloat(checkedNextFloat4);
        if (!Float.isNaN(checkedNextFloat5)) {
            textScanner2.consume('%');
        }
        if (!startsWith2) {
            textScanner2.skipWhitespace();
            if (Float.isNaN(checkedNextFloat5) || !textScanner2.consume(')')) {
                throw new SVGParseException("Bad hsl() colour value: ".concat(str));
            }
            return new SVG.Colour(hslToRgb(nextFloat2, checkedNextFloat4, checkedNextFloat5) | (-16777216));
        }
        float checkedNextFloat6 = textScanner2.checkedNextFloat(checkedNextFloat5);
        textScanner2.skipWhitespace();
        if (Float.isNaN(checkedNextFloat6) || !textScanner2.consume(')')) {
            throw new SVGParseException("Bad hsla() colour value: ".concat(str));
        }
        return new SVG.Colour((clamp255(checkedNextFloat6 * 256.0f) << 24) | hslToRgb(nextFloat2, checkedNextFloat4, checkedNextFloat5));
    }

    public static float parseFloat(String str) {
        int length = str.length();
        if (length != 0) {
            return parseFloat(length, str);
        }
        throw new SVGParseException("Invalid float value (empty string)");
    }

    public static List parseFontFamily(String str) {
        TextScanner textScanner = new TextScanner(str);
        ArrayList arrayList = null;
        do {
            String nextQuotedString = textScanner.nextQuotedString();
            if (nextQuotedString == null) {
                nextQuotedString = textScanner.nextToken(',', true);
            }
            if (nextQuotedString == null) {
                return arrayList;
            }
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.add(nextQuotedString);
            textScanner.skipCommaWhitespace();
        } while (!textScanner.empty());
        return arrayList;
    }

    public static String parseFunctionalIRI(String str) {
        if (!str.equals(SignalSeverity.NONE) && str.startsWith("url(")) {
            return str.endsWith(")") ? str.substring(4, str.length() - 1).trim() : str.substring(4).trim();
        }
        return null;
    }

    public static SVG.Length parseLength(String str) {
        if (str.length() == 0) {
            throw new SVGParseException("Invalid length value (empty string)");
        }
        int length = str.length();
        SVG.Unit unit = SVG.Unit.px;
        char charAt = str.charAt(length - 1);
        if (charAt == '%') {
            length--;
            unit = SVG.Unit.percent;
        } else if (length > 2 && Character.isLetter(charAt) && Character.isLetter(str.charAt(length - 2))) {
            length -= 2;
            try {
                unit = SVG.Unit.valueOf(str.substring(length).toLowerCase(Locale.US));
            } catch (IllegalArgumentException unused) {
                throw new SVGParseException("Invalid length unit specifier: ".concat(str));
            }
        }
        try {
            return new SVG.Length(parseFloat(length, str), unit);
        } catch (NumberFormatException e) {
            throw new SVGParseException("Invalid length value: ".concat(str), e);
        }
    }

    public static List parseLengthList(String str) {
        String str2;
        if (str.length() == 0) {
            throw new SVGParseException("Invalid length list (empty string)");
        }
        ArrayList arrayList = new ArrayList(1);
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        while (!textScanner.empty()) {
            float nextFloat = textScanner.nextFloat();
            if (Float.isNaN(nextFloat)) {
                StringBuilder sb = new StringBuilder("Invalid length list value: ");
                int i = textScanner.position;
                while (true) {
                    boolean empty = textScanner.empty();
                    str2 = textScanner.input;
                    if (empty || TextScanner.isWhitespace(str2.charAt(textScanner.position))) {
                        break;
                    }
                    textScanner.position++;
                }
                String substring = str2.substring(i, textScanner.position);
                textScanner.position = i;
                sb.append(substring);
                throw new SVGParseException(sb.toString());
            }
            SVG.Unit nextUnit = textScanner.nextUnit();
            if (nextUnit == null) {
                nextUnit = SVG.Unit.px;
            }
            arrayList.add(new SVG.Length(nextFloat, nextUnit));
            textScanner.skipCommaWhitespace();
        }
        return arrayList;
    }

    public static SVG.Length parseLengthOrAuto(TextScanner textScanner) {
        return textScanner.consume("auto") ? new SVG.Length(0.0f) : textScanner.nextLength();
    }

    public static Float parseOpacity(String str) {
        try {
            float parseFloat = parseFloat(str);
            float f = 0.0f;
            if (parseFloat >= 0.0f) {
                f = 1.0f;
                if (parseFloat > 1.0f) {
                }
                return Float.valueOf(parseFloat);
            }
            parseFloat = f;
            return Float.valueOf(parseFloat);
        } catch (SVGParseException unused) {
            return null;
        }
    }

    public static SVG.SvgPaint parsePaintSpecifier(String str) {
        SVG.SvgPaint svgPaint = null;
        if (!str.startsWith("url(")) {
            if (str.equals(SignalSeverity.NONE)) {
                return SVG.Colour.TRANSPARENT;
            }
            if (str.equals("currentColor")) {
                return SVG.CurrentColor.instance;
            }
            try {
                return parseColour(str);
            } catch (SVGParseException unused) {
                return null;
            }
        }
        int indexOf = str.indexOf(")");
        if (indexOf == -1) {
            return new SVG.PaintReference(str.substring(4).trim(), null);
        }
        String trim = str.substring(4, indexOf).trim();
        String trim2 = str.substring(indexOf + 1).trim();
        if (trim2.length() > 0) {
            if (trim2.equals(SignalSeverity.NONE)) {
                svgPaint = SVG.Colour.TRANSPARENT;
            } else if (trim2.equals("currentColor")) {
                svgPaint = SVG.CurrentColor.instance;
            } else {
                try {
                    svgPaint = parseColour(trim2);
                } catch (SVGParseException unused2) {
                }
            }
        }
        return new SVG.PaintReference(trim, svgPaint);
    }

    public static void parsePreserveAspectRatio(SVG.SvgPreserveAspectRatioContainer svgPreserveAspectRatioContainer, String str) {
        PreserveAspectRatio.Scale scale;
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        String nextToken = textScanner.nextToken();
        if ("defer".equals(nextToken)) {
            textScanner.skipWhitespace();
            nextToken = textScanner.nextToken();
        }
        PreserveAspectRatio.Alignment alignment = (PreserveAspectRatio.Alignment) ((HashMap) AspectRatioKeywords.aspectRatioKeywords).get(nextToken);
        textScanner.skipWhitespace();
        if (textScanner.empty()) {
            scale = null;
        } else {
            String nextToken2 = textScanner.nextToken();
            nextToken2.getClass();
            if (nextToken2.equals("meet")) {
                scale = PreserveAspectRatio.Scale.meet;
            } else {
                if (!nextToken2.equals("slice")) {
                    throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid preserveAspectRatio definition: ", str));
                }
                scale = PreserveAspectRatio.Scale.slice;
            }
        }
        svgPreserveAspectRatioContainer.preserveAspectRatio = new PreserveAspectRatio(alignment, scale);
    }

    public static Map parseProcessingInstructionAttributes(TextScanner textScanner) {
        HashMap hashMap = new HashMap();
        textScanner.skipWhitespace();
        String nextToken = textScanner.nextToken('=', false);
        while (nextToken != null) {
            textScanner.consume('=');
            hashMap.put(nextToken, textScanner.nextQuotedString());
            textScanner.skipWhitespace();
            nextToken = textScanner.nextToken('=', false);
        }
        return hashMap;
    }

    public static Matrix parseTransformList(String str) {
        Matrix matrix = new Matrix();
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        while (!textScanner.empty()) {
            String str2 = null;
            if (!textScanner.empty()) {
                int i = textScanner.position;
                String str3 = textScanner.input;
                int charAt = str3.charAt(i);
                while (true) {
                    if ((charAt >= 97 && charAt <= 122) || (charAt >= 65 && charAt <= 90)) {
                        charAt = textScanner.advanceChar();
                    }
                }
                int i2 = textScanner.position;
                while (TextScanner.isWhitespace(charAt)) {
                    charAt = textScanner.advanceChar();
                }
                if (charAt == 40) {
                    textScanner.position++;
                    str2 = str3.substring(i, i2);
                } else {
                    textScanner.position = i;
                }
            }
            if (str2 == null) {
                throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Bad transform function encountered in transform list: ", str));
            }
            switch (str2) {
                case "matrix":
                    textScanner.skipWhitespace();
                    float nextFloat = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat2 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat3 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat4 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat5 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float nextFloat6 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat6) && textScanner.consume(')')) {
                        Matrix matrix2 = new Matrix();
                        matrix2.setValues(new float[]{nextFloat, nextFloat3, nextFloat5, nextFloat2, nextFloat4, nextFloat6, 0.0f, 0.0f, 1.0f});
                        matrix.preConcat(matrix2);
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "rotate":
                    textScanner.skipWhitespace();
                    float nextFloat7 = textScanner.nextFloat();
                    float possibleNextFloat = textScanner.possibleNextFloat();
                    float possibleNextFloat2 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (Float.isNaN(nextFloat7) || !textScanner.consume(')')) {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    if (Float.isNaN(possibleNextFloat)) {
                        matrix.preRotate(nextFloat7);
                        break;
                    } else if (!Float.isNaN(possibleNextFloat2)) {
                        matrix.preRotate(nextFloat7, possibleNextFloat, possibleNextFloat2);
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "scale":
                    textScanner.skipWhitespace();
                    float nextFloat8 = textScanner.nextFloat();
                    float possibleNextFloat3 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat8) && textScanner.consume(')')) {
                        if (!Float.isNaN(possibleNextFloat3)) {
                            matrix.preScale(nextFloat8, possibleNextFloat3);
                            break;
                        } else {
                            matrix.preScale(nextFloat8, nextFloat8);
                            break;
                        }
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "skewX":
                    textScanner.skipWhitespace();
                    float nextFloat9 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat9) && textScanner.consume(')')) {
                        matrix.preSkew((float) Math.tan(Math.toRadians(nextFloat9)), 0.0f);
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "skewY":
                    textScanner.skipWhitespace();
                    float nextFloat10 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat10) && textScanner.consume(')')) {
                        matrix.preSkew(0.0f, (float) Math.tan(Math.toRadians(nextFloat10)));
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "translate":
                    textScanner.skipWhitespace();
                    float nextFloat11 = textScanner.nextFloat();
                    float possibleNextFloat4 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(nextFloat11) && textScanner.consume(')')) {
                        if (!Float.isNaN(possibleNextFloat4)) {
                            matrix.preTranslate(nextFloat11, possibleNextFloat4);
                            break;
                        } else {
                            matrix.preTranslate(nextFloat11, 0.0f);
                            break;
                        }
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                default:
                    throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid transform list fn: ", str2, ")"));
            }
            if (textScanner.empty()) {
                return matrix;
            }
            textScanner.skipCommaWhitespace();
        }
        return matrix;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void processStyleProperty(com.caverock.androidsvg.SVG.Style r17, java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 1930
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.processStyleProperty(com.caverock.androidsvg.SVG$Style, java.lang.String, java.lang.String):void");
    }

    public final void appendToTextContainer(String str) {
        SVG.SvgConditionalContainer svgConditionalContainer = (SVG.SvgConditionalContainer) this.currentElement;
        int size = svgConditionalContainer.children.size();
        SVG.SvgObject svgObject = size == 0 ? null : (SVG.SvgObject) svgConditionalContainer.children.get(size - 1);
        if (!(svgObject instanceof SVG.TextSequence)) {
            this.currentElement.addChild(new SVG.TextSequence(str));
        } else {
            SVG.TextSequence textSequence = (SVG.TextSequence) svgObject;
            textSequence.text = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), textSequence.text, str);
        }
    }

    public final void endElement(String str, String str2, String str3) {
        if (this.ignoring) {
            int i = this.ignoreDepth - 1;
            this.ignoreDepth = i;
            if (i == 0) {
                this.ignoring = false;
                return;
            }
        }
        if ("http://www.w3.org/2000/svg".equals(str) || "".equals(str)) {
            if (str2.length() <= 0) {
                str2 = str3;
            }
            int[] iArr = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem;
            SVGElem sVGElem = (SVGElem) ((HashMap) SVGElem.cache).get(str2);
            if (sVGElem == null) {
                sVGElem = SVGElem.UNSUPPORTED;
            }
            int i2 = iArr[sVGElem.ordinal()];
            if (i2 != 1 && i2 != 2 && i2 != 4 && i2 != 5 && i2 != 13 && i2 != 14) {
                switch (i2) {
                    case 22:
                    case 23:
                        this.inMetadataElement = false;
                        if (this.metadataElementContents != null) {
                            SVGElem sVGElem2 = this.metadataTag;
                            if (sVGElem2 == SVGElem.title) {
                                this.svgDocument.getClass();
                            } else if (sVGElem2 == SVGElem.desc) {
                                this.svgDocument.getClass();
                            }
                            this.metadataElementContents.setLength(0);
                            break;
                        }
                        break;
                    case 30:
                        StringBuilder sb = this.styleElementContents;
                        if (sb != null) {
                            this.inStyleElement = false;
                            String sb2 = sb.toString();
                            CSSParser cSSParser = new CSSParser(CSSParser.MediaType.screen, CSSParser.Source.Document);
                            SVG svg = this.svgDocument;
                            CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(sb2);
                            cSSTextScanner.skipWhitespace();
                            svg.cssRules.addAll(cSSParser.parseRuleset(cSSTextScanner));
                            this.styleElementContents.setLength(0);
                            break;
                        }
                        break;
                }
                return;
            }
            this.currentElement = ((SVG.SvgObject) this.currentElement).parent;
        }
    }

    public final void parseUsingSAX(InputStream inputStream) {
        Log.d("SVGParser", "Falling back to SAX parser");
        try {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setFeature("http://xml.org/sax/features/external-general-entities", false);
            newInstance.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            XMLReader xMLReader = newInstance.newSAXParser().getXMLReader();
            SAXHandler sAXHandler = new SAXHandler(this, null);
            xMLReader.setContentHandler(sAXHandler);
            xMLReader.setProperty("http://xml.org/sax/properties/lexical-handler", sAXHandler);
            xMLReader.parse(new InputSource(inputStream));
        } catch (IOException e) {
            throw new SVGParseException("Stream error", e);
        } catch (ParserConfigurationException e2) {
            throw new SVGParseException("XML parser problem", e2);
        } catch (SAXException e3) {
            throw new SVGParseException("SVG parse error", e3);
        }
    }

    public final void parseUsingXmlPullParser(InputStream inputStream) {
        try {
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                XPPAttributesWrapper xPPAttributesWrapper = new XPPAttributesWrapper(this, newPullParser);
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-docdecl", false);
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                newPullParser.setInput(inputStream, null);
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.nextToken()) {
                    if (eventType == 0) {
                        this.svgDocument = new SVG();
                    } else if (eventType == 8) {
                        Log.d("SVGParser", "PROC INSTR: " + newPullParser.getText());
                        TextScanner textScanner = new TextScanner(newPullParser.getText());
                        String nextToken = textScanner.nextToken();
                        parseProcessingInstructionAttributes(textScanner);
                        nextToken.equals("xml-stylesheet");
                    } else if (eventType != 10) {
                        if (eventType == 2) {
                            String name = newPullParser.getName();
                            if (newPullParser.getPrefix() != null) {
                                name = newPullParser.getPrefix() + ':' + name;
                            }
                            startElement(newPullParser.getNamespace(), newPullParser.getName(), name, xPPAttributesWrapper);
                        } else if (eventType == 3) {
                            String name2 = newPullParser.getName();
                            if (newPullParser.getPrefix() != null) {
                                name2 = newPullParser.getPrefix() + ':' + name2;
                            }
                            endElement(newPullParser.getNamespace(), newPullParser.getName(), name2);
                        } else if (eventType == 4) {
                            int[] iArr = new int[2];
                            text(newPullParser.getTextCharacters(iArr), iArr[0], iArr[1]);
                        } else if (eventType == 5) {
                            text(newPullParser.getText());
                        }
                    } else if (this.svgDocument.rootElement == null && newPullParser.getText().contains("<!ENTITY ")) {
                        try {
                            Log.d("SVGParser", "Switching to SAX parser to process entities");
                            inputStream.reset();
                            parseUsingSAX(inputStream);
                            return;
                        } catch (IOException unused) {
                            Log.w("SVGParser", "Detected internal entity definitions, but could not parse them.");
                            return;
                        }
                    }
                }
            } catch (IOException e) {
                throw new SVGParseException("Stream error", e);
            }
        } catch (XmlPullParserException e2) {
            throw new SVGParseException("XML parser problem", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x032d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void path(org.xml.sax.Attributes r25) {
        /*
            Method dump skipped, instructions count: 918
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.path(org.xml.sax.Attributes):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:206:0x036c, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x058b, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startElement(java.lang.String r17, java.lang.String r18, java.lang.String r19, org.xml.sax.Attributes r20) {
        /*
            Method dump skipped, instructions count: 3256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGParser.startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes):void");
    }

    public final void text(String str) {
        if (this.ignoring) {
            return;
        }
        if (this.inMetadataElement) {
            if (this.metadataElementContents == null) {
                this.metadataElementContents = new StringBuilder(str.length());
            }
            this.metadataElementContents.append(str);
        } else if (this.inStyleElement) {
            if (this.styleElementContents == null) {
                this.styleElementContents = new StringBuilder(str.length());
            }
            this.styleElementContents.append(str);
        } else if (this.currentElement instanceof SVG.TextContainer) {
            appendToTextContainer(str);
        }
    }

    public static float parseFloat(int i, String str) {
        float parseNumber = new NumberParser().parseNumber(0, i, str);
        if (Float.isNaN(parseNumber)) {
            throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid float value: ", str));
        }
        return parseNumber;
    }

    public final void text(char[] cArr, int i, int i2) {
        if (this.ignoring) {
            return;
        }
        if (this.inMetadataElement) {
            if (this.metadataElementContents == null) {
                this.metadataElementContents = new StringBuilder(i2);
            }
            this.metadataElementContents.append(cArr, i, i2);
        } else if (this.inStyleElement) {
            if (this.styleElementContents == null) {
                this.styleElementContents = new StringBuilder(i2);
            }
            this.styleElementContents.append(cArr, i, i2);
        } else if (this.currentElement instanceof SVG.TextContainer) {
            appendToTextContainer(new String(cArr, i, i2));
        }
    }
}
