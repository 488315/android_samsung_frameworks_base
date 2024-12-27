package com.android.server.display;

import android.os.Environment;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayAddress;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.display.config.layout.Display;
import com.android.server.display.config.layout.Layouts;
import com.android.server.display.config.layout.XmlParser;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.layout.Layout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.datatype.DatatypeConfigurationException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceStateToLayoutMap {
    public final LogicalDisplayMapper$$ExternalSyntheticLambda2 mIdProducer;
    public final boolean mIsPortInDisplayLayoutEnabled;
    public final SparseArray mLayoutMap;

    public DeviceStateToLayoutMap(
            LogicalDisplayMapper$$ExternalSyntheticLambda2
                    logicalDisplayMapper$$ExternalSyntheticLambda2,
            DisplayManagerFlags displayManagerFlags) {
        File buildPath =
                Environment.buildPath(
                        Environment.getDataDirectory(),
                        new String[] {"system/displayconfig/display_layout_configuration.xml"});
        buildPath =
                buildPath.exists()
                        ? buildPath
                        : Environment.buildPath(
                                Environment.getVendorDirectory(),
                                new String[] {
                                    "etc/displayconfig/display_layout_configuration.xml"
                                });
        this.mLayoutMap = new SparseArray();
        this.mIsPortInDisplayLayoutEnabled =
                displayManagerFlags.mPortInDisplayLayoutFlagState.isEnabled();
        this.mIdProducer = logicalDisplayMapper$$ExternalSyntheticLambda2;
        loadLayoutsFromConfig(buildPath);
        createLayout(-1);
    }

    public final Layout createLayout(int i) {
        if (this.mLayoutMap.contains(i)) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Attempted to create a second layout for state ", "DeviceStateToLayoutMap");
            return null;
        }
        Layout layout = new Layout();
        this.mLayoutMap.append(i, layout);
        return layout;
    }

    public final Layout get(int i) {
        Layout layout = (Layout) this.mLayoutMap.get(i);
        return layout == null ? (Layout) this.mLayoutMap.get(-1) : layout;
    }

    public final DisplayAddress getDisplayAddressForLayoutDisplay(Display display) {
        BigInteger bigInteger;
        BigInteger bigInteger2 = display.address_optional;
        if (bigInteger2 != null) {
            return DisplayAddress.fromPhysicalDisplayId(bigInteger2.longValue());
        }
        if (this.mIsPortInDisplayLayoutEnabled && (bigInteger = display.port_optional) != null) {
            return DisplayAddress.fromPortAndModel((int) bigInteger.longValue(), (Long) null);
        }
        throw new IllegalArgumentException(
                "Must specify a display identifier in display layout configuration: " + display);
    }

    public void loadLayoutsFromConfig(File file) {
        int i;
        DeviceStateToLayoutMap deviceStateToLayoutMap = this;
        if (!file.exists()) {
            return;
        }
        Slog.i("DeviceStateToLayoutMap", "Loading display layouts from " + file);
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(new FileInputStream(file));
            try {
                Layouts read = XmlParser.read(bufferedInputStream);
                if (read == null) {
                    Slog.i("DeviceStateToLayoutMap", "Display layout config not found: " + file);
                    bufferedInputStream.close();
                    return;
                }
                if (read.layout == null) {
                    read.layout = new ArrayList();
                }
                Iterator it = ((ArrayList) read.layout).iterator();
                while (it.hasNext()) {
                    com.android.server.display.config.layout.Layout layout =
                            (com.android.server.display.config.layout.Layout) it.next();
                    Layout createLayout =
                            deviceStateToLayoutMap.createLayout(layout.state.intValue());
                    if (layout.display == null) {
                        layout.display = new ArrayList();
                    }
                    Iterator it2 = ((ArrayList) layout.display).iterator();
                    while (it2.hasNext()) {
                        Display display = (Display) it2.next();
                        DisplayAddress displayAddressForLayoutDisplay =
                                deviceStateToLayoutMap.getDisplayAddressForLayoutDisplay(display);
                        String str = display.position;
                        if ("front".equals(str)) {
                            i = 0;
                        } else {
                            i = "rear".equals(str) ? 1 : -1;
                        }
                        BigInteger bigInteger = display.leadDisplayAddress;
                        DisplayAddress.Physical fromPhysicalDisplayId =
                                bigInteger == null
                                        ? null
                                        : DisplayAddress.fromPhysicalDisplayId(
                                                bigInteger.longValue());
                        Boolean bool = display.defaultDisplay;
                        boolean booleanValue = bool == null ? false : bool.booleanValue();
                        Boolean bool2 = display.enabled;
                        createLayout.createDisplayLocked(
                                displayAddressForLayoutDisplay,
                                booleanValue,
                                bool2 == null ? false : bool2.booleanValue(),
                                display.displayGroup,
                                deviceStateToLayoutMap.mIdProducer,
                                i,
                                fromPhysicalDisplayId,
                                display.brightnessThrottlingMapId,
                                display.refreshRateZoneId,
                                display.refreshRateThermalThrottlingMapId,
                                display.powerThrottlingMapId);
                        deviceStateToLayoutMap = this;
                        it = it;
                    }
                    Iterator it3 = it;
                    createLayout.postProcessLocked();
                    deviceStateToLayoutMap = this;
                    it = it3;
                }
                bufferedInputStream.close();
            } finally {
            }
        } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
            Slog.e(
                    "DeviceStateToLayoutMap",
                    "Encountered an error while reading/parsing display layout config file: "
                            + file,
                    e);
        }
    }
}
