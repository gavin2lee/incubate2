package com.gl.extrade.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public final class IdentityUtil {
    private IdentityUtil() {
    }

    public static int getServerIdentity() {
        String threadName = Thread.currentThread().getName();
        String userHome = System.getProperty("user.home");

        // String serverIdFilepath =
        // IdentityUtil.class.getClassLoader().getResource("server.id").getFile();
        String serverIdFilepath = "server.id";
        File serverIdFile = new File(userHome, serverIdFilepath);
        int serverId = 1006;

        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            if (serverIdFile.exists() == false) {
                serverIdFile.createNewFile();
            }

            br = new BufferedReader(new FileReader(serverIdFile));
            String sLine = br.readLine();
            if (sLine == null) {
                // do nothing currently
            } else {
                String[] parts = sLine.split(",");
                serverId = Integer.parseInt(parts[0]);
                serverId++;
            }

            ResourceUtil.closeResources(br);

            String outLine = "" + serverId + "," + threadName;
            bw = new BufferedWriter(new FileWriter(serverIdFile, false));
            bw.write(outLine);

            bw.flush();
            ResourceUtil.closeResources(bw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ResourceUtil.closeResources(br, bw);
        }

        return serverId;
    }
}
