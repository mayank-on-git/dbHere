package com.dbHere.dbHere.sftp;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class SftpService {

    private final String host = "localhost";   // change if needed
    private final int port = 22;
    private final String username = "ubuntu";
    private final String password = "emor@123";

    private Session getSession() throws Exception {
        JSch jsch = new JSch();

        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        // 🔥 VERY IMPORTANT FIX
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        // 🔥 KEY FIX (add this)
        config.put("PreferredAuthentications", "password");


        session.setConfig(config);

        session.connect(10000); // timeout

        return session;
    }

    public void uploadFile(String localFile, String remoteDir) {
        try {
            Session session = getSession();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;

            sftp.put(localFile, remoteDir);

            sftp.exit();
            session.disconnect();

            System.out.println("✅ File uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String remoteFile, String localPath) {
        try {
            Session session = getSession();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;

            sftp.get(remoteFile, new FileOutputStream(localPath));

            sftp.exit();
            session.disconnect();

            System.out.println("✅ File downloaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}