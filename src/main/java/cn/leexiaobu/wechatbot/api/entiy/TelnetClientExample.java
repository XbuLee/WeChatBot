package cn.leexiaobu.wechatbot.api.entiy;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

import cn.leexiaobu.wechatbot.client.WechatBotClient;
import cn.leexiaobu.wechatbot.domain.WechatMsg;
import cn.leexiaobu.wechatbot.enums.MsgType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.SimpleOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;


/**
 * This is a simple example of use of TelnetClient.
 * An external option handler (SimpleTelnetOptionHandler) is used.
 * Initial configuration requested by TelnetClient will be:
 * WILL ECHO, WILL SUPPRESS-GA, DO SUPPRESS-GA.
 * VT100 terminal type will be subnegotiated.
 * <p>
 * Also, use of the sendAYT(), getLocalOptionState(), getRemoteOptionState()
 * is demonstrated.
 * When connected, type AYT to send an AYT command to the server and see
 * the result.
 * Type OPT to see a report of the state of the first 25 options.
 */
@Slf4j
public class TelnetClientExample implements Runnable {
    private TelnetClient tc;
    private String ip;
    private int port;
    private WechatBotClient wechatBotClient;
    private String wxid;

    public void sendMsg(String content) {
        try {
            final OutputStream outstr = tc.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outstr, "GBK");
            outputStreamWriter.write(content);
            outputStreamWriter.flush();
        } catch (IOException e) {
            log.error("退出了账号，已关闭链接");
            stop();
            e.printStackTrace();
        }

    }

    public TelnetClientExample(String ip, int port, WechatBotClient wechatBotClient, String wxid) {
        this.ip = ip;
        this.port = port;
        this.wechatBotClient = wechatBotClient;
        this.tc = new TelnetClient();
        this.wxid = wxid;
        final TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
        final EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
        final SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
        try {
            tc.addOptionHandler(ttopt);
            tc.addOptionHandler(echoopt);
            tc.addOptionHandler(gaopt);
            tc.connect(ip, port);
        } catch (InvalidTelnetOptionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(final String[] args) throws Exception {
        final String remoteip = "175.27.187.179";
        final int remoteport = 8000;
        TelnetClientExample client = new TelnetClientExample(remoteip, remoteport, null, "23177834269@chatroom");
        final Thread reader = new Thread(client);
        reader.start();
        int ret_read = 0;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        final char[] buff = new char[1024];
        while (true) {
            ret_read = inputStreamReader.read(buff);
            if (ret_read > 0) {
                final String line = new String(buff, 0, ret_read);
                client.sendMsg(line);
            }
        }
    }

    @Override
    public void run() {
        final InputStream instr = tc.getInputStream();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(instr, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            final char[] buff = new char[1024];
            int ret_read = 0;

            do {
                ret_read = inputStreamReader.read(buff);
                if (ret_read > 0) {
                    String receiveMsg = new String(buff, 0, ret_read);
                    System.out.print(receiveMsg);
//                    wechatBotClient.sendMsg(new WechatMsg(receiveMsg, wxid, MsgType.SEND_TXT.TYPE()));
                }
            }
            while (ret_read >= 0);
        } catch (final IOException e) {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }

        try {
            tc.disconnect();
        } catch (final IOException e) {
            System.err.println("Exception while closing telnet:" + e.getMessage());
        }
    }

    public void stop() {
        try {
            tc.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
