package com.android.server.display;

import android.os.Environment;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayAddress;
import com.android.server.display.config.layout.Display;
import com.android.server.display.config.layout.Layouts;
import com.android.server.display.config.layout.XmlParser;
import com.android.server.display.layout.DisplayIdProducer;
import com.android.server.display.layout.Layout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class DeviceStateToLayoutMap {
    public final DisplayIdProducer mIdProducer;
    public final SparseArray mLayoutMap;

    public DeviceStateToLayoutMap(DisplayIdProducer displayIdProducer) {
        this(displayIdProducer, Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc/displayconfig/display_layout_configuration.xml"}));
    }

    public DeviceStateToLayoutMap(DisplayIdProducer displayIdProducer, File file) {
        this.mLayoutMap = new SparseArray();
        this.mIdProducer = displayIdProducer;
        loadLayoutsFromConfig(file);
        createLayout(-1);
    }

    public void dumpLocked(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("DeviceStateToLayoutMap:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Registered Layouts:");
        for (int i = 0; i < this.mLayoutMap.size(); i++) {
            indentingPrintWriter.println("state(" + this.mLayoutMap.keyAt(i) + "): " + this.mLayoutMap.valueAt(i));
        }
    }

    public Layout get(int i) {
        Layout layout = (Layout) this.mLayoutMap.get(i);
        return layout == null ? (Layout) this.mLayoutMap.get(-1) : layout;
    }

    public int size() {
        return this.mLayoutMap.size();
    }

    public void loadLayoutsFromConfig(File file) {
        if (!file.exists()) {
            return;
        }
        Slog.i("DeviceStateToLayoutMap", "Loading display layouts from " + file);
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                Layouts read = XmlParser.read(bufferedInputStream);
                if (read == null) {
                    Slog.i("DeviceStateToLayoutMap", "Display layout config not found: " + file);
                    bufferedInputStream.close();
                    return;
                }
                for (com.android.server.display.config.layout.Layout layout : read.getLayout()) {
                    Layout createLayout = createLayout(layout.getState().intValue());
                    for (Display display : layout.getDisplay()) {
                        createLayout.createDisplayLocked(DisplayAddress.fromPhysicalDisplayId(display.getAddress().longValue()), display.isDefaultDisplay(), display.isEnabled(), display.getDisplayGroup(), this.mIdProducer, getPosition(display.getPosition()), 0, display.getBrightnessThrottlingMapId(), display.getRefreshRateZoneId(), display.getRefreshRateThermalThrottlingMapId());
                    }
                }
                bufferedInputStream.close();
            } finally {
            }
        } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
            Slog.e("DeviceStateToLayoutMap", "Encountered an error while reading/parsing display layout config file: " + file, e);
        }
    }

    public final int getPosition(String str) {
        if ("front".equals(str)) {
            return 0;
        }
        return "rear".equals(str) ? 1 : -1;
    }

    public final Layout createLayout(int i) {
        if (this.mLayoutMap.contains(i)) {
            Slog.e("DeviceStateToLayoutMap", "Attempted to create a second layout for state " + i);
            return null;
        }
        Layout layout = new Layout();
        this.mLayoutMap.append(i, layout);
        return layout;
    }
}
