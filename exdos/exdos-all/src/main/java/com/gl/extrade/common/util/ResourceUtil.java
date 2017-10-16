package com.gl.extrade.common.util;

import java.io.Closeable;
import java.io.IOException;

public final class ResourceUtil {
    public static void closeResources(Closeable... resources) {
        for (Closeable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                   
                }
            }
        }
    }
}
