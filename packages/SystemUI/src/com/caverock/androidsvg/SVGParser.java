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

    public class AspectRatioKeywords {
        public static final Map aspectRatioKeywords;

        static {
            HashMap map = new HashMap(10);
            aspectRatioKeywords = map;
            map.put(SignalSeverity.NONE, PreserveAspectRatio.Alignment.none);
            map.put("xMinYMin", PreserveAspectRatio.Alignment.xMinYMin);
            map.put("xMidYMin", PreserveAspectRatio.Alignment.xMidYMin);
            map.put("xMaxYMin", PreserveAspectRatio.Alignment.xMaxYMin);
            map.put("xMinYMid", PreserveAspectRatio.Alignment.xMinYMid);
            map.put("xMidYMid", PreserveAspectRatio.Alignment.xMidYMid);
            map.put("xMaxYMid", PreserveAspectRatio.Alignment.xMaxYMid);
            map.put("xMinYMax", PreserveAspectRatio.Alignment.xMinYMax);
            map.put("xMidYMax", PreserveAspectRatio.Alignment.xMidYMax);
            map.put("xMaxYMax", PreserveAspectRatio.Alignment.xMaxYMax);
        }

        private AspectRatioKeywords() {
        }
    }

    public class ColourKeywords {
        public static final Map colourKeywords;

        static {
            HashMap map = new HashMap(47);
            colourKeywords = map;
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-984833, map, "aliceblue", -332841, "antiquewhite");
            map.put("aqua", -16711681);
            map.put("aquamarine", -8388652);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-983041, map, "azure", -657956, "beige");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-6972, map, "bisque", -16777216, "black");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-5171, map, "blanchedalmond", -16776961, "blue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7722014, map, "blueviolet", -5952982, "brown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-2180985, map, "burlywood", -10510688, "cadetblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8388864, map, "chartreuse", -2987746, "chocolate");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-32944, map, "coral", -10185235, "cornflowerblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1828, map, "cornsilk", -2354116, "crimson");
            map.put("cyan", -16711681);
            map.put("darkblue", -16777077);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16741493, map, "darkcyan", -4684277, "darkgoldenrod");
            map.put("darkgray", -5658199);
            map.put("darkgreen", -16751616);
            map.put("darkgrey", -5658199);
            map.put("darkkhaki", -4343957);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7667573, map, "darkmagenta", -11179217, "darkolivegreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-29696, map, "darkorange", -6737204, "darkorchid");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7667712, map, "darkred", -1468806, "darksalmon");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7357297, map, "darkseagreen", -12042869, "darkslateblue");
            map.put("darkslategray", -13676721);
            map.put("darkslategrey", -13676721);
            map.put("darkturquoise", -16724271);
            map.put("darkviolet", -7077677);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-60269, map, "deeppink", -16728065, "deepskyblue");
            map.put("dimgray", -9868951);
            map.put("dimgrey", -9868951);
            map.put("dodgerblue", -14774017);
            map.put("firebrick", -5103070);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1296, map, "floralwhite", -14513374, "forestgreen");
            map.put("fuchsia", -65281);
            map.put("gainsboro", -2302756);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-460545, map, "ghostwhite", -10496, "gold");
            map.put("goldenrod", -2448096);
            map.put("gray", -8355712);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16744448, map, "green", -5374161, "greenyellow");
            map.put("grey", -8355712);
            map.put("honeydew", -983056);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-38476, map, "hotpink", -3318692, "indianred");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-11861886, map, "indigo", -16, "ivory");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-989556, map, "khaki", -1644806, "lavender");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-3851, map, "lavenderblush", -8586240, "lawngreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1331, map, "lemonchiffon", -5383962, "lightblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1015680, map, "lightcoral", -2031617, "lightcyan");
            map.put("lightgoldenrodyellow", -329006);
            map.put("lightgray", -2894893);
            map.put("lightgreen", -7278960);
            map.put("lightgrey", -2894893);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-18751, map, "lightpink", -24454, "lightsalmon");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-14634326, map, "lightseagreen", -7876870, "lightskyblue");
            map.put("lightslategray", -8943463);
            map.put("lightslategrey", -8943463);
            map.put("lightsteelblue", -5192482);
            map.put("lightyellow", -32);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16711936, map, "lime", -13447886, "limegreen");
            map.put("linen", -331546);
            map.put("magenta", -65281);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8388608, map, "maroon", -10039894, "mediumaquamarine");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16777011, map, "mediumblue", -4565549, "mediumorchid");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7114533, map, "mediumpurple", -12799119, "mediumseagreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8689426, map, "mediumslateblue", -16713062, "mediumspringgreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-12004916, map, "mediumturquoise", -3730043, "mediumvioletred");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-15132304, map, "midnightblue", -655366, "mintcream");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-6943, map, "mistyrose", -6987, "moccasin");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8531, map, "navajowhite", -16777088, "navy");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-133658, map, "oldlace", -8355840, "olive");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-9728477, map, "olivedrab", -23296, "orange");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-47872, map, "orangered", -2461482, "orchid");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1120086, map, "palegoldenrod", -6751336, "palegreen");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-5247250, map, "paleturquoise", -2396013, "palevioletred");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-4139, map, "papayawhip", -9543, "peachpuff");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-3308225, map, "peru", -16181, "pink");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-2252579, map, "plum", -5185306, "powderblue");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-8388480, map, "purple", -10079335, "rebeccapurple");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-65536, map, "red", -4419697, "rosybrown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-12490271, map, "royalblue", -7650029, "saddlebrown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-360334, map, "salmon", -744352, "sandybrown");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-13726889, map, "seagreen", -2578, "seashell");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-6270419, map, "sienna", -4144960, "silver");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-7876885, map, "skyblue", -9807155, "slateblue");
            map.put("slategray", -9404272);
            map.put("slategrey", -9404272);
            map.put("snow", -1286);
            map.put("springgreen", -16711809);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-12156236, map, "steelblue", -2968436, "tan");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-16744320, map, "teal", -2572328, "thistle");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-40121, map, "tomato", -12525360, "turquoise");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1146130, map, "violet", -663885, "wheat");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-1, map, "white", -657931, "whitesmoke");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(-256, map, "yellow", -6632142, "yellowgreen");
            map.put("transparent", 0);
        }

        private ColourKeywords() {
        }
    }

    public class FontSizeKeywords {
        public static final Map fontSizeKeywords;

        static {
            HashMap map = new HashMap(9);
            fontSizeKeywords = map;
            SVG.Unit unit = SVG.Unit.pt;
            map.put("xx-small", new SVG.Length(0.694f, unit));
            map.put("x-small", new SVG.Length(0.833f, unit));
            map.put("small", new SVG.Length(10.0f, unit));
            map.put("medium", new SVG.Length(12.0f, unit));
            map.put("large", new SVG.Length(14.4f, unit));
            map.put("x-large", new SVG.Length(17.3f, unit));
            map.put("xx-large", new SVG.Length(20.7f, unit));
            SVG.Unit unit2 = SVG.Unit.percent;
            map.put("smaller", new SVG.Length(83.33f, unit2));
            map.put("larger", new SVG.Length(120.0f, unit2));
        }

        private FontSizeKeywords() {
        }
    }

    public class FontWeightKeywords {
        public static final Map fontWeightKeywords;

        static {
            HashMap map = new HashMap(13);
            fontWeightKeywords = map;
            map.put(SystemUIAnalytics.QPNE_VID_NORMAL, 400);
            Integer numValueOf = Integer.valueOf(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
            map.put("bold", numValueOf);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(1, map, "bolder", -1, "lighter");
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(100, map, DATA.DM_FIELD_INDEX.UT_PDN, 200, SystemUIAnalytics.SID_WALLPAPER_LOCK_COMMON_PREVIEW);
            map.put("300", 300);
            map.put("400", 400);
            SubtitleArrayMapping$$ExternalSyntheticOutline0.m(500, map, SystemUIAnalytics.SID_SUBSCREEN_NORMAL, VolteConstants.ErrorCode.BUSY_EVERYWHERE, SystemUIAnalytics.SID_WALLPAPER_VIDEO_PREVIEW);
            map.put("700", numValueOf);
            map.put(SystemUIAnalytics.SID_TOUCH_LOCKSCREEN_RESIZABLE, 800);
            map.put("900", 900);
        }

        private FontWeightKeywords() {
        }
    }

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
        public final void startElement(String str, String str2, String str3, Attributes attributes) throws SVGParseException {
            SVGParser.this.startElement(str, str2, str3, attributes);
        }

        public /* synthetic */ SAXHandler(SVGParser sVGParser, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

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

    public class TextScanner {
        public final String input;
        public final int inputLength;
        public int position = 0;
        public final NumberParser numberParser = new NumberParser();

        public TextScanner(String str) {
            this.inputLength = 0;
            String strTrim = str.trim();
            this.input = strTrim;
            this.inputLength = strTrim.length();
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
            char cCharAt = this.input.charAt(i);
            if (cCharAt != '0' && cCharAt != '1') {
                return null;
            }
            this.position++;
            return Boolean.valueOf(cCharAt == '1');
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
            float number = numberParser.parseNumber(i, i2, this.input);
            if (!Float.isNaN(number)) {
                this.position = numberParser.pos;
            }
            return number;
        }

        public final SVG.Length nextLength() {
            float fNextFloat = nextFloat();
            if (Float.isNaN(fNextFloat)) {
                return null;
            }
            SVG.Unit unitNextUnit = nextUnit();
            return unitNextUnit == null ? new SVG.Length(fNextFloat, SVG.Unit.px) : new SVG.Length(fNextFloat, unitNextUnit);
        }

        public final String nextQuotedString() {
            if (empty()) {
                return null;
            }
            int i = this.position;
            String str = this.input;
            char cCharAt = str.charAt(i);
            if (cCharAt != '\'' && cCharAt != '\"') {
                return null;
            }
            int iAdvanceChar = advanceChar();
            while (iAdvanceChar != -1 && iAdvanceChar != cCharAt) {
                iAdvanceChar = advanceChar();
            }
            if (iAdvanceChar == -1) {
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
                SVG.Unit unitValueOf = SVG.Unit.valueOf(str.substring(i2, i2 + 2).toLowerCase(Locale.US));
                this.position += 2;
                return unitValueOf;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        public final float possibleNextFloat() {
            skipCommaWhitespace();
            int i = this.position;
            int i2 = this.inputLength;
            NumberParser numberParser = this.numberParser;
            float number = numberParser.parseNumber(i, i2, this.input);
            if (!Float.isNaN(number)) {
                this.position = numberParser.pos;
            }
            return number;
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
            char cCharAt = str.charAt(i);
            if ((!z && isWhitespace(cCharAt)) || cCharAt == c) {
                return null;
            }
            int i2 = this.position;
            int iAdvanceChar = advanceChar();
            while (iAdvanceChar != -1 && iAdvanceChar != c && (z || !isWhitespace(iAdvanceChar))) {
                iAdvanceChar = advanceChar();
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
            String strTrim = attributes.getValue(i).trim();
            switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()]) {
                case 21:
                    TextScanner textScanner = new TextScanner(strTrim);
                    HashSet hashSet = new HashSet();
                    while (!textScanner.empty()) {
                        String strNextToken = textScanner.nextToken();
                        if (strNextToken.startsWith("http://www.w3.org/TR/SVG11/feature#")) {
                            hashSet.add(strNextToken.substring(35));
                        } else {
                            hashSet.add("UNSUPPORTED");
                        }
                        textScanner.skipWhitespace();
                    }
                    svgConditional.setRequiredFeatures(hashSet);
                    break;
                case 22:
                    svgConditional.setRequiredExtensions(strTrim);
                    break;
                case 23:
                    TextScanner textScanner2 = new TextScanner(strTrim);
                    HashSet hashSet2 = new HashSet();
                    while (!textScanner2.empty()) {
                        String strNextToken2 = textScanner2.nextToken();
                        int iIndexOf = strNextToken2.indexOf(45);
                        if (iIndexOf != -1) {
                            strNextToken2 = strNextToken2.substring(0, iIndexOf);
                        }
                        hashSet2.add(new Locale(strNextToken2, "", "").getLanguage());
                        textScanner2.skipWhitespace();
                    }
                    svgConditional.setSystemLanguage(hashSet2);
                    break;
                case 24:
                    TextScanner textScanner3 = new TextScanner(strTrim);
                    HashSet hashSet3 = new HashSet();
                    while (!textScanner3.empty()) {
                        hashSet3.add(textScanner3.nextToken());
                        textScanner3.skipWhitespace();
                    }
                    svgConditional.setRequiredFormats(hashSet3);
                    break;
                case 25:
                    List fontFamily = parseFontFamily(strTrim);
                    svgConditional.setRequiredFonts(fontFamily != null ? new HashSet(fontFamily) : new HashSet(0));
                    break;
            }
        }
    }

    public static void parseAttributesCore(SVG.SvgElementBase svgElementBase, Attributes attributes) throws SVGParseException {
        for (int i = 0; i < attributes.getLength(); i++) {
            String qName = attributes.getQName(i);
            if (qName.equals("id") || qName.equals("xml:id")) {
                svgElementBase.id = attributes.getValue(i).trim();
                return;
            }
            if (qName.equals("xml:space")) {
                String strTrim = attributes.getValue(i).trim();
                if ("default".equals(strTrim)) {
                    svgElementBase.spacePreserve = Boolean.FALSE;
                    return;
                } else {
                    if (!"preserve".equals(strTrim)) {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid value for \"xml:space\" attribute: ", strTrim));
                    }
                    svgElementBase.spacePreserve = Boolean.TRUE;
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x007f, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void parseAttributesGradient(SVG.GradientElement gradientElement, Attributes attributes) throws SVGParseException {
        for (int i = 0; i < attributes.getLength(); i++) {
            String strTrim = attributes.getValue(i).trim();
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
            if (i2 != 6) {
                switch (i2) {
                    case 32:
                        if (!"objectBoundingBox".equals(strTrim)) {
                            if (!"userSpaceOnUse".equals(strTrim)) {
                                throw new SVGParseException("Invalid value for attribute gradientUnits");
                            }
                            gradientElement.gradientUnitsAreUser = Boolean.TRUE;
                            break;
                        } else {
                            gradientElement.gradientUnitsAreUser = Boolean.FALSE;
                            break;
                        }
                    case 33:
                        gradientElement.gradientTransform = parseTransformList(strTrim);
                        break;
                    case 34:
                        try {
                            gradientElement.spreadMethod = SVG.GradientSpread.valueOf(strTrim);
                            break;
                        } catch (IllegalArgumentException unused) {
                            throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid spreadMethod attribute. \"", strTrim, "\" is not a valid value."));
                        }
                }
            } else if ("".equals(attributes.getURI(i)) || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i))) {
                gradientElement.href = strTrim;
            }
        }
    }

    public static void parseAttributesPolyLine(SVG.PolyLine polyLine, Attributes attributes, String str) throws SVGParseException {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (SVGAttr.fromString(attributes.getLocalName(i)) == SVGAttr.points) {
                TextScanner textScanner = new TextScanner(attributes.getValue(i));
                ArrayList arrayList = new ArrayList();
                textScanner.skipWhitespace();
                while (!textScanner.empty()) {
                    float fNextFloat = textScanner.nextFloat();
                    if (Float.isNaN(fNextFloat)) {
                        throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid <", str, "> points attribute. Non-coordinate content found in list."));
                    }
                    textScanner.skipCommaWhitespace();
                    float fNextFloat2 = textScanner.nextFloat();
                    if (Float.isNaN(fNextFloat2)) {
                        throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid <", str, "> points attribute. There should be an even number of coordinates."));
                    }
                    textScanner.skipCommaWhitespace();
                    arrayList.add(Float.valueOf(fNextFloat));
                    arrayList.add(Float.valueOf(fNextFloat2));
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
            String strTrim = attributes.getValue(i).trim();
            if (strTrim.length() != 0) {
                int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
                if (i2 == 45) {
                    TextScanner textScanner = new TextScanner(strTrim.replaceAll("/\\*.*?\\*/", ""));
                    while (true) {
                        String strNextToken = textScanner.nextToken(':', false);
                        textScanner.skipWhitespace();
                        if (!textScanner.consume(':')) {
                            break;
                        }
                        textScanner.skipWhitespace();
                        String strNextToken2 = textScanner.nextToken(';', true);
                        if (strNextToken2 == null) {
                            break;
                        }
                        textScanner.skipWhitespace();
                        if (textScanner.empty() || textScanner.consume(';')) {
                            if (svgElementBase.style == null) {
                                svgElementBase.style = new SVG.Style();
                            }
                            processStyleProperty(svgElementBase.style, strNextToken, strNextToken2);
                            textScanner.skipWhitespace();
                        }
                    }
                } else if (i2 != 46) {
                    if (svgElementBase.baseStyle == null) {
                        svgElementBase.baseStyle = new SVG.Style();
                    }
                    processStyleProperty(svgElementBase.baseStyle, attributes.getLocalName(i), attributes.getValue(i).trim());
                } else {
                    CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(strTrim);
                    ArrayList arrayList = null;
                    while (!cSSTextScanner.empty()) {
                        String strNextToken3 = cSSTextScanner.nextToken();
                        if (strNextToken3 != null) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(strNextToken3);
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
            String strTrim = attributes.getValue(i).trim();
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
            if (i2 == 1) {
                textPositionedContainer.x = parseLengthList(strTrim);
            } else if (i2 == 2) {
                textPositionedContainer.y = parseLengthList(strTrim);
            } else if (i2 == 19) {
                textPositionedContainer.dx = parseLengthList(strTrim);
            } else if (i2 == 20) {
                textPositionedContainer.dy = parseLengthList(strTrim);
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

    public static void parseAttributesViewBox(SVG.SvgViewBoxContainer svgViewBoxContainer, Attributes attributes) throws SVGParseException {
        for (int i = 0; i < attributes.getLength(); i++) {
            String strTrim = attributes.getValue(i).trim();
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
            if (i2 == 7) {
                parsePreserveAspectRatio(svgViewBoxContainer, strTrim);
            } else if (i2 != 87) {
                continue;
            } else {
                TextScanner textScanner = new TextScanner(strTrim);
                textScanner.skipWhitespace();
                float fNextFloat = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float fNextFloat2 = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float fNextFloat3 = textScanner.nextFloat();
                textScanner.skipCommaWhitespace();
                float fNextFloat4 = textScanner.nextFloat();
                if (Float.isNaN(fNextFloat) || Float.isNaN(fNextFloat2) || Float.isNaN(fNextFloat3) || Float.isNaN(fNextFloat4)) {
                    throw new SVGParseException("Invalid viewBox definition - should have four numbers");
                }
                if (fNextFloat3 < 0.0f) {
                    throw new SVGParseException("Invalid viewBox. width cannot be negative");
                }
                if (fNextFloat4 < 0.0f) {
                    throw new SVGParseException("Invalid viewBox. height cannot be negative");
                }
                svgViewBoxContainer.viewBox = new SVG.Box(fNextFloat, fNextFloat2, fNextFloat3, fNextFloat4);
            }
        }
    }

    public static SVG.Colour parseColour(String str) throws SVGParseException {
        long j;
        int i;
        if (str.charAt(0) == '#') {
            int length = str.length();
            IntegerParser integerParser = null;
            if (1 < length) {
                long j2 = 0;
                int i2 = 1;
                while (i2 < length) {
                    char cCharAt = str.charAt(i2);
                    if (cCharAt < '0' || cCharAt > '9') {
                        if (cCharAt >= 'A' && cCharAt <= 'F') {
                            j = j2 * 16;
                            i = cCharAt - 'A';
                        } else {
                            if (cCharAt < 'a' || cCharAt > 'f') {
                                break;
                            }
                            j = j2 * 16;
                            i = cCharAt - 'a';
                        }
                        j2 = j + i + 10;
                    } else {
                        j2 = (j2 * 16) + (cCharAt - '0');
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
        boolean zStartsWith = lowerCase.startsWith("rgba(");
        if (zStartsWith || lowerCase.startsWith("rgb(")) {
            TextScanner textScanner = new TextScanner(str.substring(zStartsWith ? 5 : 4));
            textScanner.skipWhitespace();
            float fNextFloat = textScanner.nextFloat();
            if (!Float.isNaN(fNextFloat) && textScanner.consume('%')) {
                fNextFloat = (fNextFloat * 256.0f) / 100.0f;
            }
            float fCheckedNextFloat = textScanner.checkedNextFloat(fNextFloat);
            if (!Float.isNaN(fCheckedNextFloat) && textScanner.consume('%')) {
                fCheckedNextFloat = (fCheckedNextFloat * 256.0f) / 100.0f;
            }
            float fCheckedNextFloat2 = textScanner.checkedNextFloat(fCheckedNextFloat);
            if (!Float.isNaN(fCheckedNextFloat2) && textScanner.consume('%')) {
                fCheckedNextFloat2 = (fCheckedNextFloat2 * 256.0f) / 100.0f;
            }
            if (!zStartsWith) {
                textScanner.skipWhitespace();
                if (Float.isNaN(fCheckedNextFloat2) || !textScanner.consume(')')) {
                    throw new SVGParseException("Bad rgb() colour value: ".concat(str));
                }
                return new SVG.Colour((clamp255(fNextFloat) << 16) | (-16777216) | (clamp255(fCheckedNextFloat) << 8) | clamp255(fCheckedNextFloat2));
            }
            float fCheckedNextFloat3 = textScanner.checkedNextFloat(fCheckedNextFloat2);
            textScanner.skipWhitespace();
            if (Float.isNaN(fCheckedNextFloat3) || !textScanner.consume(')')) {
                throw new SVGParseException("Bad rgba() colour value: ".concat(str));
            }
            return new SVG.Colour((clamp255(fCheckedNextFloat3 * 256.0f) << 24) | (clamp255(fNextFloat) << 16) | (clamp255(fCheckedNextFloat) << 8) | clamp255(fCheckedNextFloat2));
        }
        boolean zStartsWith2 = lowerCase.startsWith("hsla(");
        if (!zStartsWith2 && !lowerCase.startsWith("hsl(")) {
            Integer num = (Integer) ((HashMap) ColourKeywords.colourKeywords).get(lowerCase);
            if (num != null) {
                return new SVG.Colour(num.intValue());
            }
            throw new SVGParseException("Invalid colour keyword: ".concat(lowerCase));
        }
        TextScanner textScanner2 = new TextScanner(str.substring(zStartsWith2 ? 5 : 4));
        textScanner2.skipWhitespace();
        float fNextFloat2 = textScanner2.nextFloat();
        float fCheckedNextFloat4 = textScanner2.checkedNextFloat(fNextFloat2);
        if (!Float.isNaN(fCheckedNextFloat4)) {
            textScanner2.consume('%');
        }
        float fCheckedNextFloat5 = textScanner2.checkedNextFloat(fCheckedNextFloat4);
        if (!Float.isNaN(fCheckedNextFloat5)) {
            textScanner2.consume('%');
        }
        if (!zStartsWith2) {
            textScanner2.skipWhitespace();
            if (Float.isNaN(fCheckedNextFloat5) || !textScanner2.consume(')')) {
                throw new SVGParseException("Bad hsl() colour value: ".concat(str));
            }
            return new SVG.Colour(hslToRgb(fNextFloat2, fCheckedNextFloat4, fCheckedNextFloat5) | (-16777216));
        }
        float fCheckedNextFloat6 = textScanner2.checkedNextFloat(fCheckedNextFloat5);
        textScanner2.skipWhitespace();
        if (Float.isNaN(fCheckedNextFloat6) || !textScanner2.consume(')')) {
            throw new SVGParseException("Bad hsla() colour value: ".concat(str));
        }
        return new SVG.Colour((clamp255(fCheckedNextFloat6 * 256.0f) << 24) | hslToRgb(fNextFloat2, fCheckedNextFloat4, fCheckedNextFloat5));
    }

    public static float parseFloat(String str) throws SVGParseException {
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
            String strNextQuotedString = textScanner.nextQuotedString();
            if (strNextQuotedString == null) {
                strNextQuotedString = textScanner.nextToken(',', true);
            }
            if (strNextQuotedString == null) {
                return arrayList;
            }
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.add(strNextQuotedString);
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

    public static SVG.Length parseLength(String str) throws SVGParseException {
        if (str.length() == 0) {
            throw new SVGParseException("Invalid length value (empty string)");
        }
        int length = str.length();
        SVG.Unit unitValueOf = SVG.Unit.px;
        char cCharAt = str.charAt(length - 1);
        if (cCharAt == '%') {
            length--;
            unitValueOf = SVG.Unit.percent;
        } else if (length > 2 && Character.isLetter(cCharAt) && Character.isLetter(str.charAt(length - 2))) {
            length -= 2;
            try {
                unitValueOf = SVG.Unit.valueOf(str.substring(length).toLowerCase(Locale.US));
            } catch (IllegalArgumentException unused) {
                throw new SVGParseException("Invalid length unit specifier: ".concat(str));
            }
        }
        try {
            return new SVG.Length(parseFloat(length, str), unitValueOf);
        } catch (NumberFormatException e) {
            throw new SVGParseException("Invalid length value: ".concat(str), e);
        }
    }

    public static List parseLengthList(String str) throws SVGParseException {
        String str2;
        if (str.length() == 0) {
            throw new SVGParseException("Invalid length list (empty string)");
        }
        ArrayList arrayList = new ArrayList(1);
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        while (!textScanner.empty()) {
            float fNextFloat = textScanner.nextFloat();
            if (Float.isNaN(fNextFloat)) {
                StringBuilder sb = new StringBuilder("Invalid length list value: ");
                int i = textScanner.position;
                while (true) {
                    boolean zEmpty = textScanner.empty();
                    str2 = textScanner.input;
                    if (zEmpty || TextScanner.isWhitespace(str2.charAt(textScanner.position))) {
                        break;
                    }
                    textScanner.position++;
                }
                String strSubstring = str2.substring(i, textScanner.position);
                textScanner.position = i;
                sb.append(strSubstring);
                throw new SVGParseException(sb.toString());
            }
            SVG.Unit unitNextUnit = textScanner.nextUnit();
            if (unitNextUnit == null) {
                unitNextUnit = SVG.Unit.px;
            }
            arrayList.add(new SVG.Length(fNextFloat, unitNextUnit));
            textScanner.skipCommaWhitespace();
        }
        return arrayList;
    }

    public static SVG.Length parseLengthOrAuto(TextScanner textScanner) {
        return textScanner.consume("auto") ? new SVG.Length(0.0f) : textScanner.nextLength();
    }

    public static Float parseOpacity(String str) {
        try {
            float f = parseFloat(str);
            float f2 = 0.0f;
            if (f < 0.0f) {
                f = f2;
            } else {
                f2 = 1.0f;
                if (f > 1.0f) {
                    f = f2;
                }
            }
            return Float.valueOf(f);
        } catch (SVGParseException unused) {
            return null;
        }
    }

    public static SVG.SvgPaint parsePaintSpecifier(String str) {
        SVG.SvgPaint colour = null;
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
        int iIndexOf = str.indexOf(")");
        if (iIndexOf == -1) {
            return new SVG.PaintReference(str.substring(4).trim(), null);
        }
        String strTrim = str.substring(4, iIndexOf).trim();
        String strTrim2 = str.substring(iIndexOf + 1).trim();
        if (strTrim2.length() > 0) {
            if (strTrim2.equals(SignalSeverity.NONE)) {
                colour = SVG.Colour.TRANSPARENT;
            } else if (strTrim2.equals("currentColor")) {
                colour = SVG.CurrentColor.instance;
            } else {
                try {
                    colour = parseColour(strTrim2);
                } catch (SVGParseException unused2) {
                }
            }
        }
        return new SVG.PaintReference(strTrim, colour);
    }

    public static void parsePreserveAspectRatio(SVG.SvgPreserveAspectRatioContainer svgPreserveAspectRatioContainer, String str) throws SVGParseException {
        PreserveAspectRatio.Scale scale;
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        String strNextToken = textScanner.nextToken();
        if ("defer".equals(strNextToken)) {
            textScanner.skipWhitespace();
            strNextToken = textScanner.nextToken();
        }
        PreserveAspectRatio.Alignment alignment = (PreserveAspectRatio.Alignment) ((HashMap) AspectRatioKeywords.aspectRatioKeywords).get(strNextToken);
        textScanner.skipWhitespace();
        if (textScanner.empty()) {
            scale = null;
        } else {
            String strNextToken2 = textScanner.nextToken();
            strNextToken2.getClass();
            if (strNextToken2.equals("meet")) {
                scale = PreserveAspectRatio.Scale.meet;
            } else {
                if (!strNextToken2.equals("slice")) {
                    throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid preserveAspectRatio definition: ", str));
                }
                scale = PreserveAspectRatio.Scale.slice;
            }
        }
        svgPreserveAspectRatioContainer.preserveAspectRatio = new PreserveAspectRatio(alignment, scale);
    }

    public static Map parseProcessingInstructionAttributes(TextScanner textScanner) {
        HashMap map = new HashMap();
        textScanner.skipWhitespace();
        String strNextToken = textScanner.nextToken('=', false);
        while (strNextToken != null) {
            textScanner.consume('=');
            map.put(strNextToken, textScanner.nextQuotedString());
            textScanner.skipWhitespace();
            strNextToken = textScanner.nextToken('=', false);
        }
        return map;
    }

    public static Matrix parseTransformList(String str) throws SVGParseException {
        Matrix matrix = new Matrix();
        TextScanner textScanner = new TextScanner(str);
        textScanner.skipWhitespace();
        while (!textScanner.empty()) {
            String strSubstring = null;
            if (!textScanner.empty()) {
                int i = textScanner.position;
                String str2 = textScanner.input;
                int iCharAt = str2.charAt(i);
                while (true) {
                    if ((iCharAt >= 97 && iCharAt <= 122) || (iCharAt >= 65 && iCharAt <= 90)) {
                        iCharAt = textScanner.advanceChar();
                    }
                }
                int i2 = textScanner.position;
                while (TextScanner.isWhitespace(iCharAt)) {
                    iCharAt = textScanner.advanceChar();
                }
                if (iCharAt == 40) {
                    textScanner.position++;
                    strSubstring = str2.substring(i, i2);
                } else {
                    textScanner.position = i;
                }
            }
            if (strSubstring == null) {
                throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Bad transform function encountered in transform list: ", str));
            }
            switch (strSubstring) {
                case "matrix":
                    textScanner.skipWhitespace();
                    float fNextFloat = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float fNextFloat2 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float fNextFloat3 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float fNextFloat4 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float fNextFloat5 = textScanner.nextFloat();
                    textScanner.skipCommaWhitespace();
                    float fNextFloat6 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(fNextFloat6) && textScanner.consume(')')) {
                        Matrix matrix2 = new Matrix();
                        matrix2.setValues(new float[]{fNextFloat, fNextFloat3, fNextFloat5, fNextFloat2, fNextFloat4, fNextFloat6, 0.0f, 0.0f, 1.0f});
                        matrix.preConcat(matrix2);
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "rotate":
                    textScanner.skipWhitespace();
                    float fNextFloat7 = textScanner.nextFloat();
                    float fPossibleNextFloat = textScanner.possibleNextFloat();
                    float fPossibleNextFloat2 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (Float.isNaN(fNextFloat7) || !textScanner.consume(')')) {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    if (Float.isNaN(fPossibleNextFloat)) {
                        matrix.preRotate(fNextFloat7);
                        break;
                    } else if (!Float.isNaN(fPossibleNextFloat2)) {
                        matrix.preRotate(fNextFloat7, fPossibleNextFloat, fPossibleNextFloat2);
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                case "scale":
                    textScanner.skipWhitespace();
                    float fNextFloat8 = textScanner.nextFloat();
                    float fPossibleNextFloat3 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(fNextFloat8) && textScanner.consume(')')) {
                        if (!Float.isNaN(fPossibleNextFloat3)) {
                            matrix.preScale(fNextFloat8, fPossibleNextFloat3);
                            break;
                        } else {
                            matrix.preScale(fNextFloat8, fNextFloat8);
                            break;
                        }
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "skewX":
                    textScanner.skipWhitespace();
                    float fNextFloat9 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(fNextFloat9) && textScanner.consume(')')) {
                        matrix.preSkew((float) Math.tan(Math.toRadians(fNextFloat9)), 0.0f);
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "skewY":
                    textScanner.skipWhitespace();
                    float fNextFloat10 = textScanner.nextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(fNextFloat10) && textScanner.consume(')')) {
                        matrix.preSkew(0.0f, (float) Math.tan(Math.toRadians(fNextFloat10)));
                        break;
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                case "translate":
                    textScanner.skipWhitespace();
                    float fNextFloat11 = textScanner.nextFloat();
                    float fPossibleNextFloat4 = textScanner.possibleNextFloat();
                    textScanner.skipWhitespace();
                    if (!Float.isNaN(fNextFloat11) && textScanner.consume(')')) {
                        if (!Float.isNaN(fPossibleNextFloat4)) {
                            matrix.preTranslate(fNextFloat11, fPossibleNextFloat4);
                            break;
                        } else {
                            matrix.preTranslate(fNextFloat11, 0.0f);
                            break;
                        }
                    } else {
                        throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid transform list: ", str));
                    }
                    break;
                default:
                    throw new SVGParseException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid transform list fn: ", strSubstring, ")"));
            }
            if (textScanner.empty()) {
                return matrix;
            }
            textScanner.skipCommaWhitespace();
        }
        return matrix;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0481  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0160  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void processStyleProperty(SVG.Style style, String str, String str2) {
        SVG.Length length;
        SVG.Length lengthNextLength;
        SVG.Length length2;
        String strSubstring;
        SVG.Style.FontStyle fontStyle;
        SVG.Style.TextDecoration textDecoration;
        SVG.Style.TextAnchor textAnchor;
        Boolean bool;
        SVG.CSSClipRect cSSClipRect;
        SVG.Style.RenderQuality renderQuality;
        if (str2.length() == 0 || str2.equals("inherit")) {
            return;
        }
        try {
            switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(str).ordinal()]) {
                case 47:
                    SVG.SvgPaint paintSpecifier = parsePaintSpecifier(str2);
                    style.fill = paintSpecifier;
                    if (paintSpecifier != null) {
                        style.specifiedFlags |= 1;
                        break;
                    }
                    break;
                case 48:
                    SVG.Style.FillRule fillRule = "nonzero".equals(str2) ? SVG.Style.FillRule.NonZero : "evenodd".equals(str2) ? SVG.Style.FillRule.EvenOdd : null;
                    style.fillRule = fillRule;
                    if (fillRule != null) {
                        style.specifiedFlags |= 2;
                        break;
                    }
                    break;
                case 49:
                    Float opacity = parseOpacity(str2);
                    style.fillOpacity = opacity;
                    if (opacity != null) {
                        style.specifiedFlags |= 4;
                        break;
                    }
                    break;
                case 50:
                    SVG.SvgPaint paintSpecifier2 = parsePaintSpecifier(str2);
                    style.stroke = paintSpecifier2;
                    if (paintSpecifier2 != null) {
                        style.specifiedFlags |= 8;
                        break;
                    }
                    break;
                case 51:
                    Float opacity2 = parseOpacity(str2);
                    style.strokeOpacity = opacity2;
                    if (opacity2 != null) {
                        style.specifiedFlags |= 16;
                        break;
                    }
                    break;
                case 52:
                    style.strokeWidth = parseLength(str2);
                    style.specifiedFlags |= 32;
                    break;
                case 53:
                    SVG.Style.LineCap lineCap = "butt".equals(str2) ? SVG.Style.LineCap.Butt : "round".equals(str2) ? SVG.Style.LineCap.Round : "square".equals(str2) ? SVG.Style.LineCap.Square : null;
                    style.strokeLineCap = lineCap;
                    if (lineCap != null) {
                        style.specifiedFlags |= 64;
                        break;
                    }
                    break;
                case 54:
                    SVG.Style.LineJoin lineJoin = "miter".equals(str2) ? SVG.Style.LineJoin.Miter : "round".equals(str2) ? SVG.Style.LineJoin.Round : "bevel".equals(str2) ? SVG.Style.LineJoin.Bevel : null;
                    style.strokeLineJoin = lineJoin;
                    if (lineJoin != null) {
                        style.specifiedFlags |= 128;
                        break;
                    }
                    break;
                case 55:
                    style.strokeMiterLimit = Float.valueOf(parseFloat(str2));
                    style.specifiedFlags |= 256;
                    break;
                case 56:
                    if (!SignalSeverity.NONE.equals(str2)) {
                        SVG.Length[] lengthArr = null;
                        TextScanner textScanner = new TextScanner(str2);
                        textScanner.skipWhitespace();
                        if (!textScanner.empty() && (lengthNextLength = textScanner.nextLength()) != null && !lengthNextLength.isNegative()) {
                            float f = lengthNextLength.value;
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(lengthNextLength);
                            while (true) {
                                if (!textScanner.empty()) {
                                    textScanner.skipCommaWhitespace();
                                    SVG.Length lengthNextLength2 = textScanner.nextLength();
                                    if (lengthNextLength2 != null && !lengthNextLength2.isNegative()) {
                                        arrayList.add(lengthNextLength2);
                                        f += lengthNextLength2.value;
                                    }
                                } else if (f != 0.0f) {
                                    lengthArr = (SVG.Length[]) arrayList.toArray(new SVG.Length[arrayList.size()]);
                                }
                            }
                        }
                        style.strokeDashArray = lengthArr;
                        if (lengthArr != null) {
                            style.specifiedFlags |= 512;
                            break;
                        }
                    } else {
                        style.strokeDashArray = null;
                        style.specifiedFlags |= 512;
                        break;
                    }
                    break;
                case 57:
                    style.strokeDashOffset = parseLength(str2);
                    style.specifiedFlags |= 1024;
                    break;
                case 58:
                    style.opacity = parseOpacity(str2);
                    style.specifiedFlags |= 2048;
                    break;
                case 59:
                    style.color = parseColour(str2);
                    style.specifiedFlags |= 4096;
                    break;
                case 60:
                    if ("|caption|icon|menu|message-box|small-caption|status-bar|".contains("|" + str2 + '|')) {
                        TextScanner textScanner2 = new TextScanner(str2);
                        Integer num = null;
                        SVG.Style.FontStyle fontStyle2 = null;
                        String str3 = null;
                        while (true) {
                            String strNextToken = textScanner2.nextToken('/', false);
                            textScanner2.skipWhitespace();
                            if (strNextToken != null) {
                                if (num == null || fontStyle2 == null) {
                                    if (!strNextToken.equals(SystemUIAnalytics.QPNE_VID_NORMAL) && (num != null || (num = (Integer) ((HashMap) FontWeightKeywords.fontWeightKeywords).get(strNextToken)) == null)) {
                                        if (fontStyle2 == null) {
                                            switch (strNextToken) {
                                                case "oblique":
                                                    fontStyle2 = SVG.Style.FontStyle.Oblique;
                                                    break;
                                                case "italic":
                                                    fontStyle2 = SVG.Style.FontStyle.Italic;
                                                    break;
                                                case "normal":
                                                    fontStyle2 = SVG.Style.FontStyle.Normal;
                                                    break;
                                                default:
                                                    fontStyle2 = null;
                                                    break;
                                            }
                                            if (fontStyle2 != null) {
                                                continue;
                                            }
                                        }
                                        if (str3 == null && strNextToken.equals("small-caps")) {
                                            str3 = strNextToken;
                                        }
                                    }
                                }
                                try {
                                    length2 = (SVG.Length) ((HashMap) FontSizeKeywords.fontSizeKeywords).get(strNextToken);
                                    if (length2 == null) {
                                        length2 = parseLength(strNextToken);
                                    }
                                } catch (SVGParseException unused) {
                                    length2 = null;
                                }
                                if (textScanner2.consume('/')) {
                                    textScanner2.skipWhitespace();
                                    String strNextToken2 = textScanner2.nextToken();
                                    if (strNextToken2 != null) {
                                        parseLength(strNextToken2);
                                    }
                                    textScanner2.skipWhitespace();
                                }
                                if (textScanner2.empty()) {
                                    strSubstring = null;
                                } else {
                                    int i = textScanner2.position;
                                    textScanner2.position = textScanner2.inputLength;
                                    strSubstring = textScanner2.input.substring(i);
                                }
                                style.fontFamily = parseFontFamily(strSubstring);
                                style.fontSize = length2;
                                style.fontWeight = Integer.valueOf(num == null ? 400 : num.intValue());
                                if (fontStyle2 == null) {
                                    fontStyle2 = SVG.Style.FontStyle.Normal;
                                }
                                style.fontStyle = fontStyle2;
                                style.specifiedFlags |= 122880;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                    break;
                case 61:
                    List fontFamily = parseFontFamily(str2);
                    style.fontFamily = fontFamily;
                    if (fontFamily != null) {
                        style.specifiedFlags |= 8192;
                        break;
                    }
                    break;
                case 62:
                    try {
                        SVG.Length length3 = (SVG.Length) ((HashMap) FontSizeKeywords.fontSizeKeywords).get(str2);
                        length = length3 == null ? parseLength(str2) : length3;
                    } catch (SVGParseException unused2) {
                        length = null;
                    }
                    style.fontSize = length;
                    if (length != null) {
                        style.specifiedFlags |= 16384;
                        break;
                    }
                    break;
                case 63:
                    Integer num2 = (Integer) ((HashMap) FontWeightKeywords.fontWeightKeywords).get(str2);
                    style.fontWeight = num2;
                    if (num2 != null) {
                        style.specifiedFlags |= 32768;
                        break;
                    }
                    break;
                case 64:
                    switch (str2) {
                        case "oblique":
                            fontStyle = SVG.Style.FontStyle.Oblique;
                            break;
                        case "italic":
                            fontStyle = SVG.Style.FontStyle.Italic;
                            break;
                        case "normal":
                            fontStyle = SVG.Style.FontStyle.Normal;
                            break;
                        default:
                            fontStyle = null;
                            break;
                    }
                    style.fontStyle = fontStyle;
                    if (fontStyle != null) {
                        style.specifiedFlags |= 65536;
                        break;
                    }
                    break;
                case 65:
                    switch (str2) {
                        case "line-through":
                            textDecoration = SVG.Style.TextDecoration.LineThrough;
                            break;
                        case "underline":
                            textDecoration = SVG.Style.TextDecoration.Underline;
                            break;
                        case "none":
                            textDecoration = SVG.Style.TextDecoration.None;
                            break;
                        case "blink":
                            textDecoration = SVG.Style.TextDecoration.Blink;
                            break;
                        case "overline":
                            textDecoration = SVG.Style.TextDecoration.Overline;
                            break;
                        default:
                            textDecoration = null;
                            break;
                    }
                    style.textDecoration = textDecoration;
                    if (textDecoration != null) {
                        style.specifiedFlags |= 131072;
                        break;
                    }
                    break;
                case 66:
                    SVG.Style.TextDirection textDirection = !str2.equals("ltr") ? !str2.equals("rtl") ? null : SVG.Style.TextDirection.RTL : SVG.Style.TextDirection.LTR;
                    style.direction = textDirection;
                    if (textDirection != null) {
                        style.specifiedFlags |= 68719476736L;
                        break;
                    }
                    break;
                case 67:
                    switch (str2) {
                        case "middle":
                            textAnchor = SVG.Style.TextAnchor.Middle;
                            break;
                        case "end":
                            textAnchor = SVG.Style.TextAnchor.End;
                            break;
                        case "start":
                            textAnchor = SVG.Style.TextAnchor.Start;
                            break;
                        default:
                            textAnchor = null;
                            break;
                    }
                    style.textAnchor = textAnchor;
                    if (textAnchor != null) {
                        style.specifiedFlags |= 262144;
                        break;
                    }
                    break;
                case 68:
                    switch (str2) {
                        case "hidden":
                        case "scroll":
                            bool = Boolean.FALSE;
                            break;
                        case "auto":
                        case "visible":
                            bool = Boolean.TRUE;
                            break;
                        default:
                            bool = null;
                            break;
                    }
                    style.overflow = bool;
                    if (bool != null) {
                        style.specifiedFlags |= 524288;
                        break;
                    }
                    break;
                case 69:
                    String functionalIRI = parseFunctionalIRI(str2);
                    style.markerStart = functionalIRI;
                    style.markerMid = functionalIRI;
                    style.markerEnd = functionalIRI;
                    style.specifiedFlags |= 14680064;
                    break;
                case 70:
                    style.markerStart = parseFunctionalIRI(str2);
                    style.specifiedFlags |= 2097152;
                    break;
                case 71:
                    style.markerMid = parseFunctionalIRI(str2);
                    style.specifiedFlags |= 4194304;
                    break;
                case 72:
                    style.markerEnd = parseFunctionalIRI(str2);
                    style.specifiedFlags |= 8388608;
                    break;
                case 73:
                    if (str2.indexOf(124) < 0) {
                        if ("|inline|block|list-item|run-in|compact|marker|table|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption|none|".contains("|" + str2 + '|')) {
                            style.display = Boolean.valueOf(!str2.equals(SignalSeverity.NONE));
                            style.specifiedFlags |= 16777216;
                            break;
                        }
                    }
                    break;
                case 74:
                    if (str2.indexOf(124) < 0) {
                        if ("|visible|hidden|collapse|".contains("|" + str2 + '|')) {
                            style.visibility = Boolean.valueOf(str2.equals("visible"));
                            style.specifiedFlags |= 33554432;
                            break;
                        }
                    }
                    break;
                case 75:
                    if (str2.equals("currentColor")) {
                        style.stopColor = SVG.CurrentColor.instance;
                    } else {
                        try {
                            style.stopColor = parseColour(str2);
                        } catch (SVGParseException e) {
                            Log.w("SVGParser", e.getMessage());
                            return;
                        }
                    }
                    style.specifiedFlags |= 67108864;
                    break;
                case 76:
                    style.stopOpacity = parseOpacity(str2);
                    style.specifiedFlags |= 134217728;
                    break;
                case 77:
                    if (!"auto".equals(str2) && str2.startsWith("rect(")) {
                        TextScanner textScanner3 = new TextScanner(str2.substring(5));
                        textScanner3.skipWhitespace();
                        SVG.Length lengthOrAuto = parseLengthOrAuto(textScanner3);
                        textScanner3.skipCommaWhitespace();
                        SVG.Length lengthOrAuto2 = parseLengthOrAuto(textScanner3);
                        textScanner3.skipCommaWhitespace();
                        SVG.Length lengthOrAuto3 = parseLengthOrAuto(textScanner3);
                        textScanner3.skipCommaWhitespace();
                        SVG.Length lengthOrAuto4 = parseLengthOrAuto(textScanner3);
                        textScanner3.skipWhitespace();
                        cSSClipRect = (textScanner3.consume(')') || textScanner3.empty()) ? new SVG.CSSClipRect(lengthOrAuto, lengthOrAuto2, lengthOrAuto3, lengthOrAuto4) : null;
                    }
                    style.clip = cSSClipRect;
                    if (cSSClipRect != null) {
                        style.specifiedFlags |= 1048576;
                        break;
                    }
                    break;
                case 78:
                    style.clipPath = parseFunctionalIRI(str2);
                    style.specifiedFlags |= 268435456;
                    break;
                case 79:
                    style.clipRule = "nonzero".equals(str2) ? SVG.Style.FillRule.NonZero : "evenodd".equals(str2) ? SVG.Style.FillRule.EvenOdd : null;
                    style.specifiedFlags |= 536870912;
                    break;
                case 80:
                    style.mask = parseFunctionalIRI(str2);
                    style.specifiedFlags |= 1073741824;
                    break;
                case 81:
                    if (str2.equals("currentColor")) {
                        style.solidColor = SVG.CurrentColor.instance;
                    } else {
                        try {
                            style.solidColor = parseColour(str2);
                        } catch (SVGParseException e2) {
                            Log.w("SVGParser", e2.getMessage());
                            return;
                        }
                    }
                    style.specifiedFlags |= 2147483648L;
                    break;
                case 82:
                    style.solidOpacity = parseOpacity(str2);
                    style.specifiedFlags |= 4294967296L;
                    break;
                case 83:
                    if (str2.equals("currentColor")) {
                        style.viewportFill = SVG.CurrentColor.instance;
                    } else {
                        try {
                            style.viewportFill = parseColour(str2);
                        } catch (SVGParseException e3) {
                            Log.w("SVGParser", e3.getMessage());
                            return;
                        }
                    }
                    style.specifiedFlags |= 8589934592L;
                    break;
                case 84:
                    style.viewportFillOpacity = parseOpacity(str2);
                    style.specifiedFlags |= 17179869184L;
                    break;
                case 85:
                    SVG.Style.VectorEffect vectorEffect = !str2.equals(SignalSeverity.NONE) ? !str2.equals("non-scaling-stroke") ? null : SVG.Style.VectorEffect.NonScalingStroke : SVG.Style.VectorEffect.None;
                    style.vectorEffect = vectorEffect;
                    if (vectorEffect != null) {
                        style.specifiedFlags |= 34359738368L;
                        break;
                    }
                    break;
                case 86:
                    switch (str2) {
                        case "optimizeQuality":
                            renderQuality = SVG.Style.RenderQuality.optimizeQuality;
                            break;
                        case "auto":
                            renderQuality = SVG.Style.RenderQuality.auto;
                            break;
                        case "optimizeSpeed":
                            renderQuality = SVG.Style.RenderQuality.optimizeSpeed;
                            break;
                        default:
                            renderQuality = null;
                            break;
                    }
                    style.imageRendering = renderQuality;
                    if (renderQuality != null) {
                        style.specifiedFlags |= 137438953472L;
                        break;
                    }
                    break;
            }
        } catch (SVGParseException unused3) {
        }
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
                            if (sVGElem2 == SVGElem.title || sVGElem2 == SVGElem.desc) {
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
                            String string = sb.toString();
                            CSSParser cSSParser = new CSSParser(CSSParser.MediaType.screen, CSSParser.Source.Document);
                            SVG svg = this.svgDocument;
                            CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(string);
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

    public final void parseUsingSAX(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        Log.d("SVGParser", "Falling back to SAX parser");
        try {
            SAXParserFactory sAXParserFactoryNewInstance = SAXParserFactory.newInstance();
            sAXParserFactoryNewInstance.setFeature("http://xml.org/sax/features/external-general-entities", false);
            sAXParserFactoryNewInstance.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            XMLReader xMLReader = sAXParserFactoryNewInstance.newSAXParser().getXMLReader();
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

    public final void parseUsingXmlPullParser(InputStream inputStream) throws XmlPullParserException, ParserConfigurationException, SAXException, IOException {
        try {
            try {
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                XPPAttributesWrapper xPPAttributesWrapper = new XPPAttributesWrapper(this, xmlPullParserNewPullParser);
                xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-docdecl", false);
                xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                xmlPullParserNewPullParser.setInput(inputStream, null);
                for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.nextToken()) {
                    if (eventType == 0) {
                        this.svgDocument = new SVG();
                    } else if (eventType == 8) {
                        Log.d("SVGParser", "PROC INSTR: " + xmlPullParserNewPullParser.getText());
                        TextScanner textScanner = new TextScanner(xmlPullParserNewPullParser.getText());
                        String strNextToken = textScanner.nextToken();
                        parseProcessingInstructionAttributes(textScanner);
                        strNextToken.equals("xml-stylesheet");
                    } else if (eventType == 10) {
                        if (this.svgDocument.rootElement == null && xmlPullParserNewPullParser.getText().contains("<!ENTITY ")) {
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
                    } else if (eventType == 2) {
                        String name = xmlPullParserNewPullParser.getName();
                        if (xmlPullParserNewPullParser.getPrefix() != null) {
                            name = xmlPullParserNewPullParser.getPrefix() + ':' + name;
                        }
                        startElement(xmlPullParserNewPullParser.getNamespace(), xmlPullParserNewPullParser.getName(), name, xPPAttributesWrapper);
                    } else if (eventType == 3) {
                        String name2 = xmlPullParserNewPullParser.getName();
                        if (xmlPullParserNewPullParser.getPrefix() != null) {
                            name2 = xmlPullParserNewPullParser.getPrefix() + ':' + name2;
                        }
                        endElement(xmlPullParserNewPullParser.getNamespace(), xmlPullParserNewPullParser.getName(), name2);
                    } else if (eventType == 4) {
                        int[] iArr = new int[2];
                        text(xmlPullParserNewPullParser.getTextCharacters(iArr), iArr[0], iArr[1]);
                    } else if (eventType == 5) {
                        text(xmlPullParserNewPullParser.getText());
                    }
                }
            } catch (XmlPullParserException e) {
                throw new SVGParseException("XML parser problem", e);
            }
        } catch (IOException e2) {
            throw new SVGParseException("Stream error", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:129:0x031a, code lost:
    
        android.util.Log.e("SVGParser", "Bad path coords for " + ((char) r4) + " path segment");
     */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x032d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void path(Attributes attributes) throws SVGParseException {
        float fNextFloat;
        float f;
        char c;
        float fNextFloat2;
        float f2;
        float f3;
        float f4;
        char cCharAt;
        Attributes attributes2 = attributes;
        if (this.currentElement == null) {
            throw new SVGParseException("Invalid document. Root element must be <svg>");
        }
        SVG.Path path = new SVG.Path();
        path.document = this.svgDocument;
        path.parent = this.currentElement;
        parseAttributesCore(path, attributes2);
        parseAttributesStyle(path, attributes2);
        parseAttributesTransform(path, attributes2);
        parseAttributesConditional(path, attributes2);
        int i = 0;
        while (i < attributes2.getLength()) {
            String strTrim = attributes2.getValue(i).trim();
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes2.getLocalName(i)).ordinal()];
            float f5 = 0.0f;
            if (i2 == 8) {
                TextScanner textScanner = new TextScanner(strTrim);
                SVG.PathDefinition pathDefinition = new SVG.PathDefinition();
                if (!textScanner.empty()) {
                    int iIntValue = textScanner.nextChar().intValue();
                    char c2 = 'm';
                    if (iIntValue == 77 || iIntValue == 109) {
                        float f6 = 0.0f;
                        float fNextFloat3 = 0.0f;
                        float f7 = 0.0f;
                        float fCheckedNextFloat = 0.0f;
                        float f8 = 0.0f;
                        float f9 = 0.0f;
                        while (true) {
                            textScanner.skipWhitespace();
                            float f10 = f5;
                            switch (iIntValue) {
                                case 65:
                                case 97:
                                    float fNextFloat4 = textScanner.nextFloat();
                                    float f11 = f7;
                                    float fCheckedNextFloat2 = textScanner.checkedNextFloat(fNextFloat4);
                                    float fCheckedNextFloat3 = textScanner.checkedNextFloat(fCheckedNextFloat2);
                                    Boolean boolCheckedNextFlag = textScanner.checkedNextFlag(Float.valueOf(fCheckedNextFloat3));
                                    Boolean boolCheckedNextFlag2 = textScanner.checkedNextFlag(boolCheckedNextFlag);
                                    if (boolCheckedNextFlag2 == null) {
                                        fNextFloat = Float.NaN;
                                    } else {
                                        textScanner.skipCommaWhitespace();
                                        fNextFloat = textScanner.nextFloat();
                                    }
                                    float f12 = fNextFloat;
                                    float fCheckedNextFloat4 = textScanner.checkedNextFloat(f12);
                                    if (Float.isNaN(fCheckedNextFloat4) || fNextFloat4 < f10 || fCheckedNextFloat2 < f10) {
                                        break;
                                    } else {
                                        if (iIntValue == 97) {
                                            fCheckedNextFloat4 += f11;
                                            f = f12 + f6;
                                        } else {
                                            f = f12;
                                        }
                                        boolean zBooleanValue = boolCheckedNextFlag.booleanValue();
                                        boolean zBooleanValue2 = boolCheckedNextFlag2.booleanValue();
                                        float f13 = fCheckedNextFloat4;
                                        c = 'm';
                                        pathDefinition.arcTo(fNextFloat4, fCheckedNextFloat2, fCheckedNextFloat3, zBooleanValue, zBooleanValue2, f, f13);
                                        f6 = f;
                                        fNextFloat3 = f6;
                                        f7 = f13;
                                        fCheckedNextFloat = f7;
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                            break;
                                        } else {
                                            int i3 = textScanner.position;
                                            if (i3 != textScanner.inputLength && (((cCharAt = textScanner.input.charAt(i3)) >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z'))) {
                                                iIntValue = textScanner.nextChar().intValue();
                                            }
                                            f5 = f10;
                                            c2 = c;
                                        }
                                    }
                                    break;
                                case 67:
                                case 99:
                                    float fNextFloat5 = textScanner.nextFloat();
                                    float fCheckedNextFloat5 = textScanner.checkedNextFloat(fNextFloat5);
                                    float fCheckedNextFloat6 = textScanner.checkedNextFloat(fCheckedNextFloat5);
                                    float fCheckedNextFloat7 = textScanner.checkedNextFloat(fCheckedNextFloat6);
                                    float fCheckedNextFloat8 = textScanner.checkedNextFloat(fCheckedNextFloat7);
                                    float fCheckedNextFloat9 = textScanner.checkedNextFloat(fCheckedNextFloat8);
                                    if (Float.isNaN(fCheckedNextFloat9)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 99) {
                                            fCheckedNextFloat8 += f6;
                                            fCheckedNextFloat9 += f7;
                                            fNextFloat5 += f6;
                                            fCheckedNextFloat5 += f7;
                                            fCheckedNextFloat6 += f6;
                                            fCheckedNextFloat7 += f7;
                                        }
                                        float f14 = fCheckedNextFloat5;
                                        fNextFloat2 = fCheckedNextFloat6;
                                        f2 = fCheckedNextFloat7;
                                        f3 = fCheckedNextFloat9;
                                        f4 = fCheckedNextFloat8;
                                        pathDefinition.cubicTo(fNextFloat5, f14, fNextFloat2, f2, f4, f3);
                                        fNextFloat3 = fNextFloat2;
                                        fCheckedNextFloat = f2;
                                        f6 = f4;
                                        f7 = f3;
                                        c = 'm';
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 72:
                                case 104:
                                    float fNextFloat6 = textScanner.nextFloat();
                                    if (Float.isNaN(fNextFloat6)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 104) {
                                            fNextFloat6 += f6;
                                        }
                                        f6 = fNextFloat6;
                                        pathDefinition.lineTo(f6, f7);
                                        fNextFloat3 = f6;
                                        c = c2;
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 76:
                                case 108:
                                    float fNextFloat7 = textScanner.nextFloat();
                                    float fCheckedNextFloat10 = textScanner.checkedNextFloat(fNextFloat7);
                                    if (Float.isNaN(fCheckedNextFloat10)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 108) {
                                            fNextFloat7 += f6;
                                            fCheckedNextFloat10 += f7;
                                        }
                                        f6 = fNextFloat7;
                                        f7 = fCheckedNextFloat10;
                                        pathDefinition.lineTo(f6, f7);
                                        fNextFloat3 = f6;
                                        fCheckedNextFloat = f7;
                                        c = c2;
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 77:
                                case 109:
                                    float fNextFloat8 = textScanner.nextFloat();
                                    float fCheckedNextFloat11 = textScanner.checkedNextFloat(fNextFloat8);
                                    if (Float.isNaN(fCheckedNextFloat11)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        c2 = 'm';
                                        if (iIntValue == 109 && pathDefinition.commandsLength != 0) {
                                            fNextFloat8 += f6;
                                            fCheckedNextFloat11 += f7;
                                        }
                                        f6 = fNextFloat8;
                                        f7 = fCheckedNextFloat11;
                                        pathDefinition.moveTo(f6, f7);
                                        fNextFloat3 = f6;
                                        f8 = fNextFloat3;
                                        fCheckedNextFloat = f7;
                                        f9 = fCheckedNextFloat;
                                        iIntValue = iIntValue != 109 ? 76 : 108;
                                        c = c2;
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 81:
                                case 113:
                                    fNextFloat3 = textScanner.nextFloat();
                                    fCheckedNextFloat = textScanner.checkedNextFloat(fNextFloat3);
                                    float fCheckedNextFloat12 = textScanner.checkedNextFloat(fCheckedNextFloat);
                                    float fCheckedNextFloat13 = textScanner.checkedNextFloat(fCheckedNextFloat12);
                                    if (Float.isNaN(fCheckedNextFloat13)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 113) {
                                            fCheckedNextFloat12 += f6;
                                            fCheckedNextFloat13 += f7;
                                            fNextFloat3 += f6;
                                            fCheckedNextFloat += f7;
                                        }
                                        f6 = fCheckedNextFloat12;
                                        f7 = fCheckedNextFloat13;
                                        pathDefinition.quadTo(fNextFloat3, fCheckedNextFloat, f6, f7);
                                        c = 'm';
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 83:
                                case 115:
                                    float f15 = (f6 * 2.0f) - fNextFloat3;
                                    float f16 = (f7 * 2.0f) - fCheckedNextFloat;
                                    fNextFloat2 = textScanner.nextFloat();
                                    float fCheckedNextFloat14 = textScanner.checkedNextFloat(fNextFloat2);
                                    float fCheckedNextFloat15 = textScanner.checkedNextFloat(fCheckedNextFloat14);
                                    float fCheckedNextFloat16 = textScanner.checkedNextFloat(fCheckedNextFloat15);
                                    if (Float.isNaN(fCheckedNextFloat16)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 115) {
                                            fCheckedNextFloat15 += f6;
                                            fCheckedNextFloat16 += f7;
                                            fNextFloat2 += f6;
                                            fCheckedNextFloat14 += f7;
                                        }
                                        f2 = fCheckedNextFloat14;
                                        f3 = fCheckedNextFloat16;
                                        f4 = fCheckedNextFloat15;
                                        pathDefinition.cubicTo(f15, f16, fNextFloat2, f2, f4, f3);
                                        fNextFloat3 = fNextFloat2;
                                        fCheckedNextFloat = f2;
                                        f6 = f4;
                                        f7 = f3;
                                        c = 'm';
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 84:
                                case 116:
                                    fNextFloat3 = (f6 * 2.0f) - fNextFloat3;
                                    fCheckedNextFloat = (f7 * 2.0f) - fCheckedNextFloat;
                                    float fNextFloat9 = textScanner.nextFloat();
                                    float fCheckedNextFloat17 = textScanner.checkedNextFloat(fNextFloat9);
                                    if (Float.isNaN(fCheckedNextFloat17)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 116) {
                                            fNextFloat9 += f6;
                                            fCheckedNextFloat17 += f7;
                                        }
                                        f6 = fNextFloat9;
                                        f7 = fCheckedNextFloat17;
                                        pathDefinition.quadTo(fNextFloat3, fCheckedNextFloat, f6, f7);
                                        c = c2;
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 86:
                                case 118:
                                    float fNextFloat10 = textScanner.nextFloat();
                                    if (Float.isNaN(fNextFloat10)) {
                                        Log.e("SVGParser", "Bad path coords for " + ((char) iIntValue) + " path segment");
                                        break;
                                    } else {
                                        if (iIntValue == 118) {
                                            fNextFloat10 += f7;
                                        }
                                        f7 = fNextFloat10;
                                        pathDefinition.lineTo(f6, f7);
                                        fCheckedNextFloat = f7;
                                        c = c2;
                                        textScanner.skipCommaWhitespace();
                                        if (!textScanner.empty()) {
                                        }
                                    }
                                    break;
                                case 90:
                                case 122:
                                    pathDefinition.close();
                                    c = c2;
                                    f6 = f8;
                                    fNextFloat3 = f6;
                                    f7 = f9;
                                    fCheckedNextFloat = f7;
                                    textScanner.skipCommaWhitespace();
                                    if (!textScanner.empty()) {
                                    }
                                    break;
                            }
                        }
                    }
                }
                path.d = pathDefinition;
            } else if (i2 == 9 && parseFloat(strTrim) < 0.0f) {
                throw new SVGParseException("Invalid <path> element. pathLength cannot be negative");
            }
            i++;
            attributes2 = attributes;
        }
        this.currentElement.addChild(path);
    }

    /* JADX WARN: Code restructure failed: missing block: B:662:0x036c, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:685:0x058b, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SVGParseException {
        boolean z;
        if (this.ignoring) {
            this.ignoreDepth++;
            return;
        }
        if ("http://www.w3.org/2000/svg".equals(str) || "".equals(str)) {
            SVGElem sVGElem = (SVGElem) ((HashMap) SVGElem.cache).get(str2.length() > 0 ? str2 : str3);
            if (sVGElem == null) {
                sVGElem = SVGElem.UNSUPPORTED;
            }
            switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[sVGElem.ordinal()]) {
                case 1:
                    SVG.Svg svg = new SVG.Svg();
                    svg.document = this.svgDocument;
                    svg.parent = this.currentElement;
                    parseAttributesCore(svg, attributes);
                    parseAttributesStyle(svg, attributes);
                    parseAttributesConditional(svg, attributes);
                    parseAttributesViewBox(svg, attributes);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        String strTrim = attributes.getValue(i).trim();
                        int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i)).ordinal()];
                        if (i2 == 1) {
                            svg.x = parseLength(strTrim);
                        } else if (i2 == 2) {
                            svg.y = parseLength(strTrim);
                        } else if (i2 == 3) {
                            SVG.Length length = parseLength(strTrim);
                            svg.width = length;
                            if (length.isNegative()) {
                                throw new SVGParseException("Invalid <svg> element. width cannot be negative");
                            }
                        } else if (i2 != 4) {
                            continue;
                        } else {
                            SVG.Length length2 = parseLength(strTrim);
                            svg.height = length2;
                            if (length2.isNegative()) {
                                throw new SVGParseException("Invalid <svg> element. height cannot be negative");
                            }
                        }
                    }
                    SVG.SvgContainer svgContainer = this.currentElement;
                    if (svgContainer == null) {
                        this.svgDocument.rootElement = svg;
                    } else {
                        svgContainer.addChild(svg);
                    }
                    this.currentElement = svg;
                    return;
                case 2:
                case 3:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Group group = new SVG.Group();
                    group.document = this.svgDocument;
                    group.parent = this.currentElement;
                    parseAttributesCore(group, attributes);
                    parseAttributesStyle(group, attributes);
                    parseAttributesTransform(group, attributes);
                    parseAttributesConditional(group, attributes);
                    this.currentElement.addChild(group);
                    this.currentElement = group;
                    return;
                case 4:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Defs defs = new SVG.Defs();
                    defs.document = this.svgDocument;
                    defs.parent = this.currentElement;
                    parseAttributesCore(defs, attributes);
                    parseAttributesStyle(defs, attributes);
                    parseAttributesTransform(defs, attributes);
                    this.currentElement.addChild(defs);
                    this.currentElement = defs;
                    return;
                case 5:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Use use = new SVG.Use();
                    use.document = this.svgDocument;
                    use.parent = this.currentElement;
                    parseAttributesCore(use, attributes);
                    parseAttributesStyle(use, attributes);
                    parseAttributesTransform(use, attributes);
                    parseAttributesConditional(use, attributes);
                    for (int i3 = 0; i3 < attributes.getLength(); i3++) {
                        String strTrim2 = attributes.getValue(i3).trim();
                        int i4 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i3)).ordinal()];
                        if (i4 == 1) {
                            use.x = parseLength(strTrim2);
                        } else if (i4 == 2) {
                            use.y = parseLength(strTrim2);
                        } else if (i4 == 3) {
                            SVG.Length length3 = parseLength(strTrim2);
                            use.width = length3;
                            if (length3.isNegative()) {
                                throw new SVGParseException("Invalid <use> element. width cannot be negative");
                            }
                        } else if (i4 == 4) {
                            SVG.Length length4 = parseLength(strTrim2);
                            use.height = length4;
                            if (length4.isNegative()) {
                                throw new SVGParseException("Invalid <use> element. height cannot be negative");
                            }
                        } else if (i4 == 6 && ("".equals(attributes.getURI(i3)) || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i3)))) {
                            use.href = strTrim2;
                        }
                    }
                    this.currentElement.addChild(use);
                    this.currentElement = use;
                    return;
                case 6:
                    path(attributes);
                    return;
                case 7:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Rect rect = new SVG.Rect();
                    rect.document = this.svgDocument;
                    rect.parent = this.currentElement;
                    parseAttributesCore(rect, attributes);
                    parseAttributesStyle(rect, attributes);
                    parseAttributesTransform(rect, attributes);
                    parseAttributesConditional(rect, attributes);
                    for (int i5 = 0; i5 < attributes.getLength(); i5++) {
                        String strTrim3 = attributes.getValue(i5).trim();
                        int i6 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i5)).ordinal()];
                        if (i6 == 1) {
                            rect.x = parseLength(strTrim3);
                        } else if (i6 == 2) {
                            rect.y = parseLength(strTrim3);
                        } else if (i6 == 3) {
                            SVG.Length length5 = parseLength(strTrim3);
                            rect.width = length5;
                            if (length5.isNegative()) {
                                throw new SVGParseException("Invalid <rect> element. width cannot be negative");
                            }
                        } else if (i6 == 4) {
                            SVG.Length length6 = parseLength(strTrim3);
                            rect.height = length6;
                            if (length6.isNegative()) {
                                throw new SVGParseException("Invalid <rect> element. height cannot be negative");
                            }
                        } else if (i6 == 10) {
                            SVG.Length length7 = parseLength(strTrim3);
                            rect.rx = length7;
                            if (length7.isNegative()) {
                                throw new SVGParseException("Invalid <rect> element. rx cannot be negative");
                            }
                        } else if (i6 != 11) {
                            continue;
                        } else {
                            SVG.Length length8 = parseLength(strTrim3);
                            rect.ry = length8;
                            if (length8.isNegative()) {
                                throw new SVGParseException("Invalid <rect> element. ry cannot be negative");
                            }
                        }
                    }
                    this.currentElement.addChild(rect);
                    return;
                case 8:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Circle circle = new SVG.Circle();
                    circle.document = this.svgDocument;
                    circle.parent = this.currentElement;
                    parseAttributesCore(circle, attributes);
                    parseAttributesStyle(circle, attributes);
                    parseAttributesTransform(circle, attributes);
                    parseAttributesConditional(circle, attributes);
                    for (int i7 = 0; i7 < attributes.getLength(); i7++) {
                        String strTrim4 = attributes.getValue(i7).trim();
                        switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i7)).ordinal()]) {
                            case 12:
                                circle.cx = parseLength(strTrim4);
                                break;
                            case 13:
                                circle.cy = parseLength(strTrim4);
                                break;
                            case 14:
                                SVG.Length length9 = parseLength(strTrim4);
                                circle.r = length9;
                                if (length9.isNegative()) {
                                    throw new SVGParseException("Invalid <circle> element. r cannot be negative");
                                }
                                break;
                        }
                    }
                    this.currentElement.addChild(circle);
                    return;
                case 9:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Ellipse ellipse = new SVG.Ellipse();
                    ellipse.document = this.svgDocument;
                    ellipse.parent = this.currentElement;
                    parseAttributesCore(ellipse, attributes);
                    parseAttributesStyle(ellipse, attributes);
                    parseAttributesTransform(ellipse, attributes);
                    parseAttributesConditional(ellipse, attributes);
                    for (int i8 = 0; i8 < attributes.getLength(); i8++) {
                        String strTrim5 = attributes.getValue(i8).trim();
                        switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i8)).ordinal()]) {
                            case 10:
                                SVG.Length length10 = parseLength(strTrim5);
                                ellipse.rx = length10;
                                if (length10.isNegative()) {
                                    throw new SVGParseException("Invalid <ellipse> element. rx cannot be negative");
                                }
                                break;
                            case 11:
                                SVG.Length length11 = parseLength(strTrim5);
                                ellipse.ry = length11;
                                if (length11.isNegative()) {
                                    throw new SVGParseException("Invalid <ellipse> element. ry cannot be negative");
                                }
                                break;
                            case 12:
                                ellipse.cx = parseLength(strTrim5);
                                break;
                            case 13:
                                ellipse.cy = parseLength(strTrim5);
                                break;
                        }
                    }
                    this.currentElement.addChild(ellipse);
                    return;
                case 10:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Line line = new SVG.Line();
                    line.document = this.svgDocument;
                    line.parent = this.currentElement;
                    parseAttributesCore(line, attributes);
                    parseAttributesStyle(line, attributes);
                    parseAttributesTransform(line, attributes);
                    parseAttributesConditional(line, attributes);
                    for (int i9 = 0; i9 < attributes.getLength(); i9++) {
                        String strTrim6 = attributes.getValue(i9).trim();
                        switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i9)).ordinal()]) {
                            case 15:
                                line.x1 = parseLength(strTrim6);
                                break;
                            case 16:
                                line.y1 = parseLength(strTrim6);
                                break;
                            case 17:
                                line.x2 = parseLength(strTrim6);
                                break;
                            case 18:
                                line.y2 = parseLength(strTrim6);
                                break;
                        }
                    }
                    this.currentElement.addChild(line);
                    return;
                case 11:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.PolyLine polyLine = new SVG.PolyLine();
                    polyLine.document = this.svgDocument;
                    polyLine.parent = this.currentElement;
                    parseAttributesCore(polyLine, attributes);
                    parseAttributesStyle(polyLine, attributes);
                    parseAttributesTransform(polyLine, attributes);
                    parseAttributesConditional(polyLine, attributes);
                    parseAttributesPolyLine(polyLine, attributes, "polyline");
                    this.currentElement.addChild(polyLine);
                    return;
                case 12:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Polygon polygon = new SVG.Polygon();
                    polygon.document = this.svgDocument;
                    polygon.parent = this.currentElement;
                    parseAttributesCore(polygon, attributes);
                    parseAttributesStyle(polygon, attributes);
                    parseAttributesTransform(polygon, attributes);
                    parseAttributesConditional(polygon, attributes);
                    parseAttributesPolyLine(polygon, attributes, "polygon");
                    this.currentElement.addChild(polygon);
                    return;
                case 13:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Text text = new SVG.Text();
                    text.document = this.svgDocument;
                    text.parent = this.currentElement;
                    parseAttributesCore(text, attributes);
                    parseAttributesStyle(text, attributes);
                    parseAttributesTransform(text, attributes);
                    parseAttributesConditional(text, attributes);
                    parseAttributesTextPosition(text, attributes);
                    this.currentElement.addChild(text);
                    this.currentElement = text;
                    return;
                case 14:
                    SVG.SvgContainer svgContainer2 = this.currentElement;
                    if (svgContainer2 == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    if (!(svgContainer2 instanceof SVG.TextContainer)) {
                        throw new SVGParseException("Invalid document. <tspan> elements are only valid inside <text> or other <tspan> elements.");
                    }
                    SVG.TSpan tSpan = new SVG.TSpan();
                    tSpan.document = this.svgDocument;
                    tSpan.parent = this.currentElement;
                    parseAttributesCore(tSpan, attributes);
                    parseAttributesStyle(tSpan, attributes);
                    parseAttributesConditional(tSpan, attributes);
                    parseAttributesTextPosition(tSpan, attributes);
                    this.currentElement.addChild(tSpan);
                    this.currentElement = tSpan;
                    SVG.SvgContainer svgContainer3 = tSpan.parent;
                    if (svgContainer3 instanceof SVG.Text) {
                        tSpan.textRoot = (SVG.Text) svgContainer3;
                        return;
                    } else {
                        tSpan.textRoot = ((SVG.TextChild) svgContainer3).getTextRoot();
                        return;
                    }
                case 15:
                    SVG.SvgContainer svgContainer4 = this.currentElement;
                    if (svgContainer4 == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    if (!(svgContainer4 instanceof SVG.TextContainer)) {
                        throw new SVGParseException("Invalid document. <tref> elements are only valid inside <text> or <tspan> elements.");
                    }
                    SVG.TRef tRef = new SVG.TRef();
                    tRef.document = this.svgDocument;
                    tRef.parent = this.currentElement;
                    parseAttributesCore(tRef, attributes);
                    parseAttributesStyle(tRef, attributes);
                    parseAttributesConditional(tRef, attributes);
                    for (int i10 = 0; i10 < attributes.getLength(); i10++) {
                        String strTrim7 = attributes.getValue(i10).trim();
                        if (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i10)).ordinal()] == 6 && ("".equals(attributes.getURI(i10)) || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i10)))) {
                            tRef.href = strTrim7;
                        }
                    }
                    this.currentElement.addChild(tRef);
                    SVG.SvgContainer svgContainer5 = tRef.parent;
                    if (svgContainer5 instanceof SVG.Text) {
                        tRef.textRoot = (SVG.Text) svgContainer5;
                        return;
                    } else {
                        tRef.textRoot = ((SVG.TextChild) svgContainer5).getTextRoot();
                        return;
                    }
                case 16:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Switch r1 = new SVG.Switch();
                    r1.document = this.svgDocument;
                    r1.parent = this.currentElement;
                    parseAttributesCore(r1, attributes);
                    parseAttributesStyle(r1, attributes);
                    parseAttributesTransform(r1, attributes);
                    parseAttributesConditional(r1, attributes);
                    this.currentElement.addChild(r1);
                    this.currentElement = r1;
                    return;
                case 17:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Symbol symbol = new SVG.Symbol();
                    symbol.document = this.svgDocument;
                    symbol.parent = this.currentElement;
                    parseAttributesCore(symbol, attributes);
                    parseAttributesStyle(symbol, attributes);
                    parseAttributesConditional(symbol, attributes);
                    parseAttributesViewBox(symbol, attributes);
                    this.currentElement.addChild(symbol);
                    this.currentElement = symbol;
                    return;
                case 18:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Marker marker = new SVG.Marker();
                    marker.document = this.svgDocument;
                    marker.parent = this.currentElement;
                    parseAttributesCore(marker, attributes);
                    parseAttributesStyle(marker, attributes);
                    parseAttributesConditional(marker, attributes);
                    parseAttributesViewBox(marker, attributes);
                    for (int i11 = 0; i11 < attributes.getLength(); i11++) {
                        String strTrim8 = attributes.getValue(i11).trim();
                        switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i11)).ordinal()]) {
                            case 26:
                                marker.refX = parseLength(strTrim8);
                                continue;
                            case 27:
                                marker.refY = parseLength(strTrim8);
                                continue;
                            case 28:
                                SVG.Length length12 = parseLength(strTrim8);
                                marker.markerWidth = length12;
                                if (length12.isNegative()) {
                                    throw new SVGParseException("Invalid <marker> element. markerWidth cannot be negative");
                                }
                                continue;
                            case 29:
                                SVG.Length length13 = parseLength(strTrim8);
                                marker.markerHeight = length13;
                                if (length13.isNegative()) {
                                    throw new SVGParseException("Invalid <marker> element. markerHeight cannot be negative");
                                }
                                continue;
                            case 30:
                                if ("strokeWidth".equals(strTrim8)) {
                                    marker.markerUnitsAreUser = false;
                                    continue;
                                } else {
                                    if (!"userSpaceOnUse".equals(strTrim8)) {
                                        throw new SVGParseException("Invalid value for attribute markerUnits");
                                    }
                                    marker.markerUnitsAreUser = true;
                                }
                            case 31:
                                if ("auto".equals(strTrim8)) {
                                    marker.orient = Float.valueOf(Float.NaN);
                                    break;
                                } else {
                                    marker.orient = Float.valueOf(parseFloat(strTrim8));
                                    break;
                                }
                        }
                    }
                    this.currentElement.addChild(marker);
                    this.currentElement = marker;
                    return;
                case 19:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.SvgLinearGradient svgLinearGradient = new SVG.SvgLinearGradient();
                    svgLinearGradient.document = this.svgDocument;
                    svgLinearGradient.parent = this.currentElement;
                    parseAttributesCore(svgLinearGradient, attributes);
                    parseAttributesStyle(svgLinearGradient, attributes);
                    parseAttributesGradient(svgLinearGradient, attributes);
                    for (int i12 = 0; i12 < attributes.getLength(); i12++) {
                        String strTrim9 = attributes.getValue(i12).trim();
                        switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i12)).ordinal()]) {
                            case 15:
                                svgLinearGradient.x1 = parseLength(strTrim9);
                                break;
                            case 16:
                                svgLinearGradient.y1 = parseLength(strTrim9);
                                break;
                            case 17:
                                svgLinearGradient.x2 = parseLength(strTrim9);
                                break;
                            case 18:
                                svgLinearGradient.y2 = parseLength(strTrim9);
                                break;
                        }
                    }
                    this.currentElement.addChild(svgLinearGradient);
                    this.currentElement = svgLinearGradient;
                    return;
                case 20:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.SvgRadialGradient svgRadialGradient = new SVG.SvgRadialGradient();
                    svgRadialGradient.document = this.svgDocument;
                    svgRadialGradient.parent = this.currentElement;
                    parseAttributesCore(svgRadialGradient, attributes);
                    parseAttributesStyle(svgRadialGradient, attributes);
                    parseAttributesGradient(svgRadialGradient, attributes);
                    for (int i13 = 0; i13 < attributes.getLength(); i13++) {
                        String strTrim10 = attributes.getValue(i13).trim();
                        int i14 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i13)).ordinal()];
                        if (i14 == 35) {
                            svgRadialGradient.fx = parseLength(strTrim10);
                        } else if (i14 != 36) {
                            switch (i14) {
                                case 12:
                                    svgRadialGradient.cx = parseLength(strTrim10);
                                    break;
                                case 13:
                                    svgRadialGradient.cy = parseLength(strTrim10);
                                    break;
                                case 14:
                                    SVG.Length length14 = parseLength(strTrim10);
                                    svgRadialGradient.r = length14;
                                    if (length14.isNegative()) {
                                        throw new SVGParseException("Invalid <radialGradient> element. r cannot be negative");
                                    }
                                    break;
                            }
                        } else {
                            svgRadialGradient.fy = parseLength(strTrim10);
                        }
                    }
                    this.currentElement.addChild(svgRadialGradient);
                    this.currentElement = svgRadialGradient;
                    return;
                case 21:
                    SVG.SvgContainer svgContainer6 = this.currentElement;
                    if (svgContainer6 == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    if (!(svgContainer6 instanceof SVG.GradientElement)) {
                        throw new SVGParseException("Invalid document. <stop> elements are only valid inside <linearGradient> or <radialGradient> elements.");
                    }
                    SVG.Stop stop = new SVG.Stop();
                    stop.document = this.svgDocument;
                    stop.parent = this.currentElement;
                    parseAttributesCore(stop, attributes);
                    parseAttributesStyle(stop, attributes);
                    for (int i15 = 0; i15 < attributes.getLength(); i15++) {
                        String strTrim11 = attributes.getValue(i15).trim();
                        if (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i15)).ordinal()] == 37) {
                            if (strTrim11.length() == 0) {
                                throw new SVGParseException("Invalid offset value in <stop> (empty string)");
                            }
                            int length15 = strTrim11.length();
                            if (strTrim11.charAt(strTrim11.length() - 1) == '%') {
                                length15--;
                                z = true;
                            } else {
                                z = false;
                            }
                            try {
                                float f = parseFloat(length15, strTrim11);
                                float f2 = 100.0f;
                                if (z) {
                                    f /= 100.0f;
                                }
                                if (f < 0.0f) {
                                    f2 = 0.0f;
                                } else if (f <= 100.0f) {
                                    f2 = f;
                                }
                                stop.offset = Float.valueOf(f2);
                            } catch (NumberFormatException e) {
                                throw new SVGParseException("Invalid offset value in <stop>: ".concat(strTrim11), e);
                            }
                        }
                    }
                    this.currentElement.addChild(stop);
                    this.currentElement = stop;
                    return;
                case 22:
                case 23:
                    this.inMetadataElement = true;
                    this.metadataTag = sVGElem;
                    return;
                case 24:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.ClipPath clipPath = new SVG.ClipPath();
                    clipPath.document = this.svgDocument;
                    clipPath.parent = this.currentElement;
                    parseAttributesCore(clipPath, attributes);
                    parseAttributesStyle(clipPath, attributes);
                    parseAttributesTransform(clipPath, attributes);
                    parseAttributesConditional(clipPath, attributes);
                    for (int i16 = 0; i16 < attributes.getLength(); i16++) {
                        String strTrim12 = attributes.getValue(i16).trim();
                        if (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i16)).ordinal()] == 38) {
                            if ("objectBoundingBox".equals(strTrim12)) {
                                clipPath.clipPathUnitsAreUser = Boolean.FALSE;
                            } else {
                                if (!"userSpaceOnUse".equals(strTrim12)) {
                                    throw new SVGParseException("Invalid value for attribute clipPathUnits");
                                }
                                clipPath.clipPathUnitsAreUser = Boolean.TRUE;
                            }
                        }
                    }
                    this.currentElement.addChild(clipPath);
                    this.currentElement = clipPath;
                    return;
                case 25:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.TextPath textPath = new SVG.TextPath();
                    textPath.document = this.svgDocument;
                    textPath.parent = this.currentElement;
                    parseAttributesCore(textPath, attributes);
                    parseAttributesStyle(textPath, attributes);
                    parseAttributesConditional(textPath, attributes);
                    for (int i17 = 0; i17 < attributes.getLength(); i17++) {
                        String strTrim13 = attributes.getValue(i17).trim();
                        int i18 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i17)).ordinal()];
                        if (i18 != 6) {
                            if (i18 == 39) {
                                textPath.startOffset = parseLength(strTrim13);
                            }
                        } else if ("".equals(attributes.getURI(i17)) || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i17))) {
                            textPath.href = strTrim13;
                        }
                    }
                    this.currentElement.addChild(textPath);
                    this.currentElement = textPath;
                    SVG.SvgContainer svgContainer7 = textPath.parent;
                    if (svgContainer7 instanceof SVG.Text) {
                        textPath.textRoot = (SVG.Text) svgContainer7;
                        return;
                    } else {
                        textPath.textRoot = ((SVG.TextChild) svgContainer7).getTextRoot();
                        return;
                    }
                case 26:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Pattern pattern = new SVG.Pattern();
                    pattern.document = this.svgDocument;
                    pattern.parent = this.currentElement;
                    parseAttributesCore(pattern, attributes);
                    parseAttributesStyle(pattern, attributes);
                    parseAttributesConditional(pattern, attributes);
                    parseAttributesViewBox(pattern, attributes);
                    for (int i19 = 0; i19 < attributes.getLength(); i19++) {
                        String strTrim14 = attributes.getValue(i19).trim();
                        int i20 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i19)).ordinal()];
                        if (i20 == 1) {
                            pattern.x = parseLength(strTrim14);
                        } else if (i20 == 2) {
                            pattern.y = parseLength(strTrim14);
                        } else if (i20 == 3) {
                            SVG.Length length16 = parseLength(strTrim14);
                            pattern.width = length16;
                            if (length16.isNegative()) {
                                throw new SVGParseException("Invalid <pattern> element. width cannot be negative");
                            }
                        } else if (i20 == 4) {
                            SVG.Length length17 = parseLength(strTrim14);
                            pattern.height = length17;
                            if (length17.isNegative()) {
                                throw new SVGParseException("Invalid <pattern> element. height cannot be negative");
                            }
                        } else if (i20 != 6) {
                            switch (i20) {
                                case 40:
                                    if (!"objectBoundingBox".equals(strTrim14)) {
                                        if (!"userSpaceOnUse".equals(strTrim14)) {
                                            throw new SVGParseException("Invalid value for attribute patternUnits");
                                        }
                                        pattern.patternUnitsAreUser = Boolean.TRUE;
                                        break;
                                    } else {
                                        pattern.patternUnitsAreUser = Boolean.FALSE;
                                        break;
                                    }
                                case 41:
                                    if (!"objectBoundingBox".equals(strTrim14)) {
                                        if (!"userSpaceOnUse".equals(strTrim14)) {
                                            throw new SVGParseException("Invalid value for attribute patternContentUnits");
                                        }
                                        pattern.patternContentUnitsAreUser = Boolean.TRUE;
                                        break;
                                    } else {
                                        pattern.patternContentUnitsAreUser = Boolean.FALSE;
                                        break;
                                    }
                                case 42:
                                    pattern.patternTransform = parseTransformList(strTrim14);
                                    break;
                            }
                        } else if ("".equals(attributes.getURI(i19)) || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i19))) {
                            pattern.href = strTrim14;
                        }
                    }
                    this.currentElement.addChild(pattern);
                    this.currentElement = pattern;
                    return;
                case 27:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Image image = new SVG.Image();
                    image.document = this.svgDocument;
                    image.parent = this.currentElement;
                    parseAttributesCore(image, attributes);
                    parseAttributesStyle(image, attributes);
                    parseAttributesTransform(image, attributes);
                    parseAttributesConditional(image, attributes);
                    for (int i21 = 0; i21 < attributes.getLength(); i21++) {
                        String strTrim15 = attributes.getValue(i21).trim();
                        int i22 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i21)).ordinal()];
                        if (i22 == 1) {
                            image.x = parseLength(strTrim15);
                        } else if (i22 == 2) {
                            image.y = parseLength(strTrim15);
                        } else if (i22 == 3) {
                            SVG.Length length18 = parseLength(strTrim15);
                            image.width = length18;
                            if (length18.isNegative()) {
                                throw new SVGParseException("Invalid <use> element. width cannot be negative");
                            }
                        } else if (i22 == 4) {
                            SVG.Length length19 = parseLength(strTrim15);
                            image.height = length19;
                            if (length19.isNegative()) {
                                throw new SVGParseException("Invalid <use> element. height cannot be negative");
                            }
                        } else if (i22 != 6) {
                            if (i22 == 7) {
                                parsePreserveAspectRatio(image, strTrim15);
                            }
                        } else if ("".equals(attributes.getURI(i21)) || "http://www.w3.org/1999/xlink".equals(attributes.getURI(i21))) {
                            image.href = strTrim15;
                        }
                    }
                    this.currentElement.addChild(image);
                    this.currentElement = image;
                    return;
                case 28:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.View view = new SVG.View();
                    view.document = this.svgDocument;
                    view.parent = this.currentElement;
                    parseAttributesCore(view, attributes);
                    parseAttributesConditional(view, attributes);
                    parseAttributesViewBox(view, attributes);
                    this.currentElement.addChild(view);
                    this.currentElement = view;
                    return;
                case 29:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.Mask mask = new SVG.Mask();
                    mask.document = this.svgDocument;
                    mask.parent = this.currentElement;
                    parseAttributesCore(mask, attributes);
                    parseAttributesStyle(mask, attributes);
                    parseAttributesConditional(mask, attributes);
                    for (int i23 = 0; i23 < attributes.getLength(); i23++) {
                        String strTrim16 = attributes.getValue(i23).trim();
                        int i24 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i23)).ordinal()];
                        if (i24 == 1) {
                            parseLength(strTrim16);
                        } else if (i24 == 2) {
                            parseLength(strTrim16);
                        } else if (i24 == 3) {
                            SVG.Length length20 = parseLength(strTrim16);
                            mask.width = length20;
                            if (length20.isNegative()) {
                                throw new SVGParseException("Invalid <mask> element. width cannot be negative");
                            }
                        } else if (i24 == 4) {
                            SVG.Length length21 = parseLength(strTrim16);
                            mask.height = length21;
                            if (length21.isNegative()) {
                                throw new SVGParseException("Invalid <mask> element. height cannot be negative");
                            }
                        } else if (i24 != 43) {
                            if (i24 != 44) {
                                continue;
                            } else if ("objectBoundingBox".equals(strTrim16)) {
                                mask.maskContentUnitsAreUser = Boolean.FALSE;
                            } else {
                                if (!"userSpaceOnUse".equals(strTrim16)) {
                                    throw new SVGParseException("Invalid value for attribute maskContentUnits");
                                }
                                mask.maskContentUnitsAreUser = Boolean.TRUE;
                            }
                        } else if ("objectBoundingBox".equals(strTrim16)) {
                            mask.maskUnitsAreUser = Boolean.FALSE;
                        } else {
                            if (!"userSpaceOnUse".equals(strTrim16)) {
                                throw new SVGParseException("Invalid value for attribute maskUnits");
                            }
                            mask.maskUnitsAreUser = Boolean.TRUE;
                        }
                    }
                    this.currentElement.addChild(mask);
                    this.currentElement = mask;
                    return;
                case 30:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    String str4 = SystemUIAnalytics.QPNE_VID_COVER_ALL;
                    boolean zEquals = true;
                    for (int i25 = 0; i25 < attributes.getLength(); i25++) {
                        String strTrim17 = attributes.getValue(i25).trim();
                        int i26 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(attributes.getLocalName(i25)).ordinal()];
                        if (i26 == 88) {
                            zEquals = strTrim17.equals("text/css");
                        } else if (i26 == 89) {
                            str4 = strTrim17;
                        }
                    }
                    if (zEquals) {
                        CSSParser.MediaType mediaType = CSSParser.MediaType.screen;
                        CSSParser.CSSTextScanner cSSTextScanner = new CSSParser.CSSTextScanner(str4);
                        cSSTextScanner.skipWhitespace();
                        ArrayList arrayList = (ArrayList) CSSParser.parseMediaList(cSSTextScanner);
                        int size = arrayList.size();
                        int i27 = 0;
                        while (i27 < size) {
                            Object obj = arrayList.get(i27);
                            i27++;
                            CSSParser.MediaType mediaType2 = (CSSParser.MediaType) obj;
                            if (mediaType2 == CSSParser.MediaType.all || mediaType2 == mediaType) {
                                this.inStyleElement = true;
                                return;
                            }
                        }
                    }
                    this.ignoring = true;
                    this.ignoreDepth = 1;
                    return;
                case 31:
                    if (this.currentElement == null) {
                        throw new SVGParseException("Invalid document. Root element must be <svg>");
                    }
                    SVG.SolidColor solidColor = new SVG.SolidColor();
                    solidColor.document = this.svgDocument;
                    solidColor.parent = this.currentElement;
                    parseAttributesCore(solidColor, attributes);
                    parseAttributesStyle(solidColor, attributes);
                    this.currentElement.addChild(solidColor);
                    this.currentElement = solidColor;
                    return;
                default:
                    this.ignoring = true;
                    this.ignoreDepth = 1;
                    return;
            }
        }
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

    public static float parseFloat(int i, String str) throws SVGParseException {
        float number = new NumberParser().parseNumber(0, i, str);
        if (Float.isNaN(number)) {
            throw new SVGParseException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid float value: ", str));
        }
        return number;
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
